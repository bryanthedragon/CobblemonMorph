
package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.AbstractTruffleString;
import com.oracle.truffle.api.strings.Encodings;
import com.oracle.truffle.api.strings.Stride;
import com.oracle.truffle.api.strings.StringAttributes;
import com.oracle.truffle.api.strings.TSCodeRange;
import com.oracle.truffle.api.strings.TStringConstants;
import com.oracle.truffle.api.strings.TStringGuards;
import com.oracle.truffle.api.strings.TStringUnsafe;
import com.oracle.truffle.api.strings.TruffleString;
import sun.misc.Unsafe;

final class TStringOps {
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private static final byte[] UTF_8_STATE_MACHINE = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 10, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 11, 6, 6, 6, 5, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 0, 12, 24, 36, 60, 96, 84, 12, 12, 12, 48, 72, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0, 12, 12, 12, 12, 12, 0, 12, 0, 12, 12, 12, 24, 12, 12, 12, 12, 12, 24, 12, 24, 12, 12, 12, 12, 12, 12, 12, 12, 12, 24, 12, 12, 12, 12, 12, 24, 12, 12, 12, 12, 12, 12, 12, 24, 12, 12, 12, 12, 12, 12, 12, 12, 12, 36, 12, 36, 12, 12, 12, 36, 12, 12, 12, 12, 12, 36, 12, 36, 12, 12, 12, 36, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};
    private static final byte UTF8_ACCEPT = 0;

    TStringOps() {
    }

    static int readFromByteArray(byte[] array, int stride, int i) {
        return TStringOps.readValue((Object)array, 0, array.length >> stride, stride, i);
    }

    static void writeToByteArray(byte[] array, int stride, int i, int value2) {
        TStringOps.writeValue((Object)array, 0, array.length >> stride, stride, i, value2);
    }

    static int readValue(AbstractTruffleString a, Object arrayA, int stride, int i) {
        return TStringOps.readValue(arrayA, a.offset(), a.length(), stride, i);
    }

    static int readS0(AbstractTruffleString a, Object arrayA, int i) {
        return TStringOps.readS0(arrayA, a.offset(), a.length(), i);
    }

    static char readS1(AbstractTruffleString a, Object arrayA, int i) {
        return TStringOps.readS1(arrayA, a.offset(), a.length(), i);
    }

    static int readS2(AbstractTruffleString a, Object arrayA, int i) {
        return TStringOps.readS2(arrayA, a.offset(), a.length(), i);
    }

    static int readS0(Object array, int offset, int length, int i) {
        return TStringOps.readValue(array, offset, length, 0, i);
    }

    static char readS1(Object array, int offset, int length, int i) {
        return (char)TStringOps.readValue(array, offset, length, 1, i);
    }

    static int readS2(Object array, int offset, int length, int i) {
        return TStringOps.readValue(array, offset, length, 2, i);
    }

    static long readS3(Object array, int offset, int length) {
        boolean isNative = TStringOps.isNativePointer(array);
        Object stubArray = TStringOps.stubArray(array, isNative);
        TStringOps.validateRegion(stubArray, offset, length, 3, isNative);
        long stubOffset = TStringOps.stubOffset(array, offset, isNative);
        if (isNative) {
            return TStringOps.runReadS3Native(stubOffset);
        }
        return TStringOps.runReadS3Managed(stubArray, stubOffset);
    }

    static void writeS0(Object array, int offset, int length, int i, byte value2) {
        TStringOps.writeValue(array, offset, length, 0, i, TStringOps.uInt(value2));
    }

    private static int readValue(Object array, int offset, int length, int stride, int i) {
        boolean isNative = TStringOps.isNativePointer(array);
        Object stubArray = TStringOps.stubArray(array, isNative);
        TStringOps.validateRegionIndex(stubArray, offset, length, stride, i, isNative);
        long stubOffset = TStringOps.stubOffset(array, offset, isNative);
        return TStringOps.readValue(stubArray, stubOffset, stride, i, isNative);
    }

    private static void writeValue(Object array, int offset, int length, int stride, int i, int value2) {
        boolean isNative = TStringOps.isNativePointer(array);
        Object stubArray = TStringOps.stubArray(array, isNative);
        TStringOps.validateRegionIndex(stubArray, offset, length, stride, i, isNative);
        long stubOffset = TStringOps.stubOffset(array, offset, isNative);
        TStringOps.writeValue(stubArray, stubOffset, stride, i, isNative, value2);
    }

    private static int readValueS0(Object array, long offset, int i, boolean isNative) {
        return TStringOps.readValue(array, offset, 0, i, isNative);
    }

    private static int readValueS1(Object array, long offset, int i, boolean isNative) {
        return TStringOps.readValue(array, offset, 1, i, isNative);
    }

    private static int readValueS2(Object array, long offset, int i, boolean isNative) {
        return TStringOps.readValue(array, offset, 2, i, isNative);
    }

    private static int readValue(Object array, long offset, int stride, int i, boolean isNative) {
        if (isNative) {
            assert (array == null);
            switch (stride) {
                case 0: {
                    return TStringOps.runReadS0Native(offset, i);
                }
                case 1: {
                    return TStringOps.runReadS1Native(offset, (long)i << 1);
                }
            }
            return TStringOps.runReadS2Native(offset, (long)i << 2);
        }
        if (array == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw CompilerDirectives.shouldNotReachHere();
        }
        switch (stride) {
            case 0: {
                return TStringOps.runReadS0Managed(array, offset + (long)i);
            }
            case 1: {
                return TStringOps.runReadS1Managed(array, offset + ((long)i << 1));
            }
        }
        return TStringOps.runReadS2Managed(array, offset + ((long)i << 2));
    }

    private static void writeValue(Object array, long offset, int stride, int i, boolean isNative, int value2) {
        if (isNative) {
            switch (stride) {
                case 0: {
                    TStringOps.runWriteS0Native(offset, i, (byte)value2);
                    return;
                }
                case 1: {
                    TStringOps.runWriteS1Native(offset, (long)i << 1, (char)value2);
                    return;
                }
            }
            TStringOps.runWriteS2Native(offset, (long)i << 2, value2);
        } else {
            if (array == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                throw CompilerDirectives.shouldNotReachHere();
            }
            switch (stride) {
                case 0: {
                    TStringOps.runWriteS0Managed((byte[])array, offset + (long)i, (byte)value2);
                    return;
                }
                case 1: {
                    TStringOps.runWriteS1Managed((byte[])array, offset + ((long)i << 1), (char)value2);
                    return;
                }
            }
            TStringOps.runWriteS2Managed((byte[])array, offset + ((long)i << 2), value2);
        }
    }

    static int indexOfAnyByte(Node location, AbstractTruffleString a, Object arrayA, int fromIndex, int toIndex, byte[] values) {
        assert (a.stride() == 0);
        return TStringOps.indexOfAnyByteIntl(location, arrayA, a.offset(), toIndex, fromIndex, values);
    }

    private static int indexOfAnyByteIntl(Node location, Object array, int offset, int length, int fromIndex, byte[] values) {
        boolean isNative = TStringOps.isNativePointer(array);
        Object stubArray = TStringOps.stubArray(array, isNative);
        TStringOps.validateRegionIndex(stubArray, offset, length, 0, fromIndex, isNative);
        long stubOffset = TStringOps.stubOffset(array, offset, isNative);
        switch (values.length) {
            case 1: {
                return TStringOps.runIndexOfAny1(location, stubArray, stubOffset, length, 0, isNative, fromIndex, TStringOps.uInt(values[0]));
            }
            case 2: {
                return TStringOps.runIndexOfAny2(location, stubArray, stubOffset, length, 0, isNative, fromIndex, TStringOps.uInt(values[0]), TStringOps.uInt(values[1]));
            }
            case 3: {
                return TStringOps.runIndexOfAny3(location, stubArray, stubOffset, length, 0, isNative, fromIndex, TStringOps.uInt(values[0]), TStringOps.uInt(values[1]), TStringOps.uInt(values[2]));
            }
            case 4: {
                return TStringOps.runIndexOfAny4(location, stubArray, stubOffset, length, 0, isNative, fromIndex, TStringOps.uInt(values[0]), TStringOps.uInt(values[1]), TStringOps.uInt(values[2]), TStringOps.uInt(values[3]));
            }
        }
        return TStringOps.runIndexOfAnyByte(location, stubArray, stubOffset, length, isNative, fromIndex, values);
    }

    static int indexOfAnyChar(Node location, AbstractTruffleString a, Object arrayA, int stride, int fromIndex, int toIndex, char[] values) {
        assert (stride == 0 || stride == 1);
        return TStringOps.indexOfAnyCharIntl(location, arrayA, a.offset(), toIndex, stride, fromIndex, values);
    }

    private static int indexOfAnyCharIntl(Node location, Object array, int offset, int length, int stride, int fromIndex, char[] values) {
        boolean isNative = TStringOps.isNativePointer(array);
        Object stubArray = TStringOps.stubArray(array, isNative);
        TStringOps.validateRegionIndex(stubArray, offset, length, stride, fromIndex, isNative);
        long stubOffset = TStringOps.stubOffset(array, offset, isNative);
        if (stride == 0) {
            switch (values.length) {
                case 1: {
                    return TStringOps.runIndexOfAny1(location, stubArray, stubOffset, length, 0, isNative, fromIndex, values[0]);
                }
                case 2: {
                    return TStringOps.runIndexOfAny2(location, stubArray, stubOffset, length, 0, isNative, fromIndex, values[0], values[1]);
                }
                case 3: {
                    return TStringOps.runIndexOfAny3(location, stubArray, stubOffset, length, 0, isNative, fromIndex, values[0], values[1], values[2]);
                }
                case 4: {
                    return TStringOps.runIndexOfAny4(location, stubArray, stubOffset, length, 0, isNative, fromIndex, values[0], values[1], values[2], values[3]);
                }
            }
        } else {
            assert (stride == 1);
            switch (values.length) {
                case 1: {
                    return TStringOps.runIndexOfAny1(location, stubArray, stubOffset, length, 1, isNative, fromIndex, values[0]);
                }
                case 2: {
                    return TStringOps.runIndexOfAny2(location, stubArray, stubOffset, length, 1, isNative, fromIndex, values[0], values[1]);
                }
                case 3: {
                    return TStringOps.runIndexOfAny3(location, stubArray, stubOffset, length, 1, isNative, fromIndex, values[0], values[1], values[2]);
                }
                case 4: {
                    return TStringOps.runIndexOfAny4(location, stubArray, stubOffset, length, 1, isNative, fromIndex, values[0], values[1], values[2], values[3]);
                }
            }
        }
        return TStringOps.runIndexOfAnyChar(location, stubArray, stubOffset, length, stride, isNative, fromIndex, values);
    }

    static int indexOfAnyInt(Node location, AbstractTruffleString a, Object arrayA, int stride, int fromIndex, int toIndex, int[] values) {
        return TStringOps.indexOfAnyIntIntl(location, arrayA, a.offset(), toIndex, stride, fromIndex, values);
    }

    private static int indexOfAnyIntIntl(Node location, Object array, int offset, int length, int stride, int fromIndex, int[] values) {
        boolean isNative = TStringOps.isNativePointer(array);
        Object stubArray = TStringOps.stubArray(array, isNative);
        TStringOps.validateRegionIndex(stubArray, offset, length, stride, fromIndex, isNative);
        long stubOffset = TStringOps.stubOffset(array, offset, isNative);
        if (stride == 0) {
            switch (values.length) {
                case 1: {
                    return TStringOps.runIndexOfAny1(location, stubArray, stubOffset, length, 0, isNative, fromIndex, values[0]);
                }
                case 2: {
                    return TStringOps.runIndexOfAny2(location, stubArray, stubOffset, length, 0, isNative, fromIndex, values[0], values[1]);
                }
                case 3: {
                    return TStringOps.runIndexOfAny3(location, stubArray, stubOffset, length, 0, isNative, fromIndex, values[0], values[1], values[2]);
                }
                case 4: {
                    return TStringOps.runIndexOfAny4(location, stubArray, stubOffset, length, 0, isNative, fromIndex, values[0], values[1], values[2], values[3]);
                }
            }
        } else if (stride == 1) {
            switch (values.length) {
                case 1: {
                    return TStringOps.runIndexOfAny1(location, stubArray, stubOffset, length, 1, isNative, fromIndex, values[0]);
                }
                case 2: {
                    return TStringOps.runIndexOfAny2(location, stubArray, stubOffset, length, 1, isNative, fromIndex, values[0], values[1]);
                }
                case 3: {
                    return TStringOps.runIndexOfAny3(location, stubArray, stubOffset, length, 1, isNative, fromIndex, values[0], values[1], values[2]);
                }
                case 4: {
                    return TStringOps.runIndexOfAny4(location, stubArray, stubOffset, length, 1, isNative, fromIndex, values[0], values[1], values[2], values[3]);
                }
            }
        } else {
            assert (stride == 2);
            switch (values.length) {
                case 1: {
                    return TStringOps.runIndexOfAny1(location, stubArray, stubOffset, length, 2, isNative, fromIndex, values[0]);
                }
                case 2: {
                    return TStringOps.runIndexOfAny2(location, stubArray, stubOffset, length, 2, isNative, fromIndex, values[0], values[1]);
                }
                case 3: {
                    return TStringOps.runIndexOfAny3(location, stubArray, stubOffset, length, 2, isNative, fromIndex, values[0], values[1], values[2]);
                }
                case 4: {
                    return TStringOps.runIndexOfAny4(location, stubArray, stubOffset, length, 2, isNative, fromIndex, values[0], values[1], values[2], values[3]);
                }
            }
        }
        return TStringOps.runIndexOfAnyInt(location, stubArray, stubOffset, length, stride, isNative, fromIndex, values);
    }

    static int indexOfCodePointWithStride(Node location, AbstractTruffleString a, Object arrayA, int strideA, int fromIndex, int toIndex, int codepoint) {
        return TStringOps.indexOfCodePointWithStrideIntl(location, arrayA, a.offset(), toIndex, strideA, fromIndex, codepoint);
    }

    private static int indexOfCodePointWithStrideIntl(Node location, Object array, int offset, int length, int stride, int fromIndex, int v1) {
        boolean isNative = TStringOps.isNativePointer(array);
        Object stubArray = TStringOps.stubArray(array, isNative);
        TStringOps.validateRegionIndex(stubArray, offset, length, stride, fromIndex, isNative);
        long stubOffset = TStringOps.stubOffset(array, offset, isNative);
        switch (stride) {
            case 0: {
                return TStringOps.runIndexOfAny1(location, stubArray, stubOffset, length, 0, isNative, fromIndex, v1);
            }
            case 1: {
                return TStringOps.runIndexOfAny1(location, stubArray, stubOffset, length, 1, isNative, fromIndex, v1);
            }
        }
        assert (stride == 2);
        return TStringOps.runIndexOfAny1(location, stubArray, stubOffset, length, 2, isNative, fromIndex, v1);
    }

    static int indexOfCodePointWithOrMaskWithStride(Node location, AbstractTruffleString a, Object arrayA, int strideA, int fromIndex, int toIndex, int codepoint, int maskA) {
        return TStringOps.indexOfCodePointWithMaskWithStrideIntl(location, arrayA, a.offset(), toIndex, strideA, fromIndex, codepoint, maskA);
    }

    private static int indexOfCodePointWithMaskWithStrideIntl(Node location, Object array, int offset, int length, int stride, int fromIndex, int v1, int mask1) {
        boolean isNative = TStringOps.isNativePointer(array);
        Object stubArray = TStringOps.stubArray(array, isNative);
        TStringOps.validateRegionIndex(stubArray, offset, length, stride, fromIndex, isNative);
        long stubOffset = TStringOps.stubOffset(array, offset, isNative);
        switch (stride) {
            case 0: {
                return (v1 ^ mask1) <= 255 ? TStringOps.runIndexOfWithOrMaskWithStride(location, stubArray, stubOffset, length, 0, isNative, fromIndex, v1, mask1) : -1;
            }
            case 1: {
                return (v1 ^ mask1) <= 65535 ? TStringOps.runIndexOfWithOrMaskWithStride(location, stubArray, stubOffset, length, 1, isNative, fromIndex, v1, mask1) : -1;
            }
        }
        assert (stride == 2);
        return TStringOps.runIndexOfWithOrMaskWithStride(location, stubArray, stubOffset, length, 2, isNative, fromIndex, v1, mask1);
    }

    static int indexOf2ConsecutiveWithStride(Node location, AbstractTruffleString a, Object arrayA, int strideA, int fromIndex, int toIndex, int v1, int v2) {
        return TStringOps.indexOf2ConsecutiveWithStrideIntl(location, arrayA, a.offset(), toIndex, strideA, fromIndex, v1, v2);
    }

    private static int indexOf2ConsecutiveWithStrideIntl(Node location, Object array, int offset, int length, int stride, int fromIndex, int v1, int v2) {
        boolean isNative = TStringOps.isNativePointer(array);
        Object stubArray = TStringOps.stubArray(array, isNative);
        TStringOps.validateRegionIndex(stubArray, offset, length, stride, fromIndex, isNative);
        long stubOffset = TStringOps.stubOffset(array, offset, isNative);
        switch (stride) {
            case 0: {
                return (v1 | v2) <= 255 ? TStringOps.runIndexOf2ConsecutiveWithStride(location, stubArray, stubOffset, length, 0, isNative, fromIndex, v1, v2) : -1;
            }
            case 1: {
                return (v1 | v2) <= 65535 ? TStringOps.runIndexOf2ConsecutiveWithStride(location, stubArray, stubOffset, length, 1, isNative, fromIndex, v1, v2) : -1;
            }
        }
        assert (stride == 2);
        return TStringOps.runIndexOf2ConsecutiveWithStride(location, stubArray, stubOffset, length, 2, isNative, fromIndex, v1, v2);
    }

    private static int indexOf2ConsecutiveWithOrMaskWithStrideIntl(Node location, Object array, int offset, int length, int stride, int fromIndex, int v1, int v2, int mask1, int mask2) {
        boolean isNative = TStringOps.isNativePointer(array);
        Object stubArray = TStringOps.stubArray(array, isNative);
        TStringOps.validateRegionIndex(stubArray, offset, length, stride, fromIndex, isNative);
        long stubOffset = TStringOps.stubOffset(array, offset, isNative);
        switch (stride) {
            case 0: {
                return (v1 ^ mask1 | v2 ^ mask2) <= 255 ? TStringOps.runIndexOf2ConsecutiveWithOrMaskWithStride(location, stubArray, stubOffset, length, 0, isNative, fromIndex, v1, v2, mask1, mask2) : -1;
            }
            case 1: {
                return (v1 ^ mask1 | v2 ^ mask2) <= 65535 ? TStringOps.runIndexOf2ConsecutiveWithOrMaskWithStride(location, stubArray, stubOffset, length, 1, isNative, fromIndex, v1, v2, mask1, mask2) : -1;
            }
        }
        assert (stride == 2);
        return TStringOps.runIndexOf2ConsecutiveWithOrMaskWithStride(location, stubArray, stubOffset, length, 2, isNative, fromIndex, v1, v2, mask1, mask2);
    }

    static int indexOfStringWithOrMaskWithStride(Node location, AbstractTruffleString a, Object arrayA, int strideA, AbstractTruffleString b, Object arrayB, int strideB, int fromIndex, int toIndex, byte[] maskB) {
        int mask1;
        int offsetMask = 0;
        assert (b.length() > 1);
        assert (a.length() >= b.length());
        int max2 = toIndex - (b.length() - 2);
        int index = fromIndex;
        int b0 = TStringOps.readValue(b, arrayB, strideB, 0);
        int b1 = TStringOps.readValue(b, arrayB, strideB, 1);
        int mask0 = maskB == null ? 0 : TStringOps.readValue((Object)maskB, offsetMask, b.length(), strideB, 0);
        int n = mask1 = maskB == null ? 0 : TStringOps.readValue((Object)maskB, offsetMask, b.length(), strideB, 1);
        while (index < max2 - 1) {
            index = maskB == null ? TStringOps.indexOf2ConsecutiveWithStrideIntl(location, arrayA, a.offset(), max2, strideA, index, b0, b1) : TStringOps.indexOf2ConsecutiveWithOrMaskWithStrideIntl(location, arrayA, a.offset(), max2, strideA, index, b0, b1, mask0, mask1);
            if (index < 0) {
                return -1;
            }
            if (b.length() == 2 || TStringOps.regionEqualsWithOrMaskWithStride(location, a, arrayA, strideA, index, b, arrayB, strideB, 0, maskB, b.length())) {
                return index;
            }
            TStringConstants.truffleSafePointPoll(location, ++index);
        }
        return -1;
    }

    static int lastIndexOfCodePointWithOrMaskWithStride(Node location, AbstractTruffleString a, Object arrayA, int stride, int fromIndex, int toIndex, int codepoint, int mask) {
        return TStringOps.lastIndexOfCodePointWithOrMaskWithStrideIntl(location, arrayA, a.offset(), stride, fromIndex, toIndex, codepoint, mask);
    }

    private static int lastIndexOfCodePointWithOrMaskWithStrideIntl(Node location, Object array, int offset, int stride, int fromIndex, int toIndex, int codepoint, int mask) {
        boolean isNative = TStringOps.isNativePointer(array);
        Object stubArray = TStringOps.stubArray(array, isNative);
        TStringOps.validateRegionIndex(stubArray, offset, fromIndex, stride, toIndex, isNative);
        long stubOffset = TStringOps.stubOffset(array, offset, isNative);
        switch (stride) {
            case 0: {
                return TStringOps.runLastIndexOfWithOrMaskWithStride(location, stubArray, stubOffset, 0, isNative, fromIndex, toIndex, codepoint, mask);
            }
            case 1: {
                return TStringOps.runLastIndexOfWithOrMaskWithStride(location, stubArray, stubOffset, 1, isNative, fromIndex, toIndex, codepoint, mask);
            }
        }
        assert (stride == 2);
        return TStringOps.runLastIndexOfWithOrMaskWithStride(location, stubArray, stubOffset, 2, isNative, fromIndex, toIndex, codepoint, mask);
    }

    static int lastIndexOf2ConsecutiveWithOrMaskWithStride(Node location, AbstractTruffleString a, Object arrayA, int stride, int fromIndex, int toIndex, int v1, int v2, int mask1, int mask2) {
        return TStringOps.lastIndexOf2ConsecutiveWithOrMaskWithStrideIntl(location, arrayA, a.offset(), stride, fromIndex, toIndex, v1, v2, mask1, mask2);
    }

    private static int lastIndexOf2ConsecutiveWithOrMaskWithStrideIntl(Node location, Object array, int offset, int stride, int fromIndex, int toIndex, int v1, int v2, int mask1, int mask2) {
        boolean isNative = TStringOps.isNativePointer(array);
        Object stubArray = TStringOps.stubArray(array, isNative);
        TStringOps.validateRegionIndex(stubArray, offset, fromIndex, stride, toIndex, isNative);
        long stubOffset = TStringOps.stubOffset(array, offset, isNative);
        switch (stride) {
            case 0: {
                return TStringOps.runLastIndexOf2ConsecutiveWithOrMaskWithStride(location, stubArray, stubOffset, 0, isNative, fromIndex, toIndex, v1, v2, mask1, mask2);
            }
            case 1: {
                return TStringOps.runLastIndexOf2ConsecutiveWithOrMaskWithStride(location, stubArray, stubOffset, 1, isNative, fromIndex, toIndex, v1, v2, mask1, mask2);
            }
        }
        assert (stride == 2);
        return TStringOps.runLastIndexOf2ConsecutiveWithOrMaskWithStride(location, stubArray, stubOffset, 2, isNative, fromIndex, toIndex, v1, v2, mask1, mask2);
    }

    static int lastIndexOfStringWithOrMaskWithStride(Node location, AbstractTruffleString a, Object arrayA, int strideA, AbstractTruffleString b, Object arrayB, int strideB, int fromIndex, int toIndex, byte[] maskB) {
        int offsetMask = 0;
        assert (b.length() > 1);
        assert (a.length() >= b.length());
        int index = fromIndex;
        int b0 = TStringOps.readValue(b, arrayB, strideB, b.length() - 2);
        int b1 = TStringOps.readValue(b, arrayB, strideB, b.length() - 1);
        int mask0 = maskB == null ? 0 : TStringOps.readValue((Object)maskB, offsetMask, b.length(), strideB, b.length() - 2);
        int mask1 = maskB == null ? 0 : TStringOps.readValue((Object)maskB, offsetMask, b.length(), strideB, b.length() - 1);
        int toIndex2Consecutive = toIndex + b.length() - 2;
        while (index > toIndex2Consecutive) {
            index = TStringOps.lastIndexOf2ConsecutiveWithOrMaskWithStrideIntl(location, arrayA, a.offset(), strideA, index, toIndex2Consecutive, b0, b1, mask0, mask1);
            if (index < 0) {
                return -1;
            }
            if (b.length() == 2 || TStringOps.regionEqualsWithOrMaskWithStride(location, a, arrayA, strideA, (index += 2) - b.length(), b, arrayB, strideB, 0, maskB, b.length())) {
                return index - b.length();
            }
            TStringConstants.truffleSafePointPoll(location, --index);
        }
        return -1;
    }

    static boolean regionEqualsWithOrMaskWithStride(Node location, AbstractTruffleString a, Object arrayA, int strideA, int fromIndexA, AbstractTruffleString b, Object arrayB, int strideB, int fromIndexB, byte[] maskB, int lengthCMP) {
        return TStringOps.regionEqualsWithOrMaskWithStrideIntl(location, arrayA, a.offset(), a.length(), strideA, fromIndexA, arrayB, b.offset(), b.length(), strideB, fromIndexB, maskB, lengthCMP);
    }

    private static boolean regionEqualsWithOrMaskWithStrideIntl(Node location, Object arrayA, int offsetA, int lengthA, int strideA, int fromIndexA, Object arrayB, int offsetB, int lengthB, int strideB, int fromIndexB, byte[] maskB, int lengthCMP) {
        if (!TStringOps.rangeInBounds(fromIndexA, lengthCMP, lengthA) || !TStringOps.rangeInBounds(fromIndexB, lengthCMP, lengthB)) {
            return false;
        }
        boolean isNativeA = TStringOps.isNativePointer(arrayA);
        boolean isNativeB = TStringOps.isNativePointer(arrayB);
        Object stubArrayA = TStringOps.stubArray(arrayA, isNativeA);
        Object stubArrayB = TStringOps.stubArray(arrayB, isNativeB);
        TStringOps.validateRegion(stubArrayA, offsetA, lengthCMP, strideA, isNativeA);
        TStringOps.validateRegion(stubArrayB, offsetB, lengthCMP, strideB, isNativeB);
        long stubOffsetA = TStringOps.stubOffset(arrayA, offsetA, strideA, fromIndexA, isNativeA);
        long stubOffsetB = TStringOps.stubOffset(arrayB, offsetB, strideB, fromIndexB, isNativeB);
        int stubStride = TStringOps.stubStride(strideA, strideB);
        if (maskB == null) {
            return TStringOps.runRegionEqualsWithStride(location, stubArrayA, stubOffsetA, isNativeA, stubArrayB, stubOffsetB, isNativeB, lengthCMP, stubStride);
        }
        TStringOps.validateRegion(maskB, 0, lengthCMP, strideB, false);
        return TStringOps.runRegionEqualsWithOrMaskWithStride(location, stubArrayA, stubOffsetA, isNativeA, stubArrayB, stubOffsetB, isNativeB, maskB, lengthCMP, stubStride);
    }

    static int memcmpWithStride(Node location, AbstractTruffleString a, Object arrayA, int strideA, AbstractTruffleString b, Object arrayB, int strideB, int lengthCMP) {
        assert (lengthCMP <= a.length());
        assert (lengthCMP <= b.length());
        return TStringOps.memcmpWithStrideIntl(location, arrayA, a.offset(), strideA, arrayB, b.offset(), strideB, lengthCMP);
    }

    private static int memcmpWithStrideIntl(Node location, Object arrayA, int offsetA, int strideA, Object arrayB, int offsetB, int strideB, int lengthCMP) {
        if (lengthCMP == 0) {
            return 0;
        }
        boolean isNativeA = TStringOps.isNativePointer(arrayA);
        boolean isNativeB = TStringOps.isNativePointer(arrayB);
        Object stubArrayA = TStringOps.stubArray(arrayA, isNativeA);
        Object stubArrayB = TStringOps.stubArray(arrayB, isNativeB);
        TStringOps.validateRegion(stubArrayA, offsetA, lengthCMP, strideA, isNativeA);
        TStringOps.validateRegion(stubArrayB, offsetB, lengthCMP, strideB, isNativeB);
        long stubOffsetA = TStringOps.stubOffset(arrayA, offsetA, isNativeA);
        long stubOffsetB = TStringOps.stubOffset(arrayB, offsetB, isNativeB);
        return TStringOps.runMemCmp(location, stubArrayA, stubOffsetA, isNativeA, stubArrayB, stubOffsetB, isNativeB, lengthCMP, TStringOps.stubStride(strideA, strideB));
    }

    static int memcmpBytesWithStride(Node location, AbstractTruffleString a, Object arrayA, int strideA, AbstractTruffleString b, Object arrayB, int strideB, int lengthCMP) {
        assert (lengthCMP <= a.length());
        assert (lengthCMP <= b.length());
        return TStringOps.memcmpBytesWithStrideIntl(location, arrayA, a.offset(), strideA, arrayB, b.offset(), strideB, lengthCMP);
    }

    private static int memcmpBytesWithStrideIntl(Node location, Object arrayA, int offsetA, int strideA, Object arrayB, int offsetB, int strideB, int lengthCMP) {
        int swappedResult;
        boolean swappedIsNativeB;
        boolean swappedIsNativeA;
        long swappedOffsetB;
        long swappedOffsetA;
        Object swappedArrayB;
        Object swappedArrayA;
        int swappedStrideB;
        int swappedStrideA;
        if (lengthCMP == 0) {
            return 0;
        }
        boolean isNativeA = TStringOps.isNativePointer(arrayA);
        boolean isNativeB = TStringOps.isNativePointer(arrayB);
        Object stubArrayA = TStringOps.stubArray(arrayA, isNativeA);
        Object stubArrayB = TStringOps.stubArray(arrayB, isNativeB);
        TStringOps.validateRegion(stubArrayA, offsetA, lengthCMP, strideA, isNativeA);
        TStringOps.validateRegion(stubArrayB, offsetB, lengthCMP, strideB, isNativeB);
        long stubOffsetA = TStringOps.stubOffset(arrayA, offsetA, isNativeA);
        long stubOffsetB = TStringOps.stubOffset(arrayB, offsetB, isNativeB);
        if (strideA == strideB) {
            switch (strideA) {
                case 0: {
                    return TStringOps.runMemCmp(location, stubArrayA, stubOffsetA, isNativeA, stubArrayB, stubOffsetB, isNativeB, lengthCMP, 0);
                }
                case 1: {
                    return TStringOps.runMemCmpBytes(location, stubArrayA, stubOffsetA, 1, isNativeA, stubArrayB, stubOffsetB, 1, isNativeB, lengthCMP);
                }
            }
            assert (strideA == 2);
            return TStringOps.runMemCmpBytes(location, stubArrayA, stubOffsetA, 2, isNativeA, stubArrayB, stubOffsetB, 2, isNativeB, lengthCMP);
        }
        if (strideA < strideB) {
            swappedStrideA = strideB;
            swappedStrideB = strideA;
            swappedArrayA = stubArrayB;
            swappedArrayB = stubArrayA;
            swappedOffsetA = stubOffsetB;
            swappedOffsetB = stubOffsetA;
            swappedIsNativeA = isNativeB;
            swappedIsNativeB = isNativeA;
            swappedResult = -1;
        } else {
            swappedStrideA = strideA;
            swappedStrideB = strideB;
            swappedArrayA = stubArrayA;
            swappedArrayB = stubArrayB;
            swappedOffsetA = stubOffsetA;
            swappedOffsetB = stubOffsetB;
            swappedIsNativeA = isNativeA;
            swappedIsNativeB = isNativeB;
            swappedResult = 1;
        }
        if (swappedStrideA == 1) {
            assert (swappedStrideB == 0);
            return swappedResult * TStringOps.runMemCmpBytes(location, swappedArrayA, swappedOffsetA, 1, swappedIsNativeA, swappedArrayB, swappedOffsetB, 0, swappedIsNativeB, lengthCMP);
        }
        assert (swappedStrideA == 2);
        if (swappedStrideB == 0) {
            return swappedResult * TStringOps.runMemCmpBytes(location, swappedArrayA, swappedOffsetA, 2, swappedIsNativeA, swappedArrayB, swappedOffsetB, 0, swappedIsNativeB, lengthCMP);
        }
        assert (swappedStrideB == 1);
        return swappedResult * TStringOps.runMemCmpBytes(location, swappedArrayA, swappedOffsetA, 2, swappedIsNativeA, swappedArrayB, swappedOffsetB, 1, swappedIsNativeB, lengthCMP);
    }

    static int hashCodeWithStride(Node location, AbstractTruffleString a, Object arrayA, int stride) {
        return TStringOps.hashCodeWithStrideIntl(location, arrayA, a.offset(), a.length(), stride);
    }

    private static int hashCodeWithStrideIntl(Node location, Object array, int offset, int length, int stride) {
        boolean isNative = TStringOps.isNativePointer(array);
        Object stubArray = TStringOps.stubArray(array, isNative);
        TStringOps.validateRegion(stubArray, offset, length, stride, isNative);
        long stubOffset = TStringOps.stubOffset(array, offset, isNative);
        switch (stride) {
            case 0: {
                return TStringOps.runHashCode(location, stubArray, stubOffset, length, 0, isNative);
            }
            case 1: {
                return TStringOps.runHashCode(location, stubArray, stubOffset, length, 1, isNative);
            }
        }
        assert (stride == 2);
        return TStringOps.runHashCode(location, stubArray, stubOffset, length, 2, isNative);
    }

    static Object arraycopyWithStrideCB(Node location, char[] arrayA, int offsetA, byte[] arrayB, int offsetB, int strideB, int lengthCPY) {
        TStringOps.validateRegion(arrayA, offsetA, lengthCPY);
        TStringOps.validateRegion(arrayB, offsetB, lengthCPY, strideB);
        int stubOffsetA = Unsafe.ARRAY_CHAR_BASE_OFFSET + offsetA;
        int stubOffsetB = Unsafe.ARRAY_BYTE_BASE_OFFSET + offsetB;
        TStringOps.runArrayCopy(location, arrayA, stubOffsetA, false, arrayB, stubOffsetB, false, lengthCPY, TStringOps.stubStride(1, strideB));
        return arrayB;
    }

    static Object arraycopyWithStrideIB(Node location, int[] arrayA, int offsetA, byte[] arrayB, int offsetB, int strideB, int lengthCPY) {
        TStringOps.validateRegion(arrayA, offsetA, lengthCPY);
        TStringOps.validateRegion(arrayB, offsetB, lengthCPY, strideB);
        int stubOffsetA = Unsafe.ARRAY_INT_BASE_OFFSET + offsetA;
        int stubOffsetB = Unsafe.ARRAY_BYTE_BASE_OFFSET + offsetB;
        TStringOps.runArrayCopy(location, arrayA, stubOffsetA, false, arrayB, stubOffsetB, false, lengthCPY, TStringOps.stubStride(2, strideB));
        return arrayB;
    }

    static Object arraycopyWithStride(Node location, Object arrayA, int offsetA, int strideA, int fromIndexA, Object arrayB, int offsetB, int strideB, int fromIndexB, int lengthCPY) {
        boolean isNativeA = TStringOps.isNativePointer(arrayA);
        boolean isNativeB = TStringOps.isNativePointer(arrayB);
        Object stubArrayA = TStringOps.stubArray(arrayA, isNativeA);
        Object stubArrayB = TStringOps.stubArray(arrayB, isNativeB);
        TStringOps.validateRegion(stubArrayA, offsetA, lengthCPY, strideA, isNativeA);
        TStringOps.validateRegion(stubArrayB, offsetB, lengthCPY, strideB, isNativeB);
        long stubOffsetA = TStringOps.stubOffset(arrayA, offsetA, strideA, fromIndexA, isNativeA);
        long stubOffsetB = TStringOps.stubOffset(arrayB, offsetB, strideB, fromIndexB, isNativeB);
        TStringOps.runArrayCopy(location, stubArrayA, stubOffsetA, isNativeA, stubArrayB, stubOffsetB, isNativeB, lengthCPY, TStringOps.stubStride(strideA, strideB));
        return stubArrayB;
    }

    static byte[] arraycopyOfWithStride(Node location, Object arrayA, int offsetA, int lengthA, int strideA, int lengthB, int strideB) {
        byte[] dst = new byte[lengthB << strideB];
        TStringOps.arraycopyWithStride(location, arrayA, offsetA, strideA, 0, dst, 0, strideB, 0, lengthA);
        return dst;
    }

    static int calcStringAttributesLatin1(Node location, Object array, int offset, int length) {
        boolean isNative = TStringOps.isNativePointer(array);
        Object stubArray = TStringOps.stubArray(array, isNative);
        TStringOps.validateRegion(stubArray, offset, length, 0, isNative);
        long stubOffset = TStringOps.stubOffset(array, offset, 0, 0L, isNative);
        return TStringOps.runCalcStringAttributesLatin1(location, stubArray, stubOffset, length, isNative);
    }

    static int calcStringAttributesBMP(Node location, Object array, int offset, int length) {
        boolean isNative = TStringOps.isNativePointer(array);
        Object stubArray = TStringOps.stubArray(array, isNative);
        TStringOps.validateRegion(stubArray, offset, length, 1, isNative);
        long stubOffset = TStringOps.stubOffset(array, offset, 1, 0L, isNative);
        return TStringOps.runCalcStringAttributesBMP(location, stubArray, stubOffset, length, isNative);
    }

    static long calcStringAttributesUTF8(Node location, Object array, int offset, int length, boolean assumeValid, boolean isAtEnd, ConditionProfile brokenProfile) {
        boolean isNative = TStringOps.isNativePointer(array);
        Object stubArray = TStringOps.stubArray(array, isNative);
        TStringOps.validateRegion(stubArray, offset, length, 0, isNative);
        long stubOffset = TStringOps.stubOffset(array, offset, 0, 0L, isNative);
        if (assumeValid && !Encodings.isUTF8ContinuationByte(TStringOps.readS0(array, offset, length, 0)) && (isAtEnd || !Encodings.isUTF8ContinuationByte(TStringOps.readS0(array, offset, length + 1, length)))) {
            return TStringOps.runCalcStringAttributesUTF8(location, stubArray, stubOffset, length, isNative, true);
        }
        long attrs = TStringOps.runCalcStringAttributesUTF8(location, stubArray, stubOffset, length, isNative, false);
        if (brokenProfile.profile(TStringGuards.isBrokenMultiByte(StringAttributes.getCodeRange(attrs)))) {
            int codePointLength = 0;
            for (int i = 0; i < length; i += Encodings.utf8GetCodePointLength(array, offset, length, i, TruffleString.ErrorHandling.BEST_EFFORT)) {
                TStringConstants.truffleSafePointPoll(location, ++codePointLength);
            }
            return StringAttributes.create(codePointLength, StringAttributes.getCodeRange(attrs));
        }
        return attrs;
    }

    static long calcStringAttributesUTF16C(Node location, char[] array, int offset, int length) {
        TStringOps.validateRegion(array, offset, length);
        int stubOffset = Unsafe.ARRAY_CHAR_BASE_OFFSET + offset;
        return TStringOps.runCalcStringAttributesUTF16C(location, array, stubOffset, length);
    }

    static long calcStringAttributesUTF16(Node location, Object array, int offset, int length, boolean assumeValid) {
        boolean isNative = TStringOps.isNativePointer(array);
        Object stubArray = TStringOps.stubArray(array, isNative);
        TStringOps.validateRegion(stubArray, offset, length, 1, isNative);
        long stubOffset = TStringOps.stubOffset(array, offset, 1, 0L, isNative);
        long attrs = assumeValid ? TStringOps.runCalcStringAttributesUTF16(location, stubArray, stubOffset, length, isNative, true) : TStringOps.runCalcStringAttributesUTF16(location, stubArray, stubOffset, length, isNative, false);
        if (assumeValid && length > 0) {
            if (Encodings.isUTF16LowSurrogate(TStringOps.readS1(array, offset, length, 0))) {
                attrs = StringAttributes.create(StringAttributes.getCodePointLength(attrs), TSCodeRange.getBrokenMultiByte());
            }
            if (Encodings.isUTF16HighSurrogate(TStringOps.readS1(array, offset, length, length - 1))) {
                attrs = StringAttributes.create(StringAttributes.getCodePointLength(attrs) + 1, TSCodeRange.getBrokenMultiByte());
            }
        }
        return attrs;
    }

    static int calcStringAttributesUTF32I(Node location, int[] array, int offset, int length) {
        TStringOps.validateRegion(array, offset, length);
        int stubOffset = Unsafe.ARRAY_INT_BASE_OFFSET + offset;
        return TStringOps.runCalcStringAttributesUTF32I(location, array, stubOffset, length);
    }

    static int calcStringAttributesUTF32(Node location, Object array, int offset, int length) {
        boolean isNative = TStringOps.isNativePointer(array);
        Object stubArray = TStringOps.stubArray(array, isNative);
        TStringOps.validateRegion(stubArray, offset, length, 2, isNative);
        long stubOffset = TStringOps.stubOffset(array, offset, 2, 0L, isNative);
        return TStringOps.runCalcStringAttributesUTF32(location, stubArray, stubOffset, length, isNative);
    }

    private static int runReadS0Managed(Object array, long byteOffset) {
        return TStringOps.uInt(TStringUnsafe.getByteManaged(array, byteOffset));
    }

    private static int runReadS0Native(long array, long byteOffset) {
        return TStringOps.uInt(TStringUnsafe.getByteNative(array, byteOffset));
    }

    private static int runReadS1Managed(Object array, long byteOffset) {
        return TStringUnsafe.getCharManaged(array, byteOffset);
    }

    private static int runReadS1Native(long array, long byteOffset) {
        return TStringUnsafe.getCharNative(array, byteOffset);
    }

    private static int runReadS2Managed(Object array, long byteOffset) {
        return TStringUnsafe.getIntManaged(array, byteOffset);
    }

    private static int runReadS2Native(long array, long byteOffset) {
        return TStringUnsafe.getIntNative(array, byteOffset);
    }

    private static long runReadS3Managed(Object array, long byteOffset) {
        return TStringUnsafe.getLongManaged(array, byteOffset);
    }

    private static long runReadS3Native(long array) {
        return TStringUnsafe.getLongNative(array);
    }

    private static void runWriteS0Managed(byte[] array, long byteOffset, byte value2) {
        TStringUnsafe.putByteManaged(array, byteOffset, value2);
    }

    private static void runWriteS0Native(long array, long byteOffset, byte value2) {
        TStringUnsafe.putByteNative(array, byteOffset, value2);
    }

    private static void runWriteS1Managed(byte[] array, long byteOffset, char value2) {
        TStringUnsafe.putCharManaged(array, byteOffset, value2);
    }

    private static void runWriteS1Native(long array, long byteOffset, char value2) {
        TStringUnsafe.putCharNative(array, byteOffset, value2);
    }

    private static void runWriteS2Managed(byte[] array, long byteOffset, int value2) {
        TStringUnsafe.putIntManaged(array, byteOffset, value2);
    }

    private static void runWriteS2Native(long array, long byteOffset, int value2) {
        TStringUnsafe.putIntNative(array, byteOffset, value2);
    }

    private static int runIndexOfAnyByte(Node location, Object array, long offset, int length, boolean isNative, int fromIndex, byte ... needle) {
        for (int i = fromIndex; i < length; ++i) {
            for (int j = 0; j < needle.length; ++j) {
                if (TStringOps.readValue(array, offset, 0, i, isNative) == TStringOps.uInt(needle[j])) {
                    return i;
                }
                TStringConstants.truffleSafePointPoll(location, j + 1);
            }
            TStringConstants.truffleSafePointPoll(location, i + 1);
        }
        return -1;
    }

    private static int runIndexOfAnyChar(Node location, Object array, long offset, int length, int stride, boolean isNative, int fromIndex, char ... needle) {
        for (int i = fromIndex; i < length; ++i) {
            for (int j = 0; j < needle.length; ++j) {
                if (TStringOps.readValue(array, offset, stride, i, isNative) == needle[j]) {
                    return i;
                }
                TStringConstants.truffleSafePointPoll(location, j + 1);
            }
            TStringConstants.truffleSafePointPoll(location, i + 1);
        }
        return -1;
    }

    private static int runIndexOfAnyInt(Node location, Object array, long offset, int length, int stride, boolean isNative, int fromIndex, int ... needle) {
        for (int i = fromIndex; i < length; ++i) {
            for (int j = 0; j < needle.length; ++j) {
                if (TStringOps.readValue(array, offset, stride, i, isNative) == needle[j]) {
                    return i;
                }
                TStringConstants.truffleSafePointPoll(location, j + 1);
            }
            TStringConstants.truffleSafePointPoll(location, i + 1);
        }
        return -1;
    }

    private static int runIndexOfAny1(Node location, Object array, long offset, int length, int stride, boolean isNative, int fromIndex, int v0) {
        return TStringOps.runIndexOfAnyInt(location, array, offset, length, stride, isNative, fromIndex, v0);
    }

    private static int runIndexOfAny2(Node location, Object array, long offset, int length, int stride, boolean isNative, int fromIndex, int v0, int v1) {
        return TStringOps.runIndexOfAnyInt(location, array, offset, length, stride, isNative, fromIndex, v0, v1);
    }

    private static int runIndexOfAny3(Node location, Object array, long offset, int length, int stride, boolean isNative, int fromIndex, int v0, int v1, int v2) {
        return TStringOps.runIndexOfAnyInt(location, array, offset, length, stride, isNative, fromIndex, v0, v1, v2);
    }

    private static int runIndexOfAny4(Node location, Object array, long offset, int length, int stride, boolean isNative, int fromIndex, int v0, int v1, int v2, int v3) {
        return TStringOps.runIndexOfAnyInt(location, array, offset, length, stride, isNative, fromIndex, v0, v1, v2, v3);
    }

    private static int runIndexOfWithOrMaskWithStride(Node location, Object array, long offset, int length, int stride, boolean isNative, int fromIndex, int needle, int mask) {
        for (int i = fromIndex; i < length; ++i) {
            if ((TStringOps.readValue(array, offset, stride, i, isNative) | mask) == needle) {
                return i;
            }
            TStringConstants.truffleSafePointPoll(location, i + 1);
        }
        return -1;
    }

    private static int runLastIndexOfWithOrMaskWithStride(Node location, Object array, long offset, int stride, boolean isNative, int fromIndex, int toIndex, int needle, int mask) {
        for (int i = fromIndex - 1; i >= toIndex; --i) {
            if ((TStringOps.readValue(array, offset, stride, i, isNative) | mask) == needle) {
                return i;
            }
            TStringConstants.truffleSafePointPoll(location, i);
        }
        return -1;
    }

    private static int runIndexOf2ConsecutiveWithStride(Node location, Object array, long offset, int length, int stride, boolean isNative, int fromIndex, int c1, int c2) {
        for (int i = fromIndex + 1; i < length; ++i) {
            if (TStringOps.readValue(array, offset, stride, i - 1, isNative) == c1 && TStringOps.readValue(array, offset, stride, i, isNative) == c2) {
                return i - 1;
            }
            TStringConstants.truffleSafePointPoll(location, i);
        }
        return -1;
    }

    private static int runIndexOf2ConsecutiveWithOrMaskWithStride(Node location, Object array, long offset, int length, int stride, boolean isNative, int fromIndex, int c1, int c2, int mask1, int mask2) {
        for (int i = fromIndex + 1; i < length; ++i) {
            if ((TStringOps.readValue(array, offset, stride, i - 1, isNative) | mask1) == c1 && (TStringOps.readValue(array, offset, stride, i, isNative) | mask2) == c2) {
                return i - 1;
            }
            TStringConstants.truffleSafePointPoll(location, i);
        }
        return -1;
    }

    private static int runLastIndexOf2ConsecutiveWithOrMaskWithStride(Node location, Object array, long offset, int stride, boolean isNative, int fromIndex, int toIndex, int c1, int c2, int mask1, int mask2) {
        for (int i = fromIndex - 1; i > toIndex; --i) {
            if ((TStringOps.readValue(array, offset, stride, i - 1, isNative) | mask1) == c1 && (TStringOps.readValue(array, offset, stride, i, isNative) | mask2) == c2) {
                return i - 1;
            }
            TStringConstants.truffleSafePointPoll(location, i);
        }
        return -1;
    }

    private static boolean runRegionEqualsWithStride(Node location, Object arrayA, long offsetA, boolean isNativeA, Object arrayB, long offsetB, boolean isNativeB, int length, int stubStride) {
        int strideA = TStringOps.stubStrideToStrideA(stubStride);
        int strideB = TStringOps.stubStrideToStrideB(stubStride);
        for (int i = 0; i < length; ++i) {
            if (TStringOps.readValue(arrayA, offsetA, strideA, i, isNativeA) != TStringOps.readValue(arrayB, offsetB, strideB, i, isNativeB)) {
                return false;
            }
            TStringConstants.truffleSafePointPoll(location, i + 1);
        }
        return true;
    }

    private static boolean runRegionEqualsWithOrMaskWithStride(Node location, Object arrayA, long offsetA, boolean isNativeA, Object arrayB, long offsetB, boolean isNativeB, byte[] arrayMask, int lengthCMP, int stubStride) {
        int strideA = TStringOps.stubStrideToStrideA(stubStride);
        int strideB = TStringOps.stubStrideToStrideB(stubStride);
        for (int i = 0; i < lengthCMP; ++i) {
            if ((TStringOps.readValue(arrayA, offsetA, strideA, i, isNativeA) | TStringOps.readFromByteArray(arrayMask, strideB, i)) != TStringOps.readValue(arrayB, offsetB, strideB, i, isNativeB)) {
                return false;
            }
            TStringConstants.truffleSafePointPoll(location, i + 1);
        }
        return true;
    }

    private static int runMemCmp(Node location, Object arrayA, long offsetA, boolean isNativeA, Object arrayB, long offsetB, boolean isNativeB, int lengthCMP, int stubStride) {
        int strideA = TStringOps.stubStrideToStrideA(stubStride);
        int strideB = TStringOps.stubStrideToStrideB(stubStride);
        for (int i = 0; i < lengthCMP; ++i) {
            int cmp = TStringOps.readValue(arrayA, offsetA, strideA, i, isNativeA) - TStringOps.readValue(arrayB, offsetB, strideB, i, isNativeB);
            if (cmp != 0) {
                return cmp;
            }
            TStringConstants.truffleSafePointPoll(location, i + 1);
        }
        return 0;
    }

    private static int runMemCmpBytes(Node location, Object arrayA, long offsetA, int strideA, boolean isNativeA, Object arrayB, long offsetB, int strideB, boolean isNativeB, int lengthCMP) {
        assert (strideA >= strideB);
        for (int i = 0; i < lengthCMP; ++i) {
            int valueA = TStringOps.readValue(arrayA, offsetA, strideA, i, isNativeA);
            int valueB = TStringOps.readValue(arrayB, offsetB, strideB, i, isNativeB);
            for (int j = 0; j < 4; ++j) {
                int cmp;
                if (TStringGuards.littleEndian()) {
                    cmp = (valueA & 0xFF) - (valueB & 0xFF);
                    valueA >>= 8;
                    valueB >>= 8;
                } else {
                    cmp = (valueA >> 24 & 0xFF) - (valueB >> 24 & 0xFF);
                    valueA <<= 8;
                    valueB <<= 8;
                }
                if (cmp == 0) continue;
                return cmp;
            }
            TStringConstants.truffleSafePointPoll(location, i + 1);
        }
        return 0;
    }

    private static int runHashCode(Node location, Object array, long offset, int length, int stride, boolean isNative) {
        int hash = 0;
        for (int i = 0; i < length; ++i) {
            hash = 31 * hash + TStringOps.readValue(array, offset, stride, i, isNative);
            TStringConstants.truffleSafePointPoll(location, i + 1);
        }
        return hash;
    }

    private static void runArrayCopy(Node location, Object stubArrayA, long stubOffsetA, boolean isNativeA, Object stubArrayB, long stubOffsetB, boolean isNativeB, int lengthCPY, int stubStride) {
        int strideA = TStringOps.stubStrideToStrideA(stubStride);
        int strideB = TStringOps.stubStrideToStrideB(stubStride);
        for (int i = 0; i < lengthCPY; ++i) {
            TStringOps.writeValue(stubArrayB, stubOffsetB, strideB, i, isNativeB, TStringOps.readValue(stubArrayA, stubOffsetA, strideA, i, isNativeA));
            TStringConstants.truffleSafePointPoll(location, i + 1);
        }
    }

    private static int runCalcStringAttributesLatin1(Node location, Object array, long offset, int length, boolean isNative) {
        for (int i = 0; i < length; ++i) {
            if (TStringOps.readValueS0(array, offset, i, isNative) > 127) {
                return TSCodeRange.get8Bit();
            }
            TStringConstants.truffleSafePointPoll(location, i + 1);
        }
        return TSCodeRange.get7Bit();
    }

    private static int runCalcStringAttributesBMP(Node location, Object array, long offset, int length, boolean isNative) {
        int i;
        int codeRange = TSCodeRange.get7Bit();
        for (i = 0; i < length; ++i) {
            if (TStringOps.readValueS1(array, offset, i, isNative) > 127) {
                codeRange = TSCodeRange.get8Bit();
                break;
            }
            TStringConstants.truffleSafePointPoll(location, i + 1);
        }
        if (!TSCodeRange.is8Bit(codeRange)) {
            return TSCodeRange.get7Bit();
        }
        while (i < length) {
            if (TStringOps.readValueS1(array, offset, i, isNative) > 255) {
                return TSCodeRange.get16Bit();
            }
            TStringConstants.truffleSafePointPoll(location, i + 1);
            ++i;
        }
        return TSCodeRange.get8Bit();
    }

    private static int runCalcStringAttributesUTF32I(Node location, int[] array, long offset, int length) {
        return TStringOps.runCalcStringAttributesUTF32(location, array, offset, length, false);
    }

    private static int runCalcStringAttributesUTF32(Node location, Object array, long offset, int length, boolean isNative) {
        int value2;
        int i;
        int codeRange = TSCodeRange.get7Bit();
        for (i = 0; i < length; ++i) {
            if (Integer.toUnsignedLong(TStringOps.readValueS2(array, offset, i, isNative)) > 127L) {
                codeRange = TSCodeRange.get8Bit();
                break;
            }
            TStringConstants.truffleSafePointPoll(location, i + 1);
        }
        if (!TSCodeRange.is8Bit(codeRange)) {
            return TSCodeRange.get7Bit();
        }
        while (i < length) {
            if (Integer.toUnsignedLong(TStringOps.readValueS2(array, offset, i, isNative)) > 255L) {
                codeRange = TSCodeRange.get16Bit();
                break;
            }
            TStringConstants.truffleSafePointPoll(location, i + 1);
            ++i;
        }
        if (!TSCodeRange.is16Bit(codeRange)) {
            return TSCodeRange.get8Bit();
        }
        while (i < length) {
            value2 = TStringOps.readValueS2(array, offset, i, isNative);
            if (Integer.toUnsignedLong(value2) > 65535L) {
                codeRange = TSCodeRange.getValidFixedWidth();
                break;
            }
            if (Encodings.isUTF16Surrogate(value2)) {
                return TSCodeRange.getBrokenFixedWidth();
            }
            TStringConstants.truffleSafePointPoll(location, i + 1);
            ++i;
        }
        if (!TSCodeRange.isValidFixedWidth(codeRange)) {
            return TSCodeRange.get16Bit();
        }
        while (i < length) {
            value2 = TStringOps.readValueS2(array, offset, i, isNative);
            if (Integer.toUnsignedLong(value2) > 0x10FFFFL || Encodings.isUTF16Surrogate(value2)) {
                return TSCodeRange.getBrokenFixedWidth();
            }
            TStringConstants.truffleSafePointPoll(location, i + 1);
            ++i;
        }
        return TSCodeRange.getValidFixedWidth();
    }

    private static long runCalcStringAttributesUTF8(Node location, Object array, long offset, int length, boolean isNative, boolean assumeValid) {
        int i;
        int codeRange = TSCodeRange.get7Bit();
        for (i = 0; i < length; ++i) {
            if (TStringOps.readValueS0(array, offset, i, isNative) > 127) {
                codeRange = TSCodeRange.getValidMultiByte();
                break;
            }
            TStringConstants.truffleSafePointPoll(location, i + 1);
        }
        if (!TSCodeRange.isValidMultiByte(codeRange)) {
            return StringAttributes.create(length, TSCodeRange.get7Bit());
        }
        int nCodePoints = i;
        if (assumeValid) {
            while (i < length) {
                if (!Encodings.isUTF8ContinuationByte(TStringOps.readValueS0(array, offset, i, isNative))) {
                    ++nCodePoints;
                }
                TStringConstants.truffleSafePointPoll(location, i + 1);
                ++i;
            }
            return StringAttributes.create(nCodePoints, TSCodeRange.getValidMultiByte());
        }
        int state = 0;
        while (i < length) {
            int b = TStringOps.readValueS0(array, offset, i, isNative);
            if (!Encodings.isUTF8ContinuationByte(b)) {
                ++nCodePoints;
            }
            byte type = UTF_8_STATE_MACHINE[b];
            state = UTF_8_STATE_MACHINE[256 + state + type];
            TStringConstants.truffleSafePointPoll(location, i + 1);
            ++i;
        }
        if (state != 0) {
            codeRange = TSCodeRange.getBrokenMultiByte();
        }
        return StringAttributes.create(nCodePoints, codeRange);
    }

    private static long runCalcStringAttributesUTF16C(Node location, char[] array, long offset, int length) {
        return TStringOps.runCalcStringAttributesUTF16(location, array, offset, length, false, false);
    }

    private static long runCalcStringAttributesUTF16(Node location, Object array, long offset, int length, boolean isNative, boolean assumeValid) {
        int i;
        int codeRange = TSCodeRange.get7Bit();
        for (i = 0; i < length; ++i) {
            if (TStringOps.readValueS1(array, offset, i, isNative) > 127) {
                codeRange = TSCodeRange.get8Bit();
                break;
            }
            TStringConstants.truffleSafePointPoll(location, i + 1);
        }
        if (!TSCodeRange.is8Bit(codeRange)) {
            return StringAttributes.create(length, TSCodeRange.get7Bit());
        }
        while (i < length) {
            if (TStringOps.readValueS1(array, offset, i, isNative) > 255) {
                codeRange = TSCodeRange.get16Bit();
                break;
            }
            TStringConstants.truffleSafePointPoll(location, i + 1);
            ++i;
        }
        if (!TSCodeRange.is16Bit(codeRange)) {
            return StringAttributes.create(length, TSCodeRange.get8Bit());
        }
        while (i < length) {
            char c = (char)TStringOps.readValueS1(array, offset, i, isNative);
            if (assumeValid ? Encodings.isUTF16HighSurrogate(c) : Encodings.isUTF16Surrogate(c)) {
                codeRange = TSCodeRange.getValidMultiByte();
                break;
            }
            TStringConstants.truffleSafePointPoll(location, i + 1);
            ++i;
        }
        if (!TSCodeRange.isValidMultiByte(codeRange)) {
            return StringAttributes.create(length, TSCodeRange.get16Bit());
        }
        int nCodePoints = length;
        if (assumeValid) {
            while (i < length) {
                if (Encodings.isUTF16HighSurrogate(TStringOps.readValueS1(array, offset, i, isNative))) {
                    --nCodePoints;
                }
                TStringConstants.truffleSafePointPoll(location, i + 1);
                ++i;
            }
            return StringAttributes.create(nCodePoints, TSCodeRange.getValidMultiByte());
        }
        while (i < length) {
            char c = (char)TStringOps.readValueS1(array, offset, i, isNative);
            if (Encodings.isUTF16Surrogate(c)) {
                if (Encodings.isUTF16LowSurrogate(c) || i + 1 >= length || !Encodings.isUTF16LowSurrogate(TStringOps.readValueS1(array, offset, i + 1, isNative))) {
                    codeRange = TSCodeRange.getBrokenMultiByte();
                } else {
                    ++i;
                    --nCodePoints;
                }
            }
            TStringConstants.truffleSafePointPoll(location, i + 1);
            ++i;
        }
        return StringAttributes.create(nCodePoints, codeRange);
    }

    static long byteLength(Object array) {
        CompilerAsserts.neverPartOfCompilation();
        if (array instanceof byte[]) {
            return ((byte[])array).length;
        }
        throw CompilerDirectives.shouldNotReachHere();
    }

    private static boolean rangeInBounds(int rangeStart, int rangeLength, int arrayLength) {
        return Integer.toUnsignedLong(rangeStart + rangeLength) <= (long)arrayLength;
    }

    private static boolean isNativePointer(Object arrayB) {
        return arrayB instanceof AbstractTruffleString.NativePointer;
    }

    private static long nativePointer(Object array) {
        return ((AbstractTruffleString.NativePointer)array).pointer;
    }

    private static Object stubArray(Object arrayA, boolean isNativeA) {
        return isNativeA ? null : arrayA;
    }

    private static long stubOffset(Object arrayA, int offsetA, boolean isNativeA) {
        assert (isNativeA || arrayA instanceof byte[]);
        return (long)offsetA + (isNativeA ? TStringOps.nativePointer(arrayA) : (long)Unsafe.ARRAY_BYTE_BASE_OFFSET);
    }

    private static long stubOffset(Object arrayA, int offsetA, int strideA, long fromIndexA, boolean isNativeA) {
        return TStringOps.stubOffset(arrayA, offsetA, isNativeA) + (fromIndexA << strideA);
    }

    private static int stubStride(int strideA, int strideB) {
        assert (Stride.isStride(strideA));
        assert (Stride.isStride(strideB));
        return strideA * 3 + strideB;
    }

    private static int stubStrideToStrideA(int stubStride) {
        assert (0 <= stubStride && stubStride < 9) : stubStride;
        return stubStride / 3;
    }

    private static int stubStrideToStrideB(int stubStride) {
        assert (0 <= stubStride && stubStride < 9) : stubStride;
        return stubStride % 3;
    }

    private static int uInt(byte value2) {
        return Byte.toUnsignedInt(value2);
    }

    static void validateRegion(byte[] array, int offset, int length, int stride) {
        if (Integer.toUnsignedLong(offset) + (Integer.toUnsignedLong(length) << stride) > (long)array.length) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw CompilerDirectives.shouldNotReachHere();
        }
    }

    private static void validateRegion(char[] array, int offset, int length) {
        int charOffset = offset >> 1;
        if (Integer.toUnsignedLong(charOffset) + Integer.toUnsignedLong(length) > (long)array.length) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw CompilerDirectives.shouldNotReachHere();
        }
    }

    private static void validateRegion(int[] array, int offset, int length) {
        int intOffset = offset >> 2;
        if (Integer.toUnsignedLong(intOffset) + Integer.toUnsignedLong(length) > (long)array.length) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw CompilerDirectives.shouldNotReachHere();
        }
    }

    private static void validateRegion(Object stubArray, int offset, int length, int stride, boolean isNative) {
        if (TStringOps.invalidOffsetOrLength(stubArray, offset, length, stride, isNative)) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw CompilerDirectives.shouldNotReachHere();
        }
    }

    private static void validateRegionIndex(Object stubArray, int offset, int length, int stride, int i, boolean isNative) {
        if (TStringOps.invalidOffsetOrLength(stubArray, offset, length, stride, isNative) || TStringOps.invalidIndex(length, i)) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw CompilerDirectives.shouldNotReachHere();
        }
    }

    private static boolean invalidOffsetOrLength(Object stubArray, int offset, int length, int stride, boolean isNative) {
        if (isNative) {
            return stubArray != null || offset < 0 || length < 0;
        }
        return Integer.toUnsignedLong(offset) + (Integer.toUnsignedLong(length) << stride) > (long)((byte[])stubArray).length;
    }

    private static boolean invalidIndex(int length, int i) {
        return Integer.compareUnsigned(i, length) >= 0;
    }
}

