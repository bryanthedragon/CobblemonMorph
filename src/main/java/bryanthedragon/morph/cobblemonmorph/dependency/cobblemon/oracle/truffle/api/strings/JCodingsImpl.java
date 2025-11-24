
package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.AbstractTruffleString;
import com.oracle.truffle.api.strings.Encodings;
import com.oracle.truffle.api.strings.InternalErrors;
import com.oracle.truffle.api.strings.JCodings;
import com.oracle.truffle.api.strings.StringAttributes;
import com.oracle.truffle.api.strings.TSCodeRange;
import com.oracle.truffle.api.strings.TStringConstants;
import com.oracle.truffle.api.strings.TStringGuards;
import com.oracle.truffle.api.strings.TStringInternalNodes;
import com.oracle.truffle.api.strings.TStringOps;
import com.oracle.truffle.api.strings.TruffleString;
import java.util.Arrays;
import org.graalvm.collections.EconomicMap;
import org.graalvm.shadowed.org.jcodings.Encoding;
import org.graalvm.shadowed.org.jcodings.EncodingDB;
import org.graalvm.shadowed.org.jcodings.Ptr;
import org.graalvm.shadowed.org.jcodings.transcode.EConv;
import org.graalvm.shadowed.org.jcodings.transcode.EConvResult;
import org.graalvm.shadowed.org.jcodings.transcode.TranscoderDB;
import org.graalvm.shadowed.org.jcodings.util.CaseInsensitiveBytesHash;

