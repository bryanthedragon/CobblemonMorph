package com.oracle.truffle.regex.charset;

import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.string.Encodings;
import java.util.Iterator;

public interface ImmutableSortedListOfRanges extends SortedListOfRanges, Iterable<Range> {
   <T extends SortedListOfRanges> T createEmpty();

   <T extends SortedListOfRanges> T create(RangesBuffer buffer);

   <T extends SortedListOfRanges> T createInverse(Encodings.Encoding encoding);

   RangesBuffer getBuffer1(CompilationBuffer compilationBuffer);

   RangesBuffer getBuffer2(CompilationBuffer compilationBuffer);

   RangesBuffer getBuffer3(CompilationBuffer compilationBuffer);

   RangesBuffer createTempBuffer();

   boolean equalsBuffer(RangesBuffer buffer);

   default <T extends ImmutableSortedListOfRanges> T createIntersection(T o, CompilationBuffer compilationBuffer) {
      return this.createIntersection(o, this.getBuffer1(compilationBuffer));
   }

   default <T extends ImmutableSortedListOfRanges> T createIntersection(T o, RangesBuffer tmp) {
      if (!this.isEmpty() && !o.isEmpty()) {
         if (this.size() == 1) {
            return this.createIntersectionSingleRange(o);
         } else if (o.size() == 1) {
            return o.createIntersectionSingleRange((T)this);
         } else {
            tmp.clear();

            for (int ia = 0; ia < this.size(); ia++) {
               int search = o.binarySearch(this.getLo(ia));
               if (o.binarySearchExactMatch(search, this, ia)) {
                  this.addRangeTo(tmp, ia);
               } else {
                  int firstIntersection = o.binarySearchGetFirstIntersecting(search, this, ia);

                  for (int ib = firstIntersection; ib < o.size() && !o.rightOf(ib, this, ia); ib++) {
                     if (!<unrepresentable>.$assertionsDisabled && !this.intersects(ia, o, ib)) {
                        throw new AssertionError();
                     }

                     tmp.appendRange(Math.max(this.getLo(ia), o.getLo(ib)), Math.min(this.getHi(ia), o.getHi(ib)));
                  }
               }
            }

            if (this.equalsBuffer(tmp)) {
               return (T)this;
            } else {
               return o.equalsBuffer(tmp) ? o : this.create(tmp);
            }
         }
      } else {
         return this.createEmpty();
      }
   }

   <T extends ImmutableSortedListOfRanges> T createIntersectionSingleRange(T o);

   default <T extends SortedListOfRanges> T subtract(T o, CompilationBuffer compilationBuffer) {
      RangesBuffer subtractionRanges = this.getBuffer1(compilationBuffer);
      boolean unchanged = true;

      for (int ia = 0; ia < this.size(); ia++) {
         int search = o.binarySearch(this.getLo(ia));
         if (o.binarySearchExactMatch(search, this, ia)) {
            unchanged = false;
         } else {
            int firstIntersection = o.binarySearchGetFirstIntersecting(search, this, ia);
            if (o.binarySearchNoIntersectingFound(firstIntersection)) {
               this.addRangeTo(subtractionRanges, ia);
            } else {
               unchanged = false;
               int tmpLo = this.getLo(ia);
               int tmpHi = this.getHi(ia);
               boolean rest = true;

               for (int ib = firstIntersection; ib < o.size() && !o.rightOf(ib, tmpLo, tmpHi); ib++) {
                  if (o.intersects(ib, tmpLo, tmpHi)) {
                     if (o.contains(ib, tmpLo, tmpHi)) {
                        rest = false;
                        break;
                     }

                     if (o.containedBy(ib, tmpLo, tmpHi) && tmpLo != o.getLo(ib) && tmpHi != o.getHi(ib)) {
                        subtractionRanges.appendRange(tmpLo, o.getLo(ib) - 1);
                        tmpLo = o.getHi(ib) + 1;
                     } else if (tmpLo < o.getLo(ib)) {
                        tmpHi = o.getLo(ib) - 1;
                     } else {
                        tmpLo = o.getHi(ib) + 1;
                     }
                  }
               }

               if (rest) {
                  subtractionRanges.appendRange(tmpLo, tmpHi);
               }
            }
         }
      }

      if (!unchanged) {
         return this.create(subtractionRanges);
      } else if (!<unrepresentable>.$assertionsDisabled && !this.equalsBuffer(subtractionRanges)) {
         throw new AssertionError();
      } else {
         return (T)this;
      }
   }

