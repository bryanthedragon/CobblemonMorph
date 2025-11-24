
package com.oracle.truffle.api.staticobject;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.staticobject.StaticShape;
import java.lang.reflect.Field;
import java.nio.ByteOrder;
import sun.misc.Unsafe;

public abstract class StaticProperty {
    private static final Unsafe UNSAFE = StaticProperty.getUnsafe();
    @CompilerDirectives.CompilationFinal
    private boolean storeAsFinal;
    @CompilerDirectives.CompilationFinal
    private Class<?> type;
    @CompilerDirectives.CompilationFinal
    private StaticShape<?> shape;
    @CompilerDirectives.CompilationFinal
    private int offset;

    protected StaticProperty() {
    }

    void init(Class<?> clazz, boolean isFinal) {
        this.type = clazz;
        this.storeAsFinal = isFinal;
    }

    protected abstract String getId();

    final boolean storeAsFinal() {
        return this.storeAsFinal;
    }

    final Class<?> getPropertyType() {
        return this.type;
    }

    final void initOffset(int o) {
        if (this.offset != 0) {
            throw new IllegalStateException("Attempt to reinitialize the offset of static property '" + this.getId() + "' of type '" + this.type.getName() + "'.\nWas it added to more than one builder or multiple times to the same builder?");
        }
        this.offset = o;
    }

    final void initShape(StaticShape<?> s) {
        if (this.shape != null) {
            throw new IllegalStateException("Attempt to reinitialize the shape of static property '" + this.getId() + "' of type '" + this.type.getName() + "'.\nWas it added to more than one builder or multiple times to the same builder?");
        }
        this.shape = s;
    }

    private void throwIllegalArgumentException(Class<?> accessType) {
        CompilerAsserts.neverPartOfCompilation();
        throw new IllegalArgumentException("Static property '" + this.getId() + "' of type '" + this.type.getName() + "' cannot be accessed as '" + (accessType == null ? "null" : accessType.getName()) + "'");
    }

