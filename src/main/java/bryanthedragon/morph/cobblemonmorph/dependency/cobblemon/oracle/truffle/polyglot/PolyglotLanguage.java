package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.LanguageInfo;
import java.util.Set;
import org.graalvm.options.OptionDescriptors;
import org.graalvm.polyglot.Language;

final class PolyglotLanguage implements PolyglotImpl.VMObject {
   final PolyglotEngineImpl engine;
   final LanguageCache cache;
   final LanguageInfo info;
   Language api;
   final int engineIndex;
   final RuntimeException initError;
   private volatile OptionDescriptors options;
   private volatile OptionValuesImpl optionValues;
   private volatile boolean initialized;
   private volatile PolyglotLanguageInstance initLanguage;
   private volatile boolean firstInstance = true;
   @CompilerDirectives.CompilationFinal
   volatile Class<?> contextClass;
   volatile PolyglotLocals.LocalLocation[] previousContextLocalLocations;
   volatile PolyglotLocals.LocalLocation[] previousContextThreadLocalLocations;

   PolyglotLanguage(PolyglotEngineImpl engine, LanguageCache cache, int engineIndex, RuntimeException initError) {
      this.engine = engine;
      this.cache = cache;
      this.initError = initError;
      this.engineIndex = engineIndex;
      this.info = EngineAccessor.NODES
         .createLanguage(
            cache,
            cache.getId(),
            cache.getName(),
            cache.getVersion(),
            cache.getDefaultMimeType(),
            cache.getMimeTypes(),
            cache.isInternal(),
            cache.isInteractive()
         );
   }

   PolyglotLanguageContext getCurrentLanguageContext() {
      return PolyglotContextImpl.requireContext().contexts[this.engineIndex];
   }

   boolean isFirstInstance() {
      return this.firstInstance;
   }

   void initializeContextClass(Object contextImpl) {
      CompilerAsserts.neverPartOfCompilation();
      Class<?> newClass = contextImpl == null ? Void.class : contextImpl.getClass();
      Class<?> currentClass = this.contextClass;
      if (currentClass == null) {
         this.contextClass = newClass;
      } else if (currentClass != newClass) {
         throw new IllegalStateException(String.format("Unstable context class expected %s got %s.", newClass, currentClass));
      }
   }

   boolean dependsOn(PolyglotLanguage otherLanguage) {
      Set<String> dependentLanguages = this.cache.getDependentLanguages();
      if (dependentLanguages.contains(otherLanguage.getId())) {
         return true;
      } else {
         for (String dependentLanguage : dependentLanguages) {
            PolyglotLanguage dependentLanguageObj = this.engine.idToLanguage.get(dependentLanguage);
            if (dependentLanguageObj != null && dependentLanguageObj.dependsOn(otherLanguage)) {
               return true;
            }
         }

         return false;
      }
   }

   boolean isHost() {
      return this.engineIndex == 0;
   }

   public OptionDescriptors getOptions() {
      try {
         this.engine.checkState();
         return this.getOptionsInternal();
      } catch (Throwable var2) {
         throw PolyglotImpl.guestToHostException(this.engine, var2);
      }
   }

   OptionDescriptors getOptionsInternal() {
      this.ensureInitialized();
      return this.options;
   }

   private void ensureInitialized() {
      if (!this.initialized) {
         synchronized (this.engine.lock) {
            if (!this.initialized) {
               this.ensureInitialized(this.getInitLanguage());
               this.initialized = true;
            }
         }
      }
   }

   PolyglotLanguageInstance getInitLanguage() {
      assert Thread.holdsLock(this.engine.lock);

      if (this.initLanguage == null) {
         this.initLanguage = new PolyglotLanguageInstance(this, null);
      }

      return this.initLanguage;
   }

   PolyglotLanguageInstance createInstance(PolyglotSharingLayer sharing) {
      assert Thread.holdsLock(this.engine.lock);

      if (this.firstInstance) {
         this.firstInstance = false;
      }

      PolyglotLanguageInstance instance = null;
      if (this.initLanguage != null) {
         instance = this.initLanguage;
         instance.sharing = sharing;
         this.initLanguage = null;
      }

      if (instance == null) {
         instance = new PolyglotLanguageInstance(this, sharing);
         this.ensureInitialized(instance);
      }

      return instance;
   }

   @Override
   public PolyglotEngineImpl getEngine() {
      return this.engine;
   }

   private void ensureInitialized(PolyglotLanguageInstance instance) {
      if (!this.initialized) {
         synchronized (this.engine.lock) {
            if (!this.initialized) {
               try {
                  this.options = EngineAccessor.LANGUAGE.describeOptions(instance.spi, this.cache.getId());
               } catch (Exception var5) {
                  throw new IllegalStateException(
                     String.format("Error initializing language '%s' using class '%s'.", this.cache.getId(), this.cache.getClassName()), var5
                  );
               }

               this.initialized = true;
            }
         }
      }
   }

   OptionValuesImpl getOptionValues() {
      if (this.optionValues == null) {
         synchronized (this.engine.lock) {
            if (this.optionValues == null) {
               this.optionValues = new OptionValuesImpl(this.getOptionsInternal(), false);
            }
         }
      }

      return this.optionValues;
   }

   OptionValuesImpl getOptionValuesIfExists() {
      return this.optionValues;
   }

   public String getDefaultMimeType() {
      return this.cache.getDefaultMimeType();
   }

   void clearOptionValues() {
      this.optionValues = null;
   }

   public String getName() {
      return this.cache.getName();
   }

   public String getImplementationName() {
      return this.cache.getImplementationName();
   }

   public boolean isInteractive() {
      return this.cache.isInteractive();
   }

   public Set<String> getMimeTypes() {
      return this.cache.getMimeTypes();
   }

   public String getVersion() {
      String version = this.cache.getVersion();
      return version.equals("inherit") ? this.engine.getVersion() : version;
   }

   public String getId() {
      return this.cache.getId();
   }

   @Override
   public String toString() {
      return "PolyglotLanguage [id=" + this.getId() + ", name=" + this.getName() + ", host=" + this.isHost() + "]";
   }

   boolean assertCorrectEngine() {
      PolyglotContextImpl context = PolyglotContextImpl.requireContext();
      PolyglotLanguageContext languageContext = context.getContext(this);
      if (languageContext.isInitialized() && languageContext.language.engine != this.engine) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw CompilerDirectives.shouldNotReachHere(
            String.format(
               "Context reference was used from an Engine that is currently not entered. ContextReference of engine %s was used but engine %s is currently entered. ContextReference must not be shared between multiple Engine instances.",
               languageContext.language.engine,
               this.engine
            )
         );
      } else {
         return true;
      }
   }

   String getWebsite() {
      return this.cache.getWebsite();
   }
}
