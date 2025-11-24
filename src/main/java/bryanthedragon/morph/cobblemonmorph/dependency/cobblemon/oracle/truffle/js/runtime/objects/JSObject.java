
package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.nodes.interop.JSInteropGetIteratorNode;
import com.oracle.truffle.js.nodes.interop.JSInteropInvokeNode;
import com.oracle.truffle.js.nodes.interop.KeyInfoNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSArgumentsArray;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBase;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSObjectPrototype;
import com.oracle.truffle.js.runtime.builtins.JSTypedArrayObject;
import com.oracle.truffle.js.runtime.interop.InteropArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import java.util.ArrayList;
import java.util.List;

@ExportLibrary(value=InteropLibrary.class)
public abstract class JSObject
extends JSDynamicObject {
    public static final TruffleString CONSTRUCTOR = Strings.constant("constructor");
    public static final TruffleString PROTOTYPE = Strings.constant("prototype");
    public static final TruffleString PROTO = Strings.constant("__proto__");
    public static final HiddenKey HIDDEN_PROTO = new HiddenKey("[[Prototype]]");
    public static final TruffleString NO_SUCH_PROPERTY_NAME = Strings.constant("__noSuchProperty__");
    public static final TruffleString NO_SUCH_METHOD_NAME = Strings.constant("__noSuchMethod__");
    protected static final String[] EMPTY_STRING_ARRAY = new String[0];

    protected JSObject(Shape shape) {
        super(shape);
    }

    protected JSObject copyWithoutProperties(Shape shape) {
        throw Errors.notImplemented("copy");
    }

    public static boolean isJSObject(Object object) {
        return JSRuntime.isObject(object);
    }

    @ExportMessage
    public final boolean hasMembers() {
        return true;
    }

    @CompilerDirectives.TruffleBoundary
    protected static String[] filterEnumerableNames(JSDynamicObject target, Iterable<Object> ownKeys, JSClass jsclass) {
        ArrayList<String> names = new ArrayList<String>();
        for (Object obj : ownKeys) {
            PropertyDescriptor desc;
            if (!Strings.isTString(obj) || JSRuntime.isArrayIndex(obj) || (desc = jsclass.getOwnProperty(target, obj)) == null || !desc.getEnumerable()) continue;
            names.add(Strings.toJavaString((TruffleString)obj));
        }
        return names.toArray(EMPTY_STRING_ARRAY);
    }

    public static JavaScriptLanguage language(InteropLibrary node) {
        return JavaScriptLanguage.get(node);
    }

    @ExportMessage
    public final Object readMember(String key, @CachedLibrary(value="this") InteropLibrary self, @Cached(value="create(language(self).getJSContext())", uncached="getUncachedRead()") ReadElementNode readNode, @Cached(value="language(self).bindMemberFunctions()", allowUncached=true) boolean bindMemberFunctions, @Cached @Cached.Exclusive ExportValueNode exportNode) throws UnknownIdentifierException {
        TruffleString tStringKey = Strings.fromJavaString(key);
        JSObject target = this;
        Object result = readNode == null ? JSObject.getOrDefault((JSDynamicObject)target, tStringKey, (Object)target, null) : readNode.executeWithTargetAndIndexOrDefault(target, tStringKey, null);
        if (result == null) {
            throw UnknownIdentifierException.create(key);
        }
        return exportNode.execute(result, target, bindMemberFunctions);
    }

    @ExportMessage
    public final boolean isMemberReadable(String key, @Cached.Shared(value="keyInfo") @Cached KeyInfoNode keyInfo) {
        return keyInfo.execute(this, key, 1);
    }

    @ExportMessage
    public final void writeMember(String key, Object value2, @Cached.Shared(value="keyInfo") @Cached KeyInfoNode keyInfo, @Cached ImportValueNode castValueNode, @Cached(value="createCachedInterop()", uncached="getUncachedWrite()") WriteElementNode writeNode) throws UnknownIdentifierException, UnsupportedMessageException {
        JSObject target = this;
        if (this.testIntegrityLevel(true)) {
            throw UnsupportedMessageException.create();
        }
        if (!keyInfo.execute(target, key, 6)) {
            throw UnknownIdentifierException.create(key);
        }
        TruffleString tStringKey = Strings.fromJavaString(key);
        Object importedValue = castValueNode.executeWithTarget(value2);
        if (writeNode == null) {
            JSObject.set((JSDynamicObject)target, tStringKey, importedValue, true, null);
        } else {
            writeNode.executeWithTargetAndIndexAndValue((Object)target, tStringKey, importedValue);
        }
    }

    @ExportMessage
    public final boolean isMemberModifiable(String key, @Cached.Shared(value="keyInfo") @Cached KeyInfoNode keyInfo) {
        return keyInfo.execute(this, key, 2);
    }

    @ExportMessage
    public final boolean isMemberInsertable(String key, @Cached.Shared(value="keyInfo") @Cached KeyInfoNode keyInfo) {
        return keyInfo.execute(this, key, 4);
    }

    @ExportMessage
    public final void removeMember(String key) throws UnsupportedMessageException {
        if (this.testIntegrityLevel(false)) {
            throw UnsupportedMessageException.create();
        }
        JSObject.delete((JSDynamicObject)this, Strings.fromJavaString(key), true);
    }

    @ExportMessage
    public final boolean isMemberRemovable(String key, @Cached.Shared(value="keyInfo") @Cached KeyInfoNode keyInfo) {
        return keyInfo.execute(this, key, 16);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @ExportMessage
    public final Object invokeMember(String id, Object[] args, @CachedLibrary(value="this") InteropLibrary self, @Cached JSInteropInvokeNode callNode, @Cached @Cached.Exclusive ExportValueNode exportNode) throws UnsupportedMessageException, UnknownIdentifierException {
        JavaScriptLanguage language = JavaScriptLanguage.get(self);
        JSRealm realm = JSRealm.get(self);
        language.interopBoundaryEnter(realm);
        try {
            Object result = callNode.execute(this, Strings.fromJavaString(id), args);
            Object object = exportNode.execute(result);
            return object;
        }
        finally {
            language.interopBoundaryExit(realm);
        }
    }

    @ExportMessage
    public final boolean isMemberInvocable(String key, @Cached.Shared(value="keyInfo") @Cached KeyInfoNode keyInfo) {
        return keyInfo.execute(this, key, 8);
    }

    @ExportMessage
    public final boolean hasMemberReadSideEffects(String key, @Cached.Shared(value="keyInfo") @Cached KeyInfoNode keyInfo) {
        return keyInfo.execute(this, key, 32);
    }

    @ExportMessage
    public final boolean hasMemberWriteSideEffects(String key, @Cached.Shared(value="keyInfo") @Cached KeyInfoNode keyInfo) {
        return keyInfo.execute(this, key, 64);
    }

    @ExportMessage
    public boolean hasIterator(@CachedLibrary(value="this") InteropLibrary self, @Cached @Cached.Shared(value="getIterator") JSInteropGetIteratorNode getIteratorNode) {
        JavaScriptLanguage language = JavaScriptLanguage.get(self);
        return getIteratorNode.hasIterator(this, language);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @ExportMessage
    public Object getIterator(@CachedLibrary(value="this") InteropLibrary self, @Cached @Cached.Shared(value="getIterator") JSInteropGetIteratorNode getIteratorNode) throws UnsupportedMessageException {
        JavaScriptLanguage language = JavaScriptLanguage.get(self);
        JSRealm realm = JSRealm.get(self);
        language.interopBoundaryEnter(realm);
        try {
            Object object = getIteratorNode.getIterator(this, language);
            return object;
        }
        finally {
            language.interopBoundaryExit(realm);
        }
    }

    @ExportMessage
    public final boolean hasLanguage() {
        return true;
    }

    @ExportMessage
    public final Class<? extends TruffleLanguage<?>> getLanguage() {
        return JavaScriptLanguage.class;
    }

    @ExportMessage
    public final Object toDisplayString(boolean allowSideEffects) {
        return JSRuntime.toDisplayString(this, allowSideEffects);
    }

    public static ReadElementNode getUncachedRead() {
        return null;
    }

    public static WriteElementNode getUncachedWrite() {
        return null;
    }

    public static JSClass getJSClass(JSDynamicObject obj) {
        return JSShape.getJSClass(obj.getShape());
    }

    @CompilerDirectives.TruffleBoundary
    public static JSDynamicObject getPrototype(JSDynamicObject obj) {
        return JSObject.getJSClass(obj).getPrototypeOf(obj);
    }

    public static JSDynamicObject getPrototype(JSDynamicObject obj, JSClassProfile jsclassProfile) {
        return jsclassProfile.getJSClass(obj).getPrototypeOf(obj);
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean setPrototype(JSDynamicObject obj, JSDynamicObject newPrototype) {
        assert (newPrototype != null);
        return JSObject.getJSClass(obj).setPrototypeOf(obj, newPrototype);
    }

    public static boolean setPrototype(JSDynamicObject obj, JSDynamicObject newPrototype, JSClassProfile jsclassProfile) {
        assert (newPrototype != null);
        return jsclassProfile.getJSClass(obj).setPrototypeOf(obj, newPrototype);
    }

    @CompilerDirectives.TruffleBoundary
    public static Object get(JSDynamicObject obj, long index) {
        return JSObject.getJSClass(obj).get(obj, index);
    }

    @CompilerDirectives.TruffleBoundary
    public static Object get(JSDynamicObject obj, Object key) {
        assert (JSRuntime.isPropertyKey(key));
        return JSObject.getJSClass(obj).get(obj, key);
    }

    @CompilerDirectives.TruffleBoundary
    public static Object getMethod(JSDynamicObject obj, Object name) {
        assert (JSRuntime.isPropertyKey(name));
        Object result = JSRuntime.nullToUndefined(JSObject.getJSClass(obj).getMethodHelper(obj, obj, name, null));
        return result == Null.instance ? Undefined.instance : result;
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean set(JSDynamicObject obj, long index, Object value2) {
        return JSObject.set(obj, index, value2, false, null);
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean set(JSDynamicObject obj, Object key, Object value2) {
        return JSObject.set(obj, key, value2, false, null);
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean set(JSDynamicObject obj, long index, Object value2, boolean isStrict, Node encapsulatingNode) {
        return JSObject.getJSClass(obj).set(obj, index, value2, (Object)obj, isStrict, encapsulatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean set(JSDynamicObject obj, Object key, Object value2, boolean isStrict, Node encapsulatingNode) {
        assert (JSRuntime.isPropertyKey(key));
        assert (!(value2 instanceof String));
        return JSObject.getJSClass(obj).set(obj, key, value2, (Object)obj, isStrict, encapsulatingNode);
    }

    public static boolean setWithReceiver(JSDynamicObject obj, Object key, Object value2, Object receiver, boolean isStrict, JSClassProfile classProfile, Node encapsulatingNode) {
        assert (JSRuntime.isPropertyKey(key));
        assert (!(value2 instanceof String));
        return classProfile.getJSClass(obj).set(obj, key, value2, receiver, isStrict, encapsulatingNode);
    }

    public static boolean setWithReceiver(JSDynamicObject obj, long index, Object value2, Object receiver, boolean isStrict, JSClassProfile classProfile, Node encapsulatingNode) {
        return classProfile.getJSClass(obj).set(obj, index, value2, receiver, isStrict, encapsulatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean delete(JSDynamicObject obj, long index) {
        return JSObject.getJSClass(obj).delete(obj, index, false);
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean delete(JSDynamicObject obj, long index, boolean isStrict) {
        return JSObject.getJSClass(obj).delete(obj, index, isStrict);
    }

    public static boolean delete(JSDynamicObject obj, long index, boolean isStrict, JSClassProfile classProfile) {
        return classProfile.getJSClass(obj).delete(obj, index, isStrict);
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean delete(JSDynamicObject obj, Object key) {
        return JSObject.delete(obj, key, false);
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean delete(JSDynamicObject obj, Object key, boolean isStrict) {
        assert (JSRuntime.isPropertyKey(key));
        return JSObject.getJSClass(obj).delete(obj, key, isStrict);
    }

    public static boolean delete(JSDynamicObject obj, Object key, boolean isStrict, JSClassProfile classProfile) {
        assert (JSRuntime.isPropertyKey(key));
        return classProfile.getJSClass(obj).delete(obj, key, isStrict);
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean hasOwnProperty(JSDynamicObject obj, long index) {
        return JSObject.getJSClass(obj).hasOwnProperty(obj, index);
    }

    public static boolean hasOwnProperty(JSDynamicObject obj, long index, JSClassProfile classProfile) {
        return classProfile.getJSClass(obj).hasOwnProperty(obj, index);
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean hasOwnProperty(JSDynamicObject obj, Object key) {
        assert (JSRuntime.isPropertyKey(key));
        return JSObject.getJSClass(obj).hasOwnProperty(obj, key);
    }

    public static boolean hasOwnProperty(JSDynamicObject obj, Object key, JSClassProfile classProfile) {
        assert (JSRuntime.isPropertyKey(key));
        return classProfile.getJSClass(obj).hasOwnProperty(obj, key);
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean hasProperty(JSDynamicObject obj, long index) {
        return JSObject.getJSClass(obj).hasProperty(obj, index);
    }

    public static boolean hasProperty(JSDynamicObject obj, long index, JSClassProfile classProfile) {
        return classProfile.getJSClass(obj).hasProperty(obj, index);
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean hasProperty(JSDynamicObject obj, Object key) {
        assert (JSRuntime.isPropertyKey(key));
        return JSObject.getJSClass(obj).hasProperty(obj, key);
    }

    public static boolean hasProperty(JSDynamicObject obj, Object key, JSClassProfile classProfile) {
        assert (JSRuntime.isPropertyKey(key));
        return classProfile.getJSClass(obj).hasProperty(obj, key);
    }

    public static PropertyDescriptor getOwnProperty(JSDynamicObject obj, Object key) {
        assert (JSRuntime.isPropertyKey(key));
        return JSObject.getJSClass(obj).getOwnProperty(obj, key);
    }

    public static PropertyDescriptor getOwnProperty(JSDynamicObject obj, Object key, JSClassProfile classProfile) {
        assert (JSRuntime.isPropertyKey(key));
        return classProfile.getJSClass(obj).getOwnProperty(obj, key);
    }

    public static List<Object> ownPropertyKeys(JSDynamicObject obj) {
        return JSObject.getJSClass(obj).ownPropertyKeys(obj);
    }

    public static List<Object> ownPropertyKeys(JSDynamicObject obj, JSClassProfile classProfile) {
        return classProfile.getJSClass(obj).ownPropertyKeys(obj);
    }

    @CompilerDirectives.TruffleBoundary
    public static List<TruffleString> enumerableOwnNames(JSDynamicObject thisObj) {
        JSClass jsclass = JSObject.getJSClass(thisObj);
        if (jsclass.hasOnlyShapeProperties(thisObj)) {
            return JSShape.getEnumerablePropertyNames(thisObj.getShape());
        }
        List<Object> ownKeys = jsclass.ownPropertyKeys(thisObj);
        ArrayList<TruffleString> names = new ArrayList<TruffleString>();
        for (Object t : ownKeys) {
            PropertyDescriptor desc;
            if (!Strings.isTString(t) || (desc = jsclass.getOwnProperty(thisObj, t)) == null || !desc.getEnumerable()) continue;
            names.add((TruffleString)t);
        }
        return names;
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean defineOwnProperty(JSDynamicObject obj, Object key, PropertyDescriptor desc) {
        assert (JSRuntime.isPropertyKey(key));
        return JSObject.getJSClass(obj).defineOwnProperty(obj, key, desc, false);
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean defineOwnProperty(JSDynamicObject obj, Object key, PropertyDescriptor desc, boolean doThrow) {
        assert (JSRuntime.isPropertyKey(key));
        return JSObject.getJSClass(obj).defineOwnProperty(obj, key, desc, doThrow);
    }

    public static Object get(JSDynamicObject obj, Object key, JSClassProfile jsclassProfile) {
        assert (JSRuntime.isPropertyKey(key));
        return jsclassProfile.getJSClass(obj).get(obj, key);
    }

    public static Object get(JSDynamicObject obj, long index, JSClassProfile jsclassProfile) {
        return jsclassProfile.getJSClass(obj).get(obj, index);
    }

    public static Object getOrDefault(JSDynamicObject obj, Object key, Object receiver, Object defaultValue, JSClassProfile jsclassProfile, Node encapsulatingNode) {
        assert (JSRuntime.isPropertyKey(key));
        Object result = jsclassProfile.getJSClass(obj).getHelper(obj, receiver, key, encapsulatingNode);
        return result == null ? defaultValue : result;
    }

    public static Object getOrDefault(JSDynamicObject obj, long index, Object receiver, Object defaultValue, JSClassProfile jsclassProfile, Node encapsulatingNode) {
        Object result = jsclassProfile.getJSClass(obj).getHelper(obj, receiver, index, encapsulatingNode);
        return result == null ? defaultValue : result;
    }

    public static Object getOrDefault(JSDynamicObject obj, Object key, Object receiver, Object defaultValue) {
        return JSObject.getOrDefault(obj, key, receiver, defaultValue, JSClassProfile.getUncached(), null);
    }

    public static Object getOrDefault(JSDynamicObject obj, long index, Object receiver, Object defaultValue) {
        return JSObject.getOrDefault(obj, index, receiver, defaultValue, JSClassProfile.getUncached(), null);
    }

    @CompilerDirectives.TruffleBoundary
    public static Object getWithReceiver(JSDynamicObject obj, Object key, Object receiver, Node encapsulatingNode) {
        assert (JSRuntime.isPropertyKey(key));
        Object result = JSObject.getJSClass(obj).getHelper(obj, receiver, key, encapsulatingNode);
        return result == null ? Undefined.instance : result;
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString defaultToString(JSDynamicObject obj) {
        return JSObject.getJSClass(obj).defaultToString(obj);
    }

    @CompilerDirectives.TruffleBoundary
    public static Object toPrimitive(JSDynamicObject obj, JSToPrimitiveNode.Hint hint) {
        assert (obj != Null.instance && obj != Undefined.instance);
        Object exoticToPrim = JSObject.getMethod(obj, Symbol.SYMBOL_TO_PRIMITIVE);
        if (exoticToPrim != Undefined.instance) {
            Object result = JSRuntime.call(exoticToPrim, obj, new Object[]{hint.getHintName()});
            if (JSRuntime.isObject(result)) {
                throw Errors.createTypeError("[Symbol.toPrimitive] method returned a non-primitive object");
            }
            return result;
        }
        return JSObject.ordinaryToPrimitive(obj, hint == JSToPrimitiveNode.Hint.Default ? JSToPrimitiveNode.Hint.Number : hint);
    }

    @CompilerDirectives.TruffleBoundary
    public static Object toPrimitive(JSDynamicObject obj) {
        return JSObject.toPrimitive(obj, JSToPrimitiveNode.Hint.Default);
    }

    @CompilerDirectives.TruffleBoundary
    public static Object ordinaryToPrimitive(JSDynamicObject obj, JSToPrimitiveNode.Hint hint) {
        assert (JSRuntime.isObject(obj));
        assert (hint == JSToPrimitiveNode.Hint.String || hint == JSToPrimitiveNode.Hint.Number);
        Object[] methodNames = hint == JSToPrimitiveNode.Hint.String ? new Object[]{Strings.TO_STRING, Strings.VALUE_OF} : new Object[]{Strings.VALUE_OF, Strings.TO_STRING};
        for (Object name : methodNames) {
            Object result;
            Object method = JSObject.getMethod(obj, name);
            if (!JSRuntime.isCallable(method) || JSRuntime.isObject(result = JSRuntime.call(method, obj, new Object[0]))) continue;
            return result;
        }
        throw Errors.createTypeErrorCannotConvertToPrimitiveValue();
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean preventExtensions(JSDynamicObject obj) {
        return JSObject.preventExtensions(obj, false);
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean preventExtensions(JSDynamicObject obj, boolean doThrow) {
        return JSObject.getJSClass(obj).preventExtensions(obj, doThrow);
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean isExtensible(JSDynamicObject obj) {
        return JSObject.getJSClass(obj).isExtensible(obj);
    }

    public static boolean isExtensible(JSDynamicObject obj, JSClassProfile classProfile) {
        return classProfile.getJSClass(obj).isExtensible(obj);
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString getClassName(JSDynamicObject obj) {
        return JSObject.getJSClass(obj).getClassName(obj);
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean isFrozen(JSDynamicObject obj) {
        return JSObject.testIntegrityLevel(obj, true);
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean isSealed(JSDynamicObject obj) {
        return JSObject.testIntegrityLevel(obj, false);
    }

    public static ScriptArray getArray(JSDynamicObject obj) {
        assert (JSObject.hasArray(obj));
        if (obj instanceof JSArrayBase) {
            return ((JSArrayBase)obj).getArrayType();
        }
        return ((JSTypedArrayObject)obj).getArrayType();
    }

    public static void setArray(JSDynamicObject obj, ScriptArray array) {
        assert (JSObject.hasArray(obj));
        ((JSArrayBase)obj).setArrayType(array);
    }

    public static boolean hasArray(Object obj) {
        return JSArray.isJSArray(obj) || JSArgumentsArray.isJSArgumentsObject(obj) || JSArrayBufferView.isJSArrayBufferView(obj) || JSObjectPrototype.isJSObjectPrototype(obj);
    }

    public static JSContext getJSContext(JSDynamicObject obj) {
        return JSShape.getJSContext(obj.getShape());
    }

    @CompilerDirectives.TruffleBoundary(transferToInterpreterOnException=false)
    public static boolean testIntegrityLevel(JSDynamicObject obj, boolean frozen) {
        return JSObject.getJSClass(obj).testIntegrityLevel(obj, frozen);
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean setIntegrityLevel(JSDynamicObject obj, boolean freeze) {
        return JSObject.setIntegrityLevel(obj, freeze, false);
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean setIntegrityLevel(JSDynamicObject obj, boolean freeze, boolean doThrow) {
        return JSObject.getJSClass(obj).setIntegrityLevel(obj, freeze, doThrow);
    }

    @ExportMessage
    @ImportStatic(value={JSGuards.class, JSObject.class})
    public static abstract class GetMembers {
        @Specialization(guards={"cachedJSClass != null", "getJSClass(target) == cachedJSClass"})
        public static Object nonArrayCached(JSObject target, boolean internal, @Cached(value="getJSClass(target)") JSClass cachedJSClass) {
            return InteropArray.create(JSObject.enumerableOwnNames(target));
        }

        @Specialization(replaces={"nonArrayCached"})
        public static Object nonArrayUncached(JSObject target, boolean internal) {
            return InteropArray.create(JSObject.enumerableOwnNames(target));
        }
    }
}

