package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.nodes.LanguageInfo;
import java.io.File;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Supplier;
import org.graalvm.nativeimage.ImageInfo;
import org.graalvm.polyglot.io.FileSystem;

final class FileSystems {
   static final String FILE_SCHEME = "file";
   static final FileSystem INVALID_FILESYSTEM = new FileSystems.InvalidFileSystem();
   private static final AtomicReference<FileSystemProvider> DEFAULT_FILE_SYSTEM_PROVIDER = new AtomicReference<>();
   private static final String TMP_FILE = System.getProperty("java.io.tmpdir");

   private FileSystems() {
      throw new IllegalStateException("No instance allowed");
   }

   static FileSystem newDefaultFileSystem() {
      return newFileSystem(findDefaultFileSystemProvider());
   }

   static FileSystem newDefaultFileSystem(Path userDir) {
      return newFileSystem(findDefaultFileSystemProvider(), userDir);
   }

   static FileSystem allowLanguageHomeAccess(FileSystem fileSystem) {
      return new FileSystems.LanguageHomeFileSystem(newDefaultFileSystem(), fileSystem);
   }

   static FileSystem newReadOnlyFileSystem(FileSystem fileSystem) {
      return new FileSystems.ReadOnlyFileSystem(fileSystem);
   }

   static FileSystem newNoIOFileSystem() {
      return new FileSystems.DeniedIOFileSystem();
   }

   static FileSystem newLanguageHomeFileSystem() {
      FileSystem defaultFS = newDefaultFileSystem();
      return new FileSystems.LanguageHomeFileSystem(new FileSystems.ReadOnlyFileSystem(defaultFS), new FileSystems.PathOperationsOnlyFileSystem(defaultFS));
   }

   static boolean hasAllAccess(FileSystem fileSystem) {
      return fileSystem instanceof FileSystems.PolyglotFileSystem && ((FileSystems.PolyglotFileSystem)fileSystem).hasAllAccess();
   }

   static boolean hasNoAccess(FileSystem fileSystem) {
      return fileSystem instanceof FileSystems.PolyglotFileSystem && ((FileSystems.PolyglotFileSystem)fileSystem).hasNoAccess();
   }

   static boolean isInternal(FileSystem fileSystem) {
      return fileSystem instanceof FileSystems.PolyglotFileSystem && ((FileSystems.PolyglotFileSystem)fileSystem).isInternal();
   }

   static Supplier<Map<String, Collection<? extends TruffleFile.FileTypeDetector>>> newFileTypeDetectorsSupplier(Iterable<LanguageCache> languageCaches) {
      return new FileSystems.FileTypeDetectorsSupplier(languageCaches);
   }

   static void resetDefaultFileSystemProvider() {
      DEFAULT_FILE_SYSTEM_PROVIDER.set(null);
   }

   static String getRelativePathInLanguageHome(TruffleFile file) {
      Object engineObject = EngineAccessor.LANGUAGE.getFileSystemEngineObject(EngineAccessor.LANGUAGE.getFileSystemContext(file));
      if (engineObject instanceof PolyglotLanguageContext) {
         PolyglotLanguageContext context = (PolyglotLanguageContext)engineObject;
         FileSystem fs = EngineAccessor.LANGUAGE.getFileSystem(file);
         Path path = EngineAccessor.LANGUAGE.getPath(file);
         String result = relativizeToLanguageHome(fs, path, context.language);
         if (result != null) {
            return result;
         } else {
            Map<String, LanguageInfo> accessibleLanguages = context.getAccessibleLanguages(true);
            if (accessibleLanguages != null) {
               for (LanguageInfo language : accessibleLanguages.values()) {
                  PolyglotLanguage lang = context.context.engine.idToLanguage.get(language.getId());
                  result = relativizeToLanguageHome(fs, path, lang);
                  if (result != null) {
                     return result;
                  }
               }
            }

            return null;
         }
      } else if (engineObject instanceof PolyglotImpl.EmbedderFileSystemContext) {
         return null;
      } else {
         throw new AssertionError();
      }
   }

   private static String relativizeToLanguageHome(FileSystem fs, Path path, PolyglotLanguage language) {
      String languageHome = language.cache.getLanguageHome();
      if (languageHome == null) {
         return null;
      } else {
         Path languageHomePath = fs.parsePath(language.cache.getLanguageHome());
         return path.startsWith(languageHomePath) ? languageHomePath.relativize(path).toString() : null;
      }
   }

   private static FileSystem newFileSystem(final FileSystemProvider fileSystemProvider) {
      return new FileSystems.NIOFileSystem(fileSystemProvider);
   }

   private static FileSystem newFileSystem(final FileSystemProvider fileSystemProvider, final Path userDir) {
      return new FileSystems.NIOFileSystem(fileSystemProvider, userDir);
   }

   private static FileSystemProvider findDefaultFileSystemProvider() {
      FileSystemProvider defaultFsProvider = DEFAULT_FILE_SYSTEM_PROVIDER.get();
      if (defaultFsProvider == null) {
         for (FileSystemProvider fsp : FileSystemProvider.installedProviders()) {
            if ("file".equals(fsp.getScheme())) {
               defaultFsProvider = fsp;
               break;
            }
         }

         if (defaultFsProvider == null) {
            throw new IllegalStateException("No FileSystemProvider for scheme 'file'.");
         }

         DEFAULT_FILE_SYSTEM_PROVIDER.set(defaultFsProvider);
      }

      return defaultFsProvider;
   }

   private static boolean isFollowLinks(final LinkOption... linkOptions) {
      for (LinkOption lo : linkOptions) {
         if (Objects.requireNonNull(lo) == LinkOption.NOFOLLOW_LINKS) {
            return false;
         }
      }

      return true;
   }

   private static SecurityException forbidden(final Path path) {
      throw new SecurityException(path == null ? "Operation is not allowed." : "Operation is not allowed for: " + path);
   }

   private static class DeniedIOFileSystem implements FileSystems.PolyglotFileSystem {
      private final FileSystemProvider defaultFileSystemProvider = FileSystems.findDefaultFileSystemProvider();

      DeniedIOFileSystem() {
      }

      @Override
      public boolean isInternal() {
         return true;
      }

      @Override
      public boolean hasAllAccess() {
         return false;
      }

      @Override
      public boolean hasNoAccess() {
         return true;
      }

      @Override
      public Path parsePath(final URI uri) {
         if (!this.defaultFileSystemProvider.getScheme().equals(uri.getScheme())) {
            throw new UnsupportedOperationException("Unsupported URI scheme " + uri.getScheme());
         } else {
            try {
               return this.defaultFileSystemProvider.getPath(uri);
            } catch (FileSystemNotFoundException | IllegalArgumentException var3) {
               throw new UnsupportedOperationException(var3);
            }
         }
      }

