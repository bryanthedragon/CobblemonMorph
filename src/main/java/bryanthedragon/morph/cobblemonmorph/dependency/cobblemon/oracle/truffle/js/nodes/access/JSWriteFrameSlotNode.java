
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSFrameSlot;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.FrameSlotNode;
import com.oracle.truffle.js.nodes.access.JSWriteCurrentFrameSlotNodeGen;
import com.oracle.truffle.js.nodes.access.JSWriteScopeFrameSlotNodeGen;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;
import com.oracle.truffle.js.nodes.access.TemporalDeadZoneCheckNode;
import com.oracle.truffle.js.nodes.access.WriteNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.instrumentation.NodeObjectDescriptor;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import java.util.Set;

public abstract class JSWriteFrameSlotNode
extends FrameSlotNode.WithDescriptor
implements WriteNode {
    protected JSWriteFrameSlotNode(int slot, Object identifier) {
        super(slot, identifier);
    }

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        if (tag == JSTags.WriteVariableTag.class || tag == StandardTags.WriteVariableTag.class || tag == JSTags.InputNodeTag.class) {
            return !JSFrameUtil.isInternalIdentifier(this.getIdentifier());
        }
        return super.hasTag(tag);
    }

    @Override
    public Object getNodeObject() {
        TruffleString name = JSFrameUtil.getPublicName(this.getIdentifier());
        NodeObjectDescriptor descriptor = JSTags.createNodeObjectDescriptor("name", name);
        descriptor.addProperty("writeVariableName", (Object)name);
        return descriptor;
    }

    @Override
    public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
        if ((materializedTags.contains(JSTags.WriteVariableTag.class) || materializedTags.contains(StandardTags.WriteVariableTag.class)) && this.getRhs() != null && !this.getRhs().hasSourceSection() && this.hasSourceSection()) {
            JSWriteFrameSlotNode.transferSourceSectionAddExpressionTag(this, this.getRhs());
        }
        return this;
    }

    @Override
    public abstract JavaScriptNode getRhs();

    public abstract void executeWithFrame(Frame var1, Object var2);

    public static JSWriteFrameSlotNode create(JSFrameSlot frameSlot, JavaScriptNode rhs, boolean hasTemporalDeadZone) {
        if (!hasTemporalDeadZone) {
            return JSWriteCurrentFrameSlotNodeGen.create(frameSlot.getIndex(), frameSlot.getIdentifier(), rhs);
        }
        return JSWriteFrameSlotNode.create(frameSlot, ScopeFrameNode.createCurrent(), rhs, hasTemporalDeadZone);
    }

    public static JSWriteFrameSlotNode create(JSFrameSlot frameSlot, ScopeFrameNode scopeFrameNode, JavaScriptNode rhs, boolean hasTemporalDeadZone) {
        assert (!hasTemporalDeadZone || JSFrameUtil.hasTemporalDeadZone(frameSlot));
        return JSWriteFrameSlotNode.create(frameSlot.getIndex(), frameSlot.getIdentifier(), scopeFrameNode, rhs, hasTemporalDeadZone);
    }

    public static JSWriteFrameSlotNode create(int slotIndex, Object identifier, ScopeFrameNode scopeFrameNode, JavaScriptNode rhs, boolean hasTemporalDeadZone) {
        if (!hasTemporalDeadZone && scopeFrameNode == ScopeFrameNode.createCurrent()) {
            return JSWriteCurrentFrameSlotNodeGen.create(slotIndex, identifier, rhs);
        }
        return JSWriteScopeFrameSlotNodeGen.create(slotIndex, identifier, scopeFrameNode, hasTemporalDeadZone ? TemporalDeadZoneCheckNode.create(slotIndex, identifier, scopeFrameNode, rhs) : rhs);
    }

    @Override
    public final boolean isResultAlwaysOfType(Class<?> clazz) {
        return this.getRhs().isResultAlwaysOfType(clazz);
    }
}

