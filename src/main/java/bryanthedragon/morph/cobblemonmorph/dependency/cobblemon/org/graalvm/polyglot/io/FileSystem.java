
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
import java.nio.file.attribute.FileAttribute;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.graalvm.polyglot.io.IOHelper;

public interface FileSystem {
    public Path parsePath(URI var1);

    public Path parsePath(String var1);

    public void checkAccess(Path var1, Set<? extends AccessMode> var2, LinkOption ... var3) throws IOException;

    public void createDirectory(Path var1, FileAttribute<?> ... var2) throws IOException;

    public void delete(Path var1) throws IOException;

    public SeekableByteChannel newByteChannel(Path var1, Set<? extends OpenOption> var2, FileAttribute<?> ... var3) throws IOException;

    public DirectoryStream<Path> newDirectoryStream(Path var1, DirectoryStream.Filter<? super Path> var2) throws IOException;

    public Path toAbsolutePath(Path var1);

    public Path toRealPath(Path var1, LinkOption ... var2) throws IOException;

    public Map<String, Object> readAttributes(Path var1, String var2, LinkOption ... var3) throws IOException;

    default public void setAttribute(Path path, String attribute, Object value2, LinkOption ... options) throws IOException {
        throw new UnsupportedOperationException("Setting attributes is not supported");
    }

    default public void copy(Path source, Path target, CopyOption ... options) throws IOException {
        IOHelper.copy(source, target, this, options);
    }

    default public void move(Path source, Path target, CopyOption ... options) throws IOException {
        IOHelper.move(source, target, this, options);
    }

    default public void createLink(Path link, Path existing) throws IOException {
        throw new UnsupportedOperationException("Links are not supported");
    }

    default public void createSymbolicLink(Path link, Path target, FileAttribute<?> ... attrs) throws IOException {
        throw new UnsupportedOperationException("Links are not supported");
    }

    default public Path readSymbolicLink(Path link) throws IOException {
        throw new UnsupportedOperationException("Links are not supported");
    }

    default public void setCurrentWorkingDirectory(Path currentWorkingDirectory) {
        throw new UnsupportedOperationException("Setting current working directory is not supported.");
    }

    default public String getSeparator() {
        return this.parsePath("").getFileSystem().getSeparator();
    }

    default public String getPathSeparator() {
        return File.pathSeparator;
    }

    default public String getMimeType(Path path) {
        Objects.requireNonNull(path);
        return null;
    }

    default public Charset getEncoding(Path path) {
        Objects.requireNonNull(path);
        return null;
    }

    default public Path getTempDirectory() {
        throw new UnsupportedOperationException("Temporary directories not supported");
    }

    default public boolean isSameFile(Path path1, Path path2, LinkOption ... options) throws IOException {
        if (this.toAbsolutePath(path1).equals(this.toAbsolutePath(path2))) {
            return true;
        }
        return this.toRealPath(path1, options).equals(this.toRealPath(path2, options));
    }

    public static FileSystem newDefaultFileSystem() {
        return IOHelper.IMPL.newDefaultFileSystem();
    }

    public static FileSystem allowLanguageHomeAccess(FileSystem fileSystem) {
        return IOHelper.IMPL.allowLanguageHomeAccess(fileSystem);
    }

    public static FileSystem newReadOnlyFileSystem(FileSystem fileSystem) {
        return IOHelper.IMPL.newReadOnlyFileSystem(fileSystem);
    }
}

