
package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.unary.IsIdenticalIntegerNode;
import com.oracle.truffle.js.runtime.BigInt;

@GeneratedBy(value=IsIdenticalIntegerNode.class)
public final class IsIdenticalIntegerNodeGen
extends IsIdenticalIntegerNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private int state_0_;

    private IsIdenticalIntegerNodeGen(JavaScriptNode operand, int integer, boolean leftConstant) {
        super(operand, integer, leftConstant);
    }

    @Override
    public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && operandNodeValue instanceof Integer) {
            int operandNodeValue_ = (Integer)operandNodeValue;
            return this.doInt(operandNodeValue_);
        }
        if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x1E0) >>> 5, operandNodeValue)) {
            double operandNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0x1E0) >>> 5, operandNodeValue);
            return this.doDouble(operandNodeValue_);
        }
        if ((state_0 & 4) != 0 && operandNodeValue instanceof BigInt) {
            BigInt operandNodeValue_ = (BigInt)operandNodeValue;
            return this.doBigInt(operandNodeValue_);
        }
        if ((state_0 & 0x18) != 0) {
            if ((state_0 & 8) != 0 && JSGuards.isJavaNumber(operandNodeValue)) {
                return this.doJavaNumber(operandNodeValue);
            }
            if ((state_0 & 0x10) != 0 && IsIdenticalIntegerNodeGen.fallbackGuard_(state_0, operandNodeValue)) {
                return this.doOther(operandNodeValue);
            }
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
        return this.doInt(operandNodeValue_);
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
        return this.doDouble(operandNodeValue_);
    }

    private Object execute_generic2(int state_0, VirtualFrame frameValue) {
        Object operandNodeValue_ = this.operandNode.execute(frameValue);
        if ((state_0 & 1) != 0 && operandNodeValue_ instanceof Integer) {
            int operandNodeValue__ = (Integer)operandNodeValue_;
            return this.doInt(operandNodeValue__);
        }
        if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x1E0) >>> 5, operandNodeValue_)) {
            double operandNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0x1E0) >>> 5, operandNodeValue_);
            return this.doDouble(operandNodeValue__);
        }
        if ((state_0 & 4) != 0 && operandNodeValue_ instanceof BigInt) {
            BigInt operandNodeValue__ = (BigInt)operandNodeValue_;
            return this.doBigInt(operandNodeValue__);
        }
        if ((state_0 & 0x18) != 0) {
            if ((state_0 & 8) != 0 && JSGuards.isJavaNumber(operandNodeValue_)) {
                return this.doJavaNumber(operandNodeValue_);
            }
            if ((state_0 & 0x10) != 0 && IsIdenticalIntegerNodeGen.fallbackGuard_(state_0, operandNodeValue_)) {
                return this.doOther(operandNodeValue_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue_);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0x1E) == 0 && (state_0 & 0x1F) != 0) {
            return this.executeBoolean_int3(state_0, frameValue);
        }
        if ((state_0 & 0x1D) == 0 && (state_0 & 0x1F) != 0) {
            return this.executeBoolean_double4(state_0, frameValue);
        }
        return this.executeBoolean_generic5(state_0, frameValue);
    }

    private boolean executeBoolean_int3(int state_0, VirtualFrame frameValue) {
        int operandNodeValue_;
        try {
            operandNodeValue_ = this.operandNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 1) != 0);
        return this.doInt(operandNodeValue_);
    }

    private boolean executeBoolean_double4(int state_0, VirtualFrame frameValue) {
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
        return this.doDouble(operandNodeValue_);
    }

    private boolean executeBoolean_generic5(int state_0, VirtualFrame frameValue) {
        Object operandNodeValue_ = this.operandNode.execute(frameValue);
        if ((state_0 & 1) != 0 && operandNodeValue_ instanceof Integer) {
            int operandNodeValue__ = (Integer)operandNodeValue_;
            return this.doInt(operandNodeValue__);
        }
        if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x1E0) >>> 5, operandNodeValue_)) {
            double operandNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0x1E0) >>> 5, operandNodeValue_);
            return this.doDouble(operandNodeValue__);
        }
        if ((state_0 & 4) != 0 && operandNodeValue_ instanceof BigInt) {
            BigInt operandNodeValue__ = (BigInt)operandNodeValue_;
            return this.doBigInt(operandNodeValue__);
        }
        if ((state_0 & 0x18) != 0) {
            if ((state_0 & 8) != 0 && JSGuards.isJavaNumber(operandNodeValue_)) {
                return this.doJavaNumber(operandNodeValue_);
            }
            if ((state_0 & 0x10) != 0 && IsIdenticalIntegerNodeGen.fallbackGuard_(state_0, operandNodeValue_)) {
                return this.doOther(operandNodeValue_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue_);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.executeBoolean(frameValue);
    }

    private boolean executeAndSpecialize(Object operandNodeValue) {
        int state_0 = this.state_0_;
        if (operandNodeValue instanceof Integer) {
            int operandNodeValue_ = (Integer)operandNodeValue;
            this.state_0_ = state_0 |= 1;
            return this.doInt(operandNodeValue_);
        }
        int doubleCast0 = JSTypesGen.specializeImplicitDouble(operandNodeValue);
        if (doubleCast0 != 0) {
            double operandNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast0, operandNodeValue);
            state_0 |= doubleCast0 << 5;
            this.state_0_ = state_0 |= 2;
            return this.doDouble(operandNodeValue_);
        }
        if (operandNodeValue instanceof BigInt) {
            BigInt operandNodeValue_ = (BigInt)operandNodeValue;
            this.state_0_ = state_0 |= 4;
            return this.doBigInt(operandNodeValue_);
        }
        if (JSGuards.isJavaNumber(operandNodeValue)) {
            this.state_0_ = state_0 |= 8;
            return this.doJavaNumber(operandNodeValue);
        }
        this.state_0_ = state_0 |= 0x10;
        return this.doOther(operandNodeValue);
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
        s[0] = "doJavaNumber";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doOther";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        return Introspection.Provider.create(data);
    }

    private static boolean fallbackGuard_(int state_0, Object operandNodeValue) {
        if (JSTypesGen.isImplicitDouble(operandNodeValue)) {
            return false;
        }
        if ((state_0 & 4) == 0 && operandNodeValue instanceof BigInt) {
            return false;
        }
        return (state_0 & 8) != 0 || !JSGuards.isJavaNumber(operandNodeValue);
    }

    public static IsIdenticalIntegerNode create(JavaScriptNode operand, int integer, boolean leftConstant) {
        return new IsIdenticalIntegerNodeGen(operand, integer, leftConstant);
    }
}

