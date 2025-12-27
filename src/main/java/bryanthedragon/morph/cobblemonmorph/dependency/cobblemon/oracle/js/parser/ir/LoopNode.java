package com.oracle.js.parser.ir;

public abstract class LoopNode extends BreakableStatement {
   protected final JoinPredecessorExpression test;
   protected final Block body;
   protected final boolean controlFlowEscapes;

   protected LoopNode(
      final int lineNumber, final long token, final int finish, final Block body, final JoinPredecessorExpression test, final boolean controlFlowEscapes
   ) {
      super(lineNumber, token, finish);
      this.body = body;
      this.controlFlowEscapes = controlFlowEscapes;
      this.test = test;
   }

   protected LoopNode(final LoopNode loopNode, final JoinPredecessorExpression test, final Block body, final boolean controlFlowEscapes) {
      super(loopNode);
      this.test = test;
      this.body = body;
      this.controlFlowEscapes = controlFlowEscapes;
   }

   @Override
   public boolean isTerminal() {
      if (!this.mustEnter()) {
         return false;
      } else if (this.controlFlowEscapes) {
         return false;
      } else {
         return this.body.isTerminal() ? true : this.test == null;
      }
   }

   public abstract boolean mustEnter();

   @Override
   public boolean isLoop() {
      return true;
   }

   public abstract Block getBody();

   public abstract LoopNode setBody(final LexicalContext lc, final Block body);

   public final JoinPredecessorExpression getTest() {
      return this.test;
   }

   public abstract LoopNode setTest(final LexicalContext lc, final JoinPredecessorExpression test);

   public abstract LoopNode setControlFlowEscapes(final LexicalContext lc, final boolean controlFlowEscapes);

   public abstract boolean hasPerIterationScope();

   @Override
   public boolean isCompletionValueNeverEmpty() {
      return true;
   }
}
