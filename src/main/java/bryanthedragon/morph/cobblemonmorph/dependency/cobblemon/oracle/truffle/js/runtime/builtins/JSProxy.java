
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.ConstructorBuiltins;
import com.oracle.truffle.js.builtins.ProxyFunctionBuiltins;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.access.JSProxyCallNode;
import com.oracle.truffle.js.nodes.interop.ForeignObjectPrototypeNode;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.builtins.AbstractJSClass;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSProxyObject;
import com.oracle.truffle.js.runtime.builtins.JSUncheckedProxyHandlerObject;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.DefinePropertyUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public final class JSProxy
extends AbstractJSClass
implements PrototypeSupplier {
    public static final TruffleString CLASS_NAME = Strings.constant("Proxy");
    public static final JSProxy INSTANCE = new JSProxy();
    public static final TruffleString GET_PROTOTYPE_OF = Strings.constant("getPrototypeOf");
    public static final TruffleString SET_PROTOTYPE_OF = Strings.constant("setPrototypeOf");
    public static final TruffleString IS_EXTENSIBLE = Strings.constant("isExtensible");
    public static final TruffleString PREVENT_EXTENSIONS = Strings.constant("preventExtensions");
    public static final TruffleString GET_OWN_PROPERTY_DESCRIPTOR = Strings.constant("getOwnPropertyDescriptor");
    public static final TruffleString HAS = Strings.constant("has");
    public static final TruffleString GET = Strings.constant("get");
    public static final TruffleString SET = Strings.constant("set");
    public static final TruffleString DELETE_PROPERTY = Strings.constant("deleteProperty");
    public static final TruffleString DEFINE_PROPERTY = Strings.constant("defineProperty");
    public static final TruffleString OWN_KEYS = Strings.constant("ownKeys");
    public static final TruffleString APPLY = Strings.constant("apply");
    public static final TruffleString CONSTRUCT = Strings.constant("construct");
    public static final TruffleString FOREIGN = Strings.constant("Foreign");
    public static final TruffleString PROXY_CALL = Strings.constant("ProxyCall");
    public static final HiddenKey REVOCABLE_PROXY = new HiddenKey("RevocableProxy");

    @CompilerDirectives.TruffleBoundary
    public static boolean checkPropertyIsSettable(Object truffleTarget, Object key) {
        assert (JSRuntime.isPropertyKey(key));
        if (!JSDynamicObject.isJSDynamicObject(truffleTarget)) {
            return true;
        }
        JSDynamicObject target = (JSDynamicObject)truffleTarget;
        PropertyDescriptor desc = JSObject.getOwnProperty(target, key);
        if (desc != null) {
            if (!desc.getConfigurable()) {
                return false;
            }
            if (!JSObject.isExtensible(target)) {
                return false;
            }
        }
        return true;
    }

    private JSProxy() {
    }

    @Override
    public TruffleString getClassName(JSDynamicObject object) {
        return CLASS_NAME;
    }

    @Override
    public String toString() {
        return Strings.toJavaString(CLASS_NAME);
    }

    public static JSProxyObject create(JSContext context, JSRealm realm, Object target, JSDynamicObject handler) {
        return JSProxyObject.create(realm, context.getProxyFactory(), target, handler);
    }

    public static Object getTarget(JSDynamicObject obj) {
        assert (JSProxy.isJSProxy(obj));
        return ((JSProxyObject)obj).getProxyTarget();
    }

    public static Object getTargetNonProxy(JSDynamicObject thisObj) {
        Object obj = thisObj;
        while (JSProxy.isJSProxy(obj)) {
            obj = JSProxy.getTarget((JSDynamicObject)obj);
        }
        return obj;
    }

    public static JSDynamicObject getHandler(JSDynamicObject obj) {
        assert (JSProxy.isJSProxy(obj));
        return ((JSProxyObject)obj).getProxyHandler();
    }

    public static JSDynamicObject getHandlerChecked(JSDynamicObject obj) {
        JSDynamicObject handler = JSProxy.getHandler(obj);
        if (handler == Null.instance) {
            throw Errors.createTypeErrorProxyRevoked();
        }
        return handler;
    }

    public static JSDynamicObject getHandlerChecked(JSDynamicObject obj, BranchProfile errorBranch) {
        JSDynamicObject handler = JSProxy.getHandler(obj);
        if (handler == Null.instance) {
            errorBranch.enter();
            throw Errors.createTypeErrorProxyRevoked();
        }
        return handler;
    }

    public static boolean isJSProxy(Object obj) {
        return obj instanceof JSProxyObject;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Object getOwnHelper(JSDynamicObject store, Object receiver, Object key, Node encapsulatingNode) {
        assert (JSRuntime.isPropertyKey(key));
        return JSProxy.proxyGetHelper(store, key, receiver, encapsulatingNode);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Object getOwnHelper(JSDynamicObject store, Object receiver, long index, Node encapsulatingNode) {
        assert (JSRuntime.isSafeInteger(index));
        return JSProxy.proxyGetHelper(store, Strings.fromLong(index), receiver, encapsulatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    private static Object proxyGetHelper(JSDynamicObject proxy, Object key, Object receiver, Node encapsulatingNode) {
        assert (JSRuntime.isPropertyKey(key));
        JSDynamicObject handler = JSProxy.getHandlerChecked(proxy);
        Object target = JSProxy.getTarget(proxy);
        Object trap = JSProxy.getTrapFromObject(handler, GET);
        if (trap == Undefined.instance) {
            if (JSDynamicObject.isJSDynamicObject(target)) {
                JSDynamicObject jsobj = (JSDynamicObject)target;
                return JSObject.getJSClass(jsobj).getHelper(jsobj, receiver, key, encapsulatingNode);
            }
            Object result = JSInteropUtil.readMemberOrDefault(target, key, null);
            if (result == null && JavaScriptLanguage.get(encapsulatingNode).getJSContext().getContextOptions().hasForeignObjectPrototype()) {
                JSDynamicObject prototype = ForeignObjectPrototypeNode.getUncached().execute(target);
                result = JSObject.getJSClass(prototype).getHelper(prototype, receiver, key, encapsulatingNode);
            }
            return result;
        }
        Object trapResult = JSRuntime.call(trap, handler, new Object[]{target, key, receiver}, encapsulatingNode);
        if (!(handler instanceof JSUncheckedProxyHandlerObject)) {
            JSProxy.checkProxyGetTrapInvariants(target, key, trapResult);
        }
        return trapResult;
    }

    @CompilerDirectives.TruffleBoundary
    public static void checkProxyGetTrapInvariants(Object truffleTarget, Object key, Object trapResult) {
        assert (JSRuntime.isPropertyKey(key));
        if (!JSDynamicObject.isJSDynamicObject(truffleTarget)) {
            return;
        }
        JSDynamicObject target = (JSDynamicObject)truffleTarget;
        PropertyDescriptor targetDesc = JSObject.getOwnProperty(target, key);
        if (targetDesc != null) {
            Object targetValue;
            if (targetDesc.isDataDescriptor() && !targetDesc.getConfigurable() && !targetDesc.getWritable() && !JSRuntime.isSameValue(trapResult, targetValue = targetDesc.getValue())) {
                throw Errors.createTypeErrorProxyGetInvariantViolated(key, targetValue, trapResult);
            }
            if (targetDesc.isAccessorDescriptor() && !targetDesc.getConfigurable() && targetDesc.getGet() == Undefined.instance && trapResult != Undefined.instance) {
                throw Errors.createTypeError("Trap result must be undefined since the proxy target has a corresponding non-configurable own accessor property with undefined getter");
            }
        }
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean set(JSDynamicObject thisObj, Object key, Object value2, Object receiver, boolean isStrict, Node encapsulatingNode) {
        return JSProxy.proxySet(thisObj, key, value2, receiver, isStrict, encapsulatingNode);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean set(JSDynamicObject thisObj, long index, Object value2, Object receiver, boolean isStrict, Node encapsulatingNode) {
        return JSProxy.proxySet(thisObj, Strings.fromLong(index), value2, receiver, isStrict, encapsulatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    private static boolean proxySet(JSDynamicObject thisObj, Object key, Object value2, Object receiver, boolean isStrict, Node encapsulatingNode) {
        assert (JSRuntime.isPropertyKey(key));
        JSDynamicObject handler = JSProxy.getHandlerChecked(thisObj);
        Object target = JSProxy.getTarget(thisObj);
        Object trap = JSProxy.getTrapFromObject(handler, SET);
        if (trap == Undefined.instance) {
            if (JSDynamicObject.isJSDynamicObject(target)) {
                JSDynamicObject jsobj = (JSDynamicObject)target;
                return JSObject.getJSClass(jsobj).set(jsobj, key, value2, receiver, isStrict, encapsulatingNode);
            }
            JSInteropUtil.writeMember(target, key, value2);
            return true;
        }
        Object trapResult = JSRuntime.call(trap, handler, new Object[]{target, key, value2, receiver}, encapsulatingNode);
        boolean booleanTrapResult = JSRuntime.toBoolean(trapResult);
        if (!booleanTrapResult) {
            if (isStrict) {
                throw Errors.createTypeErrorTrapReturnedFalsish(SET, key);
            }
            return false;
        }
        if (handler instanceof JSUncheckedProxyHandlerObject) {
            return true;
        }
        return JSProxy.checkProxySetTrapInvariants(thisObj, key, value2);
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean checkProxySetTrapInvariants(JSDynamicObject proxy, Object key, Object value2) {
        assert (JSProxy.isJSProxy(proxy) && !JSProxy.isRevoked(proxy));
        assert (JSRuntime.isPropertyKey(key));
        Object target = JSProxy.getTarget(proxy);
        if (!JSDynamicObject.isJSDynamicObject(target)) {
            return true;
        }
        PropertyDescriptor targetDesc = JSObject.getOwnProperty((JSDynamicObject)target, key);
        if (targetDesc != null) {
            if (targetDesc.isDataDescriptor() && !targetDesc.getConfigurable() && !targetDesc.getWritable()) {
                if (!JSRuntime.isSameValue(value2, targetDesc.getValue())) {
                    throw Errors.createTypeError("Cannot change the value of a non-writable, non-configurable own data property");
                }
            } else if (targetDesc.isAccessorDescriptor() && !targetDesc.getConfigurable() && targetDesc.getSet() == Undefined.instance) {
                throw Errors.createTypeError("Cannot set the value of a non-configurable own accessor property with undefined setter");
            }
        }
        return true;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean hasOwnProperty(JSDynamicObject thisObj, long index) {
        return this.hasOwnProperty(thisObj, JSRuntime.toString(index));
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean hasOwnProperty(JSDynamicObject thisObj, Object key) {
        assert (JSRuntime.isObject(thisObj));
        assert (JSRuntime.isPropertyKey(key));
        PropertyDescriptor desc = JSObject.getOwnProperty(thisObj, key);
        return desc != null;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean hasProperty(JSDynamicObject thisObj, long index) {
        return this.hasProperty(thisObj, JSRuntime.toString(index));
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean hasProperty(JSDynamicObject thisObj, Object key) {
        assert (JSRuntime.isPropertyKey(key));
        JSDynamicObject handler = JSProxy.getHandlerChecked(thisObj);
        Object target = JSProxy.getTarget(thisObj);
        Object trap = JSProxy.getTrapFromObject(handler, HAS);
        if (trap == Undefined.instance) {
            if (JSDynamicObject.isJSDynamicObject(target)) {
                return JSObject.hasProperty((JSDynamicObject)target, key);
            }
            boolean result = JSInteropUtil.hasProperty(target, key);
            if (!result && JavaScriptLanguage.get(null).getJSContext().getContextOptions().hasForeignObjectPrototype()) {
                JSDynamicObject prototype = ForeignObjectPrototypeNode.getUncached().execute(target);
                result = JSObject.hasProperty(prototype, key);
            }
            return result;
        }
        boolean trapResult = JSRuntime.toBoolean(JSRuntime.call(trap, handler, new Object[]{target, key}));
        if (!trapResult && !JSProxy.checkPropertyIsSettable(target, key)) {
            throw Errors.createTypeErrorConfigurableExpected();
        }
        return trapResult;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean delete(JSDynamicObject thisObj, long index, boolean isStrict) {
        return this.delete(thisObj, Strings.fromLong(index), isStrict);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean delete(JSDynamicObject thisObj, Object key, boolean isStrict) {
        boolean extensibleTarget;
        assert (JSRuntime.isPropertyKey(key));
        JSDynamicObject handler = JSProxy.getHandlerChecked(thisObj);
        Object target = JSProxy.getTarget(thisObj);
        Object deleteFn = JSProxy.getTrapFromObject(handler, DELETE_PROPERTY);
        if (deleteFn == Undefined.instance) {
            if (JSDynamicObject.isJSDynamicObject(target)) {
                return JSObject.delete((JSDynamicObject)target, key, isStrict);
            }
            return JSInteropUtil.remove(target, key);
        }
        Object trapResult = JSRuntime.call(deleteFn, handler, new Object[]{target, key});
        boolean booleanTrapResult = JSRuntime.toBoolean(trapResult);
        if (!booleanTrapResult) {
            if (isStrict) {
                throw Errors.createTypeErrorTrapReturnedFalsish(DELETE_PROPERTY, key);
            }
            return false;
        }
        if (!JSDynamicObject.isJSDynamicObject(target)) {
            return true;
        }
        PropertyDescriptor targetDesc = JSObject.getOwnProperty((JSDynamicObject)target, key);
        if (targetDesc == null) {
            return true;
        }
        if (targetDesc.hasConfigurable() && !targetDesc.getConfigurable()) {
            throw Errors.createTypeErrorConfigurableExpected();
        }
        JSContext context = JSObject.getJSContext(thisObj);
        if (context.getEcmaScriptVersion() >= 11 && !(extensibleTarget = JSObject.isExtensible((JSDynamicObject)target))) {
            throw Errors.createTypeErrorProxyTargetNotExtensible();
        }
        return true;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean defineOwnProperty(JSDynamicObject thisObj, Object key, PropertyDescriptor desc, boolean doThrow) {
        boolean settingConfigFalse;
        assert (JSRuntime.isPropertyKey(key));
        JSDynamicObject handler = JSProxy.getHandlerChecked(thisObj);
        Object target = JSProxy.getTarget(thisObj);
        Object definePropertyFn = JSProxy.getTrapFromObject(handler, DEFINE_PROPERTY);
        if (definePropertyFn == Undefined.instance) {
            if (JSDynamicObject.isJSDynamicObject(target)) {
                return JSObject.defineOwnProperty((JSDynamicObject)target, key, desc, doThrow);
            }
            JSInteropUtil.writeMember(target, key, Null.instance);
            return true;
        }
        JSContext context = JSObject.getJSContext(thisObj);
        JSDynamicObject descObj = JSRuntime.fromPropertyDescriptor(desc, context);
        boolean trapResult = JSRuntime.toBoolean(JSRuntime.call(definePropertyFn, handler, new Object[]{target, key, descObj}));
        if (!trapResult) {
            if (doThrow) {
                if (handler instanceof JSUncheckedProxyHandlerObject) {
                    throw Errors.createTypeErrorCannotRedefineProperty(key);
                }
                throw Errors.createTypeErrorTrapReturnedFalsish(DEFINE_PROPERTY, key);
            }
            return false;
        }
        if (!JSDynamicObject.isJSDynamicObject(target) || handler instanceof JSUncheckedProxyHandlerObject) {
            return true;
        }
        PropertyDescriptor targetDesc = JSObject.getOwnProperty((JSDynamicObject)target, key);
        boolean extensibleTarget = JSObject.isExtensible((JSDynamicObject)target);
        boolean bl = settingConfigFalse = desc.hasConfigurable() && !desc.getConfigurable();
        if (targetDesc == null) {
            if (!extensibleTarget) {
                throw Errors.createTypeError("'defineProperty' on proxy: trap returned truish for adding property to the non-extensible proxy target");
            }
            if (settingConfigFalse) {
                throw Errors.createTypeError("'defineProperty' on proxy: trap returned truish for defining non-configurable property which is non-existant in the proxy target");
            }
        } else {
            if (!JSProxy.isCompatiblePropertyDescriptor(extensibleTarget, desc, targetDesc)) {
                throw Errors.createTypeError("'defineProperty' on proxy: trap returned truish for adding property that is incompatible with the existing property in the proxy target");
            }
            if (settingConfigFalse && targetDesc.getConfigurable()) {
                throw Errors.createTypeError("'defineProperty' on proxy: trap returned truish for defining non-configurable property which is configurable in the proxy target");
            }
            if (context.getEcmaScriptVersion() >= 11 && targetDesc.isDataDescriptor() && !targetDesc.getConfigurable() && targetDesc.getWritable() && desc.hasWritable() && !desc.getWritable()) {
                throw Errors.createTypeError("'defineProperty' on proxy: trap returned truish for defining non-configurable property which cannot be non-writable, unless there exists a corresponding non-configurable, non-writable own property of the proxy target");
            }
        }
        return true;
    }

    @CompilerDirectives.TruffleBoundary
    private static PropertyDescriptor completePropertyDescriptor(PropertyDescriptor desc) {
        if (desc.isGenericDescriptor() || desc.isDataDescriptor()) {
            if (!desc.hasValue()) {
                desc.setValue(Undefined.instance);
            }
            if (!desc.hasWritable()) {
                desc.setWritable(false);
            }
        } else {
            if (!desc.hasGet()) {
                desc.setGet(null);
            }
            if (!desc.hasSet()) {
                desc.setSet(null);
            }
        }
        if (!desc.hasEnumerable()) {
            desc.setEnumerable(false);
        }
        if (!desc.hasConfigurable()) {
            desc.setConfigurable(false);
        }
        return desc;
    }

    private static boolean isCompatiblePropertyDescriptor(boolean extensibleTarget, PropertyDescriptor desc, PropertyDescriptor current) {
        return DefinePropertyUtil.isCompatiblePropertyDescriptor(extensibleTarget, desc, current);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean preventExtensions(JSDynamicObject thisObj, boolean doThrow) {
        boolean targetIsExtensible;
        JSDynamicObject handler = JSProxy.getHandlerChecked(thisObj);
        Object target = JSProxy.getTarget(thisObj);
        Object preventExtensionsFn = JSProxy.getTrapFromObject(handler, PREVENT_EXTENSIONS);
        if (preventExtensionsFn == Undefined.instance) {
            if (JSDynamicObject.isJSDynamicObject(target)) {
                return JSObject.preventExtensions((JSDynamicObject)target, doThrow);
            }
            return true;
        }
        Object returnValue = JSRuntime.call(preventExtensionsFn, handler, new Object[]{target});
        boolean booleanTrapResult = JSRuntime.toBoolean(returnValue);
        if (booleanTrapResult && JSDynamicObject.isJSDynamicObject(target) && (targetIsExtensible = JSObject.isExtensible((JSDynamicObject)target))) {
            throw Errors.createTypeError("target is extensible");
        }
        if (doThrow && !booleanTrapResult) {
            throw Errors.createTypeError("'preventExtensions' on proxy: trap returned falsish");
        }
        return booleanTrapResult;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean isExtensible(JSDynamicObject thisObj) {
        JSDynamicObject handler = JSProxy.getHandlerChecked(thisObj);
        Object target = JSProxy.getTarget(thisObj);
        Object isExtensibleFn = JSProxy.getTrapFromObject(handler, IS_EXTENSIBLE);
        if (isExtensibleFn == Undefined.instance) {
            if (JSDynamicObject.isJSDynamicObject(target)) {
                return JSObject.isExtensible((JSDynamicObject)target);
            }
            return true;
        }
        Object returnValue = JSRuntime.call(isExtensibleFn, handler, new Object[]{target});
        boolean booleanTrapResult = JSRuntime.toBoolean(returnValue);
        if (!JSDynamicObject.isJSDynamicObject(target)) {
            return booleanTrapResult;
        }
        boolean targetResult = JSObject.isExtensible((JSDynamicObject)target);
        if (booleanTrapResult != targetResult) {
            throw Errors.createTypeErrorSameResultExpected();
        }
        return booleanTrapResult;
    }

    @Override
    public TruffleString getBuiltinToStringTag(JSDynamicObject object) {
        Object targetNonProxy = JSProxy.getTargetNonProxy(object);
        if (JSDynamicObject.isJSDynamicObject(targetNonProxy)) {
            if (JSArray.isJSArray(targetNonProxy)) {
                return JSArray.CLASS_NAME;
            }
            if (JSFunction.isJSFunction(targetNonProxy)) {
                return JSFunction.CLASS_NAME;
            }
            return Strings.UC_OBJECT;
        }
        InteropLibrary interop = InteropLibrary.getUncached(targetNonProxy);
        if (interop.hasArrayElements(targetNonProxy)) {
            return JSArray.CLASS_NAME;
        }
        if (interop.isExecutable(targetNonProxy) || interop.isInstantiable(targetNonProxy)) {
            return JSFunction.CLASS_NAME;
        }
        return Strings.UC_OBJECT;
    }

    @Override
    public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
        if (JavaScriptLanguage.get(null).getJSContext().isOptionNashornCompatibilityMode()) {
            return this.defaultToString(obj);
        }
        Object target = JSProxy.getTarget(obj);
        JSDynamicObject handler = JSProxy.getHandler(obj);
        return Strings.concatAll(Strings.PROXY_PAREN, JSRuntime.toDisplayStringInner(target, allowSideEffects, format, depth, obj), Strings.COMMA_SPC, JSRuntime.toDisplayStringInner(handler, allowSideEffects, format, depth, obj), Strings.PAREN_CLOSE);
    }

    @Override
    public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
        Shape initialShape = JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
        return initialShape;
    }

    public static JSConstructor createConstructor(JSRealm realm) {
        JSFunctionObject proxyConstructor = realm.lookupFunction(ConstructorBuiltins.BUILTINS, CLASS_NAME);
        JSObjectUtil.putFunctionsFromContainer(realm, proxyConstructor, ProxyFunctionBuiltins.BUILTINS);
        JSObject dummyPrototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
        return new JSConstructor(proxyConstructor, dummyPrototype);
    }

    public static Object getTrapFromObject(JSDynamicObject maybeHandler, TruffleString trapName) {
        Object method = JSObject.get(maybeHandler, trapName);
        if (method == Undefined.instance || method == Null.instance) {
            return Undefined.instance;
        }
        if (!JSRuntime.isCallable(method)) {
            throw Errors.createTypeErrorNotAFunction(method);
        }
        return method;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public JSDynamicObject getPrototypeOf(JSDynamicObject thisObj) {
        JSDynamicObject handler = JSProxy.getHandlerChecked(thisObj);
        Object target = JSProxy.getTarget(thisObj);
        Object getPrototypeOfFn = JSProxy.getTrapFromObject(handler, GET_PROTOTYPE_OF);
        if (getPrototypeOfFn == Undefined.instance) {
            if (JSDynamicObject.isJSDynamicObject(target)) {
                return JSObject.getPrototype((JSDynamicObject)target);
            }
            return Null.instance;
        }
        Object handlerProto = JSRuntime.call(getPrototypeOfFn, handler, new Object[]{target});
        if (!JSDynamicObject.isJSDynamicObject(handlerProto) || handlerProto == Undefined.instance) {
            throw Errors.createTypeError("object or null expected");
        }
        JSDynamicObject handlerProtoObj = (JSDynamicObject)handlerProto;
        if (!JSDynamicObject.isJSDynamicObject(target)) {
            return handlerProtoObj;
        }
        boolean extensibleTarget = JSObject.isExtensible((JSDynamicObject)target);
        if (extensibleTarget) {
            return handlerProtoObj;
        }
        JSDynamicObject targetProtoObj = JSObject.getPrototype((JSDynamicObject)target);
        if (handlerProtoObj != targetProtoObj) {
            throw Errors.createTypeErrorSameResultExpected();
        }
        return handlerProtoObj;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean setPrototypeOf(JSDynamicObject thisObj, JSDynamicObject newPrototype) {
        assert (JSObjectUtil.isValidPrototype(newPrototype));
        JSDynamicObject handler = JSProxy.getHandlerChecked(thisObj);
        Object target = JSProxy.getTarget(thisObj);
        Object setPrototypeOfFn = JSProxy.getTrapFromObject(handler, SET_PROTOTYPE_OF);
        if (setPrototypeOfFn == Undefined.instance) {
            if (JSDynamicObject.isJSDynamicObject(target)) {
                return JSObject.setPrototype((JSDynamicObject)target, newPrototype);
            }
            return true;
        }
        Object returnValue = JSRuntime.call(setPrototypeOfFn, handler, new Object[]{target, newPrototype});
        boolean booleanTrapResult = JSRuntime.toBoolean(returnValue);
        if (!booleanTrapResult) {
            return false;
        }
        if (!JSDynamicObject.isJSDynamicObject(target)) {
            return true;
        }
        boolean targetIsExtensible = JSObject.isExtensible((JSDynamicObject)target);
        if (targetIsExtensible) {
            return true;
        }
        JSDynamicObject targetProto = JSObject.getPrototype((JSDynamicObject)target);
        if (newPrototype != targetProto) {
            throw Errors.createTypeErrorSameResultExpected();
        }
        return true;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public List<Object> getOwnPropertyKeys(JSDynamicObject thisObj, boolean strings, boolean symbols) {
        return JSProxy.filterOwnPropertyKeys(JSProxy.ownPropertyKeysProxy(thisObj), strings, symbols);
    }

    private static List<Object> ownPropertyKeysProxy(JSDynamicObject thisObj) {
        JSDynamicObject handler = JSProxy.getHandlerChecked(thisObj);
        Object target = JSProxy.getTarget(thisObj);
        Object ownKeysFn = JSProxy.getTrapFromObject(handler, OWN_KEYS);
        if (ownKeysFn == Undefined.instance) {
            if (JSDynamicObject.isJSDynamicObject(target)) {
                return JSObject.ownPropertyKeys((JSDynamicObject)target);
            }
            return JSInteropUtil.keys(target);
        }
        Object trapResultArray = JSRuntime.call(ownKeysFn, handler, new Object[]{target});
        List<Object> trapResult = JSRuntime.createListFromArrayLikeAllowSymbolString(trapResultArray);
        if (!JSDynamicObject.isJSDynamicObject(target)) {
            ArrayList<Object> uncheckedResultKeys = new ArrayList<Object>();
            Boundaries.listAddAll(uncheckedResultKeys, trapResult);
            return uncheckedResultKeys;
        }
        if (handler instanceof JSUncheckedProxyHandlerObject) {
            return trapResult;
        }
        JSContext context = JSObject.getJSContext(thisObj);
        if (context.getEcmaScriptVersion() >= 9 && JSProxy.containsDuplicateEntries(trapResult)) {
            throw Errors.createTypeError("trap result contains duplicate entries");
        }
        boolean extensibleTarget = JSObject.isExtensible((JSDynamicObject)target);
        List<Object> targetKeys = JSObject.ownPropertyKeys((JSDynamicObject)target);
        ArrayList targetConfigurableKeys = new ArrayList();
        ArrayList targetNonconfigurableKeys = new ArrayList();
        for (Object t : targetKeys) {
            PropertyDescriptor desc = JSObject.getOwnProperty((JSDynamicObject)target, t);
            if (desc != null && !desc.getConfigurable()) {
                Boundaries.listAdd(targetNonconfigurableKeys, t);
                continue;
            }
            Boundaries.listAdd(targetConfigurableKeys, t);
        }
        if (extensibleTarget && targetNonconfigurableKeys.isEmpty()) {
            return trapResult;
        }
        ArrayList uncheckedResultKeys = new ArrayList();
        Boundaries.listAddAll(uncheckedResultKeys, trapResult);
        assert (trapResult.size() == uncheckedResultKeys.size());
        for (Object key : targetNonconfigurableKeys) {
            if (!uncheckedResultKeys.contains(key)) {
                throw Errors.createTypeErrorOwnKeysTrapMissingKey(key);
            }
            while (uncheckedResultKeys.remove(key)) {
            }
        }
        if (extensibleTarget) {
            return trapResult;
        }
        for (Object key : targetConfigurableKeys) {
            if (!uncheckedResultKeys.contains(key)) {
                throw Errors.createTypeErrorOwnKeysTrapMissingKey(key);
            }
            while (uncheckedResultKeys.remove(key)) {
            }
        }
        if (!uncheckedResultKeys.isEmpty()) {
            throw Errors.createTypeError("'ownKeys' on proxy: trap returned extra keys but proxy target is non-extensible");
        }
        return trapResult;
    }

    @CompilerDirectives.TruffleBoundary
    private static boolean containsDuplicateEntries(List<Object> trapResult) {
        HashSet<Object> set2 = new HashSet<Object>();
        for (Object entry : trapResult) {
            if (set2.add(entry)) continue;
            return true;
        }
        return false;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public PropertyDescriptor getOwnProperty(JSDynamicObject thisObj, Object key) {
        assert (JSRuntime.isPropertyKey(key));
        JSDynamicObject handler = JSProxy.getHandlerChecked(thisObj);
        Object target = JSProxy.getTarget(thisObj);
        Object getOwnPropertyFn = JSProxy.getTrapFromObject(handler, GET_OWN_PROPERTY_DESCRIPTOR);
        if (getOwnPropertyFn == Undefined.instance) {
            if (JSDynamicObject.isJSDynamicObject(target)) {
                return JSObject.getOwnProperty((JSDynamicObject)target, key);
            }
            if (Strings.isTString(key)) {
                return JSInteropUtil.getOwnProperty(target, (TruffleString)key);
            }
            assert (key instanceof Symbol);
            return null;
        }
        Object trapResultObj = JSProxy.checkTrapReturnValue(JSRuntime.call(getOwnPropertyFn, handler, new Object[]{target, key}));
        if (!JSDynamicObject.isJSDynamicObject(target)) {
            return JSRuntime.toPropertyDescriptor(trapResultObj);
        }
        PropertyDescriptor targetDesc = JSObject.getOwnProperty((JSDynamicObject)target, key);
        if (trapResultObj == Undefined.instance) {
            if (targetDesc == null) {
                return null;
            }
            if (targetDesc.hasConfigurable() && !targetDesc.getConfigurable()) {
                throw Errors.createTypeErrorConfigurableExpected();
            }
            boolean isExtensible = JSObject.isExtensible((JSDynamicObject)target);
            if (!isExtensible) {
                throw Errors.createTypeErrorProxyTargetNotExtensible();
            }
            return null;
        }
        boolean extensibleTarget = JSObject.isExtensible((JSDynamicObject)target);
        PropertyDescriptor resultDesc = JSRuntime.toPropertyDescriptor(trapResultObj);
        JSProxy.completePropertyDescriptor(resultDesc);
        if (handler instanceof JSUncheckedProxyHandlerObject) {
            return resultDesc;
        }
        boolean valid = JSProxy.isCompatiblePropertyDescriptor(extensibleTarget, resultDesc, targetDesc);
        if (!valid) {
            throw Errors.createTypeError("not a valid descriptor");
        }
        if (!resultDesc.getConfigurable()) {
            if (targetDesc == null || targetDesc.hasConfigurable() && targetDesc.getConfigurable()) {
                throw Errors.createTypeErrorFormat("'getOwnPropertyDescriptor' on proxy: trap reported non-configurability for property '%s' which is either non-existent or configurable in the proxy target", key);
            }
            JSContext context = JSObject.getJSContext(thisObj);
            if (context.getEcmaScriptVersion() >= 11 && resultDesc.hasWritable() && !resultDesc.getWritable() && targetDesc.getWritable()) {
                throw Errors.createTypeError("target is missing the corresponding non-configurable and non-writable own property");
            }
        }
        return resultDesc;
    }

    public static boolean isRevoked(JSDynamicObject proxy) {
        assert (JSProxy.isJSProxy(proxy)) : "Only proxy objects can be revoked";
        return JSProxy.getHandler(proxy) == Null.instance;
    }

    public static Object checkTrapReturnValue(Object trapResult) {
        if (JSDynamicObject.isJSDynamicObject(trapResult) || trapResult == Undefined.instance) {
            return trapResult;
        }
        throw Errors.createTypeError("proxy must return an object");
    }

    @CompilerDirectives.TruffleBoundary
    public static Object call(JSDynamicObject proxyObj, Object holder, Object[] arguments) {
        JSDynamicObject handler = JSProxy.getHandlerChecked(proxyObj);
        Object target = JSProxy.getTarget(proxyObj);
        Object trap = JSProxy.getTrapFromObject(handler, APPLY);
        if (trap == Undefined.instance) {
            return JSRuntime.call(target, holder, arguments);
        }
        JSContext ctx = JSObject.getJSContext(proxyObj);
        return JSRuntime.call(trap, handler, new Object[]{target, holder, JSArray.createConstant(ctx, JSRealm.get(null), arguments)});
    }

    @CompilerDirectives.TruffleBoundary
    public static Object construct(JSDynamicObject proxyObj, Object[] arguments) {
        if (!JSRuntime.isConstructorProxy(proxyObj)) {
            throw Errors.createTypeErrorNotAFunction(proxyObj);
        }
        JSDynamicObject handler = JSProxy.getHandlerChecked(proxyObj);
        Object target = JSProxy.getTarget(proxyObj);
        Object trap = JSProxy.getTrapFromObject(handler, CONSTRUCT);
        JSDynamicObject newTarget = proxyObj;
        if (trap == Undefined.instance) {
            return JSRuntime.construct(target, arguments);
        }
        JSContext ctx = JSObject.getJSContext(proxyObj);
        Object result = JSRuntime.call(trap, handler, new Object[]{target, JSArray.createConstant(ctx, JSRealm.get(null), arguments), newTarget});
        if (!JSRuntime.isObject(result)) {
            throw Errors.createTypeErrorNotAnObject(result);
        }
        return result;
    }

    @Override
    public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
        return realm.getProxyPrototype();
    }

    public static JSFunctionData createProxyCallFunctionData(JSContext ctx) {
        return ctx.getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.ProxyCall, c -> {
            RootCallTarget callTarget = new ProxyCallRootNode((JSContext)c, false, false).getCallTarget();
            RootCallTarget constructTarget = new ProxyCallRootNode((JSContext)c, true, false).getCallTarget();
            RootCallTarget constructNewTarget = new ProxyCallRootNode((JSContext)c, true, true).getCallTarget();
            return JSFunctionData.create(c, callTarget, constructTarget, constructNewTarget, 0, PROXY_CALL, 0);
        });
    }

    private static final class ProxyCallRootNode
    extends JavaScriptRootNode {
        @Node.Child
        JSProxyCallNode proxyCallNode;

        ProxyCallRootNode(JSContext context, boolean isNew, boolean isNewTarget) {
            this.proxyCallNode = JSProxyCallNode.create(context, isNew, isNewTarget);
        }

        @Override
        public Object execute(VirtualFrame frame) {
            return this.proxyCallNode.execute(frame.getArguments());
        }

        @Override
        public boolean isInternal() {
            return true;
        }
    }
}

