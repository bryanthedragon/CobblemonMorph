package org.graalvm.nativeimage;

import java.util.EnumSet;
import org.graalvm.nativeimage.impl.RuntimeOptionsSupport;
import org.graalvm.options.OptionDescriptors;

public final class RuntimeOptions {
   private RuntimeOptions() {
   }

   public static void set(String optionName, Object value) {
      ImageSingletons.lookup(RuntimeOptionsSupport.class).set(optionName, value);
   }

   public static <T> T get(String optionName) {
      return ImageSingletons.lookup(RuntimeOptionsSupport.class).get(optionName);
   }

   public static OptionDescriptors getOptions(EnumSet<RuntimeOptions.OptionClass> classes) {
      return ImageSingletons.lookup(RuntimeOptionsSupport.class).getOptions(classes);
   }

   public static OptionDescriptors getOptions() {
      return getOptions(EnumSet.allOf(RuntimeOptions.OptionClass.class));
   }

   public static enum OptionClass {
      VM,
      Compiler;
   }
}
