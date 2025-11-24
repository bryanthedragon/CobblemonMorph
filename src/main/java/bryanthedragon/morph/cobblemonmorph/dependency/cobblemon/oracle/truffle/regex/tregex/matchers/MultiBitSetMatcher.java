
package com.oracle.truffle.regex.tregex.matchers;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.charset.ImmutableSortedListOfIntRanges;
import com.oracle.truffle.regex.charset.Range;
import com.oracle.truffle.regex.tregex.matchers.InvertibleCharMatcher;
import com.oracle.truffle.regex.util.BitSets;
import java.util.Arrays;

public final class MultiBitSetMatcher
extends InvertibleCharMatcher {
    private static final int BYTE_RANGE = 256;
    private static final int BYTE_MAX_VALUE = 255;
    private static final int BYTE_MIN_VALUE = 0;
    private static final long[] MATCH_NONE = new long[]{0L, 0L, 0L, 0L};
    private static final long[] MATCH_ALL = new long[]{-1L, -1L, -1L, -1L};
    @CompilerDirectives.CompilationFinal(dimensions=2)
    private final long[][] bitSets;

    public static MultiBitSetMatcher fromRanges(boolean inverse, ImmutableSortedListOfIntRanges cps) {
        long[][] bitSets = new long[256][];
        Arrays.fill((Object[])bitSets, MATCH_NONE);
        long[] cur = new long[4];
        int curByte = -1;
        for (Range r : cps) {
            if (curByte == -1) {
                curByte = MultiBitSetMatcher.highByte(r.lo);
            }
            if (MultiBitSetMatcher.highByte(r.lo) > curByte) {
                bitSets[curByte] = cur;
                cur = new long[4];
                curByte = MultiBitSetMatcher.highByte(r.lo);
            }
            if (MultiBitSetMatcher.highByte(r.lo) == MultiBitSetMatcher.highByte(r.hi)) {
                BitSets.setRange(cur, MultiBitSetMatcher.lowByte(r.lo), MultiBitSetMatcher.lowByte(r.hi));
                continue;
            }
            BitSets.setRange(cur, MultiBitSetMatcher.lowByte(r.lo), 255);
            bitSets[curByte] = cur;
            for (int j = MultiBitSetMatcher.highByte(r.lo) + 1; j < MultiBitSetMatcher.highByte(r.hi); ++j) {
                bitSets[j] = MATCH_ALL;
            }
            cur = new long[4];
            curByte = MultiBitSetMatcher.highByte(r.hi);
            BitSets.setRange(cur, 0, MultiBitSetMatcher.lowByte(r.hi));
        }
        bitSets[curByte] = cur;
        return new MultiBitSetMatcher(inverse, bitSets);
    }

    MultiBitSetMatcher(boolean invert, long[][] bitSets) {
        super(invert);
        this.bitSets = bitSets;
    }

    @Override
    public boolean match(int c) {
        int highByte = MultiBitSetMatcher.highByte(c);
        return this.result(highByte < this.bitSets.length && BitSets.get(this.bitSets[highByte], MultiBitSetMatcher.lowByte(c)));
    }

    @Override
    public int estimatedCost() {
        return 8;
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        StringBuilder sb = new StringBuilder(this.modifiersToString()).append("[\n");
        for (int i = 0; i < this.bitSets.length; ++i) {
            sb.append(String.format("    %02x: ", i)).append(BitSets.toString(this.bitSets[i])).append("\n");
        }
        return sb.append("  ]").toString();
    }
}

