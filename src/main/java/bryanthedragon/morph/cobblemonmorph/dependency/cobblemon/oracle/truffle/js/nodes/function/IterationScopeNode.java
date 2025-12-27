package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode;
import com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import java.util.Set;

public abstract class IterationScopeNode extends JavaScriptNode {
   public static IterationScopeNode create(FrameDescriptor frameDescriptor, JSReadFrameSlotNode[] reads, JSWriteFrameSlotNode[] writes, int blockScopeSlot) {
      return new IterationScopeNode.FrameIterationScopeNode(frameDescriptor, reads, writes, blockScopeSlot);
   }

   public abstract VirtualFrame execute(VirtualFrame frame);

   public abstract void executeCopy(VirtualFrame toFrame, VirtualFrame fromFrame);

   public abstract void exitScope(VirtualFrame frame, VirtualFrame prevFrame);

   public static final class FrameIterationScopeNode extends IterationScopeNode {
      private final FrameDescriptor frameDescriptor;
      @Node.Children
      private final JSReadFrameSlotNode[] reads;
      @Node.Children
      private final JSWriteFrameSlotNode[] writes;
      private final int blockScopeSlot;

      public FrameIterationScopeNode(FrameDescriptor frameDescriptor, JSReadFrameSlotNode[] reads, JSWriteFrameSlotNode[] writes, int blockScopeSlot) {
         this.frameDescriptor = frameDescriptor;
         this.reads = reads;
         this.writes = writes;
         this.blockScopeSlot = blockScopeSlot;

         assert reads.length == writes.length;
      }

      @Override
      public VirtualFrame execute(VirtualFrame frame) {
         VirtualFrame prevFrame = JSFrameUtil.castMaterializedFrame(frame.getObject(this.blockScopeSlot));
         VirtualFrame nextFrame = Truffle.getRuntime().createVirtualFrame(prevFrame.getArguments(), this.frameDescriptor).materialize();
         copyParentSlot(nextFrame, prevFrame);
         this.copySlots(nextFrame, prevFrame);
         frame.setObject(this.blockScopeSlot, nextFrame);
         return prevFrame;
      }

      private static void copyParentSlot(VirtualFrame nextFrame, VirtualFrame prevFrame) {
         nextFrame.setObject(0, prevFrame.getObject(0));
      }

      @Override
      public void executeCopy(VirtualFrame frame, VirtualFrame prevFrame) {
         VirtualFrame nextFrame = JSFrameUtil.castMaterializedFrame(frame.getObject(this.blockScopeSlot));

         assert nextFrame.getObject(0) == prevFrame.getObject(0);

         this.copySlots(prevFrame, nextFrame);
         this.exitScope(frame, prevFrame);
      }

      @Override
      public void exitScope(VirtualFrame frame, VirtualFrame prevFrame) {
         frame.setObject(this.blockScopeSlot, prevFrame);
      }

      @ExplodeLoop
      private void copySlots(VirtualFrame nextFrame, VirtualFrame frame) {
         for (int i = 0; i < this.reads.length; i++) {
            this.writes[i].executeWithFrame(nextFrame, this.reads[i].execute(frame));
         }
      }

      public FrameDescriptor getFrameDescriptor() {
         return this.frameDescriptor;
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new IterationScopeNode.FrameIterationScopeNode(
            this.frameDescriptor, cloneUninitialized(this.reads, materializedTags), cloneUninitialized(this.writes, materializedTags), this.blockScopeSlot
         );
      }
   }
}
