package com.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.lang.UCharacter;
import com.cobblemon.mod.relocations.ibm.icu.util.LocaleData;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public final class AlphabeticIndex<V> implements Iterable<AlphabeticIndex.Bucket<V>> {
   private static final String BASE = "\ufdd0";
   private static final char CGJ = '͏';
   private static final Comparator<String> binaryCmp = new UTF16.StringComparator(true, false, 0);
   private final RuleBasedCollator collatorOriginal;
   private final RuleBasedCollator collatorPrimaryOnly;
   private RuleBasedCollator collatorExternal;
   private final Comparator<AlphabeticIndex.Record<V>> recordComparator = new Comparator<AlphabeticIndex.Record<V>>() {
      public int compare(AlphabeticIndex.Record<V> o1, AlphabeticIndex.Record<V> o2) {
         return AlphabeticIndex.this.collatorOriginal.compare(o1.name, o2.name);
      }
   };
   private final List<String> firstCharsInScripts;
   private final UnicodeSet initialLabels = new UnicodeSet();
   private List<AlphabeticIndex.Record<V>> inputList;
   private AlphabeticIndex.BucketList<V> buckets;
   private String overflowLabel = "…";
   private String underflowLabel = "…";
   private String inflowLabel = "…";
   private int maxLabelCount = 99;
   private static final int GC_LU_MASK = 2;
   private static final int GC_LL_MASK = 4;
   private static final int GC_LT_MASK = 8;
   private static final int GC_LM_MASK = 16;
   private static final int GC_LO_MASK = 32;
   private static final int GC_L_MASK = 62;
   private static final int GC_CN_MASK = 1;

   public AlphabeticIndex(ULocale locale) {
      this(locale, null);
   }

   public AlphabeticIndex(Locale locale) {
      this(ULocale.forLocale(locale), null);
   }

   public AlphabeticIndex(RuleBasedCollator collator) {
      this(null, collator);
   }

   private AlphabeticIndex(ULocale locale, RuleBasedCollator collator) {
      this.collatorOriginal = collator != null ? collator : (RuleBasedCollator)Collator.getInstance(locale);

      try {
         this.collatorPrimaryOnly = this.collatorOriginal.cloneAsThawed();
      } catch (Exception var4) {
         throw new IllegalStateException("Collator cannot be cloned", var4);
      }

      this.collatorPrimaryOnly.setStrength(0);
      this.collatorPrimaryOnly.freeze();
      this.firstCharsInScripts = this.getFirstCharactersInScripts();
      Collections.sort(this.firstCharsInScripts, this.collatorPrimaryOnly);

      while (!this.firstCharsInScripts.isEmpty()) {
         if (this.collatorPrimaryOnly.compare(this.firstCharsInScripts.get(0), "") != 0) {
            if (!this.addChineseIndexCharacters() && locale != null) {
               this.addIndexExemplars(locale);
            }

            return;
         }

         this.firstCharsInScripts.remove(0);
      }

      throw new IllegalArgumentException("AlphabeticIndex requires some non-ignorable script boundary strings");
   }

   public AlphabeticIndex<V> addLabels(UnicodeSet additions) {
      this.initialLabels.addAll(additions);
      this.buckets = null;
      return this;
   }

   public AlphabeticIndex<V> addLabels(ULocale... additions) {
      for (ULocale addition : additions) {
         this.addIndexExemplars(addition);
      }

      this.buckets = null;
      return this;
   }

   public AlphabeticIndex<V> addLabels(Locale... additions) {
      for (Locale addition : additions) {
         this.addIndexExemplars(ULocale.forLocale(addition));
      }

      this.buckets = null;
      return this;
   }

   public AlphabeticIndex<V> setOverflowLabel(String overflowLabel) {
      this.overflowLabel = overflowLabel;
      this.buckets = null;
      return this;
   }

   public String getUnderflowLabel() {
      return this.underflowLabel;
   }

   public AlphabeticIndex<V> setUnderflowLabel(String underflowLabel) {
      this.underflowLabel = underflowLabel;
      this.buckets = null;
      return this;
   }

   public String getOverflowLabel() {
      return this.overflowLabel;
   }

   public AlphabeticIndex<V> setInflowLabel(String inflowLabel) {
      this.inflowLabel = inflowLabel;
      this.buckets = null;
      return this;
   }

   public String getInflowLabel() {
      return this.inflowLabel;
   }

   public int getMaxLabelCount() {
      return this.maxLabelCount;
   }

   public AlphabeticIndex<V> setMaxLabelCount(int maxLabelCount) {
      this.maxLabelCount = maxLabelCount;
      this.buckets = null;
      return this;
   }

   private List<String> initLabels() {
      Normalizer2 nfkdNormalizer = Normalizer2.getNFKDInstance();
      List<String> indexCharacters = new ArrayList<>();
      String firstScriptBoundary = this.firstCharsInScripts.get(0);
      String overflowBoundary = this.firstCharsInScripts.get(this.firstCharsInScripts.size() - 1);

      for (String item : this.initialLabels) {
         boolean checkDistinct;
         if (!UTF16.hasMoreCodePointsThan(item, 1)) {
            checkDistinct = false;
         } else if (item.charAt(item.length() - 1) == '*' && item.charAt(item.length() - 2) != '*') {
            item = item.substring(0, item.length() - 1);
            checkDistinct = false;
         } else {
            checkDistinct = true;
         }

         if (this.collatorPrimaryOnly.compare(item, firstScriptBoundary) >= 0
            && this.collatorPrimaryOnly.compare(item, overflowBoundary) < 0
            && (!checkDistinct || this.collatorPrimaryOnly.compare(item, this.separated(item)) != 0)) {
            int insertionPoint = Collections.binarySearch(indexCharacters, item, this.collatorPrimaryOnly);
            if (insertionPoint < 0) {
               indexCharacters.add(~insertionPoint, item);
            } else {
               String itemAlreadyIn = indexCharacters.get(insertionPoint);
               if (isOneLabelBetterThanOther(nfkdNormalizer, item, itemAlreadyIn)) {
                  indexCharacters.set(insertionPoint, item);
               }
            }
         }
      }

      int size = indexCharacters.size() - 1;
      if (size > this.maxLabelCount) {
         int count = 0;
         int old = -1;
         Iterator<String> it = indexCharacters.iterator();

         while (it.hasNext()) {
            count++;
            it.next();
            int bump = count * this.maxLabelCount / size;
            if (bump == old) {
               it.remove();
            } else {
               old = bump;
            }
         }
      }

      return indexCharacters;
   }

   private static String fixLabel(String current) {
      if (!current.startsWith("\ufdd0")) {
         return current;
      } else {
         int rest = current.charAt("\ufdd0".length());
         return 10240 < rest && rest <= 10495 ? rest - 10240 + "劃" : current.substring("\ufdd0".length());
      }
   }

   private void addIndexExemplars(ULocale locale) {
      UnicodeSet exemplars = LocaleData.getExemplarSet(locale, 0, 2);
      if (exemplars != null && !exemplars.isEmpty()) {
         this.initialLabels.addAll(exemplars);
      } else {
         exemplars = LocaleData.getExemplarSet(locale, 0, 0);
         exemplars = exemplars.cloneAsThawed();
         if (exemplars.containsSome(97, 122) || exemplars.isEmpty()) {
            exemplars.addAll(97, 122);
         }

         if (exemplars.containsSome(44032, 55203)) {
            exemplars.remove(44032, 55203)
               .add(44032)
               .add(45208)
               .add(45796)
               .add(46972)
               .add(47560)
               .add(48148)
               .add(49324)
               .add(50500)
               .add(51088)
               .add(52264)
               .add(52852)
               .add(53440)
               .add(54028)
               .add(54616);
         }

         if (exemplars.containsSome(4608, 4991)) {
            UnicodeSet ethiopic = new UnicodeSet("[ሀለሐመሠረሰሸቀቈቐቘበቨተቸኀኈነኘአከኰኸዀወዐዘዠየደዸጀገጐጘጠጨጰጸፀፈፐፘ]");
            ethiopic.retainAll(exemplars);
            exemplars.remove(4608, 4991).addAll(ethiopic);
         }

         for (String item : exemplars) {
            this.initialLabels.add(UCharacter.toUpperCase(locale, item));
         }
      }
   }

   private boolean addChineseIndexCharacters() {
      UnicodeSet contractions = new UnicodeSet();

      try {
         this.collatorPrimaryOnly.internalAddContractions("\ufdd0".charAt(0), contractions);
      } catch (Exception var5) {
         return false;
      }

      if (contractions.isEmpty()) {
         return false;
      } else {
         this.initialLabels.addAll(contractions);

         for (String s : contractions) {
            assert s.startsWith("\ufdd0");

            char c = s.charAt(s.length() - 1);
            if ('A' <= c && c <= 'Z') {
               this.initialLabels.add(65, 90);
               break;
            }
         }

         return true;
      }
   }

   private String separated(String item) {
      StringBuilder result = new StringBuilder();
      char last = item.charAt(0);
      result.append(last);

      for (int i = 1; i < item.length(); i++) {
         char ch = item.charAt(i);
         if (!UCharacter.isHighSurrogate(last) || !UCharacter.isLowSurrogate(ch)) {
            result.append('͏');
         }

         result.append(ch);
         last = ch;
      }

      return result.toString();
   }

   public AlphabeticIndex.ImmutableIndex<V> buildImmutableIndex() {
      AlphabeticIndex.BucketList<V> immutableBucketList;
      if (this.inputList != null && !this.inputList.isEmpty()) {
         immutableBucketList = this.createBucketList();
      } else {
         if (this.buckets == null) {
            this.buckets = this.createBucketList();
         }

         immutableBucketList = this.buckets;
      }

      return new AlphabeticIndex.ImmutableIndex<>(immutableBucketList, this.collatorPrimaryOnly);
   }

   public List<String> getBucketLabels() {
      this.initBuckets();
      ArrayList<String> result = new ArrayList<>();

      for (AlphabeticIndex.Bucket<V> bucket : this.buckets) {
         result.add(bucket.getLabel());
      }

      return result;
   }

   public RuleBasedCollator getCollator() {
      if (this.collatorExternal == null) {
         try {
            this.collatorExternal = (RuleBasedCollator)this.collatorOriginal.clone();
         } catch (Exception var2) {
            throw new IllegalStateException("Collator cannot be cloned", var2);
         }
      }

      return this.collatorExternal;
   }

   public AlphabeticIndex<V> addRecord(CharSequence name, V data) {
      this.buckets = null;
      if (this.inputList == null) {
         this.inputList = new ArrayList<>();
      }

      this.inputList.add(new AlphabeticIndex.Record<>(name, data));
      return this;
   }

   public int getBucketIndex(CharSequence name) {
      this.initBuckets();
      return this.buckets.getBucketIndex(name, this.collatorPrimaryOnly);
   }

   public AlphabeticIndex<V> clearRecords() {
      if (this.inputList != null && !this.inputList.isEmpty()) {
         this.inputList.clear();
         this.buckets = null;
      }

      return this;
   }

   public int getBucketCount() {
      this.initBuckets();
      return this.buckets.getBucketCount();
   }

   public int getRecordCount() {
      return this.inputList != null ? this.inputList.size() : 0;
   }

   @Override
   public Iterator<AlphabeticIndex.Bucket<V>> iterator() {
      this.initBuckets();
      return this.buckets.iterator();
   }

   private void initBuckets() {
      if (this.buckets == null) {
         this.buckets = this.createBucketList();
         if (this.inputList != null && !this.inputList.isEmpty()) {
            Collections.sort(this.inputList, this.recordComparator);
            Iterator<AlphabeticIndex.Bucket<V>> bucketIterator = this.buckets.fullIterator();
            AlphabeticIndex.Bucket<V> currentBucket = bucketIterator.next();
            AlphabeticIndex.Bucket<V> nextBucket;
            String upperBoundary;
            if (bucketIterator.hasNext()) {
               nextBucket = bucketIterator.next();
               upperBoundary = nextBucket.lowerBoundary;
            } else {
               nextBucket = null;
               upperBoundary = null;
            }

            for (AlphabeticIndex.Record<V> r : this.inputList) {
               while (upperBoundary != null && this.collatorPrimaryOnly.compare(r.name, upperBoundary) >= 0) {
                  currentBucket = nextBucket;
                  if (bucketIterator.hasNext()) {
                     nextBucket = bucketIterator.next();
                     upperBoundary = nextBucket.lowerBoundary;
                  } else {
                     upperBoundary = null;
                  }
               }

               AlphabeticIndex.Bucket<V> bucket = currentBucket;
               if (currentBucket.displayBucket != null) {
                  bucket = currentBucket.displayBucket;
               }

               if (bucket.records == null) {
                  bucket.records = new ArrayList<>();
               }

               bucket.records.add(r);
            }
         }
      }
   }

   private static boolean isOneLabelBetterThanOther(Normalizer2 nfkdNormalizer, String one, String other) {
      String n1 = nfkdNormalizer.normalize(one);
      String n2 = nfkdNormalizer.normalize(other);
      int result = n1.codePointCount(0, n1.length()) - n2.codePointCount(0, n2.length());
      if (result != 0) {
         return result < 0;
      } else {
         result = binaryCmp.compare(n1, n2);
         return result != 0 ? result < 0 : binaryCmp.compare(one, other) < 0;
      }
   }

   private AlphabeticIndex.BucketList<V> createBucketList() {
      List<String> indexCharacters = this.initLabels();
      long variableTop;
      if (this.collatorPrimaryOnly.isAlternateHandlingShifted()) {
         variableTop = this.collatorPrimaryOnly.getVariableTop() & 4294967295L;
      } else {
         variableTop = 0L;
      }

      boolean hasInvisibleBuckets = false;
      AlphabeticIndex.Bucket<V>[] asciiBuckets = new AlphabeticIndex.Bucket[26];
      AlphabeticIndex.Bucket<V>[] pinyinBuckets = new AlphabeticIndex.Bucket[26];
      boolean hasPinyin = false;
      ArrayList<AlphabeticIndex.Bucket<V>> bucketList = new ArrayList<>();
      bucketList.add(new AlphabeticIndex.Bucket<>(this.getUnderflowLabel(), "", AlphabeticIndex.Bucket.LabelType.UNDERFLOW));
      int scriptIndex = -1;
      String scriptUpperBoundary = "";

      for (String current : indexCharacters) {
         if (this.collatorPrimaryOnly.compare(current, scriptUpperBoundary) >= 0) {
            String inflowBoundary = scriptUpperBoundary;
            boolean skippedScript = false;

            while (true) {
               scriptUpperBoundary = this.firstCharsInScripts.get(++scriptIndex);
               if (this.collatorPrimaryOnly.compare(current, scriptUpperBoundary) < 0) {
                  if (skippedScript && bucketList.size() > 1) {
                     bucketList.add(new AlphabeticIndex.Bucket<>(this.getInflowLabel(), inflowBoundary, AlphabeticIndex.Bucket.LabelType.INFLOW));
                  }
                  break;
               }

               skippedScript = true;
            }
         }

         AlphabeticIndex.Bucket<V> bucket = new AlphabeticIndex.Bucket<>(fixLabel(current), current, AlphabeticIndex.Bucket.LabelType.NORMAL);
         bucketList.add(bucket);
         char c;
         if (current.length() == 1 && 'A' <= (c = current.charAt(0)) && c <= 'Z') {
            asciiBuckets[c - 'A'] = bucket;
         } else if (current.length() == "\ufdd0".length() + 1 && current.startsWith("\ufdd0") && 'A' <= (c = current.charAt("\ufdd0".length())) && c <= 'Z') {
            pinyinBuckets[c - 'A'] = bucket;
            hasPinyin = true;
         }

         if (!current.startsWith("\ufdd0") && hasMultiplePrimaryWeights(this.collatorPrimaryOnly, variableTop, current) && !current.endsWith("\uffff")) {
            int i = bucketList.size() - 2;

            while (true) {
               AlphabeticIndex.Bucket<V> singleBucket = bucketList.get(i);
               if (singleBucket.labelType != AlphabeticIndex.Bucket.LabelType.NORMAL) {
                  break;
               }

               if (singleBucket.displayBucket == null && !hasMultiplePrimaryWeights(this.collatorPrimaryOnly, variableTop, singleBucket.lowerBoundary)) {
                  bucket = new AlphabeticIndex.Bucket<>("", current + "\uffff", AlphabeticIndex.Bucket.LabelType.NORMAL);
                  bucket.displayBucket = singleBucket;
                  bucketList.add(bucket);
                  hasInvisibleBuckets = true;
                  break;
               }

               i--;
            }
         }
      }

      if (bucketList.size() == 1) {
         return new AlphabeticIndex.BucketList<>(bucketList, bucketList);
      } else {
         bucketList.add(new AlphabeticIndex.Bucket<>(this.getOverflowLabel(), scriptUpperBoundary, AlphabeticIndex.Bucket.LabelType.OVERFLOW));
         if (hasPinyin) {
            AlphabeticIndex.Bucket<V> asciiBucket = null;

            for (int i = 0; i < 26; i++) {
               if (asciiBuckets[i] != null) {
                  asciiBucket = asciiBuckets[i];
               }

               if (pinyinBuckets[i] != null && asciiBucket != null) {
                  pinyinBuckets[i].displayBucket = asciiBucket;
                  hasInvisibleBuckets = true;
               }
            }
         }

         if (!hasInvisibleBuckets) {
            return new AlphabeticIndex.BucketList<>(bucketList, bucketList);
         } else {
            int i = bucketList.size() - 1;
            AlphabeticIndex.Bucket<V> nextBucket = bucketList.get(i);

            while (--i > 0) {
               AlphabeticIndex.Bucket<V> bucketx = bucketList.get(i);
               if (bucketx.displayBucket == null) {
                  if (bucketx.labelType == AlphabeticIndex.Bucket.LabelType.INFLOW && nextBucket.labelType != AlphabeticIndex.Bucket.LabelType.NORMAL) {
                     bucketx.displayBucket = nextBucket;
                  } else {
                     nextBucket = bucketx;
                  }
               }
            }

            ArrayList<AlphabeticIndex.Bucket<V>> publicBucketList = new ArrayList<>();

            for (AlphabeticIndex.Bucket<V> bucketx : bucketList) {
               if (bucketx.displayBucket == null) {
                  publicBucketList.add(bucketx);
               }
            }

            return new AlphabeticIndex.BucketList<>(bucketList, publicBucketList);
         }
      }
   }

   private static boolean hasMultiplePrimaryWeights(RuleBasedCollator coll, long variableTop, String s) {
      long[] ces = coll.internalGetCEs(s);
      boolean seenPrimary = false;

      for (int i = 0; i < ces.length; i++) {
         long ce = ces[i];
         long p = ce >>> 32;
         if (p > variableTop) {
            if (seenPrimary) {
               return true;
            }

            seenPrimary = true;
         }
      }

      return false;
   }

   @Deprecated
   public List<String> getFirstCharactersInScripts() {
      List<String> dest = new ArrayList<>(200);
      UnicodeSet set = new UnicodeSet();
      this.collatorPrimaryOnly.internalAddContractions(64977, set);
      if (set.isEmpty()) {
         throw new UnsupportedOperationException("AlphabeticIndex requires script-first-primary contractions");
      } else {
         for (String boundary : set) {
            int gcMask = 1 << UCharacter.getType(boundary.codePointAt(1));
            if ((gcMask & 63) != 0) {
               dest.add(boundary);
            }
         }

         return dest;
      }
   }

   public static class Bucket<V> implements Iterable<AlphabeticIndex.Record<V>> {
      private final String label;
      private final String lowerBoundary;
      private final AlphabeticIndex.Bucket.LabelType labelType;
      private AlphabeticIndex.Bucket<V> displayBucket;
      private int displayIndex;
      private List<AlphabeticIndex.Record<V>> records;

      private Bucket(String label, String lowerBoundary, AlphabeticIndex.Bucket.LabelType labelType) {
         this.label = label;
         this.lowerBoundary = lowerBoundary;
         this.labelType = labelType;
      }

      public String getLabel() {
         return this.label;
      }

      public AlphabeticIndex.Bucket.LabelType getLabelType() {
         return this.labelType;
      }

      public int size() {
         return this.records == null ? 0 : this.records.size();
      }

      @Override
      public Iterator<AlphabeticIndex.Record<V>> iterator() {
         return this.records == null ? Collections.<AlphabeticIndex.Record<V>>emptyList().iterator() : this.records.iterator();
      }

      @Override
      public String toString() {
         return "{labelType=" + this.labelType + ", lowerBoundary=" + this.lowerBoundary + ", label=" + this.label + "}";
      }

      public static enum LabelType {
         NORMAL,
         UNDERFLOW,
         INFLOW,
         OVERFLOW;
      }
   }

   private static class BucketList<V> implements Iterable<AlphabeticIndex.Bucket<V>> {
      private final ArrayList<AlphabeticIndex.Bucket<V>> bucketList;
      private final List<AlphabeticIndex.Bucket<V>> immutableVisibleList;

      private BucketList(ArrayList<AlphabeticIndex.Bucket<V>> bucketList, ArrayList<AlphabeticIndex.Bucket<V>> publicBucketList) {
         this.bucketList = bucketList;
         int displayIndex = 0;

         for (AlphabeticIndex.Bucket<V> bucket : publicBucketList) {
            bucket.displayIndex = displayIndex++;
         }

         this.immutableVisibleList = Collections.unmodifiableList(publicBucketList);
      }

      private int getBucketCount() {
         return this.immutableVisibleList.size();
      }

      private int getBucketIndex(CharSequence name, Collator collatorPrimaryOnly) {
         int start = 0;
         int limit = this.bucketList.size();

         while (start + 1 < limit) {
            int i = (start + limit) / 2;
            AlphabeticIndex.Bucket<V> bucket = this.bucketList.get(i);
            int nameVsBucket = collatorPrimaryOnly.compare(name, bucket.lowerBoundary);
            if (nameVsBucket < 0) {
               limit = i;
            } else {
               start = i;
            }
         }

         AlphabeticIndex.Bucket<V> bucket = this.bucketList.get(start);
         if (bucket.displayBucket != null) {
            bucket = bucket.displayBucket;
         }

         return bucket.displayIndex;
      }

      private Iterator<AlphabeticIndex.Bucket<V>> fullIterator() {
         return this.bucketList.iterator();
      }

      @Override
      public Iterator<AlphabeticIndex.Bucket<V>> iterator() {
         return this.immutableVisibleList.iterator();
      }
   }

   public static final class ImmutableIndex<V> implements Iterable<AlphabeticIndex.Bucket<V>> {
      private final AlphabeticIndex.BucketList<V> buckets;
      private final Collator collatorPrimaryOnly;

      private ImmutableIndex(AlphabeticIndex.BucketList<V> bucketList, Collator collatorPrimaryOnly) {
         this.buckets = bucketList;
         this.collatorPrimaryOnly = collatorPrimaryOnly;
      }

      public int getBucketCount() {
         return this.buckets.getBucketCount();
      }

      public int getBucketIndex(CharSequence name) {
         return this.buckets.getBucketIndex(name, this.collatorPrimaryOnly);
      }

      public AlphabeticIndex.Bucket<V> getBucket(int index) {
         return 0 <= index && index < this.buckets.getBucketCount() ? this.buckets.immutableVisibleList.get(index) : null;
      }

      @Override
      public Iterator<AlphabeticIndex.Bucket<V>> iterator() {
         return this.buckets.iterator();
      }
   }

   public static class Record<V> {
      private final CharSequence name;
      private final V data;

      private Record(CharSequence name, V data) {
         this.name = name;
         this.data = data;
      }

      public CharSequence getName() {
         return this.name;
      }

      public V getData() {
         return this.data;
      }

      @Override
      public String toString() {
         return this.name + "=" + this.data;
      }
   }
}
