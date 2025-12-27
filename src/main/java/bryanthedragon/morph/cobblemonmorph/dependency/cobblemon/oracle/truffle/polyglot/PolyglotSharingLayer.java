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
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;
import org.graalvm.polyglot.Source;

final class PolyglotSharingLayer {
   final PolyglotEngineImpl engine;
   @CompilerDirectives.CompilationFinal
   volatile PolyglotSharingLayer.Shared shared;
   PolyglotLanguageInstance hostLanguage;

   PolyglotSharingLayer(PolyglotEngineImpl engine) {
      this.engine = engine;
   }

   public boolean claimLayerForContext(PolyglotSharingLayer sharableLayer, PolyglotContextImpl context, Set<PolyglotLanguage> requestingLanguages) {
      assert Thread.holdsLock(this.engine.lock);

      assert !this.isClaimed() : "already claimed";

      assert sharableLayer == null || sharableLayer.isClaimed() && sharableLayer.getContextPolicy() != TruffleLanguage.ContextPolicy.EXCLUSIVE;

      PolyglotSharingLayer.Shared s = sharableLayer != null ? sharableLayer.shared : null;
      if (s != null) {
         switch (s.contextPolicy) {
            case EXCLUSIVE:
               return false;
            case REUSE:
               if (s.claimedCount > 0) {
                  return false;
               }
            case SHARED:
               break;
            default:
               CompilerDirectives.shouldNotReachHere();
         }
      }

      Map<PolyglotLanguage, OptionValuesImpl> newLanguageOptions = this.collectLanguageOptions(context.config, requestingLanguages);
      TruffleLanguage.ContextPolicy newPolicy;
      if (this.engine.isSharingEnabled(context.config)) {
         newPolicy = computeMinContextPolicyPolicy(newLanguageOptions.keySet());
      } else if (s != null && !context.config.isCodeSharingDisabled()) {
         newPolicy = s.contextPolicy;
      } else {
         newPolicy = TruffleLanguage.ContextPolicy.EXCLUSIVE;
      }

      Map<PolyglotLanguage, OptionValuesImpl> previousLanguageOptions = null;
      if (s == null) {
         s = new PolyglotSharingLayer.Shared(this.engine, newPolicy, newLanguageOptions);
         s.instances[0] = this.hostLanguage;
         if (newPolicy != TruffleLanguage.ContextPolicy.EXCLUSIVE && !areLanguageOptionsCompatible(s, newLanguageOptions, newLanguageOptions)) {
            s.contextPolicy = TruffleLanguage.ContextPolicy.EXCLUSIVE;
         }
      } else {
         previousLanguageOptions = s.previousLanguageOptions;
         if (!isContextPolicyCompatible(s.contextPolicy, newPolicy)) {
            if (this.engine.getEngineOptionValues().get(PolyglotEngineOptions.TraceCodeSharing)) {
               this.traceClaimLayer(false, s, context, requestingLanguages, previousLanguageOptions);
            }

            return false;
         }

         for (PolyglotLanguage language : previousLanguageOptions.keySet()) {
            if (!newLanguageOptions.containsKey(language)) {
               newLanguageOptions.put(language, context.config.getLanguageOptionValues(language));
            }
         }

         if (!areLanguageOptionsCompatible(s, previousLanguageOptions, newLanguageOptions)) {
            if (this.engine.getEngineOptionValues().get(PolyglotEngineOptions.TraceCodeSharing)) {
               this.traceClaimLayer(false, s, context, requestingLanguages, previousLanguageOptions);
            }

            return false;
         }

         s.previousLanguageOptions = newLanguageOptions;
         PolyglotLanguageInstance hostInstance = s.instances[0];

         assert hostInstance != null : "host instance must always be initialized before claiming a shared layer";

         context.getHostContext().patchInstance(hostInstance);
      }

      s.updatePreinitConfig(context.config);

      assert this.shared == null || this.shared == s;

      this.shared = s;
      if (this.isSingleContext()) {
         s.singleContextValue.update(context);
      } else {
         s.singleContextValue.invalidate();
         this.hostLanguage.singleLanguageContext.invalidate();
      }

      s.claimedCount++;
      if (this.engine.getEngineOptionValues().get(PolyglotEngineOptions.TraceCodeSharing)) {
         this.traceClaimLayer(true, s, context, requestingLanguages, previousLanguageOptions);
      }

      return true;
   }

