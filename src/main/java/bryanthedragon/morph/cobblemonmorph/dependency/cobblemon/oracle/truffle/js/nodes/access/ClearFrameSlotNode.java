package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Objects;
import java.util.Set;

class ClearFrameSlotNode extends JavaScriptNode {
   @Node.Child
   private ScopeFrameNode scopeFrameNode;
   private final int slot;

   protected ClearFrameSlotNode(ScopeFrameNode scopeFrameNode, int slot) {
      this.slot = slot;
      this.scopeFrameNode = Objects.requireNonNull(scopeFrameNode);
   }

   @Override
   public Object execute(VirtualFrame frame) {
      Frame scopeFrame = this.scopeFrameNode.executeFrame(frame);
      ClearFrameSlotsNode.clearSlot(scopeFrame, this.slot);
      return Undefined.instance;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return new ClearFrameSlotNode(this.scopeFrameNode, this.slot);
   }

   @Override
   public boolean isResultAlwaysOfType(Class<?> clazz) {
      return clazz == Undefined.class;
   }
}
