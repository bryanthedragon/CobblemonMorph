package org.graalvm.nativeimage.c.type;

import org.graalvm.nativeimage.c.struct.CPointerTo;
import org.graalvm.word.PointerBase;

@CPointerTo(nameOfCType = "void")
public interface VoidPointer extends PointerBase {
}
