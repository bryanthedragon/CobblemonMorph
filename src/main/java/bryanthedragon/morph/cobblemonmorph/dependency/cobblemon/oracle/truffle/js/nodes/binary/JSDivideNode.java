
package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.binary.JSBinaryNode;
import com.oracle.truffle.js.nodes.binary.JSDivideNodeGen;
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNode;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.Strings;
import java.util.Set;

@NodeInfo(shortName="/")
public abstract class JSDivideNode
extends JSBinaryNode {
    protected JSDivideNode(JavaScriptNode left, JavaScriptNode right) {
        super(left, right);
    }

    public static JSDivideNode create(JavaScriptNode left, JavaScriptNode right) {
        return JSDivideNodeGen.create(left, right);
    }

    public static JSDivideNode create() {
        return JSDivideNode.create(null, null);
    }

    public abstract Object execute(Object var1, Object var2);

    protected static boolean isCornercase(int a, int b) {
        return a != 0 && (b != -1 || a != Integer.MIN_VALUE);
    }

    @Specialization(rewriteOn={ArithmeticException.class}, guards={"b > 0"})
    protected int doInt1(int a, int b) {
        if (a % b == 0) {
            return a / b;
        }
        throw new ArithmeticException();
    }

    @Specialization(rewriteOn={ArithmeticException.class}, guards={"a > 0"})
    protected int doInt2(int a, int b) {
        return this.doInt1(a, b);
    }

    @Specialization(rewriteOn={ArithmeticException.class}, guards={"isCornercase(a, b)"})
    protected int doInt3(int a, int b) {
        return this.doInt1(a, b);
    }

    @Specialization(replaces={"doInt1", "doInt2", "doInt3"})
    protected double doDouble(double a, double b) {
        return a / b;
    }

    @Specialization(guards={"isBigIntZero(b)"})
    protected BigInt doBigIntZeroDivision(BigInt a, BigInt b) {
        throw Errors.createRangeError("Division by zero");
    }

    @Specialization(guards={"!isBigIntZero(b)"})
    protected BigInt doBigInt(BigInt a, BigInt b) {
        return a.divide(b);
    }

    @Specialization(guards={"hasOverloadedOperators(a) || hasOverloadedOperators(b)"})
    protected Object doOverloaded(Object a, Object b, @Cached(value="createNumeric(getOverloadedOperatorName())") JSOverloadedBinaryNode overloadedOperatorNode) {
        return overloadedOperatorNode.execute(a, b);
    }

    protected TruffleString getOverloadedOperatorName() {
        return Strings.SLASH;
    }

    @Specialization(guards={"!hasOverloadedOperators(a)", "!hasOverloadedOperators(b)"}, replaces={"doDouble"})
    protected Object doGeneric(Object a, Object b, @Cached(value="create()") JSDivideNode nestedDivideNode, @Cached(value="create()") JSToNumericNode toNumeric1Node, @Cached(value="create()") JSToNumericNode toNumeric2Node, @Cached(value="create()") BranchProfile mixedNumericTypes) {
        Object numericA = toNumeric1Node.execute(a);
        Object numericB = toNumeric2Node.execute(b);
        this.ensureBothSameNumericType(numericA, numericB, mixedNumericTypes);
        return nestedDivideNode.execute(numericA, numericB);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return JSDivideNodeGen.create(JSDivideNode.cloneUninitialized(this.getLeft(), materializedTags), JSDivideNode.cloneUninitialized(this.getRight(), materializedTags));
    }
}

