package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import java.util.Set;

class JSFunctionZeroArgumentsNode extends AbstractFunctionArgumentsNode {
   protected JSFunctionZeroArgumentsNode() {
   }

   @Override
   public int getCount(VirtualFrame frame) {
      return 0;
   }

   @Override
   public Object[] executeFillObjectArray(VirtualFrame frame, Object[] arguments, int delta) {
      return arguments;
   }

   @Override
   protected AbstractFunctionArgumentsNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return new JSFunctionZeroArgumentsNode();
   }

   @Override
   public void materializeInstrumentableArguments() {
   }
}
