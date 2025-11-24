
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleStackTrace;
import com.oracle.truffle.api.TruffleStackTraceElement;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.polyglot.EngineAccessor;
import com.oracle.truffle.polyglot.OptionValuesImpl;
import com.oracle.truffle.polyglot.PolyglotContextConfig;
import com.oracle.truffle.polyglot.PolyglotContextImpl;
import com.oracle.truffle.polyglot.PolyglotEngineImpl;
import com.oracle.truffle.polyglot.PolyglotEngineOptions;
import com.oracle.truffle.polyglot.PolyglotLanguage;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import com.oracle.truffle.polyglot.PolyglotLanguageInstance;
import com.oracle.truffle.polyglot.PolyglotSourceCache;
import com.oracle.truffle.polyglot.WeakAssumedValue;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.graalvm.polyglot.Source;

final class PolyglotSharingLayer {
    final PolyglotEngineImpl engine;
    @CompilerDirectives.CompilationFinal
    volatile Shared shared;
    PolyglotLanguageInstance hostLanguage;

    PolyglotSharingLayer(PolyglotEngineImpl engine) {
        this.engine = engine;
    }

    public boolean claimLayerForContext(PolyglotSharingLayer sharableLayer, PolyglotContextImpl context, Set<PolyglotLanguage> requestingLanguages) {
        Shared s;
        assert (Thread.holdsLock(this.engine.lock));
        assert (!this.isClaimed()) : "already claimed";
        assert (sharableLayer == null || sharableLayer.isClaimed() && sharableLayer.getContextPolicy() != TruffleLanguage.ContextPolicy.EXCLUSIVE);
        Shared shared = s = sharableLayer != null ? sharableLayer.shared : null;
        if (s != null) {
            switch (s.contextPolicy) {
                case EXCLUSIVE: {
                    return false;
                }
                case REUSE: {
                    if (s.claimedCount <= 0) break;
                    return false;
                }
                case SHARED: {
                    break;
                }
                default: {
                    CompilerDirectives.shouldNotReachHere();
                }
            }
        }
        Map<PolyglotLanguage, OptionValuesImpl> newLanguageOptions = this.collectLanguageOptions(context.config, requestingLanguages);
        TruffleLanguage.ContextPolicy newPolicy = this.engine.isSharingEnabled(context.config) ? PolyglotSharingLayer.computeMinContextPolicyPolicy(newLanguageOptions.keySet()) : (s != null && !context.config.isCodeSharingDisabled() ? s.contextPolicy : TruffleLanguage.ContextPolicy.EXCLUSIVE);
        Map<PolyglotLanguage, OptionValuesImpl> previousLanguageOptions = null;
        if (s == null) {
            s = new Shared(this.engine, newPolicy, newLanguageOptions);
            s.instances[0] = this.hostLanguage;
            if (newPolicy != TruffleLanguage.ContextPolicy.EXCLUSIVE && !PolyglotSharingLayer.areLanguageOptionsCompatible(s, newLanguageOptions, newLanguageOptions)) {
                s.contextPolicy = TruffleLanguage.ContextPolicy.EXCLUSIVE;
            }
        } else {
            previousLanguageOptions = s.previousLanguageOptions;
            if (!PolyglotSharingLayer.isContextPolicyCompatible(s.contextPolicy, newPolicy)) {
                if (this.engine.getEngineOptionValues().get(PolyglotEngineOptions.TraceCodeSharing).booleanValue()) {
                    this.traceClaimLayer(false, s, context, requestingLanguages, previousLanguageOptions);
                }
                return false;
            }
            for (PolyglotLanguage language : previousLanguageOptions.keySet()) {
                if (newLanguageOptions.containsKey(language)) continue;
                newLanguageOptions.put(language, context.config.getLanguageOptionValues(language));
            }
            if (!PolyglotSharingLayer.areLanguageOptionsCompatible(s, previousLanguageOptions, newLanguageOptions)) {
                if (this.engine.getEngineOptionValues().get(PolyglotEngineOptions.TraceCodeSharing).booleanValue()) {
                    this.traceClaimLayer(false, s, context, requestingLanguages, previousLanguageOptions);
                }
                return false;
            }
            s.previousLanguageOptions = newLanguageOptions;
            PolyglotLanguageInstance hostInstance = s.instances[0];
            assert (hostInstance != null) : "host instance must always be initialized before claiming a shared layer";
            context.getHostContext().patchInstance(hostInstance);
        }
        s.updatePreinitConfig(context.config);
        assert (this.shared == null || this.shared == s);
        this.shared = s;
        if (this.isSingleContext()) {
            s.singleContextValue.update(context);
        } else {
            s.singleContextValue.invalidate();
            this.hostLanguage.singleLanguageContext.invalidate();
        }
        ++s.claimedCount;
        if (this.engine.getEngineOptionValues().get(PolyglotEngineOptions.TraceCodeSharing).booleanValue()) {
            this.traceClaimLayer(true, s, context, requestingLanguages, previousLanguageOptions);
        }
        return true;
    }

