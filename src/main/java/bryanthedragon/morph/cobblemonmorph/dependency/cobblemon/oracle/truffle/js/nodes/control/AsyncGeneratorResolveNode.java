package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.CreateIterResultObjectNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.objects.AsyncGeneratorRequest;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.ArrayDeque;

public class AsyncGeneratorResolveNode extends JavaScriptBaseNode {
   @Node.Child
   private PropertyGetNode getAsyncGeneratorQueueNode;
   @Node.Child
   private CreateIterResultObjectNode createIterResultObjectNode;
   @Node.Child
   private JSFunctionCallNode callResolveNode;
   @Node.Child
   private AsyncGeneratorResumeNextNode asyncGeneratorResumeNextNode;

   protected AsyncGeneratorResolveNode(JSContext context) {
      this.createIterResultObjectNode = CreateIterResultObjectNode.create(context);
      this.getAsyncGeneratorQueueNode = PropertyGetNode.createGetHidden(JSFunction.ASYNC_GENERATOR_QUEUE_ID, context);
      this.callResolveNode = JSFunctionCallNode.createCall();
   }

   public static AsyncGeneratorResolveNode create(JSContext context) {
      return new AsyncGeneratorResolveNode(context);
   }

   public Object execute(VirtualFrame frame, JSDynamicObject generator, Object value, boolean done) {
      this.performResolve(frame, generator, value, done);
      if (this.asyncGeneratorResumeNextNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.asyncGeneratorResumeNextNode = this.insert(AsyncGeneratorResumeNextNode.create(this.getContext()));
      }

      this.asyncGeneratorResumeNextNode.execute(frame, generator);
      return Undefined.instance;
   }

   void performResolve(VirtualFrame frame, JSDynamicObject generator, Object value, boolean done) {
      ArrayDeque<AsyncGeneratorRequest> queue = (ArrayDeque<AsyncGeneratorRequest>)this.getAsyncGeneratorQueueNode.getValue(generator);

      assert !queue.isEmpty();

      AsyncGeneratorRequest next = queue.pollFirst();
      PromiseCapabilityRecord promiseCapability = next.getPromiseCapability();
      JSDynamicObject iteratorResult = this.createIterResultObjectNode.execute(frame, value, done);
      Object resolve = promiseCapability.getResolve();
      this.callResolveNode.executeCall(JSArguments.createOneArg(Undefined.instance, resolve, iteratorResult));
   }

   private JSContext getContext() {
      return this.getAsyncGeneratorQueueNode.getContext();
   }
}
