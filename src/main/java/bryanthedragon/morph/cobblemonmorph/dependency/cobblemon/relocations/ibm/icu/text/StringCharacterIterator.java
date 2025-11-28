package com.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.util.ICUCloneNotSupportedException;
import java.text.CharacterIterator;

@Deprecated
public final class StringCharacterIterator implements CharacterIterator {
   private String text;
   private int begin;
   private int end;
   private int pos;

   @Deprecated
   public StringCharacterIterator(String text) {
      this(text, 0);
   }

   @Deprecated
   public StringCharacterIterator(String text, int pos) {
      this(text, 0, text.length(), pos);
   }

   @Deprecated
   public StringCharacterIterator(String text, int begin, int end, int pos) {
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

   @Deprecated
   public void setText(String text) {
      if (text == null) {
         throw new NullPointerException();
      } else {
         this.text = text;
         this.begin = 0;
         this.end = text.length();
         this.pos = 0;
      }
   }

   @Deprecated
   @Override
   public char first() {
      this.pos = this.begin;
      return this.current();
   }

   @Deprecated
   @Override
   public char last() {
      if (this.end != this.begin) {
         this.pos = this.end - 1;
      } else {
         this.pos = this.end;
      }

      return this.current();
   }

   @Deprecated
   @Override
   public char setIndex(int p) {
      if (p >= this.begin && p <= this.end) {
         this.pos = p;
         return this.current();
      } else {
         throw new IllegalArgumentException("Invalid index");
      }
   }

   @Deprecated
   @Override
   public char current() {
      return this.pos >= this.begin && this.pos < this.end ? this.text.charAt(this.pos) : '\uffff';
   }

   @Deprecated
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

   @Deprecated
   @Override
   public char previous() {
      if (this.pos > this.begin) {
         this.pos--;
         return this.text.charAt(this.pos);
      } else {
         return '\uffff';
      }
   }

   @Deprecated
   @Override
   public int getBeginIndex() {
      return this.begin;
   }

   @Deprecated
   @Override
   public int getEndIndex() {
      return this.end;
   }

   @Deprecated
   @Override
   public int getIndex() {
      return this.pos;
   }

   @Deprecated
   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof StringCharacterIterator)) {
         return false;
      } else {
         StringCharacterIterator that = (StringCharacterIterator)obj;
         if (this.hashCode() != that.hashCode()) {
            return false;
         } else {
            return !this.text.equals(that.text) ? false : this.pos == that.pos && this.begin == that.begin && this.end == that.end;
         }
      }
   }

   @Deprecated
   @Override
   public int hashCode() {
      return this.text.hashCode() ^ this.pos ^ this.begin ^ this.end;
   }

   @Deprecated
   @Override
   public Object clone() {
      try {
         return (StringCharacterIterator)super.clone();
      } catch (CloneNotSupportedException var2) {
         throw new ICUCloneNotSupportedException(var2);
      }
   }
}
