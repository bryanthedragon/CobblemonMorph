/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.access.GetPrototypeNode;
import com.oracle.truffle.js.nodes.access.GetPrototypeNodeGen;
import com.oracle.truffle.js.nodes.access.IsExtensibleNode;
import com.oracle.truffle.js.nodes.access.IsExtensibleNodeGen;
import com.oracle.truffle.js.nodes.interop.KeyInfoNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNodeGen;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=KeyInfoNode.class)
public final class KeyInfoNodeGen
extends KeyInfoNode
implements Introspection.Provider {
    private static final Uncached UNCACHED = new Uncached();
    private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private CachedOwnPropertyData cachedOwnProperty_cache;
    @Node.Child
    private MemberData member_cache;

    private KeyInfoNodeGen() {
    }

    @Override
    @ExplodeLoop
    public boolean execute(JSDynamicObject arg0Value, String arg1Value, int arg2Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            MemberData s1_;
            if ((state_0 & 1) != 0) {
                CachedOwnPropertyData s0_ = this.cachedOwnProperty_cache;
                while (s0_ != null) {
                    Property property__;
                    if (s0_.objectLibrary_.accepts(arg0Value) && !JSGuards.isJSProxy(arg0Value) && (property__ = s0_.objectLibrary_.getProperty(arg0Value, arg1Value)) != null) {
                        return KeyInfoNode.cachedOwnProperty(arg0Value, arg1Value, arg2Value, s0_.objectLibrary_, property__, s0_.isCallable_, s0_.proxyBranch_, s0_.fromJavaStringNode_);
                    }
                    s0_ = s0_.next_;
                }
            }
            if ((state_0 & 2) != 0 && (s1_ = this.member_cache) != null) {
                return KeyInfoNode.member(arg0Value, arg1Value, arg2Value, s1_.getPrototype_, s1_.isCallable_, s1_.isExtensible_, s1_.fromJavaStringNode_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean executeAndSpecialize(JSDynamicObject arg0Value, String arg1Value, int arg2Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Property property__ = null;
            if (exclude == 0) {
                DynamicObjectLibrary objectLibrary__;
                int count0_ = 0;
                CachedOwnPropertyData s0_ = this.cachedOwnProperty_cache;
                if ((state_0 & 1) != 0) {
                    while (s0_ != null && (!s0_.objectLibrary_.accepts(arg0Value) || JSGuards.isJSProxy(arg0Value) || (property__ = s0_.objectLibrary_.getProperty(arg0Value, arg1Value)) == null)) {
                        s0_ = s0_.next_;
                        ++count0_;
                    }
                }
                if (s0_ == null && !JSGuards.isJSProxy(arg0Value) && (property__ = (objectLibrary__ = super.insert(DYNAMIC_OBJECT_LIBRARY_.create(arg0Value))).getProperty(arg0Value, arg1Value)) != null && count0_ < 2) {
                    s0_ = super.insert(new CachedOwnPropertyData(this.cachedOwnProperty_cache));
                    s0_.objectLibrary_ = s0_.insertAccessor(objectLibrary__);
                    s0_.isCallable_ = s0_.insertAccessor(IsCallableNode.create());
                    s0_.proxyBranch_ = BranchProfile.create();
                    s0_.fromJavaStringNode_ = s0_.insertAccessor(TruffleString.FromJavaStringNode.create());
                    VarHandle.storeStoreFence();
                    this.cachedOwnProperty_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                }
                if (s0_ != null) {
                    lock.unlock();
                    hasLock = false;
                    boolean bl = KeyInfoNode.cachedOwnProperty(arg0Value, arg1Value, arg2Value, s0_.objectLibrary_, property__, s0_.isCallable_, s0_.proxyBranch_, s0_.fromJavaStringNode_);
                    return bl;
                }
            }
            MemberData s1_ = super.insert(new MemberData());
            s1_.getPrototype_ = s1_.insertAccessor(GetPrototypeNode.create());
            s1_.isCallable_ = s1_.insertAccessor(IsCallableNode.create());
            s1_.isExtensible_ = s1_.insertAccessor(IsExtensibleNode.create());
            s1_.fromJavaStringNode_ = s1_.insertAccessor(TruffleString.FromJavaStringNode.create());
            VarHandle.storeStoreFence();
            this.member_cache = s1_;
            this.exclude_ = exclude |= 1;
            this.cachedOwnProperty_cache = null;
            state_0 &= 0xFFFFFFFE;
            this.state_0_ = state_0 |= 2;
            lock.unlock();
            hasLock = false;
            boolean bl = KeyInfoNode.member(arg0Value, arg1Value, arg2Value, s1_.getPrototype_, s1_.isCallable_, s1_.isExtensible_, s1_.fromJavaStringNode_);
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
        CachedOwnPropertyData s0_;
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cachedOwnProperty_cache) == null || s0_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Cloneable>> cached;
        Object[] data = new Object[3];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "cachedOwnProperty";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Cloneable>>();
            CachedOwnPropertyData s0_ = this.cachedOwnProperty_cache;
            while (s0_ != null) {
                cached.add(Arrays.asList(s0_.objectLibrary_, s0_.isCallable_, s0_.proxyBranch_, s0_.fromJavaStringNode_));
                s0_ = s0_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "member";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            MemberData s1_ = this.member_cache;
            if (s1_ != null) {
                cached.add(Arrays.asList(s1_.getPrototype_, s1_.isCallable_, s1_.isExtensible_, s1_.fromJavaStringNode_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        return Introspection.Provider.create(data);
    }

    public static KeyInfoNode create() {
        return new KeyInfoNodeGen();
    }

    public static KeyInfoNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=KeyInfoNode.class)
    @DenyReplace
    private static final class Uncached
    extends KeyInfoNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean execute(JSDynamicObject arg0Value, String arg1Value, int arg2Value) {
            return KeyInfoNode.member(arg0Value, arg1Value, arg2Value, GetPrototypeNodeGen.getUncached(), IsCallableNodeGen.getUncached(), IsExtensibleNodeGen.getUncached(), TruffleString.FromJavaStringNode.getUncached());
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

    @GeneratedBy(value=KeyInfoNode.class)
    private static final class MemberData
    extends Node {
        @Node.Child
        GetPrototypeNode getPrototype_;
        @Node.Child
        IsCallableNode isCallable_;
        @Node.Child
        IsExtensibleNode isExtensible_;
        @Node.Child
        TruffleString.FromJavaStringNode fromJavaStringNode_;

        MemberData() {
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.NONE;
        }

        <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
        }
    }

    @GeneratedBy(value=KeyInfoNode.class)
    private static final class CachedOwnPropertyData
    extends Node {
        @Node.Child
        CachedOwnPropertyData next_;
        @Node.Child
        DynamicObjectLibrary objectLibrary_;
        @Node.Child
        IsCallableNode isCallable_;
        @CompilerDirectives.CompilationFinal
        BranchProfile proxyBranch_;
        @Node.Child
        TruffleString.FromJavaStringNode fromJavaStringNode_;

        CachedOwnPropertyData(CachedOwnPropertyData next_) {
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

