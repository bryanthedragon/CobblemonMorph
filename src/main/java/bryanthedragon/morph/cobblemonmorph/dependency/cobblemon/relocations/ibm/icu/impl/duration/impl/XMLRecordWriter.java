package com.cobblemon.mod.relocations.ibm.icu.impl.duration.impl;

import com.cobblemon.mod.relocations.ibm.icu.lang.UCharacter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class XMLRecordWriter implements RecordWriter {
   private Writer w;
   private List<String> nameStack;
   static final String NULL_NAME = "Null";
   private static final String INDENT = "    ";

   public XMLRecordWriter(Writer w) {
      this.w = w;
      this.nameStack = new ArrayList<>();
   }

   @Override
   public boolean open(String title) {
      this.newline();
      this.writeString("<" + title + ">");
      this.nameStack.add(title);
      return true;
   }

   @Override
   public boolean close() {
      int ix = this.nameStack.size() - 1;
      if (ix >= 0) {
         String name = this.nameStack.remove(ix);
         this.newline();
         this.writeString("</" + name + ">");
         return true;
      } else {
         return false;
      }
   }

   public void flush() {
      try {
         this.w.flush();
      } catch (IOException var2) {
      }
   }

   @Override
   public void bool(String name, boolean value) {
      this.internalString(name, String.valueOf(value));
   }

   @Override
   public void boolArray(String name, boolean[] values) {
      if (values != null) {
         String[] stringValues = new String[values.length];

         for (int i = 0; i < values.length; i++) {
            stringValues[i] = String.valueOf(values[i]);
         }

         this.stringArray(name, stringValues);
      }
   }

   private static String ctos(char value) {
      if (value == '<') {
         return "&lt;";
      } else {
         return value == '&' ? "&amp;" : String.valueOf(value);
      }
   }

   @Override
   public void character(String name, char value) {
      if (value != '\uffff') {
         this.internalString(name, ctos(value));
      }
   }

   @Override
   public void characterArray(String name, char[] values) {
      if (values != null) {
         String[] stringValues = new String[values.length];

         for (int i = 0; i < values.length; i++) {
            char value = values[i];
            if (value == '\uffff') {
               stringValues[i] = "Null";
            } else {
               stringValues[i] = ctos(value);
            }
         }

         this.internalStringArray(name, stringValues);
      }
   }

   @Override
   public void namedIndex(String name, String[] names, int value) {
      if (value >= 0) {
         this.internalString(name, names[value]);
      }
   }

   @Override
   public void namedIndexArray(String name, String[] names, byte[] values) {
      if (values != null) {
         String[] stringValues = new String[values.length];

         for (int i = 0; i < values.length; i++) {
            int value = values[i];
            if (value < 0) {
               stringValues[i] = "Null";
            } else {
               stringValues[i] = names[value];
            }
         }

         this.internalStringArray(name, stringValues);
      }
   }

   public static String normalize(String str) {
      if (str == null) {
         return null;
      } else {
         StringBuilder sb = null;
         boolean inWhitespace = false;
         char c = '\u0000';
         boolean special = false;

         for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (UCharacter.isWhitespace(c)) {
               if (sb == null && (inWhitespace || c != ' ')) {
                  sb = new StringBuilder(str.substring(0, i));
               }

               if (inWhitespace) {
                  continue;
               }

               inWhitespace = true;
               special = false;
               c = ' ';
            } else {
               inWhitespace = false;
               special = c == '<' || c == '&';
               if (special && sb == null) {
                  sb = new StringBuilder(str.substring(0, i));
               }
            }

            if (sb != null) {
               if (special) {
                  sb.append(c == '<' ? "&lt;" : "&amp;");
               } else {
                  sb.append(c);
               }
            }
         }

         return sb != null ? sb.toString() : str;
      }
   }

   private void internalString(String name, String normalizedValue) {
      if (normalizedValue != null) {
         this.newline();
         this.writeString("<" + name + ">" + normalizedValue + "</" + name + ">");
      }
   }

   private void internalStringArray(String name, String[] normalizedValues) {
      if (normalizedValues != null) {
         this.push(name + "List");

         for (int i = 0; i < normalizedValues.length; i++) {
            String value = normalizedValues[i];
            if (value == null) {
               value = "Null";
            }

            this.string(name, value);
         }

         this.pop();
      }
   }

   @Override
   public void string(String name, String value) {
      this.internalString(name, normalize(value));
   }

   @Override
   public void stringArray(String name, String[] values) {
      if (values != null) {
         this.push(name + "List");

         for (int i = 0; i < values.length; i++) {
            String value = normalize(values[i]);
            if (value == null) {
               value = "Null";
            }

            this.internalString(name, value);
         }

         this.pop();
      }
   }

   @Override
   public void stringTable(String name, String[][] values) {
      if (values != null) {
         this.push(name + "Table");

         for (int i = 0; i < values.length; i++) {
            String[] rowValues = values[i];
            if (rowValues == null) {
               this.internalString(name + "List", "Null");
            } else {
               this.stringArray(name, rowValues);
            }
         }

         this.pop();
      }
   }

   private void push(String name) {
      this.newline();
      this.writeString("<" + name + ">");
      this.nameStack.add(name);
   }

   private void pop() {
      int ix = this.nameStack.size() - 1;
      String name = this.nameStack.remove(ix);
      this.newline();
      this.writeString("</" + name + ">");
   }

   private void newline() {
      this.writeString("\n");

      for (int i = 0; i < this.nameStack.size(); i++) {
         this.writeString("    ");
      }
   }

   private void writeString(String str) {
      if (this.w != null) {
         try {
            this.w.write(str);
         } catch (IOException var3) {
            System.err.println(var3.getMessage());
            this.w = null;
         }
      }
   }
}
