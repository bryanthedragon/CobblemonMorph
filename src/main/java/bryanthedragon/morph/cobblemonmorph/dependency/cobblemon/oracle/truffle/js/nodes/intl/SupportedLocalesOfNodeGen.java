package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(SupportedLocalesOfNode.class)
public final class SupportedLocalesOfNodeGen extends SupportedLocalesOfNode implements Introspection.Provider {
   @Node.Child
   private JavaScriptNode arguments0_;
   @Node.Child
   private JavaScriptNode arguments1_;
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private JSToObjectNode getSupportedLocalesWithOptions_toObjectNode_;
   @Node.Child
   private GetStringOptionNode getSupportedLocalesWithOptions_getMatcherNode_;

   private SupportedLocalesOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && JSGuards.isUndefined(arguments1Value_)) {
            return this.getSupportedLocales(arguments0Value_, arguments1Value_);
         }

         if ((state_0 & 2) != 0 && !JSGuards.isUndefined(arguments1Value_)) {
            return this.getSupportedLocalesWithOptions(
               arguments0Value_, arguments1Value_, this.getSupportedLocalesWithOptions_toObjectNode_, this.getSupportedLocalesWithOptions_getMatcherNode_
            );
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
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      Object var6;
      try {
         int state_0 = this.state_0_;
         if (!JSGuards.isUndefined(arguments1Value)) {
            if (JSGuards.isUndefined(arguments1Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
            }

            this.getSupportedLocalesWithOptions_toObjectNode_ = super.insert(JSToObjectNode.createToObject(this.getContext()));
            this.getSupportedLocalesWithOptions_getMatcherNode_ = super.insert(SupportedLocalesOfNode.createMatcherGetter(this.getContext()));
            int var11;
            this.state_0_ = var11 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.getSupportedLocalesWithOptions(
               arguments0Value, arguments1Value, this.getSupportedLocalesWithOptions_toObjectNode_, this.getSupportedLocalesWithOptions_getMatcherNode_
            );
         }

         int var10;
         this.state_0_ = var10 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var6 = this.getSupportedLocales(arguments0Value, arguments1Value);
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return var6;
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
      Object[] s = new Object[]{"getSupportedLocales", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"getSupportedLocalesWithOptions", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.getSupportedLocalesWithOptions_toObjectNode_, this.getSupportedLocalesWithOptions_getMatcherNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      return Introspection.Provider.create(data);
   }

   public static SupportedLocalesOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
      return new SupportedLocalesOfNodeGen(context, builtin, arguments);
   }
}
