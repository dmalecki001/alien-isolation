package org.example.specification;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@FunctionalInterface
public interface JsonPayload {

  String getPayload();

  default <T> String convert(T object) {
    return JsonConverter.GSON.toJson(object);
  }

  class JsonConverter {

    private static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
        .serializeNulls()
        .setPrettyPrinting().create();

  }

}
