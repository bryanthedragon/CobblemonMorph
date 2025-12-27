package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.source.SourceSection;

@ExportLibrary(value = InteropLibrary.class, receiverType = Short.class)
final class DefaultShortExports {
   @ExportMessage
   static boolean isNumber(Short receiver) {
      return true;
   }

   @ExportMessage
   static boolean fitsInByte(Short receiver) {
      short s = receiver;
      byte b = (byte)s;
      return b == s;
   }

   @ExportMessage
   static byte asByte(Short receiver) throws UnsupportedMessageException {
      short s = receiver;
      byte b = (byte)s;
      if (b == s) {
         return b;
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   static boolean fitsInInt(Short receiver) {
      return true;
   }

   @ExportMessage
   static boolean fitsInShort(Short receiver) {
      return true;
   }

   @ExportMessage
   static boolean fitsInLong(Short receiver) {
      return true;
   }

   @ExportMessage
   static boolean fitsInFloat(Short receiver) {
      return true;
   }

   @ExportMessage
   static boolean fitsInDouble(Short receiver) {
      return true;
   }

   @ExportMessage
   static short asShort(Short receiver) {
      return receiver;
   }

   @ExportMessage
   static int asInt(Short receiver) {
      return receiver;
   }

   @ExportMessage
   static long asLong(Short receiver) {
      return receiver.shortValue();
   }

   @ExportMessage
   static float asFloat(Short receiver) {
      return receiver.shortValue();
   }

   @ExportMessage
   static double asDouble(Short receiver) {
      return receiver.shortValue();
   }

   @ExportMessage
   static boolean hasLanguage(Short receiver) {
      return false;
   }

   @ExportMessage
   static Class<? extends TruffleLanguage<?>> getLanguage(Short receiver) throws UnsupportedMessageException {
      throw UnsupportedMessageException.create();
   }

   @ExportMessage
   static boolean hasSourceLocation(Short receiver) {
      return false;
   }

   @ExportMessage
   static SourceSection getSourceLocation(Short receiver) throws UnsupportedMessageException {
      throw UnsupportedMessageException.create();
   }

   @ExportMessage
   static boolean hasMetaObject(Short receiver) {
      return false;
   }

   @ExportMessage
   static Object getMetaObject(Short receiver) throws UnsupportedMessageException {
      throw UnsupportedMessageException.create();
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   static Object toDisplayString(Short receiver, boolean allowSideEffects) {
      return receiver.toString();
   }
}
