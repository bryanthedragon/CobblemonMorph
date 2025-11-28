package com.cobblemon.mod.relocations.ibm.icu.impl.locale;

import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.impl.UResource;
import com.cobblemon.mod.relocations.ibm.icu.util.BytesTrie;
import com.cobblemon.mod.relocations.ibm.icu.util.LocaleMatcher;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

public class LocaleDistance {
   public static final int END_OF_SUBTAG = 128;
   public static final int DISTANCE_SKIP_SCRIPT = 128;
   private static final int DISTANCE_IS_FINAL = 256;
   private static final int DISTANCE_IS_FINAL_OR_SKIP_SCRIPT = 384;
   private static final int DISTANCE_SHIFT = 3;
   private static final int DISTANCE_FRACTION_MASK = 7;
   private static final int DISTANCE_INT_SHIFT = 7;
   private static final int INDEX_SHIFT = 10;
   private static final int DISTANCE_MASK = 1023;
   private static final int INDEX_NEG_1 = -1024;
   public static final int IX_DEF_LANG_DISTANCE = 0;
   public static final int IX_DEF_SCRIPT_DISTANCE = 1;
   public static final int IX_DEF_REGION_DISTANCE = 2;
   public static final int IX_MIN_REGION_DISTANCE = 3;
   public static final int IX_LIMIT = 4;
   private static final int ABOVE_THRESHOLD = 100;
   private static final boolean DEBUG_OUTPUT = false;
   private final BytesTrie trie;
   private final byte[] regionToPartitionsIndex;
   private final String[] partitionArrays;
   private final Set<LSR> paradigmLSRs;
   private final int defaultLanguageDistance;
   private final int defaultScriptDistance;
   private final int defaultRegionDistance;
   private final int minRegionDistance;
   private final int defaultDemotionPerDesiredLocale;
   public static final LocaleDistance INSTANCE = new LocaleDistance(LocaleDistance.Data.load());

   public static final int shiftDistance(int distance) {
      return distance << 3;
   }

   public static final int getShiftedDistance(int indexAndDistance) {
      return indexAndDistance & 1023;
   }

   public static final double getDistanceDouble(int indexAndDistance) {
      double shiftedDistance = getShiftedDistance(indexAndDistance);
      return shiftedDistance / 8.0;
   }

   public static final int getDistanceFloor(int indexAndDistance) {
      return (indexAndDistance & 1023) >> 3;
   }

   public static final int getIndex(int indexAndDistance) {
      assert indexAndDistance >= 0;

      return indexAndDistance >> 10;
   }

   private LocaleDistance(LocaleDistance.Data data) {
      this.trie = new BytesTrie(data.trie, 0);
      this.regionToPartitionsIndex = data.regionToPartitionsIndex;
      this.partitionArrays = data.partitionArrays;
      this.paradigmLSRs = data.paradigmLSRs;
      this.defaultLanguageDistance = data.distances[0];
      this.defaultScriptDistance = data.distances[1];
      this.defaultRegionDistance = data.distances[2];
      this.minRegionDistance = data.distances[3];
      LSR en = new LSR("en", "Latn", "US", 7);
      LSR enGB = new LSR("en", "Latn", "GB", 7);
      int indexAndDistance = this.getBestIndexAndDistance(
         en, new LSR[]{enGB}, 1, shiftDistance(50), LocaleMatcher.FavorSubtag.LANGUAGE, LocaleMatcher.Direction.WITH_ONE_WAY
      );
      this.defaultDemotionPerDesiredLocale = getDistanceFloor(indexAndDistance);
   }

   public int testOnlyDistance(ULocale desired, ULocale supported, int threshold, LocaleMatcher.FavorSubtag favorSubtag) {
      LSR supportedLSR = XLikelySubtags.INSTANCE.makeMaximizedLsrFrom(supported);
      LSR desiredLSR = XLikelySubtags.INSTANCE.makeMaximizedLsrFrom(desired);
      int indexAndDistance = this.getBestIndexAndDistance(
         desiredLSR, new LSR[]{supportedLSR}, 1, shiftDistance(threshold), favorSubtag, LocaleMatcher.Direction.WITH_ONE_WAY
      );
      return getDistanceFloor(indexAndDistance);
   }

