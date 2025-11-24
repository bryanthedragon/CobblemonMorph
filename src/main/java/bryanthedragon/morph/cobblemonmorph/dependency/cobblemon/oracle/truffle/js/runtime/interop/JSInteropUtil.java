
package com.oracle.truffle.js.runtime.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.ArrayList;
import java.util.List;

public final class JSInteropUtil {
    private JSInteropUtil() {
    }

    public static long getArraySize(Object foreignObj, InteropLibrary interop, Node originatingNode) {
        try {
            return interop.getArraySize(foreignObj);
        }
        catch (UnsupportedMessageException e) {
            throw Errors.createTypeErrorInteropException(foreignObj, e, "getArraySize", originatingNode);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public static Object get(Object obj, Object key) {
        assert (JSRuntime.isPropertyKey(key));
        if (JSDynamicObject.isJSDynamicObject(obj)) {
            return JSObject.get((JSDynamicObject)obj, key);
        }
        return JSInteropUtil.readMemberOrDefault(obj, key, Undefined.instance);
    }

    @CompilerDirectives.TruffleBoundary
    public static Object get(Object obj, long index) {
        if (JSDynamicObject.isJSDynamicObject(obj)) {
            return JSObject.get((JSDynamicObject)obj, index);
        }
        return JSInteropUtil.readArrayElementOrDefault(obj, index, Undefined.instance);
    }

    public static Object readMemberOrDefault(Object obj, Object member, Object defaultValue) {
        return JSInteropUtil.readMemberOrDefault(obj, member, defaultValue, InteropLibrary.getUncached(), ImportValueNode.getUncached(), null);
    }

    public static Object readMemberOrDefault(Object obj, Object member, Object defaultValue, InteropLibrary interop, ImportValueNode importValue, Node originatingNode) {
        if (!Strings.isTString(member)) {
            return defaultValue;
        }
        try {
            return importValue.executeWithTarget(interop.readMember(obj, Strings.toJavaString((TruffleString)member)));
        }
        catch (UnknownIdentifierException e) {
            return defaultValue;
        }
        catch (UnsupportedMessageException e) {
            throw Errors.createTypeErrorInteropException(obj, e, "readMember", member, originatingNode);
        }
    }

    public static Object readArrayElementOrDefault(Object obj, long index, Object defaultValue, InteropLibrary interop, ImportValueNode importValue, Node originatingNode) {
        try {
            return importValue.executeWithTarget(interop.readArrayElement(obj, index));
        }
        catch (InvalidArrayIndexException e) {
            return defaultValue;
        }
        catch (UnsupportedMessageException e) {
            throw Errors.createTypeErrorInteropException(obj, e, "readArrayElement", index, originatingNode);
        }
    }

    public static Object readArrayElementOrDefault(Object obj, long index, Object defaultValue) {
        return JSInteropUtil.readArrayElementOrDefault(obj, index, defaultValue, InteropLibrary.getUncached(), ImportValueNode.getUncached(), null);
    }

    public static void writeMember(Object obj, Object member, Object value2) {
        JSInteropUtil.writeMember(obj, member, value2, InteropLibrary.getUncached(), ExportValueNode.getUncached(), null);
    }

    public static void writeMember(Object obj, Object member, Object value2, InteropLibrary interop, ExportValueNode exportValue, Node originatingNode) {
        if (!Strings.isTString(member)) {
            return;
        }
        try {
            interop.writeMember(obj, Strings.toJavaString((TruffleString)member), exportValue.execute(value2));
        }
        catch (UnknownIdentifierException | UnsupportedMessageException | UnsupportedTypeException e) {
            throw Errors.createTypeErrorInteropException(obj, e, "writeMember", member, originatingNode);
        }
    }

    public static Object toPrimitiveOrDefault(Object obj, Object defaultValue, InteropLibrary interop, Node originatingNode) {
        if (interop.isNull(obj)) {
            return Null.instance;
        }
        try {
            if (interop.isBoolean(obj)) {
                return interop.asBoolean(obj);
            }
            if (interop.isString(obj)) {
                return interop.asTruffleString(obj);
            }
            if (interop.isNumber(obj)) {
                if (interop.fitsInInt(obj)) {
                    return interop.asInt(obj);
                }
                if (interop.fitsInLong(obj)) {
                    return interop.asLong(obj);
                }
                if (interop.fitsInDouble(obj)) {
                    return interop.asDouble(obj);
                }
            }
        }
        catch (UnsupportedMessageException e) {
            throw Errors.createTypeErrorUnboxException(obj, e, originatingNode);
        }
        return defaultValue;
    }

    @CompilerDirectives.TruffleBoundary
    public static List<Object> keys(Object obj) {
        try {
            Object keysObj = InteropLibrary.getUncached().getMembers(obj);
            InteropLibrary keysInterop = InteropLibrary.getUncached(keysObj);
            long size = keysInterop.getArraySize(keysObj);
            if (size < 0L || size >= Integer.MAX_VALUE) {
                throw Errors.createRangeErrorInvalidArrayLength();
            }
            ArrayList<Object> keys = new ArrayList<Object>((int)size);
            int i = 0;
            while ((long)i < size) {
                Object key = keysInterop.readArrayElement(keysObj, i);
                assert (InteropLibrary.getUncached().isString(key));
                keys.add(InteropLibrary.getUncached().asTruffleString(key));
                ++i;
            }
            return keys;
        }
        catch (InvalidArrayIndexException | UnsupportedMessageException e) {
            throw Errors.createTypeErrorInteropException(obj, e, "readArrayElement", null);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean hasProperty(Object obj, Object key) {
        if (key instanceof TruffleString) {
            return InteropLibrary.getUncached().isMemberExisting(obj, Strings.toJavaString((TruffleString)key));
        }
        return false;
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean remove(Object obj, Object key) {
        if (key instanceof TruffleString) {
            try {
                InteropLibrary.getUncached().removeMember(obj, Strings.toJavaString((TruffleString)key));
            }
            catch (UnknownIdentifierException | UnsupportedMessageException e) {
                throw Errors.createTypeErrorInteropException(obj, e, "removeMember", key, null);
            }
            return true;
        }
        return false;
    }

    @CompilerDirectives.TruffleBoundary
    public static Object call(Object function, Object[] args) {
        Object[] exportedArgs = JSRuntime.exportValueArray(args);
        try {
            return JSRuntime.importValue(InteropLibrary.getUncached().execute(function, exportedArgs));
        }
        catch (ArityException | UnsupportedMessageException | UnsupportedTypeException e) {
            throw Errors.createTypeErrorInteropException(function, e, "execute", null);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public static Object construct(Object target, Object[] args) {
        Object[] exportedArgs = JSRuntime.exportValueArray(args);
        try {
            return JSRuntime.importValue(InteropLibrary.getUncached().instantiate(target, exportedArgs));
        }
        catch (ArityException | UnsupportedMessageException | UnsupportedTypeException e) {
            throw Errors.createTypeErrorInteropException(target, e, "instantiate", null);
        }
    }

    public static boolean isBoxedPrimitive(Object receiver, InteropLibrary interop) {
        return interop.isString(receiver) || interop.isNumber(receiver) || interop.isBoolean(receiver);
    }

    public static PropertyDescriptor getOwnProperty(Object object, TruffleString propertyKey) {
        return JSInteropUtil.getOwnProperty(object, propertyKey, InteropLibrary.getUncached(), ImportValueNode.getUncached(), TruffleString.ReadCharUTF16Node.getUncached());
    }

    public static PropertyDescriptor getOwnProperty(Object object, TruffleString propertyKey, InteropLibrary interop, ImportValueNode importValueNode, TruffleString.ReadCharUTF16Node charAtNode) {
        try {
            PropertyDescriptor desc;
            String key = Strings.toJavaString(propertyKey);
            if (interop.hasMembers(object) && interop.isMemberExisting(object, key) && (desc = JSInteropUtil.getExistingMemberProperty(object, key, interop, importValueNode)) != null) {
                return desc;
            }
            long index = JSRuntime.propertyNameToArrayIndex(propertyKey, charAtNode);
            if (JSRuntime.isArrayIndex(index) && interop.hasArrayElements(object)) {
                return JSInteropUtil.getArrayElementProperty(object, index, interop, importValueNode);
            }
        }
        catch (InteropException interopException) {
            // empty catch block
        }
        return null;
    }

    public static PropertyDescriptor getExistingMemberProperty(Object object, String key, InteropLibrary interop, ImportValueNode importValueNode) throws InteropException {
        assert (interop.hasMembers(object) && interop.isMemberExisting(object, key));
        if (interop.isMemberReadable(object, key)) {
            return PropertyDescriptor.createData(importValueNode.executeWithTarget(interop.readMember(object, key)), !interop.isMemberInternal(object, key), interop.isMemberWritable(object, key), interop.isMemberRemovable(object, key));
        }
        return null;
    }

    public static PropertyDescriptor getArrayElementProperty(Object object, long index, InteropLibrary interop, ImportValueNode importValueNode) throws InteropException {
        assert (interop.hasArrayElements(object) && JSRuntime.isArrayIndex(index));
        if (interop.isArrayElementExisting(object, index) && interop.isArrayElementReadable(object, index)) {
            return PropertyDescriptor.createData(importValueNode.executeWithTarget(interop.readArrayElement(object, index)), true, interop.isArrayElementWritable(object, index), interop.isArrayElementRemovable(object, index));
        }
        return null;
    }
}

