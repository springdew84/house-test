package com.cassey.house.env.cache;

public interface Cache<T> {
    T get(String key);

    void put(String key, T data);
}
