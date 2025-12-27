package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.source.SourceSection;

@ExportLibrary(value = InteropLibrary.class, receiverType = Integer.class)
final class DefaultIntegerExports {
   @ExportMessage
   static boolean isNumber(Integer receiver) {
      return true;
   }

   @ExportMessage
   static boolean fitsInByte(Integer receiver) {
      int i = receiver;
      byte b = (byte)i;
      return b == i;
   }

   @ExportMessage
   static boolean fitsInShort(Integer receiver) {
      int i = receiver;
      short s = (short)i;
      return s == i;
   }

   @ExportMessage
   static boolean fitsInFloat(Integer receiver) {
      int i = receiver;
      float f = i;
      return (int)f == i;
   }

   @ExportMessage
   static byte asByte(Integer receiver) throws UnsupportedMessageException {
      int i = receiver;
      byte b = (byte)i;
      if (b == i) {
         return b;
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   static short asShort(Integer receiver) throws UnsupportedMessageException {
      int i = receiver;
      short s = (short)i;
      if (s == i) {
         return s;
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   static float asFloat(Integer receiver) throws UnsupportedMessageException {
      int i = receiver;
      float f = i;
      if ((int)f == i) {
         return f;
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   static boolean fitsInInt(Integer receiver) {
      return true;
   }

   @ExportMessage
   static boolean fitsInLong(Integer receiver) {
      return true;
   }

   @ExportMessage
   static boolean fitsInDouble(Integer receiver) {
      return true;
   }

   @ExportMessage
   static int asInt(Integer receiver) {
      return receiver;
   }

   @ExportMessage
   static long asLong(Integer receiver) {
      return receiver.intValue();
   }

   @ExportMessage
   static double asDouble(Integer receiver) {
      return receiver.intValue();
   }

   @ExportMessage
   static boolean hasLanguage(Integer receiver) {
      return false;
   }

   @ExportMessage
   static Class<? extends TruffleLanguage<?>> getLanguage(Integer receiver) throws UnsupportedMessageException {
      throw UnsupportedMessageException.create();
   }

   @ExportMessage
   static boolean hasSourceLocation(Integer receiver) {
      return false;
   }

   @ExportMessage
   static SourceSection getSourceLocation(Integer receiver) throws UnsupportedMessageException {
      throw UnsupportedMessageException.create();
   }

   @ExportMessage
   static boolean hasMetaObject(Integer receiver) {
      return false;
   }

   @ExportMessage
   static Object getMetaObject(Integer receiver) throws UnsupportedMessageException {
      throw UnsupportedMessageException.create();
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   static Object toDisplayString(Integer receiver, boolean allowSideEffects) {
      return receiver.toString();
   }
}
