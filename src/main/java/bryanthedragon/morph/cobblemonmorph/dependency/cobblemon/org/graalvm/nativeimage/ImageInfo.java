
package org.graalvm.nativeimage;

public final class ImageInfo {
    public static final String PROPERTY_IMAGE_CODE_KEY = "org.graalvm.nativeimage.imagecode";
    public static final String PROPERTY_IMAGE_CODE_VALUE_BUILDTIME = "buildtime";
    public static final String PROPERTY_IMAGE_CODE_VALUE_RUNTIME = "runtime";
    public static final String PROPERTY_IMAGE_KIND_KEY = "org.graalvm.nativeimage.kind";
    public static final String PROPERTY_IMAGE_KIND_VALUE_SHARED_LIBRARY = "shared";
    public static final String PROPERTY_IMAGE_KIND_VALUE_EXECUTABLE = "executable";

    private ImageInfo() {
    }

    public static boolean inImageCode() {
        return ImageInfo.inImageBuildtimeCode() || ImageInfo.inImageRuntimeCode();
    }

    public static boolean inImageRuntimeCode() {
        return PROPERTY_IMAGE_CODE_VALUE_RUNTIME.equals(System.getProperty(PROPERTY_IMAGE_CODE_KEY));
    }

    public static boolean inImageBuildtimeCode() {
        return PROPERTY_IMAGE_CODE_VALUE_BUILDTIME.equals(System.getProperty(PROPERTY_IMAGE_CODE_KEY));
    }

    public static boolean isExecutable() {
        ImageInfo.ensureKindAvailable();
        return PROPERTY_IMAGE_KIND_VALUE_EXECUTABLE.equals(System.getProperty(PROPERTY_IMAGE_KIND_KEY));
    }

    public static boolean isSharedLibrary() {
        ImageInfo.ensureKindAvailable();
        return PROPERTY_IMAGE_KIND_VALUE_SHARED_LIBRARY.equals(System.getProperty(PROPERTY_IMAGE_KIND_KEY));
    }

    private static void ensureKindAvailable() {
        if (ImageInfo.inImageCode() && System.getProperty(PROPERTY_IMAGE_KIND_KEY) == null) {
            throw new UnsupportedOperationException("The kind of image that is built (executable or shared library) is not available yet because the relevant command line option has not been parsed yet.");
        }
    }
}

