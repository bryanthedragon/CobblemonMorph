package com.oracle.truffle.api.source;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleFile;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Objects;
import org.graalvm.polyglot.io.ByteSequence;

final class SourceImpl extends Source {
   final SourceImpl.Key key;
   private final Object sourceId;

   private SourceImpl(SourceImpl.Key key) {
      this.key = key;
      this.sourceId = new SourceImpl.SourceId(key.hashCode());
   }

   private SourceImpl(SourceImpl.Key key, Object sourceId) {
      this.key = key;
      this.sourceId = sourceId;
   }

   @Override
   protected Object getSourceId() {
      return this.sourceId;
   }

   @Override
   protected Object getSourceKey() {
      return this.key;
   }

   @Override
   public CharSequence getCharacters() {
      if (this.hasCharacters()) {
         return (CharSequence)this.key.content;
      } else {
         throw new UnsupportedOperationException();
      }
   }

   @Override
   public ByteSequence getBytes() {
      if (this.hasBytes()) {
         return (ByteSequence)this.key.content;
      } else {
         throw new UnsupportedOperationException();
      }
   }

   @Override
   public boolean hasBytes() {
      return this.key.content instanceof ByteSequence;
   }

   @Override
   public boolean hasCharacters() {
      return this.key.content instanceof CharSequence;
   }

   @Override
   Source copy() {
      return new SourceImpl(this.key, this.sourceId);
   }

   @Override
   public boolean isCached() {
      return this.key.cached;
   }

   @Override
   public String getName() {
      return this.key.name;
   }

   @Override
   public String getPath() {
      return this.key.getPath();
   }

   @Override
   public boolean isInternal() {
      return this.key.internal;
   }

   @Override
   public boolean isInteractive() {
      return this.key.interactive;
   }

   @Override
   public URL getURL() {
      return this.key.getURL();
   }

   @Override
   public URI getOriginalURI() {
      return this.key.getURI();
   }

   @Override
   public String getMimeType() {
      return this.key.mimeType;
   }

   @Override
   public String getLanguage() {
      return this.key.language;
   }

   SourceImpl.Key toKey() {
      return this.key;
   }

   static final class ImmutableKey extends SourceImpl.Key {
      private final URI uri;
      private final URL url;
      private final String path;

      ImmutableKey(
         Object content,
         String mimeType,
         String languageId,
         URL url,
         URI uri,
         String name,
         String path,
         boolean internal,
         boolean interactive,
         boolean cached,
         String relativePathInLanguageHome,
         boolean embedderSource
      ) {
         super(content, mimeType, languageId, name, internal, interactive, cached, embedderSource);
         this.uri = uri;
         this.url = url;
         this.path = path;
         if (relativePathInLanguageHome != null) {
            this.cachedHashCode = hashCodeImpl(
               content, mimeType, this.language, null, null, name, relativePathInLanguageHome, internal, interactive, cached, embedderSource
            );
         }
      }

      @Override
      String getPath() {
         return this.path;
      }

      @Override
      URI getURI() {
         return this.uri;
      }

      @Override
      URL getURL() {
         return this.url;
      }
   }

   abstract static class Key {
      final Object content;
      final String name;
      final String mimeType;
      final String language;
      final boolean internal;
      final boolean interactive;
      final boolean cached;
      final boolean embedderSource;
      volatile Integer cachedHashCode;

      Key(Object content, String mimeType, String languageId, String name, boolean internal, boolean interactive, boolean cached, boolean embedderSource) {
         this.content = content;
         this.mimeType = mimeType;
         this.language = languageId;
         this.name = name;
         this.internal = internal;
         this.interactive = interactive;
         this.cached = cached;
         this.embedderSource = embedderSource;
      }

      abstract String getPath();

      abstract URI getURI();

      abstract URL getURL();

      @Override
      public int hashCode() {
         Integer hashCode = this.cachedHashCode;
         if (hashCode == null) {
            hashCode = hashCodeImpl(
               this.content,
               this.mimeType,
               this.language,
               this.getURL(),
               this.getURI(),
               this.name,
               this.getPath(),
               this.internal,
               this.interactive,
               this.cached,
               this.embedderSource
            );
            this.cachedHashCode = hashCode;
         }

         return hashCode;
      }

      static int hashCodeImpl(
         Object content,
         String mimeType,
         String language,
         URL url,
         URI uri,
         String name,
         String path,
         boolean internal,
         boolean interactive,
         boolean cached,
         boolean embedderSource
      ) {
         int result = 31 + (content == null ? 0 : content.hashCode());
         result = 31 * result + (interactive ? 1231 : 1237);
         result = 31 * result + (internal ? 1231 : 1237);
         result = 31 * result + (cached ? 1231 : 1237);
         result = 31 * result + (embedderSource ? 1231 : 1237);
         result = 31 * result + (language == null ? 0 : language.hashCode());
         result = 31 * result + (mimeType == null ? 0 : mimeType.hashCode());
         result = 31 * result + (name == null ? 0 : name.hashCode());
         result = 31 * result + (path == null ? 0 : path.hashCode());
         result = 31 * result + (uri == null ? 0 : uri.hashCode());
         return 31 * result + (url == null ? 0 : url.hashCode());
      }

