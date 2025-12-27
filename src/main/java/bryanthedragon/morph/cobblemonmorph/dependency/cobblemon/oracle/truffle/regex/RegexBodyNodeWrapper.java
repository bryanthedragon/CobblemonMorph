package com.oracle.truffle.regex;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;

@GeneratedBy(RegexBodyNode.class)
final class RegexBodyNodeWrapper extends RegexBodyNode implements InstrumentableNode.WrapperNode {
   @Node.Child
   private RegexBodyNode delegateNode;
   @Node.Child
   private ProbeNode probeNode;

   RegexBodyNodeWrapper(RegexBodyNode copy, RegexBodyNode delegateNode, ProbeNode probeNode) {
      super(copy);
      this.delegateNode = delegateNode;
      this.probeNode = probeNode;
   }

   public RegexBodyNode getDelegateNode() {
      return this.delegateNode;
   }

   @Override
   public ProbeNode getProbeNode() {
      return this.probeNode;
   }

   @Override
   public NodeCost getCost() {
      return NodeCost.NONE;
   }

   @Override
   public Object execute(VirtualFrame frame) {
      while (true) {
         boolean wasOnReturnExecuted = false;

         Object returnValue;
         try {
            this.probeNode.onEnter(frame);
            returnValue = this.delegateNode.execute(frame);
            wasOnReturnExecuted = true;
            this.probeNode.onReturnValue(frame, returnValue);
         } catch (Throwable var6) {
            Object result = this.probeNode.onReturnExceptionalOrUnwind(frame, var6, wasOnReturnExecuted);
            if (result == ProbeNode.UNWIND_ACTION_REENTER) {
               continue;
            }

            if (result == null) {
               throw var6;
            }

            returnValue = result;
         }

         return returnValue;
      }
   }
}
