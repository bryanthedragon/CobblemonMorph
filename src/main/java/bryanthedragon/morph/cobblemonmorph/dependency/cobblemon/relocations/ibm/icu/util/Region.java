package com.cobblemon.mod.relocations.ibm.icu.util;

import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceBundle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Region implements Comparable<Region> {
   private String id;
   private int code;
   private Region.RegionType type;
   private Region containingRegion = null;
   private Set<Region> containedRegions = new TreeSet<>();
   private List<Region> preferredValues = null;
   private static boolean regionDataIsLoaded = false;
   private static Map<String, Region> regionIDMap = null;
   private static Map<Integer, Region> numericCodeMap = null;
   private static Map<String, Region> regionAliases = null;
   private static ArrayList<Region> regions = null;
   private static ArrayList<Set<Region>> availableRegions = null;
   private static final String UNKNOWN_REGION_ID = "ZZ";
   private static final String OUTLYING_OCEANIA_REGION_ID = "QO";
   private static final String WORLD_ID = "001";

   private Region() {
   }

   private static synchronized void loadRegionData() {
      if (!regionDataIsLoaded) {
         regionAliases = new HashMap<>();
         regionIDMap = new HashMap<>();
         numericCodeMap = new HashMap<>();
         availableRegions = new ArrayList<>(Region.RegionType.values().length);
         UResourceBundle metadataAlias = null;
         UResourceBundle territoryAlias = null;
         UResourceBundle codeMappings = null;
         UResourceBundle idValidity = null;
         UResourceBundle regionList = null;
         UResourceBundle regionRegular = null;
         UResourceBundle regionMacro = null;
         UResourceBundle regionUnknown = null;
         UResourceBundle worldContainment = null;
         UResourceBundle territoryContainment = null;
         UResourceBundle groupingContainment = null;
         UResourceBundle metadata = UResourceBundle.getBundleInstance(
            "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", "metadata", ICUResourceBundle.ICU_DATA_CLASS_LOADER
         );
         metadataAlias = metadata.get("alias");
         territoryAlias = metadataAlias.get("territory");
         UResourceBundle supplementalData = UResourceBundle.getBundleInstance(
            "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER
         );
         codeMappings = supplementalData.get("codeMappings");
         idValidity = supplementalData.get("idValidity");
         regionList = idValidity.get("region");
         regionRegular = regionList.get("regular");
         regionMacro = regionList.get("macroregion");
         regionUnknown = regionList.get("unknown");
         territoryContainment = supplementalData.get("territoryContainment");
         worldContainment = territoryContainment.get("001");
         groupingContainment = territoryContainment.get("grouping");
         String[] continentsArr = worldContainment.getStringArray();
         List<String> continents = Arrays.asList(continentsArr);
         Enumeration<String> groupings = groupingContainment.getKeys();
         List<String> regionCodes = new ArrayList<>();
         List<String> allRegions = new ArrayList<>();
         allRegions.addAll(Arrays.asList(regionRegular.getStringArray()));
         allRegions.addAll(Arrays.asList(regionMacro.getStringArray()));
         allRegions.add(regionUnknown.getString());

         for (String r : allRegions) {
            int rangeMarkerLocation = r.indexOf("~");
            if (rangeMarkerLocation > 0) {
               StringBuilder regionName = new StringBuilder(r);
               char endRange = regionName.charAt(rangeMarkerLocation + 1);
               regionName.setLength(rangeMarkerLocation);
               char lastChar = regionName.charAt(rangeMarkerLocation - 1);

               while (lastChar <= endRange) {
                  String newRegion = regionName.toString();
                  regionCodes.add(newRegion);
                  lastChar++;
                  regionName.setCharAt(rangeMarkerLocation - 1, lastChar);
               }
            } else {
               regionCodes.add(r);
            }
         }

         regions = new ArrayList<>(regionCodes.size());

         for (String id : regionCodes) {
            Region rx = new Region();
            rx.id = id;
            rx.type = Region.RegionType.TERRITORY;
            regionIDMap.put(id, rx);
            if (id.matches("[0-9]{3}")) {
               rx.code = Integer.valueOf(id);
               numericCodeMap.put(rx.code, rx);
               rx.type = Region.RegionType.SUBCONTINENT;
            } else {
               rx.code = -1;
            }

            regions.add(rx);
         }

         for (int i = 0; i < territoryAlias.getSize(); i++) {
            UResourceBundle res = territoryAlias.get(i);
            String aliasFrom = res.getKey();
            String aliasTo = res.get("replacement").getString();
            if (regionIDMap.containsKey(aliasTo) && !regionIDMap.containsKey(aliasFrom)) {
               regionAliases.put(aliasFrom, regionIDMap.get(aliasTo));
            } else {
               Region rx;
               if (regionIDMap.containsKey(aliasFrom)) {
                  rx = regionIDMap.get(aliasFrom);
               } else {
                  rx = new Region();
                  rx.id = aliasFrom;
                  regionIDMap.put(aliasFrom, rx);
                  if (aliasFrom.matches("[0-9]{3}")) {
                     rx.code = Integer.valueOf(aliasFrom);
                     numericCodeMap.put(rx.code, rx);
                  } else {
                     rx.code = -1;
                  }

                  regions.add(rx);
               }

               rx.type = Region.RegionType.DEPRECATED;
               List<String> aliasToRegionStrings = Arrays.asList(aliasTo.split(" "));
               rx.preferredValues = new ArrayList<>();

               for (String s : aliasToRegionStrings) {
                  if (regionIDMap.containsKey(s)) {
                     rx.preferredValues.add(regionIDMap.get(s));
                  }
               }
            }
         }

         for (int ix = 0; ix < codeMappings.getSize(); ix++) {
            UResourceBundle mapping = codeMappings.get(ix);
            if (mapping.getType() == 8) {
               String[] codeMappingStrings = mapping.getStringArray();
               String codeMappingID = codeMappingStrings[0];
               Integer codeMappingNumber = Integer.valueOf(codeMappingStrings[1]);
               String codeMapping3Letter = codeMappingStrings[2];
               if (regionIDMap.containsKey(codeMappingID)) {
                  Region rx = regionIDMap.get(codeMappingID);
                  rx.code = codeMappingNumber;
                  numericCodeMap.put(rx.code, rx);
                  regionAliases.put(codeMapping3Letter, rx);
               }
            }
         }

         if (regionIDMap.containsKey("001")) {
            Region rx = regionIDMap.get("001");
            rx.type = Region.RegionType.WORLD;
         }

         if (regionIDMap.containsKey("ZZ")) {
            Region rx = regionIDMap.get("ZZ");
            rx.type = Region.RegionType.UNKNOWN;
         }

         for (String continent : continents) {
            if (regionIDMap.containsKey(continent)) {
               Region rx = regionIDMap.get(continent);
               rx.type = Region.RegionType.CONTINENT;
            }
         }

         while (groupings.hasMoreElements()) {
            String grouping = groupings.nextElement();
            if (regionIDMap.containsKey(grouping)) {
               Region rx = regionIDMap.get(grouping);
               rx.type = Region.RegionType.GROUPING;
            }
         }

         if (regionIDMap.containsKey("QO")) {
            Region rx = regionIDMap.get("QO");
            rx.type = Region.RegionType.SUBCONTINENT;
         }

         for (int ixx = 0; ixx < territoryContainment.getSize(); ixx++) {
            UResourceBundle mapping = territoryContainment.get(ixx);
            String parent = mapping.getKey();
            if (!parent.equals("containedGroupings") && !parent.equals("deprecated") && !parent.equals("grouping")) {
               Region parentRegion = regionIDMap.get(parent);

               for (int j = 0; j < mapping.getSize(); j++) {
                  String child = mapping.getString(j);
                  Region childRegion = regionIDMap.get(child);
                  if (parentRegion != null && childRegion != null) {
                     parentRegion.containedRegions.add(childRegion);
                     if (parentRegion.getType() != Region.RegionType.GROUPING) {
                        childRegion.containingRegion = parentRegion;
                     }
                  }
               }
            }
         }

         for (int ixxx = 0; ixxx < groupingContainment.getSize(); ixxx++) {
            UResourceBundle mapping = groupingContainment.get(ixxx);
            String parent = mapping.getKey();
            Region parentRegion = regionIDMap.get(parent);

            for (int jx = 0; jx < mapping.getSize(); jx++) {
               String child = mapping.getString(jx);
               Region childRegion = regionIDMap.get(child);
               if (parentRegion != null && childRegion != null) {
                  parentRegion.containedRegions.add(childRegion);
               }
            }
         }

         for (int ixxx = 0; ixxx < Region.RegionType.values().length; ixxx++) {
            availableRegions.add(new TreeSet<>());
         }

         for (Region ar : regions) {
            Set<Region> currentSet = availableRegions.get(ar.type.ordinal());
            currentSet.add(ar);
            availableRegions.set(ar.type.ordinal(), currentSet);
         }

         regionDataIsLoaded = true;
      }
   }

   public static Region getInstance(String id) {
      if (id == null) {
         throw new NullPointerException();
      } else {
         loadRegionData();
         Region r = regionIDMap.get(id);
         if (r == null) {
            r = regionAliases.get(id);
         }

         if (r == null) {
            throw new IllegalArgumentException("Unknown region id: " + id);
         } else {
            if (r.type == Region.RegionType.DEPRECATED && r.preferredValues.size() == 1) {
               r = r.preferredValues.get(0);
            }

            return r;
         }
      }
   }

   public static Region getInstance(int code) {
      loadRegionData();
      Region r = numericCodeMap.get(code);
      if (r == null) {
         String pad = "";
         if (code < 10) {
            pad = "00";
         } else if (code < 100) {
            pad = "0";
         }

         String id = pad + Integer.toString(code);
         r = regionAliases.get(id);
      }

      if (r == null) {
         throw new IllegalArgumentException("Unknown region code: " + code);
      } else {
         if (r.type == Region.RegionType.DEPRECATED && r.preferredValues.size() == 1) {
            r = r.preferredValues.get(0);
         }

         return r;
      }
   }

   public static Set<Region> getAvailable(Region.RegionType type) {
      loadRegionData();
      return Collections.unmodifiableSet(availableRegions.get(type.ordinal()));
   }

   public Region getContainingRegion() {
      loadRegionData();
      return this.containingRegion;
   }

   public Region getContainingRegion(Region.RegionType type) {
      loadRegionData();
      if (this.containingRegion == null) {
         return null;
      } else {
         return this.containingRegion.type.equals(type) ? this.containingRegion : this.containingRegion.getContainingRegion(type);
      }
   }

   public Set<Region> getContainedRegions() {
      loadRegionData();
      return Collections.unmodifiableSet(this.containedRegions);
   }

   public Set<Region> getContainedRegions(Region.RegionType type) {
      loadRegionData();
      Set<Region> result = new TreeSet<>();

      for (Region r : this.getContainedRegions()) {
         if (r.getType() == type) {
            result.add(r);
         } else {
            result.addAll(r.getContainedRegions(type));
         }
      }

      return Collections.unmodifiableSet(result);
   }

   public List<Region> getPreferredValues() {
      loadRegionData();
      return this.type == Region.RegionType.DEPRECATED ? Collections.unmodifiableList(this.preferredValues) : null;
   }

   public boolean contains(Region other) {
      loadRegionData();
      if (this.containedRegions.contains(other)) {
         return true;
      } else {
         for (Region cr : this.containedRegions) {
            if (cr.contains(other)) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public String toString() {
      return this.id;
   }

   public int getNumericCode() {
      return this.code;
   }

   public Region.RegionType getType() {
      return this.type;
   }

   public int compareTo(Region other) {
      return this.id.compareTo(other.id);
   }

   public static enum RegionType {
      UNKNOWN,
      TERRITORY,
      WORLD,
      CONTINENT,
      SUBCONTINENT,
      GROUPING,
      DEPRECATED;
   }
}
