package org.graalvm.polyglot;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Objects;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;
import org.graalvm.polyglot.io.ByteSequence;

public final class Source {
   private static volatile AbstractPolyglotImpl IMPL;
   final AbstractPolyglotImpl.AbstractSourceDispatch dispatch;
   final Object receiver;
   private static final Source EMPTY = new Source(null, null);

   static AbstractPolyglotImpl getImpl() {
      if (IMPL == null) {
         synchronized (Engine.class) {
            if (IMPL == null) {
               IMPL = Engine.getImpl();
            }
         }
      }

      return IMPL;
   }

   Source(AbstractPolyglotImpl.AbstractSourceDispatch dispatch, Object receiver) {
      this.dispatch = dispatch;
      this.receiver = receiver;
   }

   public String getLanguage() {
      return this.dispatch.getLanguage(this.receiver);
   }

   public String getName() {
      return this.dispatch.getName(this.receiver);
   }

   public String getPath() {
      return this.dispatch.getPath(this.receiver);
   }

   public URL getURL() {
      return this.dispatch.getURL(this.receiver);
   }

   public URI getURI() {
      return this.dispatch.getURI(this.receiver);
   }

   public boolean isInteractive() {
      return this.dispatch.isInteractive(this.receiver);
   }

   public boolean isInternal() {
      return this.dispatch.isInternal(this.receiver);
   }

   public Reader getReader() {
      return this.dispatch.getReader(this.receiver);
   }

   @Deprecated(since = "19.0")
   public InputStream getInputStream() {
      return this.dispatch.getInputStream(this.receiver);
   }

   public int getLength() {
      return this.dispatch.getLength(this.receiver);
   }

   public CharSequence getCharacters() {
      return this.dispatch.getCharacters(this.receiver);
   }

   public String getMimeType() {
      return this.dispatch.getMimeType(this.receiver);
   }

   public CharSequence getCharacters(int lineNumber) {
      return this.dispatch.getCharacters(this.receiver, lineNumber);
   }

   public ByteSequence getBytes() {
      return this.dispatch.getBytes(this.receiver);
   }

   public boolean hasCharacters() {
      return this.dispatch.hasCharacters(this.receiver);
   }

   public boolean hasBytes() {
      return this.dispatch.hasBytes(this.receiver);
   }

   public int getLineCount() {
      return this.dispatch.getLineCount(this.receiver);
   }

   public int getLineNumber(int offset) throws IllegalArgumentException {
      return this.dispatch.getLineNumber(this.receiver, offset);
   }

   public int getColumnNumber(int offset) throws IllegalArgumentException {
      return this.dispatch.getColumnNumber(this.receiver, offset);
   }

   public int getLineStartOffset(int lineNumber) throws IllegalArgumentException {
      return this.dispatch.getLineStartOffset(this.receiver, lineNumber);
   }

   public int getLineLength(int lineNumber) throws IllegalArgumentException {
      return this.dispatch.getLineLength(this.receiver, lineNumber);
   }

   @Override
   public String toString() {
      return this.dispatch.toString(this.receiver);
   }

   @Override
   public int hashCode() {
      return this.dispatch.hashCode(this.receiver);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof Source) {
         Object otherImpl = ((Source)obj).receiver;
         return this.dispatch.equals(this.receiver, otherImpl);
      } else {
         return false;
      }
   }

   public static Source.Builder newBuilder(String language, CharSequence characters, String name) {
      return EMPTY.new Builder(language, characters).name(name);
   }

   public static Source.Builder newBuilder(String language, ByteSequence bytes, String name) {
      return EMPTY.new Builder(language, bytes).name(name);
   }

   public static Source.Builder newBuilder(String language, File file) {
      return EMPTY.new Builder(language, file);
   }

   public static Source.Builder newBuilder(String language, URL url) {
      return EMPTY.new Builder(language, url);
   }

   public static Source.Builder newBuilder(String language, Reader source, String name) {
      return EMPTY.new Builder(language, source).name(name);
   }

   public static Source create(String language, CharSequence source) {
      return newBuilder(language, source, "Unnamed").buildLiteral();
   }

   public static String findLanguage(File file) throws IOException {
      return getImpl().findLanguage(file);
   }

   public static String findLanguage(URL url) throws IOException {
      return getImpl().findLanguage(url);
   }

   public static String findMimeType(File file) throws IOException {
      return getImpl().findMimeType(file);
   }

   public static String findMimeType(URL url) throws IOException {
      return getImpl().findMimeType(url);
   }

   public static String findLanguage(String mimeType) {
      return getImpl().findLanguage(mimeType);
   }

   static <E extends Exception> RuntimeException silenceException(Class<E> type, Exception ex) throws E {
      throw ex;
   }

   private static void validateMimeType(String mimeType) {
      if (mimeType != null) {
         int index = mimeType.indexOf(47);
         if (index == -1 || index == 0 || index == mimeType.length() - 1) {
            throw invalidMimeType(mimeType);
         } else if (mimeType.indexOf(47, index + 1) != -1) {
            throw invalidMimeType(mimeType);
         }
      }
   }

   private static IllegalArgumentException invalidMimeType(String mimeType) {
      return new IllegalArgumentException(
         String.format("Invalid MIME type '%s' provided. A MIME type consists of a type and a subtype separated by '/'.", mimeType)
      );
   }

   public class Builder {
      private final String language;
      private final Object origin;
      private URI uri;
      private String name;
      private boolean interactive;
      private boolean internal;
      private boolean cached = true;
      private Object content;
      private String mimeType;
      private Charset fileEncoding;

      Builder(String language, Object origin) {
         Objects.requireNonNull(language);
         Objects.requireNonNull(origin);
         this.language = language;
         this.origin = origin;
      }

      public Source.Builder name(String newName) {
         this.name = newName;
         return this;
      }

      public Source.Builder content(String code) {
         return this.content((CharSequence)code);
      }

      public Source.Builder content(CharSequence characters) {
         Objects.requireNonNull(characters);
         this.content = characters;
         return this;
      }

      public Source.Builder content(ByteSequence bytes) {
         Objects.requireNonNull(bytes);
         this.content = bytes;
         return this;
      }

      public Source.Builder mimeType(String mimeType) {
         Source.validateMimeType(mimeType);
         this.mimeType = mimeType;
         return this;
      }

      public Source.Builder interactive(boolean interactive) {
         this.interactive = interactive;
         return this;
      }

      public Source.Builder internal(boolean internal) {
         this.internal = internal;
         return this;
      }

      public Source.Builder cached(boolean cached) {
         this.cached = cached;
         return this;
      }

      public Source.Builder uri(URI newUri) {
         Objects.requireNonNull(newUri);
         this.uri = newUri;
         return this;
      }

      public Source.Builder encoding(Charset encoding) {
         this.fileEncoding = encoding;
         return this;
      }

      public Source build() throws IOException {
         Source source = Source.getImpl()
            .build(
               this.language,
               this.origin,
               this.uri,
               this.name,
               this.mimeType,
               this.content,
               this.interactive,
               this.internal,
               this.cached,
               this.fileEncoding,
               null,
               null
            );
         if (source.hasBytes()) {
            this.content = source.getBytes();
         } else {
            assert source.hasCharacters();

            this.content = source.getCharacters();
         }

         return source;
      }

      public Source buildLiteral() {
         try {
            return this.build();
         } catch (IOException var2) {
            throw new AssertionError("No error expected.", var2);
         }
      }
   }
}
