
package com.oracle.truffle.api;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.LanguageAccessor;
import com.oracle.truffle.api.TruffleLanguage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import org.graalvm.polyglot.io.FileSystem;

public final class TruffleFile {
    public static final AttributeDescriptor<FileTime> LAST_MODIFIED_TIME = new AttributeDescriptor<FileTime>(AttributeGroup.BASIC, "lastModifiedTime", FileTime.class);
    public static final AttributeDescriptor<FileTime> LAST_ACCESS_TIME = new AttributeDescriptor<FileTime>(AttributeGroup.BASIC, "lastAccessTime", FileTime.class);
    public static final AttributeDescriptor<FileTime> CREATION_TIME = new AttributeDescriptor<FileTime>(AttributeGroup.BASIC, "creationTime", FileTime.class);
    public static final AttributeDescriptor<Boolean> IS_REGULAR_FILE = new AttributeDescriptor<Boolean>(AttributeGroup.BASIC, "isRegularFile", Boolean.class);
    public static final AttributeDescriptor<Boolean> IS_DIRECTORY = new AttributeDescriptor<Boolean>(AttributeGroup.BASIC, "isDirectory", Boolean.class);
    public static final AttributeDescriptor<Boolean> IS_SYMBOLIC_LINK = new AttributeDescriptor<Boolean>(AttributeGroup.BASIC, "isSymbolicLink", Boolean.class);
    public static final AttributeDescriptor<Boolean> IS_OTHER = new AttributeDescriptor<Boolean>(AttributeGroup.BASIC, "isOther", Boolean.class);
    public static final AttributeDescriptor<Long> SIZE = new AttributeDescriptor<Long>(AttributeGroup.BASIC, "size", Long.class);
    public static final AttributeDescriptor<UserPrincipal> UNIX_OWNER = new AttributeDescriptor<UserPrincipal>(AttributeGroup.POSIX, "owner", UserPrincipal.class);
    public static final AttributeDescriptor<GroupPrincipal> UNIX_GROUP = new AttributeDescriptor<GroupPrincipal>(AttributeGroup.POSIX, "group", GroupPrincipal.class);
    public static final AttributeDescriptor<Set<PosixFilePermission>> UNIX_PERMISSIONS = new AttributeDescriptor(AttributeGroup.POSIX, Set.class, "permissions");
    public static final AttributeDescriptor<Integer> UNIX_MODE = new AttributeDescriptor<Integer>(AttributeGroup.UNIX, "mode", Integer.class);
    public static final AttributeDescriptor<Long> UNIX_INODE = new AttributeDescriptor<Long>(AttributeGroup.UNIX, "ino", Long.class);
    public static final AttributeDescriptor<Long> UNIX_DEV = new AttributeDescriptor<Long>(AttributeGroup.UNIX, "dev", Long.class);
    public static final AttributeDescriptor<Long> UNIX_RDEV = new AttributeDescriptor<Long>(AttributeGroup.UNIX, "rdev", Long.class);
    public static final AttributeDescriptor<Integer> UNIX_NLINK = new AttributeDescriptor<Integer>(AttributeGroup.UNIX, "nlink", Integer.class);
    public static final AttributeDescriptor<Integer> UNIX_UID = new AttributeDescriptor<Integer>(AttributeGroup.UNIX, "uid", Integer.class);
    public static final AttributeDescriptor<Integer> UNIX_GID = new AttributeDescriptor<Integer>(AttributeGroup.UNIX, "gid", Integer.class);
    public static final AttributeDescriptor<FileTime> UNIX_CTIME = new AttributeDescriptor<FileTime>(AttributeGroup.UNIX, "ctime", FileTime.class);
    private static final int MAX_BUFFER_SIZE = 0x7FFFFFF7;
    private static final int BUFFER_SIZE = 8192;
    private final FileSystemContext fileSystemContext;
    private final Path path;
    private final Path normalizedPath;
    private final boolean isEmptyPath;

    TruffleFile(FileSystemContext fileSystemContext, Path path) {
        this(fileSystemContext, path, path.normalize(), TruffleFile.isEmptyPath(path));
    }

    TruffleFile(FileSystemContext fileSystemContext, Path path, Path normalizedPath, boolean isEmptyPath) {
        Objects.requireNonNull(fileSystemContext, "FileSystemContext must not be null.");
        Objects.requireNonNull(path, "Path must not be null.");
        Objects.requireNonNull(normalizedPath, "NormalizedPath must not be null.");
        this.fileSystemContext = fileSystemContext;
        this.path = path;
        this.normalizedPath = normalizedPath;
        this.isEmptyPath = isEmptyPath;
    }

    Path getSPIPath() {
        return this.normalizedPath;
    }

    FileSystemContext getFileSystemContext() {
        return this.fileSystemContext;
    }

    FileSystem getSPIFileSystem() {
        return this.fileSystemContext.fileSystem;
    }

