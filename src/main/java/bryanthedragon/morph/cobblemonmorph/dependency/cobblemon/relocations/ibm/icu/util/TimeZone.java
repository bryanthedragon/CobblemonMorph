package com.cobblemon.mod.relocations.ibm.icu.util;

import com.cobblemon.mod.relocations.ibm.icu.impl.Grego;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICUConfig;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.impl.JavaTimeZone;
import com.cobblemon.mod.relocations.ibm.icu.impl.OlsonTimeZone;
import com.cobblemon.mod.relocations.ibm.icu.impl.TimeZoneAdapter;
import com.cobblemon.mod.relocations.ibm.icu.impl.ZoneMeta;
import com.cobblemon.mod.relocations.ibm.icu.text.TimeZoneFormat;
import com.cobblemon.mod.relocations.ibm.icu.text.TimeZoneNames;
import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.logging.Logger;

public abstract class TimeZone implements Serializable, Cloneable, Freezable<TimeZone> {
   private static final Logger LOGGER = Logger.getLogger("com.cobblemon.mod.relocations.ibm.icu.util.TimeZone");
   private static final long serialVersionUID = -744942128318337471L;
   public static final int TIMEZONE_ICU = 0;
   public static final int TIMEZONE_JDK = 1;
   public static final int SHORT = 0;
   public static final int LONG = 1;
   public static final int SHORT_GENERIC = 2;
   public static final int LONG_GENERIC = 3;
   public static final int SHORT_GMT = 4;
   public static final int LONG_GMT = 5;
   public static final int SHORT_COMMONLY_USED = 6;
   public static final int GENERIC_LOCATION = 7;
   public static final String UNKNOWN_ZONE_ID = "Etc/Unknown";
   static final String GMT_ZONE_ID = "Etc/GMT";
   public static final TimeZone UNKNOWN_ZONE = new TimeZone.ConstantZone(0, "Etc/Unknown").freeze();
   public static final TimeZone GMT_ZONE = new TimeZone.ConstantZone(0, "Etc/GMT").freeze();
   private String ID;
   private static volatile TimeZone defaultZone = null;
   private static int TZ_IMPL = 0;
   private static final String TZIMPL_CONFIG_KEY = "com.cobblemon.mod.relocations.ibm.icu.util.TimeZone.DefaultTimeZoneType";
   private static final String TZIMPL_CONFIG_ICU = "ICU";
   private static final String TZIMPL_CONFIG_JDK = "JDK";

   public TimeZone() {
   }

   @Deprecated
   protected TimeZone(String ID) {
      if (ID == null) {
         throw new NullPointerException();
      } else {
         this.ID = ID;
      }
   }

   public abstract int getOffset(int var1, int var2, int var3, int var4, int var5, int var6);

   public int getOffset(long date) {
      int[] result = new int[2];
      this.getOffset(date, false, result);
      return result[0] + result[1];
   }

   public void getOffset(long date, boolean local, int[] offsets) {
      offsets[0] = this.getRawOffset();
      if (!local) {
         date += offsets[0];
      }

      int[] fields = new int[6];
      int pass = 0;

      while (true) {
         Grego.timeToFields(date, fields);
         offsets[1] = this.getOffset(1, fields[0], fields[1], fields[2], fields[3], fields[5]) - offsets[0];
         if (pass != 0 || !local || offsets[1] == 0) {
            return;
         }

         date -= offsets[1];
         pass++;
      }
   }

   public abstract void setRawOffset(int var1);

   public abstract int getRawOffset();

   public String getID() {
      return this.ID;
   }

   public void setID(String ID) {
      if (ID == null) {
         throw new NullPointerException();
      } else if (this.isFrozen()) {
         throw new UnsupportedOperationException("Attempt to modify a frozen TimeZone instance.");
      } else {
         this.ID = ID;
      }
   }

   public final String getDisplayName() {
      return this._getDisplayName(3, false, ULocale.getDefault(ULocale.Category.DISPLAY));
   }

   public final String getDisplayName(Locale locale) {
      return this._getDisplayName(3, false, ULocale.forLocale(locale));
   }

   public final String getDisplayName(ULocale locale) {
      return this._getDisplayName(3, false, locale);
   }

   public final String getDisplayName(boolean daylight, int style) {
      return this.getDisplayName(daylight, style, ULocale.getDefault(ULocale.Category.DISPLAY));
   }

   public String getDisplayName(boolean daylight, int style, Locale locale) {
      return this.getDisplayName(daylight, style, ULocale.forLocale(locale));
   }

   public String getDisplayName(boolean daylight, int style, ULocale locale) {
      if (style >= 0 && style <= 7) {
         return this._getDisplayName(style, daylight, locale);
      } else {
         throw new IllegalArgumentException("Illegal style: " + style);
      }
   }

