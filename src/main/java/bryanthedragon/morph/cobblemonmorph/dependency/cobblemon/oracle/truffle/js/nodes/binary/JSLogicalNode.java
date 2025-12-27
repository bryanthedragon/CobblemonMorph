package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.control.ResumableNode;
import com.oracle.truffle.js.nodes.control.YieldException;

public abstract class JSLogicalNode extends JSBinaryNode implements ResumableNode.WithIntState {
   private static final int RESUME_RIGHT = 1;
   private static final int RESUME_UNEXECUTED = 0;
   protected final ConditionProfile canShortCircuit = ConditionProfile.createBinaryProfile();

   protected JSLogicalNode(JavaScriptNode left, JavaScriptNode right) {
      super(left, right);
   }

   protected abstract boolean useLeftValue(Object leftValue);

   @Override
   public final Object execute(VirtualFrame frame) {
      Object leftValue = this.leftNode.execute(frame);
      return this.canShortCircuit.profile(this.useLeftValue(leftValue)) ? leftValue : this.rightNode.execute(frame);
   }

   @Override
   public Object resume(VirtualFrame frame, int stateSlot) {
      int state = this.getStateAsIntAndReset(frame, stateSlot);
      if (state == 0) {
         Object leftValue = this.leftNode.execute(frame);
         if (this.canShortCircuit.profile(this.useLeftValue(leftValue))) {
            return leftValue;
         } else {
            try {
               return this.getRight().execute(frame);
            } catch (YieldException var6) {
               this.setStateAsInt(frame, stateSlot, 1);
               throw var6;
            }
         }
      } else {
         assert state == 1;

         try {
            return this.rightNode.execute(frame);
         } catch (YieldException var7) {
            this.setStateAsInt(frame, stateSlot, 1);
            throw var7;
         }
      }
   }

   @Override
   public boolean isResultAlwaysOfType(Class<?> clazz) {
      return this.getLeft().isResultAlwaysOfType(clazz) && this.getRight().isResultAlwaysOfType(clazz);
   }
}
