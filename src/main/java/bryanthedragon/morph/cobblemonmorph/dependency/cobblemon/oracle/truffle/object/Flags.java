package com.oracle.truffle.object;

public final class Flags {
   static final long DEFAULT = 0L;
   static final long IMPLICIT_CAST_INT_TO_LONG = 4294967296L;
   static final long IMPLICIT_CAST_INT_TO_DOUBLE = 8589934592L;
   static final long SET_EXISTING = 17179869184L;
   static final long UPDATE_FLAGS = 34359738368L;
   static final long CONST = 68719476736L;
   static final long DECLARE = 137438953472L;
   static final long SEPARATE_SHAPE = 274877906944L;
   static final long PROPERTY_FLAGS_MASK = 4294967295L;

   private Flags() {
   }

   private static boolean getFlag(long flags, long flagBit) {
      return (flags & flagBit) != 0L;
   }

   public static boolean isImplicitCastIntToLong(long flags) {
      return getFlag(flags, 4294967296L);
   }

   public static boolean isImplicitCastIntToDouble(long flags) {
      return getFlag(flags, 8589934592L);
   }

   public static boolean isSetExisting(long flags) {
      return getFlag(flags, 17179869184L);
   }

   public static boolean isUpdateFlags(long flags) {
      return getFlag(flags, 34359738368L);
   }

   public static boolean isConstant(long flags) {
      return getFlag(flags, 68719476736L);
   }

   public static boolean isDeclaration(long flags) {
      return getFlag(flags, 137438953472L);
   }

   public static boolean isSeparateShape(long flags) {
      return getFlag(flags, 274877906944L);
   }

   public static int getPropertyFlags(long putFlags) {
      return (int)(putFlags & 4294967295L);
   }

   public static long propertyFlagsToPutFlags(int propertyFlags) {
      return propertyFlags & 4294967295L;
   }
}
