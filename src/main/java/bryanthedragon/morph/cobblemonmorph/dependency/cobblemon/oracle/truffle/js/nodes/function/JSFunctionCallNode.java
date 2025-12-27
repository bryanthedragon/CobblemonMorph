package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnknownKeyException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.access.JSProxyCallNode;
import com.oracle.truffle.js.nodes.access.JSTargetableNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertyNode;
import com.oracle.truffle.js.nodes.access.SuperPropertyReferenceNode;
import com.oracle.truffle.js.nodes.instrumentation.JSInputGeneratingNodeWrapper;
import com.oracle.truffle.js.nodes.instrumentation.JSMaterializedInvokeTargetableNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.instrumentation.NodeObjectDescriptor;
import com.oracle.truffle.js.nodes.interop.ExportArgumentsNode;
import com.oracle.truffle.js.nodes.interop.ForeignObjectPrototypeNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSNoSuchMethodAdapter;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.JavaScriptFunctionCallNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.DebugCounter;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.Lock;

public abstract class JSFunctionCallNode extends JavaScriptNode implements JavaScriptFunctionCallNode {
   private static final DebugCounter megamorphicCount = DebugCounter.create("Megamorphic call site count");
   static final byte CALL = 0;
   static final byte NEW = 1;
   static final byte NEW_TARGET = 2;
   protected final byte flags;
   @Node.Child
   protected JSFunctionCallNode.AbstractCacheNode cacheNode;

   protected JSFunctionCallNode(byte flags) {
      this.flags = flags;
   }

   public static JSFunctionCallNode createCall() {
      return create(false);
   }

   public static JSFunctionCallNode createNew() {
      return create(true);
   }

   public static JSFunctionCallNode createNewTarget() {
      return create(true, true);
   }

   public static JSFunctionCallNode create(boolean isNew) {
      return create(isNew, false);
   }

   public static JSFunctionCallNode create(boolean isNew, boolean isNewTarget) {
      return new JSFunctionCallNode.ExecuteCallNode(createFlags(isNew, isNewTarget));
   }

   private static byte createFlags(boolean isNew, boolean isNewTarget) {
      return (byte)(isNewTarget ? 2 : (isNew ? 1 : 0));
   }

   public static JSFunctionCallNode createCall(JavaScriptNode function, JavaScriptNode target, JavaScriptNode[] arguments, boolean isNew, boolean isNewTarget) {
      byte flags = createFlags(isNew, isNewTarget);
      boolean spread = hasSpreadArgument(arguments);
      if (spread) {
         return new JSFunctionCallNode.CallSpreadNode(target, function, arguments, flags);
      } else if (arguments.length == 0) {
         return new JSFunctionCallNode.Call0Node(target, function, flags);
      } else {
         return (JSFunctionCallNode)(arguments.length == 1
            ? new JSFunctionCallNode.Call1Node(target, function, arguments[0], flags)
            : new JSFunctionCallNode.CallNNode(target, function, arguments, flags));
      }
   }

   public static JSFunctionCallNode createInvoke(JSTargetableNode targetFunction, JavaScriptNode[] arguments, boolean isNew, boolean isNewTarget) {
      byte flags = createFlags(isNew, isNewTarget);
      boolean spread = hasSpreadArgument(arguments);
      if (spread) {
         return new JSFunctionCallNode.InvokeSpreadNode(targetFunction, arguments, flags);
      } else if (arguments.length == 0) {
         return new JSFunctionCallNode.Invoke0Node(targetFunction, flags);
      } else {
         return (JSFunctionCallNode)(arguments.length == 1
            ? new JSFunctionCallNode.Invoke1Node(targetFunction, arguments[0], flags)
            : new JSFunctionCallNode.InvokeNNode(targetFunction, arguments, flags));
      }
   }

   public static JSFunctionCallNode getUncachedCall() {
      return JSFunctionCallNode.Uncached.CALL;
   }

   public static JSFunctionCallNode getUncachedNew() {
      return JSFunctionCallNode.Uncached.NEW;
   }

   static boolean isNewTarget(byte flags) {
      return (flags & 2) != 0;
   }

   static boolean isNew(byte flags) {
      return (flags & 1) != 0;
   }

   private static boolean hasSpreadArgument(JavaScriptNode[] arguments) {
      for (JavaScriptNode arg : arguments) {
         if (arg instanceof SpreadArgumentNode) {
            return true;
         }
      }

      return false;
   }

   public final boolean isNew() {
      return isNew(this.flags);
   }

   public final boolean isInvoke() {
      return this instanceof JSFunctionCallNode.InvokeNode;
   }

   protected Object getPropertyKey() {
      return null;
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.FunctionCallTag.class ? true : super.hasTag(tag);
   }

   @Override
   public Object getNodeObject() {
      NodeObjectDescriptor descriptor = JSTags.createNodeObjectDescriptor();
      descriptor.addProperty("isNew", this.isNew());
      descriptor.addProperty("isInvoke", this.isInvoke());
      return descriptor;
   }

   public static JSFunctionCallNode createInternalCall(JavaScriptNode[] arguments) {
      return createCall(arguments[0], arguments[1], Arrays.copyOfRange(arguments, 2, arguments.length), false, false);
   }

