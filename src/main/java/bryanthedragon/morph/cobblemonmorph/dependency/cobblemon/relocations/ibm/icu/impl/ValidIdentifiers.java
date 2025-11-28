package com.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.impl.locale.AsciiUtil;
import com.cobblemon.mod.relocations.ibm.icu.util.UResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.util.UResourceBundleIterator;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class ValidIdentifiers {
   public static Map<ValidIdentifiers.Datatype, Map<ValidIdentifiers.Datasubtype, ValidIdentifiers.ValiditySet>> getData() {
      return ValidIdentifiers.ValidityData.data;
   }

   public static ValidIdentifiers.Datasubtype isValid(ValidIdentifiers.Datatype datatype, Set<ValidIdentifiers.Datasubtype> datasubtypes, String code) {
      Map<ValidIdentifiers.Datasubtype, ValidIdentifiers.ValiditySet> subtable = ValidIdentifiers.ValidityData.data.get(datatype);
      if (subtable != null) {
         for (ValidIdentifiers.Datasubtype datasubtype : datasubtypes) {
            ValidIdentifiers.ValiditySet validitySet = subtable.get(datasubtype);
            if (validitySet != null && validitySet.contains(AsciiUtil.toLowerString(code))) {
               return datasubtype;
            }
         }
      }

      return null;
   }

   public static ValidIdentifiers.Datasubtype isValid(
      ValidIdentifiers.Datatype datatype, Set<ValidIdentifiers.Datasubtype> datasubtypes, String code, String value
   ) {
      Map<ValidIdentifiers.Datasubtype, ValidIdentifiers.ValiditySet> subtable = ValidIdentifiers.ValidityData.data.get(datatype);
      if (subtable != null) {
         code = AsciiUtil.toLowerString(code);
         value = AsciiUtil.toLowerString(value);

         for (ValidIdentifiers.Datasubtype datasubtype : datasubtypes) {
            ValidIdentifiers.ValiditySet validitySet = subtable.get(datasubtype);
            if (validitySet != null && validitySet.contains(code, value)) {
               return datasubtype;
            }
         }
      }

      return null;
   }

   public static enum Datasubtype {
      deprecated,
      private_use,
      regular,
      special,
      unknown,
      macroregion,
      reserved;
   }

   public static enum Datatype {
      currency,
      language,
      region,
      script,
      subdivision,
      unit,
      variant,
      u,
      t,
      x,
      illegal;
   }

   private static class ValidityData {
      static final Map<ValidIdentifiers.Datatype, Map<ValidIdentifiers.Datasubtype, ValidIdentifiers.ValiditySet>> data;

      private static void addRange(String string, Set<String> subvalues) {
         string = AsciiUtil.toLowerString(string);
         int pos = string.indexOf(126);
         if (pos < 0) {
            subvalues.add(string);
         } else {
            StringRange.expand(string.substring(0, pos), string.substring(pos + 1), false, subvalues);
         }
      }

      static {
         Map<ValidIdentifiers.Datatype, Map<ValidIdentifiers.Datasubtype, ValidIdentifiers.ValiditySet>> _data = new EnumMap<>(ValidIdentifiers.Datatype.class);
         UResourceBundle suppData = UResourceBundle.getBundleInstance(
            "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER
         );
         UResourceBundle validityInfo = suppData.get("idValidity");
         UResourceBundleIterator datatypeIterator = validityInfo.getIterator();

         while (datatypeIterator.hasNext()) {
            UResourceBundle datatype = datatypeIterator.next();
            String rawKey = datatype.getKey();
            ValidIdentifiers.Datatype key = ValidIdentifiers.Datatype.valueOf(rawKey);
            Map<ValidIdentifiers.Datasubtype, ValidIdentifiers.ValiditySet> values = new EnumMap<>(ValidIdentifiers.Datasubtype.class);
            UResourceBundleIterator datasubtypeIterator = datatype.getIterator();

            while (datasubtypeIterator.hasNext()) {
               UResourceBundle datasubtype = datasubtypeIterator.next();
               String rawsubkey = datasubtype.getKey();
               ValidIdentifiers.Datasubtype subkey = ValidIdentifiers.Datasubtype.valueOf(rawsubkey);
               Set<String> subvalues = new HashSet<>();
               if (datasubtype.getType() == 0) {
                  addRange(datasubtype.getString(), subvalues);
               } else {
                  for (String string : datasubtype.getStringArray()) {
                     addRange(string, subvalues);
                  }
               }

               values.put(subkey, new ValidIdentifiers.ValiditySet(subvalues, key == ValidIdentifiers.Datatype.subdivision));
            }

            _data.put(key, Collections.unmodifiableMap(values));
         }

         data = Collections.unmodifiableMap(_data);
      }
   }

   public static class ValiditySet {
      public final Set<String> regularData;
      public final Map<String, Set<String>> subdivisionData;

      public ValiditySet(Set<String> plainData, boolean makeMap) {
         if (makeMap) {
            HashMap<String, Set<String>> _subdivisionData = new HashMap<>();

            for (String s : plainData) {
               int pos = s.indexOf(45);
               int pos2 = pos + 1;
               if (pos < 0) {
                  pos2 = pos = s.charAt(0) < 'A' ? 3 : 2;
               }

               String key = s.substring(0, pos);
               String subdivision = s.substring(pos2);
               Set<String> oldSet = _subdivisionData.get(key);
               if (oldSet == null) {
                  _subdivisionData.put(key, oldSet = new HashSet<>());
               }

               oldSet.add(subdivision);
            }

            this.regularData = null;
            HashMap<String, Set<String>> _subdivisionData2 = new HashMap<>();

            for (Entry<String, Set<String>> e : _subdivisionData.entrySet()) {
               Set<String> value = e.getValue();
               Set<String> set = value.size() == 1 ? Collections.singleton(value.iterator().next()) : Collections.unmodifiableSet(value);
               _subdivisionData2.put(e.getKey(), set);
            }

            this.subdivisionData = Collections.unmodifiableMap(_subdivisionData2);
         } else {
            this.regularData = Collections.unmodifiableSet(plainData);
            this.subdivisionData = null;
         }
      }

      public boolean contains(String code) {
         if (this.regularData != null) {
            return this.regularData.contains(code);
         } else {
            int pos = code.indexOf(45);
            String key = code.substring(0, pos);
            String value = code.substring(pos + 1);
            return this.contains(key, value);
         }
      }

      public boolean contains(String key, String value) {
         Set<String> oldSet = this.subdivisionData.get(key);
         return oldSet != null && oldSet.contains(value);
      }

      @Override
      public String toString() {
         return this.regularData != null ? this.regularData.toString() : this.subdivisionData.toString();
      }
   }
}
