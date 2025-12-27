package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class LabelNode extends LexicalContextStatement {
   private final String labelName;
   private final Block body;

   public LabelNode(final int lineNumber, final long token, final int finish, final String labelName, final Block body) {
      super(lineNumber, token, finish);
      this.labelName = labelName;
      this.body = body;
   }

   private LabelNode(final LabelNode labelNode, final String labelName, final Block body) {
      super(labelNode);
      this.labelName = labelName;
      this.body = body;
   }

   @Override
   public boolean isTerminal() {
      return this.body.isTerminal();
   }

   @Override
   public Node accept(final LexicalContext lc, final NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterLabelNode(this) ? visitor.leaveLabelNode(this.setBody(lc, (Block)this.body.accept(visitor))) : this);
   }

   @Override
   public <R> R accept(LexicalContext lc, TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterLabelNode(this);
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printType) {
      sb.append(this.labelName).append(':');
   }

   public Block getBody() {
      return this.body;
   }

   public LabelNode setBody(final LexicalContext lc, final Block body) {
      return this.body == body ? this : Node.replaceInLexicalContext(lc, this, new LabelNode(this, this.labelName, body));
   }

   public String getLabelName() {
      return this.labelName;
   }
}
