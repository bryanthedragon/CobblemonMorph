package org.graalvm.nativeimage.c.type;

import org.graalvm.nativeimage.c.struct.CPointerTo;
import org.graalvm.word.PointerBase;
import org.graalvm.word.SignedWord;

@CPointerTo(nameOfCType = "int")
public interface CIntPointer extends PointerBase {
   int read();

   int read(int index);

   int read(SignedWord index);

   void write(int value);

   void write(int index, int value);

   void write(SignedWord index, int value);

   CIntPointer addressOf(int index);

   CIntPointer addressOf(SignedWord index);
}
