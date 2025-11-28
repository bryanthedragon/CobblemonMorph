package com.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.lang.UCharacter;
import com.cobblemon.mod.relocations.ibm.icu.util.ICUCloneNotSupportedException;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import java.text.CharacterIterator;

final class BreakTransliterator extends Transliterator {
   private BreakIterator bi;
   private String insertion;
   private int[] boundaries = new int[50];
   private int boundaryCount = 0;
   static final int LETTER_OR_MARK_MASK = 510;

   public BreakTransliterator(String ID, UnicodeFilter filter, BreakIterator bi, String insertion) {
      super(ID, filter);
      this.bi = bi;
      this.insertion = insertion;
   }

   public BreakTransliterator(String ID, UnicodeFilter filter) {
      this(ID, filter, null, " ");
   }

   public String getInsertion() {
      return this.insertion;
   }

   public void setInsertion(String insertion) {
      this.insertion = insertion;
   }

   public BreakIterator getBreakIterator() {
      if (this.bi == null) {
         this.bi = BreakIterator.getWordInstance(new ULocale("th_TH"));
      }

      return this.bi;
   }

   public void setBreakIterator(BreakIterator bi) {
      this.bi = bi;
   }

   @Override
   protected synchronized void handleTransliterate(Replaceable text, Transliterator.Position pos, boolean incremental) {
      this.boundaryCount = 0;
      int boundary = 0;
      this.getBreakIterator();
      this.bi.setText(new BreakTransliterator.ReplaceableCharacterIterator(text, pos.start, pos.limit, pos.start));

      for (int var8 = this.bi.first(); var8 != -1 && var8 < pos.limit; var8 = this.bi.next()) {
         if (var8 != 0) {
            int cp = UTF16.charAt(text, var8 - 1);
            int type = UCharacter.getType(cp);
            if ((1 << type & 510) != 0) {
               cp = UTF16.charAt(text, var8);
               type = UCharacter.getType(cp);
               if ((1 << type & 510) != 0) {
                  if (this.boundaryCount >= this.boundaries.length) {
                     int[] temp = new int[this.boundaries.length * 2];
                     System.arraycopy(this.boundaries, 0, temp, 0, this.boundaries.length);
                     this.boundaries = temp;
                  }

                  this.boundaries[this.boundaryCount++] = var8;
               }
            }
         }
      }

      int delta = 0;
      int lastBoundary = 0;
      if (this.boundaryCount != 0) {
         delta = this.boundaryCount * this.insertion.length();
         lastBoundary = this.boundaries[this.boundaryCount - 1];

         while (this.boundaryCount > 0) {
            boundary = this.boundaries[--this.boundaryCount];
            text.replace(boundary, boundary, this.insertion);
         }
      }

      pos.contextLimit += delta;
      pos.limit += delta;
      pos.start = incremental ? lastBoundary + delta : pos.limit;
   }

   static void register() {
      Transliterator trans = new BreakTransliterator("Any-BreakInternal", null);
      Transliterator.registerInstance(trans, false);
   }

   @Override
   public void addSourceTargetSet(UnicodeSet inputFilter, UnicodeSet sourceSet, UnicodeSet targetSet) {
      UnicodeSet myFilter = this.getFilterAsUnicodeSet(inputFilter);
      if (myFilter.size() != 0) {
         targetSet.addAll(this.insertion);
      }
   }

   static final class ReplaceableCharacterIterator implements CharacterIterator {
      private Replaceable text;
      private int begin;
      private int end;
      private int pos;

      public ReplaceableCharacterIterator(Replaceable text, int begin, int end, int pos) {
         if (text == null) {
            throw new NullPointerException();
         } else {
            this.text = text;
            if (begin < 0 || begin > end || end > text.length()) {
               throw new IllegalArgumentException("Invalid substring range");
            } else if (pos >= begin && pos <= end) {
               this.begin = begin;
               this.end = end;
               this.pos = pos;
            } else {
               throw new IllegalArgumentException("Invalid position");
            }
         }
      }

      public void setText(Replaceable text) {
         if (text == null) {
            throw new NullPointerException();
         } else {
            this.text = text;
            this.begin = 0;
            this.end = text.length();
            this.pos = 0;
         }
      }

      @Override
      public char first() {
         this.pos = this.begin;
         return this.current();
      }

      @Override
      public char last() {
         if (this.end != this.begin) {
            this.pos = this.end - 1;
         } else {
            this.pos = this.end;
         }

         return this.current();
      }

      @Override
      public char setIndex(int p) {
         if (p >= this.begin && p <= this.end) {
            this.pos = p;
            return this.current();
         } else {
            throw new IllegalArgumentException("Invalid index");
         }
      }

      @Override
      public char current() {
         return this.pos >= this.begin && this.pos < this.end ? this.text.charAt(this.pos) : '\uffff';
      }

      @Override
      public char next() {
         if (this.pos < this.end - 1) {
            this.pos++;
            return this.text.charAt(this.pos);
         } else {
            this.pos = this.end;
            return '\uffff';
         }
      }

      @Override
      public char previous() {
         if (this.pos > this.begin) {
            this.pos--;
            return this.text.charAt(this.pos);
         } else {
            return '\uffff';
         }
      }

      @Override
      public int getBeginIndex() {
         return this.begin;
      }

      @Override
      public int getEndIndex() {
         return this.end;
      }

      @Override
      public int getIndex() {
         return this.pos;
      }

      @Override
      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (!(obj instanceof BreakTransliterator.ReplaceableCharacterIterator)) {
            return false;
         } else {
            BreakTransliterator.ReplaceableCharacterIterator that = (BreakTransliterator.ReplaceableCharacterIterator)obj;
            if (this.hashCode() != that.hashCode()) {
               return false;
            } else {
               return !this.text.equals(that.text) ? false : this.pos == that.pos && this.begin == that.begin && this.end == that.end;
            }
         }
      }

      @Override
      public int hashCode() {
         return this.text.hashCode() ^ this.pos ^ this.begin ^ this.end;
      }

      @Override
      public Object clone() {
         try {
            return (BreakTransliterator.ReplaceableCharacterIterator)super.clone();
         } catch (CloneNotSupportedException var2) {
            throw new ICUCloneNotSupportedException();
         }
      }
   }
}
