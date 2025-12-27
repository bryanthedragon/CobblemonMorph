package com.oracle.js.parser;

import com.oracle.js.parser.ir.Scope;

class ParserContextBlockNode extends ParserContextBaseNode implements ParserContextBreakableNode, ParserContextScopableNode {
   private final long token;
   private Scope scope;

   ParserContextBlockNode(final long token, Scope scope) {
      this.token = token;
      this.scope = scope;
   }

   @Override
   public boolean isBreakableWithoutLabel() {
      return false;
   }

   public long getToken() {
      return this.token;
   }

   @Override
   public Scope getScope() {
      return this.scope;
   }

   public void setScope(Scope scope) {
      this.scope = scope;
   }
}
