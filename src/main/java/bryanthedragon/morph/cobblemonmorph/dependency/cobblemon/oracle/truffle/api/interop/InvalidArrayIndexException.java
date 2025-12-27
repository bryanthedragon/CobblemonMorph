package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;

public final class InvalidArrayIndexException extends InteropException {
   private static final long serialVersionUID = 1857745390734085182L;
   private final long invalidIndex;

   private InvalidArrayIndexException(long invalidIndex) {
      super(null);
      this.invalidIndex = invalidIndex;
   }

   private InvalidArrayIndexException(long invalidIndex, Throwable cause) {
      super(null, cause);
      this.invalidIndex = invalidIndex;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String getMessage() {
      return "Invalid array index " + this.invalidIndex + ".";
   }

   public long getInvalidIndex() {
      return this.invalidIndex;
   }

   public static InvalidArrayIndexException create(long invalidIndex) {
      return new InvalidArrayIndexException(invalidIndex);
   }

   public static InvalidArrayIndexException create(long invalidIndex, Throwable cause) {
      return new InvalidArrayIndexException(invalidIndex, cause);
   }
}
