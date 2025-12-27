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

   protected abstract void onCreate(TruffleInstrument.Env env);

   protected void onFinalize(TruffleInstrument.Env env) {
   }

   protected void onDispose(TruffleInstrument.Env env) {
   }

   protected OptionDescriptors getOptionDescriptors() {
      return OptionDescriptors.EMPTY;
   }

   protected OptionDescriptors getContextOptionDescriptors() {
      return OptionDescriptors.EMPTY;
   }

   protected final <T> ContextLocal<T> createContextLocal(TruffleInstrument.ContextLocalFactory<T> factory) {
      ContextLocal<T> local = InstrumentAccessor.ENGINE.createInstrumentContextLocal(factory);
      if (this.contextLocals == null) {
         this.contextLocals = new ArrayList<>();
      }

      try {
         this.contextLocals.add(local);
         return local;
      } catch (UnsupportedOperationException var4) {
         throw new IllegalStateException(
            "The set of context locals is frozen. Context locals can only be created during construction of the TruffleInstrument subclass."
         );
      }
   }

   protected final <T> ContextThreadLocal<T> createContextThreadLocal(TruffleInstrument.ContextThreadLocalFactory<T> factory) {
      ContextThreadLocal<T> local = InstrumentAccessor.ENGINE.createInstrumentContextThreadLocal(factory);
      if (this.contextThreadLocals == null) {
         this.contextThreadLocals = new ArrayList<>();
      }

      try {
         this.contextThreadLocals.add(local);
         return local;
      } catch (UnsupportedOperationException var4) {
         throw new IllegalStateException(
            "The set of context thread locals is frozen. Context thread locals can only be created during construction of the TruffleInstrument subclass."
         );
      }
   }

   static {
      try {
         Class.forName(InstrumentationHandler.class.getName(), true, InstrumentationHandler.class.getClassLoader());
      } catch (ClassNotFoundException var1) {
         throw new IllegalStateException(var1);
      }
   }

   @FunctionalInterface
   protected interface ContextLocalFactory<T> {
      T create(TruffleContext context);
   }

   @FunctionalInterface
   protected interface ContextThreadLocalFactory<T> {
      T create(TruffleContext context, Thread thread);
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
         this.messageTransport = messageInterceptor != null ? new TruffleInstrument.Env.MessageTransportProxy(messageInterceptor) : null;
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
         return this.messageTransport == null ? null : this.messageTransport.open(uri, server);
      }

      public void registerService(Object service) {
         if (this.services == null) {
            throw new IllegalStateException();
         } else {
            this.services.add(service);
         }
      }

      @CompilerDirectives.TruffleBoundary
      static <T extends RuntimeException> RuntimeException engineToInstrumentException(Throwable t) {
         return InstrumentAccessor.engineAccess().engineToInstrumentException(t);
      }

      public <S> S lookup(LanguageInfo language, Class<S> type) {
         try {
            return InstrumentAccessor.engineAccess().lookup(language, type);
         } catch (Throwable var4) {
            throw engineToInstrumentException(var4);
         }
      }

      public <S> S lookup(InstrumentInfo instrument, Class<S> type) {
         try {
            Object vm = InstrumentAccessor.langAccess().getPolyglotInstrument(instrument);
            if (vm == this.polyglotInstrument) {
               throw new IllegalArgumentException("Not allowed to lookup services from the currrent instrument.");
            } else {
               return InstrumentAccessor.engineAccess().lookup(instrument, type);
            }
         } catch (Throwable var4) {
            throw engineToInstrumentException(var4);
         }
      }

      public Map<String, LanguageInfo> getLanguages() {
         try {
            return InstrumentAccessor.engineAccess().getInternalLanguages(this.polyglotInstrument);
         } catch (Throwable var2) {
            throw engineToInstrumentException(var2);
         }
      }

      public Map<String, InstrumentInfo> getInstruments() {
         try {
            return InstrumentAccessor.engineAccess().getInstruments(this.polyglotInstrument);
         } catch (Throwable var2) {
            throw engineToInstrumentException(var2);
         }
      }

      Object[] onCreate(TruffleInstrument instrument) {
         List<Object> arr = new ArrayList<>();
         this.services = arr;

         try {
            instrument.onCreate(this);
         } finally {
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

      public CallTarget parse(Source source, String... argumentNames) throws IOException {
         try {
            TruffleLanguage.Env env = InstrumentAccessor.ENGINE.getEnvForInstrument(source.getLanguage(), source.getMimeType());
            Object languageContext = InstrumentAccessor.LANGUAGE.getPolyglotLanguageContext(env);
            return InstrumentAccessor.ENGINE.parseForLanguage(languageContext, source, argumentNames, true);
         } catch (Throwable var5) {
            throw engineToInstrumentException(var5);
         }
      }

      public ExecutableNode parseInline(Source source, Node node, MaterializedFrame frame) {
         try {
            if (node == null) {
               throw new IllegalArgumentException("Node must not be null.");
            } else {
               TruffleLanguage.Env env = InstrumentAccessor.engineAccess().getEnvForInstrument(source.getLanguage(), source.getMimeType());

               assert InstrumentAccessor.langAccess().getLanguageInfo(env) == node.getRootNode().getLanguageInfo();

               ExecutableNode fragment = InstrumentAccessor.langAccess().parseInline(env, source, node, frame);
               if (fragment != null) {
                  TruffleLanguage<?> languageSPI = InstrumentAccessor.langAccess().getSPI(env);
                  fragment = new TruffleInstrument.Env.GuardedExecutableNode(languageSPI, fragment, frame);
               }

               return fragment;
            }
         } catch (Throwable var7) {
            throw engineToInstrumentException(var7);
         }
      }

      public TruffleFile getTruffleFile(String path) {
         try {
            return InstrumentAccessor.engineAccess().getTruffleFile(path);
         } catch (Throwable var3) {
            throw engineToInstrumentException(var3);
         }
      }

      public TruffleFile getTruffleFile(URI uri) {
         try {
            return InstrumentAccessor.engineAccess().getTruffleFile(uri);
         } catch (Throwable var3) {
            throw engineToInstrumentException(var3);
         }
      }

      public TruffleContext getEnteredContext() {
         return InstrumentAccessor.ENGINE.getCurrentCreatorTruffleContext();
      }

      private static boolean checkNullOrInterop(Object obj) {
         if (obj == null) {
            return true;
         } else {
            InstrumentAccessor.interopAccess().checkInteropType(obj);
            return true;
         }
      }

      public boolean isEngineRoot(RootNode root) {
         try {
            return InstrumentAccessor.engineAccess().isEvalRoot(root);
         } catch (Throwable var3) {
            throw engineToInstrumentException(var3);
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
         } catch (Throwable var3) {
            throw engineToInstrumentException(var3);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public Object getLanguageView(LanguageInfo language, Object value) {
         try {
            Objects.requireNonNull(language);
            return InstrumentAccessor.engineAccess().getLanguageView(language, value);
         } catch (Throwable var4) {
            throw engineToInstrumentException(var4);
         }
      }

      public Object getPolyglotBindings() {
         try {
            return InstrumentAccessor.engineAccess().getPolyglotBindingsObject();
         } catch (Throwable var2) {
            throw engineToInstrumentException(var2);
         }
      }

      public Object getScope(LanguageInfo language) {
         assert language != null;

         try {
            TruffleLanguage.Env env = InstrumentAccessor.engineAccess().getEnvForInstrument(language);
            return InstrumentAccessor.langAccess().getScope(env);
         } catch (Throwable var3) {
            throw engineToInstrumentException(var3);
         }
      }

      public TruffleLogger getLogger(String loggerName) {
         try {
            return InstrumentAccessor.engineAccess().getLogger(this.polyglotInstrument, loggerName);
         } catch (Throwable var3) {
            throw engineToInstrumentException(var3);
         }
      }

      public TruffleLogger getLogger(Class<?> forClass) {
         return this.getLogger(forClass.getName());
      }

      public long calculateContextHeapSize(TruffleContext truffleContext, long stopAtBytes, AtomicBoolean cancelled) {
         return InstrumentAccessor.engineAccess()
            .calculateContextHeapSize(InstrumentAccessor.langAccess().getPolyglotContext(truffleContext), stopAtBytes, cancelled);
      }

      public Future<Void> submitThreadLocal(TruffleContext context, Thread[] threads, ThreadLocalAction action) {
         Objects.requireNonNull(context);

         try {
            return InstrumentAccessor.ENGINE
               .submitThreadLocal(InstrumentAccessor.LANGUAGE.getPolyglotContext(context), this.polyglotInstrument, threads, action, true);
         } catch (Throwable var5) {
            throw engineToInstrumentException(var5);
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
         } catch (Throwable var4) {
            throw engineToInstrumentException(var4);
         }
      }

      private static class GuardedExecutableNode extends ExecutableNode {
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
            assert this.frameDescriptor == null || this.frameDescriptor == frame.getFrameDescriptor();

            this.assureAdopted();
            Object ret = this.fragment.execute(frame);

            assert TruffleInstrument.Env.checkNullOrInterop(ret);

            return ret;
         }

         private void assureAdopted() {
            if (this.getParent() == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               throw new IllegalStateException("Needs to be inserted into the AST before execution.");
            }
         }
      }

      private static class MessageTransportProxy implements MessageTransport {
         private final MessageTransport transport;

         MessageTransportProxy(MessageTransport transport) {
            this.transport = transport;
         }

         @Override
         public MessageEndpoint open(URI uri, MessageEndpoint peerEndpoint) throws IOException, MessageTransport.VetoException {
            Objects.requireNonNull(peerEndpoint, "The peer endpoint must be non null.");
            MessageEndpoint openedEndpoint = this.transport.open(uri, new TruffleInstrument.Env.MessageTransportProxy.MessageEndpointProxy(peerEndpoint));
            return openedEndpoint == null ? null : new TruffleInstrument.Env.MessageTransportProxy.MessageEndpointProxy(openedEndpoint);
         }

         private static class MessageEndpointProxy implements MessageEndpoint {
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
   }

   public interface Provider {
      String getInstrumentClassName();

      TruffleInstrument create();

      Collection<String> getServicesClassNames();
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.TYPE)
   public @interface Registration {
      String id() default "";

      String name() default "";

      String version() default "inherit";

      boolean internal() default false;

      Class<?>[] services() default {};

      String website() default "";
   }
}
