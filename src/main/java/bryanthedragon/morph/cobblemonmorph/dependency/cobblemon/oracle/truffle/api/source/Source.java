package com.oracle.truffle.api.source;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.impl.Accessor;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import org.graalvm.polyglot.io.ByteSequence;

public abstract class Source {
   public static final CharSequence CONTENT_NONE = null;
   private static final CharSequence CONTENT_UNSET = new String();
   private static final byte[] CONTENT_EMPTY = new byte[0];
   private static final Source EMPTY = new SourceImpl.ImmutableKey(null, null, null, null, null, null, null, false, false, false, null, false)
      .toSourceNotInterned();
   private static final String NO_FASTPATH_SUBSOURCE_CREATION_MESSAGE = "do not create sub sources from compiled code";
   private static final String URI_SCHEME = "truffle";
   private static final int MAX_BUFFER_SIZE = 2147483639;
   private static final int BUFFER_SIZE = 8192;
   static final Class<?> BYTE_SEQUENCE_CLASS = ByteSequence.create(new byte[0]).getClass();
   static final InternedSources SOURCES = new InternedSources();
   private volatile TextMap textMap;
   private volatile URI computedURI;
   volatile WeakReference<org.graalvm.polyglot.Source> cachedPolyglotSource;
   private static final boolean ALLOW_IO = SourceAccessor.ACCESSOR.engineSupport().isIOSupported();

   abstract Object getSourceId();

   abstract Object getSourceKey();

   Source() {
   }

   public abstract String getLanguage();

   public abstract String getName();

   public abstract String getPath();

   public abstract boolean isInternal();

   public abstract boolean isCached();

   public abstract boolean isInteractive();

   @Override
   public final boolean equals(Object obj) {
      if (!(obj instanceof Source)) {
         return false;
      } else {
         boolean result = this.getSourceId().equals(((Source)obj).getSourceId());

         assert !result || this.getSourceKey().equals(((Source)obj).getSourceKey());

         return result;
      }
   }

   @Override
   public final int hashCode() {
      return this.getSourceId().hashCode();
   }

   public Source subSource(int baseCharIndex, int length) {
      if (this.hasBytes()) {
         throw new UnsupportedOperationException("Operation is only enabled for character based sources.");
      } else {
         CompilerAsserts.neverPartOfCompilation("do not create sub sources from compiled code");
         return SubSourceImpl.create(this, baseCharIndex, length);
      }
   }

   public abstract CharSequence getCharacters();

   public abstract boolean hasBytes();

   public abstract boolean hasCharacters();

   public abstract ByteSequence getBytes();

   public abstract URL getURL();

   abstract URI getOriginalURI();

   public final URI getURI() {
      URI uri = this.getOriginalURI();
      if (uri == null) {
         uri = this.computedURI;
         if (uri == null) {
            byte[] bytes;
            if (this.hasBytes()) {
               bytes = this.getBytes().toByteArray();
            } else if (this.hasCharacters()) {
               bytes = this.getCharacters().toString().getBytes();
            } else {
               bytes = CONTENT_EMPTY;
            }

            uri = this.computedURI = this.getNamedURI(this.getName(), bytes);
         }
      }

      return uri;
   }

   public abstract String getMimeType();

   public final Reader getReader() {
      return new CharSequenceReader(this.getCharacters());
   }

   public final int getLength() {
      if (this.hasCharacters()) {
         return this.getCharacters().length();
      } else if (this.hasBytes()) {
         return this.getBytes().length();
      } else {
         throw new UnsupportedOperationException("Operation is only enabled for sources with character or byte content.");
      }
   }

   public final CharSequence getCharacters(int lineNumber) {
      int offset = this.getTextMap().lineStartOffset(lineNumber);
      int length = this.getTextMap().lineLength(lineNumber);
      return this.getCharacters().subSequence(offset, offset + length);
   }

   public final int getLineCount() {
      return this.getTextMap().lineCount();
   }

