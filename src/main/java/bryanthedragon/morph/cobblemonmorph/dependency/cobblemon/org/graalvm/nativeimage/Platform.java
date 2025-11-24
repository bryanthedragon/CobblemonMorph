
package org.graalvm.nativeimage;

import org.graalvm.nativeimage.ImageSingletons;
import org.graalvm.nativeimage.impl.InternalPlatform;

public interface Platform {
    public static final String PLATFORM_PROPERTY_NAME = "svm.platform";

    public static boolean includedIn(Class<? extends Platform> platformGroup) {
        return platformGroup.isInstance(ImageSingletons.lookup(Platform.class));
    }

    default public String getOS() {
        throw new UnsupportedOperationException("Platform `" + this.getClass().getCanonicalName() + "`, doesn't implement getOS");
    }

    default public String getArchitecture() {
        throw new UnsupportedOperationException("Platform `" + this.getClass().getCanonicalName() + "`, doesn't implement getArchitecture");
    }

    public static final class HOSTED_ONLY
    implements Platform {
        private HOSTED_ONLY() {
        }
    }

    public static final class WINDOWS_AARCH64
    implements WINDOWS,
    AARCH64 {
    }

    public static final class WINDOWS_AMD64
    implements WINDOWS,
    AMD64 {
    }

    public static final class MACOS_AARCH64
    implements MACOS,
    DARWIN_AARCH64 {
    }

    public static final class MACOS_AMD64
    implements MACOS,
    DARWIN_AMD64 {
    }

    public static final class IOS_AARCH64
    implements IOS,
    DARWIN_AARCH64 {
    }

    public static final class IOS_AMD64
    implements IOS,
    DARWIN_AMD64 {
    }

    public static final class ANDROID_AARCH64
    implements ANDROID,
    LINUX_AARCH64_BASE {
    }

    public static final class LINUX_AARCH64
    implements LINUX,
    LINUX_AARCH64_BASE {
    }

    public static class LINUX_AMD64
    implements LINUX,
    LINUX_AMD64_BASE {
    }

    public static interface DARWIN_AARCH64
    extends DARWIN,
    AARCH64 {
    }

    public static interface DARWIN_AMD64
    extends DARWIN,
    AMD64 {
    }

    public static interface LINUX_AARCH64_BASE
    extends LINUX,
    AARCH64 {
    }

    public static interface LINUX_AMD64_BASE
    extends LINUX,
    AMD64 {
    }

    public static interface WINDOWS
    extends InternalPlatform.PLATFORM_JNI {
        @Override
        default public String getOS() {
            return WINDOWS.class.getSimpleName().toLowerCase();
        }
    }

    public static interface MACOS
    extends DARWIN {
        @Override
        default public String getOS() {
            return "darwin";
        }
    }

    public static interface IOS
    extends DARWIN {
        @Override
        default public String getOS() {
            return IOS.class.getSimpleName().toLowerCase();
        }
    }

    public static interface DARWIN
    extends InternalPlatform.PLATFORM_JNI {
    }

    public static interface ANDROID
    extends LINUX {
        @Override
        default public String getOS() {
            return ANDROID.class.getSimpleName().toLowerCase();
        }
    }

    public static interface LINUX
    extends InternalPlatform.PLATFORM_JNI {
        @Override
        default public String getOS() {
            return LINUX.class.getSimpleName().toLowerCase();
        }
    }

    public static interface AARCH64
    extends Platform {
        @Override
        default public String getArchitecture() {
            return "aarch64";
        }
    }

    public static interface AMD64
    extends Platform {
        @Override
        default public String getArchitecture() {
            return "amd64";
        }
    }
}

