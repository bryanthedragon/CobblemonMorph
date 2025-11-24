
package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GeneratePackagePrivate;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.AbstractTruffleString;
import com.oracle.truffle.api.strings.Encodings;
import com.oracle.truffle.api.strings.FastDoubleParser;
import com.oracle.truffle.api.strings.InternalErrors;
import com.oracle.truffle.api.strings.JCodings;
import com.oracle.truffle.api.strings.MutableTruffleString;
import com.oracle.truffle.api.strings.NumberConversion;
import com.oracle.truffle.api.strings.Stride;
import com.oracle.truffle.api.strings.StringAttributes;
import com.oracle.truffle.api.strings.TSCodeRange;
import com.oracle.truffle.api.strings.TStringConstants;
import com.oracle.truffle.api.strings.TStringGuards;
import com.oracle.truffle.api.strings.TStringInternalNodesFactory;
import com.oracle.truffle.api.strings.TStringOps;
import com.oracle.truffle.api.strings.TStringOpsNodes;
import com.oracle.truffle.api.strings.TStringUnsafe;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringIterator;
import java.util.Arrays;

final class TStringInternalNodes {
    TStringInternalNodes() {
    }

    static int indexOfFixedWidth(AbstractTruffleString a, Object arrayA, int codeRangeA, int codepoint, int fromIndex, int toIndex, TStringOpsNodes.RawIndexOfCodePointNode indexOfNode) {
        if (fromIndex == toIndex || !TSCodeRange.isInCodeRange(codepoint, codeRangeA)) {
            return -1;
        }
        return indexOfNode.execute(a, arrayA, codepoint, fromIndex, toIndex);
    }

