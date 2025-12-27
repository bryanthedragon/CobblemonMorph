package org.graalvm.nativeimage;

import org.graalvm.nativeimage.impl.InternalPlatform;

public interface Platform {
   String PLATFORM_PROPERTY_NAME = "svm.platform";

   static boolean includedIn(Class<? extends Platform> platformGroup) {
      return platformGroup.isInstance(ImageSingletons.lookup(Platform.class));
   }

   default String getOS() {
      throw new UnsupportedOperationException("Platform `" + this.getClass().getCanonicalName() + "`, doesn't implement getOS");
   }

   default String getArchitecture() {
      throw new UnsupportedOperationException("Platform `" + this.getClass().getCanonicalName() + "`, doesn't implement getArchitecture");
   }

   public interface AARCH64 extends Platform {
      @Override
      default String getArchitecture() {
         return "aarch64";
      }
   }

   public interface AMD64 extends Platform {
      @Override
      default String getArchitecture() {
         return "amd64";
      }
   }

   public interface ANDROID extends Platform.LINUX {
      @Override
      default String getOS() {
         return Platform.ANDROID.class.getSimpleName().toLowerCase();
      }
   }

   public static final class ANDROID_AARCH64 implements Platform.ANDROID, Platform.LINUX_AARCH64_BASE {
   }

   public interface DARWIN extends InternalPlatform.PLATFORM_JNI {
   }

   public interface DARWIN_AARCH64 extends Platform.DARWIN, Platform.AARCH64 {
   }

   public interface DARWIN_AMD64 extends Platform.DARWIN, Platform.AMD64 {
   }

   public static final class HOSTED_ONLY implements Platform {
      private HOSTED_ONLY() {
      }
   }

   public interface IOS extends Platform.DARWIN {
      @Override
      default String getOS() {
         return Platform.IOS.class.getSimpleName().toLowerCase();
      }
   }

   public static final class IOS_AARCH64 implements Platform.IOS, Platform.DARWIN_AARCH64 {
   }

   public static final class IOS_AMD64 implements Platform.IOS, Platform.DARWIN_AMD64 {
   }

   public interface LINUX extends InternalPlatform.PLATFORM_JNI {
      @Override
      default String getOS() {
         return Platform.LINUX.class.getSimpleName().toLowerCase();
      }
   }

   public static final class LINUX_AARCH64 implements Platform.LINUX, Platform.LINUX_AARCH64_BASE {
   }

   public interface LINUX_AARCH64_BASE extends Platform.LINUX, Platform.AARCH64 {
   }

   public static class LINUX_AMD64 implements Platform.LINUX, Platform.LINUX_AMD64_BASE {
   }

   public interface LINUX_AMD64_BASE extends Platform.LINUX, Platform.AMD64 {
   }

   public interface MACOS extends Platform.DARWIN {
      @Override
      default String getOS() {
         return "darwin";
      }
   }

   public static final class MACOS_AARCH64 implements Platform.MACOS, Platform.DARWIN_AARCH64 {
   }

   public static final class MACOS_AMD64 implements Platform.MACOS, Platform.DARWIN_AMD64 {
   }

   public interface WINDOWS extends InternalPlatform.PLATFORM_JNI {
      @Override
      default String getOS() {
         return Platform.WINDOWS.class.getSimpleName().toLowerCase();
      }
   }

   public static final class WINDOWS_AARCH64 implements Platform.WINDOWS, Platform.AARCH64 {
   }

   public static final class WINDOWS_AMD64 implements Platform.WINDOWS, Platform.AMD64 {
   }
}
