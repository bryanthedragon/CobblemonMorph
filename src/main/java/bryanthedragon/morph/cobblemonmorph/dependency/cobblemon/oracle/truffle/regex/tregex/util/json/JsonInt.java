package com.oracle.truffle.regex.tregex.util.json;

import java.io.PrintWriter;

public class JsonInt extends JsonValue {
   private final long value;

   JsonInt(int value) {
      this.value = value;
   }

   JsonInt(long value) {
      this.value = value;
   }

   @Override
   public void dump(PrintWriter writer, int indent) {
      writer.print(this.value);
   }
}
