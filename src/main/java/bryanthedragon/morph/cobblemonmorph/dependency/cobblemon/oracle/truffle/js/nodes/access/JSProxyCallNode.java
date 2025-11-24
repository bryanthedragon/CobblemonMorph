
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.GetMethodNode;
import com.oracle.truffle.js.nodes.access.JSProxyCallNodeGen;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.nodes.unary.IsConstructorNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.builtins.JSProxyObject;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

@NodeInfo(cost=NodeCost.NONE)
@ImportStatic(value={JSProxy.class, JSArguments.class})
public abstract class JSProxyCallNode
extends JavaScriptBaseNode {
    private final JSContext context;
    @Node.Child
    private GetMethodNode trapGetter;
    @Node.Child
    private JSFunctionCallNode callNode;
    @Node.Child
    private JSFunctionCallNode callTrapNode;
    protected final boolean isNew;
    protected final boolean isNewTarget;
    private final ConditionProfile pxTrapFunProfile = ConditionProfile.createBinaryProfile();
    private final BranchProfile errorBranch = BranchProfile.create();

    protected JSProxyCallNode(JSContext context, boolean isNew, boolean isNewTarget) {
        this.callNode = isNew || isNewTarget ? JSFunctionCallNode.createNewTarget() : JSFunctionCallNode.createCall();
        this.callTrapNode = JSFunctionCallNode.createCall();
        this.trapGetter = GetMethodNode.create(context, isNewTarget || isNew ? JSProxy.CONSTRUCT : JSProxy.APPLY);
        this.context = context;
        this.isNew = isNew;
        this.isNewTarget = isNewTarget;
    }

    public abstract Object execute(Object[] var1);

    public static JSProxyCallNode create(JSContext context, boolean isNew, boolean isNewTarget) {
        return JSProxyCallNodeGen.create(context, isNew, isNewTarget);
    }

    @Specialization(guards={"!isNew", "!isNewTarget"})
    protected Object doCall(Object[] arguments, @Cached IsCallableNode isCallable) {
        Object thisObj = JSArguments.getThisObject(arguments);
        Object function = JSArguments.getFunctionObject(arguments);
        JSProxyObject proxy = (JSProxyObject)function;
        if (!isCallable.executeBoolean(JSProxy.getTarget(proxy))) {
            this.errorBranch.enter();
            throw Errors.createTypeErrorNotAFunction(function, this);
        }
        JSDynamicObject pxHandler = JSProxy.getHandlerChecked(proxy, this.errorBranch);
        Object pxTarget = JSProxy.getTarget(proxy);
        Object pxTrapFun = this.trapGetter.executeWithTarget(pxHandler);
        Object[] proxyArguments = JSArguments.extractUserArguments(arguments);
        if (this.pxTrapFunProfile.profile(pxTrapFun == Undefined.instance)) {
            return this.callNode.executeCall(JSArguments.create(thisObj, pxTarget, proxyArguments));
        }
        Object[] trapArgs = new Object[]{pxTarget, thisObj, JSArray.createConstant(this.context, this.getRealm(), proxyArguments)};
        return this.callTrapNode.executeCall(JSArguments.create(pxHandler, pxTrapFun, trapArgs));
    }

    @Specialization(guards={"isNew || isNewTarget"})
    protected Object doConstruct(Object[] arguments, @Cached IsConstructorNode isConstructor) {
        Object function = JSArguments.getFunctionObject(arguments);
        JSProxyObject proxy = (JSProxyObject)function;
        if (!isConstructor.executeBoolean(JSProxy.getTarget(proxy))) {
            this.errorBranch.enter();
            throw Errors.createTypeErrorNotAFunction(function, this);
        }
        JSDynamicObject pxHandler = JSProxy.getHandlerChecked(proxy, this.errorBranch);
        Object pxTarget = JSProxy.getTarget(proxy);
        Object pxTrapFun = this.trapGetter.executeWithTarget(pxHandler);
        Object newTarget = this.isNewTarget ? JSArguments.getNewTarget(arguments) : proxy;
        Object[] constructorArguments = JSArguments.extractUserArguments(arguments, this.isNewTarget ? 1 : 0);
        if (this.pxTrapFunProfile.profile(pxTrapFun == Undefined.instance)) {
            if (JSDynamicObject.isJSDynamicObject(pxTarget)) {
                return this.callNode.executeCall(JSArguments.createWithNewTarget(JSFunction.CONSTRUCT, pxTarget, newTarget, constructorArguments));
            }
            return JSInteropUtil.construct(pxTarget, constructorArguments);
        }
        Object[] trapArgs = new Object[]{pxTarget, JSArray.createConstant(this.context, this.getRealm(), constructorArguments), newTarget};
        Object result = this.callTrapNode.executeCall(JSArguments.create(pxHandler, pxTrapFun, trapArgs));
        if (!JSRuntime.isObject(result)) {
            this.errorBranch.enter();
            throw Errors.createTypeErrorNotAnObject(result, this);
        }
        return result;
    }
}

