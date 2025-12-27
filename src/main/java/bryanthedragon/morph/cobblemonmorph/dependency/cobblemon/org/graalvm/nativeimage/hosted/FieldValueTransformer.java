package org.graalvm.nativeimage.hosted;

import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;

@Platforms(Platform.HOSTED_ONLY.class)
public interface FieldValueTransformer {
   Object transform(Object receiver, Object originalValue);
}
