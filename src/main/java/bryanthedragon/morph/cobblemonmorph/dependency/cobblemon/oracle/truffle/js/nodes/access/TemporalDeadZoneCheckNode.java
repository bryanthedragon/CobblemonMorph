
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeUtil;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.FrameSlotNode;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;
import com.oracle.truffle.js.runtime.Errors;
import java.util.Set;

public final class TemporalDeadZoneCheckNode
extends FrameSlotNode {
    @Node.Child
    private JavaScriptNode child;
    @Node.Child
    private ScopeFrameNode levelFrameNode;
    private final BranchProfile deadBranch = BranchProfile.create();

    private TemporalDeadZoneCheckNode(int slot, Object identifier, ScopeFrameNode levelFrameNode, JavaScriptNode child) {
        super(slot, identifier);
        this.levelFrameNode = levelFrameNode;
        this.child = child;
    }

    private void checkNotDead(VirtualFrame frame) {
        Frame levelFrame = this.levelFrameNode.executeFrame(frame);
        if (CompilerDirectives.injectBranchProbability(1.0E-4, levelFrame.getTag(this.slot) == FrameSlotKind.Illegal.tag)) {
            this.deadBranch.enter();
            throw Errors.createReferenceErrorNotDefined(this.getIdentifier(), this);
        }
    }

    @Override
    public Object execute(VirtualFrame frame) {
        this.checkNotDead(frame);
        return this.child.execute(frame);
    }

    @Override
    public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
        this.checkNotDead(frame);
        return this.child.executeInt(frame);
    }

    @Override
    public double executeDouble(VirtualFrame frame) throws UnexpectedResultException {
        this.checkNotDead(frame);
        return this.child.executeDouble(frame);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frame) throws UnexpectedResultException {
        this.checkNotDead(frame);
        return this.child.executeBoolean(frame);
    }

    @Override
    public ScopeFrameNode getLevelFrameNode() {
        return this.levelFrameNode;
    }

    @Override
    public boolean hasTemporalDeadZone() {
        return true;
    }

    public static TemporalDeadZoneCheckNode create(int slotIndex, Object identifier, ScopeFrameNode levelFrameNode, JavaScriptNode rhs) {
        return new TemporalDeadZoneCheckNode(slotIndex, identifier, levelFrameNode, rhs);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return new TemporalDeadZoneCheckNode(this.getSlotIndex(), this.getIdentifier(), NodeUtil.cloneNode(this.levelFrameNode), TemporalDeadZoneCheckNode.cloneUninitialized(this.child, materializedTags));
    }
}

