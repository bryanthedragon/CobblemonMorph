
package org.graalvm.nativeimage.impl;

import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;
import org.graalvm.word.PointerBase;

@Platforms(value={Platform.HOSTED_ONLY.class})
public interface SizeOfSupport {
    public int sizeof(Class<? extends PointerBase> var1);
}

