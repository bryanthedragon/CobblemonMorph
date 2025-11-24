
package com.oracle.truffle.regex.tregex.matchers;

import com.oracle.truffle.regex.tregex.matchers.CharMatcher;
import com.oracle.truffle.regex.tregex.matchers.EmptyMatcher;

public final class AnyMatcher
extends CharMatcher {
    private static final AnyMatcher INSTANCE = new AnyMatcher();

    public static CharMatcher create() {
        return INSTANCE;
    }

    public static CharMatcher create(boolean invert) {
        return invert ? EmptyMatcher.create() : AnyMatcher.create();
    }

    @Override
    public boolean match(int c) {
        return true;
    }

    @Override
    public int estimatedCost() {
        return 0;
    }

    public String toString() {
        return "any";
    }
}

