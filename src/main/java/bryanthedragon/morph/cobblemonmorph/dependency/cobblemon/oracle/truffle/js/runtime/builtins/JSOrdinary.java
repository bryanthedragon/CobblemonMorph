
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSOrdinaryObject;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.util.CompilableBiFunction;

public final class JSOrdinary
extends JSNonProxy
implements PrototypeSupplier {
    public static final TruffleString TYPE_NAME = Strings.constant("object");
    public static final TruffleString CLASS_NAME = Strings.constant("Object");
    public static final TruffleString PROTOTYPE_NAME = Strings.constant("Object.prototype");
    public static final JSOrdinary INSTANCE = new JSOrdinary();
    public static final CompilableBiFunction<JSContext, JSDynamicObject, Shape> SHAPE_SUPPLIER = (ctx, proto) -> JSObjectUtil.getProtoChildShape(proto, INSTANCE, ctx);
    public static final JSOrdinary BARE_INSTANCE = new JSOrdinary();
    public static final JSOrdinary INTERNAL_FIELD_INSTANCE = new JSOrdinary();
    public static final JSOrdinary OVERLOADED_OPERATORS_INSTANCE = new JSOrdinary();

    private JSOrdinary() {
    }

    public static JSObject create(JSContext context, JSObjectFactory factory, JSRealm realm) {
        return JSOrdinary.createWithRealm(context, factory, realm);
    }

    public static JSObject createWithRealm(JSContext context, JSObjectFactory factory, JSRealm realm) {
        JSOrdinaryObject obj = JSOrdinaryObject.create(factory.getShape(realm));
        factory.initProto(obj, realm);
        return context.trackAllocation(obj);
    }

    public static JSObject create(JSContext context, JSRealm realm) {
        return JSOrdinary.createWithRealm(context, context.getOrdinaryObjectFactory(), realm);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSObject createWithPrototype(JSDynamicObject prototype, JSContext context) {
        return JSOrdinary.createWithPrototype(prototype, context, INSTANCE);
    }

    public static JSObject createWithNullPrototype(JSContext context) {
        return context.trackAllocation(JSOrdinaryObject.create(context.getEmptyShapeNullPrototype()));
    }

    @CompilerDirectives.TruffleBoundary
    public static JSObject createWithPrototype(JSDynamicObject prototype, JSContext context, JSOrdinary instanceLayout) {
        JSOrdinaryObject obj;
        assert (JSObjectUtil.isValidPrototype(prototype));
        if (prototype == Null.instance) {
            obj = JSOrdinaryObject.create(context.makeEmptyShapeWithNullPrototype(instanceLayout));
        } else if (!context.isMultiContext()) {
            obj = JSOrdinaryObject.create(JSObjectUtil.getProtoChildShape(prototype, instanceLayout, context));
        } else {
            obj = JSOrdinaryObject.create(context.makeEmptyShapeWithPrototypeInObject(instanceLayout));
            JSOrdinary.setProtoSlow(obj, prototype);
        }
        return context.trackAllocation(obj);
    }

    public static JSObject createInitWithInstancePrototype(JSDynamicObject prototype, JSContext context) {
        assert (JSObjectUtil.isValidPrototype(prototype));
        Shape shape = context.getEmptyShapePrototypeInObject();
        JSOrdinaryObject obj = JSOrdinaryObject.create(shape);
        JSOrdinary.setProtoSlow(obj, prototype);
        return obj;
    }

    private static void setProtoSlow(JSObject obj, JSDynamicObject prototype) {
        JSObjectUtil.putHiddenProperty(obj, JSObject.HIDDEN_PROTO, prototype);
    }

    public static JSObject createWithoutPrototype(JSContext context) {
        Shape shape = context.getEmptyShapePrototypeInObject();
        JSObject obj = JSOrdinary.create(context, shape);
        return obj;
    }

    public static JSObject create(JSContext context, Shape shape) {
        assert (JSShape.getJSClass(shape) instanceof JSOrdinary);
        return context.trackAllocation(JSOrdinaryObject.create(shape));
    }

    public static JSObject createInit(JSRealm realm) {
        CompilerAsserts.neverPartOfCompilation();
        return JSOrdinary.createInit(realm, realm.getObjectPrototype());
    }

    public static JSObject createInit(JSRealm realm, JSDynamicObject prototype) {
        CompilerAsserts.neverPartOfCompilation();
        assert (JSObjectUtil.isValidPrototype(prototype));
        JSContext context = realm.getContext();
        if (context.isMultiContext()) {
            return JSOrdinary.createInitWithInstancePrototype(prototype, context);
        }
        return JSOrdinaryObject.create(prototype == Null.instance ? context.getEmptyShapeNullPrototype() : JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context));
    }

    public static JSObject createWithNullPrototypeInit(JSContext context) {
        CompilerAsserts.neverPartOfCompilation();
        return JSOrdinaryObject.create(context.getEmptyShapeNullPrototype());
    }

    public static boolean isJSOrdinaryObject(Object obj) {
        return JSDynamicObject.isJSDynamicObject(obj) && JSOrdinary.isJSOrdinaryObject((JSDynamicObject)obj);
    }

    public static boolean isJSOrdinaryObject(JSDynamicObject obj) {
        return JSOrdinary.isInstance(obj, (JSClass)INSTANCE);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public TruffleString getClassName(JSDynamicObject object) {
        Object toStringTag;
        JSContext context = JSObject.getJSContext(object);
        if (context.getEcmaScriptVersion() <= 5 && Strings.isTString(toStringTag = this.get(object, Symbol.SYMBOL_TO_STRING_TAG))) {
            return JSRuntime.toStringIsString(toStringTag);
        }
        return CLASS_NAME;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
        if (JavaScriptLanguage.get(null).getJSContext().isOptionNashornCompatibilityMode()) {
            return this.defaultToString(obj);
        }
        return JSRuntime.objectToDisplayString(obj, allowSideEffects, format, depth, null);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Object get(JSDynamicObject thisObj, long index) {
        return this.get(thisObj, Strings.fromLong(index));
    }

    @Override
    public boolean hasOnlyShapeProperties(JSDynamicObject obj) {
        return true;
    }

    @Override
    public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
        return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
    }

    @Override
    public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
        return realm.getObjectPrototype();
    }
}

