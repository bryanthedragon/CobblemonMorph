
package com.oracle.truffle.regex.tregex.util.json;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.util.json.JsonArray;
import com.oracle.truffle.regex.tregex.util.json.JsonBool;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonInt;
import com.oracle.truffle.regex.tregex.util.json.JsonNull;
import com.oracle.truffle.regex.tregex.util.json.JsonObject;
import com.oracle.truffle.regex.tregex.util.json.JsonString;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
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
        ArrayList<JsonConvertible> list = new ArrayList<JsonConvertible>(array.length);
        for (char c : array) {
            list.add(Json.val(String.valueOf(c)));
        }
        return new JsonArray(list);
    }

    @CompilerDirectives.TruffleBoundary
    public static JsonValue array(short[] array) {
        ArrayList<JsonConvertible> list = new ArrayList<JsonConvertible>(array.length);
        for (short i : array) {
            list.add(Json.val(i));
        }
        return new JsonArray(list);
    }

    @CompilerDirectives.TruffleBoundary
    public static JsonValue array(int[] array) {
        ArrayList<JsonConvertible> list = new ArrayList<JsonConvertible>(array.length);
        for (int i : array) {
            list.add(Json.val(i));
        }
        return new JsonArray(list);
    }

    @CompilerDirectives.TruffleBoundary
    public static JsonArray array(JsonConvertible ... values) {
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
            return new JsonArray(new JsonConvertible[0]);
        }
        ArrayList<JsonConvertible> list = new ArrayList<JsonConvertible>(array.length);
        for (byte b : array) {
            list.add(Json.val(Byte.toUnsignedInt(b)));
        }
        return new JsonArray(list);
    }

    @CompilerDirectives.TruffleBoundary
    public static JsonObject obj(JsonObject.JsonObjectProperty ... properties2) {
        return new JsonObject(properties2);
    }

    @CompilerDirectives.TruffleBoundary
    public static JsonObject.JsonObjectProperty prop(String name, boolean value2) {
        return new JsonObject.JsonObjectProperty(name, Json.val(value2));
    }

    @CompilerDirectives.TruffleBoundary
    public static JsonObject.JsonObjectProperty prop(String name, int value2) {
        return new JsonObject.JsonObjectProperty(name, Json.val(value2));
    }

    @CompilerDirectives.TruffleBoundary
    public static JsonObject.JsonObjectProperty prop(String name, long value2) {
        return new JsonObject.JsonObjectProperty(name, Json.val(value2));
    }

    @CompilerDirectives.TruffleBoundary
    public static JsonObject.JsonObjectProperty prop(String name, String value2) {
        return new JsonObject.JsonObjectProperty(name, Json.val(value2));
    }

    @CompilerDirectives.TruffleBoundary
    public static JsonObject.JsonObjectProperty prop(String name, JsonConvertible value2) {
        return new JsonObject.JsonObjectProperty(name, value2);
    }

    @CompilerDirectives.TruffleBoundary
    public static JsonObject.JsonObjectProperty prop(String name, Iterable<? extends JsonConvertible> value2) {
        return new JsonObject.JsonObjectProperty(name, Json.array(value2));
    }

    @CompilerDirectives.TruffleBoundary
    public static JsonObject.JsonObjectProperty prop(String name, Stream<? extends JsonConvertible> value2) {
        return new JsonObject.JsonObjectProperty(name, Json.array(value2));
    }
}

