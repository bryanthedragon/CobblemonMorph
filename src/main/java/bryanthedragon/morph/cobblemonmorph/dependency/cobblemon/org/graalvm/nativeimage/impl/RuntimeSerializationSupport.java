
package org.graalvm.nativeimage.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.graalvm.nativeimage.impl.ConfigurationCondition;

public interface RuntimeSerializationSupport {
    public void registerIncludingAssociatedClasses(ConfigurationCondition var1, Class<?> var2);

    public void register(ConfigurationCondition var1, Class<?> ... var2);

    public void registerWithTargetConstructorClass(ConfigurationCondition var1, Class<?> var2, Class<?> var3);

    public void registerWithTargetConstructorClass(ConfigurationCondition var1, String var2, String var3);

    public void registerLambdaCapturingClass(ConfigurationCondition var1, String var2);

    default public void registerLambdaCapturingClass(ConfigurationCondition condition2, Class<?> lambdaCapturingClass) {
        this.registerLambdaCapturingClass(condition2, lambdaCapturingClass.getName());
    }

    public void registerProxyClass(ConfigurationCondition var1, List<String> var2);

    default public void registerProxyClass(ConfigurationCondition condition2, Class<?> ... implementedInterfaces) {
        this.registerProxyClass(condition2, Arrays.stream(implementedInterfaces).map(Class::getName).collect(Collectors.toList()));
    }
}

