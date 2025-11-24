
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.polyglot.EngineAccessor;
import com.oracle.truffle.polyglot.PolyglotEngineException;
import com.oracle.truffle.polyglot.PolyglotImpl;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

final class PolyglotSourceCache {
    private final Cache strongCache;
    private final Cache weakCache = new WeakCache();

    PolyglotSourceCache() {
        this.strongCache = new StrongCache();
    }

    CallTarget parseCached(PolyglotLanguageContext context, Source source, String[] argumentNames) {
        CallTarget target;
        if (source.isCached()) {
            Cache strong = this.strongCache;
            boolean useStrong = context.getEngine().storeEngine;
            if ((useStrong || !strong.isEmpty()) && (target = strong.lookup(context, source, argumentNames, useStrong)) != null) {
                return target;
            }
            target = this.weakCache.lookup(context, source, argumentNames, true);
        } else {
            target = PolyglotSourceCache.parseImpl(context, argumentNames, source);
        }
        return target;
    }

    void listCachedSources(PolyglotImpl polyglot, Collection<org.graalvm.polyglot.Source> source) {
        this.strongCache.listSources(polyglot, source);
        this.weakCache.listSources(polyglot, source);
    }

    private static CallTarget parseImpl(PolyglotLanguageContext context, String[] argumentNames, Source source) {
        PolyglotSourceCache.validateSource(context, source);
        CallTarget parsedTarget = EngineAccessor.LANGUAGE.parse(context.requireEnv(), source, null, argumentNames);
        if (parsedTarget == null) {
            throw new IllegalStateException(String.format("Parsing resulted in a null CallTarget for %s.", source));
        }
        return parsedTarget;
    }

    private static void validateSource(PolyglotLanguageContext context, Source source) {
        boolean expectCharacters;
        if (!source.hasBytes() && !source.hasCharacters()) {
            throw PolyglotEngineException.illegalArgument(String.format("Error evaluating the source. The source does not specify characters nor bytes.", new Object[0]));
        }
        String mimeType = source.getMimeType();
        Set<String> mimeTypes = context.language.cache.getMimeTypes();
        if (mimeType != null && !mimeTypes.contains(mimeType)) {
            throw PolyglotEngineException.illegalArgument(String.format("Error evaluating the source. The language %s does not support MIME type %s. Supported MIME types are %s.", source.getLanguage(), mimeType, mimeTypes));
        }
        String activeMimeType = mimeType;
        if (activeMimeType == null) {
            activeMimeType = context.language.cache.getDefaultMimeType();
        }
        boolean bl = expectCharacters = activeMimeType != null ? context.language.cache.isCharacterMimeType(activeMimeType) : true;
        if (mimeType != null && source.hasCharacters() != expectCharacters) {
            if (source.hasBytes()) {
                throw PolyglotEngineException.illegalArgument(String.format("Error evaluating the source. MIME type '%s' is character based for language '%s' but the source contents are byte based.", mimeType, source.getLanguage()));
            }
            throw PolyglotEngineException.illegalArgument(String.format("Error evaluating the source. MIME type '%s' is byte based for language '%s' but the source contents are character based.", mimeType, source.getLanguage()));
        }
        if (source.hasCharacters() != expectCharacters) {
            HashSet<String> binaryMimeTypes = new HashSet<String>();
            HashSet<String> characterMimeTypes = new HashSet<String>();
            for (String supportedMimeType : mimeTypes) {
                if (context.language.cache.isCharacterMimeType(supportedMimeType)) {
                    characterMimeTypes.add(supportedMimeType);
                    continue;
                }
                binaryMimeTypes.add(supportedMimeType);
            }
            if (expectCharacters) {
                if (binaryMimeTypes.isEmpty()) {
                    throw PolyglotEngineException.illegalArgument(String.format("Error evaluating the source. The language %s only supports character based sources but a binary based source was provided.", source.getLanguage()));
                }
                throw PolyglotEngineException.illegalArgument(String.format("Error evaluating the source. The language %s expects character based sources by default but a binary based source was provided. Provide a binary based source instead or specify a MIME type for the source. Available MIME types for binary based sources are %s.", source.getLanguage(), binaryMimeTypes));
            }
            if (characterMimeTypes.isEmpty()) {
                throw PolyglotEngineException.illegalArgument(String.format("Error evaluating the source. The language %s only supports binary based sources but a character based source was provided.", source.getLanguage()));
            }
            throw PolyglotEngineException.illegalArgument(String.format("Error evaluating the source. The language %s expects character based sources by default but a binary based source was provided. Provide a character based source instead or specify a MIME type for the source. Available MIME types for character based sources are %s.", source.getLanguage(), characterMimeTypes));
        }
    }