   public final int getLineNumber(int offset) throws IllegalArgumentException {
      return this.getTextMap().offsetToLine(offset);
   }

   public final int getColumnNumber(int offset) throws IllegalArgumentException {
      return this.getTextMap().offsetToCol(offset);
   }

   public final int getLineStartOffset(int lineNumber) throws IllegalArgumentException {
      return this.getTextMap().lineStartOffset(lineNumber);
   }

   public final int getLineLength(int lineNumber) throws IllegalArgumentException {
      return this.getTextMap().lineLength(lineNumber);
   }

   public final SourceSection createUnavailableSection() {
      return new SourceSectionUnavailable(this);
   }

   public final SourceSection createSection(int startLine, int startColumn, int endLine, int endColumn) {
      if (this.hasBytes()) {
         throw new UnsupportedOperationException("Operation is only enabled for character based sources.");
      } else if (startLine < 1) {
         throw new IllegalArgumentException("lineNumber < 1");
      } else if (startLine > endLine) {
         throw new IllegalArgumentException("startLine " + startLine + " > endLine " + endLine);
      } else if (startLine == endLine && startColumn > endColumn) {
         throw new IllegalArgumentException("startColumn " + startColumn + " > endColumn " + endColumn);
      } else if (this.hasCharacters()) {
         if (startColumn >= 1 && endColumn >= 1) {
            int charIndex = this.getTextMap().lineColumnToOffset(startLine, startColumn);
            int endIndex = this.getTextMap().lineColumnToOffset(endLine, endColumn);

            assert charIndex <= endIndex : charIndex + " > " + endIndex;

            int length = endIndex + 1 - charIndex;
            int sourceLength = this.getTextMap().length();
            if (length == 1 && charIndex + length > sourceLength) {
               length = 0;
            }

            if (charIndex + length > sourceLength) {
               throw new IllegalArgumentException("end position out of range");
            } else {
               SourceSection section = new SourceSectionLoaded(this, charIndex, length);

               assert assertValid(section);

               return section;
            }
         } else {
            throw new IllegalArgumentException("columnNumber < 1");
         }
      } else if (startColumn == -1) {
         if (endColumn != -1) {
            throw new IllegalArgumentException("endColumn can not be specified when startColumn is not.");
         } else {
            return new SourceSectionUnloaded.Lines(this, startLine, endLine);
         }
      } else if (startColumn >= 1 && endColumn >= 1) {
         return new SourceSectionUnloaded.LinesAndColumns(this, startLine, startColumn, endLine, endColumn);
      } else {
         throw new IllegalArgumentException("columnNumber < 1");
      }
   }

   public final SourceSection createSection(int lineNumber) {
      if (this.hasBytes()) {
         throw new UnsupportedOperationException("Operation is only enabled for character based sources.");
      } else if (lineNumber < 1) {
         throw new IllegalArgumentException("lineNumber < 1");
      } else {
         SourceSection section;
         if (this.hasCharacters()) {
            int charIndex = this.getTextMap().lineStartOffset(lineNumber);
            int length = this.getTextMap().lineLength(lineNumber);
            section = new SourceSectionLoaded(this, charIndex, length);

            assert assertValid(section);
         } else {
            section = new SourceSectionUnloaded.Lines(this, lineNumber, lineNumber);
         }

         return section;
      }
   }

   public final SourceSection createSection(int charIndex, int length) {
      if (this.hasBytes()) {
         throw new UnsupportedOperationException("Operation is only enabled for character based sources.");
      } else if (charIndex < 0) {
         throw new IllegalArgumentException("charIndex < 0");
      } else if (length < 0) {
         throw new IllegalArgumentException("length < 0");
      } else {
         SourceSection section;
         if (this.hasCharacters()) {
            section = new SourceSectionLoaded(this, charIndex, length);

            assert assertValid(section);
         } else {
            section = new SourceSectionUnloaded.Indexed(this, charIndex, length);
         }

         return section;
      }
   }

