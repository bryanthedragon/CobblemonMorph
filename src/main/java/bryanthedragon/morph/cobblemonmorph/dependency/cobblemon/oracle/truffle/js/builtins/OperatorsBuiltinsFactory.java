package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.OperatorSet;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(OperatorsBuiltins.class)
public final class OperatorsBuiltinsFactory {
   private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);

   @GeneratedBy(OperatorsBuiltins.ConstructOperatorSetNode.class)
   public static final class ConstructOperatorSetNodeGen extends OperatorsBuiltins.ConstructOperatorSetNode implements Introspection.Provider {
      private ConstructOperatorSetNodeGen(JSContext context) {
         super(context);
      }

      @Override
      public OperatorSet execute(Object arg0Value, Object[] arg1Value) {
         return this.construct(arg0Value, arg1Value);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"construct", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static OperatorsBuiltins.ConstructOperatorSetNode create(JSContext context) {
         return new OperatorsBuiltinsFactory.ConstructOperatorSetNodeGen(context);
      }
   }

   @GeneratedBy(OperatorsBuiltins.CreateOverloadedOperatorsObjectNode.class)
   public static final class CreateOverloadedOperatorsObjectNodeGen
      extends OperatorsBuiltins.CreateOverloadedOperatorsObjectNode
      implements Introspection.Provider {
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private OperatorsBuiltinsFactory.CreateOverloadedOperatorsObjectNodeGen.CachedProtoData cachedProto_cache;
      @Node.Child
      private DynamicObjectLibrary createWithProto_setProtoNode_;
      @CompilerDirectives.CompilationFinal
      private Shape createWithProto_cachedShape_;
      @CompilerDirectives.CompilationFinal
      private Shape createDefaultProto_cachedShape_;

      private CreateOverloadedOperatorsObjectNodeGen(JSContext context, OperatorSet operatorSet) {
         super(context, operatorSet);
      }

      @ExplodeLoop
      @Override
      protected JSOverloadedOperatorsObject execute(Object arg0Value) {
         int state_0 = this.state_0_;
         if ((state_0 & 1) != 0) {
            assert !this.getContext().isMultiContext();

            for (OperatorsBuiltinsFactory.CreateOverloadedOperatorsObjectNodeGen.CachedProtoData s0_ = this.cachedProto_cache; s0_ != null; s0_ = s0_.next_) {
               if (arg0Value == s0_.cachedPrototype_) {
                  assert JSGuards.isJSObject(s0_.cachedPrototype_);

                  return this.doCachedProto(arg0Value, s0_.cachedPrototype_, s0_.cachedShape_);
               }
            }
         }

         if ((state_0 & 2) != 0 && arg0Value instanceof JSObject) {
            JSObject arg0Value_ = (JSObject)arg0Value;
            return this.createWithProto(arg0Value_, this.createWithProto_setProtoNode_, this.createWithProto_cachedShape_);
         } else if ((state_0 & 4) != 0 && !JSGuards.isJSObject(arg0Value)) {
            return this.createDefaultProto(arg0Value, this.createDefaultProto_cachedShape_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
         }
      }

      private JSOverloadedOperatorsObject executeAndSpecialize(Object arg0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (!this.getContext().isMultiContext()) {
               int count0_ = 0;
               OperatorsBuiltinsFactory.CreateOverloadedOperatorsObjectNodeGen.CachedProtoData s0_ = this.cachedProto_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null) {
                     if (arg0Value == s0_.cachedPrototype_) {
                        assert JSGuards.isJSObject(s0_.cachedPrototype_);
                        break;
                     }

                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null && JSGuards.isJSObject(arg0Value) && count0_ < this.getContext().getPropertyCacheLimit()) {
                  s0_ = new OperatorsBuiltinsFactory.CreateOverloadedOperatorsObjectNodeGen.CachedProtoData(this.cachedProto_cache);
                  s0_.cachedPrototype_ = arg0Value;
                  s0_.cachedShape_ = this.getProtoChildShape(arg0Value);
                  VarHandle.storeStoreFence();
                  this.cachedProto_cache = s0_;
                  this.state_0_ = state_0 |= 1;
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.doCachedProto(arg0Value, s0_.cachedPrototype_, s0_.cachedShape_);
               }
            }

            if (arg0Value instanceof JSObject) {
               JSObject arg0Value_ = (JSObject)arg0Value;
               this.createWithProto_setProtoNode_ = super.insert(OperatorsBuiltinsFactory.DYNAMIC_OBJECT_LIBRARY_.createDispatched(3));
               this.createWithProto_cachedShape_ = this.getShapeWithoutProto();
               int var11;
               this.state_0_ = var11 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.createWithProto(arg0Value_, this.createWithProto_setProtoNode_, this.createWithProto_cachedShape_);
            } else if (JSGuards.isJSObject(arg0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            } else {
               this.createDefaultProto_cachedShape_ = this.getShapeWithDefaultProto(this.getRealm());
               int var12;
               this.state_0_ = var12 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.createDefaultProto(arg0Value, this.createDefaultProto_cachedShape_);
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
               OperatorsBuiltinsFactory.CreateOverloadedOperatorsObjectNodeGen.CachedProtoData s0_ = this.cachedProto_cache;
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
         Object[] s = new Object[]{"doCachedProto", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (OperatorsBuiltinsFactory.CreateOverloadedOperatorsObjectNodeGen.CachedProtoData s0_ = this.cachedProto_cache; s0_ != null; s0_ = s0_.next_) {
               cached.add(Arrays.asList(s0_.cachedPrototype_, s0_.cachedShape_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"createWithProto", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.createWithProto_setProtoNode_, this.createWithProto_cachedShape_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"createDefaultProto", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.createDefaultProto_cachedShape_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static OperatorsBuiltins.CreateOverloadedOperatorsObjectNode create(JSContext context, OperatorSet operatorSet) {
         return new OperatorsBuiltinsFactory.CreateOverloadedOperatorsObjectNodeGen(context, operatorSet);
      }

      @GeneratedBy(OperatorsBuiltins.CreateOverloadedOperatorsObjectNode.class)
      private static final class CachedProtoData {
         @CompilerDirectives.CompilationFinal
         OperatorsBuiltinsFactory.CreateOverloadedOperatorsObjectNodeGen.CachedProtoData next_;
         @CompilerDirectives.CompilationFinal
         Object cachedPrototype_;
         @CompilerDirectives.CompilationFinal
         Shape cachedShape_;

         CachedProtoData(OperatorsBuiltinsFactory.CreateOverloadedOperatorsObjectNodeGen.CachedProtoData next_) {
            this.next_ = next_;
         }
      }
   }

   @GeneratedBy(OperatorsBuiltins.OperatorsNode.class)
   public static final class OperatorsNodeGen extends OperatorsBuiltins.OperatorsNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private OperatorsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
            Object[] arguments1Value__ = (Object[])arguments1Value_;
            return this.doOperators(frameValue, arguments0Value_, arguments1Value__);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(frameValue, arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(VirtualFrame frameValue, Object arguments0Value, Object arguments1Value) {
         int state_0 = this.state_0_;
         if (arguments1Value instanceof Object[]) {
            Object[] arguments1Value_ = (Object[])arguments1Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.doOperators(frameValue, arguments0Value, arguments1Value_);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
         Object[] s = new Object[]{"doOperators", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static OperatorsBuiltins.OperatorsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new OperatorsBuiltinsFactory.OperatorsNodeGen(context, builtin, arguments);
      }
   }
}
