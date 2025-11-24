
package com.oracle.truffle.object;

public final class Flags {
    static final long DEFAULT = 0L;
    static final long IMPLICIT_CAST_INT_TO_LONG = 0x100000000L;
    static final long IMPLICIT_CAST_INT_TO_DOUBLE = 0x200000000L;
    static final long SET_EXISTING = 0x400000000L;
    static final long UPDATE_FLAGS = 0x800000000L;
    static final long CONST = 0x1000000000L;
    static final long DECLARE = 0x2000000000L;
    static final long SEPARATE_SHAPE = 0x4000000000L;
    static final long PROPERTY_FLAGS_MASK = 0xFFFFFFFFL;

    private Flags() {
    }

    private static boolean getFlag(long flags, long flagBit) {
        return (flags & flagBit) != 0L;
    }

    public static boolean isImplicitCastIntToLong(long flags) {
        return Flags.getFlag(flags, 0x100000000L);
    }

    public static boolean isImplicitCastIntToDouble(long flags) {
        return Flags.getFlag(flags, 0x200000000L);
    }

    public static boolean isSetExisting(long flags) {
        return Flags.getFlag(flags, 0x400000000L);
    }

    public static boolean isUpdateFlags(long flags) {
        return Flags.getFlag(flags, 0x800000000L);
    }

    public static boolean isConstant(long flags) {
        return Flags.getFlag(flags, 0x1000000000L);
    }

    public static boolean isDeclaration(long flags) {
        return Flags.getFlag(flags, 0x2000000000L);
    }

    public static boolean isSeparateShape(long flags) {
        return Flags.getFlag(flags, 0x4000000000L);
    }

    public static int getPropertyFlags(long putFlags) {
        return (int)(putFlags & 0xFFFFFFFFL);
    }

    public static long propertyFlagsToPutFlags(int propertyFlags) {
        return (long)propertyFlags & 0xFFFFFFFFL;
    }
}

