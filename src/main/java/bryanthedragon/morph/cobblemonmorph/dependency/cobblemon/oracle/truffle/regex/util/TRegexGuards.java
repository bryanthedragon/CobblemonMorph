package com.oracle.truffle.regex.util;

import com.oracle.truffle.api.strings.TruffleString;

public final class TRegexGuards {
   public static boolean neitherByteArrayNorString(Object obj) {
      return !(obj instanceof byte[]) && !(obj instanceof String) && !(obj instanceof TruffleString);
   }
}
