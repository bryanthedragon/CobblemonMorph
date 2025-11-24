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
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.PrivateBrandCheckNode;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=PrivateBrandCheckNode.class)
public final class PrivateBrandCheckNodeGen
extends PrivateBrandCheckNode
implements Introspection.Provider {
    private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private Instance0Data instance0_cache;

    private PrivateBrandCheckNodeGen(JavaScriptNode targetNode, JavaScriptNode brandNode) {
        super(targetNode, brandNode);
    }

    @Override
    @ExplodeLoop
    public Object executeWithTarget(VirtualFrame frameValue, Object targetNodeValue) {
        int state_0 = this.state_0_;
        Object brandNodeValue_ = this.brandNode.execute(frameValue);
        if ((state_0 & 7) != 0 && targetNodeValue instanceof JSObject) {
            TruffleObject brandNodeValue__;
            JSObject targetNodeValue_ = (JSObject)targetNodeValue;
            if ((state_0 & 3) != 0 && brandNodeValue_ instanceof HiddenKey) {
                brandNodeValue__ = (HiddenKey)brandNodeValue_;
                if ((state_0 & 1) != 0) {
                    Instance0Data s0_ = this.instance0_cache;
                    while (s0_ != null) {
                        if (s0_.access_.accepts(targetNodeValue_)) {
                            return this.doInstance(targetNodeValue_, (HiddenKey)brandNodeValue__, s0_.access_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    return this.instance1Boundary(state_0, targetNodeValue_, (HiddenKey)brandNodeValue__);
                }
            }
            if ((state_0 & 4) != 0 && brandNodeValue_ instanceof JSDynamicObject) {
                brandNodeValue__ = (JSDynamicObject)brandNodeValue_;
                return this.doStatic(targetNodeValue_, (JSDynamicObject)brandNodeValue__);
            }
        }
        if ((state_0 & 8) != 0 && PrivateBrandCheckNodeGen.fallbackGuard_(state_0, targetNodeValue, brandNodeValue_)) {
            return this.denied(targetNodeValue, brandNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(targetNodeValue, brandNodeValue_);
    }

    @CompilerDirectives.TruffleBoundary
    private Object instance1Boundary(int state_0, JSObject targetNodeValue_, HiddenKey brandNodeValue__) {
        DynamicObjectLibrary instance1_access__ = DYNAMIC_OBJECT_LIBRARY_.getUncached(targetNodeValue_);
        return this.doInstance(targetNodeValue_, brandNodeValue__, instance1_access__);
    }

    @Override
    @ExplodeLoop
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object targetNodeValue_ = this.targetNode.execute(frameValue);
        Object brandNodeValue_ = this.brandNode.execute(frameValue);
        if ((state_0 & 7) != 0 && targetNodeValue_ instanceof JSObject) {
            TruffleObject brandNodeValue__;
            JSObject targetNodeValue__ = (JSObject)targetNodeValue_;
            if ((state_0 & 3) != 0 && brandNodeValue_ instanceof HiddenKey) {
                brandNodeValue__ = (HiddenKey)brandNodeValue_;
                if ((state_0 & 1) != 0) {
                    Instance0Data s0_ = this.instance0_cache;
                    while (s0_ != null) {
                        if (s0_.access_.accepts(targetNodeValue__)) {
                            return this.doInstance(targetNodeValue__, (HiddenKey)brandNodeValue__, s0_.access_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    return this.instance1Boundary0(state_0, targetNodeValue__, (HiddenKey)brandNodeValue__);
                }
            }
            if ((state_0 & 4) != 0 && brandNodeValue_ instanceof JSDynamicObject) {
                brandNodeValue__ = (JSDynamicObject)brandNodeValue_;
                return this.doStatic(targetNodeValue__, (JSDynamicObject)brandNodeValue__);
            }
        }
        if ((state_0 & 8) != 0 && PrivateBrandCheckNodeGen.fallbackGuard_(state_0, targetNodeValue_, brandNodeValue_)) {
            return this.denied(targetNodeValue_, brandNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(targetNodeValue_, brandNodeValue_);
    }

    @CompilerDirectives.TruffleBoundary
    private Object instance1Boundary0(int state_0, JSObject targetNodeValue__, HiddenKey brandNodeValue__) {
        DynamicObjectLibrary instance1_access__ = DYNAMIC_OBJECT_LIBRARY_.getUncached(targetNodeValue__);
        return this.doInstance(targetNodeValue__, brandNodeValue__, instance1_access__);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.execute(frameValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object executeAndSpecialize(Object targetNodeValue, Object brandNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (targetNodeValue instanceof JSObject) {
                JSObject targetNodeValue_ = (JSObject)targetNodeValue;
                if (brandNodeValue instanceof HiddenKey) {
                    HiddenKey brandNodeValue_ = (HiddenKey)brandNodeValue;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Instance0Data s0_ = this.instance0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.access_.accepts(targetNodeValue_)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 3) {
                            s0_ = super.insert(new Instance0Data(this.instance0_cache));
                            s0_.access_ = s0_.insertAccessor(DYNAMIC_OBJECT_LIBRARY_.create(targetNodeValue_));
                            VarHandle.storeStoreFence();
                            this.instance0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object = this.doInstance(targetNodeValue_, brandNodeValue_, s0_.access_);
                            return object;
                        }
                    }
                    DynamicObjectLibrary instance1_access__ = null;
                    instance1_access__ = DYNAMIC_OBJECT_LIBRARY_.getUncached(targetNodeValue_);
                    this.exclude_ = exclude |= 1;
                    this.instance0_cache = null;
                    state_0 &= 0xFFFFFFFE;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doInstance(targetNodeValue_, brandNodeValue_, instance1_access__);
                    return object;
                }
                if (brandNodeValue instanceof JSDynamicObject) {
                    JSDynamicObject brandNodeValue_ = (JSDynamicObject)brandNodeValue;
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doStatic(targetNodeValue_, brandNodeValue_);
                    return object;
                }
            }
            this.state_0_ = state_0 |= 8;
            lock.unlock();
            hasLock = false;
            Object object = this.denied(targetNodeValue, brandNodeValue);
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
        Instance0Data s0_;
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.instance0_cache) == null || s0_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Object>> cached;
        Object[] data = new Object[5];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doInstance";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            Instance0Data s0_ = this.instance0_cache;
            while (s0_ != null) {
                cached.add(Arrays.asList(s0_.access_));
                s0_ = s0_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doInstance";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(new Object[0]));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        s = new Object[3];
        s[0] = "doStatic";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "denied";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        return Introspection.Provider.create(data);
    }

    private static boolean fallbackGuard_(int state_0, Object targetNodeValue, Object brandNodeValue) {
        if (targetNodeValue instanceof JSObject) {
            if ((state_0 & 2) == 0 && brandNodeValue instanceof HiddenKey) {
                return false;
            }
            if ((state_0 & 4) == 0 && brandNodeValue instanceof JSDynamicObject) {
                return false;
            }
        }
        return true;
    }

    public static PrivateBrandCheckNode create(JavaScriptNode targetNode, JavaScriptNode brandNode) {
        return new PrivateBrandCheckNodeGen(targetNode, brandNode);
    }

    @GeneratedBy(value=PrivateBrandCheckNode.class)
    private static final class Instance0Data
    extends Node {
        @Node.Child
        Instance0Data next_;
        @Node.Child
        DynamicObjectLibrary access_;

        Instance0Data(Instance0Data next_) {
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

