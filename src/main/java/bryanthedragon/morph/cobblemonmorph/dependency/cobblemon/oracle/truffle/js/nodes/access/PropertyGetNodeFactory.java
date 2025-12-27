package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(PropertyGetNode.class)
public final class PropertyGetNodeFactory {
   @GeneratedBy(PropertyGetNode.GetPropertyFromJSObjectNode.class)
   static final class GetPropertyFromJSObjectNodeGen extends PropertyGetNode.GetPropertyFromJSObjectNode implements Introspection.Provider {
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @CompilerDirectives.CompilationFinal
      private PropertyGetNodeFactory.GetPropertyFromJSObjectNodeGen.JSObjectCachedData jSObjectCached_cache;
      @Node.Child
      private JSHasPropertyNode required_hasPropertyNode_;
      @CompilerDirectives.CompilationFinal
      private JSClassProfile required_classProfile_;

      private GetPropertyFromJSObjectNodeGen(PropertyGetNode root) {
         super(root);
      }

      @ExplodeLoop
      @Override
      public Object executeWithJSObject(JSDynamicObject arg0Value, Object arg1Value, Object arg2Value, PropertyGetNode arg3Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               assert !this.isGlobal();

               for (PropertyGetNodeFactory.GetPropertyFromJSObjectNodeGen.JSObjectCachedData s0_ = this.jSObjectCached_cache; s0_ != null; s0_ = s0_.next_) {
                  if (s0_.cachedClass_ == this.getJSClass(arg0Value)) {
                     return this.doJSObjectCached(arg0Value, arg1Value, arg2Value, arg3Value, s0_.cachedClass_);
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               assert !this.isGlobal();

               return this.doJSObjectDirect(arg0Value, arg1Value, arg2Value, arg3Value);
            }

            if ((state_0 & 4) != 0) {
               assert this.isGlobal();

               return this.doRequired(arg0Value, arg1Value, arg2Value, arg3Value, this.required_hasPropertyNode_, this.required_classProfile_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
      }

      private Object executeAndSpecialize(JSDynamicObject arg0Value, Object arg1Value, Object arg2Value, PropertyGetNode arg3Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0 && !this.isGlobal()) {
               int count0_ = 0;
               PropertyGetNodeFactory.GetPropertyFromJSObjectNodeGen.JSObjectCachedData s0_ = this.jSObjectCached_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && s0_.cachedClass_ != this.getJSClass(arg0Value)) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null) {
                  JSClass cachedClass__ = this.getJSClass(arg0Value);
                  if (cachedClass__ == this.getJSClass(arg0Value) && count0_ < 2) {
                     s0_ = new PropertyGetNodeFactory.GetPropertyFromJSObjectNodeGen.JSObjectCachedData(this.jSObjectCached_cache);
                     s0_.cachedClass_ = cachedClass__;
                     VarHandle.storeStoreFence();
                     this.jSObjectCached_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.doJSObjectCached(arg0Value, arg1Value, arg2Value, arg3Value, s0_.cachedClass_);
               }
            }

            if (!this.isGlobal()) {
               int var18;
               this.exclude_ = var18 = exclude | 1;
               this.jSObjectCached_cache = null;
               state_0 &= -2;
               int var16;
               this.state_0_ = var16 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doJSObjectDirect(arg0Value, arg1Value, arg2Value, arg3Value);
            } else if (!this.isGlobal()) {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value);
            } else {
               this.required_hasPropertyNode_ = super.insert(JSHasPropertyNode.create());
               this.required_classProfile_ = JSClassProfile.create();
               int var17;
               this.state_0_ = var17 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.doRequired(arg0Value, arg1Value, arg2Value, arg3Value, this.required_hasPropertyNode_, this.required_classProfile_);
            }
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            if ((state_0 & state_0 - 1) == 0) {
               PropertyGetNodeFactory.GetPropertyFromJSObjectNodeGen.JSObjectCachedData s0_ = this.jSObjectCached_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[4];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"doJSObjectCached", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (PropertyGetNodeFactory.GetPropertyFromJSObjectNodeGen.JSObjectCachedData s0_ = this.jSObjectCached_cache; s0_ != null; s0_ = s0_.next_) {
               cached.add(Arrays.asList(s0_.cachedClass_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doJSObjectDirect", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"doRequired", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.required_hasPropertyNode_, this.required_classProfile_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static PropertyGetNode.GetPropertyFromJSObjectNode create(PropertyGetNode root) {
         return new PropertyGetNodeFactory.GetPropertyFromJSObjectNodeGen(root);
      }

      @GeneratedBy(PropertyGetNode.GetPropertyFromJSObjectNode.class)
      private static final class JSObjectCachedData {
         @CompilerDirectives.CompilationFinal
         PropertyGetNodeFactory.GetPropertyFromJSObjectNodeGen.JSObjectCachedData next_;
         @CompilerDirectives.CompilationFinal
         JSClass cachedClass_;

         JSObjectCachedData(PropertyGetNodeFactory.GetPropertyFromJSObjectNodeGen.JSObjectCachedData next_) {
            this.next_ = next_;
         }
      }
   }
}
