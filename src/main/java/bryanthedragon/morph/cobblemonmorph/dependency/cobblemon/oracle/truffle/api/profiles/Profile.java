package com.oracle.truffle.api.profiles;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.nodes.NodeCloneable;

public abstract class Profile extends NodeCloneable {
   static boolean isProfilingEnabled() {
      boolean enabled;
      try {
         enabled = Truffle.getRuntime().isProfilingEnabled();
      } catch (LinkageError var2) {
         enabled = true;
      }

      return enabled;
   }

   Profile() {
   }

   String toStringDisabled(Class<?> profileClass) {
      return String.format("%s(DISABLED)", profileClass.getSimpleName());
   }

   public void disable() {
   }

   public void reset() {
   }

   String toString(Class<?> profileClass, boolean uninitialized, boolean generic, String specialization) {
      String s;
      if (uninitialized) {
         s = "UNINITIALIZED";
      } else if (generic) {
         s = "GENERIC";
      } else {
         s = specialization == null ? "" : specialization;
      }

      return String.format("%s(%s)@%s", profileClass.getSimpleName(), s, Integer.toHexString(this.hashCode()));
   }
}
