
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
import com.oracle.truffle.js.nodes.function.FunctionRootNode;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.function.SpreadArgumentNode;
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
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.Lock;

public abstract class JSFunctionCallNode
extends JavaScriptNode
implements JavaScriptFunctionCallNode {
    private static final DebugCounter megamorphicCount = DebugCounter.create("Megamorphic call site count");
    static final byte CALL = 0;
    static final byte NEW = 1;
    static final byte NEW_TARGET = 2;
    protected final byte flags;
    @Node.Child
    protected AbstractCacheNode cacheNode;

    protected JSFunctionCallNode(byte flags) {
        this.flags = flags;
    }

    public static JSFunctionCallNode createCall() {
        return JSFunctionCallNode.create(false);
    }

    public static JSFunctionCallNode createNew() {
        return JSFunctionCallNode.create(true);
    }

    public static JSFunctionCallNode createNewTarget() {
        return JSFunctionCallNode.create(true, true);
    }

    public static JSFunctionCallNode create(boolean isNew) {
        return JSFunctionCallNode.create(isNew, false);
    }

    public static JSFunctionCallNode create(boolean isNew, boolean isNewTarget) {
        return new ExecuteCallNode(JSFunctionCallNode.createFlags(isNew, isNewTarget));
    }

    private static byte createFlags(boolean isNew, boolean isNewTarget) {
        return isNewTarget ? (byte)2 : (isNew ? (byte)1 : 0);
    }

    public static JSFunctionCallNode createCall(JavaScriptNode function, JavaScriptNode target, JavaScriptNode[] arguments, boolean isNew, boolean isNewTarget) {
        byte flags = JSFunctionCallNode.createFlags(isNew, isNewTarget);
        boolean spread = JSFunctionCallNode.hasSpreadArgument(arguments);
        if (spread) {
            return new CallSpreadNode(target, function, arguments, flags);
        }
        if (arguments.length == 0) {
            return new Call0Node(target, function, flags);
        }
        if (arguments.length == 1) {
            return new Call1Node(target, function, arguments[0], flags);
        }
        return new CallNNode(target, function, arguments, flags);
    }

    public static JSFunctionCallNode createInvoke(JSTargetableNode targetFunction, JavaScriptNode[] arguments, boolean isNew, boolean isNewTarget) {
        byte flags = JSFunctionCallNode.createFlags(isNew, isNewTarget);
        boolean spread = JSFunctionCallNode.hasSpreadArgument(arguments);
        if (spread) {
            return new InvokeSpreadNode(targetFunction, arguments, flags);
        }
        if (arguments.length == 0) {
            return new Invoke0Node(targetFunction, flags);
        }
        if (arguments.length == 1) {
            return new Invoke1Node(targetFunction, arguments[0], flags);
        }
        return new InvokeNNode(targetFunction, arguments, flags);
    }

    public static JSFunctionCallNode getUncachedCall() {
        return Uncached.CALL;
    }

    public static JSFunctionCallNode getUncachedNew() {
        return Uncached.NEW;
    }

    static boolean isNewTarget(byte flags) {
        return (flags & 2) != 0;
    }

    static boolean isNew(byte flags) {
        return (flags & 1) != 0;
    }

    private static boolean hasSpreadArgument(JavaScriptNode[] arguments) {
        for (JavaScriptNode arg : arguments) {
            if (!(arg instanceof SpreadArgumentNode)) continue;
            return true;
        }
        return false;
    }

    public final boolean isNew() {
        return JSFunctionCallNode.isNew(this.flags);
    }

    public final boolean isInvoke() {
        return this instanceof InvokeNode;
    }

    protected Object getPropertyKey() {
        return null;
    }

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        if (tag == JSTags.FunctionCallTag.class) {
            return true;
        }
        return super.hasTag(tag);
    }

    @Override
    public Object getNodeObject() {
        NodeObjectDescriptor descriptor = JSTags.createNodeObjectDescriptor();
        descriptor.addProperty("isNew", (Object)this.isNew());
        descriptor.addProperty("isInvoke", (Object)this.isInvoke());
        return descriptor;
    }

    public static JSFunctionCallNode createInternalCall(JavaScriptNode[] arguments) {
        return JSFunctionCallNode.createCall(arguments[0], arguments[1], Arrays.copyOfRange(arguments, 2, arguments.length), false, false);
    }

    @ExplodeLoop
    public Object executeCall(Object[] arguments) {
        Object function = JSArguments.getFunctionObject(arguments);
        AbstractCacheNode c = this.cacheNode;
        while (c != null) {
            if (c.accept(function)) {
                return c.executeCall(arguments);
            }
            c = c.nextNode;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arguments);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object executeAndSpecialize(Object[] arguments) {
        AbstractCacheNode c;
        CompilerAsserts.neverPartOfCompilation();
        Object function = JSArguments.getFunctionObject(arguments);
        Lock lock = this.getLock();
        lock.lock();
        try {
            AbstractCacheNode currentHead = this.cacheNode;
            int cachedCount = 0;
            boolean generic = false;
            c = currentHead;
            while (c != null && !c.accept(function)) {
                if (JSFunctionCallNode.isCached(c)) {
                    assert (!generic);
                    ++cachedCount;
                } else {
                    generic = generic || JSFunctionCallNode.isGeneric(c);
                }
                c = c.nextNode;
            }
            if (c == null) {
                JSContext context = this.getLanguage().getJSContext();
                if (cachedCount < context.getFunctionCacheLimit() && !generic && JSFunction.isJSFunction(function)) {
                    c = this.specializeDirectCall((JSDynamicObject)function, currentHead);
                }
                if (c == null) {
                    boolean hasCached;
                    boolean bl = hasCached = cachedCount > 0;
                    c = JSFunction.isJSFunction(function) ? this.specializeGenericFunction(currentHead, hasCached) : (JSProxy.isJSProxy(function) ? this.insertAtFront(this.specializeProxyCall(function, context), currentHead) : (JSGuards.isForeignObject(function) ? this.specializeForeignCall(arguments, currentHead) : (function instanceof JSNoSuchMethodAdapter ? this.insertAtFront(new JSNoSuchMethodAdapterCacheNode(), currentHead) : this.insertAtFront(new GenericFallbackCacheNode(), JSFunctionCallNode.dropCachedNodes(currentHead, hasCached)))));
                }
                assert (c.getParent() != null);
            }
        }
        finally {
            lock.unlock();
        }
        if (c.accept(function)) {
            return c.executeCall(arguments);
        }
        throw CompilerDirectives.shouldNotReachHere("Inconsistent guard.");
    }

    private AbstractCacheNode specializeProxyCall(Object function, JSContext context) {
        assert (JSProxy.isJSProxy(function));
        if (this.getParent() instanceof JSProxyCallNode) {
            return new JSProxyCallCacheNode(JSFunctionCallNode.isNew(this.flags), JSFunctionCallNode.isNewTarget(this.flags), context);
        }
        return new JSProxyInlineCacheNode(JSFunctionCallNode.isNew(this.flags), JSFunctionCallNode.isNewTarget(this.flags), context);
    }

    private static boolean isCached(AbstractCacheNode c) {
        return c instanceof JSFunctionCacheNode;
    }

    private static boolean isGeneric(AbstractCacheNode c) {
        return c instanceof GenericJSFunctionCacheNode || c instanceof GenericFallbackCacheNode;
    }

    private static boolean isUncached(AbstractCacheNode c) {
        return c instanceof JSProxyInlineCacheNode || c instanceof JSProxyCallCacheNode || c instanceof ForeignCallNode || c instanceof JSNoSuchMethodAdapterCacheNode;
    }

    private static int getCachedCount(AbstractCacheNode head5) {
        int count = 0;
        AbstractCacheNode c = head5;
        while (c != null) {
            if (JSFunctionCallNode.isCached(c)) {
                ++count;
            }
            c = c.nextNode;
        }
        return count;
    }

    private AbstractCacheNode specializeDirectCall(JSDynamicObject functionObj, AbstractCacheNode head5) {
        assert (JSFunction.isJSFunction(functionObj));
        JSFunctionData functionData = JSFunction.getFunctionData(functionObj);
        if (!functionData.getContext().isMultiContext()) {
            return this.specializeDirectCallInstance(functionObj, functionData, head5);
        }
        return this.specializeDirectCallShared(functionObj, functionData, head5);
    }

    private JSFunctionCacheNode specializeDirectCallInstance(JSDynamicObject functionObj, JSFunctionData functionData, AbstractCacheNode head5) {
        JSFunctionCacheNode newNode;
        JSFunctionCacheNode obsoleteNode = null;
        AbstractCacheNode previousNode = null;
        AbstractCacheNode p = null;
        AbstractCacheNode c = head5;
        while (c != null) {
            JSFunctionCacheNode current;
            if (c instanceof JSFunctionCacheNode && (current = (JSFunctionCacheNode)c).isInstanceCache() && functionData == current.getFunctionData()) {
                obsoleteNode = current;
                previousNode = p;
                break;
            }
            p = c;
            c = c.nextNode;
        }
        if (obsoleteNode == null) {
            JSFunctionCacheNode directCall = JSFunctionCallNode.createCallableNode(functionObj, functionData, JSFunctionCallNode.isNew(this.flags), JSFunctionCallNode.isNewTarget(this.flags), true);
            return this.insertAtFront(directCall, head5);
        }
        if (obsoleteNode instanceof FunctionInstanceCacheNode) {
            DirectCallNode callNode = ((FunctionInstanceCacheNode)obsoleteNode).callNode;
            newNode = functionData.isBound() ? new BoundFunctionDataCacheNode(functionData, callNode) : new UnboundFunctionDataCacheNode(functionData, callNode);
        } else {
            newNode = JSFunctionCallNode.createCallableNode(functionObj, functionData, JSFunctionCallNode.isNew(this.flags), JSFunctionCallNode.isNewTarget(this.flags), false);
        }
        return this.replaceCached(newNode, head5, obsoleteNode, previousNode);
    }

    private JSFunctionCacheNode specializeDirectCallShared(JSDynamicObject functionObj, JSFunctionData functionData, AbstractCacheNode head5) {
        JSFunctionCacheNode directCall = JSFunctionCallNode.createCallableNode(functionObj, functionData, JSFunctionCallNode.isNew(this.flags), JSFunctionCallNode.isNewTarget(this.flags), false);
        return this.insertAtFront(directCall, head5);
    }

    private AbstractCacheNode specializeGenericFunction(AbstractCacheNode head5, boolean hasCached) {
        AbstractCacheNode otherGeneric = JSFunctionCallNode.dropCachedNodes(head5, hasCached);
        GenericJSFunctionCacheNode newNode = new GenericJSFunctionCacheNode(this.flags, otherGeneric);
        this.insert(newNode);
        this.cacheNode = newNode;
        this.reportPolymorphicSpecialize();
        return newNode;
    }

    private static AbstractCacheNode dropCachedNodes(AbstractCacheNode head5, boolean hasCached) {
        if (!hasCached) {
            assert (JSFunctionCallNode.getCachedCount(head5) == 0);
            return head5;
        }
        AbstractCacheNode gen = null;
        AbstractCacheNode c = head5;
        while (c != null) {
            if (!JSFunctionCallNode.isCached(c)) {
                assert (JSFunctionCallNode.isGeneric(c) || JSFunctionCallNode.isUncached(c));
                gen = c.withNext(gen);
            }
            c = c.nextNode;
        }
        return gen;
    }

    private AbstractCacheNode specializeForeignCall(Object[] arguments, AbstractCacheNode head5) {
        Object propertyKey;
        ForeignCallNode newNode = null;
        int userArgumentCount = JSArguments.getUserArgumentCount(arguments);
        Object thisObject = JSArguments.getThisObject(arguments);
        if (JSFunctionCallNode.isNew(this.flags) || JSFunctionCallNode.isNewTarget(this.flags)) {
            int skippedArgs = JSFunctionCallNode.isNewTarget(this.flags) ? 1 : 0;
            newNode = new ForeignInstantiateNode(skippedArgs, userArgumentCount - skippedArgs);
        } else if (JSGuards.isForeignObject(thisObject) && Strings.isTString(propertyKey = this.getPropertyKey())) {
            newNode = new ForeignInvokeNode((TruffleString)propertyKey, userArgumentCount);
        }
        if (newNode == null) {
            newNode = new ForeignExecuteNode(userArgumentCount);
        }
        return this.insertAtFront(newNode, head5);
    }

    private <T extends AbstractCacheNode> T insertAtFront(T newNode, AbstractCacheNode head5) {
        this.insert(newNode);
        newNode.nextNode = head5;
        this.cacheNode = newNode;
        return newNode;
    }

    private <T extends AbstractCacheNode> T replaceCached(T newNode, AbstractCacheNode head5, AbstractCacheNode obsoleteNode, AbstractCacheNode previousNode) {
        assert (previousNode == null || previousNode.nextNode == obsoleteNode);
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
        }
        if (JSFunctionCallNode.isGeneric(this.cacheNode)) {
            return NodeCost.MEGAMORPHIC;
        }
        if (this.cacheNode.nextNode != null) {
            return NodeCost.POLYMORPHIC;
        }
        return NodeCost.MONOMORPHIC;
    }

    @Override
    public JavaScriptNode getTarget() {
        return null;
    }

    protected final Object evaluateReceiver(VirtualFrame frame, Object target) {
        JavaScriptNode targetNode = this.getTarget();
        if (targetNode instanceof SuperPropertyReferenceNode) {
            return ((SuperPropertyReferenceNode)targetNode).evaluateTarget(frame);
        }
        return target;
    }

    @ExplodeLoop
    protected static Object[] executeFillObjectArraySpread(JavaScriptNode[] arguments, VirtualFrame frame, Object[] args, int fixedArgumentsLength, BranchProfile growProfile) {
        int i;
        SimpleArrayList<Object> argList = SimpleArrayList.create((long)fixedArgumentsLength + (long)arguments.length + 3L);
        for (i = 0; i < fixedArgumentsLength; ++i) {
            argList.addUnchecked(args[i]);
        }
        for (i = 0; i < arguments.length; ++i) {
            if (arguments[i] instanceof SpreadArgumentNode) {
                ((SpreadArgumentNode)arguments[i]).executeToList(frame, argList, growProfile);
                continue;
            }
            argList.add(arguments[i].execute(frame), growProfile);
        }
        return argList.toArray();
    }

    protected static JSFunctionCacheNode createCallableNode(JSDynamicObject function, JSFunctionData functionData, boolean isNew, boolean isNewTarget, boolean cacheOnInstance) {
        CallTarget callTarget = JSFunctionCallNode.getCallTarget(functionData, isNew, isNewTarget);
        assert (callTarget != null);
        if (JSFunction.isBoundFunction(function) && JSFunctionCallNode.isBoundFunctionNestingDepthWithinLimits(function)) {
            if (cacheOnInstance) {
                return new BoundFunctionInstanceCallNode(function, isNew, isNewTarget);
            }
            return new DynamicBoundFunctionCallNode(isNew, isNewTarget, functionData);
        }
        JSFunctionCacheNode node = JSFunctionCallNode.tryInlineBuiltinFunctionCall(function, functionData, callTarget, cacheOnInstance);
        if (node != null) {
            return node;
        }
        if (cacheOnInstance) {
            return new FunctionInstanceCacheNode(function, callTarget);
        }
        if (JSFunction.isBoundFunction(function)) {
            return new BoundFunctionDataCacheNode(functionData, callTarget);
        }
        return new UnboundFunctionDataCacheNode(functionData, callTarget);
    }

    private static boolean isBoundFunctionNestingDepthWithinLimits(JSDynamicObject function) {
        JSDynamicObject boundFunction = function;
        for (int i = 0; i < 10; ++i) {
            if (JSFunction.isBoundFunction(boundFunction = JSFunction.getBoundTargetFunction(boundFunction))) continue;
            return true;
        }
        return false;
    }

    protected static CallTarget getCallTarget(JSFunctionData functionData, boolean isNew, boolean isNewTarget) {
        if (isNewTarget) {
            return functionData.getConstructNewTarget();
        }
        if (isNew) {
            return functionData.getConstructTarget();
        }
        return functionData.getCallTarget();
    }

    private static JSFunctionCacheNode tryInlineBuiltinFunctionCall(JSDynamicObject function, JSFunctionData functionData, CallTarget callTarget, boolean cacheOnInstance) {
        JavaScriptNode body;
        RootNode rootNode;
        if (callTarget instanceof RootCallTarget && (rootNode = ((RootCallTarget)callTarget).getRootNode()) instanceof FunctionRootNode && (body = ((FunctionRootNode)rootNode).getBody()) instanceof JSBuiltinNode) {
            JSBuiltinNode builtinNode = (JSBuiltinNode)body;
            JSBuiltinNode.Inlined inlined2 = builtinNode.tryCreateInlined();
            if (inlined2 != null) {
                if (cacheOnInstance) {
                    return new InlinedBuiltinFunctionInstanceCacheNode(function, callTarget, inlined2);
                }
                return new InlinedBuiltinFunctionDataCacheNode(functionData, callTarget, inlined2);
            }
            if (builtinNode.isCallerSensitive()) {
                if (cacheOnInstance) {
                    return new CallerSensitiveBuiltinFunctionInstanceCacheNode(function, functionData, callTarget);
                }
                return new CallerSensitiveBuiltinFunctionDataCacheNode(functionData, callTarget);
            }
        }
        return null;
    }

    private static class Uncached
    extends JSFunctionCallNode {
        static final Uncached CALL = new Uncached(JSFunctionCallNode.createFlags(false, false));
        static final Uncached NEW = new Uncached(JSFunctionCallNode.createFlags(true, false));

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
            if (this.isNew()) {
                return JSRuntime.construct(functionObject, functionArgs);
            }
            return JSRuntime.call(functionObject, JSArguments.getThisObject(arguments), functionArgs);
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return this;
        }
    }

    private static class GenericFallbackCacheNode
    extends AbstractCacheNode {
        GenericFallbackCacheNode() {
            megamorphicCount.inc();
        }

        @Override
        protected boolean accept(Object function) {
            return !JSFunction.isJSFunction(function) && !JSProxy.isJSProxy(function) && !JSGuards.isForeignObject(function) && !(function instanceof JSNoSuchMethodAdapter);
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
                if (!(current instanceof JSFunctionCallNode)) continue;
                callNode = (JSFunctionCallNode)current;
                break;
            }
            if (callNode != null) {
                if (callNode instanceof InvokeNode) {
                    expressionStr = ((InvokeNode)callNode).functionTargetNode.expressionToString();
                } else if (callNode instanceof CallNode) {
                    expressionStr = ((CallNode)callNode).functionNode.expressionToString();
                }
            }
            return Errors.createTypeErrorNotAFunction(expressionStr != null ? expressionStr : function, this);
        }
    }

    private static class JSNoSuchMethodAdapterCacheNode
    extends AbstractCacheNode {
        @Node.Child
        private JSFunctionCallNode noSuchMethodCallNode = JSFunctionCallNode.createCall();

        JSNoSuchMethodAdapterCacheNode() {
        }

        @Override
        public Object executeCall(Object[] arguments) {
            Object function = JSArguments.getFunctionObject(arguments);
            assert (this.accept(function));
            JSNoSuchMethodAdapter noSuchMethod = (JSNoSuchMethodAdapter)function;
            Object[] handlerArguments = JSArguments.createInitial(noSuchMethod.getThisObject(), noSuchMethod.getFunction(), JSArguments.getUserArgumentCount(arguments) + 1);
            JSArguments.setUserArgument(handlerArguments, 0, noSuchMethod.getKey());
            JSArguments.setUserArguments(handlerArguments, 1, JSArguments.extractUserArguments(arguments));
            return this.noSuchMethodCallNode.executeCall(handlerArguments);
        }

        @Override
        protected boolean accept(Object function) {
            return function instanceof JSNoSuchMethodAdapter;
        }
    }

    private static class JSProxyCallCacheNode
    extends AbstractCacheNode {
        @Node.Child
        private DirectCallNode proxyCallNode;

        JSProxyCallCacheNode(boolean isNew, boolean isNewTarget, JSContext context) {
            CallTarget target;
            JSFunctionData functionData = JSProxy.createProxyCallFunctionData(context);
            if (isNewTarget) {
                target = functionData.getConstructNewTarget();
            } else if (isNew) {
                target = functionData.getConstructTarget();
            } else {
                assert (!isNew && !isNewTarget);
                target = functionData.getCallTarget();
            }
            this.proxyCallNode = DirectCallNode.create(target);
        }

        @Override
        public Object executeCall(Object[] arguments) {
            assert (this.accept(JSArguments.getFunctionObject(arguments)));
            return this.proxyCallNode.call(arguments);
        }

        @Override
        protected boolean accept(Object function) {
            return JSProxy.isJSProxy(function);
        }
    }

    private static class JSProxyInlineCacheNode
    extends AbstractCacheNode {
        @Node.Child
        private JSProxyCallNode proxyCall;

        JSProxyInlineCacheNode(boolean isNew, boolean isNewTarget, JSContext context) {
            this.proxyCall = JSProxyCallNode.create(context, isNew, isNewTarget);
        }

        @Override
        public Object executeCall(Object[] arguments) {
            assert (this.accept(JSArguments.getFunctionObject(arguments)));
            return this.proxyCall.execute(arguments);
        }

        @Override
        protected boolean accept(Object function) {
            return JSProxy.isJSProxy(function);
        }
    }

    private static class GenericJSFunctionCacheNode
    extends AbstractCacheNode {
        private final byte flags;
        @Node.Child
        private IndirectCallNode indirectCallNode;
        @Node.Child
        private AbstractCacheNode next;
        private final BranchProfile initBranch;

        GenericJSFunctionCacheNode(byte flags, AbstractCacheNode next) {
            this.flags = flags;
            this.indirectCallNode = Truffle.getRuntime().createIndirectCallNode();
            this.next = next;
            this.initBranch = BranchProfile.create();
            megamorphicCount.inc();
        }

        @Override
        public Object executeCall(Object[] arguments) {
            Object function = JSArguments.getFunctionObject(arguments);
            JSDynamicObject functionObject = (JSDynamicObject)function;
            JSFunctionData functionData = JSFunction.getFunctionData(functionObject);
            if (JSFunctionCallNode.isNewTarget(this.flags)) {
                return this.indirectCallNode.call(functionData.getConstructNewTarget(this.initBranch), arguments);
            }
            if (JSFunctionCallNode.isNew(this.flags)) {
                return this.indirectCallNode.call(functionData.getConstructTarget(this.initBranch), arguments);
            }
            return this.indirectCallNode.call(functionData.getCallTarget(this.initBranch), arguments);
        }

        @Override
        protected boolean accept(Object function) {
            return JSFunction.isJSFunction(function);
        }
    }

    private static class ForeignInstantiateNode
    extends ForeignCallNode {
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
            }
            catch (ArityException | UnsupportedMessageException | UnsupportedTypeException e) {
                throw Errors.createTypeErrorInteropException(function, e, "instantiate", this);
            }
        }
    }

    private static final class ForeignInvokeNode
    extends ForeignExecuteNode {
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
            Object callReturn;
            Object receiver = this.thisClassProfile.profile(JSArguments.getThisObject(arguments));
            Object[] callArguments = this.exportArguments(arguments);
            if (JSGuards.isForeignObject(receiver)) {
                assert (JSArguments.getFunctionObject(arguments) == receiver);
                if (this.interop.isNull(receiver)) {
                    this.errorBranch.enter();
                    throw Errors.createTypeErrorCannotGetProperty(this.getContext(), this.functionName, receiver, false, this);
                }
                if (this.optimistic) {
                    try {
                        callReturn = this.interop.invokeMember(receiver, this.functionNameJavaString, callArguments);
                    }
                    catch (UnknownIdentifierException | UnsupportedMessageException e) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        this.optimistic = false;
                        callReturn = this.fallback(receiver, arguments, callArguments, e);
                    }
                    catch (ArityException | UnsupportedTypeException e) {
                        this.errorBranch.enter();
                        throw Errors.createTypeErrorInteropException(receiver, e, "invokeMember", this.functionName, this);
                    }
                } else if (this.interop.isMemberInvocable(receiver, this.functionNameJavaString)) {
                    try {
                        callReturn = this.interop.invokeMember(receiver, this.functionNameJavaString, callArguments);
                    }
                    catch (ArityException | UnknownIdentifierException | UnsupportedMessageException | UnsupportedTypeException e) {
                        this.errorBranch.enter();
                        throw Errors.createTypeErrorInteropException(receiver, e, "invokeMember", this.functionName, this);
                    }
                } else {
                    callReturn = this.fallback(receiver, arguments, callArguments, null);
                }
            } else {
                Object function = this.getForeignFunction(arguments);
                try {
                    callReturn = this.interop.execute(function, callArguments);
                }
                catch (ArityException | UnsupportedMessageException | UnsupportedTypeException e) {
                    this.errorBranch.enter();
                    throw Errors.createTypeErrorInteropException(function, e, "execute", this);
                }
            }
            return this.convertForeignReturn(callReturn);
        }

        private Object fallback(Object receiver, Object[] arguments, Object[] callArguments, InteropException caughtException) {
            Object function;
            InteropException ex = caughtException;
            if ((this.getContext().getContextOptions().hasForeignObjectPrototype() || JSInteropUtil.isBoxedPrimitive(receiver, this.interop)) && (function = this.maybeGetFromPrototype(receiver)) != Undefined.instance) {
                return this.callJSFunction(receiver, function, arguments);
            }
            if (this.getContext().getContextOptions().hasForeignHashProperties() && this.interop.hasHashEntries(receiver) && this.interop.isHashEntryReadable(receiver, this.functionName)) {
                try {
                    function = this.interop.readHashValue(receiver, this.functionName);
                    return InteropLibrary.getUncached().execute(function, callArguments);
                }
                catch (ArityException | UnknownKeyException | UnsupportedMessageException | UnsupportedTypeException e) {
                    ex = e;
                }
            }
            this.errorBranch.enter();
            throw Errors.createTypeErrorInteropException(receiver, ex != null ? ex : UnknownIdentifierException.create(Strings.toJavaString(this.functionName)), "invokeMember", this.functionName, this);
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

    private static class ForeignExecuteNode
    extends ForeignCallNode {
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
            }
            catch (ArityException | UnsupportedMessageException | UnsupportedTypeException e) {
                throw Errors.createTypeErrorInteropException(function, e, "execute", this);
            }
        }
    }

    private static abstract class ForeignCallNode
    extends AbstractCacheNode {
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

    private static final class CallerSensitiveBuiltinFunctionDataCacheNode
    extends CallerSensitiveBuiltinCallNode {
        CallerSensitiveBuiltinFunctionDataCacheNode(JSFunctionData functionData, CallTarget callTarget) {
            super(functionData, callTarget);
        }

        @Override
        protected boolean accept(Object function) {
            return JSFunction.isJSFunction(function) && this.functionData == JSFunction.getFunctionData((JSDynamicObject)function);
        }
    }

    private static final class CallerSensitiveBuiltinFunctionInstanceCacheNode
    extends CallerSensitiveBuiltinCallNode {
        private final JSDynamicObject functionObj;

        CallerSensitiveBuiltinFunctionInstanceCacheNode(JSDynamicObject functionObj, JSFunctionData functionData, CallTarget callTarget) {
            super(functionData, callTarget);
            assert (JSFunction.isJSFunction(functionObj));
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

    private static abstract class CallerSensitiveBuiltinCallNode
    extends JSFunctionCacheNode {
        @Node.Child
        private DirectCallNode callNode;
        protected final JSFunctionData functionData;

        CallerSensitiveBuiltinCallNode(JSFunctionData functionData, CallTarget callTarget) {
            this.functionData = functionData;
            this.callNode = Truffle.getRuntime().createDirectCallNode(callTarget);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Object executeCall(Object[] arguments) {
            JSRealm realm = this.getRealm();
            JavaScriptBaseNode prev = realm.getCallNode();
            try {
                realm.setCallNode(this);
                Object object = this.callNode.call(arguments);
                return object;
            }
            finally {
                realm.setCallNode(prev);
            }
        }

        @Override
        protected final JSFunctionData getFunctionData() {
            return this.functionData;
        }
    }

    private static final class InlinedBuiltinFunctionDataCacheNode
    extends InlinedBuiltinCallNode {
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

    private static final class InlinedBuiltinFunctionInstanceCacheNode
    extends InlinedBuiltinCallNode {
        private final JSDynamicObject functionObj;

        InlinedBuiltinFunctionInstanceCacheNode(JSDynamicObject functionObj, CallTarget callTarget, JSBuiltinNode.Inlined builtinNode) {
            super(callTarget, builtinNode);
            assert (JSFunction.isJSFunction(functionObj));
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

    private static abstract class InlinedBuiltinCallNode
    extends JSFunctionCacheNode {
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
            }
            try {
                return this.builtinNode.callInlined(arguments);
            }
            catch (JSBuiltinNode.RewriteToCallException e) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.callNode = this.insert(Truffle.getRuntime().createDirectCallNode(this.callTarget));
                this.callNode.cloneCallTarget();
                this.callNode.forceInlining();
                return this.callNode.call(arguments);
            }
        }
    }

    private static abstract class UnboundJSFunctionCacheNode
    extends JSFunctionCacheNode {
        @Node.Child
        DirectCallNode callNode;

        UnboundJSFunctionCacheNode(CallTarget callTarget) {
            RootNode root;
            this.callNode = Truffle.getRuntime().createDirectCallNode(callTarget);
            if (callTarget instanceof RootCallTarget && (root = ((RootCallTarget)callTarget).getRootNode()) instanceof FunctionRootNode && ((FunctionRootNode)root).isInlineImmediately()) {
                this.insert(this.callNode);
                if (((FunctionRootNode)root).isSplitImmediately()) {
                    this.callNode.cloneCallTarget();
                }
                this.callNode.forceInlining();
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

    private static final class DynamicBoundFunctionCallNode
    extends JSFunctionCacheNode {
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
            }
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

        @Override
        protected boolean accept(Object function) {
            return JSFunction.isJSFunction(function) && this.boundFunctionData == JSFunction.getFunctionData((JSDynamicObject)function);
        }

        @Override
        protected JSFunctionData getFunctionData() {
            return this.boundFunctionData;
        }
    }

    private static final class BoundFunctionInstanceCallNode
    extends JSFunctionCacheNode {
        @Node.Child
        private AbstractCacheNode boundNode;
        private final JSDynamicObject boundFunctionObj;
        private final Object boundThis;
        private final JSDynamicObject targetFunctionObj;
        private final Object[] addArguments;
        private final boolean useDynamicThis;
        private final boolean isNewTarget;

        BoundFunctionInstanceCallNode(JSDynamicObject function, boolean isNew, boolean isNewTarget) {
            Object lastReceiver;
            assert (JSFunction.isBoundFunction(function));
            this.boundFunctionObj = function;
            JSDynamicObject lastFunction = function;
            ArrayList<Object> prefixArguments = new ArrayList<Object>();
            do {
                Object[] extraArguments = JSFunction.getBoundArguments(lastFunction);
                prefixArguments.addAll(0, Arrays.asList(extraArguments));
                lastReceiver = JSFunction.getBoundThis(lastFunction);
            } while (JSFunction.isBoundFunction(lastFunction = JSFunction.getBoundTargetFunction(lastFunction)) && !isNewTarget);
            this.addArguments = prefixArguments.toArray(JSArguments.EMPTY_ARGUMENTS_ARRAY);
            this.targetFunctionObj = lastFunction;
            if (isNew || isNewTarget) {
                this.useDynamicThis = true;
                this.boundThis = null;
            } else {
                this.useDynamicThis = false;
                this.boundThis = lastReceiver;
            }
            this.isNewTarget = isNewTarget;
            this.boundNode = JSFunctionCallNode.createCallableNode(lastFunction, JSFunction.getFunctionData(lastFunction), isNew, isNewTarget, true);
        }

        @Override
        public Object executeCall(Object[] arguments) {
            assert (this.checkTargetFunction(arguments));
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
                if (!this.isNewTarget) continue;
                return this.targetFunctionObj == targetFunction;
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

    private static abstract class JSFunctionCacheNode
    extends AbstractCacheNode {
        JSFunctionCacheNode() {
        }

        protected boolean isInstanceCache() {
            return false;
        }

        protected abstract JSFunctionData getFunctionData();
    }

    private static abstract class AbstractCacheNode
    extends JavaScriptBaseNode {
        @Node.Child
        protected AbstractCacheNode nextNode;

        private AbstractCacheNode() {
        }

        protected abstract boolean accept(Object var1);

        public abstract Object executeCall(Object[] var1);

        protected AbstractCacheNode withNext(AbstractCacheNode newNext) {
            AbstractCacheNode copy = (AbstractCacheNode)this.copy();
            copy.nextNode = newNext;
            return copy;
        }

        @Override
        public final NodeCost getCost() {
            return NodeCost.NONE;
        }
    }

    private static final class BoundFunctionDataCacheNode
    extends UnboundJSFunctionCacheNode {
        private final JSFunctionData functionData;

        BoundFunctionDataCacheNode(JSFunctionData functionData, CallTarget callTarget) {
            super(callTarget);
            this.functionData = functionData;
            assert (functionData.isBound());
        }

        BoundFunctionDataCacheNode(JSFunctionData functionData, DirectCallNode directCallNode) {
            super(directCallNode);
            this.functionData = functionData;
        }

        @Override
        protected boolean accept(Object function) {
            return function instanceof JSFunctionObject.Bound && JSFunction.getFunctionData((JSFunctionObject.Bound)function) == this.functionData;
        }

        @Override
        protected JSFunctionData getFunctionData() {
            return this.functionData;
        }
    }

    private static final class UnboundFunctionDataCacheNode
    extends UnboundJSFunctionCacheNode {
        private final JSFunctionData functionData;

        UnboundFunctionDataCacheNode(JSFunctionData functionData, CallTarget callTarget) {
            super(callTarget);
            this.functionData = functionData;
            assert (!functionData.isBound());
        }

        UnboundFunctionDataCacheNode(JSFunctionData functionData, DirectCallNode directCallNode) {
            super(directCallNode);
            this.functionData = functionData;
        }

        @Override
        protected boolean accept(Object function) {
            return function instanceof JSFunctionObject.Unbound && JSFunction.getFunctionData((JSFunctionObject.Unbound)function) == this.functionData;
        }

        @Override
        protected JSFunctionData getFunctionData() {
            return this.functionData;
        }
    }

    private static final class FunctionInstanceCacheNode
    extends UnboundJSFunctionCacheNode {
        private final JSDynamicObject functionObj;

        FunctionInstanceCacheNode(JSDynamicObject functionObj, CallTarget callTarget) {
            super(callTarget);
            assert (JSFunction.isJSFunction(functionObj));
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

    static class ExecuteCallNode
    extends JSFunctionCallNode {
        protected ExecuteCallNode(byte flags) {
            super(flags);
        }

        @Override
        public Object execute(VirtualFrame frame) {
            throw Errors.shouldNotReachHere();
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new ExecuteCallNode(this.flags);
        }
    }

    static class InvokeSpreadNode
    extends InvokeNNode {
        private final BranchProfile growProfile = BranchProfile.create();

        protected InvokeSpreadNode(JSTargetableNode functionNode, JavaScriptNode[] arguments, byte flags) {
            this(null, functionNode, arguments, flags);
        }

        protected InvokeSpreadNode(JavaScriptNode targetNode, JSTargetableNode functionNode, JavaScriptNode[] arguments, byte flags) {
            super(targetNode, functionNode, arguments, flags);
        }

        @Override
        protected Object[] executeFillObjectArray(VirtualFrame frame, Object[] args, int delta) {
            return InvokeSpreadNode.executeFillObjectArraySpread(this.arguments, frame, args, delta, this.growProfile);
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new InvokeSpreadNode(InvokeSpreadNode.cloneUninitialized(this.targetNode, materializedTags), InvokeSpreadNode.cloneUninitialized(this.functionTargetNode, materializedTags), InvokeSpreadNode.cloneUninitialized(this.arguments, materializedTags), this.flags);
        }
    }

    static class InvokeNNode
    extends InvokeNode {
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
            for (int i = 0; i < this.arguments.length; ++i) {
                assert (!(this.arguments[i] instanceof SpreadArgumentNode));
                args[i + delta] = this.arguments[i].execute(frame);
            }
            return args;
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new InvokeNNode(InvokeNNode.cloneUninitialized(this.targetNode, materializedTags), InvokeNNode.cloneUninitialized(this.functionTargetNode, materializedTags), InvokeNNode.cloneUninitialized(this.arguments, materializedTags), this.flags);
        }

        @Override
        protected JavaScriptNode[] getArgumentNodes() {
            return this.arguments;
        }

        @Override
        protected void materializeInstrumentableArguments() {
            for (int i = 0; i < this.arguments.length; ++i) {
                if (this.arguments[i] instanceof SpreadArgumentNode || this.arguments[i].isInstrumentable()) continue;
                this.arguments[i] = this.insert(JSInputGeneratingNodeWrapper.create(this.arguments[i]));
            }
        }
    }

    static class Invoke1Node
    extends InvokeNode {
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
            return new Invoke1Node(Invoke1Node.cloneUninitialized(this.targetNode, materializedTags), Invoke1Node.cloneUninitialized(this.functionTargetNode, materializedTags), Invoke1Node.cloneUninitialized(this.argument0, materializedTags), this.flags);
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

    static class Invoke0Node
    extends InvokeNode {
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
            return new Invoke0Node(Invoke0Node.cloneUninitialized(this.targetNode, materializedTags), Invoke0Node.cloneUninitialized(this.functionTargetNode, materializedTags), this.flags);
        }

        @Override
        protected JavaScriptNode[] getArgumentNodes() {
            return new JavaScriptNode[0];
        }

        @Override
        protected void materializeInstrumentableArguments() {
        }
    }

    public static abstract class InvokeNode
    extends JSFunctionCallNode {
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
            if (this.targetNode != null) {
                return this.targetNode;
            }
            return this.functionTargetNode.getTarget();
        }

        protected abstract Object[] createArguments(VirtualFrame var1, Object var2, Object var3);

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
            if (this.targetNode != null) {
                return this.targetNode.execute(frame);
            }
            return this.functionTargetNode.evaluateTarget(frame);
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
            }
            if (materializedTags.contains(JSTags.FunctionCallTag.class) || materializedTags.contains(JSTags.ReadPropertyTag.class) || materializedTags.contains(JSTags.ReadElementTag.class)) {
                this.materializeInstrumentableArguments();
                InvokeNode invoke2 = (InvokeNode)InvokeNode.createInvoke(null, InvokeNode.cloneUninitialized(this.getArgumentNodes(), materializedTags), InvokeNode.isNew(this.flags), InvokeNode.isNewTarget(this.flags));
                JSTargetableNode functionTargetNodeDelegate = InvokeNode.cloneUninitialized(this.getFunctionTargetDelegate(), materializedTags);
                JavaScriptNode target = functionTargetNodeDelegate.getTarget();
                invoke2.targetNode = !target.isInstrumentable() ? JSInputGeneratingNodeWrapper.create(target) : target;
                invoke2.functionTargetNode = JSMaterializedInvokeTargetableNode.createFor(functionTargetNodeDelegate);
                InvokeNode.transferSourceSectionAndTags(functionTargetNodeDelegate, invoke2.functionTargetNode);
                InvokeNode.transferSourceSectionAndTags(this, invoke2);
                return invoke2;
            }
            return this;
        }

        private JSTargetableNode getFunctionTargetDelegate() {
            if (this.functionTargetNode instanceof InstrumentableNode.WrapperNode) {
                return (JSTargetableNode)((InstrumentableNode.WrapperNode)((Object)this.functionTargetNode)).getDelegateNode();
            }
            return this.functionTargetNode;
        }

        @Override
        protected Object getPropertyKey() {
            JavaScriptNode propertyNode = this.functionTargetNode;
            if (propertyNode instanceof InstrumentableNode.WrapperNode) {
                propertyNode = (JavaScriptNode)((InstrumentableNode.WrapperNode)((Object)propertyNode)).getDelegateNode();
            }
            if (propertyNode instanceof PropertyNode) {
                return ((PropertyNode)propertyNode).getPropertyKey();
            }
            return null;
        }

        public JSTargetableNode getFunctionTargetNode() {
            return this.functionTargetNode;
        }
    }

    static class CallSpreadNode
    extends CallNNode {
        private final BranchProfile growProfile = BranchProfile.create();

        protected CallSpreadNode(JavaScriptNode targetNode, JavaScriptNode functionNode, JavaScriptNode[] arguments, byte flags) {
            super(targetNode, functionNode, arguments, flags);
        }

        @Override
        protected Object[] executeFillObjectArray(VirtualFrame frame, Object[] args, int delta) {
            return CallSpreadNode.executeFillObjectArraySpread(this.arguments, frame, args, delta, this.growProfile);
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new CallSpreadNode(CallSpreadNode.cloneUninitialized(this.targetNode, materializedTags), CallSpreadNode.cloneUninitialized(this.functionNode, materializedTags), CallSpreadNode.cloneUninitialized(this.arguments, materializedTags), this.flags);
        }
    }

    static class CallNNode
    extends CallNode {
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
            for (int i = 0; i < this.arguments.length; ++i) {
                assert (!(this.arguments[i] instanceof SpreadArgumentNode));
                args[i + delta] = this.arguments[i].execute(frame);
            }
            return args;
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new CallNNode(CallNNode.cloneUninitialized(this.targetNode, materializedTags), CallNNode.cloneUninitialized(this.functionNode, materializedTags), CallNNode.cloneUninitialized(this.arguments, materializedTags), this.flags);
        }

        @Override
        protected JavaScriptNode[] getArgumentNodes() {
            return this.arguments;
        }

        @Override
        protected void materializeInstrumentableArguments() {
            for (int i = 0; i < this.arguments.length; ++i) {
                if (this.arguments[i] instanceof SpreadArgumentNode || this.arguments[i].isInstrumentable()) continue;
                this.arguments[i] = this.insert(JSInputGeneratingNodeWrapper.create(this.arguments[i]));
            }
        }
    }

    static class Call1Node
    extends CallNode {
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
            return new Call1Node(Call1Node.cloneUninitialized(this.targetNode, materializedTags), Call1Node.cloneUninitialized(this.functionNode, materializedTags), Call1Node.cloneUninitialized(this.argument0, materializedTags), this.flags);
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

    static class Call0Node
    extends CallNode {
        protected Call0Node(JavaScriptNode targetNode, JavaScriptNode functionNode, byte flags) {
            super(targetNode, functionNode, flags);
        }

        @Override
        protected final Object[] createArguments(VirtualFrame frame, Object target, Object function) {
            return JSArguments.createZeroArg(target, function);
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new Call0Node(Call0Node.cloneUninitialized(this.targetNode, materializedTags), Call0Node.cloneUninitialized(this.functionNode, materializedTags), this.flags);
        }

        @Override
        protected JavaScriptNode[] getArgumentNodes() {
            return new JavaScriptNode[0];
        }

        @Override
        protected void materializeInstrumentableArguments() {
        }
    }

    static abstract class CallNode
    extends JSFunctionCallNode {
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

        protected abstract Object[] createArguments(VirtualFrame var1, Object var2, Object var3);

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
            if (this.targetNode != null) {
                return this.targetNode.execute(frame);
            }
            return Undefined.instance;
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
                    CallNode.transferSourceSectionAddExpressionTag(this, this.functionNode);
                }
                if (this.targetNode != null) {
                    return this;
                }
                JavaScriptNode materializedTargetNode = JSInputGeneratingNodeWrapper.create(JSConstantNode.JSConstantUndefinedNode.createUndefined());
                JSFunctionCallNode call = CallNode.createCall(CallNode.cloneUninitialized(this.functionNode, materializedTags), materializedTargetNode, CallNode.cloneUninitialized(this.getArgumentNodes(), materializedTags), CallNode.isNew(this.flags), CallNode.isNewTarget(this.flags));
                CallNode.transferSourceSectionAndTags(this, call);
                return call;
            }
            return this;
        }
    }
}