   @ExplodeLoop
   public Object executeCall(Object[] arguments) {
      Object function = JSArguments.getFunctionObject(arguments);

      for (JSFunctionCallNode.AbstractCacheNode c = this.cacheNode; c != null; c = c.nextNode) {
         if (c.accept(function)) {
            return c.executeCall(arguments);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arguments);
   }

   private Object executeAndSpecialize(Object[] arguments) {
      CompilerAsserts.neverPartOfCompilation();
      Object function = JSArguments.getFunctionObject(arguments);
      Lock lock = this.getLock();
      lock.lock();

      JSFunctionCallNode.AbstractCacheNode c;
      try {
         JSFunctionCallNode.AbstractCacheNode currentHead = this.cacheNode;
         int cachedCount = 0;
         boolean generic = false;

         for (c = currentHead; c != null && !c.accept(function); c = c.nextNode) {
            if (isCached(c)) {
               assert !generic;

               cachedCount++;
            } else {
               generic = generic || isGeneric(c);
            }
         }

         if (c == null) {
            JSContext context = this.getLanguage().getJSContext();
            if (cachedCount < context.getFunctionCacheLimit() && !generic && JSFunction.isJSFunction(function)) {
               c = this.specializeDirectCall((JSDynamicObject)function, currentHead);
            }

            if (c == null) {
               boolean hasCached = cachedCount > 0;
               if (JSFunction.isJSFunction(function)) {
                  c = this.specializeGenericFunction(currentHead, hasCached);
               } else if (JSProxy.isJSProxy(function)) {
                  c = this.insertAtFront(this.specializeProxyCall(function, context), currentHead);
               } else if (JSGuards.isForeignObject(function)) {
                  c = this.specializeForeignCall(arguments, currentHead);
               } else if (function instanceof JSNoSuchMethodAdapter) {
                  c = this.insertAtFront(new JSFunctionCallNode.JSNoSuchMethodAdapterCacheNode(), currentHead);
               } else {
                  c = this.insertAtFront(new JSFunctionCallNode.GenericFallbackCacheNode(), dropCachedNodes(currentHead, hasCached));
               }
            }

            assert c.getParent() != null;
         }
      } finally {
         lock.unlock();
      }

      if (c.accept(function)) {
         return c.executeCall(arguments);
      } else {
         throw CompilerDirectives.shouldNotReachHere("Inconsistent guard.");
      }
   }

   private JSFunctionCallNode.AbstractCacheNode specializeProxyCall(Object function, JSContext context) {
      assert JSProxy.isJSProxy(function);

      return (JSFunctionCallNode.AbstractCacheNode)(this.getParent() instanceof JSProxyCallNode
         ? new JSFunctionCallNode.JSProxyCallCacheNode(isNew(this.flags), isNewTarget(this.flags), context)
         : new JSFunctionCallNode.JSProxyInlineCacheNode(isNew(this.flags), isNewTarget(this.flags), context));
   }

   private static boolean isCached(JSFunctionCallNode.AbstractCacheNode c) {
      return c instanceof JSFunctionCallNode.JSFunctionCacheNode;
   }

   private static boolean isGeneric(JSFunctionCallNode.AbstractCacheNode c) {
      return c instanceof JSFunctionCallNode.GenericJSFunctionCacheNode || c instanceof JSFunctionCallNode.GenericFallbackCacheNode;
   }

   private static boolean isUncached(JSFunctionCallNode.AbstractCacheNode c) {
      return c instanceof JSFunctionCallNode.JSProxyInlineCacheNode
         || c instanceof JSFunctionCallNode.JSProxyCallCacheNode
         || c instanceof JSFunctionCallNode.ForeignCallNode
         || c instanceof JSFunctionCallNode.JSNoSuchMethodAdapterCacheNode;
   }

   private static int getCachedCount(JSFunctionCallNode.AbstractCacheNode head) {
      int count = 0;

      for (JSFunctionCallNode.AbstractCacheNode c = head; c != null; c = c.nextNode) {
         if (isCached(c)) {
            count++;
         }
      }

      return count;
   }

   private JSFunctionCallNode.AbstractCacheNode specializeDirectCall(JSDynamicObject functionObj, JSFunctionCallNode.AbstractCacheNode head) {
      assert JSFunction.isJSFunction(functionObj);

      JSFunctionData functionData = JSFunction.getFunctionData(functionObj);
      return !functionData.getContext().isMultiContext()
         ? this.specializeDirectCallInstance(functionObj, functionData, head)
         : this.specializeDirectCallShared(functionObj, functionData, head);
   }

   private JSFunctionCallNode.JSFunctionCacheNode specializeDirectCallInstance(
      JSDynamicObject functionObj, JSFunctionData functionData, JSFunctionCallNode.AbstractCacheNode head
   ) {
      JSFunctionCallNode.JSFunctionCacheNode obsoleteNode = null;
      JSFunctionCallNode.AbstractCacheNode previousNode = null;
      JSFunctionCallNode.AbstractCacheNode p = null;

      for (JSFunctionCallNode.AbstractCacheNode c = head; c != null; c = c.nextNode) {
         if (c instanceof JSFunctionCallNode.JSFunctionCacheNode) {
            JSFunctionCallNode.JSFunctionCacheNode current = (JSFunctionCallNode.JSFunctionCacheNode)c;
            if (current.isInstanceCache() && functionData == current.getFunctionData()) {
               obsoleteNode = current;
               previousNode = p;
               break;
            }
         }

         p = c;
      }

      if (obsoleteNode == null) {
         JSFunctionCallNode.JSFunctionCacheNode directCall = createCallableNode(functionObj, functionData, isNew(this.flags), isNewTarget(this.flags), true);
         return this.insertAtFront(directCall, head);
      } else {
         JSFunctionCallNode.JSFunctionCacheNode newNode;
         if (obsoleteNode instanceof JSFunctionCallNode.FunctionInstanceCacheNode) {
            DirectCallNode callNode = ((JSFunctionCallNode.FunctionInstanceCacheNode)obsoleteNode).callNode;
            if (functionData.isBound()) {
               newNode = new JSFunctionCallNode.BoundFunctionDataCacheNode(functionData, callNode);
            } else {
               newNode = new JSFunctionCallNode.UnboundFunctionDataCacheNode(functionData, callNode);
            }
         } else {
            newNode = createCallableNode(functionObj, functionData, isNew(this.flags), isNewTarget(this.flags), false);
         }

         return this.replaceCached(newNode, head, obsoleteNode, previousNode);
      }
   }

   private JSFunctionCallNode.JSFunctionCacheNode specializeDirectCallShared(
      JSDynamicObject functionObj, JSFunctionData functionData, JSFunctionCallNode.AbstractCacheNode head
   ) {
      JSFunctionCallNode.JSFunctionCacheNode directCall = createCallableNode(functionObj, functionData, isNew(this.flags), isNewTarget(this.flags), false);
      return this.insertAtFront(directCall, head);
   }

   private JSFunctionCallNode.AbstractCacheNode specializeGenericFunction(JSFunctionCallNode.AbstractCacheNode head, boolean hasCached) {
      JSFunctionCallNode.AbstractCacheNode otherGeneric = dropCachedNodes(head, hasCached);
      JSFunctionCallNode.AbstractCacheNode newNode = new JSFunctionCallNode.GenericJSFunctionCacheNode(this.flags, otherGeneric);
      this.insert(newNode);
      this.cacheNode = newNode;
      this.reportPolymorphicSpecialize();
      return newNode;
   }

   private static JSFunctionCallNode.AbstractCacheNode dropCachedNodes(JSFunctionCallNode.AbstractCacheNode head, boolean hasCached) {
      if (!hasCached) {
         assert getCachedCount(head) == 0;

         return head;
      } else {
         JSFunctionCallNode.AbstractCacheNode gen = null;

         for (JSFunctionCallNode.AbstractCacheNode c = head; c != null; c = c.nextNode) {
            if (!isCached(c)) {
               assert isGeneric(c) || isUncached(c);

               gen = c.withNext(gen);
            }
         }

         return gen;
      }
   }

   private JSFunctionCallNode.AbstractCacheNode specializeForeignCall(Object[] arguments, JSFunctionCallNode.AbstractCacheNode head) {
      JSFunctionCallNode.AbstractCacheNode newNode = null;
      int userArgumentCount = JSArguments.getUserArgumentCount(arguments);
      Object thisObject = JSArguments.getThisObject(arguments);
      if (isNew(this.flags) || isNewTarget(this.flags)) {
         int skippedArgs = isNewTarget(this.flags) ? 1 : 0;
         newNode = new JSFunctionCallNode.ForeignInstantiateNode(skippedArgs, userArgumentCount - skippedArgs);
      } else if (JSGuards.isForeignObject(thisObject)) {
         Object propertyKey = this.getPropertyKey();
         if (Strings.isTString(propertyKey)) {
            newNode = new JSFunctionCallNode.ForeignInvokeNode((TruffleString)propertyKey, userArgumentCount);
         }
      }

      if (newNode == null) {
         newNode = new JSFunctionCallNode.ForeignExecuteNode(userArgumentCount);
      }

      return this.insertAtFront(newNode, head);
   }

   private <T extends JSFunctionCallNode.AbstractCacheNode> T insertAtFront(T newNode, JSFunctionCallNode.AbstractCacheNode head) {
      this.insert(newNode);
      newNode.nextNode = head;
      this.cacheNode = newNode;
      return newNode;
   }

   private <T extends JSFunctionCallNode.AbstractCacheNode> T replaceCached(
      T newNode,
      JSFunctionCallNode.AbstractCacheNode head,
      JSFunctionCallNode.AbstractCacheNode obsoleteNode,
      JSFunctionCallNode.AbstractCacheNode previousNode
   ) {
      assert previousNode == null || previousNode.nextNode == obsoleteNode;

      this.insert(newNode);
      newNode.nextNode = obsoleteNode.nextNode;
      if (previousNode != null) {
         previousNode.nextNode = newNode;
      } else {
         this.cacheNode = newNode;
      }

      return newNode;
   }

   @Override
   public NodeCost getCost() {
      if (this.cacheNode == null) {
         return NodeCost.UNINITIALIZED;
      } else if (isGeneric(this.cacheNode)) {
         return NodeCost.MEGAMORPHIC;
      } else {
         return this.cacheNode.nextNode != null ? NodeCost.POLYMORPHIC : NodeCost.MONOMORPHIC;
      }
   }

   public JavaScriptNode getTarget() {
      return null;
   }

   protected final Object evaluateReceiver(VirtualFrame frame, Object target) {
      JavaScriptNode targetNode = this.getTarget();
      return targetNode instanceof SuperPropertyReferenceNode ? ((SuperPropertyReferenceNode)targetNode).evaluateTarget(frame) : target;
   }

   @ExplodeLoop
   protected static Object[] executeFillObjectArraySpread(
      JavaScriptNode[] arguments, VirtualFrame frame, Object[] args, int fixedArgumentsLength, BranchProfile growProfile
   ) {
      SimpleArrayList<Object> argList = SimpleArrayList.create((long)fixedArgumentsLength + arguments.length + 3L);

      for (int i = 0; i < fixedArgumentsLength; i++) {
         argList.addUnchecked(args[i]);
      }

      for (int i = 0; i < arguments.length; i++) {
         if (arguments[i] instanceof SpreadArgumentNode) {
            ((SpreadArgumentNode)arguments[i]).executeToList(frame, argList, growProfile);
         } else {
            argList.add(arguments[i].execute(frame), growProfile);
         }
      }

      return argList.toArray();
   }

   protected static JSFunctionCallNode.JSFunctionCacheNode createCallableNode(
      JSDynamicObject function, JSFunctionData functionData, boolean isNew, boolean isNewTarget, boolean cacheOnInstance
   ) {
      CallTarget callTarget = getCallTarget(functionData, isNew, isNewTarget);

      assert callTarget != null;

      if (!JSFunction.isBoundFunction(function) || !isBoundFunctionNestingDepthWithinLimits(function)) {
         JSFunctionCallNode.JSFunctionCacheNode node = tryInlineBuiltinFunctionCall(function, functionData, callTarget, cacheOnInstance);
         if (node != null) {
            return node;
         } else if (cacheOnInstance) {
            return new JSFunctionCallNode.FunctionInstanceCacheNode(function, callTarget);
         } else {
            return (JSFunctionCallNode.JSFunctionCacheNode)(JSFunction.isBoundFunction(function)
               ? new JSFunctionCallNode.BoundFunctionDataCacheNode(functionData, callTarget)
               : new JSFunctionCallNode.UnboundFunctionDataCacheNode(functionData, callTarget));
         }
      } else {
         return (JSFunctionCallNode.JSFunctionCacheNode)(cacheOnInstance
            ? new JSFunctionCallNode.BoundFunctionInstanceCallNode(function, isNew, isNewTarget)
            : new JSFunctionCallNode.DynamicBoundFunctionCallNode(isNew, isNewTarget, functionData));
      }
   }

   private static boolean isBoundFunctionNestingDepthWithinLimits(JSDynamicObject function) {
      JSDynamicObject boundFunction = function;

      for (int i = 0; i < 10; i++) {
         boundFunction = JSFunction.getBoundTargetFunction(boundFunction);
         if (!JSFunction.isBoundFunction(boundFunction)) {
            return true;
         }
      }

      return false;
   }

   protected static CallTarget getCallTarget(JSFunctionData functionData, boolean isNew, boolean isNewTarget) {
      if (isNewTarget) {
         return functionData.getConstructNewTarget();
      } else {
         return isNew ? functionData.getConstructTarget() : functionData.getCallTarget();
      }
   }

   private static JSFunctionCallNode.JSFunctionCacheNode tryInlineBuiltinFunctionCall(
      JSDynamicObject function, JSFunctionData functionData, CallTarget callTarget, boolean cacheOnInstance
   ) {
      if (callTarget instanceof RootCallTarget) {
         RootNode rootNode = ((RootCallTarget)callTarget).getRootNode();
         if (rootNode instanceof FunctionRootNode) {
            JavaScriptNode body = ((FunctionRootNode)rootNode).getBody();
            if (body instanceof JSBuiltinNode) {
               JSBuiltinNode builtinNode = (JSBuiltinNode)body;
               JSBuiltinNode.Inlined inlined = builtinNode.tryCreateInlined();
               if (inlined != null) {
                  if (cacheOnInstance) {
                     return new JSFunctionCallNode.InlinedBuiltinFunctionInstanceCacheNode(function, callTarget, inlined);
                  }

                  return new JSFunctionCallNode.InlinedBuiltinFunctionDataCacheNode(functionData, callTarget, inlined);
               }

               if (builtinNode.isCallerSensitive()) {
                  if (cacheOnInstance) {
                     return new JSFunctionCallNode.CallerSensitiveBuiltinFunctionInstanceCacheNode(function, functionData, callTarget);
                  }

                  return new JSFunctionCallNode.CallerSensitiveBuiltinFunctionDataCacheNode(functionData, callTarget);
               }
            }
         }
      }

      return null;
   }

   private abstract static class AbstractCacheNode extends JavaScriptBaseNode {
      @Node.Child
      protected JSFunctionCallNode.AbstractCacheNode nextNode;

      protected abstract boolean accept(Object function);

      public abstract Object executeCall(Object[] arguments);

      protected JSFunctionCallNode.AbstractCacheNode withNext(JSFunctionCallNode.AbstractCacheNode newNext) {
         JSFunctionCallNode.AbstractCacheNode copy = (JSFunctionCallNode.AbstractCacheNode)this.copy();
         copy.nextNode = newNext;
         return copy;
      }

      @Override
      public final NodeCost getCost() {
         return NodeCost.NONE;
      }
   }

   private static final class BoundFunctionDataCacheNode extends JSFunctionCallNode.UnboundJSFunctionCacheNode {
      private final JSFunctionData functionData;

      BoundFunctionDataCacheNode(JSFunctionData functionData, CallTarget callTarget) {
         super(callTarget);
         this.functionData = functionData;

         assert functionData.isBound();
      }

      BoundFunctionDataCacheNode(JSFunctionData functionData, DirectCallNode directCallNode) {
         super(directCallNode);
         this.functionData = functionData;
      }

      @Override
      protected boolean accept(Object function) {
         return function instanceof JSFunctionObject.Bound
            && JSFunction.getFunctionData((JSFunctionObject)((JSFunctionObject.Bound)function)) == this.functionData;
      }

      @Override
      protected JSFunctionData getFunctionData() {
         return this.functionData;
      }
   }

   private static final class BoundFunctionInstanceCallNode extends JSFunctionCallNode.JSFunctionCacheNode {
      @Node.Child
      private JSFunctionCallNode.AbstractCacheNode boundNode;
      private final JSDynamicObject boundFunctionObj;
      private final Object boundThis;
      private final JSDynamicObject targetFunctionObj;
      private final Object[] addArguments;
      private final boolean useDynamicThis;
      private final boolean isNewTarget;

      BoundFunctionInstanceCallNode(JSDynamicObject function, boolean isNew, boolean isNewTarget) {
         assert JSFunction.isBoundFunction(function);

         this.boundFunctionObj = function;
         JSDynamicObject lastFunction = function;
         List<Object> prefixArguments = new ArrayList<>();

         Object lastReceiver;
         do {
            Object[] extraArguments = JSFunction.getBoundArguments(lastFunction);
            prefixArguments.addAll(0, Arrays.asList(extraArguments));
            lastReceiver = JSFunction.getBoundThis(lastFunction);
            lastFunction = JSFunction.getBoundTargetFunction(lastFunction);
         } while (JSFunction.isBoundFunction(lastFunction) && !isNewTarget);

         this.addArguments = prefixArguments.toArray(JSArguments.EMPTY_ARGUMENTS_ARRAY);
         this.targetFunctionObj = lastFunction;
         if (!isNew && !isNewTarget) {
            this.useDynamicThis = false;
            this.boundThis = lastReceiver;
         } else {
            this.useDynamicThis = true;
            this.boundThis = null;
         }

         this.isNewTarget = isNewTarget;
         this.boundNode = JSFunctionCallNode.createCallableNode(lastFunction, JSFunction.getFunctionData(lastFunction), isNew, isNewTarget, true);
      }

      @Override
      public Object executeCall(Object[] arguments) {
         assert this.checkTargetFunction(arguments);

         return this.boundNode.executeCall(this.bindExtraArguments(arguments));
      }

      private Object[] bindExtraArguments(Object[] origArgs) {
         Object target = this.useDynamicThis ? JSArguments.getThisObject(origArgs) : this.boundThis;
         int skip = this.isNewTarget ? 1 : 0;
         Object[] origUserArgs = JSArguments.extractUserArguments(origArgs, skip);
         int newUserArgCount = this.addArguments.length + origUserArgs.length;
         Object[] arguments = JSArguments.createInitial(target, this.targetFunctionObj, skip + newUserArgCount);
         JSArguments.setUserArguments(arguments, skip, this.addArguments);
         JSArguments.setUserArguments(arguments, skip + this.addArguments.length, origUserArgs);
         if (this.isNewTarget) {
            Object newTarget = JSArguments.getNewTarget(origArgs);
            if (newTarget == JSArguments.getFunctionObject(origArgs)) {
               newTarget = this.targetFunctionObj;
            }

            arguments[2] = newTarget;
         }

         return arguments;
      }

      private boolean checkTargetFunction(Object[] arguments) {
         JSDynamicObject targetFunction = (JSDynamicObject)JSArguments.getFunctionObject(arguments);

         while (JSFunction.isBoundFunction(targetFunction)) {
            targetFunction = JSFunction.getBoundTargetFunction(targetFunction);
            if (this.isNewTarget) {
               return this.targetFunctionObj == targetFunction;
            }
         }

         return this.targetFunctionObj == targetFunction;
      }

      @Override
      protected boolean accept(Object function) {
         return function == this.boundFunctionObj;
      }

      @Override
      protected JSFunctionData getFunctionData() {
         return JSFunction.getFunctionData(this.boundFunctionObj);
      }

      @Override
      protected boolean isInstanceCache() {
         return true;
      }
   }

   static class Call0Node extends JSFunctionCallNode.CallNode {
      protected Call0Node(JavaScriptNode targetNode, JavaScriptNode functionNode, byte flags) {
         super(targetNode, functionNode, flags);
      }

      @Override
      protected final Object[] createArguments(VirtualFrame frame, Object target, Object function) {
         return JSArguments.createZeroArg(target, function);
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new JSFunctionCallNode.Call0Node(
            cloneUninitialized(this.targetNode, materializedTags), cloneUninitialized(this.functionNode, materializedTags), this.flags
         );
      }

      @Override
      protected JavaScriptNode[] getArgumentNodes() {
         return new JavaScriptNode[0];
      }

      @Override
      protected void materializeInstrumentableArguments() {
      }
   }

   static class Call1Node extends JSFunctionCallNode.CallNode {
      @Node.Child
      protected JavaScriptNode argument0;

      protected Call1Node(JavaScriptNode targetNode, JavaScriptNode functionNode, JavaScriptNode argument0, byte flags) {
         super(targetNode, functionNode, flags);
         this.argument0 = argument0;
      }

      @Override
      protected final Object[] createArguments(VirtualFrame frame, Object target, Object function) {
         Object arg0 = this.argument0.execute(frame);
         return JSArguments.createOneArg(target, function, arg0);
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new JSFunctionCallNode.Call1Node(
            cloneUninitialized(this.targetNode, materializedTags),
            cloneUninitialized(this.functionNode, materializedTags),
            cloneUninitialized(this.argument0, materializedTags),
            this.flags
         );
      }

      @Override
      protected JavaScriptNode[] getArgumentNodes() {
         return new JavaScriptNode[]{this.argument0};
      }

      @Override
      protected void materializeInstrumentableArguments() {
         if (!this.argument0.isInstrumentable()) {
            this.argument0 = this.insert(JSInputGeneratingNodeWrapper.create(this.argument0));
         }
      }
   }

   static class CallNNode extends JSFunctionCallNode.CallNode {
      @Node.Children
      protected final JavaScriptNode[] arguments;

      protected CallNNode(JavaScriptNode targetNode, JavaScriptNode functionNode, JavaScriptNode[] arguments, byte flags) {
         super(targetNode, functionNode, flags);
         this.arguments = arguments;
      }

      @Override
      protected final Object[] createArguments(VirtualFrame frame, Object target, Object function) {
         Object[] args = JSArguments.createInitial(target, function, this.arguments.length);
         return this.executeFillObjectArray(frame, args, 2);
      }

      @ExplodeLoop
      protected Object[] executeFillObjectArray(VirtualFrame frame, Object[] args, int delta) {
         for (int i = 0; i < this.arguments.length; i++) {
            assert !(this.arguments[i] instanceof SpreadArgumentNode);

            args[i + delta] = this.arguments[i].execute(frame);
         }

         return args;
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new JSFunctionCallNode.CallNNode(
            cloneUninitialized(this.targetNode, materializedTags),
            cloneUninitialized(this.functionNode, materializedTags),
            cloneUninitialized(this.arguments, materializedTags),
            this.flags
         );
      }

      @Override
      protected JavaScriptNode[] getArgumentNodes() {
         return this.arguments;
      }

      @Override
      protected void materializeInstrumentableArguments() {
         for (int i = 0; i < this.arguments.length; i++) {
            if (!(this.arguments[i] instanceof SpreadArgumentNode) && !this.arguments[i].isInstrumentable()) {
               this.arguments[i] = this.insert(JSInputGeneratingNodeWrapper.create(this.arguments[i]));
            }
         }
      }
   }

   abstract static class CallNode extends JSFunctionCallNode {
      @Node.Child
      protected JavaScriptNode targetNode;
      @Node.Child
      protected JavaScriptNode functionNode;

      protected CallNode(JavaScriptNode targetNode, JavaScriptNode functionNode, byte flags) {
         super(flags);
         this.targetNode = targetNode;
         this.functionNode = functionNode;
      }

      @Override
      public final JavaScriptNode getTarget() {
         return this.targetNode;
      }

      public final JavaScriptNode getFunction() {
         return this.functionNode;
      }

      protected abstract Object[] createArguments(VirtualFrame frame, Object target, Object function);

      protected abstract JavaScriptNode[] getArgumentNodes();

      protected abstract void materializeInstrumentableArguments();

      @Override
      public Object execute(VirtualFrame frame) {
         Object target = this.executeTarget(frame);
         Object receiver = this.evaluateReceiver(frame, target);
         Object function = this.functionNode.execute(frame);
         return this.executeCall(this.createArguments(frame, receiver, function));
      }

      final Object executeTarget(VirtualFrame frame) {
         return this.targetNode != null ? this.targetNode.execute(frame) : Undefined.instance;
      }

      @Override
      public String expressionToString() {
         return Objects.toString(this.functionNode.expressionToString(), "(intermediate value)") + "(...)";
      }

      @Override
      protected Object getPropertyKey() {
         return null;
      }

      @Override
      public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
         if (materializedTags.contains(JSTags.FunctionCallTag.class)) {
            this.materializeInstrumentableArguments();
            if (this.hasSourceSection() && !this.functionNode.hasSourceSection()) {
               transferSourceSectionAddExpressionTag(this, this.functionNode);
            }

            if (this.targetNode != null) {
               return this;
            } else {
               JavaScriptNode materializedTargetNode = JSInputGeneratingNodeWrapper.create(JSConstantNode.JSConstantUndefinedNode.createUndefined());
               JavaScriptNode call = createCall(
                  cloneUninitialized(this.functionNode, materializedTags),
                  materializedTargetNode,
                  cloneUninitialized(this.getArgumentNodes(), materializedTags),
                  isNew(this.flags),
                  isNewTarget(this.flags)
               );
               transferSourceSectionAndTags(this, call);
               return call;
            }
         } else {
            return this;
         }
      }
   }

