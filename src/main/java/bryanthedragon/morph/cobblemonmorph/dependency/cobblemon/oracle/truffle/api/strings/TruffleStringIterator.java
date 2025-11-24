
package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GeneratePackagePrivate;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.AbstractTruffleString;
import com.oracle.truffle.api.strings.Encodings;
import com.oracle.truffle.api.strings.InternalErrors;
import com.oracle.truffle.api.strings.JCodings;
import com.oracle.truffle.api.strings.TSCodeRange;
import com.oracle.truffle.api.strings.TStringConstants;
import com.oracle.truffle.api.strings.TStringGuards;
import com.oracle.truffle.api.strings.TStringOps;
import com.oracle.truffle.api.strings.TStringOpsNodes;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringIteratorFactory;

public final class TruffleStringIterator {
    final AbstractTruffleString a;
    final Object arrayA;
    final byte codeRangeA;
    final TruffleString.Encoding encoding;
    final TruffleString.ErrorHandling errorHandling;
    private int rawIndex;

    TruffleStringIterator(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, TruffleString.ErrorHandling errorHandling, int rawIndex) {
        assert (TSCodeRange.isCodeRange(codeRangeA));
        this.a = a;
        this.arrayA = arrayA;
        this.codeRangeA = (byte)codeRangeA;
        this.encoding = encoding;
        this.errorHandling = errorHandling;
        this.rawIndex = rawIndex;
    }

    public boolean hasNext() {
        return this.rawIndex < this.a.length();
    }

    public boolean hasPrevious() {
        return this.rawIndex > 0;
    }

    public int getByteIndex() {
        return this.rawIndex << this.encoding.naturalStride;
    }

    @CompilerDirectives.TruffleBoundary
    public int nextUncached() {
        return NextNode.getUncached().execute(this);
    }

    @CompilerDirectives.TruffleBoundary
    public int previousUncached() {
        return PreviousNode.getUncached().execute(this);
    }

    int getRawIndex() {
        return this.rawIndex;
    }

    void setRawIndex(int i) {
        this.rawIndex = i;
    }

    private int readFwdS0() {
        assert (this.a.stride() == 0);
        assert (this.hasNext());
        return TStringOps.readS0(this.a, this.arrayA, this.rawIndex);
    }

    private int readFwdS1() {
        assert (this.a.stride() == 1);
        assert (this.hasNext());
        return TStringOps.readS1(this.a, this.arrayA, this.rawIndex);
    }

    private int readBckS1() {
        assert (this.a.stride() == 1);
        assert (this.hasPrevious());
        return TStringOps.readS1(this.a, this.arrayA, this.rawIndex - 1);
    }

    private static int readAndInc(TruffleStringIterator it, TStringOpsNodes.RawReadValueNode readNode) {
        assert (it.hasNext());
        return readNode.execute(it.a, it.arrayA, it.rawIndex++);
    }

    private int readAndIncS0() {
        assert (this.a.stride() == 0);
        assert (this.hasNext());
        return TStringOps.readS0(this.a, this.arrayA, this.rawIndex++);
    }

    private int readAndIncS1() {
        assert (this.a.stride() == 1);
        assert (this.hasNext());
        return TStringOps.readS1(this.a, this.arrayA, this.rawIndex++);
    }

    private static int readAndDec(TruffleStringIterator it, TStringOpsNodes.RawReadValueNode readNode) {
        assert (it.hasPrevious());
        return readNode.execute(it.a, it.arrayA, --it.rawIndex);
    }

    private int readAndDecS0() {
        assert (this.a.stride() == 0);
        assert (this.hasPrevious());
        return TStringOps.readS0(this.a, this.arrayA, --this.rawIndex);
    }

    private int readAndDecS1() {
        assert (this.a.stride() == 1);
        assert (this.hasPrevious());
        return TStringOps.readS1(this.a, this.arrayA, --this.rawIndex);
    }

    private boolean curIsUtf8ContinuationByte() {
        return Encodings.isUTF8ContinuationByte(this.readFwdS0());
    }

    static int indexOf(Node location, TruffleStringIterator it, int codepoint, int fromIndex, int toIndex, NextNode nextNode) {
        int aCodepointIndex = 0;
        while (aCodepointIndex < fromIndex && it.hasNext()) {
            nextNode.execute(it);
            TStringConstants.truffleSafePointPoll(location, ++aCodepointIndex);
        }
        if (aCodepointIndex < fromIndex) {
            return -1;
        }
        while (it.hasNext() && aCodepointIndex < toIndex) {
            if (nextNode.execute(it) == codepoint) {
                return aCodepointIndex;
            }
            TStringConstants.truffleSafePointPoll(location, ++aCodepointIndex);
        }
        return -1;
    }

