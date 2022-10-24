package com.cassey.house.env;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cassey.house.common.utils.DateUtil;
import com.cassey.house.env.gateway.Gateway;
import com.cassey.house.env.gateway.OkHttpGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;
import java.util.function.Consumer;
import java.util.function.Function;

public class Environment {
    public final static TimeZone DEFAULT_TIME_ZONE = TimeZone.getTimeZone("PST");
    public final static DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public final static DateTimeFormatter LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final Logger logger = LoggerFactory.getLogger(Environment.class);

    private final String workspace;
    private final Database database;
    private final Gateway gateway;
    private final Properties config;

    public Environment(Properties config) {
        this.config = config;
        this.workspace = config.getProperty("application.workspace");
        this.database = new Database(this);
        this.gateway = new OkHttpGateway(this);
        //this.gateway = new JerseyGateway(this);
    }

    public String getProperty(String category) {
        return config.getProperty(category);
    }

    public static Environment on(String configFile) throws IOException {
        Properties config = loadConfig(configFile);
        TimeZone.setDefault(DEFAULT_TIME_ZONE);
        return new Environment(config);
    }

    private static Properties loadConfig(String file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        Properties config = new Properties();
        config.load(inputStream);
        inputStream.close();

        return config;
    }

    @SuppressWarnings("unchecked")
    public Environment execute(String commandClassName, String... args) throws Exception {
        Class<? extends Command> commandClass = (Class<? extends Command>) Class.forName(commandClassName);
        Command command = commandClass.newInstance();

        return this.execute(command, args);
    }

    public Environment execute(Class<? extends Command> commandClass, String... args) throws Exception {
        Command command = commandClass.newInstance();
        return this.execute(command, args);
    }

    public Environment execute(Command command, String... args) throws Exception {
        logger.info("Execute runner: {}", command.getName());
        long start = System.currentTimeMillis();

        try {
            command.setEnvironment(this);
            command.run(args);
            logger.info("Finished executing {} in {}!\n", command.getName(), DateUtil.formatCost(System.currentTimeMillis() - start));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            this.exit(-1);
        }

        return this;
    }

    public List<String> readFileByLines(String... paths) {
        Path path = this.getPath(paths);
        return readFileByLines(path);
    }

    public List<String> readFileByLines(Path path) {
        logger.info("Read file: {}", path.toString());
        try {
            return Files.readAllLines(path);
        } catch (Exception e) {
            throw new ExecutionException(e);
        }
    }

    public void readFile(String paths, Consumer<String> line) throws IOException {
        Path path = this.getPath(paths);
        logger.info("Read file: {}", path.toString());
        Files.readAllLines(path).forEach(line);
    }

    public String readFile(String... paths) {
        Path path = this.getPath(paths);
        return readFile(path);
    }

    public String readFile(Path path) {
        logger.info("Read file: {}", path.toString());
        try {
            return new String(Files.readAllBytes(path));
        } catch (Exception e) {
            throw new ExecutionException(e);
        }
    }

    public JSONObject loadJsonObject(String path) {
        return loadFile(path, JSONObject::parseObject);
    }

    public JSONArray loadJsonArray(String path) {
        return loadFile(path, JSONArray::parseArray);
    }

    public <T> T loadJsonObject(String path, Class<T> clazz) {
        return loadFile(path, s -> JSONObject.parseObject(s, clazz));
    }

    public <T> T loadJsonObject(String path, TypeReference<T> type) {
        return loadFile(path, s -> JSONObject.parseObject(s, type));
    }

    public <T> List<T> loadJsonArray(String path, Class<T> clazz) {
        return loadFile(path, s -> JSONArray.parseArray(s, clazz));
    }

    public <T> T loadFile(String path, Function<String, T> swapper) {
        String content = this.readFile(path);
        return swapper.apply(content);
    }

    public void writeFile(String name, byte[] data) {
        Path path = this.getPath(name);
        this.writeFile(path, data);
    }

    public void writeFile(Path path, byte[] data) {
        //logger.info("Write File: {}", path.toString());
        try {
            Files.write(path, data);
        } catch (Exception e) {
            throw new ExecutionException(e);
        }
    }

    public void writeFile(String name, String data) {
        this.writeFile(name, data.getBytes());
    }

    public void writeFile(Path path, String data) {
        this.writeFile(path, data.getBytes());
    }

    public void writeFile(String name, Collection<? extends CharSequence> data) {
        Path path = this.getPath(name);
        this.writeFile(path, data);
    }

    public void writeFile(Path path, Collection<? extends CharSequence> data) {
        if (data != null && !data.isEmpty()) {
            //logger.info("Write file: {}", path.toString());
            try {
                Files.write(path, data);
            } catch (Exception e) {
                throw new ExecutionException(e);
            }
        }
    }

    public void appendFileRow(String name, String rowData) {
        RandomAccessFile randomFile = null;
        try {
            randomFile = new RandomAccessFile(getPath(name).toString(), "rw");
            randomFile.seek(randomFile.length());
            randomFile.write(rowData.getBytes("UTF-8"));
            randomFile.writeBytes("\r\n");
        } catch (FileNotFoundException e) {
            logger.error("save data error", e);
        } catch (IOException e) {
            logger.error("save data error", e);
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getFile(String... parts) {
        if (parts == null || parts.length == 0) {
            return null;
        }

        StringBuilder builder = new StringBuilder().append(workspace);
        for (String part : parts) {
            builder.append(File.separator).append(part);
        }

        return builder.toString();
    }

    public Path getPath(String... parts) {
        return Paths.get(this.getFile(parts));
    }

    public Database database() {
        return database;
    }

    public Gateway gateway() {
        return gateway;
    }

    public void exit(int code) {
        database.close();
        if (code < 0) {
            logger.info("exit with errors");
        }
    }
}
