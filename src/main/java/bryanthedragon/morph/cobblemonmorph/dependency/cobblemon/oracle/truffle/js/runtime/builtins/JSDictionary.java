
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.objects.Accessor;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSOrdinaryObject;
import com.oracle.truffle.js.runtime.objects.JSProperty;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.DefinePropertyUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.graalvm.collections.EconomicMap;

public final class JSDictionary
extends JSNonProxy {
    private static final HiddenKey HASHMAP_PROPERTY_NAME = new HiddenKey("%hashMap");
    public static final JSDictionary INSTANCE = new JSDictionary();

    private JSDictionary() {
    }

    public static boolean isJSDictionaryObject(Object obj) {
        return JSDynamicObject.isJSDynamicObject(obj) && JSDictionary.isJSDictionaryObject((JSDynamicObject)obj);
    }

    public static boolean isJSDictionaryObject(JSDynamicObject obj) {
        return JSDictionary.isInstance(obj, (JSClass)INSTANCE);
    }

    @Override
    public TruffleString getClassName(JSDynamicObject object) {
        return Strings.UC_OBJECT;
    }

    @Override
    public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
        return this.defaultToString(obj);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Object getOwnHelper(JSDynamicObject store, Object thisObj, Object key, Node encapsulatingNode) {
        assert (JSRuntime.isPropertyKey(key));
        PropertyDescriptor desc = (PropertyDescriptor)JSDictionary.getHashMap(store).get(key);
        if (desc != null) {
            return JSDictionary.getValue(desc, thisObj, encapsulatingNode);
        }
        return super.getOwnHelper(store, thisObj, key, encapsulatingNode);
    }

    public static Object getValue(PropertyDescriptor property, Object receiver, Node encapsulatingNode) {
        if (property.isAccessorDescriptor()) {
            Object getter = property.getGet();
            if (getter != Undefined.instance) {
                return JSRuntime.call(getter, receiver, JSArguments.EMPTY_ARGUMENTS_ARRAY, encapsulatingNode);
            }
            return Undefined.instance;
        }
        assert (property.isDataDescriptor());
        return property.getValue();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public List<Object> getOwnPropertyKeys(JSDynamicObject thisObj, boolean strings, boolean symbols) {
        assert (JSDictionary.isJSDictionaryObject(thisObj));
        List<Object> keys = JSDictionary.ordinaryOwnPropertyKeysSlow(thisObj, strings, symbols);
        for (Object key : JSDictionary.getHashMap(thisObj).getKeys()) {
            assert (JSRuntime.isPropertyKey(key));
            if (!symbols && key instanceof Symbol || !strings && Strings.isTString(key)) continue;
            keys.add(key);
        }
        Collections.sort(keys, JSRuntime::comparePropertyKeys);
        return keys;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean delete(JSDynamicObject thisObj, Object key, boolean isStrict) {
        assert (JSRuntime.isPropertyKey(key));
        EconomicMap<Object, PropertyDescriptor> hashMap = JSDictionary.getHashMap(thisObj);
        PropertyDescriptor desc = (PropertyDescriptor)hashMap.get(key);
        if (desc != null) {
            if (!desc.getConfigurable()) {
                if (isStrict) {
                    throw Errors.createTypeErrorNotConfigurableProperty(key);
                }
                return false;
            }
            hashMap.removeKey(key);
            return true;
        }
        return super.delete(thisObj, key, isStrict);
    }

    @Override
    public boolean delete(JSDynamicObject thisObj, long index, boolean isStrict) {
        return this.delete(thisObj, Strings.fromLong(index), isStrict);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean hasOwnProperty(JSDynamicObject thisObj, Object key) {
        assert (JSRuntime.isPropertyKey(key));
        if (JSDictionary.getHashMap(thisObj).containsKey(key)) {
            return true;
        }
        return super.hasOwnProperty(thisObj, key);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean set(JSDynamicObject thisObj, long index, Object value2, Object receiver, boolean isStrict, Node encapsulatingNode) {
        TruffleString key = Strings.fromLong(index);
        return JSDictionary.dictionaryObjectSet(thisObj, key, value2, receiver, isStrict, encapsulatingNode);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean set(JSDynamicObject thisObj, Object key, Object value2, Object receiver, boolean isStrict, Node encapsulatingNode) {
        assert (JSRuntime.isPropertyKey(key));
        return JSDictionary.dictionaryObjectSet(thisObj, key, value2, receiver, isStrict, encapsulatingNode);
    }

    protected static boolean dictionaryObjectSet(JSDynamicObject thisObj, Object key, Object value2, Object receiver, boolean isStrict, Node encapsulatingNode) {
        assert (JSRuntime.isPropertyKey(key));
        if (receiver != thisObj) {
            return JSDictionary.ordinarySetWithReceiver(thisObj, key, value2, receiver, isStrict, encapsulatingNode);
        }
        PropertyDescriptor property = (PropertyDescriptor)JSDictionary.getHashMap(thisObj).get(key);
        if (property != null) {
            return JSDictionary.setValue(key, property, thisObj, receiver, value2, isStrict, encapsulatingNode);
        }
        Property entry = DefinePropertyUtil.getPropertyByKey(thisObj, key);
        if (entry != null) {
            return JSProperty.setValue(entry, thisObj, receiver, value2, isStrict, encapsulatingNode);
        }
        return JSDictionary.setPropertySlow(thisObj, key, value2, receiver, isStrict, false, encapsulatingNode);
    }

    private static boolean setValue(Object key, PropertyDescriptor property, JSDynamicObject store, Object thisObj, Object value2, boolean isStrict, Node encapsulatingNode) {
        if (property.isAccessorDescriptor()) {
            Object setter = property.getSet();
            if (setter != Undefined.instance) {
                JSRuntime.call(setter, thisObj, new Object[]{value2}, encapsulatingNode);
                return true;
            }
            if (isStrict) {
                throw Errors.createTypeErrorCannotSetAccessorProperty(key, store);
            }
            return false;
        }
        assert (property.isDataDescriptor());
        if (property.getWritable()) {
            property.setValue(value2);
            return true;
        }
        if (isStrict) {
            throw Errors.createTypeErrorNotWritableProperty(key, thisObj);
        }
        return false;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public PropertyDescriptor getOwnProperty(JSDynamicObject thisObj, Object key) {
        assert (JSRuntime.isPropertyKey(key));
        PropertyDescriptor prop = (PropertyDescriptor)JSDictionary.getHashMap(thisObj).get(key);
        if (prop != null) {
            return prop;
        }
        return super.getOwnProperty(thisObj, key);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean defineOwnProperty(JSDynamicObject thisObj, Object key, PropertyDescriptor desc, boolean doThrow) {
        assert (JSRuntime.isPropertyKey(key));
        PropertyDescriptor current = (PropertyDescriptor)JSDictionary.getHashMap(thisObj).get(key);
        if (current == null) {
            current = super.getOwnProperty(thisObj, key);
            boolean extensible = JSObject.isExtensible(thisObj);
            if (current == null) {
                if (!extensible) {
                    return DefinePropertyUtil.reject(doThrow, DefinePropertyUtil.notExtensibleMessage(key, doThrow));
                }
                JSDictionary.validateAndPutDesc(thisObj, key, JSDictionary.makeFullyPopulatedPropertyDescriptor(desc));
                return true;
            }
            return DefinePropertyUtil.validateAndApplyPropertyDescriptor(thisObj, key, extensible, desc, current, doThrow);
        }
        return JSDictionary.validateAndApplyPropertyDescriptorExisting(thisObj, key, desc, current, doThrow);
    }

    private static PropertyDescriptor validateAndPutDesc(JSDynamicObject thisObj, Object key, PropertyDescriptor newDesc) {
        assert (newDesc.isFullyPopulatedPropertyDescriptor());
        return JSDictionary.getHashMap(thisObj).put(key, newDesc);
    }

    private static PropertyDescriptor makeFullyPopulatedPropertyDescriptor(PropertyDescriptor desc) {
        if (desc.isAccessorDescriptor()) {
            if (desc.hasGet() && desc.hasSet() && desc.hasEnumerable() && desc.hasConfigurable()) {
                return desc;
            }
            return PropertyDescriptor.createAccessor(desc.getGet(), desc.getSet(), desc.getEnumerable(), desc.getConfigurable());
        }
        if (desc.isDataDescriptor()) {
            if (desc.hasValue() && desc.hasWritable() && desc.hasEnumerable() && desc.hasConfigurable()) {
                return desc;
            }
            Object value2 = desc.hasValue() ? desc.getValue() : Undefined.instance;
            return PropertyDescriptor.createData(value2, desc.getEnumerable(), desc.getWritable(), desc.getConfigurable());
        }
        assert (desc.isGenericDescriptor());
        return PropertyDescriptor.createData(Undefined.instance, desc.getEnumerable(), desc.getWritable(), desc.getConfigurable());
    }

    private static boolean validateAndApplyPropertyDescriptorExisting(JSDynamicObject thisObj, Object key, PropertyDescriptor descriptor, PropertyDescriptor currentDesc, boolean doThrow) {
        CompilerAsserts.neverPartOfCompilation();
        assert (currentDesc.isFullyPopulatedPropertyDescriptor());
        if (descriptor.hasNoFields()) {
            return true;
        }
        if (!currentDesc.getConfigurable()) {
            if (descriptor.hasConfigurable() && descriptor.getConfigurable() || descriptor.hasEnumerable() && descriptor.getEnumerable() != currentDesc.getEnumerable()) {
                return DefinePropertyUtil.reject(doThrow, DefinePropertyUtil.nonConfigurableMessage(key, doThrow));
            }
            if (!descriptor.isGenericDescriptor() && descriptor.isAccessorDescriptor() != currentDesc.isAccessorDescriptor()) {
                return DefinePropertyUtil.reject(doThrow, DefinePropertyUtil.nonConfigurableMessage(key, doThrow));
            }
            if (currentDesc.isAccessorDescriptor()) {
                if (descriptor.hasGet() && !JSRuntime.isSameValue(descriptor.getGet(), currentDesc.getGet()) || descriptor.hasSet() && !JSRuntime.isSameValue(descriptor.getSet(), currentDesc.getSet())) {
                    return DefinePropertyUtil.reject(doThrow, DefinePropertyUtil.nonConfigurableMessage(key, doThrow));
                }
            } else {
                assert (currentDesc.isDataDescriptor());
                if (!currentDesc.getWritable()) {
                    if (descriptor.hasWritable() && descriptor.getWritable()) {
                        return DefinePropertyUtil.reject(doThrow, DefinePropertyUtil.nonConfigurableMessage(key, doThrow));
                    }
                    if (descriptor.hasValue() && !JSRuntime.isSameValue(descriptor.getValue(), currentDesc.getValue())) {
                        return DefinePropertyUtil.reject(doThrow, DefinePropertyUtil.nonWritableMessage(key, doThrow));
                    }
                }
            }
        }
        if (currentDesc.isDataDescriptor() && descriptor.isAccessorDescriptor()) {
            PropertyDescriptor newDesc = PropertyDescriptor.createAccessor(descriptor.getGet(), descriptor.getSet(), descriptor.getIfHasEnumerable(currentDesc.getEnumerable()), descriptor.getIfHasConfigurable(currentDesc.getConfigurable()));
            JSDictionary.validateAndPutDesc(thisObj, key, newDesc);
            return true;
        }
        if (currentDesc.isAccessorDescriptor() && descriptor.isDataDescriptor()) {
            Object value2 = descriptor.hasValue() ? descriptor.getValue() : Undefined.instance;
            PropertyDescriptor newDesc = PropertyDescriptor.createData(value2, descriptor.getIfHasEnumerable(currentDesc.getEnumerable()), descriptor.getIfHasConfigurable(currentDesc.getConfigurable()), descriptor.getWritable());
            JSDictionary.validateAndPutDesc(thisObj, key, newDesc);
            return true;
        }
        if (descriptor.hasConfigurable()) {
            currentDesc.setConfigurable(descriptor.getConfigurable());
        }
        if (descriptor.hasEnumerable()) {
            currentDesc.setEnumerable(descriptor.getEnumerable());
        }
        if (descriptor.hasWritable()) {
            currentDesc.setWritable(descriptor.getWritable());
        }
        if (descriptor.hasValue()) {
            currentDesc.setValue(descriptor.getValue());
        }
        if (descriptor.hasGet()) {
            currentDesc.setGet(descriptor.getGet());
        }
        if (descriptor.hasSet()) {
            currentDesc.setSet(descriptor.getSet());
        }
        return true;
    }

    static EconomicMap<Object, PropertyDescriptor> getHashMap(JSDynamicObject obj) {
        assert (JSDictionary.isJSDictionaryObject(obj));
        return (EconomicMap)JSDynamicObject.getOrNull(obj, HASHMAP_PROPERTY_NAME);
    }

    public static void makeDictionaryObject(JSDynamicObject obj, String reason) {
        CompilerAsserts.neverPartOfCompilation();
        if (!JSOrdinary.isJSOrdinaryObject(obj)) {
            return;
        }
        Shape currentShape = obj.getShape();
        assert (!JSDictionary.isJSDictionaryObject(obj) && currentShape.getProperty(HASHMAP_PROPERTY_NAME) == null);
        JSContext context = JSObject.getJSContext(obj);
        Shape newRootShape = JSDictionary.makeEmptyShapeForNewType(context, currentShape, INSTANCE, obj);
        assert (JSShape.hasExternalProperties(newRootShape.getFlags()));
        DynamicObjectLibrary lib = DynamicObjectLibrary.getUncached();
        List<Property> allProperties = currentShape.getPropertyListInternal(true);
        ArrayList<Object> archive = new ArrayList<Object>(allProperties.size());
        for (Property prop : allProperties) {
            Object key = prop.getKey();
            Object value2 = Properties.getOrDefault(lib, obj, key, null);
            assert (value2 != null);
            archive.add(value2);
        }
        lib.resetShape(obj, newRootShape);
        EconomicMap<Object, PropertyDescriptor> hashMap = EconomicMap.create();
        for (int i = 0; i < archive.size(); ++i) {
            Property p = allProperties.get(i);
            Object key = p.getKey();
            if (newRootShape.hasProperty(key)) continue;
            Object value3 = archive.get(i);
            if (key instanceof HiddenKey || JSProperty.isProxy(p)) {
                if (p.getLocation().isConstant()) {
                    Properties.putConstant(lib, obj, key, value3, p.getFlags());
                    continue;
                }
                Properties.putWithFlags(lib, obj, key, value3, p.getFlags());
                continue;
            }
            hashMap.put(key, JSDictionary.toPropertyDescriptor(p, value3));
        }
        JSObjectUtil.putHiddenProperty(obj, HASHMAP_PROPERTY_NAME, hashMap);
        assert (JSDictionary.isJSDictionaryObject(obj) && obj.getShape().getProperty(HASHMAP_PROPERTY_NAME) != null);
    }

    private static Shape makeEmptyShapeForNewType(JSContext context, Shape currentShape, JSClass jsclass, JSDynamicObject fromObject) {
        Property prototypeProperty = JSShape.getPrototypeProperty(currentShape);
        if (!prototypeProperty.getLocation().isConstant()) {
            return context.makeEmptyShapeWithPrototypeInObject(jsclass);
        }
        JSDynamicObject prototype = JSObjectUtil.getPrototype(fromObject);
        if (prototype == Null.instance) {
            return context.makeEmptyShapeWithNullPrototype(jsclass);
        }
        return JSObjectUtil.getProtoChildShape(prototype, jsclass, context);
    }

    private static PropertyDescriptor toPropertyDescriptor(Property p, Object value2) {
        PropertyDescriptor desc;
        if (JSProperty.isAccessor(p)) {
            desc = PropertyDescriptor.createAccessor(((Accessor)value2).getGetter(), ((Accessor)value2).getSetter());
            desc.setConfigurable(JSProperty.isConfigurable(p));
            desc.setEnumerable(JSProperty.isEnumerable(p));
        } else {
            assert (JSProperty.isData(p));
            desc = PropertyDescriptor.createData(value2, JSProperty.isEnumerable(p), JSProperty.isWritable(p), JSProperty.isConfigurable(p));
        }
        return desc;
    }

    public static Shape makeDictionaryShape(JSContext context, JSDynamicObject prototype) {
        assert (prototype != Null.instance);
        return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
    }

    public static JSDynamicObject create(JSContext context, JSRealm realm) {
        JSObjectFactory factory = context.getDictionaryObjectFactory();
        JSOrdinaryObject obj = JSOrdinaryObject.create(factory.getShape(realm));
        factory.initProto(obj, realm);
        JSObjectUtil.putHiddenProperty(obj, HASHMAP_PROPERTY_NAME, JSDictionary.newHashMap());
        return context.trackAllocation(obj);
    }

    private static EconomicMap<Object, PropertyDescriptor> newHashMap() {
        return EconomicMap.create();
    }

    @Override
    public boolean usesOrdinaryGetOwnProperty() {
        return false;
    }
}

