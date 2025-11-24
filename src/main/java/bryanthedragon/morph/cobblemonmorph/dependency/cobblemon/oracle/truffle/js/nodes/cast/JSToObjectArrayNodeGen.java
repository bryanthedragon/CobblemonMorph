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
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.array.JSGetLengthNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectArrayNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSToObjectArrayNode.class)
public final class JSToObjectArrayNodeGen
extends JSToObjectArrayNode
implements Introspection.Provider {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private JSGetLengthNode toArray_getLengthNode_;
    @Node.Child
    private ReadElementNode toArray_readNode_;
    @Node.Child
    private ForeignObject0Data foreignObject0_cache;
    @CompilerDirectives.CompilationFinal
    private BranchProfile foreignObject1_hasPropertiesBranch_;
    @Node.Child
    private ImportValueNode foreignObject1_foreignConvertNode_;

    private JSToObjectArrayNodeGen(JSContext context, boolean nullOrUndefinedAsEmptyArray) {
        super(context, nullOrUndefinedAsEmptyArray);
    }

    @Override
    @ExplodeLoop
    public Object[] executeObjectArray(Object arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof JSObject) {
            JSObject arg0Value_ = (JSObject)arg0Value;
            return this.toArray(arg0Value_, this.toArray_getLengthNode_, this.toArray_readNode_);
        }
        if ((state_0 & 6) != 0) {
            if ((state_0 & 2) != 0 && JSGuards.isUndefined(arg0Value)) {
                return this.doUndefined(arg0Value);
            }
            if ((state_0 & 4) != 0 && JSGuards.isJSNull(arg0Value)) {
                return this.doNull(arg0Value);
            }
        }
        if ((state_0 & 8) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            return this.toArrayString(arg0Value_);
        }
        if ((state_0 & 0x10) != 0 && arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            return this.toArrayInt(arg0Value_);
        }
        if ((state_0 & 0x20) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF000) >>> 12, arg0Value)) {
            double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 0xF000) >>> 12, arg0Value);
            return this.toArrayDouble(arg0Value_);
        }
        if ((state_0 & 0x40) != 0 && arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            return this.toArrayBoolean(arg0Value_);
        }
        if ((state_0 & 0x80) != 0 && arg0Value instanceof Object[]) {
            Object[] arg0Value_ = (Object[])arg0Value;
            return this.passArray(arg0Value_);
        }
        if ((state_0 & 0xF00) != 0) {
            if ((state_0 & 0x100) != 0 && JSGuards.isList(arg0Value)) {
                return this.doList(arg0Value);
            }
            if ((state_0 & 0x200) != 0) {
                ForeignObject0Data s9_ = this.foreignObject0_cache;
                while (s9_ != null) {
                    if (s9_.interop_.accepts(arg0Value) && JSGuards.isForeignObject(arg0Value)) {
                        return this.doForeignObject(arg0Value, s9_.interop_, s9_.hasPropertiesBranch_, s9_.foreignConvertNode_);
                    }
                    s9_ = s9_.next_;
                }
            }
            if ((state_0 & 0x400) != 0 && JSGuards.isForeignObject(arg0Value)) {
                return this.foreignObject1Boundary(state_0, arg0Value);
            }
            if ((state_0 & 0x800) != 0 && JSToObjectArrayNodeGen.fallbackGuard_(state_0, arg0Value)) {
                return this.doFallback(arg0Value);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private Object[] foreignObject1Boundary(int state_0, Object arg0Value) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary foreignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
            Object[] objectArray = this.doForeignObject(arg0Value, foreignObject1_interop__, this.foreignObject1_hasPropertiesBranch_, this.foreignObject1_foreignConvertNode_);
            return objectArray;
        }
        finally {
            encapsulating_.set(prev_);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object[] executeAndSpecialize(Object arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arg0Value instanceof JSObject) {
                JSObject arg0Value_ = (JSObject)arg0Value;
                this.toArray_getLengthNode_ = super.insert(JSGetLengthNode.create(this.context));
                this.toArray_readNode_ = super.insert(ReadElementNode.create(this.context));
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object[] objectArray = this.toArray(arg0Value_, this.toArray_getLengthNode_, this.toArray_readNode_);
                return objectArray;
            }
            if (JSGuards.isUndefined(arg0Value)) {
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                Object[] arg0Value_ = this.doUndefined(arg0Value);
                return arg0Value_;
            }
            if (JSGuards.isJSNull(arg0Value)) {
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                Object[] arg0Value_ = this.doNull(arg0Value);
                return arg0Value_;
            }
            if (arg0Value instanceof TruffleString) {
                TruffleString arg0Value_ = (TruffleString)arg0Value;
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                Object[] objectArray = this.toArrayString(arg0Value_);
                return objectArray;
            }
            if (arg0Value instanceof Integer) {
                int arg0Value_ = (Integer)arg0Value;
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                Object[] objectArray = this.toArrayInt(arg0Value_);
                return objectArray;
            }
            int doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value);
            if (doubleCast0 != 0) {
                double arg0Value_2 = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
                state_0 |= doubleCast0 << 12;
                this.state_0_ = state_0 |= 0x20;
                lock.unlock();
                hasLock = false;
                Object[] objectArray = this.toArrayDouble(arg0Value_2);
                return objectArray;
            }
            if (arg0Value instanceof Boolean) {
                boolean arg0Value_ = (Boolean)arg0Value;
                this.state_0_ = state_0 |= 0x40;
                lock.unlock();
                hasLock = false;
                Object[] arg0Value_2 = this.toArrayBoolean(arg0Value_);
                return arg0Value_2;
            }
            if (arg0Value instanceof Object[]) {
                Object[] arg0Value_ = (Object[])arg0Value;
                this.state_0_ = state_0 |= 0x80;
                lock.unlock();
                hasLock = false;
                Object[] arg0Value_2 = this.passArray(arg0Value_);
                return arg0Value_2;
            }
            if (JSGuards.isList(arg0Value)) {
                this.state_0_ = state_0 |= 0x100;
                lock.unlock();
                hasLock = false;
                Object[] arg0Value_ = this.doList(arg0Value);
                return arg0Value_;
            }
            if (exclude == 0) {
                int count9_ = 0;
                ForeignObject0Data s9_ = this.foreignObject0_cache;
                if ((state_0 & 0x200) != 0) {
                    while (!(s9_ == null || s9_.interop_.accepts(arg0Value) && JSGuards.isForeignObject(arg0Value))) {
                        s9_ = s9_.next_;
                        ++count9_;
                    }
                }
                if (s9_ == null && JSGuards.isForeignObject(arg0Value) && count9_ < 5) {
                    s9_ = super.insert(new ForeignObject0Data(this.foreignObject0_cache));
                    s9_.interop_ = s9_.insertAccessor(INTEROP_LIBRARY_.create(arg0Value));
                    s9_.hasPropertiesBranch_ = BranchProfile.create();
                    s9_.foreignConvertNode_ = s9_.insertAccessor(ImportValueNode.create());
                    VarHandle.storeStoreFence();
                    this.foreignObject0_cache = s9_;
                    this.state_0_ = state_0 |= 0x200;
                }
                if (s9_ != null) {
                    lock.unlock();
                    hasLock = false;
                    Object[] objectArray = this.doForeignObject(arg0Value, s9_.interop_, s9_.hasPropertiesBranch_, s9_.foreignConvertNode_);
                    return objectArray;
                }
            }
            InteropLibrary foreignObject1_interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                if (JSGuards.isForeignObject(arg0Value)) {
                    foreignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
                    this.foreignObject1_hasPropertiesBranch_ = BranchProfile.create();
                    this.foreignObject1_foreignConvertNode_ = super.insert(ImportValueNode.create());
                    this.exclude_ = exclude |= 1;
                    this.foreignObject0_cache = null;
                    state_0 &= 0xFFFFFDFF;
                    this.state_0_ = state_0 |= 0x400;
                    lock.unlock();
                    hasLock = false;
                    Object[] objectArray = this.doForeignObject(arg0Value, foreignObject1_interop__, this.foreignObject1_hasPropertiesBranch_, this.foreignObject1_foreignConvertNode_);
                    return objectArray;
                }
            }
            finally {
                encapsulating_.set(prev_);
            }
            this.state_0_ = state_0 |= 0x800;
            lock.unlock();
            hasLock = false;
            Object[] objectArray = this.doFallback(arg0Value);
            return objectArray;
        }
        finally {
            if (hasLock) {
                lock.unlock();
            }
        }
    }

    @Override
    public NodeCost getCost() {
        ForeignObject0Data s9_;
        int state_0 = this.state_0_;
        if ((state_0 & 0xFFF) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0xFFF & (state_0 & 0xFFF) - 1) == 0 && ((s9_ = this.foreignObject0_cache) == null || s9_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Cloneable>> cached;
        Object[] data = new Object[13];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "toArray";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Cloneable>>();
            cached.add(Arrays.asList(this.toArray_getLengthNode_, this.toArray_readNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doUndefined";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doNull";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "toArrayString";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "toArrayInt";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "toArrayDouble";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        s = new Object[3];
        s[0] = "toArrayBoolean";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[7] = s;
        s = new Object[3];
        s[0] = "passArray";
        s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[8] = s;
        s = new Object[3];
        s[0] = "doList";
        s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[9] = s;
        s = new Object[3];
        s[0] = "doForeignObject";
        if ((state_0 & 0x200) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            ForeignObject0Data s9_ = this.foreignObject0_cache;
            while (s9_ != null) {
                cached.add(Arrays.asList(s9_.interop_, s9_.hasPropertiesBranch_, s9_.foreignConvertNode_));
                s9_ = s9_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[10] = s;
        s = new Object[3];
        s[0] = "doForeignObject";
        if ((state_0 & 0x400) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.foreignObject1_hasPropertiesBranch_, this.foreignObject1_foreignConvertNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[11] = s;
        s = new Object[3];
        s[0] = "doFallback";
        s[1] = (state_0 & 0x800) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[12] = s;
        return Introspection.Provider.create(data);
    }

    private static boolean fallbackGuard_(int state_0, Object arg0Value) {
        if ((state_0 & 1) == 0 && arg0Value instanceof JSObject) {
            return false;
        }
        if ((state_0 & 2) == 0 && JSGuards.isUndefined(arg0Value)) {
            return false;
        }
        if ((state_0 & 4) == 0 && JSGuards.isJSNull(arg0Value)) {
            return false;
        }
        if ((state_0 & 8) == 0 && arg0Value instanceof TruffleString) {
            return false;
        }
        if (JSTypesGen.isImplicitDouble(arg0Value)) {
            return false;
        }
        if ((state_0 & 0x40) == 0 && arg0Value instanceof Boolean) {
            return false;
        }
        if ((state_0 & 0x80) == 0 && arg0Value instanceof Object[]) {
            return false;
        }
        if ((state_0 & 0x100) == 0 && JSGuards.isList(arg0Value)) {
            return false;
        }
        return (state_0 & 0x400) != 0 || !JSGuards.isForeignObject(arg0Value);
    }

    public static JSToObjectArrayNode create(JSContext context, boolean nullOrUndefinedAsEmptyArray) {
        return new JSToObjectArrayNodeGen(context, nullOrUndefinedAsEmptyArray);
    }

    @GeneratedBy(value=JSToObjectArrayNode.class)
    private static final class ForeignObject0Data
    extends Node {
        @Node.Child
        ForeignObject0Data next_;
        @Node.Child
        InteropLibrary interop_;
        @CompilerDirectives.CompilationFinal
        BranchProfile hasPropertiesBranch_;
        @Node.Child
        ImportValueNode foreignConvertNode_;

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
}

