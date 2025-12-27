package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

@GeneratedBy(HypotNode.class)
public final class HypotNodeGen extends HypotNode implements Introspection.Provider {
   @Node.Child
   private JavaScriptNode arguments0_;
   @CompilerDirectives.CompilationFinal
   private int state_0_;

   private HypotNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
      if (state_0 != 0 && arguments0Value_ instanceof Object[]) {
         Object[] arguments0Value__ = (Object[])arguments0Value_;
         return this.hypot(arguments0Value__);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_);
      }
   }

   @Override
   public double executeDouble(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      Object arguments0Value_ = this.arguments0_.execute(frameValue);
      if (state_0 != 0 && arguments0Value_ instanceof Object[]) {
         Object[] arguments0Value__ = (Object[])arguments0Value_;
         return this.hypot(arguments0Value__);
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
      if (arguments0Value instanceof Object[]) {
         Object[] arguments0Value_ = (Object[])arguments0Value;
         int var4;
         this.state_0_ = var4 = state_0 | 1;
         return this.hypot(arguments0Value_);
      } else {
         throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
      }
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
      Object[] s = new Object[]{"hypot", null, null};
      if (state_0 != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static HypotNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
      return new HypotNodeGen(context, builtin, arguments);
   }
}
