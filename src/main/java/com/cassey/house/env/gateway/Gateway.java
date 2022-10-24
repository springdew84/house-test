package com.cassey.house.env.gateway;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface Gateway {
    JSONObject get(String apiName);

    JSONObject getCached(String apiName);

    JSONObject post(String apiName, Object data);

    JSONObject put(String apiName, Object data);

    JSONObject delete(String apiName);

    JSONObject dmsdk(Map<String, Object> command);
}
