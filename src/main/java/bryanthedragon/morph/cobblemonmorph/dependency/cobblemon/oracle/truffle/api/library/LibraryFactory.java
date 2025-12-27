package com.oracle.truffle.api.library;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.dsl.GeneratedBy;
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
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import sun.misc.Unsafe;

public abstract class LibraryFactory<T extends Library> {
   private static final ConcurrentHashMap<Class<?>, LibraryFactory<?>> LIBRARIES = new ConcurrentHashMap<>();
   private static final DefaultExportProvider[] EMPTY_DEFAULT_EXPORT_ARRAY = new DefaultExportProvider[0];
   private final Class<T> libraryClass;
   private final List<Message> messages;
   private final ConcurrentHashMap<Class<?>, LibraryExport<T>> exportCache = new ConcurrentHashMap<>();
   private final ConcurrentHashMap<Class<?>, T> uncachedCache = new ConcurrentHashMap<>();
   private final ConcurrentHashMap<Class<?>, T> cachedCache = new ConcurrentHashMap<>();
   private volatile LibraryFactory<T>.CachedAOTExports aot;
   private final LibraryFactory<T>.ProxyExports proxyExports = new LibraryFactory.ProxyExports();
   final Map<String, Message> nameToMessages;
   @CompilerDirectives.CompilationFinal
   private volatile T uncachedDispatch;
   final DynamicDispatchLibrary dispatchLibrary;
   DefaultExportProvider[] beforeBuiltinDefaultExports;
   DefaultExportProvider[] afterBuiltinDefaultExports;
   private static volatile Map<String, List<DefaultExportProvider>> externalDefaultProviders;
   private static volatile Map<String, List<EagerExportProvider>> eagerExportProviders;

   private static void reinitializeNativeImageState() {
      for (Entry<Class<?>, LibraryFactory<?>> entry : LIBRARIES.entrySet()) {
         LibraryFactory<?> libraryFactory = entry.getValue();
         libraryFactory.initDefaultExports();
      }
   }

   private static void resetNativeImageState(ClassLoader imageClassLoader) {
      assert TruffleOptions.AOT : "Only supported during image generation";

      for (Entry<Class<?>, LibraryFactory<?>> entry : LIBRARIES.entrySet()) {
         LibraryFactory<?> libraryFactory = entry.getValue();
         removeClassesLoadedDuringImageBuild(libraryFactory.exportCache, imageClassLoader);
         removeClassesLoadedDuringImageBuild(libraryFactory.uncachedCache, imageClassLoader);
         removeClassesLoadedDuringImageBuild(libraryFactory.cachedCache, imageClassLoader);
         externalDefaultProviders = null;
         libraryFactory.afterBuiltinDefaultExports = null;
         libraryFactory.beforeBuiltinDefaultExports = null;
         libraryFactory.aot = null;
      }

      removeClassesLoadedDuringImageBuild(LIBRARIES, imageClassLoader);
      removeClassesLoadedDuringImageBuild(LibraryFactory.ResolvedDispatch.CACHE, imageClassLoader);
      removeClassesLoadedDuringImageBuild(LibraryFactory.ResolvedDispatch.REGISTRY, imageClassLoader);
      removeClassesLoadedDuringImageBuild(LibraryFactory.ResolvedDispatch.LIBRARY_TO_EXPORT, imageClassLoader);
   }

   private static void removeClassesLoadedDuringImageBuild(Map<? extends Class<?>, ?> map, ClassLoader imageClassLoader) {
      Class<?>[] classes = map.keySet().toArray(new Class[0]);

      for (Class<?> clazz : classes) {
         if (clazz.getClassLoader() == imageClassLoader) {
            map.remove(clazz);
         }
      }
   }

   protected LibraryFactory(Class<T> libraryClass, List<Message> messages) {
      assert this.getClass().getName().endsWith("Gen");

      assert this.getClass().getAnnotation(GeneratedBy.class) != null;

      assert this.getClass().getAnnotation(GeneratedBy.class).value() == libraryClass;

      this.libraryClass = libraryClass;
      this.messages = Collections.unmodifiableList(messages);
      Map<String, Message> messagesMap = new LinkedHashMap<>();

      for (Message message : this.getMessages()) {
         assert message.library == null;

         message.library = this;
         messagesMap.put(message.getSimpleName(), message);
      }

      this.nameToMessages = messagesMap;
      if (libraryClass == DynamicDispatchLibrary.class) {
         this.dispatchLibrary = null;
      } else {
         GenerateLibrary annotation = libraryClass.getAnnotation(GenerateLibrary.class);
         boolean dynamicDispatchEnabled = annotation == null || libraryClass.getAnnotation(GenerateLibrary.class).dynamicDispatchEnabled();
         if (dynamicDispatchEnabled) {
            this.dispatchLibrary = resolve(DynamicDispatchLibrary.class).getUncached();
         } else {
            this.dispatchLibrary = null;
         }
      }

      this.initDefaultExports();
   }

