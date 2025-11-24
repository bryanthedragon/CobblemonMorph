
package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.unary.IsIdenticalBooleanNode;

@GeneratedBy(value=IsIdenticalBooleanNode.class)
public final class IsIdenticalBooleanNodeGen
extends IsIdenticalBooleanNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private int state_0_;

    private IsIdenticalBooleanNodeGen(JavaScriptNode operand, boolean bool, boolean leftConstant) {
        super(operand, bool, leftConstant);
    }

    @Override
    public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && operandNodeValue instanceof Boolean) {
            boolean operandNodeValue_ = (Boolean)operandNodeValue;
            return this.doBoolean(operandNodeValue_);
        }
        if ((state_0 & 2) != 0 && IsIdenticalBooleanNodeGen.fallbackGuard_(state_0, operandNodeValue)) {
            return this.doOther(operandNodeValue);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 2) == 0 && state_0 != 0) {
            return this.execute_boolean0(state_0, frameValue);
        }
        return this.execute_generic1(state_0, frameValue);
    }

    private Object execute_boolean0(int state_0, VirtualFrame frameValue) {
        boolean operandNodeValue_;
        try {
            operandNodeValue_ = this.operandNode.executeBoolean(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 1) != 0);
        return this.doBoolean(operandNodeValue_);
    }

    private Object execute_generic1(int state_0, VirtualFrame frameValue) {
        Object operandNodeValue_ = this.operandNode.execute(frameValue);
        if ((state_0 & 1) != 0 && operandNodeValue_ instanceof Boolean) {
            boolean operandNodeValue__ = (Boolean)operandNodeValue_;
            return this.doBoolean(operandNodeValue__);
        }
        if ((state_0 & 2) != 0 && IsIdenticalBooleanNodeGen.fallbackGuard_(state_0, operandNodeValue_)) {
            return this.doOther(operandNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue_);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 2) == 0 && state_0 != 0) {
            return this.executeBoolean_boolean2(state_0, frameValue);
        }
        return this.executeBoolean_generic3(state_0, frameValue);
    }

    private boolean executeBoolean_boolean2(int state_0, VirtualFrame frameValue) {
        boolean operandNodeValue_;
        try {
            operandNodeValue_ = this.operandNode.executeBoolean(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 1) != 0);
        return this.doBoolean(operandNodeValue_);
    }

    private boolean executeBoolean_generic3(int state_0, VirtualFrame frameValue) {
        Object operandNodeValue_ = this.operandNode.execute(frameValue);
        if ((state_0 & 1) != 0 && operandNodeValue_ instanceof Boolean) {
            boolean operandNodeValue__ = (Boolean)operandNodeValue_;
            return this.doBoolean(operandNodeValue__);
        }
        if ((state_0 & 2) != 0 && IsIdenticalBooleanNodeGen.fallbackGuard_(state_0, operandNodeValue_)) {
            return this.doOther(operandNodeValue_);
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
        if (operandNodeValue instanceof Boolean) {
            boolean operandNodeValue_ = (Boolean)operandNodeValue;
            this.state_0_ = state_0 |= 1;
            return this.doBoolean(operandNodeValue_);
        }
        this.state_0_ = state_0 |= 2;
        return this.doOther(operandNodeValue);
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
        Object[] data = new Object[3];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "doBoolean";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doOther";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        return Introspection.Provider.create(data);
    }

    private static boolean fallbackGuard_(int state_0, Object operandNodeValue) {
        return (state_0 & 1) != 0 || !(operandNodeValue instanceof Boolean);
    }

    public static IsIdenticalBooleanNode create(JavaScriptNode operand, boolean bool, boolean leftConstant) {
        return new IsIdenticalBooleanNodeGen(operand, bool, leftConstant);
    }
}

