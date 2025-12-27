package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;

@GeneratedBy(JSEnqueueJobNode.class)
public final class JSEnqueueJobNodeGen extends JSEnqueueJobNode implements Introspection.Provider {
   @Node.Child
   private JavaScriptNode function_;
   @CompilerDirectives.CompilationFinal
   private int state_0_;

   private JSEnqueueJobNodeGen(JSContext context, JavaScriptNode function) {
      super(context);
      this.function_ = function;
   }

   @Override
   JavaScriptNode getFunction() {
      return this.function_;
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      Object functionValue_ = this.function_.execute(frameValue);
      if (state_0 != 0 && functionValue_ instanceof JSFunctionObject) {
         JSFunctionObject functionValue__ = (JSFunctionObject)functionValue_;
         return this.doOther(functionValue__);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(functionValue_);
      }
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      this.execute(frameValue);
   }

   private Object executeAndSpecialize(Object functionValue) {
      int state_0 = this.state_0_;
      if (functionValue instanceof JSFunctionObject) {
         JSFunctionObject functionValue_ = (JSFunctionObject)functionValue;
         int var4;
         this.state_0_ = var4 = state_0 | 1;
         return this.doOther(functionValue_);
      } else {
         throw new UnsupportedSpecializationException(this, new Node[]{this.function_}, functionValue);
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
      Object[] s = new Object[]{"doOther", null, null};
      if (state_0 != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static JSEnqueueJobNode create(JSContext context, JavaScriptNode function) {
      return new JSEnqueueJobNodeGen(context, function);
   }
}
