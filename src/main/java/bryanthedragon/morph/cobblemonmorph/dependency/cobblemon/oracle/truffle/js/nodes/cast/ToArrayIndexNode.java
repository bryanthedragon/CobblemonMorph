
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.dsl.Bind;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSToPropertyKeyNode;
import com.oracle.truffle.js.nodes.cast.ToArrayIndexNodeGen;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;

@ImportStatic(value={JSConfig.class, JSRuntime.class})
public abstract class ToArrayIndexNode
extends JavaScriptBaseNode {
    protected final boolean convertToPropertyKey;
    protected final boolean convertStringToIndex;

    public abstract Object execute(Object var1);

    public abstract long executeLong(Object var1) throws UnexpectedResultException;

    public final boolean isResultArrayIndex(Object result) {
        return result instanceof Long;
    }

    protected ToArrayIndexNode(boolean convertToPropertyKey, boolean convertStringToIndex) {
        this.convertToPropertyKey = convertToPropertyKey;
        this.convertStringToIndex = convertStringToIndex;
    }

    public static ToArrayIndexNode create() {
        return ToArrayIndexNodeGen.create(true, true);
    }

    public static ToArrayIndexNode createNoToPropertyKey() {
        return ToArrayIndexNodeGen.create(false, true);
    }

    public static ToArrayIndexNode createNoStringToIndex() {
        return ToArrayIndexNodeGen.create(true, false);
    }

    @Specialization(guards={"isIntArrayIndex(value)"})
    protected static long doInteger(int value2) {
        return value2;
    }

    @Specialization(guards={"isLongArrayIndex(value)"})
    protected static long doLong(long value2) {
        return JSRuntime.castArrayIndex(value2);
    }

    protected static boolean doubleIsIntIndex(double d) {
        return JSRuntime.doubleIsRepresentableAsInt(d) && d >= 0.0;
    }

    @Specialization(guards={"doubleIsIntIndex(value)"})
    protected static long doDoubleAsIntIndex(double value2) {
        return (long)value2;
    }

    protected static boolean doubleIsUintIndex(double d) {
        return JSRuntime.doubleIsRepresentableAsUnsignedInt(d, true) && d >= 0.0 && d < 4.294967295E9;
    }

    @Specialization(guards={"doubleIsUintIndex(value)"}, replaces={"doDoubleAsIntIndex"})
    protected static long doDoubleAsUintIndex(double value2) {
        return JSRuntime.castArrayIndex(value2);
    }

    @Specialization
    protected static Symbol doSymbol(Symbol value2) {
        return value2;
    }

    @Specialization(guards={"isBigIntArrayIndex(value)"})
    protected static long doBigInt(BigInt value2) {
        return value2.longValue();
    }

    @Specialization(guards={"convertStringToIndex", "arrayIndexLengthInRange(index)"})
    protected static Object convertFromString(TruffleString index, @Cached ConditionProfile startsWithDigitBranch, @Cached BranchProfile isArrayIndexBranch, @Cached TruffleString.ReadCharUTF16Node stringReadNode) {
        long longValue;
        if (startsWithDigitBranch.profile(JSRuntime.isAsciiDigit(Strings.charAt(stringReadNode, index, 0))) && JSRuntime.isArrayIndex(longValue = JSRuntime.parseArrayIndexRaw(index, stringReadNode))) {
            isArrayIndexBranch.enter();
            return JSRuntime.castArrayIndex(longValue);
        }
        return index;
    }

    @Specialization(guards={"!convertStringToIndex || !arrayIndexLengthInRange(index)"})
    protected static TruffleString convertFromStringNotInRange(TruffleString index) {
        return index;
    }

    protected static boolean notArrayIndex(Object o) {
        return !(o instanceof Integer && JSGuards.isIntArrayIndex((Integer)o) || o instanceof Double && ToArrayIndexNode.doubleIsUintIndex((Double)o) || o instanceof Long && JSGuards.isLongArrayIndex((Long)o) || o instanceof BigInt && JSGuards.isBigIntArrayIndex((BigInt)o) || o instanceof TruffleString || o instanceof Symbol);
    }

    @Specialization(guards={"notArrayIndex(value)", "index >= 0"}, limit="InteropLibraryLimit")
    protected static long doInteropArrayIndex(Object value2, @CachedLibrary(value="value") InteropLibrary interop, @Bind(value="toArrayIndex(value, interop)") long index) {
        return index;
    }

    @Specialization(guards={"notArrayIndex(value)", "toArrayIndex(value, interop) < 0"}, limit="InteropLibraryLimit")
    protected final Object doNonArrayIndex(Object value2, @CachedLibrary(value="value") InteropLibrary interop, @Cached JSToPropertyKeyNode toPropertyKey, @Cached(value="createNoToPropertyKey()") ToArrayIndexNode recursive) {
        CompilerAsserts.partialEvaluationConstant(this.convertToPropertyKey);
        if (this.convertToPropertyKey) {
            Object propertyKey = toPropertyKey.execute(value2);
            if (this.convertStringToIndex) {
                return recursive.execute(propertyKey);
            }
            return propertyKey;
        }
        return value2;
    }

    static long toArrayIndex(Object value2, InteropLibrary interop) {
        if (interop.fitsInLong(value2)) {
            try {
                long index = interop.asLong(value2);
                if (JSRuntime.isArrayIndex(index)) {
                    return JSRuntime.castArrayIndex(index);
                }
            }
            catch (UnsupportedMessageException unsupportedMessageException) {
                // empty catch block
            }
        }
        return -1L;
    }
}

