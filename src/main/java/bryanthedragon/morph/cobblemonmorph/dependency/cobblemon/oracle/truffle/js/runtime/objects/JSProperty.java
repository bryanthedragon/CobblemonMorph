
package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.objects.Accessor;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.PropertyProxy;
import com.oracle.truffle.js.runtime.objects.Undefined;

public class JSProperty {
    public static final int ACCESSOR = 8;
    public static final int PROXY = 16;
    public static final int CONST = 32;

    @CompilerDirectives.TruffleBoundary
    public String toString(Property property) {
        return "\"" + property.getKey() + "\"" + JSProperty.getAttributeString(property) + ":" + property.getLocation();
    }

    private static String getAttributeString(Property property) {
        String negative = JSProperty.getAttributeString(property, false);
        return negative.isEmpty() ? "" : "-" + negative;
    }

    protected static String getAttributeString(Property property, boolean positive) {
        return (JSProperty.isEnumerable(property) == positive ? "e" : "") + (JSProperty.isConfigurable(property) == positive ? "c" : "") + (JSProperty.isData(property) && JSProperty.isWritable(property) == positive ? "w" : "");
    }

    public static Object getValue(Property property, JSDynamicObject store, Object thisObj, Node encapsulatingNode) {
        Object value2 = property.getLocation().get(store);
        if (JSProperty.isAccessor(property)) {
            return JSProperty.getValueAccessor(thisObj, value2, encapsulatingNode);
        }
        if (JSProperty.isProxy(property)) {
            return ((PropertyProxy)value2).get(store);
        }
        assert (JSProperty.isData(property));
        return value2;
    }

    private static Object getValueAccessor(Object thisObj, Object value2, Node encapsulatingNode) {
        Object getter = ((Accessor)value2).getGetter();
        if (getter != Undefined.instance) {
            return JSRuntime.call(getter, thisObj, JSArguments.EMPTY_ARGUMENTS_ARRAY, encapsulatingNode);
        }
        return Undefined.instance;
    }

    public static boolean setValue(Property property, JSDynamicObject store, Object thisObj, Object value2, boolean isStrict, Node encapsulatingNode) {
        if (JSProperty.isAccessor(property)) {
            return JSProperty.setValueAccessor(property, store, thisObj, value2, isStrict, encapsulatingNode);
        }
        if (JSProperty.isWritable(property)) {
            if (JSProperty.isProxy(property)) {
                return JSProperty.setValueProxy(property, store, thisObj, value2, isStrict);
            }
            assert (JSProperty.isData(property));
            assert (!(value2 instanceof Accessor) && !(value2 instanceof PropertyProxy));
            boolean success = Properties.putIfPresentUncached(store, property.getKey(), value2);
            assert (success);
            return true;
        }
        if (isStrict) {
            throw Errors.createTypeErrorNotWritableProperty(property.getKey(), thisObj);
        }
        return false;
    }

    private static boolean setValueAccessor(Property property, JSDynamicObject store, Object thisObj, Object value2, boolean isStrict, Node encapsulatingNode) {
        Object setter = ((Accessor)JSDynamicObject.getOrNull(store, property.getKey())).getSetter();
        if (setter != Undefined.instance) {
            JSRuntime.call(setter, thisObj, new Object[]{value2}, encapsulatingNode);
            return true;
        }
        if (isStrict) {
            throw Errors.createTypeErrorCannotSetAccessorProperty(property.getKey(), store);
        }
        return false;
    }

    private static boolean setValueProxy(Property property, JSDynamicObject store, Object thisObj, Object value2, boolean isStrict) {
        boolean ret = ((PropertyProxy)JSDynamicObject.getOrNull(store, property.getKey())).set(store, value2);
        if (!ret && isStrict) {
            throw Errors.createTypeErrorNotWritableProperty(property.getKey(), thisObj);
        }
        return ret;
    }

    public static boolean isConfigurable(Property property) {
        return (property.getFlags() & 2) == 0;
    }

    public static boolean isEnumerable(Property property) {
        return (property.getFlags() & 1) == 0;
    }

    public static boolean isWritable(Property property) {
        return (property.getFlags() & 4) == 0;
    }

    public static boolean isProxy(Property property) {
        return (property.getFlags() & 0x10) != 0;
    }

    public static boolean isAccessor(Property property) {
        return (property.getFlags() & 8) != 0;
    }

    public static boolean isData(Property property) {
        return (property.getFlags() & 8) == 0;
    }

    public static boolean isConst(Property property) {
        return (property.getFlags() & 0x20) != 0;
    }

    public static boolean isConfigurable(int flags) {
        return (flags & 2) == 0;
    }

    public static boolean isEnumerable(int flags) {
        return (flags & 1) == 0;
    }

    public static boolean isWritable(int flags) {
        return (flags & 4) == 0;
    }

    public static boolean isProxy(int flags) {
        return (flags & 0x10) != 0;
    }

    public static boolean isAccessor(int flags) {
        return (flags & 8) != 0;
    }

    public static boolean isData(int flags) {
        return (flags & 8) == 0;
    }

    public static boolean isConst(int flags) {
        return (flags & 0x20) == 0;
    }

    public static PropertyProxy getConstantProxy(Property proxyProperty) {
        assert (JSProperty.isProxy(proxyProperty));
        return proxyProperty.getLocation().isConstant() ? (PropertyProxy)proxyProperty.getLocation().getConstantValue() : null;
    }
}

