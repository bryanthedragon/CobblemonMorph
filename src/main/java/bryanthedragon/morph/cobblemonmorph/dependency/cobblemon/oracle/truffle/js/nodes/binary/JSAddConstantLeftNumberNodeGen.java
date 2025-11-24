
package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.binary.JSAddConstantLeftNumberNode;
import com.oracle.truffle.js.nodes.binary.JSConcatStringsNode;
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSAddConstantLeftNumberNode.class)
public final class JSAddConstantLeftNumberNodeGen
extends JSAddConstantLeftNumberNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private TruffleString numberString_leftString_;
    @Node.Child
    private JSConcatStringsNode numberString_createLazyString_;
    @Node.Child
    private JSOverloadedBinaryNode overloaded_overloadedOperatorNode_;
    @Node.Child
    private PrimitiveConversionData primitiveConversion_cache;

    private JSAddConstantLeftNumberNodeGen(Number leftValue, JavaScriptNode right, boolean truncate) {
        super(leftValue, right, truncate);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object execute(Object operandNodeValue) {
        PrimitiveConversionData s7_;
        int state_0 = this.state_0_;
        if ((state_0 & 7) != 0 && operandNodeValue instanceof Integer) {
            int operandNodeValue_ = (Integer)operandNodeValue;
            if ((state_0 & 1) != 0) {
                assert (this.truncate);
                assert (this.isInt);
                return this.doIntTruncate(operandNodeValue_);
            }
            if ((state_0 & 2) != 0) {
                assert (!this.truncate);
                assert (this.isInt);
                try {
                    return this.doInt(operandNodeValue_);
                }
                catch (ArithmeticException ex) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    Lock lock = this.getLock();
                    lock.lock();
                    try {
                        this.exclude_ |= 1;
                        this.state_0_ &= 0xFFFFFFFD;
                    }
                    finally {
                        lock.unlock();
                    }
                    return this.executeAndSpecialize(operandNodeValue_);
                }
            }
            if ((state_0 & 4) != 0) {
                assert (!this.truncate);
                assert (this.isSafeLong);
                try {
                    return this.doIntOverflow(operandNodeValue_);
                }
                catch (ArithmeticException ex) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    Lock lock = this.getLock();
                    lock.lock();
                    try {
                        this.exclude_ |= 2;
                        this.state_0_ &= 0xFFFFFFFB;
                    }
                    finally {
                        lock.unlock();
                    }
                    return this.executeAndSpecialize(operandNodeValue_);
                }
            }
        }
        if ((state_0 & 8) != 0 && operandNodeValue instanceof SafeInteger) {
            SafeInteger operandNodeValue_ = (SafeInteger)operandNodeValue;
            assert (this.isInt);
            try {
                return this.doSafeInteger(operandNodeValue_);
            }
            catch (ArithmeticException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Lock lock = this.getLock();
                lock.lock();
                try {
                    this.exclude_ |= 4;
                    this.state_0_ &= 0xFFFFFFF7;
                }
                finally {
                    lock.unlock();
                }
                return this.executeAndSpecialize(operandNodeValue_);
            }
        }
        if ((state_0 & 0x10) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF00) >>> 8, operandNodeValue)) {
            double operandNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0xF00) >>> 8, operandNodeValue);
            return this.doDouble(operandNodeValue_);
        }
        if ((state_0 & 0x20) != 0 && operandNodeValue instanceof TruffleString) {
            TruffleString operandNodeValue_ = (TruffleString)operandNodeValue;
            return this.doNumberString(operandNodeValue_, this.numberString_leftString_, this.numberString_createLazyString_);
        }
        if ((state_0 & 0x40) != 0 && operandNodeValue instanceof JSOverloadedOperatorsObject) {
            JSOverloadedOperatorsObject operandNodeValue_ = (JSOverloadedOperatorsObject)operandNodeValue;
            return this.doOverloaded(operandNodeValue_, this.overloaded_overloadedOperatorNode_);
        }
        if ((state_0 & 0x80) != 0 && (s7_ = this.primitiveConversion_cache) != null && !this.hasOverloadedOperators(operandNodeValue)) {
            return this.doPrimitiveConversion(operandNodeValue, s7_.toPrimitiveB_, s7_.toNumberB_, s7_.leftString_, s7_.createLazyString_, s7_.profileB_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
        PrimitiveConversionData s7_;
        int state_0 = this.state_0_;
        if ((state_0 & 7) != 0 && operandNodeValue instanceof Integer) {
            int operandNodeValue_ = (Integer)operandNodeValue;
            if ((state_0 & 1) != 0) {
                assert (this.truncate);
                assert (this.isInt);
                return this.doIntTruncate(operandNodeValue_);
            }
            if ((state_0 & 2) != 0) {
                assert (!this.truncate);
                assert (this.isInt);
                try {
                    return this.doInt(operandNodeValue_);
                }
                catch (ArithmeticException ex) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    Lock lock = this.getLock();
                    lock.lock();
                    try {
                        this.exclude_ |= 1;
                        this.state_0_ &= 0xFFFFFFFD;
                    }
                    finally {
                        lock.unlock();
                    }
                    return this.executeAndSpecialize(operandNodeValue_);
                }
            }
            if ((state_0 & 4) != 0) {
                assert (!this.truncate);
                assert (this.isSafeLong);
                try {
                    return this.doIntOverflow(operandNodeValue_);
                }
                catch (ArithmeticException ex) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    Lock lock = this.getLock();
                    lock.lock();
                    try {
                        this.exclude_ |= 2;
                        this.state_0_ &= 0xFFFFFFFB;
                    }
                    finally {
                        lock.unlock();
                    }
                    return this.executeAndSpecialize(operandNodeValue_);
                }
            }
        }
        if ((state_0 & 8) != 0 && operandNodeValue instanceof SafeInteger) {
            SafeInteger operandNodeValue_ = (SafeInteger)operandNodeValue;
            assert (this.isInt);
            try {
                return this.doSafeInteger(operandNodeValue_);
            }
            catch (ArithmeticException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Lock lock = this.getLock();
                lock.lock();
                try {
                    this.exclude_ |= 4;
                    this.state_0_ &= 0xFFFFFFF7;
                }
                finally {
                    lock.unlock();
                }
                return this.executeAndSpecialize(operandNodeValue_);
            }
        }
        if ((state_0 & 0x10) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF00) >>> 8, operandNodeValue)) {
            double operandNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0xF00) >>> 8, operandNodeValue);
            return this.doDouble(operandNodeValue_);
        }
        if ((state_0 & 0x20) != 0 && operandNodeValue instanceof TruffleString) {
            TruffleString operandNodeValue_ = (TruffleString)operandNodeValue;
            return this.doNumberString(operandNodeValue_, this.numberString_leftString_, this.numberString_createLazyString_);
        }
        if ((state_0 & 0x40) != 0 && operandNodeValue instanceof JSOverloadedOperatorsObject) {
            JSOverloadedOperatorsObject operandNodeValue_ = (JSOverloadedOperatorsObject)operandNodeValue;
            return this.doOverloaded(operandNodeValue_, this.overloaded_overloadedOperatorNode_);
        }
        if ((state_0 & 0x80) != 0 && (s7_ = this.primitiveConversion_cache) != null && !this.hasOverloadedOperators(operandNodeValue)) {
            return this.doPrimitiveConversion(operandNodeValue, s7_.toPrimitiveB_, s7_.toNumberB_, s7_.leftString_, s7_.createLazyString_, s7_.profileB_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0xF8) == 0 && (state_0 & 0xFF) != 0) {
            return this.execute_int0(state_0, frameValue);
        }
        if ((state_0 & 0xEF) == 0 && (state_0 & 0xFF) != 0) {
            return this.execute_double1(state_0, frameValue);
        }
        return this.execute_generic2(state_0, frameValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object execute_int0(int state_0, VirtualFrame frameValue) {
        int operandNodeValue_;
        try {
            operandNodeValue_ = this.operandNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(ex.getResult());
        }
        if ((state_0 & 1) != 0) {
            assert (this.truncate);
            assert (this.isInt);
            return this.doIntTruncate(operandNodeValue_);
        }
        if ((state_0 & 2) != 0) {
            assert (!this.truncate);
            assert (this.isInt);
            try {
                return this.doInt(operandNodeValue_);
            }
            catch (ArithmeticException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Lock lock = this.getLock();
                lock.lock();
                try {
                    this.exclude_ |= 1;
                    this.state_0_ &= 0xFFFFFFFD;
                }
                finally {
                    lock.unlock();
                }
                return this.executeAndSpecialize(operandNodeValue_);
            }
        }
        if ((state_0 & 4) != 0) {
            assert (!this.truncate);
            assert (this.isSafeLong);
            try {
                return this.doIntOverflow(operandNodeValue_);
            }
            catch (ArithmeticException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Lock lock = this.getLock();
                lock.lock();
                try {
                    this.exclude_ |= 2;
                    this.state_0_ &= 0xFFFFFFFB;
                }
                finally {
                    lock.unlock();
                }
                return this.executeAndSpecialize(operandNodeValue_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue_);
    }

    private Object execute_double1(int state_0, VirtualFrame frameValue) {
        double operandNodeValue_;
        long operandNodeValue_long = 0L;
        int operandNodeValue_int = 0;
        try {
            if ((state_0 & 0xE00) == 0 && (state_0 & 0xFF) != 0) {
                operandNodeValue_ = this.operandNode.executeDouble(frameValue);
            } else if ((state_0 & 0xD00) == 0 && (state_0 & 0xFF) != 0) {
                operandNodeValue_int = this.operandNode.executeInt(frameValue);
                operandNodeValue_ = JSTypes.intToDouble(operandNodeValue_int);
            } else if ((state_0 & 0x700) == 0 && (state_0 & 0xFF) != 0) {
                operandNodeValue_long = this.operandNode.executeLong(frameValue);
                operandNodeValue_ = JSTypes.longToDouble(operandNodeValue_long);
            } else {
                Object operandNodeValue__ = this.operandNode.execute(frameValue);
                operandNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0xF00) >>> 8, operandNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 0x10) != 0);
        return this.doDouble(operandNodeValue_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object execute_generic2(int state_0, VirtualFrame frameValue) {
        PrimitiveConversionData s7_;
        Object operandNodeValue_ = this.operandNode.execute(frameValue);
        if ((state_0 & 7) != 0 && operandNodeValue_ instanceof Integer) {
            int operandNodeValue__ = (Integer)operandNodeValue_;
            if ((state_0 & 1) != 0) {
                assert (this.truncate);
                assert (this.isInt);
                return this.doIntTruncate(operandNodeValue__);
            }
            if ((state_0 & 2) != 0) {
                assert (!this.truncate);
                assert (this.isInt);
                try {
                    return this.doInt(operandNodeValue__);
                }
                catch (ArithmeticException ex) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    Lock lock = this.getLock();
                    lock.lock();
                    try {
                        this.exclude_ |= 1;
                        this.state_0_ &= 0xFFFFFFFD;
                    }
                    finally {
                        lock.unlock();
                    }
                    return this.executeAndSpecialize(operandNodeValue__);
                }
            }
            if ((state_0 & 4) != 0) {
                assert (!this.truncate);
                assert (this.isSafeLong);
                try {
                    return this.doIntOverflow(operandNodeValue__);
                }
                catch (ArithmeticException ex) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    Lock lock = this.getLock();
                    lock.lock();
                    try {
                        this.exclude_ |= 2;
                        this.state_0_ &= 0xFFFFFFFB;
                    }
                    finally {
                        lock.unlock();
                    }
                    return this.executeAndSpecialize(operandNodeValue__);
                }
            }
        }
        if ((state_0 & 8) != 0 && operandNodeValue_ instanceof SafeInteger) {
            SafeInteger operandNodeValue__ = (SafeInteger)operandNodeValue_;
            assert (this.isInt);
            try {
                return this.doSafeInteger(operandNodeValue__);
            }
            catch (ArithmeticException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Lock lock = this.getLock();
                lock.lock();
                try {
                    this.exclude_ |= 4;
                    this.state_0_ &= 0xFFFFFFF7;
                }
                finally {
                    lock.unlock();
                }
                return this.executeAndSpecialize(operandNodeValue__);
            }
        }
        if ((state_0 & 0x10) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF00) >>> 8, operandNodeValue_)) {
            double operandNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0xF00) >>> 8, operandNodeValue_);
            return this.doDouble(operandNodeValue__);
        }
        if ((state_0 & 0x20) != 0 && operandNodeValue_ instanceof TruffleString) {
            TruffleString operandNodeValue__ = (TruffleString)operandNodeValue_;
            return this.doNumberString(operandNodeValue__, this.numberString_leftString_, this.numberString_createLazyString_);
        }
        if ((state_0 & 0x40) != 0 && operandNodeValue_ instanceof JSOverloadedOperatorsObject) {
            JSOverloadedOperatorsObject operandNodeValue__ = (JSOverloadedOperatorsObject)operandNodeValue_;
            return this.doOverloaded(operandNodeValue__, this.overloaded_overloadedOperatorNode_);
        }
        if ((state_0 & 0x80) != 0 && (s7_ = this.primitiveConversion_cache) != null && !this.hasOverloadedOperators(operandNodeValue_)) {
            return this.doPrimitiveConversion(operandNodeValue_, s7_.toPrimitiveB_, s7_.toNumberB_, s7_.leftString_, s7_.createLazyString_, s7_.profileB_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue_);
    }

    @Override
    public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
        double operandNodeValue_;
        int state_0 = this.state_0_;
        if ((state_0 & 0xE4) != 0) {
            return JSTypesGen.expectDouble(this.execute(frameValue));
        }
        long operandNodeValue_long = 0L;
        int operandNodeValue_int = 0;
        try {
            if ((state_0 & 0xE00) == 0 && (state_0 & 0xFF) != 0) {
                operandNodeValue_ = this.operandNode.executeDouble(frameValue);
            } else if ((state_0 & 0xD00) == 0 && (state_0 & 0xFF) != 0) {
                operandNodeValue_int = this.operandNode.executeInt(frameValue);
                operandNodeValue_ = JSTypes.intToDouble(operandNodeValue_int);
            } else if ((state_0 & 0x700) == 0 && (state_0 & 0xFF) != 0) {
                operandNodeValue_long = this.operandNode.executeLong(frameValue);
                operandNodeValue_ = JSTypes.longToDouble(operandNodeValue_long);
            } else {
                Object operandNodeValue__ = this.operandNode.execute(frameValue);
                operandNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0xF00) >>> 8, operandNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(this.executeAndSpecialize(ex.getResult()));
        }
        if ((state_0 & 0x10) != 0) {
            return this.doDouble(operandNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectDouble(this.executeAndSpecialize((state_0 & 0xD00) == 0 && (state_0 & 0xFF) != 0 ? (Number)operandNodeValue_int : (Number)((state_0 & 0x700) == 0 && (state_0 & 0xFF) != 0 ? (Number)operandNodeValue_long : (Number)operandNodeValue_)));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
        int operandNodeValue_;
        int state_0 = this.state_0_;
        if ((state_0 & 0xE4) != 0) {
            return JSTypesGen.expectInteger(this.execute(frameValue));
        }
        try {
            operandNodeValue_ = this.operandNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(ex.getResult()));
        }
        if ((state_0 & 1) != 0) {
            assert (this.truncate);
            assert (this.isInt);
            return this.doIntTruncate(operandNodeValue_);
        }
        if ((state_0 & 2) != 0) {
            assert (!this.truncate);
            assert (this.isInt);
            try {
                return this.doInt(operandNodeValue_);
            }
            catch (ArithmeticException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Lock lock = this.getLock();
                lock.lock();
                try {
                    this.exclude_ |= 1;
                    this.state_0_ &= 0xFFFFFFFD;
                }
                finally {
                    lock.unlock();
                }
                return JSTypesGen.expectInteger(this.executeAndSpecialize(operandNodeValue_));
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectInteger(this.executeAndSpecialize(operandNodeValue_));
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        try {
            if ((state_0 & 0xFC) == 0 && (state_0 & 0xFF) != 0) {
                this.executeInt(frameValue);
                return;
            }
            if ((state_0 & 0xEF) == 0 && (state_0 & 0xFF) != 0) {
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
    private Object executeAndSpecialize(Object operandNodeValue) {
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
        ArrayList<List<Object>> cached;
        Object[] data = new Object[9];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doIntTruncate";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doInt";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : ((exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[2] = s;
        s = new Object[3];
        s[0] = "doIntOverflow";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : ((exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[3] = s;
        s = new Object[3];
        s[0] = "doSafeInteger";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : ((exclude & 4) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[4] = s;
        s = new Object[3];
        s[0] = "doDouble";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : ((exclude & 8) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[5] = s;
        s = new Object[3];
        s[0] = "doNumberString";
        if ((state_0 & 0x20) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            cached.add(Arrays.asList(this.numberString_leftString_, this.numberString_createLazyString_));
            s[2] = cached;
        } else {
            s[1] = (exclude & 0x10) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[6] = s;
        s = new Object[3];
        s[0] = "doOverloaded";
        if ((state_0 & 0x40) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.overloaded_overloadedOperatorNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[7] = s;
        s = new Object[3];
        s[0] = "doPrimitiveConversion";
        if ((state_0 & 0x80) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            PrimitiveConversionData s7_ = this.primitiveConversion_cache;
            if (s7_ != null) {
                cached.add(Arrays.asList(s7_.toPrimitiveB_, s7_.toNumberB_, s7_.leftString_, s7_.createLazyString_, s7_.profileB_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[8] = s;
        return Introspection.Provider.create(data);
    }

    public static JSAddConstantLeftNumberNode create(Number leftValue, JavaScriptNode right, boolean truncate) {
        return new JSAddConstantLeftNumberNodeGen(leftValue, right, truncate);
    }

    @GeneratedBy(value=JSAddConstantLeftNumberNode.class)
    private static final class PrimitiveConversionData
    extends Node {
        @Node.Child
        JSToPrimitiveNode toPrimitiveB_;
        @Node.Child
        JSToNumberNode toNumberB_;
        @CompilerDirectives.CompilationFinal
        TruffleString leftString_;
        @Node.Child
        JSConcatStringsNode createLazyString_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile profileB_;

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

