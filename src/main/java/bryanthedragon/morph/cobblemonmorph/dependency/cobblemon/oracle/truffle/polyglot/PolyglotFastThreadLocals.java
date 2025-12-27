package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.impl.AbstractFastThreadLocal;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

final class PolyglotFastThreadLocals {
   private static final AbstractFastThreadLocal IMPL = EngineAccessor.RUNTIME.getContextThreadLocal();
   private static final ConcurrentHashMap<List<EngineAccessor.AbstractClassLoaderSupplier>, Map<String, LanguageCache>> CLASS_NAME_CACHE = new ConcurrentHashMap<>();
   private static final ConcurrentHashMap<Class<?>, PolyglotFastThreadLocals.CachedReferences> CONTEXT_REFERENCE_CACHE = new ConcurrentHashMap<>();
   private static final FinalIntMap LANGUAGE_INDEXES = new FinalIntMap();
   private static final int RESERVED_NULL = -1;
   private static final int THREAD_INDEX = 0;
   static final int CONTEXT_INDEX = 1;
   private static final int ENCAPSULATING_NODE_REFERENCE_INDEX = 2;
   private static final int LANGUAGE_START = 3;
   static final int LANGUAGE_CONTEXT_OFFSET = 0;
   static final int LANGUAGE_SPI_OFFSET = 1;
   private static final int LANGUAGE_ELEMENTS = 2;

   static void resetNativeImageState() {
      CONTEXT_REFERENCE_CACHE.clear();
      CLASS_NAME_CACHE.clear();
   }

   static Object[] createFastThreadLocals(PolyglotThreadInfo thread) {
      PolyglotContextImpl context = thread.context;

      assert Thread.holdsLock(context);

      Object[] data = new Object[3 + thread.context.engine.languages.length * 2];
      data[0] = thread;
      data[1] = thread.context;
      data[2] = EngineAccessor.NODES.createEncapsulatingNodeReference(thread.getThread());

      for (PolyglotLanguageContext languageContext : thread.context.contexts) {
         if (languageContext.isCreated()) {
            updateLanguageObjects(data, languageContext);
         }
      }

      return data;
   }

   private static Object[] createFastThreadLocalsForLanguage(PolyglotLanguageInstance instance) {
      Object[] data = new Object[3 + instance.language.engine.languages.length * 2];
      data[0] = null;
      data[1] = null;
      data[getLanguageIndex(instance) + 1] = instance.spi;
      return data;
   }

   private static int getLanguageIndex(PolyglotLanguageInstance instance) {
      return 3 + instance.language.cache.getStaticIndex() * 2;
   }

   public static <C extends TruffleLanguage<?>> TruffleLanguage.LanguageReference<C> createLanguageReference(Class<? extends TruffleLanguage<?>> language) {
      return lookupReferences(language).languageReference;
   }

   public static <C> TruffleLanguage.ContextReference<C> createContextReference(Class<? extends TruffleLanguage<C>> language) {
      return lookupReferences(language).contextReference;
   }

   public static boolean needsEnter(PolyglotContextImpl context) {
      return IMPL.fastGet(1, PolyglotContextImpl.class, false) != context;
   }

   public static Object[] enter(PolyglotThreadInfo threadInfo) {
      Object[] prev = IMPL.get();
      IMPL.set(threadInfo.fastThreadLocals);
      return prev;
   }

   public static void leave(Object[] prev) {
      IMPL.set(prev);
   }

   public static Object enterLanguage(PolyglotLanguageInstance language) {
      Object[] prev = IMPL.get();
      IMPL.set(createFastThreadLocalsForLanguage(language));
      return prev;
   }

   public static void leaveLanguage(PolyglotLanguageInstance instance, Object prev) {
      assert IMPL.get()[getLanguageIndex(instance) + 1] != null : "language not entered";

      IMPL.set((Object[])prev);
   }

   public static void cleanup(Object[] threadLocals) {
      Arrays.fill(threadLocals, null);
   }

