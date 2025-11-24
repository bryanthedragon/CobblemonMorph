
package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;

final class HostUtil {
    private HostUtil() {
    }

    static Object convertLossLess(Object value2, Class<?> requestedType, InteropLibrary interop) {
        try {
            if (interop.isNumber(value2)) {
                if (requestedType == Byte.TYPE || requestedType == Byte.class) {
                    return interop.asByte(value2);
                }
                if (requestedType == Short.TYPE || requestedType == Short.class) {
                    return interop.asShort(value2);
                }
                if (requestedType == Integer.TYPE || requestedType == Integer.class) {
                    return interop.asInt(value2);
                }
                if (requestedType == Long.TYPE || requestedType == Long.class) {
                    return interop.asLong(value2);
                }
                if (requestedType == Float.TYPE || requestedType == Float.class) {
                    return Float.valueOf(interop.asFloat(value2));
                }
                if (requestedType == Double.TYPE || requestedType == Double.class) {
                    return interop.asDouble(value2);
                }
                if (requestedType == Number.class) {
                    return HostUtil.convertToNumber(value2, interop);
                }
            } else if (interop.isBoolean(value2)) {
                if (requestedType == Boolean.TYPE || requestedType == Boolean.class) {
                    return interop.asBoolean(value2);
                }
            } else if (interop.isString(value2)) {
                if (requestedType == Character.TYPE || requestedType == Character.class) {
                    String str = interop.asString(value2);
                    if (str.length() == 1) {
                        return Character.valueOf(str.charAt(0));
                    }
                } else if (requestedType == String.class || requestedType == CharSequence.class) {
                    return interop.asString(value2);
                }
            }
        }
        catch (UnsupportedMessageException unsupportedMessageException) {
            // empty catch block
        }
        return null;
    }

    static Object convertToNumber(Object value2, InteropLibrary interop) {
        try {
            if (value2 instanceof Number) {
                return value2;
            }
            if (interop.fitsInByte(value2)) {
                return interop.asByte(value2);
            }
            if (interop.fitsInShort(value2)) {
                return interop.asShort(value2);
            }
            if (interop.fitsInInt(value2)) {
                return interop.asInt(value2);
            }
            if (interop.fitsInLong(value2)) {
                return interop.asLong(value2);
            }
            if (interop.fitsInFloat(value2)) {
                return Float.valueOf(interop.asFloat(value2));
            }
            if (interop.fitsInDouble(value2)) {
                return interop.asDouble(value2);
            }
        }
        catch (UnsupportedMessageException unsupportedMessageException) {
            // empty catch block
        }
        return null;
    }

    static Object convertLossy(Object value2, Class<?> targetType, InteropLibrary interop) {
        if ((targetType == Character.TYPE || targetType == Character.class) && interop.fitsInInt(value2)) {
            try {
                int v = interop.asInt(value2);
                if (v >= 0 && v < 65536) {
                    return Character.valueOf((char)v);
                }
            }
            catch (UnsupportedMessageException e) {
                CompilerDirectives.shouldNotReachHere(e);
            }
        }
        return null;
    }
}

