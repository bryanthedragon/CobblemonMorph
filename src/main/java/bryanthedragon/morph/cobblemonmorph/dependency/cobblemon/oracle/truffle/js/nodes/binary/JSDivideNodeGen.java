
package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.binary.JSDivideNode;
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNode;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.runtime.BigInt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSDivideNode.class)
public final class JSDivideNodeGen
extends JSDivideNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private JSOverloadedBinaryNode overloaded_overloadedOperatorNode_;
    @Node.Child
    private GenericData generic_cache;

    private JSDivideNodeGen(JavaScriptNode left, JavaScriptNode right) {
        super(left, right);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object execute(Object leftNodeValue, Object rightNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 7) != 0 && leftNodeValue instanceof Integer) {
            int leftNodeValue_ = (Integer)leftNodeValue;
            if (rightNodeValue instanceof Integer) {
                int rightNodeValue_ = (Integer)rightNodeValue;
                if ((state_0 & 1) != 0 && rightNodeValue_ > 0) {
                    try {
                        return this.doInt1(leftNodeValue_, rightNodeValue_);
                    }
                    catch (ArithmeticException ex) {
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
                        return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
                    }
                }
                if ((state_0 & 2) != 0 && leftNodeValue_ > 0) {
                    try {
                        return this.doInt2(leftNodeValue_, rightNodeValue_);
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
                if ((state_0 & 4) != 0 && JSDivideNode.isCornercase(leftNodeValue_, rightNodeValue_)) {
                    try {
                        return this.doInt3(leftNodeValue_, rightNodeValue_);
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
        }
        if ((state_0 & 8) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF00) >>> 8, leftNodeValue)) {
            double leftNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0xF00) >>> 8, leftNodeValue);
            if (JSTypesGen.isImplicitDouble((state_0 & 0xF000) >>> 12, rightNodeValue)) {
                double rightNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0xF000) >>> 12, rightNodeValue);
                return this.doDouble(leftNodeValue_, rightNodeValue_);
            }
        }
        if ((state_0 & 0x30) != 0 && leftNodeValue instanceof BigInt) {
            BigInt leftNodeValue_ = (BigInt)leftNodeValue;
            if (rightNodeValue instanceof BigInt) {
                BigInt rightNodeValue_ = (BigInt)rightNodeValue;
                if ((state_0 & 0x10) != 0 && JSGuards.isBigIntZero(rightNodeValue_)) {
                    return this.doBigIntZeroDivision(leftNodeValue_, rightNodeValue_);
                }
                if ((state_0 & 0x20) != 0 && !JSGuards.isBigIntZero(rightNodeValue_)) {
                    return this.doBigInt(leftNodeValue_, rightNodeValue_);
                }
            }
        }
        if ((state_0 & 0xC0) != 0) {
            GenericData s7_;
            if ((state_0 & 0x40) != 0 && (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue))) {
                return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_);
            }
            if ((state_0 & 0x80) != 0 && (s7_ = this.generic_cache) != null && !this.hasOverloadedOperators(leftNodeValue) && !this.hasOverloadedOperators(rightNodeValue)) {
                return this.doGeneric(leftNodeValue, rightNodeValue, s7_.nestedDivideNode_, s7_.toNumeric1Node_, s7_.toNumeric2Node_, s7_.mixedNumericTypes_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue, rightNodeValue);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0xF8) == 0 && (state_0 & 0xFF) != 0) {
            return this.execute_int_int0(state_0, frameValue);
        }
        if ((state_0 & 0xF7) == 0 && (state_0 & 0xFF) != 0) {
            return this.execute_double_double1(state_0, frameValue);
        }
        return this.execute_generic2(state_0, frameValue);
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
        if ((state_0 & 1) != 0 && rightNodeValue_ > 0) {
            try {
                return this.doInt1(leftNodeValue_, rightNodeValue_);
            }
            catch (ArithmeticException ex) {
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
                return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
            }
        }
        if ((state_0 & 2) != 0 && leftNodeValue_ > 0) {
            try {
                return this.doInt2(leftNodeValue_, rightNodeValue_);
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
        if ((state_0 & 4) != 0 && JSDivideNode.isCornercase(leftNodeValue_, rightNodeValue_)) {
            try {
                return this.doInt3(leftNodeValue_, rightNodeValue_);
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
            if ((state_0 & 0xE00) == 0 && (state_0 & 0xFF) != 0) {
                leftNodeValue_ = this.leftNode.executeDouble(frameValue);
            } else if ((state_0 & 0xD00) == 0 && (state_0 & 0xFF) != 0) {
                leftNodeValue_int = this.leftNode.executeInt(frameValue);
                leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            } else if ((state_0 & 0x700) == 0 && (state_0 & 0xFF) != 0) {
                leftNodeValue_long = this.leftNode.executeLong(frameValue);
                leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
            } else {
                Object leftNodeValue__ = this.leftNode.execute(frameValue);
                leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0xF00) >>> 8, leftNodeValue__);
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
            if ((state_0 & 0xE000) == 0 && (state_0 & 0xFF) != 0) {
                rightNodeValue_ = this.rightNode.executeDouble(frameValue);
            } else if ((state_0 & 0xD000) == 0 && (state_0 & 0xFF) != 0) {
                rightNodeValue_int = this.rightNode.executeInt(frameValue);
                rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((state_0 & 0x7000) == 0 && (state_0 & 0xFF) != 0) {
                rightNodeValue_long = this.rightNode.executeLong(frameValue);
                rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
                Object rightNodeValue__ = this.rightNode.execute(frameValue);
                rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0xF000) >>> 12, rightNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize((state_0 & 0xD00) == 0 && (state_0 & 0xFF) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0x700) == 0 && (state_0 & 0xFF) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), ex.getResult());
        }
        assert ((state_0 & 8) != 0);
        return this.doDouble(leftNodeValue_, rightNodeValue_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object execute_generic2(int state_0, VirtualFrame frameValue) {
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        if ((state_0 & 7) != 0 && leftNodeValue_ instanceof Integer) {
            int leftNodeValue__ = (Integer)leftNodeValue_;
            if (rightNodeValue_ instanceof Integer) {
                int rightNodeValue__ = (Integer)rightNodeValue_;
                if ((state_0 & 1) != 0 && rightNodeValue__ > 0) {
                    try {
                        return this.doInt1(leftNodeValue__, rightNodeValue__);
                    }
                    catch (ArithmeticException ex) {
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
                        return this.executeAndSpecialize(leftNodeValue__, rightNodeValue__);
                    }
                }
                if ((state_0 & 2) != 0 && leftNodeValue__ > 0) {
                    try {
                        return this.doInt2(leftNodeValue__, rightNodeValue__);
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
                if ((state_0 & 4) != 0 && JSDivideNode.isCornercase(leftNodeValue__, rightNodeValue__)) {
                    try {
                        return this.doInt3(leftNodeValue__, rightNodeValue__);
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
        }
        if ((state_0 & 8) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF00) >>> 8, leftNodeValue_)) {
            double leftNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0xF00) >>> 8, leftNodeValue_);
            if (JSTypesGen.isImplicitDouble((state_0 & 0xF000) >>> 12, rightNodeValue_)) {
                double rightNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0xF000) >>> 12, rightNodeValue_);
                return this.doDouble(leftNodeValue__, rightNodeValue__);
            }
        }
        if ((state_0 & 0x30) != 0 && leftNodeValue_ instanceof BigInt) {
            BigInt leftNodeValue__ = (BigInt)leftNodeValue_;
            if (rightNodeValue_ instanceof BigInt) {
                BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
                if ((state_0 & 0x10) != 0 && JSGuards.isBigIntZero(rightNodeValue__)) {
                    return this.doBigIntZeroDivision(leftNodeValue__, rightNodeValue__);
                }
                if ((state_0 & 0x20) != 0 && !JSGuards.isBigIntZero(rightNodeValue__)) {
                    return this.doBigInt(leftNodeValue__, rightNodeValue__);
                }
            }
        }
        if ((state_0 & 0xC0) != 0) {
            GenericData s7_;
            if ((state_0 & 0x40) != 0 && (this.hasOverloadedOperators(leftNodeValue_) || this.hasOverloadedOperators(rightNodeValue_))) {
                return this.doOverloaded(leftNodeValue_, rightNodeValue_, this.overloaded_overloadedOperatorNode_);
            }
            if ((state_0 & 0x80) != 0 && (s7_ = this.generic_cache) != null && !this.hasOverloadedOperators(leftNodeValue_) && !this.hasOverloadedOperators(rightNodeValue_)) {
                return this.doGeneric(leftNodeValue_, rightNodeValue_, s7_.nestedDivideNode_, s7_.toNumeric1Node_, s7_.toNumeric2Node_, s7_.mixedNumericTypes_);
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
        if ((state_0 & 0xC0) != 0) {
            return JSTypesGen.expectDouble(this.execute(frameValue));
        }
        long leftNodeValue_long = 0L;
        int leftNodeValue_int = 0;
        try {
            if ((state_0 & 0xE00) == 0 && (state_0 & 0xFF) != 0) {
                leftNodeValue_ = this.leftNode.executeDouble(frameValue);
            } else if ((state_0 & 0xD00) == 0 && (state_0 & 0xFF) != 0) {
                leftNodeValue_int = this.leftNode.executeInt(frameValue);
                leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            } else if ((state_0 & 0x700) == 0 && (state_0 & 0xFF) != 0) {
                leftNodeValue_long = this.leftNode.executeLong(frameValue);
                leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
            } else {
                Object leftNodeValue__ = this.leftNode.execute(frameValue);
                leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0xF00) >>> 8, leftNodeValue__);
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
            if ((state_0 & 0xE000) == 0 && (state_0 & 0xFF) != 0) {
                rightNodeValue_ = this.rightNode.executeDouble(frameValue);
            } else if ((state_0 & 0xD000) == 0 && (state_0 & 0xFF) != 0) {
                rightNodeValue_int = this.rightNode.executeInt(frameValue);
                rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((state_0 & 0x7000) == 0 && (state_0 & 0xFF) != 0) {
                rightNodeValue_long = this.rightNode.executeLong(frameValue);
                rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
                Object rightNodeValue__ = this.rightNode.execute(frameValue);
                rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0xF000) >>> 12, rightNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(this.executeAndSpecialize((state_0 & 0xD00) == 0 && (state_0 & 0xFF) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0x700) == 0 && (state_0 & 0xFF) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), ex.getResult()));
        }
        if ((state_0 & 8) != 0) {
            return this.doDouble(leftNodeValue_, rightNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectDouble(this.executeAndSpecialize((state_0 & 0xD00) == 0 && (state_0 & 0xFF) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0x700) == 0 && (state_0 & 0xFF) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), (state_0 & 0xD000) == 0 && (state_0 & 0xFF) != 0 ? (Number)rightNodeValue_int : (Number)((state_0 & 0x7000) == 0 && (state_0 & 0xFF) != 0 ? (Number)rightNodeValue_long : (Number)rightNodeValue_)));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
        int rightNodeValue_;
        int leftNodeValue_;
        int state_0 = this.state_0_;
        if ((state_0 & 0xC0) != 0) {
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
        if ((state_0 & 1) != 0 && rightNodeValue_ > 0) {
            try {
                return this.doInt1(leftNodeValue_, rightNodeValue_);
            }
            catch (ArithmeticException ex) {
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
                return JSTypesGen.expectInteger(this.executeAndSpecialize(leftNodeValue_, rightNodeValue_));
            }
        }
        if ((state_0 & 2) != 0 && leftNodeValue_ > 0) {
            try {
                return this.doInt2(leftNodeValue_, rightNodeValue_);
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
        if ((state_0 & 4) != 0 && JSDivideNode.isCornercase(leftNodeValue_, rightNodeValue_)) {
            try {
                return this.doInt3(leftNodeValue_, rightNodeValue_);
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
            if ((state_0 & 0xF8) == 0 && (state_0 & 0xFF) != 0) {
                this.executeInt(frameValue);
                return;
            }
            if ((state_0 & 0xF7) == 0 && (state_0 & 0xFF) != 0) {
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
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
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
        if ((state_0 & 0xFF) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0xFF & (state_0 & 0xFF) - 1) == 0) {
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
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doInt1";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : ((exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[1] = s;
        s = new Object[3];
        s[0] = "doInt2";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : ((exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[2] = s;
        s = new Object[3];
        s[0] = "doInt3";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : ((exclude & 4) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[3] = s;
        s = new Object[3];
        s[0] = "doDouble";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : ((exclude & 8) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[4] = s;
        s = new Object[3];
        s[0] = "doBigIntZeroDivision";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        s = new Object[3];
        s[0] = "doOverloaded";
        if ((state_0 & 0x40) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Cloneable>>();
            cached.add(Arrays.asList(this.overloaded_overloadedOperatorNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[7] = s;
        s = new Object[3];
        s[0] = "doGeneric";
        if ((state_0 & 0x80) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            GenericData s7_ = this.generic_cache;
            if (s7_ != null) {
                cached.add(Arrays.asList(s7_.nestedDivideNode_, s7_.toNumeric1Node_, s7_.toNumeric2Node_, s7_.mixedNumericTypes_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[8] = s;
        return Introspection.Provider.create(data);
    }

    public static JSDivideNode create(JavaScriptNode left, JavaScriptNode right) {
        return new JSDivideNodeGen(left, right);
    }

    @GeneratedBy(value=JSDivideNode.class)
    private static final class GenericData
    extends Node {
        @Node.Child
        JSDivideNode nestedDivideNode_;
        @Node.Child
        JSToNumericNode toNumeric1Node_;
        @Node.Child
        JSToNumericNode toNumeric2Node_;
        @CompilerDirectives.CompilationFinal
        BranchProfile mixedNumericTypes_;

        GenericData() {
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

