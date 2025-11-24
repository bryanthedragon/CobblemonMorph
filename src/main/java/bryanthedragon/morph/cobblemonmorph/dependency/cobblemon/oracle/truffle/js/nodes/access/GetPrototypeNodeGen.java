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
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.Location;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.access.GetPrototypeNode;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=GetPrototypeNode.class)
public final class GetPrototypeNodeGen
extends GetPrototypeNode
implements Introspection.Provider {
    private static final Uncached UNCACHED = new Uncached();
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private CachedShapeData cachedShape_cache;
    @CompilerDirectives.CompilationFinal
    private JSClassProfile proxy_jsclassProfile_;

    private GetPrototypeNodeGen() {
    }

    @Override
    @ExplodeLoop
    public JSDynamicObject execute(Object arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 7) != 0 && arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            if ((state_0 & 1) != 0) {
                CachedShapeData s0_ = this.cachedShape_cache;
                while (s0_ != null) {
                    if (arg0Value_.getShape() == s0_.shape_) {
                        assert (s0_.prototypeLocation_ != null);
                        return GetPrototypeNode.doCachedShape(arg0Value_, s0_.shape_, s0_.prototypeLocation_);
                    }
                    s0_ = s0_.next_;
                }
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSProxy(arg0Value_)) {
                return GetPrototypeNode.doGeneric(arg0Value_);
            }
            if ((state_0 & 4) != 0 && JSGuards.isJSProxy(arg0Value_)) {
                return GetPrototypeNode.doProxy(arg0Value_, this.proxy_jsclassProfile_);
            }
        }
        if ((state_0 & 8) != 0 && !JSGuards.isJSDynamicObject(arg0Value)) {
            return GetPrototypeNode.doNotObject(arg0Value);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    @Override
    @ExplodeLoop
    public JSDynamicObject execute(JSDynamicObject arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 7) != 0) {
            if ((state_0 & 1) != 0) {
                CachedShapeData s0_ = this.cachedShape_cache;
                while (s0_ != null) {
                    if (arg0Value.getShape() == s0_.shape_) {
                        assert (s0_.prototypeLocation_ != null);
                        return GetPrototypeNode.doCachedShape(arg0Value, s0_.shape_, s0_.prototypeLocation_);
                    }
                    s0_ = s0_.next_;
                }
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSProxy(arg0Value)) {
                return GetPrototypeNode.doGeneric(arg0Value);
            }
            if ((state_0 & 4) != 0 && JSGuards.isJSProxy(arg0Value)) {
                return GetPrototypeNode.doProxy(arg0Value, this.proxy_jsclassProfile_);
            }
        }
        if ((state_0 & 8) != 0 && !JSGuards.isJSDynamicObject(arg0Value)) {
            return GetPrototypeNode.doNotObject(arg0Value);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    private JSDynamicObject executeAndSpecialize(Object arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arg0Value instanceof JSDynamicObject) {
                JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
                if (exclude == 0) {
                    int count0_ = 0;
                    CachedShapeData s0_ = this.cachedShape_cache;
                    if ((state_0 & 1) != 0) {
                        while (s0_ != null) {
                            if (arg0Value_.getShape() == s0_.shape_) {
                                assert (s0_.prototypeLocation_ != null);
                                break;
                            }
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null) {
                        Location prototypeLocation__;
                        Shape shape__ = arg0Value_.getShape();
                        if (arg0Value_.getShape() == shape__ && (prototypeLocation__ = GetPrototypeNode.getPrototypeLocation(shape__)) != null && count0_ < 2) {
                            s0_ = new CachedShapeData(this.cachedShape_cache);
                            s0_.shape_ = shape__;
                            s0_.prototypeLocation_ = prototypeLocation__;
                            VarHandle.storeStoreFence();
                            this.cachedShape_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = GetPrototypeNode.doCachedShape(arg0Value_, s0_.shape_, s0_.prototypeLocation_);
                        return jSDynamicObject;
                    }
                }
                if (!JSGuards.isJSProxy(arg0Value_)) {
                    this.exclude_ = exclude |= 1;
                    this.cachedShape_cache = null;
                    state_0 &= 0xFFFFFFFE;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = GetPrototypeNode.doGeneric(arg0Value_);
                    return jSDynamicObject;
                }
                if (JSGuards.isJSProxy(arg0Value_)) {
                    this.proxy_jsclassProfile_ = JSClassProfile.create();
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = GetPrototypeNode.doProxy(arg0Value_, this.proxy_jsclassProfile_);
                    return jSDynamicObject;
                }
            }
            if (!JSGuards.isJSDynamicObject(arg0Value)) {
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = GetPrototypeNode.doNotObject(arg0Value);
                return jSDynamicObject;
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
        CachedShapeData s0_;
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cachedShape_cache) == null || s0_.next_ == null)) {
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
        s[0] = "doCachedShape";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            CachedShapeData s0_ = this.cachedShape_cache;
            while (s0_ != null) {
                cached.add(Arrays.asList(s0_.shape_, s0_.prototypeLocation_));
                s0_ = s0_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doGeneric";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doProxy";
        if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.proxy_jsclassProfile_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[3] = s;
        s = new Object[3];
        s[0] = "doNotObject";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        return Introspection.Provider.create(data);
    }

    public static GetPrototypeNode create() {
        return new GetPrototypeNodeGen();
    }

    public static GetPrototypeNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=GetPrototypeNode.class)
    @DenyReplace
    private static final class Uncached
    extends GetPrototypeNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public JSDynamicObject execute(Object arg0Value) {
            if (arg0Value instanceof JSDynamicObject) {
                JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
                if (!JSGuards.isJSProxy(arg0Value_)) {
                    return GetPrototypeNode.doGeneric(arg0Value_);
                }
                if (JSGuards.isJSProxy(arg0Value_)) {
                    return GetPrototypeNode.doProxy(arg0Value_, JSClassProfile.getUncached());
                }
            }
            if (!JSGuards.isJSDynamicObject(arg0Value)) {
                return GetPrototypeNode.doNotObject(arg0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public JSDynamicObject execute(JSDynamicObject arg0Value) {
            if (!JSGuards.isJSProxy(arg0Value)) {
                return GetPrototypeNode.doGeneric(arg0Value);
            }
            if (JSGuards.isJSProxy(arg0Value)) {
                return GetPrototypeNode.doProxy(arg0Value, JSClassProfile.getUncached());
            }
            if (!JSGuards.isJSDynamicObject(arg0Value)) {
                return GetPrototypeNode.doNotObject(arg0Value);
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

    @GeneratedBy(value=GetPrototypeNode.class)
    private static final class CachedShapeData {
        @CompilerDirectives.CompilationFinal
        CachedShapeData next_;
        @CompilerDirectives.CompilationFinal
        Shape shape_;
        @CompilerDirectives.CompilationFinal
        Location prototypeLocation_;

        CachedShapeData(CachedShapeData next_) {
            this.next_ = next_;
        }
    }
}

