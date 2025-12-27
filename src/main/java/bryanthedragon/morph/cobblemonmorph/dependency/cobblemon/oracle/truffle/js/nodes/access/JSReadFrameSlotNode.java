package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSFrameSlot;
import com.oracle.truffle.js.nodes.ReadNode;
import com.oracle.truffle.js.nodes.RepeatableNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.instrumentation.NodeObjectDescriptor;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.Strings;

@ImportStatic(FrameSlotKind.class)
public abstract class JSReadFrameSlotNode extends FrameSlotNode implements RepeatableNode, ReadNode {
   protected final boolean hasTemporalDeadZone;

   protected JSReadFrameSlotNode(int slot, Object identifier, boolean hasTemporalDeadZone) {
      super(slot, identifier);
      this.hasTemporalDeadZone = hasTemporalDeadZone;
   }

   public static JSReadFrameSlotNode create(JSFrameSlot slot, boolean hasTemporalDeadZone) {
      return create(slot, ScopeFrameNode.createCurrent(), hasTemporalDeadZone);
   }

   public static JSReadFrameSlotNode create(JSFrameSlot slot, ScopeFrameNode levelFrameNode, boolean hasTemporalDeadZone) {
      assert !hasTemporalDeadZone || JSFrameUtil.hasTemporalDeadZone(slot);

      return create(slot.getIndex(), slot.getIdentifier(), levelFrameNode, hasTemporalDeadZone);
   }

   static JSReadFrameSlotNode create(int slotIndex, Object identifier, ScopeFrameNode levelFrameNode, boolean hasTemporalDeadZone) {
      return (JSReadFrameSlotNode)(levelFrameNode == ScopeFrameNode.createCurrent()
         ? JSReadCurrentFrameSlotNodeGen.create(slotIndex, identifier, hasTemporalDeadZone)
         : JSReadScopeFrameSlotNodeGen.create(slotIndex, identifier, levelFrameNode, hasTemporalDeadZone));
   }

   public static JSReadFrameSlotNode create(JSFrameSlot slot) {
      return JSReadCurrentFrameSlotNodeGen.create(slot.getIndex(), slot.getIdentifier(), JSFrameUtil.hasTemporalDeadZone(slot));
   }

   public static JSReadFrameSlotNode create(FrameDescriptor desc, int slotIndex) {
      return create(JSFrameSlot.fromIndexedFrameSlot(desc, slotIndex));
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      if (tag != JSTags.ReadVariableTag.class && tag != StandardTags.ReadVariableTag.class) {
         return super.hasTag(tag);
      } else {
         return JSFrameUtil.isInternalIdentifier(this.getIdentifier()) ? JSFrameUtil.isThisSlotIdentifier(this.getIdentifier()) : true;
      }
   }

   @Override
   public Object getNodeObject() {
      TruffleString name = JSFrameUtil.getPublicName(this.getIdentifier());
      NodeObjectDescriptor descriptor = JSTags.createNodeObjectDescriptor("name", name);
      descriptor.addProperty("readVariableName", name);
      return descriptor;
   }

   @Override
   public String expressionToString() {
      Object ident = this.getIdentifier();
      return ident instanceof TruffleString ? Strings.toJavaString((TruffleString)ident) : null;
   }

   @Override
   public final boolean hasTemporalDeadZone() {
      return this.hasTemporalDeadZone;
   }
}
