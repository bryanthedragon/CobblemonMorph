package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.js.nodes.JavaScriptNode;

public abstract class FrameSlotNode extends JavaScriptNode {
   protected final int slot;
   protected final Object identifier;

   protected FrameSlotNode(int slot, Object identifier) {
      this.slot = slot;
      this.identifier = identifier;
   }

   public final int getSlotIndex() {
      return this.slot;
   }

   public final Object getIdentifier() {
      return this.identifier;
   }

   public abstract ScopeFrameNode getLevelFrameNode();

   public boolean hasTemporalDeadZone() {
      return false;
   }

   protected final boolean isIllegal(Frame frame) {
      return frame.getTag(this.slot) == FrameSlotKind.Illegal.tag;
   }

   public abstract static class WithDescriptor extends FrameSlotNode {
      @CompilerDirectives.CompilationFinal
      private FrameDescriptor frameDescriptor;

      protected WithDescriptor(int slot, Object identifier) {
         super(slot, identifier);
      }

      protected final boolean isBooleanKind(Frame frame) {
         return this.isOrSetKind(frame, FrameSlotKind.Boolean);
      }

      protected final boolean isIntegerKind(Frame frame) {
         return this.isOrSetKind(frame, FrameSlotKind.Int);
      }

      protected final boolean isDoubleKind(Frame frame) {
         return this.isOrSetKind(frame, FrameSlotKind.Double);
      }

      protected final boolean isLongKind(Frame frame) {
         return this.isOrSetKind(frame, FrameSlotKind.Long);
      }

      protected final boolean isIntegerKind(Frame frame, FrameSlotKind currentKind) {
         return this.isOrSetKind(frame, currentKind, FrameSlotKind.Int);
      }

      protected final boolean isDoubleKind(Frame frame, FrameSlotKind currentKind) {
         return this.isOrSetKind(frame, currentKind, FrameSlotKind.Double);
      }

      protected final boolean isLongKind(Frame frame, FrameSlotKind currentKind) {
         return this.isOrSetKind(frame, currentKind, FrameSlotKind.Long);
      }

      protected final void ensureObjectKind(Frame frame) {
         FrameDescriptor desc = this.getFrameDescriptor(frame);
         if (desc.getSlotKind(this.slot) != FrameSlotKind.Object) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            desc.setSlotKind(this.slot, FrameSlotKind.Object);
         }
      }

      private boolean isOrSetKind(Frame frame, FrameSlotKind targetKind) {
         FrameDescriptor desc = this.getFrameDescriptor(frame);
         FrameSlotKind currentKind = desc.getSlotKind(this.slot);
         return this.isOrSetKind(frame, desc, currentKind, targetKind);
      }

      private boolean isOrSetKind(Frame frame, FrameSlotKind currentKind, FrameSlotKind targetKind) {
         FrameDescriptor desc = this.getFrameDescriptor(frame);
         return this.isOrSetKind(frame, desc, currentKind, targetKind);
      }

      private boolean isOrSetKind(Frame frame, FrameDescriptor desc, FrameSlotKind currentKind, FrameSlotKind targetKind) {
         assert desc == frame.getFrameDescriptor();

         if (currentKind == targetKind) {
            return true;
         } else if (currentKind == FrameSlotKind.Illegal) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            desc.setSlotKind(this.slot, targetKind);
            return true;
         } else {
            if (targetKind == FrameSlotKind.Double) {
               if (currentKind == FrameSlotKind.Int || currentKind == FrameSlotKind.Long) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  desc.setSlotKind(this.slot, FrameSlotKind.Double);
                  return true;
               }
            } else if (targetKind == FrameSlotKind.Long && currentKind == FrameSlotKind.Int) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               desc.setSlotKind(this.slot, FrameSlotKind.Long);
               return true;
            }

            return false;
         }
      }

      protected final FrameDescriptor getFrameDescriptor(Frame frame) {
         FrameDescriptor constantFrameDescriptor = this.frameDescriptor;
         if (constantFrameDescriptor == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.frameDescriptor = constantFrameDescriptor = frame.getFrameDescriptor();
         }

         assert constantFrameDescriptor == frame.getFrameDescriptor();

         CompilerAsserts.partialEvaluationConstant(constantFrameDescriptor);
         return constantFrameDescriptor;
      }
   }
}
