
package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.builtins.math.Atan2Node;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

@GeneratedBy(value=Atan2Node.class)
public final class Atan2NodeGen
extends Atan2Node
implements Introspection.Provider {
    @Node.Child
    private JavaScriptNode arguments0_;
    @Node.Child
    private JavaScriptNode arguments1_;
    @CompilerDirectives.CompilationFinal
    private int state_0_;

    private Atan2NodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
        if ((state_0 & 2) == 0 && (state_0 & 3) != 0) {
            return this.execute_double_double0(state_0, frameValue);
        }
        return this.execute_generic1(state_0, frameValue);
    }

    private Object execute_double_double0(int state_0, VirtualFrame frameValue) {
        double arguments1Value_;
        double arguments0Value_;
        long arguments0Value_long = 0L;
        int arguments0Value_int = 0;
        try {
            if ((state_0 & 0x38) == 0 && (state_0 & 3) != 0) {
                arguments0Value_ = this.arguments0_.executeDouble(frameValue);
            } else if ((state_0 & 0x34) == 0 && (state_0 & 3) != 0) {
                arguments0Value_int = this.arguments0_.executeInt(frameValue);
                arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
            } else if ((state_0 & 0x1C) == 0 && (state_0 & 3) != 0) {
                arguments0Value_long = this.arguments0_.executeLong(frameValue);
                arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
            } else {
                Object arguments0Value__ = this.arguments0_.execute(frameValue);
                arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C) >>> 2, arguments0Value__);
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
            if ((state_0 & 0x380) == 0 && (state_0 & 3) != 0) {
                arguments1Value_ = this.arguments1_.executeDouble(frameValue);
            } else if ((state_0 & 0x340) == 0 && (state_0 & 3) != 0) {
                arguments1Value_int = this.arguments1_.executeInt(frameValue);
                arguments1Value_ = JSTypes.intToDouble(arguments1Value_int);
            } else if ((state_0 & 0x1C0) == 0 && (state_0 & 3) != 0) {
                arguments1Value_long = this.arguments1_.executeLong(frameValue);
                arguments1Value_ = JSTypes.longToDouble(arguments1Value_long);
            } else {
                Object arguments1Value__ = this.arguments1_.execute(frameValue);
                arguments1Value_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C0) >>> 6, arguments1Value__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize((state_0 & 0x34) == 0 && (state_0 & 3) != 0 ? (Number)arguments0Value_int : (Number)((state_0 & 0x1C) == 0 && (state_0 & 3) != 0 ? (Number)arguments0Value_long : (Number)arguments0Value_), ex.getResult());
        }
        assert ((state_0 & 1) != 0);
        return Atan2Node.atan2Double(arguments0Value_, arguments1Value_);
    }

    private Object execute_generic1(int state_0, VirtualFrame frameValue) {
        Object arguments0Value_ = this.arguments0_.execute(frameValue);
        Object arguments1Value_ = this.arguments1_.execute(frameValue);
        if ((state_0 & 1) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C) >>> 2, arguments0Value_)) {
            double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 0x3C) >>> 2, arguments0Value_);
            if (JSTypesGen.isImplicitDouble((state_0 & 0x3C0) >>> 6, arguments1Value_)) {
                double arguments1Value__ = JSTypesGen.asImplicitDouble((state_0 & 0x3C0) >>> 6, arguments1Value_);
                return Atan2Node.atan2Double(arguments0Value__, arguments1Value__);
            }
        }
        if ((state_0 & 2) != 0) {
            return this.atan2Generic(arguments0Value_, arguments1Value_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
    }

    @Override
    public double executeDouble(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 2) == 0 && (state_0 & 3) != 0) {
            return this.executeDouble_double_double2(state_0, frameValue);
        }
        return this.executeDouble_generic3(state_0, frameValue);
    }

    private double executeDouble_double_double2(int state_0, VirtualFrame frameValue) {
        double arguments1Value_;
        double arguments0Value_;
        long arguments0Value_long = 0L;
        int arguments0Value_int = 0;
        try {
            if ((state_0 & 0x38) == 0 && (state_0 & 3) != 0) {
                arguments0Value_ = this.arguments0_.executeDouble(frameValue);
            } else if ((state_0 & 0x34) == 0 && (state_0 & 3) != 0) {
                arguments0Value_int = this.arguments0_.executeInt(frameValue);
                arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
            } else if ((state_0 & 0x1C) == 0 && (state_0 & 3) != 0) {
                arguments0Value_long = this.arguments0_.executeLong(frameValue);
                arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
            } else {
                Object arguments0Value__ = this.arguments0_.execute(frameValue);
                arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C) >>> 2, arguments0Value__);
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
            if ((state_0 & 0x380) == 0 && (state_0 & 3) != 0) {
                arguments1Value_ = this.arguments1_.executeDouble(frameValue);
            } else if ((state_0 & 0x340) == 0 && (state_0 & 3) != 0) {
                arguments1Value_int = this.arguments1_.executeInt(frameValue);
                arguments1Value_ = JSTypes.intToDouble(arguments1Value_int);
            } else if ((state_0 & 0x1C0) == 0 && (state_0 & 3) != 0) {
                arguments1Value_long = this.arguments1_.executeLong(frameValue);
                arguments1Value_ = JSTypes.longToDouble(arguments1Value_long);
            } else {
                Object arguments1Value__ = this.arguments1_.execute(frameValue);
                arguments1Value_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C0) >>> 6, arguments1Value__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize((state_0 & 0x34) == 0 && (state_0 & 3) != 0 ? (Number)arguments0Value_int : (Number)((state_0 & 0x1C) == 0 && (state_0 & 3) != 0 ? (Number)arguments0Value_long : (Number)arguments0Value_), ex.getResult());
        }
        assert ((state_0 & 1) != 0);
        return Atan2Node.atan2Double(arguments0Value_, arguments1Value_);
    }

    private double executeDouble_generic3(int state_0, VirtualFrame frameValue) {
        Object arguments0Value_ = this.arguments0_.execute(frameValue);
        Object arguments1Value_ = this.arguments1_.execute(frameValue);
        if ((state_0 & 1) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C) >>> 2, arguments0Value_)) {
            double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 0x3C) >>> 2, arguments0Value_);
            if (JSTypesGen.isImplicitDouble((state_0 & 0x3C0) >>> 6, arguments1Value_)) {
                double arguments1Value__ = JSTypesGen.asImplicitDouble((state_0 & 0x3C0) >>> 6, arguments1Value_);
                return Atan2Node.atan2Double(arguments0Value__, arguments1Value__);
            }
        }
        if ((state_0 & 2) != 0) {
            return this.atan2Generic(arguments0Value_, arguments1Value_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.executeDouble(frameValue);
    }

    private double executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
        int state_0 = this.state_0_;
        int doubleCast0 = JSTypesGen.specializeImplicitDouble(arguments0Value);
        if (doubleCast0 != 0) {
            double arguments0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arguments0Value);
            int doubleCast1 = JSTypesGen.specializeImplicitDouble(arguments1Value);
            if (doubleCast1 != 0) {
                double arguments1Value_ = JSTypesGen.asImplicitDouble(doubleCast1, arguments1Value);
                state_0 |= doubleCast0 << 2;
                state_0 |= doubleCast1 << 6;
                this.state_0_ = state_0 |= 1;
                return Atan2Node.atan2Double(arguments0Value_, arguments1Value_);
            }
        }
        this.state_0_ = state_0 |= 2;
        return this.atan2Generic(arguments0Value, arguments1Value);
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if ((state_0 & 3) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 3 & (state_0 & 3) - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[3];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "atan2Double";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "atan2Generic";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        return Introspection.Provider.create(data);
    }

    public static Atan2Node create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
        return new Atan2NodeGen(context, builtin, arguments);
    }
}

