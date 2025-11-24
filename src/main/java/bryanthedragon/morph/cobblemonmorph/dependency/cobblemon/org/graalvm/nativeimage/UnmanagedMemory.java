
package org.graalvm.nativeimage;

import org.graalvm.nativeimage.ImageSingletons;
import org.graalvm.nativeimage.impl.UnmanagedMemorySupport;
import org.graalvm.word.PointerBase;
import org.graalvm.word.UnsignedWord;
import org.graalvm.word.WordFactory;

public final class UnmanagedMemory {
    private UnmanagedMemory() {
    }

    public static <T extends PointerBase> T malloc(UnsignedWord size) {
        Object result = ImageSingletons.lookup(UnmanagedMemorySupport.class).malloc(size);
        if (result.isNull()) {
            throw new OutOfMemoryError("malloc of unmanaged memory");
        }
        return result;
    }

    public static <T extends PointerBase> T malloc(int size) {
        return UnmanagedMemory.malloc(WordFactory.unsigned(size));
    }

    public static <T extends PointerBase> T calloc(UnsignedWord size) {
        Object result = ImageSingletons.lookup(UnmanagedMemorySupport.class).calloc(size);
        if (result.isNull()) {
            throw new OutOfMemoryError("calloc of unmanaged memory");
        }
        return result;
    }

    public static <T extends PointerBase> T calloc(int size) {
        return UnmanagedMemory.calloc(WordFactory.unsigned(size));
    }

    public static <T extends PointerBase> T realloc(T ptr, UnsignedWord size) {
        T result = ImageSingletons.lookup(UnmanagedMemorySupport.class).realloc(ptr, size);
        if (result.isNull()) {
            throw new OutOfMemoryError("realloc of unmanaged memory");
        }
        return result;
    }

    public static void free(PointerBase ptr) {
        ImageSingletons.lookup(UnmanagedMemorySupport.class).free(ptr);
    }
}

