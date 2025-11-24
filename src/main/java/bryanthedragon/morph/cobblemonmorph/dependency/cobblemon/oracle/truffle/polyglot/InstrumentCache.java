
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.instrumentation.TruffleInstrument;
import com.oracle.truffle.polyglot.EngineAccessor;
import com.oracle.truffle.polyglot.ModuleUtils;
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
    private static final List<InstrumentCache> nativeImageCache = TruffleOptions.AOT ? new ArrayList() : null;
    private static Map<List<EngineAccessor.AbstractClassLoaderSupplier>, List<InstrumentCache>> runtimeCaches = new HashMap<List<EngineAccessor.AbstractClassLoaderSupplier>, List<InstrumentCache>>();
    private final String className;
    private final String id;
    private final String name;
    private final String version;
    private final String website;
    private final boolean internal;
    private final Set<String> services;
    private final TruffleInstrument.Provider provider;

    private static void initializeNativeImageState(ClassLoader imageClassLoader) {
        nativeImageCache.addAll(InstrumentCache.doLoad(Arrays.asList(new EngineAccessor.StrongClassLoaderSupplier(imageClassLoader))));
    }

    private static void resetNativeImageState() {
        nativeImageCache.clear();
        runtimeCaches.clear();
    }

    private InstrumentCache(String id, String name, String version, String className, boolean internal, Set<String> services, TruffleInstrument.Provider provider, String website) {
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static List<InstrumentCache> load() {
        if (TruffleOptions.AOT) {
            return nativeImageCache;
        }
        Class<InstrumentCache> clazz = InstrumentCache.class;
        synchronized (InstrumentCache.class) {
            List<EngineAccessor.AbstractClassLoaderSupplier> classLoaders = EngineAccessor.locatorOrDefaultLoaders();
            List<InstrumentCache> cache = runtimeCaches.get(classLoaders);
            if (cache == null) {
                cache = InstrumentCache.doLoad(classLoaders);
                runtimeCaches.put(classLoaders, cache);
            }
            // ** MonitorExit[var0] (shouldn't be in output)
            return cache;
        }
    }

    static List<InstrumentCache> doLoad(List<EngineAccessor.AbstractClassLoaderSupplier> suppliers) {
        ArrayList<InstrumentCache> list = new ArrayList<InstrumentCache>();
        HashSet classNamesUsed = new HashSet();
        for (Supplier supplier : suppliers) {
            ClassLoader loader = (ClassLoader)supplier.get();
            if (loader == null || !InstrumentCache.isValidLoader(loader)) continue;
            if (!TruffleOptions.AOT) {
                ModuleUtils.exportTo(loader, null);
            }
            for (TruffleInstrument.Provider provider : ServiceLoader.load(TruffleInstrument.Provider.class, loader)) {
                InstrumentCache.loadInstrumentImpl(provider, list, classNamesUsed);
            }
            if (classNamesUsed.contains(DEBUGGER_CLASS)) continue;
            try {
                InstrumentCache.loadInstrumentImpl((TruffleInstrument.Provider)loader.loadClass(DEBUGGER_PROVIDER).getConstructor(new Class[0]).newInstance(new Object[0]), list, classNamesUsed);
            }
            catch (Exception e) {
                throw CompilerDirectives.shouldNotReachHere("Failed to discover debugger instrument.", e);
            }
        }
        Collections.sort(list, new Comparator<InstrumentCache>(){

            @Override
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
            return;
        }
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
        TreeSet<String> servicesClassNames = new TreeSet<String>();
        for (String service2 : provider.getServicesClassNames()) {
            servicesClassNames.add(service2);
        }
        if (!classNamesUsed.contains(className)) {
            classNamesUsed.add(className);
            list.add(new InstrumentCache(id, name, version, className, internal, servicesClassNames, provider, website));
        }
    }

    private static boolean isValidLoader(ClassLoader loader) {
        try {
            Class<?> truffleInstrumentClassAsSeenByLoader = Class.forName(TruffleInstrument.class.getName(), true, loader);
            return truffleInstrumentClassAsSeenByLoader == TruffleInstrument.class;
        }
        catch (ClassNotFoundException ex) {
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

