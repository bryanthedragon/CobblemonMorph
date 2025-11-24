
package com.oracle.truffle.js.runtime.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.Library;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.utilities.FinalBitSet;
import com.oracle.truffle.api.utilities.TriState;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.JSInteropExecuteNode;
import com.oracle.truffle.js.nodes.interop.JSInteropExecuteNodeGen;
import com.oracle.truffle.js.nodes.promise.UnwrapPromiseNode;
import com.oracle.truffle.js.runtime.interop.InteropAsyncFunction;
import com.oracle.truffle.js.runtime.interop.InteropFunction;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=InteropAsyncFunction.class)
final class InteropAsyncFunctionGen {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

    private InteropAsyncFunctionGen() {
    }

    static {
        LibraryExport.register(InteropAsyncFunction.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=InteropAsyncFunction.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        static final FinalBitSet ENABLED_MESSAGES = InteropLibraryExports.createMessageBitSet(INTEROP_LIBRARY_, "isIdenticalOrUndefined", "identityHashCode", "isExecutable", "execute");

        private InteropLibraryExports() {
            super(InteropLibrary.class, InteropAsyncFunction.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof InteropAsyncFunction);
            InteropLibrary uncached = InteropLibraryExports.createDelegate(INTEROP_LIBRARY_, new Uncached());
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof InteropAsyncFunction);
            return InteropLibraryExports.createDelegate(INTEROP_LIBRARY_, new Cached(receiver));
        }

        @GeneratedBy(value=InteropAsyncFunction.class)
        @DenyReplace
        private static final class Uncached
        extends InteropLibrary
        implements LibraryExport.DelegateExport {
            protected Uncached() {
            }

            @Override
            public FinalBitSet getDelegateExportMessages() {
                return ENABLED_MESSAGES;
            }

            @Override
            public Object readDelegateExport(Object receiver_) {
                return ((InteropAsyncFunction)receiver_).function;
            }

            @Override
            public Library getDelegateExportLibrary(Object delegate_) {
                return INTEROP_LIBRARY_.getUncached(delegate_);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof InteropAsyncFunction) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof InteropAsyncFunction;
            }

            @Override
            public boolean isAdoptable() {
                return false;
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.MEGAMORPHIC;
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public TriState isIdenticalOrUndefined(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                InteropFunction arg0Value = (InteropFunction)arg0Value_;
                return arg0Value.isIdenticalOrUndefined(arg1Value, INTEROP_LIBRARY_.getUncached(), INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int identityHashCode(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((InteropFunction)receiver).identityHashCode();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isExecutable(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((InteropAsyncFunction)receiver).isExecutable();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object execute(Object arg0Value_, Object ... arg1Value) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                InteropAsyncFunction arg0Value = (InteropAsyncFunction)arg0Value_;
                return arg0Value.execute(arg1Value, (InteropLibrary)this.getParent(), JSInteropExecuteNodeGen.getUncached(), ExportValueNode.getUncached(), UnwrapPromiseNode.getUncached());
            }
        }

        @GeneratedBy(value=InteropAsyncFunction.class)
        private static final class Cached
        extends InteropLibrary
        implements LibraryExport.DelegateExport {
            @Node.Child
            private InteropLibrary receiverFunctionInteropLibrary_;
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @Node.Child
            private InteropLibrary isIdenticalOrUndefinedNode__isIdenticalOrUndefined_thisLib_;
            @Node.Child
            private InteropLibrary isIdenticalOrUndefinedNode__isIdenticalOrUndefined_otherLib_;
            @Node.Child
            private JSInteropExecuteNode executeNode__execute_callNode_;
            @Node.Child
            private ExportValueNode executeNode__execute_exportNode_;
            @Node.Child
            private UnwrapPromiseNode executeNode__execute_unwrapPromise_;

            protected Cached(Object receiver) {
                InteropAsyncFunction castReceiver = (InteropAsyncFunction)receiver;
                this.receiverFunctionInteropLibrary_ = super.insert(INTEROP_LIBRARY_.create(castReceiver.function));
            }

            @Override
            public FinalBitSet getDelegateExportMessages() {
                return ENABLED_MESSAGES;
            }

            @Override
            public Object readDelegateExport(Object receiver_) {
                return ((InteropAsyncFunction)receiver_).function;
            }

            @Override
            public Library getDelegateExportLibrary(Object delegate) {
                return this.receiverFunctionInteropLibrary_;
            }

            @Override
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof InteropAsyncFunction) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                if (!(receiver instanceof InteropAsyncFunction)) {
                    return false;
                }
                return this.receiverFunctionInteropLibrary_.accepts(((InteropAsyncFunction)receiver).function);
            }

            @Override
            protected TriState isIdenticalOrUndefined(Object arg0Value_, Object arg1Value) {
                assert (arg0Value_ instanceof InteropAsyncFunction) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                InteropAsyncFunction arg0Value = (InteropAsyncFunction)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 1) != 0) {
                    return arg0Value.isIdenticalOrUndefined(arg1Value, this.isIdenticalOrUndefinedNode__isIdenticalOrUndefined_thisLib_, this.isIdenticalOrUndefinedNode__isIdenticalOrUndefined_otherLib_);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isIdenticalOrUndefinedNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private TriState isIdenticalOrUndefinedNode_AndSpecialize(InteropFunction arg0Value, Object arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.isIdenticalOrUndefinedNode__isIdenticalOrUndefined_thisLib_ = super.insert(INTEROP_LIBRARY_.createDispatched(5));
                    this.isIdenticalOrUndefinedNode__isIdenticalOrUndefined_otherLib_ = super.insert(INTEROP_LIBRARY_.createDispatched(5));
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    TriState triState = arg0Value.isIdenticalOrUndefined(arg1Value, this.isIdenticalOrUndefinedNode__isIdenticalOrUndefined_thisLib_, this.isIdenticalOrUndefinedNode__isIdenticalOrUndefined_otherLib_);
                    return triState;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public NodeCost getCost() {
                int state_0 = this.state_0_;
                if ((state_0 & 1) == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                return NodeCost.MONOMORPHIC;
            }

            @Override
            public int identityHashCode(Object receiver) throws UnsupportedMessageException {
                assert (receiver instanceof InteropAsyncFunction) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((InteropAsyncFunction)receiver).identityHashCode();
            }

            @Override
            public boolean isExecutable(Object receiver) {
                assert (receiver instanceof InteropAsyncFunction) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((InteropAsyncFunction)receiver).isExecutable();
            }

            @Override
            public Object execute(Object arg0Value_, Object ... arg1Value) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
                assert (arg0Value_ instanceof InteropAsyncFunction) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                InteropAsyncFunction arg0Value = (InteropAsyncFunction)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 2) != 0) {
                    InteropLibrary executeNode__execute_self__ = (InteropLibrary)this.getParent();
                    return arg0Value.execute(arg1Value, executeNode__execute_self__, this.executeNode__execute_callNode_, this.executeNode__execute_exportNode_, this.executeNode__execute_unwrapPromise_);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeNode_AndSpecialize(InteropAsyncFunction arg0Value, Object[] arg1Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    InteropLibrary executeNode__execute_self__ = null;
                    executeNode__execute_self__ = (InteropLibrary)this.getParent();
                    this.executeNode__execute_callNode_ = super.insert(JSInteropExecuteNodeGen.create());
                    this.executeNode__execute_exportNode_ = super.insert(ExportValueNode.create());
                    this.executeNode__execute_unwrapPromise_ = super.insert(UnwrapPromiseNode.create());
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.execute(arg1Value, executeNode__execute_self__, this.executeNode__execute_callNode_, this.executeNode__execute_exportNode_, this.executeNode__execute_unwrapPromise_);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }
        }
    }
}

