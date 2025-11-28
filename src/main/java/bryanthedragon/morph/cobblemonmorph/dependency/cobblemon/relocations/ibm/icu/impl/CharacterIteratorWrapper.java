package com.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.text.UCharacterIterator;
import java.text.CharacterIterator;

public class CharacterIteratorWrapper extends UCharacterIterator {
   private CharacterIterator iterator;

   public CharacterIteratorWrapper(CharacterIterator iter) {
      if (iter == null) {
         throw new IllegalArgumentException();
      } else {
         this.iterator = iter;
      }
   }

   @Override
   public int current() {
      int c = this.iterator.current();
      return c == 65535 ? -1 : c;
   }

   @Override
   public int getLength() {
      return this.iterator.getEndIndex() - this.iterator.getBeginIndex();
   }

   @Override
   public int getIndex() {
      return this.iterator.getIndex();
   }

   @Override
   public int next() {
      int i = this.iterator.current();
      this.iterator.next();
      return i == 65535 ? -1 : i;
   }

   @Override
   public int previous() {
      int i = this.iterator.previous();
      return i == 65535 ? -1 : i;
   }

   @Override
   public void setIndex(int index) {
      try {
         this.iterator.setIndex(index);
      } catch (IllegalArgumentException var3) {
         throw new IndexOutOfBoundsException();
      }
   }

   @Override
   public void setToLimit() {
      this.iterator.setIndex(this.iterator.getEndIndex());
   }

   @Override
   public int getText(char[] fillIn, int offset) {
      int length = this.iterator.getEndIndex() - this.iterator.getBeginIndex();
      int currentIndex = this.iterator.getIndex();
      if (offset >= 0 && offset + length <= fillIn.length) {
         for (char ch = this.iterator.first(); ch != '\uffff'; ch = this.iterator.next()) {
            fillIn[offset++] = ch;
         }

         this.iterator.setIndex(currentIndex);
         return length;
      } else {
         throw new IndexOutOfBoundsException(Integer.toString(length));
      }
   }

   @Override
   public Object clone() {
      try {
         CharacterIteratorWrapper result = (CharacterIteratorWrapper)super.clone();
         result.iterator = (CharacterIterator)this.iterator.clone();
         return result;
      } catch (CloneNotSupportedException var2) {
         return null;
      }
   }

   @Override
   public int moveIndex(int delta) {
      int length = this.iterator.getEndIndex() - this.iterator.getBeginIndex();
      int idx = this.iterator.getIndex() + delta;
      if (idx < 0) {
         idx = 0;
      } else if (idx > length) {
         idx = length;
      }

      return this.iterator.setIndex(idx);
   }

   @Override
   public CharacterIterator getCharacterIterator() {
      return (CharacterIterator)this.iterator.clone();
   }
}
