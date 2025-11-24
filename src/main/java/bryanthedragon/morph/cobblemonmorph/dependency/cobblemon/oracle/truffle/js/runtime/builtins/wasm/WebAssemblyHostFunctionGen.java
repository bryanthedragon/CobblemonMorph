/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.runtime.builtins.wasm;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.access.GetIteratorBaseNode;
import com.oracle.truffle.js.nodes.access.IterableToListNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.wasm.ToJSValueNode;
import com.oracle.truffle.js.nodes.wasm.ToJSValueNodeGen;
import com.oracle.truffle.js.nodes.wasm.ToWebAssemblyValueNode;
import com.oracle.truffle.js.runtime.builtins.wasm.WebAssemblyHostFunction;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=WebAssemblyHostFunction.class)
public final class WebAssemblyHostFunctionGen {
    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

    private WebAssemblyHostFunctionGen() {
    }

    static {
        LibraryExport.register(WebAssemblyHostFunction.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=WebAssemblyHostFunction.class)
    public static class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, WebAssemblyHostFunction.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof WebAssemblyHostFunction);
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof WebAssemblyHostFunction);
            return new Cached(receiver);
        }

        @GeneratedBy(value=WebAssemblyHostFunction.class)
        public static class Uncached
        extends InteropLibrary {
            private final Class<? extends WebAssemblyHostFunction> receiverClass_;

            protected Uncached(Object receiver) {
                this.receiverClass_ = ((WebAssemblyHostFunction)receiver).getClass();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                assert (receiver.getClass() != this.receiverClass_ || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return CompilerDirectives.isExact(receiver, this.receiverClass_);
            }

            @Override
            public final boolean isAdoptable() {
                return false;
            }

            @Override
            public final NodeCost getCost() {
                return NodeCost.MEGAMORPHIC;
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isExecutable(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return WebAssemblyHostFunction.isExecutable((WebAssemblyHostFunction)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object execute(Object arg0Value_, Object ... arg1Value) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                WebAssemblyHostFunction arg0Value = (WebAssemblyHostFunction)arg0Value_;
                return arg0Value.execute(arg1Value, ToWebAssemblyValueNode.getUncached(), ToJSValueNodeGen.getUncached(), JSFunctionCallNode.getUncachedCall(), BranchProfile.getUncached(), GetIteratorBaseNode.getUncached(), IterableToListNode.getUncached(), this);
            }
        }

        @GeneratedBy(value=WebAssemblyHostFunction.class)
        public static class Cached
        extends InteropLibrary {
            private final Class<? extends WebAssemblyHostFunction> receiverClass_;
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @Node.Child
            private ExecuteData execute_cache;

            protected Cached(Object receiver) {
                WebAssemblyHostFunction castReceiver = (WebAssemblyHostFunction)receiver;
                this.receiverClass_ = castReceiver.getClass();
            }

            @Override
            public boolean accepts(Object receiver) {
                assert (receiver.getClass() != this.receiverClass_ || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return CompilerDirectives.isExact(receiver, this.receiverClass_);
            }

            @Override
            public boolean isExecutable(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return WebAssemblyHostFunction.isExecutable(CompilerDirectives.castExact(receiver, this.receiverClass_));
            }

            @Override
            public Object execute(Object arg0Value_, Object ... arg1Value) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
                ExecuteData s0_;
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                WebAssemblyHostFunction arg0Value = CompilerDirectives.castExact(arg0Value_, this.receiverClass_);
                int state_0 = this.state_0_;
                if (state_0 != 0 && (s0_ = this.execute_cache) != null) {
                    Cached self__ = this;
                    return arg0Value.execute(arg1Value, s0_.toWebAssemblyValueNode_, s0_.toJSValueNode_, s0_.callNode_, s0_.errorBranch_, s0_.getIteratorNode_, s0_.iterableToListNode_, self__);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(WebAssemblyHostFunction arg0Value, Object[] arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached self__ = null;
                    ExecuteData s0_ = super.insert(new ExecuteData());
                    s0_.toWebAssemblyValueNode_ = s0_.insertAccessor(ToWebAssemblyValueNode.create());
                    s0_.toJSValueNode_ = s0_.insertAccessor(ToJSValueNode.create());
                    s0_.callNode_ = s0_.insertAccessor(JSFunctionCallNode.createCall());
                    s0_.errorBranch_ = BranchProfile.create();
                    s0_.getIteratorNode_ = s0_.insertAccessor(GetIteratorBaseNode.create());
                    s0_.iterableToListNode_ = s0_.insertAccessor(IterableToListNode.create());
                    self__ = this;
                    VarHandle.storeStoreFence();
                    this.execute_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.execute(arg1Value, s0_.toWebAssemblyValueNode_, s0_.toJSValueNode_, s0_.callNode_, s0_.errorBranch_, s0_.getIteratorNode_, s0_.iterableToListNode_, self__);
                    return object;
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
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                return NodeCost.MONOMORPHIC;
            }

            @GeneratedBy(value=WebAssemblyHostFunction.class)
            private static final class ExecuteData
            extends Node {
                @Node.Child
                ToWebAssemblyValueNode toWebAssemblyValueNode_;
                @Node.Child
                ToJSValueNode toJSValueNode_;
                @Node.Child
                JSFunctionCallNode callNode_;
                @CompilerDirectives.CompilationFinal
                BranchProfile errorBranch_;
                @Node.Child
                GetIteratorBaseNode getIteratorNode_;
                @Node.Child
                IterableToListNode iterableToListNode_;

                ExecuteData() {
                }

                @Override
                public NodeCost getCost() {
                    return NodeCost.NONE;
                }

                <T extends Node> T insertAccessor(T node) {
                    return super.insert(node);
                }
            }
        }
    }
}

