
package org.graalvm.nativeimage.impl;

import org.graalvm.word.PointerBase;
import org.graalvm.word.UnsignedWord;

public interface UnmanagedMemorySupport {
    public <T extends PointerBase> T malloc(UnsignedWord var1);

    public <T extends PointerBase> T calloc(UnsignedWord var1);

    public <T extends PointerBase> T realloc(T var1, UnsignedWord var2);

    public void free(PointerBase var1);
}