    static int lastIndexOf(Node location, TruffleStringIterator it, int codepoint, int fromIndex, int toIndex, NextNode nextNode) {
        int aCodepointIndex = 0;
        int result = -1;
        while (aCodepointIndex < fromIndex && it.hasNext()) {
            if (nextNode.execute(it) == codepoint) {
                result = aCodepointIndex;
            }
            TStringConstants.truffleSafePointPoll(location, ++aCodepointIndex);
        }
        if (aCodepointIndex < toIndex) {
            return -1;
        }
        return result;
    }

    static int indexOfString(Node location, TruffleStringIterator aIt, TruffleStringIterator bIt, int fromIndex, int toIndex, NextNode nextNodeA, NextNode nextNodeB) {
        if (!bIt.hasNext()) {
            return fromIndex;
        }
        int aCodepointIndex = 0;
        while (aCodepointIndex < fromIndex && aIt.hasNext()) {
            nextNodeA.execute(aIt);
            TStringConstants.truffleSafePointPoll(location, ++aCodepointIndex);
        }
        if (aCodepointIndex < fromIndex) {
            return -1;
        }
        int bFirst = nextNodeB.execute(bIt);
        int bSecondIndex = bIt.getRawIndex();
        while (aIt.hasNext() && aCodepointIndex < toIndex) {
            if (nextNodeA.execute(aIt) == bFirst) {
                if (!bIt.hasNext()) {
                    return aCodepointIndex;
                }
                int aCurIndex = aIt.getRawIndex();
                int innerLoopCount = 0;
                while (bIt.hasNext()) {
                    if (!aIt.hasNext()) {
                        return -1;
                    }
                    if (nextNodeA.execute(aIt) != nextNodeB.execute(bIt)) break;
                    if (!bIt.hasNext()) {
                        return aCodepointIndex;
                    }
                    TStringConstants.truffleSafePointPoll(location, ++innerLoopCount);
                }
                aIt.setRawIndex(aCurIndex);
                bIt.setRawIndex(bSecondIndex);
            }
            TStringConstants.truffleSafePointPoll(location, ++aCodepointIndex);
        }
        return -1;
    }

    static int byteIndexOfString(Node location, TruffleStringIterator aIt, TruffleStringIterator bIt, int fromByteIndex, int toByteIndex, NextNode nextNodeA, NextNode nextNodeB) {
        if (!bIt.hasNext()) {
            return fromByteIndex;
        }
        aIt.setRawIndex(fromByteIndex);
        int bFirst = nextNodeB.execute(bIt);
        int bSecondIndex = bIt.getRawIndex();
        int loopCount = 0;
        while (aIt.hasNext() && aIt.getRawIndex() < toByteIndex) {
            int ret = aIt.getRawIndex();
            if (nextNodeA.execute(aIt) == bFirst) {
                if (!bIt.hasNext()) {
                    return ret;
                }
                int aCurIndex = aIt.getRawIndex();
                while (bIt.hasNext()) {
                    if (!aIt.hasNext()) {
                        return -1;
                    }
                    if (nextNodeA.execute(aIt) != nextNodeB.execute(bIt)) break;
                    if (!bIt.hasNext()) {
                        return ret;
                    }
                    TStringConstants.truffleSafePointPoll(location, ++loopCount);
                }
                aIt.setRawIndex(aCurIndex);
                bIt.setRawIndex(bSecondIndex);
            }
            TStringConstants.truffleSafePointPoll(location, ++loopCount);
        }
        return -1;
    }

