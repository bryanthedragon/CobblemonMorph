package org.graalvm.nativeimage.c.type;

import org.graalvm.nativeimage.c.struct.CPointerTo;
import org.graalvm.word.PointerBase;
import org.graalvm.word.SignedWord;

@CPointerTo(nameOfCType = "char")
public interface CCharPointer extends PointerBase {
   byte read();

   byte read(int index);

   byte read(SignedWord index);

   void write(byte value);

   void write(int index, byte value);

   void write(SignedWord index, byte value);

   CCharPointer addressOf(int index);

   CCharPointer addressOf(SignedWord index);
}
