
package com.oracle.truffle.api.source;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.impl.Accessor;
import com.oracle.truffle.api.source.ByteSequenceWrapper;
import com.oracle.truffle.api.source.CharSequenceReader;
import com.oracle.truffle.api.source.CharSequenceWrapper;
import com.oracle.truffle.api.source.InternedSources;
import com.oracle.truffle.api.source.SourceAccessor;
import com.oracle.truffle.api.source.SourceImpl;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.source.SourceSectionLoaded;
import com.oracle.truffle.api.source.SourceSectionUnavailable;
import com.oracle.truffle.api.source.SourceSectionUnloaded;
import com.oracle.truffle.api.source.SubSourceImpl;
import com.oracle.truffle.api.source.TextMap;
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
import java.nio.file.LinkOption;
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
    private static final Source EMPTY = new SourceImpl.ImmutableKey(null, null, null, null, null, null, null, false, false, false, null, false).toSourceNotInterned();
    private static final String NO_FASTPATH_SUBSOURCE_CREATION_MESSAGE = "do not create sub sources from compiled code";
    private static final String URI_SCHEME = "truffle";
    private static final int MAX_BUFFER_SIZE = 0x7FFFFFF7;
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

    public final boolean equals(Object obj) {
        if (!(obj instanceof Source)) {
            return false;
        }
        boolean result = this.getSourceId().equals(((Source)obj).getSourceId());
        assert (!result || this.getSourceKey().equals(((Source)obj).getSourceKey()));
        return result;
    }

    public final int hashCode() {
        return this.getSourceId().hashCode();
    }

    public Source subSource(int baseCharIndex, int length) {
        if (this.hasBytes()) {
            throw new UnsupportedOperationException("Operation is only enabled for character based sources.");
        }
        CompilerAsserts.neverPartOfCompilation(NO_FASTPATH_SUBSOURCE_CREATION_MESSAGE);
        return SubSourceImpl.create(this, baseCharIndex, length);
    }

    public abstract CharSequence getCharacters();

    public abstract boolean hasBytes();

    public abstract boolean hasCharacters();

    public abstract ByteSequence getBytes();

    public abstract URL getURL();

    abstract URI getOriginalURI();

    public final URI getURI() {
        URI uri = this.getOriginalURI();
        if (uri == null && (uri = this.computedURI) == null) {
            byte[] bytes = this.hasBytes() ? this.getBytes().toByteArray() : (this.hasCharacters() ? this.getCharacters().toString().getBytes() : CONTENT_EMPTY);
            uri = this.computedURI = this.getNamedURI(this.getName(), bytes);
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
        }
        if (this.hasBytes()) {
            return this.getBytes().length();
        }
        throw new UnsupportedOperationException("Operation is only enabled for sources with character or byte content.");
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
        }
        if (startLine < 1) {
            throw new IllegalArgumentException("lineNumber < 1");
        }
        if (startLine > endLine) {
            throw new IllegalArgumentException("startLine " + startLine + " > endLine " + endLine);
        }
        if (startLine == endLine && startColumn > endColumn) {
            throw new IllegalArgumentException("startColumn " + startColumn + " > endColumn " + endColumn);
        }
        if (this.hasCharacters()) {
            if (startColumn < 1 || endColumn < 1) {
                throw new IllegalArgumentException("columnNumber < 1");
            }
            int charIndex = this.getTextMap().lineColumnToOffset(startLine, startColumn);
            int endIndex = this.getTextMap().lineColumnToOffset(endLine, endColumn);
            assert (charIndex <= endIndex) : charIndex + " > " + endIndex;
            int length = endIndex + 1 - charIndex;
            int sourceLength = this.getTextMap().length();
            if (length == 1 && charIndex + length > sourceLength) {
                length = 0;
            }
            if (charIndex + length > sourceLength) {
                throw new IllegalArgumentException("end position out of range");
            }
            SourceSectionLoaded section = new SourceSectionLoaded(this, charIndex, length);
            assert (Source.assertValid(section));
            return section;
        }
        if (startColumn == -1) {
            if (endColumn != -1) {
                throw new IllegalArgumentException("endColumn can not be specified when startColumn is not.");
            }
            return new SourceSectionUnloaded.Lines(this, startLine, endLine);
        }
        if (startColumn < 1 || endColumn < 1) {
            throw new IllegalArgumentException("columnNumber < 1");
        }
        return new SourceSectionUnloaded.LinesAndColumns(this, startLine, startColumn, endLine, endColumn);
    }

    public final SourceSection createSection(int lineNumber) {
        SourceSection section;
        if (this.hasBytes()) {
            throw new UnsupportedOperationException("Operation is only enabled for character based sources.");
        }
        if (lineNumber < 1) {
            throw new IllegalArgumentException("lineNumber < 1");
        }
        if (this.hasCharacters()) {
            int charIndex = this.getTextMap().lineStartOffset(lineNumber);
            int length = this.getTextMap().lineLength(lineNumber);
            section = new SourceSectionLoaded(this, charIndex, length);
            assert (Source.assertValid(section));
        } else {
            section = new SourceSectionUnloaded.Lines(this, lineNumber, lineNumber);
        }
        return section;
    }

    public final SourceSection createSection(int charIndex, int length) {
        SourceSection section;
        if (this.hasBytes()) {
            throw new UnsupportedOperationException("Operation is only enabled for character based sources.");
        }
        if (charIndex < 0) {
            throw new IllegalArgumentException("charIndex < 0");
        }
        if (length < 0) {
            throw new IllegalArgumentException("length < 0");
        }
        if (this.hasCharacters()) {
            section = new SourceSectionLoaded(this, charIndex, length);
            assert (Source.assertValid(section));
        } else {
            section = new SourceSectionUnloaded.Indexed(this, charIndex, length);
        }
        return section;
    }

    public final SourceSection createSection(int startLine, int startColumn, int length) {
        if (this.hasBytes() || !this.hasCharacters()) {
            throw new UnsupportedOperationException("Operation is only enabled for character based sources.");
        }
        if (startLine <= 0) {
            throw new IllegalArgumentException("startLine < 1");
        }
        if (startColumn <= 0) {
            throw new IllegalArgumentException("startColumn < 1");
        }
        if (this.hasCharacters() && length < 0) {
            throw new IllegalArgumentException("length < 0");
        }
        int lineStartOffset = this.getTextMap().lineStartOffset(startLine);
        int lineLength = this.getTextMap().lineLength(startLine);
        if (startColumn > lineLength + 1) {
            throw new IllegalArgumentException("column out of range");
        }
        int charIndex = lineStartOffset + startColumn - 1;
        if (charIndex + length > this.getCharacters().length()) {
            throw new IllegalArgumentException("charIndex out of range");
        }
        SourceSectionLoaded section = new SourceSectionLoaded(this, charIndex, length);
        assert (Source.assertValid(section));
        return section;
    }

    public String toString() {
        return "Source [language=" + this.getLanguage() + ", name=" + this.getName() + ", path=" + this.getPath() + ", internal=" + this.isInternal() + ", cached=" + this.isCached() + ", interactive=" + this.isInteractive() + ", hasBytes=" + this.hasBytes() + ", hasCharacters=" + this.hasCharacters() + ", URL=" + this.getURL() + ", URI=" + this.getURI() + ", mimeType=" + this.getMimeType() + "]";
    }

    private static boolean assertValid(SourceSection section) {
        if (!section.isValid()) {
            throw new IllegalArgumentException("Invalid source section bounds.");
        }
        return true;
    }

    abstract Source copy();

    final TextMap getTextMap() {
        if (this.hasBytes()) {
            throw new UnsupportedOperationException("Operation is only enabled for character based sources.");
        }
        TextMap res = this.textMap;
        if (res == null) {
            res = this.textMap = this.createTextMap();
        }
        assert (res != null);
        return res;
    }

    TextMap createTextMap() {
        CharSequence code = this.getCharacters();
        if (code == null) {
            throw new RuntimeException("can't read file " + this.getName());
        }
        return TextMap.fromCharSequence(code);
    }

    private URI getNamedURI(String name, byte[] bytes) {
        return this.getNamedURI(name, bytes, 0, bytes.length);
    }

    private URI getNamedURI(String name, byte[] bytes, int byteIndex, int length) {
        Object digest = bytes != null ? Source.digest(bytes, byteIndex, length) : Integer.toString(System.identityHashCode(this), 16);
        if (name != null) {
            digest = (String)digest + "/" + name;
        }
        try {
            return new URI(URI_SCHEME, (String)digest, null);
        }
        catch (URISyntaxException ex) {
            throw new Error(ex);
        }
    }

    public static LiteralBuilder newBuilder(String language, CharSequence characters, String name) {
        Source source = EMPTY;
        Objects.requireNonNull(source);
        return source.new LiteralBuilder(language, characters, false).name(name);
    }

    public static LiteralBuilder newBuilder(String language, ByteSequence bytes, String name) {
        Source source = EMPTY;
        Objects.requireNonNull(source);
        return source.new LiteralBuilder(language, bytes, false).name(name);
    }

    public static SourceBuilder newBuilder(String language, TruffleFile file) {
        Source source = EMPTY;
        Objects.requireNonNull(source);
        return source.new LiteralBuilder(language, file, true);
    }

    static SourceBuilder newBuilder(String language, File source) {
        Source source2 = EMPTY;
        Objects.requireNonNull(source2);
        return source2.new LiteralBuilder(language, source, true);
    }

    public static SourceBuilder newBuilder(String language, URL url) {
        Source source = EMPTY;
        Objects.requireNonNull(source);
        return source.new LiteralBuilder(language, url, true);
    }

    public static SourceBuilder newBuilder(String language, Reader source, String name) {
        Source source2 = EMPTY;
        Objects.requireNonNull(source2);
        return source2.new LiteralBuilder(language, source, true).name(name);
    }

    public static LiteralBuilder newBuilder(Source source) {
        Source source2 = EMPTY;
        Objects.requireNonNull(source2);
        return source2.new LiteralBuilder(source);
    }

    public static String findLanguage(TruffleFile file) throws IOException {
        String mimeType = Source.findMimeType(file);
        return mimeType != null ? Source.findLanguage(mimeType) : null;
    }

    public static String findLanguage(URL url) throws IOException {
        String mimeType = Source.findMimeType(url);
        return mimeType != null ? Source.findLanguage(mimeType) : null;
    }

    public static String findMimeType(TruffleFile file) throws IOException {
        return file.detectMimeType();
    }

    public static String findMimeType(URL url) throws IOException {
        return Source.findMimeType(url, url.openConnection(), null, SourceAccessor.ACCESSOR.engineSupport().getCurrentFileSystemContext());
    }

    public static String findLanguage(String mimeType) {
        return org.graalvm.polyglot.Source.findLanguage(mimeType);
    }

    private static IllegalArgumentException invalidMimeType() {
        return new IllegalArgumentException("Invalid MIME type provided. MIME types consist of a type and a subtype separated by '/'.");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static Source buildSource(String language, Object origin, String name, String path, boolean canonicalizePath, String mimeType, Object content, URL url, URI uri, Charset encoding, boolean internal, boolean interactive, boolean cached, Object fileSystemContext, boolean embedderSource) throws IOException {
        TruffleFile useTruffleFile;
        URL useUrl;
        String usePath;
        String useMimeType;
        Object useContent;
        URI useUri;
        String useName;
        block24: {
            Object useFileSystemContext;
            Charset useEncoding;
            Object useOrigin;
            block26: {
                block25: {
                    useName = name;
                    useUri = uri;
                    useContent = content;
                    useMimeType = mimeType;
                    usePath = path;
                    useUrl = url;
                    useOrigin = origin;
                    useEncoding = encoding;
                    useTruffleFile = null;
                    useFileSystemContext = fileSystemContext;
                    if (useOrigin instanceof File) {
                        File file = (File)useOrigin;
                        assert (useFileSystemContext != null) : "file system context must be provided by polyglot embedding API";
                        TruffleFile truffleFile = SourceAccessor.getTruffleFile(file.toPath().toString(), useFileSystemContext);
                        useOrigin = truffleFile;
                    }
                    if (useOrigin != CONTENT_UNSET) break block25;
                    useContent = useContent == CONTENT_UNSET ? null : useContent;
                    break block24;
                }
                if (!(useOrigin instanceof TruffleFile)) break block26;
                useTruffleFile = (TruffleFile)useOrigin;
                if (!canonicalizePath || useContent == CONTENT_NONE) {
                    if (useUri == null) {
                        useUri = useTruffleFile.isAbsolute() ? useTruffleFile.toUri() : useTruffleFile.toRelativeUri();
                    }
                } else {
                    useTruffleFile = Source.getCanonicalFileIfItExists(useTruffleFile);
                }
                useFileSystemContext = SourceAccessor.LANGUAGE.getFileSystemContext(useTruffleFile);
                useName = useName == null ? useTruffleFile.getName() : useName;
                usePath = usePath == null ? useTruffleFile.getPath() : usePath;
                useUri = useUri == null ? useTruffleFile.toUri() : useUri;
                String string = useMimeType = useMimeType == null ? SourceAccessor.detectMimeType(useTruffleFile, Source.getValidMimeTypes(useFileSystemContext, language)) : useMimeType;
                if (useContent == CONTENT_UNSET) {
                    if (Source.isCharacterBased(useFileSystemContext, language, useMimeType)) {
                        useEncoding = useEncoding == null ? Source.findEncoding(useTruffleFile, useMimeType) : useEncoding;
                        useContent = Source.read(useTruffleFile, useEncoding);
                        break block24;
                    } else {
                        useContent = ByteSequence.create(useTruffleFile.readAllBytes());
                    }
                }
                break block24;
            }
            if (useOrigin instanceof URL) {
                URI tmpUri;
                useUrl = (URL)useOrigin;
                String urlPath = useUrl.getPath();
                int lastIndex = urlPath.lastIndexOf(47);
                useName = useName == null && lastIndex != -1 ? useUrl.getPath().substring(lastIndex + 1) : useName;
                try {
                    tmpUri = useUrl.toURI();
                }
                catch (URISyntaxException ex) {
                    throw new IOException("Bad URL: " + useUrl, ex);
                }
                useUri = useUri == null ? tmpUri : useUri;
                usePath = usePath == null ? useUrl.getPath() : usePath;
                useFileSystemContext = useFileSystemContext == null ? SourceAccessor.ACCESSOR.engineSupport().getCurrentFileSystemContext() : useFileSystemContext;
                try {
                    useTruffleFile = SourceAccessor.getTruffleFile(tmpUri, useFileSystemContext);
                    useTruffleFile = Source.getCanonicalFileIfItExists(useTruffleFile);
                    if (useContent != CONTENT_UNSET) break block24;
                    if (Source.isCharacterBased(useFileSystemContext, language, useMimeType)) {
                        String fileMimeType = useMimeType == null ? SourceAccessor.detectMimeType(useTruffleFile, Source.getValidMimeTypes(useFileSystemContext, language)) : useMimeType;
                        useEncoding = useEncoding == null ? Source.findEncoding(useTruffleFile, fileMimeType) : useEncoding;
                        useContent = Source.read(useTruffleFile, useEncoding);
                        break block24;
                    }
                    useContent = ByteSequence.create(useTruffleFile.readAllBytes());
                }
                catch (UnsupportedOperationException uoe) {
                    if (!ALLOW_IO) throw new SecurityException("Reading of URL " + useUrl + " is not allowed.");
                    if (!SourceAccessor.hasAllAccess(useFileSystemContext)) throw new SecurityException("Reading of URL " + useUrl + " is not allowed.");
                    URLConnection connection = useUrl.openConnection();
                    Charset charset = useEncoding = useEncoding == null ? StandardCharsets.UTF_8 : useEncoding;
                    if (useContent != CONTENT_UNSET) break block24;
                    if (Source.isCharacterBased(useFileSystemContext, language, useMimeType)) {
                        useContent = Source.read(new InputStreamReader(connection.getInputStream(), useEncoding));
                        break block24;
                    }
                    useContent = ByteSequence.create(Source.readBytes(connection));
                }
            } else if (useOrigin instanceof Reader) {
                Reader r = (Reader)useOrigin;
                useContent = useContent == CONTENT_UNSET ? Source.read(r) : useContent;
            } else if (useOrigin instanceof ByteSequence) {
                useContent = useContent == CONTENT_UNSET ? useOrigin : useContent;
            } else {
                assert (useOrigin instanceof CharSequence);
                Object object = useContent = useContent == CONTENT_UNSET ? useOrigin : useContent;
            }
        }
        if (useName == null) {
            useName = "Unnamed";
        }
        useContent = Source.enforceInterfaceContracts(useContent);
        String relativePathInLanguageHome = null;
        if (useTruffleFile != null && (relativePathInLanguageHome = SourceAccessor.ACCESSOR.engineSupport().getRelativePathInLanguageHome(useTruffleFile)) != null) {
            Object fsEngineObject = SourceAccessor.ACCESSOR.languageSupport().getFileSystemEngineObject(SourceAccessor.ACCESSOR.languageSupport().getFileSystemContext(useTruffleFile));
            if (SourceAccessor.ACCESSOR.engineSupport().inContextPreInitialization(fsEngineObject)) {
                SourceImpl.ReinitializableKey key = new SourceImpl.ReinitializableKey(useTruffleFile, useContent, useMimeType, language, useUrl, useUri, useName, usePath, internal, interactive, cached, relativePathInLanguageHome, embedderSource);
                Source source = SOURCES.intern(key);
                SourceAccessor.onSourceCreated(source);
                return source;
            }
        }
        SourceImpl.ImmutableKey key = new SourceImpl.ImmutableKey(useContent, useMimeType, language, useUrl, useUri, useName, usePath, internal, interactive, cached, relativePathInLanguageHome, embedderSource);
        return SOURCES.intern(key);
    }

    private static TruffleFile getCanonicalFileIfItExists(TruffleFile file) throws IOException {
        if (file.exists(new LinkOption[0])) {
            try {
                return file.getCanonicalFile(new LinkOption[0]);
            }
            catch (NoSuchFileException noSuchFileException) {
                // empty catch block
            }
        }
        return file;
    }

    static byte[] readBytes(URLConnection connection) throws IOException {
        long size = connection.getContentLengthLong();
        if (size < 0L) {
            size = 8192L;
        } else if (size > Integer.MAX_VALUE) {
            throw new OutOfMemoryError("Too many bytes.");
        }
        try (InputStream inputStream = connection.getInputStream();){
            byte[] byArray = Source.readBytes(inputStream, (int)size);
            return byArray;
        }
    }

    private static byte[] readBytes(InputStream source, int initialSize) throws IOException {
        int capacity = initialSize;
        byte[] buf = new byte[capacity];
        int nread = 0;
        while (true) {
            int n;
            if ((n = source.read(buf, nread, capacity - nread)) > 0) {
                nread += n;
                continue;
            }
            if (n < 0 || (n = source.read()) < 0) break;
            if (capacity <= 0x7FFFFFF7 - capacity) {
                capacity = Math.max(capacity << 1, 8192);
            } else {
                if (capacity == 0x7FFFFFF7) {
                    throw new OutOfMemoryError("Required array size too large");
                }
                capacity = 0x7FFFFFF7;
            }
            buf = Arrays.copyOf(buf, capacity);
            buf[nread++] = (byte)n;
        }
        return capacity == nread ? buf : Arrays.copyOf(buf, nread);
    }

    static String read(TruffleFile file, Charset encoding) throws IOException {
        return new String(file.readAllBytes(), encoding);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static String read(Reader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[1024];
        try {
            int n;
            while ((n = reader.read(buffer)) != -1) {
                builder.append(buffer, 0, n);
            }
        }
        finally {
            reader.close();
        }
        return builder.toString();
    }

    private static String digest(byte[] message, int from, int length) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(message, from, length);
            byte[] digest = md.digest();
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < digest.length; ++i) {
                String hex = Integer.toHexString(0xFF & digest[i]);
                if (hex.length() == 1) {
                    result.append('0');
                }
                result.append(hex);
            }
            return result.toString();
        }
        catch (NoSuchAlgorithmException e) {
            throw new AssertionError("The message digest algorithm SHA-256 is not supported.", e);
        }
    }

    static <E extends Exception> E raise(Class<E> type, Exception ex) throws E {
        throw ex;
    }

    static Object enforceInterfaceContracts(Object sequence) {
        boolean assertions = false;
        if (!$assertionsDisabled) {
            assertions = true;
            if (!true) {
                throw new AssertionError();
            }
        }
        if (assertions) {
            if (sequence instanceof CharSequence) {
                return Source.enforceCharSequenceContracts((CharSequence)sequence);
            }
            if (sequence != null) {
                assert (sequence instanceof ByteSequence);
                return Source.enforceByteSequenceContracts((ByteSequence)sequence);
            }
        }
        return sequence;
    }

    static ByteSequence enforceByteSequenceContracts(ByteSequence sequence) {
        if (BYTE_SEQUENCE_CLASS.isInstance(sequence)) {
            return sequence;
        }
        if (sequence instanceof ByteSequenceWrapper) {
            return sequence;
        }
        return new ByteSequenceWrapper(sequence);
    }

    static CharSequence enforceCharSequenceContracts(CharSequence sequence) {
        if (sequence instanceof String) {
            return sequence;
        }
        if (sequence instanceof CharSequenceWrapper) {
            return sequence;
        }
        return new CharSequenceWrapper(sequence);
    }

    static String findMimeType(URL url, URLConnection connection, Set<String> validMimeTypes, Object fileSystemContext) {
        try {
            URI uri = url.toURI();
            TruffleFile file = SourceAccessor.getTruffleFile(uri, fileSystemContext);
            String firstGuess = SourceAccessor.detectMimeType(file, validMimeTypes);
            if (firstGuess != null) {
                return firstGuess;
            }
        }
        catch (IllegalArgumentException | UnsupportedOperationException | URISyntaxException uri) {
            // empty catch block
        }
        if (!ALLOW_IO || !SourceAccessor.hasAllAccess(fileSystemContext)) {
            throw new SecurityException("Reading of URL " + url + " is not allowed.");
        }
        String contentType = connection.getContentType();
        if (contentType != null && (validMimeTypes == null || validMimeTypes.contains(contentType))) {
            return contentType;
        }
        return null;
    }

    static boolean isCharacterBased(Object fileSystemContext, String language, String mimeType) {
        Object engineObject = SourceAccessor.LANGUAGE.getFileSystemEngineObject(fileSystemContext);
        return SourceAccessor.ACCESSOR.engineSupport().isCharacterBasedSource(engineObject, language, mimeType);
    }

    static Set<String> getValidMimeTypes(Object fileSystemContext, String language) {
        Accessor.EngineSupport support = SourceAccessor.ACCESSOR.engineSupport();
        if (support == null) {
            return null;
        }
        return support.getValidMimeTypes(SourceAccessor.LANGUAGE.getFileSystemEngineObject(fileSystemContext), language);
    }

    private static void validateMimeType(String mimeType) {
        if (mimeType == null) {
            return;
        }
        int index = mimeType.indexOf(47);
        if (index == -1 || index == 0 || index == mimeType.length() - 1) {
            throw Source.invalidMimeType();
        }
        if (mimeType.indexOf(47, index + 1) != -1) {
            throw Source.invalidMimeType();
        }
    }

    static <E extends Exception> RuntimeException silenceException(Class<E> type, Exception ex) throws E {
        throw ex;
    }

    private static Charset findEncoding(TruffleFile file, String mimeType) {
        Charset encoding = mimeType == null ? null : SourceAccessor.detectEncoding(file, mimeType);
        encoding = encoding == null ? StandardCharsets.UTF_8 : encoding;
        return encoding;
    }

    private static Object getSourceContent(Source source) {
        Object content = ((SourceImpl)source).toKey().content;
        if (content == CONTENT_NONE) {
            return CONTENT_UNSET;
        }
        return content;
    }

    private static void resetNativeImageState() {
        SOURCES.resetNativeImageState();
    }

    static {
        SourceAccessor.load();
    }

    public final class LiteralBuilder
    extends SourceBuilder {
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
        public LiteralBuilder content(CharSequence characters) {
            this.buildThrowsIOException = false;
            return super.content(characters);
        }

        @Override
        public LiteralBuilder content(ByteSequence bytes) {
            this.buildThrowsIOException = false;
            return super.content(bytes);
        }

        @Override
        public LiteralBuilder name(String newName) {
            return (LiteralBuilder)super.name(newName);
        }

        @Override
        public LiteralBuilder mimeType(String newMimeType) {
            return (LiteralBuilder)super.mimeType(newMimeType);
        }

        @Override
        public LiteralBuilder cached(boolean cached) {
            return (LiteralBuilder)super.cached(cached);
        }

        @Override
        public LiteralBuilder internal(boolean enabled) {
            return (LiteralBuilder)super.internal(enabled);
        }

        @Override
        public LiteralBuilder interactive(boolean enabled) {
            return (LiteralBuilder)super.interactive(enabled);
        }

        @Override
        public LiteralBuilder uri(URI ownUri) {
            return (LiteralBuilder)super.uri(ownUri);
        }

        @Override
        public LiteralBuilder canonicalizePath(boolean canonicalize) {
            return (LiteralBuilder)super.canonicalizePath(canonicalize);
        }

        @Override
        public LiteralBuilder encoding(Charset encoding) {
            return (LiteralBuilder)super.encoding(encoding);
        }

        @Override
        public Source build() {
            try {
                return super.build();
            }
            catch (IOException e) {
                if (this.buildThrowsIOException) {
                    throw Source.silenceException(RuntimeException.class, e);
                }
                throw new AssertionError("Unexpected IOException", e);
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
        private Object content = CONTENT_UNSET;
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

        public SourceBuilder name(String newName) {
            this.name = newName;
            return this;
        }

        public LiteralBuilder content(CharSequence characters) {
            this.content = characters;
            return (LiteralBuilder)this;
        }

        public LiteralBuilder content(ByteSequence bytes) {
            this.content = bytes;
            return (LiteralBuilder)this;
        }

        public SourceBuilder mimeType(String mimeType) {
            Source.validateMimeType(mimeType);
            this.mimeType = mimeType;
            return this;
        }

        public SourceBuilder cached(boolean enabled) {
            this.cached = enabled;
            return this;
        }

        public SourceBuilder internal(boolean enabled) {
            this.internal = enabled;
            return this;
        }

        public SourceBuilder interactive(boolean enabled) {
            this.interactive = enabled;
            return this;
        }

        public SourceBuilder uri(URI ownUri) {
            this.uri = ownUri;
            return this;
        }

        public SourceBuilder canonicalizePath(boolean canonicalize) {
            this.canonicalizePath = canonicalize;
            return this;
        }

        public SourceBuilder encoding(Charset encoding) {
            this.fileEncoding = encoding;
            return this;
        }

        SourceBuilder fileSystemContext(Object context) {
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
            assert (this.language != null);
            Source source = Source.buildSource(this.language, this.origin, this.name, this.path, this.canonicalizePath, this.mimeType, this.content, this.url, this.uri, this.fileEncoding, this.internal, this.interactive, this.cached, this.fileSystemContext, this.embedderSource);
            if (source.hasBytes()) {
                this.content = source.getBytes();
            } else if (source.hasCharacters()) {
                this.content = source.getCharacters();
            }
            assert (source.getName() != null);
            assert (!source.hasCharacters() || source.getCharacters() != null);
            assert (!source.hasBytes() || source.getBytes() != null);
            assert (source.getLanguage() != null);
            return source;
        }
    }
}

