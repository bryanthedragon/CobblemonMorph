package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.IsJSObjectNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;

public abstract class CreateMethodPropertyNode extends JavaScriptBaseNode {
   protected final JSContext context;
   protected final Object key;
   @Node.Child
   protected IsJSObjectNode isObject;

   protected CreateMethodPropertyNode(JSContext context, Object key) {
      this.context = context;
      this.key = key;
      this.isObject = IsJSObjectNode.create();
   }

   public static CreateMethodPropertyNode create(JSContext context, Object key) {
      return CreateMethodPropertyNodeGen.create(context, key);
   }

   public abstract void executeVoid(Object object, Object value);

   @Specialization(guards = {"context.getPropertyCacheLimit() > 0", "isObject.executeBoolean(object)"})
   protected static void doCached(Object object, Object value, @Cached("makeDefinePropertyCache()") PropertySetNode propertyCache) {
      propertyCache.setValue(object, value);
   }

   @Specialization(guards = {"context.getPropertyCacheLimit() == 0", "isJSObject(object)"})
   protected final void doUncached(JSDynamicObject object, Object value) {
      JSObject.defineOwnProperty(object, this.key, PropertyDescriptor.createData(value, false, true, true));
   }

   @Specialization(guards = "!isJSObject(object)")
   protected final void doNonObject(Object object, Object value) {
      throw Errors.createTypeErrorNotAnObject(object, this);
   }

   protected final PropertySetNode makeDefinePropertyCache() {
      return PropertySetNode.createImpl(this.key, false, this.context, true, true, JSAttributes.getDefaultNotEnumerable());
   }
}
