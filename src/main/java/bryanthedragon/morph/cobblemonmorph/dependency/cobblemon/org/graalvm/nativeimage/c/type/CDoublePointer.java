package org.graalvm.nativeimage.c.type;

import org.graalvm.nativeimage.c.struct.CPointerTo;
import org.graalvm.word.PointerBase;
import org.graalvm.word.SignedWord;

@CPointerTo(nameOfCType = "double")
public interface CDoublePointer extends PointerBase {
   double read();

   double read(int index);

   double read(SignedWord index);

   void write(double value);

   void write(int index, double value);

   void write(SignedWord index, double value);

   CDoublePointer addressOf(int index);

   CDoublePointer addressOf(SignedWord index);
}
