package org.graalvm.nativeimage;

public final class CurrentIsolate {
   public static Isolate getIsolate() {
      throw new IllegalStateException("Cannot invoke method during native image generation");
   }

   public static IsolateThread getCurrentThread() {
      throw new IllegalStateException("Cannot invoke method during native image generation");
   }

   private CurrentIsolate() {
   }
}
