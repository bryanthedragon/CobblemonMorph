package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.math.MathBuiltins;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;

public final class JSMath {
   public static final TruffleString CLASS_NAME = Strings.constant("Math");

   private JSMath() {
   }

   public static JSObject create(JSRealm realm) {
      JSContext ctx = realm.getContext();
      JSObject obj = JSOrdinary.createInit(realm);
      JSObjectUtil.putToStringTag(obj, CLASS_NAME);
      JSObjectUtil.putDataProperty(ctx, obj, Strings.E, Math.E);
      JSObjectUtil.putDataProperty(ctx, obj, Strings.PI, Math.PI);
      JSObjectUtil.putDataProperty(ctx, obj, Strings.LN_10, 2.302585092994046);
      JSObjectUtil.putDataProperty(ctx, obj, Strings.LN_2, 0.6931471805599453);
      JSObjectUtil.putDataProperty(ctx, obj, Strings.LOG_2_E, 1.4426950408889634);
      JSObjectUtil.putDataProperty(ctx, obj, Strings.LOG_10_E, 0.4342944819032518);
      JSObjectUtil.putDataProperty(ctx, obj, Strings.SQRT_1_2, 0.7071067811865476);
      JSObjectUtil.putDataProperty(ctx, obj, Strings.SQRT_2, 1.4142135623730951);
      JSObjectUtil.putFunctionsFromContainer(realm, obj, MathBuiltins.BUILTINS);
      return obj;
   }
}