   default <T extends ImmutableSortedListOfRanges> ImmutableSortedListOfRanges.IntersectAndSubtractResult<T> intersectAndSubtract(
      T o, CompilationBuffer compilationBuffer
   ) {
      if (this.matchesNothing() || o.matchesNothing() || this.getMin() > o.getMax() || o.getMin() > this.getMax()) {
         return new ImmutableSortedListOfRanges.IntersectAndSubtractResult<>((T)this, o, this.createEmpty());
      } else if (this.matchesEverything(compilationBuffer.getEncoding())) {
         return new ImmutableSortedListOfRanges.IntersectAndSubtractResult<>(o.createInverse(compilationBuffer.getEncoding()), this.createEmpty(), o);
      } else if (o.matchesEverything(compilationBuffer.getEncoding())) {
         return new ImmutableSortedListOfRanges.IntersectAndSubtractResult<>(this.createEmpty(), this.createInverse(compilationBuffer.getEncoding()), (T)this);
      } else if (this.equals(o)) {
         return new ImmutableSortedListOfRanges.IntersectAndSubtractResult<>(this.createEmpty(), this.createEmpty(), (T)this);
      } else {
         RangesBuffer subtractedA = this.getBuffer1(compilationBuffer);
         RangesBuffer subtractedB = this.getBuffer2(compilationBuffer);
         RangesBuffer intersectionRanges = this.getBuffer3(compilationBuffer);
         int ia = 0;
         int ib = 0;
         boolean noIntersection = false;

         while (true) {
            if (this.leftOf(ia, o, ib)) {
               if (++ia >= this.size()) {
                  noIntersection = true;
                  break;
               }
            } else {
               if (!o.leftOf(ib, this, ia)) {
                  break;
               }

               if (++ib >= o.size()) {
                  noIntersection = true;
                  break;
               }
            }
         }

         if (noIntersection) {
            return new ImmutableSortedListOfRanges.IntersectAndSubtractResult<>((T)this, o, this.createEmpty());
         } else {
            this.appendRangesTo(subtractedA, 0, ia);
            o.appendRangesTo(subtractedB, 0, ib);
            int raLo = this.getLo(ia);
            int raHi = this.getHi(ia);
            int rbLo = o.getLo(ib);
            int rbHi = o.getHi(ib);
            if (!<unrepresentable>.$assertionsDisabled && !SortedListOfRanges.intersects(raLo, raHi, rbLo, rbHi)) {
               throw new AssertionError();
            } else {
               ia++;
               ib++;

               while (true) {
                  if (SortedListOfRanges.leftOf(raLo, raHi, rbLo, rbHi)) {
                     subtractedA.appendRange(raLo, raHi);
                     if (ia >= this.size()) {
                        subtractedB.appendRange(rbLo, rbHi);
                        o.appendRangesTo(subtractedB, ib, o.size());
                        break;
                     }

                     raLo = this.getLo(ia);
                     raHi = this.getHi(ia);
                     ia++;
                  } else if (SortedListOfRanges.leftOf(rbLo, rbHi, raLo, raHi)) {
                     subtractedB.appendRange(rbLo, rbHi);
                     if (ib >= o.size()) {
                        subtractedA.appendRange(raLo, raHi);
                        this.appendRangesTo(subtractedA, ia, this.size());
                        break;
                     }

                     rbLo = o.getLo(ib);
                     rbHi = o.getHi(ib);
                     ib++;
                  } else {
                     if (!<unrepresentable>.$assertionsDisabled && !SortedListOfRanges.intersects(raLo, raHi, rbLo, rbHi)) {
                        throw new AssertionError();
                     }

                     int intersectionLo = raLo;
                     if (raLo < rbLo) {
                        intersectionLo = rbLo;
                        subtractedA.appendRange(raLo, rbLo - 1);
                     } else if (raLo != rbLo) {
                        subtractedB.appendRange(rbLo, raLo - 1);
                     }

                     if (raHi > rbHi) {
                        intersectionRanges.appendRange(intersectionLo, rbHi);
                        raLo = rbHi + 1;
                        if (ib >= o.size()) {
                           subtractedA.appendRange(raLo, raHi);
                           this.appendRangesTo(subtractedA, ia, this.size());
                           break;
                        }

                        rbLo = o.getLo(ib);
                        rbHi = o.getHi(ib);
                        ib++;
                     } else if (raHi < rbHi) {
                        intersectionRanges.appendRange(intersectionLo, raHi);
                        rbLo = raHi + 1;
                        if (ia >= this.size()) {
                           subtractedB.appendRange(rbLo, rbHi);
                           o.appendRangesTo(subtractedB, ib, o.size());
                           break;
                        }

                        raLo = this.getLo(ia);
                        raHi = this.getHi(ia);
                        ia++;
                     } else {
                        if (!<unrepresentable>.$assertionsDisabled && raHi != rbHi) {
                           throw new AssertionError();
                        }

                        intersectionRanges.appendRange(intersectionLo, raHi);
                        if (ia < this.size()) {
                           raLo = this.getLo(ia);
                           raHi = this.getHi(ia);
                           ia++;
                           if (ib < o.size()) {
                              rbLo = o.getLo(ib);
                              rbHi = o.getHi(ib);
                              ib++;
                              continue;
                           }

                           subtractedA.appendRange(raLo, raHi);
                           this.appendRangesTo(subtractedA, ia, this.size());
                           break;
                        }

                        o.appendRangesTo(subtractedB, ib, o.size());
                        break;
                     }
                  }
               }

               if (subtractedA.isEmpty()) {
                  if (!<unrepresentable>.$assertionsDisabled && !this.equalsBuffer(intersectionRanges)) {
                     throw new AssertionError();
                  } else {
                     return new ImmutableSortedListOfRanges.IntersectAndSubtractResult<>(this.createEmpty(), this.create(subtractedB), (T)this);
                  }
               } else if (!subtractedB.isEmpty()) {
                  return new ImmutableSortedListOfRanges.IntersectAndSubtractResult<>(
                     this.create(subtractedA), this.create(subtractedB), this.create(intersectionRanges)
                  );
               } else if (!<unrepresentable>.$assertionsDisabled && !o.equalsBuffer(intersectionRanges)) {
                  throw new AssertionError();
               } else {
                  return new ImmutableSortedListOfRanges.IntersectAndSubtractResult<>(this.create(subtractedA), this.createEmpty(), o);
               }
            }
         }
      }
   }

