package com.oracle.truffle.api.profiles;

import com.oracle.truffle.api.CompilerDirectives;

public final class FloatValueProfile extends Profile {
   private static final FloatValueProfile DISABLED;
   private static final byte UNINITIALIZED = 0;
   private static final byte SPECIALIZED = 1;
   private static final byte GENERIC = 2;
   @CompilerDirectives.CompilationFinal
   private float cachedValue;
   @CompilerDirectives.CompilationFinal
   private int cachedRawValue;
   @CompilerDirectives.CompilationFinal
   private byte state = 0;

   FloatValueProfile() {
   }

   public float profile(float value) {
      byte localState = this.state;
      if (localState != 2) {
         if (localState == 1 && this.cachedRawValue == Float.floatToRawIntBits(value)) {
            return this.cachedValue;
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         if (localState == 0) {
            this.cachedValue = value;
            this.cachedRawValue = Float.floatToRawIntBits(value);
            this.state = 1;
         } else {
            this.state = 2;
         }
      }

      return value;
   }

   boolean isGeneric() {
      return this.state == 2;
   }

   boolean isUninitialized() {
      return this.state == 0;
   }

   @Override
   public void disable() {
      this.state = 2;
   }

   @Override
   public void reset() {
      this.state = 0;
   }

   float getCachedValue() {
      return this.cachedValue;
   }

   @Override
   public String toString() {
      return this == DISABLED
         ? this.toStringDisabled(FloatValueProfile.class)
         : this.toString(
            FloatValueProfile.class, this.state == 0, this.state == 2, String.format("value == (float)%s (raw %h)", this.cachedValue, this.cachedRawValue)
         );
   }

   public static FloatValueProfile createRawIdentityProfile() {
      return create();
   }

   public static FloatValueProfile create() {
      return Profile.isProfilingEnabled() ? new FloatValueProfile() : DISABLED;
   }

   public static FloatValueProfile getUncached() {
      return DISABLED;
   }

   static {
      FloatValueProfile profile = new FloatValueProfile();
      profile.disable();
      DISABLED = profile;
   }
}
