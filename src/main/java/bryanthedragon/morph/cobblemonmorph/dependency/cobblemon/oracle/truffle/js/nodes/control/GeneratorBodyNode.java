package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.CreateIterResultObjectNode;
import com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode;
import com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.function.FunctionBodyNode;
import com.oracle.truffle.js.nodes.function.SpecializedNewObjectNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JavaScriptRealmBoundaryRootNode;
import com.oracle.truffle.js.runtime.UserScriptException;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.objects.Completion;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Objects;
import java.util.Set;

public final class GeneratorBodyNode extends JavaScriptNode {
   @Node.Child
   private SpecializedNewObjectNode createGeneratorObject;
   @Node.Child
   private PropertySetNode setGeneratorState;
   @Node.Child
   private PropertySetNode setGeneratorContext;
   @Node.Child
   private PropertySetNode setGeneratorTarget;
   @CompilerDirectives.CompilationFinal
   private volatile RootCallTarget generatorCallTarget;
   private final JSContext context;
   @Node.Child
   private JavaScriptNode functionBody;
   @Node.Child
   private JSWriteFrameSlotNode writeYieldValueNode;
   @Node.Child
   private JSReadFrameSlotNode readYieldResultNode;

   private GeneratorBodyNode(JSContext context, JavaScriptNode functionBody, JSWriteFrameSlotNode writeYieldValueNode, JSReadFrameSlotNode readYieldResultNode) {
      this.context = context;
      this.createGeneratorObject = SpecializedNewObjectNode.create(context, false, true, true, false);
      this.setGeneratorState = PropertySetNode.createSetHidden(JSFunction.GENERATOR_STATE_ID, context);
      this.setGeneratorContext = PropertySetNode.createSetHidden(JSFunction.GENERATOR_CONTEXT_ID, context);
      this.setGeneratorTarget = PropertySetNode.createSetHidden(JSFunction.GENERATOR_TARGET_ID, context);
      this.functionBody = functionBody;
      this.writeYieldValueNode = writeYieldValueNode;
      this.readYieldResultNode = readYieldResultNode;
   }

   public static GeneratorBodyNode create(
      JSContext context, JavaScriptNode expression, JSWriteFrameSlotNode writeYieldValue, JSReadFrameSlotNode readYieldResult
   ) {
      return new GeneratorBodyNode(context, expression, writeYieldValue, readYieldResult);
   }

   private void initializeGeneratorCallTarget() {
      CompilerAsserts.neverPartOfCompilation();
      this.atomic(
         () -> {
            if (this.generatorCallTarget == null) {
               RootNode rootNode = this.getRootNode();
               GeneratorBodyNode.GeneratorRootNode generatorRootNode = new GeneratorBodyNode.GeneratorRootNode(
                  this.context, this.functionBody, this.writeYieldValueNode, this.readYieldResultNode, rootNode.getSourceSection(), rootNode.getName()
               );
               this.generatorCallTarget = generatorRootNode.getCallTarget();
               this.functionBody = null;
               this.writeYieldValueNode = null;
               this.readYieldResultNode = null;
            }
         }
      );
   }

   private void ensureGeneratorCallTargetInitialized() {
      if (this.generatorCallTarget == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.initializeGeneratorCallTarget();
      }
   }

   @Override
   public Object execute(VirtualFrame frame) {
      this.ensureGeneratorCallTargetInitialized();
      JSDynamicObject generatorObject = this.createGeneratorObject.execute(frame, JSFrameUtil.getFunctionObject(frame));
      this.generatorStart(frame, generatorObject);
      return generatorObject;
   }

   private void generatorStart(VirtualFrame frame, JSDynamicObject generatorObject) {
      this.setGeneratorState.setValue(generatorObject, JSFunction.GeneratorState.SuspendedStart);
      this.setGeneratorContext.setValue(generatorObject, frame.materialize());
      this.setGeneratorTarget.setValue(generatorObject, this.generatorCallTarget);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return this.atomic(
         () -> {
            if (this.generatorCallTarget == null) {
               return create(
                  this.context,
                  cloneUninitialized(this.functionBody, materializedTags),
                  cloneUninitialized(this.writeYieldValueNode, materializedTags),
                  cloneUninitialized(this.readYieldResultNode, materializedTags)
               );
            } else {
               GeneratorBodyNode.GeneratorRootNode generatorRoot = (GeneratorBodyNode.GeneratorRootNode)this.generatorCallTarget.getRootNode();
               return create(
                  this.context,
                  cloneUninitialized(generatorRoot.functionBody, materializedTags),
                  cloneUninitialized(generatorRoot.writeYieldValue, materializedTags),
                  cloneUninitialized(generatorRoot.readYieldResult, materializedTags)
               );
            }
         }
      );
   }

