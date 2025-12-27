package com.oracle.truffle.js.nodes;

import com.oracle.truffle.api.nodes.Node;

public interface Truncatable {
   void setTruncate();

   static void truncate(Node node) {
      if (node instanceof Truncatable) {
         ((Truncatable)node).setTruncate();
      }
   }
}
