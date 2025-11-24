/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.array;

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
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.array.ArrayLengthNode;
import com.oracle.truffle.js.nodes.array.JSGetLengthNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSArrayObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSGetLengthNode.class)
public final class JSGetLengthNodeGen
extends JSGetLengthNode
implements Introspection.Provider {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private ArrayLengthNode.ArrayLengthReadNode getArrayLengthInt_arrayLengthReadNode_;
    @Node.Child
    private ArrayLengthNode.ArrayLengthReadNode getArrayLength_arrayLengthReadNode_;
    @Node.Child
    private PropertyGetNode getNonArrayLength_getLengthPropertyNode_;
    @Node.Child
    private GetLengthForeign0Data getLengthForeign0_cache;
    @Node.Child
    private ImportValueNode getLengthForeign1_importValueNode_;

    private JSGetLengthNodeGen(JSContext context) {
        super(context);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @ExplodeLoop
    public Object execute(Object arg0Value) {
        JSDynamicObject arg0Value_;
        int state_0 = this.state_0_;
        if ((state_0 & 3) != 0 && arg0Value instanceof JSArrayObject) {
            arg0Value_ = (JSArrayObject)arg0Value;
            if ((state_0 & 1) != 0) {
                try {
                    return this.getArrayLengthInt((JSArrayObject)arg0Value_, this.getArrayLengthInt_arrayLengthReadNode_);
                }
                catch (UnexpectedResultException ex) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    Lock lock = this.getLock();
                    lock.lock();
                    try {
                        this.exclude_ |= 1;
                        this.state_0_ &= 0xFFFFFFFE;
                    }
                    finally {
                        lock.unlock();
                    }
                    return ex.getResult();
                }
            }
            if ((state_0 & 2) != 0) {
                return this.getArrayLength((JSArrayObject)arg0Value_, this.getArrayLength_arrayLengthReadNode_);
            }
        }
        if ((state_0 & 4) != 0 && arg0Value instanceof JSDynamicObject && !JSGuards.isJSArray(arg0Value_ = (JSDynamicObject)arg0Value)) {
            return this.getNonArrayLength(arg0Value_, this.getNonArrayLength_getLengthPropertyNode_);
        }
        if ((state_0 & 0x18) != 0) {
            if ((state_0 & 8) != 0) {
                GetLengthForeign0Data s3_ = this.getLengthForeign0_cache;
                while (s3_ != null) {
                    if (s3_.interop_.accepts(arg0Value) && !JSGuards.isJSDynamicObject(arg0Value)) {
                        return this.getLengthForeign(arg0Value, s3_.interop_, s3_.importValueNode_);
                    }
                    s3_ = s3_.next_;
                }
            }
            if ((state_0 & 0x10) != 0 && !JSGuards.isJSDynamicObject(arg0Value)) {
                return this.getLengthForeign1Boundary(state_0, arg0Value);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private Object getLengthForeign1Boundary(int state_0, Object arg0Value) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary getLengthForeign1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
            Double d = this.getLengthForeign(arg0Value, getLengthForeign1_interop__, this.getLengthForeign1_importValueNode_);
            return d;
        }
        finally {
            encapsulating_.set(prev_);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private Object executeAndSpecialize(Object arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            JSDynamicObject arg0Value_;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arg0Value instanceof JSArrayObject) {
                JSArrayObject arg0Value_2 = (JSArrayObject)arg0Value;
                if ((exclude & 1) == 0) {
                    this.getArrayLengthInt_arrayLengthReadNode_ = super.insert(ArrayLengthNode.ArrayLengthReadNode.create());
                    this.state_0_ = state_0 |= 1;
                    try {
                        lock.unlock();
                        hasLock = false;
                        Integer n = this.getArrayLengthInt(arg0Value_2, this.getArrayLengthInt_arrayLengthReadNode_);
                        return n;
                    }
                    catch (UnexpectedResultException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        lock.lock();
                        try {
                            this.exclude_ |= 1;
                            this.state_0_ &= 0xFFFFFFFE;
                        }
                        finally {
                            lock.unlock();
                        }
                        Object object = ex.getResult();
                        if (hasLock) {
                            lock.unlock();
                        }
                        return object;
                    }
                }
                this.getArrayLength_arrayLengthReadNode_ = super.insert(ArrayLengthNode.ArrayLengthReadNode.create());
                this.exclude_ = exclude |= 1;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                Double ex = this.getArrayLength(arg0Value_2, this.getArrayLength_arrayLengthReadNode_);
                return ex;
            }
            if (arg0Value instanceof JSDynamicObject && !JSGuards.isJSArray(arg0Value_ = (JSDynamicObject)arg0Value)) {
                this.getNonArrayLength_getLengthPropertyNode_ = super.insert(this.createLengthProperty());
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                Double ex = this.getNonArrayLength(arg0Value_, this.getNonArrayLength_getLengthPropertyNode_);
                return ex;
            }
            if ((exclude & 2) == 0) {
                int count3_ = 0;
                GetLengthForeign0Data s3_ = this.getLengthForeign0_cache;
                if ((state_0 & 8) != 0) {
                    while (s3_ != null && (!s3_.interop_.accepts(arg0Value) || JSGuards.isJSDynamicObject(arg0Value))) {
                        s3_ = s3_.next_;
                        ++count3_;
                    }
                }
                if (s3_ == null && !JSGuards.isJSDynamicObject(arg0Value) && count3_ < 3) {
                    s3_ = super.insert(new GetLengthForeign0Data(this.getLengthForeign0_cache));
                    s3_.interop_ = s3_.insertAccessor(INTEROP_LIBRARY_.create(arg0Value));
                    s3_.importValueNode_ = s3_.insertAccessor(ImportValueNode.create());
                    VarHandle.storeStoreFence();
                    this.getLengthForeign0_cache = s3_;
                    this.state_0_ = state_0 |= 8;
                }
                if (s3_ != null) {
                    lock.unlock();
                    hasLock = false;
                    Double d = this.getLengthForeign(arg0Value, s3_.interop_, s3_.importValueNode_);
                    return d;
                }
            }
            InteropLibrary getLengthForeign1_interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                if (!JSGuards.isJSDynamicObject(arg0Value)) {
                    getLengthForeign1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
                    this.getLengthForeign1_importValueNode_ = super.insert(ImportValueNode.create());
                    this.exclude_ = exclude |= 2;
                    this.getLengthForeign0_cache = null;
                    state_0 &= 0xFFFFFFF7;
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    Double d = this.getLengthForeign(arg0Value, getLengthForeign1_interop__, this.getLengthForeign1_importValueNode_);
                    return d;
                }
            }
            finally {
                encapsulating_.set(prev_);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            {
                catch (Throwable throwable) {
                    throw throwable;
                }
            }
        }
        finally {
            if (hasLock) {
                lock.unlock();
            }
        }
    }

    @Override
    public NodeCost getCost() {
        GetLengthForeign0Data s3_;
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s3_ = this.getLengthForeign0_cache) == null || s3_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Node>> cached;
        Object[] data = new Object[6];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "getArrayLengthInt";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Node>>();
            cached.add(Arrays.asList(this.getArrayLengthInt_arrayLengthReadNode_));
            s[2] = cached;
        } else {
            s[1] = (exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "getArrayLength";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.getArrayLength_arrayLengthReadNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        s = new Object[3];
        s[0] = "getNonArrayLength";
        if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.getNonArrayLength_getLengthPropertyNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[3] = s;
        s = new Object[3];
        s[0] = "getLengthForeign";
        if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            GetLengthForeign0Data s3_ = this.getLengthForeign0_cache;
            while (s3_ != null) {
                cached.add(Arrays.asList(s3_.interop_, s3_.importValueNode_));
                s3_ = s3_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = (exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[4] = s;
        s = new Object[3];
        s[0] = "getLengthForeign";
        if ((state_0 & 0x10) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.getLengthForeign1_importValueNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[5] = s;
        return Introspection.Provider.create(data);
    }

    public static JSGetLengthNode create(JSContext context) {
        return new JSGetLengthNodeGen(context);
    }

    @GeneratedBy(value=JSGetLengthNode.class)
    private static final class GetLengthForeign0Data
    extends Node {
        @Node.Child
        GetLengthForeign0Data next_;
        @Node.Child
        InteropLibrary interop_;
        @Node.Child
        ImportValueNode importValueNode_;

        GetLengthForeign0Data(GetLengthForeign0Data next_) {
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

