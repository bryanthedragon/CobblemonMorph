/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.binary;

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
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.binary.PrivateFieldInNode;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=PrivateFieldInNode.class)
public final class PrivateFieldInNodeGen
extends PrivateFieldInNode
implements Introspection.Provider {
    private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private Instance0Data instance0_cache;
    @Node.Child
    private IsObjectNode fallback_isObjectNode_;

    private PrivateFieldInNodeGen(JavaScriptNode left, JavaScriptNode right) {
        super(left, right);
    }

    @Override
    @ExplodeLoop
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        if ((state_0 & 7) != 0 && rightNodeValue_ instanceof JSDynamicObject) {
            TruffleObject leftNodeValue__;
            JSDynamicObject rightNodeValue__ = (JSDynamicObject)rightNodeValue_;
            if ((state_0 & 3) != 0 && leftNodeValue_ instanceof HiddenKey) {
                leftNodeValue__ = (HiddenKey)leftNodeValue_;
                if ((state_0 & 1) != 0) {
                    Instance0Data s0_ = this.instance0_cache;
                    while (s0_ != null) {
                        if (s0_.access_.accepts(rightNodeValue__) && JSGuards.isJSObject(rightNodeValue__)) {
                            return this.doInstance((HiddenKey)leftNodeValue__, rightNodeValue__, s0_.access_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0 && JSGuards.isJSObject(rightNodeValue__)) {
                    return this.instance1Boundary(state_0, (HiddenKey)leftNodeValue__, rightNodeValue__);
                }
            }
            if ((state_0 & 4) != 0 && leftNodeValue_ instanceof JSDynamicObject) {
                leftNodeValue__ = (JSDynamicObject)leftNodeValue_;
                if (JSGuards.isJSObject(rightNodeValue__)) {
                    return this.doStatic((JSDynamicObject)leftNodeValue__, rightNodeValue__);
                }
            }
        }
        if ((state_0 & 8) != 0 && PrivateFieldInNodeGen.fallbackGuard_(leftNodeValue_, rightNodeValue_)) {
            return this.doFallback(leftNodeValue_, rightNodeValue_, this.fallback_isObjectNode_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
    }

    @CompilerDirectives.TruffleBoundary
    private Object instance1Boundary(int state_0, HiddenKey leftNodeValue__, JSDynamicObject rightNodeValue__) {
        DynamicObjectLibrary instance1_access__ = DYNAMIC_OBJECT_LIBRARY_.getUncached(rightNodeValue__);
        return this.doInstance(leftNodeValue__, rightNodeValue__, instance1_access__);
    }

    @Override
    @ExplodeLoop
    public boolean executeBoolean(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        if ((state_0 & 7) != 0 && rightNodeValue_ instanceof JSDynamicObject) {
            TruffleObject leftNodeValue__;
            JSDynamicObject rightNodeValue__ = (JSDynamicObject)rightNodeValue_;
            if ((state_0 & 3) != 0 && leftNodeValue_ instanceof HiddenKey) {
                leftNodeValue__ = (HiddenKey)leftNodeValue_;
                if ((state_0 & 1) != 0) {
                    Instance0Data s0_ = this.instance0_cache;
                    while (s0_ != null) {
                        if (s0_.access_.accepts(rightNodeValue__) && JSGuards.isJSObject(rightNodeValue__)) {
                            return this.doInstance((HiddenKey)leftNodeValue__, rightNodeValue__, s0_.access_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0 && JSGuards.isJSObject(rightNodeValue__)) {
                    return this.instance1Boundary0(state_0, (HiddenKey)leftNodeValue__, rightNodeValue__);
                }
            }
            if ((state_0 & 4) != 0 && leftNodeValue_ instanceof JSDynamicObject) {
                leftNodeValue__ = (JSDynamicObject)leftNodeValue_;
                if (JSGuards.isJSObject(rightNodeValue__)) {
                    return this.doStatic((JSDynamicObject)leftNodeValue__, rightNodeValue__);
                }
            }
        }
        if ((state_0 & 8) != 0 && PrivateFieldInNodeGen.fallbackGuard_(leftNodeValue_, rightNodeValue_)) {
            return this.doFallback(leftNodeValue_, rightNodeValue_, this.fallback_isObjectNode_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
    }

    @CompilerDirectives.TruffleBoundary
    private boolean instance1Boundary0(int state_0, HiddenKey leftNodeValue__, JSDynamicObject rightNodeValue__) {
        DynamicObjectLibrary instance1_access__ = DYNAMIC_OBJECT_LIBRARY_.getUncached(rightNodeValue__);
        return this.doInstance(leftNodeValue__, rightNodeValue__, instance1_access__);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.executeBoolean(frameValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean executeAndSpecialize(Object leftNodeValue, Object rightNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (rightNodeValue instanceof JSDynamicObject) {
                TruffleObject leftNodeValue_;
                JSDynamicObject rightNodeValue_ = (JSDynamicObject)rightNodeValue;
                if (leftNodeValue instanceof HiddenKey) {
                    leftNodeValue_ = (HiddenKey)leftNodeValue;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Instance0Data s0_ = this.instance0_cache;
                        if ((state_0 & 1) != 0) {
                            while (!(s0_ == null || s0_.access_.accepts(rightNodeValue_) && JSGuards.isJSObject(rightNodeValue_))) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && JSGuards.isJSObject(rightNodeValue_) && count0_ < 3) {
                            s0_ = super.insert(new Instance0Data(this.instance0_cache));
                            s0_.access_ = s0_.insertAccessor(DYNAMIC_OBJECT_LIBRARY_.create(rightNodeValue_));
                            VarHandle.storeStoreFence();
                            this.instance0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            boolean bl = this.doInstance((HiddenKey)leftNodeValue_, rightNodeValue_, s0_.access_);
                            return bl;
                        }
                    }
                    DynamicObjectLibrary instance1_access__ = null;
                    if (JSGuards.isJSObject(rightNodeValue_)) {
                        instance1_access__ = DYNAMIC_OBJECT_LIBRARY_.getUncached(rightNodeValue_);
                        this.exclude_ = exclude |= 1;
                        this.instance0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = this.doInstance((HiddenKey)leftNodeValue_, rightNodeValue_, instance1_access__);
                        return bl;
                    }
                }
                if (leftNodeValue instanceof JSDynamicObject) {
                    leftNodeValue_ = (JSDynamicObject)leftNodeValue;
                    if (JSGuards.isJSObject(rightNodeValue_)) {
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = this.doStatic((JSDynamicObject)leftNodeValue_, rightNodeValue_);
                        return bl;
                    }
                }
            }
            this.fallback_isObjectNode_ = super.insert(IsObjectNode.create());
            this.state_0_ = state_0 |= 8;
            lock.unlock();
            hasLock = false;
            boolean bl = this.doFallback(leftNodeValue, rightNodeValue, this.fallback_isObjectNode_);
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
        s[0] = "doFallback";
        if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.fallback_isObjectNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[4] = s;
        return Introspection.Provider.create(data);
    }

    private static boolean fallbackGuard_(Object leftNodeValue, Object rightNodeValue) {
        if (rightNodeValue instanceof JSDynamicObject) {
            JSDynamicObject rightNodeValue_;
            if (leftNodeValue instanceof HiddenKey && JSGuards.isJSObject(rightNodeValue_ = (JSDynamicObject)rightNodeValue)) {
                return false;
            }
            if (leftNodeValue instanceof JSDynamicObject && JSGuards.isJSObject(rightNodeValue_ = (JSDynamicObject)rightNodeValue)) {
                return false;
            }
        }
        return true;
    }

    public static PrivateFieldInNode create(JavaScriptNode left, JavaScriptNode right) {
        return new PrivateFieldInNodeGen(left, right);
    }

    @GeneratedBy(value=PrivateFieldInNode.class)
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

