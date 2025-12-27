package com.oracle.truffle.js.runtime.array;

final class ByteBufferSupport {
   private ByteBufferSupport() {
   }

   static ByteBufferAccess nativeOrder() {
      return NativeVarHandleByteBufferAccess.INSTANCE;
   }

   static ByteBufferAccess littleEndian() {
      return LittleEndianVarHandleByteBufferAccess.INSTANCE;
   }

   static ByteBufferAccess bigEndian() {
      return BigEndianVarHandleByteBufferAccess.INSTANCE;
   }
}
