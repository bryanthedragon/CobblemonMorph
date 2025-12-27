package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;

@GenerateUncached
@ImportStatic(CompilerDirectives.class)
public abstract class IsJSDynamicObjectNode extends JavaScriptBaseNode {
   protected IsJSDynamicObjectNode() {
   }

   public abstract boolean executeBoolean(Object obj);

   @Specialization(guards = {"cachedClass != null", "isExact(object, cachedClass)"}, limit = "1")
   protected static boolean isObjectCached(
      Object object, @Cached("getClassIfJSDynamicObject(object)") Class<?> cachedClass, @Cached("isJSDynamicObject(object)") boolean cachedResult
   ) {
      return cachedResult;
   }

   @Specialization(replaces = "isObjectCached")
   protected boolean isObject(Object object, @Cached ConditionProfile resultProfile) {
      return resultProfile.profile(JSGuards.isJSDynamicObject(object));
   }

   public static IsJSDynamicObjectNode create() {
      return IsJSDynamicObjectNodeGen.create();
   }

   public static IsJSDynamicObjectNode getUncached() {
      return IsJSDynamicObjectNodeGen.getUncached();
   }
}
