package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public class BlockStatement extends Statement {
   private final Block block;

   public BlockStatement(final int lineNumber, final Block block) {
      super(lineNumber, block.getToken(), block.getFinish());
      this.block = block;
   }

   private BlockStatement(final BlockStatement blockStatement, final Block block) {
      super(blockStatement);
      this.block = block;
   }

   @Override
   public boolean isTerminal() {
      return this.block.isTerminal();
   }

   public boolean isSynthetic() {
      return this.block.isSynthetic();
   }

   @Override
   public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterBlockStatement(this) ? visitor.leaveBlockStatement(this.setBlock((Block)this.block.accept(visitor))) : this);
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterBlockStatement(this);
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printType) {
      this.block.toString(sb, printType);
   }

   public Block getBlock() {
      return this.block;
   }

   public BlockStatement setBlock(final Block block) {
      return this.block == block ? this : new BlockStatement(this, block);
   }
}
