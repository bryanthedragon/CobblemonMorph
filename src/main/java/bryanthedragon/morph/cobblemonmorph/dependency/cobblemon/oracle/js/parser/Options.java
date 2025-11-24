
package com.oracle.js.parser;

final class Options {
    private static final String OPTION_NAME_PREFIX = "truffle.js.";

    private Options() {
    }

    public static boolean getBooleanProperty(String name, Boolean defValue) {
        try {
            String property = System.getProperty(OPTION_NAME_PREFIX + name);
            if (property == null && defValue != null) {
                return defValue;
            }
            return property != null && !"false".equalsIgnoreCase(property);
        }
        catch (SecurityException e) {
            return false;
        }
    }

    public static boolean getBooleanProperty(String name) {
        return Options.getBooleanProperty(name, null);
    }
}

