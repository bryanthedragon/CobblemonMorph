package com.oracle.truffle.api.profiles;

import com.oracle.truffle.api.CompilerDirectives;

public final class LongValueProfile extends Profile {
   private static final LongValueProfile DISABLED;
   private static final byte UNINITIALIZED = 0;
   private static final byte SPECIALIZED = 1;
   private static final byte GENERIC = 2;
   @CompilerDirectives.CompilationFinal
   private long cachedValue;
   @CompilerDirectives.CompilationFinal
   private byte state = 0;

   LongValueProfile() {
   }

   public long profile(long value) {
      byte localState = this.state;
      if (localState != 2) {
         if (localState == 1) {
            long v = this.cachedValue;
            if (v == value) {
               return v;
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         if (localState == 0) {
            this.cachedValue = value;
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
      if (this != DISABLED) {
         this.state = 0;
      }
   }

   long getCachedValue() {
      return this.cachedValue;
   }

   @Override
   public String toString() {
      return this == DISABLED
         ? this.toStringDisabled(LongValueProfile.class)
         : this.toString(LongValueProfile.class, this.state == 0, this.state == 2, String.format("value == (long)%s", this.cachedValue));
   }

   public static LongValueProfile createIdentityProfile() {
      return create();
   }

   public static LongValueProfile create() {
      return Profile.isProfilingEnabled() ? new LongValueProfile() : DISABLED;
   }

   public static LongValueProfile getUncached() {
      return DISABLED;
   }

   static {
      LongValueProfile profile = new LongValueProfile();
      profile.disable();
      DISABLED = profile;
   }
}
