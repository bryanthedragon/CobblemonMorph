
package org.graalvm.nativeimage;

import org.graalvm.nativeimage.ImageSingletons;
import org.graalvm.nativeimage.impl.PinnedObjectSupport;
import org.graalvm.word.PointerBase;

public interface PinnedObject
extends AutoCloseable {
    public static PinnedObject create(Object object) {
        return ImageSingletons.lookup(PinnedObjectSupport.class).create(object);
    }

    @Override
    public void close();

    public Object getObject();

    public PointerBase addressOfObject();

    public <T extends PointerBase> T addressOfArrayElement(int var1);
}