   public final SourceSection createSection(int startLine, int startColumn, int length) {
      if (this.hasBytes() || !this.hasCharacters()) {
         throw new UnsupportedOperationException("Operation is only enabled for character based sources.");
      } else if (startLine <= 0) {
         throw new IllegalArgumentException("startLine < 1");
      } else if (startColumn <= 0) {
         throw new IllegalArgumentException("startColumn < 1");
      } else if (this.hasCharacters() && length < 0) {
         throw new IllegalArgumentException("length < 0");
      } else {
         int lineStartOffset = this.getTextMap().lineStartOffset(startLine);
         int lineLength = this.getTextMap().lineLength(startLine);
         if (startColumn > lineLength + 1) {
            throw new IllegalArgumentException("column out of range");
         } else {
            int charIndex = lineStartOffset + startColumn - 1;
            if (charIndex + length > this.getCharacters().length()) {
               throw new IllegalArgumentException("charIndex out of range");
            } else {
               SourceSection section = new SourceSectionLoaded(this, charIndex, length);

               assert assertValid(section);

               return section;
            }
         }
      }
   }

   @Override
   public String toString() {
      return "Source [language="
         + this.getLanguage()
         + ", name="
         + this.getName()
         + ", path="
         + this.getPath()
         + ", internal="
         + this.isInternal()
         + ", cached="
         + this.isCached()
         + ", interactive="
         + this.isInteractive()
         + ", hasBytes="
         + this.hasBytes()
         + ", hasCharacters="
         + this.hasCharacters()
         + ", URL="
         + this.getURL()
         + ", URI="
         + this.getURI()
         + ", mimeType="
         + this.getMimeType()
         + "]";
   }

   private static boolean assertValid(SourceSection section) {
      if (!section.isValid()) {
         throw new IllegalArgumentException("Invalid source section bounds.");
      } else {
         return true;
      }
   }

   abstract Source copy();

   final TextMap getTextMap() {
      if (this.hasBytes()) {
         throw new UnsupportedOperationException("Operation is only enabled for character based sources.");
      } else {
         TextMap res = this.textMap;
         if (res == null) {
            res = this.textMap = this.createTextMap();
         }

         assert res != null;

         return res;
      }
   }

   TextMap createTextMap() {
      CharSequence code = this.getCharacters();
      if (code == null) {
         throw new RuntimeException("can't read file " + this.getName());
      } else {
         return TextMap.fromCharSequence(code);
      }
   }

   private URI getNamedURI(String name, byte[] bytes) {
      return this.getNamedURI(name, bytes, 0, bytes.length);
   }

   private URI getNamedURI(String name, byte[] bytes, int byteIndex, int length) {
      String digest;
      if (bytes != null) {
         digest = digest(bytes, byteIndex, length);
      } else {
         digest = Integer.toString(System.identityHashCode(this), 16);
      }

      if (name != null) {
         digest = digest + "/" + name;
      }

      try {
         return new URI("truffle", digest, null);
      } catch (URISyntaxException var7) {
         throw new Error(var7);
      }
   }

   public static Source.LiteralBuilder newBuilder(String language, CharSequence characters, String name) {
      return EMPTY.new LiteralBuilder(language, characters, false).name(name);
   }

   public static Source.LiteralBuilder newBuilder(String language, ByteSequence bytes, String name) {
      return EMPTY.new LiteralBuilder(language, bytes, false).name(name);
   }

   public static Source.SourceBuilder newBuilder(String language, TruffleFile file) {
      return EMPTY.new LiteralBuilder(language, file, true);
   }

   static Source.SourceBuilder newBuilder(String language, File source) {
      return EMPTY.new LiteralBuilder(language, source, true);
   }

   public static Source.SourceBuilder newBuilder(String language, URL url) {
      return EMPTY.new LiteralBuilder(language, url, true);
   }

