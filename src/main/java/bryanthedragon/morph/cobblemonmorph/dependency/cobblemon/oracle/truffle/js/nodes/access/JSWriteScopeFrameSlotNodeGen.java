
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSWriteScopeFrameSlotNode;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;
import com.oracle.truffle.js.runtime.SafeInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSWriteScopeFrameSlotNode.class)
final class JSWriteScopeFrameSlotNodeGen
extends JSWriteScopeFrameSlotNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;

    private JSWriteScopeFrameSlotNodeGen(int slot, Object identifier, ScopeFrameNode scopeFrameNode, JavaScriptNode rhsNode) {
        super(slot, identifier, scopeFrameNode, rhsNode);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Frame scopeFrameNodeValue_ = this.scopeFrameNode.executeFrame(frameValue);
        if ((state_0 & 0x3E) == 0 && (state_0 & 0x3F) != 0) {
            return this.execute_boolean0(state_0, frameValue, scopeFrameNodeValue_);
        }
        if ((state_0 & 0x3D) == 0 && (state_0 & 0x3F) != 0) {
            return this.execute_int1(state_0, frameValue, scopeFrameNodeValue_);
        }
        if ((state_0 & 0x37) == 0 && (state_0 & 0x3F) != 0) {
            return this.execute_long2(state_0, frameValue, scopeFrameNodeValue_);
        }
        if ((state_0 & 0x2F) == 0 && (state_0 & 0x3F) != 0) {
            return this.execute_double3(state_0, frameValue, scopeFrameNodeValue_);
        }
        return this.execute_generic4(state_0, frameValue, scopeFrameNodeValue_);
    }

    private Object execute_boolean0(int state_0, VirtualFrame frameValue, Frame scopeFrameNodeValue_) {
        boolean rhsNodeValue_;
        try {
            rhsNodeValue_ = this.rhsNode.executeBoolean(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(scopeFrameNodeValue_, ex.getResult());
        }
        assert ((state_0 & 1) != 0);
        if (this.isBooleanKind(scopeFrameNodeValue_)) {
            return this.doBoolean(scopeFrameNodeValue_, rhsNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(scopeFrameNodeValue_, rhsNodeValue_);
    }

    private Object execute_int1(int state_0, VirtualFrame frameValue, Frame scopeFrameNodeValue_) {
        int rhsNodeValue_;
        try {
            rhsNodeValue_ = this.rhsNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(scopeFrameNodeValue_, ex.getResult());
        }
        assert ((state_0 & 2) != 0);
        FrameSlotKind integer_kind__ = this.getFrameDescriptor(scopeFrameNodeValue_).getSlotKind(this.slot);
        if (this.isIntegerKind(scopeFrameNodeValue_, integer_kind__) || this.isLongKind(scopeFrameNodeValue_, integer_kind__) || this.isDoubleKind(scopeFrameNodeValue_, integer_kind__)) {
            return this.doInteger(scopeFrameNodeValue_, rhsNodeValue_, integer_kind__);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(scopeFrameNodeValue_, rhsNodeValue_);
    }

    private Object execute_long2(int state_0, VirtualFrame frameValue, Frame scopeFrameNodeValue_) {
        long rhsNodeValue_;
        try {
            rhsNodeValue_ = this.rhsNode.executeLong(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(scopeFrameNodeValue_, ex.getResult());
        }
        assert ((state_0 & 8) != 0);
        return this.doLong(scopeFrameNodeValue_, rhsNodeValue_);
    }

    private Object execute_double3(int state_0, VirtualFrame frameValue, Frame scopeFrameNodeValue_) {
        double rhsNodeValue_;
        long rhsNodeValue_long = 0L;
        int rhsNodeValue_int = 0;
        try {
            if ((state_0 & 0x380) == 0 && (state_0 & 0x3F) != 0) {
                rhsNodeValue_ = this.rhsNode.executeDouble(frameValue);
            } else if ((state_0 & 0x340) == 0 && (state_0 & 0x3F) != 0) {
                rhsNodeValue_int = this.rhsNode.executeInt(frameValue);
                rhsNodeValue_ = JSTypes.intToDouble(rhsNodeValue_int);
            } else if ((state_0 & 0x1C0) == 0 && (state_0 & 0x3F) != 0) {
                rhsNodeValue_long = this.rhsNode.executeLong(frameValue);
                rhsNodeValue_ = JSTypes.longToDouble(rhsNodeValue_long);
            } else {
                Object rhsNodeValue__ = this.rhsNode.execute(frameValue);
                rhsNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C0) >>> 6, rhsNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(scopeFrameNodeValue_, ex.getResult());
        }
        assert ((state_0 & 0x10) != 0);
        if (this.isDoubleKind(scopeFrameNodeValue_)) {
            return this.doDouble(scopeFrameNodeValue_, rhsNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(scopeFrameNodeValue_, (state_0 & 0x340) == 0 && (state_0 & 0x3F) != 0 ? (Number)rhsNodeValue_int : (Number)((state_0 & 0x1C0) == 0 && (state_0 & 0x3F) != 0 ? (Number)rhsNodeValue_long : (Number)rhsNodeValue_));
    }

    private Object execute_generic4(int state_0, VirtualFrame frameValue, Frame scopeFrameNodeValue_) {
        Object rhsNodeValue_ = this.rhsNode.execute(frameValue);
        if ((state_0 & 0x3F) != 0) {
            int rhsNodeValue__;
            if ((state_0 & 1) != 0 && rhsNodeValue_ instanceof Boolean) {
                rhsNodeValue__ = ((Boolean)rhsNodeValue_).booleanValue();
                if (this.isBooleanKind(scopeFrameNodeValue_)) {
                    return this.doBoolean(scopeFrameNodeValue_, rhsNodeValue__ != 0);
                }
            }
            if ((state_0 & 2) != 0 && rhsNodeValue_ instanceof Integer) {
                rhsNodeValue__ = (Integer)rhsNodeValue_;
                FrameSlotKind integer_kind__ = this.getFrameDescriptor(scopeFrameNodeValue_).getSlotKind(this.slot);
                if (this.isIntegerKind(scopeFrameNodeValue_, integer_kind__) || this.isLongKind(scopeFrameNodeValue_, integer_kind__) || this.isDoubleKind(scopeFrameNodeValue_, integer_kind__)) {
                    return this.doInteger(scopeFrameNodeValue_, rhsNodeValue__, integer_kind__);
                }
            }
            if ((state_0 & 4) != 0 && rhsNodeValue_ instanceof SafeInteger) {
                SafeInteger rhsNodeValue__2 = (SafeInteger)rhsNodeValue_;
                if (this.isLongKind(scopeFrameNodeValue_)) {
                    return this.doSafeInteger(scopeFrameNodeValue_, rhsNodeValue__2);
                }
            }
            if ((state_0 & 8) != 0 && rhsNodeValue_ instanceof Long) {
                long rhsNodeValue__3 = (Long)rhsNodeValue_;
                return this.doLong(scopeFrameNodeValue_, rhsNodeValue__3);
            }
            if ((state_0 & 0x10) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C0) >>> 6, rhsNodeValue_)) {
                double rhsNodeValue__4 = JSTypesGen.asImplicitDouble((state_0 & 0x3C0) >>> 6, rhsNodeValue_);
                if (this.isDoubleKind(scopeFrameNodeValue_)) {
                    return this.doDouble(scopeFrameNodeValue_, rhsNodeValue__4);
                }
            }
            if ((state_0 & 0x20) != 0) {
                return this.doObject(scopeFrameNodeValue_, rhsNodeValue_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(scopeFrameNodeValue_, rhsNodeValue_);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
        boolean rhsNodeValue_;
        int state_0 = this.state_0_;
        if ((state_0 & 0x20) != 0) {
            return JSTypesGen.expectBoolean(this.execute(frameValue));
        }
        Frame scopeFrameNodeValue_ = this.scopeFrameNode.executeFrame(frameValue);
        try {
            rhsNodeValue_ = this.rhsNode.executeBoolean(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectBoolean(this.executeAndSpecialize(scopeFrameNodeValue_, ex.getResult()));
        }
        if ((state_0 & 1) != 0 && this.isBooleanKind(scopeFrameNodeValue_)) {
            return this.doBoolean(scopeFrameNodeValue_, rhsNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectBoolean(this.executeAndSpecialize(scopeFrameNodeValue_, rhsNodeValue_));
    }

    @Override
    public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
        double rhsNodeValue_;
        int state_0 = this.state_0_;
        if ((state_0 & 0x20) != 0) {
            return JSTypesGen.expectDouble(this.execute(frameValue));
        }
        Frame scopeFrameNodeValue_ = this.scopeFrameNode.executeFrame(frameValue);
        long rhsNodeValue_long = 0L;
        int rhsNodeValue_int = 0;
        try {
            if ((state_0 & 0x380) == 0 && (state_0 & 0x3F) != 0) {
                rhsNodeValue_ = this.rhsNode.executeDouble(frameValue);
            } else if ((state_0 & 0x340) == 0 && (state_0 & 0x3F) != 0) {
                rhsNodeValue_int = this.rhsNode.executeInt(frameValue);
                rhsNodeValue_ = JSTypes.intToDouble(rhsNodeValue_int);
            } else if ((state_0 & 0x1C0) == 0 && (state_0 & 0x3F) != 0) {
                rhsNodeValue_long = this.rhsNode.executeLong(frameValue);
                rhsNodeValue_ = JSTypes.longToDouble(rhsNodeValue_long);
            } else {
                Object rhsNodeValue__ = this.rhsNode.execute(frameValue);
                rhsNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C0) >>> 6, rhsNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(this.executeAndSpecialize(scopeFrameNodeValue_, ex.getResult()));
        }
        if ((state_0 & 0x10) != 0 && this.isDoubleKind(scopeFrameNodeValue_)) {
            return this.doDouble(scopeFrameNodeValue_, rhsNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectDouble(this.executeAndSpecialize(scopeFrameNodeValue_, (state_0 & 0x340) == 0 && (state_0 & 0x3F) != 0 ? (Number)rhsNodeValue_int : (Number)((state_0 & 0x1C0) == 0 && (state_0 & 0x3F) != 0 ? (Number)rhsNodeValue_long : (Number)rhsNodeValue_)));
    }

    @Override
    public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
        FrameSlotKind integer_kind__;
        int rhsNodeValue_;
        int state_0 = this.state_0_;
        if ((state_0 & 0x20) != 0) {
            return JSTypesGen.expectInteger(this.execute(frameValue));
        }
        Frame scopeFrameNodeValue_ = this.scopeFrameNode.executeFrame(frameValue);
        try {
            rhsNodeValue_ = this.rhsNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(scopeFrameNodeValue_, ex.getResult()));
        }
        if ((state_0 & 2) != 0 && (this.isIntegerKind(scopeFrameNodeValue_, integer_kind__ = this.getFrameDescriptor(scopeFrameNodeValue_).getSlotKind(this.slot)) || this.isLongKind(scopeFrameNodeValue_, integer_kind__) || this.isDoubleKind(scopeFrameNodeValue_, integer_kind__))) {
            return this.doInteger(scopeFrameNodeValue_, rhsNodeValue_, integer_kind__);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectInteger(this.executeAndSpecialize(scopeFrameNodeValue_, rhsNodeValue_));
    }

    @Override
    public long executeLong(VirtualFrame frameValue) throws UnexpectedResultException {
        long rhsNodeValue_;
        int state_0 = this.state_0_;
        if ((state_0 & 0x20) != 0) {
            return JSTypesGen.expectLong(this.execute(frameValue));
        }
        Frame scopeFrameNodeValue_ = this.scopeFrameNode.executeFrame(frameValue);
        try {
            rhsNodeValue_ = this.rhsNode.executeLong(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectLong(this.executeAndSpecialize(scopeFrameNodeValue_, ex.getResult()));
        }
        if ((state_0 & 8) != 0) {
            return this.doLong(scopeFrameNodeValue_, rhsNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectLong(this.executeAndSpecialize(scopeFrameNodeValue_, rhsNodeValue_));
    }

    @Override
    void executeEvaluated(VirtualFrame frameValue, Frame scopeFrameNodeValue, Object rhsNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0x3F) != 0) {
            int rhsNodeValue_;
            if ((state_0 & 1) != 0 && rhsNodeValue instanceof Boolean) {
                rhsNodeValue_ = ((Boolean)rhsNodeValue).booleanValue();
                if (this.isBooleanKind(scopeFrameNodeValue)) {
                    this.doBoolean(scopeFrameNodeValue, rhsNodeValue_ != 0);
                    return;
                }
            }
            if ((state_0 & 2) != 0 && rhsNodeValue instanceof Integer) {
                rhsNodeValue_ = (Integer)rhsNodeValue;
                FrameSlotKind integer_kind__ = this.getFrameDescriptor(scopeFrameNodeValue).getSlotKind(this.slot);
                if (this.isIntegerKind(scopeFrameNodeValue, integer_kind__) || this.isLongKind(scopeFrameNodeValue, integer_kind__) || this.isDoubleKind(scopeFrameNodeValue, integer_kind__)) {
                    this.doInteger(scopeFrameNodeValue, rhsNodeValue_, integer_kind__);
                    return;
                }
            }
            if ((state_0 & 4) != 0 && rhsNodeValue instanceof SafeInteger) {
                SafeInteger rhsNodeValue_2 = (SafeInteger)rhsNodeValue;
                if (this.isLongKind(scopeFrameNodeValue)) {
                    this.doSafeInteger(scopeFrameNodeValue, rhsNodeValue_2);
                    return;
                }
            }
            if ((state_0 & 8) != 0 && rhsNodeValue instanceof Long) {
                long rhsNodeValue_3 = (Long)rhsNodeValue;
                this.doLong(scopeFrameNodeValue, rhsNodeValue_3);
                return;
            }
            if ((state_0 & 0x10) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C0) >>> 6, rhsNodeValue)) {
                double rhsNodeValue_4 = JSTypesGen.asImplicitDouble((state_0 & 0x3C0) >>> 6, rhsNodeValue);
                if (this.isDoubleKind(scopeFrameNodeValue)) {
                    this.doDouble(scopeFrameNodeValue, rhsNodeValue_4);
                    return;
                }
            }
            if ((state_0 & 0x20) != 0) {
                this.doObject(scopeFrameNodeValue, rhsNodeValue);
                return;
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        this.executeAndSpecialize(scopeFrameNodeValue, rhsNodeValue);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        try {
            if ((state_0 & 0x37) == 0 && (state_0 & 0x3F) != 0) {
                this.executeLong(frameValue);
                return;
            }
            if ((state_0 & 0x3D) == 0 && (state_0 & 0x3F) != 0) {
                this.executeInt(frameValue);
                return;
            }
            if ((state_0 & 0x2F) == 0 && (state_0 & 0x3F) != 0) {
                this.executeDouble(frameValue);
                return;
            }
            if ((state_0 & 0x3E) == 0 && (state_0 & 0x3F) != 0) {
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object executeAndSpecialize(Frame scopeFrameNodeValue, Object rhsNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int doubleCast1;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if ((exclude & 1) == 0 && rhsNodeValue instanceof Boolean) {
                boolean rhsNodeValue_ = (Boolean)rhsNodeValue;
                if (this.isBooleanKind(scopeFrameNodeValue)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Boolean bl = this.doBoolean(scopeFrameNodeValue, rhsNodeValue_);
                    return bl;
                }
            }
            FrameSlotKind integer_kind__ = null;
            if ((exclude & 2) == 0 && rhsNodeValue instanceof Integer) {
                int rhsNodeValue_2 = (Integer)rhsNodeValue;
                integer_kind__ = this.getFrameDescriptor(scopeFrameNodeValue).getSlotKind(this.slot);
                if (this.isIntegerKind(scopeFrameNodeValue, integer_kind__) || this.isLongKind(scopeFrameNodeValue, integer_kind__) || this.isDoubleKind(scopeFrameNodeValue, integer_kind__)) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Integer n = this.doInteger(scopeFrameNodeValue, rhsNodeValue_2, integer_kind__);
                    return n;
                }
            }
            if ((exclude & 4) == 0 && rhsNodeValue instanceof SafeInteger) {
                SafeInteger rhsNodeValue_ = (SafeInteger)rhsNodeValue;
                if (this.isLongKind(scopeFrameNodeValue)) {
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    SafeInteger rhsNodeValue_2 = this.doSafeInteger(scopeFrameNodeValue, rhsNodeValue_);
                    return rhsNodeValue_2;
                }
            }
            if ((exclude & 8) == 0 && rhsNodeValue instanceof Long) {
                long rhsNodeValue_ = (Long)rhsNodeValue;
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                Long l = this.doLong(scopeFrameNodeValue, rhsNodeValue_);
                return l;
            }
            if ((exclude & 0x10) == 0 && (doubleCast1 = JSTypesGen.specializeImplicitDouble(rhsNodeValue)) != 0) {
                double rhsNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast1, rhsNodeValue);
                if (this.isDoubleKind(scopeFrameNodeValue)) {
                    this.exclude_ = exclude |= 6;
                    state_0 &= 0xFFFFFFF9;
                    state_0 |= doubleCast1 << 6;
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    Double d = this.doDouble(scopeFrameNodeValue, rhsNodeValue_);
                    return d;
                }
            }
            this.exclude_ = exclude |= 0x1F;
            state_0 &= 0xFFFFFFE0;
            this.state_0_ = state_0 |= 0x20;
            lock.unlock();
            hasLock = false;
            Object object = this.doObject(scopeFrameNodeValue, rhsNodeValue);
            return object;
        }
        finally {
            if (hasLock) {
                lock.unlock();
            }
        }
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if ((state_0 & 0x3F) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0x3F & (state_0 & 0x3F) - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[7];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doBoolean";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : ((exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[1] = s;
        s = new Object[3];
        s[0] = "doInteger";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<List<Object>> cached = new ArrayList<List<Object>>();
            cached.add(Arrays.asList(new Object[0]));
            s[2] = cached;
        } else {
            s[1] = (exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[2] = s;
        s = new Object[3];
        s[0] = "doSafeInteger";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : ((exclude & 4) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[3] = s;
        s = new Object[3];
        s[0] = "doLong";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : ((exclude & 8) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[4] = s;
        s = new Object[3];
        s[0] = "doDouble";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : ((exclude & 0x10) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[5] = s;
        s = new Object[3];
        s[0] = "doObject";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        return Introspection.Provider.create(data);
    }

    public static JSWriteScopeFrameSlotNode create(int slot, Object identifier, ScopeFrameNode scopeFrameNode, JavaScriptNode rhsNode) {
        return new JSWriteScopeFrameSlotNodeGen(slot, identifier, scopeFrameNode, rhsNode);
    }
}

