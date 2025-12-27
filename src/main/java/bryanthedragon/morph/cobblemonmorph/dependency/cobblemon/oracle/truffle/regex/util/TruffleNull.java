package com.oracle.truffle.regex.util;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.regex.AbstractRegexObject;

@ExportLibrary(InteropLibrary.class)
public final class TruffleNull extends AbstractRegexObject {
   public static final TruffleNull INSTANCE = new TruffleNull();

   private TruffleNull() {
   }

   @ExportMessage
   boolean isNull() {
      return true;
   }

   @Override
   public String toString() {
      return "TRegexNullValue";
   }
}
