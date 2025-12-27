package com.oracle.truffle.js.runtime.builtins.wasm;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Strings;

public final class JSWebAssemblyValueTypes {
   public static final TruffleString I32 = Strings.I_32;
   public static final TruffleString I64 = Strings.I_64;
   public static final TruffleString F32 = Strings.F_32;
   public static final TruffleString F64 = Strings.F_64;

   public static boolean isI32(TruffleString type) {
      return Strings.equals(I32, type);
   }

   public static boolean isI64(TruffleString type) {
      return Strings.equals(I64, type);
   }

   public static boolean isF32(TruffleString type) {
      return Strings.equals(F32, type);
   }

   public static boolean isF64(TruffleString type) {
      return Strings.equals(F64, type);
   }

   public static boolean isValueType(TruffleString type) {
      return isI32(type) || isI64(type) || isF32(type) || isF64(type);
   }
}
