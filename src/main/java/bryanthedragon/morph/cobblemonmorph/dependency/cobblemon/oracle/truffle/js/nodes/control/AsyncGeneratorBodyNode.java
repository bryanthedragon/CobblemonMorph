package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.TruffleContext;
import com.oracle.truffle.api.TruffleStackTraceElement;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode;
import com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;
import com.oracle.truffle.js.nodes.function.FunctionBodyNode;
import com.oracle.truffle.js.nodes.function.SpecializedNewObjectNode;
import com.oracle.truffle.js.nodes.promise.AsyncRootNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JavaScriptRealmBoundaryRootNode;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.objects.AsyncGeneratorRequest;
import com.oracle.truffle.js.runtime.objects.Completion;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class AsyncGeneratorBodyNode extends JavaScriptNode {
   @Node.Child
   private SpecializedNewObjectNode createAsyncGeneratorObject;
   @Node.Child
   private PropertySetNode setGeneratorState;
   @Node.Child
   private PropertySetNode setGeneratorContext;
   @Node.Child
   private PropertySetNode setGeneratorTarget;
   @Node.Child
   private PropertySetNode setGeneratorQueue;
   @CompilerDirectives.CompilationFinal
   volatile RootCallTarget resumeTarget;
   private final JSContext context;
   @Node.Child
   private JavaScriptNode functionBody;
   @Node.Child
   private JSWriteFrameSlotNode writeYieldValueNode;
   @Node.Child
   private JSReadFrameSlotNode readYieldResultNode;
   @Node.Child
   private JSWriteFrameSlotNode writeAsyncContext;
   @Node.Child
   private JSReadFrameSlotNode readAsyncContext;

   public AsyncGeneratorBodyNode(
      JSContext context,
      JavaScriptNode body,
      JSWriteFrameSlotNode writeYieldValueNode,
      JSReadFrameSlotNode readYieldResultNode,
      JSWriteFrameSlotNode writeAsyncContext,
      JSReadFrameSlotNode readAsyncContext
   ) {
      this.createAsyncGeneratorObject = SpecializedNewObjectNode.create(context, false, true, true, true);
      this.setGeneratorState = PropertySetNode.createSetHidden(JSFunction.ASYNC_GENERATOR_STATE_ID, context);
      this.setGeneratorContext = PropertySetNode.createSetHidden(JSFunction.ASYNC_GENERATOR_CONTEXT_ID, context);
      this.setGeneratorTarget = PropertySetNode.createSetHidden(JSFunction.ASYNC_GENERATOR_TARGET_ID, context);
      this.setGeneratorQueue = PropertySetNode.createSetHidden(JSFunction.ASYNC_GENERATOR_QUEUE_ID, context);
      this.context = context;
      this.writeAsyncContext = writeAsyncContext;
      this.functionBody = Objects.requireNonNull(body);
      this.writeYieldValueNode = Objects.requireNonNull(writeYieldValueNode);
      this.readYieldResultNode = Objects.requireNonNull(readYieldResultNode);
      this.readAsyncContext = Objects.requireNonNull(readAsyncContext);
   }

   public static JavaScriptNode create(
      JSContext context,
      JavaScriptNode body,
      JSWriteFrameSlotNode writeYieldValueNode,
      JSReadFrameSlotNode readYieldResultNode,
      JSWriteFrameSlotNode writeAsyncContext,
      JSReadFrameSlotNode readAsyncContext
   ) {
      return new AsyncGeneratorBodyNode(context, body, writeYieldValueNode, readYieldResultNode, writeAsyncContext, readAsyncContext);
   }

   private void initializeCallTarget() {
      CompilerAsserts.neverPartOfCompilation();
      this.atomic(
         () -> {
            if (this.resumeTarget == null) {
               RootNode rootNode = this.getRootNode();
               AsyncGeneratorBodyNode.AsyncGeneratorRootNode asyncGeneratorRootNode = new AsyncGeneratorBodyNode.AsyncGeneratorRootNode(
                  this.context,
                  this.functionBody,
                  this.writeYieldValueNode,
                  this.readYieldResultNode,
                  this.readAsyncContext,
                  rootNode.getSourceSection(),
                  rootNode.getName()
               );
               this.resumeTarget = asyncGeneratorRootNode.getCallTarget();
               this.functionBody = null;
               this.writeYieldValueNode = null;
               this.readYieldResultNode = null;
               this.readAsyncContext = null;
            }
         }
      );
   }

   private void ensureCallTargetInitialized() {
      if (this.resumeTarget == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.initializeCallTarget();
      }
   }

   private void asyncGeneratorStart(VirtualFrame frame, JSDynamicObject generatorObject) {
      MaterializedFrame materializedFrame = frame.materialize();
      this.setGeneratorState.setValue(generatorObject, JSFunction.AsyncGeneratorState.SuspendedStart);
      this.setGeneratorContext.setValue(generatorObject, materializedFrame);
      this.setGeneratorTarget.setValue(generatorObject, this.resumeTarget);
      this.setGeneratorQueue.setValue(generatorObject, new ArrayDeque(4));
      this.writeAsyncContext.executeWrite(frame, AsyncRootNode.createAsyncContext(this.resumeTarget, generatorObject, materializedFrame));
   }

   @Override
   public Object execute(VirtualFrame frame) {
      this.ensureCallTargetInitialized();
      JSDynamicObject generatorObject = this.createAsyncGeneratorObject.execute(frame, JSFrameUtil.getFunctionObject(frame));
      this.asyncGeneratorStart(frame, generatorObject);
      return generatorObject;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return this.atomic(
         () -> {
            if (this.resumeTarget == null) {
               return create(
                  this.context,
                  cloneUninitialized(this.functionBody, materializedTags),
                  cloneUninitialized(this.writeYieldValueNode, materializedTags),
                  cloneUninitialized(this.readYieldResultNode, materializedTags),
                  cloneUninitialized(this.writeAsyncContext, materializedTags),
                  cloneUninitialized(this.readAsyncContext, materializedTags)
               );
            } else {
               AsyncGeneratorBodyNode.AsyncGeneratorRootNode generatorRoot = (AsyncGeneratorBodyNode.AsyncGeneratorRootNode)this.resumeTarget.getRootNode();
               return create(
                  this.context,
                  cloneUninitialized(generatorRoot.functionBody, materializedTags),
                  cloneUninitialized(generatorRoot.writeYieldValue, materializedTags),
                  cloneUninitialized(generatorRoot.readYieldResult, materializedTags),
                  cloneUninitialized(this.writeAsyncContext, materializedTags),
                  cloneUninitialized(generatorRoot.readAsyncContext, materializedTags)
               );
            }
         }
      );
   }

   @NodeInfo(cost = NodeCost.NONE, language = "JavaScript", description = "The root node of async generator functions in JavaScript.")
   private static final class AsyncGeneratorRootNode extends JavaScriptRealmBoundaryRootNode implements AsyncRootNode {
      @Node.Child
      private PropertySetNode setGeneratorState;
      @Node.Child
      private JavaScriptNode functionBody;
      @Node.Child
      private JSWriteFrameSlotNode writeYieldValue;
      @Node.Child
      private JSReadFrameSlotNode readYieldResult;
      @Node.Child
      private JSReadFrameSlotNode readAsyncContext;
      @Node.Child
      private AsyncGeneratorResolveNode asyncGeneratorResolveNode;
      @Node.Child
      private AsyncGeneratorRejectNode asyncGeneratorRejectNode;
      @Node.Child
      private AsyncGeneratorResumeNextNode asyncGeneratorResumeNextNode;
      @Node.Child
      private TryCatchNode.GetErrorObjectNode getErrorObjectNode;
      private final JSContext context;
      private final String functionName;

      AsyncGeneratorRootNode(
         JSContext context,
         JavaScriptNode functionBody,
         JSWriteFrameSlotNode writeYieldValueNode,
         JSReadFrameSlotNode readYieldResultNode,
         JSReadFrameSlotNode readAsyncContext,
         SourceSection functionSourceSection,
         String functionName
      ) {
         super(context.getLanguage(), functionSourceSection, null);
         this.readAsyncContext = readAsyncContext;
         this.functionName = functionName;
         this.setGeneratorState = PropertySetNode.createSetHidden(JSFunction.ASYNC_GENERATOR_STATE_ID, context);
         this.functionBody = new FunctionBodyNode(functionBody);
         this.writeYieldValue = writeYieldValueNode;
         this.readYieldResult = readYieldResultNode;
         this.context = context;
         this.asyncGeneratorResolveNode = AsyncGeneratorResolveNode.create(context);
         this.asyncGeneratorResumeNextNode = AsyncGeneratorResumeNextNode.createTailCall(context);
      }

      @Override
      protected Object executeInRealm(VirtualFrame frame) {
         Object[] arguments = frame.getArguments();
         VirtualFrame generatorFrame = JSArguments.getResumeExecutionContext(arguments);
         JSDynamicObject generatorObject = (JSDynamicObject)JSArguments.getResumeGeneratorOrPromiseCapability(arguments);
         Completion completion = JSArguments.getResumeCompletion(arguments);
         JSRealm currentRealm = this.getRealm();
         JSRealm realm;
         boolean enterContext;
         if (this.context.neverCreatedChildRealms()) {
            assert currentRealm == JSFunction.getRealm(JSFrameUtil.getFunctionObject(generatorFrame));

            realm = currentRealm;
            enterContext = false;
         } else {
            realm = JSFunction.getRealm(JSFrameUtil.getFunctionObject(generatorFrame));
            enterContext = realm != currentRealm;
         }

         Object prev = null;
         TruffleContext childContext = null;
         if (enterContext) {
            childContext = realm.getTruffleContext();
            prev = childContext.enter(this);
         }

         try {
            while (
               $assertionsDisabled
                  || JSObjectUtil.getHiddenProperty(generatorObject, JSFunction.ASYNC_GENERATOR_STATE_ID) == JSFunction.AsyncGeneratorState.Executing
                  || JSObjectUtil.getHiddenProperty(generatorObject, JSFunction.ASYNC_GENERATOR_STATE_ID) == JSFunction.AsyncGeneratorState.SuspendedYield
            ) {
               this.writeYieldValue.executeWrite(generatorFrame, completion);

               try {
                  Object result = this.functionBody.execute(generatorFrame);
                  this.setGeneratorState.setValue(generatorObject, JSFunction.AsyncGeneratorState.Completed);
                  this.asyncGeneratorResolveNode.performResolve(frame, generatorObject, result, true);
               } catch (YieldException var17) {
                  if (!var17.isYield()) {
                     assert var17.isAwait();

                     return Undefined.instance;
                  }

                  this.setGeneratorState.setValue(generatorObject, JSFunction.AsyncGeneratorState.SuspendedYield);
                  this.asyncGeneratorResolveNode.performResolve(frame, generatorObject, var17.getResult(), false);
               } catch (AbstractTruffleException var18) {
                  this.asyncGeneratorReject(generatorFrame, generatorObject, var18);
               }

               Object nextCompletion = this.asyncGeneratorResumeNextNode.execute(generatorFrame, generatorObject);
               if (!(nextCompletion instanceof Completion)) {
                  return Undefined.instance;
               }

               completion = (Completion)nextCompletion;
            }

            throw new AssertionError();
         } finally {
            if (enterContext) {
               childContext.leave(this, prev);
            }
         }
      }

      private void asyncGeneratorReject(VirtualFrame generatorFrame, JSDynamicObject generatorObject, AbstractTruffleException ex) {
         if (this.getErrorObjectNode == null || this.asyncGeneratorRejectNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getErrorObjectNode = this.insert(TryCatchNode.GetErrorObjectNode.create(this.context));
            this.asyncGeneratorRejectNode = this.insert(AsyncGeneratorRejectNode.create(this.context));
         }

         this.setGeneratorState.setValue(generatorObject, JSFunction.AsyncGeneratorState.Completed);
         Object reason = this.getErrorObjectNode.execute(ex);
         this.asyncGeneratorRejectNode.performReject(generatorFrame, generatorObject, reason);
      }

      @Override
      public boolean isResumption() {
         return true;
      }

      @Override
      public String getName() {
         return this.functionName != null && !this.functionName.isEmpty() ? this.functionName : ":asyncgenerator";
      }

      @Override
      public String toString() {
         return this.getName();
      }

      @Override
      public JSDynamicObject getAsyncFunctionPromise(Frame asyncFrame) {
         Object[] initialState = (Object[])this.readAsyncContext.execute((VirtualFrame)asyncFrame);
         RootCallTarget resumeTarget = (RootCallTarget)initialState[0];

         assert resumeTarget.getRootNode() == this;

         JSDynamicObject generatorObject = (JSDynamicObject)initialState[1];
         Object queue = JSObjectUtil.getHiddenProperty(generatorObject, JSFunction.ASYNC_GENERATOR_QUEUE_ID);
         if (queue instanceof ArrayDeque && ((ArrayDeque)queue).size() == 1) {
            AsyncGeneratorRequest request = (AsyncGeneratorRequest)((ArrayDeque)queue).peekFirst();
            return request.getPromiseCapability().getPromise();
         } else {
            return null;
         }
      }

      public List<TruffleStackTraceElement> getSavedStackTrace(Frame asyncFrame) {
         Object[] initialState = (Object[])this.readAsyncContext.execute((VirtualFrame)asyncFrame);
         return (List<TruffleStackTraceElement>)initialState[3];
      }

      @Override
      protected List<TruffleStackTraceElement> findAsynchronousFrames(Frame frame) {
         if (this.context.isOptionAsyncStackTraces() && this.context.getLanguage().getAsyncStackDepth() != 0) {
            VirtualFrame asyncFrame;
            if (frame.getFrameDescriptor() == this.getFrameDescriptor()) {
               asyncFrame = JSArguments.getResumeExecutionContext(frame.getArguments());
            } else {
               asyncFrame = (VirtualFrame)ScopeFrameNode.getNonBlockScopeParentFrame(frame);
            }

            return this.getSavedStackTrace(asyncFrame);
         } else {
            return null;
         }
      }
   }
}
