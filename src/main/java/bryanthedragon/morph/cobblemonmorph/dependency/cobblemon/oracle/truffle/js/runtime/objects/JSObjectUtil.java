
package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.Builtin;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.objects.Accessor;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSOrdinaryObject;
import com.oracle.truffle.js.runtime.objects.JSPrototypeData;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.JSSharedData;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyProxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public final class JSObjectUtil {
    private static final HiddenKey PROTOTYPE_DATA = new HiddenKey("PROTOTYPE_DATA");

    private JSObjectUtil() {
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString formatToString(TruffleString object) {
        return Strings.concatAll(Strings.BRACKET_OBJECT_SPC, object, Strings.BRACKET_CLOSE);
    }

    public static JSObject createOrdinaryPrototypeObject(JSRealm realm) {
        CompilerAsserts.neverPartOfCompilation();
        return JSObjectUtil.createOrdinaryPrototypeObject(realm, realm.getObjectPrototype());
    }

    public static JSObject createOrdinaryPrototypeObject(JSRealm realm, JSDynamicObject prototype) {
        JSObject obj;
        CompilerAsserts.neverPartOfCompilation();
        assert (prototype == Null.instance || JSRuntime.isObject(prototype));
        JSContext context = realm.getContext();
        if (context.isMultiContext()) {
            obj = JSOrdinary.createInitWithInstancePrototype(prototype, context);
        } else {
            Shape initialShape = prototype == Null.instance ? context.getEmptyShapeNullPrototype() : JSObjectUtil.getProtoChildShape(prototype, JSOrdinary.INSTANCE, context);
            obj = JSOrdinaryObject.create(initialShape);
        }
        return obj;
    }

    public static void setOrVerifyPrototype(JSContext context, JSDynamicObject obj, JSDynamicObject prototype) {
        CompilerAsserts.neverPartOfCompilation();
        assert (prototype == Null.instance || JSRuntime.isObject(prototype));
        if (context.isMultiContext()) {
            JSObjectUtil.putHiddenProperty(obj, JSObject.HIDDEN_PROTO, prototype);
        } else assert (JSObjectUtil.getHiddenProperty(obj, JSObject.HIDDEN_PROTO) == prototype);
    }

    public static boolean isValidPrototype(Object proto) {
        return proto == Null.instance || JSRuntime.isObject(proto);
    }

    @CompilerDirectives.TruffleBoundary
    public static void putDataProperty(JSContext context, JSDynamicObject thisObj, Object key, Object value2, int flags) {
        assert (JSObjectUtil.checkForExistingProperty(thisObj, key));
        JSObjectUtil.defineDataProperty(context, thisObj, key, value2, flags);
    }

    @CompilerDirectives.TruffleBoundary
    public static void putDataProperty(JSDynamicObject thisObj, Object name, Object value2, int flags) {
        JSContext context = JSObject.getJSContext(thisObj);
        JSObjectUtil.putDataProperty(context, thisObj, name, value2, flags);
    }

    @CompilerDirectives.TruffleBoundary
    public static void defineDataProperty(JSContext context, JSDynamicObject thisObj, Object key, Object value2, int flags) {
        JSObjectUtil.checkForNoSuchPropertyOrMethod(context, key);
        Properties.putWithFlagsUncached(thisObj, key, value2, flags);
    }

    @CompilerDirectives.TruffleBoundary
    public static void defineDataProperty(JSDynamicObject thisObj, Object key, Object value2, int flags) {
        JSContext context = JSObject.getJSContext(thisObj);
        JSObjectUtil.defineDataProperty(context, thisObj, key, value2, flags);
    }

    public static void putOrSetDataProperty(JSContext context, JSDynamicObject thisObj, Object key, Object value2, int flags) {
        if (!JSObject.hasOwnProperty(thisObj, key)) {
            JSObjectUtil.putDataProperty(context, thisObj, key, value2, flags);
        } else {
            JSObject.set(thisObj, key, value2);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public static void defineAccessorProperty(JSDynamicObject thisObj, Object key, Accessor accessor, int flags) {
        int finalFlags = flags | 8;
        JSContext context = JSObject.getJSContext(thisObj);
        JSObjectUtil.checkForNoSuchPropertyOrMethod(context, key);
        Properties.putWithFlagsUncached(thisObj, key, accessor, finalFlags);
    }

    @CompilerDirectives.TruffleBoundary
    public static void defineProxyProperty(JSDynamicObject thisObj, Object key, PropertyProxy proxy, int flags) {
        int finalFlags = flags | 0x10;
        JSContext context = JSObject.getJSContext(thisObj);
        JSObjectUtil.checkForNoSuchPropertyOrMethod(context, key);
        Properties.putConstantUncached(thisObj, key, proxy, finalFlags);
    }

    @CompilerDirectives.TruffleBoundary
    public static void changePropertyFlags(JSDynamicObject thisObj, Object key, int flags) {
        assert (flags == (flags & 7));
        JSDynamicObject.updatePropertyFlags(thisObj, key, attr -> attr & 0xFFFFFFF8 | flags);
    }

    public static void putDataProperty(JSContext context, JSDynamicObject thisObj, Object name, Object value2) {
        JSObjectUtil.putDataProperty(context, thisObj, name, value2, JSAttributes.notConfigurableNotEnumerableNotWritable());
    }

    @CompilerDirectives.TruffleBoundary
    public static void putDeclaredDataProperty(JSContext context, JSDynamicObject thisObj, Object key, Object value2, int flags) {
        assert (JSRuntime.isPropertyKey(key));
        assert (JSObjectUtil.checkForExistingProperty(thisObj, key));
        JSObjectUtil.checkForNoSuchPropertyOrMethod(context, key);
        Properties.putConstantUncached(thisObj, key, value2, flags);
    }

    public static void putConstructorProperty(JSContext context, JSDynamicObject prototype, JSDynamicObject constructor) {
        JSObjectUtil.putDataProperty(context, prototype, JSObject.CONSTRUCTOR, constructor, JSAttributes.configurableNotEnumerableWritable());
    }

    public static void putConstructorPrototypeProperty(JSContext ctx, JSDynamicObject constructor, JSDynamicObject prototype) {
        JSObjectUtil.putDataProperty(ctx, constructor, JSObject.PROTOTYPE, prototype, JSAttributes.notConfigurableNotEnumerableNotWritable());
    }

    public static void putToStringTag(JSDynamicObject prototype, TruffleString toStringTag) {
        assert (JSObjectUtil.checkForExistingProperty(prototype, Symbol.SYMBOL_TO_STRING_TAG));
        Properties.putWithFlagsUncached(prototype, Symbol.SYMBOL_TO_STRING_TAG, toStringTag, JSAttributes.configurableNotEnumerableNotWritable());
    }

    @CompilerDirectives.TruffleBoundary
    public static void putAccessorProperty(JSContext context, JSDynamicObject thisObj, Object key, JSDynamicObject getter, JSDynamicObject setter, int flags) {
        Accessor accessor = new Accessor(getter, setter);
        JSObjectUtil.putAccessorProperty(context, thisObj, key, accessor, flags);
    }

    @CompilerDirectives.TruffleBoundary
    public static void putAccessorProperty(JSContext context, JSDynamicObject thisObj, Object key, Accessor accessor, int flags) {
        assert (JSRuntime.isPropertyKey(key));
        assert (JSObjectUtil.checkForExistingProperty(thisObj, key));
        JSObjectUtil.checkForNoSuchPropertyOrMethod(context, key);
        Properties.putWithFlagsUncached(thisObj, key, accessor, flags | 8);
    }

    public static void putBuiltinAccessorProperty(JSDynamicObject thisObj, Object key, JSDynamicObject getter, JSDynamicObject setter) {
        JSObjectUtil.putBuiltinAccessorProperty(thisObj, key, getter, setter, JSAttributes.configurableNotEnumerable());
    }

    @CompilerDirectives.TruffleBoundary
    public static void putBuiltinAccessorProperty(JSDynamicObject thisObj, Object key, JSDynamicObject getter, JSDynamicObject setter, int flags) {
        Accessor accessor = new Accessor(getter, setter);
        JSObjectUtil.putBuiltinAccessorProperty(thisObj, key, accessor, flags);
    }

    @CompilerDirectives.TruffleBoundary
    public static void putBuiltinAccessorProperty(JSDynamicObject thisObj, Object key, Accessor accessor, int flags) {
        assert (JSRuntime.isPropertyKey(key) && !JSObjectUtil.isNoSuchPropertyOrMethod(key));
        assert (JSObjectUtil.checkForExistingProperty(thisObj, key));
        Properties.putWithFlagsUncached(thisObj, key, accessor, flags | 8);
    }

    public static void putBuiltinAccessorProperty(JSDynamicObject thisObj, Object key, Accessor accessor) {
        JSObjectUtil.putBuiltinAccessorProperty(thisObj, key, accessor, JSAttributes.configurableNotEnumerable());
    }

    public static void putProxyProperty(JSDynamicObject thisObj, Object key, PropertyProxy proxy, int flags) {
        assert (JSRuntime.isPropertyKey(key) && !JSObjectUtil.isNoSuchPropertyOrMethod(key));
        assert (JSObjectUtil.checkForExistingProperty(thisObj, key));
        JSObjectUtil.defineProxyProperty(thisObj, key, proxy, flags);
    }

    private static boolean checkForExistingProperty(JSDynamicObject thisObj, Object key) {
        assert (!thisObj.getShape().hasProperty(key)) : "Don't put a property that already exists. Use the setters.";
        return true;
    }

    public static Shape getProtoChildShape(JSDynamicObject obj, JSClass jsclass, JSContext context) {
        CompilerAsserts.neverPartOfCompilation();
        if (obj == null) {
            return context.makeEmptyShapeWithPrototypeInObject(jsclass);
        }
        assert (JSRuntime.isObject(obj));
        Shape protoChild = JSObjectUtil.getProtoChildShapeMaybe(obj, jsclass);
        if (protoChild != null) {
            return protoChild;
        }
        return JSObjectUtil.getProtoChildShapeSlowPath(obj, jsclass, context);
    }

    public static Shape getProtoChildShape(JSDynamicObject obj, JSClass jsclass, JSContext context, BranchProfile branchProfile) {
        Shape protoChild = JSObjectUtil.getProtoChildShapeMaybe(obj, jsclass);
        if (protoChild != null) {
            return protoChild;
        }
        branchProfile.enter();
        return JSObjectUtil.getProtoChildShapeSlowPath(obj, jsclass, context);
    }

    private static Shape getProtoChildShapeMaybe(JSDynamicObject obj, JSClass jsclass) {
        Shape protoChild = JSShape.getProtoChildTree(obj, jsclass);
        assert (protoChild == null || JSShape.getJSClassNoCast(protoChild) == jsclass);
        return protoChild;
    }

    @CompilerDirectives.TruffleBoundary
    private static Shape getProtoChildShapeSlowPath(JSDynamicObject obj, JSClass jsclass, JSContext context) {
        JSPrototypeData prototypeData = JSObjectUtil.getPrototypeData(obj);
        if (prototypeData == null) {
            prototypeData = JSObjectUtil.putPrototypeData(obj);
        }
        return prototypeData.getOrAddProtoChildTree(jsclass, JSObjectUtil.createChildRootShape(obj, jsclass, context));
    }

    private static Shape createChildRootShape(JSDynamicObject proto, JSClass jsclass, JSContext context) {
        CompilerAsserts.neverPartOfCompilation();
        assert (proto != null && proto != Null.instance);
        return JSShape.createObjectShape(context, jsclass, proto);
    }

    public static JSPrototypeData putPrototypeData(JSDynamicObject obj) {
        CompilerAsserts.neverPartOfCompilation();
        assert (JSObjectUtil.getPrototypeData(obj) == null);
        JSPrototypeData prototypeData = new JSPrototypeData();
        JSObjectUtil.putPrototypeData(obj, prototypeData);
        return prototypeData;
    }

    private static void putPrototypeData(JSDynamicObject obj, JSPrototypeData prototypeData) {
        boolean extensible = JSShape.isExtensible(obj.getShape());
        JSObjectUtil.putHiddenProperty(obj, PROTOTYPE_DATA, prototypeData);
        assert (extensible == JSShape.isExtensible(obj.getShape()));
    }

    static JSPrototypeData getPrototypeData(JSDynamicObject obj) {
        return (JSPrototypeData)JSDynamicObject.getOrNull(obj, PROTOTYPE_DATA);
    }

    public static Map<Object, Object> archive(JSDynamicObject obj) {
        HashMap<Object, Object> ret = new HashMap<Object, Object>();
        Shape shape = obj.getShape();
        for (Property prop : shape.getPropertyListInternal(false)) {
            if (prop.getLocation().isConstant() || ret.containsKey(prop.getKey())) continue;
            ret.put(prop.getKey(), JSDynamicObject.getOrNull(obj, prop.getKey()));
        }
        return ret;
    }

    @CompilerDirectives.TruffleBoundary
    public static void setPrototypeImpl(JSDynamicObject object, JSDynamicObject newPrototype) {
        Shape newRootShape;
        CompilerAsserts.neverPartOfCompilation();
        assert (JSShape.isPrototypeInShape(object.getShape()));
        JSContext context = JSObject.getJSContext(object);
        Shape oldShape = object.getShape();
        JSShape.invalidatePrototypeAssumption(oldShape);
        JSClass jsclass = JSShape.getJSClass(oldShape);
        if (newPrototype == Null.instance) {
            newRootShape = context.makeEmptyShapeWithNullPrototype(jsclass);
        } else {
            assert (JSRuntime.isObject(newPrototype)) : newPrototype;
            newRootShape = context.isMultiContext() ? context.makeEmptyShapeWithPrototypeInObject(jsclass) : JSObjectUtil.getProtoChildShape(newPrototype, jsclass, context);
        }
        DynamicObjectLibrary lib = DynamicObjectLibrary.getUncached();
        List<Property> allProperties = oldShape.getPropertyListInternal(true);
        ArrayList<Object> archive = new ArrayList<Object>(allProperties.size());
        for (Property prop : allProperties) {
            Object value2 = Properties.getOrDefault(lib, object, prop.getKey(), null);
            archive.add(value2);
        }
        lib.resetShape(object, newRootShape);
        for (int i = 0; i < allProperties.size(); ++i) {
            Property property = allProperties.get(i);
            Object key = property.getKey();
            if (newRootShape.hasProperty(key)) continue;
            Object value3 = archive.get(i);
            int propertyFlags = property.getFlags();
            if (JSObject.HIDDEN_PROTO.equals(key)) {
                Properties.putWithFlags(lib, object, key, newPrototype, propertyFlags);
                continue;
            }
            if (property.getLocation().isConstant()) {
                Properties.putConstant(lib, object, key, value3, propertyFlags);
                continue;
            }
            Properties.putWithFlags(lib, object, key, value3, propertyFlags);
        }
        assert (JSObjectUtil.getPrototype(object) == newPrototype);
    }

    public static JSDynamicObject getPrototype(JSDynamicObject thisObj) {
        JSSharedData sharedData = JSShape.getSharedData(thisObj.getShape());
        JSDynamicObject proto = sharedData.getPrototype();
        if (proto != null) {
            assert (proto == JSDynamicObject.getOrDefault(thisObj, JSObject.HIDDEN_PROTO, Null.instance));
            return proto;
        }
        return (JSDynamicObject)JSDynamicObject.getOrDefault(thisObj, JSObject.HIDDEN_PROTO, Null.instance);
    }

    public static <T> T checkForNoSuchPropertyOrMethod(JSContext context, T key) {
        CompilerAsserts.neverPartOfCompilation();
        if (context != null && key != null && context.isOptionNashornCompatibilityMode()) {
            if (context.getNoSuchPropertyUnusedAssumption().isValid() && JSObject.NO_SUCH_PROPERTY_NAME.equals(key)) {
                context.getNoSuchPropertyUnusedAssumption().invalidate("NoSuchProperty is used");
            }
            if (context.getNoSuchMethodUnusedAssumption().isValid() && JSObject.NO_SUCH_METHOD_NAME.equals(key)) {
                context.getNoSuchMethodUnusedAssumption().invalidate("NoSuchMethod is used");
            }
        }
        return key;
    }

    public static boolean isNoSuchPropertyOrMethod(Object key) {
        CompilerAsserts.neverPartOfCompilation();
        return Strings.isTString(key) && (Strings.equals(JSObject.NO_SUCH_PROPERTY_NAME, (TruffleString)key) || Strings.equals(JSObject.NO_SUCH_METHOD_NAME, (TruffleString)key));
    }

    public static JSDynamicObject createSymbolSpeciesGetterFunction(JSRealm realm) {
        return JSFunction.create(realm, realm.getContext().getSymbolSpeciesThisGetterFunctionData());
    }

    public static void putFunctionsFromContainer(final JSRealm realm, final JSDynamicObject thisObj, JSBuiltinsContainer container) {
        final JSContext context = realm.getContext();
        container.forEachBuiltin((Consumer<? super JSBuiltin>)new Consumer<Builtin>(){

            @Override
            public void accept(Builtin builtin) {
                if (!builtin.isIncluded(context)) {
                    return;
                }
                if (builtin.isGetter() || builtin.isSetter()) {
                    return;
                }
                JSFunctionData functionData = builtin.createFunctionData(context);
                JSObjectUtil.putDataProperty(context, thisObj, builtin.getKey(), JSFunction.create(realm, functionData), builtin.getAttributeFlags());
            }
        });
    }

    public static void putHiddenProperty(JSDynamicObject obj, Object key, Object value2) {
        assert (key instanceof HiddenKey);
        Properties.putUncached(obj, key, value2);
    }

    public static Object getHiddenProperty(JSDynamicObject obj, Object key) {
        assert (key instanceof HiddenKey);
        return Properties.getOrDefaultUncached(obj, key, null);
    }

    public static boolean hasHiddenProperty(JSDynamicObject obj, Object key) {
        assert (key instanceof HiddenKey);
        return Properties.containsKeyUncached(obj, key);
    }

    public static DynamicObjectLibrary createCached(Object key, JSDynamicObject obj) {
        assert (key != null);
        return DynamicObjectLibrary.getFactory().create(obj);
    }

    public static DynamicObjectLibrary createDispatched(Object key, int limit) {
        assert (key != null);
        return DynamicObjectLibrary.getFactory().createDispatched(limit);
    }

    public static DynamicObjectLibrary createDispatched(Object key) {
        return JSObjectUtil.createDispatched(key, 5);
    }

    public static <T extends JSDynamicObject> T copyProperties(T target, JSDynamicObject source) {
        DynamicObjectLibrary objectLibrary = DynamicObjectLibrary.getUncached();
        for (Property property : source.getShape().getPropertyListInternal(true)) {
            Object key = property.getKey();
            if (Properties.containsKey(objectLibrary, target, key)) continue;
            Object value2 = Properties.getOrDefault(objectLibrary, source, key, null);
            if (property.getLocation().isConstant()) {
                Properties.putConstant(objectLibrary, target, key, value2, property.getFlags());
                continue;
            }
            Properties.putWithFlags(objectLibrary, target, key, value2, property.getFlags());
        }
        return target;
    }
}

