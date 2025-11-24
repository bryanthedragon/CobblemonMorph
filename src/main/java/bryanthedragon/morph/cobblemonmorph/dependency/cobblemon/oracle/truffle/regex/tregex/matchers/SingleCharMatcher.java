
package com.oracle.truffle.regex.tregex.matchers;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.matchers.InvertibleCharMatcher;
import com.oracle.truffle.regex.tregex.util.DebugUtil;

public final class SingleCharMatcher
extends InvertibleCharMatcher {
    private final int c;

    SingleCharMatcher(boolean invert, int c) {
        super(invert);
        this.c = c;
    }

    public static SingleCharMatcher create(boolean invert, int c) {
        return new SingleCharMatcher(invert, c);
    }

    public int getChar() {
        return this.c;
    }

    @Override
    public boolean match(int m) {
        return this.result(this.c == m);
    }

    @Override
    public int estimatedCost() {
        return 1;
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        return this.modifiersToString() + DebugUtil.charToString(this.c);
    }
}

