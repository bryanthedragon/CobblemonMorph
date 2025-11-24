
package com.oracle.truffle.host;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.impl.Accessor;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.host.GuestToHostRootNode;
import com.oracle.truffle.host.HostContext;
import com.oracle.truffle.host.HostException;
import com.oracle.truffle.host.HostFunction;
import com.oracle.truffle.host.HostLanguage;
import com.oracle.truffle.host.HostObject;
import com.oracle.truffle.host.HostProxy;
import com.oracle.truffle.host.HostTargetMapping;
import com.oracle.truffle.host.HostToTypeNode;
import com.oracle.truffle.host.HostUtil;
import java.util.function.Function;
import java.util.function.Predicate;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;
import org.graalvm.polyglot.proxy.Proxy;

final class HostAccessor
extends Accessor {
    static final HostAccessor ACCESSOR = new HostAccessor();
    static final Accessor.NodeSupport NODES = ACCESSOR.nodeSupport();
    static final Accessor.SourceSupport SOURCE = ACCESSOR.sourceSupport();
    static final Accessor.InstrumentSupport INSTRUMENT = ACCESSOR.instrumentSupport();
    static final Accessor.LanguageSupport LANGUAGE = ACCESSOR.languageSupport();
    static final Accessor.InteropSupport INTEROP = ACCESSOR.interopSupport();
    static final Accessor.ExceptionSupport EXCEPTION = ACCESSOR.exceptionSupport();
    static final Accessor.RuntimeSupport RUNTIME = ACCESSOR.runtimeSupport();
    static final Accessor.EngineSupport ENGINE = ACCESSOR.engineSupport();

    HostAccessor() {
    }

    static final class HostImpl
    extends Accessor.HostSupport {
        private HostImpl() {
        }

        @Override
        public TruffleLanguage<?> createDefaultHostLanguage(AbstractPolyglotImpl polyglot, AbstractPolyglotImpl.AbstractHostAccess access) {
            return new HostLanguage(polyglot, access);
        }

        @Override
        public boolean isHostBoundaryValue(Object obj) {
            return obj instanceof HostObject || obj instanceof HostFunction || obj instanceof HostException || obj instanceof HostContext || obj instanceof HostProxy;
        }

        @Override
        public Object convertPrimitiveLossLess(Object value2, Class<?> requestedType) {
            return HostUtil.convertLossLess(value2, requestedType, InteropLibrary.getFactory().getUncached(value2));
        }

        @Override
        public Object convertPrimitiveLossy(Object value2, Class<?> requestedType) {
            InteropLibrary interop = InteropLibrary.getFactory().getUncached(value2);
            Object result = HostUtil.convertLossLess(value2, requestedType, interop);
            if (result == null) {
                result = HostUtil.convertLossy(value2, requestedType, interop);
            }
            return result;
        }

        @Override
        public boolean isDisconnectedHostProxy(Object value2) {
            return HostProxy.isProxyGuestObject(null, value2);
        }

        @Override
        public boolean isDisconnectedHostObject(Object obj) {
            return HostObject.isInstance(null, obj);
        }

        @Override
        public Object unboxDisconnectedHostObject(Object hostValue) {
            return HostObject.valueOf(null, hostValue);
        }

        @Override
        public Object unboxDisconnectedHostProxy(Object hostValue) {
            return HostProxy.toProxyHostObject(null, hostValue);
        }

        @Override
        public Object toDisconnectedHostObject(Object hostValue) {
            if (hostValue instanceof Class) {
                return HostObject.forClass((Class)hostValue, null);
            }
            return HostObject.forObject(hostValue, null);
        }

        @Override
        public Object toDisconnectedHostProxy(Proxy hostValue) {
            return HostProxy.toProxyGuestObject(null, hostValue);
        }

        @Override
        public <S, T> Object newTargetTypeMapping(Class<S> sourceType, Class<T> targetType, Predicate<S> acceptsValue, Function<S, T> convertValue, HostAccess.TargetMappingPrecedence precedence) {
            return new HostTargetMapping(sourceType, targetType, acceptsValue, convertValue, precedence);
        }

        @Override
        public Object getHostNull() {
            return HostObject.NULL;
        }

        @Override
        public boolean isPrimitiveTarget(Class<?> c) {
            return HostToTypeNode.isPrimitiveTarget(c);
        }

        @Override
        public boolean isGuestToHostRootNode(RootNode root) {
            return root instanceof GuestToHostRootNode;
        }

        @Override
        public boolean isHostLanguage(Class<?> languageClass) {
            return languageClass == HostLanguage.class;
        }
    }
}

