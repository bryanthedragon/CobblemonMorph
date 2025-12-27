package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerDirectives;
import java.lang.reflect.Field;
import sun.misc.Unsafe;

final class TStringUnsafe {
   static final int JAVA_SPEC = getJavaSpecificationVersion();
   private static final Unsafe UNSAFE = getUnsafe();
   private static final long javaStringValueFieldOffset;
   private static final long javaStringCoderFieldOffset;
   private static final long javaStringHashFieldOffset;

   @CompilerDirectives.TruffleBoundary
   private static int getJavaSpecificationVersion() {
      return Runtime.version().feature();
   }

   private static long getObjectFieldOffset(Field field) {
      return UNSAFE.objectFieldOffset(field);
   }

   @CompilerDirectives.TruffleBoundary
   private static Field getStringDeclaredField(String name) {
      try {
         return String.class.getDeclaredField(name);
      } catch (NoSuchFieldException var2) {
         throw new RuntimeException("failed to get " + name + " field offset", var2);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static Unsafe getUnsafe() {
      try {
         return Unsafe.getUnsafe();
      } catch (SecurityException var3) {
         try {
            Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeInstance.setAccessible(true);
            return (Unsafe)theUnsafeInstance.get(Unsafe.class);
         } catch (Exception var2) {
            throw new RuntimeException("exception while trying to get Unsafe.theUnsafe via reflection:", var2);
         }
      }
   }

   static byte[] getJavaStringArray(String str) {
      assert JAVA_SPEC > 8;

      Object value = UNSAFE.getObject(str, javaStringValueFieldOffset);

      assert value instanceof byte[];

      return (byte[])value;
   }

   static int getJavaStringStride(String s) {
      return UNSAFE.getByte(s, javaStringCoderFieldOffset);
   }

   @CompilerDirectives.TruffleBoundary
   private static String allocateJavaString() {
      try {
         return (String)UNSAFE.allocateInstance(String.class);
      } catch (InstantiationException var1) {
         throw CompilerDirectives.shouldNotReachHere("unsafe string allocation failed", var1);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static String createJavaString(byte[] bytes, int stride) {
      if (stride >= 0 && stride <= 1) {
         String ret = allocateJavaString();
         UNSAFE.putInt(ret, javaStringHashFieldOffset, 0);
         UNSAFE.putByte(ret, javaStringCoderFieldOffset, (byte)stride);
         UNSAFE.putObjectVolatile(ret, javaStringValueFieldOffset, bytes);

         assert checkUnsafeStringResult(bytes, stride, ret);

         return ret;
      } else {
         throw new IllegalArgumentException("stride must be 0 or 1!");
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static boolean checkUnsafeStringResult(byte[] bytes, int stride, String ret) {
      int length = bytes.length >> stride;
      char[] chars = new char[length];

      for (int i = 0; i < length; i++) {
         chars[i] = (char)TStringOps.readFromByteArray(bytes, stride, i);
      }

      return new String(chars).equals(ret);
   }

   static byte getByteManaged(Object array, long byteOffset) {
      return UNSAFE.getByte(array, byteOffset);
   }

   static byte getByteNative(long array, long byteOffset) {
      return UNSAFE.getByte(array + byteOffset);
   }

   static char getCharManaged(Object array, long byteOffset) {
      return UNSAFE.getChar(array, byteOffset);
   }

   static char getCharNative(long array, long byteOffset) {
      return UNSAFE.getChar(array + byteOffset);
   }

   static int getIntManaged(Object array, long byteOffset) {
      return UNSAFE.getInt(array, byteOffset);
   }

   static int getIntNative(long array, long byteOffset) {
      return UNSAFE.getInt(array + byteOffset);
   }

   static long getLongManaged(Object array, long byteOffset) {
      return UNSAFE.getLong(array, byteOffset);
   }

   static long getLongNative(long array) {
      return UNSAFE.getLong(array);
   }

   static void putByteManaged(byte[] array, long byteOffset, byte value) {
      UNSAFE.putByte(array, byteOffset, value);
   }

   static void putByteNative(long array, long byteOffset, byte value) {
      UNSAFE.putByte(array + byteOffset, value);
   }

   static void putCharManaged(byte[] array, long byteOffset, char value) {
      UNSAFE.putChar(array, byteOffset, value);
   }

   static void putCharNative(long array, long byteOffset, char value) {
      UNSAFE.putChar(array + byteOffset, value);
   }

   static void putIntManaged(byte[] array, long byteOffset, int value) {
      UNSAFE.putInt(array, byteOffset, value);
   }

   static void putIntNative(long array, long byteOffset, int value) {
      UNSAFE.putInt(array + byteOffset, value);
   }

   static void copyFromNative(long arraySrc, int offsetSrc, byte[] arrayDst, long offsetDst, int byteLength) {
      UNSAFE.copyMemory(null, arraySrc + offsetSrc, arrayDst, Unsafe.ARRAY_BYTE_BASE_OFFSET + offsetDst, byteLength);
   }

   static {
      if (JAVA_SPEC <= 8) {
         throw new RuntimeException("TruffleString requires Java version > 8");
      } else {
         Field valueField = getStringDeclaredField("value");
         Field coderField = getStringDeclaredField("coder");
         Field hashField = getStringDeclaredField("hash");
         javaStringValueFieldOffset = getObjectFieldOffset(valueField);
         javaStringCoderFieldOffset = getObjectFieldOffset(coderField);
         javaStringHashFieldOffset = getObjectFieldOffset(hashField);
      }
   }
}
