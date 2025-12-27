package org.graalvm.nativeimage.impl;

import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;

@Platforms(Platform.HOSTED_ONLY.class)
public interface CConstantValueSupport {
   <T> T getCConstantValue(Class<?> declaringClass, String methodName, Class<T> returnType);
}
