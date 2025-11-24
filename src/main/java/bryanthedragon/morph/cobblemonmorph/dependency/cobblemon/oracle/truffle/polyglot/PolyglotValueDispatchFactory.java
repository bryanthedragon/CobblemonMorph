/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import com.oracle.truffle.polyglot.PolyglotLanguageContextFactory;
import com.oracle.truffle.polyglot.PolyglotValueDispatch;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=PolyglotValueDispatch.class)
final class PolyglotValueDispatchFactory {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

    PolyglotValueDispatchFactory() {
    }

    @GeneratedBy(value=PolyglotValueDispatch.InteropValue.class)
    static final class InteropValueFactory {
        InteropValueFactory() {
        }

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetHashValuesIteratorNode.class)
        static final class GetHashValuesIteratorNodeGen
        extends PolyglotValueDispatch.InteropValue.GetHashValuesIteratorNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @CompilerDirectives.CompilationFinal
            private PolyglotLanguageContext.ToHostValueNode cached1_toHost_;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_unsupported_;

            private GetHashValuesIteratorNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.hashes_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.GetHashValuesIteratorNode.doCached(arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toHost_, s0_.unsupported_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_hashes__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.GetHashValuesIteratorNode.doCached(arg0Value, arg1Value, arg2Value, cached1_hashes__, this.cached1_toHost_, this.cached1_unsupported_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.hashes_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.hashes_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toHost_ = this.createToHost();
                            s0_.unsupported_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.GetHashValuesIteratorNode.doCached(arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toHost_, s0_.unsupported_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_hashes__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_hashes__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_toHost_ = this.createToHost();
                        this.cached1_unsupported_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.GetHashValuesIteratorNode.doCached(arg0Value, arg1Value, arg2Value, cached1_hashes__, this.cached1_toHost_, this.cached1_unsupported_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.GetHashValuesIteratorNode create(PolyglotValueDispatch.InteropValue interop) {
                return new GetHashValuesIteratorNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetHashValuesIteratorNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary hashes_;
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetHashKeysIteratorNode.class)
        static final class GetHashKeysIteratorNodeGen
        extends PolyglotValueDispatch.InteropValue.GetHashKeysIteratorNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @CompilerDirectives.CompilationFinal
            private PolyglotLanguageContext.ToHostValueNode cached1_toHost_;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_unsupported_;

            private GetHashKeysIteratorNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.hashes_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.GetHashKeysIteratorNode.doCached(arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toHost_, s0_.unsupported_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_hashes__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.GetHashKeysIteratorNode.doCached(arg0Value, arg1Value, arg2Value, cached1_hashes__, this.cached1_toHost_, this.cached1_unsupported_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.hashes_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.hashes_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toHost_ = this.createToHost();
                            s0_.unsupported_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.GetHashKeysIteratorNode.doCached(arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toHost_, s0_.unsupported_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_hashes__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_hashes__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_toHost_ = this.createToHost();
                        this.cached1_unsupported_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.GetHashKeysIteratorNode.doCached(arg0Value, arg1Value, arg2Value, cached1_hashes__, this.cached1_toHost_, this.cached1_unsupported_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.GetHashKeysIteratorNode create(PolyglotValueDispatch.InteropValue interop) {
                return new GetHashKeysIteratorNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetHashKeysIteratorNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary hashes_;
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetHashEntriesIteratorNode.class)
        static final class GetHashEntriesIteratorNodeGen
        extends PolyglotValueDispatch.InteropValue.GetHashEntriesIteratorNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @CompilerDirectives.CompilationFinal
            private PolyglotLanguageContext.ToHostValueNode cached1_toHost_;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_unsupported_;

            private GetHashEntriesIteratorNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.hashes_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.GetHashEntriesIteratorNode.doCached(arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toHost_, s0_.unsupported_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_hashes__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.GetHashEntriesIteratorNode.doCached(arg0Value, arg1Value, arg2Value, cached1_hashes__, this.cached1_toHost_, this.cached1_unsupported_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.hashes_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.hashes_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toHost_ = this.createToHost();
                            s0_.unsupported_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.GetHashEntriesIteratorNode.doCached(arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toHost_, s0_.unsupported_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_hashes__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_hashes__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_toHost_ = this.createToHost();
                        this.cached1_unsupported_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.GetHashEntriesIteratorNode.doCached(arg0Value, arg1Value, arg2Value, cached1_hashes__, this.cached1_toHost_, this.cached1_unsupported_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.GetHashEntriesIteratorNode create(PolyglotValueDispatch.InteropValue interop) {
                return new GetHashEntriesIteratorNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetHashEntriesIteratorNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary hashes_;
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.RemoveHashEntryNode.class)
        static final class RemoveHashEntryNodeGen
        extends PolyglotValueDispatch.InteropValue.RemoveHashEntryNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private Cached1Data cached1_cache;

            private RemoveHashEntryNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    Cached1Data s1_;
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.hashes_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.RemoveHashEntryNode.doCached(arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toGuestKey_, s0_.unsupported_, s0_.invalidKey_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0 && (s1_ = this.cached1_cache) != null) {
                        return this.cached1Boundary(state_0, s1_, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, Cached1Data s1_, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary hashes__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.RemoveHashEntryNode.doCached(arg0Value, arg1Value, arg2Value, hashes__, s1_.toGuestKey_, s1_.unsupported_, s1_.invalidKey_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.hashes_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.hashes_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toGuestKey_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                            s0_.unsupported_ = BranchProfile.create();
                            s0_.invalidKey_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.RemoveHashEntryNode.doCached(arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toGuestKey_, s0_.unsupported_, s0_.invalidKey_);
                            return object2;
                        }
                    }
                    InteropLibrary hashes__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        Cached1Data s1_ = super.insert(new Cached1Data());
                        hashes__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        s1_.toGuestKey_ = s1_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                        s1_.unsupported_ = BranchProfile.create();
                        s1_.invalidKey_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.cached1_cache = s1_;
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.RemoveHashEntryNode.doCached(arg0Value, arg1Value, arg2Value, hashes__, s1_.toGuestKey_, s1_.unsupported_, s1_.invalidKey_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.RemoveHashEntryNode create(PolyglotValueDispatch.InteropValue interop) {
                return new RemoveHashEntryNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.RemoveHashEntryNode.class)
            private static final class Cached1Data
            extends Node {
                @Node.Child
                PolyglotLanguageContext.ToGuestValueNode toGuestKey_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidKey_;

                Cached1Data() {
                }

                @Override
                public NodeCost getCost() {
                    return NodeCost.NONE;
                }

                <T extends Node> T insertAccessor(T node) {
                    return super.insert(node);
                }
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.RemoveHashEntryNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary hashes_;
                @Node.Child
                PolyglotLanguageContext.ToGuestValueNode toGuestKey_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidKey_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.PutHashEntryNode.class)
        static final class PutHashEntryNodeGen
        extends PolyglotValueDispatch.InteropValue.PutHashEntryNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private Cached1Data cached1_cache;

            private PutHashEntryNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    Cached1Data s1_;
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.hashes_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.PutHashEntryNode.doCached(arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toGuestKey_, s0_.toGuestValue_, s0_.unsupported_, s0_.invalidKey_, s0_.invalidValue_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0 && (s1_ = this.cached1_cache) != null) {
                        return this.cached1Boundary(state_0, s1_, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, Cached1Data s1_, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary hashes__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.PutHashEntryNode.doCached(arg0Value, arg1Value, arg2Value, hashes__, s1_.toGuestKey_, s1_.toGuestValue_, s1_.unsupported_, s1_.invalidKey_, s1_.invalidValue_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.hashes_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.hashes_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toGuestKey_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                            s0_.toGuestValue_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                            s0_.unsupported_ = BranchProfile.create();
                            s0_.invalidKey_ = BranchProfile.create();
                            s0_.invalidValue_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.PutHashEntryNode.doCached(arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toGuestKey_, s0_.toGuestValue_, s0_.unsupported_, s0_.invalidKey_, s0_.invalidValue_);
                            return object2;
                        }
                    }
                    InteropLibrary hashes__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        Cached1Data s1_ = super.insert(new Cached1Data());
                        hashes__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        s1_.toGuestKey_ = s1_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                        s1_.toGuestValue_ = s1_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                        s1_.unsupported_ = BranchProfile.create();
                        s1_.invalidKey_ = BranchProfile.create();
                        s1_.invalidValue_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.cached1_cache = s1_;
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.PutHashEntryNode.doCached(arg0Value, arg1Value, arg2Value, hashes__, s1_.toGuestKey_, s1_.toGuestValue_, s1_.unsupported_, s1_.invalidKey_, s1_.invalidValue_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.PutHashEntryNode create(PolyglotValueDispatch.InteropValue interop) {
                return new PutHashEntryNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.PutHashEntryNode.class)
            private static final class Cached1Data
            extends Node {
                @Node.Child
                PolyglotLanguageContext.ToGuestValueNode toGuestKey_;
                @Node.Child
                PolyglotLanguageContext.ToGuestValueNode toGuestValue_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidKey_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidValue_;

                Cached1Data() {
                }

                @Override
                public NodeCost getCost() {
                    return NodeCost.NONE;
                }

                <T extends Node> T insertAccessor(T node) {
                    return super.insert(node);
                }
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.PutHashEntryNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary hashes_;
                @Node.Child
                PolyglotLanguageContext.ToGuestValueNode toGuestKey_;
                @Node.Child
                PolyglotLanguageContext.ToGuestValueNode toGuestValue_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidKey_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidValue_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetHashValueOrDefaultNode.class)
        static final class GetHashValueOrDefaultNodeGen
        extends PolyglotValueDispatch.InteropValue.GetHashValueOrDefaultNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private Cached1Data cached1_cache;

            private GetHashValueOrDefaultNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    Cached1Data s1_;
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.hashes_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.GetHashValueOrDefaultNode.doCached(arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toGuestKey_, s0_.toGuestDefaultValue_, s0_.toHost_, s0_.unsupported_, s0_.invalidKey_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0 && (s1_ = this.cached1_cache) != null) {
                        return this.cached1Boundary(state_0, s1_, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, Cached1Data s1_, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary hashes__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.GetHashValueOrDefaultNode.doCached(arg0Value, arg1Value, arg2Value, hashes__, s1_.toGuestKey_, s1_.toGuestDefaultValue_, s1_.toHost_, s1_.unsupported_, s1_.invalidKey_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.hashes_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.hashes_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toGuestKey_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                            s0_.toGuestDefaultValue_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                            s0_.toHost_ = this.createToHost();
                            s0_.unsupported_ = BranchProfile.create();
                            s0_.invalidKey_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.GetHashValueOrDefaultNode.doCached(arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toGuestKey_, s0_.toGuestDefaultValue_, s0_.toHost_, s0_.unsupported_, s0_.invalidKey_);
                            return object2;
                        }
                    }
                    InteropLibrary hashes__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        Cached1Data s1_ = super.insert(new Cached1Data());
                        hashes__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        s1_.toGuestKey_ = s1_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                        s1_.toGuestDefaultValue_ = s1_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                        s1_.toHost_ = this.createToHost();
                        s1_.unsupported_ = BranchProfile.create();
                        s1_.invalidKey_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.cached1_cache = s1_;
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.GetHashValueOrDefaultNode.doCached(arg0Value, arg1Value, arg2Value, hashes__, s1_.toGuestKey_, s1_.toGuestDefaultValue_, s1_.toHost_, s1_.unsupported_, s1_.invalidKey_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.GetHashValueOrDefaultNode create(PolyglotValueDispatch.InteropValue interop) {
                return new GetHashValueOrDefaultNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetHashValueOrDefaultNode.class)
            private static final class Cached1Data
            extends Node {
                @Node.Child
                PolyglotLanguageContext.ToGuestValueNode toGuestKey_;
                @Node.Child
                PolyglotLanguageContext.ToGuestValueNode toGuestDefaultValue_;
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidKey_;

                Cached1Data() {
                }

                @Override
                public NodeCost getCost() {
                    return NodeCost.NONE;
                }

                <T extends Node> T insertAccessor(T node) {
                    return super.insert(node);
                }
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetHashValueOrDefaultNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary hashes_;
                @Node.Child
                PolyglotLanguageContext.ToGuestValueNode toGuestKey_;
                @Node.Child
                PolyglotLanguageContext.ToGuestValueNode toGuestDefaultValue_;
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidKey_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetHashValueNode.class)
        static final class GetHashValueNodeGen
        extends PolyglotValueDispatch.InteropValue.GetHashValueNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private Cached1Data cached1_cache;

            private GetHashValueNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    Cached1Data s1_;
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.hashes_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.GetHashValueNode.doCached(arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toGuestKey_, s0_.toHost_, s0_.unsupported_, s0_.invalidKey_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0 && (s1_ = this.cached1_cache) != null) {
                        return this.cached1Boundary(state_0, s1_, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, Cached1Data s1_, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary hashes__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.GetHashValueNode.doCached(arg0Value, arg1Value, arg2Value, hashes__, s1_.toGuestKey_, s1_.toHost_, s1_.unsupported_, s1_.invalidKey_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.hashes_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.hashes_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toGuestKey_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                            s0_.toHost_ = this.createToHost();
                            s0_.unsupported_ = BranchProfile.create();
                            s0_.invalidKey_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.GetHashValueNode.doCached(arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toGuestKey_, s0_.toHost_, s0_.unsupported_, s0_.invalidKey_);
                            return object2;
                        }
                    }
                    InteropLibrary hashes__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        Cached1Data s1_ = super.insert(new Cached1Data());
                        hashes__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        s1_.toGuestKey_ = s1_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                        s1_.toHost_ = this.createToHost();
                        s1_.unsupported_ = BranchProfile.create();
                        s1_.invalidKey_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.cached1_cache = s1_;
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.GetHashValueNode.doCached(arg0Value, arg1Value, arg2Value, hashes__, s1_.toGuestKey_, s1_.toHost_, s1_.unsupported_, s1_.invalidKey_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.GetHashValueNode create(PolyglotValueDispatch.InteropValue interop) {
                return new GetHashValueNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetHashValueNode.class)
            private static final class Cached1Data
            extends Node {
                @Node.Child
                PolyglotLanguageContext.ToGuestValueNode toGuestKey_;
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidKey_;

                Cached1Data() {
                }

                @Override
                public NodeCost getCost() {
                    return NodeCost.NONE;
                }

                <T extends Node> T insertAccessor(T node) {
                    return super.insert(node);
                }
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetHashValueNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary hashes_;
                @Node.Child
                PolyglotLanguageContext.ToGuestValueNode toGuestKey_;
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidKey_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.HasHashEntryNode.class)
        static final class HasHashEntryNodeGen
        extends PolyglotValueDispatch.InteropValue.HasHashEntryNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private PolyglotLanguageContext.ToGuestValueNode cached1_toGuestKey_;

            private HasHashEntryNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.hashes_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.HasHashEntryNode.doCached(arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toGuestKey_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_hashes__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.HasHashEntryNode.doCached(arg0Value, arg1Value, arg2Value, cached1_hashes__, this.cached1_toGuestKey_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.hashes_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.hashes_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toGuestKey_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.HasHashEntryNode.doCached(arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toGuestKey_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_hashes__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_hashes__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_toGuestKey_ = super.insert(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.HasHashEntryNode.doCached(arg0Value, arg1Value, arg2Value, cached1_hashes__, this.cached1_toGuestKey_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.HasHashEntryNode create(PolyglotValueDispatch.InteropValue interop) {
                return new HasHashEntryNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.HasHashEntryNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary hashes_;
                @Node.Child
                PolyglotLanguageContext.ToGuestValueNode toGuestKey_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetHashSizeNode.class)
        static final class GetHashSizeNodeGen
        extends PolyglotValueDispatch.InteropValue.GetHashSizeNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_unsupported_;

            private GetHashSizeNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.hashes_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.GetHashSizeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.unsupported_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_hashes__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.GetHashSizeNode.doCached(arg0Value, arg1Value, arg2Value, cached1_hashes__, this.cached1_unsupported_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.hashes_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.hashes_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.unsupported_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.GetHashSizeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.unsupported_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_hashes__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_hashes__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_unsupported_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.GetHashSizeNode.doCached(arg0Value, arg1Value, arg2Value, cached1_hashes__, this.cached1_unsupported_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.GetHashSizeNode create(PolyglotValueDispatch.InteropValue interop) {
                return new GetHashSizeNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetHashSizeNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary hashes_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.HasHashEntriesNode.class)
        static final class HasHashEntriesNodeGen
        extends PolyglotValueDispatch.InteropValue.HasHashEntriesNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;

            private HasHashEntriesNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.hashes_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.HasHashEntriesNode.doCached(arg0Value, arg1Value, arg2Value, s0_.hashes_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_hashes__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.HasHashEntriesNode.doCached(arg0Value, arg1Value, arg2Value, cached1_hashes__);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.hashes_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.hashes_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.HasHashEntriesNode.doCached(arg0Value, arg1Value, arg2Value, s0_.hashes_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_hashes__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_hashes__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.HasHashEntriesNode.doCached(arg0Value, arg1Value, arg2Value, cached1_hashes__);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.HasHashEntriesNode create(PolyglotValueDispatch.InteropValue interop) {
                return new HasHashEntriesNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.HasHashEntriesNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary hashes_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetIteratorNextElementNode.class)
        static final class GetIteratorNextElementNodeGen
        extends PolyglotValueDispatch.InteropValue.GetIteratorNextElementNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private Cached1Data cached1_cache;

            private GetIteratorNextElementNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    Cached1Data s1_;
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.iterators_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.GetIteratorNextElementNode.doCached(arg0Value, arg1Value, arg2Value, s0_.iterators_, s0_.toHost_, s0_.unsupported_, s0_.stop_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0 && (s1_ = this.cached1_cache) != null) {
                        return this.cached1Boundary(state_0, s1_, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, Cached1Data s1_, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary iterators__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.GetIteratorNextElementNode.doCached(arg0Value, arg1Value, arg2Value, iterators__, s1_.toHost_, s1_.unsupported_, s1_.stop_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.iterators_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.iterators_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toHost_ = this.createToHost();
                            s0_.unsupported_ = BranchProfile.create();
                            s0_.stop_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.GetIteratorNextElementNode.doCached(arg0Value, arg1Value, arg2Value, s0_.iterators_, s0_.toHost_, s0_.unsupported_, s0_.stop_);
                            return object2;
                        }
                    }
                    InteropLibrary iterators__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        Cached1Data s1_ = super.insert(new Cached1Data());
                        iterators__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        s1_.toHost_ = this.createToHost();
                        s1_.unsupported_ = BranchProfile.create();
                        s1_.stop_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.cached1_cache = s1_;
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.GetIteratorNextElementNode.doCached(arg0Value, arg1Value, arg2Value, iterators__, s1_.toHost_, s1_.unsupported_, s1_.stop_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.GetIteratorNextElementNode create(PolyglotValueDispatch.InteropValue interop) {
                return new GetIteratorNextElementNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetIteratorNextElementNode.class)
            private static final class Cached1Data
            extends Node {
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile stop_;

                Cached1Data() {
                }

                @Override
                public NodeCost getCost() {
                    return NodeCost.NONE;
                }
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetIteratorNextElementNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary iterators_;
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile stop_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.HasIteratorNextElementNode.class)
        static final class HasIteratorNextElementNodeGen
        extends PolyglotValueDispatch.InteropValue.HasIteratorNextElementNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_unsupported_;

            private HasIteratorNextElementNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.iterators_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.HasIteratorNextElementNode.doCached(arg0Value, arg1Value, arg2Value, s0_.iterators_, s0_.unsupported_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_iterators__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.HasIteratorNextElementNode.doCached(arg0Value, arg1Value, arg2Value, cached1_iterators__, this.cached1_unsupported_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.iterators_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.iterators_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.unsupported_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.HasIteratorNextElementNode.doCached(arg0Value, arg1Value, arg2Value, s0_.iterators_, s0_.unsupported_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_iterators__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_iterators__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_unsupported_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.HasIteratorNextElementNode.doCached(arg0Value, arg1Value, arg2Value, cached1_iterators__, this.cached1_unsupported_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.HasIteratorNextElementNode create(PolyglotValueDispatch.InteropValue interop) {
                return new HasIteratorNextElementNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.HasIteratorNextElementNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary iterators_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.IsIteratorNode.class)
        static final class IsIteratorNodeGen
        extends PolyglotValueDispatch.InteropValue.IsIteratorNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;

            private IsIteratorNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.iterators_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.IsIteratorNode.doCached(arg0Value, arg1Value, arg2Value, s0_.iterators_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_iterators__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.IsIteratorNode.doCached(arg0Value, arg1Value, arg2Value, cached1_iterators__);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.iterators_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.iterators_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.IsIteratorNode.doCached(arg0Value, arg1Value, arg2Value, s0_.iterators_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_iterators__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_iterators__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.IsIteratorNode.doCached(arg0Value, arg1Value, arg2Value, cached1_iterators__);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.IsIteratorNode create(PolyglotValueDispatch.InteropValue interop) {
                return new IsIteratorNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.IsIteratorNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary iterators_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetIteratorNode.class)
        static final class GetIteratorNodeGen
        extends PolyglotValueDispatch.InteropValue.GetIteratorNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @CompilerDirectives.CompilationFinal
            private PolyglotLanguageContext.ToHostValueNode cached1_toHost_;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_unsupported_;

            private GetIteratorNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.iterators_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.GetIteratorNode.doCached(arg0Value, arg1Value, arg2Value, s0_.iterators_, s0_.toHost_, s0_.unsupported_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_iterators__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.GetIteratorNode.doCached(arg0Value, arg1Value, arg2Value, cached1_iterators__, this.cached1_toHost_, this.cached1_unsupported_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.iterators_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.iterators_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toHost_ = this.createToHost();
                            s0_.unsupported_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.GetIteratorNode.doCached(arg0Value, arg1Value, arg2Value, s0_.iterators_, s0_.toHost_, s0_.unsupported_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_iterators__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_iterators__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_toHost_ = this.createToHost();
                        this.cached1_unsupported_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.GetIteratorNode.doCached(arg0Value, arg1Value, arg2Value, cached1_iterators__, this.cached1_toHost_, this.cached1_unsupported_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.GetIteratorNode create(PolyglotValueDispatch.InteropValue interop) {
                return new GetIteratorNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetIteratorNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary iterators_;
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.HasIteratorNode.class)
        static final class HasIteratorNodeGen
        extends PolyglotValueDispatch.InteropValue.HasIteratorNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;

            private HasIteratorNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.iterators_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.HasIteratorNode.doCached(arg0Value, arg1Value, arg2Value, s0_.iterators_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_iterators__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.HasIteratorNode.doCached(arg0Value, arg1Value, arg2Value, cached1_iterators__);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.iterators_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.iterators_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.HasIteratorNode.doCached(arg0Value, arg1Value, arg2Value, s0_.iterators_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_iterators__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_iterators__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.HasIteratorNode.doCached(arg0Value, arg1Value, arg2Value, cached1_iterators__);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.HasIteratorNode create(PolyglotValueDispatch.InteropValue interop) {
                return new HasIteratorNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.HasIteratorNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary iterators_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetMetaParentsNode.class)
        static final class GetMetaParentsNodeGen
        extends PolyglotValueDispatch.InteropValue.GetMetaParentsNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @CompilerDirectives.CompilationFinal
            private PolyglotLanguageContext.ToHostValueNode cached1_toHost_;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_unsupported_;

            private GetMetaParentsNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.objects_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.GetMetaParentsNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.toHost_, s0_.unsupported_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.GetMetaParentsNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_toHost_, this.cached1_unsupported_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.objects_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toHost_ = this.createToHost();
                            s0_.unsupported_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.GetMetaParentsNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.toHost_, s0_.unsupported_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_objects__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_toHost_ = this.createToHost();
                        this.cached1_unsupported_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.GetMetaParentsNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_toHost_, this.cached1_unsupported_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.GetMetaParentsNode create(PolyglotValueDispatch.InteropValue interop) {
                return new GetMetaParentsNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetMetaParentsNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary objects_;
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.HasMetaParentsNode.class)
        static final class HasMetaParentsNodeGen
        extends PolyglotValueDispatch.InteropValue.HasMetaParentsNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_unsupported_;

            private HasMetaParentsNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.objects_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.HasMetaParentsNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Boolean bl = PolyglotValueDispatch.InteropValue.HasMetaParentsNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_);
                    return bl;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    boolean bl;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.objects_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.unsupported_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            boolean bl2 = PolyglotValueDispatch.InteropValue.HasMetaParentsNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_);
                            return bl2;
                        }
                    }
                    InteropLibrary cached1_objects__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_unsupported_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        bl = PolyglotValueDispatch.InteropValue.HasMetaParentsNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public NodeCost getCost() {
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.HasMetaParentsNode create(PolyglotValueDispatch.InteropValue interop) {
                return new HasMetaParentsNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.HasMetaParentsNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary objects_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.IsMetaInstanceNode.class)
        static final class IsMetaInstanceNodeGen
        extends PolyglotValueDispatch.InteropValue.IsMetaInstanceNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private PolyglotLanguageContext.ToGuestValueNode cached1_toGuest_;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_unsupported_;

            private IsMetaInstanceNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.objects_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.IsMetaInstanceNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.toGuest_, s0_.unsupported_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Boolean bl = PolyglotValueDispatch.InteropValue.IsMetaInstanceNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_toGuest_, this.cached1_unsupported_);
                    return bl;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    boolean bl;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.objects_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toGuest_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                            s0_.unsupported_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            boolean bl2 = PolyglotValueDispatch.InteropValue.IsMetaInstanceNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.toGuest_, s0_.unsupported_);
                            return bl2;
                        }
                    }
                    InteropLibrary cached1_objects__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_toGuest_ = super.insert(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                        this.cached1_unsupported_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        bl = PolyglotValueDispatch.InteropValue.IsMetaInstanceNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_toGuest_, this.cached1_unsupported_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public NodeCost getCost() {
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.IsMetaInstanceNode create(PolyglotValueDispatch.InteropValue interop) {
                return new IsMetaInstanceNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.IsMetaInstanceNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary objects_;
                @Node.Child
                PolyglotLanguageContext.ToGuestValueNode toGuest_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetMetaSimpleNameNode.class)
        static final class GetMetaSimpleNameNodeGen
        extends PolyglotValueDispatch.InteropValue.GetMetaSimpleNameNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private InteropLibrary cached1_toString_;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_unsupported_;

            private GetMetaSimpleNameNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.objects_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.GetMetaSimpleNameNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.toString_, s0_.unsupported_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    String string = PolyglotValueDispatch.InteropValue.GetMetaSimpleNameNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_toString_, this.cached1_unsupported_);
                    return string;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private String executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    String string;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.objects_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toString_ = s0_.insertAccessor(INTEROP_LIBRARY_.createDispatched(1));
                            s0_.unsupported_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            String string2 = PolyglotValueDispatch.InteropValue.GetMetaSimpleNameNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.toString_, s0_.unsupported_);
                            return string2;
                        }
                    }
                    InteropLibrary cached1_objects__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_toString_ = super.insert(INTEROP_LIBRARY_.createDispatched(1));
                        this.cached1_unsupported_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        string = PolyglotValueDispatch.InteropValue.GetMetaSimpleNameNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_toString_, this.cached1_unsupported_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
                    return string;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public NodeCost getCost() {
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.GetMetaSimpleNameNode create(PolyglotValueDispatch.InteropValue interop) {
                return new GetMetaSimpleNameNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetMetaSimpleNameNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary objects_;
                @Node.Child
                InteropLibrary toString_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetMetaQualifiedNameNode.class)
        static final class GetMetaQualifiedNameNodeGen
        extends PolyglotValueDispatch.InteropValue.GetMetaQualifiedNameNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private InteropLibrary cached1_toString_;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_unsupported_;

            private GetMetaQualifiedNameNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.objects_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.GetMetaQualifiedNameNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.toString_, s0_.unsupported_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    String string = PolyglotValueDispatch.InteropValue.GetMetaQualifiedNameNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_toString_, this.cached1_unsupported_);
                    return string;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private String executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    String string;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.objects_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toString_ = s0_.insertAccessor(INTEROP_LIBRARY_.createDispatched(1));
                            s0_.unsupported_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            String string2 = PolyglotValueDispatch.InteropValue.GetMetaQualifiedNameNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.toString_, s0_.unsupported_);
                            return string2;
                        }
                    }
                    InteropLibrary cached1_objects__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_toString_ = super.insert(INTEROP_LIBRARY_.createDispatched(1));
                        this.cached1_unsupported_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        string = PolyglotValueDispatch.InteropValue.GetMetaQualifiedNameNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_toString_, this.cached1_unsupported_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
                    return string;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public NodeCost getCost() {
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.GetMetaQualifiedNameNode create(PolyglotValueDispatch.InteropValue interop) {
                return new GetMetaQualifiedNameNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetMetaQualifiedNameNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary objects_;
                @Node.Child
                InteropLibrary toString_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.IsMetaObjectNode.class)
        static final class IsMetaObjectNodeGen
        extends PolyglotValueDispatch.InteropValue.IsMetaObjectNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;

            private IsMetaObjectNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.objects_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.IsMetaObjectNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Boolean bl = PolyglotValueDispatch.InteropValue.IsMetaObjectNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
                    return bl;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    boolean bl;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.objects_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            boolean bl2 = PolyglotValueDispatch.InteropValue.IsMetaObjectNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
                            return bl2;
                        }
                    }
                    InteropLibrary cached1_objects__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        bl = PolyglotValueDispatch.InteropValue.IsMetaObjectNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public NodeCost getCost() {
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.IsMetaObjectNode create(PolyglotValueDispatch.InteropValue interop) {
                return new IsMetaObjectNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.IsMetaObjectNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary objects_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.ThrowExceptionNode.class)
        static final class ThrowExceptionNodeGen
        extends PolyglotValueDispatch.InteropValue.ThrowExceptionNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_unsupported_;

            private ThrowExceptionNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.objects_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.ThrowExceptionNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.ThrowExceptionNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.objects_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.unsupported_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.ThrowExceptionNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_objects__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_unsupported_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.ThrowExceptionNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.ThrowExceptionNode create(PolyglotValueDispatch.InteropValue interop) {
                return new ThrowExceptionNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.ThrowExceptionNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary objects_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.IsExceptionNode.class)
        static final class IsExceptionNodeGen
        extends PolyglotValueDispatch.InteropValue.IsExceptionNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;

            private IsExceptionNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.objects_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.IsExceptionNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.IsExceptionNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.objects_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.IsExceptionNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_objects__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.IsExceptionNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.IsExceptionNode create(PolyglotValueDispatch.InteropValue interop) {
                return new IsExceptionNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.IsExceptionNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary objects_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.NewInstanceNode.class)
        static final class NewInstanceNodeGen
        extends PolyglotValueDispatch.InteropValue.NewInstanceNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private Cached1Data cached1_cache;

            private NewInstanceNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    Cached1Data s1_;
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.instantiables_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.NewInstanceNode.doCached(arg0Value, arg1Value, arg2Value, s0_.instantiables_, s0_.toGuestValues_, s0_.toHostValue_, s0_.arity_, s0_.invalidArgument_, s0_.unsupported_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0 && (s1_ = this.cached1_cache) != null) {
                        return this.cached1Boundary(state_0, s1_, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, Cached1Data s1_, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary instantiables__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.NewInstanceNode.doCached(arg0Value, arg1Value, arg2Value, instantiables__, s1_.toGuestValues_, s1_.toHostValue_, s1_.arity_, s1_.invalidArgument_, s1_.unsupported_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.instantiables_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.instantiables_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toGuestValues_ = s0_.insertAccessor(PolyglotLanguageContext.ToGuestValuesNode.create());
                            s0_.toHostValue_ = this.createToHost();
                            s0_.arity_ = BranchProfile.create();
                            s0_.invalidArgument_ = BranchProfile.create();
                            s0_.unsupported_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.NewInstanceNode.doCached(arg0Value, arg1Value, arg2Value, s0_.instantiables_, s0_.toGuestValues_, s0_.toHostValue_, s0_.arity_, s0_.invalidArgument_, s0_.unsupported_);
                            return object2;
                        }
                    }
                    InteropLibrary instantiables__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        Cached1Data s1_ = super.insert(new Cached1Data());
                        instantiables__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        s1_.toGuestValues_ = s1_.insertAccessor(PolyglotLanguageContext.ToGuestValuesNode.create());
                        s1_.toHostValue_ = this.createToHost();
                        s1_.arity_ = BranchProfile.create();
                        s1_.invalidArgument_ = BranchProfile.create();
                        s1_.unsupported_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.cached1_cache = s1_;
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.NewInstanceNode.doCached(arg0Value, arg1Value, arg2Value, instantiables__, s1_.toGuestValues_, s1_.toHostValue_, s1_.arity_, s1_.invalidArgument_, s1_.unsupported_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.NewInstanceNode create(PolyglotValueDispatch.InteropValue interop) {
                return new NewInstanceNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.NewInstanceNode.class)
            private static final class Cached1Data
            extends Node {
                @Node.Child
                PolyglotLanguageContext.ToGuestValuesNode toGuestValues_;
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHostValue_;
                @CompilerDirectives.CompilationFinal
                BranchProfile arity_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidArgument_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;

                Cached1Data() {
                }

                @Override
                public NodeCost getCost() {
                    return NodeCost.NONE;
                }

                <T extends Node> T insertAccessor(T node) {
                    return super.insert(node);
                }
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.NewInstanceNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary instantiables_;
                @Node.Child
                PolyglotLanguageContext.ToGuestValuesNode toGuestValues_;
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHostValue_;
                @CompilerDirectives.CompilationFinal
                BranchProfile arity_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidArgument_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.CanInstantiateNode.class)
        static final class CanInstantiateNodeGen
        extends PolyglotValueDispatch.InteropValue.CanInstantiateNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;

            private CanInstantiateNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.instantiables_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.CanInstantiateNode.doCached(arg0Value, arg1Value, arg2Value, s0_.instantiables_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_instantiables__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.CanInstantiateNode.doCached(arg0Value, arg1Value, arg2Value, cached1_instantiables__);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.instantiables_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.instantiables_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.CanInstantiateNode.doCached(arg0Value, arg1Value, arg2Value, s0_.instantiables_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_instantiables__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_instantiables__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.CanInstantiateNode.doCached(arg0Value, arg1Value, arg2Value, cached1_instantiables__);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.CanInstantiateNode create(PolyglotValueDispatch.InteropValue interop) {
                return new CanInstantiateNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.CanInstantiateNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary instantiables_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.CanExecuteNode.class)
        static final class CanExecuteNodeGen
        extends PolyglotValueDispatch.InteropValue.CanExecuteNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;

            private CanExecuteNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.executables_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.CanExecuteNode.doCached(arg0Value, arg1Value, arg2Value, s0_.executables_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_executables__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.CanExecuteNode.doCached(arg0Value, arg1Value, arg2Value, cached1_executables__);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.executables_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.executables_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.CanExecuteNode.doCached(arg0Value, arg1Value, arg2Value, s0_.executables_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_executables__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_executables__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.CanExecuteNode.doCached(arg0Value, arg1Value, arg2Value, cached1_executables__);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.CanExecuteNode create(PolyglotValueDispatch.InteropValue interop) {
                return new CanExecuteNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.CanExecuteNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary executables_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.CanInvokeNode.class)
        static final class CanInvokeNodeGen
        extends PolyglotValueDispatch.InteropValue.CanInvokeNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;

            private CanInvokeNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.objects_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.CanInvokeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.CanInvokeNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.objects_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.CanInvokeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_objects__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.CanInvokeNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.CanInvokeNode create(PolyglotValueDispatch.InteropValue interop) {
                return new CanInvokeNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.CanInvokeNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary objects_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.HasMemberNode.class)
        static final class HasMemberNodeGen
        extends PolyglotValueDispatch.InteropValue.HasMemberNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;

            private HasMemberNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.objects_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.HasMemberNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.HasMemberNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.objects_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.HasMemberNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_objects__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.HasMemberNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.HasMemberNode create(PolyglotValueDispatch.InteropValue interop) {
                return new HasMemberNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.HasMemberNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary objects_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.HasMembersNode.class)
        static final class HasMembersNodeGen
        extends PolyglotValueDispatch.InteropValue.HasMembersNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;

            private HasMembersNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.objects_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.HasMembersNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.HasMembersNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.objects_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.HasMembersNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_objects__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.HasMembersNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.HasMembersNode create(PolyglotValueDispatch.InteropValue interop) {
                return new HasMembersNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.HasMembersNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary objects_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.IsNullNode.class)
        static final class IsNullNodeGen
        extends PolyglotValueDispatch.InteropValue.IsNullNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;

            private IsNullNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.values_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.IsNullNode.doCached(arg0Value, arg1Value, arg2Value, s0_.values_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_values__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.IsNullNode.doCached(arg0Value, arg1Value, arg2Value, cached1_values__);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.values_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.values_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.IsNullNode.doCached(arg0Value, arg1Value, arg2Value, s0_.values_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_values__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_values__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.IsNullNode.doCached(arg0Value, arg1Value, arg2Value, cached1_values__);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.IsNullNode create(PolyglotValueDispatch.InteropValue interop) {
                return new IsNullNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.IsNullNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary values_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.RemoveMemberNode.class)
        static final class RemoveMemberNodeGen
        extends PolyglotValueDispatch.InteropValue.RemoveMemberNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_unsupported_;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_unknown_;

            private RemoveMemberNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.objects_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.RemoveMemberNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_, s0_.unknown_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.RemoveMemberNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_, this.cached1_unknown_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.objects_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.unsupported_ = BranchProfile.create();
                            s0_.unknown_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.RemoveMemberNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_, s0_.unknown_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_objects__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_unsupported_ = BranchProfile.create();
                        this.cached1_unknown_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.RemoveMemberNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_, this.cached1_unknown_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.RemoveMemberNode create(PolyglotValueDispatch.InteropValue interop) {
                return new RemoveMemberNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.RemoveMemberNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary objects_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unknown_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.PutMemberNode.class)
        static final class PutMemberNodeGen
        extends PolyglotValueDispatch.InteropValue.PutMemberNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @Node.Child
            private CachedData cached_cache;

            private PutMemberNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                CachedData s0_;
                int state_0 = this.state_0_;
                if (state_0 != 0 && (s0_ = this.cached_cache) != null) {
                    return PolyglotValueDispatch.InteropValue.PutMemberNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.toGuestValue_, s0_.unsupported_, s0_.invalidValue_, s0_.unknown_);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    CachedData s0_ = super.insert(new CachedData());
                    s0_.objects_ = s0_.insertAccessor(INTEROP_LIBRARY_.createDispatched(5));
                    s0_.toGuestValue_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                    s0_.unsupported_ = BranchProfile.create();
                    s0_.invalidValue_ = BranchProfile.create();
                    s0_.unknown_ = BranchProfile.create();
                    VarHandle.storeStoreFence();
                    this.cached_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = PolyglotValueDispatch.InteropValue.PutMemberNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.toGuestValue_, s0_.unsupported_, s0_.invalidValue_, s0_.unknown_);
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

            public static PolyglotValueDispatch.InteropValue.PutMemberNode create(PolyglotValueDispatch.InteropValue interop) {
                return new PutMemberNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.PutMemberNode.class)
            private static final class CachedData
            extends Node {
                @Node.Child
                InteropLibrary objects_;
                @Node.Child
                PolyglotLanguageContext.ToGuestValueNode toGuestValue_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidValue_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unknown_;

                CachedData() {
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetMemberNode.class)
        static final class GetMemberNodeGen
        extends PolyglotValueDispatch.InteropValue.GetMemberNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private Cached1Data cached1_cache;

            private GetMemberNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    Cached1Data s1_;
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.objects_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.GetMemberNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.toHost_, s0_.unsupported_, s0_.unknown_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0 && (s1_ = this.cached1_cache) != null) {
                        return this.cached1Boundary(state_0, s1_, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, Cached1Data s1_, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.GetMemberNode.doCached(arg0Value, arg1Value, arg2Value, objects__, s1_.toHost_, s1_.unsupported_, s1_.unknown_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.objects_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toHost_ = this.createToHost();
                            s0_.unsupported_ = BranchProfile.create();
                            s0_.unknown_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.GetMemberNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.toHost_, s0_.unsupported_, s0_.unknown_);
                            return object2;
                        }
                    }
                    InteropLibrary objects__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        Cached1Data s1_ = super.insert(new Cached1Data());
                        objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        s1_.toHost_ = this.createToHost();
                        s1_.unsupported_ = BranchProfile.create();
                        s1_.unknown_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.cached1_cache = s1_;
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.GetMemberNode.doCached(arg0Value, arg1Value, arg2Value, objects__, s1_.toHost_, s1_.unsupported_, s1_.unknown_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.GetMemberNode create(PolyglotValueDispatch.InteropValue interop) {
                return new GetMemberNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetMemberNode.class)
            private static final class Cached1Data
            extends Node {
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unknown_;

                Cached1Data() {
                }

                @Override
                public NodeCost getCost() {
                    return NodeCost.NONE;
                }
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetMemberNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary objects_;
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unknown_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.WriteBufferDoubleNode.class)
        static final class WriteBufferDoubleNodeGen
        extends PolyglotValueDispatch.InteropValue.WriteBufferDoubleNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private Cached1Data cached1_cache;

            private WriteBufferDoubleNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    Cached1Data s1_;
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.buffers_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.WriteBufferDoubleNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_, s0_.invalidIndex_, s0_.invalidValue_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0 && (s1_ = this.cached1_cache) != null) {
                        return this.cached1Boundary(state_0, s1_, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, Cached1Data s1_, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.WriteBufferDoubleNode.doCached(arg0Value, arg1Value, arg2Value, buffers__, s1_.unsupported_, s1_.invalidIndex_, s1_.invalidValue_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.buffers_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.unsupported_ = BranchProfile.create();
                            s0_.invalidIndex_ = BranchProfile.create();
                            s0_.invalidValue_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.WriteBufferDoubleNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_, s0_.invalidIndex_, s0_.invalidValue_);
                            return object2;
                        }
                    }
                    InteropLibrary buffers__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        Cached1Data s1_ = super.insert(new Cached1Data());
                        buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        s1_.unsupported_ = BranchProfile.create();
                        s1_.invalidIndex_ = BranchProfile.create();
                        s1_.invalidValue_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.cached1_cache = s1_;
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.WriteBufferDoubleNode.doCached(arg0Value, arg1Value, arg2Value, buffers__, s1_.unsupported_, s1_.invalidIndex_, s1_.invalidValue_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.WriteBufferDoubleNode create(PolyglotValueDispatch.InteropValue interop) {
                return new WriteBufferDoubleNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.WriteBufferDoubleNode.class)
            private static final class Cached1Data
            extends Node {
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidIndex_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidValue_;

                Cached1Data() {
                }

                @Override
                public NodeCost getCost() {
                    return NodeCost.NONE;
                }
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.WriteBufferDoubleNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary buffers_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidIndex_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidValue_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.ReadBufferDoubleNode.class)
        static final class ReadBufferDoubleNodeGen
        extends PolyglotValueDispatch.InteropValue.ReadBufferDoubleNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private Cached1Data cached1_cache;

            private ReadBufferDoubleNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    Cached1Data s1_;
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.buffers_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.ReadBufferDoubleNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.toHost_, s0_.unsupported_, s0_.unknown_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0 && (s1_ = this.cached1_cache) != null) {
                        return this.cached1Boundary(state_0, s1_, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, Cached1Data s1_, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.ReadBufferDoubleNode.doCached(arg0Value, arg1Value, arg2Value, buffers__, s1_.toHost_, s1_.unsupported_, s1_.unknown_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.buffers_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toHost_ = this.createToHost();
                            s0_.unsupported_ = BranchProfile.create();
                            s0_.unknown_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.ReadBufferDoubleNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.toHost_, s0_.unsupported_, s0_.unknown_);
                            return object2;
                        }
                    }
                    InteropLibrary buffers__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        Cached1Data s1_ = super.insert(new Cached1Data());
                        buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        s1_.toHost_ = this.createToHost();
                        s1_.unsupported_ = BranchProfile.create();
                        s1_.unknown_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.cached1_cache = s1_;
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.ReadBufferDoubleNode.doCached(arg0Value, arg1Value, arg2Value, buffers__, s1_.toHost_, s1_.unsupported_, s1_.unknown_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.ReadBufferDoubleNode create(PolyglotValueDispatch.InteropValue interop) {
                return new ReadBufferDoubleNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.ReadBufferDoubleNode.class)
            private static final class Cached1Data
            extends Node {
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unknown_;

                Cached1Data() {
                }

                @Override
                public NodeCost getCost() {
                    return NodeCost.NONE;
                }
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.ReadBufferDoubleNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary buffers_;
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unknown_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.WriteBufferFloatNode.class)
        static final class WriteBufferFloatNodeGen
        extends PolyglotValueDispatch.InteropValue.WriteBufferFloatNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private Cached1Data cached1_cache;

            private WriteBufferFloatNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    Cached1Data s1_;
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.buffers_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.WriteBufferFloatNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_, s0_.invalidIndex_, s0_.invalidValue_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0 && (s1_ = this.cached1_cache) != null) {
                        return this.cached1Boundary(state_0, s1_, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, Cached1Data s1_, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.WriteBufferFloatNode.doCached(arg0Value, arg1Value, arg2Value, buffers__, s1_.unsupported_, s1_.invalidIndex_, s1_.invalidValue_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.buffers_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.unsupported_ = BranchProfile.create();
                            s0_.invalidIndex_ = BranchProfile.create();
                            s0_.invalidValue_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.WriteBufferFloatNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_, s0_.invalidIndex_, s0_.invalidValue_);
                            return object2;
                        }
                    }
                    InteropLibrary buffers__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        Cached1Data s1_ = super.insert(new Cached1Data());
                        buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        s1_.unsupported_ = BranchProfile.create();
                        s1_.invalidIndex_ = BranchProfile.create();
                        s1_.invalidValue_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.cached1_cache = s1_;
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.WriteBufferFloatNode.doCached(arg0Value, arg1Value, arg2Value, buffers__, s1_.unsupported_, s1_.invalidIndex_, s1_.invalidValue_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.WriteBufferFloatNode create(PolyglotValueDispatch.InteropValue interop) {
                return new WriteBufferFloatNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.WriteBufferFloatNode.class)
            private static final class Cached1Data
            extends Node {
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidIndex_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidValue_;

                Cached1Data() {
                }

                @Override
                public NodeCost getCost() {
                    return NodeCost.NONE;
                }
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.WriteBufferFloatNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary buffers_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidIndex_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidValue_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.ReadBufferFloatNode.class)
        static final class ReadBufferFloatNodeGen
        extends PolyglotValueDispatch.InteropValue.ReadBufferFloatNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private Cached1Data cached1_cache;

            private ReadBufferFloatNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    Cached1Data s1_;
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.buffers_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.ReadBufferFloatNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.toHost_, s0_.unsupported_, s0_.unknown_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0 && (s1_ = this.cached1_cache) != null) {
                        return this.cached1Boundary(state_0, s1_, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, Cached1Data s1_, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.ReadBufferFloatNode.doCached(arg0Value, arg1Value, arg2Value, buffers__, s1_.toHost_, s1_.unsupported_, s1_.unknown_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.buffers_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toHost_ = this.createToHost();
                            s0_.unsupported_ = BranchProfile.create();
                            s0_.unknown_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.ReadBufferFloatNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.toHost_, s0_.unsupported_, s0_.unknown_);
                            return object2;
                        }
                    }
                    InteropLibrary buffers__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        Cached1Data s1_ = super.insert(new Cached1Data());
                        buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        s1_.toHost_ = this.createToHost();
                        s1_.unsupported_ = BranchProfile.create();
                        s1_.unknown_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.cached1_cache = s1_;
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.ReadBufferFloatNode.doCached(arg0Value, arg1Value, arg2Value, buffers__, s1_.toHost_, s1_.unsupported_, s1_.unknown_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.ReadBufferFloatNode create(PolyglotValueDispatch.InteropValue interop) {
                return new ReadBufferFloatNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.ReadBufferFloatNode.class)
            private static final class Cached1Data
            extends Node {
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unknown_;

                Cached1Data() {
                }

                @Override
                public NodeCost getCost() {
                    return NodeCost.NONE;
                }
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.ReadBufferFloatNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary buffers_;
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unknown_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.WriteBufferLongNode.class)
        static final class WriteBufferLongNodeGen
        extends PolyglotValueDispatch.InteropValue.WriteBufferLongNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private Cached1Data cached1_cache;

            private WriteBufferLongNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    Cached1Data s1_;
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.buffers_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.WriteBufferLongNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_, s0_.invalidIndex_, s0_.invalidValue_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0 && (s1_ = this.cached1_cache) != null) {
                        return this.cached1Boundary(state_0, s1_, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, Cached1Data s1_, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.WriteBufferLongNode.doCached(arg0Value, arg1Value, arg2Value, buffers__, s1_.unsupported_, s1_.invalidIndex_, s1_.invalidValue_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.buffers_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.unsupported_ = BranchProfile.create();
                            s0_.invalidIndex_ = BranchProfile.create();
                            s0_.invalidValue_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.WriteBufferLongNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_, s0_.invalidIndex_, s0_.invalidValue_);
                            return object2;
                        }
                    }
                    InteropLibrary buffers__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        Cached1Data s1_ = super.insert(new Cached1Data());
                        buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        s1_.unsupported_ = BranchProfile.create();
                        s1_.invalidIndex_ = BranchProfile.create();
                        s1_.invalidValue_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.cached1_cache = s1_;
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.WriteBufferLongNode.doCached(arg0Value, arg1Value, arg2Value, buffers__, s1_.unsupported_, s1_.invalidIndex_, s1_.invalidValue_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.WriteBufferLongNode create(PolyglotValueDispatch.InteropValue interop) {
                return new WriteBufferLongNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.WriteBufferLongNode.class)
            private static final class Cached1Data
            extends Node {
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidIndex_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidValue_;

                Cached1Data() {
                }

                @Override
                public NodeCost getCost() {
                    return NodeCost.NONE;
                }
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.WriteBufferLongNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary buffers_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidIndex_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidValue_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.ReadBufferLongNode.class)
        static final class ReadBufferLongNodeGen
        extends PolyglotValueDispatch.InteropValue.ReadBufferLongNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private Cached1Data cached1_cache;

            private ReadBufferLongNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    Cached1Data s1_;
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.buffers_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.ReadBufferLongNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.toHost_, s0_.unsupported_, s0_.unknown_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0 && (s1_ = this.cached1_cache) != null) {
                        return this.cached1Boundary(state_0, s1_, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, Cached1Data s1_, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.ReadBufferLongNode.doCached(arg0Value, arg1Value, arg2Value, buffers__, s1_.toHost_, s1_.unsupported_, s1_.unknown_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.buffers_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toHost_ = this.createToHost();
                            s0_.unsupported_ = BranchProfile.create();
                            s0_.unknown_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.ReadBufferLongNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.toHost_, s0_.unsupported_, s0_.unknown_);
                            return object2;
                        }
                    }
                    InteropLibrary buffers__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        Cached1Data s1_ = super.insert(new Cached1Data());
                        buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        s1_.toHost_ = this.createToHost();
                        s1_.unsupported_ = BranchProfile.create();
                        s1_.unknown_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.cached1_cache = s1_;
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.ReadBufferLongNode.doCached(arg0Value, arg1Value, arg2Value, buffers__, s1_.toHost_, s1_.unsupported_, s1_.unknown_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.ReadBufferLongNode create(PolyglotValueDispatch.InteropValue interop) {
                return new ReadBufferLongNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.ReadBufferLongNode.class)
            private static final class Cached1Data
            extends Node {
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unknown_;

                Cached1Data() {
                }

                @Override
                public NodeCost getCost() {
                    return NodeCost.NONE;
                }
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.ReadBufferLongNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary buffers_;
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unknown_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.WriteBufferIntNode.class)
        static final class WriteBufferIntNodeGen
        extends PolyglotValueDispatch.InteropValue.WriteBufferIntNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private Cached1Data cached1_cache;

            private WriteBufferIntNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    Cached1Data s1_;
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.buffers_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.WriteBufferIntNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_, s0_.invalidIndex_, s0_.invalidValue_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0 && (s1_ = this.cached1_cache) != null) {
                        return this.cached1Boundary(state_0, s1_, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, Cached1Data s1_, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.WriteBufferIntNode.doCached(arg0Value, arg1Value, arg2Value, buffers__, s1_.unsupported_, s1_.invalidIndex_, s1_.invalidValue_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.buffers_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.unsupported_ = BranchProfile.create();
                            s0_.invalidIndex_ = BranchProfile.create();
                            s0_.invalidValue_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.WriteBufferIntNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_, s0_.invalidIndex_, s0_.invalidValue_);
                            return object2;
                        }
                    }
                    InteropLibrary buffers__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        Cached1Data s1_ = super.insert(new Cached1Data());
                        buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        s1_.unsupported_ = BranchProfile.create();
                        s1_.invalidIndex_ = BranchProfile.create();
                        s1_.invalidValue_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.cached1_cache = s1_;
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.WriteBufferIntNode.doCached(arg0Value, arg1Value, arg2Value, buffers__, s1_.unsupported_, s1_.invalidIndex_, s1_.invalidValue_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.WriteBufferIntNode create(PolyglotValueDispatch.InteropValue interop) {
                return new WriteBufferIntNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.WriteBufferIntNode.class)
            private static final class Cached1Data
            extends Node {
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidIndex_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidValue_;

                Cached1Data() {
                }

                @Override
                public NodeCost getCost() {
                    return NodeCost.NONE;
                }
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.WriteBufferIntNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary buffers_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidIndex_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidValue_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.ReadBufferIntNode.class)
        static final class ReadBufferIntNodeGen
        extends PolyglotValueDispatch.InteropValue.ReadBufferIntNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private Cached1Data cached1_cache;

            private ReadBufferIntNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    Cached1Data s1_;
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.buffers_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.ReadBufferIntNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.toHost_, s0_.unsupported_, s0_.unknown_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0 && (s1_ = this.cached1_cache) != null) {
                        return this.cached1Boundary(state_0, s1_, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, Cached1Data s1_, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.ReadBufferIntNode.doCached(arg0Value, arg1Value, arg2Value, buffers__, s1_.toHost_, s1_.unsupported_, s1_.unknown_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.buffers_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toHost_ = this.createToHost();
                            s0_.unsupported_ = BranchProfile.create();
                            s0_.unknown_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.ReadBufferIntNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.toHost_, s0_.unsupported_, s0_.unknown_);
                            return object2;
                        }
                    }
                    InteropLibrary buffers__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        Cached1Data s1_ = super.insert(new Cached1Data());
                        buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        s1_.toHost_ = this.createToHost();
                        s1_.unsupported_ = BranchProfile.create();
                        s1_.unknown_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.cached1_cache = s1_;
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.ReadBufferIntNode.doCached(arg0Value, arg1Value, arg2Value, buffers__, s1_.toHost_, s1_.unsupported_, s1_.unknown_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.ReadBufferIntNode create(PolyglotValueDispatch.InteropValue interop) {
                return new ReadBufferIntNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.ReadBufferIntNode.class)
            private static final class Cached1Data
            extends Node {
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unknown_;

                Cached1Data() {
                }

                @Override
                public NodeCost getCost() {
                    return NodeCost.NONE;
                }
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.ReadBufferIntNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary buffers_;
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unknown_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.WriteBufferShortNode.class)
        static final class WriteBufferShortNodeGen
        extends PolyglotValueDispatch.InteropValue.WriteBufferShortNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private Cached1Data cached1_cache;

            private WriteBufferShortNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    Cached1Data s1_;
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.buffers_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.WriteBufferShortNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_, s0_.invalidIndex_, s0_.invalidValue_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0 && (s1_ = this.cached1_cache) != null) {
                        return this.cached1Boundary(state_0, s1_, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, Cached1Data s1_, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.WriteBufferShortNode.doCached(arg0Value, arg1Value, arg2Value, buffers__, s1_.unsupported_, s1_.invalidIndex_, s1_.invalidValue_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.buffers_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.unsupported_ = BranchProfile.create();
                            s0_.invalidIndex_ = BranchProfile.create();
                            s0_.invalidValue_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.WriteBufferShortNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_, s0_.invalidIndex_, s0_.invalidValue_);
                            return object2;
                        }
                    }
                    InteropLibrary buffers__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        Cached1Data s1_ = super.insert(new Cached1Data());
                        buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        s1_.unsupported_ = BranchProfile.create();
                        s1_.invalidIndex_ = BranchProfile.create();
                        s1_.invalidValue_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.cached1_cache = s1_;
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.WriteBufferShortNode.doCached(arg0Value, arg1Value, arg2Value, buffers__, s1_.unsupported_, s1_.invalidIndex_, s1_.invalidValue_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.WriteBufferShortNode create(PolyglotValueDispatch.InteropValue interop) {
                return new WriteBufferShortNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.WriteBufferShortNode.class)
            private static final class Cached1Data
            extends Node {
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidIndex_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidValue_;

                Cached1Data() {
                }

                @Override
                public NodeCost getCost() {
                    return NodeCost.NONE;
                }
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.WriteBufferShortNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary buffers_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidIndex_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidValue_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.ReadBufferShortNode.class)
        static final class ReadBufferShortNodeGen
        extends PolyglotValueDispatch.InteropValue.ReadBufferShortNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private Cached1Data cached1_cache;

            private ReadBufferShortNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    Cached1Data s1_;
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.buffers_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.ReadBufferShortNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.toHost_, s0_.unsupported_, s0_.unknown_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0 && (s1_ = this.cached1_cache) != null) {
                        return this.cached1Boundary(state_0, s1_, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, Cached1Data s1_, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.ReadBufferShortNode.doCached(arg0Value, arg1Value, arg2Value, buffers__, s1_.toHost_, s1_.unsupported_, s1_.unknown_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.buffers_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toHost_ = this.createToHost();
                            s0_.unsupported_ = BranchProfile.create();
                            s0_.unknown_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.ReadBufferShortNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.toHost_, s0_.unsupported_, s0_.unknown_);
                            return object2;
                        }
                    }
                    InteropLibrary buffers__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        Cached1Data s1_ = super.insert(new Cached1Data());
                        buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        s1_.toHost_ = this.createToHost();
                        s1_.unsupported_ = BranchProfile.create();
                        s1_.unknown_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.cached1_cache = s1_;
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.ReadBufferShortNode.doCached(arg0Value, arg1Value, arg2Value, buffers__, s1_.toHost_, s1_.unsupported_, s1_.unknown_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.ReadBufferShortNode create(PolyglotValueDispatch.InteropValue interop) {
                return new ReadBufferShortNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.ReadBufferShortNode.class)
            private static final class Cached1Data
            extends Node {
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unknown_;

                Cached1Data() {
                }

                @Override
                public NodeCost getCost() {
                    return NodeCost.NONE;
                }
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.ReadBufferShortNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary buffers_;
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unknown_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.WriteBufferByteNode.class)
        static final class WriteBufferByteNodeGen
        extends PolyglotValueDispatch.InteropValue.WriteBufferByteNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private Cached1Data cached1_cache;

            private WriteBufferByteNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    Cached1Data s1_;
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.buffers_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.WriteBufferByteNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_, s0_.invalidIndex_, s0_.invalidValue_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0 && (s1_ = this.cached1_cache) != null) {
                        return this.cached1Boundary(state_0, s1_, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, Cached1Data s1_, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.WriteBufferByteNode.doCached(arg0Value, arg1Value, arg2Value, buffers__, s1_.unsupported_, s1_.invalidIndex_, s1_.invalidValue_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.buffers_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.unsupported_ = BranchProfile.create();
                            s0_.invalidIndex_ = BranchProfile.create();
                            s0_.invalidValue_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.WriteBufferByteNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_, s0_.invalidIndex_, s0_.invalidValue_);
                            return object2;
                        }
                    }
                    InteropLibrary buffers__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        Cached1Data s1_ = super.insert(new Cached1Data());
                        buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        s1_.unsupported_ = BranchProfile.create();
                        s1_.invalidIndex_ = BranchProfile.create();
                        s1_.invalidValue_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.cached1_cache = s1_;
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.WriteBufferByteNode.doCached(arg0Value, arg1Value, arg2Value, buffers__, s1_.unsupported_, s1_.invalidIndex_, s1_.invalidValue_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.WriteBufferByteNode create(PolyglotValueDispatch.InteropValue interop) {
                return new WriteBufferByteNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.WriteBufferByteNode.class)
            private static final class Cached1Data
            extends Node {
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidIndex_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidValue_;

                Cached1Data() {
                }

                @Override
                public NodeCost getCost() {
                    return NodeCost.NONE;
                }
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.WriteBufferByteNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary buffers_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidIndex_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidValue_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.ReadBufferByteNode.class)
        static final class ReadBufferByteNodeGen
        extends PolyglotValueDispatch.InteropValue.ReadBufferByteNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private Cached1Data cached1_cache;

            private ReadBufferByteNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    Cached1Data s1_;
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.buffers_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.ReadBufferByteNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.toHost_, s0_.unsupported_, s0_.unknown_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0 && (s1_ = this.cached1_cache) != null) {
                        return this.cached1Boundary(state_0, s1_, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, Cached1Data s1_, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.ReadBufferByteNode.doCached(arg0Value, arg1Value, arg2Value, buffers__, s1_.toHost_, s1_.unsupported_, s1_.unknown_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.buffers_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toHost_ = this.createToHost();
                            s0_.unsupported_ = BranchProfile.create();
                            s0_.unknown_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.ReadBufferByteNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.toHost_, s0_.unsupported_, s0_.unknown_);
                            return object2;
                        }
                    }
                    InteropLibrary buffers__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        Cached1Data s1_ = super.insert(new Cached1Data());
                        buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        s1_.toHost_ = this.createToHost();
                        s1_.unsupported_ = BranchProfile.create();
                        s1_.unknown_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.cached1_cache = s1_;
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.ReadBufferByteNode.doCached(arg0Value, arg1Value, arg2Value, buffers__, s1_.toHost_, s1_.unsupported_, s1_.unknown_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.ReadBufferByteNode create(PolyglotValueDispatch.InteropValue interop) {
                return new ReadBufferByteNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.ReadBufferByteNode.class)
            private static final class Cached1Data
            extends Node {
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unknown_;

                Cached1Data() {
                }

                @Override
                public NodeCost getCost() {
                    return NodeCost.NONE;
                }
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.ReadBufferByteNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary buffers_;
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unknown_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetBufferSizeNode.class)
        static final class GetBufferSizeNodeGen
        extends PolyglotValueDispatch.InteropValue.GetBufferSizeNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_unsupported_;

            private GetBufferSizeNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.buffers_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.GetBufferSizeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.GetBufferSizeNode.doCached(arg0Value, arg1Value, arg2Value, cached1_buffers__, this.cached1_unsupported_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.buffers_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.unsupported_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.GetBufferSizeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_buffers__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_unsupported_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.GetBufferSizeNode.doCached(arg0Value, arg1Value, arg2Value, cached1_buffers__, this.cached1_unsupported_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.GetBufferSizeNode create(PolyglotValueDispatch.InteropValue interop) {
                return new GetBufferSizeNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetBufferSizeNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary buffers_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.IsBufferWritableNode.class)
        static final class IsBufferWritableNodeGen
        extends PolyglotValueDispatch.InteropValue.IsBufferWritableNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_unsupported_;

            private IsBufferWritableNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.buffers_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.IsBufferWritableNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.IsBufferWritableNode.doCached(arg0Value, arg1Value, arg2Value, cached1_buffers__, this.cached1_unsupported_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.buffers_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.unsupported_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.IsBufferWritableNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_buffers__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_unsupported_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.IsBufferWritableNode.doCached(arg0Value, arg1Value, arg2Value, cached1_buffers__, this.cached1_unsupported_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.IsBufferWritableNode create(PolyglotValueDispatch.InteropValue interop) {
                return new IsBufferWritableNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.IsBufferWritableNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary buffers_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.HasBufferElementsNode.class)
        static final class HasBufferElementsNodeGen
        extends PolyglotValueDispatch.InteropValue.HasBufferElementsNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;

            private HasBufferElementsNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.buffers_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.HasBufferElementsNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.HasBufferElementsNode.doCached(arg0Value, arg1Value, arg2Value, cached1_buffers__);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.buffers_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.HasBufferElementsNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_buffers__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_buffers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.HasBufferElementsNode.doCached(arg0Value, arg1Value, arg2Value, cached1_buffers__);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.HasBufferElementsNode create(PolyglotValueDispatch.InteropValue interop) {
                return new HasBufferElementsNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.HasBufferElementsNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary buffers_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetArraySizeNode.class)
        static final class GetArraySizeNodeGen
        extends PolyglotValueDispatch.InteropValue.GetArraySizeNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_unsupported_;

            private GetArraySizeNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.arrays_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.GetArraySizeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.arrays_, s0_.unsupported_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_arrays__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.GetArraySizeNode.doCached(arg0Value, arg1Value, arg2Value, cached1_arrays__, this.cached1_unsupported_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.arrays_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.arrays_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.unsupported_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.GetArraySizeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.arrays_, s0_.unsupported_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_arrays__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_arrays__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_unsupported_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.GetArraySizeNode.doCached(arg0Value, arg1Value, arg2Value, cached1_arrays__, this.cached1_unsupported_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.GetArraySizeNode create(PolyglotValueDispatch.InteropValue interop) {
                return new GetArraySizeNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetArraySizeNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary arrays_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.RemoveArrayElementNode.class)
        static final class RemoveArrayElementNodeGen
        extends PolyglotValueDispatch.InteropValue.RemoveArrayElementNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_unsupported_;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_invalidIndex_;

            private RemoveArrayElementNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.arrays_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.RemoveArrayElementNode.doCached(arg0Value, arg1Value, arg2Value, s0_.arrays_, s0_.unsupported_, s0_.invalidIndex_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_arrays__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.RemoveArrayElementNode.doCached(arg0Value, arg1Value, arg2Value, cached1_arrays__, this.cached1_unsupported_, this.cached1_invalidIndex_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.arrays_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.arrays_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.unsupported_ = BranchProfile.create();
                            s0_.invalidIndex_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.RemoveArrayElementNode.doCached(arg0Value, arg1Value, arg2Value, s0_.arrays_, s0_.unsupported_, s0_.invalidIndex_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_arrays__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_arrays__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_unsupported_ = BranchProfile.create();
                        this.cached1_invalidIndex_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.RemoveArrayElementNode.doCached(arg0Value, arg1Value, arg2Value, cached1_arrays__, this.cached1_unsupported_, this.cached1_invalidIndex_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.RemoveArrayElementNode create(PolyglotValueDispatch.InteropValue interop) {
                return new RemoveArrayElementNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.RemoveArrayElementNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary arrays_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidIndex_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.SetArrayElementNode.class)
        static final class SetArrayElementNodeGen
        extends PolyglotValueDispatch.InteropValue.SetArrayElementNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private Cached1Data cached1_cache;

            private SetArrayElementNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    Cached1Data s1_;
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.arrays_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.SetArrayElementNode.doCached(arg0Value, arg1Value, arg2Value, s0_.arrays_, s0_.toGuestValue_, s0_.unsupported_, s0_.invalidIndex_, s0_.invalidValue_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0 && (s1_ = this.cached1_cache) != null) {
                        return this.cached1Boundary(state_0, s1_, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, Cached1Data s1_, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary arrays__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.SetArrayElementNode.doCached(arg0Value, arg1Value, arg2Value, arrays__, s1_.toGuestValue_, s1_.unsupported_, s1_.invalidIndex_, s1_.invalidValue_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.arrays_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.arrays_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toGuestValue_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                            s0_.unsupported_ = BranchProfile.create();
                            s0_.invalidIndex_ = BranchProfile.create();
                            s0_.invalidValue_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.SetArrayElementNode.doCached(arg0Value, arg1Value, arg2Value, s0_.arrays_, s0_.toGuestValue_, s0_.unsupported_, s0_.invalidIndex_, s0_.invalidValue_);
                            return object2;
                        }
                    }
                    InteropLibrary arrays__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        Cached1Data s1_ = super.insert(new Cached1Data());
                        arrays__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        s1_.toGuestValue_ = s1_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                        s1_.unsupported_ = BranchProfile.create();
                        s1_.invalidIndex_ = BranchProfile.create();
                        s1_.invalidValue_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.cached1_cache = s1_;
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.SetArrayElementNode.doCached(arg0Value, arg1Value, arg2Value, arrays__, s1_.toGuestValue_, s1_.unsupported_, s1_.invalidIndex_, s1_.invalidValue_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.SetArrayElementNode create(PolyglotValueDispatch.InteropValue interop) {
                return new SetArrayElementNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.SetArrayElementNode.class)
            private static final class Cached1Data
            extends Node {
                @Node.Child
                PolyglotLanguageContext.ToGuestValueNode toGuestValue_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidIndex_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidValue_;

                Cached1Data() {
                }

                @Override
                public NodeCost getCost() {
                    return NodeCost.NONE;
                }

                <T extends Node> T insertAccessor(T node) {
                    return super.insert(node);
                }
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.SetArrayElementNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary arrays_;
                @Node.Child
                PolyglotLanguageContext.ToGuestValueNode toGuestValue_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidIndex_;
                @CompilerDirectives.CompilationFinal
                BranchProfile invalidValue_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetArrayElementNode.class)
        static final class GetArrayElementNodeGen
        extends PolyglotValueDispatch.InteropValue.GetArrayElementNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private Cached1Data cached1_cache;

            private GetArrayElementNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    Cached1Data s1_;
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.arrays_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.GetArrayElementNode.doCached(arg0Value, arg1Value, arg2Value, s0_.arrays_, s0_.toHost_, s0_.unsupported_, s0_.unknown_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0 && (s1_ = this.cached1_cache) != null) {
                        return this.cached1Boundary(state_0, s1_, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, Cached1Data s1_, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary arrays__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.GetArrayElementNode.doCached(arg0Value, arg1Value, arg2Value, arrays__, s1_.toHost_, s1_.unsupported_, s1_.unknown_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.arrays_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.arrays_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toHost_ = this.createToHost();
                            s0_.unsupported_ = BranchProfile.create();
                            s0_.unknown_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.GetArrayElementNode.doCached(arg0Value, arg1Value, arg2Value, s0_.arrays_, s0_.toHost_, s0_.unsupported_, s0_.unknown_);
                            return object2;
                        }
                    }
                    InteropLibrary arrays__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        Cached1Data s1_ = super.insert(new Cached1Data());
                        arrays__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        s1_.toHost_ = this.createToHost();
                        s1_.unsupported_ = BranchProfile.create();
                        s1_.unknown_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.cached1_cache = s1_;
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.GetArrayElementNode.doCached(arg0Value, arg1Value, arg2Value, arrays__, s1_.toHost_, s1_.unsupported_, s1_.unknown_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.GetArrayElementNode create(PolyglotValueDispatch.InteropValue interop) {
                return new GetArrayElementNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetArrayElementNode.class)
            private static final class Cached1Data
            extends Node {
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unknown_;

                Cached1Data() {
                }

                @Override
                public NodeCost getCost() {
                    return NodeCost.NONE;
                }
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetArrayElementNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary arrays_;
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unknown_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetMemberKeysNode.class)
        static final class GetMemberKeysNodeGen
        extends PolyglotValueDispatch.InteropValue.GetMemberKeysNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @CompilerDirectives.CompilationFinal
            private PolyglotLanguageContext.ToHostValueNode cached1_toHost_;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_unsupported_;

            private GetMemberKeysNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.objects_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.GetMemberKeysNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.toHost_, s0_.unsupported_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.GetMemberKeysNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_toHost_, this.cached1_unsupported_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.objects_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toHost_ = this.createToHost();
                            s0_.unsupported_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.GetMemberKeysNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.toHost_, s0_.unsupported_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_objects__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_toHost_ = this.createToHost();
                        this.cached1_unsupported_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.GetMemberKeysNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_toHost_, this.cached1_unsupported_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.GetMemberKeysNode create(PolyglotValueDispatch.InteropValue interop) {
                return new GetMemberKeysNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.GetMemberKeysNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary objects_;
                @CompilerDirectives.CompilationFinal
                PolyglotLanguageContext.ToHostValueNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.HasArrayElementsNode.class)
        static final class HasArrayElementsNodeGen
        extends PolyglotValueDispatch.InteropValue.HasArrayElementsNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;

            private HasArrayElementsNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.arrays_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.HasArrayElementsNode.doCached(arg0Value, arg1Value, arg2Value, s0_.arrays_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_arrays__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.HasArrayElementsNode.doCached(arg0Value, arg1Value, arg2Value, cached1_arrays__);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.arrays_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.arrays_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.HasArrayElementsNode.doCached(arg0Value, arg1Value, arg2Value, s0_.arrays_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_arrays__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_arrays__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.HasArrayElementsNode.doCached(arg0Value, arg1Value, arg2Value, cached1_arrays__);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.HasArrayElementsNode create(PolyglotValueDispatch.InteropValue interop) {
                return new HasArrayElementsNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.HasArrayElementsNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary arrays_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.AsNativePointerNode.class)
        static final class AsNativePointerNodeGen
        extends PolyglotValueDispatch.InteropValue.AsNativePointerNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_unsupported_;

            private AsNativePointerNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.natives_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.AsNativePointerNode.doCached(arg0Value, arg1Value, arg2Value, s0_.natives_, s0_.unsupported_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_natives__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.AsNativePointerNode.doCached(arg0Value, arg1Value, arg2Value, cached1_natives__, this.cached1_unsupported_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.natives_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.natives_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.unsupported_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.AsNativePointerNode.doCached(arg0Value, arg1Value, arg2Value, s0_.natives_, s0_.unsupported_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_natives__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_natives__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_unsupported_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.AsNativePointerNode.doCached(arg0Value, arg1Value, arg2Value, cached1_natives__, this.cached1_unsupported_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.AsNativePointerNode create(PolyglotValueDispatch.InteropValue interop) {
                return new AsNativePointerNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.AsNativePointerNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary natives_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.IsNativePointerNode.class)
        static final class IsNativePointerNodeGen
        extends PolyglotValueDispatch.InteropValue.IsNativePointerNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;

            private IsNativePointerNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.natives_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.IsNativePointerNode.doCached(arg0Value, arg1Value, arg2Value, s0_.natives_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_natives__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.IsNativePointerNode.doCached(arg0Value, arg1Value, arg2Value, cached1_natives__);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.natives_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.natives_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.IsNativePointerNode.doCached(arg0Value, arg1Value, arg2Value, s0_.natives_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_natives__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_natives__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.IsNativePointerNode.doCached(arg0Value, arg1Value, arg2Value, cached1_natives__);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.IsNativePointerNode create(PolyglotValueDispatch.InteropValue interop) {
                return new IsNativePointerNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.IsNativePointerNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary natives_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.AsInstantNode.class)
        static final class AsInstantNodeGen
        extends PolyglotValueDispatch.InteropValue.AsInstantNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_unsupported_;

            private AsInstantNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.objects_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.AsInstantNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.AsInstantNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.objects_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.unsupported_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.AsInstantNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_objects__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_unsupported_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.AsInstantNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.AsInstantNode create(PolyglotValueDispatch.InteropValue interop) {
                return new AsInstantNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.AsInstantNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary objects_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.AsDurationNode.class)
        static final class AsDurationNodeGen
        extends PolyglotValueDispatch.InteropValue.AsDurationNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_unsupported_;

            private AsDurationNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.objects_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.AsDurationNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.AsDurationNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.objects_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.unsupported_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.AsDurationNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_objects__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_unsupported_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.AsDurationNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.AsDurationNode create(PolyglotValueDispatch.InteropValue interop) {
                return new AsDurationNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.AsDurationNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary objects_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.IsDurationNode.class)
        static final class IsDurationNodeGen
        extends PolyglotValueDispatch.InteropValue.IsDurationNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;

            private IsDurationNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.objects_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.IsDurationNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.IsDurationNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.objects_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.IsDurationNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_objects__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.IsDurationNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.IsDurationNode create(PolyglotValueDispatch.InteropValue interop) {
                return new IsDurationNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.IsDurationNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary objects_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.AsTimeZoneNode.class)
        static final class AsTimeZoneNodeGen
        extends PolyglotValueDispatch.InteropValue.AsTimeZoneNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_unsupported_;

            private AsTimeZoneNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.objects_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.AsTimeZoneNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.AsTimeZoneNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.objects_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.unsupported_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.AsTimeZoneNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_objects__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_unsupported_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.AsTimeZoneNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.AsTimeZoneNode create(PolyglotValueDispatch.InteropValue interop) {
                return new AsTimeZoneNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.AsTimeZoneNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary objects_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.IsTimeZoneNode.class)
        static final class IsTimeZoneNodeGen
        extends PolyglotValueDispatch.InteropValue.IsTimeZoneNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;

            private IsTimeZoneNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.objects_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.IsTimeZoneNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.IsTimeZoneNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.objects_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.IsTimeZoneNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_objects__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.IsTimeZoneNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.IsTimeZoneNode create(PolyglotValueDispatch.InteropValue interop) {
                return new IsTimeZoneNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.IsTimeZoneNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary objects_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.AsTimeNode.class)
        static final class AsTimeNodeGen
        extends PolyglotValueDispatch.InteropValue.AsTimeNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_unsupported_;

            private AsTimeNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.objects_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.AsTimeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.AsTimeNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.objects_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.unsupported_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.AsTimeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_objects__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_unsupported_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.AsTimeNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.AsTimeNode create(PolyglotValueDispatch.InteropValue interop) {
                return new AsTimeNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.AsTimeNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary objects_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.IsTimeNode.class)
        static final class IsTimeNodeGen
        extends PolyglotValueDispatch.InteropValue.IsTimeNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;

            private IsTimeNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.objects_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.IsTimeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.IsTimeNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.objects_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.IsTimeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_objects__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.IsTimeNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.IsTimeNode create(PolyglotValueDispatch.InteropValue interop) {
                return new IsTimeNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.IsTimeNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary objects_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.AsDateNode.class)
        static final class AsDateNodeGen
        extends PolyglotValueDispatch.InteropValue.AsDateNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_unsupported_;

            private AsDateNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.objects_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.AsDateNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.AsDateNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.objects_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.unsupported_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.AsDateNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_objects__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_unsupported_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.AsDateNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.AsDateNode create(PolyglotValueDispatch.InteropValue interop) {
                return new AsDateNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.AsDateNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary objects_;
                @CompilerDirectives.CompilationFinal
                BranchProfile unsupported_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

        @GeneratedBy(value=PolyglotValueDispatch.InteropValue.IsDateNode.class)
        static final class IsDateNodeGen
        extends PolyglotValueDispatch.InteropValue.IsDateNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;

            private IsDateNodeGen(PolyglotValueDispatch.InteropValue interop) {
                super(interop);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.objects_.accepts(arg1Value)) {
                                return PolyglotValueDispatch.InteropValue.IsDateNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = PolyglotValueDispatch.InteropValue.IsDateNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.objects_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = PolyglotValueDispatch.InteropValue.IsDateNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_objects__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_objects__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = PolyglotValueDispatch.InteropValue.IsDateNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotValueDispatch.InteropValue.IsDateNode create(PolyglotValueDispatch.InteropValue interop) {
                return new IsDateNodeGen(interop);
            }

            @GeneratedBy(value=PolyglotValueDispatch.InteropValue.IsDateNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary objects_;

                Cached0Data(Cached0Data next_) {
                    this.next_ = next_;
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

