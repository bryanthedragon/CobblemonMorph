package com.oracle.truffle.api.nodes;

public abstract class NodeCloneable implements Cloneable {
   protected NodeCloneable() {
   }

   @Override
   protected Object clone() {
      try {
         return super.clone();
      } catch (CloneNotSupportedException var2) {
         throw new AssertionError();
      }
   }
}
