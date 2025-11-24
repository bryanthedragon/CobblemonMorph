
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.impl.units;

import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.impl.UResource;
import com.cobblemon.mod.relocations.ibm.icu.util.UResourceBundle;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class UnitPreferences {
    private HashMap<String, HashMap<String, UnitPreference[]>> mapToUnitPreferences = new HashMap();

    public UnitPreferences() {
        ICUResourceBundle resource = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", "units");
        UnitPreferencesSink sink = new UnitPreferencesSink();
        resource.getAllItemsWithFallback("unitPreferenceData", sink);
        this.mapToUnitPreferences = sink.getMapToUnitPreferences();
    }

    public static String formMapKey(String category, String usage) {
        return category + "++" + usage;
    }

    private static String[] getAllUsages(String usage) {
        ArrayList<String> result = new ArrayList<String>();
        result.add(usage);
        for (int i = usage.length() - 1; i >= 0; --i) {
            if (usage.charAt(i) != '-') continue;
            result.add(usage.substring(0, i));
        }
        if (!usage.equals("default")) {
            result.add("default");
        }
        return result.toArray(new String[0]);
    }

    public UnitPreference[] getPreferencesFor(String category, String usage, String region) {
        String subUsage;
        String[] subUsages = UnitPreferences.getAllUsages(usage);
        UnitPreference[] result = null;
        String[] stringArray = subUsages;
        int n = stringArray.length;
        for (int i = 0; i < n && (result = this.getUnitPreferences(category, subUsage = stringArray[i], region)) == null; ++i) {
        }
        assert (result != null) : "At least the category must be exist";
        return result;
    }

    private UnitPreference[] getUnitPreferences(String category, String usage, String region) {
        String key = UnitPreferences.formMapKey(category, usage);
        if (this.mapToUnitPreferences.containsKey(key)) {
            UnitPreference[] result;
            HashMap<String, UnitPreference[]> unitPreferencesMap = this.mapToUnitPreferences.get(key);
            UnitPreference[] unitPreferenceArray = result = unitPreferencesMap.containsKey(region) ? unitPreferencesMap.get(region) : unitPreferencesMap.get("001");
            assert (result != null);
            return result;
        }
        return null;
    }

    public static class UnitPreferencesSink
    extends UResource.Sink {
        private HashMap<String, HashMap<String, UnitPreference[]>> mapToUnitPreferences = new HashMap();

        public HashMap<String, HashMap<String, UnitPreference[]>> getMapToUnitPreferences() {
            return this.mapToUnitPreferences;
        }

        @Override
        public void put(UResource.Key key, UResource.Value value2, boolean noFallback) {
            assert ("unitPreferenceData".equals(key.toString()));
            UResource.Table categoryTable = value2.getTable();
            int i = 0;
            while (categoryTable.getKeyAndValue(i, key, value2)) {
                assert (value2.getType() == 2);
                String category = key.toString();
                UResource.Table usageTable = value2.getTable();
                int j = 0;
                while (usageTable.getKeyAndValue(j, key, value2)) {
                    assert (value2.getType() == 2);
                    String usage = key.toString();
                    UResource.Table regionTable = value2.getTable();
                    int k = 0;
                    while (regionTable.getKeyAndValue(k, key, value2)) {
                        assert (value2.getType() == 8);
                        String region = key.toString();
                        UResource.Array preferencesTable = value2.getArray();
                        ArrayList<UnitPreference> unitPreferences = new ArrayList<UnitPreference>();
                        int l = 0;
                        while (preferencesTable.getValue(l, value2)) {
                            assert (value2.getType() == 2);
                            UResource.Table singlePrefTable = value2.getTable();
                            String unit = null;
                            String geq = "1";
                            String skeleton = "";
                            int m = 0;
                            while (singlePrefTable.getKeyAndValue(m, key, value2)) {
                                assert (value2.getType() == 0);
                                String keyString = key.toString();
                                if ("unit".equals(keyString)) {
                                    unit = value2.getString();
                                } else if ("geq".equals(keyString)) {
                                    geq = value2.getString();
                                } else if ("skeleton".equals(keyString)) {
                                    skeleton = value2.getString();
                                } else assert (false) : "key must be unit, geq or skeleton";
                                ++m;
                            }
                            assert (unit != null);
                            unitPreferences.add(new UnitPreference(unit, geq, skeleton));
                            ++l;
                        }
                        assert (!unitPreferences.isEmpty());
                        this.insertUnitPreferences(category, usage, region, unitPreferences.toArray(new UnitPreference[0]));
                        ++k;
                    }
                    ++j;
                }
                ++i;
            }
        }

        private void insertUnitPreferences(String category, String usage, String region, UnitPreference[] unitPreferences) {
            HashMap<Object, Object> shouldInsert;
            String key = UnitPreferences.formMapKey(category, usage);
            if (this.mapToUnitPreferences.containsKey(key)) {
                shouldInsert = this.mapToUnitPreferences.get(key);
            } else {
                shouldInsert = new HashMap();
                this.mapToUnitPreferences.put(key, shouldInsert);
            }
            shouldInsert.put(region, unitPreferences);
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
}

