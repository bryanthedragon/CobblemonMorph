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
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.EnumerateNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=EnumerateNode.class)
public final class EnumerateNodeGen
extends EnumerateNode
implements Introspection.Provider {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile enumerateObject_isObject_;
    @Node.Child
    private EnumerateNode enumerateJSAdapter_enumerateCallbackResultNode_;
    @Node.Child
    private EnumerateTruffleObject0Data enumerateTruffleObject0_cache;
    @Node.Child
    private InteropLibrary enumerateTruffleObject1_keysInterop_;
    @CompilerDirectives.CompilationFinal
    private BranchProfile enumerateTruffleObject1_notIterable_;
    @Node.Child
    private JSToObjectNode nonObject_toObjectNode_;
    @Node.Child
    private EnumerateNode nonObject_enumerateNode_;

    private EnumerateNodeGen(JSContext context, boolean values, boolean requireIterable, JavaScriptNode targetNode) {
        super(context, values, requireIterable, targetNode);
    }

    @Override
    @ExplodeLoop
    public JSDynamicObject execute(Object targetNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 3) != 0 && targetNodeValue instanceof JSDynamicObject) {
            JSDynamicObject targetNodeValue_ = (JSDynamicObject)targetNodeValue;
            if ((state_0 & 1) != 0 && JSGuards.isJSDynamicObject(targetNodeValue_) && !JSGuards.isJSAdapter(targetNodeValue_)) {
                return this.doEnumerateObject(targetNodeValue_, this.enumerateObject_isObject_);
            }
            if ((state_0 & 2) != 0 && JSGuards.isJSAdapter(targetNodeValue_)) {
                return this.doEnumerateJSAdapter(targetNodeValue_, this.enumerateJSAdapter_enumerateCallbackResultNode_);
            }
        }
        if ((state_0 & 0x1C) != 0) {
            if ((state_0 & 4) != 0) {
                EnumerateTruffleObject0Data s2_ = this.enumerateTruffleObject0_cache;
                while (s2_ != null) {
                    if (s2_.interop_.accepts(targetNodeValue) && JSGuards.isForeignObject(targetNodeValue)) {
                        return this.doEnumerateTruffleObject(targetNodeValue, s2_.interop_, s2_.keysInterop_, s2_.notIterable_);
                    }
                    s2_ = s2_.next_;
                }
            }
            if ((state_0 & 8) != 0 && JSGuards.isForeignObject(targetNodeValue)) {
                return this.enumerateTruffleObject1Boundary(state_0, targetNodeValue);
            }
            if ((state_0 & 0x10) != 0 && !JSGuards.isJSObject(targetNodeValue) && !JSGuards.isForeignObject(targetNodeValue)) {
                return this.doNonObject(targetNodeValue, this.nonObject_toObjectNode_, this.nonObject_enumerateNode_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(targetNodeValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private JSDynamicObject enumerateTruffleObject1Boundary(int state_0, Object targetNodeValue) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary enumerateTruffleObject1_interop__ = INTEROP_LIBRARY_.getUncached(targetNodeValue);
            JSDynamicObject jSDynamicObject = this.doEnumerateTruffleObject(targetNodeValue, enumerateTruffleObject1_interop__, this.enumerateTruffleObject1_keysInterop_, this.enumerateTruffleObject1_notIterable_);
            return jSDynamicObject;
        }
        finally {
            encapsulating_.set(prev_);
        }
    }

    @Override
    @ExplodeLoop
    public JSDynamicObject execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object targetNodeValue_ = this.targetNode.execute(frameValue);
        if ((state_0 & 3) != 0 && targetNodeValue_ instanceof JSDynamicObject) {
            JSDynamicObject targetNodeValue__ = (JSDynamicObject)targetNodeValue_;
            if ((state_0 & 1) != 0 && JSGuards.isJSDynamicObject(targetNodeValue__) && !JSGuards.isJSAdapter(targetNodeValue__)) {
                return this.doEnumerateObject(targetNodeValue__, this.enumerateObject_isObject_);
            }
            if ((state_0 & 2) != 0 && JSGuards.isJSAdapter(targetNodeValue__)) {
                return this.doEnumerateJSAdapter(targetNodeValue__, this.enumerateJSAdapter_enumerateCallbackResultNode_);
            }
        }
        if ((state_0 & 0x1C) != 0) {
            if ((state_0 & 4) != 0) {
                EnumerateTruffleObject0Data s2_ = this.enumerateTruffleObject0_cache;
                while (s2_ != null) {
                    if (s2_.interop_.accepts(targetNodeValue_) && JSGuards.isForeignObject(targetNodeValue_)) {
                        return this.doEnumerateTruffleObject(targetNodeValue_, s2_.interop_, s2_.keysInterop_, s2_.notIterable_);
                    }
                    s2_ = s2_.next_;
                }
            }
            if ((state_0 & 8) != 0 && JSGuards.isForeignObject(targetNodeValue_)) {
                return this.enumerateTruffleObject1Boundary0(state_0, targetNodeValue_);
            }
            if ((state_0 & 0x10) != 0 && !JSGuards.isJSObject(targetNodeValue_) && !JSGuards.isForeignObject(targetNodeValue_)) {
                return this.doNonObject(targetNodeValue_, this.nonObject_toObjectNode_, this.nonObject_enumerateNode_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(targetNodeValue_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private JSDynamicObject enumerateTruffleObject1Boundary0(int state_0, Object targetNodeValue_) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary enumerateTruffleObject1_interop__ = INTEROP_LIBRARY_.getUncached(targetNodeValue_);
            JSDynamicObject jSDynamicObject = this.doEnumerateTruffleObject(targetNodeValue_, enumerateTruffleObject1_interop__, this.enumerateTruffleObject1_keysInterop_, this.enumerateTruffleObject1_notIterable_);
            return jSDynamicObject;
        }
        finally {
            encapsulating_.set(prev_);
        }
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.execute(frameValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private JSDynamicObject executeAndSpecialize(Object targetNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (targetNodeValue instanceof JSDynamicObject) {
                JSDynamicObject targetNodeValue_ = (JSDynamicObject)targetNodeValue;
                if (JSGuards.isJSDynamicObject(targetNodeValue_) && !JSGuards.isJSAdapter(targetNodeValue_)) {
                    this.enumerateObject_isObject_ = ConditionProfile.createBinaryProfile();
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.doEnumerateObject(targetNodeValue_, this.enumerateObject_isObject_);
                    return jSDynamicObject;
                }
                if (JSGuards.isJSAdapter(targetNodeValue_)) {
                    this.enumerateJSAdapter_enumerateCallbackResultNode_ = super.insert(this.createValues());
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.doEnumerateJSAdapter(targetNodeValue_, this.enumerateJSAdapter_enumerateCallbackResultNode_);
                    return jSDynamicObject;
                }
            }
            if (exclude == 0) {
                int count2_ = 0;
                EnumerateTruffleObject0Data s2_ = this.enumerateTruffleObject0_cache;
                if ((state_0 & 4) != 0) {
                    while (!(s2_ == null || s2_.interop_.accepts(targetNodeValue) && JSGuards.isForeignObject(targetNodeValue))) {
                        s2_ = s2_.next_;
                        ++count2_;
                    }
                }
                if (s2_ == null && JSGuards.isForeignObject(targetNodeValue) && count2_ < 5) {
                    s2_ = super.insert(new EnumerateTruffleObject0Data(this.enumerateTruffleObject0_cache));
                    s2_.interop_ = s2_.insertAccessor(INTEROP_LIBRARY_.create(targetNodeValue));
                    s2_.keysInterop_ = s2_.insertAccessor(INTEROP_LIBRARY_.createDispatched(5));
                    s2_.notIterable_ = BranchProfile.create();
                    VarHandle.storeStoreFence();
                    this.enumerateTruffleObject0_cache = s2_;
                    this.state_0_ = state_0 |= 4;
                }
                if (s2_ != null) {
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.doEnumerateTruffleObject(targetNodeValue, s2_.interop_, s2_.keysInterop_, s2_.notIterable_);
                    return jSDynamicObject;
                }
            }
            InteropLibrary enumerateTruffleObject1_interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                if (JSGuards.isForeignObject(targetNodeValue)) {
                    enumerateTruffleObject1_interop__ = INTEROP_LIBRARY_.getUncached(targetNodeValue);
                    this.enumerateTruffleObject1_keysInterop_ = super.insert(INTEROP_LIBRARY_.createDispatched(5));
                    this.enumerateTruffleObject1_notIterable_ = BranchProfile.create();
                    this.exclude_ = exclude |= 1;
                    this.enumerateTruffleObject0_cache = null;
                    state_0 &= 0xFFFFFFFB;
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.doEnumerateTruffleObject(targetNodeValue, enumerateTruffleObject1_interop__, this.enumerateTruffleObject1_keysInterop_, this.enumerateTruffleObject1_notIterable_);
                    return jSDynamicObject;
                }
            }
            finally {
                encapsulating_.set(prev_);
            }
            if (!JSGuards.isJSObject(targetNodeValue) && !JSGuards.isForeignObject(targetNodeValue)) {
                this.nonObject_toObjectNode_ = super.insert(JSToObjectNode.createToObjectNoCheck(this.context));
                this.nonObject_enumerateNode_ = super.insert(this.copyRecursive());
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.doNonObject(targetNodeValue, this.nonObject_toObjectNode_, this.nonObject_enumerateNode_);
                return jSDynamicObject;
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.targetNode}, targetNodeValue);
        }
        finally {
            if (hasLock) {
                lock.unlock();
            }
        }
    }

    @Override
    public NodeCost getCost() {
        EnumerateTruffleObject0Data s2_;
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s2_ = this.enumerateTruffleObject0_cache) == null || s2_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Cloneable>> cached;
        Object[] data = new Object[6];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doEnumerateObject";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Cloneable>>();
            cached.add(Arrays.asList(this.enumerateObject_isObject_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doEnumerateJSAdapter";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.enumerateJSAdapter_enumerateCallbackResultNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        s = new Object[3];
        s[0] = "doEnumerateTruffleObject";
        if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            EnumerateTruffleObject0Data s2_ = this.enumerateTruffleObject0_cache;
            while (s2_ != null) {
                cached.add(Arrays.asList(s2_.interop_, s2_.keysInterop_, s2_.notIterable_));
                s2_ = s2_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[3] = s;
        s = new Object[3];
        s[0] = "doEnumerateTruffleObject";
        if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.enumerateTruffleObject1_keysInterop_, this.enumerateTruffleObject1_notIterable_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[4] = s;
        s = new Object[3];
        s[0] = "doNonObject";
        if ((state_0 & 0x10) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.nonObject_toObjectNode_, this.nonObject_enumerateNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[5] = s;
        return Introspection.Provider.create(data);
    }

    public static EnumerateNode create(JSContext context, boolean values, boolean requireIterable, JavaScriptNode targetNode) {
        return new EnumerateNodeGen(context, values, requireIterable, targetNode);
    }

    @GeneratedBy(value=EnumerateNode.class)
    private static final class EnumerateTruffleObject0Data
    extends Node {
        @Node.Child
        EnumerateTruffleObject0Data next_;
        @Node.Child
        InteropLibrary interop_;
        @Node.Child
        InteropLibrary keysInterop_;
        @CompilerDirectives.CompilationFinal
        BranchProfile notIterable_;

        EnumerateTruffleObject0Data(EnumerateTruffleObject0Data next_) {
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

