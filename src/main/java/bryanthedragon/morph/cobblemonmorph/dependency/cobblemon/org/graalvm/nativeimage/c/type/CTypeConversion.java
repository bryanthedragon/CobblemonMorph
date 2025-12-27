package org.graalvm.nativeimage.c.type;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.graalvm.nativeimage.ImageSingletons;
import org.graalvm.nativeimage.PinnedObject;
import org.graalvm.nativeimage.impl.CTypeConversionSupport;
import org.graalvm.word.PointerBase;
import org.graalvm.word.UnsignedWord;
import org.graalvm.word.WordFactory;

public final class CTypeConversion {
   private CTypeConversion() {
   }

   public static CTypeConversion.CCharPointerHolder toCString(CharSequence javaString) {
      return ImageSingletons.lookup(CTypeConversionSupport.class).toCString(javaString);
   }

   public static UnsignedWord toCString(CharSequence javaString, CCharPointer buffer, UnsignedWord bufferSize) {
      return ImageSingletons.lookup(CTypeConversionSupport.class).toCString(javaString, buffer, bufferSize);
   }

   public static UnsignedWord toCString(CharSequence javaString, Charset charset, CCharPointer buffer, UnsignedWord bufferSize) {
      return ImageSingletons.lookup(CTypeConversionSupport.class).toCString(javaString, charset, buffer, bufferSize);
   }

   public static String toJavaString(CCharPointer cString) {
      return ImageSingletons.lookup(CTypeConversionSupport.class).toJavaString(cString);
   }

   public static String toJavaString(CCharPointer cString, UnsignedWord length) {
      return ImageSingletons.lookup(CTypeConversionSupport.class).toJavaString(cString, length);
   }

   public static String toJavaString(CCharPointer cString, UnsignedWord length, Charset charset) {
      return ImageSingletons.lookup(CTypeConversionSupport.class).toJavaString(cString, length, charset);
   }

   public static String utf8ToJavaString(CCharPointer utf8String) {
      return ImageSingletons.lookup(CTypeConversionSupport.class).utf8ToJavaString(utf8String);
   }

   public static byte toCBoolean(boolean value) {
      return (byte)(value ? 1 : 0);
   }

   public static boolean toBoolean(int value) {
      return value != 0;
   }

   public static boolean toBoolean(PointerBase pointer) {
      return pointer.isNonNull();
   }

   public static CTypeConversion.CCharPointerPointerHolder toCStrings(CharSequence[] javaStrings) {
      return new CTypeConversion.CCharPointerPointerHolder(javaStrings);
   }

   public static ByteBuffer asByteBuffer(PointerBase address, int size) {
      return ImageSingletons.lookup(CTypeConversionSupport.class).asByteBuffer(address, size);
   }

   public static CTypeConversion.CCharPointerHolder toCBytes(byte[] bytes) {
      return ImageSingletons.lookup(CTypeConversionSupport.class).toCBytes(bytes);
   }

   public interface CCharPointerHolder extends AutoCloseable {
      CCharPointer get();

      @Override
      void close();
   }

   public static final class CCharPointerPointerHolder implements AutoCloseable {
      private final CTypeConversion.CCharPointerHolder[] ccpHolderArray;
      private final PinnedObject pinnedCCPArray;

      private CCharPointerPointerHolder(CharSequence[] csArray) {
         this.ccpHolderArray = new CTypeConversion.CCharPointerHolder[csArray.length + 1];
         CCharPointer[] ccpArray = new CCharPointer[csArray.length + 1];

         for (int i = 0; i < csArray.length; i++) {
            this.ccpHolderArray[i] = CTypeConversion.toCString(csArray[i]);
            ccpArray[i] = this.ccpHolderArray[i].get();
         }

         ccpArray[csArray.length] = WordFactory.nullPointer();
         this.pinnedCCPArray = PinnedObject.create(ccpArray);
      }

      public CCharPointerPointer get() {
         return this.pinnedCCPArray.addressOfArrayElement(0);
      }

      @Override
      public void close() {
         for (int i = 0; i < this.ccpHolderArray.length - 1; i++) {
            this.ccpHolderArray[i].close();
         }

         this.pinnedCCPArray.close();
      }
   }
}