   public static Source.SourceBuilder newBuilder(String language, Reader source, String name) {
      return EMPTY.new LiteralBuilder(language, source, true).name(name);
   }

   public static Source.LiteralBuilder newBuilder(Source source) {
      return EMPTY.new LiteralBuilder(source);
   }

   public static String findLanguage(TruffleFile file) throws IOException {
      String mimeType = findMimeType(file);
      return mimeType != null ? findLanguage(mimeType) : null;
   }

   public static String findLanguage(URL url) throws IOException {
      String mimeType = findMimeType(url);
      return mimeType != null ? findLanguage(mimeType) : null;
   }

   public static String findMimeType(TruffleFile file) throws IOException {
      return file.detectMimeType();
   }

   public static String findMimeType(URL url) throws IOException {
      return findMimeType(url, url.openConnection(), null, SourceAccessor.ACCESSOR.engineSupport().getCurrentFileSystemContext());
   }

   public static String findLanguage(String mimeType) {
      return org.graalvm.polyglot.Source.findLanguage(mimeType);
   }

   private static IllegalArgumentException invalidMimeType() {
      return new IllegalArgumentException("Invalid MIME type provided. MIME types consist of a type and a subtype separated by '/'.");
   }

   static Source buildSource(
      String language,
      Object origin,
      String name,
      String path,
      boolean canonicalizePath,
      String mimeType,
      Object content,
      URL url,
      URI uri,
      Charset encoding,
      boolean internal,
      boolean interactive,
      boolean cached,
      Object fileSystemContext,
      boolean embedderSource
   ) throws IOException {
      String useName = name;
      URI useUri = uri;
      Object useContent = content;
      String useMimeType = mimeType;
      String usePath = path;
      URL useUrl = url;
      Object useOrigin = origin;
      Charset useEncoding = encoding;
      TruffleFile useTruffleFile = null;
      if (origin instanceof File) {
         File file = (File)origin;

         assert fileSystemContext != null : "file system context must be provided by polyglot embedding API";

         TruffleFile truffleFile = SourceAccessor.getTruffleFile(file.toPath().toString(), fileSystemContext);
         useOrigin = truffleFile;
      }

      if (useOrigin == CONTENT_UNSET) {
         useContent = content == CONTENT_UNSET ? null : content;
      } else if (useOrigin instanceof TruffleFile) {
         useTruffleFile = (TruffleFile)useOrigin;
         if (canonicalizePath && content != CONTENT_NONE) {
            useTruffleFile = getCanonicalFileIfItExists(useTruffleFile);
         } else if (uri == null) {
            useUri = useTruffleFile.isAbsolute() ? useTruffleFile.toUri() : useTruffleFile.toRelativeUri();
         }

         Object var35 = SourceAccessor.LANGUAGE.getFileSystemContext(useTruffleFile);
         useName = name == null ? useTruffleFile.getName() : name;
         usePath = path == null ? useTruffleFile.getPath() : path;
         useUri = useUri == null ? useTruffleFile.toUri() : useUri;
         useMimeType = mimeType == null ? SourceAccessor.detectMimeType(useTruffleFile, getValidMimeTypes(var35, language)) : mimeType;
         if (content == CONTENT_UNSET) {
            if (isCharacterBased(var35, language, useMimeType)) {
               useEncoding = encoding == null ? findEncoding(useTruffleFile, useMimeType) : encoding;
               useContent = read(useTruffleFile, useEncoding);
            } else {
               useContent = ByteSequence.create(useTruffleFile.readAllBytes());
            }
         }
      } else if (!(useOrigin instanceof URL)) {
         if (useOrigin instanceof Reader) {
            Reader r = (Reader)useOrigin;
            useContent = content == CONTENT_UNSET ? read(r) : content;
         } else if (useOrigin instanceof ByteSequence) {
            useContent = content == CONTENT_UNSET ? useOrigin : content;
         } else {
            assert useOrigin instanceof CharSequence;

            useContent = content == CONTENT_UNSET ? useOrigin : content;
         }
      } else {
         useUrl = (URL)useOrigin;
         String urlPath = useUrl.getPath();
         int lastIndex = urlPath.lastIndexOf(47);
         useName = name == null && lastIndex != -1 ? useUrl.getPath().substring(lastIndex + 1) : name;

         URI tmpUri;
         try {
            tmpUri = useUrl.toURI();
         } catch (URISyntaxException var30) {
            throw new IOException("Bad URL: " + useUrl, var30);
         }

         useUri = uri == null ? tmpUri : uri;
         usePath = path == null ? useUrl.getPath() : path;
         Object useFileSystemContext = fileSystemContext == null ? SourceAccessor.ACCESSOR.engineSupport().getCurrentFileSystemContext() : fileSystemContext;

         try {
            useTruffleFile = SourceAccessor.getTruffleFile(tmpUri, useFileSystemContext);
            useTruffleFile = getCanonicalFileIfItExists(useTruffleFile);
            if (useContent == CONTENT_UNSET) {
               if (isCharacterBased(useFileSystemContext, language, useMimeType)) {
                  String fileMimeType = useMimeType == null
                     ? SourceAccessor.detectMimeType(useTruffleFile, getValidMimeTypes(useFileSystemContext, language))
                     : useMimeType;
                  useEncoding = useEncoding == null ? findEncoding(useTruffleFile, fileMimeType) : useEncoding;
                  useContent = read(useTruffleFile, useEncoding);
               } else {
                  useContent = ByteSequence.create(useTruffleFile.readAllBytes());
               }
            }
         } catch (UnsupportedOperationException var31) {
            if (!ALLOW_IO || !SourceAccessor.hasAllAccess(useFileSystemContext)) {
               throw new SecurityException("Reading of URL " + useUrl + " is not allowed.");
            }

            URLConnection connection = useUrl.openConnection();
            useEncoding = useEncoding == null ? StandardCharsets.UTF_8 : useEncoding;
            if (useContent == CONTENT_UNSET) {
               if (isCharacterBased(useFileSystemContext, language, mimeType)) {
                  useContent = read(new InputStreamReader(connection.getInputStream(), useEncoding));
               } else {
                  useContent = ByteSequence.create(readBytes(connection));
               }
            }
         }
      }

      if (useName == null) {
         useName = "Unnamed";
      }

      useContent = enforceInterfaceContracts(useContent);
      String relativePathInLanguageHome = null;
      if (useTruffleFile != null) {
         relativePathInLanguageHome = SourceAccessor.ACCESSOR.engineSupport().getRelativePathInLanguageHome(useTruffleFile);
         if (relativePathInLanguageHome != null) {
            Object fsEngineObject = SourceAccessor.ACCESSOR
               .languageSupport()
               .getFileSystemEngineObject(SourceAccessor.ACCESSOR.languageSupport().getFileSystemContext(useTruffleFile));
            if (SourceAccessor.ACCESSOR.engineSupport().inContextPreInitialization(fsEngineObject)) {
               SourceImpl.Key key = new SourceImpl.ReinitializableKey(
                  useTruffleFile,
                  useContent,
                  useMimeType,
                  language,
                  useUrl,
                  useUri,
                  useName,
                  usePath,
                  internal,
                  interactive,
                  cached,
                  relativePathInLanguageHome,
                  embedderSource
               );
               Source source = SOURCES.intern(key);
               SourceAccessor.onSourceCreated(source);
               return source;
            }
         }
      }

      SourceImpl.Key key = new SourceImpl.ImmutableKey(
         useContent, useMimeType, language, useUrl, useUri, useName, usePath, internal, interactive, cached, relativePathInLanguageHome, embedderSource
      );
      return SOURCES.intern(key);
   }

