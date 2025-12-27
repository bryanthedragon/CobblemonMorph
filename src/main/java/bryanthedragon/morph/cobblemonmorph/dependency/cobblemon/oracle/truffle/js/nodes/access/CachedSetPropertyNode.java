package com.oracle.truffle.js.nodes.access;

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
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.util.JSClassProfile;

@ImportStatic({JSRuntime.class, CachedGetPropertyNode.class})
abstract class CachedSetPropertyNode extends JavaScriptBaseNode {
   static final int MAX_DEPTH = 1;
   protected final JSContext context;
   protected final boolean strict;
   protected final boolean setOwn;
   protected final boolean superProperty;

   CachedSetPropertyNode(JSContext context, boolean strict, boolean setOwn, boolean superProperty) {
      this.context = context;
      this.strict = strict;
      this.setOwn = setOwn;
      this.superProperty = superProperty;
   }

   public abstract void execute(JSDynamicObject target, Object propertyKey, Object value, Object receiver);

   static CachedSetPropertyNode create(JSContext context, boolean strict, boolean setOwn, boolean superProperty) {
      return CachedSetPropertyNodeGen.create(context, strict, setOwn, superProperty);
   }

   @Specialization(guards = {"cachedKey != null", "!isArrayIndex(cachedKey)", "propertyKeyEquals(equalsNode, cachedKey, key)"}, limit = "MAX_DEPTH")
   void doCachedKey(
      JSDynamicObject target,
      Object key,
      Object value,
      Object receiver,
      @Cached("cachedPropertyKey(key)") Object cachedKey,
      @Cached("createSet(cachedKey)") PropertySetNode propertyNode,
      @Cached TruffleString.EqualNode equalsNode
   ) {
      propertyNode.setValue(target, value, receiver);
   }

   @Specialization(guards = {"isArrayIndex(index)", "!isJSProxy(target)"})
   void doIntIndex(JSDynamicObject target, int index, Object value, Object receiver, @Cached("create()") JSClassProfile jsclassProfile) {
      this.doArrayIndexLong(target, index, value, receiver, jsclassProfile.getJSClass(target));
   }

   @Specialization(guards = {"!isJSProxy(target)", "toArrayIndexNode.isResultArrayIndex(maybeIndex)"}, replaces = "doIntIndex")
   void doArrayIndex(
      JSDynamicObject target,
      Object key,
      Object value,
      Object receiver,
      @Cached("createNoToPropertyKey()") ToArrayIndexNode toArrayIndexNode,
      @Bind("toArrayIndexNode.execute(key)") Object maybeIndex,
      @Cached("create()") JSClassProfile jsclassProfile
   ) {
      long index = (Long)maybeIndex;
      this.doArrayIndexLong(target, index, value, receiver, jsclassProfile.getJSClass(target));
   }

   private void doArrayIndexLong(JSDynamicObject target, long index, Object value, Object receiver, JSClass jsclass) {
      if (this.setOwn) {
         createDataPropertyOrThrow(target, Strings.fromLong(index), value);
      } else {
         jsclass.set(target, index, value, receiver, this.strict, this);
      }
   }

   @Specialization(guards = "isJSProxy(target)")
   void doProxy(JSDynamicObject target, Object index, Object value, Object receiver, @Cached("create(context, strict)") JSProxyPropertySetNode proxySet) {
      if (this.setOwn) {
         createDataPropertyOrThrow(target, proxySet.toPropertyKey(index), value);
      } else {
         proxySet.executeWithReceiverAndValue(target, receiver, value, index);
      }
   }

   @Specialization(replaces = {"doCachedKey", "doArrayIndex", "doProxy"})
   @ReportPolymorphism.Megamorphic
   void doGeneric(
      JSDynamicObject target,
      Object key,
      Object value,
      Object receiver,
      @Cached("create()") ToArrayIndexNode toArrayIndexNode,
      @Cached("createBinaryProfile()") ConditionProfile getType,
      @Cached("create()") JSClassProfile jsclassProfile,
      @Cached("createBinaryProfile()") ConditionProfile highFrequency,
      @Cached("createFrequencyBasedPropertySet(context, setOwn, strict, superProperty)") FrequencyBasedPolymorphicAccessNode.FrequencyBasedPropertySetNode hotKey,
      @Cached TruffleString.EqualNode equalsNode
   ) {
      Object arrayIndex = toArrayIndexNode.execute(key);
      if (getType.profile(arrayIndex instanceof Long)) {
         long index = (Long)arrayIndex;
         this.doArrayIndexLong(target, index, value, receiver, jsclassProfile.getJSClass(target));
      } else {
         assert JSRuntime.isPropertyKey(arrayIndex);

         if (highFrequency.profile(hotKey.executeFastSet(target, arrayIndex, value, receiver, equalsNode))) {
            return;
         }

         if (this.setOwn) {
            createDataPropertyOrThrow(target, arrayIndex, value);
         } else {
            JSObject.setWithReceiver(target, arrayIndex, value, receiver, this.strict, jsclassProfile, this);
         }
      }
   }

   private static void createDataPropertyOrThrow(JSDynamicObject target, Object propertyKey, Object value) {
      JSObject.defineOwnProperty(target, propertyKey, PropertyDescriptor.createDataDefault(value), true);
   }

   PropertySetNode createSet(Object key) {
      return PropertySetNode.createImpl(key, false, this.context, this.strict, this.setOwn, JSAttributes.getDefault(), false, this.superProperty);
   }
}
