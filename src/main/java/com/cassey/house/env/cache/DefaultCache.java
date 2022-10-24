package com.cassey.house.env.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cassey.house.env.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * default implements
 * FIFO cache
 * @param <T>
 */
public class DefaultCache<T> implements Cache<T> {
    private static final int MAX_SIZE = 100;
    private final ConcurrentMap<String, JSONObject> CACHE = new ConcurrentHashMap<>();
    private final ArrayBlockingQueue<String> KEYS = new ArrayBlockingQueue<>(MAX_SIZE);

    private final Environment environment;
    private String cacheName;

    public DefaultCache(Environment environment, String cacheName) {
        this.environment = environment;
        this.cacheName = cacheName;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T get(String key) {
        String md5 = key(key);
        JSONObject cache = CACHE.get(md5);
        if (cache == null) { // 内存中没有
            // 读取文件缓存
            Path path = environment.getPath(fileName(md5));
            if (Files.exists(path)) {
                cache = JSONObject.parseObject(environment.readFile(path));
                String cacheKey = cache.getString("key");
                if (!key.equals(cacheKey)) {
                    cache = null;
                }
            }
        }

        if (cache == null) {
            return null;
        }

        String className = cache.getString("data_type");
        String ds = cache.getString("data");
        if (ds == null) {
            return null;
        } else if (className.equals(JSONArray.class.getName())) {
            return (T) JSONArray.parseArray(ds);
        } else if (className.equals(JSONObject.class.getName())) {
            return (T) JSONObject.parseObject(ds);
        } else {
            Class<T> clazz;
            try {
                clazz = (Class<T>) Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("", e);
            }
            return JSON.parseObject(ds, clazz);
        }
    }

    @Override
    public void put(String key, T data) {
        JSONObject cache = new JSONObject();
        String md5 = key(key);
        cache.put("key", md5);

        if (data != null) {
            cache.put("data_type", data.getClass().getName());
            cache.put("data", data);
        }

        if (KEYS.size() > MAX_SIZE - 20) {
            String evictKey = KEYS.remove();
            CACHE.remove(evictKey);
        }

        CACHE.put(md5, cache);
        KEYS.add(md5);

        Path path = environment.getPath(filePath());
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        environment.writeFile(fileName(md5), cache.toJSONString());
    }

    private String key(String key) {
        return com.cassey.house.common.utils.codec.MD5.encode(key);
    }

    private String filePath() {
        return "cache/" + cacheName;
    }

    private String fileName(String md5) {
        return this.filePath() + "/" + md5 + ".json";
    }
}
