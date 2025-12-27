package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode;
import com.oracle.truffle.js.nodes.access.WriteNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.UserScriptException;
import com.oracle.truffle.js.runtime.objects.Completion;
import java.util.Set;

public class AsyncGeneratorYieldNode extends AbstractAwaitNode implements ResumableNode.WithIntState {
   @Node.Child
   protected ReturnNode returnNode;
   @Node.Child
   private YieldResultNode generatorYieldNode;

   protected AsyncGeneratorYieldNode(
      JSContext context,
      int stateSlot,
      JavaScriptNode expression,
      JSReadFrameSlotNode readAsyncContextNode,
      JSReadFrameSlotNode readYieldResultNode,
      ReturnNode returnNode
   ) {
      super(context, stateSlot, expression, readAsyncContextNode, readYieldResultNode);
      this.returnNode = returnNode;
      this.generatorYieldNode = new YieldResultNode.ExceptionYieldResultNode();
   }

   public static AsyncGeneratorYieldNode createYield(
      JSContext context,
      int stateSlot,
      JavaScriptNode expression,
      JSReadFrameSlotNode readAsyncContextNode,
      JSReadFrameSlotNode readAsyncResultNode,
      ReturnNode returnNode
   ) {
      return new AsyncGeneratorYieldNode(context, stateSlot, expression, readAsyncContextNode, readAsyncResultNode, returnNode);
   }

   public static AsyncGeneratorYieldNode createYieldStar(
      JSContext context,
      int stateSlot,
      JavaScriptNode expression,
      JSReadFrameSlotNode readAsyncContextNode,
      JSReadFrameSlotNode readAsyncResultNode,
      ReturnNode returnNode,
      int iteratorTempSlot
   ) {
      return new AsyncGeneratorYieldStarNode(context, expression, stateSlot, readAsyncContextNode, readAsyncResultNode, returnNode, iteratorTempSlot);
   }

   @Override
   public Object execute(VirtualFrame frame) {
      int state = this.getStateAsInt(frame, this.stateSlot);
      int awaitValue = 1;
      int suspendedYield = 2;
      int awaitResumptionValue = 3;
      if (state == 0) {
         Object value = this.expression.execute(frame);
         this.setStateAsInt(frame, this.stateSlot, 1);
         return this.suspendAwait(frame, value);
      } else if (state == 1) {
         Object awaited = this.resumeAwait(frame);
         this.setStateAsInt(frame, this.stateSlot, 2);
         return this.suspendYield(frame, awaited);
      } else {
         assert state >= 2;

         this.setStateAsInt(frame, this.stateSlot, 0);
         if (state == 2) {
            Completion completion = this.resumeYield(frame);
            if (completion.isNormal()) {
               return completion.getValue();
            } else if (completion.isThrow()) {
               throw UserScriptException.create(completion.getValue(), this, this.context.getContextOptions().getStackTraceLimit());
            } else {
               assert completion.isReturn();

               this.setStateAsInt(frame, this.stateSlot, 3);
               return this.suspendAwait(frame, completion.getValue());
            }
         } else {
            assert state == 3;

            Object awaited = this.resumeAwait(frame);
            return this.returnValue(frame, awaited);
         }
      }
   }

   protected final Object suspendYield(VirtualFrame frame, Object awaited) {
      return this.generatorYieldNode.generatorYield(frame, awaited);
   }

   protected final Completion resumeYield(VirtualFrame frame) {
      return (Completion)this.readAsyncResultNode.execute(frame);
   }

   protected final Object returnValue(VirtualFrame frame, Object value) {
      assert this.getStateAsInt(frame, this.stateSlot) == 0;

      if (this.returnNode instanceof ReturnNode.FrameReturnNode) {
         ((WriteNode)this.returnNode.expression).executeWrite(frame, value);
      }

      throw new ReturnException(value);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return createYield(
         this.context,
         this.stateSlot,
         cloneUninitialized(this.expression, materializedTags),
         cloneUninitialized(this.readAsyncContextNode, materializedTags),
         cloneUninitialized(this.readAsyncResultNode, materializedTags),
         cloneUninitialized(this.returnNode, materializedTags)
      );
   }
}
