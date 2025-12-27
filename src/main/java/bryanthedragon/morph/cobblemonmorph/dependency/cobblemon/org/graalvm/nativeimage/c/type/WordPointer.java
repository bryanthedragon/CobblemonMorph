package org.graalvm.nativeimage.c.type;

import org.graalvm.nativeimage.c.struct.CPointerTo;
import org.graalvm.word.PointerBase;
import org.graalvm.word.SignedWord;
import org.graalvm.word.WordBase;

@CPointerTo(nameOfCType = "void*")
public interface WordPointer extends PointerBase {
   <T extends WordBase> T read();

   <T extends WordBase> T read(int index);

   <T extends WordBase> T read(SignedWord index);

   void write(WordBase value);

   void write(int index, WordBase value);

   void write(SignedWord index, WordBase value);

   WordPointer addressOf(int index);

   WordPointer addressOf(SignedWord index);
}
