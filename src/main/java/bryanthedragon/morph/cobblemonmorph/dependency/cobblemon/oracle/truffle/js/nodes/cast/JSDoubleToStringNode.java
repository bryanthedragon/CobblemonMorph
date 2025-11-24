
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSDoubleToStringNodeGen;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;

@GenerateUncached
public abstract class JSDoubleToStringNode
extends JavaScriptBaseNode {
    public static JSDoubleToStringNode create() {
        return JSDoubleToStringNodeGen.create();
    }

    public abstract TruffleString executeString(Object var1);

    @Specialization
    protected static TruffleString doInt(int i, @Cached @Cached.Shared(value="fromLongNode") TruffleString.FromLongNode fromLongNode) {
        return Strings.fromLong(fromLongNode, i);
    }

    @Specialization
    protected static TruffleString doLong(long i, @Cached @Cached.Shared(value="fromLongNode") TruffleString.FromLongNode fromLongNode) {
        return Strings.fromLong(fromLongNode, i);
    }

    @Specialization
    protected static TruffleString doDouble(double d, @Cached @Cached.Shared(value="fromLongNode") TruffleString.FromLongNode fromLongNode, @Cached ConditionProfile isInt, @Cached ConditionProfile isNaN, @Cached ConditionProfile isPositiveInfinity, @Cached ConditionProfile isNegativeInfinity, @Cached ConditionProfile isZero, @Cached TruffleString.FromJavaStringNode fromJavaStringNode) {
        if (isZero.profile(d == 0.0)) {
            return Strings.ZERO;
        }
        if (isInt.profile(JSRuntime.doubleIsRepresentableAsInt(d, true))) {
            return JSDoubleToStringNode.doInt((int)d, fromLongNode);
        }
        if (isNaN.profile(Double.isNaN(d))) {
            return Strings.NAN;
        }
        if (isPositiveInfinity.profile(d == Double.POSITIVE_INFINITY)) {
            return Strings.INFINITY;
        }
        if (isNegativeInfinity.profile(d == Double.NEGATIVE_INFINITY)) {
            return Strings.NEGATIVE_INFINITY;
        }
        return Strings.fromJavaString(fromJavaStringNode, JSRuntime.formatDtoA(d));
    }
}

