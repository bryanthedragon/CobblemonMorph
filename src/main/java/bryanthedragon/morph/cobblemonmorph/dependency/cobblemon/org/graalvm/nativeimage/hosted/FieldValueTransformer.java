
package org.graalvm.nativeimage.hosted;

import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;

@Platforms(value={Platform.HOSTED_ONLY.class})
public interface FieldValueTransformer {
    public Object transform(Object var1, Object var2);
}