      @Override
      public Path parsePath(final String path) {
         return Paths.get(path);
      }

      @Override
      public void checkAccess(Path path, Set<? extends AccessMode> modes, LinkOption... linkOptions) throws IOException {
         throw FileSystems.forbidden(path);
      }

      @Override
      public void createDirectory(Path dir, FileAttribute<?>... attrs) throws IOException {
         throw FileSystems.forbidden(dir);
      }

      @Override
      public void delete(Path path) throws IOException {
         throw FileSystems.forbidden(path);
      }

      @Override
      public void copy(Path source, Path target, CopyOption... options) throws IOException {
         throw FileSystems.forbidden(source);
      }

      @Override
      public void move(Path source, Path target, CopyOption... options) throws IOException {
         throw FileSystems.forbidden(source);
      }

      @Override
      public SeekableByteChannel newByteChannel(Path inPath, Set<? extends OpenOption> options, FileAttribute<?>... attrs) throws IOException {
         throw FileSystems.forbidden(inPath);
      }

      @Override
      public DirectoryStream<Path> newDirectoryStream(Path dir, Filter<? super Path> filter) throws IOException {
         throw FileSystems.forbidden(dir);
      }

      @Override
      public Map<String, Object> readAttributes(Path path, String attributes, LinkOption... options) throws IOException {
         throw FileSystems.forbidden(path);
      }

      @Override
      public void setAttribute(Path path, String attribute, Object value, LinkOption... options) throws IOException {
         throw FileSystems.forbidden(path);
      }

      @Override
      public Path toAbsolutePath(Path path) {
         throw FileSystems.forbidden(path);
      }

      @Override
      public void setCurrentWorkingDirectory(Path currentWorkingDirectory) {
      }

      @Override
      public Path toRealPath(Path path, LinkOption... linkOptions) throws IOException {
         throw FileSystems.forbidden(path);
      }

      @Override
      public Path getTempDirectory() {
         throw FileSystems.forbidden(null);
      }

      @Override
      public void createLink(Path link, Path existing) throws IOException {
         throw FileSystems.forbidden(link);
      }

      @Override
      public void createSymbolicLink(Path link, Path target, FileAttribute<?>... attrs) throws IOException {
         throw FileSystems.forbidden(link);
      }

      @Override
      public Path readSymbolicLink(Path link) throws IOException {
         throw FileSystems.forbidden(link);
      }

      @Override
      public boolean isSameFile(Path path1, Path path2, LinkOption... options) throws IOException {
         throw FileSystems.forbidden(path1);
      }
   }

   private static final class FileTypeDetectorsSupplier implements Supplier<Map<String, Collection<? extends TruffleFile.FileTypeDetector>>> {
      private final Iterable<LanguageCache> languageCaches;

      FileTypeDetectorsSupplier(Iterable<LanguageCache> languageCaches) {
         this.languageCaches = languageCaches;
      }

      public Map<String, Collection<? extends TruffleFile.FileTypeDetector>> get() {
         Map<String, Collection<? extends TruffleFile.FileTypeDetector>> detectors = new HashMap<>();

         for (LanguageCache cache : this.languageCaches) {
            for (String mimeType : cache.getMimeTypes()) {
               Collection<? extends TruffleFile.FileTypeDetector> languageDetectors = cache.getFileTypeDetectors();
               Collection<? extends TruffleFile.FileTypeDetector> mimeTypeDetectors = detectors.get(mimeType);
               if (mimeTypeDetectors != null) {
                  if (!languageDetectors.isEmpty()) {
                     Collection<TruffleFile.FileTypeDetector> mergedDetectors = new ArrayList<>(mimeTypeDetectors);
                     mergedDetectors.addAll(languageDetectors);
                     detectors.put(mimeType, mergedDetectors);
                  }
               } else {
                  detectors.put(mimeType, languageDetectors);
               }
            }
         }

         return detectors;
      }
   }

   private static final class InvalidFileSystem implements FileSystems.PolyglotFileSystem {
      @Override
      public boolean isInternal() {
         return true;
      }

      @Override
      public boolean hasAllAccess() {
         return false;
      }

      @Override
      public boolean hasNoAccess() {
         return true;
      }

      @Override
      public Path parsePath(URI uri) {
         throw new UnsupportedOperationException("ParsePath not supported on InvalidFileSystem");
      }

      @Override
      public Path parsePath(String path) {
         throw new UnsupportedOperationException("ParsePath not supported on InvalidFileSystem");
      }

      @Override
      public void checkAccess(Path path, Set<? extends AccessMode> modes, LinkOption... linkOptions) throws IOException {
         throw FileSystems.forbidden(path);
      }

      @Override
      public void createDirectory(Path dir, FileAttribute<?>... attrs) throws IOException {
         throw FileSystems.forbidden(dir);
      }

      @Override
      public void delete(Path path) throws IOException {
         throw FileSystems.forbidden(path);
      }

      @Override
      public SeekableByteChannel newByteChannel(Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs) throws IOException {
         throw FileSystems.forbidden(path);
      }

      @Override
      public DirectoryStream<Path> newDirectoryStream(Path dir, Filter<? super Path> filter) throws IOException {
         throw FileSystems.forbidden(dir);
      }

      @Override
      public Path toAbsolutePath(Path path) {
         throw FileSystems.forbidden(path);
      }

      @Override
      public Path toRealPath(Path path, LinkOption... linkOptions) throws IOException {
         throw FileSystems.forbidden(path);
      }

      @Override
      public Map<String, Object> readAttributes(Path path, String attributes, LinkOption... options) throws IOException {
         throw FileSystems.forbidden(path);
      }

      @Override
      public void setAttribute(Path path, String attribute, Object value, LinkOption... options) throws IOException {
         throw FileSystems.forbidden(path);
      }

      @Override
      public void copy(Path source, Path target, CopyOption... options) throws IOException {
         throw FileSystems.forbidden(source);
      }

      @Override
      public void move(Path source, Path target, CopyOption... options) throws IOException {
         throw FileSystems.forbidden(source);
      }

      @Override
      public void createLink(Path link, Path existing) throws IOException {
         throw FileSystems.forbidden(link);
      }

      @Override
      public void createSymbolicLink(Path link, Path target, FileAttribute<?>... attrs) throws IOException {
         throw FileSystems.forbidden(link);
      }

      @Override
      public Path readSymbolicLink(Path link) throws IOException {
         throw FileSystems.forbidden(link);
      }

      @Override
      public void setCurrentWorkingDirectory(Path currentWorkingDirectory) {
         throw FileSystems.forbidden(currentWorkingDirectory);
      }
   }

