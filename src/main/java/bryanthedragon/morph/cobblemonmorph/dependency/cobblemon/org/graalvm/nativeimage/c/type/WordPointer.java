
package org.graalvm.nativeimage.c.type;

import org.graalvm.nativeimage.c.struct.CPointerTo;
import org.graalvm.word.PointerBase;
import org.graalvm.word.SignedWord;
import org.graalvm.word.WordBase;

@CPointerTo(nameOfCType="void*")
public interface WordPointer
extends PointerBase {
    public <T extends WordBase> T read();

    public <T extends WordBase> T read(int var1);

    public <T extends WordBase> T read(SignedWord var1);

    public void write(WordBase var1);

    public void write(int var1, WordBase var2);

    public void write(SignedWord var1, WordBase var2);

    public WordPointer addressOf(int var1);

    public WordPointer addressOf(SignedWord var1);
}

