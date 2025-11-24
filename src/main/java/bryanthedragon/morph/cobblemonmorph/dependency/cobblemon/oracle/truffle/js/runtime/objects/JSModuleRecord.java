
package com.oracle.truffle.js.runtime.objects;

import com.oracle.js.parser.ir.Module;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSModuleData;
import com.oracle.truffle.js.runtime.objects.JSModuleLoader;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;
import com.oracle.truffle.js.runtime.objects.ScriptOrModule;
import java.util.ArrayList;
import java.util.List;

public class JSModuleRecord
extends ScriptOrModule {
    private final JSModuleData parsedModule;
    private final JSModuleLoader moduleLoader;
    private Status status;
    private Throwable evaluationError;
    private Object executionResult;
    private JSDynamicObject namespace;
    private MaterializedFrame environment;
    private JSDynamicObject importMeta;
    private Object hostDefined;
    private int dfsIndex;
    private int dfsAncestorIndex;
    private JSModuleRecord cycleRoot = this;
    private final boolean hasTLA;
    private long asyncEvaluationOrder;
    private PromiseCapabilityRecord topLevelPromiseCapability = null;
    private List<JSModuleRecord> asyncParentModules = null;
    private int pendingAsyncDependencies = 0;

    public JSModuleRecord(JSModuleData parsedModule, JSModuleLoader moduleLoader) {
        super(parsedModule.getContext(), parsedModule.getSource());
        this.parsedModule = parsedModule;
        this.moduleLoader = moduleLoader;
        this.hasTLA = parsedModule.isTopLevelAsync();
        this.hostDefined = null;
        this.setUnlinked();
    }

    public JSModuleRecord(JSModuleData moduleData, JSModuleLoader moduleLoader, Object hostDefined) {
        this(moduleData, moduleLoader);
        this.hostDefined = hostDefined;
    }

    public Module getModule() {
        return this.parsedModule.getModule();
    }

    public JSModuleLoader getModuleLoader() {
        return this.moduleLoader;
    }

    public JSFunctionData getFunctionData() {
        return this.parsedModule.getFunctionData();
    }

    public FrameDescriptor getFrameDescriptor() {
        return this.parsedModule.getFrameDescriptor();
    }

    public JSModuleData getModuleData() {
        return this.parsedModule;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean hasBeenEvaluated() {
        return this.getStatus() == Status.Evaluated || this.getStatus() == Status.EvaluatingAsync;
    }

    public Throwable getEvaluationError() {
        assert (this.hasBeenEvaluated());
        return this.evaluationError;
    }

    public void setEvaluationError(Throwable evaluationError) {
        assert (this.hasBeenEvaluated());
        this.evaluationError = evaluationError;
    }

    public JSDynamicObject getNamespace() {
        return this.namespace;
    }

    public void setNamespace(JSDynamicObject namespace) {
        assert (this.namespace == null);
        this.namespace = namespace;
    }

    public MaterializedFrame getEnvironment() {
        return this.environment;
    }

    public void setEnvironment(MaterializedFrame environment) {
        assert (this.environment == null);
        assert (this.getFrameDescriptor() == environment.getFrameDescriptor());
        this.environment = environment;
    }

    public Object getHostDefined() {
        return this.hostDefined;
    }

    public int getDFSIndex() {
        assert (this.dfsIndex >= 0);
        return this.dfsIndex;
    }

    public void setDFSIndex(int dfsIndex) {
        this.dfsIndex = dfsIndex;
    }

    public int getDFSAncestorIndex() {
        assert (this.dfsAncestorIndex >= 0);
        return this.dfsAncestorIndex;
    }

    public void setDFSAncestorIndex(int dfsAncestorIndex) {
        this.dfsAncestorIndex = dfsAncestorIndex;
    }

    public Object getExecutionResult() {
        assert (this.hasBeenEvaluated());
        return this.executionResult;
    }

    public void setExecutionResult(Object executionResult) {
        this.executionResult = executionResult;
    }

    public Object getExecutionResultOrThrow() {
        assert (this.hasBeenEvaluated());
        Throwable error = this.getEvaluationError();
        if (error != null) {
            throw JSRuntime.rethrow(error);
        }
        Object result = this.getExecutionResult();
        assert (result != null);
        return result;
    }

    public JSDynamicObject getImportMeta() {
        if (this.importMeta == null) {
            this.importMeta = this.createMetaObject();
        }
        return this.importMeta;
    }

    private JSDynamicObject createMetaObject() {
        JSObject metaObj = JSOrdinary.createWithNullPrototype(this.context);
        if (this.context.hasImportMetaInitializerBeenSet()) {
            this.context.notifyImportMetaInitializer(metaObj, this);
        } else {
            this.initializeMetaObject(metaObj);
        }
        return metaObj;
    }

    @CompilerDirectives.TruffleBoundary
    private void initializeMetaObject(JSObject metaObj) {
        JSObject.set((JSDynamicObject)metaObj, Strings.URL, (Object)Strings.fromJavaString(this.getSource().getURI().toString()));
    }

    public void setUnlinked() {
        this.setStatus(Status.Unlinked);
        this.environment = null;
        this.dfsIndex = -1;
        this.dfsAncestorIndex = -1;
    }

    public PromiseCapabilityRecord getTopLevelCapability() {
        return this.topLevelPromiseCapability;
    }

    public void setTopLevelCapability(PromiseCapabilityRecord capability) {
        this.topLevelPromiseCapability = capability;
    }

    public boolean isAsyncEvaluation() {
        return this.asyncEvaluationOrder > 0L;
    }

    public List<JSModuleRecord> getAsyncParentModules() {
        return this.asyncParentModules;
    }

    public void setPendingAsyncDependencies(int value2) {
        this.pendingAsyncDependencies = value2;
    }

    public void initAsyncParentModules() {
        assert (this.asyncParentModules == null);
        this.asyncParentModules = new ArrayList<JSModuleRecord>();
    }

    public void incPendingAsyncDependencies() {
        ++this.pendingAsyncDependencies;
    }

    public void decPendingAsyncDependencies() {
        --this.pendingAsyncDependencies;
    }

    public void appendAsyncParentModules(JSModuleRecord moduleRecord) {
        this.asyncParentModules.add(moduleRecord);
    }

    public int getPendingAsyncDependencies() {
        return this.pendingAsyncDependencies;
    }

    public void setAsyncEvaluatingOrder(long order) {
        this.asyncEvaluationOrder = order;
    }

    public long getAsyncEvaluatingOrder() {
        return this.asyncEvaluationOrder;
    }

    public boolean hasTLA() {
        return this.hasTLA;
    }

    public void setCycleRoot(JSModuleRecord module) {
        this.cycleRoot = module;
    }

    public JSModuleRecord getCycleRoot() {
        return this.cycleRoot;
    }

    public static enum Status {
        Unlinked,
        Linking,
        Linked,
        Evaluating,
        EvaluatingAsync,
        Evaluated;

    }
}

