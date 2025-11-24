
package org.graalvm.polyglot.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Executable;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URL;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.graalvm.collections.UnmodifiableEconomicMap;
import org.graalvm.collections.UnmodifiableEconomicSet;
import org.graalvm.options.OptionDescriptors;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.EnvironmentAccess;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Instrument;
import org.graalvm.polyglot.Language;
import org.graalvm.polyglot.PolyglotAccess;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.ResourceLimitEvent;
import org.graalvm.polyglot.ResourceLimits;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.SourceSection;
import org.graalvm.polyglot.TypeLiteral;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.io.ByteSequence;
import org.graalvm.polyglot.io.FileSystem;
import org.graalvm.polyglot.io.MessageTransport;
import org.graalvm.polyglot.io.ProcessHandler;
import org.graalvm.polyglot.management.ExecutionEvent;
import org.graalvm.polyglot.management.ExecutionListener;

public abstract class AbstractPolyglotImpl {
    private APIAccess api;
    private ManagementAccess management;
    private IOAccess io;
    private AbstractPolyglotImpl next;
    private AbstractPolyglotImpl prev;

    protected AbstractPolyglotImpl() {
    }

    public final void setMonitoring(ManagementAccess monitoring) {
        this.management = monitoring;
        AbstractPolyglotImpl nextImpl = this.next;
        if (nextImpl != null) {
            nextImpl.setMonitoring(monitoring);
        }
    }

    public final void setConstructors(APIAccess constructors) {
        this.api = constructors;
        this.initialize();
    }

    public final void setNext(AbstractPolyglotImpl next) {
        this.next = next;
        if (next != null) {
            next.prev = this;
        }
    }

    public final AbstractPolyglotImpl getNext() {
        if (this.next == null) {
            throw new AbstractMethodError("No implementation available.");
        }
        return this.next;
    }

    public final void setIO(IOAccess ioAccess) {
        Objects.requireNonNull(ioAccess, "IOAccess must be non null.");
        this.io = ioAccess;
        AbstractPolyglotImpl nextImpl = this.next;
        if (nextImpl != null) {
            nextImpl.setIO(ioAccess);
        }
    }

    public final APIAccess getAPIAccess() {
        return this.api;
    }

    public final ManagementAccess getManagement() {
        return this.management;
    }

    public final IOAccess getIO() {
        if (this.io == null) {
            try {
                Class.forName("org.graalvm.polyglot.io.IOHelper", true, this.getClass().getClassLoader());
            }
            catch (ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }
        }
        return this.io;
    }

    protected void initialize() {
    }

    public Engine buildEngine(String[] permittedLanguages, OutputStream out, OutputStream err, InputStream in, Map<String, String> options, boolean useSystemProperties, boolean allowExperimentalOptions, boolean boundEngine, MessageTransport messageInterceptor, Object logHandlerOrStream, Object hostLanguage, boolean hostLanguageOnly, boolean registerInActiveEngines, AbstractPolyglotHostService polyglotHostService) {
        return this.getNext().buildEngine(permittedLanguages, out, err, in, options, useSystemProperties, allowExperimentalOptions, boundEngine, messageInterceptor, logHandlerOrStream, hostLanguage, hostLanguageOnly, registerInActiveEngines, polyglotHostService);
    }

    public abstract int getPriority();

    public void preInitializeEngine(Object hostLanguage) {
        this.getNext().preInitializeEngine(hostLanguage);
    }

    public Object createHostLanguage(AbstractHostAccess access) {
        return this.getNext().createHostLanguage(access);
    }

    public void resetPreInitializedEngine() {
        this.getNext().resetPreInitializedEngine();
    }

    public Source build(String language, Object origin, URI uri, String name, String mimeType, Object content, boolean interactive, boolean internal, boolean cached, Charset encoding, URL url, String path) throws IOException {
        return this.getNext().build(language, origin, uri, name, mimeType, content, interactive, internal, cached, encoding, url, path);
    }

    public String findLanguage(File file) throws IOException {
        return this.getNext().findLanguage(file);
    }

    public String findLanguage(URL url) throws IOException {
        return this.getNext().findLanguage(url);
    }

    public String findLanguage(String mimeType) {
        return this.getNext().findLanguage(mimeType);
    }

    public String findMimeType(File file) throws IOException {
        return this.getNext().findMimeType(file);
    }

