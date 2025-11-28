package com.cobblemon.mod.relocations.ibm.icu.impl.locale;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

public class LocaleExtensions {
   private SortedMap<Character, Extension> _map;
   private String _id;
   private static final SortedMap<Character, Extension> EMPTY_MAP = Collections.unmodifiableSortedMap(new TreeMap<>());
   public static final LocaleExtensions EMPTY_EXTENSIONS = new LocaleExtensions();
   public static final LocaleExtensions CALENDAR_JAPANESE = new LocaleExtensions();
   public static final LocaleExtensions NUMBER_THAI = new LocaleExtensions();

   private LocaleExtensions() {
   }

   LocaleExtensions(
      Map<InternalLocaleBuilder.CaseInsensitiveChar, String> extensions,
      Set<InternalLocaleBuilder.CaseInsensitiveString> uattributes,
      Map<InternalLocaleBuilder.CaseInsensitiveString, String> ukeywords
   ) {
      boolean hasExtension = extensions != null && extensions.size() > 0;
      boolean hasUAttributes = uattributes != null && uattributes.size() > 0;
      boolean hasUKeywords = ukeywords != null && ukeywords.size() > 0;
      if (!hasExtension && !hasUAttributes && !hasUKeywords) {
         this._map = EMPTY_MAP;
         this._id = "";
      } else {
         this._map = new TreeMap<>();
         if (hasExtension) {
            for (Entry<InternalLocaleBuilder.CaseInsensitiveChar, String> ext : extensions.entrySet()) {
               char key = AsciiUtil.toLower(ext.getKey().value());
               String value = ext.getValue();
               if (LanguageTag.isPrivateusePrefixChar(key)) {
                  value = InternalLocaleBuilder.removePrivateuseVariant(value);
                  if (value == null) {
                     continue;
                  }
               }

               Extension e = new Extension(key, AsciiUtil.toLowerString(value));
               this._map.put(key, e);
            }
         }

         if (hasUAttributes || hasUKeywords) {
            TreeSet<String> uaset = null;
            TreeMap<String, String> ukmap = null;
            if (hasUAttributes) {
               uaset = new TreeSet<>();

               for (InternalLocaleBuilder.CaseInsensitiveString cis : uattributes) {
                  uaset.add(AsciiUtil.toLowerString(cis.value()));
               }
            }

            if (hasUKeywords) {
               ukmap = new TreeMap<>();

               for (Entry<InternalLocaleBuilder.CaseInsensitiveString, String> kwd : ukeywords.entrySet()) {
                  String key = AsciiUtil.toLowerString(kwd.getKey().value());
                  String type = AsciiUtil.toLowerString(kwd.getValue());
                  ukmap.put(key, type);
               }
            }

            UnicodeLocaleExtension ule = new UnicodeLocaleExtension(uaset, ukmap);
            this._map.put('u', ule);
         }

         if (this._map.size() == 0) {
            this._map = EMPTY_MAP;
            this._id = "";
         } else {
            this._id = toID(this._map);
         }
      }
   }

   public Set<Character> getKeys() {
      return Collections.unmodifiableSet(this._map.keySet());
   }

   public Extension getExtension(Character key) {
      return this._map.get(AsciiUtil.toLower(key));
   }

   public String getExtensionValue(Character key) {
      Extension ext = this._map.get(AsciiUtil.toLower(key));
      return ext == null ? null : ext.getValue();
   }

   public Set<String> getUnicodeLocaleAttributes() {
      Extension ext = this._map.get('u');
      if (ext == null) {
         return Collections.emptySet();
      } else {
         assert ext instanceof UnicodeLocaleExtension;

         return ((UnicodeLocaleExtension)ext).getUnicodeLocaleAttributes();
      }
   }

   public Set<String> getUnicodeLocaleKeys() {
      Extension ext = this._map.get('u');
      if (ext == null) {
         return Collections.emptySet();
      } else {
         assert ext instanceof UnicodeLocaleExtension;

         return ((UnicodeLocaleExtension)ext).getUnicodeLocaleKeys();
      }
   }

   public String getUnicodeLocaleType(String unicodeLocaleKey) {
      Extension ext = this._map.get('u');
      if (ext == null) {
         return null;
      } else {
         assert ext instanceof UnicodeLocaleExtension;

         return ((UnicodeLocaleExtension)ext).getUnicodeLocaleType(AsciiUtil.toLowerString(unicodeLocaleKey));
      }
   }

   public boolean isEmpty() {
      return this._map.isEmpty();
   }

   public static boolean isValidKey(char c) {
      return LanguageTag.isExtensionSingletonChar(c) || LanguageTag.isPrivateusePrefixChar(c);
   }

   public static boolean isValidUnicodeLocaleKey(String ukey) {
      return UnicodeLocaleExtension.isKey(ukey);
   }

   private static String toID(SortedMap<Character, Extension> map) {
      StringBuilder buf = new StringBuilder();
      Extension privuse = null;

      for (Entry<Character, Extension> entry : map.entrySet()) {
         char singleton = entry.getKey();
         Extension extension = entry.getValue();
         if (LanguageTag.isPrivateusePrefixChar(singleton)) {
            privuse = extension;
         } else {
            if (buf.length() > 0) {
               buf.append("-");
            }

            buf.append(extension);
         }
      }

      if (privuse != null) {
         if (buf.length() > 0) {
            buf.append("-");
         }

         buf.append(privuse);
      }

      return buf.toString();
   }

   @Override
   public String toString() {
      return this._id;
   }

   public String getID() {
      return this._id;
   }

   @Override
   public int hashCode() {
      return this._id.hashCode();
   }

   @Override
   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         return !(other instanceof LocaleExtensions) ? false : this._id.equals(((LocaleExtensions)other)._id);
      }
   }

   static {
      EMPTY_EXTENSIONS._id = "";
      EMPTY_EXTENSIONS._map = EMPTY_MAP;
      CALENDAR_JAPANESE._id = "u-ca-japanese";
      CALENDAR_JAPANESE._map = new TreeMap<>();
      CALENDAR_JAPANESE._map.put('u', UnicodeLocaleExtension.CA_JAPANESE);
      NUMBER_THAI._id = "u-nu-thai";
      NUMBER_THAI._map = new TreeMap<>();
      NUMBER_THAI._map.put('u', UnicodeLocaleExtension.NU_THAI);
   }
}
