
package org.graalvm.nativeimage.impl;

import org.graalvm.nativeimage.PinnedObject;

public interface PinnedObjectSupport {
    public PinnedObject create(Object var1);

    public boolean isPinned(Object var1);
}

