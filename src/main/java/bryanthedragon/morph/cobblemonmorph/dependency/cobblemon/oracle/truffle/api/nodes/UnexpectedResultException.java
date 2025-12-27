package com.oracle.truffle.api.nodes;

import com.oracle.truffle.api.CompilerDirectives;

public final class UnexpectedResultException extends SlowPathException {
   private static final long serialVersionUID = 3676602078425211386L;
   private final Object result;

   public UnexpectedResultException(Object result) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      this.result = result;
   }

   public Object getResult() {
      return this.result;
   }
}
