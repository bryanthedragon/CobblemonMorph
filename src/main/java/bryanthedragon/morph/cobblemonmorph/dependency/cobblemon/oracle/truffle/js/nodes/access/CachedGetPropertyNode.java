package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.dsl.Bind;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.ReportPolymorphism;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.ToArrayIndexNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.util.JSClassProfile;

@ImportStatic(JSRuntime.class)
abstract class CachedGetPropertyNode extends JavaScriptBaseNode {
   static final int MAX_DEPTH = 2;
   protected final JSContext context;

   CachedGetPropertyNode(JSContext context) {
      this.context = context;
   }

   public abstract Object execute(JSDynamicObject target, Object propertyKey, Object receiver, Object defaultValue);

   static CachedGetPropertyNode create(JSContext context) {
      return CachedGetPropertyNodeGen.create(context);
   }

   @Specialization(guards = {"cachedKey != null", "!isArrayIndex(cachedKey)", "propertyKeyEquals(equalsNode, cachedKey, key)"}, limit = "MAX_DEPTH")
   Object doCachedKey(
      JSDynamicObject target,
      Object key,
      Object receiver,
      Object defaultValue,
      @Cached("cachedPropertyKey(key)") Object cachedKey,
      @Cached("create(cachedKey, context)") PropertyGetNode propertyNode,
      @Cached TruffleString.EqualNode equalsNode
   ) {
      return propertyNode.getValueOrDefault(target, receiver, defaultValue);
   }

   @Specialization(guards = {"isArrayIndex(index)", "!isJSProxy(target)"})
   Object doIntIndex(JSDynamicObject target, int index, Object receiver, Object defaultValue, @Cached("create()") JSClassProfile jsclassProfile) {
      return JSObject.getOrDefault(target, index, receiver, defaultValue, jsclassProfile, this);
   }

   @Specialization(guards = {"!isJSProxy(target)", "toArrayIndexNode.isResultArrayIndex(maybeIndex)"}, replaces = "doIntIndex")
   Object doArrayIndex(
      JSDynamicObject target,
      Object key,
      Object receiver,
      Object defaultValue,
      @Cached("create()") RequireObjectCoercibleNode requireObjectCoercibleNode,
      @Cached("createNoToPropertyKey()") ToArrayIndexNode toArrayIndexNode,
      @Bind("toArrayIndexNode.execute(key)") Object maybeIndex,
      @Cached("create()") JSClassProfile jsclassProfile
   ) {
      requireObjectCoercibleNode.executeVoid(target);
      long index = (Long)maybeIndex;
      return JSObject.getOrDefault(target, index, receiver, defaultValue, jsclassProfile, this);
   }

   @Specialization(guards = "isJSProxy(target)")
   protected Object doProxy(
      JSDynamicObject target, Object index, Object receiver, Object defaultValue, @Cached("create(context)") JSProxyPropertyGetNode proxyGet
   ) {
      return proxyGet.executeWithReceiver(target, receiver, index, defaultValue);
   }

   @Specialization(replaces = {"doCachedKey", "doArrayIndex", "doProxy"})
   @ReportPolymorphism.Megamorphic
   Object doGeneric(
      JSDynamicObject target,
      Object key,
      Object receiver,
      Object defaultValue,
      @Cached("create()") RequireObjectCoercibleNode requireObjectCoercibleNode,
      @Cached("create()") ToArrayIndexNode toArrayIndexNode,
      @Cached("createBinaryProfile()") ConditionProfile getType,
      @Cached("create()") JSClassProfile jsclassProfile,
      @Cached("createBinaryProfile()") ConditionProfile highFrequency,
      @Cached("createFrequencyBasedPropertyGet(context)") FrequencyBasedPolymorphicAccessNode.FrequencyBasedPropertyGetNode hotKey,
      @Cached TruffleString.EqualNode equalsNode
   ) {
      requireObjectCoercibleNode.executeVoid(target);
      Object arrayIndex = toArrayIndexNode.execute(key);
      if (getType.profile(arrayIndex instanceof Long)) {
         return JSObject.getOrDefault(target, ((Long)arrayIndex).longValue(), receiver, defaultValue, jsclassProfile, this);
      } else {
         assert JSRuntime.isPropertyKey(arrayIndex);

         Object maybeRead = hotKey.executeFastGet(arrayIndex, target, equalsNode);
         return highFrequency.profile(maybeRead != null) ? maybeRead : JSObject.getOrDefault(target, arrayIndex, receiver, defaultValue, jsclassProfile, this);
      }
   }

   public static Object cachedPropertyKey(Object key) {
      CompilerAsserts.neverPartOfCompilation();
      return JSRuntime.isPropertyKey(key) ? key : null;
   }
}
