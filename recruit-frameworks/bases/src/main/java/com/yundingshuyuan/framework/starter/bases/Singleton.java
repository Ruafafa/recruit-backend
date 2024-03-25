package com.yundingshuyuan.framework.starter.bases;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * 单例对象容器
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Singleton {

    private static final ConcurrentHashMap<String, Object> SINGLE_OBJECT_POOL = new ConcurrentHashMap<>();

    /**
     * 根据 key 获取单例对象
     */
    public static <T> T get(String key) {
        return (T) SINGLE_OBJECT_POOL.get(key);
    }

    /**
     * 根据 key 获取单例对象
     *
     * <p> 为空时，通过 supplier 构建单例对象并放入容器
     */
    public static <T> T get(String key, Supplier<T> supplier) {
        return (T) SINGLE_OBJECT_POOL.computeIfAbsent(key, k -> supplier.get());
    }

    /**
     * 对象放入容器
     */
    public static void put(Object value) {
        Objects.requireNonNull(value, "value cannot be null");
        put(value.getClass().getName(), value);
    }

    /**
     * 对象放入容器
     */
    public static void put(String key, Object value) {
        Objects.requireNonNull(value, "value cannot be null");
        SINGLE_OBJECT_POOL.put(key, value);
    }
}