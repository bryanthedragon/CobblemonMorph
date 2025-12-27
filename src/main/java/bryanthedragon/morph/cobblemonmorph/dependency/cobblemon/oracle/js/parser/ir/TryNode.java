package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import java.util.ArrayList;
import java.util.List;

public final class TryNode extends Statement {
   private final Block body;
   private final List<Block> catchBlocks;
   private final Block finallyBody;
   private Symbol exception;

   public TryNode(final int lineNumber, final long token, final int finish, final Block body, final List<Block> catchBlocks, final Block finallyBody) {
      super(lineNumber, token, finish);
      this.body = body;
      this.catchBlocks = List.copyOf(catchBlocks);
      this.finallyBody = finallyBody;
   }

   private TryNode(final TryNode tryNode, final Block body, final List<Block> catchBlocks, final Block finallyBody) {
      super(tryNode);
      this.body = body;
      this.catchBlocks = List.copyOf(catchBlocks);
      this.finallyBody = finallyBody;
      this.exception = tryNode.exception;
   }

   @Override
   public boolean isTerminal() {
      if (this.body.isTerminal()) {
         for (Block catchBlock : this.catchBlocks) {
            if (!catchBlock.isTerminal()) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      if (visitor.enterTryNode(this)) {
         Block newFinallyBody = this.finallyBody == null ? null : (Block)this.finallyBody.accept(visitor);
         Block newBody = (Block)this.body.accept(visitor);
         return visitor.leaveTryNode(this.setBody(newBody).setFinallyBody(newFinallyBody).setCatchBlocks(Node.accept(visitor, this.catchBlocks)));
      } else {
         return this;
      }
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterTryNode(this);
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printType) {
      sb.append("try ");
   }

   public Block getBody() {
      return this.body;
   }

   public TryNode setBody(final Block body) {
      return this.body == body ? this : new TryNode(this, body, this.catchBlocks, this.finallyBody);
   }

   public List<CatchNode> getCatches() {
      List<CatchNode> catches = new ArrayList<>(this.catchBlocks.size());

      for (Block catchBlock : this.catchBlocks) {
         catches.add(getCatchNodeFromBlock(catchBlock));
      }

      return List.copyOf(catches);
   }

   private static CatchNode getCatchNodeFromBlock(final Block catchBlock) {
      return (CatchNode)catchBlock.getLastStatement();
   }

   public List<Block> getCatchBlocks() {
      return this.catchBlocks;
   }

   public TryNode setCatchBlocks(final List<Block> catchBlocks) {
      return this.catchBlocks == catchBlocks ? this : new TryNode(this, this.body, catchBlocks, this.finallyBody);
   }

   public Symbol getException() {
      return this.exception;
   }

   public Block getFinallyBody() {
      return this.finallyBody;
   }

   public TryNode setFinallyBody(final Block finallyBody) {
      return this.finallyBody == finallyBody ? this : new TryNode(this, this.body, this.catchBlocks, finallyBody);
   }

   @Override
   public boolean isCompletionValueNeverEmpty() {
      return true;
   }
}
