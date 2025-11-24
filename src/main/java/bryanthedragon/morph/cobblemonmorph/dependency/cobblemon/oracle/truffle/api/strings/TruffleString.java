/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GeneratePackagePrivate;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.IntValueProfile;
import com.oracle.truffle.api.strings.AbstractTruffleString;
import com.oracle.truffle.api.strings.Encodings;
import com.oracle.truffle.api.strings.InternalByteArray;
import com.oracle.truffle.api.strings.InternalErrors;
import com.oracle.truffle.api.strings.JCodings;
import com.oracle.truffle.api.strings.MutableTruffleString;
import com.oracle.truffle.api.strings.NumberConversion;
import com.oracle.truffle.api.strings.Stride;
import com.oracle.truffle.api.strings.StringAttributes;
import com.oracle.truffle.api.strings.TSCodeRange;
import com.oracle.truffle.api.strings.TStringAccessor;
import com.oracle.truffle.api.strings.TStringConstants;
import com.oracle.truffle.api.strings.TStringGuards;
import com.oracle.truffle.api.strings.TStringInternalNodes;
import com.oracle.truffle.api.strings.TStringOps;
import com.oracle.truffle.api.strings.TStringOpsNodes;
import com.oracle.truffle.api.strings.TStringUnsafe;
import com.oracle.truffle.api.strings.TruffleStringFactory;
import com.oracle.truffle.api.strings.TruffleStringIterator;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.Arrays;
import java.util.BitSet;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.EconomicSet;
import org.graalvm.collections.Equivalence;

