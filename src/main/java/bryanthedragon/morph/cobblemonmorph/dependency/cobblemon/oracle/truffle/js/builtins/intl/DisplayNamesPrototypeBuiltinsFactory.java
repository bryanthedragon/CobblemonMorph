package com.oracle.truffle.js.builtins.intl;

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
import com.oracle.truffle.js.runtime.builtins.intl.JSDisplayNamesObject;

@GeneratedBy(DisplayNamesPrototypeBuiltins.class)
public final class DisplayNamesPrototypeBuiltinsFactory {
   @GeneratedBy(DisplayNamesPrototypeBuiltins.JSDisplayNamesOfNode.class)
   public static final class JSDisplayNamesOfNodeGen extends DisplayNamesPrototypeBuiltins.JSDisplayNamesOfNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSDisplayNamesOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDisplayNamesObject) {
               JSDisplayNamesObject arguments0Value__ = (JSDisplayNamesObject)arguments0Value_;
               return this.doDisplayNames(arguments0Value__, arguments1Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSDisplayNames(arguments0Value_)) {
               return this.doOther(arguments0Value_, arguments1Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSDisplayNamesObject) {
            JSDisplayNamesObject arguments0Value_ = (JSDisplayNamesObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.doDisplayNames(arguments0Value_, arguments1Value);
         } else if (!JSGuards.isJSDisplayNames(arguments0Value)) {
            int var5;
            this.state_0_ = var5 = state_0 | 2;
            return this.doOther(arguments0Value, arguments1Value);
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
         Object[] s = new Object[]{"doDisplayNames", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doOther", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static DisplayNamesPrototypeBuiltins.JSDisplayNamesOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DisplayNamesPrototypeBuiltinsFactory.JSDisplayNamesOfNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DisplayNamesPrototypeBuiltins.JSDisplayNamesResolvedOptionsNode.class)
   public static final class JSDisplayNamesResolvedOptionsNodeGen
      extends DisplayNamesPrototypeBuiltins.JSDisplayNamesResolvedOptionsNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSDisplayNamesResolvedOptionsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDisplayNamesObject) {
            JSDisplayNamesObject arguments0Value__ = (JSDisplayNamesObject)arguments0Value_;
            return this.doDisplayNames(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isJSDisplayNames(arguments0Value_)) {
            return this.doOther(arguments0Value_);
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
         if (arguments0Value instanceof JSDisplayNamesObject) {
            JSDisplayNamesObject arguments0Value_ = (JSDisplayNamesObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.doDisplayNames(arguments0Value_);
         } else if (!JSGuards.isJSDisplayNames(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.doOther(arguments0Value);
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
         Object[] s = new Object[]{"doDisplayNames", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doOther", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static DisplayNamesPrototypeBuiltins.JSDisplayNamesResolvedOptionsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DisplayNamesPrototypeBuiltinsFactory.JSDisplayNamesResolvedOptionsNodeGen(context, builtin, arguments);
      }
   }
}
