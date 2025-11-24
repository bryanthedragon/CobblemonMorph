
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSDictionary;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.objects.Accessor;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSProperty;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.PropertyProxy;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.DefinePropertyUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class JSNonProxy
extends JSClass {
    public static final TruffleString GET_SYMBOL_SPECIES_NAME = Strings.constant("get [Symbol.species]");

    protected JSNonProxy() {
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean defineOwnProperty(JSDynamicObject thisObj, Object key, PropertyDescriptor desc, boolean doThrow) {
        return DefinePropertyUtil.ordinaryDefineOwnProperty(thisObj, key, desc, doThrow);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Object getOwnHelper(JSDynamicObject store, Object thisObj, Object key, Node encapsulatingNode) {
        Property entry = DefinePropertyUtil.getPropertyByKey(store, key);
        if (entry != null) {
            return JSProperty.getValue(entry, store, thisObj, encapsulatingNode);
        }
        return null;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Object getOwnHelper(JSDynamicObject store, Object thisObj, long index, Node encapsulatingNode) {
        return this.getOwnHelper(store, thisObj, Strings.fromLong(index), encapsulatingNode);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Object getHelper(JSDynamicObject store, Object thisObj, Object key, Node encapsulatingNode) {
        Object value2 = this.getOwnHelper(store, thisObj, key, encapsulatingNode);
        if (value2 != null) {
            return value2;
        }
        return JSNonProxy.getPropertyHelperGeneric(thisObj, store, key, encapsulatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    private static Object getPropertyHelperGeneric(Object thisObj, JSDynamicObject store, Object key, Node encapsulatingNode) {
        JSDynamicObject prototype = JSObject.getPrototype(store);
        if (prototype != Null.instance) {
            return JSObject.getJSClass(prototype).getHelper(prototype, thisObj, key, encapsulatingNode);
        }
        return null;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Object getHelper(JSDynamicObject store, Object thisObj, long index, Node encapsulatingNode) {
        Object value2 = this.getOwnHelper(store, thisObj, index, encapsulatingNode);
        if (value2 != null) {
            return value2;
        }
        return JSNonProxy.getPropertyHelperGeneric(thisObj, store, index, encapsulatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    private static Object getPropertyHelperGeneric(Object thisObj, JSDynamicObject store, long index, Node encapsulatingNode) {
        JSDynamicObject prototype = JSObject.getPrototype(store);
        if (prototype != Null.instance) {
            return JSObject.getJSClass(prototype).getHelper(prototype, thisObj, index, encapsulatingNode);
        }
        return null;
    }

    @Override
    public Object getMethodHelper(JSDynamicObject store, Object thisObj, Object name, Node encapsulatingNode) {
        return this.getHelper(store, thisObj, name, encapsulatingNode);
    }

    @Override
    public List<Object> getOwnPropertyKeys(JSDynamicObject thisObj, boolean strings, boolean symbols) {
        return JSNonProxy.ordinaryOwnPropertyKeys(thisObj, strings, symbols);
    }

    public static List<Object> ordinaryOwnPropertyKeys(JSDynamicObject thisObj) {
        return JSNonProxy.ordinaryOwnPropertyKeys(thisObj, true, true);
    }

    @CompilerDirectives.TruffleBoundary
    protected static List<Object> ordinaryOwnPropertyKeys(JSDynamicObject thisObj, boolean strings, boolean symbols) {
        return JSShape.getPropertyKeyList(thisObj.getShape(), strings, symbols);
    }

    protected static List<Object> ordinaryOwnPropertyKeysSlow(JSDynamicObject thisObj, boolean strings, boolean symbols) {
        CompilerAsserts.neverPartOfCompilation();
        List<Object> keyList = thisObj.getShape().getKeyList();
        ArrayList<Object> list = new ArrayList<Object>(keyList.size());
        for (Object key : keyList) {
            if (!symbols && key instanceof Symbol || !strings && Strings.isTString(key)) continue;
            list.add(key);
        }
        Collections.sort(list, JSRuntime::comparePropertyKeys);
        return list;
    }

    @Override
    public boolean hasOnlyShapeProperties(JSDynamicObject obj) {
        return false;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean delete(JSDynamicObject thisObj, Object key, boolean isStrict) {
        return JSNonProxy.deletePropertyDefault(thisObj, key, isStrict);
    }

    protected static boolean deletePropertyDefault(JSDynamicObject object, Object key, boolean isStrict) {
        Property foundProperty = object.getShape().getProperty(key);
        if (foundProperty != null) {
            if (!JSProperty.isConfigurable(foundProperty)) {
                if (isStrict) {
                    throw Errors.createTypeErrorNotConfigurableProperty(key);
                }
                return false;
            }
            return Properties.removeKeyUncached(object, key);
        }
        return true;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean delete(JSDynamicObject thisObj, long index, boolean isStrict) {
        return JSNonProxy.deletePropertyDefault(thisObj, Strings.fromLong(index), isStrict);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean hasOwnProperty(JSDynamicObject thisObj, Object key) {
        return thisObj.getShape().hasProperty(key);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean hasOwnProperty(JSDynamicObject thisObj, long index) {
        return this.hasOwnProperty(thisObj, Strings.fromLong(index));
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean hasProperty(JSDynamicObject thisObj, long index) {
        if (this.hasOwnProperty(thisObj, index)) {
            return true;
        }
        if (JSObject.getPrototype(thisObj) != Null.instance) {
            return JSObject.hasProperty(JSObject.getPrototype(thisObj), index);
        }
        return false;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean hasProperty(JSDynamicObject thisObj, Object key) {
        if (this.hasOwnProperty(thisObj, key)) {
            return true;
        }
        JSDynamicObject prototype = JSObject.getPrototype(thisObj);
        if (prototype != Null.instance) {
            return JSObject.hasProperty(prototype, key);
        }
        return false;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean set(JSDynamicObject thisObj, long index, Object value2, Object receiver, boolean isStrict, Node encapsulatingNode) {
        return JSNonProxy.ordinarySetIndex(thisObj, index, value2, receiver, isStrict, encapsulatingNode);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean set(JSDynamicObject thisObj, Object key, Object value2, Object receiver, boolean isStrict, Node encapsulatingNode) {
        return JSNonProxy.ordinarySet(thisObj, key, value2, receiver, isStrict, encapsulatingNode);
    }

    protected static boolean ordinarySetIndex(JSDynamicObject thisObj, long index, Object value2, Object receiver, boolean isStrict, Node encapsulatingNode) {
        TruffleString key = Strings.fromLong(index);
        if (receiver != thisObj) {
            return JSNonProxy.ordinarySetWithReceiver(thisObj, key, value2, receiver, isStrict, encapsulatingNode);
        }
        Property entry = DefinePropertyUtil.getPropertyByKey(thisObj, key);
        if (entry != null) {
            return JSProperty.setValue(entry, thisObj, receiver, value2, isStrict, encapsulatingNode);
        }
        return JSNonProxy.setPropertySlow(thisObj, key, value2, receiver, isStrict, true, encapsulatingNode);
    }

    protected static boolean ordinarySet(JSDynamicObject thisObj, Object key, Object value2, Object receiver, boolean isStrict, Node encapsulatingNode) {
        if (receiver != thisObj) {
            return JSNonProxy.ordinarySetWithReceiver(thisObj, key, value2, receiver, isStrict, encapsulatingNode);
        }
        Property entry = DefinePropertyUtil.getPropertyByKey(thisObj, key);
        if (entry != null) {
            return JSProperty.setValue(entry, thisObj, receiver, value2, isStrict, encapsulatingNode);
        }
        return JSNonProxy.setPropertySlow(thisObj, key, value2, receiver, isStrict, false, encapsulatingNode);
    }

    protected static boolean ordinarySetWithReceiver(JSDynamicObject target, Object key, Object value2, Object receiver, boolean isStrict, Node encapsulatingNode) {
        assert (JSRuntime.isPropertyKey(key));
        PropertyDescriptor descriptor = JSObject.getOwnProperty(target, key);
        boolean result = JSNonProxy.performOrdinarySetWithOwnDescriptor(target, key, value2, receiver, descriptor, isStrict, encapsulatingNode);
        assert (!isStrict || result) : "should have thrown";
        return result;
    }

    @CompilerDirectives.TruffleBoundary
    protected static boolean performOrdinarySetWithOwnDescriptor(JSDynamicObject target, Object key, Object value2, Object receiver, PropertyDescriptor desc, boolean isStrict, Node encapsulatingNode) {
        PropertyDescriptor descriptor = desc;
        if (descriptor == null) {
            JSDynamicObject parent = JSObject.getPrototype(target);
            if (parent != Null.instance) {
                return JSObject.getJSClass(parent).set(parent, key, value2, receiver, isStrict, encapsulatingNode);
            }
            descriptor = PropertyDescriptor.undefinedDataDesc;
        }
        if (descriptor.isDataDescriptor()) {
            if (!descriptor.getWritable()) {
                if (isStrict) {
                    throw Errors.createTypeErrorNotWritableProperty(key, target);
                }
                return false;
            }
            if (!JSRuntime.isObject(receiver)) {
                if (isStrict) {
                    throw Errors.createTypeErrorSetNonObjectReceiver(receiver, key);
                }
                return false;
            }
            JSDynamicObject receiverObj = (JSDynamicObject)receiver;
            PropertyDescriptor existingDesc = JSObject.getOwnProperty(receiverObj, key);
            if (existingDesc != null) {
                if (existingDesc.isAccessorDescriptor()) {
                    if (isStrict) {
                        throw Errors.createTypeErrorCannotRedefineProperty(key);
                    }
                    return false;
                }
                if (!existingDesc.getWritable()) {
                    if (isStrict) {
                        throw Errors.createTypeErrorNotWritableProperty(key, receiverObj);
                    }
                    return false;
                }
                PropertyDescriptor valueDesc = PropertyDescriptor.createData(value2);
                return JSObject.defineOwnProperty(receiverObj, key, valueDesc, isStrict);
            }
            return JSRuntime.createDataProperty(receiverObj, key, value2, isStrict);
        }
        assert (descriptor.isAccessorDescriptor());
        Object setter = descriptor.getSet();
        if (setter == Undefined.instance || setter == null) {
            if (isStrict) {
                throw Errors.createTypeErrorCannotSetAccessorProperty(key, target);
            }
            return false;
        }
        JSRuntime.call(setter, receiver, new Object[]{value2}, encapsulatingNode);
        return true;
    }

    @CompilerDirectives.TruffleBoundary
    protected static boolean setPropertySlow(JSDynamicObject thisObj, Object key, Object value2, Object receiver, boolean isStrict, boolean isIndex, Node encapsulatingNode) {
        assert (JSRuntime.isPropertyKey(key));
        JSDynamicObject current = JSObject.getPrototype(thisObj);
        while (current != Null.instance) {
            if (JSProxy.isJSProxy(current) || JSArrayBufferView.isJSArrayBufferView(current)) {
                return JSObject.getJSClass(current).set(current, key, value2, receiver, isStrict, encapsulatingNode);
            }
            PropertyDescriptor desc = JSObject.getOwnProperty(current, key);
            if (desc != null) {
                if (desc.isDataDescriptor() && !desc.getWritable()) {
                    if (isStrict) {
                        throw Errors.createTypeErrorNotWritableProperty(key, current);
                    }
                    return false;
                }
                if (!desc.isAccessorDescriptor()) break;
                return JSNonProxy.invokeAccessorPropertySetter(desc, thisObj, key, value2, receiver, isStrict, encapsulatingNode);
            }
            current = JSObject.getPrototype(current);
        }
        assert (thisObj == receiver);
        JSDynamicObject receiverObj = (JSDynamicObject)receiver;
        if (!JSObject.isExtensible(receiverObj)) {
            if (isStrict) {
                throw Errors.createTypeErrorNotExtensible(receiverObj, key);
            }
            return false;
        }
        boolean isDictionaryObject = JSDictionary.isJSDictionaryObject(thisObj);
        if (!isDictionaryObject && JSNonProxy.isDictionaryObjectCandidate(thisObj, isIndex)) {
            JSDictionary.makeDictionaryObject(thisObj, "set");
            isDictionaryObject = true;
        }
        if (isDictionaryObject) {
            JSDictionary.getHashMap(thisObj).put(key, PropertyDescriptor.createDataDefault(value2));
            return true;
        }
        JSContext context = JSObject.getJSContext(thisObj);
        JSObjectUtil.putDataProperty(context, thisObj, key, value2, JSAttributes.getDefault());
        return true;
    }

    protected static boolean invokeAccessorPropertySetter(PropertyDescriptor desc, JSDynamicObject thisObj, Object key, Object value2, Object receiver, boolean isStrict, Node encapsulatingNode) {
        CompilerAsserts.neverPartOfCompilation();
        assert (desc.isAccessorDescriptor());
        Object setter = desc.getSet();
        if (setter != Undefined.instance) {
            JSRuntime.call(setter, receiver, new Object[]{value2}, encapsulatingNode);
            return true;
        }
        if (isStrict) {
            throw Errors.createTypeErrorCannotSetAccessorProperty(key, thisObj);
        }
        return false;
    }

    private static boolean isDictionaryObjectCandidate(JSDynamicObject thisObj, boolean isIndex) {
        if (!JSOrdinary.isJSOrdinaryObject(thisObj)) {
            return false;
        }
        int count = thisObj.getShape().getPropertyCount();
        return count == 0 && isIndex || count == 1024;
    }

    @Override
    public PropertyDescriptor getOwnProperty(JSDynamicObject thisObj, Object key) {
        return JSNonProxy.ordinaryGetOwnProperty(thisObj, key);
    }

    public static PropertyDescriptor ordinaryGetOwnProperty(JSDynamicObject thisObj, Object key) {
        assert (JSRuntime.isPropertyKey(key));
        Property prop = thisObj.getShape().getProperty(key);
        if (prop == null) {
            return null;
        }
        return JSNonProxy.ordinaryGetOwnPropertyIntl(thisObj, key, prop);
    }

    @CompilerDirectives.TruffleBoundary
    public static PropertyDescriptor ordinaryGetOwnPropertyIntl(JSDynamicObject thisObj, Object key, Property prop) {
        PropertyDescriptor desc;
        if (JSProperty.isData(prop)) {
            Object value2 = JSDynamicObject.getOrNull(thisObj, key);
            if (JSProperty.isProxy(prop)) {
                value2 = ((PropertyProxy)value2).get(thisObj);
            }
            desc = PropertyDescriptor.createData(value2);
            desc.setWritable(JSProperty.isWritable(prop));
        } else if (JSProperty.isAccessor(prop)) {
            Accessor acc = (Accessor)JSDynamicObject.getOrNull(thisObj, key);
            desc = PropertyDescriptor.createAccessor(acc.getGetter(), acc.getSetter());
        } else {
            desc = PropertyDescriptor.createEmpty();
        }
        desc.setEnumerable(JSProperty.isEnumerable(prop));
        desc.setConfigurable(JSProperty.isConfigurable(prop));
        return desc;
    }

    @Override
    public boolean setIntegrityLevel(JSDynamicObject thisObj, boolean freeze, boolean doThrow) {
        if (this.usesOrdinaryGetOwnProperty()) {
            return this.setIntegrityLevelFast(thisObj, freeze);
        }
        return super.setIntegrityLevel(thisObj, freeze, doThrow);
    }

    @CompilerDirectives.TruffleBoundary
    protected final boolean setIntegrityLevelFast(JSDynamicObject thisObj, boolean freeze) {
        if (JSNonProxy.testIntegrityLevelFast(thisObj, freeze)) {
            return true;
        }
        for (Property property : JSDynamicObject.getPropertyArray(thisObj)) {
            if (property.isHidden()) continue;
            int oldFlags = property.getFlags();
            int newFlags = oldFlags | 2;
            if (freeze && (oldFlags & 8) == 0) {
                newFlags |= 4;
            }
            if (newFlags == oldFlags) continue;
            Object key = property.getKey();
            JSDynamicObject.setPropertyFlags(thisObj, key, newFlags);
            assert (JSDynamicObject.getPropertyFlags(thisObj, key) == newFlags);
        }
        assert (JSNonProxy.testSealedProperties(thisObj) && (!freeze || JSNonProxy.testFrozenProperties(thisObj)));
        boolean result = this.preventExtensionsImpl(thisObj, 2 | (freeze ? 4 : 0));
        assert (result && this.testIntegrityLevel(thisObj, freeze));
        return true;
    }

    @Override
    public boolean testIntegrityLevel(JSDynamicObject obj, boolean frozen) {
        if (this.usesOrdinaryGetOwnProperty()) {
            return JSNonProxy.testIntegrityLevelFast(obj, frozen);
        }
        return super.testIntegrityLevel(obj, frozen);
    }

    @CompilerDirectives.TruffleBoundary
    protected static boolean testIntegrityLevelFast(JSDynamicObject obj, boolean frozen) {
        int objectFlags = JSDynamicObject.getObjectFlags(obj);
        if (frozen) {
            return (objectFlags & 4) != 0;
        }
        return (objectFlags & 2) != 0;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean preventExtensions(JSDynamicObject thisObj, boolean doThrow) {
        return this.preventExtensionsImpl(thisObj, 0);
    }

    protected final boolean preventExtensionsImpl(JSDynamicObject thisObj, int extraFlags) {
        int objectFlags = JSDynamicObject.getObjectFlags(thisObj);
        if ((objectFlags & 1) != 0 && (objectFlags & extraFlags) == extraFlags) {
            return true;
        }
        int newFlags = objectFlags | 1 | extraFlags;
        if ((newFlags & 2) == 0 && JSNonProxy.testSealedProperties(thisObj)) {
            newFlags |= 2;
        }
        if ((newFlags & 2) != 0 && (newFlags & 4) == 0 && JSNonProxy.testFrozenProperties(thisObj)) {
            newFlags |= 4;
        }
        if (newFlags != objectFlags) {
            JSDynamicObject.setObjectFlags(thisObj, newFlags);
        }
        assert (!this.isExtensible(thisObj));
        return true;
    }

    private static boolean testSealedProperties(JSDynamicObject thisObj) {
        return JSDynamicObject.testProperties(thisObj, p -> p.isHidden() || (p.getFlags() & 2) != 0);
    }

    private static boolean testFrozenProperties(JSDynamicObject thisObj) {
        return JSDynamicObject.testProperties(thisObj, p -> p.isHidden() || (p.getFlags() & 8) != 0 || (p.getFlags() & 4) != 0);
    }

    @Override
    public final boolean isExtensible(JSDynamicObject thisObj) {
        return JSShape.isExtensible(thisObj.getShape());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    @Override
    public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
        if (JavaScriptLanguage.get(null).getJSContext().isOptionNashornCompatibilityMode()) {
            return this.defaultToString(obj);
        }
        return JSRuntime.objectToDisplayString(obj, allowSideEffects, format, depth, this.getClassName(obj));
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public final JSDynamicObject getPrototypeOf(JSDynamicObject thisObj) {
        return JSObjectUtil.getPrototype(thisObj);
    }

    @Override
    public boolean setPrototypeOf(JSDynamicObject thisObj, JSDynamicObject newPrototype) {
        return JSNonProxy.setPrototypeStatic(thisObj, newPrototype);
    }

    @CompilerDirectives.TruffleBoundary
    static boolean setPrototypeStatic(JSDynamicObject thisObj, JSDynamicObject newPrototype) {
        JSDynamicObject oldPrototype = JSObject.getPrototype(thisObj);
        if (oldPrototype == newPrototype) {
            return true;
        }
        if (!JSNonProxy.checkProtoCycle(thisObj, newPrototype)) {
            return false;
        }
        Shape shape = thisObj.getShape();
        if (!JSShape.isExtensible(shape)) {
            return false;
        }
        if (JSShape.isPrototypeInShape(shape)) {
            JSObjectUtil.setPrototypeImpl(thisObj, newPrototype);
        } else {
            boolean success = Properties.putIfPresentUncached(thisObj, JSObject.HIDDEN_PROTO, newPrototype);
            assert (success);
        }
        return true;
    }

    public static boolean checkProtoCycle(JSDynamicObject thisObj, JSDynamicObject newPrototype) {
        JSDynamicObject proto = newPrototype;
        while (proto != Null.instance) {
            if (proto == thisObj) {
                return false;
            }
            if (JSProxy.isJSProxy(proto)) {
                return true;
            }
            proto = JSObject.getPrototype(proto);
        }
        return true;
    }

    protected static void putConstructorSpeciesGetter(JSRealm realm, JSDynamicObject constructor) {
        JSObjectUtil.putBuiltinAccessorProperty(constructor, (Object)Symbol.SYMBOL_SPECIES, JSNonProxy.createSymbolSpeciesGetterFunction(realm), Undefined.instance);
    }

    protected static JSDynamicObject createSymbolSpeciesGetterFunction(JSRealm realm) {
        return JSFunction.create(realm, realm.getContext().getSymbolSpeciesThisGetterFunctionData());
    }

    @Override
    public TruffleString getBuiltinToStringTag(JSDynamicObject object) {
        return Strings.UC_OBJECT;
    }

    @Override
    public boolean usesOrdinaryGetOwnProperty() {
        return true;
    }

    @Override
    public boolean usesOrdinaryIsExtensible() {
        return true;
    }
}

