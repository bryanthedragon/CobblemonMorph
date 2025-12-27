package com.oracle.truffle.regex.tregex.util.json;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class JsonObject extends JsonValue {
   private final ArrayList<JsonObject.JsonObjectProperty> properties = new ArrayList<>();

   JsonObject(JsonObject.JsonObjectProperty... props) {
      Collections.addAll(this.properties, props);
   }

   public JsonObject append(JsonObject.JsonObjectProperty... props) {
      Collections.addAll(this.properties, props);
      return this;
   }

   @Override
   public void dump(PrintWriter writer, int indent) {
      writer.println("{");
      boolean first = true;

      for (JsonObject.JsonObjectProperty p : this.properties) {
         if (first) {
            first = false;
         } else {
            writer.println(",");
         }

         printIndent(writer, indent + 2);
         writer.print('"');
         writer.print(p.name);
         writer.print("\": ");
         if (p.value == null) {
            Json.nullValue().dump(writer, indent + 2);
         } else {
            p.value.toJson().dump(writer, indent + 2);
         }
      }

      writer.println();
      printIndent(writer, indent);
      writer.print("}");
   }

   public static class JsonObjectProperty {
      private final String name;
      private final JsonConvertible value;

      JsonObjectProperty(String name, JsonConvertible value) {
         this.name = name;
         this.value = value;
      }
   }
}
