
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.CallTarget;
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
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode;
import com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;
import com.oracle.truffle.js.nodes.control.TryCatchNode;
import com.oracle.truffle.js.nodes.control.YieldException;
import com.oracle.truffle.js.nodes.function.FunctionBodyNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.promise.AsyncRootNode;
import com.oracle.truffle.js.nodes.promise.NewPromiseCapabilityNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JavaScriptRealmBoundaryRootNode;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.objects.Completion;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.List;
import java.util.Set;

public final class AsyncFunctionBodyNode
extends JavaScriptNode {
    private final JSContext context;
    @Node.Child
    private JavaScriptNode functionBody;
    @Node.Child
    private JSReadFrameSlotNode readAsyncContext;
    @Node.Child
    private JSWriteFrameSlotNode writeAsyncContext;
    @Node.Child
    private JSWriteFrameSlotNode writeAsyncResult;
    @Node.Child
    private NewPromiseCapabilityNode newPromiseCapability;
    @CompilerDirectives.CompilationFinal
    private volatile CallTarget resumptionTarget;
    @Node.Child
    private volatile DirectCallNode asyncCallNode;

    public AsyncFunctionBodyNode(JSContext context, JavaScriptNode body, JSWriteFrameSlotNode writeAsyncContext, JSReadFrameSlotNode readAsyncContext, JSWriteFrameSlotNode writeAsyncResult) {
        this.context = context;
        this.functionBody = body;
        this.readAsyncContext = readAsyncContext;
        this.writeAsyncContext = writeAsyncContext;
        this.writeAsyncResult = writeAsyncResult;
        this.newPromiseCapability = NewPromiseCapabilityNode.create(context);
        AsyncFunctionBodyNode.transferSourceSection(body, this);
    }

    public static JavaScriptNode create(JSContext context, JavaScriptNode body, JSWriteFrameSlotNode writeAsyncContext, JSReadFrameSlotNode readAsyncContext, JSWriteFrameSlotNode writeAsyncResult) {
        return new AsyncFunctionBodyNode(context, body, writeAsyncContext, readAsyncContext, writeAsyncResult);
    }

    private JSContext getContext() {
        return this.context;
    }

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        if (tag == JSTags.ControlFlowRootTag.class) {
            return true;
        }
        return super.hasTag(tag);
    }

    @Override
    public Object getNodeObject() {
        return JSTags.createNodeObjectDescriptor("type", JSTags.ControlFlowRootTag.Type.AsyncFunction.name());
    }

    private void initializeAsyncCallTarget() {
        CompilerAsserts.neverPartOfCompilation();
        this.atomic(() -> {
            if (this.asyncCallTargetInitializationRequired()) {
                RootNode rootNode = this.getRootNode();
                AsyncFunctionRootNode asyncRootNode = new AsyncFunctionRootNode(this.getContext(), this.functionBody, this.writeAsyncResult, this.readAsyncContext, rootNode.getSourceSection(), rootNode.getName());
                this.resumptionTarget = asyncRootNode.getCallTarget();
                DirectCallNode callNode = DirectCallNode.create(this.resumptionTarget);
                this.asyncCallNode = this.insert(callNode);
                this.functionBody = null;
                this.writeAsyncResult = null;
                this.readAsyncContext = null;
            }
        });
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

    private void asyncFunctionStart(VirtualFrame frame, PromiseCapabilityRecord promiseCapability) {
        MaterializedFrame materializedFrame = frame.materialize();
        this.writeAsyncContext.executeWrite(frame, AsyncRootNode.createAsyncContext(this.resumptionTarget, promiseCapability, materializedFrame));
        Object unusedInitialResult = null;
        this.asyncCallNode.call(JSArguments.createResumeArguments(materializedFrame, promiseCapability, Completion.Type.Normal, unusedInitialResult));
    }

    @Override
    public Object execute(VirtualFrame frame) {
        PromiseCapabilityRecord promiseCapability = this.newPromiseCapability.executeDefault();
        this.ensureAsyncCallTargetInitialized();
        this.asyncFunctionStart(frame, promiseCapability);
        return promiseCapability.getPromise();
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return this.atomic(() -> {
            if (this.resumptionTarget == null) {
                return AsyncFunctionBodyNode.create(this.getContext(), AsyncFunctionBodyNode.cloneUninitialized(this.functionBody, materializedTags), AsyncFunctionBodyNode.cloneUninitialized(this.writeAsyncContext, materializedTags), AsyncFunctionBodyNode.cloneUninitialized(this.readAsyncContext, materializedTags), AsyncFunctionBodyNode.cloneUninitialized(this.writeAsyncResult, materializedTags));
            }
            AsyncFunctionRootNode asyncFunctionRoot = (AsyncFunctionRootNode)((RootCallTarget)this.resumptionTarget).getRootNode();
            return AsyncFunctionBodyNode.create(this.getContext(), AsyncFunctionBodyNode.cloneUninitialized(asyncFunctionRoot.functionBody, materializedTags), AsyncFunctionBodyNode.cloneUninitialized(this.writeAsyncContext, materializedTags), AsyncFunctionBodyNode.cloneUninitialized(asyncFunctionRoot.readAsyncContext, materializedTags), AsyncFunctionBodyNode.cloneUninitialized(asyncFunctionRoot.writeAsyncResult, materializedTags));
        });
    }

    @NodeInfo(cost=NodeCost.NONE, language="JavaScript", description="The root node of async functions in JavaScript.")
    public static final class AsyncFunctionRootNode
    extends JavaScriptRealmBoundaryRootNode
    implements AsyncRootNode {
        private final JSContext context;
        private final String functionName;
        @Node.Child
        private JavaScriptNode functionBody;
        @Node.Child
        private JSReadFrameSlotNode readAsyncContext;
        @Node.Child
        private JSWriteFrameSlotNode writeAsyncResult;
        @Node.Child
        private JSFunctionCallNode callResolveNode;
        @Node.Child
        private JSFunctionCallNode callRejectNode;
        @Node.Child
        private TryCatchNode.GetErrorObjectNode getErrorObjectNode;

        AsyncFunctionRootNode(JSContext context, JavaScriptNode body, JSWriteFrameSlotNode asyncResult, JSReadFrameSlotNode readAsyncContext, SourceSection functionSourceSection, String functionName) {
            super(context.getLanguage(), functionSourceSection, null);
            this.context = context;
            this.functionBody = new FunctionBodyNode(body);
            this.readAsyncContext = readAsyncContext;
            this.writeAsyncResult = asyncResult;
            this.callResolveNode = JSFunctionCallNode.createCall();
            this.functionName = functionName;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        protected Object executeInRealm(VirtualFrame frame) {
            boolean enterContext;
            JSRealm realm;
            Object[] arguments = frame.getArguments();
            MaterializedFrame asyncFrame = JSArguments.getResumeExecutionContext(arguments);
            PromiseCapabilityRecord promiseCapability = (PromiseCapabilityRecord)JSArguments.getResumeGeneratorOrPromiseCapability(arguments);
            Completion resumptionValue = JSArguments.getResumeCompletion(arguments);
            this.writeAsyncResult.executeWrite(asyncFrame, resumptionValue);
            JSRealm currentRealm = this.getRealm();
            if (this.context.neverCreatedChildRealms()) {
                assert (currentRealm == JSFunction.getRealm(JSFrameUtil.getFunctionObject(asyncFrame)));
                realm = currentRealm;
                enterContext = false;
            } else {
                realm = JSFunction.getRealm(JSFrameUtil.getFunctionObject(asyncFrame));
                enterContext = realm != currentRealm;
            }
            Object prev = null;
            TruffleContext childContext = null;
            if (enterContext) {
                childContext = realm.getTruffleContext();
                prev = childContext.enter(this);
            }
            try {
                Object result = this.functionBody.execute(asyncFrame);
                this.promiseCapabilityResolve(promiseCapability, result);
            }
            catch (YieldException e) {
                assert (e.isAwait());
            }
            catch (AbstractTruffleException e) {
                this.promiseCapabilityReject(promiseCapability, e);
            }
            finally {
                if (enterContext) {
                    childContext.leave(this, prev);
                }
            }
            return Undefined.instance;
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
            Object error = this.getErrorObjectNode.execute(e);
            this.callRejectNode.executeCall(JSArguments.createOneArg(Undefined.instance, promiseCapability.getReject(), error));
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
            return ":async";
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
            Object promiseCapability = initialState[1];
            return ((PromiseCapabilityRecord)promiseCapability).getPromise();
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

