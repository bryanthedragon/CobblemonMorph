package com.cobblemon.mod.relocations.ibm.icu.impl;

import java.security.AccessController;
import java.security.PrivilegedAction;

public class ClassLoaderUtil {
   private static volatile ClassLoader BOOTSTRAP_CLASSLOADER;

   private static ClassLoader getBootstrapClassLoader() {
      if (BOOTSTRAP_CLASSLOADER == null) {
         synchronized (ClassLoaderUtil.class) {
            if (BOOTSTRAP_CLASSLOADER == null) {
               ClassLoader cl = null;
               if (System.getSecurityManager() != null) {
                  cl = AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
                     public ClassLoaderUtil.BootstrapClassLoader run() {
                        return new ClassLoaderUtil.BootstrapClassLoader();
                     }
                  });
               } else {
                  cl = new ClassLoaderUtil.BootstrapClassLoader();
               }

               BOOTSTRAP_CLASSLOADER = cl;
            }
         }
      }

      return BOOTSTRAP_CLASSLOADER;
   }

   public static ClassLoader getClassLoader(Class<?> cls) {
      ClassLoader cl = cls.getClassLoader();
      if (cl == null) {
         cl = getClassLoader();
      }

      return cl;
   }

   public static ClassLoader getClassLoader() {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      if (cl == null) {
         cl = ClassLoader.getSystemClassLoader();
         if (cl == null) {
            cl = getBootstrapClassLoader();
         }
      }

      return cl;
   }

   private static class BootstrapClassLoader extends ClassLoader {
      BootstrapClassLoader() {
         super(Object.class.getClassLoader());
      }
   }
}
