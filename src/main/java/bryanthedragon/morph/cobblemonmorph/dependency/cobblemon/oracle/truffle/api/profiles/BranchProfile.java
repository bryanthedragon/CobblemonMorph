package com.oracle.truffle.api.profiles;

import com.oracle.truffle.api.CompilerDirectives;

public final class BranchProfile extends Profile {
   private static final BranchProfile DISABLED;
   @CompilerDirectives.CompilationFinal
   private boolean visited;

   BranchProfile() {
   }

   public void enter() {
      if (!this.visited) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.visited = true;
      }
   }

   public static BranchProfile create() {
      return Profile.isProfilingEnabled() ? new BranchProfile() : getUncached();
   }

   @Override
   public void disable() {
      this.visited = true;
   }

   @Override
   public void reset() {
      if (this != DISABLED) {
         this.visited = false;
      }
   }

   @Override
   public String toString() {
      return this == DISABLED ? this.toStringDisabled(BranchProfile.class) : this.toString(BranchProfile.class, !this.visited, false, "VISITED");
   }

   public static BranchProfile getUncached() {
      return DISABLED;
   }

   static {
      BranchProfile profile = new BranchProfile();
      profile.disable();
      DISABLED = profile;
   }
}
