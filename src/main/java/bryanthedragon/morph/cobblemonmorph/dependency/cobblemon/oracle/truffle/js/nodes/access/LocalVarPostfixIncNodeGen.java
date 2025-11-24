
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.access.LocalVarIncNode;
import com.oracle.truffle.js.nodes.access.LocalVarPostfixIncNode;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.nodes.unary.JSOverloadedUnaryNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=LocalVarPostfixIncNode.class)
final class LocalVarPostfixIncNodeGen
extends LocalVarPostfixIncNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private ObjectData object_cache;

    private LocalVarPostfixIncNodeGen(LocalVarIncNode.LocalVarOp op, int slot, Object identifier, boolean hasTemporalDeadZone, ScopeFrameNode scopeFrameNode) {
        super(op, slot, identifier, hasTemporalDeadZone, scopeFrameNode);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Frame scopeFrameNodeValue_ = this.scopeFrameNode.executeFrame(frameValue);
        if (state_0 != 0) {
            ObjectData s8_;
            if ((state_0 & 1) != 0 && scopeFrameNodeValue_.isBoolean(this.slot) && this.isIntegerKind(scopeFrameNodeValue_)) {
                return this.doBoolean(scopeFrameNodeValue_);
            }
            if ((state_0 & 2) != 0 && scopeFrameNodeValue_.isBoolean(this.slot) && this.isDoubleKind(scopeFrameNodeValue_)) {
                return this.doBooleanDouble(scopeFrameNodeValue_);
            }
            if ((state_0 & 4) != 0 && scopeFrameNodeValue_.isBoolean(this.slot)) {
                return this.doBooleanObject(scopeFrameNodeValue_);
            }
            if ((state_0 & 8) != 0 && scopeFrameNodeValue_.isInt(this.slot) && this.isIntegerKind(scopeFrameNodeValue_)) {
                try {
                    return this.doInt(scopeFrameNodeValue_);
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
                    return this.executeAndSpecialize(scopeFrameNodeValue_);
                }
            }
            if ((state_0 & 0x10) != 0 && scopeFrameNodeValue_.isInt(this.slot) && this.isDoubleKind(scopeFrameNodeValue_)) {
                return this.doIntDouble(scopeFrameNodeValue_);
            }
            if ((state_0 & 0x20) != 0 && scopeFrameNodeValue_.isInt(this.slot)) {
                return this.doIntObject(scopeFrameNodeValue_);
            }
            if ((state_0 & 0x40) != 0 && scopeFrameNodeValue_.isDouble(this.slot) && this.isDoubleKind(scopeFrameNodeValue_)) {
                return this.doDouble(scopeFrameNodeValue_);
            }
            if ((state_0 & 0x80) != 0 && scopeFrameNodeValue_.isDouble(this.slot)) {
                return this.doDoubleObject(scopeFrameNodeValue_);
            }
            if ((state_0 & 0x100) != 0 && (s8_ = this.object_cache) != null && scopeFrameNodeValue_.isObject(this.slot)) {
                return this.doObject(scopeFrameNodeValue_, s8_.isNumberProfile_, s8_.isIntegerProfile_, s8_.isBigIntProfile_, s8_.isBoundaryProfile_, s8_.overloadedOperatorNode_, s8_.toNumericOperand_);
            }
            if ((state_0 & 0x200) != 0 && scopeFrameNodeValue_.isLong(this.slot) && this.isLongKind(scopeFrameNodeValue_)) {
                try {
                    return this.doSafeInteger(scopeFrameNodeValue_);
                }
                catch (ArithmeticException ex) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    Lock lock = this.getLock();
                    lock.lock();
                    try {
                        this.exclude_ |= 0x20;
                        this.state_0_ &= 0xFFFFFDFF;
                    }
                    finally {
                        lock.unlock();
                    }
                    return this.executeAndSpecialize(scopeFrameNodeValue_);
                }
            }
            if ((state_0 & 0x400) != 0 && scopeFrameNodeValue_.isLong(this.slot) && this.isDoubleKind(scopeFrameNodeValue_)) {
                return this.doSafeIntegerToDouble(scopeFrameNodeValue_);
            }
            if ((state_0 & 0x800) != 0 && scopeFrameNodeValue_.isLong(this.slot)) {
                return this.doSafeIntegerObject(scopeFrameNodeValue_);
            }
            if ((state_0 & 0x1000) != 0 && this.isIllegal(scopeFrameNodeValue_)) {
                return this.doDead(scopeFrameNodeValue_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(scopeFrameNodeValue_);
    }

    @Override
    public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
        int state_0 = this.state_0_;
        if ((state_0 & 0x1100) != 0) {
            return JSTypesGen.expectDouble(this.execute(frameValue));
        }
        Frame scopeFrameNodeValue_ = this.scopeFrameNode.executeFrame(frameValue);
        if ((state_0 & 0xCC0) != 0) {
            if ((state_0 & 0x40) != 0 && scopeFrameNodeValue_.isDouble(this.slot) && this.isDoubleKind(scopeFrameNodeValue_)) {
                return this.doDouble(scopeFrameNodeValue_);
            }
            if ((state_0 & 0x80) != 0 && scopeFrameNodeValue_.isDouble(this.slot)) {
                return this.doDoubleObject(scopeFrameNodeValue_);
            }
            if ((state_0 & 0x400) != 0 && scopeFrameNodeValue_.isLong(this.slot) && this.isDoubleKind(scopeFrameNodeValue_)) {
                return this.doSafeIntegerToDouble(scopeFrameNodeValue_);
            }
            if ((state_0 & 0x800) != 0 && scopeFrameNodeValue_.isLong(this.slot)) {
                return this.doSafeIntegerObject(scopeFrameNodeValue_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectDouble(this.executeAndSpecialize(scopeFrameNodeValue_));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
        int state_0 = this.state_0_;
        if ((state_0 & 0x1100) != 0) {
            return JSTypesGen.expectInteger(this.execute(frameValue));
        }
        Frame scopeFrameNodeValue_ = this.scopeFrameNode.executeFrame(frameValue);
        if ((state_0 & 0x3F) != 0) {
            if ((state_0 & 1) != 0 && scopeFrameNodeValue_.isBoolean(this.slot) && this.isIntegerKind(scopeFrameNodeValue_)) {
                return this.doBoolean(scopeFrameNodeValue_);
            }
            if ((state_0 & 2) != 0 && scopeFrameNodeValue_.isBoolean(this.slot) && this.isDoubleKind(scopeFrameNodeValue_)) {
                return this.doBooleanDouble(scopeFrameNodeValue_);
            }
            if ((state_0 & 4) != 0 && scopeFrameNodeValue_.isBoolean(this.slot)) {
                return this.doBooleanObject(scopeFrameNodeValue_);
            }
            if ((state_0 & 8) != 0 && scopeFrameNodeValue_.isInt(this.slot) && this.isIntegerKind(scopeFrameNodeValue_)) {
                try {
                    return this.doInt(scopeFrameNodeValue_);
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
                    return JSTypesGen.expectInteger(this.executeAndSpecialize(scopeFrameNodeValue_));
                }
            }
            if ((state_0 & 0x10) != 0 && scopeFrameNodeValue_.isInt(this.slot) && this.isDoubleKind(scopeFrameNodeValue_)) {
                return this.doIntDouble(scopeFrameNodeValue_);
            }
            if ((state_0 & 0x20) != 0 && scopeFrameNodeValue_.isInt(this.slot)) {
                return this.doIntObject(scopeFrameNodeValue_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectInteger(this.executeAndSpecialize(scopeFrameNodeValue_));
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        try {
            if ((state_0 & 0x1FC0) == 0 && state_0 != 0) {
                this.executeInt(frameValue);
                return;
            }
            if ((state_0 & 0x133F) == 0 && state_0 != 0) {
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
    private Object executeAndSpecialize(Frame scopeFrameNodeValue) {
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
        Object[] data = new Object[14];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doBoolean";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : ((exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[1] = s;
        s = new Object[3];
        s[0] = "doBooleanDouble";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : ((exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[2] = s;
        s = new Object[3];
        s[0] = "doBooleanObject";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doInt";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : ((exclude & 4) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[4] = s;
        s = new Object[3];
        s[0] = "doIntDouble";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : ((exclude & 8) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[5] = s;
        s = new Object[3];
        s[0] = "doIntObject";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        s = new Object[3];
        s[0] = "doDouble";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : ((exclude & 0x10) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[7] = s;
        s = new Object[3];
        s[0] = "doDoubleObject";
        s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[8] = s;
        s = new Object[3];
        s[0] = "doObject";
        if ((state_0 & 0x100) != 0) {
            s[1] = (byte)1;
            ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
            ObjectData s8_ = this.object_cache;
            if (s8_ != null) {
                cached.add(Arrays.asList(s8_.isNumberProfile_, s8_.isIntegerProfile_, s8_.isBigIntProfile_, s8_.isBoundaryProfile_, s8_.overloadedOperatorNode_, s8_.toNumericOperand_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[9] = s;
        s = new Object[3];
        s[0] = "doSafeInteger";
        s[1] = (state_0 & 0x200) != 0 ? Byte.valueOf((byte)1) : ((exclude & 0x20) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[10] = s;
        s = new Object[3];
        s[0] = "doSafeIntegerToDouble";
        s[1] = (state_0 & 0x400) != 0 ? Byte.valueOf((byte)1) : ((exclude & 0x40) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[11] = s;
        s = new Object[3];
        s[0] = "doSafeIntegerObject";
        s[1] = (state_0 & 0x800) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[12] = s;
        s = new Object[3];
        s[0] = "doDead";
        s[1] = (state_0 & 0x1000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[13] = s;
        return Introspection.Provider.create(data);
    }

    public static LocalVarPostfixIncNode create(LocalVarIncNode.LocalVarOp op, int slot, Object identifier, boolean hasTemporalDeadZone, ScopeFrameNode scopeFrameNode) {
        return new LocalVarPostfixIncNodeGen(op, slot, identifier, hasTemporalDeadZone, scopeFrameNode);
    }

    @GeneratedBy(value=LocalVarPostfixIncNode.class)
    private static final class ObjectData
    extends Node {
        @CompilerDirectives.CompilationFinal
        ConditionProfile isNumberProfile_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile isIntegerProfile_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile isBigIntProfile_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile isBoundaryProfile_;
        @Node.Child
        JSOverloadedUnaryNode overloadedOperatorNode_;
        @Node.Child
        JSToNumericNode toNumericOperand_;

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

