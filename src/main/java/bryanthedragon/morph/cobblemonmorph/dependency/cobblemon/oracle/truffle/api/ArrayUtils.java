
package com.oracle.truffle.api;

import com.oracle.truffle.api.CompilerDirectives;
import java.lang.reflect.Field;
import java.nio.ByteOrder;
import sun.misc.Unsafe;

public final class ArrayUtils {
    private static final Unsafe UNSAFE = ArrayUtils.getUnsafe();
    private static final long javaStringValueFieldOffset;
    private static final long javaStringCoderFieldOffset;

    private ArrayUtils() {
    }

    static long getObjectFieldOffset(Field field) {
        return UNSAFE.objectFieldOffset(field);
    }

    private static Field getStringDeclaredField(String name) {
        try {
            return String.class.getDeclaredField(name);
        }
        catch (NoSuchFieldException e) {
            throw new RuntimeException("failed to get " + name + " field offset", e);
        }
    }

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

    private static byte[] getJavaStringArray(String str) {
        Object value2 = UNSAFE.getObject((Object)str, javaStringValueFieldOffset);
        assert (value2 instanceof byte[]);
        return (byte[])value2;
    }

    private static boolean isCompactString(String s) {
        return UNSAFE.getByte((Object)s, javaStringCoderFieldOffset) == 0;
    }

    public static int indexOf(String string, int fromIndex, int maxIndex, char ... values) {
        ArrayUtils.checkArgs(string.length(), fromIndex, maxIndex, values.length);
        if (fromIndex >= string.length()) {
            return -1;
        }
        if (values.length <= 4) {
            if (ArrayUtils.isCompactString(string)) {
                int valuesInt = 0;
                int nValues = 0;
                for (int i = 0; i < values.length; ++i) {
                    if (values[i] > '\u00ff') continue;
                    valuesInt = valuesInt << 8 | values[i];
                    ++nValues;
                }
                return nValues == 0 ? -1 : ArrayUtils.indexOfBS1(ArrayUtils.getJavaStringArray(string), fromIndex, maxIndex, valuesInt, nValues);
            }
            return ArrayUtils.indexOfBS2(ArrayUtils.getJavaStringArray(string), fromIndex, maxIndex, values, values.length);
        }
        return ArrayUtils.runIndexOfS2(string, (long)fromIndex, maxIndex, values);
    }

    public static int indexOf(char[] array, int fromIndex, int maxIndex, char ... values) {
        ArrayUtils.checkArgs(array.length, fromIndex, maxIndex, values.length);
        if (fromIndex >= array.length) {
            return -1;
        }
        if (values.length <= 4) {
            return ArrayUtils.indexOfCS2(array, fromIndex, maxIndex, values, values.length);
        }
        return ArrayUtils.runIndexOfS2(array, (long)fromIndex, maxIndex, values);
    }

    public static int indexOf(byte[] array, int fromIndex, int maxIndex, byte ... values) {
        ArrayUtils.checkArgs(array.length, fromIndex, maxIndex, values.length);
        if (fromIndex >= array.length) {
            return -1;
        }
        if (values.length <= 4) {
            return ArrayUtils.indexOfBS1(array, fromIndex, maxIndex, values, values.length);
        }
        return ArrayUtils.runIndexOfS1(array, (long)fromIndex, maxIndex, values);
    }