   private static TruffleFile getCanonicalFileIfItExists(TruffleFile file) throws IOException {
      if (file.exists()) {
         try {
            return file.getCanonicalFile();
         } catch (NoSuchFileException var2) {
         }
      }

      return file;
   }

   static byte[] readBytes(URLConnection connection) throws IOException {
      long size = connection.getContentLengthLong();
      if (size < 0L) {
         size = 8192L;
      } else if (size > 2147483647L) {
         throw new OutOfMemoryError("Too many bytes.");
      }

      byte[] var4;
      try (InputStream inputStream = connection.getInputStream()) {
         var4 = readBytes(inputStream, (int)size);
      }

      return var4;
   }

   private static byte[] readBytes(InputStream source, int initialSize) throws IOException {
      int capacity = initialSize;
      byte[] buf = new byte[initialSize];
      int nread = 0;

      while (true) {
         int n;
         while ((n = source.read(buf, nread, capacity - nread)) > 0) {
            nread += n;
         }

         if (n < 0 || (n = source.read()) < 0) {
            return capacity == nread ? buf : Arrays.copyOf(buf, nread);
         }

         if (capacity <= 2147483639 - capacity) {
            capacity = Math.max(capacity << 1, 8192);
         } else {
            if (capacity == 2147483639) {
               throw new OutOfMemoryError("Required array size too large");
            }

            capacity = 2147483639;
         }

         buf = Arrays.copyOf(buf, capacity);
         buf[nread++] = (byte)n;
      }
   }

