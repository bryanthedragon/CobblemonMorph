package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GeneratedBy(BigIntPrototypeBuiltins.class)
public final class BigIntPrototypeBuiltinsFactory {
   @GeneratedBy(BigIntPrototypeBuiltins.JSBigIntToLocaleStringIntlNode.class)
   public static final class JSBigIntToLocaleStringIntlNodeGen extends BigIntPrototypeBuiltins.JSBigIntToLocaleStringIntlNode implements Introspection.Provider {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof BigInt) {
               BigInt arguments0Value__ = (BigInt)arguments0Value_;
               return this.bigIntToLocaleString(arguments0Value__, arguments1Value_, arguments2Value_);
            }

            if ((state_0 & 2) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               if (JSGuards.isJSBigInt(arguments0Value__)) {
                  return this.jsBigIntToLocaleString(arguments0Value__, arguments1Value_, arguments2Value_);
               }
            }

            if ((state_0 & 4) != 0 && fallbackGuard_(state_0, arguments0Value_, arguments1Value_, arguments2Value_)) {
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
         int state_0 = this.state_0_;
         if (arguments0Value instanceof BigInt) {
            BigInt arguments0Value_ = (BigInt)arguments0Value;
            int var8;
            this.state_0_ = var8 = state_0 | 1;
            return this.bigIntToLocaleString(arguments0Value_, arguments1Value, arguments2Value);
         } else {
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isJSBigInt(arguments0Value_)) {
                  int var7;
                  this.state_0_ = var7 = state_0 | 2;
                  return this.jsBigIntToLocaleString(arguments0Value_, arguments1Value, arguments2Value);
               }
            }

