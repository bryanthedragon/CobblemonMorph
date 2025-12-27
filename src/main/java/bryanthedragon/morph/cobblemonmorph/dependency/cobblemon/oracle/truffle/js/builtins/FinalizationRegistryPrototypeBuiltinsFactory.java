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
import com.oracle.truffle.js.runtime.builtins.JSFinalizationRegistryObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GeneratedBy(FinalizationRegistryPrototypeBuiltins.class)
public final class FinalizationRegistryPrototypeBuiltinsFactory {
   @GeneratedBy(FinalizationRegistryPrototypeBuiltins.JSFinalizationRegistryCleanupSomeNode.class)
   public static final class JSFinalizationRegistryCleanupSomeNodeGen
      extends FinalizationRegistryPrototypeBuiltins.JSFinalizationRegistryCleanupSomeNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSFinalizationRegistryCleanupSomeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSFinalizationRegistryObject) {
               JSFinalizationRegistryObject arguments0Value__ = (JSFinalizationRegistryObject)arguments0Value_;
               return this.cleanupSome(arguments0Value__, arguments1Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSFinalizationRegistry(arguments0Value_)) {
               return FinalizationRegistryPrototypeBuiltins.JSFinalizationRegistryCleanupSomeNode.notFinalizationRegistry(arguments0Value_, arguments1Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSFinalizationRegistryObject) {
            JSFinalizationRegistryObject arguments0Value_ = (JSFinalizationRegistryObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.cleanupSome(arguments0Value_, arguments1Value);
         } else if (!JSGuards.isJSFinalizationRegistry(arguments0Value)) {
            int var5;
            this.state_0_ = var5 = state_0 | 2;
            return FinalizationRegistryPrototypeBuiltins.JSFinalizationRegistryCleanupSomeNode.notFinalizationRegistry(arguments0Value, arguments1Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
         Object[] s = new Object[]{"cleanupSome", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"notFinalizationRegistry", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static FinalizationRegistryPrototypeBuiltins.JSFinalizationRegistryCleanupSomeNode create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new FinalizationRegistryPrototypeBuiltinsFactory.JSFinalizationRegistryCleanupSomeNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(FinalizationRegistryPrototypeBuiltins.JSFinalizationRegistryRegisterNode.class)
   public static final class JSFinalizationRegistryRegisterNodeGen
      extends FinalizationRegistryPrototypeBuiltins.JSFinalizationRegistryRegisterNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @Node.Child
      private JavaScriptNode arguments3_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSFinalizationRegistryRegisterNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
         this.arguments3_ = arguments != null && 3 < arguments.length ? arguments[3] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         Object arguments3Value_ = this.arguments3_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSFinalizationRegistryObject) {
               JSFinalizationRegistryObject arguments0Value__ = (JSFinalizationRegistryObject)arguments0Value_;
               return this.register(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSFinalizationRegistry(arguments0Value_)) {
               return FinalizationRegistryPrototypeBuiltins.JSFinalizationRegistryRegisterNode.notFinalizationRegistry(
                  arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value, Object arguments3Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSFinalizationRegistryObject) {
            JSFinalizationRegistryObject arguments0Value_ = (JSFinalizationRegistryObject)arguments0Value;
            int var8;
            this.state_0_ = var8 = state_0 | 1;
            return this.register(arguments0Value_, arguments1Value, arguments2Value, arguments3Value);
         } else if (!JSGuards.isJSFinalizationRegistry(arguments0Value)) {
            int var7;
            this.state_0_ = var7 = state_0 | 2;
            return FinalizationRegistryPrototypeBuiltins.JSFinalizationRegistryRegisterNode.notFinalizationRegistry(
               arguments0Value, arguments1Value, arguments2Value, arguments3Value
            );
         } else {
            throw new UnsupportedSpecializationException(
               this,
               new Node[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_},
               arguments0Value,
               arguments1Value,
               arguments2Value,
               arguments3Value
            );
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
         Object[] s = new Object[]{"register", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"notFinalizationRegistry", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static FinalizationRegistryPrototypeBuiltins.JSFinalizationRegistryRegisterNode create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new FinalizationRegistryPrototypeBuiltinsFactory.JSFinalizationRegistryRegisterNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(FinalizationRegistryPrototypeBuiltins.JSFinalizationRegistryUnregisterNode.class)
   public static final class JSFinalizationRegistryUnregisterNodeGen
      extends FinalizationRegistryPrototypeBuiltins.JSFinalizationRegistryUnregisterNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSFinalizationRegistryUnregisterNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSFinalizationRegistryObject) {
               JSFinalizationRegistryObject arguments0Value__ = (JSFinalizationRegistryObject)arguments0Value_;
               return this.unregister(arguments0Value__, arguments1Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSFinalizationRegistry(arguments0Value_)) {
               return FinalizationRegistryPrototypeBuiltins.JSFinalizationRegistryUnregisterNode.notFinalizationRegistry(arguments0Value_, arguments1Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSFinalizationRegistryObject) {
               JSFinalizationRegistryObject arguments0Value__ = (JSFinalizationRegistryObject)arguments0Value_;
               return this.unregister(arguments0Value__, arguments1Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSFinalizationRegistry(arguments0Value_)) {
               return FinalizationRegistryPrototypeBuiltins.JSFinalizationRegistryUnregisterNode.notFinalizationRegistry(arguments0Value_, arguments1Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      private boolean executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSFinalizationRegistryObject) {
            JSFinalizationRegistryObject arguments0Value_ = (JSFinalizationRegistryObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.unregister(arguments0Value_, arguments1Value);
         } else if (!JSGuards.isJSFinalizationRegistry(arguments0Value)) {
            int var5;
            this.state_0_ = var5 = state_0 | 2;
            return FinalizationRegistryPrototypeBuiltins.JSFinalizationRegistryUnregisterNode.notFinalizationRegistry(arguments0Value, arguments1Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
         Object[] s = new Object[]{"unregister", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"notFinalizationRegistry", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static FinalizationRegistryPrototypeBuiltins.JSFinalizationRegistryUnregisterNode create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new FinalizationRegistryPrototypeBuiltinsFactory.JSFinalizationRegistryUnregisterNodeGen(context, builtin, arguments);
      }
   }
}
