package com.oracle.truffle.regex.tregex.matchers;

public final class EmptyMatcher extends CharMatcher {
   private static final EmptyMatcher INSTANCE = new EmptyMatcher();

   public static EmptyMatcher create() {
      return INSTANCE;
   }

   public static CharMatcher create(boolean invert) {
      return (CharMatcher)(invert ? AnyMatcher.create() : create());
   }

   @Override
   public boolean match(int c) {
      return false;
   }

   @Override
   public int estimatedCost() {
      return 0;
   }

   @Override
   public String toString() {
      return "empty";
   }
}
