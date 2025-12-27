package com.oracle.truffle.api.nodes;

import java.util.Iterator;
import java.util.NoSuchElementException;

final class NodeIterator implements Iterator<Node> {
   private final NodeClass nodeClass;
   private final Object[] fields;
   private final Node node;
   private Node next;
   private int fieldsIndex;
   private int childrenIndex;
   private Object[] children;

   NodeIterator(NodeClass nodeClass, Node node, Object[] fields) {
      this.nodeClass = nodeClass;
      this.fields = fields;
      this.node = node;
      this.advance();
   }

   private void advance() {
      if (!this.advanceChildren()) {
         while (this.fieldsIndex < this.fields.length) {
            Object field = this.fields[this.fieldsIndex++];
            if (this.nodeClass.isChildField(field)) {
               this.next = (Node)this.nodeClass.getFieldObject(field, this.node);
               if (this.next != null) {
                  return;
               }
            } else if (this.nodeClass.isChildrenField(field)) {
               this.children = (Object[])this.nodeClass.getFieldObject(field, this.node);
               this.childrenIndex = 0;
               if (this.advanceChildren()) {
                  return;
               }
            } else if (this.nodeClass.nodeFieldsOrderedByKind()) {
               break;
            }
         }

         this.next = null;
      }
   }

   private boolean advanceChildren() {
      if (this.children == null) {
         return false;
      } else {
         while (this.childrenIndex < this.children.length) {
            this.next = (Node)this.children[this.childrenIndex];
            this.childrenIndex++;
            if (this.next != null) {
               return true;
            }
         }

         this.children = null;
         this.childrenIndex = 0;
         return false;
      }
   }

   @Override
   public boolean hasNext() {
      return this.next != null;
   }

   public Node next() {
      Node result = this.next;
      if (result == null) {
         throw new NoSuchElementException();
      } else {
         this.advance();
         return result;
      }
   }

   @Override
   public void remove() {
      throw new UnsupportedOperationException();
   }
}
