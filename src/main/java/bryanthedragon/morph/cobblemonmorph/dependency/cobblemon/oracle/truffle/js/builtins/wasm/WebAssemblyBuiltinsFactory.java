package com.oracle.truffle.js.builtins.wasm;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

@GeneratedBy(WebAssemblyBuiltins.class)
public final class WebAssemblyBuiltinsFactory {
   @GeneratedBy(WebAssemblyBuiltins.WebAssemblyCompileNode.class)
   public static final class WebAssemblyCompileNodeGen extends WebAssemblyBuiltins.WebAssemblyCompileNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private WebAssemblyCompileNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.compile(arguments0Value_);
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
         Object[] s = new Object[]{"compile", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static WebAssemblyBuiltins.WebAssemblyCompileNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new WebAssemblyBuiltinsFactory.WebAssemblyCompileNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(WebAssemblyBuiltins.WebAssemblyInstantiateNode.class)
   public static final class WebAssemblyInstantiateNodeGen extends WebAssemblyBuiltins.WebAssemblyInstantiateNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private WebAssemblyInstantiateNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.instantiate(arguments0Value_, arguments1Value_);
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
         Object[] s = new Object[]{"instantiate", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static WebAssemblyBuiltins.WebAssemblyInstantiateNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new WebAssemblyBuiltinsFactory.WebAssemblyInstantiateNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(WebAssemblyBuiltins.WebAssemblyValidateNode.class)
   public static final class WebAssemblyValidateNodeGen extends WebAssemblyBuiltins.WebAssemblyValidateNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private WebAssemblyValidateNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.validate(arguments0Value_);
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
         Object[] s = new Object[]{"validate", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static WebAssemblyBuiltins.WebAssemblyValidateNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new WebAssemblyBuiltinsFactory.WebAssemblyValidateNodeGen(context, builtin, arguments);
      }
   }
}
