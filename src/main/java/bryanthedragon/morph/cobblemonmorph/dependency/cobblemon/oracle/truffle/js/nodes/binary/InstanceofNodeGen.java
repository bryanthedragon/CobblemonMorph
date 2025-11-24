/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.binary;

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
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.GetMethodNode;
import com.oracle.truffle.js.nodes.access.GetPrototypeNode;
import com.oracle.truffle.js.nodes.access.IsJSObjectNode;
import com.oracle.truffle.js.nodes.binary.InstanceofNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.interop.ForeignObjectPrototypeNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=InstanceofNode.class)
public final class InstanceofNodeGen
extends InstanceofNode
implements Introspection.Provider {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private JSObjectData jSObject_cache;
    @Node.Child
    private ForeignTargetOther0Data foreignTargetOther0_cache;

    private InstanceofNodeGen(JSContext context, JavaScriptNode left, JavaScriptNode right) {
        super(context, left, right);
    }

    @Override
    @ExplodeLoop
    public boolean executeBoolean(Object leftNodeValue, Object rightNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0x7F) != 0) {
            Object rightNodeValue_;
            if ((state_0 & 3) != 0 && rightNodeValue instanceof JSDynamicObject) {
                JSObjectData s0_;
                rightNodeValue_ = (JSDynamicObject)rightNodeValue;
                if ((state_0 & 1) != 0 && (s0_ = this.jSObject_cache) != null && s0_.isObjectNode_.executeBoolean(rightNodeValue_)) {
                    return this.doJSObject(leftNodeValue, (JSDynamicObject)rightNodeValue_, s0_.isObjectNode_, s0_.getMethodHasInstanceNode_, s0_.toBooleanNode_, s0_.callHasInstanceNode_, s0_.isCallableNode_, s0_.hasInstanceProfile_, s0_.errorBranch_);
                }
                if ((state_0 & 2) != 0 && JSGuards.isNullOrUndefined(rightNodeValue_)) {
                    return this.doNullOrUndefinedTarget(leftNodeValue, (JSDynamicObject)rightNodeValue_);
                }
            }
            if ((state_0 & 4) != 0 && rightNodeValue instanceof TruffleString) {
                rightNodeValue_ = (TruffleString)rightNodeValue;
                return this.doStringTarget(leftNodeValue, (TruffleString)rightNodeValue_);
            }
            if ((state_0 & 8) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C00) >>> 10, rightNodeValue)) {
                double rightNodeValue_2 = JSTypesGen.asImplicitDouble((state_0 & 0x3C00) >>> 10, rightNodeValue);
                return this.doDoubleTarget(leftNodeValue, rightNodeValue_2);
            }
            if ((state_0 & 0x10) != 0 && rightNodeValue instanceof Boolean) {
                boolean rightNodeValue_3 = (Boolean)rightNodeValue;
                return this.doBooleanTarget(leftNodeValue, rightNodeValue_3);
            }
            if ((state_0 & 0x20) != 0 && rightNodeValue instanceof BigInt) {
                rightNodeValue_ = (BigInt)rightNodeValue;
                return this.doBigIntTarget(leftNodeValue, (BigInt)rightNodeValue_);
            }
            if ((state_0 & 0x40) != 0 && rightNodeValue instanceof Symbol) {
                rightNodeValue_ = (Symbol)rightNodeValue;
                return this.doSymbolTarget(leftNodeValue, (Symbol)rightNodeValue_);
            }
        }
        if ((state_0 & 0x380) != 0) {
            if ((state_0 & 0x80) != 0 && leftNodeValue instanceof JSDynamicObject) {
                JSDynamicObject leftNodeValue_ = (JSDynamicObject)leftNodeValue;
                if (JSGuards.isForeignObject(rightNodeValue) && JSGuards.isJSDynamicObject(leftNodeValue_)) {
                    return this.doForeignTargetJSType(leftNodeValue_, rightNodeValue);
                }
            }
            if ((state_0 & 0x300) != 0) {
                if ((state_0 & 0x100) != 0) {
                    ForeignTargetOther0Data s8_ = this.foreignTargetOther0_cache;
                    while (s8_ != null) {
                        if (s8_.interop_.accepts(rightNodeValue) && JSGuards.isForeignObject(rightNodeValue) && !JSGuards.isJSDynamicObject(leftNodeValue)) {
                            return this.doForeignTargetOther(leftNodeValue, rightNodeValue, s8_.interop_);
                        }
                        s8_ = s8_.next_;
                    }
                }
                if ((state_0 & 0x200) != 0 && JSGuards.isForeignObject(rightNodeValue) && !JSGuards.isJSDynamicObject(leftNodeValue)) {
                    return this.foreignTargetOther1Boundary(state_0, leftNodeValue, rightNodeValue);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue, rightNodeValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private boolean foreignTargetOther1Boundary(int state_0, Object leftNodeValue, Object rightNodeValue) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary foreignTargetOther1_interop__ = INTEROP_LIBRARY_.getUncached(rightNodeValue);
            boolean bl = this.doForeignTargetOther(leftNodeValue, rightNodeValue, foreignTargetOther1_interop__);
            return bl;
        }
        finally {
            encapsulating_.set(prev_);
        }
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0x3F7) == 0 && (state_0 & 0x3FF) != 0) {
            return this.execute_double0(state_0, frameValue);
        }
        if ((state_0 & 0x3EF) == 0 && (state_0 & 0x3FF) != 0) {
            return this.execute_boolean1(state_0, frameValue);
        }
        return this.execute_generic2(state_0, frameValue);
    }

    private Object execute_double0(int state_0, VirtualFrame frameValue) {
        double rightNodeValue_;
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        long rightNodeValue_long = 0L;
        int rightNodeValue_int = 0;
        try {
            if ((state_0 & 0x3800) == 0 && (state_0 & 0x3FF) != 0) {
                rightNodeValue_ = this.rightNode.executeDouble(frameValue);
            } else if ((state_0 & 0x3400) == 0 && (state_0 & 0x3FF) != 0) {
                rightNodeValue_int = this.rightNode.executeInt(frameValue);
                rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((state_0 & 0x1C00) == 0 && (state_0 & 0x3FF) != 0) {
                rightNodeValue_long = this.rightNode.executeLong(frameValue);
                rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
                Object rightNodeValue__ = this.rightNode.execute(frameValue);
                rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C00) >>> 10, rightNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(leftNodeValue_, ex.getResult());
        }
        assert ((state_0 & 8) != 0);
        return this.doDoubleTarget(leftNodeValue_, rightNodeValue_);
    }

    private Object execute_boolean1(int state_0, VirtualFrame frameValue) {
        boolean rightNodeValue_;
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        try {
            rightNodeValue_ = this.rightNode.executeBoolean(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(leftNodeValue_, ex.getResult());
        }
        assert ((state_0 & 0x10) != 0);
        return this.doBooleanTarget(leftNodeValue_, rightNodeValue_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private Object foreignTargetOther1Boundary0(int state_0, Object leftNodeValue_, Object rightNodeValue_) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary foreignTargetOther1_interop__ = INTEROP_LIBRARY_.getUncached(rightNodeValue_);
            Boolean bl = this.doForeignTargetOther(leftNodeValue_, rightNodeValue_, foreignTargetOther1_interop__);
            return bl;
        }
        finally {
            encapsulating_.set(prev_);
        }
    }

    @ExplodeLoop
    private Object execute_generic2(int state_0, VirtualFrame frameValue) {
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        if ((state_0 & 0x7F) != 0) {
            Object rightNodeValue__;
            if ((state_0 & 3) != 0 && rightNodeValue_ instanceof JSDynamicObject) {
                JSObjectData s0_;
                rightNodeValue__ = (JSDynamicObject)rightNodeValue_;
                if ((state_0 & 1) != 0 && (s0_ = this.jSObject_cache) != null && s0_.isObjectNode_.executeBoolean(rightNodeValue__)) {
                    return this.doJSObject(leftNodeValue_, (JSDynamicObject)rightNodeValue__, s0_.isObjectNode_, s0_.getMethodHasInstanceNode_, s0_.toBooleanNode_, s0_.callHasInstanceNode_, s0_.isCallableNode_, s0_.hasInstanceProfile_, s0_.errorBranch_);
                }
                if ((state_0 & 2) != 0 && JSGuards.isNullOrUndefined(rightNodeValue__)) {
                    return this.doNullOrUndefinedTarget(leftNodeValue_, (JSDynamicObject)rightNodeValue__);
                }
            }
            if ((state_0 & 4) != 0 && rightNodeValue_ instanceof TruffleString) {
                rightNodeValue__ = (TruffleString)rightNodeValue_;
                return this.doStringTarget(leftNodeValue_, (TruffleString)rightNodeValue__);
            }
            if ((state_0 & 8) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C00) >>> 10, rightNodeValue_)) {
                double rightNodeValue__2 = JSTypesGen.asImplicitDouble((state_0 & 0x3C00) >>> 10, rightNodeValue_);
                return this.doDoubleTarget(leftNodeValue_, rightNodeValue__2);
            }
            if ((state_0 & 0x10) != 0 && rightNodeValue_ instanceof Boolean) {
                boolean rightNodeValue__3 = (Boolean)rightNodeValue_;
                return this.doBooleanTarget(leftNodeValue_, rightNodeValue__3);
            }
            if ((state_0 & 0x20) != 0 && rightNodeValue_ instanceof BigInt) {
                rightNodeValue__ = (BigInt)rightNodeValue_;
                return this.doBigIntTarget(leftNodeValue_, (BigInt)rightNodeValue__);
            }
            if ((state_0 & 0x40) != 0 && rightNodeValue_ instanceof Symbol) {
                rightNodeValue__ = (Symbol)rightNodeValue_;
                return this.doSymbolTarget(leftNodeValue_, (Symbol)rightNodeValue__);
            }
        }
        if ((state_0 & 0x380) != 0) {
            if ((state_0 & 0x80) != 0 && leftNodeValue_ instanceof JSDynamicObject) {
                JSDynamicObject leftNodeValue__ = (JSDynamicObject)leftNodeValue_;
                if (JSGuards.isForeignObject(rightNodeValue_) && JSGuards.isJSDynamicObject(leftNodeValue__)) {
                    return this.doForeignTargetJSType(leftNodeValue__, rightNodeValue_);
                }
            }
            if ((state_0 & 0x300) != 0) {
                if ((state_0 & 0x100) != 0) {
                    ForeignTargetOther0Data s8_ = this.foreignTargetOther0_cache;
                    while (s8_ != null) {
                        if (s8_.interop_.accepts(rightNodeValue_) && JSGuards.isForeignObject(rightNodeValue_) && !JSGuards.isJSDynamicObject(leftNodeValue_)) {
                            return this.doForeignTargetOther(leftNodeValue_, rightNodeValue_, s8_.interop_);
                        }
                        s8_ = s8_.next_;
                    }
                }
                if ((state_0 & 0x200) != 0 && JSGuards.isForeignObject(rightNodeValue_) && !JSGuards.isJSDynamicObject(leftNodeValue_)) {
                    return this.foreignTargetOther1Boundary0(state_0, leftNodeValue_, rightNodeValue_);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0x3F7) == 0 && (state_0 & 0x3FF) != 0) {
            return this.executeBoolean_double3(state_0, frameValue);
        }
        if ((state_0 & 0x3EF) == 0 && (state_0 & 0x3FF) != 0) {
            return this.executeBoolean_boolean4(state_0, frameValue);
        }
        return this.executeBoolean_generic5(state_0, frameValue);
    }

    private boolean executeBoolean_double3(int state_0, VirtualFrame frameValue) {
        double rightNodeValue_;
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        long rightNodeValue_long = 0L;
        int rightNodeValue_int = 0;
        try {
            if ((state_0 & 0x3800) == 0 && (state_0 & 0x3FF) != 0) {
                rightNodeValue_ = this.rightNode.executeDouble(frameValue);
            } else if ((state_0 & 0x3400) == 0 && (state_0 & 0x3FF) != 0) {
                rightNodeValue_int = this.rightNode.executeInt(frameValue);
                rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((state_0 & 0x1C00) == 0 && (state_0 & 0x3FF) != 0) {
                rightNodeValue_long = this.rightNode.executeLong(frameValue);
                rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
                Object rightNodeValue__ = this.rightNode.execute(frameValue);
                rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C00) >>> 10, rightNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(leftNodeValue_, ex.getResult());
        }
        assert ((state_0 & 8) != 0);
        return this.doDoubleTarget(leftNodeValue_, rightNodeValue_);
    }

    private boolean executeBoolean_boolean4(int state_0, VirtualFrame frameValue) {
        boolean rightNodeValue_;
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        try {
            rightNodeValue_ = this.rightNode.executeBoolean(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(leftNodeValue_, ex.getResult());
        }
        assert ((state_0 & 0x10) != 0);
        return this.doBooleanTarget(leftNodeValue_, rightNodeValue_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private boolean foreignTargetOther1Boundary1(int state_0, Object leftNodeValue_, Object rightNodeValue_) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary foreignTargetOther1_interop__ = INTEROP_LIBRARY_.getUncached(rightNodeValue_);
            boolean bl = this.doForeignTargetOther(leftNodeValue_, rightNodeValue_, foreignTargetOther1_interop__);
            return bl;
        }
        finally {
            encapsulating_.set(prev_);
        }
    }

    @ExplodeLoop
    private boolean executeBoolean_generic5(int state_0, VirtualFrame frameValue) {
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        if ((state_0 & 0x7F) != 0) {
            Object rightNodeValue__;
            if ((state_0 & 3) != 0 && rightNodeValue_ instanceof JSDynamicObject) {
                JSObjectData s0_;
                rightNodeValue__ = (JSDynamicObject)rightNodeValue_;
                if ((state_0 & 1) != 0 && (s0_ = this.jSObject_cache) != null && s0_.isObjectNode_.executeBoolean(rightNodeValue__)) {
                    return this.doJSObject(leftNodeValue_, (JSDynamicObject)rightNodeValue__, s0_.isObjectNode_, s0_.getMethodHasInstanceNode_, s0_.toBooleanNode_, s0_.callHasInstanceNode_, s0_.isCallableNode_, s0_.hasInstanceProfile_, s0_.errorBranch_);
                }
                if ((state_0 & 2) != 0 && JSGuards.isNullOrUndefined(rightNodeValue__)) {
                    return this.doNullOrUndefinedTarget(leftNodeValue_, (JSDynamicObject)rightNodeValue__);
                }
            }
            if ((state_0 & 4) != 0 && rightNodeValue_ instanceof TruffleString) {
                rightNodeValue__ = (TruffleString)rightNodeValue_;
                return this.doStringTarget(leftNodeValue_, (TruffleString)rightNodeValue__);
            }
            if ((state_0 & 8) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C00) >>> 10, rightNodeValue_)) {
                double rightNodeValue__2 = JSTypesGen.asImplicitDouble((state_0 & 0x3C00) >>> 10, rightNodeValue_);
                return this.doDoubleTarget(leftNodeValue_, rightNodeValue__2);
            }
            if ((state_0 & 0x10) != 0 && rightNodeValue_ instanceof Boolean) {
                boolean rightNodeValue__3 = (Boolean)rightNodeValue_;
                return this.doBooleanTarget(leftNodeValue_, rightNodeValue__3);
            }
            if ((state_0 & 0x20) != 0 && rightNodeValue_ instanceof BigInt) {
                rightNodeValue__ = (BigInt)rightNodeValue_;
                return this.doBigIntTarget(leftNodeValue_, (BigInt)rightNodeValue__);
            }
            if ((state_0 & 0x40) != 0 && rightNodeValue_ instanceof Symbol) {
                rightNodeValue__ = (Symbol)rightNodeValue_;
                return this.doSymbolTarget(leftNodeValue_, (Symbol)rightNodeValue__);
            }
        }
        if ((state_0 & 0x380) != 0) {
            if ((state_0 & 0x80) != 0 && leftNodeValue_ instanceof JSDynamicObject) {
                JSDynamicObject leftNodeValue__ = (JSDynamicObject)leftNodeValue_;
                if (JSGuards.isForeignObject(rightNodeValue_) && JSGuards.isJSDynamicObject(leftNodeValue__)) {
                    return this.doForeignTargetJSType(leftNodeValue__, rightNodeValue_);
                }
            }
            if ((state_0 & 0x300) != 0) {
                if ((state_0 & 0x100) != 0) {
                    ForeignTargetOther0Data s8_ = this.foreignTargetOther0_cache;
                    while (s8_ != null) {
                        if (s8_.interop_.accepts(rightNodeValue_) && JSGuards.isForeignObject(rightNodeValue_) && !JSGuards.isJSDynamicObject(leftNodeValue_)) {
                            return this.doForeignTargetOther(leftNodeValue_, rightNodeValue_, s8_.interop_);
                        }
                        s8_ = s8_.next_;
                    }
                }
                if ((state_0 & 0x200) != 0 && JSGuards.isForeignObject(rightNodeValue_) && !JSGuards.isJSDynamicObject(leftNodeValue_)) {
                    return this.foreignTargetOther1Boundary1(state_0, leftNodeValue_, rightNodeValue_);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.executeBoolean(frameValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean executeAndSpecialize(Object leftNodeValue, Object rightNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            boolean JSObject_duplicateFound_;
            Object rightNodeValue_;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (rightNodeValue instanceof JSDynamicObject) {
                IsJSObjectNode isObjectNode__;
                rightNodeValue_ = (JSDynamicObject)rightNodeValue;
                JSObjectData s0_ = this.jSObject_cache;
                JSObject_duplicateFound_ = false;
                if ((state_0 & 1) != 0 && s0_.isObjectNode_.executeBoolean(rightNodeValue_)) {
                    JSObject_duplicateFound_ = true;
                }
                if (!JSObject_duplicateFound_ && (isObjectNode__ = super.insert(IsJSObjectNode.create())).executeBoolean(rightNodeValue_) && (state_0 & 1) == 0) {
                    s0_ = super.insert(new JSObjectData());
                    s0_.isObjectNode_ = s0_.insertAccessor(isObjectNode__);
                    s0_.getMethodHasInstanceNode_ = s0_.insertAccessor(this.createGetMethodHasInstance());
                    s0_.toBooleanNode_ = s0_.insertAccessor(JSToBooleanNode.create());
                    s0_.callHasInstanceNode_ = s0_.insertAccessor(JSFunctionCallNode.createCall());
                    s0_.isCallableNode_ = s0_.insertAccessor(IsCallableNode.create());
                    s0_.hasInstanceProfile_ = ConditionProfile.createBinaryProfile();
                    s0_.errorBranch_ = BranchProfile.create();
                    VarHandle.storeStoreFence();
                    this.jSObject_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                    JSObject_duplicateFound_ = true;
                }
                if (JSObject_duplicateFound_) {
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doJSObject(leftNodeValue, (JSDynamicObject)rightNodeValue_, s0_.isObjectNode_, s0_.getMethodHasInstanceNode_, s0_.toBooleanNode_, s0_.callHasInstanceNode_, s0_.isCallableNode_, s0_.hasInstanceProfile_, s0_.errorBranch_);
                    return bl;
                }
                if (JSGuards.isNullOrUndefined(rightNodeValue_)) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doNullOrUndefinedTarget(leftNodeValue, (JSDynamicObject)rightNodeValue_);
                    return bl;
                }
            }
            if (rightNodeValue instanceof TruffleString) {
                rightNodeValue_ = (TruffleString)rightNodeValue;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                boolean s0_ = this.doStringTarget(leftNodeValue, (TruffleString)rightNodeValue_);
                return s0_;
            }
            int doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue);
            if (doubleCast1 != 0) {
                double rightNodeValue_2 = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
                state_0 |= doubleCast1 << 10;
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                boolean bl = this.doDoubleTarget(leftNodeValue, rightNodeValue_2);
                return bl;
            }
            if (rightNodeValue instanceof Boolean) {
                boolean rightNodeValue_3 = (Boolean)rightNodeValue;
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                boolean rightNodeValue_2 = this.doBooleanTarget(leftNodeValue, rightNodeValue_3);
                return rightNodeValue_2;
            }
            if (rightNodeValue instanceof BigInt) {
                BigInt rightNodeValue_4 = (BigInt)rightNodeValue;
                this.state_0_ = state_0 |= 0x20;
                lock.unlock();
                hasLock = false;
                boolean rightNodeValue_2 = this.doBigIntTarget(leftNodeValue, rightNodeValue_4);
                return rightNodeValue_2;
            }
            if (rightNodeValue instanceof Symbol) {
                Symbol rightNodeValue_5 = (Symbol)rightNodeValue;
                this.state_0_ = state_0 |= 0x40;
                lock.unlock();
                hasLock = false;
                boolean rightNodeValue_2 = this.doSymbolTarget(leftNodeValue, rightNodeValue_5);
                return rightNodeValue_2;
            }
            if (leftNodeValue instanceof JSDynamicObject) {
                JSDynamicObject leftNodeValue_ = (JSDynamicObject)leftNodeValue;
                if (JSGuards.isForeignObject(rightNodeValue) && JSGuards.isJSDynamicObject(leftNodeValue_)) {
                    this.state_0_ = state_0 |= 0x80;
                    lock.unlock();
                    hasLock = false;
                    boolean rightNodeValue_2 = this.doForeignTargetJSType(leftNodeValue_, rightNodeValue);
                    return rightNodeValue_2;
                }
            }
            if (exclude == 0) {
                int count8_ = 0;
                ForeignTargetOther0Data s8_ = this.foreignTargetOther0_cache;
                if ((state_0 & 0x100) != 0) {
                    while (!(s8_ == null || s8_.interop_.accepts(rightNodeValue) && JSGuards.isForeignObject(rightNodeValue) && !JSGuards.isJSDynamicObject(leftNodeValue))) {
                        s8_ = s8_.next_;
                        ++count8_;
                    }
                }
                if (s8_ == null && JSGuards.isForeignObject(rightNodeValue) && !JSGuards.isJSDynamicObject(leftNodeValue) && count8_ < 5) {
                    s8_ = super.insert(new ForeignTargetOther0Data(this.foreignTargetOther0_cache));
                    s8_.interop_ = s8_.insertAccessor(INTEROP_LIBRARY_.create(rightNodeValue));
                    VarHandle.storeStoreFence();
                    this.foreignTargetOther0_cache = s8_;
                    this.state_0_ = state_0 |= 0x100;
                }
                if (s8_ != null) {
                    lock.unlock();
                    hasLock = false;
                    JSObject_duplicateFound_ = this.doForeignTargetOther(leftNodeValue, rightNodeValue, s8_.interop_);
                    return JSObject_duplicateFound_;
                }
            }
            InteropLibrary foreignTargetOther1_interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                if (JSGuards.isForeignObject(rightNodeValue) && !JSGuards.isJSDynamicObject(leftNodeValue)) {
                    foreignTargetOther1_interop__ = INTEROP_LIBRARY_.getUncached(rightNodeValue);
                    this.exclude_ = exclude |= 1;
                    this.foreignTargetOther0_cache = null;
                    state_0 &= 0xFFFFFEFF;
                    this.state_0_ = state_0 |= 0x200;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doForeignTargetOther(leftNodeValue, rightNodeValue, foreignTargetOther1_interop__);
                    return bl;
                }
            }
            finally {
                encapsulating_.set(prev_);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.leftNode, this.rightNode}, leftNodeValue, rightNodeValue);
        }
        finally {
            if (hasLock) {
                lock.unlock();
            }
        }
    }

    @Override
    public NodeCost getCost() {
        ForeignTargetOther0Data s8_;
        int state_0 = this.state_0_;
        if ((state_0 & 0x3FF) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0x3FF & (state_0 & 0x3FF) - 1) == 0 && ((s8_ = this.foreignTargetOther0_cache) == null || s8_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Object>> cached;
        Object[] data = new Object[11];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doJSObject";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            JSObjectData s0_ = this.jSObject_cache;
            if (s0_ != null) {
                cached.add(Arrays.asList(s0_.isObjectNode_, s0_.getMethodHasInstanceNode_, s0_.toBooleanNode_, s0_.callHasInstanceNode_, s0_.isCallableNode_, s0_.hasInstanceProfile_, s0_.errorBranch_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doNullOrUndefinedTarget";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doStringTarget";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doDoubleTarget";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doBooleanTarget";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doBigIntTarget";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        s = new Object[3];
        s[0] = "doSymbolTarget";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[7] = s;
        s = new Object[3];
        s[0] = "doForeignTargetJSType";
        s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[8] = s;
        s = new Object[3];
        s[0] = "doForeignTargetOther";
        if ((state_0 & 0x100) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            ForeignTargetOther0Data s8_ = this.foreignTargetOther0_cache;
            while (s8_ != null) {
                cached.add(Arrays.asList(s8_.interop_));
                s8_ = s8_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[9] = s;
        s = new Object[3];
        s[0] = "doForeignTargetOther";
        if ((state_0 & 0x200) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(new Object[0]));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[10] = s;
        return Introspection.Provider.create(data);
    }

    public static InstanceofNode create(JSContext context, JavaScriptNode left, JavaScriptNode right) {
        return new InstanceofNodeGen(context, left, right);
    }

    @GeneratedBy(value=InstanceofNode.IsBoundFunctionCacheNode.class)
    public static final class IsBoundFunctionCacheNodeGen
    extends InstanceofNode.IsBoundFunctionCacheNode
    implements Introspection.Provider {
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @CompilerDirectives.CompilationFinal
        private JSDynamicObject cachedInstance_cachedFunction_;
        @CompilerDirectives.CompilationFinal
        private boolean cachedInstance_cachedIsBound_;
        @CompilerDirectives.CompilationFinal
        private CachedShapeData cachedShape_cache;

        private IsBoundFunctionCacheNodeGen(boolean multiContext) {
            super(multiContext);
        }

        @Override
        @ExplodeLoop
        public boolean executeBoolean(JSDynamicObject arg0Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    assert (!this.multiContext);
                    if (arg0Value == this.cachedInstance_cachedFunction_) {
                        return InstanceofNode.IsBoundFunctionCacheNode.doCachedInstance(arg0Value, this.cachedInstance_cachedFunction_, this.cachedInstance_cachedIsBound_);
                    }
                }
                if ((state_0 & 2) != 0) {
                    CachedShapeData s1_ = this.cachedShape_cache;
                    while (s1_ != null) {
                        if (s1_.cachedShape_.check(arg0Value)) {
                            return InstanceofNode.IsBoundFunctionCacheNode.doCachedShape(arg0Value, s1_.cachedShape_, s1_.cachedIsBound_);
                        }
                        s1_ = s1_.next_;
                    }
                }
                if ((state_0 & 4) != 0) {
                    return InstanceofNode.IsBoundFunctionCacheNode.isBoundFunction(arg0Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private boolean executeAndSpecialize(JSDynamicObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if ((exclude & 1) == 0) {
                    boolean CachedInstance_duplicateFound_ = false;
                    if ((state_0 & 1) != 0) {
                        assert (!this.multiContext);
                        if (arg0Value == this.cachedInstance_cachedFunction_) {
                            CachedInstance_duplicateFound_ = true;
                        }
                    }
                    if (!CachedInstance_duplicateFound_ && !this.multiContext && (state_0 & 1) == 0) {
                        this.cachedInstance_cachedFunction_ = arg0Value;
                        this.cachedInstance_cachedIsBound_ = InstanceofNode.IsBoundFunctionCacheNode.isBoundFunction(arg0Value);
                        this.state_0_ = state_0 |= 1;
                        CachedInstance_duplicateFound_ = true;
                    }
                    if (CachedInstance_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        boolean bl = InstanceofNode.IsBoundFunctionCacheNode.doCachedInstance(arg0Value, this.cachedInstance_cachedFunction_, this.cachedInstance_cachedIsBound_);
                        return bl;
                    }
                }
                if ((exclude & 2) == 0) {
                    Shape cachedShape__;
                    int count1_ = 0;
                    CachedShapeData s1_ = this.cachedShape_cache;
                    if ((state_0 & 2) != 0) {
                        while (s1_ != null && !s1_.cachedShape_.check(arg0Value)) {
                            s1_ = s1_.next_;
                            ++count1_;
                        }
                    }
                    if (s1_ == null && (cachedShape__ = arg0Value.getShape()).check(arg0Value) && count1_ < 3) {
                        s1_ = new CachedShapeData(this.cachedShape_cache);
                        s1_.cachedShape_ = cachedShape__;
                        s1_.cachedIsBound_ = InstanceofNode.IsBoundFunctionCacheNode.isBoundFunction(arg0Value);
                        VarHandle.storeStoreFence();
                        this.cachedShape_cache = s1_;
                        this.exclude_ = exclude |= 1;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                    }
                    if (s1_ != null) {
                        lock.unlock();
                        hasLock = false;
                        boolean bl = InstanceofNode.IsBoundFunctionCacheNode.doCachedShape(arg0Value, s1_.cachedShape_, s1_.cachedIsBound_);
                        return bl;
                    }
                }
                this.exclude_ = exclude |= 3;
                this.cachedShape_cache = null;
                state_0 &= 0xFFFFFFFC;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                boolean bl = InstanceofNode.IsBoundFunctionCacheNode.isBoundFunction(arg0Value);
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
            CachedShapeData s1_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s1_ = this.cachedShape_cache) == null || s1_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            ArrayList<List<Object>> cached;
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "doCachedInstance";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Object>>();
                cached.add(Arrays.asList(this.cachedInstance_cachedFunction_, this.cachedInstance_cachedIsBound_));
                s[2] = cached;
            } else {
                s[1] = (exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "doCachedShape";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                CachedShapeData s1_ = this.cachedShape_cache;
                while (s1_ != null) {
                    cached.add(Arrays.asList(s1_.cachedShape_, s1_.cachedIsBound_));
                    s1_ = s1_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = (exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "isBoundFunction";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static InstanceofNode.IsBoundFunctionCacheNode create(boolean multiContext) {
            return new IsBoundFunctionCacheNodeGen(multiContext);
        }

        @GeneratedBy(value=InstanceofNode.IsBoundFunctionCacheNode.class)
        private static final class CachedShapeData {
            @CompilerDirectives.CompilationFinal
            CachedShapeData next_;
            @CompilerDirectives.CompilationFinal
            Shape cachedShape_;
            @CompilerDirectives.CompilationFinal
            boolean cachedIsBound_;

            CachedShapeData(CachedShapeData next_) {
                this.next_ = next_;
            }
        }
    }

    @GeneratedBy(value=InstanceofNode.OrdinaryHasInstanceNode.class)
    public static final class OrdinaryHasInstanceNodeGen
    extends InstanceofNode.OrdinaryHasInstanceNode
    implements Introspection.Provider {
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ForeignObjectPrototypeNode foreignPrototypeNode;
        @CompilerDirectives.CompilationFinal
        private BranchProfile invalidPrototypeBranch;
        @Node.Child
        private InstanceofNode.OrdinaryHasInstanceNode ordinaryHasInstance;
        @Node.Child
        private IsJSObjectNode isObjectNode;
        @Node.Child
        private GetPrototypeNode getPrototype1Node;
        @Node.Child
        private GetPrototypeNode getPrototype2Node;
        @Node.Child
        private GetPrototypeNode getPrototype3Node;
        @CompilerDirectives.CompilationFinal
        private BranchProfile firstTrue;
        @CompilerDirectives.CompilationFinal
        private BranchProfile firstFalse;
        @CompilerDirectives.CompilationFinal
        private BranchProfile need2Hops;
        @CompilerDirectives.CompilationFinal
        private BranchProfile need3Hops;
        @CompilerDirectives.CompilationFinal
        private BranchProfile errorBranch;
        @Node.Child
        private InstanceofNode isBound_instanceofNode_;

        private OrdinaryHasInstanceNodeGen(JSContext context) {
            super(context);
        }

        @Override
        public boolean executeBoolean(Object arg0Value, Object arg1Value) {
            int state_0 = this.state_0_;
            if ((state_0 & 0x3F) != 0) {
                if ((state_0 & 1) != 0 && !this.isCallableNode.executeBoolean(arg1Value)) {
                    return this.doNotCallable(arg0Value, arg1Value);
                }
                if ((state_0 & 0x3E) != 0 && arg1Value instanceof JSDynamicObject) {
                    JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
                    if ((state_0 & 2) != 0 && JSGuards.isJSFunction(arg1Value_) && this.isBoundFunction(arg1Value_)) {
                        return this.doIsBound(arg0Value, arg1Value_, this.isBound_instanceofNode_);
                    }
                    if ((state_0 & 4) != 0 && !JSGuards.isJSObject(arg0Value) && JSGuards.isForeignObject(arg0Value) && JSGuards.isJSFunction(arg1Value_) && !this.isBoundFunction(arg1Value_)) {
                        return this.doForeignObject(arg0Value, arg1Value_, this.foreignPrototypeNode, this.invalidPrototypeBranch, this.ordinaryHasInstance);
                    }
                    if ((state_0 & 8) != 0 && !JSGuards.isJSObject(arg0Value) && !JSGuards.isForeignObject(arg0Value) && JSGuards.isJSFunction(arg1Value_) && !this.isBoundFunction(arg1Value_)) {
                        return this.doNotAnObject(arg0Value, arg1Value_);
                    }
                    if ((state_0 & 0x10) != 0 && !JSGuards.isJSObject(arg0Value) && JSGuards.isForeignObject(arg0Value) && JSGuards.isJSProxy(arg1Value_) && JSGuards.isCallableProxy(arg1Value_)) {
                        return this.doNotAnObjectProxyForeign(arg0Value, arg1Value_, this.foreignPrototypeNode, this.invalidPrototypeBranch, this.ordinaryHasInstance);
                    }
                    if ((state_0 & 0x20) != 0 && !JSGuards.isJSObject(arg0Value) && !JSGuards.isForeignObject(arg0Value) && JSGuards.isJSProxy(arg1Value_) && JSGuards.isCallableProxy(arg1Value_)) {
                        return this.doNotAnObjectProxyPrimitive(arg0Value, arg1Value_);
                    }
                }
            }
            if ((state_0 & 0xC0) != 0 && arg0Value instanceof JSDynamicObject) {
                JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
                if (arg1Value instanceof JSDynamicObject) {
                    JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
                    if ((state_0 & 0x40) != 0 && this.isObjectNode.executeBoolean(arg0Value_) && JSGuards.isJSFunction(arg1Value_) && !this.isBoundFunction(arg1Value_)) {
                        return this.doJSObject(arg0Value_, arg1Value_, this.isObjectNode, this.getPrototype1Node, this.getPrototype2Node, this.getPrototype3Node, this.firstTrue, this.firstFalse, this.need2Hops, this.need3Hops, this.errorBranch, this.invalidPrototypeBranch);
                    }
                    if ((state_0 & 0x80) != 0 && this.isObjectNode.executeBoolean(arg0Value_) && JSGuards.isJSProxy(arg1Value_) && JSGuards.isCallableProxy(arg1Value_)) {
                        return this.doJSObjectProxy(arg0Value_, arg1Value_, this.isObjectNode, this.getPrototype1Node, this.getPrototype2Node, this.getPrototype3Node, this.firstTrue, this.firstFalse, this.need2Hops, this.need3Hops, this.errorBranch, this.invalidPrototypeBranch);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        private boolean executeAndSpecialize(Object arg0Value, Object arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (!this.isCallableNode.executeBoolean(arg1Value)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doNotCallable(arg0Value, arg1Value);
                    return bl;
                }
                if (arg1Value instanceof JSDynamicObject) {
                    JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
                    if (JSGuards.isJSFunction(arg1Value_) && this.isBoundFunction(arg1Value_)) {
                        this.isBound_instanceofNode_ = super.insert(InstanceofNode.create(this.context));
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = this.doIsBound(arg0Value, arg1Value_, this.isBound_instanceofNode_);
                        return bl;
                    }
                    if (!JSGuards.isJSObject(arg0Value) && JSGuards.isForeignObject(arg0Value) && JSGuards.isJSFunction(arg1Value_) && !this.isBoundFunction(arg1Value_)) {
                        this.foreignPrototypeNode = super.insert(this.foreignPrototypeNode == null ? ForeignObjectPrototypeNode.create() : this.foreignPrototypeNode);
                        this.invalidPrototypeBranch = this.invalidPrototypeBranch == null ? BranchProfile.create() : this.invalidPrototypeBranch;
                        this.ordinaryHasInstance = super.insert(this.ordinaryHasInstance == null ? InstanceofNode.OrdinaryHasInstanceNode.create(this.context) : this.ordinaryHasInstance);
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = this.doForeignObject(arg0Value, arg1Value_, this.foreignPrototypeNode, this.invalidPrototypeBranch, this.ordinaryHasInstance);
                        return bl;
                    }
                    if (!JSGuards.isJSObject(arg0Value) && !JSGuards.isForeignObject(arg0Value) && JSGuards.isJSFunction(arg1Value_) && !this.isBoundFunction(arg1Value_)) {
                        this.state_0_ = state_0 |= 8;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = this.doNotAnObject(arg0Value, arg1Value_);
                        return bl;
                    }
                    if (!JSGuards.isJSObject(arg0Value) && JSGuards.isForeignObject(arg0Value) && JSGuards.isJSProxy(arg1Value_) && JSGuards.isCallableProxy(arg1Value_)) {
                        this.foreignPrototypeNode = super.insert(this.foreignPrototypeNode == null ? ForeignObjectPrototypeNode.create() : this.foreignPrototypeNode);
                        this.invalidPrototypeBranch = this.invalidPrototypeBranch == null ? BranchProfile.create() : this.invalidPrototypeBranch;
                        this.ordinaryHasInstance = super.insert(this.ordinaryHasInstance == null ? InstanceofNode.OrdinaryHasInstanceNode.create(this.context) : this.ordinaryHasInstance);
                        this.state_0_ = state_0 |= 0x10;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = this.doNotAnObjectProxyForeign(arg0Value, arg1Value_, this.foreignPrototypeNode, this.invalidPrototypeBranch, this.ordinaryHasInstance);
                        return bl;
                    }
                    if (!JSGuards.isJSObject(arg0Value) && !JSGuards.isForeignObject(arg0Value) && JSGuards.isJSProxy(arg1Value_) && JSGuards.isCallableProxy(arg1Value_)) {
                        this.state_0_ = state_0 |= 0x20;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = this.doNotAnObjectProxyPrimitive(arg0Value, arg1Value_);
                        return bl;
                    }
                }
                if (arg0Value instanceof JSDynamicObject) {
                    JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
                    if (arg1Value instanceof JSDynamicObject) {
                        IsJSObjectNode jSObjectProxy_isObjectNode__;
                        IsJSObjectNode jSObject_isObjectNode__2;
                        JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
                        boolean JSObject_duplicateFound_ = false;
                        if ((state_0 & 0x40) != 0 && this.isObjectNode.executeBoolean(arg0Value_) && JSGuards.isJSFunction(arg1Value_) && !this.isBoundFunction(arg1Value_)) {
                            JSObject_duplicateFound_ = true;
                        }
                        if (!JSObject_duplicateFound_ && (jSObject_isObjectNode__2 = super.insert(this.isObjectNode == null ? IsJSObjectNode.create() : this.isObjectNode)).executeBoolean(arg0Value_) && JSGuards.isJSFunction(arg1Value_) && !this.isBoundFunction(arg1Value_) && (state_0 & 0x40) == 0) {
                            if (this.isObjectNode == null) {
                                IsJSObjectNode jSObject_isObjectNode___check = super.insert(jSObject_isObjectNode__2);
                                if (jSObject_isObjectNode___check == null) {
                                    throw new AssertionError((Object)"Specialization 'doJSObject(JSDynamicObject, JSDynamicObject, IsJSObjectNode, GetPrototypeNode, GetPrototypeNode, GetPrototypeNode, BranchProfile, BranchProfile, BranchProfile, BranchProfile, BranchProfile, BranchProfile)' contains a shared cache with name 'isObjectNode' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                                }
                                this.isObjectNode = jSObject_isObjectNode___check;
                            }
                            this.getPrototype1Node = super.insert(this.getPrototype1Node == null ? GetPrototypeNode.create() : this.getPrototype1Node);
                            this.getPrototype2Node = super.insert(this.getPrototype2Node == null ? GetPrototypeNode.create() : this.getPrototype2Node);
                            this.getPrototype3Node = super.insert(this.getPrototype3Node == null ? GetPrototypeNode.create() : this.getPrototype3Node);
                            this.firstTrue = this.firstTrue == null ? BranchProfile.create() : this.firstTrue;
                            this.firstFalse = this.firstFalse == null ? BranchProfile.create() : this.firstFalse;
                            this.need2Hops = this.need2Hops == null ? BranchProfile.create() : this.need2Hops;
                            this.need3Hops = this.need3Hops == null ? BranchProfile.create() : this.need3Hops;
                            this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                            this.invalidPrototypeBranch = this.invalidPrototypeBranch == null ? BranchProfile.create() : this.invalidPrototypeBranch;
                            this.state_0_ = state_0 |= 0x40;
                            JSObject_duplicateFound_ = true;
                        }
                        if (JSObject_duplicateFound_) {
                            lock.unlock();
                            hasLock = false;
                            boolean jSObject_isObjectNode__2 = this.doJSObject(arg0Value_, arg1Value_, this.isObjectNode, this.getPrototype1Node, this.getPrototype2Node, this.getPrototype3Node, this.firstTrue, this.firstFalse, this.need2Hops, this.need3Hops, this.errorBranch, this.invalidPrototypeBranch);
                            return jSObject_isObjectNode__2;
                        }
                        boolean JSObjectProxy_duplicateFound_ = false;
                        if ((state_0 & 0x80) != 0 && this.isObjectNode.executeBoolean(arg0Value_) && JSGuards.isJSProxy(arg1Value_) && JSGuards.isCallableProxy(arg1Value_)) {
                            JSObjectProxy_duplicateFound_ = true;
                        }
                        if (!JSObjectProxy_duplicateFound_ && (jSObjectProxy_isObjectNode__ = super.insert(this.isObjectNode == null ? IsJSObjectNode.create() : this.isObjectNode)).executeBoolean(arg0Value_) && JSGuards.isJSProxy(arg1Value_) && JSGuards.isCallableProxy(arg1Value_) && (state_0 & 0x80) == 0) {
                            if (this.isObjectNode == null) {
                                IsJSObjectNode jSObjectProxy_isObjectNode___check = super.insert(jSObjectProxy_isObjectNode__);
                                if (jSObjectProxy_isObjectNode___check == null) {
                                    throw new AssertionError((Object)"Specialization 'doJSObjectProxy(JSDynamicObject, JSDynamicObject, IsJSObjectNode, GetPrototypeNode, GetPrototypeNode, GetPrototypeNode, BranchProfile, BranchProfile, BranchProfile, BranchProfile, BranchProfile, BranchProfile)' contains a shared cache with name 'isObjectNode' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                                }
                                this.isObjectNode = jSObjectProxy_isObjectNode___check;
                            }
                            this.getPrototype1Node = super.insert(this.getPrototype1Node == null ? GetPrototypeNode.create() : this.getPrototype1Node);
                            this.getPrototype2Node = super.insert(this.getPrototype2Node == null ? GetPrototypeNode.create() : this.getPrototype2Node);
                            this.getPrototype3Node = super.insert(this.getPrototype3Node == null ? GetPrototypeNode.create() : this.getPrototype3Node);
                            this.firstTrue = this.firstTrue == null ? BranchProfile.create() : this.firstTrue;
                            this.firstFalse = this.firstFalse == null ? BranchProfile.create() : this.firstFalse;
                            this.need2Hops = this.need2Hops == null ? BranchProfile.create() : this.need2Hops;
                            this.need3Hops = this.need3Hops == null ? BranchProfile.create() : this.need3Hops;
                            this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                            this.invalidPrototypeBranch = this.invalidPrototypeBranch == null ? BranchProfile.create() : this.invalidPrototypeBranch;
                            this.state_0_ = state_0 |= 0x80;
                            JSObjectProxy_duplicateFound_ = true;
                        }
                        if (JSObjectProxy_duplicateFound_) {
                            lock.unlock();
                            hasLock = false;
                            boolean bl = this.doJSObjectProxy(arg0Value_, arg1Value_, this.isObjectNode, this.getPrototype1Node, this.getPrototype2Node, this.getPrototype3Node, this.firstTrue, this.firstFalse, this.need2Hops, this.need3Hops, this.errorBranch, this.invalidPrototypeBranch);
                            return bl;
                        }
                    }
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
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
            ArrayList<List<Cloneable>> cached;
            Object[] data = new Object[9];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doNotCallable";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doIsBound";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Cloneable>>();
                cached.add(Arrays.asList(this.isBound_instanceofNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "doForeignObject";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.foreignPrototypeNode, this.invalidPrototypeBranch, this.ordinaryHasInstance));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "doNotAnObject";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            s = new Object[3];
            s[0] = "doNotAnObjectProxyForeign";
            if ((state_0 & 0x10) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.foreignPrototypeNode, this.invalidPrototypeBranch, this.ordinaryHasInstance));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[5] = s;
            s = new Object[3];
            s[0] = "doNotAnObjectProxyPrimitive";
            s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[6] = s;
            s = new Object[3];
            s[0] = "doJSObject";
            if ((state_0 & 0x40) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.isObjectNode, this.getPrototype1Node, this.getPrototype2Node, this.getPrototype3Node, this.firstTrue, this.firstFalse, this.need2Hops, this.need3Hops, this.errorBranch, this.invalidPrototypeBranch));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[7] = s;
            s = new Object[3];
            s[0] = "doJSObjectProxy";
            if ((state_0 & 0x80) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.isObjectNode, this.getPrototype1Node, this.getPrototype2Node, this.getPrototype3Node, this.firstTrue, this.firstFalse, this.need2Hops, this.need3Hops, this.errorBranch, this.invalidPrototypeBranch));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[8] = s;
            return Introspection.Provider.create(data);
        }

        public static InstanceofNode.OrdinaryHasInstanceNode create(JSContext context) {
            return new OrdinaryHasInstanceNodeGen(context);
        }
    }

    @GeneratedBy(value=InstanceofNode.class)
    private static final class ForeignTargetOther0Data
    extends Node {
        @Node.Child
        ForeignTargetOther0Data next_;
        @Node.Child
        InteropLibrary interop_;

        ForeignTargetOther0Data(ForeignTargetOther0Data next_) {
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

    @GeneratedBy(value=InstanceofNode.class)
    private static final class JSObjectData
    extends Node {
        @Node.Child
        IsJSObjectNode isObjectNode_;
        @Node.Child
        GetMethodNode getMethodHasInstanceNode_;
        @Node.Child
        JSToBooleanNode toBooleanNode_;
        @Node.Child
        JSFunctionCallNode callHasInstanceNode_;
        @Node.Child
        IsCallableNode isCallableNode_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile hasInstanceProfile_;
        @CompilerDirectives.CompilationFinal
        BranchProfile errorBranch_;

        JSObjectData() {
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

