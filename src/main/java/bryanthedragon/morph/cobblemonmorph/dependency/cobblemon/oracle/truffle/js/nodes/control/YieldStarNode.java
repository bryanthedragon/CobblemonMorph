package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.GetIteratorBaseNode;
import com.oracle.truffle.js.nodes.access.GetMethodNode;
import com.oracle.truffle.js.nodes.access.IteratorCloseNode;
import com.oracle.truffle.js.nodes.access.IteratorCompleteNode;
import com.oracle.truffle.js.nodes.access.IteratorNextNode;
import com.oracle.truffle.js.nodes.access.IteratorValueNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.Completion;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

public class YieldStarNode extends AbstractYieldNode implements ResumableNode.WithObjectState {
   @Node.Child
   private GetIteratorBaseNode getIteratorNode;
   @Node.Child
   private IteratorNextNode iteratorNextNode;
   @Node.Child
   private IteratorCompleteNode iteratorCompleteNode;
   @Node.Child
   private IteratorValueNode iteratorValueNode;
   @Node.Child
   private GetMethodNode getThrowMethodNode;
   @Node.Child
   private GetMethodNode getReturnMethodNode;
   @Node.Child
   private JSFunctionCallNode callThrowNode;
   @Node.Child
   private JSFunctionCallNode callReturnNode;
   @Node.Child
   private IteratorCloseNode iteratorCloseNode;
   private final BranchProfile errorBranch = BranchProfile.create();

   protected YieldStarNode(
      JSContext context, int stateSlot, JavaScriptNode expression, JavaScriptNode yieldValue, ReturnNode returnNode, YieldResultNode yieldResultNode
   ) {
      super(context, stateSlot, expression, yieldValue, returnNode, yieldResultNode);
      this.getIteratorNode = GetIteratorBaseNode.create();
      this.iteratorNextNode = IteratorNextNode.create();
      this.iteratorCompleteNode = IteratorCompleteNode.create(context);
      this.iteratorValueNode = IteratorValueNode.create();
      this.getThrowMethodNode = GetMethodNode.create(context, Strings.THROW);
      this.getReturnMethodNode = GetMethodNode.create(context, Strings.RETURN);
      this.callThrowNode = JSFunctionCallNode.createCall();
      this.callReturnNode = JSFunctionCallNode.createCall();
      this.iteratorCloseNode = IteratorCloseNode.create(context);
   }

   private Object executeBegin(VirtualFrame frame) {
      IteratorRecord iteratorRecord = this.getIteratorNode.execute(this.expression.execute(frame));
      Object received = Undefined.instance;
      Object innerResult = this.iteratorNextNode.execute(iteratorRecord, received);
      return this.iteratorCompleteNode.execute(innerResult)
         ? this.iteratorValueNode.execute(innerResult)
         : this.saveStateAndYield(frame, iteratorRecord, innerResult);
   }

   private Object saveStateAndYield(VirtualFrame frame, IteratorRecord iteratorRecord, Object innerResult) {
      this.setState(frame, this.stateSlot, iteratorRecord);
      return this.generatorYield(frame, innerResult);
   }

   @Override
   public Object execute(VirtualFrame frame) {
      Object state = this.getState(frame, this.stateSlot);
      if (state == Undefined.instance) {
         return this.executeBegin(frame);
      } else {
         this.resetState(frame, this.stateSlot);
         IteratorRecord iteratorRecord = (IteratorRecord)state;
         Object received = this.yieldValue.execute(frame);
         if (!(received instanceof Completion)) {
            Object innerResult = this.iteratorNextNode.execute(iteratorRecord, received);
            return this.iteratorCompleteNode.execute(innerResult)
               ? this.iteratorValueNode.execute(innerResult)
               : this.saveStateAndYield(frame, iteratorRecord, innerResult);
         } else {
            Completion completion = (Completion)received;
            received = completion.getValue();
            if (this.returnOrExceptionProfile.profile(completion.isThrow())) {
               return this.resumeThrow(frame, iteratorRecord, received);
            } else {
               assert completion.isReturn();

               return this.resumeReturn(frame, iteratorRecord, received);
            }
         }
      }
   }

   private Object resumeReturn(VirtualFrame frame, IteratorRecord iteratorRecord, Object received) {
      JSDynamicObject iterator = iteratorRecord.getIterator();
      Object returnMethod = this.getReturnMethodNode.executeWithTarget(iterator);
      if (returnMethod == Undefined.instance) {
         return this.returnValue(frame, received);
      } else {
         JSDynamicObject innerReturnResult = this.callReturnMethod(iterator, received, returnMethod);
         return this.iteratorCompleteNode.execute(innerReturnResult)
            ? this.returnValue(frame, this.iteratorValueNode.execute(innerReturnResult))
            : this.saveStateAndYield(frame, iteratorRecord, innerReturnResult);
      }
   }

   private Object resumeThrow(VirtualFrame frame, IteratorRecord iteratorRecord, Object received) {
      JSDynamicObject iterator = iteratorRecord.getIterator();
      Object throwMethod = this.getThrowMethodNode.executeWithTarget(iterator);
      if (throwMethod != Undefined.instance) {
         JSDynamicObject innerResult = this.callThrowMethod(iterator, received, throwMethod);
         return this.iteratorCompleteNode.execute(innerResult)
            ? this.iteratorValueNode.execute(innerResult)
            : this.saveStateAndYield(frame, iteratorRecord, innerResult);
      } else {
         this.errorBranch.enter();
         this.iteratorCloseNode.executeVoid(iterator);
         throw Errors.createTypeErrorYieldStarThrowMethodMissing(this);
      }
   }

   private JSDynamicObject callThrowMethod(JSDynamicObject iterator, Object received, Object throwMethod) {
      Object innerResult = this.callThrowNode.executeCall(JSArguments.createOneArg(iterator, throwMethod, received));
      if (!JSRuntime.isObject(innerResult)) {
         this.errorBranch.enter();
         throw Errors.createTypeErrorIterResultNotAnObject(innerResult, this);
      } else {
         return (JSDynamicObject)innerResult;
      }
   }

   private JSDynamicObject callReturnMethod(JSDynamicObject iterator, Object received, Object returnMethod) {
      Object innerResult = this.callReturnNode.executeCall(JSArguments.createOneArg(iterator, returnMethod, received));
      if (!JSRuntime.isObject(innerResult)) {
         this.errorBranch.enter();
         throw Errors.createTypeErrorIterResultNotAnObject(innerResult, this);
      } else {
         return (JSDynamicObject)innerResult;
      }
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return new YieldStarNode(
         this.context,
         this.stateSlot,
         cloneUninitialized(this.expression, materializedTags),
         cloneUninitialized(this.yieldValue, materializedTags),
         cloneUninitialized(this.returnNode, materializedTags),
         this.generatorYieldNode.cloneUninitialized()
      );
   }
}
