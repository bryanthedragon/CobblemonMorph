package com.oracle.js.parser.ir;

public abstract class OptionalExpression extends Expression {
   public OptionalExpression(final long token, final int start, final int finish) {
      super(token, start, finish);
   }

   public OptionalExpression(final long token, final int finish) {
      super(token, finish);
   }

   protected OptionalExpression(final OptionalExpression baseNode) {
      super(baseNode);
   }

   public abstract boolean isOptional();

   public abstract boolean isOptionalChain();
}
