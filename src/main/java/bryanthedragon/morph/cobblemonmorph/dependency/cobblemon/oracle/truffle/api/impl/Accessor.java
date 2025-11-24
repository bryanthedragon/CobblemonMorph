
package com.oracle.truffle.api.impl;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.ContextLocal;
import com.oracle.truffle.api.ContextThreadLocal;
import com.oracle.truffle.api.InstrumentInfo;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.ThreadLocalAction;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleContext;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleLogger;
import com.oracle.truffle.api.TruffleStackTraceElement;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.impl.AbstractFastThreadLocal;
import com.oracle.truffle.api.impl.DefaultContextThreadLocal;
import com.oracle.truffle.api.impl.DefaultThreadLocalHandshake;
import com.oracle.truffle.api.impl.DelegatingOutputStream;
import com.oracle.truffle.api.impl.DispatchOutputStream;
import com.oracle.truffle.api.impl.FrameWithoutBoxing;
import com.oracle.truffle.api.impl.TVMCI;
import com.oracle.truffle.api.impl.ThreadLocalHandshake;
import com.oracle.truffle.api.impl.TruffleLocator;
import com.oracle.truffle.api.io.TruffleProcessBuilder;
import com.oracle.truffle.api.nodes.BlockNode;
import com.oracle.truffle.api.nodes.BytecodeOSRNode;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExecutableNode;
import com.oracle.truffle.api.nodes.ExecutionSignature;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import org.graalvm.collections.Pair;
import org.graalvm.nativeimage.ImageInfo;
import org.graalvm.options.OptionDescriptors;
import org.graalvm.options.OptionKey;
import org.graalvm.options.OptionValues;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;
import org.graalvm.polyglot.io.FileSystem;
import org.graalvm.polyglot.io.MessageTransport;
import org.graalvm.polyglot.io.ProcessHandler;
import org.graalvm.polyglot.proxy.Proxy;

public abstract class Accessor {
    @CompilerDirectives.CompilationFinal
    private static volatile TVMCI tvmci;

    protected void initializeNativeImageTruffleLocator() {
        TruffleLocator.initializeNativeImageTruffleLocator();
    }

    public final void transferOSRFrameStaticSlot(FrameWithoutBoxing sourceFrame, FrameWithoutBoxing targetFrame, int slot) {
        sourceFrame.transferOSRStaticSlot(targetFrame, slot);
    }

    protected Accessor() {
        String thisClassName = this.getClass().getName();
        if (!("com.oracle.truffle.api.LanguageAccessor".equals(thisClassName) || "com.oracle.truffle.api.TruffleAccessor".equals(thisClassName) || "com.oracle.truffle.api.nodes.NodeAccessor".equals(thisClassName) || "com.oracle.truffle.api.instrumentation.InstrumentAccessor".equals(thisClassName) || "com.oracle.truffle.api.source.SourceAccessor".equals(thisClassName) || "com.oracle.truffle.api.interop.InteropAccessor".equals(thisClassName) || "com.oracle.truffle.api.exception.ExceptionAccessor".equals(thisClassName) || "com.oracle.truffle.api.io.IOAccessor".equals(thisClassName) || "com.oracle.truffle.api.frame.FrameAccessor".equals(thisClassName) || "com.oracle.truffle.host.HostAccessor".equals(thisClassName) || "com.oracle.truffle.polyglot.EngineAccessor".equals(thisClassName) || "com.oracle.truffle.api.utilities.JSONHelper.DumpAccessor".equals(thisClassName) || "com.oracle.truffle.api.debug.Debugger$AccessorDebug".equals(thisClassName) || "com.oracle.truffle.tck.instrumentation.VerifierInstrument$TruffleTCKAccessor".equals(thisClassName) || "com.oracle.truffle.api.instrumentation.test.AbstractInstrumentationTest$TestAccessor".equals(thisClassName) || "com.oracle.truffle.api.test.TestAPIAccessor".equals(thisClassName) || "com.oracle.truffle.api.impl.TVMCIAccessor".equals(thisClassName) || "com.oracle.truffle.api.impl.DefaultRuntimeAccessor".equals(thisClassName) || "org.graalvm.compiler.truffle.runtime.GraalRuntimeAccessor".equals(thisClassName) || "com.oracle.truffle.api.dsl.DSLAccessor".equals(thisClassName) || "com.oracle.truffle.api.impl.ImplAccessor".equals(thisClassName) || "com.oracle.truffle.api.memory.MemoryFenceAccessor".equals(thisClassName) || "com.oracle.truffle.api.library.LibraryAccessor".equals(thisClassName) || "com.oracle.truffle.polyglot.enterprise.EnterpriseEngineAccessor".equals(thisClassName) || "com.oracle.truffle.polyglot.enterprise.test.EnterpriseDispatchTestAccessor".equals(thisClassName) || "com.oracle.truffle.api.staticobject.SomAccessor".equals(thisClassName) || "com.oracle.truffle.api.strings.TStringAccessor".equals(thisClassName))) {
            throw new IllegalStateException(thisClassName);
        }
    }

    public final NodeSupport nodeSupport() {
        return Constants.NODES;
    }

    public final LanguageSupport languageSupport() {
        return Constants.LANGUAGE;
    }

    public final EngineSupport engineSupport() {
        return Constants.ENGINE;
    }

    public final InstrumentSupport instrumentSupport() {
        return Constants.INSTRUMENT;
    }

    public final InteropSupport interopSupport() {
        return Constants.INTEROP;
    }

    public final ExceptionSupport exceptionSupport() {
        return Constants.EXCEPTION;
    }

    public final SourceSupport sourceSupport() {
        return Constants.SOURCE;
    }

    public final FrameSupport framesSupport() {
        return Constants.FRAMES;
    }

    public final RuntimeSupport runtimeSupport() {
        return Constants.RUNTIME;
    }

    public final HostSupport hostSupport() {
        return Constants.HOST;
    }