final class JCodingsImpl
implements JCodings {
    private static final int MAX_J_CODINGS_INDEX_VALUE = 127;
    @CompilerDirectives.CompilationFinal
    private static final EconomicMap<String, EncodingWrapper> J_CODINGS_MAP;
    private static final byte[] CONVERSION_REPLACEMENT;
    private static final byte[] CONVERSION_REPLACEMENT_UTF_8;
    private static final byte[] CONVERSION_REPLACEMENT_UTF_16;
    private static final byte[] CONVERSION_REPLACEMENT_UTF_32;

    JCodingsImpl() {
    }

    @CompilerDirectives.TruffleBoundary
    private static EconomicMap<String, EncodingWrapper> createJCodingsMap() {
        CaseInsensitiveBytesHash<EncodingDB.Entry> encodings = EncodingDB.getEncodings();
        if (encodings.size() > 127) {
            throw new RuntimeException(String.format("Assumption broken: org.graalvm.shadowed.org.jcodings has more than %d encodings (actual: %d)!", 127, encodings.size()));
        }
        EconomicMap<String, EncodingWrapper> allEncodings = EconomicMap.create(encodings.size());
        for (EncodingDB.Entry entry : encodings) {
            Encoding enc = entry.getEncoding();
            int i = enc.getIndex();
            if (i < 0 || i >= encodings.size()) {
                throw new RuntimeException(String.format("Assumption broken: index of org.graalvm.shadowed.org.jcodings encoding \"%s\" is greater than number of encodings (index: %d, number of encodings: %d)!", enc, i, encodings.size()));
            }
            allEncodings.put(JCodingsImpl.toEnumName(enc.toString()), new EncodingWrapper(enc));
        }
        return allEncodings;
    }

    @Override
    public JCodings.Encoding get(String encodingName) {
        return (JCodings.Encoding)J_CODINGS_MAP.get(encodingName);
    }

    @Override
    public JCodings.Encoding get(TruffleString.Encoding encoding) {
        return encoding.jCoding;
    }

    @Override
    public String name(JCodings.Encoding jCoding) {
        return JCodingsImpl.unwrap(jCoding).toString();
    }

    @Override
    public int minLength(JCodings.Encoding jCoding) {
        return JCodingsImpl.unwrap(jCoding).minLength();
    }

    @Override
    public int maxLength(JCodings.Encoding jCoding) {
        return JCodingsImpl.unwrap(jCoding).maxLength();
    }

    @Override
    public boolean isFixedWidth(JCodings.Encoding jCoding) {
        return JCodingsImpl.unwrap(jCoding).isFixedWidth();
    }

    @Override
    public boolean isSingleByte(JCodings.Encoding jCoding) {
        return JCodingsImpl.unwrap(jCoding).isSingleByte();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public int getCodePointLength(JCodings.Encoding jCoding, int codepoint) {
        return JCodingsImpl.unwrap(jCoding).codeToMbcLength(codepoint);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public int getPreviousCodePointIndex(JCodings.Encoding jCoding, byte[] array, int arrayBegin, int index, int arrayEnd) {
        return JCodingsImpl.unwrap(jCoding).prevCharHead(array, arrayBegin, index, arrayEnd);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public int getCodePointLength(JCodings.Encoding jCoding, byte[] array, int index, int arrayLength) {
        return JCodingsImpl.unwrap(jCoding).length(array, index, arrayLength);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public int readCodePoint(JCodings.Encoding jCoding, byte[] array, int index, int arrayEnd) {
        return JCodingsImpl.unwrap(jCoding).mbcToCode(array, index, arrayEnd);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public int writeCodePoint(JCodings.Encoding jCoding, int codepoint, byte[] array, int index) {
        return JCodingsImpl.unwrap(jCoding).codeToMbc(codepoint, array, index);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public int codePointIndexToRaw(Node location, AbstractTruffleString a, byte[] arrayA, int extraOffsetRaw, int index, boolean isLength, JCodings.Encoding jCoding) {
        if (this.isFixedWidth(jCoding)) {
            return index * this.minLength(jCoding);
        }
        int offset = a.byteArrayOffset() + extraOffsetRaw;
        int end2 = a.byteArrayOffset() + a.length();
        int cpi = 0;
        int i = 0;
        while (i < a.length() - extraOffsetRaw) {
            if (cpi == index) {
                return i;
            }
            int length = JCodingsImpl.unwrap(jCoding).length(arrayA, offset + i, end2);
            if (length < 1) {
                if (length < -1) {
                    if (isLength) {
                        return a.length() - extraOffsetRaw;
                    }
                    throw InternalErrors.indexOutOfBounds();
                }
                ++i;
            } else {
                i += length;
            }
            TStringConstants.truffleSafePointPoll(location, ++cpi);
        }
        return TStringInternalNodes.CodePointIndexToRawNode.atEnd(a, extraOffsetRaw, index, isLength, cpi);
    }

    @Override
    public int decode(AbstractTruffleString a, byte[] arrayA, int rawIndex, JCodings.Encoding jCoding, TruffleString.ErrorHandling errorHandling) {
        int end2;
        int p = a.byteArrayOffset() + rawIndex;
        int length = this.getCodePointLength(jCoding, arrayA, p, end2 = a.byteArrayOffset() + a.length());
        if (length < 1) {
            return Encodings.invalidCodepointReturnValue(errorHandling);
        }
        return this.readCodePoint(jCoding, arrayA, p, end2);
    }

    @Override
    public long calcStringAttributes(Node location, Object array, int offset, int length, TruffleString.Encoding encoding, ConditionProfile validCharacterProfile, ConditionProfile fixedWidthProfile) {
        if (TStringGuards.is7BitCompatible(encoding) && TStringOps.calcStringAttributesLatin1(location, array, offset, length) == TSCodeRange.get7Bit()) {
            return StringAttributes.create(length, TSCodeRange.get7Bit());
        }
        byte[] bytes = JCodings.asByteArray(array);
        int offsetBytes = array instanceof AbstractTruffleString.NativePointer ? offset - ((AbstractTruffleString.NativePointer)array).offset() : offset;
        JCodings.Encoding enc = this.get(encoding);
        int codeRange = this.isSingleByte(enc) ? TSCodeRange.getValidFixedWidth() : TSCodeRange.getValidMultiByte();
        int characters = 0;
        int p = offsetBytes;
        int end2 = offsetBytes + length;
        int loopCount = 0;
        while (p < end2) {
            int lengthOfCurrentCharacter = this.getCodePointLength(enc, bytes, p, end2);
            if (validCharacterProfile.profile(lengthOfCurrentCharacter > 0 && p + lengthOfCurrentCharacter <= end2)) {
                p += lengthOfCurrentCharacter;
            } else {
                int n = codeRange = this.isSingleByte(enc) ? TSCodeRange.getBrokenFixedWidth() : TSCodeRange.getBrokenMultiByte();
                if (fixedWidthProfile.profile(this.isFixedWidth(enc))) {
                    characters = (length + this.minLength(enc) - 1) / this.minLength(enc);
                    return StringAttributes.create(characters, codeRange);
                }
                p += this.minLength(enc);
            }
            TStringConstants.truffleSafePointPoll(location, ++loopCount);
            ++characters;
        }
        return StringAttributes.create(characters, codeRange);
    }

    @CompilerDirectives.TruffleBoundary
    private static EConv getEconvTranscoder(JCodings.Encoding jCodingSrc, JCodings.Encoding jCodingDst) {
        return TranscoderDB.open(JCodingsImpl.unwrap(jCodingSrc).getName(), JCodingsImpl.unwrap(jCodingDst).getName(), 34);
    }

    @CompilerDirectives.TruffleBoundary
    private static void econvSetReplacement(JCodings.Encoding jCodingDst, EConv econv, byte[] replacement) {
        econv.setReplacement(replacement, 0, replacement.length, JCodingsImpl.unwrap(jCodingDst).getName());
    }

    @CompilerDirectives.TruffleBoundary
    private static EConvResult econvConvert(byte[] arrayA, byte[] buffer, EConv econv, Ptr srcPtr, Ptr dstPtr, int inStop) {
        return econv.convert(arrayA, srcPtr, inStop, buffer, dstPtr, buffer.length, 0);
    }

    @Override
    public TruffleString transcode(Node location, AbstractTruffleString a, Object arrayA, int codePointLengthA, TruffleString.Encoding targetEncoding, BranchProfile outOfMemoryProfile, ConditionProfile nativeProfile, TStringInternalNodes.FromBufferWithStringCompactionNode fromBufferWithStringCompactionNode) {
        TruffleString.Encoding encoding = TruffleString.Encoding.get(a.encoding());
        JCodings.Encoding jCodingSrc = TStringGuards.isUTF16Or32(encoding) && TStringGuards.isStride0(a) ? TruffleString.Encoding.ISO_8859_1.jCoding : (TStringGuards.isUTF32(encoding) && TStringGuards.isStride1(a) ? TruffleString.Encoding.UTF_16.jCoding : JCodings.getInstance().get(encoding));
        JCodings.Encoding jCodingDst = JCodings.getInstance().get(targetEncoding);
        byte[] buffer = new byte[(int)Math.min(0x7FFFFFF7L, (long)codePointLengthA * (long)JCodings.getInstance().maxLength(jCodingDst))];
        int length = 0;
        EConv econv = JCodingsImpl.getEconvTranscoder(jCodingSrc, jCodingDst);
        boolean undefinedConversion = false;
        if (econv == null) {
            undefinedConversion = true;
            int loopCount = 0;
            for (int i = 0; i < codePointLengthA; ++i) {
                int ret = JCodings.getInstance().writeCodePoint(jCodingDst, TStringGuards.isUTF8(targetEncoding) || TStringGuards.isUTF16Or32(targetEncoding) ? 65533 : 63, buffer, length);
                assert (ret > 0);
                length += ret;
                TStringConstants.truffleSafePointPoll(location, ++loopCount);
            }
        } else {
            byte[] replacement = TStringGuards.isUTF8(targetEncoding) ? CONVERSION_REPLACEMENT_UTF_8 : (TStringGuards.isUTF16(targetEncoding) ? CONVERSION_REPLACEMENT_UTF_16 : (TStringGuards.isUTF32(targetEncoding) ? CONVERSION_REPLACEMENT_UTF_32 : CONVERSION_REPLACEMENT));
            Ptr srcPtr = new Ptr();
            Ptr dstPtr = new Ptr();
            srcPtr.p = a.byteArrayOffset();
            dstPtr.p = 0;
            int inStop = a.byteArrayOffset() + (a.length() << a.stride());
            if (arrayA instanceof AbstractTruffleString.NativePointer) {
                ((AbstractTruffleString.NativePointer)arrayA).materializeByteArray(a, nativeProfile);
            }
            byte[] bytes = JCodings.asByteArray(arrayA);
            EConvResult result = JCodingsImpl.econvConvert(bytes, buffer, econv, srcPtr, dstPtr, inStop);
            while (!result.isFinished()) {
                if (result.isUndefinedConversion()) {
                    undefinedConversion = true;
                    JCodingsImpl.econvSetReplacement(jCodingDst, econv, replacement);
                } else if (result.isDestinationBufferFull()) {
                    if (buffer.length == 0x7FFFFFF7) {
                        outOfMemoryProfile.enter();
                        throw InternalErrors.outOfMemory();
                    }
                    buffer = Arrays.copyOf(buffer, (int)Math.min(0x7FFFFFF7L, (long)buffer.length << 1));
                } else {
                    throw CompilerDirectives.shouldNotReachHere();
                }
                result = JCodingsImpl.econvConvert(bytes, buffer, econv, srcPtr, dstPtr, inStop);
            }
            length = dstPtr.p;
        }
        AbstractTruffleString.checkArrayRange(buffer, 0, length);
        return fromBufferWithStringCompactionNode.execute(buffer, 0, length, targetEncoding, length != buffer.length || targetEncoding.isSupported(), undefinedConversion || a.isMutable());
    }

    @CompilerDirectives.TruffleBoundary
    private static String toEnumName(String encodingName) {
        if ("ASCII-8BIT".equals(encodingName)) {
            return "BYTES";
        }
        Object capitalized = encodingName;
        if (Character.isLowerCase(encodingName.charAt(0))) {
            capitalized = Character.toUpperCase(encodingName.charAt(0)) + encodingName.substring(1);
        }
        return ((String)capitalized).replace('-', '_');
    }

    private static Encoding unwrap(JCodings.Encoding wrapped) {
        return ((EncodingWrapper)wrapped).encoding;
    }

    static {
        byte[] byArray;
        byte[] byArray2;
        J_CODINGS_MAP = JCodingsImpl.createJCodingsMap();
        CONVERSION_REPLACEMENT = new byte[]{63};
        CONVERSION_REPLACEMENT_UTF_8 = new byte[]{-17, -65, -67};
        if (TStringGuards.littleEndian()) {
            byte[] byArray3 = new byte[2];
            byArray3[0] = -3;
            byArray2 = byArray3;
            byArray3[1] = -1;
        } else {
            byte[] byArray4 = new byte[2];
            byArray4[0] = -1;
            byArray2 = byArray4;
            byArray4[1] = -3;
        }
        CONVERSION_REPLACEMENT_UTF_16 = byArray2;
        if (TStringGuards.littleEndian()) {
            byte[] byArray5 = new byte[4];
            byArray5[0] = -3;
            byArray5[1] = -1;
            byArray5[2] = 0;
            byArray = byArray5;
            byArray5[3] = 0;
        } else {
            byte[] byArray6 = new byte[4];
            byArray6[0] = 0;
            byArray6[1] = 0;
            byArray6[2] = -1;
            byArray = byArray6;
            byArray6[3] = -3;
        }
        CONVERSION_REPLACEMENT_UTF_32 = byArray;
    }

    private static final class EncodingWrapper
    implements JCodings.Encoding {
        private final Encoding encoding;

        private EncodingWrapper(Encoding encoding) {
            this.encoding = encoding;
        }
    }
}

