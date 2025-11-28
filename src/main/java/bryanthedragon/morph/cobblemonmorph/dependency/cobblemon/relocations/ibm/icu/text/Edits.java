package com.cobblemon.mod.relocations.ibm.icu.text;

import java.nio.BufferOverflowException;
import java.util.Arrays;

public final class Edits {
   private static final int MAX_UNCHANGED_LENGTH = 4096;
   private static final int MAX_UNCHANGED = 4095;
   private static final int MAX_SHORT_CHANGE_OLD_LENGTH = 6;
   private static final int MAX_SHORT_CHANGE_NEW_LENGTH = 7;
   private static final int SHORT_CHANGE_NUM_MASK = 511;
   private static final int MAX_SHORT_CHANGE = 28671;
   private static final int LENGTH_IN_1TRAIL = 61;
   private static final int LENGTH_IN_2TRAIL = 62;
   private static final int STACK_CAPACITY = 100;
   private char[] array = new char[100];
   private int length;
   private int delta;
   private int numChanges;

   public void reset() {
      this.length = this.delta = this.numChanges = 0;
   }

   private void setLastUnit(int last) {
      this.array[this.length - 1] = (char)last;
   }

   private int lastUnit() {
      return this.length > 0 ? this.array[this.length - 1] : 65535;
   }

   public void addUnchanged(int unchangedLength) {
      if (unchangedLength < 0) {
         throw new IllegalArgumentException("addUnchanged(" + unchangedLength + "): length must not be negative");
      } else {
         int last = this.lastUnit();
         if (last < 4095) {
            int remaining = 4095 - last;
            if (remaining >= unchangedLength) {
               this.setLastUnit(last + unchangedLength);
               return;
            }

            this.setLastUnit(4095);
            unchangedLength -= remaining;
         }

         while (unchangedLength >= 4096) {
            this.append(4095);
            unchangedLength -= 4096;
         }

         if (unchangedLength > 0) {
            this.append(unchangedLength - 1);
         }
      }
   }

   public void addReplace(int oldLength, int newLength) {
      if (oldLength < 0 || newLength < 0) {
         throw new IllegalArgumentException("addReplace(" + oldLength + ", " + newLength + "): both lengths must be non-negative");
      } else if (oldLength != 0 || newLength != 0) {
         this.numChanges++;
         int newDelta = newLength - oldLength;
         if (newDelta != 0) {
            if (newDelta > 0 && this.delta >= 0 && newDelta > Integer.MAX_VALUE - this.delta
               || newDelta < 0 && this.delta < 0 && newDelta < Integer.MIN_VALUE - this.delta) {
               throw new IndexOutOfBoundsException();
            }

            this.delta += newDelta;
         }

         if (0 < oldLength && oldLength <= 6 && newLength <= 7) {
            int u = oldLength << 12 | newLength << 9;
            int last = this.lastUnit();
            if (4095 < last && last < 28671 && (last & -512) == u && (last & 511) < 511) {
               this.setLastUnit(last + 1);
            } else {
               this.append(u);
            }
         } else {
            int head = 28672;
            if (oldLength < 61 && newLength < 61) {
               head |= oldLength << 6;
               head |= newLength;
               this.append(head);
            } else if (this.array.length - this.length >= 5 || this.growArray()) {
               int limit = this.length + 1;
               if (oldLength < 61) {
                  head |= oldLength << 6;
               } else if (oldLength <= 32767) {
                  head |= 3904;
                  this.array[limit++] = (char)(32768 | oldLength);
               } else {
                  head |= 62 + (oldLength >> 30) << 6;
                  this.array[limit++] = (char)(32768 | oldLength >> 15);
                  this.array[limit++] = (char)(32768 | oldLength);
               }

               if (newLength < 61) {
                  head |= newLength;
               } else if (newLength <= 32767) {
                  head |= 61;
                  this.array[limit++] = (char)(32768 | newLength);
               } else {
                  head |= 62 + (newLength >> 30);
                  this.array[limit++] = (char)(32768 | newLength >> 15);
                  this.array[limit++] = (char)(32768 | newLength);
               }

               this.array[this.length] = (char)head;
               this.length = limit;
            }
         }
      }
   }

