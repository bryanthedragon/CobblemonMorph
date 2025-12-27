package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(cost = NodeCost.NONE)
public abstract class ExecutionEventNode extends Node {
   protected ExecutionEventNode() {
   }

   protected void onEnter(VirtualFrame frame) {
   }

   protected void onInputValue(VirtualFrame frame, EventContext inputContext, int inputIndex, Object inputValue) {
   }

   protected void onReturnValue(VirtualFrame frame, Object result) {
   }

   protected void onReturnExceptional(VirtualFrame frame, Throwable exception) {
   }

   protected Object onUnwind(VirtualFrame frame, Object info) {
      return null;
   }

   protected void onDispose(VirtualFrame frame) {
   }

   protected final EventContext getInputContext(int index) {
      if (index >= 0 && index < this.getInputCount()) {
         ProbeNode.EventProviderWithInputChainNode node = this.getChainNode();
         if (node == null) {
            CompilerDirectives.transferToInterpreter();
            throw new AssertionError("should not be reachable as input count should be 0");
         } else {
            return node.getInputContext(index);
         }
      } else {
         CompilerDirectives.transferToInterpreter();
         throw new IndexOutOfBoundsException(String.valueOf(index));
      }
   }

   protected final int getInputCount() {
      ProbeNode.EventProviderWithInputChainNode node = this.getChainNode();
      return node == null ? 0 : node.getInputCount();
   }

   protected final void saveInputValue(VirtualFrame frame, int inputIndex, Object inputValue) {
      ProbeNode.EventProviderWithInputChainNode node = this.getChainNode();
      if (node != null) {
         node.saveInputValue(frame, inputIndex, inputValue);
      }
   }

   protected final Object[] getSavedInputValues(VirtualFrame frame) {
      ProbeNode.EventProviderWithInputChainNode node = this.getChainNode();
      return node != null ? node.getSavedInputValues(frame) : ProbeNode.EventProviderWithInputChainNode.EMPTY_ARRAY;
   }

   private ProbeNode.EventProviderWithInputChainNode getChainNode() {
      Node parent = this.getParent();
      return parent instanceof ProbeNode.EventProviderWithInputChainNode ? (ProbeNode.EventProviderWithInputChainNode)parent : null;
   }
}
