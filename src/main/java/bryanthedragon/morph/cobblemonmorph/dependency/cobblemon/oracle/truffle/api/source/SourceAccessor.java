
package com.oracle.truffle.api.source;

import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.impl.Accessor;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceImpl;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Set;
import java.util.function.Function;

final class SourceAccessor
extends Accessor {
    static final SourceAccessor ACCESSOR = new SourceAccessor();
    static final Accessor.LanguageSupport LANGUAGE = ACCESSOR.languageSupport();

    private SourceAccessor() {
    }

    public static void load() {
    }

    static String detectMimeType(TruffleFile file, Set<String> validMimeTypes) {
        return ACCESSOR.languageSupport().detectMimeType(file, validMimeTypes);
    }

    static Charset detectEncoding(TruffleFile file, String mimeType) {
        return ACCESSOR.languageSupport().detectEncoding(file, mimeType);
    }

    static TruffleFile getTruffleFile(URI uri, Object fileSystemContext) {
        return ACCESSOR.languageSupport().getTruffleFile(fileSystemContext, uri);
    }

    static TruffleFile getTruffleFile(String path, Object fileSystemContext) {
        return ACCESSOR.languageSupport().getTruffleFile(path, fileSystemContext);
    }

    static boolean hasAllAccess(Object fileSystemContext) {
        return ACCESSOR.languageSupport().hasAllAccess(fileSystemContext);
    }

    static void onSourceCreated(Source source) {
        ACCESSOR.engineSupport().onSourceCreated(source);
    }

    static String getReinitializedPath(TruffleFile truffleFile) {
        return ACCESSOR.engineSupport().getReinitializedPath(truffleFile);
    }

    static URI getReinitializedURI(TruffleFile truffleFile) {
        return ACCESSOR.engineSupport().getReinitializedURI(truffleFile);
    }

    static final class SourceSupportImpl
    extends Accessor.SourceSupport {
        SourceSupportImpl() {
        }

        @Override
        public Source copySource(Source source) {
            Source copy = source.copy();
            copy.cachedPolyglotSource = source.cachedPolyglotSource;
            return copy;
        }

        @Override
        public Object getSourceIdentifier(Source source) {
            return source.getSourceId();
        }

        @Override
        public org.graalvm.polyglot.Source getOrCreatePolyglotSource(Source source, Function<Source, org.graalvm.polyglot.Source> createSource) {
            WeakReference<org.graalvm.polyglot.Source> ref = source.cachedPolyglotSource;
            org.graalvm.polyglot.Source polyglotSource = ref == null ? null : (org.graalvm.polyglot.Source)ref.get();
            if (polyglotSource == null) {
                polyglotSource = createSource.apply(source);
                source.cachedPolyglotSource = new WeakReference<org.graalvm.polyglot.Source>(polyglotSource);
            }
            assert (polyglotSource != null);
            return polyglotSource;
        }

        @Override
        public String findMimeType(URL url, Object fileSystemContext) throws IOException {
            return Source.findMimeType(url, url.openConnection(), null, fileSystemContext);
        }

        @Override
        public Source.SourceBuilder newBuilder(String language, File origin) {
            return Source.newBuilder(language, origin);
        }

        @Override
        public void setFileSystemContext(Source.SourceBuilder builder, Object fileSystemContext) {
            builder.fileSystemContext(fileSystemContext);
        }

        @Override
        public void setEmbedderSource(Source.SourceBuilder builder, boolean enabled) {
            builder.embedderSource(enabled);
        }

        @Override
        public void setURL(Source.SourceBuilder builder, URL url) {
            builder.url(url);
        }

        @Override
        public void setPath(Source.SourceBuilder builder, String path) {
            builder.path(path);
        }

        @Override
        public void invalidateAfterPreinitialiation(Source source) {
            ((SourceImpl)source).key.invalidateAfterPreinitialiation();
        }

        @Override
        public void mergeLoadedSources(Source[] sources) {
            for (Source s : sources) {
                if (!(s instanceof SourceImpl)) continue;
                Source.SOURCES.add((SourceImpl)s);
            }
        }
    }
}

