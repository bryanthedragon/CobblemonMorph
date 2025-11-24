
package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.Truncatable;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNode;
import com.oracle.truffle.js.nodes.binary.JSRightShiftConstantNodeGen;
import com.oracle.truffle.js.nodes.binary.JSRightShiftNode;
import com.oracle.truffle.js.nodes.binary.JSRightShiftNodeGen;
import com.oracle.truffle.js.nodes.cast.JSToInt32Node;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import java.util.Objects;
import java.util.Set;

@NodeInfo(shortName=">>")
public abstract class JSRightShiftConstantNode
extends JSUnaryNode {
    protected final int shiftValue;

    protected JSRightShiftConstantNode(JavaScriptNode operand, int shiftValue) {
        super(operand);
        this.shiftValue = shiftValue;
    }

    public static JavaScriptNode create(JavaScriptNode left, JavaScriptNode right) {
        assert (right instanceof JSConstantNode.JSConstantIntegerNode);
        int shiftValue = ((JSConstantNode.JSConstantIntegerNode)right).executeInt(null);
        if (left instanceof JSConstantNode.JSConstantIntegerNode) {
            int leftValue = ((JSConstantNode.JSConstantIntegerNode)left).executeInt(null);
            return JSConstantNode.createInt(leftValue >> shiftValue);
        }
        Truncatable.truncate(left);
        return JSRightShiftConstantNodeGen.create(left, shiftValue);
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
            JSConstantNode constantNode = JSConstantNode.createInt(this.shiftValue);
            JSRightShiftNode node = JSRightShiftNodeGen.create(JSRightShiftConstantNode.cloneUninitialized(this.getOperand(), materializedTags), constantNode);
            JSRightShiftConstantNode.transferSourceSectionAddExpressionTag(this, constantNode);
            JSRightShiftConstantNode.transferSourceSectionAndTags(this, node);
            return node;
        }
        return this;
    }

    public abstract int executeInt(Object var1);

    @Specialization
    protected int doInteger(int a) {
        return a >> this.shiftValue;
    }

    @Specialization
    protected int doSafeInteger(SafeInteger a) {
        return a.intValue() >> this.shiftValue;
    }

    @Specialization
    protected int doDouble(double a, @Cached(value="create()") JSToInt32Node leftInt32Node) {
        return leftInt32Node.executeInt(a) >> this.shiftValue;
    }

    @Specialization
    protected void doBigInt(BigInt a) {
        throw Errors.createTypeErrorCannotMixBigIntWithOtherTypes(this);
    }

    @Specialization
    protected Object doOverloaded(JSOverloadedOperatorsObject a, @Cached(value="createNumeric(getOverloadedOperatorName())") JSOverloadedBinaryNode overloadedOperatorNode) {
        return overloadedOperatorNode.execute(a, this.shiftValue);
    }

    protected TruffleString getOverloadedOperatorName() {
        return Strings.ANGLE_BRACKET_CLOSE_2;
    }

    @Specialization(guards={"!hasOverloadedOperators(a)"}, replaces={"doInteger", "doSafeInteger", "doDouble", "doBigInt"})
    protected int doGeneric(Object a, @Cached(value="create()") JSToNumericNode leftToNumeric, @Cached(value="makeCopy()") JSRightShiftConstantNode innerShiftNode) {
        Object leftOperand = leftToNumeric.execute(a);
        return innerShiftNode.executeInt(leftOperand);
    }

    protected JSRightShiftConstantNode makeCopy() {
        return (JSRightShiftConstantNode)this.copyUninitialized(null);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return JSRightShiftConstantNodeGen.create(JSRightShiftConstantNode.cloneUninitialized(this.getOperand(), materializedTags), this.shiftValue);
    }

    @Override
    public String expressionToString() {
        if (this.getOperand() != null) {
            return "(" + Objects.toString(this.getOperand().expressionToString(), "(intermediate value)") + " >> " + this.shiftValue + ")";
        }
        return null;
    }
}

