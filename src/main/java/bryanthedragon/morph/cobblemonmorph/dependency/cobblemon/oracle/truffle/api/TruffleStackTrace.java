
package com.oracle.truffle.api;

import com.oracle.truffle.api.ArrayUtils;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.LanguageAccessor;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleStackTraceElement;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameInstance;
import com.oracle.truffle.api.frame.FrameInstanceVisitor;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.nodes.ControlFlowException;
import com.oracle.truffle.api.nodes.Node;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.graalvm.polyglot.PolyglotException;
import sun.misc.Unsafe;

public final class TruffleStackTrace
extends Exception {
    private static final long causeFieldIndex;
    private static final Unsafe UNSAFE;
    private static final TruffleStackTrace EMPTY;
    private List<TruffleStackTraceElement> frames;
    private final int lazyFrames;
    private Exception materializedHostException;

    private static Throwable getCause(Throwable t) {
        try {
            Throwable result = (Throwable)UNSAFE.getObject((Object)t, causeFieldIndex);
            return result == t ? null : result;
        }
        catch (IllegalArgumentException e) {
            CompilerDirectives.transferToInterpreter();
            throw new RuntimeException(e);
        }
    }

    private static void initCause(Throwable t, Throwable value2) {
        try {
            UNSAFE.putObject((Object)t, causeFieldIndex, (Object)value2);
        }
        catch (IllegalArgumentException e) {
            CompilerDirectives.transferToInterpreter();
            throw new RuntimeException(e);
        }
    }

    private TruffleStackTrace(List<TruffleStackTraceElement> frames, int lazyFrames) {
        this.frames = frames;
        this.lazyFrames = lazyFrames;
    }

    private void materializeHostException() {
        if (this.materializedHostException == null) {
            this.materializedHostException = new Exception();
        }
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    StackTraceElement[] getInternalStackTrace() {
        Exception hostException = this.materializedHostException;
        if (hostException == null) {
            hostException = this;
        }
        StackTraceElement[] hostFrames = hostException.getStackTrace();
        if (this.lazyFrames == 0) {
            return hostFrames;
        }
        StackTraceElement[] extended = new StackTraceElement[hostFrames.length + this.lazyFrames];
        System.arraycopy(hostFrames, 0, extended, this.lazyFrames, hostFrames.length);
        return extended;
    }

    @Override
    public String toString() {
        return "Attached Guest Language Frames (" + this.frames.size() + ")";
    }

    @CompilerDirectives.TruffleBoundary
    public static List<TruffleStackTraceElement> getStackTrace(Throwable throwable) {
        TruffleStackTrace stack = TruffleStackTrace.fillIn(throwable);
        if (stack != null) {
            return stack.frames;
        }
        return null;
    }

    @CompilerDirectives.TruffleBoundary
    public static List<TruffleStackTraceElement> getAsynchronousStackTrace(CallTarget target, Frame frame) {
        Objects.requireNonNull(target, "CallTarget must not be null");
        Objects.requireNonNull(frame, "Frame must not be null");
        assert (LanguageAccessor.ENGINE.hasCurrentContext());
        return LanguageAccessor.ACCESSOR.nodeSupport().findAsynchronousFrames(target, frame);
    }

    static void materializeHostFrames(Throwable t) {
        TruffleStackTrace stack = TruffleStackTrace.fillIn(t);
        if (stack != null) {
            stack.materializeHostException();
        }
    }

    private static LazyStackTrace findImpl(Throwable t) {
        assert (!(t instanceof ControlFlowException));
        Throwable cause = TruffleStackTrace.getCause(t);
        while (cause != null) {
            if (cause instanceof LazyStackTrace) {
                return (LazyStackTrace)cause;
            }
            cause = TruffleStackTrace.getCause(cause);
        }
        return null;
    }

    private static Throwable findInsertCause(Throwable t) {
        Throwable parentCause;
        Throwable lastException = t;
        while (lastException != null && (parentCause = TruffleStackTrace.getCause(lastException)) != null) {
            lastException = parentCause;
        }
        return lastException;
    }

    private static void insert(Throwable t, LazyStackTrace trace) {
        if (TruffleStackTrace.getCause(t) != null) {
            CompilerDirectives.transferToInterpreter();
        } else {
            TruffleStackTrace.initCause(t, trace);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleStackTrace fillIn(Throwable throwable) {
        int stackFrameLimit;
        Node topCallSite;
        if (throwable instanceof ControlFlowException) {
            return EMPTY;
        }
        LazyStackTrace lazy = TruffleStackTrace.getOrCreateLazyStackTrace(throwable);
        if (lazy.stackTrace != null) {
            return lazy.stackTrace;
        }
        if (LanguageAccessor.EXCEPTIONS.isException(throwable)) {
            topCallSite = LanguageAccessor.EXCEPTIONS.getLocation(throwable);
            stackFrameLimit = LanguageAccessor.EXCEPTIONS.getStackTraceElementLimit(throwable);
        } else {
            topCallSite = null;
            stackFrameLimit = -1;
        }
        ArrayList<TracebackElement> elements2 = new ArrayList<TracebackElement>();
        TracebackElement currentElement = lazy.current;
        while (currentElement != null) {
            elements2.add(currentElement);
            currentElement = currentElement.last;
        }
        Collections.reverse(elements2);
        ArrayList<TruffleStackTraceElement> frames = new ArrayList<TruffleStackTraceElement>();
        for (TracebackElement element : elements2) {
            if (element.root != null) {
                frames.add(new TruffleStackTraceElement(topCallSite, element.root, element.frame));
                topCallSite = null;
            }
            if (element.callNode == null) continue;
            topCallSite = element.callNode;
        }
        int lazyFrames = frames.size();
        TruffleStackTrace.addStackFrames(stackFrameLimit, lazyFrames, topCallSite, frames);
        lazy.stackTrace = new TruffleStackTrace(frames, lazyFrames);
        if (throwable.getStackTrace().length == 0) {
            lazy.stackTrace.materializeHostException();
        }
        return lazy.stackTrace;
    }

    static void addStackFrameInfo(Node callNode, RootCallTarget root, Throwable t, Frame currentFrame) {
        if (t instanceof ControlFlowException) {
            return;
        }
        if (t instanceof PolyglotException) {
            return;
        }
        boolean isTProfiled = CompilerDirectives.isPartialEvaluationConstant(t.getClass());
        if (currentFrame != null && root.getRootNode().isCaptureFramesForTrace()) {
            TruffleStackTrace.callInnerAddStackFrameInfo(isTProfiled, callNode, root, t, currentFrame.materialize());
        } else {
            TruffleStackTrace.callInnerAddStackFrameInfo(isTProfiled, callNode, root, t, null);
        }
    }

    private static void callInnerAddStackFrameInfo(boolean isTProfiled, Node callNode, RootCallTarget root, Throwable t, MaterializedFrame currentFrame) {
        if (isTProfiled) {
            TruffleStackTrace.innerAddStackFrameInfo(callNode, root, t, currentFrame);
        } else {
            TruffleStackTrace.innerAddStackFrameInfoBoundary(callNode, root, t, currentFrame);
        }
    }

    @CompilerDirectives.TruffleBoundary
    private static void innerAddStackFrameInfoBoundary(Node callNode, RootCallTarget root, Throwable t, MaterializedFrame currentFrame) {
        TruffleStackTrace.innerAddStackFrameInfo(callNode, root, t, currentFrame);
    }

    private static void innerAddStackFrameInfo(Node callNode, RootCallTarget root, Throwable t, MaterializedFrame currentFrame) {
        if (!LanguageAccessor.EXCEPTIONS.isException(t)) {
            TruffleStackTrace.fillIn(t);
            return;
        }
        int stackTraceElementLimit = LanguageAccessor.EXCEPTIONS.getStackTraceElementLimit(t);
        LazyStackTrace lazy = (LazyStackTrace)LanguageAccessor.EXCEPTIONS.getLazyStackTrace(t);
        if (lazy == null) {
            lazy = new LazyStackTrace();
            LanguageAccessor.EXCEPTIONS.setLazyStackTrace(t, lazy);
        }
        TruffleStackTrace.appendLazyStackTrace(callNode, root, currentFrame, lazy, stackTraceElementLimit);
    }

    private static LazyStackTrace getOrCreateLazyStackTrace(Throwable throwable) {
        if (LanguageAccessor.EXCEPTIONS.isException(throwable)) {
            LazyStackTrace lazy = (LazyStackTrace)LanguageAccessor.EXCEPTIONS.getLazyStackTrace(throwable);
            if (lazy == null) {
                lazy = new LazyStackTrace();
                LanguageAccessor.EXCEPTIONS.setLazyStackTrace(throwable, lazy);
            }
            return lazy;
        }
        LazyStackTrace lazy = TruffleStackTrace.findImpl(throwable);
        if (lazy == null) {
            Throwable insertCause = TruffleStackTrace.findInsertCause(throwable);
            if (insertCause == null) {
                return null;
            }
            lazy = new LazyStackTrace();
            TruffleStackTrace.insert(insertCause, lazy);
        }
        return lazy;
    }

    private static void appendLazyStackTrace(Node callNode, RootCallTarget root, MaterializedFrame currentFrame, LazyStackTrace lazy, int stackTraceElementLimit) {
        if (lazy.stackTrace == null) {
            if (stackTraceElementLimit >= 0 && lazy.frameCount >= stackTraceElementLimit) {
                return;
            }
            lazy.current = new TracebackElement(lazy.current, callNode, root, currentFrame);
            if (root != null && LanguageAccessor.ACCESSOR.nodeSupport().countsTowardsStackTraceLimit(root.getRootNode())) {
                ++lazy.frameCount;
            }
        }
    }

    private static void addStackFrames(final int stackFrameLimit, final int lazyFrames, final Node topCallSite, final List<TruffleStackTraceElement> frames) {
        if (stackFrameLimit >= 0 && lazyFrames >= stackFrameLimit) {
            return;
        }
        Truffle.getRuntime().iterateFrames(new FrameInstanceVisitor<FrameInstance>(){
            boolean first = true;
            int stackFrameIndex = lazyFrames;

            @Override
            public FrameInstance visitFrame(FrameInstance frameInstance) {
                if (stackFrameLimit >= 0 && this.stackFrameIndex >= stackFrameLimit) {
                    return frameInstance;
                }
                Node location = frameInstance.getCallNode();
                RootCallTarget target = (RootCallTarget)frameInstance.getCallTarget();
                if (this.first) {
                    location = topCallSite;
                    this.first = false;
                }
                boolean captureFrames = target != null && target.getRootNode().isCaptureFramesForTrace();
                Frame frame = captureFrames ? frameInstance.getFrame(FrameInstance.FrameAccess.READ_ONLY) : null;
                frames.add(new TruffleStackTraceElement(location, target, frame));
                this.first = false;
                if (target != null && LanguageAccessor.ACCESSOR.nodeSupport().countsTowardsStackTraceLimit(target.getRootNode())) {
                    ++this.stackFrameIndex;
                }
                return null;
            }
        });
    }

    static {
        Unsafe unsafe;
        try {
            unsafe = Unsafe.getUnsafe();
        }
        catch (SecurityException e) {
            try {
                Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
                theUnsafeInstance.setAccessible(true);
                unsafe = (Unsafe)theUnsafeInstance.get(Unsafe.class);
            }
            catch (Exception e2) {
                throw new RuntimeException("exception while trying to get Unsafe.theUnsafe via reflection:", e2);
            }
        }
        UNSAFE = unsafe;
        try {
            Field causeField = Throwable.class.getDeclaredField("cause");
            causeFieldIndex = ArrayUtils.getObjectFieldOffset(causeField);
        }
        catch (NoSuchFieldException | SecurityException e) {
            throw new RuntimeException(e);
        }
        EMPTY = new TruffleStackTrace(Collections.emptyList(), 0);
    }

    static final class LazyStackTrace
    extends Throwable {
        private TracebackElement current;
        private TruffleStackTrace stackTrace;
        public int frameCount;

        LazyStackTrace() {
        }

        @Override
        public Throwable fillInStackTrace() {
            return null;
        }

        public TruffleStackTrace getInternalStackTrace() {
            return this.stackTrace;
        }

        @Override
        public Throwable initCause(Throwable cause) {
            throw new IllegalAccessError("cannot change cause of AbstractTruffleException stacktrace");
        }

        @Override
        public String toString() {
            return "Attached Guest Language Frames (" + (this.frameCount + (this.stackTrace != null ? this.stackTrace.frames.size() : 0)) + ")";
        }
    }

    private static final class TracebackElement {
        private final TracebackElement last;
        private final Node callNode;
        private final RootCallTarget root;
        private final MaterializedFrame frame;

        TracebackElement(TracebackElement last, Node callNode, RootCallTarget root, MaterializedFrame frame) {
            this.last = last;
            this.callNode = callNode;
            this.root = root;
            this.frame = frame;
        }
    }
}

