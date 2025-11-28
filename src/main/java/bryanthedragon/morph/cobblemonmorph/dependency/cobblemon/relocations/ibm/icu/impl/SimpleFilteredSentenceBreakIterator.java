package com.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.text.BreakIterator;
import com.cobblemon.mod.relocations.ibm.icu.text.FilteredBreakIteratorBuilder;
import com.cobblemon.mod.relocations.ibm.icu.text.UCharacterIterator;
import com.cobblemon.mod.relocations.ibm.icu.util.BytesTrie;
import com.cobblemon.mod.relocations.ibm.icu.util.CharsTrie;
import com.cobblemon.mod.relocations.ibm.icu.util.CharsTrieBuilder;
import com.cobblemon.mod.relocations.ibm.icu.util.ICUCloneNotSupportedException;
import com.cobblemon.mod.relocations.ibm.icu.util.StringTrieBuilder;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import java.text.CharacterIterator;
import java.util.HashSet;
import java.util.Locale;

public class SimpleFilteredSentenceBreakIterator extends BreakIterator {
   private BreakIterator delegate;
   private UCharacterIterator text;
   private CharsTrie backwardsTrie;
   private CharsTrie forwardsPartialTrie;

   public SimpleFilteredSentenceBreakIterator(BreakIterator adoptBreakIterator, CharsTrie forwardsPartialTrie, CharsTrie backwardsTrie) {
      this.delegate = adoptBreakIterator;
      this.forwardsPartialTrie = forwardsPartialTrie;
      this.backwardsTrie = backwardsTrie;
   }

   private final void resetState() {
      this.text = UCharacterIterator.getInstance((CharacterIterator)this.delegate.getText().clone());
   }

   private final boolean breakExceptionAt(int n) {
      int bestPosn = -1;
      int bestValue = -1;
      this.text.setIndex(n);
      this.backwardsTrie.reset();
      if (this.text.previousCodePoint() != 32) {
         int uch = this.text.nextCodePoint();
      }

      int var6;
      while ((var6 = this.text.previousCodePoint()) >= 0) {
         BytesTrie.Result r = this.backwardsTrie.nextForCodePoint(var6);
         if (r.hasValue()) {
            bestPosn = this.text.getIndex();
            bestValue = this.backwardsTrie.getValue();
         }

         if (!r.hasNext()) {
            break;
         }
      }

      this.backwardsTrie.reset();
      if (bestPosn >= 0) {
         if (bestValue == 2) {
            return true;
         }

         if (bestValue == 1 && this.forwardsPartialTrie != null) {
            this.forwardsPartialTrie.reset();
            BytesTrie.Result rfwd = BytesTrie.Result.INTERMEDIATE_VALUE;
            this.text.setIndex(bestPosn);

            while ((var6 = this.text.nextCodePoint()) != -1 && (rfwd = this.forwardsPartialTrie.nextForCodePoint(var6)).hasNext()) {
            }

            this.forwardsPartialTrie.reset();
            if (rfwd.matches()) {
               return true;
            }
         }
      }

      return false;
   }

   private final int internalNext(int n) {
      if (n != -1 && this.backwardsTrie != null) {
         this.resetState();

         for (int textLen = this.text.getLength(); n != -1 && n != textLen; n = this.delegate.next()) {
            if (!this.breakExceptionAt(n)) {
               return n;
            }
         }

         return n;
      } else {
         return n;
      }
   }

