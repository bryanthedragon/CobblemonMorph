package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import java.util.Set;

public class NamedEvaluationNode extends JavaScriptNode {
   @Node.Child
   protected JavaScriptNode nameNode;
   @Node.Child
   protected JavaScriptNode expressionNode;
   @Node.Child
   SetFunctionNameNode setFunctionNameNode;

   protected NamedEvaluationNode(JavaScriptNode expressionNode, JavaScriptNode nameNode) {
      this.nameNode = nameNode;
      this.expressionNode = expressionNode;
      this.setFunctionNameNode = expressionNode instanceof NamedEvaluationTargetNode ? null : SetFunctionNameNode.create();
   }

   public static JavaScriptNode create(JavaScriptNode expressionNode, JavaScriptNode nameNode) {
      return new NamedEvaluationNode(expressionNode, nameNode);
   }

   @Override
   public Object execute(VirtualFrame frame) {
      Object name = this.nameNode.execute(frame);
      return this.executeWithName(frame, name);
   }

   public Object executeWithName(VirtualFrame frame, Object name) {
      Object function;
      if (this.expressionNode instanceof NamedEvaluationTargetNode) {
         assert this.setFunctionNameNode == null;

         function = ((NamedEvaluationTargetNode)this.expressionNode).executeWithName(frame, name);
      } else {
         function = this.expressionNode.execute(frame);
         this.setFunctionNameNode.execute(function, name);
      }

      return function;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(cloneUninitialized(this.expressionNode, materializedTags), cloneUninitialized(this.nameNode, materializedTags));
   }
}