   private static final class LanguageHomeFileSystem implements FileSystems.PolyglotFileSystem {
      private final FileSystem languageHomeFileSystem;
      private final FileSystem delegateFileSystem;
      private volatile Set<Path> languageHomes;

      LanguageHomeFileSystem(FileSystem languageHomeFileSystem, FileSystem delegateFileSystem) {
         this.languageHomeFileSystem = languageHomeFileSystem;
         this.delegateFileSystem = delegateFileSystem;
         Class<? extends Path> languageHomeFileSystemPathType = (Class<? extends Path>)this.languageHomeFileSystem.parsePath("").getClass();
         Class<? extends Path> customFileSystemPathType = (Class<? extends Path>)delegateFileSystem.parsePath("").getClass();
         if (languageHomeFileSystemPathType != customFileSystemPathType) {
            throw new IllegalArgumentException("Given FileSystem must have the same Path type as the default FileSystem.");
         } else if (!languageHomeFileSystem.getSeparator().equals(delegateFileSystem.getSeparator())) {
            throw new IllegalArgumentException("Given FileSystem must use the same separator character as the default FileSystem.");
         } else if (!languageHomeFileSystem.getPathSeparator().equals(delegateFileSystem.getPathSeparator())) {
            throw new IllegalArgumentException("Given FileSystem must use the same path separator character as the default FileSystem.");
         }
      }

      @Override
      public boolean isInternal() {
         return this.delegateFileSystem instanceof FileSystems.PolyglotFileSystem && ((FileSystems.PolyglotFileSystem)this.delegateFileSystem).isInternal();
      }

      @Override
      public boolean hasAllAccess() {
         return this.delegateFileSystem instanceof FileSystems.PolyglotFileSystem && ((FileSystems.PolyglotFileSystem)this.delegateFileSystem).hasAllAccess();
      }

      @Override
      public boolean hasNoAccess() {
         return this.delegateFileSystem instanceof FileSystems.PolyglotFileSystem && ((FileSystems.PolyglotFileSystem)this.delegateFileSystem).hasNoAccess();
      }

      @Override
      public Path parsePath(URI uri) {
         return this.delegateFileSystem.parsePath(uri);
      }

      @Override
      public Path parsePath(String path) {
         return this.delegateFileSystem.parsePath(path);
      }

      @Override
      public void checkAccess(Path path, Set<? extends AccessMode> modes, LinkOption... linkOptions) throws IOException {
         Path absolutePath = this.toNormalizedAbsolutePath(path);
         if (this.inLanguageHome(absolutePath)) {
            this.languageHomeFileSystem.checkAccess(absolutePath, modes, linkOptions);
         } else {
            this.delegateFileSystem.checkAccess(path, modes, linkOptions);
         }
      }

      @Override
      public void createDirectory(Path dir, FileAttribute<?>... attrs) throws IOException {
         Path absolutePath = this.toNormalizedAbsolutePath(dir);
         if (this.inLanguageHome(absolutePath)) {
            this.languageHomeFileSystem.createDirectory(absolutePath, attrs);
         } else {
            this.delegateFileSystem.createDirectory(dir, attrs);
         }
      }

      @Override
      public void delete(Path path) throws IOException {
         Path absolutePath = this.toNormalizedAbsolutePath(path);
         if (this.inLanguageHome(absolutePath)) {
            this.languageHomeFileSystem.delete(absolutePath);
         } else {
            this.delegateFileSystem.delete(path);
         }
      }

      @Override
      public SeekableByteChannel newByteChannel(Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs) throws IOException {
         Path absolutePath = this.toNormalizedAbsolutePath(path);
         return this.inLanguageHome(absolutePath)
            ? this.languageHomeFileSystem.newByteChannel(absolutePath, options, attrs)
            : this.delegateFileSystem.newByteChannel(path, options, attrs);
      }

      @Override
      public DirectoryStream<Path> newDirectoryStream(Path dir, Filter<? super Path> filter) throws IOException {
         Path absolutePath = this.toNormalizedAbsolutePath(dir);
         return this.inLanguageHome(absolutePath)
            ? this.languageHomeFileSystem.newDirectoryStream(absolutePath, filter)
            : this.delegateFileSystem.newDirectoryStream(dir, filter);
      }

      @Override
      public Path toAbsolutePath(Path path) {
         return this.delegateFileSystem.toAbsolutePath(path);
      }

      @Override
      public Path toRealPath(Path path, LinkOption... linkOptions) throws IOException {
         Path absolutePath = this.toNormalizedAbsolutePath(path);
         return this.inLanguageHome(absolutePath) ? this.languageHomeFileSystem.toRealPath(path) : this.delegateFileSystem.toRealPath(path);
      }

      @Override
      public Map<String, Object> readAttributes(Path path, String attributes, LinkOption... options) throws IOException {
         Path absolutePath = this.toNormalizedAbsolutePath(path);
         return this.inLanguageHome(absolutePath)
            ? this.languageHomeFileSystem.readAttributes(absolutePath, attributes, options)
            : this.delegateFileSystem.readAttributes(path, attributes, options);
      }

      @Override
      public void setAttribute(Path path, String attribute, Object value, LinkOption... options) throws IOException {
         Path absolutePath = this.toNormalizedAbsolutePath(path);
         if (this.inLanguageHome(absolutePath)) {
            this.languageHomeFileSystem.setAttribute(absolutePath, attribute, value, options);
         } else {
            this.delegateFileSystem.setAttribute(path, attribute, value, options);
         }
      }

      @Override
      public void createLink(Path link, Path existing) throws IOException {
         Path absoluteLink = this.toNormalizedAbsolutePath(link);
         Path absoluteExisting = this.toNormalizedAbsolutePath(existing);
         boolean linkInHome = this.inLanguageHome(absoluteLink);
         boolean existingInHome = this.inLanguageHome(absoluteExisting);
         if (linkInHome && existingInHome) {
            this.languageHomeFileSystem.createLink(absoluteLink, absoluteExisting);
         } else {
            if (linkInHome || existingInHome) {
               throw new IOException("Cross file system linking is not supported.");
            }

            this.delegateFileSystem.createLink(link, existing);
         }
      }

      @Override
      public void createSymbolicLink(Path link, Path target, FileAttribute<?>... attrs) throws IOException {
         Path absoluteLink = this.toNormalizedAbsolutePath(link);
         Path absoluteTarget = this.toNormalizedAbsolutePath(target);
         boolean linkInHome = this.inLanguageHome(absoluteLink);
         boolean targetInHome = this.inLanguageHome(absoluteTarget);
         if (linkInHome && targetInHome) {
            this.languageHomeFileSystem.createSymbolicLink(absoluteLink, target);
         } else {
            if (linkInHome || targetInHome) {
               throw new IOException("Cross file system linking is not supported.");
            }

            this.delegateFileSystem.createSymbolicLink(link, target);
         }
      }

