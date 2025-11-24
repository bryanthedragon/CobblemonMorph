
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.BigIntPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GeneratedBy(value=BigIntPrototypeBuiltins.class)
public final class BigIntPrototypeBuiltinsFactory {

    @GeneratedBy(value=BigIntPrototypeBuiltins.JSBigIntValueOfNode.class)
    public static final class JSBigIntValueOfNodeGen
    extends BigIntPrototypeBuiltins.JSBigIntValueOfNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSBigIntValueOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            JSDynamicObject arguments0Value__;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof BigInt) {
                BigInt arguments0Value__2 = (BigInt)arguments0Value_;
                return this.valueOfBigInt(arguments0Value__2);
            }
            if ((state_0 & 2) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSBigInt(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                return this.valueOf(arguments0Value__);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            if ((state_0 & 3) != 0) {
                this.execute(frameValue);
                return;
            }
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 4) != 0 && JSBigIntValueOfNodeGen.fallbackGuard_(state_0, arguments0Value_)) {
                this.valueOf(arguments0Value_);
                return;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arguments0Value_);
        }

        private BigInt executeAndSpecialize(Object arguments0Value) {
            JSDynamicObject arguments0Value_;
            int state_0 = this.state_0_;
            if (arguments0Value instanceof BigInt) {
                BigInt arguments0Value_2 = (BigInt)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.valueOfBigInt(arguments0Value_2);
            }
            if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSBigInt(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.valueOf(arguments0Value_);
            }
            this.state_0_ = state_0 |= 4;
            this.valueOf(arguments0Value);
            return null;
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
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "valueOfBigInt";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "valueOf";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "valueOf";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        private static boolean fallbackGuard_(int state_0, Object arguments0Value) {
            JSDynamicObject arguments0Value_;
            if ((state_0 & 1) == 0 && arguments0Value instanceof BigInt) {
                return false;
            }
            return !(arguments0Value instanceof JSDynamicObject) || !JSGuards.isJSBigInt(arguments0Value_ = (JSDynamicObject)arguments0Value);
        }

        public static BigIntPrototypeBuiltins.JSBigIntValueOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSBigIntValueOfNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=BigIntPrototypeBuiltins.JSBigIntToLocaleStringNode.class)
    public static final class JSBigIntToLocaleStringNodeGen
    extends BigIntPrototypeBuiltins.JSBigIntToLocaleStringNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSBigIntToLocaleStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            JSDynamicObject arguments0Value__;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof BigInt) {
                BigInt arguments0Value__2 = (BigInt)arguments0Value_;
                return this.toLocaleStringBigInt(arguments0Value__2);
            }
            if ((state_0 & 2) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSBigInt(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                return this.toLocaleStringJSBigInt(arguments0Value__);
            }
            if ((state_0 & 4) != 0 && JSBigIntToLocaleStringNodeGen.fallbackGuard_(state_0, arguments0Value_)) {
                return this.failForNonBigInts(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            JSDynamicObject arguments0Value_;
            int state_0 = this.state_0_;
            if (arguments0Value instanceof BigInt) {
                BigInt arguments0Value_2 = (BigInt)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.toLocaleStringBigInt(arguments0Value_2);
            }
            if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSBigInt(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.toLocaleStringJSBigInt(arguments0Value_);
            }
            this.state_0_ = state_0 |= 4;
            return this.failForNonBigInts(arguments0Value);
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
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "toLocaleStringBigInt";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "toLocaleStringJSBigInt";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "failForNonBigInts";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        private static boolean fallbackGuard_(int state_0, Object arguments0Value) {
            JSDynamicObject arguments0Value_;
            if ((state_0 & 1) == 0 && arguments0Value instanceof BigInt) {
                return false;
            }
            return !(arguments0Value instanceof JSDynamicObject) || !JSGuards.isJSBigInt(arguments0Value_ = (JSDynamicObject)arguments0Value);
        }

        public static BigIntPrototypeBuiltins.JSBigIntToLocaleStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSBigIntToLocaleStringNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=BigIntPrototypeBuiltins.JSBigIntToLocaleStringIntlNode.class)
    public static final class JSBigIntToLocaleStringIntlNodeGen
    extends BigIntPrototypeBuiltins.JSBigIntToLocaleStringIntlNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSBigIntToLocaleStringIntlNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if (state_0 != 0) {
                JSDynamicObject arguments0Value__;
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof BigInt) {
                    BigInt arguments0Value__2 = (BigInt)arguments0Value_;
                    return this.bigIntToLocaleString(arguments0Value__2, arguments1Value_, arguments2Value_);
                }
                if ((state_0 & 2) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSBigInt(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                    return this.jsBigIntToLocaleString(arguments0Value__, arguments1Value_, arguments2Value_);
                }
                if ((state_0 & 4) != 0 && JSBigIntToLocaleStringIntlNodeGen.fallbackGuard_(state_0, arguments0Value_, arguments1Value_, arguments2Value_)) {
                    return this.failForNonBigInts(arguments0Value_, arguments1Value_, arguments2Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            JSDynamicObject arguments0Value_;
            int state_0 = this.state_0_;
            if (arguments0Value instanceof BigInt) {
                BigInt arguments0Value_2 = (BigInt)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.bigIntToLocaleString(arguments0Value_2, arguments1Value, arguments2Value);
            }
            if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSBigInt(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.jsBigIntToLocaleString(arguments0Value_, arguments1Value, arguments2Value);
            }
            this.state_0_ = state_0 |= 4;
            return this.failForNonBigInts(arguments0Value, arguments1Value, arguments2Value);
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
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "bigIntToLocaleString";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "jsBigIntToLocaleString";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "failForNonBigInts";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        private static boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            JSDynamicObject arguments0Value_;
            if ((state_0 & 1) == 0 && arguments0Value instanceof BigInt) {
                return false;
            }
            return !(arguments0Value instanceof JSDynamicObject) || !JSGuards.isJSBigInt(arguments0Value_ = (JSDynamicObject)arguments0Value);
        }

        public static BigIntPrototypeBuiltins.JSBigIntToLocaleStringIntlNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSBigIntToLocaleStringIntlNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=BigIntPrototypeBuiltins.JSBigIntToStringNode.class)
    public static final class JSBigIntToStringNodeGen
    extends BigIntPrototypeBuiltins.JSBigIntToStringNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSBigIntToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 0xF) != 0) {
                TruffleObject arguments0Value__;
                if ((state_0 & 3) != 0 && arguments0Value_ instanceof BigInt) {
                    arguments0Value__ = (BigInt)arguments0Value_;
                    if ((state_0 & 1) != 0 && JSGuards.isUndefined(arguments1Value_)) {
                        return this.toStringBigIntRadix10((BigInt)arguments0Value__, arguments1Value_);
                    }
                    if ((state_0 & 2) != 0 && !JSGuards.isUndefined(arguments1Value_)) {
                        return this.toStringBigInt((BigInt)arguments0Value__, arguments1Value_);
                    }
                }
                if ((state_0 & 0xC) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                    arguments0Value__ = (JSDynamicObject)arguments0Value_;
                    if ((state_0 & 4) != 0 && JSGuards.isJSBigInt(arguments0Value__) && JSGuards.isUndefined(arguments1Value_)) {
                        return this.toStringRadix10((JSDynamicObject)arguments0Value__, arguments1Value_);
                    }
                    if ((state_0 & 8) != 0 && JSGuards.isJSBigInt(arguments0Value__) && !JSGuards.isUndefined(arguments1Value_)) {
                        return this.toString((JSDynamicObject)arguments0Value__, arguments1Value_);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            if ((state_0 & 0xF) != 0) {
                this.execute(frameValue);
                return;
            }
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 0x10) != 0 && JSBigIntToStringNodeGen.fallbackGuard_(state_0, arguments0Value_, arguments1Value_)) {
                this.toStringNoBigInt(arguments0Value_, arguments1Value_);
                return;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        private TruffleString executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            TruffleObject arguments0Value_;
            int state_0 = this.state_0_;
            if (arguments0Value instanceof BigInt) {
                arguments0Value_ = (BigInt)arguments0Value;
                if (JSGuards.isUndefined(arguments1Value)) {
                    this.state_0_ = state_0 |= 1;
                    return this.toStringBigIntRadix10((BigInt)arguments0Value_, arguments1Value);
                }
                if (!JSGuards.isUndefined(arguments1Value)) {
                    this.state_0_ = state_0 |= 2;
                    return this.toStringBigInt((BigInt)arguments0Value_, arguments1Value);
                }
            }
            if (arguments0Value instanceof JSDynamicObject) {
                arguments0Value_ = (JSDynamicObject)arguments0Value;
                if (JSGuards.isJSBigInt(arguments0Value_) && JSGuards.isUndefined(arguments1Value)) {
                    this.state_0_ = state_0 |= 4;
                    return this.toStringRadix10((JSDynamicObject)arguments0Value_, arguments1Value);
                }
                if (JSGuards.isJSBigInt(arguments0Value_) && !JSGuards.isUndefined(arguments1Value)) {
                    this.state_0_ = state_0 |= 8;
                    return this.toString((JSDynamicObject)arguments0Value_, arguments1Value);
                }
            }
            this.state_0_ = state_0 |= 0x10;
            this.toStringNoBigInt(arguments0Value, arguments1Value);
            return null;
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
            Object[] data = new Object[6];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "toStringBigIntRadix10";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "toStringBigInt";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "toStringRadix10";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            s = new Object[3];
            s[0] = "toString";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            s = new Object[3];
            s[0] = "toStringNoBigInt";
            s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[5] = s;
            return Introspection.Provider.create(data);
        }

        private static boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value) {
            if (arguments0Value instanceof BigInt) {
                if ((state_0 & 1) == 0 && JSGuards.isUndefined(arguments1Value)) {
                    return false;
                }
                if ((state_0 & 2) == 0 && !JSGuards.isUndefined(arguments1Value)) {
                    return false;
                }
            }
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                if (JSGuards.isJSBigInt(arguments0Value_) && JSGuards.isUndefined(arguments1Value)) {
                    return false;
                }
                arguments0Value_ = (JSDynamicObject)arguments0Value;
                if (JSGuards.isJSBigInt(arguments0Value_) && !JSGuards.isUndefined(arguments1Value)) {
                    return false;
                }
            }
            return true;
        }

        public static BigIntPrototypeBuiltins.JSBigIntToStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSBigIntToStringNodeGen(context, builtin, arguments);
        }
    }
}

