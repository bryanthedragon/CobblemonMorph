
package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.AbstractTruffleString;
import com.oracle.truffle.api.strings.JCodings;
import com.oracle.truffle.api.strings.TStringInternalNodes;
import com.oracle.truffle.api.strings.TruffleString;

final class JCodingsDisabled
implements JCodings {
    public static final String MESSAGE = "TruffleStrings: JCodings is disabled!";

    JCodingsDisabled() {
    }

    @Override
    public JCodings.Encoding get(String encodingName) {
        throw CompilerDirectives.shouldNotReachHere(MESSAGE);
    }

    @Override
    public JCodings.Encoding get(TruffleString.Encoding encoding) {
        throw CompilerDirectives.shouldNotReachHere(MESSAGE);
    }

    @Override
    public String name(JCodings.Encoding jCoding) {
        throw CompilerDirectives.shouldNotReachHere(MESSAGE);
    }

    @Override
    public int minLength(JCodings.Encoding enc) {
        throw CompilerDirectives.shouldNotReachHere(MESSAGE);
    }

    @Override
    public int maxLength(JCodings.Encoding e) {
        throw CompilerDirectives.shouldNotReachHere(MESSAGE);
    }

    @Override
    public boolean isFixedWidth(JCodings.Encoding enc) {
        throw CompilerDirectives.shouldNotReachHere(MESSAGE);
    }

    @Override
    public boolean isSingleByte(JCodings.Encoding enc) {
        throw CompilerDirectives.shouldNotReachHere(MESSAGE);
    }

    @Override
    public int getCodePointLength(JCodings.Encoding jCoding, int codepoint) {
        throw CompilerDirectives.shouldNotReachHere(MESSAGE);
    }

    @Override
    public int getPreviousCodePointIndex(JCodings.Encoding jCoding, byte[] array, int arrayBegin, int index, int arrayEnd) {
        throw CompilerDirectives.shouldNotReachHere(MESSAGE);
    }

    @Override
    public int getCodePointLength(JCodings.Encoding jCoding, byte[] array, int index, int arrayLength) {
        throw CompilerDirectives.shouldNotReachHere(MESSAGE);
    }

    @Override
    public int readCodePoint(JCodings.Encoding jCoding, byte[] array, int index, int arrayEnd) {
        throw CompilerDirectives.shouldNotReachHere(MESSAGE);
    }

    @Override
    public int writeCodePoint(JCodings.Encoding jCoding, int codepoint, byte[] array, int index) {
        throw CompilerDirectives.shouldNotReachHere(MESSAGE);
    }

    @Override
    public int codePointIndexToRaw(Node location, AbstractTruffleString a, byte[] arrayA, int extraOffsetRaw, int index, boolean isLength, JCodings.Encoding jCoding) {
        throw CompilerDirectives.shouldNotReachHere(MESSAGE);
    }

    @Override
    public int decode(AbstractTruffleString a, byte[] arrayA, int rawIndex, JCodings.Encoding jCoding, TruffleString.ErrorHandling errorHandling) {
        throw CompilerDirectives.shouldNotReachHere(MESSAGE);
    }

    @Override
    public long calcStringAttributes(Node location, Object array, int offset, int length, TruffleString.Encoding encoding, ConditionProfile validCharacterProfile, ConditionProfile fixedWidthProfile) {
        throw CompilerDirectives.shouldNotReachHere(MESSAGE);
    }

    @Override
    public TruffleString transcode(Node location, AbstractTruffleString a, Object arrayA, int codePointLengthA, TruffleString.Encoding targetEncoding, BranchProfile outOfMemoryProfile, ConditionProfile nativeProfile, TStringInternalNodes.FromBufferWithStringCompactionNode fromBufferWithStringCompactionNode) {
        throw CompilerDirectives.shouldNotReachHere(MESSAGE);
    }
}

