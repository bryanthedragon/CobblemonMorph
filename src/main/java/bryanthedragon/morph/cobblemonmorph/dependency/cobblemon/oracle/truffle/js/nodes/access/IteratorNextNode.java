package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public abstract class IteratorNextNode extends JavaScriptBaseNode {
   @Node.Child
   private JSFunctionCallNode methodCallNode;
   @Node.Child
   private IsObjectNode isObjectNode;
   private final BranchProfile errorBranch;

   protected IteratorNextNode(JSFunctionCallNode methodCallNode, IsObjectNode isObjectNode, BranchProfile errorBranch) {
      this.methodCallNode = methodCallNode;
      this.isObjectNode = isObjectNode;
      this.errorBranch = errorBranch;
   }

   public final Object execute(IteratorRecord iteratorRecord, Object value) {
      Object nextMethod = iteratorRecord.getNextMethod();
      JSDynamicObject iterator = iteratorRecord.getIterator();
      Object result = this.methodCallNode.executeCall(JSArguments.createOneArg(iterator, nextMethod, value));
      if (!this.isObjectNode.executeBoolean(result)) {
         this.errorBranch.enter();
         throw Errors.createTypeErrorIteratorResultNotObject(result, this);
      } else {
         return result;
      }
   }

   public final Object execute(IteratorRecord iteratorRecord) {
      Object nextMethod = iteratorRecord.getNextMethod();
      JSDynamicObject iterator = iteratorRecord.getIterator();
      Object result = this.methodCallNode.executeCall(JSArguments.createZeroArg(iterator, nextMethod));
      if (!this.isObjectNode.executeBoolean(result)) {
         this.errorBranch.enter();
         throw Errors.createTypeErrorIteratorResultNotObject(result, this);
      } else {
         return result;
      }
   }

   public static IteratorNextNode create() {
      return new IteratorNextNode.Cached();
   }

   public static IteratorNextNode getUncached() {
      return IteratorNextNode.Uncached.INSTANCE;
   }

   static final class Cached extends IteratorNextNode {
      Cached() {
         super(JSFunctionCallNode.createCall(), IsObjectNode.create(), BranchProfile.create());
      }
   }

   @DenyReplace
   static final class Uncached extends IteratorNextNode {
      static final IteratorNextNode.Uncached INSTANCE = new IteratorNextNode.Uncached();

      private Uncached() {
         super(JSFunctionCallNode.getUncachedCall(), IsObjectNode.getUncached(), BranchProfile.getUncached());
      }

      @Override
      public boolean isAdoptable() {
         return false;
      }
   }
}
