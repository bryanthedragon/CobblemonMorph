/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.host.HostContext;
import com.oracle.truffle.host.HostTargetMapping;
import com.oracle.truffle.host.HostTargetMappingNode;
import com.oracle.truffle.host.HostToTypeNode;
import com.oracle.truffle.host.HostToTypeNodeGen;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=HostTargetMappingNode.class)
final class HostTargetMappingNodeGen
extends HostTargetMappingNode {
    private static final Uncached UNCACHED = new Uncached();
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private CachedData cached_cache;

    private HostTargetMappingNodeGen() {
    }

    @Override
    Object execute(Object arg0Value, Class<?> arg1Value, HostContext arg2Value, InteropLibrary arg3Value, boolean arg4Value, int arg5Value, int arg6Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            CachedData s0_;
            if ((state_0 & 1) != 0 && (s0_ = this.cached_cache) != null && arg1Value != null) {
                return this.doCached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s0_.mappings_, s0_.mappingNodes_);
            }
            if ((state_0 & 2) != 0) {
                return this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object executeAndSpecialize(Object arg0Value, Class<?> arg1Value, HostContext arg2Value, InteropLibrary arg3Value, boolean arg4Value, int arg5Value, int arg6Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0 && arg1Value != null) {
                CachedData s0_ = super.insert(new CachedData());
                s0_.mappings_ = HostTargetMappingNode.getMappings(arg2Value, arg1Value);
                s0_.mappingNodes_ = (HostTargetMappingNode.SingleMappingNode[])s0_.insertAccessor(HostTargetMappingNode.createMappingNodes(s0_.mappings_));
                VarHandle.storeStoreFence();
                this.cached_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.doCached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s0_.mappings_, s0_.mappingNodes_);
                return object;
            }
            this.exclude_ = exclude |= 1;
            this.cached_cache = null;
            state_0 &= 0xFFFFFFFE;
            this.state_0_ = state_0 |= 2;
            lock.unlock();
            hasLock = false;
            Object object = this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
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
        if ((state_0 & state_0 - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    public static HostTargetMappingNode create() {
        return new HostTargetMappingNodeGen();
    }

    public static HostTargetMappingNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=HostTargetMappingNode.SingleMappingNode.class)
    static final class SingleMappingNodeGen
    extends HostTargetMappingNode.SingleMappingNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private DefaultData default_cache;

        private SingleMappingNodeGen() {
        }

        @Override
        Object execute(Object arg0Value, HostTargetMapping arg1Value, HostContext arg2Value, InteropLibrary arg3Value, boolean arg4Value) {
            DefaultData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.default_cache) != null) {
                return this.doDefault(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.acceptsProfile_, s0_.allowsImplementation_, s0_.toHostRecursive_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(Object arg0Value, HostTargetMapping arg1Value, HostContext arg2Value, InteropLibrary arg3Value, boolean arg4Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                DefaultData s0_ = super.insert(new DefaultData());
                s0_.acceptsProfile_ = ConditionProfile.create();
                s0_.allowsImplementation_ = HostTargetMappingNode.SingleMappingNode.allowsImplementation(arg2Value, arg1Value.sourceType);
                s0_.toHostRecursive_ = s0_.insertAccessor(HostToTypeNodeGen.create());
                VarHandle.storeStoreFence();
                this.default_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.doDefault(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.acceptsProfile_, s0_.allowsImplementation_, s0_.toHostRecursive_);
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

        public static HostTargetMappingNode.SingleMappingNode create() {
            return new SingleMappingNodeGen();
        }

        public static HostTargetMappingNode.SingleMappingNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=HostTargetMappingNode.SingleMappingNode.class)
        @DenyReplace
        private static final class Uncached
        extends HostTargetMappingNode.SingleMappingNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            Object execute(Object arg0Value, HostTargetMapping arg1Value, HostContext arg2Value, InteropLibrary arg3Value, boolean arg4Value) {
                return this.doDefault(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, ConditionProfile.getUncached(), HostTargetMappingNode.SingleMappingNode.allowsImplementation(arg2Value, arg1Value.sourceType), HostToTypeNodeGen.getUncached());
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

        @GeneratedBy(value=HostTargetMappingNode.SingleMappingNode.class)
        private static final class DefaultData
        extends Node {
            @CompilerDirectives.CompilationFinal
            ConditionProfile acceptsProfile_;
            @CompilerDirectives.CompilationFinal
            boolean allowsImplementation_;
            @Node.Child
            HostToTypeNode toHostRecursive_;

            DefaultData() {
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

    @GeneratedBy(value=HostTargetMappingNode.class)
    @DenyReplace
    private static final class Uncached
    extends HostTargetMappingNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        Object execute(Object arg0Value, Class<?> arg1Value, HostContext arg2Value, InteropLibrary arg3Value, boolean arg4Value, int arg5Value, int arg6Value) {
            return this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
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

    @GeneratedBy(value=HostTargetMappingNode.class)
    private static final class CachedData
    extends Node {
        @CompilerDirectives.CompilationFinal(dimensions=1)
        HostTargetMapping[] mappings_;
        @Node.Children
        HostTargetMappingNode.SingleMappingNode[] mappingNodes_;

        CachedData() {
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.NONE;
        }

        <T extends Node> T[] insertAccessor(T[] node) {
            return super.insert(node);
        }
    }
}

