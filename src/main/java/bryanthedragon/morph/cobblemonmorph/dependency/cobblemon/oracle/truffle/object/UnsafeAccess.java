package com.oracle.truffle.object;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

final class UnsafeAccess {
   private static final Unsafe UNSAFE = getUnsafe();
   static final long ARRAY_INT_BASE_OFFSET = UNSAFE.arrayBaseOffset(int[].class);
   static final long ARRAY_INT_INDEX_SCALE = UNSAFE.arrayIndexScale(int[].class);

   static long objectFieldOffset(Field field) {
      return UNSAFE.objectFieldOffset(field);
   }

   static Object unsafeGetObject(Object receiver, long offset) {
      return UNSAFE.getObject(receiver, offset);
   }

   static void unsafePutObject(Object receiver, long offset, Object value) {
      UNSAFE.putObject(receiver, offset, value);
   }

   static long unsafeGetLong(Object receiver, long offset) {
      return UNSAFE.getLong(receiver, offset);
   }

   static void unsafePutLong(Object receiver, long offset, long value) {
      UNSAFE.putLong(receiver, offset, value);
   }

   static long unsafeGetLong(Object receiver, long offset, boolean condition, Object locationIdentity) {
      return UNSAFE.getLong(receiver, offset);
   }

   static void unsafePutLong(Object receiver, long offset, long value, Object locationIdentity) {
      UNSAFE.putLong(receiver, offset, value);
   }

   private UnsafeAccess() {
   }

   private static Unsafe getUnsafe() {
      try {
         return Unsafe.getUnsafe();
      } catch (SecurityException var2) {
         try {
            Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeInstance.setAccessible(true);
            return (Unsafe)theUnsafeInstance.get(Unsafe.class);
         } catch (Exception var1) {
            throw new RuntimeException("exception while trying to get Unsafe.theUnsafe via reflection:", var1);
         }
      }
   }
}