    boolean isSingleContext() {
        Shared s = this.shared;
        return (s == null || s.contextPolicy == TruffleLanguage.ContextPolicy.EXCLUSIVE) && !this.engine.isStoreEngine();
    }

    public void preInitialize() {
        assert (Thread.holdsLock(this.engine.lock));
        assert (this.engine.isSharingEnabled(null));
        if (!this.isClaimed()) {
            return;
        }
        Shared s = this.shared;
        PolyglotContextConfig.PreinitConfig preinitConfig = s.preinitConfig;
        assert (preinitConfig != null) : "preinit config must be initialized";
        LinkedHashSet<PolyglotLanguage> toInitialize = new LinkedHashSet<PolyglotLanguage>();
        for (PolyglotLanguageInstance instance : s.instances) {
            if (instance == null || instance.language.isHost()) continue;
            toInitialize.add(instance.language);
        }
        s.preInitializedContext = PolyglotContextImpl.preinitialize(this.engine, preinitConfig, this, toInitialize, false);
        assert (s.preInitializedContext.layer.equals(this)) : "invalid resulting layer";
    }

    public PolyglotContextImpl loadPreinitializedContext(PolyglotContextConfig config) {
        assert (Thread.holdsLock(this.engine.lock));
        assert (this.engine.isSharingEnabled(null));
        Shared s = this.shared;
        if (s == null) {
            return null;
        }
        PolyglotContextImpl preinitContext = s.preInitializedContext;
        if (preinitContext == null) {
            return null;
        }
        LinkedHashSet<PolyglotLanguage> usedLanguages = new LinkedHashSet<PolyglotLanguage>();
        for (PolyglotLanguageInstance instance : s.instances) {
            if (instance == null || instance.language.isHost()) continue;
            usedLanguages.add(instance.language);
        }
        Map<PolyglotLanguage, OptionValuesImpl> newLanguageOptions = this.collectLanguageOptions(config, usedLanguages);
        if (!PolyglotSharingLayer.areLanguageOptionsCompatible(s, s.previousLanguageOptions, newLanguageOptions)) {
            if (this.engine.getEngineOptionValues().get(PolyglotEngineOptions.TraceCodeSharing).booleanValue()) {
                this.traceContextPreinit(false, s, preinitContext, s.previousLanguageOptions, newLanguageOptions);
            }
            return null;
        }
        if (this.engine.getEngineOptionValues().get(PolyglotEngineOptions.TraceCodeSharing).booleanValue()) {
            this.traceContextPreinit(true, s, preinitContext, s.previousLanguageOptions, newLanguageOptions);
        }
        assert (s.preInitializedContext == preinitContext) : "must only be mutated while engine lock is held";
        s.preInitializedContext = null;
        return preinitContext;
    }

    public void freeSharingLayer(PolyglotContextImpl context) {
        assert (Thread.holdsLock(this.engine.lock));
        assert (this.isClaimed());
        --this.shared.claimedCount;
        if (this.engine.getEngineOptionValues().get(PolyglotEngineOptions.TraceCodeSharing).booleanValue()) {
            this.traceFreeLayer(context);
        }
    }

    public PolyglotLanguageInstance allocateHostLanguage(PolyglotLanguage language) {
        assert (!this.isClaimed());
        assert (this.hostLanguage == null) : "host language allocated twice";
        this.hostLanguage = language.createInstance(this);
        return this.hostLanguage;
    }

