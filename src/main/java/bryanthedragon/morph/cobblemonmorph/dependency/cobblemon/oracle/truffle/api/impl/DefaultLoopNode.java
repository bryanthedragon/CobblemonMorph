package com.oracle.truffle.api.impl;

import com.oracle.truffle.api.TruffleSafepoint;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RepeatingNode;

public final class DefaultLoopNode extends LoopNode {
   @Node.Child
   private RepeatingNode repeatNode;

   public DefaultLoopNode(RepeatingNode repeatNode) {
      this.repeatNode = repeatNode;
   }

   @Override
   public RepeatingNode getRepeatingNode() {
      return this.repeatNode;
   }

   @Override
   public Object execute(VirtualFrame frame) {
      Object status;
      while (this.repeatNode.shouldContinue(status = this.repeatNode.executeRepeatingWithValue(frame))) {
         TruffleSafepoint.poll(this);
      }

      return status;
   }
}
