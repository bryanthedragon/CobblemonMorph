
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.JSInteropExecuteNode;
import com.oracle.truffle.js.nodes.interop.JSInteropInstantiateNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.interop.JSMetaType;
import com.oracle.truffle.js.runtime.objects.JSClassObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;

@ExportLibrary(value=InteropLibrary.class)
public final class JSProxyObject
extends JSClassObject {
    private Object proxyTarget;
    private JSDynamicObject proxyHandler;

    protected JSProxyObject(Shape shape, Object proxyTarget, JSDynamicObject proxyHandler) {
        super(shape);
        this.proxyTarget = proxyTarget;
        this.proxyHandler = proxyHandler;
    }

    public JSDynamicObject getProxyHandler() {
        return this.proxyHandler;
    }

    public Object getProxyTarget() {
        return this.proxyTarget;
    }

    public void revoke(boolean isCallable, boolean isConstructor) {
        this.proxyHandler = Null.instance;
        this.proxyTarget = RevokedTarget.lookup(isCallable, isConstructor);
    }

    public static JSProxyObject create(JSRealm realm, JSObjectFactory factory, Object target, JSDynamicObject handler) {
        return factory.initProto(new JSProxyObject(factory.getShape(realm), target, handler), realm);
    }

    @ExportMessage
    public boolean isExecutable(@Cached IsCallableNode isCallable) {
        return isCallable.executeBoolean(this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @ExportMessage
    public Object execute(Object[] args, @CachedLibrary(value="this") InteropLibrary self, @Cached JSInteropExecuteNode callNode, @Cached.Shared(value="exportValue") @Cached ExportValueNode exportNode) throws UnsupportedMessageException {
        JavaScriptLanguage language = JavaScriptLanguage.get(self);
        JSRealm realm = JSRealm.get(self);
        language.interopBoundaryEnter(realm);
        try {
            Object result = callNode.execute(this, Undefined.instance, args);
            Object object = exportNode.execute(result);
            return object;
        }
        finally {
            language.interopBoundaryExit(realm);
        }
    }

    @ExportMessage
    public boolean isInstantiable() {
        return JSRuntime.isConstructor(this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @ExportMessage
    public Object instantiate(Object[] args, @CachedLibrary(value="this") InteropLibrary self, @Cached JSInteropInstantiateNode callNode, @Cached.Shared(value="exportValue") @Cached ExportValueNode exportNode) throws UnsupportedMessageException {
        JavaScriptLanguage language = JavaScriptLanguage.get(self);
        JSRealm realm = JSRealm.get(self);
        language.interopBoundaryEnter(realm);
        try {
            Object result = callNode.execute(this, args);
            Object object = exportNode.execute(result);
            return object;
        }
        finally {
            language.interopBoundaryExit(realm);
        }
    }

    @ExportMessage
    public boolean hasMetaObject() {
        return true;
    }

    @ExportMessage
    public Object getMetaObject() {
        return JSMetaType.JS_PROXY;
    }

    @ExportLibrary(value=InteropLibrary.class)
    public static final class RevokedTarget
    implements TruffleObject {
        private final boolean isCallable;
        private final boolean isConstructor;
        @CompilerDirectives.CompilationFinal(dimensions=1)
        static final Object[] REVOKED_TARGET = new Object[]{Null.instance, new RevokedTarget(true, false), new RevokedTarget(false, true), new RevokedTarget(true, true)};

        RevokedTarget(boolean isCallable, boolean isConstructor) {
            this.isCallable = isCallable;
            this.isConstructor = isConstructor;
        }

        @ExportMessage
        public boolean isExecutable() {
            return this.isCallable;
        }

        @ExportMessage
        public Object execute(Object[] args) throws UnsupportedMessageException {
            if (this.isExecutable()) {
                throw Errors.createTypeErrorProxyRevoked();
            }
            throw UnsupportedMessageException.create();
        }

        @ExportMessage
        public boolean isInstantiable() {
            return this.isConstructor;
        }

        @ExportMessage
        public Object instantiate(Object[] args) throws UnsupportedMessageException {
            if (this.isInstantiable()) {
                throw Errors.createTypeErrorProxyRevoked();
            }
            throw UnsupportedMessageException.create();
        }

        @ExportMessage
        public TruffleString toDisplayString(boolean allowSideEffects) {
            return Null.NAME;
        }

        @ExportMessage
        public boolean hasLanguage() {
            return true;
        }

        @ExportMessage
        public Class<? extends TruffleLanguage<?>> getLanguage() {
            return JavaScriptLanguage.class;
        }

        static Object lookup(boolean callable, boolean constructor) {
            return REVOKED_TARGET[(callable ? 1 : 0) + (constructor ? 2 : 0)];
        }
    }
}

