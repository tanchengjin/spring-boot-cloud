package com.tanchengjin.test.util.throttle;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentUtil {
    private static final Map<String, Object> MAP = new ConcurrentHashMap<String, Object>();
    /**
     * 24小时(毫秒)
     */
    public static final long TTL_24H = 24 * 60 * 60 * 1000L;

    public static void put(String key, Object value) {
        put(key, value, TTL_24H);

    }

    public static void put(String key, Object value, long ttl) {
        MAP.put(key, value);
        MAP.put(key + "_ttl", System.currentTimeMillis() + ttl);
    }

    public static Object get(String key) {
        if (checkKeyIsExists(key)) {
            return MAP.get(key);
        }
        return null;
    }

    /**
     * 自增
     *
     * @param key
     * @return
     */
    public static Integer increment(String key) {
        return increment(key, TTL_24H);
    }

    public static Integer increment(String key, long ttl) {
        Integer value = null;
        if (checkKeyIsExists(key)) {
            value = (Integer) get(key);
            value = value == null ? 1 : value+1;
        } else {
            value = 1;
        }
        put(key, value, ttl);
        return value;
    }

    /**
     * 删除单个缓存
     *
     * @param key key in cache
     */
    public static void flush(String key) {
        MAP.remove(key);
        MAP.remove(key + "_ttl");
    }

    /**
     * 删除所有缓存
     */
    public static void flushAll() {
        MAP.clear();
    }

    public static boolean checkKeyIsExists(String key) {
        Long expired = (Long) MAP.get(key + "_ttl");
        if (expired == null || expired == 0L) {
            return false;
        }

        if (expired < System.currentTimeMillis()) {
            //cache expired
            flush(key);
            return false;
        }
        return true;
    }
}
