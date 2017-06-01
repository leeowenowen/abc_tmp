package com.owo.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public final class JsonUtils {
  private final static Gson GSON = new GsonBuilder().create();

  public static String toJson(Object object) {
    return GSON.toJson(object);
  }

  public static <T> T fromJson(String json, Class<T> cls) {
    return GSON.fromJson(json, cls);
  }

  public static <T> T fromJson(String json, Type type) {
    return GSON.fromJson(json, type);
  }

  public static Gson gson() {
    return GSON;
  }
}
