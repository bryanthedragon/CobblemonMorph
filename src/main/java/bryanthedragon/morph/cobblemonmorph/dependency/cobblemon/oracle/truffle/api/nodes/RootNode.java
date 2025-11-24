
package com.oracle.truffle.api.nodes;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleStackTraceElement;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExecutableNode;
import com.oracle.truffle.api.nodes.ExecutionSignature;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeAccessor;
import com.oracle.truffle.api.nodes.NodeUtil;
import com.oracle.truffle.api.source.SourceSection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.ReentrantLock;

public abstract class RootNode
extends ExecutableNode {
    private static final AtomicReferenceFieldUpdater<RootNode, ReentrantLock> LOCK_UPDATER = AtomicReferenceFieldUpdater.newUpdater(RootNode.class, ReentrantLock.class, "lock");
    @CompilerDirectives.CompilationFinal
    private volatile RootCallTarget callTarget;
    @CompilerDirectives.CompilationFinal
    private FrameDescriptor frameDescriptor;
    private volatile ReentrantLock lock;
    volatile byte instrumentationBits;

    protected RootNode(TruffleLanguage<?> language) {
        this(language, null);
    }

    protected RootNode(TruffleLanguage<?> language, FrameDescriptor frameDescriptor) {
        super(language);
        CompilerAsserts.neverPartOfCompilation();
        this.frameDescriptor = frameDescriptor == null ? new FrameDescriptor() : frameDescriptor;
    }

    @Override
    public Node copy() {
        RootNode root = (RootNode)super.copy();
        root.frameDescriptor = this.frameDescriptor;
        RootNode.resetFieldsAfterCopy(root);
        return root;
    }

    private static void resetFieldsAfterCopy(RootNode root) {
        root.callTarget = null;
        root.instrumentationBits = 0;
        root.lock = null;
    }

    public String getQualifiedName() {
        return this.getName();
    }

    public String getName() {
        return null;
    }

    public boolean isInternal() {
        if (this.getLanguageInfo() == null) {
            return true;
        }
        SourceSection sc = this.materializeSourceSection();
        if (sc != null) {
            return sc.getSource().isInternal();
        }
        return false;
    }

    protected boolean countsTowardsStackTraceLimit() {
        return !this.isInternal();
    }

    @CompilerDirectives.TruffleBoundary
    private SourceSection materializeSourceSection() {
        return this.getSourceSection();
    }

    public boolean isCaptureFramesForTrace() {
        return false;
    }

    public boolean isCloningAllowed() {
        return false;
    }

    protected boolean isCloneUninitializedSupported() {
        return false;
    }

    protected RootNode cloneUninitialized() {
        throw new UnsupportedOperationException();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    final RootNode cloneUninitializedImpl(CallTarget sourceCallTarget, RootNode uninitializedRootNode) {
        RootNode clonedRoot;
        if (this.isCloneUninitializedSupported()) {
            assert (uninitializedRootNode == null) : "uninitializedRootNode should not have been created";
            clonedRoot = this.cloneUninitialized();
            RootNode.resetFieldsAfterCopy(clonedRoot);
        } else {
            clonedRoot = NodeUtil.cloneNode(uninitializedRootNode);
            assert (clonedRoot.callTarget == null);
            assert (clonedRoot.instrumentationBits == 0);
            assert (clonedRoot.lock == null);
        }
        RootCallTarget clonedTarget = NodeAccessor.RUNTIME.newCallTarget(sourceCallTarget, clonedRoot);
        ReentrantLock l = clonedRoot.getLazyLock();
        l.lock();
        try {
            clonedRoot.setupCallTarget(clonedTarget, "callTarget not null. Was getCallTarget on the result of RootNode.cloneUninitialized called?");
        }
        finally {
            l.unlock();
        }
        return clonedRoot;
    }

    @Override
    public abstract Object execute(VirtualFrame var1);

    public final RootCallTarget getCallTarget() {
        RootCallTarget target = this.callTarget;
        if (target == null || !NodeAccessor.RUNTIME.isLoaded(target)) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            target = this.initializeTarget();
        }
        return target;
    }

    private RootCallTarget initializeTarget() {
        RootCallTarget target;
        ReentrantLock l = this.getLazyLock();
        l.lock();
        try {
            target = this.callTarget;
            if (target == null) {
                target = NodeAccessor.RUNTIME.newCallTarget(null, this);
                this.setupCallTarget(target, "callTarget was set by newCallTarget but should not");
            }
        }
        finally {
            l.unlock();
        }
        return target;
    }

    private void setupCallTarget(RootCallTarget callTarget, String message) {
        assert (this.getLazyLock().isHeldByCurrentThread());
        if (this.callTarget != null) {
            throw CompilerDirectives.shouldNotReachHere(message);
        }
        this.callTarget = callTarget;
        NodeAccessor.RUNTIME.notifyOnLoad(callTarget);
    }

    final RootCallTarget getCallTargetWithoutInitialization() {
        return this.callTarget;
    }

    public final FrameDescriptor getFrameDescriptor() {
        return this.frameDescriptor;
    }

    protected boolean isInstrumentable() {
        return true;
    }

    protected boolean isTrivial() {
        return false;
    }

    protected List<TruffleStackTraceElement> findAsynchronousFrames(Frame frame) {
        return null;
    }

    protected Object translateStackTraceElement(TruffleStackTraceElement element) {
        Node location = element.getLocation();
        return NodeAccessor.EXCEPTION.createDefaultStackTraceElementObject(element.getTarget().getRootNode(), location != null ? location.getEncapsulatingSourceSection() : null);
    }

    protected ExecutionSignature prepareForAOT() {
        return null;
    }

    public static RootNode createConstantNode(Object constant) {
        return new Constant(constant);
    }

    protected FrameDescriptor getParentFrameDescriptor() {
        return null;
    }

    final ReentrantLock getLazyLock() {
        ReentrantLock l = this.lock;
        if (l == null) {
            l = this.initializeLock();
        }
        return l;
    }

    private ReentrantLock initializeLock() {
        ReentrantLock l = new ReentrantLock();
        if (!LOCK_UPDATER.compareAndSet(this, null, l)) {
            l = Objects.requireNonNull(this.lock);
        }
        return l;
    }

    private static final class Constant
    extends RootNode {
        private final Object value;

        Constant(Object value2) {
            super(null);
            this.value = value2;
        }

        @Override
        public Object execute(VirtualFrame frame) {
            return this.value;
        }
    }
}

