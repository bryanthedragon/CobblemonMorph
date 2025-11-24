
package org.graalvm.nativeimage.c.type;

import org.graalvm.nativeimage.c.struct.CPointerTo;
import org.graalvm.word.PointerBase;
import org.graalvm.word.SignedWord;

@CPointerTo(nameOfCType="char")
public interface CCharPointer
extends PointerBase {
    public byte read();

    public byte read(int var1);

    public byte read(SignedWord var1);

    public void write(byte var1);

    public void write(int var1, byte var2);

    public void write(SignedWord var1, byte var2);

    public CCharPointer addressOf(int var1);

    public CCharPointer addressOf(SignedWord var1);
}

