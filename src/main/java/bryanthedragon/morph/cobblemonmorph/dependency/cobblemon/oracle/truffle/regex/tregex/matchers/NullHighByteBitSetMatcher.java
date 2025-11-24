
package com.oracle.truffle.regex.tregex.matchers;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.matchers.InvertibleCharMatcher;
import com.oracle.truffle.regex.util.BitSets;

public final class NullHighByteBitSetMatcher
extends InvertibleCharMatcher {
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final long[] bitSet;

    NullHighByteBitSetMatcher(boolean inverse, long[] bitSet) {
        super(inverse);
        this.bitSet = bitSet;
    }

    public static NullHighByteBitSetMatcher create(boolean inverse, long[] bitSet) {
        return new NullHighByteBitSetMatcher(inverse, bitSet);
    }

    @Override
    public boolean match(int c) {
        return this.result(BitSets.get(this.bitSet, c));
    }

    @Override
    public int estimatedCost() {
        return 4;
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        return this.modifiersToString() + "{ascii " + BitSets.toString(this.bitSet) + "}";
    }
}

