
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.utilities.TriState;
import com.oracle.truffle.js.nodes.interop.JSInteropGetIteratorNode;
import com.oracle.truffle.js.nodes.interop.KeyInfoNode;
import com.oracle.truffle.js.runtime.GraalJSException;
import com.oracle.truffle.js.runtime.builtins.JSErrorObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObjectGen;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSErrorObject.class)
final class JSErrorObjectGen {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

    private JSErrorObjectGen() {
    }

    static {
        LibraryExport.register(JSErrorObject.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=JSErrorObject.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, JSErrorObject.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof JSErrorObject);
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof JSErrorObject);
            return new Cached(receiver);
        }

        @GeneratedBy(value=JSErrorObject.class)
        @DenyReplace
        private static final class Uncached
        extends JSNonProxyObjectGen.InteropLibraryExports.Uncached {
            protected Uncached(Object receiver) {
                super(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                return super.accepts(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public TriState isIdenticalOrUndefined(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSErrorObject arg0Value = (JSErrorObject)arg0Value_;
                if (arg1Value instanceof JSDynamicObject) {
                    JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
                    return JSErrorObject.IsIdenticalOrUndefined.doError(arg0Value, arg1Value_);
                }
                if (arg1Value instanceof GraalJSException) {
                    GraalJSException arg1Value_ = (GraalJSException)arg1Value;
                    return JSErrorObject.IsIdenticalOrUndefined.doException(arg0Value, arg1Value_);
                }
                return JSErrorObject.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isException(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSErrorObject)receiver).isException();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public RuntimeException throwException(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSErrorObject)receiver).throwException();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public ExceptionType getExceptionType(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSErrorObject arg0Value = (JSErrorObject)arg0Value_;
                return arg0Value.getExceptionType(INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isExceptionIncompleteSource(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSErrorObject arg0Value = (JSErrorObject)arg0Value_;
                return arg0Value.isExceptionIncompleteSource(INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasExceptionMessage(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSErrorObject arg0Value = (JSErrorObject)arg0Value_;
                return arg0Value.hasExceptionMessage(INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getExceptionMessage(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSErrorObject arg0Value = (JSErrorObject)arg0Value_;
                return arg0Value.getExceptionMessage(INTEROP_LIBRARY_.getUncached());
            }
        }

        @GeneratedBy(value=JSErrorObject.class)
        private static final class Cached
        extends JSNonProxyObjectGen.InteropLibraryExports.Cached {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @Node.Child
            private KeyInfoNode keyInfo;
            @Node.Child
            private JSInteropGetIteratorNode getIterator;
            @Node.Child
            private InteropLibrary getExceptionTypeNode__getExceptionType_exceptions_;
            @Node.Child
            private InteropLibrary isExceptionIncompleteSourceNode__isExceptionIncompleteSource_exceptions_;
            @Node.Child
            private InteropLibrary hasExceptionMessageNode__hasExceptionMessage_exceptions_;
            @Node.Child
            private InteropLibrary getExceptionMessageNode__getExceptionMessage_exceptions_;

            protected Cached(Object receiver) {
                super(receiver);
            }

            @Override
            protected TriState isIdenticalOrUndefined(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSErrorObject arg0Value = (JSErrorObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 7) != 0) {
                    if ((state_0 & 1) != 0 && arg1Value instanceof JSDynamicObject) {
                        JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
                        return JSErrorObject.IsIdenticalOrUndefined.doError(arg0Value, arg1Value_);
                    }
                    if ((state_0 & 2) != 0 && arg1Value instanceof GraalJSException) {
                        GraalJSException arg1Value_ = (GraalJSException)arg1Value;
                        return JSErrorObject.IsIdenticalOrUndefined.doException(arg0Value, arg1Value_);
                    }
                    if ((state_0 & 4) != 0 && Cached.isIdenticalOrUndefinedFallbackGuard_(state_0, arg0Value, arg1Value)) {
                        return JSErrorObject.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isIdenticalOrUndefinedAndSpecialize(arg0Value, arg1Value);
            }

            private TriState isIdenticalOrUndefinedAndSpecialize(JSErrorObject arg0Value, Object arg1Value) {
                int state_0 = this.state_0_;
                if (arg1Value instanceof JSDynamicObject) {
                    JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
                    this.state_0_ = state_0 |= 1;
                    return JSErrorObject.IsIdenticalOrUndefined.doError(arg0Value, arg1Value_);
                }
                if (arg1Value instanceof GraalJSException) {
                    GraalJSException arg1Value_ = (GraalJSException)arg1Value;
                    this.state_0_ = state_0 |= 2;
                    return JSErrorObject.IsIdenticalOrUndefined.doException(arg0Value, arg1Value_);
                }
                this.state_0_ = state_0 |= 4;
                return JSErrorObject.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
            }

            @Override
            public NodeCost getCost() {
                int state_0 = this.state_0_;
                if ((state_0 & 7) == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & 7 & (state_0 & 7) - 1) == 0) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            @Override
            public boolean isException(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSErrorObject)receiver).isException();
            }

            @Override
            public RuntimeException throwException(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSErrorObject)receiver).throwException();
            }

            @Override
            public ExceptionType getExceptionType(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSErrorObject arg0Value = (JSErrorObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 8) != 0) {
                    return arg0Value.getExceptionType(this.getExceptionTypeNode__getExceptionType_exceptions_);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.getExceptionTypeNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private ExceptionType getExceptionTypeNode_AndSpecialize(JSErrorObject arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.getExceptionTypeNode__getExceptionType_exceptions_ = super.insert(INTEROP_LIBRARY_.createDispatched(5));
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    ExceptionType exceptionType = arg0Value.getExceptionType(this.getExceptionTypeNode__getExceptionType_exceptions_);
                    return exceptionType;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isExceptionIncompleteSource(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSErrorObject arg0Value = (JSErrorObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x10) != 0) {
                    return arg0Value.isExceptionIncompleteSource(this.isExceptionIncompleteSourceNode__isExceptionIncompleteSource_exceptions_);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isExceptionIncompleteSourceNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isExceptionIncompleteSourceNode_AndSpecialize(JSErrorObject arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.isExceptionIncompleteSourceNode__isExceptionIncompleteSource_exceptions_ = super.insert(INTEROP_LIBRARY_.createDispatched(5));
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isExceptionIncompleteSource(this.isExceptionIncompleteSourceNode__isExceptionIncompleteSource_exceptions_);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean hasExceptionMessage(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSErrorObject arg0Value = (JSErrorObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x20) != 0) {
                    return arg0Value.hasExceptionMessage(this.hasExceptionMessageNode__hasExceptionMessage_exceptions_);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.hasExceptionMessageNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean hasExceptionMessageNode_AndSpecialize(JSErrorObject arg0Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.hasExceptionMessageNode__hasExceptionMessage_exceptions_ = super.insert(INTEROP_LIBRARY_.createDispatched(5));
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.hasExceptionMessage(this.hasExceptionMessageNode__hasExceptionMessage_exceptions_);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public Object getExceptionMessage(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSErrorObject arg0Value = (JSErrorObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x40) != 0) {
                    return arg0Value.getExceptionMessage(this.getExceptionMessageNode__getExceptionMessage_exceptions_);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.getExceptionMessageNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object getExceptionMessageNode_AndSpecialize(JSErrorObject arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.getExceptionMessageNode__getExceptionMessage_exceptions_ = super.insert(INTEROP_LIBRARY_.createDispatched(5));
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.getExceptionMessage(this.getExceptionMessageNode__getExceptionMessage_exceptions_);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            private static boolean isIdenticalOrUndefinedFallbackGuard_(int state_0, JSErrorObject arg0Value, Object arg1Value) {
                if ((state_0 & 1) == 0 && arg1Value instanceof JSDynamicObject) {
                    return false;
                }
                return (state_0 & 2) != 0 || !(arg1Value instanceof GraalJSException);
            }
        }
    }
}

