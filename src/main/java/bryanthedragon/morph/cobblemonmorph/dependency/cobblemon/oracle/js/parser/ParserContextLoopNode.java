package com.oracle.js.parser;

class ParserContextLoopNode extends ParserContextBaseNode implements ParserContextBreakableNode {
   @Override
   public boolean isBreakableWithoutLabel() {
      return true;
   }
}
