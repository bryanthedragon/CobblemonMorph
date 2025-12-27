package org.graalvm.nativeimage.impl;

public interface VMRuntimeSupport {
   void initialize();

   void shutdown();
}
