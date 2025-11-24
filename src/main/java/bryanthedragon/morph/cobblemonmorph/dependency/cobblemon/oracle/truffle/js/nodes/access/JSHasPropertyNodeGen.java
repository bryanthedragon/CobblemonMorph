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
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.IntToLongTypeSystemGen;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.access.HasPropertyCacheNode;
import com.oracle.truffle.js.nodes.access.JSHasPropertyNode;
import com.oracle.truffle.js.nodes.cast.JSToPropertyKeyNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.interop.ForeignObjectPrototypeNode;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSTypedArrayObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSHasPropertyNode.class)
public final class JSHasPropertyNodeGen
extends JSHasPropertyNode
implements Introspection.Provider {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private ArrayLongCachedData arrayLongCached_cache;
    @Node.Child
    private ObjectStringCachedData objectStringCached_cache;
    @CompilerDirectives.CompilationFinal
    private String arrayStringCached_cachedName_;
    @Node.Child
    private HasPropertyCacheNode arrayStringCached_hasPropertyNode_;
    @Node.Child
    private ForeignObject0Data foreignObject0_cache;
    @Node.Child
    private ForeignObject1Data foreignObject1_cache;
    @Node.Child
    private JSToPropertyKeyNode objectObject_toPropertyKeyNode_;

    private JSHasPropertyNodeGen(boolean hasOwnProperty) {
        super(hasOwnProperty);
    }

    @Override
    @ExplodeLoop
    public boolean executeBoolean(Object arg0Value, Object arg1Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 7) != 0 && IntToLongTypeSystemGen.isImplicitLong((state_0 & 0x1800) >>> 11, arg1Value)) {
            JSDynamicObject arg0Value_;
            long arg1Value_ = IntToLongTypeSystemGen.asImplicitLong((state_0 & 0x1800) >>> 11, arg1Value);
            if ((state_0 & 3) != 0 && arg0Value instanceof JSDynamicObject) {
                arg0Value_ = (JSDynamicObject)arg0Value;
                if ((state_0 & 1) != 0 && JSGuards.isJSFastArray(arg0Value_) && JSRuntime.isArrayIndex(arg1Value_)) {
                    ArrayLongCachedData s0_ = this.arrayLongCached_cache;
                    while (s0_ != null) {
                        if (s0_.cachedArrayType_.isInstance(JSHasPropertyNode.getArrayType(arg0Value_))) {
                            return this.arrayLongCached(arg0Value_, arg1Value_, s0_.cachedArrayType_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0 && JSGuards.isJSFastArray(arg0Value_) && JSRuntime.isArrayIndex(arg1Value_)) {
                    return this.arrayLong(arg0Value_, arg1Value_);
                }
            }
            if ((state_0 & 4) != 0 && arg0Value instanceof JSTypedArrayObject) {
                arg0Value_ = (JSTypedArrayObject)arg0Value;
                return this.typedArray((JSTypedArrayObject)arg0Value_, arg1Value_);
            }
        }
        if ((state_0 & 0xF8) != 0 && arg0Value instanceof JSDynamicObject) {
            Object arg1Value_;
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            if ((state_0 & 0x38) != 0 && arg1Value instanceof String) {
                ObjectStringCachedData s3_;
                arg1Value_ = (String)arg1Value;
                if ((state_0 & 8) != 0 && (s3_ = this.objectStringCached_cache) != null) {
                    assert (s3_.cachedObjectType_ != null);
                    if (s3_.cachedObjectType_.isInstance(arg0Value_) && s3_.cachedName_.equals(arg1Value_)) {
                        return this.objectStringCached(arg0Value_, (String)arg1Value_, s3_.cachedObjectType_, s3_.cachedName_, s3_.hasPropertyNode_);
                    }
                }
                if ((state_0 & 0x10) != 0 && JSGuards.isJSArray(arg0Value_)) {
                    assert (!JSRuntime.isArrayIndex(this.arrayStringCached_cachedName_));
                    if (this.arrayStringCached_cachedName_.equals(arg1Value_)) {
                        return this.arrayStringCached(arg0Value_, (String)arg1Value_, this.arrayStringCached_cachedName_, this.arrayStringCached_hasPropertyNode_);
                    }
                }
                if ((state_0 & 0x20) != 0 && JSGuards.isJSDynamicObject(arg0Value_)) {
                    return this.objectOrArrayString(arg0Value_, (String)arg1Value_);
                }
            }
            if ((state_0 & 0x40) != 0 && arg1Value instanceof Symbol) {
                arg1Value_ = (Symbol)arg1Value;
                if (JSGuards.isJSDynamicObject(arg0Value_)) {
                    return this.objectSymbol(arg0Value_, (Symbol)arg1Value_);
                }
            }
            if ((state_0 & 0x80) != 0 && IntToLongTypeSystemGen.isImplicitLong((state_0 & 0x1800) >>> 11, arg1Value)) {
                long arg1Value_2 = IntToLongTypeSystemGen.asImplicitLong((state_0 & 0x1800) >>> 11, arg1Value);
                if (JSGuards.isJSDynamicObject(arg0Value_) && !JSGuards.isJSFastArray(arg0Value_) && !JSGuards.isJSArrayBufferView(arg0Value_)) {
                    return this.objectLong(arg0Value_, arg1Value_2);
                }
            }
        }
        if ((state_0 & 0x700) != 0) {
            JSDynamicObject arg0Value_;
            if ((state_0 & 0x300) != 0) {
                ForeignObject1Data s9_;
                if ((state_0 & 0x100) != 0) {
                    ForeignObject0Data s8_ = this.foreignObject0_cache;
                    while (s8_ != null) {
                        if (s8_.interop_.accepts(arg0Value) && JSRuntime.isForeignObject(arg0Value)) {
                            return this.foreignObject(arg0Value, arg1Value, s8_.interop_, s8_.toStringNode_, s8_.foreignObjectPrototypeNode_, s8_.hasInPrototype_);
                        }
                        s8_ = s8_.next_;
                    }
                }
                if ((state_0 & 0x200) != 0 && (s9_ = this.foreignObject1_cache) != null && JSRuntime.isForeignObject(arg0Value)) {
                    return this.foreignObject1Boundary(state_0, s9_, arg0Value, arg1Value);
                }
            }
            if ((state_0 & 0x400) != 0 && arg0Value instanceof JSDynamicObject && JSGuards.isJSDynamicObject(arg0Value_ = (JSDynamicObject)arg0Value)) {
                return this.objectObject(arg0Value_, arg1Value, this.objectObject_toPropertyKeyNode_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private boolean foreignObject1Boundary(int state_0, ForeignObject1Data s9_, Object arg0Value, Object arg1Value) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
            boolean bl = this.foreignObject(arg0Value, arg1Value, interop__, s9_.toStringNode_, s9_.foreignObjectPrototypeNode_, s9_.hasInPrototype_);
            return bl;
        }
        finally {
            encapsulating_.set(prev_);
        }
    }

    @Override
    @ExplodeLoop
    public boolean executeBoolean(Object arg0Value, long arg1Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 0x87) != 0 && IntToLongTypeSystemGen.isImplicitLong((state_0 & 0x1800) >>> 11, arg1Value)) {
            JSDynamicObject arg0Value_;
            long arg1Value_ = IntToLongTypeSystemGen.asImplicitLong((state_0 & 0x1800) >>> 11, arg1Value);
            if ((state_0 & 3) != 0 && arg0Value instanceof JSDynamicObject) {
                arg0Value_ = (JSDynamicObject)arg0Value;
                if ((state_0 & 1) != 0 && JSGuards.isJSFastArray(arg0Value_) && JSRuntime.isArrayIndex(arg1Value_)) {
                    ArrayLongCachedData s0_ = this.arrayLongCached_cache;
                    while (s0_ != null) {
                        if (s0_.cachedArrayType_.isInstance(JSHasPropertyNode.getArrayType(arg0Value_))) {
                            return this.arrayLongCached(arg0Value_, arg1Value_, s0_.cachedArrayType_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0 && JSGuards.isJSFastArray(arg0Value_) && JSRuntime.isArrayIndex(arg1Value_)) {
                    return this.arrayLong(arg0Value_, arg1Value_);
                }
            }
            if ((state_0 & 4) != 0 && arg0Value instanceof JSTypedArrayObject) {
                arg0Value_ = (JSTypedArrayObject)arg0Value;
                return this.typedArray((JSTypedArrayObject)arg0Value_, arg1Value_);
            }
            if ((state_0 & 0x80) != 0 && arg0Value instanceof JSDynamicObject && JSGuards.isJSDynamicObject(arg0Value_ = (JSDynamicObject)arg0Value) && !JSGuards.isJSFastArray(arg0Value_) && !JSGuards.isJSArrayBufferView(arg0Value_)) {
                return this.objectLong(arg0Value_, arg1Value_);
            }
        }
        if ((state_0 & 0x700) != 0) {
            JSDynamicObject arg0Value_;
            if ((state_0 & 0x300) != 0) {
                ForeignObject1Data s9_;
                if ((state_0 & 0x100) != 0) {
                    ForeignObject0Data s8_ = this.foreignObject0_cache;
                    while (s8_ != null) {
                        if (s8_.interop_.accepts(arg0Value) && JSRuntime.isForeignObject(arg0Value)) {
                            return this.foreignObject(arg0Value, arg1Value, s8_.interop_, s8_.toStringNode_, s8_.foreignObjectPrototypeNode_, s8_.hasInPrototype_);
                        }
                        s8_ = s8_.next_;
                    }
                }
                if ((state_0 & 0x200) != 0 && (s9_ = this.foreignObject1_cache) != null && JSRuntime.isForeignObject(arg0Value)) {
                    return this.foreignObject1Boundary0(state_0, s9_, arg0Value, arg1Value);
                }
            }
            if ((state_0 & 0x400) != 0 && arg0Value instanceof JSDynamicObject && JSGuards.isJSDynamicObject(arg0Value_ = (JSDynamicObject)arg0Value)) {
                return this.objectObject(arg0Value_, arg1Value, this.objectObject_toPropertyKeyNode_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private boolean foreignObject1Boundary0(int state_0, ForeignObject1Data s9_, Object arg0Value, long arg1Value) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
            boolean bl = this.foreignObject(arg0Value, arg1Value, interop__, s9_.toStringNode_, s9_.foreignObjectPrototypeNode_, s9_.hasInPrototype_);
            return bl;
        }
        finally {
            encapsulating_.set(prev_);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean executeAndSpecialize(Object arg0Value, Object arg1Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            int oldState_0 = state_0 & 0x7FF;
            try {
                JSDynamicObject arg0Value_;
                int longCast1 = IntToLongTypeSystemGen.specializeImplicitLong(arg1Value);
                if (longCast1 != 0) {
                    int count0_;
                    JSDynamicObject arg0Value_2;
                    long arg1Value_ = IntToLongTypeSystemGen.asImplicitLong(longCast1, arg1Value);
                    if (arg0Value instanceof JSDynamicObject) {
                        arg0Value_2 = (JSDynamicObject)arg0Value;
                        if ((exclude & 1) == 0 && JSGuards.isJSFastArray(arg0Value_2) && JSRuntime.isArrayIndex(arg1Value_)) {
                            count0_ = 0;
                            ArrayLongCachedData s0_ = this.arrayLongCached_cache;
                            if ((state_0 & 1) != 0) {
                                while (s0_ != null && !s0_.cachedArrayType_.isInstance(JSHasPropertyNode.getArrayType(arg0Value_2))) {
                                    s0_ = s0_.next_;
                                    count0_ += 1;
                                }
                            }
                            if (s0_ == null) {
                                ScriptArray cachedArrayType__ = JSHasPropertyNode.getArrayType(arg0Value_2);
                                if (cachedArrayType__.isInstance(JSHasPropertyNode.getArrayType(arg0Value_2)) && count0_ < 3) {
                                    s0_ = new ArrayLongCachedData(this.arrayLongCached_cache);
                                    s0_.cachedArrayType_ = cachedArrayType__;
                                    VarHandle.storeStoreFence();
                                    this.arrayLongCached_cache = s0_;
                                    state_0 |= longCast1 << 11;
                                    this.state_0_ = state_0 |= 1;
                                }
                            } else {
                                state_0 |= longCast1 << 11;
                                this.state_0_ = state_0 |= 1;
                            }
                            if (s0_ != null) {
                                lock.unlock();
                                hasLock = false;
                                boolean bl = this.arrayLongCached(arg0Value_2, arg1Value_, s0_.cachedArrayType_);
                                return bl;
                            }
                        }
                        if (JSGuards.isJSFastArray(arg0Value_2) && JSRuntime.isArrayIndex(arg1Value_)) {
                            this.exclude_ = exclude |= 1;
                            this.arrayLongCached_cache = null;
                            state_0 &= 0xFFFFFFFE;
                            state_0 |= longCast1 << 11;
                            this.state_0_ = state_0 |= 2;
                            lock.unlock();
                            hasLock = false;
                            count0_ = this.arrayLong(arg0Value_2, arg1Value_);
                            return count0_;
                        }
                    }
                    if (arg0Value instanceof JSTypedArrayObject) {
                        arg0Value_2 = (JSTypedArrayObject)arg0Value;
                        state_0 |= longCast1 << 11;
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        count0_ = this.typedArray((JSTypedArrayObject)arg0Value_2, arg1Value_);
                        return count0_;
                    }
                }
                if (arg0Value instanceof JSDynamicObject) {
                    int longCast12;
                    JSDynamicObject arg0Value_3 = (JSDynamicObject)arg0Value;
                    if (arg1Value instanceof String) {
                        String arg1Value_ = (String)arg1Value;
                        if ((exclude & 2) == 0) {
                            JSClass cachedObjectType__;
                            ObjectStringCachedData s3_ = this.objectStringCached_cache;
                            boolean ObjectStringCached_duplicateFound_ = false;
                            if ((state_0 & 8) != 0) {
                                assert (s3_.cachedObjectType_ != null);
                                if (s3_.cachedObjectType_.isInstance(arg0Value_3) && s3_.cachedName_.equals(arg1Value_)) {
                                    ObjectStringCached_duplicateFound_ = true;
                                }
                            }
                            if (!ObjectStringCached_duplicateFound_ && (cachedObjectType__ = JSHasPropertyNode.getCacheableObjectType(arg0Value_3)) != null && cachedObjectType__.isInstance(arg0Value_3) && (state_0 & 8) == 0) {
                                s3_ = super.insert(new ObjectStringCachedData());
                                s3_.cachedObjectType_ = cachedObjectType__;
                                s3_.cachedName_ = arg1Value_;
                                s3_.hasPropertyNode_ = s3_.insertAccessor(this.getCachedPropertyGetter(arg0Value_3, arg1Value_));
                                VarHandle.storeStoreFence();
                                this.objectStringCached_cache = s3_;
                                this.state_0_ = state_0 |= 8;
                                ObjectStringCached_duplicateFound_ = true;
                            }
                            if (ObjectStringCached_duplicateFound_) {
                                lock.unlock();
                                hasLock = false;
                                boolean bl = this.objectStringCached(arg0Value_3, arg1Value_, s3_.cachedObjectType_, s3_.cachedName_, s3_.hasPropertyNode_);
                                return bl;
                            }
                        }
                        if ((exclude & 4) == 0) {
                            String arrayStringCached_cachedName__2;
                            boolean ArrayStringCached_duplicateFound_ = false;
                            if ((state_0 & 0x10) != 0 && JSGuards.isJSArray(arg0Value_3)) {
                                assert (!JSRuntime.isArrayIndex(this.arrayStringCached_cachedName_));
                                if (this.arrayStringCached_cachedName_.equals(arg1Value_)) {
                                    ArrayStringCached_duplicateFound_ = true;
                                }
                            }
                            if (!ArrayStringCached_duplicateFound_ && JSGuards.isJSArray(arg0Value_3) && !JSRuntime.isArrayIndex(arrayStringCached_cachedName__2 = arg1Value_) && (state_0 & 0x10) == 0) {
                                this.arrayStringCached_cachedName_ = arrayStringCached_cachedName__2;
                                this.arrayStringCached_hasPropertyNode_ = super.insert(this.getCachedPropertyGetter(arg0Value_3, arg1Value_));
                                this.state_0_ = state_0 |= 0x10;
                                ArrayStringCached_duplicateFound_ = true;
                            }
                            if (ArrayStringCached_duplicateFound_) {
                                lock.unlock();
                                hasLock = false;
                                boolean arrayStringCached_cachedName__2 = this.arrayStringCached(arg0Value_3, arg1Value_, this.arrayStringCached_cachedName_, this.arrayStringCached_hasPropertyNode_);
                                return arrayStringCached_cachedName__2;
                            }
                        }
                        if (JSGuards.isJSDynamicObject(arg0Value_3)) {
                            this.exclude_ = exclude |= 6;
                            this.objectStringCached_cache = null;
                            state_0 &= 0xFFFFFFE7;
                            this.state_0_ = state_0 |= 0x20;
                            lock.unlock();
                            hasLock = false;
                            boolean ArrayStringCached_duplicateFound_ = this.objectOrArrayString(arg0Value_3, arg1Value_);
                            return ArrayStringCached_duplicateFound_;
                        }
                    }
                    if (arg1Value instanceof Symbol) {
                        Symbol arg1Value_ = (Symbol)arg1Value;
                        if (JSGuards.isJSDynamicObject(arg0Value_3)) {
                            this.state_0_ = state_0 |= 0x40;
                            lock.unlock();
                            hasLock = false;
                            boolean ArrayStringCached_duplicateFound_ = this.objectSymbol(arg0Value_3, arg1Value_);
                            return ArrayStringCached_duplicateFound_;
                        }
                    }
                    if ((longCast12 = IntToLongTypeSystemGen.specializeImplicitLong(arg1Value)) != 0) {
                        long arg1Value_ = IntToLongTypeSystemGen.asImplicitLong(longCast12, arg1Value);
                        if (JSGuards.isJSDynamicObject(arg0Value_3) && !JSGuards.isJSFastArray(arg0Value_3) && !JSGuards.isJSArrayBufferView(arg0Value_3)) {
                            state_0 |= longCast12 << 11;
                            this.state_0_ = state_0 |= 0x80;
                            lock.unlock();
                            hasLock = false;
                            boolean bl = this.objectLong(arg0Value_3, arg1Value_);
                            return bl;
                        }
                    }
                }
                if ((exclude & 8) == 0) {
                    int count8_ = 0;
                    ForeignObject0Data s8_ = this.foreignObject0_cache;
                    if ((state_0 & 0x100) != 0) {
                        while (!(s8_ == null || s8_.interop_.accepts(arg0Value) && JSRuntime.isForeignObject(arg0Value))) {
                            s8_ = s8_.next_;
                            ++count8_;
                        }
                    }
                    if (s8_ == null && JSRuntime.isForeignObject(arg0Value) && count8_ < 5) {
                        s8_ = super.insert(new ForeignObject0Data(this.foreignObject0_cache));
                        s8_.interop_ = s8_.insertAccessor(INTEROP_LIBRARY_.create(arg0Value));
                        s8_.toStringNode_ = s8_.insertAccessor(JSToStringNode.create());
                        s8_.foreignObjectPrototypeNode_ = s8_.insertAccessor(ForeignObjectPrototypeNode.create());
                        s8_.hasInPrototype_ = s8_.insertAccessor(JSHasPropertyNode.create());
                        VarHandle.storeStoreFence();
                        this.foreignObject0_cache = s8_;
                        this.state_0_ = state_0 |= 0x100;
                    }
                    if (s8_ != null) {
                        lock.unlock();
                        hasLock = false;
                        boolean arg1Value_ = this.foreignObject(arg0Value, arg1Value, s8_.interop_, s8_.toStringNode_, s8_.foreignObjectPrototypeNode_, s8_.hasInPrototype_);
                        return arg1Value_;
                    }
                }
                InteropLibrary interop__ = null;
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    if (JSRuntime.isForeignObject(arg0Value)) {
                        ForeignObject1Data s9_ = super.insert(new ForeignObject1Data());
                        interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
                        s9_.toStringNode_ = s9_.insertAccessor(JSToStringNode.create());
                        s9_.foreignObjectPrototypeNode_ = s9_.insertAccessor(ForeignObjectPrototypeNode.create());
                        s9_.hasInPrototype_ = s9_.insertAccessor(JSHasPropertyNode.create());
                        VarHandle.storeStoreFence();
                        this.foreignObject1_cache = s9_;
                        this.exclude_ = exclude |= 8;
                        this.foreignObject0_cache = null;
                        state_0 &= 0xFFFFFEFF;
                        this.state_0_ = state_0 |= 0x200;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = this.foreignObject(arg0Value, arg1Value, interop__, s9_.toStringNode_, s9_.foreignObjectPrototypeNode_, s9_.hasInPrototype_);
                        return bl;
                    }
                }
                finally {
                    encapsulating_.set(prev_);
                }
                if (arg0Value instanceof JSDynamicObject && JSGuards.isJSDynamicObject(arg0Value_ = (JSDynamicObject)arg0Value)) {
                    this.objectObject_toPropertyKeyNode_ = super.insert(JSToPropertyKeyNode.create());
                    this.state_0_ = state_0 |= 0x400;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.objectObject(arg0Value_, arg1Value, this.objectObject_toPropertyKeyNode_);
                    return bl;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }
            finally {
                if (oldState_0 != 0) {
                    this.checkForPolymorphicSpecialize(oldState_0);
                }
            }
        }
        finally {
            if (hasLock) {
                lock.unlock();
            }
        }
    }

    private void checkForPolymorphicSpecialize(int oldState_0) {
        if ((oldState_0 & 0x460) == 0 && (this.state_0_ & 0x460) != 0) {
            this.reportPolymorphicSpecialize();
        }
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if ((state_0 & 0x7FF) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0x7FF & (state_0 & 0x7FF) - 1) == 0) {
            ArrayLongCachedData s0_ = this.arrayLongCached_cache;
            ForeignObject0Data s8_ = this.foreignObject0_cache;
            if (!(s0_ != null && s0_.next_ != null || s8_ != null && s8_.next_ != null)) {
                return NodeCost.MONOMORPHIC;
            }
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Object>> cached;
        Object[] data = new Object[12];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "arrayLongCached";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            ArrayLongCachedData s0_ = this.arrayLongCached_cache;
            while (s0_ != null) {
                cached.add(Arrays.asList(s0_.cachedArrayType_));
                s0_ = s0_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = (exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "arrayLong";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "typedArray";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "objectStringCached";
        if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            ObjectStringCachedData s3_ = this.objectStringCached_cache;
            if (s3_ != null) {
                cached.add(Arrays.asList(s3_.cachedObjectType_, s3_.cachedName_, s3_.hasPropertyNode_));
            }
            s[2] = cached;
        } else {
            s[1] = (exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[4] = s;
        s = new Object[3];
        s[0] = "arrayStringCached";
        if ((state_0 & 0x10) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.arrayStringCached_cachedName_, this.arrayStringCached_hasPropertyNode_));
            s[2] = cached;
        } else {
            s[1] = (exclude & 4) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[5] = s;
        s = new Object[3];
        s[0] = "objectOrArrayString";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        s = new Object[3];
        s[0] = "objectSymbol";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[7] = s;
        s = new Object[3];
        s[0] = "objectLong";
        s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[8] = s;
        s = new Object[3];
        s[0] = "foreignObject";
        if ((state_0 & 0x100) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            ForeignObject0Data s8_ = this.foreignObject0_cache;
            while (s8_ != null) {
                cached.add(Arrays.asList(s8_.interop_, s8_.toStringNode_, s8_.foreignObjectPrototypeNode_, s8_.hasInPrototype_));
                s8_ = s8_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = (exclude & 8) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[9] = s;
        s = new Object[3];
        s[0] = "foreignObject";
        if ((state_0 & 0x200) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            ForeignObject1Data s9_ = this.foreignObject1_cache;
            if (s9_ != null) {
                cached.add(Arrays.asList(s9_.toStringNode_, s9_.foreignObjectPrototypeNode_, s9_.hasInPrototype_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[10] = s;
        s = new Object[3];
        s[0] = "objectObject";
        if ((state_0 & 0x400) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.objectObject_toPropertyKeyNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[11] = s;
        return Introspection.Provider.create(data);
    }

    public static JSHasPropertyNode create(boolean hasOwnProperty) {
        return new JSHasPropertyNodeGen(hasOwnProperty);
    }

    @GeneratedBy(value=JSHasPropertyNode.class)
    private static final class ForeignObject1Data
    extends Node {
        @Node.Child
        JSToStringNode toStringNode_;
        @Node.Child
        ForeignObjectPrototypeNode foreignObjectPrototypeNode_;
        @Node.Child
        JSHasPropertyNode hasInPrototype_;

        ForeignObject1Data() {
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.NONE;
        }

        <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
        }
    }

    @GeneratedBy(value=JSHasPropertyNode.class)
    private static final class ForeignObject0Data
    extends Node {
        @Node.Child
        ForeignObject0Data next_;
        @Node.Child
        InteropLibrary interop_;
        @Node.Child
        JSToStringNode toStringNode_;
        @Node.Child
        ForeignObjectPrototypeNode foreignObjectPrototypeNode_;
        @Node.Child
        JSHasPropertyNode hasInPrototype_;

        ForeignObject0Data(ForeignObject0Data next_) {
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

    @GeneratedBy(value=JSHasPropertyNode.class)
    private static final class ObjectStringCachedData
    extends Node {
        @CompilerDirectives.CompilationFinal
        JSClass cachedObjectType_;
        @CompilerDirectives.CompilationFinal
        String cachedName_;
        @Node.Child
        HasPropertyCacheNode hasPropertyNode_;

        ObjectStringCachedData() {
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.NONE;
        }

        <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
        }
    }

    @GeneratedBy(value=JSHasPropertyNode.class)
    private static final class ArrayLongCachedData {
        @CompilerDirectives.CompilationFinal
        ArrayLongCachedData next_;
        @CompilerDirectives.CompilationFinal
        ScriptArray cachedArrayType_;

        ArrayLongCachedData(ArrayLongCachedData next_) {
            this.next_ = next_;
        }
    }
}

