package com.cobblemon.mod.relocations.ibm.icu.impl;

public class Pair<F, S> {
   public final F first;
   public final S second;

   protected Pair(F first, S second) {
      this.first = first;
      this.second = second;
   }

   public static <F, S> Pair<F, S> of(F first, S second) {
      if (first != null && second != null) {
         return new Pair<>(first, second);
      } else {
         throw new IllegalArgumentException("Pair.of requires non null values.");
      }
   }

   @Override
   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof Pair)) {
         return false;
      } else {
         Pair<?, ?> rhs = (Pair<?, ?>)other;
         return this.first.equals(rhs.first) && this.second.equals(rhs.second);
      }
   }

   @Override
   public int hashCode() {
      return this.first.hashCode() * 37 + this.second.hashCode();
   }
}
