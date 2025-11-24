
package org.graalvm.nativeimage.impl;

import org.graalvm.nativeimage.ImageSingletons;
import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;

@Platforms(value={Platform.HOSTED_ONLY.class})
public abstract class ImageSingletonsSupport {
    private static ImageSingletonsSupport support;

    protected static void installSupport(ImageSingletonsSupport imageSingletonsSupport) {
        assert (imageSingletonsSupport != null) : "ImageSingletonsSupport cannot be null.";
        support = imageSingletonsSupport;
    }

    public static boolean isInstalled() {
        return support != null;
    }

    public static ImageSingletonsSupport get() {
        ImageSingletonsSupport.checkInstalled();
        return support;
    }

    private static void checkInstalled() {
        if (support == null) {
            throw new Error("The class " + ImageSingletons.class.getSimpleName() + " can only be used when building native images, i.e., when using the native-image command.");
        }
    }

    protected ImageSingletonsSupport() {
    }

    public abstract <T> void add(Class<T> var1, T var2);

    public abstract <T> T lookup(Class<T> var1);

    public abstract boolean contains(Class<?> var1);
}

