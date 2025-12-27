package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;

@GenerateUncached
public abstract class IteratorStepNode extends JavaScriptBaseNode {
   protected IteratorStepNode() {
   }

   public abstract Object execute(IteratorRecord iteratorRecord);

   @Specialization
   protected static Object step(
      IteratorRecord iteratorRecord,
      @Cached IteratorNextNode iteratorNextNode,
      @Cached("create(getLanguage().getJSContext())") IteratorCompleteNode iteratorCompleteNode
   ) {
      Object result = iteratorNextNode.execute(iteratorRecord);
      Object done = iteratorCompleteNode.execute(result);
      return done == Boolean.TRUE ? false : result;
   }

   public static IteratorStepNode create() {
      return IteratorStepNodeGen.create();
   }

   public static IteratorStepNode getUncached() {
      return IteratorStepNodeGen.getUncached();
   }
}
