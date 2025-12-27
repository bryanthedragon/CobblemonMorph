package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

public final class ConstructorResultNode extends JavaScriptNode {
   @Node.Child
   private JavaScriptNode bodyNode;
   @Node.Child
   private JavaScriptNode thisNode;
   private final boolean derived;
   @Node.Child
   private IsObjectNode isObjectNode;
   private final ConditionProfile isObject = ConditionProfile.createBinaryProfile();
   private final ConditionProfile isNotUndefined = ConditionProfile.createBinaryProfile();

   private ConstructorResultNode(boolean derived, JavaScriptNode bodyNode, JavaScriptNode thisNode) {
      this.bodyNode = bodyNode;
      this.derived = derived;
      this.thisNode = thisNode;
      this.isObjectNode = IsObjectNode.create();
   }

   public static JavaScriptNode createBase(JavaScriptNode bodyNode, JavaScriptNode thisNode) {
      return new ConstructorResultNode(false, bodyNode, thisNode);
   }

   public static JavaScriptNode createDerived(JavaScriptNode bodyNode, JavaScriptNode thisNode) {
      return new ConstructorResultNode(true, bodyNode, thisNode);
   }

   @Override
   public Object execute(VirtualFrame frame) {
      Object result = this.bodyNode.execute(frame);
      if (this.isObject.profile(this.isObjectNode.executeBoolean(result))) {
         return result;
      } else if (this.derived && this.isNotUndefined.profile(result != Undefined.instance)) {
         return Null.instance;
      } else {
         Object thisObject = this.thisNode.execute(frame);

         assert thisObject != JSFunction.CONSTRUCT;

         return thisObject;
      }
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return new ConstructorResultNode(this.derived, cloneUninitialized(this.bodyNode, materializedTags), cloneUninitialized(this.thisNode, materializedTags));
   }
}
