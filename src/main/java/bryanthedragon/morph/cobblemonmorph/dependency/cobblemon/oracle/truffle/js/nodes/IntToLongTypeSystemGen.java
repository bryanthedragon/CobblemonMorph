
package com.oracle.truffle.js.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.IntToLongTypeSystem;

@GeneratedBy(value=IntToLongTypeSystem.class)
public final class IntToLongTypeSystemGen
extends IntToLongTypeSystem {
    protected IntToLongTypeSystemGen() {
    }

    public static long expectImplicitLong(int state, Object value2) throws UnexpectedResultException {
        if ((state & 1) != 0 && value2 instanceof Long) {
            return (Long)value2;
        }
        if ((state & 2) != 0 && value2 instanceof Integer) {
            return IntToLongTypeSystemGen.intToLong((Integer)value2);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnexpectedResultException(value2);
    }

    public static boolean isImplicitLong(int state, Object value2) {
        return (state & 1) != 0 && value2 instanceof Long || (state & 2) != 0 && value2 instanceof Integer;
    }

    public static boolean isImplicitLong(Object value2) {
        return value2 instanceof Long || value2 instanceof Integer;
    }

    public static long asImplicitLong(int state, Object value2) {
        if (CompilerDirectives.inInterpreter()) {
            return IntToLongTypeSystemGen.asImplicitLong(value2);
        }
        if ((state & 1) != 0 && value2 instanceof Long) {
            return (Long)value2;
        }
        if ((state & 2) != 0 && value2 instanceof Integer) {
            return IntToLongTypeSystemGen.intToLong((Integer)value2);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new IllegalArgumentException("Illegal implicit source type.");
    }

    public static long asImplicitLong(Object value2) {
        if (value2 instanceof Long) {
            return (Long)value2;
        }
        if (value2 instanceof Integer) {
            return IntToLongTypeSystemGen.intToLong((Integer)value2);
        }
        throw new IllegalArgumentException("Illegal implicit source type.");
    }

    public static int specializeImplicitLong(Object value2) {
        if (value2 instanceof Long) {
            return 1;
        }
        if (value2 instanceof Integer) {
            return 2;
        }
        return 0;
    }
}

