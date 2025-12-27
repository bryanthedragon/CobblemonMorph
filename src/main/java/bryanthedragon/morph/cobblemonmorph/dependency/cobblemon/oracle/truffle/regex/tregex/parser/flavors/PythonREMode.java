package com.oracle.truffle.regex.tregex.parser.flavors;

import com.oracle.truffle.regex.tregex.string.Encodings;

public enum PythonREMode {
   None,
   Str,
   Bytes;

   public static PythonREMode fromEncoding(Encodings.Encoding encoding) {
      return encoding == Encodings.LATIN_1 ? Bytes : Str;
   }
}
