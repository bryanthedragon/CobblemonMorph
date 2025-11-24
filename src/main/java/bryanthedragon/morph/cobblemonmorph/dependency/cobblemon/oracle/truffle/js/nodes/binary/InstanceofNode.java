
package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.GetMethodNode;
import com.oracle.truffle.js.nodes.access.GetPrototypeNode;
import com.oracle.truffle.js.nodes.access.IsJSObjectNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.binary.InstanceofNodeGen;
import com.oracle.truffle.js.nodes.binary.JSBinaryNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.interop.ForeignObjectPrototypeNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

@ImportStatic(value={JSConfig.class})
public abstract class InstanceofNode
extends JSBinaryNode {
    protected final JSContext context;

    protected InstanceofNode(JSContext context, JavaScriptNode left, JavaScriptNode right) {
        super(left, right);
        this.context = context;
    }

    public static InstanceofNode create(JSContext context) {
        return InstanceofNode.create(context, null, null);
    }

    public static InstanceofNode create(JSContext context, JavaScriptNode left, JavaScriptNode right) {
        return InstanceofNodeGen.create(context, left, right);
    }

    @Override
    public boolean isResultAlwaysOfType(Class<?> clazz) {
        return clazz == Boolean.TYPE;
    }

    public abstract boolean executeBoolean(Object var1, Object var2);

    GetMethodNode createGetMethodHasInstance() {
        return GetMethodNode.create(this.context, Symbol.SYMBOL_HAS_INSTANCE);
    }

    @Specialization(guards={"isObjectNode.executeBoolean(target)"}, limit="1")
    protected boolean doJSObject(Object obj, JSDynamicObject target, @Cached(value="create()") IsJSObjectNode isObjectNode, @Cached(value="createGetMethodHasInstance()") GetMethodNode getMethodHasInstanceNode, @Cached(value="create()") JSToBooleanNode toBooleanNode, @Cached(value="createCall()") JSFunctionCallNode callHasInstanceNode, @Cached(value="create()") IsCallableNode isCallableNode, @Cached(value="createBinaryProfile()") ConditionProfile hasInstanceProfile, @Cached(value="create()") BranchProfile errorBranch) {
        Object hasInstance = getMethodHasInstanceNode.executeWithTarget(target);
        if (hasInstanceProfile.profile(hasInstance == Undefined.instance)) {
            if (!isCallableNode.executeBoolean(target)) {
                errorBranch.enter();
                throw Errors.createTypeErrorInvalidInstanceofTarget(target, this);
            }
            hasInstance = this.getRealm().getOrdinaryHasInstanceFunction();
        }
        Object res = callHasInstanceNode.executeCall(JSArguments.createOneArg(target, hasInstance, obj));
        return toBooleanNode.executeBoolean(res);
    }

    @Specialization(guards={"isNullOrUndefined(target)"})
    protected boolean doNullOrUndefinedTarget(Object obj, JSDynamicObject target) {
        throw Errors.createTypeErrorInvalidInstanceofTarget(target, this);
    }

    @Specialization
    protected boolean doStringTarget(Object obj, TruffleString target) {
        throw Errors.createTypeErrorInvalidInstanceofTarget(target, this);
    }

    @Specialization
    protected boolean doDoubleTarget(Object obj, double target) {
        throw Errors.createTypeErrorInvalidInstanceofTarget(target, this);
    }

    @Specialization
    protected boolean doBooleanTarget(Object obj, boolean target) {
        throw Errors.createTypeErrorInvalidInstanceofTarget(target, this);
    }

    @Specialization
    protected boolean doBigIntTarget(Object obj, BigInt target) {
        throw Errors.createTypeErrorInvalidInstanceofTarget(target, this);
    }

    @Specialization
    protected boolean doSymbolTarget(Object obj, Symbol target) {
        throw Errors.createTypeErrorInvalidInstanceofTarget(target, this);
    }

    @Specialization(guards={"isForeignObject(target)", "isJSDynamicObject(instance)"})
    protected boolean doForeignTargetJSType(JSDynamicObject instance, Object target) {
        return false;
    }

    @Specialization(guards={"isForeignObject(target)", "!isJSDynamicObject(instance)"}, limit="InteropLibraryLimit")
    protected boolean doForeignTargetOther(Object instance, Object target, @CachedLibrary(value="target") InteropLibrary interop) {
        try {
            return interop.isMetaInstance(target, instance);
        }
        catch (UnsupportedMessageException e) {
            throw Errors.createTypeErrorInvalidInstanceofTarget(target, this);
        }
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return InstanceofNodeGen.create(this.context, InstanceofNode.cloneUninitialized(this.getLeft(), materializedTags), InstanceofNode.cloneUninitialized(this.getRight(), materializedTags));
    }

    public static final class OrdinaryHasInstanceRootNode
    extends JavaScriptRootNode {
        @Node.Child
        OrdinaryHasInstanceNode ordinaryHasInstanceNode;

        public OrdinaryHasInstanceRootNode(JSContext context) {
            this.ordinaryHasInstanceNode = OrdinaryHasInstanceNode.create(context);
        }

        @Override
        public Object execute(VirtualFrame frame) {
            Object[] arguments = frame.getArguments();
            Object target = JSArguments.getThisObject(arguments);
            Object obj = JSArguments.getUserArgument(arguments, 0);
            return this.ordinaryHasInstanceNode.executeBoolean(obj, target);
        }

        @Override
        public boolean isInternal() {
            return true;
        }
    }

    public static abstract class IsBoundFunctionCacheNode
    extends JavaScriptBaseNode {
        final boolean multiContext;

        public abstract boolean executeBoolean(JSDynamicObject var1);

        protected IsBoundFunctionCacheNode(boolean multiContext) {
            this.multiContext = multiContext;
        }

        public static IsBoundFunctionCacheNode create(JSContext context) {
            return InstanceofNodeGen.IsBoundFunctionCacheNodeGen.create(context.isMultiContext());
        }

        @Specialization(guards={"!multiContext", "func == cachedFunction"}, limit="1")
        protected static boolean doCachedInstance(JSDynamicObject func, @Cached(value="func") JSDynamicObject cachedFunction, @Cached(value="isBoundFunction(func)") boolean cachedIsBound) {
            assert (IsBoundFunctionCacheNode.isBoundFunction(func) == cachedIsBound);
            return cachedIsBound;
        }

        @Specialization(guards={"cachedShape.check(func)"}, replaces={"doCachedInstance"})
        protected static boolean doCachedShape(JSDynamicObject func, @Cached(value="func.getShape()") Shape cachedShape, @Cached(value="isBoundFunction(func)") boolean cachedIsBound) {
            assert (IsBoundFunctionCacheNode.isBoundFunction(func) == cachedIsBound);
            return cachedIsBound;
        }

        @Specialization(replaces={"doCachedShape"})
        protected static boolean isBoundFunction(JSDynamicObject func) {
            assert (JSFunction.isJSFunction(func));
            return JSFunction.isBoundFunction(func);
        }
    }

    public static abstract class OrdinaryHasInstanceNode
    extends JavaScriptBaseNode {
        protected final JSContext context;
        @CompilerDirectives.CompilationFinal
        private boolean lessThan4 = true;
        @Node.Child
        private PropertyGetNode getPrototypeNode;
        @Node.Child
        private IsBoundFunctionCacheNode boundFuncCacheNode;
        @Node.Child
        protected IsCallableNode isCallableNode;

        public abstract boolean executeBoolean(Object var1, Object var2);

        protected OrdinaryHasInstanceNode(JSContext context) {
            this.context = context;
            this.boundFuncCacheNode = IsBoundFunctionCacheNode.create(context);
            this.isCallableNode = IsCallableNode.create();
        }

        public static OrdinaryHasInstanceNode create(JSContext context) {
            return InstanceofNodeGen.OrdinaryHasInstanceNodeGen.create(context);
        }

        private JSDynamicObject getConstructorPrototype(JSDynamicObject rhs, BranchProfile invalidPrototypeBranch) {
            Object proto;
            if (this.getPrototypeNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getPrototypeNode = this.insert(PropertyGetNode.create(JSObject.PROTOTYPE, this.context));
            }
            if (!JSRuntime.isObject(proto = this.getPrototypeNode.getValue(rhs))) {
                invalidPrototypeBranch.enter();
                throw this.createTypeErrorInvalidPrototype(rhs, proto);
            }
            return (JSDynamicObject)proto;
        }

        @Specialization(guards={"!isCallableNode.executeBoolean(check)"})
        protected boolean doNotCallable(Object obj, Object check) {
            return false;
        }

        @Specialization(guards={"isJSFunction(check)", "isBoundFunction(check)"})
        protected boolean doIsBound(Object obj, JSDynamicObject check, @Cached(value="create(context)") InstanceofNode instanceofNode) {
            JSDynamicObject boundTargetFunction = JSFunction.getBoundTargetFunction(check);
            return instanceofNode.executeBoolean(obj, boundTargetFunction);
        }

        @Specialization(guards={"!isJSObject(left)", "isForeignObject(left)", "isJSFunction(right)", "!isBoundFunction(right)"})
        protected boolean doForeignObject(Object left, JSDynamicObject right, @Cached @Cached.Shared(value="foreignPrototypeNode") ForeignObjectPrototypeNode getForeignPrototypeNode, @Cached @Cached.Shared(value="invalidPrototypeBranch") BranchProfile invalidPrototypeBranch, @Cached(value="create(context)") @Cached.Shared(value="ordinaryHasInstance") OrdinaryHasInstanceNode ordinaryHasInstanceNode) {
            if (this.context.isOptionForeignObjectPrototype()) {
                return this.foreignObjectIntl(left, right, getForeignPrototypeNode, invalidPrototypeBranch, ordinaryHasInstanceNode);
            }
            return false;
        }

        private boolean foreignObjectIntl(Object left, JSDynamicObject right, ForeignObjectPrototypeNode getForeignPrototypeNode, BranchProfile invalidPrototypeBranch, OrdinaryHasInstanceNode ordinaryHasInstanceNode) {
            JSDynamicObject rightProto = this.getConstructorPrototype(right, invalidPrototypeBranch);
            JSDynamicObject foreignProto = getForeignPrototypeNode.execute(left);
            if (foreignProto == rightProto) {
                return true;
            }
            return ordinaryHasInstanceNode.executeBoolean(foreignProto, right);
        }

        @Specialization(guards={"!isJSObject(left)", "!isForeignObject(left)", "isJSFunction(right)", "!isBoundFunction(right)"})
        protected boolean doNotAnObject(Object left, JSDynamicObject right) {
            return false;
        }

        @Specialization(guards={"!isJSObject(left)", "isForeignObject(left)", "isJSProxy(right)", "isCallableProxy(right)"})
        protected boolean doNotAnObjectProxyForeign(Object left, JSDynamicObject right, @Cached @Cached.Shared(value="foreignPrototypeNode") ForeignObjectPrototypeNode getForeignPrototypeNode, @Cached @Cached.Shared(value="invalidPrototypeBranch") BranchProfile invalidPrototypeBranch, @Cached(value="create(context)") @Cached.Shared(value="ordinaryHasInstance") OrdinaryHasInstanceNode ordinaryHasInstanceNode) {
            return this.doForeignObject(left, right, getForeignPrototypeNode, invalidPrototypeBranch, ordinaryHasInstanceNode);
        }

        @Specialization(guards={"!isJSObject(left)", "!isForeignObject(left)", "isJSProxy(right)", "isCallableProxy(right)"})
        protected boolean doNotAnObjectProxyPrimitive(Object left, JSDynamicObject right) {
            return false;
        }

        @Specialization(guards={"isObjectNode.executeBoolean(left)", "isJSFunction(right)", "!isBoundFunction(right)"}, limit="1")
        protected boolean doJSObject(JSDynamicObject left, JSDynamicObject right, @Cached @Cached.Shared(value="isObjectNode") IsJSObjectNode isObjectNode, @Cached @Cached.Shared(value="getPrototype1Node") GetPrototypeNode getPrototype1Node, @Cached @Cached.Shared(value="getPrototype2Node") GetPrototypeNode getPrototype2Node, @Cached @Cached.Shared(value="getPrototype3Node") GetPrototypeNode getPrototype3Node, @Cached @Cached.Shared(value="firstTrue") BranchProfile firstTrue, @Cached @Cached.Shared(value="firstFalse") BranchProfile firstFalse, @Cached @Cached.Shared(value="need2Hops") BranchProfile need2Hops, @Cached @Cached.Shared(value="need3Hops") BranchProfile need3Hops, @Cached @Cached.Shared(value="errorBranch") BranchProfile errorBranch, @Cached @Cached.Shared(value="invalidPrototypeBranch") BranchProfile invalidPrototypeBranch) {
            JSDynamicObject ctorPrototype = this.getConstructorPrototype(right, invalidPrototypeBranch);
            if (this.lessThan4) {
                JSDynamicObject proto = getPrototype1Node.execute(left);
                if (proto == ctorPrototype) {
                    firstTrue.enter();
                    return true;
                }
                if (proto == Null.instance) {
                    firstFalse.enter();
                    return false;
                }
                need2Hops.enter();
                proto = getPrototype2Node.execute(proto);
                if (proto == ctorPrototype) {
                    return true;
                }
                if (proto == Null.instance) {
                    return false;
                }
                need3Hops.enter();
                proto = getPrototype3Node.execute(proto);
                if (proto == ctorPrototype) {
                    return true;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.lessThan4 = false;
            }
            return this.doJSObject4(left, ctorPrototype, getPrototype3Node, errorBranch);
        }

        @Specialization(guards={"isObjectNode.executeBoolean(left)", "isJSProxy(right)", "isCallableProxy(right)"}, limit="1")
        protected boolean doJSObjectProxy(JSDynamicObject left, JSDynamicObject right, @Cached @Cached.Shared(value="isObjectNode") IsJSObjectNode isObjectNode, @Cached @Cached.Shared(value="getPrototype1Node") GetPrototypeNode getPrototype1Node, @Cached @Cached.Shared(value="getPrototype2Node") GetPrototypeNode getPrototype2Node, @Cached @Cached.Shared(value="getPrototype3Node") GetPrototypeNode getPrototype3Node, @Cached @Cached.Shared(value="firstTrue") BranchProfile firstTrue, @Cached @Cached.Shared(value="firstFalse") BranchProfile firstFalse, @Cached @Cached.Shared(value="need2Hops") BranchProfile need2Hops, @Cached @Cached.Shared(value="need3Hops") BranchProfile need3Hops, @Cached @Cached.Shared(value="errorBranch") BranchProfile errorBranch, @Cached @Cached.Shared(value="invalidPrototypeBranch") BranchProfile invalidPrototypeBranch) {
            return this.doJSObject(left, right, isObjectNode, getPrototype1Node, getPrototype2Node, getPrototype3Node, firstTrue, firstFalse, need2Hops, need3Hops, errorBranch, invalidPrototypeBranch);
        }

        private boolean doJSObject4(JSDynamicObject obj, JSDynamicObject check, GetPrototypeNode getLoopedPrototypeNode, BranchProfile errorBranch) {
            JSDynamicObject proto = obj;
            int counter = 0;
            while ((proto = getLoopedPrototypeNode.execute(proto)) != Null.instance) {
                if (++counter > this.context.getContextOptions().getMaxPrototypeChainLength()) {
                    errorBranch.enter();
                    throw Errors.createRangeError("prototype chain length exceeded");
                }
                if (proto != check) continue;
                return true;
            }
            return false;
        }

        protected boolean isBoundFunction(JSDynamicObject func) {
            assert (JSFunction.isJSFunction(func));
            return this.boundFuncCacheNode.executeBoolean(func);
        }

        @CompilerDirectives.TruffleBoundary
        private JSException createTypeErrorInvalidPrototype(JSDynamicObject obj, Object proto) {
            if (this.context.isOptionV8CompatibilityMode()) {
                return Errors.createTypeError("Function has non-object prototype '" + JSRuntime.safeToString(proto) + "' in instanceof check");
            }
            return Errors.createTypeError("\"prototype\" of " + JSRuntime.safeToString(obj) + " is not an Object, it is " + JSRuntime.safeToString(proto), (Node)this);
        }
    }
}

