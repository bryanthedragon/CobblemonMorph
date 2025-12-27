package com.oracle.truffle.js.runtime.util;

import com.oracle.truffle.api.CompilerDirectives;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public final class LazyValue<T> implements Supplier<T> {
   private final Supplier<T> supplier;
   private final AtomicReference<T> ref;

   public LazyValue(Supplier<T> supplier) {
      this.supplier = supplier;
      this.ref = new AtomicReference<>();
   }

   @Override
   public T get() {
      T value = this.ref.get();
      if (value == null) {
         value = this.init();
      }

      return value;
   }

   @CompilerDirectives.TruffleBoundary
   private T init() {
      T value = this.supplier.get();
      if (!this.ref.compareAndSet(null, value)) {
         value = this.ref.get();
      }

      return value;
   }
}
