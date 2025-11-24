
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.builtins.OperatorsBuiltins;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToNumericNodeGen;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import java.util.Set;

public abstract class JSToNumericNode
extends JavaScriptBaseNode {
    @Node.Child
    private JSToNumberNode toNumberNode;
    @Node.Child
    private JSToPrimitiveNode toPrimitiveNode;
    private final boolean toNumericOperand;

    public abstract Object execute(Object var1);

    protected JSToNumericNode(boolean toNumericOperand) {
        this.toNumericOperand = toNumericOperand;
    }

    public static JSToNumericNode create(boolean toNumericOperand) {
        return JSToNumericNodeGen.create(toNumericOperand);
    }

    public static JSToNumericNode create() {
        return JSToNumericNode.create(false);
    }

    public static JSToNumericNode createToNumericOperand() {
        return JSToNumericNode.create(true);
    }

    public static JavaScriptNode create(JavaScriptNode child, boolean toNumericOperand) {
        Object constantOperand;
        if (child.isResultAlwaysOfType(Number.class) || child.isResultAlwaysOfType(Integer.TYPE) || child.isResultAlwaysOfType(Double.TYPE)) {
            return child;
        }
        if (child instanceof JSConstantNode && (constantOperand = ((JSConstantNode)child).getValue()) != null && !(constantOperand instanceof Symbol) && JSRuntime.isJSPrimitive(constantOperand)) {
            return JSConstantNode.create(JSRuntime.toNumeric(constantOperand));
        }
        return JSToNumericNodeGen.JSToNumericWrapperNodeGen.create(child, toNumericOperand);
    }

    public static JavaScriptNode create(JavaScriptNode child) {
        return JSToNumericNode.create(child, false);
    }

    public static JavaScriptNode createToNumericOperand(JavaScriptNode child) {
        return JSToNumericNode.create(child, true);
    }

    @Specialization
    protected static int doInt(int value2) {
        return value2;
    }

    @Specialization
    protected static double doDouble(double value2) {
        return value2;
    }

    @Specialization
    protected Object doBigInt(BigInt value2) {
        return value2;
    }

    @Specialization(guards={"isJSBigInt(value)"})
    protected Object doJSBigInt(Object value2) {
        return this.toPrimitive(value2);
    }

    @Specialization(guards={"isToNumericOperand()"})
    protected Object doOverloaded(JSOverloadedOperatorsObject arg) {
        OperatorsBuiltins.checkOverloadedOperatorsAllowed(arg, this);
        return arg;
    }

    @Specialization(guards={"isToNumericOperand()", "!isJSBigInt(value)", "!hasOverloadedOperators(value)"})
    protected Object doToNumericOperandOther(Object value2) {
        Object primValue = this.toPrimitive(value2);
        if (JSRuntime.isBigInt(primValue)) {
            return primValue;
        }
        return this.toNumber(primValue);
    }

    @Specialization(guards={"!isToNumericOperand()", "!isJSBigInt(value)"})
    protected Object doToNumericOther(Object value2) {
        Object primValue = this.toPrimitive(value2);
        if (JSRuntime.isBigInt(primValue)) {
            return primValue;
        }
        return this.toNumber(primValue);
    }

    private Number toNumber(Object value2) {
        if (this.toNumberNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toNumberNode = this.insert(JSToNumberNode.create());
        }
        return this.toNumberNode.executeNumber(value2);
    }

    private Object toPrimitive(Object value2) {
        if (this.toPrimitiveNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toPrimitiveNode = this.insert(JSToPrimitiveNode.createHintNumber());
        }
        return this.toPrimitiveNode.execute(value2);
    }

    protected boolean isToNumericOperand() {
        return this.toNumericOperand;
    }

    public static abstract class JSToNumericWrapperNode
    extends JSUnaryNode {
        @Node.Child
        private JSToNumericNode toNumericNode;
        private final boolean toNumericOperand;

        protected JSToNumericWrapperNode(JavaScriptNode operand, boolean toNumericOperand) {
            super(operand);
            this.toNumericOperand = toNumericOperand;
        }

        @Specialization
        protected Object doDefault(Object value2) {
            if (this.toNumericNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toNumericNode = this.insert(JSToNumericNode.create(this.toNumericOperand));
            }
            return this.toNumericNode.execute(value2);
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return JSToNumericNode.create(JSToNumericWrapperNode.cloneUninitialized(this.getOperand(), materializedTags), this.toNumericOperand);
        }

        @Override
        public String expressionToString() {
            return this.getOperand().expressionToString();
        }
    }
}