    public static int indexOfWithOrMask(byte[] haystack, int fromIndex, int length, byte[] needle, byte[] mask) {
        int index;
        ArrayUtils.checkArgsIndexOf(haystack.length, fromIndex, length);
        if (mask != null) {
            ArrayUtils.checkMaskLengthIndexOf(needle.length, mask.length);
        }
        if (needle.length == 0) {
            return fromIndex;
        }
        if (length - needle.length < 0) {
            return -1;
        }
        if (needle.length == 1) {
            if (mask == null) {
                return ArrayUtils.stubIndexOfB1S1(haystack, fromIndex, fromIndex + length, Byte.toUnsignedInt(needle[0]));
            }
            return ArrayUtils.stubIndexOfWithOrMaskS1(haystack, fromIndex, fromIndex + length, Byte.toUnsignedInt(needle[0]), Byte.toUnsignedInt(mask[0]));
        }
        int max2 = fromIndex + length - (needle.length - 2);
        if (mask == null) {
            for (index = fromIndex; index < max2 - 1; ++index) {
                if ((index = ArrayUtils.stubIndexOf2ConsecutiveS1(haystack, index, max2, Byte.toUnsignedInt(needle[0]), Byte.toUnsignedInt(needle[1]))) < 0) {
                    return -1;
                }
                if (needle.length != 2 && !ArrayUtils.stubRegionEqualsS1(haystack, index, needle, 0L, needle.length)) continue;
                return index;
            }
        } else {
            while (index < max2 - 1) {
                if ((index = ArrayUtils.stubIndexOf2ConsecutiveWithOrMaskS1(haystack, index, max2, Byte.toUnsignedInt(needle[0]), Byte.toUnsignedInt(needle[1]), Byte.toUnsignedInt(mask[0]), Byte.toUnsignedInt(mask[1]))) < 0) {
                    return -1;
                }
                if (needle.length == 2 || ArrayUtils.stubRegionEqualsWithOrMaskS1(haystack, index, needle, 0L, mask, mask.length)) {
                    return index;
                }
                ++index;
            }
        }
        return -1;
    }

    public static int indexOfWithOrMask(char[] haystack, int fromIndex, int length, char[] needle, char[] mask) {
        int index;
        ArrayUtils.checkArgsIndexOf(haystack.length, fromIndex, length);
        if (mask != null) {
            ArrayUtils.checkMaskLengthIndexOf(needle.length, mask.length);
        }
        if (needle.length == 0) {
            return fromIndex;
        }
        if (length - needle.length < 0) {
            return -1;
        }
        if (needle.length == 1) {
            if (mask == null) {
                return ArrayUtils.stubIndexOfC1S2(haystack, fromIndex, fromIndex + length, needle[0]);
            }
            return ArrayUtils.stubIndexOfWithOrMaskS2(haystack, (long)fromIndex, fromIndex + length, (int)needle[0], (int)mask[0]);
        }
        int max2 = fromIndex + length - (needle.length - 2);
        if (mask == null) {
            for (index = fromIndex; index < max2 - 1; ++index) {
                if ((index = ArrayUtils.stubIndexOf2ConsecutiveS2(haystack, (long)index, max2, (int)needle[0], (int)needle[1])) < 0) {
                    return -1;
                }
                if (needle.length != 2 && !ArrayUtils.stubRegionEqualsS2(haystack, index, needle, 0L, needle.length)) continue;
                return index;
            }
        } else {
            while (index < max2 - 1) {
                if ((index = ArrayUtils.stubIndexOf2ConsecutiveWithOrMaskS2(haystack, (long)index, max2, (int)needle[0], (int)needle[1], (int)mask[0], (int)mask[1])) < 0) {
                    return -1;
                }
                if (needle.length == 2 || ArrayUtils.stubRegionEqualsWithOrMaskS2(haystack, index, needle, 0L, mask, mask.length)) {
                    return index;
                }
                ++index;
            }
        }
        return -1;
    }

    public static int indexOfWithOrMask(String haystack, int fromIndex, int length, String needle, String mask) {
        ArrayUtils.checkArgsIndexOf(haystack.length(), fromIndex, length);
        if (mask != null) {
            ArrayUtils.checkMaskLengthIndexOf(needle.length(), mask.length());
        }
        if (needle.isEmpty()) {
            return fromIndex;
        }
        if (length - needle.length() < 0) {
            return -1;
        }
        if (needle.length() == 1) {
            return ArrayUtils.indexOfWithOrMaskJLString(haystack, fromIndex, length, needle, mask);
        }
        int max2 = fromIndex + length - (needle.length() - 2);
        for (int index = fromIndex; index < max2 - 1; ++index) {
            if ((index = ArrayUtils.indexOf2ConsecutiveWithOrMaskJLString(haystack, index, needle, mask, max2)) < 0) {
                return -1;
            }
            if (needle.length() != 2 && !ArrayUtils.regionEqualsWithOrMask(haystack, index, needle, 0, needle.length(), mask)) continue;
            return index;
        }
        return -1;
    }

