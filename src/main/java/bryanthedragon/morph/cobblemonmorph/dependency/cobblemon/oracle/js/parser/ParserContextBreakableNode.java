package com.oracle.js.parser;

interface ParserContextBreakableNode extends ParserContextNode {
   boolean isBreakableWithoutLabel();
}
