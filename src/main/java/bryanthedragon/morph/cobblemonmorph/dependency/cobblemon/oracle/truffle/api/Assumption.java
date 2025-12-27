package com.oracle.truffle.api;

import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.InvalidAssumptionException;

public interface Assumption {
   Assumption ALWAYS_VALID = createAlwaysValid();
   Assumption NEVER_VALID = createNeverValid();

   private static Assumption createNeverValid() {
      Assumption assumption = create("<never valid>");
      assumption.invalidate();
      return assumption;
   }

   private static Assumption createAlwaysValid() {
      return LanguageAccessor.RUNTIME.createAlwaysValidAssumption();
   }

   void check() throws InvalidAssumptionException;

   boolean isValid();

   void invalidate();

   default void invalidate(String message) {
      this.invalidate();
   }

   String getName();

   static boolean isValidAssumption(Assumption assumption) {
      return assumption != null && assumption.isValid();
   }

   @ExplodeLoop
   static boolean isValidAssumption(Assumption[] assumptions) {
      CompilerAsserts.partialEvaluationConstant(assumptions);
      if (assumptions == null) {
         return false;
      } else {
         for (Assumption assumption : assumptions) {
            if (!isValidAssumption(assumption)) {
               return false;
            }
         }

         return true;
      }
   }

   static Assumption create() {
      return Truffle.getRuntime().createAssumption();
   }

   static Assumption create(String name) {
      return Truffle.getRuntime().createAssumption(name);
   }
}
