
package com.oracle.truffle.regex.tregex.matchers;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.regex.charset.CharMatchers;
import com.oracle.truffle.regex.tregex.matchers.InvertibleCharMatcher;

public final class RangeListMatcher
extends InvertibleCharMatcher {
    public static final int MAX_NUMBER_OF_RANGES = 6;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final int[] ranges;

    RangeListMatcher(boolean invert, int[] ranges) {
        super(invert);
        this.ranges = ranges;
        assert (ranges.length <= 12) : "this matcher should only be used for short lists, to keep code size under control";
    }

    public static RangeListMatcher create(boolean invert, int[] ranges) {
        return new RangeListMatcher(invert, ranges);
    }

    @Override
    @ExplodeLoop(kind=ExplodeLoop.LoopExplosionKind.FULL_UNROLL)
    public boolean match(int c) {
        for (int i = 0; i < this.ranges.length; i += 2) {
            int lo = this.ranges[i];
            int hi = this.ranges[i + 1];
            if (RangeListMatcher.isSingleChar(lo, hi)) {
                if (lo != c) continue;
                return this.result(true);
            }
            if (RangeListMatcher.isTwoChars(lo, hi)) {
                if (c != lo && c != hi) continue;
                return this.result(true);
            }
            if (lo <= c) {
                if (hi < c) continue;
                return this.result(true);
            }
            return this.result(false);
        }
        return this.result(false);
    }

    private static boolean isSingleChar(int lo, int hi) {
        CompilerAsserts.partialEvaluationConstant(lo);
        CompilerAsserts.partialEvaluationConstant(hi);
        return lo == hi;
    }

    private static boolean isTwoChars(int lo, int hi) {
        CompilerAsserts.partialEvaluationConstant(lo);
        CompilerAsserts.partialEvaluationConstant(hi);
        return lo + 1 == hi;
    }

    @Override
    public int estimatedCost() {
        return this.ranges.length;
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        return "list " + this.modifiersToString() + "[" + CharMatchers.rangesToString(this.ranges) + "]";
    }
}

