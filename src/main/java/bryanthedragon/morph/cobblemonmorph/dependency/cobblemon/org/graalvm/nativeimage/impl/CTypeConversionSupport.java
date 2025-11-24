
package org.graalvm.nativeimage.impl;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.nativeimage.c.type.CTypeConversion;
import org.graalvm.word.PointerBase;
import org.graalvm.word.UnsignedWord;

public interface CTypeConversionSupport {
    public UnsignedWord toCString(CharSequence var1, Charset var2, CCharPointer var3, UnsignedWord var4);

    public UnsignedWord toCString(CharSequence var1, CCharPointer var2, UnsignedWord var3);

    public CTypeConversion.CCharPointerHolder toCString(CharSequence var1);

    public String toJavaString(CCharPointer var1);

    public String toJavaString(CCharPointer var1, UnsignedWord var2);

    public String toJavaString(CCharPointer var1, UnsignedWord var2, Charset var3);

    public String utf8ToJavaString(CCharPointer var1);

    public CTypeConversion.CCharPointerHolder toCBytes(byte[] var1);

    public ByteBuffer asByteBuffer(PointerBase var1, int var2);
}