   static String read(TruffleFile file, Charset encoding) throws IOException {
      return new String(file.readAllBytes(), encoding);
   }

   static String read(Reader reader) throws IOException {
      StringBuilder builder = new StringBuilder();
      char[] buffer = new char[1024];

      try {
         while (true) {
            int n = reader.read(buffer);
            if (n == -1) {
               return builder.toString();
            }

            builder.append(buffer, 0, n);
         }
      } finally {
         reader.close();
      }
   }

   private static String digest(byte[] message, int from, int length) {
      try {
         MessageDigest md = MessageDigest.getInstance("SHA-256");
         md.update(message, from, length);
         byte[] digest = md.digest();
         StringBuilder result = new StringBuilder();

         for (int i = 0; i < digest.length; i++) {
            String hex = Integer.toHexString(255 & digest[i]);
            if (hex.length() == 1) {
               result.append('0');
            }

            result.append(hex);
         }

         return result.toString();
      } catch (NoSuchAlgorithmException var8) {
         throw new AssertionError("The message digest algorithm SHA-256 is not supported.", var8);
      }
   }

   static <E extends Exception> E raise(Class<E> type, Exception ex) throws E {
      throw ex;
   }

   static Object enforceInterfaceContracts(Object sequence) {
      boolean assertions = false;
      if (!$assertionsDisabled) {
         assertions = true;
         if (false) {
            throw new AssertionError();
         }
      }

      if (assertions) {
         if (sequence instanceof CharSequence) {
            return enforceCharSequenceContracts((CharSequence)sequence);
         }

         if (sequence != null) {
            assert sequence instanceof ByteSequence;

            return enforceByteSequenceContracts((ByteSequence)sequence);
         }
      }

      return sequence;
   }

   static ByteSequence enforceByteSequenceContracts(ByteSequence sequence) {
      if (BYTE_SEQUENCE_CLASS.isInstance(sequence)) {
         return sequence;
      } else {
         return (ByteSequence)(sequence instanceof ByteSequenceWrapper ? sequence : new ByteSequenceWrapper(sequence));
      }
   }

   static CharSequence enforceCharSequenceContracts(CharSequence sequence) {
      if (sequence instanceof String) {
         return sequence;
      } else {
         return (CharSequence)(sequence instanceof CharSequenceWrapper ? sequence : new CharSequenceWrapper(sequence));
      }
   }

