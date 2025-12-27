package com.oracle.truffle.api.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public interface RepeatingNode extends NodeInterface {
   Object CONTINUE_LOOP_STATUS = new Object() {
      @Override
      public String toString() {
         return "CONTINUE_LOOP_STATUS";
      }
   };
   Object BREAK_LOOP_STATUS = new Object() {
      @Override
      public String toString() {
         return "BREAK_LOOP_STATUS";
      }
   };

   boolean executeRepeating(VirtualFrame frame);

   default Object executeRepeatingWithValue(VirtualFrame frame) {
      return this.executeRepeating(frame) ? CONTINUE_LOOP_STATUS : BREAK_LOOP_STATUS;
   }

   default Object initialLoopStatus() {
      return CONTINUE_LOOP_STATUS;
   }

   default boolean shouldContinue(Object returnValue) {
      return returnValue == this.initialLoopStatus();
   }
}
