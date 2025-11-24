
package com.oracle.truffle.api.library;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.library.DefaultExportProvider;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.EagerExportProvider;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.GenerateLibrary;
import com.oracle.truffle.api.library.Library;
import com.oracle.truffle.api.library.LibraryAccessor;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.Message;
import com.oracle.truffle.api.library.ReflectionLibrary;
import com.oracle.truffle.api.utilities.FinalBitSet;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import sun.misc.Unsafe;

public abstract class LibraryFactory<T extends Library> {
    private static final ConcurrentHashMap<Class<?>, LibraryFactory<?>> LIBRARIES;
    private static final DefaultExportProvider[] EMPTY_DEFAULT_EXPORT_ARRAY;
    private final Class<T> libraryClass;
    private final List<Message> messages;
    private final ConcurrentHashMap<Class<?>, LibraryExport<T>> exportCache = new ConcurrentHashMap();
    private final ConcurrentHashMap<Class<?>, T> uncachedCache = new ConcurrentHashMap();
    private final ConcurrentHashMap<Class<?>, T> cachedCache = new ConcurrentHashMap();
    private volatile CachedAOTExports aot;
    private final ProxyExports proxyExports = new ProxyExports();
    final Map<String, Message> nameToMessages;
    @CompilerDirectives.CompilationFinal
    private volatile T uncachedDispatch;
    final DynamicDispatchLibrary dispatchLibrary;
    DefaultExportProvider[] beforeBuiltinDefaultExports;
    DefaultExportProvider[] afterBuiltinDefaultExports;
    private static volatile Map<String, List<DefaultExportProvider>> externalDefaultProviders;
    private static volatile Map<String, List<EagerExportProvider>> eagerExportProviders;

    private static void reinitializeNativeImageState() {
        for (Map.Entry<Class<?>, LibraryFactory<?>> entry : LIBRARIES.entrySet()) {
            LibraryFactory<?> libraryFactory = entry.getValue();
            libraryFactory.initDefaultExports();
        }
    }

    private static void resetNativeImageState(ClassLoader imageClassLoader) {
        assert (TruffleOptions.AOT) : "Only supported during image generation";
        for (Map.Entry<Class<?>, LibraryFactory<?>> entry : LIBRARIES.entrySet()) {
            LibraryFactory<?> libraryFactory = entry.getValue();
            LibraryFactory.removeClassesLoadedDuringImageBuild(libraryFactory.exportCache, imageClassLoader);
            LibraryFactory.removeClassesLoadedDuringImageBuild(libraryFactory.uncachedCache, imageClassLoader);
            LibraryFactory.removeClassesLoadedDuringImageBuild(libraryFactory.cachedCache, imageClassLoader);
            externalDefaultProviders = null;
            libraryFactory.afterBuiltinDefaultExports = null;
            libraryFactory.beforeBuiltinDefaultExports = null;
            libraryFactory.aot = null;
        }
        LibraryFactory.removeClassesLoadedDuringImageBuild(LIBRARIES, imageClassLoader);
        LibraryFactory.removeClassesLoadedDuringImageBuild(ResolvedDispatch.CACHE, imageClassLoader);
        LibraryFactory.removeClassesLoadedDuringImageBuild(ResolvedDispatch.REGISTRY, imageClassLoader);
        LibraryFactory.removeClassesLoadedDuringImageBuild(ResolvedDispatch.LIBRARY_TO_EXPORT, imageClassLoader);
    }

    private static void removeClassesLoadedDuringImageBuild(Map<? extends Class<?>, ?> map, ClassLoader imageClassLoader) {
        Class[] classes;
        for (Class clazz : classes = map.keySet().toArray(new Class[0])) {
            if (clazz.getClassLoader() != imageClassLoader) continue;
            map.remove(clazz);
        }
    }