   @NodeInfo(cost = NodeCost.NONE, language = "JavaScript", description = "The root node of generator functions in JavaScript.")
   private static final class GeneratorRootNode extends JavaScriptRealmBoundaryRootNode {
      @Node.Child
      private CreateIterResultObjectNode createIterResultObject;
      @Node.Child
      private PropertyGetNode getGeneratorState;
      @Node.Child
      private PropertySetNode setGeneratorState;
      @Node.Child
      private JavaScriptNode functionBody;
      @Node.Child
      private JSWriteFrameSlotNode writeYieldValue;
      @Node.Child
      private JSReadFrameSlotNode readYieldResult;
      private final BranchProfile errorBranch = BranchProfile.create();
      private final ConditionProfile returnOrExceptionProfile = ConditionProfile.createBinaryProfile();
      private final String functionName;

      GeneratorRootNode(
         JSContext context,
         JavaScriptNode functionBody,
         JSWriteFrameSlotNode writeYieldValueNode,
         JSReadFrameSlotNode readYieldResultNode,
         SourceSection functionSourceSection,
         String functionName
      ) {
         super(context.getLanguage(), functionSourceSection, null);
         this.createIterResultObject = CreateIterResultObjectNode.create(context);
         this.getGeneratorState = PropertyGetNode.createGetHidden(JSFunction.GENERATOR_STATE_ID, context);
         this.setGeneratorState = PropertySetNode.createSetHidden(JSFunction.GENERATOR_STATE_ID, context);
         this.functionBody = new FunctionBodyNode(functionBody);
         Objects.requireNonNull(writeYieldValueNode);
         Objects.requireNonNull(readYieldResultNode);
         this.writeYieldValue = writeYieldValueNode;
         this.readYieldResult = readYieldResultNode;
         this.functionName = functionName;
      }

      @Override
      protected Object executeInRealm(VirtualFrame frame) {
         Object[] arguments = frame.getArguments();
         VirtualFrame generatorFrame = JSArguments.getResumeExecutionContext(arguments);
         JSDynamicObject generatorObject = (JSDynamicObject)JSArguments.getResumeGeneratorOrPromiseCapability(arguments);
         Completion.Type completionType = JSArguments.getResumeCompletionType(arguments);
         Object value = JSArguments.getResumeCompletionValue(arguments);
         JSFunction.GeneratorState generatorState = this.generatorValidate(generatorObject);
         if (completionType == Completion.Type.Normal) {
            if (JSFunction.GeneratorState.Completed.equals(generatorState)) {
               return this.createIterResultObject.execute(frame, Undefined.instance, true);
            }

            assert JSFunction.GeneratorState.SuspendedStart.equals(generatorState) || JSFunction.GeneratorState.SuspendedYield.equals(generatorState);
         } else {
            Completion completion = Completion.create(completionType, value);

            assert completion.isThrow() || completion.isReturn();

            if (JSFunction.GeneratorState.SuspendedStart.equals(generatorState)) {
               generatorState = JSFunction.GeneratorState.Completed;
               this.setGeneratorState.setValue(generatorObject, generatorState);
            }

            if (JSFunction.GeneratorState.Completed.equals(generatorState)) {
               if (this.returnOrExceptionProfile.profile(completion.isReturn())) {
                  return this.createIterResultObject.execute(frame, completion.getValue(), true);
               }

               assert completion.isThrow();

               throw UserScriptException.create(completion.getValue(), this, this.getGeneratorState.getContext().getContextOptions().getStackTraceLimit());
            }

            assert JSFunction.GeneratorState.SuspendedYield.equals(generatorState);

            value = completion;
         }

         generatorState = JSFunction.GeneratorState.Executing;
         this.setGeneratorState.setValue(generatorObject, generatorState);
         this.writeYieldValue.executeWrite(generatorFrame, value);

         Object var9;
         try {
            Object result = this.functionBody.execute(generatorFrame);
            return this.createIterResultObject.execute(frame, result, true);
         } catch (YieldException var13) {
            generatorState = JSFunction.GeneratorState.SuspendedYield;
            var9 = this.readYieldResult == null ? var13.getResult() : this.readYieldResult.execute(generatorFrame);
         } finally {
            if (JSFunction.GeneratorState.Executing.equals(generatorState)) {
               generatorState = JSFunction.GeneratorState.Completed;
            }

            this.setGeneratorState.setValue(generatorObject, generatorState);
         }

         return var9;
      }

      private JSFunction.GeneratorState generatorValidate(JSDynamicObject generatorObject) {
         Object generatorState = this.getGeneratorState.getValue(generatorObject);
         if (generatorState == Undefined.instance) {
            this.errorBranch.enter();
            throw Errors.createTypeErrorGeneratorObjectExpected();
         } else if (JSFunction.GeneratorState.Executing.equals(generatorState)) {
            this.errorBranch.enter();
            throw Errors.createTypeError("generator is already executing");
         } else {
            return (JSFunction.GeneratorState)generatorState;
         }
      }

      @Override
      public boolean isResumption() {
         return true;
      }

      @Override
      public String getName() {
         return this.functionName != null && !this.functionName.isEmpty() ? this.functionName : ":generator";
      }
   }
}
