
package com.oracle.truffle.js.nodes.promise;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.CreateDataPropertyNode;
import com.oracle.truffle.js.nodes.access.CreateObjectNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.arguments.AccessIndexedArgumentNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.promise.PerformPromiseAllNode;
import com.oracle.truffle.js.nodes.promise.PerformPromiseCombinatorNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayObject;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;

public class PerformPromiseAllSettledNode
extends PerformPromiseAllNode {
    protected PerformPromiseAllSettledNode(JSContext context) {
        super(context);
    }

    public static PerformPromiseAllSettledNode create(JSContext context) {
        return new PerformPromiseAllSettledNode(context);
    }

    @Override
    protected JSFunctionObject createResolveElementFunction(int index, SimpleArrayList<Object> values, PromiseCapabilityRecord resultCapability, PerformPromiseCombinatorNode.BoxedInt remainingElementsCount) {
        JSFunctionData functionData = this.context.getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.PromiseAllSettledResolveElement, c -> PerformPromiseAllSettledNode.createResolveElementFunctionImpl(c));
        JSFunctionObject function = JSFunction.create(this.getRealm(), functionData);
        this.setArgs.setValue(function, new PerformPromiseAllNode.ResolveElementArgs(index, values, resultCapability, remainingElementsCount));
        return function;
    }

    @Override
    protected Object createRejectElementFunction(int index, SimpleArrayList<Object> values, PromiseCapabilityRecord resultCapability, PerformPromiseCombinatorNode.BoxedInt remainingElementsCount) {
        JSFunctionData functionData = this.context.getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.PromiseAllSettledRejectElement, c -> PerformPromiseAllSettledNode.createRejectElementFunctionImpl(c));
        JSFunctionObject function = JSFunction.create(this.getRealm(), functionData);
        this.setArgs.setValue(function, new PerformPromiseAllNode.ResolveElementArgs(index, values, resultCapability, remainingElementsCount));
        return function;
    }

    private static JSFunctionData createResolveElementFunctionImpl(JSContext context) {
        class PromiseAllSettledResolveElementRootNode
        extends JavaScriptRootNode {
            @Node.Child
            private JavaScriptNode valueNode = AccessIndexedArgumentNode.create(0);
            @Node.Child
            private PropertyGetNode getArgs = PropertyGetNode.createGetHidden(PerformPromiseAllNode.RESOLVE_ELEMENT_ARGS_KEY, this.val$context);
            @Node.Child
            private JSFunctionCallNode callResolve = JSFunctionCallNode.createCall();
            @Node.Child
            private CreateObjectNode objectCreateNode = CreateObjectNode.create(this.val$context);
            @Node.Child
            private CreateDataPropertyNode createStatusPropertyNode = CreateDataPropertyNode.create(this.val$context, Strings.STATUS);
            @Node.Child
            private CreateDataPropertyNode createValuePropertyNode = CreateDataPropertyNode.create(this.val$context, Strings.VALUE);
            final /* synthetic */ JSContext val$context;

            PromiseAllSettledResolveElementRootNode(JSContext val$context) {
                this.val$context = val$context;
            }

            @Override
            public Object execute(VirtualFrame frame) {
                JSFunctionObject functionObject = JSFrameUtil.getFunctionObject(frame);
                PerformPromiseAllNode.ResolveElementArgs args = (PerformPromiseAllNode.ResolveElementArgs)this.getArgs.getValue(functionObject);
                if (args.alreadyCalled) {
                    return Undefined.instance;
                }
                args.alreadyCalled = true;
                Object value2 = this.valueNode.execute(frame);
                JSDynamicObject obj = this.objectCreateNode.execute(frame);
                this.createStatusPropertyNode.executeVoid(obj, Strings.FULFILLED);
                this.createValuePropertyNode.executeVoid(obj, value2);
                args.values.set(args.index, obj);
                --args.remainingElements.value;
                if (args.remainingElements.value == 0) {
                    JSArrayObject valuesArray = JSArray.createConstantObjectArray(this.val$context, this.getRealm(), args.values.toArray());
                    return this.callResolve.executeCall(JSArguments.createOneArg(Undefined.instance, args.capability.getResolve(), valuesArray));
                }
                return Undefined.instance;
            }
        }
        return JSFunctionData.createCallOnly(context, new PromiseAllSettledResolveElementRootNode(context).getCallTarget(), 1, Strings.EMPTY_STRING);
    }

    private static JSFunctionData createRejectElementFunctionImpl(JSContext context) {
        class PromiseAllSettledRejectElementRootNode
        extends JavaScriptRootNode {
            @Node.Child
            private JavaScriptNode valueNode = AccessIndexedArgumentNode.create(0);
            @Node.Child
            private PropertyGetNode getArgs = PropertyGetNode.createGetHidden(PerformPromiseAllNode.RESOLVE_ELEMENT_ARGS_KEY, this.val$context);
            @Node.Child
            private JSFunctionCallNode callResolve = JSFunctionCallNode.createCall();
            @Node.Child
            private CreateObjectNode objectCreateNode = CreateObjectNode.create(this.val$context);
            @Node.Child
            private CreateDataPropertyNode createStatusPropertyNode = CreateDataPropertyNode.create(this.val$context, Strings.STATUS);
            @Node.Child
            private CreateDataPropertyNode createReasonPropertyNode = CreateDataPropertyNode.create(this.val$context, Strings.REASON);
            final /* synthetic */ JSContext val$context;

            PromiseAllSettledRejectElementRootNode(JSContext val$context) {
                this.val$context = val$context;
            }

            @Override
            public Object execute(VirtualFrame frame) {
                JSFunctionObject functionObject = JSFrameUtil.getFunctionObject(frame);
                PerformPromiseAllNode.ResolveElementArgs args = (PerformPromiseAllNode.ResolveElementArgs)this.getArgs.getValue(functionObject);
                if (args.alreadyCalled) {
                    return Undefined.instance;
                }
                args.alreadyCalled = true;
                Object value2 = this.valueNode.execute(frame);
                JSDynamicObject obj = this.objectCreateNode.execute(frame);
                this.createStatusPropertyNode.executeVoid(obj, Strings.REJECTED);
                this.createReasonPropertyNode.executeVoid(obj, value2);
                args.values.set(args.index, obj);
                --args.remainingElements.value;
                if (args.remainingElements.value == 0) {
                    JSArrayObject valuesArray = JSArray.createConstantObjectArray(this.val$context, this.getRealm(), args.values.toArray());
                    return this.callResolve.executeCall(JSArguments.createOneArg(Undefined.instance, args.capability.getResolve(), valuesArray));
                }
                return Undefined.instance;
            }
        }
        return JSFunctionData.createCallOnly(context, new PromiseAllSettledRejectElementRootNode(context).getCallTarget(), 1, Strings.EMPTY_STRING);
    }
}

