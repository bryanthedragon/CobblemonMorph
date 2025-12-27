package com.oracle.truffle.api.profiles;

import com.oracle.truffle.api.CompilerDirectives;

public final class DoubleValueProfile extends Profile {
   private static final DoubleValueProfile DISABLED;
   private static final byte UNINITIALIZED = 0;
   private static final byte SPECIALIZED = 1;
   private static final byte GENERIC = 2;
   @CompilerDirectives.CompilationFinal
   private double cachedValue;
   @CompilerDirectives.CompilationFinal
   private long cachedRawValue;
   @CompilerDirectives.CompilationFinal
   private byte state = 0;

   DoubleValueProfile() {
   }

   public double profile(double value) {
      byte localState = this.state;
      if (localState != 2) {
         if (localState == 1 && this.cachedRawValue == Double.doubleToRawLongBits(value)) {
            return this.cachedValue;
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         if (localState == 0) {
            this.cachedValue = value;
            this.cachedRawValue = Double.doubleToRawLongBits(value);
            this.state = 1;
         } else {
            this.state = 2;
         }
      }

      return value;
   }

   @Override
   public void disable() {
      this.state = 2;
   }

   @Override
   public void reset() {
      if (this != DISABLED) {
         this.state = 0;
      }
   }

   boolean isGeneric() {
      return this.state == 2;
   }

   boolean isUninitialized() {
      return this.state == 0;
   }

   double getCachedValue() {
      return this.cachedValue;
   }

   @Override
   public String toString() {
      return this == DISABLED
         ? this.toStringDisabled(DoubleValueProfile.class)
         : this.toString(
            DoubleValueProfile.class, this.state == 0, this.state == 2, String.format("value == (double)%s (raw %h)", this.cachedValue, this.cachedRawValue)
         );
   }

   public static DoubleValueProfile createRawIdentityProfile() {
      return create();
   }

   public static DoubleValueProfile create() {
      return Profile.isProfilingEnabled() ? new DoubleValueProfile() : DISABLED;
   }

   public static DoubleValueProfile getUncached() {
      return DISABLED;
   }

   static {
      DoubleValueProfile profile = new DoubleValueProfile();
      profile.disable();
      DISABLED = profile;
   }
}
