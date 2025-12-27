package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.WriteNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.UserScriptException;

abstract class AbstractYieldNode extends JavaScriptNode implements ResumableNode, SuspendNode {
   protected final int stateSlot;
   @Node.Child
   protected JavaScriptNode expression;
   @Node.Child
   protected JavaScriptNode yieldValue;
   @Node.Child
   protected ReturnNode returnNode;
   @Node.Child
   protected YieldResultNode generatorYieldNode;
   protected final JSContext context;
   protected final ConditionProfile returnOrExceptionProfile = ConditionProfile.createBinaryProfile();

   protected AbstractYieldNode(
      JSContext context, int stateSlot, JavaScriptNode expression, JavaScriptNode yieldValue, ReturnNode returnNode, YieldResultNode yieldResultNode
   ) {
      this.stateSlot = stateSlot;
      this.context = context;
      this.expression = expression;
      this.returnNode = returnNode;
      this.yieldValue = yieldValue;
      this.generatorYieldNode = yieldResultNode;
   }

   protected final Object generatorYield(VirtualFrame frame, Object iterNextObj) {
      throw this.generatorYieldNode.generatorYield(frame, iterNextObj);
   }

   protected final Object throwValue(Object value) {
      throw UserScriptException.create(value, this, this.context.getContextOptions().getStackTraceLimit());
   }

   protected final Object returnValue(VirtualFrame frame, Object value) {
      if (this.returnNode instanceof ReturnNode.FrameReturnNode) {
         ((WriteNode)this.returnNode.expression).executeWrite(frame, value);
      }

      throw new ReturnException(value);
   }
}
