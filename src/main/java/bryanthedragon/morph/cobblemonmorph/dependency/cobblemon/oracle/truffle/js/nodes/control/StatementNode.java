package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.objects.Undefined;

public abstract class StatementNode extends JavaScriptNode {
   public static final Object EMPTY = Undefined.instance;

   protected static boolean executeConditionAsBoolean(VirtualFrame frame, JavaScriptNode conditionNode) {
      try {
         return conditionNode.executeBoolean(frame);
      } catch (UnexpectedResultException var3) {
         throw Errors.shouldNotReachHere("the condition should always provide a boolean result");
      }
   }
}
