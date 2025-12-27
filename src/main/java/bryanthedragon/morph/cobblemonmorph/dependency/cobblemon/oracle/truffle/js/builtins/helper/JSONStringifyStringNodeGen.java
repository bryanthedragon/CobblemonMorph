package com.oracle.truffle.js.builtins.helper;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GeneratedBy(JSONStringifyStringNode.class)
public final class JSONStringifyStringNodeGen extends JSONStringifyStringNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private int state_0_;

   private JSONStringifyStringNodeGen(JSContext context) {
      super(context);
   }

   @Override
   public Object execute(Object arg0Value, Object arg1Value, JSDynamicObject arg2Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0 && arg1Value instanceof TruffleString) {
         TruffleString arg1Value_ = (TruffleString)arg1Value;
         return this.jsonStrMain(arg0Value, arg1Value_, arg2Value);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
      }
   }

   private Object executeAndSpecialize(Object arg0Value, Object arg1Value, JSDynamicObject arg2Value) {
      int state_0 = this.state_0_;
      if (arg1Value instanceof TruffleString) {
         TruffleString arg1Value_ = (TruffleString)arg1Value;
         int var6;
         this.state_0_ = var6 = state_0 | 1;
         return this.jsonStrMain(arg0Value, arg1Value_, arg2Value);
      } else {
         throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
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
      Object[] s = new Object[]{"jsonStrMain", null, null};
      if (state_0 != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static JSONStringifyStringNode create(JSContext context) {
      return new JSONStringifyStringNodeGen(context);
   }
}
