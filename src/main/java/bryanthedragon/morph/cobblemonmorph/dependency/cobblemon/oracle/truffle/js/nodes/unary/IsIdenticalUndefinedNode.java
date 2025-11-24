
package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.unary.IsIdenticalBaseNode;
import com.oracle.truffle.js.nodes.unary.IsIdenticalUndefinedNodeGen;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

public abstract class IsIdenticalUndefinedNode
extends IsIdenticalBaseNode {
    protected IsIdenticalUndefinedNode(JavaScriptNode operand, boolean leftConstant) {
        super(operand, leftConstant);
    }

    @Specialization
    protected boolean doObject(Object a) {
        return a == Undefined.instance;
    }

    public static IsIdenticalUndefinedNode create(JavaScriptNode operand, boolean leftConstant) {
        return IsIdenticalUndefinedNodeGen.create(operand, leftConstant);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return IsIdenticalUndefinedNode.create(IsIdenticalUndefinedNode.cloneUninitialized(this.getOperand(), materializedTags), this.leftConstant);
    }

    @Override
    protected Object getConstantValue() {
        return Undefined.instance;
    }
}

