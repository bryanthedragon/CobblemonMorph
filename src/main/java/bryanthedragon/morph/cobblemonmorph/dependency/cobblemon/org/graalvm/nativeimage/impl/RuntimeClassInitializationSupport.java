package org.graalvm.nativeimage.impl;

public interface RuntimeClassInitializationSupport {
   void initializeAtRunTime(String name, String reason);

   void initializeAtBuildTime(String name, String reason);

   void rerunInitialization(String name, String reason);

   void initializeAtRunTime(Class<?> aClass, String reason);

   void rerunInitialization(Class<?> aClass, String reason);

   void initializeAtBuildTime(Class<?> aClass, String reason);
}