    public String findMimeType(URL url) throws IOException {
        return this.getNext().findMimeType(url);
    }

    public AbstractHostAccess createHostAccess() {
        return this.getNext().createHostAccess();
    }

    public Class<?> loadLanguageClass(String className) {
        return this.getNext().loadLanguageClass(className);
    }

    public Context getCurrentContext() {
        return this.getNext().getCurrentContext();
    }

    public Value asValue(Object o) {
        return this.getNext().asValue(o);
    }

    public <S, T> Object newTargetTypeMapping(Class<S> sourceType, Class<T> targetType, Predicate<S> acceptsValue, Function<S, T> convertValue, HostAccess.TargetMappingPrecedence precedence) {
        return this.getNext().newTargetTypeMapping(sourceType, targetType, acceptsValue, convertValue, precedence);
    }

    public Object buildLimits(long statementLimit, Predicate<Source> statementLimitSourceFilter, Consumer<ResourceLimitEvent> onLimit) {
        return this.getNext().buildLimits(statementLimit, statementLimitSourceFilter, onLimit);
    }

    public FileSystem newDefaultFileSystem() {
        return this.getNext().newDefaultFileSystem();
    }

    public FileSystem allowLanguageHomeAccess(FileSystem fileSystem) {
        return this.getNext().allowLanguageHomeAccess(fileSystem);
    }

    public FileSystem newReadOnlyFileSystem(FileSystem fileSystem) {
        return this.getNext().newReadOnlyFileSystem(fileSystem);
    }

    public ProcessHandler newDefaultProcessHandler() {
        return this.getNext().newDefaultProcessHandler();
    }

    public boolean isDefaultProcessHandler(ProcessHandler processHandler) {
        return this.getNext().isDefaultProcessHandler(processHandler);
    }

    public ThreadScope createThreadScope() {
        return this.getNext().createThreadScope();
    }

    protected final OptionDescriptors createAllEngineOptionDescriptors() {
        AbstractPolyglotImpl current = this;
        while (current.prev != null) {
            current = current.prev;
        }
        OptionDescriptors union = OptionDescriptors.EMPTY;
        while (current != null) {
            union = OptionDescriptors.createUnion(current.createEngineOptionDescriptors(), union);
            current = current.next;
        }
        return union;
    }

    protected OptionDescriptors createEngineOptionDescriptors() {
        return OptionDescriptors.EMPTY;
    }

    public final AbstractPolyglotImpl getRootImpl() {
        AbstractPolyglotImpl current = this;
        while (current.prev != null) {
            current = current.prev;
        }
        return current;
    }

    public static abstract class ThreadScope
    implements AutoCloseable {
        protected ThreadScope(AbstractPolyglotImpl engineImpl) {
            Objects.requireNonNull(engineImpl);
        }

        @Override
        public abstract void close();
    }

