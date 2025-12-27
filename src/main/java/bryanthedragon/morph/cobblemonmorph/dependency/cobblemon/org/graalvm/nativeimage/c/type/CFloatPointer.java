package org.graalvm.nativeimage.c.type;

import org.graalvm.nativeimage.c.struct.CPointerTo;
import org.graalvm.word.PointerBase;
import org.graalvm.word.SignedWord;

@CPointerTo(nameOfCType = "float")
public interface CFloatPointer extends PointerBase {
   float read();

   float read(int index);

   float read(SignedWord index);

   void write(float value);

   void write(int index, float value);

   void write(SignedWord index, float value);

   CFloatPointer addressOf(int index);

   CFloatPointer addressOf(SignedWord index);
}
