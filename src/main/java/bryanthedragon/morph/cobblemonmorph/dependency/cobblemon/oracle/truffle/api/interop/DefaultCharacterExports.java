package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.source.SourceSection;

@ExportLibrary(value = InteropLibrary.class, receiverType = Character.class)
final class DefaultCharacterExports {
   @ExportMessage
   static boolean isString(Character receiver) {
      return true;
   }

   @CompilerDirectives.TruffleBoundary
   @ExportMessage
   static String asString(Character receiver) {
      return receiver.toString();
   }

   @ExportMessage
   static boolean hasLanguage(Character receiver) {
      return false;
   }

   @ExportMessage
   static Class<? extends TruffleLanguage<?>> getLanguage(Character receiver) throws UnsupportedMessageException {
      throw UnsupportedMessageException.create();
   }

   @ExportMessage
   static boolean hasSourceLocation(Character receiver) {
      return false;
   }

   @ExportMessage
   static SourceSection getSourceLocation(Character receiver) throws UnsupportedMessageException {
      throw UnsupportedMessageException.create();
   }

   @ExportMessage
   static boolean hasMetaObject(Character receiver) {
      return false;
   }

   @ExportMessage
   static Object getMetaObject(Character receiver) throws UnsupportedMessageException {
      throw UnsupportedMessageException.create();
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   static Object toDisplayString(Character receiver, boolean allowSideEffects) {
      return receiver.toString();
   }
}
