
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
import com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode;
import com.oracle.truffle.js.nodes.access.JSWriteScopeFrameSlotNodeGen;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;
import com.oracle.truffle.js.runtime.SafeInteger;
import java.util.Set;

abstract class JSWriteScopeFrameSlotNode
extends JSWriteFrameSlotNode {
    @Node.Child
    @Executed
    ScopeFrameNode scopeFrameNode;
    @Node.Child
    @Executed
    JavaScriptNode rhsNode;

    protected JSWriteScopeFrameSlotNode(int slot, Object identifier, ScopeFrameNode scopeFrameNode, JavaScriptNode rhsNode) {
        super(slot, identifier);
        this.scopeFrameNode = scopeFrameNode;
        this.rhsNode = rhsNode;
    }

    @Specialization(guards={"isBooleanKind(levelFrame)"})
    protected final boolean doBoolean(Frame levelFrame, boolean value2) {
        levelFrame.setBoolean(this.slot, value2);
        return value2;
    }

    @Specialization(guards={"(isIntegerKind(frame, kind) || isLongKind(frame, kind)) || isDoubleKind(frame, kind)"})
    protected final int doInteger(Frame frame, int value2, @Bind(value="getFrameDescriptor(frame).getSlotKind(slot)") FrameSlotKind kind) {
        if (this.isIntegerKind(frame, kind)) {
            frame.setInt(this.slot, value2);
        } else if (this.isLongKind(frame, kind)) {
            frame.setLong(this.slot, value2);
        } else if (this.isDoubleKind(frame, kind)) {
            frame.setDouble(this.slot, value2);
        }
        return value2;
    }

    @Specialization(guards={"isLongKind(levelFrame)"})
    protected final SafeInteger doSafeInteger(Frame levelFrame, SafeInteger value2) {
        levelFrame.setLong(this.slot, value2.longValue());
        return value2;
    }

    @Specialization
    protected final long doLong(Frame levelFrame, long value2) {
        this.ensureObjectKind(levelFrame);
        levelFrame.setObject(this.slot, value2);
        return value2;
    }

    @Specialization(guards={"isDoubleKind(levelFrame)"}, replaces={"doInteger", "doSafeInteger"})
    protected final double doDouble(Frame levelFrame, double value2) {
        levelFrame.setDouble(this.slot, value2);
        return value2;
    }

    @Specialization(replaces={"doBoolean", "doInteger", "doDouble", "doSafeInteger", "doLong"})
    protected final Object doObject(Frame levelFrame, Object value2) {
        this.ensureObjectKind(levelFrame);
        levelFrame.setObject(this.slot, value2);
        return value2;
    }

    @Override
    public final void executeWithFrame(Frame frame, Object value2) {
        this.executeEvaluated(null, frame, value2);
    }

    abstract void executeEvaluated(VirtualFrame var1, Frame var2, Object var3);

    @Override
    public final void executeWrite(VirtualFrame frame, Object value2) {
        this.executeEvaluated(frame, this.getLevelFrameNode().executeFrame(frame), value2);
    }

    @Override
    public ScopeFrameNode getLevelFrameNode() {
        return this.scopeFrameNode;
    }

    @Override
    public JavaScriptNode getRhs() {
        return this.rhsNode;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return JSWriteScopeFrameSlotNodeGen.create(this.getSlotIndex(), this.getIdentifier(), this.getLevelFrameNode(), JSWriteScopeFrameSlotNode.cloneUninitialized(this.getRhs(), materializedTags));
    }
}