   public int getBestIndexAndDistance(
      LSR desired, LSR[] supportedLSRs, int supportedLSRsLength, int shiftedThreshold, LocaleMatcher.FavorSubtag favorSubtag, LocaleMatcher.Direction direction
   ) {
      BytesTrie iter = new BytesTrie(this.trie);
      int desLangDistance = trieNext(iter, desired.language, false);
      long desLangState = desLangDistance >= 0 && supportedLSRsLength > 1 ? iter.getState64() : 0L;
      int bestIndex = -1;
      int bestLikelyInfo = -1;

      for (int slIndex = 0; slIndex < supportedLSRsLength; slIndex++) {
         LSR supported = supportedLSRs[slIndex];
         boolean star = false;
         int distance = desLangDistance;
         if (desLangDistance >= 0) {
            assert (desLangDistance & 256) == 0;

            if (slIndex != 0) {
               iter.resetToState64(desLangState);
            }

            distance = trieNext(iter, supported.language, true);
         }

         int flags;
         if (distance >= 0) {
            flags = distance & 384;
            distance &= -385;
         } else {
            if (desired.language.equals(supported.language)) {
               distance = 0;
            } else {
               distance = this.defaultLanguageDistance;
            }

            flags = 0;
            star = true;
         }

         assert 0 <= distance && distance <= 100;

         int roundedThreshold = shiftedThreshold + 7 >> 3;
         if (favorSubtag == LocaleMatcher.FavorSubtag.SCRIPT) {
            distance >>= 2;
         }

         if (distance <= roundedThreshold) {
            int scriptDistance;
            if (!star && flags == 0) {
               scriptDistance = getDesSuppScriptDistance(iter, iter.getState64(), desired.script, supported.script);
               flags = scriptDistance & 256;
               scriptDistance &= -257;
            } else if (desired.script.equals(supported.script)) {
               scriptDistance = 0;
            } else {
               scriptDistance = this.defaultScriptDistance;
            }

            distance += scriptDistance;
            if (distance <= roundedThreshold) {
               if (!desired.region.equals(supported.region)) {
                  if (!star && (flags & 256) == 0) {
                     int remainingThreshold = roundedThreshold - distance;
                     if (this.minRegionDistance > remainingThreshold) {
                        continue;
                     }

                     distance += getRegionPartitionsDistance(
                        iter, iter.getState64(), this.partitionsForRegion(desired), this.partitionsForRegion(supported), remainingThreshold
                     );
                  } else {
                     distance += this.defaultRegionDistance;
                  }
               }

               int shiftedDistance = shiftDistance(distance);
               if (shiftedDistance == 0) {
                  shiftedDistance |= desired.flags ^ supported.flags;
                  if (shiftedDistance < shiftedThreshold
                     && (direction != LocaleMatcher.Direction.ONLY_TWO_WAY || this.isMatch(supported, desired, shiftedThreshold, favorSubtag))) {
                     if (shiftedDistance == 0) {
                        return slIndex << 10;
                     }

                     bestIndex = slIndex;
                     shiftedThreshold = shiftedDistance;
                     bestLikelyInfo = -1;
                  }
               } else if (shiftedDistance < shiftedThreshold) {
                  if (direction != LocaleMatcher.Direction.ONLY_TWO_WAY || this.isMatch(supported, desired, shiftedThreshold, favorSubtag)) {
                     bestIndex = slIndex;
                     shiftedThreshold = shiftedDistance;
                     bestLikelyInfo = -1;
                  }
               } else if (shiftedDistance == shiftedThreshold
                  && bestIndex >= 0
                  && (direction != LocaleMatcher.Direction.ONLY_TWO_WAY || this.isMatch(supported, desired, shiftedThreshold, favorSubtag))) {
                  bestLikelyInfo = XLikelySubtags.INSTANCE.compareLikely(supported, supportedLSRs[bestIndex], bestLikelyInfo);
                  if ((bestLikelyInfo & 1) != 0) {
                     bestIndex = slIndex;
                  }
               }
            }
         }
      }

      return bestIndex >= 0 ? bestIndex << 10 | shiftedThreshold : -1024 | shiftDistance(100);
   }

   private boolean isMatch(LSR desired, LSR supported, int shiftedThreshold, LocaleMatcher.FavorSubtag favorSubtag) {
      return this.getBestIndexAndDistance(desired, new LSR[]{supported}, 1, shiftedThreshold, favorSubtag, null) >= 0;
   }

