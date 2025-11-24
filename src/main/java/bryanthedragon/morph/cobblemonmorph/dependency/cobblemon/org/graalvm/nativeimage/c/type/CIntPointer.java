
package org.graalvm.nativeimage.c.type;

import org.graalvm.nativeimage.c.struct.CPointerTo;
import org.graalvm.word.PointerBase;
import org.graalvm.word.SignedWord;

@CPointerTo(nameOfCType="int")
public interface CIntPointer
extends PointerBase {
    public int read();

    public int read(int var1);

    public int read(SignedWord var1);

    public void write(int var1);

    public void write(int var1, int var2);

    public void write(SignedWord var1, int var2);

    public CIntPointer addressOf(int var1);

    public CIntPointer addressOf(SignedWord var1);
}

