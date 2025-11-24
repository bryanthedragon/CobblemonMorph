
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode;
import com.oracle.truffle.js.nodes.access.JSReadScopeFrameSlotNodeGen;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.SafeInteger;
import java.util.Set;

abstract class JSReadScopeFrameSlotNode
extends JSReadFrameSlotNode {
    @Node.Child
    @Executed
    ScopeFrameNode scopeFrameNode;

    JSReadScopeFrameSlotNode(int slot, Object identifier, ScopeFrameNode scopeFrameNode, boolean hasTemporalDeadZone) {
        super(slot, identifier, hasTemporalDeadZone);
        this.scopeFrameNode = scopeFrameNode;
    }

    @Specialization(guards={"levelFrame.isBoolean(slot)"})
    protected final boolean doBoolean(Frame levelFrame) {
        return levelFrame.getBoolean(this.slot);
    }

    @Specialization(guards={"levelFrame.isInt(slot)"})
    protected final int doInt(Frame levelFrame) {
        return levelFrame.getInt(this.slot);
    }

    @Specialization(guards={"levelFrame.isDouble(slot) || levelFrame.isInt(slot)"})
    protected final double doDouble(Frame levelFrame) {
        if (levelFrame.isInt(this.slot)) {
            return levelFrame.getInt(this.slot);
        }
        return levelFrame.getDouble(this.slot);
    }

    @Specialization(guards={"levelFrame.isObject(slot)"})
    protected final Object doObject(Frame levelFrame) {
        return levelFrame.getObject(this.slot);
    }

    @Specialization(guards={"levelFrame.isLong(slot)"})
    protected final SafeInteger doSafeInteger(Frame levelFrame) {
        return SafeInteger.valueOf(levelFrame.getLong(this.slot));
    }

    @Specialization(guards={"isIllegal(levelFrame)"})
    protected final Object doDead(Frame levelFrame) {
        assert (this.hasTemporalDeadZone());
        throw Errors.createReferenceErrorNotDefined(this.getIdentifier(), this);
    }

    @Override
    public ScopeFrameNode getLevelFrameNode() {
        return this.scopeFrameNode;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return JSReadScopeFrameSlotNodeGen.create(this.getSlotIndex(), this.getIdentifier(), this.getLevelFrameNode(), this.hasTemporalDeadZone());
    }
}

