package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.CreateIterResultObjectNode;
import com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.Completion;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.Set;

public class YieldNode extends AbstractYieldNode implements ResumableNode.WithIntState {
   @Node.Child
   private CreateIterResultObjectNode createIterResultObjectNode;

   protected YieldNode(
      JSContext context, int stateSlot, JavaScriptNode expression, JavaScriptNode yieldValue, ReturnNode returnNode, YieldResultNode yieldResultNode
   ) {
      super(context, stateSlot, expression, yieldValue, returnNode, yieldResultNode);
      this.createIterResultObjectNode = CreateIterResultObjectNode.create(context);
   }

   public static JavaScriptNode createYield(
      JSContext context, int stateSlot, JavaScriptNode expression, JavaScriptNode yieldValue, ReturnNode returnNode, JSWriteFrameSlotNode writeYieldResultNode
   ) {
      return new YieldNode(
         context,
         stateSlot,
         expression,
         yieldValue,
         returnNode,
         (YieldResultNode)(writeYieldResultNode == null
            ? new YieldResultNode.ExceptionYieldResultNode()
            : new YieldResultNode.FrameYieldResultNode(writeYieldResultNode))
      );
   }

   public static JavaScriptNode createYieldStar(
      JSContext context, int stateSlot, JavaScriptNode expression, JavaScriptNode yieldValue, ReturnNode returnNode, JSWriteFrameSlotNode writeYieldResultNode
   ) {
      return new YieldStarNode(
         context,
         stateSlot,
         expression,
         yieldValue,
         returnNode,
         (YieldResultNode)(writeYieldResultNode == null
            ? new YieldResultNode.ExceptionYieldResultNode()
            : new YieldResultNode.FrameYieldResultNode(writeYieldResultNode))
      );
   }

   @Override
   public Object execute(VirtualFrame frame) {
      int index = this.getStateAsInt(frame, this.stateSlot);
      if (index == 0) {
         Object value = this.expression.execute(frame);
         JSDynamicObject iterNextObj = this.createIterResultObjectNode.execute(frame, value, false);
         this.setStateAsInt(frame, this.stateSlot, 1);
         return this.generatorYield(frame, iterNextObj);
      } else {
         assert index == 1;

         this.setStateAsInt(frame, this.stateSlot, 0);
         Object value = this.yieldValue.execute(frame);
         if (value instanceof Completion) {
            Completion completion = (Completion)value;
            value = completion.getValue();
            if (this.returnOrExceptionProfile.profile(completion.isThrow())) {
               return this.throwValue(value);
            } else {
               assert completion.isReturn();

               return this.returnValue(frame, value);
            }
         } else {
            return value;
         }
      }
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return new YieldNode(
         this.context,
         this.stateSlot,
         cloneUninitialized(this.expression, materializedTags),
         cloneUninitialized(this.yieldValue, materializedTags),
         cloneUninitialized(this.returnNode, materializedTags),
         this.generatorYieldNode.cloneUninitialized()
      );
   }
}
