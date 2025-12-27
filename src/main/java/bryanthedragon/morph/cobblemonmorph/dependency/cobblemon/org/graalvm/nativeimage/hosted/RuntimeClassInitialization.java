package org.graalvm.nativeimage.hosted;

import org.graalvm.nativeimage.ImageSingletons;
import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;
import org.graalvm.nativeimage.impl.RuntimeClassInitializationSupport;

@Platforms(Platform.HOSTED_ONLY.class)
public final class RuntimeClassInitialization {
   public static void initializeAtRunTime(Class<?>... classes) {
      StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();

      for (Class<?> aClass : classes) {
         ImageSingletons.lookup(RuntimeClassInitializationSupport.class)
            .initializeAtRunTime(aClass, classReason(stacktrace, getUnqualifiedName(aClass) + ".class"));
      }
   }

   public static void initializeAtBuildTime(Class<?>... classes) {
      StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();

      for (Class<?> aClass : classes) {
         ImageSingletons.lookup(RuntimeClassInitializationSupport.class)
            .initializeAtBuildTime(aClass, classReason(stacktrace, getUnqualifiedName(aClass) + ".class"));
      }
   }

   public static void initializeAtRunTime(String... packages) {
      StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();

      for (String aPackage : packages) {
         ImageSingletons.lookup(RuntimeClassInitializationSupport.class).initializeAtRunTime(aPackage, classReason(stacktrace, aPackage));
      }
   }

   public static void initializeAtBuildTime(String... packages) {
      StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();

      for (String aPackage : packages) {
         ImageSingletons.lookup(RuntimeClassInitializationSupport.class).initializeAtBuildTime(aPackage, classReason(stacktrace, aPackage));
      }
   }

   private static String getCaller(StackTraceElement[] stackTrace) {
      StackTraceElement e = stackTrace[2];
      return e.getClassName() + "." + e.getMethodName();
   }

   private static String classReason(StackTraceElement[] stacktrace, String simpleName) {
      return "from feature " + getCaller(stacktrace) + " with '" + simpleName + "'";
   }

   private static String getUnqualifiedName(Class<?> aClass) {
      String name = aClass.getTypeName();
      return name.substring(name.lastIndexOf(46) + 1);
   }

   private RuntimeClassInitialization() {
   }
}
