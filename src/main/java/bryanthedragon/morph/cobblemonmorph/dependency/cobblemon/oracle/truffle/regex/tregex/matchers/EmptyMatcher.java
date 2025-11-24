
package com.oracle.truffle.regex.tregex.matchers;

import com.oracle.truffle.regex.tregex.matchers.AnyMatcher;
import com.oracle.truffle.regex.tregex.matchers.CharMatcher;

public final class EmptyMatcher
extends CharMatcher {
    private static final EmptyMatcher INSTANCE = new EmptyMatcher();

    public static EmptyMatcher create() {
        return INSTANCE;
    }

    public static CharMatcher create(boolean invert) {
        return invert ? AnyMatcher.create() : EmptyMatcher.create();
    }

    @Override
    public boolean match(int c) {
        return false;
    }

    @Override
    public int estimatedCost() {
        return 0;
    }

    public String toString() {
        return "empty";
    }
}

