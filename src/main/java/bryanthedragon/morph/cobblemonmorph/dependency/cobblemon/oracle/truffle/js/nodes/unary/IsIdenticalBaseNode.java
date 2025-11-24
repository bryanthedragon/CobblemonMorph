
package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.RepeatableNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.binary.JSIdenticalNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import java.util.Set;

public abstract class IsIdenticalBaseNode
extends JSUnaryNode
implements RepeatableNode {
    protected final boolean leftConstant;

    public IsIdenticalBaseNode(JavaScriptNode operand, boolean leftConstant) {
        super(operand);
        this.leftConstant = leftConstant;
    }

    protected abstract Object getConstantValue();

    @Override
    public boolean isResultAlwaysOfType(Class<?> clazz) {
        return clazz == Boolean.TYPE;
    }

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        if (tag == JSTags.BinaryOperationTag.class) {
            return true;
        }
        return super.hasTag(tag);
    }

    @Override
    public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
        if (materializedTags.contains(JSTags.BinaryOperationTag.class)) {
            JSConstantNode constantNode = JSConstantNode.create(this.getConstantValue());
            JavaScriptNode newOperand = IsIdenticalBaseNode.cloneUninitialized(this.getOperand(), materializedTags);
            JavaScriptNode materialized = this.leftConstant ? JSIdenticalNode.createUnoptimized(constantNode, newOperand) : JSIdenticalNode.createUnoptimized(newOperand, constantNode);
            IsIdenticalBaseNode.transferSourceSectionAddExpressionTag(this, constantNode);
            IsIdenticalBaseNode.transferSourceSectionAndTags(this, materialized);
            return materialized;
        }
        return this;
    }
}

