package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(FunctionPrototypeBuiltins.class)
public final class FunctionPrototypeBuiltinsFactory {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

   @GeneratedBy(FunctionPrototypeBuiltins.HasInstanceNode.class)
   public static final class HasInstanceNodeGen extends FunctionPrototypeBuiltins.HasInstanceNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private HasInstanceNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.hasInstance(arguments0Value_, arguments1Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.hasInstance(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"hasInstance", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static FunctionPrototypeBuiltins.HasInstanceNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new FunctionPrototypeBuiltinsFactory.HasInstanceNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(FunctionPrototypeBuiltins.JSApplyNode.class)
   public static final class JSApplyNodeGen extends FunctionPrototypeBuiltins.JSApplyNode implements Introspection.Provider {
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
      private IsCallableNode isCallable;

      private JSApplyNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               if (JSGuards.isJSFunction(arguments0Value__)) {
                  return this.applyFunction(arguments0Value__, arguments1Value_, arguments2Value_);
               }
            }

            if ((state_0 & 6) != 0) {
               if ((state_0 & 2) != 0 && this.isCallable.executeBoolean(arguments0Value_)) {
                  return this.applyCallable(arguments0Value_, arguments1Value_, arguments2Value_, this.isCallable);
               }

               if ((state_0 & 4) != 0 && !this.isCallable.executeBoolean(arguments0Value_)) {
                  return this.error(arguments0Value_, arguments1Value_, arguments2Value_, this.isCallable);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
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
            if (exclude == 0 && arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isJSFunction(arguments0Value_)) {
                  int var17;
                  this.state_0_ = var17 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.applyFunction(arguments0Value_, arguments1Value, arguments2Value);
               }
            }

            boolean ApplyCallable_duplicateFound_ = false;
            if ((state_0 & 2) != 0 && this.isCallable.executeBoolean(arguments0Value)) {
               ApplyCallable_duplicateFound_ = true;
            }

            if (!ApplyCallable_duplicateFound_) {
               IsCallableNode applyCallable_isCallable__ = super.insert(this.isCallable == null ? IsCallableNode.create() : this.isCallable);
               if (applyCallable_isCallable__.executeBoolean(arguments0Value) && (state_0 & 2) == 0) {
                  if (this.isCallable == null) {
                     IsCallableNode applyCallable_isCallable___check = super.insert(applyCallable_isCallable__);
                     if (applyCallable_isCallable___check == null) {
                        throw new AssertionError(
                           "Specialization 'applyCallable(Object, Object, Object, IsCallableNode)' contains a shared cache with name 'isCallable' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                        );
                     }

                     this.isCallable = applyCallable_isCallable___check;
                  }

                  int var18;
                  this.exclude_ = var18 = exclude | 1;
                  int var15 = state_0 & -2;
                  this.state_0_ = state_0 = var15 | 2;
                  ApplyCallable_duplicateFound_ = true;
               }
            }

            if (!ApplyCallable_duplicateFound_) {
               boolean Error_duplicateFound_ = false;
               if ((state_0 & 4) != 0 && !this.isCallable.executeBoolean(arguments0Value)) {
                  Error_duplicateFound_ = true;
               }

               if (!Error_duplicateFound_) {
                  IsCallableNode error_isCallable__ = super.insert(this.isCallable == null ? IsCallableNode.create() : this.isCallable);
                  if (!error_isCallable__.executeBoolean(arguments0Value) && (state_0 & 4) == 0) {
                     if (this.isCallable == null) {
                        IsCallableNode error_isCallable___check = super.insert(error_isCallable__);
                        if (error_isCallable___check == null) {
                           throw new AssertionError(
                              "Specialization 'error(Object, Object, Object, IsCallableNode)' contains a shared cache with name 'isCallable' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isCallable = error_isCallable___check;
                     }

                     int var16;
                     this.state_0_ = var16 = state_0 | 4;
                     Error_duplicateFound_ = true;
                  }
               }

               if (!Error_duplicateFound_) {
                  throw new UnsupportedSpecializationException(
                     this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
                  );
               } else {
                  lock.unlock();
                  hasLock = false;
                  return this.error(arguments0Value, arguments1Value, arguments2Value, this.isCallable);
               }
            } else {
               lock.unlock();
               hasLock = false;
               return this.applyCallable(arguments0Value, arguments1Value, arguments2Value, this.isCallable);
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
         int exclude = this.exclude_;
         Object[] s = new Object[]{"applyFunction", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"applyCallable", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.isCallable));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"error", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.isCallable));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static FunctionPrototypeBuiltins.JSApplyNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new FunctionPrototypeBuiltinsFactory.JSApplyNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(FunctionPrototypeBuiltins.JSBindNode.class)
   public static final class JSBindNodeGen extends FunctionPrototypeBuiltins.JSBindNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSBindNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         if (state_0 != 0 && arguments2Value_ instanceof Object[]) {
            Object[] arguments2Value__ = (Object[])arguments2Value_;
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSFunctionObject) {
               JSFunctionObject arguments0Value__ = (JSFunctionObject)arguments0Value_;
               return this.bindFunction(arguments0Value__, arguments1Value_, arguments2Value__);
            }

            if ((state_0 & 2) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               if (JSGuards.isJSProxy(arguments0Value__)) {
                  return this.bindProxy(arguments0Value__, arguments1Value_, arguments2Value__);
               }
            }

            if ((state_0 & 4) != 0 && !JSGuards.isJSFunction(arguments0Value_) && !JSGuards.isJSProxy(arguments0Value_)) {
               return this.bindError(arguments0Value_, arguments1Value_, arguments2Value__);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         int state_0 = this.state_0_;
         if (arguments2Value instanceof Object[]) {
            Object[] arguments2Value_ = (Object[])arguments2Value;
            if (arguments0Value instanceof JSFunctionObject) {
               JSFunctionObject arguments0Value_ = (JSFunctionObject)arguments0Value;
               int var9;
               this.state_0_ = var9 = state_0 | 1;
               return this.bindFunction(arguments0Value_, arguments1Value, arguments2Value_);
            }

            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isJSProxy(arguments0Value_)) {
                  int var8;
                  this.state_0_ = var8 = state_0 | 2;
                  return this.bindProxy(arguments0Value_, arguments1Value, arguments2Value_);
               }
            }

            if (!JSGuards.isJSFunction(arguments0Value) && !JSGuards.isJSProxy(arguments0Value)) {
               int var7;
               this.state_0_ = var7 = state_0 | 4;
               return this.bindError(arguments0Value, arguments1Value, arguments2Value_);
            }
         }

         throw new UnsupportedSpecializationException(
            this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
         );
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
         Object[] s = new Object[]{"bindFunction", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"bindProxy", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"bindError", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static FunctionPrototypeBuiltins.JSBindNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new FunctionPrototypeBuiltinsFactory.JSBindNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(FunctionPrototypeBuiltins.JSCallNode.class)
   public static final class JSCallNodeGen extends FunctionPrototypeBuiltins.JSCallNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSCallNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         if (state_0 != 0 && arguments2Value_ instanceof Object[]) {
            Object[] arguments2Value__ = (Object[])arguments2Value_;
            return this.call(arguments0Value_, arguments1Value_, arguments2Value__);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         int state_0 = this.state_0_;
         if (arguments2Value instanceof Object[]) {
            Object[] arguments2Value_ = (Object[])arguments2Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.call(arguments0Value, arguments1Value, arguments2Value_);
         } else {
            throw new UnsupportedSpecializationException(
               this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
            );
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
         Object[] s = new Object[]{"call", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static FunctionPrototypeBuiltins.JSCallNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new FunctionPrototypeBuiltinsFactory.JSCallNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(FunctionPrototypeBuiltins.JSFunctionToStringNode.class)
   public static final class JSFunctionToStringNodeGen extends FunctionPrototypeBuiltins.JSFunctionToStringNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private IsCallableNode isCallable;
      @Node.Child
      private InteropLibrary toStringCallable0_interop_;

      private JSFunctionToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 3) != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if ((state_0 & 1) != 0 && JSGuards.isJSFunction(arguments0Value__) && !this.isBoundTarget(arguments0Value__)) {
               return this.toStringDefault(arguments0Value__);
            }

            if ((state_0 & 2) != 0 && JSGuards.isJSFunction(arguments0Value__) && this.isBoundTarget(arguments0Value__)) {
               return this.toStringBound(arguments0Value__);
            }
         }

         if ((state_0 & 60) != 0) {
            if ((state_0 & 4) != 0 && this.toStringCallable0_interop_.accepts(arguments0Value_)) {
               assert this.isES2019OrLater();

               if (!JSGuards.isJSFunction(arguments0Value_) && this.isCallable.executeBoolean(arguments0Value_)) {
                  return this.toStringCallable(arguments0Value_, this.isCallable, this.toStringCallable0_interop_);
               }
            }

            if ((state_0 & 8) != 0) {
               assert this.isES2019OrLater();

               if (!JSGuards.isJSFunction(arguments0Value_) && this.isCallable.executeBoolean(arguments0Value_)) {
                  return this.toStringCallable1Boundary(state_0, arguments0Value_);
               }
            }

            if ((state_0 & 16) != 0) {
               assert this.isES2019OrLater();

               if (!this.isCallable.executeBoolean(arguments0Value_)) {
                  return this.toStringNotCallable(arguments0Value_, this.isCallable);
               }
            }

            if ((state_0 & 32) != 0) {
               assert !this.isES2019OrLater();

               if (!JSGuards.isJSFunction(arguments0Value_)) {
                  return this.toStringNotFunction(arguments0Value_);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_);
      }

      @CompilerDirectives.TruffleBoundary
      private Object toStringCallable1Boundary(int state_0, Object arguments0Value_) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         TruffleString var6;
         try {
            InteropLibrary toStringCallable1_interop__ = FunctionPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value_);
            var6 = this.toStringCallable(arguments0Value_, this.isCallable, toStringCallable1_interop__);
         } finally {
            encapsulating_.set(prev_);
         }

         return var6;
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private TruffleString executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isJSFunction(arguments0Value_) && !this.isBoundTarget(arguments0Value_)) {
                  int var23;
                  this.state_0_ = var23 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.toStringDefault(arguments0Value_);
               }

               if (JSGuards.isJSFunction(arguments0Value_) && this.isBoundTarget(arguments0Value_)) {
                  int var22;
                  this.state_0_ = var22 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.toStringBound(arguments0Value_);
               }
            }

            if (exclude == 0) {
               boolean ToStringCallable0_duplicateFound_ = false;
               if ((state_0 & 4) != 0 && this.toStringCallable0_interop_.accepts(arguments0Value)) {
                  assert this.isES2019OrLater();

                  if (!JSGuards.isJSFunction(arguments0Value) && this.isCallable.executeBoolean(arguments0Value)) {
                     ToStringCallable0_duplicateFound_ = true;
                  }
               }

               if (!ToStringCallable0_duplicateFound_ && this.isES2019OrLater() && !JSGuards.isJSFunction(arguments0Value)) {
                  IsCallableNode toStringCallable0_isCallable__ = super.insert(this.isCallable == null ? IsCallableNode.create() : this.isCallable);
                  if (toStringCallable0_isCallable__.executeBoolean(arguments0Value) && (state_0 & 4) == 0) {
                     if (this.isCallable == null) {
                        IsCallableNode toStringCallable0_isCallable___check = super.insert(toStringCallable0_isCallable__);
                        if (toStringCallable0_isCallable___check == null) {
                           throw new AssertionError(
                              "Specialization 'toStringCallable(Object, IsCallableNode, InteropLibrary)' contains a shared cache with name 'isCallable' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isCallable = toStringCallable0_isCallable___check;
                     }

                     this.toStringCallable0_interop_ = super.insert(FunctionPrototypeBuiltinsFactory.INTEROP_LIBRARY_.create(arguments0Value));
                     this.state_0_ = state_0 |= 4;
                     ToStringCallable0_duplicateFound_ = true;
                  }
               }

               if (ToStringCallable0_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return this.toStringCallable(arguments0Value, this.isCallable, this.toStringCallable0_interop_);
               }
            }

            InteropLibrary toStringCallable1_interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            try {
               boolean ToStringCallable1_duplicateFound_ = false;
               if ((state_0 & 8) != 0) {
                  assert this.isES2019OrLater();

                  if (!JSGuards.isJSFunction(arguments0Value) && this.isCallable.executeBoolean(arguments0Value)) {
                     ToStringCallable1_duplicateFound_ = true;
                     toStringCallable1_interop__ = FunctionPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value);
                  }
               }

               if (!ToStringCallable1_duplicateFound_ && this.isES2019OrLater() && !JSGuards.isJSFunction(arguments0Value)) {
                  IsCallableNode toStringCallable1_isCallable__ = super.insert(this.isCallable == null ? IsCallableNode.create() : this.isCallable);
                  if (toStringCallable1_isCallable__.executeBoolean(arguments0Value) && (state_0 & 8) == 0) {
                     if (this.isCallable == null) {
                        IsCallableNode toStringCallable1_isCallable___check = super.insert(toStringCallable1_isCallable__);
                        if (toStringCallable1_isCallable___check == null) {
                           throw new AssertionError(
                              "Specialization 'toStringCallable(Object, IsCallableNode, InteropLibrary)' contains a shared cache with name 'isCallable' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isCallable = toStringCallable1_isCallable___check;
                     }

                     toStringCallable1_interop__ = FunctionPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value);
                     int var24;
                     this.exclude_ = var24 = exclude | 1;
                     int var20 = state_0 & -5;
                     this.state_0_ = state_0 = var20 | 8;
                     ToStringCallable1_duplicateFound_ = true;
                  }
               }

               if (ToStringCallable1_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return this.toStringCallable(arguments0Value, this.isCallable, toStringCallable1_interop__);
               }
            } finally {
               encapsulating_.set(prev_);
            }

            boolean var27 = false;
            if ((state_0 & 16) != 0) {
               assert this.isES2019OrLater();

               if (!this.isCallable.executeBoolean(arguments0Value)) {
                  var27 = true;
               }
            }

            if (!var27 && this.isES2019OrLater()) {
               IsCallableNode toStringNotCallable_isCallable__ = super.insert(this.isCallable == null ? IsCallableNode.create() : this.isCallable);
               if (!toStringNotCallable_isCallable__.executeBoolean(arguments0Value) && (state_0 & 16) == 0) {
                  if (this.isCallable == null) {
                     IsCallableNode toStringNotCallable_isCallable___check = super.insert(toStringNotCallable_isCallable__);
                     if (toStringNotCallable_isCallable___check == null) {
                        throw new AssertionError(
                           "Specialization 'toStringNotCallable(Object, IsCallableNode)' contains a shared cache with name 'isCallable' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                        );
                     }

                     this.isCallable = toStringNotCallable_isCallable___check;
                  }

                  this.state_0_ = state_0 |= 16;
                  var27 = true;
               }
            }

            if (var27) {
               lock.unlock();
               hasLock = false;
               return this.toStringNotCallable(arguments0Value, this.isCallable);
            } else if (this.isES2019OrLater() || JSGuards.isJSFunction(arguments0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
            } else {
               int var21;
               this.state_0_ = var21 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return this.toStringNotFunction(arguments0Value);
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
         Object[] data = new Object[7];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"toStringDefault", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"toStringBound", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"toStringCallable", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.isCallable, this.toStringCallable0_interop_));
            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"toStringCallable", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.isCallable));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"toStringNotCallable", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.isCallable));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         s = new Object[]{"toStringNotFunction", null, null};
         if ((state_0 & 32) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[6] = s;
         return Introspection.Provider.create(data);
      }

      public static FunctionPrototypeBuiltins.JSFunctionToStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new FunctionPrototypeBuiltinsFactory.JSFunctionToStringNodeGen(context, builtin, arguments);
      }
   }
}
