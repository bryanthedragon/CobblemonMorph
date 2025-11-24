
package com.oracle.truffle.regex.tregex.matchers;

public abstract class CharMatcher {
    public abstract boolean match(int var1);

    public abstract int estimatedCost();
}

