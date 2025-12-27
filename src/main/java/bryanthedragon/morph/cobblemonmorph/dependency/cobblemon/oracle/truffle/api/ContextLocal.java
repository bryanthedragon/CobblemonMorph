package com.oracle.truffle.api;

public abstract class ContextLocal<T> {
   protected ContextLocal(Object polyglotObject) {
      if (!LanguageAccessor.ENGINE.isPolyglotObject(polyglotObject)) {
         throw new IllegalStateException("No custom subclasses of ContextLocal allowed.");
      }
   }

   public abstract T get();

   public abstract T get(TruffleContext context);
}
