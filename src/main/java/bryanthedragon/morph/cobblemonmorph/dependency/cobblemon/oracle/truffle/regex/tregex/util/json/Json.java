package com.oracle.truffle.regex.tregex.util.json;

import com.oracle.truffle.api.CompilerDirectives;
import java.util.ArrayList;
import java.util.stream.Stream;

public final class Json {
   @CompilerDirectives.TruffleBoundary
   public static JsonBool val(boolean val) {
      return new JsonBool(val);
   }

   @CompilerDirectives.TruffleBoundary
   public static JsonInt val(int val) {
      return new JsonInt(val);
   }

   @CompilerDirectives.TruffleBoundary
   public static JsonInt val(long val) {
      return new JsonInt(val);
   }

   @CompilerDirectives.TruffleBoundary
   public static JsonString val(String val) {
      return new JsonString(val);
   }

   @CompilerDirectives.TruffleBoundary
   public static JsonNull nullValue() {
      return JsonNull.INSTANCE;
   }

   @CompilerDirectives.TruffleBoundary
   public static JsonValue array(char[] array) {
      ArrayList<JsonConvertible> list = new ArrayList<>(array.length);

      for (char c : array) {
         list.add(val(String.valueOf(c)));
      }

      return new JsonArray(list);
   }

   @CompilerDirectives.TruffleBoundary
   public static JsonValue array(short[] array) {
      ArrayList<JsonConvertible> list = new ArrayList<>(array.length);

      for (int i : array) {
         list.add(val(i));
      }

      return new JsonArray(list);
   }

   @CompilerDirectives.TruffleBoundary
   public static JsonValue array(int[] array) {
      ArrayList<JsonConvertible> list = new ArrayList<>(array.length);

      for (int i : array) {
         list.add(val(i));
      }

      return new JsonArray(list);
   }

   @CompilerDirectives.TruffleBoundary
   public static JsonArray array(JsonConvertible... values) {
      return new JsonArray(values);
   }

   @CompilerDirectives.TruffleBoundary
   public static JsonArray array(Iterable<? extends JsonConvertible> values) {
      return new JsonArray(values);
   }

   @CompilerDirectives.TruffleBoundary
   public static JsonArray array(Stream<? extends JsonConvertible> values) {
      return new JsonArray(values);
   }

   @CompilerDirectives.TruffleBoundary
   public static JsonArray arrayUnsigned(byte[] array) {
      if (array == null) {
         return new JsonArray();
      } else {
         ArrayList<JsonConvertible> list = new ArrayList<>(array.length);

         for (byte b : array) {
            list.add(val(Byte.toUnsignedInt(b)));
         }

         return new JsonArray(list);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static JsonObject obj(JsonObject.JsonObjectProperty... properties) {
      return new JsonObject(properties);
   }

   @CompilerDirectives.TruffleBoundary
   public static JsonObject.JsonObjectProperty prop(String name, boolean value) {
      return new JsonObject.JsonObjectProperty(name, val(value));
   }

   @CompilerDirectives.TruffleBoundary
   public static JsonObject.JsonObjectProperty prop(String name, int value) {
      return new JsonObject.JsonObjectProperty(name, val(value));
   }

   @CompilerDirectives.TruffleBoundary
   public static JsonObject.JsonObjectProperty prop(String name, long value) {
      return new JsonObject.JsonObjectProperty(name, val(value));
   }

   @CompilerDirectives.TruffleBoundary
   public static JsonObject.JsonObjectProperty prop(String name, String value) {
      return new JsonObject.JsonObjectProperty(name, val(value));
   }

   @CompilerDirectives.TruffleBoundary
   public static JsonObject.JsonObjectProperty prop(String name, JsonConvertible value) {
      return new JsonObject.JsonObjectProperty(name, value);
   }

   @CompilerDirectives.TruffleBoundary
   public static JsonObject.JsonObjectProperty prop(String name, Iterable<? extends JsonConvertible> value) {
      return new JsonObject.JsonObjectProperty(name, array(value));
   }

   @CompilerDirectives.TruffleBoundary
   public static JsonObject.JsonObjectProperty prop(String name, Stream<? extends JsonConvertible> value) {
      return new JsonObject.JsonObjectProperty(name, array(value));
   }
}
