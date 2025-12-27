package com.oracle.truffle.api;

public class OptimizationFailedException extends RuntimeException {
   private final RootCallTarget callTarget;
   private static final long serialVersionUID = -8797188744430210785L;

   public OptimizationFailedException(Throwable cause, RootCallTarget callTarget) {
      super(cause);
      this.callTarget = callTarget;
   }

   public RootCallTarget getCallTarget() {
      return this.callTarget;
   }
}
