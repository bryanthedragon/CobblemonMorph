package com.oracle.truffle.regex.tregex.util.json;

import java.io.PrintWriter;

public final class JsonNull extends JsonValue {
   public static final JsonNull INSTANCE = new JsonNull();

   private JsonNull() {
   }

   @Override
   public void dump(PrintWriter writer, int indent) {
      writer.print("null");
   }
}
