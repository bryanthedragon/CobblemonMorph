
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.builtins.NumberFunctionBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.SafeInteger;

@GeneratedBy(value=NumberFunctionBuiltins.class)
public final class NumberFunctionBuiltinsFactory {

    @GeneratedBy(value=NumberFunctionBuiltins.JSNumberIsSafeIntegerNode.class)
    public static final class JSNumberIsSafeIntegerNodeGen
    extends NumberFunctionBuiltins.JSNumberIsSafeIntegerNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSNumberIsSafeIntegerNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.isSafeIntegerInt(arguments0Value_);
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
            return this.isSafeIntegerDouble(arguments0Value_);
        }

        private Object execute_generic2(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
                int arguments0Value__ = (Integer)arguments0Value_;
                return this.isSafeIntegerInt(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x78) >>> 3, arguments0Value_)) {
                double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 0x78) >>> 3, arguments0Value_);
                return this.isSafeIntegerDouble(arguments0Value__);
            }
            if ((state_0 & 4) != 0 && !JSGuards.isNumber(arguments0Value_)) {
                return this.isSafeIntegerNotANumber(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            if ((state_0 & 6) == 0 && (state_0 & 7) != 0) {
                return this.executeBoolean_int3(state_0, frameValue);
            }
            if ((state_0 & 5) == 0 && (state_0 & 7) != 0) {
                return this.executeBoolean_double4(state_0, frameValue);
            }
            return this.executeBoolean_generic5(state_0, frameValue);
        }

        private boolean executeBoolean_int3(int state_0, VirtualFrame frameValue) {
            int arguments0Value_;
            try {
                arguments0Value_ = this.arguments0_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(ex.getResult());
            }
            assert ((state_0 & 1) != 0);
            return this.isSafeIntegerInt(arguments0Value_);
        }

        private boolean executeBoolean_double4(int state_0, VirtualFrame frameValue) {
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
            return this.isSafeIntegerDouble(arguments0Value_);
        }

        private boolean executeBoolean_generic5(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
                int arguments0Value__ = (Integer)arguments0Value_;
                return this.isSafeIntegerInt(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x78) >>> 3, arguments0Value_)) {
                double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 0x78) >>> 3, arguments0Value_);
                return this.isSafeIntegerDouble(arguments0Value__);
            }
            if ((state_0 & 4) != 0 && !JSGuards.isNumber(arguments0Value_)) {
                return this.isSafeIntegerNotANumber(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeBoolean(frameValue);
        }

        private boolean executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof Integer) {
                int arguments0Value_ = (Integer)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.isSafeIntegerInt(arguments0Value_);
            }
            int doubleCast0 = JSTypesGen.specializeImplicitDouble(arguments0Value);
            if (doubleCast0 != 0) {
                double arguments0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arguments0Value);
                state_0 |= doubleCast0 << 3;
                this.state_0_ = state_0 |= 2;
                return this.isSafeIntegerDouble(arguments0Value_);
            }
            if (!JSGuards.isNumber(arguments0Value)) {
                this.state_0_ = state_0 |= 4;
                return this.isSafeIntegerNotANumber(arguments0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            s[0] = "isSafeIntegerInt";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "isSafeIntegerDouble";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "isSafeIntegerNotANumber";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static NumberFunctionBuiltins.JSNumberIsSafeIntegerNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSNumberIsSafeIntegerNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=NumberFunctionBuiltins.JSNumberIsIntegerNode.class)
    public static final class JSNumberIsIntegerNodeGen
    extends NumberFunctionBuiltins.JSNumberIsIntegerNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSNumberIsIntegerNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 0xB) == 0 && (state_0 & 0xF) != 0) {
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
            return NumberFunctionBuiltins.JSNumberIsIntegerNode.isInteger(arguments0Value_);
        }

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
            assert ((state_0 & 4) != 0);
            return NumberFunctionBuiltins.JSNumberIsIntegerNode.isInteger(arguments0Value_);
        }

        private Object execute_generic2(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
                int arguments0Value__ = (Integer)arguments0Value_;
                return NumberFunctionBuiltins.JSNumberIsIntegerNode.isInteger(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && arguments0Value_ instanceof SafeInteger) {
                SafeInteger arguments0Value__ = (SafeInteger)arguments0Value_;
                return NumberFunctionBuiltins.JSNumberIsIntegerNode.isInteger(arguments0Value__);
            }
            if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value_)) {
                double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value_);
                return NumberFunctionBuiltins.JSNumberIsIntegerNode.isInteger(arguments0Value__);
            }
            if ((state_0 & 8) != 0 && !JSGuards.isNumber(arguments0Value_)) {
                return NumberFunctionBuiltins.JSNumberIsIntegerNode.isInteger(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            if ((state_0 & 0xE) == 0 && (state_0 & 0xF) != 0) {
                return this.executeBoolean_int3(state_0, frameValue);
            }
            if ((state_0 & 0xB) == 0 && (state_0 & 0xF) != 0) {
                return this.executeBoolean_double4(state_0, frameValue);
            }
            return this.executeBoolean_generic5(state_0, frameValue);
        }

        private boolean executeBoolean_int3(int state_0, VirtualFrame frameValue) {
            int arguments0Value_;
            try {
                arguments0Value_ = this.arguments0_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(ex.getResult());
            }
            assert ((state_0 & 1) != 0);
            return NumberFunctionBuiltins.JSNumberIsIntegerNode.isInteger(arguments0Value_);
        }

        private boolean executeBoolean_double4(int state_0, VirtualFrame frameValue) {
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
            assert ((state_0 & 4) != 0);
            return NumberFunctionBuiltins.JSNumberIsIntegerNode.isInteger(arguments0Value_);
        }

        private boolean executeBoolean_generic5(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
                int arguments0Value__ = (Integer)arguments0Value_;
                return NumberFunctionBuiltins.JSNumberIsIntegerNode.isInteger(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && arguments0Value_ instanceof SafeInteger) {
                SafeInteger arguments0Value__ = (SafeInteger)arguments0Value_;
                return NumberFunctionBuiltins.JSNumberIsIntegerNode.isInteger(arguments0Value__);
            }
            if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value_)) {
                double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value_);
                return NumberFunctionBuiltins.JSNumberIsIntegerNode.isInteger(arguments0Value__);
            }
            if ((state_0 & 8) != 0 && !JSGuards.isNumber(arguments0Value_)) {
                return NumberFunctionBuiltins.JSNumberIsIntegerNode.isInteger(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeBoolean(frameValue);
        }

        private boolean executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof Integer) {
                int arguments0Value_ = (Integer)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return NumberFunctionBuiltins.JSNumberIsIntegerNode.isInteger(arguments0Value_);
            }
            if (arguments0Value instanceof SafeInteger) {
                SafeInteger arguments0Value_ = (SafeInteger)arguments0Value;
                this.state_0_ = state_0 |= 2;
                return NumberFunctionBuiltins.JSNumberIsIntegerNode.isInteger(arguments0Value_);
            }
            int doubleCast0 = JSTypesGen.specializeImplicitDouble(arguments0Value);
            if (doubleCast0 != 0) {
                double arguments0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arguments0Value);
                state_0 |= doubleCast0 << 4;
                this.state_0_ = state_0 |= 4;
                return NumberFunctionBuiltins.JSNumberIsIntegerNode.isInteger(arguments0Value_);
            }
            if (!JSGuards.isNumber(arguments0Value)) {
                this.state_0_ = state_0 |= 8;
                return NumberFunctionBuiltins.JSNumberIsIntegerNode.isInteger(arguments0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            Object[] s = new Object[3];
            s[0] = "isInteger";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "isInteger";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "isInteger";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            s = new Object[3];
            s[0] = "isInteger";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            return Introspection.Provider.create(data);
        }

        public static NumberFunctionBuiltins.JSNumberIsIntegerNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSNumberIsIntegerNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=NumberFunctionBuiltins.JSNumberIsFiniteNode.class)
    public static final class JSNumberIsFiniteNodeGen
    extends NumberFunctionBuiltins.JSNumberIsFiniteNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSNumberIsFiniteNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.isFinite(arguments0Value_);
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
            return this.isFinite(arguments0Value_);
        }

        private Object execute_generic2(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
                int arguments0Value__ = (Integer)arguments0Value_;
                return this.isFinite(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x78) >>> 3, arguments0Value_)) {
                double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 0x78) >>> 3, arguments0Value_);
                return this.isFinite(arguments0Value__);
            }
            if ((state_0 & 4) != 0 && !JSGuards.isNumber(arguments0Value_)) {
                return this.isFinite(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            if ((state_0 & 6) == 0 && (state_0 & 7) != 0) {
                return this.executeBoolean_int3(state_0, frameValue);
            }
            if ((state_0 & 5) == 0 && (state_0 & 7) != 0) {
                return this.executeBoolean_double4(state_0, frameValue);
            }
            return this.executeBoolean_generic5(state_0, frameValue);
        }

        private boolean executeBoolean_int3(int state_0, VirtualFrame frameValue) {
            int arguments0Value_;
            try {
                arguments0Value_ = this.arguments0_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(ex.getResult());
            }
            assert ((state_0 & 1) != 0);
            return this.isFinite(arguments0Value_);
        }

        private boolean executeBoolean_double4(int state_0, VirtualFrame frameValue) {
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
            return this.isFinite(arguments0Value_);
        }

        private boolean executeBoolean_generic5(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
                int arguments0Value__ = (Integer)arguments0Value_;
                return this.isFinite(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x78) >>> 3, arguments0Value_)) {
                double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 0x78) >>> 3, arguments0Value_);
                return this.isFinite(arguments0Value__);
            }
            if ((state_0 & 4) != 0 && !JSGuards.isNumber(arguments0Value_)) {
                return this.isFinite(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeBoolean(frameValue);
        }

        private boolean executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof Integer) {
                int arguments0Value_ = (Integer)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.isFinite(arguments0Value_);
            }
            int doubleCast0 = JSTypesGen.specializeImplicitDouble(arguments0Value);
            if (doubleCast0 != 0) {
                double arguments0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arguments0Value);
                state_0 |= doubleCast0 << 3;
                this.state_0_ = state_0 |= 2;
                return this.isFinite(arguments0Value_);
            }
            if (!JSGuards.isNumber(arguments0Value)) {
                this.state_0_ = state_0 |= 4;
                return this.isFinite(arguments0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            s[0] = "isFinite";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "isFinite";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "isFinite";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static NumberFunctionBuiltins.JSNumberIsFiniteNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSNumberIsFiniteNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=NumberFunctionBuiltins.JSNumberIsNaNNode.class)
    public static final class JSNumberIsNaNNodeGen
    extends NumberFunctionBuiltins.JSNumberIsNaNNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSNumberIsNaNNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) == 0 && (state_0 & 3) != 0) {
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
            assert ((state_0 & 2) != 0);
            return this.isNaNDouble(arguments0Value_);
        }

        private Object execute_generic1(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && !this.isDouble(arguments0Value_)) {
                return this.isNaNNotDouble(arguments0Value_);
            }
            if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C) >>> 2, arguments0Value_)) {
                double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 0x3C) >>> 2, arguments0Value_);
                return this.isNaNDouble(arguments0Value__);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            if ((state_0 & 1) == 0 && (state_0 & 3) != 0) {
                return this.executeBoolean_double2(state_0, frameValue);
            }
            return this.executeBoolean_generic3(state_0, frameValue);
        }

        private boolean executeBoolean_double2(int state_0, VirtualFrame frameValue) {
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
            assert ((state_0 & 2) != 0);
            return this.isNaNDouble(arguments0Value_);
        }

        private boolean executeBoolean_generic3(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && !this.isDouble(arguments0Value_)) {
                return this.isNaNNotDouble(arguments0Value_);
            }
            if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C) >>> 2, arguments0Value_)) {
                double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 0x3C) >>> 2, arguments0Value_);
                return this.isNaNDouble(arguments0Value__);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeBoolean(frameValue);
        }

        private boolean executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (!this.isDouble(arguments0Value)) {
                this.state_0_ = state_0 |= 1;
                return this.isNaNNotDouble(arguments0Value);
            }
            int doubleCast0 = JSTypesGen.specializeImplicitDouble(arguments0Value);
            if (doubleCast0 != 0) {
                double arguments0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arguments0Value);
                state_0 |= doubleCast0 << 2;
                this.state_0_ = state_0 |= 2;
                return this.isNaNDouble(arguments0Value_);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            s[0] = "isNaNNotDouble";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "isNaNDouble";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static NumberFunctionBuiltins.JSNumberIsNaNNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSNumberIsNaNNodeGen(context, builtin, arguments);
        }
    }
}

