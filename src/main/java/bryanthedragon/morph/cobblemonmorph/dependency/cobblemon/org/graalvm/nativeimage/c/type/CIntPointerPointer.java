package org.graalvm.nativeimage.c.type;

import org.graalvm.nativeimage.c.struct.CPointerTo;
import org.graalvm.word.PointerBase;
import org.graalvm.word.SignedWord;

@CPointerTo(CIntPointer.class)
public interface CIntPointerPointer extends PointerBase {
   CIntPointer read();

   CIntPointer read(int index);

   CIntPointer read(SignedWord index);

   void write(CIntPointer value);

   void write(int index, CIntPointer value);

   void write(SignedWord index, CIntPointer value);

   CIntPointerPointer addressOf(int index);

   CIntPointerPointer addressOf(SignedWord index);
}
