package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.JSArguments;
import java.util.Set;

public class DefaultDerivedConstructorSuperCallNode extends JavaScriptNode {
   @Node.Child
   JSFunctionCallNode callNode = JSFunctionCallNode.createNewTarget();
   @Node.Child
   JavaScriptNode functionNode;
   @Node.Child
   JavaScriptNode targetNode;

   protected DefaultDerivedConstructorSuperCallNode(JavaScriptNode function, JavaScriptNode target) {
      this.functionNode = function;
      this.targetNode = target;
   }

   @Override
   public Object execute(VirtualFrame frame) {
      Object superFunction = this.functionNode.execute(frame);
      Object target = this.targetNode.execute(frame);
      Object[] arguments = JSArguments.create(target, superFunction, JSArguments.extractUserArguments(frame.getArguments()));
      return this.callNode.executeCall(arguments);
   }

   public static JavaScriptNode create(JavaScriptNode function, JavaScriptNode target) {
      return new DefaultDerivedConstructorSuperCallNode(function, target);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(cloneUninitialized(this.functionNode, materializedTags), cloneUninitialized(this.targetNode, materializedTags));
   }
}
