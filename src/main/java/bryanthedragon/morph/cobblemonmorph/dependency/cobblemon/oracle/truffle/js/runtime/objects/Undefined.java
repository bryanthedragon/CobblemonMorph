package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Strings;

public final class Undefined {
   public static final TruffleString NAME = Strings.UNDEFINED;
   public static final TruffleString TYPE_NAME = NAME;
   public static final JSDynamicObject instance = new Nullish();

   private Undefined() {
   }
}
