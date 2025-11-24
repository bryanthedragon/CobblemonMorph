
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.array.ArrayAllocationSite;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.SparseArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantEmptyPrototypeArray;
import com.oracle.truffle.js.runtime.array.dyn.LazyRegexResultArray;
import com.oracle.truffle.js.runtime.builtins.ArrayAccess;
import com.oracle.truffle.js.runtime.builtins.JSArgumentsArray;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectPrototype;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.builtins.JSSlowArray;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSProperty;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.DefinePropertyUtil;
import com.oracle.truffle.js.runtime.util.IteratorUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

public abstract class JSAbstractArray
extends JSNonProxy {
    public static final TruffleString LENGTH = Strings.constant("length");
    protected static final String ARRAY_LENGTH_NOT_WRITABLE = "array length is not writable";
    private static final String LENGTH_PROPERTY_NOT_WRITABLE = "length property not writable";
    protected static final String CANNOT_REDEFINE_PROPERTY_LENGTH = "Cannot redefine property: length";
    protected static final String MAKE_SLOW_ARRAY_NEVER_PART_OF_COMPILATION_MESSAGE = "do not convert to slow array from compiled code";
    public static final String ARRAY_PROTOTYPE_NO_ELEMENTS_INVALIDATION = "Array.prototype no element assumption";
    public static final HiddenKey LAZY_REGEX_RESULT_ID = new HiddenKey("lazyRegexResult");
    public static final HiddenKey LAZY_REGEX_ORIGINAL_INPUT_ID = new HiddenKey("lazyRegexResultOriginalInput");
    public static final Comparator<Object> DEFAULT_JSARRAY_COMPARATOR = new DefaultJSArrayComparator();
    public static final Comparator<Object> DEFAULT_JSARRAY_INTEGER_COMPARATOR = new DefaultJSArrayIntegerComparator();
    public static final Comparator<Object> DEFAULT_JSARRAY_DOUBLE_COMPARATOR = new DefaultJSArrayDoubleComparator();

    public static ScriptArray arrayGetArrayType(JSDynamicObject thisObj) {
        assert (JSArray.isJSArray(thisObj) || JSArgumentsArray.isJSArgumentsObject(thisObj) || JSObjectPrototype.isJSObjectPrototype(thisObj));
        return JSAbstractArray.arrayAccess().getArrayType(thisObj);
    }

    public static long arrayGetLength(JSDynamicObject thisObj) {
        return JSAbstractArray.arrayAccess().getLength(thisObj);
    }

    public static int arrayGetUsedLength(JSDynamicObject thisObj) {
        return JSAbstractArray.arrayAccess().getUsedLength(thisObj);
    }

    public static long arrayGetIndexOffset(JSDynamicObject thisObj) {
        return JSAbstractArray.arrayAccess().getIndexOffset(thisObj);
    }

    public static int arrayGetArrayOffset(JSDynamicObject thisObj) {
        return JSAbstractArray.arrayAccess().getArrayOffset(thisObj);
    }

    public static void arraySetArrayType(JSDynamicObject thisObj, ScriptArray arrayType) {
        JSAbstractArray.arrayAccess().setArrayType(thisObj, arrayType);
    }

    public static void arraySetLength(JSDynamicObject thisObj, int length) {
        assert (length >= 0);
        JSAbstractArray.arrayAccess().setLength(thisObj, length);
    }

    public static void arraySetLength(JSDynamicObject thisObj, long length) {
        assert (JSRuntime.isValidArrayLength(length));
        JSAbstractArray.arrayAccess().setLength(thisObj, length);
    }

    public static void arraySetUsedLength(JSDynamicObject thisObj, int usedLength) {
        assert (usedLength >= 0);
        JSAbstractArray.arrayAccess().setUsedLength(thisObj, usedLength);
    }

    public static void arraySetIndexOffset(JSDynamicObject thisObj, long indexOffset) {
        JSAbstractArray.arrayAccess().setIndexOffset(thisObj, indexOffset);
    }

    public static void arraySetArrayOffset(JSDynamicObject thisObj, int arrayOffset) {
        assert (arrayOffset >= 0);
        JSAbstractArray.arrayAccess().setArrayOffset(thisObj, arrayOffset);
    }

    public static Object arrayGetArray(JSDynamicObject thisObj) {
        assert (JSObject.hasArray(thisObj));
        return JSAbstractArray.arrayAccess().getArray(thisObj);
    }

    public static void arraySetArray(JSDynamicObject thisObj, Object array) {
        assert (JSObject.hasArray(thisObj));
        assert (array != null && (array.getClass().isArray() || array instanceof TreeMap));
        JSAbstractArray.arrayAccess().setArray(thisObj, array);
    }

    public static int arrayGetHoleCount(JSDynamicObject thisObj) {
        return JSAbstractArray.arrayAccess().getHoleCount(thisObj);
    }

    public static void arraySetHoleCount(JSDynamicObject thisObj, int holeCount) {
        assert (holeCount >= 0);
        JSAbstractArray.arrayAccess().setHoleCount(thisObj, holeCount);
    }

    public static ArrayAllocationSite arrayGetAllocationSite(JSDynamicObject thisObj) {
        return JSAbstractArray.arrayAccess().getAllocationSite(thisObj);
    }

    public static Object arrayGetRegexResult(JSDynamicObject thisObj, DynamicObjectLibrary lazyRegexResult) {
        assert (JSArray.isJSArray(thisObj) && JSArray.arrayGetArrayType(thisObj) == LazyRegexResultArray.LAZY_REGEX_RESULT_ARRAY);
        return Properties.getOrDefault(lazyRegexResult, thisObj, LAZY_REGEX_RESULT_ID, null);
    }

    public static TruffleString arrayGetRegexResultOriginalInput(JSDynamicObject thisObj, DynamicObjectLibrary lazyRegexResultOriginalInput) {
        return (TruffleString)Properties.getOrDefault(lazyRegexResultOriginalInput, thisObj, LAZY_REGEX_ORIGINAL_INPUT_ID, null);
    }

    protected JSAbstractArray() {
    }

    protected static final ArrayAccess arrayAccess() {
        return ArrayAccess.SINGLETON;
    }

    public long getLength(JSDynamicObject thisObj) {
        return JSAbstractArray.arrayGetLength(thisObj);
    }

    @CompilerDirectives.TruffleBoundary
    public boolean setLength(JSDynamicObject thisObj, long length, boolean doThrow) {
        long minIndex;
        if (length < 0L) {
            throw Errors.createRangeErrorInvalidArrayLength();
        }
        ScriptArray array = JSAbstractArray.arrayGetArrayType(thisObj);
        if (length > Integer.MAX_VALUE && !(array instanceof SparseArray)) {
            array = SparseArray.makeSparseArray(thisObj, array);
        }
        if (array.isSealed() && length < (minIndex = array.lastElementIndex(thisObj) + 1L)) {
            array = array.setLength(thisObj, minIndex, doThrow);
            JSAbstractArray.arraySetArrayType(thisObj, array);
            return array.canDeleteElement(thisObj, minIndex - 1L, doThrow);
        }
        JSAbstractArray.arraySetArrayType(thisObj, array.setLength(thisObj, length, doThrow));
        return true;
    }

    @Override
    public TruffleString getBuiltinToStringTag(JSDynamicObject object) {
        return this.getClassName(object);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public final Object getOwnHelper(JSDynamicObject store, Object thisObj, Object key, Node encapsulatingNode) {
        long idx = JSRuntime.propertyKeyToArrayIndex(key);
        if (JSRuntime.isArrayIndex(idx)) {
            return this.getOwnHelper(store, thisObj, idx, encapsulatingNode);
        }
        return super.getOwnHelper(store, thisObj, key, encapsulatingNode);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public final boolean set(JSDynamicObject thisObj, Object key, Object value2, Object receiver, boolean isStrict, Node encapsulatingNode) {
        if (receiver != thisObj) {
            return JSAbstractArray.ordinarySetWithReceiver(thisObj, key, value2, receiver, isStrict, encapsulatingNode);
        }
        assert (receiver == thisObj);
        long idx = JSRuntime.propertyKeyToArrayIndex(key);
        if (JSRuntime.isArrayIndex(idx)) {
            return this.set(thisObj, idx, value2, receiver, isStrict, encapsulatingNode);
        }
        return super.set(thisObj, key, value2, receiver, isStrict, encapsulatingNode);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean set(JSDynamicObject thisObj, long index, Object value2, Object receiver, boolean isStrict, Node encapsulatingNode) {
        if (receiver != thisObj) {
            return JSAbstractArray.ordinarySetWithReceiver(thisObj, Strings.fromLong(index), value2, receiver, isStrict, encapsulatingNode);
        }
        assert (receiver == thisObj);
        if (JSAbstractArray.arrayGetArrayType(thisObj).hasElement(thisObj, index)) {
            return JSAbstractArray.setElement(thisObj, index, value2, isStrict);
        }
        return JSAbstractArray.setPropertySlow(thisObj, index, value2, receiver, isStrict, encapsulatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    private static boolean setPropertySlow(JSDynamicObject thisObj, long index, Object value2, Object receiver, boolean isStrict, Node encapsulatingNode) {
        if (!JSObject.getJSContext(thisObj).getArrayPrototypeNoElementsAssumption().isValid() && JSAbstractArray.setPropertyPrototypes(thisObj, index, value2, receiver, isStrict, encapsulatingNode)) {
            return true;
        }
        if (!JSObject.isExtensible(thisObj)) {
            if (isStrict) {
                throw Errors.createTypeErrorNotExtensible(thisObj, Strings.fromLong(index));
            }
            return true;
        }
        return JSAbstractArray.setElement(thisObj, index, value2, isStrict);
    }

    private static boolean setPropertyPrototypes(JSDynamicObject thisObj, long index, Object value2, Object receiver, boolean isStrict, Node encapsulatingNode) {
        JSDynamicObject current = JSObject.getPrototype(thisObj);
        TruffleString propertyName = null;
        while (current != Null.instance) {
            if (JSProxy.isJSProxy(current)) {
                return JSObject.getJSClass(current).set(current, index, value2, receiver, false, encapsulatingNode);
            }
            if (JSAbstractArray.canHaveReadOnlyOrAccessorProperties(current) && JSObject.hasOwnProperty(current, index)) {
                PropertyDescriptor desc;
                if (propertyName == null) {
                    propertyName = Strings.fromLong(index);
                }
                if ((desc = JSObject.getOwnProperty(current, propertyName)) != null) {
                    if (desc.isAccessorDescriptor()) {
                        JSAbstractArray.invokeAccessorPropertySetter(desc, thisObj, propertyName, value2, receiver, isStrict, encapsulatingNode);
                        return true;
                    }
                    if (desc.getWritable()) break;
                    if (isStrict) {
                        throw Errors.createTypeError("Cannot assign to read only property '" + index + "' of " + JSObject.defaultToString(thisObj));
                    }
                    return true;
                }
            }
            current = JSObject.getPrototype(current);
        }
        return false;
    }

    private static boolean canHaveReadOnlyOrAccessorProperties(JSDynamicObject current) {
        return !JSArrayBufferView.isJSArrayBufferView(current);
    }

    private static boolean setElement(JSDynamicObject thisObj, long index, Object value2, boolean isStrict) {
        JSAbstractArray.arraySetArrayType(thisObj, JSAbstractArray.arrayGetArrayType(thisObj).setElement(thisObj, index, value2, isStrict));
        return true;
    }

    @Override
    public boolean delete(JSDynamicObject thisObj, long index, boolean isStrict) {
        ScriptArray arrayType = JSAbstractArray.arrayGetArrayType(thisObj);
        if (arrayType.canDeleteElement(thisObj, index, isStrict)) {
            JSAbstractArray.arraySetArrayType(thisObj, arrayType.deleteElement(thisObj, index, isStrict));
            return true;
        }
        return false;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Object getOwnHelper(JSDynamicObject store, Object thisObj, long index, Node encapsulatingNode) {
        ScriptArray array = JSAbstractArray.arrayGetArrayType(store);
        if (array.hasElement(store, index)) {
            return array.getElement(store, index);
        }
        return super.getOwnHelper(store, thisObj, Strings.fromLong(index), encapsulatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static Object[] toArray(JSDynamicObject thisObj) {
        return JSAbstractArray.arrayGetArrayType(thisObj).toArray(thisObj);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public final boolean hasOwnProperty(JSDynamicObject thisObj, Object key) {
        if (super.hasOwnProperty(thisObj, key)) {
            return true;
        }
        long index = JSRuntime.propertyKeyToArrayIndex(key);
        if (JSRuntime.isArrayIndex(index)) {
            return JSAbstractArray.arrayGetArrayType(thisObj).hasElement(thisObj, index);
        }
        return false;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public final boolean hasOwnProperty(JSDynamicObject thisObj, long index) {
        ScriptArray array = JSAbstractArray.arrayGetArrayType(thisObj);
        if (array.hasElement(thisObj, index)) {
            return true;
        }
        return super.hasOwnProperty(thisObj, Strings.fromLong(index));
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public List<Object> getOwnPropertyKeys(JSDynamicObject thisObj, boolean strings, boolean symbols) {
        return JSAbstractArray.ownPropertyKeysSlowArray(thisObj, strings, symbols);
    }

    @CompilerDirectives.TruffleBoundary
    protected static List<Object> ownPropertyKeysFastArray(JSDynamicObject thisObj, boolean strings, boolean symbols) {
        assert (JSArray.isJSFastArray(thisObj) || JSArgumentsArray.isJSFastArgumentsObject(thisObj));
        List<Object> indices = strings ? JSAbstractArray.arrayGetArrayType(thisObj).ownPropertyKeys(thisObj) : Collections.emptyList();
        List<Object> keyList = thisObj.getShape().getKeyList();
        if (keyList.isEmpty()) {
            return indices;
        }
        ArrayList list = new ArrayList(keyList.size());
        if (strings) {
            keyList.forEach(k -> {
                assert (!Strings.isTString(k) || !JSRuntime.isArrayIndexString((TruffleString)k));
                if (Strings.isTString(k)) {
                    list.add(k);
                }
            });
        }
        if (symbols) {
            keyList.forEach(k -> {
                if (k instanceof Symbol) {
                    list.add(k);
                }
            });
        }
        return IteratorUtil.concatLists(indices, list);
    }

    @CompilerDirectives.TruffleBoundary
    protected static List<Object> ownPropertyKeysSlowArray(JSDynamicObject thisObj, boolean strings, boolean symbols) {
        List<Object> keyList;
        ArrayList<Object> list = new ArrayList<Object>();
        if (strings) {
            ScriptArray array = JSAbstractArray.arrayGetArrayType(thisObj);
            long currentIndex = array.firstElementIndex(thisObj);
            while (currentIndex <= array.lastElementIndex(thisObj)) {
                list.add(Strings.fromLong(currentIndex));
                currentIndex = array.nextElementIndex(thisObj, currentIndex);
            }
        }
        if (!(keyList = thisObj.getShape().getKeyList()).isEmpty()) {
            if (strings) {
                int before = list.size();
                keyList.forEach(k -> {
                    if (Strings.isTString(k) && JSRuntime.isArrayIndexString((TruffleString)k)) {
                        list.add(k);
                    }
                });
                int after2 = list.size();
                if (after2 != before) {
                    Collections.sort(list, (o1, o2) -> Long.compare(JSRuntime.propertyKeyToArrayIndex(o1), JSRuntime.propertyKeyToArrayIndex(o2)));
                }
                keyList.forEach(k -> {
                    if (Strings.isTString(k) && !JSRuntime.isArrayIndexString((TruffleString)k)) {
                        list.add(k);
                    }
                });
            }
            if (symbols) {
                keyList.forEach(k -> {
                    if (k instanceof Symbol) {
                        list.add(k);
                    }
                });
            }
        }
        return list;
    }

    protected static long toArrayLengthOrRangeError(Object obj) {
        Number len = JSRuntime.toNumber(obj);
        Long len32 = JSRuntime.toUInt32(len);
        Number numberLen = JSRuntime.toNumber(obj);
        return JSAbstractArray.toArrayLengthOrRangeError(numberLen, len32);
    }

    public static long toArrayLengthOrRangeError(Number len, Number len32) {
        double d;
        double d32 = JSRuntime.doubleValue(len32);
        if (d32 == (d = JSRuntime.doubleValue(len))) {
            return JSRuntime.longValue(len32);
        }
        if (d == 0.0) {
            return 0L;
        }
        throw Errors.createRangeErrorInvalidArrayLength();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean defineOwnProperty(JSDynamicObject thisObj, Object key, PropertyDescriptor descriptor, boolean doThrow) {
        if (Strings.isTString(key) && Strings.equals(LENGTH, (TruffleString)key)) {
            return this.defineOwnPropertyLength(thisObj, descriptor, doThrow);
        }
        if (Strings.isTString(key) && JSRuntime.isArrayIndexString((TruffleString)key)) {
            return this.defineOwnPropertyIndex(thisObj, (TruffleString)key, descriptor, doThrow);
        }
        return super.defineOwnProperty(thisObj, key, descriptor, doThrow);
    }

    private boolean defineOwnPropertyLength(JSDynamicObject thisObj, PropertyDescriptor descriptor, boolean doThrow) {
        if (!descriptor.hasValue()) {
            boolean success = DefinePropertyUtil.ordinaryDefineOwnProperty(thisObj, LENGTH, descriptor, doThrow);
            if (success && descriptor.hasWritable() && !descriptor.getWritable()) {
                JSAbstractArray.setLengthNotWritable(thisObj);
            }
            return success;
        }
        long newLen = JSRuntime.toUInt32(descriptor.getValue());
        Number numberLen = JSRuntime.toNumber(descriptor.getValue());
        if (JSRuntime.doubleValue(numberLen) != (double)newLen) {
            throw Errors.createRangeErrorInvalidArrayLength();
        }
        PropertyDescriptor lenDesc = this.getOwnProperty(thisObj, LENGTH);
        if (newLen >= this.getLength(thisObj)) {
            return this.definePropertyLength(thisObj, descriptor, lenDesc, newLen, doThrow);
        }
        if (!lenDesc.getWritable()) {
            return DefinePropertyUtil.reject(doThrow, ARRAY_LENGTH_NOT_WRITABLE);
        }
        long pos = this.getLength(thisObj);
        if (!this.definePropertyLength(thisObj, descriptor, lenDesc, newLen, doThrow)) {
            return false;
        }
        if (JSSlowArray.isJSSlowArray(thisObj)) {
            return this.deleteElementsAfterShortening(thisObj, descriptor, doThrow, newLen, lenDesc, pos);
        }
        return true;
    }

    private static void setLengthNotWritable(JSDynamicObject thisObj) {
        JSAbstractArray.arraySetArrayType(thisObj, JSAbstractArray.arrayGetArrayType(thisObj).setLengthNotWritable());
    }

    private boolean deleteElementsAfterShortening(JSDynamicObject thisObj, PropertyDescriptor descriptor, boolean doThrow, long newLen, PropertyDescriptor lenDesc, long startPos) {
        assert (JSRuntime.isValidArrayLength(newLen));
        long pos = startPos;
        while (pos > newLen) {
            TruffleString key;
            Property prop;
            if ((prop = DefinePropertyUtil.getPropertyByKey(thisObj, key = Strings.fromLong(--pos))) == null) continue;
            if (JSProperty.isConfigurable(prop)) {
                this.delete(thisObj, key, doThrow);
                continue;
            }
            long len = pos + 1L;
            descriptor.setValue(JSRuntime.longToIntOrDouble(len));
            this.definePropertyLength(thisObj, descriptor, lenDesc, len, doThrow);
            DefinePropertyUtil.ordinaryDefineOwnProperty(thisObj, LENGTH, descriptor, false);
            return DefinePropertyUtil.reject(doThrow, "cannot set the length to expected value");
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean definePropertyLength(JSDynamicObject thisObj, PropertyDescriptor descriptor, PropertyDescriptor currentDesc, long len, boolean doThrow) {
        assert (JSRuntime.isValidArrayLength(len));
        assert (!currentDesc.getConfigurable());
        boolean currentWritable = currentDesc.getWritable();
        boolean currentEnumerable = currentDesc.getEnumerable();
        boolean newWritable = descriptor.getIfHasWritable(currentWritable);
        boolean newEnumerable = descriptor.getIfHasEnumerable(currentEnumerable);
        boolean newConfigurable = descriptor.getIfHasConfigurable(false);
        if (newConfigurable || newEnumerable != currentEnumerable) {
            return DefinePropertyUtil.reject(doThrow, CANNOT_REDEFINE_PROPERTY_LENGTH);
        }
        if (!(currentWritable != newWritable || currentEnumerable != newEnumerable || descriptor.hasValue() && len != this.getLength(thisObj))) {
            return true;
        }
        if (!currentWritable) {
            return DefinePropertyUtil.reject(doThrow, LENGTH_PROPERTY_NOT_WRITABLE);
        }
        try {
            this.setLength(thisObj, len, doThrow);
        }
        finally {
            int newAttr = JSAttributes.fromConfigurableEnumerableWritable(newConfigurable, newEnumerable, newWritable);
            JSObjectUtil.changePropertyFlags(thisObj, LENGTH, newAttr);
        }
        if (!newWritable) {
            JSAbstractArray.setLengthNotWritable(thisObj);
        }
        return true;
    }

    protected boolean defineOwnPropertyIndex(JSDynamicObject thisObj, TruffleString name, PropertyDescriptor descriptor, boolean doThrow) {
        PropertyDescriptor lenDesc;
        assert (Strings.isTString(name));
        long index = JSRuntime.toUInt32(name);
        if (index >= this.getLength(thisObj) && !(lenDesc = this.getOwnProperty(thisObj, LENGTH)).getWritable()) {
            DefinePropertyUtil.reject(doThrow, ARRAY_LENGTH_NOT_WRITABLE);
        }
        boolean wasNotExtensible = !JSShape.isExtensible(thisObj.getShape());
        boolean success = JSObject.defineOwnProperty(this.makeSlowArray(thisObj), name, descriptor, doThrow);
        if (wasNotExtensible) assert (!JSShape.isExtensible(thisObj.getShape()));
        return success;
    }

    protected JSDynamicObject makeSlowArray(JSDynamicObject thisObj) {
        CompilerAsserts.neverPartOfCompilation(MAKE_SLOW_ARRAY_NEVER_PART_OF_COMPILATION_MESSAGE);
        if (this.isSlowArray(thisObj)) {
            return thisObj;
        }
        assert (!JSSlowArray.isJSSlowArray(thisObj));
        JSDynamicObject.setJSClass(thisObj, JSSlowArray.INSTANCE);
        JSContext context = JSObject.getJSContext(thisObj);
        context.getFastArrayAssumption().invalidate("create slow ArgumentsObject");
        if (JSAbstractArray.isArrayPrototype(thisObj)) {
            context.getArrayPrototypeNoElementsAssumption().invalidate("Array.prototype has no elements");
        }
        assert (JSSlowArray.isJSSlowArray(thisObj));
        return thisObj;
    }

    private static boolean isArrayPrototype(JSDynamicObject thisObj) {
        return JSAbstractArray.arrayGetArrayType(thisObj) instanceof ConstantEmptyPrototypeArray;
    }

    @Override
    public boolean testIntegrityLevel(JSDynamicObject thisObj, boolean frozen) {
        ScriptArray array = JSAbstractArray.arrayGetArrayType(thisObj);
        boolean arrayIs = frozen ? array.isFrozen() : array.isSealed();
        return arrayIs && JSNonProxy.testIntegrityLevelFast(thisObj, frozen);
    }

    @Override
    public boolean setIntegrityLevel(JSDynamicObject thisObj, boolean freeze, boolean doThrow) {
        if (this.testIntegrityLevel(thisObj, freeze)) {
            return true;
        }
        ScriptArray arr = JSAbstractArray.arrayGetArrayType(thisObj);
        JSAbstractArray.arraySetArrayType(thisObj, freeze ? arr.freeze() : arr.seal());
        return super.setIntegrityLevelFast(thisObj, freeze);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public final boolean preventExtensions(JSDynamicObject thisObj, boolean doThrow) {
        boolean result = super.preventExtensions(thisObj, doThrow);
        ScriptArray arr = JSAbstractArray.arrayGetArrayType(thisObj);
        JSAbstractArray.arraySetArrayType(thisObj, arr.preventExtensions());
        assert (!this.isExtensible(thisObj));
        return result;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean delete(JSDynamicObject thisObj, Object key, boolean isStrict) {
        long index = JSRuntime.propertyKeyToArrayIndex(key);
        if (index >= 0L) {
            return this.delete(thisObj, index, isStrict);
        }
        return super.delete(thisObj, key, isStrict);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean setPrototypeOf(JSDynamicObject thisObj, JSDynamicObject newPrototype) {
        JSObject.getJSContext(thisObj).getArrayPrototypeNoElementsAssumption().invalidate(ARRAY_PROTOTYPE_NO_ELEMENTS_INVALIDATION);
        return super.setPrototypeOf(thisObj, newPrototype);
    }

    @Override
    public PropertyDescriptor getOwnProperty(JSDynamicObject thisObj, Object key) {
        return JSAbstractArray.ordinaryGetOwnPropertyArray(thisObj, key);
    }

    @CompilerDirectives.TruffleBoundary
    public static PropertyDescriptor ordinaryGetOwnPropertyArray(JSDynamicObject thisObj, Object key) {
        ScriptArray array;
        assert (JSRuntime.isPropertyKey(key));
        long idx = JSRuntime.propertyKeyToArrayIndex(key);
        if (JSRuntime.isArrayIndex(idx) && (array = JSAbstractArray.arrayGetArrayType(thisObj)).hasElement(thisObj, idx)) {
            Object value2 = array.getElement(thisObj, idx);
            return PropertyDescriptor.createData(value2, true, !array.isFrozen(), !array.isSealed());
        }
        Property prop = thisObj.getShape().getProperty(key);
        if (prop == null) {
            return null;
        }
        return JSNonProxy.ordinaryGetOwnPropertyIntl(thisObj, key, prop);
    }

    @Override
    public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
        if (JavaScriptLanguage.get(null).getJSContext().isOptionNashornCompatibilityMode()) {
            return this.defaultToString(obj);
        }
        return JSRuntime.objectToDisplayString(obj, allowSideEffects, format, depth, null);
    }

    protected boolean isSlowArray(JSDynamicObject thisObj) {
        return JSSlowArray.isJSSlowArray(thisObj);
    }

    @Override
    public boolean usesOrdinaryGetOwnProperty() {
        return false;
    }

    static final class DefaultJSArrayDoubleComparator
    implements Comparator<Object> {
        DefaultJSArrayDoubleComparator() {
        }

        @Override
        public int compare(Object arg0, Object arg1) {
            double d2;
            double d1 = JSRuntime.doubleValue((Number)arg0);
            if (d1 == (d2 = JSRuntime.doubleValue((Number)arg1))) {
                return 0;
            }
            if (d1 <= 0.0 && d2 > 0.0) {
                return -1;
            }
            if (d2 <= 0.0 && d1 > 0.0) {
                return 1;
            }
            TruffleString str0 = JSRuntime.doubleToString(d1);
            TruffleString str1 = JSRuntime.doubleToString(d2);
            return Strings.compareTo(str0, str1);
        }
    }

    static final class DefaultJSArrayIntegerComparator
    implements Comparator<Object> {
        DefaultJSArrayIntegerComparator() {
        }

        @Override
        public int compare(Object arg0, Object arg1) {
            int i2;
            int i1 = (int)JSRuntime.toInteger((Number)arg0);
            if (i1 == (i2 = (int)JSRuntime.toInteger((Number)arg1))) {
                return 0;
            }
            if (i1 <= 0 && i2 > 0) {
                return -1;
            }
            if (i2 <= 0 && i1 > 0) {
                return 1;
            }
            TruffleString str0 = Strings.fromInt(i1);
            TruffleString str1 = Strings.fromInt(i2);
            return Strings.compareTo(str0, str1);
        }
    }

    static final class DefaultJSArrayComparator
    implements Comparator<Object> {
        DefaultJSArrayComparator() {
        }

        @Override
        public int compare(Object arg0, Object arg1) {
            if (arg0 == Undefined.instance) {
                if (arg1 == Undefined.instance) {
                    return 0;
                }
                return 1;
            }
            if (arg1 == Undefined.instance) {
                return -1;
            }
            TruffleString str0 = JSRuntime.toString(arg0);
            TruffleString str1 = JSRuntime.toString(arg1);
            if (str0 == null) {
                if (str1 == null) {
                    return 0;
                }
                return 1;
            }
            if (str1 == null) {
                return -1;
            }
            return Strings.compareTo(str0, str1);
        }
    }
}

