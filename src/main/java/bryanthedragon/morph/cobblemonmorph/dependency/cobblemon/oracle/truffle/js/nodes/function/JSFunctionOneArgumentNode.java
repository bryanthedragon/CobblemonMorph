package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.instrumentation.JSInputGeneratingNodeWrapper;
import java.util.Set;

class JSFunctionOneArgumentNode extends AbstractFunctionArgumentsNode {
   @Node.Child
   private JavaScriptNode child;

   protected JSFunctionOneArgumentNode(JavaScriptNode child) {
      this.child = child;
   }

   public static AbstractFunctionArgumentsNode create(JavaScriptNode child) {
      return new JSFunctionOneArgumentNode(child);
   }

   public static AbstractFunctionArgumentsNode create(JavaScriptNode child, boolean optimizeConstantArguments) {
      return (AbstractFunctionArgumentsNode)(optimizeConstantArguments ? create(child) : new JSFunctionOneArgumentNode(child));
   }

   @Override
   public int getCount(VirtualFrame frame) {
      return 1;
   }

   @Override
   public Object[] executeFillObjectArray(VirtualFrame frame, Object[] arguments, int delta) {
      arguments[delta] = this.child.execute(frame);
      return arguments;
   }

   @Override
   protected AbstractFunctionArgumentsNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return new JSFunctionOneArgumentNode(JavaScriptNode.cloneUninitialized(this.child, materializedTags));
   }

   @Override
   public void materializeInstrumentableArguments() {
      if (!this.child.isInstrumentable()) {
         this.child = this.insert(JSInputGeneratingNodeWrapper.create(this.child));
      }
   }
}
