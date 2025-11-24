
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.SparseArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.util.DefinePropertyUtil;

public final class JSSlowArray
extends JSAbstractArray {
    public static final TruffleString CLASS_NAME = Strings.constant("Array");
    public static final JSSlowArray INSTANCE = new JSSlowArray();

    private JSSlowArray() {
    }

    public static boolean isJSSlowArray(Object obj) {
        return JSDynamicObject.isJSDynamicObject(obj) && JSSlowArray.isJSSlowArray((JSDynamicObject)obj);
    }

    public static boolean isJSSlowArray(JSDynamicObject obj) {
        return JSSlowArray.isInstance(obj, (JSClass)INSTANCE);
    }

    @Override
    public TruffleString getClassName(JSDynamicObject object) {
        return CLASS_NAME;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Object getOwnHelper(JSDynamicObject store, Object thisObj, long index, Node encapsulatingNode) {
        TruffleString indexAsString = Strings.fromLong(index);
        if (JSOrdinary.INSTANCE.hasOwnProperty(store, indexAsString)) {
            return JSOrdinary.INSTANCE.getOwnHelper(store, thisObj, indexAsString, encapsulatingNode);
        }
        return super.getOwnHelper(store, thisObj, index, encapsulatingNode);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean set(JSDynamicObject thisObj, long index, Object value2, Object receiver, boolean isStrict, Node encapsulatingNode) {
        TruffleString indexAsString = Strings.fromLong(index);
        if (JSOrdinary.INSTANCE.hasOwnProperty(thisObj, indexAsString)) {
            return JSSlowArray.ordinarySet(thisObj, indexAsString, value2, receiver, isStrict, encapsulatingNode);
        }
        return super.set(thisObj, index, value2, receiver, isStrict, encapsulatingNode);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean delete(JSDynamicObject thisObj, long index, boolean isStrict) {
        ScriptArray array = JSSlowArray.arrayAccess().getArrayType(thisObj);
        if (array.hasElement(thisObj, index)) {
            ScriptArray arrayType = JSSlowArray.arrayGetArrayType(thisObj);
            if (arrayType.canDeleteElement(thisObj, index, isStrict)) {
                JSSlowArray.arraySetArrayType(thisObj, arrayType.deleteElement(thisObj, index, isStrict));
                return true;
            }
            return false;
        }
        return JSOrdinary.INSTANCE.delete(thisObj, index, isStrict);
    }

    @Override
    protected JSDynamicObject makeSlowArray(JSDynamicObject thisObj) {
        assert (JSSlowArray.isJSSlowArray(thisObj));
        return thisObj;
    }

    @Override
    protected boolean defineOwnPropertyIndex(JSDynamicObject thisObj, TruffleString name, PropertyDescriptor descriptor, boolean doThrow) {
        boolean succeeded;
        ScriptArray arrayType;
        PropertyDescriptor desc;
        assert (Strings.isTString(name));
        long index = JSRuntime.toUInt32(name);
        if (index >= this.getLength(thisObj) && !(desc = this.getOwnProperty(thisObj, LENGTH)).getWritable()) {
            return DefinePropertyUtil.reject(doThrow, "array length is not writable");
        }
        if (this.getLength(thisObj) <= index) {
            this.setLength(thisObj, index + 1L, doThrow);
        }
        if ((arrayType = JSSlowArray.arrayGetArrayType(thisObj)).hasElement(thisObj, index) && !JSOrdinary.INSTANCE.hasOwnProperty(thisObj, name)) {
            JSContext context = JSObject.getJSContext(thisObj);
            boolean wasNotExtensible = !JSShape.isExtensible(thisObj.getShape());
            JSObjectUtil.putDataProperty(context, thisObj, name, this.get(thisObj, index), JSAttributes.fromConfigurableEnumerableWritable(!arrayType.isSealed(), true, !arrayType.isFrozen()));
            if (wasNotExtensible) assert (!JSShape.isExtensible(thisObj.getShape()));
            JSSlowArray.arraySetArrayType(thisObj, arrayType.deleteElementImpl(thisObj, index, false));
        }
        if (!(succeeded = JSSlowArray.jsDefineProperty(thisObj, index, descriptor, false))) {
            JSContext context = JavaScriptLanguage.getCurrentLanguage().getJSContext();
            return DefinePropertyUtil.reject(doThrow, context.isOptionNashornCompatibilityMode() ? "cannot set property" : "Cannot redefine property");
        }
        return true;
    }

    private static boolean jsDefineProperty(JSDynamicObject thisObj, long index, PropertyDescriptor descriptor, boolean doThrow) {
        ScriptArray internalArray = JSSlowArray.arrayAccess().getArrayType(thisObj);
        boolean copyValue = internalArray.hasElement(thisObj, index) && !descriptor.hasValue() && !descriptor.hasGet();
        boolean succeed = DefinePropertyUtil.ordinaryDefineOwnProperty(thisObj, Strings.fromLong(index), descriptor, doThrow);
        if (copyValue) {
            JSObject.set(thisObj, index, internalArray.getElement(thisObj, index), doThrow, null);
        }
        return succeed;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean setLength(JSDynamicObject thisObj, long length, boolean doThrow) {
        if (this.testIntegrityLevel(thisObj, true)) {
            throw Errors.createTypeError("cannot set length of a frozen array");
        }
        long oldLen = this.getLength(thisObj);
        long newLen = length;
        ScriptArray internalArray = JSSlowArray.arrayGetArrayType(thisObj);
        boolean sealed = internalArray.isSealed();
        boolean deleteSucceeded = true;
        if (newLen < oldLen) {
            for (long idx = oldLen - 1L; idx >= newLen; --idx) {
                deleteSucceeded = internalArray.hasElement(thisObj, idx) ? !sealed : JSOrdinary.INSTANCE.delete(thisObj, idx, false);
                if (deleteSucceeded) continue;
                newLen = idx + 1L;
                break;
            }
        }
        if (newLen > Integer.MAX_VALUE && !(internalArray instanceof SparseArray)) {
            internalArray = SparseArray.makeSparseArray(thisObj, internalArray);
        }
        JSSlowArray.arraySetArrayType(thisObj, internalArray.setLength(thisObj, newLen, doThrow));
        if (!deleteSucceeded) {
            JSContext context = JavaScriptLanguage.getCurrentLanguage().getJSContext();
            return DefinePropertyUtil.reject(doThrow, context.isOptionNashornCompatibilityMode() ? "cannot set property: length" : "Cannot redefine property: length");
        }
        return true;
    }
}

