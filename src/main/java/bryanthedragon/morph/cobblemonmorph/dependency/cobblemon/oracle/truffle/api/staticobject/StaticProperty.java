package com.oracle.truffle.api.staticobject;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import java.lang.reflect.Field;
import java.nio.ByteOrder;
import sun.misc.Unsafe;

public abstract class StaticProperty {
   private static final Unsafe UNSAFE = getUnsafe();
   @CompilerDirectives.CompilationFinal
   private boolean storeAsFinal;
   @CompilerDirectives.CompilationFinal
   private Class<?> type;
   @CompilerDirectives.CompilationFinal
   private StaticShape<?> shape;
   @CompilerDirectives.CompilationFinal
   private int offset;

   protected StaticProperty() {
   }

   void init(Class<?> clazz, boolean isFinal) {
      this.type = clazz;
      this.storeAsFinal = isFinal;
   }

   protected abstract String getId();

   final boolean storeAsFinal() {
      return this.storeAsFinal;
   }

   final Class<?> getPropertyType() {
      return this.type;
   }

   final void initOffset(int o) {
      if (this.offset != 0) {
         throw new IllegalStateException(
            "Attempt to reinitialize the offset of static property '"
               + this.getId()
               + "' of type '"
               + this.type.getName()
               + "'.\nWas it added to more than one builder or multiple times to the same builder?"
         );
      } else {
         this.offset = o;
      }
   }

   final void initShape(StaticShape<?> s) {
      if (this.shape != null) {
         throw new IllegalStateException(
            "Attempt to reinitialize the shape of static property '"
               + this.getId()
               + "' of type '"
               + this.type.getName()
               + "'.\nWas it added to more than one builder or multiple times to the same builder?"
         );
      } else {
         this.shape = s;
      }
   }

   private void throwIllegalArgumentException(Class<?> accessType) {
      CompilerAsserts.neverPartOfCompilation();
      throw new IllegalArgumentException(
         "Static property '"
            + this.getId()
            + "' of type '"
            + this.type.getName()
            + "' cannot be accessed as '"
            + (accessType == null ? "null" : accessType.getName())
            + "'"
      );
   }

