
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.access.IsArrayNode;
import com.oracle.truffle.js.runtime.builtins.JSArgumentsObject;
import com.oracle.truffle.js.runtime.builtins.JSArrayObject;
import com.oracle.truffle.js.runtime.builtins.JSTypedArrayObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=IsArrayNode.class)
public final class IsArrayNodeGen
extends IsArrayNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private Shape jSFastArrayShape_cachedShape_;
    @CompilerDirectives.CompilationFinal
    private Class<?> otherCached_cachedClass_;

    private IsArrayNodeGen(IsArrayNode.Kind kind) {
        super(kind);
    }

    @Override
    public boolean execute(Object arg0Value) {
        JSNonProxyObject arg0Value_;
        int state_0 = this.state_0_;
        if ((state_0 & 7) != 0 && arg0Value instanceof JSArrayObject) {
            arg0Value_ = (JSArrayObject)arg0Value;
            if ((state_0 & 1) != 0) {
                assert (this.kind == IsArrayNode.Kind.Array || this.kind == IsArrayNode.Kind.AnyArray);
                return this.doJSArray((JSArrayObject)arg0Value_);
            }
            if ((state_0 & 2) != 0) {
                assert (this.kind == IsArrayNode.Kind.FastArray || this.kind == IsArrayNode.Kind.FastOrTypedArray);
                if (arg0Value_.getShape() == this.jSFastArrayShape_cachedShape_) {
                    return this.doJSFastArrayShape((JSArrayObject)arg0Value_, this.jSFastArrayShape_cachedShape_);
                }
            }
            if ((state_0 & 4) != 0) {
                assert (this.kind == IsArrayNode.Kind.FastArray || this.kind == IsArrayNode.Kind.FastOrTypedArray);
                return this.doJSFastArray((JSArrayObject)arg0Value_);
            }
        }
        if ((state_0 & 8) != 0 && arg0Value instanceof JSTypedArrayObject) {
            arg0Value_ = (JSTypedArrayObject)arg0Value;
            assert (this.kind == IsArrayNode.Kind.AnyArray || this.kind == IsArrayNode.Kind.FastOrTypedArray);
            return this.doJSTypedArray((JSTypedArrayObject)arg0Value_);
        }
        if ((state_0 & 0x10) != 0 && arg0Value instanceof JSArgumentsObject) {
            arg0Value_ = (JSArgumentsObject)arg0Value;
            assert (this.kind == IsArrayNode.Kind.AnyArray || this.kind == IsArrayNode.Kind.FastOrTypedArray);
            if (JSGuards.isJSArgumentsObject(arg0Value_)) {
                return this.doJSArgumentsObject((JSArgumentsObject)arg0Value_);
            }
        }
        if ((state_0 & 0x1E0) != 0) {
            if ((state_0 & 0x20) != 0) {
                assert (this.kind == IsArrayNode.Kind.AnyArray);
                if (JSGuards.isJSObjectPrototype(arg0Value)) {
                    return this.doJSObjectPrototype(arg0Value);
                }
            }
            if ((state_0 & 0x40) != 0) {
                assert (this.kind == IsArrayNode.Kind.Array || this.kind == IsArrayNode.Kind.FastArray);
                if (!JSGuards.isJSArray(arg0Value)) {
                    return this.doNotJSArray(arg0Value);
                }
            }
            if ((state_0 & 0x80) != 0 && CompilerDirectives.isExact(arg0Value, this.otherCached_cachedClass_)) {
                return this.doOtherCached(arg0Value, this.otherCached_cachedClass_);
            }
            if ((state_0 & 0x100) != 0) {
                return this.doOther(arg0Value);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean executeAndSpecialize(Object arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            boolean JSFastArrayShape_duplicateFound_;
            JSNonProxyObject arg0Value_;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arg0Value instanceof JSArrayObject) {
                arg0Value_ = (JSArrayObject)arg0Value;
                if ((exclude & 1) == 0 && (this.kind == IsArrayNode.Kind.Array || this.kind == IsArrayNode.Kind.AnyArray)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doJSArray((JSArrayObject)arg0Value_);
                    return bl;
                }
                if ((exclude & 2) == 0) {
                    JSFastArrayShape_duplicateFound_ = false;
                    if ((state_0 & 2) != 0) {
                        assert (this.kind == IsArrayNode.Kind.FastArray || this.kind == IsArrayNode.Kind.FastOrTypedArray);
                        if (arg0Value_.getShape() == this.jSFastArrayShape_cachedShape_) {
                            JSFastArrayShape_duplicateFound_ = true;
                        }
                    }
                    if (!(JSFastArrayShape_duplicateFound_ || this.kind != IsArrayNode.Kind.FastArray && this.kind != IsArrayNode.Kind.FastOrTypedArray)) {
                        Shape jSFastArrayShape_cachedShape__ = this.getInitialArrayShape();
                        if (arg0Value_.getShape() == jSFastArrayShape_cachedShape__ && (state_0 & 2) == 0) {
                            this.jSFastArrayShape_cachedShape_ = jSFastArrayShape_cachedShape__;
                            this.state_0_ = state_0 |= 2;
                            JSFastArrayShape_duplicateFound_ = true;
                        }
                    }
                    if (JSFastArrayShape_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        boolean bl = this.doJSFastArrayShape((JSArrayObject)arg0Value_, this.jSFastArrayShape_cachedShape_);
                        return bl;
                    }
                }
                if ((exclude & 4) == 0 && (this.kind == IsArrayNode.Kind.FastArray || this.kind == IsArrayNode.Kind.FastOrTypedArray)) {
                    this.exclude_ = exclude |= 2;
                    state_0 &= 0xFFFFFFFD;
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    JSFastArrayShape_duplicateFound_ = this.doJSFastArray((JSArrayObject)arg0Value_);
                    return JSFastArrayShape_duplicateFound_;
                }
            }
            if ((exclude & 8) == 0 && arg0Value instanceof JSTypedArrayObject) {
                arg0Value_ = (JSTypedArrayObject)arg0Value;
                if (this.kind == IsArrayNode.Kind.AnyArray || this.kind == IsArrayNode.Kind.FastOrTypedArray) {
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    JSFastArrayShape_duplicateFound_ = this.doJSTypedArray((JSTypedArrayObject)arg0Value_);
                    return JSFastArrayShape_duplicateFound_;
                }
            }
            if ((exclude & 0x10) == 0 && arg0Value instanceof JSArgumentsObject) {
                arg0Value_ = (JSArgumentsObject)arg0Value;
                if ((this.kind == IsArrayNode.Kind.AnyArray || this.kind == IsArrayNode.Kind.FastOrTypedArray) && JSGuards.isJSArgumentsObject(arg0Value_)) {
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    JSFastArrayShape_duplicateFound_ = this.doJSArgumentsObject((JSArgumentsObject)arg0Value_);
                    return JSFastArrayShape_duplicateFound_;
                }
            }
            if ((exclude & 0x20) == 0 && this.kind == IsArrayNode.Kind.AnyArray && JSGuards.isJSObjectPrototype(arg0Value)) {
                this.state_0_ = state_0 |= 0x20;
                lock.unlock();
                hasLock = false;
                boolean arg0Value_2 = this.doJSObjectPrototype(arg0Value);
                return arg0Value_2;
            }
            if (!(this.kind != IsArrayNode.Kind.Array && this.kind != IsArrayNode.Kind.FastArray || JSGuards.isJSArray(arg0Value))) {
                this.state_0_ = state_0 |= 0x40;
                lock.unlock();
                hasLock = false;
                boolean arg0Value_2 = this.doNotJSArray(arg0Value);
                return arg0Value_2;
            }
            if ((exclude & 0x40) == 0) {
                Class<?> otherCached_cachedClass__;
                boolean OtherCached_duplicateFound_ = false;
                if ((state_0 & 0x80) != 0 && CompilerDirectives.isExact(arg0Value, this.otherCached_cachedClass_)) {
                    OtherCached_duplicateFound_ = true;
                }
                if (!OtherCached_duplicateFound_ && CompilerDirectives.isExact(arg0Value, otherCached_cachedClass__ = arg0Value.getClass()) && (state_0 & 0x80) == 0) {
                    this.otherCached_cachedClass_ = otherCached_cachedClass__;
                    this.state_0_ = state_0 |= 0x80;
                    OtherCached_duplicateFound_ = true;
                }
                if (OtherCached_duplicateFound_) {
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doOtherCached(arg0Value, this.otherCached_cachedClass_);
                    return bl;
                }
            }
            this.exclude_ = exclude |= 0x7F;
            state_0 &= 0xFFFFFF40;
            this.state_0_ = state_0 |= 0x100;
            lock.unlock();
            hasLock = false;
            boolean bl = this.doOther(arg0Value);
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
        ArrayList<List<Object>> cached;
        Object[] data = new Object[10];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doJSArray";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : ((exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[1] = s;
        s = new Object[3];
        s[0] = "doJSFastArrayShape";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            cached.add(Arrays.asList(this.jSFastArrayShape_cachedShape_));
            s[2] = cached;
        } else {
            s[1] = (exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[2] = s;
        s = new Object[3];
        s[0] = "doJSFastArray";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : ((exclude & 4) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[3] = s;
        s = new Object[3];
        s[0] = "doJSTypedArray";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : ((exclude & 8) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[4] = s;
        s = new Object[3];
        s[0] = "doJSArgumentsObject";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : ((exclude & 0x10) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[5] = s;
        s = new Object[3];
        s[0] = "doJSObjectPrototype";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : ((exclude & 0x20) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[6] = s;
        s = new Object[3];
        s[0] = "doNotJSArray";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[7] = s;
        s = new Object[3];
        s[0] = "doOtherCached";
        if ((state_0 & 0x80) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.otherCached_cachedClass_));
            s[2] = cached;
        } else {
            s[1] = (exclude & 0x40) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[8] = s;
        s = new Object[3];
        s[0] = "doOther";
        s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[9] = s;
        return Introspection.Provider.create(data);
    }

    public static IsArrayNode create(IsArrayNode.Kind kind) {
        return new IsArrayNodeGen(kind);
    }
}

