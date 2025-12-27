package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value;

import com.bedrockk.molang.runtime.struct.ArrayStruct;
import com.bedrockk.molang.runtime.struct.VariableStruct;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.Map.Entry;

public interface MoValue {
   static MoValue of(Object value) {
      if (value instanceof JsonObject) {
         VariableStruct struct = new VariableStruct();

         for (Entry<String, JsonElement> entry : ((JsonObject)value).entrySet()) {
            struct.setDirectly(entry.getKey(), (com.bedrockk.molang.runtime.value.MoValue) of(entry.getValue()));
         }
         return (MoValue) struct;
      } 
      else if (value instanceof JsonPrimitive primitive) {
         if (primitive.isBoolean()) {
            return new DoubleValue(primitive.getAsBoolean());
         } 
         else {
            return (MoValue)(primitive.isNumber() ? new DoubleValue(primitive.getAsNumber()) : new StringValue(primitive.getAsString()));
         }
      } 
      else if (!(value instanceof JsonArray)) {
         return (MoValue)(value instanceof MoValue ? (MoValue)value : new DoubleValue(value));
      } 
      else {
         ArrayStruct struct = new ArrayStruct();
         int i = 0;

         for (JsonElement element : (JsonArray)value) {
            struct.setDirectly(String.valueOf(i), (com.bedrockk.molang.runtime.value.MoValue) of(element));
         }

         return (MoValue) struct;
      }
   }

   static JsonElement writeToJson(MoValue moValue) {
      if (moValue instanceof DoubleValue) {
         return new JsonPrimitive(((DoubleValue)moValue).value());
      } 
      else if (moValue instanceof StringValue) {
         return new JsonPrimitive(((StringValue)moValue).value());
      } 
      else if (moValue instanceof ArrayStruct) {
         JsonArray array = new JsonArray();
         for (com.bedrockk.molang.runtime.value.MoValue value : ((ArrayStruct)moValue).getMap().values()) {
            JsonElement element = writeToJson((MoValue) value);
            if (element != null) {
               array.add(element);
            }
         }
         return array;
      } 
      else if (moValue instanceof VariableStruct) {
         JsonObject object = new JsonObject();
         for (Entry<String, com.bedrockk.molang.runtime.value.MoValue> entry : ((VariableStruct)moValue).getMap().entrySet()) {
            JsonElement element = writeToJson((MoValue) entry.getValue());
            if (element != null) {
               object.add(entry.getKey(), element);
            }
         }
         return object;
      } 
      else {
         return null;
      }
   }

   Object value();

   default String asString() {
      return this.toString();
   }

   default double asDouble() {
      return 1.0;
   }
}