   private static final int getDesSuppScriptDistance(BytesTrie iter, long startState, String desired, String supported) {
      int distance = trieNext(iter, desired, false);
      if (distance >= 0) {
         distance = trieNext(iter, supported, true);
      }

      if (distance < 0) {
         BytesTrie.Result result = iter.resetToState64(startState).next(42);

         assert result.hasValue();

         if (desired.equals(supported)) {
            distance = 0;
         } else {
            distance = iter.getValue();

            assert distance >= 0;
         }

         if (result == BytesTrie.Result.FINAL_VALUE) {
            distance |= 256;
         }
      }

      return distance;
   }

   private static final int getRegionPartitionsDistance(BytesTrie iter, long startState, String desiredPartitions, String supportedPartitions, int threshold) {
      int desLength = desiredPartitions.length();
      int suppLength = supportedPartitions.length();
      if (desLength == 1 && suppLength == 1) {
         BytesTrie.Result result = iter.next(desiredPartitions.charAt(0) | 128);
         if (result.hasNext()) {
            result = iter.next(supportedPartitions.charAt(0) | 128);
            if (result.hasValue()) {
               return iter.getValue();
            }
         }

         return getFallbackRegionDistance(iter, startState);
      } else {
         int regionDistance = 0;
         boolean star = false;
         int di = 0;

         while (true) {
            BytesTrie.Result result = iter.next(desiredPartitions.charAt(di++) | 128);
            if (result.hasNext()) {
               long desState = suppLength > 1 ? iter.getState64() : 0L;
               int si = 0;

               while (true) {
                  result = iter.next(supportedPartitions.charAt(si++) | 128);
                  int d;
                  if (result.hasValue()) {
                     d = iter.getValue();
                  } else if (star) {
                     d = 0;
                  } else {
                     d = getFallbackRegionDistance(iter, startState);
                     star = true;
                  }

                  if (d > threshold) {
                     return d;
                  }

                  if (regionDistance < d) {
                     regionDistance = d;
                  }

                  if (si >= suppLength) {
                     break;
                  }

                  iter.resetToState64(desState);
               }
            } else if (!star) {
               int dx = getFallbackRegionDistance(iter, startState);
               if (dx > threshold) {
                  return dx;
               }

               if (regionDistance < dx) {
                  regionDistance = dx;
               }

               star = true;
            }

            if (di >= desLength) {
               return regionDistance;
            }

            iter.resetToState64(startState);
         }
      }
   }

   private static final int getFallbackRegionDistance(BytesTrie iter, long startState) {
      BytesTrie.Result result = iter.resetToState64(startState).next(42);

      assert result.hasValue();

      int distance = iter.getValue();

      assert distance >= 0;

      return distance;
   }

   private static final int trieNext(BytesTrie iter, String s, boolean wantValue) {
      if (s.isEmpty()) {
         return -1;
      } else {
         int i = 0;
         int end = s.length() - 1;

         while (true) {
            int c = s.charAt(i);
            if (i >= end) {
               BytesTrie.Result result = iter.next(c | 128);
               if (wantValue) {
                  if (result.hasValue()) {
                     int value = iter.getValue();
                     if (result == BytesTrie.Result.FINAL_VALUE) {
                        value |= 256;
                     }

                     return value;
                  }
               } else if (result.hasNext()) {
                  return 0;
               }

               return -1;
            }

            if (!iter.next(c).hasNext()) {
               return -1;
            }

            i++;
         }
      }
   }

   @Override
   public String toString() {
      return this.testOnlyGetDistanceTable().toString();
   }

   private String partitionsForRegion(LSR lsr) {
      int pIndex = this.regionToPartitionsIndex[lsr.regionIndex];
      return this.partitionArrays[pIndex];
   }

   public boolean isParadigmLSR(LSR lsr) {
      assert this.paradigmLSRs.size() <= 15;

      for (LSR plsr : this.paradigmLSRs) {
         if (lsr.isEquivalentTo(plsr)) {
            return true;
         }
      }

      return false;
   }

   public int getDefaultScriptDistance() {
      return this.defaultScriptDistance;
   }

   int getDefaultRegionDistance() {
      return this.defaultRegionDistance;
   }

   public int getDefaultDemotionPerDesiredLocale() {
      return this.defaultDemotionPerDesiredLocale;
   }

