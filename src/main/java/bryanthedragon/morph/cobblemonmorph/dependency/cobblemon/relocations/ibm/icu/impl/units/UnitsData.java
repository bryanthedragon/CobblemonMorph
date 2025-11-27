
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.impl.units;

import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.impl.IllegalIcuArgumentException;
import com.cobblemon.mod.relocations.ibm.icu.impl.UResource;
import com.cobblemon.mod.relocations.ibm.icu.impl.units.ConversionRates;
import com.cobblemon.mod.relocations.ibm.icu.impl.units.MeasureUnitImpl;
import com.cobblemon.mod.relocations.ibm.icu.impl.units.UnitPreferences;
import com.cobblemon.mod.relocations.ibm.icu.util.UResourceBundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class UnitsData {
    private static String[] simpleUnits = null;
    private static int[] simpleUnitCategories = null;
    private ConversionRates conversionRates = new ConversionRates();
    private UnitPreferences unitPreferences = new UnitPreferences();

    public static String[] getSimpleUnits() {
        return simpleUnits;
    }

    public ConversionRates getConversionRates() {
        return this.conversionRates;
    }

    public UnitPreferences getUnitPreferences() {
        return this.unitPreferences;
    }

    public static int getCategoryIndexOfSimpleUnit(int simpleUnitIndex) {
        return simpleUnitCategories[simpleUnitIndex];
    }

    public String getCategory(MeasureUnitImpl measureUnit) {
        MeasureUnitImpl baseMeasureUnitImpl = this.getConversionRates().extractCompoundBaseUnit(measureUnit);
        baseMeasureUnitImpl.serialize();
        String identifier = baseMeasureUnitImpl.getIdentifier();
        Integer index = Categories.baseUnitToIndex.get(identifier);
        if (index == null) {
            baseMeasureUnitImpl.takeReciprocal();
            baseMeasureUnitImpl.serialize();
            identifier = baseMeasureUnitImpl.getIdentifier();
            index = Categories.baseUnitToIndex.get(identifier);
        }
        baseMeasureUnitImpl.takeReciprocal();
        MeasureUnitImpl simplifiedUnit = baseMeasureUnitImpl.copyAndSimplify();
        if (index == null) {
            simplifiedUnit.serialize();
            identifier = simplifiedUnit.getIdentifier();
            index = Categories.baseUnitToIndex.get(identifier);
        }
        if (index == null) {
            simplifiedUnit.takeReciprocal();
            simplifiedUnit.serialize();
            identifier = simplifiedUnit.getIdentifier();
            index = Categories.baseUnitToIndex.get(identifier);
        }
        if (index == null) {
            throw new IllegalIcuArgumentException("This unit does not has a category" + measureUnit.getIdentifier());
        }
        return Categories.indexToCategory[index];
    }

    public UnitPreferences.UnitPreference[] getPreferencesFor(String category, String usage, String region) {
        return this.unitPreferences.getPreferencesFor(category, usage, region);
    }

    static {
        ICUResourceBundle resource = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", "units");
        SimpleUnitIdentifiersSink sink = new SimpleUnitIdentifiersSink();
        resource.getAllItemsWithFallback("convertUnits", sink);
        simpleUnits = sink.simpleUnits;
        simpleUnitCategories = sink.simpleUnitCategories;
    }

    public static class CategoriesSink
    extends UResource.Sink {
        HashMap<String, Integer> mapFromUnitToIndex = new HashMap();
        ArrayList<String> categories = new ArrayList();

        @Override
        public void put(UResource.Key key, UResource.Value value2, boolean noFallback) {
            assert (key.toString().equals("unitQuantities"));
            assert (value2.getType() == 8);
            UResource.Array categoryArray = value2.getArray();
            int i = 0;
            while (categoryArray.getValue(i, value2)) {
                assert (value2.getType() == 2);
                UResource.Table table = value2.getTable();
                assert (table.getSize() == 1) : "expecting single-entry table, got size: " + table.getSize();
                table.getKeyAndValue(0, key, value2);
                assert (value2.getType() == 0) : "expecting category string";
                this.mapFromUnitToIndex.put(key.toString(), this.categories.size());
                this.categories.add(value2.toString());
                ++i;
            }
        }
    }

    public static class Categories {
        static HashMap<String, Integer> baseUnitToIndex;
        static String[] indexToCategory;

        static {
            ICUResourceBundle resource = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", "units");
            CategoriesSink sink = new CategoriesSink();
            resource.getAllItemsWithFallback("unitQuantities", sink);
            baseUnitToIndex = sink.mapFromUnitToIndex;
            indexToCategory = sink.categories.toArray(new String[0]);
        }
    }

    public static class Constants {
        public static final int kSimpleUnitOffset = 512;
        public static final int kPowerPartOffset = 256;
        public static final int kInitialCompoundPartOffset = 192;
        public static final int kCompoundPartOffset = 128;
        public static final int kPrefixOffset = 64;
        public static final String CONVERSION_UNIT_TABLE_NAME = "convertUnits";
        public static final String UNIT_PREFERENCE_TABLE_NAME = "unitPreferenceData";
        public static final String CATEGORY_TABLE_NAME = "unitQuantities";
        public static final String DEFAULT_REGION = "001";
        public static final String DEFAULT_USAGE = "default";
    }

    public static class SimpleUnitIdentifiersSink
    extends UResource.Sink {
        String[] simpleUnits = null;
        int[] simpleUnitCategories = null;

        @Override
        public void put(UResource.Key key, UResource.Value value2, boolean noFallback) {
            assert (key.toString().equals("convertUnits"));
            assert (value2.getType() == 2);
            UResource.Table simpleUnitsTable = value2.getTable();
            ArrayList<String> simpleUnits = new ArrayList<String>();
            ArrayList<Integer> simpleUnitCategories = new ArrayList<Integer>();
            int i = 0;
            while (simpleUnitsTable.getKeyAndValue(i, key, value2)) {
                if (!key.toString().equals("kilogram")) {
                    UResource.Table table = value2.getTable();
                    if (!table.findValue("target", value2)) {
                        assert (false) : "Could not find \"target\" for simple unit: " + key;
                    } else {
                        String target = value2.getString();
                        simpleUnits.add(key.toString());
                        simpleUnitCategories.add(Categories.baseUnitToIndex.get(target));
                    }
                }
                ++i;
            }
            this.simpleUnits = simpleUnits.toArray(new String[0]);
            this.simpleUnitCategories = new int[simpleUnitCategories.size()];
            Iterator iter = simpleUnitCategories.iterator();
            for (int i2 = 0; i2 < this.simpleUnitCategories.length; ++i2) {
                this.simpleUnitCategories[i2] = (Integer)iter.next();
            }
        }
    }
}

