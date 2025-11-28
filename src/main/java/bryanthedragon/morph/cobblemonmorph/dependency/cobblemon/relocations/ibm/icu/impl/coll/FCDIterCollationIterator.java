package com.cobblemon.mod.relocations.ibm.icu.impl.coll;

import com.cobblemon.mod.relocations.ibm.icu.impl.Normalizer2Impl;
import com.cobblemon.mod.relocations.ibm.icu.text.UCharacterIterator;

public final class FCDIterCollationIterator extends IterCollationIterator {
   private FCDIterCollationIterator.State state = FCDIterCollationIterator.State.ITER_CHECK_FWD;
   private int start;
   private int pos;
   private int limit;
   private final Normalizer2Impl nfcImpl;
   private StringBuilder s;
   private StringBuilder normalized;

   public FCDIterCollationIterator(CollationData data, boolean numeric, UCharacterIterator ui, int startIndex) {
      super(data, numeric, ui);
      this.start = startIndex;
      this.nfcImpl = data.nfcImpl;
   }

   @Override
   public void resetToOffset(int newOffset) {
      super.resetToOffset(newOffset);
      this.start = newOffset;
      this.state = FCDIterCollationIterator.State.ITER_CHECK_FWD;
   }

   @Override
   public int getOffset() {
      if (this.state.compareTo(FCDIterCollationIterator.State.ITER_CHECK_BWD) <= 0) {
         return this.iter.getIndex();
      } else if (this.state == FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT) {
         return this.pos;
      } else {
         return this.pos == 0 ? this.start : this.limit;
      }
   }

   @Override
   public int nextCodePoint() {
      while (true) {
         if (this.state == FCDIterCollationIterator.State.ITER_CHECK_FWD) {
            int c = this.iter.next();
            if (c < 0) {
               return c;
            }

            if (!CollationFCD.hasTccc(c) || !CollationFCD.maybeTibetanCompositeVowel(c) && !CollationFCD.hasLccc(this.iter.current())) {
               if (isLeadSurrogate(c)) {
                  int trail = this.iter.next();
                  if (isTrailSurrogate(trail)) {
                     return Character.toCodePoint((char)c, (char)trail);
                  }

                  if (trail >= 0) {
                     this.iter.previous();
                  }
               }

               return c;
            }

            this.iter.previous();
            if (!this.nextSegment()) {
               return -1;
            }
         } else {
            if (this.state == FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT && this.pos != this.limit) {
               int cx = this.iter.nextCodePoint();
               this.pos = this.pos + Character.charCount(cx);

               assert cx >= 0;

               return cx;
            }

            if (this.state.compareTo(FCDIterCollationIterator.State.IN_NORM_ITER_AT_LIMIT) >= 0 && this.pos != this.normalized.length()) {
               int cx = this.normalized.codePointAt(this.pos);
               this.pos = this.pos + Character.charCount(cx);
               return cx;
            }

            this.switchToForward();
         }
      }
   }

   @Override
   public int previousCodePoint() {
      while (true) {
         if (this.state == FCDIterCollationIterator.State.ITER_CHECK_BWD) {
            int c = this.iter.previous();
            if (c < 0) {
               this.start = this.pos = 0;
               this.state = FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT;
               return -1;
            }

            if (CollationFCD.hasLccc(c)) {
               int prev = -1;
               if (CollationFCD.maybeTibetanCompositeVowel(c) || CollationFCD.hasTccc(prev = this.iter.previous())) {
                  this.iter.next();
                  if (prev >= 0) {
                     this.iter.next();
                  }

                  if (this.previousSegment()) {
                     continue;
                  }

                  return -1;
               }

               if (isTrailSurrogate(c)) {
                  if (prev < 0) {
                     prev = this.iter.previous();
                  }

                  if (isLeadSurrogate(prev)) {
                     return Character.toCodePoint((char)prev, (char)c);
                  }
               }

               if (prev >= 0) {
                  this.iter.next();
               }
            }

            return c;
         } else {
            if (this.state == FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT && this.pos != this.start) {
               int cx = this.iter.previousCodePoint();
               this.pos = this.pos - Character.charCount(cx);

               assert cx >= 0;

               return cx;
            }

            if (this.state.compareTo(FCDIterCollationIterator.State.IN_NORM_ITER_AT_LIMIT) >= 0 && this.pos != 0) {
               int cx = this.normalized.codePointBefore(this.pos);
               this.pos = this.pos - Character.charCount(cx);
               return cx;
            }

            this.switchToBackward();
         }
      }
   }

