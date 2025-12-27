package com.oracle.truffle.api.profiles;

import com.oracle.truffle.api.CompilerDirectives;

public final class ByteValueProfile extends Profile {
   private static final ByteValueProfile DISABLED;
   private static final byte UNINITIALIZED = 0;
   private static final byte SPECIALIZED = 1;
   private static final byte GENERIC = 2;
   @CompilerDirectives.CompilationFinal
   private byte cachedValue;
   @CompilerDirectives.CompilationFinal
   private byte state = 0;

   ByteValueProfile() {
   }

   public byte profile(byte value) {
      byte localState = this.state;
      if (localState != 2) {
         if (localState == 1) {
            byte v = this.cachedValue;
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

   byte getCachedValue() {
      return this.cachedValue;
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

   @Override
   public String toString() {
      return this == DISABLED
         ? this.toStringDisabled(ByteValueProfile.class)
         : this.toString(ByteValueProfile.class, this.state == 0, this.state == 2, String.format("value == (byte)%s", this.cachedValue));
   }

   public static ByteValueProfile createIdentityProfile() {
      return create();
   }

   public static ByteValueProfile create() {
      return Profile.isProfilingEnabled() ? new ByteValueProfile() : DISABLED;
   }

   public static ByteValueProfile getUncached() {
      return DISABLED;
   }

   static {
      ByteValueProfile profile = new ByteValueProfile();
      profile.disable();
      DISABLED = profile;
   }
}
