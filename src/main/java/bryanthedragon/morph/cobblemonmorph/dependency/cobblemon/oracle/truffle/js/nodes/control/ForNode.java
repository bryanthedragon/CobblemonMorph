
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.NodeUtil;
import com.oracle.truffle.api.nodes.RepeatingNode;
import com.oracle.truffle.js.nodes.JSNodeUtil;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.control.AbstractRepeatingNode;
import com.oracle.truffle.js.nodes.control.DiscardResultNode;
import com.oracle.truffle.js.nodes.control.ResumableNode;
import com.oracle.truffle.js.nodes.control.StatementNode;
import com.oracle.truffle.js.nodes.control.YieldException;
import com.oracle.truffle.js.nodes.function.IterationScopeNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTaggedExecutionNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.Pair;
import java.util.Set;

@NodeInfo(shortName="for")
public final class ForNode
extends StatementNode
implements ResumableNode.WithObjectState {
    @Node.Child
    private LoopNode loop;
    @Node.Child
    private IterationScopeNode copy;

    private ForNode(RepeatingNode repeatingNode, IterationScopeNode copy) {
        this(Truffle.getRuntime().createLoopNode(repeatingNode), copy);
    }

    private ForNode(LoopNode loopNode, IterationScopeNode copy) {
        this.copy = copy;
        this.loop = loopNode;
    }

    public static RepeatingNode createForRepeatingNode(JavaScriptNode condition2, JavaScriptNode body, JavaScriptNode modify, IterationScopeNode copy, JavaScriptNode isFirstNode, JavaScriptNode setNotFirstNode) {
        JavaScriptNode nonVoidBody = body instanceof DiscardResultNode ? ((DiscardResultNode)body).getOperand() : body;
        return new ForRepeatingNode(condition2, nonVoidBody, modify, copy, isFirstNode, setNotFirstNode);
    }

    public static ForNode createFor(LoopNode loopNode) {
        ForRepeatingNode forRepeatingNode = (ForRepeatingNode)loopNode.getRepeatingNode();
        return new ForNode(loopNode, NodeUtil.cloneNode(forRepeatingNode.copy));
    }

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        if (tag == JSTags.ControlFlowRootTag.class) {
            return true;
        }
        return super.hasTag(tag);
    }

    @Override
    public Object getNodeObject() {
        return JSTags.createNodeObjectDescriptor("type", JSTags.ControlFlowRootTag.Type.ForIteration.name());
    }

    @Override
    public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
        if (ForNode.hasMaterializationTag(materializedTags) && AbstractRepeatingNode.materializationNeeded(this.loop.getRepeatingNode())) {
            IterationScopeNode newCopy = ForNode.cloneUninitialized(this.copy, materializedTags);
            AbstractRepeatingNode materializedLoop = (AbstractRepeatingNode)((AbstractRepeatingNode)this.loop.getRepeatingNode()).materializeInstrumentableNodes(materializedTags);
            if (materializedLoop == this.loop.getRepeatingNode()) {
                materializedLoop = ForNode.cloneUninitialized((AbstractRepeatingNode)this.loop.getRepeatingNode(), materializedTags);
            }
            ForNode.transferSourceSection(this, materializedLoop.bodyNode);
            ForNode materializedNode = new ForNode(materializedLoop, newCopy);
            ForNode.transferSourceSectionAndTags(this, materializedNode);
            return materializedNode;
        }
        return this;
    }

    private static boolean hasMaterializationTag(Set<Class<? extends Tag>> materializedTags) {
        return materializedTags.contains(JSTags.ControlFlowRootTag.class) || materializedTags.contains(JSTags.ControlFlowBlockTag.class) || materializedTags.contains(JSTags.ControlFlowBranchTag.class);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        this.executeVoid(frame);
        return EMPTY;
    }

    @Override
    public void executeVoid(VirtualFrame frame) {
        VirtualFrame prevFrame = this.copy.execute(frame);
        try {
            this.loop.execute(frame);
        }
        finally {
            this.copy.exitScope(frame, prevFrame);
        }
    }

    @Override
    public Object resume(VirtualFrame frame, int stateSlot) {
        Object state = this.getStateAndReset(frame, stateSlot);
        VirtualFrame prevFrame = state == Undefined.instance ? this.copy.execute(frame) : JSFrameUtil.castMaterializedFrame(state);
        boolean yielded = false;
        try {
            this.loop.execute(frame);
        }
        catch (YieldException e) {
            yielded = true;
            this.setState(frame, stateSlot, prevFrame);
            throw e;
        }
        finally {
            if (!yielded) {
                this.copy.exitScope(frame, prevFrame);
            }
        }
        return EMPTY;
    }

    @Override
    public boolean isResultAlwaysOfType(Class<?> clazz) {
        assert (EMPTY == Undefined.instance);
        return clazz == Undefined.class;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return new ForNode((RepeatingNode)((Object)ForNode.cloneUninitialized((JavaScriptNode)((Object)this.loop.getRepeatingNode()), materializedTags)), ForNode.cloneUninitialized(this.copy, materializedTags));
    }

    public LoopNode getLoopNode() {
        return this.loop;
    }

    private static final class ForRepeatingNode
    extends AbstractRepeatingNode
    implements ResumableNode.WithObjectState {
        @Node.Child
        private JavaScriptNode modify;
        @Node.Child
        private IterationScopeNode copy;
        @Node.Child
        private JavaScriptNode isFirstNode;
        @Node.Child
        private JavaScriptNode setNotFirstNode;

        ForRepeatingNode(JavaScriptNode condition2, JavaScriptNode body, JavaScriptNode modify, IterationScopeNode copy, JavaScriptNode isFirstNode, JavaScriptNode setNotFirstNode) {
            super(condition2, body);
            this.modify = modify;
            this.copy = copy;
            this.isFirstNode = isFirstNode;
            this.setNotFirstNode = setNotFirstNode;
        }

        @Override
        public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
            if (ForNode.hasMaterializationTag(materializedTags) && this.materializationNeeded()) {
                JavaScriptNode newBody = JSTaggedExecutionNode.createFor(this.bodyNode, JSTags.ControlFlowBlockTag.class, materializedTags);
                JavaScriptNode newCondition = JSTaggedExecutionNode.createForInput(this.conditionNode, JSTags.ControlFlowBranchTag.class, JSTags.createNodeObjectDescriptor("type", JSTags.ControlFlowBranchTag.Type.Condition.name()), materializedTags);
                if (newBody == this.bodyNode && newCondition == this.conditionNode) {
                    return this;
                }
                if (newBody == this.bodyNode) {
                    newBody = ForRepeatingNode.cloneUninitialized(this.bodyNode, materializedTags);
                }
                if (newCondition == this.conditionNode) {
                    newCondition = ForRepeatingNode.cloneUninitialized(this.conditionNode, materializedTags);
                }
                ForRepeatingNode newLoop = new ForRepeatingNode(newCondition, newBody, ForRepeatingNode.cloneUninitialized(this.modify, materializedTags), ForRepeatingNode.cloneUninitialized(this.copy, materializedTags), this.isFirstNode, ForRepeatingNode.cloneUninitialized(this.setNotFirstNode, materializedTags));
                ForRepeatingNode.transferSourceSectionAndTags(this, newLoop);
                return newLoop;
            }
            return this;
        }

        private boolean materializationNeeded() {
            return !JSNodeUtil.isTaggedNode(this.bodyNode);
        }

        @Override
        public boolean executeRepeating(VirtualFrame frame) {
            VirtualFrame prevFrame = this.copy.execute(frame);
            if (this.notFirstIteration(frame)) {
                this.modify.executeVoid(frame);
            }
            if (this.executeCondition(frame)) {
                this.executeBody(frame);
                this.copy.executeCopy(frame, prevFrame);
                return true;
            }
            return false;
        }

        private boolean notFirstIteration(VirtualFrame frame) {
            if (StatementNode.executeConditionAsBoolean(frame, this.isFirstNode)) {
                this.setNotFirstNode.executeVoid(frame);
                return false;
            }
            return true;
        }

        @Override
        public Object resume(VirtualFrame frame, int stateSlot) {
            int index;
            MaterializedFrame prevFrame;
            Object state = this.getStateAndReset(frame, stateSlot);
            if (state == Undefined.instance) {
                prevFrame = this.copy.execute(frame).materialize();
                index = 0;
            } else {
                Pair statePair = (Pair)state;
                prevFrame = JSFrameUtil.castMaterializedFrame(statePair.getFirst());
                index = (Integer)statePair.getSecond();
            }
            if (index <= 0 && this.notFirstIteration(frame)) {
                try {
                    this.modify.executeVoid(frame);
                }
                catch (YieldException e) {
                    this.setState(frame, stateSlot, new Pair<MaterializedFrame, Integer>(prevFrame, 0));
                    throw e;
                }
            }
            boolean condition2 = true;
            if (index <= 1) {
                try {
                    condition2 = this.executeCondition(frame);
                }
                catch (YieldException e) {
                    this.setState(frame, stateSlot, new Pair<MaterializedFrame, Integer>(prevFrame, 1));
                    throw e;
                }
            }
            if (condition2) {
                try {
                    this.executeBody(frame);
                }
                catch (YieldException e) {
                    this.setState(frame, stateSlot, new Pair<MaterializedFrame, Integer>(prevFrame, 2));
                    throw e;
                }
                this.copy.executeCopy(frame, prevFrame);
                return true;
            }
            return false;
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new ForRepeatingNode(ForRepeatingNode.cloneUninitialized(this.conditionNode, materializedTags), ForRepeatingNode.cloneUninitialized(this.bodyNode, materializedTags), ForRepeatingNode.cloneUninitialized(this.modify, materializedTags), ForRepeatingNode.cloneUninitialized(this.copy, materializedTags), ForRepeatingNode.cloneUninitialized(this.isFirstNode, materializedTags), ForRepeatingNode.cloneUninitialized(this.setNotFirstNode, materializedTags));
        }
    }
}

