package com.tjpld.smileapp.config.utlis.jsonutils;

import com.google.gson.Gson;
import com.google.gson.JsonNull;

import java.lang.reflect.Type;

/**
 * Created ${guo} on ${2015/10/16}.
 */
public class JsonUtil {

    private static Gson gson = new Gson();
    static {
        if (gson == null) {
            gson = new Gson();
        }
    }


    public static String toJson(Object src) {
        if (src == null) {
            return gson.toJson(JsonNull.INSTANCE);
        }
        return gson.toJson(src);
    }


    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, (Type) classOfT);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        ResultEntity<T> t = null;
        t = gson.fromJson(json, typeOfT);
        return t.getResult();
    }


    public static <T> ResultEntity<T> fromJsonToResult(String json, Type typeOfT) {
        ResultEntity<T> t = gson.fromJson(json, typeOfT);
        return t;
    }


    public static <T> Boolean fromJson(Type typeOfT, String json) {
        ResultEntity<T> t = null;
        t = gson.fromJson(json, typeOfT);
        return t.isSuccess();
    }
}
