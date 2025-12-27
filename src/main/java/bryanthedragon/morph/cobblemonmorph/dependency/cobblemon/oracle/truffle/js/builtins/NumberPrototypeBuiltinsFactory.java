package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerAsIntNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSNumberObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(NumberPrototypeBuiltins.class)
public final class NumberPrototypeBuiltinsFactory {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

   @GeneratedBy(NumberPrototypeBuiltins.JSNumberToExponentialNode.class)
   public static final class JSNumberToExponentialNodeGen extends NumberPrototypeBuiltins.JSNumberToExponentialNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @CompilerDirectives.CompilationFinal
      private BranchProfile digitsError;
      @Node.Child
      private JSToIntegerAsIntNode toInt;
      @Node.Child
      private NumberPrototypeBuiltinsFactory.JSNumberToExponentialNodeGen.ToExponentialForeignObjectUndefined0Data toExponentialForeignObjectUndefined0_cache;
      @Node.Child
      private NumberPrototypeBuiltinsFactory.JSNumberToExponentialNodeGen.ToExponentialForeignObject0Data toExponentialForeignObject0_cache;
      @CompilerDirectives.CompilationFinal
      private BranchProfile toExponentialForeignObject1_digitsErrorBranch_;
      @Node.Child
      private JSToIntegerAsIntNode toExponentialForeignObject1_toIntegerNode_;

