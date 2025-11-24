
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode;
import com.oracle.truffle.js.nodes.control.TryCatchNode;
import com.oracle.truffle.js.nodes.control.YieldException;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.promise.AsyncRootNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.objects.Completion;
import com.oracle.truffle.js.runtime.objects.JSModuleRecord;
import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

public final class TopLevelAwaitModuleBodyNode
extends JavaScriptNode {
    private final JSContext context;
    @CompilerDirectives.CompilationFinal
    private volatile CallTarget resumptionTarget;
    @Node.Child
    private JavaScriptNode moduleBodyNode;
    @Node.Child
    private JSWriteFrameSlotNode writeAsyncResult;
    @Node.Child
    private volatile DirectCallNode asyncCallNode;
    @Node.Child
    private JSWriteFrameSlotNode writeAsyncContextNode;

    private TopLevelAwaitModuleBodyNode(JSContext context, JavaScriptNode body, JSWriteFrameSlotNode asyncResult, JSWriteFrameSlotNode writeAsyncContextNode) {
        this.context = context;
        this.moduleBodyNode = body;
        this.writeAsyncContextNode = writeAsyncContextNode;
        this.writeAsyncResult = asyncResult;
    }

    public static JavaScriptNode create(JSContext context, JavaScriptNode body, JSWriteFrameSlotNode asyncContext, JSWriteFrameSlotNode writeAsyncContextNode) {
        return new TopLevelAwaitModuleBodyNode(context, body, asyncContext, writeAsyncContextNode);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object[] arguments = frame.getArguments();
        JSModuleRecord moduleRecord = (JSModuleRecord)JSArguments.getUserArgument(arguments, 0);
        MaterializedFrame moduleFrame = moduleRecord.getEnvironment() != null ? JSFrameUtil.castMaterializedFrame(moduleRecord.getEnvironment()) : frame.materialize();
        PromiseCapabilityRecord promiseCapability = JSArguments.getUserArgumentCount(arguments) >= 2 ? (PromiseCapabilityRecord)JSArguments.getUserArgument(arguments, 1) : null;
        this.ensureAsyncCallTargetInitialized();
        if (promiseCapability != null) {
            this.writeAsyncContextNode.executeWrite(moduleFrame, AsyncRootNode.createAsyncContext(this.resumptionTarget, promiseCapability, moduleFrame));
        }
        Object unusedInitialResult = null;
        this.asyncCallNode.call(JSArguments.createResumeArguments(moduleFrame, promiseCapability, Completion.Type.Normal, unusedInitialResult));
        if (promiseCapability == null) {
            return Undefined.instance;
        }
        return promiseCapability.getPromise();
    }

    private boolean asyncCallTargetInitializationRequired() {
        return this.resumptionTarget == null || this.asyncCallNode == null;
    }

    private void ensureAsyncCallTargetInitialized() {
        if (this.asyncCallTargetInitializationRequired()) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.initializeAsyncCallTarget();
        }
    }

    private void initializeAsyncCallTarget() {
        CompilerAsserts.neverPartOfCompilation();
        this.atomic(() -> {
            if (this.asyncCallTargetInitializationRequired()) {
                TopLevelAwaitModuleRootNode asyncRootNode = new TopLevelAwaitModuleRootNode(this.context, this.moduleBodyNode, this.writeAsyncResult, this.getRootNode().getSourceSection(), "");
                this.resumptionTarget = asyncRootNode.getCallTarget();
                this.asyncCallNode = this.insert(DirectCallNode.create(this.resumptionTarget));
                this.moduleBodyNode = null;
                this.writeAsyncResult = null;
            }
        });
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return TopLevelAwaitModuleBodyNode.create(this.context, TopLevelAwaitModuleBodyNode.cloneUninitialized(this.moduleBodyNode, materializedTags), TopLevelAwaitModuleBodyNode.cloneUninitialized(this.writeAsyncResult, materializedTags), TopLevelAwaitModuleBodyNode.cloneUninitialized(this.writeAsyncContextNode, materializedTags));
    }

    @NodeInfo(cost=NodeCost.NONE, language="JavaScript")
    public static final class TopLevelAwaitModuleRootNode
    extends JavaScriptRootNode {
        private final JSContext context;
        private final String functionName;
        @Node.Child
        private JavaScriptNode functionBody;
        @Node.Child
        private JSFunctionCallNode callResolveNode;
        @Node.Child
        private JSFunctionCallNode callRejectNode;
        @Node.Child
        private JSWriteFrameSlotNode writeAsyncResult;
        @Node.Child
        private TryCatchNode.GetErrorObjectNode getErrorObjectNode;

        TopLevelAwaitModuleRootNode(JSContext context, JavaScriptNode body, JSWriteFrameSlotNode asyncResult, SourceSection functionSourceSection, String functionName) {
            super(context.getLanguage(), functionSourceSection, null);
            this.context = context;
            this.functionBody = body;
            this.callResolveNode = JSFunctionCallNode.createCall();
            this.functionName = functionName;
            this.writeAsyncResult = asyncResult;
        }

        @Override
        public Object execute(VirtualFrame frame) {
            Object[] arguments = frame.getArguments();
            MaterializedFrame asyncFrame = JSArguments.getResumeExecutionContext(arguments);
            PromiseCapabilityRecord promiseCapability = (PromiseCapabilityRecord)JSArguments.getResumeGeneratorOrPromiseCapability(arguments);
            Completion resumptionValue = JSArguments.getResumeCompletion(arguments);
            this.writeAsyncResult.executeWrite(asyncFrame, resumptionValue);
            JSModuleRecord moduleRecord = (JSModuleRecord)JSArguments.getUserArgument(asyncFrame.getArguments(), 0);
            try {
                Object returnValue = this.functionBody.execute(asyncFrame);
                assert (promiseCapability != null);
                this.promiseCapabilityResolve(promiseCapability, returnValue);
            }
            catch (YieldException e) {
                assert (promiseCapability != null ? e.isAwait() : e.isYield());
                if (e.isYield()) {
                    moduleRecord.setEnvironment(JSFrameUtil.castMaterializedFrame(asyncFrame));
                } else assert (e.isAwait());
            }
            catch (AbstractTruffleException e) {
                if (promiseCapability != null) {
                    this.promiseCapabilityReject(promiseCapability, e);
                }
                throw e;
            }
            return Undefined.instance;
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
            return ":top-level-await-module";
        }

        private void promiseCapabilityResolve(PromiseCapabilityRecord promiseCapability, Object result) {
            this.callResolveNode.executeCall(JSArguments.createOneArg(Undefined.instance, promiseCapability.getResolve(), result));
        }

        private void promiseCapabilityReject(PromiseCapabilityRecord promiseCapability, AbstractTruffleException e) {
            if (this.getErrorObjectNode == null || this.callRejectNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getErrorObjectNode = this.insert(TryCatchNode.GetErrorObjectNode.create(this.context));
                this.callRejectNode = this.insert(JSFunctionCallNode.createCall());
            }
            Object result = this.getErrorObjectNode.execute(e);
            this.callRejectNode.executeCall(JSArguments.createOneArg(Undefined.instance, promiseCapability.getReject(), result));
        }
    }
}