   private final int internalPrev(int n) {
      if (n != 0 && n != -1 && this.backwardsTrie != null) {
         this.resetState();

         while (n != -1 && n != 0) {
            if (!this.breakExceptionAt(n)) {
               return n;
            }

            n = this.delegate.previous();
         }

         return n;
      } else {
         return n;
      }
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this == obj) {
         return true;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         SimpleFilteredSentenceBreakIterator other = (SimpleFilteredSentenceBreakIterator)obj;
         return this.delegate.equals(other.delegate)
            && this.text.equals(other.text)
            && this.backwardsTrie.equals(other.backwardsTrie)
            && this.forwardsPartialTrie.equals(other.forwardsPartialTrie);
      }
   }

   @Override
   public int hashCode() {
      return this.forwardsPartialTrie.hashCode() * 39 + this.backwardsTrie.hashCode() * 11 + this.delegate.hashCode();
   }

   @Override
   public Object clone() {
      SimpleFilteredSentenceBreakIterator other = (SimpleFilteredSentenceBreakIterator)super.clone();

      try {
         if (this.delegate != null) {
            other.delegate = (BreakIterator)this.delegate.clone();
         }

         if (this.text != null) {
            other.text = (UCharacterIterator)this.text.clone();
         }

         if (this.backwardsTrie != null) {
            other.backwardsTrie = this.backwardsTrie.clone();
         }

         if (this.forwardsPartialTrie != null) {
            other.forwardsPartialTrie = this.forwardsPartialTrie.clone();
         }

         return other;
      } catch (CloneNotSupportedException var3) {
         throw new ICUCloneNotSupportedException(var3);
      }
   }

   @Override
   public int first() {
      return this.delegate.first();
   }

   @Override
   public int preceding(int offset) {
      return this.internalPrev(this.delegate.preceding(offset));
   }

   @Override
   public int previous() {
      return this.internalPrev(this.delegate.previous());
   }

   @Override
   public int current() {
      return this.delegate.current();
   }

   @Override
   public boolean isBoundary(int offset) {
      if (!this.delegate.isBoundary(offset)) {
         return false;
      } else if (this.backwardsTrie == null) {
         return true;
      } else {
         this.resetState();
         return !this.breakExceptionAt(offset);
      }
   }

   @Override
   public int next() {
      return this.internalNext(this.delegate.next());
   }

   @Override
   public int next(int n) {
      return this.internalNext(this.delegate.next(n));
   }

   @Override
   public int following(int offset) {
      return this.internalNext(this.delegate.following(offset));
   }

   @Override
   public int last() {
      return this.delegate.last();
   }

   @Override
   public CharacterIterator getText() {
      return this.delegate.getText();
   }

   @Override
   public void setText(CharacterIterator newText) {
      this.delegate.setText(newText);
   }

   public static class Builder extends FilteredBreakIteratorBuilder {
      private HashSet<CharSequence> filterSet = new HashSet<>();
      static final int PARTIAL = 1;
      static final int MATCH = 2;
      static final int SuppressInReverse = 1;
      static final int AddToForward = 2;

      public Builder(Locale loc) {
         this(ULocale.forLocale(loc));
      }

      public Builder(ULocale loc) {
         ICUResourceBundle rb = ICUResourceBundle.getBundleInstance(
            "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b/brkitr", loc, ICUResourceBundle.OpenType.LOCALE_ROOT
         );
         ICUResourceBundle breaks = rb.findWithFallback("exceptions/SentenceBreak");
         if (breaks != null) {
            int index = 0;

            for (int size = breaks.getSize(); index < size; index++) {
               ICUResourceBundle b = (ICUResourceBundle)breaks.get(index);
               String br = b.getString();
               this.filterSet.add(br);
            }
         }
      }

      public Builder() {
      }

      @Override
      public boolean suppressBreakAfter(CharSequence str) {
         return this.filterSet.add(str);
      }

      @Override
      public boolean unsuppressBreakAfter(CharSequence str) {
         return this.filterSet.remove(str);
      }

      @Override
      public BreakIterator wrapIteratorWithFilter(BreakIterator adoptBreakIterator) {
         if (this.filterSet.isEmpty()) {
            return adoptBreakIterator;
         } else {
            CharsTrieBuilder builder = new CharsTrieBuilder();
            CharsTrieBuilder builder2 = new CharsTrieBuilder();
            int revCount = 0;
            int fwdCount = 0;
            int subCount = this.filterSet.size();
            CharSequence[] ustrs = new CharSequence[subCount];
            int[] partials = new int[subCount];
            CharsTrie backwardsTrie = null;
            CharsTrie forwardsPartialTrie = null;
            int i = 0;

            for (CharSequence s : this.filterSet) {
               ustrs[i] = s;
               partials[i] = 0;
               i++;
            }

            for (int var16 = 0; var16 < subCount; var16++) {
               String thisStr = ustrs[var16].toString();
               int nn = thisStr.indexOf(46);
               if (nn > -1 && nn + 1 != thisStr.length()) {
                  int sameAs = -1;

                  for (int j = 0; j < subCount; j++) {
                     if (j != var16 && thisStr.regionMatches(0, ustrs[j].toString(), 0, nn + 1)) {
                        if (partials[j] == 0) {
                           partials[j] = 3;
                        } else if ((partials[j] & 1) != 0) {
                           sameAs = j;
                        }
                     }
                  }

                  if (sameAs == -1 && partials[var16] == 0) {
                     StringBuilder prefix = new StringBuilder(thisStr.substring(0, nn + 1));
                     prefix.reverse();
                     builder.add(prefix, 1);
                     revCount++;
                     partials[var16] = 3;
                  }
               }
            }

            for (int var17 = 0; var17 < subCount; var17++) {
               String thisStr = ustrs[var17].toString();
               if (partials[var17] == 0) {
                  StringBuilder reversed = new StringBuilder(thisStr).reverse();
                  builder.add(reversed, 2);
                  revCount++;
               } else {
                  builder2.add(thisStr, 2);
                  fwdCount++;
               }
            }

            if (revCount > 0) {
               backwardsTrie = builder.build(StringTrieBuilder.Option.FAST);
            }

            if (fwdCount > 0) {
               forwardsPartialTrie = builder2.build(StringTrieBuilder.Option.FAST);
            }

            return new SimpleFilteredSentenceBreakIterator(adoptBreakIterator, forwardsPartialTrie, backwardsTrie);
         }
      }
   }
}
