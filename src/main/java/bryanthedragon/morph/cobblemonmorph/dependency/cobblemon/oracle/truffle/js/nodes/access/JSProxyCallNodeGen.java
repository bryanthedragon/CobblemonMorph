package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.nodes.unary.IsConstructorNode;
import com.oracle.truffle.js.runtime.JSContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSProxyCallNode.class)
public final class JSProxyCallNodeGen extends JSProxyCallNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private IsCallableNode call_isCallable_;
   @Node.Child
   private IsConstructorNode construct_isConstructor_;

   private JSProxyCallNodeGen(JSContext context, boolean isNew, boolean isNewTarget) {
      super(context, isNew, isNewTarget);
   }

   @Override
   public Object execute(Object[] arg0Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0) {
            assert !this.isNew;

            assert !this.isNewTarget;

            return this.doCall(arg0Value, this.call_isCallable_);
         }

         if ((state_0 & 2) != 0) {
            assert this.isNew || this.isNewTarget;

            return this.doConstruct(arg0Value, this.construct_isConstructor_);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value);
   }

   private Object executeAndSpecialize(Object[] arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      Object var5;
      try {
         int state_0 = this.state_0_;
         if (this.isNew || this.isNewTarget) {
            if (!this.isNew && !this.isNewTarget) {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }

            this.construct_isConstructor_ = super.insert(IsConstructorNode.create());
            int var10;
            this.state_0_ = var10 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doConstruct(arg0Value, this.construct_isConstructor_);
         }

         this.call_isCallable_ = super.insert(IsCallableNode.create());
         int var9;
         this.state_0_ = var9 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var5 = this.doCall(arg0Value, this.call_isCallable_);
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return var5;
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[]{0, null, null};
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"doCall", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.call_isCallable_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doConstruct", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.construct_isConstructor_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      return Introspection.Provider.create(data);
   }

   public static JSProxyCallNode create(JSContext context, boolean isNew, boolean isNewTarget) {
      return new JSProxyCallNodeGen(context, isNew, isNewTarget);
   }
}