    public PolyglotLanguageInstance allocateInstance(PolyglotContextImpl context, PolyglotLanguage language) {
        TruffleLanguage.ContextPolicy languagePolicy;
        assert (Thread.holdsLock(this.engine.lock));
        assert (this.isClaimed()) : "allocateInstance before claim";
        assert (!language.isHost()) : "not host language";
        PolyglotContextConfig config = context.config;
        Shared s = this.shared;
        TruffleLanguage.ContextPolicy layerPolicy = s.contextPolicy;
        if (layerPolicy == TruffleLanguage.ContextPolicy.EXCLUSIVE) {
            assert (s.claimedCount <= 1);
            languagePolicy = TruffleLanguage.ContextPolicy.EXCLUSIVE;
        } else {
            OptionValuesImpl values;
            languagePolicy = language.cache.getPolicy();
            if (languagePolicy != TruffleLanguage.ContextPolicy.EXCLUSIVE && config != null && !PolyglotSharingLayer.areOptionsCompatible(s, language, values = config.getLanguageOptionValues(language), values)) {
                languagePolicy = TruffleLanguage.ContextPolicy.EXCLUSIVE;
            }
        }
        if (!PolyglotSharingLayer.isContextPolicyCompatible(layerPolicy, languagePolicy)) {
            String resolution;
            String reason;
            String id = language.getId();
            if (!this.engine.boundEngine) {
                reason = String.format("The context was configured with a shared engine but lazily initialized language '%s' does not support sharing. ", id);
                resolution = String.format(" To resolve this either: %n - Ensure all languages are known when the context is constructed, by providing all required languages in the Context.newBuilder(\"%s\") method. %n - Avoid lazy initialization of language '%s' by initializing as the first language using Context.initialize(\"%s\"). %n - Disable sharing for the polyglot context by removing the explicit engine configuration with Context.Builder.engine(...).", id, id, id, id);
            } else if (this.engine.storeEngine) {
                reason = String.format("The engine was configured to be stored but lazily initialized language '%s' does not support storing sharing data. ", id);
                resolution = "";
            } else {
                reason = String.format("The engine was forced to use code sharing but lazily initialized language '%s' does not support sharing. ", id);
                resolution = "";
            }
            throw new SharingLazyInitializationError(String.format("%sNon sharable languages cannot be initialized lazily and must be known ahead of time when the context is created. Use the --engine.TraceCodeCache option to print debug details on the sharing decisions.%s", reason, resolution));
        }
        PolyglotLanguageInstance instance = s.instances[language.engineIndex];
        if (instance == null) {
            s.instances[language.engineIndex] = instance = language.createInstance(this);
            if (!this.isSingleContext()) {
                EngineAccessor.LANGUAGE.initializeMultiContext(instance.spi);
            }
            if (this.engine.getEngineOptionValues().get(PolyglotEngineOptions.TraceCodeSharing).booleanValue()) {
                this.traceAllocateLanguageInstance(context, language);
            }
        }
        return instance;
    }

    public PolyglotSourceCache getSourceCache() {
        assert (this.isClaimed()) : "source cache access before claim";
        return this.shared.sourceCache;
    }

    public PolyglotLanguageInstance getInstance(PolyglotLanguage language) {
        Shared s = this.shared;
        if (s == null) {
            return null;
        }
        return PolyglotSharingLayer.getInstance(s, language);
    }

    private static PolyglotLanguageInstance getInstance(Shared s, PolyglotLanguage language) {
        return s.instances[language.engineIndex];
    }

    public PolyglotContextImpl getSingleConstantContext() {
        if (CompilerDirectives.inInterpreter() || !CompilerDirectives.isPartialEvaluationConstant(this)) {
            return null;
        }
        Shared s = this.shared;
        if (s == null) {
            return null;
        }
        return s.singleContextValue.getConstant();
    }

    public PolyglotLanguageContext getSingleConstantLanguageContext(PolyglotLanguage language) {
        if (CompilerDirectives.inInterpreter() || !CompilerDirectives.isPartialEvaluationConstant(this)) {
            return null;
        }
        CompilerAsserts.partialEvaluationConstant(language);
        Shared s = this.shared;
        if (s == null) {
            return null;
        }
        PolyglotLanguageInstance instance = s.instances[language.engineIndex];
        CompilerAsserts.partialEvaluationConstant(instance);
        if (instance == null) {
            return null;
        }
        return instance.singleLanguageContext.getConstant();
    }

    public TruffleLanguage.ContextPolicy getContextPolicy() {
        assert (this.isClaimed()) : "context policy lookup before claim";
        return this.shared.contextPolicy;
    }

