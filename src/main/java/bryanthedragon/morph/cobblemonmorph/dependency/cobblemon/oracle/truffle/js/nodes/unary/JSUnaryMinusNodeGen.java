
package com.oracle.truffle.js.nodes.unary;

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
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.nodes.unary.JSOverloadedUnaryNode;
import com.oracle.truffle.js.nodes.unary.JSUnaryMinusNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSUnaryMinusNode.class)
public final class JSUnaryMinusNodeGen
extends JSUnaryMinusNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private JSOverloadedUnaryNode overloaded_overloadedOperatorNode_;
    @Node.Child
    private JSToNumericNode generic_toNumericNode_;
    @Node.Child
    private JSUnaryMinusNode generic_recursiveUnaryMinus_;

    private JSUnaryMinusNodeGen(JavaScriptNode operand) {
        super(operand);
    }

    @Override
    public Object execute(Object operandNodeValue) {
        int operandNodeValue_;
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && operandNodeValue instanceof Integer && JSUnaryMinusNode.isInt(operandNodeValue_ = ((Integer)operandNodeValue).intValue())) {
            return JSUnaryMinusNode.doInt(operandNodeValue_);
        }
        if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x1E0) >>> 5, operandNodeValue)) {
            double operandNodeValue_2 = JSTypesGen.asImplicitDouble((state_0 & 0x1E0) >>> 5, operandNodeValue);
            return JSUnaryMinusNode.doDouble(operandNodeValue_2);
        }
        if ((state_0 & 4) != 0 && operandNodeValue instanceof BigInt) {
            BigInt operandNodeValue_3 = (BigInt)operandNodeValue;
            return JSUnaryMinusNode.doBigInt(operandNodeValue_3);
        }
        if ((state_0 & 8) != 0 && operandNodeValue instanceof JSOverloadedOperatorsObject) {
            JSOverloadedOperatorsObject operandNodeValue_4 = (JSOverloadedOperatorsObject)operandNodeValue;
            return this.doOverloaded(operandNodeValue_4, this.overloaded_overloadedOperatorNode_);
        }
        if ((state_0 & 0x10) != 0 && !this.hasOverloadedOperators(operandNodeValue)) {
            return JSUnaryMinusNode.doGeneric(operandNodeValue, this.generic_toNumericNode_, this.generic_recursiveUnaryMinus_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue);
    }

    @Override
    public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
        int operandNodeValue_;
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && operandNodeValue instanceof Integer && JSUnaryMinusNode.isInt(operandNodeValue_ = ((Integer)operandNodeValue).intValue())) {
            return JSUnaryMinusNode.doInt(operandNodeValue_);
        }
        if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x1E0) >>> 5, operandNodeValue)) {
            double operandNodeValue_2 = JSTypesGen.asImplicitDouble((state_0 & 0x1E0) >>> 5, operandNodeValue);
            return JSUnaryMinusNode.doDouble(operandNodeValue_2);
        }
        if ((state_0 & 4) != 0 && operandNodeValue instanceof BigInt) {
            BigInt operandNodeValue_3 = (BigInt)operandNodeValue;
            return JSUnaryMinusNode.doBigInt(operandNodeValue_3);
        }
        if ((state_0 & 8) != 0 && operandNodeValue instanceof JSOverloadedOperatorsObject) {
            JSOverloadedOperatorsObject operandNodeValue_4 = (JSOverloadedOperatorsObject)operandNodeValue;
            return this.doOverloaded(operandNodeValue_4, this.overloaded_overloadedOperatorNode_);
        }
        if ((state_0 & 0x10) != 0 && !this.hasOverloadedOperators(operandNodeValue)) {
            return JSUnaryMinusNode.doGeneric(operandNodeValue, this.generic_toNumericNode_, this.generic_recursiveUnaryMinus_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0x1E) == 0 && (state_0 & 0x1F) != 0) {
            return this.execute_int0(state_0, frameValue);
        }
        if ((state_0 & 0x1D) == 0 && (state_0 & 0x1F) != 0) {
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
        if (JSUnaryMinusNode.isInt(operandNodeValue_)) {
            return JSUnaryMinusNode.doInt(operandNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue_);
    }

    private Object execute_double1(int state_0, VirtualFrame frameValue) {
        double operandNodeValue_;
        long operandNodeValue_long = 0L;
        int operandNodeValue_int = 0;
        try {
            if ((state_0 & 0x1C0) == 0 && (state_0 & 0x1F) != 0) {
                operandNodeValue_ = this.operandNode.executeDouble(frameValue);
            } else if ((state_0 & 0x1A0) == 0 && (state_0 & 0x1F) != 0) {
                operandNodeValue_int = this.operandNode.executeInt(frameValue);
                operandNodeValue_ = JSTypes.intToDouble(operandNodeValue_int);
            } else if ((state_0 & 0xE0) == 0 && (state_0 & 0x1F) != 0) {
                operandNodeValue_long = this.operandNode.executeLong(frameValue);
                operandNodeValue_ = JSTypes.longToDouble(operandNodeValue_long);
            } else {
                Object operandNodeValue__ = this.operandNode.execute(frameValue);
                operandNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x1E0) >>> 5, operandNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 2) != 0);
        return JSUnaryMinusNode.doDouble(operandNodeValue_);
    }

    private Object execute_generic2(int state_0, VirtualFrame frameValue) {
        int operandNodeValue__;
        Object operandNodeValue_ = this.operandNode.execute(frameValue);
        if ((state_0 & 1) != 0 && operandNodeValue_ instanceof Integer && JSUnaryMinusNode.isInt(operandNodeValue__ = ((Integer)operandNodeValue_).intValue())) {
            return JSUnaryMinusNode.doInt(operandNodeValue__);
        }
        if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x1E0) >>> 5, operandNodeValue_)) {
            double operandNodeValue__2 = JSTypesGen.asImplicitDouble((state_0 & 0x1E0) >>> 5, operandNodeValue_);
            return JSUnaryMinusNode.doDouble(operandNodeValue__2);
        }
        if ((state_0 & 4) != 0 && operandNodeValue_ instanceof BigInt) {
            BigInt operandNodeValue__3 = (BigInt)operandNodeValue_;
            return JSUnaryMinusNode.doBigInt(operandNodeValue__3);
        }
        if ((state_0 & 8) != 0 && operandNodeValue_ instanceof JSOverloadedOperatorsObject) {
            JSOverloadedOperatorsObject operandNodeValue__4 = (JSOverloadedOperatorsObject)operandNodeValue_;
            return this.doOverloaded(operandNodeValue__4, this.overloaded_overloadedOperatorNode_);
        }
        if ((state_0 & 0x10) != 0 && !this.hasOverloadedOperators(operandNodeValue_)) {
            return JSUnaryMinusNode.doGeneric(operandNodeValue_, this.generic_toNumericNode_, this.generic_recursiveUnaryMinus_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue_);
    }

    @Override
    public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
        double operandNodeValue_;
        int state_0 = this.state_0_;
        if ((state_0 & 0x18) != 0) {
            return JSTypesGen.expectDouble(this.execute(frameValue));
        }
        long operandNodeValue_long = 0L;
        int operandNodeValue_int = 0;
        try {
            if ((state_0 & 0x1C0) == 0 && (state_0 & 0x1F) != 0) {
                operandNodeValue_ = this.operandNode.executeDouble(frameValue);
            } else if ((state_0 & 0x1A0) == 0 && (state_0 & 0x1F) != 0) {
                operandNodeValue_int = this.operandNode.executeInt(frameValue);
                operandNodeValue_ = JSTypes.intToDouble(operandNodeValue_int);
            } else if ((state_0 & 0xE0) == 0 && (state_0 & 0x1F) != 0) {
                operandNodeValue_long = this.operandNode.executeLong(frameValue);
                operandNodeValue_ = JSTypes.longToDouble(operandNodeValue_long);
            } else {
                Object operandNodeValue__ = this.operandNode.execute(frameValue);
                operandNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x1E0) >>> 5, operandNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(this.executeAndSpecialize(ex.getResult()));
        }
        if ((state_0 & 2) != 0) {
            return JSUnaryMinusNode.doDouble(operandNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectDouble(this.executeAndSpecialize((state_0 & 0x1A0) == 0 && (state_0 & 0x1F) != 0 ? (Number)operandNodeValue_int : (Number)((state_0 & 0xE0) == 0 && (state_0 & 0x1F) != 0 ? (Number)operandNodeValue_long : (Number)operandNodeValue_)));
    }

    @Override
    public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
        int operandNodeValue_;
        int state_0 = this.state_0_;
        if ((state_0 & 0x18) != 0) {
            return JSTypesGen.expectInteger(this.execute(frameValue));
        }
        try {
            operandNodeValue_ = this.operandNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(ex.getResult()));
        }
        if ((state_0 & 1) != 0 && JSUnaryMinusNode.isInt(operandNodeValue_)) {
            return JSUnaryMinusNode.doInt(operandNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectInteger(this.executeAndSpecialize(operandNodeValue_));
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        try {
            if ((state_0 & 0x1E) == 0 && (state_0 & 0x1F) != 0) {
                this.executeInt(frameValue);
                return;
            }
            if ((state_0 & 0x1D) == 0 && (state_0 & 0x1F) != 0) {
                this.executeDouble(frameValue);
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

    private Object executeAndSpecialize(Object operandNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int operandNodeValue_;
            int state_0 = this.state_0_;
            if (operandNodeValue instanceof Integer && JSUnaryMinusNode.isInt(operandNodeValue_ = ((Integer)operandNodeValue).intValue())) {
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Integer n = JSUnaryMinusNode.doInt(operandNodeValue_);
                return n;
            }
            int doubleCast0 = JSTypesGen.specializeImplicitDouble(operandNodeValue);
            if (doubleCast0 != 0) {
                double operandNodeValue_2 = JSTypesGen.asImplicitDouble(doubleCast0, operandNodeValue);
                state_0 |= doubleCast0 << 5;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                Double d = JSUnaryMinusNode.doDouble(operandNodeValue_2);
                return d;
            }
            if (operandNodeValue instanceof BigInt) {
                BigInt operandNodeValue_3 = (BigInt)operandNodeValue;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                BigInt bigInt = JSUnaryMinusNode.doBigInt(operandNodeValue_3);
                return bigInt;
            }
            if (operandNodeValue instanceof JSOverloadedOperatorsObject) {
                JSOverloadedOperatorsObject operandNodeValue_4 = (JSOverloadedOperatorsObject)operandNodeValue;
                this.overloaded_overloadedOperatorNode_ = super.insert(JSOverloadedUnaryNode.create(this.getOverloadedOperatorName()));
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                Object object = this.doOverloaded(operandNodeValue_4, this.overloaded_overloadedOperatorNode_);
                return object;
            }
            if (!this.hasOverloadedOperators(operandNodeValue)) {
                this.generic_toNumericNode_ = super.insert(JSToNumericNode.create());
                this.generic_recursiveUnaryMinus_ = super.insert(JSUnaryMinusNode.create());
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                Object object = JSUnaryMinusNode.doGeneric(operandNodeValue, this.generic_toNumericNode_, this.generic_recursiveUnaryMinus_);
                return object;
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
        if ((state_0 & 0x1F) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0x1F & (state_0 & 0x1F) - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<JavaScriptBaseNode>> cached;
        Object[] data = new Object[6];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "doInt";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doDouble";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doOverloaded";
        if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<JavaScriptBaseNode>>();
            cached.add(Arrays.asList(this.overloaded_overloadedOperatorNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[4] = s;
        s = new Object[3];
        s[0] = "doGeneric";
        if ((state_0 & 0x10) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.generic_toNumericNode_, this.generic_recursiveUnaryMinus_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[5] = s;
        return Introspection.Provider.create(data);
    }

    public static JSUnaryMinusNode create(JavaScriptNode operand) {
        return new JSUnaryMinusNodeGen(operand);
    }
}