   static EncapsulatingNodeReference getEncapsulatingNodeReference(boolean invalidateOnNull) {
      return IMPL.fastGet(2, EncapsulatingNodeReference.class, invalidateOnNull);
   }

   public static PolyglotThreadInfo getCurrentThread(PolyglotSharingLayer layer) {
      if (CompilerDirectives.inCompiledCode() && layer != null) {
         PolyglotContextImpl singleContext = layer.getSingleConstantContext();
         if (singleContext != null && CompilerDirectives.isPartialEvaluationConstant(singleContext)) {
            PolyglotThreadInfo constantThread = singleContext.singleThreadValue.getConstant();
            if (constantThread != null) {
               return constantThread;
            }
         }
      }

      return IMPL.fastGet(0, PolyglotThreadInfo.class, true);
   }

   public static PolyglotThreadInfo getCurrentThreadEngine(PolyglotEngineImpl engine) {
      if (CompilerDirectives.inCompiledCode() && engine != null) {
         PolyglotContextImpl singleContext = engine.singleContextValue.getConstant();
         if (singleContext != null) {
            PolyglotThreadInfo constantThread = singleContext.singleThreadValue.getConstant();
            if (constantThread != null) {
               return constantThread;
            }
         }
      }

      return IMPL.fastGet(0, PolyglotThreadInfo.class, true);
   }

   public static PolyglotContextImpl getContext(PolyglotSharingLayer layer) {
      if (CompilerDirectives.inCompiledCode() && layer != null) {
         PolyglotContextImpl value = layer.getSingleConstantContext();
         if (value != null) {
            return value;
         }
      }

      return IMPL.fastGet(1, PolyglotContextImpl.class, true);
   }

   public static PolyglotContextImpl getContextWithEngine(PolyglotEngineImpl engine) {
      if (CompilerDirectives.inCompiledCode() && engine != null) {
         PolyglotContextImpl context = engine.singleContextValue.getConstant();
         if (context != null) {
            return context;
         }
      }

      return IMPL.fastGet(1, PolyglotContextImpl.class, true);
   }

   public static PolyglotContextImpl getContextWithNode(Node node) {
      if (CompilerDirectives.inCompiledCode()) {
         PolyglotSharingLayer layer = resolveLayer(node);
         if (layer != null) {
            return layer.getSingleConstantContext();
         }
      }

      return IMPL.fastGet(1, PolyglotContextImpl.class, true);
   }

   public static TruffleLanguage<Object> getLanguage(Node node, int index, Class<?> languageClass) {
      assert validSharing(node);

      if (CompilerDirectives.inCompiledCode()) {
         PolyglotLanguageInstance instance = resolveLanguageInstance(node, index);
         if (instance != null) {
            return instance.spi;
         }
      }

      return IMPL.fastGet(index, (Class<TruffleLanguage<Object>>)languageClass, true);
   }

   public static Object getLanguageContext(Node node, int index) {
      assert validSharing(node);

      Class<?> contextClass = null;
      if (CompilerDirectives.inCompiledCode()) {
         PolyglotLanguageInstance instance = resolveLanguageInstance(node, index);
         if (instance != null) {
            PolyglotLanguageContext languageContext = instance.singleLanguageContext.getConstant();
            if (languageContext != null) {
               return languageContext.getContextImpl();
            }
         }

         contextClass = findContextClass(node, index);
      }

      return IMPL.fastGet(index, contextClass, true);
   }

   private static boolean validSharing(Node node) {
      PolyglotContextImpl currentContext = getContext(null);
      if (currentContext == null) {
         return true;
      } else {
         PolyglotSharingLayer astLayer = resolveLayer(node);
         if (astLayer == null) {
            return true;
         } else {
            PolyglotSharingLayer currentLayer = currentContext.layer;
            if (!Objects.equals(astLayer, currentLayer)) {
               throw PolyglotSharingLayer.invalidSharingError(node, astLayer, currentLayer);
            } else {
               return true;
            }
         }
      }
   }

