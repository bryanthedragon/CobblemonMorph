
package org.graalvm.nativeimage.c.type;

import org.graalvm.nativeimage.c.struct.CPointerTo;
import org.graalvm.nativeimage.c.type.CIntPointer;
import org.graalvm.word.PointerBase;
import org.graalvm.word.SignedWord;

@CPointerTo(value=CIntPointer.class)
public interface CIntPointerPointer
extends PointerBase {
    public CIntPointer read();

    public CIntPointer read(int var1);

    public CIntPointer read(SignedWord var1);

    public void write(CIntPointer var1);

    public void write(int var1, CIntPointer var2);

    public void write(SignedWord var1, CIntPointer var2);

    public CIntPointerPointer addressOf(int var1);

    public CIntPointerPointer addressOf(SignedWord var1);
}