public final class TruffleString
extends AbstractTruffleString {
    private static final VarHandle NEXT_UPDATER = TruffleString.initializeNextUpdater();
    private static final byte FLAG_CACHE_HEAD = -128;
    private final int codePointLength;
    private final byte codeRange;
    TruffleString next;

    @CompilerDirectives.TruffleBoundary
    private static VarHandle initializeNextUpdater() {
        try {
            return MethodHandles.lookup().findVarHandle(TruffleString.class, "next", TruffleString.class);
        }
        catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private TruffleString(Object data, int offset, int length, int stride, Encoding encoding, int codePointLength, int codeRange, boolean isCacheHead) {
        super(data, offset, length, stride, encoding, isCacheHead ? -128 : 0);
        assert (codePointLength >= 0);
        assert (TruffleString.validateCodeRange(encoding, codeRange));
        this.codePointLength = codePointLength;
        this.codeRange = (byte)codeRange;
    }

    private static TruffleString create(Object data, int offset, int length, int stride, Encoding encoding, int codePointLength, int codeRange, boolean isCacheHead) {
        TruffleString string = new TruffleString(data, offset, length, stride, encoding, codePointLength, codeRange, isCacheHead);
        if (AbstractTruffleString.DEBUG_ALWAYS_CREATE_JAVA_STRING) {
            string.toJavaStringUncached();
        }
        return string;
    }

    private static boolean validateCodeRange(Encoding encoding, int codeRange) {
        assert (TruffleString.isByte(codeRange));
        assert (TSCodeRange.isCodeRange(codeRange));
        assert (!TStringGuards.isAscii(encoding) || TStringGuards.is7Bit(codeRange) || TStringGuards.isBrokenFixedWidth(codeRange));
        assert (!TStringGuards.isLatin1(encoding) || TStringGuards.is7Bit(codeRange) || TStringGuards.is8Bit(codeRange));
        assert (!(TStringGuards.isUTF8(encoding) && (TStringGuards.is8Bit(codeRange) || TStringGuards.is16Bit(codeRange) || TStringGuards.isValidFixedWidth(codeRange) || TStringGuards.isBrokenFixedWidth(codeRange))));
        assert (!TStringGuards.isUTF16(encoding) || !TStringGuards.isValidFixedWidth(codeRange) && !TStringGuards.isBrokenFixedWidth(codeRange));
        assert (!TStringGuards.isUTF32(encoding) || !TStringGuards.isValidMultiByte(codeRange) && !TStringGuards.isBrokenMultiByte(codeRange));
        assert (!TStringGuards.isBytes(encoding) || TStringGuards.is7Bit(codeRange) || TStringGuards.isValidFixedWidth(codeRange));
        return true;
    }

    static TruffleString createFromByteArray(byte[] bytes, int length, int stride, Encoding encoding, int codePointLength, int codeRange) {
        return TruffleString.createFromByteArray(bytes, length, stride, encoding, codePointLength, codeRange, true);
    }

    static TruffleString createFromByteArray(byte[] bytes, int length, int stride, Encoding encoding, int codePointLength, int codeRange, boolean isCacheHead) {
        return TruffleString.createFromArray(bytes, 0, length, stride, encoding, codePointLength, codeRange, isCacheHead);
    }

    static TruffleString createFromArray(Object bytes, int offset, int length, int stride, Encoding encoding, int codePointLength, int codeRange) {
        return TruffleString.createFromArray(bytes, offset, length, stride, encoding, codePointLength, codeRange, true);
    }

    static TruffleString createFromArray(Object bytes, int offset, int length, int stride, Encoding encoding, int codePointLength, int codeRange, boolean isCacheHead) {
        assert (bytes instanceof byte[] || TStringGuards.isInlinedJavaString(bytes) || bytes instanceof AbstractTruffleString.NativePointer);
        assert (offset >= 0);
        assert (bytes instanceof AbstractTruffleString.NativePointer || (long)offset + ((long)length << stride) <= TStringOps.byteLength(bytes));
        assert (TruffleString.attrsAreCorrect(bytes, encoding, offset, length, codePointLength, codeRange, stride));
        if (DEBUG_NON_ZERO_OFFSET && bytes instanceof byte[]) {
            int byteLength;
            int add2 = byteLength = Math.toIntExact((long)length << stride);
            byte[] copy = new byte[add2 + byteLength];
            System.arraycopy(bytes, offset, copy, add2, byteLength);
            return TruffleString.create(copy, add2, length, stride, encoding, codePointLength, codeRange, isCacheHead);
        }
        return TruffleString.create(bytes, offset, length, stride, encoding, codePointLength, codeRange, isCacheHead);
    }

    static TruffleString createConstant(byte[] bytes, int length, int stride, Encoding encoding, int codePointLength, int codeRange) {
        return TruffleString.createConstant(bytes, length, stride, encoding, codePointLength, codeRange, true);
    }

    static TruffleString createConstant(byte[] bytes, int length, int stride, Encoding encoding, int codePointLength, int codeRange, boolean isCacheHead) {
        TruffleString ret = TruffleString.createFromByteArray(bytes, length, stride, encoding, codePointLength, codeRange, isCacheHead);
        ret.hashCode();
        return ret;
    }

    static TruffleString createLazyLong(long value2, Encoding encoding) {
        int length = NumberConversion.stringLengthLong(value2);
        return TruffleString.create(new AbstractTruffleString.LazyLong(value2), 0, length, 0, encoding, length, TSCodeRange.get7Bit(), true);
    }

    static TruffleString createLazyConcat(TruffleString a, TruffleString b, Encoding encoding, int length, int stride) {
        assert (!TSCodeRange.isBrokenMultiByte(a.codeRange()));
        assert (!TSCodeRange.isBrokenMultiByte(b.codeRange()));
        assert (a.isLooselyCompatibleTo(encoding));
        assert (b.isLooselyCompatibleTo(encoding));
        assert (length == a.length() + b.length());
        int codeRange = TSCodeRange.commonCodeRange(a.codeRange(), b.codeRange());
        return TruffleString.create(new AbstractTruffleString.LazyConcat(a, b), 0, length, stride, encoding, a.codePointLength() + b.codePointLength(), codeRange, true);
    }

    static TruffleString createWrapJavaString(String str, int codePointLength, int codeRange) {
        int stride = TStringUnsafe.getJavaStringStride(str);
        return TruffleString.create(str, 0, str.length(), stride, Encoding.UTF_16, codePointLength, codeRange, false);
    }

    private static boolean attrsAreCorrect(Object bytes, Encoding encoding, int offset, int length, int codePointLength, int codeRange, int stride) {
        CompilerAsserts.neverPartOfCompilation();
        if (length == 0) {
            int length0CodeRange = TStringGuards.is7BitCompatible(encoding) ? TSCodeRange.get7Bit() : (JCodings.getInstance().isSingleByte(encoding.jCoding) ? TSCodeRange.getValidFixedWidth() : TSCodeRange.getValidMultiByte());
            return TStringOps.byteLength(bytes) == 0L && offset == 0 && codePointLength == 0 && codeRange == length0CodeRange && stride == 0;
        }
        int knownCodeRange = TSCodeRange.getUnknown();
        if (TStringGuards.isUTF16Or32(encoding) && stride == 0) {
            knownCodeRange = TSCodeRange.get8Bit();
        } else if (TStringGuards.isUTF32(encoding) && stride == 1) {
            knownCodeRange = TSCodeRange.get16Bit();
        }
        if (bytes instanceof AbstractTruffleString.NativePointer) {
            ((AbstractTruffleString.NativePointer)bytes).materializeByteArray(length << stride, ConditionProfile.getUncached());
        }
        long attrs = TStringInternalNodes.CalcStringAttributesNode.getUncached().execute(null, bytes, offset, length, stride, encoding, knownCodeRange);
        int cpLengthCalc = StringAttributes.getCodePointLength(attrs);
        int codeRangeCalc = StringAttributes.getCodeRange(attrs);
        assert (cpLengthCalc == codePointLength) : "inconsistent codePointLength: " + cpLengthCalc + " != " + codePointLength;
        assert (codeRangeCalc == codeRange) : "inconsistent codeRange: " + TSCodeRange.toString(codeRangeCalc) + " != " + TSCodeRange.toString(codeRange);
        return attrs == StringAttributes.create(codePointLength, codeRange);
    }

    boolean isLooselyCompatibleTo(Encoding expectedEncoding) {
        return this.isLooselyCompatibleTo(expectedEncoding.id, expectedEncoding.maxCompatibleCodeRange, this.codeRange());
    }

    int codePointLength() {
        return this.codePointLength;
    }

    int codeRange() {
        return this.codeRange;
    }

    boolean isCacheHead() {
        assert ((this.flags() & 0xFFFFFF80) != 0 == this.flags() < 0);
        return this.flags() < 0;
    }

    TruffleString getCacheHead() {
        assert (this.cacheRingIsValid());
        TruffleString cur = this.next;
        if (cur == null) {
            assert (this.isCacheHead());
            return this;
        }
        while (!cur.isCacheHead()) {
            cur = cur.next;
        }
        return cur;
    }

    @CompilerDirectives.TruffleBoundary
    void cacheInsert(TruffleString entry) {
        TruffleString cacheHeadNext;
        assert (!entry.isCacheHead());
        TruffleString cacheHead = this.getCacheHead();
        assert (!TruffleString.cacheEntryEquals(cacheHead, entry));
        do {
            if (TruffleString.hasDuplicateEncoding(cacheHead, cacheHeadNext = cacheHead.next, entry)) {
                return;
            }
            TruffleString truffleString = entry.next = cacheHeadNext == null ? cacheHead : cacheHeadNext;
        } while (!TruffleString.setNextAtomic(cacheHead, cacheHeadNext, entry));
    }

    void cacheInsertFirstBeforePublished(TruffleString entry) {
        TruffleString cacheHead;
        assert (!entry.isCacheHead());
        assert (this.isCacheHead());
        assert (this.next == null);
        entry.next = cacheHead = this;
        cacheHead.next = entry;
    }

    private static boolean hasDuplicateEncoding(TruffleString cacheHead, TruffleString start2, TruffleString insertEntry) {
        if (start2 == null) {
            return false;
        }
        TruffleString current = start2;
        while (current != cacheHead) {
            if (TruffleString.cacheEntryEquals(insertEntry, current)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    private static boolean cacheEntryEquals(TruffleString a, TruffleString b) {
        return b.encoding() == a.encoding() && (!TStringGuards.isUTF16(a.encoding()) || b.isJavaString() == a.isJavaString());
    }

    @CompilerDirectives.TruffleBoundary
    private static boolean setNextAtomic(TruffleString cacheHead, TruffleString currentNext, TruffleString newNext) {
        return NEXT_UPDATER.compareAndSet(cacheHead, currentNext, newNext);
    }

    private boolean cacheRingIsValid() {
        CompilerAsserts.neverPartOfCompilation();
        TruffleString head5 = null;
        TruffleString cur = this;
        boolean javaStringVisited = false;
        BitSet visitedEncodings = new BitSet(Encoding.values().length);
        EconomicSet<TruffleString> visited = EconomicSet.create(Equivalence.IDENTITY_WITH_SYSTEM_HASHCODE);
        do {
            if (cur.isCacheHead()) {
                assert (head5 == null) : "multiple cache heads";
                head5 = cur;
            }
            if (cur.isJavaString()) {
                assert (!javaStringVisited) : "duplicate cached java string";
                javaStringVisited = true;
            } else {
                assert (!visitedEncodings.get(cur.encoding())) : "duplicate encoding";
                visitedEncodings.set(cur.encoding());
            }
            assert (visited.add(cur)) : "not a ring structure";
        } while ((cur = cur.next) != this && cur != null);
        return true;
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString fromCodePointUncached(int codepoint, Encoding encoding) {
        return FromCodePointNode.getUncached().execute(codepoint, encoding);
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString fromCodePointUncached(int codepoint, Encoding encoding, boolean allowUTF16Surrogates) {
        return FromCodePointNode.getUncached().execute(codepoint, encoding, allowUTF16Surrogates);
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString fromLongUncached(long value2, Encoding encoding, boolean lazy) {
        return FromLongNode.getUncached().execute(value2, encoding, lazy);
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString fromByteArrayUncached(byte[] value2, Encoding encoding) {
        return FromByteArrayNode.getUncached().execute(value2, encoding);
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString fromByteArrayUncached(byte[] value2, Encoding encoding, boolean copy) {
        return FromByteArrayNode.getUncached().execute(value2, encoding, copy);
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString fromByteArrayUncached(byte[] value2, int byteOffset, int byteLength, Encoding encoding, boolean copy) {
        return FromByteArrayNode.getUncached().execute(value2, byteOffset, byteLength, encoding, copy);
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString fromCharArrayUTF16Uncached(char[] value2) {
        return FromCharArrayUTF16Node.getUncached().execute(value2);
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString fromCharArrayUTF16Uncached(char[] value2, int charOffset, int charLength) {
        return FromCharArrayUTF16Node.getUncached().execute(value2, charOffset, charLength);
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString fromJavaStringUncached(String s, Encoding encoding) {
        return FromJavaStringNode.getUncached().execute(s, encoding);
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString fromJavaStringUncached(String s, int charOffset, int length, Encoding encoding, boolean copy) {
        return FromJavaStringNode.getUncached().execute(s, charOffset, length, encoding, copy);
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString fromIntArrayUTF32Uncached(int[] value2) {
        return FromIntArrayUTF32Node.getUncached().execute(value2);
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString fromIntArrayUTF32Uncached(int[] value2, int intOffset, int intLength) {
        return FromIntArrayUTF32Node.getUncached().execute(value2, intOffset, intLength);
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString fromNativePointerUncached(Object pointerObject, int byteOffset, int byteLength, Encoding encoding, boolean copy) {
        return FromNativePointerNode.getUncached().execute(pointerObject, byteOffset, byteLength, encoding, copy);
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class CreateBackwardCodePointIteratorNode
    extends Node {
        CreateBackwardCodePointIteratorNode() {
        }

        public final TruffleStringIterator execute(AbstractTruffleString a, Encoding expectedEncoding) {
            return this.execute(a, expectedEncoding, ErrorHandling.BEST_EFFORT);
        }

        public abstract TruffleStringIterator execute(AbstractTruffleString var1, Encoding var2, ErrorHandling var3);

        @Specialization
        static TruffleStringIterator createIterator(AbstractTruffleString a, Encoding expectedEncoding, ErrorHandling errorHandling, @Cached ToIndexableNode toIndexableNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode) {
            CompilerAsserts.partialEvaluationConstant((Object)errorHandling);
            a.checkEncoding(expectedEncoding);
            return AbstractTruffleString.backwardIterator(a, toIndexableNode.execute(a, a.data()), getCodeRangeANode.execute(a), expectedEncoding, errorHandling);
        }

        public static CreateBackwardCodePointIteratorNode create() {
            return TruffleStringFactory.CreateBackwardCodePointIteratorNodeGen.create();
        }

        public static CreateBackwardCodePointIteratorNode getUncached() {
            return TruffleStringFactory.CreateBackwardCodePointIteratorNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class CreateCodePointIteratorNode
    extends Node {
        CreateCodePointIteratorNode() {
        }

        public final TruffleStringIterator execute(AbstractTruffleString a, Encoding expectedEncoding) {
            return this.execute(a, expectedEncoding, ErrorHandling.BEST_EFFORT);
        }

        public abstract TruffleStringIterator execute(AbstractTruffleString var1, Encoding var2, ErrorHandling var3);

        @Specialization
        static TruffleStringIterator createIterator(AbstractTruffleString a, Encoding expectedEncoding, ErrorHandling errorHandling, @Cached ToIndexableNode toIndexableNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode) {
            CompilerAsserts.partialEvaluationConstant((Object)errorHandling);
            a.checkEncoding(expectedEncoding);
            return AbstractTruffleString.forwardIterator(a, toIndexableNode.execute(a, a.data()), getCodeRangeANode.execute(a), expectedEncoding, errorHandling);
        }

        public static CreateCodePointIteratorNode create() {
            return TruffleStringFactory.CreateCodePointIteratorNodeGen.create();
        }

        public static CreateCodePointIteratorNode getUncached() {
            return TruffleStringFactory.CreateCodePointIteratorNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @GenerateUncached
    public static abstract class ForceEncodingNode
    extends Node {
        ForceEncodingNode() {
        }

        public abstract TruffleString execute(AbstractTruffleString var1, Encoding var2, Encoding var3);

        @Specialization(guards={"isCompatibleAndNotCompacted(a, expectedEncoding, targetEncoding)"})
        static TruffleString compatibleImmutable(TruffleString a, Encoding expectedEncoding, Encoding targetEncoding) {
            assert (!a.isJavaString());
            return a;
        }

        @Specialization(guards={"isCompatibleAndNotCompacted(a, expectedEncoding, targetEncoding)"})
        static TruffleString compatibleMutable(MutableTruffleString a, Encoding expectedEncoding, Encoding targetEncoding, @Cached AsTruffleStringNode asTruffleStringNode) {
            return asTruffleStringNode.execute(a, targetEncoding);
        }

        @Specialization(guards={"!isCompatibleAndNotCompacted(a, expectedEncoding, targetEncoding)"})
        static TruffleString reinterpret(AbstractTruffleString a, Encoding expectedEncoding, Encoding targetEncoding, @Cached ToIndexableNode toIndexableNode, @Cached ConditionProfile managedProfile, @Cached ConditionProfile inflateProfile, @Cached CopyToByteArrayNode copyToByteArrayNode, @Cached TStringInternalNodes.FromBufferWithStringCompactionNode fromBufferWithStringCompactionNode, @Cached TStringInternalNodes.FromNativePointerNode fromNativePointerNode) {
            Object arrayA = toIndexableNode.execute(a, a.data());
            int byteLength = a.length() << expectedEncoding.naturalStride;
            if (managedProfile.profile(arrayA instanceof byte[] || a.isMutable())) {
                int offset;
                Object arrayNoCompaction;
                if (inflateProfile.profile(TStringGuards.isUTF16Or32(expectedEncoding) && a.stride() != expectedEncoding.naturalStride)) {
                    byte[] inflated = new byte[byteLength];
                    copyToByteArrayNode.execute(a, 0, inflated, 0, byteLength, expectedEncoding);
                    arrayNoCompaction = inflated;
                    offset = 0;
                } else {
                    arrayNoCompaction = arrayA;
                    offset = a.offset();
                }
                return fromBufferWithStringCompactionNode.execute(arrayNoCompaction, offset, byteLength, targetEncoding, a.isMutable(), true);
            }
            assert (arrayA instanceof AbstractTruffleString.NativePointer);
            return fromNativePointerNode.execute((AbstractTruffleString.NativePointer)arrayA, a.offset(), byteLength, targetEncoding, true);
        }

        static boolean isCompatibleAndNotCompacted(AbstractTruffleString a, Encoding expectedEncoding, Encoding targetEncoding) {
            return expectedEncoding.naturalStride == targetEncoding.naturalStride && (a.encoding() == targetEncoding.id || a.stride() == targetEncoding.naturalStride && a.isCompatibleTo(targetEncoding));
        }

        public static ForceEncodingNode create() {
            return TruffleStringFactory.ForceEncodingNodeGen.create();
        }

        public static ForceEncodingNode getUncached() {
            return TruffleStringFactory.ForceEncodingNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @GenerateUncached
    public static abstract class SwitchEncodingNode
    extends Node {
        SwitchEncodingNode() {
        }

        public abstract TruffleString execute(AbstractTruffleString var1, Encoding var2);

        @Specialization(guards={"a.isCompatibleTo(encoding)"})
        static TruffleString compatibleImmutable(TruffleString a, Encoding encoding) {
            assert (!a.isJavaString());
            return a;
        }

        @Specialization(guards={"a.isCompatibleTo(encoding)"})
        static TruffleString compatibleMutable(MutableTruffleString a, Encoding encoding, @Cached AsTruffleStringNode asTruffleStringNode) {
            return asTruffleStringNode.execute(a, encoding);
        }

        @Specialization(guards={"!a.isCompatibleTo(encoding)"})
        static TruffleString transCode(TruffleString a, Encoding encoding, @Cached ConditionProfile cacheHit, @Cached ToIndexableNode toIndexableNode, @Cached @Cached.Shared(value="transCodeNode") TStringInternalNodes.TransCodeNode transCodeNode) {
            TruffleString transCoded;
            if (a.isEmpty()) {
                return encoding.getEmpty();
            }
            TruffleString cur = a.next;
            assert (!a.isJavaString());
            if (cur != null) {
                while (cur != a && cur.encoding() != encoding.id || TStringGuards.isUTF16(encoding) && cur.isJavaString()) {
                    cur = cur.next;
                }
                if (cacheHit.profile(cur.encoding() == encoding.id)) {
                    assert (!cur.isJavaString());
                    return cur;
                }
            }
            if (!(transCoded = transCodeNode.execute(a, toIndexableNode.execute(a, a.data()), a.codePointLength(), a.codeRange(), encoding)).isCacheHead()) {
                a.cacheInsert(transCoded);
            }
            return transCoded;
        }

        @Specialization(guards={"!a.isCompatibleTo(encoding)"})
        TruffleString transCodeMutable(MutableTruffleString a, Encoding encoding, @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode, @Cached @Cached.Shared(value="transCodeNode") TStringInternalNodes.TransCodeNode transCodeNode, @Cached ConditionProfile isCompatibleProfile) {
            if (a.isEmpty()) {
                return encoding.getEmpty();
            }
            int codePointLengthA = getCodePointLengthNode.execute(a);
            int codeRangeA = getCodeRangeNode.execute(a);
            if (isCompatibleProfile.profile(codeRangeA < encoding.maxCompatibleCodeRange)) {
                int strideDst = Stride.fromCodeRange(codeRangeA, encoding);
                byte[] arrayDst = new byte[a.length() << strideDst];
                TStringOps.arraycopyWithStride(this, a.data(), a.offset(), a.stride(), 0, arrayDst, 0, strideDst, 0, a.length());
                return TruffleString.createFromByteArray(arrayDst, a.length(), strideDst, encoding, codePointLengthA, codeRangeA);
            }
            return transCodeNode.execute(a, a.data(), codePointLengthA, codeRangeA, encoding);
        }

        public static SwitchEncodingNode create() {
            return TruffleStringFactory.SwitchEncodingNodeGen.create();
        }

        public static SwitchEncodingNode getUncached() {
            return TruffleStringFactory.SwitchEncodingNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @GenerateUncached
    public static abstract class ToJavaStringNode
    extends Node {
        ToJavaStringNode() {
        }

        public abstract String execute(AbstractTruffleString var1);

        @Specialization
        static String doUTF16(TruffleString a, @Cached ConditionProfile cacheHit, @Cached ToIndexableNode toIndexableNode, @Cached TStringInternalNodes.ToJavaStringNode toJavaStringNode) {
            if (a.isEmpty()) {
                return "";
            }
            TruffleString cur = a.next;
            if (cur != null) {
                while (cur != a && !cur.isJavaString()) {
                    cur = cur.next;
                }
                if (cacheHit.profile(cur.isJavaString())) {
                    return (String)cur.data();
                }
            }
            if ((cur = a.next) != null) {
                while (cur != a && !cur.isCompatibleTo(Encoding.UTF_16)) {
                    cur = cur.next;
                }
            } else {
                cur = a;
            }
            if (cur.isJavaString()) {
                return (String)cur.data();
            }
            TruffleString s = toJavaStringNode.execute(cur, toIndexableNode.execute(cur, cur.data()));
            a.cacheInsert(s);
            return (String)s.data();
        }

        @Specialization
        static String doMutable(MutableTruffleString a, @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode, @Cached TStringInternalNodes.TransCodeNode transCodeNode, @Cached TStringInternalNodes.CreateJavaStringNode createJavaStringNode) {
            int codeRangeA;
            if (a.isEmpty()) {
                return "";
            }
            AbstractTruffleString utf16String = TStringGuards.isUTF16(a.encoding()) || (codeRangeA = getCodeRangeNode.execute(a)) < Encoding.UTF_16.maxCompatibleCodeRange ? a : transCodeNode.execute(a, a.data(), getCodePointLengthNode.execute(a), codeRangeA, Encoding.UTF_16);
            return createJavaStringNode.execute(utf16String, utf16String.data());
        }

        public static ToJavaStringNode create() {
            return TruffleStringFactory.ToJavaStringNodeGen.create();
        }

        public static ToJavaStringNode getUncached() {
            return TruffleStringFactory.ToJavaStringNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringAccessor.class})
    @GenerateUncached
    public static abstract class CopyToNativeMemoryNode
    extends Node {
        CopyToNativeMemoryNode() {
        }

        public abstract void execute(AbstractTruffleString var1, int var2, Object var3, int var4, int var5, Encoding var6);

        @Specialization
        void doCopy(AbstractTruffleString a, int byteFromIndexA, Object pointerObject, int byteFromIndexB, int byteLength, Encoding expectedEncoding, @Cached(value="createInteropLibrary()", uncached="getUncachedInteropLibrary()") Node interopLibrary, @Cached ToIndexableNode toIndexableNode, @Cached ConditionProfile utf16Profile, @Cached ConditionProfile utf16S0Profile, @Cached ConditionProfile utf32Profile, @Cached ConditionProfile utf32S0Profile, @Cached ConditionProfile utf32S1Profile) {
            CopyToByteArrayNode.doCopyInternal(this, a, byteFromIndexA, AbstractTruffleString.NativePointer.create(this, pointerObject, interopLibrary, byteFromIndexB), byteFromIndexB, byteLength, expectedEncoding, toIndexableNode, utf16Profile, utf16S0Profile, utf32Profile, utf32S0Profile, utf32S1Profile);
        }

        public static CopyToNativeMemoryNode create() {
            return TruffleStringFactory.CopyToNativeMemoryNodeGen.create();
        }

        public static CopyToNativeMemoryNode getUncached() {
            return TruffleStringFactory.CopyToNativeMemoryNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @GenerateUncached
    public static abstract class CopyToByteArrayNode
    extends Node {
        CopyToByteArrayNode() {
        }

        public final byte[] execute(AbstractTruffleString string, Encoding expectedEncoding) {
            int byteLength = string.byteLength(expectedEncoding);
            byte[] copy = new byte[byteLength];
            this.execute(string, 0, copy, 0, byteLength, expectedEncoding);
            return copy;
        }

        public abstract void execute(AbstractTruffleString var1, int var2, byte[] var3, int var4, int var5, Encoding var6);

        @Specialization
        void doCopy(AbstractTruffleString a, int byteFromIndexA, byte[] arrayB, int byteFromIndexB, int byteLength, Encoding expectedEncoding, @Cached ToIndexableNode toIndexableNode, @Cached ConditionProfile utf16Profile, @Cached ConditionProfile utf16S0Profile, @Cached ConditionProfile utf32Profile, @Cached ConditionProfile utf32S0Profile, @Cached ConditionProfile utf32S1Profile) {
            AbstractTruffleString.boundsCheckRegionI(byteFromIndexB, byteLength, arrayB.length);
            CopyToByteArrayNode.doCopyInternal(this, a, byteFromIndexA, arrayB, byteFromIndexB, byteLength, expectedEncoding, toIndexableNode, utf16Profile, utf16S0Profile, utf32Profile, utf32S0Profile, utf32S1Profile);
        }

        private static void doCopyInternal(Node location, AbstractTruffleString a, int byteFromIndexA, Object arrayB, int byteFromIndexB, int byteLength, Encoding expectedEncoding, ToIndexableNode toIndexableNode, ConditionProfile utf16Profile, ConditionProfile utf16S0Profile, ConditionProfile utf32Profile, ConditionProfile utf32S0Profile, ConditionProfile utf32S1Profile) {
            int fromIndexA;
            if (byteLength == 0) {
                return;
            }
            a.checkEncoding(expectedEncoding);
            int offsetA = a.offset();
            boolean offsetB = false;
            Object arrayA = toIndexableNode.execute(a, a.data());
            if (utf16Profile.profile(TStringGuards.isUTF16(expectedEncoding))) {
                a.boundsCheckByteIndexUTF16(byteFromIndexA);
                AbstractTruffleString.checkByteLengthUTF16(byteLength);
                fromIndexA = AbstractTruffleString.rawIndex(byteFromIndexA, expectedEncoding);
                int fromIndexB = AbstractTruffleString.rawIndex(byteFromIndexB, expectedEncoding);
                int length = AbstractTruffleString.rawIndex(byteLength, expectedEncoding);
                a.boundsCheckRegionRaw(fromIndexA, length);
                if (utf16S0Profile.profile(TStringGuards.isStride0(a))) {
                    TStringOps.arraycopyWithStride(location, arrayA, offsetA, 0, fromIndexA, arrayB, 0, 1, fromIndexB, length);
                    return;
                }
            } else if (utf32Profile.profile(TStringGuards.isUTF32(expectedEncoding))) {
                a.boundsCheckByteIndexUTF32(byteFromIndexA);
                AbstractTruffleString.checkByteLengthUTF32(byteLength);
                fromIndexA = AbstractTruffleString.rawIndex(byteFromIndexA, expectedEncoding);
                int fromIndexB = AbstractTruffleString.rawIndex(byteFromIndexB, expectedEncoding);
                int length = AbstractTruffleString.rawIndex(byteLength, expectedEncoding);
                a.boundsCheckRegionRaw(fromIndexA, length);
                if (utf32S0Profile.profile(TStringGuards.isStride0(a))) {
                    TStringOps.arraycopyWithStride(location, arrayA, offsetA, 0, fromIndexA, arrayB, 0, 2, fromIndexB, length);
                    return;
                }
                if (utf32S1Profile.profile(TStringGuards.isStride1(a))) {
                    TStringOps.arraycopyWithStride(location, arrayA, offsetA, 1, fromIndexA, arrayB, 0, 2, fromIndexB, length);
                    return;
                }
            }
            int byteLengthA = a.length() << a.stride();
            AbstractTruffleString.boundsCheckRegionI(byteFromIndexA, byteLength, byteLengthA);
            TStringOps.arraycopyWithStride(location, arrayA, offsetA, 0, byteFromIndexA, arrayB, 0, 0, byteFromIndexB, byteLength);
        }

        public static CopyToByteArrayNode create() {
            return TruffleStringFactory.CopyToByteArrayNodeGen.create();
        }

        public static CopyToByteArrayNode getUncached() {
            return TruffleStringFactory.CopyToByteArrayNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @GenerateUncached
    public static abstract class GetInternalNativePointerNode
    extends Node {
        GetInternalNativePointerNode() {
        }

        public abstract Object execute(AbstractTruffleString var1, Encoding var2);

        @Specialization
        static Object getNativePointer(AbstractTruffleString a, Encoding expectedEncoding) {
            a.checkEncoding(expectedEncoding);
            if (!a.isNative()) {
                throw InternalErrors.unsupportedOperation("string is not backed by a native pointer!");
            }
            return ((AbstractTruffleString.NativePointer)a.data()).getPointerObject();
        }

        public static GetInternalNativePointerNode create() {
            return TruffleStringFactory.GetInternalNativePointerNodeGen.create();
        }

        public static GetInternalNativePointerNode getUncached() {
            return TruffleStringFactory.GetInternalNativePointerNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @GenerateUncached
    public static abstract class GetInternalByteArrayNode
    extends Node {
        GetInternalByteArrayNode() {
        }

        public abstract InternalByteArray execute(AbstractTruffleString var1, Encoding var2);

        @Specialization
        InternalByteArray getInternalByteArray(AbstractTruffleString a, Encoding expectedEncoding, @Cached ToIndexableNode toIndexableNode, @Cached ConditionProfile utf16Profile, @Cached ConditionProfile utf16S0Profile, @Cached ConditionProfile utf32Profile, @Cached ConditionProfile utf32S0Profile, @Cached ConditionProfile utf32S1Profile, @Cached ConditionProfile isByteArrayProfile) {
            if (a.isEmpty()) {
                return InternalByteArray.EMPTY;
            }
            a.checkEncoding(expectedEncoding);
            Object arrayA = toIndexableNode.execute(a, a.data());
            if (utf16Profile.profile(TStringGuards.isUTF16(expectedEncoding))) {
                if (utf16S0Profile.profile(TStringGuards.isStride0(a))) {
                    return this.inflate(a, arrayA, 0, 1);
                }
            } else if (utf32Profile.profile(TStringGuards.isUTF32(expectedEncoding))) {
                if (utf32S0Profile.profile(TStringGuards.isStride0(a))) {
                    return this.inflate(a, arrayA, 0, 2);
                }
                if (utf32S1Profile.profile(TStringGuards.isStride1(a))) {
                    return this.inflate(a, arrayA, 1, 2);
                }
            }
            int byteLength = a.length() << a.stride();
            if (isByteArrayProfile.profile(arrayA instanceof byte[])) {
                return new InternalByteArray((byte[])arrayA, a.offset(), byteLength);
            }
            return new InternalByteArray(TStringOps.arraycopyOfWithStride(this, arrayA, a.offset(), byteLength, 0, byteLength, 0), 0, byteLength);
        }

        private InternalByteArray inflate(AbstractTruffleString a, Object arrayA, int strideA, int strideB) {
            assert (a.stride() == strideA);
            CompilerAsserts.partialEvaluationConstant(strideA);
            CompilerAsserts.partialEvaluationConstant(strideB);
            return new InternalByteArray(TStringOps.arraycopyOfWithStride(this, arrayA, a.offset(), a.length(), strideA, a.length(), strideB), 0, a.length() << strideB);
        }

        public static GetInternalByteArrayNode create() {
            return TruffleStringFactory.GetInternalByteArrayNodeGen.create();
        }

        public static GetInternalByteArrayNode getUncached() {
            return TruffleStringFactory.GetInternalByteArrayNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class ParseDoubleNode
    extends Node {
        ParseDoubleNode() {
        }

        public abstract double execute(AbstractTruffleString var1) throws NumberFormatException;

        @Specialization(guards={"isLazyLongSafeInteger(a)"})
        static double doLazyLong(AbstractTruffleString a) {
            return ((AbstractTruffleString.LazyLong)a.data()).value;
        }

        @Specialization(guards={"!isLazyLongSafeInteger(a)"})
        static double parseDouble(AbstractTruffleString a, @Cached ToIndexableNode toIndexableNode, @Cached TStringInternalNodes.ParseDoubleNode parseDoubleNode) throws NumberFormatException {
            return parseDoubleNode.execute(a, toIndexableNode.execute(a, a.data()));
        }

        static boolean isLazyLongSafeInteger(AbstractTruffleString a) {
            return a.isLazyLong() && NumberConversion.isSafeInteger(((AbstractTruffleString.LazyLong)a.data()).value);
        }

        public static ParseDoubleNode create() {
            return TruffleStringFactory.ParseDoubleNodeGen.create();
        }

        public static ParseDoubleNode getUncached() {
            return TruffleStringFactory.ParseDoubleNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class ParseLongNode
    extends Node {
        ParseLongNode() {
        }

        public abstract long execute(AbstractTruffleString var1, int var2) throws NumberFormatException;

        @Specialization(guards={"a.isLazyLong()", "radix == 10"})
        static long doLazyLong(AbstractTruffleString a, int radix) {
            return ((AbstractTruffleString.LazyLong)a.data()).value;
        }

        @Specialization(guards={"!a.isLazyLong() || radix != 10"})
        static long doParse(AbstractTruffleString a, int radix, @Cached ToIndexableNode toIndexableNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode, @Cached TStringInternalNodes.ParseLongNode parseLongNode, @Cached(value="createIdentityProfile()") IntValueProfile radixProfile) throws NumberFormatException {
            int codeRangeA = getCodeRangeANode.execute(a);
            return parseLongNode.execute(a, toIndexableNode.execute(a, a.data()), codeRangeA, Encoding.get(a.encoding()), radixProfile.profile(radix));
        }

        public static ParseLongNode create() {
            return TruffleStringFactory.ParseLongNodeGen.create();
        }

        public static ParseLongNode getUncached() {
            return TruffleStringFactory.ParseLongNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class ParseIntNode
    extends Node {
        ParseIntNode() {
        }

        public abstract int execute(AbstractTruffleString var1, int var2) throws NumberFormatException;

        @Specialization(guards={"a.isLazyLong()", "radix == 10"})
        static int doLazyLong(AbstractTruffleString a, int radix, @Cached BranchProfile errorProfile) throws NumberFormatException {
            long value2 = ((AbstractTruffleString.LazyLong)a.data()).value;
            if (value2 < Integer.MIN_VALUE || value2 > Integer.MAX_VALUE) {
                errorProfile.enter();
                throw NumberConversion.numberFormatException(a, NumberFormatException.Reason.OVERFLOW);
            }
            return (int)value2;
        }

        @Specialization(guards={"!a.isLazyLong() || radix != 10"})
        static int doParse(AbstractTruffleString a, int radix, @Cached ToIndexableNode toIndexableNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode, @Cached TStringInternalNodes.ParseIntNode parseIntNode, @Cached(value="createIdentityProfile()") IntValueProfile radixProfile) throws NumberFormatException {
            int codeRangeA = getCodeRangeANode.execute(a);
            return parseIntNode.execute(a, toIndexableNode.execute(a, a.data()), codeRangeA, Encoding.get(a.encoding()), radixProfile.profile(radix));
        }

        public static ParseIntNode create() {
            return TruffleStringFactory.ParseIntNodeGen.create();
        }

        public static ParseIntNode getUncached() {
            return TruffleStringFactory.ParseIntNodeGen.getUncached();
        }
    }

    public static final class IllegalByteArrayLengthException
    extends IllegalArgumentException {
        private static final long serialVersionUID = 2871353611734808666L;

        IllegalByteArrayLengthException(String msg) {
            super(msg);
        }
    }

    public static final class NumberFormatException
    extends Exception {
        private static final long serialVersionUID = 102938855488837538L;
        private final AbstractTruffleString string;
        private final int regionOffset;
        private final int regionLength;
        private final Reason reason;

        NumberFormatException(AbstractTruffleString string, Reason reason) {
            this(string, -1, -1, reason);
        }

        NumberFormatException(AbstractTruffleString string, int regionOffset, int regionLength, Reason reason) {
            this.string = string;
            this.regionOffset = regionOffset;
            this.regionLength = regionLength;
            this.reason = reason;
        }

        Reason getReason() {
            return this.reason;
        }

        AbstractTruffleString getString() {
            return this.string;
        }

        int getRegionByteOffset() {
            return this.regionOffset < 0 ? this.regionOffset : this.regionOffset << this.string.stride();
        }

        int getRegionByteLength() {
            return this.regionLength < 0 ? this.regionLength : this.regionLength << this.string.stride();
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public String getMessage() {
            StringBuilder sb = new StringBuilder();
            sb.append("error parsing \"").append(this.getString()).append("\": ");
            sb.append(this.getReason().message);
            if (this.regionOffset >= 0) {
                if (this.regionLength == 1) {
                    sb.append(" at byte index ").append(this.getRegionByteOffset());
                } else {
                    sb.append(" from byte index ").append(this.getRegionByteOffset()).append(" to ").append(this.getRegionByteOffset() + this.getRegionByteLength());
                }
            }
            return sb.toString();
        }

        @Override
        public Throwable fillInStackTrace() {
            return this;
        }

        static enum Reason {
            EMPTY("no digits found"),
            INVALID_CODEPOINT("invalid codepoint"),
            LONE_SIGN("lone '+' or '-'"),
            OVERFLOW("overflow"),
            MALFORMED_HEX_ESCAPE("malformed hex escape sequence"),
            MULTIPLE_DECIMAL_POINTS("multiple decimal points"),
            UNSUPPORTED_RADIX("unsupported radix");

            private final String message;

            private Reason(String message) {
                this.message = message;
            }

            public String getMessage() {
                return this.message;
            }
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class EqualNode
    extends Node {
        EqualNode() {
        }

        public abstract boolean execute(AbstractTruffleString var1, AbstractTruffleString var2, Encoding var3);

        @Specialization(guards={"identical(a, b)"})
        static boolean sameObject(AbstractTruffleString a, AbstractTruffleString b, Encoding expectedEncoding) {
            return true;
        }

        @Specialization(guards={"!identical(a, b)"})
        boolean check(AbstractTruffleString a, AbstractTruffleString b, Encoding expectedEncoding, @Cached ToIndexableNode toIndexableNodeA, @Cached ToIndexableNode toIndexableNodeB, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode, @Cached ConditionProfile lengthAndCodeRangeCheckProfile, @Cached BranchProfile compareHashProfile, @Cached ConditionProfile checkFirstByteProfile) {
            int codeRangeA = getCodeRangeANode.execute(a);
            int codeRangeB = getCodeRangeBNode.execute(b);
            a.looseCheckEncoding(expectedEncoding, codeRangeA);
            b.looseCheckEncoding(expectedEncoding, codeRangeB);
            return EqualNode.checkContentEquals(a, codeRangeA, b, codeRangeB, toIndexableNodeA, toIndexableNodeB, lengthAndCodeRangeCheckProfile, compareHashProfile, checkFirstByteProfile, this);
        }

        static boolean checkContentEquals(AbstractTruffleString a, int codeRangeA, AbstractTruffleString b, int codeRangeB, ToIndexableNode toIndexableNodeA, ToIndexableNode toIndexableNodeB, ConditionProfile lengthAndCodeRangeCheckProfile, BranchProfile compareHashProfile, ConditionProfile checkFirstByteProfile, EqualNode equalNode) {
            assert (TSCodeRange.isKnown(codeRangeA, codeRangeB));
            int lengthCMP = a.length();
            if (lengthAndCodeRangeCheckProfile.profile(lengthCMP != b.length() || codeRangeA != codeRangeB)) {
                return false;
            }
            if (a.isHashCodeCalculated() && b.isHashCodeCalculated()) {
                compareHashProfile.enter();
                if (a.getHashCodeUnsafe() != b.getHashCodeUnsafe()) {
                    return false;
                }
            }
            if (lengthCMP == 0) {
                return true;
            }
            Object arrayA = toIndexableNodeA.execute(a, a.data());
            Object arrayB = toIndexableNodeB.execute(b, b.data());
            int strideA = a.stride();
            int strideB = b.stride();
            if (checkFirstByteProfile.profile(arrayA instanceof byte[] && arrayB instanceof byte[] && (strideA | strideB) == 0)) {
                if (((byte[])arrayA)[a.offset()] != ((byte[])arrayB)[b.offset()]) {
                    return false;
                }
                if (lengthCMP == 1) {
                    return true;
                }
            }
            return TStringOps.regionEqualsWithOrMaskWithStride(equalNode, a, arrayA, strideA, 0, b, arrayB, strideB, 0, null, lengthCMP);
        }

        public static EqualNode create() {
            return TruffleStringFactory.EqualNodeGen.create();
        }

        public static EqualNode getUncached() {
            return TruffleStringFactory.EqualNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class SubstringByteIndexNode
    extends Node {
        SubstringByteIndexNode() {
        }

        public abstract TruffleString execute(AbstractTruffleString var1, int var2, int var3, Encoding var4, boolean var5);

        static boolean isSame(int v0, int v1) {
            return v0 == v1;
        }

        @Specialization(guards={"isSame(byteLength, 0)"})
        static TruffleString substringEmpty(AbstractTruffleString a, int fromByteIndex, int byteLength, Encoding expectedEncoding, boolean lazy) {
            a.checkEncoding(expectedEncoding);
            int fromIndex = AbstractTruffleString.rawIndex(fromByteIndex, expectedEncoding);
            a.boundsCheckRegionRaw(fromIndex, 0);
            return expectedEncoding.getEmpty();
        }

        @Specialization(guards={"byteLength != 0"})
        static TruffleString substringRaw(AbstractTruffleString a, int fromByteIndex, int byteLength, Encoding expectedEncoding, boolean lazy, @Cached ToIndexableNode toIndexableNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode, @Cached TStringInternalNodes.SubstringNode substringNode) {
            a.checkEncoding(expectedEncoding);
            int codeRangeA = getCodeRangeANode.execute(a);
            int fromIndex = AbstractTruffleString.rawIndex(fromByteIndex, expectedEncoding);
            int length = AbstractTruffleString.rawIndex(byteLength, expectedEncoding);
            a.boundsCheckRegionRaw(fromIndex, length);
            return substringNode.execute(a, toIndexableNode.execute(a, a.data()), codeRangeA, expectedEncoding, fromIndex, length, lazy && a.isImmutable());
        }

        public static SubstringByteIndexNode create() {
            return TruffleStringFactory.SubstringByteIndexNodeGen.create();
        }

        public static SubstringByteIndexNode getUncached() {
            return TruffleStringFactory.SubstringByteIndexNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class SubstringNode
    extends Node {
        SubstringNode() {
        }

        public abstract TruffleString execute(AbstractTruffleString var1, int var2, int var3, Encoding var4, boolean var5);

        @Specialization
        static TruffleString substring(AbstractTruffleString a, int fromIndex, int length, Encoding expectedEncoding, boolean lazy, @Cached ToIndexableNode toIndexableNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode, @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode, @Cached TStringInternalNodes.CodePointIndexToRawNode translateIndexNode, @Cached TStringInternalNodes.SubstringNode substringNode) {
            a.checkEncoding(expectedEncoding);
            a.boundsCheckRegion(fromIndex, length, getCodePointLengthNode);
            if (length == 0) {
                return expectedEncoding.getEmpty();
            }
            Object arrayA = toIndexableNode.execute(a, a.data());
            int codeRangeA = getCodeRangeANode.execute(a);
            int fromIndexRaw = translateIndexNode.execute(a, arrayA, codeRangeA, expectedEncoding, 0, fromIndex, false);
            int lengthRaw = translateIndexNode.execute(a, arrayA, codeRangeA, expectedEncoding, fromIndexRaw, length, true);
            return substringNode.execute(a, arrayA, codeRangeA, expectedEncoding, fromIndexRaw, lengthRaw, lazy && a.isImmutable());
        }

        public static SubstringNode create() {
            return TruffleStringFactory.SubstringNodeGen.create();
        }

        public static SubstringNode getUncached() {
            return TruffleStringFactory.SubstringNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class RepeatNode
    extends Node {
        RepeatNode() {
        }

        public abstract TruffleString execute(AbstractTruffleString var1, int var2, Encoding var3);

        @Specialization
        TruffleString repeat(AbstractTruffleString a, int n, Encoding expectedEncoding, @Cached AsTruffleStringNode asTruffleStringNode, @Cached ToIndexableNode toIndexableNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode, @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode, @Cached TStringInternalNodes.CalcStringAttributesNode calcStringAttributesNode, @Cached ConditionProfile brokenProfile, @Cached BranchProfile outOfMemoryProfile) {
            a.checkEncoding(expectedEncoding);
            if (n < 0) {
                throw InternalErrors.illegalArgument("n must be positive");
            }
            if (a.isEmpty() || n == 0) {
                return expectedEncoding.getEmpty();
            }
            if (n == 1) {
                return asTruffleStringNode.execute(a, expectedEncoding);
            }
            Object arrayA = toIndexableNode.execute(a, a.data());
            int codeRangeA = getCodeRangeNode.execute(a);
            int codePointLengthA = getCodePointLengthNode.execute(a);
            int byteLengthA = a.length() << a.stride();
            long byteLength = (long)byteLengthA * (long)n;
            if (Long.compareUnsigned(byteLength, 0x7FFFFFF7L) > 0) {
                outOfMemoryProfile.enter();
                throw InternalErrors.outOfMemory();
            }
            byte[] array = new byte[(int)byteLength];
            int offsetB = 0;
            for (int i = 0; i < n; ++i) {
                TStringOps.arraycopyWithStride(this, arrayA, a.offset(), 0, 0, array, offsetB, 0, 0, byteLengthA);
                offsetB += byteLengthA;
                TStringConstants.truffleSafePointPoll(this, i + 1);
            }
            int length = (int)(byteLength >> a.stride());
            if (brokenProfile.profile(TStringGuards.isBrokenFixedWidth(codeRangeA) || TStringGuards.isBrokenMultiByte(codeRangeA))) {
                long attrs = calcStringAttributesNode.execute(null, array, 0, length, a.stride(), expectedEncoding, TSCodeRange.getUnknown());
                codeRangeA = StringAttributes.getCodeRange(attrs);
                codePointLengthA = StringAttributes.getCodePointLength(attrs);
            } else {
                codePointLengthA *= n;
            }
            return TruffleString.createFromByteArray(array, length, a.stride(), expectedEncoding, codePointLengthA, codeRangeA);
        }

        public static RepeatNode create() {
            return TruffleStringFactory.RepeatNodeGen.create();
        }

        public static RepeatNode getUncached() {
            return TruffleStringFactory.RepeatNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class ConcatNode
    extends Node {
        ConcatNode() {
        }

        public abstract TruffleString execute(AbstractTruffleString var1, AbstractTruffleString var2, Encoding var3, boolean var4);

        @Specialization(guards={"isEmpty(a)"})
        static TruffleString aEmpty(AbstractTruffleString a, TruffleString b, Encoding expectedEncoding, boolean lazy) {
            CompilerAsserts.partialEvaluationConstant(lazy);
            if (AbstractTruffleString.DEBUG_STRICT_ENCODING_CHECKS) {
                b.looseCheckEncoding(expectedEncoding, b.codeRange());
                return b.switchEncodingUncached(expectedEncoding);
            }
            b.checkEncoding(expectedEncoding);
            return b;
        }

        @Specialization(guards={"isEmpty(a)"})
        static TruffleString aEmptyMutable(AbstractTruffleString a, MutableTruffleString b, Encoding expectedEncoding, boolean lazy, @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode, @Cached TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode fromBufferWithStringCompactionNode) {
            CompilerAsserts.partialEvaluationConstant(lazy);
            if (AbstractTruffleString.DEBUG_STRICT_ENCODING_CHECKS) {
                b.looseCheckEncoding(expectedEncoding, TStringInternalNodes.GetCodeRangeNode.getUncached().execute(b));
                return b.switchEncodingUncached(expectedEncoding);
            }
            int codeRange = getCodeRangeNode.execute(b);
            b.looseCheckEncoding(expectedEncoding, codeRange);
            return fromBufferWithStringCompactionNode.execute(b.data(), b.offset(), b.length() << b.stride(), expectedEncoding, getCodePointLengthNode.execute(b), codeRange);
        }

        @Specialization(guards={"isEmpty(b)"})
        static TruffleString bEmpty(TruffleString a, AbstractTruffleString b, Encoding expectedEncoding, boolean lazy) {
            CompilerAsserts.partialEvaluationConstant(lazy);
            if (AbstractTruffleString.DEBUG_STRICT_ENCODING_CHECKS) {
                a.looseCheckEncoding(expectedEncoding, a.codeRange());
                return a.switchEncodingUncached(expectedEncoding);
            }
            a.checkEncoding(expectedEncoding);
            return a;
        }

        @Specialization(guards={"isEmpty(b)"})
        static TruffleString bEmptyMutable(MutableTruffleString a, AbstractTruffleString b, Encoding expectedEncoding, boolean lazy, @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode, @Cached TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode fromBufferWithStringCompactionNode) {
            CompilerAsserts.partialEvaluationConstant(lazy);
            if (AbstractTruffleString.DEBUG_STRICT_ENCODING_CHECKS) {
                a.looseCheckEncoding(expectedEncoding, TStringInternalNodes.GetCodeRangeNode.getUncached().execute(a));
                return a.switchEncodingUncached(expectedEncoding);
            }
            int codeRange = getCodeRangeNode.execute(a);
            a.looseCheckEncoding(expectedEncoding, codeRange);
            return fromBufferWithStringCompactionNode.execute(a.data(), a.offset(), a.length() << a.stride(), expectedEncoding, getCodePointLengthNode.execute(a), codeRange);
        }

        @Specialization(guards={"!isEmpty(a)", "!isEmpty(b)"})
        static TruffleString doConcat(AbstractTruffleString a, AbstractTruffleString b, Encoding encoding, boolean lazy, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode, @Cached TStringInternalNodes.StrideFromCodeRangeNode getStrideNode, @Cached TStringInternalNodes.ConcatEagerNode concatEagerNode, @Cached AsTruffleStringNode asTruffleStringANode, @Cached AsTruffleStringNode asTruffleStringBNode, @Cached BranchProfile outOfMemoryProfile, @Cached ConditionProfile lazyProfile) {
            CompilerAsserts.partialEvaluationConstant(lazy);
            int codeRangeA = getCodeRangeANode.execute(a);
            int codeRangeB = getCodeRangeBNode.execute(b);
            a.looseCheckEncoding(encoding, codeRangeA);
            b.looseCheckEncoding(encoding, codeRangeB);
            int commonCodeRange = TSCodeRange.commonCodeRange(codeRangeA, codeRangeB);
            assert (!TStringGuards.isBrokenMultiByte(codeRangeA) && !TStringGuards.isBrokenMultiByte(codeRangeB) || TStringGuards.isBrokenMultiByte(commonCodeRange));
            int targetStride = getStrideNode.execute(commonCodeRange, encoding);
            int length = ConcatNode.addByteLengths(a, b, targetStride, outOfMemoryProfile);
            boolean valid = !TStringGuards.isBrokenMultiByte(commonCodeRange);
            if (lazyProfile.profile(lazy && valid && (a.isImmutable() || b.isImmutable()) && length << targetStride >= 40)) {
                if (AbstractTruffleString.DEBUG_STRICT_ENCODING_CHECKS) {
                    return TruffleString.createLazyConcat(ConcatNode.asTruffleStringLoose(a, encoding), ConcatNode.asTruffleStringLoose(b, encoding), encoding, length, targetStride);
                }
                return TruffleString.createLazyConcat(asTruffleStringANode.execute(a, encoding), asTruffleStringBNode.execute(b, encoding), encoding, length, targetStride);
            }
            return concatEagerNode.execute(a, b, encoding, length, targetStride, commonCodeRange);
        }

        static int addByteLengths(AbstractTruffleString a, AbstractTruffleString b, int targetStride, BranchProfile outOfMemoryProfile) {
            long length = (long)a.length() + (long)b.length();
            if (length << targetStride > 0x7FFFFFF7L) {
                outOfMemoryProfile.enter();
                throw InternalErrors.outOfMemory();
            }
            return (int)length;
        }

        private static TruffleString asTruffleStringLoose(AbstractTruffleString a, Encoding encoding) {
            if (a.isImmutable()) {
                return (TruffleString)a;
            }
            return TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode.getUncached().execute(a.data(), a.offset(), a.length() << a.stride(), encoding, TStringInternalNodes.GetCodePointLengthNode.getUncached().execute(a), TStringInternalNodes.GetCodeRangeNode.getUncached().execute(a));
        }

        public static ConcatNode create() {
            return TruffleStringFactory.ConcatNodeGen.create();
        }

        public static ConcatNode getUncached() {
            return TruffleStringFactory.ConcatNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class RegionEqualByteIndexNode
    extends Node {
        RegionEqualByteIndexNode() {
        }

        public final boolean execute(AbstractTruffleString a, int fromByteIndexA, AbstractTruffleString b, int fromByteIndexB, int length, Encoding expectedEncoding) {
            return this.execute(a, fromByteIndexA, b, fromByteIndexB, length, null, expectedEncoding);
        }

        public final boolean execute(AbstractTruffleString a, int fromByteIndexA, WithMask b, int fromByteIndexB, int length, Encoding expectedEncoding) {
            return this.execute(a, fromByteIndexA, b.string, fromByteIndexB, length, b.mask, expectedEncoding);
        }

        abstract boolean execute(AbstractTruffleString var1, int var2, AbstractTruffleString var3, int var4, int var5, byte[] var6, Encoding var7);

        @Specialization
        boolean regionEquals(AbstractTruffleString a, int byteFromIndexA, AbstractTruffleString b, int byteFromIndexB, int byteLength, byte[] mask, Encoding expectedEncoding, @Cached ToIndexableNode toIndexableNodeA, @Cached ToIndexableNode toIndexableNodeB, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode) {
            if (byteLength == 0) {
                return true;
            }
            int codeRangeA = getCodeRangeANode.execute(a);
            int codeRangeB = getCodeRangeBNode.execute(b);
            a.looseCheckEncoding(expectedEncoding, codeRangeA);
            b.looseCheckEncoding(expectedEncoding, codeRangeB);
            int fromIndexA = AbstractTruffleString.rawIndex(byteFromIndexA, expectedEncoding);
            int fromIndexB = AbstractTruffleString.rawIndex(byteFromIndexB, expectedEncoding);
            int length = AbstractTruffleString.rawIndex(byteLength, expectedEncoding);
            a.boundsCheckRegionRaw(fromIndexA, length);
            b.boundsCheckRegionRaw(fromIndexB, length);
            Object arrayA = toIndexableNodeA.execute(a, a.data());
            Object arrayB = toIndexableNodeB.execute(b, b.data());
            return TStringOps.regionEqualsWithOrMaskWithStride(this, a, arrayA, a.stride(), fromIndexA, b, arrayB, b.stride(), fromIndexB, mask, length);
        }

        public static RegionEqualByteIndexNode create() {
            return TruffleStringFactory.RegionEqualByteIndexNodeGen.create();
        }

        public static RegionEqualByteIndexNode getUncached() {
            return TruffleStringFactory.RegionEqualByteIndexNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class RegionEqualNode
    extends Node {
        RegionEqualNode() {
        }

        public abstract boolean execute(AbstractTruffleString var1, int var2, AbstractTruffleString var3, int var4, int var5, Encoding var6);

        @Specialization
        static boolean regionEquals(AbstractTruffleString a, int fromIndexA, AbstractTruffleString b, int fromIndexB, int length, Encoding expectedEncoding, @Cached ToIndexableNode toIndexableNodeA, @Cached ToIndexableNode toIndexableNodeB, @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthANode, @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthBNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode, @Cached TStringInternalNodes.RegionEqualsNode regionEqualsNode) {
            if (length == 0) {
                return true;
            }
            int codeRangeA = getCodeRangeANode.execute(a);
            int codeRangeB = getCodeRangeBNode.execute(b);
            a.looseCheckEncoding(expectedEncoding, codeRangeA);
            b.looseCheckEncoding(expectedEncoding, codeRangeB);
            a.boundsCheckRegion(fromIndexA, length, getCodePointLengthANode);
            b.boundsCheckRegion(fromIndexB, length, getCodePointLengthBNode);
            Object arrayA = toIndexableNodeA.execute(a, a.data());
            Object arrayB = toIndexableNodeB.execute(b, b.data());
            return regionEqualsNode.execute(a, arrayA, codeRangeA, fromIndexA, b, arrayB, codeRangeB, fromIndexB, length, expectedEncoding);
        }

        public static RegionEqualNode create() {
            return TruffleStringFactory.RegionEqualNodeGen.create();
        }

        public static RegionEqualNode getUncached() {
            return TruffleStringFactory.RegionEqualNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class CompareIntsUTF32Node
    extends Node {
        CompareIntsUTF32Node() {
        }

        public abstract int execute(AbstractTruffleString var1, AbstractTruffleString var2);

        @Specialization
        int compare(AbstractTruffleString a, AbstractTruffleString b, @Cached ToIndexableNode toIndexableNodeA, @Cached ToIndexableNode toIndexableNodeB, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode) {
            int cmp;
            int codeRangeA = getCodeRangeANode.execute(a);
            int codeRangeB = getCodeRangeBNode.execute(b);
            a.looseCheckEncoding(Encoding.UTF_32, codeRangeA);
            b.looseCheckEncoding(Encoding.UTF_32, codeRangeB);
            Object aData = toIndexableNodeA.execute(a, a.data());
            Object bData = toIndexableNodeB.execute(b, b.data());
            if (aData instanceof byte[] && bData instanceof byte[] && (a.stride() | b.stride()) == 0 && a.length() != 0 && b.length() != 0 && (cmp = Byte.compareUnsigned((byte)((byte[])aData)[a.offset()], (byte)((byte[])bData)[b.offset()])) != 0) {
                return cmp;
            }
            if (a == b) {
                return 0;
            }
            return TStringOpsNodes.memcmp(this, a, aData, b, bData);
        }

        public static CompareIntsUTF32Node create() {
            return TruffleStringFactory.CompareIntsUTF32NodeGen.create();
        }

        public static CompareIntsUTF32Node getUncached() {
            return TruffleStringFactory.CompareIntsUTF32NodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class CompareCharsUTF16Node
    extends Node {
        CompareCharsUTF16Node() {
        }

        public abstract int execute(AbstractTruffleString var1, AbstractTruffleString var2);

        @Specialization
        int compare(AbstractTruffleString a, AbstractTruffleString b, @Cached ToIndexableNode toIndexableNodeA, @Cached ToIndexableNode toIndexableNodeB, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode) {
            int cmp;
            int codeRangeA = getCodeRangeANode.execute(a);
            int codeRangeB = getCodeRangeBNode.execute(b);
            a.looseCheckEncoding(Encoding.UTF_16, codeRangeA);
            b.looseCheckEncoding(Encoding.UTF_16, codeRangeB);
            Object aData = toIndexableNodeA.execute(a, a.data());
            Object bData = toIndexableNodeB.execute(b, b.data());
            if (aData instanceof byte[] && bData instanceof byte[] && (a.stride() | b.stride()) == 0 && a.length() != 0 && b.length() != 0 && (cmp = Byte.compareUnsigned((byte)((byte[])aData)[a.offset()], (byte)((byte[])bData)[b.offset()])) != 0) {
                return cmp;
            }
            if (a == b) {
                return 0;
            }
            return TStringOpsNodes.memcmp(this, a, aData, b, bData);
        }

        public static CompareCharsUTF16Node create() {
            return TruffleStringFactory.CompareCharsUTF16NodeGen.create();
        }

        public static CompareCharsUTF16Node getUncached() {
            return TruffleStringFactory.CompareCharsUTF16NodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class CompareBytesNode
    extends Node {
        CompareBytesNode() {
        }

        public abstract int execute(AbstractTruffleString var1, AbstractTruffleString var2, Encoding var3);

        @Specialization
        int compare(AbstractTruffleString a, AbstractTruffleString b, Encoding expectedEncoding, @Cached ToIndexableNode toIndexableNodeA, @Cached ToIndexableNode toIndexableNodeB, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode) {
            int cmp;
            AbstractTruffleString.nullCheck((Object)expectedEncoding);
            int codeRangeA = getCodeRangeANode.execute(a);
            int codeRangeB = getCodeRangeBNode.execute(b);
            a.looseCheckEncoding(expectedEncoding, codeRangeA);
            b.looseCheckEncoding(expectedEncoding, codeRangeB);
            Object aData = toIndexableNodeA.execute(a, a.data());
            Object bData = toIndexableNodeB.execute(b, b.data());
            if (aData instanceof byte[] && bData instanceof byte[] && (a.stride() | b.stride()) == 0 && a.length() != 0 && b.length() != 0 && (cmp = Byte.compareUnsigned((byte)((byte[])aData)[a.offset()], (byte)((byte[])bData)[b.offset()])) != 0) {
                return cmp;
            }
            if (a == b) {
                return 0;
            }
            return TStringOpsNodes.memcmpBytes(this, a, aData, b, bData);
        }

        public static CompareBytesNode create() {
            return TruffleStringFactory.CompareBytesNodeGen.create();
        }

        public static CompareBytesNode getUncached() {
            return TruffleStringFactory.CompareBytesNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class LastByteIndexOfStringNode
    extends Node {
        LastByteIndexOfStringNode() {
        }

        public final int execute(AbstractTruffleString a, AbstractTruffleString b, int fromIndex, int toIndex, Encoding expectedEncoding) {
            return this.execute(a, b, fromIndex, toIndex, null, expectedEncoding);
        }

        public final int execute(AbstractTruffleString a, WithMask b, int fromIndex, int toIndex, Encoding expectedEncoding) {
            return this.execute(a, b.string, fromIndex, toIndex, b.mask, expectedEncoding);
        }

        abstract int execute(AbstractTruffleString var1, AbstractTruffleString var2, int var3, int var4, byte[] var5, Encoding var6);

        @Specialization
        static int lastByteIndexOfString(AbstractTruffleString a, AbstractTruffleString b, int fromIndexB, int toIndexB, byte[] mask, Encoding expectedEncoding, @Cached ToIndexableNode toIndexableNodeA, @Cached ToIndexableNode toIndexableNodeB, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode, @Cached TStringInternalNodes.LastIndexOfStringRawNode indexOfStringNode) {
            int codeRangeA = getCodeRangeANode.execute(a);
            int codeRangeB = getCodeRangeBNode.execute(b);
            a.looseCheckEncoding(expectedEncoding, codeRangeA);
            b.looseCheckEncoding(expectedEncoding, codeRangeB);
            if (mask != null && TStringGuards.isUnsupportedEncoding(expectedEncoding) && !TStringGuards.isFixedWidth(codeRangeA)) {
                throw InternalErrors.unsupportedOperation();
            }
            if (b.isEmpty()) {
                return fromIndexB;
            }
            if (a.isEmpty()) {
                return -1;
            }
            int fromIndex = AbstractTruffleString.rawIndex(fromIndexB, expectedEncoding);
            int toIndex = AbstractTruffleString.rawIndex(toIndexB, expectedEncoding);
            a.boundsCheckRaw(toIndex, fromIndex);
            Object arrayA = toIndexableNodeA.execute(a, a.data());
            Object arrayB = toIndexableNodeB.execute(b, b.data());
            if (TStringGuards.indexOfCannotMatch(codeRangeA, b, codeRangeB, mask, fromIndex - toIndex)) {
                return -1;
            }
            return AbstractTruffleString.byteIndex(indexOfStringNode.execute(a, arrayA, codeRangeA, b, arrayB, codeRangeB, fromIndex, toIndex, mask, expectedEncoding), expectedEncoding);
        }

        public static LastByteIndexOfStringNode create() {
            return TruffleStringFactory.LastByteIndexOfStringNodeGen.create();
        }

        public static LastByteIndexOfStringNode getUncached() {
            return TruffleStringFactory.LastByteIndexOfStringNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class LastIndexOfStringNode
    extends Node {
        LastIndexOfStringNode() {
        }

        public abstract int execute(AbstractTruffleString var1, AbstractTruffleString var2, int var3, int var4, Encoding var5);

        @Specialization
        static int lastIndexOfString(AbstractTruffleString a, AbstractTruffleString b, int fromIndex, int toIndex, Encoding expectedEncoding, @Cached ToIndexableNode toIndexableNodeA, @Cached ToIndexableNode toIndexableNodeB, @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthANode, @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthBNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode, @Cached TStringInternalNodes.LastIndexOfStringNode indexOfStringNode) {
            int codeRangeA = getCodeRangeANode.execute(a);
            int codeRangeB = getCodeRangeBNode.execute(b);
            a.looseCheckEncoding(expectedEncoding, codeRangeA);
            b.looseCheckEncoding(expectedEncoding, codeRangeB);
            if (b.isEmpty()) {
                return fromIndex;
            }
            if (a.isEmpty()) {
                return -1;
            }
            a.boundsCheck(toIndex, fromIndex, getCodePointLengthANode);
            Object arrayA = toIndexableNodeA.execute(a, a.data());
            Object arrayB = toIndexableNodeB.execute(b, b.data());
            if (TStringGuards.indexOfCannotMatch(codeRangeA, b, codeRangeB, fromIndex - toIndex, getCodePointLengthBNode)) {
                return -1;
            }
            return indexOfStringNode.execute(a, arrayA, codeRangeA, b, arrayB, codeRangeB, fromIndex, toIndex, expectedEncoding);
        }

        public static LastIndexOfStringNode create() {
            return TruffleStringFactory.LastIndexOfStringNodeGen.create();
        }

        public static LastIndexOfStringNode getUncached() {
            return TruffleStringFactory.LastIndexOfStringNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class ByteIndexOfStringNode
    extends Node {
        ByteIndexOfStringNode() {
        }

        public final int execute(AbstractTruffleString a, AbstractTruffleString b, int fromByteIndex, int toByteIndex, Encoding expectedEncoding) {
            return this.execute(a, b, fromByteIndex, toByteIndex, null, expectedEncoding);
        }

        public final int execute(AbstractTruffleString a, WithMask b, int fromByteIndex, int toByteIndex, Encoding expectedEncoding) {
            return this.execute(a, b.string, fromByteIndex, toByteIndex, b.mask, expectedEncoding);
        }

        abstract int execute(AbstractTruffleString var1, AbstractTruffleString var2, int var3, int var4, byte[] var5, Encoding var6);

        @Specialization
        static int indexOfString(AbstractTruffleString a, AbstractTruffleString b, int fromByteIndex, int toByteIndex, byte[] mask, Encoding expectedEncoding, @Cached ToIndexableNode toIndexableNodeA, @Cached ToIndexableNode toIndexableNodeB, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode, @Cached TStringInternalNodes.IndexOfStringRawNode indexOfStringNode) {
            int codeRangeA = getCodeRangeANode.execute(a);
            int codeRangeB = getCodeRangeBNode.execute(b);
            a.looseCheckEncoding(expectedEncoding, codeRangeA);
            b.looseCheckEncoding(expectedEncoding, codeRangeB);
            if (mask != null && TStringGuards.isUnsupportedEncoding(expectedEncoding) && !TStringGuards.isFixedWidth(codeRangeA)) {
                throw InternalErrors.unsupportedOperation();
            }
            if (b.isEmpty()) {
                return fromByteIndex;
            }
            if (a.isEmpty()) {
                return -1;
            }
            int fromIndex = AbstractTruffleString.rawIndex(fromByteIndex, expectedEncoding);
            int toIndex = AbstractTruffleString.rawIndex(toByteIndex, expectedEncoding);
            a.boundsCheckRaw(fromIndex, toIndex);
            Object arrayA = toIndexableNodeA.execute(a, a.data());
            Object arrayB = toIndexableNodeB.execute(b, b.data());
            if (TStringGuards.indexOfCannotMatch(codeRangeA, b, codeRangeB, mask, toIndex - fromIndex)) {
                return -1;
            }
            return AbstractTruffleString.byteIndex(indexOfStringNode.execute(a, arrayA, codeRangeA, b, arrayB, codeRangeB, fromIndex, toIndex, mask, expectedEncoding), expectedEncoding);
        }

        public static ByteIndexOfStringNode create() {
            return TruffleStringFactory.ByteIndexOfStringNodeGen.create();
        }

        public static ByteIndexOfStringNode getUncached() {
            return TruffleStringFactory.ByteIndexOfStringNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class IndexOfStringNode
    extends Node {
        IndexOfStringNode() {
        }

        public abstract int execute(AbstractTruffleString var1, AbstractTruffleString var2, int var3, int var4, Encoding var5);

        @Specialization
        static int indexOfString(AbstractTruffleString a, AbstractTruffleString b, int fromIndex, int toIndex, Encoding expectedEncoding, @Cached ToIndexableNode toIndexableNodeA, @Cached ToIndexableNode toIndexableNodeB, @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthANode, @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthBNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode, @Cached TStringInternalNodes.IndexOfStringNode indexOfStringNode) {
            int codeRangeA = getCodeRangeANode.execute(a);
            int codeRangeB = getCodeRangeBNode.execute(b);
            a.looseCheckEncoding(expectedEncoding, codeRangeA);
            b.looseCheckEncoding(expectedEncoding, codeRangeB);
            if (b.isEmpty()) {
                return fromIndex;
            }
            if (a.isEmpty()) {
                return -1;
            }
            a.boundsCheck(fromIndex, toIndex, getCodePointLengthANode);
            Object arrayA = toIndexableNodeA.execute(a, a.data());
            Object arrayB = toIndexableNodeB.execute(b, b.data());
            if (TStringGuards.indexOfCannotMatch(codeRangeA, b, codeRangeB, toIndex - fromIndex, getCodePointLengthBNode)) {
                return -1;
            }
            return indexOfStringNode.execute(a, arrayA, codeRangeA, b, arrayB, codeRangeB, fromIndex, toIndex, expectedEncoding);
        }

        public static IndexOfStringNode create() {
            return TruffleStringFactory.IndexOfStringNodeGen.create();
        }

        public static IndexOfStringNode getUncached() {
            return TruffleStringFactory.IndexOfStringNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class LastByteIndexOfCodePointNode
    extends Node {
        LastByteIndexOfCodePointNode() {
        }

        public abstract int execute(AbstractTruffleString var1, int var2, int var3, int var4, Encoding var5);

        @Specialization
        static int doIndexOf(AbstractTruffleString a, int codepoint, int fromByteIndex, int toByteIndex, Encoding expectedEncoding, @Cached ToIndexableNode toIndexableNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode, @Cached TStringInternalNodes.LastIndexOfCodePointRawNode lastIndexOfNode) {
            a.checkEncoding(expectedEncoding);
            if (a.isEmpty()) {
                return -1;
            }
            int fromIndex = AbstractTruffleString.rawIndex(fromByteIndex, expectedEncoding);
            int toIndex = AbstractTruffleString.rawIndex(toByteIndex, expectedEncoding);
            a.boundsCheckRaw(toIndex, fromIndex);
            return AbstractTruffleString.byteIndex(lastIndexOfNode.execute(a, toIndexableNode.execute(a, a.data()), getCodeRangeNode.execute(a), expectedEncoding, codepoint, fromIndex, toIndex), expectedEncoding);
        }

        public static LastByteIndexOfCodePointNode create() {
            return TruffleStringFactory.LastByteIndexOfCodePointNodeGen.create();
        }

        public static LastByteIndexOfCodePointNode getUncached() {
            return TruffleStringFactory.LastByteIndexOfCodePointNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class LastIndexOfCodePointNode
    extends Node {
        LastIndexOfCodePointNode() {
        }

        public abstract int execute(AbstractTruffleString var1, int var2, int var3, int var4, Encoding var5);

        @Specialization
        static int doIndexOf(AbstractTruffleString a, int codepoint, int fromIndex, int toIndex, Encoding expectedEncoding, @Cached ToIndexableNode toIndexableNode, @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode, @Cached TStringInternalNodes.LastIndexOfCodePointNode lastIndexOfNode) {
            a.checkEncoding(expectedEncoding);
            if (a.isEmpty()) {
                return -1;
            }
            a.boundsCheck(toIndex, fromIndex, getCodePointLengthNode);
            Object arrayA = toIndexableNode.execute(a, a.data());
            return lastIndexOfNode.execute(a, arrayA, getCodeRangeNode.execute(a), expectedEncoding, codepoint, fromIndex, toIndex);
        }

        public static LastIndexOfCodePointNode create() {
            return TruffleStringFactory.LastIndexOfCodePointNodeGen.create();
        }

        public static LastIndexOfCodePointNode getUncached() {
            return TruffleStringFactory.LastIndexOfCodePointNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class ByteIndexOfCodePointNode
    extends Node {
        ByteIndexOfCodePointNode() {
        }

        public abstract int execute(AbstractTruffleString var1, int var2, int var3, int var4, Encoding var5);

        @Specialization
        static int doIndexOf(AbstractTruffleString a, int codepoint, int fromByteIndex, int toByteIndex, Encoding expectedEncoding, @Cached ToIndexableNode toIndexableNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode, @Cached TStringInternalNodes.IndexOfCodePointRawNode indexOfNode) {
            a.checkEncoding(expectedEncoding);
            if (a.isEmpty()) {
                return -1;
            }
            int fromIndex = AbstractTruffleString.rawIndex(fromByteIndex, expectedEncoding);
            int toIndex = AbstractTruffleString.rawIndex(toByteIndex, expectedEncoding);
            a.boundsCheckRaw(fromIndex, toIndex);
            return AbstractTruffleString.byteIndex(indexOfNode.execute(a, toIndexableNode.execute(a, a.data()), getCodeRangeNode.execute(a), expectedEncoding, codepoint, fromIndex, toIndex), expectedEncoding);
        }

        public static ByteIndexOfCodePointNode create() {
            return TruffleStringFactory.ByteIndexOfCodePointNodeGen.create();
        }

        public static ByteIndexOfCodePointNode getUncached() {
            return TruffleStringFactory.ByteIndexOfCodePointNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class IndexOfCodePointNode
    extends Node {
        IndexOfCodePointNode() {
        }

        public abstract int execute(AbstractTruffleString var1, int var2, int var3, int var4, Encoding var5);

        @Specialization
        static int doIndexOf(AbstractTruffleString a, int codepoint, int fromIndex, int toIndex, Encoding expectedEncoding, @Cached ToIndexableNode toIndexableNode, @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode, @Cached TStringInternalNodes.IndexOfCodePointNode indexOfNode) {
            a.checkEncoding(expectedEncoding);
            if (a.isEmpty()) {
                return -1;
            }
            a.boundsCheck(fromIndex, toIndex, getCodePointLengthNode);
            Object arrayA = toIndexableNode.execute(a, a.data());
            return indexOfNode.execute(a, arrayA, getCodeRangeNode.execute(a), expectedEncoding, codepoint, fromIndex, toIndex);
        }

        public static IndexOfCodePointNode create() {
            return TruffleStringFactory.IndexOfCodePointNodeGen.create();
        }

        public static IndexOfCodePointNode getUncached() {
            return TruffleStringFactory.IndexOfCodePointNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class IntIndexOfAnyIntUTF32Node
    extends Node {
        IntIndexOfAnyIntUTF32Node() {
        }

        public abstract int execute(AbstractTruffleString var1, int var2, int var3, int[] var4);

        @Specialization
        int indexOfRaw(AbstractTruffleString a, int fromIntIndex, int maxIntIndex, int[] values, @Cached ToIndexableNode toIndexableNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode, @Cached TStringOpsNodes.IndexOfAnyIntNode indexOfNode) {
            a.checkEncoding(Encoding.UTF_32);
            if (a.isEmpty()) {
                return -1;
            }
            a.boundsCheckRaw(fromIntIndex, maxIntIndex);
            if (fromIntIndex == maxIntIndex || IntIndexOfAnyIntUTF32Node.noneInCodeRange(this, getCodeRangeNode.execute(a), values)) {
                return -1;
            }
            return indexOfNode.execute(a, toIndexableNode.execute(a, a.data()), fromIntIndex, maxIntIndex, values);
        }

        private static boolean noneInCodeRange(Node location, int codeRange, int[] values) {
            for (int i = 0; i < values.length; ++i) {
                if (TSCodeRange.isInCodeRange(values[i], codeRange)) {
                    return false;
                }
                TStringConstants.truffleSafePointPoll(location, i + 1);
            }
            return true;
        }

        public static IntIndexOfAnyIntUTF32Node create() {
            return TruffleStringFactory.IntIndexOfAnyIntUTF32NodeGen.create();
        }

        public static IntIndexOfAnyIntUTF32Node getUncached() {
            return TruffleStringFactory.IntIndexOfAnyIntUTF32NodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class CharIndexOfAnyCharUTF16Node
    extends Node {
        CharIndexOfAnyCharUTF16Node() {
        }

        public abstract int execute(AbstractTruffleString var1, int var2, int var3, char[] var4);

        @Specialization
        int indexOfRaw(AbstractTruffleString a, int fromCharIndex, int maxCharIndex, char[] values, @Cached ToIndexableNode toIndexableNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode, @Cached TStringOpsNodes.IndexOfAnyCharNode indexOfNode) {
            a.checkEncoding(Encoding.UTF_16);
            if (a.isEmpty()) {
                return -1;
            }
            a.boundsCheckRaw(fromCharIndex, maxCharIndex);
            int codeRangeA = getCodeRangeNode.execute(a);
            if (fromCharIndex == maxCharIndex || TSCodeRange.isFixedWidth(codeRangeA) && CharIndexOfAnyCharUTF16Node.noneInCodeRange(this, codeRangeA, values)) {
                return -1;
            }
            return indexOfNode.execute(a, toIndexableNode.execute(a, a.data()), fromCharIndex, maxCharIndex, values);
        }

        private static boolean noneInCodeRange(Node location, int codeRange, char[] values) {
            for (int i = 0; i < values.length; ++i) {
                if (TSCodeRange.isInCodeRange(values[i], codeRange)) {
                    return false;
                }
                TStringConstants.truffleSafePointPoll(location, i + 1);
            }
            return true;
        }

        public static CharIndexOfAnyCharUTF16Node create() {
            return TruffleStringFactory.CharIndexOfAnyCharUTF16NodeGen.create();
        }

        public static CharIndexOfAnyCharUTF16Node getUncached() {
            return TruffleStringFactory.CharIndexOfAnyCharUTF16NodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class ByteIndexOfAnyByteNode
    extends Node {
        ByteIndexOfAnyByteNode() {
        }

        public abstract int execute(AbstractTruffleString var1, int var2, int var3, byte[] var4, Encoding var5);

        @Specialization
        int indexOfRaw(AbstractTruffleString a, int fromByteIndex, int maxByteIndex, byte[] values, Encoding expectedEncoding, @Cached ToIndexableNode toIndexableNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode) {
            if (TStringGuards.isUTF16Or32(expectedEncoding)) {
                throw InternalErrors.illegalArgument("UTF-16 and UTF-32 not supported!");
            }
            a.checkEncoding(expectedEncoding);
            if (a.isEmpty()) {
                return -1;
            }
            a.boundsCheckRaw(fromByteIndex, maxByteIndex);
            if (fromByteIndex == maxByteIndex || TSCodeRange.is7Bit(getCodeRangeNode.execute(a)) && ByteIndexOfAnyByteNode.noneIsAscii(this, values)) {
                return -1;
            }
            assert (TStringGuards.isStride0(a));
            Object arrayA = toIndexableNode.execute(a, a.data());
            return TStringOps.indexOfAnyByte(this, a, arrayA, fromByteIndex, maxByteIndex, values);
        }

        private static boolean noneIsAscii(Node location, byte[] values) {
            for (int i = 0; i < values.length; ++i) {
                if (Byte.toUnsignedInt(values[i]) <= 127) {
                    return false;
                }
                TStringConstants.truffleSafePointPoll(location, i + 1);
            }
            return true;
        }

        public static ByteIndexOfAnyByteNode create() {
            return TruffleStringFactory.ByteIndexOfAnyByteNodeGen.create();
        }

        public static ByteIndexOfAnyByteNode getUncached() {
            return TruffleStringFactory.ByteIndexOfAnyByteNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class CodePointAtByteIndexNode
    extends Node {
        CodePointAtByteIndexNode() {
        }

        public final int execute(AbstractTruffleString a, int i, Encoding expectedEncoding) {
            return this.execute(a, i, expectedEncoding, ErrorHandling.BEST_EFFORT);
        }

        public abstract int execute(AbstractTruffleString var1, int var2, Encoding var3, ErrorHandling var4);

        @Specialization
        static int readCodePoint(AbstractTruffleString a, int byteIndex, Encoding expectedEncoding, ErrorHandling errorHandling, @Cached ToIndexableNode toIndexableNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode, @Cached TStringInternalNodes.CodePointAtRawNode readCodePointNode) {
            CompilerAsserts.partialEvaluationConstant((Object)errorHandling);
            int i = AbstractTruffleString.rawIndex(byteIndex, expectedEncoding);
            a.checkEncoding(expectedEncoding);
            a.boundsCheckRaw(i);
            return readCodePointNode.execute(a, toIndexableNode.execute(a, a.data()), getCodeRangeNode.execute(a), expectedEncoding, i, errorHandling);
        }

        public static CodePointAtByteIndexNode create() {
            return TruffleStringFactory.CodePointAtByteIndexNodeGen.create();
        }

        public static CodePointAtByteIndexNode getUncached() {
            return TruffleStringFactory.CodePointAtByteIndexNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class CodePointAtIndexNode
    extends Node {
        CodePointAtIndexNode() {
        }

        public final int execute(AbstractTruffleString a, int i, Encoding expectedEncoding) {
            return this.execute(a, i, expectedEncoding, ErrorHandling.BEST_EFFORT);
        }

        public abstract int execute(AbstractTruffleString var1, int var2, Encoding var3, ErrorHandling var4);

        @Specialization
        static int readCodePoint(AbstractTruffleString a, int i, Encoding expectedEncoding, ErrorHandling errorHandling, @Cached ToIndexableNode toIndexableNode, @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode, @Cached TStringInternalNodes.CodePointAtNode readCodePointNode) {
            CompilerAsserts.partialEvaluationConstant((Object)errorHandling);
            a.checkEncoding(expectedEncoding);
            a.boundsCheck(i, getCodePointLengthNode);
            Object arrayA = toIndexableNode.execute(a, a.data());
            return readCodePointNode.execute(a, arrayA, getCodeRangeNode.execute(a), expectedEncoding, i, errorHandling);
        }

        public static CodePointAtIndexNode create() {
            return TruffleStringFactory.CodePointAtIndexNodeGen.create();
        }

        public static CodePointAtIndexNode getUncached() {
            return TruffleStringFactory.CodePointAtIndexNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class CodePointIndexToByteIndexNode
    extends Node {
        CodePointIndexToByteIndexNode() {
        }

        public abstract int execute(AbstractTruffleString var1, int var2, int var3, Encoding var4);

        @Specialization
        static int translate(AbstractTruffleString a, int byteOffset, int codepointIndex, Encoding expectedEncoding, @Cached ToIndexableNode toIndexableNode, @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode, @Cached TStringInternalNodes.CodePointIndexToRawNode codePointIndexToRawNode) {
            a.checkEncoding(expectedEncoding);
            a.boundsCheckRegion(0, codepointIndex, getCodePointLengthNode);
            int rawOffset = AbstractTruffleString.rawIndex(byteOffset, expectedEncoding);
            a.boundsCheckRawLength(rawOffset);
            if (codepointIndex == 0) {
                return 0;
            }
            Object arrayA = toIndexableNode.execute(a, a.data());
            int codeRangeA = getCodeRangeNode.execute(a);
            return codePointIndexToRawNode.execute(a, arrayA, codeRangeA, expectedEncoding, rawOffset, codepointIndex, true) << expectedEncoding.naturalStride;
        }

        public static CodePointIndexToByteIndexNode create() {
            return TruffleStringFactory.CodePointIndexToByteIndexNodeGen.create();
        }

        public static CodePointIndexToByteIndexNode getUncached() {
            return TruffleStringFactory.CodePointIndexToByteIndexNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class ByteIndexToCodePointIndexNode
    extends Node {
        ByteIndexToCodePointIndexNode() {
        }

        public abstract int execute(AbstractTruffleString var1, int var2, int var3, Encoding var4);

        @Specialization
        static int translate(AbstractTruffleString a, int byteOffset, int byteIndex, Encoding expectedEncoding, @Cached ToIndexableNode toIndexableNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode, @Cached TStringInternalNodes.RawIndexToCodePointIndexNode rawIndexToCodePointIndexNode) {
            a.checkEncoding(expectedEncoding);
            int rawOffset = AbstractTruffleString.rawIndex(byteOffset, expectedEncoding);
            int rawIndex = AbstractTruffleString.rawIndex(byteIndex, expectedEncoding);
            a.boundsCheckRegionRaw(rawOffset, rawIndex);
            if (byteIndex == 0) {
                return 0;
            }
            Object arrayA = toIndexableNode.execute(a, a.data());
            int codeRangeA = getCodeRangeNode.execute(a);
            return rawIndexToCodePointIndexNode.execute(a, arrayA, codeRangeA, expectedEncoding, a.offset() + byteOffset, rawIndex);
        }

        public static ByteIndexToCodePointIndexNode create() {
            return TruffleStringFactory.ByteIndexToCodePointIndexNodeGen.create();
        }

        public static ByteIndexToCodePointIndexNode getUncached() {
            return TruffleStringFactory.ByteIndexToCodePointIndexNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class ByteLengthOfCodePointNode
    extends Node {
        ByteLengthOfCodePointNode() {
        }

        public final int execute(AbstractTruffleString a, int byteIndex, Encoding expectedEncoding) {
            return this.execute(a, byteIndex, expectedEncoding, ErrorHandling.BEST_EFFORT);
        }

        public abstract int execute(AbstractTruffleString var1, int var2, Encoding var3, ErrorHandling var4);

        @Specialization
        static int translate(AbstractTruffleString a, int byteIndex, Encoding expectedEncoding, ErrorHandling errorHandling, @Cached ToIndexableNode toIndexableNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode, @Cached TStringInternalNodes.ByteLengthOfCodePointNode byteLengthOfCodePointNode) {
            CompilerAsserts.partialEvaluationConstant((Object)errorHandling);
            a.checkEncoding(expectedEncoding);
            int rawIndex = AbstractTruffleString.rawIndex(byteIndex, expectedEncoding);
            a.boundsCheckRaw(rawIndex);
            Object arrayA = toIndexableNode.execute(a, a.data());
            int codeRangeA = getCodeRangeNode.execute(a);
            return byteLengthOfCodePointNode.execute(a, arrayA, codeRangeA, expectedEncoding, rawIndex, errorHandling);
        }

        public static ByteLengthOfCodePointNode create() {
            return TruffleStringFactory.ByteLengthOfCodePointNodeGen.create();
        }

        public static ByteLengthOfCodePointNode getUncached() {
            return TruffleStringFactory.ByteLengthOfCodePointNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class ReadCharUTF16Node
    extends Node {
        ReadCharUTF16Node() {
        }

        public abstract char execute(AbstractTruffleString var1, int var2);

        @Specialization
        static char doRead(AbstractTruffleString a, int i, @Cached ToIndexableNode toIndexableNode, @Cached ConditionProfile utf16S0Profile) {
            a.checkEncoding(Encoding.UTF_16);
            a.boundsCheckRaw(i);
            Object arrayA = toIndexableNode.execute(a, a.data());
            if (utf16S0Profile.profile(TStringGuards.isStride0(a))) {
                return (char)TStringOps.readS0(a, arrayA, i);
            }
            assert (TStringGuards.isStride1(a));
            return TStringOps.readS1(a, arrayA, i);
        }

        public static ReadCharUTF16Node create() {
            return TruffleStringFactory.ReadCharUTF16NodeGen.create();
        }

        public static ReadCharUTF16Node getUncached() {
            return TruffleStringFactory.ReadCharUTF16NodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class ReadByteNode
    extends Node {
        ReadByteNode() {
        }

        public abstract int execute(AbstractTruffleString var1, int var2, Encoding var3);

        @Specialization
        static int doRead(AbstractTruffleString a, int i, Encoding expectedEncoding, @Cached ToIndexableNode toIndexableNode, @Cached TStringInternalNodes.ReadByteNode readByteNode) {
            a.checkEncoding(expectedEncoding);
            Object arrayA = toIndexableNode.execute(a, a.data());
            return readByteNode.execute(a, arrayA, i, expectedEncoding);
        }

        public static ReadByteNode create() {
            return TruffleStringFactory.ReadByteNodeGen.create();
        }

        public static ReadByteNode getUncached() {
            return TruffleStringFactory.ReadByteNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @GenerateUncached
    public static abstract class HashCodeNode
    extends Node {
        HashCodeNode() {
        }

        public abstract int execute(AbstractTruffleString var1, Encoding var2);

        @Specialization
        static int calculateHash(AbstractTruffleString a, Encoding expectedEncoding, @Cached ConditionProfile cacheMiss, @Cached ToIndexableNode toIndexableNode, @Cached TStringOpsNodes.CalculateHashCodeNode calculateHashCodeNode) {
            a.checkEncoding(expectedEncoding);
            int h = a.hashCode;
            if (cacheMiss.profile(h == 0)) {
                h = calculateHashCodeNode.execute(a, toIndexableNode.execute(a, a.data()));
                if (h == 0) {
                    --h;
                }
                a.hashCode = h;
            }
            return h;
        }

        public static HashCodeNode create() {
            return TruffleStringFactory.HashCodeNodeGen.create();
        }

        public static HashCodeNode getUncached() {
            return TruffleStringFactory.HashCodeNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class CodePointLengthNode
    extends Node {
        CodePointLengthNode() {
        }

        public abstract int execute(AbstractTruffleString var1, Encoding var2);

        @Specialization
        static int get(AbstractTruffleString a, Encoding expectedEncoding, @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode) {
            a.checkEncoding(expectedEncoding);
            return getCodePointLengthNode.execute(a);
        }

        public static CodePointLengthNode create() {
            return TruffleStringFactory.CodePointLengthNodeGen.create();
        }

        public static CodePointLengthNode getUncached() {
            return TruffleStringFactory.CodePointLengthNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @GenerateUncached
    public static abstract class IsValidNode
    extends Node {
        IsValidNode() {
        }

        public abstract boolean execute(AbstractTruffleString var1, Encoding var2);

        @Specialization
        static boolean isValid(AbstractTruffleString a, Encoding expectedEncoding, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode) {
            a.checkEncoding(expectedEncoding);
            int codeRange = getCodeRangeNode.execute(a);
            return !TStringGuards.isBrokenMultiByte(codeRange) && !TStringGuards.isBrokenFixedWidth(codeRange);
        }

        public static IsValidNode create() {
            return TruffleStringFactory.IsValidNodeGen.create();
        }

        public static IsValidNode getUncached() {
            return TruffleStringFactory.IsValidNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @GenerateUncached
    public static abstract class CodeRangeEqualsNode
    extends Node {
        CodeRangeEqualsNode() {
        }

        public abstract boolean execute(AbstractTruffleString var1, CodeRange var2);

        @Specialization
        static boolean codeRangeEquals(AbstractTruffleString a, CodeRange codeRange, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode) {
            return CodeRange.equals(getCodeRangeNode.execute(a), codeRange);
        }

        public static CodeRangeEqualsNode create() {
            return TruffleStringFactory.CodeRangeEqualsNodeGen.create();
        }

        public static CodeRangeEqualsNode getUncached() {
            return TruffleStringFactory.CodeRangeEqualsNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @GenerateUncached
    public static abstract class GetByteCodeRangeNode
    extends Node {
        GetByteCodeRangeNode() {
        }

        public abstract CodeRange execute(AbstractTruffleString var1, Encoding var2);

        @Specialization
        static CodeRange getCodeRange(AbstractTruffleString a, Encoding expectedEncoding, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode) {
            a.checkEncoding(expectedEncoding);
            return CodeRange.getByteCodeRange(getCodeRangeNode.execute(a), expectedEncoding);
        }

        public static GetByteCodeRangeNode create() {
            return TruffleStringFactory.GetByteCodeRangeNodeGen.create();
        }

        public static GetByteCodeRangeNode getUncached() {
            return TruffleStringFactory.GetByteCodeRangeNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @GenerateUncached
    public static abstract class GetCodeRangeNode
    extends Node {
        GetCodeRangeNode() {
        }

        public abstract CodeRange execute(AbstractTruffleString var1, Encoding var2);

        @Specialization
        static CodeRange getCodeRange(AbstractTruffleString a, Encoding expectedEncoding, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode) {
            a.checkEncoding(expectedEncoding);
            return CodeRange.get(getCodeRangeNode.execute(a));
        }

        public static GetCodeRangeNode create() {
            return TruffleStringFactory.GetCodeRangeNodeGen.create();
        }

        public static GetCodeRangeNode getUncached() {
            return TruffleStringFactory.GetCodeRangeNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @GenerateUncached
    public static abstract class MaterializeNode
    extends Node {
        MaterializeNode() {
        }

        public abstract void execute(AbstractTruffleString var1, Encoding var2);

        @Specialization
        static void doMaterialize(AbstractTruffleString a, Encoding expectedEncoding, @Cached ToIndexableNode toIndexableNode) {
            a.checkEncoding(expectedEncoding);
            toIndexableNode.execute(a, a.data());
            assert (a.isMaterialized(expectedEncoding));
        }

        public static MaterializeNode create() {
            return TruffleStringFactory.MaterializeNodeGen.create();
        }

        public static MaterializeNode getUncached() {
            return TruffleStringFactory.MaterializeNodeGen.getUncached();
        }
    }

    @ImportStatic(value={TStringGuards.class})
    static abstract class ToIndexableNode
    extends Node {
        ToIndexableNode() {
        }

        abstract Object execute(AbstractTruffleString var1, Object var2);

        static ToIndexableNode create() {
            return TruffleStringFactory.ToIndexableNodeFactory.ToIndexableImplNodeGen.create();
        }

        static ToIndexableNode getUncached() {
            return Uncached.INSTANCE;
        }

        @DenyReplace
        private static final class Uncached
        extends ToIndexableNode {
            private static final Uncached INSTANCE = new Uncached();

            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            Object execute(AbstractTruffleString a, Object data) {
                if (data instanceof byte[]) {
                    return data;
                }
                return Uncached.slowPath(a, data);
            }

            private static Object slowPath(AbstractTruffleString a, Object data) {
                if (data instanceof AbstractTruffleString.NativePointer) {
                    if (TStringGuards.isSupportedEncoding(a.encoding())) {
                        return ToIndexableImplNode.doNativeSupported(a, (AbstractTruffleString.NativePointer)data);
                    }
                    return ToIndexableImplNode.doNativeUnsupported(a, (AbstractTruffleString.NativePointer)data, ConditionProfile.getUncached());
                }
                if (data instanceof AbstractTruffleString.LazyConcat) {
                    return ToIndexableImplNode.doLazyConcatIntl(INSTANCE, a);
                }
                if (data instanceof AbstractTruffleString.LazyLong) {
                    return ToIndexableImplNode.doLazyLong(a, (AbstractTruffleString.LazyLong)data, ConditionProfile.getUncached());
                }
                throw new UnsupportedSpecializationException(INSTANCE, new Node[]{null, null}, a, data);
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.MEGAMORPHIC;
            }

            @Override
            public boolean isAdoptable() {
                return false;
            }
        }

        static abstract class ToIndexableImplNode
        extends ToIndexableNode {
            ToIndexableImplNode() {
            }

            @Specialization
            static byte[] doByteArray(AbstractTruffleString a, byte[] data) {
                return data;
            }

            @Specialization(guards={"isSupportedEncoding(a.encoding())"})
            static AbstractTruffleString.NativePointer doNativeSupported(AbstractTruffleString a, AbstractTruffleString.NativePointer data) {
                return data;
            }

            @Specialization(guards={"!isSupportedEncoding(a.encoding())"})
            static AbstractTruffleString.NativePointer doNativeUnsupported(AbstractTruffleString a, AbstractTruffleString.NativePointer data, @Cached ConditionProfile materializeProfile) {
                data.materializeByteArray(a, materializeProfile);
                return data;
            }

            @Specialization
            byte[] doLazyConcat(AbstractTruffleString a, AbstractTruffleString.LazyConcat data) {
                return ToIndexableImplNode.doLazyConcatIntl(this, a);
            }

            private static byte[] doLazyConcatIntl(ToIndexableNode location, AbstractTruffleString a) {
                a.setData(AbstractTruffleString.LazyConcat.flatten(location, (TruffleString)a));
                return (byte[])a.data();
            }

            @Specialization
            static byte[] doLazyLong(AbstractTruffleString a, AbstractTruffleString.LazyLong data, @Cached ConditionProfile materializeProfile) {
                if (materializeProfile.profile(data.bytes == null)) {
                    data.setBytes((TruffleString)a, NumberConversion.longToString(data.value, a.length()));
                }
                return data.bytes;
            }
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class AsManagedNode
    extends Node {
        AsManagedNode() {
        }

        public abstract TruffleString execute(AbstractTruffleString var1, Encoding var2);

        @Specialization(guards={"!a.isNative()"})
        static TruffleString managedImmutable(TruffleString a, Encoding expectedEncoding) {
            a.checkEncoding(expectedEncoding);
            assert (!(a.data() instanceof AbstractTruffleString.NativePointer));
            return a;
        }

        @Specialization(guards={"a.isNative() || a.isMutable()"})
        static TruffleString nativeOrMutable(AbstractTruffleString a, Encoding expectedEncoding, @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode, @Cached TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode fromBufferWithStringCompactionNode) {
            a.checkEncoding(expectedEncoding);
            Object data = a.data();
            assert (data instanceof byte[] || data instanceof AbstractTruffleString.NativePointer);
            return fromBufferWithStringCompactionNode.execute(data, a.offset(), a.length() << a.stride(), expectedEncoding, getCodePointLengthNode.execute(a), getCodeRangeNode.execute(a));
        }

        public static AsManagedNode create() {
            return TruffleStringFactory.AsManagedNodeGen.create();
        }

        public static AsManagedNode getUncached() {
            return TruffleStringFactory.AsManagedNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class AsTruffleStringNode
    extends Node {
        AsTruffleStringNode() {
        }

        public abstract TruffleString execute(AbstractTruffleString var1, Encoding var2);

        @Specialization
        static TruffleString immutable(TruffleString a, Encoding expectedEncoding) {
            a.checkEncoding(expectedEncoding);
            return a;
        }

        @Specialization
        static TruffleString fromMutableString(MutableTruffleString a, Encoding expectedEncoding, @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode, @Cached TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode fromBufferWithStringCompactionNode) {
            int codeRange = getCodeRangeNode.execute(a);
            a.looseCheckEncoding(expectedEncoding, codeRange);
            return fromBufferWithStringCompactionNode.execute(a.data(), a.offset(), a.length() << a.stride(), expectedEncoding, getCodePointLengthNode.execute(a), codeRange);
        }

        public static AsTruffleStringNode create() {
            return TruffleStringFactory.AsTruffleStringNodeGen.create();
        }

        public static AsTruffleStringNode getUncached() {
            return TruffleStringFactory.AsTruffleStringNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class, TStringAccessor.class})
    @GenerateUncached
    public static abstract class FromNativePointerNode
    extends Node {
        FromNativePointerNode() {
        }

        public abstract TruffleString execute(Object var1, int var2, int var3, Encoding var4, boolean var5);

        @Specialization
        TruffleString fromNativePointer(Object pointerObject, int byteOffset, int byteLength, Encoding enc, boolean copy, @Cached(value="createInteropLibrary()", uncached="getUncachedInteropLibrary()") Node interopLibrary, @Cached TStringInternalNodes.FromNativePointerNode fromNativePointerNode, @Cached TStringInternalNodes.FromBufferWithStringCompactionNode fromBufferWithStringCompactionNode) {
            AbstractTruffleString.NativePointer pointer = AbstractTruffleString.NativePointer.create(this, pointerObject, interopLibrary, byteOffset);
            if (copy) {
                return fromBufferWithStringCompactionNode.execute(pointer, byteOffset, byteLength, enc, true, true);
            }
            return fromNativePointerNode.execute(pointer, byteOffset, byteLength, enc, true);
        }

        public static FromNativePointerNode create() {
            return TruffleStringFactory.FromNativePointerNodeGen.create();
        }

        public static FromNativePointerNode getUncached() {
            return TruffleStringFactory.FromNativePointerNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class FromIntArrayUTF32Node
    extends Node {
        FromIntArrayUTF32Node() {
        }

        public final TruffleString execute(int[] value2) {
            return this.execute(value2, 0, value2.length);
        }

        public abstract TruffleString execute(int[] var1, int var2, int var3);

        @Specialization
        TruffleString doNonEmpty(int[] value2, int intOffset, int length, @Cached ConditionProfile utf32Compact0Profile, @Cached ConditionProfile utf32Compact1Profile, @Cached BranchProfile outOfMemoryProfile) {
            AbstractTruffleString.checkArrayRange(value2.length, intOffset, length);
            if (length == 0) {
                return Encoding.UTF_32.getEmpty();
            }
            if (length == 1 && value2[intOffset] <= 255) {
                return TStringConstants.getSingleByte(Encoding.UTF_32, value2[intOffset]);
            }
            int offsetV = intOffset << 2;
            if (length > 0x1FFFFFFD || offsetV < 0) {
                outOfMemoryProfile.enter();
                throw InternalErrors.outOfMemory();
            }
            int codeRange = TStringOps.calcStringAttributesUTF32I(this, value2, offsetV, length);
            int stride = Stride.fromCodeRangeUTF32(codeRange);
            byte[] array = new byte[length << stride];
            if (utf32Compact0Profile.profile(stride == 0)) {
                TStringOps.arraycopyWithStrideIB(this, value2, offsetV, array, 0, 0, length);
            } else if (utf32Compact1Profile.profile(stride == 1)) {
                TStringOps.arraycopyWithStrideIB(this, value2, offsetV, array, 0, 1, length);
            } else {
                TStringOps.arraycopyWithStrideIB(this, value2, offsetV, array, 0, 2, length);
            }
            return TruffleString.createFromArray(array, 0, length, stride, Encoding.UTF_32, length, codeRange);
        }

        public static FromIntArrayUTF32Node create() {
            return TruffleStringFactory.FromIntArrayUTF32NodeGen.create();
        }

        public static FromIntArrayUTF32Node getUncached() {
            return TruffleStringFactory.FromIntArrayUTF32NodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class FromJavaStringNode
    extends Node {
        FromJavaStringNode() {
        }

        public final TruffleString execute(String value2, Encoding encoding) {
            return this.execute(value2, 0, value2.length(), encoding, false);
        }

        public abstract TruffleString execute(String var1, int var2, int var3, Encoding var4, boolean var5);

        @Specialization
        static TruffleString doUTF16(String javaString, int charOffset, int length, Encoding encoding, boolean copy, @Cached TStringInternalNodes.FromJavaStringUTF16Node fromJavaStringUTF16Node, @Cached SwitchEncodingNode switchEncodingNode, @Cached ConditionProfile utf16Profile) {
            if (javaString.isEmpty()) {
                return encoding.getEmpty();
            }
            TruffleString utf16String = fromJavaStringUTF16Node.execute(javaString, charOffset, length, copy);
            if (utf16Profile.profile(encoding == Encoding.UTF_16)) {
                return utf16String;
            }
            return switchEncodingNode.execute(utf16String, encoding);
        }

        public static FromJavaStringNode create() {
            return TruffleStringFactory.FromJavaStringNodeGen.create();
        }

        public static FromJavaStringNode getUncached() {
            return TruffleStringFactory.FromJavaStringNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class FromCharArrayUTF16Node
    extends Node {
        FromCharArrayUTF16Node() {
        }

        public final TruffleString execute(char[] value2) {
            return this.execute(value2, 0, value2.length);
        }

        public abstract TruffleString execute(char[] var1, int var2, int var3);

        @Specialization
        TruffleString doNonEmpty(char[] value2, int charOffset, int charLength, @Cached ConditionProfile utf16CompactProfile, @Cached BranchProfile outOfMemoryProfile) {
            AbstractTruffleString.checkArrayRange(value2.length, charOffset, charLength);
            if (charLength == 0) {
                return Encoding.UTF_16.getEmpty();
            }
            if (charLength == 1 && value2[charOffset] <= '\u00ff') {
                return TStringConstants.getSingleByte(Encoding.UTF_16, value2[charOffset]);
            }
            int offsetV = charOffset << 1;
            if (value2.length > 0x3FFFFFFB || offsetV < 0) {
                outOfMemoryProfile.enter();
                throw InternalErrors.outOfMemory();
            }
            long attrs = TStringOps.calcStringAttributesUTF16C(this, value2, offsetV, charLength);
            int codePointLength = StringAttributes.getCodePointLength(attrs);
            int codeRange = StringAttributes.getCodeRange(attrs);
            int stride = Stride.fromCodeRangeUTF16(codeRange);
            byte[] array = new byte[charLength << stride];
            if (utf16CompactProfile.profile(stride == 0)) {
                TStringOps.arraycopyWithStrideCB(this, value2, offsetV, array, 0, 0, charLength);
            } else {
                TStringOps.arraycopyWithStrideCB(this, value2, offsetV, array, 0, 1, charLength);
            }
            return TruffleString.createFromArray(array, 0, charLength, stride, Encoding.UTF_16, codePointLength, codeRange);
        }

        public static FromCharArrayUTF16Node create() {
            return TruffleStringFactory.FromCharArrayUTF16NodeGen.create();
        }

        public static FromCharArrayUTF16Node getUncached() {
            return TruffleStringFactory.FromCharArrayUTF16NodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class FromByteArrayNode
    extends Node {
        FromByteArrayNode() {
        }

        public final TruffleString execute(byte[] value2, Encoding encoding) {
            return this.execute(value2, encoding, true);
        }

        public final TruffleString execute(byte[] value2, Encoding encoding, boolean copy) {
            return this.execute(value2, 0, value2.length, encoding, copy);
        }

        public abstract TruffleString execute(byte[] var1, int var2, int var3, Encoding var4, boolean var5);

        @Specialization
        static TruffleString fromByteArray(byte[] value2, int byteOffset, int byteLength, Encoding enc, boolean copy, @Cached TStringInternalNodes.FromBufferWithStringCompactionNode fromBufferWithStringCompactionNode) {
            AbstractTruffleString.checkArrayRange(value2, byteOffset, byteLength);
            return fromBufferWithStringCompactionNode.execute(value2, byteOffset, byteLength, enc, copy, true);
        }

        public static FromByteArrayNode create() {
            return TruffleStringFactory.FromByteArrayNodeGen.create();
        }

        public static FromByteArrayNode getUncached() {
            return TruffleStringFactory.FromByteArrayNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @ImportStatic(value={TStringGuards.class})
    @GenerateUncached
    public static abstract class FromLongNode
    extends Node {
        FromLongNode() {
        }

        public abstract TruffleString execute(long var1, Encoding var3, boolean var4);

        @Specialization(guards={"is7BitCompatible(enc)", "lazy"})
        static TruffleString doLazy(long value2, Encoding enc, boolean lazy) {
            CompilerAsserts.partialEvaluationConstant(lazy);
            return TruffleString.createLazyLong(value2, enc);
        }

        @Specialization(guards={"is7BitCompatible(enc)", "!lazy"})
        static TruffleString doEager(long value2, Encoding enc, boolean lazy) {
            CompilerAsserts.partialEvaluationConstant(lazy);
            int length = NumberConversion.stringLengthLong(value2);
            return TruffleString.createFromByteArray(NumberConversion.longToString(value2, length), length, 0, enc, length, TSCodeRange.get7Bit());
        }

        @Specialization(guards={"!is7BitCompatible(enc)"})
        static TruffleString unsupported(long value2, Encoding enc, boolean lazy) {
            CompilerAsserts.partialEvaluationConstant(lazy);
            throw InternalErrors.unsupportedOperation(FromLongNode.nonAsciiCompatibleMessage(enc));
        }

        @CompilerDirectives.TruffleBoundary
        private static String nonAsciiCompatibleMessage(Encoding enc) {
            return "Encoding " + enc + " is not ASCII-compatible";
        }

        public static FromLongNode create() {
            return TruffleStringFactory.FromLongNodeGen.create();
        }

        public static FromLongNode getUncached() {
            return TruffleStringFactory.FromLongNodeGen.getUncached();
        }
    }

    @GeneratePackagePrivate
    @GenerateUncached
    public static abstract class FromCodePointNode
    extends Node {
        FromCodePointNode() {
        }

        public final TruffleString execute(int codepoint, Encoding encoding) {
            return this.execute(codepoint, encoding, encoding == Encoding.UTF_16);
        }

        public abstract TruffleString execute(int var1, Encoding var2, boolean var3);

        /*
         * Enabled aggressive block sorting
         */
        @Specialization
        static TruffleString fromCodePoint(int c, Encoding enc, boolean allowUTF16Surrogates, @Cached ConditionProfile bytesProfile, @Cached ConditionProfile utf8Profile, @Cached ConditionProfile utf16Profile, @Cached ConditionProfile utf32Profile, @Cached ConditionProfile exoticProfile, @Cached ConditionProfile bmpProfile, @Cached BranchProfile invalidCodePoint) {
            int codeRange;
            int stride;
            int length;
            byte[] bytes;
            assert (!allowUTF16Surrogates || TStringGuards.isUTF16Or32(enc)) : "allowUTF16Surrogates is only supported on UTF-16 and UTF-32";
            CompilerAsserts.partialEvaluationConstant(allowUTF16Surrogates);
            if (TStringGuards.is7BitCompatible(enc) && Integer.compareUnsigned(c, 127) <= 0) {
                return TStringConstants.getSingleByteAscii(enc, c);
            }
            if (TStringGuards.is8BitCompatible(enc) && Integer.compareUnsigned(c, 255) <= 0) {
                if ($assertionsDisabled) return TStringConstants.getSingleByte(enc, c);
                if (TStringGuards.isSupportedEncoding(enc)) return TStringConstants.getSingleByte(enc, c);
                throw new AssertionError();
            }
            if (bytesProfile.profile(TStringGuards.isBytes(enc))) {
                if (Integer.compareUnsigned(c, 255) <= 0) return TStringConstants.getSingleByte(Encoding.BYTES, c);
                invalidCodePoint.enter();
                return null;
            }
            if (utf8Profile.profile(TStringGuards.isUTF8(enc))) {
                if (!Encodings.isValidUnicodeCodepoint(c)) {
                    invalidCodePoint.enter();
                    return null;
                }
                assert (c > 127);
                bytes = Encodings.utf8Encode(c);
                length = bytes.length;
                stride = 0;
                codeRange = TSCodeRange.getValidMultiByte();
                return TruffleString.createFromByteArray(bytes, length, stride, enc, 1, codeRange);
            }
            if (utf16Profile.profile(TStringGuards.isUTF16(enc))) {
                if (Integer.toUnsignedLong(c) > 0x10FFFFL) {
                    invalidCodePoint.enter();
                    return null;
                }
                assert (c > 255);
                bytes = new byte[c <= 65535 ? 2 : 4];
                stride = 1;
                if (!bmpProfile.profile(c <= 65535)) {
                    length = 2;
                    codeRange = TSCodeRange.getValidMultiByte();
                    Encodings.utf16EncodeSurrogatePair(c, bytes, 0);
                    return TruffleString.createFromByteArray(bytes, length, stride, enc, 1, codeRange);
                }
                length = 1;
                if (Encodings.isUTF16Surrogate(c)) {
                    if (!allowUTF16Surrogates) {
                        invalidCodePoint.enter();
                        return null;
                    }
                    codeRange = TSCodeRange.getBrokenMultiByte();
                } else {
                    codeRange = TSCodeRange.get16Bit();
                }
                TStringOps.writeToByteArray(bytes, 1, 0, c);
                return TruffleString.createFromByteArray(bytes, length, stride, enc, 1, codeRange);
            }
            if (utf32Profile.profile(TStringGuards.isUTF32(enc))) {
                if (Integer.toUnsignedLong(c) > 0x10FFFFL) {
                    invalidCodePoint.enter();
                    return null;
                }
                assert (c > 255);
                if (c <= 65535) {
                    if (Encodings.isUTF16Surrogate(c)) {
                        if (!allowUTF16Surrogates) {
                            invalidCodePoint.enter();
                            return null;
                        }
                        codeRange = TSCodeRange.getBrokenFixedWidth();
                    } else {
                        codeRange = TSCodeRange.get16Bit();
                    }
                } else {
                    codeRange = TSCodeRange.getValidFixedWidth();
                }
                boolean compact1 = TSCodeRange.is16Bit(codeRange);
                bytes = new byte[compact1 ? 2 : 4];
                length = 1;
                if (bmpProfile.profile(compact1)) {
                    stride = 1;
                    TStringOps.writeToByteArray(bytes, 1, 0, c);
                    return TruffleString.createFromByteArray(bytes, length, stride, enc, 1, codeRange);
                }
                stride = 2;
                TStringOps.writeToByteArray(bytes, 2, 0, c);
                return TruffleString.createFromByteArray(bytes, length, stride, enc, 1, codeRange);
            }
            if (exoticProfile.profile(!TStringGuards.isSupportedEncoding(enc))) {
                assert (!TStringGuards.isBytes(enc));
                JCodings.Encoding jCodingsEnc = JCodings.getInstance().get(enc);
                length = JCodings.getInstance().getCodePointLength(jCodingsEnc, c);
                stride = 0;
                int n = codeRange = JCodings.getInstance().isSingleByte(jCodingsEnc) ? TSCodeRange.getValidFixedWidth() : TSCodeRange.getValidMultiByte();
                if (length < 1) {
                    invalidCodePoint.enter();
                    return null;
                }
                bytes = new byte[length];
                int ret = JCodings.getInstance().writeCodePoint(jCodingsEnc, c, bytes, 0);
                if (ret == length && JCodings.getInstance().getCodePointLength(jCodingsEnc, bytes, 0, length) == ret) {
                    if (JCodings.getInstance().readCodePoint(jCodingsEnc, bytes, 0, length) == c) return TruffleString.createFromByteArray(bytes, length, stride, enc, 1, codeRange);
                }
                invalidCodePoint.enter();
                return null;
            }
            if (!($assertionsDisabled || TStringGuards.isAscii(enc) && Integer.compareUnsigned(c, 127) > 0)) {
                if (!TStringGuards.isLatin1(enc)) throw new AssertionError();
                if (Integer.compareUnsigned(c, 255) <= 0) {
                    throw new AssertionError();
                }
            }
            invalidCodePoint.enter();
            return null;
        }

        public static FromCodePointNode create() {
            return TruffleStringFactory.FromCodePointNodeGen.create();
        }

        public static FromCodePointNode getUncached() {
            return TruffleStringFactory.FromCodePointNodeGen.getUncached();
        }
    }

    public static enum ErrorHandling {
        BEST_EFFORT,
        RETURN_NEGATIVE;

    }

    public static final class WithMask {
        final AbstractTruffleString string;
        @CompilerDirectives.CompilationFinal(dimensions=1)
        final byte[] mask;

        WithMask(AbstractTruffleString string, byte[] mask) {
            this.string = string;
            this.mask = mask;
        }

        public static WithMask createUncached(AbstractTruffleString a, byte[] mask, Encoding expectedEncoding) {
            return CreateNode.getUncached().execute(a, mask, expectedEncoding);
        }

        public static WithMask createUTF16Uncached(AbstractTruffleString a, char[] mask) {
            return CreateUTF16Node.getUncached().execute(a, mask);
        }

        public static WithMask createUTF32Uncached(AbstractTruffleString a, int[] mask) {
            return CreateUTF32Node.getUncached().execute(a, mask);
        }

        private static void checkMaskLength(AbstractTruffleString string, int length) {
            if (length != string.length()) {
                throw InternalErrors.illegalArgument("mask length does not match string length!");
            }
        }

        @GeneratePackagePrivate
        @ImportStatic(value={TStringGuards.class})
        @GenerateUncached
        public static abstract class CreateUTF32Node
        extends Node {
            CreateUTF32Node() {
            }

            public abstract WithMask execute(AbstractTruffleString var1, int[] var2);

            @Specialization
            WithMask doCreate(AbstractTruffleString a, int[] mask) {
                a.checkEncoding(Encoding.UTF_32);
                WithMask.checkMaskLength(a, mask.length);
                byte[] maskBytes = new byte[a.length() << a.stride()];
                if (a.stride() == 0) {
                    TStringOps.arraycopyWithStrideIB(this, mask, 0, maskBytes, 0, 0, mask.length);
                } else if (a.stride() == 1) {
                    TStringOps.arraycopyWithStrideIB(this, mask, 0, maskBytes, 0, 1, mask.length);
                } else {
                    TStringOps.arraycopyWithStrideIB(this, mask, 0, maskBytes, 0, 2, mask.length);
                }
                return new WithMask(a, maskBytes);
            }

            public static CreateUTF32Node create() {
                return TruffleStringFactory.WithMaskFactory.CreateUTF32NodeGen.create();
            }

            public static CreateUTF32Node getUncached() {
                return TruffleStringFactory.WithMaskFactory.CreateUTF32NodeGen.getUncached();
            }
        }

        @GeneratePackagePrivate
        @ImportStatic(value={TStringGuards.class})
        @GenerateUncached
        public static abstract class CreateUTF16Node
        extends Node {
            CreateUTF16Node() {
            }

            public abstract WithMask execute(AbstractTruffleString var1, char[] var2);

            @Specialization
            WithMask doCreate(AbstractTruffleString a, char[] mask) {
                a.checkEncoding(Encoding.UTF_16);
                WithMask.checkMaskLength(a, mask.length);
                byte[] maskBytes = new byte[a.length() << a.stride()];
                if (a.stride() == 0) {
                    TStringOps.arraycopyWithStrideCB(this, mask, 0, maskBytes, 0, 0, mask.length);
                } else {
                    TStringOps.arraycopyWithStrideCB(this, mask, 0, maskBytes, 0, 1, mask.length);
                }
                return new WithMask(a, maskBytes);
            }

            public static CreateUTF16Node create() {
                return TruffleStringFactory.WithMaskFactory.CreateUTF16NodeGen.create();
            }

            public static CreateUTF16Node getUncached() {
                return TruffleStringFactory.WithMaskFactory.CreateUTF16NodeGen.getUncached();
            }
        }

        @GeneratePackagePrivate
        @ImportStatic(value={TStringGuards.class})
        @GenerateUncached
        public static abstract class CreateNode
        extends Node {
            CreateNode() {
            }

            public abstract WithMask execute(AbstractTruffleString var1, byte[] var2, Encoding var3);

            @Specialization
            WithMask doCreate(AbstractTruffleString a, byte[] mask, Encoding expectedEncoding) {
                if (expectedEncoding == Encoding.UTF_16 || expectedEncoding == Encoding.UTF_32) {
                    throw InternalErrors.illegalArgument("use a CreateUTF16Node for UTF-16, and CreateUTF32Node for UTF-32");
                }
                a.checkEncoding(expectedEncoding);
                WithMask.checkMaskLength(a, mask.length);
                assert (TStringGuards.isStride0(a));
                return new WithMask(a, Arrays.copyOf(mask, mask.length));
            }

            public static CreateNode create() {
                return TruffleStringFactory.WithMaskFactory.CreateNodeGen.create();
            }

            public static CreateNode getUncached() {
                return TruffleStringFactory.WithMaskFactory.CreateNodeGen.getUncached();
            }
        }
    }

    public static final class CodeRange
    extends Enum<CodeRange> {
        public static final /* enum */ CodeRange ASCII = new CodeRange();
        public static final /* enum */ CodeRange LATIN_1 = new CodeRange();
        public static final /* enum */ CodeRange BMP = new CodeRange();
        public static final /* enum */ CodeRange VALID = new CodeRange();
        public static final /* enum */ CodeRange BROKEN = new CodeRange();
        @CompilerDirectives.CompilationFinal(dimensions=1)
        private static final CodeRange[] CODE_RANGES;
        @CompilerDirectives.CompilationFinal(dimensions=1)
        private static final CodeRange[] BYTE_CODE_RANGES;
        private static final /* synthetic */ CodeRange[] $VALUES;

        public static CodeRange[] values() {
            return (CodeRange[])$VALUES.clone();
        }

        public static CodeRange valueOf(String name) {
            return Enum.valueOf(CodeRange.class, name);
        }

        public boolean isSubsetOf(CodeRange other) {
            return this.ordinal() <= other.ordinal();
        }

        public boolean isSupersetOf(CodeRange other) {
            return this.ordinal() >= other.ordinal();
        }

        static CodeRange get(int codeRange) {
            return CODE_RANGES[codeRange];
        }

        static CodeRange getByteCodeRange(int codeRange, Encoding encoding) {
            return codeRange == TSCodeRange.get7Bit() && TStringGuards.isUTF16Or32(encoding) ? VALID : BYTE_CODE_RANGES[codeRange];
        }

        static boolean equals(int codeRange, CodeRange codeRangeEnum) {
            return codeRange == codeRangeEnum.ordinal() || codeRangeEnum == VALID && TStringGuards.isValidMultiByte(codeRange) || codeRangeEnum == BROKEN && TStringGuards.isBrokenMultiByte(codeRange);
        }

        static {
            $VALUES = new CodeRange[]{ASCII, LATIN_1, BMP, VALID, BROKEN};
            CODE_RANGES = new CodeRange[]{ASCII, LATIN_1, BMP, VALID, BROKEN, VALID, BROKEN};
            BYTE_CODE_RANGES = new CodeRange[]{ASCII, VALID, VALID, VALID, BROKEN, VALID, BROKEN};
            assert (CodeRange.get(TSCodeRange.get7Bit()) == ASCII);
            assert (CodeRange.get(TSCodeRange.get8Bit()) == LATIN_1);
            assert (CodeRange.get(TSCodeRange.get16Bit()) == BMP);
            assert (CodeRange.get(TSCodeRange.getValidFixedWidth()) == VALID);
            assert (CodeRange.get(TSCodeRange.getBrokenFixedWidth()) == BROKEN);
            assert (CodeRange.get(TSCodeRange.getValidMultiByte()) == VALID);
            assert (CodeRange.get(TSCodeRange.getBrokenMultiByte()) == BROKEN);
            assert (CodeRange.equals(TSCodeRange.get7Bit(), ASCII));
            assert (CodeRange.equals(TSCodeRange.get8Bit(), LATIN_1));
            assert (CodeRange.equals(TSCodeRange.get16Bit(), BMP));
            assert (CodeRange.equals(TSCodeRange.getValidFixedWidth(), VALID));
            assert (CodeRange.equals(TSCodeRange.getBrokenFixedWidth(), BROKEN));
            assert (CodeRange.equals(TSCodeRange.getValidMultiByte(), VALID));
            assert (CodeRange.equals(TSCodeRange.getBrokenMultiByte(), BROKEN));
            assert (TSCodeRange.getUnknown() == CODE_RANGES.length);
        }
    }

    public static final class Encoding
    extends Enum<Encoding> {
        public static final /* enum */ Encoding UTF_32LE = new Encoding(TStringGuards.littleEndian() ? 0 : 97, "UTF_32LE", TStringGuards.littleEndian() ? 2 : 0);
        public static final /* enum */ Encoding UTF_32BE = new Encoding(TStringGuards.littleEndian() ? 97 : 0, "UTF_32BE", TStringGuards.littleEndian() ? 0 : 2);
        public static final /* enum */ Encoding UTF_16LE = new Encoding(TStringGuards.littleEndian() ? 1 : 98, "UTF_16LE", TStringGuards.littleEndian() ? 1 : 0);
        public static final /* enum */ Encoding UTF_16BE = new Encoding(TStringGuards.littleEndian() ? 98 : 1, "UTF_16BE", TStringGuards.littleEndian() ? 0 : 1);
        public static final /* enum */ Encoding ISO_8859_1 = new Encoding(2, "ISO_8859_1");
        public static final /* enum */ Encoding UTF_8 = new Encoding(3, "UTF_8");
        public static final /* enum */ Encoding US_ASCII = new Encoding(4, "US_ASCII");
        public static final /* enum */ Encoding BYTES = new Encoding(5, "BYTES");
        public static final /* enum */ Encoding Big5 = new Encoding(6, "Big5");
        public static final /* enum */ Encoding Big5_HKSCS = new Encoding(7, "Big5_HKSCS");
        public static final /* enum */ Encoding Big5_UAO = new Encoding(8, "Big5_UAO");
        public static final /* enum */ Encoding CP51932 = new Encoding(9, "CP51932");
        public static final /* enum */ Encoding CP850 = new Encoding(10, "CP850");
        public static final /* enum */ Encoding CP852 = new Encoding(11, "CP852");
        public static final /* enum */ Encoding CP855 = new Encoding(12, "CP855");
        public static final /* enum */ Encoding CP949 = new Encoding(13, "CP949");
        public static final /* enum */ Encoding CP950 = new Encoding(14, "CP950");
        public static final /* enum */ Encoding CP951 = new Encoding(15, "CP951");
        public static final /* enum */ Encoding EUC_JIS_2004 = new Encoding(16, "EUC_JIS_2004");
        public static final /* enum */ Encoding EUC_JP = new Encoding(17, "EUC_JP");
        public static final /* enum */ Encoding EUC_KR = new Encoding(18, "EUC_KR");
        public static final /* enum */ Encoding EUC_TW = new Encoding(19, "EUC_TW");
        public static final /* enum */ Encoding Emacs_Mule = new Encoding(20, "Emacs_Mule");
        public static final /* enum */ Encoding EucJP_ms = new Encoding(21, "EucJP_ms");
        public static final /* enum */ Encoding GB12345 = new Encoding(22, "GB12345");
        public static final /* enum */ Encoding GB18030 = new Encoding(23, "GB18030");
        public static final /* enum */ Encoding GB1988 = new Encoding(24, "GB1988");
        public static final /* enum */ Encoding GB2312 = new Encoding(25, "GB2312");
        public static final /* enum */ Encoding GBK = new Encoding(26, "GBK");
        public static final /* enum */ Encoding IBM437 = new Encoding(27, "IBM437");
        public static final /* enum */ Encoding IBM737 = new Encoding(28, "IBM737");
        public static final /* enum */ Encoding IBM775 = new Encoding(29, "IBM775");
        public static final /* enum */ Encoding IBM852 = new Encoding(30, "IBM852");
        public static final /* enum */ Encoding IBM855 = new Encoding(31, "IBM855");
        public static final /* enum */ Encoding IBM857 = new Encoding(32, "IBM857");
        public static final /* enum */ Encoding IBM860 = new Encoding(33, "IBM860");
        public static final /* enum */ Encoding IBM861 = new Encoding(34, "IBM861");
        public static final /* enum */ Encoding IBM862 = new Encoding(35, "IBM862");
        public static final /* enum */ Encoding IBM863 = new Encoding(36, "IBM863");
        public static final /* enum */ Encoding IBM864 = new Encoding(37, "IBM864");
        public static final /* enum */ Encoding IBM865 = new Encoding(38, "IBM865");
        public static final /* enum */ Encoding IBM866 = new Encoding(39, "IBM866");
        public static final /* enum */ Encoding IBM869 = new Encoding(40, "IBM869");
        public static final /* enum */ Encoding ISO_8859_10 = new Encoding(41, "ISO_8859_10");
        public static final /* enum */ Encoding ISO_8859_11 = new Encoding(42, "ISO_8859_11");
        public static final /* enum */ Encoding ISO_8859_13 = new Encoding(43, "ISO_8859_13");
        public static final /* enum */ Encoding ISO_8859_14 = new Encoding(44, "ISO_8859_14");
        public static final /* enum */ Encoding ISO_8859_15 = new Encoding(45, "ISO_8859_15");
        public static final /* enum */ Encoding ISO_8859_16 = new Encoding(46, "ISO_8859_16");
        public static final /* enum */ Encoding ISO_8859_2 = new Encoding(47, "ISO_8859_2");
        public static final /* enum */ Encoding ISO_8859_3 = new Encoding(48, "ISO_8859_3");
        public static final /* enum */ Encoding ISO_8859_4 = new Encoding(49, "ISO_8859_4");
        public static final /* enum */ Encoding ISO_8859_5 = new Encoding(50, "ISO_8859_5");
        public static final /* enum */ Encoding ISO_8859_6 = new Encoding(51, "ISO_8859_6");
        public static final /* enum */ Encoding ISO_8859_7 = new Encoding(52, "ISO_8859_7");
        public static final /* enum */ Encoding ISO_8859_8 = new Encoding(53, "ISO_8859_8");
        public static final /* enum */ Encoding ISO_8859_9 = new Encoding(54, "ISO_8859_9");
        public static final /* enum */ Encoding KOI8_R = new Encoding(55, "KOI8_R");
        public static final /* enum */ Encoding KOI8_U = new Encoding(56, "KOI8_U");
        public static final /* enum */ Encoding MacCentEuro = new Encoding(57, "MacCentEuro");
        public static final /* enum */ Encoding MacCroatian = new Encoding(58, "MacCroatian");
        public static final /* enum */ Encoding MacCyrillic = new Encoding(59, "MacCyrillic");
        public static final /* enum */ Encoding MacGreek = new Encoding(60, "MacGreek");
        public static final /* enum */ Encoding MacIceland = new Encoding(61, "MacIceland");
        public static final /* enum */ Encoding MacJapanese = new Encoding(62, "MacJapanese");
        public static final /* enum */ Encoding MacRoman = new Encoding(63, "MacRoman");
        public static final /* enum */ Encoding MacRomania = new Encoding(64, "MacRomania");
        public static final /* enum */ Encoding MacThai = new Encoding(65, "MacThai");
        public static final /* enum */ Encoding MacTurkish = new Encoding(66, "MacTurkish");
        public static final /* enum */ Encoding MacUkraine = new Encoding(67, "MacUkraine");
        public static final /* enum */ Encoding SJIS_DoCoMo = new Encoding(68, "SJIS_DoCoMo");
        public static final /* enum */ Encoding SJIS_KDDI = new Encoding(69, "SJIS_KDDI");
        public static final /* enum */ Encoding SJIS_SoftBank = new Encoding(70, "SJIS_SoftBank");
        public static final /* enum */ Encoding Shift_JIS = new Encoding(71, "Shift_JIS");
        public static final /* enum */ Encoding Stateless_ISO_2022_JP = new Encoding(72, "Stateless_ISO_2022_JP");
        public static final /* enum */ Encoding Stateless_ISO_2022_JP_KDDI = new Encoding(73, "Stateless_ISO_2022_JP_KDDI");
        public static final /* enum */ Encoding TIS_620 = new Encoding(74, "TIS_620");
        public static final /* enum */ Encoding UTF8_DoCoMo = new Encoding(75, "UTF8_DoCoMo");
        public static final /* enum */ Encoding UTF8_KDDI = new Encoding(76, "UTF8_KDDI");
        public static final /* enum */ Encoding UTF8_MAC = new Encoding(77, "UTF8_MAC");
        public static final /* enum */ Encoding UTF8_SoftBank = new Encoding(78, "UTF8_SoftBank");
        public static final /* enum */ Encoding Windows_1250 = new Encoding(79, "Windows_1250");
        public static final /* enum */ Encoding Windows_1251 = new Encoding(80, "Windows_1251");
        public static final /* enum */ Encoding Windows_1252 = new Encoding(81, "Windows_1252");
        public static final /* enum */ Encoding Windows_1253 = new Encoding(82, "Windows_1253");
        public static final /* enum */ Encoding Windows_1254 = new Encoding(83, "Windows_1254");
        public static final /* enum */ Encoding Windows_1255 = new Encoding(84, "Windows_1255");
        public static final /* enum */ Encoding Windows_1256 = new Encoding(85, "Windows_1256");
        public static final /* enum */ Encoding Windows_1257 = new Encoding(86, "Windows_1257");
        public static final /* enum */ Encoding Windows_1258 = new Encoding(87, "Windows_1258");
        public static final /* enum */ Encoding Windows_31J = new Encoding(88, "Windows_31J");
        public static final /* enum */ Encoding Windows_874 = new Encoding(89, "Windows_874");
        public static final /* enum */ Encoding CP50220 = new Encoding(90, "CP50220");
        public static final /* enum */ Encoding CP50221 = new Encoding(91, "CP50221");
        public static final /* enum */ Encoding IBM037 = new Encoding(92, "IBM037");
        public static final /* enum */ Encoding ISO_2022_JP = new Encoding(93, "ISO_2022_JP");
        public static final /* enum */ Encoding ISO_2022_JP_2 = new Encoding(94, "ISO_2022_JP_2");
        public static final /* enum */ Encoding ISO_2022_JP_KDDI = new Encoding(95, "ISO_2022_JP_KDDI");
        public static final /* enum */ Encoding UTF_7 = new Encoding(96, "UTF_7");
        public static final Encoding UTF_32;
        public static final Encoding UTF_16;
        final byte id;
        final String name;
        final JCodings.Encoding jCoding;
        final byte maxCompatibleCodeRange;
        final byte naturalStride;
        @CompilerDirectives.CompilationFinal(dimensions=1)
        private static final Encoding[] ENCODINGS_TABLE;
        @CompilerDirectives.CompilationFinal(dimensions=1)
        private static final JCodings.Encoding[] J_CODINGS_TABLE;
        @CompilerDirectives.CompilationFinal(dimensions=1)
        private static final byte[] MAX_COMPATIBLE_CODE_RANGE;
        @CompilerDirectives.CompilationFinal(dimensions=1)
        private static final TruffleString[] EMPTY_STRINGS;
        private static final EconomicMap<String, Encoding> J_CODINGS_NAME_MAP;
        private static final /* synthetic */ Encoding[] $VALUES;

        public static Encoding[] values() {
            return (Encoding[])$VALUES.clone();
        }

        public static Encoding valueOf(String name) {
            return Enum.valueOf(Encoding.class, name);
        }

        private Encoding(int id, String name) {
            this(id, name, 0);
        }

        private Encoding(int id, String name, int naturalStride) {
            assert (id <= 127);
            assert (Stride.isStride(naturalStride));
            this.id = (byte)id;
            this.name = name;
            JCodings.Encoding encoding = this.jCoding = JCodings.ENABLED ? JCodings.getInstance().get(name) : null;
            this.maxCompatibleCodeRange = this.is16BitCompatible() ? (byte)(TSCodeRange.get16Bit() + 1) : (this.is8BitCompatible() ? (byte)(TSCodeRange.get8Bit() + 1) : (this.is7BitCompatible() ? (byte)(TSCodeRange.get7Bit() + 1) : (byte)0));
            this.naturalStride = (byte)naturalStride;
        }

        private static TruffleString createEmpty(Encoding encoding) {
            if (encoding.is7BitCompatible() && !AbstractTruffleString.DEBUG_STRICT_ENCODING_CHECKS || encoding == US_ASCII) {
                return EMPTY_STRINGS[Encoding.US_ASCII.id];
            }
            TruffleString ret = TruffleString.createConstant(new byte[0], 0, 0, encoding, 0, TSCodeRange.getAsciiCodeRange(encoding), false);
            EMPTY_STRINGS[Encoding.US_ASCII.id].cacheInsert(ret);
            return ret;
        }

        public TruffleString getEmpty() {
            return EMPTY_STRINGS[this.id];
        }

        public static Encoding fromJCodingName(String name) {
            Encoding encoding = J_CODINGS_NAME_MAP.get(name, null);
            if (encoding == null) {
                throw InternalErrors.unknownEncoding(name);
            }
            return encoding;
        }

        static Encoding get(int encoding) {
            return ENCODINGS_TABLE[encoding];
        }

        static JCodings.Encoding getJCoding(int encoding) {
            assert (J_CODINGS_TABLE[encoding] == Encoding.get((int)encoding).jCoding);
            return J_CODINGS_TABLE[encoding];
        }

        static int getMaxCompatibleCodeRange(int encoding) {
            return MAX_COMPATIBLE_CODE_RANGE[encoding];
        }

        boolean is7BitCompatible() {
            return Encoding.is7BitCompatible(this.id);
        }

        boolean is8BitCompatible() {
            return Encoding.is8BitCompatible(this.id);
        }

        boolean is16BitCompatible() {
            return Encoding.is16BitCompatible(this.id);
        }

        boolean isSupported() {
            return Encoding.isSupported(this.id);
        }

        boolean isUnsupported() {
            return Encoding.isUnsupported(this.id);
        }

        static boolean is7BitCompatible(int encoding) {
            return encoding < 90;
        }

        static boolean is8BitCompatible(int encoding) {
            return encoding < 3;
        }

        static boolean is16BitCompatible(int encoding) {
            return encoding < 2;
        }

        static boolean isSupported(int encoding) {
            return encoding < 6;
        }

        static boolean isUnsupported(int encoding) {
            return encoding >= 6;
        }

        static boolean isFixedWidth(int encoding) {
            return JCodings.getInstance().isFixedWidth(Encoding.getJCoding(encoding));
        }

        static boolean isFixedWidth(Encoding encoding) {
            return JCodings.getInstance().isFixedWidth(encoding.jCoding);
        }

        static {
            $VALUES = new Encoding[]{UTF_32LE, UTF_32BE, UTF_16LE, UTF_16BE, ISO_8859_1, UTF_8, US_ASCII, BYTES, Big5, Big5_HKSCS, Big5_UAO, CP51932, CP850, CP852, CP855, CP949, CP950, CP951, EUC_JIS_2004, EUC_JP, EUC_KR, EUC_TW, Emacs_Mule, EucJP_ms, GB12345, GB18030, GB1988, GB2312, GBK, IBM437, IBM737, IBM775, IBM852, IBM855, IBM857, IBM860, IBM861, IBM862, IBM863, IBM864, IBM865, IBM866, IBM869, ISO_8859_10, ISO_8859_11, ISO_8859_13, ISO_8859_14, ISO_8859_15, ISO_8859_16, ISO_8859_2, ISO_8859_3, ISO_8859_4, ISO_8859_5, ISO_8859_6, ISO_8859_7, ISO_8859_8, ISO_8859_9, KOI8_R, KOI8_U, MacCentEuro, MacCroatian, MacCyrillic, MacGreek, MacIceland, MacJapanese, MacRoman, MacRomania, MacThai, MacTurkish, MacUkraine, SJIS_DoCoMo, SJIS_KDDI, SJIS_SoftBank, Shift_JIS, Stateless_ISO_2022_JP, Stateless_ISO_2022_JP_KDDI, TIS_620, UTF8_DoCoMo, UTF8_KDDI, UTF8_MAC, UTF8_SoftBank, Windows_1250, Windows_1251, Windows_1252, Windows_1253, Windows_1254, Windows_1255, Windows_1256, Windows_1257, Windows_1258, Windows_31J, Windows_874, CP50220, CP50221, IBM037, ISO_2022_JP, ISO_2022_JP_2, ISO_2022_JP_KDDI, UTF_7};
            UTF_32 = TStringGuards.littleEndian() ? UTF_32LE : UTF_32BE;
            UTF_16 = TStringGuards.littleEndian() ? UTF_16LE : UTF_16BE;
            ENCODINGS_TABLE = new Encoding[Encoding.values().length];
            J_CODINGS_TABLE = new JCodings.Encoding[Encoding.values().length];
            MAX_COMPATIBLE_CODE_RANGE = new byte[Encoding.values().length];
            EMPTY_STRINGS = new TruffleString[Encoding.values().length];
            J_CODINGS_NAME_MAP = EconomicMap.create(Encoding.values().length);
            for (Encoding e : Encoding.values()) {
                assert (ENCODINGS_TABLE[e.id] == null);
                Encoding.ENCODINGS_TABLE[e.id] = e;
                assert (J_CODINGS_TABLE[e.id] == null);
                Encoding.J_CODINGS_TABLE[e.id] = e.jCoding;
                Encoding.MAX_COMPATIBLE_CODE_RANGE[e.id] = e.maxCompatibleCodeRange;
                if (!JCodings.ENABLED) continue;
                J_CODINGS_NAME_MAP.put(JCodings.getInstance().name(e.jCoding), e);
            }
            assert (Encoding.UTF_16.naturalStride == 1);
            assert (Encoding.UTF_32.naturalStride == 2);
            Encoding.EMPTY_STRINGS[Encoding.US_ASCII.id] = TruffleString.createConstant(new byte[0], 0, 0, US_ASCII, 0, TSCodeRange.get7Bit());
            for (Encoding e : Encoding.values()) {
                if (e == US_ASCII) continue;
                assert (EMPTY_STRINGS[e.id] == null);
                if (!e.isSupported() && !JCodings.ENABLED) continue;
                Encoding.EMPTY_STRINGS[e.id] = Encoding.createEmpty(e);
            }
        }
    }
}

