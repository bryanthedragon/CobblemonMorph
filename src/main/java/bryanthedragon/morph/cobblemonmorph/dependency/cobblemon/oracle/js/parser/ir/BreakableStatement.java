package com.oracle.js.parser.ir;

abstract class BreakableStatement extends LexicalContextStatement implements BreakableNode {
   protected BreakableStatement(final int lineNumber, final long token, final int finish) {
      super(lineNumber, token, finish);
   }

   protected BreakableStatement(final BreakableStatement breakableNode) {
      super(breakableNode);
   }

   @Override
   public boolean isBreakableWithoutLabel() {
      return true;
   }
}
