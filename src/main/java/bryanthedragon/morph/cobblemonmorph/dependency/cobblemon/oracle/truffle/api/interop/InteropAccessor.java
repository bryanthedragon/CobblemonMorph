
package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.impl.Accessor;
import com.oracle.truffle.api.interop.ArrayIterator;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.Node;

final class InteropAccessor
extends Accessor {
    static final InteropAccessor ACCESSOR = new InteropAccessor();
    static final Accessor.LanguageSupport LANGUAGE = ACCESSOR.languageSupport();
    static final Accessor.ExceptionSupport EXCEPTION = ACCESSOR.exceptionSupport();
    static final Accessor.InstrumentSupport INSTRUMENT = ACCESSOR.instrumentSupport();
    static final Accessor.NodeSupport NODES = ACCESSOR.nodeSupport();
    static final Accessor.HostSupport HOST = ACCESSOR.hostSupport();

    private InteropAccessor() {
    }

    static Object checkInteropType(Object obj) {
        assert (InteropAccessor.checkInteropTypeImpl(obj));
        return obj;
    }

    private static boolean checkInteropTypeImpl(Object obj) {
        if (InteropLibrary.isValidValue(obj)) {
            return true;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        Class<?> clazz = obj != null ? obj.getClass() : null;
        return InteropAccessor.yieldAnError(clazz);
    }

    @CompilerDirectives.TruffleBoundary
    private static boolean yieldAnError(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        sb.append(clazz == null ? "null" : clazz.getName());
        sb.append(" isn't allowed Truffle interop type!\n");
        if (clazz == null) {
            throw new NullPointerException(sb.toString());
        }
        throw new ClassCastException(sb.toString());
    }

    static final class EmptyTruffleObject
    implements TruffleObject {
        static final EmptyTruffleObject INSTANCE = new EmptyTruffleObject();

        EmptyTruffleObject() {
        }
    }

    static class InteropImpl
    extends Accessor.InteropSupport {
        InteropImpl() {
        }

        @Override
        public boolean isTruffleObject(Object value2) {
            return value2 instanceof TruffleObject;
        }

        @Override
        public void checkInteropType(Object result) {
            InteropAccessor.checkInteropType(result);
        }

        @Override
        public boolean isExecutableObject(Object value2) {
            return InteropLibrary.getUncached().isExecutable(value2);
        }

        @Override
        public boolean isScopeObject(Object receiver) {
            InteropLibrary interop = InteropLibrary.getUncached();
            return interop.isScope(receiver) && interop.hasMembers(receiver);
        }

        @Override
        public Object createDefaultNodeObject(Node node) {
            return EmptyTruffleObject.INSTANCE;
        }

        @Override
        public Object createDefaultIterator(Object receiver) {
            return new ArrayIterator(receiver);
        }

        @Override
        public Node createDispatchedInteropLibrary(int limit) {
            return InteropLibrary.getFactory().createDispatched(limit);
        }

        @Override
        public Node getUncachedInteropLibrary() {
            return InteropLibrary.getUncached();
        }

        @Override
        public long unboxPointer(Node library, Object value2) {
            InteropLibrary interop = (InteropLibrary)library;
            if (!interop.isPointer(value2)) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                throw new IllegalArgumentException("value is not an interop pointer");
            }
            try {
                return interop.asPointer(value2);
            }
            catch (UnsupportedMessageException e) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                throw new IllegalArgumentException("value is not an interop pointer");
            }
        }
    }
}

