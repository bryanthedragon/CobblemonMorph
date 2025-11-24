/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.host.HostContext;
import com.oracle.truffle.host.HostContextFactory;
import com.oracle.truffle.host.HostFieldDesc;
import com.oracle.truffle.host.HostMethodDesc;
import com.oracle.truffle.host.HostObject;
import com.oracle.truffle.host.HostToTypeNode;
import com.oracle.truffle.host.HostToTypeNodeGen;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=HostObject.class)
final class HostObjectFactory {
    HostObjectFactory() {
    }

    @GeneratedBy(value=HostObject.IsMapEntryNode.class)
    static final class IsMapEntryNodeGen
    extends HostObject.IsMapEntryNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private boolean default_isMapAccess_;

        private IsMapEntryNodeGen() {
        }

        @Override
        public boolean execute(HostObject arg0Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arg0Value.obj == null) {
                    return this.doNull(arg0Value);
                }
                if ((state_0 & 2) != 0 && arg0Value.obj != null) {
                    return this.doDefault(arg0Value, this.default_isMapAccess_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
        }

        private boolean executeAndSpecialize(HostObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arg0Value.obj == null) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doNull(arg0Value);
                    return bl;
                }
                if (arg0Value.obj != null) {
                    this.default_isMapAccess_ = arg0Value.getHostClassCache().isMapAccess();
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doDefault(arg0Value, this.default_isMapAccess_);
                    return bl;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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
            if ((state_0 & state_0 - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static HostObject.IsMapEntryNode create() {
            return new IsMapEntryNodeGen();
        }

        public static HostObject.IsMapEntryNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=HostObject.IsMapEntryNode.class)
        @DenyReplace
        private static final class Uncached
        extends HostObject.IsMapEntryNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean execute(HostObject arg0Value) {
                if (arg0Value.obj == null) {
                    return this.doNull(arg0Value);
                }
                if (arg0Value.obj != null) {
                    return this.doDefault(arg0Value, arg0Value.getHostClassCache().isMapAccess());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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

    @GeneratedBy(value=HostObject.ContainsKeyNode.class)
    static final class ContainsKeyNodeGen
    extends HostObject.ContainsKeyNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private HostObject.IsMapNode isMap;
        @Node.Child
        private HostToTypeNode map_toHost_;
        @CompilerDirectives.CompilationFinal
        private BranchProfile map_error_;

        private ContainsKeyNodeGen() {
        }

        @Override
        public boolean execute(HostObject arg0Value, Object arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && this.isMap.execute(arg0Value)) {
                    return HostObject.ContainsKeyNode.doMap(arg0Value, arg1Value, this.isMap, this.map_toHost_, this.map_error_);
                }
                if ((state_0 & 2) != 0 && !this.isMap.execute(arg0Value)) {
                    return HostObject.ContainsKeyNode.doNotMap(arg0Value, arg1Value, this.isMap);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        private boolean executeAndSpecialize(HostObject arg0Value, Object arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                HostObject.IsMapNode notMap_isMap__;
                HostObject.IsMapNode map_isMap__2;
                int state_0 = this.state_0_;
                boolean Map_duplicateFound_ = false;
                if ((state_0 & 1) != 0 && this.isMap.execute(arg0Value)) {
                    Map_duplicateFound_ = true;
                }
                if (!Map_duplicateFound_ && (map_isMap__2 = super.insert(this.isMap == null ? IsMapNodeGen.create() : this.isMap)).execute(arg0Value) && (state_0 & 1) == 0) {
                    if (this.isMap == null) {
                        HostObject.IsMapNode map_isMap___check = super.insert(map_isMap__2);
                        if (map_isMap___check == null) {
                            throw new AssertionError((Object)"Specialization 'doMap(HostObject, Object, IsMapNode, HostToTypeNode, BranchProfile)' contains a shared cache with name 'isMap' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.isMap = map_isMap___check;
                    }
                    this.map_toHost_ = super.insert(HostToTypeNodeGen.create());
                    this.map_error_ = BranchProfile.create();
                    this.state_0_ = state_0 |= 1;
                    Map_duplicateFound_ = true;
                }
                if (Map_duplicateFound_) {
                    lock.unlock();
                    hasLock = false;
                    boolean map_isMap__2 = HostObject.ContainsKeyNode.doMap(arg0Value, arg1Value, this.isMap, this.map_toHost_, this.map_error_);
                    return map_isMap__2;
                }
                boolean NotMap_duplicateFound_ = false;
                if ((state_0 & 2) != 0 && !this.isMap.execute(arg0Value)) {
                    NotMap_duplicateFound_ = true;
                }
                if (!NotMap_duplicateFound_ && !(notMap_isMap__ = super.insert(this.isMap == null ? IsMapNodeGen.create() : this.isMap)).execute(arg0Value) && (state_0 & 2) == 0) {
                    if (this.isMap == null) {
                        HostObject.IsMapNode notMap_isMap___check = super.insert(notMap_isMap__);
                        if (notMap_isMap___check == null) {
                            throw new AssertionError((Object)"Specialization 'doNotMap(HostObject, Object, IsMapNode)' contains a shared cache with name 'isMap' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.isMap = notMap_isMap___check;
                    }
                    this.state_0_ = state_0 |= 2;
                    NotMap_duplicateFound_ = true;
                }
                if (NotMap_duplicateFound_) {
                    lock.unlock();
                    hasLock = false;
                    boolean bl = HostObject.ContainsKeyNode.doNotMap(arg0Value, arg1Value, this.isMap);
                    return bl;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
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
            if ((state_0 & state_0 - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static HostObject.ContainsKeyNode create() {
            return new ContainsKeyNodeGen();
        }

        public static HostObject.ContainsKeyNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=HostObject.ContainsKeyNode.class)
        @DenyReplace
        private static final class Uncached
        extends HostObject.ContainsKeyNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean execute(HostObject arg0Value, Object arg1Value) {
                if (IsMapNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.ContainsKeyNode.doMap(arg0Value, arg1Value, IsMapNodeGen.getUncached(), HostToTypeNodeGen.getUncached(), BranchProfile.getUncached());
                }
                if (!IsMapNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.ContainsKeyNode.doNotMap(arg0Value, arg1Value, IsMapNodeGen.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
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

    @GeneratedBy(value=HostObject.IsMapNode.class)
    static final class IsMapNodeGen
    extends HostObject.IsMapNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private boolean default_isMapAccess_;

        private IsMapNodeGen() {
        }

        @Override
        public boolean execute(HostObject arg0Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arg0Value.obj == null) {
                    return this.doNull(arg0Value);
                }
                if ((state_0 & 2) != 0 && arg0Value.obj != null) {
                    return this.doDefault(arg0Value, this.default_isMapAccess_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
        }

        private boolean executeAndSpecialize(HostObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arg0Value.obj == null) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doNull(arg0Value);
                    return bl;
                }
                if (arg0Value.obj != null) {
                    this.default_isMapAccess_ = arg0Value.getHostClassCache().isMapAccess();
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doDefault(arg0Value, this.default_isMapAccess_);
                    return bl;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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
            if ((state_0 & state_0 - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static HostObject.IsMapNode create() {
            return new IsMapNodeGen();
        }

        public static HostObject.IsMapNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=HostObject.IsMapNode.class)
        @DenyReplace
        private static final class Uncached
        extends HostObject.IsMapNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean execute(HostObject arg0Value) {
                if (arg0Value.obj == null) {
                    return this.doNull(arg0Value);
                }
                if (arg0Value.obj != null) {
                    return this.doDefault(arg0Value, arg0Value.getHostClassCache().isMapAccess());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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

    @GeneratedBy(value=HostObject.IsIteratorNode.class)
    static final class IsIteratorNodeGen
    extends HostObject.IsIteratorNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private boolean default_isIteratorAccess_;

        private IsIteratorNodeGen() {
        }

        @Override
        public boolean execute(HostObject arg0Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arg0Value.obj == null) {
                    return this.doNull(arg0Value);
                }
                if ((state_0 & 2) != 0 && arg0Value.obj != null) {
                    return this.doDefault(arg0Value, this.default_isIteratorAccess_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
        }

        private boolean executeAndSpecialize(HostObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arg0Value.obj == null) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doNull(arg0Value);
                    return bl;
                }
                if (arg0Value.obj != null) {
                    this.default_isIteratorAccess_ = arg0Value.getHostClassCache().isIteratorAccess();
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doDefault(arg0Value, this.default_isIteratorAccess_);
                    return bl;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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
            if ((state_0 & state_0 - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static HostObject.IsIteratorNode create() {
            return new IsIteratorNodeGen();
        }

        public static HostObject.IsIteratorNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=HostObject.IsIteratorNode.class)
        @DenyReplace
        private static final class Uncached
        extends HostObject.IsIteratorNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean execute(HostObject arg0Value) {
                if (arg0Value.obj == null) {
                    return this.doNull(arg0Value);
                }
                if (arg0Value.obj != null) {
                    return this.doDefault(arg0Value, arg0Value.getHostClassCache().isIteratorAccess());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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

    @GeneratedBy(value=HostObject.IsIterableNode.class)
    static final class IsIterableNodeGen
    extends HostObject.IsIterableNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private boolean default_isIterableAccess_;

        private IsIterableNodeGen() {
        }

        @Override
        public boolean execute(HostObject arg0Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arg0Value.obj == null) {
                    return this.doNull(arg0Value);
                }
                if ((state_0 & 2) != 0 && arg0Value.obj != null) {
                    return this.doDefault(arg0Value, this.default_isIterableAccess_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
        }

        private boolean executeAndSpecialize(HostObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arg0Value.obj == null) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doNull(arg0Value);
                    return bl;
                }
                if (arg0Value.obj != null) {
                    this.default_isIterableAccess_ = arg0Value.getHostClassCache().isIterableAccess();
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doDefault(arg0Value, this.default_isIterableAccess_);
                    return bl;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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
            if ((state_0 & state_0 - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static HostObject.IsIterableNode create() {
            return new IsIterableNodeGen();
        }

        public static HostObject.IsIterableNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=HostObject.IsIterableNode.class)
        @DenyReplace
        private static final class Uncached
        extends HostObject.IsIterableNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean execute(HostObject arg0Value) {
                if (arg0Value.obj == null) {
                    return this.doNull(arg0Value);
                }
                if (arg0Value.obj != null) {
                    return this.doDefault(arg0Value, arg0Value.getHostClassCache().isIterableAccess());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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

    @GeneratedBy(value=HostObject.IsBufferNode.class)
    static final class IsBufferNodeGen
    extends HostObject.IsBufferNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private boolean default_isBufferAccess_;

        private IsBufferNodeGen() {
        }

        @Override
        public boolean execute(HostObject arg0Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arg0Value.obj == null) {
                    return this.doNull(arg0Value);
                }
                if ((state_0 & 2) != 0 && arg0Value.obj != null) {
                    return this.doDefault(arg0Value, this.default_isBufferAccess_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
        }

        private boolean executeAndSpecialize(HostObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arg0Value.obj == null) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doNull(arg0Value);
                    return bl;
                }
                if (arg0Value.obj != null) {
                    this.default_isBufferAccess_ = arg0Value.getHostClassCache().isBufferAccess();
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doDefault(arg0Value, this.default_isBufferAccess_);
                    return bl;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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
            if ((state_0 & state_0 - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static HostObject.IsBufferNode create() {
            return new IsBufferNodeGen();
        }

        public static HostObject.IsBufferNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=HostObject.IsBufferNode.class)
        @DenyReplace
        private static final class Uncached
        extends HostObject.IsBufferNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean execute(HostObject arg0Value) {
                if (arg0Value.obj == null) {
                    return this.doNull(arg0Value);
                }
                if (arg0Value.obj != null) {
                    return this.doDefault(arg0Value, arg0Value.getHostClassCache().isBufferAccess());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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

    @GeneratedBy(value=HostObject.IsArrayNode.class)
    static final class IsArrayNodeGen
    extends HostObject.IsArrayNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private boolean default_isArrayAccess_;

        private IsArrayNodeGen() {
        }

        @Override
        public boolean execute(HostObject arg0Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arg0Value.obj == null) {
                    return this.doNull(arg0Value);
                }
                if ((state_0 & 2) != 0 && arg0Value.obj != null) {
                    return this.doDefault(arg0Value, this.default_isArrayAccess_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
        }

        private boolean executeAndSpecialize(HostObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arg0Value.obj == null) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doNull(arg0Value);
                    return bl;
                }
                if (arg0Value.obj != null) {
                    this.default_isArrayAccess_ = arg0Value.getHostClassCache().isArrayAccess();
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doDefault(arg0Value, this.default_isArrayAccess_);
                    return bl;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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
            if ((state_0 & state_0 - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static HostObject.IsArrayNode create() {
            return new IsArrayNodeGen();
        }

        public static HostObject.IsArrayNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=HostObject.IsArrayNode.class)
        @DenyReplace
        private static final class Uncached
        extends HostObject.IsArrayNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean execute(HostObject arg0Value) {
                if (arg0Value.obj == null) {
                    return this.doNull(arg0Value);
                }
                if (arg0Value.obj != null) {
                    return this.doDefault(arg0Value, arg0Value.getHostClassCache().isArrayAccess());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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

    @GeneratedBy(value=HostObject.IsListNode.class)
    static final class IsListNodeGen
    extends HostObject.IsListNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private boolean default_isListAccess_;

        private IsListNodeGen() {
        }

        @Override
        public boolean execute(HostObject arg0Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arg0Value.obj == null) {
                    return this.doNull(arg0Value);
                }
                if ((state_0 & 2) != 0 && arg0Value.obj != null) {
                    return this.doDefault(arg0Value, this.default_isListAccess_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
        }

        private boolean executeAndSpecialize(HostObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arg0Value.obj == null) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doNull(arg0Value);
                    return bl;
                }
                if (arg0Value.obj != null) {
                    this.default_isListAccess_ = arg0Value.getHostClassCache().isListAccess();
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doDefault(arg0Value, this.default_isListAccess_);
                    return bl;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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
            if ((state_0 & state_0 - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static HostObject.IsListNode create() {
            return new IsListNodeGen();
        }

        public static HostObject.IsListNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=HostObject.IsListNode.class)
        @DenyReplace
        private static final class Uncached
        extends HostObject.IsListNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean execute(HostObject arg0Value) {
                if (arg0Value.obj == null) {
                    return this.doNull(arg0Value);
                }
                if (arg0Value.obj != null) {
                    return this.doDefault(arg0Value, arg0Value.getHostClassCache().isListAccess());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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

    @GeneratedBy(value=HostObject.WriteFieldNode.class)
    static final class WriteFieldNodeGen
    extends HostObject.WriteFieldNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private CachedData cached_cache;
        @Node.Child
        private HostToTypeNode uncached_toHost_;

        private WriteFieldNodeGen() {
        }

        @Override
        @ExplodeLoop
        public void execute(HostFieldDesc arg0Value, HostObject arg1Value, Object arg2Value) throws UnsupportedTypeException, UnknownIdentifierException {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    CachedData s0_ = this.cached_cache;
                    while (s0_ != null) {
                        if (arg0Value == s0_.cachedField_) {
                            HostObject.WriteFieldNode.doCached(arg0Value, arg1Value, arg2Value, s0_.cachedField_, s0_.toHost_, s0_.error_);
                            return;
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    HostObject.WriteFieldNode.doUncached(arg0Value, arg1Value, arg2Value, this.uncached_toHost_);
                    return;
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void executeAndSpecialize(HostFieldDesc arg0Value, HostObject arg1Value, Object arg2Value) throws UnsupportedTypeException, UnknownIdentifierException {
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
                        while (s0_ != null && arg0Value != s0_.cachedField_) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && count0_ < 3) {
                        s0_ = super.insert(new CachedData(this.cached_cache));
                        s0_.cachedField_ = arg0Value;
                        s0_.toHost_ = s0_.insertAccessor(HostToTypeNodeGen.create());
                        s0_.error_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.cached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        HostObject.WriteFieldNode.doCached(arg0Value, arg1Value, arg2Value, s0_.cachedField_, s0_.toHost_, s0_.error_);
                        return;
                    }
                }
                this.uncached_toHost_ = super.insert(HostToTypeNodeGen.create());
                this.exclude_ = exclude |= 1;
                this.cached_cache = null;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                HostObject.WriteFieldNode.doUncached(arg0Value, arg1Value, arg2Value, this.uncached_toHost_);
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

        public static HostObject.WriteFieldNode create() {
            return new WriteFieldNodeGen();
        }

        public static HostObject.WriteFieldNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=HostObject.WriteFieldNode.class)
        @DenyReplace
        private static final class Uncached
        extends HostObject.WriteFieldNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void execute(HostFieldDesc arg0Value, HostObject arg1Value, Object arg2Value) throws UnsupportedTypeException, UnknownIdentifierException {
                HostObject.WriteFieldNode.doUncached(arg0Value, arg1Value, arg2Value, HostToTypeNodeGen.getUncached());
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

        @GeneratedBy(value=HostObject.WriteFieldNode.class)
        private static final class CachedData
        extends Node {
            @Node.Child
            CachedData next_;
            @CompilerDirectives.CompilationFinal
            HostFieldDesc cachedField_;
            @Node.Child
            HostToTypeNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile error_;

            CachedData(CachedData next_) {
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

    @GeneratedBy(value=HostObject.ReadFieldNode.class)
    static final class ReadFieldNodeGen
    extends HostObject.ReadFieldNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private CachedData cached_cache;
        @Node.Child
        private HostContext.ToGuestValueNode uncached_toGuest_;

        private ReadFieldNodeGen() {
        }

        @Override
        @ExplodeLoop
        public Object execute(HostFieldDesc arg0Value, HostObject arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    CachedData s0_ = this.cached_cache;
                    while (s0_ != null) {
                        if (arg0Value == s0_.cachedField_) {
                            return HostObject.ReadFieldNode.doCached(arg0Value, arg1Value, s0_.cachedField_, s0_.toGuest_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    return HostObject.ReadFieldNode.doUncached(arg0Value, arg1Value, this.uncached_toGuest_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(HostFieldDesc arg0Value, HostObject arg1Value) {
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
                        while (s0_ != null && arg0Value != s0_.cachedField_) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && count0_ < 3) {
                        s0_ = super.insert(new CachedData(this.cached_cache));
                        s0_.cachedField_ = arg0Value;
                        s0_.toGuest_ = s0_.insertAccessor(HostContextFactory.ToGuestValueNodeGen.create());
                        VarHandle.storeStoreFence();
                        this.cached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        Object object = HostObject.ReadFieldNode.doCached(arg0Value, arg1Value, s0_.cachedField_, s0_.toGuest_);
                        return object;
                    }
                }
                this.uncached_toGuest_ = super.insert(HostContextFactory.ToGuestValueNodeGen.create());
                this.exclude_ = exclude |= 1;
                this.cached_cache = null;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                Object object = HostObject.ReadFieldNode.doUncached(arg0Value, arg1Value, this.uncached_toGuest_);
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

        public static HostObject.ReadFieldNode create() {
            return new ReadFieldNodeGen();
        }

        public static HostObject.ReadFieldNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=HostObject.ReadFieldNode.class)
        @DenyReplace
        private static final class Uncached
        extends HostObject.ReadFieldNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object execute(HostFieldDesc arg0Value, HostObject arg1Value) {
                return HostObject.ReadFieldNode.doUncached(arg0Value, arg1Value, HostContextFactory.ToGuestValueNodeGen.getUncached());
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

        @GeneratedBy(value=HostObject.ReadFieldNode.class)
        private static final class CachedData
        extends Node {
            @Node.Child
            CachedData next_;
            @CompilerDirectives.CompilationFinal
            HostFieldDesc cachedField_;
            @Node.Child
            HostContext.ToGuestValueNode toGuest_;

            CachedData(CachedData next_) {
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

    @GeneratedBy(value=HostObject.LookupMethodNode.class)
    static final class LookupMethodNodeGen
    extends HostObject.LookupMethodNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @CompilerDirectives.CompilationFinal
        private CachedData cached_cache;

        private LookupMethodNodeGen() {
        }

        @Override
        @ExplodeLoop
        public HostMethodDesc execute(HostObject arg0Value, Class<?> arg1Value, String arg2Value, boolean arg3Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    CachedData s0_ = this.cached_cache;
                    while (s0_ != null) {
                        if (arg3Value == s0_.cachedStatic_ && arg1Value == s0_.cachedClazz_ && s0_.cachedName_.equals(arg2Value)) {
                            return this.doCached(arg0Value, arg1Value, arg2Value, arg3Value, s0_.cachedStatic_, s0_.cachedClazz_, s0_.cachedName_, s0_.cachedMethod_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    return this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private HostMethodDesc executeAndSpecialize(HostObject arg0Value, Class<?> arg1Value, String arg2Value, boolean arg3Value) {
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
                        while (!(s0_ == null || arg3Value == s0_.cachedStatic_ && arg1Value == s0_.cachedClazz_ && s0_.cachedName_.equals(arg2Value))) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && count0_ < 3) {
                        s0_ = new CachedData(this.cached_cache);
                        s0_.cachedStatic_ = arg3Value;
                        s0_.cachedClazz_ = arg1Value;
                        s0_.cachedName_ = arg2Value;
                        s0_.cachedMethod_ = this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value);
                        VarHandle.storeStoreFence();
                        this.cached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        HostMethodDesc hostMethodDesc = this.doCached(arg0Value, arg1Value, arg2Value, arg3Value, s0_.cachedStatic_, s0_.cachedClazz_, s0_.cachedName_, s0_.cachedMethod_);
                        return hostMethodDesc;
                    }
                }
                this.exclude_ = exclude |= 1;
                this.cached_cache = null;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                HostMethodDesc hostMethodDesc = this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value);
                return hostMethodDesc;
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

        public static HostObject.LookupMethodNode create() {
            return new LookupMethodNodeGen();
        }

        public static HostObject.LookupMethodNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=HostObject.LookupMethodNode.class)
        @DenyReplace
        private static final class Uncached
        extends HostObject.LookupMethodNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public HostMethodDesc execute(HostObject arg0Value, Class<?> arg1Value, String arg2Value, boolean arg3Value) {
                return this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value);
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

        @GeneratedBy(value=HostObject.LookupMethodNode.class)
        private static final class CachedData {
            @CompilerDirectives.CompilationFinal
            CachedData next_;
            @CompilerDirectives.CompilationFinal
            boolean cachedStatic_;
            @CompilerDirectives.CompilationFinal
            Class<?> cachedClazz_;
            @CompilerDirectives.CompilationFinal
            String cachedName_;
            @CompilerDirectives.CompilationFinal
            HostMethodDesc cachedMethod_;

            CachedData(CachedData next_) {
                this.next_ = next_;
            }
        }
    }

    @GeneratedBy(value=HostObject.LookupInnerClassNode.class)
    static final class LookupInnerClassNodeGen
    extends HostObject.LookupInnerClassNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @CompilerDirectives.CompilationFinal
        private CachedData cached_cache;

        private LookupInnerClassNodeGen() {
        }

        @Override
        @ExplodeLoop
        public Class<?> execute(Class<?> arg0Value, String arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    CachedData s0_ = this.cached_cache;
                    while (s0_ != null) {
                        if (arg0Value == s0_.cachedClazz_ && s0_.cachedName_.equals(arg1Value)) {
                            return this.doCached(arg0Value, arg1Value, s0_.cachedClazz_, s0_.cachedName_, s0_.cachedInnerClass_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    return this.doUncached(arg0Value, arg1Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Class<?> executeAndSpecialize(Class<?> arg0Value, String arg1Value) {
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
                        while (!(s0_ == null || arg0Value == s0_.cachedClazz_ && s0_.cachedName_.equals(arg1Value))) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && count0_ < 3) {
                        s0_ = new CachedData(this.cached_cache);
                        s0_.cachedClazz_ = arg0Value;
                        s0_.cachedName_ = arg1Value;
                        s0_.cachedInnerClass_ = this.doUncached(arg0Value, arg1Value);
                        VarHandle.storeStoreFence();
                        this.cached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        Class<?> clazz = this.doCached(arg0Value, arg1Value, s0_.cachedClazz_, s0_.cachedName_, s0_.cachedInnerClass_);
                        return clazz;
                    }
                }
                this.exclude_ = exclude |= 1;
                this.cached_cache = null;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                Class<?> clazz = this.doUncached(arg0Value, arg1Value);
                return clazz;
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

        public static HostObject.LookupInnerClassNode create() {
            return new LookupInnerClassNodeGen();
        }

        public static HostObject.LookupInnerClassNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=HostObject.LookupInnerClassNode.class)
        @DenyReplace
        private static final class Uncached
        extends HostObject.LookupInnerClassNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Class<?> execute(Class<?> arg0Value, String arg1Value) {
                return this.doUncached(arg0Value, arg1Value);
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

        @GeneratedBy(value=HostObject.LookupInnerClassNode.class)
        private static final class CachedData {
            @CompilerDirectives.CompilationFinal
            CachedData next_;
            @CompilerDirectives.CompilationFinal
            Class<?> cachedClazz_;
            @CompilerDirectives.CompilationFinal
            String cachedName_;
            @CompilerDirectives.CompilationFinal
            Class<?> cachedInnerClass_;

            CachedData(CachedData next_) {
                this.next_ = next_;
            }
        }
    }

    @GeneratedBy(value=HostObject.LookupFunctionalMethodNode.class)
    static final class LookupFunctionalMethodNodeGen
    extends HostObject.LookupFunctionalMethodNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @CompilerDirectives.CompilationFinal
        private CachedData cached_cache;

        private LookupFunctionalMethodNodeGen() {
        }

        @Override
        @ExplodeLoop
        public HostMethodDesc execute(HostObject arg0Value, Class<?> arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    CachedData s0_ = this.cached_cache;
                    while (s0_ != null) {
                        if (arg1Value == s0_.cachedClazz_) {
                            return this.doCached(arg0Value, arg1Value, s0_.cachedClazz_, s0_.cachedMethod_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    return HostObject.LookupFunctionalMethodNode.doUncached(arg0Value, arg1Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private HostMethodDesc executeAndSpecialize(HostObject arg0Value, Class<?> arg1Value) {
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
                        while (s0_ != null && arg1Value != s0_.cachedClazz_) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && count0_ < 3) {
                        s0_ = new CachedData(this.cached_cache);
                        s0_.cachedClazz_ = arg1Value;
                        s0_.cachedMethod_ = HostObject.LookupFunctionalMethodNode.doUncached(arg0Value, arg1Value);
                        VarHandle.storeStoreFence();
                        this.cached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        HostMethodDesc hostMethodDesc = this.doCached(arg0Value, arg1Value, s0_.cachedClazz_, s0_.cachedMethod_);
                        return hostMethodDesc;
                    }
                }
                this.exclude_ = exclude |= 1;
                this.cached_cache = null;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                HostMethodDesc hostMethodDesc = HostObject.LookupFunctionalMethodNode.doUncached(arg0Value, arg1Value);
                return hostMethodDesc;
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

        public static HostObject.LookupFunctionalMethodNode create() {
            return new LookupFunctionalMethodNodeGen();
        }

        public static HostObject.LookupFunctionalMethodNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=HostObject.LookupFunctionalMethodNode.class)
        @DenyReplace
        private static final class Uncached
        extends HostObject.LookupFunctionalMethodNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public HostMethodDesc execute(HostObject arg0Value, Class<?> arg1Value) {
                return HostObject.LookupFunctionalMethodNode.doUncached(arg0Value, arg1Value);
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

        @GeneratedBy(value=HostObject.LookupFunctionalMethodNode.class)
        private static final class CachedData {
            @CompilerDirectives.CompilationFinal
            CachedData next_;
            @CompilerDirectives.CompilationFinal
            Class<?> cachedClazz_;
            @CompilerDirectives.CompilationFinal
            HostMethodDesc cachedMethod_;

            CachedData(CachedData next_) {
                this.next_ = next_;
            }
        }
    }

    @GeneratedBy(value=HostObject.LookupFieldNode.class)
    static final class LookupFieldNodeGen
    extends HostObject.LookupFieldNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @CompilerDirectives.CompilationFinal
        private CachedData cached_cache;

        private LookupFieldNodeGen() {
        }

        @Override
        @ExplodeLoop
        public HostFieldDesc execute(HostObject arg0Value, Class<?> arg1Value, String arg2Value, boolean arg3Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    CachedData s0_ = this.cached_cache;
                    while (s0_ != null) {
                        if (arg3Value == s0_.cachedStatic_ && arg1Value == s0_.cachedClazz_ && s0_.cachedName_.equals(arg2Value)) {
                            return this.doCached(arg0Value, arg1Value, arg2Value, arg3Value, s0_.cachedStatic_, s0_.cachedClazz_, s0_.cachedName_, s0_.cachedField_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    return this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private HostFieldDesc executeAndSpecialize(HostObject arg0Value, Class<?> arg1Value, String arg2Value, boolean arg3Value) {
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
                        while (!(s0_ == null || arg3Value == s0_.cachedStatic_ && arg1Value == s0_.cachedClazz_ && s0_.cachedName_.equals(arg2Value))) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && count0_ < 3) {
                        s0_ = new CachedData(this.cached_cache);
                        s0_.cachedStatic_ = arg3Value;
                        s0_.cachedClazz_ = arg1Value;
                        s0_.cachedName_ = arg2Value;
                        s0_.cachedField_ = this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value);
                        VarHandle.storeStoreFence();
                        this.cached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        HostFieldDesc hostFieldDesc = this.doCached(arg0Value, arg1Value, arg2Value, arg3Value, s0_.cachedStatic_, s0_.cachedClazz_, s0_.cachedName_, s0_.cachedField_);
                        return hostFieldDesc;
                    }
                }
                this.exclude_ = exclude |= 1;
                this.cached_cache = null;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                HostFieldDesc hostFieldDesc = this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value);
                return hostFieldDesc;
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

        public static HostObject.LookupFieldNode create() {
            return new LookupFieldNodeGen();
        }

        public static HostObject.LookupFieldNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=HostObject.LookupFieldNode.class)
        @DenyReplace
        private static final class Uncached
        extends HostObject.LookupFieldNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public HostFieldDesc execute(HostObject arg0Value, Class<?> arg1Value, String arg2Value, boolean arg3Value) {
                return this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value);
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

        @GeneratedBy(value=HostObject.LookupFieldNode.class)
        private static final class CachedData {
            @CompilerDirectives.CompilationFinal
            CachedData next_;
            @CompilerDirectives.CompilationFinal
            boolean cachedStatic_;
            @CompilerDirectives.CompilationFinal
            Class<?> cachedClazz_;
            @CompilerDirectives.CompilationFinal
            String cachedName_;
            @CompilerDirectives.CompilationFinal
            HostFieldDesc cachedField_;

            CachedData(CachedData next_) {
                this.next_ = next_;
            }
        }
    }

    @GeneratedBy(value=HostObject.LookupConstructorNode.class)
    static final class LookupConstructorNodeGen
    extends HostObject.LookupConstructorNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @CompilerDirectives.CompilationFinal
        private CachedData cached_cache;

        private LookupConstructorNodeGen() {
        }

        @Override
        @ExplodeLoop
        public HostMethodDesc execute(HostObject arg0Value, Class<?> arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    CachedData s0_ = this.cached_cache;
                    while (s0_ != null) {
                        if (arg1Value == s0_.cachedClazz_) {
                            return this.doCached(arg0Value, arg1Value, s0_.cachedClazz_, s0_.cachedMethod_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    return this.doUncached(arg0Value, arg1Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private HostMethodDesc executeAndSpecialize(HostObject arg0Value, Class<?> arg1Value) {
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
                        while (s0_ != null && arg1Value != s0_.cachedClazz_) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && count0_ < 3) {
                        s0_ = new CachedData(this.cached_cache);
                        s0_.cachedClazz_ = arg1Value;
                        s0_.cachedMethod_ = this.doUncached(arg0Value, arg1Value);
                        VarHandle.storeStoreFence();
                        this.cached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        HostMethodDesc hostMethodDesc = this.doCached(arg0Value, arg1Value, s0_.cachedClazz_, s0_.cachedMethod_);
                        return hostMethodDesc;
                    }
                }
                this.exclude_ = exclude |= 1;
                this.cached_cache = null;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                HostMethodDesc hostMethodDesc = this.doUncached(arg0Value, arg1Value);
                return hostMethodDesc;
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

        public static HostObject.LookupConstructorNode create() {
            return new LookupConstructorNodeGen();
        }

        public static HostObject.LookupConstructorNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=HostObject.LookupConstructorNode.class)
        @DenyReplace
        private static final class Uncached
        extends HostObject.LookupConstructorNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public HostMethodDesc execute(HostObject arg0Value, Class<?> arg1Value) {
                return this.doUncached(arg0Value, arg1Value);
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

        @GeneratedBy(value=HostObject.LookupConstructorNode.class)
        private static final class CachedData {
            @CompilerDirectives.CompilationFinal
            CachedData next_;
            @CompilerDirectives.CompilationFinal
            Class<?> cachedClazz_;
            @CompilerDirectives.CompilationFinal
            HostMethodDesc cachedMethod_;

            CachedData(CachedData next_) {
                this.next_ = next_;
            }
        }
    }

    @GeneratedBy(value=HostObject.ArrayGet.class)
    static final class ArrayGetNodeGen
    extends HostObject.ArrayGet {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private ArrayGetNodeGen() {
        }

        @Override
        protected Object execute(Object arg0Value, int arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arg0Value instanceof boolean[]) {
                    boolean[] arg0Value_ = (boolean[])arg0Value;
                    return HostObject.ArrayGet.doBoolean(arg0Value_, arg1Value);
                }
                if ((state_0 & 2) != 0 && arg0Value instanceof byte[]) {
                    byte[] arg0Value_ = (byte[])arg0Value;
                    return HostObject.ArrayGet.doByte(arg0Value_, arg1Value);
                }
                if ((state_0 & 4) != 0 && arg0Value instanceof short[]) {
                    short[] arg0Value_ = (short[])arg0Value;
                    return HostObject.ArrayGet.doShort(arg0Value_, arg1Value);
                }
                if ((state_0 & 8) != 0 && arg0Value instanceof char[]) {
                    char[] arg0Value_ = (char[])arg0Value;
                    return Character.valueOf(HostObject.ArrayGet.doChar(arg0Value_, arg1Value));
                }
                if ((state_0 & 0x10) != 0 && arg0Value instanceof int[]) {
                    int[] arg0Value_ = (int[])arg0Value;
                    return HostObject.ArrayGet.doInt(arg0Value_, arg1Value);
                }
                if ((state_0 & 0x20) != 0 && arg0Value instanceof long[]) {
                    long[] arg0Value_ = (long[])arg0Value;
                    return HostObject.ArrayGet.doLong(arg0Value_, arg1Value);
                }
                if ((state_0 & 0x40) != 0 && arg0Value instanceof float[]) {
                    float[] arg0Value_ = (float[])arg0Value;
                    return Float.valueOf(HostObject.ArrayGet.doFloat(arg0Value_, arg1Value));
                }
                if ((state_0 & 0x80) != 0 && arg0Value instanceof double[]) {
                    double[] arg0Value_ = (double[])arg0Value;
                    return HostObject.ArrayGet.doDouble(arg0Value_, arg1Value);
                }
                if ((state_0 & 0x100) != 0 && arg0Value instanceof Object[]) {
                    Object[] arg0Value_ = (Object[])arg0Value;
                    return HostObject.ArrayGet.doObject(arg0Value_, arg1Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        private Object executeAndSpecialize(Object arg0Value, int arg1Value) {
            int state_0 = this.state_0_;
            if (arg0Value instanceof boolean[]) {
                boolean[] arg0Value_ = (boolean[])arg0Value;
                this.state_0_ = state_0 |= 1;
                return HostObject.ArrayGet.doBoolean(arg0Value_, arg1Value);
            }
            if (arg0Value instanceof byte[]) {
                byte[] arg0Value_ = (byte[])arg0Value;
                this.state_0_ = state_0 |= 2;
                return HostObject.ArrayGet.doByte(arg0Value_, arg1Value);
            }
            if (arg0Value instanceof short[]) {
                short[] arg0Value_ = (short[])arg0Value;
                this.state_0_ = state_0 |= 4;
                return HostObject.ArrayGet.doShort(arg0Value_, arg1Value);
            }
            if (arg0Value instanceof char[]) {
                char[] arg0Value_ = (char[])arg0Value;
                this.state_0_ = state_0 |= 8;
                return Character.valueOf(HostObject.ArrayGet.doChar(arg0Value_, arg1Value));
            }
            if (arg0Value instanceof int[]) {
                int[] arg0Value_ = (int[])arg0Value;
                this.state_0_ = state_0 |= 0x10;
                return HostObject.ArrayGet.doInt(arg0Value_, arg1Value);
            }
            if (arg0Value instanceof long[]) {
                long[] arg0Value_ = (long[])arg0Value;
                this.state_0_ = state_0 |= 0x20;
                return HostObject.ArrayGet.doLong(arg0Value_, arg1Value);
            }
            if (arg0Value instanceof float[]) {
                float[] arg0Value_ = (float[])arg0Value;
                this.state_0_ = state_0 |= 0x40;
                return Float.valueOf(HostObject.ArrayGet.doFloat(arg0Value_, arg1Value));
            }
            if (arg0Value instanceof double[]) {
                double[] arg0Value_ = (double[])arg0Value;
                this.state_0_ = state_0 |= 0x80;
                return HostObject.ArrayGet.doDouble(arg0Value_, arg1Value);
            }
            if (arg0Value instanceof Object[]) {
                Object[] arg0Value_ = (Object[])arg0Value;
                this.state_0_ = state_0 |= 0x100;
                return HostObject.ArrayGet.doObject(arg0Value_, arg1Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
        }

        @Override
        public NodeCost getCost() {
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static HostObject.ArrayGet create() {
            return new ArrayGetNodeGen();
        }

        public static HostObject.ArrayGet getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=HostObject.ArrayGet.class)
        @DenyReplace
        private static final class Uncached
        extends HostObject.ArrayGet {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            protected Object execute(Object arg0Value, int arg1Value) {
                if (arg0Value instanceof boolean[]) {
                    boolean[] arg0Value_ = (boolean[])arg0Value;
                    return HostObject.ArrayGet.doBoolean(arg0Value_, arg1Value);
                }
                if (arg0Value instanceof byte[]) {
                    byte[] arg0Value_ = (byte[])arg0Value;
                    return HostObject.ArrayGet.doByte(arg0Value_, arg1Value);
                }
                if (arg0Value instanceof short[]) {
                    short[] arg0Value_ = (short[])arg0Value;
                    return HostObject.ArrayGet.doShort(arg0Value_, arg1Value);
                }
                if (arg0Value instanceof char[]) {
                    char[] arg0Value_ = (char[])arg0Value;
                    return Character.valueOf(HostObject.ArrayGet.doChar(arg0Value_, arg1Value));
                }
                if (arg0Value instanceof int[]) {
                    int[] arg0Value_ = (int[])arg0Value;
                    return HostObject.ArrayGet.doInt(arg0Value_, arg1Value);
                }
                if (arg0Value instanceof long[]) {
                    long[] arg0Value_ = (long[])arg0Value;
                    return HostObject.ArrayGet.doLong(arg0Value_, arg1Value);
                }
                if (arg0Value instanceof float[]) {
                    float[] arg0Value_ = (float[])arg0Value;
                    return Float.valueOf(HostObject.ArrayGet.doFloat(arg0Value_, arg1Value));
                }
                if (arg0Value instanceof double[]) {
                    double[] arg0Value_ = (double[])arg0Value;
                    return HostObject.ArrayGet.doDouble(arg0Value_, arg1Value);
                }
                if (arg0Value instanceof Object[]) {
                    Object[] arg0Value_ = (Object[])arg0Value;
                    return HostObject.ArrayGet.doObject(arg0Value_, arg1Value);
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
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

    @GeneratedBy(value=HostObject.ArraySet.class)
    static final class ArraySetNodeGen
    extends HostObject.ArraySet {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private ArraySetNodeGen() {
        }

        @Override
        protected void execute(Object arg0Value, int arg1Value, Object arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                Object[] arg0Value_;
                if ((state_0 & 1) != 0 && arg0Value instanceof boolean[]) {
                    arg0Value_ = (boolean[])arg0Value;
                    if (arg2Value instanceof Boolean) {
                        boolean arg2Value_ = (Boolean)arg2Value;
                        HostObject.ArraySet.doBoolean(arg0Value_, arg1Value, arg2Value_);
                        return;
                    }
                }
                if ((state_0 & 2) != 0 && arg0Value instanceof byte[]) {
                    arg0Value_ = (byte[])arg0Value;
                    if (arg2Value instanceof Byte) {
                        byte arg2Value_ = (Byte)arg2Value;
                        HostObject.ArraySet.doByte((byte[])arg0Value_, arg1Value, arg2Value_);
                        return;
                    }
                }
                if ((state_0 & 4) != 0 && arg0Value instanceof short[]) {
                    arg0Value_ = (short[])arg0Value;
                    if (arg2Value instanceof Short) {
                        short arg2Value_ = (Short)arg2Value;
                        HostObject.ArraySet.doShort((short[])arg0Value_, arg1Value, arg2Value_);
                        return;
                    }
                }
                if ((state_0 & 8) != 0 && arg0Value instanceof char[]) {
                    arg0Value_ = (char[])arg0Value;
                    if (arg2Value instanceof Character) {
                        char arg2Value_ = ((Character)arg2Value).charValue();
                        HostObject.ArraySet.doChar((char[])arg0Value_, arg1Value, arg2Value_);
                        return;
                    }
                }
                if ((state_0 & 0x10) != 0 && arg0Value instanceof int[]) {
                    arg0Value_ = (int[])arg0Value;
                    if (arg2Value instanceof Integer) {
                        int arg2Value_ = (Integer)arg2Value;
                        HostObject.ArraySet.doInt((int[])arg0Value_, arg1Value, arg2Value_);
                        return;
                    }
                }
                if ((state_0 & 0x20) != 0 && arg0Value instanceof long[]) {
                    arg0Value_ = (long[])arg0Value;
                    if (arg2Value instanceof Long) {
                        long arg2Value_ = (Long)arg2Value;
                        HostObject.ArraySet.doLong((long[])arg0Value_, arg1Value, arg2Value_);
                        return;
                    }
                }
                if ((state_0 & 0x40) != 0 && arg0Value instanceof float[]) {
                    arg0Value_ = (float[])arg0Value;
                    if (arg2Value instanceof Float) {
                        float arg2Value_ = ((Float)arg2Value).floatValue();
                        HostObject.ArraySet.doFloat((float[])arg0Value_, arg1Value, arg2Value_);
                        return;
                    }
                }
                if ((state_0 & 0x80) != 0 && arg0Value instanceof double[]) {
                    arg0Value_ = (double[])arg0Value;
                    if (arg2Value instanceof Double) {
                        double arg2Value_ = (Double)arg2Value;
                        HostObject.ArraySet.doDouble((double[])arg0Value_, arg1Value, arg2Value_);
                        return;
                    }
                }
                if ((state_0 & 0x100) != 0 && arg0Value instanceof Object[]) {
                    arg0Value_ = (Object[])arg0Value;
                    HostObject.ArraySet.doObject(arg0Value_, arg1Value, arg2Value);
                    return;
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        private void executeAndSpecialize(Object arg0Value, int arg1Value, Object arg2Value) {
            Object[] arg0Value_;
            int state_0 = this.state_0_;
            if (arg0Value instanceof boolean[]) {
                arg0Value_ = (boolean[])arg0Value;
                if (arg2Value instanceof Boolean) {
                    boolean arg2Value_ = (Boolean)arg2Value;
                    this.state_0_ = state_0 |= 1;
                    HostObject.ArraySet.doBoolean(arg0Value_, arg1Value, arg2Value_);
                    return;
                }
            }
            if (arg0Value instanceof byte[]) {
                arg0Value_ = (byte[])arg0Value;
                if (arg2Value instanceof Byte) {
                    byte arg2Value_ = (Byte)arg2Value;
                    this.state_0_ = state_0 |= 2;
                    HostObject.ArraySet.doByte((byte[])arg0Value_, arg1Value, arg2Value_);
                    return;
                }
            }
            if (arg0Value instanceof short[]) {
                arg0Value_ = (short[])arg0Value;
                if (arg2Value instanceof Short) {
                    short arg2Value_ = (Short)arg2Value;
                    this.state_0_ = state_0 |= 4;
                    HostObject.ArraySet.doShort((short[])arg0Value_, arg1Value, arg2Value_);
                    return;
                }
            }
            if (arg0Value instanceof char[]) {
                arg0Value_ = (char[])arg0Value;
                if (arg2Value instanceof Character) {
                    char arg2Value_ = ((Character)arg2Value).charValue();
                    this.state_0_ = state_0 |= 8;
                    HostObject.ArraySet.doChar((char[])arg0Value_, arg1Value, arg2Value_);
                    return;
                }
            }
            if (arg0Value instanceof int[]) {
                arg0Value_ = (int[])arg0Value;
                if (arg2Value instanceof Integer) {
                    int arg2Value_ = (Integer)arg2Value;
                    this.state_0_ = state_0 |= 0x10;
                    HostObject.ArraySet.doInt((int[])arg0Value_, arg1Value, arg2Value_);
                    return;
                }
            }
            if (arg0Value instanceof long[]) {
                arg0Value_ = (long[])arg0Value;
                if (arg2Value instanceof Long) {
                    long arg2Value_ = (Long)arg2Value;
                    this.state_0_ = state_0 |= 0x20;
                    HostObject.ArraySet.doLong((long[])arg0Value_, arg1Value, arg2Value_);
                    return;
                }
            }
            if (arg0Value instanceof float[]) {
                arg0Value_ = (float[])arg0Value;
                if (arg2Value instanceof Float) {
                    float arg2Value_ = ((Float)arg2Value).floatValue();
                    this.state_0_ = state_0 |= 0x40;
                    HostObject.ArraySet.doFloat((float[])arg0Value_, arg1Value, arg2Value_);
                    return;
                }
            }
            if (arg0Value instanceof double[]) {
                arg0Value_ = (double[])arg0Value;
                if (arg2Value instanceof Double) {
                    double arg2Value_ = (Double)arg2Value;
                    this.state_0_ = state_0 |= 0x80;
                    HostObject.ArraySet.doDouble((double[])arg0Value_, arg1Value, arg2Value_);
                    return;
                }
            }
            if (arg0Value instanceof Object[]) {
                arg0Value_ = (Object[])arg0Value;
                this.state_0_ = state_0 |= 0x100;
                HostObject.ArraySet.doObject(arg0Value_, arg1Value, arg2Value);
                return;
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
        }

        @Override
        public NodeCost getCost() {
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static HostObject.ArraySet create() {
            return new ArraySetNodeGen();
        }

        public static HostObject.ArraySet getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=HostObject.ArraySet.class)
        @DenyReplace
        private static final class Uncached
        extends HostObject.ArraySet {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            protected void execute(Object arg0Value, int arg1Value, Object arg2Value) {
                Object[] arg0Value_;
                if (arg0Value instanceof boolean[]) {
                    arg0Value_ = (boolean[])arg0Value;
                    if (arg2Value instanceof Boolean) {
                        boolean arg2Value_ = (Boolean)arg2Value;
                        HostObject.ArraySet.doBoolean(arg0Value_, arg1Value, arg2Value_);
                        return;
                    }
                }
                if (arg0Value instanceof byte[]) {
                    arg0Value_ = (byte[])arg0Value;
                    if (arg2Value instanceof Byte) {
                        byte arg2Value_ = (Byte)arg2Value;
                        HostObject.ArraySet.doByte((byte[])arg0Value_, arg1Value, arg2Value_);
                        return;
                    }
                }
                if (arg0Value instanceof short[]) {
                    arg0Value_ = (short[])arg0Value;
                    if (arg2Value instanceof Short) {
                        short arg2Value_ = (Short)arg2Value;
                        HostObject.ArraySet.doShort((short[])arg0Value_, arg1Value, arg2Value_);
                        return;
                    }
                }
                if (arg0Value instanceof char[]) {
                    arg0Value_ = (char[])arg0Value;
                    if (arg2Value instanceof Character) {
                        char arg2Value_ = ((Character)arg2Value).charValue();
                        HostObject.ArraySet.doChar((char[])arg0Value_, arg1Value, arg2Value_);
                        return;
                    }
                }
                if (arg0Value instanceof int[]) {
                    arg0Value_ = (int[])arg0Value;
                    if (arg2Value instanceof Integer) {
                        int arg2Value_ = (Integer)arg2Value;
                        HostObject.ArraySet.doInt((int[])arg0Value_, arg1Value, arg2Value_);
                        return;
                    }
                }
                if (arg0Value instanceof long[]) {
                    arg0Value_ = (long[])arg0Value;
                    if (arg2Value instanceof Long) {
                        long arg2Value_ = (Long)arg2Value;
                        HostObject.ArraySet.doLong((long[])arg0Value_, arg1Value, arg2Value_);
                        return;
                    }
                }
                if (arg0Value instanceof float[]) {
                    arg0Value_ = (float[])arg0Value;
                    if (arg2Value instanceof Float) {
                        float arg2Value_ = ((Float)arg2Value).floatValue();
                        HostObject.ArraySet.doFloat((float[])arg0Value_, arg1Value, arg2Value_);
                        return;
                    }
                }
                if (arg0Value instanceof double[]) {
                    arg0Value_ = (double[])arg0Value;
                    if (arg2Value instanceof Double) {
                        double arg2Value_ = (Double)arg2Value;
                        HostObject.ArraySet.doDouble((double[])arg0Value_, arg1Value, arg2Value_);
                        return;
                    }
                }
                if (arg0Value instanceof Object[]) {
                    arg0Value_ = (Object[])arg0Value;
                    HostObject.ArraySet.doObject(arg0Value_, arg1Value, arg2Value);
                    return;
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
    }
}