   private void append(int r) {
      if (this.length < this.array.length || this.growArray()) {
         this.array[this.length++] = (char)r;
      }
   }

   private boolean growArray() {
      int newCapacity;
      if (this.array.length == 100) {
         newCapacity = 2000;
      } else {
         if (this.array.length == Integer.MAX_VALUE) {
            throw new BufferOverflowException();
         }

         if (this.array.length >= 1073741823) {
            newCapacity = Integer.MAX_VALUE;
         } else {
            newCapacity = 2 * this.array.length;
         }
      }

      if (newCapacity - this.array.length < 5) {
         throw new BufferOverflowException();
      } else {
         this.array = Arrays.copyOf(this.array, newCapacity);
         return true;
      }
   }

   public int lengthDelta() {
      return this.delta;
   }

   public boolean hasChanges() {
      return this.numChanges != 0;
   }

   public int numberOfChanges() {
      return this.numChanges;
   }

   public Edits.Iterator getCoarseChangesIterator() {
      return new Edits.Iterator(this.array, this.length, true, true);
   }

   public Edits.Iterator getCoarseIterator() {
      return new Edits.Iterator(this.array, this.length, false, true);
   }

   public Edits.Iterator getFineChangesIterator() {
      return new Edits.Iterator(this.array, this.length, true, false);
   }

   public Edits.Iterator getFineIterator() {
      return new Edits.Iterator(this.array, this.length, false, false);
   }

   public Edits mergeAndAppend(Edits ab, Edits bc) {
      Edits.Iterator abIter = ab.getFineIterator();
      Edits.Iterator bcIter = bc.getFineIterator();
      boolean abHasNext = true;
      boolean bcHasNext = true;
      int aLength = 0;
      int ab_bLength = 0;
      int bc_bLength = 0;
      int cLength = 0;
      int pending_aLength = 0;
      int pending_cLength = 0;

      while (true) {
         while (bc_bLength == 0 && bcHasNext && (bcHasNext = bcIter.next())) {
            bc_bLength = bcIter.oldLength();
            cLength = bcIter.newLength();
            if (bc_bLength != 0) {
               break;
            }

            if (ab_bLength != 0 && abIter.hasChange()) {
               pending_cLength += cLength;
            } else {
               this.addReplace(pending_aLength, pending_cLength + cLength);
               pending_cLength = 0;
               pending_aLength = 0;
            }
         }

         if (ab_bLength == 0) {
            if (!abHasNext || !(abHasNext = abIter.next())) {
               if (bc_bLength != 0) {
                  throw new IllegalArgumentException("The ab output string is shorter than the bc input string.");
               } else {
                  if (pending_aLength != 0 || pending_cLength != 0) {
                     this.addReplace(pending_aLength, pending_cLength);
                  }

                  return this;
               }
            }

            aLength = abIter.oldLength();
            ab_bLength = abIter.newLength();
            if (ab_bLength == 0) {
               if (bc_bLength != bcIter.oldLength() && bcIter.hasChange()) {
                  pending_aLength += aLength;
                  continue;
               }

               this.addReplace(pending_aLength + aLength, pending_cLength);
               pending_cLength = 0;
               pending_aLength = 0;
               continue;
            }
         }

         if (bc_bLength == 0) {
            throw new IllegalArgumentException("The bc input string is shorter than the ab output string.");
         }

         if (!abIter.hasChange() && !bcIter.hasChange()) {
            if (pending_aLength != 0 || pending_cLength != 0) {
               this.addReplace(pending_aLength, pending_cLength);
               pending_cLength = 0;
               pending_aLength = 0;
            }

            int unchangedLength = aLength <= cLength ? aLength : cLength;
            this.addUnchanged(unchangedLength);
            ab_bLength = aLength -= unchangedLength;
            bc_bLength = cLength -= unchangedLength;
         } else {
            if (!abIter.hasChange() && bcIter.hasChange()) {
               if (ab_bLength >= bc_bLength) {
                  this.addReplace(pending_aLength + bc_bLength, pending_cLength + cLength);
                  pending_cLength = 0;
                  pending_aLength = 0;
                  aLength = ab_bLength -= bc_bLength;
                  bc_bLength = 0;
                  continue;
               }
            } else if (abIter.hasChange() && !bcIter.hasChange()) {
               if (ab_bLength <= bc_bLength) {
                  this.addReplace(pending_aLength + aLength, pending_cLength + ab_bLength);
                  pending_cLength = 0;
                  pending_aLength = 0;
                  cLength = bc_bLength -= ab_bLength;
                  ab_bLength = 0;
                  continue;
               }
            } else if (ab_bLength == bc_bLength) {
               this.addReplace(pending_aLength + aLength, pending_cLength + cLength);
               pending_cLength = 0;
               pending_aLength = 0;
               bc_bLength = 0;
               ab_bLength = 0;
               continue;
            }

            pending_aLength += aLength;
            pending_cLength += cLength;
            if (ab_bLength < bc_bLength) {
               bc_bLength -= ab_bLength;
               ab_bLength = 0;
               cLength = 0;
            } else {
               ab_bLength -= bc_bLength;
               bc_bLength = 0;
               aLength = 0;
            }
         }
      }
   }

