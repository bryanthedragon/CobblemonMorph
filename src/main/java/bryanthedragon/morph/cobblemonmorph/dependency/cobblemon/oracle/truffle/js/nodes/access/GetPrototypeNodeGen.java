package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.Location;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(GetPrototypeNode.class)
public final class GetPrototypeNodeGen extends GetPrototypeNode implements Introspection.Provider {
   private static final GetPrototypeNodeGen.Uncached UNCACHED = new GetPrototypeNodeGen.Uncached();
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private GetPrototypeNodeGen.CachedShapeData cachedShape_cache;
   @CompilerDirectives.CompilationFinal
   private JSClassProfile proxy_jsclassProfile_;

   private GetPrototypeNodeGen() {
   }

   @ExplodeLoop
   @Override
   public JSDynamicObject execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 7) != 0 && arg0Value instanceof JSDynamicObject) {
         JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
         if ((state_0 & 1) != 0) {
            for (GetPrototypeNodeGen.CachedShapeData s0_ = this.cachedShape_cache; s0_ != null; s0_ = s0_.next_) {
               if (arg0Value_.getShape() == s0_.shape_) {
                  assert s0_.prototypeLocation_ != null;

                  return GetPrototypeNode.doCachedShape(arg0Value_, s0_.shape_, s0_.prototypeLocation_);
               }
            }
         }

         if ((state_0 & 2) != 0 && !JSGuards.isJSProxy(arg0Value_)) {
            return GetPrototypeNode.doGeneric(arg0Value_);
         }

         if ((state_0 & 4) != 0 && JSGuards.isJSProxy(arg0Value_)) {
            return GetPrototypeNode.doProxy(arg0Value_, this.proxy_jsclassProfile_);
         }
      }

      if ((state_0 & 8) != 0 && !JSGuards.isJSDynamicObject(arg0Value)) {
         return GetPrototypeNode.doNotObject(arg0Value);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   @ExplodeLoop
   @Override
   public JSDynamicObject execute(JSDynamicObject arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 7) != 0) {
         if ((state_0 & 1) != 0) {
            for (GetPrototypeNodeGen.CachedShapeData s0_ = this.cachedShape_cache; s0_ != null; s0_ = s0_.next_) {
               if (arg0Value.getShape() == s0_.shape_) {
                  assert s0_.prototypeLocation_ != null;

                  return GetPrototypeNode.doCachedShape(arg0Value, s0_.shape_, s0_.prototypeLocation_);
               }
            }
         }

         if ((state_0 & 2) != 0 && !JSGuards.isJSProxy(arg0Value)) {
            return GetPrototypeNode.doGeneric(arg0Value);
         }

         if ((state_0 & 4) != 0 && JSGuards.isJSProxy(arg0Value)) {
            return GetPrototypeNode.doProxy(arg0Value, this.proxy_jsclassProfile_);
         }
      }

      if ((state_0 & 8) != 0 && !JSGuards.isJSDynamicObject(arg0Value)) {
         return GetPrototypeNode.doNotObject(arg0Value);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   private JSDynamicObject executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            if (exclude == 0) {
               int count0_ = 0;
               GetPrototypeNodeGen.CachedShapeData s0_ = this.cachedShape_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null) {
                     if (arg0Value_.getShape() == s0_.shape_) {
                        assert s0_.prototypeLocation_ != null;
                        break;
                     }

                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null) {
                  Shape shape__ = arg0Value_.getShape();
                  if (arg0Value_.getShape() == shape__) {
                     Location prototypeLocation__ = GetPrototypeNode.getPrototypeLocation(shape__);
                     if (prototypeLocation__ != null && count0_ < 2) {
                        s0_ = new GetPrototypeNodeGen.CachedShapeData(this.cachedShape_cache);
                        s0_.shape_ = shape__;
                        s0_.prototypeLocation_ = prototypeLocation__;
                        VarHandle.storeStoreFence();
                        this.cachedShape_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                     }
                  }
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return GetPrototypeNode.doCachedShape(arg0Value_, s0_.shape_, s0_.prototypeLocation_);
               }
            }

            if (!JSGuards.isJSProxy(arg0Value_)) {
               int var18;
               this.exclude_ = var18 = exclude | 1;
               this.cachedShape_cache = null;
               state_0 &= -2;
               int var17;
               this.state_0_ = var17 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return GetPrototypeNode.doGeneric(arg0Value_);
            }

            if (JSGuards.isJSProxy(arg0Value_)) {
               this.proxy_jsclassProfile_ = JSClassProfile.create();
               int var15;
               this.state_0_ = var15 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return GetPrototypeNode.doProxy(arg0Value_, this.proxy_jsclassProfile_);
            }
         }

         if (JSGuards.isJSDynamicObject(arg0Value)) {
            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
         } else {
            int var14;
            this.state_0_ = var14 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return GetPrototypeNode.doNotObject(arg0Value);
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
            GetPrototypeNodeGen.CachedShapeData s0_ = this.cachedShape_cache;
            if (s0_ == null || s0_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[5];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doCachedShape", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (GetPrototypeNodeGen.CachedShapeData s0_ = this.cachedShape_cache; s0_ != null; s0_ = s0_.next_) {
            cached.add(Arrays.asList(s0_.shape_, s0_.prototypeLocation_));
         }

         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doGeneric", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doProxy", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.proxy_jsclassProfile_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doNotObject", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      return Introspection.Provider.create(data);
   }

   public static GetPrototypeNode create() {
      return new GetPrototypeNodeGen();
   }

   public static GetPrototypeNode getUncached() {
      return UNCACHED;
   }

   @GeneratedBy(GetPrototypeNode.class)
   private static final class CachedShapeData {
      @CompilerDirectives.CompilationFinal
      GetPrototypeNodeGen.CachedShapeData next_;
      @CompilerDirectives.CompilationFinal
      Shape shape_;
      @CompilerDirectives.CompilationFinal
      Location prototypeLocation_;

      CachedShapeData(GetPrototypeNodeGen.CachedShapeData next_) {
         this.next_ = next_;
      }
   }

   @GeneratedBy(GetPrototypeNode.class)
   @DenyReplace
   private static final class Uncached extends GetPrototypeNode {
      @CompilerDirectives.TruffleBoundary
      @Override
      public JSDynamicObject execute(Object arg0Value) {
         if (arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            if (!JSGuards.isJSProxy(arg0Value_)) {
               return GetPrototypeNode.doGeneric(arg0Value_);
            }

            if (JSGuards.isJSProxy(arg0Value_)) {
               return GetPrototypeNode.doProxy(arg0Value_, JSClassProfile.getUncached());
            }
         }

         if (!JSGuards.isJSDynamicObject(arg0Value)) {
            return GetPrototypeNode.doNotObject(arg0Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
         }
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public JSDynamicObject execute(JSDynamicObject arg0Value) {
         if (!JSGuards.isJSProxy(arg0Value)) {
            return GetPrototypeNode.doGeneric(arg0Value);
         } else if (JSGuards.isJSProxy(arg0Value)) {
            return GetPrototypeNode.doProxy(arg0Value, JSClassProfile.getUncached());
         } else if (!JSGuards.isJSDynamicObject(arg0Value)) {
            return GetPrototypeNode.doNotObject(arg0Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
         }
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MEGAMORPHIC;
      }

      @Override
      public boolean isAdoptable() {
         return false;
      }
   }
}
