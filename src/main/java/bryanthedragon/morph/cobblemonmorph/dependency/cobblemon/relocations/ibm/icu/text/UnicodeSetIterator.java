package com.cobblemon.mod.relocations.ibm.icu.text;

import java.util.Iterator;

public final class UnicodeSetIterator {
   public static final int IS_STRING = -1;
   public int codepoint;
   public int codepointEnd;
   public String string;
   private UnicodeSet set;
   private int endRange = 0;
   private int range = 0;
   private int endElement;
   private int nextElement;
   private Iterator<String> stringIterator = null;

   public UnicodeSetIterator(UnicodeSet set) {
      this.reset(set);
   }

   public UnicodeSetIterator() {
      this.reset(new UnicodeSet());
   }

   public UnicodeSetIterator skipToStrings() {
      this.range = this.endRange;
      this.endElement = -1;
      this.nextElement = 0;
      return this;
   }

   public boolean next() {
      if (this.nextElement <= this.endElement) {
         this.codepoint = this.codepointEnd = this.nextElement++;
         return true;
      } else if (this.range < this.endRange) {
         this.loadRange(++this.range);
         this.codepoint = this.codepointEnd = this.nextElement++;
         return true;
      } else if (this.stringIterator == null) {
         return false;
      } else {
         this.codepoint = -1;
         this.string = this.stringIterator.next();
         if (!this.stringIterator.hasNext()) {
            this.stringIterator = null;
         }

         return true;
      }
   }

   public boolean nextRange() {
      if (this.nextElement <= this.endElement) {
         this.codepointEnd = this.endElement;
         this.codepoint = this.nextElement;
         this.nextElement = this.endElement + 1;
         return true;
      } else if (this.range < this.endRange) {
         this.loadRange(++this.range);
         this.codepointEnd = this.endElement;
         this.codepoint = this.nextElement;
         this.nextElement = this.endElement + 1;
         return true;
      } else if (this.stringIterator == null) {
         return false;
      } else {
         this.codepoint = -1;
         this.string = this.stringIterator.next();
         if (!this.stringIterator.hasNext()) {
            this.stringIterator = null;
         }

         return true;
      }
   }

   public void reset(UnicodeSet uset) {
      this.set = uset;
      this.reset();
   }

   public void reset() {
      this.endRange = this.set.getRangeCount() - 1;
      this.range = 0;
      this.endElement = -1;
      this.nextElement = 0;
      if (this.endRange >= 0) {
         this.loadRange(this.range);
      }

      if (this.set.hasStrings()) {
         this.stringIterator = this.set.strings.iterator();
      } else {
         this.stringIterator = null;
      }
   }

   public String getString() {
      return this.codepoint != -1 ? UTF16.valueOf(this.codepoint) : this.string;
   }

   private void loadRange(int aRange) {
      this.nextElement = this.set.getRangeStart(aRange);
      this.endElement = this.set.getRangeEnd(aRange);
   }
}