    public static abstract class AbstractValueDispatch
    extends AbstractDispatchClass {
        protected AbstractValueDispatch(AbstractPolyglotImpl impl) {
            Objects.requireNonNull(impl);
        }

        public boolean hasArrayElements(Object context, Object receiver) {
            return false;
        }

        public abstract Value getArrayElement(Object var1, Object var2, long var3);

        public abstract void setArrayElement(Object var1, Object var2, long var3, Object var5);

        public abstract boolean removeArrayElement(Object var1, Object var2, long var3);

        public abstract long getArraySize(Object var1, Object var2);

        public boolean hasBufferElements(Object context, Object receiver) {
            return false;
        }

        public abstract boolean isBufferWritable(Object var1, Object var2);

        public abstract long getBufferSize(Object var1, Object var2);

        public abstract byte readBufferByte(Object var1, Object var2, long var3);

        public abstract void writeBufferByte(Object var1, Object var2, long var3, byte var5);

        public abstract short readBufferShort(Object var1, Object var2, ByteOrder var3, long var4);

        public abstract void writeBufferShort(Object var1, Object var2, ByteOrder var3, long var4, short var6);

        public abstract int readBufferInt(Object var1, Object var2, ByteOrder var3, long var4);

        public abstract void writeBufferInt(Object var1, Object var2, ByteOrder var3, long var4, int var6);

        public abstract long readBufferLong(Object var1, Object var2, ByteOrder var3, long var4);

        public abstract void writeBufferLong(Object var1, Object var2, ByteOrder var3, long var4, long var6);

        public abstract float readBufferFloat(Object var1, Object var2, ByteOrder var3, long var4);

        public abstract void writeBufferFloat(Object var1, Object var2, ByteOrder var3, long var4, float var6);

        public abstract double readBufferDouble(Object var1, Object var2, ByteOrder var3, long var4);

        public abstract void writeBufferDouble(Object var1, Object var2, ByteOrder var3, long var4, double var6);

        public boolean hasMembers(Object context, Object receiver) {
            return false;
        }

        public abstract Value getMember(Object var1, Object var2, String var3);

        public boolean hasMember(Object context, Object receiver, String key) {
            return false;
        }

        public Context getContext(Object context) {
            return null;
        }

        public Set<String> getMemberKeys(Object context, Object receiver) {
            return Collections.emptySet();
        }

        public abstract void putMember(Object var1, Object var2, String var3, Object var4);

        public abstract boolean removeMember(Object var1, Object var2, String var3);

        public boolean canExecute(Object context, Object receiver) {
            return false;
        }

        public abstract Value execute(Object var1, Object var2, Object[] var3);

        public abstract Value execute(Object var1, Object var2);

        public boolean canInstantiate(Object context, Object receiver) {
            return false;
        }

        public abstract Value newInstance(Object var1, Object var2, Object[] var3);

        public abstract void executeVoid(Object var1, Object var2, Object[] var3);

        public abstract void executeVoid(Object var1, Object var2);

        public boolean canInvoke(Object context, String identifier, Object receiver) {
            return false;
        }

        public abstract Value invoke(Object var1, Object var2, String var3, Object[] var4);

        public abstract Value invoke(Object var1, Object var2, String var3);

        public boolean isString(Object context, Object receiver) {
            return false;
        }

        public abstract String asString(Object var1, Object var2);

        public boolean isBoolean(Object context, Object receiver) {
            return false;
        }

        public abstract boolean asBoolean(Object var1, Object var2);

        public boolean fitsInInt(Object context, Object receiver) {
            return false;
        }

        public abstract int asInt(Object var1, Object var2);

        public boolean fitsInLong(Object context, Object receiver) {
            return false;
        }

        public abstract long asLong(Object var1, Object var2);

        public boolean fitsInDouble(Object context, Object receiver) {
            return false;
        }

        public abstract double asDouble(Object var1, Object var2);

        public boolean fitsInFloat(Object context, Object receiver) {
            return false;
        }

        public abstract float asFloat(Object var1, Object var2);

        public boolean isNull(Object context, Object receiver) {
            return false;
        }

        public boolean isNativePointer(Object context, Object receiver) {
            return false;
        }

        public boolean fitsInByte(Object context, Object receiver) {
            return false;
        }

        public abstract byte asByte(Object var1, Object var2);

        public boolean fitsInShort(Object context, Object receiver) {
            return false;
        }

        public abstract short asShort(Object var1, Object var2);

        public abstract long asNativePointer(Object var1, Object var2);

        public boolean isHostObject(Object context, Object receiver) {
            return false;
        }

        public boolean isProxyObject(Object context, Object receiver) {
            return false;
        }

        public abstract Object asHostObject(Object var1, Object var2);

        public abstract Object asProxyObject(Object var1, Object var2);

        public abstract String toString(Object var1, Object var2);

        public abstract Value getMetaObject(Object var1, Object var2);

        public boolean isNumber(Object context, Object receiver) {
            return false;
        }

        public abstract <T> T as(Object var1, Object var2, Class<T> var3);

        public abstract <T> T as(Object var1, Object var2, TypeLiteral<T> var3);

        public abstract SourceSection getSourceLocation(Object var1, Object var2);

        public boolean isDate(Object context, Object receiver) {
            return false;
        }

        public abstract LocalDate asDate(Object var1, Object var2);

        public boolean isTime(Object context, Object receiver) {
            return false;
        }

        public abstract LocalTime asTime(Object var1, Object var2);

        public abstract Instant asInstant(Object var1, Object var2);

        public boolean isTimeZone(Object context, Object receiver) {
            return false;
        }

        public abstract ZoneId asTimeZone(Object var1, Object var2);

        public boolean isDuration(Object context, Object receiver) {
            return false;
        }

        public abstract Duration asDuration(Object var1, Object var2);

        public boolean isException(Object context, Object receiver) {
            return false;
        }

        public abstract RuntimeException throwException(Object var1, Object var2);

        public boolean isMetaObject(Object context, Object receiver) {
            return false;
        }

        public abstract String getMetaQualifiedName(Object var1, Object var2);

        public abstract String getMetaSimpleName(Object var1, Object var2);

        public abstract boolean isMetaInstance(Object var1, Object var2, Object var3);

        public abstract boolean hasMetaParents(Object var1, Object var2);

        public abstract Value getMetaParents(Object var1, Object var2);

        public abstract boolean equalsImpl(Object var1, Object var2, Object var3);

        public abstract int hashCodeImpl(Object var1, Object var2);

        public boolean hasIterator(Object context, Object receiver) {
            return false;
        }

        public abstract Value getIterator(Object var1, Object var2);

        public boolean isIterator(Object context, Object receiver) {
            return false;
        }

        public abstract boolean hasIteratorNextElement(Object var1, Object var2);

        public abstract Value getIteratorNextElement(Object var1, Object var2);

        public boolean hasHashEntries(Object context, Object receiver) {
            return false;
        }

        public abstract long getHashSize(Object var1, Object var2);

        public boolean hasHashEntry(Object context, Object receiver, Object key) {
            return false;
        }

        public abstract Value getHashValue(Object var1, Object var2, Object var3);

        public abstract Value getHashValueOrDefault(Object var1, Object var2, Object var3, Object var4);

        public abstract void putHashEntry(Object var1, Object var2, Object var3, Object var4);

        public abstract boolean removeHashEntry(Object var1, Object var2, Object var3);

        public abstract Value getHashEntriesIterator(Object var1, Object var2);

        public abstract Value getHashKeysIterator(Object var1, Object var2);

        public abstract Value getHashValuesIterator(Object var1, Object var2);

        public abstract void pin(Object var1, Object var2);
    }

