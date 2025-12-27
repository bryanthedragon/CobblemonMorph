package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.cast.JSToPropertyKeyNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSProxyPropertyGetNode.class)
public final class JSProxyPropertyGetNodeGen extends JSProxyPropertyGetNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private JSProxyPropertyGetNodeGen.GenericData generic_cache;

   private JSProxyPropertyGetNodeGen(JSContext context) {
      super(context);
   }

   @Override
   public Object executeWithReceiver(Object arg0Value, Object arg1Value, Object arg2Value, Object arg3Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0 && arg0Value instanceof JSDynamicObject) {
         JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
         JSProxyPropertyGetNodeGen.GenericData s0_ = this.generic_cache;
         if (s0_ != null) {
            return this.doGeneric(arg0Value_, arg1Value, arg2Value, arg3Value, s0_.toPropertyKeyNode_, s0_.hasTrap_, s0_.targetClassProfile_);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
   }

   private Object executeAndSpecialize(Object arg0Value, Object arg1Value, Object arg2Value, Object arg3Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      Object var10;
      try {
         int state_0 = this.state_0_;
         if (!(arg0Value instanceof JSDynamicObject)) {
            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value);
         }

         JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
         JSProxyPropertyGetNodeGen.GenericData s0_ = super.insert(new JSProxyPropertyGetNodeGen.GenericData());
         s0_.toPropertyKeyNode_ = s0_.insertAccessor(JSToPropertyKeyNode.create());
         s0_.hasTrap_ = ConditionProfile.createBinaryProfile();
         s0_.targetClassProfile_ = JSClassProfile.create();
         VarHandle.storeStoreFence();
         this.generic_cache = s0_;
         int var14;
         this.state_0_ = var14 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var10 = this.doGeneric(arg0Value_, arg1Value, arg2Value, arg3Value, s0_.toPropertyKeyNode_, s0_.hasTrap_, s0_.targetClassProfile_);
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return var10;
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[]{0, null};
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"doGeneric", null, null};
      if (state_0 != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         JSProxyPropertyGetNodeGen.GenericData s0_ = this.generic_cache;
         if (s0_ != null) {
            cached.add(Arrays.asList(s0_.toPropertyKeyNode_, s0_.hasTrap_, s0_.targetClassProfile_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static JSProxyPropertyGetNode create(JSContext context) {
      return new JSProxyPropertyGetNodeGen(context);
   }

   @GeneratedBy(JSProxyPropertyGetNode.class)
   private static final class GenericData extends Node {
      @Node.Child
      JSToPropertyKeyNode toPropertyKeyNode_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile hasTrap_;
      @CompilerDirectives.CompilationFinal
      JSClassProfile targetClassProfile_;

      GenericData() {
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T insertAccessor(T node) {
         return super.insert(node);
      }
   }
}
