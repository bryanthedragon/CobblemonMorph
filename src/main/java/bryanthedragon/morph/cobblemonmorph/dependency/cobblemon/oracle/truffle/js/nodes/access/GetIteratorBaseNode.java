package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.interop.ForeignObjectPrototypeNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

@GenerateUncached
@ImportStatic(JSInteropUtil.class)
public abstract class GetIteratorBaseNode extends JavaScriptBaseNode {
   protected GetIteratorBaseNode() {
   }

   public final IteratorRecord execute(Object iteratedObject) {
      return this.execute(iteratedObject, Undefined.instance);
   }

   public abstract IteratorRecord execute(Object iteratedObject, Object method);

   public static GetIteratorBaseNode create() {
      return GetIteratorBaseNodeGen.create();
   }

   public static GetIteratorBaseNode getUncached() {
      return GetIteratorBaseNodeGen.getUncached();
   }

   @Specialization
   protected final IteratorRecord doGetIterator(
      Object items,
      Object methodOpt,
      @Cached(value = "createIteratorMethodNode()", uncached = "uncachedIteratorMethodNode()") GetMethodNode getIteratorMethodNode,
      @Cached IsCallableNode isCallableNode,
      @Cached(value = "createCall()", uncached = "getUncachedCall()") JSFunctionCallNode iteratorCallNode,
      @Cached IsJSObjectNode isObjectNode,
      @Cached(value = "createNextMethodNode()", uncached = "uncachedNextMethodNode()") PropertyGetNode getNextMethodNode,
      @Cached BranchProfile errorBranch
   ) {
      Object method;
      if (methodOpt != Undefined.instance) {
         method = methodOpt;
      } else if (getIteratorMethodNode == null) {
         method = this.getIteratorMethodUncached(items);
      } else {
         method = getIteratorMethodNode.executeWithTarget(items);
      }

      return getIterator(items, method, isCallableNode, iteratorCallNode, isObjectNode, getNextMethodNode, errorBranch, this);
   }

   private Object getIteratorMethodUncached(Object items) {
      Object obj = JSRuntime.toObject(this.getLanguage().getJSContext(), items);
      if (JSRuntime.isForeignObject(obj)) {
         obj = ForeignObjectPrototypeNode.getUncached().execute(obj);
      }

      Object method;
      if (obj instanceof JSDynamicObject) {
         method = JSObject.get((JSDynamicObject)obj, Symbol.SYMBOL_ITERATOR);
      } else {
         method = Undefined.instance;
      }

      return method;
   }

   public static IteratorRecord getIterator(
      Object iteratedObject,
      Object method,
      IsCallableNode isCallableNode,
      JSFunctionCallNode methodCallNode,
      IsJSObjectNode isObjectNode,
      PropertyGetNode getNextMethodNode,
      BranchProfile errorBranch,
      JavaScriptBaseNode origin
   ) {
      if (!isCallableNode.executeBoolean(method)) {
         errorBranch.enter();
         throw Errors.createTypeErrorNotIterable(iteratedObject, origin);
      } else {
         return getIterator(iteratedObject, method, methodCallNode, isObjectNode, getNextMethodNode, origin);
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
         Object nextMethod = getNextMethodNode != null ? getNextMethodNode.getValue(jsIterator) : JSObject.get(jsIterator, Strings.NEXT);
         return IteratorRecord.create(jsIterator, nextMethod, false);
      } else {
         throw Errors.createTypeErrorNotAnObject(iterator, origin);
      }
   }

   GetMethodNode createIteratorMethodNode() {
      return GetMethodNode.create(this.getLanguage().getJSContext(), Symbol.SYMBOL_ITERATOR);
   }

   static GetMethodNode uncachedIteratorMethodNode() {
      return null;
   }

   PropertyGetNode createNextMethodNode() {
      return PropertyGetNode.create(Strings.NEXT, this.getLanguage().getJSContext());
   }

   static PropertyGetNode uncachedNextMethodNode() {
      return null;
   }
}