      @Override
      public Path readSymbolicLink(Path link) throws IOException {
         Path absolutePath = this.toNormalizedAbsolutePath(link);
         return this.inLanguageHome(absolutePath) ? this.languageHomeFileSystem.readSymbolicLink(absolutePath) : this.delegateFileSystem.readSymbolicLink(link);
      }

      @Override
      public void setCurrentWorkingDirectory(Path currentWorkingDirectory) {
         this.languageHomeFileSystem.setCurrentWorkingDirectory(currentWorkingDirectory);
         this.delegateFileSystem.setCurrentWorkingDirectory(currentWorkingDirectory);
      }

      @Override
      public String getSeparator() {
         return this.delegateFileSystem.getSeparator();
      }

      @Override
      public String getPathSeparator() {
         return this.delegateFileSystem.getPathSeparator();
      }

      @Override
      public String getMimeType(Path path) {
         Path absolutePath = this.toNormalizedAbsolutePath(path);
         return this.inLanguageHome(absolutePath) ? this.languageHomeFileSystem.getMimeType(absolutePath) : this.delegateFileSystem.getMimeType(path);
      }

      @Override
      public Charset getEncoding(Path path) {
         Path absolutePath = this.toNormalizedAbsolutePath(path);
         return this.inLanguageHome(absolutePath) ? this.languageHomeFileSystem.getEncoding(absolutePath) : this.delegateFileSystem.getEncoding(path);
      }

      @Override
      public Path getTempDirectory() {
         return this.delegateFileSystem.getTempDirectory();
      }

      @Override
      public boolean isSameFile(Path path1, Path path2, LinkOption... options) throws IOException {
         Path absolutePath1 = this.toNormalizedAbsolutePath(path1);
         Path absolutePath2 = this.toNormalizedAbsolutePath(path2);
         boolean path1InHome = this.inLanguageHome(absolutePath1);
         boolean path2InHome = this.inLanguageHome(absolutePath2);
         if (path1InHome && path2InHome) {
            return this.languageHomeFileSystem.isSameFile(absolutePath1, absolutePath2);
         } else {
            return !path1InHome && !path2InHome ? this.delegateFileSystem.isSameFile(path1, path2) : false;
         }
      }

      private Path toNormalizedAbsolutePath(Path path) {
         if (path.isAbsolute()) {
            return path;
         } else {
            Path absolutePath = this.languageHomeFileSystem.toAbsolutePath(path);
            return isNormalized(path) ? absolutePath : absolutePath.normalize();
         }
      }

      private static boolean isNormalized(Path path) {
         for (Path name : path) {
            String strName = name.toString();
            if (".".equals(strName) || "..".equals(strName)) {
               return false;
            }
         }

         return true;
      }

      private boolean inLanguageHome(final Path path) {
         if (path.isAbsolute() && isNormalized(path)) {
            for (Path home : this.getLanguageHomes()) {
               if (path.startsWith(home)) {
                  return true;
               }
            }

            return false;
         } else {
            throw new IllegalArgumentException("The path must be normalized absolute path.");
         }
      }

      private Set<Path> getLanguageHomes() {
         Set<Path> res = this.languageHomes;
         if (res == null) {
            synchronized (this) {
               res = this.languageHomes;
               if (res == null) {
                  res = new HashSet<>();

                  for (LanguageCache cache : LanguageCache.languages().values()) {
                     String languageHome = cache.getLanguageHome();
                     if (languageHome != null) {
                        res.add(Paths.get(languageHome));
                     }
                  }

                  this.languageHomes = res;
               }
            }
         }

         return res;
      }
   }

   private static final class NIOFileSystem implements FileSystems.PolyglotFileSystem {
      private final FileSystemProvider hostfs;
      private final boolean explicitUserDir;
      private volatile Path userDir;
      private volatile Path tmpDir;

      NIOFileSystem(final FileSystemProvider fileSystemProvider) {
         this(fileSystemProvider, false, null);
      }

      NIOFileSystem(final FileSystemProvider fileSystemProvider, final Path userDir) {
         this(fileSystemProvider, true, userDir);
      }

      private NIOFileSystem(final FileSystemProvider fileSystemProvider, final boolean explicitUserDir, final Path userDir) {
         Objects.requireNonNull(fileSystemProvider, "FileSystemProvider must be non null.");
         this.hostfs = fileSystemProvider;
         this.explicitUserDir = explicitUserDir;
         this.userDir = userDir;
      }

      @Override
      public boolean isInternal() {
         return true;
      }

      @Override
      public boolean hasAllAccess() {
         return "file".equals(this.hostfs.getScheme());
      }

      @Override
      public boolean hasNoAccess() {
         return false;
      }

      @Override
      public Path parsePath(URI uri) {
         try {
            return this.hostfs.getPath(uri);
         } catch (FileSystemNotFoundException | IllegalArgumentException var3) {
            throw new UnsupportedOperationException(var3);
         }
      }

      @Override
      public Path parsePath(String path) {
         if (!"file".equals(this.hostfs.getScheme())) {
            throw new IllegalStateException("The ParsePath(String path) should be called only for file scheme.");
         } else {
            return Paths.get(path);
         }
      }

      @Override
      public void checkAccess(Path path, Set<? extends AccessMode> modes, LinkOption... linkOptions) throws IOException {
         if (FileSystems.isFollowLinks(linkOptions)) {
            this.hostfs.checkAccess(this.resolveRelative(path), modes.toArray(new AccessMode[modes.size()]));
         } else {
            if (!modes.isEmpty()) {
               throw new UnsupportedOperationException("CheckAccess for NIO Provider is unsupported with non empty AccessMode and NOFOLLOW_LINKS.");
            }

            this.hostfs.readAttributes(path, "isRegularFile", LinkOption.NOFOLLOW_LINKS);
         }
      }

      @Override
      public void createDirectory(Path dir, FileAttribute<?>... attrs) throws IOException {
         this.hostfs.createDirectory(this.resolveRelative(dir), attrs);
      }

      @Override
      public void delete(Path path) throws IOException {
         this.hostfs.delete(this.resolveRelative(path));
      }

      @Override
      public void copy(Path source, Path target, CopyOption... options) throws IOException {
         this.hostfs.copy(this.resolveRelative(source), this.resolveRelative(target), options);
      }

      @Override
      public void move(Path source, Path target, CopyOption... options) throws IOException {
         this.hostfs.move(this.resolveRelative(source), this.resolveRelative(target), options);
      }

