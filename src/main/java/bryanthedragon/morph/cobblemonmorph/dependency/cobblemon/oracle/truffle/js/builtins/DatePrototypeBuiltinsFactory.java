
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.builtins.DatePrototypeBuiltins;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

@GeneratedBy(value=DatePrototypeBuiltins.class)
public final class DatePrototypeBuiltinsFactory {

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateToPrimitiveNode.class)
    public static final class JSDateToPrimitiveNodeGen
    extends DatePrototypeBuiltins.JSDateToPrimitiveNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private JSDateToPrimitiveNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.toPrimitive(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "toPrimitive";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateToPrimitiveNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSDateToPrimitiveNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateToJSONNode.class)
    public static final class JSDateToJSONNodeGen
    extends DatePrototypeBuiltins.JSDateToJSONNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private JSDateToJSONNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.toJSON(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "toJSON";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateToJSONNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSDateToJSONNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateGetTimezoneOffsetNode.class)
    public static final class JSDateGetTimezoneOffsetNodeGen
    extends DatePrototypeBuiltins.JSDateGetTimezoneOffsetNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSDateGetTimezoneOffsetNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.getTimezoneOffset(arguments0Value_);
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.getTimezoneOffset(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeDouble(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "getTimezoneOffset";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateGetTimezoneOffsetNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSDateGetTimezoneOffsetNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateSetMillisecondsNode.class)
    public static final class JSDateSetMillisecondsNodeGen
    extends DatePrototypeBuiltins.JSDateSetMillisecondsNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private JSDateSetMillisecondsNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            super(context, builtin, isUTC);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.setMilliseconds(arguments0Value_, arguments1Value_);
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.setMilliseconds(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeDouble(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "setMilliseconds";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateSetMillisecondsNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            return new JSDateSetMillisecondsNodeGen(context, builtin, isUTC, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateSetSecondsNode.class)
    public static final class JSDateSetSecondsNodeGen
    extends DatePrototypeBuiltins.JSDateSetSecondsNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSDateSetSecondsNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            super(context, builtin, isUTC);
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
                Object[] arguments1Value__ = (Object[])arguments1Value_;
                return this.setSeconds(arguments0Value_, arguments1Value__);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
                Object[] arguments1Value__ = (Object[])arguments1Value_;
                return this.setSeconds(arguments0Value_, arguments1Value__);
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
            if (arguments1Value instanceof Object[]) {
                Object[] arguments1Value_ = (Object[])arguments1Value;
                this.state_0_ = state_0 |= 1;
                return this.setSeconds(arguments0Value, arguments1Value_);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
        }

        @Override
        public NodeCost getCost() {
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "setSeconds";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateSetSecondsNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            return new JSDateSetSecondsNodeGen(context, builtin, isUTC, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateSetMinutesNode.class)
    public static final class JSDateSetMinutesNodeGen
    extends DatePrototypeBuiltins.JSDateSetMinutesNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSDateSetMinutesNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            super(context, builtin, isUTC);
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
                Object[] arguments1Value__ = (Object[])arguments1Value_;
                return this.doOperation(arguments0Value_, arguments1Value__);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
                Object[] arguments1Value__ = (Object[])arguments1Value_;
                return this.doOperation(arguments0Value_, arguments1Value__);
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
            if (arguments1Value instanceof Object[]) {
                Object[] arguments1Value_ = (Object[])arguments1Value;
                this.state_0_ = state_0 |= 1;
                return this.doOperation(arguments0Value, arguments1Value_);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
        }

        @Override
        public NodeCost getCost() {
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doOperation";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateSetMinutesNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            return new JSDateSetMinutesNodeGen(context, builtin, isUTC, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateSetHoursNode.class)
    public static final class JSDateSetHoursNodeGen
    extends DatePrototypeBuiltins.JSDateSetHoursNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSDateSetHoursNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            super(context, builtin, isUTC);
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
                Object[] arguments1Value__ = (Object[])arguments1Value_;
                return this.setHours(arguments0Value_, arguments1Value__);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
                Object[] arguments1Value__ = (Object[])arguments1Value_;
                return this.setHours(arguments0Value_, arguments1Value__);
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
            if (arguments1Value instanceof Object[]) {
                Object[] arguments1Value_ = (Object[])arguments1Value;
                this.state_0_ = state_0 |= 1;
                return this.setHours(arguments0Value, arguments1Value_);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
        }

        @Override
        public NodeCost getCost() {
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "setHours";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateSetHoursNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            return new JSDateSetHoursNodeGen(context, builtin, isUTC, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateSetMonthNode.class)
    public static final class JSDateSetMonthNodeGen
    extends DatePrototypeBuiltins.JSDateSetMonthNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSDateSetMonthNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            super(context, builtin, isUTC);
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
                Object[] arguments1Value__ = (Object[])arguments1Value_;
                return this.setMonth(arguments0Value_, arguments1Value__);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
                Object[] arguments1Value__ = (Object[])arguments1Value_;
                return this.setMonth(arguments0Value_, arguments1Value__);
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
            if (arguments1Value instanceof Object[]) {
                Object[] arguments1Value_ = (Object[])arguments1Value;
                this.state_0_ = state_0 |= 1;
                return this.setMonth(arguments0Value, arguments1Value_);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
        }

        @Override
        public NodeCost getCost() {
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "setMonth";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateSetMonthNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            return new JSDateSetMonthNodeGen(context, builtin, isUTC, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateSetFullYearNode.class)
    public static final class JSDateSetFullYearNodeGen
    extends DatePrototypeBuiltins.JSDateSetFullYearNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSDateSetFullYearNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            super(context, builtin, isUTC);
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
                Object[] arguments1Value__ = (Object[])arguments1Value_;
                return this.setFullYear(arguments0Value_, arguments1Value__);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
                Object[] arguments1Value__ = (Object[])arguments1Value_;
                return this.setFullYear(arguments0Value_, arguments1Value__);
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
            if (arguments1Value instanceof Object[]) {
                Object[] arguments1Value_ = (Object[])arguments1Value;
                this.state_0_ = state_0 |= 1;
                return this.setFullYear(arguments0Value, arguments1Value_);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
        }

        @Override
        public NodeCost getCost() {
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "setFullYear";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateSetFullYearNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            return new JSDateSetFullYearNodeGen(context, builtin, isUTC, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateSetYearNode.class)
    public static final class JSDateSetYearNodeGen
    extends DatePrototypeBuiltins.JSDateSetYearNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private JSDateSetYearNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.setYear(arguments0Value_, arguments1Value_);
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.setYear(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeDouble(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "setYear";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateSetYearNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSDateSetYearNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateSetDateNode.class)
    public static final class JSDateSetDateNodeGen
    extends DatePrototypeBuiltins.JSDateSetDateNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private JSDateSetDateNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            super(context, builtin, isUTC);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.doOperation(arguments0Value_, arguments1Value_);
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.doOperation(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeDouble(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "doOperation";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateSetDateNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            return new JSDateSetDateNodeGen(context, builtin, isUTC, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateSetTimeNode.class)
    public static final class JSDateSetTimeNodeGen
    extends DatePrototypeBuiltins.JSDateSetTimeNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private JSDateSetTimeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.doOperation(arguments0Value_, arguments1Value_);
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.doOperation(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeDouble(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "doOperation";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateSetTimeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSDateSetTimeNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateGetMillisecondsNode.class)
    public static final class JSDateGetMillisecondsNodeGen
    extends DatePrototypeBuiltins.JSDateGetMillisecondsNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSDateGetMillisecondsNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            super(context, builtin, isUTC);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.doOperation(arguments0Value_);
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.doOperation(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeDouble(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "doOperation";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateGetMillisecondsNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            return new JSDateGetMillisecondsNodeGen(context, builtin, isUTC, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateGetSecondsNode.class)
    public static final class JSDateGetSecondsNodeGen
    extends DatePrototypeBuiltins.JSDateGetSecondsNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSDateGetSecondsNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            super(context, builtin, isUTC);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.doOperation(arguments0Value_);
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.doOperation(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeDouble(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "doOperation";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateGetSecondsNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            return new JSDateGetSecondsNodeGen(context, builtin, isUTC, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateGetMinutesNode.class)
    public static final class JSDateGetMinutesNodeGen
    extends DatePrototypeBuiltins.JSDateGetMinutesNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSDateGetMinutesNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            super(context, builtin, isUTC);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.doOperation(arguments0Value_);
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.doOperation(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeDouble(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "doOperation";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateGetMinutesNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            return new JSDateGetMinutesNodeGen(context, builtin, isUTC, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateGetHoursNode.class)
    public static final class JSDateGetHoursNodeGen
    extends DatePrototypeBuiltins.JSDateGetHoursNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSDateGetHoursNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            super(context, builtin, isUTC);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.doOperation(arguments0Value_);
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.doOperation(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeDouble(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "doOperation";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateGetHoursNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            return new JSDateGetHoursNodeGen(context, builtin, isUTC, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateGetDayNode.class)
    public static final class JSDateGetDayNodeGen
    extends DatePrototypeBuiltins.JSDateGetDayNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSDateGetDayNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            super(context, builtin, isUTC);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.doOperation(arguments0Value_);
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.doOperation(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeDouble(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "doOperation";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateGetDayNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            return new JSDateGetDayNodeGen(context, builtin, isUTC, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateGetDateNode.class)
    public static final class JSDateGetDateNodeGen
    extends DatePrototypeBuiltins.JSDateGetDateNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSDateGetDateNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            super(context, builtin, isUTC);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.doOperation(arguments0Value_);
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.doOperation(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeDouble(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "doOperation";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateGetDateNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            return new JSDateGetDateNodeGen(context, builtin, isUTC, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateGetMonthNode.class)
    public static final class JSDateGetMonthNodeGen
    extends DatePrototypeBuiltins.JSDateGetMonthNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSDateGetMonthNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            super(context, builtin, isUTC);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.doOperation(arguments0Value_);
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.doOperation(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeDouble(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "doOperation";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateGetMonthNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            return new JSDateGetMonthNodeGen(context, builtin, isUTC, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateGetYearNode.class)
    public static final class JSDateGetYearNodeGen
    extends DatePrototypeBuiltins.JSDateGetYearNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSDateGetYearNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.doOperation(arguments0Value_);
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.doOperation(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeDouble(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "doOperation";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateGetYearNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSDateGetYearNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateGetFullYearNode.class)
    public static final class JSDateGetFullYearNodeGen
    extends DatePrototypeBuiltins.JSDateGetFullYearNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSDateGetFullYearNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            super(context, builtin, isUTC);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.doOperation(arguments0Value_);
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.doOperation(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeDouble(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "doOperation";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateGetFullYearNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            return new JSDateGetFullYearNodeGen(context, builtin, isUTC, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateToISOStringNode.class)
    public static final class JSDateToISOStringNodeGen
    extends DatePrototypeBuiltins.JSDateToISOStringNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSDateToISOStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.doOperation(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "doOperation";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateToISOStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSDateToISOStringNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateToLocaleTimeStringIntlNode.class)
    public static final class JSDateToLocaleTimeStringIntlNodeGen
    extends DatePrototypeBuiltins.JSDateToLocaleTimeStringIntlNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;

        private JSDateToLocaleTimeStringIntlNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
            this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            return this.doOperation(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "doOperation";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateToLocaleTimeStringIntlNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSDateToLocaleTimeStringIntlNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateToLocaleTimeStringNode.class)
    public static final class JSDateToLocaleTimeStringNodeGen
    extends DatePrototypeBuiltins.JSDateToLocaleTimeStringNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSDateToLocaleTimeStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.doOperation(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "doOperation";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateToLocaleTimeStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSDateToLocaleTimeStringNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateToLocaleDateStringIntlNode.class)
    public static final class JSDateToLocaleDateStringIntlNodeGen
    extends DatePrototypeBuiltins.JSDateToLocaleDateStringIntlNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;

        private JSDateToLocaleDateStringIntlNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
            this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            return this.doOperation(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "doOperation";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateToLocaleDateStringIntlNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSDateToLocaleDateStringIntlNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateToLocaleDateStringNode.class)
    public static final class JSDateToLocaleDateStringNodeGen
    extends DatePrototypeBuiltins.JSDateToLocaleDateStringNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSDateToLocaleDateStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.doOperation(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "doOperation";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateToLocaleDateStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSDateToLocaleDateStringNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateToTimeStringNode.class)
    public static final class JSDateToTimeStringNodeGen
    extends DatePrototypeBuiltins.JSDateToTimeStringNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSDateToTimeStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.doOperation(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "doOperation";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateToTimeStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSDateToTimeStringNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateToDateStringNode.class)
    public static final class JSDateToDateStringNodeGen
    extends DatePrototypeBuiltins.JSDateToDateStringNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSDateToDateStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.doOperation(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "doOperation";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateToDateStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSDateToDateStringNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateToStringIntlNode.class)
    public static final class JSDateToStringIntlNodeGen
    extends DatePrototypeBuiltins.JSDateToStringIntlNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;

        private JSDateToStringIntlNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            super(context, builtin, isUTC);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
            this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            return this.doOperation(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "doOperation";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateToStringIntlNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            return new JSDateToStringIntlNodeGen(context, builtin, isUTC, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateToStringNode.class)
    public static final class JSDateToStringNodeGen
    extends DatePrototypeBuiltins.JSDateToStringNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSDateToStringNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            super(context, builtin, isUTC);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.doOperation(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "doOperation";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateToStringNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
            return new JSDateToStringNodeGen(context, builtin, isUTC, arguments);
        }
    }

    @GeneratedBy(value=DatePrototypeBuiltins.JSDateValueOfNode.class)
    public static final class JSDateValueOfNodeGen
    extends DatePrototypeBuiltins.JSDateValueOfNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSDateValueOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.doOperation(arguments0Value_);
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.doOperation(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeDouble(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "doOperation";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DatePrototypeBuiltins.JSDateValueOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSDateValueOfNodeGen(context, builtin, arguments);
        }
    }
}

