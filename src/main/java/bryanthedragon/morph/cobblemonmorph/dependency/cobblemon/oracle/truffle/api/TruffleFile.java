package com.oracle.truffle.api;

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
import java.nio.file.DirectoryStream.Filter;
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
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import org.graalvm.polyglot.io.FileSystem;

public final class TruffleFile {
   public static final TruffleFile.AttributeDescriptor<FileTime> LAST_MODIFIED_TIME = new TruffleFile.AttributeDescriptor<>(
      TruffleFile.AttributeGroup.BASIC, "lastModifiedTime", FileTime.class
   );
   public static final TruffleFile.AttributeDescriptor<FileTime> LAST_ACCESS_TIME = new TruffleFile.AttributeDescriptor<>(
      TruffleFile.AttributeGroup.BASIC, "lastAccessTime", FileTime.class
   );
   public static final TruffleFile.AttributeDescriptor<FileTime> CREATION_TIME = new TruffleFile.AttributeDescriptor<>(
      TruffleFile.AttributeGroup.BASIC, "creationTime", FileTime.class
   );
   public static final TruffleFile.AttributeDescriptor<Boolean> IS_REGULAR_FILE = new TruffleFile.AttributeDescriptor<>(
      TruffleFile.AttributeGroup.BASIC, "isRegularFile", Boolean.class
   );
   public static final TruffleFile.AttributeDescriptor<Boolean> IS_DIRECTORY = new TruffleFile.AttributeDescriptor<>(
      TruffleFile.AttributeGroup.BASIC, "isDirectory", Boolean.class
   );
   public static final TruffleFile.AttributeDescriptor<Boolean> IS_SYMBOLIC_LINK = new TruffleFile.AttributeDescriptor<>(
      TruffleFile.AttributeGroup.BASIC, "isSymbolicLink", Boolean.class
   );
   public static final TruffleFile.AttributeDescriptor<Boolean> IS_OTHER = new TruffleFile.AttributeDescriptor<>(
      TruffleFile.AttributeGroup.BASIC, "isOther", Boolean.class
   );
   public static final TruffleFile.AttributeDescriptor<Long> SIZE = new TruffleFile.AttributeDescriptor<>(TruffleFile.AttributeGroup.BASIC, "size", Long.class);
   public static final TruffleFile.AttributeDescriptor<UserPrincipal> UNIX_OWNER = new TruffleFile.AttributeDescriptor<>(
      TruffleFile.AttributeGroup.POSIX, "owner", UserPrincipal.class
   );
   public static final TruffleFile.AttributeDescriptor<GroupPrincipal> UNIX_GROUP = new TruffleFile.AttributeDescriptor<>(
      TruffleFile.AttributeGroup.POSIX, "group", GroupPrincipal.class
   );
   public static final TruffleFile.AttributeDescriptor<Set<PosixFilePermission>> UNIX_PERMISSIONS = new TruffleFile.AttributeDescriptor<>(
      TruffleFile.AttributeGroup.POSIX, Set.class, "permissions"
   );
   public static final TruffleFile.AttributeDescriptor<Integer> UNIX_MODE = new TruffleFile.AttributeDescriptor<>(
      TruffleFile.AttributeGroup.UNIX, "mode", Integer.class
   );
   public static final TruffleFile.AttributeDescriptor<Long> UNIX_INODE = new TruffleFile.AttributeDescriptor<>(
      TruffleFile.AttributeGroup.UNIX, "ino", Long.class
   );
   public static final TruffleFile.AttributeDescriptor<Long> UNIX_DEV = new TruffleFile.AttributeDescriptor<>(
      TruffleFile.AttributeGroup.UNIX, "dev", Long.class
   );
   public static final TruffleFile.AttributeDescriptor<Long> UNIX_RDEV = new TruffleFile.AttributeDescriptor<>(
      TruffleFile.AttributeGroup.UNIX, "rdev", Long.class
   );
   public static final TruffleFile.AttributeDescriptor<Integer> UNIX_NLINK = new TruffleFile.AttributeDescriptor<>(
      TruffleFile.AttributeGroup.UNIX, "nlink", Integer.class
   );
   public static final TruffleFile.AttributeDescriptor<Integer> UNIX_UID = new TruffleFile.AttributeDescriptor<>(
      TruffleFile.AttributeGroup.UNIX, "uid", Integer.class
   );
   public static final TruffleFile.AttributeDescriptor<Integer> UNIX_GID = new TruffleFile.AttributeDescriptor<>(
      TruffleFile.AttributeGroup.UNIX, "gid", Integer.class
   );
   public static final TruffleFile.AttributeDescriptor<FileTime> UNIX_CTIME = new TruffleFile.AttributeDescriptor<>(
      TruffleFile.AttributeGroup.UNIX, "ctime", FileTime.class
   );
   private static final int MAX_BUFFER_SIZE = 2147483639;
   private static final int BUFFER_SIZE = 8192;
   private final TruffleFile.FileSystemContext fileSystemContext;
   private final Path path;
   private final Path normalizedPath;
   private final boolean isEmptyPath;

   TruffleFile(final TruffleFile.FileSystemContext fileSystemContext, final Path path) {
      this(fileSystemContext, path, path.normalize(), isEmptyPath(path));
   }

