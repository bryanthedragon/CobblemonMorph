package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JSNodeUtil;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.arguments.AccessArgumentsArrayDirectlyNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import java.util.Set;

public class CallApplyArgumentsNode extends JavaScriptNode {
   @Node.Child
   private JSFunctionCallNode.InvokeNode callNode;

   protected CallApplyArgumentsNode(JSFunctionCallNode callNode) {
      this.callNode = (JSFunctionCallNode.InvokeNode)callNode;
   }

   @Override
   public final Object execute(VirtualFrame frame) {
      Object target = this.callNode.executeTarget(frame);
      Object function = this.callNode.executeFunctionWithTarget(frame, target);
      if (function != this.getRealm().getApplyFunctionObject()) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.replaceWithOrdinaryCall();
      }

      return this.callNode.executeCall(this.callNode.createArguments(frame, target, function));
   }

   @Override
   public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
      if (materializedTags.contains(StandardTags.ExpressionTag.class) || materializedTags.contains(JSTags.FunctionCallTag.class)) {
         this.replaceWithOrdinaryCall();
      }

      return this;
   }

   private void replaceWithOrdinaryCall() {
      this.atomic(() -> {
         for (JavaScriptNode n : this.callNode.getArgumentNodes()) {
            JavaScriptNode node = JSNodeUtil.getWrappedNode(n);
            if (node instanceof AccessArgumentsArrayDirectlyNode) {
               ((AccessArgumentsArrayDirectlyNode)node).replaceWithDefaultArguments();
            }
         }

         this.replace(this.callNode, "not the built-in apply function");
      });
   }

   public static JavaScriptNode create(JSFunctionCallNode callNode) {
      return new CallApplyArgumentsNode(callNode);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(cloneUninitialized(this.callNode, materializedTags));
   }
}