    public static abstract class AbstractHostLanguageService
    extends AbstractDispatchClass {
        protected AbstractHostLanguageService(AbstractPolyglotImpl polyglot) {
            Objects.requireNonNull(polyglot);
        }

        public abstract void release();

        public abstract void initializeHostContext(Object var1, Object var2, HostAccess var3, ClassLoader var4, Predicate<String> var5, boolean var6, boolean var7);

        public abstract void throwHostLanguageException(String var1);

        public abstract void addToHostClassPath(Object var1, Object var2);

        public abstract Object toGuestValue(Object var1, Object var2, boolean var3);

        public abstract Object asHostDynamicClass(Object var1, Class<?> var2);

        public abstract Object asHostStaticClass(Object var1, Class<?> var2);

        public abstract Object findDynamicClass(Object var1, String var2);

        public abstract Object findStaticClass(Object var1, String var2);

        public abstract Object createToHostTypeNode();

        public abstract <T> T toHostType(Object var1, Object var2, Object var3, Class<T> var4, Type var5);

        public abstract boolean isHostValue(Object var1);

        public abstract Object unboxHostObject(Object var1);

        public abstract Object unboxProxyObject(Object var1);

        public abstract Throwable unboxHostException(Throwable var1);

        public abstract Object toHostObject(Object var1, Object var2);

        public abstract RuntimeException toHostException(Object var1, Throwable var2);

        public abstract boolean isHostException(Object var1);

        public abstract boolean isHostFunction(Object var1);

        public abstract boolean isHostObject(Object var1);

        public abstract boolean isHostSymbol(Object var1);

        public abstract Object createHostAdapter(Object var1, Object[] var2, Object var3);

        public abstract boolean isHostProxy(Object var1);

        public abstract Error toHostResourceError(Throwable var1);

        public abstract int findNextGuestToHostStackTraceElement(StackTraceElement var1, StackTraceElement[] var2, int var3);

        public abstract Object migrateValue(Object var1, Object var2, Object var3);

        public abstract void pin(Object var1);

        public abstract void hostExit(int var1);
    }

