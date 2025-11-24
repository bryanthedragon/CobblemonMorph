
package org.graalvm.nativeimage.c.type;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.graalvm.nativeimage.ImageSingletons;
import org.graalvm.nativeimage.PinnedObject;
import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.nativeimage.c.type.CCharPointerPointer;
import org.graalvm.nativeimage.impl.CTypeConversionSupport;
import org.graalvm.word.PointerBase;
import org.graalvm.word.UnsignedWord;
import org.graalvm.word.WordFactory;

public final class CTypeConversion {
    private CTypeConversion() {
    }

    public static CCharPointerHolder toCString(CharSequence javaString) {
        return ImageSingletons.lookup(CTypeConversionSupport.class).toCString(javaString);
    }

    public static UnsignedWord toCString(CharSequence javaString, CCharPointer buffer, UnsignedWord bufferSize) {
        return ImageSingletons.lookup(CTypeConversionSupport.class).toCString(javaString, buffer, bufferSize);
    }

    public static UnsignedWord toCString(CharSequence javaString, Charset charset, CCharPointer buffer, UnsignedWord bufferSize) {
        return ImageSingletons.lookup(CTypeConversionSupport.class).toCString(javaString, charset, buffer, bufferSize);
    }

    public static String toJavaString(CCharPointer cString) {
        return ImageSingletons.lookup(CTypeConversionSupport.class).toJavaString(cString);
    }

    public static String toJavaString(CCharPointer cString, UnsignedWord length) {
        return ImageSingletons.lookup(CTypeConversionSupport.class).toJavaString(cString, length);
    }

    public static String toJavaString(CCharPointer cString, UnsignedWord length, Charset charset) {
        return ImageSingletons.lookup(CTypeConversionSupport.class).toJavaString(cString, length, charset);
    }

    public static String utf8ToJavaString(CCharPointer utf8String) {
        return ImageSingletons.lookup(CTypeConversionSupport.class).utf8ToJavaString(utf8String);
    }

    public static byte toCBoolean(boolean value2) {
        return (byte)(value2 ? 1 : 0);
    }

    public static boolean toBoolean(int value2) {
        return value2 != 0;
    }

    public static boolean toBoolean(PointerBase pointer) {
        return pointer.isNonNull();
    }

    public static CCharPointerPointerHolder toCStrings(CharSequence[] javaStrings) {
        return new CCharPointerPointerHolder(javaStrings);
    }

    public static ByteBuffer asByteBuffer(PointerBase address, int size) {
        return ImageSingletons.lookup(CTypeConversionSupport.class).asByteBuffer(address, size);
    }

    public static CCharPointerHolder toCBytes(byte[] bytes) {
        return ImageSingletons.lookup(CTypeConversionSupport.class).toCBytes(bytes);
    }

    public static final class CCharPointerPointerHolder
    implements AutoCloseable {
        private final CCharPointerHolder[] ccpHolderArray;
        private final PinnedObject pinnedCCPArray;

        private CCharPointerPointerHolder(CharSequence[] csArray) {
            this.ccpHolderArray = new CCharPointerHolder[csArray.length + 1];
            CCharPointer[] ccpArray = new CCharPointer[csArray.length + 1];
            for (int i = 0; i < csArray.length; ++i) {
                this.ccpHolderArray[i] = CTypeConversion.toCString(csArray[i]);
                ccpArray[i] = this.ccpHolderArray[i].get();
            }
            ccpArray[csArray.length] = (CCharPointer)WordFactory.nullPointer();
            this.pinnedCCPArray = PinnedObject.create(ccpArray);
        }

        public CCharPointerPointer get() {
            return (CCharPointerPointer)this.pinnedCCPArray.addressOfArrayElement(0);
        }

        @Override
        public void close() {
            for (int i = 0; i < this.ccpHolderArray.length - 1; ++i) {
                this.ccpHolderArray[i].close();
            }
            this.pinnedCCPArray.close();
        }
    }

    public static interface CCharPointerHolder
    extends AutoCloseable {
        public CCharPointer get();

        @Override
        public void close();
    }
}

