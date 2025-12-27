package com.oracle.truffle.api.impl;

public abstract class JDKAccessor {
   private JDKAccessor() {
   }

   public static boolean isVirtualThread(Thread t) {
      return false;
   }
}
