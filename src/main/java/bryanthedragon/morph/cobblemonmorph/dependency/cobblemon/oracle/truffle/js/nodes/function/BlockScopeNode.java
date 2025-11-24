
package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RepeatingNode;
import com.oracle.truffle.js.nodes.FrameDescriptorProvider;
import com.oracle.truffle.js.nodes.JSFrameSlot;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.control.ResumableNode;
import com.oracle.truffle.js.nodes.control.YieldException;
import com.oracle.truffle.js.nodes.function.NamedEvaluationTargetNode;
import com.oracle.truffle.js.nodes.instrumentation.DeclareTagProvider;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

public abstract class BlockScopeNode
extends NamedEvaluationTargetNode
implements RepeatingNode {
    @Node.Child
    protected JavaScriptNode block;

    protected BlockScopeNode(JavaScriptNode block) {
        this.block = block;
    }

    public static BlockScopeNode create(JavaScriptNode block, JSFrameSlot blockScopeSlot, FrameDescriptor frameDescriptor, JSFrameSlot parentSlot, boolean functionBlock, boolean captureFunctionFrame, boolean generatorFunctionBlock, boolean hasParentBlock, int start2, int end2) {
        return new FrameBlockScopeNode(block, blockScopeSlot.getIndex(), frameDescriptor, parentSlot.getIndex(), functionBlock, captureFunctionFrame, generatorFunctionBlock, hasParentBlock, start2, end2);
    }

    public static BlockScopeNode createVirtual(JavaScriptNode block, int frameStart, int frameEnd) {
        return new VirtualBlockScopeNode(block, frameStart, frameEnd);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        try {
            Object object = this.block.execute(this.appendScopeFrame(frame));
            return object;
        }
        finally {
            this.exitScope(frame);
        }
    }

    @Override
    public void executeVoid(VirtualFrame frame) {
        try {
            this.block.executeVoid(this.appendScopeFrame(frame));
        }
        finally {
            this.exitScope(frame);
        }
    }

    public abstract VirtualFrame appendScopeFrame(VirtualFrame var1);

    public final void exitScope(VirtualFrame frame) {
        this.exitScope(frame, false);
    }

    public abstract void exitScope(VirtualFrame var1, boolean var2);

    public abstract Object getBlockScope(VirtualFrame var1);

    public abstract void setBlockScope(VirtualFrame var1, Object var2);

    @Override
    public boolean executeRepeating(VirtualFrame frame) {
        try {
            boolean bl = ((RepeatingNode)((Object)this.block)).executeRepeating(this.appendScopeFrame(frame));
            return bl;
        }
        finally {
            this.exitScope(frame);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object executeWithName(VirtualFrame frame, Object name) {
        try {
            Object object = ((NamedEvaluationTargetNode)this.block).executeWithName(this.appendScopeFrame(frame), name);
            return object;
        }
        finally {
            this.exitScope(frame);
        }
    }

    public JavaScriptNode getBlock() {
        return this.block;
    }

    public abstract boolean isFunctionBlock();

    @Override
    public boolean isResultAlwaysOfType(Class<?> clazz) {
        return this.block.isResultAlwaysOfType(clazz);
    }

    public int getFrameStart() {
        return -1;
    }

    public int getFrameEnd() {
        return -1;
    }

    public static class VirtualBlockScopeNode
    extends BlockScopeNode
    implements ResumableNode {
        protected final int start;
        protected final int end;
        private static final Object SCOPE_PLACEHOLDER = Null.instance;

        protected VirtualBlockScopeNode(JavaScriptNode block, int start2, int end2) {
            super(block);
            this.start = start2;
            this.end = end2;
        }

        @Override
        public VirtualFrame appendScopeFrame(VirtualFrame frame) {
            return frame;
        }

        @Override
        @ExplodeLoop
        public void exitScope(VirtualFrame frame, boolean yield) {
            if (!yield) {
                for (int i = this.start; i < this.end; ++i) {
                    frame.clear(i);
                }
            }
        }

        @Override
        public Object resume(VirtualFrame frame, int stateSlot) {
            boolean yield = false;
            try {
                Object object = this.block.execute(this.appendScopeFrame(frame));
                return object;
            }
            catch (YieldException e) {
                yield = true;
                throw e;
            }
            finally {
                this.exitScope(frame, yield);
            }
        }

        @Override
        public Object getBlockScope(VirtualFrame frame) {
            return SCOPE_PLACEHOLDER;
        }

        @Override
        public void setBlockScope(VirtualFrame frame, Object state) {
            assert (state == SCOPE_PLACEHOLDER);
        }

        @Override
        public boolean isFunctionBlock() {
            return false;
        }

        @Override
        public int getFrameStart() {
            return this.start;
        }

        @Override
        public int getFrameEnd() {
            return this.end;
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new VirtualBlockScopeNode(VirtualBlockScopeNode.cloneUninitialized(this.block, materializedTags), this.start, this.end);
        }
    }

    public static class FrameBlockScopeNode
    extends BlockScopeNode
    implements ResumableNode.WithObjectState,
    FrameDescriptorProvider {
        protected final int blockScopeSlot;
        protected final int parentSlot;
        protected final FrameDescriptor frameDescriptor;
        protected final boolean functionBlock;
        protected final boolean captureFunctionFrame;
        protected final boolean generatorFunctionBlock;
        protected final boolean hasParentBlock;
        protected final int start;
        protected final int end;

        protected FrameBlockScopeNode(JavaScriptNode block, int blockScopeSlot, FrameDescriptor frameDescriptor, int parentSlot, boolean functionBlock, boolean captureFunctionFrame, boolean generatorFunctionBlock, boolean hasParentBlock, int start2, int end2) {
            super(block);
            this.blockScopeSlot = blockScopeSlot;
            this.parentSlot = parentSlot;
            this.functionBlock = functionBlock;
            this.captureFunctionFrame = captureFunctionFrame;
            this.generatorFunctionBlock = generatorFunctionBlock;
            this.hasParentBlock = hasParentBlock;
            this.start = start2;
            this.end = end2;
            this.frameDescriptor = frameDescriptor;
        }

        @Override
        public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
            if (materializedTags.contains(JSTags.DeclareTag.class) && !DeclareTagProvider.isMaterializedFrameProvider(this)) {
                JavaScriptNode materialized = DeclareTagProvider.createMaterializedBlockNode(this, FrameBlockScopeNode.cloneUninitialized(this.block, materializedTags), this.blockScopeSlot, this.frameDescriptor, this.parentSlot, this.functionBlock, this.captureFunctionFrame, this.generatorFunctionBlock, this.hasParentBlock, this.start, this.end);
                FrameBlockScopeNode.transferSourceSectionAndTags(this, materialized);
                return materialized;
            }
            return this;
        }

        @Override
        public VirtualFrame appendScopeFrame(VirtualFrame frame) {
            Object[] arguments;
            Object parentScopeFrame = frame.getObject(this.blockScopeSlot);
            if (this.captureFunctionFrame) {
                assert (parentScopeFrame == Undefined.instance);
                parentScopeFrame = frame.materialize();
            }
            if (this.hasParentBlock) {
                arguments = JSFrameUtil.castMaterializedFrame(parentScopeFrame).getArguments();
            } else {
                assert (parentScopeFrame == Undefined.instance || this.captureFunctionFrame);
                arguments = JSArguments.createZeroArg(Undefined.instance, JSFrameUtil.getFunctionObjectNoCast(frame));
            }
            MaterializedFrame scopeFrame = Truffle.getRuntime().createVirtualFrame(arguments, this.frameDescriptor).materialize();
            scopeFrame.setObject(this.parentSlot, parentScopeFrame);
            frame.setObject(this.blockScopeSlot, scopeFrame);
            return frame;
        }

        @Override
        public void exitScope(VirtualFrame frame, boolean yield) {
            MaterializedFrame blockScopeFrame = JSFrameUtil.castMaterializedFrame(frame.getObject(this.blockScopeSlot));
            assert (blockScopeFrame.getFrameDescriptor() == this.frameDescriptor) : blockScopeFrame.getFrameDescriptor();
            if (this.generatorFunctionBlock) {
                return;
            }
            Object parentScopeFrame = blockScopeFrame.getObject(this.parentSlot);
            if (this.captureFunctionFrame) {
                assert (((Frame)parentScopeFrame).getFrameDescriptor() == frame.getFrameDescriptor());
                parentScopeFrame = Undefined.instance;
            }
            frame.setObject(this.blockScopeSlot, parentScopeFrame);
            if (!yield && this.start < this.end) {
                this.clearVirtualSlots(frame);
            }
        }

        @ExplodeLoop
        private void clearVirtualSlots(VirtualFrame frame) {
            for (int i = this.start; i < this.end; ++i) {
                frame.clear(i);
            }
        }

        @Override
        public FrameDescriptor getFrameDescriptor() {
            return this.frameDescriptor;
        }

        @Override
        public Object resume(VirtualFrame frame, int stateSlot) {
            Object state = this.getStateAndReset(frame, stateSlot);
            if (state == Undefined.instance) {
                this.appendScopeFrame(frame);
            } else {
                this.setBlockScope(frame, state);
            }
            boolean yield = false;
            try {
                Object object = this.block.execute(frame);
                return object;
            }
            catch (YieldException e) {
                yield = true;
                this.setState(frame, stateSlot, this.getBlockScope(frame));
                throw e;
            }
            finally {
                this.exitScope(frame, yield);
            }
        }

        @Override
        public Object getBlockScope(VirtualFrame frame) {
            return frame.getObject(this.blockScopeSlot);
        }

        @Override
        public void setBlockScope(VirtualFrame frame, Object state) {
            assert (state instanceof MaterializedFrame);
            frame.setObject(this.blockScopeSlot, state);
        }

        @Override
        public boolean isFunctionBlock() {
            return this.functionBlock;
        }

        @Override
        public int getFrameStart() {
            return this.start;
        }

        @Override
        public int getFrameEnd() {
            return this.end;
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new FrameBlockScopeNode(FrameBlockScopeNode.cloneUninitialized(this.block, materializedTags), this.blockScopeSlot, this.frameDescriptor, this.parentSlot, this.functionBlock, this.captureFunctionFrame, this.generatorFunctionBlock, this.hasParentBlock, this.start, this.end);
        }
    }
}