    @CompilerDirectives.TruffleBoundary
    public boolean exists(LinkOption ... options) {
        try {
            return this.checkAccess(EnumSet.noneOf(AccessMode.class), options);
        }
        catch (SecurityException se) {
            throw se;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public boolean isReadable() {
        try {
            return this.checkAccess(AccessMode.READ);
        }
        catch (SecurityException se) {
            throw se;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public boolean isWritable() {
        try {
            return this.checkAccess(AccessMode.WRITE);
        }
        catch (SecurityException se) {
            throw se;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public boolean isExecutable() {
        try {
            return this.checkAccess(AccessMode.EXECUTE);
        }
        catch (SecurityException se) {
            throw se;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public boolean isDirectory(LinkOption ... options) {
        try {
            return this.getAttributeImpl("isDirectory", Boolean.class, options);
        }
        catch (IOException ioe) {
            return false;
        }
        catch (SecurityException se) {
            throw se;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public boolean isRegularFile(LinkOption ... options) {
        try {
            return this.getAttributeImpl("isRegularFile", Boolean.class, options);
        }
        catch (IOException ioe) {
            return false;
        }
        catch (SecurityException se) {
            throw se;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public boolean isSymbolicLink() {
        try {
            return this.getAttributeImpl("isSymbolicLink", Boolean.class, LinkOption.NOFOLLOW_LINKS);
        }
        catch (IOException ioe) {
            return false;
        }
        catch (SecurityException se) {
            throw se;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public boolean isAbsolute() {
        try {
            return this.path.isAbsolute();
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public String getName() {
        try {
            Path fileName = this.path.getFileName();
            return fileName == null ? null : fileName.toString();
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public String getPath() {
        try {
            return this.path.toString();
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public URI toUri() {
        try {
            Path absolutePath = this.path.isAbsolute() ? this.path : this.toAbsolutePathImpl()[0];
            return absolutePath.toUri();
        }
        catch (SecurityException se) {
            throw se;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public URI toRelativeUri() {
        if (this.isAbsolute()) {
            return this.toUri();
        }
        try {
            String strPath = "/".equals(this.fileSystemContext.fileSystem.getSeparator()) ? this.path.toString() : this.path.toString().replace(this.fileSystemContext.fileSystem.getSeparator(), "/");
            return new URI(null, null, strPath, null);
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public TruffleFile getAbsoluteFile() {
        if (this.path.isAbsolute()) {
            return this;
        }
        try {
            Path[] absolutePaths = this.toAbsolutePathImpl();
            return new TruffleFile(this.fileSystemContext, absolutePaths[0], absolutePaths[1], false);
        }
        catch (SecurityException se) {
            throw se;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public TruffleFile getCanonicalFile(LinkOption ... options) throws IOException {
        try {
            Path realPath = this.fileSystemContext.fileSystem.toRealPath(this.normalizedPath, options);
            return new TruffleFile(this.fileSystemContext, realPath, realPath, false);
        }
        catch (IOException | SecurityException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public TruffleFile getParent() {
        try {
            Path parent = this.path.getParent();
            return parent == null ? null : new TruffleFile(this.fileSystemContext, parent);
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public TruffleFile resolve(String name) {
        try {
            return new TruffleFile(this.fileSystemContext, this.path.resolve(name));
        }
        catch (InvalidPathException ip) {
            throw ip;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public TruffleFile resolveSibling(String name) {
        try {
            return new TruffleFile(this.fileSystemContext, this.path.resolveSibling(name));
        }
        catch (InvalidPathException ip) {
            throw ip;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public long size(LinkOption ... options) throws IOException {
        try {
            return this.getAttributeImpl("size", Long.class, options);
        }
        catch (IOException | SecurityException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public FileTime getLastModifiedTime(LinkOption ... options) throws IOException {
        try {
            return this.getAttributeImpl("lastModifiedTime", FileTime.class, options);
        }
        catch (IOException | SecurityException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public void setLastModifiedTime(FileTime time, LinkOption ... options) throws IOException {
        try {
            this.checkFileOperationPreconditions();
            this.fileSystemContext.fileSystem.setAttribute(this.normalizedPath, "lastModifiedTime", time, options);
        }
        catch (IOException | SecurityException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public FileTime getLastAccessTime(LinkOption ... options) throws IOException {
        try {
            return this.getAttributeImpl("lastAccessTime", FileTime.class, options);
        }
        catch (IOException | SecurityException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public void setLastAccessTime(FileTime time, LinkOption ... options) throws IOException {
        try {
            this.checkFileOperationPreconditions();
            this.fileSystemContext.fileSystem.setAttribute(this.normalizedPath, "lastAccessTime", time, options);
        }
        catch (IOException | SecurityException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public FileTime getCreationTime(LinkOption ... options) throws IOException {
        try {
            return this.getAttributeImpl("creationTime", FileTime.class, options);
        }
        catch (IOException | SecurityException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public void setCreationTime(FileTime time, LinkOption ... options) throws IOException {
        try {
            this.checkFileOperationPreconditions();
            this.fileSystemContext.fileSystem.setAttribute(this.normalizedPath, "creationTime", time, options);
        }
        catch (IOException | SecurityException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public Collection<TruffleFile> list() throws IOException {
        try {
            this.checkFileOperationPreconditions();
            ArrayList<TruffleFile> result = new ArrayList<TruffleFile>();
            boolean normalized = this.isNormalized();
            try (DirectoryStream<Path> stream = this.fileSystemContext.fileSystem.newDirectoryStream(this.normalizedPath, AllFiles.INSTANCE);){
                for (Path p : stream) {
                    result.add(new TruffleFile(this.fileSystemContext, normalized ? p : this.path.resolve(p.getFileName()), normalized ? p : this.normalizedPath.resolve(p.getFileName()), false));
                }
            }
            return result;
        }
        catch (IOException | SecurityException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public SeekableByteChannel newByteChannel(Set<? extends OpenOption> options, FileAttribute<?> ... attributes) throws IOException {
        try {
            this.checkFileOperationPreconditions();
            return ByteChannelDecorator.create(this.fileSystemContext.fileSystem.newByteChannel(this.normalizedPath, options, attributes));
        }
        catch (IOException | IllegalArgumentException | SecurityException | UnsupportedOperationException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public InputStream newInputStream(OpenOption ... options) throws IOException {
        HashSet<OpenOption> openOptions = new HashSet<OpenOption>();
        if (options.length > 0) {
            for (OpenOption option : options) {
                if (option == StandardOpenOption.APPEND || option == StandardOpenOption.WRITE) {
                    throw new IllegalArgumentException(String.format("Option %s is not allowed.", option));
                }
                openOptions.add(option);
            }
        }
        return Channels.newInputStream(this.newByteChannel(openOptions, new FileAttribute[0]));
    }

    @CompilerDirectives.TruffleBoundary
    public BufferedReader newBufferedReader(Charset charset) throws IOException {
        return new BufferedReader(new InputStreamReader(this.newInputStream(new OpenOption[0]), charset));
    }

    @CompilerDirectives.TruffleBoundary
    public BufferedReader newBufferedReader() throws IOException {
        return this.newBufferedReader(StandardCharsets.UTF_8);
    }

    /*
     * Enabled aggressive exception aggregation
     */
    @CompilerDirectives.TruffleBoundary
    public byte[] readAllBytes() throws IOException {
        try (SeekableByteChannel channel = this.newByteChannel(Collections.emptySet(), new FileAttribute[0]);){
            byte[] byArray;
            block21: {
                long sizel = channel.size();
                if (sizel > 0x7FFFFFF7L) {
                    throw new OutOfMemoryError("File size is too large.");
                }
                InputStream in = Channels.newInputStream(channel);
                try {
                    int size = (int)sizel;
                    byte[] buf = new byte[size];
                    int read2 = 0;
                    while (true) {
                        int n;
                        if ((n = in.read(buf, read2, size - read2)) > 0) {
                            read2 += n;
                            continue;
                        }
                        if (n < 0 || (n = in.read()) < 0) break;
                        if (size << 1 <= 0x7FFFFFF7) {
                            size = Math.max(size << 1, 8192);
                        } else {
                            if (size == 0x7FFFFFF7) {
                                throw new OutOfMemoryError("Required array size too large");
                            }
                            size = 0x7FFFFFF7;
                        }
                        buf = Arrays.copyOf(buf, size);
                        buf[read2++] = (byte)n;
                    }
                    byte[] byArray2 = byArray = size == read2 ? buf : Arrays.copyOf(buf, read2);
                    if (in == null) break block21;
                }
                catch (Throwable throwable) {
                    if (in != null) {
                        try {
                            in.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                in.close();
            }
            return byArray;
        }
        catch (IOException | OutOfMemoryError | SecurityException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public OutputStream newOutputStream(OpenOption ... options) throws IOException {
        HashSet<OpenOption> openOptions = new HashSet<OpenOption>(Math.max(options.length, 2) + 1);
        openOptions.add(StandardOpenOption.WRITE);
        if (options.length == 0) {
            openOptions.add(StandardOpenOption.CREATE);
            openOptions.add(StandardOpenOption.TRUNCATE_EXISTING);
        } else {
            for (OpenOption option : options) {
                if (option == StandardOpenOption.READ) {
                    throw new IllegalArgumentException(String.format("Option %s is not allowed.", option));
                }
                openOptions.add(option);
            }
        }
        return Channels.newOutputStream(this.newByteChannel(openOptions, new FileAttribute[0]));
    }

    @CompilerDirectives.TruffleBoundary
    public BufferedWriter newBufferedWriter(Charset charset, OpenOption ... options) throws IOException {
        return new BufferedWriter(new OutputStreamWriter(this.newOutputStream(options), charset));
    }

    @CompilerDirectives.TruffleBoundary
    public BufferedWriter newBufferedWriter(OpenOption ... options) throws IOException {
        return this.newBufferedWriter(StandardCharsets.UTF_8, options);
    }

    @CompilerDirectives.TruffleBoundary
    public void createFile(FileAttribute<?> ... attributes) throws IOException {
        this.newByteChannel(EnumSet.of(StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW), attributes).close();
    }

    @CompilerDirectives.TruffleBoundary
    public void createDirectory(FileAttribute<?> ... attributes) throws IOException {
        try {
            this.checkFileOperationPreconditions();
            this.createDirectoryImpl(this.normalizedPath, attributes);
        }
        catch (IOException | SecurityException | UnsupportedOperationException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public void createDirectories(FileAttribute<?> ... attributes) throws IOException {
        try {
            this.checkFileOperationPreconditions();
            try {
                this.createDirAndCheck(this.normalizedPath, attributes);
                return;
            }
            catch (FileAlreadyExistsException faee) {
                throw faee;
            }
            catch (IOException faee) {
                SecurityException notAllowed = null;
                Path absolutePath = this.normalizedPath;
                try {
                    absolutePath = this.fileSystemContext.fileSystem.toAbsolutePath(absolutePath);
                }
                catch (SecurityException se) {
                    notAllowed = se;
                }
                Path lastExisting = this.findExisting(absolutePath);
                if (lastExisting == null) {
                    if (notAllowed != null) {
                        throw notAllowed;
                    }
                    throw new FileSystemException(this.path.toString(), null, "Cannot determine root");
                }
                for (Path pathElement : lastExisting.relativize(absolutePath)) {
                    lastExisting = lastExisting.resolve(pathElement);
                    this.createDirAndCheck(lastExisting, attributes);
                }
            }
        }
        catch (IOException | SecurityException | UnsupportedOperationException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public void delete() throws IOException {
        try {
            this.checkFileOperationPreconditions();
            this.fileSystemContext.fileSystem.delete(this.normalizedPath);
        }
        catch (IOException | SecurityException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public void move(TruffleFile target, CopyOption ... options) throws IOException {
        try {
            this.checkFileOperationPreconditions();
            target.checkFileOperationPreconditions();
            this.fileSystemContext.fileSystem.move(this.normalizedPath, target.normalizedPath, options);
        }
        catch (IOException | SecurityException | UnsupportedOperationException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public Set<PosixFilePermission> getPosixPermissions(LinkOption ... linkOptions) throws IOException {
        try {
            return (Set)this.getAttributeImpl(this.normalizedPath, "posix:permissions", linkOptions);
        }
        catch (IOException | SecurityException | UnsupportedOperationException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public void setPosixPermissions(Set<? extends PosixFilePermission> permissions, LinkOption ... linkOptions) throws IOException {
        try {
            this.checkFileOperationPreconditions();
            this.fileSystemContext.fileSystem.setAttribute(this.normalizedPath, "posix:permissions", permissions, linkOptions);
        }
        catch (IOException | SecurityException | UnsupportedOperationException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        return this.path.toString();
    }

    @CompilerDirectives.TruffleBoundary
    public int hashCode() {
        int res = 17;
        res = res * 31 + this.fileSystemContext.hashCode();
        res = res * 31 + this.path.hashCode();
        return res;
    }

    @CompilerDirectives.TruffleBoundary
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || other.getClass() != TruffleFile.class) {
            return false;
        }
        TruffleFile otherFile = (TruffleFile)other;
        return this.path.equals(otherFile.path) && this.fileSystemContext.equals(otherFile.fileSystemContext);
    }

    @CompilerDirectives.TruffleBoundary
    public TruffleFile normalize() {
        if (this.isNormalized()) {
            return this;
        }
        Path newPath = !this.isEmptyPath && TruffleFile.isEmptyPath(this.normalizedPath) ? this.fileSystemContext.fileSystem.parsePath(".") : this.normalizedPath;
        return new TruffleFile(this.fileSystemContext, newPath, this.normalizedPath, this.isEmptyPath);
    }

    @CompilerDirectives.TruffleBoundary
    public TruffleFile relativize(TruffleFile other) {
        try {
            return new TruffleFile(this.fileSystemContext, this.path.relativize(other.path));
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public boolean startsWith(String other) {
        try {
            return this.path.startsWith(other);
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public boolean startsWith(TruffleFile other) {
        try {
            return this.path.startsWith(other.path);
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public boolean endsWith(String other) {
        try {
            return this.path.endsWith(other);
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public boolean endsWith(TruffleFile other) {
        try {
            return this.path.endsWith(other.path);
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public void createLink(TruffleFile target) throws IOException {
        try {
            this.checkFileOperationPreconditions();
            this.fileSystemContext.fileSystem.createLink(this.normalizedPath, target.normalizedPath);
        }
        catch (IOException | SecurityException | UnsupportedOperationException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public void createSymbolicLink(TruffleFile target, FileAttribute<?> ... attrs) throws IOException {
        try {
            this.checkFileOperationPreconditions();
            this.fileSystemContext.fileSystem.createSymbolicLink(this.normalizedPath, target.path, attrs);
        }
        catch (IOException | SecurityException | UnsupportedOperationException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public TruffleFile readSymbolicLink() throws IOException {
        try {
            this.checkFileOperationPreconditions();
            return new TruffleFile(this.fileSystemContext, this.fileSystemContext.fileSystem.readSymbolicLink(this.normalizedPath));
        }
        catch (IOException | SecurityException | UnsupportedOperationException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public UserPrincipal getOwner(LinkOption ... options) throws IOException {
        try {
            return this.getAttributeImpl("posix:owner", UserPrincipal.class, options);
        }
        catch (IOException | SecurityException | UnsupportedOperationException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public GroupPrincipal getGroup(LinkOption ... options) throws IOException {
        try {
            return this.getAttributeImpl("posix:group", GroupPrincipal.class, options);
        }
        catch (IOException | SecurityException | UnsupportedOperationException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public DirectoryStream<TruffleFile> newDirectoryStream() throws IOException {
        try {
            this.checkFileOperationPreconditions();
            return new TruffleFileDirectoryStream(this, this.fileSystemContext.fileSystem.newDirectoryStream(this.normalizedPath, AllFiles.INSTANCE));
        }
        catch (IOException | SecurityException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public void visit(FileVisitor<TruffleFile> visitor, int maxDepth, FileVisitOption ... options) throws IOException {
        if (maxDepth < 0) {
            throw new IllegalArgumentException("The maxDepth must be >= 0");
        }
        try {
            this.checkFileOperationPreconditions();
            Walker walker = new Walker(this, maxDepth, options);
            for (Walker.Event event : walker) {
                FileVisitResult result;
                switch (event.type) {
                    case PRE_VISIT_DIRECTORY: {
                        result = visitor.preVisitDirectory(event.file, event.attrs);
                        if (result != FileVisitResult.SKIP_SUBTREE && result != FileVisitResult.SKIP_SIBLINGS) break;
                        walker.pop();
                        break;
                    }
                    case VISIT: {
                        IOException ioe = event.ioe;
                        if (ioe == null) {
                            result = visitor.visitFile(event.file, event.attrs);
                            break;
                        }
                        result = visitor.visitFileFailed(event.file, ioe);
                        break;
                    }
                    case POST_VISIT_DIRECTORY: {
                        result = visitor.postVisitDirectory(event.file, event.ioe);
                        break;
                    }
                    default: {
                        throw new IllegalStateException("Unexpected event type: " + event.type);
                    }
                }
                if (Objects.requireNonNull(result) == FileVisitResult.CONTINUE) continue;
                switch (result) {
                    case SKIP_SIBLINGS: {
                        walker.skipRemainingSiblings();
                        break;
                    }
                    case TERMINATE: {
                        return;
                    }
                }
            }
        }
        catch (IOException | SecurityException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public void copy(TruffleFile target, CopyOption ... options) throws IOException {
        try {
            this.checkFileOperationPreconditions();
            target.checkFileOperationPreconditions();
            this.fileSystemContext.fileSystem.copy(this.normalizedPath, target.normalizedPath, options);
        }
        catch (IOException | SecurityException | UnsupportedOperationException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    @Deprecated(since="20.2")
    public String getMimeType() throws IOException {
        return this.detectMimeType(null);
    }

    @CompilerDirectives.TruffleBoundary
    public String detectMimeType() {
        return this.detectMimeType(null);
    }

    @CompilerDirectives.TruffleBoundary
    public boolean isSameFile(TruffleFile other, LinkOption ... options) throws IOException {
        try {
            this.checkFileOperationPreconditions();
            other.checkFileOperationPreconditions();
            if (this.equals(other)) {
                return true;
            }
            if (!this.fileSystemContext.fileSystem.equals(other.fileSystemContext.fileSystem)) {
                return false;
            }
            return this.fileSystemContext.fileSystem.isSameFile(this.normalizedPath, other.normalizedPath, options);
        }
        catch (IOException | SecurityException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    String detectMimeType(Set<String> validMimeTypes) {
        try {
            if (validMimeTypes != null && validMimeTypes.isEmpty()) {
                return null;
            }
            this.checkFileOperationPreconditions();
            String result = this.fileSystemContext.fileSystem.getMimeType(this.normalizedPath);
            if (result != null && (validMimeTypes == null || validMimeTypes.contains(result))) {
                return result;
            }
            for (FileTypeDetector fileTypeDetector : this.fileSystemContext.getFileTypeDetectors(validMimeTypes)) {
                try {
                    result = fileTypeDetector.findMimeType(this);
                    if (result == null || validMimeTypes != null && !validMimeTypes.contains(result)) continue;
                    return result;
                }
                catch (IOException ioe) {
                }
            }
            return null;
        }
        catch (IOException ioe) {
            return null;
        }
        catch (SecurityException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    Charset detectEncoding(String mimeType) {
        try {
            assert (mimeType != null);
            this.checkFileOperationPreconditions();
            Charset result = this.fileSystemContext.fileSystem.getEncoding(this.normalizedPath);
            if (result != null) {
                return result;
            }
            for (FileTypeDetector fileTypeDetector : this.fileSystemContext.getFileTypeDetectors(Collections.singleton(mimeType))) {
                try {
                    result = fileTypeDetector.findEncoding(this);
                    if (result == null) continue;
                    return result;
                }
                catch (IOException ioe) {
                }
            }
            return null;
        }
        catch (IOException ioe) {
            return null;
        }
        catch (SecurityException | UnsupportedOperationException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    static TruffleFile createTempFile(TruffleFile targetDirectory, String prefix, String suffix, boolean dir, FileAttribute<?> ... attrs) throws IOException {
        String usePrefix;
        Objects.requireNonNull(targetDirectory, "TargetDirectory must be non null.");
        targetDirectory.checkFileOperationPreconditions();
        String string = usePrefix = prefix != null ? prefix : "";
        String useSuffix = suffix != null ? suffix : (dir ? "" : ".tmp");
        while (true) {
            try {
                TruffleFile target;
                while ((target = TruffleFile.createUniquePath(targetDirectory, usePrefix, useSuffix)).exists(new LinkOption[0])) {
                }
                if (dir) {
                    target.createDirectory(attrs);
                } else {
                    target.createFile(attrs);
                }
                return target;
            }
            catch (InvalidPathException e) {
                throw new IllegalArgumentException("Prefix (" + usePrefix + ") or suffix (" + useSuffix + ") are not valid file name components");
            }
            catch (FileAlreadyExistsException fileAlreadyExistsException) {
                continue;
            }
            break;
        }
    }

    private void checkFileOperationPreconditions() throws IOException {
        if (this.isEmptyPath) {
            throw new NoSuchFileException("");
        }
    }

    private static TruffleFile createUniquePath(TruffleFile targetDirectory, String prefix, String suffix) {
        long n = TempFileRandomHolder.getRandom().nextLong();
        n = n == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(n);
        String name = prefix + Long.toString(n) + suffix;
        TruffleFile result = targetDirectory.resolve(name);
        if (!targetDirectory.equals(result.getParent())) {
            throw new InvalidPathException(name, "Must be a simple name");
        }
        return result;
    }

    private static boolean isEmptyPath(Path path) {
        if (path.isAbsolute()) {
            return false;
        }
        switch (path.getNameCount()) {
            case 0: {
                return true;
            }
            case 1: {
                return path.getName(0).toString().isEmpty();
            }
        }
        return false;
    }

    @CompilerDirectives.TruffleBoundary
    public <T> T getAttribute(AttributeDescriptor<T> attribute, LinkOption ... linkOptions) throws IOException {
        try {
            return this.getAttributeImpl(TruffleFile.createAttributeString(attribute.group, Collections.singleton(attribute.name)), attribute.clazz, linkOptions);
        }
        catch (IOException | SecurityException | UnsupportedOperationException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public <T> void setAttribute(AttributeDescriptor<T> attribute, T value2, LinkOption ... linkOptions) throws IOException {
        try {
            this.checkFileOperationPreconditions();
            this.fileSystemContext.fileSystem.setAttribute(this.normalizedPath, TruffleFile.createAttributeString(attribute.group, Collections.singleton(attribute.name)), value2, linkOptions);
        }
        catch (IOException | SecurityException | UnsupportedOperationException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public Attributes getAttributes(Collection<? extends AttributeDescriptor<?>> attributes, LinkOption ... linkOptions) throws IOException {
        HashSet useAttributes = new HashSet(attributes);
        if (useAttributes.isEmpty()) {
            throw new IllegalArgumentException("No descriptors given.");
        }
        try {
            this.checkFileOperationPreconditions();
            AttributeGroup group = null;
            ArrayList<String> attributeNames = new ArrayList<String>();
            for (AttributeDescriptor attributeDescriptor : useAttributes) {
                if (group == null || !group.contains(attributeDescriptor.group)) {
                    group = attributeDescriptor.group;
                }
                attributeNames.add(attributeDescriptor.name);
            }
            Map<String, Object> map = this.fileSystemContext.fileSystem.readAttributes(this.normalizedPath, TruffleFile.createAttributeString(group, attributeNames), linkOptions);
            return new Attributes(useAttributes, map);
        }
        catch (IOException | SecurityException | UnsupportedOperationException e) {
            throw e;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    private static String createAttributeString(AttributeGroup group, Iterable<String> attributeNames) {
        String joinedNames = String.join((CharSequence)",", attributeNames);
        return group == AttributeGroup.BASIC ? joinedNames : group.name + ":" + joinedNames;
    }

    private boolean isNormalized() {
        return this.path == this.normalizedPath || this.path.equals(this.normalizedPath);
    }

    private Path[] toAbsolutePathImpl() {
        Path absolute = this.fileSystemContext.fileSystem.toAbsolutePath(this.path);
        Path normalizedAbsolute = this.fileSystemContext.fileSystem.toAbsolutePath(this.normalizedPath).normalize();
        return new Path[]{absolute, normalizedAbsolute};
    }

    private boolean checkAccess(AccessMode ... modes) {
        EnumSet<AccessMode> modesSet = EnumSet.noneOf(AccessMode.class);
        Collections.addAll(modesSet, modes);
        return this.checkAccess(modesSet, new LinkOption[0]);
    }

    private boolean checkAccess(Set<? extends AccessMode> modes, LinkOption ... linkOptions) {
        try {
            this.checkFileOperationPreconditions();
            this.fileSystemContext.fileSystem.checkAccess(this.normalizedPath, modes, linkOptions);
            return true;
        }
        catch (IOException ioe) {
            return false;
        }
    }

    private <T> T getAttributeImpl(String attribute, Class<T> type, LinkOption ... options) throws IOException {
        return this.getAttributeImpl(this.normalizedPath, attribute, type, options);
    }

    private <T> T getAttributeImpl(Path forPath, String attribute, Class<T> type, LinkOption ... options) throws IOException {
        Object value2 = this.getAttributeImpl(forPath, attribute, options);
        return value2 == null ? null : (T)type.cast(value2);
    }

    private Object getAttributeImpl(Path forPath, String attribute, LinkOption ... options) throws IOException {
        this.checkFileOperationPreconditions();
        Map<String, Object> map = this.fileSystemContext.fileSystem.readAttributes(forPath, attribute, options);
        int index = attribute.indexOf(58);
        String key = index < 0 ? attribute : attribute.substring(index + 1);
        return map.get(key);
    }

    private Path createDirectoryImpl(Path dir, FileAttribute<?> ... attrs) throws IOException {
        this.fileSystemContext.fileSystem.createDirectory(dir, attrs);
        return dir;
    }

    private Path createDirAndCheck(Path dir, FileAttribute<?> ... attrs) throws IOException {
        try {
            return this.createDirectoryImpl(dir, attrs);
        }
        catch (FileAlreadyExistsException faee) {
            try {
                if (this.getAttributeImpl(dir, "isDirectory", Boolean.class, LinkOption.NOFOLLOW_LINKS).booleanValue()) {
                    return dir;
                }
                throw faee;
            }
            catch (IOException ioe) {
                throw faee;
            }
        }
    }

    private Path findExisting(Path forPath) throws IOException {
        EnumSet<AccessMode> mode = EnumSet.noneOf(AccessMode.class);
        for (Path p = forPath.getParent(); p != null; p = p.getParent()) {
            try {
                this.fileSystemContext.fileSystem.checkAccess(p, mode, new LinkOption[0]);
                return p;
            }
            catch (NoSuchFileException noSuchFileException) {
                continue;
            }
        }
        return null;
    }

    private <T extends Throwable> RuntimeException wrapHostException(T t) {
        throw TruffleFile.wrapHostException(t, this.fileSystemContext.fileSystem);
    }

    static <T extends Throwable> RuntimeException wrapHostException(T t, FileSystem fs) {
        if (LanguageAccessor.engineAccess().isInternal(fs)) {
            throw TruffleLanguage.Env.engineToLanguageException(t);
        }
        throw LanguageAccessor.engineAccess().wrapHostException(null, LanguageAccessor.engineAccess().getCurrentHostContext(), t);
    }

    private static final class Walker
    implements Iterable<Event> {
        private final TruffleFile start;
        private final int maxDepth;
        private final boolean followSymLinks;
        private IteratorImpl currentIterator;

        Walker(TruffleFile start2, int maxDepth, FileVisitOption ... options) {
            this.start = start2;
            this.maxDepth = maxDepth;
            boolean followSymLinksTmp = false;
            for (FileVisitOption option : options) {
                if (option != FileVisitOption.FOLLOW_LINKS) continue;
                followSymLinksTmp = true;
                break;
            }
            this.followSymLinks = followSymLinksTmp;
        }

        @Override
        public Iterator<Event> iterator() {
            if (this.currentIterator != null) {
                throw new IllegalStateException("Multiple iterators are not allowed.");
            }
            this.currentIterator = new IteratorImpl(this.start, this.maxDepth, this.followSymLinks);
            return this.currentIterator;
        }

        void pop() {
            if (!this.currentIterator.stack.isEmpty()) {
                try {
                    this.currentIterator.stack.removeLast().close();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
        }

        void skipRemainingSiblings() {
            if (!this.currentIterator.stack.isEmpty()) {
                this.currentIterator.stack.peekLast().setSkipped(true);
            }
        }

        private static class IteratorImpl
        implements Iterator<Event> {
            private final int maxDepth;
            private final LinkOption[] linkOptions;
            private final Deque<Dir> stack;
            private Event current;

            IteratorImpl(TruffleFile start2, int maxDepth, boolean followSymLinks) {
                LinkOption[] linkOptionArray;
                this.maxDepth = maxDepth;
                if (followSymLinks) {
                    linkOptionArray = new LinkOption[]{};
                } else {
                    LinkOption[] linkOptionArray2 = new LinkOption[1];
                    linkOptionArray = linkOptionArray2;
                    linkOptionArray2[0] = LinkOption.NOFOLLOW_LINKS;
                }
                this.linkOptions = linkOptionArray;
                this.stack = new ArrayDeque<Dir>();
                this.current = this.enter(start2);
            }

            @Override
            public boolean hasNext() {
                Dir top;
                if (this.current == null && (top = this.stack.peekLast()) != null) {
                    IOException ioe = null;
                    TruffleFile file = null;
                    if (!top.isSkipped()) {
                        try {
                            file = top.next();
                        }
                        catch (DirectoryIteratorException x) {
                            ioe = x.getCause();
                        }
                    }
                    if (file == null) {
                        try {
                            top.close();
                        }
                        catch (IOException e) {
                            if (ioe == null) {
                                ioe = e;
                            }
                            ioe.addSuppressed(e);
                        }
                        this.stack.removeLast();
                        this.current = new Event(Event.Type.POST_VISIT_DIRECTORY, top.directory, ioe);
                    } else {
                        this.current = this.enter(file);
                    }
                }
                return this.current != null;
            }

            @Override
            public Event next() {
                if (this.current == null) {
                    throw new NoSuchElementException();
                }
                Event res = this.current;
                this.current = null;
                return res;
            }

            private Event enter(TruffleFile file) {
                BasicFileAttributesImpl attrs;
                try {
                    attrs = new BasicFileAttributesImpl(file.fileSystemContext.fileSystem.readAttributes(file.normalizedPath, "*", this.linkOptions));
                }
                catch (IOException ioe) {
                    return new Event(Event.Type.VISIT, file, ioe);
                }
                int currentDepth = this.stack.size();
                if (currentDepth >= this.maxDepth || !attrs.isDirectory()) {
                    return new Event(Event.Type.VISIT, file, attrs);
                }
                DirectoryStream<TruffleFile> stream = null;
                try {
                    stream = file.newDirectoryStream();
                }
                catch (IOException ioe) {
                    return new Event(Event.Type.VISIT, file, ioe);
                }
                this.stack.addLast(new Dir(file, stream));
                return new Event(Event.Type.PRE_VISIT_DIRECTORY, file, attrs);
            }

            private static final class BasicFileAttributesImpl
            implements BasicFileAttributes {
                private Map<String, Object> attrsMap;

                BasicFileAttributesImpl(Map<String, Object> attrsMap) {
                    this.attrsMap = Objects.requireNonNull(attrsMap);
                }

                @Override
                public FileTime lastModifiedTime() {
                    return (FileTime)this.attrsMap.get("lastModifiedTime");
                }

                @Override
                public FileTime lastAccessTime() {
                    return (FileTime)this.attrsMap.get("lastAccessTime");
                }

                @Override
                public FileTime creationTime() {
                    return (FileTime)this.attrsMap.get("creationTime");
                }

                @Override
                public boolean isRegularFile() {
                    return (Boolean)this.attrsMap.get("isRegularFile");
                }

                @Override
                public boolean isDirectory() {
                    return (Boolean)this.attrsMap.get("isDirectory");
                }

                @Override
                public boolean isSymbolicLink() {
                    return (Boolean)this.attrsMap.get("isSymbolicLink");
                }

                @Override
                public boolean isOther() {
                    return (Boolean)this.attrsMap.get("isOther");
                }

                @Override
                public long size() {
                    return (Long)this.attrsMap.get("size");
                }

                @Override
                public Object fileKey() {
                    return this.attrsMap.get("fileKey");
                }
            }

            private static final class Dir
            implements Closeable {
                final TruffleFile directory;
                final DirectoryStream<TruffleFile> stream;
                private final Iterator<TruffleFile> iterator;
                private boolean skipped;

                Dir(TruffleFile directory, DirectoryStream<TruffleFile> stream) {
                    this.directory = directory;
                    this.stream = stream;
                    this.iterator = stream.iterator();
                }

                void setSkipped(boolean value2) {
                    this.skipped = value2;
                }

                boolean isSkipped() {
                    return this.skipped;
                }

                TruffleFile next() {
                    return this.iterator.hasNext() ? this.iterator.next() : null;
                }

                @Override
                public void close() throws IOException {
                    this.stream.close();
                }
            }
        }

        static class Event {
            final Type type;
            final TruffleFile file;
            final IOException ioe;
            final BasicFileAttributes attrs;

            Event(Type type, TruffleFile file, BasicFileAttributes attrs) {
                this.type = type;
                this.file = file;
                this.attrs = attrs;
                this.ioe = null;
            }

            Event(Type type, TruffleFile file, IOException ioe) {
                this.type = type;
                this.file = file;
                this.attrs = null;
                this.ioe = ioe;
            }

            static enum Type {
                PRE_VISIT_DIRECTORY,
                VISIT,
                POST_VISIT_DIRECTORY;

            }
        }
    }

    private static final class TruffleFileDirectoryStream
    implements DirectoryStream<TruffleFile> {
        private final TruffleFile directory;
        private final DirectoryStream<Path> delegate;

        TruffleFileDirectoryStream(TruffleFile directory, DirectoryStream<Path> delegate) {
            this.directory = directory;
            this.delegate = delegate;
        }

        @Override
        public Iterator<TruffleFile> iterator() {
            try {
                Iterator<Path> delegateIterator = this.delegate.iterator();
                boolean normalized = this.directory.isNormalized();
                return new IteratorImpl(this.directory, delegateIterator, normalized);
            }
            catch (Throwable t) {
                throw this.directory.wrapHostException(t);
            }
        }

        @Override
        public void close() throws IOException {
            try {
                this.delegate.close();
            }
            catch (IOException e) {
                throw e;
            }
            catch (Throwable t) {
                throw this.directory.wrapHostException(t);
            }
        }

        private static final class IteratorImpl
        implements Iterator<TruffleFile> {
            private final TruffleFile directory;
            private final Iterator<? extends Path> delegateIterator;
            private final boolean normalized;

            IteratorImpl(TruffleFile directory, Iterator<? extends Path> delegateIterator, boolean normalized) {
                this.directory = directory;
                this.delegateIterator = delegateIterator;
                this.normalized = normalized;
            }

            @Override
            public boolean hasNext() {
                try {
                    return this.delegateIterator.hasNext();
                }
                catch (Throwable t) {
                    throw this.directory.wrapHostException(t);
                }
            }

            @Override
            public TruffleFile next() {
                try {
                    Path path = this.delegateIterator.next();
                    return new TruffleFile(this.directory.fileSystemContext, this.normalized ? path : this.directory.path.resolve(path.getFileName()), this.normalized ? path : this.directory.normalizedPath.resolve(path.getFileName()), false);
                }
                catch (DirectoryIteratorException e) {
                    throw e;
                }
                catch (Throwable t) {
                    throw this.directory.wrapHostException(t);
                }
            }
        }
    }

    private static final class ByteChannelDecorator
    implements SeekableByteChannel {
        private final SeekableByteChannel delegate;

        ByteChannelDecorator(SeekableByteChannel delegate) {
            this.delegate = delegate;
        }

        @Override
        public int read(ByteBuffer dst) throws IOException {
            return this.delegate.read(dst);
        }

        @Override
        public int write(ByteBuffer src) throws IOException {
            return this.delegate.write(src);
        }

        @Override
        public boolean isOpen() {
            return this.delegate.isOpen();
        }

        @Override
        public void close() throws IOException {
            this.delegate.close();
        }

        @Override
        public long position() throws IOException {
            return this.delegate.position();
        }

        @Override
        public SeekableByteChannel position(long newPosition) throws IOException {
            this.delegate.position(newPosition);
            return this;
        }

        @Override
        public long size() throws IOException {
            return this.delegate.size();
        }

        @Override
        public SeekableByteChannel truncate(long size) throws IOException {
            this.delegate.truncate(size);
            return this;
        }

        static SeekableByteChannel create(SeekableByteChannel delegate) {
            Objects.requireNonNull(delegate, "Delegate must be non null.");
            return new ByteChannelDecorator(delegate);
        }
    }

    private static final class AllFiles
    implements DirectoryStream.Filter<Path> {
        static final DirectoryStream.Filter<Path> INSTANCE = new AllFiles();

        private AllFiles() {
        }

        @Override
        public boolean accept(Path entry) throws IOException {
            return true;
        }
    }

    static final class FileSystemContext {
        final Object engineObject;
        private volatile Map<String, Collection<? extends FileTypeDetector>> fileTypeDetectors;
        final FileSystem fileSystem;

        FileSystemContext(Object engineFileSystemContext, FileSystem fileSystem) {
            Objects.requireNonNull(engineFileSystemContext);
            Objects.requireNonNull(fileSystem);
            this.engineObject = engineFileSystemContext;
            this.fileSystem = fileSystem;
        }

        Iterable<? extends FileTypeDetector> getFileTypeDetectors(Set<String> mimeTypes) {
            Map<String, Collection<? extends FileTypeDetector>> result = this.fileTypeDetectors;
            if (result == null) {
                result = LanguageAccessor.engineAccess().getEngineFileTypeDetectors(this.engineObject);
                assert (result != null);
                this.fileTypeDetectors = result;
            }
            HashSet<? extends FileTypeDetector> filtered = new HashSet<FileTypeDetector>();
            for (Map.Entry<String, Collection<? extends FileTypeDetector>> e : result.entrySet()) {
                if (mimeTypes != null && !mimeTypes.contains(e.getKey())) continue;
                filtered.addAll(e.getValue());
            }
            return filtered;
        }
    }

    public static interface FileTypeDetector {
        public String findMimeType(TruffleFile var1) throws IOException;

        public Charset findEncoding(TruffleFile var1) throws IOException;
    }

    public static final class Attributes {
        private final Set<AttributeDescriptor<?>> queriedAttributes;
        private final Map<String, Object> delegate;

        Attributes(Set<AttributeDescriptor<?>> queriedAttributes, Map<String, Object> delegate) {
            assert (queriedAttributes != null);
            assert (delegate != null);
            this.queriedAttributes = queriedAttributes;
            this.delegate = delegate;
        }

        public <T> T get(AttributeDescriptor<T> descriptor) {
            Object value2 = this.delegate.get(descriptor.name);
            if (value2 != null) {
                return descriptor.clazz.cast(value2);
            }
            if (this.queriedAttributes.contains(descriptor)) {
                return null;
            }
            throw new IllegalArgumentException("The attribute: " + descriptor.toString() + " was not queried.");
        }
    }

    public static final class AttributeDescriptor<T> {
        final AttributeGroup group;
        final String name;
        final Class<T> clazz;

        AttributeDescriptor(AttributeGroup group, String name, Class<T> clazz) {
            this.group = group;
            this.name = name;
            this.clazz = clazz;
        }

        AttributeDescriptor(AttributeGroup group, Class<?> rawType, String name) {
            this.group = group;
            this.clazz = rawType;
            this.name = name;
        }

        public String toString() {
            return this.group + ":" + this.name;
        }
    }

    private static final class AttributeGroup {
        static final AttributeGroup BASIC = new AttributeGroup("basic", null);
        static final AttributeGroup POSIX = new AttributeGroup("posix", BASIC);
        static final AttributeGroup UNIX = new AttributeGroup("unix", POSIX);
        final String name;
        private final AttributeGroup parent;

        AttributeGroup(String name, AttributeGroup parent) {
            this.name = name;
            this.parent = parent;
        }

        boolean contains(AttributeGroup other) {
            if (this.name.equals(other.name)) {
                return true;
            }
            if (this.parent != null) {
                return this.parent.contains(other);
            }
            return false;
        }

        public String toString() {
            return this.name;
        }
    }

    private static final class TempFileRandomHolder {
        private static Random RANDOM;

        private TempFileRandomHolder() {
        }

        static Random getRandom() {
            if (RANDOM == null) {
                RANDOM = new Random();
            }
            return RANDOM;
        }
    }
}