   default <T extends ImmutableSortedListOfRanges> T union(T o) {
      return this.union(o, this.createTempBuffer());
   }

   default <T extends ImmutableSortedListOfRanges> T union(T o, CompilationBuffer compilationBuffer) {
      return this.union(o, this.getBuffer1(compilationBuffer));
   }

   default <T extends ImmutableSortedListOfRanges> T union(T o, RangesBuffer target) {
      if (!this.matchesNothing() && (o.size() != 1 || o.getMin() > this.getMin() || o.getMax() < this.getMax())) {
         if (!o.matchesNothing() && (this.size() != 1 || this.getMin() > o.getMin() || this.getMax() < o.getMax())) {
            SortedListOfRanges.union(this, o, target);
            if (this.equalsBuffer(target)) {
               return (T)this;
            } else {
               return o.equalsBuffer(target) ? o : this.create(target);
            }
         } else {
            return (T)this;
         }
      } else {
         return o;
      }
   }

   @Override
   default Iterator<Range> iterator() {
      return new ImmutableSortedListOfRanges.ImmutableSortedListOfRangesIterator(this);
   }

   static {
      if (<unrepresentable>.$assertionsDisabled) {
      }
   }

   public static final class ImmutableSortedListOfRangesIterator implements Iterator<Range> {
      private final ImmutableSortedListOfRanges ranges;
      private int i = 0;

      private ImmutableSortedListOfRangesIterator(ImmutableSortedListOfRanges ranges) {
         this.ranges = ranges;
      }

      @Override
      public boolean hasNext() {
         return this.i < this.ranges.size();
      }

      public Range next() {
         Range ret = new Range(this.ranges.getLo(this.i), this.ranges.getHi(this.i));
         this.i++;
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