   private void initDefaultExports() {
      List<DefaultExportProvider> providers = getExternalDefaultProviders().get(this.libraryClass.getName());
      List<DefaultExportProvider> beforeBuiltin = null;
      List<DefaultExportProvider> afterBuiltin = null;
      if (providers != null && !providers.isEmpty()) {
         for (DefaultExportProvider provider : providers) {
            new ArrayList();
            Object providerList;
            if (provider.getPriority() > 0) {
               if (beforeBuiltin == null) {
                  beforeBuiltin = new ArrayList<>();
               }

               providerList = beforeBuiltin;
            } else {
               if (afterBuiltin == null) {
                  afterBuiltin = new ArrayList<>();
               }

               providerList = afterBuiltin;
            }

            providerList.add(provider);
         }
      }

      if (beforeBuiltin != null) {
         this.beforeBuiltinDefaultExports = beforeBuiltin.toArray(new DefaultExportProvider[beforeBuiltin.size()]);
      } else {
         this.beforeBuiltinDefaultExports = EMPTY_DEFAULT_EXPORT_ARRAY;
      }

      if (afterBuiltin != null) {
         this.afterBuiltinDefaultExports = afterBuiltin.toArray(new DefaultExportProvider[afterBuiltin.size()]);
      } else {
         this.afterBuiltinDefaultExports = EMPTY_DEFAULT_EXPORT_ARRAY;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public final T createDispatched(int limit) {
      if (limit <= 0) {
         return this.getUncached();
      } else {
         this.ensureLibraryInitialized();
         return this.createDispatchImpl(limit);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public final T create(Object receiver) {
      Class<?> dispatchClass = this.dispatch(receiver);
      T cached = this.cachedCache.get(dispatchClass);
      if (cached != null) {
         assert this.validateExport(receiver, dispatchClass, cached);

         return cached;
      } else {
         this.ensureLibraryInitialized();
         LibraryExport<T> export = this.lookupExport(receiver, dispatchClass);
         cached = export.createCached(receiver);

         assert (cached = this.createAssertionsImpl(export, cached)) != null;

         if (!cached.isAdoptable()) {
            assert receiver instanceof LibraryExport || cached.accepts(receiver) : String.format(
               "Invalid accepts implementation detected in '%s'", dispatchClass.getName()
            );

            T otherCached = this.cachedCache.putIfAbsent(dispatchClass, cached);
            if (otherCached != null) {
               return otherCached;
            }
         }

         return cached;
      }
   }

   protected final T createAOT(LibraryExport<T> lib) {
      return lib.createCached(lib);
   }

   private LibraryFactory<T>.CachedAOTExports aotSupport() {
      LibraryFactory<T>.CachedAOTExports support = this.aot;
      if (support == null || !support.isValid()) {
         this.ensureEagerExportsRegistered();
         support = this.aot = new LibraryFactory.CachedAOTExports();
      }

      return support;
   }

   private void ensureEagerExportsRegistered() {
      if (!TruffleOptions.AOT) {
         List<EagerExportProvider> list = getEagerExportProviders().get(this.libraryClass.getName());
         if (list != null) {
            for (EagerExportProvider provider : list) {
               provider.ensureRegistered();
            }
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
      LibraryFactory.Lazy.UNSAFE.ensureClassInitialized(this.libraryClass);
   }

   @CompilerDirectives.TruffleBoundary
   public final T getUncached(Object receiver) {
      Class<?> dispatchClass = this.dispatch(receiver);
      T uncached = this.uncachedCache.get(dispatchClass);
      if (uncached != null) {
         assert this.validateExport(receiver, dispatchClass, uncached);

         return uncached;
      } else {
         return this.getUncachedSlowPath(receiver, dispatchClass);
      }
   }

   private T getUncachedSlowPath(Object receiver, Class<?> dispatchClass) {
      this.ensureLibraryInitialized();
      LibraryExport<T> export = this.lookupExport(receiver, dispatchClass);
      T uncached = export.createUncached(receiver);

      assert this.validateExport(receiver, dispatchClass, uncached);

      assert uncached.accepts(receiver);

      assert (uncached = this.createAssertionsImpl(export, uncached)) != null;

      T otherUncached = this.uncachedCache.putIfAbsent(dispatchClass, uncached);
      return otherUncached != null ? otherUncached : uncached;
   }

   private static Map<String, List<DefaultExportProvider>> getExternalDefaultProviders() {
      Map<String, List<DefaultExportProvider>> providers = externalDefaultProviders;
      if (providers == null) {
         synchronized (LibraryFactory.class) {
            providers = externalDefaultProviders;
            if (providers == null) {
               providers = loadExternalDefaultProviders();
            }
         }
      }

      return providers;
   }

   private static Map<String, List<DefaultExportProvider>> loadExternalDefaultProviders() {
      Map<String, List<DefaultExportProvider>> providers = new LinkedHashMap<>();

      for (DefaultExportProvider provider : LibraryAccessor.engineAccessor().loadServices(DefaultExportProvider.class)) {
         String libraryClassName = provider.getLibraryClassName();
         List<DefaultExportProvider> providerList = providers.get(libraryClassName);
         if (providerList == null) {
            providerList = new ArrayList<>();
            providers.put(libraryClassName, providerList);
         }

         providerList.add(provider);
      }

      for (List<DefaultExportProvider> providerList : providers.values()) {
         Collections.sort(providerList, new Comparator<DefaultExportProvider>() {
            public int compare(DefaultExportProvider o1, DefaultExportProvider o2) {
               return Integer.compare(o2.getPriority(), o1.getPriority());
            }
         });
      }

      return providers;
   }

   private static Map<String, List<EagerExportProvider>> getEagerExportProviders() {
      Map<String, List<EagerExportProvider>> providers = eagerExportProviders;
      if (providers == null) {
         synchronized (LibraryFactory.class) {
            providers = eagerExportProviders;
            if (providers == null) {
               providers = loadEagerExportProviders();
            }
         }
      }

      return providers;
   }

   private static Map<String, List<EagerExportProvider>> loadEagerExportProviders() {
      Map<String, List<EagerExportProvider>> providers = new LinkedHashMap<>();

      for (EagerExportProvider provider : LibraryAccessor.engineAccessor().loadServices(EagerExportProvider.class)) {
         String libraryClassName = provider.getLibraryClassName();
         List<EagerExportProvider> providerList = providers.get(libraryClassName);
         if (providerList == null) {
            providerList = new ArrayList<>();
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
      return this.needsAssertions(export) ? this.createAssertions(cached) : cached;
   }

   private boolean needsAssertions(LibraryExport<T> export) {
      Class<?> registerClass = export.registerClass;
      return !export.isDefaultExport()
         || registerClass == null
         || !registerClass.getName().equals("com.oracle.truffle.api.interop.DefaultTruffleObjectExports");
   }

   private boolean validateExport(Object receiver, Class<?> dispatchClass, T library) {
      this.validateExport(receiver, dispatchClass, this.lookupExport(receiver, dispatchClass));

      assert receiver instanceof LibraryExport || library.accepts(receiver) : library.getClass().getName();

      return true;
   }

   private Class<?> dispatch(Object receiver) {
      if (receiver == null) {
         throw new NullPointerException("Null receiver values are not supported by libraries.");
      } else if (this.dispatchLibrary == null) {
         return receiver instanceof LibraryExport ? ((LibraryExport)receiver).getReceiverClass() : receiver.getClass();
      } else {
         Class<?> dispatch = this.dispatchLibrary.dispatch(receiver);
         return dispatch == null ? receiver.getClass() : dispatch;
      }
   }

   protected abstract T createDispatchImpl(int limit);

   protected final List<LibraryExport<T>> getAOTExports() {
      return this.aotSupport().exports;
   }

   protected abstract T createUncachedDispatch();

   protected abstract T createProxy(ReflectionLibrary lib);

   protected T createDelegate(T original) {
      return original;
   }

   protected T createAssertions(T delegate) {
      return delegate;
   }

   protected abstract Class<?> getDefaultClass(Object receiver);

   private Class<?> getDefaultClassImpl(Object receiver) {
      for (DefaultExportProvider defaultExport : this.beforeBuiltinDefaultExports) {
         if (defaultExport.getReceiverClass().isInstance(receiver)) {
            return defaultExport.getDefaultExport();
         }
      }

      Class<?> defaultClass = this.getDefaultClass(receiver);
      if (defaultClass != this.getLibraryClass()) {
         return defaultClass;
      } else {
         for (DefaultExportProvider defaultExportx : this.afterBuiltinDefaultExports) {
            if (defaultExportx.getReceiverClass().isInstance(receiver)) {
               return defaultExportx.getDefaultExport();
            }
         }

         return defaultClass;
      }
   }

   protected abstract Object genericDispatch(Library library, Object receiver, Message message, Object[] arguments, int parameterOffset) throws Exception;

   protected FinalBitSet createMessageBitSet(Message... enabledMessages) {
      throw CompilerDirectives.shouldNotReachHere("should be generated");
   }

   protected static boolean isDelegated(Library lib, int index) {
      boolean result = ((LibraryExport.DelegateExport)lib).getDelegateExportMessages().get(index);
      CompilerAsserts.partialEvaluationConstant(result);
      return !result;
   }

   protected static Object readDelegate(Library lib, Object receiver) {
      return ((LibraryExport.DelegateExport)lib).readDelegateExport(receiver);
   }

   protected static <T extends Library> T getDelegateLibrary(T lib, Object delegate) {
      return (T)((LibraryExport.DelegateExport)lib).getDelegateExportLibrary(delegate);
   }

   final LibraryExport<T> lookupExport(Object receiver, Class<?> dispatchedClass) {
      LibraryExport<T> lib = this.exportCache.get(dispatchedClass);
      if (lib != null) {
         return lib;
      } else {
         LibraryFactory.ResolvedDispatch resolvedLibrary = LibraryFactory.ResolvedDispatch.lookup(dispatchedClass);
         LibraryExport<T> var6 = resolvedLibrary.getLibrary(this.libraryClass);
         if (var6 == null) {
            if (this.libraryClass != DynamicDispatchLibrary.class && resolvedLibrary.<ReflectionLibrary>getLibrary(ReflectionLibrary.class) != null) {
               var6 = this.proxyExports;
            } else {
               Class<?> defaultClass = this.getDefaultClassImpl(receiver);
               var6 = LibraryFactory.ResolvedDispatch.lookup(defaultClass).getLibrary(this.libraryClass);
            }
         } else {
            assert !((LibraryExport)var6).isDefaultExport() : String.format(
               "Dynamic dispatch from receiver class '%s' to default export '%s' detected. Use null instead to dispatch to a default export.",
               receiver.getClass().getName(),
               dispatchedClass.getName()
            );

            this.validateExport(receiver, dispatchedClass, (LibraryExport<T>)var6);
         }

         LibraryExport<T> concurrent = this.exportCache.putIfAbsent(dispatchedClass, (LibraryExport<T>)var6);
         return (LibraryExport<T>)(concurrent != null ? concurrent : var6);
      }
   }

   private void validateExport(Object receiver, Class<?> dispatchedClass, LibraryExport<T> exports) throws AssertionError {
      if (!exports.getReceiverClass().isInstance(receiver)) {
         if (!(receiver instanceof LibraryExport) || exports.getReceiverClass() != ((LibraryExport)receiver).getReceiverClass()) {
            throw CompilerDirectives.shouldNotReachHere(
               String.format(
                  "Receiver class %s was dynamically dispatched to incompatible exports %s. Expected receiver class %s.",
                  receiver.getClass().getName(),
                  dispatchedClass.getName(),
                  exports.getReceiverClass().getName()
               )
            );
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static <T extends Library> LibraryFactory<T> resolve(Class<T> library) {
      Objects.requireNonNull(library);
      return resolveImpl(library, true);
   }

   private static <T extends Library> LibraryFactory<T> resolveImpl(Class<T> library, boolean fail) {
      LibraryFactory<?> lib = LIBRARIES.get(library);
      if (lib == null) {
         loadGeneratedClass(library);
         lib = LIBRARIES.get(library);
         if (lib == null) {
            if (fail) {
               throw new IllegalArgumentException(
                  String.format(
                     "Class '%s' is not a registered library. Truffle libraries must be annotated with @%s to be registered. Did the Truffle annotation processor run?",
                     library.getName(),
                     GenerateLibrary.class.getSimpleName()
                  )
               );
            }

            return null;
         }
      }

      return (LibraryFactory<T>)lib;
   }

   static LibraryFactory<?> loadGeneratedClass(Class<?> libraryClass) {
      if (Library.class.isAssignableFrom(libraryClass)) {
         String generatedClassName = libraryClass.getPackage().getName() + "." + libraryClass.getSimpleName() + "Gen";

         try {
            Class.forName(generatedClassName, true, libraryClass.getClassLoader());
         } catch (ClassNotFoundException var3) {
            return null;
         }

         return LIBRARIES.get(libraryClass);
      } else {
         return null;
      }
   }

   static Message resolveMessage(Class<? extends Library> library, String message, boolean fail) {
      Objects.requireNonNull(message);
      LibraryFactory<?> lib = resolveImpl(library, fail);
      if (lib == null) {
         assert !fail;

         return null;
      } else {
         return resolveLibraryMessage(lib, message, fail);
      }
   }

   private static Message resolveLibraryMessage(LibraryFactory<?> lib, String message, boolean fail) {
      Message foundMessage = lib.nameToMessages.get(message);
      if (fail && foundMessage == null) {
         throw new IllegalArgumentException(String.format("Unknown message '%s' for library '%s' specified.", message, lib.getLibraryClass().getName()));
      } else {
         return foundMessage;
      }
   }

   protected static <T extends Library> void register(Class<T> libraryClass, LibraryFactory<T> library) {
      LibraryFactory<?> lib = LIBRARIES.putIfAbsent(libraryClass, library);
      if (lib != null) {
         throw CompilerDirectives.shouldNotReachHere("Reflection cannot be installed for a library twice.");
      }
   }

   @Override
   public String toString() {
      return "LibraryFactory [library=" + this.libraryClass.getName() + "]";
   }

   private final class CachedAOTExports {
      final int previousExportSize;
      final List<LibraryExport<T>> exports;

      CachedAOTExports() {
         List<LibraryExport<T>> libraryToExports = LibraryFactory.ResolvedDispatch.getLibraryToExports(LibraryFactory.this.libraryClass);
         LibraryExport<T>[] allExports = libraryToExports.toArray(new LibraryExport[libraryToExports.size()]);
         this.previousExportSize = allExports.length;
         this.exports = Arrays.asList(allExports)
            .stream()
            .filter(e -> e.isAOT())
            .sorted((e1, e2) -> Integer.compare(e2.aotPriority, e1.aotPriority))
            .collect(Collectors.toList());
         if (this.exports.isEmpty()) {
            throw new IllegalStateException(
               "No AOT exports found for library "
                  + LibraryFactory.this.libraryClass.getName()
                  + ". Make sure at least one reachable export sets useForAOT to true to resolve this."
            );
         }
      }

      boolean isValid() {
         return LibraryFactory.ResolvedDispatch.getLibraryToExports(LibraryFactory.this.libraryClass).size() == this.previousExportSize;
      }
   }

   static class Lazy {
      private static final Unsafe UNSAFE;

      static {
         Unsafe unsafe;
         try {
            unsafe = Unsafe.getUnsafe();
         } catch (SecurityException var4) {
            try {
               Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
               theUnsafeInstance.setAccessible(true);
               unsafe = (Unsafe)theUnsafeInstance.get(Unsafe.class);
            } catch (Exception var3) {
               throw new RuntimeException("exception while trying to get Unsafe.theUnsafe via reflection:", var3);
            }
         }

         UNSAFE = unsafe;
      }
   }

   final class ProxyExports extends LibraryExport<T> {
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

   static final class ResolvedDispatch {
      private static final ConcurrentHashMap<Class<?>, LibraryFactory.ResolvedDispatch> CACHE = new ConcurrentHashMap<>();
      private static final ConcurrentHashMap<Class<?>, LibraryExport<?>[]> REGISTRY = new ConcurrentHashMap<>();
      private static final ConcurrentHashMap<Class<? extends Library>, List<? extends LibraryExport<?>>> LIBRARY_TO_EXPORT = new ConcurrentHashMap<>();
      private static final LibraryFactory.ResolvedDispatch OBJECT_RECEIVER = new LibraryFactory.ResolvedDispatch(null, Object.class);
      private final LibraryFactory.ResolvedDispatch parent;
      private final Class<?> dispatchClass;
      private final Map<Class<?>, LibraryExport<?>> libraries;

      private ResolvedDispatch(LibraryFactory.ResolvedDispatch parent, Class<?> dispatchClass, LibraryExport<?>... libs) {
         this.parent = parent;
         this.dispatchClass = dispatchClass;
         Map<Class<?>, LibraryExport<?>> libraries = new LinkedHashMap<>();

         for (LibraryExport<?> lib : libs) {
            libraries.put(lib.getLibrary(), lib);
         }

         this.libraries = libraries;
      }

      <T extends Library> LibraryExport<T> getLibrary(Class<T> libraryClass) {
         LibraryExport<?> lib = this.libraries.get(libraryClass);
         if (lib == null && this.parent != null) {
            lib = this.parent.getLibrary(libraryClass);
         }

         return (LibraryExport<T>)lib;
      }

      @CompilerDirectives.TruffleBoundary
      static LibraryFactory.ResolvedDispatch lookup(Class<?> receiverClass) {
         LibraryFactory.ResolvedDispatch type = CACHE.get(receiverClass);
         if (type == null) {
            type = resolveClass(receiverClass);
         }

         return type;
      }

      static <T extends Library> void register(Class<?> receiverClass, LibraryExport<?>... libs) {
         for (LibraryExport<?> lib : libs) {
            lib.registerClass = receiverClass;
         }

         LibraryExport<?>[] prevLibs = REGISTRY.put(receiverClass, libs);
         if (prevLibs != null) {
            throw new IllegalStateException("Receiver " + receiverClass + " is already registered.");
         } else {
            for (LibraryExport<?> lib : libs) {
               registerLibraryToExports(lib);
            }

            if (TruffleOptions.AOT) {
               lookup(receiverClass);
            }
         }
      }

      private static <T extends Library> void registerLibraryToExports(LibraryExport<T> lib) {
         getLibraryToExports(lib.getLibrary()).add(lib);
      }

      private static <T extends Library> List<LibraryExport<T>> getLibraryToExports(Class<T> libraryClass) {
         return (List<LibraryExport<T>>)LIBRARY_TO_EXPORT.computeIfAbsent(libraryClass, c -> Collections.synchronizedList(new ArrayList<>()));
      }

      @Override
      public String toString() {
         return "ResolvedDispatch[" + this.dispatchClass.getName() + "]";
      }

      Set<Class<?>> getLibraries() {
         return this.libraries.keySet();
      }

      private static boolean hasExports(Class<?> c) {
         return c.getAnnotationsByType(ExportLibrary.class).length > 0;
      }

      private static LibraryFactory.ResolvedDispatch resolveClass(Class<?> dispatchClass) {
         if (dispatchClass == null) {
            return OBJECT_RECEIVER;
         } else {
            LibraryFactory.ResolvedDispatch parent = resolveClass(dispatchClass.getSuperclass());
            LibraryExport<?>[] libs = REGISTRY.get(dispatchClass);
            if (libs == null && hasExports(dispatchClass)) {
               loadGeneratedClass(dispatchClass);
               libs = REGISTRY.get(dispatchClass);
               if (libs == null) {
                  throw CompilerDirectives.shouldNotReachHere(
                     String.format("Libraries for class '%s' could not be resolved. Not registered?", dispatchClass.getName())
                  );
               }
            }

            LibraryFactory.ResolvedDispatch resolved;
            if (libs != null) {
               resolved = new LibraryFactory.ResolvedDispatch(parent, dispatchClass, libs);
            } else {
               resolved = parent;
            }

            LibraryFactory.ResolvedDispatch concurrent = CACHE.putIfAbsent(dispatchClass, resolved);
            return concurrent != null ? concurrent : resolved;
         }
      }

      static void loadGeneratedClass(Class<?> currentReceiverClass) {
         String generatedClassName = currentReceiverClass.getPackage().getName() + "." + currentReceiverClass.getSimpleName() + "Gen";

         try {
            Class.forName(generatedClassName, true, currentReceiverClass.getClassLoader());
         } catch (ClassNotFoundException var3) {
            throw CompilerDirectives.shouldNotReachHere(
               String.format(
                  "Generated class '%s' for class '%s' not found. Did the Truffle annotation processor run?",
                  generatedClassName,
                  currentReceiverClass.getName()
               ),
               var3
            );
         }
      }
   }
}
