package org.graalvm.nativeimage;

import java.io.IOException;
import org.graalvm.nativeimage.impl.HeapDumpSupport;
import org.graalvm.nativeimage.impl.VMRuntimeSupport;

public final class VMRuntime {
   public static void initialize() {
      ImageSingletons.lookup(VMRuntimeSupport.class).initialize();
   }

   public static void shutdown() {
      ImageSingletons.lookup(VMRuntimeSupport.class).shutdown();
   }

   public static void dumpHeap(String outputFile, boolean live) throws IOException {
      if (!ImageSingletons.contains(HeapDumpSupport.class)) {
         throw new UnsupportedOperationException();
      } else {
         ImageSingletons.lookup(HeapDumpSupport.class).dumpHeap(outputFile, live);
      }
   }

   private VMRuntime() {
   }
}
