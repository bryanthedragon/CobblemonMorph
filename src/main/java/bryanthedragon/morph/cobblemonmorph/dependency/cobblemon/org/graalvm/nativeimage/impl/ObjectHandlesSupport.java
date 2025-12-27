package org.graalvm.nativeimage.impl;

import org.graalvm.nativeimage.ObjectHandles;

public interface ObjectHandlesSupport {
   ObjectHandles getGlobalHandles();

   ObjectHandles createHandles();
}
