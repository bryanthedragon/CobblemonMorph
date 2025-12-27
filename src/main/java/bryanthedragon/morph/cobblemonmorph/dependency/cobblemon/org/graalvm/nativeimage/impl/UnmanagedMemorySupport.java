package org.graalvm.nativeimage.impl;

import org.graalvm.word.PointerBase;
import org.graalvm.word.UnsignedWord;

public interface UnmanagedMemorySupport {
   <T extends PointerBase> T malloc(UnsignedWord size);

   <T extends PointerBase> T calloc(UnsignedWord size);

   <T extends PointerBase> T realloc(T ptr, UnsignedWord size);

   void free(PointerBase ptr);
}
