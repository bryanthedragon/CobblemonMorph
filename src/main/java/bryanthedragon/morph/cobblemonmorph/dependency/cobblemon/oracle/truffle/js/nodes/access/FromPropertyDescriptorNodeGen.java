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
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.js.nodes.access.FromPropertyDescriptorNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=FromPropertyDescriptorNode.class)
public final class FromPropertyDescriptorNodeGen
extends FromPropertyDescriptorNode
implements Introspection.Provider {
    private static final Uncached UNCACHED = new Uncached();
    private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private ToJSObjectData toJSObject_cache;

    private FromPropertyDescriptorNodeGen() {
    }

    @Override
    public JSDynamicObject execute(PropertyDescriptor arg0Value, JSContext arg1Value) {
        ToJSObjectData s0_;
        int state_0 = this.state_0_;
        if (state_0 != 0 && (s0_ = this.toJSObject_cache) != null) {
            return this.toJSObject(arg0Value, arg1Value, s0_.putValueNode_, s0_.putWritableNode_, s0_.putGetNode_, s0_.putSetNode_, s0_.putEnumerableNode_, s0_.putConfigurableNode_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private JSDynamicObject executeAndSpecialize(PropertyDescriptor arg0Value, JSContext arg1Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            ToJSObjectData s0_ = super.insert(new ToJSObjectData());
            s0_.putValueNode_ = s0_.insertAccessor(DYNAMIC_OBJECT_LIBRARY_.createDispatched(6));
            s0_.putWritableNode_ = s0_.insertAccessor(DYNAMIC_OBJECT_LIBRARY_.createDispatched(6));
            s0_.putGetNode_ = s0_.insertAccessor(DYNAMIC_OBJECT_LIBRARY_.createDispatched(6));
            s0_.putSetNode_ = s0_.insertAccessor(DYNAMIC_OBJECT_LIBRARY_.createDispatched(6));
            s0_.putEnumerableNode_ = s0_.insertAccessor(DYNAMIC_OBJECT_LIBRARY_.createDispatched(6));
            s0_.putConfigurableNode_ = s0_.insertAccessor(DYNAMIC_OBJECT_LIBRARY_.createDispatched(6));
            VarHandle.storeStoreFence();
            this.toJSObject_cache = s0_;
            this.state_0_ = state_0 |= 1;
            lock.unlock();
            hasLock = false;
            JSDynamicObject jSDynamicObject = this.toJSObject(arg0Value, arg1Value, s0_.putValueNode_, s0_.putWritableNode_, s0_.putGetNode_, s0_.putSetNode_, s0_.putEnumerableNode_, s0_.putConfigurableNode_);
            return jSDynamicObject;
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

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[2];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "toJSObject";
        if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<List<DynamicObjectLibrary>> cached = new ArrayList<List<DynamicObjectLibrary>>();
            ToJSObjectData s0_ = this.toJSObject_cache;
            if (s0_ != null) {
                cached.add(Arrays.asList(s0_.putValueNode_, s0_.putWritableNode_, s0_.putGetNode_, s0_.putSetNode_, s0_.putEnumerableNode_, s0_.putConfigurableNode_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static FromPropertyDescriptorNode create() {
        return new FromPropertyDescriptorNodeGen();
    }

    public static FromPropertyDescriptorNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=FromPropertyDescriptorNode.class)
    @DenyReplace
    private static final class Uncached
    extends FromPropertyDescriptorNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public JSDynamicObject execute(PropertyDescriptor arg0Value, JSContext arg1Value) {
            return this.toJSObject(arg0Value, arg1Value, DYNAMIC_OBJECT_LIBRARY_.getUncached(), DYNAMIC_OBJECT_LIBRARY_.getUncached(), DYNAMIC_OBJECT_LIBRARY_.getUncached(), DYNAMIC_OBJECT_LIBRARY_.getUncached(), DYNAMIC_OBJECT_LIBRARY_.getUncached(), DYNAMIC_OBJECT_LIBRARY_.getUncached());
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

    @GeneratedBy(value=FromPropertyDescriptorNode.class)
    private static final class ToJSObjectData
    extends Node {
        @Node.Child
        DynamicObjectLibrary putValueNode_;
        @Node.Child
        DynamicObjectLibrary putWritableNode_;
        @Node.Child
        DynamicObjectLibrary putGetNode_;
        @Node.Child
        DynamicObjectLibrary putSetNode_;
        @Node.Child
        DynamicObjectLibrary putEnumerableNode_;
        @Node.Child
        DynamicObjectLibrary putConfigurableNode_;

        ToJSObjectData() {
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

