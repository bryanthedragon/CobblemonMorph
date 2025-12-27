package com.oracle.truffle.api.nodes;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.VirtualFrame;

public interface BytecodeOSRNode extends NodeInterface {
   Object executeOSR(VirtualFrame osrFrame, int target, Object interpreterState);

   Object getOSRMetadata();

   void setOSRMetadata(Object osrMetadata);

   @Deprecated(since = "22.2")
   default void copyIntoOSRFrame(VirtualFrame osrFrame, VirtualFrame parentFrame, int target) {
      NodeAccessor.RUNTIME.transferOSRFrame(this, parentFrame, osrFrame, target);
   }

   default void copyIntoOSRFrame(VirtualFrame osrFrame, VirtualFrame parentFrame, int target, Object targetMetadata) {
      NodeAccessor.RUNTIME.transferOSRFrame(this, parentFrame, osrFrame, target, targetMetadata);
   }

   default void restoreParentFrame(VirtualFrame osrFrame, VirtualFrame parentFrame) {
      NodeAccessor.RUNTIME.restoreOSRFrame(this, osrFrame, parentFrame);
   }

   default void prepareOSR(int target) {
   }

   static boolean pollOSRBackEdge(BytecodeOSRNode osrNode) {
      if (!CompilerDirectives.inInterpreter()) {
         return false;
      } else if (!<unrepresentable>.$assertionsDisabled && !BytecodeOSRValidation.validateNode(osrNode)) {
         throw new AssertionError();
      } else {
         return NodeAccessor.RUNTIME.pollBytecodeOSRBackEdge(osrNode);
      }
   }

   static Object tryOSR(BytecodeOSRNode osrNode, int target, Object interpreterState, Runnable beforeTransfer, VirtualFrame parentFrame) {
      CompilerAsserts.neverPartOfCompilation();
      return NodeAccessor.RUNTIME.tryBytecodeOSR(osrNode, target, interpreterState, beforeTransfer, parentFrame);
   }

   default Object[] storeParentFrameInArguments(VirtualFrame parentFrame) {
      CompilerAsserts.neverPartOfCompilation();
      return new Object[]{parentFrame};
   }

   default Frame restoreParentFrameFromArguments(Object[] arguments) {
      return (Frame)arguments[0];
   }

   static {
      if (<unrepresentable>.$assertionsDisabled) {
      }
   }
}
