
package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.ContextLocal;
import com.oracle.truffle.api.ContextThreadLocal;
import com.oracle.truffle.api.InstrumentInfo;
import com.oracle.truffle.api.ThreadLocalAction;
import com.oracle.truffle.api.TruffleContext;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleLogger;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentAccessor;
import com.oracle.truffle.api.instrumentation.InstrumentationHandler;
import com.oracle.truffle.api.instrumentation.Instrumenter;
import com.oracle.truffle.api.nodes.ExecutableNode;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.Source;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import org.graalvm.options.OptionDescriptors;
import org.graalvm.options.OptionValues;
import org.graalvm.polyglot.io.MessageEndpoint;
import org.graalvm.polyglot.io.MessageTransport;

public abstract class TruffleInstrument {
    List<ContextThreadLocal<?>> contextThreadLocals;
    List<ContextLocal<?>> contextLocals;

    protected TruffleInstrument() {
    }

    protected abstract void onCreate(Env var1);

    protected void onFinalize(Env env) {
    }

    protected void onDispose(Env env) {
    }

    protected OptionDescriptors getOptionDescriptors() {
        return OptionDescriptors.EMPTY;
    }

    protected OptionDescriptors getContextOptionDescriptors() {
        return OptionDescriptors.EMPTY;
    }

    protected final <T> ContextLocal<T> createContextLocal(ContextLocalFactory<T> factory) {
        ContextLocal local = InstrumentAccessor.ENGINE.createInstrumentContextLocal(factory);
        if (this.contextLocals == null) {
            this.contextLocals = new ArrayList();
        }
        try {
            this.contextLocals.add(local);
        }
        catch (UnsupportedOperationException e) {
            throw new IllegalStateException("The set of context locals is frozen. Context locals can only be created during construction of the TruffleInstrument subclass.");
        }
        return local;
    }

    protected final <T> ContextThreadLocal<T> createContextThreadLocal(ContextThreadLocalFactory<T> factory) {
        ContextThreadLocal local = InstrumentAccessor.ENGINE.createInstrumentContextThreadLocal(factory);
        if (this.contextThreadLocals == null) {
            this.contextThreadLocals = new ArrayList();
        }
        try {
            this.contextThreadLocals.add(local);
        }
        catch (UnsupportedOperationException e) {
            throw new IllegalStateException("The set of context thread locals is frozen. Context thread locals can only be created during construction of the TruffleInstrument subclass.");
        }
        return local;
    }