   boolean isSingleContext() {
      PolyglotSharingLayer.Shared s = this.shared;
      return (s == null || s.contextPolicy == TruffleLanguage.ContextPolicy.EXCLUSIVE) && !this.engine.isStoreEngine();
   }

   public void preInitialize() {
      assert Thread.holdsLock(this.engine.lock);

      assert this.engine.isSharingEnabled(null);

      if (this.isClaimed()) {
         PolyglotSharingLayer.Shared s = this.shared;
         PolyglotContextConfig.PreinitConfig preinitConfig = s.preinitConfig;

         assert preinitConfig != null : "preinit config must be initialized";

         Set<PolyglotLanguage> toInitialize = new LinkedHashSet<>();

         for (PolyglotLanguageInstance instance : s.instances) {
            if (instance != null && !instance.language.isHost()) {
               toInitialize.add(instance.language);
            }
         }

         s.preInitializedContext = PolyglotContextImpl.preinitialize(this.engine, preinitConfig, this, toInitialize, false);

         assert s.preInitializedContext.layer.equals(this) : "invalid resulting layer";
      }
   }

   public PolyglotContextImpl loadPreinitializedContext(PolyglotContextConfig config) {
      assert Thread.holdsLock(this.engine.lock);

      assert this.engine.isSharingEnabled(null);

      PolyglotSharingLayer.Shared s = this.shared;
      if (s == null) {
         return null;
      } else {
         PolyglotContextImpl preinitContext = s.preInitializedContext;
         if (preinitContext == null) {
            return null;
         } else {
            Set<PolyglotLanguage> usedLanguages = new LinkedHashSet<>();

            for (PolyglotLanguageInstance instance : s.instances) {
               if (instance != null && !instance.language.isHost()) {
                  usedLanguages.add(instance.language);
               }
            }

            Map<PolyglotLanguage, OptionValuesImpl> newLanguageOptions = this.collectLanguageOptions(config, usedLanguages);
            if (!areLanguageOptionsCompatible(s, s.previousLanguageOptions, newLanguageOptions)) {
               if (this.engine.getEngineOptionValues().get(PolyglotEngineOptions.TraceCodeSharing)) {
                  this.traceContextPreinit(false, s, preinitContext, s.previousLanguageOptions, newLanguageOptions);
               }

               return null;
            } else {
               if (this.engine.getEngineOptionValues().get(PolyglotEngineOptions.TraceCodeSharing)) {
                  this.traceContextPreinit(true, s, preinitContext, s.previousLanguageOptions, newLanguageOptions);
               }

               assert s.preInitializedContext == preinitContext : "must only be mutated while engine lock is held";

               s.preInitializedContext = null;
               return preinitContext;
            }
         }
      }
   }

   public void freeSharingLayer(PolyglotContextImpl context) {
      assert Thread.holdsLock(this.engine.lock);

      assert this.isClaimed();

      this.shared.claimedCount--;
      if (this.engine.getEngineOptionValues().get(PolyglotEngineOptions.TraceCodeSharing)) {
         this.traceFreeLayer(context);
      }
   }

   public PolyglotLanguageInstance allocateHostLanguage(PolyglotLanguage language) {
      assert !this.isClaimed();

      assert this.hostLanguage == null : "host language allocated twice";

      this.hostLanguage = language.createInstance(this);
      return this.hostLanguage;
   }

