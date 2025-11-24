
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
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
import com.oracle.truffle.js.nodes.control.AsyncGeneratorRejectNode;
import com.oracle.truffle.js.nodes.control.AsyncGeneratorResolveNode;
import com.oracle.truffle.js.nodes.control.AsyncGeneratorResumeNextNode;
import com.oracle.truffle.js.nodes.control.TryCatchNode;
import com.oracle.truffle.js.nodes.function.FunctionBodyNode;
import com.oracle.truffle.js.nodes.function.SpecializedNewObjectNode;
import com.oracle.truffle.js.nodes.promise.AsyncRootNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JavaScriptRealmBoundaryRootNode;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.objects.AsyncGeneratorRequest;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class AsyncGeneratorBodyNode
extends JavaScriptNode {
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

    public AsyncGeneratorBodyNode(JSContext context, JavaScriptNode body, JSWriteFrameSlotNode writeYieldValueNode, JSReadFrameSlotNode readYieldResultNode, JSWriteFrameSlotNode writeAsyncContext, JSReadFrameSlotNode readAsyncContext) {
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

    public static JavaScriptNode create(JSContext context, JavaScriptNode body, JSWriteFrameSlotNode writeYieldValueNode, JSReadFrameSlotNode readYieldResultNode, JSWriteFrameSlotNode writeAsyncContext, JSReadFrameSlotNode readAsyncContext) {
        return new AsyncGeneratorBodyNode(context, body, writeYieldValueNode, readYieldResultNode, writeAsyncContext, readAsyncContext);
    }

    private void initializeCallTarget() {
        CompilerAsserts.neverPartOfCompilation();
        this.atomic(() -> {
            if (this.resumeTarget == null) {
                RootNode rootNode = this.getRootNode();
                AsyncGeneratorRootNode asyncGeneratorRootNode = new AsyncGeneratorRootNode(this.context, this.functionBody, this.writeYieldValueNode, this.readYieldResultNode, this.readAsyncContext, rootNode.getSourceSection(), rootNode.getName());
                this.resumeTarget = asyncGeneratorRootNode.getCallTarget();
                this.functionBody = null;
                this.writeYieldValueNode = null;
                this.readYieldResultNode = null;
                this.readAsyncContext = null;
            }
        });
    }

    private void ensureCallTargetInitialized() {
        if (this.resumeTarget == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.initializeCallTarget();
        }
    }

    private void asyncGeneratorStart(VirtualFrame frame, JSDynamicObject generatorObject) {
        MaterializedFrame materializedFrame = frame.materialize();
        this.setGeneratorState.setValue(generatorObject, (Object)JSFunction.AsyncGeneratorState.SuspendedStart);
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
        return this.atomic(() -> {
            if (this.resumeTarget == null) {
                return AsyncGeneratorBodyNode.create(this.context, AsyncGeneratorBodyNode.cloneUninitialized(this.functionBody, materializedTags), AsyncGeneratorBodyNode.cloneUninitialized(this.writeYieldValueNode, materializedTags), AsyncGeneratorBodyNode.cloneUninitialized(this.readYieldResultNode, materializedTags), AsyncGeneratorBodyNode.cloneUninitialized(this.writeAsyncContext, materializedTags), AsyncGeneratorBodyNode.cloneUninitialized(this.readAsyncContext, materializedTags));
            }
            AsyncGeneratorRootNode generatorRoot = (AsyncGeneratorRootNode)this.resumeTarget.getRootNode();
            return AsyncGeneratorBodyNode.create(this.context, AsyncGeneratorBodyNode.cloneUninitialized(generatorRoot.functionBody, materializedTags), AsyncGeneratorBodyNode.cloneUninitialized(generatorRoot.writeYieldValue, materializedTags), AsyncGeneratorBodyNode.cloneUninitialized(generatorRoot.readYieldResult, materializedTags), AsyncGeneratorBodyNode.cloneUninitialized(this.writeAsyncContext, materializedTags), AsyncGeneratorBodyNode.cloneUninitialized(generatorRoot.readAsyncContext, materializedTags));
        });
    }

    @NodeInfo(cost=NodeCost.NONE, language="JavaScript", description="The root node of async generator functions in JavaScript.")
    private static final class AsyncGeneratorRootNode
    extends JavaScriptRealmBoundaryRootNode
    implements AsyncRootNode {
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

        AsyncGeneratorRootNode(JSContext context, JavaScriptNode functionBody, JSWriteFrameSlotNode writeYieldValueNode, JSReadFrameSlotNode readYieldResultNode, JSReadFrameSlotNode readAsyncContext, SourceSection functionSourceSection, String functionName) {
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

        /*
         * Exception decompiling
         */
        @Override
        protected Object executeInRealm(VirtualFrame frame) {
            /*
             * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
             * 
             * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[CATCHBLOCK]], but top level block is 2[TRYBLOCK]
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
             *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
             *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:923)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
             *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
             *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
             *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
             *     at org.benf.cfr.reader.Main.main(Main.java:54)
             */
            throw new IllegalStateException("Decompilation failed");
        }

        private void asyncGeneratorReject(VirtualFrame generatorFrame, JSDynamicObject generatorObject, AbstractTruffleException ex) {
            if (this.getErrorObjectNode == null || this.asyncGeneratorRejectNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getErrorObjectNode = this.insert(TryCatchNode.GetErrorObjectNode.create(this.context));
                this.asyncGeneratorRejectNode = this.insert(AsyncGeneratorRejectNode.create(this.context));
            }
            this.setGeneratorState.setValue(generatorObject, (Object)JSFunction.AsyncGeneratorState.Completed);
            Object reason = this.getErrorObjectNode.execute(ex);
            this.asyncGeneratorRejectNode.performReject(generatorFrame, generatorObject, reason);
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
            return ":asyncgenerator";
        }

        @Override
        public String toString() {
            return this.getName();
        }

        @Override
        public JSDynamicObject getAsyncFunctionPromise(Frame asyncFrame) {
            Object[] initialState = (Object[])this.readAsyncContext.execute((VirtualFrame)asyncFrame);
            RootCallTarget resumeTarget = (RootCallTarget)initialState[0];
            assert (resumeTarget.getRootNode() == this);
            JSDynamicObject generatorObject = (JSDynamicObject)initialState[1];
            Object queue = JSObjectUtil.getHiddenProperty(generatorObject, JSFunction.ASYNC_GENERATOR_QUEUE_ID);
            if (queue instanceof ArrayDeque && ((ArrayDeque)queue).size() == 1) {
                AsyncGeneratorRequest request = (AsyncGeneratorRequest)((ArrayDeque)queue).peekFirst();
                return request.getPromiseCapability().getPromise();
            }
            return null;
        }

        public List<TruffleStackTraceElement> getSavedStackTrace(Frame asyncFrame) {
            Object[] initialState = (Object[])this.readAsyncContext.execute((VirtualFrame)asyncFrame);
            return (List)initialState[3];
        }

        @Override
        protected List<TruffleStackTraceElement> findAsynchronousFrames(Frame frame) {
            if (!this.context.isOptionAsyncStackTraces() || this.context.getLanguage().getAsyncStackDepth() == 0) {
                return null;
            }
            VirtualFrame asyncFrame = frame.getFrameDescriptor() == this.getFrameDescriptor() ? JSArguments.getResumeExecutionContext(frame.getArguments()) : (VirtualFrame)ScopeFrameNode.getNonBlockScopeParentFrame(frame);
            return this.getSavedStackTrace(asyncFrame);
        }
    }
}

