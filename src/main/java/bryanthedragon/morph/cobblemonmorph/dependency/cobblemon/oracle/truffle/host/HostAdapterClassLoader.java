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
   static final ProtectionDomain GENERATED_PROTECTION_DOMAIN = createGeneratedProtectionDomain();
   static final Collection<String> VISIBLE_INTERNAL_CLASS_NAMES = Collections.unmodifiableCollection(new HashSet<>(Arrays.asList(Value.class.getName())));
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
      } catch (ClassNotFoundException var4) {
         throw new IllegalStateException(var4);
      }
   }

   private ClassLoader createClassLoader(final ClassLoader parentLoader, final Object classOverrides) {
      return new HostAdapterClassLoader.GeneratedClassLoader(parentLoader, classOverrides);
   }

   static boolean isAdapterInstance(Object adapter) {
      return isGeneratedClass(adapter.getClass());
   }

   static boolean isGeneratedClass(Class<?> clazz) {
      return isGeneratedClassLoader(clazz.getClassLoader());
   }

   static boolean isGeneratedClassLoader(ClassLoader classLoader) {
      return classLoader instanceof HostAdapterClassLoader.GeneratedClassLoader;
   }

   private static ProtectionDomain createGeneratedProtectionDomain() {
      Permissions permissions = new Permissions();
      permissions.add(new AllPermission());
      return new ProtectionDomain(new CodeSource(null, (CodeSigner[])null), permissions);
   }

   static Value getClassOverrides(ClassLoader classLoader) {
      return (Value)((Supplier)classLoader).get();
   }

   static byte[] loadClassBytes(String className) {
      String classFileName = "/" + className.replace('.', '/') + ".class";

      try {
         byte[] var6;
         try (
            InputStream in = Objects.requireNonNull(HostAdapterClassLoader.class.getResourceAsStream(classFileName), className);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
         ) {
            byte[] buf = new byte[4000];

            int n;
            while ((n = in.read(buf)) > 0) {
               out.write(buf, 0, n);
            }

            var6 = out.toByteArray();
         }

         return var6;
      } catch (IOException var11) {
         throw new IllegalStateException(var11);
      }
   }

   final class GeneratedClassLoader extends SecureClassLoader implements Supplier<Value> {
      private final ClassLoader internalLoader = HostAdapterClassLoader.GeneratedClassLoader.class.getClassLoader();
      private final Object classOverrides;

      private GeneratedClassLoader(final ClassLoader parentLoader, final Object classOverrides) {
         super(parentLoader);
         this.classOverrides = classOverrides;
      }

      @Override
      public Class<?> loadClass(final String name, final boolean resolve) throws ClassNotFoundException {
         if (this.isGeneratedClassName(name)) {
            return this.loadGeneratedClass(name, resolve);
         } else {
            return HostAdapterClassLoader.VISIBLE_INTERNAL_CLASS_NAMES.contains(name) ? this.loadInternalClass(name) : super.loadClass(name, resolve);
         }
      }

      private Class<?> loadGeneratedClass(final String name, final boolean resolve) throws ClassNotFoundException {
         synchronized (this.getClassLoadingLock(name)) {
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

      private Class<?> loadInternalClass(final String name) throws ClassNotFoundException {
         assert HostAdapterClassLoader.VISIBLE_INTERNAL_CLASS_NAMES.contains(name);

         return this.internalLoader != null ? this.internalLoader.loadClass(name) : Class.forName(name, false, this.internalLoader);
      }

      private boolean isGeneratedClassName(final String name) {
         return name.equals(HostAdapterClassLoader.this.className) || name.equals("com.oracle.truffle.host.adapters.HostAdapterServices");
      }

      @Override
      protected Class<?> findClass(final String name) throws ClassNotFoundException {
         if (name.equals(HostAdapterClassLoader.this.className)) {
            return this.defineClass(
               name,
               HostAdapterClassLoader.this.classBytes,
               0,
               HostAdapterClassLoader.this.classBytes.length,
               HostAdapterClassLoader.GENERATED_PROTECTION_DOMAIN
            );
         } else if (name.equals("com.oracle.truffle.host.adapters.HostAdapterServices")) {
            byte[] bytes = HostAdapterClassLoader.LazyClassBytes.SERVICE_CLASS_BYTES;
            return this.defineClass(name, bytes, 0, bytes.length, HostAdapterClassLoader.GENERATED_PROTECTION_DOMAIN);
         } else {
            throw new ClassNotFoundException(name);
         }
      }

      public Value get() {
         return Context.getCurrent().asValue(this.classOverrides);
      }
   }

   interface LazyClassBytes {
      byte[] SERVICE_CLASS_BYTES = HostAdapterClassLoader.loadClassBytes("com.oracle.truffle.host.adapters.HostAdapterServices");
   }
}