    public static abstract class AbstractPolyglotHostService
    extends AbstractDispatchClass {
        protected AbstractPolyglotHostService(AbstractPolyglotImpl polyglot) {
            Objects.requireNonNull(polyglot);
        }

        public abstract void patch(AbstractPolyglotHostService var1);

        public abstract void notifyClearExplicitContextStack(Object var1);

        public abstract void notifyContextCancellingOrExiting(Object var1, boolean var2, int var3, boolean var4, String var5);

        public abstract void notifyContextClosed(Object var1, boolean var2, boolean var3, String var4);

        public abstract void notifyEngineClosed(Object var1, boolean var2);
    }

    public static abstract class AbstractHostAccess
    extends AbstractDispatchClass {
        protected AbstractHostAccess(AbstractPolyglotImpl impl) {
            Objects.requireNonNull(impl);
        }

        public abstract Object toGuestValue(Object var1, Object var2);

        public abstract <T> List<T> toList(Object var1, Object var2, boolean var3, Class<T> var4, Type var5);

        public abstract <K, V> Map<K, V> toMap(Object var1, Object var2, boolean var3, Class<K> var4, Type var5, Class<V> var6, Type var7);

        public abstract <K, V> Map.Entry<K, V> toMapEntry(Object var1, Object var2, boolean var3, Class<K> var4, Type var5, Class<V> var6, Type var7);

        public abstract <T> Function<?, ?> toFunction(Object var1, Object var2, Class<?> var3, Type var4, Class<?> var5, Type var6);

        public abstract Object toObjectProxy(Object var1, Class<?> var2, Object var3) throws IllegalArgumentException;

        public abstract <T> T toFunctionProxy(Object var1, Class<T> var2, Object var3);

        public abstract <T> Iterable<T> toIterable(Object var1, Object var2, boolean var3, Class<T> var4, Type var5);

        public abstract <T> Iterator<T> toIterator(Object var1, Object var2, boolean var3, Class<T> var4, Type var5);

        public abstract PolyglotException toPolyglotException(Object var1, Throwable var2);

        public abstract Value toValue(Object var1, Object var2);

        public abstract String getValueInfo(Object var1, Object var2);

        public abstract Value[] toValues(Object var1, Object[] var2, int var3);

        public abstract Value[] toValues(Object var1, Object[] var2);

        public abstract void rethrowPolyglotException(Object var1, PolyglotException var2);

        public abstract RuntimeException toEngineException(RuntimeException var1);

        public abstract boolean isEngineException(RuntimeException var1);

        public abstract RuntimeException unboxEngineException(RuntimeException var1);
    }

    public static abstract class AbstractLanguageDispatch
    extends AbstractDispatchClass {
        protected AbstractLanguageDispatch(AbstractPolyglotImpl engineImpl) {
            Objects.requireNonNull(engineImpl);
        }

        public abstract String getName(Object var1);

        public abstract String getImplementationName(Object var1);

        public abstract boolean isInteractive(Object var1);

        public abstract String getVersion(Object var1);

        public abstract String getId(Object var1);

        public abstract OptionDescriptors getOptions(Object var1);

        public abstract Set<String> getMimeTypes(Object var1);

        public abstract String getDefaultMimeType(Object var1);

        public abstract String getWebsite(Object var1);
    }

    public static abstract class AbstractInstrumentDispatch
    extends AbstractDispatchClass {
        protected AbstractInstrumentDispatch(AbstractPolyglotImpl engineImpl) {
            Objects.requireNonNull(engineImpl);
        }

        public abstract String getId(Object var1);

        public abstract String getName(Object var1);

        public abstract OptionDescriptors getOptions(Object var1);

        public abstract String getVersion(Object var1);

        public abstract <T> T lookup(Object var1, Class<T> var2);

        public abstract String getWebsite(Object var1);
    }

    public static abstract class AbstractStackFrameImpl
    extends AbstractDispatchClass {
        protected AbstractStackFrameImpl(AbstractPolyglotImpl engineImpl) {
            Objects.requireNonNull(engineImpl);
        }

        public abstract StackTraceElement toHostFrame();

        public abstract SourceSection getSourceLocation();

        public abstract String getRootName();

        public abstract Language getLanguage();

        public abstract boolean isHostFrame();

        public abstract String toStringImpl(int var1);
    }

