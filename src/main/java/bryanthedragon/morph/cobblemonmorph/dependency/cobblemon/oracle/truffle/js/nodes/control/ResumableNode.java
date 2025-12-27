package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.objects.Undefined;

public interface ResumableNode {
   default Object resume(VirtualFrame frame, int stateSlot) {
      throw CompilerDirectives.shouldNotReachHere();
   }

   static JavaScriptNode createResumableNode(ResumableNode node, int stateSlot) {
      if (!<unrepresentable>.$assertionsDisabled && node instanceof SuspendNode) {
         throw new AssertionError(node);
      } else {
         JavaScriptNode original = (JavaScriptNode)node;
         return GeneratorWrapperNode.createWrapper(original, stateSlot);
      }
   }

   default void resetState(VirtualFrame frame, int stateSlot) {
      frame.setObject(stateSlot, Undefined.instance);
   }

   default FrameSlotKind getStateSlotKind() {
      return FrameSlotKind.Illegal;
   }

   static {
      if (<unrepresentable>.$assertionsDisabled) {
      }
   }

   public interface WithIntState extends ResumableNode {
      default void setStateAsInt(VirtualFrame frame, int stateSlot, int state) {
         if (!<unrepresentable>.$assertionsDisabled && frame.getFrameDescriptor().getSlotKind(stateSlot) != FrameSlotKind.Int) {
            throw new AssertionError();
         } else {
            frame.setInt(stateSlot, state);
         }
      }

      default int getStateAsInt(VirtualFrame frame, int stateSlot) {
         if (!<unrepresentable>.$assertionsDisabled && frame.getFrameDescriptor().getSlotKind(stateSlot) != FrameSlotKind.Int) {
            throw new AssertionError();
         } else if (frame.isInt(stateSlot)) {
            return frame.getInt(stateSlot);
         } else if (<unrepresentable>.$assertionsDisabled || frame.isObject(stateSlot) && frame.getObject(stateSlot) == Undefined.instance) {
            return 0;
         } else {
            throw new AssertionError();
         }
      }

      default int getStateAsIntAndReset(VirtualFrame frame, int stateSlot) {
         int state = this.getStateAsInt(frame, stateSlot);
         this.resetState(frame, stateSlot);
         return state;
      }

      @Override
      default FrameSlotKind getStateSlotKind() {
         return FrameSlotKind.Int;
      }
   }

   public interface WithObjectState extends ResumableNode {
      default void setState(VirtualFrame frame, int stateSlot, Object state) {
         if (!<unrepresentable>.$assertionsDisabled && frame.getFrameDescriptor().getSlotKind(stateSlot) != FrameSlotKind.Object) {
            throw new AssertionError();
         } else {
            frame.setObject(stateSlot, state);
         }
      }

      default Object getState(VirtualFrame frame, int stateSlot) {
         if (!<unrepresentable>.$assertionsDisabled && frame.getFrameDescriptor().getSlotKind(stateSlot) != FrameSlotKind.Object) {
            throw new AssertionError();
         } else {
            return frame.getObject(stateSlot);
         }
      }

      default Object getStateAndReset(VirtualFrame frame, int stateSlot) {
         Object state = this.getState(frame, stateSlot);
         this.resetState(frame, stateSlot);
         return state;
      }

      @Override
      default FrameSlotKind getStateSlotKind() {
         return FrameSlotKind.Object;
      }
   }
}
