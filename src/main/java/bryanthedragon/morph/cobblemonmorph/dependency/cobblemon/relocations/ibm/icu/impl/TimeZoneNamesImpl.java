package com.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.text.TimeZoneNames;
import com.cobblemon.mod.relocations.ibm.icu.util.TimeZone;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import com.cobblemon.mod.relocations.ibm.icu.util.UResourceBundle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class TimeZoneNamesImpl extends TimeZoneNames {
   private static final long serialVersionUID = -2179814848495897472L;
   private static final String ZONE_STRINGS_BUNDLE = "zoneStrings";
   private static final String MZ_PREFIX = "meta:";
   private static volatile Set<String> METAZONE_IDS;
   private static final TimeZoneNamesImpl.TZ2MZsCache TZ_TO_MZS_CACHE = new TimeZoneNamesImpl.TZ2MZsCache();
   private static final TimeZoneNamesImpl.MZ2TZsCache MZ_TO_TZS_CACHE = new TimeZoneNamesImpl.MZ2TZsCache();
   private transient ICUResourceBundle _zoneStrings;
   private transient ConcurrentHashMap<String, TimeZoneNamesImpl.ZNames> _mzNamesMap;
   private transient ConcurrentHashMap<String, TimeZoneNamesImpl.ZNames> _tzNamesMap;
   private transient boolean _namesFullyLoaded;
   private transient TextTrieMap<TimeZoneNamesImpl.NameInfo> _namesTrie;
   private transient boolean _namesTrieFullyLoaded;
   private static final Pattern LOC_EXCLUSION_PATTERN = Pattern.compile("Etc/.*|SystemV/.*|.*/Riyadh8[7-9]");

   public TimeZoneNamesImpl(ULocale locale) {
      this.initialize(locale);
   }

   @Override
   public Set<String> getAvailableMetaZoneIDs() {
      return _getAvailableMetaZoneIDs();
   }

   static Set<String> _getAvailableMetaZoneIDs() {
      if (METAZONE_IDS == null) {
         synchronized (TimeZoneNamesImpl.class) {
            if (METAZONE_IDS == null) {
               UResourceBundle bundle = UResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", "metaZones");
               UResourceBundle mapTimezones = bundle.get("mapTimezones");
               Set<String> keys = mapTimezones.keySet();
               METAZONE_IDS = Collections.unmodifiableSet(keys);
            }
         }
      }

      return METAZONE_IDS;
   }

   @Override
   public Set<String> getAvailableMetaZoneIDs(String tzID) {
      return _getAvailableMetaZoneIDs(tzID);
   }

   static Set<String> _getAvailableMetaZoneIDs(String tzID) {
      if (tzID != null && tzID.length() != 0) {
         List<TimeZoneNamesImpl.MZMapEntry> maps = TZ_TO_MZS_CACHE.getInstance(tzID, tzID);
         if (maps.isEmpty()) {
            return Collections.emptySet();
         } else {
            Set<String> mzIDs = new HashSet<>(maps.size());

            for (TimeZoneNamesImpl.MZMapEntry map : maps) {
               mzIDs.add(map.mzID());
            }

            return Collections.unmodifiableSet(mzIDs);
         }
      } else {
         return Collections.emptySet();
      }
   }

   @Override
   public String getMetaZoneID(String tzID, long date) {
      return _getMetaZoneID(tzID, date);
   }

   static String _getMetaZoneID(String tzID, long date) {
      if (tzID != null && tzID.length() != 0) {
         String mzID = null;

         for (TimeZoneNamesImpl.MZMapEntry map : TZ_TO_MZS_CACHE.getInstance(tzID, tzID)) {
            if (date >= map.from() && date < map.to()) {
               mzID = map.mzID();
               break;
            }
         }

         return mzID;
      } else {
         return null;
      }
   }

   @Override
   public String getReferenceZoneID(String mzID, String region) {
      return _getReferenceZoneID(mzID, region);
   }

   static String _getReferenceZoneID(String mzID, String region) {
      if (mzID != null && mzID.length() != 0) {
         String refID = null;
         Map<String, String> regionTzMap = MZ_TO_TZS_CACHE.getInstance(mzID, mzID);
         if (!regionTzMap.isEmpty()) {
            refID = regionTzMap.get(region);
            if (refID == null) {
               refID = regionTzMap.get("001");
            }
         }

         return refID;
      } else {
         return null;
      }
   }

   @Override
   public String getMetaZoneDisplayName(String mzID, TimeZoneNames.NameType type) {
      return mzID != null && mzID.length() != 0 ? this.loadMetaZoneNames(mzID).getName(type) : null;
   }

   @Override
   public String getTimeZoneDisplayName(String tzID, TimeZoneNames.NameType type) {
      return tzID != null && tzID.length() != 0 ? this.loadTimeZoneNames(tzID).getName(type) : null;
   }

   @Override
   public String getExemplarLocationName(String tzID) {
      return tzID != null && tzID.length() != 0 ? this.loadTimeZoneNames(tzID).getName(TimeZoneNames.NameType.EXEMPLAR_LOCATION) : null;
   }

   @Override
   public synchronized Collection<TimeZoneNames.MatchInfo> find(CharSequence text, int start, EnumSet<TimeZoneNames.NameType> nameTypes) {
      if (text != null && text.length() != 0 && start >= 0 && start < text.length()) {
         TimeZoneNamesImpl.NameSearchHandler handler = new TimeZoneNamesImpl.NameSearchHandler(nameTypes);
         Collection<TimeZoneNames.MatchInfo> matches = this.doFind(handler, text, start);
         if (matches != null) {
            return matches;
         } else {
            this.addAllNamesIntoTrie();
            matches = this.doFind(handler, text, start);
            if (matches != null) {
               return matches;
            } else {
               this.internalLoadAllDisplayNames();

               for (String tzID : TimeZone.getAvailableIDs(TimeZone.SystemTimeZoneType.CANONICAL, null, null)) {
                  if (!this._tzNamesMap.containsKey(tzID)) {
                     TimeZoneNamesImpl.ZNames.createTimeZoneAndPutInCache(this._tzNamesMap, null, tzID);
                  }
               }

               this.addAllNamesIntoTrie();
               this._namesTrieFullyLoaded = true;
               return this.doFind(handler, text, start);
            }
         }
      } else {
         throw new IllegalArgumentException("bad input text or range");
      }
   }

   private Collection<TimeZoneNames.MatchInfo> doFind(TimeZoneNamesImpl.NameSearchHandler handler, CharSequence text, int start) {
      handler.resetResults();
      this._namesTrie.find(text, start, handler);
      return handler.getMaxMatchLen() != text.length() - start && !this._namesTrieFullyLoaded ? null : handler.getMatches();
   }

   @Override
   public synchronized void loadAllDisplayNames() {
      this.internalLoadAllDisplayNames();
   }

   @Override
   public void getDisplayNames(String tzID, TimeZoneNames.NameType[] types, long date, String[] dest, int destOffset) {
      if (tzID != null && tzID.length() != 0) {
         TimeZoneNamesImpl.ZNames tzNames = this.loadTimeZoneNames(tzID);
         TimeZoneNamesImpl.ZNames mzNames = null;

         for (int i = 0; i < types.length; i++) {
            TimeZoneNames.NameType type = types[i];
            String name = tzNames.getName(type);
            if (name == null) {
               if (mzNames == null) {
                  String mzID = this.getMetaZoneID(tzID, date);
                  if (mzID != null && mzID.length() != 0) {
                     mzNames = this.loadMetaZoneNames(mzID);
                  } else {
                     mzNames = TimeZoneNamesImpl.ZNames.EMPTY_ZNAMES;
                  }
               }

               name = mzNames.getName(type);
            }

            dest[destOffset + i] = name;
         }
      }
   }

   private void internalLoadAllDisplayNames() {
      if (!this._namesFullyLoaded) {
         this._namesFullyLoaded = true;
         new TimeZoneNamesImpl.ZoneStringsLoader().load();
      }
   }

   private void addAllNamesIntoTrie() {
      for (Entry<String, TimeZoneNamesImpl.ZNames> entry : this._tzNamesMap.entrySet()) {
         entry.getValue().addAsTimeZoneIntoTrie(entry.getKey(), this._namesTrie);
      }

      for (Entry<String, TimeZoneNamesImpl.ZNames> entry : this._mzNamesMap.entrySet()) {
         entry.getValue().addAsMetaZoneIntoTrie(entry.getKey(), this._namesTrie);
      }
   }

   private void initialize(ULocale locale) {
      ICUResourceBundle bundle = (ICUResourceBundle)ICUResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b/zone", locale);
      this._zoneStrings = (ICUResourceBundle)bundle.get("zoneStrings");
      this._tzNamesMap = new ConcurrentHashMap<>();
      this._mzNamesMap = new ConcurrentHashMap<>();
      this._namesFullyLoaded = false;
      this._namesTrie = new TextTrieMap<>(true);
      this._namesTrieFullyLoaded = false;
      TimeZone tz = TimeZone.getDefault();
      String tzCanonicalID = ZoneMeta.getCanonicalCLDRID(tz);
      if (tzCanonicalID != null) {
         this.loadStrings(tzCanonicalID);
      }
   }

   private synchronized void loadStrings(String tzCanonicalID) {
      if (tzCanonicalID != null && tzCanonicalID.length() != 0) {
         this.loadTimeZoneNames(tzCanonicalID);

         for (String mzID : this.getAvailableMetaZoneIDs(tzCanonicalID)) {
            this.loadMetaZoneNames(mzID);
         }
      }
   }

   private void writeObject(ObjectOutputStream out) throws IOException {
      ULocale locale = this._zoneStrings.getULocale();
      out.writeObject(locale);
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      ULocale locale = (ULocale)in.readObject();
      this.initialize(locale);
   }

   private synchronized TimeZoneNamesImpl.ZNames loadMetaZoneNames(String mzID) {
      TimeZoneNamesImpl.ZNames mznames = this._mzNamesMap.get(mzID);
      if (mznames == null) {
         TimeZoneNamesImpl.ZNamesLoader loader = new TimeZoneNamesImpl.ZNamesLoader();
         loader.loadMetaZone(this._zoneStrings, mzID);
         mznames = TimeZoneNamesImpl.ZNames.createMetaZoneAndPutInCache(this._mzNamesMap, loader.getNames(), mzID);
      }

      return mznames;
   }

   private synchronized TimeZoneNamesImpl.ZNames loadTimeZoneNames(String tzID) {
      TimeZoneNamesImpl.ZNames tznames = this._tzNamesMap.get(tzID);
      if (tznames == null) {
         TimeZoneNamesImpl.ZNamesLoader loader = new TimeZoneNamesImpl.ZNamesLoader();
         loader.loadTimeZone(this._zoneStrings, tzID);
         tznames = TimeZoneNamesImpl.ZNames.createTimeZoneAndPutInCache(this._tzNamesMap, loader.getNames(), tzID);
      }

      return tznames;
   }

   public static String getDefaultExemplarLocationName(String tzID) {
      if (tzID != null && tzID.length() != 0 && !LOC_EXCLUSION_PATTERN.matcher(tzID).matches()) {
         String location = null;
         int sep = tzID.lastIndexOf(47);
         if (sep > 0 && sep + 1 < tzID.length()) {
            location = tzID.substring(sep + 1).replace('_', ' ');
         }

         return location;
      } else {
         return null;
      }
   }

   private static class MZ2TZsCache extends SoftCache<String, Map<String, String>, String> {
      private MZ2TZsCache() {
      }

      protected Map<String, String> createInstance(String key, String data) {
         Map<String, String> map = null;
         UResourceBundle bundle = UResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", "metaZones");
         UResourceBundle mapTimezones = bundle.get("mapTimezones");

         try {
            UResourceBundle regionMap = mapTimezones.get(key);
            Set<String> regions = regionMap.keySet();
            map = new HashMap<>(regions.size());

            for (String region : regions) {
               String tzID = regionMap.getString(region).intern();
               map.put(region.intern(), tzID);
            }
         } catch (MissingResourceException var11) {
            map = Collections.emptyMap();
         }

         return map;
      }
   }

   private static class MZMapEntry {
      private String _mzID;
      private long _from;
      private long _to;

      MZMapEntry(String mzID, long from, long to) {
         this._mzID = mzID;
         this._from = from;
         this._to = to;
      }

      String mzID() {
         return this._mzID;
      }

      long from() {
         return this._from;
      }

      long to() {
         return this._to;
      }
   }

   private static class NameInfo {
      String tzID;
      String mzID;
      TimeZoneNames.NameType type;

      private NameInfo() {
      }
   }

   private static class NameSearchHandler implements TextTrieMap.ResultHandler<TimeZoneNamesImpl.NameInfo> {
      private EnumSet<TimeZoneNames.NameType> _nameTypes;
      private Collection<TimeZoneNames.MatchInfo> _matches;
      private int _maxMatchLen;

      NameSearchHandler(EnumSet<TimeZoneNames.NameType> nameTypes) {
         this._nameTypes = nameTypes;
      }

      @Override
      public boolean handlePrefixMatch(int matchLength, Iterator<TimeZoneNamesImpl.NameInfo> values) {
         while (values.hasNext()) {
            TimeZoneNamesImpl.NameInfo ninfo = values.next();
            if (this._nameTypes == null || this._nameTypes.contains(ninfo.type)) {
               TimeZoneNames.MatchInfo minfo;
               if (ninfo.tzID != null) {
                  minfo = new TimeZoneNames.MatchInfo(ninfo.type, ninfo.tzID, null, matchLength);
               } else {
                  assert ninfo.mzID != null;

                  minfo = new TimeZoneNames.MatchInfo(ninfo.type, null, ninfo.mzID, matchLength);
               }

               if (this._matches == null) {
                  this._matches = new LinkedList<>();
               }

               this._matches.add(minfo);
               if (matchLength > this._maxMatchLen) {
                  this._maxMatchLen = matchLength;
               }
            }
         }

         return true;
      }

      public Collection<TimeZoneNames.MatchInfo> getMatches() {
         return (Collection<TimeZoneNames.MatchInfo>)(this._matches == null ? Collections.emptyList() : this._matches);
      }

      public int getMaxMatchLen() {
         return this._maxMatchLen;
      }

      public void resetResults() {
         this._matches = null;
         this._maxMatchLen = 0;
      }
   }

   private static class TZ2MZsCache extends SoftCache<String, List<TimeZoneNamesImpl.MZMapEntry>, String> {
      private TZ2MZsCache() {
      }

      protected List<TimeZoneNamesImpl.MZMapEntry> createInstance(String key, String data) {
         List<TimeZoneNamesImpl.MZMapEntry> mzMaps = null;
         UResourceBundle bundle = UResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", "metaZones");
         UResourceBundle metazoneInfoBundle = bundle.get("metazoneInfo");
         String tzkey = data.replace('/', ':');

         try {
            UResourceBundle zoneBundle = metazoneInfoBundle.get(tzkey);
            mzMaps = new ArrayList<>(zoneBundle.getSize());

            for (int idx = 0; idx < zoneBundle.getSize(); idx++) {
               UResourceBundle mz = zoneBundle.get(idx);
               String mzid = mz.getString(0);
               String fromStr = "1970-01-01 00:00";
               String toStr = "9999-12-31 23:59";
               if (mz.getSize() == 3) {
                  fromStr = mz.getString(1);
                  toStr = mz.getString(2);
               }

               long from = parseDate(fromStr);
               long to = parseDate(toStr);
               mzMaps.add(new TimeZoneNamesImpl.MZMapEntry(mzid, from, to));
            }
         } catch (MissingResourceException var17) {
            mzMaps = Collections.emptyList();
         }

         return mzMaps;
      }

      private static long parseDate(String text) {
         int year = 0;
         int month = 0;
         int day = 0;
         int hour = 0;
         int min = 0;

         for (int idx = 0; idx <= 3; idx++) {
            int n = text.charAt(idx) - '0';
            if (n < 0 || n >= 10) {
               throw new IllegalArgumentException("Bad year");
            }

            year = 10 * year + n;
         }

         for (int var10 = 5; var10 <= 6; var10++) {
            int n = text.charAt(var10) - '0';
            if (n < 0 || n >= 10) {
               throw new IllegalArgumentException("Bad month");
            }

            month = 10 * month + n;
         }

         for (int var11 = 8; var11 <= 9; var11++) {
            int n = text.charAt(var11) - '0';
            if (n < 0 || n >= 10) {
               throw new IllegalArgumentException("Bad day");
            }

            day = 10 * day + n;
         }

         for (int var12 = 11; var12 <= 12; var12++) {
            int n = text.charAt(var12) - '0';
            if (n < 0 || n >= 10) {
               throw new IllegalArgumentException("Bad hour");
            }

            hour = 10 * hour + n;
         }

         for (int var13 = 14; var13 <= 15; var13++) {
            int n = text.charAt(var13) - '0';
            if (n < 0 || n >= 10) {
               throw new IllegalArgumentException("Bad minute");
            }

            min = 10 * min + n;
         }

         return Grego.fieldsToDay(year, month - 1, day) * 86400000L + hour * 3600000L + min * 60000L;
      }
   }

   private static class ZNames {
      public static final int NUM_NAME_TYPES = 7;
      static final TimeZoneNamesImpl.ZNames EMPTY_ZNAMES = new TimeZoneNamesImpl.ZNames(null);
      private static final int EX_LOC_INDEX = TimeZoneNamesImpl.ZNames.NameTypeIndex.EXEMPLAR_LOCATION.ordinal();
      private String[] _names;
      private boolean didAddIntoTrie;

      private static int getNameTypeIndex(TimeZoneNames.NameType type) {
         switch (type) {
            case EXEMPLAR_LOCATION:
               return TimeZoneNamesImpl.ZNames.NameTypeIndex.EXEMPLAR_LOCATION.ordinal();
            case LONG_GENERIC:
               return TimeZoneNamesImpl.ZNames.NameTypeIndex.LONG_GENERIC.ordinal();
            case LONG_STANDARD:
               return TimeZoneNamesImpl.ZNames.NameTypeIndex.LONG_STANDARD.ordinal();
            case LONG_DAYLIGHT:
               return TimeZoneNamesImpl.ZNames.NameTypeIndex.LONG_DAYLIGHT.ordinal();
            case SHORT_GENERIC:
               return TimeZoneNamesImpl.ZNames.NameTypeIndex.SHORT_GENERIC.ordinal();
            case SHORT_STANDARD:
               return TimeZoneNamesImpl.ZNames.NameTypeIndex.SHORT_STANDARD.ordinal();
            case SHORT_DAYLIGHT:
               return TimeZoneNamesImpl.ZNames.NameTypeIndex.SHORT_DAYLIGHT.ordinal();
            default:
               throw new AssertionError("No NameTypeIndex match for " + type);
         }
      }

      private static TimeZoneNames.NameType getNameType(int index) {
         switch (TimeZoneNamesImpl.ZNames.NameTypeIndex.values[index]) {
            case EXEMPLAR_LOCATION:
               return TimeZoneNames.NameType.EXEMPLAR_LOCATION;
            case LONG_GENERIC:
               return TimeZoneNames.NameType.LONG_GENERIC;
            case LONG_STANDARD:
               return TimeZoneNames.NameType.LONG_STANDARD;
            case LONG_DAYLIGHT:
               return TimeZoneNames.NameType.LONG_DAYLIGHT;
            case SHORT_GENERIC:
               return TimeZoneNames.NameType.SHORT_GENERIC;
            case SHORT_STANDARD:
               return TimeZoneNames.NameType.SHORT_STANDARD;
            case SHORT_DAYLIGHT:
               return TimeZoneNames.NameType.SHORT_DAYLIGHT;
            default:
               throw new AssertionError("No NameType match for " + index);
         }
      }

      protected ZNames(String[] names) {
         this._names = names;
         this.didAddIntoTrie = names == null;
      }

      public static TimeZoneNamesImpl.ZNames createMetaZoneAndPutInCache(Map<String, TimeZoneNamesImpl.ZNames> cache, String[] names, String mzID) {
         String key = mzID.intern();
         TimeZoneNamesImpl.ZNames value;
         if (names == null) {
            value = EMPTY_ZNAMES;
         } else {
            value = new TimeZoneNamesImpl.ZNames(names);
         }

         cache.put(key, value);
         return value;
      }

      public static TimeZoneNamesImpl.ZNames createTimeZoneAndPutInCache(Map<String, TimeZoneNamesImpl.ZNames> cache, String[] names, String tzID) {
         names = names == null ? new String[EX_LOC_INDEX + 1] : names;
         if (names[EX_LOC_INDEX] == null) {
            names[EX_LOC_INDEX] = TimeZoneNamesImpl.getDefaultExemplarLocationName(tzID);
         }

         String key = tzID.intern();
         TimeZoneNamesImpl.ZNames value = new TimeZoneNamesImpl.ZNames(names);
         cache.put(key, value);
         return value;
      }

      public String getName(TimeZoneNames.NameType type) {
         int index = getNameTypeIndex(type);
         return this._names != null && index < this._names.length ? this._names[index] : null;
      }

      public void addAsMetaZoneIntoTrie(String mzID, TextTrieMap<TimeZoneNamesImpl.NameInfo> trie) {
         this.addNamesIntoTrie(mzID, null, trie);
      }

      public void addAsTimeZoneIntoTrie(String tzID, TextTrieMap<TimeZoneNamesImpl.NameInfo> trie) {
         this.addNamesIntoTrie(null, tzID, trie);
      }

      private void addNamesIntoTrie(String mzID, String tzID, TextTrieMap<TimeZoneNamesImpl.NameInfo> trie) {
         if (this._names != null && !this.didAddIntoTrie) {
            this.didAddIntoTrie = true;

            for (int i = 0; i < this._names.length; i++) {
               String name = this._names[i];
               if (name != null) {
                  TimeZoneNamesImpl.NameInfo info = new TimeZoneNamesImpl.NameInfo();
                  info.mzID = mzID;
                  info.tzID = tzID;
                  info.type = getNameType(i);
                  trie.put(name, info);
               }
            }
         }
      }

      private static enum NameTypeIndex {
         EXEMPLAR_LOCATION,
         LONG_GENERIC,
         LONG_STANDARD,
         LONG_DAYLIGHT,
         SHORT_GENERIC,
         SHORT_STANDARD,
         SHORT_DAYLIGHT;

         static final TimeZoneNamesImpl.ZNames.NameTypeIndex[] values = values();
      }
   }

   private static final class ZNamesLoader extends UResource.Sink {
      private String[] names;
      private static TimeZoneNamesImpl.ZNamesLoader DUMMY_LOADER = new TimeZoneNamesImpl.ZNamesLoader();

      private ZNamesLoader() {
      }

      void loadMetaZone(ICUResourceBundle zoneStrings, String mzID) {
         String key = "meta:" + mzID;
         this.loadNames(zoneStrings, key);
      }

      void loadTimeZone(ICUResourceBundle zoneStrings, String tzID) {
         String key = tzID.replace('/', ':');
         this.loadNames(zoneStrings, key);
      }

      void loadNames(ICUResourceBundle zoneStrings, String key) {
         assert zoneStrings != null;

         assert key != null;

         assert key.length() > 0;

         this.names = null;

         try {
            zoneStrings.getAllItemsWithFallback(key, this);
         } catch (MissingResourceException var4) {
         }
      }

      private static TimeZoneNamesImpl.ZNames.NameTypeIndex nameTypeIndexFromKey(UResource.Key key) {
         if (key.length() != 2) {
            return null;
         } else {
            char c0 = key.charAt(0);
            char c1 = key.charAt(1);
            if (c0 == 'l') {
               return c1 == 'g'
                  ? TimeZoneNamesImpl.ZNames.NameTypeIndex.LONG_GENERIC
                  : (
                     c1 == 's'
                        ? TimeZoneNamesImpl.ZNames.NameTypeIndex.LONG_STANDARD
                        : (c1 == 'd' ? TimeZoneNamesImpl.ZNames.NameTypeIndex.LONG_DAYLIGHT : null)
                  );
            } else if (c0 == 's') {
               return c1 == 'g'
                  ? TimeZoneNamesImpl.ZNames.NameTypeIndex.SHORT_GENERIC
                  : (
                     c1 == 's'
                        ? TimeZoneNamesImpl.ZNames.NameTypeIndex.SHORT_STANDARD
                        : (c1 == 'd' ? TimeZoneNamesImpl.ZNames.NameTypeIndex.SHORT_DAYLIGHT : null)
                  );
            } else {
               return c0 == 'e' && c1 == 'c' ? TimeZoneNamesImpl.ZNames.NameTypeIndex.EXEMPLAR_LOCATION : null;
            }
         }
      }

      private void setNameIfEmpty(UResource.Key key, UResource.Value value) {
         if (this.names == null) {
            this.names = new String[7];
         }

         TimeZoneNamesImpl.ZNames.NameTypeIndex index = nameTypeIndexFromKey(key);
         if (index != null) {
            assert index.ordinal() < 7;

            if (this.names[index.ordinal()] == null) {
               this.names[index.ordinal()] = value.getString();
            }
         }
      }

      @Override
      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         UResource.Table namesTable = value.getTable();

         for (int i = 0; namesTable.getKeyAndValue(i, key, value); i++) {
            assert value.getType() == 0;

            this.setNameIfEmpty(key, value);
         }
      }

      private String[] getNames() {
         if (Utility.sameObjects(this.names, null)) {
            return null;
         } else {
            int length = 0;

            for (int i = 0; i < 7; i++) {
               String name = this.names[i];
               if (name != null) {
                  if (name.equals("∅∅∅")) {
                     this.names[i] = null;
                  } else {
                     length = i + 1;
                  }
               }
            }

            String[] result;
            if (length == 7) {
               result = this.names;
            } else if (length == 0) {
               result = null;
            } else {
               result = Arrays.copyOfRange(this.names, 0, length);
            }

            return result;
         }
      }
   }

   private final class ZoneStringsLoader extends UResource.Sink {
      private static final int INITIAL_NUM_ZONES = 300;
      private HashMap<UResource.Key, TimeZoneNamesImpl.ZNamesLoader> keyToLoader = new HashMap<>(300);
      private StringBuilder sb = new StringBuilder(32);

      private ZoneStringsLoader() {
      }

      void load() {
         TimeZoneNamesImpl.this._zoneStrings.getAllItemsWithFallback("", this);

         for (Entry<UResource.Key, TimeZoneNamesImpl.ZNamesLoader> entry : this.keyToLoader.entrySet()) {
            TimeZoneNamesImpl.ZNamesLoader loader = entry.getValue();
            if (loader != TimeZoneNamesImpl.ZNamesLoader.DUMMY_LOADER) {
               UResource.Key key = entry.getKey();
               if (this.isMetaZone(key)) {
                  String mzID = this.mzIDFromKey(key);
                  TimeZoneNamesImpl.ZNames.createMetaZoneAndPutInCache(TimeZoneNamesImpl.this._mzNamesMap, loader.getNames(), mzID);
               } else {
                  String tzID = this.tzIDFromKey(key);
                  TimeZoneNamesImpl.ZNames.createTimeZoneAndPutInCache(TimeZoneNamesImpl.this._tzNamesMap, loader.getNames(), tzID);
               }
            }
         }
      }

      @Override
      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         UResource.Table timeZonesTable = value.getTable();

         for (int j = 0; timeZonesTable.getKeyAndValue(j, key, value); j++) {
            assert !value.isNoInheritanceMarker();

            if (value.getType() == 2) {
               this.consumeNamesTable(key, value, noFallback);
            }
         }
      }

      private void consumeNamesTable(UResource.Key key, UResource.Value value, boolean noFallback) {
         TimeZoneNamesImpl.ZNamesLoader loader = this.keyToLoader.get(key);
         if (loader == null) {
            if (this.isMetaZone(key)) {
               String mzID = this.mzIDFromKey(key);
               if (TimeZoneNamesImpl.this._mzNamesMap.containsKey(mzID)) {
                  loader = TimeZoneNamesImpl.ZNamesLoader.DUMMY_LOADER;
               } else {
                  loader = new TimeZoneNamesImpl.ZNamesLoader();
               }
            } else {
               String tzID = this.tzIDFromKey(key);
               if (TimeZoneNamesImpl.this._tzNamesMap.containsKey(tzID)) {
                  loader = TimeZoneNamesImpl.ZNamesLoader.DUMMY_LOADER;
               } else {
                  loader = new TimeZoneNamesImpl.ZNamesLoader();
               }
            }

            UResource.Key newKey = this.createKey(key);
            this.keyToLoader.put(newKey, loader);
         }

         if (loader != TimeZoneNamesImpl.ZNamesLoader.DUMMY_LOADER) {
            loader.put(key, value, noFallback);
         }
      }

      UResource.Key createKey(UResource.Key key) {
         return key.clone();
      }

      boolean isMetaZone(UResource.Key key) {
         return key.startsWith("meta:");
      }

      private String mzIDFromKey(UResource.Key key) {
         this.sb.setLength(0);

         for (int i = "meta:".length(); i < key.length(); i++) {
            this.sb.append(key.charAt(i));
         }

         return this.sb.toString();
      }

      private String tzIDFromKey(UResource.Key key) {
         this.sb.setLength(0);

         for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (c == ':') {
               c = '/';
            }

            this.sb.append(c);
         }

         return this.sb.toString();
      }
   }
}
