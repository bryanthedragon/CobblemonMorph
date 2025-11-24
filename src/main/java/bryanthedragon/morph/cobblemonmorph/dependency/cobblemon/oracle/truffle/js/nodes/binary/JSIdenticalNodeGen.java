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
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.binary.JSIdenticalNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSIdenticalNode.class)
public final class JSIdenticalNodeGen
extends JSIdenticalNode
implements Introspection.Provider {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int state_1_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private InteropLibrary isNullInterop;
    @Node.Child
    private TruffleString.EqualNode truffleString_equalsNode_;
    @CompilerDirectives.CompilationFinal
    private NumberNotNumberCachedData numberNotNumberCached_cache;
    @Node.Child
    private ForeignObject0Data foreignObject0_cache;

    private JSIdenticalNodeGen(JavaScriptNode left, JavaScriptNode right, int type) {
        super(left, right, type);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @ExplodeLoop
    public boolean executeBoolean(Object leftNodeValue, Object rightNodeValue) {
        Object rightNodeValue_;
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && leftNodeValue instanceof Integer) {
            int n = (Integer)leftNodeValue;
            if (rightNodeValue instanceof Integer) {
                int rightNodeValue_2 = (Integer)rightNodeValue;
                return JSIdenticalNode.doInt(n, rightNodeValue_2);
            }
        }
        if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x78000000) >>> 27, leftNodeValue)) {
            double d = JSTypesGen.asImplicitDouble((state_0 & 0x78000000) >>> 27, leftNodeValue);
            if (JSTypesGen.isImplicitDouble(this.state_1_ >>> 0, rightNodeValue)) {
                double rightNodeValue_3 = JSTypesGen.asImplicitDouble(this.state_1_ >>> 0, rightNodeValue);
                return this.doDouble(d, rightNodeValue_3);
            }
        }
        if ((state_0 & 4) != 0 && leftNodeValue instanceof Boolean) {
            boolean bl = (Boolean)leftNodeValue;
            if (rightNodeValue instanceof Boolean) {
                boolean rightNodeValue_4 = (Boolean)rightNodeValue;
                return JSIdenticalNode.doBoolean(bl, rightNodeValue_4);
            }
        }
        if ((state_0 & 0x18) != 0 && leftNodeValue instanceof BigInt) {
            BigInt bigInt = (BigInt)leftNodeValue;
            if ((state_0 & 8) != 0 && rightNodeValue instanceof BigInt) {
                BigInt rightNodeValue_5 = (BigInt)rightNodeValue;
                return JSIdenticalNode.doBigInt(bigInt, rightNodeValue_5);
            }
            if ((state_0 & 0x10) != 0 && JSTypesGen.isImplicitDouble(this.state_1_ >>> 0, rightNodeValue)) {
                double rightNodeValue_6 = JSTypesGen.asImplicitDouble(this.state_1_ >>> 0, rightNodeValue);
                return JSIdenticalNode.doBigIntDouble(bigInt, rightNodeValue_6);
            }
        }
        if ((state_0 & 0x20) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x78000000) >>> 27, leftNodeValue)) {
            double d = JSTypesGen.asImplicitDouble((state_0 & 0x78000000) >>> 27, leftNodeValue);
            if (rightNodeValue instanceof BigInt) {
                BigInt rightNodeValue_7 = (BigInt)rightNodeValue;
                return JSIdenticalNode.doDoubleBigInt(d, rightNodeValue_7);
            }
        }
        if ((state_0 & 0x1C0) != 0) {
            if ((state_0 & 0xC0) != 0) {
                if ((state_0 & 0x40) != 0 && JSGuards.isUndefined(leftNodeValue)) {
                    return JSIdenticalNode.doUndefinedA(leftNodeValue, rightNodeValue);
                }
                if ((state_0 & 0x80) != 0 && JSGuards.isUndefined(rightNodeValue)) {
                    return JSIdenticalNode.doUndefinedB(leftNodeValue, rightNodeValue);
                }
            }
            if ((state_0 & 0x100) != 0 && leftNodeValue instanceof JSObject) {
                JSObject jSObject = (JSObject)leftNodeValue;
                return JSIdenticalNode.doJSObjectA(jSObject, rightNodeValue);
            }
        }
        if ((state_0 & 0x7E00) != 0) {
            if ((state_0 & 0x200) != 0 && rightNodeValue instanceof JSObject) {
                JSObject jSObject = (JSObject)rightNodeValue;
                return JSIdenticalNode.doJSObjectB(leftNodeValue, jSObject);
            }
            if ((state_0 & 0x7C00) != 0) {
                if ((state_0 & 0x400) != 0 && JSGuards.isJSNull(leftNodeValue) && JSGuards.isJSNull(rightNodeValue)) {
                    return JSIdenticalNode.doNullNull(leftNodeValue, rightNodeValue);
                }
                if ((state_0 & 0x800) != 0 && JSGuards.isJSNull(leftNodeValue) && JSGuards.isUndefined(rightNodeValue)) {
                    return JSIdenticalNode.doNullUndefined(leftNodeValue, rightNodeValue);
                }
                if ((state_0 & 0x1000) != 0 && JSGuards.isUndefined(leftNodeValue) && JSGuards.isJSNull(rightNodeValue)) {
                    return JSIdenticalNode.doUndefinedNull(leftNodeValue, rightNodeValue);
                }
                if ((state_0 & 0x2000) != 0 && JSGuards.isJSNull(leftNodeValue) && !JSRuntime.isNullOrUndefined(rightNodeValue)) {
                    return JSIdenticalNode.doNullA(leftNodeValue, rightNodeValue, this.isNullInterop);
                }
                if ((state_0 & 0x4000) != 0 && !JSRuntime.isNullOrUndefined(leftNodeValue) && JSGuards.isJSNull(rightNodeValue)) {
                    return JSIdenticalNode.doNullB(leftNodeValue, rightNodeValue, this.isNullInterop);
                }
            }
        }
        if ((state_0 & 0x18000) != 0 && leftNodeValue instanceof TruffleString) {
            TruffleString truffleString = (TruffleString)leftNodeValue;
            if (rightNodeValue instanceof TruffleString) {
                rightNodeValue_ = (TruffleString)rightNodeValue;
                if ((state_0 & 0x8000) != 0 && JSGuards.isReferenceEquals(truffleString, rightNodeValue_)) {
                    return JSIdenticalNode.doTruffleStringIdentity(truffleString, (TruffleString)rightNodeValue_);
                }
                if ((state_0 & 0x10000) != 0) {
                    return JSIdenticalNode.doTruffleString(truffleString, (TruffleString)rightNodeValue_, this.truffleString_equalsNode_);
                }
            }
        }
        if ((state_0 & 0x20000) != 0 && leftNodeValue instanceof Symbol) {
            Symbol symbol = (Symbol)leftNodeValue;
            if (rightNodeValue instanceof Symbol) {
                rightNodeValue_ = (Symbol)rightNodeValue;
                return JSIdenticalNode.doSymbol(symbol, (Symbol)rightNodeValue_);
            }
        }
        if ((state_0 & 0x7C0000) != 0) {
            if ((state_0 & 0x40000) != 0 && JSGuards.isBoolean(leftNodeValue) != JSGuards.isBoolean(rightNodeValue)) {
                return JSIdenticalNode.doBooleanNotBoolean(leftNodeValue, rightNodeValue);
            }
            if ((state_0 & 0x80000) != 0 && JSGuards.isSymbol(leftNodeValue) != JSGuards.isSymbol(rightNodeValue)) {
                return JSIdenticalNode.doSymbolNotSymbol(leftNodeValue, rightNodeValue);
            }
            if ((state_0 & 0x100000) != 0) {
                void var4_14;
                NumberNotNumberCachedData numberNotNumberCachedData = this.numberNotNumberCached_cache;
                while (var4_14 != null) {
                    if (leftNodeValue.getClass() == var4_14.cachedClassA_ && rightNodeValue.getClass() == var4_14.cachedClassB_) {
                        assert (JSIdenticalNode.isJavaNumberType(var4_14.cachedClassA_) != JSIdenticalNode.isJavaNumberType(var4_14.cachedClassB_));
                        return JSIdenticalNode.doNumberNotNumberCached(leftNodeValue, rightNodeValue, var4_14.cachedClassA_, var4_14.cachedClassB_);
                    }
                    NumberNotNumberCachedData numberNotNumberCachedData2 = var4_14.next_;
                }
            }
            if ((state_0 & 0x200000) != 0 && JSRuntime.isJavaNumber(leftNodeValue) != JSRuntime.isJavaNumber(rightNodeValue)) {
                return JSIdenticalNode.doNumberNotNumber(leftNodeValue, rightNodeValue);
            }
            if ((state_0 & 0x400000) != 0 && JSGuards.isString(leftNodeValue) != JSGuards.isString(rightNodeValue)) {
                return JSIdenticalNode.doStringNotString(leftNodeValue, rightNodeValue);
            }
        }
        if ((state_0 & 0x800000) != 0 && leftNodeValue instanceof Number) {
            Number number = (Number)leftNodeValue;
            if (rightNodeValue instanceof Number) {
                rightNodeValue_ = (Number)rightNodeValue;
                if (JSRuntime.isJavaNumber(number) && JSRuntime.isJavaNumber(rightNodeValue_)) {
                    return this.doNumber(number, (Number)rightNodeValue_);
                }
            }
        }
        if ((state_0 & 0x7000000) != 0) {
            if ((state_0 & 0x1000000) != 0) {
                void var4_18;
                ForeignObject0Data foreignObject0Data = this.foreignObject0_cache;
                while (var4_18 != null) {
                    if (var4_18.aInterop_.accepts(leftNodeValue) && var4_18.bInterop_.accepts(rightNodeValue) && JSRuntime.isForeignObject(leftNodeValue) && JSRuntime.isForeignObject(rightNodeValue)) {
                        return JSIdenticalNode.doForeignObject(leftNodeValue, rightNodeValue, var4_18.aInterop_, var4_18.bInterop_);
                    }
                    ForeignObject0Data foreignObject0Data2 = var4_18.next_;
                }
            }
            if ((state_0 & 0x2000000) != 0 && JSRuntime.isForeignObject(leftNodeValue) && JSRuntime.isForeignObject(rightNodeValue)) {
                return this.foreignObject1Boundary(state_0, leftNodeValue, rightNodeValue);
            }
            if ((state_0 & 0x4000000) != 0 && JSIdenticalNodeGen.fallbackGuard_(state_0, leftNodeValue, rightNodeValue)) {
                return JSIdenticalNode.doFallback(leftNodeValue, rightNodeValue);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue, rightNodeValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private boolean foreignObject1Boundary(int state_0, Object leftNodeValue, Object rightNodeValue) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary foreignObject1_aInterop__ = INTEROP_LIBRARY_.getUncached(leftNodeValue);
            InteropLibrary foreignObject1_bInterop__ = INTEROP_LIBRARY_.getUncached(rightNodeValue);
            boolean bl = JSIdenticalNode.doForeignObject(leftNodeValue, rightNodeValue, foreignObject1_aInterop__, foreignObject1_bInterop__);
            return bl;
        }
        finally {
            encapsulating_.set(prev_);
        }
    }

    @Override
    public boolean executeBoolean(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0x7FFFFFE) == 0 && (state_0 & 0x7FFFFFF) != 0) {
            return this.executeBoolean_int_int0(state_0, frameValue);
        }
        if ((state_0 & 0x7FFFFFD) == 0 && (state_0 & 0x7FFFFFF) != 0) {
            return this.executeBoolean_double_double1(state_0, frameValue);
        }
        if ((state_0 & 0x7FFFFFB) == 0 && (state_0 & 0x7FFFFFF) != 0) {
            return this.executeBoolean_boolean_boolean2(state_0, frameValue);
        }
        if ((state_0 & 0x7FFFFEF) == 0 && (state_0 & 0x7FFFFFF) != 0) {
            return this.executeBoolean_double3(state_0, frameValue);
        }
        if ((state_0 & 0x7FFFFDF) == 0 && (state_0 & 0x7FFFFFF) != 0) {
            return this.executeBoolean_double4(state_0, frameValue);
        }
        return this.executeBoolean_generic5(state_0, frameValue);
    }

    private boolean executeBoolean_int_int0(int state_0, VirtualFrame frameValue) {
        int rightNodeValue_;
        int leftNodeValue_;
        try {
            leftNodeValue_ = this.leftNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = this.rightNode.execute(frameValue);
            return this.executeAndSpecialize(ex.getResult(), rightNodeValue);
        }
        try {
            rightNodeValue_ = this.rightNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(leftNodeValue_, ex.getResult());
        }
        assert ((state_0 & 1) != 0);
        return JSIdenticalNode.doInt(leftNodeValue_, rightNodeValue_);
    }

    private boolean executeBoolean_double_double1(int state_0, VirtualFrame frameValue) {
        double rightNodeValue_;
        double leftNodeValue_;
        long leftNodeValue_long = 0L;
        int leftNodeValue_int = 0;
        try {
            if ((state_0 & 0x70000000) == 0 && (state_0 & 0x7FFFFFF) != 0) {
                leftNodeValue_ = this.leftNode.executeDouble(frameValue);
            } else if ((state_0 & 0x68000000) == 0 && (state_0 & 0x7FFFFFF) != 0) {
                leftNodeValue_int = this.leftNode.executeInt(frameValue);
                leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            } else if ((state_0 & 0x38000000) == 0 && (state_0 & 0x7FFFFFF) != 0) {
                leftNodeValue_long = this.leftNode.executeLong(frameValue);
                leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
            } else {
                Object leftNodeValue__ = this.leftNode.execute(frameValue);
                leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x78000000) >>> 27, leftNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = this.rightNode.execute(frameValue);
            return this.executeAndSpecialize(ex.getResult(), rightNodeValue);
        }
        long rightNodeValue_long = 0L;
        int rightNodeValue_int = 0;
        try {
            if ((this.state_1_ & 0xE) == 0 && (state_0 & 0x7FFFFFF) != 0) {
                rightNodeValue_ = this.rightNode.executeDouble(frameValue);
            } else if ((this.state_1_ & 0xD) == 0 && (state_0 & 0x7FFFFFF) != 0) {
                rightNodeValue_int = this.rightNode.executeInt(frameValue);
                rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((this.state_1_ & 7) == 0 && (state_0 & 0x7FFFFFF) != 0) {
                rightNodeValue_long = this.rightNode.executeLong(frameValue);
                rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
                Object rightNodeValue__ = this.rightNode.execute(frameValue);
                rightNodeValue_ = JSTypesGen.expectImplicitDouble(this.state_1_ >>> 0, rightNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize((state_0 & 0x68000000) == 0 && (state_0 & 0x7FFFFFF) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0x38000000) == 0 && (state_0 & 0x7FFFFFF) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), ex.getResult());
        }
        assert ((state_0 & 2) != 0);
        return this.doDouble(leftNodeValue_, rightNodeValue_);
    }

    private boolean executeBoolean_boolean_boolean2(int state_0, VirtualFrame frameValue) {
        boolean rightNodeValue_;
        boolean leftNodeValue_;
        try {
            leftNodeValue_ = this.leftNode.executeBoolean(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = this.rightNode.execute(frameValue);
            return this.executeAndSpecialize(ex.getResult(), rightNodeValue);
        }
        try {
            rightNodeValue_ = this.rightNode.executeBoolean(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(leftNodeValue_, ex.getResult());
        }
        assert ((state_0 & 4) != 0);
        return JSIdenticalNode.doBoolean(leftNodeValue_, rightNodeValue_);
    }

    private boolean executeBoolean_double3(int state_0, VirtualFrame frameValue) {
        double rightNodeValue_;
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        long rightNodeValue_long = 0L;
        int rightNodeValue_int = 0;
        try {
            if ((this.state_1_ & 0xE) == 0 && (state_0 & 0x7FFFFFF) != 0) {
                rightNodeValue_ = this.rightNode.executeDouble(frameValue);
            } else if ((this.state_1_ & 0xD) == 0 && (state_0 & 0x7FFFFFF) != 0) {
                rightNodeValue_int = this.rightNode.executeInt(frameValue);
                rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((this.state_1_ & 7) == 0 && (state_0 & 0x7FFFFFF) != 0) {
                rightNodeValue_long = this.rightNode.executeLong(frameValue);
                rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
                Object rightNodeValue__ = this.rightNode.execute(frameValue);
                rightNodeValue_ = JSTypesGen.expectImplicitDouble(this.state_1_ >>> 0, rightNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(leftNodeValue_, ex.getResult());
        }
        assert ((state_0 & 0x10) != 0);
        if (leftNodeValue_ instanceof BigInt) {
            BigInt leftNodeValue__ = (BigInt)leftNodeValue_;
            return JSIdenticalNode.doBigIntDouble(leftNodeValue__, rightNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, (this.state_1_ & 0xD) == 0 && (state_0 & 0x7FFFFFF) != 0 ? (Number)rightNodeValue_int : (Number)((this.state_1_ & 7) == 0 && (state_0 & 0x7FFFFFF) != 0 ? (Number)rightNodeValue_long : (Number)rightNodeValue_));
    }

    private boolean executeBoolean_double4(int state_0, VirtualFrame frameValue) {
        double leftNodeValue_;
        long leftNodeValue_long = 0L;
        int leftNodeValue_int = 0;
        try {
            if ((state_0 & 0x70000000) == 0 && (state_0 & 0x7FFFFFF) != 0) {
                leftNodeValue_ = this.leftNode.executeDouble(frameValue);
            } else if ((state_0 & 0x68000000) == 0 && (state_0 & 0x7FFFFFF) != 0) {
                leftNodeValue_int = this.leftNode.executeInt(frameValue);
                leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            } else if ((state_0 & 0x38000000) == 0 && (state_0 & 0x7FFFFFF) != 0) {
                leftNodeValue_long = this.leftNode.executeLong(frameValue);
                leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
            } else {
                Object leftNodeValue__ = this.leftNode.execute(frameValue);
                leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x78000000) >>> 27, leftNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = this.rightNode.execute(frameValue);
            return this.executeAndSpecialize(ex.getResult(), rightNodeValue);
        }
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        assert ((state_0 & 0x20) != 0);
        if (rightNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
            return JSIdenticalNode.doDoubleBigInt(leftNodeValue_, rightNodeValue__);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize((state_0 & 0x68000000) == 0 && (state_0 & 0x7FFFFFF) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0x38000000) == 0 && (state_0 & 0x7FFFFFF) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), rightNodeValue_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private boolean foreignObject1Boundary0(int state_0, Object leftNodeValue_, Object rightNodeValue_) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary foreignObject1_aInterop__ = INTEROP_LIBRARY_.getUncached(leftNodeValue_);
            InteropLibrary foreignObject1_bInterop__ = INTEROP_LIBRARY_.getUncached(rightNodeValue_);
            boolean bl = JSIdenticalNode.doForeignObject(leftNodeValue_, rightNodeValue_, foreignObject1_aInterop__, foreignObject1_bInterop__);
            return bl;
        }
        finally {
            encapsulating_.set(prev_);
        }
    }

    /*
     * WARNING - void declaration
     */
    @ExplodeLoop
    private boolean executeBoolean_generic5(int state_0, VirtualFrame frameValue) {
        Object rightNodeValue__;
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        if ((state_0 & 1) != 0 && leftNodeValue_ instanceof Integer) {
            int n = (Integer)leftNodeValue_;
            if (rightNodeValue_ instanceof Integer) {
                int rightNodeValue__2 = (Integer)rightNodeValue_;
                return JSIdenticalNode.doInt(n, rightNodeValue__2);
            }
        }
        if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x78000000) >>> 27, leftNodeValue_)) {
            double d = JSTypesGen.asImplicitDouble((state_0 & 0x78000000) >>> 27, leftNodeValue_);
            if (JSTypesGen.isImplicitDouble(this.state_1_ >>> 0, rightNodeValue_)) {
                double rightNodeValue__3 = JSTypesGen.asImplicitDouble(this.state_1_ >>> 0, rightNodeValue_);
                return this.doDouble(d, rightNodeValue__3);
            }
        }
        if ((state_0 & 4) != 0 && leftNodeValue_ instanceof Boolean) {
            boolean bl = (Boolean)leftNodeValue_;
            if (rightNodeValue_ instanceof Boolean) {
                boolean rightNodeValue__4 = (Boolean)rightNodeValue_;
                return JSIdenticalNode.doBoolean(bl, rightNodeValue__4);
            }
        }
        if ((state_0 & 0x18) != 0 && leftNodeValue_ instanceof BigInt) {
            BigInt bigInt = (BigInt)leftNodeValue_;
            if ((state_0 & 8) != 0 && rightNodeValue_ instanceof BigInt) {
                BigInt rightNodeValue__5 = (BigInt)rightNodeValue_;
                return JSIdenticalNode.doBigInt(bigInt, rightNodeValue__5);
            }
            if ((state_0 & 0x10) != 0 && JSTypesGen.isImplicitDouble(this.state_1_ >>> 0, rightNodeValue_)) {
                double rightNodeValue__6 = JSTypesGen.asImplicitDouble(this.state_1_ >>> 0, rightNodeValue_);
                return JSIdenticalNode.doBigIntDouble(bigInt, rightNodeValue__6);
            }
        }
        if ((state_0 & 0x20) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x78000000) >>> 27, leftNodeValue_)) {
            double d = JSTypesGen.asImplicitDouble((state_0 & 0x78000000) >>> 27, leftNodeValue_);
            if (rightNodeValue_ instanceof BigInt) {
                BigInt rightNodeValue__7 = (BigInt)rightNodeValue_;
                return JSIdenticalNode.doDoubleBigInt(d, rightNodeValue__7);
            }
        }
        if ((state_0 & 0x1C0) != 0) {
            if ((state_0 & 0xC0) != 0) {
                if ((state_0 & 0x40) != 0 && JSGuards.isUndefined(leftNodeValue_)) {
                    return JSIdenticalNode.doUndefinedA(leftNodeValue_, rightNodeValue_);
                }
                if ((state_0 & 0x80) != 0 && JSGuards.isUndefined(rightNodeValue_)) {
                    return JSIdenticalNode.doUndefinedB(leftNodeValue_, rightNodeValue_);
                }
            }
            if ((state_0 & 0x100) != 0 && leftNodeValue_ instanceof JSObject) {
                JSObject jSObject = (JSObject)leftNodeValue_;
                return JSIdenticalNode.doJSObjectA(jSObject, rightNodeValue_);
            }
        }
        if ((state_0 & 0x7E00) != 0) {
            if ((state_0 & 0x200) != 0 && rightNodeValue_ instanceof JSObject) {
                JSObject jSObject = (JSObject)rightNodeValue_;
                return JSIdenticalNode.doJSObjectB(leftNodeValue_, jSObject);
            }
            if ((state_0 & 0x7C00) != 0) {
                if ((state_0 & 0x400) != 0 && JSGuards.isJSNull(leftNodeValue_) && JSGuards.isJSNull(rightNodeValue_)) {
                    return JSIdenticalNode.doNullNull(leftNodeValue_, rightNodeValue_);
                }
                if ((state_0 & 0x800) != 0 && JSGuards.isJSNull(leftNodeValue_) && JSGuards.isUndefined(rightNodeValue_)) {
                    return JSIdenticalNode.doNullUndefined(leftNodeValue_, rightNodeValue_);
                }
                if ((state_0 & 0x1000) != 0 && JSGuards.isUndefined(leftNodeValue_) && JSGuards.isJSNull(rightNodeValue_)) {
                    return JSIdenticalNode.doUndefinedNull(leftNodeValue_, rightNodeValue_);
                }
                if ((state_0 & 0x2000) != 0 && JSGuards.isJSNull(leftNodeValue_) && !JSRuntime.isNullOrUndefined(rightNodeValue_)) {
                    return JSIdenticalNode.doNullA(leftNodeValue_, rightNodeValue_, this.isNullInterop);
                }
                if ((state_0 & 0x4000) != 0 && !JSRuntime.isNullOrUndefined(leftNodeValue_) && JSGuards.isJSNull(rightNodeValue_)) {
                    return JSIdenticalNode.doNullB(leftNodeValue_, rightNodeValue_, this.isNullInterop);
                }
            }
        }
        if ((state_0 & 0x18000) != 0 && leftNodeValue_ instanceof TruffleString) {
            TruffleString truffleString = (TruffleString)leftNodeValue_;
            if (rightNodeValue_ instanceof TruffleString) {
                rightNodeValue__ = (TruffleString)rightNodeValue_;
                if ((state_0 & 0x8000) != 0 && JSGuards.isReferenceEquals(truffleString, rightNodeValue__)) {
                    return JSIdenticalNode.doTruffleStringIdentity(truffleString, (TruffleString)rightNodeValue__);
                }
                if ((state_0 & 0x10000) != 0) {
                    return JSIdenticalNode.doTruffleString(truffleString, (TruffleString)rightNodeValue__, this.truffleString_equalsNode_);
                }
            }
        }
        if ((state_0 & 0x20000) != 0 && leftNodeValue_ instanceof Symbol) {
            Symbol symbol = (Symbol)leftNodeValue_;
            if (rightNodeValue_ instanceof Symbol) {
                rightNodeValue__ = (Symbol)rightNodeValue_;
                return JSIdenticalNode.doSymbol(symbol, (Symbol)rightNodeValue__);
            }
        }
        if ((state_0 & 0x7C0000) != 0) {
            if ((state_0 & 0x40000) != 0 && JSGuards.isBoolean(leftNodeValue_) != JSGuards.isBoolean(rightNodeValue_)) {
                return JSIdenticalNode.doBooleanNotBoolean(leftNodeValue_, rightNodeValue_);
            }
            if ((state_0 & 0x80000) != 0 && JSGuards.isSymbol(leftNodeValue_) != JSGuards.isSymbol(rightNodeValue_)) {
                return JSIdenticalNode.doSymbolNotSymbol(leftNodeValue_, rightNodeValue_);
            }
            if ((state_0 & 0x100000) != 0) {
                void var5_15;
                NumberNotNumberCachedData numberNotNumberCachedData = this.numberNotNumberCached_cache;
                while (var5_15 != null) {
                    if (leftNodeValue_.getClass() == var5_15.cachedClassA_ && rightNodeValue_.getClass() == var5_15.cachedClassB_) {
                        assert (JSIdenticalNode.isJavaNumberType(var5_15.cachedClassA_) != JSIdenticalNode.isJavaNumberType(var5_15.cachedClassB_));
                        return JSIdenticalNode.doNumberNotNumberCached(leftNodeValue_, rightNodeValue_, var5_15.cachedClassA_, var5_15.cachedClassB_);
                    }
                    NumberNotNumberCachedData numberNotNumberCachedData2 = var5_15.next_;
                }
            }
            if ((state_0 & 0x200000) != 0 && JSRuntime.isJavaNumber(leftNodeValue_) != JSRuntime.isJavaNumber(rightNodeValue_)) {
                return JSIdenticalNode.doNumberNotNumber(leftNodeValue_, rightNodeValue_);
            }
            if ((state_0 & 0x400000) != 0 && JSGuards.isString(leftNodeValue_) != JSGuards.isString(rightNodeValue_)) {
                return JSIdenticalNode.doStringNotString(leftNodeValue_, rightNodeValue_);
            }
        }
        if ((state_0 & 0x800000) != 0 && leftNodeValue_ instanceof Number) {
            Number number = (Number)leftNodeValue_;
            if (rightNodeValue_ instanceof Number) {
                rightNodeValue__ = (Number)rightNodeValue_;
                if (JSRuntime.isJavaNumber(number) && JSRuntime.isJavaNumber(rightNodeValue__)) {
                    return this.doNumber(number, (Number)rightNodeValue__);
                }
            }
        }
        if ((state_0 & 0x7000000) != 0) {
            if ((state_0 & 0x1000000) != 0) {
                void var5_19;
                ForeignObject0Data foreignObject0Data = this.foreignObject0_cache;
                while (var5_19 != null) {
                    if (var5_19.aInterop_.accepts(leftNodeValue_) && var5_19.bInterop_.accepts(rightNodeValue_) && JSRuntime.isForeignObject(leftNodeValue_) && JSRuntime.isForeignObject(rightNodeValue_)) {
                        return JSIdenticalNode.doForeignObject(leftNodeValue_, rightNodeValue_, var5_19.aInterop_, var5_19.bInterop_);
                    }
                    ForeignObject0Data foreignObject0Data2 = var5_19.next_;
                }
            }
            if ((state_0 & 0x2000000) != 0 && JSRuntime.isForeignObject(leftNodeValue_) && JSRuntime.isForeignObject(rightNodeValue_)) {
                return this.foreignObject1Boundary0(state_0, leftNodeValue_, rightNodeValue_);
            }
            if ((state_0 & 0x4000000) != 0 && JSIdenticalNodeGen.fallbackGuard_(state_0, leftNodeValue_, rightNodeValue_)) {
                return JSIdenticalNode.doFallback(leftNodeValue_, rightNodeValue_);
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
     * WARNING - void declaration
     */
    private boolean executeAndSpecialize(Object leftNodeValue, Object rightNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int n;
            int n2;
            int state_0 = this.state_0_;
            int state_1 = this.state_1_;
            int exclude = this.exclude_;
            if (leftNodeValue instanceof Integer) {
                int n3 = (Integer)leftNodeValue;
                if (rightNodeValue instanceof Integer) {
                    int rightNodeValue_2 = (Integer)rightNodeValue;
                    this.state_0_ = state_0 |= 1;
                    this.state_1_ = state_1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = JSIdenticalNode.doInt(n3, rightNodeValue_2);
                    return bl;
                }
            }
            if ((n2 = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
                double leftNodeValue_2 = JSTypesGen.asImplicitDouble(n2, leftNodeValue);
                int doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue);
                if (doubleCast1 != 0) {
                    double rightNodeValue_3 = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
                    state_0 |= n2 << 27;
                    this.state_0_ = state_0 |= 2;
                    this.state_1_ = state_1 |= doubleCast1 << 0;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doDouble(leftNodeValue_2, rightNodeValue_3);
                    return bl;
                }
            }
            if (leftNodeValue instanceof Boolean) {
                boolean bl = (Boolean)leftNodeValue;
                if (rightNodeValue instanceof Boolean) {
                    boolean rightNodeValue_4 = (Boolean)rightNodeValue;
                    this.state_0_ = state_0 |= 4;
                    this.state_1_ = state_1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl2 = JSIdenticalNode.doBoolean(bl, rightNodeValue_4);
                    return bl2;
                }
            }
            if (leftNodeValue instanceof BigInt) {
                BigInt bigInt = (BigInt)leftNodeValue;
                if (rightNodeValue instanceof BigInt) {
                    BigInt rightNodeValue_5 = (BigInt)rightNodeValue;
                    this.state_0_ = state_0 |= 8;
                    this.state_1_ = state_1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = JSIdenticalNode.doBigInt(bigInt, rightNodeValue_5);
                    return bl;
                }
                int doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue);
                if (doubleCast1 != 0) {
                    double rightNodeValue_6 = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
                    this.state_0_ = state_0 |= 0x10;
                    this.state_1_ = state_1 |= doubleCast1 << 0;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = JSIdenticalNode.doBigIntDouble(bigInt, rightNodeValue_6);
                    return bl;
                }
            }
            if ((n = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
                double leftNodeValue_4 = JSTypesGen.asImplicitDouble(n, leftNodeValue);
                if (rightNodeValue instanceof BigInt) {
                    BigInt rightNodeValue_7 = (BigInt)rightNodeValue;
                    state_0 |= n << 27;
                    this.state_0_ = state_0 |= 0x20;
                    this.state_1_ = state_1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = JSIdenticalNode.doDoubleBigInt(leftNodeValue_4, rightNodeValue_7);
                    return bl;
                }
            }
            if (JSGuards.isUndefined(leftNodeValue)) {
                this.state_0_ = state_0 |= 0x40;
                this.state_1_ = state_1;
                lock.unlock();
                hasLock = false;
                boolean bl = JSIdenticalNode.doUndefinedA(leftNodeValue, rightNodeValue);
                return bl;
            }
            if (JSGuards.isUndefined(rightNodeValue)) {
                this.state_0_ = state_0 |= 0x80;
                this.state_1_ = state_1;
                lock.unlock();
                hasLock = false;
                boolean bl = JSIdenticalNode.doUndefinedB(leftNodeValue, rightNodeValue);
                return bl;
            }
            if (leftNodeValue instanceof JSObject) {
                JSObject jSObject = (JSObject)leftNodeValue;
                this.state_0_ = state_0 |= 0x100;
                this.state_1_ = state_1;
                lock.unlock();
                hasLock = false;
                boolean leftNodeValue_4 = JSIdenticalNode.doJSObjectA(jSObject, rightNodeValue);
                return leftNodeValue_4;
            }
            if (rightNodeValue instanceof JSObject) {
                JSObject jSObject = (JSObject)rightNodeValue;
                this.state_0_ = state_0 |= 0x200;
                this.state_1_ = state_1;
                lock.unlock();
                hasLock = false;
                boolean leftNodeValue_4 = JSIdenticalNode.doJSObjectB(leftNodeValue, jSObject);
                return leftNodeValue_4;
            }
            if (JSGuards.isJSNull(leftNodeValue) && JSGuards.isJSNull(rightNodeValue)) {
                this.state_0_ = state_0 |= 0x400;
                this.state_1_ = state_1;
                lock.unlock();
                hasLock = false;
                boolean bl = JSIdenticalNode.doNullNull(leftNodeValue, rightNodeValue);
                return bl;
            }
            if (JSGuards.isJSNull(leftNodeValue) && JSGuards.isUndefined(rightNodeValue)) {
                this.state_0_ = state_0 |= 0x800;
                this.state_1_ = state_1;
                lock.unlock();
                hasLock = false;
                boolean bl = JSIdenticalNode.doNullUndefined(leftNodeValue, rightNodeValue);
                return bl;
            }
            if (JSGuards.isUndefined(leftNodeValue) && JSGuards.isJSNull(rightNodeValue)) {
                this.state_0_ = state_0 |= 0x1000;
                this.state_1_ = state_1;
                lock.unlock();
                hasLock = false;
                boolean bl = JSIdenticalNode.doUndefinedNull(leftNodeValue, rightNodeValue);
                return bl;
            }
            if (JSGuards.isJSNull(leftNodeValue) && !JSRuntime.isNullOrUndefined(rightNodeValue)) {
                this.isNullInterop = super.insert(this.isNullInterop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.isNullInterop);
                this.state_0_ = state_0 |= 0x2000;
                this.state_1_ = state_1;
                lock.unlock();
                hasLock = false;
                boolean bl = JSIdenticalNode.doNullA(leftNodeValue, rightNodeValue, this.isNullInterop);
                return bl;
            }
            if (!JSRuntime.isNullOrUndefined(leftNodeValue) && JSGuards.isJSNull(rightNodeValue)) {
                this.isNullInterop = super.insert(this.isNullInterop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.isNullInterop);
                this.state_0_ = state_0 |= 0x4000;
                this.state_1_ = state_1;
                lock.unlock();
                hasLock = false;
                boolean bl = JSIdenticalNode.doNullB(leftNodeValue, rightNodeValue, this.isNullInterop);
                return bl;
            }
            if (leftNodeValue instanceof TruffleString) {
                TruffleString truffleString = (TruffleString)leftNodeValue;
                if (rightNodeValue instanceof TruffleString) {
                    TruffleString rightNodeValue_9 = (TruffleString)rightNodeValue;
                    if ((exclude & 1) == 0 && JSGuards.isReferenceEquals(truffleString, rightNodeValue_9)) {
                        this.state_0_ = state_0 |= 0x8000;
                        this.state_1_ = state_1;
                        lock.unlock();
                        hasLock = false;
                        boolean rightNodeValue_6 = JSIdenticalNode.doTruffleStringIdentity(truffleString, rightNodeValue_9);
                        return rightNodeValue_6;
                    }
                    this.truffleString_equalsNode_ = super.insert(TruffleString.EqualNode.create());
                    this.exclude_ = exclude |= 1;
                    state_0 &= 0xFFFF7FFF;
                    this.state_0_ = state_0 |= 0x10000;
                    this.state_1_ = state_1;
                    lock.unlock();
                    hasLock = false;
                    boolean rightNodeValue_6 = JSIdenticalNode.doTruffleString(truffleString, rightNodeValue_9, this.truffleString_equalsNode_);
                    return rightNodeValue_6;
                }
            }
            if (leftNodeValue instanceof Symbol) {
                Symbol bl = (Symbol)leftNodeValue;
                if (rightNodeValue instanceof Symbol) {
                    Symbol rightNodeValue_10 = (Symbol)rightNodeValue;
                    this.state_0_ = state_0 |= 0x20000;
                    this.state_1_ = state_1;
                    lock.unlock();
                    hasLock = false;
                    boolean rightNodeValue_6 = JSIdenticalNode.doSymbol(bl, rightNodeValue_10);
                    return rightNodeValue_6;
                }
            }
            if (JSGuards.isBoolean(leftNodeValue) != JSGuards.isBoolean(rightNodeValue)) {
                this.state_0_ = state_0 |= 0x40000;
                this.state_1_ = state_1;
                lock.unlock();
                hasLock = false;
                boolean bl = JSIdenticalNode.doBooleanNotBoolean(leftNodeValue, rightNodeValue);
                return bl;
            }
            if (JSGuards.isSymbol(leftNodeValue) != JSGuards.isSymbol(rightNodeValue)) {
                this.state_0_ = state_0 |= 0x80000;
                this.state_1_ = state_1;
                lock.unlock();
                hasLock = false;
                boolean bl = JSIdenticalNode.doSymbolNotSymbol(leftNodeValue, rightNodeValue);
                return bl;
            }
            if ((exclude & 2) == 0) {
                void var8_27;
                boolean bl = false;
                NumberNotNumberCachedData s20_ = this.numberNotNumberCached_cache;
                if ((state_0 & 0x100000) != 0) {
                    while (s20_ != null) {
                        if (leftNodeValue.getClass() == s20_.cachedClassA_ && rightNodeValue.getClass() == s20_.cachedClassB_) {
                            assert (JSIdenticalNode.isJavaNumberType(s20_.cachedClassA_) != JSIdenticalNode.isJavaNumberType(s20_.cachedClassB_));
                            break;
                        }
                        s20_ = s20_.next_;
                        ++var8_27;
                    }
                }
                if (s20_ == null) {
                    Class<?> cachedClassA__ = leftNodeValue.getClass();
                    if (leftNodeValue.getClass() == cachedClassA__) {
                        Class<?> cachedClassB__ = rightNodeValue.getClass();
                        if (rightNodeValue.getClass() == cachedClassB__ && JSIdenticalNode.isJavaNumberType(cachedClassA__) != JSIdenticalNode.isJavaNumberType(cachedClassB__) && var8_27 < 3) {
                            s20_ = new NumberNotNumberCachedData(this.numberNotNumberCached_cache);
                            s20_.cachedClassA_ = cachedClassA__;
                            s20_.cachedClassB_ = cachedClassB__;
                            VarHandle.storeStoreFence();
                            this.numberNotNumberCached_cache = s20_;
                            this.state_0_ = state_0 |= 0x100000;
                            this.state_1_ = state_1;
                        }
                    }
                }
                if (s20_ != null) {
                    lock.unlock();
                    hasLock = false;
                    boolean cachedClassA__ = JSIdenticalNode.doNumberNotNumberCached(leftNodeValue, rightNodeValue, s20_.cachedClassA_, s20_.cachedClassB_);
                    return cachedClassA__;
                }
            }
            if (JSRuntime.isJavaNumber(leftNodeValue) != JSRuntime.isJavaNumber(rightNodeValue)) {
                this.exclude_ = exclude |= 2;
                this.numberNotNumberCached_cache = null;
                state_0 &= 0xFFEFFFFF;
                this.state_0_ = state_0 |= 0x200000;
                this.state_1_ = state_1;
                lock.unlock();
                hasLock = false;
                boolean bl = JSIdenticalNode.doNumberNotNumber(leftNodeValue, rightNodeValue);
                return bl;
            }
            if (JSGuards.isString(leftNodeValue) != JSGuards.isString(rightNodeValue)) {
                this.state_0_ = state_0 |= 0x400000;
                this.state_1_ = state_1;
                lock.unlock();
                hasLock = false;
                boolean bl = JSIdenticalNode.doStringNotString(leftNodeValue, rightNodeValue);
                return bl;
            }
            if (leftNodeValue instanceof Number) {
                Number number = (Number)leftNodeValue;
                if (rightNodeValue instanceof Number) {
                    Number rightNodeValue_11 = (Number)rightNodeValue;
                    if (JSRuntime.isJavaNumber(number) && JSRuntime.isJavaNumber(rightNodeValue_11)) {
                        this.state_0_ = state_0 |= 0x800000;
                        this.state_1_ = state_1;
                        lock.unlock();
                        hasLock = false;
                        boolean cachedClassA__ = this.doNumber(number, rightNodeValue_11);
                        return cachedClassA__;
                    }
                }
            }
            if ((exclude & 4) == 0) {
                void var8_32;
                boolean bl = false;
                ForeignObject0Data s24_ = this.foreignObject0_cache;
                if ((state_0 & 0x1000000) != 0) {
                    while (!(s24_ == null || s24_.aInterop_.accepts(leftNodeValue) && s24_.bInterop_.accepts(rightNodeValue) && JSRuntime.isForeignObject(leftNodeValue) && JSRuntime.isForeignObject(rightNodeValue))) {
                        s24_ = s24_.next_;
                        ++var8_32;
                    }
                }
                if (s24_ == null && JSRuntime.isForeignObject(leftNodeValue) && JSRuntime.isForeignObject(rightNodeValue) && var8_32 < 5) {
                    s24_ = super.insert(new ForeignObject0Data(this.foreignObject0_cache));
                    s24_.aInterop_ = s24_.insertAccessor(INTEROP_LIBRARY_.create(leftNodeValue));
                    s24_.bInterop_ = s24_.insertAccessor(INTEROP_LIBRARY_.create(rightNodeValue));
                    VarHandle.storeStoreFence();
                    this.foreignObject0_cache = s24_;
                    this.state_0_ = state_0 |= 0x1000000;
                    this.state_1_ = state_1;
                }
                if (s24_ != null) {
                    lock.unlock();
                    hasLock = false;
                    boolean cachedClassA__ = JSIdenticalNode.doForeignObject(leftNodeValue, rightNodeValue, s24_.aInterop_, s24_.bInterop_);
                    return cachedClassA__;
                }
            }
            Object var8_33 = null;
            InteropLibrary foreignObject1_aInterop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                if (JSRuntime.isForeignObject(leftNodeValue) && JSRuntime.isForeignObject(rightNodeValue)) {
                    foreignObject1_aInterop__ = INTEROP_LIBRARY_.getUncached(leftNodeValue);
                    InteropLibrary interopLibrary = INTEROP_LIBRARY_.getUncached(rightNodeValue);
                    this.exclude_ = exclude |= 4;
                    this.foreignObject0_cache = null;
                    state_0 &= 0xFEFFFFFF;
                    this.state_0_ = state_0 |= 0x2000000;
                    this.state_1_ = state_1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = JSIdenticalNode.doForeignObject(leftNodeValue, rightNodeValue, foreignObject1_aInterop__, interopLibrary);
                    return bl;
                }
            }
            finally {
                encapsulating_.set(prev_);
            }
            this.state_0_ = state_0 |= 0x4000000;
            this.state_1_ = state_1;
            lock.unlock();
            hasLock = false;
            boolean bl = JSIdenticalNode.doFallback(leftNodeValue, rightNodeValue);
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
        if ((state_0 & 0x7FFFFFF) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        int counter = 0;
        if ((counter += Integer.bitCount(state_0 & 0x7FFFFFF)) == 1) {
            NumberNotNumberCachedData s20_ = this.numberNotNumberCached_cache;
            ForeignObject0Data s24_ = this.foreignObject0_cache;
            if (!(s20_ != null && s20_.next_ != null || s24_ != null && s24_.next_ != null)) {
                return NodeCost.MONOMORPHIC;
            }
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Object>> cached;
        Object[] data = new Object[28];
        data[0] = 0;
        int state_0 = this.state_0_;
        int state_1 = this.state_1_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doInt";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doDouble";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doBoolean";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doBigIntDouble";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doDoubleBigInt";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        s = new Object[3];
        s[0] = "doUndefinedA";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[7] = s;
        s = new Object[3];
        s[0] = "doUndefinedB";
        s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[8] = s;
        s = new Object[3];
        s[0] = "doJSObjectA";
        s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[9] = s;
        s = new Object[3];
        s[0] = "doJSObjectB";
        s[1] = (state_0 & 0x200) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[10] = s;
        s = new Object[3];
        s[0] = "doNullNull";
        s[1] = (state_0 & 0x400) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[11] = s;
        s = new Object[3];
        s[0] = "doNullUndefined";
        s[1] = (state_0 & 0x800) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[12] = s;
        s = new Object[3];
        s[0] = "doUndefinedNull";
        s[1] = (state_0 & 0x1000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[13] = s;
        s = new Object[3];
        s[0] = "doNullA";
        if ((state_0 & 0x2000) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            cached.add(Arrays.asList(this.isNullInterop));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[14] = s;
        s = new Object[3];
        s[0] = "doNullB";
        if ((state_0 & 0x4000) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.isNullInterop));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[15] = s;
        s = new Object[3];
        s[0] = "doTruffleStringIdentity";
        s[1] = (state_0 & 0x8000) != 0 ? Byte.valueOf((byte)1) : ((exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[16] = s;
        s = new Object[3];
        s[0] = "doTruffleString";
        if ((state_0 & 0x10000) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.truffleString_equalsNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[17] = s;
        s = new Object[3];
        s[0] = "doSymbol";
        s[1] = (state_0 & 0x20000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[18] = s;
        s = new Object[3];
        s[0] = "doBooleanNotBoolean";
        s[1] = (state_0 & 0x40000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[19] = s;
        s = new Object[3];
        s[0] = "doSymbolNotSymbol";
        s[1] = (state_0 & 0x80000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[20] = s;
        s = new Object[3];
        s[0] = "doNumberNotNumberCached";
        if ((state_0 & 0x100000) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            NumberNotNumberCachedData s20_ = this.numberNotNumberCached_cache;
            while (s20_ != null) {
                cached.add(Arrays.asList(s20_.cachedClassA_, s20_.cachedClassB_));
                s20_ = s20_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = (exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[21] = s;
        s = new Object[3];
        s[0] = "doNumberNotNumber";
        s[1] = (state_0 & 0x200000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[22] = s;
        s = new Object[3];
        s[0] = "doStringNotString";
        s[1] = (state_0 & 0x400000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[23] = s;
        s = new Object[3];
        s[0] = "doNumber";
        s[1] = (state_0 & 0x800000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[24] = s;
        s = new Object[3];
        s[0] = "doForeignObject";
        if ((state_0 & 0x1000000) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            ForeignObject0Data s24_ = this.foreignObject0_cache;
            while (s24_ != null) {
                cached.add(Arrays.asList(s24_.aInterop_, s24_.bInterop_));
                s24_ = s24_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = (exclude & 4) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[25] = s;
        s = new Object[3];
        s[0] = "doForeignObject";
        if ((state_0 & 0x2000000) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(new Object[0]));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[26] = s;
        s = new Object[3];
        s[0] = "doFallback";
        s[1] = (state_0 & 0x4000000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[27] = s;
        return Introspection.Provider.create(data);
    }

    private static boolean fallbackGuard_(int state_0, Object leftNodeValue, Object rightNodeValue) {
        Number rightNodeValue_;
        Number leftNodeValue_;
        if (JSTypesGen.isImplicitDouble(leftNodeValue) && JSTypesGen.isImplicitDouble(rightNodeValue)) {
            return false;
        }
        if ((state_0 & 4) == 0 && leftNodeValue instanceof Boolean && rightNodeValue instanceof Boolean) {
            return false;
        }
        if (leftNodeValue instanceof BigInt) {
            if ((state_0 & 8) == 0 && rightNodeValue instanceof BigInt) {
                return false;
            }
            if (JSTypesGen.isImplicitDouble(rightNodeValue)) {
                return false;
            }
        }
        if (JSTypesGen.isImplicitDouble(leftNodeValue) && rightNodeValue instanceof BigInt) {
            return false;
        }
        if ((state_0 & 0x40) == 0 && JSGuards.isUndefined(leftNodeValue)) {
            return false;
        }
        if ((state_0 & 0x80) == 0 && JSGuards.isUndefined(rightNodeValue)) {
            return false;
        }
        if ((state_0 & 0x100) == 0 && leftNodeValue instanceof JSObject) {
            return false;
        }
        if ((state_0 & 0x200) == 0 && rightNodeValue instanceof JSObject) {
            return false;
        }
        if ((state_0 & 0x400) == 0 && JSGuards.isJSNull(leftNodeValue) && JSGuards.isJSNull(rightNodeValue)) {
            return false;
        }
        if ((state_0 & 0x800) == 0 && JSGuards.isJSNull(leftNodeValue) && JSGuards.isUndefined(rightNodeValue)) {
            return false;
        }
        if ((state_0 & 0x1000) == 0 && JSGuards.isUndefined(leftNodeValue) && JSGuards.isJSNull(rightNodeValue)) {
            return false;
        }
        if ((state_0 & 0x2000) == 0 && JSGuards.isJSNull(leftNodeValue) && !JSRuntime.isNullOrUndefined(rightNodeValue)) {
            return false;
        }
        if ((state_0 & 0x4000) == 0 && !JSRuntime.isNullOrUndefined(leftNodeValue) && JSGuards.isJSNull(rightNodeValue)) {
            return false;
        }
        if ((state_0 & 0x10000) == 0 && leftNodeValue instanceof TruffleString && rightNodeValue instanceof TruffleString) {
            return false;
        }
        if ((state_0 & 0x20000) == 0 && leftNodeValue instanceof Symbol && rightNodeValue instanceof Symbol) {
            return false;
        }
        if ((state_0 & 0x40000) == 0 && JSGuards.isBoolean(leftNodeValue) != JSGuards.isBoolean(rightNodeValue)) {
            return false;
        }
        if ((state_0 & 0x80000) == 0 && JSGuards.isSymbol(leftNodeValue) != JSGuards.isSymbol(rightNodeValue)) {
            return false;
        }
        if ((state_0 & 0x200000) == 0 && JSRuntime.isJavaNumber(leftNodeValue) != JSRuntime.isJavaNumber(rightNodeValue)) {
            return false;
        }
        if ((state_0 & 0x400000) == 0 && JSGuards.isString(leftNodeValue) != JSGuards.isString(rightNodeValue)) {
            return false;
        }
        if (leftNodeValue instanceof Number && rightNodeValue instanceof Number && JSRuntime.isJavaNumber(leftNodeValue_ = (Number)leftNodeValue) && JSRuntime.isJavaNumber(rightNodeValue_ = (Number)rightNodeValue)) {
            return false;
        }
        return (state_0 & 0x2000000) != 0 || !JSRuntime.isForeignObject(leftNodeValue) || !JSRuntime.isForeignObject(rightNodeValue);
    }

    public static JSIdenticalNode create(JavaScriptNode left, JavaScriptNode right, int type) {
        return new JSIdenticalNodeGen(left, right, type);
    }

    @GeneratedBy(value=JSIdenticalNode.class)
    private static final class ForeignObject0Data
    extends Node {
        @Node.Child
        ForeignObject0Data next_;
        @Node.Child
        InteropLibrary aInterop_;
        @Node.Child
        InteropLibrary bInterop_;

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

    @GeneratedBy(value=JSIdenticalNode.class)
    private static final class NumberNotNumberCachedData {
        @CompilerDirectives.CompilationFinal
        NumberNotNumberCachedData next_;
        @CompilerDirectives.CompilationFinal
        Class<?> cachedClassA_;
        @CompilerDirectives.CompilationFinal
        Class<?> cachedClassB_;

        NumberNotNumberCachedData(NumberNotNumberCachedData next_) {
            this.next_ = next_;
        }
    }
}

