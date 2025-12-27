package org.graalvm.nativeimage;

import org.graalvm.word.PointerBase;

public final class StackValue {
   private StackValue() {
   }

   public static <T extends PointerBase> T get(Class<T> structType) {
      throw new IllegalStateException("Cannot invoke method during native image generation");
   }

   public static <T extends PointerBase> T get(int numberOfElements, Class<T> structType) {
      throw new IllegalStateException("Cannot invoke method during native image generation");
   }

   public static <T extends PointerBase> T get(int size) {
      throw new IllegalStateException("Cannot invoke method during native image generation");
   }

   public static <T extends PointerBase> T get(int numberOfElements, int elementSize) {
      throw new IllegalStateException("Cannot invoke method during native image generation");
   }
}
