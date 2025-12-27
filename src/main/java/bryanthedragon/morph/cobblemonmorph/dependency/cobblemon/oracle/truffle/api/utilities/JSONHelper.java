package com.oracle.truffle.api.utilities;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class JSONHelper {
   private JSONHelper() {
   }

   private static String quote(CharSequence value) {
      StringBuilder builder = new StringBuilder(value.length() + 2);
      builder.append('"');

      for (int i = 0; i < value.length(); i++) {
         char c = value.charAt(i);
         switch (c) {
            case '\b':
               builder.append("\\b");
               break;
            case '\t':
               builder.append("\\t");
               break;
            case '\n':
               builder.append("\\n");
               break;
            case '\f':
               builder.append("\\f");
               break;
            case '\r':
               builder.append("\\r");
               break;
            case '"':
               builder.append("\\\"");
               break;
            case '\\':
               builder.append("\\\\");
               break;
            default:
               if (c < ' ') {
                  builder.append("\\u00");
                  builder.append(Character.forDigit(c >> 4 & 15, 16));
                  builder.append(Character.forDigit(c & 15, 16));
               } else {
                  builder.append(c);
               }
         }
      }

      builder.append('"');
      return builder.toString();
   }

   public static JSONHelper.JSONObjectBuilder object() {
      return new JSONHelper.JSONObjectBuilder();
   }

   public static JSONHelper.JSONArrayBuilder array() {
      return new JSONHelper.JSONArrayBuilder();
   }

   public static final class JSONArrayBuilder extends JSONHelper.JSONStringBuilder {
      private final List<Object> contents = new ArrayList<>();

      private JSONArrayBuilder() {
      }

      public JSONHelper.JSONArrayBuilder add(String value) {
         this.contents.add(value);
         return this;
      }

      public JSONHelper.JSONArrayBuilder add(Number value) {
         this.contents.add(value);
         return this;
      }

      public JSONHelper.JSONArrayBuilder add(Boolean value) {
         this.contents.add(value);
         return this;
      }

      public JSONHelper.JSONArrayBuilder add(JSONHelper.JSONStringBuilder value) {
         this.contents.add(value);
         return this;
      }

      @Override
      protected void appendTo(StringBuilder sb) {
         sb.append("[");
         boolean comma = false;

         for (Object value : this.contents) {
            if (comma) {
               sb.append(", ");
            }

            appendValue(sb, value);
            comma = true;
         }

         sb.append("]");
      }
   }

   public static final class JSONObjectBuilder extends JSONHelper.JSONStringBuilder {
      private final Map<String, Object> contents = new LinkedHashMap<>();

      private JSONObjectBuilder() {
      }

      public JSONHelper.JSONObjectBuilder add(String key, String value) {
         this.contents.put(key, value);
         return this;
      }

      public JSONHelper.JSONObjectBuilder add(String key, Number value) {
         this.contents.put(key, value);
         return this;
      }

      public JSONHelper.JSONObjectBuilder add(String key, Boolean value) {
         this.contents.put(key, value);
         return this;
      }

      public JSONHelper.JSONObjectBuilder add(String key, JSONHelper.JSONStringBuilder value) {
         this.contents.put(key, value);
         return this;
      }

      @Override
      protected void appendTo(StringBuilder sb) {
         sb.append("{");
         boolean comma = false;

         for (Entry<String, Object> entry : this.contents.entrySet()) {
            if (comma) {
               sb.append(", ");
            }

            sb.append(JSONHelper.quote(entry.getKey()));
            sb.append(": ");
            appendValue(sb, entry.getValue());
            comma = true;
         }

         sb.append("}");
      }
   }

   public abstract static class JSONStringBuilder {
      private JSONStringBuilder() {
      }

      @Override
      public final String toString() {
         StringBuilder sb = new StringBuilder();
         this.appendTo(sb);
         return sb.toString();
      }

      protected abstract void appendTo(StringBuilder sb);

      protected static void appendValue(StringBuilder sb, Object value) {
         if (value instanceof JSONHelper.JSONStringBuilder) {
            ((JSONHelper.JSONStringBuilder)value).appendTo(sb);
         } else if (!(value instanceof Integer) && !(value instanceof Boolean) && value != null) {
            sb.append(JSONHelper.quote(String.valueOf(value)));
         } else {
            sb.append(value);
         }
      }
   }
}
