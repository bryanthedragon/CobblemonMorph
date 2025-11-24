
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleStackTrace;
import com.oracle.truffle.api.TruffleStackTraceElement;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.polyglot.EngineAccessor;
import com.oracle.truffle.polyglot.PolyglotContextImpl;
import com.oracle.truffle.polyglot.PolyglotEngineException;
import com.oracle.truffle.polyglot.PolyglotEngineImpl;
import com.oracle.truffle.polyglot.PolyglotEngineOptions;
import com.oracle.truffle.polyglot.PolyglotExceptionFrame;
import com.oracle.truffle.polyglot.PolyglotImpl;
import com.oracle.truffle.polyglot.PolyglotLanguage;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

final class PolyglotExceptionImpl {
    private static final String CAUSE_CAPTION = "Caused by host exception: ";
    private static final boolean TRACE_STACK_TRACE_WALKING = false;
    private Object api;
    final PolyglotImpl polyglot;
    final PolyglotEngineImpl engine;
    final PolyglotContextImpl context;
    final Throwable exception;
    final boolean showInternalStackFrames;
    private final List<TruffleStackTraceElement> guestFrames;
    private StackTraceElement[] javaStackTrace;
    private List<PolyglotException.StackFrame> materializedFrames;
    private final org.graalvm.polyglot.SourceSection sourceLocation;
    private final boolean internal;
    private final boolean cancelled;
    private final boolean exit;
    private final boolean incompleteSource;
    private final boolean syntaxError;
    private final boolean resourceExhausted;
    private final boolean interrupted;
    private final int exitStatus;
    private final Value guestObject;
    private final String message;

    PolyglotExceptionImpl(PolyglotEngineImpl engine, PolyglotContextImpl.State polyglotContextState, boolean polyglotContextResourceExhausted, int exitCode, Throwable original) {
        this(engine.impl, engine, polyglotContextState, polyglotContextResourceExhausted, exitCode, null, original, false, false);
    }

    PolyglotExceptionImpl(PolyglotImpl polyglot, Throwable original) {
        this(polyglot, null, null, false, 0, null, original, true, false);
    }

