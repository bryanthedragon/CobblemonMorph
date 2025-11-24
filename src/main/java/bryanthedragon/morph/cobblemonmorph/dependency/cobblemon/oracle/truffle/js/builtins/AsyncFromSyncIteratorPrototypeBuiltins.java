
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.builtins.AsyncFromSyncIteratorPrototypeBuiltinsFactory;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.CreateIterResultObjectNode;
import com.oracle.truffle.js.nodes.access.GetMethodNode;
import com.oracle.truffle.js.nodes.access.IteratorCompleteNode;
import com.oracle.truffle.js.nodes.access.IteratorNextNode;
import com.oracle.truffle.js.nodes.access.IteratorValueNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.arguments.AccessIndexedArgumentNode;
import com.oracle.truffle.js.nodes.control.TryCatchNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.promise.NewPromiseCapabilityNode;
import com.oracle.truffle.js.nodes.promise.PerformPromiseThenNode;
import com.oracle.truffle.js.nodes.promise.PromiseResolveNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class AsyncFromSyncIteratorPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<GeneratorPrototype> {
    public static final JSBuiltinsContainer BUILTINS = new AsyncFromSyncIteratorPrototypeBuiltins();

    protected AsyncFromSyncIteratorPrototypeBuiltins() {
        super(JSFunction.ASYNC_FROM_SYNC_ITERATOR_PROTOTYPE_NAME, GeneratorPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, GeneratorPrototype builtinEnum) {
        assert (context.getEcmaScriptVersion() >= 8);
        switch (builtinEnum) {
            case next: {
                return AsyncFromSyncIteratorPrototypeBuiltinsFactory.AsyncFromSyncNextNodeGen.create(context, builtin, AsyncFromSyncIteratorPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case return_: {
                return AsyncFromSyncIteratorPrototypeBuiltinsFactory.AsyncFromSyncReturnNodeGen.create(context, builtin, AsyncFromSyncIteratorPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case throw_: {
                return AsyncFromSyncIteratorPrototypeBuiltinsFactory.AsyncFromSyncThrowNodeGen.create(context, builtin, AsyncFromSyncIteratorPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class AsyncFromSyncThrow
    extends AsyncFromSyncMethod {
        @Node.Child
        private GetMethodNode getThrow;

        public AsyncFromSyncThrow(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.getThrow = GetMethodNode.create(context, Strings.THROW);
        }

        @Override
        protected GetMethodNode getMethod() {
            return this.getThrow;
        }

        @Override
        protected Object processUndefinedMethod(VirtualFrame frame, PromiseCapabilityRecord promiseCapability, Object value2) {
            this.promiseCapabilityRejectImpl(promiseCapability, value2);
            return promiseCapability.getPromise();
        }

        @Specialization(guards={"isObject(thisObj)"})
        protected Object doThrow(VirtualFrame frame, JSDynamicObject thisObj, Object value2) {
            return this.doMethod(frame, thisObj, value2);
        }
    }

    public static abstract class AsyncFromSyncReturn
    extends AsyncFromSyncMethod {
        @Node.Child
        private GetMethodNode getReturn;
        @Node.Child
        private CreateIterResultObjectNode createIterResult;

        public AsyncFromSyncReturn(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.getReturn = GetMethodNode.create(context, Strings.RETURN);
            this.createIterResult = CreateIterResultObjectNode.create(this.getContext());
        }

        @Override
        protected GetMethodNode getMethod() {
            return this.getReturn;
        }

        @Override
        protected Object processUndefinedMethod(VirtualFrame frame, PromiseCapabilityRecord promiseCapability, Object value2) {
            JSDynamicObject iterResult = this.createIterResult.execute(frame, value2, true);
            this.promiseCapabilityResolve(promiseCapability, iterResult);
            return promiseCapability.getPromise();
        }

        @Specialization(guards={"isObject(thisObj)"})
        protected Object resume(VirtualFrame frame, JSDynamicObject thisObj, Object value2) {
            return this.doMethod(frame, thisObj, value2);
        }
    }

    public static abstract class AsyncFromSyncMethod
    extends AsyncFromSyncBaseNode {
        @Node.Child
        private JSFunctionCallNode executeReturnMethod = JSFunctionCallNode.createCall();

        public AsyncFromSyncMethod(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        protected abstract GetMethodNode getMethod();

        protected abstract Object processUndefinedMethod(VirtualFrame var1, PromiseCapabilityRecord var2, Object var3);

        protected Object doMethod(VirtualFrame frame, JSDynamicObject thisObj, Object value2) {
            Object returnResult;
            PromiseCapabilityRecord promiseCapability = this.createPromiseCapability();
            if (!this.isAsyncFromSyncIterator(thisObj)) {
                JSException typeError = Errors.createTypeErrorIncompatibleReceiver(thisObj);
                this.promiseCapabilityReject(promiseCapability, typeError);
                return promiseCapability.getPromise();
            }
            IteratorRecord syncIteratorRecord = (IteratorRecord)this.getSyncIteratorRecordNode.getValue(thisObj);
            JSDynamicObject syncIterator = syncIteratorRecord.getIterator();
            Object method = this.getMethod().executeWithTarget(syncIterator);
            if (method == Undefined.instance) {
                return this.processUndefinedMethod(frame, promiseCapability, value2);
            }
            try {
                returnResult = this.valuePresenceProfile.profile(JSArguments.getUserArgumentCount(frame.getArguments()) == 0) ? this.executeReturnMethod.executeCall(JSArguments.create(syncIterator, method, new Object[0])) : this.executeReturnMethod.executeCall(JSArguments.create(syncIterator, method, value2));
            }
            catch (AbstractTruffleException e) {
                this.promiseCapabilityReject(promiseCapability, e);
                return promiseCapability.getPromise();
            }
            if (!JSDynamicObject.isJSDynamicObject(returnResult)) {
                this.promiseCapabilityReject(promiseCapability, Errors.createTypeErrorNotAnObject(returnResult));
                return promiseCapability.getPromise();
            }
            return this.asyncFromSyncIteratorContinuation(returnResult, promiseCapability);
        }
    }

    public static abstract class AsyncFromSyncNext
    extends AsyncFromSyncBaseNode {
        public AsyncFromSyncNext(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isObject(thisObj)"})
        protected Object next(VirtualFrame frame, JSDynamicObject thisObj, Object value2) {
            Object nextResult;
            PromiseCapabilityRecord promiseCapability = this.createPromiseCapability();
            if (!this.isAsyncFromSyncIterator(thisObj)) {
                JSException typeError = Errors.createTypeErrorIncompatibleReceiver(thisObj);
                this.promiseCapabilityReject(promiseCapability, typeError);
                return promiseCapability.getPromise();
            }
            IteratorRecord syncIteratorRecord = (IteratorRecord)this.getSyncIteratorRecordNode.getValue(thisObj);
            try {
                nextResult = this.valuePresenceProfile.profile(JSArguments.getUserArgumentCount(frame.getArguments()) == 0) ? this.iteratorNextNode.execute(syncIteratorRecord) : this.iteratorNextNode.execute(syncIteratorRecord, value2);
            }
            catch (AbstractTruffleException e) {
                this.promiseCapabilityReject(promiseCapability, e);
                return promiseCapability.getPromise();
            }
            return this.asyncFromSyncIteratorContinuation(nextResult, promiseCapability);
        }
    }

    @ImportStatic(value={JSRuntime.class})
    private static abstract class AsyncFromSyncBaseNode
    extends JSBuiltinNode {
        static final HiddenKey DONE = new HiddenKey("Done");
        @Node.Child
        private JSFunctionCallNode executePromiseMethodNode;
        @Node.Child
        private NewPromiseCapabilityNode newPromiseCapabilityNode;
        @Node.Child
        protected PerformPromiseThenNode performPromiseThenNode;
        @Node.Child
        private PromiseResolveNode promiseResolveNode;
        @Node.Child
        protected IteratorNextNode iteratorNextNode;
        @Node.Child
        protected IteratorValueNode iteratorValueNode;
        @Node.Child
        protected IteratorCompleteNode iteratorCompleteNode;
        @Node.Child
        protected PropertyGetNode getSyncIteratorRecordNode;
        @Node.Child
        private PropertySetNode setDoneNode;
        @Node.Child
        private TryCatchNode.GetErrorObjectNode getErrorObjectNode;
        protected ConditionProfile valuePresenceProfile = ConditionProfile.createBinaryProfile();

        AsyncFromSyncBaseNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.newPromiseCapabilityNode = NewPromiseCapabilityNode.create(context);
            this.executePromiseMethodNode = JSFunctionCallNode.createCall();
            this.iteratorNextNode = IteratorNextNode.create();
            this.iteratorCompleteNode = IteratorCompleteNode.create(context);
            this.iteratorValueNode = IteratorValueNode.create();
            this.getSyncIteratorRecordNode = PropertyGetNode.createGetHidden(JSFunction.ASYNC_FROM_SYNC_ITERATOR_KEY, context);
            this.setDoneNode = PropertySetNode.createSetHidden(DONE, context);
            this.performPromiseThenNode = PerformPromiseThenNode.create(context);
            this.promiseResolveNode = PromiseResolveNode.create(context);
        }

        protected PromiseCapabilityRecord createPromiseCapability() {
            return this.newPromiseCapabilityNode.executeDefault();
        }

        protected boolean isAsyncFromSyncIterator(JSDynamicObject thiz) {
            return thiz != Undefined.instance && this.getSyncIteratorRecordNode.getValue(thiz) != Undefined.instance;
        }

        protected void promiseCapabilityReject(PromiseCapabilityRecord promiseCapability, AbstractTruffleException exception) {
            if (this.getErrorObjectNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getErrorObjectNode = this.insert(TryCatchNode.GetErrorObjectNode.create(this.getContext()));
            }
            Object result = this.getErrorObjectNode.execute(exception);
            this.promiseCapabilityRejectImpl(promiseCapability, result);
        }

        protected void promiseCapabilityRejectImpl(PromiseCapabilityRecord promiseCapability, Object result) {
            this.executePromiseMethodNode.executeCall(JSArguments.createOneArg(Undefined.instance, promiseCapability.getReject(), result));
        }

        protected void promiseCapabilityResolve(PromiseCapabilityRecord valueWrapperCapability, Object result) {
            this.executePromiseMethodNode.executeCall(JSArguments.createOneArg(Undefined.instance, valueWrapperCapability.getResolve(), result));
        }

        protected final Object asyncFromSyncIteratorContinuation(Object result, PromiseCapabilityRecord promiseCapability) {
            JSDynamicObject valueWrapper;
            Object returnValue;
            boolean done;
            try {
                done = this.iteratorCompleteNode.execute(result);
            }
            catch (AbstractTruffleException e) {
                this.promiseCapabilityReject(promiseCapability, e);
                return promiseCapability.getPromise();
            }
            try {
                returnValue = this.iteratorValueNode.execute(result);
            }
            catch (AbstractTruffleException e) {
                this.promiseCapabilityReject(promiseCapability, e);
                return promiseCapability.getPromise();
            }
            JSRealm realm = this.getRealm();
            if (this.getContext().usePromiseResolve()) {
                valueWrapper = this.promiseResolveNode.execute(realm.getPromiseConstructor(), returnValue);
            } else {
                PromiseCapabilityRecord valueWrapperCapability = this.createPromiseCapability();
                this.promiseCapabilityResolve(valueWrapperCapability, returnValue);
                valueWrapper = valueWrapperCapability.getPromise();
            }
            JSFunctionObject onFulfilled = this.createIteratorValueUnwrapFunction(realm, done);
            this.performPromiseThenNode.execute(valueWrapper, onFulfilled, Undefined.instance, promiseCapability);
            return promiseCapability.getPromise();
        }

        protected final JSFunctionObject createIteratorValueUnwrapFunction(JSRealm realm, boolean done) {
            JSContext context = realm.getContext();
            JSFunctionData functionData = context.getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.AsyncFromSyncIteratorValueUnwrap, c -> AsyncFromSyncBaseNode.createIteratorValueUnwrapImpl(c));
            JSFunctionObject function = JSFunction.create(realm, functionData);
            this.setDoneNode.setValueBoolean(function, done);
            return function;
        }

        private static JSFunctionData createIteratorValueUnwrapImpl(JSContext context) {
            class AsyncFromSyncIteratorValueUnwrapRootNode
            extends JavaScriptRootNode {
                @Node.Child
                private JavaScriptNode valueNode = AccessIndexedArgumentNode.create(0);
                @Node.Child
                private PropertyGetNode isDoneNode = PropertyGetNode.createGetHidden(DONE, this.val$context);
                @Node.Child
                private CreateIterResultObjectNode createIterResult = CreateIterResultObjectNode.create(this.val$context);
                final /* synthetic */ JSContext val$context;

                AsyncFromSyncIteratorValueUnwrapRootNode(JSContext val$context) {
                    this.val$context = val$context;
                }

                @Override
                public Object execute(VirtualFrame frame) {
                    boolean done;
                    JSFunctionObject functionObject = JSFrameUtil.getFunctionObject(frame);
                    Object value2 = this.valueNode.execute(frame);
                    try {
                        done = this.isDoneNode.getValueBoolean(functionObject);
                    }
                    catch (UnexpectedResultException e) {
                        throw Errors.shouldNotReachHere();
                    }
                    return this.createIterResult.execute(frame, value2, done);
                }
            }
            return JSFunctionData.createCallOnly(context, new AsyncFromSyncIteratorValueUnwrapRootNode(context).getCallTarget(), 1, Strings.EMPTY_STRING);
        }
    }

    public static enum GeneratorPrototype implements BuiltinEnum<GeneratorPrototype>
    {
        next(1),
        return_(1),
        throw_(1);

        private final int length;

        private GeneratorPrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

