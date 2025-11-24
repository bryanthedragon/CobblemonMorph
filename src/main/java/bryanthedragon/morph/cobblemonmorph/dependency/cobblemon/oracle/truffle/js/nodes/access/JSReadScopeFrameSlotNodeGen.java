
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.access.JSReadScopeFrameSlotNode;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;

@GeneratedBy(value=JSReadScopeFrameSlotNode.class)
final class JSReadScopeFrameSlotNodeGen
extends JSReadScopeFrameSlotNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private int state_0_;

    private JSReadScopeFrameSlotNodeGen(int slot, Object identifier, ScopeFrameNode scopeFrameNode, boolean hasTemporalDeadZone) {
        super(slot, identifier, scopeFrameNode, hasTemporalDeadZone);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Frame scopeFrameNodeValue_ = this.scopeFrameNode.executeFrame(frameValue);
        if (state_0 != 0) {
            if ((state_0 & 1) != 0 && scopeFrameNodeValue_.isBoolean(this.slot)) {
                return this.doBoolean(scopeFrameNodeValue_);
            }
            if ((state_0 & 2) != 0 && scopeFrameNodeValue_.isInt(this.slot)) {
                return this.doInt(scopeFrameNodeValue_);
            }
            if ((state_0 & 4) != 0 && (scopeFrameNodeValue_.isDouble(this.slot) || scopeFrameNodeValue_.isInt(this.slot))) {
                return this.doDouble(scopeFrameNodeValue_);
            }
            if ((state_0 & 8) != 0 && scopeFrameNodeValue_.isObject(this.slot)) {
                return this.doObject(scopeFrameNodeValue_);
            }
            if ((state_0 & 0x10) != 0 && scopeFrameNodeValue_.isLong(this.slot)) {
                return this.doSafeInteger(scopeFrameNodeValue_);
            }
            if ((state_0 & 0x20) != 0 && this.isIllegal(scopeFrameNodeValue_)) {
                return this.doDead(scopeFrameNodeValue_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(scopeFrameNodeValue_);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
        int state_0 = this.state_0_;
        if ((state_0 & 0x28) != 0) {
            return JSTypesGen.expectBoolean(this.execute(frameValue));
        }
        Frame scopeFrameNodeValue_ = this.scopeFrameNode.executeFrame(frameValue);
        if ((state_0 & 1) != 0 && scopeFrameNodeValue_.isBoolean(this.slot)) {
            return this.doBoolean(scopeFrameNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectBoolean(this.executeAndSpecialize(scopeFrameNodeValue_));
    }

    @Override
    public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
        int state_0 = this.state_0_;
        if ((state_0 & 0x28) != 0) {
            return JSTypesGen.expectDouble(this.execute(frameValue));
        }
        Frame scopeFrameNodeValue_ = this.scopeFrameNode.executeFrame(frameValue);
        if ((state_0 & 4) != 0 && (scopeFrameNodeValue_.isDouble(this.slot) || scopeFrameNodeValue_.isInt(this.slot))) {
            return this.doDouble(scopeFrameNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectDouble(this.executeAndSpecialize(scopeFrameNodeValue_));
    }

    @Override
    public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
        int state_0 = this.state_0_;
        if ((state_0 & 0x28) != 0) {
            return JSTypesGen.expectInteger(this.execute(frameValue));
        }
        Frame scopeFrameNodeValue_ = this.scopeFrameNode.executeFrame(frameValue);
        if ((state_0 & 2) != 0 && scopeFrameNodeValue_.isInt(this.slot)) {
            return this.doInt(scopeFrameNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectInteger(this.executeAndSpecialize(scopeFrameNodeValue_));
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        try {
            if ((state_0 & 0x3D) == 0 && state_0 != 0) {
                this.executeInt(frameValue);
                return;
            }
            if ((state_0 & 0x3B) == 0 && state_0 != 0) {
                this.executeDouble(frameValue);
                return;
            }
            if ((state_0 & 0x3E) == 0 && state_0 != 0) {
                this.executeBoolean(frameValue);
                return;
            }
            this.execute(frameValue);
            return;
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return;
        }
    }

    private Object executeAndSpecialize(Frame scopeFrameNodeValue) {
        int state_0 = this.state_0_;
        if (scopeFrameNodeValue.isBoolean(this.slot)) {
            this.state_0_ = state_0 |= 1;
            return this.doBoolean(scopeFrameNodeValue);
        }
        if (scopeFrameNodeValue.isInt(this.slot)) {
            this.state_0_ = state_0 |= 2;
            return this.doInt(scopeFrameNodeValue);
        }
        if (scopeFrameNodeValue.isDouble(this.slot) || scopeFrameNodeValue.isInt(this.slot)) {
            this.state_0_ = state_0 |= 4;
            return this.doDouble(scopeFrameNodeValue);
        }
        if (scopeFrameNodeValue.isObject(this.slot)) {
            this.state_0_ = state_0 |= 8;
            return this.doObject(scopeFrameNodeValue);
        }
        if (scopeFrameNodeValue.isLong(this.slot)) {
            this.state_0_ = state_0 |= 0x10;
            return this.doSafeInteger(scopeFrameNodeValue);
        }
        if (this.isIllegal(scopeFrameNodeValue)) {
            this.state_0_ = state_0 |= 0x20;
            return this.doDead(scopeFrameNodeValue);
        }
        throw new UnsupportedSpecializationException(this, new Node[]{this.scopeFrameNode}, scopeFrameNodeValue);
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[7];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "doBoolean";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doInt";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doDouble";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doObject";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doSafeInteger";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doDead";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        return Introspection.Provider.create(data);
    }

    public static JSReadScopeFrameSlotNode create(int slot, Object identifier, ScopeFrameNode scopeFrameNode, boolean hasTemporalDeadZone) {
        return new JSReadScopeFrameSlotNodeGen(slot, identifier, scopeFrameNode, hasTemporalDeadZone);
    }
}

