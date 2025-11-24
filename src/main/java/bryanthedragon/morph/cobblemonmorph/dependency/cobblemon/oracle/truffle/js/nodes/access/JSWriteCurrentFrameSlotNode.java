
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Bind;
import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSWriteCurrentFrameSlotNodeGen;
import com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;
import com.oracle.truffle.js.runtime.SafeInteger;
import java.util.Set;

abstract class JSWriteCurrentFrameSlotNode
extends JSWriteFrameSlotNode {
    @Node.Child
    @Executed
    JavaScriptNode rhsNode;

    protected JSWriteCurrentFrameSlotNode(int slot, Object identifier, JavaScriptNode rhsNode) {
        super(slot, identifier);
        this.rhsNode = rhsNode;
    }

    @Specialization(guards={"isBooleanKind(frame)"})
    protected final boolean doBoolean(VirtualFrame frame, boolean value2) {
        frame.setBoolean(this.slot, value2);
        return value2;
    }

    @Specialization(guards={"(isIntegerKind(frame, kind) || isLongKind(frame, kind)) || isDoubleKind(frame, kind)"})
    protected final int doInteger(VirtualFrame frame, int value2, @Bind(value="getFrameDescriptor(frame).getSlotKind(slot)") FrameSlotKind kind) {
        if (this.isIntegerKind(frame, kind)) {
            frame.setInt(this.slot, value2);
        } else if (this.isLongKind(frame, kind)) {
            frame.setLong(this.slot, value2);
        } else if (this.isDoubleKind(frame, kind)) {
            frame.setDouble(this.slot, value2);
        }
        return value2;
    }

    @Specialization(guards={"isLongKind(frame)"})
    protected final SafeInteger doSafeInteger(VirtualFrame frame, SafeInteger value2) {
        frame.setLong(this.slot, value2.longValue());
        return value2;
    }

    @Specialization
    protected final long doLong(VirtualFrame frame, long value2) {
        this.ensureObjectKind(frame);
        frame.setObject(this.slot, value2);
        return value2;
    }

    @Specialization(guards={"isDoubleKind(frame)"}, replaces={"doInteger", "doSafeInteger"})
    protected final double doDouble(VirtualFrame frame, double value2) {
        frame.setDouble(this.slot, value2);
        return value2;
    }

    @Specialization(replaces={"doBoolean", "doInteger", "doDouble", "doSafeInteger", "doLong"})
    protected final Object doObject(VirtualFrame frame, Object value2) {
        this.ensureObjectKind(frame);
        frame.setObject(this.slot, value2);
        return value2;
    }

    @Override
    public final void executeWithFrame(Frame frame, Object value2) {
        this.executeEvaluated((VirtualFrame)frame, value2);
    }

    abstract void executeEvaluated(VirtualFrame var1, Object var2);

    @Override
    public final void executeWrite(VirtualFrame frame, Object value2) {
        this.executeEvaluated(frame, value2);
    }

    @Override
    public JavaScriptNode getRhs() {
        return this.rhsNode;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return JSWriteCurrentFrameSlotNodeGen.create(this.getSlotIndex(), this.getIdentifier(), JSWriteCurrentFrameSlotNode.cloneUninitialized(this.getRhs(), materializedTags));
    }

    @Override
    public ScopeFrameNode getLevelFrameNode() {
        return ScopeFrameNode.createCurrent();
    }
}