   static class CallSpreadNode extends JSFunctionCallNode.CallNNode {
      private final BranchProfile growProfile = BranchProfile.create();

      protected CallSpreadNode(JavaScriptNode targetNode, JavaScriptNode functionNode, JavaScriptNode[] arguments, byte flags) {
         super(targetNode, functionNode, arguments, flags);
      }

      @Override
      protected Object[] executeFillObjectArray(VirtualFrame frame, Object[] args, int delta) {
         return executeFillObjectArraySpread(this.arguments, frame, args, delta, this.growProfile);
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new JSFunctionCallNode.CallSpreadNode(
            cloneUninitialized(this.targetNode, materializedTags),
            cloneUninitialized(this.functionNode, materializedTags),
            cloneUninitialized(this.arguments, materializedTags),
            this.flags
         );
      }
   }

   private abstract static class CallerSensitiveBuiltinCallNode extends JSFunctionCallNode.JSFunctionCacheNode {
      @Node.Child
      private DirectCallNode callNode;
      protected final JSFunctionData functionData;

      CallerSensitiveBuiltinCallNode(JSFunctionData functionData, CallTarget callTarget) {
         this.functionData = functionData;
         this.callNode = Truffle.getRuntime().createDirectCallNode(callTarget);
      }

      @Override
      public Object executeCall(Object[] arguments) {
         JSRealm realm = this.getRealm();
         JavaScriptBaseNode prev = realm.getCallNode();

         Object var4;
         try {
            realm.setCallNode(this);
            var4 = this.callNode.call(arguments);
         } finally {
            realm.setCallNode(prev);
         }

         return var4;
      }

