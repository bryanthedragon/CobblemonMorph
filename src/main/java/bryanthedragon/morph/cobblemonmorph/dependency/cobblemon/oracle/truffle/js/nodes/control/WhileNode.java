
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.RepeatingNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.control.AbstractRepeatingNode;
import com.oracle.truffle.js.nodes.control.DiscardResultNode;
import com.oracle.truffle.js.nodes.control.ResumableNode;
import com.oracle.truffle.js.nodes.control.StatementNode;
import com.oracle.truffle.js.nodes.control.YieldException;
import com.oracle.truffle.js.nodes.instrumentation.JSTaggedExecutionNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

@NodeInfo(shortName="while")
public final class WhileNode
extends StatementNode {
    @Node.Child
    private LoopNode loop;
    private final JSTags.ControlFlowRootTag.Type loopType;

    private WhileNode(RepeatingNode repeatingNode, JSTags.ControlFlowRootTag.Type type) {
        this(Truffle.getRuntime().createLoopNode(repeatingNode), type);
    }

    private WhileNode(LoopNode loopNode, JSTags.ControlFlowRootTag.Type type) {
        this.loop = loopNode;
        this.loopType = type;
    }

    private static JavaScriptNode createWhileDo(LoopNode loopNode, JSTags.ControlFlowRootTag.Type type) {
        return new WhileNode(loopNode, type);
    }

    public static RepeatingNode createWhileDoRepeatingNode(JavaScriptNode condition2, JavaScriptNode body) {
        JavaScriptNode nonVoidBody = body instanceof DiscardResultNode ? ((DiscardResultNode)body).getOperand() : body;
        return new WhileDoRepeatingNode(condition2, nonVoidBody);
    }

    public static JavaScriptNode createWhileDo(LoopNode loopNode) {
        return WhileNode.createWhileDo(loopNode, JSTags.ControlFlowRootTag.Type.WhileIteration);
    }

    public static JavaScriptNode createDesugaredFor(LoopNode loopNode) {
        return WhileNode.createWhileDo(loopNode, JSTags.ControlFlowRootTag.Type.ForIteration);
    }

    public static JavaScriptNode createDesugaredForOf(LoopNode loopNode) {
        return WhileNode.createWhileDo(loopNode, JSTags.ControlFlowRootTag.Type.ForOfIteration);
    }

    public static JavaScriptNode createDesugaredForIn(LoopNode loopNode) {
        return WhileNode.createWhileDo(loopNode, JSTags.ControlFlowRootTag.Type.ForInIteration);
    }

    public static JavaScriptNode createDesugaredForAwaitOf(LoopNode loopNode) {
        return WhileNode.createWhileDo(loopNode, JSTags.ControlFlowRootTag.Type.ForAwaitOfIteration);
    }

    public static RepeatingNode createDoWhileRepeatingNode(JavaScriptNode condition2, JavaScriptNode body) {
        JavaScriptNode nonVoidBody = body instanceof DiscardResultNode ? ((DiscardResultNode)body).getOperand() : body;
        return new DoWhileRepeatingNode(condition2, nonVoidBody);
    }

    public static JavaScriptNode createDoWhile(LoopNode loopNode) {
        return new WhileNode(loopNode, JSTags.ControlFlowRootTag.Type.DoWhileIteration);
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
        return JSTags.createNodeObjectDescriptor("type", this.loopType.name());
    }

    @Override
    public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
        if (WhileNode.hasMaterializationTag(materializedTags) && AbstractRepeatingNode.materializationNeeded(this.loop.getRepeatingNode()) && this.loop.getRepeatingNode() instanceof AbstractRepeatingNode) {
            WhileNode materialized;
            AbstractRepeatingNode repeatingNode = (AbstractRepeatingNode)this.loop.getRepeatingNode();
            JavaScriptNode bodyNode = JSTaggedExecutionNode.createFor(repeatingNode.bodyNode, JSTags.ControlFlowBlockTag.class, materializedTags);
            JavaScriptNode conditionNode = JSTaggedExecutionNode.createForInput(repeatingNode.conditionNode, JSTags.ControlFlowBranchTag.class, JSTags.createNodeObjectDescriptor("type", JSTags.ControlFlowBranchTag.Type.Condition.name()), materializedTags);
            if (bodyNode == repeatingNode.bodyNode && conditionNode == repeatingNode.conditionNode) {
                return this;
            }
            if (bodyNode == repeatingNode.bodyNode) {
                bodyNode = WhileNode.cloneUninitialized(repeatingNode.bodyNode, materializedTags);
            }
            if (conditionNode == repeatingNode.conditionNode) {
                conditionNode = WhileNode.cloneUninitialized(repeatingNode.conditionNode, materializedTags);
            }
            WhileNode.transferSourceSection(this, bodyNode);
            if (repeatingNode instanceof DoWhileRepeatingNode) {
                materialized = new WhileNode(new DoWhileRepeatingNode(conditionNode, bodyNode), this.loopType);
            } else {
                assert (repeatingNode instanceof WhileDoRepeatingNode);
                materialized = new WhileNode(new WhileDoRepeatingNode(conditionNode, bodyNode), this.loopType);
            }
            WhileNode.transferSourceSectionAndTags(this, materialized);
            return materialized;
        }
        return this;
    }

    private static boolean hasMaterializationTag(Set<Class<? extends Tag>> materializedTags) {
        return materializedTags.contains(JSTags.ControlFlowRootTag.class) || materializedTags.contains(JSTags.ControlFlowBlockTag.class) || materializedTags.contains(JSTags.ControlFlowBranchTag.class);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return new WhileNode((RepeatingNode)((Object)WhileNode.cloneUninitialized((JavaScriptNode)((Object)this.loop.getRepeatingNode()), materializedTags)), this.loopType);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        this.loop.execute(frame);
        return EMPTY;
    }

    @Override
    public void executeVoid(VirtualFrame frame) {
        this.loop.execute(frame);
    }

    @Override
    public boolean isResultAlwaysOfType(Class<?> clazz) {
        assert (EMPTY == Undefined.instance);
        return clazz == Undefined.class;
    }

    public LoopNode getLoopNode() {
        return this.loop;
    }

    private static final class WhileDoRepeatingNode
    extends AbstractRepeatingNode
    implements ResumableNode.WithIntState {
        WhileDoRepeatingNode(JavaScriptNode condition2, JavaScriptNode body) {
            super(condition2, body);
        }

        @Override
        public boolean executeRepeating(VirtualFrame frame) {
            if (this.executeCondition(frame)) {
                this.executeBody(frame);
                return true;
            }
            return false;
        }

        @Override
        public Object resume(VirtualFrame frame, int stateSlot) {
            int index = this.getStateAsIntAndReset(frame, stateSlot);
            if (index != 0 || this.executeCondition(frame)) {
                try {
                    this.executeBody(frame);
                }
                catch (YieldException e) {
                    this.setStateAsInt(frame, stateSlot, 1);
                    throw e;
                }
                return true;
            }
            return false;
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new WhileDoRepeatingNode(WhileDoRepeatingNode.cloneUninitialized(this.conditionNode, materializedTags), WhileDoRepeatingNode.cloneUninitialized(this.bodyNode, materializedTags));
        }
    }

    private static final class DoWhileRepeatingNode
    extends AbstractRepeatingNode
    implements ResumableNode.WithIntState {
        DoWhileRepeatingNode(JavaScriptNode condition2, JavaScriptNode body) {
            super(condition2, body);
        }

        @Override
        public boolean executeRepeating(VirtualFrame frame) {
            this.executeBody(frame);
            return this.executeCondition(frame);
        }

        @Override
        public Object resume(VirtualFrame frame, int stateSlot) {
            int index = this.getStateAsIntAndReset(frame, stateSlot);
            if (index == 0) {
                this.executeBody(frame);
            }
            try {
                return this.executeCondition(frame);
            }
            catch (YieldException e) {
                this.setStateAsInt(frame, stateSlot, 1);
                throw e;
            }
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new DoWhileRepeatingNode(DoWhileRepeatingNode.cloneUninitialized(this.conditionNode, materializedTags), DoWhileRepeatingNode.cloneUninitialized(this.bodyNode, materializedTags));
        }
    }
}

