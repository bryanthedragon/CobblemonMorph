package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import java.util.Set;

public abstract class IteratorIsDoneNode extends JavaScriptNode {
   @Node.Child
   @Executed
   JavaScriptNode iteratorNode;

   protected IteratorIsDoneNode(JavaScriptNode iteratorNode) {
      this.iteratorNode = iteratorNode;
   }

   public static IteratorIsDoneNode create(JavaScriptNode iteratorNode) {
      return IteratorIsDoneNodeGen.create(iteratorNode);
   }

   @Specialization
   protected static boolean doIterator(IteratorRecord iteratorRecord) {
      return iteratorRecord.isDone();
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(cloneUninitialized(this.iteratorNode, materializedTags));
   }
}
