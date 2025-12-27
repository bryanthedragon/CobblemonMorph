package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.instrumentation.ProvidedTags;
import com.oracle.truffle.api.instrumentation.Tag;
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

final class LanguageCache implements Comparable<LanguageCache> {
   private static final Map<String, LanguageCache> nativeImageCache = TruffleOptions.AOT ? new HashMap<>() : null;
   private static final Map<String, LanguageCache> nativeImageMimes = TruffleOptions.AOT ? new HashMap<>() : null;
   private static final Set<String> languagesOverridingPatchContext = TruffleOptions.AOT ? new HashSet<>() : null;
   private static final Map<Collection<EngineAccessor.AbstractClassLoaderSupplier>, Map<String, LanguageCache>> runtimeCaches = new HashMap<>();
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

   private LanguageCache(
      String id,
      String name,
      String implementationName,
      String version,
      String className,
      String languageHome,
      Set<String> characterMimeTypes,
      Set<String> byteMimeTypes,
      String defaultMimeType,
      Set<String> dependentLanguages,
      boolean interactive,
      boolean internal,
      boolean needsAllEncodings,
      Set<String> services,
      TruffleLanguage.ContextPolicy contextPolicy,
      TruffleLanguage.Provider provider,
      String website
   ) {
      assert provider != null : "Provider must be non null";

      this.className = className;
      this.name = name;
      this.implementationName = implementationName;
      this.version = version;
      this.characterMimeTypes = characterMimeTypes;
      this.mimeTypes = new TreeSet<>();
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

   static LanguageCache createHostLanguageCache(TruffleLanguage<Object> languageInstance, String... services) {
      LanguageCache.HostLanguageProvider hostLanguageProvider = new LanguageCache.HostLanguageProvider(languageInstance, services);
      LanguageCache cache = new LanguageCache(
         "host",
         "Host",
         "Host",
         System.getProperty("java.version"),
         languageInstance.getClass().getName(),
         null,
         Collections.emptySet(),
         Collections.emptySet(),
         null,
         Collections.emptySet(),
         false,
         false,
         false,
         hostLanguageProvider.getServicesClassNames(),
         TruffleLanguage.ContextPolicy.SHARED,
         hostLanguageProvider,
         ""
      );
      cache.staticIndex = 0;
      return cache;
   }

   static Map<String, LanguageCache> languageMimes() {
      if (TruffleOptions.AOT) {
         return nativeImageMimes;
      } else {
         Map<String, LanguageCache> cache = runtimeMimes;
         if (cache == null) {
            synchronized (LanguageCache.class) {
               cache = runtimeMimes;
               if (cache == null) {
                  runtimeMimes = cache = createMimes();
               }
            }
         }

         return cache;
      }
   }

   private static Map<String, LanguageCache> createMimes() {
      Map<String, LanguageCache> mimes = new LinkedHashMap<>();

      for (LanguageCache cache : languages().values()) {
         for (String mime : cache.getMimeTypes()) {
            mimes.put(mime, cache);
         }
      }

      return mimes;
   }

   public static boolean getNeedsAllEncodings() {
      for (LanguageCache cache : languages().values()) {
         if (cache.isNeedsAllEncodings()) {
            return true;
         }
      }

      return false;
   }

   static Map<String, LanguageCache> languages() {
      return loadLanguages(EngineAccessor.locatorOrDefaultLoaders());
   }

   static Map<String, LanguageCache> loadLanguages(List<EngineAccessor.AbstractClassLoaderSupplier> classLoaders) {
      if (TruffleOptions.AOT) {
         return nativeImageCache;
      } else {
         synchronized (LanguageCache.class) {
            Map<String, LanguageCache> cache = runtimeCaches.get(classLoaders);
            if (cache == null) {
               cache = createLanguages(classLoaders);
               runtimeCaches.put(classLoaders, cache);
            }

            return cache;
         }
      }
   }

   private static synchronized Map<String, LanguageCache> createLanguages(List<EngineAccessor.AbstractClassLoaderSupplier> suppliers) {
      List<LanguageCache> caches = new ArrayList<>();

      for (Supplier<ClassLoader> supplier : suppliers) {
         ClassLoader loader = supplier.get();
         if (loader != null && isValidLoader(loader)) {
            if (!TruffleOptions.AOT) {
               ModuleUtils.exportTo(loader, null);
            }

            for (TruffleLanguage.Provider provider : ServiceLoader.load(TruffleLanguage.Provider.class, loader)) {
               loadLanguageImpl(provider, caches);
            }
         }
      }

      Map<String, LanguageCache> cacheToId = new LinkedHashMap<>();

      for (LanguageCache languageCache : caches) {
         LanguageCache prev = cacheToId.put(languageCache.getId(), languageCache);
         if (prev != null && (!prev.getClassName().equals(languageCache.getClassName()) || !hasSameCodeSource(prev, languageCache))) {
            String message = String.format(
               "Duplicate language id %s. First language [%s]. Second language [%s].",
               languageCache.getId(),
               formatLanguageLocation(prev),
               formatLanguageLocation(languageCache)
            );
            throw new IllegalStateException(message);
         }
      }

      int languageId = 0;

      for (LanguageCache cache : cacheToId.values()) {
         cache.staticIndex = ++languageId;
      }

      maxStaticIndex = Math.max(maxStaticIndex, languageId);
      return cacheToId;
   }

   private static boolean hasSameCodeSource(LanguageCache first, LanguageCache second) {
      assert first.provider != null && second.provider != null : "Must not be called for host language cache";

      return first.provider.getClass() == second.provider.getClass();
   }

   private static boolean isValidLoader(ClassLoader loader) {
      try {
         Class<?> truffleLanguageClassAsSeenByLoader = Class.forName(TruffleLanguage.class.getName(), true, loader);
         return truffleLanguageClassAsSeenByLoader == TruffleLanguage.class;
      } catch (ClassNotFoundException var2) {
         return false;
      }
   }

   private static void loadLanguageImpl(TruffleLanguage.Provider provider, List<LanguageCache> into) {
      TruffleLanguage.Registration reg = provider.getClass().getAnnotation(TruffleLanguage.Registration.class);
      if (reg == null) {
         PrintStream out = System.err;
         out.println("Provider " + provider.getClass() + " is missing @Registration annotation.");
      } else {
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
            } else if (name.length() == 1) {
               id = name;
            } else {
               id = name.toLowerCase();
            }
         }

         String languageHome = getLanguageHomeImpl(id);
         if (languageHome == null) {
            URL url = provider.getClass().getClassLoader().getResource(className.replace('.', '/') + ".class");
            if (url != null) {
               try {
                  languageHome = getLanguageHomeFromURLConnection(id, url.openConnection());
               } catch (IOException var19) {
               }
            }
         }

         String implementationName = reg.implementationName();
         String version = reg.version();
         TreeSet<String> characterMimes = new TreeSet<>();
         Collections.addAll(characterMimes, reg.characterMimeTypes());
         TreeSet<String> byteMimeTypes = new TreeSet<>();
         Collections.addAll(byteMimeTypes, reg.byteMimeTypes());
         String defaultMime = reg.defaultMimeType();
         if (defaultMime.isEmpty()) {
            defaultMime = null;
         }

         TreeSet<String> dependentLanguages = new TreeSet<>();
         Collections.addAll(dependentLanguages, reg.dependentLanguages());
         boolean interactive = reg.interactive();
         boolean internal = reg.internal();
         boolean needsAllEncodings = reg.needsAllEncodings();
         Set<String> servicesClassNames = new TreeSet<>();

         for (String service : provider.getServicesClassNames()) {
            servicesClassNames.add(service);
         }

         into.add(
            new LanguageCache(
               id,
               name,
               implementationName,
               version,
               className,
               languageHome,
               characterMimes,
               byteMimeTypes,
               defaultMime,
               dependentLanguages,
               interactive,
               internal,
               needsAllEncodings,
               servicesClassNames,
               reg.contextPolicy(),
               provider,
               reg.website()
            )
         );
      }
   }

   private static String getLanguageHomeFromURLConnection(String languageId, URLConnection connection) {
      if (connection instanceof JarURLConnection) {
         try {
            URL url = ((JarURLConnection)connection).getJarFileURL();
            if ("file".equals(url.getProtocol())) {
               Path path = Paths.get(url.toURI());
               Path parent = path.getParent();
               return parent != null ? parent.toString() : null;
            }
         } catch (FileSystemNotFoundException | IllegalArgumentException | SecurityException | URISyntaxException var5) {
            assert false : "Cannot locate " + languageId + " language home due to " + var5.getMessage();
         }
      }

      return null;
   }

   private static String formatLanguageLocation(LanguageCache languageCache) {
      StringBuilder sb = new StringBuilder();
      sb.append("Language class ").append(languageCache.getClassName());
      CodeSource source = languageCache.provider != null ? languageCache.provider.getClass().getProtectionDomain().getCodeSource() : null;
      URL url = source != null ? source.getLocation() : null;
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
      assert TruffleOptions.AOT : "Only supported in native image";

      return languagesOverridingPatchContext.contains(languageId);
   }

   static void resetNativeImageCacheLanguageHomes() {
      synchronized (LanguageCache.class) {
         if (nativeImageCache != null) {
            resetNativeImageCacheLanguageHomes(nativeImageCache);
         }

         for (Map<String, LanguageCache> caches : runtimeCaches.values()) {
            resetNativeImageCacheLanguageHomes(caches);
         }
      }
   }

   private static void resetNativeImageCacheLanguageHomes(Map<String, LanguageCache> caches) {
      for (LanguageCache cache : caches.values()) {
         cache.languageHome = null;
      }
   }

   private static void initializeNativeImageState(ClassLoader imageClassLoader) {
      assert TruffleOptions.AOT : "Only supported during image generation";

      nativeImageCache.putAll(createLanguages(Arrays.asList(new EngineAccessor.StrongClassLoaderSupplier(imageClassLoader))));
      nativeImageMimes.putAll(createMimes());

      for (LanguageCache languageCache : nativeImageCache.values()) {
         try {
            Class<?> clz = Class.forName(languageCache.className, false, imageClassLoader);

            for (Method m : clz.getDeclaredMethods()) {
               if (m.getName().equals("patchContext")) {
                  languagesOverridingPatchContext.add(languageCache.id);
                  break;
               }
            }
         } catch (ReflectiveOperationException var8) {
            PrintStream out = System.err;
            out.println("Failed to lookup patchContext method. " + var8);
         }
      }
   }

   private static void resetNativeImageState() {
      assert TruffleOptions.AOT : "Only supported during image generation";

      nativeImageCache.clear();
      nativeImageMimes.clear();
   }

   private static void removeLanguageFromNativeImage(String languageId) {
      assert TruffleOptions.AOT : "Only supported during image generation";

      assert nativeImageCache.containsKey(languageId);

      LanguageCache cache = nativeImageCache.remove(languageId);
      if (cache != null) {
         for (String mime : cache.getMimeTypes()) {
            if (nativeImageCache.get(mime) == cache) {
               nativeImageMimes.remove(mime);
            }
         }
      }
   }

   int getStaticIndex() {
      return this.staticIndex;
   }

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
         this.languageHome = getLanguageHomeImpl(this.id);
      }

      return this.languageHome;
   }

   TruffleLanguage<?> loadLanguage() {
      return this.provider.create();
   }

   Set<? extends Class<? extends Tag>> getProvidedTags() {
      Set<Class<? extends Tag>> res = this.providedTags;
      if (res == null) {
         ProvidedTags tags = this.provider.getClass().getAnnotation(ProvidedTags.class);
         if (tags == null) {
            res = Collections.emptySet();
         } else {
            Set<Class<? extends Tag>> var3 = new HashSet();
            Collections.addAll(var3, tags.value());
            res = Collections.unmodifiableSet(var3);
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
         result = this.provider.createFileTypeDetectors();
         this.fileTypeDetectors = result;
      }

      return result;
   }

   @Override
   public String toString() {
      return "LanguageCache [id="
         + this.id
         + ", name="
         + this.name
         + ", implementationName="
         + this.implementationName
         + ", version="
         + this.version
         + ", className="
         + this.className
         + ", services="
         + this.services
         + "]";
   }

   String getWebsite() {
      return this.website;
   }

   private static final class HostLanguageProvider implements TruffleLanguage.Provider {
      private final TruffleLanguage<?> languageInstance;
      private final Set<String> servicesClassNames;

      HostLanguageProvider(TruffleLanguage<?> languageInstance, String... services) {
         assert languageInstance != null : "LanguageInstance must be non null.";

         this.languageInstance = languageInstance;
         if (services.length == 0) {
            this.servicesClassNames = Collections.emptySet();
         } else {
            Set<String> treeSet = new TreeSet<>();
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