    static int lastIndexOfFixedWidth(AbstractTruffleString a, Object arrayA, int codeRangeA, int codepoint, int fromIndex, int toIndex, TStringOpsNodes.RawLastIndexOfCodePointNode indexOfNode) {
        if (fromIndex == toIndex || !TSCodeRange.isInCodeRange(codepoint, codeRangeA)) {
            return -1;
        }
        return indexOfNode.execute(a, arrayA, codepoint, fromIndex, toIndex);
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class TransCodeIntlNode
    extends Node {
        TransCodeIntlNode() {
        }

        abstract TruffleString execute(AbstractTruffleString var1, Object var2, int var3, int var4, TruffleString.Encoding var5, TruffleString.Encoding var6);

        @Specialization(guards={"isSupportedEncoding(sourceEncoding)", "isAscii(targetEncoding) || isBytes(targetEncoding)"})
        TruffleString targetAscii(AbstractTruffleString a, Object arrayA, int codePointLengthA, int codeRangeA, TruffleString.Encoding sourceEncoding, TruffleString.Encoding targetEncoding, @Cached @Cached.Shared(value="iteratorNextNode") TruffleStringIterator.NextNode iteratorNextNode) {
            assert (!TStringGuards.is7Bit(codeRangeA));
            byte[] buffer = new byte[codePointLengthA];
            TruffleStringIterator it = AbstractTruffleString.forwardIterator(a, arrayA, codeRangeA, sourceEncoding);
            int i = 0;
            while (it.hasNext()) {
                int codepoint = iteratorNextNode.execute(it);
                buffer[i++] = (byte)(codepoint > 127 ? 63 : (byte)codepoint);
                TStringConstants.truffleSafePointPoll(this, i);
            }
            return TransCodeIntlNode.create(a, buffer, buffer.length, 0, targetEncoding, codePointLengthA, TSCodeRange.get7Bit(), true);
        }

        @Specialization(guards={"isSupportedEncoding(sourceEncoding)", "isLatin1(targetEncoding)"})
        TruffleString latin1Transcode(AbstractTruffleString a, Object arrayA, int codePointLengthA, int codeRangeA, TruffleString.Encoding sourceEncoding, TruffleString.Encoding targetEncoding, @Cached @Cached.Shared(value="iteratorNextNode") TruffleStringIterator.NextNode iteratorNextNode) {
            assert (!TStringGuards.is7Or8Bit(codeRangeA));
            byte[] buffer = new byte[codePointLengthA];
            TruffleStringIterator it = AbstractTruffleString.forwardIterator(a, arrayA, codeRangeA, sourceEncoding);
            int codeRange = TSCodeRange.get7Bit();
            int i = 0;
            while (it.hasNext()) {
                int codepoint = iteratorNextNode.execute(it);
                byte latin1 = (byte)(codepoint > 255 ? 63 : (byte)codepoint);
                buffer[i++] = latin1;
                if (latin1 < 0) {
                    codeRange = TSCodeRange.get8Bit();
                }
                TStringConstants.truffleSafePointPoll(this, i);
            }
            return TransCodeIntlNode.create(a, buffer, codePointLengthA, 0, TruffleString.Encoding.ISO_8859_1, codePointLengthA, codeRange, true);
        }

        @Specialization(guards={"isSupportedEncoding(sourceEncoding)", "!isLarge(codePointLengthA)", "isUTF8(targetEncoding)"})
        TruffleString utf8TranscodeRegular(AbstractTruffleString a, Object arrayA, int codePointLengthA, int codeRangeA, TruffleString.Encoding sourceEncoding, TruffleString.Encoding targetEncoding, @Cached @Cached.Shared(value="iteratorNextNode") TruffleStringIterator.NextNode iteratorNextNode, @Cached @Cached.Shared(value="brokenProfile") ConditionProfile brokenProfile, @Cached @Cached.Shared(value="outOfMemoryProfile") BranchProfile outOfMemoryProfile) {
            return this.utf8Transcode(a, arrayA, codePointLengthA, codeRangeA, sourceEncoding, iteratorNextNode, false, brokenProfile, outOfMemoryProfile);
        }

        @Specialization(guards={"isSupportedEncoding(sourceEncoding)", "isLarge(codePointLengthA)", "isUTF8(targetEncoding)"})
        TruffleString utf8TranscodeLarge(AbstractTruffleString a, Object arrayA, int codePointLengthA, int codeRangeA, TruffleString.Encoding sourceEncoding, TruffleString.Encoding targetEncoding, @Cached @Cached.Shared(value="iteratorNextNode") TruffleStringIterator.NextNode iteratorNextNode, @Cached @Cached.Shared(value="brokenProfile") ConditionProfile brokenProfile, @Cached @Cached.Shared(value="outOfMemoryProfile") BranchProfile outOfMemoryProfile) {
            return this.utf8Transcode(a, arrayA, codePointLengthA, codeRangeA, sourceEncoding, iteratorNextNode, true, brokenProfile, outOfMemoryProfile);
        }

        static boolean isLarge(int codePointLengthA) {
            return codePointLengthA > 0x1FFFFFFD;
        }

        private TruffleString utf8Transcode(AbstractTruffleString a, Object arrayA, int codePointLengthA, int codeRangeA, TruffleString.Encoding sourceEncoding, TruffleStringIterator.NextNode iteratorNextNode, boolean isLarge, ConditionProfile brokenProfile, BranchProfile outOfMemoryProfile) {
            int codePointLength;
            assert (!TStringGuards.is7Bit(codeRangeA));
            TruffleStringIterator it = AbstractTruffleString.forwardIterator(a, arrayA, codeRangeA, sourceEncoding);
            byte[] buffer = new byte[isLarge ? 0x7FFFFFF7 : codePointLengthA * 4];
            int codeRange = TSCodeRange.getValidMultiByte();
            int length = 0;
            int loopCount = 0;
            while (it.hasNext()) {
                int codepoint = iteratorNextNode.execute(it);
                if (Encodings.isUTF16Surrogate(codepoint) || Integer.toUnsignedLong(codepoint) > 0x10FFFFL) {
                    codeRange = TSCodeRange.getBrokenMultiByte();
                    codepoint = Encodings.invalidCodepoint();
                }
                int n = Encodings.utf8EncodedSize(codepoint);
                assert (isLarge || length + n <= buffer.length);
                if (isLarge && length > 0x7FFFFFF7 - n) {
                    outOfMemoryProfile.enter();
                    throw InternalErrors.outOfMemory();
                }
                Encodings.utf8Encode(codepoint, buffer, length, n);
                length += n;
                TStringConstants.truffleSafePointPoll(this, ++loopCount);
            }
            if (TStringGuards.isBrokenMultiByte(codeRange)) {
                long attrs = TStringOps.calcStringAttributesUTF8(this, buffer, 0, length, false, false, brokenProfile);
                codePointLength = StringAttributes.getCodePointLength(attrs);
                codeRange = StringAttributes.getCodeRange(attrs);
            } else {
                codePointLength = codePointLengthA;
            }
            return TransCodeIntlNode.create(a, Arrays.copyOf(buffer, length), length, 0, TruffleString.Encoding.UTF_8, codePointLength, codeRange, TStringGuards.isBrokenMultiByte(codeRange));
        }

        @Specialization(guards={"isUTF32(sourceEncoding)", "isUTF16(targetEncoding)"})
        TruffleString utf16Fixed32Bit(AbstractTruffleString a, Object arrayA, int codePointLengthA, int codeRangeA, TruffleString.Encoding sourceEncoding, TruffleString.Encoding targetEncoding) {
            int codePointLength;
            assert (TStringGuards.isValidFixedWidth(codeRangeA) || TStringGuards.isBrokenFixedWidth(codeRangeA));
            assert (TStringGuards.isStride2(a));
            byte[] buffer = new byte[codePointLengthA * 4];
            int length = 0;
            int codeRange = TStringGuards.isValidFixedWidth(codeRangeA) ? TSCodeRange.getValidMultiByte() : TSCodeRange.getBrokenMultiByte();
            for (int i = 0; i < a.length(); ++i) {
                int codepoint = TStringOps.readS2(a, arrayA, i);
                length += Encodings.utf16Encode(codepoint, buffer, length);
                TStringConstants.truffleSafePointPoll(this, i + 1);
            }
            if (TStringGuards.isBrokenMultiByte(codeRange)) {
                long attrs = TStringOps.calcStringAttributesUTF16(this, buffer, 0, length, false);
                codePointLength = StringAttributes.getCodePointLength(attrs);
                codeRange = StringAttributes.getCodeRange(attrs);
            } else {
                codePointLength = codePointLengthA;
            }
            return TransCodeIntlNode.create(a, Arrays.copyOf(buffer, length * 2), length, 1, TruffleString.Encoding.UTF_16, codePointLength, codeRange, TStringGuards.isBrokenMultiByte(codeRange));
        }

        @Specialization(guards={"isSupportedEncoding(sourceEncoding)", "!isFixedWidth(codeRangeA)", "!isLarge(codePointLengthA)", "isUTF16(targetEncoding)"})
        TruffleString utf16TranscodeRegular(AbstractTruffleString a, Object arrayA, int codePointLengthA, int codeRangeA, TruffleString.Encoding sourceEncoding, TruffleString.Encoding targetEncoding, @Cached @Cached.Shared(value="iteratorNextNode") TruffleStringIterator.NextNode iteratorNextNode, @Cached @Cached.Shared(value="outOfMemoryProfile") BranchProfile outOfMemoryProfile) {
            return this.utf16Transcode(a, arrayA, codePointLengthA, codeRangeA, sourceEncoding, iteratorNextNode, false, outOfMemoryProfile);
        }

        @Specialization(guards={"isSupportedEncoding(sourceEncoding)", "!isFixedWidth(codeRangeA)", "isLarge(codePointLengthA)", "isUTF16(targetEncoding)"})
        TruffleString utf16TranscodeLarge(AbstractTruffleString a, Object arrayA, int codePointLengthA, int codeRangeA, TruffleString.Encoding sourceEncoding, TruffleString.Encoding targetEncoding, @Cached @Cached.Shared(value="iteratorNextNode") TruffleStringIterator.NextNode iteratorNextNode, @Cached @Cached.Shared(value="outOfMemoryProfile") BranchProfile outOfMemoryProfile) {
            return this.utf16Transcode(a, arrayA, codePointLengthA, codeRangeA, sourceEncoding, iteratorNextNode, true, outOfMemoryProfile);
        }

        private TruffleString utf16Transcode(AbstractTruffleString a, Object arrayA, int codePointLengthA, int codeRangeA, TruffleString.Encoding sourceEncoding, TruffleStringIterator.NextNode iteratorNextNode, boolean isLarge, BranchProfile outOfMemoryProfile) {
            int codepoint;
            int curIndex;
            assert (TStringGuards.isValidBrokenOrUnknownMultiByte(codeRangeA));
            TruffleStringIterator it = AbstractTruffleString.forwardIterator(a, arrayA, codeRangeA, sourceEncoding);
            byte[] buffer = new byte[codePointLengthA];
            int codePointLength = codePointLengthA;
            int length = 0;
            int codeRange = TSCodeRange.get7Bit();
            while (it.hasNext()) {
                curIndex = it.getRawIndex();
                codepoint = iteratorNextNode.execute(it);
                if (codepoint > 255) {
                    buffer = TStringOps.arraycopyOfWithStride(this, buffer, 0, length, 0, codePointLengthA, 1);
                    codeRange = TSCodeRange.get16Bit();
                    it.setRawIndex(curIndex);
                    break;
                }
                if (codepoint > 127) {
                    codeRange = TSCodeRange.get8Bit();
                }
                buffer[length++] = (byte)codepoint;
                TStringConstants.truffleSafePointPoll(this, length);
            }
            if (!it.hasNext()) {
                assert (length == codePointLengthA);
                return TransCodeIntlNode.create(a, buffer, length, 0, TruffleString.Encoding.UTF_16, codePointLengthA, codeRange, false);
            }
            while (it.hasNext()) {
                curIndex = it.getRawIndex();
                codepoint = iteratorNextNode.execute(it);
                if (codepoint > 65535) {
                    buffer = Arrays.copyOf(buffer, isLarge ? 0x7FFFFFF7 : buffer.length * 2);
                    codeRange = TSCodeRange.commonCodeRange(codeRange, TSCodeRange.getValidMultiByte());
                    it.setRawIndex(curIndex);
                    break;
                }
                if (Encodings.isUTF16Surrogate(codepoint)) {
                    codeRange = TSCodeRange.getBrokenMultiByte();
                }
                TStringOps.writeToByteArray(buffer, 1, length++, codepoint);
                TStringConstants.truffleSafePointPoll(this, length);
            }
            if (!it.hasNext()) {
                assert (length == codePointLengthA);
                if (TStringGuards.isBrokenMultiByte(codeRange)) {
                    long attrs = TStringOps.calcStringAttributesUTF16(this, buffer, 0, length, false);
                    codePointLength = StringAttributes.getCodePointLength(attrs);
                    codeRange = StringAttributes.getCodeRange(attrs);
                }
                return TransCodeIntlNode.create(a, buffer, length, 1, TruffleString.Encoding.UTF_16, codePointLength, codeRange, TStringGuards.isBrokenMultiByte(codeRange));
            }
            int loopCount = 0;
            while (it.hasNext()) {
                codepoint = iteratorNextNode.execute(it);
                if (Encodings.isUTF16Surrogate(codepoint) || Integer.toUnsignedLong(codepoint) > 0x10FFFFL) {
                    codeRange = TSCodeRange.getBrokenMultiByte();
                }
                if (isLarge && length + Encodings.utf16EncodedSize(codepoint) > 0x3FFFFFFB) {
                    outOfMemoryProfile.enter();
                    throw InternalErrors.outOfMemory();
                }
                length += Encodings.utf16Encode(codepoint, buffer, length);
                TStringConstants.truffleSafePointPoll(this, ++loopCount);
            }
            if (TStringGuards.isBrokenMultiByte(codeRange)) {
                long attrs = TStringOps.calcStringAttributesUTF16(this, buffer, 0, length, false);
                codePointLength = StringAttributes.getCodePointLength(attrs);
                codeRange = StringAttributes.getCodeRange(attrs);
            }
            return TransCodeIntlNode.create(a, Arrays.copyOf(buffer, length * 2), length, 1, TruffleString.Encoding.UTF_16, codePointLength, codeRange, TStringGuards.isBrokenMultiByte(codeRange));
        }

        @Specialization(guards={"!isUTF16(sourceEncoding)", "isSupportedEncoding(sourceEncoding)", "!isFixedWidth(codeRangeA)", "!isLarge(codePointLengthA)", "isUTF32(targetEncoding)"})
        TruffleString utf32TranscodeRegular(AbstractTruffleString a, Object arrayA, int codePointLengthA, int codeRangeA, TruffleString.Encoding sourceEncoding, TruffleString.Encoding targetEncoding, @Cached @Cached.Shared(value="iteratorNextNode") TruffleStringIterator.NextNode iteratorNextNode) {
            return this.utf32Transcode(a, arrayA, codePointLengthA, codeRangeA, sourceEncoding, iteratorNextNode);
        }

        @Specialization(guards={"isSupportedEncoding(sourceEncoding)", "!isFixedWidth(codeRangeA)", "isLarge(codePointLengthA)", "isUTF32(targetEncoding)"})
        static TruffleString utf32TranscodeLarge(AbstractTruffleString a, Object arrayA, int codePointLengthA, int codeRangeA, TruffleString.Encoding sourceEncoding, TruffleString.Encoding targetEncoding) {
            throw InternalErrors.outOfMemory();
        }

        @Specialization(guards={"isUTF16(sourceEncoding)", "!isFixedWidth(codeRangeA)", "!isLarge(codePointLengthA)", "isUTF32(targetEncoding)"})
        TruffleString utf32TranscodeUTF16(AbstractTruffleString a, Object arrayA, int codePointLengthA, int codeRangeA, TruffleString.Encoding sourceEncoding, TruffleString.Encoding targetEncoding, @Cached @Cached.Shared(value="iteratorNextNode") TruffleStringIterator.NextNode iteratorNextNode) {
            assert (TransCodeIntlNode.containsSurrogates(a));
            TruffleStringIterator it = AbstractTruffleString.forwardIterator(a, arrayA, codeRangeA, sourceEncoding);
            byte[] buffer = new byte[codePointLengthA << 2];
            int length = 0;
            while (it.hasNext()) {
                TStringOps.writeToByteArray(buffer, 2, length++, iteratorNextNode.execute(it));
                TStringConstants.truffleSafePointPoll(this, length);
            }
            assert (length == codePointLengthA);
            boolean isBroken = TStringGuards.isBrokenMultiByte(codeRangeA);
            return TransCodeIntlNode.create(a, buffer, length, 2, TruffleString.Encoding.UTF_32, codePointLengthA, isBroken ? TSCodeRange.getBrokenFixedWidth() : TSCodeRange.getValidFixedWidth(), isBroken);
        }

        private TruffleString utf32Transcode(AbstractTruffleString a, Object arrayA, int codePointLengthA, int codeRangeA, TruffleString.Encoding sourceEncoding, TruffleStringIterator.NextNode iteratorNextNode) {
            int curIndex;
            assert (TStringGuards.isValidBrokenOrUnknownMultiByte(codeRangeA));
            TruffleStringIterator it = AbstractTruffleString.forwardIterator(a, arrayA, codeRangeA, sourceEncoding);
            byte[] buffer = new byte[codePointLengthA];
            int length = 0;
            int codeRange = TSCodeRange.get7Bit();
            int codepoint = 0;
            while (it.hasNext()) {
                curIndex = it.getRawIndex();
                codepoint = iteratorNextNode.execute(it);
                if (codepoint > 255) {
                    if (Encodings.isUTF16Surrogate(codepoint)) {
                        buffer = TStringOps.arraycopyOfWithStride(this, buffer, 0, length, 0, codePointLengthA, 2);
                        codeRange = TSCodeRange.getBrokenFixedWidth();
                    } else {
                        buffer = TStringOps.arraycopyOfWithStride(this, buffer, 0, length, 0, codePointLengthA, 1);
                        codeRange = TSCodeRange.get16Bit();
                    }
                    it.setRawIndex(curIndex);
                    break;
                }
                if (codepoint > 127) {
                    codeRange = TSCodeRange.get8Bit();
                }
                buffer[length++] = (byte)codepoint;
                TStringConstants.truffleSafePointPoll(this, length);
            }
            if (!it.hasNext()) {
                assert (length == codePointLengthA);
                return TransCodeIntlNode.create(a, buffer, length, 0, TruffleString.Encoding.UTF_32, codePointLengthA, codeRange, TStringGuards.isBrokenFixedWidth(codeRange));
            }
            if (TStringGuards.is16Bit(codeRange)) {
                while (it.hasNext()) {
                    curIndex = it.getRawIndex();
                    codepoint = iteratorNextNode.execute(it);
                    if (codepoint > 65535 || Encodings.isUTF16Surrogate(codepoint)) {
                        buffer = TStringOps.arraycopyOfWithStride(this, buffer, 0, length, 1, codePointLengthA, 2);
                        codeRange = Encodings.isValidUnicodeCodepoint(codepoint) ? TSCodeRange.getValidFixedWidth() : TSCodeRange.getBrokenFixedWidth();
                        it.setRawIndex(curIndex);
                        break;
                    }
                    TStringOps.writeToByteArray(buffer, 1, length++, codepoint);
                    TStringConstants.truffleSafePointPoll(this, length);
                }
            }
            if (!it.hasNext()) {
                assert (length == codePointLengthA);
                return TransCodeIntlNode.create(a, buffer, length, 1, TruffleString.Encoding.UTF_32, codePointLengthA, codeRange, TStringGuards.isBrokenFixedWidth(codeRange));
            }
            while (it.hasNext()) {
                codepoint = iteratorNextNode.execute(it);
                if (!Encodings.isValidUnicodeCodepoint(codepoint)) {
                    codeRange = TSCodeRange.getBrokenFixedWidth();
                }
                TStringOps.writeToByteArray(buffer, 2, length++, codepoint);
                TStringConstants.truffleSafePointPoll(this, length);
            }
            return TransCodeIntlNode.create(a, buffer, length, 2, TruffleString.Encoding.UTF_32, codePointLengthA, codeRange, TStringGuards.isBrokenFixedWidth(codeRange));
        }

        @Specialization(guards={"isUnsupportedEncoding(sourceEncoding) || isUnsupportedEncoding(targetEncoding)"})
        TruffleString unsupported(AbstractTruffleString a, Object arrayA, int codePointLengthA, int codeRangeA, TruffleString.Encoding sourceEncoding, TruffleString.Encoding targetEncoding, @Cached BranchProfile outOfMemoryProfile, @Cached ConditionProfile nativeProfile, @Cached FromBufferWithStringCompactionNode fromBufferWithStringCompactionNode) {
            return JCodings.getInstance().transcode(this, a, arrayA, codePointLengthA, targetEncoding, outOfMemoryProfile, nativeProfile, fromBufferWithStringCompactionNode);
        }

        private static TruffleString create(AbstractTruffleString a, byte[] buffer, int length, int stride, TruffleString.Encoding encoding, int codePointLength, int codeRange, boolean isCacheHead) {
            return TruffleString.createFromByteArray(buffer, length, stride, encoding, codePointLength, codeRange, isCacheHead || a.isMutable());
        }

        @CompilerDirectives.TruffleBoundary
        private static boolean containsSurrogates(AbstractTruffleString a) {
            CompilerAsserts.neverPartOfCompilation();
            for (int i = 0; i < a.length(); ++i) {
                if (!Encodings.isUTF16Surrogate(a.readCharUTF16Uncached(i))) continue;
                return true;
            }
            return false;
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class TransCodeNode
    extends Node {
        TransCodeNode() {
        }

        abstract TruffleString execute(AbstractTruffleString var1, Object var2, int var3, int var4, TruffleString.Encoding var5);

        @Specialization
        TruffleString transcode(AbstractTruffleString a, Object arrayA, int codePointLengthA, int codeRangeA, TruffleString.Encoding targetEncoding, @Cached ConditionProfile asciiBytesInvalidProfile, @Cached TransCodeIntlNode transCodeIntlNode) {
            if (AbstractTruffleString.DEBUG_STRICT_ENCODING_CHECKS && a.isImmutable() && codeRangeA < targetEncoding.maxCompatibleCodeRange) {
                if (a.stride() == 0) {
                    return TruffleString.createFromArray(arrayA, a.offset(), a.length(), 0, targetEncoding, codePointLengthA, codeRangeA, false);
                }
                int targetStride = Stride.fromCodeRange(codeRangeA, targetEncoding);
                byte[] array = TStringOps.arraycopyOfWithStride(this, arrayA, a.offset(), a.length(), a.stride(), a.length(), targetStride);
                return TruffleString.createFromArray(array, 0, a.length(), targetStride, targetEncoding, codePointLengthA, codeRangeA, false);
            }
            assert (a.length() > 0);
            if (asciiBytesInvalidProfile.profile((TStringGuards.isAscii(a.encoding()) || TStringGuards.isBytes(a.encoding())) && TStringGuards.isSupportedEncoding(targetEncoding))) {
                assert ((TStringGuards.isBrokenFixedWidth(codeRangeA) || TStringGuards.isValidFixedWidth(codeRangeA)) && TStringGuards.isStride0(a) && codePointLengthA == a.length());
                byte[] buffer = new byte[codePointLengthA];
                for (int i = 0; i < buffer.length; ++i) {
                    int c = TStringOps.readS0(a, arrayA, i);
                    buffer[i] = (byte)(c > 127 ? 63 : c);
                    TStringConstants.truffleSafePointPoll(this, i + 1);
                }
                return TransCodeIntlNode.create(a, buffer, buffer.length, 0, targetEncoding, codePointLengthA, TSCodeRange.get7Bit(), true);
            }
            return transCodeIntlNode.execute(a, arrayA, codePointLengthA, codeRangeA, TruffleString.Encoding.get(a.encoding()), targetEncoding);
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class CreateJavaStringNode
    extends Node {
        CreateJavaStringNode() {
        }

        abstract String execute(AbstractTruffleString var1, Object var2);

        @Specialization
        String createJavaString(AbstractTruffleString a, Object arrayA, @Cached ConditionProfile reuseProfile, @Cached GetCodeRangeNode getCodeRangeNode) {
            byte[] bytes;
            assert (CreateJavaStringNode.isUTF16Compatible(a));
            int codeRange = getCodeRangeNode.execute(a);
            int stride = Stride.fromCodeRangeUTF16(codeRange);
            if (reuseProfile.profile(a instanceof TruffleString && arrayA instanceof byte[] && a.length() << a.stride() == ((byte[])arrayA).length && a.stride() == stride)) {
                assert (a.offset() == 0);
                bytes = (byte[])arrayA;
            } else {
                bytes = new byte[a.length() << stride];
                TStringOps.arraycopyWithStride(this, arrayA, a.offset(), a.stride(), 0, bytes, 0, stride, 0, a.length());
            }
            return TStringUnsafe.createJavaString(bytes, stride);
        }

        private static boolean isUTF16Compatible(AbstractTruffleString a) {
            return a.isCompatibleTo(TruffleString.Encoding.UTF_16) || a instanceof MutableTruffleString && ((MutableTruffleString)a).codeRange() < TruffleString.Encoding.UTF_16.maxCompatibleCodeRange;
        }
    }

    @ImportStatic(value={TStringGuards.class, TruffleString.Encoding.class})
    @GenerateUncached
    static abstract class ToJavaStringNode
    extends Node {
        ToJavaStringNode() {
        }

        abstract TruffleString execute(TruffleString var1, Object var2);

        @Specialization(guards={"a.isCompatibleTo(UTF_16)"})
        static TruffleString doUTF16(TruffleString a, Object arrayA, @Cached @Cached.Shared(value="createStringNode") CreateJavaStringNode createStringNode) {
            return TruffleString.createWrapJavaString(createStringNode.execute(a, arrayA), a.codePointLength(), a.codeRange());
        }

        @Specialization(guards={"!a.isCompatibleTo(UTF_16)"})
        static TruffleString doGeneric(TruffleString a, Object arrayA, @Cached GetCodePointLengthNode getCodePointLengthNode, @Cached GetCodeRangeNode getCodeRangeNode, @Cached TransCodeNode transCodeNode, @Cached @Cached.Shared(value="createStringNode") CreateJavaStringNode createStringNode) {
            TruffleString utf16 = transCodeNode.execute(a, arrayA, getCodePointLengthNode.execute(a), getCodeRangeNode.execute(a), TruffleString.Encoding.UTF_16);
            if (!utf16.isCacheHead()) {
                a.cacheInsert(utf16);
            }
            return TruffleString.createWrapJavaString(createStringNode.execute(utf16, utf16.data()), utf16.codePointLength(), utf16.codeRange());
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class FromJavaStringUTF16Node
    extends Node {
        FromJavaStringUTF16Node() {
        }

        abstract TruffleString execute(String var1, int var2, int var3, boolean var4);

        @Specialization
        TruffleString doNonEmpty(String javaString, int charOffset, int length, boolean copy, @Cached ConditionProfile utf16CompactProfile) {
            byte[] array;
            int offset;
            int stride;
            int codePointLength;
            int codeRange;
            AbstractTruffleString.checkArrayRange(javaString.length(), charOffset, length);
            CompilerAsserts.partialEvaluationConstant(copy);
            if (length == 0) {
                return TruffleString.Encoding.UTF_16.getEmpty();
            }
            int strideJS = TStringUnsafe.getJavaStringStride(javaString);
            int offsetJS = charOffset << 1;
            byte[] arrayJS = TStringUnsafe.getJavaStringArray(javaString);
            if (utf16CompactProfile.profile(strideJS == 0)) {
                if (length == 1) {
                    return TStringConstants.getSingleByte(TruffleString.Encoding.UTF_16, Byte.toUnsignedInt(arrayJS[charOffset]));
                }
                codeRange = TStringOps.calcStringAttributesLatin1(this, arrayJS, offsetJS, length);
                codePointLength = length;
            } else {
                assert (strideJS == 1);
                if (length == 1 && TStringOps.readFromByteArray(arrayJS, 1, charOffset) <= 255) {
                    return TStringConstants.getSingleByte(TruffleString.Encoding.UTF_16, TStringOps.readFromByteArray(arrayJS, 1, charOffset));
                }
                long attrs = TStringOps.calcStringAttributesUTF16(this, arrayJS, offsetJS, length, false);
                codePointLength = StringAttributes.getCodePointLength(attrs);
                codeRange = StringAttributes.getCodeRange(attrs);
            }
            if (!copy || length == javaString.length()) {
                stride = strideJS;
                offset = offsetJS;
                array = arrayJS;
            } else {
                stride = Stride.fromCodeRangeUTF16(codeRange);
                array = new byte[length << stride];
                offset = 0;
                if (strideJS == 1 && stride == 0) {
                    TStringOps.arraycopyWithStride(this, arrayJS, offsetJS, 1, 0, array, offset, 0, 0, length);
                } else {
                    assert (strideJS == stride);
                    TStringOps.arraycopyWithStride(this, arrayJS, offsetJS, 0, 0, array, offset, 0, 0, length << stride);
                }
            }
            TruffleString ret = TruffleString.createFromArray(array, offset, length, stride, TruffleString.Encoding.UTF_16, codePointLength, codeRange);
            if (length == javaString.length()) {
                assert (charOffset == 0);
                TruffleString wrapped = TruffleString.createWrapJavaString(javaString, codePointLength, codeRange);
                ret.cacheInsertFirstBeforePublished(wrapped);
            }
            return ret;
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class ParseDoubleNode
    extends Node {
        ParseDoubleNode() {
        }

        abstract double execute(AbstractTruffleString var1, Object var2) throws TruffleString.NumberFormatException;

        @Specialization(guards={"cachedStride == a.stride()"})
        double doParse(AbstractTruffleString a, Object arrayA, @Cached(value="a.stride()", allowUncached=true) int cachedStride, @Cached BranchProfile errorProfile) throws TruffleString.NumberFormatException {
            return FastDoubleParser.parseDouble(this, a, arrayA, cachedStride, 0, a.length(), errorProfile);
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class ParseLongNode
    extends Node {
        ParseLongNode() {
        }

        abstract long execute(AbstractTruffleString var1, Object var2, int var3, TruffleString.Encoding var4, int var5) throws TruffleString.NumberFormatException;

        @Specialization(guards={"is7Bit(codeRangeA)", "cachedStride == a.stride()"})
        static long do7Bit(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int radix, @Cached(value="a.stride()", allowUncached=true) int cachedStride, @Cached BranchProfile errorProfile) throws TruffleString.NumberFormatException {
            return NumberConversion.parseLong7Bit(a, arrayA, cachedStride, radix, errorProfile);
        }

        @Specialization(guards={"!is7Bit(codeRangeA)"})
        static long parseLong(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int radix, @Cached TruffleStringIterator.NextNode nextNode, @Cached BranchProfile errorProfile) throws TruffleString.NumberFormatException {
            return NumberConversion.parseLong(AbstractTruffleString.forwardIterator(a, arrayA, codeRangeA, encoding), radix, errorProfile, nextNode);
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class ParseIntNode
    extends Node {
        ParseIntNode() {
        }

        abstract int execute(AbstractTruffleString var1, Object var2, int var3, TruffleString.Encoding var4, int var5) throws TruffleString.NumberFormatException;

        @Specialization(guards={"is7Bit(codeRangeA)", "cachedStride == a.stride()"})
        static int do7Bit(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int radix, @Cached(value="a.stride()", allowUncached=true) int cachedStride, @Cached BranchProfile errorProfile) throws TruffleString.NumberFormatException {
            return NumberConversion.parseInt7Bit(a, arrayA, cachedStride, radix, errorProfile);
        }

        @Specialization(guards={"!is7Bit(codeRangeA)"})
        static int doGeneric(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int radix, @Cached TruffleStringIterator.NextNode nextNode, @Cached BranchProfile errorProfile) throws TruffleString.NumberFormatException {
            return NumberConversion.parseInt(AbstractTruffleString.forwardIterator(a, arrayA, codeRangeA, encoding), radix, errorProfile, nextNode);
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class CalcStringAttributesInnerNode
    extends Node {
        CalcStringAttributesInnerNode() {
        }

        abstract long execute(AbstractTruffleString var1, Object var2, int var3, int var4, int var5, TruffleString.Encoding var6, int var7);

        @Specialization(guards={"is8Bit(knownCodeRange) || isAsciiBytesOrLatin1(encoding)", "stride == 0"})
        long doLatin1(AbstractTruffleString a, Object array, int offset, int length, int stride, TruffleString.Encoding encoding, int knownCodeRange) {
            int codeRange = TStringOps.calcStringAttributesLatin1(this, array, offset, length);
            return StringAttributes.create(length, TStringGuards.is8Bit(codeRange) && TStringGuards.isAsciiBytesOrLatin1(encoding) ? TSCodeRange.asciiLatinBytesNonAsciiCodeRange(encoding) : codeRange);
        }

        @Specialization(guards={"isUpTo16Bit(knownCodeRange)", "stride == 1"})
        long doBMP(AbstractTruffleString a, Object array, int offset, int length, int stride, TruffleString.Encoding encoding, int knownCodeRange) {
            return StringAttributes.create(length, TStringOps.calcStringAttributesBMP(this, array, offset, length));
        }

        @Specialization(guards={"isUTF8(encoding)", "!isFixedWidth(knownCodeRange)"})
        long doUTF8(AbstractTruffleString a, Object array, int offset, int length, int stride, TruffleString.Encoding encoding, int knownCodeRange, @Cached ConditionProfile brokenProfile) {
            assert (stride == 0);
            if (TStringGuards.isValidMultiByte(knownCodeRange) && a != null) {
                return TStringOps.calcStringAttributesUTF8(this, array, offset, length, true, offset + length == a.offset() + a.length(), brokenProfile);
            }
            return TStringOps.calcStringAttributesUTF8(this, array, offset, length, false, false, brokenProfile);
        }

        @Specialization(guards={"isUTF16(encoding)", "isValidMultiByte(knownCodeRange)"})
        long doUTF16Valid(AbstractTruffleString a, Object array, int offset, int length, int stride, TruffleString.Encoding encoding, int knownCodeRange) {
            assert (stride == 1);
            return TStringOps.calcStringAttributesUTF16(this, array, offset, length, true);
        }

        @Specialization(guards={"isUTF16(encoding)", "isBrokenMultiByteOrUnknown(knownCodeRange)"})
        long doUTF16Unknown(AbstractTruffleString a, Object array, int offset, int length, int stride, TruffleString.Encoding encoding, int knownCodeRange) {
            assert (stride == 1);
            return TStringOps.calcStringAttributesUTF16(this, array, offset, length, false);
        }

        @Specialization(guards={"stride == 2"})
        long doUTF32(AbstractTruffleString a, Object array, int offset, int length, int stride, TruffleString.Encoding encoding, int knownCodeRange) {
            assert (TStringGuards.isUTF32(encoding));
            return StringAttributes.create(length, TStringOps.calcStringAttributesUTF32(this, array, offset, length));
        }

        @Specialization(guards={"isUnsupportedEncoding(encoding)"})
        long doGeneric(AbstractTruffleString a, Object array, int offset, int length, int stride, TruffleString.Encoding encoding, int knownCodeRange, @Cached ConditionProfile validCharacterProfile, @Cached ConditionProfile fixedWidthProfile) {
            assert (stride == 0);
            return JCodings.getInstance().calcStringAttributes(this, array, offset, length, encoding, validCharacterProfile, fixedWidthProfile);
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class CalcStringAttributesNode
    extends Node {
        CalcStringAttributesNode() {
        }

        abstract long execute(AbstractTruffleString var1, Object var2, int var3, int var4, int var5, TruffleString.Encoding var6, int var7);

        @Specialization(guards={"is7Bit(knownCodeRange)"})
        long ascii(AbstractTruffleString a, Object array, int offset, int length, int stride, TruffleString.Encoding encoding, int knownCodeRange) {
            assert (length > 0);
            return StringAttributes.create(length, TSCodeRange.get7Bit());
        }

        @Specialization(guards={"!is7Bit(knownCodeRange)"})
        long notAscii(AbstractTruffleString a, Object array, int offset, int length, int stride, TruffleString.Encoding encoding, int knownCodeRange, @Cached CalcStringAttributesInnerNode calcNode) {
            assert (length > 0);
            return calcNode.execute(a, array, offset, length, stride, encoding, knownCodeRange);
        }

        static CalcStringAttributesNode getUncached() {
            return TStringInternalNodesFactory.CalcStringAttributesNodeGen.getUncached();
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class StrideFromCodeRangeNode
    extends Node {
        StrideFromCodeRangeNode() {
        }

        abstract int execute(int var1, TruffleString.Encoding var2);

        @Specialization(guards={"isUTF16(encoding)"})
        int doUTF16(int codeRange, TruffleString.Encoding encoding) {
            return Stride.fromCodeRangeUTF16(codeRange);
        }

        @Specialization(guards={"isUTF32(encoding)"})
        int doUTF32(int codeRange, TruffleString.Encoding encoding) {
            return Stride.fromCodeRangeUTF32(codeRange);
        }

        @Specialization(guards={"!isUTF16(encoding)", "!isUTF32(encoding)"})
        int doOther(int codeRange, TruffleString.Encoding encoding) {
            return 0;
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class LastIndexOfStringRawNode
    extends Node {
        LastIndexOfStringRawNode() {
        }

        abstract int execute(AbstractTruffleString var1, Object var2, int var3, AbstractTruffleString var4, Object var5, int var6, int var7, int var8, byte[] var9, TruffleString.Encoding var10);

        @Specialization(guards={"isSupportedEncoding(encoding) || isFixedWidth(codeRangeA)"})
        static int lios8SameEncoding(AbstractTruffleString a, Object arrayA, int codeRangeA, AbstractTruffleString b, Object arrayB, int codeRangeB, int fromIndex, int toIndex, byte[] mask, TruffleString.Encoding encoding, @Cached TStringOpsNodes.RawLastIndexOfStringNode indexOfStringNode) {
            assert (!b.isEmpty() && !TStringGuards.indexOfCannotMatch(codeRangeA, b, codeRangeB, mask, fromIndex - toIndex));
            return indexOfStringNode.execute(a, arrayA, b, arrayB, fromIndex, toIndex, mask);
        }

        @Specialization(guards={"isUnsupportedEncoding(encoding)", "!isFixedWidth(codeRangeA)"})
        int unsupported(AbstractTruffleString a, Object arrayA, int codeRangeA, AbstractTruffleString b, Object arrayB, int codeRangeB, int fromIndex, int toIndex, byte[] mask, TruffleString.Encoding encoding, @Cached TruffleStringIterator.NextNode nextNodeA, @Cached TruffleStringIterator.PreviousNode prevNodeA, @Cached TruffleStringIterator.PreviousNode prevNodeB) {
            assert (mask == null);
            assert (!b.isEmpty() && !TStringGuards.indexOfCannotMatch(codeRangeA, b, codeRangeB, mask, fromIndex - toIndex));
            TruffleStringIterator aIt = AbstractTruffleString.forwardIterator(a, arrayA, codeRangeA, encoding);
            TruffleStringIterator bIt = AbstractTruffleString.backwardIterator(b, arrayB, codeRangeB, encoding);
            return TruffleStringIterator.lastByteIndexOfString(this, aIt, bIt, fromIndex, toIndex, nextNodeA, prevNodeA, prevNodeB);
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class LastIndexOfStringNode
    extends Node {
        LastIndexOfStringNode() {
        }

        abstract int execute(AbstractTruffleString var1, Object var2, int var3, AbstractTruffleString var4, Object var5, int var6, int var7, int var8, TruffleString.Encoding var9);

        @Specialization(guards={"isFixedWidth(codeRangeA, codeRangeB)"})
        static int direct(AbstractTruffleString a, Object arrayA, int codeRangeA, AbstractTruffleString b, Object arrayB, int codeRangeB, int fromIndex, int toIndex, TruffleString.Encoding encoding, @Cached TStringOpsNodes.RawLastIndexOfStringNode indexOfStringNode) {
            assert (!b.isEmpty() && !TStringGuards.indexOfCannotMatch(codeRangeA, b, codeRangeB, fromIndex - toIndex, GetCodePointLengthNode.getUncached()));
            return indexOfStringNode.execute(a, arrayA, b, arrayB, fromIndex, toIndex, null);
        }

        @Specialization(guards={"!isFixedWidth(codeRangeA, codeRangeB)"})
        int decode(AbstractTruffleString a, Object arrayA, int codeRangeA, AbstractTruffleString b, Object arrayB, int codeRangeB, int fromIndex, int toIndex, TruffleString.Encoding encoding, @Cached TruffleStringIterator.NextNode nextNodeA, @Cached TruffleStringIterator.PreviousNode prevNodeA, @Cached TruffleStringIterator.PreviousNode prevNodeB) {
            assert (!b.isEmpty() && !TStringGuards.indexOfCannotMatch(codeRangeA, b, codeRangeB, fromIndex - toIndex, GetCodePointLengthNode.getUncached()));
            TruffleStringIterator aIt = AbstractTruffleString.forwardIterator(a, arrayA, codeRangeA, encoding);
            TruffleStringIterator bIt = AbstractTruffleString.backwardIterator(b, arrayB, codeRangeB, encoding);
            return TruffleStringIterator.lastIndexOfString(this, aIt, bIt, fromIndex, toIndex, nextNodeA, prevNodeA, prevNodeB);
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class IndexOfStringRawNode
    extends Node {
        IndexOfStringRawNode() {
        }

        abstract int execute(AbstractTruffleString var1, Object var2, int var3, AbstractTruffleString var4, Object var5, int var6, int var7, int var8, byte[] var9, TruffleString.Encoding var10);

        @Specialization(guards={"isSupportedEncoding(encoding) || isFixedWidth(codeRangeA)"})
        static int supported(AbstractTruffleString a, Object arrayA, int codeRangeA, AbstractTruffleString b, Object arrayB, int codeRangeB, int fromIndex, int toIndex, byte[] mask, TruffleString.Encoding encoding, @Cached TStringOpsNodes.RawIndexOfStringNode indexOfStringNode) {
            assert (!b.isEmpty() && !TStringGuards.indexOfCannotMatch(codeRangeA, b, codeRangeB, mask, toIndex - fromIndex));
            return indexOfStringNode.execute(a, arrayA, b, arrayB, fromIndex, toIndex, mask);
        }

        @Specialization(guards={"isUnsupportedEncoding(encoding)", "!isFixedWidth(codeRangeA)"})
        int unsupported(AbstractTruffleString a, Object arrayA, int codeRangeA, AbstractTruffleString b, Object arrayB, int codeRangeB, int fromIndex, int toIndex, byte[] mask, TruffleString.Encoding encoding, @Cached TruffleStringIterator.NextNode nextNodeA, @Cached TruffleStringIterator.NextNode nextNodeB) {
            assert (mask == null);
            assert (!b.isEmpty() && !TStringGuards.indexOfCannotMatch(codeRangeA, b, codeRangeB, mask, toIndex - fromIndex));
            TruffleStringIterator aIt = AbstractTruffleString.forwardIterator(a, arrayA, codeRangeA, encoding);
            TruffleStringIterator bIt = AbstractTruffleString.forwardIterator(b, arrayB, codeRangeB, encoding);
            return TruffleStringIterator.byteIndexOfString(this, aIt, bIt, fromIndex, toIndex, nextNodeA, nextNodeB);
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class IndexOfStringNode
    extends Node {
        IndexOfStringNode() {
        }

        abstract int execute(AbstractTruffleString var1, Object var2, int var3, AbstractTruffleString var4, Object var5, int var6, int var7, int var8, TruffleString.Encoding var9);

        @Specialization(guards={"isFixedWidth(codeRangeA, codeRangeB)"})
        static int direct(AbstractTruffleString a, Object arrayA, int codeRangeA, AbstractTruffleString b, Object arrayB, int codeRangeB, int fromIndex, int toIndex, TruffleString.Encoding encoding, @Cached TStringOpsNodes.RawIndexOfStringNode indexOfStringNode) {
            assert (!b.isEmpty() && !TStringGuards.indexOfCannotMatch(codeRangeA, b, codeRangeB, toIndex - fromIndex, GetCodePointLengthNode.getUncached()));
            return indexOfStringNode.execute(a, arrayA, b, arrayB, fromIndex, toIndex, null);
        }

        @Specialization(guards={"!isFixedWidth(codeRangeA, codeRangeB)"})
        int decode(AbstractTruffleString a, Object arrayA, int codeRangeA, AbstractTruffleString b, Object arrayB, int codeRangeB, int fromIndex, int toIndex, TruffleString.Encoding encoding, @Cached TruffleStringIterator.NextNode nextNodeA, @Cached TruffleStringIterator.NextNode nextNodeB) {
            assert (!b.isEmpty() && !TStringGuards.indexOfCannotMatch(codeRangeA, b, codeRangeB, toIndex - fromIndex, GetCodePointLengthNode.getUncached()));
            TruffleStringIterator aIt = AbstractTruffleString.forwardIterator(a, arrayA, codeRangeA, encoding);
            TruffleStringIterator bIt = AbstractTruffleString.forwardIterator(b, arrayB, codeRangeB, encoding);
            return TruffleStringIterator.indexOfString(this, aIt, bIt, fromIndex, toIndex, nextNodeA, nextNodeB);
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class RegionEqualsNode
    extends Node {
        RegionEqualsNode() {
        }

        abstract boolean execute(AbstractTruffleString var1, Object var2, int var3, int var4, AbstractTruffleString var5, Object var6, int var7, int var8, int var9, TruffleString.Encoding var10);

        @Specialization(guards={"isFixedWidth(codeRangeA, codeRangeB)"})
        boolean direct(AbstractTruffleString a, Object arrayA, int codeRangeA, int fromIndexA, AbstractTruffleString b, Object arrayB, int codeRangeB, int fromIndexB, int length, TruffleString.Encoding encoding) {
            return TStringOps.regionEqualsWithOrMaskWithStride(this, a, arrayA, a.stride(), fromIndexA, b, arrayB, b.stride(), fromIndexB, null, length);
        }

        @Specialization(guards={"!isFixedWidth(codeRangeA, codeRangeB)"})
        boolean decode(AbstractTruffleString a, Object arrayA, int codeRangeA, int fromIndexA, AbstractTruffleString b, Object arrayB, int codeRangeB, int fromIndexB, int length, TruffleString.Encoding encoding, @Cached TruffleStringIterator.NextNode nextNodeA, @Cached TruffleStringIterator.NextNode nextNodeB) {
            int i;
            TruffleStringIterator aIt = AbstractTruffleString.forwardIterator(a, arrayA, codeRangeA, encoding);
            TruffleStringIterator bIt = AbstractTruffleString.forwardIterator(b, arrayB, codeRangeB, encoding);
            for (i = 0; i < fromIndexA; ++i) {
                if (!aIt.hasNext()) {
                    return false;
                }
                nextNodeA.execute(aIt);
                TStringConstants.truffleSafePointPoll(this, i + 1);
            }
            for (i = 0; i < fromIndexB; ++i) {
                if (!bIt.hasNext()) {
                    return false;
                }
                nextNodeB.execute(bIt);
                TStringConstants.truffleSafePointPoll(this, i + 1);
            }
            for (i = 0; i < length; ++i) {
                if (!aIt.hasNext() || !bIt.hasNext() || nextNodeA.execute(aIt) != nextNodeB.execute(bIt)) {
                    return false;
                }
                TStringConstants.truffleSafePointPoll(this, i + 1);
            }
            return true;
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class ConcatMaterializeBytesNode
    extends Node {
        ConcatMaterializeBytesNode() {
        }

        abstract byte[] execute(AbstractTruffleString var1, Object var2, AbstractTruffleString var3, Object var4, TruffleString.Encoding var5, int var6, int var7);

        @Specialization(guards={"isUTF16(encoding) || isUTF32(encoding)"})
        byte[] doWithCompression(AbstractTruffleString a, Object arrayA, AbstractTruffleString b, Object arrayB, TruffleString.Encoding encoding, int concatLength, int concatStride) {
            byte[] bytes = new byte[concatLength << concatStride];
            TStringOps.arraycopyWithStride(this, arrayA, a.offset(), a.stride(), 0, bytes, 0, concatStride, 0, a.length());
            TStringOps.arraycopyWithStride(this, arrayB, b.offset(), b.stride(), 0, bytes, 0, concatStride, a.length(), b.length());
            return bytes;
        }

        @Specialization(guards={"!isUTF16(encoding)", "!isUTF32(encoding)"})
        byte[] doNoCompression(AbstractTruffleString a, Object arrayA, AbstractTruffleString b, Object arrayB, TruffleString.Encoding encoding, int concatLength, int concatStride) {
            assert (TStringGuards.isStride0(a));
            assert (TStringGuards.isStride0(b));
            assert (concatStride == 0);
            byte[] bytes = new byte[concatLength];
            TStringOps.arraycopyWithStride(this, arrayA, a.offset(), 0, 0, bytes, 0, 0, 0, a.length());
            TStringOps.arraycopyWithStride(this, arrayB, b.offset(), 0, 0, bytes, 0, 0, a.length(), b.length());
            return bytes;
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class ConcatEagerNode
    extends Node {
        ConcatEagerNode() {
        }

        abstract TruffleString execute(AbstractTruffleString var1, AbstractTruffleString var2, TruffleString.Encoding var3, int var4, int var5, int var6);

        @Specialization
        static TruffleString concat(AbstractTruffleString a, AbstractTruffleString b, TruffleString.Encoding encoding, int concatLength, int concatStride, int concatCodeRange, @Cached TruffleString.ToIndexableNode toIndexableNodeA, @Cached TruffleString.ToIndexableNode toIndexableNodeB, @Cached GetCodePointLengthNode getCodePointLengthANode, @Cached GetCodePointLengthNode getCodePointLengthBNode, @Cached ConcatMaterializeBytesNode materializeBytesNode, @Cached CalcStringAttributesNode calculateAttributesNode, @Cached ConditionProfile brokenProfile) {
            int codeRange;
            int codePointLength;
            byte[] bytes = materializeBytesNode.execute(a, toIndexableNodeA.execute(a, a.data()), b, toIndexableNodeB.execute(b, b.data()), encoding, concatLength, concatStride);
            if (brokenProfile.profile(TStringGuards.isBrokenMultiByte(concatCodeRange))) {
                long attrs = calculateAttributesNode.execute(null, bytes, 0, concatLength, concatStride, encoding, TSCodeRange.getUnknown());
                codePointLength = StringAttributes.getCodePointLength(attrs);
                codeRange = StringAttributes.getCodeRange(attrs);
            } else {
                codePointLength = getCodePointLengthANode.execute(a) + getCodePointLengthBNode.execute(b);
                codeRange = concatCodeRange;
            }
            return TruffleString.createFromByteArray(bytes, concatLength, concatStride, encoding, codePointLength, codeRange);
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class SubstringNode
    extends Node {
        SubstringNode() {
        }

        abstract TruffleString execute(AbstractTruffleString var1, Object var2, int var3, TruffleString.Encoding var4, int var5, int var6, boolean var7);

        @Specialization(guards={"length == 0"})
        static TruffleString lengthZero(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int fromIndex, int length, boolean lazy) {
            return encoding.getEmpty();
        }

        @Specialization(guards={"fromIndex == 0", "length == length(a)"})
        static TruffleString sameStr(TruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int fromIndex, int length, boolean lazy) {
            return a;
        }

        @Specialization(guards={"length > 0", "length != length(a) || a.isMutable()", "!lazy"})
        static TruffleString materializeSubstring(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int fromIndex, int length, boolean lazy, @Cached CreateSubstringNode createSubstringNode) {
            return createSubstringNode.execute(a, arrayA, a.offset() + (fromIndex << a.stride()), length, a.stride(), encoding, codeRangeA);
        }

        @Specialization(guards={"length > 0", "length != length(a)", "lazy"})
        TruffleString createLazySubstring(TruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int fromIndex, int length, boolean lazy, @Cached CalcStringAttributesNode calcAttributesNode, @Cached ConditionProfile stride1MustMaterializeProfile, @Cached ConditionProfile stride2MustMaterializeProfile) {
            Object array;
            int offset;
            int stride;
            int lazyOffset = a.offset() + (fromIndex << a.stride());
            long attrs = calcAttributesNode.execute(a, arrayA, lazyOffset, length, a.stride(), encoding, codeRangeA);
            int codeRange = StringAttributes.getCodeRange(attrs);
            int codePointLength = StringAttributes.getCodePointLength(attrs);
            if (stride1MustMaterializeProfile.profile(a.stride() == 1 && TSCodeRange.isMoreRestrictiveOrEqual(codeRange, TSCodeRange.get8Bit()))) {
                assert (TStringGuards.isUTF16Or32(encoding));
                stride = 0;
                offset = 0;
                byte[] newBytes = new byte[length];
                TStringOps.arraycopyWithStride(this, arrayA, lazyOffset, 1, 0, newBytes, offset, 0, 0, length);
                array = newBytes;
            } else if (stride2MustMaterializeProfile.profile(a.stride() == 2 && TSCodeRange.isMoreRestrictiveOrEqual(codeRange, TSCodeRange.get16Bit()))) {
                assert (TStringGuards.isUTF32(encoding));
                stride = Stride.fromCodeRangeUTF32(StringAttributes.getCodeRange(attrs));
                offset = 0;
                byte[] newBytes = new byte[length << stride];
                if (stride == 0) {
                    TStringOps.arraycopyWithStride(this, arrayA, lazyOffset, 2, 0, newBytes, offset, 0, 0, length);
                } else {
                    assert (stride == 1);
                    TStringOps.arraycopyWithStride(this, arrayA, lazyOffset, 2, 0, newBytes, offset, 1, 0, length);
                }
                array = newBytes;
            } else {
                array = TStringGuards.isUnsupportedEncoding(encoding) && arrayA instanceof AbstractTruffleString.NativePointer ? ((AbstractTruffleString.NativePointer)arrayA).copy(lazyOffset) : arrayA;
                offset = lazyOffset;
                stride = a.stride();
            }
            return TruffleString.createFromArray(array, offset, length, stride, encoding, codePointLength, codeRange);
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class LastIndexOfCodePointRawNode
    extends Node {
        LastIndexOfCodePointRawNode() {
        }

        abstract int execute(AbstractTruffleString var1, Object var2, int var3, TruffleString.Encoding var4, int var5, int var6, int var7);

        @Specialization(guards={"isFixedWidth(codeRangeA)"})
        static int utf8Fixed(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int codepoint, int fromIndex, int toIndex, @Cached @Cached.Shared(value="lastIndexOfNode") TStringOpsNodes.RawLastIndexOfCodePointNode lastIndexOfNode) {
            return TStringInternalNodes.lastIndexOfFixedWidth(a, arrayA, codeRangeA, codepoint, fromIndex, toIndex, lastIndexOfNode);
        }

        @Specialization(guards={"isUTF8(encoding)", "!isFixedWidth(codeRangeA)"})
        int utf8Variable(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int codepoint, int fromIndex, int toIndex, @Cached @Cached.Shared(value="lastIndexOfNode") TStringOpsNodes.RawLastIndexOfCodePointNode lastIndexOfNode) {
            int encodedSize = Encodings.utf8EncodedSize(codepoint);
            if (encodedSize > fromIndex - toIndex) {
                return -1;
            }
            if (encodedSize == 1) {
                return TStringInternalNodes.lastIndexOfFixedWidth(a, arrayA, codeRangeA, codepoint, fromIndex, toIndex, lastIndexOfNode);
            }
            byte[] encoded = Encodings.utf8EncodeNonAscii(codepoint, encodedSize);
            TruffleString b = TruffleString.createFromByteArray(encoded, encoded.length, 0, TruffleString.Encoding.UTF_8, 1, TSCodeRange.getValidMultiByte());
            return TStringOps.lastIndexOfStringWithOrMaskWithStride(this, a, arrayA, 0, b, encoded, 0, fromIndex, toIndex, null);
        }

        @Specialization(guards={"isUTF16(encoding)", "!isFixedWidth(codeRangeA)"})
        int utf16Variable(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int codepoint, int fromIndex, int toIndex, @Cached @Cached.Shared(value="lastIndexOfNode") TStringOpsNodes.RawLastIndexOfCodePointNode lastIndexOfNode) {
            assert (TStringGuards.isStride1(a));
            int encodedSize = Encodings.utf16EncodedSize(codepoint);
            if (encodedSize > fromIndex - toIndex) {
                return -1;
            }
            if (encodedSize == 1) {
                return TStringInternalNodes.lastIndexOfFixedWidth(a, arrayA, codeRangeA, codepoint, fromIndex, toIndex, lastIndexOfNode);
            }
            return TStringOps.lastIndexOf2ConsecutiveWithOrMaskWithStride(this, a, arrayA, 1, fromIndex, toIndex, Character.highSurrogate(codepoint), Character.lowSurrogate(codepoint), 0, 0);
        }

        @Specialization(guards={"isUnsupportedEncoding(encoding)", "!isFixedWidth(codeRangeA)"})
        int unsupported(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int codepoint, int fromIndex, int toIndex, @Cached TruffleStringIterator.PreviousNode prevNode) {
            TruffleStringIterator it = TruffleString.backwardIterator(a, arrayA, codeRangeA, encoding);
            it.setRawIndex(fromIndex);
            int loopCount = 0;
            while (it.hasPrevious() && it.getRawIndex() >= toIndex) {
                if (prevNode.execute(it) == codepoint) {
                    return it.getRawIndex();
                }
                TStringConstants.truffleSafePointPoll(this, ++loopCount);
            }
            return -1;
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class LastIndexOfCodePointNode
    extends Node {
        LastIndexOfCodePointNode() {
        }

        abstract int execute(AbstractTruffleString var1, Object var2, int var3, TruffleString.Encoding var4, int var5, int var6, int var7);

        @Specialization(guards={"isFixedWidth(codeRangeA)"})
        static int doFixedWidth(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int codepoint, int fromIndex, int toIndex, @Cached TStringOpsNodes.RawLastIndexOfCodePointNode lastIndexOfNode) {
            return TStringInternalNodes.lastIndexOfFixedWidth(a, arrayA, codeRangeA, codepoint, fromIndex, toIndex, lastIndexOfNode);
        }

        @Specialization(guards={"!isFixedWidth(codeRangeA)"})
        int decode(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int codepoint, int fromIndex, int toIndex, @Cached TruffleStringIterator.NextNode nextNode) {
            return TruffleStringIterator.lastIndexOf(this, AbstractTruffleString.forwardIterator(a, arrayA, codeRangeA, encoding), codepoint, fromIndex, toIndex, nextNode);
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class IndexOfCodePointRawNode
    extends Node {
        IndexOfCodePointRawNode() {
        }

        abstract int execute(AbstractTruffleString var1, Object var2, int var3, TruffleString.Encoding var4, int var5, int var6, int var7);

        @Specialization(guards={"isFixedWidth(codeRangeA)"})
        static int utf8Fixed(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int codepoint, int fromIndex, int toIndex, @Cached TStringOpsNodes.RawIndexOfCodePointNode indexOfNode) {
            return TStringInternalNodes.indexOfFixedWidth(a, arrayA, codeRangeA, codepoint, fromIndex, toIndex, indexOfNode);
        }

        @Specialization(guards={"isUTF8(encoding)", "!isFixedWidth(codeRangeA)"})
        int utf8Variable(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int codepoint, int fromIndex, int toIndex) {
            assert (TStringGuards.isStride0(a));
            int encodedSize = Encodings.utf8EncodedSize(codepoint);
            if (encodedSize > toIndex - fromIndex) {
                return -1;
            }
            if (encodedSize == 1) {
                return TStringOps.indexOfCodePointWithStride(this, a, arrayA, 0, fromIndex, toIndex, codepoint);
            }
            byte[] encoded = Encodings.utf8EncodeNonAscii(codepoint, encodedSize);
            TruffleString b = TruffleString.createFromByteArray(encoded, encoded.length, 0, TruffleString.Encoding.UTF_8, 1, TSCodeRange.getValidMultiByte());
            return TStringOps.indexOfStringWithOrMaskWithStride(this, a, arrayA, 0, b, encoded, 0, fromIndex, toIndex, null);
        }

        @Specialization(guards={"isUTF16(encoding)", "!isFixedWidth(codeRangeA)"})
        int utf16Variable(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int codepoint, int fromIndex, int toIndex) {
            assert (TStringGuards.isStride1(a));
            int encodedSize = Encodings.utf16EncodedSize(codepoint);
            if (encodedSize > toIndex - fromIndex) {
                return -1;
            }
            if (encodedSize == 1) {
                return TStringOps.indexOfCodePointWithStride(this, a, arrayA, 1, fromIndex, toIndex, codepoint);
            }
            return TStringOps.indexOf2ConsecutiveWithStride(this, a, arrayA, 1, fromIndex, toIndex, Character.highSurrogate(codepoint), Character.lowSurrogate(codepoint));
        }

        @Specialization(guards={"isUnsupportedEncoding(encoding)", "!isFixedWidth(codeRangeA)"})
        int unsupported(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int codepoint, int fromIndex, int toIndex, @Cached TruffleStringIterator.NextNode nextNode) {
            TruffleStringIterator it = AbstractTruffleString.forwardIterator(a, arrayA, codeRangeA, encoding);
            it.setRawIndex(fromIndex);
            int loopCount = 0;
            while (it.hasNext() && it.getRawIndex() < toIndex) {
                int ret = it.getRawIndex();
                if (nextNode.execute(it) == codepoint) {
                    return ret;
                }
                TStringConstants.truffleSafePointPoll(this, ++loopCount);
            }
            return -1;
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class IndexOfCodePointNode
    extends Node {
        IndexOfCodePointNode() {
        }

        abstract int execute(AbstractTruffleString var1, Object var2, int var3, TruffleString.Encoding var4, int var5, int var6, int var7);

        @Specialization(guards={"isFixedWidth(codeRangeA)"})
        static int doFixedWidth(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int codepoint, int fromIndex, int toIndex, @Cached TStringOpsNodes.RawIndexOfCodePointNode indexOfNode) {
            return TStringInternalNodes.indexOfFixedWidth(a, arrayA, codeRangeA, codepoint, fromIndex, toIndex, indexOfNode);
        }

        @Specialization(guards={"!isFixedWidth(codeRangeA)"})
        int decode(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int codepoint, int fromIndex, int toIndex, @Cached TruffleStringIterator.NextNode nextNode) {
            return TruffleStringIterator.indexOf(this, AbstractTruffleString.forwardIterator(a, arrayA, codeRangeA, encoding), codepoint, fromIndex, toIndex, nextNode);
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class CodePointAtRawNode
    extends Node {
        CodePointAtRawNode() {
        }

        abstract int execute(AbstractTruffleString var1, Object var2, int var3, TruffleString.Encoding var4, int var5, TruffleString.ErrorHandling var6);

        @Specialization(guards={"isUTF16(encoding)"})
        static int utf16(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int i, TruffleString.ErrorHandling errorHandling, @Cached ConditionProfile fixedWidthProfile, @Cached ConditionProfile validProfile, @Cached ConditionProfile stride0Profile) {
            if (fixedWidthProfile.profile(TStringGuards.isFixedWidth(codeRangeA))) {
                if (stride0Profile.profile(TStringGuards.isStride0(a))) {
                    return TStringOps.readS0(a, arrayA, i);
                }
                assert (TStringGuards.isStride1(a));
                return TStringOps.readS1(a, arrayA, i);
            }
            if (validProfile.profile(TStringGuards.isValidMultiByte(codeRangeA))) {
                return Encodings.utf16DecodeValid(a, arrayA, i);
            }
            assert (TStringGuards.isBrokenMultiByte(codeRangeA));
            return Encodings.utf16DecodeBroken(a, arrayA, i, errorHandling);
        }

        @Specialization(guards={"isUTF32(encoding)"})
        static int utf32(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int i, TruffleString.ErrorHandling errorHandling, @Cached ConditionProfile stride0Profile, @Cached ConditionProfile stride1Profile) {
            if (stride0Profile.profile(TStringGuards.isStride0(a))) {
                return TStringOps.readS0(a, arrayA, i);
            }
            if (stride1Profile.profile(TStringGuards.isStride1(a))) {
                char c = TStringOps.readS1(a, arrayA, i);
                if (errorHandling == TruffleString.ErrorHandling.RETURN_NEGATIVE && Encodings.isUTF16Surrogate(c)) {
                    return -1;
                }
                return c;
            }
            assert (TStringGuards.isStride2(a));
            int c = TStringOps.readS2(a, arrayA, i);
            if (errorHandling == TruffleString.ErrorHandling.RETURN_NEGATIVE && !Encodings.isValidUnicodeCodepoint(c)) {
                return -1;
            }
            return c;
        }

        @Specialization(guards={"isUTF8(encoding)"})
        static int utf8(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int i, TruffleString.ErrorHandling errorHandling, @Cached ConditionProfile fixedWidthProfile, @Cached ConditionProfile validProfile) {
            if (fixedWidthProfile.profile(TStringGuards.is7Bit(codeRangeA))) {
                return TStringOps.readS0(a, arrayA, i);
            }
            if (validProfile.profile(TStringGuards.isValidMultiByte(codeRangeA))) {
                return Encodings.utf8DecodeValid(a, arrayA, i);
            }
            assert (TStringGuards.isBrokenMultiByte(codeRangeA));
            return Encodings.utf8DecodeBroken(a, arrayA, i, errorHandling);
        }

        @Specialization(guards={"!isUTF16Or32(encoding)", "!isUTF8(encoding)", "isBytes(encoding) || is7Or8Bit(codeRangeA)"})
        static int doFixed(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int i, TruffleString.ErrorHandling errorHandling) {
            assert (TStringGuards.isStride0(a));
            return TStringOps.readS0(a, arrayA, i);
        }

        @Specialization(guards={"isAscii(encoding)", "!is7Or8Bit(codeRangeA)"})
        static int doAsciiBroken(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int i, TruffleString.ErrorHandling errorHandling) {
            assert (TStringGuards.isStride0(a));
            int c = TStringOps.readS0(a, arrayA, i);
            if (errorHandling == TruffleString.ErrorHandling.RETURN_NEGATIVE && c > 127) {
                return -1;
            }
            assert (errorHandling == TruffleString.ErrorHandling.BEST_EFFORT);
            return c;
        }

        @Specialization(guards={"isUnsupportedEncoding(encoding)", "!is7Or8Bit(codeRangeA)"})
        static int unsupported(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int i, TruffleString.ErrorHandling errorHandling) {
            return JCodings.getInstance().decode(a, JCodings.asByteArray(arrayA), i, JCodings.getInstance().get(encoding), errorHandling);
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class CodePointAtNode
    extends Node {
        CodePointAtNode() {
        }

        abstract int execute(AbstractTruffleString var1, Object var2, int var3, TruffleString.Encoding var4, int var5, TruffleString.ErrorHandling var6);

        @Specialization(guards={"isUTF16(encoding)"})
        int utf16(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int i, TruffleString.ErrorHandling errorHandling, @Cached ConditionProfile fixedWidthProfile, @Cached ConditionProfile stride0Profile, @Cached ConditionProfile validProfile) {
            if (fixedWidthProfile.profile(TStringGuards.isFixedWidth(codeRangeA))) {
                if (stride0Profile.profile(TStringGuards.isStride0(a))) {
                    return TStringOps.readS0(a, arrayA, i);
                }
                assert (TStringGuards.isStride1(a));
                return TStringOps.readS1(a, arrayA, i);
            }
            if (validProfile.profile(TStringGuards.isValidMultiByte(codeRangeA))) {
                assert (TStringGuards.isStride1(a));
                return Encodings.utf16DecodeValid(a, arrayA, Encodings.utf16ValidCodePointToCharIndex(this, a, arrayA, i));
            }
            assert (TStringGuards.isStride1(a));
            assert (TStringGuards.isBrokenMultiByte(codeRangeA));
            return Encodings.utf16DecodeBroken(a, arrayA, Encodings.utf16BrokenCodePointToCharIndex(this, a, arrayA, i), errorHandling);
        }

        @Specialization(guards={"isUTF32(encoding)"})
        static int utf32(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int i, TruffleString.ErrorHandling errorHandling, @Cached ConditionProfile stride0Profile, @Cached ConditionProfile stride1Profile) {
            return CodePointAtRawNode.utf32(a, arrayA, codeRangeA, encoding, i, errorHandling, stride0Profile, stride1Profile);
        }

        @Specialization(guards={"isUTF8(encoding)"})
        int utf8(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int i, TruffleString.ErrorHandling errorHandling, @Cached ConditionProfile fixedWidthProfile, @Cached ConditionProfile validProfile) {
            if (fixedWidthProfile.profile(TStringGuards.is7Bit(codeRangeA))) {
                return TStringOps.readS0(a, arrayA, i);
            }
            int byteIndex = Encodings.utf8CodePointToByteIndex(this, a, arrayA, i);
            if (validProfile.profile(TStringGuards.isValidMultiByte(codeRangeA))) {
                return Encodings.utf8DecodeValid(a, arrayA, byteIndex);
            }
            assert (TStringGuards.isBrokenMultiByte(codeRangeA));
            return Encodings.utf8DecodeBroken(a, arrayA, byteIndex, errorHandling);
        }

        @Specialization(guards={"!isUTF16Or32(encoding)", "!isUTF8(encoding)", "isBytes(encoding) || is7Or8Bit(codeRangeA)"})
        static int doFixed(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int i, TruffleString.ErrorHandling errorHandling) {
            return CodePointAtRawNode.doFixed(a, arrayA, codeRangeA, encoding, i, errorHandling);
        }

        @Specialization(guards={"isAscii(encoding)", "!is7Or8Bit(codeRangeA)"})
        static int doAsciiBroken(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int i, TruffleString.ErrorHandling errorHandling) {
            return CodePointAtRawNode.doAsciiBroken(a, arrayA, codeRangeA, encoding, i, errorHandling);
        }

        @Specialization(guards={"isUnsupportedEncoding(encoding)", "!is7Or8Bit(codeRangeA)"})
        int unsupported(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int i, TruffleString.ErrorHandling errorHandling) {
            assert (TStringGuards.isStride0(a));
            JCodings.Encoding jCoding = JCodings.getInstance().get(encoding);
            byte[] bytes = JCodings.asByteArray(arrayA);
            return JCodings.getInstance().decode(a, bytes, JCodings.getInstance().codePointIndexToRaw(this, a, bytes, 0, i, false, jCoding), jCoding, errorHandling);
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class ReadByteNode
    extends Node {
        ReadByteNode() {
        }

        abstract int execute(AbstractTruffleString var1, Object var2, int var3, TruffleString.Encoding var4);

        @Specialization(guards={"isUTF16(encoding)"})
        static int doUTF16(AbstractTruffleString a, Object arrayA, int i, TruffleString.Encoding encoding, @Cached ConditionProfile stride0Profile) {
            int index;
            a.boundsCheckByteIndexUTF16(i);
            if (stride0Profile.profile(TStringGuards.isStride0(a))) {
                if (TStringGuards.bigEndian() == ((i & 1) == 0)) {
                    return 0;
                }
                index = i >> 1;
            } else {
                assert (TStringGuards.isStride1(a));
                index = i;
            }
            return TStringOps.readS0(arrayA, a.offset(), a.length() << a.stride(), index);
        }

        @Specialization(guards={"isUTF32(encoding)"})
        static int doUTF32(AbstractTruffleString a, Object arrayA, int i, TruffleString.Encoding encoding, @Cached ConditionProfile stride0Profile, @Cached ConditionProfile stride1Profile) {
            int index;
            a.boundsCheckByteIndexUTF32(i);
            if (stride0Profile.profile(TStringGuards.isStride0(a))) {
                if ((i & 3) != (TStringGuards.bigEndian() ? 3 : 0)) {
                    return 0;
                }
                index = i >> 2;
            } else if (stride1Profile.profile(TStringGuards.isStride1(a))) {
                if (TStringGuards.bigEndian() == ((i & 2) == 0)) {
                    return 0;
                }
                index = TStringGuards.bigEndian() ? i >> 1 | i & 1 : i >> 1 & 0xFFFFFFFE | i & 1;
            } else {
                assert (TStringGuards.isStride2(a));
                index = i;
            }
            return TStringOps.readS0(arrayA, a.offset(), a.length() << a.stride(), index);
        }

        @Specialization(guards={"!isUTF16Or32(encoding)"})
        static int doRest(AbstractTruffleString a, Object arrayA, int i, TruffleString.Encoding encoding) {
            a.boundsCheckByteIndexS0(i);
            return TStringOps.readS0(a, arrayA, i);
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class CodePointIndexToRawNode
    extends Node {
        CodePointIndexToRawNode() {
        }

        abstract int execute(AbstractTruffleString var1, Object var2, int var3, TruffleString.Encoding var4, int var5, int var6, boolean var7);

        @Specialization(guards={"isFixedWidth(codeRangeA)"})
        int doFixed(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int extraOffsetRaw, int index, boolean isLength) {
            return index;
        }

        @Specialization(guards={"isUTF8(encoding)", "isValidMultiByte(codeRangeA)"})
        int utf8Valid(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int extraOffsetRaw, int index, boolean isLength) {
            assert (TStringGuards.isStride0(a));
            int cpi = 0;
            for (int i = extraOffsetRaw; i < a.length(); ++i) {
                if (!Encodings.isUTF8ContinuationByte(TStringOps.readS0(a, arrayA, i))) {
                    if (cpi == index) {
                        return i - extraOffsetRaw;
                    }
                    ++cpi;
                }
                TStringConstants.truffleSafePointPoll(this, i + 1);
            }
            return CodePointIndexToRawNode.atEnd(a, extraOffsetRaw, index, isLength, cpi);
        }

        @Specialization(guards={"isUTF8(encoding)", "isBrokenMultiByte(codeRangeA)"})
        int utf8Broken(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int extraOffsetRaw, int index, boolean isLength) {
            assert (TStringGuards.isStride0(a));
            int cpi = 0;
            for (int i = extraOffsetRaw; i < a.length(); i += Encodings.utf8GetCodePointLength(a, arrayA, i, TruffleString.ErrorHandling.BEST_EFFORT)) {
                if (cpi == index) {
                    return i - extraOffsetRaw;
                }
                TStringConstants.truffleSafePointPoll(this, ++cpi);
            }
            return CodePointIndexToRawNode.atEnd(a, extraOffsetRaw, index, isLength, cpi);
        }

        @Specialization(guards={"isUTF16(encoding)", "isValidMultiByte(codeRangeA)"})
        int utf16Valid(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int extraOffsetRaw, int index, boolean isLength) {
            assert (TStringGuards.isStride1(a));
            int cpi = 0;
            for (int i = extraOffsetRaw; i < a.length(); ++i) {
                if (!Encodings.isUTF16LowSurrogate(TStringOps.readS1(a, arrayA, i))) {
                    if (cpi == index) {
                        return i - extraOffsetRaw;
                    }
                    ++cpi;
                }
                TStringConstants.truffleSafePointPoll(this, i + 1);
            }
            return CodePointIndexToRawNode.atEnd(a, extraOffsetRaw, index, isLength, cpi);
        }

        @Specialization(guards={"isUTF16(encoding)", "isBrokenMultiByte(codeRangeA)"})
        int utf16Broken(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int extraOffsetRaw, int index, boolean isLength) {
            assert (TStringGuards.isStride1(a));
            int cpi = 0;
            for (int i = extraOffsetRaw; i < a.length(); ++i) {
                if (i <= extraOffsetRaw || !Encodings.isUTF16LowSurrogate(TStringOps.readS1(a, arrayA, i)) || !Encodings.isUTF16HighSurrogate(TStringOps.readS1(a, arrayA, i - 1))) {
                    if (cpi == index) {
                        return i - extraOffsetRaw;
                    }
                    ++cpi;
                }
                TStringConstants.truffleSafePointPoll(this, i + 1);
            }
            return CodePointIndexToRawNode.atEnd(a, extraOffsetRaw, index, isLength, cpi);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization(guards={"isUnsupportedEncoding(encoding)"})
        int unsupported(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int extraOffsetRaw, int index, boolean isLength) {
            JCodings.Encoding jCoding = JCodings.getInstance().get(encoding);
            return JCodings.getInstance().codePointIndexToRaw(this, a, JCodings.asByteArray(arrayA), extraOffsetRaw, index, isLength, jCoding);
        }

        static int atEnd(AbstractTruffleString a, int extraOffsetRaw, int index, boolean isLength, int cpi) {
            if (isLength && cpi == index) {
                return a.length() - extraOffsetRaw;
            }
            throw InternalErrors.indexOutOfBounds();
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class RawIndexToCodePointIndexNode
    extends Node {
        RawIndexToCodePointIndexNode() {
        }

        abstract int execute(AbstractTruffleString var1, Object var2, int var3, TruffleString.Encoding var4, int var5, int var6);

        @Specialization(guards={"isFixedWidth(codeRangeA)"})
        int doFixed(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int offset, int index) {
            return index;
        }

        @Specialization(guards={"isUTF8(encoding)", "isValidMultiByte(codeRangeA)"})
        int utf8Valid(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int offset, int index, @Cached ConditionProfile brokenProfile) {
            return StringAttributes.getCodePointLength(TStringOps.calcStringAttributesUTF8(this, arrayA, offset, index, true, offset + index == a.offset() + a.length(), brokenProfile));
        }

        @Specialization(guards={"isUTF8(encoding)", "isBrokenMultiByte(codeRangeA)"})
        int utf8Broken(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int offset, int index, @Cached ConditionProfile brokenProfile) {
            return StringAttributes.getCodePointLength(TStringOps.calcStringAttributesUTF8(this, arrayA, offset, index, false, false, brokenProfile));
        }

        @Specialization(guards={"isUTF16(encoding)", "isValidMultiByte(codeRangeA)"})
        int utf16Valid(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int offset, int index) {
            assert (TStringGuards.isStride1(a));
            return StringAttributes.getCodePointLength(TStringOps.calcStringAttributesUTF16(this, arrayA, offset, index, true));
        }

        @Specialization(guards={"isUTF16(encoding)", "isBrokenMultiByte(codeRangeA)"})
        int utf16Broken(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int offset, int index) {
            assert (TStringGuards.isStride1(a));
            return StringAttributes.getCodePointLength(TStringOps.calcStringAttributesUTF16(this, arrayA, offset, index, false));
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization(guards={"isUnsupportedEncoding(encoding)"})
        int unsupported(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int offset, int index, @Cached ConditionProfile validProfile, @Cached ConditionProfile fixedWidthProfile) {
            return StringAttributes.getCodePointLength(JCodings.getInstance().calcStringAttributes(this, arrayA, offset, index, encoding, validProfile, fixedWidthProfile));
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class ByteLengthOfCodePointNode
    extends Node {
        ByteLengthOfCodePointNode() {
        }

        abstract int execute(AbstractTruffleString var1, Object var2, int var3, TruffleString.Encoding var4, int var5, TruffleString.ErrorHandling var6);

        @Specialization(guards={"isFixedWidth(codeRangeA)", "isBestEffort(errorHandling)"})
        int doFixed(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int index, TruffleString.ErrorHandling errorHandling) {
            return 1 << encoding.naturalStride;
        }

        @Specialization(guards={"isUpToValidFixedWidth(codeRangeA)", "isReturnNegative(errorHandling)"})
        int doFixedValidReturnNegative(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int index, TruffleString.ErrorHandling errorHandling) {
            return 1 << encoding.naturalStride;
        }

        @Specialization(guards={"isAscii(encoding)", "isBrokenFixedWidth(codeRangeA)", "isReturnNegative(errorHandling)"})
        int doASCIIBrokenReturnNegative(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int index, TruffleString.ErrorHandling errorHandling) {
            assert (TStringGuards.isStride0(a));
            return TStringOps.readS0(a, arrayA, index) < 128 ? 1 : -1;
        }

        @Specialization(guards={"isUTF32(encoding)", "isBrokenFixedWidth(codeRangeA)", "isReturnNegative(errorHandling)"})
        int doUTF32BrokenReturnNegative(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int index, TruffleString.ErrorHandling errorHandling, @Cached CodePointAtRawNode codePointAtRawNode) {
            return codePointAtRawNode.execute(a, arrayA, codeRangeA, encoding, index, TruffleString.ErrorHandling.RETURN_NEGATIVE) < 0 ? -1 : 4;
        }

        @Specialization(guards={"isUTF8(encoding)", "isValidMultiByte(codeRangeA)"})
        int utf8Valid(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int index, TruffleString.ErrorHandling errorHandling) {
            assert (TStringGuards.isStride0(a));
            int firstByte = TStringOps.readS0(a, arrayA, index);
            return firstByte <= 127 ? 1 : Encodings.utf8CodePointLength(firstByte);
        }

        @Specialization(guards={"isUTF8(encoding)", "isBrokenMultiByte(codeRangeA)"})
        int utf8Broken(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int index, TruffleString.ErrorHandling errorHandling) {
            assert (TStringGuards.isStride0(a));
            return Encodings.utf8GetCodePointLength(a, arrayA, index, errorHandling);
        }

        @Specialization(guards={"isUTF16(encoding)", "isValidMultiByte(codeRangeA)"})
        int utf16Valid(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int index, TruffleString.ErrorHandling errorHandling) {
            assert (TStringGuards.isStride1(a));
            return Encodings.isUTF16HighSurrogate(TStringOps.readS1(a, arrayA, index)) ? 4 : 2;
        }

        @Specialization(guards={"isUTF16(encoding)", "isBrokenMultiByte(codeRangeA)"})
        int utf16Broken(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int index, TruffleString.ErrorHandling errorHandling) {
            assert (TStringGuards.isStride1(a));
            return Encodings.utf16BrokenGetCodePointByteLength(a, arrayA, index, errorHandling);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization(guards={"isUnsupportedEncoding(encoding)"})
        int unsupported(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, int index, TruffleString.ErrorHandling errorHandling) {
            assert (TStringGuards.isStride0(a));
            JCodings.Encoding jCoding = JCodings.getInstance().get(encoding);
            int cpLength = JCodings.getInstance().getCodePointLength(jCoding, JCodings.asByteArray(arrayA), a.byteArrayOffset() + index, a.byteArrayOffset() + a.length());
            int regionLength = a.length() - index;
            if (errorHandling == TruffleString.ErrorHandling.BEST_EFFORT) {
                if (cpLength > 0 && cpLength <= regionLength) {
                    return cpLength;
                }
                return Math.min(JCodings.getInstance().minLength(jCoding), regionLength);
            }
            assert (errorHandling == TruffleString.ErrorHandling.RETURN_NEGATIVE);
            if (cpLength <= regionLength) {
                return cpLength;
            }
            return -1 - (cpLength - regionLength);
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class FromNativePointerNode
    extends Node {
        FromNativePointerNode() {
        }

        abstract TruffleString execute(AbstractTruffleString.NativePointer var1, int var2, int var3, TruffleString.Encoding var4, boolean var5);

        @Specialization
        TruffleString fromNativePointerInternal(AbstractTruffleString.NativePointer pointer, int byteOffset, int byteLength, TruffleString.Encoding encoding, boolean isCacheHead, @Cached ConditionProfile asciiLatinBytesProfile, @Cached ConditionProfile utf8Profile, @Cached ConditionProfile utf8BrokenProfile, @Cached ConditionProfile utf16Profile, @Cached ConditionProfile utf32Profile, @Cached ConditionProfile exoticValidProfile, @Cached ConditionProfile exoticFixedWidthProfile) {
            int stride;
            int codeRange;
            int codePointLength;
            int length;
            if (byteLength == 0) {
                return encoding.getEmpty();
            }
            if (utf16Profile.profile(TStringGuards.isUTF16(encoding))) {
                AbstractTruffleString.checkByteLengthUTF16(byteLength);
                length = byteLength >> 1;
                long attrs = TStringOps.calcStringAttributesUTF16(this, pointer, byteOffset, length, false);
                codePointLength = StringAttributes.getCodePointLength(attrs);
                codeRange = StringAttributes.getCodeRange(attrs);
                stride = 1;
            } else if (utf32Profile.profile(TStringGuards.isUTF32(encoding))) {
                AbstractTruffleString.checkByteLengthUTF32(byteLength);
                length = byteLength >> 2;
                codeRange = TStringOps.calcStringAttributesUTF32(this, pointer, byteOffset, length);
                codePointLength = length;
                stride = 2;
            } else {
                length = byteLength;
                stride = 0;
                if (utf8Profile.profile(TStringGuards.isUTF8(encoding))) {
                    long attrs = TStringOps.calcStringAttributesUTF8(this, pointer, byteOffset, length, false, false, utf8BrokenProfile);
                    codeRange = StringAttributes.getCodeRange(attrs);
                    codePointLength = StringAttributes.getCodePointLength(attrs);
                } else if (asciiLatinBytesProfile.profile(TStringGuards.isAsciiBytesOrLatin1(encoding))) {
                    int cr = TStringOps.calcStringAttributesLatin1(this, pointer, byteOffset, length);
                    codeRange = TStringGuards.is8Bit(cr) ? TSCodeRange.asciiLatinBytesNonAsciiCodeRange(encoding) : cr;
                    codePointLength = length;
                } else {
                    pointer.materializeByteArray(byteLength, ConditionProfile.getUncached());
                    long attrs = JCodings.getInstance().calcStringAttributes(this, pointer, byteOffset, length, encoding, exoticValidProfile, exoticFixedWidthProfile);
                    codeRange = StringAttributes.getCodeRange(attrs);
                    codePointLength = StringAttributes.getCodePointLength(attrs);
                }
            }
            return TruffleString.createFromArray(pointer, byteOffset, length, stride, encoding, codePointLength, codeRange, isCacheHead);
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class FromBufferWithStringCompactionKnownAttributesNode
    extends Node {
        FromBufferWithStringCompactionKnownAttributesNode() {
        }

        abstract TruffleString execute(Object var1, int var2, int var3, TruffleString.Encoding var4, int var5, int var6);

        @Specialization
        TruffleString fromBufferWithStringCompaction(Object arrayA, int offsetA, int byteLength, TruffleString.Encoding encoding, int codePointLength, int codeRange, @Cached ConditionProfile utf16Profile, @Cached ConditionProfile utf16CompactProfile, @Cached ConditionProfile utf32Profile, @Cached ConditionProfile utf32Compact0Profile, @Cached ConditionProfile utf32Compact1Profile) {
            byte[] array;
            int stride;
            int length;
            if (byteLength == 0) {
                return encoding.getEmpty();
            }
            boolean offset = false;
            if (utf16Profile.profile(TStringGuards.isUTF16(encoding))) {
                AbstractTruffleString.checkByteLengthUTF16(byteLength);
                length = byteLength >> 1;
                stride = Stride.fromCodeRangeUTF16(codeRange);
                array = new byte[length << stride];
                if (utf16CompactProfile.profile(stride == 0)) {
                    TStringOps.arraycopyWithStride(this, arrayA, offsetA, 1, 0, array, 0, 0, 0, length);
                } else {
                    TStringOps.arraycopyWithStride(this, arrayA, offsetA, 1, 0, array, 0, 1, 0, length);
                }
            } else if (utf32Profile.profile(TStringGuards.isUTF32(encoding))) {
                AbstractTruffleString.checkByteLengthUTF32(byteLength);
                length = byteLength >> 2;
                stride = Stride.fromCodeRangeUTF32(codeRange);
                array = new byte[length << stride];
                if (utf32Compact0Profile.profile(stride == 0)) {
                    TStringOps.arraycopyWithStride(this, arrayA, offsetA, 2, 0, array, 0, 0, 0, length);
                } else if (utf32Compact1Profile.profile(stride == 1)) {
                    TStringOps.arraycopyWithStride(this, arrayA, offsetA, 2, 0, array, 0, 1, 0, length);
                } else {
                    TStringOps.arraycopyWithStride(this, arrayA, offsetA, 2, 0, array, 0, 2, 0, length);
                }
            } else {
                length = byteLength;
                stride = 0;
                array = TStringOps.arraycopyOfWithStride(this, arrayA, offsetA, length, 0, length, 0);
            }
            return TruffleString.createFromArray(array, 0, length, stride, encoding, codePointLength, codeRange, true);
        }

        static FromBufferWithStringCompactionKnownAttributesNode getUncached() {
            return TStringInternalNodesFactory.FromBufferWithStringCompactionKnownAttributesNodeGen.getUncached();
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class FromBufferWithStringCompactionNode
    extends Node {
        FromBufferWithStringCompactionNode() {
        }

        abstract TruffleString execute(Object var1, int var2, int var3, TruffleString.Encoding var4, boolean var5, boolean var6);

        @Specialization
        TruffleString fromBufferWithStringCompaction(Object arrayA, int offsetA, int byteLength, TruffleString.Encoding encoding, boolean copy, boolean isCacheHead, @Cached ConditionProfile asciiLatinBytesProfile, @Cached ConditionProfile utf8Profile, @Cached ConditionProfile utf8BrokenProfile, @Cached ConditionProfile utf16Profile, @Cached ConditionProfile utf16CompactProfile, @Cached ConditionProfile utf32Profile, @Cached ConditionProfile utf32Compact0Profile, @Cached ConditionProfile utf32Compact1Profile, @Cached ConditionProfile exoticValidProfile, @Cached ConditionProfile exoticFixedWidthProfile) {
            Object array;
            int offset;
            int stride;
            int codeRange;
            int codePointLength;
            int length;
            if (byteLength == 0) {
                return encoding.getEmpty();
            }
            if (utf16Profile.profile(TStringGuards.isUTF16(encoding))) {
                AbstractTruffleString.checkByteLengthUTF16(byteLength);
                length = byteLength >> 1;
                long attrs = TStringOps.calcStringAttributesUTF16(this, arrayA, offsetA, length, false);
                codePointLength = StringAttributes.getCodePointLength(attrs);
                codeRange = StringAttributes.getCodeRange(attrs);
                stride = Stride.fromCodeRangeUTF16(codeRange);
                if (copy || stride == 0) {
                    offset = 0;
                    array = new byte[length << stride];
                    if (utf16CompactProfile.profile(stride == 0)) {
                        TStringOps.arraycopyWithStride(this, arrayA, offsetA, 1, 0, array, offset, 0, 0, length);
                    } else {
                        TStringOps.arraycopyWithStride(this, arrayA, offsetA, 1, 0, array, offset, 1, 0, length);
                    }
                } else {
                    offset = offsetA;
                    array = arrayA;
                }
            } else if (utf32Profile.profile(TStringGuards.isUTF32(encoding))) {
                AbstractTruffleString.checkByteLengthUTF32(byteLength);
                length = byteLength >> 2;
                codeRange = TStringOps.calcStringAttributesUTF32(this, arrayA, offsetA, length);
                codePointLength = length;
                stride = Stride.fromCodeRangeUTF32(codeRange);
                if (copy || stride < 2) {
                    offset = 0;
                    array = new byte[length << stride];
                    if (utf32Compact0Profile.profile(stride == 0)) {
                        TStringOps.arraycopyWithStride(this, arrayA, offsetA, 2, 0, array, offset, 0, 0, length);
                    } else if (utf32Compact1Profile.profile(stride == 1)) {
                        TStringOps.arraycopyWithStride(this, arrayA, offsetA, 2, 0, array, offset, 1, 0, length);
                    } else {
                        TStringOps.arraycopyWithStride(this, arrayA, offsetA, 2, 0, array, offset, 2, 0, length);
                    }
                } else {
                    offset = offsetA;
                    array = arrayA;
                }
            } else {
                length = byteLength;
                stride = 0;
                if (utf8Profile.profile(TStringGuards.isUTF8(encoding))) {
                    long attrs = TStringOps.calcStringAttributesUTF8(this, arrayA, offsetA, length, false, false, utf8BrokenProfile);
                    codeRange = StringAttributes.getCodeRange(attrs);
                    codePointLength = StringAttributes.getCodePointLength(attrs);
                } else if (asciiLatinBytesProfile.profile(TStringGuards.isAsciiBytesOrLatin1(encoding))) {
                    int cr = TStringOps.calcStringAttributesLatin1(this, arrayA, offsetA, length);
                    codeRange = TStringGuards.is8Bit(cr) ? TSCodeRange.asciiLatinBytesNonAsciiCodeRange(encoding) : cr;
                    codePointLength = length;
                } else {
                    if (arrayA instanceof AbstractTruffleString.NativePointer) {
                        ((AbstractTruffleString.NativePointer)arrayA).materializeByteArray(length << stride, ConditionProfile.getUncached());
                    }
                    long attrs = JCodings.getInstance().calcStringAttributes(this, arrayA, offsetA, length, encoding, exoticValidProfile, exoticFixedWidthProfile);
                    codeRange = StringAttributes.getCodeRange(attrs);
                    codePointLength = StringAttributes.getCodePointLength(attrs);
                }
                if (copy) {
                    offset = 0;
                    array = TStringOps.arraycopyOfWithStride(this, arrayA, offsetA, length, 0, length, 0);
                } else {
                    offset = offsetA;
                    array = arrayA;
                }
            }
            return TruffleString.createFromArray(array, offset, length, stride, encoding, codePointLength, codeRange, isCacheHead);
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class CreateSubstringNode
    extends Node {
        CreateSubstringNode() {
        }

        abstract TruffleString execute(AbstractTruffleString var1, Object var2, int var3, int var4, int var5, TruffleString.Encoding var6, int var7);

        @Specialization(guards={"encoding == cachedEncoding", "stride == cachedStride"}, limit="6")
        TruffleString doCached(AbstractTruffleString a, Object array, int offset, int length, int stride, TruffleString.Encoding encoding, int codeRange, @Cached(value="encoding") TruffleString.Encoding cachedEncoding, @Cached(value="stride") int cachedStride, @Cached CalcStringAttributesNode calcAttributesNode) {
            return CreateSubstringNode.createString(a, array, offset, length, cachedStride, cachedEncoding, codeRange, calcAttributesNode, this);
        }

        @Specialization(replaces={"doCached"})
        TruffleString doUncached(AbstractTruffleString a, Object array, int offset, int length, int stride, TruffleString.Encoding encoding, int codeRange, @Cached CalcStringAttributesNode calcAttributesNode) {
            return CreateSubstringNode.createString(a, array, offset, length, stride, encoding, codeRange, calcAttributesNode, this);
        }

        private static TruffleString createString(AbstractTruffleString a, Object array, int offset, int length, int stride, TruffleString.Encoding encoding, int codeRange, CalcStringAttributesNode calcAttributesNode, CreateSubstringNode location) {
            long attrs = calcAttributesNode.execute(a, array, offset, length, stride, encoding, codeRange);
            int newStride = Stride.fromCodeRange(StringAttributes.getCodeRange(attrs), encoding);
            byte[] newBytes = new byte[length << newStride];
            TStringOps.arraycopyWithStride(location, array, offset, stride, 0, newBytes, 0, newStride, 0, length);
            return TruffleString.createFromByteArray(newBytes, length, newStride, encoding, StringAttributes.getCodePointLength(attrs), StringAttributes.getCodeRange(attrs));
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class GetCodePointLengthNode
    extends Node {
        GetCodePointLengthNode() {
        }

        abstract int execute(AbstractTruffleString var1);

        @Specialization
        int immutable(TruffleString a) {
            return a.codePointLength();
        }

        @Specialization(guards={"a.codePointLength() >= 0"})
        int mutableCacheHit(MutableTruffleString a) {
            return a.codePointLength();
        }

        @Specialization(guards={"a.codePointLength() < 0"})
        int mutableCacheMiss(MutableTruffleString a, @Cached MutableTruffleString.CalcLazyAttributesNode calcLazyAttributesNode) {
            calcLazyAttributesNode.execute(a);
            return a.codePointLength();
        }

        static GetCodePointLengthNode getUncached() {
            return TStringInternalNodesFactory.GetCodePointLengthNodeGen.getUncached();
        }
    }

    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    static abstract class GetCodeRangeNode
    extends Node {
        GetCodeRangeNode() {
        }

        abstract int execute(AbstractTruffleString var1);

        @Specialization
        int immutable(TruffleString a) {
            return a.codeRange();
        }

        @Specialization(guards={"!isUnknown(a.codeRange())"})
        int mutableCacheHit(MutableTruffleString a) {
            return a.codeRange();
        }

        @Specialization(guards={"isUnknown(a.codeRange())"})
        int mutableCacheMiss(MutableTruffleString a, @Cached MutableTruffleString.CalcLazyAttributesNode calcLazyAttributesNode) {
            calcLazyAttributesNode.execute(a);
            return a.codeRange();
        }

        static GetCodeRangeNode getUncached() {
            return TStringInternalNodesFactory.GetCodeRangeNodeGen.getUncached();
        }
    }
}

