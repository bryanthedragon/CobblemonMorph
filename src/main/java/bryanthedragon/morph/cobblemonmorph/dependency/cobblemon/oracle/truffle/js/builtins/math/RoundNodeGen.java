
package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.builtins.math.RoundNode;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=RoundNode.class)
public final class RoundNodeGen
extends RoundNode
implements Introspection.Provider {
    @Node.Child
    private JavaScriptNode arguments0_;
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile roundDouble_profileA_;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile roundDouble_profileB_;

    private RoundNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
        super(context, builtin);
        this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
    }

    @Override
    public JavaScriptNode[] getArguments() {
        return new JavaScriptNode[]{this.arguments0_};
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0xE) == 0 && (state_0 & 0xF) != 0) {
            return this.execute_int0(state_0, frameValue);
        }
        if ((state_0 & 1) == 0 && (state_0 & 0xF) != 0) {
            return this.execute_double1(state_0, frameValue);
        }
        return this.execute_generic2(state_0, frameValue);
    }

    private Object execute_int0(int state_0, VirtualFrame frameValue) {
        int arguments0Value_;
        try {
            arguments0Value_ = this.arguments0_.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 1) != 0);
        return RoundNode.roundInt(arguments0Value_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object execute_double1(int state_0, VirtualFrame frameValue) {
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
            return this.executeAndSpecialize(ex.getResult());
        }
        if ((state_0 & 2) != 0 && RoundNode.isCornercase(arguments0Value_)) {
            return RoundNode.roundCornercase(arguments0Value_);
        }
        if ((state_0 & 4) != 0 && !RoundNode.isCornercase(arguments0Value_) && JSGuards.isDoubleInInt32Range(arguments0Value_)) {
            try {
                return this.roundDoubleInt(arguments0Value_);
            }
            catch (ArithmeticException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Lock lock = this.getLock();
                lock.lock();
                try {
                    this.exclude_ |= 1;
                    this.state_0_ &= 0xFFFFFFFB;
                }
                finally {
                    lock.unlock();
                }
                return this.executeAndSpecialize(arguments0Value_);
            }
        }
        if ((state_0 & 8) != 0 && !RoundNode.isCornercase(arguments0Value_)) {
            return this.roundDouble(arguments0Value_, this.roundDouble_profileA_, this.roundDouble_profileB_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize((state_0 & 0xD0) == 0 && (state_0 & 0xF) != 0 ? (Number)arguments0Value_int : (Number)((state_0 & 0x70) == 0 && (state_0 & 0xF) != 0 ? (Number)arguments0Value_long : (Number)arguments0Value_));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object execute_generic2(int state_0, VirtualFrame frameValue) {
        Object arguments0Value_ = this.arguments0_.execute(frameValue);
        if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
            int arguments0Value__ = (Integer)arguments0Value_;
            return RoundNode.roundInt(arguments0Value__);
        }
        if ((state_0 & 0xE) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value_)) {
            double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value_);
            if ((state_0 & 2) != 0 && RoundNode.isCornercase(arguments0Value__)) {
                return RoundNode.roundCornercase(arguments0Value__);
            }
            if ((state_0 & 4) != 0 && !RoundNode.isCornercase(arguments0Value__) && JSGuards.isDoubleInInt32Range(arguments0Value__)) {
                try {
                    return this.roundDoubleInt(arguments0Value__);
                }
                catch (ArithmeticException ex) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    Lock lock = this.getLock();
                    lock.lock();
                    try {
                        this.exclude_ |= 1;
                        this.state_0_ &= 0xFFFFFFFB;
                    }
                    finally {
                        lock.unlock();
                    }
                    return this.executeAndSpecialize(arguments0Value__);
                }
            }
            if ((state_0 & 8) != 0 && !RoundNode.isCornercase(arguments0Value__)) {
                return this.roundDouble(arguments0Value__, this.roundDouble_profileA_, this.roundDouble_profileB_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arguments0Value_);
    }

    @Override
    public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
        double arguments0Value_;
        int state_0 = this.state_0_;
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
            return JSTypesGen.expectDouble(this.executeAndSpecialize(ex.getResult()));
        }
        if ((state_0 & 2) != 0 && RoundNode.isCornercase(arguments0Value_)) {
            return RoundNode.roundCornercase(arguments0Value_);
        }
        if ((state_0 & 8) != 0 && !RoundNode.isCornercase(arguments0Value_)) {
            return this.roundDouble(arguments0Value_, this.roundDouble_profileA_, this.roundDouble_profileB_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectDouble(this.executeAndSpecialize((state_0 & 0xD0) == 0 && (state_0 & 0xF) != 0 ? (Number)arguments0Value_int : (Number)((state_0 & 0x70) == 0 && (state_0 & 0xF) != 0 ? (Number)arguments0Value_long : (Number)arguments0Value_)));
    }

    @Override
    public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
        int state_0 = this.state_0_;
        if ((state_0 & 4) == 0 && (state_0 & 5) != 0) {
            return this.executeInt_int3(state_0, frameValue);
        }
        if ((state_0 & 1) == 0 && (state_0 & 5) != 0) {
            return this.executeInt_double4(state_0, frameValue);
        }
        return this.executeInt_generic5(state_0, frameValue);
    }

    private int executeInt_int3(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
        int arguments0Value_;
        try {
            arguments0Value_ = this.arguments0_.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(ex.getResult()));
        }
        assert ((state_0 & 1) != 0);
        return RoundNode.roundInt(arguments0Value_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int executeInt_double4(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
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
            return JSTypesGen.expectInteger(this.executeAndSpecialize(ex.getResult()));
        }
        assert ((state_0 & 4) != 0);
        if (!RoundNode.isCornercase(arguments0Value_) && JSGuards.isDoubleInInt32Range(arguments0Value_)) {
            try {
                return this.roundDoubleInt(arguments0Value_);
            }
            catch (ArithmeticException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Lock lock = this.getLock();
                lock.lock();
                try {
                    this.exclude_ |= 1;
                    this.state_0_ &= 0xFFFFFFFB;
                }
                finally {
                    lock.unlock();
                }
                return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_));
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectInteger(this.executeAndSpecialize((state_0 & 0xD0) == 0 && (state_0 & 0xF) != 0 ? (Number)arguments0Value_int : (Number)((state_0 & 0x70) == 0 && (state_0 & 0xF) != 0 ? (Number)arguments0Value_long : (Number)arguments0Value_)));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int executeInt_generic5(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
        double arguments0Value__;
        Object arguments0Value_ = this.arguments0_.execute(frameValue);
        if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
            int arguments0Value__2 = (Integer)arguments0Value_;
            return RoundNode.roundInt(arguments0Value__2);
        }
        if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value_) && !RoundNode.isCornercase(arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value_)) && JSGuards.isDoubleInInt32Range(arguments0Value__)) {
            try {
                return this.roundDoubleInt(arguments0Value__);
            }
            catch (ArithmeticException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Lock lock = this.getLock();
                lock.lock();
                try {
                    this.exclude_ |= 1;
                    this.state_0_ &= 0xFFFFFFFB;
                }
                finally {
                    lock.unlock();
                }
                return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value__));
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_));
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        try {
            if ((state_0 & 0xA) == 0 && (state_0 & 0xF) != 0) {
                this.executeInt(frameValue);
                return;
            }
            if ((state_0 & 5) == 0 && (state_0 & 0xF) != 0) {
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
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private Object executeAndSpecialize(Object arguments0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments0Value instanceof Integer) {
                int arguments0Value_ = (Integer)arguments0Value;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Integer n = RoundNode.roundInt(arguments0Value_);
                return n;
            }
            int doubleCast0 = JSTypesGen.specializeImplicitDouble(arguments0Value);
            if (doubleCast0 != 0) {
                double arguments0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arguments0Value);
                if (RoundNode.isCornercase(arguments0Value_)) {
                    state_0 |= doubleCast0 << 4;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Double d = RoundNode.roundCornercase(arguments0Value_);
                    return d;
                }
                if (exclude == 0 && !RoundNode.isCornercase(arguments0Value_) && JSGuards.isDoubleInInt32Range(arguments0Value_)) {
                    state_0 |= doubleCast0 << 4;
                    this.state_0_ = state_0 |= 4;
                    try {
                        lock.unlock();
                        hasLock = false;
                        Integer n = this.roundDoubleInt(arguments0Value_);
                        return n;
                    }
                    catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        lock.lock();
                        try {
                            this.exclude_ |= 1;
                            this.state_0_ &= 0xFFFFFFFB;
                        }
                        finally {
                            lock.unlock();
                        }
                        Object object = this.executeAndSpecialize(arguments0Value_);
                        if (hasLock) {
                            lock.unlock();
                        }
                        return object;
                    }
                }
                if (!RoundNode.isCornercase(arguments0Value_)) {
                    this.roundDouble_profileA_ = ConditionProfile.createBinaryProfile();
                    this.roundDouble_profileB_ = ConditionProfile.createBinaryProfile();
                    this.exclude_ = exclude |= 1;
                    state_0 &= 0xFFFFFFFB;
                    state_0 |= doubleCast0 << 4;
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    Double d = this.roundDouble(arguments0Value_, this.roundDouble_profileA_, this.roundDouble_profileB_);
                    return d;
                }
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
        Object[] data = new Object[5];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "roundInt";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "roundCornercase";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "roundDoubleInt";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : (exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[3] = s;
        s = new Object[3];
        s[0] = "roundDouble";
        if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            ArrayList<List<ConditionProfile>> cached = new ArrayList<List<ConditionProfile>>();
            cached.add(Arrays.asList(this.roundDouble_profileA_, this.roundDouble_profileB_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[4] = s;
        return Introspection.Provider.create(data);
    }

    public static RoundNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
        return new RoundNodeGen(context, builtin, arguments);
    }
}