      @Override
      protected final JSFunctionData getFunctionData() {
         return this.functionData;
      }
   }

   private static final class CallerSensitiveBuiltinFunctionDataCacheNode extends JSFunctionCallNode.CallerSensitiveBuiltinCallNode {
      CallerSensitiveBuiltinFunctionDataCacheNode(JSFunctionData functionData, CallTarget callTarget) {
         super(functionData, callTarget);
      }

      @Override
      protected boolean accept(Object function) {
         return JSFunction.isJSFunction(function) && this.functionData == JSFunction.getFunctionData((JSDynamicObject)function);
      }
   }

   private static final class CallerSensitiveBuiltinFunctionInstanceCacheNode extends JSFunctionCallNode.CallerSensitiveBuiltinCallNode {
      private final JSDynamicObject functionObj;

      CallerSensitiveBuiltinFunctionInstanceCacheNode(JSDynamicObject functionObj, JSFunctionData functionData, CallTarget callTarget) {
         super(functionData, callTarget);

         assert JSFunction.isJSFunction(functionObj);

         this.functionObj = functionObj;
      }

      @Override
      protected boolean accept(Object function) {
         return this.functionObj == function;
      }

      @Override
      protected boolean isInstanceCache() {
         return true;
      }
   }

   private static final class DynamicBoundFunctionCallNode extends JSFunctionCallNode.JSFunctionCacheNode {
      @Node.Child
      private JSFunctionCallNode boundTargetCallNode;
      private final boolean useDynamicThis;
      private final boolean isNewTarget;
      private final JSFunctionData boundFunctionData;

