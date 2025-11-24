
package com.oracle.truffle.regex.charset;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.charset.CharMatchers;
import com.oracle.truffle.regex.charset.ImmutableSortedListOfIntRanges;
import com.oracle.truffle.regex.charset.Range;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.buffer.IntRangesBuffer;
import com.oracle.truffle.regex.tregex.buffer.ObjectArrayBuffer;
import com.oracle.truffle.regex.util.BitSets;
import java.util.Arrays;

public final class CompressedCodePointSet {
    private static final long[][] NO_BITSETS = new long[0][];
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final int[] ranges;
    @CompilerDirectives.CompilationFinal(dimensions=2)
    private final long[][] bitSets;

    private CompressedCodePointSet(int[] ranges, long[][] bitSets) {
        this.ranges = ranges;
        this.bitSets = bitSets;
        assert (this.bitSets == null || this.bitSets.length == this.ranges.length / 2);
    }

    public int[] getRanges() {
        return this.ranges;
    }

    public boolean hasBitSets() {
        return this.bitSets != null;
    }

    public long[][] getBitSets() {
        return this.bitSets;
    }

    public int getLo(int i) {
        return this.ranges[i * 2];
    }

    public int getHi(int i) {
        return this.ranges[i * 2 + 1];
    }

    public int size() {
        return this.ranges.length / 2;
    }

    public boolean hasBitSet(int i) {
        return this.hasBitSets() && this.bitSets[i] != null;
    }

    public long[] getBitSet(int i) {
        return this.bitSets[i];
    }

    public static CompressedCodePointSet create(ImmutableSortedListOfIntRanges cps, CompilationBuffer compilationBuffer) {
        int size = cps.size();
        if (size < 3) {
            return new CompressedCodePointSet(cps.toArray(), null);
        }
        if (BitSets.highByte(cps.getMin()) == BitSets.highByte(cps.getMax())) {
            return CompressedCodePointSet.convertToBitSet(cps);
        }
        assert (size >= 1);
        IntRangesBuffer ranges = compilationBuffer.getIntRangesBuffer1();
        ObjectArrayBuffer<long[]> bitSets = compilationBuffer.getObjectBuffer1();
        int lowestOCP = 0;
        int curPlane = BitSets.highByte(cps.getHi(0));
        for (int i = 0; i < size; ++i) {
            if (BitSets.highByte(cps.getLo(i)) != curPlane) {
                CompressedCodePointSet.processRanges(cps, ranges, bitSets, lowestOCP, i, i, curPlane);
                curPlane = BitSets.highByte(cps.getLo(i));
                lowestOCP = i;
            }
            if (BitSets.highByte(cps.getHi(i)) == curPlane) continue;
            if (lowestOCP != i) {
                CompressedCodePointSet.processRanges(cps, ranges, bitSets, lowestOCP, i, i + 1, curPlane);
            }
            curPlane = BitSets.highByte(cps.getHi(i));
            lowestOCP = i;
        }
        CompressedCodePointSet.processRanges(cps, ranges, bitSets, lowestOCP, size, size, curPlane);
        if (bitSets.isEmpty()) {
            return new CompressedCodePointSet(cps.toArray(), null);
        }
        assert (ranges.rangesAreSortedAndDisjoint());
        return new CompressedCodePointSet(ranges.toArray(), (long[][])bitSets.toArray((ST[])NO_BITSETS));
    }

    private static CompressedCodePointSet convertToBitSet(ImmutableSortedListOfIntRanges cps) {
        int highByte = BitSets.highByte(cps.getMin());
        long[] bs = BitSets.createBitSetArray(cps.getMax() + 1);
        for (int i = 0; i < cps.size(); ++i) {
            assert (BitSets.highByte(cps.getLo(i)) == highByte && BitSets.highByte(cps.getHi(i)) == highByte);
            BitSets.setRange(bs, BitSets.lowByte(cps.getLo(i)), BitSets.lowByte(cps.getHi(i)));
        }
        return new CompressedCodePointSet(new int[]{cps.getMin(), cps.getMax()}, new long[][]{bs});
    }

    private static void processRanges(ImmutableSortedListOfIntRanges cps, IntRangesBuffer ranges, ObjectArrayBuffer<long[]> bitSets, int iMin, int iMax, int iMaxBS, int curPlane) {
        if (iMaxBS - iMin >= 3) {
            CompressedCodePointSet.addBitSet(cps, bitSets, ranges, iMin, iMaxBS, curPlane);
        } else {
            CompressedCodePointSet.addRanges(cps, bitSets, ranges, iMin, iMax);
        }
    }