   private static PolyglotFastThreadLocals.CachedReferences lookupReferences(Class<? extends TruffleLanguage<?>> language) {
      return CONTEXT_REFERENCE_CACHE.computeIfAbsent(language, c -> new PolyglotFastThreadLocals.CachedReferences((Class<?>)c));
   }

   static void notifyLanguageCreated(PolyglotLanguageContext languageContext) {
      assert Thread.holdsLock(languageContext.context);

      for (PolyglotThreadInfo threadInfo : languageContext.context.getSeenThreads().values()) {
         updateLanguageObjects(threadInfo.fastThreadLocals, languageContext);
      }
   }

   private static void updateLanguageObjects(Object[] data, PolyglotLanguageContext languageContext) {
      PolyglotLanguageInstance instance = languageContext.getLanguageInstance();
      int languageIndex = getLanguageIndex(instance);

      assert languageIndex + 2 - 1 < data.length : "unexpected fast thread local state";

      data[languageIndex + 0] = languageContext.getContextImpl();
      data[languageIndex + 1] = instance.spi;
   }

   private static PolyglotLanguageInstance resolveLanguageInstance(Node node, int index) {
      CompilerAsserts.partialEvaluationConstant(index);
      if (!CompilerDirectives.isPartialEvaluationConstant(node)) {
         return null;
      } else if (node == null) {
         return null;
      } else {
         RootNode root = node.getRootNode();
         if (root == null) {
            return null;
         } else {
            PolyglotSharingLayer layer = (PolyglotSharingLayer)EngineAccessor.NODES.getSharingLayer(root);
            if (layer == null) {
               return null;
            } else {
               int languageIndex = resolveLanguageIndex(index);
               return layer.getInstance(layer.engine.languages[languageIndex]);
            }
         }
      }
   }

   private static int computeLanguageIndex(Class<?> languageClass, int offset) {
      List<EngineAccessor.AbstractClassLoaderSupplier> loaders = EngineAccessor.locatorOrDefaultLoaders();
      int staticIndex;
      if (EngineAccessor.HOST.isHostLanguage(languageClass)) {
         staticIndex = 0;
      } else {
         Map<String, LanguageCache> classNames = CLASS_NAME_CACHE.get(loaders);
         if (classNames == null) {
            Map<String, LanguageCache> var8 = new HashMap();
            Map<String, LanguageCache> idToLanguage = LanguageCache.loadLanguages(loaders);

            for (LanguageCache cache : idToLanguage.values()) {
               var8.put(cache.getClassName(), cache);
            }

            classNames = CLASS_NAME_CACHE.computeIfAbsent(loaders, k -> Collections.unmodifiableMap(var8));
         }

         LanguageCache cache = classNames.get(languageClass.getName());
         if (cache == null) {
            return -1;
         }

         staticIndex = cache.getStaticIndex();

         assert staticIndex <= LanguageCache.getMaxStaticIndex() : "invalid sharing between class loaders";
      }

      return 3 + staticIndex * 2 + offset;
   }

   private static int resolveLanguageIndex(int index) {
      if (index >= 3 && index < 3 + (LanguageCache.getMaxStaticIndex() + 1) * 2) {
         return Math.floorDiv(index - 3, 2);
      } else {
         throw CompilerDirectives.shouldNotReachHere("invalid fast thread local index");
      }
   }

   static int computePELanguageIndex(Class<? extends TruffleLanguage<?>> languageClass, int offset) {
      int indexValue = LANGUAGE_INDEXES.get(languageClass);
      if (indexValue == -1) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         synchronized (LANGUAGE_INDEXES) {
            indexValue = LANGUAGE_INDEXES.get(languageClass);
            if (indexValue == -1) {
               indexValue = computeLanguageIndex(languageClass, 0);
               LANGUAGE_INDEXES.put(languageClass, indexValue);
            }
         }
      }

