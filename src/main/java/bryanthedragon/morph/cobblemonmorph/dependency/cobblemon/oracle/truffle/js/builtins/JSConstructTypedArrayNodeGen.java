/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.builtins.JSConstructTypedArrayNode;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.GetMethodNode;
import com.oracle.truffle.js.nodes.access.IsJSObjectNode;
import com.oracle.truffle.js.nodes.access.IterableToListNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.nodes.array.JSGetLengthNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.array.TypedArray;
import com.oracle.truffle.js.runtime.array.TypedArrayFactory;
import com.oracle.truffle.js.runtime.builtins.JSAbstractBuffer;
import com.oracle.truffle.js.runtime.builtins.JSArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSConstructTypedArrayNode.class)
public final class JSConstructTypedArrayNodeGen
extends JSConstructTypedArrayNode
implements Introspection.Provider {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @Node.Child
    private JavaScriptNode arguments0_;
    @Node.Child
    private JavaScriptNode arguments1_;
    @Node.Child
    private JavaScriptNode arguments2_;
    @Node.Child
    private JavaScriptNode arguments3_;
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile lengthIsUndefined;
    @Node.Child
    private InteropLibrary interopArrayBuffer_interop_;
    @Node.Child
    private ObjectData object_cache;
    @Node.Child
    private ForeignObject0Data foreignObject0_cache;
    @Node.Child
    private ForeignObject1Data foreignObject1_cache;

    private JSConstructTypedArrayNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
        super(context, builtin);
        this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
        this.arguments3_ = arguments != null && 3 < arguments.length ? arguments[3] : null;
    }

    @Override
    public JavaScriptNode[] getArguments() {
        return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_};
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0xFBF) == 0 && state_0 != 0) {
            return this.execute_int0(state_0, frameValue);
        }
        return this.execute_generic1(state_0, frameValue);
    }

    private Object execute_int0(int state_0, VirtualFrame frameValue) {
        JSDynamicObject arguments0Value__;
        int arguments1Value_;
        Object arguments0Value_ = this.arguments0_.execute(frameValue);
        try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments2Value = this.arguments2_.execute(frameValue);
            Object arguments3Value = this.arguments3_.execute(frameValue);
            return this.executeAndSpecialize(arguments0Value_, ex.getResult(), arguments2Value, arguments3Value);
        }
        Object arguments2Value_ = this.arguments2_.execute(frameValue);
        Object arguments3Value_ = this.arguments3_.execute(frameValue);
        assert ((state_0 & 0x40) != 0);
        if (arguments0Value_ instanceof JSDynamicObject && !JSGuards.isUndefined(arguments0Value__ = (JSDynamicObject)arguments0Value_) && arguments1Value_ >= 0) {
            return this.doIntLength(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private Object foreignObject1Boundary(int state_0, ForeignObject1Data s10_, JSDynamicObject arguments0Value__, Object arguments1Value_, Object arguments2Value_, Object arguments3Value_) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary interop__ = INTEROP_LIBRARY_.getUncached(arguments1Value_);
            JSDynamicObject jSDynamicObject = this.doForeignObject(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, interop__, s10_.writeOwnNode_, s10_.importValue_, s10_.lengthIsUndefined_);
            return jSDynamicObject;
        }
        finally {
            encapsulating_.set(prev_);
        }
    }

    @ExplodeLoop
    private Object execute_generic1(int state_0, VirtualFrame frameValue) {
        Object arguments0Value_ = this.arguments0_.execute(frameValue);
        Object arguments1Value_ = this.arguments1_.execute(frameValue);
        Object arguments2Value_ = this.arguments2_.execute(frameValue);
        Object arguments3Value_ = this.arguments3_.execute(frameValue);
        if (state_0 != 0) {
            if ((state_0 & 0x7FF) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments1Value__;
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                if ((state_0 & 0x3F) != 0 && arguments1Value_ instanceof JSDynamicObject) {
                    arguments1Value__ = (JSDynamicObject)arguments1Value_;
                    if ((state_0 & 1) != 0 && !JSGuards.isUndefined(arguments0Value__) && JSArrayBuffer.isJSHeapArrayBuffer(arguments1Value__)) {
                        return this.doArrayBuffer(arguments0Value__, arguments1Value__, arguments2Value_, arguments3Value_, this.lengthIsUndefined);
                    }
                    if ((state_0 & 2) != 0 && !JSGuards.isUndefined(arguments0Value__) && JSArrayBuffer.isJSDirectArrayBuffer(arguments1Value__)) {
                        return this.doDirectArrayBuffer(arguments0Value__, arguments1Value__, arguments2Value_, arguments3Value_, this.lengthIsUndefined);
                    }
                    if ((state_0 & 4) != 0 && !JSGuards.isUndefined(arguments0Value__) && JSGuards.isJSSharedArrayBuffer(arguments1Value__)) {
                        return this.doSharedArrayBuffer(arguments0Value__, arguments1Value__, arguments2Value_, arguments3Value_, this.lengthIsUndefined);
                    }
                    if ((state_0 & 8) != 0 && !JSGuards.isUndefined(arguments0Value__) && JSArrayBuffer.isJSInteropArrayBuffer(arguments1Value__)) {
                        return this.doInteropArrayBuffer(arguments0Value__, arguments1Value__, arguments2Value_, arguments3Value_, this.lengthIsUndefined, this.interopArrayBuffer_interop_);
                    }
                    if ((state_0 & 0x10) != 0 && !JSGuards.isUndefined(arguments0Value__) && JSGuards.isJSArrayBufferView(arguments1Value__)) {
                        return this.doArrayBufferView(arguments0Value__, arguments1Value__, arguments2Value_, arguments3Value_);
                    }
                    if ((state_0 & 0x20) != 0 && !JSGuards.isUndefined(arguments0Value__) && JSGuards.isUndefined(arguments1Value__)) {
                        return this.doEmpty(arguments0Value__, arguments1Value__, arguments2Value_, arguments3Value_);
                    }
                }
                if ((state_0 & 0x40) != 0 && arguments1Value_ instanceof Integer) {
                    int arguments1Value__2 = (Integer)arguments1Value_;
                    if (!JSGuards.isUndefined(arguments0Value__) && arguments1Value__2 >= 0) {
                        return this.doIntLength(arguments0Value__, arguments1Value__2, arguments2Value_, arguments3Value_);
                    }
                }
                if (!((state_0 & 0x80) == 0 || JSGuards.isUndefined(arguments0Value__) || JSGuards.isJSObject(arguments1Value_) || JSRuntime.isForeignObject(arguments1Value_))) {
                    return this.doLength(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_);
                }
                if ((state_0 & 0x100) != 0 && arguments1Value_ instanceof JSDynamicObject) {
                    arguments1Value__ = (JSDynamicObject)arguments1Value_;
                    ObjectData s8_ = this.object_cache;
                    if (s8_ != null && !JSGuards.isUndefined(arguments0Value__) && JSGuards.isJSObject(arguments1Value__) && !JSAbstractBuffer.isJSAbstractBuffer(arguments1Value__) && !JSGuards.isJSArrayBufferView(arguments1Value__)) {
                        return this.doObject(arguments0Value__, arguments1Value__, arguments2Value_, arguments3Value_, s8_.getIteratorMethodNode_, s8_.isIterableProfile_, s8_.writeOwnNode_, s8_.iteratorCallNode_, s8_.isObjectNode_, s8_.getNextMethodNode_, s8_.iterableToListNode_, s8_.getLengthNode_, s8_.readNode_);
                    }
                }
                if ((state_0 & 0x600) != 0) {
                    ForeignObject1Data s10_;
                    if ((state_0 & 0x200) != 0) {
                        ForeignObject0Data s9_ = this.foreignObject0_cache;
                        while (s9_ != null) {
                            if (s9_.interop_.accepts(arguments1Value_) && !JSGuards.isUndefined(arguments0Value__) && JSRuntime.isForeignObject(arguments1Value_)) {
                                return this.doForeignObject(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, s9_.interop_, s9_.writeOwnNode_, s9_.importValue_, s9_.lengthIsUndefined_);
                            }
                            s9_ = s9_.next_;
                        }
                    }
                    if ((state_0 & 0x400) != 0 && (s10_ = this.foreignObject1_cache) != null && !JSGuards.isUndefined(arguments0Value__) && JSRuntime.isForeignObject(arguments1Value_)) {
                        return this.foreignObject1Boundary(state_0, s10_, arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_);
                    }
                }
            }
            if ((state_0 & 0x800) != 0 && JSGuards.isUndefined(arguments0Value_)) {
                return this.doUndefinedNewTarget(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.execute(frameValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value, Object arguments3Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                if (arguments1Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments1Value_ = (JSDynamicObject)arguments1Value;
                    if (!JSGuards.isUndefined(arguments0Value_) && JSArrayBuffer.isJSHeapArrayBuffer(arguments1Value_)) {
                        this.lengthIsUndefined = this.lengthIsUndefined == null ? ConditionProfile.createBinaryProfile() : this.lengthIsUndefined;
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.doArrayBuffer(arguments0Value_, arguments1Value_, arguments2Value, arguments3Value, this.lengthIsUndefined);
                        return jSDynamicObject;
                    }
                    if (!JSGuards.isUndefined(arguments0Value_) && JSArrayBuffer.isJSDirectArrayBuffer(arguments1Value_)) {
                        this.lengthIsUndefined = this.lengthIsUndefined == null ? ConditionProfile.createBinaryProfile() : this.lengthIsUndefined;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.doDirectArrayBuffer(arguments0Value_, arguments1Value_, arguments2Value, arguments3Value, this.lengthIsUndefined);
                        return jSDynamicObject;
                    }
                    if (!JSGuards.isUndefined(arguments0Value_) && JSGuards.isJSSharedArrayBuffer(arguments1Value_)) {
                        this.lengthIsUndefined = this.lengthIsUndefined == null ? ConditionProfile.createBinaryProfile() : this.lengthIsUndefined;
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.doSharedArrayBuffer(arguments0Value_, arguments1Value_, arguments2Value, arguments3Value, this.lengthIsUndefined);
                        return jSDynamicObject;
                    }
                    if (!JSGuards.isUndefined(arguments0Value_) && JSArrayBuffer.isJSInteropArrayBuffer(arguments1Value_)) {
                        this.lengthIsUndefined = this.lengthIsUndefined == null ? ConditionProfile.createBinaryProfile() : this.lengthIsUndefined;
                        this.interopArrayBuffer_interop_ = super.insert(INTEROP_LIBRARY_.createDispatched(5));
                        this.state_0_ = state_0 |= 8;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.doInteropArrayBuffer(arguments0Value_, arguments1Value_, arguments2Value, arguments3Value, this.lengthIsUndefined, this.interopArrayBuffer_interop_);
                        return jSDynamicObject;
                    }
                    if (!JSGuards.isUndefined(arguments0Value_) && JSGuards.isJSArrayBufferView(arguments1Value_)) {
                        this.state_0_ = state_0 |= 0x10;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.doArrayBufferView(arguments0Value_, arguments1Value_, arguments2Value, arguments3Value);
                        return jSDynamicObject;
                    }
                    if (!JSGuards.isUndefined(arguments0Value_) && JSGuards.isUndefined(arguments1Value_)) {
                        this.state_0_ = state_0 |= 0x20;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.doEmpty(arguments0Value_, arguments1Value_, arguments2Value, arguments3Value);
                        return jSDynamicObject;
                    }
                }
                if ((exclude & 1) == 0 && arguments1Value instanceof Integer) {
                    int arguments1Value_ = (Integer)arguments1Value;
                    if (!JSGuards.isUndefined(arguments0Value_) && arguments1Value_ >= 0) {
                        this.state_0_ = state_0 |= 0x40;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.doIntLength(arguments0Value_, arguments1Value_, arguments2Value, arguments3Value);
                        return jSDynamicObject;
                    }
                }
                if (!(JSGuards.isUndefined(arguments0Value_) || JSGuards.isJSObject(arguments1Value) || JSRuntime.isForeignObject(arguments1Value))) {
                    this.exclude_ = exclude |= 1;
                    state_0 &= 0xFFFFFFBF;
                    this.state_0_ = state_0 |= 0x80;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject arguments1Value_ = this.doLength(arguments0Value_, arguments1Value, arguments2Value, arguments3Value);
                    return arguments1Value_;
                }
                if (arguments1Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments1Value_ = (JSDynamicObject)arguments1Value;
                    if (!JSGuards.isUndefined(arguments0Value_) && JSGuards.isJSObject(arguments1Value_) && !JSAbstractBuffer.isJSAbstractBuffer(arguments1Value_) && !JSGuards.isJSArrayBufferView(arguments1Value_)) {
                        ObjectData s8_ = super.insert(new ObjectData());
                        s8_.getIteratorMethodNode_ = s8_.insertAccessor(this.createGetIteratorMethod());
                        s8_.isIterableProfile_ = ConditionProfile.createBinaryProfile();
                        s8_.writeOwnNode_ = s8_.insertAccessor(this.createWriteOwn());
                        s8_.iteratorCallNode_ = s8_.insertAccessor(JSFunctionCallNode.createCall());
                        s8_.isObjectNode_ = s8_.insertAccessor(IsJSObjectNode.create());
                        s8_.getNextMethodNode_ = s8_.insertAccessor(PropertyGetNode.create(JSRuntime.NEXT, this.getContext()));
                        s8_.iterableToListNode_ = s8_.insertAccessor(IterableToListNode.create());
                        s8_.getLengthNode_ = s8_.insertAccessor(this.createGetLength());
                        s8_.readNode_ = s8_.insertAccessor(ReadElementNode.create(this.getContext()));
                        VarHandle.storeStoreFence();
                        this.object_cache = s8_;
                        this.state_0_ = state_0 |= 0x100;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.doObject(arguments0Value_, arguments1Value_, arguments2Value, arguments3Value, s8_.getIteratorMethodNode_, s8_.isIterableProfile_, s8_.writeOwnNode_, s8_.iteratorCallNode_, s8_.isObjectNode_, s8_.getNextMethodNode_, s8_.iterableToListNode_, s8_.getLengthNode_, s8_.readNode_);
                        return jSDynamicObject;
                    }
                }
                if ((exclude & 2) == 0) {
                    int count9_ = 0;
                    ForeignObject0Data s9_ = this.foreignObject0_cache;
                    if ((state_0 & 0x200) != 0) {
                        while (!(s9_ == null || s9_.interop_.accepts(arguments1Value) && !JSGuards.isUndefined(arguments0Value_) && JSRuntime.isForeignObject(arguments1Value))) {
                            s9_ = s9_.next_;
                            ++count9_;
                        }
                    }
                    if (s9_ == null && !JSGuards.isUndefined(arguments0Value_) && JSRuntime.isForeignObject(arguments1Value) && count9_ < 5) {
                        s9_ = super.insert(new ForeignObject0Data(this.foreignObject0_cache));
                        s9_.interop_ = s9_.insertAccessor(INTEROP_LIBRARY_.create(arguments1Value));
                        s9_.writeOwnNode_ = s9_.insertAccessor(this.createWriteOwn());
                        s9_.importValue_ = s9_.insertAccessor(ImportValueNode.create());
                        s9_.lengthIsUndefined_ = ConditionProfile.createBinaryProfile();
                        VarHandle.storeStoreFence();
                        this.foreignObject0_cache = s9_;
                        this.state_0_ = state_0 |= 0x200;
                    }
                    if (s9_ != null) {
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.doForeignObject(arguments0Value_, arguments1Value, arguments2Value, arguments3Value, s9_.interop_, s9_.writeOwnNode_, s9_.importValue_, s9_.lengthIsUndefined_);
                        return jSDynamicObject;
                    }
                }
                InteropLibrary interop__ = null;
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    if (!JSGuards.isUndefined(arguments0Value_) && JSRuntime.isForeignObject(arguments1Value)) {
                        ForeignObject1Data s10_ = super.insert(new ForeignObject1Data());
                        interop__ = INTEROP_LIBRARY_.getUncached(arguments1Value);
                        s10_.writeOwnNode_ = s10_.insertAccessor(this.createWriteOwn());
                        s10_.importValue_ = s10_.insertAccessor(ImportValueNode.create());
                        s10_.lengthIsUndefined_ = ConditionProfile.createBinaryProfile();
                        VarHandle.storeStoreFence();
                        this.foreignObject1_cache = s10_;
                        this.exclude_ = exclude |= 2;
                        this.foreignObject0_cache = null;
                        state_0 &= 0xFFFFFDFF;
                        this.state_0_ = state_0 |= 0x400;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.doForeignObject(arguments0Value_, arguments1Value, arguments2Value, arguments3Value, interop__, s10_.writeOwnNode_, s10_.importValue_, s10_.lengthIsUndefined_);
                        return jSDynamicObject;
                    }
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }
            if (JSGuards.isUndefined(arguments0Value)) {
                this.state_0_ = state_0 |= 0x800;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.doUndefinedNewTarget(arguments0Value, arguments1Value, arguments2Value, arguments3Value);
                return jSDynamicObject;
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_}, arguments0Value, arguments1Value, arguments2Value, arguments3Value);
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
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s9_ = this.foreignObject0_cache) == null || s9_.next_ == null)) {
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
        s[0] = "doArrayBuffer";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Cloneable>>();
            cached.add(Arrays.asList(this.lengthIsUndefined));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doDirectArrayBuffer";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.lengthIsUndefined));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        s = new Object[3];
        s[0] = "doSharedArrayBuffer";
        if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.lengthIsUndefined));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[3] = s;
        s = new Object[3];
        s[0] = "doInteropArrayBuffer";
        if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.lengthIsUndefined, this.interopArrayBuffer_interop_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[4] = s;
        s = new Object[3];
        s[0] = "doArrayBufferView";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doEmpty";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        s = new Object[3];
        s[0] = "doIntLength";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : ((exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[7] = s;
        s = new Object[3];
        s[0] = "doLength";
        s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[8] = s;
        s = new Object[3];
        s[0] = "doObject";
        if ((state_0 & 0x100) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            ObjectData s8_ = this.object_cache;
            if (s8_ != null) {
                cached.add(Arrays.asList(s8_.getIteratorMethodNode_, s8_.isIterableProfile_, s8_.writeOwnNode_, s8_.iteratorCallNode_, s8_.isObjectNode_, s8_.getNextMethodNode_, s8_.iterableToListNode_, s8_.getLengthNode_, s8_.readNode_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[9] = s;
        s = new Object[3];
        s[0] = "doForeignObject";
        if ((state_0 & 0x200) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            ForeignObject0Data s9_ = this.foreignObject0_cache;
            while (s9_ != null) {
                cached.add(Arrays.asList(s9_.interop_, s9_.writeOwnNode_, s9_.importValue_, s9_.lengthIsUndefined_));
                s9_ = s9_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = (exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[10] = s;
        s = new Object[3];
        s[0] = "doForeignObject";
        if ((state_0 & 0x400) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            ForeignObject1Data s10_ = this.foreignObject1_cache;
            if (s10_ != null) {
                cached.add(Arrays.asList(s10_.writeOwnNode_, s10_.importValue_, s10_.lengthIsUndefined_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[11] = s;
        s = new Object[3];
        s[0] = "doUndefinedNewTarget";
        s[1] = (state_0 & 0x800) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[12] = s;
        return Introspection.Provider.create(data);
    }

    public static JSConstructTypedArrayNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
        return new JSConstructTypedArrayNodeGen(context, builtin, arguments);
    }

    @GeneratedBy(value=JSConstructTypedArrayNode.IntegerIndexedObjectCreateNode.class)
    static final class IntegerIndexedObjectCreateNodeGen
    extends JSConstructTypedArrayNode.IntegerIndexedObjectCreateNode
    implements Introspection.Provider {
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @CompilerDirectives.CompilationFinal
        private JSDynamicObject cachedProto_cachedProto_;
        @CompilerDirectives.CompilationFinal
        private JSObjectFactory cachedProto_objectFactory_;

        private IntegerIndexedObjectCreateNodeGen(JSContext context, TypedArrayFactory factory) {
            super(context, factory);
        }

        @Override
        JSDynamicObject execute(JSDynamicObject arg0Value, TypedArray arg1Value, int arg2Value, int arg3Value, JSDynamicObject arg4Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && this.isDefaultPrototype(arg4Value)) {
                    return this.doDefaultProto(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
                }
                if ((state_0 & 2) != 0 && !this.isDefaultPrototype(arg4Value)) {
                    assert (this.context.isMultiContext());
                    return this.doMultiContext(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
                }
                if ((state_0 & 4) != 0 && !this.isDefaultPrototype(arg4Value)) {
                    assert (!this.context.isMultiContext());
                    if (arg4Value == this.cachedProto_cachedProto_) {
                        return this.doCachedProto(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, this.cachedProto_cachedProto_, this.cachedProto_objectFactory_);
                    }
                }
                if ((state_0 & 8) != 0 && !this.isDefaultPrototype(arg4Value)) {
                    assert (!this.context.isMultiContext());
                    return this.doUncachedProto(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
        }

        private JSDynamicObject executeAndSpecialize(JSDynamicObject arg0Value, TypedArray arg1Value, int arg2Value, int arg3Value, JSDynamicObject arg4Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (this.isDefaultPrototype(arg4Value)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.doDefaultProto(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
                    return jSDynamicObject;
                }
                if (!this.isDefaultPrototype(arg4Value) && this.context.isMultiContext()) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.doMultiContext(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
                    return jSDynamicObject;
                }
                if (exclude == 0) {
                    boolean CachedProto_duplicateFound_ = false;
                    if ((state_0 & 4) != 0 && !this.isDefaultPrototype(arg4Value)) {
                        assert (!this.context.isMultiContext());
                        if (arg4Value == this.cachedProto_cachedProto_) {
                            CachedProto_duplicateFound_ = true;
                        }
                    }
                    if (!(CachedProto_duplicateFound_ || this.isDefaultPrototype(arg4Value) || this.context.isMultiContext() || (state_0 & 4) != 0)) {
                        this.cachedProto_cachedProto_ = arg4Value;
                        this.cachedProto_objectFactory_ = this.makeObjectFactory(this.cachedProto_cachedProto_);
                        this.state_0_ = state_0 |= 4;
                        CachedProto_duplicateFound_ = true;
                    }
                    if (CachedProto_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.doCachedProto(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, this.cachedProto_cachedProto_, this.cachedProto_objectFactory_);
                        return jSDynamicObject;
                    }
                }
                if (!this.isDefaultPrototype(arg4Value) && !this.context.isMultiContext()) {
                    this.exclude_ = exclude |= 1;
                    state_0 &= 0xFFFFFFFB;
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.doUncachedProto(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
                    return jSDynamicObject;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
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
            Object[] data = new Object[5];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "doDefaultProto";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doMultiContext";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "doCachedProto";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                ArrayList<List<Object>> cached = new ArrayList<List<Object>>();
                cached.add(Arrays.asList(this.cachedProto_cachedProto_, this.cachedProto_objectFactory_));
                s[2] = cached;
            } else {
                s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "doUncachedProto";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            return Introspection.Provider.create(data);
        }

        public static JSConstructTypedArrayNode.IntegerIndexedObjectCreateNode create(JSContext context, TypedArrayFactory factory) {
            return new IntegerIndexedObjectCreateNodeGen(context, factory);
        }
    }

    @GeneratedBy(value=JSConstructTypedArrayNode.class)
    private static final class ForeignObject1Data
    extends Node {
        @Node.Child
        WriteElementNode writeOwnNode_;
        @Node.Child
        ImportValueNode importValue_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile lengthIsUndefined_;

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

    @GeneratedBy(value=JSConstructTypedArrayNode.class)
    private static final class ForeignObject0Data
    extends Node {
        @Node.Child
        ForeignObject0Data next_;
        @Node.Child
        InteropLibrary interop_;
        @Node.Child
        WriteElementNode writeOwnNode_;
        @Node.Child
        ImportValueNode importValue_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile lengthIsUndefined_;

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

    @GeneratedBy(value=JSConstructTypedArrayNode.class)
    private static final class ObjectData
    extends Node {
        @Node.Child
        GetMethodNode getIteratorMethodNode_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile isIterableProfile_;
        @Node.Child
        WriteElementNode writeOwnNode_;
        @Node.Child
        JSFunctionCallNode iteratorCallNode_;
        @Node.Child
        IsJSObjectNode isObjectNode_;
        @Node.Child
        PropertyGetNode getNextMethodNode_;
        @Node.Child
        IterableToListNode iterableToListNode_;
        @Node.Child
        JSGetLengthNode getLengthNode_;
        @Node.Child
        ReadElementNode readNode_;

        ObjectData() {
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

