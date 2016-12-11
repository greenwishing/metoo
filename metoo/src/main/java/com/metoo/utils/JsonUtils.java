package com.metoo.utils;

import com.metoo.core.domain.product.ProductNotice;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;
import net.sf.json.util.PropertyFilter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/11
 */
public class JsonUtils {

    public static JsonConfig config(Class clazz) {
        return config(clazz, Collections.emptyMap());
    }

    public static JsonConfig config(Class clazz, Map classMap) {
        JsonConfig cfg = new JsonConfig();
        cfg.setRootClass(clazz);
        cfg.setJavaPropertyFilter(new JavaPropertyNameFilter());
        if (!classMap.isEmpty()) {
            cfg.setClassMap(classMap);
        }
        return cfg;
    }

    public static <T> String toJsonString(List<T> list) {
        return JSONArray.fromObject(list).toString();
    }

    public static <T> String toJsonString(T[] list) {
        return JSONArray.fromObject(list).toString();
    }

    public static <T> String toJsonString(T obj) {
        return JSONObject.fromObject(obj).toString();
    }

    public static <T> List<T> toBeanList(String listJson, Class<T> clazz) {
        List<T> beanList = new ArrayList<>();
        if (!mayBeJSONArray(listJson)) {
            return beanList;
        }
        return toBeanList(JSONArray.fromObject(listJson), clazz);
    }

    public static <T> List<T> toBeanList(JSONArray jsonArray, Class<T> clazz) {
        List<T> beanList = new ArrayList<>();
        JsonConfig config = JsonUtils.config(clazz);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            T notice = toBean(jsonObject, config);
            beanList.add(notice);
        }
        return beanList;
    }

    public static <T> T toBean(JSONObject jsonObject, Class<T> clazz) {
        return toBean(jsonObject, JsonUtils.config(clazz));
    }

    @SuppressWarnings("unchecked")
    public static <T> T toBean(JSONObject jsonObject, JsonConfig config) {
        return (T) JSONObject.toBean(jsonObject, config);
    }

    public static boolean mayBeJSONObject(String string) {
        return string != null && ("null".equals(string) || string.startsWith("{") && string.endsWith("}"));
    }

    public static boolean mayBeJSONArray(String string) {
        return string != null && ("null".equals(string) || string.startsWith("[") && string.endsWith("]"));
    }

    public static boolean mayBeJSON(String string) {
        return string != null && ("null".equals(string) || string.startsWith("[") && string.endsWith("]") || string.startsWith("{") && string.endsWith("}"));
    }

    public static class JavaPropertyNameFilter implements PropertyFilter {

        @Override
        public boolean apply(Object o, String s, Object o2) {
            Class<?> clazz = o.getClass();
            try {
                Field field = clazz.getDeclaredField(s);
                if (field == null) {
                    return true;
                }
            } catch (NoSuchFieldException e) {
                return true;
            }
            return false;
        }
    }
}
