package com.oracle.truffle.api;

public abstract class ContextThreadLocal<T> {
   protected ContextThreadLocal(Object polyglotObject) {
      if (!LanguageAccessor.ENGINE.isPolyglotObject(polyglotObject)) {
         throw new IllegalStateException("No custom subclasses of ContextLocal allowed.");
      }
   }

   public abstract T get();

   public abstract T get(Thread t);

   public abstract T get(TruffleContext t);

   public abstract T get(TruffleContext context, Thread t);
}
