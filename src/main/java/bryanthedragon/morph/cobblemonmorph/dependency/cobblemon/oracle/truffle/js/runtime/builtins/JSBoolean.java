
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.BooleanPrototypeBuiltins;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.builtins.JSBooleanObject;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSPrimitive;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSShape;

public final class JSBoolean
extends JSPrimitive
implements JSConstructorFactory.Default {
    public static final TruffleString TYPE_NAME = Strings.LC_BOOLEAN;
    public static final TruffleString CLASS_NAME = Strings.UC_BOOLEAN;
    public static final TruffleString PROTOTYPE_NAME = Strings.BOOLEAN_PROTOTYPE;
    public static final TruffleString TRUE_NAME = Strings.TRUE;
    public static final TruffleString FALSE_NAME = Strings.FALSE;
    public static final JSBoolean INSTANCE = new JSBoolean();

    private JSBoolean() {
    }

    public static JSBooleanObject create(JSContext context, JSRealm realm, boolean value2) {
        JSBooleanObject obj = JSBooleanObject.create(realm, context.getBooleanFactory(), value2);
        return context.trackAllocation(obj);
    }

    @Override
    public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject ctor) {
        JSContext ctx = realm.getContext();
        Shape protoShape = JSShape.createPrototypeShape(realm.getContext(), INSTANCE, realm.getObjectPrototype());
        JSBooleanObject booleanPrototype = JSBooleanObject.create(protoShape, false);
        JSObjectUtil.setOrVerifyPrototype(ctx, booleanPrototype, realm.getObjectPrototype());
        JSObjectUtil.putConstructorProperty(ctx, booleanPrototype, ctor);
        JSObjectUtil.putFunctionsFromContainer(realm, booleanPrototype, BooleanPrototypeBuiltins.BUILTINS);
        return booleanPrototype;
    }

    @Override
    public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
        Shape initialShape = JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
        return initialShape;
    }

    public static JSConstructor createConstructor(JSRealm realm) {
        return INSTANCE.createConstructorAndPrototype(realm);
    }

    public static boolean valueOf(JSDynamicObject obj) {
        assert (JSBoolean.isJSBoolean(obj));
        return ((JSBooleanObject)obj).getBooleanValue();
    }

    public static boolean isJSBoolean(Object obj) {
        return obj instanceof JSBooleanObject;
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
    public TruffleString getBuiltinToStringTag(JSDynamicObject object) {
        return this.getClassName(object);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException noBooleanError() {
        throw Errors.createTypeError("not a Boolean object");
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
        if (JavaScriptLanguage.get(null).getJSContext().isOptionNashornCompatibilityMode()) {
            return Strings.concatAll(Strings.BRACKET_BOOLEAN_SPC, Strings.fromBoolean(JSBoolean.valueOf(obj)), Strings.BRACKET_CLOSE);
        }
        boolean primitiveValue = JSBoolean.valueOf(obj);
        return JSRuntime.objectToDisplayString(obj, allowSideEffects, format, depth, this.getBuiltinToStringTag(obj), new TruffleString[]{Strings.PRIMITIVE_VALUE}, new Object[]{primitiveValue});
    }

    @Override
    public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
        return realm.getBooleanPrototype();
    }
}