   public PolyglotLanguageInstance allocateInstance(PolyglotContextImpl context, PolyglotLanguage language) {
      assert Thread.holdsLock(this.engine.lock);

      assert this.isClaimed() : "allocateInstance before claim";

      assert !language.isHost() : "not host language";

      PolyglotContextConfig config = context.config;
      PolyglotSharingLayer.Shared s = this.shared;
      TruffleLanguage.ContextPolicy layerPolicy = s.contextPolicy;
      TruffleLanguage.ContextPolicy languagePolicy;
      if (layerPolicy == TruffleLanguage.ContextPolicy.EXCLUSIVE) {
         assert s.claimedCount <= 1;

         languagePolicy = TruffleLanguage.ContextPolicy.EXCLUSIVE;
      } else {
         languagePolicy = language.cache.getPolicy();
         if (languagePolicy != TruffleLanguage.ContextPolicy.EXCLUSIVE && config != null) {
            OptionValuesImpl values = config.getLanguageOptionValues(language);
            if (!areOptionsCompatible(s, language, values, values)) {
               languagePolicy = TruffleLanguage.ContextPolicy.EXCLUSIVE;
            }
         }
      }

      if (!isContextPolicyCompatible(layerPolicy, languagePolicy)) {
         String id = language.getId();
         String reason;
         String resolution;
         if (!this.engine.boundEngine) {
            reason = String.format("The context was configured with a shared engine but lazily initialized language '%s' does not support sharing. ", id);
            resolution = String.format(
               " To resolve this either: %n - Ensure all languages are known when the context is constructed, by providing all required languages in the Context.newBuilder(\"%s\") method. %n - Avoid lazy initialization of language '%s' by initializing as the first language using Context.initialize(\"%s\"). %n - Disable sharing for the polyglot context by removing the explicit engine configuration with Context.Builder.engine(...).",
               id,
               id,
               id,
               id
            );
         } else if (this.engine.storeEngine) {
            reason = String.format("The engine was configured to be stored but lazily initialized language '%s' does not support storing sharing data. ", id);
            resolution = "";
         } else {
            reason = String.format("The engine was forced to use code sharing but lazily initialized language '%s' does not support sharing. ", id);
            resolution = "";
         }

         throw new PolyglotSharingLayer.SharingLazyInitializationError(
            String.format(
               "%sNon sharable languages cannot be initialized lazily and must be known ahead of time when the context is created. Use the --engine.TraceCodeCache option to print debug details on the sharing decisions.%s",
               reason,
               resolution
            )
         );
      } else {
         PolyglotLanguageInstance instance = s.instances[language.engineIndex];
         if (instance == null) {
            instance = language.createInstance(this);
            s.instances[language.engineIndex] = instance;
            if (!this.isSingleContext()) {
               EngineAccessor.LANGUAGE.initializeMultiContext(instance.spi);
            }

            if (this.engine.getEngineOptionValues().get(PolyglotEngineOptions.TraceCodeSharing)) {
               this.traceAllocateLanguageInstance(context, language);
            }
         }

         return instance;
      }
   }

   public PolyglotSourceCache getSourceCache() {
      assert this.isClaimed() : "source cache access before claim";

      return this.shared.sourceCache;
   }

   public PolyglotLanguageInstance getInstance(PolyglotLanguage language) {
      PolyglotSharingLayer.Shared s = this.shared;
      return s == null ? null : getInstance(s, language);
   }

   private static PolyglotLanguageInstance getInstance(PolyglotSharingLayer.Shared s, PolyglotLanguage language) {
      return s.instances[language.engineIndex];
   }

   public PolyglotContextImpl getSingleConstantContext() {
      if (!CompilerDirectives.inInterpreter() && CompilerDirectives.isPartialEvaluationConstant(this)) {
         PolyglotSharingLayer.Shared s = this.shared;
         return s == null ? null : s.singleContextValue.getConstant();
      } else {
         return null;
      }
   }

   public PolyglotLanguageContext getSingleConstantLanguageContext(PolyglotLanguage language) {
      if (!CompilerDirectives.inInterpreter() && CompilerDirectives.isPartialEvaluationConstant(this)) {
         CompilerAsserts.partialEvaluationConstant(language);
         PolyglotSharingLayer.Shared s = this.shared;
         if (s == null) {
            return null;
         } else {
            PolyglotLanguageInstance instance = s.instances[language.engineIndex];
            CompilerAsserts.partialEvaluationConstant(instance);
            return instance == null ? null : instance.singleLanguageContext.getConstant();
         }
      } else {
         return null;
      }
   }

