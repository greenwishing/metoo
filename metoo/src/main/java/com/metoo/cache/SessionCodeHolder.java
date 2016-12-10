package com.metoo.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/10
 */
public class SessionCodeHolder {

    private static final Map<String, String> CACHED_CODE_MAP = new HashMap<>();

    public static void put(String sessionId, String code) {
        CACHED_CODE_MAP.put(sessionId, code);
    }

    public static String get(String sessionId) {
        return CACHED_CODE_MAP.get(sessionId);
    }

    public static void remove(String sessionId) {
        CACHED_CODE_MAP.remove(sessionId);
    }
}
