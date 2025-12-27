package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

@GeneratedBy(Atan2Node.class)
public final class Atan2NodeGen extends Atan2Node implements Introspection.Provider {
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
      return (state_0 & 2) == 0 && (state_0 & 3) != 0 ? this.execute_double_double0(state_0, frameValue) : this.execute_generic1(state_0, frameValue);
   }

   private Object execute_double_double0(int state_0, VirtualFrame frameValue) {
      long arguments0Value_long = 0L;
      int arguments0Value_int = 0;

      double arguments0Value_;
      try {
         if ((state_0 & 56) == 0 && (state_0 & 3) != 0) {
            arguments0Value_ = this.arguments0_.executeDouble(frameValue);
         } else if ((state_0 & 52) == 0 && (state_0 & 3) != 0) {
            arguments0Value_int = this.arguments0_.executeInt(frameValue);
            arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
         } else if ((state_0 & 28) == 0 && (state_0 & 3) != 0) {
            arguments0Value_long = this.arguments0_.executeLong(frameValue);
            arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
         } else {
            Object arguments0Value__ = this.arguments0_.execute(frameValue);
            arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 60) >>> 2, arguments0Value__);
         }
      } catch (UnexpectedResultException var15) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object arguments1Value = this.arguments1_.execute(frameValue);
         return this.executeAndSpecialize(var15.getResult(), arguments1Value);
      }

      long arguments1Value_long = 0L;
      int arguments1Value_int = 0;

      double arguments1Value_;
      try {
         if ((state_0 & 896) == 0 && (state_0 & 3) != 0) {
            arguments1Value_ = this.arguments1_.executeDouble(frameValue);
         } else if ((state_0 & 832) == 0 && (state_0 & 3) != 0) {
            arguments1Value_int = this.arguments1_.executeInt(frameValue);
            arguments1Value_ = JSTypes.intToDouble(arguments1Value_int);
         } else if ((state_0 & 448) == 0 && (state_0 & 3) != 0) {
            arguments1Value_long = this.arguments1_.executeLong(frameValue);
            arguments1Value_ = JSTypes.longToDouble(arguments1Value_long);
         } else {
            Object arguments1Value__ = this.arguments1_.execute(frameValue);
            arguments1Value_ = JSTypesGen.expectImplicitDouble((state_0 & 960) >>> 6, arguments1Value__);
         }
      } catch (UnexpectedResultException var14) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(
            (state_0 & 52) == 0 && (state_0 & 3) != 0
               ? arguments0Value_int
               : ((state_0 & 28) == 0 && (state_0 & 3) != 0 ? arguments0Value_long : arguments0Value_),
            var14.getResult()
         );
      }

      assert (state_0 & 1) != 0;

      return Atan2Node.atan2Double(arguments0Value_, arguments1Value_);
   }

   private Object execute_generic1(int state_0, VirtualFrame frameValue) {
      Object arguments0Value_ = this.arguments0_.execute(frameValue);
      Object arguments1Value_ = this.arguments1_.execute(frameValue);
      if ((state_0 & 1) != 0 && JSTypesGen.isImplicitDouble((state_0 & 60) >>> 2, arguments0Value_)) {
         double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 60) >>> 2, arguments0Value_);
         if (JSTypesGen.isImplicitDouble((state_0 & 960) >>> 6, arguments1Value_)) {
            double arguments1Value__ = JSTypesGen.asImplicitDouble((state_0 & 960) >>> 6, arguments1Value_);
            return Atan2Node.atan2Double(arguments0Value__, arguments1Value__);
         }
      }

      if ((state_0 & 2) != 0) {
         return this.atan2Generic(arguments0Value_, arguments1Value_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }
   }

   @Override
   public double executeDouble(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      return (state_0 & 2) == 0 && (state_0 & 3) != 0
         ? this.executeDouble_double_double2(state_0, frameValue)
         : this.executeDouble_generic3(state_0, frameValue);
   }

   private double executeDouble_double_double2(int state_0, VirtualFrame frameValue) {
      long arguments0Value_long = 0L;
      int arguments0Value_int = 0;

      double arguments0Value_;
      try {
         if ((state_0 & 56) == 0 && (state_0 & 3) != 0) {
            arguments0Value_ = this.arguments0_.executeDouble(frameValue);
         } else if ((state_0 & 52) == 0 && (state_0 & 3) != 0) {
            arguments0Value_int = this.arguments0_.executeInt(frameValue);
            arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
         } else if ((state_0 & 28) == 0 && (state_0 & 3) != 0) {
            arguments0Value_long = this.arguments0_.executeLong(frameValue);
            arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
         } else {
            Object arguments0Value__ = this.arguments0_.execute(frameValue);
            arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 60) >>> 2, arguments0Value__);
         }
      } catch (UnexpectedResultException var15) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object arguments1Value = this.arguments1_.execute(frameValue);
         return this.executeAndSpecialize(var15.getResult(), arguments1Value);
      }

      long arguments1Value_long = 0L;
      int arguments1Value_int = 0;

      double arguments1Value_;
      try {
         if ((state_0 & 896) == 0 && (state_0 & 3) != 0) {
            arguments1Value_ = this.arguments1_.executeDouble(frameValue);
         } else if ((state_0 & 832) == 0 && (state_0 & 3) != 0) {
            arguments1Value_int = this.arguments1_.executeInt(frameValue);
            arguments1Value_ = JSTypes.intToDouble(arguments1Value_int);
         } else if ((state_0 & 448) == 0 && (state_0 & 3) != 0) {
            arguments1Value_long = this.arguments1_.executeLong(frameValue);
            arguments1Value_ = JSTypes.longToDouble(arguments1Value_long);
         } else {
            Object arguments1Value__ = this.arguments1_.execute(frameValue);
            arguments1Value_ = JSTypesGen.expectImplicitDouble((state_0 & 960) >>> 6, arguments1Value__);
         }
      } catch (UnexpectedResultException var14) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(
            (state_0 & 52) == 0 && (state_0 & 3) != 0
               ? arguments0Value_int
               : ((state_0 & 28) == 0 && (state_0 & 3) != 0 ? arguments0Value_long : arguments0Value_),
            var14.getResult()
         );
      }

      assert (state_0 & 1) != 0;

      return Atan2Node.atan2Double(arguments0Value_, arguments1Value_);
   }

   private double executeDouble_generic3(int state_0, VirtualFrame frameValue) {
      Object arguments0Value_ = this.arguments0_.execute(frameValue);
      Object arguments1Value_ = this.arguments1_.execute(frameValue);
      if ((state_0 & 1) != 0 && JSTypesGen.isImplicitDouble((state_0 & 60) >>> 2, arguments0Value_)) {
         double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 60) >>> 2, arguments0Value_);
         if (JSTypesGen.isImplicitDouble((state_0 & 960) >>> 6, arguments1Value_)) {
            double arguments1Value__ = JSTypesGen.asImplicitDouble((state_0 & 960) >>> 6, arguments1Value_);
            return Atan2Node.atan2Double(arguments0Value__, arguments1Value__);
         }
      }

      if ((state_0 & 2) != 0) {
         return this.atan2Generic(arguments0Value_, arguments1Value_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      this.executeDouble(frameValue);
   }

   private double executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
      int state_0 = this.state_0_;
      int doubleCast0;
      if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arguments0Value)) != 0) {
         double arguments0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arguments0Value);
         int doubleCast1;
         if ((doubleCast1 = JSTypesGen.specializeImplicitDouble(arguments1Value)) != 0) {
            double arguments1Value_ = JSTypesGen.asImplicitDouble(doubleCast1, arguments1Value);
            state_0 |= doubleCast0 << 2;
            state_0 |= doubleCast1 << 6;
            int var13;
            this.state_0_ = var13 = state_0 | 1;
            return Atan2Node.atan2Double(arguments0Value_, arguments1Value_);
         }
      }

      int var10;
      this.state_0_ = var10 = state_0 | 2;
      return this.atan2Generic(arguments0Value, arguments1Value);
   }

   @Override
   public NodeCost getCost() {
      int state_0 = this.state_0_;
      if ((state_0 & 3) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & 3 & (state_0 & 3) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[]{0, null, null};
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"atan2Double", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"atan2Generic", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      return Introspection.Provider.create(data);
   }

   public static Atan2Node create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
      return new Atan2NodeGen(context, builtin, arguments);
   }
}