   static String findMimeType(final URL url, URLConnection connection, Set<String> validMimeTypes, Object fileSystemContext) {
      try {
         URI uri = url.toURI();
         TruffleFile file = SourceAccessor.getTruffleFile(uri, fileSystemContext);
         String firstGuess = SourceAccessor.detectMimeType(file, validMimeTypes);
         if (firstGuess != null) {
            return firstGuess;
         }
      } catch (IllegalArgumentException | UnsupportedOperationException | URISyntaxException var7) {
      }

      if (ALLOW_IO && SourceAccessor.hasAllAccess(fileSystemContext)) {
         String contentType = connection.getContentType();
         return contentType == null || validMimeTypes != null && !validMimeTypes.contains(contentType) ? null : contentType;
      } else {
         throw new SecurityException("Reading of URL " + url + " is not allowed.");
      }
   }

   static boolean isCharacterBased(Object fileSystemContext, String language, String mimeType) {
      Object engineObject = SourceAccessor.LANGUAGE.getFileSystemEngineObject(fileSystemContext);
      return SourceAccessor.ACCESSOR.engineSupport().isCharacterBasedSource(engineObject, language, mimeType);
   }

   static Set<String> getValidMimeTypes(Object fileSystemContext, String language) {
      Accessor.EngineSupport support = SourceAccessor.ACCESSOR.engineSupport();
      return support == null ? null : support.getValidMimeTypes(SourceAccessor.LANGUAGE.getFileSystemEngineObject(fileSystemContext), language);
   }

   private static void validateMimeType(String mimeType) {
      if (mimeType != null) {
         int index = mimeType.indexOf(47);
         if (index == -1 || index == 0 || index == mimeType.length() - 1) {
            throw invalidMimeType();
         } else if (mimeType.indexOf(47, index + 1) != -1) {
            throw invalidMimeType();
         }
      }
   }

   static <E extends Exception> RuntimeException silenceException(Class<E> type, Exception ex) throws E {
      throw ex;
   }

   private static Charset findEncoding(TruffleFile file, String mimeType) {
      Charset encoding = mimeType == null ? null : SourceAccessor.detectEncoding(file, mimeType);
      return encoding == null ? StandardCharsets.UTF_8 : encoding;
   }

   private static Object getSourceContent(Source source) {
      Object content = ((SourceImpl)source).toKey().content;
      return content == CONTENT_NONE ? CONTENT_UNSET : content;
   }

   private static void resetNativeImageState() {
      SOURCES.resetNativeImageState();
   }

   static {
      SourceAccessor.load();
   }

   public final class LiteralBuilder extends Source.SourceBuilder {
      private boolean buildThrowsIOException;

      LiteralBuilder(String language, Object origin, boolean originReadingThrows) {
         super(language, origin);
         this.buildThrowsIOException = originReadingThrows;
      }

      LiteralBuilder(Source source) {
         super(source.getLanguage(), Source.getSourceContent(source));
         this.cached(source.isCached());
         this.interactive(source.isInteractive());
         this.internal(source.isInternal());
         this.mimeType(source.getMimeType());
         this.name(source.getName());
         this.uri(((SourceImpl)source).toKey().getURI());
         this.path = source.getPath();
         this.url = source.getURL();
         this.buildThrowsIOException = false;
      }

      @Override
      public Source.LiteralBuilder content(CharSequence characters) {
         this.buildThrowsIOException = false;
         return super.content(characters);
      }

      @Override
      public Source.LiteralBuilder content(ByteSequence bytes) {
         this.buildThrowsIOException = false;
         return super.content(bytes);
      }

      public Source.LiteralBuilder name(String newName) {
         return (Source.LiteralBuilder)super.name(newName);
      }

      public Source.LiteralBuilder mimeType(String newMimeType) {
         return (Source.LiteralBuilder)super.mimeType(newMimeType);
      }

      public Source.LiteralBuilder cached(boolean cached) {
         return (Source.LiteralBuilder)super.cached(cached);
      }

