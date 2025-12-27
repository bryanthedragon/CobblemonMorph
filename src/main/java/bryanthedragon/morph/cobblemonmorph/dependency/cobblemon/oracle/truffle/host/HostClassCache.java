package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleOptions;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

final class HostClassCache {
   static final HostTargetMapping[] EMPTY_MAPPINGS = new HostTargetMapping[0];
   private final AbstractPolyglotImpl.APIAccess apiAccess;
   final HostAccess hostAccess;
   final AbstractPolyglotImpl.AbstractHostAccess polyglotHostAccess;
   private final boolean arrayAccess;
   private final boolean listAccess;
   private final boolean bufferAccess;
   private final boolean iterableAccess;
   private final boolean iteratorAccess;
   private final boolean mapAccess;
   final boolean allowsPublicAccess;
   final boolean allowsAccessInheritance;
   private final Map<Class<?>, Object> targetMappings;
   private final Object unnamedModule;
   private final WeakReference<HostClassCache> weakHostClassRef = new WeakReference<>(this);
   private final ClassValue<HostClassDesc> descs = new ClassValue<HostClassDesc>() {
      protected HostClassDesc computeValue(Class<?> type) {
         return new HostClassDesc(HostClassCache.this.weakHostClassRef, type);
      }
   };

   private HostClassCache(
      AbstractPolyglotImpl.AbstractHostAccess polyglotAccess, AbstractPolyglotImpl.APIAccess apiAccess, HostAccess conf, ClassLoader classLoader
   ) {
      this.polyglotHostAccess = polyglotAccess;
      this.hostAccess = conf;
      this.apiAccess = apiAccess;
      this.arrayAccess = apiAccess.isArrayAccessible(this.hostAccess);
      this.listAccess = apiAccess.isListAccessible(this.hostAccess);
      this.bufferAccess = apiAccess.isBufferAccessible(this.hostAccess);
      this.iterableAccess = apiAccess.isIterableAccessible(this.hostAccess);
      this.iteratorAccess = apiAccess.isIteratorAccessible(this.hostAccess);
      this.mapAccess = apiAccess.isMapAccessible(this.hostAccess);
      this.allowsPublicAccess = apiAccess.allowsPublicAccess(this.hostAccess);
      this.allowsAccessInheritance = apiAccess.allowsAccessInheritance(this.hostAccess);
      this.targetMappings = groupMappings(apiAccess, conf);
      this.unnamedModule = HostContext.getUnnamedModule(classLoader);
   }

   Object getUnnamedModule() {
      return this.unnamedModule;
   }

   boolean hasTargetMappings() {
      return this.targetMappings != null;
   }

   @CompilerDirectives.TruffleBoundary
   HostTargetMapping[] getMappings(Class<?> targetType) {
      if (this.targetMappings != null) {
         Class<?> lookupType;
         if (targetType.isPrimitive()) {
            if (targetType == byte.class) {
               lookupType = Byte.class;
            } else if (targetType == short.class) {
               lookupType = Short.class;
            } else if (targetType == int.class) {
               lookupType = Integer.class;
            } else if (targetType == long.class) {
               lookupType = Long.class;
            } else if (targetType == float.class) {
               lookupType = Float.class;
            } else if (targetType == double.class) {
               lookupType = Double.class;
            } else if (targetType == boolean.class) {
               lookupType = Boolean.class;
            } else if (targetType == char.class) {
               lookupType = Character.class;
            } else if (targetType == void.class) {
               lookupType = Void.class;
            } else {
               lookupType = null;
            }
         } else {
            lookupType = targetType;
         }

         HostTargetMapping[] mappings = (HostTargetMapping[])this.targetMappings.get(lookupType);
         return mappings == null ? EMPTY_MAPPINGS : mappings;
      } else {
         return EMPTY_MAPPINGS;
      }
   }

   private static Map<Class<?>, Object> groupMappings(AbstractPolyglotImpl.APIAccess apiAccess, HostAccess conf) {
      List<Object> mappings = apiAccess.getTargetMappings(conf);
      if (mappings == null) {
         return null;
      } else {
         Map<Class<?>, Object> localMappings = new HashMap<>();

         for (Object mapping : mappings) {
            HostTargetMapping map = (HostTargetMapping)mapping;
            List<HostTargetMapping> list = (List<HostTargetMapping>)localMappings.get(map.targetType);
            if (list == null) {
               list = new ArrayList<>();
               localMappings.put(map.targetType, list);
            }

            list.add(map);
         }

         for (Entry<Class<?>, Object> object : localMappings.entrySet()) {
            List<HostTargetMapping> classMappings = (List<HostTargetMapping>)object.getValue();
            Collections.sort(classMappings);
            object.setValue(classMappings.toArray(EMPTY_MAPPINGS));
         }

         return localMappings;
      }
   }

   public static HostClassCache findOrInitialize(
      AbstractPolyglotImpl.AbstractHostAccess hostLanguage, AbstractPolyglotImpl.APIAccess apiAccess, HostAccess conf, ClassLoader classLoader
   ) {
      HostClassCache cache = (HostClassCache)apiAccess.getHostAccessImpl(conf);
      if (cache == null) {
         cache = initializeHostCache(hostLanguage, apiAccess, conf, classLoader);
      }

      return cache;
   }

   private static HostClassCache initializeHostCache(
      AbstractPolyglotImpl.AbstractHostAccess polyglotAccess, AbstractPolyglotImpl.APIAccess apiAccess, HostAccess conf, ClassLoader classLoader
   ) {
      synchronized (conf) {
         HostClassCache cache = (HostClassCache)apiAccess.getHostAccessImpl(conf);
         if (cache == null) {
            cache = new HostClassCache(polyglotAccess, apiAccess, conf, classLoader);
            apiAccess.setHostAccessImpl(conf, cache);
         }

         return cache;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static HostClassCache forInstance(HostObject receiver) {
      return receiver.context.getHostClassCache();
   }

   @CompilerDirectives.TruffleBoundary
   HostClassDesc forClass(Class<?> clazz) {
      return this.descs.get(clazz);
   }

   @CompilerDirectives.TruffleBoundary
   boolean allowsAccess(Method m) {
      return this.apiAccess.allowsAccess(this.hostAccess, m) || isGeneratedClassMember(m);
   }

   @CompilerDirectives.TruffleBoundary
   boolean allowsAccess(Constructor<?> m) {
      return this.apiAccess.allowsAccess(this.hostAccess, m) || isGeneratedClassMember(m);
   }

   @CompilerDirectives.TruffleBoundary
   boolean allowsAccess(Field f) {
      return this.apiAccess.allowsAccess(this.hostAccess, f) || isGeneratedClassMember(f);
   }

   private static boolean isGeneratedClassMember(Member member) {
      return TruffleOptions.AOT ? false : HostAdapterClassLoader.isGeneratedClass(member.getDeclaringClass());
   }

   boolean isArrayAccess() {
      return this.arrayAccess;
   }

   boolean isListAccess() {
      return this.listAccess;
   }

   boolean isBufferAccess() {
      return this.bufferAccess;
   }

   boolean isIterableAccess() {
      return this.iterableAccess;
   }

   boolean isIteratorAccess() {
      return this.iteratorAccess;
   }

   boolean isMapAccess() {
      return this.mapAccess;
   }

   boolean allowsImplementation(Class<?> type) {
      return this.apiAccess.allowsImplementation(this.hostAccess, type);
   }

   boolean methodScoped(Executable e) {
      return this.apiAccess.isMethodScoped(this.hostAccess, e);
   }
}
