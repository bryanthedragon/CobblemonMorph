package org.graalvm.nativeimage.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface RuntimeSerializationSupport {
   void registerIncludingAssociatedClasses(ConfigurationCondition condition, Class<?> clazz);

   void register(ConfigurationCondition condition, Class<?>... classes);

   void registerWithTargetConstructorClass(ConfigurationCondition condition, Class<?> clazz, Class<?> customTargetConstructorClazz);

   void registerWithTargetConstructorClass(ConfigurationCondition condition, String className, String customTargetConstructorClassName);

   void registerLambdaCapturingClass(ConfigurationCondition condition, String lambdaCapturingClassName);

   default void registerLambdaCapturingClass(ConfigurationCondition condition, Class<?> lambdaCapturingClass) {
      this.registerLambdaCapturingClass(condition, lambdaCapturingClass.getName());
   }

   void registerProxyClass(ConfigurationCondition condition, List<String> implementedInterfaces);

   default void registerProxyClass(ConfigurationCondition condition, Class<?>... implementedInterfaces) {
      this.registerProxyClass(condition, Arrays.stream(implementedInterfaces).map(Class::getName).collect(Collectors.toList()));
   }
}
