
package org.graalvm.nativeimage.impl;

import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;

@Platforms(value={Platform.HOSTED_ONLY.class})
public interface CConstantValueSupport {
    public <T> T getCConstantValue(Class<?> var1, String var2, Class<T> var3);
}

