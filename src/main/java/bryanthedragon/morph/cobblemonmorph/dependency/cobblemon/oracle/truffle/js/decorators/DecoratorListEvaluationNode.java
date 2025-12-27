package com.oracle.truffle.js.decorators;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import java.util.Objects;

public class DecoratorListEvaluationNode extends JavaScriptNode {
   @Node.Children
   private JavaScriptNode[] decorators;

   public static DecoratorListEvaluationNode create(JavaScriptNode[] decorators) {
      return new DecoratorListEvaluationNode(decorators);
   }

   DecoratorListEvaluationNode(JavaScriptNode[] decorators) {
      this.decorators = Objects.requireNonNull(decorators);
   }

   @ExplodeLoop
   public Object[] execute(VirtualFrame frame) {
      Object[] values = new Object[this.decorators.length];

      for (int i = 0; i < this.decorators.length; i++) {
         Object maybeDecorator = this.decorators[i].execute(frame);
         values[this.decorators.length - i - 1] = maybeDecorator;
      }

      return values;
   }
}
