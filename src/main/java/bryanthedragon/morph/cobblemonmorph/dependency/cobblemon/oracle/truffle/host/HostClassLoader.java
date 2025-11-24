
package com.oracle.truffle.host;

import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.host.HostContext;
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
import java.nio.file.LinkOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.HashMap;
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

final class HostClassLoader
extends ClassLoader
implements Closeable {
    private final HostContext hostContext;
    private final ConcurrentMap<TruffleFile, Boolean> roots;
    private final Queue<Loader> loaders;
    private final Set<Closeable> toClose;
    private volatile boolean closed;

    HostClassLoader(HostContext context, ClassLoader parent) {
        super(parent);
        this.hostContext = context;
        this.roots = new ConcurrentHashMap<TruffleFile, Boolean>();
        this.loaders = new ConcurrentLinkedQueue<Loader>();
        this.toClose = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    @Override
    public void close() throws IOException {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkPermission(new RuntimePermission("closeClassLoader"));
        }
        if (!this.closed) {
            this.closed = true;
            ArrayList<IOException> exceptions = new ArrayList<IOException>();
            for (Closeable closeable : this.loaders) {
                try {
                    closeable.close();
                }
                catch (IOException ioe) {
                    exceptions.add(ioe);
                }
            }
            this.loaders.clear();
            this.roots.clear();
            Set<Closeable> set2 = this.toClose;
            synchronized (set2) {
                for (Closeable closeable : this.toClose) {
                    try {
                        closeable.close();
                    }
                    catch (IOException ioe) {
                        exceptions.add(ioe);
                    }
                }
                this.toClose.clear();
            }
            if (!exceptions.isEmpty()) {
                void var4_7;
                IOException first = (IOException)exceptions.get(0);
                boolean bl = true;
                while (var4_7 < exceptions.size()) {
                    first.addSuppressed((Throwable)exceptions.get((int)var4_7));
                    ++var4_7;
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
        }
        Resource res = this.findFirstResource(name);
        if (res == null) {
            return null;
        }
        try {
            in = res.getInputStream();
            this.toClose.add(in);
            return in;
        }
        catch (IOException ioe) {
            return null;
        }
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        this.hostContext.validateClass(className);
        String resourceName = HostClassLoader.getResourceName(className);
        Resource res = this.findFirstResource(resourceName);
        if (res == null) {
            return super.findClass(className);
        }
        try {
            byte[] content = res.getContent();
            this.definePackage(className);
            return this.defineClass(className, content, 0, content.length, res.getProtectionDomain());
        }
        catch (IOException ioe) {
            throw new ClassNotFoundException("Cannot load class: " + className, ioe);
        }
    }

    private void definePackage(String className) {
        String packageName = HostClassLoader.getPackageName(className);
        if (this.getPackage(packageName) == null) {
            this.definePackage(packageName, null, null, null, null, null, null, null);
        }
    }

    @Override
    protected URL findResource(String name) {
        Resource res = this.findFirstResource(name);
        return res == null ? null : res.getURL();
    }

    private Resource findFirstResource(String name) {
        for (Loader loader : this.getLoaders()) {
            Resource res = loader.findResource(name);
            if (res == null) continue;
            return res;
        }
        return null;
    }

    @Override
    protected Enumeration<URL> findResources(String name) throws IOException {
        ArrayList<URL> resources = new ArrayList<URL>();
        for (Loader loader : this.getLoaders()) {
            URL url;
            Resource res = loader.findResource(name);
            if (res == null || (url = res.getURL()) == null) continue;
            resources.add(url);
        }
        return Collections.enumeration(resources);
    }

    public void addClasspathRoot(TruffleFile file) {
        if (!this.closed && this.roots.putIfAbsent(file, Boolean.TRUE) == null) {
            this.loaders.add(file.isRegularFile(new LinkOption[0]) ? new JarLoader(file) : new FolderLoader(file));
        }
    }

    private Iterable<Loader> getLoaders() {
        return this.closed ? Collections.emptyList() : this.loaders;
    }

    private static String getResourceName(String className) {
        return className.replace('.', '/') + ".class";
    }

    private static String getPackageName(String className) {
        int lastDot = className.lastIndexOf(46);
        return lastDot == -1 ? "" : className.substring(0, lastDot);
    }

    private static final class JarLoader
    extends Loader {
        private volatile Map<String, Map<String, ZipUtils.Info>> content;

        JarLoader(TruffleFile root) {
            super(root);
        }

        @Override
        public Resource findResource(final String name) {
            String[] parts = JarLoader.split(name);
            try {
                Map<String, ZipUtils.Info> folderContent = this.getResourceMap().get(parts[0]);
                if (folderContent == null) {
                    return null;
                }
                final ZipUtils.Info info = folderContent.get(parts[1]);
                if (info == null) {
                    return null;
                }
                return new Resource(this.protectionDomain){

                    @Override
                    URL getURL() {
                        StringBuilder url = new StringBuilder("jar:");
                        url.append(root.toUri());
                        url.append("!/");
                        url.append(name);
                        try {
                            return new URL(null, url.toString(), new ResourceURLStreamHandler(this));
                        }
                        catch (MalformedURLException malformed) {
                            return null;
                        }
                    }

                    @Override
                    long getLength() throws IOException {
                        return info.size;
                    }

                    @Override
                    InputStream getInputStream() throws IOException {
                        return ZipUtils.getInputStream(this.getChannel(), info.offset);
                    }
                };
            }
            catch (IOException ioe) {
                return null;
            }
        }

        @Override
        public void close() throws IOException {
        }

        private SeekableByteChannel getChannel() throws IOException {
            return this.root.newByteChannel(EnumSet.of(StandardOpenOption.READ), new FileAttribute[0]);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Map<String, Map<String, ZipUtils.Info>> getResourceMap() throws IOException {
            Map<String, Map<String, ZipUtils.Info>> res = this.content;
            if (res == null) {
                JarLoader jarLoader = this;
                synchronized (jarLoader) {
                    res = this.content;
                    if (res == null) {
                        try (SeekableByteChannel channel = this.getChannel();){
                            res = ZipUtils.readEntries(channel);
                        }
                        this.content = res;
                    }
                }
            }
            return res;
        }

        private static String[] split(String resourceName) {
            int index = resourceName.lastIndexOf(47);
            if (index < 0) {
                return new String[]{"", resourceName};
            }
            return new String[]{resourceName.substring(0, index), resourceName.substring(index + 1, resourceName.length())};
        }

        static final class ZipUtils {
            private static final int LIMIT = 65536;
            private static final long UINT32_MAX_VALUE = 0xFFFFFFFFL;
            private static final int UINT16_MAX_VALUE = 65535;

            private ZipUtils() {
                throw new IllegalStateException("No instance allowed.");
            }

            static InputStream getInputStream(SeekableByteChannel channel, long offset) throws IOException {
                channel.position(offset);
                ZipInputStream in = new ZipInputStream(new ChannelInputStream(channel));
                ZipEntry e = in.getNextEntry();
                if (e != null && e.getCrc() == 0L && e.getMethod() == 0) {
                    in.close();
                    return new ChannelInputStream(channel, e.getSize());
                }
                return in;
            }

            static Map<String, Map<String, Info>> readEntries(SeekableByteChannel channel) throws IOException {
                long size = (int)channel.size();
                channel.position(size - 22L);
                ByteBuffer data = ByteBuffer.allocate(22);
                data.order(ByteOrder.LITTLE_ENDIAN);
                int giveup = 0;
                do {
                    data.clear();
                    if (ZipUtils.readFully(channel, data) != 22) {
                        throw new IOException();
                    }
                    channel.position(channel.position() - 23L);
                    if (++giveup <= 65536) continue;
                    throw new IOException();
                } while (ZipUtils.getsig(data) != 101010256L);
                long censize = ZipUtils.endsiz(data);
                long cenoff = ZipUtils.endoff(data);
                channel.position(cenoff);
                HashMap<String, Map<String, Info>> result = new HashMap<String, Map<String, Info>>();
                int cenread = 0;
                data = ByteBuffer.allocate(46);
                data.order(ByteOrder.LITTLE_ENDIAN);
                while ((long)cenread < censize) {
                    data.clear();
                    if (ZipUtils.readFully(channel, data) != 46) {
                        throw new IOException("No central table");
                    }
                    if (ZipUtils.getsig(data) != 33639248L) {
                        throw new IOException("No central table");
                    }
                    int cennam = ZipUtils.cennam(data);
                    int cenext = ZipUtils.cenext(data);
                    int cencom = ZipUtils.cencom(data);
                    long lhoff = ZipUtils.cenoff(data);
                    long cenlen = ZipUtils.cenlen(data);
                    String name = ZipUtils.name(channel, cennam);
                    int seekby = cenext + cencom;
                    int cendatalen = 46 + cennam + seekby;
                    cenread += cendatalen;
                    if (!ZipUtils.isDirectory(name)) {
                        String[] parts = JarLoader.split(name);
                        Map<String, Info> names = result.computeIfAbsent(parts[0], new Function<String, Map<String, Info>>(){

                            @Override
                            public Map<String, Info> apply(String t) {
                                return new HashMap<String, Info>();
                            }
                        });
                        names.put(parts[1], new Info(lhoff, cenlen));
                    }
                    ZipUtils.seekBy(channel, seekby);
                }
                return result;
            }

            private static String name(SeekableByteChannel channel, int cennam) throws IOException {
                ByteBuffer name = ByteBuffer.allocate(cennam);
                if (ZipUtils.readFully(channel, name) != cennam) {
                    throw new IOException("Unexpected EOF.");
                }
                return new String(name.array(), "UTF-8");
            }

            private static boolean isDirectory(String name) {
                return name.endsWith("/");
            }

            private static int readFully(SeekableByteChannel channel, ByteBuffer buffer) throws IOException {
                int read2;
                int res = 0;
                while (buffer.remaining() > 0 && (read2 = channel.read(buffer)) != -1) {
                    res += read2;
                }
                return res;
            }

            private static long getsig(ByteBuffer b) {
                return (long)b.getInt(0) & 0xFFFFFFFFL;
            }

            private static long endsiz(ByteBuffer b) {
                return (long)b.getInt(12) & 0xFFFFFFFFL;
            }

            private static long endoff(ByteBuffer b) {
                return (long)b.getInt(16) & 0xFFFFFFFFL;
            }

            private static long cenlen(ByteBuffer b) {
                return (long)b.getInt(24) & 0xFFFFFFFFL;
            }

            private static int cennam(ByteBuffer b) {
                return b.getShort(28) & 0xFFFF;
            }

            private static int cenext(ByteBuffer b) {
                return b.getShort(30) & 0xFFFF;
            }

            private static int cencom(ByteBuffer b) {
                return b.getShort(32) & 0xFFFF;
            }

            private static long cenoff(ByteBuffer b) {
                return (long)b.getInt(42) & 0xFFFFFFFFL;
            }

            private static void seekBy(SeekableByteChannel ch, int offset) throws IOException {
                ch.position(ch.position() + (long)offset);
            }

            static final class Info {
                final long offset;
                final long size;

                Info(long offset, long size) {
                    this.offset = offset;
                    this.size = size;
                }
            }

            private static class ChannelInputStream
            extends InputStream {
                private final SeekableByteChannel channel;
                private final long len;

                ChannelInputStream(SeekableByteChannel channel) throws IOException {
                    this.channel = channel;
                    this.len = channel.size();
                }

                ChannelInputStream(SeekableByteChannel channel, long len) throws IOException {
                    assert (channel != null);
                    assert (len >= 0L);
                    this.channel = channel;
                    this.len = channel.position() + len;
                }

                @Override
                public int read(byte[] data, int offset, int size) throws IOException {
                    int rem = this.available();
                    if (rem == 0) {
                        return -1;
                    }
                    int rlen = size < rem ? size : rem;
                    ByteBuffer buffer = ByteBuffer.wrap(data, offset, rlen);
                    return this.channel.read(buffer);
                }

                @Override
                public int read() throws IOException {
                    if (this.available() == 0) {
                        return -1;
                    }
                    ByteBuffer buffer = ByteBuffer.allocate(1);
                    this.channel.read(buffer);
                    buffer.flip();
                    return buffer.get();
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
        }
    }

    private static final class FolderLoader
    extends Loader {
        FolderLoader(TruffleFile root) {
            super(root);
        }

        @Override
        public Resource findResource(String name) {
            final TruffleFile file = this.root.resolve(name);
            if (!file.isRegularFile(new LinkOption[0])) {
                return null;
            }
            return new Resource(this.protectionDomain){

                @Override
                public URL getURL() {
                    try {
                        return new URL((URL)null, file.toUri().toString(), new ResourceURLStreamHandler(this));
                    }
                    catch (MalformedURLException malformed) {
                        return null;
                    }
                }

                @Override
                public long getLength() throws IOException {
                    return file.size(new LinkOption[0]);
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

    private static final class ResourceURLStreamHandler
    extends URLStreamHandler {
        private final Resource resource;

        ResourceURLStreamHandler(Resource resource) {
            this.resource = resource;
        }

        @Override
        protected URLConnection openConnection(URL u) {
            return new URLConnection(u){

                @Override
                public void connect() {
                }

                @Override
                public InputStream getInputStream() throws IOException {
                    return resource.getInputStream();
                }
            };
        }
    }

    private static abstract class Loader
    implements Closeable {
        final TruffleFile root;
        final ProtectionDomain protectionDomain;

        Loader(TruffleFile root) {
            URL rootURL;
            this.root = root;
            try {
                rootURL = root.toUri().toURL();
            }
            catch (MalformedURLException e) {
                rootURL = null;
            }
            this.protectionDomain = rootURL == null ? null : new ProtectionDomain(new CodeSource(rootURL, (CodeSigner[])null), null);
        }

        abstract Resource findResource(String var1);
    }

    private static abstract class Resource {
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
            byte[] res;
            int len;
            long lenl = this.getLength();
            if (lenl > Integer.MAX_VALUE) {
                throw new IOException("Invalid class file size.");
            }
            if (lenl == -1L) {
                len = Integer.MAX_VALUE;
                res = new byte[4096];
            } else {
                len = (int)lenl;
                res = new byte[len];
            }
            try (InputStream in = this.getInputStream();){
                int read2;
                for (int pos = 0; pos < len; pos += read2) {
                    int toRead;
                    if (pos == res.length) {
                        toRead = Math.min(len - pos, res.length + 1 << 12);
                        if (toRead > 0) {
                            res = Arrays.copyOf(res, pos + toRead);
                        }
                    } else {
                        toRead = res.length - pos;
                    }
                    if ((read2 = in.read(res, pos, toRead)) >= 0) continue;
                    if (len == Integer.MAX_VALUE) {
                        if (res.length == pos) break;
                        res = Arrays.copyOf(res, pos);
                        break;
                    }
                    throw new EOFException("Unexpected EOF");
                }
                byte[] byArray = res;
                return byArray;
            }
        }
    }
}

