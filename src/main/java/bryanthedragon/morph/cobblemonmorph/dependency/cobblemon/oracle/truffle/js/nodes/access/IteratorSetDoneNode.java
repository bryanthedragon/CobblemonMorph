package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import java.util.Set;

public abstract class IteratorSetDoneNode extends JavaScriptNode {
   @Node.Child
   @Executed
   JavaScriptNode iteratorNode;
   @Node.Child
   @Executed
   JavaScriptNode isDoneNode;

   protected IteratorSetDoneNode(JavaScriptNode iteratorNode, JavaScriptNode isDoneNode) {
      this.iteratorNode = iteratorNode;
      this.isDoneNode = isDoneNode;
   }

   public static IteratorSetDoneNode create(JavaScriptNode iteratorNode, JavaScriptNode isDoneNode) {
      return IteratorSetDoneNodeGen.create(iteratorNode, isDoneNode);
   }

   @Specialization
   protected static boolean doIteratorStep(IteratorRecord iteratorRecord, boolean isDone) {
      iteratorRecord.setDone(isDone);
      return isDone;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(cloneUninitialized(this.iteratorNode, materializedTags), cloneUninitialized(this.isDoneNode, materializedTags));
   }
}
