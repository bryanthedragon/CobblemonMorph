package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(CreateDataPropertyNode.class)
public final class CreateDataPropertyNodeGen extends CreateDataPropertyNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private PropertySetNode cached_propertyCache_;

   private CreateDataPropertyNodeGen(JSContext context, Object key, boolean enumerable) {
      super(context, key, enumerable);
   }

   @Override
   public void executeVoid(Object arg0Value, Object arg1Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0) {
            assert this.context.getPropertyCacheLimit() > 0;

            if (this.isObject.executeBoolean(arg0Value)) {
               CreateDataPropertyNode.doCached(arg0Value, arg1Value, this.cached_propertyCache_);
               return;
            }
         }

         if ((state_0 & 2) != 0 && arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;

            assert this.context.getPropertyCacheLimit() == 0;

            if (JSGuards.isJSObject(arg0Value_)) {
               this.doUncached(arg0Value_, arg1Value);
               return;
            }
         }

         if ((state_0 & 4) != 0 && !JSGuards.isJSObject(arg0Value)) {
            this.doNonObject(arg0Value, arg1Value);
            return;
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      this.executeAndSpecialize(arg0Value, arg1Value);
   }

   private void executeAndSpecialize(Object arg0Value, Object arg1Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         if (this.context.getPropertyCacheLimit() > 0 && this.isObject.executeBoolean(arg0Value)) {
            this.cached_propertyCache_ = super.insert(this.makeDefinePropertyCache());
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            CreateDataPropertyNode.doCached(arg0Value, arg1Value, this.cached_propertyCache_);
         } else {
            if (arg0Value instanceof JSDynamicObject) {
               JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
               if (this.context.getPropertyCacheLimit() == 0 && JSGuards.isJSObject(arg0Value_)) {
                  int var11;
                  this.state_0_ = var11 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  this.doUncached(arg0Value_, arg1Value);
                  return;
               }
            }

            if (JSGuards.isJSObject(arg0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            } else {
               int var10;
               this.state_0_ = var10 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               this.doNonObject(arg0Value, arg1Value);
            }
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
         return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[4];
      data[0] = 0;
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"doCached", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.cached_propertyCache_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doUncached", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doNonObject", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      return Introspection.Provider.create(data);
   }

   public static CreateDataPropertyNode create(JSContext context, Object key, boolean enumerable) {
      return new CreateDataPropertyNodeGen(context, key, enumerable);
   }
}
