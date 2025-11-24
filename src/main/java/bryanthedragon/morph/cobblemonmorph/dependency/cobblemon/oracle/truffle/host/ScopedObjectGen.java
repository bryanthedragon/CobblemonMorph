/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.library.Message;
import com.oracle.truffle.api.library.ReflectionLibrary;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.host.HostMethodScope;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=HostMethodScope.ScopedObject.class)
final class ScopedObjectGen {
    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);
    private static final LibraryFactory<ReflectionLibrary> REFLECTION_LIBRARY_ = LibraryFactory.resolve(ReflectionLibrary.class);

    private ScopedObjectGen() {
    }

    static {
        LibraryExport.register(HostMethodScope.ScopedObject.class, new ReflectionLibraryExports());
    }

    @GeneratedBy(value=HostMethodScope.ScopedObject.class)
    private static final class ReflectionLibraryExports
    extends LibraryExport<ReflectionLibrary> {
        private ReflectionLibraryExports() {
            super(ReflectionLibrary.class, HostMethodScope.ScopedObject.class, false, false, 0);
        }

        @Override
        protected ReflectionLibrary createUncached(Object receiver) {
            assert (receiver instanceof HostMethodScope.ScopedObject);
            Uncached uncached = new Uncached();
            return uncached;
        }

        @Override
        protected ReflectionLibrary createCached(Object receiver) {
            assert (receiver instanceof HostMethodScope.ScopedObject);
            return new Cached();
        }

        @GeneratedBy(value=HostMethodScope.ScopedObject.class)
        @DenyReplace
        private static final class Uncached
        extends ReflectionLibrary {
            protected Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof HostMethodScope.ScopedObject) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof HostMethodScope.ScopedObject;
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
            public Object send(Object arg0Value_, Message arg1Value, Object ... arg2Value) throws Exception {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostMethodScope.ScopedObject arg0Value = (HostMethodScope.ScopedObject)arg0Value_;
                return arg0Value.send(arg1Value, arg2Value, REFLECTION_LIBRARY_.getUncached(), BranchProfile.getUncached(), BranchProfile.getUncached());
            }
        }

        @GeneratedBy(value=HostMethodScope.ScopedObject.class)
        private static final class Cached
        extends ReflectionLibrary {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @Node.Child
            private SendData send_cache;

            protected Cached() {
            }

            @Override
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof HostMethodScope.ScopedObject) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof HostMethodScope.ScopedObject;
            }

            @Override
            public Object send(Object arg0Value_, Message arg1Value, Object ... arg2Value) throws Exception {
                SendData s0_;
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
                HostMethodScope.ScopedObject arg0Value = (HostMethodScope.ScopedObject)arg0Value_;
                int state_0 = this.state_0_;
                if (state_0 != 0 && (s0_ = this.send_cache) != null) {
                    return arg0Value.send(arg1Value, arg2Value, s0_.library_, s0_.seenError_, s0_.seenOther_);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(HostMethodScope.ScopedObject arg0Value, Message arg1Value, Object[] arg2Value) throws Exception {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    SendData s0_ = super.insert(new SendData());
                    s0_.library_ = s0_.insertAccessor(REFLECTION_LIBRARY_.createDispatched(5));
                    s0_.seenError_ = BranchProfile.create();
                    s0_.seenOther_ = BranchProfile.create();
                    VarHandle.storeStoreFence();
                    this.send_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.send(arg1Value, arg2Value, s0_.library_, s0_.seenError_, s0_.seenOther_);
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

            @GeneratedBy(value=HostMethodScope.ScopedObject.class)
            private static final class SendData
            extends Node {
                @Node.Child
                ReflectionLibrary library_;
                @CompilerDirectives.CompilationFinal
                BranchProfile seenError_;
                @CompilerDirectives.CompilationFinal
                BranchProfile seenOther_;

                SendData() {
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

