package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public class BlockExpression extends Expression {
   private final Block block;

   public BlockExpression(final long token, final int finish, final Block block) {
      super(token, finish);
      this.block = block;
   }

   private BlockExpression(final BlockExpression classNode, final Block block) {
      super(classNode);
      this.block = block;
   }

   public Block getBlock() {
      return this.block;
   }

   private BlockExpression setBlock(final Block block) {
      return this.block == block ? this : new BlockExpression(this, block);
   }

   @Override
   public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      if (visitor.enterBlockExpression(this)) {
         Block newBlock = (Block)this.block.accept(visitor);
         return visitor.leaveBlockExpression(this.setBlock(newBlock));
      } else {
         return this;
      }
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterBlockExpression(this);
   }

   @Override
   public void toString(StringBuilder sb, boolean printType) {
      this.block.toString(sb, printType);
   }
}