    protected LibraryFactory(Class<T> libraryClass, List<Message> messages) {
        assert (this.getClass().getName().endsWith("Gen"));
        assert (this.getClass().getAnnotation(GeneratedBy.class) != null);
        assert (this.getClass().getAnnotation(GeneratedBy.class).value() == libraryClass);
        this.libraryClass = libraryClass;
        this.messages = Collections.unmodifiableList(messages);
        LinkedHashMap<String, Message> messagesMap = new LinkedHashMap<String, Message>();
        for (Message message : this.getMessages()) {
            assert (message.library == null);
            message.library = this;
            messagesMap.put(message.getSimpleName(), message);
        }
        this.nameToMessages = messagesMap;
        if (libraryClass == DynamicDispatchLibrary.class) {
            this.dispatchLibrary = null;
        } else {
            GenerateLibrary annotation = libraryClass.getAnnotation(GenerateLibrary.class);
            boolean dynamicDispatchEnabled = annotation == null || libraryClass.getAnnotation(GenerateLibrary.class).dynamicDispatchEnabled();
            this.dispatchLibrary = dynamicDispatchEnabled ? LibraryFactory.resolve(DynamicDispatchLibrary.class).getUncached() : null;
        }
        this.initDefaultExports();
    }

    private void initDefaultExports() {
        List<DefaultExportProvider> providers = LibraryFactory.getExternalDefaultProviders().get(this.libraryClass.getName());
        ArrayList beforeBuiltin = null;
        ArrayList afterBuiltin = null;
        if (providers != null && !providers.isEmpty()) {
            for (DefaultExportProvider provider : providers) {
                ArrayList<DefaultExportProvider> providerList = new ArrayList();
                if (provider.getPriority() > 0) {
                    if (beforeBuiltin == null) {
                        beforeBuiltin = new ArrayList();
                    }
                    providerList = beforeBuiltin;
                } else {
                    if (afterBuiltin == null) {
                        afterBuiltin = new ArrayList();
                    }
                    providerList = afterBuiltin;
                }
                providerList.add(provider);
            }
        }
        this.beforeBuiltinDefaultExports = beforeBuiltin != null ? beforeBuiltin.toArray(new DefaultExportProvider[beforeBuiltin.size()]) : EMPTY_DEFAULT_EXPORT_ARRAY;
        this.afterBuiltinDefaultExports = afterBuiltin != null ? afterBuiltin.toArray(new DefaultExportProvider[afterBuiltin.size()]) : EMPTY_DEFAULT_EXPORT_ARRAY;
    }

    @CompilerDirectives.TruffleBoundary
    public final T createDispatched(int limit) {
        if (limit <= 0) {
            return this.getUncached();
        }
        this.ensureLibraryInitialized();
        return this.createDispatchImpl(limit);
    }

    @CompilerDirectives.TruffleBoundary
    public final T create(Object receiver) {
        Class<?> dispatchClass = this.dispatch(receiver);
        Library cached = (Library)this.cachedCache.get(dispatchClass);
        if (cached != null) {
            assert (this.validateExport(receiver, dispatchClass, cached));
            return (T)cached;
        }
        this.ensureLibraryInitialized();
        LibraryExport<T> export = this.lookupExport(receiver, dispatchClass);
        cached = export.createCached(receiver);
        assert ((cached = this.createAssertionsImpl(export, cached)) != null);
        if (!cached.isAdoptable()) {
            assert (receiver instanceof LibraryExport || cached.accepts(receiver)) : String.format("Invalid accepts implementation detected in '%s'", dispatchClass.getName());
            Library otherCached = this.cachedCache.putIfAbsent(dispatchClass, cached);
            if (otherCached != null) {
                return (T)otherCached;
            }
        }
        return (T)cached;
    }

    protected final T createAOT(LibraryExport<T> lib) {
        return lib.createCached(lib);
    }

    private CachedAOTExports aotSupport() {
        CachedAOTExports support = this.aot;
        if (support == null || !support.isValid()) {
            this.ensureEagerExportsRegistered();
            support = this.aot = new CachedAOTExports();
        }
        return support;
    }

