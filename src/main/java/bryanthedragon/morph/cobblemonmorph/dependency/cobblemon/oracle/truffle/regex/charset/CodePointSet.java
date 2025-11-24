
package com.oracle.truffle.regex.charset;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.charset.Constants;
import com.oracle.truffle.regex.charset.ImmutableSortedListOfIntRanges;
import com.oracle.truffle.regex.charset.ImmutableSortedListOfRanges;
import com.oracle.truffle.regex.charset.RangesBuffer;
import com.oracle.truffle.regex.charset.SortedListOfRanges;
import com.oracle.truffle.regex.tregex.buffer.IntRangesBuffer;
import com.oracle.truffle.regex.tregex.string.Encodings;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import com.oracle.truffle.regex.util.EmptyArrays;
import java.util.Arrays;

public final class CodePointSet
extends ImmutableSortedListOfIntRanges
implements Comparable<CodePointSet>,
JsonConvertible {
    private static final CodePointSet CONSTANT_EMPTY;
    private static final CodePointSet[] CONSTANT_ASCII;
    private static final CodePointSet[] CONSTANT_CASE_FOLD_ASCII;

    private CodePointSet(int[] ranges) {
        super(ranges);
        assert (ranges.length == 0 || ranges[0] >= 0 && ranges[ranges.length - 1] >= 0);
    }

    public int[] getRanges() {
        return this.ranges;
    }

    public static CodePointSet getEmpty() {
        return CONSTANT_EMPTY;
    }

    public static CodePointSet createNoDedup(int ... ranges) {
        return new CodePointSet(ranges);
    }

    public static CodePointSet create(int single) {
        if (single < 128) {
            return CONSTANT_ASCII[single];
        }
        return new CodePointSet(new int[]{single, single});
    }

    public static CodePointSet create(int ... ranges) {
        CodePointSet constant = CodePointSet.checkConstants(ranges, ranges.length);
        if (constant == null) {
            return new CodePointSet(ranges);
        }
        return constant;
    }

    public static CodePointSet create(IntRangesBuffer buf) {
        CodePointSet constant = CodePointSet.checkConstants(buf.getBuffer(), buf.length());
        if (constant == null) {
            return new CodePointSet(buf.toArray());
        }
        return constant;
    }

    private static CodePointSet checkConstants(int[] ranges, int length) {
        if (length == 0) {
            return CONSTANT_EMPTY;
        }
        if (length == 2 && ranges[0] == ranges[1] && ranges[0] < 128) {
            return CONSTANT_ASCII[ranges[0]];
        }
        if (length == 4 && ranges[0] == ranges[1] && ranges[0] >= 65 && ranges[0] <= 90 && ranges[2] == ranges[3] && ranges[2] == (ranges[0] | 0x20)) {
            return CONSTANT_CASE_FOLD_ASCII[ranges[0] - 65];
        }
        for (CodePointSet predefCC : Constants.CONSTANT_CODE_POINT_SETS) {
            if (predefCC.ranges.length != length || !CodePointSet.rangesEqual(predefCC.ranges, ranges, length)) continue;
            return predefCC;
        }
        return null;
    }

    public CodePointSet createEmpty() {
        return CodePointSet.getEmpty();
    }

    public CodePointSet create(RangesBuffer buffer) {
        assert (buffer instanceof IntRangesBuffer);
        return CodePointSet.create((IntRangesBuffer)buffer);
    }

    @Override
    public boolean equalsBuffer(RangesBuffer buffer) {
        assert (buffer instanceof IntRangesBuffer);
        IntRangesBuffer buf = (IntRangesBuffer)buffer;
        return this.ranges.length == buf.length() && CodePointSet.rangesEqual(this.ranges, buf.getBuffer(), this.ranges.length);
    }

    public CodePointSet createInverse(Encodings.Encoding encoding) {
        return CodePointSet.createInverse(this, encoding);
    }

    public static CodePointSet createInverse(SortedListOfRanges src, Encodings.Encoding encoding) {
        if (src.matchesNothing()) {
            return encoding.getFullSet();
        }
        return new CodePointSet(CodePointSet.createInverseArray(src, encoding));
    }

    @Override
    public <T extends ImmutableSortedListOfRanges> T createIntersectionSingleRange(T o) {
        int iLo;
        assert (this.size() == 1 && !o.isEmpty());
        if (this.getMin() <= o.getMin() && this.getMax() >= o.getMax()) {
            return o;
        }
        int iHi = o.size() - 1;
        for (iLo = 0; iLo < o.size() && o.getHi(iLo) < this.getMin(); ++iLo) {
        }
        while (iHi >= 0 && o.getLo(iHi) > this.getMax()) {
            --iHi;
        }
        if (iHi < iLo) {
            return (T)this.createEmpty();
        }
        int[] intersection = Arrays.copyOfRange(((CodePointSet)o).ranges, iLo * 2, (iHi + 1) * 2);
        intersection[0] = Math.max(intersection[0], this.getMin());
        intersection[intersection.length - 1] = Math.min(intersection[intersection.length - 1], this.getMax());
        return (T)CodePointSet.create(intersection);
    }

    @Override
    public int compareTo(CodePointSet o) {
        if (this == o) {
            return 0;
        }
        int cmp = this.size() - o.size();
        if (cmp != 0) {
            return cmp;
        }
        for (int i = 0; i < this.size(); ++i) {
            cmp = this.getLo(i) - o.getLo(i);
            if (cmp == 0) continue;
            return cmp;
        }
        return cmp;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof CodePointSet) {
            return Arrays.equals(this.ranges, ((CodePointSet)obj).ranges);
        }
        if (obj instanceof SortedListOfRanges) {
            return this.equalsListOfRanges((SortedListOfRanges)obj);
        }
        return false;
    }

    public int hashCode() {
        return Arrays.hashCode(this.ranges);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public JsonValue toJson() {
        return Json.array(this.ranges);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public String toString() {
        return this.defaultToString();
    }

    @CompilerDirectives.TruffleBoundary
    public String dumpRaw() {
        StringBuilder sb = new StringBuilder(this.size() * 20);
        for (int i = 0; i < this.size(); ++i) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(String.format("0x%06x, 0x%06x", this.getLo(i), this.getHi(i)));
        }
        return sb.toString();
    }

    @Override
    public int[] toArray() {
        return this.getRanges();
    }

    public byte[] inverseToByteArray(Encodings.Encoding encoding) {
        byte[] array = new byte[this.inverseValueCount(encoding)];
        int index = 0;
        int lastHi = -1;
        for (int i = 0; i < this.size(); ++i) {
            for (int j = lastHi + 1; j < this.getLo(i); ++j) {
                assert (j <= 255);
                array[index++] = (byte)j;
            }
            lastHi = this.getHi(i);
        }
        for (int j = lastHi + 1; j <= encoding.getMaxValue(); ++j) {
            assert (j <= 255);
            array[index++] = (byte)j;
        }
        return array;
    }

    public char[] inverseToCharArray(Encodings.Encoding encoding) {
        char[] array = new char[this.inverseValueCount(encoding)];
        int index = 0;
        int lastHi = -1;
        for (int i = 0; i < this.size(); ++i) {
            for (int j = lastHi + 1; j < this.getLo(i); ++j) {
                assert (j <= 65535);
                array[index++] = (char)j;
            }
            lastHi = this.getHi(i);
        }
        for (int j = lastHi + 1; j <= encoding.getMaxValue(); ++j) {
            assert (j <= 65535);
            array[index++] = (char)j;
        }
        return array;
    }

    public int[] inverseToIntArray(Encodings.Encoding encoding) {
        int[] array = new int[this.inverseValueCount(encoding)];
        int index = 0;
        int lastHi = -1;
        for (int i = 0; i < this.size(); ++i) {
            int j = lastHi + 1;
            while (j < this.getLo(i)) {
                array[index++] = j++;
            }
            lastHi = this.getHi(i);
        }
        int j = lastHi + 1;
        while (j <= encoding.getMaxValue()) {
            array[index++] = j++;
        }
        return array;
    }

    static {
        int i;
        CONSTANT_EMPTY = new CodePointSet(EmptyArrays.INT);
        CONSTANT_ASCII = new CodePointSet[128];
        CONSTANT_CASE_FOLD_ASCII = new CodePointSet[26];
        CodePointSet.CONSTANT_ASCII[0] = new CodePointSet(new int[]{0, 0});
        for (i = 1; i < 128; ++i) {
            CodePointSet.CONSTANT_ASCII[i] = new CodePointSet(new int[]{i, i});
        }
        for (i = 65; i <= 90; ++i) {
            CodePointSet.CONSTANT_CASE_FOLD_ASCII[i - 65] = new CodePointSet(new int[]{i, i, Character.toLowerCase(i), Character.toLowerCase(i)});
        }
    }
}

