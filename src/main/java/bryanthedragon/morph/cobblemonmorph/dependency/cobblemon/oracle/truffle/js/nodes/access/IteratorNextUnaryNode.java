package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.Set;

public class IteratorNextUnaryNode extends JavaScriptNode {
   @Node.Child
   private JSFunctionCallNode methodCallNode;
   @Node.Child
   private IsObjectNode isObjectNode;
   @Node.Child
   private JavaScriptNode iteratorNode;
   private final BranchProfile errorBranch = BranchProfile.create();

   protected IteratorNextUnaryNode(JavaScriptNode iteratorNode) {
      this.iteratorNode = iteratorNode;
      this.methodCallNode = JSFunctionCallNode.createCall();
      this.isObjectNode = IsObjectNode.create();
   }

   public static JavaScriptNode create(JavaScriptNode iteratorNode) {
      return new IteratorNextUnaryNode(iteratorNode);
   }

   @Override
   public Object execute(VirtualFrame frame) {
      IteratorRecord iterator = (IteratorRecord)this.iteratorNode.execute(frame);
      return this.execute(iterator);
   }

   public Object execute(IteratorRecord iteratorRecord) {
      JSDynamicObject iterator = iteratorRecord.getIterator();
      Object next = iteratorRecord.getNextMethod();
      Object nextResult = this.methodCallNode.executeCall(JSArguments.createZeroArg(iterator, next));
      if (!this.isObjectNode.executeBoolean(nextResult)) {
         this.errorBranch.enter();
         throw Errors.createTypeErrorIteratorResultNotObject(nextResult, this);
      } else {
         return nextResult;
      }
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(cloneUninitialized(this.iteratorNode, materializedTags));
   }
}