      DynamicBoundFunctionCallNode(boolean isNew, boolean isNewTarget, JSFunctionData boundFunctionData) {
         this.useDynamicThis = isNew || isNewTarget;
         this.isNewTarget = isNewTarget;
         this.boundFunctionData = boundFunctionData;
         this.boundTargetCallNode = JSFunctionCallNode.create(isNew, isNewTarget);
      }

      @Override
      public Object executeCall(Object[] arguments) {
         return this.boundTargetCallNode.executeCall(this.bindExtraArguments(arguments));
      }

      private Object[] bindExtraArguments(Object[] origArgs) {
         JSDynamicObject function = (JSDynamicObject)JSArguments.getFunctionObject(origArgs);
         if (!JSFunction.isBoundFunction(function)) {
            throw Errors.shouldNotReachHere();
         } else {
            JSDynamicObject boundTargetFunction = JSFunction.getBoundTargetFunction(function);
            Object boundThis = this.useDynamicThis ? JSArguments.getThisObject(origArgs) : JSFunction.getBoundThis(function);
            Object[] boundArguments = JSFunction.getBoundArguments(function);
            int skip = this.isNewTarget ? 1 : 0;
            Object[] origUserArgs = JSArguments.extractUserArguments(origArgs, skip);
            int newUserArgCount = boundArguments.length + origUserArgs.length;
            Object[] arguments = JSArguments.createInitial(boundThis, boundTargetFunction, skip + newUserArgCount);
            JSArguments.setUserArguments(arguments, skip, boundArguments);
            JSArguments.setUserArguments(arguments, skip + boundArguments.length, origUserArgs);
            if (this.isNewTarget) {
               Object newTarget = JSArguments.getNewTarget(origArgs);
               if (newTarget == function) {
                  newTarget = boundTargetFunction;
               }

               arguments[2] = newTarget;
            }

            return arguments;
         }
      }

      @Override
      protected boolean accept(Object function) {
         return JSFunction.isJSFunction(function) && this.boundFunctionData == JSFunction.getFunctionData((JSDynamicObject)function);
      }

      @Override
      protected JSFunctionData getFunctionData() {
         return this.boundFunctionData;
      }
   }

   static class ExecuteCallNode extends JSFunctionCallNode {
      protected ExecuteCallNode(byte flags) {
         super(flags);
      }

      @Override
      public Object execute(VirtualFrame frame) {
         throw Errors.shouldNotReachHere();
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new JSFunctionCallNode.ExecuteCallNode(this.flags);
      }
   }

   private abstract static class ForeignCallNode extends JSFunctionCallNode.AbstractCacheNode {
      @Node.Child
      private ExportArgumentsNode exportArgumentsNode;
      @Node.Child
      private ImportValueNode typeConvertNode;
      private final ValueProfile functionClassProfile = ValueProfile.createClassProfile();

      ForeignCallNode(int expectedArgumentCount) {
         this.exportArgumentsNode = ExportArgumentsNode.create(expectedArgumentCount);
         this.typeConvertNode = ImportValueNode.create();
      }

      @Override
      protected boolean accept(Object function) {
         return JSGuards.isForeignObject(this.functionClassProfile.profile(function));
      }

      protected final Object getForeignFunction(Object[] arguments) {
         return this.functionClassProfile.profile(JSArguments.getFunctionObject(arguments));
      }

      protected final Object[] exportArguments(Object[] arguments) {
         return this.exportArgumentsNode.export(JSArguments.extractUserArguments(arguments));
      }

      protected final Object[] exportArguments(Object[] arguments, int skip) {
         return this.exportArgumentsNode.export(JSArguments.extractUserArguments(arguments, skip));
      }

      protected final Object convertForeignReturn(Object returnValue) {
         return this.typeConvertNode.executeWithTarget(returnValue);
      }
   }

   private static class ForeignExecuteNode extends JSFunctionCallNode.ForeignCallNode {
      @Node.Child
      protected InteropLibrary interop = InteropLibrary.getFactory().createDispatched(5);

      ForeignExecuteNode(int expectedArgumentCount) {
         super(expectedArgumentCount);
      }

      @Override
      public Object executeCall(Object[] arguments) {
         Object function = this.getForeignFunction(arguments);
         Object[] callArguments = this.exportArguments(arguments);

         try {
            return this.convertForeignReturn(this.interop.execute(function, callArguments));
         } catch (ArityException | UnsupportedMessageException | UnsupportedTypeException var5) {
            throw Errors.createTypeErrorInteropException(function, var5, "execute", this);
         }
      }
   }

   private static class ForeignInstantiateNode extends JSFunctionCallNode.ForeignCallNode {
      @Node.Child
      protected InteropLibrary interop;
      private final int skip;

      ForeignInstantiateNode(int skip, int expectedArgumentCount) {
         super(expectedArgumentCount);
         this.skip = skip;
         this.interop = InteropLibrary.getFactory().createDispatched(5);
      }

      @Override
      public Object executeCall(Object[] arguments) {
         Object function = this.getForeignFunction(arguments);
         Object[] callArguments = this.exportArguments(arguments, this.skip);

         try {
            return this.convertForeignReturn(this.interop.instantiate(function, callArguments));
         } catch (ArityException | UnsupportedMessageException | UnsupportedTypeException var5) {
            throw Errors.createTypeErrorInteropException(function, var5, "instantiate", this);
         }
      }
   }

   private static final class ForeignInvokeNode extends JSFunctionCallNode.ForeignExecuteNode {
      private final TruffleString functionName;
      private final String functionNameJavaString;
      private final ValueProfile thisClassProfile = ValueProfile.createClassProfile();
      @Node.Child
      private ForeignObjectPrototypeNode foreignObjectPrototypeNode;
      @Node.Child
      protected JSFunctionCallNode callJSFunctionNode;
      @Node.Child
      protected PropertyGetNode getFunctionNode;
      private final BranchProfile errorBranch = BranchProfile.create();
      @CompilerDirectives.CompilationFinal
      private boolean optimistic = true;

      ForeignInvokeNode(TruffleString functionName, int expectedArgumentCount) {
         super(expectedArgumentCount);
         this.functionName = functionName;
         this.functionNameJavaString = Strings.toJavaString(functionName);
      }

