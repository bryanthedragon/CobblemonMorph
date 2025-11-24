
package com.oracle.truffle.js.nodes.binary;

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
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.binary.JSCompareNode;
import com.oracle.truffle.js.nodes.binary.JSIdenticalNodeGen;
import com.oracle.truffle.js.nodes.unary.IsIdenticalBooleanNode;
import com.oracle.truffle.js.nodes.unary.IsIdenticalIntegerNode;
import com.oracle.truffle.js.nodes.unary.IsIdenticalStringNode;
import com.oracle.truffle.js.nodes.unary.IsIdenticalUndefinedNode;
import com.oracle.truffle.js.nodes.unary.IsNullNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.io.Serializable;
import java.util.Set;

@NodeInfo(shortName="===")
@ImportStatic(value={JSRuntime.class, JSConfig.class, JSGuards.class})
public abstract class JSIdenticalNode
extends JSCompareNode {
    protected static final int MAX_CLASSES = 3;
    protected static final int STRICT_EQUALITY_COMPARISON = 0;
    protected static final int SAME_VALUE = 1;
    protected static final int SAME_VALUE_ZERO = 2;
    protected final int type;

    protected JSIdenticalNode(JavaScriptNode left, JavaScriptNode right, int type) {
        super(left, right);
        this.type = type;
    }

    public static JSIdenticalNode createStrictEqualityComparison() {
        return JSIdenticalNodeGen.create(null, null, 0);
    }

    public static JSIdenticalNode createSameValue() {
        return JSIdenticalNodeGen.create(null, null, 1);
    }

    public static JSIdenticalNode createSameValue(JavaScriptNode left, JavaScriptNode right) {
        return JSIdenticalNodeGen.create(left, right, 1);
    }

    public static JSIdenticalNode createSameValueZero() {
        return JSIdenticalNodeGen.create(null, null, 2);
    }

    public static JavaScriptNode createUnoptimized(JavaScriptNode left, JavaScriptNode right) {
        return JSIdenticalNodeGen.create(left, right, 0);
    }

    public static JavaScriptNode create(JavaScriptNode left, JavaScriptNode right) {
        if (left instanceof JSConstantNode.JSConstantNullNode) {
            return IsNullNode.create(right, true);
        }
        if (right instanceof JSConstantNode.JSConstantNullNode) {
            return IsNullNode.create(left, false);
        }
        if (left instanceof JSConstantNode.JSConstantStringNode) {
            return IsIdenticalStringNode.create((TruffleString)left.execute(null), right, true);
        }
        if (right instanceof JSConstantNode.JSConstantStringNode) {
            return IsIdenticalStringNode.create((TruffleString)right.execute(null), left, false);
        }
        if (left instanceof JSConstantNode.JSConstantIntegerNode) {
            return IsIdenticalIntegerNode.create((Integer)left.execute(null), right, true);
        }
        if (right instanceof JSConstantNode.JSConstantIntegerNode) {
            return IsIdenticalIntegerNode.create((Integer)right.execute(null), left, false);
        }
        if (left instanceof JSConstantNode.JSConstantBooleanNode) {
            return IsIdenticalBooleanNode.create((Boolean)left.execute(null), right, true);
        }
        if (right instanceof JSConstantNode.JSConstantBooleanNode) {
            return IsIdenticalBooleanNode.create((Boolean)right.execute(null), left, false);
        }
        if (left instanceof JSConstantNode.JSConstantUndefinedNode) {
            return IsIdenticalUndefinedNode.create(right, true);
        }
        if (right instanceof JSConstantNode.JSConstantUndefinedNode) {
            return IsIdenticalUndefinedNode.create(left, false);
        }
        return JSIdenticalNodeGen.create(left, right, 0);
    }

    public abstract boolean executeBoolean(Object var1, Object var2);

    @Specialization
    protected static boolean doInt(int a, int b) {
        return a == b;
    }

    @Specialization
    protected boolean doDouble(double a, double b) {
        if (this.type == 0) {
            return a == b;
        }
        if (this.type == 1) {
            if (Double.isNaN(a)) {
                return Double.isNaN(b);
            }
            if (a == 0.0 && b == 0.0) {
                return JSRuntime.isNegativeZero(a) == JSRuntime.isNegativeZero(b);
            }
            return a == b;
        }
        assert (this.type == 2);
        if (Double.isNaN(a)) {
            return Double.isNaN(b);
        }
        return a == b;
    }

    @Specialization
    protected static boolean doBoolean(boolean a, boolean b) {
        return a == b;
    }

    @Specialization
    protected static boolean doBigInt(BigInt a, BigInt b) {
        return a.compareTo(b) == 0;
    }

    @Specialization
    protected static boolean doBigIntDouble(BigInt a, double b) {
        return false;
    }

    @Specialization
    protected static boolean doDoubleBigInt(double a, BigInt b) {
        return JSIdenticalNode.doBigIntDouble(b, a);
    }

    @Specialization(guards={"isUndefined(a)"})
    protected static boolean doUndefinedA(Object a, Object b) {
        return a == b;
    }

    @Specialization(guards={"isUndefined(b)"})
    protected static boolean doUndefinedB(Object a, Object b) {
        return a == b;
    }

    @Specialization
    protected static boolean doJSObjectA(JSObject a, Object b) {
        return a == b;
    }

    @Specialization
    protected static boolean doJSObjectB(Object a, JSObject b) {
        return a == b;
    }

    @Specialization(guards={"isJSNull(a)", "isJSNull(b)"})
    protected static boolean doNullNull(Object a, Object b) {
        return true;
    }

    @Specialization(guards={"isJSNull(a)", "isUndefined(b)"})
    protected static boolean doNullUndefined(Object a, Object b) {
        return false;
    }

    @Specialization(guards={"isUndefined(a)", "isJSNull(b)"})
    protected static boolean doUndefinedNull(Object a, Object b) {
        return false;
    }

    @Specialization(guards={"isJSNull(a)", "!isNullOrUndefined(b)"})
    protected static boolean doNullA(Object a, Object b, @Cached.Shared(value="isNullInterop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary nullInterop) {
        assert (b != Undefined.instance);
        return nullInterop.isNull(b);
    }

    @Specialization(guards={"!isNullOrUndefined(a)", "isJSNull(b)"})
    protected static boolean doNullB(Object a, Object b, @Cached.Shared(value="isNullInterop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary nullInterop) {
        assert (a != Undefined.instance);
        return nullInterop.isNull(a);
    }

    @Specialization(guards={"isReferenceEquals(a, b)"})
    protected static boolean doTruffleStringIdentity(TruffleString a, TruffleString b) {
        return true;
    }

    @Specialization(replaces={"doTruffleStringIdentity"})
    protected static boolean doTruffleString(TruffleString a, TruffleString b, @Cached TruffleString.EqualNode equalsNode) {
        return equalsNode.execute(a, b, TruffleString.Encoding.UTF_16);
    }

    @Specialization
    protected static boolean doSymbol(Symbol a, Symbol b) {
        return a == b;
    }

    @Specialization(guards={"isBoolean(a) != isBoolean(b)"})
    protected static boolean doBooleanNotBoolean(Object a, Object b) {
        assert (a != null && b != null);
        return false;
    }

    @Specialization(guards={"isSymbol(a) != isSymbol(b)"})
    protected static boolean doSymbolNotSymbol(Object a, Object b) {
        assert (a != null && b != null);
        return false;
    }

    protected static boolean isJavaNumberType(Class<?> clazz) {
        return Number.class.isAssignableFrom(clazz);
    }

    @Specialization(guards={"a.getClass() == cachedClassA", "b.getClass() == cachedClassB", "isJavaNumberType(cachedClassA) != isJavaNumberType(cachedClassB)"}, limit="MAX_CLASSES")
    protected static boolean doNumberNotNumberCached(Object a, Object b, @Cached(value="a.getClass()") Class<?> cachedClassA, @Cached(value="b.getClass()") Class<?> cachedClassB) {
        return false;
    }

    @Specialization(guards={"isJavaNumber(a) != isJavaNumber(b)"}, replaces={"doNumberNotNumberCached"})
    protected static boolean doNumberNotNumber(Object a, Object b) {
        assert (a != null && b != null);
        return false;
    }

    @Specialization(guards={"isString(a) != isString(b)"})
    protected static boolean doStringNotString(Object a, Object b) {
        assert (a != null && b != null);
        return false;
    }

    @Specialization(guards={"isJavaNumber(a)", "isJavaNumber(b)"})
    protected boolean doNumber(Number a, Number b) {
        return this.doDouble(JSRuntime.doubleValue(a), JSRuntime.doubleValue(b));
    }

    @Specialization(guards={"isForeignObject(a)", "isForeignObject(b)"}, limit="InteropLibraryLimit")
    protected static boolean doForeignObject(Object a, Object b, @CachedLibrary(value="a") InteropLibrary aInterop, @CachedLibrary(value="b") InteropLibrary bInterop) {
        return aInterop.isIdentical(a, b, bInterop) || aInterop.isNull(a) && bInterop.isNull(b);
    }

    @Fallback
    protected static boolean doFallback(Object a, Object b) {
        assert (!JSRuntime.identical(a, b)) : a + " (" + (Serializable)(a == null ? "null" : a.getClass()) + "), " + b + " (" + (Serializable)(b == null ? "null" : b.getClass()) + ")";
        return false;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return JSIdenticalNodeGen.create(JSIdenticalNode.cloneUninitialized(this.getLeft(), materializedTags), JSIdenticalNode.cloneUninitialized(this.getRight(), materializedTags), this.type);
    }
}

