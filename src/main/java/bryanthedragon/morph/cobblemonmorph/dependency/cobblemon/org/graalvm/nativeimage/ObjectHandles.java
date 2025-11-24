
package org.graalvm.nativeimage;

import org.graalvm.nativeimage.ImageSingletons;
import org.graalvm.nativeimage.ObjectHandle;
import org.graalvm.nativeimage.impl.ObjectHandlesSupport;

public interface ObjectHandles {
    public static ObjectHandles getGlobal() {
        return ImageSingletons.lookup(ObjectHandlesSupport.class).getGlobalHandles();
    }

    public static ObjectHandles create() {
        return ImageSingletons.lookup(ObjectHandlesSupport.class).createHandles();
    }

    public ObjectHandle create(Object var1);

    public <T> T get(ObjectHandle var1);

    public void destroy(ObjectHandle var1);
}

