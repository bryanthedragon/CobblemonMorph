
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanUnaryNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Symbol;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSToBooleanUnaryNode.class)
public final class JSToBooleanUnaryNodeGen
extends JSToBooleanUnaryNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private JSToBooleanNode foreignObject_toBooleanNode_;

    private JSToBooleanUnaryNodeGen(JavaScriptNode operand) {
        super(operand);
    }

    @Override
    public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && operandNodeValue instanceof Boolean) {
            boolean operandNodeValue_ = (Boolean)operandNodeValue;
            return JSToBooleanUnaryNode.doBoolean(operandNodeValue_);
        }
        if ((state_0 & 6) != 0) {
            if ((state_0 & 2) != 0 && JSGuards.isJSNull(operandNodeValue)) {
                return JSToBooleanUnaryNode.doNull(operandNodeValue);
            }
            if ((state_0 & 4) != 0 && JSGuards.isUndefined(operandNodeValue)) {
                return JSToBooleanUnaryNode.doUndefined(operandNodeValue);
            }
        }
        if ((state_0 & 8) != 0 && operandNodeValue instanceof Integer) {
            int operandNodeValue_ = (Integer)operandNodeValue;
            return JSToBooleanUnaryNode.doInt(operandNodeValue_);
        }
        if ((state_0 & 0x10) != 0 && operandNodeValue instanceof Long) {
            long operandNodeValue_ = (Long)operandNodeValue;
            return JSToBooleanUnaryNode.doLong(operandNodeValue_);
        }
        if ((state_0 & 0x20) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x7800) >>> 11, operandNodeValue)) {
            double operandNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0x7800) >>> 11, operandNodeValue);
            return JSToBooleanUnaryNode.doDouble(operandNodeValue_);
        }
        if ((state_0 & 0x40) != 0 && operandNodeValue instanceof BigInt) {
            BigInt operandNodeValue_ = (BigInt)operandNodeValue;
            return JSToBooleanUnaryNode.doBigInt(operandNodeValue_);
        }
        if ((state_0 & 0x80) != 0 && operandNodeValue instanceof TruffleString) {
            TruffleString operandNodeValue_ = (TruffleString)operandNodeValue;
            return JSToBooleanUnaryNode.doString(operandNodeValue_);
        }
        if ((state_0 & 0x100) != 0 && JSGuards.isJSObject(operandNodeValue)) {
            return JSToBooleanUnaryNode.doObject(operandNodeValue);
        }
        if ((state_0 & 0x200) != 0 && operandNodeValue instanceof Symbol) {
            Symbol operandNodeValue_ = (Symbol)operandNodeValue;
            return JSToBooleanUnaryNode.doSymbol(operandNodeValue_);
        }
        if ((state_0 & 0x400) != 0 && JSGuards.isForeignObject(operandNodeValue)) {
            return JSToBooleanUnaryNode.doForeignObject(operandNodeValue, this.foreignObject_toBooleanNode_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0x7FE) == 0 && (state_0 & 0x7FF) != 0) {
            return this.executeBoolean_boolean0(state_0, frameValue);
        }
        if ((state_0 & 0x7F7) == 0 && (state_0 & 0x7FF) != 0) {
            return this.executeBoolean_int1(state_0, frameValue);
        }
        if ((state_0 & 0x7EF) == 0 && (state_0 & 0x7FF) != 0) {
            return this.executeBoolean_long2(state_0, frameValue);
        }
        if ((state_0 & 0x7DF) == 0 && (state_0 & 0x7FF) != 0) {
            return this.executeBoolean_double3(state_0, frameValue);
        }
        return this.executeBoolean_generic4(state_0, frameValue);
    }

    private boolean executeBoolean_boolean0(int state_0, VirtualFrame frameValue) {
        boolean operandNodeValue_;
        try {
            operandNodeValue_ = this.operandNode.executeBoolean(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 1) != 0);
        return JSToBooleanUnaryNode.doBoolean(operandNodeValue_);
    }

    private boolean executeBoolean_int1(int state_0, VirtualFrame frameValue) {
        int operandNodeValue_;
        try {
            operandNodeValue_ = this.operandNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 8) != 0);
        return JSToBooleanUnaryNode.doInt(operandNodeValue_);
    }

    private boolean executeBoolean_long2(int state_0, VirtualFrame frameValue) {
        long operandNodeValue_;
        try {
            operandNodeValue_ = this.operandNode.executeLong(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 0x10) != 0);
        return JSToBooleanUnaryNode.doLong(operandNodeValue_);
    }

    private boolean executeBoolean_double3(int state_0, VirtualFrame frameValue) {
        double operandNodeValue_;
        long operandNodeValue_long = 0L;
        int operandNodeValue_int = 0;
        try {
            if ((state_0 & 0x7000) == 0 && (state_0 & 0x7FF) != 0) {
                operandNodeValue_ = this.operandNode.executeDouble(frameValue);
            } else if ((state_0 & 0x6800) == 0 && (state_0 & 0x7FF) != 0) {
                operandNodeValue_int = this.operandNode.executeInt(frameValue);
                operandNodeValue_ = JSTypes.intToDouble(operandNodeValue_int);
            } else if ((state_0 & 0x3800) == 0 && (state_0 & 0x7FF) != 0) {
                operandNodeValue_long = this.operandNode.executeLong(frameValue);
                operandNodeValue_ = JSTypes.longToDouble(operandNodeValue_long);
            } else {
                Object operandNodeValue__ = this.operandNode.execute(frameValue);
                operandNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x7800) >>> 11, operandNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 0x20) != 0);
        return JSToBooleanUnaryNode.doDouble(operandNodeValue_);
    }

    private boolean executeBoolean_generic4(int state_0, VirtualFrame frameValue) {
        Object operandNodeValue_ = this.operandNode.execute(frameValue);
        if ((state_0 & 1) != 0 && operandNodeValue_ instanceof Boolean) {
            boolean operandNodeValue__ = (Boolean)operandNodeValue_;
            return JSToBooleanUnaryNode.doBoolean(operandNodeValue__);
        }
        if ((state_0 & 6) != 0) {
            if ((state_0 & 2) != 0 && JSGuards.isJSNull(operandNodeValue_)) {
                return JSToBooleanUnaryNode.doNull(operandNodeValue_);
            }
            if ((state_0 & 4) != 0 && JSGuards.isUndefined(operandNodeValue_)) {
                return JSToBooleanUnaryNode.doUndefined(operandNodeValue_);
            }
        }
        if ((state_0 & 8) != 0 && operandNodeValue_ instanceof Integer) {
            int operandNodeValue__ = (Integer)operandNodeValue_;
            return JSToBooleanUnaryNode.doInt(operandNodeValue__);
        }
        if ((state_0 & 0x10) != 0 && operandNodeValue_ instanceof Long) {
            long operandNodeValue__ = (Long)operandNodeValue_;
            return JSToBooleanUnaryNode.doLong(operandNodeValue__);
        }
        if ((state_0 & 0x20) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x7800) >>> 11, operandNodeValue_)) {
            double operandNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0x7800) >>> 11, operandNodeValue_);
            return JSToBooleanUnaryNode.doDouble(operandNodeValue__);
        }
        if ((state_0 & 0x40) != 0 && operandNodeValue_ instanceof BigInt) {
            BigInt operandNodeValue__ = (BigInt)operandNodeValue_;
            return JSToBooleanUnaryNode.doBigInt(operandNodeValue__);
        }
        if ((state_0 & 0x80) != 0 && operandNodeValue_ instanceof TruffleString) {
            TruffleString operandNodeValue__ = (TruffleString)operandNodeValue_;
            return JSToBooleanUnaryNode.doString(operandNodeValue__);
        }
        if ((state_0 & 0x100) != 0 && JSGuards.isJSObject(operandNodeValue_)) {
            return JSToBooleanUnaryNode.doObject(operandNodeValue_);
        }
        if ((state_0 & 0x200) != 0 && operandNodeValue_ instanceof Symbol) {
            Symbol operandNodeValue__ = (Symbol)operandNodeValue_;
            return JSToBooleanUnaryNode.doSymbol(operandNodeValue__);
        }
        if ((state_0 & 0x400) != 0 && JSGuards.isForeignObject(operandNodeValue_)) {
            return JSToBooleanUnaryNode.doForeignObject(operandNodeValue_, this.foreignObject_toBooleanNode_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue_);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.executeBoolean(frameValue);
    }

    private boolean executeAndSpecialize(Object operandNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            if (operandNodeValue instanceof Boolean) {
                boolean operandNodeValue_ = (Boolean)operandNodeValue;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                boolean bl = JSToBooleanUnaryNode.doBoolean(operandNodeValue_);
                return bl;
            }
            if (JSGuards.isJSNull(operandNodeValue)) {
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                boolean operandNodeValue_ = JSToBooleanUnaryNode.doNull(operandNodeValue);
                return operandNodeValue_;
            }
            if (JSGuards.isUndefined(operandNodeValue)) {
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                boolean operandNodeValue_ = JSToBooleanUnaryNode.doUndefined(operandNodeValue);
                return operandNodeValue_;
            }
            if (operandNodeValue instanceof Integer) {
                int operandNodeValue_ = (Integer)operandNodeValue;
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                boolean bl = JSToBooleanUnaryNode.doInt(operandNodeValue_);
                return bl;
            }
            if (operandNodeValue instanceof Long) {
                long operandNodeValue_ = (Long)operandNodeValue;
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                boolean bl = JSToBooleanUnaryNode.doLong(operandNodeValue_);
                return bl;
            }
            int doubleCast0 = JSTypesGen.specializeImplicitDouble(operandNodeValue);
            if (doubleCast0 != 0) {
                double operandNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast0, operandNodeValue);
                state_0 |= doubleCast0 << 11;
                this.state_0_ = state_0 |= 0x20;
                lock.unlock();
                hasLock = false;
                boolean bl = JSToBooleanUnaryNode.doDouble(operandNodeValue_);
                return bl;
            }
            if (operandNodeValue instanceof BigInt) {
                BigInt operandNodeValue_ = (BigInt)operandNodeValue;
                this.state_0_ = state_0 |= 0x40;
                lock.unlock();
                hasLock = false;
                boolean bl = JSToBooleanUnaryNode.doBigInt(operandNodeValue_);
                return bl;
            }
            if (operandNodeValue instanceof TruffleString) {
                TruffleString operandNodeValue_ = (TruffleString)operandNodeValue;
                this.state_0_ = state_0 |= 0x80;
                lock.unlock();
                hasLock = false;
                boolean bl = JSToBooleanUnaryNode.doString(operandNodeValue_);
                return bl;
            }
            if (JSGuards.isJSObject(operandNodeValue)) {
                this.state_0_ = state_0 |= 0x100;
                lock.unlock();
                hasLock = false;
                boolean operandNodeValue_ = JSToBooleanUnaryNode.doObject(operandNodeValue);
                return operandNodeValue_;
            }
            if (operandNodeValue instanceof Symbol) {
                Symbol operandNodeValue_ = (Symbol)operandNodeValue;
                this.state_0_ = state_0 |= 0x200;
                lock.unlock();
                hasLock = false;
                boolean bl = JSToBooleanUnaryNode.doSymbol(operandNodeValue_);
                return bl;
            }
            if (JSGuards.isForeignObject(operandNodeValue)) {
                this.foreignObject_toBooleanNode_ = super.insert(JSToBooleanNode.create());
                this.state_0_ = state_0 |= 0x400;
                lock.unlock();
                hasLock = false;
                boolean bl = JSToBooleanUnaryNode.doForeignObject(operandNodeValue, this.foreignObject_toBooleanNode_);
                return bl;
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.operandNode}, operandNodeValue);
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
        if ((state_0 & 0x7FF) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0x7FF & (state_0 & 0x7FF) - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[12];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "doBoolean";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doNull";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doUndefined";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doInt";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doLong";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doDouble";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[7] = s;
        s = new Object[3];
        s[0] = "doString";
        s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[8] = s;
        s = new Object[3];
        s[0] = "doObject";
        s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[9] = s;
        s = new Object[3];
        s[0] = "doSymbol";
        s[1] = (state_0 & 0x200) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[10] = s;
        s = new Object[3];
        s[0] = "doForeignObject";
        if ((state_0 & 0x400) != 0) {
            s[1] = (byte)1;
            ArrayList<List<JSToBooleanNode>> cached = new ArrayList<List<JSToBooleanNode>>();
            cached.add(Arrays.asList(this.foreignObject_toBooleanNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[11] = s;
        return Introspection.Provider.create(data);
    }

    public static JSToBooleanUnaryNode create(JavaScriptNode operand) {
        return new JSToBooleanUnaryNodeGen(operand);
    }
}