    static int lastIndexOfString(Node location, TruffleStringIterator aIt, TruffleStringIterator bIt, int fromIndex, int toIndex, NextNode nextNodeA, PreviousNode prevNodeA, PreviousNode prevNodeB) {
        if (!bIt.hasPrevious()) {
            return fromIndex;
        }
        int bFirstCodePoint = prevNodeB.execute(bIt);
        int lastMatchIndex = -1;
        int lastMatchByteIndex = -1;
        int aCodepointIndex = 0;
        while (aCodepointIndex < fromIndex && aIt.hasNext()) {
            if (nextNodeA.execute(aIt) == bFirstCodePoint) {
                lastMatchIndex = aCodepointIndex;
                lastMatchByteIndex = aIt.getRawIndex();
            }
            TStringConstants.truffleSafePointPoll(location, ++aCodepointIndex);
        }
        if (aCodepointIndex < fromIndex || lastMatchIndex < 0) {
            return -1;
        }
        aCodepointIndex = lastMatchIndex;
        aIt.setRawIndex(lastMatchByteIndex);
        int bSecondIndex = bIt.getRawIndex();
        while (aIt.hasPrevious() && aCodepointIndex >= toIndex) {
            if (prevNodeA.execute(aIt) == bFirstCodePoint) {
                if (!bIt.hasPrevious()) {
                    return aCodepointIndex;
                }
                int aCurIndex = aIt.getRawIndex();
                int aCurCodePointIndex = aCodepointIndex;
                while (bIt.hasPrevious()) {
                    if (!aIt.hasPrevious()) {
                        return -1;
                    }
                    if (prevNodeA.execute(aIt) != prevNodeB.execute(bIt)) break;
                    if (!bIt.hasPrevious() && --aCurCodePointIndex >= toIndex) {
                        return aCurCodePointIndex;
                    }
                    TStringConstants.truffleSafePointPoll(location, aCurCodePointIndex);
                }
                aIt.setRawIndex(aCurIndex);
                bIt.setRawIndex(bSecondIndex);
            }
            TStringConstants.truffleSafePointPoll(location, --aCodepointIndex);
        }
        return -1;
    }

