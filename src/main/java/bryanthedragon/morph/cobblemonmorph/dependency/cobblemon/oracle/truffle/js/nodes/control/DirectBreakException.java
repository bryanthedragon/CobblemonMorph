package com.oracle.truffle.js.nodes.control;

public final class DirectBreakException extends BreakException {
   private static final long serialVersionUID = 9159010796978828438L;
   static final DirectBreakException instance = new DirectBreakException();

   private DirectBreakException() {
      super(0);
   }

   public DirectBreakException(int id) {
      super(id);
   }
}