      @Override
      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (!(obj instanceof SourceImpl.Key)) {
            return false;
         } else {
            SourceImpl.Key other = (SourceImpl.Key)obj;
            return Objects.equals(this.language, other.language)
               && Objects.equals(this.mimeType, other.mimeType)
               && Objects.equals(this.name, other.name)
               && Objects.equals(this.getPath(), other.getPath())
               && Objects.equals(this.getURI(), other.getURI())
               && Objects.equals(this.getURL(), other.getURL())
               && this.interactive == other.interactive
               && this.internal == other.internal
               && this.cached == other.cached
               && this.embedderSource == other.embedderSource
               && this.compareContent(other);
         }
      }

      void invalidateAfterPreinitialiation() {
      }

      private boolean compareContent(SourceImpl.Key other) {
         Object otherContent = other.content;
         if (this.content == other.content) {
            return true;
         } else if (this.content instanceof CharSequence && otherContent instanceof CharSequence) {
            return compareCharacters((CharSequence)this.content, (CharSequence)otherContent);
         } else {
            return this.content instanceof ByteSequence && otherContent instanceof ByteSequence
               ? compareBytes((ByteSequence)this.content, (ByteSequence)otherContent)
               : false;
         }
      }

      private static boolean compareBytes(ByteSequence bytes, ByteSequence other) {
         return bytes != null && bytes.length() == other.length() ? bytes.equals(other) : false;
      }

      private static boolean compareCharacters(CharSequence characters, CharSequence other) {
         return characters != null && characters.length() == other.length() ? Objects.equals(characters.toString(), other.toString()) : false;
      }

      SourceImpl toSourceInterned() {
         assert this.cached;

         return new SourceImpl(this);
      }

      SourceImpl toSourceNotInterned() {
         assert !this.cached;

         return new SourceImpl(this, this);
      }
   }

   static final class ReinitializableKey extends SourceImpl.Key {
      private static final Object INVALID = new Object();
      private TruffleFile truffleFile;
      private Object uri;
      private Object url;
      private Object path;

      ReinitializableKey(
         TruffleFile truffleFile,
         Object content,
         String mimeType,
         String languageId,
         URL url,
         URI uri,
         String name,
         String path,
         boolean internal,
         boolean interactive,
         boolean cached,
         String relativePathInLanguageHome,
         boolean embedderSource
      ) {
         super(content, mimeType, languageId, name, internal, interactive, cached, embedderSource);
         Objects.requireNonNull(truffleFile, "TruffleFile must be non null.");
         this.truffleFile = truffleFile;
         this.uri = uri;
         this.url = url;
         this.path = path;
         this.cachedHashCode = hashCodeImpl(
            content, mimeType, this.language, null, null, name, relativePathInLanguageHome, internal, interactive, cached, embedderSource
         );
      }

      @Override
      void invalidateAfterPreinitialiation() {
         if (this.path != INVALID && Objects.equals(this.path, SourceAccessor.getReinitializedPath(this.truffleFile))) {
            this.path = INVALID;
         }

         if (this.uri != INVALID && Objects.equals(this.uri, SourceAccessor.getReinitializedURI(this.truffleFile))) {
            this.uri = INVALID;
         }

         try {
            if (this.url != null
               && this.url != INVALID
               && SourceAccessor.getReinitializedURI(this.truffleFile).toURL().toExternalForm().equals(((URL)this.url).toExternalForm())) {
               this.url = INVALID;
            }
         } catch (MalformedURLException var2) {
            throw new AssertionError(var2);
         }
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      String getPath() {
         if (this.path == INVALID) {
            this.path = SourceAccessor.getReinitializedPath(this.truffleFile);
         }

         return (String)this.path;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      URI getURI() {
         if (this.uri == INVALID) {
            this.uri = SourceAccessor.getReinitializedURI(this.truffleFile);
         }

         return (URI)this.uri;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      URL getURL() {
         if (this.url == INVALID) {
            try {
               URI localUri = this.getURI();
               this.url = new URL(localUri.getScheme(), localUri.getHost(), localUri.getPort(), localUri.getRawPath());
            } catch (MalformedURLException var2) {
               throw new AssertionError(var2);
            }
         }

         return (URL)this.url;
      }
   }

   private static final class SourceId {
      final int hash;

      SourceId(int hash) {
         this.hash = hash;
      }

      @Override
      public boolean equals(Object obj) {
         return this == obj;
      }

      @Override
      public int hashCode() {
         return this.hash;
      }
   }
}
