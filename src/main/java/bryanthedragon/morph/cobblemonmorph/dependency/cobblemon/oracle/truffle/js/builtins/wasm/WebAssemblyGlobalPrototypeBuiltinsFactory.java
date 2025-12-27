package com.oracle.truffle.js.builtins.wasm;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

@GeneratedBy(WebAssemblyGlobalPrototypeBuiltins.class)
public final class WebAssemblyGlobalPrototypeBuiltinsFactory {
   @GeneratedBy(WebAssemblyGlobalPrototypeBuiltins.WebAssemblyGlobalValueOfNode.class)
   public static final class WebAssemblyGlobalValueOfNodeGen
      extends WebAssemblyGlobalPrototypeBuiltins.WebAssemblyGlobalValueOfNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private WebAssemblyGlobalValueOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.valueOf(arguments0Value_);
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
         Object[] s = new Object[]{"valueOf", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static WebAssemblyGlobalPrototypeBuiltins.WebAssemblyGlobalValueOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new WebAssemblyGlobalPrototypeBuiltinsFactory.WebAssemblyGlobalValueOfNodeGen(context, builtin, arguments);
      }
   }
}
