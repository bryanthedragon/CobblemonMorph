
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.TruffleStackTrace;
import com.oracle.truffle.api.TruffleStackTraceElement;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.arguments.AccessIndexedArgumentNode;
import com.oracle.truffle.js.nodes.control.AwaitResumeNode;
import com.oracle.truffle.js.nodes.control.ResumableNode;
import com.oracle.truffle.js.nodes.control.SuspendNode;
import com.oracle.truffle.js.nodes.control.YieldException;
import com.oracle.truffle.js.nodes.function.FunctionRootNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.promise.AsyncHandlerRootNode;
import com.oracle.truffle.js.nodes.promise.AsyncRootNode;
import com.oracle.truffle.js.nodes.promise.NewPromiseCapabilityNode;
import com.oracle.truffle.js.nodes.promise.PerformPromiseThenNode;
import com.oracle.truffle.js.nodes.promise.PromiseResolveNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.UserScriptException;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSPromise;
import com.oracle.truffle.js.runtime.objects.Completion;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;
import com.oracle.truffle.js.runtime.objects.PromiseReactionRecord;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAwaitNode
extends JavaScriptNode
implements ResumableNode,
SuspendNode {
    protected final int stateSlot;
    @Node.Child
    protected JavaScriptNode expression;
    @Node.Child
    protected JSReadFrameSlotNode readAsyncResultNode;
    @Node.Child
    protected JSReadFrameSlotNode readAsyncContextNode;
    @Node.Child
    private NewPromiseCapabilityNode newPromiseCapabilityNode;
    @Node.Child
    private PerformPromiseThenNode performPromiseThenNode;
    @Node.Child
    private PromiseResolveNode promiseResolveNode;
    @Node.Child
    private JSFunctionCallNode callPromiseResolveNode;
    @Node.Child
    private PropertySetNode setPromiseIsHandledNode;
    @Node.Child
    private PropertySetNode setAsyncContextNode;
    @Node.Child
    private PropertySetNode setAsyncTargetNode;
    @Node.Child
    private PropertySetNode setAsyncCallNode;
    @Node.Child
    private PropertySetNode setAsyncGeneratorNode;
    protected final JSContext context;
    private final ConditionProfile asyncTypeProf = ConditionProfile.createBinaryProfile();
    private final ConditionProfile resumptionTypeProf = ConditionProfile.createBinaryProfile();
    private final BranchProfile saveStackBranch = BranchProfile.create();
    static final HiddenKey ASYNC_CONTEXT = new HiddenKey("AsyncContext");
    static final HiddenKey ASYNC_TARGET = new HiddenKey("AsyncTarget");
    static final HiddenKey ASYNC_GENERATOR = new HiddenKey("AsyncGenerator");
    static final HiddenKey ASYNC_CALL_NODE = new HiddenKey("AsyncCallNode");

    protected AbstractAwaitNode(JSContext context, int stateSlot, JavaScriptNode expression, JSReadFrameSlotNode readAsyncContextNode, JSReadFrameSlotNode readAsyncResultNode) {
        this.stateSlot = stateSlot;
        this.context = context;
        this.expression = expression;
        this.readAsyncResultNode = readAsyncResultNode;
        this.readAsyncContextNode = readAsyncContextNode;
        this.setAsyncContextNode = PropertySetNode.createSetHidden(ASYNC_CONTEXT, context);
        this.setAsyncTargetNode = PropertySetNode.createSetHidden(ASYNC_TARGET, context);
        this.setAsyncGeneratorNode = PropertySetNode.createSetHidden(ASYNC_GENERATOR, context);
        if (context.isOptionAsyncStackTraces() && expression != null && expression.hasTag(StandardTags.CallTag.class)) {
            this.setAsyncCallNode = PropertySetNode.createSetHidden(ASYNC_CALL_NODE, context);
        }
        this.performPromiseThenNode = PerformPromiseThenNode.create(context);
        if (context.usePromiseResolve()) {
            this.promiseResolveNode = PromiseResolveNode.create(context);
        } else {
            this.callPromiseResolveNode = JSFunctionCallNode.createCall();
        }
    }

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        if (tag == JSTags.ControlFlowBranchTag.class || tag == JSTags.InputNodeTag.class) {
            return true;
        }
        return super.hasTag(tag);
    }

    protected final Object suspendAwait(VirtualFrame frame, Object value2) {
        Object[] initialState = (Object[])this.readAsyncContextNode.execute(frame);
        CallTarget resumeTarget = (CallTarget)initialState[0];
        Object generatorOrCapability = initialState[1];
        MaterializedFrame asyncContext = (MaterializedFrame)initialState[2];
        if (this.asyncTypeProf.profile(generatorOrCapability instanceof PromiseCapabilityRecord)) {
            JSDynamicObject parentPromise = ((PromiseCapabilityRecord)generatorOrCapability).getPromise();
            this.context.notifyPromiseHook(-1, parentPromise);
        }
        JSDynamicObject promise = this.promiseResolve(value2);
        JSFunctionObject onFulfilled = this.createAwaitFulfilledFunction(resumeTarget, asyncContext, generatorOrCapability);
        JSFunctionObject onRejected = this.createAwaitRejectedFunction(resumeTarget, asyncContext, generatorOrCapability);
        PromiseCapabilityRecord throwawayCapability = this.newThrowawayCapability();
        this.fillAsyncStackTrace(frame, onFulfilled, onRejected);
        this.context.notifyPromiseHook(-1, promise);
        this.echoInput(frame, promise);
        this.performPromiseThenNode.execute(promise, onFulfilled, onRejected, throwawayCapability);
        throw YieldException.AWAIT_NULL;
    }

    private void fillAsyncStackTrace(VirtualFrame frame, JSDynamicObject onFulfilled, JSDynamicObject onRejected) {
        if (this.setAsyncCallNode != null) {
            this.setAsyncCallNode.setValue(onFulfilled, this.expression);
            this.setAsyncCallNode.setValue(onRejected, this.expression);
        }
        if (this.context.isOptionAsyncStackTraces()) {
            Object[] asyncContext = (Object[])this.readAsyncContextNode.execute(frame);
            int asyncStackDepth = 0;
            if (CompilerDirectives.injectBranchProbability(1.0E-4, asyncContext[3] == null && (asyncStackDepth = this.context.getLanguage().getAsyncStackDepth()) > 0)) {
                this.saveStackBranch.enter();
                asyncContext[3] = AbstractAwaitNode.captureStackTrace(this, asyncStackDepth);
            }
        }
    }

    @CompilerDirectives.TruffleBoundary
    private static List<TruffleStackTraceElement> captureStackTrace(Node callNode, int asyncStackDepth) {
        List<TruffleStackTraceElement> stackTrace = TruffleStackTrace.getStackTrace(UserScriptException.create(Undefined.instance, callNode, asyncStackDepth));
        ArrayList<TruffleStackTraceElement> filteredStackTrace = new ArrayList<TruffleStackTraceElement>();
        boolean seenThis = false;
        for (TruffleStackTraceElement s : stackTrace) {
            RootNode rootNode = s.getTarget().getRootNode();
            if (!seenThis) {
                if (rootNode != callNode.getRootNode()) continue;
                seenThis = true;
                continue;
            }
            if (rootNode instanceof FunctionRootNode && ((FunctionRootNode)rootNode).getFunctionData().isAsync() && !((FunctionRootNode)rootNode).getFunctionData().isGenerator() || !(rootNode instanceof JavaScriptRootNode) || !((JavaScriptRootNode)rootNode).isFunction() && !((JavaScriptRootNode)rootNode).isResumption()) continue;
            filteredStackTrace.add(s);
        }
        return filteredStackTrace;
    }

    private JSDynamicObject promiseResolve(Object value2) {
        if (this.context.usePromiseResolve()) {
            return this.promiseResolveNode.execute(this.getRealm().getPromiseConstructor(), value2);
        }
        PromiseCapabilityRecord promiseCapability = this.newPromiseCapability();
        Object resolve = promiseCapability.getResolve();
        this.callPromiseResolveNode.executeCall(JSArguments.createOneArg(Undefined.instance, resolve, value2));
        return promiseCapability.getPromise();
    }

    private PromiseCapabilityRecord newThrowawayCapability() {
        if (this.context.getEcmaScriptVersion() >= 10) {
            return null;
        }
        if (this.setPromiseIsHandledNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.setPromiseIsHandledNode = this.insert(PropertySetNode.createSetHidden(JSPromise.PROMISE_IS_HANDLED, this.context));
        }
        PromiseCapabilityRecord throwawayCapability = this.newPromiseCapability();
        this.setPromiseIsHandledNode.setValueBoolean(throwawayCapability.getPromise(), true);
        return throwawayCapability;
    }

    @Override
    public Object getNodeObject() {
        return JSTags.createNodeObjectDescriptor("type", JSTags.ControlFlowBranchTag.Type.Await.name());
    }

    protected final Object resumeAwait(VirtualFrame frame) {
        Completion result = (Completion)this.readAsyncResultNode.execute(frame);
        this.echoInput(frame, result.getValue());
        if (this.resumptionTypeProf.profile(result.isNormal())) {
            return result.getValue();
        }
        assert (result.isThrow());
        Object reason = result.getValue();
        throw UserScriptException.create(reason, this, this.context.getContextOptions().getStackTraceLimit());
    }

    private PromiseCapabilityRecord newPromiseCapability() {
        if (this.newPromiseCapabilityNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.newPromiseCapabilityNode = this.insert(NewPromiseCapabilityNode.create(this.context));
        }
        return this.newPromiseCapabilityNode.executeDefault();
    }

    private JSFunctionObject createAwaitFulfilledFunction(CallTarget resumeTarget, MaterializedFrame asyncContext, Object generator) {
        JSFunctionData functionData = this.context.getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.AwaitFulfilled, c -> AbstractAwaitNode.createAwaitFulfilledImpl(c));
        JSFunctionObject function = JSFunction.create(this.getRealm(), functionData);
        this.setAsyncTargetNode.setValue(function, resumeTarget);
        this.setAsyncContextNode.setValue(function, asyncContext);
        this.setAsyncGeneratorNode.setValue(function, generator);
        return function;
    }

    private static JSFunctionData createAwaitFulfilledImpl(JSContext context) {
        class AwaitFulfilledRootNode
        extends AwaitSettledRootNode {
            final /* synthetic */ JSContext val$context;

            AwaitFulfilledRootNode(JSContext val$context) {
                this.val$context = val$context;
                super(val$context, false);
            }
        }
        return JSFunctionData.createCallOnly(context, new AwaitFulfilledRootNode(context).getCallTarget(), 1, Strings.EMPTY_STRING);
    }

    private JSFunctionObject createAwaitRejectedFunction(CallTarget resumeTarget, MaterializedFrame asyncContext, Object generator) {
        JSFunctionData functionData = this.context.getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.AwaitRejected, c -> AbstractAwaitNode.createAwaitRejectedImpl(c));
        JSFunctionObject function = JSFunction.create(this.getRealm(), functionData);
        this.setAsyncTargetNode.setValue(function, resumeTarget);
        this.setAsyncContextNode.setValue(function, asyncContext);
        this.setAsyncGeneratorNode.setValue(function, generator);
        return function;
    }

    private static JSFunctionData createAwaitRejectedImpl(JSContext context) {
        class AwaitRejectedRootNode
        extends AwaitSettledRootNode {
            final /* synthetic */ JSContext val$context;

            AwaitRejectedRootNode(JSContext val$context) {
                this.val$context = val$context;
                super(val$context, true);
            }
        }
        return JSFunctionData.createCallOnly(context, new AwaitRejectedRootNode(context).getCallTarget(), 1, Strings.EMPTY_STRING);
    }

    protected void echoInput(VirtualFrame frame, Object value2) {
    }

    public static List<TruffleStackTraceElement> findAsyncStackFramesFromPromise(JSDynamicObject promise) {
        ArrayList<TruffleStackTraceElement> stackTrace = new ArrayList<TruffleStackTraceElement>(4);
        AbstractAwaitNode.collectAsyncStackFramesFromPromise(promise, stackTrace);
        return stackTrace;
    }

    private static void collectAsyncStackFramesFromPromise(JSDynamicObject startPromise, List<TruffleStackTraceElement> stackTrace) {
        JSDynamicObject nextPromise = startPromise;
        do {
            JSFunctionObject handlerFunction;
            RootNode rootNode;
            JSDynamicObject currPromise = nextPromise;
            nextPromise = null;
            Object fulfillReactions = null;
            if (JSPromise.isPending(currPromise)) {
                fulfillReactions = JSObjectUtil.getHiddenProperty(currPromise, JSPromise.PROMISE_FULFILL_REACTIONS);
            }
            if (!(fulfillReactions instanceof SimpleArrayList) || ((SimpleArrayList)fulfillReactions).size() != 1) continue;
            SimpleArrayList fulfillList = (SimpleArrayList)fulfillReactions;
            PromiseReactionRecord reaction = (PromiseReactionRecord)fulfillList.get(0);
            Object handler = reaction.getHandler();
            if (JSFunction.isJSFunction(handler) && (rootNode = ((RootCallTarget)JSFunction.getCallTarget(handlerFunction = (JSFunctionObject)handler)).getRootNode()) instanceof AsyncHandlerRootNode) {
                AsyncHandlerRootNode.AsyncStackTraceInfo result = ((AsyncHandlerRootNode)((Object)rootNode)).getAsyncStackTraceInfo(handlerFunction);
                if (result.stackTraceElement != null) {
                    stackTrace.add(result.stackTraceElement);
                }
                nextPromise = result.promise;
                continue;
            }
            if (reaction.getCapability() == null) continue;
            nextPromise = reaction.getCapability().getPromise();
        } while (nextPromise != null);
    }

    public static List<TruffleStackTraceElement> findAsyncStackFramesFromHandler(JSFunctionObject handlerFunction) {
        ArrayList<TruffleStackTraceElement> stackTrace = new ArrayList<TruffleStackTraceElement>(4);
        RootNode rootNode = ((RootCallTarget)JSFunction.getCallTarget(handlerFunction)).getRootNode();
        if (rootNode instanceof AsyncHandlerRootNode) {
            AsyncHandlerRootNode.AsyncStackTraceInfo result = ((AsyncHandlerRootNode)((Object)rootNode)).getAsyncStackTraceInfo(handlerFunction);
            JSDynamicObject promise = result.promise;
            if (promise != null) {
                AbstractAwaitNode.collectAsyncStackFramesFromPromise(promise, stackTrace);
            }
        }
        return stackTrace;
    }

    static class AwaitSettledRootNode
    extends JavaScriptRootNode
    implements AsyncHandlerRootNode {
        @Node.Child
        private JavaScriptNode valueNode = AccessIndexedArgumentNode.create(0);
        @Node.Child
        private PropertyGetNode getAsyncTarget;
        @Node.Child
        private PropertyGetNode getAsyncContext;
        @Node.Child
        private PropertyGetNode getAsyncGenerator;
        @Node.Child
        private AwaitResumeNode awaitResumeNode;

        AwaitSettledRootNode(JSContext context, boolean rejected) {
            this.getAsyncTarget = PropertyGetNode.createGetHidden(ASYNC_TARGET, context);
            this.getAsyncContext = PropertyGetNode.createGetHidden(ASYNC_CONTEXT, context);
            this.getAsyncGenerator = PropertyGetNode.createGetHidden(ASYNC_GENERATOR, context);
            this.awaitResumeNode = AwaitResumeNode.create(rejected);
        }

        @Override
        public Object execute(VirtualFrame frame) {
            JSFunctionObject functionObject = JSFrameUtil.getFunctionObject(frame);
            CallTarget asyncTarget = (CallTarget)this.getAsyncTarget.getValue(functionObject);
            Object asyncContext = this.getAsyncContext.getValue(functionObject);
            Object generator = this.getAsyncGenerator.getValue(functionObject);
            Object value2 = this.valueNode.execute(frame);
            return this.awaitResumeNode.execute(asyncTarget, asyncContext, generator, value2);
        }

        @Override
        public AsyncHandlerRootNode.AsyncStackTraceInfo getAsyncStackTraceInfo(JSFunctionObject handlerFunction) {
            assert (JSFunction.isJSFunction(handlerFunction) && ((RootCallTarget)JSFunction.getFunctionData(handlerFunction).getCallTarget()).getRootNode() == this);
            RootCallTarget asyncTarget = (RootCallTarget)JSObjectUtil.getHiddenProperty(handlerFunction, ASYNC_TARGET);
            if (asyncTarget.getRootNode() instanceof AsyncRootNode) {
                MaterializedFrame asyncContextFrame = (MaterializedFrame)JSObjectUtil.getHiddenProperty(handlerFunction, ASYNC_CONTEXT);
                Node callNode = (Node)JSObjectUtil.getHiddenProperty(handlerFunction, ASYNC_CALL_NODE);
                TruffleStackTraceElement asyncStackTraceElement = TruffleStackTraceElement.create(callNode, asyncTarget, asyncContextFrame);
                JSDynamicObject asyncPromise = ((AsyncRootNode)((Object)asyncTarget.getRootNode())).getAsyncFunctionPromise(asyncContextFrame);
                return new AsyncHandlerRootNode.AsyncStackTraceInfo(asyncPromise, asyncStackTraceElement);
            }
            return new AsyncHandlerRootNode.AsyncStackTraceInfo();
        }
    }
}

