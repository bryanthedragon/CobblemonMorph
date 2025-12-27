package com.oracle.truffle.regex.tregex.nodes;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;

@GeneratedBy(TRegexExecutorBaseNode.class)
final class TRegexExecutorBaseNodeWrapper extends TRegexExecutorBaseNode implements InstrumentableNode.WrapperNode {
   @Node.Child
   private TRegexExecutorBaseNode delegateNode;
   @Node.Child
   private ProbeNode probeNode;

   TRegexExecutorBaseNodeWrapper(TRegexExecutorBaseNode delegateNode, ProbeNode probeNode) {
      this.delegateNode = delegateNode;
      this.probeNode = probeNode;
   }

   public TRegexExecutorBaseNode getDelegateNode() {
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
   public Object execute(VirtualFrame frame, TRegexExecutorLocals locals, TruffleString.CodeRange codeRange, boolean tString) {
      while (true) {
         boolean wasOnReturnExecuted = false;

         Object returnValue;
         try {
            this.probeNode.onEnter(frame);
            returnValue = this.delegateNode.execute(frame, locals, codeRange, tString);
            wasOnReturnExecuted = true;
            this.probeNode.onReturnValue(frame, returnValue);
         } catch (Throwable var9) {
            Object result = this.probeNode.onReturnExceptionalOrUnwind(frame, var9, wasOnReturnExecuted);
            if (result == ProbeNode.UNWIND_ACTION_REENTER) {
               continue;
            }

            if (result == null) {
               throw var9;
            }

            returnValue = result;
         }

         return returnValue;
      }
   }
}
