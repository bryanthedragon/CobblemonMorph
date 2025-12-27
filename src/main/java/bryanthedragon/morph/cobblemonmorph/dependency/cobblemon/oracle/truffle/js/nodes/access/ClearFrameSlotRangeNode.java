package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Objects;
import java.util.Set;

class ClearFrameSlotRangeNode extends JavaScriptNode {
   private final int start;
   private final int end;
   @Node.Child
   private ScopeFrameNode scopeFrameNode;

   protected ClearFrameSlotRangeNode(ScopeFrameNode scopeFrameNode, int start, int end) {
      this.start = start;
      this.end = end;
      this.scopeFrameNode = Objects.requireNonNull(scopeFrameNode);
   }

   @ExplodeLoop
   @Override
   public Object execute(VirtualFrame frame) {
      Frame scopeFrame = this.scopeFrameNode.executeFrame(frame);

      for (int slot = this.start; slot < this.end; slot++) {
         ClearFrameSlotsNode.clearSlot(scopeFrame, slot);
      }

      return Undefined.instance;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return new ClearFrameSlotRangeNode(this.scopeFrameNode, this.start, this.end);
   }

   @Override
   public boolean isResultAlwaysOfType(Class<?> clazz) {
      return clazz == Undefined.class;
   }
}