   TruffleFile(final TruffleFile.FileSystemContext fileSystemContext, final Path path, final Path normalizedPath, boolean isEmptyPath) {
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

   TruffleFile.FileSystemContext getFileSystemContext() {
      return this.fileSystemContext;
   }

   FileSystem getSPIFileSystem() {
      return this.fileSystemContext.fileSystem;
   }

   @CompilerDirectives.TruffleBoundary
   public boolean exists(LinkOption... options) {
      try {
         return this.checkAccess(EnumSet.noneOf(AccessMode.class), options);
      } catch (SecurityException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw this.wrapHostException(var4);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public boolean isReadable() {
      try {
         return this.checkAccess(AccessMode.READ);
      } catch (SecurityException var2) {
         throw var2;
      } catch (Throwable var3) {
         throw this.wrapHostException(var3);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public boolean isWritable() {
      try {
         return this.checkAccess(AccessMode.WRITE);
      } catch (SecurityException var2) {
         throw var2;
      } catch (Throwable var3) {
         throw this.wrapHostException(var3);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public boolean isExecutable() {
      try {
         return this.checkAccess(AccessMode.EXECUTE);
      } catch (SecurityException var2) {
         throw var2;
      } catch (Throwable var3) {
         throw this.wrapHostException(var3);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public boolean isDirectory(LinkOption... options) {
      try {
         return this.getAttributeImpl("isDirectory", Boolean.class, options);
      } catch (IOException var3) {
         return false;
      } catch (SecurityException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw this.wrapHostException(var5);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public boolean isRegularFile(LinkOption... options) {
      try {
         return this.getAttributeImpl("isRegularFile", Boolean.class, options);
      } catch (IOException var3) {
         return false;
      } catch (SecurityException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw this.wrapHostException(var5);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public boolean isSymbolicLink() {
      try {
         return this.getAttributeImpl("isSymbolicLink", Boolean.class, LinkOption.NOFOLLOW_LINKS);
      } catch (IOException var2) {
         return false;
      } catch (SecurityException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw this.wrapHostException(var4);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public boolean isAbsolute() {
      try {
         return this.path.isAbsolute();
      } catch (Throwable var2) {
         throw this.wrapHostException(var2);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public String getName() {
      try {
         Path fileName = this.path.getFileName();
         return fileName == null ? null : fileName.toString();
      } catch (Throwable var2) {
         throw this.wrapHostException(var2);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public String getPath() {
      try {
         return this.path.toString();
      } catch (Throwable var2) {
         throw this.wrapHostException(var2);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public URI toUri() {
      try {
         Path absolutePath = this.path.isAbsolute() ? this.path : this.toAbsolutePathImpl()[0];
         return absolutePath.toUri();
      } catch (SecurityException var2) {
         throw var2;
      } catch (Throwable var3) {
         throw this.wrapHostException(var3);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public URI toRelativeUri() {
      if (this.isAbsolute()) {
         return this.toUri();
      } else {
         try {
            String strPath = "/".equals(this.fileSystemContext.fileSystem.getSeparator())
               ? this.path.toString()
               : this.path.toString().replace(this.fileSystemContext.fileSystem.getSeparator(), "/");
            return new URI(null, null, strPath, null);
         } catch (Throwable var2) {
            throw this.wrapHostException(var2);
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   public TruffleFile getAbsoluteFile() {
      if (this.path.isAbsolute()) {
         return this;
      } else {
         try {
            Path[] absolutePaths = this.toAbsolutePathImpl();
            return new TruffleFile(this.fileSystemContext, absolutePaths[0], absolutePaths[1], false);
         } catch (SecurityException var2) {
            throw var2;
         } catch (Throwable var3) {
            throw this.wrapHostException(var3);
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   public TruffleFile getCanonicalFile(LinkOption... options) throws IOException {
      try {
         Path realPath = this.fileSystemContext.fileSystem.toRealPath(this.normalizedPath, options);
         return new TruffleFile(this.fileSystemContext, realPath, realPath, false);
      } catch (SecurityException | IOException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw this.wrapHostException(var4);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public TruffleFile getParent() {
      try {
         Path parent = this.path.getParent();
         return parent == null ? null : new TruffleFile(this.fileSystemContext, parent);
      } catch (Throwable var2) {
         throw this.wrapHostException(var2);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public TruffleFile resolve(String name) {
      try {
         return new TruffleFile(this.fileSystemContext, this.path.resolve(name));
      } catch (InvalidPathException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw this.wrapHostException(var4);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public TruffleFile resolveSibling(String name) {
      try {
         return new TruffleFile(this.fileSystemContext, this.path.resolveSibling(name));
      } catch (InvalidPathException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw this.wrapHostException(var4);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public long size(LinkOption... options) throws IOException {
      try {
         return this.getAttributeImpl("size", Long.class, options);
      } catch (SecurityException | IOException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw this.wrapHostException(var4);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public FileTime getLastModifiedTime(LinkOption... options) throws IOException {
      try {
         return this.getAttributeImpl("lastModifiedTime", FileTime.class, options);
      } catch (SecurityException | IOException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw this.wrapHostException(var4);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public void setLastModifiedTime(FileTime time, LinkOption... options) throws IOException {
      try {
         this.checkFileOperationPreconditions();
         this.fileSystemContext.fileSystem.setAttribute(this.normalizedPath, "lastModifiedTime", time, options);
      } catch (SecurityException | IOException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw this.wrapHostException(var5);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public FileTime getLastAccessTime(LinkOption... options) throws IOException {
      try {
         return this.getAttributeImpl("lastAccessTime", FileTime.class, options);
      } catch (SecurityException | IOException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw this.wrapHostException(var4);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public void setLastAccessTime(FileTime time, LinkOption... options) throws IOException {
      try {
         this.checkFileOperationPreconditions();
         this.fileSystemContext.fileSystem.setAttribute(this.normalizedPath, "lastAccessTime", time, options);
      } catch (SecurityException | IOException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw this.wrapHostException(var5);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public FileTime getCreationTime(LinkOption... options) throws IOException {
      try {
         return this.getAttributeImpl("creationTime", FileTime.class, options);
      } catch (SecurityException | IOException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw this.wrapHostException(var4);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public void setCreationTime(FileTime time, LinkOption... options) throws IOException {
      try {
         this.checkFileOperationPreconditions();
         this.fileSystemContext.fileSystem.setAttribute(this.normalizedPath, "creationTime", time, options);
      } catch (SecurityException | IOException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw this.wrapHostException(var5);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public Collection<TruffleFile> list() throws IOException {
      try {
         this.checkFileOperationPreconditions();
         Collection<TruffleFile> result = new ArrayList<>();
         boolean normalized = this.isNormalized();

         try (DirectoryStream<Path> stream = this.fileSystemContext.fileSystem.newDirectoryStream(this.normalizedPath, TruffleFile.AllFiles.INSTANCE)) {
            for (Path p : stream) {
               result.add(
                  new TruffleFile(
                     this.fileSystemContext,
                     normalized ? p : this.path.resolve(p.getFileName()),
                     normalized ? p : this.normalizedPath.resolve(p.getFileName()),
                     false
                  )
               );
            }
         }

         return result;
      } catch (SecurityException | IOException var8) {
         throw var8;
      } catch (Throwable var9) {
         throw this.wrapHostException(var9);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public SeekableByteChannel newByteChannel(Set<? extends OpenOption> options, FileAttribute<?>... attributes) throws IOException {
      try {
         this.checkFileOperationPreconditions();
         return TruffleFile.ByteChannelDecorator.create(this.fileSystemContext.fileSystem.newByteChannel(this.normalizedPath, options, attributes));
      } catch (UnsupportedOperationException | IllegalArgumentException | SecurityException | IOException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw this.wrapHostException(var5);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public InputStream newInputStream(OpenOption... options) throws IOException {
      Set<OpenOption> openOptions = new HashSet<>();
      if (options.length > 0) {
         for (OpenOption option : options) {
            if (option == StandardOpenOption.APPEND || option == StandardOpenOption.WRITE) {
               throw new IllegalArgumentException(String.format("Option %s is not allowed.", option));
            }

            openOptions.add(option);
         }
      }

      return Channels.newInputStream(this.newByteChannel(openOptions));
   }

   @CompilerDirectives.TruffleBoundary
   public BufferedReader newBufferedReader(Charset charset) throws IOException {
      return new BufferedReader(new InputStreamReader(this.newInputStream(), charset));
   }

   @CompilerDirectives.TruffleBoundary
   public BufferedReader newBufferedReader() throws IOException {
      return this.newBufferedReader(StandardCharsets.UTF_8);
   }

   @CompilerDirectives.TruffleBoundary
   public byte[] readAllBytes() throws IOException {
      try (SeekableByteChannel channel = this.newByteChannel(Collections.emptySet())) {
         long sizel = channel.size();
         if (sizel > 2147483639L) {
            throw new OutOfMemoryError("File size is too large.");
         } else {
            try (InputStream in = Channels.newInputStream(channel)) {
               int size = (int)sizel;
               byte[] buf = new byte[size];
               int read = 0;

               while (true) {
                  int n;
                  while ((n = in.read(buf, read, size - read)) <= 0) {
                     if (n < 0 || (n = in.read()) < 0) {
                        return size == read ? buf : Arrays.copyOf(buf, read);
                     }

                     if (size << 1 <= 2147483639) {
                        size = Math.max(size << 1, 8192);
                     } else {
                        if (size == 2147483639) {
                           throw new OutOfMemoryError("Required array size too large");
                        }

                        size = 2147483639;
                     }

                     buf = Arrays.copyOf(buf, size);
                     buf[read++] = (byte)n;
                  }

                  read += n;
               }
            }
         }
      } catch (OutOfMemoryError | SecurityException | IOException var13) {
         throw var13;
      } catch (Throwable var14) {
         throw this.wrapHostException(var14);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public OutputStream newOutputStream(OpenOption... options) throws IOException {
      Set<OpenOption> openOptions = new HashSet<>(Math.max(options.length, 2) + 1);
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

      return Channels.newOutputStream(this.newByteChannel(openOptions));
   }

   @CompilerDirectives.TruffleBoundary
   public BufferedWriter newBufferedWriter(Charset charset, OpenOption... options) throws IOException {
      return new BufferedWriter(new OutputStreamWriter(this.newOutputStream(options), charset));
   }

   @CompilerDirectives.TruffleBoundary
   public BufferedWriter newBufferedWriter(OpenOption... options) throws IOException {
      return this.newBufferedWriter(StandardCharsets.UTF_8, options);
   }

   @CompilerDirectives.TruffleBoundary
   public void createFile(FileAttribute<?>... attributes) throws IOException {
      this.newByteChannel(EnumSet.of(StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW), attributes).close();
   }

   @CompilerDirectives.TruffleBoundary
   public void createDirectory(FileAttribute<?>... attributes) throws IOException {
      try {
         this.checkFileOperationPreconditions();
         this.createDirectoryImpl(this.normalizedPath, attributes);
      } catch (UnsupportedOperationException | SecurityException | IOException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw this.wrapHostException(var4);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public void createDirectories(FileAttribute<?>... attributes) throws IOException {
      try {
         this.checkFileOperationPreconditions();

         try {
            this.createDirAndCheck(this.normalizedPath, attributes);
         } catch (FileAlreadyExistsException var8) {
            throw var8;
         } catch (IOException var9) {
            SecurityException notAllowed = null;
            Path absolutePath = this.normalizedPath;

            try {
               absolutePath = this.fileSystemContext.fileSystem.toAbsolutePath(absolutePath);
            } catch (SecurityException var7) {
               notAllowed = var7;
            }

            Path lastExisting = this.findExisting(absolutePath);
            if (lastExisting == null) {
               if (notAllowed != null) {
                  throw notAllowed;
               } else {
                  throw new FileSystemException(this.path.toString(), null, "Cannot determine root");
               }
            } else {
               for (Path pathElement : lastExisting.relativize(absolutePath)) {
                  lastExisting = lastExisting.resolve(pathElement);
                  this.createDirAndCheck(lastExisting, attributes);
               }
            }
         }
      } catch (UnsupportedOperationException | SecurityException | IOException var10) {
         throw var10;
      } catch (Throwable var11) {
         throw this.wrapHostException(var11);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public void delete() throws IOException {
      try {
         this.checkFileOperationPreconditions();
         this.fileSystemContext.fileSystem.delete(this.normalizedPath);
      } catch (SecurityException | IOException var2) {
         throw var2;
      } catch (Throwable var3) {
         throw this.wrapHostException(var3);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public void move(TruffleFile target, CopyOption... options) throws IOException {
      try {
         this.checkFileOperationPreconditions();
         target.checkFileOperationPreconditions();
         this.fileSystemContext.fileSystem.move(this.normalizedPath, target.normalizedPath, options);
      } catch (UnsupportedOperationException | SecurityException | IOException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw this.wrapHostException(var5);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public Set<PosixFilePermission> getPosixPermissions(LinkOption... linkOptions) throws IOException {
      try {
         return (Set<PosixFilePermission>)this.getAttributeImpl(this.normalizedPath, "posix:permissions", linkOptions);
      } catch (SecurityException | UnsupportedOperationException | IOException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw this.wrapHostException(var4);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public void setPosixPermissions(Set<? extends PosixFilePermission> permissions, LinkOption... linkOptions) throws IOException {
      try {
         this.checkFileOperationPreconditions();
         this.fileSystemContext.fileSystem.setAttribute(this.normalizedPath, "posix:permissions", permissions, linkOptions);
      } catch (SecurityException | UnsupportedOperationException | IOException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw this.wrapHostException(var5);
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      return this.path.toString();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public int hashCode() {
      int res = 17;
      res = res * 31 + this.fileSystemContext.hashCode();
      return res * 31 + this.path.hashCode();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean equals(final Object other) {
      if (this == other) {
         return true;
      } else if (other != null && other.getClass() == TruffleFile.class) {
         TruffleFile otherFile = (TruffleFile)other;
         return this.path.equals(otherFile.path) && this.fileSystemContext.equals(otherFile.fileSystemContext);
      } else {
         return false;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public TruffleFile normalize() {
      if (this.isNormalized()) {
         return this;
      } else {
         Path newPath;
         if (!this.isEmptyPath && isEmptyPath(this.normalizedPath)) {
            newPath = this.fileSystemContext.fileSystem.parsePath(".");
         } else {
            newPath = this.normalizedPath;
         }

         return new TruffleFile(this.fileSystemContext, newPath, this.normalizedPath, this.isEmptyPath);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public TruffleFile relativize(TruffleFile other) {
      try {
         return new TruffleFile(this.fileSystemContext, this.path.relativize(other.path));
      } catch (IllegalArgumentException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw this.wrapHostException(var4);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public boolean startsWith(String other) {
      try {
         return this.path.startsWith(other);
      } catch (IllegalArgumentException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw this.wrapHostException(var4);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public boolean startsWith(TruffleFile other) {
      try {
         return this.path.startsWith(other.path);
      } catch (Throwable var3) {
         throw this.wrapHostException(var3);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public boolean endsWith(String other) {
      try {
         return this.path.endsWith(other);
      } catch (IllegalArgumentException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw this.wrapHostException(var4);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public boolean endsWith(TruffleFile other) {
      try {
         return this.path.endsWith(other.path);
      } catch (Throwable var3) {
         throw this.wrapHostException(var3);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public void createLink(TruffleFile target) throws IOException {
      try {
         this.checkFileOperationPreconditions();
         this.fileSystemContext.fileSystem.createLink(this.normalizedPath, target.normalizedPath);
      } catch (SecurityException | UnsupportedOperationException | IOException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw this.wrapHostException(var4);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public void createSymbolicLink(TruffleFile target, FileAttribute<?>... attrs) throws IOException {
      try {
         this.checkFileOperationPreconditions();
         this.fileSystemContext.fileSystem.createSymbolicLink(this.normalizedPath, target.path, attrs);
      } catch (SecurityException | UnsupportedOperationException | IOException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw this.wrapHostException(var5);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public TruffleFile readSymbolicLink() throws IOException {
      try {
         this.checkFileOperationPreconditions();
         return new TruffleFile(this.fileSystemContext, this.fileSystemContext.fileSystem.readSymbolicLink(this.normalizedPath));
      } catch (SecurityException | UnsupportedOperationException | IOException var2) {
         throw var2;
      } catch (Throwable var3) {
         throw this.wrapHostException(var3);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public UserPrincipal getOwner(LinkOption... options) throws IOException {
      try {
         return this.getAttributeImpl("posix:owner", UserPrincipal.class, options);
      } catch (SecurityException | UnsupportedOperationException | IOException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw this.wrapHostException(var4);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public GroupPrincipal getGroup(LinkOption... options) throws IOException {
      try {
         return this.getAttributeImpl("posix:group", GroupPrincipal.class, options);
      } catch (SecurityException | UnsupportedOperationException | IOException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw this.wrapHostException(var4);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public DirectoryStream<TruffleFile> newDirectoryStream() throws IOException {
      try {
         this.checkFileOperationPreconditions();
         return new TruffleFile.TruffleFileDirectoryStream(
            this, this.fileSystemContext.fileSystem.newDirectoryStream(this.normalizedPath, TruffleFile.AllFiles.INSTANCE)
         );
      } catch (SecurityException | IOException var2) {
         throw var2;
      } catch (Throwable var3) {
         throw this.wrapHostException(var3);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public void visit(FileVisitor<TruffleFile> visitor, int maxDepth, FileVisitOption... options) throws IOException {
      if (maxDepth < 0) {
         throw new IllegalArgumentException("The maxDepth must be >= 0");
      } else {
         try {
            this.checkFileOperationPreconditions();
            TruffleFile.Walker walker = new TruffleFile.Walker(this, maxDepth, options);

            for (TruffleFile.Walker.Event event : walker) {
               FileVisitResult result;
               switch (event.type) {
                  case PRE_VISIT_DIRECTORY:
                     result = visitor.preVisitDirectory(event.file, event.attrs);
                     if (result == FileVisitResult.SKIP_SUBTREE || result == FileVisitResult.SKIP_SIBLINGS) {
                        walker.pop();
                     }
                     break;
                  case VISIT:
                     IOException ioe = event.ioe;
                     if (ioe == null) {
                        result = visitor.visitFile(event.file, event.attrs);
                     } else {
                        result = visitor.visitFileFailed(event.file, ioe);
                     }
                     break;
                  case POST_VISIT_DIRECTORY:
                     result = visitor.postVisitDirectory(event.file, event.ioe);
                     break;
                  default:
                     throw new IllegalStateException("Unexpected event type: " + event.type);
               }

               if (Objects.requireNonNull(result) != FileVisitResult.CONTINUE) {
                  switch (result) {
                     case SKIP_SIBLINGS:
                        walker.skipRemainingSiblings();
                        break;
                     case TERMINATE:
                        return;
                  }
               }
            }
         } catch (SecurityException | IOException var9) {
            throw var9;
         } catch (Throwable var10) {
            throw this.wrapHostException(var10);
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   public void copy(TruffleFile target, CopyOption... options) throws IOException {
      try {
         this.checkFileOperationPreconditions();
         target.checkFileOperationPreconditions();
         this.fileSystemContext.fileSystem.copy(this.normalizedPath, target.normalizedPath, options);
      } catch (UnsupportedOperationException | SecurityException | IOException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw this.wrapHostException(var5);
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Deprecated(since = "20.2")
   public String getMimeType() throws IOException {
      return this.detectMimeType(null);
   }

   @CompilerDirectives.TruffleBoundary
   public String detectMimeType() {
      return this.detectMimeType(null);
   }

   @CompilerDirectives.TruffleBoundary
   public boolean isSameFile(TruffleFile other, LinkOption... options) throws IOException {
      try {
         this.checkFileOperationPreconditions();
         other.checkFileOperationPreconditions();
         if (this.equals(other)) {
            return true;
         } else {
            return !this.fileSystemContext.fileSystem.equals(other.fileSystemContext.fileSystem)
               ? false
               : this.fileSystemContext.fileSystem.isSameFile(this.normalizedPath, other.normalizedPath, options);
         }
      } catch (SecurityException | IOException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw this.wrapHostException(var5);
      }
   }

   @CompilerDirectives.TruffleBoundary
   String detectMimeType(Set<String> validMimeTypes) {
      try {
         if (validMimeTypes != null && validMimeTypes.isEmpty()) {
            return null;
         } else {
            this.checkFileOperationPreconditions();
            String result = this.fileSystemContext.fileSystem.getMimeType(this.normalizedPath);
            if (result == null || validMimeTypes != null && !validMimeTypes.contains(result)) {
               for (TruffleFile.FileTypeDetector detector : this.fileSystemContext.getFileTypeDetectors(validMimeTypes)) {
                  try {
                     result = detector.findMimeType(this);
                     if (result != null && (validMimeTypes == null || validMimeTypes.contains(result))) {
                        return result;
                     }
                  } catch (IOException var6) {
                  }
               }

               return null;
            } else {
               return result;
            }
         }
      } catch (IOException var7) {
         return null;
      } catch (SecurityException var8) {
         throw var8;
      } catch (Throwable var9) {
         throw this.wrapHostException(var9);
      }
   }

   Charset detectEncoding(String mimeType) {
      try {
         assert mimeType != null;

         this.checkFileOperationPreconditions();
         Charset result = this.fileSystemContext.fileSystem.getEncoding(this.normalizedPath);
         if (result != null) {
            return result;
         } else {
            for (TruffleFile.FileTypeDetector detector : this.fileSystemContext.getFileTypeDetectors(Collections.singleton(mimeType))) {
               try {
                  result = detector.findEncoding(this);
                  if (result != null) {
                     return result;
                  }
               } catch (IOException var6) {
               }
            }

            return null;
         }
      } catch (IOException var7) {
         return null;
      } catch (SecurityException | UnsupportedOperationException var8) {
         throw var8;
      } catch (Throwable var9) {
         throw this.wrapHostException(var9);
      }
   }

   static TruffleFile createTempFile(TruffleFile targetDirectory, String prefix, String suffix, boolean dir, FileAttribute<?>... attrs) throws IOException {
      Objects.requireNonNull(targetDirectory, "TargetDirectory must be non null.");
      targetDirectory.checkFileOperationPreconditions();
      String usePrefix = prefix != null ? prefix : "";
      String useSuffix = suffix != null ? suffix : (dir ? "" : ".tmp");

      while (true) {
         try {
            TruffleFile target = createUniquePath(targetDirectory, usePrefix, useSuffix);
            if (!target.exists()) {
               if (dir) {
                  target.createDirectory(attrs);
               } else {
                  target.createFile(attrs);
               }

               return target;
            }
         } catch (InvalidPathException var9) {
            throw new IllegalArgumentException("Prefix (" + usePrefix + ") or suffix (" + useSuffix + ") are not valid file name components");
         } catch (FileAlreadyExistsException var10) {
         }
      }
   }

   private void checkFileOperationPreconditions() throws IOException {
      if (this.isEmptyPath) {
         throw new NoSuchFileException("");
      }
   }

   private static TruffleFile createUniquePath(TruffleFile targetDirectory, String prefix, String suffix) {
      long n = TruffleFile.TempFileRandomHolder.getRandom().nextLong();
      n = n == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(n);
      String name = prefix + Long.toString(n) + suffix;
      TruffleFile result = targetDirectory.resolve(name);
      if (!targetDirectory.equals(result.getParent())) {
         throw new InvalidPathException(name, "Must be a simple name");
      } else {
         return result;
      }
   }

   private static boolean isEmptyPath(Path path) {
      if (path.isAbsolute()) {
         return false;
      } else {
         switch (path.getNameCount()) {
            case 0:
               return true;
            case 1:
               return path.getName(0).toString().isEmpty();
            default:
               return false;
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   public <T> T getAttribute(TruffleFile.AttributeDescriptor<T> attribute, LinkOption... linkOptions) throws IOException {
      try {
         return this.getAttributeImpl(createAttributeString(attribute.group, Collections.singleton(attribute.name)), attribute.clazz, linkOptions);
      } catch (UnsupportedOperationException | SecurityException | IOException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw this.wrapHostException(var5);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public <T> void setAttribute(TruffleFile.AttributeDescriptor<T> attribute, T value, LinkOption... linkOptions) throws IOException {
      try {
         this.checkFileOperationPreconditions();
         this.fileSystemContext
            .fileSystem
            .setAttribute(this.normalizedPath, createAttributeString(attribute.group, Collections.singleton(attribute.name)), value, linkOptions);
      } catch (UnsupportedOperationException | SecurityException | IOException var5) {
         throw var5;
      } catch (Throwable var6) {
         throw this.wrapHostException(var6);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public TruffleFile.Attributes getAttributes(Collection<? extends TruffleFile.AttributeDescriptor<?>> attributes, LinkOption... linkOptions) throws IOException {
      Set<TruffleFile.AttributeDescriptor<?>> useAttributes = new HashSet<>(attributes);
      if (useAttributes.isEmpty()) {
         throw new IllegalArgumentException("No descriptors given.");
      } else {
         try {
            this.checkFileOperationPreconditions();
            TruffleFile.AttributeGroup group = null;
            List<String> attributeNames = new ArrayList<>();

            for (TruffleFile.AttributeDescriptor<?> descriptor : useAttributes) {
               if (group == null || !group.contains(descriptor.group)) {
                  group = descriptor.group;
               }

               attributeNames.add(descriptor.name);
            }

            Map<String, Object> map = this.fileSystemContext
               .fileSystem
               .readAttributes(this.normalizedPath, createAttributeString(group, attributeNames), linkOptions);
            return new TruffleFile.Attributes(useAttributes, map);
         } catch (UnsupportedOperationException | SecurityException | IOException var8) {
            throw var8;
         } catch (Throwable var9) {
            throw this.wrapHostException(var9);
         }
      }
   }

   private static String createAttributeString(TruffleFile.AttributeGroup group, Iterable<String> attributeNames) {
      String joinedNames = String.join(",", attributeNames);
      return group == TruffleFile.AttributeGroup.BASIC ? joinedNames : group.name + ":" + joinedNames;
   }

   private boolean isNormalized() {
      return this.path == this.normalizedPath || this.path.equals(this.normalizedPath);
   }

   private Path[] toAbsolutePathImpl() {
      Path absolute = this.fileSystemContext.fileSystem.toAbsolutePath(this.path);
      Path normalizedAbsolute = this.fileSystemContext.fileSystem.toAbsolutePath(this.normalizedPath).normalize();
      return new Path[]{absolute, normalizedAbsolute};
   }

   private boolean checkAccess(AccessMode... modes) {
      Set<AccessMode> modesSet = EnumSet.noneOf(AccessMode.class);
      Collections.addAll(modesSet, modes);
      return this.checkAccess(modesSet);
   }

   private boolean checkAccess(Set<? extends AccessMode> modes, LinkOption... linkOptions) {
      try {
         this.checkFileOperationPreconditions();
         this.fileSystemContext.fileSystem.checkAccess(this.normalizedPath, modes, linkOptions);
         return true;
      } catch (IOException var4) {
         return false;
      }
   }

   private <T> T getAttributeImpl(String attribute, Class<T> type, LinkOption... options) throws IOException {
      return this.getAttributeImpl(this.normalizedPath, attribute, type, options);
   }

   private <T> T getAttributeImpl(Path forPath, String attribute, Class<T> type, LinkOption... options) throws IOException {
      Object value = this.getAttributeImpl(forPath, attribute, options);
      return value == null ? null : type.cast(value);
   }

   private Object getAttributeImpl(final Path forPath, final String attribute, final LinkOption... options) throws IOException {
      this.checkFileOperationPreconditions();
      Map<String, Object> map = this.fileSystemContext.fileSystem.readAttributes(forPath, attribute, options);
      int index = attribute.indexOf(58);
      String key = index < 0 ? attribute : attribute.substring(index + 1);
      return map.get(key);
   }

   private Path createDirectoryImpl(Path dir, FileAttribute<?>... attrs) throws IOException {
      this.fileSystemContext.fileSystem.createDirectory(dir, attrs);
      return dir;
   }

   private Path createDirAndCheck(Path dir, FileAttribute<?>... attrs) throws IOException {
      try {
         return this.createDirectoryImpl(dir, attrs);
      } catch (FileAlreadyExistsException var6) {
         FileAlreadyExistsException faee = var6;

         try {
            if (this.getAttributeImpl(dir, "isDirectory", Boolean.class, LinkOption.NOFOLLOW_LINKS)) {
               return dir;
            } else {
               throw faee;
            }
         } catch (IOException var5) {
            throw var6;
         }
      }
   }

   private Path findExisting(Path forPath) throws IOException {
      Set<AccessMode> mode = EnumSet.noneOf(AccessMode.class);

      for (Path p = forPath.getParent(); p != null; p = p.getParent()) {
         try {
            this.fileSystemContext.fileSystem.checkAccess(p, mode);
            return p;
         } catch (NoSuchFileException var5) {
         }
      }

      return null;
   }

   private <T extends Throwable> RuntimeException wrapHostException(T t) {
      throw wrapHostException(t, this.fileSystemContext.fileSystem);
   }

   static <T extends Throwable> RuntimeException wrapHostException(T t, FileSystem fs) {
      if (LanguageAccessor.engineAccess().isInternal(fs)) {
         throw TruffleLanguage.Env.engineToLanguageException(t);
      } else {
         throw LanguageAccessor.engineAccess().wrapHostException(null, LanguageAccessor.engineAccess().getCurrentHostContext(), t);
      }
   }

   private static final class AllFiles implements Filter<Path> {
      static final Filter<Path> INSTANCE = new TruffleFile.AllFiles();

      public boolean accept(Path entry) throws IOException {
         return true;
      }
   }

   public static final class AttributeDescriptor<T> {
      final TruffleFile.AttributeGroup group;
      final String name;
      final Class<T> clazz;

      AttributeDescriptor(TruffleFile.AttributeGroup group, String name, Class<T> clazz) {
         this.group = group;
         this.name = name;
         this.clazz = clazz;
      }

      AttributeDescriptor(TruffleFile.AttributeGroup group, Class<?> rawType, String name) {
         this.group = group;
         this.clazz = (Class<T>)rawType;
         this.name = name;
      }

      @Override
      public String toString() {
         return this.group + ":" + this.name;
      }
   }

   private static final class AttributeGroup {
      static final TruffleFile.AttributeGroup BASIC = new TruffleFile.AttributeGroup("basic", null);
      static final TruffleFile.AttributeGroup POSIX = new TruffleFile.AttributeGroup("posix", BASIC);
      static final TruffleFile.AttributeGroup UNIX = new TruffleFile.AttributeGroup("unix", POSIX);
      final String name;
      private final TruffleFile.AttributeGroup parent;

      AttributeGroup(String name, TruffleFile.AttributeGroup parent) {
         this.name = name;
         this.parent = parent;
      }

      boolean contains(TruffleFile.AttributeGroup other) {
         if (this.name.equals(other.name)) {
            return true;
         } else {
            return this.parent != null ? this.parent.contains(other) : false;
         }
      }

      @Override
      public String toString() {
         return this.name;
      }
   }

   public static final class Attributes {
      private final Set<TruffleFile.AttributeDescriptor<?>> queriedAttributes;
      private final Map<String, Object> delegate;

      Attributes(Set<TruffleFile.AttributeDescriptor<?>> queriedAttributes, Map<String, Object> delegate) {
         assert queriedAttributes != null;

         assert delegate != null;

         this.queriedAttributes = queriedAttributes;
         this.delegate = delegate;
      }

      public <T> T get(TruffleFile.AttributeDescriptor<T> descriptor) {
         Object value = this.delegate.get(descriptor.name);
         if (value != null) {
            return descriptor.clazz.cast(value);
         } else if (this.queriedAttributes.contains(descriptor)) {
            return null;
         } else {
            throw new IllegalArgumentException("The attribute: " + descriptor.toString() + " was not queried.");
         }
      }
   }

   private static final class ByteChannelDecorator implements SeekableByteChannel {
      private final SeekableByteChannel delegate;

      ByteChannelDecorator(final SeekableByteChannel delegate) {
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

      static SeekableByteChannel create(final SeekableByteChannel delegate) {
         Objects.requireNonNull(delegate, "Delegate must be non null.");
         return new TruffleFile.ByteChannelDecorator(delegate);
      }
   }

   static final class FileSystemContext {
      final Object engineObject;
      private volatile Map<String, Collection<? extends TruffleFile.FileTypeDetector>> fileTypeDetectors;
      final FileSystem fileSystem;

      FileSystemContext(Object engineFileSystemContext, FileSystem fileSystem) {
         Objects.requireNonNull(engineFileSystemContext);
         Objects.requireNonNull(fileSystem);
         this.engineObject = engineFileSystemContext;
         this.fileSystem = fileSystem;
      }

      Iterable<? extends TruffleFile.FileTypeDetector> getFileTypeDetectors(Set<String> mimeTypes) {
         Map<String, Collection<? extends TruffleFile.FileTypeDetector>> result = this.fileTypeDetectors;
         if (result == null) {
            result = LanguageAccessor.engineAccess().getEngineFileTypeDetectors(this.engineObject);

            assert result != null;

            this.fileTypeDetectors = result;
         }

         Set<TruffleFile.FileTypeDetector> filtered = new HashSet<>();

         for (Entry<String, Collection<? extends TruffleFile.FileTypeDetector>> e : result.entrySet()) {
            if (mimeTypes == null || mimeTypes.contains(e.getKey())) {
               filtered.addAll(e.getValue());
            }
         }

         return filtered;
      }
   }

   public interface FileTypeDetector {
      String findMimeType(TruffleFile file) throws IOException;

      Charset findEncoding(TruffleFile file) throws IOException;
   }

   private static final class TempFileRandomHolder {
      private static Random RANDOM;

      static Random getRandom() {
         if (RANDOM == null) {
            RANDOM = new Random();
         }

         return RANDOM;
      }
   }

   private static final class TruffleFileDirectoryStream implements DirectoryStream<TruffleFile> {
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
            return new TruffleFile.TruffleFileDirectoryStream.IteratorImpl(this.directory, delegateIterator, normalized);
         } catch (Throwable var3) {
            throw this.directory.wrapHostException(var3);
         }
      }

      @Override
      public void close() throws IOException {
         try {
            this.delegate.close();
         } catch (IOException var2) {
            throw var2;
         } catch (Throwable var3) {
            throw this.directory.wrapHostException(var3);
         }
      }

      private static final class IteratorImpl implements Iterator<TruffleFile> {
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
            } catch (Throwable var2) {
               throw this.directory.wrapHostException(var2);
            }
         }

         public TruffleFile next() {
            try {
               Path path = this.delegateIterator.next();
               return new TruffleFile(
                  this.directory.fileSystemContext,
                  this.normalized ? path : this.directory.path.resolve(path.getFileName()),
                  this.normalized ? path : this.directory.normalizedPath.resolve(path.getFileName()),
                  false
               );
            } catch (DirectoryIteratorException var2) {
               throw var2;
            } catch (Throwable var3) {
               throw this.directory.wrapHostException(var3);
            }
         }
      }
   }

   private static final class Walker implements Iterable<TruffleFile.Walker.Event> {
      private final TruffleFile start;
      private final int maxDepth;
      private final boolean followSymLinks;
      private TruffleFile.Walker.IteratorImpl currentIterator;

      Walker(TruffleFile start, int maxDepth, FileVisitOption... options) {
         this.start = start;
         this.maxDepth = maxDepth;
         boolean followSymLinksTmp = false;

         for (FileVisitOption option : options) {
            if (option == FileVisitOption.FOLLOW_LINKS) {
               followSymLinksTmp = true;
               break;
            }
         }

         this.followSymLinks = followSymLinksTmp;
      }

      @Override
      public Iterator<TruffleFile.Walker.Event> iterator() {
         if (this.currentIterator != null) {
            throw new IllegalStateException("Multiple iterators are not allowed.");
         } else {
            this.currentIterator = new TruffleFile.Walker.IteratorImpl(this.start, this.maxDepth, this.followSymLinks);
            return this.currentIterator;
         }
      }

      void pop() {
         if (!this.currentIterator.stack.isEmpty()) {
            try {
               this.currentIterator.stack.removeLast().close();
            } catch (IOException var2) {
            }
         }
      }

      void skipRemainingSiblings() {
         if (!this.currentIterator.stack.isEmpty()) {
            this.currentIterator.stack.peekLast().setSkipped(true);
         }
      }

      static class Event {
         final TruffleFile.Walker.Event.Type type;
         final TruffleFile file;
         final IOException ioe;
         final BasicFileAttributes attrs;

         Event(TruffleFile.Walker.Event.Type type, TruffleFile file, BasicFileAttributes attrs) {
            this.type = type;
            this.file = file;
            this.attrs = attrs;
            this.ioe = null;
         }

         Event(TruffleFile.Walker.Event.Type type, TruffleFile file, IOException ioe) {
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

      private static class IteratorImpl implements Iterator<TruffleFile.Walker.Event> {
         private final int maxDepth;
         private final LinkOption[] linkOptions;
         private final Deque<TruffleFile.Walker.IteratorImpl.Dir> stack;
         private TruffleFile.Walker.Event current;

         IteratorImpl(TruffleFile start, int maxDepth, boolean followSymLinks) {
            this.maxDepth = maxDepth;
            this.linkOptions = followSymLinks ? new LinkOption[0] : new LinkOption[]{LinkOption.NOFOLLOW_LINKS};
            this.stack = new ArrayDeque<>();
            this.current = this.enter(start);
         }

         @Override
         public boolean hasNext() {
            if (this.current == null) {
               TruffleFile.Walker.IteratorImpl.Dir top = this.stack.peekLast();
               if (top != null) {
                  IOException ioe = null;
                  TruffleFile file = null;
                  if (!top.isSkipped()) {
                     try {
                        file = top.next();
                     } catch (DirectoryIteratorException var5) {
                        ioe = var5.getCause();
                     }
                  }

                  if (file == null) {
                     try {
                        top.close();
                     } catch (IOException var6) {
                        if (ioe == null) {
                           ioe = var6;
                        } else {
                           ioe.addSuppressed(var6);
                        }
                     }

                     this.stack.removeLast();
                     this.current = new TruffleFile.Walker.Event(TruffleFile.Walker.Event.Type.POST_VISIT_DIRECTORY, top.directory, ioe);
                  } else {
                     this.current = this.enter(file);
                  }
               }
            }

            return this.current != null;
         }

         public TruffleFile.Walker.Event next() {
            if (this.current == null) {
               throw new NoSuchElementException();
            } else {
               TruffleFile.Walker.Event res = this.current;
               this.current = null;
               return res;
            }
         }

         private TruffleFile.Walker.Event enter(TruffleFile file) {
            BasicFileAttributes attrs;
            try {
               attrs = new TruffleFile.Walker.IteratorImpl.BasicFileAttributesImpl(
                  file.fileSystemContext.fileSystem.readAttributes(file.normalizedPath, "*", this.linkOptions)
               );
            } catch (IOException var7) {
               return new TruffleFile.Walker.Event(TruffleFile.Walker.Event.Type.VISIT, file, var7);
            }

            int currentDepth = this.stack.size();
            if (currentDepth < this.maxDepth && attrs.isDirectory()) {
               DirectoryStream<TruffleFile> stream = null;

               try {
                  stream = file.newDirectoryStream();
               } catch (IOException var6) {
                  return new TruffleFile.Walker.Event(TruffleFile.Walker.Event.Type.VISIT, file, var6);
               }

               this.stack.addLast(new TruffleFile.Walker.IteratorImpl.Dir(file, stream));
               return new TruffleFile.Walker.Event(TruffleFile.Walker.Event.Type.PRE_VISIT_DIRECTORY, file, attrs);
            } else {
               return new TruffleFile.Walker.Event(TruffleFile.Walker.Event.Type.VISIT, file, attrs);
            }
         }

         private static final class BasicFileAttributesImpl implements BasicFileAttributes {
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

         private static final class Dir implements Closeable {
            final TruffleFile directory;
            final DirectoryStream<TruffleFile> stream;
            private final Iterator<TruffleFile> iterator;
            private boolean skipped;

            Dir(TruffleFile directory, DirectoryStream<TruffleFile> stream) {
               this.directory = directory;
               this.stream = stream;
               this.iterator = stream.iterator();
            }

            void setSkipped(boolean value) {
               this.skipped = value;
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
   }
}
