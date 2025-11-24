
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.builtins.JSArgumentsArray;
import com.oracle.truffle.js.runtime.builtins.JSArgumentsObject;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSSlowArgumentsArray;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.util.DefinePropertyUtil;
import java.util.Map;

public abstract class JSAbstractArgumentsArray
extends JSAbstractArray {
    public static final TruffleString CALLEE = Strings.constant("callee");
    public static final TruffleString CALLER = Strings.constant("caller");
    protected static final TruffleString CLASS_NAME = Strings.constant("Arguments");

    @Override
    @CompilerDirectives.TruffleBoundary
    public long getLength(JSDynamicObject thisObj) {
        Object lengthValue = this.get(thisObj, LENGTH);
        return JSRuntime.toInteger(JSRuntime.toNumber(lengthValue));
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean delete(JSDynamicObject thisObj, long index, boolean isStrict) {
        if (JSAbstractArgumentsArray.isMappedArguments(thisObj)) {
            this.makeSlowArray(thisObj);
            return JSObject.delete(thisObj, index, isStrict);
        }
        return super.delete(thisObj, index, isStrict);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean delete(JSDynamicObject thisObj, Object key, boolean isStrict) {
        long index = JSRuntime.propertyKeyToArrayIndex(key);
        if (index >= 0L && JSRuntime.isArrayIndex(index)) {
            return this.delete(thisObj, index, isStrict);
        }
        return super.delete(thisObj, key, isStrict);
    }

    @Override
    public TruffleString getClassName(JSDynamicObject object) {
        return CLASS_NAME;
    }

    protected static boolean isMappedArguments(JSDynamicObject thisObj) {
        return thisObj instanceof JSArgumentsObject.Mapped;
    }

    @Override
    protected JSDynamicObject makeSlowArray(JSDynamicObject thisObj) {
        CompilerAsserts.neverPartOfCompilation("do not convert to slow array from compiled code");
        if (this.isSlowArray(thisObj)) {
            return thisObj;
        }
        assert (JSArgumentsArray.isJSFastArgumentsObject(thisObj));
        JSDynamicObject.setJSClass(thisObj, JSSlowArgumentsArray.INSTANCE);
        if (JSAbstractArgumentsArray.isMappedArguments(thisObj)) {
            ((JSArgumentsObject.Mapped)thisObj).initDisconnectedIndices();
        }
        JSObject.getJSContext(thisObj).getFastArgumentsObjectAssumption().invalidate("create slow ArgumentsObject");
        return thisObj;
    }

    public static int getConnectedArgumentCount(JSDynamicObject argumentsArray) {
        assert (JSArgumentsArray.isJSArgumentsObject(argumentsArray));
        return ((JSArgumentsObject.Mapped)argumentsArray).getConnectedArgumentCount();
    }

    @CompilerDirectives.TruffleBoundary
    private static Map<Long, Object> getDisconnectedIndices(JSDynamicObject argumentsArray) {
        assert (JSAbstractArgumentsArray.hasDisconnectedIndices(argumentsArray));
        return ((JSArgumentsObject.Mapped)argumentsArray).getDisconnectedIndices();
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean wasIndexDisconnected(JSDynamicObject argumentsArray, long index) {
        assert (JSAbstractArgumentsArray.hasDisconnectedIndices(argumentsArray));
        return JSAbstractArgumentsArray.getDisconnectedIndices(argumentsArray).containsKey(index);
    }

    @CompilerDirectives.TruffleBoundary
    public static Object getDisconnectedIndexValue(JSDynamicObject argumentsArray, long index) {
        assert (JSAbstractArgumentsArray.hasDisconnectedIndices(argumentsArray));
        assert (JSAbstractArgumentsArray.wasIndexDisconnected(argumentsArray, index));
        return JSAbstractArgumentsArray.getDisconnectedIndices(argumentsArray).get(index);
    }

    @CompilerDirectives.TruffleBoundary
    public static Object setDisconnectedIndexValue(JSDynamicObject argumentsArray, long index, Object value2) {
        assert (JSAbstractArgumentsArray.hasDisconnectedIndices(argumentsArray));
        assert (JSAbstractArgumentsArray.wasIndexDisconnected(argumentsArray, index));
        JSAbstractArgumentsArray.getDisconnectedIndices(argumentsArray).put(index, value2);
        return value2;
    }

    @CompilerDirectives.TruffleBoundary
    public static void disconnectIndex(JSDynamicObject argumentsArray, long index, Object oldValue) {
        if (!JSAbstractArgumentsArray.hasDisconnectedIndices(argumentsArray)) {
            JSArgumentsArray.INSTANCE.makeSlowArray(argumentsArray);
        }
        JSAbstractArgumentsArray.getDisconnectedIndices(argumentsArray).put(index, oldValue);
    }

    public static boolean hasDisconnectedIndices(JSDynamicObject argumentsArray) {
        return JSSlowArgumentsArray.isJSSlowArgumentsObject(argumentsArray);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean defineOwnProperty(JSDynamicObject thisObj, Object key, PropertyDescriptor descriptor, boolean doThrow) {
        boolean allowed;
        boolean isMappedArguments = JSAbstractArgumentsArray.isMappedArguments(thisObj);
        long index = JSRuntime.propertyKeyToArrayIndex(key);
        Object oldValue = null;
        boolean isIndexConnected = false;
        if (index >= 0L) {
            this.makeSlowArray(thisObj);
            isIndexConnected = isMappedArguments && !JSAbstractArgumentsArray.wasIndexDisconnected(thisObj, index);
            oldValue = super.get(thisObj, index);
            ScriptArray arrayType = JSAbstractArgumentsArray.arrayGetArrayType(thisObj);
            if (arrayType.hasElement(thisObj, index)) {
                JSContext context = JSObject.getJSContext(thisObj);
                JSObjectUtil.putDataProperty(context, thisObj, key, oldValue, JSAttributes.getDefault());
                if (arrayType.canDeleteElement(thisObj, index, false)) {
                    JSAbstractArgumentsArray.arraySetArrayType(thisObj, arrayType.deleteElement(thisObj, index, false));
                }
            }
        }
        if (!(allowed = DefinePropertyUtil.ordinaryDefineOwnProperty(thisObj, key, descriptor, doThrow))) {
            return DefinePropertyUtil.reject(doThrow, "not allowed to defineProperty on an arguments object");
        }
        if (isIndexConnected) {
            assert (Strings.isTString(key)) : key;
            JSAbstractArgumentsArray.definePropertyMapped(thisObj, (TruffleString)key, descriptor, index, oldValue, thisObj);
        }
        return true;
    }

    @CompilerDirectives.TruffleBoundary
    private static void definePropertyMapped(JSDynamicObject thisObj, TruffleString name, PropertyDescriptor descriptor, long index, Object oldValueParam, JSDynamicObject obj) {
        if (descriptor.isAccessorDescriptor()) {
            JSAbstractArgumentsArray.disconnectIndex(thisObj, index, oldValueParam);
        } else {
            Object value2;
            Object oldValue = oldValueParam;
            if (descriptor.hasValue()) {
                value2 = descriptor.getValue();
                JSObject.set(obj, name, value2);
                oldValue = value2;
            }
            if (descriptor.hasWritable() && Boolean.FALSE.equals(value2 = Boolean.valueOf(descriptor.getWritable()))) {
                JSAbstractArgumentsArray.disconnectIndex(thisObj, index, oldValue);
            }
        }
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public PropertyDescriptor getOwnProperty(JSDynamicObject thisObj, Object key) {
        assert (JSRuntime.isPropertyKey(key));
        PropertyDescriptor desc = JSAbstractArgumentsArray.ordinaryGetOwnPropertyArray(thisObj, key);
        if (desc == null) {
            return null;
        }
        long index = JSRuntime.propertyKeyToArrayIndex(key);
        if (index >= 0L) {
            boolean isMapped;
            boolean bl = isMapped = JSArgumentsArray.isJSFastArgumentsObject(thisObj) || JSAbstractArgumentsArray.isMappedArguments(thisObj) && !JSAbstractArgumentsArray.wasIndexDisconnected(thisObj, index);
            if (isMapped) {
                desc.setValue(super.get(thisObj, index));
            }
        }
        if (desc.isDataDescriptor() && CALLER.equals(key) && JSFunction.isJSFunction(desc.getValue()) && JSFunction.isStrict((JSDynamicObject)desc.getValue())) {
            throw Errors.createTypeError("caller not allowed in strict mode");
        }
        return desc;
    }

    @Override
    protected boolean isSlowArray(JSDynamicObject thisObj) {
        return JSSlowArgumentsArray.isJSSlowArgumentsObject(thisObj);
    }
}

