package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import java.util.List;

public final class SwitchNode extends BreakableStatement {
   private final Expression expression;
   private final List<CaseNode> cases;
   private final int defaultCaseIndex;
   private Symbol tag;

   public SwitchNode(
      final int lineNumber, final long token, final int finish, final Expression expression, final List<CaseNode> cases, final int defaultCaseIndex
   ) {
      super(lineNumber, token, finish);
      this.expression = expression;
      this.cases = List.copyOf(cases);
      this.defaultCaseIndex = defaultCaseIndex;

      assert defaultCaseIndex == -1 || cases.get(defaultCaseIndex).getTest() == null;
   }

   private SwitchNode(final SwitchNode switchNode, final Expression expression, final List<CaseNode> cases, final int defaultCaseIndex) {
      super(switchNode);
      this.expression = expression;
      this.cases = List.copyOf(cases);
      this.defaultCaseIndex = defaultCaseIndex;
      this.tag = switchNode.getTag();
   }

   @Override
   public boolean isTerminal() {
      if (!this.cases.isEmpty() && this.defaultCaseIndex != -1) {
         for (CaseNode caseNode : this.cases) {
            if (!caseNode.isTerminal()) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public Node accept(final LexicalContext lc, final NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterSwitchNode(this)
         ? visitor.leaveSwitchNode(
            this.setExpression(lc, (Expression)this.expression.accept(visitor)).setCases(lc, Node.accept(visitor, this.cases), this.defaultCaseIndex)
         )
         : this);
   }

   @Override
   public <R> R accept(LexicalContext lc, TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterSwitchNode(this);
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printType) {
      sb.append("switch (");
      this.expression.toString(sb, printType);
      sb.append(')');
   }

   public CaseNode getDefaultCase() {
      return this.defaultCaseIndex == -1 ? null : this.cases.get(this.defaultCaseIndex);
   }

   public List<CaseNode> getCases() {
      return this.cases;
   }

   private SwitchNode setCases(final LexicalContext lc, final List<CaseNode> cases, final int defaultCaseIndex) {
      return this.cases == cases ? this : Node.replaceInLexicalContext(lc, this, new SwitchNode(this, this.expression, cases, defaultCaseIndex));
   }

   public Expression getExpression() {
      return this.expression;
   }

   public SwitchNode setExpression(final LexicalContext lc, final Expression expression) {
      return this.expression == expression ? this : Node.replaceInLexicalContext(lc, this, new SwitchNode(this, expression, this.cases, this.defaultCaseIndex));
   }

   public Symbol getTag() {
      return this.tag;
   }

   public void setTag(final Symbol tag) {
      this.tag = tag;
   }

   public boolean hasDefaultCase() {
      return this.defaultCaseIndex != -1;
   }

   @Override
   public boolean isCompletionValueNeverEmpty() {
      return true;
   }
}
