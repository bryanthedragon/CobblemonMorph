package com.oracle.truffle.js.runtime.array;

final class ByteArraySupport {
   private ByteArraySupport() {
   }

   static ByteArrayAccess littleEndian() {
      return VarHandleLittleEndianByteArrayAccess.INSTANCE;
   }

   static ByteArrayAccess bigEndian() {
      return VarHandleBigEndianByteArrayAccess.INSTANCE;
   }

   static ByteArrayAccess nativeOrder() {
      return VarHandleNativeOrderByteArrayAccess.INSTANCE;
   }
}
