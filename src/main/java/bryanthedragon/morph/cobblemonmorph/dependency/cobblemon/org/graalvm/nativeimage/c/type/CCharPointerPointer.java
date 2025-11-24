
package org.graalvm.nativeimage.c.type;

import org.graalvm.nativeimage.c.struct.CPointerTo;
import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.word.PointerBase;
import org.graalvm.word.SignedWord;

@CPointerTo(value=CCharPointer.class)
public interface CCharPointerPointer
extends PointerBase {
    public CCharPointer read();

    public CCharPointer read(int var1);

    public CCharPointer read(SignedWord var1);

    public void write(CCharPointer var1);

    public void write(int var1, CCharPointer var2);

    public void write(SignedWord var1, CCharPointer var2);

    public CCharPointerPointer addressOf(int var1);

    public CCharPointerPointer addressOf(SignedWord var1);
}

