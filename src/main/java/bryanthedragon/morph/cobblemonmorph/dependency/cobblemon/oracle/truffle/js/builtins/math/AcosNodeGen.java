
package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.builtins.math.AcosNode;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

@GeneratedBy(value=AcosNode.class)
public final class AcosNodeGen
extends AcosNode
implements Introspection.Provider {
    @Node.Child
    private JavaScriptNode arguments0_;
    @CompilerDirectives.CompilationFinal
    private int state_0_;

    private AcosNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
        if ((state_0 & 2) == 0 && (state_0 & 3) != 0) {
            return this.execute_double0(state_0, frameValue);
        }
        return this.execute_generic1(state_0, frameValue);
    }

    private Object execute_double0(int state_0, VirtualFrame frameValue) {
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
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 1) != 0);
        return AcosNode.acosDouble(arguments0Value_);
    }

    private Object execute_generic1(int state_0, VirtualFrame frameValue) {
        Object arguments0Value_ = this.arguments0_.execute(frameValue);
        if ((state_0 & 1) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C) >>> 2, arguments0Value_)) {
            double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 0x3C) >>> 2, arguments0Value_);
            return AcosNode.acosDouble(arguments0Value__);
        }
        if ((state_0 & 2) != 0) {
            return this.acosGeneric(arguments0Value_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arguments0Value_);
    }

    @Override
    public double executeDouble(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 2) == 0 && (state_0 & 3) != 0) {
            return this.executeDouble_double2(state_0, frameValue);
        }
        return this.executeDouble_generic3(state_0, frameValue);
    }

    private double executeDouble_double2(int state_0, VirtualFrame frameValue) {
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
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 1) != 0);
        return AcosNode.acosDouble(arguments0Value_);
    }

    private double executeDouble_generic3(int state_0, VirtualFrame frameValue) {
        Object arguments0Value_ = this.arguments0_.execute(frameValue);
        if ((state_0 & 1) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C) >>> 2, arguments0Value_)) {
            double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 0x3C) >>> 2, arguments0Value_);
            return AcosNode.acosDouble(arguments0Value__);
        }
        if ((state_0 & 2) != 0) {
            return this.acosGeneric(arguments0Value_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arguments0Value_);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.executeDouble(frameValue);
    }

    private double executeAndSpecialize(Object arguments0Value) {
        int state_0 = this.state_0_;
        int doubleCast0 = JSTypesGen.specializeImplicitDouble(arguments0Value);
        if (doubleCast0 != 0) {
            double arguments0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arguments0Value);
            state_0 |= doubleCast0 << 2;
            this.state_0_ = state_0 |= 1;
            return AcosNode.acosDouble(arguments0Value_);
        }
        this.state_0_ = state_0 |= 2;
        return this.acosGeneric(arguments0Value);
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
        s[0] = "acosDouble";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "acosGeneric";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        return Introspection.Provider.create(data);
    }

    public static AcosNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
        return new AcosNodeGen(context, builtin, arguments);
    }
}

