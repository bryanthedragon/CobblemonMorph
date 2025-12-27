package com.oracle.truffle.api.nodes;

final class BytecodeOSRValidation {
   private BytecodeOSRValidation() {
   }

   static boolean validateNode(BytecodeOSRNode node) {
      if (!(node instanceof Node)) {
         throw new ClassCastException(String.format("%s must be of type Node.", node.getClass()));
      } else {
         Node osrNode = (Node)node;
         RootNode root = osrNode.getRootNode();
         if (root == null) {
            throw new AssertionError(String.format("%s was not adopted but executed.", node.getClass()));
         } else {
            return true;
         }
      }
   }
}