    static {
        try {
            Class.forName(InstrumentationHandler.class.getName(), true, InstrumentationHandler.class.getClassLoader());
        }
        catch (ClassNotFoundException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public static interface Provider {
        public String getInstrumentClassName();

        public TruffleInstrument create();

        public Collection<String> getServicesClassNames();
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.TYPE})
    public static @interface Registration {
        public String id() default "";

        public String name() default "";

        public String version() default "inherit";

        public boolean internal() default false;

        public Class<?>[] services() default {};

        public String website() default "";
    }

    public static final class Env {
        private final Object polyglotInstrument;
        private final InputStream in;
        private final OutputStream err;
        private final OutputStream out;
        private final MessageTransport messageTransport;
        OptionValues options;
        InstrumentationHandler.InstrumentClientInstrumenter instrumenter;
        private List<Object> services;

        Env(Object polyglotInstrument, OutputStream out, OutputStream err, InputStream in, MessageTransport messageInterceptor) {
            this.polyglotInstrument = polyglotInstrument;
            this.in = in;
            this.err = err;
            this.out = out;
            this.messageTransport = messageInterceptor != null ? new MessageTransportProxy(messageInterceptor) : null;
        }

        Object getPolyglotInstrument() {
            return this.polyglotInstrument;
        }

        public Instrumenter getInstrumenter() {
            return this.instrumenter;
        }

        public InputStream in() {
            return this.in;
        }

        public OutputStream out() {
            return this.out;
        }

        public OutputStream err() {
            return this.err;
        }

        public MessageEndpoint startServer(URI uri, MessageEndpoint server) throws IOException, MessageTransport.VetoException {
            if (this.messageTransport == null) {
                return null;
            }
            return this.messageTransport.open(uri, server);
        }

        public void registerService(Object service2) {
            if (this.services == null) {
                throw new IllegalStateException();
            }
            this.services.add(service2);
        }

        @CompilerDirectives.TruffleBoundary
        static <T extends RuntimeException> RuntimeException engineToInstrumentException(Throwable t) {
            return InstrumentAccessor.engineAccess().engineToInstrumentException(t);
        }

        public <S> S lookup(LanguageInfo language, Class<S> type) {
            try {
                return InstrumentAccessor.engineAccess().lookup(language, type);
            }
            catch (Throwable t) {
                throw Env.engineToInstrumentException(t);
            }
        }

        public <S> S lookup(InstrumentInfo instrument, Class<S> type) {
            try {
                Object vm = InstrumentAccessor.langAccess().getPolyglotInstrument(instrument);
                if (vm == this.polyglotInstrument) {
                    throw new IllegalArgumentException("Not allowed to lookup services from the currrent instrument.");
                }
                return InstrumentAccessor.engineAccess().lookup(instrument, type);
            }
            catch (Throwable t) {
                throw Env.engineToInstrumentException(t);
            }
        }

        public Map<String, LanguageInfo> getLanguages() {
            try {
                return InstrumentAccessor.engineAccess().getInternalLanguages(this.polyglotInstrument);
            }
            catch (Throwable t) {
                throw Env.engineToInstrumentException(t);
            }
        }

        public Map<String, InstrumentInfo> getInstruments() {
            try {
                return InstrumentAccessor.engineAccess().getInstruments(this.polyglotInstrument);
            }
            catch (Throwable t) {
                throw Env.engineToInstrumentException(t);
            }
        }

        Object[] onCreate(TruffleInstrument instrument) {
            ArrayList<Object> arr = new ArrayList<Object>();
            this.services = arr;
            try {
                instrument.onCreate(this);
            }
            finally {
                this.services = null;
            }
            return arr.toArray();
        }

        public OptionValues getOptions() {
            return this.options;
        }

        @CompilerDirectives.TruffleBoundary
        public OptionValues getOptions(TruffleContext context) {
            Objects.requireNonNull(context);
            return InstrumentAccessor.ENGINE.getInstrumentContextOptions(this.polyglotInstrument, InstrumentAccessor.LANGUAGE.getPolyglotContext(context));
        }

        public CallTarget parse(Source source, String ... argumentNames) throws IOException {
            try {
                TruffleLanguage.Env env = InstrumentAccessor.ENGINE.getEnvForInstrument(source.getLanguage(), source.getMimeType());
                Object languageContext = InstrumentAccessor.LANGUAGE.getPolyglotLanguageContext(env);
                return InstrumentAccessor.ENGINE.parseForLanguage(languageContext, source, argumentNames, true);
            }
            catch (Throwable t) {
                throw Env.engineToInstrumentException(t);
            }
        }

        public ExecutableNode parseInline(Source source, Node node, MaterializedFrame frame) {
            try {
                if (node == null) {
                    throw new IllegalArgumentException("Node must not be null.");
                }
                TruffleLanguage.Env env = InstrumentAccessor.engineAccess().getEnvForInstrument(source.getLanguage(), source.getMimeType());
                assert (InstrumentAccessor.langAccess().getLanguageInfo(env) == node.getRootNode().getLanguageInfo());
                ExecutableNode fragment = InstrumentAccessor.langAccess().parseInline(env, source, node, frame);
                if (fragment != null) {
                    TruffleLanguage<?> languageSPI = InstrumentAccessor.langAccess().getSPI(env);
                    fragment = new GuardedExecutableNode(languageSPI, fragment, frame);
                }
                return fragment;
            }
            catch (Throwable t) {
                throw Env.engineToInstrumentException(t);
            }
        }

        public TruffleFile getTruffleFile(String path) {
            try {
                return InstrumentAccessor.engineAccess().getTruffleFile(path);
            }
            catch (Throwable t) {
                throw Env.engineToInstrumentException(t);
            }
        }

        public TruffleFile getTruffleFile(URI uri) {
            try {
                return InstrumentAccessor.engineAccess().getTruffleFile(uri);
            }
            catch (Throwable t) {
                throw Env.engineToInstrumentException(t);
            }
        }

        public TruffleContext getEnteredContext() {
            return InstrumentAccessor.ENGINE.getCurrentCreatorTruffleContext();
        }

        private static boolean checkNullOrInterop(Object obj) {
            if (obj == null) {
                return true;
            }
            InstrumentAccessor.interopAccess().checkInteropType(obj);
            return true;
        }

        public boolean isEngineRoot(RootNode root) {
            try {
                return InstrumentAccessor.engineAccess().isEvalRoot(root);
            }
            catch (Throwable t) {
                throw Env.engineToInstrumentException(t);
            }
        }

        public void setAsynchronousStackDepth(int depth) {
            InstrumentAccessor.engineAccess().setAsynchronousStackDepth(this.polyglotInstrument, depth);
        }

        @CompilerDirectives.TruffleBoundary
        public LanguageInfo getLanguageInfo(Class<? extends TruffleLanguage<?>> languageClass) {
            try {
                Objects.requireNonNull(languageClass);
                return InstrumentAccessor.engineAccess().getLanguageInfo(this.polyglotInstrument, languageClass);
            }
            catch (Throwable t) {
                throw Env.engineToInstrumentException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public Object getLanguageView(LanguageInfo language, Object value2) {
            try {
                Objects.requireNonNull(language);
                return InstrumentAccessor.engineAccess().getLanguageView(language, value2);
            }
            catch (Throwable t) {
                throw Env.engineToInstrumentException(t);
            }
        }

        public Object getPolyglotBindings() {
            try {
                return InstrumentAccessor.engineAccess().getPolyglotBindingsObject();
            }
            catch (Throwable t) {
                throw Env.engineToInstrumentException(t);
            }
        }

        public Object getScope(LanguageInfo language) {
            assert (language != null);
            try {
                TruffleLanguage.Env env = InstrumentAccessor.engineAccess().getEnvForInstrument(language);
                return InstrumentAccessor.langAccess().getScope(env);
            }
            catch (Throwable t) {
                throw Env.engineToInstrumentException(t);
            }
        }

        public TruffleLogger getLogger(String loggerName) {
            try {
                return InstrumentAccessor.engineAccess().getLogger(this.polyglotInstrument, loggerName);
            }
            catch (Throwable t) {
                throw Env.engineToInstrumentException(t);
            }
        }

        public TruffleLogger getLogger(Class<?> forClass) {
            return this.getLogger(forClass.getName());
        }

        public long calculateContextHeapSize(TruffleContext truffleContext, long stopAtBytes, AtomicBoolean cancelled) {
            return InstrumentAccessor.engineAccess().calculateContextHeapSize(InstrumentAccessor.langAccess().getPolyglotContext(truffleContext), stopAtBytes, cancelled);
        }

        public Future<Void> submitThreadLocal(TruffleContext context, Thread[] threads, ThreadLocalAction action2) {
            Objects.requireNonNull(context);
            try {
                return InstrumentAccessor.ENGINE.submitThreadLocal(InstrumentAccessor.LANGUAGE.getPolyglotContext(context), this.polyglotInstrument, threads, action2, true);
            }
            catch (Throwable t) {
                throw Env.engineToInstrumentException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public Thread createSystemThread(Runnable runnable) {
            return this.createSystemThread(runnable, null);
        }

        @CompilerDirectives.TruffleBoundary
        public Thread createSystemThread(Runnable runnable, ThreadGroup threadGroup) {
            Objects.requireNonNull(runnable, "Runnable must be non null.");
            try {
                return InstrumentAccessor.ENGINE.createInstrumentSystemThread(this.polyglotInstrument, runnable, threadGroup);
            }
            catch (Throwable t) {
                throw Env.engineToInstrumentException(t);
            }
        }

        private static class MessageTransportProxy
        implements MessageTransport {
            private final MessageTransport transport;

            MessageTransportProxy(MessageTransport transport) {
                this.transport = transport;
            }

            @Override
            public MessageEndpoint open(URI uri, MessageEndpoint peerEndpoint) throws IOException, MessageTransport.VetoException {
                Objects.requireNonNull(peerEndpoint, "The peer endpoint must be non null.");
                MessageEndpoint openedEndpoint = this.transport.open(uri, new MessageEndpointProxy(peerEndpoint));
                if (openedEndpoint == null) {
                    return null;
                }
                return new MessageEndpointProxy(openedEndpoint);
            }

            private static class MessageEndpointProxy
            implements MessageEndpoint {
                private final MessageEndpoint endpoint;

                MessageEndpointProxy(MessageEndpoint endpoint) {
                    this.endpoint = endpoint;
                }

                @Override
                public void sendText(String text) throws IOException {
                    this.endpoint.sendText(text);
                }

                @Override
                public void sendBinary(ByteBuffer data) throws IOException {
                    this.endpoint.sendBinary(data);
                }

                @Override
                public void sendPing(ByteBuffer data) throws IOException {
                    this.endpoint.sendPing(data);
                }

                @Override
                public void sendPong(ByteBuffer data) throws IOException {
                    this.endpoint.sendPong(data);
                }

                @Override
                public void sendClose() throws IOException {
                    this.endpoint.sendClose();
                }
            }
        }

        private static class GuardedExecutableNode
        extends ExecutableNode {
            private final FrameDescriptor frameDescriptor;
            @Node.Child
            private ExecutableNode fragment;

            GuardedExecutableNode(TruffleLanguage<?> languageSPI, ExecutableNode fragment, MaterializedFrame frameLocation) {
                super(languageSPI);
                this.frameDescriptor = frameLocation != null ? frameLocation.getFrameDescriptor() : null;
                this.fragment = fragment;
            }

            @Override
            public Object execute(VirtualFrame frame) {
                assert (this.frameDescriptor == null || this.frameDescriptor == frame.getFrameDescriptor());
                this.assureAdopted();
                Object ret = this.fragment.execute(frame);
                assert (Env.checkNullOrInterop(ret));
                return ret;
            }

            private void assureAdopted() {
                if (this.getParent() == null) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    throw new IllegalStateException("Needs to be inserted into the AST before execution.");
                }
            }
        }
    }

    @FunctionalInterface
    protected static interface ContextThreadLocalFactory<T> {
        public T create(TruffleContext var1, Thread var2);
    }

    @FunctionalInterface
    protected static interface ContextLocalFactory<T> {
        public T create(TruffleContext var1);
    }
}