   private String _getDisplayName(int style, boolean daylight, ULocale locale) {
      if (locale == null) {
         throw new NullPointerException("locale is null");
      } else {
         String result = null;
         if (style == 7 || style == 3 || style == 2) {
            TimeZoneFormat tzfmt = TimeZoneFormat.getInstance(locale);
            long date = System.currentTimeMillis();
            Output<TimeZoneFormat.TimeType> timeType = new Output<>(TimeZoneFormat.TimeType.UNKNOWN);
            switch (style) {
               case 2:
                  result = tzfmt.format(TimeZoneFormat.Style.GENERIC_SHORT, this, date, timeType);
                  break;
               case 3:
                  result = tzfmt.format(TimeZoneFormat.Style.GENERIC_LONG, this, date, timeType);
                  break;
               case 7:
                  result = tzfmt.format(TimeZoneFormat.Style.GENERIC_LOCATION, this, date, timeType);
            }

            if (daylight && timeType.value == TimeZoneFormat.TimeType.STANDARD || !daylight && timeType.value == TimeZoneFormat.TimeType.DAYLIGHT) {
               int offset = daylight ? this.getRawOffset() + this.getDSTSavings() : this.getRawOffset();
               result = style == 2 ? tzfmt.formatOffsetShortLocalizedGMT(offset) : tzfmt.formatOffsetLocalizedGMT(offset);
            }
         } else if (style != 5 && style != 4) {
            assert style == 1 || style == 0 || style == 6;

            long date = System.currentTimeMillis();
            TimeZoneNames tznames = TimeZoneNames.getInstance(locale);
            TimeZoneNames.NameType nameType = null;
            switch (style) {
               case 0:
               case 6:
                  nameType = daylight ? TimeZoneNames.NameType.SHORT_DAYLIGHT : TimeZoneNames.NameType.SHORT_STANDARD;
                  break;
               case 1:
                  nameType = daylight ? TimeZoneNames.NameType.LONG_DAYLIGHT : TimeZoneNames.NameType.LONG_STANDARD;
            }

            result = tznames.getDisplayName(ZoneMeta.getCanonicalCLDRID(this), nameType, date);
            if (result == null) {
               TimeZoneFormat tzfmt = TimeZoneFormat.getInstance(locale);
               int offset = daylight && this.useDaylightTime() ? this.getRawOffset() + this.getDSTSavings() : this.getRawOffset();
               result = style == 1 ? tzfmt.formatOffsetLocalizedGMT(offset) : tzfmt.formatOffsetShortLocalizedGMT(offset);
            }
         } else {
            TimeZoneFormat tzfmt = TimeZoneFormat.getInstance(locale);
            int offset = daylight && this.useDaylightTime() ? this.getRawOffset() + this.getDSTSavings() : this.getRawOffset();
            switch (style) {
               case 4:
                  result = tzfmt.formatOffsetISO8601Basic(offset, false, false, false);
                  break;
               case 5:
                  result = tzfmt.formatOffsetLocalizedGMT(offset);
            }
         }

         assert result != null;

         return result;
      }
   }

   public int getDSTSavings() {
      return this.useDaylightTime() ? 3600000 : 0;
   }

   public abstract boolean useDaylightTime();

   public boolean observesDaylightTime() {
      return this.useDaylightTime() || this.inDaylightTime(new Date());
   }

   public abstract boolean inDaylightTime(Date var1);

   public static TimeZone getTimeZone(String ID) {
      return getTimeZone(ID, TZ_IMPL, false);
   }

   public static TimeZone getFrozenTimeZone(String ID) {
      return getTimeZone(ID, TZ_IMPL, true);
   }

   public static TimeZone getTimeZone(String ID, int type) {
      return getTimeZone(ID, type, false);
   }

   private static TimeZone getTimeZone(String id, int type, boolean frozen) {
      TimeZone result;
      if (type == 1) {
         result = JavaTimeZone.createTimeZone(id);
         if (result != null) {
            return frozen ? result.freeze() : result;
         }

         result = getFrozenICUTimeZone(id, false);
      } else {
         result = getFrozenICUTimeZone(id, true);
      }

      if (result == null) {
         LOGGER.fine("\"" + id + "\" is a bogus id so timezone is falling back to Etc/Unknown(GMT).");
         result = UNKNOWN_ZONE;
      }

      return frozen ? result : result.cloneAsThawed();
   }

   static BasicTimeZone getFrozenICUTimeZone(String id, boolean trySystem) {
      BasicTimeZone result = null;
      if (trySystem) {
         result = ZoneMeta.getSystemTimeZone(id);
      }

      if (result == null) {
         result = ZoneMeta.getCustomTimeZone(id);
      }

      return result;
   }

