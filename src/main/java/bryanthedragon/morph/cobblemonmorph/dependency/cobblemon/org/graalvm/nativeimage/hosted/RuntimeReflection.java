package org.graalvm.nativeimage.hosted;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.graalvm.nativeimage.ImageSingletons;
import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;
import org.graalvm.nativeimage.impl.ConfigurationCondition;
import org.graalvm.nativeimage.impl.RuntimeReflectionSupport;

@Platforms(Platform.HOSTED_ONLY.class)
public final class RuntimeReflection {
   public static void register(Class<?>... classes) {
      ImageSingletons.lookup(RuntimeReflectionSupport.class).register(ConfigurationCondition.alwaysTrue(), classes);
   }

   public static void register(Executable... methods) {
      ImageSingletons.lookup(RuntimeReflectionSupport.class).register(ConfigurationCondition.alwaysTrue(), false, methods);
   }

   public static void registerAsQueried(Executable... methods) {
      ImageSingletons.lookup(RuntimeReflectionSupport.class).register(ConfigurationCondition.alwaysTrue(), true, methods);
   }

   public static void register(Field... fields) {
      ImageSingletons.lookup(RuntimeReflectionSupport.class).register(ConfigurationCondition.alwaysTrue(), false, fields);
   }

   @Deprecated(since = "21.1")
   public static void register(boolean finalIsWritable, Field... fields) {
      register(fields);
   }

   @Deprecated(since = "21.1")
   public static void register(boolean finalIsWritable, boolean allowUnsafeAccess, Field... fields) {
      register(fields);
   }

   public static void registerForReflectiveInstantiation(Class<?>... classes) {
      for (Class<?> clazz : classes) {
         if (clazz.isArray() || clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
            throw new IllegalArgumentException(
               "Class " + clazz.getTypeName() + " cannot be instantiated reflectively. It must be a non-abstract instance type."
            );
         }

         Constructor<?> nullaryConstructor;
         try {
            nullaryConstructor = clazz.getDeclaredConstructor();
         } catch (NoSuchMethodException var7) {
            throw new IllegalArgumentException(
               "Class " + clazz.getTypeName() + " cannot be instantiated reflectively . It does not have a nullary constructor."
            );
         }

         register(nullaryConstructor);
      }
   }

   private RuntimeReflection() {
   }
}