    public final IOSupport ioSupport() {
        return Constants.IO;
    }

    public static void main(String ... args) {
        throw new IllegalStateException();
    }

    private static TVMCI getTVMCI() {
        if (ImageInfo.inImageRuntimeCode()) {
            return tvmci;
        }
        TVMCI result = tvmci;
        if (result == null) {
            tvmci = result = Truffle.getRuntime().getCapability(TVMCI.class);
        }
        return result;
    }

    private static class Constants {
        private static final LanguageSupport LANGUAGE = (LanguageSupport)Constants.loadSupport("com.oracle.truffle.api.LanguageAccessor$LanguageImpl");
        private static final NodeSupport NODES = (NodeSupport)Constants.loadSupport("com.oracle.truffle.api.nodes.NodeAccessor$AccessNodes");
        private static final InstrumentSupport INSTRUMENT = (InstrumentSupport)Constants.loadSupport("com.oracle.truffle.api.instrumentation.InstrumentAccessor$InstrumentImpl");
        private static final SourceSupport SOURCE = (SourceSupport)Constants.loadSupport("com.oracle.truffle.api.source.SourceAccessor$SourceSupportImpl");
        private static final InteropSupport INTEROP = (InteropSupport)Constants.loadSupport("com.oracle.truffle.api.interop.InteropAccessor$InteropImpl");
        private static final ExceptionSupport EXCEPTION = (ExceptionSupport)Constants.loadSupport("com.oracle.truffle.api.exception.ExceptionAccessor$ExceptionSupportImpl");
        private static final IOSupport IO = (IOSupport)Constants.loadSupport("com.oracle.truffle.api.io.IOAccessor$IOSupportImpl");
        private static final FrameSupport FRAMES = (FrameSupport)Constants.loadSupport("com.oracle.truffle.api.frame.FrameAccessor$FramesImpl");
        private static final EngineSupport ENGINE = (EngineSupport)Constants.loadSupport("com.oracle.truffle.polyglot.EngineAccessor$EngineImpl");
        private static final HostSupport HOST = (HostSupport)Constants.loadSupport("com.oracle.truffle.host.HostAccessor$HostImpl");
        private static final RuntimeSupport RUNTIME = Accessor.getTVMCI().createRuntimeSupport(RuntimeSupport.PERMISSION);

        private Constants() {
        }

