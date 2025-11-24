
package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.dsl.Bind;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.IsPrimitiveNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.binary.JSCompareNode;
import com.oracle.truffle.js.nodes.binary.JSEqualNodeGen;
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.unary.JSIsNullOrUndefinedNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.io.Serializable;
import java.util.Set;

@NodeInfo(shortName="==")
@ImportStatic(value={JSRuntime.class, JSInteropUtil.class, JSConfig.class})
public abstract class JSEqualNode
extends JSCompareNode {
    protected JSEqualNode(JavaScriptNode left, JavaScriptNode right) {
        super(left, right);
    }

    public static JSEqualNode create() {
        return JSEqualNodeGen.create(null, null);
    }

    public static JavaScriptNode create(JavaScriptNode left, JavaScriptNode right) {
        boolean rightIs;
        boolean leftIs = left instanceof JSConstantNode.JSConstantUndefinedNode || left instanceof JSConstantNode.JSConstantNullNode;
        boolean bl = rightIs = right instanceof JSConstantNode.JSConstantUndefinedNode || right instanceof JSConstantNode.JSConstantNullNode;
        if (leftIs) {
            if (rightIs) {
                return JSConstantNode.createBoolean(true);
            }
            return JSIsNullOrUndefinedNode.createFromEquals(left, right);
        }
        if (rightIs) {
            return JSIsNullOrUndefinedNode.createFromEquals(left, right);
        }
        return JSEqualNodeGen.create(left, right);
    }

    public static JavaScriptNode createUnoptimized(JavaScriptNode left, JavaScriptNode right) {
        return JSEqualNodeGen.create(left, right);
    }

    public abstract boolean executeBoolean(Object var1, Object var2);

    @Specialization
    protected static boolean doInt(int a, int b) {
        return a == b;
    }

    @Specialization
    protected static boolean doIntBoolean(int a, boolean b) {
        return a == (b ? 1 : 0);
    }

    @Specialization
    protected static boolean doDouble(double a, double b) {
        return a == b;
    }

    @Specialization
    protected static boolean doBigInt(BigInt a, BigInt b) {
        return a.compareTo(b) == 0;
    }

    @Specialization
    protected boolean doDoubleString(double a, TruffleString b) {
        return JSEqualNode.doDouble(a, this.stringToDouble(b));
    }

    @Specialization
    protected static boolean doDoubleBoolean(double a, boolean b) {
        return JSEqualNode.doDouble(a, b ? 1.0 : 0.0);
    }

    @Specialization
    protected static boolean doBoolean(boolean a, boolean b) {
        return a == b;
    }

    @Specialization
    protected static boolean doBooleanInt(boolean a, int b) {
        return (a ? 1 : 0) == b;
    }

    @Specialization
    protected static boolean doBooleanDouble(boolean a, double b) {
        return JSEqualNode.doDouble(a ? 1.0 : 0.0, b);
    }

    @Specialization
    protected boolean doBooleanString(boolean a, TruffleString b) {
        return JSEqualNode.doBooleanDouble(a, this.stringToDouble(b));
    }

    @Specialization(guards={"isReferenceEquals(a, b)"})
    protected static boolean doStringIdentity(TruffleString a, TruffleString b) {
        return true;
    }

    @Specialization(replaces={"doStringIdentity"})
    protected static boolean doString(TruffleString a, TruffleString b, @Cached TruffleString.EqualNode equalsNode) {
        return Strings.equals(equalsNode, a, b);
    }

    @Specialization
    protected boolean doStringDouble(TruffleString a, double b) {
        return JSEqualNode.doDouble(this.stringToDouble(a), b);
    }

    @Specialization
    protected boolean doStringBoolean(TruffleString a, boolean b) {
        return JSEqualNode.doDoubleBoolean(this.stringToDouble(a), b);
    }

    @Specialization
    protected boolean doStringBigInt(TruffleString a, BigInt b) {
        BigInt aBigInt = JSRuntime.stringToBigInt(a);
        return aBigInt != null ? aBigInt.compareTo(b) == 0 : false;
    }

    @Specialization
    protected boolean doBigIntString(BigInt a, TruffleString b) {
        return this.doStringBigInt(b, a);
    }

    @Specialization
    protected boolean doBooleanBigInt(boolean a, BigInt b) {
        return JSEqualNode.doBigInt(a ? BigInt.ONE : BigInt.ZERO, b);
    }

    @Specialization
    protected boolean doBigIntBoolean(BigInt a, boolean b) {
        return this.doBooleanBigInt(b, a);
    }

    @Specialization(guards={"isNullOrUndefined(a)", "isNullOrUndefined(b)"})
    protected static boolean doBothNullOrUndefined(Object a, Object b) {
        return true;
    }

    @Specialization(guards={"isNullOrUndefined(a)"})
    protected static boolean doLeftNullOrUndefined(Object a, Object b, @Cached.Shared(value="bInterop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary bInterop) {
        return JSEqualNode.isNullish(b, bInterop);
    }

    @Specialization(guards={"isNullOrUndefined(b)"})
    protected static boolean doRightNullOrUndefined(Object a, Object b, @Cached.Shared(value="aInterop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary aInterop) {
        return JSEqualNode.isNullish(a, aInterop);
    }

    @Specialization(guards={"hasOverloadedOperators(a) || hasOverloadedOperators(b)"})
    protected boolean doOverloaded(Object a, Object b, @Cached(value="createHintDefault(getOverloadedOperatorName())") JSOverloadedBinaryNode overloadedOperatorNode, @Cached(value="create()") JSToBooleanNode toBooleanNode) {
        if (a == b) {
            return true;
        }
        return toBooleanNode.executeBoolean(overloadedOperatorNode.execute(a, b));
    }

    protected static TruffleString getOverloadedOperatorName() {
        return Strings.SYMBOL_EQUALS_EQUALS;
    }

    @Specialization(guards={"!hasOverloadedOperators(a)", "!hasOverloadedOperators(b)"})
    protected static boolean doJSObject(JSObject a, JSDynamicObject b) {
        return a == b;
    }

    @Specialization(guards={"!hasOverloadedOperators(a)", "!hasOverloadedOperators(b)"})
    protected static boolean doJSObject(JSDynamicObject a, JSObject b) {
        return a == b;
    }

    @Specialization(guards={"!hasOverloadedOperators(a)", "isPrimitiveNode.executeBoolean(b)"}, limit="1")
    protected boolean doJSObjectVsPrimitive(JSObject a, Object b, @Cached.Shared(value="bInterop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary bInterop, @Cached.Shared(value="toPrimitive") @Cached(value="createHintDefault()") JSToPrimitiveNode toPrimitiveNode, @Cached.Shared(value="isPrimitive") @Cached IsPrimitiveNode isPrimitiveNode, @Cached.Shared(value="equal") @Cached JSEqualNode nestedEqualNode) {
        if (JSEqualNode.isNullish(b, bInterop)) {
            return false;
        }
        return nestedEqualNode.executeBoolean(toPrimitiveNode.execute(a), b);
    }

    @Specialization(guards={"!hasOverloadedOperators(b)", "isPrimitiveNode.executeBoolean(a)"}, limit="1")
    protected boolean doJSObjectVsPrimitive(Object a, JSObject b, @Cached.Shared(value="aInterop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary aInterop, @Cached.Shared(value="toPrimitive") @Cached(value="createHintDefault()") JSToPrimitiveNode toPrimitiveNode, @Cached.Shared(value="isPrimitive") @Cached IsPrimitiveNode isPrimitiveNode, @Cached.Shared(value="equal") @Cached JSEqualNode nestedEqualNode) {
        if (JSEqualNode.isNullish(a, aInterop)) {
            return false;
        }
        return nestedEqualNode.executeBoolean(a, toPrimitiveNode.execute(b));
    }

    @Specialization
    protected boolean doBigIntAndInt(BigInt a, int b) {
        return a.compareTo(BigInt.valueOf(b)) == 0;
    }

    @Specialization
    protected boolean doBigIntAndNumber(BigInt a, double b) {
        if (Double.isNaN(b)) {
            return false;
        }
        return a.compareValueTo(b) == 0;
    }

    @Specialization
    protected boolean doIntAndBigInt(int a, BigInt b) {
        return b.compareTo(BigInt.valueOf(a)) == 0;
    }

    @Specialization
    protected boolean doNumberAndBigInt(double a, BigInt b) {
        return this.doBigIntAndNumber(b, a);
    }

    @Specialization
    protected static boolean doSymbol(Symbol a, Symbol b) {
        return a == b;
    }

    @Specialization(guards={"!isSymbol(b)", "!isObject(b)"})
    protected static boolean doSymbolNotSymbol(Symbol a, Object b) {
        return false;
    }

    @Specialization(guards={"!isSymbol(a)", "!isObject(a)"})
    protected static boolean doSymbolNotSymbol(Object a, Symbol b) {
        return false;
    }

    @Specialization(guards={"isAForeign || isBForeign"})
    protected boolean doForeign(Object a, Object b, @Bind(value="isForeignObject(a)") boolean isAForeign, @Bind(value="isForeignObject(b)") boolean isBForeign, @Cached.Shared(value="aInterop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary aInterop, @Cached.Shared(value="bInterop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary bInterop, @Cached.Shared(value="toPrimitive") @Cached(value="createHintDefault()") JSToPrimitiveNode toPrimitiveNode, @Cached.Shared(value="isPrimitive") @Cached IsPrimitiveNode isPrimitiveNode, @Cached.Shared(value="equal") @Cached JSEqualNode nestedEqualNode) {
        Object primRight;
        assert (a != null && b != null);
        boolean isAPrimitive = isPrimitiveNode.executeBoolean(a);
        boolean isBPrimitive = isPrimitiveNode.executeBoolean(b);
        if (!isAPrimitive && !isBPrimitive) {
            return aInterop.isIdentical(a, b, bInterop);
        }
        if (JSEqualNode.isNullish(a, aInterop)) {
            return JSEqualNode.isNullish(b, bInterop);
        }
        if (JSEqualNode.isNullish(b, bInterop)) {
            assert (!JSEqualNode.isNullish(a, bInterop));
            return false;
        }
        Object primLeft = !isAPrimitive || isAForeign ? toPrimitiveNode.execute(a) : a;
        Object object = primRight = !isBPrimitive || isBForeign ? toPrimitiveNode.execute(b) : b;
        assert (!JSGuards.isForeignObject(primLeft) && !JSGuards.isForeignObject(primRight));
        return nestedEqualNode.executeBoolean(primLeft, primRight);
    }

    @Specialization(guards={"isJavaNumber(a)", "isJavaNumber(b)"})
    protected static boolean doNumber(Number a, Number b) {
        return JSEqualNode.doDouble(JSRuntime.doubleValue(a), JSRuntime.doubleValue(b));
    }

    @Specialization(guards={"isJavaNumber(a)"})
    protected boolean doNumberString(Object a, TruffleString b) {
        return this.doDoubleString(JSRuntime.doubleValue((Number)a), b);
    }

    @Specialization(guards={"isJavaNumber(b)"})
    protected boolean doStringNumber(TruffleString a, Object b) {
        return this.doStringDouble(a, JSRuntime.doubleValue((Number)b));
    }

    @Fallback
    protected static boolean doFallback(Object a, Object b) {
        assert (!JSRuntime.equal(a, b)) : a + " (" + (Serializable)(a == null ? "null" : a.getClass()) + "), " + b + " (" + (Serializable)(b == null ? "null" : b.getClass()) + ")";
        return false;
    }

    protected static boolean isNullish(Object value2, InteropLibrary interop) {
        return JSRuntime.isNullOrUndefined(value2) || interop.isNull(value2);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return JSEqualNodeGen.create(JSEqualNode.cloneUninitialized(this.getLeft(), materializedTags), JSEqualNode.cloneUninitialized(this.getRight(), materializedTags));
    }
}