      @Override
      public Object executeCall(Object[] arguments) {
         Object receiver = this.thisClassProfile.profile(JSArguments.getThisObject(arguments));
         Object[] callArguments = this.exportArguments(arguments);
         Object callReturn;
         if (JSGuards.isForeignObject(receiver)) {
            assert JSArguments.getFunctionObject(arguments) == receiver;

            if (this.interop.isNull(receiver)) {
               this.errorBranch.enter();
               throw Errors.createTypeErrorCannotGetProperty(this.getContext(), this.functionName, receiver, false, this);
            }

            if (this.optimistic) {
               try {
                  callReturn = this.interop.invokeMember(receiver, this.functionNameJavaString, callArguments);
               } catch (UnsupportedMessageException | UnknownIdentifierException var9) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  this.optimistic = false;
                  callReturn = this.fallback(receiver, arguments, callArguments, var9);
               } catch (ArityException | UnsupportedTypeException var10) {
                  this.errorBranch.enter();
                  throw Errors.createTypeErrorInteropException(receiver, var10, "invokeMember", this.functionName, this);
               }
            } else if (this.interop.isMemberInvocable(receiver, this.functionNameJavaString)) {
               try {
                  callReturn = this.interop.invokeMember(receiver, this.functionNameJavaString, callArguments);
               } catch (UnsupportedMessageException | UnsupportedTypeException | ArityException | UnknownIdentifierException var8) {
                  this.errorBranch.enter();
                  throw Errors.createTypeErrorInteropException(receiver, var8, "invokeMember", this.functionName, this);
               }
            } else {
               callReturn = this.fallback(receiver, arguments, callArguments, null);
            }
         } else {
            Object function = this.getForeignFunction(arguments);

            try {
               callReturn = this.interop.execute(function, callArguments);
            } catch (ArityException | UnsupportedMessageException | UnsupportedTypeException var7) {
               this.errorBranch.enter();
               throw Errors.createTypeErrorInteropException(function, var7, "execute", this);
            }
         }

         return this.convertForeignReturn(callReturn);
      }

      private Object fallback(Object receiver, Object[] arguments, Object[] callArguments, InteropException caughtException) {
         InteropException ex = caughtException;
         if (this.getContext().getContextOptions().hasForeignObjectPrototype() || JSInteropUtil.isBoxedPrimitive(receiver, this.interop)) {
            Object function = this.maybeGetFromPrototype(receiver);
            if (function != Undefined.instance) {
               return this.callJSFunction(receiver, function, arguments);
            }
         }

         if (this.getContext().getContextOptions().hasForeignHashProperties()
            && this.interop.hasHashEntries(receiver)
            && this.interop.isHashEntryReadable(receiver, this.functionName)) {
            try {
               Object function = this.interop.readHashValue(receiver, this.functionName);
               return InteropLibrary.getUncached().execute(function, callArguments);
            } catch (UnknownKeyException | UnsupportedTypeException | ArityException | UnsupportedMessageException var7) {
               ex = var7;
            }
         }

         this.errorBranch.enter();
         throw Errors.createTypeErrorInteropException(
            receiver,
            (InteropException)(ex != null ? ex : UnknownIdentifierException.create(Strings.toJavaString(this.functionName))),
            "invokeMember",
            this.functionName,
            this
         );
      }

      private Object maybeGetFromPrototype(Object receiver) {
         if (this.foreignObjectPrototypeNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.foreignObjectPrototypeNode = this.insert(ForeignObjectPrototypeNode.create());
         }

         JSDynamicObject prototype = this.foreignObjectPrototypeNode.execute(receiver);
         return this.getFunction(prototype);
      }

      private Object getFunction(Object object) {
         if (this.getFunctionNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getFunctionNode = this.insert(PropertyGetNode.create(this.functionName, this.getContext()));
         }

         return this.getFunctionNode.getValue(object);
      }

      private Object callJSFunction(Object receiver, Object function, Object[] arguments) {
         if (this.callJSFunctionNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.callJSFunctionNode = this.insert(JSFunctionCallNode.createCall());
         }

         return this.callJSFunctionNode.executeCall(JSArguments.create(receiver, function, JSArguments.extractUserArguments(arguments)));
      }

