package com.oracle.js.parser;

class ParserContextLabelNode extends ParserContextBaseNode {
   private final String name;

   ParserContextLabelNode(final String name) {
      this.name = name;
   }

   public String getLabelName() {
      return this.name;
   }
}
