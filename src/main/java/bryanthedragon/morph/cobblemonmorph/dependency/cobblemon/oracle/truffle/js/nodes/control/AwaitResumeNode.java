package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.function.InternalCallNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.objects.Completion;

public class AwaitResumeNode extends JavaScriptBaseNode {
   private final boolean rejected;
   @Node.Child
   private InternalCallNode executeResumeNode;

   AwaitResumeNode(boolean rejected) {
      this.rejected = rejected;
      this.executeResumeNode = InternalCallNode.create();
   }

   public static AwaitResumeNode create(boolean rejected) {
      return new AwaitResumeNode(rejected);
   }

   public Object execute(CallTarget asyncTarget, Object asyncContext, Object generator, Object result) {
      Completion resumptionValue;
      if (this.rejected) {
         resumptionValue = Completion.forThrow(result);
      } else {
         resumptionValue = Completion.forNormal(result);
      }

      return this.executeResumeNode.execute(asyncTarget, JSArguments.createResumeArguments(asyncContext, generator, resumptionValue));
   }
}