            int var6;
            this.state_0_ = var6 = state_0 | 4;
            return this.failForNonBigInts(arguments0Value, arguments1Value, arguments2Value);
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[4];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"bigIntToLocaleString", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"jsBigIntToLocaleString", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"failForNonBigInts", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      private static boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         if ((state_0 & 1) == 0 && arguments0Value instanceof BigInt) {
            return false;
         } else {
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isJSBigInt(arguments0Value_)) {
                  return false;
               }
            }

            return true;
         }
      }

      public static BigIntPrototypeBuiltins.JSBigIntToLocaleStringIntlNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new BigIntPrototypeBuiltinsFactory.JSBigIntToLocaleStringIntlNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(BigIntPrototypeBuiltins.JSBigIntToLocaleStringNode.class)
   public static final class JSBigIntToLocaleStringNodeGen extends BigIntPrototypeBuiltins.JSBigIntToLocaleStringNode implements Introspection.Provider {
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
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof BigInt) {
            BigInt arguments0Value__ = (BigInt)arguments0Value_;
            return this.toLocaleStringBigInt(arguments0Value__);
         } else {
            if ((state_0 & 2) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               if (JSGuards.isJSBigInt(arguments0Value__)) {
                  return this.toLocaleStringJSBigInt(arguments0Value__);
               }
            }

            if ((state_0 & 4) != 0 && fallbackGuard_(state_0, arguments0Value_)) {
               return this.failForNonBigInts(arguments0Value_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.executeAndSpecialize(arguments0Value_);
            }
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof BigInt) {
            BigInt arguments0Value_ = (BigInt)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.toLocaleStringBigInt(arguments0Value_);
         } else {
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isJSBigInt(arguments0Value_)) {
                  int var5;
                  this.state_0_ = var5 = state_0 | 2;
                  return this.toLocaleStringJSBigInt(arguments0Value_);
               }
            }

            int var4;
            this.state_0_ = var4 = state_0 | 4;
            return this.failForNonBigInts(arguments0Value);
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[4];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"toLocaleStringBigInt", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"toLocaleStringJSBigInt", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"failForNonBigInts", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      private static boolean fallbackGuard_(int state_0, Object arguments0Value) {
         if ((state_0 & 1) == 0 && arguments0Value instanceof BigInt) {
            return false;
         } else {
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isJSBigInt(arguments0Value_)) {
                  return false;
               }
            }

            return true;
         }
      }

      public static BigIntPrototypeBuiltins.JSBigIntToLocaleStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new BigIntPrototypeBuiltinsFactory.JSBigIntToLocaleStringNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(BigIntPrototypeBuiltins.JSBigIntToStringNode.class)
   public static final class JSBigIntToStringNodeGen extends BigIntPrototypeBuiltins.JSBigIntToStringNode implements Introspection.Provider {
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
         if ((state_0 & 15) != 0) {
            if ((state_0 & 3) != 0 && arguments0Value_ instanceof BigInt) {
               BigInt arguments0Value__ = (BigInt)arguments0Value_;
               if ((state_0 & 1) != 0 && JSGuards.isUndefined(arguments1Value_)) {
                  return this.toStringBigIntRadix10(arguments0Value__, arguments1Value_);
               }

               if ((state_0 & 2) != 0 && !JSGuards.isUndefined(arguments1Value_)) {
                  return this.toStringBigInt(arguments0Value__, arguments1Value_);
               }
            }

            if ((state_0 & 12) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__x = (JSDynamicObject)arguments0Value_;
               if ((state_0 & 4) != 0 && JSGuards.isJSBigInt(arguments0Value__x) && JSGuards.isUndefined(arguments1Value_)) {
                  return this.toStringRadix10(arguments0Value__x, arguments1Value_);
               }

               if ((state_0 & 8) != 0 && JSGuards.isJSBigInt(arguments0Value__x) && !JSGuards.isUndefined(arguments1Value_)) {
                  return this.toString(arguments0Value__x, arguments1Value_);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         if ((state_0 & 15) != 0) {
            this.execute(frameValue);
         } else {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 16) != 0 && fallbackGuard_(state_0, arguments0Value_, arguments1Value_)) {
               this.toStringNoBigInt(arguments0Value_, arguments1Value_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.executeAndSpecialize(arguments0Value_, arguments1Value_);
            }
         }
      }

      private TruffleString executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof BigInt) {
            BigInt arguments0Value_ = (BigInt)arguments0Value;
            if (JSGuards.isUndefined(arguments1Value)) {
               int var9;
               this.state_0_ = var9 = state_0 | 1;
               return this.toStringBigIntRadix10(arguments0Value_, arguments1Value);
            }

            if (!JSGuards.isUndefined(arguments1Value)) {
               int var8;
               this.state_0_ = var8 = state_0 | 2;
               return this.toStringBigInt(arguments0Value_, arguments1Value);
            }
         }

         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_x = (JSDynamicObject)arguments0Value;
            if (JSGuards.isJSBigInt(arguments0Value_x) && JSGuards.isUndefined(arguments1Value)) {
               int var7;
               this.state_0_ = var7 = state_0 | 4;
               return this.toStringRadix10(arguments0Value_x, arguments1Value);
            }

            if (JSGuards.isJSBigInt(arguments0Value_x) && !JSGuards.isUndefined(arguments1Value)) {
               int var6;
               this.state_0_ = var6 = state_0 | 8;
               return this.toString(arguments0Value_x, arguments1Value);
            }
         }

         int var5;
         this.state_0_ = var5 = state_0 | 16;
         this.toStringNoBigInt(arguments0Value, arguments1Value);
         return null;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[6];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"toStringBigIntRadix10", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"toStringBigInt", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"toStringRadix10", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"toString", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"toStringNoBigInt", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

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
         return new BigIntPrototypeBuiltinsFactory.JSBigIntToStringNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(BigIntPrototypeBuiltins.JSBigIntValueOfNode.class)
   public static final class JSBigIntValueOfNodeGen extends BigIntPrototypeBuiltins.JSBigIntValueOfNode implements Introspection.Provider {
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
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof BigInt) {
            BigInt arguments0Value__ = (BigInt)arguments0Value_;
            return this.valueOfBigInt(arguments0Value__);
         } else {
            if ((state_0 & 2) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               if (JSGuards.isJSBigInt(arguments0Value__)) {
                  return this.valueOf(arguments0Value__);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         if ((state_0 & 3) != 0) {
            this.execute(frameValue);
         } else {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 4) != 0 && fallbackGuard_(state_0, arguments0Value_)) {
               this.valueOf(arguments0Value_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.executeAndSpecialize(arguments0Value_);
            }
         }
      }

      private BigInt executeAndSpecialize(Object arguments0Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof BigInt) {
            BigInt arguments0Value_ = (BigInt)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.valueOfBigInt(arguments0Value_);
         } else {
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isJSBigInt(arguments0Value_)) {
                  int var5;
                  this.state_0_ = var5 = state_0 | 2;
                  return this.valueOf(arguments0Value_);
               }
            }

            int var4;
            this.state_0_ = var4 = state_0 | 4;
            this.valueOf(arguments0Value);
            return null;
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[4];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"valueOfBigInt", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"valueOf", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"valueOf", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      private static boolean fallbackGuard_(int state_0, Object arguments0Value) {
         if ((state_0 & 1) == 0 && arguments0Value instanceof BigInt) {
            return false;
         } else {
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isJSBigInt(arguments0Value_)) {
                  return false;
               }
            }

            return true;
         }
      }

      public static BigIntPrototypeBuiltins.JSBigIntValueOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new BigIntPrototypeBuiltinsFactory.JSBigIntValueOfNodeGen(context, builtin, arguments);
      }
   }
}