      return indexValue + offset;
   }

   protected static PolyglotSharingLayer resolveLayer(Node node) {
      if (!CompilerDirectives.isPartialEvaluationConstant(node)) {
         return null;
      } else if (node == null) {
         return null;
      } else {
         RootNode root = node.getRootNode();
         return root == null ? null : (PolyglotSharingLayer)EngineAccessor.NODES.getSharingLayer(root);
      }
   }

   private static PolyglotEngineImpl resolveEngine(Node node) {
      PolyglotSharingLayer layer = resolveLayer(node);
      return layer != null ? layer.engine : null;
   }

   private static PolyglotLanguage findLanguage(Node node, int index) {
      PolyglotEngineImpl engine = resolveEngine(node);
      if (engine == null) {
         return null;
      } else {
         int languageIndex = resolveLanguageIndex(index);
         return languageIndex > engine.languages.length ? null : engine.languages[languageIndex];
      }
   }

   private static <C> Class<C> findContextClass(Node node, int index) {
      if (index == -1) {
         return null;
      } else {
         PolyglotLanguage language = findLanguage(node, index);
         CompilerAsserts.partialEvaluationConstant(language);
         Class<C> targetClass = null;
         if (language != null) {
            targetClass = (Class<C>)language.contextClass;
         }

         return targetClass;
      }
   }

   static boolean assertValidGet(int index, int expectedOffset, Class<?> expectedType, Class<?> languageClass) {
      if (index == -1) {
         throw new IllegalArgumentException("Language " + languageClass + " not installed but used.");
      } else {
         Object[] data = IMPL.get();

         assert data != null : "No polyglot context is entered. A language or context reference must not be used if there is no polyglot context entered.";

         assert index >= 3 && index < 3 + (LanguageCache.getMaxStaticIndex() + 1) * 2 : "Invalid internal language index range";

         assert (index - 3) % 2 == expectedOffset : "Invalid internal language index offset";

         Object value = data[index];

         assert value == null || expectedType == null || expectedType.isInstance(value) : "Invalid type in internal state.";

         return true;
      }
   }

   static final class CachedReferences {
      final PolyglotFastThreadLocals.ContextReferenceImpl contextReference;
      final PolyglotFastThreadLocals.LanguageReferenceImpl languageReference;

      CachedReferences(Class<?> languageClass) {
         this.contextReference = new PolyglotFastThreadLocals.ContextReferenceImpl(languageClass);
         this.languageReference = new PolyglotFastThreadLocals.LanguageReferenceImpl(languageClass);
      }
   }

   static final class ContextReferenceImpl extends TruffleLanguage.ContextReference<Object> {
      private final Class<?> languageClass;
      private final int index;

      ContextReferenceImpl(Class<?> languageClass) {
         this.languageClass = languageClass;
         this.index = PolyglotFastThreadLocals.computeLanguageIndex(languageClass, 0);
      }

      @Override
      public Object get(Node node) {
         assert PolyglotFastThreadLocals.assertValidGet(this.index, 0, PolyglotFastThreadLocals.findContextClass(node, this.index), this.languageClass);

         return PolyglotFastThreadLocals.getLanguageContext(node, this.index);
      }

      @Override
      public String toString() {
         return "ContextReference[language=" + this.languageClass + ", index = " + this.index + "]";
      }
   }

   static final class LanguageReferenceImpl extends TruffleLanguage.LanguageReference<TruffleLanguage<Object>> {
      private final Class<TruffleLanguage<Object>> languageClass;
      private final int index;

      LanguageReferenceImpl(Class<?> languageClass) {
         this.languageClass = (Class<TruffleLanguage<Object>>)languageClass;
         this.index = PolyglotFastThreadLocals.computeLanguageIndex(languageClass, 1);
      }

      @Override
      public TruffleLanguage<Object> get(Node node) {
         assert PolyglotFastThreadLocals.assertValidGet(this.index, 1, this.languageClass, this.languageClass);

         return PolyglotFastThreadLocals.getLanguage(node, this.index, this.languageClass);
      }

      @Override
      public String toString() {
         return "LanguageReference[language=" + this.languageClass + ", index = " + this.index + "]";
      }
   }
}
