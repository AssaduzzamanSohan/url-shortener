package com.example.urlshortener.service;

public interface RedisService {

    Object get(String key, String hashKey);

    Object get(String key);

    void set(String key, String hashKey, String value);

    void set(String key, String value);
}