    private void ensureEagerExportsRegistered() {
        List<EagerExportProvider> list;
        if (!TruffleOptions.AOT && (list = LibraryFactory.getEagerExportProviders().get(this.libraryClass.getName())) != null) {
            for (EagerExportProvider provider : list) {
                provider.ensureRegistered();
            }
        }
    }

    public final T getUncached() {
        T dispatch = this.uncachedDispatch;
        if (dispatch == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.ensureLibraryInitialized();
            dispatch = this.createUncachedDispatch();
            T otherDispatch = this.uncachedDispatch;
            if (otherDispatch != null) {
                dispatch = otherDispatch;
            } else {
                this.uncachedDispatch = dispatch;
            }
        }
        return dispatch;
    }

    private void ensureLibraryInitialized() {
        CompilerAsserts.neverPartOfCompilation();
        Lazy.UNSAFE.ensureClassInitialized(this.libraryClass);
    }

    @CompilerDirectives.TruffleBoundary
    public final T getUncached(Object receiver) {
        Class<?> dispatchClass = this.dispatch(receiver);
        Library uncached = (Library)this.uncachedCache.get(dispatchClass);
        if (uncached != null) {
            assert (this.validateExport(receiver, dispatchClass, uncached));
            return (T)uncached;
        }
        return this.getUncachedSlowPath(receiver, dispatchClass);
    }