   public static final class Iterator {
      private final char[] array;
      private int index;
      private final int length;
      private int remaining;
      private final boolean onlyChanges_;
      private final boolean coarse;
      private int dir;
      private boolean changed;
      private int oldLength_;
      private int newLength_;
      private int srcIndex;
      private int replIndex;
      private int destIndex;

      private Iterator(char[] a, int len, boolean oc, boolean crs) {
         this.array = a;
         this.length = len;
         this.onlyChanges_ = oc;
         this.coarse = crs;
      }

      private int readLength(int head) {
         if (head < 61) {
            return head;
         } else if (head < 62) {
            assert this.index < this.length;

            assert this.array[this.index] >= '耀';

            return this.array[this.index++] & 32767;
         } else {
            assert this.index + 2 <= this.length;

            assert this.array[this.index] >= '耀';

            assert this.array[this.index + 1] >= '耀';

            int len = (head & 1) << 30 | (this.array[this.index] & 32767) << 15 | this.array[this.index + 1] & 32767;
            this.index += 2;
            return len;
         }
      }

      private void updateNextIndexes() {
         this.srcIndex = this.srcIndex + this.oldLength_;
         if (this.changed) {
            this.replIndex = this.replIndex + this.newLength_;
         }

         this.destIndex = this.destIndex + this.newLength_;
      }

      private void updatePreviousIndexes() {
         this.srcIndex = this.srcIndex - this.oldLength_;
         if (this.changed) {
            this.replIndex = this.replIndex - this.newLength_;
         }

         this.destIndex = this.destIndex - this.newLength_;
      }

      private boolean noNext() {
         this.dir = 0;
         this.changed = false;
         this.oldLength_ = this.newLength_ = 0;
         return false;
      }

      public boolean next() {
         return this.next(this.onlyChanges_);
      }

      private boolean next(boolean onlyChanges) {
         if (this.dir > 0) {
            this.updateNextIndexes();
         } else {
            if (this.dir < 0 && this.remaining > 0) {
               this.index++;
               this.dir = 1;
               return true;
            }

            this.dir = 1;
         }

         if (this.remaining >= 1) {
            if (this.remaining > 1) {
               this.remaining--;
               return true;
            }

            this.remaining = 0;
         }

         if (this.index >= this.length) {
            return this.noNext();
         } else {
            int u = this.array[this.index++];
            if (u <= 4095) {
               this.changed = false;

               for (this.oldLength_ = u + 1; this.index < this.length && (u = this.array[this.index]) <= 4095; this.oldLength_ += u + 1) {
                  this.index++;
               }

               this.newLength_ = this.oldLength_;
               if (!onlyChanges) {
                  return true;
               }

               this.updateNextIndexes();
               if (this.index >= this.length) {
                  return this.noNext();
               }

               this.index++;
            }

            this.changed = true;
            if (u <= 28671) {
               int oldLen = u >> 12;
               int newLen = u >> 9 & 7;
               int num = (u & 511) + 1;
               if (!this.coarse) {
                  this.oldLength_ = oldLen;
                  this.newLength_ = newLen;
                  if (num > 1) {
                     this.remaining = num;
                  }

                  return true;
               }

               this.oldLength_ = num * oldLen;
               this.newLength_ = num * newLen;
            } else {
               assert u <= 32767;

               this.oldLength_ = this.readLength(u >> 6 & 63);
               this.newLength_ = this.readLength(u & 63);
               if (!this.coarse) {
                  return true;
               }
            }

            while (this.index < this.length && (var6 = this.array[this.index]) > 4095) {
               this.index++;
               if (var6 <= 28671) {
                  int num = (var6 & 511) + 1;
                  this.oldLength_ += (var6 >> '\f') * num;
                  this.newLength_ += (var6 >> '\t' & 7) * num;
               } else {
                  assert var6 <= 32767;

                  this.oldLength_ = this.oldLength_ + this.readLength(var6 >> 6 & 63);
                  this.newLength_ = this.newLength_ + this.readLength(var6 & '?');
               }
            }

            return true;
         }
      }

