
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.polyglot.EngineAccessor;
import com.oracle.truffle.polyglot.PolyglotContextImpl;
import com.oracle.truffle.polyglot.PolyglotImpl;
import com.oracle.truffle.polyglot.PolyglotLanguage;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import com.oracle.truffle.polyglot.PolyglotLanguageInstance;
import com.oracle.truffle.polyglot.PolyglotSharingLayer;
import java.util.Objects;

abstract class HostToGuestRootNode
extends RootNode {
    protected static final int ARGUMENT_OFFSET = 2;
    @CompilerDirectives.CompilationFinal
    private boolean seenEnter;
    @CompilerDirectives.CompilationFinal
    private boolean seenNonEnter;
    private final PolyglotLanguage language;
    private final PolyglotSharingLayer layer;
    @CompilerDirectives.CompilationFinal
    private boolean seenError;

    HostToGuestRootNode(PolyglotSharingLayer layer) {
        super(null);
        assert (layer != null);
        this.layer = layer;
        this.language = null;
        EngineAccessor.NODES.setSharingLayer(this, layer);
    }

    HostToGuestRootNode(PolyglotLanguageInstance language) {
        super(null);
        EngineAccessor.NODES.setSharingLayer(this, language.sharing);
        this.layer = language.sharing;
        this.language = language.language;
    }

    protected abstract Class<?> getReceiverType();

    @Override
    public final Object execute(VirtualFrame frame) {
        Object[] prev;
        boolean needsEnter;
        PolyglotContextImpl constantContext;
        Object[] args = frame.getArguments();
        PolyglotLanguageContext languageContext = this.layer.getSingleConstantLanguageContext(this.language);
        if (languageContext == null) {
            languageContext = (PolyglotLanguageContext)args[0];
        }
        if ((constantContext = this.layer.getSingleConstantContext()) == null) {
            constantContext = languageContext.context;
        }
        assert (languageContext.context == constantContext);
        PolyglotContextImpl context = constantContext;
        assert (Objects.equals(this.layer, languageContext.context.layer)) : PolyglotSharingLayer.invalidSharingError(this, this.layer, languageContext.context.layer);
        try {
            needsEnter = this.layer.engine.needsEnter(context);
            if (needsEnter) {
                if (!this.seenEnter) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.seenEnter = true;
                }
                prev = this.layer.engine.enterCached(context, true);
            } else {
                if (!this.seenNonEnter) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.seenNonEnter = true;
                }
                prev = null;
            }
        }
        catch (Throwable e) {
            throw this.handleException(languageContext, e, false, RuntimeException.class);
        }
        try {
            Object[] arguments = frame.getArguments();
            Object receiver = this.getReceiverType().cast(arguments[1]);
            Object result = this.executeImpl(languageContext, receiver, arguments);
            assert (!(result instanceof TruffleObject));
            Object object = result;
            return object;
        }
        catch (Throwable e) {
            throw this.handleException(languageContext, e, true, RuntimeException.class);
        }
        finally {
            if (needsEnter) {
                try {
                    this.layer.engine.leaveCached(prev, context);
                }
                catch (Throwable e) {
                    throw this.handleException(languageContext, e, false, RuntimeException.class);
                }
            }
        }
    }

    private <E extends Throwable> E handleException(PolyglotLanguageContext languageContext, Throwable e, boolean entered, Class<E> exceptionType) throws E {
        if (!this.seenError) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.seenError = true;
        }
        throw PolyglotImpl.guestToHostException(languageContext, e, entered);
    }

    protected abstract Object executeImpl(PolyglotLanguageContext var1, Object var2, Object[] var3);

    static <T> T installHostCodeCache(PolyglotLanguageContext languageContext, Object key, T value2, Class<T> expectedType) {
        T result = expectedType.cast(languageContext.getLanguageInstance().hostToGuestCodeCache.putIfAbsent(key, value2));
        if (result != null) {
            return result;
        }
        return value2;
    }

    static <T> T lookupHostCodeCache(PolyglotLanguageContext languageContext, Object key, Class<T> expectedType) {
        return expectedType.cast(languageContext.getLanguageInstance().hostToGuestCodeCache.get(key));
    }
}