    public static abstract class AbstractExceptionDispatch
    extends AbstractDispatchClass {
        protected AbstractExceptionDispatch(AbstractPolyglotImpl engineImpl) {
            Objects.requireNonNull(engineImpl);
        }

        public abstract boolean isInternalError(Object var1);

        public abstract boolean isCancelled(Object var1);

        public abstract boolean isExit(Object var1);

        public abstract int getExitStatus(Object var1);

        public abstract Iterable<PolyglotException.StackFrame> getPolyglotStackTrace(Object var1);

        public abstract boolean isSyntaxError(Object var1);

        public abstract Value getGuestObject(Object var1);

        public abstract boolean isIncompleteSource(Object var1);

        public abstract void onCreate(Object var1, PolyglotException var2);

        public abstract void printStackTrace(Object var1, PrintStream var2);

        public abstract void printStackTrace(Object var1, PrintWriter var2);

        public abstract StackTraceElement[] getStackTrace(Object var1);

        public abstract String getMessage(Object var1);

        public abstract boolean isHostException(Object var1);

        public abstract Throwable asHostException(Object var1);

        public abstract SourceSection getSourceLocation(Object var1);

        public abstract boolean isResourceExhausted(Object var1);

        public abstract boolean isInterrupted(Object var1);
    }

    public static abstract class AbstractEngineDispatch
    extends AbstractDispatchClass {
        protected AbstractEngineDispatch(AbstractPolyglotImpl impl) {
            Objects.requireNonNull(impl);
        }

        public abstract void setAPI(Object var1, Engine var2);

        public abstract Language requirePublicLanguage(Object var1, String var2);

        public abstract Instrument requirePublicInstrument(Object var1, String var2);

        public abstract void close(Object var1, Object var2, boolean var3);

        public abstract Map<String, Instrument> getInstruments(Object var1);

        public abstract Map<String, Language> getLanguages(Object var1);

        public abstract OptionDescriptors getOptions(Object var1);

        public abstract Context createContext(Object var1, OutputStream var2, OutputStream var3, InputStream var4, boolean var5, HostAccess var6, PolyglotAccess var7, boolean var8, boolean var9, boolean var10, boolean var11, boolean var12, boolean var13, Predicate<String> var14, Map<String, String> var15, Map<String, String[]> var16, String[] var17, FileSystem var18, Object var19, boolean var20, ProcessHandler var21, EnvironmentAccess var22, Map<String, String> var23, ZoneId var24, Object var25, String var26, ClassLoader var27, boolean var28, boolean var29);

        public abstract String getImplementationName(Object var1);

        public abstract Set<Source> getCachedSources(Object var1);

        public abstract String getVersion(Object var1);

        public abstract ExecutionListener attachExecutionListener(Object var1, Consumer<ExecutionEvent> var2, Consumer<ExecutionEvent> var3, boolean var4, boolean var5, boolean var6, Predicate<Source> var7, Predicate<String> var8, boolean var9, boolean var10, boolean var11);

        public abstract void shutdown(Object var1);
    }

    public static abstract class AbstractContextDispatch
    extends AbstractDispatchClass {
        protected AbstractContextDispatch(AbstractPolyglotImpl impl) {
            Objects.requireNonNull(impl);
        }

        public abstract boolean initializeLanguage(Object var1, String var2);

        public abstract Value eval(Object var1, String var2, Source var3);

        public abstract Value parse(Object var1, String var2, Source var3);

        public abstract void close(Object var1, boolean var2);

        public abstract boolean interrupt(Object var1, Duration var2);

        public abstract Value asValue(Object var1, Object var2);

        public abstract void explicitEnter(Object var1);

        public abstract void explicitLeave(Object var1);

        public abstract Value getBindings(Object var1, String var2);

        public abstract Value getPolyglotBindings(Object var1);

        public abstract void resetLimits(Object var1);

        public abstract void safepoint(Object var1);

        public abstract void setAPI(Object var1, Context var2);
    }

    public static abstract class AbstractSourceSectionDispatch
    extends AbstractDispatchClass {
        protected AbstractSourceSectionDispatch(AbstractPolyglotImpl polyglotImpl) {
            Objects.requireNonNull(polyglotImpl);
        }

        public abstract boolean isAvailable(Object var1);

        public abstract boolean hasLines(Object var1);

        public abstract boolean hasColumns(Object var1);

        public abstract boolean hasCharIndex(Object var1);

        public abstract int getStartLine(Object var1);

        public abstract int getStartColumn(Object var1);

        public abstract int getEndLine(Object var1);

        public abstract int getEndColumn(Object var1);

        public abstract int getCharIndex(Object var1);

        public abstract int getCharLength(Object var1);

        public abstract int getCharEndIndex(Object var1);

        public abstract CharSequence getCode(Object var1);

        public abstract String toString(Object var1);

        public abstract int hashCode(Object var1);

        public abstract boolean equals(Object var1, Object var2);
    }

