package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Strings;

public abstract class JSAbstractBuffer extends JSNonProxy {
   protected static final TruffleString BYTE_LENGTH = Strings.constant("byteLength");

   protected JSAbstractBuffer() {
   }

   public static boolean isJSAbstractBuffer(Object obj) {
      return obj instanceof JSArrayBufferObject;
   }
}
