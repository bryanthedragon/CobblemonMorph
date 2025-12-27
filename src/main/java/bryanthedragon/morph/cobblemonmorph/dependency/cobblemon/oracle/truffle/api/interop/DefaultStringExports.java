package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;

@ExportLibrary(value = InteropLibrary.class, receiverType = String.class)
final class DefaultStringExports {
   @ExportMessage
   static boolean isString(String receiver) {
      return true;
   }

   @ExportMessage
   static String asString(String receiver) {
      return receiver;
   }

   @ExportMessage
   static TruffleString asTruffleString(String receiver, @Cached TruffleString.FromJavaStringNode fromJavaStringNode) {
      return fromJavaStringNode.execute(receiver, TruffleString.Encoding.UTF_16);
   }

   @ExportMessage
   static boolean hasLanguage(String receiver) {
      return false;
   }

   @ExportMessage
   static Class<? extends TruffleLanguage<?>> getLanguage(String receiver) throws UnsupportedMessageException {
      throw UnsupportedMessageException.create();
   }

   @ExportMessage
   static boolean hasSourceLocation(String receiver) {
      return false;
   }

   @ExportMessage
   static SourceSection getSourceLocation(String receiver) throws UnsupportedMessageException {
      throw UnsupportedMessageException.create();
   }

   @ExportMessage
   static boolean hasMetaObject(String receiver) {
      return false;
   }

   @ExportMessage
   static Object getMetaObject(String receiver) throws UnsupportedMessageException {
      throw UnsupportedMessageException.create();
   }

   @ExportMessage
   static Object toDisplayString(String receiver, boolean allowSideEffects) {
      return receiver;
   }
}
