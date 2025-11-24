
package com.oracle.truffle.js.nodes.promise;

import com.oracle.js.parser.ir.Module;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.arguments.AccessIndexedArgumentNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.control.TryCatchNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.promise.NewPromiseCapabilityNode;
import com.oracle.truffle.js.nodes.promise.PerformPromiseThenNode;
import com.oracle.truffle.js.nodes.promise.PromiseReactionJobNode;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSPromise;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSModuleRecord;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;
import com.oracle.truffle.js.runtime.objects.PromiseReactionRecord;
import com.oracle.truffle.js.runtime.objects.ScriptOrModule;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.Pair;
import com.oracle.truffle.js.runtime.util.Triple;
import com.oracle.truffle.js.runtime.util.UnmodifiableArrayList;
import java.util.Map;
import java.util.Set;

public class ImportCallNode
extends JavaScriptNode {
    private static final HiddenKey CURRENT_MODULE_RECORD_KEY = new HiddenKey("%currentModuleRecord");
    private static final TruffleString ASSERTIONS = Strings.constant("assert");
    @Node.Child
    private JavaScriptNode argRefNode;
    @Node.Child
    private JavaScriptNode activeScriptOrModuleNode;
    @Node.Child
    private NewPromiseCapabilityNode newPromiseCapabilityNode;
    @Node.Child
    private JSToStringNode toStringNode;
    @Node.Child
    private PromiseReactionJobNode promiseReactionJobNode;
    @Node.Child
    private JavaScriptNode optionsRefNode;
    @Node.Child
    private JSFunctionCallNode callRejectNode;
    @Node.Child
    private TryCatchNode.GetErrorObjectNode getErrorObjectNode;
    @Node.Child
    private EnumerableOwnPropertyNamesNode enumerableOwnPropertyNamesNode;
    @Node.Child
    private PropertyGetNode getAssertionsNode;
    private final JSContext context;

    protected ImportCallNode(JSContext context, JavaScriptNode argRefNode, JavaScriptNode activeScriptOrModuleNode, JavaScriptNode optionsRefNode) {
        this.context = context;
        this.argRefNode = argRefNode;
        this.activeScriptOrModuleNode = activeScriptOrModuleNode;
        this.optionsRefNode = optionsRefNode;
        this.newPromiseCapabilityNode = NewPromiseCapabilityNode.create(context);
        this.toStringNode = JSToStringNode.create();
        this.promiseReactionJobNode = PromiseReactionJobNode.create(context);
    }

    public static ImportCallNode create(JSContext context, JavaScriptNode argRefNode, JavaScriptNode activeScriptOrModuleNode) {
        return new ImportCallNode(context, argRefNode, activeScriptOrModuleNode, null);
    }

    public static ImportCallNode createWithOptions(JSContext context, JavaScriptNode specifierRefNode, JavaScriptNode activeScriptOrModuleNode, JavaScriptNode optionsRefNode) {
        return new ImportCallNode(context, specifierRefNode, activeScriptOrModuleNode, optionsRefNode);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object referencingScriptOrModule = this.getActiveScriptOrModule(frame);
        Object specifier = this.argRefNode.execute(frame);
        if (this.context.getContextOptions().isImportAssertions() && this.optionsRefNode != null) {
            return this.executeAssertions(frame, referencingScriptOrModule, specifier);
        }
        return this.executeWithoutAssertions(referencingScriptOrModule, specifier);
    }

    private Object executeWithoutAssertions(Object referencingScriptOrModule, Object specifier) {
        TruffleString specifierString;
        PromiseCapabilityRecord promiseCapability = this.newPromiseCapability();
        try {
            specifierString = this.toStringNode.executeString(specifier);
        }
        catch (AbstractTruffleException ex) {
            return this.rejectPromise(promiseCapability, ex);
        }
        return this.hostImportModuleDynamically(referencingScriptOrModule, Module.ModuleRequest.create(specifierString), promiseCapability);
    }

    private Object executeAssertions(VirtualFrame frame, Object referencingScriptOrModule, Object specifier) {
        TruffleString specifierString;
        assert (this.optionsRefNode != null);
        if (this.enumerableOwnPropertyNamesNode == null || this.getAssertionsNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.enumerableOwnPropertyNamesNode = this.insert(EnumerableOwnPropertyNamesNode.createKeys(this.context));
            this.getAssertionsNode = this.insert(PropertyGetNode.create(ASSERTIONS, this.context));
        }
        Object options = this.optionsRefNode.execute(frame);
        PromiseCapabilityRecord promiseCapability = this.newPromiseCapability();
        try {
            specifierString = this.toStringNode.executeString(specifier);
        }
        catch (AbstractTruffleException ex) {
            return this.rejectPromise(promiseCapability, ex);
        }
        Map.Entry[] assertions = null;
        if (options != Undefined.instance) {
            Object assertionsObj;
            if (!JSRuntime.isObject(options)) {
                return this.rejectPromiseWithTypeError(promiseCapability, "The second argument to import() must be an object");
            }
            try {
                assertionsObj = this.getAssertionsNode.getValue(options);
            }
            catch (AbstractTruffleException ex) {
                return this.rejectPromise(promiseCapability, ex);
            }
            if (assertionsObj != Undefined.instance) {
                UnmodifiableArrayList<? extends Object> keys;
                if (!JSRuntime.isObject(assertionsObj)) {
                    return this.rejectPromiseWithTypeError(promiseCapability, "The 'assert' option must be an object");
                }
                JSDynamicObject obj = (JSDynamicObject)assertionsObj;
                try {
                    keys = this.enumerableOwnPropertyNamesNode.execute(obj);
                }
                catch (AbstractTruffleException ex) {
                    return this.rejectPromise(promiseCapability, ex);
                }
                assertions = new Map.Entry[keys.size()];
                for (int i = 0; i < keys.size(); ++i) {
                    Object value2;
                    TruffleString key = (TruffleString)keys.get(i);
                    try {
                        value2 = JSObject.get(obj, key);
                    }
                    catch (AbstractTruffleException ex) {
                        return this.rejectPromise(promiseCapability, ex);
                    }
                    if (!Strings.isTString(value2)) {
                        return this.rejectPromiseWithTypeError(promiseCapability, "Import assertion value must be a string");
                    }
                    assertions[i] = Boundaries.mapEntry(key, JSRuntime.toStringIsString(value2));
                }
            }
        }
        Module.ModuleRequest moduleRequest = assertions == null ? Module.ModuleRequest.create(specifierString) : ImportCallNode.createModuleRequestWithAssertions(specifierString, assertions);
        return this.hostImportModuleDynamically(referencingScriptOrModule, moduleRequest, promiseCapability);
    }

    @CompilerDirectives.TruffleBoundary
    private static Module.ModuleRequest createModuleRequestWithAssertions(TruffleString specifierString, Map.Entry<TruffleString, TruffleString>[] assertions) {
        return Module.ModuleRequest.create(specifierString, assertions);
    }

    private Object getActiveScriptOrModule(VirtualFrame frame) {
        if (this.activeScriptOrModuleNode != null) {
            return this.activeScriptOrModuleNode.execute(frame);
        }
        return new ScriptOrModule(this.context, this.getEncapsulatingSourceSection().getSource());
    }

    private JSDynamicObject hostImportModuleDynamically(Object referencingScriptOrModule, Module.ModuleRequest moduleRequest, PromiseCapabilityRecord promiseCapability) {
        JSRealm realm = this.getRealm();
        if (this.context.hasImportModuleDynamicallyCallbackBeenSet()) {
            JSDynamicObject promise = this.context.hostImportModuleDynamically(realm, (ScriptOrModule)referencingScriptOrModule, moduleRequest);
            if (promise == null) {
                return this.rejectPromise(promiseCapability, ImportCallNode.createTypeErrorCannotImport(moduleRequest.getSpecifier()));
            }
            assert (JSPromise.isJSPromise(promise));
            return promise;
        }
        this.context.promiseEnqueueJob(realm, this.createImportModuleDynamicallyJob((ScriptOrModule)referencingScriptOrModule, moduleRequest, promiseCapability));
        return promiseCapability.getPromise();
    }

    private PromiseCapabilityRecord newPromiseCapability() {
        if (this.newPromiseCapabilityNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.newPromiseCapabilityNode = this.insert(NewPromiseCapabilityNode.create(this.context));
        }
        return this.newPromiseCapabilityNode.executeDefault();
    }

    private JSDynamicObject rejectPromise(PromiseCapabilityRecord promiseCapability, AbstractTruffleException ex) {
        if (this.callRejectNode == null || this.getErrorObjectNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.callRejectNode = this.insert(JSFunctionCallNode.createCall());
            this.getErrorObjectNode = this.insert(TryCatchNode.GetErrorObjectNode.create(this.context));
        }
        Object error = this.getErrorObjectNode.execute(ex);
        this.callRejectNode.executeCall(JSArguments.createOneArg(Undefined.instance, promiseCapability.getReject(), error));
        return promiseCapability.getPromise();
    }

    private Object rejectPromiseWithTypeError(PromiseCapabilityRecord promiseCapability, String errorMessage) {
        if (this.callRejectNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
        }
        return this.rejectPromise(promiseCapability, Errors.createTypeError(errorMessage, (Node)this));
    }

    @CompilerDirectives.TruffleBoundary
    private static JSException createTypeErrorCannotImport(TruffleString specifier) {
        return Errors.createError("Cannot dynamically import module: " + specifier);
    }

    public JSFunctionObject createImportModuleDynamicallyJob(ScriptOrModule referencingScriptOrModule, Module.ModuleRequest moduleRequest, PromiseCapabilityRecord promiseCapability) {
        if (this.context.isOptionTopLevelAwait()) {
            Triple<ScriptOrModule, Module.ModuleRequest, PromiseCapabilityRecord> request = new Triple<ScriptOrModule, Module.ModuleRequest, PromiseCapabilityRecord>(referencingScriptOrModule, moduleRequest, promiseCapability);
            PromiseCapabilityRecord startModuleLoadCapability = this.newPromiseCapability();
            PromiseReactionRecord startModuleLoad = PromiseReactionRecord.create(startModuleLoadCapability, this.createImportModuleDynamicallyHandler(), true);
            return this.promiseReactionJobNode.execute(startModuleLoad, request);
        }
        Pair<ScriptOrModule, Module.ModuleRequest> request = new Pair<ScriptOrModule, Module.ModuleRequest>(referencingScriptOrModule, moduleRequest);
        return this.promiseReactionJobNode.execute(PromiseReactionRecord.create(promiseCapability, this.createImportModuleDynamicallyHandler(), true), request);
    }

    private JSDynamicObject createImportModuleDynamicallyHandler() {
        JSFunctionData functionData = this.context.getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.ImportModuleDynamically, c -> ImportCallNode.createImportModuleDynamicallyHandlerImpl(c));
        return JSFunction.create(this.getRealm(), functionData);
    }

    private static JSFunctionData createImportModuleDynamicallyHandlerImpl(JSContext context) {
        class TopLevelAwaitImportModuleDynamicallyRootNode
        extends ImportModuleDynamicallyRootNode {
            @Node.Child
            private PerformPromiseThenNode promiseThenNode;
            @Node.Child
            private JSFunctionCallNode callPromiseResolve;
            @Node.Child
            private JSFunctionCallNode callPromiseReject;
            @Node.Child
            private TryCatchNode.GetErrorObjectNode getErrorObjectNode;
            @Node.Child
            private PropertySetNode setModuleRecord;
            final /* synthetic */ JSContext val$context;

            TopLevelAwaitImportModuleDynamicallyRootNode(JSContext val$context) {
                this.val$context = val$context;
                class ImportModuleDynamicallyRootNode
                extends JavaScriptRootNode {
                    @Node.Child
                    protected JavaScriptNode argumentNode = AccessIndexedArgumentNode.create(0);
                    final /* synthetic */ JSContext val$context;

                    ImportModuleDynamicallyRootNode(JSContext val$context) {
                        this.val$context = val$context;
                    }

                    @Override
                    public Object execute(VirtualFrame frame) {
                        Pair request = (Pair)this.argumentNode.execute(frame);
                        ScriptOrModule referencingScriptOrModule = (ScriptOrModule)request.getFirst();
                        Module.ModuleRequest moduleRequest = (Module.ModuleRequest)request.getSecond();
                        JSModuleRecord moduleRecord = this.val$context.getEvaluator().hostResolveImportedModule(this.val$context, referencingScriptOrModule, moduleRequest);
                        return this.finishDynamicImport(this.getRealm(), moduleRecord, referencingScriptOrModule, moduleRequest);
                    }

                    protected Object finishDynamicImport(JSRealm realm, JSModuleRecord moduleRecord, ScriptOrModule referencingScriptOrModule, Module.ModuleRequest moduleRequest) {
                        this.val$context.getEvaluator().moduleLinking(realm, moduleRecord);
                        this.val$context.getEvaluator().moduleEvaluation(realm, moduleRecord);
                        if (moduleRecord.getEvaluationError() != null) {
                            throw JSRuntime.rethrow(moduleRecord.getEvaluationError());
                        }
                        assert (moduleRecord == this.val$context.getEvaluator().hostResolveImportedModule(this.val$context, referencingScriptOrModule, moduleRequest));
                        assert (moduleRecord.hasBeenEvaluated());
                        return this.val$context.getEvaluator().getModuleNamespace(moduleRecord);
                    }
                }
                super(val$context);
                this.promiseThenNode = PerformPromiseThenNode.create(this.val$context);
                this.callPromiseResolve = JSFunctionCallNode.createCall();
            }

            @Override
            public Object execute(VirtualFrame frame) {
                Triple request = (Triple)this.argumentNode.execute(frame);
                ScriptOrModule referencingScriptOrModule = (ScriptOrModule)request.getFirst();
                Module.ModuleRequest moduleRequest = (Module.ModuleRequest)request.getSecond();
                PromiseCapabilityRecord moduleLoadedCapability = (PromiseCapabilityRecord)request.getThird();
                try {
                    JSModuleRecord moduleRecord = this.val$context.getEvaluator().hostResolveImportedModule(this.val$context, referencingScriptOrModule, moduleRequest);
                    JSRealm realm = this.getRealm();
                    if (moduleRecord.hasTLA()) {
                        this.val$context.getEvaluator().moduleLinking(realm, moduleRecord);
                        Object innerPromise = this.val$context.getEvaluator().moduleEvaluation(realm, moduleRecord);
                        assert (JSPromise.isJSPromise(innerPromise));
                        JSDynamicObject resolve = this.createFinishDynamicImportCapabilityCallback(this.val$context, realm, moduleRecord, false);
                        JSDynamicObject reject = this.createFinishDynamicImportCapabilityCallback(this.val$context, realm, moduleRecord, true);
                        this.promiseThenNode.execute((JSDynamicObject)innerPromise, resolve, reject, moduleLoadedCapability);
                    } else {
                        Object result = this.finishDynamicImport(realm, moduleRecord, referencingScriptOrModule, moduleRequest);
                        if (moduleRecord.isAsyncEvaluation()) {
                            PromiseCapabilityRecord topLevelCapability = moduleRecord.getTopLevelCapability();
                            this.promiseThenNode.execute(topLevelCapability.getPromise(), moduleLoadedCapability.getResolve(), moduleLoadedCapability.getReject(), null);
                        } else {
                            this.callPromiseResolve.executeCall(JSArguments.create(Undefined.instance, moduleLoadedCapability.getResolve(), result));
                        }
                    }
                }
                catch (AbstractTruffleException ex) {
                    this.rejectPromise(moduleLoadedCapability, ex);
                }
                return Undefined.instance;
            }

            private void rejectPromise(PromiseCapabilityRecord moduleLoadedCapability, AbstractTruffleException ex) {
                if (this.getErrorObjectNode == null || this.callPromiseReject == null) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.getErrorObjectNode = this.insert(TryCatchNode.GetErrorObjectNode.create(this.val$context));
                    this.callPromiseReject = this.insert(JSFunctionCallNode.createCall());
                }
                Object errorObject = this.getErrorObjectNode.execute(ex);
                this.callPromiseReject.executeCall(JSArguments.create(Undefined.instance, moduleLoadedCapability.getReject(), errorObject));
            }

            private JSDynamicObject createFinishDynamicImportCapabilityCallback(JSContext cx, JSRealm realm, JSModuleRecord moduleRecord, boolean onReject) {
                JSFunctionData functionData = onReject ? cx.getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.FinishImportModuleDynamicallyReject, c -> ImportCallNode.createFinishDynamicImportNormalImpl(c, true)) : cx.getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.FinishImportModuleDynamicallyResolve, c -> ImportCallNode.createFinishDynamicImportNormalImpl(c, false));
                JSFunctionObject resolveFunction = JSFunction.create(realm, functionData);
                if (this.setModuleRecord == null) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.setModuleRecord = this.insert(PropertySetNode.createSetHidden(CURRENT_MODULE_RECORD_KEY, cx));
                }
                this.setModuleRecord.setValue(resolveFunction, moduleRecord);
                return resolveFunction;
            }
        }
        ImportModuleDynamicallyRootNode root = context.isOptionTopLevelAwait() ? new TopLevelAwaitImportModuleDynamicallyRootNode(context) : new ImportModuleDynamicallyRootNode(context);
        return JSFunctionData.createCallOnly(context, root.getCallTarget(), 0, Strings.EMPTY_STRING);
    }

    private static JSFunctionData createFinishDynamicImportNormalImpl(final JSContext cx, final boolean onReject) {
        class FinishDynamicImportNormalRootNode
        extends JavaScriptRootNode {
            @Node.Child
            private PropertyGetNode getModuleRecord;

            FinishDynamicImportNormalRootNode() {
                this.getModuleRecord = PropertyGetNode.createGetHidden(CURRENT_MODULE_RECORD_KEY, cx);
            }

            @Override
            public Object execute(VirtualFrame frame) {
                JSDynamicObject thisFunction = (JSDynamicObject)JSArguments.getFunctionObject(frame.getArguments());
                JSModuleRecord moduleRecord = (JSModuleRecord)this.getModuleRecord.getValue(thisFunction);
                assert (moduleRecord != null);
                if (onReject) {
                    assert (moduleRecord.getEvaluationError() != null);
                    throw JSRuntime.rethrow(moduleRecord.getEvaluationError());
                }
                return cx.getEvaluator().getModuleNamespace(moduleRecord);
            }
        }
        return JSFunctionData.createCallOnly(cx, new FinishDynamicImportNormalRootNode().getCallTarget(), 0, Strings.EMPTY_STRING);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        if (this.optionsRefNode == null) {
            return ImportCallNode.create(this.context, ImportCallNode.cloneUninitialized(this.argRefNode, materializedTags), ImportCallNode.cloneUninitialized(this.activeScriptOrModuleNode, materializedTags));
        }
        return ImportCallNode.createWithOptions(this.context, ImportCallNode.cloneUninitialized(this.argRefNode, materializedTags), ImportCallNode.cloneUninitialized(this.activeScriptOrModuleNode, materializedTags), ImportCallNode.cloneUninitialized(this.optionsRefNode, materializedTags));
    }
}

