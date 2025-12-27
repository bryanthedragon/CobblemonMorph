package com.oracle.js.parser.ir;

import com.oracle.truffle.api.strings.TruffleString;

public interface PropertyKey {
   String getPropertyName();

   TruffleString getPropertyNameTS();
}
