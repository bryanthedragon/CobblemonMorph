
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.control.EmptyNode;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

@NodeInfo(cost=NodeCost.NONE)
public class DiscardResultNode
extends JSUnaryNode {
    protected DiscardResultNode(JavaScriptNode operand) {
        super(operand);
    }

    public static JavaScriptNode create(JavaScriptNode operand) {
        if (operand.isResultAlwaysOfType(Undefined.class)) {
            return operand;
        }
        if (operand instanceof JSConstantNode) {
            return EmptyNode.create();
        }
        return new DiscardResultNode(operand);
    }

    @Override
    public boolean isResultAlwaysOfType(Class<?> clazz) {
        return clazz == Undefined.class;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return new DiscardResultNode(DiscardResultNode.cloneUninitialized(this.getOperand(), materializedTags));
    }

    @Override
    public Object execute(VirtualFrame frame, Object operandValue) {
        return Undefined.instance;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        this.operandNode.execute(frame);
        return Undefined.instance;
    }

    @Override
    public void executeVoid(VirtualFrame frame) {
        this.operandNode.executeVoid(frame);
    }
}

