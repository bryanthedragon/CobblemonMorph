
package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.unary.IsIdenticalBaseNode;
import com.oracle.truffle.js.nodes.unary.IsIdenticalBooleanNodeGen;
import java.util.Set;

public abstract class IsIdenticalBooleanNode
extends IsIdenticalBaseNode {
    private final boolean bool;

    protected IsIdenticalBooleanNode(JavaScriptNode operand, boolean bool, boolean leftConstant) {
        super(operand, leftConstant);
        this.bool = bool;
    }

    @Specialization
    protected boolean doBoolean(boolean a) {
        return a == this.bool;
    }

    @Fallback
    protected boolean doOther(Object other) {
        return false;
    }

    public static IsIdenticalBooleanNode create(boolean bool, JavaScriptNode operand, boolean leftConstant) {
        return IsIdenticalBooleanNodeGen.create(operand, bool, leftConstant);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return IsIdenticalBooleanNode.create(this.bool, IsIdenticalBooleanNode.cloneUninitialized(this.getOperand(), materializedTags), this.leftConstant);
    }

    @Override
    protected Object getConstantValue() {
        return this.bool;
    }
}