    private static int indexOfWithOrMaskJLString(String haystack, int fromIndex, int length, String needle, String mask) {
        int maxIndex = fromIndex + length;
        char v0 = needle.charAt(0);
        byte[] array = ArrayUtils.getJavaStringArray(haystack);
        if (mask == null) {
            if (ArrayUtils.isCompactString(haystack)) {
                return v0 <= '\u00ff' ? ArrayUtils.stubIndexOfB1S1(array, fromIndex, maxIndex, v0) : -1;
            }
            return ArrayUtils.stubIndexOfB1S2(array, fromIndex, maxIndex, v0);
        }
        char mask0 = mask.charAt(0);
        if (ArrayUtils.isCompactString(haystack)) {
            return (v0 ^ mask0) <= 255 ? ArrayUtils.stubIndexOfWithOrMaskS1(array, fromIndex, maxIndex, v0, mask0) : -1;
        }
        return ArrayUtils.stubIndexOfWithOrMaskS2(array, (long)fromIndex, maxIndex, (int)v0, (int)mask0);
    }

    private static int indexOf2ConsecutiveWithOrMaskJLString(String haystack, int fromIndex, String needle, String mask, int max2) {
        char v0 = needle.charAt(0);
        char v1 = needle.charAt(1);
        byte[] array = ArrayUtils.getJavaStringArray(haystack);
        if (mask == null) {
            if (ArrayUtils.isCompactString(haystack)) {
                return v0 <= '\u00ff' && v1 <= '\u00ff' ? ArrayUtils.stubIndexOf2ConsecutiveS1(array, fromIndex, max2, v0, v1) : -1;
            }
            return ArrayUtils.stubIndexOf2ConsecutiveS2(array, (long)fromIndex, max2, (int)v0, (int)v1);
        }
        char mask0 = mask.charAt(0);
        char mask1 = mask.charAt(1);
        if (ArrayUtils.isCompactString(haystack)) {
            return (v0 ^ mask0) <= 255 && (v1 ^ mask1) <= 255 ? ArrayUtils.stubIndexOf2ConsecutiveWithOrMaskS1(array, fromIndex, max2, v0, v1, mask0, mask1) : -1;
        }
        return ArrayUtils.stubIndexOf2ConsecutiveWithOrMaskS2(array, (long)fromIndex, max2, (int)v0, (int)v1, (int)mask0, (int)mask1);
    }

    public static boolean regionEqualsWithOrMask(byte[] a, int offsetA, byte[] b, int offsetB, int length, byte[] mask) {
        ArrayUtils.requireNonNull(a);
        ArrayUtils.requireNonNull(b);
        ArrayUtils.checkArgsRegionEquals(offsetA, offsetB, length);
        if (ArrayUtils.regionEqualsOutOfBounds(a.length, offsetA, b.length, offsetB, length)) {
            return false;
        }
        if (mask == null) {
            return ArrayUtils.stubRegionEqualsS1(a, offsetA, b, offsetB, length);
        }
        ArrayUtils.checkMaskLengthRegionEquals(length, mask.length);
        return ArrayUtils.stubRegionEqualsWithOrMaskS1(a, offsetA, b, offsetB, mask, mask.length);
    }

    public static boolean regionEqualsWithOrMask(char[] a, int offsetA, char[] b, int offsetB, int length, char[] mask) {
        ArrayUtils.requireNonNull(a);
        ArrayUtils.requireNonNull(b);
        ArrayUtils.checkArgsRegionEquals(offsetA, offsetB, length);
        if (ArrayUtils.regionEqualsOutOfBounds(a.length, offsetA, b.length, offsetB, length)) {
            return false;
        }
        if (mask == null) {
            return ArrayUtils.stubRegionEqualsS2(a, offsetA, b, offsetB, length);
        }
        ArrayUtils.checkMaskLengthRegionEquals(length, mask.length);
        return ArrayUtils.stubRegionEqualsWithOrMaskS2(a, offsetA, b, offsetB, mask, mask.length);
    }

