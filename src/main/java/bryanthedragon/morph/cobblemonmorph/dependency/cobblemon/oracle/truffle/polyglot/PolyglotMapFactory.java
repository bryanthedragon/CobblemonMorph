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
import com.oracle.truffle.polyglot.PolyglotMap;
import com.oracle.truffle.polyglot.PolyglotToHostNode;
import com.oracle.truffle.polyglot.PolyglotToHostNodeGen;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=PolyglotMap.class)
final class PolyglotMapFactory {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

    PolyglotMapFactory() {
    }

    @GeneratedBy(value=PolyglotMap.Cache.class)
    static final class CacheFactory {
        CacheFactory() {
        }

        @GeneratedBy(value=PolyglotMap.Cache.HashSizeNode.class)
        static final class HashSizeNodeGen
        extends PolyglotMap.Cache.HashSizeNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_error_;

            private HashSizeNodeGen(PolyglotMap.Cache cache) {
                super(cache);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.interop_.accepts(arg1Value)) {
                                return this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.error_);
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
                    InteropLibrary cached1_interop__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_error_);
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
                            while (s0_ != null && !s0_.interop_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.interop_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.error_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.error_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_interop__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_interop__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_error_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_error_);
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

            public static PolyglotMap.Cache.HashSizeNode create(PolyglotMap.Cache cache) {
                return new HashSizeNodeGen(cache);
            }

            @GeneratedBy(value=PolyglotMap.Cache.HashSizeNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary interop_;
                @CompilerDirectives.CompilationFinal
                BranchProfile error_;

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

        @GeneratedBy(value=PolyglotMap.Cache.HashEntriesIteratorNode.class)
        static final class HashEntriesIteratorNodeGen
        extends PolyglotMap.Cache.HashEntriesIteratorNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private PolyglotToHostNode cached1_toHost_;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_error_;

            private HashEntriesIteratorNodeGen(PolyglotMap.Cache cache) {
                super(cache);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.interop_.accepts(arg1Value)) {
                                return this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toHost_, s0_.error_);
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
                    InteropLibrary cached1_interop__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_toHost_, this.cached1_error_);
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
                            while (s0_ != null && !s0_.interop_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.interop_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toHost_ = s0_.insertAccessor(PolyglotToHostNodeGen.create());
                            s0_.error_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toHost_, s0_.error_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_interop__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_interop__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_toHost_ = super.insert(PolyglotToHostNodeGen.create());
                        this.cached1_error_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_toHost_, this.cached1_error_);
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

            public static PolyglotMap.Cache.HashEntriesIteratorNode create(PolyglotMap.Cache cache) {
                return new HashEntriesIteratorNodeGen(cache);
            }

