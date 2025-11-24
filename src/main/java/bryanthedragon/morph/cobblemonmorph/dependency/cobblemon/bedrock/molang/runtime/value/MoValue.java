/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.ArrayStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.VariableStruct;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.Map;

public interface MoValue {
    @SuppressWarnings("rawtypes")
    public static MoValue of(Object value2) {
        if (value2 instanceof JsonObject) {
            VariableStruct struct2 = new VariableStruct();
            for (Map.Entry entry : ((JsonObject)value2).entrySet()) {
                struct2.setDirectly((String)entry.getKey(), MoValue.of(entry.getValue()));
            }
            return struct2;
        }
        if (value2 instanceof JsonPrimitive) {
            JsonPrimitive primitive = (JsonPrimitive)value2;
            if (primitive.isBoolean()) {
                return new DoubleValue(primitive.getAsBoolean());
            }
            if (primitive.isNumber()) {
                return new DoubleValue(primitive.getAsNumber());
            }
            return new StringValue(primitive.getAsString());
        }
        if (value2 instanceof JsonArray) {
            ArrayStruct struct3 = new ArrayStruct();
            int i = 0;
            for (JsonElement element : (JsonArray)value2) {
                struct3.setDirectly(String.valueOf(i), MoValue.of(element));
            }
            return struct3;
        }
        if (value2 instanceof MoValue) {
            return (MoValue)value2;
        }
        return new DoubleValue(value2);
    }

    public static JsonElement writeToJson(MoValue moValue) {
        if (moValue instanceof DoubleValue) {
            return new JsonPrimitive((Number)((DoubleValue)moValue).value());
        }
        if (moValue instanceof StringValue) {
            return new JsonPrimitive(((StringValue)moValue).value());
        }
        if (moValue instanceof ArrayStruct) {
            JsonArray array = new JsonArray();
            for (MoValue value2 : ((ArrayStruct)moValue).getMap().values()) {
                JsonElement element = MoValue.writeToJson(value2);
                if (element == null) continue;
                array.add(element);
            }
            return array;
        }
        if (moValue instanceof VariableStruct) {
            JsonObject object = new JsonObject();
            for (Map.Entry<String, MoValue> entry : ((VariableStruct)moValue).getMap().entrySet()) {
                JsonElement element = MoValue.writeToJson(entry.getValue());
                if (element == null) continue;
                object.add(entry.getKey(), element);
            }
            return object;
        }
        return null;
    }

    public Object value();

    default public String asString() {
        return this.toString();
    }

    default public double asDouble() {
        return 1.0;
    }
}

