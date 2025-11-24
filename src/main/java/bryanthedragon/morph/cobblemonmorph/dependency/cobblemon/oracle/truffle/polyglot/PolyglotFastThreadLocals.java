
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.impl.AbstractFastThreadLocal;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.polyglot.EngineAccessor;
import com.oracle.truffle.polyglot.FinalIntMap;
import com.oracle.truffle.polyglot.LanguageCache;
import com.oracle.truffle.polyglot.PolyglotContextImpl;
import com.oracle.truffle.polyglot.PolyglotEngineImpl;
import com.oracle.truffle.polyglot.PolyglotLanguage;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import com.oracle.truffle.polyglot.PolyglotLanguageInstance;
import com.oracle.truffle.polyglot.PolyglotSharingLayer;
import com.oracle.truffle.polyglot.PolyglotThreadInfo;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

final class PolyglotFastThreadLocals {
    private static final AbstractFastThreadLocal IMPL = EngineAccessor.RUNTIME.getContextThreadLocal();
    private static final ConcurrentHashMap<List<EngineAccessor.AbstractClassLoaderSupplier>, Map<String, LanguageCache>> CLASS_NAME_CACHE = new ConcurrentHashMap();
    private static final ConcurrentHashMap<Class<?>, CachedReferences> CONTEXT_REFERENCE_CACHE = new ConcurrentHashMap();
    private static final FinalIntMap LANGUAGE_INDEXES = new FinalIntMap();
    private static final int RESERVED_NULL = -1;
    private static final int THREAD_INDEX = 0;
    static final int CONTEXT_INDEX = 1;
    private static final int ENCAPSULATING_NODE_REFERENCE_INDEX = 2;
    private static final int LANGUAGE_START = 3;
    static final int LANGUAGE_CONTEXT_OFFSET = 0;
    static final int LANGUAGE_SPI_OFFSET = 1;
    private static final int LANGUAGE_ELEMENTS = 2;

    PolyglotFastThreadLocals() {
    }

    static void resetNativeImageState() {
        CONTEXT_REFERENCE_CACHE.clear();
        CLASS_NAME_CACHE.clear();
    }

    static Object[] createFastThreadLocals(PolyglotThreadInfo thread) {
        PolyglotContextImpl context = thread.context;
        assert (Thread.holdsLock(context));
        Object[] data = new Object[3 + thread.context.engine.languages.length * 2];
        data[0] = thread;
        data[1] = thread.context;
        data[2] = EngineAccessor.NODES.createEncapsulatingNodeReference(thread.getThread());
        for (PolyglotLanguageContext languageContext : thread.context.contexts) {
            if (!languageContext.isCreated()) continue;
            PolyglotFastThreadLocals.updateLanguageObjects(data, languageContext);
        }
        return data;
    }

    private static Object[] createFastThreadLocalsForLanguage(PolyglotLanguageInstance instance) {
        Object[] data = new Object[3 + instance.language.engine.languages.length * 2];
        data[0] = null;
        data[1] = null;
        data[PolyglotFastThreadLocals.getLanguageIndex((PolyglotLanguageInstance)instance) + 1] = instance.spi;
        return data;
    }

    private static int getLanguageIndex(PolyglotLanguageInstance instance) {
        return 3 + instance.language.cache.getStaticIndex() * 2;
    }

    public static <C extends TruffleLanguage<?>> TruffleLanguage.LanguageReference<C> createLanguageReference(Class<? extends TruffleLanguage<?>> language) {
        return PolyglotFastThreadLocals.lookupReferences(language).languageReference;
    }

    public static <C> TruffleLanguage.ContextReference<C> createContextReference(Class<? extends TruffleLanguage<C>> language) {
        return PolyglotFastThreadLocals.lookupReferences(language).contextReference;
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
        IMPL.set(PolyglotFastThreadLocals.createFastThreadLocalsForLanguage(language));
        return prev;
    }

    public static void leaveLanguage(PolyglotLanguageInstance instance, Object prev) {
        assert (IMPL.get()[PolyglotFastThreadLocals.getLanguageIndex(instance) + 1] != null) : "language not entered";
        IMPL.set((Object[])prev);
    }