            @GeneratedBy(value=PolyglotMap.Cache.HashEntriesIteratorNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary interop_;
                @Node.Child
                PolyglotToHostNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile error_;

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

        @GeneratedBy(value=PolyglotMap.Cache.RemoveBoolean.class)
        static final class RemoveBooleanNodeGen
        extends PolyglotMap.Cache.RemoveBoolean {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private PolyglotLanguageContext.ToGuestValueNode cached1_toGuest_;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_error_;

            private RemoveBooleanNodeGen(PolyglotMap.Cache cache) {
                super(cache);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.interop_.accepts(arg1Value)) {
                                return this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toGuest_, s0_.error_);
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
                    InteropLibrary cached1_interop__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_toGuest_, this.cached1_error_);
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
                            while (s0_ != null && !s0_.interop_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.interop_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toGuest_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                            s0_.error_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toGuest_, s0_.error_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_interop__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_interop__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_toGuest_ = super.insert(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                        this.cached1_error_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_toGuest_, this.cached1_error_);
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

            public static PolyglotMap.Cache.RemoveBoolean create(PolyglotMap.Cache cache) {
                return new RemoveBooleanNodeGen(cache);
            }

            @GeneratedBy(value=PolyglotMap.Cache.RemoveBoolean.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary interop_;
                @Node.Child
                PolyglotLanguageContext.ToGuestValueNode toGuest_;
                @CompilerDirectives.CompilationFinal
                BranchProfile error_;

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

        @GeneratedBy(value=PolyglotMap.Cache.RemoveNode.class)
        static final class RemoveNodeGen
        extends PolyglotMap.Cache.RemoveNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private PolyglotLanguageContext.ToGuestValueNode cached1_toGuest_;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_error_;

            private RemoveNodeGen(PolyglotMap.Cache cache) {
                super(cache);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.interop_.accepts(arg1Value)) {
                                return this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toGuest_, s0_.error_);
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
                    InteropLibrary cached1_interop__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_toGuest_, this.cached1_error_);
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
                            while (s0_ != null && !s0_.interop_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.interop_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toGuest_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                            s0_.error_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toGuest_, s0_.error_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_interop__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_interop__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_toGuest_ = super.insert(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                        this.cached1_error_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_toGuest_, this.cached1_error_);
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

            public static PolyglotMap.Cache.RemoveNode create(PolyglotMap.Cache cache) {
                return new RemoveNodeGen(cache);
            }

            @GeneratedBy(value=PolyglotMap.Cache.RemoveNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary interop_;
                @Node.Child
                PolyglotLanguageContext.ToGuestValueNode toGuest_;
                @CompilerDirectives.CompilationFinal
                BranchProfile error_;

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

        @GeneratedBy(value=PolyglotMap.Cache.Put.class)
        static final class PutNodeGen
        extends PolyglotMap.Cache.Put {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private PolyglotLanguageContext.ToGuestValueNode cached1_toGuest_;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_error_;

            private PutNodeGen(PolyglotMap.Cache cache) {
                super(cache);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.interop_.accepts(arg1Value)) {
                                return this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toGuest_, s0_.error_);
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
                    InteropLibrary cached1_interop__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_toGuest_, this.cached1_error_);
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
                            while (s0_ != null && !s0_.interop_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.interop_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toGuest_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                            s0_.error_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toGuest_, s0_.error_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_interop__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_interop__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_toGuest_ = super.insert(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                        this.cached1_error_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_toGuest_, this.cached1_error_);
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

            public static PolyglotMap.Cache.Put create(PolyglotMap.Cache cache) {
                return new PutNodeGen(cache);
            }

            @GeneratedBy(value=PolyglotMap.Cache.Put.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary interop_;
                @Node.Child
                PolyglotLanguageContext.ToGuestValueNode toGuest_;
                @CompilerDirectives.CompilationFinal
                BranchProfile error_;

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

        @GeneratedBy(value=PolyglotMap.Cache.GetNode.class)
        static final class GetNodeGen
        extends PolyglotMap.Cache.GetNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private Cached1Data cached1_cache;

            private GetNodeGen(PolyglotMap.Cache cache) {
                super(cache);
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
                            if (s0_.interop_.accepts(arg1Value)) {
                                return this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toGuest_, s0_.toHost_, s0_.error_);
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
                    InteropLibrary interop__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = this.doCached(arg0Value, arg1Value, arg2Value, interop__, s1_.toGuest_, s1_.toHost_, s1_.error_);
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
                            while (s0_ != null && !s0_.interop_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.interop_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toGuest_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                            s0_.toHost_ = s0_.insertAccessor(PolyglotToHostNodeGen.create());
                            s0_.error_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toGuest_, s0_.toHost_, s0_.error_);
                            return object2;
                        }
                    }
                    InteropLibrary interop__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        Cached1Data s1_ = super.insert(new Cached1Data());
                        interop__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        s1_.toGuest_ = s1_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                        s1_.toHost_ = s1_.insertAccessor(PolyglotToHostNodeGen.create());
                        s1_.error_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.cached1_cache = s1_;
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = this.doCached(arg0Value, arg1Value, arg2Value, interop__, s1_.toGuest_, s1_.toHost_, s1_.error_);
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

            public static PolyglotMap.Cache.GetNode create(PolyglotMap.Cache cache) {
                return new GetNodeGen(cache);
            }

            @GeneratedBy(value=PolyglotMap.Cache.GetNode.class)
            private static final class Cached1Data
            extends Node {
                @Node.Child
                PolyglotLanguageContext.ToGuestValueNode toGuest_;
                @Node.Child
                PolyglotToHostNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile error_;

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

            @GeneratedBy(value=PolyglotMap.Cache.GetNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary interop_;
                @Node.Child
                PolyglotLanguageContext.ToGuestValueNode toGuest_;
                @Node.Child
                PolyglotToHostNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile error_;

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

        @GeneratedBy(value=PolyglotMap.Cache.EntrySet.class)
        static final class EntrySetNodeGen
        extends PolyglotMap.Cache.EntrySet {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private PolyglotToHostNode cached1_toHost_;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_error_;

            private EntrySetNodeGen(PolyglotMap.Cache cache) {
                super(cache);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.interop_.accepts(arg1Value)) {
                                return this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toHost_, s0_.error_);
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
                    InteropLibrary cached1_interop__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_toHost_, this.cached1_error_);
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
                            while (s0_ != null && !s0_.interop_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.interop_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toHost_ = s0_.insertAccessor(PolyglotToHostNodeGen.create());
                            s0_.error_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toHost_, s0_.error_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_interop__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_interop__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_toHost_ = super.insert(PolyglotToHostNodeGen.create());
                        this.cached1_error_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_toHost_, this.cached1_error_);
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

            public static PolyglotMap.Cache.EntrySet create(PolyglotMap.Cache cache) {
                return new EntrySetNodeGen(cache);
            }

            @GeneratedBy(value=PolyglotMap.Cache.EntrySet.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary interop_;
                @Node.Child
                PolyglotToHostNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile error_;

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

        @GeneratedBy(value=PolyglotMap.Cache.ContainsKeyNode.class)
        static final class ContainsKeyNodeGen
        extends PolyglotMap.Cache.ContainsKeyNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private PolyglotLanguageContext.ToGuestValueNode cached1_toGuest_;

            private ContainsKeyNodeGen(PolyglotMap.Cache cache) {
                super(cache);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.interop_.accepts(arg1Value)) {
                                return this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toGuest_);
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
                    InteropLibrary cached1_interop__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_toGuest_);
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
                            while (s0_ != null && !s0_.interop_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.interop_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toGuest_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toGuest_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_interop__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_interop__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_toGuest_ = super.insert(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_toGuest_);
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

            public static PolyglotMap.Cache.ContainsKeyNode create(PolyglotMap.Cache cache) {
                return new ContainsKeyNodeGen(cache);
            }

            @GeneratedBy(value=PolyglotMap.Cache.ContainsKeyNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary interop_;
                @Node.Child
                PolyglotLanguageContext.ToGuestValueNode toGuest_;

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

