package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Bind;
import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.SafeInteger;
import java.util.Set;

abstract class JSWriteScopeFrameSlotNode extends JSWriteFrameSlotNode {
   @Node.Child
   @Executed
   ScopeFrameNode scopeFrameNode;
   @Node.Child
   @Executed
   JavaScriptNode rhsNode;

   protected JSWriteScopeFrameSlotNode(int slot, Object identifier, ScopeFrameNode scopeFrameNode, JavaScriptNode rhsNode) {
      super(slot, identifier);
      this.scopeFrameNode = scopeFrameNode;
      this.rhsNode = rhsNode;
   }

   @Specialization(guards = "isBooleanKind(levelFrame)")
   protected final boolean doBoolean(Frame levelFrame, boolean value) {
      levelFrame.setBoolean(this.slot, value);
      return value;
   }

   @Specialization(guards = "(isIntegerKind(frame, kind) || isLongKind(frame, kind)) || isDoubleKind(frame, kind)")
   protected final int doInteger(Frame frame, int value, @Bind("getFrameDescriptor(frame).getSlotKind(slot)") FrameSlotKind kind) {
      if (this.isIntegerKind(frame, kind)) {
         frame.setInt(this.slot, value);
      } else if (this.isLongKind(frame, kind)) {
         frame.setLong(this.slot, value);
      } else if (this.isDoubleKind(frame, kind)) {
         frame.setDouble(this.slot, value);
      }

      return value;
   }

   @Specialization(guards = "isLongKind(levelFrame)")
   protected final SafeInteger doSafeInteger(Frame levelFrame, SafeInteger value) {
      levelFrame.setLong(this.slot, value.longValue());
      return value;
   }

   @Specialization
   protected final long doLong(Frame levelFrame, long value) {
      this.ensureObjectKind(levelFrame);
      levelFrame.setObject(this.slot, value);
      return value;
   }

   @Specialization(guards = "isDoubleKind(levelFrame)", replaces = {"doInteger", "doSafeInteger"})
   protected final double doDouble(Frame levelFrame, double value) {
      levelFrame.setDouble(this.slot, value);
      return value;
   }

   @Specialization(replaces = {"doBoolean", "doInteger", "doDouble", "doSafeInteger", "doLong"})
   protected final Object doObject(Frame levelFrame, Object value) {
      this.ensureObjectKind(levelFrame);
      levelFrame.setObject(this.slot, value);
      return value;
   }

   @Override
   public final void executeWithFrame(Frame frame, Object value) {
      this.executeEvaluated(null, frame, value);
   }

   abstract void executeEvaluated(VirtualFrame unusedCurrentFrame, Frame levelFrame, Object value);

   @Override
   public final void executeWrite(VirtualFrame frame, Object value) {
      this.executeEvaluated(frame, this.getLevelFrameNode().executeFrame(frame), value);
   }

   @Override
   public ScopeFrameNode getLevelFrameNode() {
      return this.scopeFrameNode;
   }

   @Override
   public JavaScriptNode getRhs() {
      return this.rhsNode;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return JSWriteScopeFrameSlotNodeGen.create(
         this.getSlotIndex(), this.getIdentifier(), this.getLevelFrameNode(), cloneUninitialized(this.getRhs(), materializedTags)
      );
   }
}
