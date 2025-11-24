/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.cast;

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
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.cast.JSToPropertyKeyNode;
import com.oracle.truffle.js.nodes.cast.ToArrayIndexNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Symbol;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ToArrayIndexNode.class)
public final class ToArrayIndexNodeGen
extends ToArrayIndexNode
implements Introspection.Provider {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private ConvertFromStringData convertFromString_cache;
    @Node.Child
    private InteropArrayIndex0Data interopArrayIndex0_cache;
    @Node.Child
    private NonArrayIndex0Data nonArrayIndex0_cache;
    @Node.Child
    private JSToPropertyKeyNode nonArrayIndex1_toPropertyKey_;
    @Node.Child
    private ToArrayIndexNode nonArrayIndex1_recursive_;

    private ToArrayIndexNodeGen(boolean convertToPropertyKey, boolean convertStringToIndex) {
        super(convertToPropertyKey, convertStringToIndex);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @ExplodeLoop
    public Object execute(Object arg0Value) {
        BigInt arg0Value_;
        int arg0Value_2;
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof Integer && JSGuards.isIntArrayIndex(arg0Value_2 = ((Integer)arg0Value).intValue())) {
            return ToArrayIndexNode.doInteger(arg0Value_2);
        }
        if ((state_0 & 2) != 0 && arg0Value instanceof Long && JSGuards.isLongArrayIndex(arg0Value_ = ((Long)arg0Value).longValue())) {
            return ToArrayIndexNode.doLong(arg0Value_);
        }
        if ((state_0 & 0xC) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF000) >>> 12, arg0Value)) {
            double arg0Value_3 = JSTypesGen.asImplicitDouble((state_0 & 0xF000) >>> 12, arg0Value);
            if ((state_0 & 4) != 0 && ToArrayIndexNode.doubleIsIntIndex(arg0Value_3)) {
                return ToArrayIndexNode.doDoubleAsIntIndex(arg0Value_3);
            }
            if ((state_0 & 8) != 0 && ToArrayIndexNode.doubleIsUintIndex(arg0Value_3)) {
                return ToArrayIndexNode.doDoubleAsUintIndex(arg0Value_3);
            }
        }
        if ((state_0 & 0x10) != 0 && arg0Value instanceof Symbol) {
            Symbol arg0Value_4 = (Symbol)arg0Value;
            return ToArrayIndexNode.doSymbol(arg0Value_4);
        }
        if ((state_0 & 0x20) != 0 && arg0Value instanceof BigInt && JSGuards.isBigIntArrayIndex(arg0Value_ = (BigInt)arg0Value)) {
            return ToArrayIndexNode.doBigInt(arg0Value_);
        }
        if ((state_0 & 0xC0) != 0 && arg0Value instanceof TruffleString) {
            ConvertFromStringData s6_;
            TruffleString arg0Value_5 = (TruffleString)arg0Value;
            if ((state_0 & 0x40) != 0 && (s6_ = this.convertFromString_cache) != null) {
                assert (this.convertStringToIndex);
                if (JSRuntime.arrayIndexLengthInRange(arg0Value_5)) {
                    return ToArrayIndexNode.convertFromString(arg0Value_5, s6_.startsWithDigitBranch_, s6_.isArrayIndexBranch_, s6_.stringReadNode_);
                }
            }
            if (!((state_0 & 0x80) == 0 || this.convertStringToIndex && JSRuntime.arrayIndexLengthInRange(arg0Value_5))) {
                return ToArrayIndexNode.convertFromStringNotInRange(arg0Value_5);
            }
        }
        if ((state_0 & 0xF00) != 0) {
            Node prev_;
            if ((state_0 & 0x100) != 0) {
                InteropArrayIndex0Data s8_ = this.interopArrayIndex0_cache;
                while (s8_ != null) {
                    long index__;
                    if (s8_.interop_.accepts(arg0Value) && ToArrayIndexNode.notArrayIndex(arg0Value) && (index__ = ToArrayIndexNode.toArrayIndex(arg0Value, s8_.interop_)) >= 0L) {
                        return ToArrayIndexNode.doInteropArrayIndex(arg0Value, s8_.interop_, index__);
                    }
                    s8_ = s8_.next_;
                }
            }
            if ((state_0 & 0x200) != 0) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary interopArrayIndex1_interop__;
                    long interopArrayIndex1_index__;
                    if (ToArrayIndexNode.notArrayIndex(arg0Value) && (interopArrayIndex1_index__ = ToArrayIndexNode.toArrayIndex(arg0Value, interopArrayIndex1_interop__ = INTEROP_LIBRARY_.getUncached())) >= 0L) {
                        Object object = this.interopArrayIndex1Boundary(state_0, arg0Value);
                        return object;
                    }
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }
            if ((state_0 & 0x400) != 0) {
                NonArrayIndex0Data s10_ = this.nonArrayIndex0_cache;
                while (s10_ != null) {
                    if (s10_.interop_.accepts(arg0Value) && ToArrayIndexNode.notArrayIndex(arg0Value) && ToArrayIndexNode.toArrayIndex(arg0Value, s10_.interop_) < 0L) {
                        return this.doNonArrayIndex(arg0Value, s10_.interop_, s10_.toPropertyKey_, s10_.recursive_);
                    }
                    s10_ = s10_.next_;
                }
            }
            if ((state_0 & 0x800) != 0) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary nonArrayIndex1_interop__;
                    if (ToArrayIndexNode.notArrayIndex(arg0Value) && ToArrayIndexNode.toArrayIndex(arg0Value, nonArrayIndex1_interop__ = INTEROP_LIBRARY_.getUncached()) < 0L) {
                        Object object = this.nonArrayIndex1Boundary(state_0, arg0Value);
                        return object;
                    }
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    @CompilerDirectives.TruffleBoundary
    private Object interopArrayIndex1Boundary(int state_0, Object arg0Value) {
        InteropLibrary interopArrayIndex1_interop__ = INTEROP_LIBRARY_.getUncached();
        long interopArrayIndex1_index__ = ToArrayIndexNode.toArrayIndex(arg0Value, interopArrayIndex1_interop__);
        return ToArrayIndexNode.doInteropArrayIndex(arg0Value, interopArrayIndex1_interop__, interopArrayIndex1_index__);
    }

    @CompilerDirectives.TruffleBoundary
    private Object nonArrayIndex1Boundary(int state_0, Object arg0Value) {
        InteropLibrary nonArrayIndex1_interop__ = INTEROP_LIBRARY_.getUncached();
        return this.doNonArrayIndex(arg0Value, nonArrayIndex1_interop__, this.nonArrayIndex1_toPropertyKey_, this.nonArrayIndex1_recursive_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @ExplodeLoop
    public long executeLong(Object arg0Value) throws UnexpectedResultException {
        BigInt arg0Value_;
        int arg0Value_2;
        int state_0 = this.state_0_;
        if ((state_0 & 0xC40) != 0) {
            return JSTypesGen.expectLong(this.execute(arg0Value));
        }
        if ((state_0 & 1) != 0 && arg0Value instanceof Integer && JSGuards.isIntArrayIndex(arg0Value_2 = ((Integer)arg0Value).intValue())) {
            return ToArrayIndexNode.doInteger(arg0Value_2);
        }
        if ((state_0 & 2) != 0 && arg0Value instanceof Long && JSGuards.isLongArrayIndex(arg0Value_ = ((Long)arg0Value).longValue())) {
            return ToArrayIndexNode.doLong(arg0Value_);
        }
        if ((state_0 & 0xC) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF000) >>> 12, arg0Value)) {
            double arg0Value_3 = JSTypesGen.asImplicitDouble((state_0 & 0xF000) >>> 12, arg0Value);
            if ((state_0 & 4) != 0 && ToArrayIndexNode.doubleIsIntIndex(arg0Value_3)) {
                return ToArrayIndexNode.doDoubleAsIntIndex(arg0Value_3);
            }
            if ((state_0 & 8) != 0 && ToArrayIndexNode.doubleIsUintIndex(arg0Value_3)) {
                return ToArrayIndexNode.doDoubleAsUintIndex(arg0Value_3);
            }
        }
        if ((state_0 & 0x20) != 0 && arg0Value instanceof BigInt && JSGuards.isBigIntArrayIndex(arg0Value_ = (BigInt)arg0Value)) {
            return ToArrayIndexNode.doBigInt(arg0Value_);
        }
        if ((state_0 & 0x300) != 0) {
            if ((state_0 & 0x100) != 0) {
                InteropArrayIndex0Data s8_ = this.interopArrayIndex0_cache;
                while (s8_ != null) {
                    long index__;
                    if (s8_.interop_.accepts(arg0Value) && ToArrayIndexNode.notArrayIndex(arg0Value) && (index__ = ToArrayIndexNode.toArrayIndex(arg0Value, s8_.interop_)) >= 0L) {
                        return ToArrayIndexNode.doInteropArrayIndex(arg0Value, s8_.interop_, index__);
                    }
                    s8_ = s8_.next_;
                }
            }
            if ((state_0 & 0x200) != 0) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary interopArrayIndex1_interop__;
                    long interopArrayIndex1_index__;
                    if (ToArrayIndexNode.notArrayIndex(arg0Value) && (interopArrayIndex1_index__ = ToArrayIndexNode.toArrayIndex(arg0Value, interopArrayIndex1_interop__ = INTEROP_LIBRARY_.getUncached())) >= 0L) {
                        long l = this.interopArrayIndex1Boundary0(state_0, arg0Value);
                        return l;
                    }
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectLong(this.executeAndSpecialize(arg0Value));
    }

    @CompilerDirectives.TruffleBoundary
    private long interopArrayIndex1Boundary0(int state_0, Object arg0Value) throws UnexpectedResultException {
        InteropLibrary interopArrayIndex1_interop__ = INTEROP_LIBRARY_.getUncached();
        long interopArrayIndex1_index__ = ToArrayIndexNode.toArrayIndex(arg0Value, interopArrayIndex1_interop__);
        return ToArrayIndexNode.doInteropArrayIndex(arg0Value, interopArrayIndex1_interop__, interopArrayIndex1_index__);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object executeAndSpecialize(Object arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            BigInt arg0Value_;
            int arg0Value_2;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arg0Value instanceof Integer && JSGuards.isIntArrayIndex(arg0Value_2 = ((Integer)arg0Value).intValue())) {
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Long l = ToArrayIndexNode.doInteger(arg0Value_2);
                return l;
            }
            if (arg0Value instanceof Long && JSGuards.isLongArrayIndex(arg0Value_ = ((Long)arg0Value).longValue())) {
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                Long l = ToArrayIndexNode.doLong(arg0Value_);
                return l;
            }
            int doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value);
            if (doubleCast0 != 0) {
                double arg0Value_3 = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
                if ((exclude & 1) == 0 && ToArrayIndexNode.doubleIsIntIndex(arg0Value_3)) {
                    state_0 |= doubleCast0 << 12;
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    Long l = ToArrayIndexNode.doDoubleAsIntIndex(arg0Value_3);
                    return l;
                }
                if (ToArrayIndexNode.doubleIsUintIndex(arg0Value_3)) {
                    this.exclude_ = exclude |= 1;
                    state_0 &= 0xFFFFFFFB;
                    state_0 |= doubleCast0 << 12;
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    Long l = ToArrayIndexNode.doDoubleAsUintIndex(arg0Value_3);
                    return l;
                }
            }
            if (arg0Value instanceof Symbol) {
                Symbol arg0Value_4 = (Symbol)arg0Value;
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                Symbol arg0Value_3 = ToArrayIndexNode.doSymbol(arg0Value_4);
                return arg0Value_3;
            }
            if (arg0Value instanceof BigInt && JSGuards.isBigIntArrayIndex(arg0Value_ = (BigInt)arg0Value)) {
                this.state_0_ = state_0 |= 0x20;
                lock.unlock();
                hasLock = false;
                Long arg0Value_3 = ToArrayIndexNode.doBigInt(arg0Value_);
                return arg0Value_3;
            }
            if (arg0Value instanceof TruffleString) {
                TruffleString arg0Value_5 = (TruffleString)arg0Value;
                if (this.convertStringToIndex && JSRuntime.arrayIndexLengthInRange(arg0Value_5)) {
                    ConvertFromStringData s6_ = super.insert(new ConvertFromStringData());
                    s6_.startsWithDigitBranch_ = ConditionProfile.create();
                    s6_.isArrayIndexBranch_ = BranchProfile.create();
                    s6_.stringReadNode_ = s6_.insertAccessor(TruffleString.ReadCharUTF16Node.create());
                    VarHandle.storeStoreFence();
                    this.convertFromString_cache = s6_;
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    Object object = ToArrayIndexNode.convertFromString(arg0Value_5, s6_.startsWithDigitBranch_, s6_.isArrayIndexBranch_, s6_.stringReadNode_);
                    return object;
                }
                if (!this.convertStringToIndex || !JSRuntime.arrayIndexLengthInRange(arg0Value_5)) {
                    this.state_0_ = state_0 |= 0x80;
                    lock.unlock();
                    hasLock = false;
                    TruffleString s6_ = ToArrayIndexNode.convertFromStringNotInRange(arg0Value_5);
                    return s6_;
                }
            }
            long index__ = 0L;
            if ((exclude & 2) == 0) {
                Object interop__;
                int count8_ = 0;
                InteropArrayIndex0Data s8_ = this.interopArrayIndex0_cache;
                if ((state_0 & 0x100) != 0) {
                    while (!(s8_ == null || s8_.interop_.accepts(arg0Value) && ToArrayIndexNode.notArrayIndex(arg0Value) && (index__ = ToArrayIndexNode.toArrayIndex(arg0Value, s8_.interop_)) >= 0L)) {
                        s8_ = s8_.next_;
                        ++count8_;
                    }
                }
                if (s8_ == null && ToArrayIndexNode.notArrayIndex(arg0Value) && (index__ = ToArrayIndexNode.toArrayIndex(arg0Value, (InteropLibrary)(interop__ = super.insert(INTEROP_LIBRARY_.create(arg0Value))))) >= 0L && count8_ < 5) {
                    s8_ = super.insert(new InteropArrayIndex0Data(this.interopArrayIndex0_cache));
                    s8_.interop_ = (InteropLibrary)s8_.insertAccessor(interop__);
                    VarHandle.storeStoreFence();
                    this.interopArrayIndex0_cache = s8_;
                    this.state_0_ = state_0 |= 0x100;
                }
                if (s8_ != null) {
                    lock.unlock();
                    hasLock = false;
                    interop__ = ToArrayIndexNode.doInteropArrayIndex(arg0Value, s8_.interop_, index__);
                    return interop__;
                }
            }
            long interopArrayIndex1_index__ = 0L;
            InteropLibrary interopArrayIndex1_interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                if (ToArrayIndexNode.notArrayIndex(arg0Value) && (interopArrayIndex1_index__ = ToArrayIndexNode.toArrayIndex(arg0Value, interopArrayIndex1_interop__ = INTEROP_LIBRARY_.getUncached())) >= 0L) {
                    this.exclude_ = exclude |= 2;
                    this.interopArrayIndex0_cache = null;
                    state_0 &= 0xFFFFFEFF;
                    this.state_0_ = state_0 |= 0x200;
                    lock.unlock();
                    hasLock = false;
                    Long l = ToArrayIndexNode.doInteropArrayIndex(arg0Value, interopArrayIndex1_interop__, interopArrayIndex1_index__);
                    return l;
                }
            }
            finally {
                encapsulating_.set(prev_);
            }
            if ((exclude & 4) == 0) {
                Object interop__1;
                int count10_ = 0;
                NonArrayIndex0Data s10_ = this.nonArrayIndex0_cache;
                if ((state_0 & 0x400) != 0) {
                    while (!(s10_ == null || s10_.interop_.accepts(arg0Value) && ToArrayIndexNode.notArrayIndex(arg0Value) && ToArrayIndexNode.toArrayIndex(arg0Value, s10_.interop_) < 0L)) {
                        s10_ = s10_.next_;
                        ++count10_;
                    }
                }
                if (s10_ == null && ToArrayIndexNode.notArrayIndex(arg0Value) && ToArrayIndexNode.toArrayIndex(arg0Value, (InteropLibrary)(interop__1 = super.insert(INTEROP_LIBRARY_.create(arg0Value)))) < 0L && count10_ < 5) {
                    s10_ = super.insert(new NonArrayIndex0Data(this.nonArrayIndex0_cache));
                    s10_.interop_ = (InteropLibrary)s10_.insertAccessor(interop__1);
                    s10_.toPropertyKey_ = s10_.insertAccessor(JSToPropertyKeyNode.create());
                    s10_.recursive_ = s10_.insertAccessor(ToArrayIndexNode.createNoToPropertyKey());
                    VarHandle.storeStoreFence();
                    this.nonArrayIndex0_cache = s10_;
                    this.state_0_ = state_0 |= 0x400;
                }
                if (s10_ != null) {
                    lock.unlock();
                    hasLock = false;
                    interop__1 = this.doNonArrayIndex(arg0Value, s10_.interop_, s10_.toPropertyKey_, s10_.recursive_);
                    return interop__1;
                }
            }
            InteropLibrary nonArrayIndex1_interop__ = null;
            EncapsulatingNodeReference encapsulating_2 = EncapsulatingNodeReference.getCurrent();
            Node prev_2 = encapsulating_2.set(this);
            try {
                if (ToArrayIndexNode.notArrayIndex(arg0Value) && ToArrayIndexNode.toArrayIndex(arg0Value, nonArrayIndex1_interop__ = INTEROP_LIBRARY_.getUncached()) < 0L) {
                    this.nonArrayIndex1_toPropertyKey_ = super.insert(JSToPropertyKeyNode.create());
                    this.nonArrayIndex1_recursive_ = super.insert(ToArrayIndexNode.createNoToPropertyKey());
                    this.exclude_ = exclude |= 4;
                    this.nonArrayIndex0_cache = null;
                    state_0 &= 0xFFFFFBFF;
                    this.state_0_ = state_0 |= 0x800;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doNonArrayIndex(arg0Value, nonArrayIndex1_interop__, this.nonArrayIndex1_toPropertyKey_, this.nonArrayIndex1_recursive_);
                    return object;
                }
            }
            finally {
                encapsulating_2.set(prev_2);
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
        if ((state_0 & 0xFFF) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0xFFF & (state_0 & 0xFFF) - 1) == 0) {
            InteropArrayIndex0Data s8_ = this.interopArrayIndex0_cache;
            NonArrayIndex0Data s10_ = this.nonArrayIndex0_cache;
            if (!(s8_ != null && s8_.next_ != null || s10_ != null && s10_.next_ != null)) {
                return NodeCost.MONOMORPHIC;
            }
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Object>> cached;
        Object[] data = new Object[13];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doInteger";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doLong";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doDoubleAsIntIndex";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : ((exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[3] = s;
        s = new Object[3];
        s[0] = "doDoubleAsUintIndex";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doSymbol";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        s = new Object[3];
        s[0] = "convertFromString";
        if ((state_0 & 0x40) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            ConvertFromStringData s6_ = this.convertFromString_cache;
            if (s6_ != null) {
                cached.add(Arrays.asList(s6_.startsWithDigitBranch_, s6_.isArrayIndexBranch_, s6_.stringReadNode_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[7] = s;
        s = new Object[3];
        s[0] = "convertFromStringNotInRange";
        s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[8] = s;
        s = new Object[3];
        s[0] = "doInteropArrayIndex";
        if ((state_0 & 0x100) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            InteropArrayIndex0Data s8_ = this.interopArrayIndex0_cache;
            while (s8_ != null) {
                cached.add(Arrays.asList(s8_.interop_));
                s8_ = s8_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = (exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[9] = s;
        s = new Object[3];
        s[0] = "doInteropArrayIndex";
        if ((state_0 & 0x200) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(new Object[0]));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[10] = s;
        s = new Object[3];
        s[0] = "doNonArrayIndex";
        if ((state_0 & 0x400) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            NonArrayIndex0Data s10_ = this.nonArrayIndex0_cache;
            while (s10_ != null) {
                cached.add(Arrays.asList(s10_.interop_, s10_.toPropertyKey_, s10_.recursive_));
                s10_ = s10_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = (exclude & 4) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[11] = s;
        s = new Object[3];
        s[0] = "doNonArrayIndex";
        if ((state_0 & 0x800) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.nonArrayIndex1_toPropertyKey_, this.nonArrayIndex1_recursive_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[12] = s;
        return Introspection.Provider.create(data);
    }

    public static ToArrayIndexNode create(boolean convertToPropertyKey, boolean convertStringToIndex) {
        return new ToArrayIndexNodeGen(convertToPropertyKey, convertStringToIndex);
    }

    @GeneratedBy(value=ToArrayIndexNode.class)
    private static final class NonArrayIndex0Data
    extends Node {
        @Node.Child
        NonArrayIndex0Data next_;
        @Node.Child
        InteropLibrary interop_;
        @Node.Child
        JSToPropertyKeyNode toPropertyKey_;
        @Node.Child
        ToArrayIndexNode recursive_;

        NonArrayIndex0Data(NonArrayIndex0Data next_) {
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

    @GeneratedBy(value=ToArrayIndexNode.class)
    private static final class InteropArrayIndex0Data
    extends Node {
        @Node.Child
        InteropArrayIndex0Data next_;
        @Node.Child
        InteropLibrary interop_;

        InteropArrayIndex0Data(InteropArrayIndex0Data next_) {
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

    @GeneratedBy(value=ToArrayIndexNode.class)
    private static final class ConvertFromStringData
    extends Node {
        @CompilerDirectives.CompilationFinal
        ConditionProfile startsWithDigitBranch_;
        @CompilerDirectives.CompilationFinal
        BranchProfile isArrayIndexBranch_;
        @Node.Child
        TruffleString.ReadCharUTF16Node stringReadNode_;

        ConvertFromStringData() {
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

