package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CallTarget;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.graalvm.polyglot.Source;

final class PolyglotSourceCache {
   private final PolyglotSourceCache.Cache strongCache;
   private final PolyglotSourceCache.Cache weakCache = new PolyglotSourceCache.WeakCache();

   PolyglotSourceCache() {
      this.strongCache = new PolyglotSourceCache.StrongCache();
   }

   CallTarget parseCached(PolyglotLanguageContext context, com.oracle.truffle.api.source.Source source, String[] argumentNames) {
      CallTarget target;
      if (source.isCached()) {
         PolyglotSourceCache.Cache strong = this.strongCache;
         boolean useStrong = context.getEngine().storeEngine;
         if (useStrong || !strong.isEmpty()) {
            target = strong.lookup(context, source, argumentNames, useStrong);
            if (target != null) {
               return target;
            }
         }

         target = this.weakCache.lookup(context, source, argumentNames, true);
      } else {
         target = parseImpl(context, argumentNames, source);
      }

      return target;
   }

   void listCachedSources(PolyglotImpl polyglot, Collection<Source> source) {
      this.strongCache.listSources(polyglot, source);
      this.weakCache.listSources(polyglot, source);
   }

   private static CallTarget parseImpl(PolyglotLanguageContext context, String[] argumentNames, com.oracle.truffle.api.source.Source source) {
      validateSource(context, source);
      CallTarget parsedTarget = EngineAccessor.LANGUAGE.parse(context.requireEnv(), source, null, argumentNames);
      if (parsedTarget == null) {
         throw new IllegalStateException(String.format("Parsing resulted in a null CallTarget for %s.", source));
      } else {
         return parsedTarget;
      }
   }

   private static void validateSource(PolyglotLanguageContext context, com.oracle.truffle.api.source.Source source) {
      if (!source.hasBytes() && !source.hasCharacters()) {
         throw PolyglotEngineException.illegalArgument(String.format("Error evaluating the source. The source does not specify characters nor bytes."));
      } else {
         String mimeType = source.getMimeType();
         Set<String> mimeTypes = context.language.cache.getMimeTypes();
         if (mimeType != null && !mimeTypes.contains(mimeType)) {
            throw PolyglotEngineException.illegalArgument(
               String.format(
                  "Error evaluating the source. The language %s does not support MIME type %s. Supported MIME types are %s.",
                  source.getLanguage(),
                  mimeType,
                  mimeTypes
               )
            );
         } else {
            String activeMimeType = mimeType;
            if (mimeType == null) {
               activeMimeType = context.language.cache.getDefaultMimeType();
            }

            boolean expectCharacters = activeMimeType != null ? context.language.cache.isCharacterMimeType(activeMimeType) : true;
            if (mimeType != null && source.hasCharacters() != expectCharacters) {
               if (source.hasBytes()) {
                  throw PolyglotEngineException.illegalArgument(
                     String.format(
                        "Error evaluating the source. MIME type '%s' is character based for language '%s' but the source contents are byte based.",
                        mimeType,
                        source.getLanguage()
                     )
                  );
               } else {
                  throw PolyglotEngineException.illegalArgument(
                     String.format(
                        "Error evaluating the source. MIME type '%s' is byte based for language '%s' but the source contents are character based.",
                        mimeType,
                        source.getLanguage()
                     )
                  );
               }
            } else if (source.hasCharacters() != expectCharacters) {
               Set<String> binaryMimeTypes = new HashSet<>();
               Set<String> characterMimeTypes = new HashSet<>();

               for (String supportedMimeType : mimeTypes) {
                  if (context.language.cache.isCharacterMimeType(supportedMimeType)) {
                     characterMimeTypes.add(supportedMimeType);
                  } else {
                     binaryMimeTypes.add(supportedMimeType);
                  }
               }

               if (expectCharacters) {
                  if (binaryMimeTypes.isEmpty()) {
                     throw PolyglotEngineException.illegalArgument(
                        String.format(
                           "Error evaluating the source. The language %s only supports character based sources but a binary based source was provided.",
                           source.getLanguage()
                        )
                     );
                  } else {
                     throw PolyglotEngineException.illegalArgument(
                        String.format(
                           "Error evaluating the source. The language %s expects character based sources by default but a binary based source was provided. Provide a binary based source instead or specify a MIME type for the source. Available MIME types for binary based sources are %s.",
                           source.getLanguage(),
                           binaryMimeTypes
                        )
                     );
                  }
               } else if (characterMimeTypes.isEmpty()) {
                  throw PolyglotEngineException.illegalArgument(
                     String.format(
                        "Error evaluating the source. The language %s only supports binary based sources but a character based source was provided.",
                        source.getLanguage()
                     )
                  );
               } else {
                  throw PolyglotEngineException.illegalArgument(
                     String.format(
                        "Error evaluating the source. The language %s expects character based sources by default but a binary based source was provided. Provide a character based source instead or specify a MIME type for the source. Available MIME types for character based sources are %s.",
                        source.getLanguage(),
                        characterMimeTypes
                     )
                  );
               }
            }
         }
      }
   }

   private abstract static class Cache {
      abstract boolean isEmpty();

      abstract CallTarget lookup(PolyglotLanguageContext context, com.oracle.truffle.api.source.Source source, String[] argumentNames, boolean parse);

      abstract void listSources(PolyglotImpl polyglot, Collection<Source> source);
   }

