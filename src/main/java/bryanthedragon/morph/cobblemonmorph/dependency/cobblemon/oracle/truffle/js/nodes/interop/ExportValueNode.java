
package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.interop.ExportValueNodeGen;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.interop.InteropAsyncFunction;
import com.oracle.truffle.js.runtime.interop.InteropBoundFunction;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

@ImportStatic(value={JSGuards.class, JSFunction.class})
@GenerateUncached
public abstract class ExportValueNode
extends JavaScriptBaseNode {
    ExportValueNode() {
    }

    public final Object execute(Object value2) {
        return this.execute(value2, Undefined.instance, false);
    }

    public abstract Object execute(Object var1, Object var2, boolean var3);

    protected final boolean isInteropCompletePromises() {
        return this.getLanguage().getJSContext().getContextOptions().isMLEMode();
    }

    @Specialization(guards={"!bindFunctions", "!isInteropCompletePromises() || !isAsyncFunction(function)"})
    protected static JSDynamicObject doFunctionNoBind(JSFunctionObject function, Object thiz, boolean bindFunctions) {
        return function;
    }

    @Specialization(guards={"bindFunctions", "isUndefined(thiz)", "!isInteropCompletePromises() || !isAsyncFunction(function)"})
    protected static JSDynamicObject doFunctionUndefinedThis(JSFunctionObject function, Object thiz, boolean bindFunctions) {
        return function;
    }

    @Specialization(guards={"bindFunctions", "!isUndefined(thiz)", "!isBoundJSFunction(function)", "!isInteropCompletePromises() || !isAsyncFunction(function)"})
    protected static TruffleObject doBindUnboundFunction(JSFunctionObject function, Object thiz, boolean bindFunctions) {
        return new InteropBoundFunction(function, thiz);
    }

    @Specialization(guards={"bindFunctions", "isBoundJSFunction(function)", "!isInteropCompletePromises() || !isAsyncFunction(function)"})
    protected static JSDynamicObject doBoundFunction(JSFunctionObject function, Object thiz, boolean bindFunctions) {
        return function;
    }

    @Specialization(guards={"isInteropCompletePromises()", "isAsyncFunction(function)"})
    protected static TruffleObject doAsyncFunction(JSFunctionObject function, Object thiz, boolean bindFunctions) {
        return new InteropAsyncFunction(function);
    }

    @Specialization
    protected static double doSafeInteger(SafeInteger value2, Object thiz, boolean bindFunctions) {
        return value2.doubleValue();
    }

    @Specialization(guards={"!isJSFunction(value)"})
    protected static JSDynamicObject doObject(JSDynamicObject value2, Object thiz, boolean bindFunctions) {
        return value2;
    }

    @Specialization
    protected static int doInt(int value2, Object thiz, boolean bindFunctions) {
        return value2;
    }

    @Specialization
    protected static long doLong(long value2, Object thiz, boolean bindFunctions) {
        return value2;
    }

    @Specialization
    protected static float doFloat(float value2, Object thiz, boolean bindFunctions) {
        return value2;
    }

    @Specialization
    protected static double doDouble(double value2, Object thiz, boolean bindFunctions) {
        return value2;
    }

    @Specialization
    protected static boolean doBoolean(boolean value2, Object thiz, boolean bindFunctions) {
        return value2;
    }

    @Specialization
    protected static BigInt doBigInt(BigInt value2, Object thiz, boolean bindFunctions) {
        return value2;
    }

    @Specialization
    protected static TruffleString doString(TruffleString value2, Object thiz, boolean bindFunctions) {
        return value2;
    }

    @Specialization(guards={"!isJSFunction(value)"}, replaces={"doObject"})
    protected static TruffleObject doTruffleObject(TruffleObject value2, Object thiz, boolean bindFunctions) {
        return value2;
    }

    @CompilerDirectives.TruffleBoundary
    @Specialization(guards={"!isTruffleObject(thiz)", "!isString(thiz)", "!isBoolean(thiz)", "!isNumberDouble(thiz)", "!isNumberLong(thiz)", "!isNumberInteger(thiz)"})
    protected static Object doOther(Object value2, Object thiz, boolean bindFunctions) {
        throw Errors.createTypeErrorFormat("Cannot convert to TruffleObject: %s", value2 == null ? null : value2.getClass().getSimpleName());
    }

    public static ExportValueNode create() {
        return ExportValueNodeGen.create();
    }

    public static ExportValueNode getUncached() {
        return ExportValueNodeGen.getUncached();
    }
}

