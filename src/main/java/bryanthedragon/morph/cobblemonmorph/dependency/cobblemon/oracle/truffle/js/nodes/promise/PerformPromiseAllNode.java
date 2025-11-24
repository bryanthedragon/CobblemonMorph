
package com.oracle.truffle.js.nodes.promise;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleStackTraceElement;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.arguments.AccessIndexedArgumentNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.promise.AsyncHandlerRootNode;
import com.oracle.truffle.js.nodes.promise.PerformPromiseCombinatorNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayObject;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSPromise;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;

public class PerformPromiseAllNode
extends PerformPromiseCombinatorNode {
    static final HiddenKey RESOLVE_ELEMENT_ARGS_KEY = new HiddenKey("ResolveElementArgs");
    @Node.Child
    protected JSFunctionCallNode callResolve;
    @Node.Child
    protected PropertyGetNode getThen;
    @Node.Child
    protected JSFunctionCallNode callThen;
    @Node.Child
    protected PropertySetNode setArgs;
    private final BranchProfile growProfile = BranchProfile.create();

    protected PerformPromiseAllNode(JSContext context) {
        super(context);
        this.callResolve = JSFunctionCallNode.createCall();
        this.getThen = PropertyGetNode.create(JSPromise.THEN, false, context);
        this.callThen = JSFunctionCallNode.createCall();
        this.setArgs = PropertySetNode.createSetHidden(RESOLVE_ELEMENT_ARGS_KEY, context);
    }

    public static PerformPromiseAllNode create(JSContext context) {
        return new PerformPromiseAllNode(context);
    }

    @Override
    public JSDynamicObject execute(IteratorRecord iteratorRecord, JSDynamicObject constructor, PromiseCapabilityRecord resultCapability, Object promiseResolve) {
        assert (JSRuntime.isConstructor(constructor));
        assert (JSRuntime.isCallable(promiseResolve));
        SimpleArrayList<Object> values = new SimpleArrayList<Object>(10);
        PerformPromiseCombinatorNode.BoxedInt remainingElementsCount = new PerformPromiseCombinatorNode.BoxedInt(1);
        int index = 0;
        while (true) {
            Object next;
            if ((next = this.iteratorStepOrSetDone(iteratorRecord)) == Boolean.FALSE) {
                iteratorRecord.setDone(true);
                --remainingElementsCount.value;
                if (remainingElementsCount.value == 0) {
                    JSArrayObject valuesArray = JSArray.createConstantObjectArray(this.context, this.getRealm(), values.toArray());
                    this.callResolve.executeCall(JSArguments.createOneArg(Undefined.instance, resultCapability.getResolve(), valuesArray));
                }
                return resultCapability.getPromise();
            }
            Object nextValue = this.iteratorValueOrSetDone(iteratorRecord, next);
            values.add(Undefined.instance, this.growProfile);
            Object nextPromise = this.callResolve.executeCall(JSArguments.createOneArg(constructor, promiseResolve, nextValue));
            JSFunctionObject resolveElement = this.createResolveElementFunction(index, values, resultCapability, remainingElementsCount);
            Object rejectElement = this.createRejectElementFunction(index, values, resultCapability, remainingElementsCount);
            ++remainingElementsCount.value;
            this.callThen.executeCall(JSArguments.create(nextPromise, this.getThen.getValue(nextPromise), resolveElement, rejectElement));
            ++index;
        }
    }

    protected JSFunctionObject createResolveElementFunction(int index, SimpleArrayList<Object> values, PromiseCapabilityRecord resultCapability, PerformPromiseCombinatorNode.BoxedInt remainingElementsCount) {
        JSFunctionData functionData = this.context.getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.PromiseAllResolveElement, c -> PerformPromiseAllNode.createResolveElementFunctionImpl(c));
        JSFunctionObject function = JSFunction.create(this.getRealm(), functionData);
        this.setArgs.setValue(function, new ResolveElementArgs(index, values, resultCapability, remainingElementsCount));
        return function;
    }

    protected Object createRejectElementFunction(int index, SimpleArrayList<Object> values, PromiseCapabilityRecord resultCapability, PerformPromiseCombinatorNode.BoxedInt remainingElementsCount) {
        return resultCapability.getReject();
    }

    private static JSFunctionData createResolveElementFunctionImpl(JSContext context) {
        class PromiseAllResolveElementRootNode
        extends JavaScriptRootNode
        implements AsyncHandlerRootNode {
            @Node.Child
            private JavaScriptNode valueNode = AccessIndexedArgumentNode.create(0);
            @Node.Child
            private PropertyGetNode getArgs = PropertyGetNode.createGetHidden(RESOLVE_ELEMENT_ARGS_KEY, this.val$context);
            @Node.Child
            private JSFunctionCallNode callResolve = JSFunctionCallNode.createCall();
            final /* synthetic */ JSContext val$context;

            PromiseAllResolveElementRootNode(JSContext val$context) {
                this.val$context = val$context;
            }

            @Override
            public Object execute(VirtualFrame frame) {
                JSFunctionObject functionObject = JSFrameUtil.getFunctionObject(frame);
                ResolveElementArgs args = (ResolveElementArgs)this.getArgs.getValue(functionObject);
                if (args.alreadyCalled) {
                    return Undefined.instance;
                }
                args.alreadyCalled = true;
                Object value2 = this.valueNode.execute(frame);
                args.values.set(args.index, value2);
                --args.remainingElements.value;
                if (args.remainingElements.value == 0) {
                    JSArrayObject valuesArray = JSArray.createConstantObjectArray(this.val$context, this.getRealm(), args.values.toArray());
                    return this.callResolve.executeCall(JSArguments.createOneArg(Undefined.instance, args.capability.getResolve(), valuesArray));
                }
                return Undefined.instance;
            }

            @Override
            public AsyncHandlerRootNode.AsyncStackTraceInfo getAsyncStackTraceInfo(JSFunctionObject handlerFunction) {
                assert (JSFunction.isJSFunction(handlerFunction) && ((RootCallTarget)JSFunction.getFunctionData(handlerFunction).getCallTarget()).getRootNode() == this);
                ResolveElementArgs resolveArgs = (ResolveElementArgs)JSObjectUtil.getHiddenProperty(handlerFunction, RESOLVE_ELEMENT_ARGS_KEY);
                int promiseIndex = resolveArgs.index;
                JSRealm realm = JSFunction.getRealm(handlerFunction);
                TruffleStackTraceElement asyncStackTraceElement = PerformPromiseAllNode.createPromiseAllStackTraceElement(promiseIndex, realm);
                JSDynamicObject resultPromise = resolveArgs.capability.getPromise();
                return new AsyncHandlerRootNode.AsyncStackTraceInfo(resultPromise, asyncStackTraceElement);
            }
        }
        return JSFunctionData.createCallOnly(context, new PromiseAllResolveElementRootNode(context).getCallTarget(), 1, Strings.EMPTY_STRING);
    }

    static TruffleStackTraceElement createPromiseAllStackTraceElement(int promiseIndex, JSRealm realm) {
        return TruffleStackTraceElement.create(new PromiseAllMarkerRootNode(null, JSBuiltin.createSourceSection()), (RootCallTarget)JSFunction.getFunctionData(realm.getPromiseAllFunctionObject()).getCallTarget(), Truffle.getRuntime().createMaterializedFrame(JSArguments.createOneArg(realm.getPromiseConstructor(), realm.getPromiseAllFunctionObject(), promiseIndex)));
    }

    public static final class PromiseAllMarkerRootNode
    extends JavaScriptRootNode {
        PromiseAllMarkerRootNode(JavaScriptLanguage lang, SourceSection sourceSection) {
            super(lang, sourceSection, null);
        }

        @Override
        public Object execute(VirtualFrame frame) {
            throw Errors.shouldNotReachHere();
        }

        @Override
        public boolean isInternal() {
            return false;
        }

        @Override
        protected boolean isInstrumentable() {
            return false;
        }

        @Override
        public String getName() {
            return "Promise.all";
        }
    }

    public static final class ResolveElementArgs {
        public final int index;
        public final PromiseCapabilityRecord capability;
        boolean alreadyCalled = false;
        final SimpleArrayList<Object> values;
        final PerformPromiseCombinatorNode.BoxedInt remainingElements;

        ResolveElementArgs(int index, SimpleArrayList<Object> values, PromiseCapabilityRecord capability, PerformPromiseCombinatorNode.BoxedInt remainingElements) {
            this.index = index;
            this.values = values;
            this.capability = capability;
            this.remainingElements = remainingElements;
        }
    }
}