      @Override
      public SeekableByteChannel newByteChannel(Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs) throws IOException {
         Path resolved = this.resolveRelative(path);

         try {
            return this.hostfs.newFileChannel(resolved, options, attrs);
         } catch (UnsupportedOperationException var6) {
            return this.hostfs.newByteChannel(resolved, options, attrs);
         }
      }

      @Override
      public DirectoryStream<Path> newDirectoryStream(Path dir, Filter<? super Path> filter) throws IOException {
         Path cwd = this.userDir;
         Path resolvedPath;
         boolean relativize;
         if (!dir.isAbsolute() && cwd != null) {
            resolvedPath = cwd.resolve(dir);
            relativize = true;
         } else {
            resolvedPath = dir;
            relativize = false;
         }

         DirectoryStream<Path> result = this.hostfs.newDirectoryStream(resolvedPath, filter);
         if (relativize) {
            result = new FileSystems.NIOFileSystem.RelativizeDirectoryStream(cwd, result);
         }

         return result;
      }

      @Override
      public void createLink(Path link, Path existing) throws IOException {
         this.hostfs.createLink(this.resolveRelative(link), this.resolveRelative(existing));
      }

      @Override
      public void createSymbolicLink(Path link, Path target, FileAttribute<?>... attrs) throws IOException {
         this.hostfs.createSymbolicLink(this.resolveRelative(link), target, attrs);
      }

      @Override
      public Path readSymbolicLink(Path link) throws IOException {
         return this.hostfs.readSymbolicLink(this.resolveRelative(link));
      }

      @Override
      public Map<String, Object> readAttributes(Path path, String attributes, LinkOption... options) throws IOException {
         return this.hostfs.readAttributes(this.resolveRelative(path), attributes, options);
      }

      @Override
      public void setAttribute(Path path, String attribute, Object value, LinkOption... options) throws IOException {
         this.hostfs.setAttribute(this.resolveRelative(path), attribute, value, options);
      }

      @Override
      public Path toAbsolutePath(Path path) {
         if (path.isAbsolute()) {
            return path;
         } else {
            Path cwd = this.userDir;
            if (cwd == null) {
               if (this.explicitUserDir) {
                  throw new SecurityException("Access to user.dir is not allowed.");
               } else {
                  return path.toAbsolutePath();
               }
            } else {
               return cwd.resolve(path);
            }
         }
      }

      @Override
      public void setCurrentWorkingDirectory(Path currentWorkingDirectory) {
         Objects.requireNonNull(currentWorkingDirectory, "Current working directory must be non null.");
         if (!currentWorkingDirectory.isAbsolute()) {
            throw new IllegalArgumentException("Current working directory must be absolute.");
         } else {
            boolean nonDirectory;
            try {
               nonDirectory = Boolean.FALSE.equals(this.hostfs.readAttributes(currentWorkingDirectory, "isDirectory").get("isDirectory"));
            } catch (IOException var4) {
               nonDirectory = false;
            }

            if (nonDirectory) {
               throw new IllegalArgumentException("Current working directory must be directory.");
            } else if (this.explicitUserDir && this.userDir == null) {
               throw new SecurityException("Modification of current working directory is not allowed.");
            } else {
               this.userDir = currentWorkingDirectory;
            }
         }
      }

      @Override
      public Path toRealPath(Path path, LinkOption... linkOptions) throws IOException {
         Path resolvedPath = this.resolveRelative(path);
         return resolvedPath.toRealPath(linkOptions);
      }

      @Override
      public Path getTempDirectory() {
         Path result = this.tmpDir;
         if (result == null) {
            if (FileSystems.TMP_FILE == null) {
               throw new IllegalStateException("The java.io.tmpdir is not set.");
            }

            result = this.parsePath(FileSystems.TMP_FILE);
            this.tmpDir = result;
         }

         return result;
      }

      @Override
      public boolean isSameFile(Path path1, Path path2, LinkOption... options) throws IOException {
         if (FileSystems.isFollowLinks(options)) {
            Path absolutePath1 = this.resolveRelative(path1);
            Path absolutePath2 = this.resolveRelative(path2);
            return this.hostfs.isSameFile(absolutePath1, absolutePath2);
         } else {
            return FileSystems.PolyglotFileSystem.super.isSameFile(path1, path2, options);
         }
      }

      private Path resolveRelative(Path path) {
         return !path.isAbsolute() && this.userDir != null ? this.toAbsolutePath(path) : path;
      }

      private static final class RelativizeDirectoryStream implements DirectoryStream<Path> {
         private final Path folder;
         private final DirectoryStream<? extends Path> delegateDirectoryStream;

         RelativizeDirectoryStream(Path folder, DirectoryStream<? extends Path> delegateDirectoryStream) {
            this.folder = folder;
            this.delegateDirectoryStream = delegateDirectoryStream;
         }

         @Override
         public Iterator<Path> iterator() {
            return new FileSystems.NIOFileSystem.RelativizeDirectoryStream.RelativizeIterator(this.folder, this.delegateDirectoryStream.iterator());
         }

         @Override
         public void close() throws IOException {
            this.delegateDirectoryStream.close();
         }

         private static final class RelativizeIterator implements Iterator<Path> {
            private final Path folder;
            private final Iterator<? extends Path> delegateIterator;

            RelativizeIterator(Path folder, Iterator<? extends Path> delegateIterator) {
               this.folder = folder;
               this.delegateIterator = delegateIterator;
            }

            @Override
            public boolean hasNext() {
               return this.delegateIterator.hasNext();
            }

            public Path next() {
               return this.folder.relativize(this.delegateIterator.next());
            }
         }
      }
   }

   private static final class PathOperationsOnlyFileSystem extends FileSystems.DeniedIOFileSystem {
      private final FileSystem delegateFileSystem;

      PathOperationsOnlyFileSystem(FileSystem fileSystem) {
         this.delegateFileSystem = fileSystem;
      }

      @Override
      public boolean isInternal() {
         return this.delegateFileSystem instanceof FileSystems.PolyglotFileSystem && ((FileSystems.PolyglotFileSystem)this.delegateFileSystem).isInternal();
      }

      @Override
      public boolean hasNoAccess() {
         return this.delegateFileSystem instanceof FileSystems.PolyglotFileSystem && ((FileSystems.PolyglotFileSystem)this.delegateFileSystem).hasNoAccess();
      }

      @Override
      public Path toAbsolutePath(Path path) {
         return this.delegateFileSystem.toAbsolutePath(path);
      }

      @Override
      public void setCurrentWorkingDirectory(Path currentWorkingDirectory) {
         this.delegateFileSystem.setCurrentWorkingDirectory(currentWorkingDirectory);
         super.setCurrentWorkingDirectory(currentWorkingDirectory);
      }

