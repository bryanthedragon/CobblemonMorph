package org.graalvm.word;

public abstract class LocationIdentity {
   public static final LocationIdentity ANY_LOCATION = new LocationIdentity.AnyLocationIdentity();
   public static final LocationIdentity INIT_LOCATION = new LocationIdentity.InitLocationIdentity();

   protected LocationIdentity() {
   }

   public static LocationIdentity any() {
      return ANY_LOCATION;
   }

   public static LocationIdentity init() {
      return INIT_LOCATION;
   }

   public abstract boolean isImmutable();

   public final boolean isMutable() {
      return !this.isImmutable();
   }

   public final boolean isAny() {
      return this == ANY_LOCATION;
   }

   public final boolean isInit() {
      return this == INIT_LOCATION;
   }

   public final boolean isSingle() {
      return this != ANY_LOCATION;
   }

   public final boolean overlaps(LocationIdentity other) {
      if (other == this) {
         return true;
      } else {
         return !other.isImmutable() && !this.isImmutable() ? this.isAny() || other.isAny() || this.equals(other) : false;
      }
   }

   private static final class AnyLocationIdentity extends LocationIdentity {
      @Override
      public boolean isImmutable() {
         return false;
      }

      @Override
      public String toString() {
         return "ANY_LOCATION";
      }
   }

   private static final class InitLocationIdentity extends LocationIdentity {
      @Override
      public boolean isImmutable() {
         return true;
      }

      @Override
      public String toString() {
         return "INIT_LOCATION";
      }
   }
}
