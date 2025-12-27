package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(IsRegExpNode.class)
public final class IsRegExpNodeGen extends IsRegExpNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private IsRegExpNodeGen.IsObjectData isObject_cache;

   private IsRegExpNodeGen(JSContext context) {
      super(context);
   }

   @Override
   public boolean executeBoolean(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof JSDynamicObject) {
         JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
         IsRegExpNodeGen.IsObjectData s0_ = this.isObject_cache;
         if (s0_ != null) {
            return this.doIsObject(arg0Value_, s0_.isObjectNode_, s0_.toBooleanNode_, s0_.isJSRegExpNode_, s0_.hasMatchSymbol_);
         }
      }

      if ((state_0 & 2) != 0 && fallbackGuard_(state_0, arg0Value)) {
         return this.doNonObject(arg0Value);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   private boolean executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      boolean var7;
      try {
         int state_0 = this.state_0_;
         if (!(arg0Value instanceof JSDynamicObject)) {
            int var12;
            this.state_0_ = var12 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doNonObject(arg0Value);
         }

         JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
         IsRegExpNodeGen.IsObjectData s0_ = super.insert(new IsRegExpNodeGen.IsObjectData());
         s0_.isObjectNode_ = s0_.insertAccessor(IsJSObjectNode.create());
         s0_.toBooleanNode_ = s0_.insertAccessor(JSToBooleanNode.create());
         s0_.isJSRegExpNode_ = s0_.insertAccessor(IsRegExpNode.createIsJSRegExpNode());
         s0_.hasMatchSymbol_ = ConditionProfile.createBinaryProfile();
         VarHandle.storeStoreFence();
         this.isObject_cache = s0_;
         int var11;
         this.state_0_ = var11 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var7 = this.doIsObject(arg0Value_, s0_.isObjectNode_, s0_.toBooleanNode_, s0_.isJSRegExpNode_, s0_.hasMatchSymbol_);
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return var7;
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
      Object[] s = new Object[]{"doIsObject", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         IsRegExpNodeGen.IsObjectData s0_ = this.isObject_cache;
         if (s0_ != null) {
            cached.add(Arrays.asList(s0_.isObjectNode_, s0_.toBooleanNode_, s0_.isJSRegExpNode_, s0_.hasMatchSymbol_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doNonObject", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      return Introspection.Provider.create(data);
   }

   private static boolean fallbackGuard_(int state_0, Object arg0Value) {
      return (state_0 & 1) != 0 || !(arg0Value instanceof JSDynamicObject);
   }

   public static IsRegExpNode create(JSContext context) {
      return new IsRegExpNodeGen(context);
   }

   @GeneratedBy(IsRegExpNode.class)
   private static final class IsObjectData extends Node {
      @Node.Child
      IsJSObjectNode isObjectNode_;
      @Node.Child
      JSToBooleanNode toBooleanNode_;
      @Node.Child
      IsJSClassNode isJSRegExpNode_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile hasMatchSymbol_;

      IsObjectData() {
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
