package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.instrumentation.DeclareTagProvider;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import java.util.Set;

@NodeInfo(cost = NodeCost.NONE)
public class FunctionBodyNode extends AbstractBodyNode {
   @Node.Child
   private JavaScriptNode body;

   public FunctionBodyNode(JavaScriptNode body) {
      this.body = body;
   }

   public static FunctionBodyNode create(JavaScriptNode body) {
      return new FunctionBodyNode(body);
   }

   public JavaScriptNode getBody() {
      return this.body;
   }

   @Override
   public Object execute(VirtualFrame frame) {
      return this.body.execute(frame);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(cloneUninitialized(this.body, materializedTags));
   }

   @Override
   public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
      if (!materializedTags.contains(JSTags.DeclareTag.class) || DeclareTagProvider.isMaterializedFrameProvider(this)) {
         return this;
      } else {
         assert this.getRootNode() instanceof FunctionRootNode : "Malformed AST";

         FrameDescriptor frameDescriptor = this.getRootNode().getFrameDescriptor();
         JavaScriptNode materialized = DeclareTagProvider.createMaterializedFunctionBodyNode(
            this, cloneUninitialized(this.body, materializedTags), frameDescriptor
         );
         materialized.setSourceSection(this.getSourceSection());
         return materialized;
      }
   }
}
