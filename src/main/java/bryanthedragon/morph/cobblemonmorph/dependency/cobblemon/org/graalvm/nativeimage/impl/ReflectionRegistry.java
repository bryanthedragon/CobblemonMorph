package org.graalvm.nativeimage.impl;

import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.util.Arrays;

public interface ReflectionRegistry {
   default void register(ConfigurationCondition condition, Class<?>... classes) {
      Arrays.stream(classes).forEach(clazz -> this.register(condition, false, (Class<?>)clazz));
   }

   void register(ConfigurationCondition condition, boolean unsafeAllocated, Class<?> clazz);

   void register(ConfigurationCondition condition, boolean queriedOnly, Executable... methods);

   void register(ConfigurationCondition condition, boolean finalIsWritable, Field... fields);

   default void registerClassLookupException(ConfigurationCondition condition, String typeName, Throwable t) {
   }
}