      private JSNumberToExponentialNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      @ExplodeLoop
      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 3) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               if ((state_0 & 1) != 0 && JSGuards.isJSNumber(arguments0Value__) && JSGuards.isUndefined(arguments1Value_)) {
                  return this.toExponentialUndefined(arguments0Value__, arguments1Value_);
               }

               if ((state_0 & 2) != 0 && JSGuards.isJSNumber(arguments0Value__) && !JSGuards.isUndefined(arguments1Value_)) {
                  return this.toExponential(arguments0Value__, arguments1Value_, this.digitsError, this.toInt);
               }
            }

            if ((state_0 & 508) != 0) {
               if ((state_0 & 4) != 0 && JSGuards.isJavaNumber(arguments0Value_) && JSGuards.isUndefined(arguments1Value_)) {
                  return this.toExponentialPrimitiveUndefined(arguments0Value_, arguments1Value_);
               }

               if ((state_0 & 8) != 0 && JSGuards.isJavaNumber(arguments0Value_) && !JSGuards.isUndefined(arguments1Value_)) {
                  return this.toExponentialPrimitive(arguments0Value_, arguments1Value_, this.digitsError, this.toInt);
               }

               if ((state_0 & 16) != 0) {
                  for (NumberPrototypeBuiltinsFactory.JSNumberToExponentialNodeGen.ToExponentialForeignObjectUndefined0Data s4_ = this.toExponentialForeignObjectUndefined0_cache;
                     s4_ != null;
                     s4_ = s4_.next_
                  ) {
                     if (s4_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_) && JSGuards.isUndefined(arguments1Value_)) {
                        return this.toExponentialForeignObjectUndefined(arguments0Value_, arguments1Value_, s4_.interop_);
                     }
                  }
               }

               if ((state_0 & 32) != 0 && JSGuards.isForeignObject(arguments0Value_) && JSGuards.isUndefined(arguments1Value_)) {
                  return this.toExponentialForeignObjectUndefined1Boundary(state_0, arguments0Value_, arguments1Value_);
               }

               if ((state_0 & 64) != 0) {
                  for (NumberPrototypeBuiltinsFactory.JSNumberToExponentialNodeGen.ToExponentialForeignObject0Data s6_ = this.toExponentialForeignObject0_cache;
                     s6_ != null;
                     s6_ = s6_.next_
                  ) {
                     if (s6_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_) && !JSGuards.isUndefined(arguments1Value_)) {
                        return this.toExponentialForeignObject(arguments0Value_, arguments1Value_, s6_.digitsErrorBranch_, s6_.toIntegerNode_, s6_.interop_);
                     }
                  }
               }

               if ((state_0 & 128) != 0 && JSGuards.isForeignObject(arguments0Value_) && !JSGuards.isUndefined(arguments1Value_)) {
                  return this.toExponentialForeignObject1Boundary(state_0, arguments0Value_, arguments1Value_);
               }

               if ((state_0 & 256) != 0
                  && !JSGuards.isJSNumber(arguments0Value_)
                  && !JSGuards.isJavaNumber(arguments0Value_)
                  && !JSGuards.isForeignObject(arguments0Value_)) {
                  return this.toExponentialOther(arguments0Value_, arguments1Value_);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @CompilerDirectives.TruffleBoundary
      private Object toExponentialForeignObjectUndefined1Boundary(int state_0, Object arguments0Value_, Object arguments1Value_) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         Object var7;
         try {
            InteropLibrary toExponentialForeignObjectUndefined1_interop__ = NumberPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value_);
            var7 = this.toExponentialForeignObjectUndefined(arguments0Value_, arguments1Value_, toExponentialForeignObjectUndefined1_interop__);
         } finally {
            encapsulating_.set(prev_);
         }

         return var7;
      }

      @CompilerDirectives.TruffleBoundary
      private Object toExponentialForeignObject1Boundary(int state_0, Object arguments0Value_, Object arguments1Value_) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         Object var7;
         try {
            InteropLibrary toExponentialForeignObject1_interop__ = NumberPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value_);
            var7 = this.toExponentialForeignObject(
               arguments0Value_,
               arguments1Value_,
               this.toExponentialForeignObject1_digitsErrorBranch_,
               this.toExponentialForeignObject1_toIntegerNode_,
               toExponentialForeignObject1_interop__
            );
         } finally {
            encapsulating_.set(prev_);
         }

         return var7;
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isJSNumber(arguments0Value_) && JSGuards.isUndefined(arguments1Value)) {
                  int var34;
                  this.state_0_ = var34 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.toExponentialUndefined(arguments0Value_, arguments1Value);
               }

               if (JSGuards.isJSNumber(arguments0Value_) && !JSGuards.isUndefined(arguments1Value)) {
                  this.digitsError = this.digitsError == null ? BranchProfile.create() : this.digitsError;
                  this.toInt = super.insert(this.toInt == null ? JSToIntegerAsIntNode.create() : this.toInt);
                  int var33;
                  this.state_0_ = var33 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.toExponential(arguments0Value_, arguments1Value, this.digitsError, this.toInt);
               }
            }

            if (JSGuards.isJavaNumber(arguments0Value) && JSGuards.isUndefined(arguments1Value)) {
               int var32;
               this.state_0_ = var32 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.toExponentialPrimitiveUndefined(arguments0Value, arguments1Value);
            } else if (JSGuards.isJavaNumber(arguments0Value) && !JSGuards.isUndefined(arguments1Value)) {
               this.digitsError = this.digitsError == null ? BranchProfile.create() : this.digitsError;
               this.toInt = super.insert(this.toInt == null ? JSToIntegerAsIntNode.create() : this.toInt);
               int var31;
               this.state_0_ = var31 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.toExponentialPrimitive(arguments0Value, arguments1Value, this.digitsError, this.toInt);
            } else {
               if ((exclude & 1) == 0) {
                  int count4_ = 0;
                  NumberPrototypeBuiltinsFactory.JSNumberToExponentialNodeGen.ToExponentialForeignObjectUndefined0Data s4_ = this.toExponentialForeignObjectUndefined0_cache;
                  if ((state_0 & 16) != 0) {
                     while (
                        s4_ != null
                           && (!s4_.interop_.accepts(arguments0Value) || !JSGuards.isForeignObject(arguments0Value) || !JSGuards.isUndefined(arguments1Value))
                     ) {
                        s4_ = s4_.next_;
                        count4_++;
                     }
                  }

                  if (s4_ == null && JSGuards.isForeignObject(arguments0Value) && JSGuards.isUndefined(arguments1Value) && count4_ < 5) {
                     s4_ = super.insert(
                        new NumberPrototypeBuiltinsFactory.JSNumberToExponentialNodeGen.ToExponentialForeignObjectUndefined0Data(
                           this.toExponentialForeignObjectUndefined0_cache
                        )
                     );
                     s4_.interop_ = s4_.insertAccessor(NumberPrototypeBuiltinsFactory.INTEROP_LIBRARY_.create(arguments0Value));
                     VarHandle.storeStoreFence();
                     this.toExponentialForeignObjectUndefined0_cache = s4_;
                     this.state_0_ = state_0 |= 16;
                  }

                  if (s4_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.toExponentialForeignObjectUndefined(arguments0Value, arguments1Value, s4_.interop_);
                  }
               }

               InteropLibrary toExponentialForeignObjectUndefined1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  if (JSGuards.isForeignObject(arguments0Value) && JSGuards.isUndefined(arguments1Value)) {
                     toExponentialForeignObjectUndefined1_interop__ = NumberPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value);
                     int var36;
                     this.exclude_ = var36 = exclude | 1;
                     this.toExponentialForeignObjectUndefined0_cache = null;
                     state_0 &= -17;
                     int var30;
                     this.state_0_ = var30 = state_0 | 32;
                     lock.unlock();
                     hasLock = false;
                     return this.toExponentialForeignObjectUndefined(arguments0Value, arguments1Value, toExponentialForeignObjectUndefined1_interop__);
                  }
               } finally {
                  encapsulating_.set(prev_);
               }

               if ((exclude & 2) == 0) {
                  int count6_ = 0;
                  NumberPrototypeBuiltinsFactory.JSNumberToExponentialNodeGen.ToExponentialForeignObject0Data s6_ = this.toExponentialForeignObject0_cache;
                  if ((state_0 & 64) != 0) {
                     while (
                        s6_ != null
                           && (!s6_.interop_.accepts(arguments0Value) || !JSGuards.isForeignObject(arguments0Value) || JSGuards.isUndefined(arguments1Value))
                     ) {
                        s6_ = s6_.next_;
                        count6_++;
                     }
                  }

                  if (s6_ == null && JSGuards.isForeignObject(arguments0Value) && !JSGuards.isUndefined(arguments1Value) && count6_ < 5) {
                     s6_ = super.insert(
                        new NumberPrototypeBuiltinsFactory.JSNumberToExponentialNodeGen.ToExponentialForeignObject0Data(this.toExponentialForeignObject0_cache)
                     );
                     s6_.digitsErrorBranch_ = BranchProfile.create();
                     s6_.toIntegerNode_ = s6_.insertAccessor(JSToIntegerAsIntNode.create());
                     s6_.interop_ = s6_.insertAccessor(NumberPrototypeBuiltinsFactory.INTEROP_LIBRARY_.create(arguments0Value));
                     VarHandle.storeStoreFence();
                     this.toExponentialForeignObject0_cache = s6_;
                     this.state_0_ = state_0 |= 64;
                  }

                  if (s6_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.toExponentialForeignObject(arguments0Value, arguments1Value, s6_.digitsErrorBranch_, s6_.toIntegerNode_, s6_.interop_);
                  }
               }

               toExponentialForeignObjectUndefined1_interop__ = null;
               encapsulating_ = EncapsulatingNodeReference.getCurrent();
               prev_ = encapsulating_.set(this);

               try {
                  if (JSGuards.isForeignObject(arguments0Value) && !JSGuards.isUndefined(arguments1Value)) {
                     this.toExponentialForeignObject1_digitsErrorBranch_ = BranchProfile.create();
                     this.toExponentialForeignObject1_toIntegerNode_ = super.insert(JSToIntegerAsIntNode.create());
                     toExponentialForeignObjectUndefined1_interop__ = NumberPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value);
                     int var35;
                     this.exclude_ = var35 = exclude | 2;
                     this.toExponentialForeignObject0_cache = null;
                     state_0 &= -65;
                     int var28;
                     this.state_0_ = var28 = state_0 | 128;
                     lock.unlock();
                     hasLock = false;
                     return this.toExponentialForeignObject(
                        arguments0Value,
                        arguments1Value,
                        this.toExponentialForeignObject1_digitsErrorBranch_,
                        this.toExponentialForeignObject1_toIntegerNode_,
                        toExponentialForeignObjectUndefined1_interop__
                     );
                  }
               } finally {
                  encapsulating_.set(prev_);
               }

               if (JSGuards.isJSNumber(arguments0Value) || JSGuards.isJavaNumber(arguments0Value) || JSGuards.isForeignObject(arguments0Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
               } else {
                  int var26;
                  this.state_0_ = var26 = state_0 | 256;
                  lock.unlock();
                  hasLock = false;
                  return this.toExponentialOther(arguments0Value, arguments1Value);
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
            if ((state_0 & state_0 - 1) == 0) {
               NumberPrototypeBuiltinsFactory.JSNumberToExponentialNodeGen.ToExponentialForeignObjectUndefined0Data s4_ = this.toExponentialForeignObjectUndefined0_cache;
               NumberPrototypeBuiltinsFactory.JSNumberToExponentialNodeGen.ToExponentialForeignObject0Data s6_ = this.toExponentialForeignObject0_cache;
               if ((s4_ == null || s4_.next_ == null) && (s6_ == null || s6_.next_ == null)) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[10];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"toExponentialUndefined", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"toExponential", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.digitsError, this.toInt));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"toExponentialPrimitiveUndefined", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"toExponentialPrimitive", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.digitsError, this.toInt));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"toExponentialForeignObjectUndefined", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (NumberPrototypeBuiltinsFactory.JSNumberToExponentialNodeGen.ToExponentialForeignObjectUndefined0Data s4_ = this.toExponentialForeignObjectUndefined0_cache;
               s4_ != null;
               s4_ = s4_.next_
            ) {
               cached.add(Arrays.asList(s4_.interop_));
            }

            s[2] = cached;
         } else if ((exclude & 1) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         s = new Object[]{"toExponentialForeignObjectUndefined", null, null};
         if ((state_0 & 32) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[6] = s;
         s = new Object[]{"toExponentialForeignObject", null, null};
         if ((state_0 & 64) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (NumberPrototypeBuiltinsFactory.JSNumberToExponentialNodeGen.ToExponentialForeignObject0Data s6_ = this.toExponentialForeignObject0_cache;
               s6_ != null;
               s6_ = s6_.next_
            ) {
               cached.add(Arrays.asList(s6_.digitsErrorBranch_, s6_.toIntegerNode_, s6_.interop_));
            }

            s[2] = cached;
         } else if ((exclude & 2) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[7] = s;
         s = new Object[]{"toExponentialForeignObject", null, null};
         if ((state_0 & 128) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toExponentialForeignObject1_digitsErrorBranch_, this.toExponentialForeignObject1_toIntegerNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[8] = s;
         s = new Object[]{"toExponentialOther", null, null};
         if ((state_0 & 256) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[9] = s;
         return Introspection.Provider.create(data);
      }

      public static NumberPrototypeBuiltins.JSNumberToExponentialNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new NumberPrototypeBuiltinsFactory.JSNumberToExponentialNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(NumberPrototypeBuiltins.JSNumberToExponentialNode.class)
      private static final class ToExponentialForeignObject0Data extends Node {
         @Node.Child
         NumberPrototypeBuiltinsFactory.JSNumberToExponentialNodeGen.ToExponentialForeignObject0Data next_;
         @CompilerDirectives.CompilationFinal
         BranchProfile digitsErrorBranch_;
         @Node.Child
         JSToIntegerAsIntNode toIntegerNode_;
         @Node.Child
         InteropLibrary interop_;

         ToExponentialForeignObject0Data(NumberPrototypeBuiltinsFactory.JSNumberToExponentialNodeGen.ToExponentialForeignObject0Data next_) {
            this.next_ = next_;
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(NumberPrototypeBuiltins.JSNumberToExponentialNode.class)
      private static final class ToExponentialForeignObjectUndefined0Data extends Node {
         @Node.Child
         NumberPrototypeBuiltinsFactory.JSNumberToExponentialNodeGen.ToExponentialForeignObjectUndefined0Data next_;
         @Node.Child
         InteropLibrary interop_;

         ToExponentialForeignObjectUndefined0Data(NumberPrototypeBuiltinsFactory.JSNumberToExponentialNodeGen.ToExponentialForeignObjectUndefined0Data next_) {
            this.next_ = next_;
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

   @GeneratedBy(NumberPrototypeBuiltins.JSNumberToFixedNode.class)
   public static final class JSNumberToFixedNodeGen extends NumberPrototypeBuiltins.JSNumberToFixedNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private JSToIntegerAsIntNode toInt;
      @Node.Child
      private NumberPrototypeBuiltinsFactory.JSNumberToFixedNodeGen.ToFixedForeignObject0Data toFixedForeignObject0_cache;
      @Node.Child
      private JSToIntegerAsIntNode toFixedForeignObject1_toIntegerNode_;

      private JSNumberToFixedNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      @ExplodeLoop
      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               if (JSGuards.isJSNumber(arguments0Value__)) {
                  return this.toFixed(arguments0Value__, arguments1Value_, this.toInt);
               }
            }

            if ((state_0 & 30) != 0) {
               if ((state_0 & 2) != 0 && JSGuards.isJavaNumber(arguments0Value_)) {
                  return this.toFixedJava(arguments0Value_, arguments1Value_, this.toInt);
               }

               if ((state_0 & 4) != 0) {
                  for (NumberPrototypeBuiltinsFactory.JSNumberToFixedNodeGen.ToFixedForeignObject0Data s2_ = this.toFixedForeignObject0_cache;
                     s2_ != null;
                     s2_ = s2_.next_
                  ) {
                     if (s2_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_)) {
                        return this.toFixedForeignObject(arguments0Value_, arguments1Value_, s2_.toIntegerNode_, s2_.interop_);
                     }
                  }
               }

               if ((state_0 & 8) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                  return this.toFixedForeignObject1Boundary(state_0, arguments0Value_, arguments1Value_);
               }

               if ((state_0 & 16) != 0 && fallbackGuard_(state_0, arguments0Value_, arguments1Value_)) {
                  return this.toFixedGeneric(arguments0Value_, arguments1Value_);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @CompilerDirectives.TruffleBoundary
      private Object toFixedForeignObject1Boundary(int state_0, Object arguments0Value_, Object arguments1Value_) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         Object var7;
         try {
            InteropLibrary toFixedForeignObject1_interop__ = NumberPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value_);
            var7 = this.toFixedForeignObject(arguments0Value_, arguments1Value_, this.toFixedForeignObject1_toIntegerNode_, toFixedForeignObject1_interop__);
         } finally {
            encapsulating_.set(prev_);
         }

         return var7;
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isJSNumber(arguments0Value_)) {
                  this.toInt = super.insert(this.toInt == null ? JSToIntegerAsIntNode.create() : this.toInt);
                  int var23;
                  this.state_0_ = var23 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.toFixed(arguments0Value_, arguments1Value, this.toInt);
               }
            }

            if (JSGuards.isJavaNumber(arguments0Value)) {
               this.toInt = super.insert(this.toInt == null ? JSToIntegerAsIntNode.create() : this.toInt);
               int var22;
               this.state_0_ = var22 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.toFixedJava(arguments0Value, arguments1Value, this.toInt);
            } else {
               if (exclude == 0) {
                  int count2_ = 0;
                  NumberPrototypeBuiltinsFactory.JSNumberToFixedNodeGen.ToFixedForeignObject0Data s2_ = this.toFixedForeignObject0_cache;
                  if ((state_0 & 4) != 0) {
                     while (s2_ != null && (!s2_.interop_.accepts(arguments0Value) || !JSGuards.isForeignObject(arguments0Value))) {
                        s2_ = s2_.next_;
                        count2_++;
                     }
                  }

                  if (s2_ == null && JSGuards.isForeignObject(arguments0Value) && count2_ < 5) {
                     s2_ = super.insert(new NumberPrototypeBuiltinsFactory.JSNumberToFixedNodeGen.ToFixedForeignObject0Data(this.toFixedForeignObject0_cache));
                     s2_.toIntegerNode_ = s2_.insertAccessor(JSToIntegerAsIntNode.create());
                     s2_.interop_ = s2_.insertAccessor(NumberPrototypeBuiltinsFactory.INTEROP_LIBRARY_.create(arguments0Value));
                     VarHandle.storeStoreFence();
                     this.toFixedForeignObject0_cache = s2_;
                     this.state_0_ = state_0 |= 4;
                  }

                  if (s2_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.toFixedForeignObject(arguments0Value, arguments1Value, s2_.toIntegerNode_, s2_.interop_);
                  }
               }

               InteropLibrary toFixedForeignObject1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  if (JSGuards.isForeignObject(arguments0Value)) {
                     this.toFixedForeignObject1_toIntegerNode_ = super.insert(JSToIntegerAsIntNode.create());
                     toFixedForeignObject1_interop__ = NumberPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value);
                     int var24;
                     this.exclude_ = var24 = exclude | 1;
                     this.toFixedForeignObject0_cache = null;
                     state_0 &= -5;
                     int var21;
                     this.state_0_ = var21 = state_0 | 8;
                     lock.unlock();
                     hasLock = false;
                     return this.toFixedForeignObject(
                        arguments0Value, arguments1Value, this.toFixedForeignObject1_toIntegerNode_, toFixedForeignObject1_interop__
                     );
                  }
               } finally {
                  encapsulating_.set(prev_);
               }

               int var19;
               this.state_0_ = var19 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.toFixedGeneric(arguments0Value, arguments1Value);
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
               NumberPrototypeBuiltinsFactory.JSNumberToFixedNodeGen.ToFixedForeignObject0Data s2_ = this.toFixedForeignObject0_cache;
               if (s2_ == null || s2_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[6];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"toFixed", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toInt));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"toFixedJava", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toInt));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"toFixedForeignObject", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (NumberPrototypeBuiltinsFactory.JSNumberToFixedNodeGen.ToFixedForeignObject0Data s2_ = this.toFixedForeignObject0_cache;
               s2_ != null;
               s2_ = s2_.next_
            ) {
               cached.add(Arrays.asList(s2_.toIntegerNode_, s2_.interop_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"toFixedForeignObject", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toFixedForeignObject1_toIntegerNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"toFixedGeneric", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         return Introspection.Provider.create(data);
      }

      private static boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value) {
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            if (JSGuards.isJSNumber(arguments0Value_)) {
               return false;
            }
         }

         return (state_0 & 2) == 0 && JSGuards.isJavaNumber(arguments0Value) ? false : (state_0 & 8) != 0 || !JSGuards.isForeignObject(arguments0Value);
      }

      public static NumberPrototypeBuiltins.JSNumberToFixedNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new NumberPrototypeBuiltinsFactory.JSNumberToFixedNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(NumberPrototypeBuiltins.JSNumberToFixedNode.class)
      private static final class ToFixedForeignObject0Data extends Node {
         @Node.Child
         NumberPrototypeBuiltinsFactory.JSNumberToFixedNodeGen.ToFixedForeignObject0Data next_;
         @Node.Child
         JSToIntegerAsIntNode toIntegerNode_;
         @Node.Child
         InteropLibrary interop_;

         ToFixedForeignObject0Data(NumberPrototypeBuiltinsFactory.JSNumberToFixedNodeGen.ToFixedForeignObject0Data next_) {
            this.next_ = next_;
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

   @GeneratedBy(NumberPrototypeBuiltins.JSNumberToLocaleStringIntlNode.class)
   public static final class JSNumberToLocaleStringIntlNodeGen extends NumberPrototypeBuiltins.JSNumberToLocaleStringIntlNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private NumberPrototypeBuiltinsFactory.JSNumberToLocaleStringIntlNodeGen.ToLocaleStringForeignObject0Data toLocaleStringForeignObject0_cache;

      private JSNumberToLocaleStringIntlNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_};
      }

      @ExplodeLoop
      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               if (JSGuards.isJSNumber(arguments0Value__)) {
                  return this.jsNumberToLocaleString(arguments0Value__, arguments1Value_, arguments2Value_);
               }
            }

            if ((state_0 & 30) != 0) {
               if ((state_0 & 2) != 0 && JSGuards.isJavaNumber(arguments0Value_)) {
                  return this.javaNumberToLocaleString(arguments0Value_, arguments1Value_, arguments2Value_);
               }

               if ((state_0 & 4) != 0) {
                  for (NumberPrototypeBuiltinsFactory.JSNumberToLocaleStringIntlNodeGen.ToLocaleStringForeignObject0Data s2_ = this.toLocaleStringForeignObject0_cache;
                     s2_ != null;
                     s2_ = s2_.next_
                  ) {
                     if (s2_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_)) {
                        return this.toLocaleStringForeignObject(arguments0Value_, arguments1Value_, arguments2Value_, s2_.interop_);
                     }
                  }
               }

               if ((state_0 & 8) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                  return this.toLocaleStringForeignObject1Boundary(state_0, arguments0Value_, arguments1Value_, arguments2Value_);
               }

               if ((state_0 & 16) != 0 && fallbackGuard_(state_0, arguments0Value_, arguments1Value_, arguments2Value_)) {
                  return this.failForNonNumbers(arguments0Value_, arguments1Value_, arguments2Value_);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
      }

      @CompilerDirectives.TruffleBoundary
      private Object toLocaleStringForeignObject1Boundary(int state_0, Object arguments0Value_, Object arguments1Value_, Object arguments2Value_) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         TruffleString var8;
         try {
            InteropLibrary toLocaleStringForeignObject1_interop__ = NumberPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value_);
            var8 = this.toLocaleStringForeignObject(arguments0Value_, arguments1Value_, arguments2Value_, toLocaleStringForeignObject1_interop__);
         } finally {
            encapsulating_.set(prev_);
         }

         return var8;
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isJSNumber(arguments0Value_)) {
                  int var24;
                  this.state_0_ = var24 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.jsNumberToLocaleString(arguments0Value_, arguments1Value, arguments2Value);
               }
            }

            if (JSGuards.isJavaNumber(arguments0Value)) {
               int var23;
               this.state_0_ = var23 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.javaNumberToLocaleString(arguments0Value, arguments1Value, arguments2Value);
            } else {
               if (exclude == 0) {
                  int count2_ = 0;
                  NumberPrototypeBuiltinsFactory.JSNumberToLocaleStringIntlNodeGen.ToLocaleStringForeignObject0Data s2_ = this.toLocaleStringForeignObject0_cache;
                  if ((state_0 & 4) != 0) {
                     while (s2_ != null && (!s2_.interop_.accepts(arguments0Value) || !JSGuards.isForeignObject(arguments0Value))) {
                        s2_ = s2_.next_;
                        count2_++;
                     }
                  }

                  if (s2_ == null && JSGuards.isForeignObject(arguments0Value) && count2_ < 5) {
                     s2_ = super.insert(
                        new NumberPrototypeBuiltinsFactory.JSNumberToLocaleStringIntlNodeGen.ToLocaleStringForeignObject0Data(
                           this.toLocaleStringForeignObject0_cache
                        )
                     );
                     s2_.interop_ = s2_.insertAccessor(NumberPrototypeBuiltinsFactory.INTEROP_LIBRARY_.create(arguments0Value));
                     VarHandle.storeStoreFence();
                     this.toLocaleStringForeignObject0_cache = s2_;
                     this.state_0_ = state_0 |= 4;
                  }

                  if (s2_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.toLocaleStringForeignObject(arguments0Value, arguments1Value, arguments2Value, s2_.interop_);
                  }
               }

               InteropLibrary toLocaleStringForeignObject1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  if (JSGuards.isForeignObject(arguments0Value)) {
                     toLocaleStringForeignObject1_interop__ = NumberPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value);
                     int var25;
                     this.exclude_ = var25 = exclude | 1;
                     this.toLocaleStringForeignObject0_cache = null;
                     state_0 &= -5;
                     int var22;
                     this.state_0_ = var22 = state_0 | 8;
                     lock.unlock();
                     hasLock = false;
                     return this.toLocaleStringForeignObject(arguments0Value, arguments1Value, arguments2Value, toLocaleStringForeignObject1_interop__);
                  }
               } finally {
                  encapsulating_.set(prev_);
               }

               int var20;
               this.state_0_ = var20 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.failForNonNumbers(arguments0Value, arguments1Value, arguments2Value);
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
               NumberPrototypeBuiltinsFactory.JSNumberToLocaleStringIntlNodeGen.ToLocaleStringForeignObject0Data s2_ = this.toLocaleStringForeignObject0_cache;
               if (s2_ == null || s2_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[6];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"jsNumberToLocaleString", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"javaNumberToLocaleString", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"toLocaleStringForeignObject", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (NumberPrototypeBuiltinsFactory.JSNumberToLocaleStringIntlNodeGen.ToLocaleStringForeignObject0Data s2_ = this.toLocaleStringForeignObject0_cache;
               s2_ != null;
               s2_ = s2_.next_
            ) {
               cached.add(Arrays.asList(s2_.interop_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"toLocaleStringForeignObject", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"failForNonNumbers", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         return Introspection.Provider.create(data);
      }

      private static boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            if (JSGuards.isJSNumber(arguments0Value_)) {
               return false;
            }
         }

         return (state_0 & 2) == 0 && JSGuards.isJavaNumber(arguments0Value) ? false : (state_0 & 8) != 0 || !JSGuards.isForeignObject(arguments0Value);
      }

      public static NumberPrototypeBuiltins.JSNumberToLocaleStringIntlNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new NumberPrototypeBuiltinsFactory.JSNumberToLocaleStringIntlNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(NumberPrototypeBuiltins.JSNumberToLocaleStringIntlNode.class)
      private static final class ToLocaleStringForeignObject0Data extends Node {
         @Node.Child
         NumberPrototypeBuiltinsFactory.JSNumberToLocaleStringIntlNodeGen.ToLocaleStringForeignObject0Data next_;
         @Node.Child
         InteropLibrary interop_;

         ToLocaleStringForeignObject0Data(NumberPrototypeBuiltinsFactory.JSNumberToLocaleStringIntlNodeGen.ToLocaleStringForeignObject0Data next_) {
            this.next_ = next_;
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

   @GeneratedBy(NumberPrototypeBuiltins.JSNumberToLocaleStringNode.class)
   public static final class JSNumberToLocaleStringNodeGen extends NumberPrototypeBuiltins.JSNumberToLocaleStringNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private NumberPrototypeBuiltinsFactory.JSNumberToLocaleStringNodeGen.ToLocaleStringForeignObject0Data toLocaleStringForeignObject0_cache;

      private JSNumberToLocaleStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @ExplodeLoop
      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if (JSGuards.isJSNumber(arguments0Value__)) {
               return this.toLocaleString(arguments0Value__);
            }
         }

         if ((state_0 & 30) != 0) {
            if ((state_0 & 2) != 0 && JSGuards.isJavaNumber(arguments0Value_)) {
               return this.toLocaleStringPrimitive(arguments0Value_);
            }

            if ((state_0 & 4) != 0) {
               for (NumberPrototypeBuiltinsFactory.JSNumberToLocaleStringNodeGen.ToLocaleStringForeignObject0Data s2_ = this.toLocaleStringForeignObject0_cache;
                  s2_ != null;
                  s2_ = s2_.next_
               ) {
                  if (s2_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_)) {
                     return this.toLocaleStringForeignObject(arguments0Value_, s2_.interop_);
                  }
               }
            }

            if ((state_0 & 8) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
               return this.toLocaleStringForeignObject1Boundary(state_0, arguments0Value_);
            }

            if ((state_0 & 16) != 0 && fallbackGuard_(state_0, arguments0Value_)) {
               return this.toLocaleStringOther(arguments0Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_);
      }

      @CompilerDirectives.TruffleBoundary
      private Object toLocaleStringForeignObject1Boundary(int state_0, Object arguments0Value_) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         Object var6;
         try {
            InteropLibrary toLocaleStringForeignObject1_interop__ = NumberPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value_);
            var6 = this.toLocaleStringForeignObject(arguments0Value_, toLocaleStringForeignObject1_interop__);
         } finally {
            encapsulating_.set(prev_);
         }

         return var6;
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isJSNumber(arguments0Value_)) {
                  int var22;
                  this.state_0_ = var22 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.toLocaleString(arguments0Value_);
               }
            }

            if (JSGuards.isJavaNumber(arguments0Value)) {
               int var21;
               this.state_0_ = var21 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.toLocaleStringPrimitive(arguments0Value);
            } else {
               if (exclude == 0) {
                  int count2_ = 0;
                  NumberPrototypeBuiltinsFactory.JSNumberToLocaleStringNodeGen.ToLocaleStringForeignObject0Data s2_ = this.toLocaleStringForeignObject0_cache;
                  if ((state_0 & 4) != 0) {
                     while (s2_ != null && (!s2_.interop_.accepts(arguments0Value) || !JSGuards.isForeignObject(arguments0Value))) {
                        s2_ = s2_.next_;
                        count2_++;
                     }
                  }

                  if (s2_ == null && JSGuards.isForeignObject(arguments0Value) && count2_ < 5) {
                     s2_ = super.insert(
                        new NumberPrototypeBuiltinsFactory.JSNumberToLocaleStringNodeGen.ToLocaleStringForeignObject0Data(
                           this.toLocaleStringForeignObject0_cache
                        )
                     );
                     s2_.interop_ = s2_.insertAccessor(NumberPrototypeBuiltinsFactory.INTEROP_LIBRARY_.create(arguments0Value));
                     VarHandle.storeStoreFence();
                     this.toLocaleStringForeignObject0_cache = s2_;
                     this.state_0_ = state_0 |= 4;
                  }

                  if (s2_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.toLocaleStringForeignObject(arguments0Value, s2_.interop_);
                  }
               }

               InteropLibrary toLocaleStringForeignObject1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  if (JSGuards.isForeignObject(arguments0Value)) {
                     toLocaleStringForeignObject1_interop__ = NumberPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value);
                     int var23;
                     this.exclude_ = var23 = exclude | 1;
                     this.toLocaleStringForeignObject0_cache = null;
                     state_0 &= -5;
                     int var20;
                     this.state_0_ = var20 = state_0 | 8;
                     lock.unlock();
                     hasLock = false;
                     return this.toLocaleStringForeignObject(arguments0Value, toLocaleStringForeignObject1_interop__);
                  }
               } finally {
                  encapsulating_.set(prev_);
               }

               int var18;
               this.state_0_ = var18 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.toLocaleStringOther(arguments0Value);
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
               NumberPrototypeBuiltinsFactory.JSNumberToLocaleStringNodeGen.ToLocaleStringForeignObject0Data s2_ = this.toLocaleStringForeignObject0_cache;
               if (s2_ == null || s2_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[6];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"toLocaleString", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"toLocaleStringPrimitive", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"toLocaleStringForeignObject", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (NumberPrototypeBuiltinsFactory.JSNumberToLocaleStringNodeGen.ToLocaleStringForeignObject0Data s2_ = this.toLocaleStringForeignObject0_cache;
               s2_ != null;
               s2_ = s2_.next_
            ) {
               cached.add(Arrays.asList(s2_.interop_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"toLocaleStringForeignObject", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"toLocaleStringOther", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         return Introspection.Provider.create(data);
      }

      private static boolean fallbackGuard_(int state_0, Object arguments0Value) {
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            if (JSGuards.isJSNumber(arguments0Value_)) {
               return false;
            }
         }

         return (state_0 & 2) == 0 && JSGuards.isJavaNumber(arguments0Value) ? false : (state_0 & 8) != 0 || !JSGuards.isForeignObject(arguments0Value);
      }

      public static NumberPrototypeBuiltins.JSNumberToLocaleStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new NumberPrototypeBuiltinsFactory.JSNumberToLocaleStringNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(NumberPrototypeBuiltins.JSNumberToLocaleStringNode.class)
      private static final class ToLocaleStringForeignObject0Data extends Node {
         @Node.Child
         NumberPrototypeBuiltinsFactory.JSNumberToLocaleStringNodeGen.ToLocaleStringForeignObject0Data next_;
         @Node.Child
         InteropLibrary interop_;

         ToLocaleStringForeignObject0Data(NumberPrototypeBuiltinsFactory.JSNumberToLocaleStringNodeGen.ToLocaleStringForeignObject0Data next_) {
            this.next_ = next_;
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

   @GeneratedBy(NumberPrototypeBuiltins.JSNumberToPrecisionNode.class)
   public static final class JSNumberToPrecisionNodeGen extends NumberPrototypeBuiltins.JSNumberToPrecisionNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private JSToStringNode toString;
      @Node.Child
      private JSToNumberNode toNumber;
      @Node.Child
      private NumberPrototypeBuiltinsFactory.JSNumberToPrecisionNodeGen.ToPrecisionForeignObjectUndefined0Data toPrecisionForeignObjectUndefined0_cache;
      @Node.Child
      private JSToStringNode toPrecisionForeignObjectUndefined1_toStringNode_;
      @Node.Child
      private NumberPrototypeBuiltinsFactory.JSNumberToPrecisionNodeGen.ToPrecisionForeignObject0Data toPrecisionForeignObject0_cache;
      @Node.Child
      private JSToNumberNode toPrecisionForeignObject1_toNumberNode_;

      private JSNumberToPrecisionNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      @ExplodeLoop
      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 3) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               if ((state_0 & 1) != 0 && JSGuards.isJSNumber(arguments0Value__) && JSGuards.isUndefined(arguments1Value_)) {
                  return this.toPrecisionUndefined(arguments0Value__, arguments1Value_, this.toString);
               }

               if ((state_0 & 2) != 0 && JSGuards.isJSNumber(arguments0Value__) && !JSGuards.isUndefined(arguments1Value_)) {
                  return this.toPrecision(arguments0Value__, arguments1Value_, this.toNumber);
               }
            }

            if ((state_0 & 508) != 0) {
               if ((state_0 & 4) != 0 && JSGuards.isJavaNumber(arguments0Value_) && JSGuards.isUndefined(arguments1Value_)) {
                  return this.toPrecisionPrimitiveUndefined(arguments0Value_, arguments1Value_, this.toString);
               }

               if ((state_0 & 8) != 0 && JSGuards.isJavaNumber(arguments0Value_) && !JSGuards.isUndefined(arguments1Value_)) {
                  return this.toPrecisionPrimitive(arguments0Value_, arguments1Value_, this.toNumber);
               }

               if ((state_0 & 16) != 0) {
                  for (NumberPrototypeBuiltinsFactory.JSNumberToPrecisionNodeGen.ToPrecisionForeignObjectUndefined0Data s4_ = this.toPrecisionForeignObjectUndefined0_cache;
                     s4_ != null;
                     s4_ = s4_.next_
                  ) {
                     if (s4_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_) && JSGuards.isUndefined(arguments1Value_)) {
                        return this.toPrecisionForeignObjectUndefined(arguments0Value_, arguments1Value_, s4_.toStringNode_, s4_.interop_);
                     }
                  }
               }

               if ((state_0 & 32) != 0 && JSGuards.isForeignObject(arguments0Value_) && JSGuards.isUndefined(arguments1Value_)) {
                  return this.toPrecisionForeignObjectUndefined1Boundary(state_0, arguments0Value_, arguments1Value_);
               }

               if ((state_0 & 64) != 0) {
                  for (NumberPrototypeBuiltinsFactory.JSNumberToPrecisionNodeGen.ToPrecisionForeignObject0Data s6_ = this.toPrecisionForeignObject0_cache;
                     s6_ != null;
                     s6_ = s6_.next_
                  ) {
                     if (s6_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_) && !JSGuards.isUndefined(arguments1Value_)) {
                        return this.toPrecisionForeignObject(arguments0Value_, arguments1Value_, s6_.toNumberNode_, s6_.interop_);
                     }
                  }
               }

               if ((state_0 & 128) != 0 && JSGuards.isForeignObject(arguments0Value_) && !JSGuards.isUndefined(arguments1Value_)) {
                  return this.toPrecisionForeignObject1Boundary(state_0, arguments0Value_, arguments1Value_);
               }

               if ((state_0 & 256) != 0
                  && !JSGuards.isJSNumber(arguments0Value_)
                  && !JSGuards.isJavaNumber(arguments0Value_)
                  && !JSGuards.isForeignObject(arguments0Value_)) {
                  return this.toPrecisionOther(arguments0Value_, arguments1Value_);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @CompilerDirectives.TruffleBoundary
      private Object toPrecisionForeignObjectUndefined1Boundary(int state_0, Object arguments0Value_, Object arguments1Value_) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         Object var7;
         try {
            InteropLibrary toPrecisionForeignObjectUndefined1_interop__ = NumberPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value_);
            var7 = this.toPrecisionForeignObjectUndefined(
               arguments0Value_, arguments1Value_, this.toPrecisionForeignObjectUndefined1_toStringNode_, toPrecisionForeignObjectUndefined1_interop__
            );
         } finally {
            encapsulating_.set(prev_);
         }

         return var7;
      }

      @CompilerDirectives.TruffleBoundary
      private Object toPrecisionForeignObject1Boundary(int state_0, Object arguments0Value_, Object arguments1Value_) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         Object var7;
         try {
            InteropLibrary toPrecisionForeignObject1_interop__ = NumberPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value_);
            var7 = this.toPrecisionForeignObject(
               arguments0Value_, arguments1Value_, this.toPrecisionForeignObject1_toNumberNode_, toPrecisionForeignObject1_interop__
            );
         } finally {
            encapsulating_.set(prev_);
         }

         return var7;
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isJSNumber(arguments0Value_) && JSGuards.isUndefined(arguments1Value)) {
                  this.toString = super.insert(this.toString == null ? JSToStringNode.create() : this.toString);
                  int var34;
                  this.state_0_ = var34 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.toPrecisionUndefined(arguments0Value_, arguments1Value, this.toString);
               }

               if (JSGuards.isJSNumber(arguments0Value_) && !JSGuards.isUndefined(arguments1Value)) {
                  this.toNumber = super.insert(this.toNumber == null ? JSToNumberNode.create() : this.toNumber);
                  int var33;
                  this.state_0_ = var33 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.toPrecision(arguments0Value_, arguments1Value, this.toNumber);
               }
            }

            if (JSGuards.isJavaNumber(arguments0Value) && JSGuards.isUndefined(arguments1Value)) {
               this.toString = super.insert(this.toString == null ? JSToStringNode.create() : this.toString);
               int var32;
               this.state_0_ = var32 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.toPrecisionPrimitiveUndefined(arguments0Value, arguments1Value, this.toString);
            } else if (JSGuards.isJavaNumber(arguments0Value) && !JSGuards.isUndefined(arguments1Value)) {
               this.toNumber = super.insert(this.toNumber == null ? JSToNumberNode.create() : this.toNumber);
               int var31;
               this.state_0_ = var31 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.toPrecisionPrimitive(arguments0Value, arguments1Value, this.toNumber);
            } else {
               if ((exclude & 1) == 0) {
                  int count4_ = 0;
                  NumberPrototypeBuiltinsFactory.JSNumberToPrecisionNodeGen.ToPrecisionForeignObjectUndefined0Data s4_ = this.toPrecisionForeignObjectUndefined0_cache;
                  if ((state_0 & 16) != 0) {
                     while (
                        s4_ != null
                           && (!s4_.interop_.accepts(arguments0Value) || !JSGuards.isForeignObject(arguments0Value) || !JSGuards.isUndefined(arguments1Value))
                     ) {
                        s4_ = s4_.next_;
                        count4_++;
                     }
                  }

                  if (s4_ == null && JSGuards.isForeignObject(arguments0Value) && JSGuards.isUndefined(arguments1Value) && count4_ < 5) {
                     s4_ = super.insert(
                        new NumberPrototypeBuiltinsFactory.JSNumberToPrecisionNodeGen.ToPrecisionForeignObjectUndefined0Data(
                           this.toPrecisionForeignObjectUndefined0_cache
                        )
                     );
                     s4_.toStringNode_ = s4_.insertAccessor(JSToStringNode.create());
                     s4_.interop_ = s4_.insertAccessor(NumberPrototypeBuiltinsFactory.INTEROP_LIBRARY_.create(arguments0Value));
                     VarHandle.storeStoreFence();
                     this.toPrecisionForeignObjectUndefined0_cache = s4_;
                     this.state_0_ = state_0 |= 16;
                  }

                  if (s4_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.toPrecisionForeignObjectUndefined(arguments0Value, arguments1Value, s4_.toStringNode_, s4_.interop_);
                  }
               }

               InteropLibrary toPrecisionForeignObjectUndefined1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  if (JSGuards.isForeignObject(arguments0Value) && JSGuards.isUndefined(arguments1Value)) {
                     this.toPrecisionForeignObjectUndefined1_toStringNode_ = super.insert(JSToStringNode.create());
                     toPrecisionForeignObjectUndefined1_interop__ = NumberPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value);
                     int var36;
                     this.exclude_ = var36 = exclude | 1;
                     this.toPrecisionForeignObjectUndefined0_cache = null;
                     state_0 &= -17;
                     int var30;
                     this.state_0_ = var30 = state_0 | 32;
                     lock.unlock();
                     hasLock = false;
                     return this.toPrecisionForeignObjectUndefined(
                        arguments0Value, arguments1Value, this.toPrecisionForeignObjectUndefined1_toStringNode_, toPrecisionForeignObjectUndefined1_interop__
                     );
                  }
               } finally {
                  encapsulating_.set(prev_);
               }

               if ((exclude & 2) == 0) {
                  int count6_ = 0;
                  NumberPrototypeBuiltinsFactory.JSNumberToPrecisionNodeGen.ToPrecisionForeignObject0Data s6_ = this.toPrecisionForeignObject0_cache;
                  if ((state_0 & 64) != 0) {
                     while (
                        s6_ != null
                           && (!s6_.interop_.accepts(arguments0Value) || !JSGuards.isForeignObject(arguments0Value) || JSGuards.isUndefined(arguments1Value))
                     ) {
                        s6_ = s6_.next_;
                        count6_++;
                     }
                  }

                  if (s6_ == null && JSGuards.isForeignObject(arguments0Value) && !JSGuards.isUndefined(arguments1Value) && count6_ < 5) {
                     s6_ = super.insert(
                        new NumberPrototypeBuiltinsFactory.JSNumberToPrecisionNodeGen.ToPrecisionForeignObject0Data(this.toPrecisionForeignObject0_cache)
                     );
                     s6_.toNumberNode_ = s6_.insertAccessor(JSToNumberNode.create());
                     s6_.interop_ = s6_.insertAccessor(NumberPrototypeBuiltinsFactory.INTEROP_LIBRARY_.create(arguments0Value));
                     VarHandle.storeStoreFence();
                     this.toPrecisionForeignObject0_cache = s6_;
                     this.state_0_ = state_0 |= 64;
                  }

                  if (s6_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.toPrecisionForeignObject(arguments0Value, arguments1Value, s6_.toNumberNode_, s6_.interop_);
                  }
               }

               toPrecisionForeignObjectUndefined1_interop__ = null;
               encapsulating_ = EncapsulatingNodeReference.getCurrent();
               prev_ = encapsulating_.set(this);

               try {
                  if (JSGuards.isForeignObject(arguments0Value) && !JSGuards.isUndefined(arguments1Value)) {
                     this.toPrecisionForeignObject1_toNumberNode_ = super.insert(JSToNumberNode.create());
                     toPrecisionForeignObjectUndefined1_interop__ = NumberPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value);
                     int var35;
                     this.exclude_ = var35 = exclude | 2;
                     this.toPrecisionForeignObject0_cache = null;
                     state_0 &= -65;
                     int var28;
                     this.state_0_ = var28 = state_0 | 128;
                     lock.unlock();
                     hasLock = false;
                     return this.toPrecisionForeignObject(
                        arguments0Value, arguments1Value, this.toPrecisionForeignObject1_toNumberNode_, toPrecisionForeignObjectUndefined1_interop__
                     );
                  }
               } finally {
                  encapsulating_.set(prev_);
               }

               if (JSGuards.isJSNumber(arguments0Value) || JSGuards.isJavaNumber(arguments0Value) || JSGuards.isForeignObject(arguments0Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
               } else {
                  int var26;
                  this.state_0_ = var26 = state_0 | 256;
                  lock.unlock();
                  hasLock = false;
                  return this.toPrecisionOther(arguments0Value, arguments1Value);
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
            if ((state_0 & state_0 - 1) == 0) {
               NumberPrototypeBuiltinsFactory.JSNumberToPrecisionNodeGen.ToPrecisionForeignObjectUndefined0Data s4_ = this.toPrecisionForeignObjectUndefined0_cache;
               NumberPrototypeBuiltinsFactory.JSNumberToPrecisionNodeGen.ToPrecisionForeignObject0Data s6_ = this.toPrecisionForeignObject0_cache;
               if ((s4_ == null || s4_.next_ == null) && (s6_ == null || s6_.next_ == null)) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[10];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"toPrecisionUndefined", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toString));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"toPrecision", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toNumber));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"toPrecisionPrimitiveUndefined", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toString));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"toPrecisionPrimitive", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toNumber));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"toPrecisionForeignObjectUndefined", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (NumberPrototypeBuiltinsFactory.JSNumberToPrecisionNodeGen.ToPrecisionForeignObjectUndefined0Data s4_ = this.toPrecisionForeignObjectUndefined0_cache;
               s4_ != null;
               s4_ = s4_.next_
            ) {
               cached.add(Arrays.asList(s4_.toStringNode_, s4_.interop_));
            }

            s[2] = cached;
         } else if ((exclude & 1) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         s = new Object[]{"toPrecisionForeignObjectUndefined", null, null};
         if ((state_0 & 32) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toPrecisionForeignObjectUndefined1_toStringNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[6] = s;
         s = new Object[]{"toPrecisionForeignObject", null, null};
         if ((state_0 & 64) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (NumberPrototypeBuiltinsFactory.JSNumberToPrecisionNodeGen.ToPrecisionForeignObject0Data s6_ = this.toPrecisionForeignObject0_cache;
               s6_ != null;
               s6_ = s6_.next_
            ) {
               cached.add(Arrays.asList(s6_.toNumberNode_, s6_.interop_));
            }

            s[2] = cached;
         } else if ((exclude & 2) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[7] = s;
         s = new Object[]{"toPrecisionForeignObject", null, null};
         if ((state_0 & 128) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toPrecisionForeignObject1_toNumberNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[8] = s;
         s = new Object[]{"toPrecisionOther", null, null};
         if ((state_0 & 256) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[9] = s;
         return Introspection.Provider.create(data);
      }

      public static NumberPrototypeBuiltins.JSNumberToPrecisionNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new NumberPrototypeBuiltinsFactory.JSNumberToPrecisionNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(NumberPrototypeBuiltins.JSNumberToPrecisionNode.class)
      private static final class ToPrecisionForeignObject0Data extends Node {
         @Node.Child
         NumberPrototypeBuiltinsFactory.JSNumberToPrecisionNodeGen.ToPrecisionForeignObject0Data next_;
         @Node.Child
         JSToNumberNode toNumberNode_;
         @Node.Child
         InteropLibrary interop_;

         ToPrecisionForeignObject0Data(NumberPrototypeBuiltinsFactory.JSNumberToPrecisionNodeGen.ToPrecisionForeignObject0Data next_) {
            this.next_ = next_;
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(NumberPrototypeBuiltins.JSNumberToPrecisionNode.class)
      private static final class ToPrecisionForeignObjectUndefined0Data extends Node {
         @Node.Child
         NumberPrototypeBuiltinsFactory.JSNumberToPrecisionNodeGen.ToPrecisionForeignObjectUndefined0Data next_;
         @Node.Child
         JSToStringNode toStringNode_;
         @Node.Child
         InteropLibrary interop_;

         ToPrecisionForeignObjectUndefined0Data(NumberPrototypeBuiltinsFactory.JSNumberToPrecisionNodeGen.ToPrecisionForeignObjectUndefined0Data next_) {
            this.next_ = next_;
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

   @GeneratedBy(NumberPrototypeBuiltins.JSNumberToStringNode.class)
   public static final class JSNumberToStringNodeGen extends NumberPrototypeBuiltins.JSNumberToStringNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private JSToIntegerAsIntNode toInt;
      @Node.Child
      private NumberPrototypeBuiltinsFactory.JSNumberToStringNodeGen.ToStringForeignObject0Data toStringForeignObject0_cache;
      @Node.Child
      private JSToIntegerAsIntNode toStringForeignObject1_toIntegerNode_;

      private JSNumberToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return (state_0 & 991) == 0 && state_0 != 0 ? this.execute_int0(state_0, frameValue) : this.execute_generic1(state_0, frameValue);
      }

      private Object execute_int0(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);

         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var6) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, var6.getResult());
         }

         assert (state_0 & 32) != 0;

         if (arguments0Value_ instanceof Number) {
            Number arguments0Value__ = (Number)arguments0Value_;
            return this.toStringPrimitiveRadixInt(arguments0Value__, arguments1Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @CompilerDirectives.TruffleBoundary
      private Object toStringForeignObject1Boundary(int state_0, Object arguments0Value_, Object arguments1Value_) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         Object var7;
         try {
            InteropLibrary toStringForeignObject1_interop__ = NumberPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value_);
            var7 = this.toStringForeignObject(arguments0Value_, arguments1Value_, this.toStringForeignObject1_toIntegerNode_, toStringForeignObject1_interop__);
         } finally {
            encapsulating_.set(prev_);
         }

         return var7;
      }

      @ExplodeLoop
      private Object execute_generic1(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 31) != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSNumberObject) {
               JSNumberObject arguments0Value__ = (JSNumberObject)arguments0Value_;
               if (NumberPrototypeBuiltins.JSNumberToStringNode.isJSNumberInteger(arguments0Value__) && this.isRadix10(arguments1Value_)) {
                  return this.toStringIntRadix10(arguments0Value__, arguments1Value_);
               }
            }

            if ((state_0 & 6) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               if ((state_0 & 2) != 0 && JSGuards.isJSNumber(arguments0Value__) && this.isRadix10(arguments1Value_)) {
                  return this.toStringRadix10(arguments0Value__, arguments1Value_);
               }

               if ((state_0 & 4) != 0 && JSGuards.isJSNumber(arguments0Value__) && !JSGuards.isUndefined(arguments1Value_)) {
                  return this.toString(arguments0Value__, arguments1Value_, this.toInt);
               }
            }

            if ((state_0 & 24) != 0) {
               if ((state_0 & 8) != 0
                  && JSGuards.isJavaNumber(arguments0Value_)
                  && JSGuards.isNumberInteger(arguments0Value_)
                  && this.isRadix10(arguments1Value_)) {
                  return this.toStringPrimitiveIntRadix10(arguments0Value_, arguments1Value_);
               }

               if ((state_0 & 16) != 0 && JSGuards.isJavaNumber(arguments0Value_) && this.isRadix10(arguments1Value_)) {
                  return this.toStringPrimitiveRadix10(arguments0Value_, arguments1Value_);
               }
            }
         }

         if ((state_0 & 96) != 0 && arguments0Value_ instanceof Number) {
            Number arguments0Value__x = (Number)arguments0Value_;
            if ((state_0 & 32) != 0 && arguments1Value_ instanceof Integer) {
               int arguments1Value__ = (Integer)arguments1Value_;
               return this.toStringPrimitiveRadixInt(arguments0Value__x, arguments1Value__);
            }

            if ((state_0 & 64) != 0 && !JSGuards.isUndefined(arguments1Value_)) {
               return this.toStringPrimitive(arguments0Value__x, arguments1Value_, this.toInt);
            }
         }

         if ((state_0 & 896) != 0) {
            if ((state_0 & 128) != 0) {
               for (NumberPrototypeBuiltinsFactory.JSNumberToStringNodeGen.ToStringForeignObject0Data s7_ = this.toStringForeignObject0_cache;
                  s7_ != null;
                  s7_ = s7_.next_
               ) {
                  if (s7_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_)) {
                     return this.toStringForeignObject(arguments0Value_, arguments1Value_, s7_.toIntegerNode_, s7_.interop_);
                  }
               }
            }

            if ((state_0 & 256) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
               return this.toStringForeignObject1Boundary(state_0, arguments0Value_, arguments1Value_);
            }

            if ((state_0 & 512) != 0
               && !JSGuards.isJSNumber(arguments0Value_)
               && !JSGuards.isJavaNumber(arguments0Value_)
               && !JSGuards.isForeignObject(arguments0Value_)) {
               return this.toStringNoNumber(arguments0Value_, arguments1Value_);
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

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments0Value instanceof JSNumberObject) {
               JSNumberObject arguments0Value_ = (JSNumberObject)arguments0Value;
               if (NumberPrototypeBuiltins.JSNumberToStringNode.isJSNumberInteger(arguments0Value_) && this.isRadix10(arguments1Value)) {
                  int var29;
                  this.state_0_ = var29 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.toStringIntRadix10(arguments0Value_, arguments1Value);
               }
            }

            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isJSNumber(arguments0Value_) && this.isRadix10(arguments1Value)) {
                  int var28;
                  this.state_0_ = var28 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.toStringRadix10(arguments0Value_, arguments1Value);
               }

               if (JSGuards.isJSNumber(arguments0Value_) && !JSGuards.isUndefined(arguments1Value)) {
                  this.toInt = super.insert(this.toInt == null ? JSToIntegerAsIntNode.create() : this.toInt);
                  int var27;
                  this.state_0_ = var27 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return this.toString(arguments0Value_, arguments1Value, this.toInt);
               }
            }

            if (JSGuards.isJavaNumber(arguments0Value) && JSGuards.isNumberInteger(arguments0Value) && this.isRadix10(arguments1Value)) {
               int var26;
               this.state_0_ = var26 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.toStringPrimitiveIntRadix10(arguments0Value, arguments1Value);
            } else if (JSGuards.isJavaNumber(arguments0Value) && this.isRadix10(arguments1Value)) {
               int var25;
               this.state_0_ = var25 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.toStringPrimitiveRadix10(arguments0Value, arguments1Value);
            } else {
               if (arguments0Value instanceof Number) {
                  Number arguments0Value_x = (Number)arguments0Value;
                  if ((exclude & 1) == 0 && arguments1Value instanceof Integer) {
                     int arguments1Value_ = (Integer)arguments1Value;
                     int var24;
                     this.state_0_ = var24 = state_0 | 32;
                     lock.unlock();
                     hasLock = false;
                     return this.toStringPrimitiveRadixInt(arguments0Value_x, arguments1Value_);
                  }

                  if (!JSGuards.isUndefined(arguments1Value)) {
                     this.toInt = super.insert(this.toInt == null ? JSToIntegerAsIntNode.create() : this.toInt);
                     int var31;
                     this.exclude_ = var31 = exclude | 1;
                     state_0 &= -33;
                     int var23;
                     this.state_0_ = var23 = state_0 | 64;
                     lock.unlock();
                     hasLock = false;
                     return this.toStringPrimitive(arguments0Value_x, arguments1Value, this.toInt);
                  }
               }

               if ((exclude & 2) == 0) {
                  int count7_ = 0;
                  NumberPrototypeBuiltinsFactory.JSNumberToStringNodeGen.ToStringForeignObject0Data s7_ = this.toStringForeignObject0_cache;
                  if ((state_0 & 128) != 0) {
                     while (s7_ != null && (!s7_.interop_.accepts(arguments0Value) || !JSGuards.isForeignObject(arguments0Value))) {
                        s7_ = s7_.next_;
                        count7_++;
                     }
                  }

                  if (s7_ == null && JSGuards.isForeignObject(arguments0Value) && count7_ < 5) {
                     s7_ = super.insert(
                        new NumberPrototypeBuiltinsFactory.JSNumberToStringNodeGen.ToStringForeignObject0Data(this.toStringForeignObject0_cache)
                     );
                     s7_.toIntegerNode_ = s7_.insertAccessor(JSToIntegerAsIntNode.create());
                     s7_.interop_ = s7_.insertAccessor(NumberPrototypeBuiltinsFactory.INTEROP_LIBRARY_.create(arguments0Value));
                     VarHandle.storeStoreFence();
                     this.toStringForeignObject0_cache = s7_;
                     this.state_0_ = state_0 |= 128;
                  }

                  if (s7_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.toStringForeignObject(arguments0Value, arguments1Value, s7_.toIntegerNode_, s7_.interop_);
                  }
               }

               InteropLibrary toStringForeignObject1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  if (JSGuards.isForeignObject(arguments0Value)) {
                     this.toStringForeignObject1_toIntegerNode_ = super.insert(JSToIntegerAsIntNode.create());
                     toStringForeignObject1_interop__ = NumberPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value);
                     int var30;
                     this.exclude_ = var30 = exclude | 2;
                     this.toStringForeignObject0_cache = null;
                     state_0 &= -129;
                     int var21;
                     this.state_0_ = var21 = state_0 | 256;
                     lock.unlock();
                     hasLock = false;
                     return this.toStringForeignObject(
                        arguments0Value, arguments1Value, this.toStringForeignObject1_toIntegerNode_, toStringForeignObject1_interop__
                     );
                  }
               } finally {
                  encapsulating_.set(prev_);
               }

               if (JSGuards.isJSNumber(arguments0Value) || JSGuards.isJavaNumber(arguments0Value) || JSGuards.isForeignObject(arguments0Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
               } else {
                  int var19;
                  this.state_0_ = var19 = state_0 | 512;
                  lock.unlock();
                  hasLock = false;
                  return this.toStringNoNumber(arguments0Value, arguments1Value);
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
            if ((state_0 & state_0 - 1) == 0) {
               NumberPrototypeBuiltinsFactory.JSNumberToStringNodeGen.ToStringForeignObject0Data s7_ = this.toStringForeignObject0_cache;
               if (s7_ == null || s7_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[11];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"toStringIntRadix10", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"toStringRadix10", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"toString", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toInt));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"toStringPrimitiveIntRadix10", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"toStringPrimitiveRadix10", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         s = new Object[]{"toStringPrimitiveRadixInt", null, null};
         if ((state_0 & 32) != 0) {
            s[1] = (byte)1;
         } else if ((exclude & 1) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[6] = s;
         s = new Object[]{"toStringPrimitive", null, null};
         if ((state_0 & 64) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toInt));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[7] = s;
         s = new Object[]{"toStringForeignObject", null, null};
         if ((state_0 & 128) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (NumberPrototypeBuiltinsFactory.JSNumberToStringNodeGen.ToStringForeignObject0Data s7_ = this.toStringForeignObject0_cache;
               s7_ != null;
               s7_ = s7_.next_
            ) {
               cached.add(Arrays.asList(s7_.toIntegerNode_, s7_.interop_));
            }

            s[2] = cached;
         } else if ((exclude & 2) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[8] = s;
         s = new Object[]{"toStringForeignObject", null, null};
         if ((state_0 & 256) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toStringForeignObject1_toIntegerNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[9] = s;
         s = new Object[]{"toStringNoNumber", null, null};
         if ((state_0 & 512) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[10] = s;
         return Introspection.Provider.create(data);
      }

      public static NumberPrototypeBuiltins.JSNumberToStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new NumberPrototypeBuiltinsFactory.JSNumberToStringNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(NumberPrototypeBuiltins.JSNumberToStringNode.class)
      private static final class ToStringForeignObject0Data extends Node {
         @Node.Child
         NumberPrototypeBuiltinsFactory.JSNumberToStringNodeGen.ToStringForeignObject0Data next_;
         @Node.Child
         JSToIntegerAsIntNode toIntegerNode_;
         @Node.Child
         InteropLibrary interop_;

         ToStringForeignObject0Data(NumberPrototypeBuiltinsFactory.JSNumberToStringNodeGen.ToStringForeignObject0Data next_) {
            this.next_ = next_;
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

   @GeneratedBy(NumberPrototypeBuiltins.JSNumberValueOfNode.class)
   public static final class JSNumberValueOfNodeGen extends NumberPrototypeBuiltins.JSNumberValueOfNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private NumberPrototypeBuiltinsFactory.JSNumberValueOfNodeGen.ValueOfForeignObject0Data valueOfForeignObject0_cache;

      private JSNumberValueOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @ExplodeLoop
      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if (JSGuards.isJSNumber(arguments0Value__)) {
               return this.valueOf(arguments0Value__);
            }
         }

         if ((state_0 & 30) != 0) {
            if ((state_0 & 2) != 0 && JSGuards.isJavaNumber(arguments0Value_)) {
               return this.valueOfPrimitive(arguments0Value_);
            }

            if ((state_0 & 4) != 0) {
               for (NumberPrototypeBuiltinsFactory.JSNumberValueOfNodeGen.ValueOfForeignObject0Data s2_ = this.valueOfForeignObject0_cache;
                  s2_ != null;
                  s2_ = s2_.next_
               ) {
                  if (s2_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_)) {
                     return this.valueOfForeignObject(arguments0Value_, s2_.interop_);
                  }
               }
            }

            if ((state_0 & 8) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
               return this.valueOfForeignObject1Boundary(state_0, arguments0Value_);
            }

            if ((state_0 & 16) != 0 && fallbackGuard_(state_0, arguments0Value_)) {
               return this.valueOfOther(arguments0Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_);
      }

      @CompilerDirectives.TruffleBoundary
      private Object valueOfForeignObject1Boundary(int state_0, Object arguments0Value_) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         Double var6;
         try {
            InteropLibrary valueOfForeignObject1_interop__ = NumberPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value_);
            var6 = this.valueOfForeignObject(arguments0Value_, valueOfForeignObject1_interop__);
         } finally {
            encapsulating_.set(prev_);
         }

         return var6;
      }

      @ExplodeLoop
      @Override
      public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         if ((state_0 & 17) != 0) {
            return JSTypesGen.expectDouble(this.execute(frameValue));
         } else {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 14) != 0) {
               if ((state_0 & 2) != 0 && JSGuards.isJavaNumber(arguments0Value_)) {
                  return this.valueOfPrimitive(arguments0Value_);
               }

               if ((state_0 & 4) != 0) {
                  for (NumberPrototypeBuiltinsFactory.JSNumberValueOfNodeGen.ValueOfForeignObject0Data s2_ = this.valueOfForeignObject0_cache;
                     s2_ != null;
                     s2_ = s2_.next_
                  ) {
                     if (s2_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_)) {
                        return this.valueOfForeignObject(arguments0Value_, s2_.interop_);
                     }
                  }
               }

               if ((state_0 & 8) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                  return this.valueOfForeignObject1Boundary0(state_0, arguments0Value_);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(this.executeAndSpecialize(arguments0Value_));
         }
      }

      @CompilerDirectives.TruffleBoundary
      private double valueOfForeignObject1Boundary0(int state_0, Object arguments0Value_) throws UnexpectedResultException {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         double var6;
         try {
            InteropLibrary valueOfForeignObject1_interop__ = NumberPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value_);
            var6 = this.valueOfForeignObject(arguments0Value_, valueOfForeignObject1_interop__);
         } finally {
            encapsulating_.set(prev_);
         }

         return var6;
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         int state_0 = this.state_0_;

         try {
            if ((state_0 & 17) == 0 && state_0 != 0) {
               this.executeDouble(frameValue);
            } else {
               this.execute(frameValue);
            }
         } catch (UnexpectedResultException var4) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
         }
      }

      private Object executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isJSNumber(arguments0Value_)) {
                  int var22;
                  this.state_0_ = var22 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.valueOf(arguments0Value_);
               }
            }

            if (JSGuards.isJavaNumber(arguments0Value)) {
               int var21;
               this.state_0_ = var21 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.valueOfPrimitive(arguments0Value);
            } else {
               if (exclude == 0) {
                  int count2_ = 0;
                  NumberPrototypeBuiltinsFactory.JSNumberValueOfNodeGen.ValueOfForeignObject0Data s2_ = this.valueOfForeignObject0_cache;
                  if ((state_0 & 4) != 0) {
                     while (s2_ != null && (!s2_.interop_.accepts(arguments0Value) || !JSGuards.isForeignObject(arguments0Value))) {
                        s2_ = s2_.next_;
                        count2_++;
                     }
                  }

                  if (s2_ == null && JSGuards.isForeignObject(arguments0Value) && count2_ < 5) {
                     s2_ = super.insert(new NumberPrototypeBuiltinsFactory.JSNumberValueOfNodeGen.ValueOfForeignObject0Data(this.valueOfForeignObject0_cache));
                     s2_.interop_ = s2_.insertAccessor(NumberPrototypeBuiltinsFactory.INTEROP_LIBRARY_.create(arguments0Value));
                     VarHandle.storeStoreFence();
                     this.valueOfForeignObject0_cache = s2_;
                     this.state_0_ = state_0 |= 4;
                  }

                  if (s2_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.valueOfForeignObject(arguments0Value, s2_.interop_);
                  }
               }

               InteropLibrary valueOfForeignObject1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  if (JSGuards.isForeignObject(arguments0Value)) {
                     valueOfForeignObject1_interop__ = NumberPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value);
                     int var23;
                     this.exclude_ = var23 = exclude | 1;
                     this.valueOfForeignObject0_cache = null;
                     state_0 &= -5;
                     int var20;
                     this.state_0_ = var20 = state_0 | 8;
                     lock.unlock();
                     hasLock = false;
                     return this.valueOfForeignObject(arguments0Value, valueOfForeignObject1_interop__);
                  }
               } finally {
                  encapsulating_.set(prev_);
               }

               int var18;
               this.state_0_ = var18 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.valueOfOther(arguments0Value);
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
               NumberPrototypeBuiltinsFactory.JSNumberValueOfNodeGen.ValueOfForeignObject0Data s2_ = this.valueOfForeignObject0_cache;
               if (s2_ == null || s2_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[6];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"valueOf", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"valueOfPrimitive", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"valueOfForeignObject", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (NumberPrototypeBuiltinsFactory.JSNumberValueOfNodeGen.ValueOfForeignObject0Data s2_ = this.valueOfForeignObject0_cache;
               s2_ != null;
               s2_ = s2_.next_
            ) {
               cached.add(Arrays.asList(s2_.interop_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"valueOfForeignObject", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"valueOfOther", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         return Introspection.Provider.create(data);
      }

      private static boolean fallbackGuard_(int state_0, Object arguments0Value) {
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            if (JSGuards.isJSNumber(arguments0Value_)) {
               return false;
            }
         }

         return (state_0 & 2) == 0 && JSGuards.isJavaNumber(arguments0Value) ? false : (state_0 & 8) != 0 || !JSGuards.isForeignObject(arguments0Value);
      }

      public static NumberPrototypeBuiltins.JSNumberValueOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new NumberPrototypeBuiltinsFactory.JSNumberValueOfNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(NumberPrototypeBuiltins.JSNumberValueOfNode.class)
      private static final class ValueOfForeignObject0Data extends Node {
         @Node.Child
         NumberPrototypeBuiltinsFactory.JSNumberValueOfNodeGen.ValueOfForeignObject0Data next_;
         @Node.Child
         InteropLibrary interop_;

         ValueOfForeignObject0Data(NumberPrototypeBuiltinsFactory.JSNumberValueOfNodeGen.ValueOfForeignObject0Data next_) {
            this.next_ = next_;
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
}