    private static final class WeakSourceKey
    extends WeakReference<Source> {
        final SourceKey key;

        WeakSourceKey(SourceKey key, Source value2, ReferenceQueue<? super Source> q) {
            super(value2, q);
            this.key = key;
        }

        public int hashCode() {
            return this.key.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj instanceof WeakSourceKey) {
                WeakSourceKey other = (WeakSourceKey)obj;
                return this.key.equals(other.key);
            }
            return false;
        }
    }

    private static final class SourceKey {
        private final Object key;
        private final String[] arguments;

        SourceKey(Object key, String[] arguments) {
            this.key = key;
            this.arguments = arguments != null && arguments.length == 0 ? null : arguments;
        }

        public int hashCode() {
            int prime = 31;
            int result = 1;
            result = 31 * result + this.key.hashCode();
            result = 31 * result + Arrays.hashCode(this.arguments);
            return result;
        }

        public boolean equals(Object obj) {
            if (obj instanceof SourceKey) {
                SourceKey other = (SourceKey)obj;
                return this.key.equals(other.key) && Arrays.equals(this.arguments, other.arguments);
            }
            return false;
        }
    }

    static class WeakCacheValue {
        final CallTarget target;
        final Source source;

        WeakCacheValue(CallTarget target, Source source) {
            this.target = target;
            this.source = source;
        }
    }

    private static final class WeakCache
    extends Cache {
        private final ConcurrentHashMap<WeakSourceKey, WeakCacheValue> sourceCache = new ConcurrentHashMap();
        private final ReferenceQueue<Source> deadSources = new ReferenceQueue();

        private WeakCache() {
        }

        @Override
        CallTarget lookup(PolyglotLanguageContext context, Source source, String[] argumentNames, boolean parse2) {
            this.cleanupStaleEntries();
            Object sourceId = EngineAccessor.SOURCE.getSourceIdentifier(source);
            Source sourceValue = EngineAccessor.SOURCE.copySource(source);
            WeakSourceKey ref = new WeakSourceKey(new SourceKey(sourceId, argumentNames), source, this.deadSources);
            WeakCacheValue value2 = this.sourceCache.get(ref);
            if (value2 == null) {
                if (parse2) {
                    value2 = new WeakCacheValue(PolyglotSourceCache.parseImpl(context, argumentNames, sourceValue), sourceValue);
                    WeakCacheValue prev = this.sourceCache.putIfAbsent(ref, value2);
                    if (prev != null) {
                        value2 = prev;
                    }
                } else {
                    return null;
                }
            }
            return value2.target;
        }

        @Override
        boolean isEmpty() {
            return this.sourceCache.isEmpty();
        }

        @Override
        void listSources(PolyglotImpl polyglot, Collection<org.graalvm.polyglot.Source> sources) {
            this.cleanupStaleEntries();
            for (WeakCacheValue value2 : this.sourceCache.values()) {
                sources.add(PolyglotImpl.getOrCreatePolyglotSource(polyglot, value2.source));
            }
        }

        private void cleanupStaleEntries() {
            WeakSourceKey sourceRef = null;
            while ((sourceRef = (WeakSourceKey)this.deadSources.poll()) != null) {
                this.sourceCache.remove(sourceRef);
            }
        }
    }

    private static final class StrongCache
    extends Cache {
        private final ConcurrentHashMap<SourceKey, CallTarget> sourceCache = new ConcurrentHashMap();

        private StrongCache() {
        }

        @Override
        CallTarget lookup(PolyglotLanguageContext context, Source source, String[] argumentNames, boolean parse2) {
            CallTarget prevTarget;
            SourceKey key = new SourceKey(source, argumentNames);
            CallTarget target = this.sourceCache.get(key);
            if (target == null && parse2 && (prevTarget = this.sourceCache.putIfAbsent(key, target = PolyglotSourceCache.parseImpl(context, argumentNames, source))) != null) {
                target = prevTarget;
            }
            return target;
        }

        @Override
        boolean isEmpty() {
            return this.sourceCache.isEmpty();
        }

        @Override
        void listSources(PolyglotImpl polyglot, Collection<org.graalvm.polyglot.Source> sources) {
            for (SourceKey key : this.sourceCache.keySet()) {
                sources.add(PolyglotImpl.getOrCreatePolyglotSource(polyglot, (Source)key.key));
            }
        }
    }

    private static abstract class Cache {
        private Cache() {
        }

        abstract boolean isEmpty();

        abstract CallTarget lookup(PolyglotLanguageContext var1, Source var2, String[] var3, boolean var4);

        abstract void listSources(PolyglotImpl var1, Collection<org.graalvm.polyglot.Source> var2);
    }
}