      @Override
      public Path toRealPath(Path path, LinkOption... linkOptions) throws IOException {
         return this.delegateFileSystem.toRealPath(path, linkOptions);
      }

      @Override
      public boolean isSameFile(Path path1, Path path2, LinkOption... options) throws IOException {
         return this.delegateFileSystem.isSameFile(path1, path2, options);
      }
   }

   private interface PolyglotFileSystem extends FileSystem {
      boolean isInternal();

      boolean hasAllAccess();

      boolean hasNoAccess();
   }

   static final class PreInitializeContextFileSystem implements FileSystems.PolyglotFileSystem {
      private FileSystem delegate = FileSystems.newDefaultFileSystem();
      private Function<Path, FileSystems.PreInitializeContextFileSystem.PreInitializePath> factory = new FileSystems.PreInitializeContextFileSystem.ImageBuildTimeFactory(
         
      );

      void onPreInitializeContextEnd() {
         if (this.factory == null) {
            throw new IllegalStateException("Context pre-initialization already finished.");
         } else {
            ((FileSystems.PreInitializeContextFileSystem.ImageBuildTimeFactory)this.factory).onPreInitializeContextEnd();
            this.factory = null;
            this.delegate = FileSystems.INVALID_FILESYSTEM;
         }
      }

      void onLoadPreinitializedContext(FileSystem newDelegate) {
         Objects.requireNonNull(newDelegate, "NewDelegate must be non null.");
         if (this.factory != null) {
            throw new IllegalStateException("Pre-initialized context already loaded.");
         } else {
            this.delegate = newDelegate;
            this.factory = new FileSystems.PreInitializeContextFileSystem.ImageExecutionTimeFactory();
         }
      }

      String pathToString(Path path) {
         if (this.delegate != FileSystems.INVALID_FILESYSTEM) {
            return path.toString();
         } else {
            verifyImageState();
            return ((FileSystems.PreInitializeContextFileSystem.PreInitializePath)path).resolve(FileSystems.newDefaultFileSystem()).toString();
         }
      }

      URI absolutePathtoURI(Path path) {
         if (this.delegate != FileSystems.INVALID_FILESYSTEM) {
            return path.toUri();
         } else {
            verifyImageState();
            Path resolved = ((FileSystems.PreInitializeContextFileSystem.PreInitializePath)path).resolve(FileSystems.newDefaultFileSystem());
            if (!resolved.isAbsolute()) {
               throw new IllegalArgumentException("Path must be absolute.");
            } else {
               return resolved.toUri();
            }
         }
      }

      private static void verifyImageState() {
         if (ImageInfo.inImageBuildtimeCode()) {
            throw new IllegalStateException("Reintroducing absolute path into an image heap.");
         }
      }

      @Override
      public boolean isInternal() {
         return this.delegate instanceof FileSystems.PolyglotFileSystem && ((FileSystems.PolyglotFileSystem)this.delegate).isInternal();
      }

      @Override
      public boolean hasAllAccess() {
         return this.delegate instanceof FileSystems.PolyglotFileSystem && ((FileSystems.PolyglotFileSystem)this.delegate).hasAllAccess();
      }

      @Override
      public boolean hasNoAccess() {
         return this.delegate instanceof FileSystems.PolyglotFileSystem && ((FileSystems.PolyglotFileSystem)this.delegate).hasNoAccess();
      }

      @Override
      public Path parsePath(URI path) {
         try {
            return this.wrap(this.delegate.parsePath(path));
         } catch (FileSystemNotFoundException | IllegalArgumentException var3) {
            throw new UnsupportedOperationException(var3);
         }
      }

      @Override
      public Path parsePath(String path) {
         return this.wrap(this.delegate.parsePath(path));
      }

      @Override
      public void checkAccess(Path path, Set<? extends AccessMode> modes, LinkOption... linkOptions) throws IOException {
         this.delegate.checkAccess(unwrap(path), modes, linkOptions);
      }

      @Override
      public void createDirectory(Path dir, FileAttribute<?>... attrs) throws IOException {
         this.delegate.createDirectory(unwrap(dir), attrs);
      }

      @Override
      public void delete(Path path) throws IOException {
         this.delegate.delete(unwrap(path));
      }

      @Override
      public SeekableByteChannel newByteChannel(Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs) throws IOException {
         return this.delegate.newByteChannel(unwrap(path), options, attrs);
      }

      @Override
      public DirectoryStream<Path> newDirectoryStream(Path dir, Filter<? super Path> filter) throws IOException {
         final DirectoryStream<Path> delegateStream = this.delegate.newDirectoryStream(unwrap(dir), filter);
         return new DirectoryStream<Path>() {
            @Override
            public Iterator<Path> iterator() {
               return PreInitializeContextFileSystem.this.new WrappingPathIterator(delegateStream.iterator());
            }

            @Override
            public void close() throws IOException {
               delegateStream.close();
            }
         };
      }

      @Override
      public Path toAbsolutePath(Path path) {
         return this.wrap(this.delegate.toAbsolutePath(unwrap(path)));
      }

      @Override
      public Path toRealPath(Path path, LinkOption... linkOptions) throws IOException {
         return this.wrap(this.delegate.toRealPath(unwrap(path), linkOptions));
      }

      @Override
      public Map<String, Object> readAttributes(Path path, String attributes, LinkOption... options) throws IOException {
         return this.delegate.readAttributes(unwrap(path), attributes, options);
      }

      @Override
      public void setAttribute(Path path, String attribute, Object value, LinkOption... options) throws IOException {
         this.delegate.setAttribute(unwrap(path), attribute, value, options);
      }

      @Override
      public void copy(Path source, Path target, CopyOption... options) throws IOException {
         this.delegate.copy(unwrap(source), unwrap(target), options);
      }

      @Override
      public void move(Path source, Path target, CopyOption... options) throws IOException {
         this.delegate.move(unwrap(source), unwrap(target), options);
      }

      @Override
      public void createLink(Path link, Path existing) throws IOException {
         this.delegate.createLink(unwrap(link), unwrap(existing));
      }

      @Override
      public void createSymbolicLink(Path link, Path target, FileAttribute<?>... attrs) throws IOException {
         this.delegate.createSymbolicLink(unwrap(link), unwrap(target), attrs);
      }

      @Override
      public Path readSymbolicLink(Path link) throws IOException {
         return this.wrap(this.delegate.readSymbolicLink(unwrap(link)));
      }

      @Override
      public void setCurrentWorkingDirectory(Path currentWorkingDirectory) {
         this.delegate.setCurrentWorkingDirectory(unwrap(currentWorkingDirectory));
      }

      @Override
      public String getSeparator() {
         return this.delegate.getSeparator();
      }

