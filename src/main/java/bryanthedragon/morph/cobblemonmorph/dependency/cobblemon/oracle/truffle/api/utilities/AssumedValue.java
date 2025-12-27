package com.oracle.truffle.api.utilities;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class AssumedValue<T> {
   private final String name;
   @CompilerDirectives.CompilationFinal
   private T value;
   @CompilerDirectives.CompilationFinal
   private volatile Assumption assumption;
   private static final AtomicReferenceFieldUpdater<AssumedValue, Assumption> ASSUMPTION_UPDATER = AtomicReferenceFieldUpdater.newUpdater(
      AssumedValue.class, Assumption.class, "assumption"
   );

   public AssumedValue(T initialValue) {
      this(null, initialValue);
   }

   @CompilerDirectives.TruffleBoundary
   public AssumedValue(String name, T initialValue) {
      this.name = name;
      this.value = initialValue;
      this.assumption = Truffle.getRuntime().createAssumption(name);
   }

   public T get() {
      if (CompilerDirectives.isPartialEvaluationConstant(this.assumption)) {
         CompilerAsserts.partialEvaluationConstant(this.value);
         if (!this.assumption.isValid()) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
         }
      }

      return this.value;
   }

   @CompilerDirectives.TruffleBoundary
   public void set(T newValue) {
      this.value = newValue;
      Assumption newAssumption = Truffle.getRuntime().createAssumption(this.name);
      Assumption oldAssumption = ASSUMPTION_UPDATER.getAndSet(this, newAssumption);
      oldAssumption.invalidate();
   }
}
