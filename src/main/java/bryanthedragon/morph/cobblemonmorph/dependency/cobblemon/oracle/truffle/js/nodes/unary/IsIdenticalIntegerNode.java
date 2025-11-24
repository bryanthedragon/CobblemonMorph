
package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.unary.IsIdenticalBaseNode;
import com.oracle.truffle.js.nodes.unary.IsIdenticalIntegerNodeGen;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSRuntime;
import java.util.Set;

public abstract class IsIdenticalIntegerNode
extends IsIdenticalBaseNode {
    private final int integer;

    protected IsIdenticalIntegerNode(JavaScriptNode operand, int integer, boolean leftConstant) {
        super(operand, leftConstant);
        this.integer = integer;
    }

    @Specialization
    protected boolean doInt(int a) {
        return a == this.integer;
    }

    @Specialization
    protected boolean doDouble(double a) {
        return a == (double)this.integer;
    }

    @Specialization
    protected boolean doBigInt(BigInt a) {
        return false;
    }

    @Specialization(guards={"isJavaNumber(a)"})
    protected boolean doJavaNumber(Object a) {
        double doubleValue = JSRuntime.toDouble(a);
        return doubleValue == (double)this.integer;
    }

    @Fallback
    protected boolean doOther(Object other) {
        return false;
    }

    public static IsIdenticalIntegerNode create(int integer, JavaScriptNode operand, boolean leftConstant) {
        return IsIdenticalIntegerNodeGen.create(operand, integer, leftConstant);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return IsIdenticalIntegerNode.create(this.integer, IsIdenticalIntegerNode.cloneUninitialized(this.getOperand(), materializedTags), this.leftConstant);
    }

    @Override
    protected Object getConstantValue() {
        return this.integer;
    }
}

