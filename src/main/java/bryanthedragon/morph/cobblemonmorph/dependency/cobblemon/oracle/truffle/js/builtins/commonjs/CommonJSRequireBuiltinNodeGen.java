package com.oracle.truffle.js.builtins.commonjs;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GeneratedBy(CommonJSRequireBuiltin.class)
public final class CommonJSRequireBuiltinNodeGen extends CommonJSRequireBuiltin implements Introspection.Provider {
   @Node.Child
   private JavaScriptNode arguments0_;
   @Node.Child
   private JavaScriptNode arguments1_;
   @CompilerDirectives.CompilationFinal
   private int state_0_;

   private CommonJSRequireBuiltinNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
      if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
         JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
         if (arguments1Value_ instanceof TruffleString) {
            TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
            return this.require(arguments0Value__, arguments1Value__);
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
      if (arguments0Value instanceof JSDynamicObject) {
         JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
         if (arguments1Value instanceof TruffleString) {
            TruffleString arguments1Value_ = (TruffleString)arguments1Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.require(arguments0Value_, arguments1Value_);
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
      Object[] s = new Object[]{"require", null, null};
      if (state_0 != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static CommonJSRequireBuiltin create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
      return new CommonJSRequireBuiltinNodeGen(context, builtin, arguments);
   }
}
