package com.metoo.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/10
 */
public class SessionEmailHolder {

    private static final Map<String, String> CACHED_SESSION_MAP = new HashMap<>();

    public static void put(String sessionId, String email) {
        CACHED_SESSION_MAP.put(sessionId, email);
    }

    public static String get(String sessionId) {
        return CACHED_SESSION_MAP.get(sessionId);
    }

    public static void remove(String sessionId) {
        CACHED_SESSION_MAP.remove(sessionId);
    }
}
