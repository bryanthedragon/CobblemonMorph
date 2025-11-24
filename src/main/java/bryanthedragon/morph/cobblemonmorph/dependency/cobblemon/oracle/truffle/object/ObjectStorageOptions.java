
package com.oracle.truffle.object;

@Deprecated
public final class ObjectStorageOptions {
    private static final String OPTION_PREFIX = "truffle.object.";
    public static final boolean PrimitiveLocations = ObjectStorageOptions.booleanOption("truffle.object.PrimitiveLocations", true);
    public static final boolean IntegerLocations = ObjectStorageOptions.booleanOption("truffle.object.IntegerLocations", true);
    public static final boolean DoubleLocations = ObjectStorageOptions.booleanOption("truffle.object.DoubleLocations", true);
    public static final boolean LongLocations = ObjectStorageOptions.booleanOption("truffle.object.LongLocations", true);
    public static final boolean BooleanLocations = ObjectStorageOptions.booleanOption("truffle.object.BooleanLocations", true);
    public static final boolean TypedObjectLocations = ObjectStorageOptions.booleanOption("truffle.object.TypedObjectLocations", true);
    public static final boolean InObjectFields = ObjectStorageOptions.booleanOption("truffle.object.InObjectFields", true);
    static final boolean TriePropertyMap = ObjectStorageOptions.booleanOption("truffle.object.TriePropertyMap", true);
    public static final boolean TraceReshape = ObjectStorageOptions.booleanOption("truffle.object.TraceReshape", false);
    static final boolean DebugCounters = ObjectStorageOptions.booleanOption("truffle.object.DebugCounters", false);
    static final boolean DumpDebugCounters = ObjectStorageOptions.booleanOption("truffle.object.DumpDebugCounters", true);
    static final boolean DumpShapesDOT = ObjectStorageOptions.booleanOption("truffle.object.DumpShapesDOT", false);
    static final boolean DumpShapesJSON = ObjectStorageOptions.booleanOption("truffle.object.DumpShapesJSON", false);
    static final boolean DumpShapesIGV = ObjectStorageOptions.booleanOption("truffle.object.DumpShapesIGV", false);
    static final boolean DumpShapes = DumpShapesDOT || DumpShapesJSON || DumpShapesIGV;
    static final String DumpShapesPath = System.getProperty("truffle.object.DumpShapesPath", "");
    static final boolean Profile = ObjectStorageOptions.booleanOption("truffle.object.Profile", false);
    static final int ProfileTopResults = Integer.getInteger("truffle.object.ProfileTopResults", -1);

    private ObjectStorageOptions() {
    }

    public static boolean booleanOption(String name, boolean defaultValue) {
        String value2 = System.getProperty(name);
        return value2 == null ? defaultValue : value2.equalsIgnoreCase("true");
    }
}