        private static <T> T loadSupport(String className) {
            try {
                Class<?> klass = Class.forName(className, true, Accessor.class.getClassLoader());
                Constructor<?> constructor = klass.getDeclaredConstructor(new Class[0]);
                constructor.setAccessible(true);
                return (T)constructor.newInstance(new Object[0]);
            }
            catch (ReflectiveOperationException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public static abstract class RuntimeSupport {
        static final Object PERMISSION = new Object();

        protected RuntimeSupport(Object permission2) {
            if (permission2 != PERMISSION) {
                throw new AssertionError((Object)"Invalid permission to create runtime support.");
            }
        }

        public abstract RootCallTarget newCallTarget(CallTarget var1, RootNode var2);

        public abstract boolean isLoaded(CallTarget var1);

        public abstract void notifyOnLoad(CallTarget var1);

        public ThreadLocalHandshake getThreadLocalHandshake() {
            return DefaultThreadLocalHandshake.SINGLETON;
        }

        public abstract void onLoopCount(Node var1, int var2);

        public abstract boolean pollBytecodeOSRBackEdge(BytecodeOSRNode var1);

        public abstract Object tryBytecodeOSR(BytecodeOSRNode var1, int var2, Object var3, Runnable var4, VirtualFrame var5);

        public abstract void onOSRNodeReplaced(BytecodeOSRNode var1, Node var2, Node var3, CharSequence var4);

        public abstract void transferOSRFrame(BytecodeOSRNode var1, Frame var2, Frame var3, int var4);

        public abstract void transferOSRFrame(BytecodeOSRNode var1, Frame var2, Frame var3, int var4, Object var5);

        public abstract void restoreOSRFrame(BytecodeOSRNode var1, Frame var2, Frame var3);

        public abstract OptionDescriptors getEngineOptionDescriptors();

        public abstract boolean isGuestCallStackFrame(StackTraceElement var1);

        public abstract void initializeProfile(CallTarget var1, Class<?>[] var2);

        public abstract <T extends Node> BlockNode<T> createBlockNode(T[] var1, BlockNode.ElementExecutor<T> var2);

        public abstract Assumption createAlwaysValidAssumption();

        public abstract String getSavedProperty(String var1);

        public abstract void reportPolymorphicSpecialize(Node var1);

        public abstract Object callInlined(Node var1, CallTarget var2, Object ... var3);

        public abstract Object callProfiled(CallTarget var1, Object ... var2);

        public abstract Object[] castArrayFixedLength(Object[] var1, int var2);

        public abstract <T> T unsafeCast(Object var1, Class<T> var2, boolean var3, boolean var4, boolean var5);

        public abstract void flushCompileQueue(Object var1);

        public abstract Object createRuntimeData(OptionValues var1, Function<String, TruffleLogger> var2);

        public abstract Object tryLoadCachedEngine(OptionValues var1, Function<String, TruffleLogger> var2);

        public abstract void onEngineCreate(Object var1, Object var2);

        public abstract boolean isStoreEnabled(OptionValues var1);

        public abstract void onEnginePatch(Object var1, OptionValues var2, Function<String, TruffleLogger> var3);

        public abstract boolean onEngineClosing(Object var1);

        public abstract void onEngineClosed(Object var1);

        public abstract boolean isOSRRootNode(RootNode var1);

        public abstract int getObjectAlignment();

        public abstract int getArrayBaseOffset(Class<?> var1);

        public abstract int getArrayIndexScale(Class<?> var1);

        public abstract int getBaseInstanceSize(Class<?> var1);

        public abstract Object[] getResolvedFields(Class<?> var1, boolean var2, boolean var3);

        public abstract Object getFieldValue(Object var1, Object var2);

        public AbstractFastThreadLocal getContextThreadLocal() {
            return DefaultContextThreadLocal.SINGLETON;
        }
    }

    public static abstract class SomSupport
    extends Support {
        static final String IMPL_CLASS_NAME = "com.oracle.truffle.api.staticobject.SomAccessor";

        protected SomSupport() {
            super(IMPL_CLASS_NAME);
        }
    }

    public static abstract class IOSupport
    extends Support {
        static final String IMPL_CLASS_NAME = "com.oracle.truffle.api.io.IOAccessor$IOSupportImpl";

        protected IOSupport() {
            super(IMPL_CLASS_NAME);
        }

        public abstract TruffleProcessBuilder createProcessBuilder(Object var1, FileSystem var2, List<String> var3);
    }

    public static abstract class ExceptionSupport
    extends Support {
        static final String IMPL_CLASS_NAME = "com.oracle.truffle.api.exception.ExceptionAccessor$ExceptionSupportImpl";

        protected ExceptionSupport() {
            super(IMPL_CLASS_NAME);
        }

        public abstract Throwable getLazyStackTrace(Throwable var1);

        public abstract void setLazyStackTrace(Throwable var1, Throwable var2);

        public abstract Object createDefaultStackTraceElementObject(RootNode var1, SourceSection var2);

        public abstract boolean isException(Object var1);

        public abstract RuntimeException throwException(Object var1);

        public abstract Object getExceptionType(Object var1);

        public abstract boolean isExceptionIncompleteSource(Object var1);

        public abstract int getExceptionExitStatus(Object var1);

        public abstract boolean hasExceptionCause(Object var1);

        public abstract Object getExceptionCause(Object var1);

        public abstract boolean hasExceptionMessage(Object var1);

        public abstract Object getExceptionMessage(Object var1);

        public abstract boolean hasExceptionStackTrace(Object var1);

        public abstract Object getExceptionStackTrace(Object var1);

        public abstract boolean hasSourceLocation(Object var1);

        public abstract SourceSection getSourceLocation(Object var1);

        public abstract int getStackTraceElementLimit(Object var1);

        public abstract Node getLocation(Object var1);

        public abstract boolean assertGuestObject(Object var1);
    }

    public static abstract class FrameSupport
    extends Support {
        static final String IMPL_CLASS_NAME = "com.oracle.truffle.api.frame.FrameAccessor$FramesImpl";

        protected FrameSupport() {
            super(IMPL_CLASS_NAME);
        }

        public abstract void markMaterializeCalled(FrameDescriptor var1);

        public abstract boolean getMaterializeCalled(FrameDescriptor var1);

        public abstract boolean usesAllStaticMode(FrameDescriptor var1);

        public abstract boolean usesMixedStaticMode(FrameDescriptor var1);
    }

    public static abstract class InstrumentSupport
    extends Support {
        static final String IMPL_CLASS_NAME = "com.oracle.truffle.api.instrumentation.InstrumentAccessor$InstrumentImpl";

        protected InstrumentSupport() {
            super(IMPL_CLASS_NAME);
        }

        public abstract void initializeInstrument(Object var1, Object var2, String var3, Supplier<? extends Object> var4);

        public abstract void createInstrument(Object var1, Object var2, String[] var3, OptionValues var4);

        public abstract void finalizeInstrument(Object var1, Object var2);

        public abstract void disposeInstrument(Object var1, Object var2, boolean var3);

        public abstract <T> T getInstrumentationHandlerService(Object var1, Object var2, Class<T> var3);

        public abstract Object createInstrumentationHandler(Object var1, DispatchOutputStream var2, DispatchOutputStream var3, InputStream var4, MessageTransport var5, boolean var6);

        public abstract void collectEnvServices(Set<Object> var1, Object var2, TruffleLanguage<?> var3);

        public abstract void onFirstExecution(RootNode var1, boolean var2);

        public abstract void onLoad(RootNode var1);

        public final DispatchOutputStream createDispatchOutput(OutputStream out) {
            if (out instanceof DispatchOutputStream) {
                return (DispatchOutputStream)out;
            }
            return new DispatchOutputStream(out);
        }

        public final DelegatingOutputStream createDelegatingOutput(OutputStream out, DispatchOutputStream delegate) {
            return new DelegatingOutputStream(out, delegate);
        }

        public final OutputStream getOut(DispatchOutputStream out) {
            return out.getOut();
        }

        public abstract OptionDescriptors describeEngineOptions(Object var1, Object var2, String var3);

        public abstract OptionDescriptors describeContextOptions(Object var1, Object var2, String var3);

        public abstract Object getEngineInstrumenter(Object var1);

        public abstract void onNodeInserted(RootNode var1, Node var2);

        public abstract boolean hasContextBindings(Object var1);

        public abstract boolean hasThreadBindings(Object var1);

        public abstract void notifyContextCreated(Object var1, TruffleContext var2);

        public abstract void notifyContextClosed(Object var1, TruffleContext var2);

        public abstract void notifyContextResetLimit(Object var1, TruffleContext var2);

        public abstract void notifyLanguageContextCreate(Object var1, TruffleContext var2, LanguageInfo var3);

        public abstract void notifyLanguageContextCreated(Object var1, TruffleContext var2, LanguageInfo var3);

        public abstract void notifyLanguageContextCreateFailed(Object var1, TruffleContext var2, LanguageInfo var3);

        public abstract void notifyLanguageContextInitialize(Object var1, TruffleContext var2, LanguageInfo var3);

        public abstract void notifyLanguageContextInitialized(Object var1, TruffleContext var2, LanguageInfo var3);

        public abstract void notifyLanguageContextInitializeFailed(Object var1, TruffleContext var2, LanguageInfo var3);

        public abstract void notifyLanguageContextFinalized(Object var1, TruffleContext var2, LanguageInfo var3);

        public abstract void notifyLanguageContextDisposed(Object var1, TruffleContext var2, LanguageInfo var3);

        public abstract void notifyThreadStarted(Object var1, TruffleContext var2, Thread var3);

        public abstract void notifyThreadFinished(Object var1, TruffleContext var2, Thread var3);

        public abstract org.graalvm.polyglot.SourceSection createSourceSection(Object var1, Source var2, SourceSection var3);

        public abstract void patchInstrumentationHandler(Object var1, DispatchOutputStream var2, DispatchOutputStream var3, InputStream var4);

        public abstract void finalizeStoreInstrumentationHandler(Object var1);

        public abstract boolean isInputValueSlotIdentifier(Object var1);

        public abstract boolean isInstrumentable(Node var1);

        public abstract Object invokeContextLocalFactory(Object var1, TruffleContext var2);

        public abstract Object invokeContextThreadLocalFactory(Object var1, TruffleContext var2, Thread var3);

        public abstract void notifyEnter(Object var1, TruffleContext var2);

        public abstract void notifyLeave(Object var1, TruffleContext var2);

        public abstract Collection<CallTarget> getLoadedCallTargets(Object var1);

        public abstract Object getPolyglotInstrument(Object var1);
    }

    public static abstract class LanguageSupport
    extends Support {
        static final String IMPL_CLASS_NAME = "com.oracle.truffle.api.LanguageAccessor$LanguageImpl";

        protected LanguageSupport() {
            super(IMPL_CLASS_NAME);
        }

        public abstract void initializeLanguage(TruffleLanguage<?> var1, LanguageInfo var2, Object var3, Object var4);

        public abstract TruffleLanguage.Env createEnv(Object var1, TruffleLanguage<?> var2, OutputStream var3, OutputStream var4, InputStream var5, Map<String, Object> var6, OptionValues var7, String[] var8);

        public abstract boolean areOptionsCompatible(TruffleLanguage<?> var1, OptionValues var2, OptionValues var3);

        public abstract Object createEnvContext(TruffleLanguage.Env var1, List<Object> var2);

        public abstract TruffleContext createTruffleContext(Object var1, boolean var2);

        public abstract void postInitEnv(TruffleLanguage.Env var1);

        public abstract Object evalInContext(com.oracle.truffle.api.source.Source var1, Node var2, MaterializedFrame var3);

        public abstract void dispose(TruffleLanguage.Env var1);

        public abstract LanguageInfo getLanguageInfo(TruffleLanguage.Env var1);

        public abstract LanguageInfo getLanguageInfo(TruffleLanguage<?> var1);

        public abstract Object getPolyglotLanguageInstance(TruffleLanguage<?> var1);

        public abstract CallTarget parse(TruffleLanguage.Env var1, com.oracle.truffle.api.source.Source var2, Node var3, String ... var4);

        public abstract ExecutableNode parseInline(TruffleLanguage.Env var1, com.oracle.truffle.api.source.Source var2, Node var3, MaterializedFrame var4);

        public abstract boolean isVisible(TruffleLanguage.Env var1, Object var2);

        public abstract Object getContext(TruffleLanguage.Env var1);

        public abstract Object getPolyglotLanguageContext(TruffleLanguage.Env var1);

        public abstract TruffleLanguage<?> getSPI(TruffleLanguage.Env var1);

        public abstract InstrumentInfo createInstrument(Object var1, String var2, String var3, String var4);

        public abstract Object getPolyglotInstrument(InstrumentInfo var1);

        public abstract boolean isContextInitialized(TruffleLanguage.Env var1);

        public abstract OptionDescriptors describeOptions(TruffleLanguage<?> var1, String var2);

        public abstract void onThrowable(Node var1, RootCallTarget var2, Throwable var3, Frame var4);

        public abstract boolean isThreadAccessAllowed(TruffleLanguage.Env var1, Thread var2, boolean var3);

        public abstract void initializeThread(TruffleLanguage.Env var1, Thread var2);

        public abstract void initializeMultiThreading(TruffleLanguage.Env var1);

        public abstract void disposeThread(TruffleLanguage.Env var1, Thread var2);

        public abstract void finalizeContext(TruffleLanguage.Env var1);

        public abstract void exitContext(TruffleLanguage.Env var1, TruffleLanguage.ExitMode var2, int var3);

        public abstract TruffleLanguage.Env patchEnvContext(TruffleLanguage.Env var1, OutputStream var2, OutputStream var3, InputStream var4, Map<String, Object> var5, OptionValues var6, String[] var7);

        public abstract void initializeMultiContext(TruffleLanguage<?> var1);

        public abstract boolean isTruffleStackTrace(Throwable var1);

        public abstract StackTraceElement[] getInternalStackTraceElements(Throwable var1);

        public abstract void materializeHostFrames(Throwable var1);

        public abstract void configureLoggers(Object var1, Map<String, Level> var2, Object ... var3);

        public abstract Object getDefaultLoggers();

        public abstract Object createEngineLoggers(Object var1);

        public abstract Object getLoggersSPI(Object var1);

        public abstract void closeEngineLoggers(Object var1);

        public abstract TruffleLogger getLogger(String var1, String var2, Object var3);

        public abstract Object getLoggerCache(TruffleLogger var1);

        public abstract TruffleLanguage<?> getLanguage(TruffleLanguage.Env var1);

        public abstract Object createFileSystemContext(Object var1, FileSystem var2);

        public abstract String detectMimeType(TruffleFile var1, Set<String> var2);

        public abstract Charset detectEncoding(TruffleFile var1, String var2);

        public abstract TruffleFile getTruffleFile(String var1, Object var2);

        public abstract boolean hasAllAccess(Object var1);

        public abstract TruffleFile getTruffleFile(Object var1, String var2);

        public abstract TruffleFile getTruffleFile(Object var1, URI var2);

        public abstract FileSystem getFileSystem(TruffleFile var1);

        public abstract Path getPath(TruffleFile var1);

        public abstract Object getLanguageView(TruffleLanguage.Env var1, Object var2);

        public abstract Object getFileSystemContext(TruffleFile var1);

        public abstract Object getFileSystemEngineObject(Object var1);

        public abstract Object getPolyglotContext(TruffleContext var1);

        public abstract Object invokeContextLocalFactory(Object var1, Object var2);

        public abstract Object invokeContextThreadLocalFactory(Object var1, Object var2, Thread var3);

        public abstract Object getScope(TruffleLanguage.Env var1);

        public abstract boolean isSynchronousTLAction(ThreadLocalAction var1);

        public abstract boolean isSideEffectingTLAction(ThreadLocalAction var1);

        public abstract boolean isRecurringTLAction(ThreadLocalAction var1);

        public abstract void performTLAction(ThreadLocalAction var1, ThreadLocalAction.Access var2);
    }

    public static abstract class EngineSupport
    extends Support {
        static final String IMPL_CLASS_NAME = "com.oracle.truffle.polyglot.EngineAccessor$EngineImpl";

        protected EngineSupport() {
            super(IMPL_CLASS_NAME);
        }

        public abstract <T> Iterable<T> loadServices(Class<T> var1);

        public abstract Object getInstrumentationHandler(Object var1);

        public abstract Object getInstrumentationHandler(RootNode var1);

        public abstract void exportSymbol(Object var1, String var2, Object var3);

        public abstract Map<String, ? extends Object> getExportedSymbols();

        public abstract Object getPolyglotBindingsObject();

        public abstract Object importSymbol(Object var1, TruffleLanguage.Env var2, String var3);

        public abstract boolean isMimeTypeSupported(Object var1, String var2);

        public abstract boolean isEvalRoot(RootNode var1);

        public abstract boolean isMultiThreaded(Object var1);

        public final void attachOutputConsumer(DispatchOutputStream dos, OutputStream out) {
            dos.attach(out);
        }

        public final void detachOutputConsumer(DispatchOutputStream dos, OutputStream out) {
            dos.detach(out);
        }

        public abstract Object getCurrentSharingLayer();

        public abstract Object getCurrentPolyglotEngine();

        public abstract CallTarget parseForLanguage(Object var1, com.oracle.truffle.api.source.Source var2, String[] var3, boolean var4);

        public abstract TruffleLanguage.Env getEnvForInstrument(String var1, String var2);

        public abstract TruffleLanguage.Env getEnvForInstrument(LanguageInfo var1);

        public abstract boolean hasCurrentContext();

        public abstract boolean isDisposed(Object var1);

        public abstract Map<String, LanguageInfo> getInternalLanguages(Object var1);

        public abstract Map<String, LanguageInfo> getPublicLanguages(Object var1);

        public abstract Map<String, InstrumentInfo> getInstruments(Object var1);

        public abstract org.graalvm.polyglot.SourceSection createSourceSection(Object var1, Source var2, SourceSection var3);

        public abstract <T> T lookup(InstrumentInfo var1, Class<T> var2);

        public abstract <S> S lookup(LanguageInfo var1, Class<S> var2);

        public abstract <T extends TruffleLanguage<?>> T getCurrentLanguage(Class<T> var1);

        public abstract <C, T extends TruffleLanguage<C>> C getCurrentContext(Class<T> var1);

        public abstract TruffleContext getTruffleContext(Object var1);

        public abstract TruffleContext getCurrentCreatorTruffleContext();

        public abstract Object toGuestValue(Node var1, Object var2, Object var3);

        public abstract Object getPolyglotEngine(Object var1);

        public abstract Object getPolyglotSharingLayer(Object var1);

        public abstract Object lookupHostSymbol(Object var1, TruffleLanguage.Env var2, String var3);

        public abstract Object asHostSymbol(Object var1, Class<?> var2);

        public abstract boolean isHostAccessAllowed(Object var1, TruffleLanguage.Env var2);

        public abstract boolean isNativeAccessAllowed(Object var1, TruffleLanguage.Env var2);

        public abstract boolean isInnerContextOptionsAllowed(Object var1, TruffleLanguage.Env var2);

        public abstract boolean isCurrentNativeAccessAllowed(Node var1);

        public abstract boolean inContextPreInitialization(Object var1);

        public abstract TruffleContext createInternalContext(Object var1, OutputStream var2, OutputStream var3, InputStream var4, ZoneId var5, String[] var6, Map<String, Object> var7, Map<String, String> var8, Map<String, String[]> var9, Boolean var10, boolean var11, Runnable var12, Consumer<Integer> var13, Runnable var14, boolean var15, Boolean var16, Boolean var17, Boolean var18, Boolean var19, Boolean var20, Boolean var21, Boolean var22, Boolean var23, Map<String, String> var24, Boolean var25);

        public abstract Object enterInternalContext(Node var1, Object var2);

        public abstract void leaveInternalContext(Node var1, Object var2, Object var3);

        public abstract Object[] enterContextAsPolyglotThread(Object var1);

        public abstract void leaveContextAsPolyglotThread(Object var1, Object[] var2);

        public abstract Object enterIfNeeded(Object var1);

        public abstract void leaveIfNeeded(Object var1, Object var2);

        public abstract boolean initializeInnerContext(Node var1, Object var2, String var3, boolean var4);

        public abstract Object evalInternalContext(Node var1, Object var2, com.oracle.truffle.api.source.Source var3, boolean var4);

        public abstract void clearExplicitContextStack(Object var1);

        public abstract void initiateCancelOrExit(Object var1, boolean var2, int var3, boolean var4, String var5);

        public abstract void closeContext(Object var1, boolean var2, Node var3, boolean var4, String var5);

        public abstract void closeContext(Object var1, boolean var2, boolean var3, String var4);

        public abstract void closeEngine(Object var1, boolean var2);

        public abstract void exitContext(Object var1, Node var2, int var3);

        public abstract boolean isContextEntered(Object var1);

        public abstract boolean isContextActive(Object var1);

        public abstract void reportAllLanguageContexts(Object var1, Object var2);

        public abstract void reportAllContextThreads(Object var1, Object var2);

        public abstract TruffleContext getParentContext(Object var1);

        public abstract boolean isCreateThreadAllowed(Object var1);

        public final Thread createThread(Object polyglotLanguageContext, Runnable runnable, Object innerContextImpl, ThreadGroup group) {
            return this.createThread(polyglotLanguageContext, runnable, innerContextImpl, group, 0L);
        }

        public final Thread createThread(Object polyglotLanguageContext, Runnable runnable, Object innerContextImpl) {
            return this.createThread(polyglotLanguageContext, runnable, innerContextImpl, null, 0L);
        }

        public abstract Thread createThread(Object var1, Runnable var2, Object var3, ThreadGroup var4, long var5);

        public abstract RuntimeException wrapHostException(Node var1, Object var2, Throwable var3);

        public abstract boolean isHostException(Object var1, Throwable var2);

        public abstract Throwable asHostException(Object var1, Throwable var2);

        public abstract Object getCurrentHostContext();

        public abstract PolyglotException wrapGuestException(Object var1, Throwable var2);

        public abstract PolyglotException wrapGuestException(String var1, Throwable var2);

        public abstract <T> T getOrCreateRuntimeData(Object var1);

        public abstract Set<? extends Class<?>> getProvidedTags(LanguageInfo var1);

        public abstract Object getPolyglotBindingsForLanguage(Object var1);

        public abstract Object findMetaObjectForLanguage(Object var1, Object var2);

        public abstract boolean isInternal(FileSystem var1);

        public abstract boolean hasAllAccess(FileSystem var1);

        public abstract boolean hasNoAccess(FileSystem var1);

        public abstract boolean isInternal(TruffleFile var1);

        public abstract String getLanguageHome(LanguageInfo var1);

        public abstract void addToHostClassPath(Object var1, TruffleFile var2);

        public abstract boolean isInstrumentExceptionsAreThrown(Object var1);

        public abstract Object asBoxedGuestValue(Object var1, Object var2);

        public abstract Object createDefaultLoggerCache();

        public abstract Object getContextLoggerCache(Object var1);

        public abstract Handler getLogHandler(Object var1);

        public abstract Map<String, Level> getLogLevels(Object var1);

        public abstract Object getLoggerOwner(Object var1);

        public abstract TruffleLogger getLogger(Object var1, String var2);

        public abstract LogRecord createLogRecord(Object var1, Level var2, String var3, String var4, String var5, String var6, Object[] var7, Throwable var8);

        public abstract Object getOuterContext(Object var1);

        public abstract boolean isCharacterBasedSource(Object var1, String var2, String var3);

        public abstract Set<String> getValidMimeTypes(Object var1, String var2);

        public abstract Object asHostObject(Object var1, Object var2);

        public abstract boolean isHostObject(Object var1, Object var2);

        public abstract boolean isHostFunction(Object var1, Object var2);

        public abstract boolean isHostSymbol(Object var1, Object var2);

        public abstract <S> S lookupService(Object var1, LanguageInfo var2, LanguageInfo var3, Class<S> var4);

        public abstract <T extends TruffleLanguage<C>, C> TruffleLanguage.ContextReference<C> createContextReference(Class<T> var1);

        public abstract <T extends TruffleLanguage<?>> TruffleLanguage.LanguageReference<T> createLanguageReference(Class<T> var1);

        public abstract FileSystem getFileSystem(Object var1);

        public abstract boolean isPolyglotEvalAllowed(Object var1);

        public abstract boolean isPolyglotBindingsAccessAllowed(Object var1);

        public abstract TruffleFile getTruffleFile(String var1);

        public abstract TruffleFile getTruffleFile(URI var1);

        public abstract int getAsynchronousStackDepth(Object var1);

        public abstract void setAsynchronousStackDepth(Object var1, int var2);

        public abstract boolean isCreateProcessAllowed(Object var1);

        public abstract Map<String, String> getProcessEnvironment(Object var1);

        public abstract Process createSubProcess(Object var1, List<String> var2, String var3, Map<String, String> var4, boolean var5, ProcessHandler.Redirect var6, ProcessHandler.Redirect var7, ProcessHandler.Redirect var8) throws IOException;

        public abstract boolean hasDefaultProcessHandler(Object var1);

        public abstract ProcessHandler.Redirect createRedirectToOutputStream(Object var1, OutputStream var2);

        public abstract boolean isIOAllowed(Object var1, TruffleLanguage.Env var2);

        public abstract boolean isIOSupported();

        public abstract boolean isCreateProcessSupported();

        public abstract ZoneId getTimeZone(Object var1);

        public abstract Set<String> getLanguageIds();

        public abstract Set<String> getInstrumentIds();

        public abstract Set<String> getInternalIds();

        public abstract String getUnparsedOptionValue(OptionValues var1, OptionKey<?> var2);

        public abstract String getRelativePathInLanguageHome(TruffleFile var1);

        public abstract void onSourceCreated(com.oracle.truffle.api.source.Source var1);

        public abstract void registerOnDispose(Object var1, Closeable var2);

        public abstract String getReinitializedPath(TruffleFile var1);

        public abstract URI getReinitializedURI(TruffleFile var1);

        public abstract LanguageInfo getLanguageInfo(Object var1, Class<? extends TruffleLanguage<?>> var2);

        public abstract Object getDefaultLanguageView(TruffleLanguage<?> var1, Object var2);

        public abstract Object getLanguageView(LanguageInfo var1, Object var2);

        public abstract boolean initializeLanguage(Object var1, LanguageInfo var2);

        public abstract RuntimeException engineToLanguageException(Throwable var1);

        public abstract RuntimeException engineToInstrumentException(Throwable var1);

        public abstract Object getCurrentFileSystemContext();

        public abstract Object getPublicFileSystemContext(Object var1);

        public abstract Object getInternalFileSystemContext(Object var1);

        public abstract Map<String, Collection<? extends TruffleFile.FileTypeDetector>> getEngineFileTypeDetectors(Object var1);

        public abstract boolean skipEngineValidation(RootNode var1);

        public abstract AssertionError invalidSharingError(Node var1, Object var2, Object var3) throws AssertionError;

        public abstract boolean isPolyglotObject(Object var1);

        public abstract void initializeLanguageContextLocal(List<? extends ContextLocal<?>> var1, Object var2);

        public abstract void initializeLanguageContextThreadLocal(List<? extends ContextThreadLocal<?>> var1, Object var2);

        public abstract void initializeInstrumentContextLocal(List<? extends ContextLocal<?>> var1, Object var2);

        public abstract void initializeInstrumentContextThreadLocal(List<? extends ContextThreadLocal<?>> var1, Object var2);

        public abstract <T> ContextThreadLocal<T> createLanguageContextThreadLocal(Object var1);

        public abstract <T> ContextThreadLocal<T> createInstrumentContextThreadLocal(Object var1);

        public abstract <T> ContextLocal<T> createLanguageContextLocal(Object var1);

        public abstract <T> ContextLocal<T> createInstrumentContextLocal(Object var1);

        public abstract OptionValues getInstrumentContextOptions(Object var1, Object var2);

        public abstract boolean isContextClosed(Object var1);

        public abstract boolean isContextCancelling(Object var1);

        public abstract boolean isContextExiting(Object var1);

        public abstract Future<Void> pause(Object var1);

        public abstract void resume(Object var1, Future<Void> var2);

        public abstract <T, G> Iterator<T> mergeHostGuestFrames(Object var1, StackTraceElement[] var2, Iterator<G> var3, boolean var4, Function<StackTraceElement, T> var5, Function<G, T> var6);

        public abstract Object createHostAdapterClass(Object var1, Object[] var2, Object var3);

        public abstract OptionValues getEngineOptionValues(Object var1);

        public abstract Collection<? extends CallTarget> findCallTargets(Object var1);

        public abstract void preinitializeContext(Object var1);

        public abstract void finalizeStore(Object var1);

        public abstract Object getEngineLock(Object var1);

        public abstract long calculateContextHeapSize(Object var1, long var2, AtomicBoolean var4);

        public abstract Future<Void> submitThreadLocal(Object var1, Object var2, Thread[] var3, ThreadLocalAction var4, boolean var5);

        public abstract Object getContext(Object var1);

        public abstract ClassLoader getStaticObjectClassLoader(Object var1, Class<?> var2);

        public abstract void setStaticObjectClassLoader(Object var1, Class<?> var2, ClassLoader var3);

        public abstract ConcurrentHashMap<Pair<Class<?>, Class<?>>, Object> getGeneratorCache(Object var1);

        public abstract boolean areStaticObjectSafetyChecksRelaxed(Object var1);

        public abstract String getStaticObjectStorageStrategy(Object var1);

        public abstract Object getHostContext(Object var1);

        public abstract Value asValue(Object var1, Object var2);

        public abstract Object enterLanguageFromRuntime(TruffleLanguage<?> var1);

        public abstract void leaveLanguageFromRuntime(TruffleLanguage<?> var1, Object var2);

        public abstract Throwable getPolyglotExceptionCause(Object var1);

        public abstract Object getPolyglotExceptionContext(Object var1);

        public abstract Object getPolyglotExceptionEngine(Object var1);

        public abstract boolean isCancelExecution(Throwable var1);

        public abstract boolean isExitException(Throwable var1);

        public abstract boolean isInterruptExecution(Throwable var1);

        public abstract boolean isResourceLimitCancelExecution(Throwable var1);

        public abstract boolean isPolyglotEngineException(Throwable var1);

        public abstract Throwable getPolyglotEngineExceptionCause(Throwable var1);

        public abstract RuntimeException createPolyglotEngineException(RuntimeException var1);

        public abstract int getExitExceptionExitCode(Throwable var1);

        public abstract SourceSection getCancelExecutionSourceLocation(Throwable var1);

        public abstract ThreadDeath createCancelExecution(SourceSection var1, String var2, boolean var3);

        public abstract SourceSection getExitExceptionSourceLocation(Throwable var1);

        public abstract ThreadDeath createExitException(SourceSection var1, String var2, int var3);

        public abstract Throwable createInterruptExecution(SourceSection var1);

        public abstract Map<String, String> readOptionsFromSystemProperties(Map<String, String> var1);

        public abstract AbstractPolyglotImpl.AbstractHostLanguageService getHostService(Object var1);

        public abstract Handler getEngineLogHandler(Object var1);

        public abstract Handler getContextLogHandler(Object var1);

        public abstract LogRecord createLogRecord(Level var1, String var2, String var3, String var4, String var5, Object[] var6, Throwable var7, String var8);

        public abstract String getFormatKind(LogRecord var1);

        public abstract boolean isPolyglotThread(Thread var1);

        public abstract Object getHostNull();

        public abstract Object getGuestToHostCodeCache(Object var1);

        public abstract Object installGuestToHostCodeCache(Object var1, Object var2);

        public abstract boolean getNeedsAllEncodings();

        public abstract boolean requireLanguageWithAllEncodings(Object var1);

        public abstract AutoCloseable createPolyglotThreadScope();

        public abstract Engine getPolyglotEngineAPI(Object var1);

        public abstract Context getPolyglotContextAPI(Object var1);

        public abstract EncapsulatingNodeReference getEncapsulatingNodeReference(boolean var1);

        public abstract Thread createInstrumentSystemThread(Object var1, Runnable var2, ThreadGroup var3);

        public abstract Thread createLanguageSystemThread(Object var1, Runnable var2, ThreadGroup var3);
    }

    public static abstract class HostSupport
    extends Support {
        static final String IMPL_CLASS_NAME = "com.oracle.truffle.host.HostAccessor$HostImpl";

        protected HostSupport() {
            super(IMPL_CLASS_NAME);
        }

        public abstract TruffleLanguage<?> createDefaultHostLanguage(AbstractPolyglotImpl var1, AbstractPolyglotImpl.AbstractHostAccess var2);

        public abstract boolean isHostBoundaryValue(Object var1);

        public abstract Object convertPrimitiveLossLess(Object var1, Class<?> var2);

        public abstract Object convertPrimitiveLossy(Object var1, Class<?> var2);

        public abstract boolean isDisconnectedHostProxy(Object var1);

        public abstract boolean isDisconnectedHostObject(Object var1);

        public abstract Object unboxDisconnectedHostObject(Object var1);

        public abstract Object unboxDisconnectedHostProxy(Object var1);

        public abstract Object toDisconnectedHostObject(Object var1);

        public abstract Object toDisconnectedHostProxy(Proxy var1);

        public abstract <S, T> Object newTargetTypeMapping(Class<S> var1, Class<T> var2, Predicate<S> var3, Function<S, T> var4, HostAccess.TargetMappingPrecedence var5);

        public abstract Object getHostNull();

        public abstract boolean isPrimitiveTarget(Class<?> var1);

        public abstract boolean isGuestToHostRootNode(RootNode var1);

        public abstract boolean isHostLanguage(Class<?> var1);
    }

    public static abstract class InteropSupport
    extends Support {
        static final String IMPL_CLASS_NAME = "com.oracle.truffle.api.interop.InteropAccessor$InteropImpl";

        protected InteropSupport() {
            super(IMPL_CLASS_NAME);
        }

        public abstract boolean isTruffleObject(Object var1);

        public abstract void checkInteropType(Object var1);

        public abstract boolean isExecutableObject(Object var1);

        public abstract Object createDefaultNodeObject(Node var1);

        public abstract boolean isScopeObject(Object var1);

        public abstract Object createDefaultIterator(Object var1);

        public abstract Node createDispatchedInteropLibrary(int var1);

        public abstract Node getUncachedInteropLibrary();

        public abstract long unboxPointer(Node var1, Object var2);
    }

    public static abstract class SourceSupport
    extends Support {
        static final String IMPL_CLASS_NAME = "com.oracle.truffle.api.source.SourceAccessor$SourceSupportImpl";

        protected SourceSupport() {
            super(IMPL_CLASS_NAME);
        }

        public abstract Object getSourceIdentifier(com.oracle.truffle.api.source.Source var1);

        public abstract com.oracle.truffle.api.source.Source copySource(com.oracle.truffle.api.source.Source var1);

        public abstract Source getOrCreatePolyglotSource(com.oracle.truffle.api.source.Source var1, Function<com.oracle.truffle.api.source.Source, Source> var2);

        public abstract String findMimeType(URL var1, Object var2) throws IOException;

        public abstract Source.SourceBuilder newBuilder(String var1, File var2);

        public abstract void setFileSystemContext(Source.SourceBuilder var1, Object var2);

        public abstract void invalidateAfterPreinitialiation(com.oracle.truffle.api.source.Source var1);

        public abstract void mergeLoadedSources(com.oracle.truffle.api.source.Source[] var1);

        public abstract void setEmbedderSource(Source.SourceBuilder var1, boolean var2);

        public abstract void setURL(Source.SourceBuilder var1, URL var2);

        public abstract void setPath(Source.SourceBuilder var1, String var2);
    }

    public static abstract class NodeSupport
    extends Support {
        static final String IMPL_CLASS_NAME = "com.oracle.truffle.api.nodes.NodeAccessor$AccessNodes";

        protected NodeSupport() {
            super(IMPL_CLASS_NAME);
        }

        public abstract boolean isInstrumentable(RootNode var1);

        public abstract boolean isCloneUninitializedSupported(RootNode var1);

        public abstract RootNode cloneUninitialized(CallTarget var1, RootNode var2, RootNode var3);

        public abstract int adoptChildrenAndCount(RootNode var1);

        public abstract Object getLanguageCache(LanguageInfo var1);

        public abstract TruffleLanguage<?> getLanguage(RootNode var1);

        public abstract LanguageInfo createLanguage(Object var1, String var2, String var3, String var4, String var5, Set<String> var6, boolean var7, boolean var8);

        public abstract Object getSharingLayer(RootNode var1);

        public abstract List<TruffleStackTraceElement> findAsynchronousFrames(CallTarget var1, Frame var2);

        public abstract int getRootNodeBits(RootNode var1);

        public abstract void setRootNodeBits(RootNode var1, int var2);

        public abstract Lock getLock(Node var1);

        public abstract void applySharingLayer(RootNode var1, RootNode var2);

        public abstract void forceAdoption(Node var1, Node var2);

        public abstract boolean isTrivial(RootNode var1);

        public abstract FrameDescriptor getParentFrameDescriptor(RootNode var1);

        public abstract Object translateStackTraceElement(TruffleStackTraceElement var1);

        public abstract ExecutionSignature prepareForAOT(RootNode var1);

        public abstract void setSharingLayer(RootNode var1, Object var2);

        public abstract boolean countsTowardsStackTraceLimit(RootNode var1);

        public abstract CallTarget getCallTargetWithoutInitialization(RootNode var1);

        public abstract EncapsulatingNodeReference createEncapsulatingNodeReference(Thread var1);
    }

    static abstract class Support {
        Support(String onlyAllowedClassName) {
            if (!this.getClass().getName().equals(onlyAllowedClassName)) {
                throw new AssertionError((Object)("No custom subclasses of support classes allowed. Implementation must be " + onlyAllowedClassName + "."));
            }
        }
    }
}

