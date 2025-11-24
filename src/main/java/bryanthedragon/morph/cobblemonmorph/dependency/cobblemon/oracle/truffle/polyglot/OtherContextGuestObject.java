
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.library.Message;
import com.oracle.truffle.api.library.ReflectionLibrary;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.polyglot.EngineAccessor;
import com.oracle.truffle.polyglot.PolyglotContextImpl;
import com.oracle.truffle.polyglot.PolyglotEngineException;
import com.oracle.truffle.polyglot.PolyglotEngineImpl;
import com.oracle.truffle.polyglot.PolyglotImpl;
import com.oracle.truffle.polyglot.PolyglotSharingLayer;

@ExportLibrary(value=ReflectionLibrary.class)
final class OtherContextGuestObject
implements TruffleObject {
    static final Object OTHER_VALUE = new Object();
    static final ReflectionLibrary OTHER_VALUE_UNCACHED = ReflectionLibrary.getFactory().getUncached(OTHER_VALUE);
    final PolyglotContextImpl receiverContext;
    final Object delegate;
    final PolyglotContextImpl delegateContext;
    static final int CACHE_LIMIT = 5;
    private static final Message IDENTICAL = Message.resolve(InteropLibrary.class, "isIdentical");

    OtherContextGuestObject(PolyglotContextImpl receiverContext, Object delegate, PolyglotContextImpl delegateContext) {
        assert (!(delegate instanceof OtherContextGuestObject)) : "recursive host foreign value found";
        assert (receiverContext != null && delegateContext != null) : "Must have associated contexts.";
        assert (receiverContext != delegateContext) : "no need for foreign value if contexts match";
        this.delegate = delegate;
        this.receiverContext = receiverContext;
        this.delegateContext = delegateContext;
    }

    static boolean canCache(PolyglotSharingLayer cachedLayer, PolyglotContextImpl context0, PolyglotContextImpl context1) {
        return cachedLayer != null && cachedLayer.isClaimed() && cachedLayer.shared == context0.layer.shared && cachedLayer.shared == context1.layer.shared;
    }

    static PolyglotSharingLayer getCachedLayer(Node library) {
        RootNode root = library.getRootNode();
        if (root == null) {
            return null;
        }
        return (PolyglotSharingLayer)EngineAccessor.NODES.getSharingLayer(root);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static Object sendImpl(PolyglotSharingLayer layer, Object receiver, Message message, Object[] args, PolyglotContextImpl receiverContext, PolyglotContextImpl delegateContext, ReflectionLibrary delegateLibrary, BranchProfile seenOther, BranchProfile seenError) throws Exception {
        if (message.getLibraryClass() != InteropLibrary.class) {
            seenOther.enter();
            return OtherContextGuestObject.fallbackSend(message, args);
        }
        try {
            Object[] prev = layer.engine.enter(delegateContext);
            try {
                Object returnValue;
                Object[] migratedArgs = OtherContextGuestObject.migrateArgs(message, args, receiverContext, delegateContext);
                if (message == IDENTICAL && migratedArgs[0] instanceof OtherContextGuestObject) {
                    OtherContextGuestObject foreignCompare = (OtherContextGuestObject)migratedArgs[0];
                    assert (foreignCompare.delegateContext != delegateContext);
                    returnValue = Boolean.FALSE;
                } else {
                    returnValue = delegateLibrary.send(receiver, message, migratedArgs);
                }
                Object object = OtherContextGuestObject.migrateReturn(returnValue, receiverContext, delegateContext);
                return object;
            }
            catch (Throwable e) {
                seenError.enter();
                throw OtherContextGuestObject.migrateException(receiverContext, e, delegateContext);
            }
            finally {
                layer.engine.leave(prev, delegateContext);
            }
        }
        catch (Throwable t) {
            seenError.enter();
            throw OtherContextGuestObject.toHostOrInnerContextBoundaryException(receiverContext, t, delegateContext);
        }
    }

    @CompilerDirectives.TruffleBoundary
    static <T extends Throwable> RuntimeException migrateException(PolyglotContextImpl receiverContext, Throwable e, PolyglotContextImpl valueContext) throws T {
        if (e instanceof OtherContextException) {
            OtherContextException other = (OtherContextException)e;
            if (other.receiverContext == receiverContext && other.delegateContext == valueContext) {
                throw other;
            }
            throw new OtherContextException(receiverContext, other.delegate, other.delegateContext);
        }
        if (InteropLibrary.getUncached().isException(e)) {
            if (e instanceof AbstractTruffleException) {
                throw new OtherContextException(receiverContext, (AbstractTruffleException)e, valueContext);
            }
            throw new OtherContextException(receiverContext, (Exception)e, valueContext);
        }
        throw e;
    }

    @CompilerDirectives.TruffleBoundary
    static <T extends Throwable> RuntimeException toHostOrInnerContextBoundaryException(PolyglotContextImpl receiverContext, Throwable e, PolyglotContextImpl delegateContext) throws T {
        if (e instanceof PolyglotEngineException || e instanceof PolyglotEngineImpl.CancelExecution || e instanceof PolyglotContextImpl.ExitException) {
            try {
                if (e instanceof PolyglotEngineImpl.CancelExecution) {
                    if (delegateContext.parent != null) {
                        PolyglotContextImpl.State receiverContextState = receiverContext.state;
                        if (!receiverContextState.isCancelling() && receiverContextState != PolyglotContextImpl.State.CLOSED_CANCELLED) {
                            delegateContext.runOnCancelled();
                            throw new IllegalStateException("Context cancel exception of inner context leaks outside to a non-cancelled context!");
                        }
                        throw (PolyglotEngineImpl.CancelExecution)e;
                    }
                    throw PolyglotImpl.guestToHostException(delegateContext.getHostContext(), e, false);
                }
                if (e instanceof PolyglotContextImpl.ExitException) {
                    if (delegateContext.parent != null) {
                        PolyglotContextImpl.State receiverContextState = receiverContext.state;
                        if (!receiverContextState.isExiting() && receiverContextState != PolyglotContextImpl.State.CLOSED_EXITED) {
                            delegateContext.runOnExited(((PolyglotContextImpl.ExitException)e).getExitCode());
                            throw new IllegalStateException("Context exit exception of inner context leaks outside to a non-exited context!");
                        }
                        throw (PolyglotContextImpl.ExitException)e;
                    }
                    throw PolyglotImpl.guestToHostException(delegateContext.getHostContext(), e, false);
                }
                if (delegateContext.parent != null && e instanceof PolyglotEngineException && ((PolyglotEngineException)e).closedException) {
                    boolean enclosingDisposing;
                    PolyglotContextImpl.State enclosingState = receiverContext != null ? receiverContext.state : PolyglotContextImpl.State.DEFAULT;
                    boolean bl = enclosingDisposing = receiverContext != null && receiverContext.disposing;
                    if (enclosingState != PolyglotContextImpl.State.CLOSED && !enclosingDisposing) {
                        delegateContext.runOnClosed();
                        throw new IllegalStateException("Context close exception of inner context leaks outside to a non-closed context!");
                    }
                }
                throw PolyglotImpl.engineToLanguageException(e);
            }
            catch (Throwable ex) {
                if (delegateContext.parent != null) {
                    throw ex;
                }
                throw receiverContext.engine.host.toHostException(receiverContext.getHostContextImpl(), ex);
            }
        }
        throw e;
    }

    @CompilerDirectives.TruffleBoundary
    private static Object fallbackSend(Message message, Object[] args) throws Exception {
        return OTHER_VALUE_UNCACHED.send(OTHER_VALUE, message, args);
    }

    private static Object migrateReturn(Object arg, PolyglotContextImpl receiverContext, PolyglotContextImpl delegateContext) {
        if (arg instanceof TruffleObject) {
            return receiverContext.migrateValue(arg, delegateContext);
        }
        assert (InteropLibrary.isValidProtocolValue(arg)) : "unexpected interop primitive";
        return arg;
    }

    private static Object migrateArg(Object arg, PolyglotContextImpl receiverContext, PolyglotContextImpl delegateContext) {
        if (arg instanceof TruffleObject) {
            return delegateContext.migrateValue(arg, receiverContext);
        }
        if (arg instanceof Object[]) {
            return OtherContextGuestObject.migrateArgs(null, (Object[])arg, receiverContext, delegateContext);
        }
        if (arg instanceof InteropLibrary) {
            return InteropLibrary.getUncached();
        }
        assert (InteropLibrary.isValidProtocolValue(arg));
        return arg;
    }

    private static Object[] migrateArgs(Message message, Object[] args, PolyglotContextImpl receiverContext, PolyglotContextImpl delegateContext) {
        if (message != null) {
            return OtherContextGuestObject.migrateArgsExplode(message, args, receiverContext, delegateContext);
        }
        Object[] newArgs = new Object[args.length];
        for (int i = 0; i < args.length; ++i) {
            newArgs[i] = OtherContextGuestObject.migrateArg(args[i], receiverContext, delegateContext);
        }
        return newArgs;
    }

    @ExplodeLoop
    private static Object[] migrateArgsExplode(Message message, Object[] args, PolyglotContextImpl receiverContext, PolyglotContextImpl delegateContext) {
        int length = message.getParameterCount();
        Object[] newArgs = new Object[length - 1];
        for (int i = 0; i < length - 1; ++i) {
            newArgs[i] = OtherContextGuestObject.migrateArg(args[i], receiverContext, delegateContext);
        }
        return newArgs;
    }

    public String toString() {
        return "OtherContextGuestObject[targetContext=0x" + Integer.toHexString(System.identityHashCode(this.receiverContext)) + ", delegate=(" + this.delegate.getClass().getSimpleName() + "(0x" + Integer.toHexString(System.identityHashCode(this.delegate)) + "), delegateContext=0x" + Integer.toHexString(System.identityHashCode(this.delegateContext));
    }

    @ExportLibrary(value=ReflectionLibrary.class)
    static class OtherContextException
    extends AbstractTruffleException {
        final PolyglotContextImpl receiverContext;
        final Exception delegate;
        final PolyglotContextImpl delegateContext;

        OtherContextException(PolyglotContextImpl receiverContext, AbstractTruffleException delegate, PolyglotContextImpl delegateContext) {
            super(delegate);
            assert (!(delegate instanceof OtherContextException)) : "recursive host foreign value found";
            assert (receiverContext != null && delegateContext != null) : "Must have associated contexts.";
            assert (receiverContext != delegateContext) : "no need for foreign value if contexts match";
            this.delegate = delegate;
            this.receiverContext = receiverContext;
            this.delegateContext = delegateContext;
        }

        @CompilerDirectives.TruffleBoundary
        OtherContextException(PolyglotContextImpl thisContext, Exception delegate, PolyglotContextImpl delegateContext) {
            super(delegate.getMessage());
            assert (!(delegate instanceof OtherContextException)) : "recursive host foreign value found";
            assert (thisContext != null && delegateContext != null) : "Must have associated contexts.";
            assert (thisContext != delegateContext) : "no need for foreign value if contexts match";
            this.delegate = delegate;
            this.receiverContext = thisContext;
            this.delegateContext = delegateContext;
        }

        @ExportMessage
        @ImportStatic(value={OtherContextGuestObject.class})
        static class Send {
            Send() {
            }

            @Specialization(guards={"canCache(cachedLayer, receiver.receiverContext, receiver.delegateContext)"}, limit="1")
            static Object doCached(OtherContextException receiver, Message message, Object[] args, @CachedLibrary(value="receiver") ReflectionLibrary receiverLibrary, @Cached(value="getCachedLayer(receiverLibrary)") PolyglotSharingLayer cachedLayer, @CachedLibrary(limit="CACHE_LIMIT") ReflectionLibrary delegateLibrary, @Cached BranchProfile seenOther, @Cached BranchProfile seenError) throws Exception {
                assert (cachedLayer != null);
                return OtherContextGuestObject.sendImpl(cachedLayer, receiver.delegate, message, args, receiver.receiverContext, receiver.delegateContext, delegateLibrary, seenOther, seenError);
            }

            @CompilerDirectives.TruffleBoundary
            @Specialization(replaces={"doCached"})
            static Object doSlowPath(OtherContextException receiver, Message message, Object[] args) throws Exception {
                return OtherContextGuestObject.sendImpl(receiver.receiverContext.layer, receiver.delegate, message, args, receiver.receiverContext, receiver.delegateContext, ReflectionLibrary.getUncached(receiver.delegate), BranchProfile.getUncached(), BranchProfile.getUncached());
            }
        }
    }

    @ExportMessage
    @ImportStatic(value={OtherContextGuestObject.class})
    static class Send {
        Send() {
        }

        @Specialization(guards={"canCache(cachedLayer, receiver.receiverContext, receiver.delegateContext)"}, limit="1")
        static Object doCached(OtherContextGuestObject receiver, Message message, Object[] args, @CachedLibrary(value="receiver") ReflectionLibrary receiverLibrary, @Cached(value="getCachedLayer(receiverLibrary)") PolyglotSharingLayer cachedLayer, @CachedLibrary(limit="CACHE_LIMIT") ReflectionLibrary delegateLibrary, @Cached BranchProfile seenOther, @Cached BranchProfile seenError) throws Exception {
            assert (cachedLayer != null);
            return OtherContextGuestObject.sendImpl(cachedLayer, receiver.delegate, message, args, receiver.receiverContext, receiver.delegateContext, delegateLibrary, seenOther, seenError);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization(replaces={"doCached"})
        static Object doSlowPath(OtherContextGuestObject receiver, Message message, Object[] args) throws Exception {
            return OtherContextGuestObject.sendImpl(receiver.receiverContext.layer, receiver.delegate, message, args, receiver.receiverContext, receiver.delegateContext, ReflectionLibrary.getUncached(receiver.delegate), BranchProfile.getUncached(), BranchProfile.getUncached());
        }
    }
}