    PolyglotExceptionImpl(PolyglotImpl polyglot, PolyglotEngineImpl engine, PolyglotContextImpl.State polyglotContextState, boolean polyglotContextResourceExhausted, int exitCode, PolyglotLanguageContext languageContext, Throwable original, boolean allowInterop, boolean entered) {
        InteropLibrary interop;
        this.polyglot = polyglot;
        this.engine = engine;
        this.context = languageContext != null ? languageContext.context : null;
        this.exception = original;
        this.guestFrames = TruffleStackTrace.getStackTrace(original);
        this.showInternalStackFrames = engine == null ? false : engine.engineOptionValues.get(PolyglotEngineOptions.ShowInternalStackFrames);
        Error resourceLimitError = PolyglotExceptionImpl.getResourceLimitError(engine, this.exception);
        String exceptionMessage = null;
        if (allowInterop && (interop = InteropLibrary.getUncached()).isException(this.exception)) {
            try {
                boolean cancelInducedTruffleException;
                ExceptionType exceptionType = interop.getExceptionType(this.exception);
                this.internal = false;
                this.cancelled = cancelInducedTruffleException = polyglotContextState != null && (polyglotContextState.isCancelling() || polyglotContextState == PolyglotContextImpl.State.CLOSED_CANCELLED);
                this.resourceExhausted = resourceLimitError != null || cancelInducedTruffleException && polyglotContextResourceExhausted;
                this.syntaxError = exceptionType == ExceptionType.PARSE_ERROR;
                this.exit = exceptionType == ExceptionType.EXIT;
                this.exitStatus = this.exit ? interop.getExceptionExitStatus(this.exception) : 0;
                this.incompleteSource = this.syntaxError ? interop.isExceptionIncompleteSource(this.exception) : false;
                boolean bl = this.interrupted = exceptionType == ExceptionType.INTERRUPT && !this.cancelled;
                if (interop.hasExceptionMessage(this.exception)) {
                    exceptionMessage = interop.asString(interop.getExceptionMessage(this.exception));
                }
                this.sourceLocation = interop.hasSourceLocation(this.exception) ? this.newSourceSection(interop.getSourceLocation(this.exception)) : null;
                if (entered && languageContext != null && languageContext.isCreated() && !PolyglotExceptionImpl.isHostException(engine, this.exception)) {
                    this.guestObject = languageContext.asValue(this.exception);
                }
                this.guestObject = null;
            }
            catch (UnsupportedMessageException ume) {
                throw CompilerDirectives.shouldNotReachHere(ume);
            }
        } else {
            boolean exitInducedTruffleOrInterruptException;
            boolean interruptException = this.exception instanceof PolyglotEngineImpl.InterruptExecution || this.exception != null && this.exception.getCause() instanceof InterruptedException || PolyglotExceptionImpl.isHostException(engine, this.exception) && this.asHostException() instanceof InterruptedException;
            boolean truffleException = this.exception instanceof AbstractTruffleException;
            boolean cancelInducedTruffleOrInterruptException = !(polyglotContextState == null || !polyglotContextState.isCancelling() && polyglotContextState != PolyglotContextImpl.State.CLOSED_CANCELLED || !interruptException && !truffleException);
            this.cancelled = cancelInducedTruffleOrInterruptException || this.exception instanceof PolyglotEngineImpl.CancelExecution;
            this.resourceExhausted = resourceLimitError != null || cancelInducedTruffleOrInterruptException && polyglotContextResourceExhausted;
            this.interrupted = interruptException && !this.cancelled;
            this.syntaxError = false;
            this.incompleteSource = false;
            SourceSection location = null;
            boolean bl = exitInducedTruffleOrInterruptException = !(polyglotContextState == null || !polyglotContextState.isExiting() && polyglotContextState != PolyglotContextImpl.State.CLOSED_EXITED || !interruptException && !truffleException);
            if (exitInducedTruffleOrInterruptException || this.exception instanceof PolyglotContextImpl.ExitException) {
                this.exit = true;
                this.exitStatus = this.exception instanceof PolyglotContextImpl.ExitException ? ((PolyglotContextImpl.ExitException)this.exception).getExitCode() : exitCode;
                this.guestObject = null;
                location = this.exception instanceof PolyglotContextImpl.ExitException ? ((PolyglotContextImpl.ExitException)this.exception).getSourceLocation() : null;
            } else {
                this.exit = false;
                this.exitStatus = 0;
                this.guestObject = null;
            }
            boolean bl2 = this.internal = !this.interrupted && !this.cancelled && !this.resourceExhausted && !this.exit && !truffleException;
            if (this.exception instanceof PolyglotEngineImpl.CancelExecution) {
                location = ((PolyglotEngineImpl.CancelExecution)this.exception).getSourceLocation();
            }
            org.graalvm.polyglot.SourceSection sourceSection = this.sourceLocation = location != null ? this.newSourceSection(location) : null;
        }
        if (exceptionMessage == null) {
            String string = this.isHostException() ? this.asHostException().getMessage() : (exceptionMessage = this.internal ? this.exception.toString() : this.exception.getMessage());
        }
        if (exceptionMessage != null) {
            this.message = exceptionMessage;
        } else if (resourceLimitError != null) {
            Object resourceExhaustedMessage = "Resource exhausted";
            if (resourceLimitError instanceof StackOverflowError) {
                resourceExhaustedMessage = (String)resourceExhaustedMessage + ": Stack overflow";
            }
            if (resourceLimitError instanceof OutOfMemoryError) {
                resourceExhaustedMessage = (String)resourceExhaustedMessage + ": Out of memory";
            }
            this.message = resourceExhaustedMessage;
        } else {
            this.message = null;
        }
        EngineAccessor.LANGUAGE.materializeHostFrames(original);
    }

