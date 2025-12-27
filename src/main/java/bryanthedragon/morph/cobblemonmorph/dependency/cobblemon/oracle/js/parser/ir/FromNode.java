package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import com.oracle.truffle.api.strings.TruffleString;

public class FromNode extends Node {
   private final LiteralNode<TruffleString> moduleSpecifier;

   public FromNode(final long token, final int start, final int finish, final LiteralNode<TruffleString> moduleSpecifier) {
      super(token, start, finish);
      this.moduleSpecifier = moduleSpecifier;
   }

   private FromNode(final FromNode node, final LiteralNode<TruffleString> moduleSpecifier) {
      super(node);
      this.moduleSpecifier = moduleSpecifier;
   }

   public LiteralNode<TruffleString> getModuleSpecifier() {
      return this.moduleSpecifier;
   }

   public FromNode setModuleSpecifier(LiteralNode<TruffleString> moduleSpecifier) {
      return this.moduleSpecifier == moduleSpecifier ? this : new FromNode(this, moduleSpecifier);
   }

   @Override
   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterFromNode(this)
         ? visitor.leaveFromNode(this.setModuleSpecifier((LiteralNode<TruffleString>)this.moduleSpecifier.accept(visitor)))
         : this);
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterFromNode(this);
   }

   @Override
   public void toString(StringBuilder sb, boolean printType) {
      sb.append("from");
      sb.append(' ');
      this.moduleSpecifier.toString(sb, printType);
   }
}
