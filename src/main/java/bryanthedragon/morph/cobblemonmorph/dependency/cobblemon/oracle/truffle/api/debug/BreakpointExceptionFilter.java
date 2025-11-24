
package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.debug.DebugException;
import com.oracle.truffle.api.debug.Debugger;
import com.oracle.truffle.api.debug.DebuggerSession;
import com.oracle.truffle.api.frame.FrameInstance;
import com.oracle.truffle.api.frame.FrameInstanceVisitor;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.nodes.Node;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

final class BreakpointExceptionFilter {
    private Debugger debugger;
    final boolean caught;
    final boolean uncaught;
    private final DebuggerSession.StableBoolean haveReportedExceptions = new DebuggerSession.StableBoolean(false);
    private final Set<Throwable> reportedExceptions = Collections.newSetFromMap(new WeakHashMap());
    private final ThreadLocal<Throwable> exceptionsOnThreads = new ThreadLocal();

    BreakpointExceptionFilter(boolean caught, boolean uncaught) {
        this.caught = caught;
        this.uncaught = uncaught;
    }

    void setDebugger(Debugger debugger) {
        assert (this.debugger == null);
        this.debugger = debugger;
    }

    Match matchException(Node throwNode, Throwable exception) {
        if (this.wasReported(exception)) {
            return Match.UNMATCHED;
        }
        if (this.caught && this.uncaught) {
            return Match.MATCHED;
        }
        return this.testExceptionCaught(throwNode, exception);
    }

    @CompilerDirectives.TruffleBoundary
    private Match testExceptionCaught(Node throwNode, Throwable exception) {
        if (!InteropLibrary.getUncached().isException(exception)) {
            return this.uncaught ? Match.MATCHED : Match.UNMATCHED;
        }
        DebugException.CatchLocation catchLocation = BreakpointExceptionFilter.getCatchNode(throwNode, exception);
        boolean exceptionCaught = catchLocation != null;
        return new Match(this.caught && exceptionCaught || this.uncaught && !exceptionCaught, catchLocation);
    }

    static DebugException.CatchLocation getCatchNode(final Node throwNode, final Throwable exception) {
        final DebugException.CatchLocation[] catchLocationPtr = new DebugException.CatchLocation[]{null};
        Truffle.getRuntime().iterateFrames(new FrameInstanceVisitor<FrameInstance>(){
            private int depth = 0;

            @Override
            public FrameInstance visitFrame(FrameInstance frameInstance) {
                Node catchNode;
                Node node = this.depth == 0 ? throwNode : frameInstance.getCallNode();
                if (node != null && (catchNode = BreakpointExceptionFilter.getCatchNodeImpl(node, exception)) != null) {
                    catchLocationPtr[0] = new DebugException.CatchLocation(catchNode.getSourceSection(), frameInstance, this.depth);
                    return frameInstance;
                }
                ++this.depth;
                return null;
            }
        });
        return catchLocationPtr[0];
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Node getCatchNodeImpl(Node node, Throwable exception) {
        Node parent;
        InstrumentableNode inode;
        if (node instanceof InstrumentableNode && (inode = (InstrumentableNode)((Object)node)).isInstrumentable() && inode.hasTag(StandardTags.TryBlockTag.class)) {
            Object catches;
            Object nodeObject = inode.getNodeObject();
            if (nodeObject == null) return node;
            InteropLibrary library = InteropLibrary.getFactory().getUncached(nodeObject);
            TruffleObject object = (TruffleObject)nodeObject;
            if (!library.isMemberInvocable(nodeObject, "catches")) return node;
            try {
                catches = library.invokeMember(nodeObject, "catches", exception);
            }
            catch (ArityException | UnknownIdentifierException | UnsupportedMessageException | UnsupportedTypeException ex) {
                throw new IllegalStateException("Unexpected exception from 'catches' on '" + object, exception);
            }
            if (!(catches instanceof Boolean)) {
                throw new IllegalStateException("Unexpected return value from 'catches' on '" + object + "' : " + catches);
            }
            if (Boolean.TRUE.equals(catches)) {
                return node;
            }
        }
        if ((parent = node.getParent()) == null) return null;
        return BreakpointExceptionFilter.getCatchNodeImpl(parent, exception);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private boolean wasReported(Throwable exception) {
        BreakpointExceptionFilter breakpointExceptionFilter = this;
        synchronized (breakpointExceptionFilter) {
            boolean reported = this.reportedExceptions.contains(exception);
            if (!reported) {
                this.reportedExceptions.add(exception);
            }
            return reported;
        }
    }

    void resetReportedException() {
        if (this.haveReportedExceptions.get()) {
            this.doResetReportedException();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private void doResetReportedException() {
        Throwable exception = this.exceptionsOnThreads.get();
        BreakpointExceptionFilter breakpointExceptionFilter = this;
        synchronized (breakpointExceptionFilter) {
            if (exception != null) {
                this.exceptionsOnThreads.remove();
                this.reportedExceptions.remove(exception);
            }
            if (this.reportedExceptions.isEmpty()) {
                this.haveReportedExceptions.set(false);
            }
        }
    }

    static final class Match {
        static final Match MATCHED = new Match(true);
        static final Match UNMATCHED = new Match(false);
        final boolean isMatched;
        final boolean isCatchNodeComputed;
        final DebugException.CatchLocation catchLocation;

        private Match(boolean isMatched) {
            this.isMatched = isMatched;
            this.isCatchNodeComputed = false;
            this.catchLocation = null;
        }

        private Match(boolean isMatched, DebugException.CatchLocation catchLocation) {
            this.isMatched = isMatched;
            this.isCatchNodeComputed = true;
            this.catchLocation = catchLocation;
        }
    }
}

