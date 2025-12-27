package com.oracle.truffle.api.impl;

public abstract class AbstractFastThreadLocal {
   protected AbstractFastThreadLocal() {
   }

   public abstract void set(Object[] data);

   public abstract <C> Object[] get();

   public abstract <C> C fastGet(int index, Class<C> castType, boolean invalidateOnNull);
}