    static int lastByteIndexOfString(Node location, TruffleStringIterator aIt, TruffleStringIterator bIt, int fromByteIndex, int toByteIndex, NextNode nextNodeA, PreviousNode prevNodeA, PreviousNode prevNodeB) {
        if (!bIt.hasPrevious()) {
            return fromByteIndex;
        }
        int bFirstCodePoint = prevNodeB.execute(bIt);
        int lastMatchByteIndex = -1;
        int loopCount = 0;
        while (aIt.getRawIndex() < fromByteIndex && aIt.hasNext()) {
            if (nextNodeA.execute(aIt) == bFirstCodePoint) {
                lastMatchByteIndex = aIt.getRawIndex();
            }
            TStringConstants.truffleSafePointPoll(location, ++loopCount);
        }
        if (aIt.getRawIndex() < fromByteIndex || lastMatchByteIndex < 0) {
            return -1;
        }
        aIt.setRawIndex(lastMatchByteIndex);
        int bSecondIndex = bIt.getRawIndex();
        while (aIt.hasPrevious() && aIt.getRawIndex() > toByteIndex) {
            if (prevNodeA.execute(aIt) == bFirstCodePoint) {
                if (!bIt.hasPrevious()) {
                    return aIt.getRawIndex();
                }
                int aCurIndex = aIt.getRawIndex();
                while (bIt.hasPrevious()) {
                    if (!aIt.hasPrevious()) {
                        return -1;
                    }
                    if (prevNodeA.execute(aIt) != prevNodeB.execute(bIt)) break;
                    if (!bIt.hasPrevious() && aIt.getRawIndex() >= toByteIndex) {
                        return aIt.getRawIndex();
                    }
                    TStringConstants.truffleSafePointPoll(location, ++loopCount);
                }
                aIt.setRawIndex(aCurIndex);
                bIt.setRawIndex(bSecondIndex);
            }
            TStringConstants.truffleSafePointPoll(location, ++loopCount);
        }
        return -1;
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class PreviousNode
    extends Node {
        PreviousNode() {
        }

        public final int execute(TruffleStringIterator it) {
            if (!it.hasPrevious()) {
                throw InternalErrors.illegalState("beginning of string has been reached already");
            }
            return this.executeInternal(it);
        }

        abstract int executeInternal(TruffleStringIterator var1);

        @Specialization(guards={"isFixedWidth(it.codeRangeA)", "isBestEffort(it.errorHandling)"})
        static int fixed(TruffleStringIterator it, @Cached TStringOpsNodes.RawReadValueNode readNode) {
            return TruffleStringIterator.readAndDec(it, readNode);
        }

        @Specialization(guards={"isUpToValidFixedWidth(it.codeRangeA)", "isReturnNegative(it.errorHandling)"})
        static int fixedValid(TruffleStringIterator it, @Cached TStringOpsNodes.RawReadValueNode readNode) {
            return TruffleStringIterator.readAndDec(it, readNode);
        }

        @Specialization(guards={"isAscii(it.encoding)", "isBrokenFixedWidth(it.codeRangeA)", "isReturnNegative(it.errorHandling)"})
        static int brokenAscii(TruffleStringIterator it, @Cached TStringOpsNodes.RawReadValueNode readNode) {
            int codepoint = TruffleStringIterator.readAndDec(it, readNode);
            return codepoint < 128 ? codepoint : -1;
        }

        @Specialization(guards={"isUTF32(it.encoding)", "isBrokenFixedWidth(it.codeRangeA)", "isReturnNegative(it.errorHandling)"})
        static int brokenUTF32(TruffleStringIterator it, @Cached TStringOpsNodes.RawReadValueNode readNode) {
            int codepoint = TruffleStringIterator.readAndDec(it, readNode);
            return Encodings.isValidUnicodeCodepoint(codepoint) ? codepoint : -1;
        }

        @Specialization(guards={"isUTF8(it.encoding)", "isValidMultiByte(it.codeRangeA)"})
        static int utf8Valid(TruffleStringIterator it) {
            int b = it.readAndDecS0();
            if (b < 128) {
                return b;
            }
            assert (Encodings.isUTF8ContinuationByte(b));
            int codepoint = b & 0x3F;
            for (int j = 1; j < 4; ++j) {
                b = it.readAndDecS0();
                if (j >= 3 || !Encodings.isUTF8ContinuationByte(b)) break;
                codepoint |= (b & 0x3F) << 6 * j;
            }
            int nBytes = Integer.numberOfLeadingZeros(~(b << 24));
            assert (1 < nBytes && nBytes < 5) : nBytes;
            return codepoint | (b & 255 >>> nBytes) << 6 * (nBytes - 1);
        }

        @Specialization(guards={"isUTF8(it.encoding)", "isBrokenMultiByteOrUnknown(it.codeRangeA)"})
        static int utf8Broken(TruffleStringIterator it) {
            int nBytes;
            int initialIndex = it.rawIndex;
            int b = it.readAndDecS0();
            if (b < 128) {
                return b;
            }
            if (!Encodings.isUTF8ContinuationByte(b)) {
                return Encodings.invalidCodepointReturnValue(it.errorHandling);
            }
            int codepoint = b & 0x3F;
            for (int j = 1; j < 4 && it.hasPrevious(); ++j) {
                b = it.readAndDecS0();
                if (j >= 3 || !Encodings.isUTF8ContinuationByte(b)) break;
                codepoint |= (b & 0x3F) << 6 * j;
            }
            if ((nBytes = Integer.numberOfLeadingZeros(~(b << 24))) < 2 || nBytes != initialIndex - it.rawIndex || Encodings.utf8IsInvalidCodePoint(codepoint |= (b & 255 >>> nBytes) << 6 * (nBytes - 1), nBytes)) {
                it.rawIndex = initialIndex - 1;
                return Encodings.invalidCodepointReturnValue(it.errorHandling);
            }
            return codepoint;
        }

        @Specialization(guards={"isUTF16(it.encoding)", "isValidMultiByte(it.codeRangeA)"})
        static int utf16Valid(TruffleStringIterator it) {
            char c = (char)it.readAndDecS1();
            if (Encodings.isUTF16LowSurrogate(c)) {
                assert (Encodings.isUTF16HighSurrogate((char)it.readBckS1()));
                return Character.toCodePoint((char)it.readAndDecS1(), c);
            }
            return c;
        }

        @Specialization(guards={"isUTF16(it.encoding)", "isBrokenMultiByteOrUnknown(it.codeRangeA)"})
        static int utf16Broken(TruffleStringIterator it) {
            char c2;
            char c = (char)it.readAndDecS1();
            if (it.errorHandling == TruffleString.ErrorHandling.RETURN_NEGATIVE) {
                if (Encodings.isUTF16Surrogate(c)) {
                    char c22;
                    if (Encodings.isUTF16LowSurrogate(c) && it.hasPrevious() && Encodings.isUTF16HighSurrogate(c22 = (char)it.readBckS1())) {
                        --it.rawIndex;
                        return Character.toCodePoint(c22, c);
                    }
                    return -1;
                }
            } else if (Encodings.isUTF16LowSurrogate(c) && it.hasPrevious() && Encodings.isUTF16HighSurrogate(c2 = (char)it.readBckS1())) {
                --it.rawIndex;
                return Character.toCodePoint(c2, c);
            }
            return c;
        }

        @Specialization(guards={"isUnsupportedEncoding(it.encoding)"})
        static int unsupported(TruffleStringIterator it) {
            assert (it.hasPrevious());
            byte[] bytes = JCodings.asByteArray(it.arrayA);
            int start2 = it.a.byteArrayOffset();
            int index = it.a.byteArrayOffset() + it.rawIndex;
            int end2 = it.a.byteArrayOffset() + it.a.length();
            JCodings.Encoding jCoding = JCodings.getInstance().get(it.encoding);
            int prevIndex = JCodings.getInstance().getPreviousCodePointIndex(jCoding, bytes, start2, index, end2);
            if (prevIndex < 0) {
                --it.rawIndex;
                return Encodings.invalidCodepointReturnValue(it.errorHandling);
            }
            assert (prevIndex >= it.a.byteArrayOffset());
            assert (prevIndex < index);
            it.rawIndex = prevIndex - it.a.byteArrayOffset();
            return JCodings.getInstance().readCodePoint(jCoding, bytes, prevIndex, end2);
        }

        public static PreviousNode create() {
            return TruffleStringIteratorFactory.PreviousNodeGen.create();
        }

        public static PreviousNode getUncached() {
            return TruffleStringIteratorFactory.PreviousNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class NextNode
    extends Node {
        NextNode() {
        }

        public final int execute(TruffleStringIterator it) {
            if (!it.hasNext()) {
                throw InternalErrors.illegalState("end of string has been reached already");
            }
            return this.executeInternal(it);
        }

        abstract int executeInternal(TruffleStringIterator var1);

        @Specialization(guards={"isFixedWidth(it.codeRangeA)", "isBestEffort(it.errorHandling)"})
        static int fixed(TruffleStringIterator it, @Cached TStringOpsNodes.RawReadValueNode readNode) {
            return TruffleStringIterator.readAndInc(it, readNode);
        }

        @Specialization(guards={"isUpToValidFixedWidth(it.codeRangeA)", "isReturnNegative(it.errorHandling)"})
        static int fixedValid(TruffleStringIterator it, @Cached TStringOpsNodes.RawReadValueNode readNode) {
            return TruffleStringIterator.readAndInc(it, readNode);
        }

        @Specialization(guards={"isAscii(it.encoding)", "isBrokenFixedWidth(it.codeRangeA)", "isReturnNegative(it.errorHandling)"})
        static int brokenAscii(TruffleStringIterator it, @Cached TStringOpsNodes.RawReadValueNode readNode) {
            int codepoint = TruffleStringIterator.readAndInc(it, readNode);
            return codepoint < 128 ? codepoint : -1;
        }

        @Specialization(guards={"isUTF32(it.encoding)", "isBrokenFixedWidth(it.codeRangeA)", "isReturnNegative(it.errorHandling)"})
        static int brokenUTF32(TruffleStringIterator it, @Cached TStringOpsNodes.RawReadValueNode readNode) {
            int codepoint = TruffleStringIterator.readAndInc(it, readNode);
            return Encodings.isValidUnicodeCodepoint(codepoint) ? codepoint : -1;
        }

        @Specialization(guards={"isUTF8(it.encoding)", "isValidMultiByte(it.codeRangeA)"})
        static int utf8Valid(TruffleStringIterator it) {
            int b = it.readAndIncS0();
            if (b < 128) {
                return b;
            }
            int nBytes = Integer.numberOfLeadingZeros(~(b << 24));
            int codepoint = b & 255 >>> nBytes;
            assert (1 < nBytes && nBytes < 5) : nBytes;
            assert (it.rawIndex + nBytes - 1 <= it.a.length());
            switch (nBytes) {
                case 4: {
                    assert (it.curIsUtf8ContinuationByte());
                    codepoint = codepoint << 6 | it.readAndIncS0() & 0x3F;
                }
                case 3: {
                    assert (it.curIsUtf8ContinuationByte());
                    codepoint = codepoint << 6 | it.readAndIncS0() & 0x3F;
                }
            }
            assert (it.curIsUtf8ContinuationByte());
            codepoint = codepoint << 6 | it.readAndIncS0() & 0x3F;
            return codepoint;
        }

        @Specialization(guards={"isUTF8(it.encoding)", "isBrokenMultiByteOrUnknown(it.codeRangeA)"})
        static int utf8Broken(TruffleStringIterator it) {
            int b = it.readAndIncS0();
            if (b < 128) {
                return b;
            }
            int resetIndex = it.rawIndex;
            int nBytes = Integer.numberOfLeadingZeros(~(b << 24));
            int codepoint = b & 255 >>> nBytes;
            switch (nBytes) {
                case 4: {
                    if (!it.hasNext() || !it.curIsUtf8ContinuationByte()) {
                        return Encodings.invalidCodepointReturnValue(it.errorHandling);
                    }
                    codepoint = codepoint << 6 | it.readAndIncS0() & 0x3F;
                }
                case 3: {
                    if (!it.hasNext() || !it.curIsUtf8ContinuationByte()) {
                        it.setRawIndex(resetIndex);
                        return Encodings.invalidCodepointReturnValue(it.errorHandling);
                    }
                    codepoint = codepoint << 6 | it.readAndIncS0() & 0x3F;
                }
                case 2: {
                    if (!it.hasNext() || !it.curIsUtf8ContinuationByte()) {
                        it.setRawIndex(resetIndex);
                        return Encodings.invalidCodepointReturnValue(it.errorHandling);
                    }
                    codepoint = codepoint << 6 | it.readAndIncS0() & 0x3F;
                    break;
                }
                default: {
                    return Encodings.invalidCodepointReturnValue(it.errorHandling);
                }
            }
            if (Encodings.utf8IsInvalidCodePoint(codepoint, nBytes)) {
                it.setRawIndex(resetIndex);
                return Encodings.invalidCodepointReturnValue(it.errorHandling);
            }
            return codepoint;
        }

        @Specialization(guards={"isUTF16(it.encoding)", "isValidMultiByte(it.codeRangeA)"})
        static int utf16Valid(TruffleStringIterator it) {
            char c = (char)it.readAndIncS1();
            if (Encodings.isUTF16HighSurrogate(c)) {
                assert (it.hasNext());
                assert (Encodings.isUTF16LowSurrogate(it.readFwdS1()));
                return Character.toCodePoint(c, (char)it.readAndIncS1());
            }
            return c;
        }

        @Specialization(guards={"isUTF16(it.encoding)", "isBrokenMultiByteOrUnknown(it.codeRangeA)"})
        static int utf16Broken(TruffleStringIterator it) {
            char c2;
            char c = (char)it.readAndIncS1();
            if (it.errorHandling == TruffleString.ErrorHandling.RETURN_NEGATIVE) {
                if (Encodings.isUTF16Surrogate(c)) {
                    char c22;
                    if (Encodings.isUTF16HighSurrogate(c) && it.hasNext() && Encodings.isUTF16LowSurrogate(c22 = (char)it.readFwdS1())) {
                        ++it.rawIndex;
                        return Character.toCodePoint(c, c22);
                    }
                    return -1;
                }
            } else if (Encodings.isUTF16HighSurrogate(c) && it.hasNext() && Encodings.isUTF16LowSurrogate(c2 = (char)it.readFwdS1())) {
                ++it.rawIndex;
                return Character.toCodePoint(c, c2);
            }
            return c;
        }

        @Specialization(guards={"isUnsupportedEncoding(it.encoding)"})
        static int unsupported(TruffleStringIterator it) {
            assert (it.hasNext());
            byte[] bytes = JCodings.asByteArray(it.arrayA);
            int p = it.a.byteArrayOffset() + it.rawIndex;
            int end2 = it.a.byteArrayOffset() + it.a.length();
            JCodings.Encoding jCoding = JCodings.getInstance().get(it.encoding);
            int length = JCodings.getInstance().getCodePointLength(jCoding, bytes, p, end2);
            if (length < 1) {
                it.rawIndex = length < -1 ? it.a.length() : ++it.rawIndex;
                return Encodings.invalidCodepointReturnValue(it.errorHandling);
            }
            it.rawIndex += length;
            return JCodings.getInstance().readCodePoint(jCoding, bytes, p, end2);
        }

        public static NextNode create() {
            return TruffleStringIteratorFactory.NextNodeGen.create();
        }

        public static NextNode getUncached() {
            return TruffleStringIteratorFactory.NextNodeGen.getUncached();
        }
    }
}

