package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.SafeInteger;
import java.util.Set;

abstract class JSReadCurrentFrameSlotNode extends JSReadFrameSlotNode {
   JSReadCurrentFrameSlotNode(int slot, Object identifier, boolean hasTemporalDeadZone) {
      super(slot, identifier, hasTemporalDeadZone);
   }

   @Specialization(guards = "frame.isBoolean(slot)")
   protected final boolean doBoolean(VirtualFrame frame) {
      return frame.getBoolean(this.slot);
   }

   @Specialization(guards = "frame.isInt(slot)")
   protected final int doInt(VirtualFrame frame) {
      return frame.getInt(this.slot);
   }

   @Specialization(guards = "frame.isDouble(slot) || frame.isInt(slot)")
   protected final double doDouble(VirtualFrame frame) {
      return frame.isInt(this.slot) ? frame.getInt(this.slot) : frame.getDouble(this.slot);
   }

   @Specialization(guards = "frame.isObject(slot)")
   protected final Object doObject(VirtualFrame frame) {
      return frame.getObject(this.slot);
   }

   @Specialization(guards = "frame.isLong(slot)")
   protected final SafeInteger doSafeInteger(VirtualFrame frame) {
      return SafeInteger.valueOf(frame.getLong(this.slot));
   }

   @Specialization(guards = "isIllegal(frame)")
   protected final Object doDead(VirtualFrame frame) {
      assert this.hasTemporalDeadZone();

      throw Errors.createReferenceErrorNotDefined(this.getIdentifier(), this);
   }

   @Override
   public ScopeFrameNode getLevelFrameNode() {
      return ScopeFrameNode.createCurrent();
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return JSReadCurrentFrameSlotNodeGen.create(this.getSlotIndex(), this.getIdentifier(), this.hasTemporalDeadZone());
   }
}
