package com.oracle.js.parser;

import com.oracle.js.parser.ir.Scope;
import com.oracle.js.parser.ir.Statement;
import java.util.Iterator;
import java.util.NoSuchElementException;

class ParserContext {
   private ParserContextNode[] stack;
   private int sp = 0;
   private static final int INITIAL_DEPTH = 16;

   ParserContext() {
      this.stack = new ParserContextNode[16];
   }

   public <T extends ParserContextNode> T push(final T node) {
      assert !this.contains(node);

      if (this.sp == this.stack.length) {
         ParserContextNode[] newStack = new ParserContextNode[this.sp * 2];
         System.arraycopy(this.stack, 0, newStack, 0, this.sp);
         this.stack = newStack;
      }

      this.stack[this.sp] = node;
      this.sp++;
      return node;
   }

   public ParserContextNode peek() {
      return this.stack[this.sp - 1];
   }

   public <T extends ParserContextNode> T pop(final T node) {
      this.sp--;
      T popped = (T)this.stack[this.sp];
      this.stack[this.sp] = null;

      assert node == popped;

      return popped;
   }

   public boolean contains(final ParserContextNode node) {
      for (int i = 0; i < this.sp; i++) {
         if (this.stack[i] == node) {
            return true;
         }
      }

      return false;
   }

   private ParserContextBreakableNode getBreakable() {
      ParserContext.NodeIterator<ParserContextBreakableNode> iter = new ParserContext.NodeIterator<>(
         ParserContextBreakableNode.class, this.getCurrentFunction()
      );

      while (iter.hasNext()) {
         ParserContextBreakableNode next = iter.next();
         if (next.isBreakableWithoutLabel()) {
            return next;
         }
      }

      return null;
   }

   public ParserContextBreakableNode getBreakable(final String labelName) {
      return labelName != null ? this.findLabelledItem(labelName, ParserContextBreakableNode.class) : this.getBreakable();
   }

   public ParserContextLoopNode getCurrentLoop() {
      Iterator<ParserContextLoopNode> iter = new ParserContext.NodeIterator<>(ParserContextLoopNode.class, this.getCurrentFunction());
      return iter.hasNext() ? iter.next() : null;
   }

   private ParserContextLoopNode getContinueTo() {
      return this.getCurrentLoop();
   }

   public ParserContextLoopNode getContinueTo(final String labelName) {
      return labelName != null ? this.findLabelledItem(labelName, ParserContextLoopNode.class) : this.getContinueTo();
   }

   public ParserContextLabelNode findLabel(final String name) {
      Iterator<ParserContextLabelNode> iter = new ParserContext.NodeIterator<>(ParserContextLabelNode.class, this.getCurrentFunction());

      while (iter.hasNext()) {
         ParserContextLabelNode next = iter.next();
         if (next.getLabelName().equals(name)) {
            return next;
         }
      }

      return null;
   }

   private <T extends ParserContextBreakableNode> T findLabelledItem(final String labelName, Class<T> breakableType) {
      T prev = null;
      Iterator<ParserContextNode> iter = new ParserContext.NodeIterator<>(ParserContextNode.class, this.getCurrentFunction());

      while (iter.hasNext()) {
         ParserContextNode next = iter.next();
         if (next instanceof ParserContextLabelNode) {
            ParserContextLabelNode labelStatement = (ParserContextLabelNode)next;
            if (labelStatement.getLabelName().equals(labelName)) {
               return prev;
            }
         } else if (breakableType == ParserContextLoopNode.class && next instanceof ParserContextBlockNode && next.getFlag(16) == 0) {
            prev = null;
         } else if (breakableType.isInstance(next)) {
            prev = (T)breakableType.cast(next);
         }
      }

      return null;
   }

   public void prependStatementToCurrentNode(final Statement statement) {
      assert statement != null;

      this.stack[this.sp - 1].prependStatement(statement);
   }

   public void appendStatementToCurrentNode(final Statement statement) {
      assert statement != null;

      this.stack[this.sp - 1].appendStatement(statement);
   }

