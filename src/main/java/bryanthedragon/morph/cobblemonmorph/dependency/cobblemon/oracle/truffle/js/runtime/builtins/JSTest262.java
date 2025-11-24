
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.PerformanceBuiltins;
import com.oracle.truffle.js.builtins.Test262Builtins;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
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
        JSObjectUtil.putDataProperty(obj, Strings.CREATE_REALM, (Object)realm.lookupFunction(Test262Builtins.BUILTINS, Strings.CREATE_REALM), JSAttributes.getDefaultNotEnumerable());
        JSObjectUtil.putDataProperty(obj, Strings.DETACH_ARRAY_BUFFER, (Object)realm.lookupFunction(Test262Builtins.BUILTINS, Strings.DETACH_ARRAY_BUFFER), JSAttributes.getDefaultNotEnumerable());
        JSObjectUtil.putDataProperty(obj, Strings.EVAL_SCRIPT, (Object)realm.lookupFunction(Test262Builtins.BUILTINS, Strings.EVAL_SCRIPT), JSAttributes.getDefaultNotEnumerable());
        JSObjectUtil.putDataProperty(obj, Strings.GLOBAL, (Object)realm.getGlobalObject(), JSAttributes.getDefaultNotEnumerable());
        JSObjectUtil.putDataProperty(obj, Strings.GC, (Object)realm.lookupFunction(Test262Builtins.BUILTINS, Strings.GC), JSAttributes.getDefaultNotEnumerable());
        JSObject agent = JSTest262.createAgent(realm);
        JSObjectUtil.putDataProperty(obj, Strings.AGENT, (Object)agent, JSAttributes.getDefaultNotEnumerable());
        return obj;
    }

    private static JSObject createAgent(JSRealm realm) {
        JSObject agent = JSOrdinary.createInit(realm);
        JSObjectUtil.putDataProperty(agent, Strings.START, (Object)realm.lookupFunction(Test262Builtins.BUILTINS, Strings.AGENT_START), JSAttributes.getDefaultNotEnumerable());
        JSObjectUtil.putDataProperty(agent, Strings.BROADCAST, (Object)realm.lookupFunction(Test262Builtins.BUILTINS, Strings.AGENT_BROADCAST), JSAttributes.getDefaultNotEnumerable());
        JSObjectUtil.putDataProperty(agent, Strings.GET_REPORT, (Object)realm.lookupFunction(Test262Builtins.BUILTINS, Strings.AGENT_GET_REPORT), JSAttributes.getDefaultNotEnumerable());
        JSObjectUtil.putDataProperty(agent, Strings.SLEEP, (Object)realm.lookupFunction(Test262Builtins.BUILTINS, Strings.AGENT_SLEEP), JSAttributes.getDefaultNotEnumerable());
        JSObjectUtil.putDataProperty(agent, Strings.MONOTONIC_NOW, (Object)realm.lookupFunction(PerformanceBuiltins.BUILTINS, Strings.NOW), JSAttributes.getDefaultNotEnumerable());
        JSObjectUtil.putDataProperty(agent, Strings.RECEIVE_BROADCAST, (Object)realm.lookupFunction(Test262Builtins.BUILTINS, Strings.AGENT_RECEIVE_BROADCAST), JSAttributes.getDefaultNotEnumerable());
        JSObjectUtil.putDataProperty(agent, Strings.REPORT, (Object)realm.lookupFunction(Test262Builtins.BUILTINS, Strings.AGENT_REPORT), JSAttributes.getDefaultNotEnumerable());
        JSObjectUtil.putDataProperty(agent, Strings.LEAVING, (Object)realm.lookupFunction(Test262Builtins.BUILTINS, Strings.AGENT_LEAVING), JSAttributes.getDefaultNotEnumerable());
        return agent;
    }
}

