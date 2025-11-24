
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.polyglot.EngineAccessor;
import com.oracle.truffle.polyglot.LanguageCache;
import com.oracle.truffle.polyglot.PolyglotImpl;
import com.oracle.truffle.polyglot.PolyglotLanguage;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
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
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
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
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Supplier;
import org.graalvm.nativeimage.ImageInfo;

final class FileSystems {
    static final String FILE_SCHEME = "file";
    static final org.graalvm.polyglot.io.FileSystem INVALID_FILESYSTEM = new InvalidFileSystem();
    private static final AtomicReference<FileSystemProvider> DEFAULT_FILE_SYSTEM_PROVIDER = new AtomicReference();
    private static final String TMP_FILE = System.getProperty("java.io.tmpdir");

    private FileSystems() {
        throw new IllegalStateException("No instance allowed");
    }

    static org.graalvm.polyglot.io.FileSystem newDefaultFileSystem() {
        return FileSystems.newFileSystem(FileSystems.findDefaultFileSystemProvider());
    }

    static org.graalvm.polyglot.io.FileSystem newDefaultFileSystem(Path userDir) {
        return FileSystems.newFileSystem(FileSystems.findDefaultFileSystemProvider(), userDir);
    }

    static org.graalvm.polyglot.io.FileSystem allowLanguageHomeAccess(org.graalvm.polyglot.io.FileSystem fileSystem) {
        return new LanguageHomeFileSystem(FileSystems.newDefaultFileSystem(), fileSystem);
    }

    static org.graalvm.polyglot.io.FileSystem newReadOnlyFileSystem(org.graalvm.polyglot.io.FileSystem fileSystem) {
        return new ReadOnlyFileSystem(fileSystem);
    }

    static org.graalvm.polyglot.io.FileSystem newNoIOFileSystem() {
        return new DeniedIOFileSystem();
    }

    static org.graalvm.polyglot.io.FileSystem newLanguageHomeFileSystem() {
        org.graalvm.polyglot.io.FileSystem defaultFS = FileSystems.newDefaultFileSystem();
        return new LanguageHomeFileSystem(new ReadOnlyFileSystem(defaultFS), new PathOperationsOnlyFileSystem(defaultFS));
    }

    static boolean hasAllAccess(org.graalvm.polyglot.io.FileSystem fileSystem) {
        return fileSystem instanceof PolyglotFileSystem && ((PolyglotFileSystem)fileSystem).hasAllAccess();
    }

    static boolean hasNoAccess(org.graalvm.polyglot.io.FileSystem fileSystem) {
        return fileSystem instanceof PolyglotFileSystem && ((PolyglotFileSystem)fileSystem).hasNoAccess();
    }

    static boolean isInternal(org.graalvm.polyglot.io.FileSystem fileSystem) {
        return fileSystem instanceof PolyglotFileSystem && ((PolyglotFileSystem)fileSystem).isInternal();
    }

    static Supplier<Map<String, Collection<? extends TruffleFile.FileTypeDetector>>> newFileTypeDetectorsSupplier(Iterable<LanguageCache> languageCaches) {
        return new FileTypeDetectorsSupplier(languageCaches);
    }

    static void resetDefaultFileSystemProvider() {
        DEFAULT_FILE_SYSTEM_PROVIDER.set(null);
    }

    static String getRelativePathInLanguageHome(TruffleFile file) {
        Object engineObject = EngineAccessor.LANGUAGE.getFileSystemEngineObject(EngineAccessor.LANGUAGE.getFileSystemContext(file));
        if (engineObject instanceof PolyglotLanguageContext) {
            Path path;
            PolyglotLanguageContext context = (PolyglotLanguageContext)engineObject;
            org.graalvm.polyglot.io.FileSystem fs = EngineAccessor.LANGUAGE.getFileSystem(file);
            String result = FileSystems.relativizeToLanguageHome(fs, path = EngineAccessor.LANGUAGE.getPath(file), context.language);
            if (result != null) {
                return result;
            }
            Map<String, LanguageInfo> accessibleLanguages = context.getAccessibleLanguages(true);
            if (accessibleLanguages != null) {
                for (LanguageInfo language : accessibleLanguages.values()) {
                    PolyglotLanguage lang = context.context.engine.idToLanguage.get(language.getId());
                    result = FileSystems.relativizeToLanguageHome(fs, path, lang);
                    if (result == null) continue;
                    return result;
                }
            }
            return null;
        }
        if (engineObject instanceof PolyglotImpl.EmbedderFileSystemContext) {
            return null;
        }
        throw new AssertionError();
    }

    private static String relativizeToLanguageHome(org.graalvm.polyglot.io.FileSystem fs, Path path, PolyglotLanguage language) {
        String languageHome = language.cache.getLanguageHome();
        if (languageHome == null) {
            return null;
        }
        Path languageHomePath = fs.parsePath(language.cache.getLanguageHome());
        if (path.startsWith(languageHomePath)) {
            return languageHomePath.relativize(path).toString();
        }
        return null;
    }

    private static org.graalvm.polyglot.io.FileSystem newFileSystem(FileSystemProvider fileSystemProvider) {
        return new NIOFileSystem(fileSystemProvider);
    }

    private static org.graalvm.polyglot.io.FileSystem newFileSystem(FileSystemProvider fileSystemProvider, Path userDir) {
        return new NIOFileSystem(fileSystemProvider, userDir);
    }

    private static FileSystemProvider findDefaultFileSystemProvider() {
        FileSystemProvider defaultFsProvider = DEFAULT_FILE_SYSTEM_PROVIDER.get();
        if (defaultFsProvider == null) {
            for (FileSystemProvider fsp : FileSystemProvider.installedProviders()) {
                if (!FILE_SCHEME.equals(fsp.getScheme())) continue;
                defaultFsProvider = fsp;
                break;
            }
            if (defaultFsProvider == null) {
                throw new IllegalStateException("No FileSystemProvider for scheme 'file'.");
            }
            DEFAULT_FILE_SYSTEM_PROVIDER.set(defaultFsProvider);
        }
        return defaultFsProvider;
    }

