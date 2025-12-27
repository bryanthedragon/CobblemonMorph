package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;

public final class InvalidBufferOffsetException extends InteropException {
   private static final long serialVersionUID = 1857745390734085837L;
   private final long byteOffset;
   private final long length;

   private InvalidBufferOffsetException(long byteOffset, long length) {
      super(null);
      this.byteOffset = byteOffset;
      this.length = length;
   }

   private InvalidBufferOffsetException(long byteOffset, long length, Throwable cause) {
      super(null, cause);
      this.byteOffset = byteOffset;
      this.length = length;
   }

   public long getByteOffset() {
      return this.byteOffset;
   }

   public long getLength() {
      return this.length;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String getMessage() {
      return "Invalid buffer access of length " + this.length + " at byteOffset " + this.byteOffset + ".";
   }

   public static InvalidBufferOffsetException create(long byteOffset, long length) {
      return new InvalidBufferOffsetException(byteOffset, length);
   }

   public static InvalidBufferOffsetException create(long byteOffset, long length, Throwable cause) {
      return new InvalidBufferOffsetException(byteOffset, length, cause);
   }
}
