
package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.binary.JSAddNode;
import com.oracle.truffle.js.nodes.binary.JSConcatStringsNode;
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNode;
import com.oracle.truffle.js.nodes.cast.JSDoubleToStringNode;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSAddNode.class)
public final class JSAddNodeGen
extends JSAddNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private JSConcatStringsNode concatStringsNode;
    @Node.Child
    private TruffleString.FromLongNode stringFromLongNode;
    @Node.Child
    private JSDoubleToStringNode doubleToStringNode;
    @Node.Child
    private JSOverloadedBinaryNode overloaded_overloadedOperatorNode_;
    @Node.Child
    private PrimitiveConversionData primitiveConversion_cache;

    private JSAddNodeGen(boolean truncate, JavaScriptNode left, JavaScriptNode right) {
        super(truncate, left, right);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object execute(Object leftNodeValue, Object rightNodeValue) {
        int rightNodeValue_;
        int leftNodeValue_;
        int state_0 = this.state_0_;
        if ((state_0 & 0xF) != 0 && leftNodeValue instanceof Integer) {
            leftNodeValue_ = (Integer)leftNodeValue;
            if ((state_0 & 7) != 0 && rightNodeValue instanceof Integer) {
                rightNodeValue_ = (Integer)rightNodeValue;
                if ((state_0 & 1) != 0) {
                    assert (this.truncate);
                    return JSAddNode.doIntTruncate(leftNodeValue_, rightNodeValue_);
                }
                if ((state_0 & 2) != 0) {
                    assert (!this.truncate);
                    try {
                        return JSAddNode.doInt(leftNodeValue_, rightNodeValue_);
                    }
                    catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        Lock lock = this.getLock();
                        lock.lock();
                        try {
                            this.exclude_ |= 2;
                            this.state_0_ &= 0xFFFFFFFD;
                        }
                        finally {
                            lock.unlock();
                        }
                        return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
                    }
                }
                if ((state_0 & 4) != 0) {
                    assert (!this.truncate);
                    try {
                        return JSAddNode.doIntOverflow(leftNodeValue_, rightNodeValue_);
                    }
                    catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        Lock lock = this.getLock();
                        lock.lock();
                        try {
                            this.exclude_ |= 4;
                            this.state_0_ &= 0xFFFFFFFB;
                        }
                        finally {
                            lock.unlock();
                        }
                        return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
                    }
                }
            }
            if ((state_0 & 8) != 0 && rightNodeValue instanceof SafeInteger) {
                SafeInteger rightNodeValue_2 = (SafeInteger)rightNodeValue;
                try {
                    return JSAddNode.doIntSafeInteger(leftNodeValue_, rightNodeValue_2);
                }
                catch (ArithmeticException ex) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    Lock lock = this.getLock();
                    lock.lock();
                    try {
                        this.exclude_ |= 8;
                        this.state_0_ &= 0xFFFFFFF7;
                    }
                    finally {
                        lock.unlock();
                    }
                    return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_2);
                }
            }
        }
        if ((state_0 & 0x30) != 0 && leftNodeValue instanceof SafeInteger) {
            SafeInteger leftNodeValue_2 = (SafeInteger)leftNodeValue;
            if ((state_0 & 0x10) != 0 && rightNodeValue instanceof Integer) {
                rightNodeValue_ = (Integer)rightNodeValue;
                try {
                    return JSAddNode.doSafeIntegerInt(leftNodeValue_2, rightNodeValue_);
                }
                catch (ArithmeticException ex) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    Lock lock = this.getLock();
                    lock.lock();
                    try {
                        this.exclude_ |= 0x10;
                        this.state_0_ &= 0xFFFFFFEF;
                    }
                    finally {
                        lock.unlock();
                    }
                    return this.executeAndSpecialize(leftNodeValue_2, rightNodeValue_);
                }
            }
            if ((state_0 & 0x20) != 0 && rightNodeValue instanceof SafeInteger) {
                SafeInteger rightNodeValue_3 = (SafeInteger)rightNodeValue;
                try {
                    return JSAddNode.doSafeInteger(leftNodeValue_2, rightNodeValue_3);
                }
                catch (ArithmeticException ex) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    Lock lock = this.getLock();
                    lock.lock();
                    try {
                        this.exclude_ |= 0x20;
                        this.state_0_ &= 0xFFFFFFDF;
                    }
                    finally {
                        lock.unlock();
                    }
                    return this.executeAndSpecialize(leftNodeValue_2, rightNodeValue_3);
                }
            }
        }
        if ((state_0 & 0x40) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x78000) >>> 15, leftNodeValue)) {
            double leftNodeValue_3 = JSTypesGen.asImplicitDouble((state_0 & 0x78000) >>> 15, leftNodeValue);
            if (JSTypesGen.isImplicitDouble((state_0 & 0x780000) >>> 19, rightNodeValue)) {
                double rightNodeValue_4 = JSTypesGen.asImplicitDouble((state_0 & 0x780000) >>> 19, rightNodeValue);
                return JSAddNode.doDouble(leftNodeValue_3, rightNodeValue_4);
            }
        }
        if ((state_0 & 0x80) != 0 && leftNodeValue instanceof BigInt) {
            BigInt leftNodeValue_4 = (BigInt)leftNodeValue;
            if (rightNodeValue instanceof BigInt) {
                BigInt rightNodeValue_5 = (BigInt)rightNodeValue;
                return this.doBigInt(leftNodeValue_4, rightNodeValue_5);
            }
        }
        if ((state_0 & 0x300) != 0 && leftNodeValue instanceof TruffleString) {
            TruffleString leftNodeValue_5 = (TruffleString)leftNodeValue;
            if ((state_0 & 0x100) != 0 && rightNodeValue instanceof TruffleString) {
                TruffleString rightNodeValue_6 = (TruffleString)rightNodeValue;
                return this.doString(leftNodeValue_5, rightNodeValue_6, this.concatStringsNode);
            }
            if ((state_0 & 0x200) != 0 && rightNodeValue instanceof Integer) {
                rightNodeValue_ = (Integer)rightNodeValue;
                return this.doStringInt(leftNodeValue_5, rightNodeValue_, this.concatStringsNode, this.stringFromLongNode);
            }
        }
        if ((state_0 & 0x400) != 0 && leftNodeValue instanceof Integer) {
            leftNodeValue_ = (Integer)leftNodeValue;
            if (rightNodeValue instanceof TruffleString) {
                TruffleString rightNodeValue_7 = (TruffleString)rightNodeValue;
                return this.doIntString(leftNodeValue_, rightNodeValue_7, this.concatStringsNode, this.stringFromLongNode);
            }
        }
        if ((state_0 & 0x800) != 0 && leftNodeValue instanceof TruffleString) {
            TruffleString leftNodeValue_6 = (TruffleString)leftNodeValue;
            if (JSGuards.isNumber(rightNodeValue)) {
                return this.doStringNumber(leftNodeValue_6, rightNodeValue, this.concatStringsNode, this.doubleToStringNode);
            }
        }
        if ((state_0 & 0x7000) != 0) {
            if ((state_0 & 0x1000) != 0 && rightNodeValue instanceof TruffleString) {
                TruffleString rightNodeValue_8 = (TruffleString)rightNodeValue;
                if (JSGuards.isNumber(leftNodeValue)) {
                    return this.doNumberString(leftNodeValue, rightNodeValue_8, this.concatStringsNode, this.doubleToStringNode);
                }
            }
            if ((state_0 & 0x6000) != 0) {
                PrimitiveConversionData s14_;
                if ((state_0 & 0x2000) != 0 && (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue))) {
                    return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_);
                }
                if ((state_0 & 0x4000) != 0 && (s14_ = this.primitiveConversion_cache) != null && !this.hasOverloadedOperators(leftNodeValue) && !this.hasOverloadedOperators(rightNodeValue)) {
                    return this.doPrimitiveConversion(leftNodeValue, rightNodeValue, s14_.toPrimitiveA_, s14_.toPrimitiveB_, s14_.toNumericA_, s14_.toNumericB_, s14_.toStringA_, s14_.toStringB_, s14_.profileA_, s14_.profileB_, s14_.add_, s14_.mixedNumericTypes_);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue, rightNodeValue);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0x7FF8) == 0 && (state_0 & Short.MAX_VALUE) != 0) {
            return this.execute_int_int0(state_0, frameValue);
        }
        if ((state_0 & 0x7FBF) == 0 && (state_0 & Short.MAX_VALUE) != 0) {
            return this.execute_double_double1(state_0, frameValue);
        }
        if ((state_0 & 0x7BF7) == 0 && (state_0 & Short.MAX_VALUE) != 0) {
            return this.execute_int2(state_0, frameValue);
        }
        if ((state_0 & 0x7DEF) == 0 && (state_0 & Short.MAX_VALUE) != 0) {
            return this.execute_int3(state_0, frameValue);
        }
        return this.execute_generic4(state_0, frameValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object execute_int_int0(int state_0, VirtualFrame frameValue) {
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
        if ((state_0 & 1) != 0) {
            assert (this.truncate);
            return JSAddNode.doIntTruncate(leftNodeValue_, rightNodeValue_);
        }
        if ((state_0 & 2) != 0) {
            assert (!this.truncate);
            try {
                return JSAddNode.doInt(leftNodeValue_, rightNodeValue_);
            }
            catch (ArithmeticException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Lock lock = this.getLock();
                lock.lock();
                try {
                    this.exclude_ |= 2;
                    this.state_0_ &= 0xFFFFFFFD;
                }
                finally {
                    lock.unlock();
                }
                return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
            }
        }
        if ((state_0 & 4) != 0) {
            assert (!this.truncate);
            try {
                return JSAddNode.doIntOverflow(leftNodeValue_, rightNodeValue_);
            }
            catch (ArithmeticException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Lock lock = this.getLock();
                lock.lock();
                try {
                    this.exclude_ |= 4;
                    this.state_0_ &= 0xFFFFFFFB;
                }
                finally {
                    lock.unlock();
                }
                return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
    }

    private Object execute_double_double1(int state_0, VirtualFrame frameValue) {
        double rightNodeValue_;
        double leftNodeValue_;
        long leftNodeValue_long = 0L;
        int leftNodeValue_int = 0;
        try {
            if ((state_0 & 0x70000) == 0 && (state_0 & Short.MAX_VALUE) != 0) {
                leftNodeValue_ = this.leftNode.executeDouble(frameValue);
            } else if ((state_0 & 0x68000) == 0 && (state_0 & Short.MAX_VALUE) != 0) {
                leftNodeValue_int = this.leftNode.executeInt(frameValue);
                leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            } else if ((state_0 & 0x38000) == 0 && (state_0 & Short.MAX_VALUE) != 0) {
                leftNodeValue_long = this.leftNode.executeLong(frameValue);
                leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
            } else {
                Object leftNodeValue__ = this.leftNode.execute(frameValue);
                leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x78000) >>> 15, leftNodeValue__);
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
            if ((state_0 & 0x700000) == 0 && (state_0 & Short.MAX_VALUE) != 0) {
                rightNodeValue_ = this.rightNode.executeDouble(frameValue);
            } else if ((state_0 & 0x680000) == 0 && (state_0 & Short.MAX_VALUE) != 0) {
                rightNodeValue_int = this.rightNode.executeInt(frameValue);
                rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((state_0 & 0x380000) == 0 && (state_0 & Short.MAX_VALUE) != 0) {
                rightNodeValue_long = this.rightNode.executeLong(frameValue);
                rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
                Object rightNodeValue__ = this.rightNode.execute(frameValue);
                rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x780000) >>> 19, rightNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize((state_0 & 0x68000) == 0 && (state_0 & Short.MAX_VALUE) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0x38000) == 0 && (state_0 & Short.MAX_VALUE) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), ex.getResult());
        }
        assert ((state_0 & 0x40) != 0);
        return JSAddNode.doDouble(leftNodeValue_, rightNodeValue_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object execute_int2(int state_0, VirtualFrame frameValue) {
        int leftNodeValue_;
        try {
            leftNodeValue_ = this.leftNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = this.rightNode.execute(frameValue);
            return this.executeAndSpecialize(ex.getResult(), rightNodeValue);
        }
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        if ((state_0 & 8) != 0 && rightNodeValue_ instanceof SafeInteger) {
            SafeInteger rightNodeValue__ = (SafeInteger)rightNodeValue_;
            try {
                return JSAddNode.doIntSafeInteger(leftNodeValue_, rightNodeValue__);
            }
            catch (ArithmeticException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Lock lock = this.getLock();
                lock.lock();
                try {
                    this.exclude_ |= 8;
                    this.state_0_ &= 0xFFFFFFF7;
                }
                finally {
                    lock.unlock();
                }
                return this.executeAndSpecialize(leftNodeValue_, rightNodeValue__);
            }
        }
        if ((state_0 & 0x400) != 0 && rightNodeValue_ instanceof TruffleString) {
            TruffleString rightNodeValue__ = (TruffleString)rightNodeValue_;
            return this.doIntString(leftNodeValue_, rightNodeValue__, this.concatStringsNode, this.stringFromLongNode);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object execute_int3(int state_0, VirtualFrame frameValue) {
        int rightNodeValue_;
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        try {
            rightNodeValue_ = this.rightNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(leftNodeValue_, ex.getResult());
        }
        if ((state_0 & 0x10) != 0 && leftNodeValue_ instanceof SafeInteger) {
            SafeInteger leftNodeValue__ = (SafeInteger)leftNodeValue_;
            try {
                return JSAddNode.doSafeIntegerInt(leftNodeValue__, rightNodeValue_);
            }
            catch (ArithmeticException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Lock lock = this.getLock();
                lock.lock();
                try {
                    this.exclude_ |= 0x10;
                    this.state_0_ &= 0xFFFFFFEF;
                }
                finally {
                    lock.unlock();
                }
                return this.executeAndSpecialize(leftNodeValue__, rightNodeValue_);
            }
        }
        if ((state_0 & 0x200) != 0 && leftNodeValue_ instanceof TruffleString) {
            TruffleString leftNodeValue__ = (TruffleString)leftNodeValue_;
            return this.doStringInt(leftNodeValue__, rightNodeValue_, this.concatStringsNode, this.stringFromLongNode);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object execute_generic4(int state_0, VirtualFrame frameValue) {
        int rightNodeValue__;
        int leftNodeValue__;
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        if ((state_0 & 0xF) != 0 && leftNodeValue_ instanceof Integer) {
            leftNodeValue__ = (Integer)leftNodeValue_;
            if ((state_0 & 7) != 0 && rightNodeValue_ instanceof Integer) {
                rightNodeValue__ = (Integer)rightNodeValue_;
                if ((state_0 & 1) != 0) {
                    assert (this.truncate);
                    return JSAddNode.doIntTruncate(leftNodeValue__, rightNodeValue__);
                }
                if ((state_0 & 2) != 0) {
                    assert (!this.truncate);
                    try {
                        return JSAddNode.doInt(leftNodeValue__, rightNodeValue__);
                    }
                    catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        Lock lock = this.getLock();
                        lock.lock();
                        try {
                            this.exclude_ |= 2;
                            this.state_0_ &= 0xFFFFFFFD;
                        }
                        finally {
                            lock.unlock();
                        }
                        return this.executeAndSpecialize(leftNodeValue__, rightNodeValue__);
                    }
                }
                if ((state_0 & 4) != 0) {
                    assert (!this.truncate);
                    try {
                        return JSAddNode.doIntOverflow(leftNodeValue__, rightNodeValue__);
                    }
                    catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        Lock lock = this.getLock();
                        lock.lock();
                        try {
                            this.exclude_ |= 4;
                            this.state_0_ &= 0xFFFFFFFB;
                        }
                        finally {
                            lock.unlock();
                        }
                        return this.executeAndSpecialize(leftNodeValue__, rightNodeValue__);
                    }
                }
            }
            if ((state_0 & 8) != 0 && rightNodeValue_ instanceof SafeInteger) {
                SafeInteger rightNodeValue__2 = (SafeInteger)rightNodeValue_;
                try {
                    return JSAddNode.doIntSafeInteger(leftNodeValue__, rightNodeValue__2);
                }
                catch (ArithmeticException ex) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    Lock lock = this.getLock();
                    lock.lock();
                    try {
                        this.exclude_ |= 8;
                        this.state_0_ &= 0xFFFFFFF7;
                    }
                    finally {
                        lock.unlock();
                    }
                    return this.executeAndSpecialize(leftNodeValue__, rightNodeValue__2);
                }
            }
        }
        if ((state_0 & 0x30) != 0 && leftNodeValue_ instanceof SafeInteger) {
            SafeInteger leftNodeValue__2 = (SafeInteger)leftNodeValue_;
            if ((state_0 & 0x10) != 0 && rightNodeValue_ instanceof Integer) {
                rightNodeValue__ = (Integer)rightNodeValue_;
                try {
                    return JSAddNode.doSafeIntegerInt(leftNodeValue__2, rightNodeValue__);
                }
                catch (ArithmeticException ex) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    Lock lock = this.getLock();
                    lock.lock();
                    try {
                        this.exclude_ |= 0x10;
                        this.state_0_ &= 0xFFFFFFEF;
                    }
                    finally {
                        lock.unlock();
                    }
                    return this.executeAndSpecialize(leftNodeValue__2, rightNodeValue__);
                }
            }
            if ((state_0 & 0x20) != 0 && rightNodeValue_ instanceof SafeInteger) {
                SafeInteger rightNodeValue__3 = (SafeInteger)rightNodeValue_;
                try {
                    return JSAddNode.doSafeInteger(leftNodeValue__2, rightNodeValue__3);
                }
                catch (ArithmeticException ex) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    Lock lock = this.getLock();
                    lock.lock();
                    try {
                        this.exclude_ |= 0x20;
                        this.state_0_ &= 0xFFFFFFDF;
                    }
                    finally {
                        lock.unlock();
                    }
                    return this.executeAndSpecialize(leftNodeValue__2, rightNodeValue__3);
                }
            }
        }
        if ((state_0 & 0x40) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x78000) >>> 15, leftNodeValue_)) {
            double leftNodeValue__3 = JSTypesGen.asImplicitDouble((state_0 & 0x78000) >>> 15, leftNodeValue_);
            if (JSTypesGen.isImplicitDouble((state_0 & 0x780000) >>> 19, rightNodeValue_)) {
                double rightNodeValue__4 = JSTypesGen.asImplicitDouble((state_0 & 0x780000) >>> 19, rightNodeValue_);
                return JSAddNode.doDouble(leftNodeValue__3, rightNodeValue__4);
            }
        }
        if ((state_0 & 0x80) != 0 && leftNodeValue_ instanceof BigInt) {
            BigInt leftNodeValue__4 = (BigInt)leftNodeValue_;
            if (rightNodeValue_ instanceof BigInt) {
                BigInt rightNodeValue__5 = (BigInt)rightNodeValue_;
                return this.doBigInt(leftNodeValue__4, rightNodeValue__5);
            }
        }
        if ((state_0 & 0x300) != 0 && leftNodeValue_ instanceof TruffleString) {
            TruffleString leftNodeValue__5 = (TruffleString)leftNodeValue_;
            if ((state_0 & 0x100) != 0 && rightNodeValue_ instanceof TruffleString) {
                TruffleString rightNodeValue__6 = (TruffleString)rightNodeValue_;
                return this.doString(leftNodeValue__5, rightNodeValue__6, this.concatStringsNode);
            }
            if ((state_0 & 0x200) != 0 && rightNodeValue_ instanceof Integer) {
                rightNodeValue__ = (Integer)rightNodeValue_;
                return this.doStringInt(leftNodeValue__5, rightNodeValue__, this.concatStringsNode, this.stringFromLongNode);
            }
        }
        if ((state_0 & 0x400) != 0 && leftNodeValue_ instanceof Integer) {
            leftNodeValue__ = (Integer)leftNodeValue_;
            if (rightNodeValue_ instanceof TruffleString) {
                TruffleString rightNodeValue__7 = (TruffleString)rightNodeValue_;
                return this.doIntString(leftNodeValue__, rightNodeValue__7, this.concatStringsNode, this.stringFromLongNode);
            }
        }
        if ((state_0 & 0x800) != 0 && leftNodeValue_ instanceof TruffleString) {
            TruffleString leftNodeValue__6 = (TruffleString)leftNodeValue_;
            if (JSGuards.isNumber(rightNodeValue_)) {
                return this.doStringNumber(leftNodeValue__6, rightNodeValue_, this.concatStringsNode, this.doubleToStringNode);
            }
        }
        if ((state_0 & 0x7000) != 0) {
            if ((state_0 & 0x1000) != 0 && rightNodeValue_ instanceof TruffleString) {
                TruffleString rightNodeValue__8 = (TruffleString)rightNodeValue_;
                if (JSGuards.isNumber(leftNodeValue_)) {
                    return this.doNumberString(leftNodeValue_, rightNodeValue__8, this.concatStringsNode, this.doubleToStringNode);
                }
            }
            if ((state_0 & 0x6000) != 0) {
                PrimitiveConversionData s14_;
                if ((state_0 & 0x2000) != 0 && (this.hasOverloadedOperators(leftNodeValue_) || this.hasOverloadedOperators(rightNodeValue_))) {
                    return this.doOverloaded(leftNodeValue_, rightNodeValue_, this.overloaded_overloadedOperatorNode_);
                }
                if ((state_0 & 0x4000) != 0 && (s14_ = this.primitiveConversion_cache) != null && !this.hasOverloadedOperators(leftNodeValue_) && !this.hasOverloadedOperators(rightNodeValue_)) {
                    return this.doPrimitiveConversion(leftNodeValue_, rightNodeValue_, s14_.toPrimitiveA_, s14_.toPrimitiveB_, s14_.toNumericA_, s14_.toNumericB_, s14_.toStringA_, s14_.toStringB_, s14_.profileA_, s14_.profileB_, s14_.add_, s14_.mixedNumericTypes_);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
    }

    @Override
    public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
        double rightNodeValue_;
        double leftNodeValue_;
        int state_0 = this.state_0_;
        if ((state_0 & 0x7804) != 0) {
            return JSTypesGen.expectDouble(this.execute(frameValue));
        }
        long leftNodeValue_long = 0L;
        int leftNodeValue_int = 0;
        try {
            if ((state_0 & 0x70000) == 0 && (state_0 & Short.MAX_VALUE) != 0) {
                leftNodeValue_ = this.leftNode.executeDouble(frameValue);
            } else if ((state_0 & 0x68000) == 0 && (state_0 & Short.MAX_VALUE) != 0) {
                leftNodeValue_int = this.leftNode.executeInt(frameValue);
                leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            } else if ((state_0 & 0x38000) == 0 && (state_0 & Short.MAX_VALUE) != 0) {
                leftNodeValue_long = this.leftNode.executeLong(frameValue);
                leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
            } else {
                Object leftNodeValue__ = this.leftNode.execute(frameValue);
                leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x78000) >>> 15, leftNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = this.rightNode.execute(frameValue);
            return JSTypesGen.expectDouble(this.executeAndSpecialize(ex.getResult(), rightNodeValue));
        }
        long rightNodeValue_long = 0L;
        int rightNodeValue_int = 0;
        try {
            if ((state_0 & 0x700000) == 0 && (state_0 & Short.MAX_VALUE) != 0) {
                rightNodeValue_ = this.rightNode.executeDouble(frameValue);
            } else if ((state_0 & 0x680000) == 0 && (state_0 & Short.MAX_VALUE) != 0) {
                rightNodeValue_int = this.rightNode.executeInt(frameValue);
                rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((state_0 & 0x380000) == 0 && (state_0 & Short.MAX_VALUE) != 0) {
                rightNodeValue_long = this.rightNode.executeLong(frameValue);
                rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
                Object rightNodeValue__ = this.rightNode.execute(frameValue);
                rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x780000) >>> 19, rightNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(this.executeAndSpecialize((state_0 & 0x68000) == 0 && (state_0 & Short.MAX_VALUE) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0x38000) == 0 && (state_0 & Short.MAX_VALUE) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), ex.getResult()));
        }
        if ((state_0 & 0x40) != 0) {
            return JSAddNode.doDouble(leftNodeValue_, rightNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectDouble(this.executeAndSpecialize((state_0 & 0x68000) == 0 && (state_0 & Short.MAX_VALUE) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0x38000) == 0 && (state_0 & Short.MAX_VALUE) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), (state_0 & 0x680000) == 0 && (state_0 & Short.MAX_VALUE) != 0 ? (Number)rightNodeValue_int : (Number)((state_0 & 0x380000) == 0 && (state_0 & Short.MAX_VALUE) != 0 ? (Number)rightNodeValue_long : (Number)rightNodeValue_)));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
        int rightNodeValue_;
        int leftNodeValue_;
        int state_0 = this.state_0_;
        if ((state_0 & 0x7804) != 0) {
            return JSTypesGen.expectInteger(this.execute(frameValue));
        }
        try {
            leftNodeValue_ = this.leftNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = this.rightNode.execute(frameValue);
            return JSTypesGen.expectInteger(this.executeAndSpecialize(ex.getResult(), rightNodeValue));
        }
        try {
            rightNodeValue_ = this.rightNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(leftNodeValue_, ex.getResult()));
        }
        if ((state_0 & 1) != 0) {
            assert (this.truncate);
            return JSAddNode.doIntTruncate(leftNodeValue_, rightNodeValue_);
        }
        if ((state_0 & 2) != 0) {
            assert (!this.truncate);
            try {
                return JSAddNode.doInt(leftNodeValue_, rightNodeValue_);
            }
            catch (ArithmeticException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Lock lock = this.getLock();
                lock.lock();
                try {
                    this.exclude_ |= 2;
                    this.state_0_ &= 0xFFFFFFFD;
                }
                finally {
                    lock.unlock();
                }
                return JSTypesGen.expectInteger(this.executeAndSpecialize(leftNodeValue_, rightNodeValue_));
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectInteger(this.executeAndSpecialize(leftNodeValue_, rightNodeValue_));
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        try {
            if ((state_0 & 0x7FFC) == 0 && (state_0 & Short.MAX_VALUE) != 0) {
                this.executeInt(frameValue);
                return;
            }
            if ((state_0 & 0x7FBF) == 0 && (state_0 & Short.MAX_VALUE) != 0) {
                this.executeDouble(frameValue);
                return;
            }
            this.execute(frameValue);
            return;
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return;
        }
    }

    /*
     * Exception decompiling
     */
    private Object executeAndSpecialize(Object leftNodeValue, Object rightNodeValue) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 5 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if ((state_0 & Short.MAX_VALUE) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & Short.MAX_VALUE & (state_0 & Short.MAX_VALUE) - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Cloneable>> cached;
        Object[] data = new Object[16];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doIntTruncate";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : ((exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[1] = s;
        s = new Object[3];
        s[0] = "doInt";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : ((exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[2] = s;
        s = new Object[3];
        s[0] = "doIntOverflow";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : ((exclude & 4) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[3] = s;
        s = new Object[3];
        s[0] = "doIntSafeInteger";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : ((exclude & 8) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[4] = s;
        s = new Object[3];
        s[0] = "doSafeIntegerInt";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : ((exclude & 0x10) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[5] = s;
        s = new Object[3];
        s[0] = "doSafeInteger";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : ((exclude & 0x20) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[6] = s;
        s = new Object[3];
        s[0] = "doDouble";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : ((exclude & 0x40) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[7] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : ((exclude & 0x80) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[8] = s;
        s = new Object[3];
        s[0] = "doString";
        if ((state_0 & 0x100) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Cloneable>>();
            cached.add(Arrays.asList(this.concatStringsNode));
            s[2] = cached;
        } else {
            s[1] = (exclude & 0x100) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[9] = s;
        s = new Object[3];
        s[0] = "doStringInt";
        if ((state_0 & 0x200) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.concatStringsNode, this.stringFromLongNode));
            s[2] = cached;
        } else {
            s[1] = (exclude & 0x200) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[10] = s;
        s = new Object[3];
        s[0] = "doIntString";
        if ((state_0 & 0x400) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.concatStringsNode, this.stringFromLongNode));
            s[2] = cached;
        } else {
            s[1] = (exclude & 0x400) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[11] = s;
        s = new Object[3];
        s[0] = "doStringNumber";
        if ((state_0 & 0x800) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.concatStringsNode, this.doubleToStringNode));
            s[2] = cached;
        } else {
            s[1] = (exclude & 0x800) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[12] = s;
        s = new Object[3];
        s[0] = "doNumberString";
        if ((state_0 & 0x1000) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.concatStringsNode, this.doubleToStringNode));
            s[2] = cached;
        } else {
            s[1] = (exclude & 0x1000) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[13] = s;
        s = new Object[3];
        s[0] = "doOverloaded";
        if ((state_0 & 0x2000) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.overloaded_overloadedOperatorNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[14] = s;
        s = new Object[3];
        s[0] = "doPrimitiveConversion";
        if ((state_0 & 0x4000) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            PrimitiveConversionData s14_ = this.primitiveConversion_cache;
            if (s14_ != null) {
                cached.add(Arrays.asList(s14_.toPrimitiveA_, s14_.toPrimitiveB_, s14_.toNumericA_, s14_.toNumericB_, s14_.toStringA_, s14_.toStringB_, s14_.profileA_, s14_.profileB_, s14_.add_, s14_.mixedNumericTypes_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[15] = s;
        return Introspection.Provider.create(data);
    }

    public static JSAddNode create(boolean truncate, JavaScriptNode left, JavaScriptNode right) {
        return new JSAddNodeGen(truncate, left, right);
    }

    @GeneratedBy(value=JSAddNode.class)
    private static final class PrimitiveConversionData
    extends Node {
        @Node.Child
        JSToPrimitiveNode toPrimitiveA_;
        @Node.Child
        JSToPrimitiveNode toPrimitiveB_;
        @Node.Child
        JSToNumericNode toNumericA_;
        @Node.Child
        JSToNumericNode toNumericB_;
        @Node.Child
        JSToStringNode toStringA_;
        @Node.Child
        JSToStringNode toStringB_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile profileA_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile profileB_;
        @Node.Child
        JSAddNode add_;
        @CompilerDirectives.CompilationFinal
        BranchProfile mixedNumericTypes_;

        PrimitiveConversionData() {
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

