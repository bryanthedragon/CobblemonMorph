package com.oracle.truffle.api.memory;

final class ByteArrayOutOfBoundsException extends IndexOutOfBoundsException {
   @Override
   public Throwable fillInStackTrace() {
      return this;
   }
}
