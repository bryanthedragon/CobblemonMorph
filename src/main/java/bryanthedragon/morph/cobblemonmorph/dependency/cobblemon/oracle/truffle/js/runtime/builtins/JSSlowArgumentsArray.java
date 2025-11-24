
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArgumentsArray;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public final class JSSlowArgumentsArray
extends JSAbstractArgumentsArray {
    static final JSSlowArgumentsArray INSTANCE = new JSSlowArgumentsArray();

    private JSSlowArgumentsArray() {
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean delete(JSDynamicObject thisObj, long index, boolean isStrict) {
        boolean wasDeleted;
        if (JSSlowArgumentsArray.isSealedOrFrozen(thisObj)) {
            return true;
        }
        boolean isMappedArguments = JSSlowArgumentsArray.isMappedArguments(thisObj);
        boolean indexDisconnected = isMappedArguments && JSSlowArgumentsArray.wasIndexDisconnected(thisObj, index);
        Object oldValue = indexDisconnected ? null : this.get(thisObj, index);
        ScriptArray arrayType = JSSlowArgumentsArray.arrayGetArrayType(thisObj);
        if (arrayType.hasElement(thisObj, index)) {
            JSSlowArgumentsArray.arraySetArrayType(thisObj, arrayType.deleteElement(thisObj, index, false));
            wasDeleted = true;
        } else {
            wasDeleted = JSOrdinary.INSTANCE.delete(thisObj, index, isStrict);
        }
        if (wasDeleted && isMappedArguments && !indexDisconnected) {
            JSSlowArgumentsArray.disconnectIndex(thisObj, index, oldValue);
        }
        return wasDeleted;
    }

    private static boolean isSealedOrFrozen(JSDynamicObject thisObj) {
        ScriptArray array = JSSlowArgumentsArray.arrayGetArrayType(thisObj);
        return array.isSealed() || array.isFrozen();
    }

    public static boolean isJSSlowArgumentsObject(JSDynamicObject obj) {
        return JSSlowArgumentsArray.isInstance(obj, (JSClass)INSTANCE);
    }

    @Override
    protected JSDynamicObject makeSlowArray(JSDynamicObject thisObj) {
        assert (JSSlowArgumentsArray.isJSSlowArgumentsObject(thisObj));
        return thisObj;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean set(JSDynamicObject thisObj, long index, Object value2, Object receiver, boolean isStrict, Node encapsulatingNode) {
        TruffleString indexAsString = Strings.fromLong(index);
        if (JSOrdinary.INSTANCE.hasOwnProperty(thisObj, indexAsString)) {
            return JSSlowArgumentsArray.ordinarySet(thisObj, indexAsString, value2, receiver, isStrict, encapsulatingNode);
        }
        return super.set(thisObj, index, value2, receiver, isStrict, encapsulatingNode);
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
}

