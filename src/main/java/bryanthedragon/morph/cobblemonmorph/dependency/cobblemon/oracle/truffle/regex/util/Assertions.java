package com.oracle.truffle.regex.util;

public interface Assertions {
   static boolean isSorted(int[] array) {
      int prev = Integer.MIN_VALUE;

      for (int i : array) {
         if (prev > i) {
            return false;
         }

         prev = i;
      }

      return true;
   }
}
