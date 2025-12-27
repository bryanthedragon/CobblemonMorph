package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.Set;

@ImportStatic(JSInteropUtil.class)
public abstract class GetIteratorNode extends JavaScriptNode {
   @Node.Child
   @Executed
   protected JavaScriptNode objectNode;
   @Node.Child
   private GetMethodNode getIteratorMethodNode;
   @Node.Child
   protected PropertyGetNode getNextMethodNode;
   private final BranchProfile errorBranch = BranchProfile.create();
   protected final JSContext context;

   protected GetIteratorNode(JSContext context, JavaScriptNode objectNode) {
      this.context = context;
      this.objectNode = objectNode;
      this.getNextMethodNode = PropertyGetNode.create(Strings.NEXT, context);
   }

   public static GetIteratorNode create(JSContext context, JavaScriptNode iteratedObject) {
      return GetIteratorNodeGen.create(context, iteratedObject);
   }

   public static GetIteratorNode createAsync(JSContext context, JavaScriptNode iteratedObject) {
      return GetAsyncIteratorNodeGen.create(context, iteratedObject);
   }

   @Specialization
   protected IteratorRecord doGetIterator(
      Object iteratedObject,
      @Cached("create()") IsCallableNode isCallableNode,
      @Cached("createCall()") JSFunctionCallNode methodCallNode,
      @Cached("create()") IsJSObjectNode isObjectNode
   ) {
      Object method = this.getIteratorMethodNode().executeWithTarget(iteratedObject);
      return this.getIterator(iteratedObject, method, isCallableNode, methodCallNode, isObjectNode);
   }

   protected final IteratorRecord getIterator(
      Object iteratedObject, Object method, IsCallableNode isCallableNode, JSFunctionCallNode methodCallNode, IsJSObjectNode isObjectNode
   ) {
      if (!isCallableNode.executeBoolean(method)) {
         this.errorBranch.enter();
         throw Errors.createTypeErrorNotIterable(iteratedObject, this);
      } else {
         return getIterator(iteratedObject, method, methodCallNode, isObjectNode, this.getNextMethodNode, this);
      }
   }

   public static IteratorRecord getIterator(
      Object iteratedObject,
      Object method,
      JSFunctionCallNode methodCallNode,
      IsJSObjectNode isObjectNode,
      PropertyGetNode getNextMethodNode,
      JavaScriptBaseNode origin
   ) {
      Object iterator = methodCallNode.executeCall(JSArguments.createZeroArg(iteratedObject, method));
      if (isObjectNode.executeBoolean(iterator)) {
         JSDynamicObject jsIterator = (JSDynamicObject)iterator;
         Object nextMethod = getNextMethodNode.getValue(jsIterator);
         return IteratorRecord.create(jsIterator, nextMethod, false);
      } else {
         throw Errors.createTypeErrorNotAnObject(iterator, origin);
      }
   }

   public abstract IteratorRecord execute(VirtualFrame frame);

   public abstract IteratorRecord execute(Object iteratedObject);

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return GetIteratorNodeGen.create(this.context, cloneUninitialized(this.objectNode, materializedTags));
   }

   protected GetMethodNode getIteratorMethodNode() {
      if (this.getIteratorMethodNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.getIteratorMethodNode = this.insert(GetMethodNode.create(this.context, Symbol.SYMBOL_ITERATOR));
      }

      return this.getIteratorMethodNode;
   }
}
