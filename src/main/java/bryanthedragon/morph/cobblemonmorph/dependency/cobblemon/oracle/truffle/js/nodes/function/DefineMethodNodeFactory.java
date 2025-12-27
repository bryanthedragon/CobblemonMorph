package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionFactory;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(DefineMethodNode.class)
public final class DefineMethodNodeFactory {
   @GeneratedBy(DefineMethodNode.FunctionCreateNode.class)
   protected static final class FunctionCreateNodeGen extends DefineMethodNode.FunctionCreateNode implements Introspection.Provider {
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @CompilerDirectives.CompilationFinal
      private DefineMethodNodeFactory.FunctionCreateNodeGen.CachedData cached_cache;
      @CompilerDirectives.CompilationFinal
      private JSFunctionFactory multiContext_factory_;

      private FunctionCreateNodeGen(JSContext context, JSFunctionData functionData, int blockScopeSlot) {
         super(context, functionData, blockScopeSlot);
      }

      @ExplodeLoop
      @Override
      public JSFunctionObject executeWithPrototype(VirtualFrame frameValue, Object arg0Value) {
         int state_0 = this.state_0_;
         if ((state_0 & 7) != 0 && arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            if ((state_0 & 1) != 0) {
               assert !this.getContext().isMultiContext();

               for (DefineMethodNodeFactory.FunctionCreateNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
                  if (arg0Value_ == s0_.cachedPrototype_) {
                     assert JSGuards.isJSObject(s0_.cachedPrototype_);

                     return this.doCached(frameValue, arg0Value_, s0_.cachedPrototype_, s0_.factory_);
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               assert !this.getContext().isMultiContext();

               if (JSGuards.isJSObject(arg0Value_)) {
                  return this.doUncached(frameValue, arg0Value_);
               }
            }

            if ((state_0 & 4) != 0) {
               assert this.getContext().isMultiContext();

               if (JSGuards.isJSObject(arg0Value_)) {
                  return this.doMultiContext(frameValue, arg0Value_, this.multiContext_factory_);
               }
            }
         }

         if ((state_0 & 8) != 0 && !JSGuards.isJSObject(arg0Value)) {
            return this.doNonObject(arg0Value);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(frameValue, arg0Value);
         }
      }

      private JSFunctionObject executeAndSpecialize(VirtualFrame frameValue, Object arg0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arg0Value instanceof JSDynamicObject) {
               JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
               if (exclude == 0 && !this.getContext().isMultiContext()) {
                  int count0_ = 0;
                  DefineMethodNodeFactory.FunctionCreateNodeGen.CachedData s0_ = this.cached_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null) {
                        if (arg0Value_ == s0_.cachedPrototype_) {
                           assert JSGuards.isJSObject(s0_.cachedPrototype_);
                           break;
                        }

                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && JSGuards.isJSObject(arg0Value_) && count0_ < this.getContext().getPropertyCacheLimit()) {
                     s0_ = new DefineMethodNodeFactory.FunctionCreateNodeGen.CachedData(this.cached_cache);
                     s0_.cachedPrototype_ = arg0Value_;
                     s0_.factory_ = this.makeFactory(arg0Value_);
                     VarHandle.storeStoreFence();
                     this.cached_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doCached(frameValue, arg0Value_, s0_.cachedPrototype_, s0_.factory_);
                  }
               }

               if (!this.getContext().isMultiContext() && JSGuards.isJSObject(arg0Value_)) {
                  int var18;
                  this.exclude_ = var18 = exclude | 1;
                  this.cached_cache = null;
                  state_0 &= -2;
                  int var17;
                  this.state_0_ = var17 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.doUncached(frameValue, arg0Value_);
               }

               if (this.getContext().isMultiContext() && JSGuards.isJSObject(arg0Value_)) {
                  this.multiContext_factory_ = this.makeFactoryMultiContext();
                  int var15;
                  this.state_0_ = var15 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return this.doMultiContext(frameValue, arg0Value_, this.multiContext_factory_);
               }
            }

            if (JSGuards.isJSObject(arg0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            } else {
               int var14;
               this.state_0_ = var14 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.doNonObject(arg0Value);
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
               DefineMethodNodeFactory.FunctionCreateNodeGen.CachedData s0_ = this.cached_cache;
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
         Object[] s = new Object[]{"doCached", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (DefineMethodNodeFactory.FunctionCreateNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
               cached.add(Arrays.asList(s0_.cachedPrototype_, s0_.factory_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
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
         s = new Object[]{"doMultiContext", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.multiContext_factory_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"doNonObject", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         return Introspection.Provider.create(data);
      }

      public static DefineMethodNode.FunctionCreateNode create(JSContext context, JSFunctionData functionData, int blockScopeSlot) {
         return new DefineMethodNodeFactory.FunctionCreateNodeGen(context, functionData, blockScopeSlot);
      }

      @GeneratedBy(DefineMethodNode.FunctionCreateNode.class)
      private static final class CachedData {
         @CompilerDirectives.CompilationFinal
         DefineMethodNodeFactory.FunctionCreateNodeGen.CachedData next_;
         @CompilerDirectives.CompilationFinal
         JSDynamicObject cachedPrototype_;
         @CompilerDirectives.CompilationFinal
         JSFunctionFactory factory_;

         CachedData(DefineMethodNodeFactory.FunctionCreateNodeGen.CachedData next_) {
            this.next_ = next_;
         }
      }
   }
}
