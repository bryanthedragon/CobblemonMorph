
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSONBuiltins;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;

public final class JSON {
    public static final TruffleString CLASS_NAME = Strings.constant("JSON");

    private JSON() {
    }

    public static JSObject create(JSRealm realm) {
        JSObject obj = JSOrdinary.createInit(realm);
        JSObjectUtil.putToStringTag(obj, CLASS_NAME);
        JSObjectUtil.putFunctionsFromContainer(realm, obj, JSONBuiltins.BUILTINS);
        return obj;
    }
}

