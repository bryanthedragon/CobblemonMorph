
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.instrumentation.ProvidedTags;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.polyglot.EngineAccessor;
import com.oracle.truffle.polyglot.ModuleUtils;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;

final class LanguageCache
implements Comparable<LanguageCache> {
    private static final Map<String, LanguageCache> nativeImageCache = TruffleOptions.AOT ? new HashMap() : null;
    private static final Map<String, LanguageCache> nativeImageMimes = TruffleOptions.AOT ? new HashMap() : null;
    private static final Set<String> languagesOverridingPatchContext = TruffleOptions.AOT ? new HashSet() : null;
    private static final Map<Collection<EngineAccessor.AbstractClassLoaderSupplier>, Map<String, LanguageCache>> runtimeCaches = new HashMap<Collection<EngineAccessor.AbstractClassLoaderSupplier>, Map<String, LanguageCache>>();
    private static volatile Map<String, LanguageCache> runtimeMimes;
    @CompilerDirectives.CompilationFinal
    private static volatile int maxStaticIndex;
    private final String className;
    private final Set<String> mimeTypes;
    private final Set<String> characterMimeTypes;
    private final String defaultMimeType;
    private final Set<String> dependentLanguages;
    private final String id;
    private final String name;
    private final String implementationName;
    private final String version;
    private final boolean interactive;
    private final boolean internal;
    private final boolean needsAllEncodings;
    private final Set<String> services;
    private final TruffleLanguage.ContextPolicy contextPolicy;
    private final TruffleLanguage.Provider provider;
    private final String website;
    private volatile List<TruffleFile.FileTypeDetector> fileTypeDetectors;
    private volatile Set<Class<? extends Tag>> providedTags;
    private int staticIndex;
    private String languageHome;

    private LanguageCache(String id, String name, String implementationName, String version, String className, String languageHome, Set<String> characterMimeTypes, Set<String> byteMimeTypes, String defaultMimeType, Set<String> dependentLanguages, boolean interactive, boolean internal, boolean needsAllEncodings, Set<String> services, TruffleLanguage.ContextPolicy contextPolicy, TruffleLanguage.Provider provider, String website) {
        assert (provider != null) : "Provider must be non null";
        this.className = className;
        this.name = name;
        this.implementationName = implementationName;
        this.version = version;
        this.characterMimeTypes = characterMimeTypes;
        this.mimeTypes = new TreeSet<String>();
        this.mimeTypes.addAll(characterMimeTypes);
        this.mimeTypes.addAll(byteMimeTypes);
        this.defaultMimeType = this.mimeTypes.size() == 1 && defaultMimeType == null ? this.mimeTypes.iterator().next() : defaultMimeType;
        this.dependentLanguages = dependentLanguages;
        this.id = id;
        this.interactive = interactive;
        this.internal = internal;
        this.needsAllEncodings = needsAllEncodings;
        this.languageHome = languageHome;
        this.services = services;
        this.contextPolicy = contextPolicy;
        this.provider = provider;
        this.website = website;
    }

    static int getMaxStaticIndex() {
        return maxStaticIndex;
    }

    static LanguageCache createHostLanguageCache(TruffleLanguage<Object> languageInstance, String ... services) {
        HostLanguageProvider hostLanguageProvider = new HostLanguageProvider(languageInstance, services);
        LanguageCache cache = new LanguageCache("host", "Host", "Host", System.getProperty("java.version"), languageInstance.getClass().getName(), null, Collections.emptySet(), Collections.emptySet(), null, Collections.emptySet(), false, false, false, (Set<String>)hostLanguageProvider.getServicesClassNames(), TruffleLanguage.ContextPolicy.SHARED, hostLanguageProvider, "");
        cache.staticIndex = 0;
        return cache;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static Map<String, LanguageCache> languageMimes() {
        if (TruffleOptions.AOT) {
            return nativeImageMimes;
        }
        Map<String, LanguageCache> cache = runtimeMimes;
        if (cache != null) return cache;
        Class<LanguageCache> clazz = LanguageCache.class;
        synchronized (LanguageCache.class) {
            cache = runtimeMimes;
            if (cache != null) return cache;
            runtimeMimes = cache = LanguageCache.createMimes();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return cache;
        }
    }

    private static Map<String, LanguageCache> createMimes() {
        LinkedHashMap<String, LanguageCache> mimes = new LinkedHashMap<String, LanguageCache>();
        for (LanguageCache cache : LanguageCache.languages().values()) {
            for (String mime : cache.getMimeTypes()) {
                mimes.put(mime, cache);
            }
        }
        return mimes;
    }

    public static boolean getNeedsAllEncodings() {
        for (LanguageCache cache : LanguageCache.languages().values()) {
            if (!cache.isNeedsAllEncodings()) continue;
            return true;
        }
        return false;
    }

    static Map<String, LanguageCache> languages() {
        return LanguageCache.loadLanguages(EngineAccessor.locatorOrDefaultLoaders());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static Map<String, LanguageCache> loadLanguages(List<EngineAccessor.AbstractClassLoaderSupplier> classLoaders) {
        if (TruffleOptions.AOT) {
            return nativeImageCache;
        }
        Class<LanguageCache> clazz = LanguageCache.class;
        synchronized (LanguageCache.class) {
            Map<String, LanguageCache> cache = runtimeCaches.get(classLoaders);
            if (cache == null) {
                cache = LanguageCache.createLanguages(classLoaders);
                runtimeCaches.put(classLoaders, cache);
            }
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return cache;
        }
    }

    /*
     * WARNING - void declaration
     */
    private static synchronized Map<String, LanguageCache> createLanguages(List<EngineAccessor.AbstractClassLoaderSupplier> suppliers) {
        void var3_6;
        ArrayList<LanguageCache> caches = new ArrayList<LanguageCache>();
        for (Supplier supplier : suppliers) {
            ClassLoader loader = (ClassLoader)supplier.get();
            if (loader == null || !LanguageCache.isValidLoader(loader)) continue;
            if (!TruffleOptions.AOT) {
                ModuleUtils.exportTo(loader, null);
            }
            for (TruffleLanguage.Provider provider : ServiceLoader.load(TruffleLanguage.Provider.class, loader)) {
                LanguageCache.loadLanguageImpl(provider, caches);
            }
        }
        LinkedHashMap<String, LanguageCache> cacheToId = new LinkedHashMap<String, LanguageCache>();
        for (LanguageCache languageCache : caches) {
            LanguageCache prev = cacheToId.put(languageCache.getId(), languageCache);
            if (prev == null || prev.getClassName().equals(languageCache.getClassName()) && LanguageCache.hasSameCodeSource(prev, languageCache)) continue;
            String message = String.format("Duplicate language id %s. First language [%s]. Second language [%s].", languageCache.getId(), LanguageCache.formatLanguageLocation(prev), LanguageCache.formatLanguageLocation(languageCache));
            throw new IllegalStateException(message);
        }
        boolean bl = false;
        for (LanguageCache cache : cacheToId.values()) {
            cache.staticIndex = ++var3_6;
        }
        maxStaticIndex = Math.max(maxStaticIndex, (int)var3_6);
        return cacheToId;
    }

    private static boolean hasSameCodeSource(LanguageCache first, LanguageCache second) {
        assert (first.provider != null && second.provider != null) : "Must not be called for host language cache";
        return first.provider.getClass() == second.provider.getClass();
    }

    private static boolean isValidLoader(ClassLoader loader) {
        try {
            Class<?> truffleLanguageClassAsSeenByLoader = Class.forName(TruffleLanguage.class.getName(), true, loader);
            return truffleLanguageClassAsSeenByLoader == TruffleLanguage.class;
        }
        catch (ClassNotFoundException ex) {
            return false;
        }
    }

    private static void loadLanguageImpl(TruffleLanguage.Provider provider, List<LanguageCache> into) {
        URL url;
        String languageHome;
        TruffleLanguage.Registration reg = provider.getClass().getAnnotation(TruffleLanguage.Registration.class);
        if (reg == null) {
            PrintStream out = System.err;
            out.println("Provider " + provider.getClass() + " is missing @Registration annotation.");
            return;
        }
        String className = provider.getLanguageClassName();
        String name = reg.name();
        String id = reg.id();
        if (id == null || id.isEmpty()) {
            if (name.isEmpty()) {
                int lastIndex = className.lastIndexOf(36);
                if (lastIndex == -1) {
                    lastIndex = className.lastIndexOf(46);
                }
                id = className.substring(lastIndex + 1);
            } else {
                id = name.length() == 1 ? name : name.toLowerCase();
            }
        }
        if ((languageHome = LanguageCache.getLanguageHomeImpl(id)) == null && (url = provider.getClass().getClassLoader().getResource(className.replace('.', '/') + ".class")) != null) {
            try {
                languageHome = LanguageCache.getLanguageHomeFromURLConnection(id, url.openConnection());
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        String implementationName = reg.implementationName();
        String version = reg.version();
        TreeSet<String> characterMimes = new TreeSet<String>();
        Collections.addAll(characterMimes, reg.characterMimeTypes());
        TreeSet<String> byteMimeTypes = new TreeSet<String>();
        Collections.addAll(byteMimeTypes, reg.byteMimeTypes());
        String defaultMime = reg.defaultMimeType();
        if (defaultMime.isEmpty()) {
            defaultMime = null;
        }
        TreeSet<String> dependentLanguages = new TreeSet<String>();
        Collections.addAll(dependentLanguages, reg.dependentLanguages());
        boolean interactive = reg.interactive();
        boolean internal = reg.internal();
        boolean needsAllEncodings = reg.needsAllEncodings();
        TreeSet<String> servicesClassNames = new TreeSet<String>();
        for (String service2 : provider.getServicesClassNames()) {
            servicesClassNames.add(service2);
        }
        into.add(new LanguageCache(id, name, implementationName, version, className, languageHome, characterMimes, byteMimeTypes, defaultMime, dependentLanguages, interactive, internal, needsAllEncodings, servicesClassNames, reg.contextPolicy(), provider, reg.website()));
    }

    private static String getLanguageHomeFromURLConnection(String languageId, URLConnection connection) {
        block4: {
            if (connection instanceof JarURLConnection) {
                try {
                    URL url = ((JarURLConnection)connection).getJarFileURL();
                    if ("file".equals(url.getProtocol())) {
                        Path path = Paths.get(url.toURI());
                        Path parent = path.getParent();
                        return parent != null ? parent.toString() : null;
                    }
                }
                catch (IllegalArgumentException | SecurityException | URISyntaxException | FileSystemNotFoundException e) {
                    if ($assertionsDisabled) break block4;
                    throw new AssertionError((Object)("Cannot locate " + languageId + " language home due to " + e.getMessage()));
                }
            }
        }
        return null;
    }

    private static String formatLanguageLocation(LanguageCache languageCache) {
        URL url;
        StringBuilder sb = new StringBuilder();
        sb.append("Language class ").append(languageCache.getClassName());
        CodeSource source = languageCache.provider != null ? languageCache.provider.getClass().getProtectionDomain().getCodeSource() : null;
        URL uRL = url = source != null ? source.getLocation() : null;
        if (url != null) {
            sb.append(", Loaded from " + url);
        }
        return sb.toString();
    }

    private static String getLanguageHomeImpl(String languageId) {
        String home = System.getProperty("org.graalvm.language." + languageId + ".home");
        if (home == null) {
            home = System.getProperty(languageId + ".home");
        }
        return home;
    }

    static boolean overridesPathContext(String languageId) {
        assert (TruffleOptions.AOT) : "Only supported in native image";
        return languagesOverridingPatchContext.contains(languageId);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static void resetNativeImageCacheLanguageHomes() {
        Class<LanguageCache> clazz = LanguageCache.class;
        synchronized (LanguageCache.class) {
            if (nativeImageCache != null) {
                LanguageCache.resetNativeImageCacheLanguageHomes(nativeImageCache);
            }
            for (Map<String, LanguageCache> caches : runtimeCaches.values()) {
                LanguageCache.resetNativeImageCacheLanguageHomes(caches);
            }
            // ** MonitorExit[var0] (shouldn't be in output)
            return;
        }
    }

    private static void resetNativeImageCacheLanguageHomes(Map<String, LanguageCache> caches) {
        for (LanguageCache cache : caches.values()) {
            cache.languageHome = null;
        }
    }

    private static void initializeNativeImageState(ClassLoader imageClassLoader) {
        assert (TruffleOptions.AOT) : "Only supported during image generation";
        nativeImageCache.putAll(LanguageCache.createLanguages(Arrays.asList(new EngineAccessor.StrongClassLoaderSupplier(imageClassLoader))));
        nativeImageMimes.putAll(LanguageCache.createMimes());
        block2: for (LanguageCache languageCache : nativeImageCache.values()) {
            try {
                Class<?> clz = Class.forName(languageCache.className, false, imageClassLoader);
                for (Method m : clz.getDeclaredMethods()) {
                    if (!m.getName().equals("patchContext")) continue;
                    languagesOverridingPatchContext.add(languageCache.id);
                    continue block2;
                }
            }
            catch (ReflectiveOperationException roe) {
                PrintStream out = System.err;
                out.println("Failed to lookup patchContext method. " + roe);
            }
        }
    }

    private static void resetNativeImageState() {
        assert (TruffleOptions.AOT) : "Only supported during image generation";
        nativeImageCache.clear();
        nativeImageMimes.clear();
    }

    private static void removeLanguageFromNativeImage(String languageId) {
        assert (TruffleOptions.AOT) : "Only supported during image generation";
        assert (nativeImageCache.containsKey(languageId));
        LanguageCache cache = nativeImageCache.remove(languageId);
        if (cache != null) {
            for (String mime : cache.getMimeTypes()) {
                if (nativeImageCache.get(mime) != cache) continue;
                nativeImageMimes.remove(mime);
            }
        }
    }

    int getStaticIndex() {
        return this.staticIndex;
    }

    @Override
    public int compareTo(LanguageCache o) {
        return this.id.compareTo(o.id);
    }

    String getId() {
        return this.id;
    }

    Set<String> getMimeTypes() {
        return this.mimeTypes;
    }

    String getDefaultMimeType() {
        return this.defaultMimeType;
    }

    boolean isCharacterMimeType(String mimeType) {
        return this.characterMimeTypes.contains(mimeType);
    }

    String getName() {
        return this.name;
    }

    String getImplementationName() {
        return this.implementationName;
    }

    Set<String> getDependentLanguages() {
        return this.dependentLanguages;
    }

    String getVersion() {
        return this.version;
    }

    String getClassName() {
        return this.className;
    }

    boolean isInternal() {
        return this.internal;
    }

    boolean isInteractive() {
        return this.interactive;
    }

    public boolean isNeedsAllEncodings() {
        return this.needsAllEncodings;
    }

    String getLanguageHome() {
        if (this.languageHome == null) {
            this.languageHome = LanguageCache.getLanguageHomeImpl(this.id);
        }
        return this.languageHome;
    }

    TruffleLanguage<?> loadLanguage() {
        return this.provider.create();
    }

    Set<? extends Class<? extends Tag>> getProvidedTags() {
        Set<Class<Tag>> res = this.providedTags;
        if (res == null) {
            ProvidedTags tags = this.provider.getClass().getAnnotation(ProvidedTags.class);
            if (tags == null) {
                res = Collections.emptySet();
            } else {
                res = new HashSet<Class<? extends Tag>>();
                Collections.addAll(res, tags.value());
                res = Collections.unmodifiableSet(res);
            }
            this.providedTags = res;
        }
        return res;
    }

    TruffleLanguage.ContextPolicy getPolicy() {
        return this.contextPolicy;
    }

    Collection<String> getServices() {
        return this.services;
    }

    boolean supportsService(Class<?> clazz) {
        return this.services.contains(clazz.getName()) || this.services.contains(clazz.getCanonicalName());
    }

    List<? extends TruffleFile.FileTypeDetector> getFileTypeDetectors() {
        List<TruffleFile.FileTypeDetector> result = this.fileTypeDetectors;
        if (result == null) {
            this.fileTypeDetectors = result = this.provider.createFileTypeDetectors();
        }
        return result;
    }

    public String toString() {
        return "LanguageCache [id=" + this.id + ", name=" + this.name + ", implementationName=" + this.implementationName + ", version=" + this.version + ", className=" + this.className + ", services=" + this.services + "]";
    }

    String getWebsite() {
        return this.website;
    }

    private static final class HostLanguageProvider
    implements TruffleLanguage.Provider {
        private final TruffleLanguage<?> languageInstance;
        private final Set<String> servicesClassNames;

        HostLanguageProvider(TruffleLanguage<?> languageInstance, String ... services) {
            assert (languageInstance != null) : "LanguageInstance must be non null.";
            this.languageInstance = languageInstance;
            if (services.length == 0) {
                this.servicesClassNames = Collections.emptySet();
            } else {
                TreeSet treeSet = new TreeSet();
                Collections.addAll(treeSet, services);
                this.servicesClassNames = Collections.unmodifiableSet(treeSet);
            }
        }

        @Override
        public String getLanguageClassName() {
            return this.languageInstance.getClass().getName();
        }

        @Override
        public TruffleLanguage<?> create() {
            return this.languageInstance;
        }

        @Override
        public List<TruffleFile.FileTypeDetector> createFileTypeDetectors() {
            return Collections.emptyList();
        }

        public Set<String> getServicesClassNames() {
            return this.servicesClassNames;
        }
    }
}

