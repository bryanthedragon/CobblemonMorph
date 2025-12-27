package com.oracle.truffle.api.profiles;

import com.oracle.truffle.api.CompilerDirectives;

public final class LoopConditionProfile extends ConditionProfile {
   private static final LoopConditionProfile DISABLED;
   @CompilerDirectives.CompilationFinal
   private long trueCount;
   @CompilerDirectives.CompilationFinal
   private int falseCount;

   LoopConditionProfile() {
   }

   @Override
   public boolean profile(boolean condition) {
      long trueCountLocal = this.trueCount;
      int falseCountLocal = this.falseCount;
      if (trueCountLocal == 0L && condition) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
      }

      if (CompilerDirectives.inInterpreter()) {
         if (condition) {
            if (trueCountLocal < Long.MAX_VALUE) {
               this.trueCount = trueCountLocal + 1L;
            }
         } else if (falseCountLocal < Integer.MAX_VALUE) {
            this.falseCount = falseCountLocal + 1;
         }

         return condition;
      } else {
         return this != DISABLED ? CompilerDirectives.injectBranchProbability(calculateProbability(trueCountLocal, falseCountLocal), condition) : condition;
      }
   }

   public void profileCounted(long length) {
      if (CompilerDirectives.inInterpreter()) {
         long trueCountLocal = this.trueCount + length;
         if (trueCountLocal >= 0L) {
            this.trueCount = trueCountLocal;
            int falseCountLocal = this.falseCount;
            if (falseCountLocal < Integer.MAX_VALUE) {
               this.falseCount = falseCountLocal + 1;
            }
         }
      }
   }

   public boolean inject(boolean condition) {
      return CompilerDirectives.inCompiledCode() && this != DISABLED
         ? CompilerDirectives.injectBranchProbability(calculateProbability(this.trueCount, this.falseCount), condition)
         : condition;
   }

   private static double calculateProbability(long trueCountLocal, int falseCountLocal) {
      return falseCountLocal == 0 && trueCountLocal == 0L ? 0.0 : (double)trueCountLocal / (trueCountLocal + falseCountLocal);
   }

   @Override
   public void disable() {
      if (this.trueCount == 0L) {
         this.trueCount = 1L;
      }

      if (this.falseCount == 0) {
         this.falseCount = 1;
      }
   }

   @Override
   public void reset() {
      if (this != DISABLED) {
         this.trueCount = 0L;
         this.falseCount = 0;
      }
   }

   long getTrueCount() {
      return this.trueCount;
   }

   int getFalseCount() {
      return this.falseCount;
   }

   @Override
   public String toString() {
      return this == DISABLED
         ? this.toStringDisabled(LoopConditionProfile.class)
         : this.toString(
            LoopConditionProfile.class,
            this.falseCount == 0,
            false,
            String.format(
               "trueProbability=%s (trueCount=%s, falseCount=%s)", calculateProbability(this.trueCount, this.falseCount), this.trueCount, this.falseCount
            )
         );
   }

   public static LoopConditionProfile createCountingProfile() {
      return Profile.isProfilingEnabled() ? new LoopConditionProfile() : DISABLED;
   }

   public static LoopConditionProfile create() {
      return createCountingProfile();
   }

   public static LoopConditionProfile getUncached() {
      return DISABLED;
   }

   static {
      LoopConditionProfile profile = new LoopConditionProfile();
      profile.trueCount = Long.MAX_VALUE;
      profile.falseCount = Integer.MAX_VALUE;
      DISABLED = profile;
   }
}