    public static abstract class AbstractSourceDispatch
    extends AbstractDispatchClass {
        protected AbstractSourceDispatch(AbstractPolyglotImpl engineImpl) {
            Objects.requireNonNull(engineImpl);
        }

        public abstract String getName(Object var1);

        public abstract String getPath(Object var1);

        public abstract boolean isCached(Object var1);

        public abstract boolean isInteractive(Object var1);

        public abstract URL getURL(Object var1);

        public abstract URI getURI(Object var1);

        public abstract Reader getReader(Object var1);

        public abstract InputStream getInputStream(Object var1);

        public abstract int getLength(Object var1);

        public abstract CharSequence getCharacters(Object var1);

        public abstract CharSequence getCharacters(Object var1, int var2);

        public abstract int getLineCount(Object var1);

        public abstract int getLineNumber(Object var1, int var2);

        public abstract int getColumnNumber(Object var1, int var2);

        public abstract int getLineStartOffset(Object var1, int var2);

        public abstract int getLineLength(Object var1, int var2);

        public abstract String toString(Object var1);

        public abstract int hashCode(Object var1);

        public abstract boolean equals(Object var1, Object var2);

        public abstract boolean isInternal(Object var1);

        public abstract ByteSequence getBytes(Object var1);

        public abstract boolean hasCharacters(Object var1);

        public abstract boolean hasBytes(Object var1);

        public abstract String getMimeType(Object var1);

        public abstract String getLanguage(Object var1);
    }

    public static abstract class AbstractExecutionEventDispatch
    extends AbstractDispatchClass {
        protected AbstractExecutionEventDispatch(AbstractPolyglotImpl polyglotImpl) {
            Objects.requireNonNull(polyglotImpl);
        }

        public abstract List<Value> getExecutionEventInputValues(Object var1);

        public abstract SourceSection getExecutionEventLocation(Object var1);

        public abstract String getExecutionEventRootName(Object var1);

        public abstract Value getExecutionEventReturnValue(Object var1);

        public abstract boolean isExecutionEventExpression(Object var1);

        public abstract boolean isExecutionEventStatement(Object var1);

        public abstract boolean isExecutionEventRoot(Object var1);

        public abstract PolyglotException getExecutionEventException(Object var1);
    }

    public static abstract class AbstractExecutionListenerDispatch
    extends AbstractDispatchClass {
        protected AbstractExecutionListenerDispatch(AbstractPolyglotImpl polyglotImpl) {
            Objects.requireNonNull(polyglotImpl);
        }

        public abstract void closeExecutionListener(Object var1);
    }

    public static abstract class AbstractDispatchClass {
    }

    public static abstract class APIAccess {
        protected APIAccess() {
            if (!this.getClass().getCanonicalName().equals("org.graalvm.polyglot.Engine.APIAccessImpl")) {
                throw new AssertionError((Object)("Only one implementation of APIAccess allowed. " + this.getClass().getCanonicalName()));
            }
        }

        public abstract Engine newEngine(AbstractEngineDispatch var1, Object var2, boolean var3);

        public abstract Context newContext(AbstractContextDispatch var1, Object var2, Engine var3);

        public abstract Language newLanguage(AbstractLanguageDispatch var1, Object var2);

        public abstract Instrument newInstrument(AbstractInstrumentDispatch var1, Object var2);

        public abstract Value newValue(AbstractValueDispatch var1, Object var2, Object var3);

        public abstract Source newSource(AbstractSourceDispatch var1, Object var2);

        public abstract SourceSection newSourceSection(Source var1, AbstractSourceSectionDispatch var2, Object var3);

        public abstract PolyglotException newLanguageException(String var1, AbstractExceptionDispatch var2, Object var3);

        public abstract Object getReceiver(Instrument var1);

        public abstract Object getReceiver(Language var1);

        public abstract Object getReceiver(Engine var1);

        public abstract Object getReceiver(Context var1);

        public abstract Object getReceiver(PolyglotException var1);

