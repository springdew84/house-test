package com.cassey.house.env.gateway;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cassey.house.env.Environment;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class OkHttpGateway extends AbstractGateway implements Gateway {
    private final static Logger logger = LoggerFactory.getLogger(OkHttpGateway.class);

    private static final OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    public OkHttpGateway(Environment environment) {
        super(environment);
    }

    @Override
    public JSONObject get(String apiName) {
        return request(apiName, builder -> builder.get().build());
    }

    @Override
    public JSONObject post(String apiName, Object data) {
        return request(apiName, builder -> {
            //logger.info("Post data: {}", JSON.toJSONString(data));
            String json = JSON.toJSONString(data);
            RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
            return builder.post(requestBody).build();
        });
    }

    @Override
    public JSONObject put(String apiName, Object data) {
        return request(apiName, builder -> {
            logger.info("Put data: {}", JSON.toJSONString(data));

            RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(data));
            return builder.put(requestBody).build();
        });
    }

    @Override
    public JSONObject delete(String apiName) {
        return request(apiName, builder -> builder.delete().build());
    }

    @Override
    public JSONObject dmsdk(Map<String, Object> command) {
        return post("/dmsdk", command);
    }

    private JSONObject request(String apiName, Function<Request.Builder, Request> executor) {
        String url;
        if (apiName.contains("http")) {
            url = apiName;
        } else {
            if (hosts.size() > 1) {
                //随机获取节点
                int index = ThreadLocalRandom.current().nextInt(100000) % hosts.size();
                url = hosts.get(index) + apiName;
            } else {
                url = hosts.get(0) + apiName;
            }
        }

        Request.Builder builder = new Request.Builder();
        builder.url(url).header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36");
        if (StringUtils.isNotEmpty(authorization)) builder.header("x-dm-authorization", authorization);
        if (StringUtils.isNotEmpty(token)) builder.header("token", token).header("x-dm-token", token);

        Request request = executor.apply(builder);
        try {
            Response response = httpClient.newCall(request).execute();

            if (response.code() == 200) {
                String responseText = response.body().string();
                //logger.info("Response: {}", responseText);
                return JSONObject.parseObject(responseText);
            } else {
                logger.error("Response: {}:{}", response.code(), response.message());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new JSONObject();
    }
}
