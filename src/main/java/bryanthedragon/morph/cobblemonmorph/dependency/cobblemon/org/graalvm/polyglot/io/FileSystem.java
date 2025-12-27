package org.graalvm.polyglot.io;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.attribute.FileAttribute;
import java.util.Map;
import java.util.Set;

public interface FileSystem {
   Path parsePath(URI uri);

   Path parsePath(String path);

   void checkAccess(Path path, Set<? extends AccessMode> modes, LinkOption... linkOptions) throws IOException;

   void createDirectory(Path dir, FileAttribute<?>... attrs) throws IOException;

   void delete(Path path) throws IOException;

   SeekableByteChannel newByteChannel(Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs) throws IOException;

   DirectoryStream<Path> newDirectoryStream(Path dir, Filter<? super Path> filter) throws IOException;

   Path toAbsolutePath(Path path);

   Path toRealPath(Path path, LinkOption... linkOptions) throws IOException;

   Map<String, Object> readAttributes(Path path, String attributes, LinkOption... options) throws IOException;

   default void setAttribute(Path path, String attribute, Object value, LinkOption... options) throws IOException {
      throw new UnsupportedOperationException("Setting attributes is not supported");
   }

   default void copy(Path source, Path target, CopyOption... options) throws IOException {
      IOHelper.copy(source, target, this, options);
   }

   default void move(Path source, Path target, CopyOption... options) throws IOException {
      IOHelper.move(source, target, this, options);
   }

   default void createLink(Path link, Path existing) throws IOException {
      throw new UnsupportedOperationException("Links are not supported");
   }

   default void createSymbolicLink(Path link, Path target, FileAttribute<?>... attrs) throws IOException {
      throw new UnsupportedOperationException("Links are not supported");
   }

   default Path readSymbolicLink(Path link) throws IOException {
      throw new UnsupportedOperationException("Links are not supported");
   }

   default void setCurrentWorkingDirectory(Path currentWorkingDirectory) {
      throw new UnsupportedOperationException("Setting current working directory is not supported.");
   }

   default String getSeparator() {
      return this.parsePath("").getFileSystem().getSeparator();
   }

   default String getPathSeparator() {
      return File.pathSeparator;
   }

   default String getMimeType(Path path) {
      return null;
   }

   default Charset getEncoding(Path path) {
      return null;
   }

   default Path getTempDirectory() {
      throw new UnsupportedOperationException("Temporary directories not supported");
   }

   default boolean isSameFile(Path path1, Path path2, LinkOption... options) throws IOException {
      return this.toAbsolutePath(path1).equals(this.toAbsolutePath(path2)) ? true : this.toRealPath(path1, options).equals(this.toRealPath(path2, options));
   }

   static FileSystem newDefaultFileSystem() {
      return IOHelper.IMPL.newDefaultFileSystem();
   }

   static FileSystem allowLanguageHomeAccess(FileSystem fileSystem) {
      return IOHelper.IMPL.allowLanguageHomeAccess(fileSystem);
   }

   static FileSystem newReadOnlyFileSystem(FileSystem fileSystem) {
      return IOHelper.IMPL.newReadOnlyFileSystem(fileSystem);
   }
}