    public static boolean regionEqualsWithOrMask(String a, int offsetA, String b, int offsetB, int length, String mask) {
        ArrayUtils.requireNonNull(a);
        ArrayUtils.requireNonNull(b);
        ArrayUtils.checkArgsRegionEquals(offsetA, offsetB, length);
        if (ArrayUtils.regionEqualsOutOfBounds(a.length(), offsetA, b.length(), offsetB, length)) {
            return false;
        }
        if (mask == null) {
            int byteLength;
            int byteOffsetB;
            int byteOffsetA;
            byte[] arrayA = ArrayUtils.getJavaStringArray(a);
            byte[] arrayB = ArrayUtils.getJavaStringArray(b);
            boolean compactA = ArrayUtils.isCompactString(a);
            if (compactA != ArrayUtils.isCompactString(b)) {
                return ArrayUtils.stubRegionEqualsS2S1(compactA ? arrayB : arrayA, compactA ? (long)offsetB : (long)offsetA, compactA ? arrayA : arrayB, compactA ? (long)offsetA : (long)offsetB, length);
            }
            if (compactA) {
                byteOffsetA = offsetA;
                byteOffsetB = offsetB;
                byteLength = length;
            } else {
                byteOffsetA = offsetA << 1;
                byteOffsetB = offsetB << 1;
                byteLength = length << 1;
            }
            return ArrayUtils.stubRegionEqualsS1(arrayA, byteOffsetA, arrayB, byteOffsetB, byteLength);
        }
        ArrayUtils.checkMaskLengthRegionEquals(length, mask.length());
        byte[] arrayA = ArrayUtils.getJavaStringArray(a);
        byte[] arrayB = ArrayUtils.getJavaStringArray(b);
        byte[] arrayM = ArrayUtils.getJavaStringArray(mask);
        boolean compact1 = ArrayUtils.isCompactString(a);
        boolean compact2 = ArrayUtils.isCompactString(b);
        boolean compactMask = ArrayUtils.isCompactString(mask);
        if (compact2) {
            if (compactMask) {
                if (compact1) {
                    return ArrayUtils.stubRegionEqualsWithOrMaskCompactStrings(arrayA, offsetA, arrayB, offsetB, arrayM, mask.length(), true, true, true);
                }
                return ArrayUtils.stubRegionEqualsWithOrMaskCompactStrings(arrayA, offsetA, arrayB, offsetB, arrayM, mask.length(), false, true, true);
            }
            return false;
        }
        if (compactMask) {
            if (compact1) {
                return ArrayUtils.stubRegionEqualsWithOrMaskCompactStrings(arrayA, offsetA, arrayB, offsetB, arrayM, mask.length(), true, false, true);
            }
            return ArrayUtils.stubRegionEqualsWithOrMaskCompactStrings(arrayA, offsetA, arrayB, offsetB, arrayM, mask.length(), false, false, true);
        }
        if (compact1) {
            return ArrayUtils.stubRegionEqualsWithOrMaskCompactStrings(arrayA, offsetA, arrayB, offsetB, arrayM, mask.length(), true, false, false);
        }
        return ArrayUtils.stubRegionEqualsWithOrMaskCompactStrings(arrayA, offsetA, arrayB, offsetB, arrayM, mask.length(), false, false, false);
    }

    private static boolean regionEqualsOutOfBounds(int lengthA, int offsetA, int lengthB, int offsetB, int length) {
        return lengthA - offsetA < length || lengthB - offsetB < length;
    }

    private static void checkArgsRegionEquals(int offsetA, int offsetB, int length) {
        if (offsetA < 0 || offsetB < 0 || length < 0) {
            ArrayUtils.illegalArgumentException("length, offsetA and offsetB must be positive");
        }
    }