   public ParserContextFunctionNode getCurrentFunction() {
      for (int i = this.sp - 1; i >= 0; i--) {
         if (this.stack[i] instanceof ParserContextFunctionNode) {
            ParserContextFunctionNode function = (ParserContextFunctionNode)this.stack[i];
            if (!function.isCoverArrowHead()) {
               return function;
            }
         }
      }

      return null;
   }

   public Iterator<ParserContextBlockNode> getBlocks() {
      return new ParserContext.NodeIterator<>(ParserContextBlockNode.class);
   }

   public ParserContextBlockNode getCurrentBlock() {
      return this.getBlocks().next();
   }

   public Statement getLastStatement() {
      if (this.sp == 0) {
         return null;
      } else {
         ParserContextNode top = this.stack[this.sp - 1];
         int s = top.getStatements().size();
         return s == 0 ? null : top.getStatements().get(s - 1);
      }
   }

   public Iterator<ParserContextFunctionNode> getFunctions() {
      return new ParserContext.NodeIterator<>(ParserContextFunctionNode.class);
   }

   public ParserContextFunctionNode getCurrentNonArrowFunction() {
      Iterator<ParserContextFunctionNode> iter = this.getFunctions();

      while (iter.hasNext()) {
         ParserContextFunctionNode fn = iter.next();
         if (!fn.isArrow()) {
            return fn;
         }
      }

      return null;
   }

   public Scope getCurrentScope() {
      for (int i = this.sp - 1; i >= 0; i--) {
         if (this.stack[i] instanceof ParserContextScopableNode) {
            ParserContextScopableNode scopable = (ParserContextScopableNode)this.stack[i];
            return scopable.getScope();
         }
      }

      return null;
   }

   public ParserContextClassNode getCurrentClass() {
      Iterator<ParserContextClassNode> iter = new ParserContext.NodeIterator<>(ParserContextClassNode.class);
      return iter.hasNext() ? iter.next() : null;
   }

   public Iterator<ParserContextClassNode> getClasses() {
      return new ParserContext.NodeIterator<>(ParserContextClassNode.class);
   }

   public Iterator<ParserContextNode> getAllNodes() {
      return new ParserContext.NodeIterator<>(ParserContextNode.class);
   }

   public ParserContextFunctionNode setCurrentFunctionFlag(int flag) {
      for (int i = this.sp - 1; i >= 0; i--) {
         if (this.stack[i] instanceof ParserContextFunctionNode) {
            ParserContextFunctionNode fn = (ParserContextFunctionNode)this.stack[i];
            fn.setFlag(flag);
            return fn;
         }
      }

      return null;
   }

   public void propagateFunctionFlags() {
      ParserContextFunctionNode current = null;

      for (int i = this.sp - 1; i >= 0; i--) {
         if (this.stack[i] instanceof ParserContextFunctionNode) {
            ParserContextFunctionNode f = (ParserContextFunctionNode)this.stack[i];
            if (current != null) {
               current.propagateFlagsToParent(f);
               break;
            }

            current = f;
         }
      }
   }

   private class NodeIterator<T extends ParserContextNode> implements Iterator<T> {
      private int index = ParserContext.this.sp - 1;
      private T next;
      private final Class<T> clazz;
      private ParserContextNode until;

      NodeIterator(final Class<T> clazz) {
         this(clazz, null);
      }

      NodeIterator(final Class<T> clazz, final ParserContextNode until) {
         this.clazz = clazz;
         this.until = until;
         this.next = this.findNext();
      }

      @Override
      public boolean hasNext() {
         return this.next != null;
      }

      public T next() {
         if (this.next == null) {
            throw new NoSuchElementException();
         } else {
            T lnext = this.next;
            this.next = this.findNext();
            return lnext;
         }
      }

      private T findNext() {
         for (int i = this.index; i >= 0; i--) {
            Object node = ParserContext.this.stack[i];
            if (node == this.until) {
               return null;
            }

            if (this.clazz.isAssignableFrom(node.getClass())) {
               this.index = i - 1;
               return (T)node;
            }
         }

         return null;
      }
   }
}
