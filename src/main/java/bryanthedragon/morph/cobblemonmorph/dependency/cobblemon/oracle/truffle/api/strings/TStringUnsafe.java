
package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.strings.TStringOps;
import java.lang.reflect.Field;
import sun.misc.Unsafe;

final class TStringUnsafe {
    static final int JAVA_SPEC = TStringUnsafe.getJavaSpecificationVersion();
    private static final Unsafe UNSAFE = TStringUnsafe.getUnsafe();
    private static final long javaStringValueFieldOffset;
    private static final long javaStringCoderFieldOffset;
    private static final long javaStringHashFieldOffset;

    TStringUnsafe() {
    }

    @CompilerDirectives.TruffleBoundary
    private static int getJavaSpecificationVersion() {
        return Runtime.version().feature();
    }

    private static long getObjectFieldOffset(Field field) {
        return UNSAFE.objectFieldOffset(field);
    }

    @CompilerDirectives.TruffleBoundary
    private static Field getStringDeclaredField(String name) {
        try {
            return String.class.getDeclaredField(name);
        }
        catch (NoSuchFieldException e) {
            throw new RuntimeException("failed to get " + name + " field offset", e);
        }
    }

    @CompilerDirectives.TruffleBoundary
    private static Unsafe getUnsafe() {
        try {
            return Unsafe.getUnsafe();
        }
        catch (SecurityException e1) {
            try {
                Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
                theUnsafeInstance.setAccessible(true);
                return (Unsafe)theUnsafeInstance.get(Unsafe.class);
            }
            catch (Exception e2) {
                throw new RuntimeException("exception while trying to get Unsafe.theUnsafe via reflection:", e2);
            }
        }
    }

    static byte[] getJavaStringArray(String str) {
        assert (JAVA_SPEC > 8);
        Object value2 = UNSAFE.getObject((Object)str, javaStringValueFieldOffset);
        assert (value2 instanceof byte[]);
        return (byte[])value2;
    }

    static int getJavaStringStride(String s) {
        return UNSAFE.getByte((Object)s, javaStringCoderFieldOffset);
    }

    @CompilerDirectives.TruffleBoundary
    private static String allocateJavaString() {
        try {
            return (String)UNSAFE.allocateInstance(String.class);
        }
        catch (InstantiationException e) {
            throw CompilerDirectives.shouldNotReachHere("unsafe string allocation failed", e);
        }
    }

    @CompilerDirectives.TruffleBoundary
    static String createJavaString(byte[] bytes, int stride) {
        if (stride < 0 || stride > 1) {
            throw new IllegalArgumentException("stride must be 0 or 1!");
        }
        String ret = TStringUnsafe.allocateJavaString();
        UNSAFE.putInt((Object)ret, javaStringHashFieldOffset, 0);
        UNSAFE.putByte((Object)ret, javaStringCoderFieldOffset, (byte)stride);
        UNSAFE.putObjectVolatile(ret, javaStringValueFieldOffset, bytes);
        assert (TStringUnsafe.checkUnsafeStringResult(bytes, stride, ret));
        return ret;
    }

    @CompilerDirectives.TruffleBoundary
    private static boolean checkUnsafeStringResult(byte[] bytes, int stride, String ret) {
        int length = bytes.length >> stride;
        char[] chars = new char[length];
        for (int i = 0; i < length; ++i) {
            chars[i] = (char)TStringOps.readFromByteArray(bytes, stride, i);
        }
        return new String(chars).equals(ret);
    }

    static byte getByteManaged(Object array, long byteOffset) {
        return UNSAFE.getByte(array, byteOffset);
    }

    static byte getByteNative(long array, long byteOffset) {
        return UNSAFE.getByte(array + byteOffset);
    }

    static char getCharManaged(Object array, long byteOffset) {
        return UNSAFE.getChar(array, byteOffset);
    }

    static char getCharNative(long array, long byteOffset) {
        return UNSAFE.getChar(array + byteOffset);
    }

    static int getIntManaged(Object array, long byteOffset) {
        return UNSAFE.getInt(array, byteOffset);
    }

    static int getIntNative(long array, long byteOffset) {
        return UNSAFE.getInt(array + byteOffset);
    }

    static long getLongManaged(Object array, long byteOffset) {
        return UNSAFE.getLong(array, byteOffset);
    }

    static long getLongNative(long array) {
        return UNSAFE.getLong(array);
    }

    static void putByteManaged(byte[] array, long byteOffset, byte value2) {
        UNSAFE.putByte((Object)array, byteOffset, value2);
    }

    static void putByteNative(long array, long byteOffset, byte value2) {
        UNSAFE.putByte(array + byteOffset, value2);
    }

    static void putCharManaged(byte[] array, long byteOffset, char value2) {
        UNSAFE.putChar((Object)array, byteOffset, value2);
    }

    static void putCharNative(long array, long byteOffset, char value2) {
        UNSAFE.putChar(array + byteOffset, value2);
    }

    static void putIntManaged(byte[] array, long byteOffset, int value2) {
        UNSAFE.putInt((Object)array, byteOffset, value2);
    }

    static void putIntNative(long array, long byteOffset, int value2) {
        UNSAFE.putInt(array + byteOffset, value2);
    }

    static void copyFromNative(long arraySrc, int offsetSrc, byte[] arrayDst, long offsetDst, int byteLength) {
        UNSAFE.copyMemory(null, arraySrc + (long)offsetSrc, arrayDst, (long)Unsafe.ARRAY_BYTE_BASE_OFFSET + offsetDst, byteLength);
    }

    static {
        if (JAVA_SPEC <= 8) {
            throw new RuntimeException("TruffleString requires Java version > 8");
        }
        Field valueField = TStringUnsafe.getStringDeclaredField("value");
        Field coderField = TStringUnsafe.getStringDeclaredField("coder");
        Field hashField = TStringUnsafe.getStringDeclaredField("hash");
        javaStringValueFieldOffset = TStringUnsafe.getObjectFieldOffset(valueField);
        javaStringCoderFieldOffset = TStringUnsafe.getObjectFieldOffset(coderField);
        javaStringHashFieldOffset = TStringUnsafe.getObjectFieldOffset(hashField);
    }
}

