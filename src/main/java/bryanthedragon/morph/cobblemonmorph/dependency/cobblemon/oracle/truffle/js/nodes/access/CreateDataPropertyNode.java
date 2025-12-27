package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public abstract class CreateDataPropertyNode extends JavaScriptBaseNode {
   protected final JSContext context;
   protected final Object key;
   protected final boolean enumerable;
   @Node.Child
   protected IsJSObjectNode isObject;

   protected CreateDataPropertyNode(JSContext context, Object key, boolean enumerable) {
      assert JSRuntime.isPropertyKey(key);

      this.context = context;
      this.key = key;
      this.isObject = IsJSObjectNode.create();
      this.enumerable = enumerable;
   }

   public static CreateDataPropertyNode create(JSContext context, Object key) {
      return CreateDataPropertyNodeGen.create(context, key, true);
   }

   public static CreateDataPropertyNode createNonEnumerable(JSContext context, Object key) {
      return CreateDataPropertyNodeGen.create(context, key, false);
   }

   public abstract void executeVoid(Object object, Object value);

   @Specialization(guards = {"context.getPropertyCacheLimit() > 0", "isObject.executeBoolean(object)"})
   protected static void doCached(Object object, Object value, @Cached("makeDefinePropertyCache()") PropertySetNode propertyCache) {
      propertyCache.setValue(object, value);
   }

   @Specialization(guards = {"context.getPropertyCacheLimit() == 0", "isJSObject(object)"})
   protected final void doUncached(JSDynamicObject object, Object value) {
      if (this.enumerable) {
         JSRuntime.createDataPropertyOrThrow(object, this.key, value);
      } else {
         JSRuntime.createNonEnumerableDataPropertyOrThrow(object, this.key, value);
      }
   }

   @Specialization(guards = "!isJSObject(object)")
   protected final void doNonObject(Object object, Object value) {
      throw Errors.createTypeErrorNotAnObject(object, this);
   }

   protected final PropertySetNode makeDefinePropertyCache() {
      return this.enumerable
         ? PropertySetNode.createImpl(this.key, false, this.context, true, true, JSAttributes.getDefault())
         : PropertySetNode.createImpl(this.key, false, this.context, true, true, JSAttributes.getDefaultNotEnumerable());
   }
}
