
package org.graalvm.nativeimage;

import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.word.UnsignedWord;

public interface LogHandler {
    public void log(CCharPointer var1, UnsignedWord var2);

    public void flush();

    public void fatalError();
}