        public abstract Object getReceiver(Value var1);

        public abstract Object getReceiver(ResourceLimits var1);

        public abstract Object getReceiver(Source var1);

        public abstract Object getReceiver(SourceSection var1);

        public abstract AbstractValueDispatch getDispatch(Value var1);

        public abstract Object getContext(Value var1);

        public abstract AbstractStackFrameImpl getDispatch(PolyglotException.StackFrame var1);

        public abstract AbstractLanguageDispatch getDispatch(Language var1);

        public abstract AbstractInstrumentDispatch getDispatch(Instrument var1);

        public abstract AbstractEngineDispatch getDispatch(Engine var1);

        public abstract AbstractContextDispatch getDispatch(Context var1);

        public abstract AbstractSourceDispatch getDispatch(Source var1);

        public abstract AbstractSourceSectionDispatch getDispatch(SourceSection var1);

        public abstract ResourceLimitEvent newResourceLimitsEvent(Context var1);

        public abstract PolyglotException.StackFrame newPolyglotStackTraceElement(AbstractStackFrameImpl var1, Object var2);

        public abstract List<Object> getTargetMappings(HostAccess var1);

        public abstract boolean allowsAccess(HostAccess var1, AnnotatedElement var2);

        public abstract boolean allowsImplementation(HostAccess var1, Class<?> var2);

        public abstract boolean isMethodScopingEnabled(HostAccess var1);

        public abstract boolean isMethodScoped(HostAccess var1, Executable var2);

        public abstract boolean isArrayAccessible(HostAccess var1);

        public abstract boolean isListAccessible(HostAccess var1);

        public abstract boolean isBufferAccessible(HostAccess var1);

        public abstract boolean isIterableAccessible(HostAccess var1);

        public abstract boolean isIteratorAccessible(HostAccess var1);

        public abstract boolean isMapAccessible(HostAccess var1);

        public abstract boolean allowsPublicAccess(HostAccess var1);

        public abstract boolean allowsAccessInheritance(HostAccess var1);

        public abstract Object getHostAccessImpl(HostAccess var1);

        public abstract void setHostAccessImpl(HostAccess var1, Object var2);

        public abstract UnmodifiableEconomicSet<String> getEvalAccess(PolyglotAccess var1, String var2);

        public abstract UnmodifiableEconomicMap<String, UnmodifiableEconomicSet<String>> getEvalAccess(PolyglotAccess var1);

        public abstract UnmodifiableEconomicSet<String> getBindingsAccess(PolyglotAccess var1);

        public abstract String validatePolyglotAccess(PolyglotAccess var1, Set<String> var2);

        public abstract void engineClosed(Engine var1);
    }

    public static abstract class IOAccess {
        protected IOAccess() {
            if (!this.getClass().getCanonicalName().equals("org.graalvm.polyglot.io.IOHelper.IOAccessImpl")) {
                throw new AssertionError((Object)("Only one implementation of IOAccess allowed. " + this.getClass().getCanonicalName()));
            }
        }

        public abstract ProcessHandler.ProcessCommand newProcessCommand(List<String> var1, String var2, Map<String, String> var3, boolean var4, ProcessHandler.Redirect var5, ProcessHandler.Redirect var6, ProcessHandler.Redirect var7);

        public abstract ProcessHandler.Redirect createRedirectToStream(OutputStream var1);

        public abstract OutputStream getOutputStream(ProcessHandler.Redirect var1);
    }

    public static abstract class ManagementAccess {
        protected ManagementAccess() {
            if (!this.getClass().getCanonicalName().equals("org.graalvm.polyglot.management.Management.ManagementAccessImpl")) {
                throw new AssertionError((Object)("Only one implementation of ManagementAccessImpl allowed. " + this.getClass().getCanonicalName()));
            }
        }

        public abstract ExecutionListener newExecutionListener(AbstractExecutionListenerDispatch var1, Object var2);

        public abstract ExecutionEvent newExecutionEvent(AbstractExecutionEventDispatch var1, Object var2);

        public abstract Object getReceiver(ExecutionListener var1);

        public abstract AbstractExecutionListenerDispatch getDispatch(ExecutionListener var1);

        public abstract Object getReceiver(ExecutionEvent var1);

        public abstract AbstractExecutionEventDispatch getDispatch(ExecutionEvent var1);
    }
}

