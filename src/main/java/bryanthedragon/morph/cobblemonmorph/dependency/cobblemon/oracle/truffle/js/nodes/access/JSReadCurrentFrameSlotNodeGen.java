
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.access.JSReadCurrentFrameSlotNode;

@GeneratedBy(value=JSReadCurrentFrameSlotNode.class)
final class JSReadCurrentFrameSlotNodeGen
extends JSReadCurrentFrameSlotNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private int state_0_;

    private JSReadCurrentFrameSlotNodeGen(int slot, Object identifier, boolean hasTemporalDeadZone) {
        super(slot, identifier, hasTemporalDeadZone);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && frameValue.isBoolean(this.slot)) {
            return this.doBoolean(frameValue);
        }
        if ((state_0 & 2) != 0 && frameValue.isInt(this.slot)) {
            return this.doInt(frameValue);
        }
        if ((state_0 & 4) != 0 && (frameValue.isDouble(this.slot) || frameValue.isInt(this.slot))) {
            return this.doDouble(frameValue);
        }
        if ((state_0 & 8) != 0 && frameValue.isObject(this.slot)) {
            return this.doObject(frameValue);
        }
        if ((state_0 & 0x10) != 0 && frameValue.isLong(this.slot)) {
            return this.doSafeInteger(frameValue);
        }
        if ((state_0 & 0x20) != 0 && this.isIllegal(frameValue)) {
            return this.doDead(frameValue);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(frameValue);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
        int state_0 = this.state_0_;
        if ((state_0 & 0x28) != 0) {
            return JSTypesGen.expectBoolean(this.execute(frameValue));
        }
        if ((state_0 & 1) != 0 && frameValue.isBoolean(this.slot)) {
            return this.doBoolean(frameValue);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectBoolean(this.executeAndSpecialize(frameValue));
    }

    @Override
    public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
        int state_0 = this.state_0_;
        if ((state_0 & 0x28) != 0) {
            return JSTypesGen.expectDouble(this.execute(frameValue));
        }
        if ((state_0 & 4) != 0 && (frameValue.isDouble(this.slot) || frameValue.isInt(this.slot))) {
            return this.doDouble(frameValue);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectDouble(this.executeAndSpecialize(frameValue));
    }

    @Override
    public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
        int state_0 = this.state_0_;
        if ((state_0 & 0x28) != 0) {
            return JSTypesGen.expectInteger(this.execute(frameValue));
        }
        if ((state_0 & 2) != 0 && frameValue.isInt(this.slot)) {
            return this.doInt(frameValue);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectInteger(this.executeAndSpecialize(frameValue));
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

    private Object executeAndSpecialize(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if (frameValue.isBoolean(this.slot)) {
            this.state_0_ = state_0 |= 1;
            return this.doBoolean(frameValue);
        }
        if (frameValue.isInt(this.slot)) {
            this.state_0_ = state_0 |= 2;
            return this.doInt(frameValue);
        }
        if (frameValue.isDouble(this.slot) || frameValue.isInt(this.slot)) {
            this.state_0_ = state_0 |= 4;
            return this.doDouble(frameValue);
        }
        if (frameValue.isObject(this.slot)) {
            this.state_0_ = state_0 |= 8;
            return this.doObject(frameValue);
        }
        if (frameValue.isLong(this.slot)) {
            this.state_0_ = state_0 |= 0x10;
            return this.doSafeInteger(frameValue);
        }
        if (this.isIllegal(frameValue)) {
            this.state_0_ = state_0 |= 0x20;
            return this.doDead(frameValue);
        }
        throw new UnsupportedSpecializationException(this, new Node[0], new Object[0]);
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

    public static JSReadCurrentFrameSlotNode create(int slot, Object identifier, boolean hasTemporalDeadZone) {
        return new JSReadCurrentFrameSlotNodeGen(slot, identifier, hasTemporalDeadZone);
    }
}

