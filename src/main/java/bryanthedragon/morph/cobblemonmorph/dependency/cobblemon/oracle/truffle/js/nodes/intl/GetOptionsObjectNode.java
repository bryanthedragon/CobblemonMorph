package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.objects.JSObject;

public abstract class GetOptionsObjectNode extends JavaScriptBaseNode {
   private final JSContext context;

   public GetOptionsObjectNode(JSContext context) {
      this.context = context;
   }

   public abstract Object execute(Object options);

   @Specialization(guards = "isUndefined(options)")
   public Object fromUndefined(Object options) {
      return JSOrdinary.createWithNullPrototype(this.context);
   }

   @Specialization
   public Object fromJSObject(JSObject options) {
      return options;
   }

   @Specialization(guards = "!isUndefined(options)", replaces = "fromJSObject")
   public Object fromOther(Object options, @Cached IsObjectNode isObjectNode, @Cached BranchProfile errorBranch) {
      if (isObjectNode.executeBoolean(options)) {
         return options;
      } else {
         errorBranch.enter();
         throw Errors.createTypeErrorNotAnObject(options, this);
      }
   }
}
