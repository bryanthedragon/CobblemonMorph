package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import java.util.List;

public final class CaseNode extends Node implements Terminal {
   private final Expression test;
   protected final List<Statement> statements;
   private final boolean terminal;

   public CaseNode(final long token, final int finish, final Expression test, List<Statement> statements) {
      super(token, finish);
      this.test = test;
      this.statements = List.copyOf(statements);
      this.terminal = isTerminal(statements);
   }

   CaseNode(final CaseNode caseNode, final int finish, final Expression test, final List<Statement> statements) {
      super(caseNode, finish);
      this.test = test;
      this.statements = List.copyOf(statements);
      this.terminal = isTerminal(statements);
   }

   private static boolean isTerminal(List<Statement> statements) {
      return statements.isEmpty() ? false : statements.get(statements.size() - 1).hasTerminalFlags();
   }

   @Override
   public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      if (visitor.enterCaseNode(this)) {
         Expression newTest = this.test == null ? null : (Expression)this.test.accept(visitor);
         List<Statement> newStatements = Node.accept(visitor, this.statements);
         return visitor.leaveCaseNode(this.setTest(newTest).setStatements(newStatements));
      } else {
         return this;
      }
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterCaseNode(this);
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printTypes) {
      if (this.test != null) {
         sb.append("case ");
         this.test.toString(sb, printTypes);
         sb.append(':');
      } else {
         sb.append("default:");
      }
   }

   @Override
   public boolean isTerminal() {
      return this.terminal;
   }

   public List<Statement> getStatements() {
      return this.statements;
   }

   public Expression getTest() {
      return this.test;
   }

   public CaseNode setTest(final Expression test) {
      return this.test == test ? this : new CaseNode(this, this.finish, test, this.statements);
   }

   public CaseNode setStatements(final List<Statement> statements) {
      if (this.statements == statements) {
         return this;
      } else {
         int lastFinish = 0;
         if (!statements.isEmpty()) {
            lastFinish = statements.get(statements.size() - 1).getFinish();
         }

         return new CaseNode(this, lastFinish, this.test, statements);
      }
   }
}
