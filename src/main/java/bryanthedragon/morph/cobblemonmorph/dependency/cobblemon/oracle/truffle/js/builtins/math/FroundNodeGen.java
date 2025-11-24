
package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.builtins.math.FroundNode;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;

@GeneratedBy(value=FroundNode.class)
public final class FroundNodeGen
extends FroundNode
implements Introspection.Provider {
    @Node.Child
    private JavaScriptNode arguments0_;
    @CompilerDirectives.CompilationFinal
    private int state_0_;

    private FroundNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
        if ((state_0 & 6) == 0 && (state_0 & 7) != 0) {
            return this.execute_int0(state_0, frameValue);
        }
        if ((state_0 & 5) == 0 && (state_0 & 7) != 0) {
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
        if (JSRuntime.intIsRepresentableAsFloat(arguments0Value_)) {
            return FroundNode.fround(arguments0Value_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arguments0Value_);
    }

    private Object execute_double1(int state_0, VirtualFrame frameValue) {
        double arguments0Value_;
        long arguments0Value_long = 0L;
        int arguments0Value_int = 0;
        try {
            if ((state_0 & 0x70) == 0 && (state_0 & 7) != 0) {
                arguments0Value_ = this.arguments0_.executeDouble(frameValue);
            } else if ((state_0 & 0x68) == 0 && (state_0 & 7) != 0) {
                arguments0Value_int = this.arguments0_.executeInt(frameValue);
                arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
            } else if ((state_0 & 0x38) == 0 && (state_0 & 7) != 0) {
                arguments0Value_long = this.arguments0_.executeLong(frameValue);
                arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
            } else {
                Object arguments0Value__ = this.arguments0_.execute(frameValue);
                arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 0x78) >>> 3, arguments0Value__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 2) != 0);
        return FroundNode.fround(arguments0Value_);
    }

    private Object execute_generic2(int state_0, VirtualFrame frameValue) {
        int arguments0Value__;
        Object arguments0Value_ = this.arguments0_.execute(frameValue);
        if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer && JSRuntime.intIsRepresentableAsFloat(arguments0Value__ = ((Integer)arguments0Value_).intValue())) {
            return FroundNode.fround(arguments0Value__);
        }
        if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x78) >>> 3, arguments0Value_)) {
            double arguments0Value__2 = JSTypesGen.asImplicitDouble((state_0 & 0x78) >>> 3, arguments0Value_);
            return FroundNode.fround(arguments0Value__2);
        }
        if ((state_0 & 4) != 0) {
            return this.fround(arguments0Value_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arguments0Value_);
    }

    @Override
    public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
        int state_0 = this.state_0_;
        if ((state_0 & 4) == 0 && (state_0 & 6) != 0) {
            return this.executeDouble_double3(state_0, frameValue);
        }
        return this.executeDouble_generic4(state_0, frameValue);
    }

    private double executeDouble_double3(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
        double arguments0Value_;
        long arguments0Value_long = 0L;
        int arguments0Value_int = 0;
        try {
            if ((state_0 & 0x70) == 0 && (state_0 & 7) != 0) {
                arguments0Value_ = this.arguments0_.executeDouble(frameValue);
            } else if ((state_0 & 0x68) == 0 && (state_0 & 7) != 0) {
                arguments0Value_int = this.arguments0_.executeInt(frameValue);
                arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
            } else if ((state_0 & 0x38) == 0 && (state_0 & 7) != 0) {
                arguments0Value_long = this.arguments0_.executeLong(frameValue);
                arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
            } else {
                Object arguments0Value__ = this.arguments0_.execute(frameValue);
                arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 0x78) >>> 3, arguments0Value__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(this.executeAndSpecialize(ex.getResult()));
        }
        assert ((state_0 & 2) != 0);
        return FroundNode.fround(arguments0Value_);
    }

    private double executeDouble_generic4(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
        Object arguments0Value_ = this.arguments0_.execute(frameValue);
        if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x78) >>> 3, arguments0Value_)) {
            double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 0x78) >>> 3, arguments0Value_);
            return FroundNode.fround(arguments0Value__);
        }
        if ((state_0 & 4) != 0) {
            return this.fround(arguments0Value_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectDouble(this.executeAndSpecialize(arguments0Value_));
    }

    @Override
    public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
        int arguments0Value_;
        int state_0 = this.state_0_;
        try {
            arguments0Value_ = this.arguments0_.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(ex.getResult()));
        }
        if ((state_0 & 1) != 0 && JSRuntime.intIsRepresentableAsFloat(arguments0Value_)) {
            return FroundNode.fround(arguments0Value_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_));
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        try {
            if ((state_0 & 6) == 0 && (state_0 & 7) != 0) {
                this.executeInt(frameValue);
                return;
            }
            if ((state_0 & 1) == 0 && (state_0 & 7) != 0) {
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

    private Object executeAndSpecialize(Object arguments0Value) {
        int arguments0Value_;
        int state_0 = this.state_0_;
        if (arguments0Value instanceof Integer && JSRuntime.intIsRepresentableAsFloat(arguments0Value_ = ((Integer)arguments0Value).intValue())) {
            this.state_0_ = state_0 |= 1;
            return FroundNode.fround(arguments0Value_);
        }
        int doubleCast0 = JSTypesGen.specializeImplicitDouble(arguments0Value);
        if (doubleCast0 != 0) {
            double arguments0Value_2 = JSTypesGen.asImplicitDouble(doubleCast0, arguments0Value);
            state_0 |= doubleCast0 << 3;
            this.state_0_ = state_0 |= 2;
            return FroundNode.fround(arguments0Value_2);
        }
        this.state_0_ = state_0 |= 4;
        return this.fround(arguments0Value);
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if ((state_0 & 7) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 7 & (state_0 & 7) - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[4];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "fround";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "fround";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "fround";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        return Introspection.Provider.create(data);
    }

    public static FroundNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
        return new FroundNodeGen(context, builtin, arguments);
    }
}