    public static void cleanup(Object[] threadLocals) {
        Arrays.fill(threadLocals, null);
    }

    static EncapsulatingNodeReference getEncapsulatingNodeReference(boolean invalidateOnNull) {
        return IMPL.fastGet(2, EncapsulatingNodeReference.class, invalidateOnNull);
    }

    public static PolyglotThreadInfo getCurrentThread(PolyglotSharingLayer layer) {
        PolyglotThreadInfo constantThread;
        PolyglotContextImpl singleContext;
        if (CompilerDirectives.inCompiledCode() && layer != null && (singleContext = layer.getSingleConstantContext()) != null && CompilerDirectives.isPartialEvaluationConstant(singleContext) && (constantThread = singleContext.singleThreadValue.getConstant()) != null) {
            return constantThread;
        }
        return IMPL.fastGet(0, PolyglotThreadInfo.class, true);
    }

    public static PolyglotThreadInfo getCurrentThreadEngine(PolyglotEngineImpl engine) {
        PolyglotThreadInfo constantThread;
        PolyglotContextImpl singleContext;
        if (CompilerDirectives.inCompiledCode() && engine != null && (singleContext = engine.singleContextValue.getConstant()) != null && (constantThread = singleContext.singleThreadValue.getConstant()) != null) {
            return constantThread;
        }
        return IMPL.fastGet(0, PolyglotThreadInfo.class, true);
    }

    public static PolyglotContextImpl getContext(PolyglotSharingLayer layer) {
        PolyglotContextImpl value2;
        if (CompilerDirectives.inCompiledCode() && layer != null && (value2 = layer.getSingleConstantContext()) != null) {
            return value2;
        }
        return IMPL.fastGet(1, PolyglotContextImpl.class, true);
    }

    public static PolyglotContextImpl getContextWithEngine(PolyglotEngineImpl engine) {
        PolyglotContextImpl context;
        if (CompilerDirectives.inCompiledCode() && engine != null && (context = engine.singleContextValue.getConstant()) != null) {
            return context;
        }
        return IMPL.fastGet(1, PolyglotContextImpl.class, true);
    }

    public static PolyglotContextImpl getContextWithNode(Node node) {
        PolyglotSharingLayer layer;
        if (CompilerDirectives.inCompiledCode() && (layer = PolyglotFastThreadLocals.resolveLayer(node)) != null) {
            return layer.getSingleConstantContext();
        }
        return IMPL.fastGet(1, PolyglotContextImpl.class, true);
    }

    public static TruffleLanguage<Object> getLanguage(Node node, int index, Class<?> languageClass) {
        PolyglotLanguageInstance instance;
        assert (PolyglotFastThreadLocals.validSharing(node));
        if (CompilerDirectives.inCompiledCode() && (instance = PolyglotFastThreadLocals.resolveLanguageInstance(node, index)) != null) {
            return instance.spi;
        }
        return (TruffleLanguage)IMPL.fastGet(index, languageClass, true);
    }

    public static Object getLanguageContext(Node node, int index) {
        assert (PolyglotFastThreadLocals.validSharing(node));
        Class contextClass = null;
        if (CompilerDirectives.inCompiledCode()) {
            PolyglotLanguageContext languageContext;
            PolyglotLanguageInstance instance = PolyglotFastThreadLocals.resolveLanguageInstance(node, index);
            if (instance != null && (languageContext = instance.singleLanguageContext.getConstant()) != null) {
                return languageContext.getContextImpl();
            }
            contextClass = PolyglotFastThreadLocals.findContextClass(node, index);
        }
        return IMPL.fastGet(index, contextClass, true);
    }

    private static boolean validSharing(Node node) {
        PolyglotContextImpl currentContext = PolyglotFastThreadLocals.getContext(null);
        if (currentContext == null) {
            return true;
        }
        PolyglotSharingLayer astLayer = PolyglotFastThreadLocals.resolveLayer(node);
        if (astLayer == null) {
            return true;
        }
        PolyglotSharingLayer currentLayer = currentContext.layer;
        if (!Objects.equals(astLayer, currentLayer)) {
            throw PolyglotSharingLayer.invalidSharingError(node, astLayer, currentLayer);
        }
        return true;
    }

