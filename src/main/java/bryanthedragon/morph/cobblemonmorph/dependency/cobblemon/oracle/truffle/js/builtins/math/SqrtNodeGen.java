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

@GeneratedBy(SqrtNode.class)
public final class SqrtNodeGen extends SqrtNode implements Introspection.Provider {
   @Node.Child
   private JavaScriptNode arguments0_;
   @CompilerDirectives.CompilationFinal
   private int state_0_;

   private SqrtNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
      return (state_0 & 2) == 0 && (state_0 & 3) != 0 ? this.execute_double0(state_0, frameValue) : this.execute_generic1(state_0, frameValue);
   }

   private Object execute_double0(int state_0, VirtualFrame frameValue) {
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
      } catch (UnexpectedResultException var9) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(var9.getResult());
      }

      assert (state_0 & 1) != 0;

      return SqrtNode.sqrtDouble(arguments0Value_);
   }

   private Object execute_generic1(int state_0, VirtualFrame frameValue) {
      Object arguments0Value_ = this.arguments0_.execute(frameValue);
      if ((state_0 & 1) != 0 && JSTypesGen.isImplicitDouble((state_0 & 60) >>> 2, arguments0Value_)) {
         double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 60) >>> 2, arguments0Value_);
         return SqrtNode.sqrtDouble(arguments0Value__);
      } else if ((state_0 & 2) != 0) {
         return this.sqrtGeneric(arguments0Value_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_);
      }
   }

   @Override
   public double executeDouble(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      return (state_0 & 2) == 0 && (state_0 & 3) != 0 ? this.executeDouble_double2(state_0, frameValue) : this.executeDouble_generic3(state_0, frameValue);
   }

   private double executeDouble_double2(int state_0, VirtualFrame frameValue) {
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
      } catch (UnexpectedResultException var9) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(var9.getResult());
      }

      assert (state_0 & 1) != 0;

      return SqrtNode.sqrtDouble(arguments0Value_);
   }

   private double executeDouble_generic3(int state_0, VirtualFrame frameValue) {
      Object arguments0Value_ = this.arguments0_.execute(frameValue);
      if ((state_0 & 1) != 0 && JSTypesGen.isImplicitDouble((state_0 & 60) >>> 2, arguments0Value_)) {
         double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 60) >>> 2, arguments0Value_);
         return SqrtNode.sqrtDouble(arguments0Value__);
      } else if ((state_0 & 2) != 0) {
         return this.sqrtGeneric(arguments0Value_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_);
      }
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      this.executeDouble(frameValue);
   }

   private double executeAndSpecialize(Object arguments0Value) {
      int state_0 = this.state_0_;
      int doubleCast0;
      if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arguments0Value)) != 0) {
         double arguments0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arguments0Value);
         state_0 |= doubleCast0 << 2;
         int var8;
         this.state_0_ = var8 = state_0 | 1;
         return SqrtNode.sqrtDouble(arguments0Value_);
      } else {
         int var6;
         this.state_0_ = var6 = state_0 | 2;
         return this.sqrtGeneric(arguments0Value);
      }
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
      Object[] s = new Object[]{"sqrtDouble", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"sqrtGeneric", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      return Introspection.Provider.create(data);
   }

   public static SqrtNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
      return new SqrtNodeGen(context, builtin, arguments);
   }
}