      private boolean previous() {
         if (this.dir >= 0) {
            if (this.dir > 0) {
               if (this.remaining > 0) {
                  this.index--;
                  this.dir = -1;
                  return true;
               }

               this.updateNextIndexes();
            }

            this.dir = -1;
         }

         if (this.remaining > 0) {
            int u = this.array[this.index];

            assert 4095 < u && u <= 28671;

            if (this.remaining <= (u & 511)) {
               this.remaining++;
               this.updatePreviousIndexes();
               return true;
            }

            this.remaining = 0;
         }

         if (this.index <= 0) {
            return this.noNext();
         } else {
            int ux = this.array[--this.index];
            if (ux <= 4095) {
               this.changed = false;
               this.oldLength_ = ux + 1;

               while (this.index > 0 && (var8 = this.array[this.index - 1]) <= 4095) {
                  this.index--;
                  this.oldLength_ += var8 + 1;
               }

               this.newLength_ = this.oldLength_;
               this.updatePreviousIndexes();
               return true;
            } else {
               this.changed = true;
               if (ux <= 28671) {
                  int oldLen = ux >> 12;
                  int newLen = ux >> 9 & 7;
                  int num = (ux & 511) + 1;
                  if (!this.coarse) {
                     this.oldLength_ = oldLen;
                     this.newLength_ = newLen;
                     if (num > 1) {
                        this.remaining = 1;
                     }

                     this.updatePreviousIndexes();
                     return true;
                  }

                  this.oldLength_ = num * oldLen;
                  this.newLength_ = num * newLen;
               } else {
                  if (ux <= 32767) {
                     this.oldLength_ = this.readLength(ux >> 6 & 63);
                     this.newLength_ = this.readLength(ux & 63);
                  } else {
                     assert this.index > 0;

                     while ((var6 = this.array[--this.index]) > 32767) {
                     }

                     assert var6 > 28671;

                     int headIndex = this.index++;
                     this.oldLength_ = this.readLength(var6 >> 6 & 63);
                     this.newLength_ = this.readLength(var6 & '?');
                     this.index = headIndex;
                  }

                  if (!this.coarse) {
                     this.updatePreviousIndexes();
                     return true;
                  }
               }

               while (this.index > 0 && (var7 = this.array[this.index - 1]) > 4095) {
                  this.index--;
                  if (var7 <= 28671) {
                     int num = (var7 & 511) + 1;
                     this.oldLength_ += (var7 >> '\f') * num;
                     this.newLength_ += (var7 >> '\t' & 7) * num;
                  } else if (var7 <= 32767) {
                     int headIndex = this.index++;
                     this.oldLength_ = this.oldLength_ + this.readLength(var7 >> 6 & 63);
                     this.newLength_ = this.newLength_ + this.readLength(var7 & '?');
                     this.index = headIndex;
                  }
               }

               this.updatePreviousIndexes();
               return true;
            }
         }
      }

      public boolean findSourceIndex(int i) {
         return this.findIndex(i, true) == 0;
      }

      public boolean findDestinationIndex(int i) {
         return this.findIndex(i, false) == 0;
      }