    private static void checkMaskLengthRegionEquals(int length, int maskLength) {
        if (length > maskLength) {
            ArrayUtils.illegalArgumentException("mask length must be greater or equal to length");
        }
    }

    private static void checkArgs(int length, int fromIndex, int maxIndex, int nValues) {
        if (fromIndex < 0) {
            ArrayUtils.illegalArgumentException("fromIndex must be positive");
        }
        if (maxIndex > length || maxIndex < fromIndex) {
            ArrayUtils.illegalArgumentException("maxIndex out of range");
        }
        if (nValues == 0) {
            ArrayUtils.illegalArgumentException("no search values provided");
        }
    }

    private static void checkArgsIndexOf(int hayStackLength, int fromIndex, int length) {
        if (fromIndex < 0 || length < 0) {
            ArrayUtils.illegalArgumentException("fromIndex and length must be positive");
        }
        if (fromIndex + length > hayStackLength) {
            ArrayUtils.illegalArgumentException("length out of range");
        }
    }

    private static void checkMaskLengthIndexOf(int lengthB, int maskLength) {
        if (lengthB != maskLength) {
            ArrayUtils.illegalArgumentException("mask and needle length must be equal");
        }
    }

    private static void requireNonNull(Object obj) {
        if (obj == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new NullPointerException();
        }
    }