   @Override
   protected long handleNextCE32() {
      int c;
      while (true) {
         if (this.state == FCDIterCollationIterator.State.ITER_CHECK_FWD) {
            c = this.iter.next();
            if (c < 0) {
               return -4294967104L;
            }

            if (!CollationFCD.hasTccc(c) || !CollationFCD.maybeTibetanCompositeVowel(c) && !CollationFCD.hasLccc(this.iter.current())) {
               break;
            }

            this.iter.previous();
            if (!this.nextSegment()) {
               int var2 = -1;
               return 192L;
            }
         } else {
            if (this.state == FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT && this.pos != this.limit) {
               c = this.iter.next();
               this.pos++;

               assert c >= 0;
               break;
            }

            if (this.state.compareTo(FCDIterCollationIterator.State.IN_NORM_ITER_AT_LIMIT) >= 0 && this.pos != this.normalized.length()) {
               c = this.normalized.charAt(this.pos++);
               break;
            }

            this.switchToForward();
         }
      }

      return this.makeCodePointAndCE32Pair(c, this.trie.getFromU16SingleLead((char)c));
   }

   @Override
   protected char handleGetTrailSurrogate() {
      if (this.state.compareTo(FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT) <= 0) {
         int trail = this.iter.next();
         if (isTrailSurrogate(trail)) {
            if (this.state == FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT) {
               this.pos++;
            }
         } else if (trail >= 0) {
            this.iter.previous();
         }

         return (char)trail;
      } else {
         assert this.pos < this.normalized.length();

         char trail;
         if (Character.isLowSurrogate(trail = this.normalized.charAt(this.pos))) {
            this.pos++;
         }

         return trail;
      }
   }

   @Override
   protected void forwardNumCodePoints(int num) {
      while (num > 0 && this.nextCodePoint() >= 0) {
         num--;
      }
   }

   @Override
   protected void backwardNumCodePoints(int num) {
      while (num > 0 && this.previousCodePoint() >= 0) {
         num--;
      }
   }

   private void switchToForward() {
      assert this.state == FCDIterCollationIterator.State.ITER_CHECK_BWD
         || this.state == FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT && this.pos == this.limit
         || this.state.compareTo(FCDIterCollationIterator.State.IN_NORM_ITER_AT_LIMIT) >= 0 && this.pos == this.normalized.length();

      if (this.state == FCDIterCollationIterator.State.ITER_CHECK_BWD) {
         this.start = this.pos = this.iter.getIndex();
         if (this.pos == this.limit) {
            this.state = FCDIterCollationIterator.State.ITER_CHECK_FWD;
         } else {
            this.state = FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT;
         }
      } else {
         if (this.state != FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT) {
            if (this.state == FCDIterCollationIterator.State.IN_NORM_ITER_AT_START) {
               this.iter.moveIndex(this.limit - this.start);
            }

            this.start = this.limit;
         }

         this.state = FCDIterCollationIterator.State.ITER_CHECK_FWD;
      }
   }

