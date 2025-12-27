package com.oracle.truffle.api.frame;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.nodes.Node;

public interface FrameInstance {
   Frame getFrame(FrameInstance.FrameAccess access);

   boolean isVirtualFrame();

   default int getCompilationTier() {
      return 0;
   }

   default boolean isCompilationRoot() {
      return true;
   }

   Node getCallNode();

   CallTarget getCallTarget();

   public static enum FrameAccess {
      READ_ONLY,
      READ_WRITE,
      MATERIALIZE;
   }
}
