
package com.oracle.truffle.host;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.host.HostAccessor;
import com.oracle.truffle.host.HostClassCache;
import com.oracle.truffle.host.HostContext;
import com.oracle.truffle.host.HostLanguageService;
import com.oracle.truffle.host.HostMethodScope;
import com.oracle.truffle.host.HostObject;
import com.oracle.truffle.host.HostToTypeNode;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

final class HostLanguage
extends TruffleLanguage<HostContext> {
    @CompilerDirectives.CompilationFinal
    HostClassCache hostClassCache;
    final AbstractPolyglotImpl.AbstractHostAccess access;
    final AbstractPolyglotImpl polyglot;
    final AbstractPolyglotImpl.APIAccess api;
    final HostLanguageService service;
    @CompilerDirectives.CompilationFinal
    private boolean methodScopingEnabled;
    static final TruffleLanguage.LanguageReference<HostLanguage> REFERENCE = TruffleLanguage.LanguageReference.create(HostLanguage.class);

    HostLanguage(AbstractPolyglotImpl polyglot, AbstractPolyglotImpl.AbstractHostAccess hostAccess) {
        this.polyglot = polyglot;
        this.access = hostAccess;
        this.api = polyglot.getAPIAccess();
        this.service = new HostLanguageService(polyglot, this);
    }

    @Override
    protected boolean patchContext(HostContext context, TruffleLanguage.Env newEnv) {
        return true;
    }

    @Override
    protected HostContext createContext(TruffleLanguage.Env env) {
        env.registerService(this.service);
        return new HostContext(this, env);
    }

    static Object unwrapIfScoped(HostLanguage language, Object o) {
        if (language == null || !language.methodScopingEnabled) {
            return o;
        }
        return language.unwrapIfScoped(o);
    }

    @Override
    protected Object getScope(HostContext context) {
        return context.topScope;
    }

    @Override
    protected boolean isThreadAccessAllowed(Thread thread, boolean singleThreaded) {
        return true;
    }

    private Object unwrapIfScoped(Object obj) {
        if (!this.methodScopingEnabled) {
            return obj;
        }
        Object o = obj;
        if (o instanceof HostMethodScope.ScopedObject) {
            o = ((HostMethodScope.ScopedObject)o).unwrapForGuest();
        }
        return o;
    }

    void initializeHostAccess(HostAccess policy, ClassLoader cl) {
        if (policy == null) {
            return;
        }
        HostClassCache cache = HostClassCache.findOrInitialize(this.access, this.api, policy, cl);
        if (this.hostClassCache != null) {
            if (!this.hostClassCache.hostAccess.equals(cache.hostAccess)) {
                throw HostAccessor.ENGINE.createPolyglotEngineException(new IllegalStateException("Found different host access configuration for a context with a shared engine. The host access configuration must be the same for all contexts of an engine. Provide the same host access configuration using the Context.Builder.allowHostAccess method when constructing the context."));
            }
        } else {
            this.hostClassCache = cache;
        }
        this.methodScopingEnabled = this.api.isMethodScopingEnabled(policy);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    protected Object getLanguageView(HostContext hostContext, Object value2) {
        Object wrapped;
        if (value2 instanceof TruffleObject) {
            InteropLibrary lib = InteropLibrary.getFactory().getUncached(value2);
            try {
                assert (!lib.hasLanguage(value2) || lib.getLanguage(value2) != HostLanguage.class);
            }
            catch (UnsupportedMessageException e) {
                throw CompilerDirectives.shouldNotReachHere(e);
            }
            wrapped = HostToTypeNode.convertToObject(hostContext, value2, lib);
        } else {
            wrapped = value2;
        }
        return HostObject.forObject(wrapped, hostContext);
    }

    @Override
    protected CallTarget parse(TruffleLanguage.ParsingRequest request) throws Exception {
        final String sourceString = request.getSource().getCharacters().toString();
        return new RootNode(this){

            @Override
            public Object execute(VirtualFrame frame) {
                return HostLanguage.this.service.findDynamicClass(HostContext.get(this), sourceString);
            }
        }.getCallTarget();
    }

    static HostLanguage get(Node node) {
        return REFERENCE.get(node);
    }

    @Override
    protected void disposeContext(HostContext context) {
        context.disposeClassLoader();
    }

    static class HostLanguageException
    extends AbstractTruffleException {
        HostLanguageException(String message) {
            super(message);
        }
    }
}

