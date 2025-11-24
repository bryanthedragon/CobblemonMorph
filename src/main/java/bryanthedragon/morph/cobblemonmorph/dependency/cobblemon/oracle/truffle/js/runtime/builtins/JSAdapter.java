
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.builtins.AbstractJSClass;
import com.oracle.truffle.js.runtime.builtins.JSAdapterObject;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import java.util.List;

public final class JSAdapter
extends AbstractJSClass
implements JSConstructorFactory.Default,
PrototypeSupplier {
    public static final TruffleString CLASS_NAME = Strings.constant("JSAdapter");
    public static final JSAdapter INSTANCE = new JSAdapter();
    private static final TruffleString GET = Strings.constant("__get__");
    private static final TruffleString PUT = Strings.constant("__put__");
    private static final TruffleString HAS = Strings.constant("__has__");
    private static final TruffleString CALL = Strings.constant("__call__");
    private static final TruffleString DELETE = Strings.constant("__delete__");
    public static final TruffleString NEW = Strings.constant("__new__");
    public static final TruffleString GET_IDS = Strings.constant("__getIds__");
    public static final TruffleString GET_VALUES = Strings.constant("__getValues__");

    private JSAdapter() {
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
    public String toString() {
        return Strings.toJavaString(this.getClassName());
    }

    public static JSObject create(JSContext context, JSRealm realm, JSDynamicObject adaptee, JSDynamicObject overrides, JSDynamicObject proto) {
        JSObjectFactory factory = context.getJSAdapterFactory();
        JSAdapterObject obj = new JSAdapterObject(factory.getShape(realm), adaptee, overrides);
        factory.initProto(obj, realm);
        if (proto != null) {
            JSObject.setPrototype(obj, proto);
        }
        return context.trackAllocation(obj);
    }

    public static JSDynamicObject getAdaptee(JSDynamicObject obj) {
        assert (JSAdapter.isJSAdapter(obj));
        return ((JSAdapterObject)obj).getAdaptee();
    }

    public static JSDynamicObject getOverrides(JSDynamicObject obj) {
        assert (JSAdapter.isJSAdapter(obj));
        return ((JSAdapterObject)obj).getOverrides();
    }

    public static boolean isJSAdapter(Object obj) {
        return obj instanceof JSAdapterObject;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Object getOwnHelper(JSDynamicObject store, Object thisObj, Object key, Node encapsulatingNode) {
        assert (JSRuntime.isPropertyKey(key));
        JSDynamicObject overrides = JSAdapter.getOverrides(store);
        if (overrides != null && JSObject.hasOwnProperty(overrides, key)) {
            return JSObject.get(overrides, key);
        }
        return JSAdapter.getIntl(store, key);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Object getOwnHelper(JSDynamicObject store, Object thisObj, long index, Node encapsulatingNode) {
        JSDynamicObject overrides = JSAdapter.getOverrides(store);
        if (overrides != null && JSObject.hasOwnProperty(overrides, index)) {
            return JSObject.get(overrides, index);
        }
        assert (JSRuntime.longIsRepresentableAsInt(index));
        return JSAdapter.getIntl(store, (int)index);
    }

    private static Object getIntl(JSDynamicObject thisObj, Object key) {
        if (key instanceof Symbol) {
            return null;
        }
        JSDynamicObject adaptee = JSAdapter.getAdaptee(thisObj);
        Object get = JSObject.get(adaptee, GET);
        if (JSFunction.isJSFunction(get)) {
            return JSFunction.call((JSFunctionObject)get, thisObj, new Object[]{key});
        }
        return null;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean hasOwnProperty(JSDynamicObject thisObj, long index) {
        JSDynamicObject overrides = JSAdapter.getOverrides(thisObj);
        if (overrides != null && JSObject.hasOwnProperty(overrides, index)) {
            return true;
        }
        return JSAdapter.hasOwnPropertyIntl(thisObj, index);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean hasOwnProperty(JSDynamicObject thisObj, Object key) {
        assert (JSRuntime.isPropertyKey(key));
        JSDynamicObject overrides = JSAdapter.getOverrides(thisObj);
        if (overrides != null && JSObject.hasOwnProperty(overrides, key)) {
            return true;
        }
        return JSAdapter.hasOwnPropertyIntl(thisObj, key);
    }

    private static boolean hasOwnPropertyIntl(JSDynamicObject thisObj, Object key) {
        JSDynamicObject adaptee = JSAdapter.getAdaptee(thisObj);
        Object has = JSObject.get(adaptee, HAS);
        if (JSFunction.isJSFunction(has)) {
            return JSRuntime.toBoolean(JSFunction.call((JSFunctionObject)has, thisObj, new Object[]{key}));
        }
        return false;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean set(JSDynamicObject thisObj, long index, Object value2, Object receiver, boolean isStrict, Node encapsulatingNode) {
        JSDynamicObject overrides = JSAdapter.getOverrides(thisObj);
        if (overrides != null && JSObject.hasOwnProperty(overrides, index)) {
            JSObject.set(overrides, index, value2, isStrict, encapsulatingNode);
            return true;
        }
        JSDynamicObject adaptee = JSAdapter.getAdaptee(thisObj);
        Object set2 = JSObject.get(adaptee, PUT);
        if (JSFunction.isJSFunction(set2)) {
            assert (JSRuntime.longIsRepresentableAsInt(index));
            JSFunction.call((JSFunctionObject)set2, thisObj, new Object[]{(int)index, value2});
        }
        return true;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean set(JSDynamicObject thisObj, Object key, Object value2, Object receiver, boolean isStrict, Node encapsulatingNode) {
        assert (JSRuntime.isPropertyKey(key));
        JSDynamicObject overrides = JSAdapter.getOverrides(thisObj);
        if (overrides != null && JSObject.hasOwnProperty(overrides, key)) {
            return JSObject.set(overrides, key, value2, isStrict, encapsulatingNode);
        }
        JSDynamicObject adaptee = JSAdapter.getAdaptee(thisObj);
        Object set2 = JSObject.get(adaptee, PUT);
        if (JSFunction.isJSFunction(set2)) {
            JSFunction.call((JSFunctionObject)set2, thisObj, new Object[]{key, value2});
        }
        return true;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean delete(JSDynamicObject thisObj, long index, boolean isStrict) {
        JSDynamicObject overrides = JSAdapter.getOverrides(thisObj);
        if (overrides != null && JSObject.delete(overrides, index, isStrict)) {
            return true;
        }
        JSDynamicObject adaptee = JSAdapter.getAdaptee(thisObj);
        Object delete = JSObject.get(adaptee, DELETE);
        if (JSFunction.isJSFunction(delete)) {
            JSFunction.call((JSFunctionObject)delete, thisObj, new Object[]{index});
        }
        return true;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean delete(JSDynamicObject thisObj, Object key, boolean isStrict) {
        JSDynamicObject overrides = JSAdapter.getOverrides(thisObj);
        if (overrides != null && JSObject.delete(overrides, key, isStrict)) {
            return true;
        }
        JSDynamicObject adaptee = JSAdapter.getAdaptee(thisObj);
        Object delete = JSObject.get(adaptee, DELETE);
        if (JSFunction.isJSFunction(delete)) {
            JSFunction.call((JSFunctionObject)delete, thisObj, new Object[]{key});
        }
        return true;
    }

    @Override
    public boolean defineOwnProperty(JSDynamicObject thisObj, Object key, PropertyDescriptor desc, boolean doThrow) {
        return this.set(thisObj, key, desc.getValue(), (Object)thisObj, doThrow, null);
    }

    @Override
    public boolean preventExtensions(JSDynamicObject thisObj, boolean doThrow) {
        return true;
    }

    @Override
    public boolean isExtensible(JSDynamicObject thisObj) {
        return true;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public List<Object> getOwnPropertyKeys(JSDynamicObject thisObj, boolean strings, boolean symbols) {
        Object returnValue;
        JSDynamicObject adaptee = JSAdapter.getAdaptee(thisObj);
        Object getIds = JSObject.get(adaptee, GET_IDS);
        if (JSFunction.isJSFunction(getIds) && JSRuntime.isObject(returnValue = JSFunction.call((JSFunctionObject)getIds, thisObj, JSArguments.EMPTY_ARGUMENTS_ARRAY))) {
            return JSAdapter.filterOwnPropertyKeys(JSRuntime.createListFromArrayLikeAllowSymbolString(returnValue), strings, symbols);
        }
        return super.getOwnPropertyKeys(thisObj, strings, symbols);
    }

    @Override
    public TruffleString toDisplayStringImpl(JSDynamicObject object, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
        return this.defaultToString(object);
    }

    @Override
    public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject ctor) {
        JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
        JSObjectUtil.putConstructorProperty(realm.getContext(), prototype, ctor);
        JSObjectUtil.putToStringTag(prototype, CLASS_NAME);
        return prototype;
    }

    @Override
    public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
        return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
    }

    public static JSConstructor createConstructor(JSRealm realm) {
        return INSTANCE.createConstructorAndPrototype(realm);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Object getMethodHelper(JSDynamicObject store, Object thisObj, Object key, Node encapsulatingNode) {
        if (key instanceof Symbol) {
            return null;
        }
        JSDynamicObject adaptee = JSAdapter.getAdaptee(store);
        Object call = JSObject.get(adaptee, CALL);
        if (JSFunction.isJSFunction(call)) {
            return JSFunction.bind(JSFunction.getRealm((JSFunctionObject)call), (JSFunctionObject)call, store, new Object[]{key});
        }
        throw this.createTypeErrorNoSuchFunction(store, key);
    }

    @CompilerDirectives.TruffleBoundary
    private JSException createTypeErrorNoSuchFunction(JSDynamicObject thisObj, Object key) {
        return Errors.createTypeErrorFormat("%s has no such function \"%s\"", this.defaultToString(thisObj), key);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public JSDynamicObject getPrototypeOf(JSDynamicObject thisObj) {
        return JSObjectUtil.getPrototype(thisObj);
    }

    @Override
    public boolean setPrototypeOf(JSDynamicObject thisObj, JSDynamicObject newPrototype) {
        return JSNonProxy.setPrototypeStatic(thisObj, newPrototype);
    }

    @Override
    public PropertyDescriptor getOwnProperty(JSDynamicObject thisObj, Object key) {
        return null;
    }

    @Override
    public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
        return realm.getJSAdapterPrototype();
    }
}

