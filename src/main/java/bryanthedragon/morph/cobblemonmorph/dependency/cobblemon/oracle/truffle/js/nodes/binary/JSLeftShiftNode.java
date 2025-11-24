
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
import com.oracle.truffle.js.nodes.binary.JSLeftShiftConstantNode;
import com.oracle.truffle.js.nodes.binary.JSLeftShiftNodeGen;
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNode;
import com.oracle.truffle.js.nodes.cast.JSToInt32Node;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.nodes.cast.JSToUInt32Node;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.Strings;
import java.util.Set;

@NodeInfo(shortName="<<")
public abstract class JSLeftShiftNode
extends JSBinaryNode {
    protected JSLeftShiftNode(JavaScriptNode left, JavaScriptNode right) {
        super(left, right);
    }

    public static JavaScriptNode create(JavaScriptNode left, JavaScriptNode right) {
        Truncatable.truncate(left);
        Truncatable.truncate(right);
        if (right instanceof JSConstantNode.JSConstantIntegerNode) {
            return JSLeftShiftConstantNode.create(left, right);
        }
        return JSLeftShiftNodeGen.create(left, right);
    }

    public abstract Object executeObject(Object var1, Object var2);

    @Specialization
    protected int doInteger(int a, int b) {
        return a << b;
    }

    @Specialization(guards={"!largerThan2e32(b)"})
    protected int doIntegerDouble(int a, double b) {
        return a << (int)b;
    }

    @Specialization
    protected Object doDouble(double a, double b, @Cached(value="create()") JSLeftShiftNode leftShift, @Cached(value="create()") JSToInt32Node leftInt32, @Cached(value="create()") JSToUInt32Node rightUInt32) {
        return leftShift.executeObject(leftInt32.executeInt(a), rightUInt32.execute(b));
    }

    @Specialization
    protected BigInt doBigInt(BigInt a, BigInt b) {
        if (b.compareTo(BigInt.MAX_INT) < 0) {
            if (b.compareTo(BigInt.MIN_INT) > 0) {
                try {
                    return a.shiftLeft(b.intValue());
                }
                catch (ArithmeticException ae) {
                    throw Errors.createRangeErrorBigIntMaxSizeExceeded();
                }
            }
            return a.signum() < 0 ? BigInt.NEGATIVE_ONE : BigInt.ZERO;
        }
        throw Errors.createRangeErrorBigIntMaxSizeExceeded();
    }

    @Specialization(guards={"hasOverloadedOperators(a) || hasOverloadedOperators(b)"})
    protected Object doOverloaded(Object a, Object b, @Cached(value="createNumeric(getOverloadedOperatorName())") JSOverloadedBinaryNode overloadedOperatorNode) {
        return overloadedOperatorNode.execute(a, b);
    }

    protected TruffleString getOverloadedOperatorName() {
        return Strings.ANGLE_BRACKET_OPEN_2;
    }

    @Specialization(guards={"!hasOverloadedOperators(a)", "!hasOverloadedOperators(b)"}, replaces={"doInteger", "doIntegerDouble", "doDouble", "doBigInt"})
    protected Object doGeneric(Object a, Object b, @Cached(value="create()") JSLeftShiftNode leftShift, @Cached(value="create()") JSToNumericNode leftToNumeric, @Cached(value="create()") JSToNumericNode rightToNumeric, @Cached(value="create()") BranchProfile mixedNumericTypes) {
        Object operandA = leftToNumeric.execute(a);
        Object operandB = rightToNumeric.execute(b);
        this.ensureBothSameNumericType(operandA, operandB, mixedNumericTypes);
        return leftShift.executeObject(operandA, operandB);
    }

    public static JSLeftShiftNode create() {
        return JSLeftShiftNodeGen.create(null, null);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return JSLeftShiftNodeGen.create(JSLeftShiftNode.cloneUninitialized(this.getLeft(), materializedTags), JSLeftShiftNode.cloneUninitialized(this.getRight(), materializedTags));
    }
}

