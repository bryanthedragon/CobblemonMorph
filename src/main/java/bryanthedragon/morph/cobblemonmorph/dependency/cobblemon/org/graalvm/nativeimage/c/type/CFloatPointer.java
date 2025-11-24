
package org.graalvm.nativeimage.c.type;

import org.graalvm.nativeimage.c.struct.CPointerTo;
import org.graalvm.word.PointerBase;
import org.graalvm.word.SignedWord;

@CPointerTo(nameOfCType="float")
public interface CFloatPointer
extends PointerBase {
    public float read();

    public float read(int var1);

    public float read(SignedWord var1);

    public void write(float var1);

    public void write(int var1, float var2);

    public void write(SignedWord var1, float var2);

    public CFloatPointer addressOf(int var1);

    public CFloatPointer addressOf(SignedWord var1);
}

