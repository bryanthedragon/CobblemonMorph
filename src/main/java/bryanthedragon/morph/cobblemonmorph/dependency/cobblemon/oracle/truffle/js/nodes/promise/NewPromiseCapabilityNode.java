
package com.oracle.truffle.js.nodes.promise;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.arguments.AccessIndexedArgumentNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.nodes.unary.IsConstructorNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;
import com.oracle.truffle.js.runtime.objects.Undefined;

public class NewPromiseCapabilityNode
extends JavaScriptBaseNode {
    public static final HiddenKey PROMISE_CAPABILITY_KEY = new HiddenKey("PromiseCapability");
    private final JSContext context;
    @Node.Child
    private IsConstructorNode isConstructor;
    @Node.Child
    private JSFunctionCallNode newPromise;
    @Node.Child
    private IsCallableNode isCallable;
    @Node.Child
    private PropertySetNode setPromiseCapability;
    private final BranchProfile errorBranch = BranchProfile.create();

    protected NewPromiseCapabilityNode(JSContext context) {
        this.context = context;
        this.isConstructor = IsConstructorNode.create();
        this.newPromise = JSFunctionCallNode.createNew();
        this.isCallable = IsCallableNode.create();
        this.setPromiseCapability = PropertySetNode.createSetHidden(PROMISE_CAPABILITY_KEY, context);
    }

    public static NewPromiseCapabilityNode create(JSContext context) {
        return new NewPromiseCapabilityNode(context);
    }

    public PromiseCapabilityRecord executeDefault() {
        return this.execute(this.getRealm().getPromiseConstructor());
    }

    @CompilerDirectives.TruffleBoundary
    public static PromiseCapabilityRecord createDefault(JSRealm realm) {
        JSFunctionObject constructor = realm.getPromiseConstructor();
        JSContext context = realm.getContext();
        assert (JSFunction.isConstructor(constructor));
        PromiseCapabilityRecord promiseCapability = PromiseCapabilityRecord.create(Undefined.instance, Undefined.instance, Undefined.instance);
        JSDynamicObject executor = NewPromiseCapabilityNode.getCapabilitiesExecutor(context, realm, promiseCapability);
        JSDynamicObject promise = (JSDynamicObject)JSFunction.construct(constructor, new Object[]{executor});
        assert (JSFunction.isJSFunction(promiseCapability.getResolve()) && JSFunction.isJSFunction(promiseCapability.getReject()));
        promiseCapability.setPromise(promise);
        return promiseCapability;
    }

    public PromiseCapabilityRecord execute(JSDynamicObject constructor) {
        if (!this.isConstructor.executeBoolean(constructor)) {
            this.errorBranch.enter();
            throw Errors.createTypeErrorNotAConstructor(constructor, this.context);
        }
        PromiseCapabilityRecord promiseCapability = PromiseCapabilityRecord.create(Undefined.instance, Undefined.instance, Undefined.instance);
        JSDynamicObject executor = this.getCapabilitiesExecutor(promiseCapability);
        JSDynamicObject promise = (JSDynamicObject)this.newPromise.executeCall(JSArguments.create(Undefined.instance, constructor, executor));
        if (!this.isCallable.executeBoolean(promiseCapability.getResolve()) || !this.isCallable.executeBoolean(promiseCapability.getReject())) {
            this.errorBranch.enter();
            throw Errors.createTypeError("cannot create promise");
        }
        promiseCapability.setPromise(promise);
        return promiseCapability;
    }

    private JSDynamicObject getCapabilitiesExecutor(PromiseCapabilityRecord promiseCapability) {
        JSFunctionData functionData = this.context.getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.PromiseGetCapabilitiesExecutor, c -> NewPromiseCapabilityNode.createGetCapabilitiesExecutorImpl(c));
        JSFunctionObject function = JSFunction.create(this.getRealm(), functionData);
        this.setPromiseCapability.setValue(function, promiseCapability);
        return function;
    }

    private static JSDynamicObject getCapabilitiesExecutor(JSContext context, JSRealm realm, PromiseCapabilityRecord promiseCapability) {
        JSFunctionData functionData = context.getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.PromiseGetCapabilitiesExecutor, c -> NewPromiseCapabilityNode.createGetCapabilitiesExecutorImpl(c));
        JSFunctionObject function = JSFunction.create(realm, functionData);
        JSObjectUtil.putHiddenProperty(function, PROMISE_CAPABILITY_KEY, promiseCapability);
        return function;
    }

    private static JSFunctionData createGetCapabilitiesExecutorImpl(JSContext context) {
        class GetCapabilitiesExecutorNode
        extends JavaScriptRootNode {
            @Node.Child
            private JavaScriptNode resolveNode = AccessIndexedArgumentNode.create(0);
            @Node.Child
            private JavaScriptNode rejectNode = AccessIndexedArgumentNode.create(1);
            @Node.Child
            private PropertyGetNode getPromiseCapability = PropertyGetNode.createGetHidden(PROMISE_CAPABILITY_KEY, this.val$context);
            private final BranchProfile errorBranch = BranchProfile.create();
            final /* synthetic */ JSContext val$context;

            GetCapabilitiesExecutorNode(JSContext val$context) {
                this.val$context = val$context;
            }

            @Override
            public Object execute(VirtualFrame frame) {
                JSFunctionObject functionObject = JSFrameUtil.getFunctionObject(frame);
                PromiseCapabilityRecord capability = (PromiseCapabilityRecord)this.getPromiseCapability.getValue(functionObject);
                if (capability.getResolve() != Undefined.instance || capability.getReject() != Undefined.instance) {
                    this.errorBranch.enter();
                    throw Errors.createTypeError("error while creating capability!");
                }
                Object resolve = this.resolveNode.execute(frame);
                Object reject = this.rejectNode.execute(frame);
                capability.setResolve(resolve);
                capability.setReject(reject);
                return Undefined.instance;
            }
        }
        return JSFunctionData.createCallOnly(context, new GetCapabilitiesExecutorNode(context).getCallTarget(), 2, Strings.EMPTY_STRING);
    }
}

