package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.TestV8Builtins;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;

public final class JSTestV8 {
   public static final TruffleString CLASS_NAME = Strings.constant("TestV8");

   private JSTestV8() {
   }

   public static JSObject create(JSRealm realm) {
      JSContext ctx = realm.getContext();
      JSObject obj = JSOrdinary.createInit(realm);
      JSObjectUtil.putToStringTag(obj, CLASS_NAME);
      JSObjectUtil.putDataProperty(ctx, obj, Strings.STRING_MAX_LENGTH, Integer.valueOf(ctx.getStringLengthLimit()));
      JSObjectUtil.putFunctionsFromContainer(realm, obj, TestV8Builtins.BUILTINS);
      return obj;
   }
}