    private static Error getResourceLimitError(PolyglotEngineImpl engine, Throwable e) {
        if (e instanceof PolyglotEngineImpl.CancelExecution) {
            return ((PolyglotEngineImpl.CancelExecution)e).isResourceLimit() ? (Error)e : null;
        }
        if (PolyglotExceptionImpl.isHostException(engine, e)) {
            Error toCheck = engine.host.toHostResourceError(e);
            assert (toCheck == null || toCheck instanceof StackOverflowError || toCheck instanceof OutOfMemoryError);
            return toCheck;
        }
        if (e instanceof StackOverflowError || e instanceof OutOfMemoryError) {
            return (Error)e;
        }
        return null;
    }

    private org.graalvm.polyglot.SourceSection newSourceSection(SourceSection section) {
        Source truffleSource = section.getSource();
        org.graalvm.polyglot.Source source = this.polyglot.getAPIAccess().newSource(this.polyglot.getSourceDispatch(), truffleSource);
        return this.polyglot.getAPIAccess().newSourceSection(source, this.polyglot.getSourceSectionDispatch(), section);
    }

    public boolean equals(Object obj) {
        if (obj instanceof PolyglotExceptionImpl) {
            return this.exception == ((PolyglotExceptionImpl)obj).exception;
        }
        return false;
    }

    public int hashCode() {
        return this.exception.hashCode();
    }

    public org.graalvm.polyglot.SourceSection getSourceLocation() {
        return this.sourceLocation;
    }

    public void onCreate(PolyglotException instance) {
        this.api = instance;
    }

    public boolean isResourceExhausted() {
        return this.resourceExhausted;
    }

    public boolean isInterrupted() {
        return this.interrupted;
    }

    public boolean isHostException() {
        return PolyglotExceptionImpl.isHostException(this.engine, this.exception);
    }

    public Throwable asHostException() {
        if (!this.isHostException()) {
            throw PolyglotEngineException.unsupported(String.format("Unsupported operation %s.%s. You can ensure that the operation is supported using %s.%s.", PolyglotException.class.getSimpleName(), "asHostException()", PolyglotException.class.getSimpleName(), "isHostException()"));
        }
        return this.engine.host.unboxHostException(this.exception);
    }

    public void printStackTrace(PrintWriter s) {
        this.printStackTrace(new WrappedPrintWriter(s));
    }

