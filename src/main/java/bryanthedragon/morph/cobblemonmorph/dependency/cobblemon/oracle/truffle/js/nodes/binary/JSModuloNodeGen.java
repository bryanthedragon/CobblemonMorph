
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
import com.oracle.truffle.js.nodes.binary.JSModuloNode;
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNode;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.runtime.BigInt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSModuloNode.class)
public final class JSModuloNodeGen
extends JSModuloNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private BranchProfile intPow2_negativeBranch_;
    @CompilerDirectives.CompilationFinal
    private BranchProfile intPow2_negativeZeroBranch_;
    @CompilerDirectives.CompilationFinal
    private BranchProfile int_specialBranch_;
    @Node.Child
    private JSOverloadedBinaryNode overloaded_overloadedOperatorNode_;
    @Node.Child
    private GenericData generic_cache;

    private JSModuloNodeGen(JavaScriptNode left, JavaScriptNode right) {
        super(left, right);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object execute(Object leftNodeValue, Object rightNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 3) != 0 && leftNodeValue instanceof Integer) {
            int leftNodeValue_ = (Integer)leftNodeValue;
            if (rightNodeValue instanceof Integer) {
                int rightNodeValue_ = (Integer)rightNodeValue;
                if ((state_0 & 1) != 0 && JSModuloNode.isPowOf2(rightNodeValue_)) {
                    try {
                        return this.doIntPow2(leftNodeValue_, rightNodeValue_, this.intPow2_negativeBranch_, this.intPow2_negativeZeroBranch_);
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
                if ((state_0 & 2) != 0 && !JSModuloNode.isPowOf2(rightNodeValue_)) {
                    try {
                        return this.doInt(leftNodeValue_, rightNodeValue_, this.int_specialBranch_);
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
            }
        }
        if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x780) >>> 7, leftNodeValue)) {
            double leftNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0x780) >>> 7, leftNodeValue);
            if (JSTypesGen.isImplicitDouble((state_0 & 0x7800) >>> 11, rightNodeValue)) {
                double rightNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0x7800) >>> 11, rightNodeValue);
                return this.doDouble(leftNodeValue_, rightNodeValue_);
            }
        }
        if ((state_0 & 0x10) != 0 && leftNodeValue instanceof BigInt) {
            BigInt rightNodeValue_;
            BigInt leftNodeValue_ = (BigInt)leftNodeValue;
            if (rightNodeValue instanceof BigInt && !JSGuards.isBigIntZero(rightNodeValue_ = (BigInt)rightNodeValue)) {
                return this.doBigInteger(leftNodeValue_, rightNodeValue_);
            }
        }
        if ((state_0 & 0x60) != 0) {
            GenericData s6_;
            if ((state_0 & 0x20) != 0 && (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue))) {
                return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_);
            }
            if ((state_0 & 0x40) != 0 && (s6_ = this.generic_cache) != null && !this.hasOverloadedOperators(leftNodeValue) && !this.hasOverloadedOperators(rightNodeValue)) {
                return this.doGeneric(leftNodeValue, rightNodeValue, s6_.nestedModuloNode_, s6_.toNumeric1Node_, s6_.toNumeric2Node_, s6_.mixedNumericTypes_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue, rightNodeValue);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0x74) == 0 && (state_0 & 0x77) != 0) {
            return this.execute_int_int0(state_0, frameValue);
        }
        if ((state_0 & 0x73) == 0 && (state_0 & 0x77) != 0) {
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
        if ((state_0 & 1) != 0 && JSModuloNode.isPowOf2(rightNodeValue_)) {
            try {
                return this.doIntPow2(leftNodeValue_, rightNodeValue_, this.intPow2_negativeBranch_, this.intPow2_negativeZeroBranch_);
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
        if ((state_0 & 2) != 0 && !JSModuloNode.isPowOf2(rightNodeValue_)) {
            try {
                return this.doInt(leftNodeValue_, rightNodeValue_, this.int_specialBranch_);
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
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
    }

    private Object execute_double_double1(int state_0, VirtualFrame frameValue) {
        double rightNodeValue_;
        double leftNodeValue_;
        long leftNodeValue_long = 0L;
        int leftNodeValue_int = 0;
        try {
            if ((state_0 & 0x700) == 0 && (state_0 & 0x7F) != 0) {
                leftNodeValue_ = this.leftNode.executeDouble(frameValue);
            } else if ((state_0 & 0x680) == 0 && (state_0 & 0x7F) != 0) {
                leftNodeValue_int = this.leftNode.executeInt(frameValue);
                leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            } else if ((state_0 & 0x380) == 0 && (state_0 & 0x7F) != 0) {
                leftNodeValue_long = this.leftNode.executeLong(frameValue);
                leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
            } else {
                Object leftNodeValue__ = this.leftNode.execute(frameValue);
                leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x780) >>> 7, leftNodeValue__);
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
            if ((state_0 & 0x7000) == 0 && (state_0 & 0x7F) != 0) {
                rightNodeValue_ = this.rightNode.executeDouble(frameValue);
            } else if ((state_0 & 0x6800) == 0 && (state_0 & 0x7F) != 0) {
                rightNodeValue_int = this.rightNode.executeInt(frameValue);
                rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((state_0 & 0x3800) == 0 && (state_0 & 0x7F) != 0) {
                rightNodeValue_long = this.rightNode.executeLong(frameValue);
                rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
                Object rightNodeValue__ = this.rightNode.execute(frameValue);
                rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x7800) >>> 11, rightNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize((state_0 & 0x680) == 0 && (state_0 & 0x7F) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0x380) == 0 && (state_0 & 0x7F) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), ex.getResult());
        }
        assert ((state_0 & 4) != 0);
        return this.doDouble(leftNodeValue_, rightNodeValue_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object execute_generic2(int state_0, VirtualFrame frameValue) {
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        if ((state_0 & 3) != 0 && leftNodeValue_ instanceof Integer) {
            int leftNodeValue__ = (Integer)leftNodeValue_;
            if (rightNodeValue_ instanceof Integer) {
                int rightNodeValue__ = (Integer)rightNodeValue_;
                if ((state_0 & 1) != 0 && JSModuloNode.isPowOf2(rightNodeValue__)) {
                    try {
                        return this.doIntPow2(leftNodeValue__, rightNodeValue__, this.intPow2_negativeBranch_, this.intPow2_negativeZeroBranch_);
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
                if ((state_0 & 2) != 0 && !JSModuloNode.isPowOf2(rightNodeValue__)) {
                    try {
                        return this.doInt(leftNodeValue__, rightNodeValue__, this.int_specialBranch_);
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
            }
        }
        if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x780) >>> 7, leftNodeValue_)) {
            double leftNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0x780) >>> 7, leftNodeValue_);
            if (JSTypesGen.isImplicitDouble((state_0 & 0x7800) >>> 11, rightNodeValue_)) {
                double rightNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0x7800) >>> 11, rightNodeValue_);
                return this.doDouble(leftNodeValue__, rightNodeValue__);
            }
        }
        if ((state_0 & 0x10) != 0 && leftNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__;
            BigInt leftNodeValue__ = (BigInt)leftNodeValue_;
            if (rightNodeValue_ instanceof BigInt && !JSGuards.isBigIntZero(rightNodeValue__ = (BigInt)rightNodeValue_)) {
                return this.doBigInteger(leftNodeValue__, rightNodeValue__);
            }
        }
        if ((state_0 & 0x60) != 0) {
            GenericData s6_;
            if ((state_0 & 0x20) != 0 && (this.hasOverloadedOperators(leftNodeValue_) || this.hasOverloadedOperators(rightNodeValue_))) {
                return this.doOverloaded(leftNodeValue_, rightNodeValue_, this.overloaded_overloadedOperatorNode_);
            }
            if ((state_0 & 0x40) != 0 && (s6_ = this.generic_cache) != null && !this.hasOverloadedOperators(leftNodeValue_) && !this.hasOverloadedOperators(rightNodeValue_)) {
                return this.doGeneric(leftNodeValue_, rightNodeValue_, s6_.nestedModuloNode_, s6_.toNumeric1Node_, s6_.toNumeric2Node_, s6_.mixedNumericTypes_);
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
        if ((state_0 & 0x60) != 0) {
            return JSTypesGen.expectDouble(this.execute(frameValue));
        }
        long leftNodeValue_long = 0L;
        int leftNodeValue_int = 0;
        try {
            if ((state_0 & 0x700) == 0 && (state_0 & 0x7F) != 0) {
                leftNodeValue_ = this.leftNode.executeDouble(frameValue);
            } else if ((state_0 & 0x680) == 0 && (state_0 & 0x7F) != 0) {
                leftNodeValue_int = this.leftNode.executeInt(frameValue);
                leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            } else if ((state_0 & 0x380) == 0 && (state_0 & 0x7F) != 0) {
                leftNodeValue_long = this.leftNode.executeLong(frameValue);
                leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
            } else {
                Object leftNodeValue__ = this.leftNode.execute(frameValue);
                leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x780) >>> 7, leftNodeValue__);
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
            if ((state_0 & 0x7000) == 0 && (state_0 & 0x7F) != 0) {
                rightNodeValue_ = this.rightNode.executeDouble(frameValue);
            } else if ((state_0 & 0x6800) == 0 && (state_0 & 0x7F) != 0) {
                rightNodeValue_int = this.rightNode.executeInt(frameValue);
                rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((state_0 & 0x3800) == 0 && (state_0 & 0x7F) != 0) {
                rightNodeValue_long = this.rightNode.executeLong(frameValue);
                rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
                Object rightNodeValue__ = this.rightNode.execute(frameValue);
                rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x7800) >>> 11, rightNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(this.executeAndSpecialize((state_0 & 0x680) == 0 && (state_0 & 0x7F) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0x380) == 0 && (state_0 & 0x7F) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), ex.getResult()));
        }
        if ((state_0 & 4) != 0) {
            return this.doDouble(leftNodeValue_, rightNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectDouble(this.executeAndSpecialize((state_0 & 0x680) == 0 && (state_0 & 0x7F) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0x380) == 0 && (state_0 & 0x7F) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), (state_0 & 0x6800) == 0 && (state_0 & 0x7F) != 0 ? (Number)rightNodeValue_int : (Number)((state_0 & 0x3800) == 0 && (state_0 & 0x7F) != 0 ? (Number)rightNodeValue_long : (Number)rightNodeValue_)));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
        int rightNodeValue_;
        int leftNodeValue_;
        int state_0 = this.state_0_;
        if ((state_0 & 0x60) != 0) {
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
        if ((state_0 & 1) != 0 && JSModuloNode.isPowOf2(rightNodeValue_)) {
            try {
                return this.doIntPow2(leftNodeValue_, rightNodeValue_, this.intPow2_negativeBranch_, this.intPow2_negativeZeroBranch_);
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
        if ((state_0 & 2) != 0 && !JSModuloNode.isPowOf2(rightNodeValue_)) {
            try {
                return this.doInt(leftNodeValue_, rightNodeValue_, this.int_specialBranch_);
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
            if ((state_0 & 0x7C) == 0 && (state_0 & 0x7F) != 0) {
                this.executeInt(frameValue);
                return;
            }
            if ((state_0 & 0x7B) == 0 && (state_0 & 0x7F) != 0) {
                this.executeDouble(frameValue);
                return;
            }
            if ((state_0 & 0x77) != 0) {
                this.execute(frameValue);
                return;
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return;
        }
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        if ((state_0 & 8) != 0 && leftNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__;
            BigInt leftNodeValue__ = (BigInt)leftNodeValue_;
            if (rightNodeValue_ instanceof BigInt && JSGuards.isBigIntZero(rightNodeValue__ = (BigInt)rightNodeValue_)) {
                this.doBigIntegerZeroDivision(leftNodeValue__, rightNodeValue__);
                return;
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
    }

    /*
     * Exception decompiling
     */
    private Object executeAndSpecialize(Object leftNodeValue, Object rightNodeValue) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
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
        if ((state_0 & 0x7F) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0x7F & (state_0 & 0x7F) - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Cloneable>> cached;
        Object[] data = new Object[8];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doIntPow2";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Cloneable>>();
            cached.add(Arrays.asList(this.intPow2_negativeBranch_, this.intPow2_negativeZeroBranch_));
            s[2] = cached;
        } else {
            s[1] = (exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doInt";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.int_specialBranch_));
            s[2] = cached;
        } else {
            s[1] = (exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[2] = s;
        s = new Object[3];
        s[0] = "doDouble";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : ((exclude & 4) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[3] = s;
        s = new Object[3];
        s[0] = "doBigIntegerZeroDivision";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : ((exclude & 8) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[4] = s;
        s = new Object[3];
        s[0] = "doBigInteger";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : ((exclude & 0x10) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[5] = s;
        s = new Object[3];
        s[0] = "doOverloaded";
        if ((state_0 & 0x20) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.overloaded_overloadedOperatorNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[6] = s;
        s = new Object[3];
        s[0] = "doGeneric";
        if ((state_0 & 0x40) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            GenericData s6_ = this.generic_cache;
            if (s6_ != null) {
                cached.add(Arrays.asList(s6_.nestedModuloNode_, s6_.toNumeric1Node_, s6_.toNumeric2Node_, s6_.mixedNumericTypes_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[7] = s;
        return Introspection.Provider.create(data);
    }

    public static JSModuloNode create(JavaScriptNode left, JavaScriptNode right) {
        return new JSModuloNodeGen(left, right);
    }

    @GeneratedBy(value=JSModuloNode.class)
    private static final class GenericData
    extends Node {
        @Node.Child
        JSModuloNode nestedModuloNode_;
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