      @Override
      public Charset getEncoding(Path path) {
         return this.delegate.getEncoding(unwrap(path));
      }

      @Override
      public String getMimeType(Path path) {
         return this.delegate.getMimeType(unwrap(path));
      }

      @Override
      public Path getTempDirectory() {
         return this.wrap(this.delegate.getTempDirectory());
      }

      @Override
      public boolean isSameFile(Path path1, Path path2, LinkOption... options) throws IOException {
         return this.delegate.isSameFile(unwrap(path1), unwrap(path2), options);
      }

      @Override
      public int hashCode() {
         return this.delegate.hashCode();
      }

      @Override
      public boolean equals(Object other) {
         if (other == this) {
            return true;
         } else {
            return !(other instanceof FileSystems.PreInitializeContextFileSystem)
               ? false
               : this.delegate.equals(((FileSystems.PreInitializeContextFileSystem)other).delegate);
         }
      }

      Path wrap(Path path) {
         return path == null ? null : this.factory.apply(path);
      }

      static Path unwrap(Path path) {
         return path.getClass() == FileSystems.PreInitializeContextFileSystem.PreInitializePath.class
            ? ((FileSystems.PreInitializeContextFileSystem.PreInitializePath)path).getDelegate()
            : path;
      }

      private final class ImageBuildTimeFactory extends FileSystems.PreInitializeContextFileSystem.ImageExecutionTimeFactory {
         private final Collection<Reference<FileSystems.PreInitializeContextFileSystem.PreInitializePath>> emittedPaths = new ArrayList<>();

         @Override
         public FileSystems.PreInitializeContextFileSystem.PreInitializePath apply(Path path) {
            FileSystems.PreInitializeContextFileSystem.PreInitializePath preInitPath = super.apply(path);
            this.emittedPaths.add(new WeakReference<>(preInitPath));
            return preInitPath;
         }

         void onPreInitializeContextEnd() {
            Map<String, Path> languageHomes = new HashMap<>();

            for (LanguageCache cache : LanguageCache.languages().values()) {
               String languageHome = cache.getLanguageHome();
               if (languageHome != null) {
                  languageHomes.put(cache.getId(), PreInitializeContextFileSystem.this.delegate.parsePath(languageHome));
               }
            }

            for (Reference<FileSystems.PreInitializeContextFileSystem.PreInitializePath> pathRef : this.emittedPaths) {
               FileSystems.PreInitializeContextFileSystem.PreInitializePath path = pathRef.get();
               if (path != null) {
                  path.onPreInitializeContextEnd(languageHomes);
               }
            }
         }
      }

      private class ImageExecutionTimeFactory implements Function<Path, FileSystems.PreInitializeContextFileSystem.PreInitializePath> {
         public FileSystems.PreInitializeContextFileSystem.PreInitializePath apply(Path path) {
            return PreInitializeContextFileSystem.this.new PreInitializePath(path);
         }
      }

      private static final class ImageHeapPath {
         private final String languageId;
         private final String path;

         ImageHeapPath(String languageId, String path) {
            assert path != null;

            this.languageId = languageId;
            this.path = path;
         }
      }

      private final class PreInitializePath implements Path {
         private volatile Object delegatePath;

         PreInitializePath(Path delegatePath) {
            this.delegatePath = delegatePath;
         }

         private Path getDelegate() {
            Path result = this.resolve(PreInitializeContextFileSystem.this.delegate);
            this.delegatePath = result;
            return result;
         }

         private Path resolve(FileSystem fs) {
            Object current = this.delegatePath;
            if (current instanceof Path) {
               return (Path)current;
            } else if (!(current instanceof FileSystems.PreInitializeContextFileSystem.ImageHeapPath)) {
               throw new IllegalStateException("Unknown delegate " + current);
            } else {
               FileSystems.PreInitializeContextFileSystem.ImageHeapPath imageHeapPath = (FileSystems.PreInitializeContextFileSystem.ImageHeapPath)current;
               String languageId = imageHeapPath.languageId;
               String path = imageHeapPath.path;
               Path result;
               String newLanguageHome;
               if (languageId != null && (newLanguageHome = LanguageCache.languages().get(languageId).getLanguageHome()) != null) {
                  result = fs.parsePath(newLanguageHome).resolve(path);
               } else {
                  result = fs.parsePath(path);
               }

               return result;
            }
         }

         void onPreInitializeContextEnd(Map<String, Path> languageHomes) {
            Path internalPath = (Path)this.delegatePath;
            String languageId = null;

            for (Entry<String, Path> e : languageHomes.entrySet()) {
               if (internalPath.startsWith(e.getValue())) {
                  internalPath = e.getValue().relativize(internalPath);
                  languageId = e.getKey();
                  break;
               }
            }

            this.delegatePath = new FileSystems.PreInitializeContextFileSystem.ImageHeapPath(languageId, internalPath.toString());
         }

         @Override
         public java.nio.file.FileSystem getFileSystem() {
            return this.getDelegate().getFileSystem();
         }

         @Override
         public boolean isAbsolute() {
            return this.getDelegate().isAbsolute();
         }

         @Override
         public Path getRoot() {
            return PreInitializeContextFileSystem.this.wrap(this.getDelegate().getRoot());
         }

         @Override
         public Path getFileName() {
            return PreInitializeContextFileSystem.this.wrap(this.getDelegate().getFileName());
         }

         @Override
         public Path getParent() {
            return PreInitializeContextFileSystem.this.wrap(this.getDelegate().getParent());
         }

         @Override
         public int getNameCount() {
            return this.getDelegate().getNameCount();
         }

         @Override
         public Path getName(int index) {
            return PreInitializeContextFileSystem.this.wrap(this.getDelegate().getName(index));
         }

         @Override
         public Path subpath(int beginIndex, int endIndex) {
            return PreInitializeContextFileSystem.this.wrap(this.getDelegate().subpath(beginIndex, endIndex));
         }

         @Override
         public boolean startsWith(Path other) {
            return this.getDelegate().startsWith(FileSystems.PreInitializeContextFileSystem.unwrap(other));
         }

         @Override
         public boolean startsWith(String other) {
            return this.getDelegate().startsWith(other);
         }

         @Override
         public boolean endsWith(Path other) {
            return this.getDelegate().endsWith(FileSystems.PreInitializeContextFileSystem.unwrap(other));
         }

         @Override
         public boolean endsWith(String other) {
            return this.getDelegate().endsWith(other);
         }

         @Override
         public Path normalize() {
            return PreInitializeContextFileSystem.this.wrap(this.getDelegate().normalize());
         }

         @Override
         public Path resolve(Path other) {
            return PreInitializeContextFileSystem.this.wrap(this.getDelegate().resolve(FileSystems.PreInitializeContextFileSystem.unwrap(other)));
         }

