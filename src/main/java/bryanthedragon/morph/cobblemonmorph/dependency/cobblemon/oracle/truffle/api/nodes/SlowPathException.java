package com.oracle.truffle.api.nodes;

import com.oracle.truffle.api.CompilerDirectives;

public class SlowPathException extends Exception {
   private static final long serialVersionUID = 3676602078425211386L;

   public SlowPathException() {
      CompilerDirectives.transferToInterpreterAndInvalidate();
   }

   public SlowPathException(String message, Throwable cause) {
      super(message, cause);
      CompilerDirectives.transferToInterpreterAndInvalidate();
   }

   public SlowPathException(String message) {
      super(message);
      CompilerDirectives.transferToInterpreterAndInvalidate();
   }

   public SlowPathException(Throwable cause) {
      super(cause);
      CompilerDirectives.transferToInterpreterAndInvalidate();
   }

   @Deprecated(since = "19.0")
   @Override
   public final Throwable fillInStackTrace() {
      return this;
   }
}
