
package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.unary.IsIdenticalBaseNode;
import com.oracle.truffle.js.nodes.unary.IsIdenticalStringNodeGen;
import com.oracle.truffle.js.runtime.Strings;
import java.util.Set;

public abstract class IsIdenticalStringNode
extends IsIdenticalBaseNode {
    protected final TruffleString string;

    protected IsIdenticalStringNode(TruffleString string, JavaScriptNode operand, boolean leftConstant) {
        super(operand, leftConstant);
        this.string = string;
    }

    @Specialization
    protected boolean doString(TruffleString other, @Cached TruffleString.EqualNode equalsNode) {
        return Strings.equals(equalsNode, this.string, other);
    }

    @Specialization(guards={"!isTruffleString(other)"})
    protected boolean doOther(Object other) {
        return false;
    }

    public static IsIdenticalStringNode create(TruffleString string, JavaScriptNode operand, boolean leftConstant) {
        return IsIdenticalStringNodeGen.create(string, operand, leftConstant);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return IsIdenticalStringNode.create(this.string, IsIdenticalStringNode.cloneUninitialized(this.getOperand(), materializedTags), this.leftConstant);
    }

    @Override
    protected Object getConstantValue() {
        return this.string;
    }
}

