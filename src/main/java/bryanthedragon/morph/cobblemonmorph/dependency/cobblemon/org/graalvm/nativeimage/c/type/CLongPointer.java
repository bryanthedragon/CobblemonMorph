package org.graalvm.nativeimage.c.type;

import org.graalvm.nativeimage.c.struct.CPointerTo;
import org.graalvm.word.PointerBase;
import org.graalvm.word.SignedWord;

@CPointerTo(nameOfCType = "long long")
public interface CLongPointer extends PointerBase {
   long read();

   long read(int index);

   long read(SignedWord index);

   void write(long value);

   void write(int index, long value);

   void write(SignedWord index, long value);

   CLongPointer addressOf(int index);

   CLongPointer addressOf(SignedWord index);
}
