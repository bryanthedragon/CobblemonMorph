
package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.unary.JSIsArrayNode;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSIsArrayNode.class)
public final class JSIsArrayNodeGen
extends JSIsArrayNode
implements Introspection.Provider {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private Class<?> isArrayClass_cachedClass_;
    @CompilerDirectives.CompilationFinal
    private boolean isArrayClass_cachedIsArray_;
    @CompilerDirectives.CompilationFinal
    private boolean isArrayClass_cachedIsProxy_;
    @Node.Child
    private InteropLibrary primitiveOrForeign_interop_;

    private JSIsArrayNodeGen(boolean jsType) {
        super(jsType);
    }

    @Override
    public boolean execute(Object arg0Value) {
        JSDynamicObject arg0Value_;
        int state_0 = this.state_0_;
        if ((state_0 & 3) != 0) {
            if ((state_0 & 1) != 0) {
                assert (!this.isArrayClass_cachedIsProxy_);
                assert (this.isArrayClass_cachedClass_ != null);
                if (CompilerDirectives.isExact(arg0Value, this.isArrayClass_cachedClass_)) {
                    return JSIsArrayNode.doIsArrayClass(arg0Value, this.isArrayClass_cachedClass_, this.isArrayClass_cachedIsArray_, this.isArrayClass_cachedIsProxy_);
                }
            }
            if ((state_0 & 2) != 0 && JSGuards.isJSArray(arg0Value)) {
                return this.doJSArray(arg0Value);
            }
        }
        if ((state_0 & 4) != 0 && arg0Value instanceof JSDynamicObject && JSGuards.isJSProxy(arg0Value_ = (JSDynamicObject)arg0Value)) {
            return this.doJSProxy(arg0Value_);
        }
        if ((state_0 & 0x38) != 0) {
            if ((state_0 & 8) != 0 && !JSGuards.isJSArray(arg0Value) && !JSGuards.isJSProxy(arg0Value) && JSGuards.isJSDynamicObject(arg0Value)) {
                return this.doJSObject(arg0Value);
            }
            if ((state_0 & 0x10) != 0 && !JSGuards.isJSDynamicObject(arg0Value)) {
                assert (this.jsType);
                return this.doNotObject(arg0Value);
            }
            if ((state_0 & 0x20) != 0 && !JSGuards.isJSDynamicObject(arg0Value)) {
                assert (!this.jsType);
                return this.doPrimitiveOrForeign(arg0Value, this.primitiveOrForeign_interop_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    private boolean executeAndSpecialize(Object arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            JSDynamicObject arg0Value_;
            boolean bl;
            boolean IsArrayClass_duplicateFound_;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
                Class<?> isArrayClass_cachedClass__;
                boolean isArrayClass_cachedIsProxy__;
                IsArrayClass_duplicateFound_ = false;
                if ((state_0 & 1) != 0) {
                    assert (!this.isArrayClass_cachedIsProxy_);
                    assert (this.isArrayClass_cachedClass_ != null);
                    if (CompilerDirectives.isExact(arg0Value, this.isArrayClass_cachedClass_)) {
                        IsArrayClass_duplicateFound_ = true;
                    }
                }
                if (!IsArrayClass_duplicateFound_ && !(isArrayClass_cachedIsProxy__ = JSGuards.isJSProxy(arg0Value)) && (isArrayClass_cachedClass__ = JSGuards.getClassIfJSDynamicObject(arg0Value)) != null && CompilerDirectives.isExact(arg0Value, isArrayClass_cachedClass__) && (state_0 & 1) == 0) {
                    this.isArrayClass_cachedClass_ = isArrayClass_cachedClass__;
                    this.isArrayClass_cachedIsArray_ = JSGuards.isJSArray(arg0Value);
                    this.isArrayClass_cachedIsProxy_ = isArrayClass_cachedIsProxy__;
                    this.state_0_ = state_0 |= 1;
                    IsArrayClass_duplicateFound_ = true;
                }
                if (IsArrayClass_duplicateFound_) {
                    lock.unlock();
                    hasLock = false;
                    bl = JSIsArrayNode.doIsArrayClass(arg0Value, this.isArrayClass_cachedClass_, this.isArrayClass_cachedIsArray_, this.isArrayClass_cachedIsProxy_);
                    return bl;
                }
            }
            if (JSGuards.isJSArray(arg0Value)) {
                this.exclude_ = exclude |= 1;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                IsArrayClass_duplicateFound_ = this.doJSArray(arg0Value);
                return IsArrayClass_duplicateFound_;
            }
            if (arg0Value instanceof JSDynamicObject && JSGuards.isJSProxy(arg0Value_ = (JSDynamicObject)arg0Value)) {
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                bl = this.doJSProxy(arg0Value_);
                return bl;
            }
            if (!JSGuards.isJSArray(arg0Value) && !JSGuards.isJSProxy(arg0Value) && JSGuards.isJSDynamicObject(arg0Value)) {
                this.exclude_ = exclude |= 1;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                boolean bl2 = this.doJSObject(arg0Value);
                return bl2;
            }
            if (!JSGuards.isJSDynamicObject(arg0Value) && this.jsType) {
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                boolean bl3 = this.doNotObject(arg0Value);
                return bl3;
            }
            if (!JSGuards.isJSDynamicObject(arg0Value) && !this.jsType) {
                this.primitiveOrForeign_interop_ = super.insert(INTEROP_LIBRARY_.createDispatched(6));
                this.state_0_ = state_0 |= 0x20;
                lock.unlock();
                hasLock = false;
                boolean bl4 = this.doPrimitiveOrForeign(arg0Value, this.primitiveOrForeign_interop_);
                return bl4;
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
        Object[] data = new Object[7];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doIsArrayClass";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            cached.add(Arrays.asList(this.isArrayClass_cachedClass_, this.isArrayClass_cachedIsArray_, this.isArrayClass_cachedIsProxy_));
            s[2] = cached;
        } else {
            s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doJSArray";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doJSProxy";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doJSObject";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doNotObject";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doPrimitiveOrForeign";
        if ((state_0 & 0x20) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.primitiveOrForeign_interop_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[6] = s;
        return Introspection.Provider.create(data);
    }

    public static JSIsArrayNode create(boolean jsType) {
        return new JSIsArrayNodeGen(jsType);
    }
}

