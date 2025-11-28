package com.cobblemon.mod.relocations.ibm.icu.impl.coll;

import com.cobblemon.mod.relocations.ibm.icu.impl.Normalizer2Impl;
import com.cobblemon.mod.relocations.ibm.icu.impl.Trie2;
import com.cobblemon.mod.relocations.ibm.icu.impl.Utility;
import com.cobblemon.mod.relocations.ibm.icu.text.UnicodeSet;
import com.cobblemon.mod.relocations.ibm.icu.util.CharsTrie;
import java.util.Iterator;

public final class TailoredSet {
   private CollationData data;
   private CollationData baseData;
   private UnicodeSet tailored;
   private StringBuilder unreversedPrefix = new StringBuilder();
   private String suffix;

   public TailoredSet(UnicodeSet t) {
      this.tailored = t;
   }

   public void forData(CollationData d) {
      this.data = d;
      this.baseData = d.base;

      assert this.baseData != null;

      Iterator<Trie2.Range> trieIterator = this.data.trie.iterator();

      Trie2.Range range;
      while (trieIterator.hasNext() && !(range = trieIterator.next()).leadSurrogate) {
         this.enumTailoredRange(range.startCodePoint, range.endCodePoint, range.value, this);
      }
   }

   private void enumTailoredRange(int start, int end, int ce32, TailoredSet ts) {
      if (ce32 != 192) {
         ts.handleCE32(start, end, ce32);
      }
   }

   private void handleCE32(int start, int end, int ce32) {
      assert ce32 != 192;

      if (Collation.isSpecialCE32(ce32)) {
         ce32 = this.data.getIndirectCE32(ce32);
         if (ce32 == 192) {
            return;
         }
      }

      do {
         int baseCE32 = this.baseData.getFinalCE32(this.baseData.getCE32(start));
         if (!Collation.isSelfContainedCE32(ce32) || !Collation.isSelfContainedCE32(baseCE32)) {
            this.compare(start, ce32, baseCE32);
         } else if (ce32 != baseCE32) {
            this.tailored.add(start);
         }
      } while (++start <= end);
   }

