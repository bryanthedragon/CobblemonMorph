package org.graalvm.nativeimage;

import org.graalvm.nativeimage.impl.ImageSingletonsSupport;

public final class ImageSingletons {
   @Platforms(Platform.HOSTED_ONLY.class)
   public static <T> void add(Class<T> key, T value) {
      ImageSingletonsSupport.get().add(key, value);
   }

   public static <T> T lookup(Class<T> key) {
      return ImageSingletonsSupport.get().lookup(key);
   }

   public static boolean contains(Class<?> key) {
      return !ImageInfo.inImageCode() ? false : ImageSingletonsSupport.get().contains(key);
   }

   private ImageSingletons() {
   }
}
