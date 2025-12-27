package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public abstract class CoerceOptionsToObjectNode extends JavaScriptBaseNode {
   private final JSContext context;

   public JSContext getContext() {
      return this.context;
   }

   public CoerceOptionsToObjectNode(JSContext context) {
      this.context = context;
   }

   public abstract JSDynamicObject execute(Object opts);

   @Specialization(guards = "isUndefined(opts)")
   public JSDynamicObject fromUndefined(Object opts) {
      return JSOrdinary.createWithNullPrototype(this.getContext());
   }

   @Specialization(guards = "!isUndefined(opts)")
   public JSDynamicObject fromOtherThanUndefined(Object opts, @Cached("createToObject(getContext())") JSToObjectNode toObjectNode) {
      return (JSDynamicObject)toObjectNode.execute(opts);
   }
}
