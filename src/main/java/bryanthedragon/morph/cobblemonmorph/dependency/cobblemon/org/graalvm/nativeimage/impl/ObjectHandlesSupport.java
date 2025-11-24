
package org.graalvm.nativeimage.impl;

import org.graalvm.nativeimage.ObjectHandles;

public interface ObjectHandlesSupport {
    public ObjectHandles getGlobalHandles();

    public ObjectHandles createHandles();
}

