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
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.RequireObjectNode;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=RequireObjectNode.class)
public final class RequireObjectNodeGen
extends RequireObjectNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private ObjectShapeData objectShape_cache;

    private RequireObjectNodeGen(JavaScriptNode operand) {
        super(operand);
    }

    @Override
    @ExplodeLoop
    public Object execute(Object operandNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && operandNodeValue instanceof JSDynamicObject) {
            JSDynamicObject operandNodeValue_ = (JSDynamicObject)operandNodeValue;
            ObjectShapeData s0_ = this.objectShape_cache;
            while (s0_ != null) {
                if (s0_.cachedShape_.check(operandNodeValue_)) {
                    return RequireObjectNode.doObjectShape(operandNodeValue_, s0_.cachedShape_, s0_.cachedResult_);
                }
                s0_ = s0_.next_;
            }
        }
        if ((state_0 & 2) != 0) {
            return RequireObjectNode.doObject(operandNodeValue);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue);
    }

    @Override
    @ExplodeLoop
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object operandNodeValue_ = this.operandNode.execute(frameValue);
        if ((state_0 & 1) != 0 && operandNodeValue_ instanceof JSDynamicObject) {
            JSDynamicObject operandNodeValue__ = (JSDynamicObject)operandNodeValue_;
            ObjectShapeData s0_ = this.objectShape_cache;
            while (s0_ != null) {
                if (s0_.cachedShape_.check(operandNodeValue__)) {
                    return RequireObjectNode.doObjectShape(operandNodeValue__, s0_.cachedShape_, s0_.cachedResult_);
                }
                s0_ = s0_.next_;
            }
        }
        if ((state_0 & 2) != 0) {
            return RequireObjectNode.doObject(operandNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue_);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.execute(frameValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object executeAndSpecialize(Object operandNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0 && operandNodeValue instanceof JSDynamicObject) {
                Shape cachedShape__;
                JSDynamicObject operandNodeValue_ = (JSDynamicObject)operandNodeValue;
                int count0_ = 0;
                ObjectShapeData s0_ = this.objectShape_cache;
                if ((state_0 & 1) != 0) {
                    while (s0_ != null && !s0_.cachedShape_.check(operandNodeValue_)) {
                        s0_ = s0_.next_;
                        ++count0_;
                    }
                }
                if (s0_ == null && (cachedShape__ = operandNodeValue_.getShape()).check(operandNodeValue_) && count0_ < 1) {
                    s0_ = new ObjectShapeData(this.objectShape_cache);
                    s0_.cachedShape_ = cachedShape__;
                    s0_.cachedResult_ = JSGuards.isJSObject(operandNodeValue_);
                    VarHandle.storeStoreFence();
                    this.objectShape_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                }
                if (s0_ != null) {
                    lock.unlock();
                    hasLock = false;
                    Object object = RequireObjectNode.doObjectShape(operandNodeValue_, s0_.cachedShape_, s0_.cachedResult_);
                    return object;
                }
            }
            this.exclude_ = exclude |= 1;
            this.objectShape_cache = null;
            state_0 &= 0xFFFFFFFE;
            this.state_0_ = state_0 |= 2;
            lock.unlock();
            hasLock = false;
            Object object = RequireObjectNode.doObject(operandNodeValue);
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
        ObjectShapeData s0_;
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.objectShape_cache) == null || s0_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[3];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doObjectShape";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<List<Object>> cached = new ArrayList<List<Object>>();
            ObjectShapeData s0_ = this.objectShape_cache;
            while (s0_ != null) {
                cached.add(Arrays.asList(s0_.cachedShape_, s0_.cachedResult_));
                s0_ = s0_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doObject";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        return Introspection.Provider.create(data);
    }

    public static RequireObjectNode create(JavaScriptNode operand) {
        return new RequireObjectNodeGen(operand);
    }

    @GeneratedBy(value=RequireObjectNode.class)
    private static final class ObjectShapeData {
        @CompilerDirectives.CompilationFinal
        ObjectShapeData next_;
        @CompilerDirectives.CompilationFinal
        Shape cachedShape_;
        @CompilerDirectives.CompilationFinal
        boolean cachedResult_;

        ObjectShapeData(ObjectShapeData next_) {
            this.next_ = next_;
        }
    }
}