      private int findIndex(int i, boolean findSource) {
         if (i < 0) {
            return -1;
         } else {
            int spanStart;
            int spanLength;
            if (findSource) {
               spanStart = this.srcIndex;
               spanLength = this.oldLength_;
            } else {
               spanStart = this.destIndex;
               spanLength = this.newLength_;
            }

            if (i < spanStart) {
               if (i >= spanStart / 2) {
                  while (true) {
                     boolean hasPrevious = this.previous();

                     assert hasPrevious;

                     spanStart = findSource ? this.srcIndex : this.destIndex;
                     if (i >= spanStart) {
                        return 0;
                     }

                     if (this.remaining > 0) {
                        spanLength = findSource ? this.oldLength_ : this.newLength_;
                        int u = this.array[this.index];

                        assert 4095 < u && u <= 28671;

                        int num = (u & 511) + 1 - this.remaining;
                        int len = num * spanLength;
                        if (i >= spanStart - len) {
                           int n = (spanStart - i - 1) / spanLength + 1;
                           this.srcIndex = this.srcIndex - n * this.oldLength_;
                           this.replIndex = this.replIndex - n * this.newLength_;
                           this.destIndex = this.destIndex - n * this.newLength_;
                           this.remaining += n;
                           return 0;
                        }

                        this.srcIndex = this.srcIndex - num * this.oldLength_;
                        this.replIndex = this.replIndex - num * this.newLength_;
                        this.destIndex = this.destIndex - num * this.newLength_;
                        this.remaining = 0;
                     }
                  }
               }

               this.dir = 0;
               this.index = this.remaining = this.oldLength_ = this.newLength_ = this.srcIndex = this.replIndex = this.destIndex = 0;
            } else if (i < spanStart + spanLength) {
               return 0;
            }

            while (this.next(false)) {
               if (findSource) {
                  spanStart = this.srcIndex;
                  spanLength = this.oldLength_;
               } else {
                  spanStart = this.destIndex;
                  spanLength = this.newLength_;
               }

               if (i < spanStart + spanLength) {
                  return 0;
               }

               if (this.remaining > 1) {
                  int len = this.remaining * spanLength;
                  if (i < spanStart + len) {
                     int n = (i - spanStart) / spanLength;
                     this.srcIndex = this.srcIndex + n * this.oldLength_;
                     this.replIndex = this.replIndex + n * this.newLength_;
                     this.destIndex = this.destIndex + n * this.newLength_;
                     this.remaining -= n;
                     return 0;
                  }

                  this.oldLength_ = this.oldLength_ * this.remaining;
                  this.newLength_ = this.newLength_ * this.remaining;
                  this.remaining = 0;
               }
            }

            return 1;
         }
      }

      public int destinationIndexFromSourceIndex(int i) {
         int where = this.findIndex(i, true);
         if (where < 0) {
            return 0;
         } else if (where > 0 || i == this.srcIndex) {
            return this.destIndex;
         } else {
            return this.changed ? this.destIndex + this.newLength_ : this.destIndex + (i - this.srcIndex);
         }
      }

      public int sourceIndexFromDestinationIndex(int i) {
         int where = this.findIndex(i, false);
         if (where < 0) {
            return 0;
         } else if (where > 0 || i == this.destIndex) {
            return this.srcIndex;
         } else {
            return this.changed ? this.srcIndex + this.oldLength_ : this.srcIndex + (i - this.destIndex);
         }
      }

      public boolean hasChange() {
         return this.changed;
      }

      public int oldLength() {
         return this.oldLength_;
      }

      public int newLength() {
         return this.newLength_;
      }

      public int sourceIndex() {
         return this.srcIndex;
      }

      public int replacementIndex() {
         return this.replIndex;
      }

      public int destinationIndex() {
         return this.destIndex;
      }

      @Override
      public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append(super.toString());
         sb.append("{ src[");
         sb.append(this.srcIndex);
         sb.append("..");
         sb.append(this.srcIndex + this.oldLength_);
         if (this.changed) {
            sb.append("] ⇝ dest[");
         } else {
            sb.append("] ≡ dest[");
         }

         sb.append(this.destIndex);
         sb.append("..");
         sb.append(this.destIndex + this.newLength_);
         if (this.changed) {
            sb.append("], repl[");
            sb.append(this.replIndex);
            sb.append("..");
            sb.append(this.replIndex + this.newLength_);
            sb.append("] }");
         } else {
            sb.append("] (no-change) }");
         }

         return sb.toString();
      }
   }
}
