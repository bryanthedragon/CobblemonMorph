package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Symbol;

@GeneratedBy(SymbolFunctionBuiltins.class)
public final class SymbolFunctionBuiltinsFactory {
   @GeneratedBy(SymbolFunctionBuiltins.SymbolForNode.class)
   public static final class SymbolForNodeGen extends SymbolFunctionBuiltins.SymbolForNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private SymbolForNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.symbolFor(arguments0Value_);
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
         Object[] s = new Object[]{"symbolFor", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static SymbolFunctionBuiltins.SymbolForNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new SymbolFunctionBuiltinsFactory.SymbolForNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(SymbolFunctionBuiltins.SymbolKeyForNode.class)
   public static final class SymbolKeyForNodeGen extends SymbolFunctionBuiltins.SymbolKeyForNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private SymbolKeyForNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof Symbol) {
            Symbol arguments0Value__ = (Symbol)arguments0Value_;
            if (JSGuards.isSymbol(arguments0Value__)) {
               return this.symbolKeyFor(arguments0Value__);
            }
         }

         if ((state_0 & 2) != 0 && !JSGuards.isSymbol(arguments0Value_)) {
            return SymbolFunctionBuiltins.SymbolKeyForNode.valueOf(arguments0Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof Symbol) {
            Symbol arguments0Value_ = (Symbol)arguments0Value;
            if (JSGuards.isSymbol(arguments0Value_)) {
               int var5;
               this.state_0_ = var5 = state_0 | 1;
               return this.symbolKeyFor(arguments0Value_);
            }
         }

         if (!JSGuards.isSymbol(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return SymbolFunctionBuiltins.SymbolKeyForNode.valueOf(arguments0Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"symbolKeyFor", null, null};
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
         return Introspection.Provider.create(data);
      }

      public static SymbolFunctionBuiltins.SymbolKeyForNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new SymbolFunctionBuiltinsFactory.SymbolKeyForNodeGen(context, builtin, arguments);
      }
   }
}
