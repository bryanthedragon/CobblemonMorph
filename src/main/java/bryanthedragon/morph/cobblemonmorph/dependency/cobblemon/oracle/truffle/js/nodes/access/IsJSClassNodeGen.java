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
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.IsJSClassNode;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=IsJSClassNode.class)
public final class IsJSClassNodeGen
extends IsJSClassNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private IsInstanceShapeData isInstanceShape_cache;

    private IsJSClassNodeGen(JSClass jsclass, JavaScriptNode operand) {
        super(jsclass, operand);
    }

    @Override
    @ExplodeLoop
    public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 3) != 0 && operandNodeValue instanceof JSDynamicObject) {
            JSDynamicObject operandNodeValue_ = (JSDynamicObject)operandNodeValue;
            if ((state_0 & 1) != 0) {
                IsInstanceShapeData s0_ = this.isInstanceShape_cache;
                while (s0_ != null) {
                    if (s0_.cachedShape_.check(operandNodeValue_)) {
                        return IsJSClassNode.doIsInstanceShape(operandNodeValue_, s0_.cachedShape_, s0_.cachedResult_);
                    }
                    s0_ = s0_.next_;
                }
            }
            if ((state_0 & 2) != 0) {
                return this.doIsInstanceObject(operandNodeValue_);
            }
        }
        if ((state_0 & 4) != 0) {
            return this.doIsInstance(operandNodeValue);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue);
    }

    @Override
    @ExplodeLoop
    public boolean executeBoolean(Object operandNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 3) != 0 && operandNodeValue instanceof JSDynamicObject) {
            JSDynamicObject operandNodeValue_ = (JSDynamicObject)operandNodeValue;
            if ((state_0 & 1) != 0) {
                IsInstanceShapeData s0_ = this.isInstanceShape_cache;
                while (s0_ != null) {
                    if (s0_.cachedShape_.check(operandNodeValue_)) {
                        return IsJSClassNode.doIsInstanceShape(operandNodeValue_, s0_.cachedShape_, s0_.cachedResult_);
                    }
                    s0_ = s0_.next_;
                }
            }
            if ((state_0 & 2) != 0) {
                return this.doIsInstanceObject(operandNodeValue_);
            }
        }
        if ((state_0 & 4) != 0) {
            return this.doIsInstance(operandNodeValue);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue);
    }

    @Override
    @ExplodeLoop
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object operandNodeValue_ = this.operandNode.execute(frameValue);
        if ((state_0 & 3) != 0 && operandNodeValue_ instanceof JSDynamicObject) {
            JSDynamicObject operandNodeValue__ = (JSDynamicObject)operandNodeValue_;
            if ((state_0 & 1) != 0) {
                IsInstanceShapeData s0_ = this.isInstanceShape_cache;
                while (s0_ != null) {
                    if (s0_.cachedShape_.check(operandNodeValue__)) {
                        return IsJSClassNode.doIsInstanceShape(operandNodeValue__, s0_.cachedShape_, s0_.cachedResult_);
                    }
                    s0_ = s0_.next_;
                }
            }
            if ((state_0 & 2) != 0) {
                return this.doIsInstanceObject(operandNodeValue__);
            }
        }
        if ((state_0 & 4) != 0) {
            return this.doIsInstance(operandNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue_);
    }

    @Override
    @ExplodeLoop
    public boolean executeBoolean(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object operandNodeValue_ = this.operandNode.execute(frameValue);
        if ((state_0 & 3) != 0 && operandNodeValue_ instanceof JSDynamicObject) {
            JSDynamicObject operandNodeValue__ = (JSDynamicObject)operandNodeValue_;
            if ((state_0 & 1) != 0) {
                IsInstanceShapeData s0_ = this.isInstanceShape_cache;
                while (s0_ != null) {
                    if (s0_.cachedShape_.check(operandNodeValue__)) {
                        return IsJSClassNode.doIsInstanceShape(operandNodeValue__, s0_.cachedShape_, s0_.cachedResult_);
                    }
                    s0_ = s0_.next_;
                }
            }
            if ((state_0 & 2) != 0) {
                return this.doIsInstanceObject(operandNodeValue__);
            }
        }
        if ((state_0 & 4) != 0) {
            return this.doIsInstance(operandNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue_);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.executeBoolean(frameValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean executeAndSpecialize(Object operandNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (operandNodeValue instanceof JSDynamicObject) {
                JSDynamicObject operandNodeValue_ = (JSDynamicObject)operandNodeValue;
                if ((exclude & 1) == 0) {
                    Shape cachedShape__;
                    int count0_ = 0;
                    IsInstanceShapeData s0_ = this.isInstanceShape_cache;
                    if ((state_0 & 1) != 0) {
                        while (s0_ != null && !s0_.cachedShape_.check(operandNodeValue_)) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && (cachedShape__ = operandNodeValue_.getShape()).check(operandNodeValue_) && count0_ < 1) {
                        s0_ = new IsInstanceShapeData(this.isInstanceShape_cache);
                        s0_.cachedShape_ = cachedShape__;
                        s0_.cachedResult_ = this.doIsInstance(operandNodeValue_);
                        VarHandle.storeStoreFence();
                        this.isInstanceShape_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        boolean bl = IsJSClassNode.doIsInstanceShape(operandNodeValue_, s0_.cachedShape_, s0_.cachedResult_);
                        return bl;
                    }
                }
                if ((exclude & 2) == 0) {
                    this.exclude_ = exclude |= 1;
                    this.isInstanceShape_cache = null;
                    state_0 &= 0xFFFFFFFE;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doIsInstanceObject(operandNodeValue_);
                    return bl;
                }
            }
            this.exclude_ = exclude |= 3;
            this.isInstanceShape_cache = null;
            state_0 &= 0xFFFFFFFC;
            this.state_0_ = state_0 |= 4;
            lock.unlock();
            hasLock = false;
            boolean bl = this.doIsInstance(operandNodeValue);
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
        IsInstanceShapeData s0_;
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.isInstanceShape_cache) == null || s0_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[4];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doIsInstanceShape";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<List<Object>> cached = new ArrayList<List<Object>>();
            IsInstanceShapeData s0_ = this.isInstanceShape_cache;
            while (s0_ != null) {
                cached.add(Arrays.asList(s0_.cachedShape_, s0_.cachedResult_));
                s0_ = s0_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = (exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doIsInstanceObject";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : ((exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[2] = s;
        s = new Object[3];
        s[0] = "doIsInstance";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        return Introspection.Provider.create(data);
    }

    public static IsJSClassNode create(JSClass jsclass, JavaScriptNode operand) {
        return new IsJSClassNodeGen(jsclass, operand);
    }

    @GeneratedBy(value=IsJSClassNode.class)
    private static final class IsInstanceShapeData {
        @CompilerDirectives.CompilationFinal
        IsInstanceShapeData next_;
        @CompilerDirectives.CompilationFinal
        Shape cachedShape_;
        @CompilerDirectives.CompilationFinal
        boolean cachedResult_;

        IsInstanceShapeData(IsInstanceShapeData next_) {
            this.next_ = next_;
        }
    }
}

