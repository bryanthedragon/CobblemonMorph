
package org.graalvm.nativeimage.impl;

import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.util.Arrays;
import org.graalvm.nativeimage.impl.ConfigurationCondition;

public interface ReflectionRegistry {
    default public void register(ConfigurationCondition condition2, Class<?> ... classes) {
        Arrays.stream(classes).forEach(clazz -> this.register(condition2, false, (Class<?>)clazz));
    }

    public void register(ConfigurationCondition var1, boolean var2, Class<?> var3);

    public void register(ConfigurationCondition var1, boolean var2, Executable ... var3);

    public void register(ConfigurationCondition var1, boolean var2, Field ... var3);

    default public void registerClassLookupException(ConfigurationCondition condition2, String typeName, Throwable t) {
    }
}