    private static CachedReferences lookupReferences(Class<? extends TruffleLanguage<?>> language) {
        return CONTEXT_REFERENCE_CACHE.computeIfAbsent(language, c -> new CachedReferences((Class<?>)c));
    }

    static void notifyLanguageCreated(PolyglotLanguageContext languageContext) {
        assert (Thread.holdsLock(languageContext.context));
        for (PolyglotThreadInfo threadInfo : languageContext.context.getSeenThreads().values()) {
            PolyglotFastThreadLocals.updateLanguageObjects(threadInfo.fastThreadLocals, languageContext);
        }
    }

    private static void updateLanguageObjects(Object[] data, PolyglotLanguageContext languageContext) {
        PolyglotLanguageInstance instance = languageContext.getLanguageInstance();
        int languageIndex = PolyglotFastThreadLocals.getLanguageIndex(instance);
        assert (languageIndex + 2 - 1 < data.length) : "unexpected fast thread local state";
        data[languageIndex + 0] = languageContext.getContextImpl();
        data[languageIndex + 1] = instance.spi;
    }

    private static PolyglotLanguageInstance resolveLanguageInstance(Node node, int index) {
        CompilerAsserts.partialEvaluationConstant(index);
        if (!CompilerDirectives.isPartialEvaluationConstant(node)) {
            return null;
        }
        if (node == null) {
            return null;
        }
        RootNode root = node.getRootNode();
        if (root == null) {
            return null;
        }
        PolyglotSharingLayer layer = (PolyglotSharingLayer)EngineAccessor.NODES.getSharingLayer(root);
        if (layer == null) {
            return null;
        }
        int languageIndex = PolyglotFastThreadLocals.resolveLanguageIndex(index);
        return layer.getInstance(layer.engine.languages[languageIndex]);
    }

    private static int computeLanguageIndex(Class<?> languageClass, int offset) {
        int staticIndex;
        List<EngineAccessor.AbstractClassLoaderSupplier> loaders = EngineAccessor.locatorOrDefaultLoaders();
        if (EngineAccessor.HOST.isHostLanguage(languageClass)) {
            staticIndex = 0;
        } else {
            LanguageCache cache;
            Map classNames = CLASS_NAME_CACHE.get(loaders);
            if (classNames == null) {
                classNames = new HashMap<String, LanguageCache>();
                Map<String, LanguageCache> idToLanguage = LanguageCache.loadLanguages(loaders);
                for (LanguageCache cache2 : idToLanguage.values()) {
                    classNames.put((String)cache2.getClassName(), (LanguageCache)cache2);
                }
                Map finalClassNames = classNames;
                classNames = CLASS_NAME_CACHE.computeIfAbsent(loaders, k -> Collections.unmodifiableMap(finalClassNames));
            }
            if ((cache = classNames.get(languageClass.getName())) == null) {
                return -1;
            }
            staticIndex = cache.getStaticIndex();
            assert (staticIndex <= LanguageCache.getMaxStaticIndex()) : "invalid sharing between class loaders";
        }
        return 3 + staticIndex * 2 + offset;
    }