    private void checkObjectGetAccess() {
        if (this.type.isPrimitive()) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.throwIllegalArgumentException(Object.class);
        }
    }

    private void checkObjectSetAccess(Object value2) {
        if (value2 == null) {
            if (this.type.isPrimitive()) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.throwIllegalArgumentException(null);
            }
        } else if (!this.type.isInstance(value2)) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.throwIllegalArgumentException(value2.getClass());
        }
    }

    private void checkPrimitiveAccess(Class<?> accessType) {
        if (this.type != accessType) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.throwIllegalArgumentException(accessType);
        }
    }

    public final Object getObject(Object obj) {
        this.checkObjectGetAccess();
        return UNSAFE.getObject(this.shape.getStorage(obj, false), (long)this.offset);
    }

    public final Object getObjectVolatile(Object obj) {
        this.checkObjectGetAccess();
        return UNSAFE.getObjectVolatile(this.shape.getStorage(obj, false), this.offset);
    }

    public final void setObject(Object obj, Object value2) {
        this.checkObjectSetAccess(value2);
        UNSAFE.putObject(this.shape.getStorage(obj, false), (long)this.offset, value2);
    }

    public final void setObjectVolatile(Object obj, Object value2) {
        this.checkObjectSetAccess(value2);
        UNSAFE.putObjectVolatile(this.shape.getStorage(obj, false), this.offset, value2);
    }

    public final boolean compareAndSwapObject(Object obj, Object expect, Object update) {
        this.checkObjectSetAccess(update);
        return UNSAFE.compareAndSwapObject(this.shape.getStorage(obj, false), this.offset, expect, update);
    }

    public final Object getAndSetObject(Object obj, Object value2) {
        this.checkObjectSetAccess(value2);
        return UNSAFE.getAndSetObject(this.shape.getStorage(obj, false), this.offset, value2);
    }

    public final Object compareAndExchangeObject(Object obj, Object expect, Object update) {
        this.checkObjectSetAccess(update);
        return CASSupport.compareAndExchangeObject(this.shape.getStorage(obj, false), this.offset, expect, update);
    }

    public final boolean getBoolean(Object obj) {
        this.checkPrimitiveAccess(Boolean.TYPE);
        return UNSAFE.getBoolean(this.shape.getStorage(obj, true), (long)this.offset);
    }

    public final boolean getBooleanVolatile(Object obj) {
        this.checkPrimitiveAccess(Boolean.TYPE);
        return UNSAFE.getBooleanVolatile(this.shape.getStorage(obj, true), this.offset);
    }

    public final void setBoolean(Object obj, boolean value2) {
        this.checkPrimitiveAccess(Boolean.TYPE);
        UNSAFE.putBoolean(this.shape.getStorage(obj, true), (long)this.offset, value2);
    }

    public final void setBooleanVolatile(Object obj, boolean value2) {
        this.checkPrimitiveAccess(Boolean.TYPE);
        UNSAFE.putBooleanVolatile(this.shape.getStorage(obj, true), this.offset, value2);
    }

    public final boolean compareAndSwapBoolean(Object obj, boolean expect, boolean update) {
        this.checkPrimitiveAccess(Boolean.TYPE);
        return CASSupport.compareAndSetBoolean(this.shape.getStorage(obj, true), this.offset, expect, update);
    }

    public final boolean compareAndExchangeBoolean(Object obj, boolean expect, boolean update) {
        this.checkPrimitiveAccess(Boolean.TYPE);
        return CASSupport.compareAndExchangeBoolean(this.shape.getStorage(obj, true), this.offset, expect, update);
    }

    public final byte getByte(Object obj) {
        this.checkPrimitiveAccess(Byte.TYPE);
        return UNSAFE.getByte(this.shape.getStorage(obj, true), (long)this.offset);
    }

    public final byte getByteVolatile(Object obj) {
        this.checkPrimitiveAccess(Byte.TYPE);
        return UNSAFE.getByteVolatile(this.shape.getStorage(obj, true), this.offset);
    }

    public final void setByte(Object obj, byte value2) {
        this.checkPrimitiveAccess(Byte.TYPE);
        UNSAFE.putByte(this.shape.getStorage(obj, true), (long)this.offset, value2);
    }

    public final void setByteVolatile(Object obj, byte value2) {
        this.checkPrimitiveAccess(Byte.TYPE);
        UNSAFE.putByteVolatile(this.shape.getStorage(obj, true), this.offset, value2);
    }

    public final boolean compareAndSwapByte(Object obj, byte expect, byte update) {
        this.checkPrimitiveAccess(Byte.TYPE);
        return CASSupport.compareAndSetByte(this.shape.getStorage(obj, true), this.offset, expect, update);
    }

    public final byte compareAndExchangeByte(Object obj, byte expect, byte update) {
        this.checkPrimitiveAccess(Byte.TYPE);
        return CASSupport.compareAndExchangeByte(this.shape.getStorage(obj, true), this.offset, expect, update);
    }

    public final char getChar(Object obj) {
        this.checkPrimitiveAccess(Character.TYPE);
        return UNSAFE.getChar(this.shape.getStorage(obj, true), (long)this.offset);
    }

    public final char getCharVolatile(Object obj) {
        this.checkPrimitiveAccess(Character.TYPE);
        return UNSAFE.getCharVolatile(this.shape.getStorage(obj, true), this.offset);
    }

    public final void setChar(Object obj, char value2) {
        this.checkPrimitiveAccess(Character.TYPE);
        UNSAFE.putChar(this.shape.getStorage(obj, true), (long)this.offset, value2);
    }

    public final void setCharVolatile(Object obj, char value2) {
        this.checkPrimitiveAccess(Character.TYPE);
        UNSAFE.putCharVolatile(this.shape.getStorage(obj, true), this.offset, value2);
    }

    public final boolean compareAndSwapChar(Object obj, char expect, char update) {
        this.checkPrimitiveAccess(Character.TYPE);
        return CASSupport.compareAndSetChar(this.shape.getStorage(obj, true), this.offset, expect, update);
    }

    public final char compareAndExchangeChar(Object obj, char expect, char update) {
        this.checkPrimitiveAccess(Character.TYPE);
        return CASSupport.compareAndExchangeChar(this.shape.getStorage(obj, true), this.offset, expect, update);
    }

    public final double getDouble(Object obj) {
        this.checkPrimitiveAccess(Double.TYPE);
        return UNSAFE.getDouble(this.shape.getStorage(obj, true), (long)this.offset);
    }

    public final double getDoubleVolatile(Object obj) {
        this.checkPrimitiveAccess(Double.TYPE);
        return UNSAFE.getDoubleVolatile(this.shape.getStorage(obj, true), this.offset);
    }

    public final void setDouble(Object obj, double value2) {
        this.checkPrimitiveAccess(Double.TYPE);
        UNSAFE.putDouble(this.shape.getStorage(obj, true), (long)this.offset, value2);
    }

    public final void setDoubleVolatile(Object obj, double value2) {
        this.checkPrimitiveAccess(Double.TYPE);
        UNSAFE.putDoubleVolatile(this.shape.getStorage(obj, true), this.offset, value2);
    }

    public final boolean compareAndSwapDouble(Object obj, double expect, double update) {
        this.checkPrimitiveAccess(Double.TYPE);
        return CASSupport.compareAndSetDouble(this.shape.getStorage(obj, true), this.offset, expect, update);
    }

    public final double compareAndExchangeDouble(Object obj, double expect, double update) {
        this.checkPrimitiveAccess(Double.TYPE);
        return CASSupport.compareAndExchangeDouble(this.shape.getStorage(obj, true), this.offset, expect, update);
    }

    public final float getFloat(Object obj) {
        this.checkPrimitiveAccess(Float.TYPE);
        return UNSAFE.getFloat(this.shape.getStorage(obj, true), (long)this.offset);
    }

    public final float getFloatVolatile(Object obj) {
        this.checkPrimitiveAccess(Float.TYPE);
        return UNSAFE.getFloatVolatile(this.shape.getStorage(obj, true), this.offset);
    }

    public final void setFloat(Object obj, float value2) {
        this.checkPrimitiveAccess(Float.TYPE);
        UNSAFE.putFloat(this.shape.getStorage(obj, true), (long)this.offset, value2);
    }

    public final void setFloatVolatile(Object obj, float value2) {
        this.checkPrimitiveAccess(Float.TYPE);
        UNSAFE.putFloatVolatile(this.shape.getStorage(obj, true), this.offset, value2);
    }

    public final boolean compareAndSwapFloat(Object obj, float expect, float update) {
        this.checkPrimitiveAccess(Float.TYPE);
        return CASSupport.compareAndSetFloat(this.shape.getStorage(obj, true), this.offset, expect, update);
    }

    public final float compareAndExchangeFloat(Object obj, float expect, float update) {
        this.checkPrimitiveAccess(Float.TYPE);
        return CASSupport.compareAndExchangeFloat(this.shape.getStorage(obj, true), this.offset, expect, update);
    }

    public final int getInt(Object obj) {
        this.checkPrimitiveAccess(Integer.TYPE);
        return UNSAFE.getInt(this.shape.getStorage(obj, true), (long)this.offset);
    }

    public final int getIntVolatile(Object obj) {
        this.checkPrimitiveAccess(Integer.TYPE);
        return UNSAFE.getIntVolatile(this.shape.getStorage(obj, true), this.offset);
    }

    public final void setInt(Object obj, int value2) {
        this.checkPrimitiveAccess(Integer.TYPE);
        UNSAFE.putInt(this.shape.getStorage(obj, true), (long)this.offset, value2);
    }

    public final void setIntVolatile(Object obj, int value2) {
        this.checkPrimitiveAccess(Integer.TYPE);
        UNSAFE.putIntVolatile(this.shape.getStorage(obj, true), this.offset, value2);
    }

    public final boolean compareAndSwapInt(Object obj, int expect, int update) {
        this.checkPrimitiveAccess(Integer.TYPE);
        return UNSAFE.compareAndSwapInt(this.shape.getStorage(obj, true), this.offset, expect, update);
    }

    public final int compareAndExchangeInt(Object obj, int expect, int update) {
        this.checkPrimitiveAccess(Integer.TYPE);
        return CASSupport.compareAndExchangeInt(this.shape.getStorage(obj, true), this.offset, expect, update);
    }

    public final int getAndAddInt(Object obj, int delta) {
        this.checkPrimitiveAccess(Integer.TYPE);
        return UNSAFE.getAndAddInt(this.shape.getStorage(obj, true), this.offset, delta);
    }

    public final int getAndSetInt(Object obj, int value2) {
        this.checkPrimitiveAccess(Integer.TYPE);
        return UNSAFE.getAndSetInt(this.shape.getStorage(obj, true), this.offset, value2);
    }

    public final long getLong(Object obj) {
        this.checkPrimitiveAccess(Long.TYPE);
        return UNSAFE.getLong(this.shape.getStorage(obj, true), (long)this.offset);
    }

    public final long getLongVolatile(Object obj) {
        this.checkPrimitiveAccess(Long.TYPE);
        return UNSAFE.getLongVolatile(this.shape.getStorage(obj, true), this.offset);
    }

    public final void setLong(Object obj, long value2) {
        this.checkPrimitiveAccess(Long.TYPE);
        UNSAFE.putLong(this.shape.getStorage(obj, true), (long)this.offset, value2);
    }

    public final void setLongVolatile(Object obj, long value2) {
        this.checkPrimitiveAccess(Long.TYPE);
        UNSAFE.putLongVolatile(this.shape.getStorage(obj, true), this.offset, value2);
    }

    public final boolean compareAndSwapLong(Object obj, long expect, long update) {
        this.checkPrimitiveAccess(Long.TYPE);
        return UNSAFE.compareAndSwapLong(this.shape.getStorage(obj, true), this.offset, expect, update);
    }

    public final long compareAndExchangeLong(Object obj, long expect, long update) {
        this.checkPrimitiveAccess(Long.TYPE);
        return CASSupport.compareAndExchangeLong(this.shape.getStorage(obj, true), this.offset, expect, update);
    }

    public final long getAndAddLong(Object obj, long delta) {
        this.checkPrimitiveAccess(Long.TYPE);
        return UNSAFE.getAndAddLong(this.shape.getStorage(obj, true), this.offset, delta);
    }

    public final long getAndSetLong(Object obj, long value2) {
        this.checkPrimitiveAccess(Long.TYPE);
        return UNSAFE.getAndSetLong(this.shape.getStorage(obj, true), this.offset, value2);
    }

    public final short getShort(Object obj) {
        this.checkPrimitiveAccess(Short.TYPE);
        return UNSAFE.getShort(this.shape.getStorage(obj, true), (long)this.offset);
    }

    public final short getShortVolatile(Object obj) {
        this.checkPrimitiveAccess(Short.TYPE);
        return UNSAFE.getShortVolatile(this.shape.getStorage(obj, true), this.offset);
    }

    public final void setShort(Object obj, short value2) {
        this.checkPrimitiveAccess(Short.TYPE);
        UNSAFE.putShort(this.shape.getStorage(obj, true), (long)this.offset, value2);
    }

    public final void setShortVolatile(Object obj, short value2) {
        this.checkPrimitiveAccess(Short.TYPE);
        UNSAFE.putShortVolatile(this.shape.getStorage(obj, true), this.offset, value2);
    }

    public final boolean compareAndSwapShort(Object obj, short expect, short update) {
        this.checkPrimitiveAccess(Short.TYPE);
        return CASSupport.compareAndSetShort(this.shape.getStorage(obj, true), this.offset, expect, update);
    }

    public final short compareAndExchangeShort(Object obj, short expect, short update) {
        this.checkPrimitiveAccess(Short.TYPE);
        return CASSupport.compareAndExchangeShort(this.shape.getStorage(obj, true), this.offset, expect, update);
    }

    private static Unsafe getUnsafe() {
        try {
            return Unsafe.getUnsafe();
        }
        catch (SecurityException securityException) {
            try {
                Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
                theUnsafeInstance.setAccessible(true);
                return (Unsafe)theUnsafeInstance.get(Unsafe.class);
            }
            catch (Exception e) {
                throw new RuntimeException("exception while trying to get Unsafe.theUnsafe via reflection:", e);
            }
        }
    }

    private static final class CASSupport {
        private CASSupport() {
        }

        private static boolean isBigEndian() {
            return ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
        }

        private static boolean compareAndSetByte(Object o, long offset, byte expected, byte x) {
            return CASSupport.compareAndExchangeByte(o, offset, expected, x) == expected;
        }

        private static boolean compareAndSetBoolean(Object o, long offset, boolean expected, boolean x) {
            byte byteExpected = expected ? (byte)1 : 0;
            byte byteX = x ? (byte)1 : 0;
            return CASSupport.compareAndSetByte(o, offset, byteExpected, byteX);
        }

        private static boolean compareAndSetShort(Object o, long offset, short expected, short x) {
            return CASSupport.compareAndExchangeShort(o, offset, expected, x) == expected;
        }

        private static boolean compareAndSetChar(Object o, long offset, char expected, char x) {
            return CASSupport.compareAndSetShort(o, offset, (short)expected, (short)x);
        }

        private static boolean compareAndSetFloat(Object o, long offset, float expected, float x) {
            return UNSAFE.compareAndSwapInt(o, offset, Float.floatToRawIntBits(expected), Float.floatToRawIntBits(x));
        }

        private static boolean compareAndSetDouble(Object o, long offset, double expected, double x) {
            return UNSAFE.compareAndSwapLong(o, offset, Double.doubleToRawLongBits(expected), Double.doubleToRawLongBits(x));
        }

        private static byte compareAndExchangeByte(Object o, long offset, byte expected, byte x) {
            int fullWord;
            long wordOffset = offset & 0xFFFFFFFFFFFFFFFCL;
            int shift2 = (int)(offset & 3L) << 3;
            if (CASSupport.isBigEndian()) {
                shift2 = 24 - shift2;
            }
            int mask = 255 << shift2;
            int maskedExpected = (expected & 0xFF) << shift2;
            int maskedX = (x & 0xFF) << shift2;
            do {
                if (((fullWord = UNSAFE.getIntVolatile(o, wordOffset)) & mask) == maskedExpected) continue;
                return (byte)((fullWord & mask) >> shift2);
            } while (!UNSAFE.compareAndSwapInt(o, wordOffset, fullWord, fullWord & ~mask | maskedX));
            return expected;
        }

        private static boolean compareAndExchangeBoolean(Object o, long offset, boolean expected, boolean x) {
            byte byteExpected = expected ? (byte)1 : 0;
            byte byteX = x ? (byte)1 : 0;
            return CASSupport.compareAndExchangeByte(o, offset, byteExpected, byteX) != 0;
        }

        private static short compareAndExchangeShort(Object o, long offset, short expected, short x) {
            int fullWord;
            if ((offset & 3L) == 3L) {
                throw new IllegalArgumentException("Update spans the word, not supported");
            }
            long wordOffset = offset & 0xFFFFFFFFFFFFFFFCL;
            int shift2 = (int)(offset & 3L) << 3;
            if (CASSupport.isBigEndian()) {
                shift2 = 16 - shift2;
            }
            int mask = 65535 << shift2;
            int maskedExpected = (expected & 0xFFFF) << shift2;
            int maskedX = (x & 0xFFFF) << shift2;
            do {
                if (((fullWord = UNSAFE.getIntVolatile(o, wordOffset)) & mask) == maskedExpected) continue;
                return (short)((fullWord & mask) >> shift2);
            } while (!UNSAFE.compareAndSwapInt(o, wordOffset, fullWord, fullWord & ~mask | maskedX));
            return expected;
        }

        private static char compareAndExchangeChar(Object o, long offset, char expected, char x) {
            return (char)CASSupport.compareAndExchangeShort(o, offset, (short)expected, (short)x);
        }

        private static int compareAndExchangeInt(Object o, long offset, int expected, int x) {
            do {
                int result;
                if ((result = UNSAFE.getIntVolatile(o, offset)) == expected) continue;
                return result;
            } while (!UNSAFE.compareAndSwapInt(o, offset, expected, x));
            return expected;
        }

        private static Object compareAndExchangeObject(Object o, long offset, Object expected, Object x) {
            do {
                Object result;
                if ((result = UNSAFE.getObjectVolatile(o, offset)) == expected) continue;
                return result;
            } while (!UNSAFE.compareAndSwapObject(o, offset, expected, x));
            return expected;
        }

        private static float compareAndExchangeFloat(Object o, long offset, float expected, float x) {
            return Float.intBitsToFloat(CASSupport.compareAndExchangeInt(o, offset, Float.floatToRawIntBits(expected), Float.floatToRawIntBits(x)));
        }

        private static long compareAndExchangeLong(Object o, long offset, long expected, long x) {
            do {
                long result;
                if ((result = UNSAFE.getLongVolatile(o, offset)) == expected) continue;
                return result;
            } while (!UNSAFE.compareAndSwapLong(o, offset, expected, x));
            return expected;
        }

        private static double compareAndExchangeDouble(Object o, long offset, double expected, double x) {
            return Double.longBitsToDouble(CASSupport.compareAndExchangeLong(o, offset, Double.doubleToRawLongBits(expected), Double.doubleToRawLongBits(x)));
        }
    }
}

