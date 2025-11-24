
package com.oracle.truffle.host;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.AllPermission;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.ProtectionDomain;
import java.security.SecureClassLoader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.Supplier;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

final class HostAdapterClassLoader {
    static final ProtectionDomain GENERATED_PROTECTION_DOMAIN = HostAdapterClassLoader.createGeneratedProtectionDomain();
    static final Collection<String> VISIBLE_INTERNAL_CLASS_NAMES = Collections.unmodifiableCollection(new HashSet<String>(Arrays.asList(Value.class.getName())));
    static final String SERVICE_CLASS_NAME = "com.oracle.truffle.host.adapters.HostAdapterServices";
    private final String className;
    private final byte[] classBytes;

    HostAdapterClassLoader(String className, byte[] classBytes) {
        this.className = className.replace('/', '.');
        this.classBytes = classBytes;
    }

    Class<?> generateClass(ClassLoader parentLoader, Object classOverrides) {
        try {
            return Class.forName(this.className, true, this.createClassLoader(parentLoader, classOverrides));
        }
        catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    private ClassLoader createClassLoader(ClassLoader parentLoader, Object classOverrides) {
        return new GeneratedClassLoader(parentLoader, classOverrides);
    }

    static boolean isAdapterInstance(Object adapter2) {
        return HostAdapterClassLoader.isGeneratedClass(adapter2.getClass());
    }

    static boolean isGeneratedClass(Class<?> clazz) {
        return HostAdapterClassLoader.isGeneratedClassLoader(clazz.getClassLoader());
    }

    static boolean isGeneratedClassLoader(ClassLoader classLoader) {
        return classLoader instanceof GeneratedClassLoader;
    }

    private static ProtectionDomain createGeneratedProtectionDomain() {
        Permissions permissions = new Permissions();
        permissions.add(new AllPermission());
        return new ProtectionDomain(new CodeSource(null, (CodeSigner[])null), permissions);
    }

    static Value getClassOverrides(ClassLoader classLoader) {
        return (Value)((Supplier)((Object)classLoader)).get();
    }

    /*
     * Enabled aggressive exception aggregation
     */
    static byte[] loadClassBytes(String className) {
        String classFileName = "/" + className.replace('.', '/') + ".class";
        try (InputStream in = Objects.requireNonNull(HostAdapterClassLoader.class.getResourceAsStream(classFileName), className);){
            byte[] byArray;
            try (ByteArrayOutputStream out = new ByteArrayOutputStream();){
                int n;
                byte[] buf = new byte[4000];
                while ((n = in.read(buf)) > 0) {
                    out.write(buf, 0, n);
                }
                byArray = out.toByteArray();
            }
            return byArray;
        }
        catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    final class GeneratedClassLoader
    extends SecureClassLoader
    implements Supplier<Value> {
        private final ClassLoader internalLoader;
        private final Object classOverrides;

        private GeneratedClassLoader(ClassLoader parentLoader, Object classOverrides) {
            super(parentLoader);
            this.internalLoader = GeneratedClassLoader.class.getClassLoader();
            this.classOverrides = classOverrides;
        }

        @Override
        public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            if (this.isGeneratedClassName(name)) {
                return this.loadGeneratedClass(name, resolve);
            }
            if (VISIBLE_INTERNAL_CLASS_NAMES.contains(name)) {
                return this.loadInternalClass(name);
            }
            return super.loadClass(name, resolve);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Class<?> loadGeneratedClass(String name, boolean resolve) throws ClassNotFoundException {
            Object object = this.getClassLoadingLock(name);
            synchronized (object) {
                Class<?> c = this.findLoadedClass(name);
                if (c == null) {
                    c = this.findClass(name);
                }
                if (resolve) {
                    this.resolveClass(c);
                }
                return c;
            }
        }

        private Class<?> loadInternalClass(String name) throws ClassNotFoundException {
            assert (VISIBLE_INTERNAL_CLASS_NAMES.contains(name));
            return this.internalLoader != null ? this.internalLoader.loadClass(name) : Class.forName(name, false, this.internalLoader);
        }

        private boolean isGeneratedClassName(String name) {
            return name.equals(HostAdapterClassLoader.this.className) || name.equals(HostAdapterClassLoader.SERVICE_CLASS_NAME);
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            if (name.equals(HostAdapterClassLoader.this.className)) {
                return this.defineClass(name, HostAdapterClassLoader.this.classBytes, 0, HostAdapterClassLoader.this.classBytes.length, GENERATED_PROTECTION_DOMAIN);
            }
            if (name.equals(HostAdapterClassLoader.SERVICE_CLASS_NAME)) {
                byte[] bytes = LazyClassBytes.SERVICE_CLASS_BYTES;
                return this.defineClass(name, bytes, 0, bytes.length, GENERATED_PROTECTION_DOMAIN);
            }
            throw new ClassNotFoundException(name);
        }

        @Override
        public Value get() {
            return Context.getCurrent().asValue(this.classOverrides);
        }
    }

    static interface LazyClassBytes {
        public static final byte[] SERVICE_CLASS_BYTES = HostAdapterClassLoader.loadClassBytes("com.oracle.truffle.host.adapters.HostAdapterServices");
    }
}

