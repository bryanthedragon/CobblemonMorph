/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.object;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.object.DynamicObjectLibraryImpl;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=DynamicObjectLibraryImpl.class)
final class DynamicObjectLibraryImplFactory {
    DynamicObjectLibraryImplFactory() {
    }

    @GeneratedBy(value=DynamicObjectLibraryImpl.ResetShapeNode.class)
    static final class ResetShapeNodeGen
    extends DynamicObjectLibraryImpl.ResetShapeNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private CachedData cached_cache;

        private ResetShapeNodeGen() {
        }

        @Override
        @ExplodeLoop
        boolean execute(DynamicObject arg0Value, Shape arg1Value, Shape arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                CachedData s0_ = this.cached_cache;
                while (s0_ != null) {
                    if (arg2Value == s0_.cachedOtherShape_) {
                        return DynamicObjectLibraryImpl.ResetShapeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.cachedOtherShape_);
                    }
                    s0_ = s0_.next_;
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        private boolean executeAndSpecialize(DynamicObject arg0Value, Shape arg1Value, Shape arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                Shape cachedOtherShape__;
                int state_0 = this.state_0_;
                int count0_ = 0;
                CachedData s0_ = this.cached_cache;
                if (state_0 != 0) {
                    while (s0_ != null && arg2Value != s0_.cachedOtherShape_) {
                        s0_ = s0_.next_;
                        ++count0_;
                    }
                }
                if (s0_ == null && arg2Value == (cachedOtherShape__ = DynamicObjectLibraryImpl.ResetShapeNode.verifyResetShape(arg1Value, arg2Value)) && count0_ < 3) {
                    s0_ = new CachedData(this.cached_cache);
                    s0_.cachedOtherShape_ = cachedOtherShape__;
                    VarHandle.storeStoreFence();
                    this.cached_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                }
                if (s0_ != null) {
                    lock.unlock();
                    hasLock = false;
                    boolean bl = DynamicObjectLibraryImpl.ResetShapeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.cachedOtherShape_);
                    return bl;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
            }
            finally {
                if (hasLock) {
                    lock.unlock();
                }
            }
        }

        @Override
        public NodeCost getCost() {
            CachedData s0_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached_cache) == null || s0_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static DynamicObjectLibraryImpl.ResetShapeNode create() {
            return new ResetShapeNodeGen();
        }

