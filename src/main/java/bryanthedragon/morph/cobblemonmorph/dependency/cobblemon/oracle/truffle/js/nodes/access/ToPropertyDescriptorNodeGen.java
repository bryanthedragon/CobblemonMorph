/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.access.ToPropertyDescriptorNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ToPropertyDescriptorNode.class)
public final class ToPropertyDescriptorNodeGen
extends ToPropertyDescriptorNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private DefaultData default_cache;
    @Node.Child
    private JSToStringNode nonObject_toStringNode_;
    @Node.Child
    private TruffleString.ConcatNode nonObject_concatNode_;

    private ToPropertyDescriptorNodeGen(JSContext context) {
        super(context);
    }

    @Override
    public Object execute(Object arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 3) != 0 && arg0Value instanceof JSDynamicObject) {
            DefaultData s1_;
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            if ((state_0 & 1) != 0 && !this.wasExecuted(arg0Value_) && JSGuards.isJSObject(arg0Value_)) {
                return this.nonSpecialized(arg0Value_);
            }
            if ((state_0 & 2) != 0 && (s1_ = this.default_cache) != null && this.wasExecuted(arg0Value_) && JSGuards.isJSObject(arg0Value_)) {
                return this.doDefault(arg0Value_, s1_.hasGetBranch_, s1_.hasSetBranch_, s1_.hasEnumerableBranch_, s1_.hasConfigurableBranch_, s1_.hasValueBranch_, s1_.hasWritableBranch_, s1_.isCallable_);
            }
        }
        if ((state_0 & 4) != 0 && !JSGuards.isJSObject(arg0Value)) {
            return this.doNonObject(arg0Value, this.nonObject_toStringNode_, this.nonObject_concatNode_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    private Object executeAndSpecialize(Object arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            if (arg0Value instanceof JSDynamicObject) {
                JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
                if (!this.wasExecuted(arg0Value_) && JSGuards.isJSObject(arg0Value_)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.nonSpecialized(arg0Value_);
                    return object;
                }
                if (this.wasExecuted(arg0Value_) && JSGuards.isJSObject(arg0Value_)) {
                    DefaultData s1_ = super.insert(new DefaultData());
                    s1_.hasGetBranch_ = BranchProfile.create();
                    s1_.hasSetBranch_ = BranchProfile.create();
                    s1_.hasEnumerableBranch_ = BranchProfile.create();
                    s1_.hasConfigurableBranch_ = BranchProfile.create();
                    s1_.hasValueBranch_ = BranchProfile.create();
                    s1_.hasWritableBranch_ = BranchProfile.create();
                    s1_.isCallable_ = s1_.insertAccessor(IsCallableNode.create());
                    VarHandle.storeStoreFence();
                    this.default_cache = s1_;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doDefault(arg0Value_, s1_.hasGetBranch_, s1_.hasSetBranch_, s1_.hasEnumerableBranch_, s1_.hasConfigurableBranch_, s1_.hasValueBranch_, s1_.hasWritableBranch_, s1_.isCallable_);
                    return object;
                }
            }
            if (!JSGuards.isJSObject(arg0Value)) {
                this.nonObject_toStringNode_ = super.insert(JSToStringNode.create());
                this.nonObject_concatNode_ = super.insert(TruffleString.ConcatNode.create());
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                Object object = this.doNonObject(arg0Value, this.nonObject_toStringNode_, this.nonObject_concatNode_);
                return object;
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

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Cloneable>> cached;
        Object[] data = new Object[4];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "nonSpecialized";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doDefault";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Cloneable>>();
            DefaultData s1_ = this.default_cache;
            if (s1_ != null) {
                cached.add(Arrays.asList(s1_.hasGetBranch_, s1_.hasSetBranch_, s1_.hasEnumerableBranch_, s1_.hasConfigurableBranch_, s1_.hasValueBranch_, s1_.hasWritableBranch_, s1_.isCallable_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        s = new Object[3];
        s[0] = "doNonObject";
        if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.nonObject_toStringNode_, this.nonObject_concatNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[3] = s;
        return Introspection.Provider.create(data);
    }

    public static ToPropertyDescriptorNode create(JSContext context) {
        return new ToPropertyDescriptorNodeGen(context);
    }

    @GeneratedBy(value=ToPropertyDescriptorNode.class)
    private static final class DefaultData
    extends Node {
        @CompilerDirectives.CompilationFinal
        BranchProfile hasGetBranch_;
        @CompilerDirectives.CompilationFinal
        BranchProfile hasSetBranch_;
        @CompilerDirectives.CompilationFinal
        BranchProfile hasEnumerableBranch_;
        @CompilerDirectives.CompilationFinal
        BranchProfile hasConfigurableBranch_;
        @CompilerDirectives.CompilationFinal
        BranchProfile hasValueBranch_;
        @CompilerDirectives.CompilationFinal
        BranchProfile hasWritableBranch_;
        @Node.Child
        IsCallableNode isCallable_;

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

