package com.oracle.truffle.api.profiles;

import com.oracle.truffle.api.CompilerDirectives;

public final class IntValueProfile extends Profile {
   private static final IntValueProfile DISABLED;
   private static final byte UNINITIALIZED = 0;
   private static final byte SPECIALIZED = 1;
   private static final byte GENERIC = 2;
   @CompilerDirectives.CompilationFinal
   private int cachedValue;
   @CompilerDirectives.CompilationFinal
   private byte state = 0;

   IntValueProfile() {
   }

   public int profile(int value) {
      byte localState = this.state;
      if (localState != 2) {
         if (localState == 1) {
            int v = this.cachedValue;
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

   int getCachedValue() {
      return this.cachedValue;
   }

   @Override
   public void disable() {
      this.state = 2;
   }

   @Override
   public void reset() {
      this.state = 0;
   }

   @Override
   public String toString() {
      return this.toString(IntValueProfile.class, this.isUninitialized(), this.isGeneric(), String.format("value == (int)%s", this.cachedValue));
   }

   public static IntValueProfile createIdentityProfile() {
      return create();
   }

   public static IntValueProfile create() {
      return Profile.isProfilingEnabled() ? new IntValueProfile() : DISABLED;
   }

   public static IntValueProfile getUncached() {
      return DISABLED;
   }

   static {
      IntValueProfile profile = new IntValueProfile();
      profile.disable();
      DISABLED = profile;
   }
}