   private void compare(int c, int ce32, int baseCE32) {
      if (Collation.isPrefixCE32(ce32)) {
         int dataIndex = Collation.indexFromCE32(ce32);
         ce32 = this.data.getFinalCE32(this.data.getCE32FromContexts(dataIndex));
         if (Collation.isPrefixCE32(baseCE32)) {
            int baseIndex = Collation.indexFromCE32(baseCE32);
            baseCE32 = this.baseData.getFinalCE32(this.baseData.getCE32FromContexts(baseIndex));
            this.comparePrefixes(c, this.data.contexts, dataIndex + 2, this.baseData.contexts, baseIndex + 2);
         } else {
            this.addPrefixes(this.data, c, this.data.contexts, dataIndex + 2);
         }
      } else if (Collation.isPrefixCE32(baseCE32)) {
         int baseIndex = Collation.indexFromCE32(baseCE32);
         baseCE32 = this.baseData.getFinalCE32(this.baseData.getCE32FromContexts(baseIndex));
         this.addPrefixes(this.baseData, c, this.baseData.contexts, baseIndex + 2);
      }

      if (Collation.isContractionCE32(ce32)) {
         int dataIndex = Collation.indexFromCE32(ce32);
         if ((ce32 & 256) != 0) {
            ce32 = 1;
         } else {
            ce32 = this.data.getFinalCE32(this.data.getCE32FromContexts(dataIndex));
         }

         if (Collation.isContractionCE32(baseCE32)) {
            int baseIndex = Collation.indexFromCE32(baseCE32);
            if ((baseCE32 & 256) != 0) {
               baseCE32 = 1;
            } else {
               baseCE32 = this.baseData.getFinalCE32(this.baseData.getCE32FromContexts(baseIndex));
            }

            this.compareContractions(c, this.data.contexts, dataIndex + 2, this.baseData.contexts, baseIndex + 2);
         } else {
            this.addContractions(c, this.data.contexts, dataIndex + 2);
         }
      } else if (Collation.isContractionCE32(baseCE32)) {
         int baseIndex = Collation.indexFromCE32(baseCE32);
         baseCE32 = this.baseData.getFinalCE32(this.baseData.getCE32FromContexts(baseIndex));
         this.addContractions(c, this.baseData.contexts, baseIndex + 2);
      }

      int tag;
      if (Collation.isSpecialCE32(ce32)) {
         tag = Collation.tagFromCE32(ce32);

         assert tag != 8;

         assert tag != 9;

         assert tag != 14;
      } else {
         tag = -1;
      }

      int baseTag;
      if (Collation.isSpecialCE32(baseCE32)) {
         baseTag = Collation.tagFromCE32(baseCE32);

         assert baseTag != 8;

         assert baseTag != 9;
      } else {
         baseTag = -1;
      }

      if (baseTag == 14) {
         if (!Collation.isLongPrimaryCE32(ce32)) {
            this.add(c);
            return;
         }

         long dataCE = this.baseData.ces[Collation.indexFromCE32(baseCE32)];
         long p = Collation.getThreeBytePrimaryForOffsetData(c, dataCE);
         if (Collation.primaryFromLongPrimaryCE32(ce32) != p) {
            this.add(c);
            return;
         }
      }

      if (tag != baseTag) {
         this.add(c);
      } else {
         if (tag == 5) {
            int length = Collation.lengthFromCE32(ce32);
            int baseLength = Collation.lengthFromCE32(baseCE32);
            if (length != baseLength) {
               this.add(c);
               return;
            }

            int idx0 = Collation.indexFromCE32(ce32);
            int idx1 = Collation.indexFromCE32(baseCE32);

            for (int i = 0; i < length; i++) {
               if (this.data.ce32s[idx0 + i] != this.baseData.ce32s[idx1 + i]) {
                  this.add(c);
                  break;
               }
            }
         } else if (tag == 6) {
            int length = Collation.lengthFromCE32(ce32);
            int baseLength = Collation.lengthFromCE32(baseCE32);
            if (length != baseLength) {
               this.add(c);
               return;
            }

            int idx0 = Collation.indexFromCE32(ce32);
            int idx1 = Collation.indexFromCE32(baseCE32);

            for (int ix = 0; ix < length; ix++) {
               if (this.data.ces[idx0 + ix] != this.baseData.ces[idx1 + ix]) {
                  this.add(c);
                  break;
               }
            }
         } else if (tag == 12) {
            StringBuilder jamos = new StringBuilder();
            int length = Normalizer2Impl.Hangul.decompose(c, jamos);
            if (this.tailored.contains(jamos.charAt(0)) || this.tailored.contains(jamos.charAt(1)) || length == 3 && this.tailored.contains(jamos.charAt(2))) {
               this.add(c);
            }
         } else if (ce32 != baseCE32) {
            this.add(c);
         }
      }
   }

   private void comparePrefixes(int c, CharSequence p, int pidx, CharSequence q, int qidx) {
      CharsTrie.Iterator prefixes = new CharsTrie(p, pidx).iterator();
      CharsTrie.Iterator basePrefixes = new CharsTrie(q, qidx).iterator();
      String tp = null;
      String bp = null;
      String none = "\uffff";
      CharsTrie.Entry te = null;
      CharsTrie.Entry be = null;

      while (true) {
         if (tp == null) {
            if (prefixes.hasNext()) {
               te = prefixes.next();
               tp = te.chars.toString();
            } else {
               te = null;
               tp = none;
            }
         }

         if (bp == null) {
            if (basePrefixes.hasNext()) {
               be = basePrefixes.next();
               bp = be.chars.toString();
            } else {
               be = null;
               bp = none;
            }
         }

         if (Utility.sameObjects(tp, none) && Utility.sameObjects(bp, none)) {
            return;
         }

         int cmp = tp.compareTo(bp);
         if (cmp >= 0) {
            if (cmp <= 0) {
               this.setPrefix(tp);

               assert te != null && be != null;

               this.compare(c, te.value, be.value);
               this.resetPrefix();
               be = null;
               te = null;
               bp = null;
               tp = null;
            } else {
               assert be != null;

               this.addPrefix(this.baseData, bp, c, be.value);
               be = null;
               bp = null;
            }
         } else {
            assert te != null;

            this.addPrefix(this.data, tp, c, te.value);
            te = null;
            tp = null;
         }
      }
   }

