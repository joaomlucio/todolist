package com.desafio.todolist.helpers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class LocalDateTimeTypeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.n");

  @Override
  public LocalDateTime deserialize(JsonElement json, java.lang.reflect.Type typeOfT,
      JsonDeserializationContext context) throws JsonParseException {
    return LocalDateTime.parse(json.getAsString(), formatter);
  }

  @Override
  public JsonElement serialize(LocalDateTime src, java.lang.reflect.Type typeOfSrc,
      JsonSerializationContext context) {
    return new JsonPrimitive(formatter.format(src));
  }
}
