package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.nodes.RootNode;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import org.graalvm.collections.Pair;

final class PolyglotLanguageInstance implements PolyglotImpl.VMObject {
   final PolyglotLanguage language;
   final TruffleLanguage<Object> spi;
   @CompilerDirectives.CompilationFinal
   PolyglotSharingLayer sharing;
   private final Map<Class<?>, PolyglotValueDispatch> valueCache;
   private final Map<Class<?>, CallTarget> callTargetCache;
   final Map<Object, Object> hostToGuestCodeCache = new ConcurrentHashMap<>();
   final Map<Class<?>, ClassLoader> staticObjectClassLoaders = new ConcurrentHashMap<>();
   final ConcurrentHashMap<Pair<Class<?>, Class<?>>, Object> generatorCache = new ConcurrentHashMap<>();
   final WeakAssumedValue<PolyglotLanguageContext> singleLanguageContext = new WeakAssumedValue<>("single language context");
   List<PolyglotLocals.LanguageContextLocal<?>> contextLocals;
   List<PolyglotLocals.LanguageContextThreadLocal<?>> contextThreadLocals;
   PolyglotLocals.LocalLocation[] contextLocalLocations;
   PolyglotLocals.LocalLocation[] contextThreadLocalLocations;
   @CompilerDirectives.CompilationFinal
   private volatile Object guestToHostCodeCache;
   private static final AtomicReferenceFieldUpdater<PolyglotLanguageInstance, Object> GUEST_TO_HOST_CODE_CACHE_UPDATER = AtomicReferenceFieldUpdater.newUpdater(
      PolyglotLanguageInstance.class, Object.class, "guestToHostCodeCache"
   );

   PolyglotLanguageInstance(PolyglotLanguage language, PolyglotSharingLayer layer) {
      this.language = language;
      this.sharing = layer;
      this.valueCache = new ConcurrentHashMap<>();
      this.callTargetCache = new ConcurrentHashMap<>();

      try {
         this.spi = (TruffleLanguage<Object>)language.cache.loadLanguage();
         EngineAccessor.LANGUAGE.initializeLanguage(this.spi, language.info, language, language.isHost() ? null : this);
      } catch (Exception var4) {
         throw new IllegalStateException(
            String.format("Error initializing language '%s' using class '%s'.", language.cache.getId(), language.cache.getClassName()), var4
         );
      }

      PolyglotValueDispatch.createDefaultValues(this.getImpl(), this, this.valueCache);
   }

   CallTarget lookupCallTarget(Class<? extends RootNode> rootNodeClass) {
      return this.callTargetCache.get(rootNodeClass);
   }

   CallTarget installCallTarget(RootNode rootNode) {
      return this.callTargetCache.computeIfAbsent(rootNode.getClass(), r -> rootNode.getCallTarget());
   }

   @Override
   public PolyglotEngineImpl getEngine() {
      return this.language.engine;
   }

   PolyglotValueDispatch lookupValueCache(PolyglotContextImpl context, Object guestValue) {
      PolyglotValueDispatch cache = this.valueCache.get(guestValue.getClass());
      if (cache == null) {
         Object prev = this.language.engine.enterIfNeeded(context, true);

         try {
            cache = this.lookupValueCacheImpl(guestValue);
         } finally {
            this.language.engine.leaveIfNeeded(prev, context);
         }
      }

      assert Objects.equals(cache.languageInstance.sharing, this.sharing) : PolyglotSharingLayer.invalidSharingError(
         null, cache.languageInstance.sharing, this.sharing
      );

      return cache;
   }

   private synchronized PolyglotValueDispatch lookupValueCacheImpl(Object guestValue) {
      return this.valueCache.computeIfAbsent(guestValue.getClass(), new Function<Class<?>, PolyglotValueDispatch>() {
         public PolyglotValueDispatch apply(Class<?> t) {
            return PolyglotValueDispatch.createInteropValue(PolyglotLanguageInstance.this, (TruffleObject)guestValue, guestValue.getClass());
         }
      });
   }

   Object getGuestToHostCodeCache() {
      return this.guestToHostCodeCache;
   }

   Object installGuestToHostCodeCache(Object newCache) {
      return GUEST_TO_HOST_CODE_CACHE_UPDATER.compareAndSet(this, null, newCache) ? newCache : this.guestToHostCodeCache;
   }

   @Override
   public String toString() {
      return "PolyglotLanguageInstance[" + this.spi + "]";
   }
}
