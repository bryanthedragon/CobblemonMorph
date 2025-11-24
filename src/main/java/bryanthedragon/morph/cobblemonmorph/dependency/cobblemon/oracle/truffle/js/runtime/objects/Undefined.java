
package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Nullish;

public final class Undefined {
    public static final TruffleString NAME;
    public static final TruffleString TYPE_NAME;
    public static final JSDynamicObject instance;

    private Undefined() {
    }

    static {
        TYPE_NAME = NAME = Strings.UNDEFINED;
        instance = new Nullish();
    }
}