   private static final class SourceKey {
      private final Object key;
      private final String[] arguments;

      SourceKey(Object key, String[] arguments) {
         this.key = key;
         this.arguments = arguments != null && arguments.length == 0 ? null : arguments;
      }

      @Override
      public int hashCode() {
         int prime = 31;
         int result = 1;
         result = 31 * result + this.key.hashCode();
         return 31 * result + Arrays.hashCode((Object[])this.arguments);
      }

      @Override
      public boolean equals(Object obj) {
         if (!(obj instanceof PolyglotSourceCache.SourceKey)) {
            return false;
         } else {
            PolyglotSourceCache.SourceKey other = (PolyglotSourceCache.SourceKey)obj;
            return this.key.equals(other.key) && Arrays.equals((Object[])this.arguments, (Object[])other.arguments);
         }
      }
   }

   private static final class StrongCache extends PolyglotSourceCache.Cache {
      private final ConcurrentHashMap<PolyglotSourceCache.SourceKey, CallTarget> sourceCache = new ConcurrentHashMap<>();

      @Override
      CallTarget lookup(PolyglotLanguageContext context, com.oracle.truffle.api.source.Source source, String[] argumentNames, boolean parse) {
         PolyglotSourceCache.SourceKey key = new PolyglotSourceCache.SourceKey(source, argumentNames);
         CallTarget target = this.sourceCache.get(key);
         if (target == null && parse) {
            target = PolyglotSourceCache.parseImpl(context, argumentNames, source);
            CallTarget prevTarget = this.sourceCache.putIfAbsent(key, target);
            if (prevTarget != null) {
               target = prevTarget;
            }
         }

         return target;
      }

      @Override
      boolean isEmpty() {
         return this.sourceCache.isEmpty();
      }

      @Override
      void listSources(PolyglotImpl polyglot, Collection<Source> sources) {
         for (PolyglotSourceCache.SourceKey key : this.sourceCache.keySet()) {
            sources.add(PolyglotImpl.getOrCreatePolyglotSource(polyglot, (com.oracle.truffle.api.source.Source)key.key));
         }
      }
   }

   private static final class WeakCache extends PolyglotSourceCache.Cache {
      private final ConcurrentHashMap<PolyglotSourceCache.WeakSourceKey, PolyglotSourceCache.WeakCacheValue> sourceCache = new ConcurrentHashMap<>();
      private final ReferenceQueue<com.oracle.truffle.api.source.Source> deadSources = new ReferenceQueue<>();

      @Override
      CallTarget lookup(PolyglotLanguageContext context, com.oracle.truffle.api.source.Source source, String[] argumentNames, boolean parse) {
         this.cleanupStaleEntries();
         Object sourceId = EngineAccessor.SOURCE.getSourceIdentifier(source);
         com.oracle.truffle.api.source.Source sourceValue = EngineAccessor.SOURCE.copySource(source);
         PolyglotSourceCache.WeakSourceKey ref = new PolyglotSourceCache.WeakSourceKey(
            new PolyglotSourceCache.SourceKey(sourceId, argumentNames), source, this.deadSources
         );
         PolyglotSourceCache.WeakCacheValue value = this.sourceCache.get(ref);
         if (value == null) {
            if (!parse) {
               return null;
            }

            value = new PolyglotSourceCache.WeakCacheValue(PolyglotSourceCache.parseImpl(context, argumentNames, sourceValue), sourceValue);
            PolyglotSourceCache.WeakCacheValue prev = this.sourceCache.putIfAbsent(ref, value);
            if (prev != null) {
               value = prev;
            }
         }

         return value.target;
      }

      @Override
      boolean isEmpty() {
         return this.sourceCache.isEmpty();
      }

      @Override
      void listSources(PolyglotImpl polyglot, Collection<Source> sources) {
         this.cleanupStaleEntries();

         for (PolyglotSourceCache.WeakCacheValue value : this.sourceCache.values()) {
            sources.add(PolyglotImpl.getOrCreatePolyglotSource(polyglot, value.source));
         }
      }

      private void cleanupStaleEntries() {
         PolyglotSourceCache.WeakSourceKey sourceRef = null;

         while ((sourceRef = (PolyglotSourceCache.WeakSourceKey)this.deadSources.poll()) != null) {
            this.sourceCache.remove(sourceRef);
         }
      }
   }

   static class WeakCacheValue {
      final CallTarget target;
      final com.oracle.truffle.api.source.Source source;

      WeakCacheValue(CallTarget target, com.oracle.truffle.api.source.Source source) {
         this.target = target;
         this.source = source;
      }
   }

   private static final class WeakSourceKey extends WeakReference<com.oracle.truffle.api.source.Source> {
      final PolyglotSourceCache.SourceKey key;

      WeakSourceKey(
         PolyglotSourceCache.SourceKey key, com.oracle.truffle.api.source.Source value, ReferenceQueue<? super com.oracle.truffle.api.source.Source> q
      ) {
         super(value, q);
         this.key = key;
      }

      @Override
      public int hashCode() {
         return this.key.hashCode();
      }

      @Override
      public boolean equals(Object obj) {
         if (obj instanceof PolyglotSourceCache.WeakSourceKey) {
            PolyglotSourceCache.WeakSourceKey other = (PolyglotSourceCache.WeakSourceKey)obj;
            return this.key.equals(other.key);
         } else {
            return false;
         }
      }
   }
}
