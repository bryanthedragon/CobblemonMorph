package com.oracle.truffle.js.runtime.util;

import com.oracle.truffle.api.CompilerDirectives;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class DirectByteBufferHelper {
   private static final Class<? extends ByteBuffer> DIRECT_BYTE_BUFFER_CLASS = (Class<? extends ByteBuffer>)ByteBuffer.allocateDirect(0).getClass();

   private DirectByteBufferHelper() {
   }

   @CompilerDirectives.TruffleBoundary
   private static ByteBuffer allocateDirectImpl(int length) {
      return ByteBuffer.allocateDirect(length).order(ByteOrder.nativeOrder());
   }

   public static ByteBuffer allocateDirect(int length) {
      return cast(allocateDirectImpl(length));
   }

   public static ByteBuffer cast(ByteBuffer buffer) {
      return CompilerDirectives.castExact(buffer, DIRECT_BYTE_BUFFER_CLASS);
   }
}
