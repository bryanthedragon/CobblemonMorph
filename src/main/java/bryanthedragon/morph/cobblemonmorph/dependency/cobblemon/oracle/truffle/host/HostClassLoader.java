package com.oracle.truffle.host;

import com.oracle.truffle.api.TruffleFile;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.StandardOpenOption;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

final class HostClassLoader extends ClassLoader implements Closeable {
   private final HostContext hostContext;
   private final ConcurrentMap<TruffleFile, Boolean> roots;
   private final Queue<HostClassLoader.Loader> loaders;
   private final Set<Closeable> toClose;
   private volatile boolean closed;

   HostClassLoader(HostContext context, ClassLoader parent) {
      super(parent);
      this.hostContext = context;
      this.roots = new ConcurrentHashMap<>();
      this.loaders = new ConcurrentLinkedQueue<>();
      this.toClose = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap<>()));
   }

   @Override
   public void close() throws IOException {
      SecurityManager security = System.getSecurityManager();
      if (security != null) {
         security.checkPermission(new RuntimePermission("closeClassLoader"));
      }

      if (!this.closed) {
         this.closed = true;
         List<IOException> exceptions = new ArrayList<>();

         for (Closeable closeable : this.loaders) {
            try {
               closeable.close();
            } catch (IOException var9) {
               exceptions.add(var9);
            }
         }

         this.loaders.clear();
         this.roots.clear();
         synchronized (this.toClose) {
            for (Closeable closeable : this.toClose) {
               try {
                  closeable.close();
               } catch (IOException var8) {
                  exceptions.add(var8);
               }
            }

            this.toClose.clear();
         }

         if (!exceptions.isEmpty()) {
            IOException first = exceptions.get(0);

            for (int i = 1; i < exceptions.size(); i++) {
               first.addSuppressed(exceptions.get(i));
            }

            throw first;
         }
      }
   }

   @Override
   public InputStream getResourceAsStream(String name) {
      InputStream in = this.getParent().getResourceAsStream(name);
      if (in != null) {
         return in;
      } else {
         HostClassLoader.Resource res = this.findFirstResource(name);
         if (res == null) {
            return null;
         } else {
            try {
               in = res.getInputStream();
               this.toClose.add(in);
               return in;
            } catch (IOException var5) {
               return null;
            }
         }
      }
   }

   @Override
   protected Class<?> findClass(String className) throws ClassNotFoundException {
      this.hostContext.validateClass(className);
      String resourceName = getResourceName(className);
      HostClassLoader.Resource res = this.findFirstResource(resourceName);
      if (res == null) {
         return super.findClass(className);
      } else {
         try {
            byte[] content = res.getContent();
            this.definePackage(className);
            return this.defineClass(className, content, 0, content.length, res.getProtectionDomain());
         } catch (IOException var5) {
            throw new ClassNotFoundException("Cannot load class: " + className, var5);
         }
      }
   }

   private void definePackage(String className) {
      String packageName = getPackageName(className);
      if (this.getPackage(packageName) == null) {
         this.definePackage(packageName, null, null, null, null, null, null, null);
      }
   }

   @Override
   protected URL findResource(String name) {
      HostClassLoader.Resource res = this.findFirstResource(name);
      return res == null ? null : res.getURL();
   }

   private HostClassLoader.Resource findFirstResource(String name) {
      for (HostClassLoader.Loader loader : this.getLoaders()) {
         HostClassLoader.Resource res = loader.findResource(name);
         if (res != null) {
            return res;
         }
      }

      return null;
   }

   @Override
   protected Enumeration<URL> findResources(String name) throws IOException {
      List<URL> resources = new ArrayList<>();

      for (HostClassLoader.Loader loader : this.getLoaders()) {
         HostClassLoader.Resource res = loader.findResource(name);
         URL url;
         if (res != null && (url = res.getURL()) != null) {
            resources.add(url);
         }
      }

      return Collections.enumeration(resources);
   }

   public void addClasspathRoot(TruffleFile file) {
      if (!this.closed && this.roots.putIfAbsent(file, Boolean.TRUE) == null) {
         this.loaders.add((HostClassLoader.Loader)(file.isRegularFile() ? new HostClassLoader.JarLoader(file) : new HostClassLoader.FolderLoader(file)));
      }
   }

   private Iterable<HostClassLoader.Loader> getLoaders() {
      return (Iterable<HostClassLoader.Loader>)(this.closed ? Collections.emptyList() : this.loaders);
   }

   private static String getResourceName(String className) {
      return className.replace('.', '/') + ".class";
   }

   private static String getPackageName(String className) {
      int lastDot = className.lastIndexOf(46);
      return lastDot == -1 ? "" : className.substring(0, lastDot);
   }

   private static final class FolderLoader extends HostClassLoader.Loader {
      FolderLoader(TruffleFile root) {
         super(root);
      }

      @Override
      public HostClassLoader.Resource findResource(String name) {
         final TruffleFile file = this.root.resolve(name);
         return !file.isRegularFile() ? null : new HostClassLoader.Resource(this.protectionDomain) {
            @Override
            public URL getURL() {
               try {
                  return new URL((URL)null, file.toUri().toString(), new HostClassLoader.ResourceURLStreamHandler(this));
               } catch (MalformedURLException var2) {
                  return null;
               }
            }

            @Override
            public long getLength() throws IOException {
               return file.size();
            }

            @Override
            public InputStream getInputStream() throws IOException {
               return file.newInputStream(StandardOpenOption.READ);
            }

            @Override
            public byte[] getContent() throws IOException {
               return file.readAllBytes();
            }
         };
      }

      @Override
      public void close() throws IOException {
      }
   }

   private static final class JarLoader extends HostClassLoader.Loader {
      private volatile Map<String, Map<String, HostClassLoader.JarLoader.ZipUtils.Info>> content;

      JarLoader(TruffleFile root) {
         super(root);
      }

      @Override
      public HostClassLoader.Resource findResource(String name) {
         String[] parts = split(name);

         try {
            Map<String, HostClassLoader.JarLoader.ZipUtils.Info> folderContent = this.getResourceMap().get(parts[0]);
            if (folderContent == null) {
               return null;
            } else {
               final HostClassLoader.JarLoader.ZipUtils.Info info = folderContent.get(parts[1]);
               return info == null ? null : new HostClassLoader.Resource(this.protectionDomain) {
                  @Override
                  URL getURL() {
                     StringBuilder url = new StringBuilder("jar:");
                     url.append(JarLoader.this.root.toUri());
                     url.append("!/");
                     url.append(name);

                     try {
                        return new URL(null, url.toString(), new HostClassLoader.ResourceURLStreamHandler(this));
                     } catch (MalformedURLException var3) {
                        return null;
                     }
                  }

                  @Override
                  long getLength() throws IOException {
                     return info.size;
                  }

                  @Override
                  InputStream getInputStream() throws IOException {
                     return HostClassLoader.JarLoader.ZipUtils.getInputStream(JarLoader.this.getChannel(), info.offset);
                  }
               };
            }
         } catch (IOException var5) {
            return null;
         }
      }

      @Override
      public void close() throws IOException {
      }

      private SeekableByteChannel getChannel() throws IOException {
         return this.root.newByteChannel(EnumSet.of(StandardOpenOption.READ));
      }

      private Map<String, Map<String, HostClassLoader.JarLoader.ZipUtils.Info>> getResourceMap() throws IOException {
         Map<String, Map<String, HostClassLoader.JarLoader.ZipUtils.Info>> res = this.content;
         if (res == null) {
            synchronized (this) {
               res = this.content;
               if (res == null) {
                  try (SeekableByteChannel channel = this.getChannel()) {
                     res = HostClassLoader.JarLoader.ZipUtils.readEntries(channel);
                  }

                  this.content = res;
               }
            }
         }

         return res;
      }

      private static String[] split(String resourceName) {
         int index = resourceName.lastIndexOf(47);
         return index < 0
            ? new String[]{"", resourceName}
            : new String[]{resourceName.substring(0, index), resourceName.substring(index + 1, resourceName.length())};
      }

      static final class ZipUtils {
         private static final int LIMIT = 65536;
         private static final long UINT32_MAX_VALUE = 4294967295L;
         private static final int UINT16_MAX_VALUE = 65535;

         private ZipUtils() {
            throw new IllegalStateException("No instance allowed.");
         }

         static InputStream getInputStream(SeekableByteChannel channel, long offset) throws IOException {
            channel.position(offset);
            ZipInputStream in = new ZipInputStream(new HostClassLoader.JarLoader.ZipUtils.ChannelInputStream(channel));
            ZipEntry e = in.getNextEntry();
            if (e != null && e.getCrc() == 0L && e.getMethod() == 0) {
               in.close();
               return new HostClassLoader.JarLoader.ZipUtils.ChannelInputStream(channel, e.getSize());
            } else {
               return in;
            }
         }

         static Map<String, Map<String, HostClassLoader.JarLoader.ZipUtils.Info>> readEntries(SeekableByteChannel channel) throws IOException {
            long size = (int)channel.size();
            channel.position(size - 22L);
            ByteBuffer data = ByteBuffer.allocate(22);
            data.order(ByteOrder.LITTLE_ENDIAN);
            int giveup = 0;

            do {
               data.clear();
               if (readFully(channel, data) != 22) {
                  throw new IOException();
               }

               channel.position(channel.position() - 23L);
               if (++giveup > 65536) {
                  throw new IOException();
               }
            } while (getsig(data) != 101010256L);

            long censize = endsiz(data);
            long cenoff = endoff(data);
            channel.position(cenoff);
            Map<String, Map<String, HostClassLoader.JarLoader.ZipUtils.Info>> result = new HashMap<>();
            int cenread = 0;
            data = ByteBuffer.allocate(46);
            data.order(ByteOrder.LITTLE_ENDIAN);

            while (cenread < censize) {
               data.clear();
               if (readFully(channel, data) != 46) {
                  throw new IOException("No central table");
               }

               if (getsig(data) != 33639248L) {
                  throw new IOException("No central table");
               }

               int cennam = cennam(data);
               int cenext = cenext(data);
               int cencom = cencom(data);
               long lhoff = cenoff(data);
               long cenlen = cenlen(data);
               String name = name(channel, cennam);
               int seekby = cenext + cencom;
               int cendatalen = 46 + cennam + seekby;
               cenread += cendatalen;
               if (!isDirectory(name)) {
                  String[] parts = HostClassLoader.JarLoader.split(name);
                  Map<String, HostClassLoader.JarLoader.ZipUtils.Info> names = result.computeIfAbsent(
                     parts[0], new Function<String, Map<String, HostClassLoader.JarLoader.ZipUtils.Info>>() {
                        public Map<String, HostClassLoader.JarLoader.ZipUtils.Info> apply(String t) {
                           return new HashMap<>();
                        }
                     }
                  );
                  names.put(parts[1], new HostClassLoader.JarLoader.ZipUtils.Info(lhoff, cenlen));
               }

               seekBy(channel, seekby);
            }

            return result;
         }

         private static String name(SeekableByteChannel channel, int cennam) throws IOException {
            ByteBuffer name = ByteBuffer.allocate(cennam);
            if (readFully(channel, name) != cennam) {
               throw new IOException("Unexpected EOF.");
            } else {
               return new String(name.array(), "UTF-8");
            }
         }

         private static boolean isDirectory(String name) {
            return name.endsWith("/");
         }

         private static int readFully(SeekableByteChannel channel, ByteBuffer buffer) throws IOException {
            int res = 0;

            while (buffer.remaining() > 0) {
               int read = channel.read(buffer);
               if (read == -1) {
                  break;
               }

               res += read;
            }

            return res;
         }

         private static long getsig(ByteBuffer b) {
            return b.getInt(0) & 4294967295L;
         }

         private static long endsiz(ByteBuffer b) {
            return b.getInt(12) & 4294967295L;
         }

         private static long endoff(ByteBuffer b) {
            return b.getInt(16) & 4294967295L;
         }

         private static long cenlen(ByteBuffer b) {
            return b.getInt(24) & 4294967295L;
         }

         private static int cennam(ByteBuffer b) {
            return b.getShort(28) & 65535;
         }

         private static int cenext(ByteBuffer b) {
            return b.getShort(30) & 65535;
         }

         private static int cencom(ByteBuffer b) {
            return b.getShort(32) & 65535;
         }

         private static long cenoff(ByteBuffer b) {
            return b.getInt(42) & 4294967295L;
         }

         private static void seekBy(final SeekableByteChannel ch, int offset) throws IOException {
            ch.position(ch.position() + offset);
         }

         private static class ChannelInputStream extends InputStream {
            private final SeekableByteChannel channel;
            private final long len;

            ChannelInputStream(SeekableByteChannel channel) throws IOException {
               this.channel = channel;
               this.len = channel.size();
            }

            ChannelInputStream(SeekableByteChannel channel, long len) throws IOException {
               assert channel != null;

               assert len >= 0L;

               this.channel = channel;
               this.len = channel.position() + len;
            }

            @Override
            public int read(byte[] data, int offset, int size) throws IOException {
               int rem = this.available();
               if (rem == 0) {
                  return -1;
               } else {
                  int rlen = size < rem ? size : rem;
                  ByteBuffer buffer = ByteBuffer.wrap(data, offset, rlen);
                  return this.channel.read(buffer);
               }
            }

            @Override
            public int read() throws IOException {
               if (this.available() == 0) {
                  return -1;
               } else {
                  ByteBuffer buffer = ByteBuffer.allocate(1);
                  this.channel.read(buffer);
                  buffer.flip();
                  return buffer.get();
               }
            }

            @Override
            public int available() throws IOException {
               return (int)(this.len - this.channel.position());
            }

            @Override
            public void close() throws IOException {
               this.channel.close();
            }
         }

         static final class Info {
            final long offset;
            final long size;

            Info(long offset, long size) {
               this.offset = offset;
               this.size = size;
            }
         }
      }
   }

   private abstract static class Loader implements Closeable {
      final TruffleFile root;
      final ProtectionDomain protectionDomain;

      Loader(TruffleFile root) {
         this.root = root;

         URL rootURL;
         try {
            rootURL = root.toUri().toURL();
         } catch (MalformedURLException var4) {
            rootURL = null;
         }

         this.protectionDomain = rootURL == null ? null : new ProtectionDomain(new CodeSource(rootURL, (CodeSigner[])null), null);
      }

      abstract HostClassLoader.Resource findResource(String name);
   }

   private abstract static class Resource {
      private final ProtectionDomain protectionDomain;

      Resource(ProtectionDomain protectionDomain) {
         this.protectionDomain = protectionDomain;
      }

      abstract URL getURL();

      abstract long getLength() throws IOException;

      abstract InputStream getInputStream() throws IOException;

      final ProtectionDomain getProtectionDomain() {
         return this.protectionDomain;
      }

      byte[] getContent() throws IOException {
         long lenl = this.getLength();
         if (lenl > 2147483647L) {
            throw new IOException("Invalid class file size.");
         } else {
            byte[] res;
            int len;
            if (lenl == -1L) {
               len = Integer.MAX_VALUE;
               res = new byte[4096];
            } else {
               len = (int)lenl;
               res = new byte[len];
            }

            byte[] var11;
            try (InputStream in = this.getInputStream()) {
               int pos = 0;

               while (pos < len) {
                  int toRead;
                  if (pos == res.length) {
                     toRead = Math.min(len - pos, res.length + 1 << 12);
                     if (toRead > 0) {
                        res = Arrays.copyOf(res, pos + toRead);
                     }
                  } else {
                     toRead = res.length - pos;
                  }

                  int read = in.read(res, pos, toRead);
                  if (read < 0) {
                     if (len != Integer.MAX_VALUE) {
                        throw new EOFException("Unexpected EOF");
                     }

                     if (res.length != pos) {
                        res = Arrays.copyOf(res, pos);
                     }
                     break;
                  }

                  pos += read;
               }

               var11 = res;
            }

            return var11;
         }
      }
   }

   private static final class ResourceURLStreamHandler extends URLStreamHandler {
      private final HostClassLoader.Resource resource;

      ResourceURLStreamHandler(HostClassLoader.Resource resource) {
         this.resource = resource;
      }

      @Override
      protected URLConnection openConnection(URL u) {
         return new URLConnection(u) {
            @Override
            public void connect() {
            }

            @Override
            public InputStream getInputStream() throws IOException {
               return ResourceURLStreamHandler.this.resource.getInputStream();
            }
         };
      }
   }
}
