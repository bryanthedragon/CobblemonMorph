package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

final class HostAdapterFactory {
   @CompilerDirectives.TruffleBoundary
   static HostAdapterFactory.AdapterResult getAdapterClassFor(HostContext hostContext, Class<?>[] types, Object classOverrides) {
      assert types.length > 0;

      HostClassCache hostClassCache = hostContext.getHostClassCache();
      HostClassLoader hostClassLoader = hostContext.getClassloader();
      if (classOverrides == null) {
         if (types.length == 1) {
            HostClassDesc classDesc = HostClassDesc.forClass(hostClassCache, types[0]);
            return classDesc.getAdapter(hostContext);
         } else {
            Map<List<Class<?>>, HostAdapterFactory.AdapterResult> map = hostContext.adapterCache.get(getTypeForCache(types));
            List<Class<?>> cacheKey = Arrays.asList(types);
            HostAdapterFactory.AdapterResult result = map.get(cacheKey);
            if (result == null) {
               result = makeAdapterClassFor(hostClassCache, types, hostClassLoader, classOverrides);
               if (result.isSuccess()) {
                  HostAdapterFactory.AdapterResult prev = map.putIfAbsent(cacheKey, result);
                  if (prev != null) {
                     result = prev;
                  }
               }
            }

            return result;
         }
      } else {
         return makeAdapterClassFor(hostClassCache, types, hostClassLoader, classOverrides);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static HostAdapterFactory.AdapterResult makeAdapterClassFor(HostClassCache hostClassCache, Class<?>[] types, ClassLoader classLoader, Object classOverrides) {
      return makeAdapterClassForCommon(hostClassCache, types, classLoader, classOverrides);
   }

   @CompilerDirectives.TruffleBoundary
   static HostAdapterFactory.AdapterResult makeAdapterClassFor(HostClassCache hostClassCache, Class<?> type, ClassLoader classLoader) {
      return makeAdapterClassForCommon(hostClassCache, new Class[]{type}, classLoader, null);
   }

   private static HostAdapterFactory.AdapterResult makeAdapterClassForCommon(
      HostClassCache hostClassCache, Class<?>[] types, ClassLoader classLoader, Object classOverrides
   ) {
      assert types.length > 0;

      CompilerAsserts.neverPartOfCompilation();
      AbstractPolyglotImpl.AbstractHostAccess polyglotAccess = hostClassCache.polyglotHostAccess;
      Class<?> superClass = null;
      List<Class<?>> interfaces = new ArrayList<>();

      for (Class<?> t : types) {
         if (!t.isInterface()) {
            if (superClass != null) {
               throw HostEngineException.illegalArgument(
                  polyglotAccess,
                  String.format(
                     "Can not extend multiple classes %s and %s. At most one of the specified types can be a class, the rest must all be interfaces.",
                     t.getCanonicalName(),
                     superClass.getCanonicalName()
                  )
               );
            }

            if (Modifier.isFinal(t.getModifiers())) {
               throw HostEngineException.illegalArgument(polyglotAccess, String.format("Can not extend final class %s.", t.getCanonicalName()));
            }

            superClass = t;
         } else {
            if (interfaces.size() >= 65535) {
               throw HostEngineException.illegalArgument(polyglotAccess, "interface limit exceeded");
            }

            interfaces.add(t);
         }

         if (!Modifier.isPublic(t.getModifiers())) {
            throw HostEngineException.illegalArgument(polyglotAccess, String.format("Class not public: %s.", t.getCanonicalName()));
         }

         if (!HostInteropReflect.isExtensibleType(t) || !hostClassCache.allowsImplementation(t)) {
            throw HostEngineException.illegalArgument(polyglotAccess, "Implementation not allowed for " + t);
         }
      }

      superClass = superClass != null ? superClass : Object.class;
      ClassLoader commonLoader = getCommonClassLoader(classLoader, superClass);
      if (!classLoaderCanSee(commonLoader, types)) {
         throw HostEngineException.illegalArgument(
            polyglotAccess, "Could not determine a class loader that can see all types: " + Arrays.toString((Object[])types)
         );
      } else {
         Class<?> adapterClass;
         try {
            adapterClass = generateAdapterClassFor(superClass, interfaces, commonLoader, hostClassCache, classOverrides);
         } catch (IllegalArgumentException var16) {
            return new HostAdapterFactory.AdapterResult(HostEngineException.illegalArgument(polyglotAccess, var16));
         } catch (RuntimeException var17) {
            if (polyglotAccess.isEngineException(var17)) {
               return new HostAdapterFactory.AdapterResult(var17);
            }

            throw var17;
         }

         HostClassDesc classDesc = hostClassCache.forClass(adapterClass);
         HostMethodDesc constructor = classDesc.lookupConstructor();
         HostMethodDesc.SingleMethod valueConstructor = null;
         if (constructor == null) {
            return new HostAdapterFactory.AdapterResult(
               HostEngineException.illegalArgument(polyglotAccess, "No accessible constructor: " + superClass.getCanonicalName())
            );
         } else {
            for (HostMethodDesc.SingleMethod overload : constructor.getOverloads()) {
               if (overload.getParameterCount() == 1 && overload.getParameterTypes()[0] == Value.class) {
                  valueConstructor = overload;
                  break;
               }
            }

            return new HostAdapterFactory.AdapterResult(adapterClass, constructor, valueConstructor);
         }
      }
   }

   private static Class<?> generateAdapterClassFor(
      Class<?> superClass, List<Class<?>> interfaces, ClassLoader commonLoader, HostClassCache hostClassCache, Object classOverrides
   ) {
      boolean classOverride = classOverrides != null;
      HostAdapterBytecodeGenerator bytecodeGenerator = new HostAdapterBytecodeGenerator(superClass, interfaces, commonLoader, hostClassCache, classOverride);
      HostAdapterClassLoader generatedClassLoader = bytecodeGenerator.createAdapterClassLoader();
      return generatedClassLoader.generateClass(commonLoader, classOverrides);
   }

   @CompilerDirectives.TruffleBoundary
   static Object getSuperAdapter(HostObject adapter) {
      assert isAdapterInstance(adapter.obj);

      return new HostAdapterSuperMembers(adapter);
   }

   @CompilerDirectives.TruffleBoundary
   static String getSuperMethodName(String methodName) {
      assert !methodName.startsWith("super$");

      return "super$".concat(methodName);
   }

   @CompilerDirectives.TruffleBoundary
   static boolean isAdapterInstance(Object adapter) {
      return HostAdapterClassLoader.isAdapterInstance(adapter);
   }

   private static boolean classLoaderCanSee(ClassLoader loader, Class<?> clazz) {
      if (clazz.getClassLoader() == loader) {
         return true;
      } else {
         try {
            return Class.forName(clazz.getName(), false, loader) == clazz;
         } catch (ClassNotFoundException var3) {
            return false;
         }
      }
   }

   private static boolean classLoaderCanSee(ClassLoader loader, Class<?>[] classes) {
      for (Class<?> c : classes) {
         if (!classLoaderCanSee(loader, c)) {
            return false;
         }
      }

      return true;
   }

   private static ClassLoader getCommonClassLoader(ClassLoader classLoader, Class<?> superclass) {
      return superclass != Object.class && HostAdapterClassLoader.isGeneratedClass(superclass) ? superclass.getClassLoader() : classLoader;
   }

   private static Class<?> getTypeForCache(Class<?>[] types) {
      return types[0];
   }

   static final class AdapterResult {
      private final Class<?> adapterClass;
      private final HostMethodDesc constructor;
      private final HostMethodDesc.SingleMethod valueConstructor;
      private final RuntimeException exception;

      AdapterResult(Class<?> adapterClass, HostMethodDesc constructor, HostMethodDesc.SingleMethod valueConstructor) {
         this.adapterClass = Objects.requireNonNull(adapterClass);
         this.constructor = constructor;
         this.valueConstructor = valueConstructor;
         this.exception = null;
      }

      AdapterResult(RuntimeException exception) {
         this.adapterClass = null;
         this.constructor = null;
         this.valueConstructor = null;
         this.exception = exception;
      }

      Class<?> getAdapterClass() {
         return this.adapterClass;
      }

      HostMethodDesc getConstructor() {
         return this.constructor;
      }

      HostMethodDesc.SingleMethod getValueConstructor() {
         return this.valueConstructor;
      }

      boolean isSuccess() {
         return this.constructor != null;
      }

      boolean isAutoConvertible() {
         return this.valueConstructor != null;
      }

      RuntimeException throwException() {
         throw this.exception;
      }
   }
}
