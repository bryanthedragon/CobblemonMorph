
package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.SlowPathException;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.builtins.math.PowNode;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=PowNode.class)
public final class PowNodeGen
extends PowNode
implements Introspection.Provider {
    @Node.Child
    private JavaScriptNode arguments0_;
    @Node.Child
    private JavaScriptNode arguments1_;
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile pow3_branch1_;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile pow3_branch2_;
    @Node.Child
    private PowNode pow1_powNode_;

    private PowNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
        super(context, builtin);
        this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
    }

    @Override
    public JavaScriptNode[] getArguments() {
        return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 8) == 0 && (state_0 & 0xF) != 0) {
            return this.execute_double_double0(state_0, frameValue);
        }
        return this.execute_generic1(state_0, frameValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object execute_double_double0(int state_0, VirtualFrame frameValue) {
        double arguments1Value_;
        double arguments0Value_;
        long arguments0Value_long = 0L;
        int arguments0Value_int = 0;
        try {
            if ((state_0 & 0xE0) == 0 && (state_0 & 0xF) != 0) {
                arguments0Value_ = this.arguments0_.executeDouble(frameValue);
            } else if ((state_0 & 0xD0) == 0 && (state_0 & 0xF) != 0) {
                arguments0Value_int = this.arguments0_.executeInt(frameValue);
                arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
            } else if ((state_0 & 0x70) == 0 && (state_0 & 0xF) != 0) {
                arguments0Value_long = this.arguments0_.executeLong(frameValue);
                arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
            } else {
                Object arguments0Value__ = this.arguments0_.execute(frameValue);
                arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments1Value = this.arguments1_.execute(frameValue);
            return this.executeAndSpecialize(ex.getResult(), arguments1Value);
        }
        long arguments1Value_long = 0L;
        int arguments1Value_int = 0;
        try {
            if ((state_0 & 0xE00) == 0 && (state_0 & 0xF) != 0) {
                arguments1Value_ = this.arguments1_.executeDouble(frameValue);
            } else if ((state_0 & 0xD00) == 0 && (state_0 & 0xF) != 0) {
                arguments1Value_int = this.arguments1_.executeInt(frameValue);
                arguments1Value_ = JSTypes.intToDouble(arguments1Value_int);
            } else if ((state_0 & 0x700) == 0 && (state_0 & 0xF) != 0) {
                arguments1Value_long = this.arguments1_.executeLong(frameValue);
                arguments1Value_ = JSTypes.longToDouble(arguments1Value_long);
            } else {
                Object arguments1Value__ = this.arguments1_.execute(frameValue);
                arguments1Value_ = JSTypesGen.expectImplicitDouble((state_0 & 0xF00) >>> 8, arguments1Value__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize((state_0 & 0xD0) == 0 && (state_0 & 0xF) != 0 ? (Number)arguments0Value_int : (Number)((state_0 & 0x70) == 0 && (state_0 & 0xF) != 0 ? (Number)arguments0Value_long : (Number)arguments0Value_), ex.getResult());
        }
        if ((state_0 & 1) != 0) {
            try {
                return this.pow(arguments0Value_, arguments1Value_);
            }
            catch (SlowPathException ex) {
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
                return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
            }
        }
        if ((state_0 & 2) != 0) {
            try {
                return this.pow2(arguments0Value_, arguments1Value_);
            }
            catch (SlowPathException ex) {
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
                return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
            }
        }
        if ((state_0 & 4) != 0) {
            return this.pow3(arguments0Value_, arguments1Value_, this.pow3_branch1_, this.pow3_branch2_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize((state_0 & 0xD0) == 0 && (state_0 & 0xF) != 0 ? (Number)arguments0Value_int : (Number)((state_0 & 0x70) == 0 && (state_0 & 0xF) != 0 ? (Number)arguments0Value_long : (Number)arguments0Value_), (state_0 & 0xD00) == 0 && (state_0 & 0xF) != 0 ? (Number)arguments1Value_int : (Number)((state_0 & 0x700) == 0 && (state_0 & 0xF) != 0 ? (Number)arguments1Value_long : (Number)arguments1Value_));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object execute_generic1(int state_0, VirtualFrame frameValue) {
        Object arguments0Value_ = this.arguments0_.execute(frameValue);
        Object arguments1Value_ = this.arguments1_.execute(frameValue);
        if ((state_0 & 7) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value_)) {
            double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value_);
            if (JSTypesGen.isImplicitDouble((state_0 & 0xF00) >>> 8, arguments1Value_)) {
                double arguments1Value__ = JSTypesGen.asImplicitDouble((state_0 & 0xF00) >>> 8, arguments1Value_);
                if ((state_0 & 1) != 0) {
                    try {
                        return this.pow(arguments0Value__, arguments1Value__);
                    }
                    catch (SlowPathException ex) {
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
                        return this.executeAndSpecialize(arguments0Value__, arguments1Value__);
                    }
                }
                if ((state_0 & 2) != 0) {
                    try {
                        return this.pow2(arguments0Value__, arguments1Value__);
                    }
                    catch (SlowPathException ex) {
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
                        return this.executeAndSpecialize(arguments0Value__, arguments1Value__);
                    }
                }
                if ((state_0 & 4) != 0) {
                    return this.pow3(arguments0Value__, arguments1Value__, this.pow3_branch1_, this.pow3_branch2_);
                }
            }
        }
        if ((state_0 & 8) != 0) {
            return this.pow(arguments0Value_, arguments1Value_, this.pow1_powNode_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
        double arguments1Value_;
        double arguments0Value_;
        int state_0 = this.state_0_;
        if ((state_0 & 8) != 0) {
            return JSTypesGen.expectDouble(this.execute(frameValue));
        }
        long arguments0Value_long = 0L;
        int arguments0Value_int = 0;
        try {
            if ((state_0 & 0xE0) == 0 && (state_0 & 0xF) != 0) {
                arguments0Value_ = this.arguments0_.executeDouble(frameValue);
            } else if ((state_0 & 0xD0) == 0 && (state_0 & 0xF) != 0) {
                arguments0Value_int = this.arguments0_.executeInt(frameValue);
                arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
            } else if ((state_0 & 0x70) == 0 && (state_0 & 0xF) != 0) {
                arguments0Value_long = this.arguments0_.executeLong(frameValue);
                arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
            } else {
                Object arguments0Value__ = this.arguments0_.execute(frameValue);
                arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments1Value = this.arguments1_.execute(frameValue);
            return JSTypesGen.expectDouble(this.executeAndSpecialize(ex.getResult(), arguments1Value));
        }
        long arguments1Value_long = 0L;
        int arguments1Value_int = 0;
        try {
            if ((state_0 & 0xE00) == 0 && (state_0 & 0xF) != 0) {
                arguments1Value_ = this.arguments1_.executeDouble(frameValue);
            } else if ((state_0 & 0xD00) == 0 && (state_0 & 0xF) != 0) {
                arguments1Value_int = this.arguments1_.executeInt(frameValue);
                arguments1Value_ = JSTypes.intToDouble(arguments1Value_int);
            } else if ((state_0 & 0x700) == 0 && (state_0 & 0xF) != 0) {
                arguments1Value_long = this.arguments1_.executeLong(frameValue);
                arguments1Value_ = JSTypes.longToDouble(arguments1Value_long);
            } else {
                Object arguments1Value__ = this.arguments1_.execute(frameValue);
                arguments1Value_ = JSTypesGen.expectImplicitDouble((state_0 & 0xF00) >>> 8, arguments1Value__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(this.executeAndSpecialize((state_0 & 0xD0) == 0 && (state_0 & 0xF) != 0 ? (Number)arguments0Value_int : (Number)((state_0 & 0x70) == 0 && (state_0 & 0xF) != 0 ? (Number)arguments0Value_long : (Number)arguments0Value_), ex.getResult()));
        }
        if ((state_0 & 1) != 0) {
            try {
                return this.pow(arguments0Value_, arguments1Value_);
            }
            catch (SlowPathException ex) {
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
                return JSTypesGen.expectDouble(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
            }
        }
        if ((state_0 & 2) != 0) {
            try {
                return this.pow2(arguments0Value_, arguments1Value_);
            }
            catch (SlowPathException ex) {
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
                return JSTypesGen.expectDouble(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
            }
        }
        if ((state_0 & 4) != 0) {
            return this.pow3(arguments0Value_, arguments1Value_, this.pow3_branch1_, this.pow3_branch2_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectDouble(this.executeAndSpecialize((state_0 & 0xD0) == 0 && (state_0 & 0xF) != 0 ? (Number)arguments0Value_int : (Number)((state_0 & 0x70) == 0 && (state_0 & 0xF) != 0 ? (Number)arguments0Value_long : (Number)arguments0Value_), (state_0 & 0xD00) == 0 && (state_0 & 0xF) != 0 ? (Number)arguments1Value_int : (Number)((state_0 & 0x700) == 0 && (state_0 & 0xF) != 0 ? (Number)arguments1Value_long : (Number)arguments1Value_)));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public double execute(Object arguments0Value, Object arguments1Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 7) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value)) {
            double arguments0Value_ = JSTypesGen.asImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value);
            if (JSTypesGen.isImplicitDouble((state_0 & 0xF00) >>> 8, arguments1Value)) {
                double arguments1Value_ = JSTypesGen.asImplicitDouble((state_0 & 0xF00) >>> 8, arguments1Value);
                if ((state_0 & 1) != 0) {
                    try {
                        return this.pow(arguments0Value_, arguments1Value_);
                    }
                    catch (SlowPathException ex) {
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
                        return (Double)this.executeAndSpecialize(arguments0Value_, arguments1Value_);
                    }
                }
                if ((state_0 & 2) != 0) {
                    try {
                        return this.pow2(arguments0Value_, arguments1Value_);
                    }
                    catch (SlowPathException ex) {
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
                        return (Double)this.executeAndSpecialize(arguments0Value_, arguments1Value_);
                    }
                }
                if ((state_0 & 4) != 0) {
                    return this.pow3(arguments0Value_, arguments1Value_, this.pow3_branch1_, this.pow3_branch2_);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return (Double)this.executeAndSpecialize(arguments0Value, arguments1Value);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        try {
            if ((state_0 & 8) == 0 && (state_0 & 0xF) != 0) {
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
    private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
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
        if ((state_0 & 0xF) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0xF & (state_0 & 0xF) - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Cloneable>> cached;
        Object[] data = new Object[5];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "pow";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : ((exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[1] = s;
        s = new Object[3];
        s[0] = "pow2";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : ((exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[2] = s;
        s = new Object[3];
        s[0] = "pow3";
        if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Cloneable>>();
            cached.add(Arrays.asList(this.pow3_branch1_, this.pow3_branch2_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[3] = s;
        s = new Object[3];
        s[0] = "pow";
        if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.pow1_powNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[4] = s;
        return Introspection.Provider.create(data);
    }

    public static PowNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
        return new PowNodeGen(context, builtin, arguments);
    }
}

