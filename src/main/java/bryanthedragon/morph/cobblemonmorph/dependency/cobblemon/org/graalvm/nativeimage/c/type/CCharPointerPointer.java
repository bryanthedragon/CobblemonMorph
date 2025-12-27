package org.graalvm.nativeimage.c.type;

import org.graalvm.nativeimage.c.struct.CPointerTo;
import org.graalvm.word.PointerBase;
import org.graalvm.word.SignedWord;

@CPointerTo(CCharPointer.class)
public interface CCharPointerPointer extends PointerBase {
   CCharPointer read();

   CCharPointer read(int index);

   CCharPointer read(SignedWord index);

   void write(CCharPointer value);

   void write(int index, CCharPointer value);

   void write(SignedWord index, CCharPointer value);

   CCharPointerPointer addressOf(int index);

   CCharPointerPointer addressOf(SignedWord index);
}
