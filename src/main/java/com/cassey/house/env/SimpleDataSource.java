package com.cassey.house.env;

import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

/**
 * phoenix jdbc driver 创建hbase连接池的成本很低，不需要像druid数据源那样复杂的管理过程
 */
public class SimpleDataSource implements DataSource, AutoCloseable {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SimpleDataSource.class);

    private String driverClass;

    private String url;

    private String user;

    private String password;

    private Properties connectionProperties;

    /**
     * 查询超时时间（秒），大于0才会有效
     */
    private volatile int queryTimeout = 600;

    /**
     * 本地连接池的最大连接数
     */
    private volatile int maxActive = 64;
    /**
     * 本地连接池
     */
    private final ConcurrentLinkedQueue<ConnectionDelegation> connectionPool = new ConcurrentLinkedQueue<>();


    @Override
    public Connection getConnection() throws SQLException {
        // 优先从本地连接池中取连接
        ConnectionDelegation delegation;
        //从本地连接池中获取连接，直到获取到一个没有过期的连接为止
        while ((delegation = connectionPool.poll()) != null) {
            if (!delegation.isClosed()) {
                break;
            }
        }

        // 本地连接池没有获取到有效连接
        if (delegation == null) {
            delegation = createConnection();
        }

        return delegation;
        //新建的连接使用完close的时候，会尝试放回本地连接池，以回收利用
    }

    @Override
    public Connection getConnection(String user, String password) throws SQLException {
        if (!Objects.equals(user, this.user)) {
            throw new UnsupportedOperationException("Not supported by " + this.getClass().getName());
        }

        if (!Objects.equals(password, this.password)) {
            throw new UnsupportedOperationException("Not supported by " + this.getClass().getName());
        }

        return getConnection();
    }

    /**
     * 尝试回收利用连接。
     * 如果本地连接池还有空位，则将连接放回连接池以复用
     * 否则关掉连接，不再利用
     *
     * @param connectionDelegation 连接
     * @throws SQLException 无法回收关闭连接时可能抛出
     */
    public void tryRecycle(ConnectionDelegation connectionDelegation) throws SQLException {
        //连接过期了
        if (getPoolSize() >= maxActive) {
            // 直接关掉当前连接而不入池
            connectionDelegation.getDelegated().close();
        } else {
            // 将当前连接入池
            connectionPool.add(connectionDelegation);
        }
    }

    @Override
    public void close() throws Exception {
        //关掉池中的所有连接
        ConnectionDelegation delegation;
        while ((delegation = connectionPool.poll()) != null) {
            delegation.getDelegated().close();
        }
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
//        Class.forName(driverClass);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Properties getConnectionProperties() {
        return connectionProperties;
    }

    public void setConnectionProperties(Properties connectionProperties) {
        this.connectionProperties = connectionProperties;
    }

    public int getQueryTimeout() {
        return queryTimeout;
    }

    public void setQueryTimeout(int queryTimeout) {
        this.queryTimeout = queryTimeout;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getPoolSize() {
        return connectionPool.size();
    }

    /**
     * 新建连接
     */
    private ConnectionDelegation createConnection() throws SQLException {
        //新建连接返回
        Connection realConnection;
        if (connectionProperties != null) {
            realConnection = DriverManager.getConnection(url, connectionProperties);
        } else if (user != null && !user.isEmpty()) {
            realConnection = DriverManager.getConnection(url, user, password);
        } else {
            realConnection = DriverManager.getConnection(url);
        }

        logger.info("New connection created");
        return new ConnectionDelegation(realConnection);
    }

    /**
     * Returns 0, indicating the default system timeout is to be used.
     */
    @Override
    public int getLoginTimeout() {
        return 0;
    }

    /**
     * Setting a login timeout is not supported.
     */
    @Override
    public void setLoginTimeout(int timeout) {
        throw new UnsupportedOperationException("setLoginTimeout");
    }

    /**
     * LogWriter methods are not supported.
     */
    @Override
    public PrintWriter getLogWriter() {
        throw new UnsupportedOperationException("getLogWriter");
    }

    /**
     * LogWriter methods are not supported.
     */
    @Override
    public void setLogWriter(PrintWriter pw) {
        throw new UnsupportedOperationException("setLogWriter");
    }


    //---------------------------------------------------------------------
    // Implementation of JDBC 4.0's Wrapper interface
    //---------------------------------------------------------------------

    @Override
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return (T) this;
        }
        throw new SQLException("DataSource of type [" + getClass().getName() +
                "] cannot be unwrapped as [" + iface.getName() + "]");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) {
        return iface.isInstance(this);
    }


    //---------------------------------------------------------------------
    // Implementation of JDBC 4.1's getParentLogger method
    //---------------------------------------------------------------------

    @Override
    public Logger getParentLogger() {
        return Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    }

    private class ConnectionDelegation implements Connection {
        //被代理的连接对象
        private final Connection delegated;

        public ConnectionDelegation(Connection delegated) {
            this.delegated = delegated;
        }

        @Override
        public Statement createStatement() throws SQLException {
            return setupStatement(delegated.createStatement());
        }

        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            return setupStatement(delegated.prepareStatement(sql));
        }

        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            return setupStatement(delegated.prepareCall(sql));
        }

        @Override
        public String nativeSQL(String sql) throws SQLException {
            return delegated.nativeSQL(sql);
        }

        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException {
            delegated.setAutoCommit(autoCommit);
        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            return delegated.getAutoCommit();
        }

        @Override
        public void commit() throws SQLException {
            delegated.commit();
        }

        @Override
        public void rollback() throws SQLException {
            delegated.rollback();
        }

        @Override
        public void close() throws SQLException {
            tryRecycle(this);
        }

        @Override
        public boolean isClosed() throws SQLException {
            return delegated.isClosed();
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            return delegated.getMetaData();
        }

        @Override
        public void setReadOnly(boolean readOnly) throws SQLException {
            delegated.setReadOnly(readOnly);
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return delegated.isReadOnly();
        }

        @Override
        public void setCatalog(String catalog) throws SQLException {
            delegated.setCatalog(catalog);
        }

        @Override
        public String getCatalog() throws SQLException {
            return delegated.getCatalog();
        }

        @Override
        public void setTransactionIsolation(int level) throws SQLException {
            delegated.setTransactionIsolation(level);
        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            return delegated.getTransactionIsolation();
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return delegated.getWarnings();
        }

        @Override
        public void clearWarnings() throws SQLException {
            delegated.clearWarnings();
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            return setupStatement(delegated.createStatement(resultSetType, resultSetConcurrency));
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return setupStatement(delegated.prepareStatement(sql, resultSetType, resultSetConcurrency));
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return setupStatement(delegated.prepareCall(sql, resultSetType, resultSetConcurrency));
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return delegated.getTypeMap();
        }

        @Override
        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
            delegated.setTypeMap(map);
        }

        @Override
        public void setHoldability(int holdability) throws SQLException {
            delegated.setHoldability(holdability);
        }

        @Override
        public int getHoldability() throws SQLException {
            return delegated.getHoldability();
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            return delegated.setSavepoint();
        }

        @Override
        public Savepoint setSavepoint(String name) throws SQLException {
            return delegated.setSavepoint(name);
        }

        @Override
        public void rollback(Savepoint savepoint) throws SQLException {
            delegated.rollback(savepoint);
        }

        @Override
        public void releaseSavepoint(Savepoint savepoint) throws SQLException {
            delegated.releaseSavepoint(savepoint);
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return setupStatement(delegated.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability));
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return setupStatement(delegated.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return setupStatement(delegated.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            return setupStatement(delegated.prepareStatement(sql, autoGeneratedKeys));
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            return setupStatement(delegated.prepareStatement(sql, columnIndexes));
        }

        @Override
        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            return setupStatement(delegated.prepareStatement(sql, columnNames));
        }

        @Override
        public Clob createClob() throws SQLException {
            return delegated.createClob();
        }

        @Override
        public Blob createBlob() throws SQLException {
            return delegated.createBlob();
        }

        @Override
        public NClob createNClob() throws SQLException {
            return delegated.createNClob();
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            return delegated.createSQLXML();
        }

        @Override
        public boolean isValid(int timeout) throws SQLException {
            return delegated.isValid(timeout);
        }

        @Override
        public void setClientInfo(String name, String value) throws SQLClientInfoException {
            delegated.setClientInfo(name, value);
        }

        @Override
        public void setClientInfo(Properties properties) throws SQLClientInfoException {
            delegated.setClientInfo(properties);
        }

        @Override
        public String getClientInfo(String name) throws SQLException {
            return delegated.getClientInfo(name);
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            return delegated.getClientInfo();
        }

        @Override
        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            return delegated.createArrayOf(typeName, elements);
        }

        @Override
        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            return delegated.createStruct(typeName, attributes);
        }

        @Override
        public void setSchema(String schema) throws SQLException {
            delegated.setSchema(schema);
        }

        @Override
        public String getSchema() throws SQLException {
            return delegated.getSchema();
        }

        @Override
        public void abort(Executor executor) throws SQLException {
            delegated.abort(executor);
        }

        @Override
        public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
            delegated.setNetworkTimeout(executor, milliseconds);
        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            return delegated.getNetworkTimeout();
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return delegated.unwrap(iface);
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return delegated.isWrapperFor(iface);
        }

        public Connection getDelegated() {
            return delegated;
        }

        private <T extends Statement> T setupStatement(T statement) throws SQLException {
            if (queryTimeout > 0) {
                statement.setQueryTimeout(getQueryTimeout());
            }
            return statement;
        }
    }
}