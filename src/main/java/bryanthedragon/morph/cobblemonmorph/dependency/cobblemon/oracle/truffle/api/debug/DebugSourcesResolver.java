
package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.TruffleInstrument;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.WeakHashMap;

final class DebugSourcesResolver {
    private final TruffleInstrument.Env env;
    private volatile URI[] sourcePath = new URI[0];
    private final Map<Source, Source> resolvedMap = new WeakHashMap<Source, Source>();

    DebugSourcesResolver(TruffleInstrument.Env env) {
        this.env = env;
    }

    void setSourcePath(Iterable<URI> uris) {
        ArrayList<URI> collection;
        if (uris instanceof Collection) {
            collection = (ArrayList<URI>)uris;
        } else {
            ArrayList<URI> list = new ArrayList<URI>();
            for (URI uri : uris) {
                list.add(uri);
            }
            collection = list;
        }
        URI[] array = collection.toArray(new URI[collection.size()]);
        for (int i = 0; i < array.length; ++i) {
            if (array[i].isAbsolute()) continue;
            try {
                array[i] = new URI("file://" + array[i].toString());
                continue;
            }
            catch (URISyntaxException ex) {
                throw new IllegalArgumentException("URI " + array[i] + " is not absolute and can not be converted to a file: " + ex.getLocalizedMessage());
            }
        }
        this.sourcePath = array;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    Source resolve(Source source) {
        Source resolved;
        if (source.hasCharacters() || source.hasBytes()) {
            return source;
        }
        Map<Source, Source> map = this.resolvedMap;
        synchronized (map) {
            resolved = this.resolvedMap.getOrDefault(source, source);
            if (resolved == source) {
                resolved = this.doResolve(source);
                this.resolvedMap.put(source, resolved);
            }
        }
        return resolved;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Source doResolve(Source source) {
        URI uri = source.getURI();
        InputStream stream = null;
        if (uri.isAbsolute()) {
            try {
                stream = uri.toURL().openConnection().getInputStream();
            }
            catch (IOException ioex) {
                return null;
            }
        } else {
            URI[] roots;
            for (URI root : roots = this.sourcePath) {
                URI resolved = DebugSourcesResolver.resolve(root, uri);
                try {
                    stream = resolved.toURL().openConnection().getInputStream();
                    uri = resolved;
                    break;
                }
                catch (IOException ioex) {
                }
            }
        }
        if (stream == null) {
            return null;
        }
        try {
            Object name;
            Source.SourceBuilder builder = null;
            if ("file".equals(uri.getScheme())) {
                TruffleFile file = this.env.getTruffleFile(uri);
                builder = Source.newBuilder(source.getLanguage(), file);
            } else {
                try {
                    URL url = uri.toURL();
                    builder = Source.newBuilder(source.getLanguage(), url);
                }
                catch (IllegalArgumentException | MalformedURLException exception) {
                    // empty catch block
                }
            }
            if (builder == null) {
                name = uri.getPath() != null ? uri.getPath() : uri.getSchemeSpecificPart();
                builder = Source.newBuilder(source.getLanguage(), new InputStreamReader(stream), (String)name).uri(uri);
            }
            try {
                name = builder.cached(false).interactive(source.isInteractive()).internal(source.isInternal()).mimeType(source.getMimeType()).build();
                return name;
            }
            catch (IOException | SecurityException e) {
                this.env.getLogger("").warning(String.format("Failed to resolve %s: %s%s", source.getURI(), e.getLocalizedMessage(), System.lineSeparator()));
                Source source2 = null;
                try {
                    stream.close();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
                return source2;
            }
        }
        finally {
            try {
                stream.close();
            }
            catch (IOException iOException) {}
        }
    }

    private static URI resolve(URI base, URI child) {
        String childPath = child.getPath();
        if (childPath == null || childPath.isEmpty()) {
            return base;
        }
        Object path = base.getPath();
        try {
            URI resolved;
            if (path != null) {
                path = ((String)path).endsWith("/") ? (String)path + childPath : (String)path + "/" + childPath;
                resolved = new URI(base.getScheme(), base.getUserInfo(), base.getHost(), base.getPort(), (String)path, base.getQuery(), base.getFragment());
            } else {
                Object ssp = base.getSchemeSpecificPart();
                ssp = ((String)ssp).endsWith("/") ? (String)ssp + childPath : (String)ssp + "/" + childPath;
                resolved = new URI(base.getScheme(), (String)ssp, base.getFragment());
            }
            return resolved.normalize();
        }
        catch (URISyntaxException ex) {
            return base;
        }
    }

    SourceSection resolve(SourceSection section) {
        if (section == null) {
            return null;
        }
        Source source = section.getSource();
        Source rSource = this.resolve(source);
        if (rSource == source || rSource == null) {
            return section;
        }
        try {
            if (!section.isAvailable()) {
                return rSource.createUnavailableSection();
            }
            if (section.hasCharIndex()) {
                return rSource.createSection(section.getCharIndex(), section.getCharLength());
            }
            if (section.hasColumns()) {
                return rSource.createSection(section.getStartLine(), section.getStartColumn(), section.getEndLine(), section.getEndColumn());
            }
            if (section.hasLines()) {
                int startColumn;
                int startLine = section.getStartLine();
                int endLine = section.getEndLine();
                CharSequence firstLine = rSource.getCharacters(startLine);
                int length = firstLine.length();
                for (startColumn = 0; startColumn < length && Character.isWhitespace(firstLine.charAt(startColumn)); ++startColumn) {
                }
                if (startColumn == length) {
                    startColumn = 0;
                }
                return rSource.createSection(startLine, startColumn + 1, endLine, rSource.getLineLength(endLine));
            }
            return section;
        }
        catch (IllegalArgumentException ex) {
            return section;
        }
    }

    static SourceSection findEncapsulatedSourceSection(Node node) {
        for (Node n = node; n != null; n = n.getParent()) {
            SourceSection sourceSection;
            if (!(n instanceof InstrumentableNode) || !((InstrumentableNode)((Object)n)).isInstrumentable() || (sourceSection = n.getSourceSection()) == null || !sourceSection.isAvailable()) continue;
            return sourceSection;
        }
        RootNode rootNode = node.getRootNode();
        return rootNode != null ? rootNode.getSourceSection() : null;
    }
}

