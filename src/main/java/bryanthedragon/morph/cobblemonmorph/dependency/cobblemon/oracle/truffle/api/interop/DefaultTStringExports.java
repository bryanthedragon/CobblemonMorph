package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;

@ExportLibrary(value = InteropLibrary.class, receiverType = TruffleString.class)
final class DefaultTStringExports {
   @ExportMessage
   static boolean isString(TruffleString receiver) {
      return true;
   }

   @ExportMessage
   static String asString(TruffleString receiver, @Cached TruffleString.ToJavaStringNode toJavaStringNode) {
      return toJavaStringNode.execute(receiver);
   }

   @ExportMessage
   static TruffleString asTruffleString(TruffleString receiver) {
      return receiver;
   }

   @ExportMessage
   static boolean hasLanguage(TruffleString receiver) {
      return false;
   }

   @ExportMessage
   static Class<? extends TruffleLanguage<?>> getLanguage(TruffleString receiver) throws UnsupportedMessageException {
      throw UnsupportedMessageException.create();
   }

   @ExportMessage
   static boolean hasSourceLocation(TruffleString receiver) {
      return false;
   }

   @ExportMessage
   static SourceSection getSourceLocation(TruffleString receiver) throws UnsupportedMessageException {
      throw UnsupportedMessageException.create();
   }

   @ExportMessage
   static boolean hasMetaObject(TruffleString receiver) {
      return false;
   }

   @ExportMessage
   static Object getMetaObject(TruffleString receiver) throws UnsupportedMessageException {
      throw UnsupportedMessageException.create();
   }

   @ExportMessage
   static Object toDisplayString(TruffleString receiver, boolean allowSideEffects) {
      return receiver;
   }
}
