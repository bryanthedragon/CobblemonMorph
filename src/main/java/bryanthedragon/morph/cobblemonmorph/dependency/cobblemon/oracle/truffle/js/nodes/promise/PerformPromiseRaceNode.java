package com.oracle.truffle.js.nodes.promise;

import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.JSPromise;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;

public class PerformPromiseRaceNode extends PerformPromiseCombinatorNode {
   @Node.Child
   private JSFunctionCallNode callResolve = JSFunctionCallNode.createCall();
   @Node.Child
   private PropertyGetNode getThen;
   @Node.Child
   private JSFunctionCallNode callThen;

   protected PerformPromiseRaceNode(JSContext context) {
      super(context);
      this.getThen = PropertyGetNode.create(JSPromise.THEN, false, context);
      this.callThen = JSFunctionCallNode.createCall();
   }

   public static PerformPromiseRaceNode create(JSContext context) {
      return new PerformPromiseRaceNode(context);
   }

   @Override
   public JSDynamicObject execute(IteratorRecord iteratorRecord, JSDynamicObject constructor, PromiseCapabilityRecord resultCapability, Object promiseResolve) {
      assert JSRuntime.isConstructor(constructor);

      assert JSRuntime.isCallable(promiseResolve);

      while (true) {
         Object next = this.iteratorStepOrSetDone(iteratorRecord);
         if (next == Boolean.FALSE) {
            iteratorRecord.setDone(true);
            return resultCapability.getPromise();
         }

         Object nextValue = this.iteratorValueOrSetDone(iteratorRecord, next);
         Object nextPromise = this.callResolve.executeCall(JSArguments.createOneArg(constructor, promiseResolve, nextValue));
         this.callThen
            .executeCall(JSArguments.create(nextPromise, this.getThen.getValue(nextPromise), resultCapability.getResolve(), resultCapability.getReject()));
      }
   }
}
