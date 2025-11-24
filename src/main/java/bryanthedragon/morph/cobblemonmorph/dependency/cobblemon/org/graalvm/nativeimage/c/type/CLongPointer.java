
package org.graalvm.nativeimage.c.type;

import org.graalvm.nativeimage.c.struct.CPointerTo;
import org.graalvm.word.PointerBase;
import org.graalvm.word.SignedWord;

@CPointerTo(nameOfCType="long long")
public interface CLongPointer
extends PointerBase {
    public long read();

    public long read(int var1);

    public long read(SignedWord var1);

    public void write(long var1);

    public void write(int var1, long var2);

    public void write(SignedWord var1, long var2);

    public CLongPointer addressOf(int var1);

    public CLongPointer addressOf(SignedWord var1);
}

