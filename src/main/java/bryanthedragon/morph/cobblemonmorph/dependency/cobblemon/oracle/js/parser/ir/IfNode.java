package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class IfNode extends Statement {
   private final Expression test;
   private final Block pass;
   private final Block fail;

   public IfNode(final int lineNumber, final long token, final int finish, final Expression test, final Block pass, final Block fail) {
      super(lineNumber, token, finish);
      this.test = test;
      this.pass = pass;
      this.fail = fail;
   }

   private IfNode(final IfNode ifNode, final Expression test, final Block pass, final Block fail) {
      super(ifNode);
      this.test = test;
      this.pass = pass;
      this.fail = fail;
   }

   @Override
   public boolean isTerminal() {
      return this.pass.isTerminal() && this.fail != null && this.fail.isTerminal();
   }

   @Override
   public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterIfNode(this)
         ? visitor.leaveIfNode(
            this.setTest((Expression)this.test.accept(visitor))
               .setPass((Block)this.pass.accept(visitor))
               .setFail(this.fail == null ? null : (Block)this.fail.accept(visitor))
         )
         : this);
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterIfNode(this);
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printTypes) {
      sb.append("if (");
      this.test.toString(sb, printTypes);
      sb.append(')');
   }

   public Block getFail() {
      return this.fail;
   }

   private IfNode setFail(final Block fail) {
      return this.fail == fail ? this : new IfNode(this, this.test, this.pass, fail);
   }

   public Block getPass() {
      return this.pass;
   }

   private IfNode setPass(final Block pass) {
      return this.pass == pass ? this : new IfNode(this, this.test, pass, this.fail);
   }

   public Expression getTest() {
      return this.test;
   }

   public IfNode setTest(final Expression test) {
      return this.test == test ? this : new IfNode(this, test, this.pass, this.fail);
   }

   @Override
   public boolean isCompletionValueNeverEmpty() {
      return true;
   }
}
