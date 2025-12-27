package com.oracle.truffle.regex.tregex.matchers;

public final class AnyMatcher extends CharMatcher {
   private static final AnyMatcher INSTANCE = new AnyMatcher();

   public static CharMatcher create() {
      return INSTANCE;
   }

   public static CharMatcher create(boolean invert) {
      return (CharMatcher)(invert ? EmptyMatcher.create() : create());
   }

   @Override
   public boolean match(int c) {
      return true;
   }

   @Override
   public int estimatedCost() {
      return 0;
   }

   @Override
   public String toString() {
      return "any";
   }
}
