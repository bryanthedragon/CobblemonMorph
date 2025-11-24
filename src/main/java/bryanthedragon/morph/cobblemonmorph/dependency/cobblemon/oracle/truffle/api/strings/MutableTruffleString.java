
package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GeneratePackagePrivate;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.api.strings.AbstractTruffleString;
import com.oracle.truffle.api.strings.JCodings;
import com.oracle.truffle.api.strings.MutableTruffleStringFactory;
import com.oracle.truffle.api.strings.StringAttributes;
import com.oracle.truffle.api.strings.TSCodeRange;
import com.oracle.truffle.api.strings.TStringAccessor;
import com.oracle.truffle.api.strings.TStringGuards;
import com.oracle.truffle.api.strings.TStringInternalNodes;
import com.oracle.truffle.api.strings.TStringOps;
import com.oracle.truffle.api.strings.TruffleString;
import java.util.Arrays;

public final class MutableTruffleString
extends AbstractTruffleString {
    private int codePointLength = -1;
    private byte codeRange = (byte)TSCodeRange.getUnknown();

    private MutableTruffleString(Object data, int offset, int length, int stride, TruffleString.Encoding encoding) {
        super(data, offset, length, stride, encoding, 0);
        assert (data instanceof byte[] || data instanceof AbstractTruffleString.NativePointer);
        if (TruffleString.Encoding.isFixedWidth(encoding)) {
            this.codePointLength = encoding.isSupported() ? length : length / JCodings.getInstance().minLength(encoding.jCoding);
        }
    }

    private static MutableTruffleString create(Object data, int offset, int length, TruffleString.Encoding encoding) {
        MutableTruffleString string = new MutableTruffleString(data, offset, length, encoding.naturalStride, encoding);
        if (AbstractTruffleString.DEBUG_ALWAYS_CREATE_JAVA_STRING) {
            string.toJavaStringUncached();
        }
        return string;
    }

    int codePointLength() {
        return this.codePointLength;
    }

    int codeRange() {
        return this.codeRange;
    }

    void updateCachedAttributes(int newCodePointLength, int newCodeRange) {
        assert (newCodePointLength >= 0);
        assert (TSCodeRange.isCodeRange(newCodeRange));
        this.codePointLength = newCodePointLength;
        this.codeRange = (byte)newCodeRange;
    }

    void invalidateCachedAttributes() {
        if (!TruffleString.Encoding.isFixedWidth(this.encoding())) {
            this.codePointLength = -1;
        }
        this.codeRange = (byte)TSCodeRange.getUnknown();
        this.hashCode = 0;
        if (this.data() instanceof AbstractTruffleString.NativePointer) {
            ((AbstractTruffleString.NativePointer)this.data()).invalidateCachedByteArray();
        }
    }

    public void notifyExternalMutation() {
        this.invalidateCachedAttributes();
    }

    @CompilerDirectives.TruffleBoundary
    public static MutableTruffleString fromByteArrayUncached(byte[] value2, int byteOffset, int byteLength, TruffleString.Encoding encoding, boolean copy) {
        return FromByteArrayNode.getUncached().execute(value2, byteOffset, byteLength, encoding, copy);
    }

    @CompilerDirectives.TruffleBoundary
    public static MutableTruffleString fromNativePointerUncached(Object pointerObject, int byteOffset, int byteLength, TruffleString.Encoding encoding, boolean copy) {
        return FromNativePointerNode.getUncached().execute(pointerObject, byteOffset, byteLength, encoding, copy);
    }

    @CompilerDirectives.TruffleBoundary
    public void writeByteUncached(int byteIndex, byte value2, TruffleString.Encoding expectedEncoding) {
        WriteByteNode.getUncached().execute(this, byteIndex, value2, expectedEncoding);
    }

    @CompilerDirectives.TruffleBoundary
    public MutableTruffleString concatUncached(AbstractTruffleString b, TruffleString.Encoding expectedEncoding) {
        return ConcatNode.getUncached().execute(this, b, expectedEncoding);
    }

    @CompilerDirectives.TruffleBoundary
    public MutableTruffleString substringUncached(int byteOffset, int byteLength, TruffleString.Encoding expectedEncoding) {
        return SubstringNode.getUncached().execute(this, byteOffset, byteLength, expectedEncoding);
    }

    @CompilerDirectives.TruffleBoundary
    public MutableTruffleString substringByteIndexUncached(int byteOffset, int byteLength, TruffleString.Encoding expectedEncoding) {
        return SubstringByteIndexNode.getUncached().execute(this, byteOffset, byteLength, expectedEncoding);
    }

    static MutableTruffleString createCopying(AbstractTruffleString a, TruffleString.Encoding encoding, TruffleString.CopyToByteArrayNode copyToByteArrayNode) {
        return MutableTruffleString.createCopying(a, encoding, encoding, a.byteLength(encoding), copyToByteArrayNode);
    }

    static MutableTruffleString createCopying(AbstractTruffleString a, TruffleString.Encoding expectedEncoding, TruffleString.Encoding targetEncoding, TruffleString.CopyToByteArrayNode copyToByteArrayNode) {
        int byteLength = a.byteLength(expectedEncoding);
        MutableTruffleString.checkByteLength(byteLength, targetEncoding);
        return MutableTruffleString.createCopying(a, expectedEncoding, targetEncoding, byteLength, copyToByteArrayNode);
    }

    static MutableTruffleString createCopying(AbstractTruffleString a, TruffleString.Encoding expectedEncoding, TruffleString.Encoding targetEncoding, int byteLength, TruffleString.CopyToByteArrayNode copyToByteArrayNode) {
        byte[] array = new byte[byteLength];
        copyToByteArrayNode.execute(a, 0, array, 0, byteLength, expectedEncoding);
        return MutableTruffleString.create(array, 0, byteLength >> targetEncoding.naturalStride, targetEncoding);
    }

    @GenerateUncached
    static abstract class CalcLazyAttributesNode
    extends Node {
        CalcLazyAttributesNode() {
        }

        abstract void execute(MutableTruffleString var1);

        @Specialization
        void calc(MutableTruffleString a, @Cached(value="createClassProfile()") ValueProfile dataClassProfile, @Cached ConditionProfile asciiBytesLatinProfile, @Cached ConditionProfile utf8Profile, @Cached ConditionProfile utf8BrokenProfile, @Cached ConditionProfile utf16Profile, @Cached ConditionProfile utf16S0Profile, @Cached ConditionProfile utf32Profile, @Cached ConditionProfile utf32S0Profile, @Cached ConditionProfile utf32S1Profile, @Cached ConditionProfile exoticMaterializeNativeProfile, @Cached ConditionProfile exoticValidProfile, @Cached ConditionProfile exoticFixedWidthProfile) {
            int codePointLength;
            int codeRange;
            Object data = dataClassProfile.profile(a.data());
            int encoding = a.encoding();
            int offset = a.offset();
            int length = a.length();
            if (utf16Profile.profile(TStringGuards.isUTF16(encoding))) {
                if (utf16S0Profile.profile(TStringGuards.isStride0(a))) {
                    codeRange = TStringOps.calcStringAttributesLatin1(this, data, offset, length);
                    codePointLength = length;
                } else {
                    assert (TStringGuards.isStride1(a));
                    long attrs = TStringOps.calcStringAttributesUTF16(this, data, offset, length, false);
                    codePointLength = StringAttributes.getCodePointLength(attrs);
                    codeRange = StringAttributes.getCodeRange(attrs);
                }
            } else if (utf32Profile.profile(TStringGuards.isUTF32(encoding))) {
                if (utf32S0Profile.profile(TStringGuards.isStride0(a))) {
                    codeRange = TStringOps.calcStringAttributesLatin1(this, data, offset, length);
                } else if (utf32S1Profile.profile(TStringGuards.isStride1(a))) {
                    codeRange = TStringOps.calcStringAttributesBMP(this, data, offset, length);
                } else {
                    assert (TStringGuards.isStride2(a));
                    codeRange = TStringOps.calcStringAttributesUTF32(this, data, offset, length);
                }
                codePointLength = length;
            } else if (utf8Profile.profile(TStringGuards.isUTF8(encoding))) {
                long attrs = TStringOps.calcStringAttributesUTF8(this, data, offset, length, false, false, utf8BrokenProfile);
                codeRange = StringAttributes.getCodeRange(attrs);
                codePointLength = StringAttributes.getCodePointLength(attrs);
            } else if (asciiBytesLatinProfile.profile(TStringGuards.isAsciiBytesOrLatin1(encoding))) {
                int cr = TStringOps.calcStringAttributesLatin1(this, data, offset, length);
                codeRange = TStringGuards.is8Bit(cr) ? TSCodeRange.asciiLatinBytesNonAsciiCodeRange(encoding) : cr;
                codePointLength = length;
            } else {
                if (data instanceof AbstractTruffleString.NativePointer) {
                    ((AbstractTruffleString.NativePointer)data).materializeByteArray(a, exoticMaterializeNativeProfile);
                }
                long attrs = JCodings.getInstance().calcStringAttributes(this, data, offset, length, TruffleString.Encoding.get(encoding), exoticValidProfile, exoticFixedWidthProfile);
                codeRange = StringAttributes.getCodeRange(attrs);
                codePointLength = StringAttributes.getCodePointLength(attrs);
            }
            a.updateCachedAttributes(codePointLength, codeRange);
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class ForceEncodingNode
    extends Node {
        ForceEncodingNode() {
        }

        public abstract MutableTruffleString execute(AbstractTruffleString var1, TruffleString.Encoding var2, TruffleString.Encoding var3);

        @Specialization(guards={"a.isCompatibleTo(targetEncoding)"})
        static MutableTruffleString compatible(MutableTruffleString a, TruffleString.Encoding expectedEncoding, TruffleString.Encoding targetEncoding) {
            a.checkEncoding(expectedEncoding);
            return a;
        }

        @Specialization(guards={"!a.isCompatibleTo(targetEncoding) || a.isImmutable()"})
        static MutableTruffleString reinterpret(AbstractTruffleString a, TruffleString.Encoding expectedEncoding, TruffleString.Encoding targetEncoding, @Cached TruffleString.CopyToByteArrayNode copyToByteArrayNode) {
            a.checkEncoding(expectedEncoding);
            return MutableTruffleString.createCopying(a, expectedEncoding, targetEncoding, copyToByteArrayNode);
        }

        public static ForceEncodingNode create() {
            return MutableTruffleStringFactory.ForceEncodingNodeGen.create();
        }

        public static ForceEncodingNode getUncached() {
            return MutableTruffleStringFactory.ForceEncodingNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class SwitchEncodingNode
    extends Node {
        SwitchEncodingNode() {
        }

        public abstract MutableTruffleString execute(AbstractTruffleString var1, TruffleString.Encoding var2);

        @Specialization(guards={"a.isCompatibleTo(encoding)"})
        static MutableTruffleString compatibleMutable(MutableTruffleString a, TruffleString.Encoding encoding) {
            return a;
        }

        @Specialization(guards={"!a.isCompatibleTo(encoding) || a.isImmutable()"})
        static MutableTruffleString transcodeAndCopy(AbstractTruffleString a, TruffleString.Encoding encoding, @Cached TruffleString.SwitchEncodingNode switchEncodingNode, @Cached AsMutableTruffleStringNode asMutableTruffleStringNode) {
            TruffleString switched = switchEncodingNode.execute(a, encoding);
            return asMutableTruffleStringNode.execute(switched, encoding);
        }

        public static SwitchEncodingNode create() {
            return MutableTruffleStringFactory.SwitchEncodingNodeGen.create();
        }

        public static SwitchEncodingNode getUncached() {
            return MutableTruffleStringFactory.SwitchEncodingNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class SubstringByteIndexNode
    extends Node {
        SubstringByteIndexNode() {
        }

        public abstract MutableTruffleString execute(AbstractTruffleString var1, int var2, int var3, TruffleString.Encoding var4);

        @Specialization
        static MutableTruffleString substringByteIndex(AbstractTruffleString a, int byteOffset, int byteLength, TruffleString.Encoding expectedEncoding, @Cached TruffleString.CopyToByteArrayNode copyToByteArrayNode) {
            return SubstringByteIndexNode.createSubstring(a, byteOffset, byteLength, expectedEncoding, copyToByteArrayNode);
        }

        static MutableTruffleString createSubstring(AbstractTruffleString a, int byteOffset, int byteLength, TruffleString.Encoding expectedEncoding, TruffleString.CopyToByteArrayNode copyToByteArrayNode) {
            a.checkEncoding(expectedEncoding);
            AbstractTruffleString.checkByteLength(byteLength, expectedEncoding);
            a.boundsCheckRegionRaw(AbstractTruffleString.rawIndex(byteOffset, expectedEncoding), AbstractTruffleString.rawIndex(byteLength, expectedEncoding));
            byte[] array = new byte[byteLength];
            copyToByteArrayNode.execute(a, byteOffset, array, 0, byteLength, expectedEncoding);
            return MutableTruffleString.create(array, 0, byteLength >> expectedEncoding.naturalStride, expectedEncoding);
        }

        public static SubstringByteIndexNode create() {
            return MutableTruffleStringFactory.SubstringByteIndexNodeGen.create();
        }

        public static SubstringByteIndexNode getUncached() {
            return MutableTruffleStringFactory.SubstringByteIndexNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class SubstringNode
    extends Node {
        SubstringNode() {
        }

        public abstract MutableTruffleString execute(AbstractTruffleString var1, int var2, int var3, TruffleString.Encoding var4);

        @Specialization
        static MutableTruffleString substring(AbstractTruffleString a, int fromIndex, int length, TruffleString.Encoding expectedEncoding, @Cached TruffleString.ToIndexableNode toIndexableNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode, @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode, @Cached TStringInternalNodes.CodePointIndexToRawNode translateIndexNode, @Cached TruffleString.CopyToByteArrayNode copyToByteArrayNode) {
            a.checkEncoding(expectedEncoding);
            a.boundsCheckRegion(fromIndex, length, getCodePointLengthNode);
            Object arrayA = toIndexableNode.execute(a, a.data());
            int codeRangeA = getCodeRangeANode.execute(a);
            int fromIndexRaw = translateIndexNode.execute(a, arrayA, codeRangeA, expectedEncoding, 0, fromIndex, length == 0);
            int lengthRaw = translateIndexNode.execute(a, arrayA, codeRangeA, expectedEncoding, fromIndexRaw, length, true);
            byte stride = expectedEncoding.naturalStride;
            return SubstringByteIndexNode.createSubstring(a, fromIndexRaw << stride, lengthRaw << stride, expectedEncoding, copyToByteArrayNode);
        }

        public static SubstringNode create() {
            return MutableTruffleStringFactory.SubstringNodeGen.create();
        }

        public static SubstringNode getUncached() {
            return MutableTruffleStringFactory.SubstringNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class ConcatNode
    extends Node {
        ConcatNode() {
        }

        public abstract MutableTruffleString execute(AbstractTruffleString var1, AbstractTruffleString var2, TruffleString.Encoding var3);

        @Specialization
        static MutableTruffleString concat(AbstractTruffleString a, AbstractTruffleString b, TruffleString.Encoding expectedEncoding, @Cached TruffleString.ToIndexableNode toIndexableNodeA, @Cached TruffleString.ToIndexableNode toIndexableNodeB, @Cached TStringInternalNodes.ConcatMaterializeBytesNode materializeBytesNode, @Cached BranchProfile outOfMemoryProfile) {
            a.checkEncoding(expectedEncoding);
            b.checkEncoding(expectedEncoding);
            int length = TruffleString.ConcatNode.addByteLengths(a, b, expectedEncoding.naturalStride, outOfMemoryProfile);
            int offset = 0;
            byte[] array = materializeBytesNode.execute(a, toIndexableNodeA.execute(a, a.data()), b, toIndexableNodeB.execute(b, b.data()), expectedEncoding, length, expectedEncoding.naturalStride);
            return MutableTruffleString.create(array, offset, length, expectedEncoding);
        }

        public static ConcatNode create() {
            return MutableTruffleStringFactory.ConcatNodeGen.create();
        }

        public static ConcatNode getUncached() {
            return MutableTruffleStringFactory.ConcatNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class WriteByteNode
    extends Node {
        WriteByteNode() {
        }

        public abstract void execute(MutableTruffleString var1, int var2, byte var3, TruffleString.Encoding var4);

        @Specialization
        static void writeByte(MutableTruffleString a, int byteIndex, byte value2, TruffleString.Encoding expectedEncoding) {
            a.checkEncoding(expectedEncoding);
            int byteLength = a.length() << a.stride();
            TruffleString.boundsCheckI(byteIndex, byteLength);
            TStringOps.writeS0(a.data(), a.offset(), byteLength, byteIndex, value2);
            if (!TSCodeRange.is7Bit(a.codeRange) || value2 < 0) {
                a.invalidateCachedAttributes();
            }
        }

        public static WriteByteNode create() {
            return MutableTruffleStringFactory.WriteByteNodeGen.create();
        }

        public static WriteByteNode getUncached() {
            return MutableTruffleStringFactory.WriteByteNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class, TStringAccessor.class})
    @GenerateUncached
    public static abstract class AsManagedNode
    extends Node {
        AsManagedNode() {
        }

        public abstract MutableTruffleString execute(AbstractTruffleString var1, TruffleString.Encoding var2);

        @Specialization(guards={"!a.isNative()"})
        static MutableTruffleString mutable(MutableTruffleString a, TruffleString.Encoding expectedEncoding) {
            a.checkEncoding(expectedEncoding);
            return a;
        }

        @Specialization(guards={"a.isNative() || a.isImmutable()"})
        static MutableTruffleString fromTruffleString(AbstractTruffleString a, TruffleString.Encoding expectedEncoding, @Cached TruffleString.CopyToByteArrayNode copyToByteArrayNode) {
            return MutableTruffleString.createCopying(a, expectedEncoding, copyToByteArrayNode);
        }

        public static AsManagedNode create() {
            return MutableTruffleStringFactory.AsManagedNodeGen.create();
        }

        public static AsManagedNode getUncached() {
            return MutableTruffleStringFactory.AsManagedNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class, TStringAccessor.class})
    @GenerateUncached
    public static abstract class AsMutableTruffleStringNode
    extends Node {
        AsMutableTruffleStringNode() {
        }

        public abstract MutableTruffleString execute(AbstractTruffleString var1, TruffleString.Encoding var2);

        @Specialization
        static MutableTruffleString mutable(MutableTruffleString a, TruffleString.Encoding expectedEncoding) {
            a.checkEncoding(expectedEncoding);
            return a;
        }

        @Specialization
        static MutableTruffleString fromTruffleString(TruffleString a, TruffleString.Encoding expectedEncoding, @Cached TruffleString.CopyToByteArrayNode copyToByteArrayNode) {
            return MutableTruffleString.createCopying(a, expectedEncoding, copyToByteArrayNode);
        }

        public static AsMutableTruffleStringNode create() {
            return MutableTruffleStringFactory.AsMutableTruffleStringNodeGen.create();
        }

        public static AsMutableTruffleStringNode getUncached() {
            return MutableTruffleStringFactory.AsMutableTruffleStringNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class, TStringAccessor.class})
    @GenerateUncached
    public static abstract class FromNativePointerNode
    extends Node {
        FromNativePointerNode() {
        }

        public abstract MutableTruffleString execute(Object var1, int var2, int var3, TruffleString.Encoding var4, boolean var5);

        @Specialization
        MutableTruffleString fromNativePointer(Object pointerObject, int byteOffset, int byteLength, TruffleString.Encoding enc, boolean copy, @Cached(value="createInteropLibrary()", uncached="getUncachedInteropLibrary()") Node interopLibrary) {
            int offset;
            Object array;
            AbstractTruffleString.checkByteLength(byteLength, enc);
            AbstractTruffleString.NativePointer nativePointer = AbstractTruffleString.NativePointer.create(this, pointerObject, interopLibrary, byteOffset);
            if (copy) {
                array = TStringOps.arraycopyOfWithStride(this, nativePointer, byteOffset, byteLength, 0, byteLength, 0);
                offset = 0;
            } else {
                array = nativePointer;
                offset = byteOffset;
            }
            return MutableTruffleString.create(array, offset, byteLength >> enc.naturalStride, enc);
        }

        public static FromNativePointerNode create() {
            return MutableTruffleStringFactory.FromNativePointerNodeGen.create();
        }

        public static FromNativePointerNode getUncached() {
            return MutableTruffleStringFactory.FromNativePointerNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class FromByteArrayNode
    extends Node {
        FromByteArrayNode() {
        }

        public abstract MutableTruffleString execute(byte[] var1, int var2, int var3, TruffleString.Encoding var4, boolean var5);

        @Specialization
        static MutableTruffleString fromByteArray(byte[] value2, int byteOffset, int byteLength, TruffleString.Encoding enc, boolean copy) {
            int offset;
            byte[] array;
            AbstractTruffleString.checkArrayRange(value2, byteOffset, byteLength);
            AbstractTruffleString.checkByteLength(byteLength, enc);
            if (copy) {
                array = Arrays.copyOfRange(value2, byteOffset, byteOffset + byteLength);
                offset = 0;
            } else {
                array = value2;
                offset = byteOffset;
            }
            return MutableTruffleString.create(array, offset, byteLength >> enc.naturalStride, enc);
        }

        public static FromByteArrayNode create() {
            return MutableTruffleStringFactory.FromByteArrayNodeGen.create();
        }

        public static FromByteArrayNode getUncached() {
            return MutableTruffleStringFactory.FromByteArrayNodeGen.getUncached();
        }
    }
}

