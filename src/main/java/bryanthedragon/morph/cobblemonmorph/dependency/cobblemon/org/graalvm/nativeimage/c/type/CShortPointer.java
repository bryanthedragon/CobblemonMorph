
package org.graalvm.nativeimage.c.type;

import org.graalvm.nativeimage.c.struct.CPointerTo;
import org.graalvm.word.PointerBase;
import org.graalvm.word.SignedWord;

@CPointerTo(nameOfCType="short")
public interface CShortPointer
extends PointerBase {
    public short read();

    public short read(int var1);

    public short read(SignedWord var1);

    public void write(short var1);

    public void write(int var1, short var2);

    public void write(SignedWord var1, short var2);

    public CShortPointer addressOf(int var1);

    public CShortPointer addressOf(SignedWord var1);
}

