package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.instrumentation.TruffleInstrument;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;

final class InstrumentCache {
   private static final String DEBUGGER_CLASS = "com.oracle.truffle.api.debug.impl.DebuggerInstrument";
   private static final String DEBUGGER_PROVIDER = "com.oracle.truffle.api.debug.impl.DebuggerInstrumentProvider";
   private static final List<InstrumentCache> nativeImageCache = TruffleOptions.AOT ? new ArrayList<>() : null;
   private static Map<List<EngineAccessor.AbstractClassLoaderSupplier>, List<InstrumentCache>> runtimeCaches = new HashMap<>();
   private final String className;
   private final String id;
   private final String name;
   private final String version;
   private final String website;
   private final boolean internal;
   private final Set<String> services;
   private final TruffleInstrument.Provider provider;

   private static void initializeNativeImageState(ClassLoader imageClassLoader) {
      nativeImageCache.addAll(doLoad(Arrays.asList(new EngineAccessor.StrongClassLoaderSupplier(imageClassLoader))));
   }

   private static void resetNativeImageState() {
      nativeImageCache.clear();
      runtimeCaches.clear();
   }

   private InstrumentCache(
      String id, String name, String version, String className, boolean internal, Set<String> services, TruffleInstrument.Provider provider, String website
   ) {
      this.id = id;
      this.name = name;
      this.version = version;
      this.website = website;
      this.className = className;
      this.internal = internal;
      this.services = services;
      this.provider = provider;
   }

   boolean isInternal() {
      return this.internal;
   }

   static List<InstrumentCache> load() {
      if (TruffleOptions.AOT) {
         return nativeImageCache;
      } else {
         synchronized (InstrumentCache.class) {
            List<EngineAccessor.AbstractClassLoaderSupplier> classLoaders = EngineAccessor.locatorOrDefaultLoaders();
            List<InstrumentCache> cache = runtimeCaches.get(classLoaders);
            if (cache == null) {
               cache = doLoad(classLoaders);
               runtimeCaches.put(classLoaders, cache);
            }

            return cache;
         }
      }
   }

   static List<InstrumentCache> doLoad(List<EngineAccessor.AbstractClassLoaderSupplier> suppliers) {
      List<InstrumentCache> list = new ArrayList<>();
      Set<String> classNamesUsed = new HashSet<>();

      for (Supplier<ClassLoader> supplier : suppliers) {
         ClassLoader loader = supplier.get();
         if (loader != null && isValidLoader(loader)) {
            if (!TruffleOptions.AOT) {
               ModuleUtils.exportTo(loader, null);
            }

            for (TruffleInstrument.Provider provider : ServiceLoader.load(TruffleInstrument.Provider.class, loader)) {
               loadInstrumentImpl(provider, list, classNamesUsed);
            }

            if (!classNamesUsed.contains("com.oracle.truffle.api.debug.impl.DebuggerInstrument")) {
               try {
                  loadInstrumentImpl(
                     (TruffleInstrument.Provider)loader.loadClass("com.oracle.truffle.api.debug.impl.DebuggerInstrumentProvider")
                        .getConstructor()
                        .newInstance(),
                     list,
                     classNamesUsed
                  );
               } catch (Exception var8) {
                  throw CompilerDirectives.shouldNotReachHere("Failed to discover debugger instrument.", var8);
               }
            }
         }
      }

      Collections.sort(list, new Comparator<InstrumentCache>() {
         public int compare(InstrumentCache o1, InstrumentCache o2) {
            return o1.getId().compareTo(o2.getId());
         }
      });
      return list;
   }

   private static void loadInstrumentImpl(TruffleInstrument.Provider provider, List<? super InstrumentCache> list, Set<? super String> classNamesUsed) {
      TruffleInstrument.Registration reg = provider.getClass().getAnnotation(TruffleInstrument.Registration.class);
      if (reg == null) {
         PrintStream out = System.err;
         out.println("Provider " + provider.getClass() + " is missing @Registration annotation.");
      } else {
         String className = provider.getInstrumentClassName();
         String name = reg.name();
         String id = reg.id();
         if (id == null || id.isEmpty()) {
            int lastIndex = className.lastIndexOf(36);
            if (lastIndex == -1) {
               lastIndex = className.lastIndexOf(46);
            }

            id = className.substring(lastIndex + 1);
         }

         String version = reg.version();
         String website = reg.website();
         boolean internal = reg.internal();
         Set<String> servicesClassNames = new TreeSet<>();

         for (String service : provider.getServicesClassNames()) {
            servicesClassNames.add(service);
         }

         if (!classNamesUsed.contains(className)) {
            classNamesUsed.add(className);
            list.add(new InstrumentCache(id, name, version, className, internal, servicesClassNames, provider, website));
         }
      }
   }

   private static boolean isValidLoader(ClassLoader loader) {
      try {
         Class<?> truffleInstrumentClassAsSeenByLoader = Class.forName(TruffleInstrument.class.getName(), true, loader);
         return truffleInstrumentClassAsSeenByLoader == TruffleInstrument.class;
      } catch (ClassNotFoundException var2) {
         return false;
      }
   }

   String getId() {
      return this.id;
   }

   String getName() {
      return this.name;
   }

   String getClassName() {
      return this.className;
   }

   String getVersion() {
      return this.version;
   }

   TruffleInstrument loadInstrument() {
      return this.provider.create();
   }

   boolean supportsService(Class<?> clazz) {
      return this.services.contains(clazz.getName()) || this.services.contains(clazz.getCanonicalName());
   }

   String[] services() {
      return this.services.toArray(new String[0]);
   }

   String getWebsite() {
      return this.website;
   }
}
