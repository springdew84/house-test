package com.cassey.house.env.cache;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cassey.house.env.Environment;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class CacheManager {
    private final ReentrantLock lock = new ReentrantLock();
    private final Map<String, Cache> cacheMap = new HashMap<>();
    private static CacheManager instance = null;

    private CacheManager() {
    }

    public static CacheManager getInstance() {
        if (instance == null) {
            synchronized (CacheManager.class) {
                if (instance == null) {
                    instance = new CacheManager();
                }
            }
        }

        return instance;
    }

    public Cache<JSONArray> getArrayCache(Environment environment, String name) {
        return getCache(environment, name);
    }

    public Cache<JSONObject> getObjectCache(Environment environment, String name) {
        return getCache(environment, name);
    }

    private Cache getCache(Environment environment, String name) {
        if(cacheMap.containsKey(name)) {
            return cacheMap.get(name);
        }

        ReentrantLock lock = this.lock;
        lock.lock();
        if(!cacheMap.containsKey(name)) {
            cacheMap.put(name, new DefaultCache<>(environment, name));
        }
        lock.unlock();

        return cacheMap.get(name);
    }
}