   public static synchronized void setDefaultTimeZoneType(int type) {
      if (type != 0 && type != 1) {
         throw new IllegalArgumentException("Invalid timezone type");
      } else {
         TZ_IMPL = type;
      }
   }

   public static int getDefaultTimeZoneType() {
      return TZ_IMPL;
   }

   public static Set<String> getAvailableIDs(TimeZone.SystemTimeZoneType zoneType, String region, Integer rawOffset) {
      return ZoneMeta.getAvailableIDs(zoneType, region, rawOffset);
   }

   public static String[] getAvailableIDs(int rawOffset) {
      Set<String> ids = getAvailableIDs(TimeZone.SystemTimeZoneType.ANY, null, rawOffset);
      return ids.toArray(new String[0]);
   }

   public static String[] getAvailableIDs(String country) {
      Set<String> ids = getAvailableIDs(TimeZone.SystemTimeZoneType.ANY, country, null);
      return ids.toArray(new String[0]);
   }

   public static String[] getAvailableIDs() {
      Set<String> ids = getAvailableIDs(TimeZone.SystemTimeZoneType.ANY, null, null);
      return ids.toArray(new String[0]);
   }

   public static int countEquivalentIDs(String id) {
      return ZoneMeta.countEquivalentIDs(id);
   }

   public static String getEquivalentID(String id, int index) {
      return ZoneMeta.getEquivalentID(id, index);
   }

   @Deprecated
   public static TimeZone forULocaleOrDefault(ULocale locale) {
      String tz = locale.getKeywordValue("timezone");
      return tz == null ? getDefault() : getTimeZone(tz);
   }

   @Deprecated
   public static TimeZone forLocaleOrDefault(Locale locale) {
      return forULocaleOrDefault(ULocale.forLocale(locale));
   }

   public static TimeZone getDefault() {
      TimeZone tmpDefaultZone = defaultZone;
      if (tmpDefaultZone == null) {
         synchronized (java.util.TimeZone.class) {
            synchronized (TimeZone.class) {
               tmpDefaultZone = defaultZone;
               if (tmpDefaultZone == null) {
                  if (TZ_IMPL == 1) {
                     tmpDefaultZone = new JavaTimeZone();
                  } else {
                     java.util.TimeZone temp = java.util.TimeZone.getDefault();
                     tmpDefaultZone = getFrozenTimeZone(temp.getID());
                  }

                  defaultZone = tmpDefaultZone;
               }
            }
         }
      }

      return tmpDefaultZone.cloneAsThawed();
   }

   public static synchronized void setDefault(TimeZone tz) {
      setICUDefault(tz);
      if (tz != null) {
         java.util.TimeZone jdkZone = null;
         if (tz instanceof JavaTimeZone) {
            jdkZone = ((JavaTimeZone)tz).unwrap();
         } else if (tz instanceof OlsonTimeZone) {
            String icuID = tz.getID();
            jdkZone = java.util.TimeZone.getTimeZone(icuID);
            if (!icuID.equals(jdkZone.getID())) {
               icuID = getCanonicalID(icuID);
               jdkZone = java.util.TimeZone.getTimeZone(icuID);
               if (!icuID.equals(jdkZone.getID())) {
                  jdkZone = null;
               }
            }
         }

         if (jdkZone == null) {
            jdkZone = TimeZoneAdapter.wrap(tz);
         }

         java.util.TimeZone.setDefault(jdkZone);
      }
   }

   @Deprecated
   public static synchronized void setICUDefault(TimeZone tz) {
      if (tz == null) {
         defaultZone = null;
      } else if (tz.isFrozen()) {
         defaultZone = tz;
      } else {
         defaultZone = ((TimeZone)tz.clone()).freeze();
      }
   }

   public boolean hasSameRules(TimeZone other) {
      return other != null && this.getRawOffset() == other.getRawOffset() && this.useDaylightTime() == other.useDaylightTime();
   }

   @Override
   public Object clone() {
      return this.isFrozen() ? this : this.cloneAsThawed();
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else {
         return obj != null && this.getClass() == obj.getClass() ? this.ID.equals(((TimeZone)obj).ID) : false;
      }
   }

   @Override
   public int hashCode() {
      return this.ID.hashCode();
   }

   public static String getTZDataVersion() {
      return VersionInfo.getTZDataVersion();
   }

   public static String getCanonicalID(String id) {
      return getCanonicalID(id, null);
   }

