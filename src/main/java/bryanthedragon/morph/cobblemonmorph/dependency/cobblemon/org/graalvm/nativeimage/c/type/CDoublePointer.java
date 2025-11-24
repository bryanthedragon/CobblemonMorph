
package org.graalvm.nativeimage.c.type;

import org.graalvm.nativeimage.c.struct.CPointerTo;
import org.graalvm.word.PointerBase;
import org.graalvm.word.SignedWord;

@CPointerTo(nameOfCType="double")
public interface CDoublePointer
extends PointerBase {
    public double read();

    public double read(int var1);

    public double read(SignedWord var1);

    public void write(double var1);

    public void write(int var1, double var2);

    public void write(SignedWord var1, double var2);

    public CDoublePointer addressOf(int var1);

    public CDoublePointer addressOf(SignedWord var1);
}

