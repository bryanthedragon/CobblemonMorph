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
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

final class DebugSourcesResolver {
   private final TruffleInstrument.Env env;
   private volatile URI[] sourcePath = new URI[0];
   private final Map<Source, Source> resolvedMap = new WeakHashMap<>();

   DebugSourcesResolver(TruffleInstrument.Env env) {
      this.env = env;
   }

   void setSourcePath(Iterable<URI> uris) {
      Collection<URI> collection;
      if (uris instanceof Collection) {
         collection = (Collection<URI>)uris;
      } else {
         List<URI> list = new ArrayList<>();

         for (URI uri : uris) {
            list.add(uri);
         }

         collection = list;
      }

      URI[] array = collection.toArray(new URI[collection.size()]);

      for (int i = 0; i < array.length; i++) {
         if (!array[i].isAbsolute()) {
            try {
               array[i] = new URI("file://" + array[i].toString());
            } catch (URISyntaxException var6) {
               throw new IllegalArgumentException("URI " + array[i] + " is not absolute and can not be converted to a file: " + var6.getLocalizedMessage());
            }
         }
      }

      this.sourcePath = array;
   }

   Source resolve(Source source) {
      if (!source.hasCharacters() && !source.hasBytes()) {
         synchronized (this.resolvedMap) {
            Source resolved = this.resolvedMap.getOrDefault(source, source);
            if (resolved == source) {
               resolved = this.doResolve(source);
               this.resolvedMap.put(source, resolved);
            }

            return resolved;
         }
      } else {
         return source;
      }
   }

   private Source doResolve(Source source) {
      URI uri = source.getURI();
      InputStream stream = null;
      if (uri.isAbsolute()) {
         try {
            stream = uri.toURL().openConnection().getInputStream();
         } catch (IOException var23) {
            return null;
         }
      } else {
         URI[] roots = this.sourcePath;

         for (URI root : roots) {
            URI resolved = resolve(root, uri);

            try {
               stream = resolved.toURL().openConnection().getInputStream();
               uri = resolved;
               break;
            } catch (IOException var26) {
            }
         }
      }

      if (stream == null) {
         return null;
      } else {
         Object var32;
         try {
            Source.SourceBuilder builder = null;
            if ("file".equals(uri.getScheme())) {
               TruffleFile file = this.env.getTruffleFile(uri);
               builder = Source.newBuilder(source.getLanguage(), file);
            } else {
               try {
                  URL url = uri.toURL();
                  builder = Source.newBuilder(source.getLanguage(), url);
               } catch (IllegalArgumentException | MalformedURLException var22) {
               }
            }

            if (builder == null) {
               String name = uri.getPath() != null ? uri.getPath() : uri.getSchemeSpecificPart();
               builder = Source.newBuilder(source.getLanguage(), new InputStreamReader(stream), name).uri(uri);
            }

            try {
               return builder.cached(false).interactive(source.isInteractive()).internal(source.isInternal()).mimeType(source.getMimeType()).build();
            } catch (SecurityException | IOException var24) {
               this.env
                  .getLogger("")
                  .warning(String.format("Failed to resolve %s: %s%s", source.getURI(), var24.getLocalizedMessage(), System.lineSeparator()));
               var32 = null;
            }
         } finally {
            try {
               stream.close();
            } catch (IOException var21) {
            }
         }

         return (Source)var32;
      }
   }

   private static URI resolve(URI base, URI child) {
      String childPath = child.getPath();
      if (childPath != null && !childPath.isEmpty()) {
         String path = base.getPath();

         try {
            URI resolved;
            if (path != null) {
               if (path.endsWith("/")) {
                  path = path + childPath;
               } else {
                  path = path + "/" + childPath;
               }

               resolved = new URI(base.getScheme(), base.getUserInfo(), base.getHost(), base.getPort(), path, base.getQuery(), base.getFragment());
            } else {
               String ssp = base.getSchemeSpecificPart();
               if (ssp.endsWith("/")) {
                  ssp = ssp + childPath;
               } else {
                  ssp = ssp + "/" + childPath;
               }

               resolved = new URI(base.getScheme(), ssp, base.getFragment());
            }

            return resolved.normalize();
         } catch (URISyntaxException var6) {
            return base;
         }
      } else {
         return base;
      }
   }

   SourceSection resolve(SourceSection section) {
      if (section == null) {
         return null;
      } else {
         Source source = section.getSource();
         Source rSource = this.resolve(source);
         if (rSource != source && rSource != null) {
            try {
               if (!section.isAvailable()) {
                  return rSource.createUnavailableSection();
               } else if (section.hasCharIndex()) {
                  return rSource.createSection(section.getCharIndex(), section.getCharLength());
               } else if (section.hasColumns()) {
                  return rSource.createSection(section.getStartLine(), section.getStartColumn(), section.getEndLine(), section.getEndColumn());
               } else if (!section.hasLines()) {
                  return section;
               } else {
                  int startLine = section.getStartLine();
                  int endLine = section.getEndLine();
                  int startColumn = 0;
                  CharSequence firstLine = rSource.getCharacters(startLine);
                  int length = firstLine.length();

                  while (startColumn < length && Character.isWhitespace(firstLine.charAt(startColumn))) {
                     startColumn++;
                  }

                  if (startColumn == length) {
                     startColumn = 0;
                  }

                  return rSource.createSection(startLine, startColumn + 1, endLine, rSource.getLineLength(endLine));
               }
            } catch (IllegalArgumentException var9) {
               return section;
            }
         } else {
            return section;
         }
      }
   }

   static SourceSection findEncapsulatedSourceSection(Node node) {
      for (Node n = node; n != null; n = n.getParent()) {
         if (n instanceof InstrumentableNode && ((InstrumentableNode)n).isInstrumentable()) {
            SourceSection sourceSection = n.getSourceSection();
            if (sourceSection != null && sourceSection.isAvailable()) {
               return sourceSection;
            }
         }
      }

      RootNode rootNode = node.getRootNode();
      return rootNode != null ? rootNode.getSourceSection() : null;
   }
}
