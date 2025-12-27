package org.graalvm.nativeimage;

import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.word.UnsignedWord;

public interface LogHandler {
   void log(CCharPointer bytes, UnsignedWord length);

   void flush();

   void fatalError();
}