      public Source.LiteralBuilder internal(boolean enabled) {
         return (Source.LiteralBuilder)super.internal(enabled);
      }

      public Source.LiteralBuilder interactive(boolean enabled) {
         return (Source.LiteralBuilder)super.interactive(enabled);
      }

      public Source.LiteralBuilder uri(URI ownUri) {
         return (Source.LiteralBuilder)super.uri(ownUri);
      }

      public Source.LiteralBuilder canonicalizePath(boolean canonicalize) {
         return (Source.LiteralBuilder)super.canonicalizePath(canonicalize);
      }

      public Source.LiteralBuilder encoding(Charset encoding) {
         return (Source.LiteralBuilder)super.encoding(encoding);
      }

      @Override
      public Source build() {
         try {
            return super.build();
         } catch (IOException var2) {
            if (this.buildThrowsIOException) {
               throw Source.silenceException(RuntimeException.class, var2);
            } else {
               throw new AssertionError("Unexpected IOException", var2);
            }
         }
      }
   }

   public class SourceBuilder {
      private final String language;
      private final Object origin;
      private URI uri;
      URL url;
      private String name;
      String path;
      private boolean canonicalizePath = true;
      private String mimeType;
      private Object content = Source.CONTENT_UNSET;
      private boolean internal;
      private boolean interactive;
      private boolean cached = true;
      private Charset fileEncoding;
      private Object fileSystemContext;
      private boolean embedderSource;

      SourceBuilder(String language, Object origin) {
         Objects.requireNonNull(language);
         Objects.requireNonNull(origin);
         this.language = language;
         this.origin = origin;
      }

      public Source.SourceBuilder name(String newName) {
         this.name = newName;
         return this;
      }

      public Source.LiteralBuilder content(CharSequence characters) {
         this.content = characters;
         return (Source.LiteralBuilder)this;
      }

      public Source.LiteralBuilder content(ByteSequence bytes) {
         this.content = bytes;
         return (Source.LiteralBuilder)this;
      }

      public Source.SourceBuilder mimeType(String mimeType) {
         Source.validateMimeType(mimeType);
         this.mimeType = mimeType;
         return this;
      }

      public Source.SourceBuilder cached(boolean enabled) {
         this.cached = enabled;
         return this;
      }

      public Source.SourceBuilder internal(boolean enabled) {
         this.internal = enabled;
         return this;
      }

      public Source.SourceBuilder interactive(boolean enabled) {
         this.interactive = enabled;
         return this;
      }

      public Source.SourceBuilder uri(URI ownUri) {
         this.uri = ownUri;
         return this;
      }

      public Source.SourceBuilder canonicalizePath(boolean canonicalize) {
         this.canonicalizePath = canonicalize;
         return this;
      }

      public Source.SourceBuilder encoding(Charset encoding) {
         this.fileEncoding = encoding;
         return this;
      }

      Source.SourceBuilder fileSystemContext(Object context) {
         this.fileSystemContext = context;
         return this;
      }

      void embedderSource(boolean b) {
         this.embedderSource = b;
      }

      void url(URL url) {
         this.url = url;
      }

      void path(String path) {
         this.path = path;
      }

      public Source build() throws IOException {
         assert this.language != null;

         Source source = Source.buildSource(
            this.language,
            this.origin,
            this.name,
            this.path,
            this.canonicalizePath,
            this.mimeType,
            this.content,
            this.url,
            this.uri,
            this.fileEncoding,
            this.internal,
            this.interactive,
            this.cached,
            this.fileSystemContext,
            this.embedderSource
         );
         if (source.hasBytes()) {
            this.content = source.getBytes();
         } else if (source.hasCharacters()) {
            this.content = source.getCharacters();
         }

         assert source.getName() != null;

         assert !source.hasCharacters() || source.getCharacters() != null;

         assert !source.hasBytes() || source.getBytes() != null;

         assert source.getLanguage() != null;

         return source;
      }
   }
}
