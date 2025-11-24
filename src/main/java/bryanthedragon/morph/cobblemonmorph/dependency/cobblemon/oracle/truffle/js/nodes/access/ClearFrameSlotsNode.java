
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.ClearFrameSlotNode;
import com.oracle.truffle.js.nodes.access.ClearFrameSlotRangeNode;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Objects;
import java.util.Set;

public class ClearFrameSlotsNode
extends JavaScriptNode {
    @Node.Child
    private ScopeFrameNode scopeFrameNode;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final int[] slots;

    protected ClearFrameSlotsNode(ScopeFrameNode scopeFrameNode, int[] slots) {
        this.scopeFrameNode = Objects.requireNonNull(scopeFrameNode);
        this.slots = Objects.requireNonNull(slots);
    }

    @Override
    @ExplodeLoop
    public Object execute(VirtualFrame frame) {
        Frame scopeFrame = this.scopeFrameNode.executeFrame(frame);
        for (int slot : this.slots) {
            ClearFrameSlotsNode.clearSlot(scopeFrame, slot);
        }
        return Undefined.instance;
    }

    static void clearSlot(Frame scopeFrame, int slot) {
        assert (JSFrameUtil.hasTemporalDeadZone(scopeFrame.getFrameDescriptor(), slot)) : slot;
        scopeFrame.clear(slot);
    }

    public static JavaScriptNode create(ScopeFrameNode scopeFrameNode, int[] slots) {
        if (slots.length == 1) {
            return new ClearFrameSlotNode(scopeFrameNode, slots[0]);
        }
        return new ClearFrameSlotsNode(scopeFrameNode, slots);
    }

    public static JavaScriptNode createRange(ScopeFrameNode scopeFrameNode, int start2, int end2) {
        return new ClearFrameSlotRangeNode(scopeFrameNode, start2, end2);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return ClearFrameSlotsNode.create(this.scopeFrameNode, this.slots);
    }

    @Override
    public boolean isResultAlwaysOfType(Class<?> clazz) {
        return clazz == Undefined.class;
    }
}

