package com.oracle.truffle.object;

@Deprecated
public final class ObjectStorageOptions {
   private static final String OPTION_PREFIX = "truffle.object.";
   public static final boolean PrimitiveLocations = booleanOption("truffle.object.PrimitiveLocations", true);
   public static final boolean IntegerLocations = booleanOption("truffle.object.IntegerLocations", true);
   public static final boolean DoubleLocations = booleanOption("truffle.object.DoubleLocations", true);
   public static final boolean LongLocations = booleanOption("truffle.object.LongLocations", true);
   public static final boolean BooleanLocations = booleanOption("truffle.object.BooleanLocations", true);
   public static final boolean TypedObjectLocations = booleanOption("truffle.object.TypedObjectLocations", true);
   public static final boolean InObjectFields = booleanOption("truffle.object.InObjectFields", true);
   static final boolean TriePropertyMap = booleanOption("truffle.object.TriePropertyMap", true);
   public static final boolean TraceReshape = booleanOption("truffle.object.TraceReshape", false);
   static final boolean DebugCounters = booleanOption("truffle.object.DebugCounters", false);
   static final boolean DumpDebugCounters = booleanOption("truffle.object.DumpDebugCounters", true);
   static final boolean DumpShapesDOT = booleanOption("truffle.object.DumpShapesDOT", false);
   static final boolean DumpShapesJSON = booleanOption("truffle.object.DumpShapesJSON", false);
   static final boolean DumpShapesIGV = booleanOption("truffle.object.DumpShapesIGV", false);
   static final boolean DumpShapes = DumpShapesDOT || DumpShapesJSON || DumpShapesIGV;
   static final String DumpShapesPath = System.getProperty("truffle.object.DumpShapesPath", "");
   static final boolean Profile = booleanOption("truffle.object.Profile", false);
   static final int ProfileTopResults = Integer.getInteger("truffle.object.ProfileTopResults", -1);

   private ObjectStorageOptions() {
   }

   public static boolean booleanOption(String name, boolean defaultValue) {
      String value = System.getProperty(name);
      return value == null ? defaultValue : value.equalsIgnoreCase("true");
   }
}
