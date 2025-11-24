
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JSNodeUtil;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanUnaryNode;
import com.oracle.truffle.js.nodes.control.ResumableNode;
import com.oracle.truffle.js.nodes.control.StatementNode;
import com.oracle.truffle.js.nodes.control.YieldException;
import com.oracle.truffle.js.nodes.instrumentation.JSTaggedExecutionNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.unary.JSNotNode;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;
import java.util.concurrent.locks.Lock;

@NodeInfo(shortName="if")
public final class IfNode
extends StatementNode
implements ResumableNode.WithIntState {
    @Node.Child
    private JavaScriptNode condition;
    @Node.Child
    private JavaScriptNode thenPart;
    @Node.Child
    private JavaScriptNode elsePart;
    private final ConditionProfile conditionProfile = ConditionProfile.createCountingProfile();

    public static IfNode create(JavaScriptNode condition2, JavaScriptNode thenPart, JavaScriptNode elsePart) {
        if (condition2 instanceof JSNotNode) {
            JavaScriptNode operand = ((JSNotNode)condition2).getOperand();
            IfNode.transferSourceSectionAddExpressionTag(condition2, operand);
            return new IfNode(operand, elsePart, thenPart);
        }
        return new IfNode(condition2, thenPart, elsePart);
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
        return JSTags.createNodeObjectDescriptor("type", JSTags.ControlFlowRootTag.Type.Conditional.name());
    }

    @Override
    public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
        if (IfNode.hasMaterializationTag(materializedTags) && this.materializationNeeded()) {
            JavaScriptNode newElsePart;
            JavaScriptNode newCondition = JSTaggedExecutionNode.createForInput(this.condition, JSTags.ControlFlowBranchTag.class, JSTags.createNodeObjectDescriptor("type", JSTags.ControlFlowBranchTag.Type.Condition.name()), materializedTags);
            JavaScriptNode newThenPart = this.thenPart != null ? JSTaggedExecutionNode.createForInput(this.thenPart, JSTags.ControlFlowBlockTag.class, materializedTags) : null;
            JavaScriptNode javaScriptNode = newElsePart = this.elsePart != null ? JSTaggedExecutionNode.createForInput(this.elsePart, JSTags.ControlFlowBlockTag.class, materializedTags) : null;
            if (newCondition == this.condition && newThenPart == this.thenPart && newElsePart == this.elsePart) {
                return this;
            }
            if (newCondition == this.condition) {
                newCondition = IfNode.cloneUninitialized(this.condition, materializedTags);
            }
            if (newThenPart == this.thenPart) {
                newThenPart = IfNode.cloneUninitialized(this.thenPart, materializedTags);
            }
            if (newElsePart == this.elsePart) {
                newElsePart = IfNode.cloneUninitialized(this.elsePart, materializedTags);
            }
            IfNode newIf = IfNode.create(newCondition, newThenPart, newElsePart);
            IfNode.transferSourceSectionAndTags(this, newIf);
            return newIf;
        }
        return this;
    }

    private boolean materializationNeeded() {
        return !JSNodeUtil.isTaggedNode(this.condition) || this.elsePart != null && !JSNodeUtil.isTaggedNode(this.elsePart) || this.thenPart != null && !JSNodeUtil.isTaggedNode(this.thenPart);
    }

    private static boolean hasMaterializationTag(Set<Class<? extends Tag>> materializedTags) {
        return materializedTags.contains(JSTags.ControlFlowRootTag.class) || materializedTags.contains(JSTags.ControlFlowBranchTag.class) || materializedTags.contains(JSTags.ControlFlowBlockTag.class);
    }

    private IfNode(JavaScriptNode condition2, JavaScriptNode thenPart, JavaScriptNode elsePart) {
        this.condition = condition2;
        this.thenPart = thenPart;
        this.elsePart = elsePart;
    }

    public JavaScriptNode getThenPart() {
        return this.thenPart;
    }

    public JavaScriptNode getElsePart() {
        return this.elsePart;
    }

    public JavaScriptNode getCondition() {
        return this.condition;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        if (this.conditionProfile.profile(this.executeCondition(frame))) {
            if (this.thenPart != null) {
                return this.thenPart.execute(frame);
            }
            return EMPTY;
        }
        if (this.elsePart != null) {
            return this.elsePart.execute(frame);
        }
        return EMPTY;
    }

    @Override
    public void executeVoid(VirtualFrame frame) {
        if (this.conditionProfile.profile(this.executeCondition(frame))) {
            if (this.thenPart != null) {
                this.thenPart.executeVoid(frame);
            }
        } else if (this.elsePart != null) {
            this.elsePart.executeVoid(frame);
        }
    }

    @Override
    public Object resume(VirtualFrame frame, int stateSlot) {
        int index = this.getStateAsIntAndReset(frame, stateSlot);
        if (index == 0 && this.conditionProfile.profile(this.executeCondition(frame)) || index == 1) {
            try {
                if (this.thenPart != null) {
                    return this.thenPart.execute(frame);
                }
                return EMPTY;
            }
            catch (YieldException e) {
                this.setStateAsInt(frame, stateSlot, 1);
                throw e;
            }
        }
        assert (index == 0 || index == 2);
        try {
            if (this.elsePart != null) {
                return this.elsePart.execute(frame);
            }
            return EMPTY;
        }
        catch (YieldException e) {
            this.setStateAsInt(frame, stateSlot, 2);
            throw e;
        }
    }

    @Override
    public boolean isResultAlwaysOfType(Class<?> clazz) {
        return IfNode.isResultAlwaysOfType(this.thenPart, clazz) && IfNode.isResultAlwaysOfType(this.elsePart, clazz);
    }

    private static boolean isResultAlwaysOfType(JavaScriptNode child, Class<?> clazz) {
        if (child == null) {
            return clazz == Undefined.class;
        }
        return child.isResultAlwaysOfType(clazz);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return new IfNode(IfNode.cloneUninitialized(this.condition, materializedTags), IfNode.cloneUninitialized(this.thenPart, materializedTags), IfNode.cloneUninitialized(this.elsePart, materializedTags));
    }

    protected boolean executeCondition(VirtualFrame frame) {
        try {
            return this.condition.executeBoolean(frame);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            JavaScriptNode node = this.insertToBoolean();
            if (node instanceof JSConstantNode) {
                try {
                    return node.executeBoolean(frame);
                }
                catch (UnexpectedResultException e) {
                    throw CompilerDirectives.shouldNotReachHere(e);
                }
            }
            if (node instanceof JSUnaryNode) {
                return (Boolean)((JSUnaryNode)node).execute(frame, ex.getResult());
            }
            throw CompilerDirectives.shouldNotReachHere("Unexpected result node of JSToBooleanNode.create");
        }
    }

    private JavaScriptNode insertToBoolean() {
        CompilerAsserts.neverPartOfCompilation();
        Lock lock = this.getLock();
        lock.lock();
        JavaScriptNode cond = this.condition;
        try {
            if (!(cond instanceof JSToBooleanUnaryNode)) {
                this.condition = cond = this.insert(JSToBooleanUnaryNode.create(cond));
            }
        }
        finally {
            lock.unlock();
        }
        return cond;
    }
}

