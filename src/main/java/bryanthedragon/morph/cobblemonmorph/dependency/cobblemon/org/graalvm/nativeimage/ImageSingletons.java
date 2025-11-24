
package org.graalvm.nativeimage;

import org.graalvm.nativeimage.ImageInfo;
import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;
import org.graalvm.nativeimage.impl.ImageSingletonsSupport;

public final class ImageSingletons {
    @Platforms(value={Platform.HOSTED_ONLY.class})
    public static <T> void add(Class<T> key, T value2) {
        ImageSingletonsSupport.get().add(key, value2);
    }

    public static <T> T lookup(Class<T> key) {
        return ImageSingletonsSupport.get().lookup(key);
    }

    public static boolean contains(Class<?> key) {
        if (!ImageInfo.inImageCode()) {
            return false;
        }
        return ImageSingletonsSupport.get().contains(key);
    }

    private ImageSingletons() {
    }
}

