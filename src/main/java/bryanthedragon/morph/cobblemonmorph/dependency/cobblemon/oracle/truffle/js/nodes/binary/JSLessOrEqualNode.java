
package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.binary.JSCompareNode;
import com.oracle.truffle.js.nodes.binary.JSLessOrEqualNodeGen;
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.cast.JSToStringOrNumberNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

@NodeInfo(shortName="<=")
public abstract class JSLessOrEqualNode
extends JSCompareNode {
    protected JSLessOrEqualNode(JavaScriptNode left, JavaScriptNode right) {
        super(left, right);
    }

    public static JSLessOrEqualNode create(JavaScriptNode left, JavaScriptNode right) {
        return JSLessOrEqualNodeGen.create(left, right);
    }

    public static JSLessOrEqualNode create() {
        return JSLessOrEqualNodeGen.create(null, null);
    }

    public abstract boolean executeBoolean(Object var1, Object var2);

    @Specialization
    protected boolean doInt(int a, int b) {
        return a <= b;
    }

    @Specialization
    protected boolean doSafeInteger(int a, SafeInteger b) {
        return (long)a <= b.longValue();
    }

    @Specialization
    protected boolean doSafeInteger(SafeInteger a, int b) {
        return a.longValue() <= (long)b;
    }

    @Specialization
    protected boolean doSafeInteger(SafeInteger a, SafeInteger b) {
        return a.longValue() <= b.longValue();
    }

    @Specialization
    protected boolean doDouble(double a, double b) {
        return a <= b;
    }

    @Specialization
    protected boolean doString(TruffleString a, TruffleString b, @Cached TruffleString.CompareCharsUTF16Node compareNode) {
        return Strings.compareTo(compareNode, a, b) <= 0;
    }

    @Specialization
    protected boolean doStringDouble(TruffleString a, double b) {
        return this.doDouble(this.stringToDouble(a), b);
    }

    @Specialization
    protected boolean doDoubleString(double a, TruffleString b) {
        return this.doDouble(a, this.stringToDouble(b));
    }

    @Specialization
    protected boolean doStringBigInt(TruffleString a, BigInt b) {
        BigInt aBigInt = JSRuntime.stringToBigInt(a);
        return aBigInt == null ? false : this.doBigInt(aBigInt, b);
    }

    @Specialization
    protected boolean doBigIntString(BigInt a, TruffleString b) {
        BigInt bBigInt = JSRuntime.stringToBigInt(b);
        return bBigInt == null ? false : this.doBigInt(a, bBigInt);
    }

    @Specialization
    protected boolean doBigInt(BigInt a, BigInt b) {
        return a.compareTo(b) <= 0;
    }

    @Specialization
    protected boolean doBigIntAndInt(BigInt a, int b) {
        return a.compareTo(BigInt.valueOf(b)) <= 0;
    }

    @Specialization
    protected boolean doBigIntAndNumber(BigInt a, double b) {
        if (Double.isNaN(b)) {
            return false;
        }
        return a.compareValueTo(b) <= 0;
    }

    @Specialization
    protected boolean doIntAndBigInt(int a, BigInt b) {
        return b.compareTo(BigInt.valueOf(a)) >= 0;
    }

    @Specialization
    protected boolean doNumberAndBigInt(double a, BigInt b) {
        if (Double.isNaN(a)) {
            return false;
        }
        return b.compareValueTo(a) >= 0;
    }

    @Specialization(guards={"isJavaNumber(a)", "isJavaNumber(b)"})
    protected boolean doJavaNumber(Object a, Object b) {
        return this.doDouble(JSRuntime.doubleValue((Number)a), JSRuntime.doubleValue((Number)b));
    }

    @Specialization(guards={"hasOverloadedOperators(a) || hasOverloadedOperators(b)"})
    protected boolean doOverloaded(Object a, Object b, @Cached(value="createHintNumberRightToLeft(getOverloadedOperatorName())") JSOverloadedBinaryNode overloadedOperatorNode, @Cached(value="create()") JSToBooleanNode toBooleanNode) {
        Object result = overloadedOperatorNode.execute(b, a);
        if (result == Undefined.instance) {
            return false;
        }
        return !toBooleanNode.executeBoolean(result);
    }

    protected TruffleString getOverloadedOperatorName() {
        return Strings.ANGLE_BRACKET_OPEN;
    }

    @Specialization(guards={"!hasOverloadedOperators(a)", "!hasOverloadedOperators(b)"}, replaces={"doInt", "doDouble", "doString", "doStringDouble", "doDoubleString", "doBigInt", "doBigIntAndNumber", "doNumberAndBigInt", "doJavaNumber"})
    protected boolean doGeneric(Object a, Object b, @Cached(value="create()") JSToStringOrNumberNode toStringOrNumber1, @Cached(value="createHintNumber()") JSToPrimitiveNode toPrimitive1, @Cached(value="create()") JSToStringOrNumberNode toStringOrNumber2, @Cached(value="createHintNumber()") JSToPrimitiveNode toPrimitive2, @Cached(value="create()") JSLessOrEqualNode lessOrEqualNode) {
        return lessOrEqualNode.executeBoolean(toStringOrNumber1.execute(toPrimitive1.execute(a)), toStringOrNumber2.execute(toPrimitive2.execute(b)));
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return JSLessOrEqualNodeGen.create(JSLessOrEqualNode.cloneUninitialized(this.getLeft(), materializedTags), JSLessOrEqualNode.cloneUninitialized(this.getRight(), materializedTags));
    }
}

