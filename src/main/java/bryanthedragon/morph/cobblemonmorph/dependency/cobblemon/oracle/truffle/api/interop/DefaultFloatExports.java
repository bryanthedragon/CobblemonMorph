package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.source.SourceSection;

@ExportLibrary(value = InteropLibrary.class, receiverType = Float.class)
final class DefaultFloatExports {
   @ExportMessage
   static boolean fitsInByte(Float receiver) {
      float f = receiver;
      byte b = (byte)f;
      return b == f && !NumberUtils.isNegativeZero(f);
   }

   @ExportMessage
   static boolean fitsInInt(Float receiver) {
      float f = receiver;
      if (NumberUtils.inSafeIntegerRange(f) && !NumberUtils.isNegativeZero(f)) {
         int i = (int)f;
         if (i == f) {
            return true;
         }
      }

      return false;
   }

   @ExportMessage
   static boolean fitsInShort(Float receiver) {
      float f = receiver;
      short s = (short)f;
      return s == f && !NumberUtils.isNegativeZero(f);
   }

   @ExportMessage
   static boolean fitsInLong(Float receiver) {
      float f = receiver;
      if (NumberUtils.inSafeIntegerRange(f) && !NumberUtils.isNegativeZero(f)) {
         long l = (long)f;
         if ((float)l == f) {
            return true;
         }
      }

      return false;
   }

   @ExportMessage
   static byte asByte(Float receiver) throws UnsupportedMessageException {
      float f = receiver;
      byte b = (byte)f;
      if (b == f && !NumberUtils.isNegativeZero(f)) {
         return b;
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   static short asShort(Float receiver) throws UnsupportedMessageException {
      float f = receiver;
      short s = (short)f;
      if (s == f && !NumberUtils.isNegativeZero(f)) {
         return s;
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   static int asInt(Float receiver) throws UnsupportedMessageException {
      float f = receiver;
      if (NumberUtils.inSafeIntegerRange(f) && !NumberUtils.isNegativeZero(f)) {
         int i = (int)f;
         if (i == f) {
            return i;
         }
      }

      throw UnsupportedMessageException.create();
   }

   @ExportMessage
   static long asLong(Float receiver) throws UnsupportedMessageException {
      float f = receiver;
      if (NumberUtils.inSafeIntegerRange(f) && !NumberUtils.isNegativeZero(f)) {
         long l = (long)f;
         if ((float)l == f) {
            return l;
         }
      }

      throw UnsupportedMessageException.create();
   }

   @ExportMessage
   static boolean isNumber(Float receiver) {
      return true;
   }

   @ExportMessage
   static boolean fitsInFloat(Float receiver) {
      return true;
   }

   @ExportMessage
   static float asFloat(Float receiver) {
      return receiver;
   }

   @ExportMessage
   static boolean fitsInDouble(Float receiver) {
      return true;
   }

   @ExportMessage
   static double asDouble(Float receiver) {
      return receiver.floatValue();
   }

   @ExportMessage
   static boolean hasLanguage(Float receiver) {
      return false;
   }

   @ExportMessage
   static Class<? extends TruffleLanguage<?>> getLanguage(Float receiver) throws UnsupportedMessageException {
      throw UnsupportedMessageException.create();
   }

   @ExportMessage
   static boolean hasSourceLocation(Float receiver) {
      return false;
   }

   @ExportMessage
   static SourceSection getSourceLocation(Float receiver) throws UnsupportedMessageException {
      throw UnsupportedMessageException.create();
   }

   @ExportMessage
   static boolean hasMetaObject(Float receiver) {
      return false;
   }

   @ExportMessage
   static Object getMetaObject(Float receiver) throws UnsupportedMessageException {
      throw UnsupportedMessageException.create();
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   static Object toDisplayString(Float receiver, boolean allowSideEffects) {
      return receiver.toString();
   }
}
