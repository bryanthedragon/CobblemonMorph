
package com.oracle.truffle.regex.tregex.matchers;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.matchers.InvertibleCharMatcher;
import com.oracle.truffle.regex.tregex.util.DebugUtil;

public final class TwoCharMatcher
extends InvertibleCharMatcher {
    private final int c1;
    private final int c2;

    TwoCharMatcher(boolean invert, int c1, int c2) {
        super(invert);
        assert (c1 != c2);
        this.c1 = c1;
        this.c2 = c2;
    }

    public static TwoCharMatcher create(boolean invert, int c1, int c2) {
        return new TwoCharMatcher(invert, c1, c2);
    }

    @Override
    public boolean match(int m) {
        return this.result(m == this.c1 || m == this.c2);
    }

    @Override
    public int estimatedCost() {
        return 2;
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        return this.modifiersToString() + DebugUtil.charToString(this.c1) + "||" + DebugUtil.charToString(this.c2);
    }
}

