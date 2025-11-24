
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.frame.MaterializedFrame;
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
import com.oracle.truffle.js.nodes.control.YieldException;
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

public final class GeneratorBodyNode
extends JavaScriptNode {
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

    public static GeneratorBodyNode create(JSContext context, JavaScriptNode expression, JSWriteFrameSlotNode writeYieldValue, JSReadFrameSlotNode readYieldResult) {
        return new GeneratorBodyNode(context, expression, writeYieldValue, readYieldResult);
    }

    private void initializeGeneratorCallTarget() {
        CompilerAsserts.neverPartOfCompilation();
        this.atomic(() -> {
            if (this.generatorCallTarget == null) {
                RootNode rootNode = this.getRootNode();
                GeneratorRootNode generatorRootNode = new GeneratorRootNode(this.context, this.functionBody, this.writeYieldValueNode, this.readYieldResultNode, rootNode.getSourceSection(), rootNode.getName());
                this.generatorCallTarget = generatorRootNode.getCallTarget();
                this.functionBody = null;
                this.writeYieldValueNode = null;
                this.readYieldResultNode = null;
            }
        });
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
        this.setGeneratorState.setValue(generatorObject, (Object)JSFunction.GeneratorState.SuspendedStart);
        this.setGeneratorContext.setValue(generatorObject, frame.materialize());
        this.setGeneratorTarget.setValue(generatorObject, this.generatorCallTarget);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return this.atomic(() -> {
            if (this.generatorCallTarget == null) {
                return GeneratorBodyNode.create(this.context, GeneratorBodyNode.cloneUninitialized(this.functionBody, materializedTags), GeneratorBodyNode.cloneUninitialized(this.writeYieldValueNode, materializedTags), GeneratorBodyNode.cloneUninitialized(this.readYieldResultNode, materializedTags));
            }
            GeneratorRootNode generatorRoot = (GeneratorRootNode)this.generatorCallTarget.getRootNode();
            return GeneratorBodyNode.create(this.context, GeneratorBodyNode.cloneUninitialized(generatorRoot.functionBody, materializedTags), GeneratorBodyNode.cloneUninitialized(generatorRoot.writeYieldValue, materializedTags), GeneratorBodyNode.cloneUninitialized(generatorRoot.readYieldResult, materializedTags));
        });
    }

    @NodeInfo(cost=NodeCost.NONE, language="JavaScript", description="The root node of generator functions in JavaScript.")
    private static final class GeneratorRootNode
    extends JavaScriptRealmBoundaryRootNode {
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

        GeneratorRootNode(JSContext context, JavaScriptNode functionBody, JSWriteFrameSlotNode writeYieldValueNode, JSReadFrameSlotNode readYieldResultNode, SourceSection functionSourceSection, String functionName) {
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

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        protected Object executeInRealm(VirtualFrame frame) {
            Object[] arguments = frame.getArguments();
            MaterializedFrame generatorFrame = JSArguments.getResumeExecutionContext(arguments);
            JSDynamicObject generatorObject = (JSDynamicObject)JSArguments.getResumeGeneratorOrPromiseCapability(arguments);
            Completion.Type completionType = JSArguments.getResumeCompletionType(arguments);
            Object value2 = JSArguments.getResumeCompletionValue(arguments);
            JSFunction.GeneratorState generatorState = this.generatorValidate(generatorObject);
            if (completionType == Completion.Type.Normal) {
                if (JSFunction.GeneratorState.Completed.equals((Object)generatorState)) {
                    return this.createIterResultObject.execute(frame, Undefined.instance, true);
                }
                assert (JSFunction.GeneratorState.SuspendedStart.equals((Object)generatorState) || JSFunction.GeneratorState.SuspendedYield.equals((Object)generatorState));
            } else {
                Completion completion = Completion.create(completionType, value2);
                assert (completion.isThrow() || completion.isReturn());
                if (JSFunction.GeneratorState.SuspendedStart.equals((Object)generatorState)) {
                    generatorState = JSFunction.GeneratorState.Completed;
                    this.setGeneratorState.setValue(generatorObject, (Object)generatorState);
                }
                if (JSFunction.GeneratorState.Completed.equals((Object)generatorState)) {
                    if (this.returnOrExceptionProfile.profile(completion.isReturn())) {
                        return this.createIterResultObject.execute(frame, completion.getValue(), true);
                    }
                    assert (completion.isThrow());
                    throw UserScriptException.create(completion.getValue(), this, this.getGeneratorState.getContext().getContextOptions().getStackTraceLimit());
                }
                assert (JSFunction.GeneratorState.SuspendedYield.equals((Object)generatorState));
                value2 = completion;
            }
            generatorState = JSFunction.GeneratorState.Executing;
            this.setGeneratorState.setValue(generatorObject, (Object)generatorState);
            this.writeYieldValue.executeWrite(generatorFrame, value2);
            try {
                Object result = this.functionBody.execute(generatorFrame);
                JSDynamicObject jSDynamicObject = this.createIterResultObject.execute(frame, result, true);
                return jSDynamicObject;
            }
            catch (YieldException e) {
                generatorState = JSFunction.GeneratorState.SuspendedYield;
                Object object = this.readYieldResult == null ? e.getResult() : this.readYieldResult.execute(generatorFrame);
                return object;
            }
            finally {
                if (JSFunction.GeneratorState.Executing.equals((Object)generatorState)) {
                    generatorState = JSFunction.GeneratorState.Completed;
                }
                this.setGeneratorState.setValue(generatorObject, (Object)generatorState);
            }
        }

        private JSFunction.GeneratorState generatorValidate(JSDynamicObject generatorObject) {
            Object generatorState = this.getGeneratorState.getValue(generatorObject);
            if (generatorState == Undefined.instance) {
                this.errorBranch.enter();
                throw Errors.createTypeErrorGeneratorObjectExpected();
            }
            if (JSFunction.GeneratorState.Executing.equals(generatorState)) {
                this.errorBranch.enter();
                throw Errors.createTypeError("generator is already executing");
            }
            return (JSFunction.GeneratorState)((Object)generatorState);
        }

        @Override
        public boolean isResumption() {
            return true;
        }

        @Override
        public String getName() {
            if (this.functionName != null && !this.functionName.isEmpty()) {
                return this.functionName;
            }
            return ":generator";
        }
    }
}

