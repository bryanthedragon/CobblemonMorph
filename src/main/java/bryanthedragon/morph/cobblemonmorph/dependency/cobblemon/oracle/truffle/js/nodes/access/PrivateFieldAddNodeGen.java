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
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.js.nodes.access.PrivateFieldAddNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=PrivateFieldAddNode.class)
public final class PrivateFieldAddNodeGen
extends PrivateFieldAddNode
implements Introspection.Provider {
    private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private FieldAdd0Data fieldAdd0_cache;

    private PrivateFieldAddNodeGen(JSContext context) {
        super(context);
    }

    @Override
    @ExplodeLoop
    public void execute(Object arg0Value, Object arg1Value, Object arg2Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 3) != 0 && arg0Value instanceof JSObject) {
                JSObject arg0Value_ = (JSObject)arg0Value;
                if (arg1Value instanceof HiddenKey) {
                    HiddenKey arg1Value_ = (HiddenKey)arg1Value;
                    if ((state_0 & 1) != 0) {
                        FieldAdd0Data s0_ = this.fieldAdd0_cache;
                        while (s0_ != null) {
                            if (s0_.access_.accepts(arg0Value_)) {
                                this.doFieldAdd(arg0Value_, arg1Value_, arg2Value, s0_.access_);
                                return;
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        this.fieldAdd1Boundary(state_0, arg0Value_, arg1Value_, arg2Value);
                        return;
                    }
                }
            }
            if ((state_0 & 4) != 0 && PrivateFieldAddNodeGen.fallbackGuard_(state_0, arg0Value, arg1Value, arg2Value)) {
                this.doFallback(arg0Value, arg1Value, arg2Value);
                return;
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
    }

    @CompilerDirectives.TruffleBoundary
    private void fieldAdd1Boundary(int state_0, JSObject arg0Value_, HiddenKey arg1Value_, Object arg2Value) {
        DynamicObjectLibrary fieldAdd1_access__ = DYNAMIC_OBJECT_LIBRARY_.getUncached(arg0Value_);
        this.doFieldAdd(arg0Value_, arg1Value_, arg2Value, fieldAdd1_access__);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void executeAndSpecialize(Object arg0Value, Object arg1Value, Object arg2Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arg0Value instanceof JSObject) {
                JSObject arg0Value_ = (JSObject)arg0Value;
                if (arg1Value instanceof HiddenKey) {
                    HiddenKey arg1Value_ = (HiddenKey)arg1Value;
                    if (exclude == 0) {
                        int count0_ = 0;
                        FieldAdd0Data s0_ = this.fieldAdd0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.access_.accepts(arg0Value_)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 3) {
                            s0_ = super.insert(new FieldAdd0Data(this.fieldAdd0_cache));
                            s0_.access_ = s0_.insertAccessor(DYNAMIC_OBJECT_LIBRARY_.create(arg0Value_));
                            VarHandle.storeStoreFence();
                            this.fieldAdd0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            this.doFieldAdd(arg0Value_, arg1Value_, arg2Value, s0_.access_);
                            return;
                        }
                    }
                    DynamicObjectLibrary fieldAdd1_access__ = null;
                    fieldAdd1_access__ = DYNAMIC_OBJECT_LIBRARY_.getUncached(arg0Value_);
                    this.exclude_ = exclude |= 1;
                    this.fieldAdd0_cache = null;
                    state_0 &= 0xFFFFFFFE;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    this.doFieldAdd(arg0Value_, arg1Value_, arg2Value, fieldAdd1_access__);
                    return;
                }
            }
            this.state_0_ = state_0 |= 4;
            lock.unlock();
            hasLock = false;
            this.doFallback(arg0Value, arg1Value, arg2Value);
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
        FieldAdd0Data s0_;
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.fieldAdd0_cache) == null || s0_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Object>> cached;
        Object[] data = new Object[4];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doFieldAdd";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            FieldAdd0Data s0_ = this.fieldAdd0_cache;
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
        s[0] = "doFieldAdd";
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
        s[0] = "doFallback";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        return Introspection.Provider.create(data);
    }

    private static boolean fallbackGuard_(int state_0, Object arg0Value, Object arg1Value, Object arg2Value) {
        return (state_0 & 2) != 0 || !(arg0Value instanceof JSObject) || !(arg1Value instanceof HiddenKey);
    }

    public static PrivateFieldAddNode create(JSContext context) {
        return new PrivateFieldAddNodeGen(context);
    }

    @GeneratedBy(value=PrivateFieldAddNode.class)
    private static final class FieldAdd0Data
    extends Node {
        @Node.Child
        FieldAdd0Data next_;
        @Node.Child
        DynamicObjectLibrary access_;

        FieldAdd0Data(FieldAdd0Data next_) {
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

