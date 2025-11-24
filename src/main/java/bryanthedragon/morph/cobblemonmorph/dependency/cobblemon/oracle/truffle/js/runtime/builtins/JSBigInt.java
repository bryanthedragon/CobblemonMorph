
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.BigIntFunctionBuiltins;
import com.oracle.truffle.js.builtins.BigIntPrototypeBuiltins;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.builtins.JSBigIntObject;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSPrimitive;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;

public final class JSBigInt
extends JSPrimitive
implements JSConstructorFactory.WithFunctions {
    public static final TruffleString TYPE_NAME = Strings.constant("bigint");
    public static final TruffleString CLASS_NAME = Strings.constant("BigInt");
    public static final TruffleString PROTOTYPE_NAME = Strings.constant("BigInt.prototype");
    public static final JSBigInt INSTANCE = new JSBigInt();

    private JSBigInt() {
    }

    public static JSBigIntObject create(JSContext context, JSRealm realm, BigInt value2) {
        JSBigIntObject obj = JSBigIntObject.create(realm, context.getBigIntFactory(), value2);
        return context.trackAllocation(obj);
    }

    private static BigInt getBigIntegerField(JSDynamicObject obj) {
        assert (JSBigInt.isJSBigInt(obj));
        return ((JSBigIntObject)obj).getBigIntValue();
    }

    public static BigInt valueOf(JSDynamicObject obj) {
        return JSBigInt.getBigIntegerField(obj);
    }

    @Override
    public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject ctor) {
        JSContext context = realm.getContext();
        JSObject bigIntPrototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
        JSObjectUtil.putConstructorProperty(context, bigIntPrototype, ctor);
        JSObjectUtil.putFunctionsFromContainer(realm, bigIntPrototype, BigIntPrototypeBuiltins.BUILTINS);
        JSObjectUtil.putToStringTag(bigIntPrototype, CLASS_NAME);
        return bigIntPrototype;
    }

    @Override
    public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
        Shape initialShape = JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
        return initialShape;
    }

    public static JSConstructor createConstructor(JSRealm realm) {
        return INSTANCE.createConstructorAndPrototype(realm, BigIntFunctionBuiltins.BUILTINS);
    }

    public static boolean isJSBigInt(Object obj) {
        return obj instanceof JSBigIntObject;
    }

    @Override
    public TruffleString getClassName() {
        return CLASS_NAME;
    }

    @Override
    public TruffleString getClassName(JSDynamicObject object) {
        return this.getClassName();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
        if (JavaScriptLanguage.get(null).getJSContext().isOptionNashornCompatibilityMode()) {
            return super.toDisplayStringImpl(obj, allowSideEffects, format, depth);
        }
        BigInt primitiveValue = JSBigInt.valueOf(obj);
        return JSRuntime.objectToDisplayString(obj, allowSideEffects, format, depth, this.getBuiltinToStringTag(obj), new TruffleString[]{Strings.PRIMITIVE_VALUE}, new Object[]{primitiveValue});
    }

    @Override
    public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
        return realm.getBigIntPrototype();
    }
}

