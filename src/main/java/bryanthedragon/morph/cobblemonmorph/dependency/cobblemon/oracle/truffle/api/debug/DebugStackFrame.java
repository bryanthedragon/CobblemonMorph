
package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.debug.DebugException;
import com.oracle.truffle.api.debug.DebugScope;
import com.oracle.truffle.api.debug.DebugValue;
import com.oracle.truffle.api.debug.Debugger;
import com.oracle.truffle.api.debug.DebuggerSession;
import com.oracle.truffle.api.debug.SuspendAnchor;
import com.oracle.truffle.api.debug.SuspendedContext;
import com.oracle.truffle.api.debug.SuspendedEvent;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameInstance;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.NodeLibrary;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;
import java.util.Objects;

public final class DebugStackFrame {
    final SuspendedEvent event;
    private final FrameInstance currentFrame;
    private final StackTraceElement hostTraceElement;
    private final int depth;
    private final String name;
    private final DebugException nameEx;

    DebugStackFrame(SuspendedEvent session, FrameInstance instance, int depth) {
        this.event = session;
        this.currentFrame = instance;
        this.hostTraceElement = null;
        this.depth = depth;
        String frameName = null;
        DebugException frameNameEx = null;
        try {
            frameName = this.initName();
        }
        catch (DebugException ex) {
            frameNameEx = ex;
        }
        this.name = frameName;
        this.nameEx = frameNameEx;
    }

    DebugStackFrame(SuspendedEvent session, StackTraceElement hostElement, int depth) {
        this.event = session;
        this.currentFrame = null;
        this.hostTraceElement = hostElement;
        this.depth = depth;
        this.name = hostElement.getClassName() + "." + hostElement.getMethodName();
        this.nameEx = null;
    }

    private String initName() throws DebugException {
        Node node;
        this.verifyValidState(false);
        if (this.currentFrame == null) {
            node = this.getContext().getInstrumentedNode();
        } else {
            node = this.currentFrame.getCallNode();
            node = InstrumentableNode.findInstrumentableParent(node);
        }
        try {
            RootNode root;
            if (node != null) {
                Frame frame = this.findTruffleFrame(FrameInstance.FrameAccess.READ_ONLY);
                NodeLibrary nodeLibrary = NodeLibrary.getUncached();
                if (nodeLibrary.hasRootInstance(node, frame)) {
                    Object instance = nodeLibrary.getRootInstance(node, frame);
                    InteropLibrary interop = InteropLibrary.getUncached();
                    if (interop.hasExecutableName(instance)) {
                        return interop.asString(interop.getExecutableName(instance));
                    }
                }
            }
            if ((root = this.findCurrentRoot()) == null) {
                return null;
            }
            return root.getName();
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            RootNode root = this.findCurrentRoot();
            LanguageInfo languageInfo = root != null ? root.getLanguageInfo() : null;
            throw DebugException.create(this.event.getSession(), ex, languageInfo);
        }
    }

    public boolean isInternal() {
        this.verifyValidState(true);
        if (this.isHost()) {
            return false;
        }
        RootNode root = this.findCurrentRoot();
        if (root == null) {
            return true;
        }
        return root.isInternal();
    }

    public boolean isHost() {
        return this.hostTraceElement != null;
    }

    public StackTraceElement getHostTraceElement() {
        return this.hostTraceElement;
    }

    public String getName() throws DebugException {
        this.verifyValidState(true);
        if (this.nameEx != null) {
            throw this.nameEx;
        }
        return this.name;
    }

    public SourceSection getSourceSection() {
        this.verifyValidState(true);
        if (this.isHost()) {
            return null;
        }
        if (this.currentFrame == null) {
            SuspendedContext context = this.getContext();
            return this.event.getSession().resolveSection(context.getInstrumentedSourceSection());
        }
        Node callNode = this.currentFrame.getCallNode();
        if (callNode != null) {
            return this.event.getSession().resolveSection(callNode);
        }
        return null;
    }

    public LanguageInfo getLanguage() {
        this.verifyValidState(true);
        if (this.isHost()) {
            return null;
        }
        RootNode root = this.findCurrentRoot();
        if (root == null) {
            return null;
        }
        return root.getLanguageInfo();
    }

