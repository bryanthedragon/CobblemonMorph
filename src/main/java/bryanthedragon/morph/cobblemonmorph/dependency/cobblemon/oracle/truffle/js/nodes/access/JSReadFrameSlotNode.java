
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSFrameSlot;
import com.oracle.truffle.js.nodes.ReadNode;
import com.oracle.truffle.js.nodes.RepeatableNode;
import com.oracle.truffle.js.nodes.access.FrameSlotNode;
import com.oracle.truffle.js.nodes.access.JSReadCurrentFrameSlotNodeGen;
import com.oracle.truffle.js.nodes.access.JSReadScopeFrameSlotNodeGen;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.instrumentation.NodeObjectDescriptor;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.Strings;

@ImportStatic(value={FrameSlotKind.class})
public abstract class JSReadFrameSlotNode
extends FrameSlotNode
implements RepeatableNode,
ReadNode {
    protected final boolean hasTemporalDeadZone;

    protected JSReadFrameSlotNode(int slot, Object identifier, boolean hasTemporalDeadZone) {
        super(slot, identifier);
        this.hasTemporalDeadZone = hasTemporalDeadZone;
    }

    public static JSReadFrameSlotNode create(JSFrameSlot slot, boolean hasTemporalDeadZone) {
        return JSReadFrameSlotNode.create(slot, ScopeFrameNode.createCurrent(), hasTemporalDeadZone);
    }

    public static JSReadFrameSlotNode create(JSFrameSlot slot, ScopeFrameNode levelFrameNode, boolean hasTemporalDeadZone) {
        assert (!hasTemporalDeadZone || JSFrameUtil.hasTemporalDeadZone(slot));
        return JSReadFrameSlotNode.create(slot.getIndex(), slot.getIdentifier(), levelFrameNode, hasTemporalDeadZone);
    }

    static JSReadFrameSlotNode create(int slotIndex, Object identifier, ScopeFrameNode levelFrameNode, boolean hasTemporalDeadZone) {
        if (levelFrameNode == ScopeFrameNode.createCurrent()) {
            return JSReadCurrentFrameSlotNodeGen.create(slotIndex, identifier, hasTemporalDeadZone);
        }
        return JSReadScopeFrameSlotNodeGen.create(slotIndex, identifier, levelFrameNode, hasTemporalDeadZone);
    }

    public static JSReadFrameSlotNode create(JSFrameSlot slot) {
        return JSReadCurrentFrameSlotNodeGen.create(slot.getIndex(), slot.getIdentifier(), JSFrameUtil.hasTemporalDeadZone(slot));
    }

    public static JSReadFrameSlotNode create(FrameDescriptor desc, int slotIndex) {
        return JSReadFrameSlotNode.create(JSFrameSlot.fromIndexedFrameSlot(desc, slotIndex));
    }

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        if (tag == JSTags.ReadVariableTag.class || tag == StandardTags.ReadVariableTag.class) {
            if (JSFrameUtil.isInternalIdentifier(this.getIdentifier())) {
                return JSFrameUtil.isThisSlotIdentifier(this.getIdentifier());
            }
            return true;
        }
        return super.hasTag(tag);
    }

    @Override
    public Object getNodeObject() {
        TruffleString name = JSFrameUtil.getPublicName(this.getIdentifier());
        NodeObjectDescriptor descriptor = JSTags.createNodeObjectDescriptor("name", name);
        descriptor.addProperty("readVariableName", (Object)name);
        return descriptor;
    }

    @Override
    public String expressionToString() {
        Object ident = this.getIdentifier();
        if (ident instanceof TruffleString) {
            return Strings.toJavaString((TruffleString)ident);
        }
        return null;
    }

    @Override
    public final boolean hasTemporalDeadZone() {
        return this.hasTemporalDeadZone;
    }
}

