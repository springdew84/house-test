package com.cassey.house.env;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cassey.house.common.jdbc.SQLUtil;
import com.cassey.house.env.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.Closeable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Database implements Closeable {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    private static final String DEFAULT_DRIVER_CLASS = "com.mysql.jdbc.Driver";

    public static final String MALL = "mall";
    private final ReentrantLock lock = new ReentrantLock();

    private final Map<String, SimpleDataSource> dataSourceMap = new HashMap<>();

    private final Environment environment;

    Database(Environment environment) {
        this.environment = environment;
    }

    public StatementBuilder ugc() {
        return use("ugc");
    }

    public StatementBuilder mall() {
        return use("mall");
    }

    public StatementBuilder hive() {
        return use("hive");
    }

    public StatementBuilder ucenter() {
        return use("dm_ucenter");
    }

    public StatementBuilder dealmoon() {
        return use("dealmoon");
    }

    public StatementBuilder recdb() {
        return use("recdb");
    }

    public StatementBuilder use(String name) {
        if (!dataSourceMap.containsKey(name)) {
            ReentrantLock lock = this.lock;
            lock.lock();
            if (!dataSourceMap.containsKey(name)) {
                String url = environment.getProperty("jdbc." + name + ".url");
                if (url == null || url.isEmpty()) {
                    throw new NullPointerException("jdbc url for " + name + " not configured");
                }

                String driver = environment.getProperty("jdbc." + name + ".driver");
                if (driver == null || driver.isEmpty()) {
                    driver = DEFAULT_DRIVER_CLASS;
                }
                String user = environment.getProperty("jdbc." + name + ".user");
                String password = environment.getProperty("jdbc." + name + ".password");
                logger.info("Create DataSource {} with user {}", url, user);

                SimpleDataSource dataSource = new SimpleDataSource();
                dataSource.setDriverClass(driver);
                dataSource.setUrl(url);
                dataSource.setUser(user);
                dataSource.setPassword(password);
                dataSource.setQueryTimeout(10000);

                dataSourceMap.put(name, dataSource);
            }
            lock.unlock();
        }

        return new StatementBuilder(environment, dataSourceMap.get(name), name);
    }

    @Override
    public void close() {
        if (!dataSourceMap.isEmpty()) {
            logger.info("Release DataSources ......");
            Set<String> keys = new HashSet<>(dataSourceMap.keySet());
            keys.forEach(key -> {
                SimpleDataSource dataSource = dataSourceMap.get(key);
                try {
                    dataSource.close();
                    dataSourceMap.remove(key);
                    logger.info("Released DataSource {}", key);
                } catch (Exception e) {
                    logger.error("Error Release DataSources", e);
                }
            });
        }
    }

    public static class StatementBuilder {
        private final Environment environment;
        private final DataSource dataSource;
        private String sql;
        private boolean cacheEnabled = true;
        private Object[] args;
        private String cacheName;
        private String sourceName;

        StatementBuilder(Environment environment, DataSource dataSource, String sourceName) {
            this.environment = environment;
            this.dataSource = dataSource;
            this.sourceName = sourceName;
        }

        public StatementBuilder prepare(CharSequence sql) {
            this.sql = sql.toString();
            return this;
        }

        public StatementBuilder args(Object... args) {
            this.args = args;
            return this;
        }

        public StatementBuilder enableCache() {
            return enableCache(null);
        }

        public StatementBuilder enableCache(String cacheName) {
            this.cacheEnabled = true;
            this.cacheName = cacheName;
            return this;
        }

        public StatementBuilder disableCache() {
            this.cacheEnabled = false;
            return this;
        }

        public JSONArray list() {
            return list(true);
        }

        public JSONArray list(boolean printLog) {
            String formatted = SQLUtil.format(sql, args);
            if(printLog) logger.info(formatted);
            if (cacheEnabled) {
                JSONArray data = CacheManager.getInstance().getArrayCache(environment, this.name()).get(formatted);
                if (data != null) {
                    logger.info("return cached " + data.size() + " rows");
                    return data;
                }
            }

            try (Connection connection = this.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    this.setArgs(statement, args, 1);

                    try (ResultSet resultSet = statement.executeQuery()) {
                        //列
                        List<String> columns = new ArrayList<>();
                        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                            columns.add(resultSet.getMetaData().getColumnLabel(i));
                        }
                        //遍历数据行
                        JSONArray data = new JSONArray();
                        while (resultSet.next()) {
                            JSONObject object = new JSONObject();
                            for (String column : columns) {
                                object.put(column, resultSet.getObject(column));
                            }
                            data.add(object);
                        }

                        //logger.info("{} rows returned", data.size());
                        if(cacheEnabled) {
                            CacheManager.getInstance().getArrayCache(environment, this.name()).put(formatted, data);
                        }
                        return data;
                    }
                }
            } catch (Exception e) {
                throw new ExecutionException(e);
            }
        }

        private String name() {
            if(cacheName == null) {
                return sourceName;
            }

            return sourceName + "/" + cacheName;
        }

        public JSONObject findFirst() {
            return findFirst(true);
        }

        public JSONObject findFirst(boolean printLog) {
            String formatted = SQLUtil.format(sql, args);
            if(printLog) logger.info(formatted);

            if (cacheEnabled) {
                JSONObject cached = CacheManager.getInstance().getObjectCache(environment, this.name()).get(formatted);
                if (cached != null) {
                    logger.info("return cached: " + (cached.isEmpty() ? "null" : cached.toJSONString()));
                    return cached.isEmpty() ? null : cached;
                }
            }

            try (Connection connection = this.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    this.setArgs(statement, args, 1);

                    try (ResultSet resultSet = statement.executeQuery()) {
                        JSONObject object = null;

                        if (resultSet.next()) {
                            object = new JSONObject();
                            List<String> columns = new ArrayList<>();
                            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                                columns.add(resultSet.getMetaData().getColumnLabel(i));
                            }

                            for (String column : columns) {
                                object.put(column, resultSet.getObject(column));
                            }
                        }

                        if (cacheEnabled && object != null) {
                            CacheManager.getInstance().getObjectCache(environment, this.name()).put(formatted, object);
                        }

                        if (printLog) logger.info(object == null ? "null" : object.toJSONString());
                        return object;
                    }
                }
            } catch (Exception e) {
                throw new ExecutionException(e);
            }
        }

        public int update() {
            logger.info(SQLUtil.format(sql, args));

            try (Connection connection = this.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    this.setArgs(statement, args, 1);
                    int updated = statement.executeUpdate();

                    logger.info("{} rows affected", updated);
                    return updated;
                }
            } catch (Exception e) {
                throw new ExecutionException(e);
            }
        }

        private int setArgs(PreparedStatement statement, Object args, int index) {
            if (args != null) {
                int fromIndex = index;
                if (args instanceof Iterable) {
                    for (Object o : ((Iterable<?>) args)) {
                        fromIndex = this.setArgs(statement, o, fromIndex);
                    }
                } else if (args.getClass().isArray()) {
                    for (Object o : ((Object[]) args)) {
                        fromIndex = this.setArgs(statement, o, fromIndex);
                    }
                } else {
                    try {
                        statement.setObject(fromIndex++, args);
                    } catch (SQLException e) {
                        throw new ExecutionException(e);
                    }
                }

                return fromIndex;
            }
            return 0;
        }

        private Connection getConnection() {
            try {
                return dataSource.getConnection();
            } catch (SQLException e) {
                throw new ExecutionException(e);
            }
        }
    }
}
