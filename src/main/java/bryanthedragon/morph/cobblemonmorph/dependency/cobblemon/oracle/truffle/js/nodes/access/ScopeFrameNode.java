package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSFrameSlot;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.Strings;

public abstract class ScopeFrameNode extends JavaScriptBaseNode {
   public static final int PARENT_SCOPE_SLOT_INDEX = 0;
   public static final TruffleString PARENT_SCOPE_IDENTIFIER = Strings.constant("<parent>");
   public static final TruffleString BLOCK_SCOPE_IDENTIFIER = Strings.constant("<blockscope>");
   public static final TruffleString EVAL_SCOPE_IDENTIFIER = Strings.constant("<evalscope>");

   public static ScopeFrameNode createCurrent() {
      return ScopeFrameNode.CurrentFrameNode.instance();
   }

   public static ScopeFrameNode create(int frameLevel) {
      return create(frameLevel, 0, null);
   }

   public static ScopeFrameNode create(int frameLevel, int scopeLevel, JSFrameSlot blockScopeSlot) {
      if (frameLevel == 0) {
         if (scopeLevel == 0) {
            if (blockScopeSlot != null) {
               return new ScopeFrameNode.CurrentBlockScopeFrameNode(blockScopeSlot.getIndex());
            }

            return ScopeFrameNode.CurrentFrameNode.instance();
         }

         if (blockScopeSlot != null) {
            return new ScopeFrameNode.EnclosingScopeFrameNode(scopeLevel, blockScopeSlot.getIndex());
         }
      } else if (scopeLevel == 0) {
         assert frameLevel > 0;

         return ScopeFrameNode.EnclosingFunctionFrameNode.instance(frameLevel);
      }

      return new ScopeFrameNode.EnclosingFunctionScopeFrameNode(frameLevel, scopeLevel);
   }

   public static boolean isBlockScopeFrame(Frame frame) {
      FrameDescriptor desc = frame.getFrameDescriptor();
      return desc.getNumberOfSlots() > 0 && PARENT_SCOPE_IDENTIFIER.equals(desc.getSlotName(0));
   }

   public static Frame getBlockScopeParentFrame(Frame frame) {
      return isBlockScopeFrame(frame) ? (Frame)frame.getObject(0) : null;
   }

   public static Frame getNonBlockScopeParentFrame(Frame frame) {
      Frame parent = frame;

      while (isBlockScopeFrame(parent)) {
         parent = getBlockScopeParentFrame(parent);
      }

      return parent;
   }

   public abstract Frame executeFrame(Frame frame);

   @Override
   public final boolean isAdoptable() {
      return false;
   }

   @NodeInfo(cost = NodeCost.NONE)
   private static final class CurrentBlockScopeFrameNode extends ScopeFrameNode {
      private final int blockScopeSlot;

      private CurrentBlockScopeFrameNode(int blockScopeSlot) {
         this.blockScopeSlot = blockScopeSlot;
      }

      @Override
      public Frame executeFrame(Frame frame) {
         return JSFrameUtil.castMaterializedFrame(frame.getObject(this.blockScopeSlot));
      }
   }

   @NodeInfo(cost = NodeCost.NONE)
   private static final class CurrentFrameNode extends ScopeFrameNode {
      private static final ScopeFrameNode INSTANCE = new ScopeFrameNode.CurrentFrameNode();

      static ScopeFrameNode instance() {
         return INSTANCE;
      }

      @Override
      public Frame executeFrame(Frame frame) {
         return frame;
      }
   }

   private static final class EnclosingFunctionFrameNode extends ScopeFrameNode {
      private final int frameLevel;
      private static final ScopeFrameNode[] STATIC_INSTANCES = new ScopeFrameNode[]{
         ScopeFrameNode.CurrentFrameNode.instance(),
         new ScopeFrameNode.EnclosingFunctionFrameNode(1),
         new ScopeFrameNode.EnclosingFunctionFrameNode(2),
         new ScopeFrameNode.EnclosingFunctionFrameNode(3)
      };

      private EnclosingFunctionFrameNode(int frameLevel) {
         assert frameLevel >= 1;

         this.frameLevel = frameLevel;
      }

      static ScopeFrameNode instance(int frameLevel) {
         return (ScopeFrameNode)(frameLevel < STATIC_INSTANCES.length
            ? STATIC_INSTANCES[frameLevel]
            : new ScopeFrameNode.EnclosingFunctionFrameNode(frameLevel));
      }

      @ExplodeLoop
      @Override
      public Frame executeFrame(Frame frame) {
         MaterializedFrame retFrame = JSFrameUtil.castMaterializedFrame(JSArguments.getEnclosingFrame(frame.getArguments()));
         int level = this.frameLevel;
         if (level > 1) {
            for (int i = 1; i < level; i++) {
               retFrame = JSFrameUtil.castMaterializedFrame(JSArguments.getEnclosingFrame(retFrame.getArguments()));
            }
         }

         return retFrame;
      }
   }

   private static final class EnclosingFunctionScopeFrameNode extends ScopeFrameNode {
      private final int frameLevel;
      private final int scopeLevel;

      EnclosingFunctionScopeFrameNode(int frameLevel, int scopeLevel) {
         this.frameLevel = frameLevel;
         this.scopeLevel = scopeLevel;
      }

      @ExplodeLoop
      @Override
      public Frame executeFrame(Frame frame) {
         Frame retFrame = frame;

         for (int i = 0; i < this.frameLevel; i++) {
            retFrame = JSFrameUtil.castMaterializedFrame(JSArguments.getEnclosingFrame(retFrame.getArguments()));
         }

         for (int i = 0; i < this.scopeLevel; i++) {
            retFrame = JSFrameUtil.castMaterializedFrame(retFrame.getObject(0));
         }

         return retFrame;
      }
   }

   private static final class EnclosingScopeFrameNode extends ScopeFrameNode {
      private final int scopeLevel;
      private final int blockScopeSlot;

      EnclosingScopeFrameNode(int scopeLevel, int blockScopeSlot) {
         assert scopeLevel >= 1;

         this.scopeLevel = scopeLevel;
         this.blockScopeSlot = blockScopeSlot;
      }

      @ExplodeLoop
      @Override
      public Frame executeFrame(Frame frame) {
         Frame retFrame = JSFrameUtil.castMaterializedFrame(frame.getObject(this.blockScopeSlot));

         for (int i = 0; i < this.scopeLevel; i++) {
            retFrame = JSFrameUtil.castMaterializedFrame(retFrame.getObject(0));
         }

         return retFrame;
      }
   }
}