   private boolean nextSegment() {
      assert this.state == FCDIterCollationIterator.State.ITER_CHECK_FWD;

      this.pos = this.iter.getIndex();
      if (this.s == null) {
         this.s = new StringBuilder();
      } else {
         this.s.setLength(0);
      }

      int prevCC = 0;

      while (true) {
         int c = this.iter.nextCodePoint();
         if (c < 0) {
            break;
         }

         int fcd16 = this.nfcImpl.getFCD16(c);
         int leadCC = fcd16 >> 8;
         if (leadCC == 0 && this.s.length() != 0) {
            this.iter.previousCodePoint();
            break;
         }

         this.s.appendCodePoint(c);
         if (leadCC == 0 || prevCC <= leadCC && !CollationFCD.isFCD16OfTibetanCompositeVowel(fcd16)) {
            prevCC = fcd16 & 0xFF;
            if (prevCC != 0) {
               continue;
            }
            break;
         }

         while (true) {
            c = this.iter.nextCodePoint();
            if (c < 0) {
               break;
            }

            if (this.nfcImpl.getFCD16(c) <= 255) {
               this.iter.previousCodePoint();
               break;
            }

            this.s.appendCodePoint(c);
         }

         this.normalize(this.s);
         this.start = this.pos;
         this.limit = this.pos + this.s.length();
         this.state = FCDIterCollationIterator.State.IN_NORM_ITER_AT_LIMIT;
         this.pos = 0;
         return true;
      }

      this.limit = this.pos + this.s.length();

      assert this.pos != this.limit;

      this.iter.moveIndex(-this.s.length());
      this.state = FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT;
      return true;
   }

   private void switchToBackward() {
      assert this.state == FCDIterCollationIterator.State.ITER_CHECK_FWD
         || this.state == FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT && this.pos == this.start
         || this.state.compareTo(FCDIterCollationIterator.State.IN_NORM_ITER_AT_LIMIT) >= 0 && this.pos == 0;

      if (this.state == FCDIterCollationIterator.State.ITER_CHECK_FWD) {
         this.limit = this.pos = this.iter.getIndex();
         if (this.pos == this.start) {
            this.state = FCDIterCollationIterator.State.ITER_CHECK_BWD;
         } else {
            this.state = FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT;
         }
      } else {
         if (this.state != FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT) {
            if (this.state == FCDIterCollationIterator.State.IN_NORM_ITER_AT_LIMIT) {
               this.iter.moveIndex(this.start - this.limit);
            }

            this.limit = this.start;
         }

         this.state = FCDIterCollationIterator.State.ITER_CHECK_BWD;
      }
   }

   private boolean previousSegment() {
      assert this.state == FCDIterCollationIterator.State.ITER_CHECK_BWD;

      this.pos = this.iter.getIndex();
      if (this.s == null) {
         this.s = new StringBuilder();
      } else {
         this.s.setLength(0);
      }

      int nextCC = 0;

      while (true) {
         int c = this.iter.previousCodePoint();
         if (c < 0) {
            break;
         }

         int fcd16 = this.nfcImpl.getFCD16(c);
         int trailCC = fcd16 & 0xFF;
         if (trailCC == 0 && this.s.length() != 0) {
            this.iter.nextCodePoint();
            break;
         }

         this.s.appendCodePoint(c);
         if (trailCC == 0 || (nextCC == 0 || trailCC <= nextCC) && !CollationFCD.isFCD16OfTibetanCompositeVowel(fcd16)) {
            nextCC = fcd16 >> 8;
            if (nextCC != 0) {
               continue;
            }
            break;
         }

         while (fcd16 > 255) {
            c = this.iter.previousCodePoint();
            if (c < 0) {
               break;
            }

            fcd16 = this.nfcImpl.getFCD16(c);
            if (fcd16 == 0) {
               this.iter.nextCodePoint();
               break;
            }

            this.s.appendCodePoint(c);
         }

         this.s.reverse();
         this.normalize(this.s);
         this.limit = this.pos;
         this.start = this.pos - this.s.length();
         this.state = FCDIterCollationIterator.State.IN_NORM_ITER_AT_START;
         this.pos = this.normalized.length();
         return true;
      }

      this.start = this.pos - this.s.length();

      assert this.pos != this.start;

      this.iter.moveIndex(this.s.length());
      this.state = FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT;
      return true;
   }

   private void normalize(CharSequence s) {
      if (this.normalized == null) {
         this.normalized = new StringBuilder();
      }

      this.nfcImpl.decompose(s, this.normalized);
   }

   private static enum State {
      ITER_CHECK_FWD,
      ITER_CHECK_BWD,
      ITER_IN_FCD_SEGMENT,
      IN_NORM_ITER_AT_LIMIT,
      IN_NORM_ITER_AT_START;
   }
}
