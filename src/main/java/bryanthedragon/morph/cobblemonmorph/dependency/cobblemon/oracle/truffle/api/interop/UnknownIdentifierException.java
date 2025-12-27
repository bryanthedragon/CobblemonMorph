package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;

public final class UnknownIdentifierException extends InteropException {
   private static final long serialVersionUID = 1857745390734085182L;
   private final String unknownIdentifier;

   private UnknownIdentifierException(String unknownIdentifier) {
      super(null);
      this.unknownIdentifier = unknownIdentifier;
   }

   private UnknownIdentifierException(String unknownIdentifier, Throwable cause) {
      super(null, cause);
      this.unknownIdentifier = unknownIdentifier;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String getMessage() {
      return "Unknown identifier: " + this.unknownIdentifier;
   }

   public String getUnknownIdentifier() {
      return this.unknownIdentifier;
   }

   public static UnknownIdentifierException create(String unknownIdentifier) {
      return new UnknownIdentifierException(unknownIdentifier);
   }

   public static UnknownIdentifierException create(String unknownIdentifier, Throwable cause) {
      return new UnknownIdentifierException(unknownIdentifier, cause);
   }
}
