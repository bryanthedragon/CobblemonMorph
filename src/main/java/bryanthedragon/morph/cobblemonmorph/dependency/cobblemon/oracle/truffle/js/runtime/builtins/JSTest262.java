package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.PerformanceBuiltins;
import com.oracle.truffle.js.builtins.Test262Builtins;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;

public final class JSTest262 {
   public static final TruffleString CLASS_NAME = Strings.constant("Test262");
   public static final TruffleString GLOBAL_PROPERTY_NAME = Strings.constant("$262");

   private JSTest262() {
   }

   public static JSObject create(JSRealm realm) {
      JSObject obj = JSOrdinary.createInit(realm);
      JSObjectUtil.putDataProperty(
         obj, Strings.CREATE_REALM, realm.lookupFunction(Test262Builtins.BUILTINS, Strings.CREATE_REALM), JSAttributes.getDefaultNotEnumerable()
      );
      JSObjectUtil.putDataProperty(
         obj, Strings.DETACH_ARRAY_BUFFER, realm.lookupFunction(Test262Builtins.BUILTINS, Strings.DETACH_ARRAY_BUFFER), JSAttributes.getDefaultNotEnumerable()
      );
      JSObjectUtil.putDataProperty(
         obj, Strings.EVAL_SCRIPT, realm.lookupFunction(Test262Builtins.BUILTINS, Strings.EVAL_SCRIPT), JSAttributes.getDefaultNotEnumerable()
      );
      JSObjectUtil.putDataProperty(obj, Strings.GLOBAL, realm.getGlobalObject(), JSAttributes.getDefaultNotEnumerable());
      JSObjectUtil.putDataProperty(obj, Strings.GC, realm.lookupFunction(Test262Builtins.BUILTINS, Strings.GC), JSAttributes.getDefaultNotEnumerable());
      JSObject agent = createAgent(realm);
      JSObjectUtil.putDataProperty(obj, Strings.AGENT, agent, JSAttributes.getDefaultNotEnumerable());
      return obj;
   }

   private static JSObject createAgent(JSRealm realm) {
      JSObject agent = JSOrdinary.createInit(realm);
      JSObjectUtil.putDataProperty(
         agent, Strings.START, realm.lookupFunction(Test262Builtins.BUILTINS, Strings.AGENT_START), JSAttributes.getDefaultNotEnumerable()
      );
      JSObjectUtil.putDataProperty(
         agent, Strings.BROADCAST, realm.lookupFunction(Test262Builtins.BUILTINS, Strings.AGENT_BROADCAST), JSAttributes.getDefaultNotEnumerable()
      );
      JSObjectUtil.putDataProperty(
         agent, Strings.GET_REPORT, realm.lookupFunction(Test262Builtins.BUILTINS, Strings.AGENT_GET_REPORT), JSAttributes.getDefaultNotEnumerable()
      );
      JSObjectUtil.putDataProperty(
         agent, Strings.SLEEP, realm.lookupFunction(Test262Builtins.BUILTINS, Strings.AGENT_SLEEP), JSAttributes.getDefaultNotEnumerable()
      );
      JSObjectUtil.putDataProperty(
         agent, Strings.MONOTONIC_NOW, realm.lookupFunction(PerformanceBuiltins.BUILTINS, Strings.NOW), JSAttributes.getDefaultNotEnumerable()
      );
      JSObjectUtil.putDataProperty(
         agent,
         Strings.RECEIVE_BROADCAST,
         realm.lookupFunction(Test262Builtins.BUILTINS, Strings.AGENT_RECEIVE_BROADCAST),
         JSAttributes.getDefaultNotEnumerable()
      );
      JSObjectUtil.putDataProperty(
         agent, Strings.REPORT, realm.lookupFunction(Test262Builtins.BUILTINS, Strings.AGENT_REPORT), JSAttributes.getDefaultNotEnumerable()
      );
      JSObjectUtil.putDataProperty(
         agent, Strings.LEAVING, realm.lookupFunction(Test262Builtins.BUILTINS, Strings.AGENT_LEAVING), JSAttributes.getDefaultNotEnumerable()
      );
      return agent;
   }
}
