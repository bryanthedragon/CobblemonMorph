package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.source.SourceSection;

@ExportLibrary(value = InteropLibrary.class, receiverType = Double.class)
final class DefaultDoubleExports {
   @ExportMessage
   static boolean fitsInByte(Double receiver) {
      double d = receiver;
      byte b = (byte)d;
      return b == d && !NumberUtils.isNegativeZero(d);
   }

   @ExportMessage
   static boolean fitsInInt(Double receiver) {
      double d = receiver;
      int i = (int)d;
      return i == d && !NumberUtils.isNegativeZero(d);
   }

   @ExportMessage
   static boolean fitsInShort(Double receiver) {
      double d = receiver;
      short s = (short)d;
      return s == d && !NumberUtils.isNegativeZero(d);
   }

   @ExportMessage
   static boolean fitsInLong(Double receiver) {
      double d = receiver;
      long l = (long)d;
      return NumberUtils.inSafeIntegerRange(d) && !NumberUtils.isNegativeZero(d) && l == d;
   }

   @ExportMessage
   static boolean fitsInFloat(Double receiver) {
      double d = receiver;
      float f = (float)d;
      return !Double.isFinite(d) || f == d;
   }

   @ExportMessage
   static byte asByte(Double receiver) throws UnsupportedMessageException {
      double d = receiver;
      byte b = (byte)d;
      if (b == d && !NumberUtils.isNegativeZero(d)) {
         return b;
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   static short asShort(Double receiver) throws UnsupportedMessageException {
      double d = receiver;
      short s = (short)d;
      if (s == d && !NumberUtils.isNegativeZero(d)) {
         return s;
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   static int asInt(Double receiver) throws UnsupportedMessageException {
      double d = receiver;
      int i = (int)d;
      if (i == d && !NumberUtils.isNegativeZero(d)) {
         return i;
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   static long asLong(Double receiver) throws UnsupportedMessageException {
      double d = receiver;
      if (NumberUtils.inSafeIntegerRange(d) && !NumberUtils.isNegativeZero(d)) {
         long l = (long)d;
         if (l == d) {
            return l;
         }
      }

      throw UnsupportedMessageException.create();
   }

   @ExportMessage
   static float asFloat(Double receiver) throws UnsupportedMessageException {
      double d = receiver;
      float f = (float)d;
      if (Double.isFinite(d) && f != d) {
         throw UnsupportedMessageException.create();
      } else {
         return f;
      }
   }

   @ExportMessage
   static boolean isNumber(Double receiver) {
      return true;
   }

   @ExportMessage
   static boolean fitsInDouble(Double receiver) {
      return true;
   }

   @ExportMessage
   static double asDouble(Double receiver) {
      return receiver;
   }

   @ExportMessage
   static boolean hasLanguage(Double receiver) {
      return false;
   }

   @ExportMessage
   static Class<? extends TruffleLanguage<?>> getLanguage(Double receiver) throws UnsupportedMessageException {
      throw UnsupportedMessageException.create();
   }

   @ExportMessage
   static boolean hasSourceLocation(Double receiver) {
      return false;
   }

   @ExportMessage
   static SourceSection getSourceLocation(Double receiver) throws UnsupportedMessageException {
      throw UnsupportedMessageException.create();
   }

   @ExportMessage
   static boolean hasMetaObject(Double receiver) {
      return false;
   }

   @ExportMessage
   static Object getMetaObject(Double receiver) throws UnsupportedMessageException {
      throw UnsupportedMessageException.create();
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   static Object toDisplayString(Double receiver, boolean allowSideEffects) {
      return receiver.toString();
   }
}
