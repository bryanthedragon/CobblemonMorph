package org.graalvm.nativeimage.impl;

import org.graalvm.nativeimage.PinnedObject;

public interface PinnedObjectSupport {
   PinnedObject create(Object object);

   boolean isPinned(Object object);
}
