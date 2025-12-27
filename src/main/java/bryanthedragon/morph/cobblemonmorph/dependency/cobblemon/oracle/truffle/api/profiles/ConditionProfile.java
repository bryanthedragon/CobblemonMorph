package com.oracle.truffle.api.profiles;

import com.oracle.truffle.api.CompilerDirectives;

public abstract class ConditionProfile extends Profile {
   ConditionProfile() {
   }

   public abstract boolean profile(boolean value);

   public static ConditionProfile createCountingProfile() {
      return Profile.isProfilingEnabled() ? ConditionProfile.Counting.createLazyLoadClass() : ConditionProfile.Disabled.INSTANCE;
   }

   public static ConditionProfile createBinaryProfile() {
      return Profile.isProfilingEnabled() ? ConditionProfile.Binary.createLazyLoadClass() : ConditionProfile.Disabled.INSTANCE;
   }

   public static ConditionProfile create() {
      return createBinaryProfile();
   }

   public static ConditionProfile getUncached() {
      return ConditionProfile.Disabled.INSTANCE;
   }

   static final class Binary extends ConditionProfile {
      @CompilerDirectives.CompilationFinal
      private boolean wasTrue;
      @CompilerDirectives.CompilationFinal
      private boolean wasFalse;

      @Override
      public boolean profile(boolean value) {
         if (value) {
            if (!this.wasTrue) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.wasTrue = true;
            }

            return true;
         } else {
            if (!this.wasFalse) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.wasFalse = true;
            }

            return false;
         }
      }

      @Override
      public void disable() {
         this.wasFalse = true;
         this.wasTrue = true;
      }

      @Override
      public void reset() {
         this.wasFalse = false;
         this.wasTrue = false;
      }

      boolean wasTrue() {
         return this.wasTrue;
      }

      boolean wasFalse() {
         return this.wasFalse;
      }

      @Override
      public String toString() {
         return String.format("%s(wasTrue=%s, wasFalse=%s)@%x", this.getClass().getSimpleName(), this.wasTrue, this.wasFalse, this.hashCode());
      }

      static ConditionProfile createLazyLoadClass() {
         return new ConditionProfile.Binary();
      }
   }

   static final class Counting extends ConditionProfile {
      @CompilerDirectives.CompilationFinal
      private int trueCount;
      @CompilerDirectives.CompilationFinal
      private int falseCount;
      public static final int MAX_VALUE = 1073741823;

      @Override
      public boolean profile(boolean value) {
         int t = this.trueCount;
         int f = this.falseCount;
         boolean val = value;
         if (value) {
            if (t == 0) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
            }

            if (f == 0) {
               val = true;
            }

            if (CompilerDirectives.inInterpreter() && t < 1073741823) {
               this.trueCount = t + 1;
            }
         } else {
            if (f == 0) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
            }

            if (t == 0) {
               val = false;
            }

            if (CompilerDirectives.inInterpreter() && f < 1073741823) {
               this.falseCount = f + 1;
            }
         }

         if (CompilerDirectives.inInterpreter()) {
            return val;
         } else {
            int sum = t + f;
            return CompilerDirectives.injectBranchProbability((double)t / sum, val);
         }
      }

      @Override
      public void disable() {
         if (this.trueCount == 0) {
            this.trueCount = 1;
         }

         if (this.falseCount == 0) {
            this.falseCount = 1;
         }
      }

      @Override
      public void reset() {
         this.trueCount = 0;
         this.falseCount = 0;
      }

      int getTrueCount() {
         return this.trueCount;
      }

      int getFalseCount() {
         return this.falseCount;
      }

      @Override
      public String toString() {
         int t = this.trueCount;
         int f = this.falseCount;
         int sum = t + f;
         String details = String.format("trueProbability=%s (trueCount=%s, falseCount=%s)", (double)t / sum, t, f);
         return this.toString(ConditionProfile.class, sum == 0, false, details);
      }

      static ConditionProfile createLazyLoadClass() {
         return new ConditionProfile.Counting();
      }
   }

   static final class Disabled extends ConditionProfile {
      static final ConditionProfile INSTANCE = new ConditionProfile.Disabled();

      @Override
      protected Object clone() {
         return INSTANCE;
      }

      @Override
      public boolean profile(boolean value) {
         return value;
      }

      @Override
      public String toString() {
         return this.toStringDisabled(ConditionProfile.class);
      }
   }
}
