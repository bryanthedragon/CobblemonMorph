
package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.binary.JSBitwiseAndConstantNodeGen;
import com.oracle.truffle.js.nodes.binary.JSBitwiseAndNode;
import com.oracle.truffle.js.nodes.binary.JSBitwiseAndNodeGen;
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNode;
import com.oracle.truffle.js.nodes.cast.JSToInt32Node;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@NodeInfo(shortName="&")
public abstract class JSBitwiseAndConstantNode
extends JSUnaryNode {
    protected final boolean isInt;
    protected final int rightIntValue;
    protected final BigInt rightBigIntValue;

    protected JSBitwiseAndConstantNode(JavaScriptNode left, Object rightValue) {
        super(left);
        if (rightValue instanceof BigInt) {
            this.isInt = false;
            this.rightIntValue = 0;
            this.rightBigIntValue = (BigInt)rightValue;
        } else {
            this.isInt = true;
            this.rightIntValue = (Integer)rightValue;
            this.rightBigIntValue = null;
        }
    }

    public static JSBitwiseAndConstantNode create(JavaScriptNode left, Object right) {
        return JSBitwiseAndConstantNodeGen.create(left, right);
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
            JSConstantNode constantNode = JSConstantNode.create(this.isInt ? Integer.valueOf(this.rightIntValue) : this.rightBigIntValue);
            JSBitwiseAndNode node = JSBitwiseAndNodeGen.create(JSBitwiseAndConstantNode.cloneUninitialized(this.getOperand(), materializedTags), constantNode);
            JSBitwiseAndConstantNode.transferSourceSectionAddExpressionTag(this, constantNode);
            JSBitwiseAndConstantNode.transferSourceSectionAndTags(this, node);
            return node;
        }
        return this;
    }

    public abstract Object executeObject(Object var1);

    @Specialization(guards={"isInt"})
    protected int doInteger(int a) {
        return a & this.rightIntValue;
    }

    @Specialization(guards={"isInt"})
    protected int doSafeInteger(SafeInteger a) {
        return this.doInteger(a.intValue());
    }

    @Specialization(guards={"isInt"})
    protected int doDouble(double a, @Cached(value="create()") JSToInt32Node leftInt32) {
        return this.doInteger(leftInt32.executeInt(a));
    }

    @Specialization(guards={"!isInt"})
    protected void doIntegerThrows(int a) {
        throw Errors.createTypeErrorCannotMixBigIntWithOtherTypes(this);
    }

    @Specialization(guards={"!isInt"})
    protected void doDoubleThrows(double a) {
        throw Errors.createTypeErrorCannotMixBigIntWithOtherTypes(this);
    }

    @Specialization(guards={"isInt"})
    protected void doBigIntThrows(BigInt a) {
        throw Errors.createTypeErrorCannotMixBigIntWithOtherTypes(this);
    }

    @Specialization(guards={"!isInt"})
    protected BigInt doBigInt(BigInt a) {
        return a.and(this.rightBigIntValue);
    }

    @Specialization
    protected Object doOverloaded(JSOverloadedOperatorsObject a, @Cached(value="createNumeric(getOverloadedOperatorName())") JSOverloadedBinaryNode overloadedOperatorNode) {
        return overloadedOperatorNode.execute(a, this.isInt ? Integer.valueOf(this.rightIntValue) : this.rightBigIntValue);
    }

    protected TruffleString getOverloadedOperatorName() {
        return Strings.SYMBOL_AMPERSAND;
    }

    @Specialization(guards={"!hasOverloadedOperators(a)", "isInt"}, replaces={"doInteger", "doSafeInteger", "doDouble", "doBigIntThrows"})
    protected Object doGeneric(Object a, @Cached(value="create()") JSToNumericNode toNumeric, @Cached(value="createBinaryProfile()") ConditionProfile profileIsBigInt, @Cached(value="makeCopy()") JavaScriptNode innerAndNode) {
        Object numericA = toNumeric.execute(a);
        if (profileIsBigInt.profile(JSRuntime.isBigInt(numericA))) {
            throw Errors.createTypeErrorCannotMixBigIntWithOtherTypes(this);
        }
        return ((JSBitwiseAndConstantNode)innerAndNode).executeObject(numericA);
    }

    protected JSBitwiseAndConstantNode makeCopy() {
        return (JSBitwiseAndConstantNode)this.copyUninitialized(null);
    }

    protected final boolean isInt() {
        return this.isInt;
    }

    @Specialization(guards={"!hasOverloadedOperators(a)", "!isInt()"}, replaces={"doIntegerThrows", "doDoubleThrows", "doBigInt"})
    protected BigInt doGenericBigIntCase(Object a, @Cached(value="create()") JSToNumericNode toNumeric, @Cached(value="createBinaryProfile()") ConditionProfile profileIsBigInt) {
        Object numericA = toNumeric.execute(a);
        if (profileIsBigInt.profile(JSRuntime.isBigInt(numericA))) {
            return this.doBigInt((BigInt)numericA);
        }
        throw Errors.createTypeErrorCannotMixBigIntWithOtherTypes(this);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return JSBitwiseAndConstantNodeGen.create(JSBitwiseAndConstantNode.cloneUninitialized(this.getOperand(), materializedTags), (Object)(this.isInt ? Integer.valueOf(this.rightIntValue) : this.rightBigIntValue));
    }

    @Override
    public String expressionToString() {
        if (this.getOperand() != null) {
            return "(" + Objects.toString(this.getOperand().expressionToString(), "(intermediate value)") + " & " + (Serializable)(this.isInt ? Integer.valueOf(this.rightIntValue) : this.rightBigIntValue.toString()) + ")";
        }
        return null;
    }
}

