
package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.GenerateWrapper;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.unary.JSUnaryNodeWrapper;
import java.util.Objects;

@GenerateWrapper
public abstract class JSUnaryNode
extends JavaScriptNode {
    @Node.Child
    @Executed
    protected JavaScriptNode operandNode;

    protected JSUnaryNode(JavaScriptNode operand) {
        this.operandNode = operand;
    }

    protected JSUnaryNode() {
    }

    public final JavaScriptNode getOperand() {
        return this.operandNode;
    }

    public abstract Object execute(VirtualFrame var1, Object var2);

    @Override
    public String expressionToString() {
        String shortName;
        NodeInfo annotation;
        if (this.getOperand() != null && (annotation = this.getClass().getAnnotation(NodeInfo.class)) != null && !(shortName = annotation.shortName()).isEmpty()) {
            return "(" + shortName + (shortName.length() <= 2 ? "" : " ") + Objects.toString(this.getOperand().expressionToString(), "(intermediate value)") + ")";
        }
        return null;
    }

    @Override
    public Object getNodeObject() {
        String shortName;
        NodeInfo annotation = this.getClass().getAnnotation(NodeInfo.class);
        if (annotation != null && !(shortName = annotation.shortName()).isEmpty()) {
            return JSTags.createNodeObjectDescriptor("operator", annotation.shortName());
        }
        return null;
    }

    @Override
    public InstrumentableNode.WrapperNode createWrapper(ProbeNode probe) {
        return new JSUnaryNodeWrapper(this, probe);
    }
}

