package com.cobblemon.mod.relocations.ibm.icu.impl.units;

import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.impl.UResource;
import com.cobblemon.mod.relocations.ibm.icu.util.UResourceBundle;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class UnitPreferences {
   private HashMap<String, HashMap<String, UnitPreferences.UnitPreference[]>> mapToUnitPreferences = new HashMap<>();

   public UnitPreferences() {
      ICUResourceBundle resource = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", "units");
      UnitPreferences.UnitPreferencesSink sink = new UnitPreferences.UnitPreferencesSink();
      resource.getAllItemsWithFallback("unitPreferenceData", sink);
      this.mapToUnitPreferences = sink.getMapToUnitPreferences();
   }

   public static String formMapKey(String category, String usage) {
      return category + "++" + usage;
   }

   private static String[] getAllUsages(String usage) {
      ArrayList<String> result = new ArrayList<>();
      result.add(usage);

      for (int i = usage.length() - 1; i >= 0; i--) {
         if (usage.charAt(i) == '-') {
            result.add(usage.substring(0, i));
         }
      }

      if (!usage.equals("default")) {
         result.add("default");
      }

      return result.toArray(new String[0]);
   }

   public UnitPreferences.UnitPreference[] getPreferencesFor(String category, String usage, String region) {
      String[] subUsages = getAllUsages(usage);
      UnitPreferences.UnitPreference[] result = null;

      for (String subUsage : subUsages) {
         result = this.getUnitPreferences(category, subUsage, region);
         if (result != null) {
            break;
         }
      }

      assert result != null : "At least the category must be exist";

      return result;
   }

   private UnitPreferences.UnitPreference[] getUnitPreferences(String category, String usage, String region) {
      String key = formMapKey(category, usage);
      if (this.mapToUnitPreferences.containsKey(key)) {
         HashMap<String, UnitPreferences.UnitPreference[]> unitPreferencesMap = this.mapToUnitPreferences.get(key);
         UnitPreferences.UnitPreference[] result = unitPreferencesMap.containsKey(region) ? unitPreferencesMap.get(region) : unitPreferencesMap.get("001");

         assert result != null;

         return result;
      } else {
         return null;
      }
   }

   public static class UnitPreference {
      private final String unit;
      private final BigDecimal geq;
      private final String skeleton;

      public UnitPreference(String unit, String geq, String skeleton) {
         this.unit = unit;
         this.geq = new BigDecimal(geq);
         this.skeleton = skeleton;
      }

      public String getUnit() {
         return this.unit;
      }

      public BigDecimal getGeq() {
         return this.geq;
      }

      public String getSkeleton() {
         return this.skeleton;
      }
   }

   public static class UnitPreferencesSink extends UResource.Sink {
      private HashMap<String, HashMap<String, UnitPreferences.UnitPreference[]>> mapToUnitPreferences = new HashMap<>();

      public HashMap<String, HashMap<String, UnitPreferences.UnitPreference[]>> getMapToUnitPreferences() {
         return this.mapToUnitPreferences;
      }

      @Override
      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         assert "unitPreferenceData".equals(key.toString());

         UResource.Table categoryTable = value.getTable();

         for (int i = 0; categoryTable.getKeyAndValue(i, key, value); i++) {
            assert value.getType() == 2;

            String category = key.toString();
            UResource.Table usageTable = value.getTable();

            for (int j = 0; usageTable.getKeyAndValue(j, key, value); j++) {
               assert value.getType() == 2;

               String usage = key.toString();
               UResource.Table regionTable = value.getTable();

               for (int k = 0; regionTable.getKeyAndValue(k, key, value); k++) {
                  assert value.getType() == 8;

                  String region = key.toString();
                  UResource.Array preferencesTable = value.getArray();
                  ArrayList<UnitPreferences.UnitPreference> unitPreferences = new ArrayList<>();

                  for (int l = 0; preferencesTable.getValue(l, value); l++) {
                     assert value.getType() == 2;

                     UResource.Table singlePrefTable = value.getTable();
                     String unit = null;
                     String geq = "1";
                     String skeleton = "";

                     for (int m = 0; singlePrefTable.getKeyAndValue(m, key, value); m++) {
                        assert value.getType() == 0;

                        String keyString = key.toString();
                        if ("unit".equals(keyString)) {
                           unit = value.getString();
                        } else if ("geq".equals(keyString)) {
                           geq = value.getString();
                        } else if ("skeleton".equals(keyString)) {
                           skeleton = value.getString();
                        } else {
                           assert false : "key must be unit, geq or skeleton";
                        }
                     }

                     assert unit != null;

                     unitPreferences.add(new UnitPreferences.UnitPreference(unit, geq, skeleton));
                  }

                  assert !unitPreferences.isEmpty();

                  this.insertUnitPreferences(category, usage, region, unitPreferences.toArray(new UnitPreferences.UnitPreference[0]));
               }
            }
         }
      }

      private void insertUnitPreferences(String category, String usage, String region, UnitPreferences.UnitPreference[] unitPreferences) {
         String key = UnitPreferences.formMapKey(category, usage);
         HashMap<String, UnitPreferences.UnitPreference[]> shouldInsert;
         if (this.mapToUnitPreferences.containsKey(key)) {
            shouldInsert = this.mapToUnitPreferences.get(key);
         } else {
            shouldInsert = new HashMap<>();
            this.mapToUnitPreferences.put(key, shouldInsert);
         }

         shouldInsert.put(region, unitPreferences);
      }
   }
}