   public TruffleLanguage.ContextPolicy getContextPolicy() {
      assert this.isClaimed() : "context policy lookup before claim";

      return this.shared.contextPolicy;
   }

   public boolean isClaimed() {
      return this.shared != null;
   }

   @Override
   public boolean equals(Object obj) {
      if (!(obj instanceof PolyglotSharingLayer)) {
         return false;
      } else {
         PolyglotSharingLayer other = (PolyglotSharingLayer)obj;
         return this.engine == other.engine && this.shared == other.shared;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.engine, this.shared);
   }

   @Override
   public String toString() {
      StringBuilder string = new StringBuilder();
      PolyglotSharingLayer.Shared s = this.shared;
      if (s == null) {
         string.append("state=unclaimed");
      } else {
         string.append("state=claimed layer-policy=");
         string.append(s.contextPolicy);
         string.append(" languages=[");
         String sep = "";

         for (PolyglotLanguageInstance instance : s.instances) {
            if (instance != null) {
               string.append(sep);
               string.append(instance.language.getId());
               sep = ", ";
            }
         }

         string.append("]");
      }

      return "PolyglotSharingLayer[" + string + "]";
   }

   private static boolean isContextPolicyCompatible(TruffleLanguage.ContextPolicy prevPolicy, TruffleLanguage.ContextPolicy newPolicy) {
      return prevPolicy.ordinal() <= newPolicy.ordinal();
   }

   private static TruffleLanguage.ContextPolicy computeMinContextPolicyPolicy(Set<PolyglotLanguage> languages) {
      assert !languages.isEmpty() : "cannot compute sharing for empty set of languages";

      TruffleLanguage.ContextPolicy newPolicy = TruffleLanguage.ContextPolicy.SHARED;

      for (PolyglotLanguage language : languages) {
         TruffleLanguage.ContextPolicy policy = language.cache.getPolicy();
         if (policy.ordinal() < newPolicy.ordinal()) {
            newPolicy = policy;
            if (policy == TruffleLanguage.ContextPolicy.EXCLUSIVE) {
               break;
            }
         }
      }

      return newPolicy;
   }

   private static boolean areLanguageOptionsCompatible(
      PolyglotSharingLayer.Shared s, Map<PolyglotLanguage, OptionValuesImpl> oldLanguageOptions, Map<PolyglotLanguage, OptionValuesImpl> newLanguageOptions
   ) {
      for (Entry<PolyglotLanguage, OptionValuesImpl> entry : newLanguageOptions.entrySet()) {
         PolyglotLanguage language = entry.getKey();
         OptionValuesImpl newOptions = entry.getValue();

         assert newOptions != null;

         OptionValuesImpl prevOptions = oldLanguageOptions.get(language);
         if (prevOptions == null) {
            prevOptions = language.getOptionValues();
         }

         if (!areOptionsCompatible(s, language, prevOptions, newOptions)) {
            return false;
         }
      }

      return true;
   }

   private static boolean areOptionsCompatible(
      PolyglotSharingLayer.Shared s, PolyglotLanguage language, OptionValuesImpl previousOptions, OptionValuesImpl newOptions
   ) {
      PolyglotLanguageInstance instance = resolveInstance(s, language);
      return EngineAccessor.LANGUAGE.areOptionsCompatible(instance.spi, previousOptions, newOptions);
   }

   private static PolyglotLanguageInstance resolveInstance(PolyglotSharingLayer.Shared s, PolyglotLanguage language) {
      PolyglotLanguageInstance instance = getInstance(s, language);
      if (instance == null) {
         instance = language.getInitLanguage();
      }

      return instance;
   }