    private static boolean isFollowLinks(LinkOption ... linkOptions) {
        for (LinkOption lo : linkOptions) {
            if (Objects.requireNonNull(lo) != LinkOption.NOFOLLOW_LINKS) continue;
            return false;
        }
        return true;
    }

    private static SecurityException forbidden(Path path) {
        throw new SecurityException((String)(path == null ? "Operation is not allowed." : "Operation is not allowed for: " + path));
    }

    private static interface PolyglotFileSystem
    extends org.graalvm.polyglot.io.FileSystem {
        public boolean isInternal();

        public boolean hasAllAccess();

        public boolean hasNoAccess();
    }

    private static final class FileTypeDetectorsSupplier
    implements Supplier<Map<String, Collection<? extends TruffleFile.FileTypeDetector>>> {
        private final Iterable<LanguageCache> languageCaches;

        FileTypeDetectorsSupplier(Iterable<LanguageCache> languageCaches) {
            this.languageCaches = languageCaches;
        }

        @Override
        public Map<String, Collection<? extends TruffleFile.FileTypeDetector>> get() {
            HashMap<String, Collection<? extends TruffleFile.FileTypeDetector>> detectors = new HashMap<String, Collection<? extends TruffleFile.FileTypeDetector>>();
            for (LanguageCache cache : this.languageCaches) {
                for (String mimeType : cache.getMimeTypes()) {
                    List<? extends TruffleFile.FileTypeDetector> languageDetectors = cache.getFileTypeDetectors();
                    Collection mimeTypeDetectors = (Collection)detectors.get(mimeType);
                    if (mimeTypeDetectors != null) {
                        if (languageDetectors.isEmpty()) continue;
                        ArrayList<? extends TruffleFile.FileTypeDetector> mergedDetectors = new ArrayList<TruffleFile.FileTypeDetector>(mimeTypeDetectors);
                        mergedDetectors.addAll(languageDetectors);
                        detectors.put(mimeType, mergedDetectors);
                        continue;
                    }
                    detectors.put(mimeType, languageDetectors);
                }
            }
            return detectors;
        }
    }