    public boolean isClaimed() {
        return this.shared != null;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PolyglotSharingLayer)) {
            return false;
        }
        PolyglotSharingLayer other = (PolyglotSharingLayer)obj;
        return this.engine == other.engine && this.shared == other.shared;
    }

    public int hashCode() {
        return Objects.hash(this.engine, this.shared);
    }

    public String toString() {
        StringBuilder string = new StringBuilder();
        Shared s = this.shared;
        if (s == null) {
            string.append("state=unclaimed");
        } else {
            string.append("state=claimed layer-policy=");
            string.append((Object)s.contextPolicy);
            string.append(" languages=[");
            String sep = "";
            for (PolyglotLanguageInstance instance : s.instances) {
                if (instance == null) continue;
                string.append(sep);
                string.append(instance.language.getId());
                sep = ", ";
            }
            string.append("]");
        }
        return "PolyglotSharingLayer[" + string + "]";
    }

    private static boolean isContextPolicyCompatible(TruffleLanguage.ContextPolicy prevPolicy, TruffleLanguage.ContextPolicy newPolicy) {
        return prevPolicy.ordinal() <= newPolicy.ordinal();
    }

    private static TruffleLanguage.ContextPolicy computeMinContextPolicyPolicy(Set<PolyglotLanguage> languages) {
        assert (!languages.isEmpty()) : "cannot compute sharing for empty set of languages";
        TruffleLanguage.ContextPolicy newPolicy = TruffleLanguage.ContextPolicy.SHARED;
        for (PolyglotLanguage language : languages) {
            TruffleLanguage.ContextPolicy policy = language.cache.getPolicy();
            if (policy.ordinal() >= newPolicy.ordinal() || (newPolicy = policy) != TruffleLanguage.ContextPolicy.EXCLUSIVE) continue;
            break;
        }
        return newPolicy;
    }

    private static boolean areLanguageOptionsCompatible(Shared s, Map<PolyglotLanguage, OptionValuesImpl> oldLanguageOptions, Map<PolyglotLanguage, OptionValuesImpl> newLanguageOptions) {
        for (Map.Entry<PolyglotLanguage, OptionValuesImpl> entry : newLanguageOptions.entrySet()) {
            PolyglotLanguage language = entry.getKey();
            OptionValuesImpl newOptions = entry.getValue();
            assert (newOptions != null);
            OptionValuesImpl prevOptions = oldLanguageOptions.get(language);
            if (prevOptions == null) {
                prevOptions = language.getOptionValues();
            }
            if (PolyglotSharingLayer.areOptionsCompatible(s, language, prevOptions, newOptions)) continue;
            return false;
        }
        return true;
    }

    private static boolean areOptionsCompatible(Shared s, PolyglotLanguage language, OptionValuesImpl previousOptions, OptionValuesImpl newOptions) {
        PolyglotLanguageInstance instance = PolyglotSharingLayer.resolveInstance(s, language);
        return EngineAccessor.LANGUAGE.areOptionsCompatible(instance.spi, previousOptions, newOptions);
    }

    private static PolyglotLanguageInstance resolveInstance(Shared s, PolyglotLanguage language) {
        PolyglotLanguageInstance instance = PolyglotSharingLayer.getInstance(s, language);
        if (instance == null) {
            instance = language.getInitLanguage();
        }
        return instance;
    }

    private Map<PolyglotLanguage, OptionValuesImpl> collectLanguageOptions(PolyglotContextConfig config, Set<PolyglotLanguage> forcedLanguages) {
        HashMap<PolyglotLanguage, OptionValuesImpl> newOptions = new HashMap<PolyglotLanguage, OptionValuesImpl>();
        Set<PolyglotLanguage> languages = config.getConfiguredLanguages();
        if (!languages.containsAll(forcedLanguages)) {
            languages = new HashSet<PolyglotLanguage>(languages);
            for (PolyglotLanguage language : forcedLanguages) {
                config.addConfiguredLanguage(this.engine, languages, language);
            }
        }
        for (PolyglotLanguage language : languages) {
            newOptions.put(language, config.getLanguageOptionValues(language));
        }
        return newOptions;
    }

    public void listCachedSources(Set<Source> sources) {
        Shared s = this.shared;
        if (s != null) {
            s.sourceCache.listCachedSources(this.engine.getImpl(), sources);
        }
    }

    public static AssertionError invalidSharingError(Node node, PolyglotSharingLayer previousLayer, PolyglotSharingLayer currentLayer) throws AssertionError {
        PolyglotSharingLayer prev = previousLayer;
        PolyglotSharingLayer current = currentLayer;
        Exception e = new Exception();
        StringBuilder stack = new StringBuilder();
        Exception exceptionCreating = null;
        try {
            Object root;
            TruffleStackTrace.fillIn(e);
            stack.append(String.format("%n  <<current-context>>", new Object[0]));
            PolyglotSharingLayer.printLayerChange(stack, prev, current);
            if (node != null && (root = node.getRootNode()) != null) {
                stack.append(String.format("%n  %s(%s)", PolyglotSharingLayer.createJavaStackFrame((RootNode)root, node.getEncapsulatingSourceSection()), node));
            }
            for (TruffleStackTraceElement stackTrace : TruffleStackTrace.getStackTrace(e)) {
                RootNode root2 = stackTrace.getTarget().getRootNode();
                current = (PolyglotSharingLayer)EngineAccessor.NODES.getSharingLayer(root2);
                PolyglotSharingLayer.printLayerChange(stack, prev, current);
                SourceSection sourceSection = null;
                Node location = stackTrace.getLocation();
                if (location != null) {
                    sourceSection = location.getEncapsulatingSourceSection();
                }
                stack.append(String.format("%n  %s", PolyglotSharingLayer.createJavaStackFrame(root2, sourceSection)));
                if (current == null) continue;
                prev = current;
            }
        }
        catch (Exception ex) {
            exceptionCreating = ex;
        }
        AssertionError error = new AssertionError((Object)String.format("Invalid sharing of AST nodes detected. The current context uses a different sharing layer than the executed node. A common cause of this are CallTargets that are reused across different contexts in an invalid way.Stack trace: %s", stack.toString()));
        if (exceptionCreating != null) {
            ((Throwable)((Object)error)).addSuppressed(exceptionCreating);
        }
        throw error;
    }

    private static void printLayerChange(StringBuilder stack, PolyglotSharingLayer previousLayer, PolyglotSharingLayer newLayer) {
        if (newLayer != null && !Objects.equals(previousLayer, newLayer)) {
            stack.append(String.format("%n    <-- Sharing Layer Change: 0x%H => 0x%H -->", System.identityHashCode(previousLayer.shared), System.identityHashCode(newLayer.shared)));
        }
    }

    private static StackTraceElement createJavaStackFrame(RootNode root, SourceSection sourceSection) {
        PolyglotLanguageInstance instance;
        SourceSection sc = sourceSection;
        if (sc == null) {
            sc = root.getSourceSection();
        }
        String language = (instance = PolyglotSharingLayer.lookupLanguageInstance(root)) != null ? instance.language.getId() : "Unknown";
        String rootName = root.getName();
        String declaringClass = "<" + language + ">";
        String methodName = rootName == null ? "" : rootName;
        String fileName = sc != null ? sc.getSource().getName() : "Unknown";
        int startLine = sc != null ? sc.getStartLine() : -1;
        return new StackTraceElement(declaringClass, methodName, fileName, startLine);
    }

    private static PolyglotLanguageInstance lookupLanguageInstance(RootNode root) {
        TruffleLanguage<?> spi = EngineAccessor.NODES.getLanguage(root);
        if (spi != null) {
            return (PolyglotLanguageInstance)EngineAccessor.LANGUAGE.getPolyglotLanguageInstance(spi);
        }
        return null;
    }

    private void traceContextPreinit(boolean success, Shared s, PolyglotContextImpl context, Map<PolyglotLanguage, OptionValuesImpl> previousOptions, Map<PolyglotLanguage, OptionValuesImpl> newLanguageOptions) {
        this.trace(context, s, "loading pre-init", String.format("claimedCount:%s sharingEnabled:%s ", success ? s.claimedCount - 1 : s.claimedCount, this.engine.isSharingEnabled(context.config)));
        for (Map.Entry<PolyglotLanguage, OptionValuesImpl> entry : newLanguageOptions.entrySet()) {
            this.traceCompatibility(s, context, previousOptions, entry);
        }
        this.trace(context, s, success ? "loaded" : "failed to load pre-init", "");
    }

    private void traceClaimLayer(boolean success, Shared s, PolyglotContextImpl context, Set<PolyglotLanguage> requestingLangauges, Map<PolyglotLanguage, OptionValuesImpl> previousOptions) {
        this.trace(context, s, "claiming", String.format("claimedCount:%s sharingEnabled:%s ", success ? s.claimedCount - 1 : s.claimedCount, this.engine.isSharingEnabled(context.config)));
        Map<PolyglotLanguage, OptionValuesImpl> newLanguageOptions = this.collectLanguageOptions(context.config, requestingLangauges);
        for (Map.Entry<PolyglotLanguage, OptionValuesImpl> entry : newLanguageOptions.entrySet()) {
            this.traceCompatibility(s, context, previousOptions, entry);
        }
        this.trace(context, s, success ? "claimed" : "failed to claim", String.format("claimedCount:%s layer-policy:%s", new Object[]{s.claimedCount, s.contextPolicy}));
    }

    private void traceCompatibility(Shared s, PolyglotContextImpl context, Map<PolyglotLanguage, OptionValuesImpl> previousOptions, Map.Entry<PolyglotLanguage, OptionValuesImpl> entry) {
        StringBuilder languageInfos = new StringBuilder();
        PolyglotLanguage language = entry.getKey();
        TruffleLanguage.ContextPolicy policy = language.cache.getPolicy();
        languageInfos.append(String.format("%s registration-policy:%s  ", new Object[]{language.getId(), policy}));
        boolean optionsCompatible = PolyglotSharingLayer.isContextPolicyCompatible(s.contextPolicy, policy);
        if (optionsCompatible && this.engine.isSharingEnabled(context.config)) {
            OptionValuesImpl prevOptions;
            OptionValuesImpl newOptions = entry.getValue();
            OptionValuesImpl optionValuesImpl = prevOptions = previousOptions != null ? previousOptions.get(language) : newOptions;
            if (prevOptions == null) {
                prevOptions = language.getOptionValues();
            }
            optionsCompatible = PolyglotSharingLayer.areOptionsCompatible(s, language, prevOptions, newOptions);
            languageInfos.append(String.format("%s.areOptionsCompatibleWith(%s, %s) == %s", PolyglotSharingLayer.resolveInstance((Shared)s, (PolyglotLanguage)language).spi.getClass().getSimpleName(), prevOptions, newOptions, optionsCompatible));
        }
        this.trace(context, s, optionsCompatible ? "  compatible" : "  incompatible", languageInfos.toString());
    }

    private void traceFreeLayer(PolyglotContextImpl context) {
        this.trace(context, this.shared, "freed", String.format("claimedCount:%s", context.layer.shared.claimedCount));
    }

    private void traceAllocateLanguageInstance(PolyglotContextImpl context, PolyglotLanguage language) {
        this.trace(context, this.shared, "created language", String.format("%s for policy %s", new Object[]{language.getId(), this.shared.contextPolicy}));
    }

    private void trace(PolyglotContextImpl context, Shared s, String label, String message) {
        this.engine.getEngineLogger().info(String.format("[sharing] engine 0x%8H context 0x%8H layer 0x%8H: %-20s %s", this.engine.hashCode(), Objects.hash(context), s.hashCode(), label, message));
    }

    static final class SharingLazyInitializationError
    extends AbstractTruffleException {
        SharingLazyInitializationError(String message) {
            super(message);
        }
    }

    static final class Shared {
        final PolyglotSourceCache sourceCache = new PolyglotSourceCache();
        @CompilerDirectives.CompilationFinal(dimensions=1)
        private final PolyglotLanguageInstance[] instances;
        @CompilerDirectives.CompilationFinal
        TruffleLanguage.ContextPolicy contextPolicy;
        Map<PolyglotLanguage, OptionValuesImpl> previousLanguageOptions;
        final WeakAssumedValue<PolyglotContextImpl> singleContextValue = new WeakAssumedValue("single context");
        volatile PolyglotContextConfig.PreinitConfig preinitConfig;
        volatile PolyglotContextImpl preInitializedContext;
        int claimedCount;

        private Shared(PolyglotEngineImpl engine, TruffleLanguage.ContextPolicy contextPolicy, Map<PolyglotLanguage, OptionValuesImpl> previousLanguageOptions) {
            this.contextPolicy = contextPolicy;
            this.instances = new PolyglotLanguageInstance[engine.languageCount];
            this.previousLanguageOptions = previousLanguageOptions;
        }

        void updatePreinitConfig(PolyglotContextConfig config) {
            PolyglotContextConfig.PreinitConfig prev = this.preinitConfig;
            PolyglotContextConfig.PreinitConfig newConfig = prev == null ? new PolyglotContextConfig.PreinitConfig(config) : new PolyglotContextConfig.PreinitConfig(prev, config);
            this.preinitConfig = newConfig;
        }
    }
}

