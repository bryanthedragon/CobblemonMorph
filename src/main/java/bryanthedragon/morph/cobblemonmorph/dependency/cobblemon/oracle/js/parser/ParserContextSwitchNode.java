package com.oracle.js.parser;

class ParserContextSwitchNode extends ParserContextBaseNode implements ParserContextBreakableNode {
   @Override
   public boolean isBreakableWithoutLabel() {
      return true;
   }
}
