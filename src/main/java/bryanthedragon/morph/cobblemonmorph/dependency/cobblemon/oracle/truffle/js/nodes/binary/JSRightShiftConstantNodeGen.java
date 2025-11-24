
package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNode;
import com.oracle.truffle.js.nodes.binary.JSRightShiftConstantNode;
import com.oracle.truffle.js.nodes.cast.JSToInt32Node;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSRightShiftConstantNode.class)
public final class JSRightShiftConstantNodeGen
extends JSRightShiftConstantNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private JSToInt32Node double_leftInt32Node_;
    @Node.Child
    private JSOverloadedBinaryNode overloaded_overloadedOperatorNode_;
    @Node.Child
    private JSToNumericNode generic_leftToNumeric_;
    @Node.Child
    private JSRightShiftConstantNode generic_innerShiftNode_;

    private JSRightShiftConstantNodeGen(JavaScriptNode operand, int shiftValue) {
        super(operand, shiftValue);
    }

    @Override
    public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && operandNodeValue instanceof Integer) {
            int operandNodeValue_ = (Integer)operandNodeValue;
            return this.doInteger(operandNodeValue_);
        }
        if ((state_0 & 2) != 0 && operandNodeValue instanceof SafeInteger) {
            SafeInteger operandNodeValue_ = (SafeInteger)operandNodeValue;
            return this.doSafeInteger(operandNodeValue_);
        }
        if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C0) >>> 6, operandNodeValue)) {
            double operandNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0x3C0) >>> 6, operandNodeValue);
            return this.doDouble(operandNodeValue_, this.double_leftInt32Node_);
        }
        if ((state_0 & 0x10) != 0 && operandNodeValue instanceof JSOverloadedOperatorsObject) {
            JSOverloadedOperatorsObject operandNodeValue_ = (JSOverloadedOperatorsObject)operandNodeValue;
            return this.doOverloaded(operandNodeValue_, this.overloaded_overloadedOperatorNode_);
        }
        if ((state_0 & 0x20) != 0 && !this.hasOverloadedOperators(operandNodeValue)) {
            return this.doGeneric(operandNodeValue, this.generic_leftToNumeric_, this.generic_innerShiftNode_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0x36) == 0 && (state_0 & 0x37) != 0) {
            return this.execute_int0(state_0, frameValue);
        }
        if ((state_0 & 0x33) == 0 && (state_0 & 0x37) != 0) {
            return this.execute_double1(state_0, frameValue);
        }
        return this.execute_generic2(state_0, frameValue);
    }

    private Object execute_int0(int state_0, VirtualFrame frameValue) {
        int operandNodeValue_;
        try {
            operandNodeValue_ = this.operandNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 1) != 0);
        return this.doInteger(operandNodeValue_);
    }

    private Object execute_double1(int state_0, VirtualFrame frameValue) {
        double operandNodeValue_;
        long operandNodeValue_long = 0L;
        int operandNodeValue_int = 0;
        try {
            if ((state_0 & 0x380) == 0 && (state_0 & 0x3F) != 0) {
                operandNodeValue_ = this.operandNode.executeDouble(frameValue);
            } else if ((state_0 & 0x340) == 0 && (state_0 & 0x3F) != 0) {
                operandNodeValue_int = this.operandNode.executeInt(frameValue);
                operandNodeValue_ = JSTypes.intToDouble(operandNodeValue_int);
            } else if ((state_0 & 0x1C0) == 0 && (state_0 & 0x3F) != 0) {
                operandNodeValue_long = this.operandNode.executeLong(frameValue);
                operandNodeValue_ = JSTypes.longToDouble(operandNodeValue_long);
            } else {
                Object operandNodeValue__ = this.operandNode.execute(frameValue);
                operandNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C0) >>> 6, operandNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 4) != 0);
        return this.doDouble(operandNodeValue_, this.double_leftInt32Node_);
    }

    private Object execute_generic2(int state_0, VirtualFrame frameValue) {
        Object operandNodeValue_ = this.operandNode.execute(frameValue);
        if ((state_0 & 1) != 0 && operandNodeValue_ instanceof Integer) {
            int operandNodeValue__ = (Integer)operandNodeValue_;
            return this.doInteger(operandNodeValue__);
        }
        if ((state_0 & 2) != 0 && operandNodeValue_ instanceof SafeInteger) {
            SafeInteger operandNodeValue__ = (SafeInteger)operandNodeValue_;
            return this.doSafeInteger(operandNodeValue__);
        }
        if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C0) >>> 6, operandNodeValue_)) {
            double operandNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0x3C0) >>> 6, operandNodeValue_);
            return this.doDouble(operandNodeValue__, this.double_leftInt32Node_);
        }
        if ((state_0 & 0x10) != 0 && operandNodeValue_ instanceof JSOverloadedOperatorsObject) {
            JSOverloadedOperatorsObject operandNodeValue__ = (JSOverloadedOperatorsObject)operandNodeValue_;
            return this.doOverloaded(operandNodeValue__, this.overloaded_overloadedOperatorNode_);
        }
        if ((state_0 & 0x20) != 0 && !this.hasOverloadedOperators(operandNodeValue_)) {
            return this.doGeneric(operandNodeValue_, this.generic_leftToNumeric_, this.generic_innerShiftNode_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue_);
    }

    @Override
    public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
        int state_0 = this.state_0_;
        if ((state_0 & 0x10) != 0) {
            return JSTypesGen.expectInteger(this.execute(frameValue));
        }
        if ((state_0 & 0x26) == 0 && (state_0 & 0x27) != 0) {
            return this.executeInt_int3(state_0, frameValue);
        }
        if ((state_0 & 0x23) == 0 && (state_0 & 0x27) != 0) {
            return this.executeInt_double4(state_0, frameValue);
        }
        return this.executeInt_generic5(state_0, frameValue);
    }

    private int executeInt_int3(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
        int operandNodeValue_;
        try {
            operandNodeValue_ = this.operandNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(ex.getResult()));
        }
        assert ((state_0 & 1) != 0);
        return this.doInteger(operandNodeValue_);
    }

    private int executeInt_double4(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
        double operandNodeValue_;
        long operandNodeValue_long = 0L;
        int operandNodeValue_int = 0;
        try {
            if ((state_0 & 0x380) == 0 && (state_0 & 0x3F) != 0) {
                operandNodeValue_ = this.operandNode.executeDouble(frameValue);
            } else if ((state_0 & 0x340) == 0 && (state_0 & 0x3F) != 0) {
                operandNodeValue_int = this.operandNode.executeInt(frameValue);
                operandNodeValue_ = JSTypes.intToDouble(operandNodeValue_int);
            } else if ((state_0 & 0x1C0) == 0 && (state_0 & 0x3F) != 0) {
                operandNodeValue_long = this.operandNode.executeLong(frameValue);
                operandNodeValue_ = JSTypes.longToDouble(operandNodeValue_long);
            } else {
                Object operandNodeValue__ = this.operandNode.execute(frameValue);
                operandNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C0) >>> 6, operandNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(ex.getResult()));
        }
        assert ((state_0 & 4) != 0);
        return this.doDouble(operandNodeValue_, this.double_leftInt32Node_);
    }

    private int executeInt_generic5(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
        Object operandNodeValue_ = this.operandNode.execute(frameValue);
        if ((state_0 & 1) != 0 && operandNodeValue_ instanceof Integer) {
            int operandNodeValue__ = (Integer)operandNodeValue_;
            return this.doInteger(operandNodeValue__);
        }
        if ((state_0 & 2) != 0 && operandNodeValue_ instanceof SafeInteger) {
            SafeInteger operandNodeValue__ = (SafeInteger)operandNodeValue_;
            return this.doSafeInteger(operandNodeValue__);
        }
        if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C0) >>> 6, operandNodeValue_)) {
            double operandNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0x3C0) >>> 6, operandNodeValue_);
            return this.doDouble(operandNodeValue__, this.double_leftInt32Node_);
        }
        if ((state_0 & 0x20) != 0 && !this.hasOverloadedOperators(operandNodeValue_)) {
            return this.doGeneric(operandNodeValue_, this.generic_leftToNumeric_, this.generic_innerShiftNode_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectInteger(this.executeAndSpecialize(operandNodeValue_));
    }

    @Override
    public int executeInt(Object operandNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0x10) != 0) {
            return (Integer)this.execute(null, operandNodeValue);
        }
        if ((state_0 & 1) != 0 && operandNodeValue instanceof Integer) {
            int operandNodeValue_ = (Integer)operandNodeValue;
            return this.doInteger(operandNodeValue_);
        }
        if ((state_0 & 2) != 0 && operandNodeValue instanceof SafeInteger) {
            SafeInteger operandNodeValue_ = (SafeInteger)operandNodeValue;
            return this.doSafeInteger(operandNodeValue_);
        }
        if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C0) >>> 6, operandNodeValue)) {
            double operandNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0x3C0) >>> 6, operandNodeValue);
            return this.doDouble(operandNodeValue_, this.double_leftInt32Node_);
        }
        if ((state_0 & 0x20) != 0 && !this.hasOverloadedOperators(operandNodeValue)) {
            return this.doGeneric(operandNodeValue, this.generic_leftToNumeric_, this.generic_innerShiftNode_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return (Integer)this.executeAndSpecialize(operandNodeValue);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        try {
            if ((state_0 & 0x18) == 0 && (state_0 & 0x3F) != 0) {
                this.executeInt(frameValue);
                return;
            }
            if ((state_0 & 0x37) != 0) {
                this.execute(frameValue);
                return;
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return;
        }
        Object operandNodeValue_ = this.operandNode.execute(frameValue);
        if ((state_0 & 8) != 0 && operandNodeValue_ instanceof BigInt) {
            BigInt operandNodeValue__ = (BigInt)operandNodeValue_;
            this.doBigInt(operandNodeValue__);
            return;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        this.executeAndSpecialize(operandNodeValue_);
    }

    private Object executeAndSpecialize(Object operandNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int doubleCast0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if ((exclude & 1) == 0 && operandNodeValue instanceof Integer) {
                int operandNodeValue_ = (Integer)operandNodeValue;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Integer n = this.doInteger(operandNodeValue_);
                return n;
            }
            if ((exclude & 2) == 0 && operandNodeValue instanceof SafeInteger) {
                SafeInteger operandNodeValue_ = (SafeInteger)operandNodeValue;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                Integer n = this.doSafeInteger(operandNodeValue_);
                return n;
            }
            if ((exclude & 4) == 0 && (doubleCast0 = JSTypesGen.specializeImplicitDouble(operandNodeValue)) != 0) {
                double operandNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast0, operandNodeValue);
                this.double_leftInt32Node_ = super.insert(JSToInt32Node.create());
                state_0 |= doubleCast0 << 6;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                Integer n = this.doDouble(operandNodeValue_, this.double_leftInt32Node_);
                return n;
            }
            if ((exclude & 8) == 0 && operandNodeValue instanceof BigInt) {
                BigInt operandNodeValue_ = (BigInt)operandNodeValue;
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                this.doBigInt(operandNodeValue_);
                Object var7_15 = null;
                return var7_15;
            }
            if (operandNodeValue instanceof JSOverloadedOperatorsObject) {
                JSOverloadedOperatorsObject operandNodeValue_ = (JSOverloadedOperatorsObject)operandNodeValue;
                this.overloaded_overloadedOperatorNode_ = super.insert(JSOverloadedBinaryNode.createNumeric(this.getOverloadedOperatorName()));
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                Object object = this.doOverloaded(operandNodeValue_, this.overloaded_overloadedOperatorNode_);
                return object;
            }
            if (!this.hasOverloadedOperators(operandNodeValue)) {
                this.generic_leftToNumeric_ = super.insert(JSToNumericNode.create());
                this.generic_innerShiftNode_ = super.insert(this.makeCopy());
                this.exclude_ = exclude |= 0xF;
                state_0 &= 0xFFFFFFF0;
                this.state_0_ = state_0 |= 0x20;
                lock.unlock();
                hasLock = false;
                Integer n = this.doGeneric(operandNodeValue, this.generic_leftToNumeric_, this.generic_innerShiftNode_);
                return n;
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
        ArrayList<List<JavaScriptBaseNode>> cached;
        Object[] data = new Object[7];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doInteger";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : ((exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[1] = s;
        s = new Object[3];
        s[0] = "doSafeInteger";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : ((exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[2] = s;
        s = new Object[3];
        s[0] = "doDouble";
        if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<JavaScriptBaseNode>>();
            cached.add(Arrays.asList(this.double_leftInt32Node_));
            s[2] = cached;
        } else {
            s[1] = (exclude & 4) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[3] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : ((exclude & 8) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[4] = s;
        s = new Object[3];
        s[0] = "doOverloaded";
        if ((state_0 & 0x10) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.overloaded_overloadedOperatorNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[5] = s;
        s = new Object[3];
        s[0] = "doGeneric";
        if ((state_0 & 0x20) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.generic_leftToNumeric_, this.generic_innerShiftNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[6] = s;
        return Introspection.Provider.create(data);
    }

    public static JSRightShiftConstantNode create(JavaScriptNode operand, int shiftValue) {
        return new JSRightShiftConstantNodeGen(operand, shiftValue);
    }
}

