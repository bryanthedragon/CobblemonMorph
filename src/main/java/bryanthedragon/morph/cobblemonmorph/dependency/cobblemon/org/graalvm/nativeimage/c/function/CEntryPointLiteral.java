package org.graalvm.nativeimage.c.function;

import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;
import org.graalvm.nativeimage.impl.CEntryPointLiteralCodePointer;

public final class CEntryPointLiteral<T extends CFunctionPointer> {
   private CFunctionPointer functionPointer;

   private CEntryPointLiteral(Class<?> definingClass, String methodName, Class<?>... parameterTypes) {
      this.functionPointer = new CEntryPointLiteralCodePointer(definingClass, methodName, parameterTypes);
   }

   @Platforms(Platform.HOSTED_ONLY.class)
   public static <T extends CFunctionPointer> CEntryPointLiteral<T> create(Class<?> definingClass, String methodName, Class<?>... parameterTypes) {
      return new CEntryPointLiteral<>(definingClass, methodName, parameterTypes);
   }

   public T getFunctionPointer() {
      throw new IllegalStateException("Cannot invoke method during native image generation");
   }
}