   private void checkObjectGetAccess() {
      if (this.type.isPrimitive()) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.throwIllegalArgumentException(Object.class);
      }
   }

   private void checkObjectSetAccess(Object value) {
      if (value == null) {
         if (this.type.isPrimitive()) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.throwIllegalArgumentException(null);
         }
      } else if (!this.type.isInstance(value)) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.throwIllegalArgumentException(value.getClass());
      }
   }

   private void checkPrimitiveAccess(Class<?> accessType) {
      if (this.type != accessType) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.throwIllegalArgumentException(accessType);
      }
   }

   public final Object getObject(Object obj) {
      this.checkObjectGetAccess();
      return UNSAFE.getObject(this.shape.getStorage(obj, false), this.offset);
   }

   public final Object getObjectVolatile(Object obj) {
      this.checkObjectGetAccess();
      return UNSAFE.getObjectVolatile(this.shape.getStorage(obj, false), this.offset);
   }

   public final void setObject(Object obj, Object value) {
      this.checkObjectSetAccess(value);
      UNSAFE.putObject(this.shape.getStorage(obj, false), this.offset, value);
   }

   public final void setObjectVolatile(Object obj, Object value) {
      this.checkObjectSetAccess(value);
      UNSAFE.putObjectVolatile(this.shape.getStorage(obj, false), this.offset, value);
   }

   public final boolean compareAndSwapObject(Object obj, Object expect, Object update) {
      this.checkObjectSetAccess(update);
      return UNSAFE.compareAndSwapObject(this.shape.getStorage(obj, false), this.offset, expect, update);
   }

   public final Object getAndSetObject(Object obj, Object value) {
      this.checkObjectSetAccess(value);
      return UNSAFE.getAndSetObject(this.shape.getStorage(obj, false), this.offset, value);
   }

   public final Object compareAndExchangeObject(Object obj, Object expect, Object update) {
      this.checkObjectSetAccess(update);
      return StaticProperty.CASSupport.compareAndExchangeObject(this.shape.getStorage(obj, false), (long)this.offset, expect, update);
   }

   public final boolean getBoolean(Object obj) {
      this.checkPrimitiveAccess(boolean.class);
      return UNSAFE.getBoolean(this.shape.getStorage(obj, true), this.offset);
   }

   public final boolean getBooleanVolatile(Object obj) {
      this.checkPrimitiveAccess(boolean.class);
      return UNSAFE.getBooleanVolatile(this.shape.getStorage(obj, true), this.offset);
   }

   public final void setBoolean(Object obj, boolean value) {
      this.checkPrimitiveAccess(boolean.class);
      UNSAFE.putBoolean(this.shape.getStorage(obj, true), this.offset, value);
   }

   public final void setBooleanVolatile(Object obj, boolean value) {
      this.checkPrimitiveAccess(boolean.class);
      UNSAFE.putBooleanVolatile(this.shape.getStorage(obj, true), this.offset, value);
   }

   public final boolean compareAndSwapBoolean(Object obj, boolean expect, boolean update) {
      this.checkPrimitiveAccess(boolean.class);
      return StaticProperty.CASSupport.compareAndSetBoolean(this.shape.getStorage(obj, true), (long)this.offset, expect, update);
   }

   public final boolean compareAndExchangeBoolean(Object obj, boolean expect, boolean update) {
      this.checkPrimitiveAccess(boolean.class);
      return StaticProperty.CASSupport.compareAndExchangeBoolean(this.shape.getStorage(obj, true), (long)this.offset, expect, update);
   }

   public final byte getByte(Object obj) {
      this.checkPrimitiveAccess(byte.class);
      return UNSAFE.getByte(this.shape.getStorage(obj, true), this.offset);
   }

   public final byte getByteVolatile(Object obj) {
      this.checkPrimitiveAccess(byte.class);
      return UNSAFE.getByteVolatile(this.shape.getStorage(obj, true), this.offset);
   }

   public final void setByte(Object obj, byte value) {
      this.checkPrimitiveAccess(byte.class);
      UNSAFE.putByte(this.shape.getStorage(obj, true), this.offset, value);
   }

   public final void setByteVolatile(Object obj, byte value) {
      this.checkPrimitiveAccess(byte.class);
      UNSAFE.putByteVolatile(this.shape.getStorage(obj, true), this.offset, value);
   }

   public final boolean compareAndSwapByte(Object obj, byte expect, byte update) {
      this.checkPrimitiveAccess(byte.class);
      return StaticProperty.CASSupport.compareAndSetByte(this.shape.getStorage(obj, true), (long)this.offset, expect, update);
   }

   public final byte compareAndExchangeByte(Object obj, byte expect, byte update) {
      this.checkPrimitiveAccess(byte.class);
      return StaticProperty.CASSupport.compareAndExchangeByte(this.shape.getStorage(obj, true), (long)this.offset, expect, update);
   }

   public final char getChar(Object obj) {
      this.checkPrimitiveAccess(char.class);
      return UNSAFE.getChar(this.shape.getStorage(obj, true), this.offset);
   }

   public final char getCharVolatile(Object obj) {
      this.checkPrimitiveAccess(char.class);
      return UNSAFE.getCharVolatile(this.shape.getStorage(obj, true), this.offset);
   }

   public final void setChar(Object obj, char value) {
      this.checkPrimitiveAccess(char.class);
      UNSAFE.putChar(this.shape.getStorage(obj, true), this.offset, value);
   }

   public final void setCharVolatile(Object obj, char value) {
      this.checkPrimitiveAccess(char.class);
      UNSAFE.putCharVolatile(this.shape.getStorage(obj, true), this.offset, value);
   }

   public final boolean compareAndSwapChar(Object obj, char expect, char update) {
      this.checkPrimitiveAccess(char.class);
      return StaticProperty.CASSupport.compareAndSetChar(this.shape.getStorage(obj, true), (long)this.offset, expect, update);
   }

   public final char compareAndExchangeChar(Object obj, char expect, char update) {
      this.checkPrimitiveAccess(char.class);
      return StaticProperty.CASSupport.compareAndExchangeChar(this.shape.getStorage(obj, true), (long)this.offset, expect, update);
   }

   public final double getDouble(Object obj) {
      this.checkPrimitiveAccess(double.class);
      return UNSAFE.getDouble(this.shape.getStorage(obj, true), this.offset);
   }

   public final double getDoubleVolatile(Object obj) {
      this.checkPrimitiveAccess(double.class);
      return UNSAFE.getDoubleVolatile(this.shape.getStorage(obj, true), this.offset);
   }

   public final void setDouble(Object obj, double value) {
      this.checkPrimitiveAccess(double.class);
      UNSAFE.putDouble(this.shape.getStorage(obj, true), this.offset, value);
   }

   public final void setDoubleVolatile(Object obj, double value) {
      this.checkPrimitiveAccess(double.class);
      UNSAFE.putDoubleVolatile(this.shape.getStorage(obj, true), this.offset, value);
   }

   public final boolean compareAndSwapDouble(Object obj, double expect, double update) {
      this.checkPrimitiveAccess(double.class);
      return StaticProperty.CASSupport.compareAndSetDouble(this.shape.getStorage(obj, true), (long)this.offset, expect, update);
   }

   public final double compareAndExchangeDouble(Object obj, double expect, double update) {
      this.checkPrimitiveAccess(double.class);
      return StaticProperty.CASSupport.compareAndExchangeDouble(this.shape.getStorage(obj, true), (long)this.offset, expect, update);
   }

   public final float getFloat(Object obj) {
      this.checkPrimitiveAccess(float.class);
      return UNSAFE.getFloat(this.shape.getStorage(obj, true), this.offset);
   }

   public final float getFloatVolatile(Object obj) {
      this.checkPrimitiveAccess(float.class);
      return UNSAFE.getFloatVolatile(this.shape.getStorage(obj, true), this.offset);
   }

   public final void setFloat(Object obj, float value) {
      this.checkPrimitiveAccess(float.class);
      UNSAFE.putFloat(this.shape.getStorage(obj, true), this.offset, value);
   }

   public final void setFloatVolatile(Object obj, float value) {
      this.checkPrimitiveAccess(float.class);
      UNSAFE.putFloatVolatile(this.shape.getStorage(obj, true), this.offset, value);
   }

   public final boolean compareAndSwapFloat(Object obj, float expect, float update) {
      this.checkPrimitiveAccess(float.class);
      return StaticProperty.CASSupport.compareAndSetFloat(this.shape.getStorage(obj, true), (long)this.offset, expect, update);
   }

   public final float compareAndExchangeFloat(Object obj, float expect, float update) {
      this.checkPrimitiveAccess(float.class);
      return StaticProperty.CASSupport.compareAndExchangeFloat(this.shape.getStorage(obj, true), (long)this.offset, expect, update);
   }

   public final int getInt(Object obj) {
      this.checkPrimitiveAccess(int.class);
      return UNSAFE.getInt(this.shape.getStorage(obj, true), this.offset);
   }

   public final int getIntVolatile(Object obj) {
      this.checkPrimitiveAccess(int.class);
      return UNSAFE.getIntVolatile(this.shape.getStorage(obj, true), this.offset);
   }

   public final void setInt(Object obj, int value) {
      this.checkPrimitiveAccess(int.class);
      UNSAFE.putInt(this.shape.getStorage(obj, true), this.offset, value);
   }

   public final void setIntVolatile(Object obj, int value) {
      this.checkPrimitiveAccess(int.class);
      UNSAFE.putIntVolatile(this.shape.getStorage(obj, true), this.offset, value);
   }

   public final boolean compareAndSwapInt(Object obj, int expect, int update) {
      this.checkPrimitiveAccess(int.class);
      return UNSAFE.compareAndSwapInt(this.shape.getStorage(obj, true), this.offset, expect, update);
   }

   public final int compareAndExchangeInt(Object obj, int expect, int update) {
      this.checkPrimitiveAccess(int.class);
      return StaticProperty.CASSupport.compareAndExchangeInt(this.shape.getStorage(obj, true), (long)this.offset, expect, update);
   }

   public final int getAndAddInt(Object obj, int delta) {
      this.checkPrimitiveAccess(int.class);
      return UNSAFE.getAndAddInt(this.shape.getStorage(obj, true), this.offset, delta);
   }

   public final int getAndSetInt(Object obj, int value) {
      this.checkPrimitiveAccess(int.class);
      return UNSAFE.getAndSetInt(this.shape.getStorage(obj, true), this.offset, value);
   }

   public final long getLong(Object obj) {
      this.checkPrimitiveAccess(long.class);
      return UNSAFE.getLong(this.shape.getStorage(obj, true), this.offset);
   }

   public final long getLongVolatile(Object obj) {
      this.checkPrimitiveAccess(long.class);
      return UNSAFE.getLongVolatile(this.shape.getStorage(obj, true), this.offset);
   }

   public final void setLong(Object obj, long value) {
      this.checkPrimitiveAccess(long.class);
      UNSAFE.putLong(this.shape.getStorage(obj, true), this.offset, value);
   }

   public final void setLongVolatile(Object obj, long value) {
      this.checkPrimitiveAccess(long.class);
      UNSAFE.putLongVolatile(this.shape.getStorage(obj, true), this.offset, value);
   }

   public final boolean compareAndSwapLong(Object obj, long expect, long update) {
      this.checkPrimitiveAccess(long.class);
      return UNSAFE.compareAndSwapLong(this.shape.getStorage(obj, true), this.offset, expect, update);
   }

   public final long compareAndExchangeLong(Object obj, long expect, long update) {
      this.checkPrimitiveAccess(long.class);
      return StaticProperty.CASSupport.compareAndExchangeLong(this.shape.getStorage(obj, true), (long)this.offset, expect, update);
   }

   public final long getAndAddLong(Object obj, long delta) {
      this.checkPrimitiveAccess(long.class);
      return UNSAFE.getAndAddLong(this.shape.getStorage(obj, true), this.offset, delta);
   }

   public final long getAndSetLong(Object obj, long value) {
      this.checkPrimitiveAccess(long.class);
      return UNSAFE.getAndSetLong(this.shape.getStorage(obj, true), this.offset, value);
   }

   public final short getShort(Object obj) {
      this.checkPrimitiveAccess(short.class);
      return UNSAFE.getShort(this.shape.getStorage(obj, true), this.offset);
   }

   public final short getShortVolatile(Object obj) {
      this.checkPrimitiveAccess(short.class);
      return UNSAFE.getShortVolatile(this.shape.getStorage(obj, true), this.offset);
   }

   public final void setShort(Object obj, short value) {
      this.checkPrimitiveAccess(short.class);
      UNSAFE.putShort(this.shape.getStorage(obj, true), this.offset, value);
   }

   public final void setShortVolatile(Object obj, short value) {
      this.checkPrimitiveAccess(short.class);
      UNSAFE.putShortVolatile(this.shape.getStorage(obj, true), this.offset, value);
   }

   public final boolean compareAndSwapShort(Object obj, short expect, short update) {
      this.checkPrimitiveAccess(short.class);
      return StaticProperty.CASSupport.compareAndSetShort(this.shape.getStorage(obj, true), (long)this.offset, expect, update);
   }

   public final short compareAndExchangeShort(Object obj, short expect, short update) {
      this.checkPrimitiveAccess(short.class);
      return StaticProperty.CASSupport.compareAndExchangeShort(this.shape.getStorage(obj, true), (long)this.offset, expect, update);
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

   private static final class CASSupport {
      private static boolean isBigEndian() {
         return ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
      }

      private static boolean compareAndSetByte(Object o, long offset, byte expected, byte x) {
         return compareAndExchangeByte(o, offset, expected, x) == expected;
      }

      private static boolean compareAndSetBoolean(Object o, long offset, boolean expected, boolean x) {
         byte byteExpected = (byte)(expected ? 1 : 0);
         byte byteX = (byte)(x ? 1 : 0);
         return compareAndSetByte(o, offset, byteExpected, byteX);
      }

      private static boolean compareAndSetShort(Object o, long offset, short expected, short x) {
         return compareAndExchangeShort(o, offset, expected, x) == expected;
      }

      private static boolean compareAndSetChar(Object o, long offset, char expected, char x) {
         return compareAndSetShort(o, offset, (short)expected, (short)x);
      }

      private static boolean compareAndSetFloat(Object o, long offset, float expected, float x) {
         return StaticProperty.UNSAFE.compareAndSwapInt(o, offset, Float.floatToRawIntBits(expected), Float.floatToRawIntBits(x));
      }

      private static boolean compareAndSetDouble(Object o, long offset, double expected, double x) {
         return StaticProperty.UNSAFE.compareAndSwapLong(o, offset, Double.doubleToRawLongBits(expected), Double.doubleToRawLongBits(x));
      }

      private static byte compareAndExchangeByte(Object o, long offset, byte expected, byte x) {
         long wordOffset = offset & -4L;
         int shift = (int)(offset & 3L) << 3;
         if (isBigEndian()) {
            shift = 24 - shift;
         }

         int mask = 255 << shift;
         int maskedExpected = (expected & 255) << shift;
         int maskedX = (x & 255) << shift;

         int fullWord;
         do {
            fullWord = StaticProperty.UNSAFE.getIntVolatile(o, wordOffset);
            if ((fullWord & mask) != maskedExpected) {
               return (byte)((fullWord & mask) >> shift);
            }
         } while (!StaticProperty.UNSAFE.compareAndSwapInt(o, wordOffset, fullWord, fullWord & ~mask | maskedX));

         return expected;
      }

      private static boolean compareAndExchangeBoolean(Object o, long offset, boolean expected, boolean x) {
         byte byteExpected = (byte)(expected ? 1 : 0);
         byte byteX = (byte)(x ? 1 : 0);
         return compareAndExchangeByte(o, offset, byteExpected, byteX) != 0;
      }

      private static short compareAndExchangeShort(Object o, long offset, short expected, short x) {
         if ((offset & 3L) == 3L) {
            throw new IllegalArgumentException("Update spans the word, not supported");
         } else {
            long wordOffset = offset & -4L;
            int shift = (int)(offset & 3L) << 3;
            if (isBigEndian()) {
               shift = 16 - shift;
            }

            int mask = 65535 << shift;
            int maskedExpected = (expected & '\uffff') << shift;
            int maskedX = (x & '\uffff') << shift;

            int fullWord;
            do {
               fullWord = StaticProperty.UNSAFE.getIntVolatile(o, wordOffset);
               if ((fullWord & mask) != maskedExpected) {
                  return (short)((fullWord & mask) >> shift);
               }
            } while (!StaticProperty.UNSAFE.compareAndSwapInt(o, wordOffset, fullWord, fullWord & ~mask | maskedX));

            return expected;
         }
      }

      private static char compareAndExchangeChar(Object o, long offset, char expected, char x) {
         return (char)compareAndExchangeShort(o, offset, (short)expected, (short)x);
      }

      private static int compareAndExchangeInt(Object o, long offset, int expected, int x) {
         do {
            int result = StaticProperty.UNSAFE.getIntVolatile(o, offset);
            if (result != expected) {
               return result;
            }
         } while (!StaticProperty.UNSAFE.compareAndSwapInt(o, offset, expected, x));

         return expected;
      }

      private static Object compareAndExchangeObject(Object o, long offset, Object expected, Object x) {
         do {
            Object result = StaticProperty.UNSAFE.getObjectVolatile(o, offset);
            if (result != expected) {
               return result;
            }
         } while (!StaticProperty.UNSAFE.compareAndSwapObject(o, offset, expected, x));

         return expected;
      }

      private static float compareAndExchangeFloat(Object o, long offset, float expected, float x) {
         return Float.intBitsToFloat(compareAndExchangeInt(o, offset, Float.floatToRawIntBits(expected), Float.floatToRawIntBits(x)));
      }

      private static long compareAndExchangeLong(Object o, long offset, long expected, long x) {
         do {
            long result = StaticProperty.UNSAFE.getLongVolatile(o, offset);
            if (result != expected) {
               return result;
            }
         } while (!StaticProperty.UNSAFE.compareAndSwapLong(o, offset, expected, x));

         return expected;
      }

      private static double compareAndExchangeDouble(Object o, long offset, double expected, double x) {
         return Double.longBitsToDouble(compareAndExchangeLong(o, offset, Double.doubleToRawLongBits(expected), Double.doubleToRawLongBits(x)));
      }
   }
}