      private JSContext getContext() {
         return this.getLanguage().getJSContext();
      }
   }

   private static final class FunctionInstanceCacheNode extends JSFunctionCallNode.UnboundJSFunctionCacheNode {
      private final JSDynamicObject functionObj;

      FunctionInstanceCacheNode(JSDynamicObject functionObj, CallTarget callTarget) {
         super(callTarget);

         assert JSFunction.isJSFunction(functionObj);

         this.functionObj = functionObj;
      }

      @Override
      protected boolean accept(Object function) {
         return this.functionObj == function;
      }

      @Override
      protected JSFunctionData getFunctionData() {
         return JSFunction.getFunctionData(this.functionObj);
      }

      @Override
      protected boolean isInstanceCache() {
         return true;
      }
   }

   private static class GenericFallbackCacheNode extends JSFunctionCallNode.AbstractCacheNode {
      GenericFallbackCacheNode() {
         JSFunctionCallNode.megamorphicCount.inc();
      }

      @Override
      protected boolean accept(Object function) {
         return !JSFunction.isJSFunction(function)
            && !JSProxy.isJSProxy(function)
            && !JSGuards.isForeignObject(function)
            && !(function instanceof JSNoSuchMethodAdapter);
      }

      @Override
      public Object executeCall(Object[] arguments) {
         Object function = JSArguments.getFunctionObject(arguments);
         throw this.typeError(function);
      }

      @CompilerDirectives.TruffleBoundary
      private JSException typeError(Object function) {
         Object expressionStr = null;
         JSFunctionCallNode callNode = null;

         for (Node current = this; current != null; current = current.getParent()) {
            if (current instanceof JSFunctionCallNode) {
               callNode = (JSFunctionCallNode)current;
               break;
            }
         }

         if (callNode != null) {
            if (callNode instanceof JSFunctionCallNode.InvokeNode) {
               expressionStr = ((JSFunctionCallNode.InvokeNode)callNode).functionTargetNode.expressionToString();
            } else if (callNode instanceof JSFunctionCallNode.CallNode) {
               expressionStr = ((JSFunctionCallNode.CallNode)callNode).functionNode.expressionToString();
            }
         }

         return Errors.createTypeErrorNotAFunction(expressionStr != null ? expressionStr : function, this);
      }
   }

   private static class GenericJSFunctionCacheNode extends JSFunctionCallNode.AbstractCacheNode {
      private final byte flags;
      @Node.Child
      private IndirectCallNode indirectCallNode;
      @Node.Child
      private JSFunctionCallNode.AbstractCacheNode next;
      private final BranchProfile initBranch;

      GenericJSFunctionCacheNode(byte flags, JSFunctionCallNode.AbstractCacheNode next) {
         this.flags = flags;
         this.indirectCallNode = Truffle.getRuntime().createIndirectCallNode();
         this.next = next;
         this.initBranch = BranchProfile.create();
         JSFunctionCallNode.megamorphicCount.inc();
      }

      @Override
      public Object executeCall(Object[] arguments) {
         Object function = JSArguments.getFunctionObject(arguments);
         JSDynamicObject functionObject = (JSDynamicObject)function;
         JSFunctionData functionData = JSFunction.getFunctionData(functionObject);
         if (JSFunctionCallNode.isNewTarget(this.flags)) {
            return this.indirectCallNode.call(functionData.getConstructNewTarget(this.initBranch), arguments);
         } else {
            return JSFunctionCallNode.isNew(this.flags)
               ? this.indirectCallNode.call(functionData.getConstructTarget(this.initBranch), arguments)
               : this.indirectCallNode.call(functionData.getCallTarget(this.initBranch), arguments);
         }
      }

      @Override
      protected boolean accept(Object function) {
         return JSFunction.isJSFunction(function);
      }
   }

   private abstract static class InlinedBuiltinCallNode extends JSFunctionCallNode.JSFunctionCacheNode {
      private final CallTarget callTarget;
      @Node.Child
      private JSBuiltinNode.Inlined builtinNode;
      @Node.Child
      private DirectCallNode callNode;

      InlinedBuiltinCallNode(CallTarget callTarget, JSBuiltinNode.Inlined builtinNode) {
         this.callTarget = callTarget;
         this.builtinNode = builtinNode;
      }

      @Override
      public Object executeCall(Object[] arguments) {
         if (this.callNode != null) {
            return this.callNode.call(arguments);
         } else {
            try {
               return this.builtinNode.callInlined(arguments);
            } catch (JSBuiltinNode.RewriteToCallException var3) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.callNode = this.insert(Truffle.getRuntime().createDirectCallNode(this.callTarget));
               this.callNode.cloneCallTarget();
               this.callNode.forceInlining();
               return this.callNode.call(arguments);
            }
         }
      }
   }

   private static final class InlinedBuiltinFunctionDataCacheNode extends JSFunctionCallNode.InlinedBuiltinCallNode {
      private final JSFunctionData functionData;

      InlinedBuiltinFunctionDataCacheNode(JSFunctionData functionData, CallTarget callTarget, JSBuiltinNode.Inlined builtinNode) {
         super(callTarget, builtinNode);
         this.functionData = functionData;
      }

      @Override
      protected boolean accept(Object function) {
         return JSFunction.isJSFunction(function) && this.functionData == JSFunction.getFunctionData((JSDynamicObject)function);
      }

      @Override
      protected JSFunctionData getFunctionData() {
         return this.functionData;
      }
   }

   private static final class InlinedBuiltinFunctionInstanceCacheNode extends JSFunctionCallNode.InlinedBuiltinCallNode {
      private final JSDynamicObject functionObj;

      InlinedBuiltinFunctionInstanceCacheNode(JSDynamicObject functionObj, CallTarget callTarget, JSBuiltinNode.Inlined builtinNode) {
         super(callTarget, builtinNode);

         assert JSFunction.isJSFunction(functionObj);

         this.functionObj = functionObj;
      }

      @Override
      protected boolean accept(Object function) {
         return this.functionObj == function;
      }

      @Override
      protected JSFunctionData getFunctionData() {
         return JSFunction.getFunctionData(this.functionObj);
      }

      @Override
      protected boolean isInstanceCache() {
         return true;
      }
   }

   static class Invoke0Node extends JSFunctionCallNode.InvokeNode {
      protected Invoke0Node(JSTargetableNode functionNode, byte flags) {
         this(null, functionNode, flags);
      }

      protected Invoke0Node(JavaScriptNode targetNode, JSTargetableNode functionNode, byte flags) {
         super(targetNode, functionNode, flags);
      }

      @Override
      protected final Object[] createArguments(VirtualFrame frame, Object target, Object function) {
         return JSArguments.createZeroArg(target, function);
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new JSFunctionCallNode.Invoke0Node(
            cloneUninitialized(this.targetNode, materializedTags), cloneUninitialized(this.functionTargetNode, materializedTags), this.flags
         );
      }

      @Override
      protected JavaScriptNode[] getArgumentNodes() {
         return new JavaScriptNode[0];
      }

      @Override
      protected void materializeInstrumentableArguments() {
      }
   }

   static class Invoke1Node extends JSFunctionCallNode.InvokeNode {
      @Node.Child
      protected JavaScriptNode argument0;

      protected Invoke1Node(JSTargetableNode functionNode, JavaScriptNode argument0, byte flags) {
         this(null, functionNode, argument0, flags);
      }

      protected Invoke1Node(JavaScriptNode targetNode, JSTargetableNode functionNode, JavaScriptNode argument0, byte flags) {
         super(targetNode, functionNode, flags);
         this.argument0 = argument0;
      }

      @Override
      protected final Object[] createArguments(VirtualFrame frame, Object target, Object function) {
         Object arg0 = this.argument0.execute(frame);
         return JSArguments.createOneArg(target, function, arg0);
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new JSFunctionCallNode.Invoke1Node(
            cloneUninitialized(this.targetNode, materializedTags),
            cloneUninitialized(this.functionTargetNode, materializedTags),
            cloneUninitialized(this.argument0, materializedTags),
            this.flags
         );
      }

      @Override
      protected JavaScriptNode[] getArgumentNodes() {
         return new JavaScriptNode[]{this.argument0};
      }

      @Override
      protected void materializeInstrumentableArguments() {
         if (!this.argument0.isInstrumentable()) {
            this.argument0 = this.insert(JSInputGeneratingNodeWrapper.create(this.argument0));
         }
      }
   }

   static class InvokeNNode extends JSFunctionCallNode.InvokeNode {
      @Node.Children
      protected final JavaScriptNode[] arguments;

      protected InvokeNNode(JSTargetableNode functionNode, JavaScriptNode[] arguments, byte flags) {
         this(null, functionNode, arguments, flags);
      }

      protected InvokeNNode(JavaScriptNode targetNode, JSTargetableNode functionNode, JavaScriptNode[] arguments, byte flags) {
         super(targetNode, functionNode, flags);
         this.arguments = arguments;
      }

      @Override
      protected final Object[] createArguments(VirtualFrame frame, Object target, Object function) {
         Object[] args = JSArguments.createInitial(target, function, this.arguments.length);
         return this.executeFillObjectArray(frame, args, 2);
      }

      @ExplodeLoop
      protected Object[] executeFillObjectArray(VirtualFrame frame, Object[] args, int delta) {
         for (int i = 0; i < this.arguments.length; i++) {
            assert !(this.arguments[i] instanceof SpreadArgumentNode);

            args[i + delta] = this.arguments[i].execute(frame);
         }

         return args;
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new JSFunctionCallNode.InvokeNNode(
            cloneUninitialized(this.targetNode, materializedTags),
            cloneUninitialized(this.functionTargetNode, materializedTags),
            cloneUninitialized(this.arguments, materializedTags),
            this.flags
         );
      }

      @Override
      protected JavaScriptNode[] getArgumentNodes() {
         return this.arguments;
      }

      @Override
      protected void materializeInstrumentableArguments() {
         for (int i = 0; i < this.arguments.length; i++) {
            if (!(this.arguments[i] instanceof SpreadArgumentNode) && !this.arguments[i].isInstrumentable()) {
               this.arguments[i] = this.insert(JSInputGeneratingNodeWrapper.create(this.arguments[i]));
            }
         }
      }
   }

   public abstract static class InvokeNode extends JSFunctionCallNode {
      @Node.Child
      protected JavaScriptNode targetNode;
      @Node.Child
      protected JSTargetableNode functionTargetNode;

      protected InvokeNode(JSTargetableNode functionTargetNode, byte flags) {
         super(flags);
         this.functionTargetNode = functionTargetNode;
      }

      protected InvokeNode(JavaScriptNode targetNode, JSTargetableNode functionTargetNode, byte flags) {
         super(flags);
         this.targetNode = targetNode;
         this.functionTargetNode = functionTargetNode;
      }

      @Override
      public final JavaScriptNode getTarget() {
         return this.targetNode != null ? this.targetNode : this.functionTargetNode.getTarget();
      }

      protected abstract Object[] createArguments(VirtualFrame frame, Object target, Object function);

      protected abstract JavaScriptNode[] getArgumentNodes();

      protected abstract void materializeInstrumentableArguments();

      @Override
      public Object execute(VirtualFrame frame) {
         Object target = this.executeTarget(frame);
         Object receiver = this.evaluateReceiver(frame, target);
         Object function = this.executeFunctionWithTarget(frame, target);
         return this.executeCall(this.createArguments(frame, receiver, function));
      }

      protected final Object executeTarget(VirtualFrame frame) {
         return this.targetNode != null ? this.targetNode.execute(frame) : this.functionTargetNode.evaluateTarget(frame);
      }

      final Object executeFunctionWithTarget(VirtualFrame frame, Object target) {
         return this.functionTargetNode.executeWithTarget(frame, target);
      }

      @Override
      public String expressionToString() {
         return Objects.toString(this.functionTargetNode.expressionToString(), "(intermediate value)") + "(...)";
      }

      @Override
      public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
         if (this.targetNode != null) {
            return this;
         } else if (!materializedTags.contains(JSTags.FunctionCallTag.class)
            && !materializedTags.contains(JSTags.ReadPropertyTag.class)
            && !materializedTags.contains(JSTags.ReadElementTag.class)) {
            return this;
         } else {
            this.materializeInstrumentableArguments();
            JSFunctionCallNode.InvokeNode invoke = (JSFunctionCallNode.InvokeNode)createInvoke(
               null, cloneUninitialized(this.getArgumentNodes(), materializedTags), isNew(this.flags), isNewTarget(this.flags)
            );
            JSTargetableNode functionTargetNodeDelegate = cloneUninitialized(this.getFunctionTargetDelegate(), materializedTags);
            JavaScriptNode target = functionTargetNodeDelegate.getTarget();
            invoke.targetNode = !target.isInstrumentable() ? JSInputGeneratingNodeWrapper.create(target) : target;
            invoke.functionTargetNode = JSMaterializedInvokeTargetableNode.createFor(functionTargetNodeDelegate);
            transferSourceSectionAndTags(functionTargetNodeDelegate, invoke.functionTargetNode);
            transferSourceSectionAndTags(this, invoke);
            return invoke;
         }
      }

      private JSTargetableNode getFunctionTargetDelegate() {
         return this.functionTargetNode instanceof InstrumentableNode.WrapperNode
            ? (JSTargetableNode)((InstrumentableNode.WrapperNode)this.functionTargetNode).getDelegateNode()
            : this.functionTargetNode;
      }

      @Override
      protected Object getPropertyKey() {
         JavaScriptNode propertyNode = this.functionTargetNode;
         if (propertyNode instanceof InstrumentableNode.WrapperNode) {
            propertyNode = (JavaScriptNode)((InstrumentableNode.WrapperNode)propertyNode).getDelegateNode();
         }

         return propertyNode instanceof PropertyNode ? ((PropertyNode)propertyNode).getPropertyKey() : null;
      }

      public JSTargetableNode getFunctionTargetNode() {
         return this.functionTargetNode;
      }
   }

   static class InvokeSpreadNode extends JSFunctionCallNode.InvokeNNode {
      private final BranchProfile growProfile = BranchProfile.create();

      protected InvokeSpreadNode(JSTargetableNode functionNode, JavaScriptNode[] arguments, byte flags) {
         this(null, functionNode, arguments, flags);
      }

      protected InvokeSpreadNode(JavaScriptNode targetNode, JSTargetableNode functionNode, JavaScriptNode[] arguments, byte flags) {
         super(targetNode, functionNode, arguments, flags);
      }

      @Override
      protected Object[] executeFillObjectArray(VirtualFrame frame, Object[] args, int delta) {
         return executeFillObjectArraySpread(this.arguments, frame, args, delta, this.growProfile);
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new JSFunctionCallNode.InvokeSpreadNode(
            cloneUninitialized(this.targetNode, materializedTags),
            cloneUninitialized(this.functionTargetNode, materializedTags),
            cloneUninitialized(this.arguments, materializedTags),
            this.flags
         );
      }
   }

   private abstract static class JSFunctionCacheNode extends JSFunctionCallNode.AbstractCacheNode {
      JSFunctionCacheNode() {
      }

      protected boolean isInstanceCache() {
         return false;
      }

      protected abstract JSFunctionData getFunctionData();
   }

   private static class JSNoSuchMethodAdapterCacheNode extends JSFunctionCallNode.AbstractCacheNode {
      @Node.Child
      private JSFunctionCallNode noSuchMethodCallNode = JSFunctionCallNode.createCall();

      JSNoSuchMethodAdapterCacheNode() {
      }

      @Override
      public Object executeCall(Object[] arguments) {
         Object function = JSArguments.getFunctionObject(arguments);

         assert this.accept(function);

         JSNoSuchMethodAdapter noSuchMethod = (JSNoSuchMethodAdapter)function;
         Object[] handlerArguments = JSArguments.createInitial(
            noSuchMethod.getThisObject(), noSuchMethod.getFunction(), JSArguments.getUserArgumentCount(arguments) + 1
         );
         JSArguments.setUserArgument(handlerArguments, 0, noSuchMethod.getKey());
         JSArguments.setUserArguments(handlerArguments, 1, JSArguments.extractUserArguments(arguments));
         return this.noSuchMethodCallNode.executeCall(handlerArguments);
      }

      @Override
      protected boolean accept(Object function) {
         return function instanceof JSNoSuchMethodAdapter;
      }
   }

   private static class JSProxyCallCacheNode extends JSFunctionCallNode.AbstractCacheNode {
      @Node.Child
      private DirectCallNode proxyCallNode;

      JSProxyCallCacheNode(boolean isNew, boolean isNewTarget, JSContext context) {
         JSFunctionData functionData = JSProxy.createProxyCallFunctionData(context);
         CallTarget target;
         if (isNewTarget) {
            target = functionData.getConstructNewTarget();
         } else if (isNew) {
            target = functionData.getConstructTarget();
         } else {
            assert !isNew && !isNewTarget;

            target = functionData.getCallTarget();
         }

         this.proxyCallNode = DirectCallNode.create(target);
      }

      @Override
      public Object executeCall(Object[] arguments) {
         assert this.accept(JSArguments.getFunctionObject(arguments));

         return this.proxyCallNode.call(arguments);
      }

      @Override
      protected boolean accept(Object function) {
         return JSProxy.isJSProxy(function);
      }
   }

   private static class JSProxyInlineCacheNode extends JSFunctionCallNode.AbstractCacheNode {
      @Node.Child
      private JSProxyCallNode proxyCall;

      JSProxyInlineCacheNode(boolean isNew, boolean isNewTarget, JSContext context) {
         this.proxyCall = JSProxyCallNode.create(context, isNew, isNewTarget);
      }

      @Override
      public Object executeCall(Object[] arguments) {
         assert this.accept(JSArguments.getFunctionObject(arguments));

         return this.proxyCall.execute(arguments);
      }

      @Override
      protected boolean accept(Object function) {
         return JSProxy.isJSProxy(function);
      }
   }

   private static final class UnboundFunctionDataCacheNode extends JSFunctionCallNode.UnboundJSFunctionCacheNode {
      private final JSFunctionData functionData;

      UnboundFunctionDataCacheNode(JSFunctionData functionData, CallTarget callTarget) {
         super(callTarget);
         this.functionData = functionData;

         assert !functionData.isBound();
      }

      UnboundFunctionDataCacheNode(JSFunctionData functionData, DirectCallNode directCallNode) {
         super(directCallNode);
         this.functionData = functionData;
      }

      @Override
      protected boolean accept(Object function) {
         return function instanceof JSFunctionObject.Unbound
            && JSFunction.getFunctionData((JSFunctionObject)((JSFunctionObject.Unbound)function)) == this.functionData;
      }

      @Override
      protected JSFunctionData getFunctionData() {
         return this.functionData;
      }
   }

   private abstract static class UnboundJSFunctionCacheNode extends JSFunctionCallNode.JSFunctionCacheNode {
      @Node.Child
      DirectCallNode callNode;

      UnboundJSFunctionCacheNode(CallTarget callTarget) {
         this.callNode = Truffle.getRuntime().createDirectCallNode(callTarget);
         if (callTarget instanceof RootCallTarget) {
            RootNode root = ((RootCallTarget)callTarget).getRootNode();
            if (root instanceof FunctionRootNode && ((FunctionRootNode)root).isInlineImmediately()) {
               this.insert(this.callNode);
               if (((FunctionRootNode)root).isSplitImmediately()) {
                  this.callNode.cloneCallTarget();
               }

               this.callNode.forceInlining();
            }
         }
      }

      UnboundJSFunctionCacheNode(DirectCallNode callNode) {
         this.callNode = callNode;
      }

      @Override
      public final Object executeCall(Object[] arguments) {
         return this.callNode.call(arguments);
      }
   }

   private static class Uncached extends JSFunctionCallNode {
      static final JSFunctionCallNode.Uncached CALL = new JSFunctionCallNode.Uncached(JSFunctionCallNode.createFlags(false, false));
      static final JSFunctionCallNode.Uncached NEW = new JSFunctionCallNode.Uncached(JSFunctionCallNode.createFlags(true, false));

      protected Uncached(byte flags) {
         super(flags);
      }

      @Override
      public Object execute(VirtualFrame frame) {
         throw Errors.shouldNotReachHere();
      }

      @Override
      public Object executeCall(Object[] arguments) {
         Object functionObject = JSArguments.getFunctionObject(arguments);
         Object[] functionArgs = JSArguments.extractUserArguments(arguments);
         return this.isNew()
            ? JSRuntime.construct(functionObject, functionArgs)
            : JSRuntime.call(functionObject, JSArguments.getThisObject(arguments), functionArgs);
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return this;
      }
   }
}
