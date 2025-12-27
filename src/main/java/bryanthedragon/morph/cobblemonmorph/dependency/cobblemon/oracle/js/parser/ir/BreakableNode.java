package com.oracle.js.parser.ir;

public interface BreakableNode extends LexicalContextNode {
   boolean isBreakableWithoutLabel();
}