    public void printStackTrace(PrintStream s) {
        this.printStackTrace(new WrappedPrintStream(s));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void printStackTrace(PrintStreamOrWriter s) {
        Object object = s.lock();
        synchronized (object) {
            if (this.isInternalError() && (this.guestFrames == null || this.guestFrames.isEmpty())) {
                s.print(this.api.getClass().getName() + ": ");
                s.printStackTrace(this.exception);
                s.println("Internal GraalVM error, please report at https://github.com/oracle/graal/issues/.");
                return;
            }
            if (this.isInternalError() || this.getMessage() == null || this.getMessage().isEmpty()) {
                s.println(this.api);
            } else {
                s.println(this.getMessage());
            }
            this.materialize();
            int languageIdLength = 0;
            for (PolyglotException.StackFrame traceElement : this.getPolyglotStackTrace()) {
                if (traceElement.isHostFrame()) continue;
                languageIdLength = Math.max(languageIdLength, this.polyglot.getAPIAccess().getDispatch(traceElement).getLanguage().getId().length());
            }
            for (PolyglotException.StackFrame traceElement : this.getPolyglotStackTrace()) {
                s.println("\tat " + this.polyglot.getAPIAccess().getDispatch(traceElement).toStringImpl(languageIdLength));
            }
            if (this.isHostException()) {
                s.println(CAUSE_CAPTION + this.asHostException());
            }
            if (this.isInternalError()) {
                s.println("Original Internal Error: ");
                s.printStackTrace(this.exception);
            }
        }
    }

    public String getMessage() {
        return this.message;
    }

    public StackTraceElement[] getJavaStackTrace() {
        if (this.javaStackTrace == null) {
            this.materialize();
            this.javaStackTrace = new StackTraceElement[this.materializedFrames.size()];
            for (int i = 0; i < this.javaStackTrace.length; ++i) {
                this.javaStackTrace[i] = this.materializedFrames.get(i).toHostFrame();
            }
        }
        return this.javaStackTrace;
    }

    private void materialize() {
        if (this.materializedFrames == null) {
            ArrayList<PolyglotException.StackFrame> frames = new ArrayList<PolyglotException.StackFrame>();
            for (PolyglotException.StackFrame frame : this.getPolyglotStackTrace()) {
                frames.add(frame);
            }
            this.materializedFrames = Collections.unmodifiableList(frames);
        }
    }

    public StackTraceElement[] getStackTrace() {
        return (StackTraceElement[])this.getJavaStackTrace().clone();
    }

    public boolean isInternalError() {
        return this.internal;
    }

    public Iterable<PolyglotException.StackFrame> getPolyglotStackTrace() {
        if (this.materializedFrames != null) {
            return this.materializedFrames;
        }
        return new Iterable<PolyglotException.StackFrame>(){

            @Override
            public Iterator<PolyglotException.StackFrame> iterator() {
                return PolyglotExceptionImpl.createStackFrameIterator(PolyglotExceptionImpl.this);
            }
        };
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public boolean isExit() {
        return this.exit;
    }

    public boolean isIncompleteSource() {
        return this.incompleteSource;
    }

    public int getExitStatus() {
        return this.exitStatus;
    }

    public boolean isSyntaxError() {
        return this.syntaxError;
    }

    public Value getGuestObject() {
        return this.guestObject;
    }

    static String printStackToString(PolyglotLanguageContext context, Node node) {
        StackTraceException stack = new StackTraceException(node);
        TruffleStackTrace.fillIn(stack);
        PolyglotException e = PolyglotImpl.guestToHostException(context, stack, true);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(out));
        return new String(out.toByteArray());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    Object getFileSystemContext(PolyglotLanguage language) {
        if (this.context == null) {
            return null;
        }
        PolyglotContextImpl polyglotContextImpl = this.context;
        synchronized (polyglotContextImpl) {
            PolyglotLanguageContext languageContext = this.context.getContext(language);
            if (!languageContext.isCreated()) {
                return null;
            }
            return languageContext.getInternalFileSystemContext();
        }
    }

    static Iterator<PolyglotException.StackFrame> createStackFrameIterator(final PolyglotExceptionImpl impl) {
        final AbstractPolyglotImpl.APIAccess apiAccess = impl.polyglot.getAPIAccess();
        Throwable cause = PolyglotExceptionImpl.findCause(impl.engine, impl.exception);
        StackTraceElement[] hostStack = EngineAccessor.LANGUAGE.isTruffleStackTrace(cause) ? EngineAccessor.LANGUAGE.getInternalStackTraceElements(cause) : (cause.getStackTrace() == null || cause.getStackTrace().length == 0 ? impl.exception.getStackTrace() : cause.getStackTrace());
        Iterator guestFrames = impl.guestFrames == null ? Collections.emptyIterator() : impl.guestFrames.iterator();
        boolean inHostLanguage = impl.isHostException() || impl.isInternalError();
        return new MergedHostGuestIterator<PolyglotException.StackFrame, TruffleStackTraceElement>(impl.engine, hostStack, guestFrames, inHostLanguage, new Function<StackTraceElement, PolyglotException.StackFrame>(){

            @Override
            public PolyglotException.StackFrame apply(StackTraceElement element) {
                return apiAccess.newPolyglotStackTraceElement(PolyglotExceptionFrame.createHost(impl, element), impl.api);
            }
        }, new Function<TruffleStackTraceElement, PolyglotException.StackFrame>(){
            private boolean firstGuestFrame = true;

            @Override
            public PolyglotException.StackFrame apply(TruffleStackTraceElement guestFrame) {
                boolean first = this.firstGuestFrame;
                this.firstGuestFrame = false;
                PolyglotExceptionFrame guest = PolyglotExceptionFrame.createGuest(impl, guestFrame, first);
                if (guest != null) {
                    return apiAccess.newPolyglotStackTraceElement(guest, impl.api);
                }
                return null;
            }
        });
    }

    private static Throwable findCause(PolyglotEngineImpl engine, Throwable throwable) {
        Throwable cause = throwable;
        if (PolyglotExceptionImpl.isHostException(engine, cause)) {
            return PolyglotExceptionImpl.findCause(engine, engine.host.unboxHostException(cause));
        }
        if (EngineAccessor.EXCEPTION.isException(cause)) {
            return EngineAccessor.EXCEPTION.getLazyStackTrace(cause);
        }
        while (cause.getCause() != null && cause.getStackTrace().length == 0) {
            if (PolyglotExceptionImpl.isHostException(engine, cause)) {
                cause = engine.host.unboxHostException(cause);
                continue;
            }
            cause = cause.getCause();
        }
        return cause;
    }

    private static boolean isHostException(PolyglotEngineImpl engine, Throwable cause) {
        return engine != null && engine.host != null && engine.host.isHostException(cause);
    }

    static class MergedHostGuestIterator<T, G>
    implements Iterator<T> {
        private static final String POLYGLOT_PACKAGE = Engine.class.getName().substring(0, Engine.class.getName().lastIndexOf(46) + 1);
        private static final String HOST_INTEROP_PACKAGE = "com.oracle.truffle.polyglot.";
        private static final String[] JAVA_INTEROP_HOST_TO_GUEST = new String[]{"com.oracle.truffle.polyglot.PolyglotMap", "com.oracle.truffle.polyglot.PolyglotList", "com.oracle.truffle.polyglot.PolyglotFunction", "com.oracle.truffle.polyglot.PolyglotMapAndFunction", "com.oracle.truffle.polyglot.PolyglotFunctionProxyHandler", "com.oracle.truffle.polyglot.PolyglotObjectProxyHandler"};
        private final PolyglotEngineImpl engine;
        private final Iterator<G> guestFrames;
        private final StackTraceElement[] hostStack;
        private final ListIterator<StackTraceElement> hostFrames;
        private final Function<StackTraceElement, T> hostFrameConvertor;
        private final Function<G, T> guestFrameConvertor;
        private boolean inHostLanguage;
        private T fetchedNext;

        MergedHostGuestIterator(PolyglotEngineImpl engine, StackTraceElement[] hostStack, Iterator<G> guestFrames, boolean inHostLanguage, Function<StackTraceElement, T> hostFrameConvertor, Function<G, T> guestFrameConvertor) {
            this.engine = engine;
            this.hostStack = hostStack;
            this.hostFrames = Arrays.asList(hostStack).listIterator();
            this.guestFrames = guestFrames;
            this.inHostLanguage = inHostLanguage;
            this.hostFrameConvertor = hostFrameConvertor;
            this.guestFrameConvertor = guestFrameConvertor;
        }

        @Override
        public boolean hasNext() {
            return this.fetchNext() != null;
        }

        @Override
        public T next() {
            T next = this.fetchNext();
            if (next == null) {
                throw new NoSuchElementException();
            }
            this.fetchedNext = null;
            return next;
        }

        T fetchNext() {
            if (this.fetchedNext != null) {
                return this.fetchedNext;
            }
            while (this.hostFrames.hasNext()) {
                StackTraceElement element = this.hostFrames.next();
                this.traceStackTraceElement(element);
                if (this.inHostLanguage) {
                    int guestToHost = MergedHostGuestIterator.findGuestToHostFrame(this.engine, element, this.hostStack, this.hostFrames.nextIndex());
                    if (guestToHost >= 0) {
                        assert (!MergedHostGuestIterator.isHostToGuest(element));
                        this.inHostLanguage = false;
                        for (int i = 0; i < guestToHost; ++i) {
                            element = this.hostFrames.next();
                            this.traceStackTraceElement(element);
                        }
                    }
                } else if (MergedHostGuestIterator.isHostToGuest(element)) {
                    this.inHostLanguage = true;
                    while (this.hostFrames.hasNext()) {
                        StackTraceElement next = this.hostFrames.next();
                        this.traceStackTraceElement(next);
                        if (MergedHostGuestIterator.isHostToGuest(next)) {
                            element = next;
                            continue;
                        }
                        this.hostFrames.previous();
                        break;
                    }
                }
                if (MergedHostGuestIterator.isGuestCall(element)) {
                    G guestFrame;
                    T frame;
                    this.inHostLanguage = false;
                    if (!this.guestFrames.hasNext() || (frame = this.guestFrameConvertor.apply(guestFrame = this.guestFrames.next())) == null) continue;
                    this.fetchedNext = frame;
                    return this.fetchedNext;
                }
                if (!this.inHostLanguage) continue;
                this.fetchedNext = this.hostFrameConvertor.apply(element);
                return this.fetchedNext;
            }
            while (this.guestFrames.hasNext()) {
                G guestFrame = this.guestFrames.next();
                T frame = this.guestFrameConvertor.apply(guestFrame);
                if (frame == null) continue;
                this.fetchedNext = frame;
                return this.fetchedNext;
            }
            return null;
        }

        static boolean isLazyStackTraceElement(StackTraceElement element) {
            return element == null;
        }

        static boolean isGuestCall(StackTraceElement element) {
            return MergedHostGuestIterator.isLazyStackTraceElement(element) || EngineAccessor.RUNTIME.isGuestCallStackFrame(element);
        }

        static boolean isHostToGuest(StackTraceElement element) {
            if (MergedHostGuestIterator.isLazyStackTraceElement(element)) {
                return false;
            }
            if (element.getClassName().startsWith(POLYGLOT_PACKAGE) && element.getClassName().indexOf(46, POLYGLOT_PACKAGE.length()) < 0) {
                return true;
            }
            if (element.getClassName().startsWith(HOST_INTEROP_PACKAGE)) {
                for (String hostToGuestClassName : JAVA_INTEROP_HOST_TO_GUEST) {
                    if (!element.getClassName().equals(hostToGuestClassName)) continue;
                    return true;
                }
            }
            return false;
        }

        static int findGuestToHostFrame(PolyglotEngineImpl engine, StackTraceElement firstElement, StackTraceElement[] hostStack, int nextElementIndex) {
            if (MergedHostGuestIterator.isLazyStackTraceElement(firstElement)) {
                return -1;
            }
            if (engine == null || engine.host == null) {
                return -1;
            }
            return engine.host.findNextGuestToHostStackTraceElement(firstElement, hostStack, nextElementIndex);
        }

        private void traceStackTraceElement(StackTraceElement element) {
        }
    }

    private static class WrappedPrintWriter
    extends PrintStreamOrWriter {
        private final PrintWriter printWriter;

        WrappedPrintWriter(PrintWriter printWriter) {
            this.printWriter = printWriter;
        }

        @Override
        Object lock() {
            return this.printWriter;
        }

        @Override
        void print(Object o) {
            this.printWriter.print(o);
        }

        @Override
        void println(Object o) {
            this.printWriter.println(o);
        }

        @Override
        void printStackTrace(Throwable t) {
            t.printStackTrace(this.printWriter);
        }
    }

    private static class WrappedPrintStream
    extends PrintStreamOrWriter {
        private final PrintStream printStream;

        WrappedPrintStream(PrintStream printStream) {
            this.printStream = printStream;
        }

        @Override
        Object lock() {
            return this.printStream;
        }

        @Override
        void print(Object o) {
            this.printStream.print(o);
        }

        @Override
        void println(Object o) {
            this.printStream.println(o);
        }

        @Override
        void printStackTrace(Throwable t) {
            t.printStackTrace(this.printStream);
        }
    }

    private static abstract class PrintStreamOrWriter {
        private PrintStreamOrWriter() {
        }

        abstract Object lock();

        abstract void print(Object var1);

        abstract void println(Object var1);

        abstract void printStackTrace(Throwable var1);
    }

    static class StackTraceException
    extends AbstractTruffleException {
        StackTraceException(Node location) {
            super(location);
        }
    }
}