         @Override
         public Path resolve(String other) {
            return PreInitializeContextFileSystem.this.wrap(this.getDelegate().resolve(other));
         }

         @Override
         public Path resolveSibling(Path other) {
            return PreInitializeContextFileSystem.this.wrap(this.getDelegate().resolveSibling(FileSystems.PreInitializeContextFileSystem.unwrap(other)));
         }

         @Override
         public Path resolveSibling(String other) {
            return PreInitializeContextFileSystem.this.wrap(this.getDelegate().resolveSibling(other));
         }

         @Override
         public Path relativize(Path other) {
            return PreInitializeContextFileSystem.this.wrap(this.getDelegate().relativize(FileSystems.PreInitializeContextFileSystem.unwrap(other)));
         }

         @Override
         public URI toUri() {
            return this.getDelegate().toUri();
         }

         @Override
         public Path toAbsolutePath() {
            return PreInitializeContextFileSystem.this.wrap(this.getDelegate().toAbsolutePath());
         }

         @Override
         public Path toRealPath(LinkOption... options) throws IOException {
            return PreInitializeContextFileSystem.this.wrap(this.getDelegate().toRealPath(options));
         }

         @Override
         public File toFile() {
            return this.getDelegate().toFile();
         }

         @Override
         public WatchKey register(WatchService watcher, Kind<?>[] events, Modifier... modifiers) throws IOException {
            return this.getDelegate().register(watcher, events, modifiers);
         }

         @Override
         public WatchKey register(WatchService watcher, Kind<?>... events) throws IOException {
            return this.getDelegate().register(watcher, events);
         }

         @Override
         public Iterator<Path> iterator() {
            return PreInitializeContextFileSystem.this.new WrappingPathIterator(this.getDelegate().iterator());
         }

         @Override
         public int compareTo(Path other) {
            return this.getDelegate().compareTo(FileSystems.PreInitializeContextFileSystem.unwrap(other));
         }

         @Override
         public int hashCode() {
            return this.getDelegate().hashCode();
         }

         @Override
         public boolean equals(Object other) {
            if (other == this) {
               return true;
            } else {
               return !(other instanceof Path) ? false : this.getDelegate().equals(FileSystems.PreInitializeContextFileSystem.unwrap((Path)other));
            }
         }

         @Override
         public String toString() {
            return this.getDelegate().toString();
         }
      }

      private final class WrappingPathIterator implements Iterator<Path> {
         private final Iterator<Path> delegateIterator;

         WrappingPathIterator(Iterator<Path> delegateIterator) {
            this.delegateIterator = delegateIterator;
         }

         @Override
         public boolean hasNext() {
            return this.delegateIterator.hasNext();
         }

         public Path next() {
            return PreInitializeContextFileSystem.this.wrap(this.delegateIterator.next());
         }
      }
   }

   private static class ReadOnlyFileSystem extends FileSystems.DeniedIOFileSystem {
      private static final List<AccessMode> READ_MODES = Arrays.asList(AccessMode.READ, AccessMode.EXECUTE);
      private static final List<StandardOpenOption> READ_OPTIONS = Arrays.asList(
         StandardOpenOption.READ, StandardOpenOption.DSYNC, StandardOpenOption.SPARSE, StandardOpenOption.SYNC, StandardOpenOption.TRUNCATE_EXISTING
      );
      private final FileSystem delegateFileSystem;

      ReadOnlyFileSystem(FileSystem fileSystem) {
         this.delegateFileSystem = fileSystem;
      }

      @Override
      public boolean isInternal() {
         return this.delegateFileSystem instanceof FileSystems.PolyglotFileSystem && ((FileSystems.PolyglotFileSystem)this.delegateFileSystem).isInternal();
      }

      @Override
      public boolean hasNoAccess() {
         return this.delegateFileSystem instanceof FileSystems.PolyglotFileSystem && ((FileSystems.PolyglotFileSystem)this.delegateFileSystem).hasNoAccess();
      }

      @Override
      public void checkAccess(Path path, Set<? extends AccessMode> modes, LinkOption... linkOptions) throws IOException {
         Set<? extends AccessMode> writeModes = new HashSet<>(modes);
         writeModes.removeAll(READ_MODES);
         if (writeModes.isEmpty()) {
            this.delegateFileSystem.checkAccess(path, modes, linkOptions);
         } else {
            throw new IOException("Read-only file");
         }
      }

      @Override
      public SeekableByteChannel newByteChannel(Path inPath, Set<? extends OpenOption> options, FileAttribute<?>... attrs) throws IOException {
         Set<OpenOption> copy = new HashSet<>(options);
         Set<OpenOption> writeOptions = new HashSet<>(copy);
         boolean read = writeOptions.contains(StandardOpenOption.READ);
         writeOptions.removeAll(READ_OPTIONS);
         if (read) {
            writeOptions.remove(StandardOpenOption.APPEND);
         }

         boolean write = !writeOptions.isEmpty();
         if (write) {
            throw FileSystems.forbidden(inPath);
         } else {
            return this.delegateFileSystem.newByteChannel(inPath, copy, attrs);
         }
      }

      @Override
      public DirectoryStream<Path> newDirectoryStream(Path dir, Filter<? super Path> filter) throws IOException {
         return this.delegateFileSystem.newDirectoryStream(dir, filter);
      }

      @Override
      public Map<String, Object> readAttributes(Path path, String attributes, LinkOption... options) throws IOException {
         return this.delegateFileSystem.readAttributes(path, attributes, options);
      }

      @Override
      public Path toAbsolutePath(Path path) {
         return this.delegateFileSystem.toAbsolutePath(path);
      }

      @Override
      public Path readSymbolicLink(Path link) throws IOException {
         return this.delegateFileSystem.toAbsolutePath(link);
      }

      @Override
      public void setCurrentWorkingDirectory(Path currentWorkingDirectory) {
         this.delegateFileSystem.setCurrentWorkingDirectory(currentWorkingDirectory);
         super.setCurrentWorkingDirectory(currentWorkingDirectory);
      }

      @Override
      public Path toRealPath(Path path, LinkOption... linkOptions) throws IOException {
         return this.delegateFileSystem.toRealPath(path, linkOptions);
      }

      @Override
      public boolean isSameFile(Path path1, Path path2, LinkOption... options) throws IOException {
         return this.delegateFileSystem.isSameFile(path1, path2, options);
      }

      @Override
      public String getMimeType(Path path) {
         return this.delegateFileSystem.getMimeType(path);
      }

      @Override
      public Charset getEncoding(Path path) {
         return this.delegateFileSystem.getEncoding(path);
      }
   }
}