    private T getUncachedSlowPath(Object receiver, Class<?> dispatchClass) {
        this.ensureLibraryInitialized();
        LibraryExport<T> export = this.lookupExport(receiver, dispatchClass);
        T uncached = export.createUncached(receiver);
        assert (this.validateExport(receiver, dispatchClass, uncached));
        assert (((Library)uncached).accepts(receiver));
        assert ((uncached = this.createAssertionsImpl(export, uncached)) != null);
        Library otherUncached = (Library)this.uncachedCache.putIfAbsent(dispatchClass, uncached);
        if (otherUncached != null) {
            return (T)otherUncached;
        }
        return uncached;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Map<String, List<DefaultExportProvider>> getExternalDefaultProviders() {
        Map<String, List<DefaultExportProvider>> providers = externalDefaultProviders;
        if (providers != null) return providers;
        Class<LibraryFactory> clazz = LibraryFactory.class;
        synchronized (LibraryFactory.class) {
            providers = externalDefaultProviders;
            if (providers != null) return providers;
            return LibraryFactory.loadExternalDefaultProviders();
        }
    }

    private static Map<String, List<DefaultExportProvider>> loadExternalDefaultProviders() {
        LinkedHashMap<String, List<DefaultExportProvider>> providers = new LinkedHashMap<String, List<DefaultExportProvider>>();
        for (DefaultExportProvider provider : LibraryAccessor.engineAccessor().loadServices(DefaultExportProvider.class)) {
            String libraryClassName = provider.getLibraryClassName();
            ArrayList<DefaultExportProvider> providerList = (ArrayList<DefaultExportProvider>)providers.get(libraryClassName);
            if (providerList == null) {
                providerList = new ArrayList<DefaultExportProvider>();
                providers.put(libraryClassName, providerList);
            }
            providerList.add(provider);
        }
        for (List providerList : providers.values()) {
            Collections.sort(providerList, new Comparator<DefaultExportProvider>(){

                @Override
                public int compare(DefaultExportProvider o1, DefaultExportProvider o2) {
                    return Integer.compare(o2.getPriority(), o1.getPriority());
                }
            });
        }
        return providers;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Map<String, List<EagerExportProvider>> getEagerExportProviders() {
        Map<String, List<EagerExportProvider>> providers = eagerExportProviders;
        if (providers != null) return providers;
        Class<LibraryFactory> clazz = LibraryFactory.class;
        synchronized (LibraryFactory.class) {
            providers = eagerExportProviders;
            if (providers != null) return providers;
            return LibraryFactory.loadEagerExportProviders();
        }
    }

    private static Map<String, List<EagerExportProvider>> loadEagerExportProviders() {
        LinkedHashMap<String, List<EagerExportProvider>> providers = new LinkedHashMap<String, List<EagerExportProvider>>();
        for (EagerExportProvider provider : LibraryAccessor.engineAccessor().loadServices(EagerExportProvider.class)) {
            String libraryClassName = provider.getLibraryClassName();
            ArrayList<EagerExportProvider> providerList = (ArrayList<EagerExportProvider>)providers.get(libraryClassName);
            if (providerList == null) {
                providerList = new ArrayList<EagerExportProvider>();
                providers.put(libraryClassName, providerList);
            }
            providerList.add(provider);
        }
        return providers;
    }

    final Class<T> getLibraryClass() {
        return this.libraryClass;
    }

    public final List<Message> getMessages() {
        return this.messages;
    }

    private T createAssertionsImpl(LibraryExport<T> export, T cached) {
        if (this.needsAssertions(export)) {
            return this.createAssertions(cached);
        }
        return cached;
    }

    private boolean needsAssertions(LibraryExport<T> export) {
        Class<?> registerClass = export.registerClass;
        return !export.isDefaultExport() || registerClass == null || !registerClass.getName().equals("com.oracle.truffle.api.interop.DefaultTruffleObjectExports");
    }

    private boolean validateExport(Object receiver, Class<?> dispatchClass, T library) {
        this.validateExport(receiver, dispatchClass, this.lookupExport(receiver, dispatchClass));
        assert (receiver instanceof LibraryExport || ((Library)library).accepts(receiver)) : library.getClass().getName();
        return true;
    }

    private Class<?> dispatch(Object receiver) {
        if (receiver == null) {
            throw new NullPointerException("Null receiver values are not supported by libraries.");
        }
        if (this.dispatchLibrary == null) {
            if (receiver instanceof LibraryExport) {
                return ((LibraryExport)receiver).getReceiverClass();
            }
            return receiver.getClass();
        }
        Class<?> dispatch = this.dispatchLibrary.dispatch(receiver);
        if (dispatch == null) {
            return receiver.getClass();
        }
        return dispatch;
    }

    protected abstract T createDispatchImpl(int var1);

    protected final List<LibraryExport<T>> getAOTExports() {
        return this.aotSupport().exports;
    }

    protected abstract T createUncachedDispatch();

    protected abstract T createProxy(ReflectionLibrary var1);

    protected T createDelegate(T original) {
        return original;
    }

    protected T createAssertions(T delegate) {
        return delegate;
    }

    protected abstract Class<?> getDefaultClass(Object var1);

    private Class<?> getDefaultClassImpl(Object receiver) {
        for (DefaultExportProvider defaultExport : this.beforeBuiltinDefaultExports) {
            if (!defaultExport.getReceiverClass().isInstance(receiver)) continue;
            return defaultExport.getDefaultExport();
        }
        Class<?> defaultClass = this.getDefaultClass(receiver);
        if (defaultClass != this.getLibraryClass()) {
            return defaultClass;
        }
        for (DefaultExportProvider defaultExport : this.afterBuiltinDefaultExports) {
            if (!defaultExport.getReceiverClass().isInstance(receiver)) continue;
            return defaultExport.getDefaultExport();
        }
        return defaultClass;
    }

    protected abstract Object genericDispatch(Library var1, Object var2, Message var3, Object[] var4, int var5) throws Exception;

    protected FinalBitSet createMessageBitSet(Message ... enabledMessages) {
        throw CompilerDirectives.shouldNotReachHere("should be generated");
    }

    protected static boolean isDelegated(Library lib, int index) {
        boolean result = ((LibraryExport.DelegateExport)((Object)lib)).getDelegateExportMessages().get(index);
        CompilerAsserts.partialEvaluationConstant(result);
        return !result;
    }

    protected static Object readDelegate(Library lib, Object receiver) {
        return ((LibraryExport.DelegateExport)((Object)lib)).readDelegateExport(receiver);
    }

    protected static <T extends Library> T getDelegateLibrary(T lib, Object delegate) {
        return (T)((LibraryExport.DelegateExport)((Object)lib)).getDelegateExportLibrary(delegate);
    }

    final LibraryExport<T> lookupExport(Object receiver, Class<?> dispatchedClass) {
        LibraryExport lib = this.exportCache.get(dispatchedClass);
        if (lib != null) {
            return lib;
        }
        ResolvedDispatch resolvedLibrary = ResolvedDispatch.lookup(dispatchedClass);
        lib = resolvedLibrary.getLibrary(this.libraryClass);
        if (lib == null) {
            if (this.libraryClass != DynamicDispatchLibrary.class && resolvedLibrary.getLibrary(ReflectionLibrary.class) != null) {
                lib = this.proxyExports;
            } else {
                Class<?> defaultClass = this.getDefaultClassImpl(receiver);
                lib = ResolvedDispatch.lookup(defaultClass).getLibrary(this.libraryClass);
            }
        } else {
            assert (!lib.isDefaultExport()) : String.format("Dynamic dispatch from receiver class '%s' to default export '%s' detected. Use null instead to dispatch to a default export.", receiver.getClass().getName(), dispatchedClass.getName());
            this.validateExport(receiver, dispatchedClass, lib);
        }
        LibraryExport concurrent = this.exportCache.putIfAbsent(dispatchedClass, lib);
        return concurrent != null ? concurrent : lib;
    }

    private void validateExport(Object receiver, Class<?> dispatchedClass, LibraryExport<T> exports) throws AssertionError {
        if (!exports.getReceiverClass().isInstance(receiver)) {
            if (receiver instanceof LibraryExport && exports.getReceiverClass() == ((LibraryExport)receiver).getReceiverClass()) {
                return;
            }
            throw CompilerDirectives.shouldNotReachHere(String.format("Receiver class %s was dynamically dispatched to incompatible exports %s. Expected receiver class %s.", receiver.getClass().getName(), dispatchedClass.getName(), exports.getReceiverClass().getName()));
        }
    }

    @CompilerDirectives.TruffleBoundary
    public static <T extends Library> LibraryFactory<T> resolve(Class<T> library) {
        Objects.requireNonNull(library);
        return LibraryFactory.resolveImpl(library, true);
    }

    private static <T extends Library> LibraryFactory<T> resolveImpl(Class<T> library, boolean fail) {
        LibraryFactory<?> lib = LIBRARIES.get(library);
        if (lib == null) {
            LibraryFactory.loadGeneratedClass(library);
            lib = LIBRARIES.get(library);
            if (lib == null) {
                if (fail) {
                    throw new IllegalArgumentException(String.format("Class '%s' is not a registered library. Truffle libraries must be annotated with @%s to be registered. Did the Truffle annotation processor run?", library.getName(), GenerateLibrary.class.getSimpleName()));
                }
                return null;
            }
        }
        return lib;
    }

    static LibraryFactory<?> loadGeneratedClass(Class<?> libraryClass) {
        if (Library.class.isAssignableFrom(libraryClass)) {
            String generatedClassName = libraryClass.getPackage().getName() + "." + libraryClass.getSimpleName() + "Gen";
            try {
                Class.forName(generatedClassName, true, libraryClass.getClassLoader());
            }
            catch (ClassNotFoundException e) {
                return null;
            }
            return LIBRARIES.get(libraryClass);
        }
        return null;
    }

    static Message resolveMessage(Class<? extends Library> library, String message, boolean fail) {
        Objects.requireNonNull(message);
        LibraryFactory<? extends Library> lib = LibraryFactory.resolveImpl(library, fail);
        if (lib == null) {
            assert (!fail);
            return null;
        }
        return LibraryFactory.resolveLibraryMessage(lib, message, fail);
    }

    private static Message resolveLibraryMessage(LibraryFactory<?> lib, String message, boolean fail) {
        Message foundMessage = lib.nameToMessages.get(message);
        if (fail && foundMessage == null) {
            throw new IllegalArgumentException(String.format("Unknown message '%s' for library '%s' specified.", message, lib.getLibraryClass().getName()));
        }
        return foundMessage;
    }

    protected static <T extends Library> void register(Class<T> libraryClass, LibraryFactory<T> library) {
        LibraryFactory<T> lib = LIBRARIES.putIfAbsent(libraryClass, library);
        if (lib != null) {
            throw CompilerDirectives.shouldNotReachHere("Reflection cannot be installed for a library twice.");
        }
    }

    public String toString() {
        return "LibraryFactory [library=" + this.libraryClass.getName() + "]";
    }

    static {
        EMPTY_DEFAULT_EXPORT_ARRAY = new DefaultExportProvider[0];
        LIBRARIES = new ConcurrentHashMap();
    }

    static final class ResolvedDispatch {
        private static final ConcurrentHashMap<Class<?>, ResolvedDispatch> CACHE = new ConcurrentHashMap();
        private static final ConcurrentHashMap<Class<?>, LibraryExport<?>[]> REGISTRY = new ConcurrentHashMap();
        private static final ConcurrentHashMap<Class<? extends Library>, List<? extends LibraryExport<?>>> LIBRARY_TO_EXPORT = new ConcurrentHashMap();
        private static final ResolvedDispatch OBJECT_RECEIVER = new ResolvedDispatch(null, Object.class, new LibraryExport[0]);
        private final ResolvedDispatch parent;
        private final Class<?> dispatchClass;
        private final Map<Class<?>, LibraryExport<?>> libraries;

        private ResolvedDispatch(ResolvedDispatch parent, Class<?> dispatchClass, LibraryExport<?> ... libs) {
            this.parent = parent;
            this.dispatchClass = dispatchClass;
            LinkedHashMap libraries = new LinkedHashMap();
            for (LibraryExport<?> lib : libs) {
                libraries.put(lib.getLibrary(), lib);
            }
            this.libraries = libraries;
        }

        <T extends Library> LibraryExport<T> getLibrary(Class<T> libraryClass) {
            LibraryExport<Object> lib = this.libraries.get(libraryClass);
            if (lib == null && this.parent != null) {
                lib = this.parent.getLibrary(libraryClass);
            }
            return lib;
        }

        @CompilerDirectives.TruffleBoundary
        static ResolvedDispatch lookup(Class<?> receiverClass) {
            ResolvedDispatch type = CACHE.get(receiverClass);
            if (type == null) {
                type = ResolvedDispatch.resolveClass(receiverClass);
            }
            return type;
        }

        static <T extends Library> void register(Class<?> receiverClass, LibraryExport<?> ... libs) {
            for (LibraryExport<?> lib : libs) {
                lib.registerClass = receiverClass;
            }
            LibraryExport<?>[] prevLibs = REGISTRY.put(receiverClass, libs);
            if (prevLibs != null) {
                throw new IllegalStateException("Receiver " + receiverClass + " is already registered.");
            }
            for (LibraryExport<?> lib : libs) {
                ResolvedDispatch.registerLibraryToExports(lib);
            }
            if (TruffleOptions.AOT) {
                ResolvedDispatch.lookup(receiverClass);
            }
        }

        private static <T extends Library> void registerLibraryToExports(LibraryExport<T> lib) {
            ResolvedDispatch.getLibraryToExports(lib.getLibrary()).add(lib);
        }

        private static <T extends Library> List<LibraryExport<T>> getLibraryToExports(Class<T> libraryClass) {
            return LIBRARY_TO_EXPORT.computeIfAbsent(libraryClass, c -> Collections.synchronizedList(new ArrayList()));
        }

        public String toString() {
            return "ResolvedDispatch[" + this.dispatchClass.getName() + "]";
        }

        Set<Class<?>> getLibraries() {
            return this.libraries.keySet();
        }

        private static boolean hasExports(Class<?> c) {
            return ((ExportLibrary[])c.getAnnotationsByType(ExportLibrary.class)).length > 0;
        }

        private static ResolvedDispatch resolveClass(Class<?> dispatchClass) {
            ResolvedDispatch resolved;
            ResolvedDispatch concurrent;
            if (dispatchClass == null) {
                return OBJECT_RECEIVER;
            }
            ResolvedDispatch parent = ResolvedDispatch.resolveClass(dispatchClass.getSuperclass());
            LibraryExport<?>[] libs = REGISTRY.get(dispatchClass);
            if (libs == null && ResolvedDispatch.hasExports(dispatchClass)) {
                ResolvedDispatch.loadGeneratedClass(dispatchClass);
                libs = REGISTRY.get(dispatchClass);
                if (libs == null) {
                    throw CompilerDirectives.shouldNotReachHere(String.format("Libraries for class '%s' could not be resolved. Not registered?", dispatchClass.getName()));
                }
            }
            if ((concurrent = CACHE.putIfAbsent(dispatchClass, resolved = libs != null ? new ResolvedDispatch(parent, dispatchClass, libs) : parent)) != null) {
                return concurrent;
            }
            return resolved;
        }

        static void loadGeneratedClass(Class<?> currentReceiverClass) {
            String generatedClassName = currentReceiverClass.getPackage().getName() + "." + currentReceiverClass.getSimpleName() + "Gen";
            try {
                Class.forName(generatedClassName, true, currentReceiverClass.getClassLoader());
            }
            catch (ClassNotFoundException e) {
                throw CompilerDirectives.shouldNotReachHere(String.format("Generated class '%s' for class '%s' not found. Did the Truffle annotation processor run?", generatedClassName, currentReceiverClass.getName()), e);
            }
        }
    }

    final class ProxyExports
    extends LibraryExport<T> {
        protected ProxyExports() {
            super(LibraryFactory.this.libraryClass, Object.class, true);
        }

        @Override
        public T createUncached(Object receiver) {
            return LibraryFactory.this.createProxy(ReflectionLibrary.getFactory().getUncached(receiver));
        }

        @Override
        protected T createCached(Object receiver) {
            return LibraryFactory.this.createProxy(ReflectionLibrary.getFactory().create(receiver));
        }
    }

    private final class CachedAOTExports {
        final int previousExportSize;
        final List<LibraryExport<T>> exports;

        CachedAOTExports() {
            List<LibraryExport<LibraryExport>> libraryToExports = ResolvedDispatch.getLibraryToExports(LibraryFactory.this.libraryClass);
            LibraryExport[] allExports = libraryToExports.toArray(new LibraryExport[libraryToExports.size()]);
            this.previousExportSize = allExports.length;
            this.exports = Arrays.asList(allExports).stream().filter(e -> e.isAOT()).sorted((e1, e2) -> Integer.compare(e2.aotPriority, e1.aotPriority)).collect(Collectors.toList());
            if (this.exports.isEmpty()) {
                throw new IllegalStateException("No AOT exports found for library " + LibraryFactory.this.libraryClass.getName() + ". Make sure at least one reachable export sets useForAOT to true to resolve this.");
            }
        }

        boolean isValid() {
            return ResolvedDispatch.getLibraryToExports(LibraryFactory.this.libraryClass).size() == this.previousExportSize;
        }
    }

    static class Lazy {
        private static final Unsafe UNSAFE;

        Lazy() {
        }

        static {
            Unsafe unsafe;
            try {
                unsafe = Unsafe.getUnsafe();
            }
            catch (SecurityException e) {
                try {
                    Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
                    theUnsafeInstance.setAccessible(true);
                    unsafe = (Unsafe)theUnsafeInstance.get(Unsafe.class);
                }
                catch (Exception e2) {
                    throw new RuntimeException("exception while trying to get Unsafe.theUnsafe via reflection:", e2);
                }
            }
            UNSAFE = unsafe;
        }
    }
}