   private Map<PolyglotLanguage, OptionValuesImpl> collectLanguageOptions(PolyglotContextConfig config, Set<PolyglotLanguage> forcedLanguages) {
      Map<PolyglotLanguage, OptionValuesImpl> newOptions = new HashMap<>();
      Set<PolyglotLanguage> languages = config.getConfiguredLanguages();
      if (!languages.containsAll(forcedLanguages)) {
         languages = new HashSet<>(languages);

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
      PolyglotSharingLayer.Shared s = this.shared;
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
         TruffleStackTrace.fillIn(e);
         stack.append(String.format("%n  <<current-context>>"));
         printLayerChange(stack, prev, current);
         if (node != null) {
            RootNode root = node.getRootNode();
            if (root != null) {
               stack.append(String.format("%n  %s(%s)", createJavaStackFrame(root, node.getEncapsulatingSourceSection()), node));
            }
         }

         for (TruffleStackTraceElement stackTrace : TruffleStackTrace.getStackTrace(e)) {
            RootNode root = stackTrace.getTarget().getRootNode();
            current = (PolyglotSharingLayer)EngineAccessor.NODES.getSharingLayer(root);
            printLayerChange(stack, prev, current);
            SourceSection sourceSection = null;
            Node location = stackTrace.getLocation();
            if (location != null) {
               sourceSection = location.getEncapsulatingSourceSection();
            }

            stack.append(String.format("%n  %s", createJavaStackFrame(root, sourceSection)));
            if (current != null) {
               prev = current;
            }
         }
      } catch (Exception var13) {
         exceptionCreating = var13;
      }

      AssertionError error = new AssertionError(
         String.format(
            "Invalid sharing of AST nodes detected. The current context uses a different sharing layer than the executed node. A common cause of this are CallTargets that are reused across different contexts in an invalid way.Stack trace: %s",
            stack.toString()
         )
      );
      if (exceptionCreating != null) {
         error.addSuppressed(exceptionCreating);
      }

      throw error;
   }

   private static void printLayerChange(StringBuilder stack, PolyglotSharingLayer previousLayer, PolyglotSharingLayer newLayer) {
      if (newLayer != null && !Objects.equals(previousLayer, newLayer)) {
         stack.append(
            String.format(
               "%n    <-- Sharing Layer Change: 0x%H => 0x%H -->", System.identityHashCode(previousLayer.shared), System.identityHashCode(newLayer.shared)
            )
         );
      }
   }

   private static StackTraceElement createJavaStackFrame(RootNode root, SourceSection sourceSection) {
      SourceSection sc = sourceSection;
      if (sourceSection == null) {
         sc = root.getSourceSection();
      }

      PolyglotLanguageInstance instance = lookupLanguageInstance(root);
      String language = instance != null ? instance.language.getId() : "Unknown";
      String rootName = root.getName();
      String declaringClass = "<" + language + ">";
      String methodName = rootName == null ? "" : rootName;
      String fileName = sc != null ? sc.getSource().getName() : "Unknown";
      int startLine = sc != null ? sc.getStartLine() : -1;
      return new StackTraceElement(declaringClass, methodName, fileName, startLine);
   }

   private static PolyglotLanguageInstance lookupLanguageInstance(RootNode root) {
      TruffleLanguage<?> spi = EngineAccessor.NODES.getLanguage(root);
      return spi != null ? (PolyglotLanguageInstance)EngineAccessor.LANGUAGE.getPolyglotLanguageInstance(spi) : null;
   }

   private void traceContextPreinit(
      boolean success,
      PolyglotSharingLayer.Shared s,
      PolyglotContextImpl context,
      Map<PolyglotLanguage, OptionValuesImpl> previousOptions,
      Map<PolyglotLanguage, OptionValuesImpl> newLanguageOptions
   ) {
      this.trace(
         context,
         s,
         "loading pre-init",
         String.format("claimedCount:%s sharingEnabled:%s ", success ? s.claimedCount - 1 : s.claimedCount, this.engine.isSharingEnabled(context.config))
      );

      for (Entry<PolyglotLanguage, OptionValuesImpl> entry : newLanguageOptions.entrySet()) {
         this.traceCompatibility(s, context, previousOptions, entry);
      }

      this.trace(context, s, success ? "loaded" : "failed to load pre-init", "");
   }

