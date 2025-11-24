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
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.access.ArrayBufferViewGetByteLengthNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.array.TypedArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ArrayBufferViewGetByteLengthNode.class)
public final class ArrayBufferViewGetByteLengthNodeGen
extends ArrayBufferViewGetByteLengthNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private GetByteLengthData getByteLength_cache;

    private ArrayBufferViewGetByteLengthNodeGen(JSContext context) {
        super(context);
    }

    @Override
    @ExplodeLoop
    public int executeInt(JSDynamicObject arg0Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 1) != 0 && JSGuards.isJSArrayBufferView(arg0Value) && this.hasDetachedBuffer(arg0Value)) {
                return this.getByteLengthDetached(arg0Value);
            }
            if ((state_0 & 2) != 0 && JSGuards.isJSArrayBufferView(arg0Value) && !this.hasDetachedBuffer(arg0Value)) {
                GetByteLengthData s1_ = this.getByteLength_cache;
                while (s1_ != null) {
                    if (s1_.cachedArray_ == ArrayBufferViewGetByteLengthNode.getArrayType(arg0Value)) {
                        return this.getByteLength(arg0Value, s1_.cachedArray_);
                    }
                    s1_ = s1_.next_;
                }
            }
            if ((state_0 & 4) != 0 && JSGuards.isJSArrayBufferView(arg0Value) && !this.hasDetachedBuffer(arg0Value)) {
                return this.getByteLengthOverLimit(arg0Value);
            }
            if ((state_0 & 8) != 0 && !JSGuards.isJSArrayBufferView(arg0Value)) {
                return this.getByteLengthNoObj(arg0Value);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    private int executeAndSpecialize(JSDynamicObject arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int n;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (JSGuards.isJSArrayBufferView(arg0Value) && this.hasDetachedBuffer(arg0Value)) {
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n2 = this.getByteLengthDetached(arg0Value);
                return n2;
            }
            if (exclude == 0 && JSGuards.isJSArrayBufferView(arg0Value) && !this.hasDetachedBuffer(arg0Value)) {
                TypedArray cachedArray__;
                int count1_ = 0;
                GetByteLengthData s1_ = this.getByteLength_cache;
                if ((state_0 & 2) != 0) {
                    while (s1_ != null && s1_.cachedArray_ != ArrayBufferViewGetByteLengthNode.getArrayType(arg0Value)) {
                        s1_ = s1_.next_;
                        ++count1_;
                    }
                }
                if (s1_ == null && (cachedArray__ = ArrayBufferViewGetByteLengthNode.getArrayType(arg0Value)) == ArrayBufferViewGetByteLengthNode.getArrayType(arg0Value) && count1_ < 3) {
                    s1_ = new GetByteLengthData(this.getByteLength_cache);
                    s1_.cachedArray_ = cachedArray__;
                    VarHandle.storeStoreFence();
                    this.getByteLength_cache = s1_;
                    this.state_0_ = state_0 |= 2;
                }
                if (s1_ != null) {
                    lock.unlock();
                    hasLock = false;
                    int n3 = this.getByteLength(arg0Value, s1_.cachedArray_);
                    return n3;
                }
            }
            if (JSGuards.isJSArrayBufferView(arg0Value) && !this.hasDetachedBuffer(arg0Value)) {
                this.exclude_ = exclude |= 1;
                this.getByteLength_cache = null;
                state_0 &= 0xFFFFFFFD;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                n = this.getByteLengthOverLimit(arg0Value);
                return n;
            }
            if (!JSGuards.isJSArrayBufferView(arg0Value)) {
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                n = this.getByteLengthNoObj(arg0Value);
                return n;
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
        GetByteLengthData s1_;
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s1_ = this.getByteLength_cache) == null || s1_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[5];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "getByteLengthDetached";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "getByteLength";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<List<TypedArray>> cached = new ArrayList<List<TypedArray>>();
            GetByteLengthData s1_ = this.getByteLength_cache;
            while (s1_ != null) {
                cached.add(Arrays.asList(s1_.cachedArray_));
                s1_ = s1_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[2] = s;
        s = new Object[3];
        s[0] = "getByteLengthOverLimit";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "getByteLengthNoObj";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        return Introspection.Provider.create(data);
    }

    public static ArrayBufferViewGetByteLengthNode create(JSContext context) {
        return new ArrayBufferViewGetByteLengthNodeGen(context);
    }

    @GeneratedBy(value=ArrayBufferViewGetByteLengthNode.class)
    private static final class GetByteLengthData {
        @CompilerDirectives.CompilationFinal
        GetByteLengthData next_;
        @CompilerDirectives.CompilationFinal
        TypedArray cachedArray_;

        GetByteLengthData(GetByteLengthData next_) {
            this.next_ = next_;
        }
    }
}

