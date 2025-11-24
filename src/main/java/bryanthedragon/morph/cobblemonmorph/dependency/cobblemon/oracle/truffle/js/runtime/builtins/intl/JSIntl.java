
package com.oracle.truffle.js.runtime.builtins.intl;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.intl.IntlBuiltins;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;

public final class JSIntl {
    public static final TruffleString CLASS_NAME = Strings.constant("Intl");

    private JSIntl() {
    }

    public static JSObject create(JSRealm realm) {
        JSObject obj = JSOrdinary.createInit(realm);
        JSObjectUtil.putFunctionsFromContainer(realm, obj, IntlBuiltins.BUILTINS);
        JSObjectUtil.putToStringTag(obj, CLASS_NAME);
        return obj;
    }
}

