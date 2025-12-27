package com.oracle.truffle.regex.tregex.matchers;

public abstract class CharMatcher {
   public abstract boolean match(int c);

   public abstract int estimatedCost();
}
