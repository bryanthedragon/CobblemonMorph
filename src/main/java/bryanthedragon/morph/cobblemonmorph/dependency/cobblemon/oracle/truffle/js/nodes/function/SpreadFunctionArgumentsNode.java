package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;
import java.util.Set;

class SpreadFunctionArgumentsNode extends JSFunctionArgumentsNode {
   private final BranchProfile growProfile = BranchProfile.create();

   protected SpreadFunctionArgumentsNode(JavaScriptNode[] args) {
      super(args);
   }

   @Override
   public int getCount(VirtualFrame frame) {
      return this.args.length;
   }

   @ExplodeLoop
   @Override
   public Object[] executeFillObjectArray(VirtualFrame frame, Object[] arguments, int fixedArgumentsLength) {
      SimpleArrayList<Object> argList = SimpleArrayList.create((long)fixedArgumentsLength + this.args.length + 3L);

      for (int i = 0; i < fixedArgumentsLength; i++) {
         argList.addUnchecked(arguments[i]);
      }

      for (int i = 0; i < this.args.length; i++) {
         if (this.args[i] instanceof SpreadArgumentNode) {
            ((SpreadArgumentNode)this.args[i]).executeToList(frame, argList, this.growProfile);
         } else {
            argList.add(this.args[i].execute(frame), this.growProfile);
         }
      }

      return argList.toArray();
   }

   @Override
   protected AbstractFunctionArgumentsNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return new SpreadFunctionArgumentsNode(JavaScriptNode.cloneUninitialized(this.args, materializedTags));
   }
}