        public static DynamicObjectLibraryImpl.ResetShapeNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=DynamicObjectLibraryImpl.ResetShapeNode.class)
        @DenyReplace
        private static final class Uncached
        extends DynamicObjectLibraryImpl.ResetShapeNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            boolean execute(DynamicObject arg0Value, Shape arg1Value, Shape arg2Value) {
                if (arg2Value == DynamicObjectLibraryImpl.ResetShapeNode.verifyResetShape(arg1Value, arg2Value)) {
                    return DynamicObjectLibraryImpl.ResetShapeNode.doCached(arg0Value, arg1Value, arg2Value, DynamicObjectLibraryImpl.ResetShapeNode.verifyResetShape(arg1Value, arg2Value));
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.MEGAMORPHIC;
            }

            @Override
            public boolean isAdoptable() {
                return false;
            }
        }

        @GeneratedBy(value=DynamicObjectLibraryImpl.ResetShapeNode.class)
        private static final class CachedData {
            @CompilerDirectives.CompilationFinal
            CachedData next_;
            @CompilerDirectives.CompilationFinal
            Shape cachedOtherShape_;

            CachedData(CachedData next_) {
                this.next_ = next_;
            }
        }
    }

    @GeneratedBy(value=DynamicObjectLibraryImpl.MakeSharedNode.class)
    static final class MakeSharedNodeGen
    extends DynamicObjectLibraryImpl.MakeSharedNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private Shape newShape_;

        private MakeSharedNodeGen() {
        }

        @Override
        void execute(DynamicObject arg0Value, Shape arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                DynamicObjectLibraryImpl.MakeSharedNode.doCached(arg0Value, arg1Value, this.newShape_);
                return;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arg0Value, arg1Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void executeAndSpecialize(DynamicObject arg0Value, Shape arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.newShape_ = DynamicObjectLibraryImpl.MakeSharedNode.makeSharedShape(arg1Value);
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                DynamicObjectLibraryImpl.MakeSharedNode.doCached(arg0Value, arg1Value, this.newShape_);
                return;
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

        public static DynamicObjectLibraryImpl.MakeSharedNode create() {
            return new MakeSharedNodeGen();
        }

        public static DynamicObjectLibraryImpl.MakeSharedNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=DynamicObjectLibraryImpl.MakeSharedNode.class)
        @DenyReplace
        private static final class Uncached
        extends DynamicObjectLibraryImpl.MakeSharedNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            void execute(DynamicObject arg0Value, Shape arg1Value) {
                DynamicObjectLibraryImpl.MakeSharedNode.doCached(arg0Value, arg1Value, DynamicObjectLibraryImpl.MakeSharedNode.makeSharedShape(arg1Value));
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.MEGAMORPHIC;
            }

            @Override
            public boolean isAdoptable() {
                return false;
            }
        }
    }

    @GeneratedBy(value=DynamicObjectLibraryImpl.SetDynamicTypeNode.class)
    static final class SetDynamicTypeNodeGen
    extends DynamicObjectLibraryImpl.SetDynamicTypeNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @CompilerDirectives.CompilationFinal
        private CachedData cached_cache;

        private SetDynamicTypeNodeGen() {
        }

        @Override
        @ExplodeLoop
        boolean execute(DynamicObject arg0Value, Shape arg1Value, Object arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    CachedData s0_ = this.cached_cache;
                    while (s0_ != null) {
                        if (arg2Value == s0_.newObjectType_) {
                            return DynamicObjectLibraryImpl.SetDynamicTypeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.newObjectType_, s0_.newShape_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    return DynamicObjectLibraryImpl.SetDynamicTypeNode.doUncached(arg0Value, arg1Value, arg2Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private boolean executeAndSpecialize(DynamicObject arg0Value, Shape arg1Value, Object arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (exclude == 0) {
                    int count0_ = 0;
                    CachedData s0_ = this.cached_cache;
                    if ((state_0 & 1) != 0) {
                        while (s0_ != null && arg2Value != s0_.newObjectType_) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && count0_ < 3) {
                        s0_ = new CachedData(this.cached_cache);
                        s0_.newObjectType_ = arg2Value;
                        s0_.newShape_ = DynamicObjectLibraryImpl.SetDynamicTypeNode.shapeSetDynamicType(arg1Value, s0_.newObjectType_);
                        VarHandle.storeStoreFence();
                        this.cached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        boolean bl = DynamicObjectLibraryImpl.SetDynamicTypeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.newObjectType_, s0_.newShape_);
                        return bl;
                    }
                }
                this.exclude_ = exclude |= 1;
                this.cached_cache = null;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                boolean bl = DynamicObjectLibraryImpl.SetDynamicTypeNode.doUncached(arg0Value, arg1Value, arg2Value);
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
            CachedData s0_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached_cache) == null || s0_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static DynamicObjectLibraryImpl.SetDynamicTypeNode create() {
            return new SetDynamicTypeNodeGen();
        }

        public static DynamicObjectLibraryImpl.SetDynamicTypeNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=DynamicObjectLibraryImpl.SetDynamicTypeNode.class)
        @DenyReplace
        private static final class Uncached
        extends DynamicObjectLibraryImpl.SetDynamicTypeNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            boolean execute(DynamicObject arg0Value, Shape arg1Value, Object arg2Value) {
                return DynamicObjectLibraryImpl.SetDynamicTypeNode.doUncached(arg0Value, arg1Value, arg2Value);
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.MEGAMORPHIC;
            }

            @Override
            public boolean isAdoptable() {
                return false;
            }
        }

        @GeneratedBy(value=DynamicObjectLibraryImpl.SetDynamicTypeNode.class)
        private static final class CachedData {
            @CompilerDirectives.CompilationFinal
            CachedData next_;
            @CompilerDirectives.CompilationFinal
            Object newObjectType_;
            @CompilerDirectives.CompilationFinal
            Shape newShape_;

            CachedData(CachedData next_) {
                this.next_ = next_;
            }
        }
    }

    @GeneratedBy(value=DynamicObjectLibraryImpl.SetFlagsNode.class)
    static final class SetFlagsNodeGen
    extends DynamicObjectLibraryImpl.SetFlagsNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @CompilerDirectives.CompilationFinal
        private CachedData cached_cache;

        private SetFlagsNodeGen() {
        }

        @Override
        @ExplodeLoop
        boolean execute(DynamicObject arg0Value, Shape arg1Value, int arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    CachedData s0_ = this.cached_cache;
                    while (s0_ != null) {
                        if (arg2Value == s0_.newFlags_) {
                            return DynamicObjectLibraryImpl.SetFlagsNode.doCached(arg0Value, arg1Value, arg2Value, s0_.newFlags_, s0_.newShape_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    return DynamicObjectLibraryImpl.SetFlagsNode.doUncached(arg0Value, arg1Value, arg2Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private boolean executeAndSpecialize(DynamicObject arg0Value, Shape arg1Value, int arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (exclude == 0) {
                    int count0_ = 0;
                    CachedData s0_ = this.cached_cache;
                    if ((state_0 & 1) != 0) {
                        while (s0_ != null && arg2Value != s0_.newFlags_) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && count0_ < 3) {
                        s0_ = new CachedData(this.cached_cache);
                        s0_.newFlags_ = arg2Value;
                        s0_.newShape_ = DynamicObjectLibraryImpl.SetFlagsNode.shapeSetFlags(arg1Value, s0_.newFlags_);
                        VarHandle.storeStoreFence();
                        this.cached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        boolean bl = DynamicObjectLibraryImpl.SetFlagsNode.doCached(arg0Value, arg1Value, arg2Value, s0_.newFlags_, s0_.newShape_);
                        return bl;
                    }
                }
                this.exclude_ = exclude |= 1;
                this.cached_cache = null;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                boolean bl = DynamicObjectLibraryImpl.SetFlagsNode.doUncached(arg0Value, arg1Value, arg2Value);
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
            CachedData s0_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached_cache) == null || s0_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static DynamicObjectLibraryImpl.SetFlagsNode create() {
            return new SetFlagsNodeGen();
        }

        public static DynamicObjectLibraryImpl.SetFlagsNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=DynamicObjectLibraryImpl.SetFlagsNode.class)
        @DenyReplace
        private static final class Uncached
        extends DynamicObjectLibraryImpl.SetFlagsNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            boolean execute(DynamicObject arg0Value, Shape arg1Value, int arg2Value) {
                return DynamicObjectLibraryImpl.SetFlagsNode.doUncached(arg0Value, arg1Value, arg2Value);
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.MEGAMORPHIC;
            }

            @Override
            public boolean isAdoptable() {
                return false;
            }
        }

        @GeneratedBy(value=DynamicObjectLibraryImpl.SetFlagsNode.class)
        private static final class CachedData {
            @CompilerDirectives.CompilationFinal
            CachedData next_;
            @CompilerDirectives.CompilationFinal
            int newFlags_;
            @CompilerDirectives.CompilationFinal
            Shape newShape_;

            CachedData(CachedData next_) {
                this.next_ = next_;
            }
        }
    }
}

