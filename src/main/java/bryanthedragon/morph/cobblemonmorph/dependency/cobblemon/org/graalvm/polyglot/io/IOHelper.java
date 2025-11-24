
package org.graalvm.polyglot.io;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessMode;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.CopyOption;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;
import org.graalvm.polyglot.io.FileSystem;
import org.graalvm.polyglot.io.ProcessHandler;

final class IOHelper {
    static final AbstractPolyglotImpl IMPL = IOHelper.initImpl();

    private IOHelper() {
        throw new IllegalStateException("No instance allowed.");
    }

    static void copy(Path source, Path target, FileSystem fileSystem, CopyOption ... options) throws IOException {
        IOHelper.copy(source, target, fileSystem, fileSystem, options);
    }

    static void copy(Path source, Path target, FileSystem sourceFileSystem, FileSystem targetFileSystem, CopyOption ... options) throws IOException {
        Path targetReal;
        if (source.equals(target)) {
            return;
        }
        Path sourceReal = sourceFileSystem.toRealPath(source, LinkOption.NOFOLLOW_LINKS);
        try {
            targetReal = targetFileSystem.toRealPath(target, LinkOption.NOFOLLOW_LINKS);
        }
        catch (NoSuchFileException doesNotExist) {
            targetReal = target;
        }
        if (sourceReal.equals(targetReal)) {
            return;
        }
        HashSet<LinkOption> linkOptions = new HashSet<LinkOption>();
        EnumSet<StandardCopyOption> copyOptions = EnumSet.noneOf(StandardCopyOption.class);
        for (CopyOption option : options) {
            if (option instanceof StandardCopyOption) {
                copyOptions.add((StandardCopyOption)option);
                continue;
            }
            if (!(option instanceof LinkOption)) continue;
            linkOptions.add((LinkOption)option);
        }
        if (copyOptions.contains(StandardCopyOption.ATOMIC_MOVE)) {
            throw new AtomicMoveNotSupportedException(source.toString(), target.toString(), "Atomic move not supported");
        }
        Map<String, Object> sourceAttributes = sourceFileSystem.readAttributes(sourceReal, "basic:isSymbolicLink,isDirectory,lastModifiedTime,lastAccessTime,creationTime", linkOptions.toArray(new LinkOption[linkOptions.size()]));
        if (((Boolean)sourceAttributes.getOrDefault("isSymbolicLink", false)).booleanValue()) {
            throw new IOException("Copying of symbolic links is not supported.");
        }
        if (copyOptions.contains(StandardCopyOption.REPLACE_EXISTING)) {
            try {
                targetFileSystem.delete(targetReal);
            }
            catch (NoSuchFileException noSuchFileException) {}
        } else {
            boolean exists;
            try {
                targetFileSystem.checkAccess(targetReal, EnumSet.noneOf(AccessMode.class), new LinkOption[0]);
                exists = true;
            }
            catch (IOException ioe) {
                exists = false;
            }
            if (exists) {
                throw new FileAlreadyExistsException(target.toString());
            }
        }
        if (((Boolean)sourceAttributes.getOrDefault("isDirectory", false)).booleanValue()) {
            targetFileSystem.createDirectory(targetReal, new FileAttribute[0]);
        } else {
            EnumSet<StandardOpenOption> readOptions = EnumSet.of(StandardOpenOption.READ);
            EnumSet<StandardOpenOption> writeOptions = EnumSet.of(StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);
            try (SeekableByteChannel sourceChannel = sourceFileSystem.newByteChannel(sourceReal, readOptions, new FileAttribute[0]);
                 SeekableByteChannel targetChannel = targetFileSystem.newByteChannel(targetReal, writeOptions, new FileAttribute[0]);){
                ByteBuffer buffer = ByteBuffer.allocateDirect(65536);
                while (sourceChannel.read(buffer) != -1) {
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        targetChannel.write(buffer);
                    }
                    buffer.clear();
                }
            }
        }
        if (copyOptions.contains(StandardCopyOption.COPY_ATTRIBUTES)) {
            String[] basicMutableAttributes = new String[]{"lastModifiedTime", "lastAccessTime", "creationTime"};
            try {
                for (String key : basicMutableAttributes) {
                    Object value2 = sourceAttributes.get(key);
                    if (value2 == null) continue;
                    targetFileSystem.setAttribute(targetReal, key, value2, new LinkOption[0]);
                }
            }
            catch (Throwable rootCause) {
                try {
                    targetFileSystem.delete(targetReal);
                }
                catch (Throwable suppressed) {
                    rootCause.addSuppressed(suppressed);
                }
                throw rootCause;
            }
        }
    }

    static void move(Path source, Path target, FileSystem fileSystem, CopyOption ... options) throws IOException {
        for (CopyOption option : options) {
            if (!StandardCopyOption.ATOMIC_MOVE.equals(option)) continue;
            throw new AtomicMoveNotSupportedException(source.toString(), target.toString(), "Atomic move not supported");
        }
        fileSystem.copy(source, target, options);
        fileSystem.delete(source);
    }

    static void move(Path source, Path target, FileSystem sourceFileSystem, FileSystem targetFileSystem, CopyOption ... options) throws IOException {
        for (CopyOption option : options) {
            if (!StandardCopyOption.ATOMIC_MOVE.equals(option)) continue;
            throw new AtomicMoveNotSupportedException(source.toString(), target.toString(), "Atomic move not supported");
        }
        IOHelper.copy(source, target, sourceFileSystem, targetFileSystem, options);
        sourceFileSystem.delete(source);
    }

    private static AbstractPolyglotImpl initImpl() {
        try {
            Method method = Engine.class.getDeclaredMethod("getImpl", new Class[0]);
            method.setAccessible(true);
            AbstractPolyglotImpl polyglotImpl = (AbstractPolyglotImpl)method.invoke(null, new Object[0]);
            polyglotImpl.setIO(new IOAccessImpl());
            return polyglotImpl;
        }
        catch (Exception e) {
            throw new IllegalStateException("Failed to initialize execution listener class.", e);
        }
    }

    private static final class IOAccessImpl
    extends AbstractPolyglotImpl.IOAccess {
        private IOAccessImpl() {
        }

        @Override
        public ProcessHandler.ProcessCommand newProcessCommand(List<String> cmd, String cwd, Map<String, String> environment, boolean redirectErrorStream, ProcessHandler.Redirect inputRedirect, ProcessHandler.Redirect outputRedirect, ProcessHandler.Redirect errorRedirect) {
            return new ProcessHandler.ProcessCommand(cmd, cwd, environment, redirectErrorStream, inputRedirect, outputRedirect, errorRedirect);
        }

        @Override
        public ProcessHandler.Redirect createRedirectToStream(OutputStream stream) {
            Objects.requireNonNull("Stream must be non null.");
            return new ProcessHandler.Redirect(ProcessHandler.Redirect.Type.STREAM, stream);
        }

        @Override
        public OutputStream getOutputStream(ProcessHandler.Redirect redirect) {
            return redirect.getOutputStream();
        }
    }
}

