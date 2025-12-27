package com.oracle.js.parser.ir;

import com.oracle.js.parser.TokenType;

public abstract class BaseNode extends OptionalExpression implements FunctionCall {
   protected final Expression base;
   private final boolean isSuper;
   private final boolean optional;
   private final boolean optionalChain;
   private boolean isFunction;

   public BaseNode(final long token, final int finish, final Expression base, final boolean isSuper, final boolean optional, final boolean optionalChain) {
      super(token, base.getStart(), finish);
      this.base = base;
      this.isSuper = isSuper;
      this.optional = optional;
      this.optionalChain = optionalChain;

      assert (!isSuper || !optional) && (!optional || optionalChain);
   }

   protected BaseNode(final BaseNode baseNode, final Expression base, final boolean isSuper, final boolean optional, final boolean optionalChain) {
      super(baseNode);
      this.base = base;
      this.isSuper = isSuper;
      this.optional = optional;
      this.optionalChain = optionalChain;
      this.isFunction = baseNode.isFunction;
   }

   public Expression getBase() {
      return this.base;
   }

   @Override
   public boolean isFunction() {
      return this.isFunction;
   }

   public boolean isSuper() {
      return this.isSuper;
   }

   public boolean isIndex() {
      return this.isTokenType(TokenType.LBRACKET);
   }

   final BaseNode setIsFunction() {
      this.isFunction = true;
      return this;
   }

   public abstract BaseNode setIsSuper();

   @Override
   public final boolean isOptional() {
      return this.optional;
   }

   @Override
   public final boolean isOptionalChain() {
      return this.optionalChain;
   }
}