   private void compareContractions(int c, CharSequence p, int pidx, CharSequence q, int qidx) {
      CharsTrie.Iterator suffixes = new CharsTrie(p, pidx).iterator();
      CharsTrie.Iterator baseSuffixes = new CharsTrie(q, qidx).iterator();
      String ts = null;
      String bs = null;
      String none = "\uffff\uffff";
      CharsTrie.Entry te = null;
      CharsTrie.Entry be = null;

      while (true) {
         if (ts == null) {
            if (suffixes.hasNext()) {
               te = suffixes.next();
               ts = te.chars.toString();
            } else {
               te = null;
               ts = none;
            }
         }

         if (bs == null) {
            if (baseSuffixes.hasNext()) {
               be = baseSuffixes.next();
               bs = be.chars.toString();
            } else {
               be = null;
               bs = none;
            }
         }

         if (Utility.sameObjects(ts, none) && Utility.sameObjects(bs, none)) {
            return;
         }

         int cmp = ts.compareTo(bs);
         if (cmp < 0) {
            this.addSuffix(c, ts);
            te = null;
            ts = null;
         } else if (cmp > 0) {
            this.addSuffix(c, bs);
            be = null;
            bs = null;
         } else {
            this.suffix = ts;
            this.compare(c, te.value, be.value);
            this.suffix = null;
            be = null;
            te = null;
            bs = null;
            ts = null;
         }
      }
   }

   private void addPrefixes(CollationData d, int c, CharSequence p, int pidx) {
      CharsTrie.Iterator prefixes = new CharsTrie(p, pidx).iterator();

      while (prefixes.hasNext()) {
         CharsTrie.Entry e = prefixes.next();
         this.addPrefix(d, e.chars, c, e.value);
      }
   }

   private void addPrefix(CollationData d, CharSequence pfx, int c, int ce32) {
      this.setPrefix(pfx);
      ce32 = d.getFinalCE32(ce32);
      if (Collation.isContractionCE32(ce32)) {
         int idx = Collation.indexFromCE32(ce32);
         this.addContractions(c, d.contexts, idx + 2);
      }

      this.tailored.add(new StringBuilder(this.unreversedPrefix.appendCodePoint(c)));
      this.resetPrefix();
   }

   private void addContractions(int c, CharSequence p, int pidx) {
      CharsTrie.Iterator suffixes = new CharsTrie(p, pidx).iterator();

      while (suffixes.hasNext()) {
         CharsTrie.Entry e = suffixes.next();
         this.addSuffix(c, e.chars);
      }
   }

   private void addSuffix(int c, CharSequence sfx) {
      this.tailored.add(new StringBuilder(this.unreversedPrefix).appendCodePoint(c).append(sfx));
   }

   private void add(int c) {
      if (this.unreversedPrefix.length() == 0 && this.suffix == null) {
         this.tailored.add(c);
      } else {
         StringBuilder s = new StringBuilder(this.unreversedPrefix);
         s.appendCodePoint(c);
         if (this.suffix != null) {
            s.append(this.suffix);
         }

         this.tailored.add(s);
      }
   }

   private void setPrefix(CharSequence pfx) {
      this.unreversedPrefix.setLength(0);
      this.unreversedPrefix.append(pfx).reverse();
   }

   private void resetPrefix() {
      this.unreversedPrefix.setLength(0);
   }
}
