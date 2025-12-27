package com.oracle.truffle.js.nodes.promise;

import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.IteratorStepNode;
import com.oracle.truffle.js.nodes.access.IteratorValueNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;

public abstract class PerformPromiseCombinatorNode extends JavaScriptBaseNode {
   protected final JSContext context;
   @Node.Child
   private IteratorStepNode iteratorStep;
   @Node.Child
   private IteratorValueNode iteratorValue;

   protected PerformPromiseCombinatorNode(JSContext context) {
      this.context = context;
      this.iteratorStep = IteratorStepNode.create();
      this.iteratorValue = IteratorValueNode.create();
   }

   public abstract JSDynamicObject execute(
      IteratorRecord iteratorRecord, JSDynamicObject constructor, PromiseCapabilityRecord resultCapability, Object promiseResolve
   );

   protected final Object iteratorStepOrSetDone(IteratorRecord iteratorRecord) {
      try {
         return this.iteratorStep.execute(iteratorRecord);
      } catch (Throwable var4) {
         iteratorRecord.setDone(true);
         throw var4;
      }
   }

   protected final Object iteratorValueOrSetDone(IteratorRecord iteratorRecord, Object next) {
      try {
         return this.iteratorValue.execute(next);
      } catch (Throwable var5) {
         iteratorRecord.setDone(true);
         throw var5;
      }
   }

   protected static final class BoxedInt {
      int value;

      BoxedInt(int value) {
         this.value = value;
      }
   }
}
