package com.oracle.truffle.api.impl;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleOptions;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.graalvm.nativeimage.ImageInfo;

public abstract class TruffleLocator {
   private static TruffleLocator nativeImageLocator;
   private static final AtomicBoolean NATIVE_IMAGE_LOCATOR_INITIALIZED = new AtomicBoolean();

   public static List<ClassLoader> loaders() {
      TruffleLocator locator = Truffle.getRuntime().getCapability(TruffleLocator.class);
      if (locator != null) {
         List<ClassLoader> found = new ArrayList<>();
         TruffleLocator.Response response = new TruffleLocator.Response(found);
         locator.locate(response);
         return found.isEmpty() ? null : found;
      } else {
         return null;
      }
   }

   static void initializeNativeImageTruffleLocator() {
      assert TruffleOptions.AOT : "Only supported in AOT mode.";

      if (nativeImageLocator != null && (ImageInfo.inImageBuildtimeCode() || NATIVE_IMAGE_LOCATOR_INITIALIZED.compareAndSet(false, true))) {
         nativeImageLocator.locate(new TruffleLocator.Response(new ArrayList<>()));
      }
   }

   protected abstract void locate(TruffleLocator.Response response);

   private static void initializeNativeImageState() {
      assert TruffleOptions.AOT : "Only supported during image generation";

      nativeImageLocator = Truffle.getRuntime().getCapability(TruffleLocator.class);
   }

   private static void resetNativeImageState() {
      assert TruffleOptions.AOT : "Only supported during image generation";

      nativeImageLocator = null;
   }

   public static final class Response {
      private final List<ClassLoader> loaders;

      Response(List<ClassLoader> loaders) {
         this.loaders = loaders;
      }

      public void registerClassLoader(ClassLoader languageLoader) {
         this.loaders.add(languageLoader);
      }
   }
}
