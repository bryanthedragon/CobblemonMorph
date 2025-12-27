package com.oracle.truffle.regex.tregex.util.json;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;

public class JsonArray extends JsonValue {
   private final ArrayList<JsonConvertible> values;

   JsonArray(ArrayList<JsonConvertible> values) {
      this.values = values;
   }

   JsonArray(JsonConvertible... values) {
      this(new ArrayList<>());
      if (values != null) {
         Collections.addAll(this.values, values);
      }
   }

   JsonArray(Iterable<? extends JsonConvertible> values) {
      this(new ArrayList<>());
      if (values != null) {
         for (JsonConvertible v : values) {
            this.values.add(v);
         }
      }
   }

   JsonArray(Stream<? extends JsonConvertible> values) {
      this(new ArrayList<>());
      if (values != null) {
         values.forEach(this.values::add);
      }
   }

   public JsonArray append(JsonConvertible value) {
      this.values.add(value);
      return this;
   }

   @Override
   public void dump(PrintWriter writer, int indent) {
      writer.print("[");
      boolean first = true;

      for (JsonConvertible v : this.values) {
         if (first) {
            first = false;
         } else {
            writer.print(",");
         }

         writer.println();
         printIndent(writer, indent + 2);
         if (v == null) {
            Json.nullValue().dump(writer, indent + 2);
         } else {
            v.toJson().dump(writer, indent + 2);
         }
      }

      if (!first) {
         writer.println();
         printIndent(writer, indent);
      }

      writer.print("]");
   }
}
