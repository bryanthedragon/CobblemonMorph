package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;

public final class UnknownKeyException extends InteropException {
   private static final long serialVersionUID = 1857745390734085182L;
   private final Object unknownKey;

   private UnknownKeyException(Object unknownKey) {
      super(null);
      this.unknownKey = unknownKey;
   }

   private UnknownKeyException(Object unknownKey, Throwable cause) {
      super(null, cause);
      this.unknownKey = unknownKey;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String getMessage() {
      InteropLibrary interop = InteropLibrary.getUncached();

      try {
         return "Unknown identifier: " + interop.asString(interop.toDisplayString(this.unknownKey, false));
      } catch (UnsupportedMessageException var3) {
         throw CompilerDirectives.shouldNotReachHere(var3);
      }
   }

   public Object getUnknownKey() {
      return this.unknownKey;
   }

   public static UnknownKeyException create(Object unknownKey) {
      return new UnknownKeyException(unknownKey);
   }

   public static UnknownKeyException create(Object unknownKey, Throwable cause) {
      return new UnknownKeyException(unknownKey, cause);
   }
}
