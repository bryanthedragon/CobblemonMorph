package org.graalvm.nativeimage.c.type;

import org.graalvm.nativeimage.c.struct.CPointerTo;
import org.graalvm.word.PointerBase;
import org.graalvm.word.SignedWord;

@CPointerTo(nameOfCType = "short")
public interface CShortPointer extends PointerBase {
   short read();

   short read(int index);

   short read(SignedWord index);

   void write(short value);

   void write(int index, short value);

   void write(SignedWord index, short value);

   CShortPointer addressOf(int index);

   CShortPointer addressOf(SignedWord index);
}