   private void traceClaimLayer(
      boolean success,
      PolyglotSharingLayer.Shared s,
      PolyglotContextImpl context,
      Set<PolyglotLanguage> requestingLangauges,
      Map<PolyglotLanguage, OptionValuesImpl> previousOptions
   ) {
      this.trace(
         context,
         s,
         "claiming",
         String.format("claimedCount:%s sharingEnabled:%s ", success ? s.claimedCount - 1 : s.claimedCount, this.engine.isSharingEnabled(context.config))
      );
      Map<PolyglotLanguage, OptionValuesImpl> newLanguageOptions = this.collectLanguageOptions(context.config, requestingLangauges);

      for (Entry<PolyglotLanguage, OptionValuesImpl> entry : newLanguageOptions.entrySet()) {
         this.traceCompatibility(s, context, previousOptions, entry);
      }

      this.trace(context, s, success ? "claimed" : "failed to claim", String.format("claimedCount:%s layer-policy:%s", s.claimedCount, s.contextPolicy));
   }

   private void traceCompatibility(
      PolyglotSharingLayer.Shared s,
      PolyglotContextImpl context,
      Map<PolyglotLanguage, OptionValuesImpl> previousOptions,
      Entry<PolyglotLanguage, OptionValuesImpl> entry
   ) {
      StringBuilder languageInfos = new StringBuilder();
      PolyglotLanguage language = entry.getKey();
      TruffleLanguage.ContextPolicy policy = language.cache.getPolicy();
      languageInfos.append(String.format("%s registration-policy:%s  ", language.getId(), policy));
      boolean optionsCompatible = isContextPolicyCompatible(s.contextPolicy, policy);
      if (optionsCompatible && this.engine.isSharingEnabled(context.config)) {
         OptionValuesImpl newOptions = entry.getValue();
         OptionValuesImpl prevOptions = previousOptions != null ? previousOptions.get(language) : newOptions;
         if (prevOptions == null) {
            prevOptions = language.getOptionValues();
         }

         optionsCompatible = areOptionsCompatible(s, language, prevOptions, newOptions);
         languageInfos.append(
            String.format(
               "%s.areOptionsCompatibleWith(%s, %s) == %s",
               resolveInstance(s, language).spi.getClass().getSimpleName(),
               prevOptions,
               newOptions,
               optionsCompatible
            )
         );
      }

      this.trace(context, s, optionsCompatible ? "  compatible" : "  incompatible", languageInfos.toString());
   }

   private void traceFreeLayer(PolyglotContextImpl context) {
      this.trace(context, this.shared, "freed", String.format("claimedCount:%s", context.layer.shared.claimedCount));
   }

   private void traceAllocateLanguageInstance(PolyglotContextImpl context, PolyglotLanguage language) {
      this.trace(context, this.shared, "created language", String.format("%s for policy %s", language.getId(), this.shared.contextPolicy));
   }

   private void trace(PolyglotContextImpl context, PolyglotSharingLayer.Shared s, String label, String message) {
      this.engine
         .getEngineLogger()
         .info(
            String.format(
               "[sharing] engine 0x%8H context 0x%8H layer 0x%8H: %-20s %s", this.engine.hashCode(), Objects.hash(context), s.hashCode(), label, message
            )
         );
   }

   static final class Shared {
      final PolyglotSourceCache sourceCache = new PolyglotSourceCache();
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private final PolyglotLanguageInstance[] instances;
      @CompilerDirectives.CompilationFinal
      TruffleLanguage.ContextPolicy contextPolicy;
      Map<PolyglotLanguage, OptionValuesImpl> previousLanguageOptions;
      final WeakAssumedValue<PolyglotContextImpl> singleContextValue = new WeakAssumedValue<>("single context");
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
         PolyglotContextConfig.PreinitConfig newConfig;
         if (prev == null) {
            newConfig = new PolyglotContextConfig.PreinitConfig(config);
         } else {
            newConfig = new PolyglotContextConfig.PreinitConfig(prev, config);
         }

         this.preinitConfig = newConfig;
      }
   }

   static final class SharingLazyInitializationError extends AbstractTruffleException {
      SharingLazyInitializationError(String message) {
         super(message);
      }
   }
}
