package com.oracle.truffle.js.builtins.wasm;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

@GeneratedBy(WebAssemblyTablePrototypeBuiltins.class)
public final class WebAssemblyTablePrototypeBuiltinsFactory {
   @GeneratedBy(WebAssemblyTablePrototypeBuiltins.WebAssemblyTableGetNode.class)
   public static final class WebAssemblyTableGetNodeGen extends WebAssemblyTablePrototypeBuiltins.WebAssemblyTableGetNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private WebAssemblyTableGetNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.get(arguments0Value_, arguments1Value_);
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
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"get", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static WebAssemblyTablePrototypeBuiltins.WebAssemblyTableGetNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new WebAssemblyTablePrototypeBuiltinsFactory.WebAssemblyTableGetNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(WebAssemblyTablePrototypeBuiltins.WebAssemblyTableGrowNode.class)
   public static final class WebAssemblyTableGrowNodeGen extends WebAssemblyTablePrototypeBuiltins.WebAssemblyTableGrowNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private WebAssemblyTableGrowNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.grow(arguments0Value_, arguments1Value_);
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
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"grow", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static WebAssemblyTablePrototypeBuiltins.WebAssemblyTableGrowNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new WebAssemblyTablePrototypeBuiltinsFactory.WebAssemblyTableGrowNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(WebAssemblyTablePrototypeBuiltins.WebAssemblyTableSetNode.class)
   public static final class WebAssemblyTableSetNodeGen extends WebAssemblyTablePrototypeBuiltins.WebAssemblyTableSetNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;

      private WebAssemblyTableSetNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.set(arguments0Value_, arguments1Value_, arguments2Value_);
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
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"set", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static WebAssemblyTablePrototypeBuiltins.WebAssemblyTableSetNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new WebAssemblyTablePrototypeBuiltinsFactory.WebAssemblyTableSetNodeGen(context, builtin, arguments);
      }
   }
}
