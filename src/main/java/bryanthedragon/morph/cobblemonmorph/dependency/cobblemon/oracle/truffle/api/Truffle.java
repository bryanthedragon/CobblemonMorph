package com.oracle.truffle.api;

import com.oracle.truffle.api.impl.DefaultTruffleRuntime;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public final class Truffle {
   private static final TruffleRuntime RUNTIME = initRuntime();

   private Truffle() {
   }

   public static TruffleRuntime getRuntime() {
      return RUNTIME;
   }

   private static TruffleRuntimeAccess selectTruffleRuntimeAccess(List<Iterable<TruffleRuntimeAccess>> lookups) {
      TruffleRuntimeAccess selectedAccess = null;

      for (Iterable<TruffleRuntimeAccess> lookup : lookups) {
         if (lookup != null) {
            for (TruffleRuntimeAccess access : lookup) {
               try {
                  ;
               } catch (ServiceConfigurationError var7) {
                  continue;
               }

               if (selectedAccess == null) {
                  selectedAccess = access;
               } else if (selectedAccess != access && selectedAccess.getClass() != access.getClass()) {
                  if (selectedAccess.getPriority() == access.getPriority()) {
                     throw new InternalError(
                        String.format(
                           "Providers for %s with same priority %d: %s (loader: %s) vs. %s (loader: %s)",
                           TruffleRuntimeAccess.class.getName(),
                           access.getPriority(),
                           selectedAccess,
                           selectedAccess.getClass().getClassLoader(),
                           access,
                           access.getClass().getClassLoader()
                        )
                     );
                  }

                  if (selectedAccess.getPriority() < access.getPriority()) {
                     selectedAccess = access;
                  }
               }
            }
         }
      }

      return selectedAccess;
   }

   private static TruffleRuntime initRuntime() {
      return AccessController.doPrivileged(new PrivilegedAction<TruffleRuntime>() {
         public TruffleRuntime run() {
            String runtimeClassName = System.getProperty("truffle.TruffleRuntime");
            if (runtimeClassName == null || runtimeClassName.isEmpty()) {
               Class<?> lookupClass = Truffle.class;
               ModuleLayer moduleLayer = lookupClass.getModule().getLayer();
               TruffleRuntimeAccess access;
               if (moduleLayer != null) {
                  access = Truffle.selectTruffleRuntimeAccess(List.of(ServiceLoader.load(moduleLayer, TruffleRuntimeAccess.class)));
               } else {
                  access = Truffle.selectTruffleRuntimeAccess(List.of(ServiceLoader.load(TruffleRuntimeAccess.class, lookupClass.getClassLoader())));
               }

               if (access == null) {
                  access = Truffle.selectTruffleRuntimeAccess(List.of(ServiceLoader.load(TruffleRuntimeAccess.class)));
               }

               if (access != null) {
                  Truffle.exportTo(access.getClass());
                  return access.getRuntime();
               } else {
                  return new DefaultTruffleRuntime();
               }
            } else if (runtimeClassName.equals(DefaultTruffleRuntime.class.getName())) {
               return new DefaultTruffleRuntime();
            } else {
               try {
                  ClassLoader cl = Thread.currentThread().getContextClassLoader();
                  Class<?> runtimeClass = Class.forName(runtimeClassName, false, cl);
                  return (TruffleRuntime)runtimeClass.getDeclaredConstructor().newInstance();
               } catch (Throwable var5) {
                  throw new InternalError(var5);
               }
            }
         }
      });
   }

   private static void exportTo(Class<?> client) {
      Module truffleModule = Truffle.class.getModule();
      exportFromTo(truffleModule, client.getModule());
   }

   private static void exportFromTo(Module truffleModule, Module clientModule) {
      if (truffleModule != clientModule) {
         for (String pkg : truffleModule.getPackages()) {
            boolean exported = truffleModule.isExported(pkg, clientModule);
            if (!exported) {
               truffleModule.addExports(pkg, clientModule);
            }
         }
      }
   }
}
