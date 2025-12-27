package org.graalvm.nativeimage.hosted;

import org.graalvm.nativeimage.ImageSingletons;
import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;
import org.graalvm.nativeimage.impl.ConfigurationCondition;
import org.graalvm.nativeimage.impl.RuntimeSerializationSupport;

@Platforms(Platform.HOSTED_ONLY.class)
public final class RuntimeSerialization {
   public static void registerIncludingAssociatedClasses(Class<?> clazz) {
      ImageSingletons.lookup(RuntimeSerializationSupport.class).registerIncludingAssociatedClasses(ConfigurationCondition.alwaysTrue(), clazz);
   }

   public static void register(Class<?>... classes) {
      ImageSingletons.lookup(RuntimeSerializationSupport.class).register(ConfigurationCondition.alwaysTrue(), classes);
   }

   public static void registerWithTargetConstructorClass(Class<?> clazz, Class<?> customTargetConstructorClazz) {
      ImageSingletons.lookup(RuntimeSerializationSupport.class)
         .registerWithTargetConstructorClass(ConfigurationCondition.alwaysTrue(), clazz, customTargetConstructorClazz);
   }

   public static void registerLambdaCapturingClass(Class<?> lambdaCapturingClass) {
      ImageSingletons.lookup(RuntimeSerializationSupport.class).registerLambdaCapturingClass(ConfigurationCondition.alwaysTrue(), lambdaCapturingClass);
   }

   public static void registerProxyClass(Class<?>... implementedInterfaces) {
      ImageSingletons.lookup(RuntimeSerializationSupport.class).registerProxyClass(ConfigurationCondition.alwaysTrue(), implementedInterfaces);
   }

   private RuntimeSerialization() {
   }
}