    private static void addRanges(ImmutableSortedListOfIntRanges cps, ObjectArrayBuffer<long[]> bitSets, IntRangesBuffer ranges, int iMinArg, int iMax) {
        if (iMinArg == iMax) {
            return;
        }
        int iMin = iMinArg;
        if (!bitSets.isEmpty() && bitSets.peek() != null && BitSets.highByte(cps.getLo(iMin)) == BitSets.highByte(ranges.getMax())) {
            ranges.appendRangeAllowAdjacent(ranges.getMax() + 1, cps.getHi(iMin));
            ++iMin;
        }
        cps.appendRangesTo(ranges, iMin, iMax);
        for (int i = 0; i < iMax - iMinArg; ++i) {
            bitSets.add(null);
        }
    }

    private static void addBitSet(ImmutableSortedListOfIntRanges cps, ObjectArrayBuffer<long[]> bitSets, IntRangesBuffer ranges, int iMin, int iMax, int curPlane) {
        int bsRangeLo;
        int bsRangeHi;
        long[] bs;
        assert (iMax - iMin > 1);
        int curPlaneLo = curPlane << 8;
        if (CompressedCodePointSet.rangeCrossesPlanes(cps, iMin)) {
            if (bitSets.isEmpty() || bitSets.peek() == null || BitSets.highByte(cps.getLo(iMin)) != BitSets.highByte(ranges.getMax())) {
                ranges.appendRangeAllowAdjacent(cps.getLo(iMin), curPlaneLo - 1);
                bitSets.add(null);
            } else if (BitSets.highByte(cps.getHi(iMin)) - BitSets.highByte(cps.getLo(iMin)) > 1) {
                ranges.appendRangeAllowAdjacent(ranges.getMax() + 1, curPlaneLo - 1);
                bitSets.add(null);
            }
        }
        int iMinBS = iMin;
        int iMaxBS = iMax;
        if (CompressedCodePointSet.rangeCrossesPlanes(cps, iMax - 1)) {
            bs = BitSets.createBitSetArray(256);
            --iMaxBS;
            BitSets.setRange(bs, BitSets.lowByte(cps.getLo(iMax - 1)), 255);
            bsRangeHi = curPlaneLo | 0xFF;
        } else {
            bs = BitSets.createBitSetArray(BitSets.lowByte(cps.getHi(iMax - 1)) + 1);
            bsRangeHi = cps.getHi(iMax - 1);
        }
        if (CompressedCodePointSet.rangeCrossesPlanes(cps, iMin)) {
            assert (BitSets.highByte(cps.getHi(iMin)) == curPlane);
            ++iMinBS;
            BitSets.setRange(bs, 0, BitSets.lowByte(cps.getHi(iMin)));
            bsRangeLo = curPlaneLo;
        } else {
            bsRangeLo = cps.getLo(iMin);
        }
        for (int i = iMinBS; i < iMaxBS; ++i) {
            assert (BitSets.highByte(cps.getLo(i)) == curPlane && BitSets.highByte(cps.getHi(i)) == curPlane);
            BitSets.setRange(bs, BitSets.lowByte(cps.getLo(i)), BitSets.lowByte(cps.getHi(i)));
        }
        bitSets.add(bs);
        ranges.appendRangeAllowAdjacent(bsRangeLo, bsRangeHi);
    }

    private static boolean rangeCrossesPlanes(ImmutableSortedListOfIntRanges ranges, int i) {
        return BitSets.highByte(ranges.getLo(i)) != BitSets.highByte(ranges.getHi(i));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof CompressedCodePointSet) {
            return Arrays.equals(this.ranges, ((CompressedCodePointSet)obj).ranges) && Arrays.deepEquals((Object[])this.bitSets, (Object[])((CompressedCodePointSet)obj).bitSets);
        }
        return false;
    }

    public int hashCode() {
        return Arrays.hashCode(this.ranges) * 31 + Arrays.deepHashCode((Object[])this.bitSets);
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        if (this.bitSets == null) {
            return "[" + CharMatchers.rangesToString(this.ranges) + "]";
        }
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < this.ranges.length; i += 2) {
            if (this.bitSets[i / 2] == null) {
                sb.append(Range.toString(this.ranges[i], this.ranges[i + 1]));
                continue;
            }
            sb.append("[range: ").append(Range.toString(this.ranges[i], this.ranges[i + 1])).append(", bs: ").append(BitSets.toString(this.bitSets[i / 2])).append("]");
        }
        return sb.append("]").toString();
    }
}