    private static final class InvalidFileSystem
    implements PolyglotFileSystem {
        private InvalidFileSystem() {
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
        public Path parsePath(URI uri) {
            throw new UnsupportedOperationException("ParsePath not supported on InvalidFileSystem");
        }

        @Override
        public Path parsePath(String path) {
            throw new UnsupportedOperationException("ParsePath not supported on InvalidFileSystem");
        }

        @Override
        public void checkAccess(Path path, Set<? extends AccessMode> modes, LinkOption ... linkOptions) throws IOException {
            throw FileSystems.forbidden(path);
        }

        @Override
        public void createDirectory(Path dir, FileAttribute<?> ... attrs) throws IOException {
            throw FileSystems.forbidden(dir);
        }

        @Override
        public void delete(Path path) throws IOException {
            throw FileSystems.forbidden(path);
        }

        @Override
        public SeekableByteChannel newByteChannel(Path path, Set<? extends OpenOption> options, FileAttribute<?> ... attrs) throws IOException {
            throw FileSystems.forbidden(path);
        }

        @Override
        public DirectoryStream<Path> newDirectoryStream(Path dir, DirectoryStream.Filter<? super Path> filter) throws IOException {
            throw FileSystems.forbidden(dir);
        }

        @Override
        public Path toAbsolutePath(Path path) {
            throw FileSystems.forbidden(path);
        }

        @Override
        public Path toRealPath(Path path, LinkOption ... linkOptions) throws IOException {
            throw FileSystems.forbidden(path);
        }

        @Override
        public Map<String, Object> readAttributes(Path path, String attributes, LinkOption ... options) throws IOException {
            throw FileSystems.forbidden(path);
        }

        @Override
        public void setAttribute(Path path, String attribute, Object value2, LinkOption ... options) throws IOException {
            throw FileSystems.forbidden(path);
        }

        @Override
        public void copy(Path source, Path target, CopyOption ... options) throws IOException {
            throw FileSystems.forbidden(source);
        }

        @Override
        public void move(Path source, Path target, CopyOption ... options) throws IOException {
            throw FileSystems.forbidden(source);
        }

        @Override
        public void createLink(Path link, Path existing) throws IOException {
            throw FileSystems.forbidden(link);
        }

        @Override
        public void createSymbolicLink(Path link, Path target, FileAttribute<?> ... attrs) throws IOException {
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

    private static final class PathOperationsOnlyFileSystem
    extends DeniedIOFileSystem {
        private final org.graalvm.polyglot.io.FileSystem delegateFileSystem;

        PathOperationsOnlyFileSystem(org.graalvm.polyglot.io.FileSystem fileSystem) {
            this.delegateFileSystem = fileSystem;
        }

        @Override
        public boolean isInternal() {
            return this.delegateFileSystem instanceof PolyglotFileSystem && ((PolyglotFileSystem)this.delegateFileSystem).isInternal();
        }

        @Override
        public boolean hasNoAccess() {
            return this.delegateFileSystem instanceof PolyglotFileSystem && ((PolyglotFileSystem)this.delegateFileSystem).hasNoAccess();
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
        public Path toRealPath(Path path, LinkOption ... linkOptions) throws IOException {
            return this.delegateFileSystem.toRealPath(path, linkOptions);
        }

        @Override
        public boolean isSameFile(Path path1, Path path2, LinkOption ... options) throws IOException {
            return this.delegateFileSystem.isSameFile(path1, path2, options);
        }
    }

    private static class ReadOnlyFileSystem
    extends DeniedIOFileSystem {
        private static final List<AccessMode> READ_MODES = Arrays.asList(AccessMode.READ, AccessMode.EXECUTE);
        private static final List<StandardOpenOption> READ_OPTIONS = Arrays.asList(StandardOpenOption.READ, StandardOpenOption.DSYNC, StandardOpenOption.SPARSE, StandardOpenOption.SYNC, StandardOpenOption.TRUNCATE_EXISTING);
        private final org.graalvm.polyglot.io.FileSystem delegateFileSystem;

        ReadOnlyFileSystem(org.graalvm.polyglot.io.FileSystem fileSystem) {
            this.delegateFileSystem = fileSystem;
        }

        @Override
        public boolean isInternal() {
            return this.delegateFileSystem instanceof PolyglotFileSystem && ((PolyglotFileSystem)this.delegateFileSystem).isInternal();
        }

        @Override
        public boolean hasNoAccess() {
            return this.delegateFileSystem instanceof PolyglotFileSystem && ((PolyglotFileSystem)this.delegateFileSystem).hasNoAccess();
        }

        @Override
        public void checkAccess(Path path, Set<? extends AccessMode> modes, LinkOption ... linkOptions) throws IOException {
            HashSet<? extends AccessMode> writeModes = new HashSet<AccessMode>(modes);
            writeModes.removeAll(READ_MODES);
            if (!writeModes.isEmpty()) {
                throw new IOException("Read-only file");
            }
            this.delegateFileSystem.checkAccess(path, modes, linkOptions);
        }

        @Override
        public SeekableByteChannel newByteChannel(Path inPath, Set<? extends OpenOption> options, FileAttribute<?> ... attrs) throws IOException {
            boolean write;
            HashSet<? extends OpenOption> copy = new HashSet<OpenOption>(options);
            HashSet<? extends OpenOption> writeOptions = new HashSet<OpenOption>(copy);
            boolean read2 = writeOptions.contains(StandardOpenOption.READ);
            writeOptions.removeAll(READ_OPTIONS);
            if (read2) {
                writeOptions.remove(StandardOpenOption.APPEND);
            }
            boolean bl = write = !writeOptions.isEmpty();
            if (write) {
                throw FileSystems.forbidden(inPath);
            }
            return this.delegateFileSystem.newByteChannel(inPath, copy, attrs);
        }

        @Override
        public DirectoryStream<Path> newDirectoryStream(Path dir, DirectoryStream.Filter<? super Path> filter) throws IOException {
            return this.delegateFileSystem.newDirectoryStream(dir, filter);
        }

        @Override
        public Map<String, Object> readAttributes(Path path, String attributes, LinkOption ... options) throws IOException {
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
        public Path toRealPath(Path path, LinkOption ... linkOptions) throws IOException {
            return this.delegateFileSystem.toRealPath(path, linkOptions);
        }

        @Override
        public boolean isSameFile(Path path1, Path path2, LinkOption ... options) throws IOException {
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

    private static final class LanguageHomeFileSystem
    implements PolyglotFileSystem {
        private final org.graalvm.polyglot.io.FileSystem languageHomeFileSystem;
        private final org.graalvm.polyglot.io.FileSystem delegateFileSystem;
        private volatile Set<Path> languageHomes;

        LanguageHomeFileSystem(org.graalvm.polyglot.io.FileSystem languageHomeFileSystem, org.graalvm.polyglot.io.FileSystem delegateFileSystem) {
            this.languageHomeFileSystem = languageHomeFileSystem;
            this.delegateFileSystem = delegateFileSystem;
            Class<?> languageHomeFileSystemPathType = this.languageHomeFileSystem.parsePath("").getClass();
            Class<?> customFileSystemPathType = delegateFileSystem.parsePath("").getClass();
            if (languageHomeFileSystemPathType != customFileSystemPathType) {
                throw new IllegalArgumentException("Given FileSystem must have the same Path type as the default FileSystem.");
            }
            if (!languageHomeFileSystem.getSeparator().equals(delegateFileSystem.getSeparator())) {
                throw new IllegalArgumentException("Given FileSystem must use the same separator character as the default FileSystem.");
            }
            if (!languageHomeFileSystem.getPathSeparator().equals(delegateFileSystem.getPathSeparator())) {
                throw new IllegalArgumentException("Given FileSystem must use the same path separator character as the default FileSystem.");
            }
        }

        @Override
        public boolean isInternal() {
            return this.delegateFileSystem instanceof PolyglotFileSystem && ((PolyglotFileSystem)this.delegateFileSystem).isInternal();
        }

        @Override
        public boolean hasAllAccess() {
            return this.delegateFileSystem instanceof PolyglotFileSystem && ((PolyglotFileSystem)this.delegateFileSystem).hasAllAccess();
        }

        @Override
        public boolean hasNoAccess() {
            return this.delegateFileSystem instanceof PolyglotFileSystem && ((PolyglotFileSystem)this.delegateFileSystem).hasNoAccess();
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
        public void checkAccess(Path path, Set<? extends AccessMode> modes, LinkOption ... linkOptions) throws IOException {
            Path absolutePath = this.toNormalizedAbsolutePath(path);
            if (this.inLanguageHome(absolutePath)) {
                this.languageHomeFileSystem.checkAccess(absolutePath, modes, linkOptions);
            } else {
                this.delegateFileSystem.checkAccess(path, modes, linkOptions);
            }
        }

        @Override
        public void createDirectory(Path dir, FileAttribute<?> ... attrs) throws IOException {
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
        public SeekableByteChannel newByteChannel(Path path, Set<? extends OpenOption> options, FileAttribute<?> ... attrs) throws IOException {
            Path absolutePath = this.toNormalizedAbsolutePath(path);
            if (this.inLanguageHome(absolutePath)) {
                return this.languageHomeFileSystem.newByteChannel(absolutePath, options, attrs);
            }
            return this.delegateFileSystem.newByteChannel(path, options, attrs);
        }

        @Override
        public DirectoryStream<Path> newDirectoryStream(Path dir, DirectoryStream.Filter<? super Path> filter) throws IOException {
            Path absolutePath = this.toNormalizedAbsolutePath(dir);
            if (this.inLanguageHome(absolutePath)) {
                return this.languageHomeFileSystem.newDirectoryStream(absolutePath, filter);
            }
            return this.delegateFileSystem.newDirectoryStream(dir, filter);
        }

        @Override
        public Path toAbsolutePath(Path path) {
            return this.delegateFileSystem.toAbsolutePath(path);
        }

        @Override
        public Path toRealPath(Path path, LinkOption ... linkOptions) throws IOException {
            Path absolutePath = this.toNormalizedAbsolutePath(path);
            if (this.inLanguageHome(absolutePath)) {
                return this.languageHomeFileSystem.toRealPath(path, new LinkOption[0]);
            }
            return this.delegateFileSystem.toRealPath(path, new LinkOption[0]);
        }

        @Override
        public Map<String, Object> readAttributes(Path path, String attributes, LinkOption ... options) throws IOException {
            Path absolutePath = this.toNormalizedAbsolutePath(path);
            if (this.inLanguageHome(absolutePath)) {
                return this.languageHomeFileSystem.readAttributes(absolutePath, attributes, options);
            }
            return this.delegateFileSystem.readAttributes(path, attributes, options);
        }

        @Override
        public void setAttribute(Path path, String attribute, Object value2, LinkOption ... options) throws IOException {
            Path absolutePath = this.toNormalizedAbsolutePath(path);
            if (this.inLanguageHome(absolutePath)) {
                this.languageHomeFileSystem.setAttribute(absolutePath, attribute, value2, options);
            } else {
                this.delegateFileSystem.setAttribute(path, attribute, value2, options);
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
            } else if (!linkInHome && !existingInHome) {
                this.delegateFileSystem.createLink(link, existing);
            } else {
                throw new IOException("Cross file system linking is not supported.");
            }
        }

        @Override
        public void createSymbolicLink(Path link, Path target, FileAttribute<?> ... attrs) throws IOException {
            Path absoluteLink = this.toNormalizedAbsolutePath(link);
            Path absoluteTarget = this.toNormalizedAbsolutePath(target);
            boolean linkInHome = this.inLanguageHome(absoluteLink);
            boolean targetInHome = this.inLanguageHome(absoluteTarget);
            if (linkInHome && targetInHome) {
                this.languageHomeFileSystem.createSymbolicLink(absoluteLink, target, new FileAttribute[0]);
            } else if (!linkInHome && !targetInHome) {
                this.delegateFileSystem.createSymbolicLink(link, target, new FileAttribute[0]);
            } else {
                throw new IOException("Cross file system linking is not supported.");
            }
        }

        @Override
        public Path readSymbolicLink(Path link) throws IOException {
            Path absolutePath = this.toNormalizedAbsolutePath(link);
            if (this.inLanguageHome(absolutePath)) {
                return this.languageHomeFileSystem.readSymbolicLink(absolutePath);
            }
            return this.delegateFileSystem.readSymbolicLink(link);
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
            if (this.inLanguageHome(absolutePath)) {
                return this.languageHomeFileSystem.getMimeType(absolutePath);
            }
            return this.delegateFileSystem.getMimeType(path);
        }

        @Override
        public Charset getEncoding(Path path) {
            Path absolutePath = this.toNormalizedAbsolutePath(path);
            if (this.inLanguageHome(absolutePath)) {
                return this.languageHomeFileSystem.getEncoding(absolutePath);
            }
            return this.delegateFileSystem.getEncoding(path);
        }

        @Override
        public Path getTempDirectory() {
            return this.delegateFileSystem.getTempDirectory();
        }

        @Override
        public boolean isSameFile(Path path1, Path path2, LinkOption ... options) throws IOException {
            Path absolutePath1 = this.toNormalizedAbsolutePath(path1);
            Path absolutePath2 = this.toNormalizedAbsolutePath(path2);
            boolean path1InHome = this.inLanguageHome(absolutePath1);
            boolean path2InHome = this.inLanguageHome(absolutePath2);
            if (path1InHome && path2InHome) {
                return this.languageHomeFileSystem.isSameFile(absolutePath1, absolutePath2, new LinkOption[0]);
            }
            if (!path1InHome && !path2InHome) {
                return this.delegateFileSystem.isSameFile(path1, path2, new LinkOption[0]);
            }
            return false;
        }

        private Path toNormalizedAbsolutePath(Path path) {
            if (path.isAbsolute()) {
                return path;
            }
            Path absolutePath = this.languageHomeFileSystem.toAbsolutePath(path);
            if (LanguageHomeFileSystem.isNormalized(path)) {
                return absolutePath;
            }
            return absolutePath.normalize();
        }

        private static boolean isNormalized(Path path) {
            for (Path name : path) {
                String strName = name.toString();
                if (!".".equals(strName) && !"..".equals(strName)) continue;
                return false;
            }
            return true;
        }

        private boolean inLanguageHome(Path path) {
            if (!path.isAbsolute() || !LanguageHomeFileSystem.isNormalized(path)) {
                throw new IllegalArgumentException("The path must be normalized absolute path.");
            }
            for (Path home : this.getLanguageHomes()) {
                if (!path.startsWith(home)) continue;
                return true;
            }
            return false;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Set<Path> getLanguageHomes() {
            Set<Path> res = this.languageHomes;
            if (res == null) {
                LanguageHomeFileSystem languageHomeFileSystem = this;
                synchronized (languageHomeFileSystem) {
                    res = this.languageHomes;
                    if (res == null) {
                        res = new HashSet<Path>();
                        for (LanguageCache cache : LanguageCache.languages().values()) {
                            String languageHome = cache.getLanguageHome();
                            if (languageHome == null) continue;
                            res.add(Paths.get(languageHome, new String[0]));
                        }
                        this.languageHomes = res;
                    }
                }
            }
            return res;
        }
    }

    private static class DeniedIOFileSystem
    implements PolyglotFileSystem {
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
        public Path parsePath(URI uri) {
            if (!this.defaultFileSystemProvider.getScheme().equals(uri.getScheme())) {
                throw new UnsupportedOperationException("Unsupported URI scheme " + uri.getScheme());
            }
            try {
                return this.defaultFileSystemProvider.getPath(uri);
            }
            catch (IllegalArgumentException | FileSystemNotFoundException e) {
                throw new UnsupportedOperationException(e);
            }
        }

        @Override
        public Path parsePath(String path) {
            return Paths.get(path, new String[0]);
        }

        @Override
        public void checkAccess(Path path, Set<? extends AccessMode> modes, LinkOption ... linkOptions) throws IOException {
            throw FileSystems.forbidden(path);
        }

        @Override
        public void createDirectory(Path dir, FileAttribute<?> ... attrs) throws IOException {
            throw FileSystems.forbidden(dir);
        }

        @Override
        public void delete(Path path) throws IOException {
            throw FileSystems.forbidden(path);
        }

        @Override
        public void copy(Path source, Path target, CopyOption ... options) throws IOException {
            throw FileSystems.forbidden(source);
        }

        @Override
        public void move(Path source, Path target, CopyOption ... options) throws IOException {
            throw FileSystems.forbidden(source);
        }

        @Override
        public SeekableByteChannel newByteChannel(Path inPath, Set<? extends OpenOption> options, FileAttribute<?> ... attrs) throws IOException {
            throw FileSystems.forbidden(inPath);
        }

        @Override
        public DirectoryStream<Path> newDirectoryStream(Path dir, DirectoryStream.Filter<? super Path> filter) throws IOException {
            throw FileSystems.forbidden(dir);
        }

        @Override
        public Map<String, Object> readAttributes(Path path, String attributes, LinkOption ... options) throws IOException {
            throw FileSystems.forbidden(path);
        }

        @Override
        public void setAttribute(Path path, String attribute, Object value2, LinkOption ... options) throws IOException {
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
        public Path toRealPath(Path path, LinkOption ... linkOptions) throws IOException {
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
        public void createSymbolicLink(Path link, Path target, FileAttribute<?> ... attrs) throws IOException {
            throw FileSystems.forbidden(link);
        }

        @Override
        public Path readSymbolicLink(Path link) throws IOException {
            throw FileSystems.forbidden(link);
        }

        @Override
        public boolean isSameFile(Path path1, Path path2, LinkOption ... options) throws IOException {
            throw FileSystems.forbidden(path1);
        }
    }

    private static final class NIOFileSystem
    implements PolyglotFileSystem {
        private final FileSystemProvider hostfs;
        private final boolean explicitUserDir;
        private volatile Path userDir;
        private volatile Path tmpDir;

        NIOFileSystem(FileSystemProvider fileSystemProvider) {
            this(fileSystemProvider, false, null);
        }

        NIOFileSystem(FileSystemProvider fileSystemProvider, Path userDir) {
            this(fileSystemProvider, true, userDir);
        }

        private NIOFileSystem(FileSystemProvider fileSystemProvider, boolean explicitUserDir, Path userDir) {
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
            return FileSystems.FILE_SCHEME.equals(this.hostfs.getScheme());
        }

        @Override
        public boolean hasNoAccess() {
            return false;
        }

        @Override
        public Path parsePath(URI uri) {
            try {
                return this.hostfs.getPath(uri);
            }
            catch (IllegalArgumentException | FileSystemNotFoundException e) {
                throw new UnsupportedOperationException(e);
            }
        }

        @Override
        public Path parsePath(String path) {
            if (!FileSystems.FILE_SCHEME.equals(this.hostfs.getScheme())) {
                throw new IllegalStateException("The ParsePath(String path) should be called only for file scheme.");
            }
            return Paths.get(path, new String[0]);
        }

        @Override
        public void checkAccess(Path path, Set<? extends AccessMode> modes, LinkOption ... linkOptions) throws IOException {
            if (FileSystems.isFollowLinks(linkOptions)) {
                this.hostfs.checkAccess(this.resolveRelative(path), modes.toArray(new AccessMode[modes.size()]));
            } else if (modes.isEmpty()) {
                this.hostfs.readAttributes(path, "isRegularFile", LinkOption.NOFOLLOW_LINKS);
            } else {
                throw new UnsupportedOperationException("CheckAccess for NIO Provider is unsupported with non empty AccessMode and NOFOLLOW_LINKS.");
            }
        }

        @Override
        public void createDirectory(Path dir, FileAttribute<?> ... attrs) throws IOException {
            this.hostfs.createDirectory(this.resolveRelative(dir), attrs);
        }

        @Override
        public void delete(Path path) throws IOException {
            this.hostfs.delete(this.resolveRelative(path));
        }

        @Override
        public void copy(Path source, Path target, CopyOption ... options) throws IOException {
            this.hostfs.copy(this.resolveRelative(source), this.resolveRelative(target), options);
        }

        @Override
        public void move(Path source, Path target, CopyOption ... options) throws IOException {
            this.hostfs.move(this.resolveRelative(source), this.resolveRelative(target), options);
        }

        @Override
        public SeekableByteChannel newByteChannel(Path path, Set<? extends OpenOption> options, FileAttribute<?> ... attrs) throws IOException {
            Path resolved = this.resolveRelative(path);
            try {
                return this.hostfs.newFileChannel(resolved, options, attrs);
            }
            catch (UnsupportedOperationException uoe) {
                return this.hostfs.newByteChannel(resolved, options, attrs);
            }
        }

        @Override
        public DirectoryStream<Path> newDirectoryStream(Path dir, DirectoryStream.Filter<? super Path> filter) throws IOException {
            boolean relativize;
            Path resolvedPath;
            Path cwd = this.userDir;
            if (!dir.isAbsolute() && cwd != null) {
                resolvedPath = cwd.resolve(dir);
                relativize = true;
            } else {
                resolvedPath = dir;
                relativize = false;
            }
            RelativizeDirectoryStream result = this.hostfs.newDirectoryStream(resolvedPath, filter);
            if (relativize) {
                result = new RelativizeDirectoryStream(cwd, result);
            }
            return result;
        }

        @Override
        public void createLink(Path link, Path existing) throws IOException {
            this.hostfs.createLink(this.resolveRelative(link), this.resolveRelative(existing));
        }

        @Override
        public void createSymbolicLink(Path link, Path target, FileAttribute<?> ... attrs) throws IOException {
            this.hostfs.createSymbolicLink(this.resolveRelative(link), target, attrs);
        }

        @Override
        public Path readSymbolicLink(Path link) throws IOException {
            return this.hostfs.readSymbolicLink(this.resolveRelative(link));
        }

        @Override
        public Map<String, Object> readAttributes(Path path, String attributes, LinkOption ... options) throws IOException {
            return this.hostfs.readAttributes(this.resolveRelative(path), attributes, options);
        }

        @Override
        public void setAttribute(Path path, String attribute, Object value2, LinkOption ... options) throws IOException {
            this.hostfs.setAttribute(this.resolveRelative(path), attribute, value2, options);
        }

        @Override
        public Path toAbsolutePath(Path path) {
            if (path.isAbsolute()) {
                return path;
            }
            Path cwd = this.userDir;
            if (cwd == null) {
                if (this.explicitUserDir) {
                    throw new SecurityException("Access to user.dir is not allowed.");
                }
                return path.toAbsolutePath();
            }
            return cwd.resolve(path);
        }

        @Override
        public void setCurrentWorkingDirectory(Path currentWorkingDirectory) {
            boolean nonDirectory;
            Objects.requireNonNull(currentWorkingDirectory, "Current working directory must be non null.");
            if (!currentWorkingDirectory.isAbsolute()) {
                throw new IllegalArgumentException("Current working directory must be absolute.");
            }
            try {
                nonDirectory = Boolean.FALSE.equals(this.hostfs.readAttributes(currentWorkingDirectory, "isDirectory", new LinkOption[0]).get("isDirectory"));
            }
            catch (IOException ioe) {
                nonDirectory = false;
            }
            if (nonDirectory) {
                throw new IllegalArgumentException("Current working directory must be directory.");
            }
            if (this.explicitUserDir && this.userDir == null) {
                throw new SecurityException("Modification of current working directory is not allowed.");
            }
            this.userDir = currentWorkingDirectory;
        }

        @Override
        public Path toRealPath(Path path, LinkOption ... linkOptions) throws IOException {
            Path resolvedPath = this.resolveRelative(path);
            return resolvedPath.toRealPath(linkOptions);
        }

        @Override
        public Path getTempDirectory() {
            Path result = this.tmpDir;
            if (result == null) {
                if (TMP_FILE == null) {
                    throw new IllegalStateException("The java.io.tmpdir is not set.");
                }
                this.tmpDir = result = this.parsePath(TMP_FILE);
            }
            return result;
        }

        @Override
        public boolean isSameFile(Path path1, Path path2, LinkOption ... options) throws IOException {
            if (FileSystems.isFollowLinks(options)) {
                Path absolutePath1 = this.resolveRelative(path1);
                Path absolutePath2 = this.resolveRelative(path2);
                return this.hostfs.isSameFile(absolutePath1, absolutePath2);
            }
            return PolyglotFileSystem.super.isSameFile(path1, path2, options);
        }

        private Path resolveRelative(Path path) {
            return !path.isAbsolute() && this.userDir != null ? this.toAbsolutePath(path) : path;
        }

        private static final class RelativizeDirectoryStream
        implements DirectoryStream<Path> {
            private final Path folder;
            private final DirectoryStream<? extends Path> delegateDirectoryStream;

            RelativizeDirectoryStream(Path folder, DirectoryStream<? extends Path> delegateDirectoryStream) {
                this.folder = folder;
                this.delegateDirectoryStream = delegateDirectoryStream;
            }

            @Override
            public Iterator<Path> iterator() {
                return new RelativizeIterator(this.folder, this.delegateDirectoryStream.iterator());
            }

            @Override
            public void close() throws IOException {
                this.delegateDirectoryStream.close();
            }

            private static final class RelativizeIterator
            implements Iterator<Path> {
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

                @Override
                public Path next() {
                    return this.folder.relativize(this.delegateIterator.next());
                }
            }
        }
    }

    static final class PreInitializeContextFileSystem
    implements PolyglotFileSystem {
        private org.graalvm.polyglot.io.FileSystem delegate = FileSystems.newDefaultFileSystem();
        private Function<Path, PreInitializePath> factory = new ImageBuildTimeFactory();

        PreInitializeContextFileSystem() {
        }

        void onPreInitializeContextEnd() {
            if (this.factory == null) {
                throw new IllegalStateException("Context pre-initialization already finished.");
            }
            ((ImageBuildTimeFactory)this.factory).onPreInitializeContextEnd();
            this.factory = null;
            this.delegate = INVALID_FILESYSTEM;
        }

        void onLoadPreinitializedContext(org.graalvm.polyglot.io.FileSystem newDelegate) {
            Objects.requireNonNull(newDelegate, "NewDelegate must be non null.");
            if (this.factory != null) {
                throw new IllegalStateException("Pre-initialized context already loaded.");
            }
            this.delegate = newDelegate;
            this.factory = new ImageExecutionTimeFactory();
        }

        String pathToString(Path path) {
            if (this.delegate != INVALID_FILESYSTEM) {
                return path.toString();
            }
            PreInitializeContextFileSystem.verifyImageState();
            return ((PreInitializePath)path).resolve(FileSystems.newDefaultFileSystem()).toString();
        }

        URI absolutePathtoURI(Path path) {
            if (this.delegate != INVALID_FILESYSTEM) {
                return path.toUri();
            }
            PreInitializeContextFileSystem.verifyImageState();
            Path resolved = ((PreInitializePath)path).resolve(FileSystems.newDefaultFileSystem());
            if (!resolved.isAbsolute()) {
                throw new IllegalArgumentException("Path must be absolute.");
            }
            return resolved.toUri();
        }

        private static void verifyImageState() {
            if (ImageInfo.inImageBuildtimeCode()) {
                throw new IllegalStateException("Reintroducing absolute path into an image heap.");
            }
        }

        @Override
        public boolean isInternal() {
            return this.delegate instanceof PolyglotFileSystem && ((PolyglotFileSystem)this.delegate).isInternal();
        }

        @Override
        public boolean hasAllAccess() {
            return this.delegate instanceof PolyglotFileSystem && ((PolyglotFileSystem)this.delegate).hasAllAccess();
        }

        @Override
        public boolean hasNoAccess() {
            return this.delegate instanceof PolyglotFileSystem && ((PolyglotFileSystem)this.delegate).hasNoAccess();
        }

        @Override
        public Path parsePath(URI path) {
            try {
                return this.wrap(this.delegate.parsePath(path));
            }
            catch (IllegalArgumentException | FileSystemNotFoundException e) {
                throw new UnsupportedOperationException(e);
            }
        }

        @Override
        public Path parsePath(String path) {
            return this.wrap(this.delegate.parsePath(path));
        }

        @Override
        public void checkAccess(Path path, Set<? extends AccessMode> modes, LinkOption ... linkOptions) throws IOException {
            this.delegate.checkAccess(PreInitializeContextFileSystem.unwrap(path), modes, linkOptions);
        }

        @Override
        public void createDirectory(Path dir, FileAttribute<?> ... attrs) throws IOException {
            this.delegate.createDirectory(PreInitializeContextFileSystem.unwrap(dir), attrs);
        }

        @Override
        public void delete(Path path) throws IOException {
            this.delegate.delete(PreInitializeContextFileSystem.unwrap(path));
        }

        @Override
        public SeekableByteChannel newByteChannel(Path path, Set<? extends OpenOption> options, FileAttribute<?> ... attrs) throws IOException {
            return this.delegate.newByteChannel(PreInitializeContextFileSystem.unwrap(path), options, attrs);
        }

        @Override
        public DirectoryStream<Path> newDirectoryStream(Path dir, DirectoryStream.Filter<? super Path> filter) throws IOException {
            final DirectoryStream<Path> delegateStream = this.delegate.newDirectoryStream(PreInitializeContextFileSystem.unwrap(dir), filter);
            return new DirectoryStream<Path>(){

                @Override
                public Iterator<Path> iterator() {
                    return new WrappingPathIterator(delegateStream.iterator());
                }

                @Override
                public void close() throws IOException {
                    delegateStream.close();
                }
            };
        }

        @Override
        public Path toAbsolutePath(Path path) {
            return this.wrap(this.delegate.toAbsolutePath(PreInitializeContextFileSystem.unwrap(path)));
        }

        @Override
        public Path toRealPath(Path path, LinkOption ... linkOptions) throws IOException {
            return this.wrap(this.delegate.toRealPath(PreInitializeContextFileSystem.unwrap(path), linkOptions));
        }

        @Override
        public Map<String, Object> readAttributes(Path path, String attributes, LinkOption ... options) throws IOException {
            return this.delegate.readAttributes(PreInitializeContextFileSystem.unwrap(path), attributes, options);
        }

        @Override
        public void setAttribute(Path path, String attribute, Object value2, LinkOption ... options) throws IOException {
            this.delegate.setAttribute(PreInitializeContextFileSystem.unwrap(path), attribute, value2, options);
        }

        @Override
        public void copy(Path source, Path target, CopyOption ... options) throws IOException {
            this.delegate.copy(PreInitializeContextFileSystem.unwrap(source), PreInitializeContextFileSystem.unwrap(target), options);
        }

        @Override
        public void move(Path source, Path target, CopyOption ... options) throws IOException {
            this.delegate.move(PreInitializeContextFileSystem.unwrap(source), PreInitializeContextFileSystem.unwrap(target), options);
        }

        @Override
        public void createLink(Path link, Path existing) throws IOException {
            this.delegate.createLink(PreInitializeContextFileSystem.unwrap(link), PreInitializeContextFileSystem.unwrap(existing));
        }

        @Override
        public void createSymbolicLink(Path link, Path target, FileAttribute<?> ... attrs) throws IOException {
            this.delegate.createSymbolicLink(PreInitializeContextFileSystem.unwrap(link), PreInitializeContextFileSystem.unwrap(target), attrs);
        }

        @Override
        public Path readSymbolicLink(Path link) throws IOException {
            return this.wrap(this.delegate.readSymbolicLink(PreInitializeContextFileSystem.unwrap(link)));
        }

        @Override
        public void setCurrentWorkingDirectory(Path currentWorkingDirectory) {
            this.delegate.setCurrentWorkingDirectory(PreInitializeContextFileSystem.unwrap(currentWorkingDirectory));
        }

        @Override
        public String getSeparator() {
            return this.delegate.getSeparator();
        }

        @Override
        public Charset getEncoding(Path path) {
            return this.delegate.getEncoding(PreInitializeContextFileSystem.unwrap(path));
        }

        @Override
        public String getMimeType(Path path) {
            return this.delegate.getMimeType(PreInitializeContextFileSystem.unwrap(path));
        }

        @Override
        public Path getTempDirectory() {
            return this.wrap(this.delegate.getTempDirectory());
        }

        @Override
        public boolean isSameFile(Path path1, Path path2, LinkOption ... options) throws IOException {
            return this.delegate.isSameFile(PreInitializeContextFileSystem.unwrap(path1), PreInitializeContextFileSystem.unwrap(path2), options);
        }

        public int hashCode() {
            return this.delegate.hashCode();
        }

        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof PreInitializeContextFileSystem)) {
                return false;
            }
            return this.delegate.equals(((PreInitializeContextFileSystem)other).delegate);
        }

        Path wrap(Path path) {
            return path == null ? null : (Path)this.factory.apply(path);
        }

        static Path unwrap(Path path) {
            return path.getClass() == PreInitializePath.class ? ((PreInitializePath)path).getDelegate() : path;
        }

        private static final class ImageHeapPath {
            private final String languageId;
            private final String path;

            ImageHeapPath(String languageId, String path) {
                assert (path != null);
                this.languageId = languageId;
                this.path = path;
            }
        }

        private final class PreInitializePath
        implements Path {
            private volatile Object delegatePath;

            PreInitializePath(Path delegatePath) {
                this.delegatePath = delegatePath;
            }

            private Path getDelegate() {
                Path result = this.resolve(PreInitializeContextFileSystem.this.delegate);
                this.delegatePath = result;
                return result;
            }

            private Path resolve(org.graalvm.polyglot.io.FileSystem fs) {
                Object current = this.delegatePath;
                if (current instanceof Path) {
                    return (Path)current;
                }
                if (current instanceof ImageHeapPath) {
                    String newLanguageHome;
                    ImageHeapPath imageHeapPath = (ImageHeapPath)current;
                    String languageId = imageHeapPath.languageId;
                    String path = imageHeapPath.path;
                    Path result = languageId != null && (newLanguageHome = LanguageCache.languages().get(languageId).getLanguageHome()) != null ? fs.parsePath(newLanguageHome).resolve(path) : fs.parsePath(path);
                    return result;
                }
                throw new IllegalStateException("Unknown delegate " + String.valueOf(current));
            }

            void onPreInitializeContextEnd(Map<String, Path> languageHomes) {
                Path internalPath = (Path)this.delegatePath;
                String languageId = null;
                for (Map.Entry<String, Path> e : languageHomes.entrySet()) {
                    if (!internalPath.startsWith(e.getValue())) continue;
                    internalPath = e.getValue().relativize(internalPath);
                    languageId = e.getKey();
                    break;
                }
                this.delegatePath = new ImageHeapPath(languageId, internalPath.toString());
            }

            @Override
            public FileSystem getFileSystem() {
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
                return this.getDelegate().startsWith(PreInitializeContextFileSystem.unwrap(other));
            }

            @Override
            public boolean startsWith(String other) {
                return this.getDelegate().startsWith(other);
            }

            @Override
            public boolean endsWith(Path other) {
                return this.getDelegate().endsWith(PreInitializeContextFileSystem.unwrap(other));
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
                return PreInitializeContextFileSystem.this.wrap(this.getDelegate().resolve(PreInitializeContextFileSystem.unwrap(other)));
            }

            @Override
            public Path resolve(String other) {
                return PreInitializeContextFileSystem.this.wrap(this.getDelegate().resolve(other));
            }

            @Override
            public Path resolveSibling(Path other) {
                return PreInitializeContextFileSystem.this.wrap(this.getDelegate().resolveSibling(PreInitializeContextFileSystem.unwrap(other)));
            }

            @Override
            public Path resolveSibling(String other) {
                return PreInitializeContextFileSystem.this.wrap(this.getDelegate().resolveSibling(other));
            }

            @Override
            public Path relativize(Path other) {
                return PreInitializeContextFileSystem.this.wrap(this.getDelegate().relativize(PreInitializeContextFileSystem.unwrap(other)));
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
            public Path toRealPath(LinkOption ... options) throws IOException {
                return PreInitializeContextFileSystem.this.wrap(this.getDelegate().toRealPath(options));
            }

            @Override
            public File toFile() {
                return this.getDelegate().toFile();
            }

            @Override
            public WatchKey register(WatchService watcher, WatchEvent.Kind<?>[] events, WatchEvent.Modifier ... modifiers) throws IOException {
                return this.getDelegate().register(watcher, events, modifiers);
            }

            @Override
            public WatchKey register(WatchService watcher, WatchEvent.Kind<?> ... events) throws IOException {
                return this.getDelegate().register(watcher, events);
            }

            @Override
            public Iterator<Path> iterator() {
                return new WrappingPathIterator(this.getDelegate().iterator());
            }

            @Override
            public int compareTo(Path other) {
                return this.getDelegate().compareTo(PreInitializeContextFileSystem.unwrap(other));
            }

            @Override
            public int hashCode() {
                return this.getDelegate().hashCode();
            }

            @Override
            public boolean equals(Object other) {
                if (other == this) {
                    return true;
                }
                if (!(other instanceof Path)) {
                    return false;
                }
                return this.getDelegate().equals(PreInitializeContextFileSystem.unwrap((Path)other));
            }

            @Override
            public String toString() {
                return this.getDelegate().toString();
            }
        }

        private final class WrappingPathIterator
        implements Iterator<Path> {
            private final Iterator<Path> delegateIterator;

            WrappingPathIterator(Iterator<Path> delegateIterator) {
                this.delegateIterator = delegateIterator;
            }

            @Override
            public boolean hasNext() {
                return this.delegateIterator.hasNext();
            }

            @Override
            public Path next() {
                return PreInitializeContextFileSystem.this.wrap(this.delegateIterator.next());
            }
        }

        private final class ImageBuildTimeFactory
        extends ImageExecutionTimeFactory {
            private final Collection<Reference<PreInitializePath>> emittedPaths;

            private ImageBuildTimeFactory() {
                this.emittedPaths = new ArrayList<Reference<PreInitializePath>>();
            }

            @Override
            public PreInitializePath apply(Path path) {
                PreInitializePath preInitPath = super.apply(path);
                this.emittedPaths.add(new WeakReference<PreInitializePath>(preInitPath));
                return preInitPath;
            }

            void onPreInitializeContextEnd() {
                HashMap<String, Path> languageHomes = new HashMap<String, Path>();
                for (LanguageCache languageCache : LanguageCache.languages().values()) {
                    String languageHome = languageCache.getLanguageHome();
                    if (languageHome == null) continue;
                    languageHomes.put(languageCache.getId(), PreInitializeContextFileSystem.this.delegate.parsePath(languageHome));
                }
                for (Reference reference : this.emittedPaths) {
                    PreInitializePath path = (PreInitializePath)reference.get();
                    if (path == null) continue;
                    path.onPreInitializeContextEnd(languageHomes);
                }
            }
        }

        private class ImageExecutionTimeFactory
        implements Function<Path, PreInitializePath> {
            private ImageExecutionTimeFactory() {
            }

            @Override
            public PreInitializePath apply(Path path) {
                return new PreInitializePath(path);
            }
        }
    }
}

