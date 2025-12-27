package com.oracle.truffle.api.impl;

import com.oracle.truffle.api.CompilerDirectives;

final class DefaultContextThreadLocal extends AbstractFastThreadLocal {
   static final DefaultContextThreadLocal SINGLETON = new DefaultContextThreadLocal();
   private final ThreadLocal<Object[]> threadLocal = new ThreadLocal<>();

   @CompilerDirectives.TruffleBoundary
   @Override
   public void set(Object[] data) {
      this.threadLocal.set(data);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public <C> Object[] get() {
      return this.threadLocal.get();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public <C> C fastGet(int index, Class<C> castType, boolean invalidateOnNull) {
      Object[] data = this.get();
      if (data == null) {
         return null;
      } else {
         C result = (C)data[index];

         assert castType == null || result == null || result.getClass() == castType : "invalid type";

         return result;
      }
   }
}
