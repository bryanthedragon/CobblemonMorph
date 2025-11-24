
package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.binary.JSBinaryNode;
import com.oracle.truffle.js.nodes.binary.JSMultiplyNodeGen;
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNode;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import java.util.Set;

@NodeInfo(shortName="*")
public abstract class JSMultiplyNode
extends JSBinaryNode {
    protected JSMultiplyNode(JavaScriptNode left, JavaScriptNode right) {
        super(left, right);
    }

    public static JavaScriptNode create(JavaScriptNode left, JavaScriptNode right) {
        if (left instanceof JSConstantNode.JSConstantIntegerNode && right instanceof JSConstantNode.JSConstantIntegerNode) {
            int rightValue;
            int leftValue = ((JSConstantNode.JSConstantIntegerNode)left).executeInt(null);
            long result = (long)leftValue * (long)(rightValue = ((JSConstantNode.JSConstantIntegerNode)right).executeInt(null));
            if (result == 0L && (leftValue < 0 || rightValue < 0)) {
                return JSConstantNode.createDouble(-0.0);
            }
            if (JSRuntime.longIsRepresentableAsInt(result)) {
                return JSConstantNode.createInt((int)result);
            }
            return JSConstantNode.createDouble(result);
        }
        return JSMultiplyNodeGen.create(left, right);
    }

    public static JSMultiplyNode create() {
        return (JSMultiplyNode)JSMultiplyNode.create(null, null);
    }

    public abstract Object execute(Object var1, Object var2);

    @Specialization(guards={"b > 0"}, rewriteOn={ArithmeticException.class})
    protected int doIntBLargerZero(int a, int b) {
        return Math.multiplyExact(a, b);
    }

    @Specialization(guards={"a > 0"}, rewriteOn={ArithmeticException.class})
    protected int doIntALargerZero(int a, int b) {
        return Math.multiplyExact(a, b);
    }

    @Specialization(rewriteOn={ArithmeticException.class})
    protected int doInt(int a, int b, @Cached(value="create()") BranchProfile resultZeroBranch) {
        int result = Math.multiplyExact(a, b);
        if (result == 0) {
            resultZeroBranch.enter();
            if (a < 0 || b < 0) {
                throw new ArithmeticException("could be -0");
            }
        }
        return result;
    }

    @Specialization
    protected double doDouble(double a, double b) {
        return a * b;
    }

    @Specialization
    @CompilerDirectives.TruffleBoundary
    protected BigInt doBigInts(BigInt a, BigInt b) {
        try {
            return a.multiply(b);
        }
        catch (ArithmeticException ae) {
            throw Errors.createRangeErrorBigIntMaxSizeExceeded();
        }
    }

    @Specialization(guards={"hasOverloadedOperators(a) || hasOverloadedOperators(b)"})
    protected Object doOverloaded(Object a, Object b, @Cached(value="createNumeric(getOverloadedOperatorName())") JSOverloadedBinaryNode overloadedOperatorNode) {
        return overloadedOperatorNode.execute(a, b);
    }

    protected TruffleString getOverloadedOperatorName() {
        return Strings.SYMBOL_STAR;
    }

    @Specialization(guards={"!hasOverloadedOperators(a)", "!hasOverloadedOperators(b)"}, replaces={"doDouble"})
    protected Object doGeneric(Object a, Object b, @Cached(value="create()") JSMultiplyNode nestedMultiplyNode, @Cached(value="create()") JSToNumericNode toNumeric1Node, @Cached(value="create()") JSToNumericNode toNumeric2Node, @Cached(value="create()") BranchProfile mixedNumericTypes) {
        Object operandA = toNumeric1Node.execute(a);
        Object operandB = toNumeric2Node.execute(b);
        this.ensureBothSameNumericType(operandA, operandB, mixedNumericTypes);
        return nestedMultiplyNode.execute(operandA, operandB);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return JSMultiplyNodeGen.create(JSMultiplyNode.cloneUninitialized(this.getLeft(), materializedTags), JSMultiplyNode.cloneUninitialized(this.getRight(), materializedTags));
    }
}

