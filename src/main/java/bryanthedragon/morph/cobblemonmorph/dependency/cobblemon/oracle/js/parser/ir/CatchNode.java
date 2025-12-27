package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class CatchNode extends Statement {
   private final IdentNode exception;
   private final Expression pattern;
   private final Expression exceptionCondition;
   private final Block body;
   private final boolean isSyntheticRethrow;

   public CatchNode(
      final int lineNumber,
      final long token,
      final int finish,
      final IdentNode exception,
      final Expression pattern,
      final Expression exceptionCondition,
      final Block body,
      final boolean isSyntheticRethrow
   ) {
      super(lineNumber, token, finish);
      this.exception = exception == null ? null : exception.setIsInitializedHere();
      this.pattern = pattern;
      this.exceptionCondition = exceptionCondition;
      this.body = body;
      this.isSyntheticRethrow = isSyntheticRethrow;
   }

   private CatchNode(
      final CatchNode catchNode,
      final IdentNode exception,
      final Expression pattern,
      final Expression exceptionCondition,
      final Block body,
      final boolean isSyntheticRethrow
   ) {
      super(catchNode);
      this.exception = exception;
      this.pattern = pattern;
      this.exceptionCondition = exceptionCondition;
      this.body = body;
      this.isSyntheticRethrow = isSyntheticRethrow;
   }

   @Override
   public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterCatchNode(this)
         ? visitor.leaveCatchNode(
            this.setException(this.exception == null ? null : (IdentNode)this.exception.accept(visitor))
               .setDestructuringPattern(this.pattern == null ? null : (Expression)this.pattern.accept(visitor))
               .setExceptionCondition(this.exceptionCondition == null ? null : (Expression)this.exceptionCondition.accept(visitor))
               .setBody((Block)this.body.accept(visitor))
         )
         : this);
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterCatchNode(this);
   }

   @Override
   public boolean isTerminal() {
      return this.body.isTerminal();
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printTypes) {
      sb.append(" catch (");
      if (this.pattern != null) {
         this.pattern.toString(sb, printTypes);
      } else {
         this.exception.toString(sb, printTypes);
      }

      if (this.exceptionCondition != null) {
         sb.append(" if ");
         this.exceptionCondition.toString(sb, printTypes);
      }

      sb.append(')');
   }

   public Expression getException() {
      return this.exception;
   }

   public Expression getExceptionCondition() {
      return this.exceptionCondition;
   }

   public CatchNode setExceptionCondition(final Expression exceptionCondition) {
      return this.exceptionCondition == exceptionCondition
         ? this
         : new CatchNode(this, this.exception, this.pattern, exceptionCondition, this.body, this.isSyntheticRethrow);
   }

   public Block getBody() {
      return this.body;
   }

   public CatchNode setException(final IdentNode exception) {
      return this.exception == exception ? this : new CatchNode(this, exception, this.pattern, this.exceptionCondition, this.body, this.isSyntheticRethrow);
   }

   public Expression getDestructuringPattern() {
      return this.pattern;
   }

   public CatchNode setDestructuringPattern(final Expression pattern) {
      return this.pattern == pattern ? this : new CatchNode(this, this.exception, pattern, this.exceptionCondition, this.body, this.isSyntheticRethrow);
   }

   private CatchNode setBody(final Block body) {
      return this.body == body ? this : new CatchNode(this, this.exception, this.pattern, this.exceptionCondition, body, this.isSyntheticRethrow);
   }

   public boolean isSyntheticRethrow() {
      return this.isSyntheticRethrow;
   }
}
