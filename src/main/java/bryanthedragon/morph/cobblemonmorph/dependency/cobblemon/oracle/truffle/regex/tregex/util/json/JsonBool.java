package com.oracle.truffle.regex.tregex.util.json;

import java.io.PrintWriter;

public class JsonBool extends JsonValue {
   private final boolean value;

   JsonBool(boolean value) {
      this.value = value;
   }

   @Override
   public void dump(PrintWriter writer, int indent) {
      writer.print(this.value);
   }
}
