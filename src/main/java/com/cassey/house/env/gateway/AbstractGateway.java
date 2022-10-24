package com.cassey.house.env.gateway;

import com.alibaba.fastjson.JSONObject;
import com.cassey.house.env.Environment;
import com.cassey.house.env.cache.Cache;
import com.cassey.house.env.cache.DefaultCache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.twelvemonkeys.lang.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractGateway implements Gateway {
    private final static Logger logger = LoggerFactory.getLogger(AbstractGateway.class);

    private final Environment environment;
    protected List<String> hosts;
    protected String authorization;
    protected String token;
    protected static final ThreadLocalRandom random = ThreadLocalRandom.current();
    protected static Cache<JSONObject> cache;

    protected AbstractGateway(Environment environment) {
        this.environment = environment;
        this.authorization = environment.getProperty("gateway.authorization");
        this.token = environment.getProperty("gateway.token");
        cache = new DefaultCache<>(environment, "gateway");

        hosts = new ArrayList<>();
        String url = environment.getProperty("gateway.url");
        if (url.contains(";")) {
            String[] urlArray = url.split(";");
            for (String str : urlArray) {
                hosts.add(str);
            }
        } else {
            hosts.add(url);
        }
    }

    @Override
    public JSONObject getCached(String apiName) {
        JSONObject data = cache.get(apiName);
        if (data != null) {
            logger.info("return cached: " + data.toJSONString());
            return data;
        }

        data = this.get(apiName);
        cache.put(apiName, data);

        return data;
    }
}
