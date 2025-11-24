
package com.oracle.truffle.regex.charset;

import com.oracle.truffle.regex.charset.Range;
import com.oracle.truffle.regex.charset.RangesBuffer;
import com.oracle.truffle.regex.charset.SortedListOfRanges;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.string.Encodings;
import java.util.Iterator;

public interface ImmutableSortedListOfRanges
extends SortedListOfRanges,
Iterable<Range> {
    public <T extends SortedListOfRanges> T createEmpty();

    public <T extends SortedListOfRanges> T create(RangesBuffer var1);

    public <T extends SortedListOfRanges> T createInverse(Encodings.Encoding var1);

    public RangesBuffer getBuffer1(CompilationBuffer var1);

    public RangesBuffer getBuffer2(CompilationBuffer var1);

    public RangesBuffer getBuffer3(CompilationBuffer var1);

    public RangesBuffer createTempBuffer();

    public boolean equalsBuffer(RangesBuffer var1);

    default public <T extends ImmutableSortedListOfRanges> T createIntersection(T o, CompilationBuffer compilationBuffer) {
        return this.createIntersection(o, this.getBuffer1(compilationBuffer));
    }

    default public <T extends ImmutableSortedListOfRanges> T createIntersection(T o, RangesBuffer tmp) {
        if (this.isEmpty() || o.isEmpty()) {
            return (T)((ImmutableSortedListOfRanges)this.createEmpty());
        }
        if (this.size() == 1) {
            return this.createIntersectionSingleRange(o);
        }
        if (o.size() == 1) {
            return (T)o.createIntersectionSingleRange((ImmutableSortedListOfRanges)this);
        }
        tmp.clear();
        for (int ia = 0; ia < this.size(); ++ia) {
            int firstIntersection;
            int search = o.binarySearch(this.getLo(ia));
            if (o.binarySearchExactMatch(search, this, ia)) {
                this.addRangeTo(tmp, ia);
                continue;
            }
            for (int ib = firstIntersection = o.binarySearchGetFirstIntersecting(search, this, ia); ib < o.size() && !o.rightOf(ib, this, ia); ++ib) {
                if (!1.$assertionsDisabled && !this.intersects(ia, o, ib)) {
                    throw new AssertionError();
                }
                tmp.appendRange(Math.max(this.getLo(ia), o.getLo(ib)), Math.min(this.getHi(ia), o.getHi(ib)));
            }
        }
        if (this.equalsBuffer(tmp)) {
            return (T)this;
        }
        if (o.equalsBuffer(tmp)) {
            return (T)o;
        }
        return (T)((ImmutableSortedListOfRanges)this.create(tmp));
    }

    public <T extends ImmutableSortedListOfRanges> T createIntersectionSingleRange(T var1);

    default public <T extends SortedListOfRanges> T subtract(T o, CompilationBuffer compilationBuffer) {
        RangesBuffer subtractionRanges = this.getBuffer1(compilationBuffer);
        boolean unchanged = true;
        for (int ia = 0; ia < this.size(); ++ia) {
            int search = o.binarySearch(this.getLo(ia));
            if (o.binarySearchExactMatch(search, this, ia)) {
                unchanged = false;
                continue;
            }
            int firstIntersection = o.binarySearchGetFirstIntersecting(search, this, ia);
            if (o.binarySearchNoIntersectingFound(firstIntersection)) {
                this.addRangeTo(subtractionRanges, ia);
                continue;
            }
            unchanged = false;
            int tmpLo = this.getLo(ia);
            int tmpHi = this.getHi(ia);
            boolean rest = true;
            for (int ib = firstIntersection; ib < o.size() && !o.rightOf(ib, tmpLo, tmpHi); ++ib) {
                if (!o.intersects(ib, tmpLo, tmpHi)) continue;
                if (o.contains(ib, tmpLo, tmpHi)) {
                    rest = false;
                    break;
                }
                if (o.containedBy(ib, tmpLo, tmpHi) && tmpLo != o.getLo(ib) && tmpHi != o.getHi(ib)) {
                    subtractionRanges.appendRange(tmpLo, o.getLo(ib) - 1);
                    tmpLo = o.getHi(ib) + 1;
                    continue;
                }
                if (tmpLo < o.getLo(ib)) {
                    tmpHi = o.getLo(ib) - 1;
                    continue;
                }
                tmpLo = o.getHi(ib) + 1;
            }
            if (!rest) continue;
            subtractionRanges.appendRange(tmpLo, tmpHi);
        }
        if (unchanged) {
            if (!1.$assertionsDisabled && !this.equalsBuffer(subtractionRanges)) {
                throw new AssertionError();
            }
            return (T)this;
        }
        return this.create(subtractionRanges);
    }

    default public <T extends ImmutableSortedListOfRanges> IntersectAndSubtractResult<T> intersectAndSubtract(T o, CompilationBuffer compilationBuffer) {
        RangesBuffer intersectionRanges;
        RangesBuffer subtractedB;
        RangesBuffer subtractedA;
        block28: {
            boolean noIntersection;
            int ib;
            int ia;
            block27: {
                if (this.matchesNothing() || o.matchesNothing() || this.getMin() > o.getMax() || o.getMin() > this.getMax()) {
                    return new IntersectAndSubtractResult<ImmutableSortedListOfRanges>(this, o, (ImmutableSortedListOfRanges)this.createEmpty());
                }
                if (this.matchesEverything(compilationBuffer.getEncoding())) {
                    return new IntersectAndSubtractResult<ImmutableSortedListOfRanges>((ImmutableSortedListOfRanges)o.createInverse(compilationBuffer.getEncoding()), (ImmutableSortedListOfRanges)this.createEmpty(), o);
                }
                if (o.matchesEverything(compilationBuffer.getEncoding())) {
                    return new IntersectAndSubtractResult<ImmutableSortedListOfRanges>((ImmutableSortedListOfRanges)this.createEmpty(), (ImmutableSortedListOfRanges)this.createInverse(compilationBuffer.getEncoding()), this);
                }
                if (this.equals(o)) {
                    return new IntersectAndSubtractResult<ImmutableSortedListOfRanges>((ImmutableSortedListOfRanges)this.createEmpty(), (ImmutableSortedListOfRanges)this.createEmpty(), this);
                }
                subtractedA = this.getBuffer1(compilationBuffer);
                subtractedB = this.getBuffer2(compilationBuffer);
                intersectionRanges = this.getBuffer3(compilationBuffer);
                ia = 0;
                ib = 0;
                noIntersection = false;
                while (true) {
                    if (this.leftOf(ia, o, ib)) {
                        if (++ia < this.size()) continue;
                        noIntersection = true;
                        break block27;
                    }
                    if (!o.leftOf(ib, this, ia)) break block27;
                    if (++ib >= o.size()) break;
                }
                noIntersection = true;
            }
            if (noIntersection) {
                return new IntersectAndSubtractResult<ImmutableSortedListOfRanges>(this, o, (ImmutableSortedListOfRanges)this.createEmpty());
            }
            this.appendRangesTo(subtractedA, 0, ia);
            o.appendRangesTo(subtractedB, 0, ib);
            int raLo = this.getLo(ia);
            int raHi = this.getHi(ia);
            int rbLo = o.getLo(ib);
            int rbHi = o.getHi(ib);
            if (!1.$assertionsDisabled && !SortedListOfRanges.intersects(raLo, raHi, rbLo, rbHi)) {
                throw new AssertionError();
            }
            ++ia;
            ++ib;
            while (true) {
                if (SortedListOfRanges.leftOf(raLo, raHi, rbLo, rbHi)) {
                    subtractedA.appendRange(raLo, raHi);
                    if (ia < this.size()) {
                        raLo = this.getLo(ia);
                        raHi = this.getHi(ia);
                        ++ia;
                        continue;
                    }
                    subtractedB.appendRange(rbLo, rbHi);
                    o.appendRangesTo(subtractedB, ib, o.size());
                    break block28;
                }
                if (SortedListOfRanges.leftOf(rbLo, rbHi, raLo, raHi)) {
                    subtractedB.appendRange(rbLo, rbHi);
                    if (ib < o.size()) {
                        rbLo = o.getLo(ib);
                        rbHi = o.getHi(ib);
                        ++ib;
                        continue;
                    }
                    subtractedA.appendRange(raLo, raHi);
                    this.appendRangesTo(subtractedA, ia, this.size());
                    break block28;
                }
                if (!1.$assertionsDisabled && !SortedListOfRanges.intersects(raLo, raHi, rbLo, rbHi)) {
                    throw new AssertionError();
                }
                int intersectionLo = raLo;
                if (raLo < rbLo) {
                    intersectionLo = rbLo;
                    subtractedA.appendRange(raLo, intersectionLo - 1);
                } else if (raLo != rbLo) {
                    subtractedB.appendRange(rbLo, intersectionLo - 1);
                }
                int intersectionHi = raHi;
                if (raHi > rbHi) {
                    intersectionHi = rbHi;
                    intersectionRanges.appendRange(intersectionLo, intersectionHi);
                    raLo = intersectionHi + 1;
                    if (ib < o.size()) {
                        rbLo = o.getLo(ib);
                        rbHi = o.getHi(ib);
                        ++ib;
                        continue;
                    }
                    subtractedA.appendRange(raLo, raHi);
                    this.appendRangesTo(subtractedA, ia, this.size());
                    break block28;
                }
                if (raHi < rbHi) {
                    intersectionRanges.appendRange(intersectionLo, intersectionHi);
                    rbLo = intersectionHi + 1;
                    if (ia < this.size()) {
                        raLo = this.getLo(ia);
                        raHi = this.getHi(ia);
                        ++ia;
                        continue;
                    }
                    subtractedB.appendRange(rbLo, rbHi);
                    o.appendRangesTo(subtractedB, ib, o.size());
                    break block28;
                }
                if (!1.$assertionsDisabled && raHi != rbHi) {
                    throw new AssertionError();
                }
                intersectionRanges.appendRange(intersectionLo, intersectionHi);
                if (ia >= this.size()) {
                    o.appendRangesTo(subtractedB, ib, o.size());
                    break block28;
                }
                raLo = this.getLo(ia);
                raHi = this.getHi(ia);
                ++ia;
                if (ib >= o.size()) break;
                rbLo = o.getLo(ib);
                rbHi = o.getHi(ib);
                ++ib;
            }
            subtractedA.appendRange(raLo, raHi);
            this.appendRangesTo(subtractedA, ia, this.size());
        }
        if (subtractedA.isEmpty()) {
            if (!1.$assertionsDisabled && !this.equalsBuffer(intersectionRanges)) {
                throw new AssertionError();
            }
            return new IntersectAndSubtractResult<ImmutableSortedListOfRanges>((ImmutableSortedListOfRanges)this.createEmpty(), (ImmutableSortedListOfRanges)this.create(subtractedB), this);
        }
        if (subtractedB.isEmpty()) {
            if (!1.$assertionsDisabled && !o.equalsBuffer(intersectionRanges)) {
                throw new AssertionError();
            }
            return new IntersectAndSubtractResult<ImmutableSortedListOfRanges>((ImmutableSortedListOfRanges)this.create(subtractedA), (ImmutableSortedListOfRanges)this.createEmpty(), o);
        }
        return new IntersectAndSubtractResult<ImmutableSortedListOfRanges>((ImmutableSortedListOfRanges)this.create(subtractedA), (ImmutableSortedListOfRanges)this.create(subtractedB), (ImmutableSortedListOfRanges)this.create(intersectionRanges));
    }

    default public <T extends ImmutableSortedListOfRanges> T union(T o) {
        return this.union(o, this.createTempBuffer());
    }

    default public <T extends ImmutableSortedListOfRanges> T union(T o, CompilationBuffer compilationBuffer) {
        return this.union(o, this.getBuffer1(compilationBuffer));
    }

    default public <T extends ImmutableSortedListOfRanges> T union(T o, RangesBuffer target) {
        if (this.matchesNothing() || o.size() == 1 && o.getMin() <= this.getMin() && o.getMax() >= this.getMax()) {
            return o;
        }
        if (o.matchesNothing() || this.size() == 1 && this.getMin() <= o.getMin() && this.getMax() >= o.getMax()) {
            return (T)this;
        }
        SortedListOfRanges.union(this, o, target);
        if (this.equalsBuffer(target)) {
            return (T)this;
        }
        if (o.equalsBuffer(target)) {
            return o;
        }
        return (T)((ImmutableSortedListOfRanges)this.create(target));
    }

    @Override
    default public Iterator<Range> iterator() {
        return new ImmutableSortedListOfRangesIterator(this);
    }

    static {
        if (1.$assertionsDisabled) {
            // empty if block
        }
    }

    public static final class ImmutableSortedListOfRangesIterator
    implements Iterator<Range> {
        private final ImmutableSortedListOfRanges ranges;
        private int i = 0;

        private ImmutableSortedListOfRangesIterator(ImmutableSortedListOfRanges ranges) {
            this.ranges = ranges;
        }

        @Override
        public boolean hasNext() {
            return this.i < this.ranges.size();
        }

        @Override
        public Range next() {
            Range ret = new Range(this.ranges.getLo(this.i), this.ranges.getHi(this.i));
            ++this.i;
            return ret;
        }
    }

    public static final class IntersectAndSubtractResult<T extends ImmutableSortedListOfRanges> {
        public final T subtractedA;
        public final T subtractedB;
        public final T intersection;

        public IntersectAndSubtractResult(T subtractedA, T subtractedB, T intersected) {
            this.subtractedA = subtractedA;
            this.subtractedB = subtractedB;
            this.intersection = intersected;
        }
    }
}

