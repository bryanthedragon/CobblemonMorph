package com.oracle.truffle.js.parser.json;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.js.runtime.Strings;

public class JSONParserUtil {
   public static TruffleString quote(final TruffleString value) {
      TruffleStringBuilder product = Strings.builderCreate();
      Strings.builderAppend(product, Strings.DOUBLE_QUOTE);
      int len = Strings.length(value);

      for (int i = 0; i < len; i++) {
         char ch = Strings.charAt(value, i);
         if (ch >= ' ') {
            if (ch == '\\') {
               Strings.builderAppend(product, Strings.BACKSLASH_BACKSLASH);
            } else if (ch == '"') {
               Strings.builderAppend(product, Strings.BACKSLASH_DOUBLE_QUOTE);
            } else {
               Strings.builderAppend(product, ch);
            }
         } else if (ch == '\b') {
            Strings.builderAppend(product, Strings.BACKSLASH_B);
         } else if (ch == '\f') {
            Strings.builderAppend(product, Strings.BACKSLASH_F);
         } else if (ch == '\n') {
            Strings.builderAppend(product, Strings.BACKSLASH_N);
         } else if (ch == '\r') {
            Strings.builderAppend(product, Strings.BACKSLASH_R);
         } else if (ch == '\t') {
            Strings.builderAppend(product, Strings.BACKSLASH_T);
         } else {
            Strings.builderAppend(product, Strings.BACKSLASH_U);
            TruffleString hex = Strings.intToHexString(ch);

            for (int j = Strings.length(hex); j < 4; j++) {
               Strings.builderAppend(product, '0');
            }

            Strings.builderAppend(product, hex);
         }
      }

      Strings.builderAppend(product, Strings.DOUBLE_QUOTE);
      return Strings.builderToString(product);
   }
}
