
package com.oracle.truffle.regex.tregex.matchers;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.matchers.InvertibleCharMatcher;
import com.oracle.truffle.regex.tregex.matchers.NullHighByteBitSetMatcher;
import com.oracle.truffle.regex.tregex.util.DebugUtil;
import com.oracle.truffle.regex.util.BitSets;

public final class BitSetMatcher
extends InvertibleCharMatcher {
    private final int highByte;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final long[] bitSet;

    BitSetMatcher(boolean invert, int highByte, long[] bitSet) {
        super(invert);
        assert (highByte != 0) : "use NullHighByteBitSetMatcher instead!";
        this.highByte = highByte;
        this.bitSet = bitSet;
    }

    public static InvertibleCharMatcher create(boolean invert, int highByte, long[] bitSet) {
        if (highByte == 0) {
            return NullHighByteBitSetMatcher.create(invert, bitSet);
        }
        return new BitSetMatcher(invert, highByte, bitSet);
    }

    public long[] getBitSet() {
        return this.bitSet;
    }

    @Override
    public boolean match(int c) {
        return this.result(BitSetMatcher.highByte(c) == this.highByte && BitSets.get(this.bitSet, BitSetMatcher.lowByte(c)));
    }

    @Override
    public int estimatedCost() {
        return 5;
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        return this.modifiersToString() + "{hi " + DebugUtil.charToString(this.highByte) + " lo " + BitSets.toString(this.bitSet) + "}";
    }
}

