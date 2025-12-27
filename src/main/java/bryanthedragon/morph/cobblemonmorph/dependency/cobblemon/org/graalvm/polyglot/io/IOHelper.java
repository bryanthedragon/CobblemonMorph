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
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

final class IOHelper {
   static final AbstractPolyglotImpl IMPL = initImpl();

   private IOHelper() {
      throw new IllegalStateException("No instance allowed.");
   }

   static void copy(final Path source, final Path target, final FileSystem fileSystem, CopyOption... options) throws IOException {
      copy(source, target, fileSystem, fileSystem, options);
   }

   static void copy(final Path source, final Path target, final FileSystem sourceFileSystem, final FileSystem targetFileSystem, CopyOption... options) throws IOException {
      if (!source.equals(target)) {
         Path sourceReal = sourceFileSystem.toRealPath(source, LinkOption.NOFOLLOW_LINKS);

         Path targetReal;
         try {
            targetReal = targetFileSystem.toRealPath(target, LinkOption.NOFOLLOW_LINKS);
         } catch (NoSuchFileException var21) {
            targetReal = target;
         }

         if (!sourceReal.equals(targetReal)) {
            Set<LinkOption> linkOptions = new HashSet<>();
            Set<StandardCopyOption> copyOptions = EnumSet.noneOf(StandardCopyOption.class);

            for (CopyOption option : options) {
               if (option instanceof StandardCopyOption) {
                  copyOptions.add((StandardCopyOption)option);
               } else if (option instanceof LinkOption) {
                  linkOptions.add((LinkOption)option);
               }
            }

            if (copyOptions.contains(StandardCopyOption.ATOMIC_MOVE)) {
               throw new AtomicMoveNotSupportedException(source.toString(), target.toString(), "Atomic move not supported");
            } else {
               Map<String, Object> sourceAttributes = sourceFileSystem.readAttributes(
                  sourceReal,
                  "basic:isSymbolicLink,isDirectory,lastModifiedTime,lastAccessTime,creationTime",
                  linkOptions.toArray(new LinkOption[linkOptions.size()])
               );
               if ((Boolean)sourceAttributes.getOrDefault("isSymbolicLink", false)) {
                  throw new IOException("Copying of symbolic links is not supported.");
               } else {
                  if (copyOptions.contains(StandardCopyOption.REPLACE_EXISTING)) {
                     try {
                        targetFileSystem.delete(targetReal);
                     } catch (NoSuchFileException var20) {
                     }
                  } else {
                     boolean exists;
                     try {
                        targetFileSystem.checkAccess(targetReal, EnumSet.noneOf(AccessMode.class));
                        exists = true;
                     } catch (IOException var19) {
                        exists = false;
                     }

                     if (exists) {
                        throw new FileAlreadyExistsException(target.toString());
                     }
                  }

                  if ((Boolean)sourceAttributes.getOrDefault("isDirectory", false)) {
                     targetFileSystem.createDirectory(targetReal);
                  } else {
                     Set<StandardOpenOption> readOptions = EnumSet.of(StandardOpenOption.READ);
                     Set<StandardOpenOption> writeOptions = EnumSet.of(StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);

                     try (
                        SeekableByteChannel sourceChannel = sourceFileSystem.newByteChannel(sourceReal, readOptions);
                        SeekableByteChannel targetChannel = targetFileSystem.newByteChannel(targetReal, writeOptions);
                     ) {
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
                           Object value = sourceAttributes.get(key);
                           if (value != null) {
                              targetFileSystem.setAttribute(targetReal, key, value);
                           }
                        }
                     } catch (Throwable var22) {
                        try {
                           targetFileSystem.delete(targetReal);
                        } catch (Throwable var16) {
                           var22.addSuppressed(var16);
                        }

                        throw var22;
                     }
                  }
               }
            }
         }
      }
   }

   static void move(final Path source, final Path target, final FileSystem fileSystem, CopyOption... options) throws IOException {
      for (CopyOption option : options) {
         if (StandardCopyOption.ATOMIC_MOVE.equals(option)) {
            throw new AtomicMoveNotSupportedException(source.toString(), target.toString(), "Atomic move not supported");
         }
      }

      fileSystem.copy(source, target, options);
      fileSystem.delete(source);
   }

   static void move(final Path source, final Path target, final FileSystem sourceFileSystem, final FileSystem targetFileSystem, CopyOption... options) throws IOException {
      for (CopyOption option : options) {
         if (StandardCopyOption.ATOMIC_MOVE.equals(option)) {
            throw new AtomicMoveNotSupportedException(source.toString(), target.toString(), "Atomic move not supported");
         }
      }

      copy(source, target, sourceFileSystem, targetFileSystem, options);
      sourceFileSystem.delete(source);
   }

   private static AbstractPolyglotImpl initImpl() {
      try {
         Method method = Engine.class.getDeclaredMethod("getImpl");
         method.setAccessible(true);
         AbstractPolyglotImpl polyglotImpl = (AbstractPolyglotImpl)method.invoke(null);
         polyglotImpl.setIO(new IOHelper.IOAccessImpl());
         return polyglotImpl;
      } catch (Exception var2) {
         throw new IllegalStateException("Failed to initialize execution listener class.", var2);
      }
   }

   private static final class IOAccessImpl extends AbstractPolyglotImpl.IOAccess {
      @Override
      public ProcessHandler.ProcessCommand newProcessCommand(
         List<String> cmd,
         String cwd,
         Map<String, String> environment,
         boolean redirectErrorStream,
         ProcessHandler.Redirect inputRedirect,
         ProcessHandler.Redirect outputRedirect,
         ProcessHandler.Redirect errorRedirect
      ) {
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
