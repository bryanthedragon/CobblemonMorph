package org.graalvm.nativeimage;

import java.nio.file.Path;
import java.util.Map;
import org.graalvm.nativeimage.c.function.CEntryPointLiteral;
import org.graalvm.nativeimage.impl.ProcessPropertiesSupport;

public final class ProcessProperties {
   public static String getExecutableName() {
      return ImageSingletons.lookup(ProcessPropertiesSupport.class).getExecutableName();
   }

   public static long getProcessID() {
      return ImageSingletons.lookup(ProcessPropertiesSupport.class).getProcessID();
   }

   public static long getProcessID(Process process) {
      return ImageSingletons.lookup(ProcessPropertiesSupport.class).getProcessID(process);
   }

   public static int waitForProcessExit(long processID) {
      return ImageSingletons.lookup(ProcessPropertiesSupport.class).waitForProcessExit(processID);
   }

   public static boolean destroy(long processID) {
      return ImageSingletons.lookup(ProcessPropertiesSupport.class).destroy(processID);
   }

   public static boolean destroyForcibly(long processID) {
      return ImageSingletons.lookup(ProcessPropertiesSupport.class).destroyForcibly(processID);
   }

   public static boolean isAlive(long processID) {
      return ImageSingletons.lookup(ProcessPropertiesSupport.class).isAlive(processID);
   }

   public static String getObjectFile(String symbol) {
      return ImageSingletons.lookup(ProcessPropertiesSupport.class).getObjectFile(symbol);
   }

   public static String getObjectFile(CEntryPointLiteral<?> symbol) {
      return ImageSingletons.lookup(ProcessPropertiesSupport.class).getObjectFile(symbol);
   }

   public static String setLocale(String category, String locale) {
      return ImageSingletons.lookup(ProcessPropertiesSupport.class).setLocale(category, locale);
   }

   public static void exec(Path executable, String... args) {
      ImageSingletons.lookup(ProcessPropertiesSupport.class).exec(executable, args);
   }

   public static void exec(Path executable, String[] args, Map<String, String> env) {
      ImageSingletons.lookup(ProcessPropertiesSupport.class).exec(executable, args, env);
   }

   public static String getArgumentVectorProgramName() {
      return ImageSingletons.lookup(ProcessPropertiesSupport.class).getArgumentVectorProgramName();
   }

   public static boolean setArgumentVectorProgramName(String name) {
      return ImageSingletons.lookup(ProcessPropertiesSupport.class).setArgumentVectorProgramName(name);
   }

   public static int getArgumentVectorBlockSize() {
      return ImageSingletons.lookup(ProcessPropertiesSupport.class).getArgumentVectorBlockSize();
   }

   private ProcessProperties() {
   }
}
