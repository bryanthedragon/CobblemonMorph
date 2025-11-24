
package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.intl.LocalePrototypeBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.intl.JSLocaleObject;

@GeneratedBy(value=LocalePrototypeBuiltins.class)
public final class LocalePrototypeBuiltinsFactory {

    @GeneratedBy(value=LocalePrototypeBuiltins.JSLocaleWeekInfoAccessor.class)
    public static final class JSLocaleWeekInfoAccessorNodeGen
    extends LocalePrototypeBuiltins.JSLocaleWeekInfoAccessor
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSLocaleWeekInfoAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
                return this.doLocale(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
                return this.doOther(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.doLocale(arguments0Value_);
            }
            if (!JSGuards.isJSLocale(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.doOther(arguments0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doLocale";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doOther";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static LocalePrototypeBuiltins.JSLocaleWeekInfoAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSLocaleWeekInfoAccessorNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=LocalePrototypeBuiltins.JSLocaleTextInfoAccessor.class)
    public static final class JSLocaleTextInfoAccessorNodeGen
    extends LocalePrototypeBuiltins.JSLocaleTextInfoAccessor
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSLocaleTextInfoAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
                return this.doLocale(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
                return this.doOther(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.doLocale(arguments0Value_);
            }
            if (!JSGuards.isJSLocale(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.doOther(arguments0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doLocale";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doOther";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static LocalePrototypeBuiltins.JSLocaleTextInfoAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSLocaleTextInfoAccessorNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=LocalePrototypeBuiltins.JSLocaleTimeZonesAccessor.class)
    public static final class JSLocaleTimeZonesAccessorNodeGen
    extends LocalePrototypeBuiltins.JSLocaleTimeZonesAccessor
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSLocaleTimeZonesAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
                return this.doLocale(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
                return this.doOther(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.doLocale(arguments0Value_);
            }
            if (!JSGuards.isJSLocale(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.doOther(arguments0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doLocale";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doOther";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static LocalePrototypeBuiltins.JSLocaleTimeZonesAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSLocaleTimeZonesAccessorNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=LocalePrototypeBuiltins.JSLocaleNumberingSystemsAccessor.class)
    public static final class JSLocaleNumberingSystemsAccessorNodeGen
    extends LocalePrototypeBuiltins.JSLocaleNumberingSystemsAccessor
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSLocaleNumberingSystemsAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
                return this.doLocale(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
                return this.doOther(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.doLocale(arguments0Value_);
            }
            if (!JSGuards.isJSLocale(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.doOther(arguments0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doLocale";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doOther";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static LocalePrototypeBuiltins.JSLocaleNumberingSystemsAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSLocaleNumberingSystemsAccessorNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=LocalePrototypeBuiltins.JSLocaleHourCyclesAccessor.class)
    public static final class JSLocaleHourCyclesAccessorNodeGen
    extends LocalePrototypeBuiltins.JSLocaleHourCyclesAccessor
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSLocaleHourCyclesAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
                return this.doLocale(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
                return this.doOther(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.doLocale(arguments0Value_);
            }
            if (!JSGuards.isJSLocale(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.doOther(arguments0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doLocale";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doOther";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static LocalePrototypeBuiltins.JSLocaleHourCyclesAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSLocaleHourCyclesAccessorNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=LocalePrototypeBuiltins.JSLocaleCollationsAccessor.class)
    public static final class JSLocaleCollationsAccessorNodeGen
    extends LocalePrototypeBuiltins.JSLocaleCollationsAccessor
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSLocaleCollationsAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
                return this.doLocale(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
                return this.doOther(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.doLocale(arguments0Value_);
            }
            if (!JSGuards.isJSLocale(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.doOther(arguments0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doLocale";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doOther";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static LocalePrototypeBuiltins.JSLocaleCollationsAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSLocaleCollationsAccessorNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=LocalePrototypeBuiltins.JSLocaleCalendarsAccessor.class)
    public static final class JSLocaleCalendarsAccessorNodeGen
    extends LocalePrototypeBuiltins.JSLocaleCalendarsAccessor
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSLocaleCalendarsAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
                return this.doLocale(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
                return this.doOther(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.doLocale(arguments0Value_);
            }
            if (!JSGuards.isJSLocale(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.doOther(arguments0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doLocale";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doOther";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static LocalePrototypeBuiltins.JSLocaleCalendarsAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSLocaleCalendarsAccessorNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=LocalePrototypeBuiltins.JSLocaleRegionAccessor.class)
    public static final class JSLocaleRegionAccessorNodeGen
    extends LocalePrototypeBuiltins.JSLocaleRegionAccessor
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSLocaleRegionAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
                return this.doLocale(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
                return this.doOther(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.doLocale(arguments0Value_);
            }
            if (!JSGuards.isJSLocale(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.doOther(arguments0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doLocale";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doOther";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static LocalePrototypeBuiltins.JSLocaleRegionAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSLocaleRegionAccessorNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=LocalePrototypeBuiltins.JSLocaleScriptAccessor.class)
    public static final class JSLocaleScriptAccessorNodeGen
    extends LocalePrototypeBuiltins.JSLocaleScriptAccessor
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSLocaleScriptAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
                return this.doLocale(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
                return this.doOther(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.doLocale(arguments0Value_);
            }
            if (!JSGuards.isJSLocale(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.doOther(arguments0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doLocale";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doOther";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static LocalePrototypeBuiltins.JSLocaleScriptAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSLocaleScriptAccessorNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=LocalePrototypeBuiltins.JSLocaleLanguageAccessor.class)
    public static final class JSLocaleLanguageAccessorNodeGen
    extends LocalePrototypeBuiltins.JSLocaleLanguageAccessor
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSLocaleLanguageAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
                return this.doLocale(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
                return this.doOther(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.doLocale(arguments0Value_);
            }
            if (!JSGuards.isJSLocale(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.doOther(arguments0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doLocale";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doOther";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static LocalePrototypeBuiltins.JSLocaleLanguageAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSLocaleLanguageAccessorNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=LocalePrototypeBuiltins.JSLocaleNumberingSystemAccessor.class)
    public static final class JSLocaleNumberingSystemAccessorNodeGen
    extends LocalePrototypeBuiltins.JSLocaleNumberingSystemAccessor
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSLocaleNumberingSystemAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
                return this.doLocale(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
                return this.doOther(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.doLocale(arguments0Value_);
            }
            if (!JSGuards.isJSLocale(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.doOther(arguments0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doLocale";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doOther";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static LocalePrototypeBuiltins.JSLocaleNumberingSystemAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSLocaleNumberingSystemAccessorNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=LocalePrototypeBuiltins.JSLocaleNumericAccessor.class)
    public static final class JSLocaleNumericAccessorNodeGen
    extends LocalePrototypeBuiltins.JSLocaleNumericAccessor
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSLocaleNumericAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
                return this.doLocale(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
                return this.doOther(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
                return this.doLocale(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
                return this.doOther(arguments0Value_);
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
            if (arguments0Value instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.doLocale(arguments0Value_);
            }
            if (!JSGuards.isJSLocale(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.doOther(arguments0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doLocale";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doOther";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static LocalePrototypeBuiltins.JSLocaleNumericAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSLocaleNumericAccessorNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=LocalePrototypeBuiltins.JSLocaleHourCycleAccessor.class)
    public static final class JSLocaleHourCycleAccessorNodeGen
    extends LocalePrototypeBuiltins.JSLocaleHourCycleAccessor
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSLocaleHourCycleAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
                return this.doLocale(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
                return this.doOther(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.doLocale(arguments0Value_);
            }
            if (!JSGuards.isJSLocale(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.doOther(arguments0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doLocale";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doOther";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static LocalePrototypeBuiltins.JSLocaleHourCycleAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSLocaleHourCycleAccessorNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=LocalePrototypeBuiltins.JSLocaleCollationAccessor.class)
    public static final class JSLocaleCollationAccessorNodeGen
    extends LocalePrototypeBuiltins.JSLocaleCollationAccessor
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSLocaleCollationAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
                return this.doLocale(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
                return this.doOther(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.doLocale(arguments0Value_);
            }
            if (!JSGuards.isJSLocale(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.doOther(arguments0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doLocale";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doOther";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static LocalePrototypeBuiltins.JSLocaleCollationAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSLocaleCollationAccessorNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=LocalePrototypeBuiltins.JSLocaleCaseFirstAccessor.class)
    public static final class JSLocaleCaseFirstAccessorNodeGen
    extends LocalePrototypeBuiltins.JSLocaleCaseFirstAccessor
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSLocaleCaseFirstAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
                return this.doLocale(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
                return this.doOther(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.doLocale(arguments0Value_);
            }
            if (!JSGuards.isJSLocale(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.doOther(arguments0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doLocale";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doOther";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static LocalePrototypeBuiltins.JSLocaleCaseFirstAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSLocaleCaseFirstAccessorNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=LocalePrototypeBuiltins.JSLocaleCalendarAccessor.class)
    public static final class JSLocaleCalendarAccessorNodeGen
    extends LocalePrototypeBuiltins.JSLocaleCalendarAccessor
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSLocaleCalendarAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
                return this.doLocale(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
                return this.doOther(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.doLocale(arguments0Value_);
            }
            if (!JSGuards.isJSLocale(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.doOther(arguments0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doLocale";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doOther";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static LocalePrototypeBuiltins.JSLocaleCalendarAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSLocaleCalendarAccessorNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=LocalePrototypeBuiltins.JSLocaleBaseNameAccessor.class)
    public static final class JSLocaleBaseNameAccessorNodeGen
    extends LocalePrototypeBuiltins.JSLocaleBaseNameAccessor
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSLocaleBaseNameAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
                return this.doLocale(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
                return this.doOther(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.doLocale(arguments0Value_);
            }
            if (!JSGuards.isJSLocale(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.doOther(arguments0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doLocale";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doOther";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static LocalePrototypeBuiltins.JSLocaleBaseNameAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSLocaleBaseNameAccessorNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=LocalePrototypeBuiltins.JSLocaleToStringNode.class)
    public static final class JSLocaleToStringNodeGen
    extends LocalePrototypeBuiltins.JSLocaleToStringNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSLocaleToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
                return this.doLocale(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
                return this.doOther(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private TruffleString executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.doLocale(arguments0Value_);
            }
            if (!JSGuards.isJSLocale(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.doOther(arguments0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doLocale";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doOther";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static LocalePrototypeBuiltins.JSLocaleToStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSLocaleToStringNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=LocalePrototypeBuiltins.JSLocaleMinimizeNode.class)
    public static final class JSLocaleMinimizeNodeGen
    extends LocalePrototypeBuiltins.JSLocaleMinimizeNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSLocaleMinimizeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
                return this.doLocale(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
                return this.doOther(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.doLocale(arguments0Value_);
            }
            if (!JSGuards.isJSLocale(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.doOther(arguments0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doLocale";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doOther";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static LocalePrototypeBuiltins.JSLocaleMinimizeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSLocaleMinimizeNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=LocalePrototypeBuiltins.JSLocaleMaximizeNode.class)
    public static final class JSLocaleMaximizeNodeGen
    extends LocalePrototypeBuiltins.JSLocaleMaximizeNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSLocaleMaximizeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
                return this.doLocale(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
                return this.doOther(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSLocaleObject) {
                JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.doLocale(arguments0Value_);
            }
            if (!JSGuards.isJSLocale(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.doOther(arguments0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doLocale";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doOther";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static LocalePrototypeBuiltins.JSLocaleMaximizeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSLocaleMaximizeNodeGen(context, builtin, arguments);
        }
    }
}