    private static void illegalArgumentException(String msg) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new IllegalArgumentException(msg);
    }

    private static int indexOfBS1(byte[] array, int fromIndex, int maxIndex, int values, int nValues) {
        if (nValues == 1) {
            return ArrayUtils.stubIndexOfB1S1(array, fromIndex, maxIndex, values & 0xFF);
        }
        if (nValues == 2) {
            return ArrayUtils.stubIndexOfB2S1(array, fromIndex, maxIndex, values & 0xFF, values >>> 8 & 0xFF);
        }
        if (nValues == 3) {
            return ArrayUtils.stubIndexOfB3S1(array, fromIndex, maxIndex, values & 0xFF, values >>> 8 & 0xFF, values >>> 16 & 0xFF);
        }
        return ArrayUtils.stubIndexOfB4S1(array, fromIndex, maxIndex, values & 0xFF, values >>> 8 & 0xFF, values >>> 16 & 0xFF, values >>> 24 & 0xFF);
    }

    private static int indexOfBS1(byte[] array, int fromIndex, int maxIndex, byte[] bytes, int nValues) {
        if (nValues == 1) {
            return ArrayUtils.stubIndexOfB1S1(array, fromIndex, maxIndex, Byte.toUnsignedInt(bytes[0]));
        }
        if (nValues == 2) {
            return ArrayUtils.stubIndexOfB2S1(array, fromIndex, maxIndex, Byte.toUnsignedInt(bytes[0]), Byte.toUnsignedInt(bytes[1]));
        }
        if (nValues == 3) {
            return ArrayUtils.stubIndexOfB3S1(array, fromIndex, maxIndex, Byte.toUnsignedInt(bytes[0]), Byte.toUnsignedInt(bytes[1]), Byte.toUnsignedInt(bytes[2]));
        }
        return ArrayUtils.stubIndexOfB4S1(array, fromIndex, maxIndex, Byte.toUnsignedInt(bytes[0]), Byte.toUnsignedInt(bytes[1]), Byte.toUnsignedInt(bytes[2]), Byte.toUnsignedInt(bytes[3]));
    }

    private static int indexOfBS2(byte[] array, int fromIndex, int maxIndex, char[] chars, int nValues) {
        if (nValues == 1) {
            return ArrayUtils.stubIndexOfB1S2(array, fromIndex, maxIndex, chars[0]);
        }
        if (nValues == 2) {
            return ArrayUtils.stubIndexOfB2S2(array, fromIndex, maxIndex, chars[0], chars[1]);
        }
        if (nValues == 3) {
            return ArrayUtils.stubIndexOfB3S2(array, fromIndex, maxIndex, chars[0], chars[1], chars[2]);
        }
        return ArrayUtils.stubIndexOfB4S2(array, fromIndex, maxIndex, chars[0], chars[1], chars[2], chars[3]);
    }

    private static int indexOfCS2(char[] array, int fromIndex, int maxIndex, char[] chars, int nValues) {
        if (nValues == 1) {
            return ArrayUtils.stubIndexOfC1S2(array, fromIndex, maxIndex, chars[0]);
        }
        if (nValues == 2) {
            return ArrayUtils.stubIndexOfC2S2(array, fromIndex, maxIndex, chars[0], chars[1]);
        }
        if (nValues == 3) {
            return ArrayUtils.stubIndexOfC3S2(array, fromIndex, maxIndex, chars[0], chars[1], chars[2]);
        }
        return ArrayUtils.stubIndexOfC4S2(array, fromIndex, maxIndex, chars[0], chars[1], chars[2], chars[3]);
    }

    private static int stubIndexOfB1S1(byte[] array, long fromIndex, int maxIndex, int v1) {
        return ArrayUtils.runIndexOfS1(array, fromIndex, maxIndex, v1);
    }

    private static int stubIndexOfB2S1(byte[] array, long fromIndex, int maxIndex, int v1, int v2) {
        return ArrayUtils.runIndexOfS1(array, fromIndex, maxIndex, v1, v2);
    }

    private static int stubIndexOfB3S1(byte[] array, long fromIndex, int maxIndex, int v1, int v2, int v3) {
        return ArrayUtils.runIndexOfS1(array, fromIndex, maxIndex, v1, v2, v3);
    }

    private static int stubIndexOfB4S1(byte[] array, long fromIndex, int maxIndex, int v1, int v2, int v3, int v4) {
        return ArrayUtils.runIndexOfS1(array, fromIndex, maxIndex, v1, v2, v3, v4);
    }

    private static int stubIndexOfB1S2(byte[] array, long fromIndex, int maxIndex, int v1) {
        return ArrayUtils.runIndexOfS2(array, fromIndex, maxIndex, v1);
    }

    private static int stubIndexOfB2S2(byte[] array, long fromIndex, int maxIndex, int v1, int v2) {
        return ArrayUtils.runIndexOfS2(array, fromIndex, maxIndex, v1, v2);
    }

    private static int stubIndexOfB3S2(byte[] array, long fromIndex, int maxIndex, int v1, int v2, int v3) {
        return ArrayUtils.runIndexOfS2(array, fromIndex, maxIndex, v1, v2, v3);
    }

    private static int stubIndexOfB4S2(byte[] array, long fromIndex, int maxIndex, int v1, int v2, int v3, int v4) {
        return ArrayUtils.runIndexOfS2(array, fromIndex, maxIndex, v1, v2, v3, v4);
    }

    private static int stubIndexOfC1S2(char[] array, long fromIndex, int maxIndex, int v1) {
        return ArrayUtils.runIndexOfS2(array, fromIndex, maxIndex, v1);
    }

    private static int stubIndexOfC2S2(char[] array, long fromIndex, int maxIndex, int v1, int v2) {
        return ArrayUtils.runIndexOfS2(array, fromIndex, maxIndex, v1, v2);
    }

    private static int stubIndexOfC3S2(char[] array, long fromIndex, int maxIndex, int v1, int v2, int v3) {
        return ArrayUtils.runIndexOfS2(array, fromIndex, maxIndex, v1, v2, v3);
    }

    private static int stubIndexOfC4S2(char[] array, long fromIndex, int maxIndex, int v1, int v2, int v3, int v4) {
        return ArrayUtils.runIndexOfS2(array, fromIndex, maxIndex, v1, v2, v3, v4);
    }

    private static int stubIndexOfWithOrMaskS1(byte[] haystack, long fromIndex, int maxIndex, int needle, int mask) {
        for (int i = (int)fromIndex; i < maxIndex; ++i) {
            if ((Byte.toUnsignedInt(haystack[i]) | mask) != needle) continue;
            return i;
        }
        return -1;
    }

    private static int stubIndexOfWithOrMaskS2(byte[] haystack, long fromIndex, int maxIndex, int needle, int mask) {
        for (int i = (int)fromIndex; i < maxIndex; ++i) {
            if ((ArrayUtils.readChar(haystack, i) | mask) != needle) continue;
            return i;
        }
        return -1;
    }

    private static int stubIndexOfWithOrMaskS2(char[] haystack, long fromIndex, int maxIndex, int needle, int mask) {
        for (int i = (int)fromIndex; i < maxIndex; ++i) {
            if ((haystack[i] | mask) != needle) continue;
            return i;
        }
        return -1;
    }

    private static int stubIndexOf2ConsecutiveS1(byte[] haystack, long fromIndex, int maxIndex, int c1, int c2) {
        for (int i = (int)(fromIndex + 1L); i < maxIndex; ++i) {
            if (Byte.toUnsignedInt(haystack[i - 1]) != c1 || Byte.toUnsignedInt(haystack[i]) != c2) continue;
            return i - 1;
        }
        return -1;
    }

    private static int stubIndexOf2ConsecutiveS2(byte[] haystack, long fromIndex, int maxIndex, int c1, int c2) {
        for (int i = (int)(fromIndex + 1L); i < maxIndex; ++i) {
            if (ArrayUtils.readChar(haystack, i - 1) != c1 || ArrayUtils.readChar(haystack, i) != c2) continue;
            return i - 1;
        }
        return -1;
    }

    private static int stubIndexOf2ConsecutiveS2(char[] haystack, long fromIndex, int maxIndex, int c1, int c2) {
        for (int i = (int)(fromIndex + 1L); i < maxIndex; ++i) {
            if (haystack[i - 1] != c1 || haystack[i] != c2) continue;
            return i - 1;
        }
        return -1;
    }

    private static int stubIndexOf2ConsecutiveWithOrMaskS1(byte[] haystack, long fromIndex, int maxIndex, int c1, int c2, int mask1, int mask2) {
        for (int i = (int)(fromIndex + 1L); i < maxIndex; ++i) {
            if ((Byte.toUnsignedInt(haystack[i - 1]) | mask1) != c1 || (Byte.toUnsignedInt(haystack[i]) | mask2) != c2) continue;
            return i - 1;
        }
        return -1;
    }

    private static int stubIndexOf2ConsecutiveWithOrMaskS2(byte[] haystack, long fromIndex, int maxIndex, int c1, int c2, int mask1, int mask2) {
        for (int i = (int)(fromIndex + 1L); i < maxIndex; ++i) {
            if ((ArrayUtils.readChar(haystack, i - 1) | mask1) != c1 || (ArrayUtils.readChar(haystack, i) | mask2) != c2) continue;
            return i - 1;
        }
        return -1;
    }

    private static int stubIndexOf2ConsecutiveWithOrMaskS2(char[] haystack, long fromIndex, int maxIndex, int c1, int c2, int mask1, int mask2) {
        for (int i = (int)(fromIndex + 1L); i < maxIndex; ++i) {
            if ((haystack[i - 1] | mask1) != c1 || (haystack[i] | mask2) != c2) continue;
            return i - 1;
        }
        return -1;
    }

    private static boolean stubRegionEqualsS1(byte[] a, long offsetA, byte[] b, long offsetB, int length) {
        for (int i = 0; i < length; ++i) {
            if (a[(int)offsetA + i] == b[(int)offsetB + i]) continue;
            return false;
        }
        return true;
    }

    private static boolean stubRegionEqualsS2S1(byte[] a, long offsetA, byte[] b, long offsetB, int length) {
        for (int i = 0; i < length; ++i) {
            if (ArrayUtils.readChar(a, (int)offsetA + i) == Byte.toUnsignedInt(b[(int)offsetB + i])) continue;
            return false;
        }
        return true;
    }

    private static boolean stubRegionEqualsS2(char[] a, long offsetA, char[] b, long offsetB, int length) {
        for (int i = 0; i < length; ++i) {
            if (a[(int)offsetA + i] == b[(int)offsetB + i]) continue;
            return false;
        }
        return true;
    }

    private static boolean stubRegionEqualsWithOrMaskS1(byte[] a, long offsetA, byte[] b, long offsetB, byte[] mask, int length) {
        for (int i = 0; i < length; ++i) {
            if ((a[(int)offsetA + i] | mask[i]) == b[(int)offsetB + i]) continue;
            return false;
        }
        return true;
    }

    private static boolean stubRegionEqualsWithOrMaskS2(char[] a, long offsetA, char[] b, long offsetB, char[] mask, int length) {
        for (int i = 0; i < length; ++i) {
            if ((a[(int)offsetA + i] | mask[i]) == b[(int)offsetB + i]) continue;
            return false;
        }
        return true;
    }

    private static boolean stubRegionEqualsWithOrMaskCompactStrings(byte[] a, long offsetA, byte[] b, long offsetB, byte[] mask, int length, boolean compactA, boolean compactB, boolean compactM) {
        for (int i = 0; i < length; ++i) {
            int vM;
            int vA = compactA ? Byte.toUnsignedInt(a[(int)offsetA + i]) : ArrayUtils.readChar(a, (int)offsetA + i);
            int vB = compactB ? Byte.toUnsignedInt(b[(int)offsetB + i]) : ArrayUtils.readChar(b, (int)offsetB + i);
            int n = vM = compactM ? Byte.toUnsignedInt(mask[i]) : ArrayUtils.readChar(mask, i);
            if ((vA | vM) == vB) continue;
            return false;
        }
        return true;
    }

    private static int readChar(byte[] array, int i) {
        int byte0 = Byte.toUnsignedInt(array[i << 1]);
        int byte1 = Byte.toUnsignedInt(array[(i << 1) + 1]);
        return ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN ? byte0 | byte1 << 8 : byte0 << 8 | byte1;
    }

    private static int runIndexOfS1(byte[] array, long fromIndex, int maxIndex, int ... values) {
        for (int i = (int)fromIndex; i < maxIndex; ++i) {
            for (int v : values) {
                if (Byte.toUnsignedInt(array[i]) != v) continue;
                return i;
            }
        }
        return -1;
    }

    private static int runIndexOfS1(byte[] array, long fromIndex, int maxIndex, byte ... values) {
        for (int i = (int)fromIndex; i < maxIndex; ++i) {
            for (byte v : values) {
                if (array[i] != v) continue;
                return i;
            }
        }
        return -1;
    }

    private static int runIndexOfS2(byte[] array, long fromIndex, int maxIndex, int ... values) {
        for (int i = (int)fromIndex; i < maxIndex; ++i) {
            int c = ArrayUtils.readChar(array, i);
            for (int v : values) {
                if (c != v) continue;
                return i;
            }
        }
        return -1;
    }

    private static int runIndexOfS2(char[] array, long fromIndex, int maxIndex, int ... values) {
        for (int i = (int)fromIndex; i < maxIndex; ++i) {
            for (int v : values) {
                if (array[i] != v) continue;
                return i;
            }
        }
        return -1;
    }

    private static int runIndexOfS2(char[] array, long fromIndex, int maxIndex, char ... values) {
        for (int i = (int)fromIndex; i < maxIndex; ++i) {
            for (char v : values) {
                if (array[i] != v) continue;
                return i;
            }
        }
        return -1;
    }

    private static int runIndexOfS2(String haystack, long fromIndex, int maxIndex, char[] needle) {
        for (int i = (int)fromIndex; i < maxIndex; ++i) {
            for (char c : needle) {
                if (haystack.charAt(i) != c) continue;
                return i;
            }
        }
        return -1;
    }

    static {
        Field valueField = ArrayUtils.getStringDeclaredField("value");
        javaStringValueFieldOffset = ArrayUtils.getObjectFieldOffset(valueField);
        Field coderField = ArrayUtils.getStringDeclaredField("coder");
        javaStringCoderFieldOffset = ArrayUtils.getObjectFieldOffset(coderField);
    }
}