    public DebugScope getScope() throws DebugException {
        Node node;
        this.verifyValidState(false);
        if (this.isHost()) {
            return null;
        }
        SuspendedContext context = this.getContext();
        RootNode root = this.findCurrentRoot();
        if (root == null) {
            return null;
        }
        if (this.currentFrame == null) {
            node = context.getInstrumentedNode();
        } else {
            node = this.currentFrame.getCallNode();
            if (node == null) {
                return null;
            }
            node = InstrumentableNode.findInstrumentableParent(node);
        }
        DebuggerSession session = this.event.getSession();
        Frame frame = this.findTruffleFrame(FrameInstance.FrameAccess.READ_WRITE);
        try {
            if (!NodeLibrary.getUncached().hasScope(node, frame)) {
                return null;
            }
            Object scope = NodeLibrary.getUncached().getScope(node, frame, this.isEnter());
            return new DebugScope(scope, session, this.event, node, frame, root);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(session, ex, root.getLanguageInfo());
        }
    }

    private boolean isEnter() {
        return this.depth == 0 && SuspendAnchor.BEFORE.equals((Object)this.event.getSuspendAnchor());
    }

    public Node getRawNode(Class<? extends TruffleLanguage<?>> languageClass) {
        Objects.requireNonNull(languageClass);
        RootNode rootNode = this.findCurrentRoot();
        if (rootNode == null) {
            return null;
        }
        TruffleLanguage<?> language = Debugger.ACCESSOR.nodeSupport().getLanguage(rootNode);
        return language != null && language.getClass() == languageClass ? this.getCurrentNode() : null;
    }

    public Frame getRawFrame(Class<? extends TruffleLanguage<?>> languageClass, FrameInstance.FrameAccess access) {
        Objects.requireNonNull(languageClass);
        RootNode rootNode = this.findCurrentRoot();
        if (rootNode == null) {
            return null;
        }
        TruffleLanguage<?> language = Debugger.ACCESSOR.nodeSupport().getLanguage(rootNode);
        return language != null && language.getClass() == languageClass ? this.findTruffleFrame(access) : null;
    }

    DebugValue wrapHeapValue(Object result) {
        assert (!this.isHost()) : "Can not wrap values in host frames.";
        RootNode root = this.findCurrentRoot();
        LanguageInfo language = root != null ? root.getLanguageInfo() : null;
        return new DebugValue.HeapValue(this.event.getSession(), language, null, result);
    }

    public DebugValue eval(String code) throws DebugException {
        this.verifyValidState(false);
        if (this.isHost()) {
            throw new IllegalStateException("Can not evaluate code in host frames.");
        }
        Object result = DebuggerSession.evalInContext(this.event, code, this.currentFrame);
        return this.wrapHeapValue(result);
    }

    public boolean equals(Object obj) {
        if (obj instanceof DebugStackFrame) {
            DebugStackFrame other = (DebugStackFrame)obj;
            return this.event == other.event && this.hostTraceElement == other.hostTraceElement && (this.currentFrame == other.currentFrame || this.currentFrame != null && other.currentFrame != null && this.currentFrame.getFrame(FrameInstance.FrameAccess.READ_ONLY) == other.currentFrame.getFrame(FrameInstance.FrameAccess.READ_ONLY));
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.event, this.currentFrame);
    }

    Frame findTruffleFrame(FrameInstance.FrameAccess access) {
        assert (!this.isHost()) : "No Truffle frame in host stack frame";
        if (this.currentFrame == null) {
            return this.event.getMaterializedFrame();
        }
        return this.currentFrame.getFrame(access);
    }

    int getDepth() {
        return this.depth;
    }

    private SuspendedContext getContext() {
        SuspendedContext context = this.event.getContext();
        if (context == null) {
            this.verifyValidState(true);
            assert (false) : "should not be reachable";
        }
        return context;
    }

    RootNode findCurrentRoot() {
        if (this.isHost()) {
            return null;
        }
        SuspendedContext context = this.getContext();
        if (this.currentFrame == null) {
            return context.getInstrumentedNode().getRootNode();
        }
        return ((RootCallTarget)this.currentFrame.getCallTarget()).getRootNode();
    }

    RootCallTarget getCallTarget() {
        if (this.isHost()) {
            return null;
        }
        SuspendedContext context = this.getContext();
        if (this.currentFrame == null) {
            return context.getInstrumentedNode().getRootNode().getCallTarget();
        }
        return (RootCallTarget)this.currentFrame.getCallTarget();
    }

    Node getCurrentNode() {
        if (this.isHost()) {
            return null;
        }
        if (this.currentFrame == null) {
            return this.getContext().getInstrumentedNode();
        }
        Node callNode = this.currentFrame.getCallNode();
        if (callNode != null) {
            return callNode;
        }
        CallTarget target = this.currentFrame.getCallTarget();
        if (target instanceof RootCallTarget) {
            return ((RootCallTarget)target).getRootNode();
        }
        return null;
    }

    void verifyValidState(boolean allowDifferentThread) {
        this.event.verifyValidState(allowDifferentThread);
    }
}

