package com.cassey.house.env;


import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public abstract class Command {
    protected static final DateTimeFormatter date_time_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    protected static final SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

    protected final Logger logger = LoggerFactory.getLogger(this.getName());

    public static final Command EMPTY_COMMAND = new Command() {
        @Override
        public void run(String[] args) {
            logger.info("Will do nothing");
        }

        @Override
        public String getName() {
            return "Empty Command";
        }
    };

    protected Environment environment;

    public abstract void run(String[] args) throws Exception;

    void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getName() {
        return this.getClass().getName();
    }

    public String getFilePath(String name) {
        return getWorkspace() + File.separator + name;
    }

    protected String getWorkspace() {
        return "";
    }

    protected JSONArray loadJSONArrayFile(Path path) {
        if (!Files.exists(path)) {
            return new JSONArray();
        }

        return JSONArray.parseArray(environment.readFile(path));
    }
}
