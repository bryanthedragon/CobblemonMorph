package com.oracle.truffle.api;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameInstanceVisitor;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.RepeatingNode;

public interface TruffleRuntime {
   String getName();

   DirectCallNode createDirectCallNode(CallTarget target);

   LoopNode createLoopNode(RepeatingNode body);

   IndirectCallNode createIndirectCallNode();

   Assumption createAssumption();

   Assumption createAssumption(String name);

   VirtualFrame createVirtualFrame(Object[] arguments, FrameDescriptor frameDescriptor);

   MaterializedFrame createMaterializedFrame(Object[] arguments);

   MaterializedFrame createMaterializedFrame(Object[] arguments, FrameDescriptor frameDescriptor);

   default <T> T iterateFrames(FrameInstanceVisitor<T> visitor) {
      return this.iterateFrames(visitor, 0);
   }

   default <T> T iterateFrames(FrameInstanceVisitor<T> visitor, int skipFrames) {
      throw new AbstractMethodError();
   }

   <T> T getCapability(Class<T> capability);

   void notifyTransferToInterpreter();

   boolean isProfilingEnabled();
}
