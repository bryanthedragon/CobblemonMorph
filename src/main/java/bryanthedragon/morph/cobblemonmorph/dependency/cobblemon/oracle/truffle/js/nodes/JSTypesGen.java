
package com.oracle.truffle.js.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;

@GeneratedBy(value=JSTypes.class)
public final class JSTypesGen
extends JSTypes {
    protected JSTypesGen() {
    }

    public static boolean isBoolean(Object value2) {
        return value2 instanceof Boolean;
    }

    public static boolean asBoolean(Object value2) {
        assert (value2 instanceof Boolean) : "JSTypesGen.asBoolean: boolean expected";
        return (Boolean)value2;
    }

    public static boolean expectBoolean(Object value2) throws UnexpectedResultException {
        if (value2 instanceof Boolean) {
            return (Boolean)value2;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnexpectedResultException(value2);
    }

    public static boolean isInteger(Object value2) {
        return value2 instanceof Integer;
    }

    public static int asInteger(Object value2) {
        assert (value2 instanceof Integer) : "JSTypesGen.asInteger: int expected";
        return (Integer)value2;
    }

    public static int expectInteger(Object value2) throws UnexpectedResultException {
        if (value2 instanceof Integer) {
            return (Integer)value2;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnexpectedResultException(value2);
    }

    public static boolean isDouble(Object value2) {
        return value2 instanceof Double;
    }

    public static double asDouble(Object value2) {
        assert (value2 instanceof Double) : "JSTypesGen.asDouble: double expected";
        return (Double)value2;
    }

    public static double expectDouble(Object value2) throws UnexpectedResultException {
        if (value2 instanceof Double) {
            return (Double)value2;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnexpectedResultException(value2);
    }

    public static boolean isLong(Object value2) {
        return value2 instanceof Long;
    }

    public static long asLong(Object value2) {
        assert (value2 instanceof Long) : "JSTypesGen.asLong: long expected";
        return (Long)value2;
    }

    public static long expectLong(Object value2) throws UnexpectedResultException {
        if (value2 instanceof Long) {
            return (Long)value2;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnexpectedResultException(value2);
    }

    public static boolean isSafeInteger(Object value2) {
        return value2 instanceof SafeInteger;
    }

    public static SafeInteger asSafeInteger(Object value2) {
        assert (value2 instanceof SafeInteger) : "JSTypesGen.asSafeInteger: SafeInteger expected";
        return (SafeInteger)value2;
    }

    public static SafeInteger expectSafeInteger(Object value2) throws UnexpectedResultException {
        if (value2 instanceof SafeInteger) {
            return (SafeInteger)value2;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnexpectedResultException(value2);
    }

    public static boolean isBigInt(Object value2) {
        return value2 instanceof BigInt;
    }

    public static BigInt asBigInt(Object value2) {
        assert (value2 instanceof BigInt) : "JSTypesGen.asBigInt: BigInt expected";
        return (BigInt)value2;
    }

    public static BigInt expectBigInt(Object value2) throws UnexpectedResultException {
        if (value2 instanceof BigInt) {
            return (BigInt)value2;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnexpectedResultException(value2);
    }

    public static double expectImplicitDouble(int state, Object value2) throws UnexpectedResultException {
        if ((state & 1) != 0 && value2 instanceof Double) {
            return (Double)value2;
        }
        if ((state & 2) != 0 && value2 instanceof Integer) {
            return JSTypesGen.intToDouble((Integer)value2);
        }
        if ((state & 4) != 0 && value2 instanceof SafeInteger) {
            return JSTypesGen.safeIntegerToDouble((SafeInteger)value2);
        }
        if ((state & 8) != 0 && value2 instanceof Long) {
            return JSTypesGen.longToDouble((Long)value2);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnexpectedResultException(value2);
    }

    public static boolean isImplicitDouble(int state, Object value2) {
        return (state & 1) != 0 && value2 instanceof Double || (state & 2) != 0 && value2 instanceof Integer || (state & 4) != 0 && value2 instanceof SafeInteger || (state & 8) != 0 && value2 instanceof Long;
    }

    public static boolean isImplicitDouble(Object value2) {
        return value2 instanceof Double || value2 instanceof Integer || value2 instanceof SafeInteger || value2 instanceof Long;
    }

    public static double asImplicitDouble(int state, Object value2) {
        if (CompilerDirectives.inInterpreter()) {
            return JSTypesGen.asImplicitDouble(value2);
        }
        if ((state & 1) != 0 && value2 instanceof Double) {
            return (Double)value2;
        }
        if ((state & 2) != 0 && value2 instanceof Integer) {
            return JSTypesGen.intToDouble((Integer)value2);
        }
        if ((state & 4) != 0 && value2 instanceof SafeInteger) {
            return JSTypesGen.safeIntegerToDouble((SafeInteger)value2);
        }
        if ((state & 8) != 0 && value2 instanceof Long) {
            return JSTypesGen.longToDouble((Long)value2);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new IllegalArgumentException("Illegal implicit source type.");
    }

    public static double asImplicitDouble(Object value2) {
        if (value2 instanceof Double) {
            return (Double)value2;
        }
        if (value2 instanceof Integer) {
            return JSTypesGen.intToDouble((Integer)value2);
        }
        if (value2 instanceof SafeInteger) {
            return JSTypesGen.safeIntegerToDouble((SafeInteger)value2);
        }
        if (value2 instanceof Long) {
            return JSTypesGen.longToDouble((Long)value2);
        }
        throw new IllegalArgumentException("Illegal implicit source type.");
    }

    public static int specializeImplicitDouble(Object value2) {
        if (value2 instanceof Double) {
            return 1;
        }
        if (value2 instanceof Integer) {
            return 2;
        }
        if (value2 instanceof SafeInteger) {
            return 4;
        }
        if (value2 instanceof Long) {
            return 8;
        }
        return 0;
    }
}