   public static String getCanonicalID(String id, boolean[] isSystemID) {
      String canonicalID = null;
      boolean systemTzid = false;
      if (id != null && id.length() != 0) {
         if (id.equals("Etc/Unknown")) {
            canonicalID = "Etc/Unknown";
            systemTzid = false;
         } else {
            canonicalID = ZoneMeta.getCanonicalCLDRID(id);
            if (canonicalID != null) {
               systemTzid = true;
            } else {
               canonicalID = ZoneMeta.getCustomID(id);
            }
         }
      }

      if (isSystemID != null) {
         isSystemID[0] = systemTzid;
      }

      return canonicalID;
   }

   public static String getRegion(String id) {
      String region = null;
      if (!id.equals("Etc/Unknown")) {
         region = ZoneMeta.getRegion(id);
      }

      if (region == null) {
         throw new IllegalArgumentException("Unknown system zone id: " + id);
      } else {
         return region;
      }
   }

   public static String getWindowsID(String id) {
      boolean[] isSystemID = new boolean[]{false};
      id = getCanonicalID(id, isSystemID);
      if (!isSystemID[0]) {
         return null;
      } else {
         UResourceBundle top = UResourceBundle.getBundleInstance(
            "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", "windowsZones", ICUResourceBundle.ICU_DATA_CLASS_LOADER
         );
         UResourceBundle mapTimezones = top.get("mapTimezones");
         UResourceBundleIterator resitr = mapTimezones.getIterator();

         while (resitr.hasNext()) {
            UResourceBundle winzone = resitr.next();
            if (winzone.getType() == 2) {
               UResourceBundleIterator rgitr = winzone.getIterator();

               while (rgitr.hasNext()) {
                  UResourceBundle regionalData = rgitr.next();
                  if (regionalData.getType() == 0) {
                     String[] tzids = regionalData.getString().split(" ");

                     for (String tzid : tzids) {
                        if (tzid.equals(id)) {
                           return winzone.getKey();
                        }
                     }
                  }
               }
            }
         }

         return null;
      }
   }

   public static String getIDForWindowsID(String winid, String region) {
      String id = null;
      UResourceBundle top = UResourceBundle.getBundleInstance(
         "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", "windowsZones", ICUResourceBundle.ICU_DATA_CLASS_LOADER
      );
      UResourceBundle mapTimezones = top.get("mapTimezones");

      try {
         UResourceBundle zones = mapTimezones.get(winid);
         if (region != null) {
            try {
               id = zones.getString(region);
               if (id != null) {
                  int endIdx = id.indexOf(32);
                  if (endIdx > 0) {
                     id = id.substring(0, endIdx);
                  }
               }
            } catch (MissingResourceException var7) {
            }
         }

         if (id == null) {
            id = zones.getString("001");
         }
      } catch (MissingResourceException var8) {
      }

      return id;
   }

   @Override
   public boolean isFrozen() {
      return false;
   }

   public TimeZone freeze() {
      throw new UnsupportedOperationException("Needs to be implemented by the subclass.");
   }

   public TimeZone cloneAsThawed() {
      try {
         return (TimeZone)super.clone();
      } catch (CloneNotSupportedException var2) {
         throw new ICUCloneNotSupportedException(var2);
      }
   }

   static {
      String type = ICUConfig.get("com.cobblemon.mod.relocations.ibm.icu.util.TimeZone.DefaultTimeZoneType", "ICU");
      if (type.equalsIgnoreCase("JDK")) {
         TZ_IMPL = 1;
      }
   }

   private static final class ConstantZone extends TimeZone {
      private static final long serialVersionUID = 1L;
      private int rawOffset;
      private transient volatile boolean isFrozen = false;

      private ConstantZone(int rawOffset, String ID) {
         super(ID);
         this.rawOffset = rawOffset;
      }

      @Override
      public int getOffset(int era, int year, int month, int day, int dayOfWeek, int milliseconds) {
         return this.rawOffset;
      }

      @Override
      public void setRawOffset(int offsetMillis) {
         if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen TimeZone instance.");
         } else {
            this.rawOffset = offsetMillis;
         }
      }

      @Override
      public int getRawOffset() {
         return this.rawOffset;
      }

      @Override
      public boolean useDaylightTime() {
         return false;
      }

      @Override
      public boolean inDaylightTime(Date date) {
         return false;
      }

      @Override
      public boolean isFrozen() {
         return this.isFrozen;
      }

      @Override
      public TimeZone freeze() {
         this.isFrozen = true;
         return this;
      }

      @Override
      public TimeZone cloneAsThawed() {
         TimeZone.ConstantZone tz = (TimeZone.ConstantZone)super.cloneAsThawed();
         tz.isFrozen = false;
         return tz;
      }
   }

   public static enum SystemTimeZoneType {
      ANY,
      CANONICAL,
      CANONICAL_LOCATION;
   }
}
