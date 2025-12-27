package org.graalvm.nativeimage.impl;

import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;
import org.graalvm.word.PointerBase;

@Platforms(Platform.HOSTED_ONLY.class)
public interface SizeOfSupport {
   int sizeof(Class<? extends PointerBase> clazz);
}
