package org.graalvm.nativeimage.impl;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.nativeimage.c.type.CTypeConversion;
import org.graalvm.word.PointerBase;
import org.graalvm.word.UnsignedWord;

public interface CTypeConversionSupport {
   UnsignedWord toCString(CharSequence javaString, Charset charset, CCharPointer buffer, UnsignedWord bufferSize);

   UnsignedWord toCString(CharSequence javaString, CCharPointer buffer, UnsignedWord bufferSize);

   CTypeConversion.CCharPointerHolder toCString(CharSequence javaString);

   String toJavaString(CCharPointer cString);

   String toJavaString(CCharPointer cString, UnsignedWord length);

   String toJavaString(CCharPointer cString, UnsignedWord length, Charset charset);

   String utf8ToJavaString(CCharPointer utf8String);

   CTypeConversion.CCharPointerHolder toCBytes(byte[] bytes);

   ByteBuffer asByteBuffer(PointerBase address, int size);
}
