
package com.oracle.truffle.api.utilities;

public enum TriState {
    TRUE,
    FALSE,
    UNDEFINED;


    public static TriState valueOf(boolean b) {
        return b ? TRUE : FALSE;
    }

    public static TriState valueOf(Boolean b) {
        return b == null ? UNDEFINED : (Boolean.TRUE.equals(b) ? TRUE : FALSE);
    }
}