   public Map<String, Integer> testOnlyGetDistanceTable() {
      Map<String, Integer> map = new TreeMap<>();
      StringBuilder sb = new StringBuilder();

      for (BytesTrie.Entry entry : this.trie) {
         sb.setLength(0);
         int length = entry.bytesLength();

         for (int i = 0; i < length; i++) {
            byte b = entry.byteAt(i);
            if (b == 42) {
               sb.append("*-*-");
            } else if (b >= 0) {
               sb.append((char)b);
            } else {
               sb.append((char)(b & 127)).append('-');
            }
         }

         assert sb.length() > 0 && sb.charAt(sb.length() - 1) == '-';

         sb.setLength(sb.length() - 1);
         map.put(sb.toString(), entry.value);
      }

      return map;
   }

   public void testOnlyPrintDistanceTable() {
      for (Entry<String, Integer> mapping : this.testOnlyGetDistanceTable().entrySet()) {
         String suffix = "";
         int value = mapping.getValue();
         if ((value & 128) != 0) {
            value &= -129;
            suffix = " skip script";
         }

         System.out.println(mapping.getKey() + '=' + value + suffix);
      }
   }

   public static final class Data {
      public byte[] trie;
      public byte[] regionToPartitionsIndex;
      public String[] partitionArrays;
      public Set<LSR> paradigmLSRs;
      public int[] distances;

      public Data(byte[] trie, byte[] regionToPartitionsIndex, String[] partitionArrays, Set<LSR> paradigmLSRs, int[] distances) {
         this.trie = trie;
         this.regionToPartitionsIndex = regionToPartitionsIndex;
         this.partitionArrays = partitionArrays;
         this.paradigmLSRs = paradigmLSRs;
         this.distances = distances;
      }

      private static UResource.Value getValue(UResource.Table table, String key, UResource.Value value) {
         if (!table.findValue(key, value)) {
            throw new MissingResourceException("langInfo.res missing data", "", "match/" + key);
         } else {
            return value;
         }
      }

      public static LocaleDistance.Data load() throws MissingResourceException {
         ICUResourceBundle langInfo = ICUResourceBundle.getBundleInstance(
            "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", "langInfo", ICUResourceBundle.ICU_DATA_CLASS_LOADER, ICUResourceBundle.OpenType.DIRECT
         );
         UResource.Value value = langInfo.getValueWithFallback("match");
         UResource.Table matchTable = value.getTable();
         ByteBuffer buffer = getValue(matchTable, "trie", value).getBinary();
         byte[] trie = new byte[buffer.remaining()];
         buffer.get(trie);
         buffer = getValue(matchTable, "regionToPartitions", value).getBinary();
         byte[] regionToPartitions = new byte[buffer.remaining()];
         buffer.get(regionToPartitions);
         if (regionToPartitions.length < 1677) {
            throw new MissingResourceException("langInfo.res binary data too short", "", "match/regionToPartitions");
         } else {
            String[] partitions = getValue(matchTable, "partitions", value).getStringArray();
            Set<LSR> paradigmLSRs;
            if (matchTable.findValue("paradigms", value)) {
               String[] paradigms = value.getStringArray();
               paradigmLSRs = new LinkedHashSet<>(paradigms.length / 3);

               for (int i = 0; i < paradigms.length; i += 3) {
                  paradigmLSRs.add(new LSR(paradigms[i], paradigms[i + 1], paradigms[i + 2], 0));
               }
            } else {
               paradigmLSRs = Collections.emptySet();
            }

            int[] distances = getValue(matchTable, "distances", value).getIntVector();
            if (distances.length < 4) {
               throw new MissingResourceException("langInfo.res intvector too short", "", "match/distances");
            } else {
               return new LocaleDistance.Data(trie, regionToPartitions, partitions, paradigmLSRs, distances);
            }
         }
      }

      @Override
      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else if (other != null && this.getClass().equals(other.getClass())) {
            LocaleDistance.Data od = (LocaleDistance.Data)other;
            return Arrays.equals(this.trie, od.trie)
               && Arrays.equals(this.regionToPartitionsIndex, od.regionToPartitionsIndex)
               && Arrays.equals((Object[])this.partitionArrays, (Object[])od.partitionArrays)
               && this.paradigmLSRs.equals(od.paradigmLSRs)
               && Arrays.equals(this.distances, od.distances);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return 1;
      }
   }
}
