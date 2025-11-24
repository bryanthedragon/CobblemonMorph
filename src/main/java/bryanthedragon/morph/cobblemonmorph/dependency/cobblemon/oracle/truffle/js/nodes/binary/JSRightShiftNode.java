
package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.Truncatable;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.binary.JSBinaryNode;
import com.oracle.truffle.js.nodes.binary.JSLeftShiftNode;
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNode;
import com.oracle.truffle.js.nodes.binary.JSRightShiftConstantNode;
import com.oracle.truffle.js.nodes.binary.JSRightShiftNodeGen;
import com.oracle.truffle.js.nodes.cast.JSToInt32Node;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.nodes.cast.JSToUInt32Node;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Strings;
import java.util.Set;

@NodeInfo(shortName=">>")
public abstract class JSRightShiftNode
extends JSBinaryNode {
    protected JSRightShiftNode(JavaScriptNode left, JavaScriptNode right) {
        super(left, right);
    }

    public static JavaScriptNode create(JavaScriptNode left, JavaScriptNode right) {
        Truncatable.truncate(left);
        Truncatable.truncate(right);
        if (right instanceof JSConstantNode.JSConstantIntegerNode) {
            return JSRightShiftConstantNode.create(left, right);
        }
        return JSRightShiftNodeGen.create(left, right);
    }

    public abstract Object execute(Object var1, Object var2);

    @Specialization
    protected int doInteger(int a, int b) {
        return a >> b;
    }

    @Specialization
    protected BigInt doBigInt(BigInt a, BigInt b, @Cached(value="create()") JSLeftShiftNode leftShift) {
        return leftShift.doBigInt(a, b.negate());
    }

    @Specialization(guards={"!largerThan2e32(b)"})
    protected int doIntDouble(int a, double b) {
        return a >> (int)b;
    }

    @Specialization
    protected Object doDouble(double a, double b, @Cached(value="create()") JSRightShiftNode rightShift, @Cached(value="create()") JSToInt32Node leftInt32, @Cached(value="create()") JSToUInt32Node rightUInt32) {
        return rightShift.execute(leftInt32.executeInt(a), rightUInt32.execute(b));
    }

    @Specialization(guards={"hasOverloadedOperators(a) || hasOverloadedOperators(b)"})
    protected Object doOverloaded(Object a, Object b, @Cached(value="createNumeric(getOverloadedOperatorName())") JSOverloadedBinaryNode overloadedOperatorNode) {
        return overloadedOperatorNode.execute(a, b);
    }

    protected TruffleString getOverloadedOperatorName() {
        return Strings.ANGLE_BRACKET_CLOSE_2;
    }

    @Specialization(guards={"!hasOverloadedOperators(a)", "!hasOverloadedOperators(b)"}, replaces={"doInteger", "doIntDouble", "doDouble", "doBigInt"})
    protected Object doGeneric(Object a, Object b, @Cached(value="create()") JSRightShiftNode rightShift, @Cached(value="create()") JSToNumericNode leftToNumeric, @Cached(value="create()") JSToNumericNode rightToNumeric, @Cached(value="create()") BranchProfile mixedNumericTypes) {
        Object operandA = leftToNumeric.execute(a);
        Object operandB = rightToNumeric.execute(b);
        this.ensureBothSameNumericType(operandA, operandB, mixedNumericTypes);
        return rightShift.execute(operandA, operandB);
    }

    public static JSRightShiftNode create() {
        return JSRightShiftNodeGen.create(null, null);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return JSRightShiftNodeGen.create(JSRightShiftNode.cloneUninitialized(this.getLeft(), materializedTags), JSRightShiftNode.cloneUninitialized(this.getRight(), materializedTags));
    }
}

