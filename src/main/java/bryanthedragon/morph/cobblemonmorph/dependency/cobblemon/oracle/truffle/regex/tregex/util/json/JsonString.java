package com.oracle.truffle.regex.tregex.util.json;

import java.io.PrintWriter;

public class JsonString extends JsonValue {
   private final String value;

   JsonString(String value) {
      this.value = value;
   }

   @Override
   public void dump(PrintWriter writer, int indent) {
      writer.print("\"");

      for (int i = 0; i < this.value.length(); i++) {
         char ch = this.value.charAt(i);
         if (ch < ' ') {
            if (ch == '\b') {
               writer.print("\\b");
            } else if (ch == '\f') {
               writer.print("\\f");
            } else if (ch == '\n') {
               writer.print("\\n");
            } else if (ch == '\r') {
               writer.print("\\r");
            } else if (ch == '\t') {
               writer.print("\\t");
            } else {
               writer.print("\\u00");
               writer.print(Character.forDigit(ch >> 4 & 15, 16));
               writer.print(Character.forDigit(ch & 15, 16));
            }
         } else if (ch == '\\') {
            writer.print("\\\\");
         } else if (ch == '"') {
            writer.print("\\\"");
         } else {
            writer.print(ch);
         }
      }

      writer.print("\"");
   }
}
