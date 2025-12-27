package com.oracle.truffle.js.builtins.wasm;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

@GeneratedBy(WebAssemblyMemoryPrototypeBuiltins.class)
public final class WebAssemblyMemoryPrototypeBuiltinsFactory {
   @GeneratedBy(WebAssemblyMemoryPrototypeBuiltins.WebAssemblyMemoryGrowNode.class)
   public static final class WebAssemblyMemoryGrowNodeGen
      extends WebAssemblyMemoryPrototypeBuiltins.WebAssemblyMemoryGrowNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private WebAssemblyMemoryGrowNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

      public static WebAssemblyMemoryPrototypeBuiltins.WebAssemblyMemoryGrowNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new WebAssemblyMemoryPrototypeBuiltinsFactory.WebAssemblyMemoryGrowNodeGen(context, builtin, arguments);
      }
   }
}
