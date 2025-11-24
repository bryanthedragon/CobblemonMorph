
package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.AbstractTruffleString;
import com.oracle.truffle.api.strings.TStringConstants;
import com.oracle.truffle.api.strings.TStringGuards;
import com.oracle.truffle.api.strings.TStringOps;

final class TStringOpsNodes {
    static final String LIMIT_STRIDE = "9";

    TStringOpsNodes() {
    }

    static int memcmp(Node location, AbstractTruffleString a, Object arrayA, AbstractTruffleString b, Object arrayB) {
        int cmp = TStringOps.memcmpWithStride(location, a, arrayA, a.stride(), b, arrayB, b.stride(), Math.min(a.length(), b.length()));
        return TStringOpsNodes.memCmpTail(cmp, a.length(), b.length());
    }

    static int memcmpBytes(Node location, AbstractTruffleString a, Object arrayA, AbstractTruffleString b, Object arrayB) {
        int cmp = TStringOps.memcmpBytesWithStride(location, a, arrayA, a.stride(), b, arrayB, b.stride(), Math.min(a.length(), b.length()));
        return TStringOpsNodes.memCmpTail(cmp, a.length(), b.length());
    }

    static int memCmpTail(int cmp, int lengthA, int lengthB) {
        return cmp == 0 ? lengthA - lengthB : cmp;
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class CalculateHashCodeNode
    extends Node {
        CalculateHashCodeNode() {
        }

        abstract int execute(AbstractTruffleString var1, Object var2);

        @Specialization(guards={"stride(a) == cachedStrideA"}, limit="9")
        int cached(AbstractTruffleString a, Object arrayA, @Cached(value="stride(a)", allowUncached=true) int cachedStrideA) {
            return TStringOps.hashCodeWithStride(this, a, arrayA, cachedStrideA);
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class RawLastIndexOfStringNode
    extends Node {
        RawLastIndexOfStringNode() {
        }

        abstract int execute(AbstractTruffleString var1, Object var2, AbstractTruffleString var3, Object var4, int var5, int var6, byte[] var7);

        @Specialization(guards={"length(b) == 1", "stride(a) == cachedStrideA", "stride(b) == cachedStrideB"}, limit="9")
        int cachedLen1(AbstractTruffleString a, Object arrayA, AbstractTruffleString b, Object arrayB, int fromIndex, int toIndex, byte[] mask, @Cached(value="stride(a)", allowUncached=true) int cachedStrideA, @Cached(value="stride(b)", allowUncached=true) int cachedStrideB) {
            int b0 = TStringOps.readValue(b, arrayB, cachedStrideB, 0);
            int mask0 = mask == null ? 0 : TStringOps.readFromByteArray(mask, cachedStrideB, 0);
            return TStringOps.lastIndexOfCodePointWithOrMaskWithStride(this, a, arrayA, cachedStrideA, fromIndex, toIndex, b0, mask0);
        }

        @Specialization(guards={"length(b) > 1", "stride(a) == cachedStrideA", "stride(b) == cachedStrideB"}, limit="9")
        int cached(AbstractTruffleString a, Object arrayA, AbstractTruffleString b, Object arrayB, int fromIndex, int toIndex, byte[] mask, @Cached(value="stride(a)", allowUncached=true) int cachedStrideA, @Cached(value="stride(b)", allowUncached=true) int cachedStrideB) {
            return TStringOps.lastIndexOfStringWithOrMaskWithStride(this, a, arrayA, cachedStrideA, b, arrayB, cachedStrideB, fromIndex, toIndex, mask);
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class RawIndexOfStringNode
    extends Node {
        RawIndexOfStringNode() {
        }

        abstract int execute(AbstractTruffleString var1, Object var2, AbstractTruffleString var3, Object var4, int var5, int var6, byte[] var7);

        @Specialization(guards={"length(b) == 1", "stride(a) == cachedStrideA", "stride(b) == cachedStrideB"}, limit="9")
        int cachedLen1(AbstractTruffleString a, Object arrayA, AbstractTruffleString b, Object arrayB, int fromIndex, int toIndex, byte[] mask, @Cached(value="stride(a)", allowUncached=true) int cachedStrideA, @Cached(value="stride(b)", allowUncached=true) int cachedStrideB) {
            int b0 = TStringOps.readValue(b, arrayB, cachedStrideB, 0);
            int mask0 = mask == null ? 0 : TStringOps.readFromByteArray(mask, cachedStrideB, 0);
            return TStringOps.indexOfCodePointWithOrMaskWithStride(this, a, arrayA, cachedStrideA, fromIndex, toIndex, b0, mask0);
        }

        @Specialization(guards={"length(b) > 1", "stride(a) == cachedStrideA", "stride(b) == cachedStrideB"}, limit="9")
        int cached(AbstractTruffleString a, Object arrayA, AbstractTruffleString b, Object arrayB, int fromIndex, int toIndex, byte[] mask, @Cached(value="stride(a)", allowUncached=true) int cachedStrideA, @Cached(value="stride(b)", allowUncached=true) int cachedStrideB) {
            return TStringOps.indexOfStringWithOrMaskWithStride(this, a, arrayA, cachedStrideA, b, arrayB, cachedStrideB, fromIndex, toIndex, mask);
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class RawLastIndexOfCodePointNode
    extends Node {
        RawLastIndexOfCodePointNode() {
        }

        abstract int execute(AbstractTruffleString var1, Object var2, int var3, int var4, int var5);

        @Specialization(guards={"stride(a) == cachedStrideA"}, limit="9")
        int cached(AbstractTruffleString a, Object arrayA, int codepoint, int fromIndex, int toIndex, @Cached(value="stride(a)", allowUncached=true) int cachedStrideA) {
            return TStringOps.lastIndexOfCodePointWithOrMaskWithStride(this, a, arrayA, cachedStrideA, fromIndex, toIndex, codepoint, 0);
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class RawIndexOfCodePointNode
    extends Node {
        RawIndexOfCodePointNode() {
        }

        abstract int execute(AbstractTruffleString var1, Object var2, int var3, int var4, int var5);

        @Specialization(guards={"stride(a) == cachedStrideA"}, limit="9")
        int cached(AbstractTruffleString a, Object arrayA, int codepoint, int fromIndex, int toIndex, @Cached(value="stride(a)", allowUncached=true) int cachedStrideA) {
            return TStringOps.indexOfCodePointWithStride(this, a, arrayA, cachedStrideA, fromIndex, toIndex, codepoint);
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class IndexOfAnyIntNode
    extends Node {
        IndexOfAnyIntNode() {
        }

        abstract int execute(AbstractTruffleString var1, Object var2, int var3, int var4, int[] var5);

        @Specialization(guards={"isStride0(a)", "values.length == 1"})
        int stride0(AbstractTruffleString a, Object arrayA, int fromIndex, int maxIndex, int[] values) {
            return TStringOps.indexOfAnyInt(this, a, arrayA, 0, fromIndex, maxIndex, values);
        }

        @Specialization(guards={"isStride0(a)", "values.length > 1"})
        int stride0MultiValue(AbstractTruffleString a, Object arrayA, int fromIndex, int maxIndex, int[] values) {
            int[] stride0Values;
            int n = 0;
            for (int i = 0; i < values.length; ++i) {
                if (values[i] <= 255) {
                    ++n;
                }
                TStringConstants.truffleSafePointPoll(this, i + 1);
            }
            if (n != values.length) {
                stride0Values = new int[n];
                n = 0;
                for (int i = 0; i < values.length; ++i) {
                    if (values[i] <= 255) {
                        stride0Values[n++] = values[i];
                    }
                    TStringConstants.truffleSafePointPoll(this, i + 1);
                }
            } else {
                stride0Values = values;
            }
            return TStringOps.indexOfAnyInt(this, a, arrayA, 0, fromIndex, maxIndex, stride0Values);
        }

        @Specialization(guards={"isStride1(a)", "values.length == 1"})
        int stride1(AbstractTruffleString a, Object arrayA, int fromIndex, int maxIndex, int[] values) {
            return TStringOps.indexOfAnyInt(this, a, arrayA, 1, fromIndex, maxIndex, values);
        }

        @Specialization(guards={"isStride1(a)", "values.length > 1"})
        int stride1MultiValue(AbstractTruffleString a, Object arrayA, int fromIndex, int maxIndex, int[] values) {
            int[] stride1Values;
            int n = 0;
            for (int i = 0; i < values.length; ++i) {
                if (values[i] <= 65535) {
                    ++n;
                }
                TStringConstants.truffleSafePointPoll(this, i + 1);
            }
            if (n != values.length) {
                stride1Values = new int[n];
                n = 0;
                for (int i = 0; i < values.length; ++i) {
                    if (values[i] <= 65535) {
                        stride1Values[n++] = values[i];
                    }
                    TStringConstants.truffleSafePointPoll(this, i + 1);
                }
            } else {
                stride1Values = values;
            }
            return TStringOps.indexOfAnyInt(this, a, arrayA, 1, fromIndex, maxIndex, stride1Values);
        }

        @Specialization(guards={"isStride2(a)"})
        int stride2(AbstractTruffleString a, Object arrayA, int fromIndex, int maxIndex, int[] values) {
            return TStringOps.indexOfAnyInt(this, a, arrayA, 2, fromIndex, maxIndex, values);
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class IndexOfAnyCharNode
    extends Node {
        IndexOfAnyCharNode() {
        }

        abstract int execute(AbstractTruffleString var1, Object var2, int var3, int var4, char[] var5);

        @Specialization(guards={"isStride0(a)", "values.length == 1"})
        int stride0(AbstractTruffleString a, Object arrayA, int fromIndex, int maxIndex, char[] values) {
            return TStringOps.indexOfAnyChar(this, a, arrayA, 0, fromIndex, maxIndex, values);
        }

        @Specialization(guards={"isStride0(a)", "values.length > 1"})
        int stride0MultiValue(AbstractTruffleString a, Object arrayA, int fromIndex, int maxIndex, char[] values) {
            char[] stride0Values;
            int n = 0;
            for (int i = 0; i < values.length; ++i) {
                if (values[i] <= '\u00ff') {
                    ++n;
                }
                TStringConstants.truffleSafePointPoll(this, i + 1);
            }
            if (n != values.length) {
                stride0Values = new char[n];
                n = 0;
                for (int i = 0; i < values.length; ++i) {
                    if (values[i] <= '\u00ff') {
                        stride0Values[n++] = values[i];
                    }
                    TStringConstants.truffleSafePointPoll(this, i + 1);
                }
            } else {
                stride0Values = values;
            }
            return TStringOps.indexOfAnyChar(this, a, arrayA, 0, fromIndex, maxIndex, stride0Values);
        }

        @Specialization(guards={"isStride1(a)"})
        int stride1(AbstractTruffleString a, Object arrayA, int fromIndex, int maxIndex, char[] values) {
            return TStringOps.indexOfAnyChar(this, a, arrayA, 1, fromIndex, maxIndex, values);
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class RawReadValueNode
    extends Node {
        RawReadValueNode() {
        }

        abstract int execute(AbstractTruffleString var1, Object var2, int var3);

        @Specialization(guards={"stride(a) == cachedStrideA"}, limit="9")
        static int cached(AbstractTruffleString a, Object arrayA, int i, @Cached(value="stride(a)", allowUncached=true) int cachedStrideA) {
            return TStringOps.readValue(a, arrayA, cachedStrideA, i);
        }
    }
}

