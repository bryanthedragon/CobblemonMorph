
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

public abstract class ScopeFrameNode
extends JavaScriptBaseNode {
    public static final int PARENT_SCOPE_SLOT_INDEX = 0;
    public static final TruffleString PARENT_SCOPE_IDENTIFIER = Strings.constant("<parent>");
    public static final TruffleString BLOCK_SCOPE_IDENTIFIER = Strings.constant("<blockscope>");
    public static final TruffleString EVAL_SCOPE_IDENTIFIER = Strings.constant("<evalscope>");

    public static ScopeFrameNode createCurrent() {
        return CurrentFrameNode.instance();
    }

    public static ScopeFrameNode create(int frameLevel) {
        return ScopeFrameNode.create(frameLevel, 0, null);
    }

    public static ScopeFrameNode create(int frameLevel, int scopeLevel, JSFrameSlot blockScopeSlot) {
        if (frameLevel == 0) {
            if (scopeLevel == 0) {
                if (blockScopeSlot != null) {
                    return new CurrentBlockScopeFrameNode(blockScopeSlot.getIndex());
                }
                return CurrentFrameNode.instance();
            }
            if (blockScopeSlot != null) {
                return new EnclosingScopeFrameNode(scopeLevel, blockScopeSlot.getIndex());
            }
        } else if (scopeLevel == 0) {
            assert (frameLevel > 0);
            return EnclosingFunctionFrameNode.instance(frameLevel);
        }
        return new EnclosingFunctionScopeFrameNode(frameLevel, scopeLevel);
    }

    public static boolean isBlockScopeFrame(Frame frame) {
        FrameDescriptor desc = frame.getFrameDescriptor();
        return desc.getNumberOfSlots() > 0 && PARENT_SCOPE_IDENTIFIER.equals(desc.getSlotName(0));
    }

    public static Frame getBlockScopeParentFrame(Frame frame) {
        if (ScopeFrameNode.isBlockScopeFrame(frame)) {
            return (Frame)frame.getObject(0);
        }
        return null;
    }

    public static Frame getNonBlockScopeParentFrame(Frame frame) {
        Frame parent = frame;
        while (ScopeFrameNode.isBlockScopeFrame(parent)) {
            parent = ScopeFrameNode.getBlockScopeParentFrame(parent);
        }
        return parent;
    }

    public abstract Frame executeFrame(Frame var1);

    @Override
    public final boolean isAdoptable() {
        return false;
    }

    private static final class EnclosingFunctionFrameNode
    extends ScopeFrameNode {
        private final int frameLevel;
        private static final ScopeFrameNode[] STATIC_INSTANCES = new ScopeFrameNode[]{CurrentFrameNode.instance(), new EnclosingFunctionFrameNode(1), new EnclosingFunctionFrameNode(2), new EnclosingFunctionFrameNode(3)};

        private EnclosingFunctionFrameNode(int frameLevel) {
            assert (frameLevel >= 1);
            this.frameLevel = frameLevel;
        }

        static ScopeFrameNode instance(int frameLevel) {
            if (frameLevel < STATIC_INSTANCES.length) {
                return STATIC_INSTANCES[frameLevel];
            }
            return new EnclosingFunctionFrameNode(frameLevel);
        }

        @Override
        @ExplodeLoop
        public Frame executeFrame(Frame frame) {
            MaterializedFrame retFrame = JSFrameUtil.castMaterializedFrame(JSArguments.getEnclosingFrame(frame.getArguments()));
            int level = this.frameLevel;
            if (level > 1) {
                for (int i = 1; i < level; ++i) {
                    retFrame = JSFrameUtil.castMaterializedFrame(JSArguments.getEnclosingFrame(retFrame.getArguments()));
                }
            }
            return retFrame;
        }
    }

    private static final class EnclosingFunctionScopeFrameNode
    extends ScopeFrameNode {
        private final int frameLevel;
        private final int scopeLevel;

        EnclosingFunctionScopeFrameNode(int frameLevel, int scopeLevel) {
            this.frameLevel = frameLevel;
            this.scopeLevel = scopeLevel;
        }

        @Override
        @ExplodeLoop
        public Frame executeFrame(Frame frame) {
            int i;
            Frame retFrame = frame;
            for (i = 0; i < this.frameLevel; ++i) {
                retFrame = JSFrameUtil.castMaterializedFrame(JSArguments.getEnclosingFrame(retFrame.getArguments()));
            }
            for (i = 0; i < this.scopeLevel; ++i) {
                retFrame = JSFrameUtil.castMaterializedFrame(retFrame.getObject(0));
            }
            return retFrame;
        }
    }

    private static final class EnclosingScopeFrameNode
    extends ScopeFrameNode {
        private final int scopeLevel;
        private final int blockScopeSlot;

        EnclosingScopeFrameNode(int scopeLevel, int blockScopeSlot) {
            assert (scopeLevel >= 1);
            this.scopeLevel = scopeLevel;
            this.blockScopeSlot = blockScopeSlot;
        }

        @Override
        @ExplodeLoop
        public Frame executeFrame(Frame frame) {
            MaterializedFrame retFrame = JSFrameUtil.castMaterializedFrame(frame.getObject(this.blockScopeSlot));
            for (int i = 0; i < this.scopeLevel; ++i) {
                retFrame = JSFrameUtil.castMaterializedFrame(retFrame.getObject(0));
            }
            return retFrame;
        }
    }

    @NodeInfo(cost=NodeCost.NONE)
    private static final class CurrentBlockScopeFrameNode
    extends ScopeFrameNode {
        private final int blockScopeSlot;

        private CurrentBlockScopeFrameNode(int blockScopeSlot) {
            this.blockScopeSlot = blockScopeSlot;
        }

        @Override
        public Frame executeFrame(Frame frame) {
            return JSFrameUtil.castMaterializedFrame(frame.getObject(this.blockScopeSlot));
        }
    }

    @NodeInfo(cost=NodeCost.NONE)
    private static final class CurrentFrameNode
    extends ScopeFrameNode {
        private static final ScopeFrameNode INSTANCE = new CurrentFrameNode();

        private CurrentFrameNode() {
        }

        static ScopeFrameNode instance() {
            return INSTANCE;
        }

        @Override
        public Frame executeFrame(Frame frame) {
            return frame;
        }
    }
}

