package com.oracle.truffle.js.runtime.util;

import com.oracle.truffle.api.CompilerDirectives;
import java.util.Objects;

public final class Pair<T, U> {
   private final T first;
   private final U second;

   public Pair(T first, U second) {
      this.first = first;
      this.second = second;
   }

   public T getFirst() {
      return this.first;
   }

   public U getSecond() {
      return this.second;
   }

   @Override
   public int hashCode() {
      int prime = 31;
      int result = 1;
      result = 31 * result + Objects.hashCode(this.first);
      return 31 * result + Objects.hashCode(this.second);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof Pair)) {
         return false;
      } else {
         Pair<?, ?> other = (Pair<?, ?>)obj;
         return Objects.equals(this.first, other.first) && Objects.equals(this.second, other.second);
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      return "(" + this.first + ", " + this.second + ")";
   }
}
