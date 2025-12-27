package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

@GeneratedBy(ImulNode.class)
public final class ImulNodeGen extends ImulNode implements Introspection.Provider {
   @Node.Child
   private JavaScriptNode arguments0_;
   @Node.Child
   private JavaScriptNode arguments1_;
   @CompilerDirectives.CompilationFinal
   private int state_0_;

   private ImulNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

      int arguments0Value_;
      try {
         arguments0Value_ = this.arguments0_.executeInt(frameValue);
      } catch (UnexpectedResultException var7) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object arguments1Value = this.arguments1_.execute(frameValue);
         return this.executeAndSpecialize(var7.getResult(), arguments1Value);
      }

      int arguments1Value_;
      try {
         arguments1Value_ = this.arguments1_.executeInt(frameValue);
      } catch (UnexpectedResultException var6) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, var6.getResult());
      }

      if (state_0 != 0) {
         return ImulNode.imul(arguments0Value_, arguments1Value_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }
   }

   @Override
   public int executeInt(VirtualFrame frameValue) {
      int state_0 = this.state_0_;

      int arguments0Value_;
      try {
         arguments0Value_ = this.arguments0_.executeInt(frameValue);
      } catch (UnexpectedResultException var7) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object arguments1Value = this.arguments1_.execute(frameValue);
         return this.executeAndSpecialize(var7.getResult(), arguments1Value);
      }

      int arguments1Value_;
      try {
         arguments1Value_ = this.arguments1_.executeInt(frameValue);
      } catch (UnexpectedResultException var6) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, var6.getResult());
      }

      if (state_0 != 0) {
         return ImulNode.imul(arguments0Value_, arguments1Value_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      this.executeInt(frameValue);
   }

   private int executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
      int state_0 = this.state_0_;
      if (arguments0Value instanceof Integer) {
         int arguments0Value_ = (Integer)arguments0Value;
         if (arguments1Value instanceof Integer) {
            int arguments1Value_ = (Integer)arguments1Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return ImulNode.imul(arguments0Value_, arguments1Value_);
         }
      }

      throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
   }

   @Override
   public NodeCost getCost() {
      int state_0 = this.state_0_;
      return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[]{0, null};
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"imul", null, null};
      if (state_0 != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static ImulNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
      return new ImulNodeGen(context, builtin, arguments);
   }
}
