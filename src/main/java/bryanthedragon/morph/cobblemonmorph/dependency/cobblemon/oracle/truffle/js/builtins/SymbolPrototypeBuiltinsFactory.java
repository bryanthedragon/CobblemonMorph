package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

@GeneratedBy(SymbolPrototypeBuiltins.class)
public final class SymbolPrototypeBuiltinsFactory {
   @GeneratedBy(SymbolPrototypeBuiltins.SymbolToPrimitiveNode.class)
   public static final class SymbolToPrimitiveNodeGen extends SymbolPrototypeBuiltins.SymbolToPrimitiveNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private SymbolToPrimitiveNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.toPrimitive(arguments0Value_);
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
         Object[] s = new Object[]{"toPrimitive", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static SymbolPrototypeBuiltins.SymbolToPrimitiveNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new SymbolPrototypeBuiltinsFactory.SymbolToPrimitiveNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(SymbolPrototypeBuiltins.SymbolToStringNode.class)
   public static final class SymbolToStringNodeGen extends SymbolPrototypeBuiltins.SymbolToStringNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private SymbolToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.toString(arguments0Value_);
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
         Object[] s = new Object[]{"toString", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static SymbolPrototypeBuiltins.SymbolToStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new SymbolPrototypeBuiltinsFactory.SymbolToStringNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(SymbolPrototypeBuiltins.SymbolValueOfNode.class)
   public static final class SymbolValueOfNodeGen extends SymbolPrototypeBuiltins.SymbolValueOfNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private SymbolValueOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

      public static SymbolPrototypeBuiltins.SymbolValueOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new SymbolPrototypeBuiltinsFactory.SymbolValueOfNodeGen(context, builtin, arguments);
      }
   }
}
