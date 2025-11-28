package com.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.impl.ICUConfig;
import com.cobblemon.mod.relocations.ibm.icu.impl.SoftCache;
import com.cobblemon.mod.relocations.ibm.icu.impl.TZDBTimeZoneNames;
import com.cobblemon.mod.relocations.ibm.icu.impl.TimeZoneNamesImpl;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;

public abstract class TimeZoneNames implements Serializable {
   private static final long serialVersionUID = -9180227029248969153L;
   private static TimeZoneNames.Cache TZNAMES_CACHE = new TimeZoneNames.Cache();
   private static final TimeZoneNames.Factory TZNAMES_FACTORY;
   private static final String FACTORY_NAME_PROP = "com.cobblemon.mod.relocations.ibm.icu.text.TimeZoneNames.Factory.impl";
   private static final String DEFAULT_FACTORY_CLASS = "com.cobblemon.mod.relocations.ibm.icu.impl.TimeZoneNamesFactoryImpl";

   public static TimeZoneNames getInstance(ULocale locale) {
      String key = locale.getBaseName();
      return TZNAMES_CACHE.getInstance(key, locale);
   }

   public static TimeZoneNames getInstance(Locale locale) {
      return getInstance(ULocale.forLocale(locale));
   }

   public static TimeZoneNames getTZDBInstance(ULocale locale) {
      return new TZDBTimeZoneNames(locale);
   }

   public abstract Set<String> getAvailableMetaZoneIDs();

   public abstract Set<String> getAvailableMetaZoneIDs(String var1);

   public abstract String getMetaZoneID(String var1, long var2);

   public abstract String getReferenceZoneID(String var1, String var2);

   public abstract String getMetaZoneDisplayName(String var1, TimeZoneNames.NameType var2);

   public final String getDisplayName(String tzID, TimeZoneNames.NameType type, long date) {
      String name = this.getTimeZoneDisplayName(tzID, type);
      if (name == null) {
         String mzID = this.getMetaZoneID(tzID, date);
         name = this.getMetaZoneDisplayName(mzID, type);
      }

      return name;
   }

   public abstract String getTimeZoneDisplayName(String var1, TimeZoneNames.NameType var2);

   public String getExemplarLocationName(String tzID) {
      return TimeZoneNamesImpl.getDefaultExemplarLocationName(tzID);
   }

   public Collection<TimeZoneNames.MatchInfo> find(CharSequence text, int start, EnumSet<TimeZoneNames.NameType> types) {
      throw new UnsupportedOperationException("The method is not implemented in TimeZoneNames base class.");
   }

   @Deprecated
   public void loadAllDisplayNames() {
   }

   @Deprecated
   public void getDisplayNames(String tzID, TimeZoneNames.NameType[] types, long date, String[] dest, int destOffset) {
      if (tzID != null && tzID.length() != 0) {
         String mzID = null;

         for (int i = 0; i < types.length; i++) {
            TimeZoneNames.NameType type = types[i];
            String name = this.getTimeZoneDisplayName(tzID, type);
            if (name == null) {
               if (mzID == null) {
                  mzID = this.getMetaZoneID(tzID, date);
               }

               name = this.getMetaZoneDisplayName(mzID, type);
            }

            dest[destOffset + i] = name;
         }
      }
   }

   protected TimeZoneNames() {
   }

   static {
      TimeZoneNames.Factory factory = null;
      String classname = ICUConfig.get(
         "com.cobblemon.mod.relocations.ibm.icu.text.TimeZoneNames.Factory.impl", "com.cobblemon.mod.relocations.ibm.icu.impl.TimeZoneNamesFactoryImpl"
      );

      while (true) {
         try {
            factory = (TimeZoneNames.Factory)Class.forName(classname).newInstance();
            break;
         } catch (ClassNotFoundException var3) {
         } catch (IllegalAccessException var4) {
         } catch (InstantiationException var5) {
         }

         if (classname.equals("com.cobblemon.mod.relocations.ibm.icu.impl.TimeZoneNamesFactoryImpl")) {
            break;
         }

         classname = "com.cobblemon.mod.relocations.ibm.icu.impl.TimeZoneNamesFactoryImpl";
      }

      if (factory == null) {
         factory = new TimeZoneNames.DefaultTimeZoneNames.FactoryImpl();
      }

      TZNAMES_FACTORY = factory;
   }

   private static class Cache extends SoftCache<String, TimeZoneNames, ULocale> {
      private Cache() {
      }

      protected TimeZoneNames createInstance(String key, ULocale data) {
         return TimeZoneNames.TZNAMES_FACTORY.getTimeZoneNames(data);
      }
   }

   private static class DefaultTimeZoneNames extends TimeZoneNames {
      private static final long serialVersionUID = -995672072494349071L;
      public static final TimeZoneNames.DefaultTimeZoneNames INSTANCE = new TimeZoneNames.DefaultTimeZoneNames();

      @Override
      public Set<String> getAvailableMetaZoneIDs() {
         return Collections.emptySet();
      }

      @Override
      public Set<String> getAvailableMetaZoneIDs(String tzID) {
         return Collections.emptySet();
      }

      @Override
      public String getMetaZoneID(String tzID, long date) {
         return null;
      }

      @Override
      public String getReferenceZoneID(String mzID, String region) {
         return null;
      }

      @Override
      public String getMetaZoneDisplayName(String mzID, TimeZoneNames.NameType type) {
         return null;
      }

      @Override
      public String getTimeZoneDisplayName(String tzID, TimeZoneNames.NameType type) {
         return null;
      }

      @Override
      public Collection<TimeZoneNames.MatchInfo> find(CharSequence text, int start, EnumSet<TimeZoneNames.NameType> nameTypes) {
         return Collections.emptyList();
      }

      public static class FactoryImpl extends TimeZoneNames.Factory {
         @Override
         public TimeZoneNames getTimeZoneNames(ULocale locale) {
            return TimeZoneNames.DefaultTimeZoneNames.INSTANCE;
         }
      }
   }

   @Deprecated
   public abstract static class Factory {
      @Deprecated
      public abstract TimeZoneNames getTimeZoneNames(ULocale var1);

      @Deprecated
      protected Factory() {
      }
   }

   public static class MatchInfo {
      private TimeZoneNames.NameType _nameType;
      private String _tzID;
      private String _mzID;
      private int _matchLength;

      public MatchInfo(TimeZoneNames.NameType nameType, String tzID, String mzID, int matchLength) {
         if (nameType == null) {
            throw new IllegalArgumentException("nameType is null");
         } else if (tzID == null && mzID == null) {
            throw new IllegalArgumentException("Either tzID or mzID must be available");
         } else if (matchLength <= 0) {
            throw new IllegalArgumentException("matchLength must be positive value");
         } else {
            this._nameType = nameType;
            this._tzID = tzID;
            this._mzID = mzID;
            this._matchLength = matchLength;
         }
      }

      public String tzID() {
         return this._tzID;
      }

      public String mzID() {
         return this._mzID;
      }

      public TimeZoneNames.NameType nameType() {
         return this._nameType;
      }

      public int matchLength() {
         return this._matchLength;
      }
   }

   public static enum NameType {
      LONG_GENERIC,
      LONG_STANDARD,
      LONG_DAYLIGHT,
      SHORT_GENERIC,
      SHORT_STANDARD,
      SHORT_DAYLIGHT,
      EXEMPLAR_LOCATION;
   }
}