    private static int resolveLanguageIndex(int index) {
        if (index < 3 || index >= 3 + (LanguageCache.getMaxStaticIndex() + 1) * 2) {
            throw CompilerDirectives.shouldNotReachHere("invalid fast thread local index");
        }
        return Math.floorDiv(index - 3, 2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static int computePELanguageIndex(Class<? extends TruffleLanguage<?>> languageClass, int offset) {
        int indexValue = LANGUAGE_INDEXES.get(languageClass);
        if (indexValue == -1) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            FinalIntMap finalIntMap = LANGUAGE_INDEXES;
            synchronized (finalIntMap) {
                indexValue = LANGUAGE_INDEXES.get(languageClass);
                if (indexValue == -1) {
                    indexValue = PolyglotFastThreadLocals.computeLanguageIndex(languageClass, 0);
                    LANGUAGE_INDEXES.put(languageClass, indexValue);
                }
            }
        }
        return indexValue + offset;
    }

    protected static PolyglotSharingLayer resolveLayer(Node node) {
        if (!CompilerDirectives.isPartialEvaluationConstant(node)) {
            return null;
        }
        if (node == null) {
            return null;
        }
        RootNode root = node.getRootNode();
        if (root == null) {
            return null;
        }
        return (PolyglotSharingLayer)EngineAccessor.NODES.getSharingLayer(root);
    }

    private static PolyglotEngineImpl resolveEngine(Node node) {
        PolyglotSharingLayer layer = PolyglotFastThreadLocals.resolveLayer(node);
        if (layer != null) {
            return layer.engine;
        }
        return null;
    }

    private static PolyglotLanguage findLanguage(Node node, int index) {
        PolyglotEngineImpl engine = PolyglotFastThreadLocals.resolveEngine(node);
        if (engine == null) {
            return null;
        }
        int languageIndex = PolyglotFastThreadLocals.resolveLanguageIndex(index);
        if (languageIndex > engine.languages.length) {
            return null;
        }
        return engine.languages[languageIndex];
    }

    private static <C> Class<C> findContextClass(Node node, int index) {
        if (index == -1) {
            return null;
        }
        PolyglotLanguage language = PolyglotFastThreadLocals.findLanguage(node, index);
        CompilerAsserts.partialEvaluationConstant(language);
        Class<?> targetClass = null;
        if (language != null) {
            targetClass = language.contextClass;
        }
        return targetClass;
    }

    static boolean assertValidGet(int index, int expectedOffset, Class<?> expectedType, Class<?> languageClass) {
        if (index == -1) {
            throw new IllegalArgumentException("Language " + languageClass + " not installed but used.");
        }
        Object[] data = IMPL.get();
        assert (data != null) : "No polyglot context is entered. A language or context reference must not be used if there is no polyglot context entered.";
        assert (index >= 3 && index < 3 + (LanguageCache.getMaxStaticIndex() + 1) * 2) : "Invalid internal language index range";
        assert ((index - 3) % 2 == expectedOffset) : "Invalid internal language index offset";
        Object value2 = data[index];
        assert (value2 == null || expectedType == null || expectedType.isInstance(value2)) : "Invalid type in internal state.";
        return true;
    }

    static final class LanguageReferenceImpl
    extends TruffleLanguage.LanguageReference<TruffleLanguage<Object>> {
        private final Class<TruffleLanguage<Object>> languageClass;
        private final int index;

        LanguageReferenceImpl(Class<?> languageClass) {
            this.languageClass = languageClass;
            this.index = PolyglotFastThreadLocals.computeLanguageIndex(languageClass, 1);
        }

        @Override
        public TruffleLanguage<Object> get(Node node) {
            assert (PolyglotFastThreadLocals.assertValidGet(this.index, 1, this.languageClass, this.languageClass));
            return PolyglotFastThreadLocals.getLanguage(node, this.index, this.languageClass);
        }

        public String toString() {
            return "LanguageReference[language=" + this.languageClass + ", index = " + this.index + "]";
        }
    }

    static final class ContextReferenceImpl
    extends TruffleLanguage.ContextReference<Object> {
        private final Class<?> languageClass;
        private final int index;

        ContextReferenceImpl(Class<?> languageClass) {
            this.languageClass = languageClass;
            this.index = PolyglotFastThreadLocals.computeLanguageIndex(languageClass, 0);
        }

        @Override
        public Object get(Node node) {
            assert (PolyglotFastThreadLocals.assertValidGet(this.index, 0, PolyglotFastThreadLocals.findContextClass(node, this.index), this.languageClass));
            return PolyglotFastThreadLocals.getLanguageContext(node, this.index);
        }

        public String toString() {
            return "ContextReference[language=" + this.languageClass + ", index = " + this.index + "]";
        }
    }

    static final class CachedReferences {
        final ContextReferenceImpl contextReference;
        final LanguageReferenceImpl languageReference;

        CachedReferences(Class<?> languageClass) {
            this.contextReference = new ContextReferenceImpl(languageClass);
            this.languageReference = new LanguageReferenceImpl(languageClass);
        }
    }
}

