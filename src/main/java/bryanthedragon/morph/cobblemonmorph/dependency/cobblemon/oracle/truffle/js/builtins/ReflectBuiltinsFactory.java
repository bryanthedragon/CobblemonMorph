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
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.helper.ListSizeNode;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.FromPropertyDescriptorNode;
import com.oracle.truffle.js.nodes.access.IsExtensibleNode;
import com.oracle.truffle.js.nodes.access.JSGetOwnPropertyNode;
import com.oracle.truffle.js.nodes.access.ToPropertyDescriptorNode;
import com.oracle.truffle.js.nodes.cast.JSToPropertyKeyNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.ForeignObjectPrototypeNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.nodes.unary.IsConstructorNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ReflectBuiltins.class)
public final class ReflectBuiltinsFactory {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

   @GeneratedBy(ReflectBuiltins.ReflectApplyNode.class)
   public static final class ReflectApplyNodeGen extends ReflectBuiltins.ReflectApplyNode implements Introspection.Provider {
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

      private ReflectApplyNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                  return ReflectBuiltins.ReflectApplyNode.error(arguments0Value_, arguments1Value_, arguments2Value_, this.isCallable);
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
                  return ReflectBuiltins.ReflectApplyNode.error(arguments0Value, arguments1Value, arguments2Value, this.isCallable);
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

      public static ReflectBuiltins.ReflectApplyNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ReflectBuiltinsFactory.ReflectApplyNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ReflectBuiltins.ReflectConstructNode.class)
   public static final class ReflectConstructNodeGen extends ReflectBuiltins.ReflectConstructNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private IsConstructorNode isConstructorNode_;

      private ReflectConstructNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.reflectConstruct(arguments0Value_, arguments1Value_, arguments2Value__, this.isConstructorNode_);
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
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         Object var8;
         try {
            int state_0 = this.state_0_;
            if (!(arguments2Value instanceof Object[])) {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
               );
            }

            Object[] arguments2Value_ = (Object[])arguments2Value;
            this.isConstructorNode_ = super.insert(IsConstructorNode.create());
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.reflectConstruct(arguments0Value, arguments1Value, arguments2Value_, this.isConstructorNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var8;
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
         Object[] s = new Object[]{"reflectConstruct", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.isConstructorNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ReflectBuiltins.ReflectConstructNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ReflectBuiltinsFactory.ReflectConstructNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ReflectBuiltins.ReflectDefinePropertyNode.class)
   public static final class ReflectDefinePropertyNodeGen extends ReflectBuiltins.ReflectDefinePropertyNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToPropertyKeyNode toPropertyKeyNode_;
      @Node.Child
      private ToPropertyDescriptorNode toPropertyDescriptorNode_;

      private ReflectDefinePropertyNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.reflectDefineProperty(arguments0Value_, arguments1Value_, arguments2Value_, this.toPropertyKeyNode_, this.toPropertyDescriptorNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         if (state_0 != 0) {
            return this.reflectDefineProperty(arguments0Value_, arguments1Value_, arguments2Value_, this.toPropertyKeyNode_, this.toPropertyDescriptorNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      private boolean executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         boolean var7;
         try {
            int state_0 = this.state_0_;
            this.toPropertyKeyNode_ = super.insert(JSToPropertyKeyNode.create());
            this.toPropertyDescriptorNode_ = super.insert(ToPropertyDescriptorNode.create(this.getContext()));
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.reflectDefineProperty(arguments0Value, arguments1Value, arguments2Value, this.toPropertyKeyNode_, this.toPropertyDescriptorNode_);
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
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"reflectDefineProperty", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toPropertyKeyNode_, this.toPropertyDescriptorNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ReflectBuiltins.ReflectDefinePropertyNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ReflectBuiltinsFactory.ReflectDefinePropertyNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ReflectBuiltins.ReflectDeletePropertyNode.class)
   public static final class ReflectDeletePropertyNodeGen extends ReflectBuiltins.ReflectDeletePropertyNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @CompilerDirectives.CompilationFinal
      private JSClassProfile object_classProfile_;
      @Node.Child
      private ReflectBuiltinsFactory.ReflectDeletePropertyNodeGen.ForeignObject0Data foreignObject0_cache;
      @Node.Child
      private TruffleString.ToJavaStringNode foreignObject1_toJavaStringNode_;

      private ReflectDeletePropertyNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
               if (JSGuards.isJSObject(arguments0Value__)) {
                  return this.doObject(arguments0Value__, arguments1Value_, this.object_classProfile_);
               }
            }

            if ((state_0 & 14) != 0) {
               if ((state_0 & 2) != 0) {
                  for (ReflectBuiltinsFactory.ReflectDeletePropertyNodeGen.ForeignObject0Data s1_ = this.foreignObject0_cache; s1_ != null; s1_ = s1_.next_) {
                     if (s1_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_)) {
                        return this.doForeignObject(arguments0Value_, arguments1Value_, s1_.interop_, s1_.toJavaStringNode_);
                     }
                  }
               }

               if ((state_0 & 4) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                  return this.foreignObject1Boundary(state_0, arguments0Value_, arguments1Value_);
               }

               if ((state_0 & 8) != 0 && !JSGuards.isJSObject(arguments0Value_) && !JSGuards.isForeignObject(arguments0Value_)) {
                  return this.doNonObject(arguments0Value_, arguments1Value_);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @CompilerDirectives.TruffleBoundary
      private Object foreignObject1Boundary(int state_0, Object arguments0Value_, Object arguments1Value_) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         Boolean var7;
         try {
            InteropLibrary foreignObject1_interop__ = ReflectBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value_);
            var7 = this.doForeignObject(arguments0Value_, arguments1Value_, foreignObject1_interop__, this.foreignObject1_toJavaStringNode_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var7;
      }

      @ExplodeLoop
      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               if (JSGuards.isJSObject(arguments0Value__)) {
                  return this.doObject(arguments0Value__, arguments1Value_, this.object_classProfile_);
               }
            }

            if ((state_0 & 14) != 0) {
               if ((state_0 & 2) != 0) {
                  for (ReflectBuiltinsFactory.ReflectDeletePropertyNodeGen.ForeignObject0Data s1_ = this.foreignObject0_cache; s1_ != null; s1_ = s1_.next_) {
                     if (s1_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_)) {
                        return this.doForeignObject(arguments0Value_, arguments1Value_, s1_.interop_, s1_.toJavaStringNode_);
                     }
                  }
               }

               if ((state_0 & 4) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                  return this.foreignObject1Boundary0(state_0, arguments0Value_, arguments1Value_);
               }

               if ((state_0 & 8) != 0 && !JSGuards.isJSObject(arguments0Value_) && !JSGuards.isForeignObject(arguments0Value_)) {
                  return this.doNonObject(arguments0Value_, arguments1Value_);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @CompilerDirectives.TruffleBoundary
      private boolean foreignObject1Boundary0(int state_0, Object arguments0Value_, Object arguments1Value_) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         boolean var7;
         try {
            InteropLibrary foreignObject1_interop__ = ReflectBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value_);
            var7 = this.doForeignObject(arguments0Value_, arguments1Value_, foreignObject1_interop__, this.foreignObject1_toJavaStringNode_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var7;
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      private boolean executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isJSObject(arguments0Value_)) {
                  this.object_classProfile_ = JSClassProfile.create();
                  int var22;
                  this.state_0_ = var22 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.doObject(arguments0Value_, arguments1Value, this.object_classProfile_);
               }
            }

            if (exclude == 0) {
               int count1_ = 0;
               ReflectBuiltinsFactory.ReflectDeletePropertyNodeGen.ForeignObject0Data s1_ = this.foreignObject0_cache;
               if ((state_0 & 2) != 0) {
                  while (s1_ != null && (!s1_.interop_.accepts(arguments0Value) || !JSGuards.isForeignObject(arguments0Value))) {
                     s1_ = s1_.next_;
                     count1_++;
                  }
               }

               if (s1_ == null && JSGuards.isForeignObject(arguments0Value) && count1_ < 5) {
                  s1_ = super.insert(new ReflectBuiltinsFactory.ReflectDeletePropertyNodeGen.ForeignObject0Data(this.foreignObject0_cache));
                  s1_.interop_ = s1_.insertAccessor(ReflectBuiltinsFactory.INTEROP_LIBRARY_.create(arguments0Value));
                  s1_.toJavaStringNode_ = s1_.insertAccessor(TruffleString.ToJavaStringNode.create());
                  VarHandle.storeStoreFence();
                  this.foreignObject0_cache = s1_;
                  this.state_0_ = state_0 |= 2;
               }

               if (s1_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.doForeignObject(arguments0Value, arguments1Value, s1_.interop_, s1_.toJavaStringNode_);
               }
            }

            InteropLibrary foreignObject1_interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            try {
               if (JSGuards.isForeignObject(arguments0Value)) {
                  foreignObject1_interop__ = ReflectBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value);
                  this.foreignObject1_toJavaStringNode_ = super.insert(TruffleString.ToJavaStringNode.create());
                  int var23;
                  this.exclude_ = var23 = exclude | 1;
                  this.foreignObject0_cache = null;
                  state_0 &= -3;
                  int var21;
                  this.state_0_ = var21 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return this.doForeignObject(arguments0Value, arguments1Value, foreignObject1_interop__, this.foreignObject1_toJavaStringNode_);
               }
            } finally {
               encapsulating_.set(prev_);
            }

            if (JSGuards.isJSObject(arguments0Value) || JSGuards.isForeignObject(arguments0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
            } else {
               int var19;
               this.state_0_ = var19 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.doNonObject(arguments0Value, arguments1Value);
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
               ReflectBuiltinsFactory.ReflectDeletePropertyNodeGen.ForeignObject0Data s1_ = this.foreignObject0_cache;
               if (s1_ == null || s1_.next_ == null) {
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
         Object[] s = new Object[]{"doObject", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.object_classProfile_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doForeignObject", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (ReflectBuiltinsFactory.ReflectDeletePropertyNodeGen.ForeignObject0Data s1_ = this.foreignObject0_cache; s1_ != null; s1_ = s1_.next_) {
               cached.add(Arrays.asList(s1_.interop_, s1_.toJavaStringNode_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"doForeignObject", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.foreignObject1_toJavaStringNode_));
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

      public static ReflectBuiltins.ReflectDeletePropertyNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ReflectBuiltinsFactory.ReflectDeletePropertyNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(ReflectBuiltins.ReflectDeletePropertyNode.class)
      private static final class ForeignObject0Data extends Node {
         @Node.Child
         ReflectBuiltinsFactory.ReflectDeletePropertyNodeGen.ForeignObject0Data next_;
         @Node.Child
         InteropLibrary interop_;
         @Node.Child
         TruffleString.ToJavaStringNode toJavaStringNode_;

         ForeignObject0Data(ReflectBuiltinsFactory.ReflectDeletePropertyNodeGen.ForeignObject0Data next_) {
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

   @GeneratedBy(ReflectBuiltins.ReflectGetNode.class)
   public static final class ReflectGetNodeGen extends ReflectBuiltins.ReflectGetNode implements Introspection.Provider {
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
      @CompilerDirectives.CompilationFinal
      private JSClassProfile object_classProfile_;
      @Node.Child
      private ReflectBuiltinsFactory.ReflectGetNodeGen.ForeignObject0Data foreignObject0_cache;
      @Node.Child
      private ReflectBuiltinsFactory.ReflectGetNodeGen.ForeignObject1Data foreignObject1_cache;

      private ReflectGetNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if (state_0 != 0 && arguments2Value_ instanceof Object[]) {
            Object[] arguments2Value__ = (Object[])arguments2Value_;
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               if (JSGuards.isJSObject(arguments0Value__)) {
                  return this.doObject(arguments0Value__, arguments1Value_, arguments2Value__, this.object_classProfile_);
               }
            }

            if ((state_0 & 14) != 0) {
               if ((state_0 & 2) != 0) {
                  for (ReflectBuiltinsFactory.ReflectGetNodeGen.ForeignObject0Data s1_ = this.foreignObject0_cache; s1_ != null; s1_ = s1_.next_) {
                     if (s1_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_)) {
                        return this.doForeignObject(
                           arguments0Value_,
                           arguments1Value_,
                           arguments2Value__,
                           s1_.interop_,
                           s1_.importValue_,
                           s1_.foreignObjectPrototypeNode_,
                           s1_.classProfile_
                        );
                     }
                  }
               }

               if ((state_0 & 4) != 0) {
                  ReflectBuiltinsFactory.ReflectGetNodeGen.ForeignObject1Data s2_ = this.foreignObject1_cache;
                  if (s2_ != null && JSGuards.isForeignObject(arguments0Value_)) {
                     return this.foreignObject1Boundary(state_0, s2_, arguments0Value_, arguments1Value_, arguments2Value__);
                  }
               }

               if ((state_0 & 8) != 0 && !JSGuards.isJSObject(arguments0Value_) && !JSGuards.isForeignObject(arguments0Value_)) {
                  return this.doNonObject(arguments0Value_, arguments1Value_, arguments2Value__);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
      }

      @CompilerDirectives.TruffleBoundary
      private Object foreignObject1Boundary(
         int state_0,
         ReflectBuiltinsFactory.ReflectGetNodeGen.ForeignObject1Data s2_,
         Object arguments0Value_,
         Object arguments1Value_,
         Object[] arguments2Value__
      ) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         Object var9;
         try {
            InteropLibrary interop__ = ReflectBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value_);
            var9 = this.doForeignObject(
               arguments0Value_, arguments1Value_, arguments2Value__, interop__, s2_.importValue_, s2_.foreignObjectPrototypeNode_, s2_.classProfile_
            );
         } finally {
            encapsulating_.set(prev_);
         }

         return var9;
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
            if (arguments2Value instanceof Object[]) {
               Object[] arguments2Value_ = (Object[])arguments2Value;
               if (arguments0Value instanceof JSDynamicObject) {
                  JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                  if (JSGuards.isJSObject(arguments0Value_)) {
                     this.object_classProfile_ = JSClassProfile.create();
                     int var25;
                     this.state_0_ = var25 = state_0 | 1;
                     lock.unlock();
                     hasLock = false;
                     return this.doObject(arguments0Value_, arguments1Value, arguments2Value_, this.object_classProfile_);
                  }
               }

               if (exclude == 0) {
                  int count1_ = 0;
                  ReflectBuiltinsFactory.ReflectGetNodeGen.ForeignObject0Data s1_ = this.foreignObject0_cache;
                  if ((state_0 & 2) != 0) {
                     while (s1_ != null && (!s1_.interop_.accepts(arguments0Value) || !JSGuards.isForeignObject(arguments0Value))) {
                        s1_ = s1_.next_;
                        count1_++;
                     }
                  }

                  if (s1_ == null && JSGuards.isForeignObject(arguments0Value) && count1_ < 5) {
                     s1_ = super.insert(new ReflectBuiltinsFactory.ReflectGetNodeGen.ForeignObject0Data(this.foreignObject0_cache));
                     s1_.interop_ = s1_.insertAccessor(ReflectBuiltinsFactory.INTEROP_LIBRARY_.create(arguments0Value));
                     s1_.importValue_ = s1_.insertAccessor(ImportValueNode.create());
                     s1_.foreignObjectPrototypeNode_ = s1_.insertAccessor(ForeignObjectPrototypeNode.create());
                     s1_.classProfile_ = JSClassProfile.create();
                     VarHandle.storeStoreFence();
                     this.foreignObject0_cache = s1_;
                     this.state_0_ = state_0 |= 2;
                  }

                  if (s1_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doForeignObject(
                        arguments0Value, arguments1Value, arguments2Value_, s1_.interop_, s1_.importValue_, s1_.foreignObjectPrototypeNode_, s1_.classProfile_
                     );
                  }
               }

               InteropLibrary interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  if (JSGuards.isForeignObject(arguments0Value)) {
                     ReflectBuiltinsFactory.ReflectGetNodeGen.ForeignObject1Data s2_ = super.insert(
                        new ReflectBuiltinsFactory.ReflectGetNodeGen.ForeignObject1Data()
                     );
                     interop__ = ReflectBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value);
                     s2_.importValue_ = s2_.insertAccessor(ImportValueNode.create());
                     s2_.foreignObjectPrototypeNode_ = s2_.insertAccessor(ForeignObjectPrototypeNode.create());
                     s2_.classProfile_ = JSClassProfile.create();
                     VarHandle.storeStoreFence();
                     this.foreignObject1_cache = s2_;
                     int var26;
                     this.exclude_ = var26 = exclude | 1;
                     this.foreignObject0_cache = null;
                     state_0 &= -3;
                     int var24;
                     this.state_0_ = var24 = state_0 | 4;
                     lock.unlock();
                     hasLock = false;
                     return this.doForeignObject(
                        arguments0Value, arguments1Value, arguments2Value_, interop__, s2_.importValue_, s2_.foreignObjectPrototypeNode_, s2_.classProfile_
                     );
                  }
               } finally {
                  encapsulating_.set(prev_);
               }

               if (!JSGuards.isJSObject(arguments0Value) && !JSGuards.isForeignObject(arguments0Value)) {
                  int var22;
                  this.state_0_ = var22 = state_0 | 8;
                  lock.unlock();
                  hasLock = false;
                  return this.doNonObject(arguments0Value, arguments1Value, arguments2Value_);
               }
            }

            throw new UnsupportedSpecializationException(
               this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
            );
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
               ReflectBuiltinsFactory.ReflectGetNodeGen.ForeignObject0Data s1_ = this.foreignObject0_cache;
               if (s1_ == null || s1_.next_ == null) {
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
         Object[] s = new Object[]{"doObject", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.object_classProfile_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doForeignObject", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (ReflectBuiltinsFactory.ReflectGetNodeGen.ForeignObject0Data s1_ = this.foreignObject0_cache; s1_ != null; s1_ = s1_.next_) {
               cached.add(Arrays.asList(s1_.interop_, s1_.importValue_, s1_.foreignObjectPrototypeNode_, s1_.classProfile_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"doForeignObject", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            ReflectBuiltinsFactory.ReflectGetNodeGen.ForeignObject1Data s2_ = this.foreignObject1_cache;
            if (s2_ != null) {
               cached.add(Arrays.asList(s2_.importValue_, s2_.foreignObjectPrototypeNode_, s2_.classProfile_));
            }

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

      public static ReflectBuiltins.ReflectGetNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ReflectBuiltinsFactory.ReflectGetNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(ReflectBuiltins.ReflectGetNode.class)
      private static final class ForeignObject0Data extends Node {
         @Node.Child
         ReflectBuiltinsFactory.ReflectGetNodeGen.ForeignObject0Data next_;
         @Node.Child
         InteropLibrary interop_;
         @Node.Child
         ImportValueNode importValue_;
         @Node.Child
         ForeignObjectPrototypeNode foreignObjectPrototypeNode_;
         @CompilerDirectives.CompilationFinal
         JSClassProfile classProfile_;

         ForeignObject0Data(ReflectBuiltinsFactory.ReflectGetNodeGen.ForeignObject0Data next_) {
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

      @GeneratedBy(ReflectBuiltins.ReflectGetNode.class)
      private static final class ForeignObject1Data extends Node {
         @Node.Child
         ImportValueNode importValue_;
         @Node.Child
         ForeignObjectPrototypeNode foreignObjectPrototypeNode_;
         @CompilerDirectives.CompilationFinal
         JSClassProfile classProfile_;

         ForeignObject1Data() {
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

   @GeneratedBy(ReflectBuiltins.ReflectGetOwnPropertyDescriptorNode.class)
   public static final class ReflectGetOwnPropertyDescriptorNodeGen
      extends ReflectBuiltins.ReflectGetOwnPropertyDescriptorNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ReflectBuiltinsFactory.ReflectGetOwnPropertyDescriptorNodeGen.ReflectGetOwnPropertyDescriptorData reflectGetOwnPropertyDescriptor_cache;

      private ReflectGetOwnPropertyDescriptorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if (state_0 != 0) {
            ReflectBuiltinsFactory.ReflectGetOwnPropertyDescriptorNodeGen.ReflectGetOwnPropertyDescriptorData s0_ = this.reflectGetOwnPropertyDescriptor_cache;
            if (s0_ != null) {
               return this.reflectGetOwnPropertyDescriptor(
                  arguments0Value_, arguments1Value_, s0_.toPropertyKeyNode_, s0_.getOwnPropertyNode_, s0_.fromPropertyDescriptorNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         JSDynamicObject var7;
         try {
            int state_0 = this.state_0_;
            ReflectBuiltinsFactory.ReflectGetOwnPropertyDescriptorNodeGen.ReflectGetOwnPropertyDescriptorData s0_ = super.insert(
               new ReflectBuiltinsFactory.ReflectGetOwnPropertyDescriptorNodeGen.ReflectGetOwnPropertyDescriptorData()
            );
            s0_.toPropertyKeyNode_ = s0_.insertAccessor(JSToPropertyKeyNode.create());
            s0_.getOwnPropertyNode_ = s0_.insertAccessor(JSGetOwnPropertyNode.create());
            s0_.fromPropertyDescriptorNode_ = s0_.insertAccessor(FromPropertyDescriptorNode.create());
            VarHandle.storeStoreFence();
            this.reflectGetOwnPropertyDescriptor_cache = s0_;
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.reflectGetOwnPropertyDescriptor(
               arguments0Value, arguments1Value, s0_.toPropertyKeyNode_, s0_.getOwnPropertyNode_, s0_.fromPropertyDescriptorNode_
            );
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
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"reflectGetOwnPropertyDescriptor", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            ReflectBuiltinsFactory.ReflectGetOwnPropertyDescriptorNodeGen.ReflectGetOwnPropertyDescriptorData s0_ = this.reflectGetOwnPropertyDescriptor_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.toPropertyKeyNode_, s0_.getOwnPropertyNode_, s0_.fromPropertyDescriptorNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ReflectBuiltins.ReflectGetOwnPropertyDescriptorNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ReflectBuiltinsFactory.ReflectGetOwnPropertyDescriptorNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(ReflectBuiltins.ReflectGetOwnPropertyDescriptorNode.class)
      private static final class ReflectGetOwnPropertyDescriptorData extends Node {
         @Node.Child
         JSToPropertyKeyNode toPropertyKeyNode_;
         @Node.Child
         JSGetOwnPropertyNode getOwnPropertyNode_;
         @Node.Child
         FromPropertyDescriptorNode fromPropertyDescriptorNode_;

         ReflectGetOwnPropertyDescriptorData() {
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

   @GeneratedBy(ReflectBuiltins.ReflectGetPrototypeOfNode.class)
   public static final class ReflectGetPrototypeOfNodeGen extends ReflectBuiltins.ReflectGetPrototypeOfNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private ReflectGetPrototypeOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.reflectGetPrototypeOf(arguments0Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"reflectGetPrototypeOf", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ReflectBuiltins.ReflectGetPrototypeOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ReflectBuiltinsFactory.ReflectGetPrototypeOfNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ReflectBuiltins.ReflectHasNode.class)
   public static final class ReflectHasNodeGen extends ReflectBuiltins.ReflectHasNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @CompilerDirectives.CompilationFinal
      private JSClassProfile object_jsclassProfile_;
      @Node.Child
      private ReflectBuiltinsFactory.ReflectHasNodeGen.ForeignObject0Data foreignObject0_cache;
      @Node.Child
      private ReflectBuiltinsFactory.ReflectHasNodeGen.ForeignObject1Data foreignObject1_cache;

      private ReflectHasNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
               if (JSGuards.isJSObject(arguments0Value__)) {
                  return this.doObject(arguments0Value__, arguments1Value_, this.object_jsclassProfile_);
               }
            }

            if ((state_0 & 14) != 0) {
               if ((state_0 & 2) != 0) {
                  for (ReflectBuiltinsFactory.ReflectHasNodeGen.ForeignObject0Data s1_ = this.foreignObject0_cache; s1_ != null; s1_ = s1_.next_) {
                     if (s1_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_)) {
                        return this.doForeignObject(
                           arguments0Value_, arguments1Value_, s1_.interop_, s1_.toJavaStringNode_, s1_.foreignObjectPrototypeNode_, s1_.classProfile_
                        );
                     }
                  }
               }

               if ((state_0 & 4) != 0) {
                  ReflectBuiltinsFactory.ReflectHasNodeGen.ForeignObject1Data s2_ = this.foreignObject1_cache;
                  if (s2_ != null && JSGuards.isForeignObject(arguments0Value_)) {
                     return this.foreignObject1Boundary(state_0, s2_, arguments0Value_, arguments1Value_);
                  }
               }

               if ((state_0 & 8) != 0 && !JSGuards.isJSObject(arguments0Value_) && !JSGuards.isForeignObject(arguments0Value_)) {
                  return this.doNonObject(arguments0Value_, arguments1Value_);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @CompilerDirectives.TruffleBoundary
      private Object foreignObject1Boundary(
         int state_0, ReflectBuiltinsFactory.ReflectHasNodeGen.ForeignObject1Data s2_, Object arguments0Value_, Object arguments1Value_
      ) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         Object var8;
         try {
            InteropLibrary interop__ = ReflectBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value_);
            var8 = this.doForeignObject(
               arguments0Value_, arguments1Value_, interop__, s2_.toJavaStringNode_, s2_.foreignObjectPrototypeNode_, s2_.classProfile_
            );
         } finally {
            encapsulating_.set(prev_);
         }

         return var8;
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
               if (JSGuards.isJSObject(arguments0Value_)) {
                  this.object_jsclassProfile_ = JSClassProfile.create();
                  int var23;
                  this.state_0_ = var23 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.doObject(arguments0Value_, arguments1Value, this.object_jsclassProfile_);
               }
            }

            if (exclude == 0) {
               int count1_ = 0;
               ReflectBuiltinsFactory.ReflectHasNodeGen.ForeignObject0Data s1_ = this.foreignObject0_cache;
               if ((state_0 & 2) != 0) {
                  while (s1_ != null && (!s1_.interop_.accepts(arguments0Value) || !JSGuards.isForeignObject(arguments0Value))) {
                     s1_ = s1_.next_;
                     count1_++;
                  }
               }

               if (s1_ == null && JSGuards.isForeignObject(arguments0Value) && count1_ < 5) {
                  s1_ = super.insert(new ReflectBuiltinsFactory.ReflectHasNodeGen.ForeignObject0Data(this.foreignObject0_cache));
                  s1_.interop_ = s1_.insertAccessor(ReflectBuiltinsFactory.INTEROP_LIBRARY_.create(arguments0Value));
                  s1_.toJavaStringNode_ = s1_.insertAccessor(TruffleString.ToJavaStringNode.create());
                  s1_.foreignObjectPrototypeNode_ = s1_.insertAccessor(ForeignObjectPrototypeNode.create());
                  s1_.classProfile_ = JSClassProfile.create();
                  VarHandle.storeStoreFence();
                  this.foreignObject0_cache = s1_;
                  this.state_0_ = state_0 |= 2;
               }

               if (s1_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.doForeignObject(
                     arguments0Value, arguments1Value, s1_.interop_, s1_.toJavaStringNode_, s1_.foreignObjectPrototypeNode_, s1_.classProfile_
                  );
               }
            }

            InteropLibrary interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            try {
               if (JSGuards.isForeignObject(arguments0Value)) {
                  ReflectBuiltinsFactory.ReflectHasNodeGen.ForeignObject1Data s2_ = super.insert(
                     new ReflectBuiltinsFactory.ReflectHasNodeGen.ForeignObject1Data()
                  );
                  interop__ = ReflectBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value);
                  s2_.toJavaStringNode_ = s2_.insertAccessor(TruffleString.ToJavaStringNode.create());
                  s2_.foreignObjectPrototypeNode_ = s2_.insertAccessor(ForeignObjectPrototypeNode.create());
                  s2_.classProfile_ = JSClassProfile.create();
                  VarHandle.storeStoreFence();
                  this.foreignObject1_cache = s2_;
                  int var24;
                  this.exclude_ = var24 = exclude | 1;
                  this.foreignObject0_cache = null;
                  state_0 &= -3;
                  int var22;
                  this.state_0_ = var22 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return this.doForeignObject(
                     arguments0Value, arguments1Value, interop__, s2_.toJavaStringNode_, s2_.foreignObjectPrototypeNode_, s2_.classProfile_
                  );
               }
            } finally {
               encapsulating_.set(prev_);
            }

            if (JSGuards.isJSObject(arguments0Value) || JSGuards.isForeignObject(arguments0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
            } else {
               int var20;
               this.state_0_ = var20 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.doNonObject(arguments0Value, arguments1Value);
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
               ReflectBuiltinsFactory.ReflectHasNodeGen.ForeignObject0Data s1_ = this.foreignObject0_cache;
               if (s1_ == null || s1_.next_ == null) {
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
         Object[] s = new Object[]{"doObject", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.object_jsclassProfile_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doForeignObject", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (ReflectBuiltinsFactory.ReflectHasNodeGen.ForeignObject0Data s1_ = this.foreignObject0_cache; s1_ != null; s1_ = s1_.next_) {
               cached.add(Arrays.asList(s1_.interop_, s1_.toJavaStringNode_, s1_.foreignObjectPrototypeNode_, s1_.classProfile_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"doForeignObject", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            ReflectBuiltinsFactory.ReflectHasNodeGen.ForeignObject1Data s2_ = this.foreignObject1_cache;
            if (s2_ != null) {
               cached.add(Arrays.asList(s2_.toJavaStringNode_, s2_.foreignObjectPrototypeNode_, s2_.classProfile_));
            }

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

      public static ReflectBuiltins.ReflectHasNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ReflectBuiltinsFactory.ReflectHasNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(ReflectBuiltins.ReflectHasNode.class)
      private static final class ForeignObject0Data extends Node {
         @Node.Child
         ReflectBuiltinsFactory.ReflectHasNodeGen.ForeignObject0Data next_;
         @Node.Child
         InteropLibrary interop_;
         @Node.Child
         TruffleString.ToJavaStringNode toJavaStringNode_;
         @Node.Child
         ForeignObjectPrototypeNode foreignObjectPrototypeNode_;
         @CompilerDirectives.CompilationFinal
         JSClassProfile classProfile_;

         ForeignObject0Data(ReflectBuiltinsFactory.ReflectHasNodeGen.ForeignObject0Data next_) {
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

      @GeneratedBy(ReflectBuiltins.ReflectHasNode.class)
      private static final class ForeignObject1Data extends Node {
         @Node.Child
         TruffleString.ToJavaStringNode toJavaStringNode_;
         @Node.Child
         ForeignObjectPrototypeNode foreignObjectPrototypeNode_;
         @CompilerDirectives.CompilationFinal
         JSClassProfile classProfile_;

         ForeignObject1Data() {
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

   @GeneratedBy(ReflectBuiltins.ReflectIsExtensibleNode.class)
   public static final class ReflectIsExtensibleNodeGen extends ReflectBuiltins.ReflectIsExtensibleNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private IsExtensibleNode isExtensibleNode_;

      private ReflectIsExtensibleNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if (state_0 != 0) {
            return this.reflectIsExtensible(arguments0Value_, this.isExtensibleNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if (state_0 != 0) {
            return this.reflectIsExtensible(arguments0Value_, this.isExtensibleNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      private boolean executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         boolean var5;
         try {
            int state_0 = this.state_0_;
            this.isExtensibleNode_ = super.insert(IsExtensibleNode.create());
            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var5 = this.reflectIsExtensible(arguments0Value, this.isExtensibleNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var5;
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
         Object[] s = new Object[]{"reflectIsExtensible", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.isExtensibleNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ReflectBuiltins.ReflectIsExtensibleNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ReflectBuiltinsFactory.ReflectIsExtensibleNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ReflectBuiltins.ReflectOwnKeysNode.class)
   public static final class ReflectOwnKeysNodeGen extends ReflectBuiltins.ReflectOwnKeysNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @CompilerDirectives.CompilationFinal
      private JSClassProfile reflectOwnKeys_jsclassProfile_;
      @Node.Child
      private ListSizeNode reflectOwnKeys_listSize_;
      @Node.Child
      private ReflectBuiltinsFactory.ReflectOwnKeysNodeGen.ForeignObject0Data foreignObject0_cache;

      private ReflectOwnKeysNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && JSGuards.isJSObject(arguments0Value_)) {
               return this.reflectOwnKeys(arguments0Value_, this.reflectOwnKeys_jsclassProfile_, this.reflectOwnKeys_listSize_);
            }

            if ((state_0 & 2) != 0) {
               for (ReflectBuiltinsFactory.ReflectOwnKeysNodeGen.ForeignObject0Data s1_ = this.foreignObject0_cache; s1_ != null; s1_ = s1_.next_) {
                  if (s1_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_)) {
                     return this.doForeignObject(arguments0Value_, s1_.interop_);
                  }
               }
            }

            if ((state_0 & 4) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
               return this.foreignObject1Boundary(state_0, arguments0Value_);
            }

            if ((state_0 & 8) != 0 && !JSGuards.isJSObject(arguments0Value_) && !JSGuards.isForeignObject(arguments0Value_)) {
               return this.doNonObject(arguments0Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_);
      }

      @CompilerDirectives.TruffleBoundary
      private Object foreignObject1Boundary(int state_0, Object arguments0Value_) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         Object var6;
         try {
            InteropLibrary foreignObject1_interop__ = ReflectBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value_);
            var6 = this.doForeignObject(arguments0Value_, foreignObject1_interop__);
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
            if (JSGuards.isJSObject(arguments0Value)) {
               this.reflectOwnKeys_jsclassProfile_ = JSClassProfile.create();
               this.reflectOwnKeys_listSize_ = super.insert(ListSizeNode.create());
               int var21;
               this.state_0_ = var21 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.reflectOwnKeys(arguments0Value, this.reflectOwnKeys_jsclassProfile_, this.reflectOwnKeys_listSize_);
            } else {
               if (exclude == 0) {
                  int count1_ = 0;
                  ReflectBuiltinsFactory.ReflectOwnKeysNodeGen.ForeignObject0Data s1_ = this.foreignObject0_cache;
                  if ((state_0 & 2) != 0) {
                     while (s1_ != null && (!s1_.interop_.accepts(arguments0Value) || !JSGuards.isForeignObject(arguments0Value))) {
                        s1_ = s1_.next_;
                        count1_++;
                     }
                  }

                  if (s1_ == null && JSGuards.isForeignObject(arguments0Value) && count1_ < 5) {
                     s1_ = super.insert(new ReflectBuiltinsFactory.ReflectOwnKeysNodeGen.ForeignObject0Data(this.foreignObject0_cache));
                     s1_.interop_ = s1_.insertAccessor(ReflectBuiltinsFactory.INTEROP_LIBRARY_.create(arguments0Value));
                     VarHandle.storeStoreFence();
                     this.foreignObject0_cache = s1_;
                     this.state_0_ = state_0 |= 2;
                  }

                  if (s1_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doForeignObject(arguments0Value, s1_.interop_);
                  }
               }

               InteropLibrary foreignObject1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  if (JSGuards.isForeignObject(arguments0Value)) {
                     foreignObject1_interop__ = ReflectBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value);
                     int var22;
                     this.exclude_ = var22 = exclude | 1;
                     this.foreignObject0_cache = null;
                     state_0 &= -3;
                     int var20;
                     this.state_0_ = var20 = state_0 | 4;
                     lock.unlock();
                     hasLock = false;
                     return this.doForeignObject(arguments0Value, foreignObject1_interop__);
                  }
               } finally {
                  encapsulating_.set(prev_);
               }

               if (JSGuards.isJSObject(arguments0Value) || JSGuards.isForeignObject(arguments0Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
               } else {
                  int var18;
                  this.state_0_ = var18 = state_0 | 8;
                  lock.unlock();
                  hasLock = false;
                  return this.doNonObject(arguments0Value);
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
               ReflectBuiltinsFactory.ReflectOwnKeysNodeGen.ForeignObject0Data s1_ = this.foreignObject0_cache;
               if (s1_ == null || s1_.next_ == null) {
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
         Object[] s = new Object[]{"reflectOwnKeys", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.reflectOwnKeys_jsclassProfile_, this.reflectOwnKeys_listSize_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doForeignObject", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (ReflectBuiltinsFactory.ReflectOwnKeysNodeGen.ForeignObject0Data s1_ = this.foreignObject0_cache; s1_ != null; s1_ = s1_.next_) {
               cached.add(Arrays.asList(s1_.interop_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"doForeignObject", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
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

      public static ReflectBuiltins.ReflectOwnKeysNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ReflectBuiltinsFactory.ReflectOwnKeysNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(ReflectBuiltins.ReflectOwnKeysNode.class)
      private static final class ForeignObject0Data extends Node {
         @Node.Child
         ReflectBuiltinsFactory.ReflectOwnKeysNodeGen.ForeignObject0Data next_;
         @Node.Child
         InteropLibrary interop_;

         ForeignObject0Data(ReflectBuiltinsFactory.ReflectOwnKeysNodeGen.ForeignObject0Data next_) {
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

   @GeneratedBy(ReflectBuiltins.ReflectPreventExtensionsNode.class)
   public static final class ReflectPreventExtensionsNodeGen extends ReflectBuiltins.ReflectPreventExtensionsNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private ReflectPreventExtensionsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.reflectPreventExtensions(arguments0Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.reflectPreventExtensions(arguments0Value_);
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
         Object[] s = new Object[]{"reflectPreventExtensions", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ReflectBuiltins.ReflectPreventExtensionsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ReflectBuiltinsFactory.ReflectPreventExtensionsNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ReflectBuiltins.ReflectSetNode.class)
   public static final class ReflectSetNodeGen extends ReflectBuiltins.ReflectSetNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @Node.Child
      private JavaScriptNode arguments3_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @CompilerDirectives.CompilationFinal
      private JSClassProfile reflectSet_jsclassProfile_;
      @Node.Child
      private ReflectBuiltinsFactory.ReflectSetNodeGen.ForeignObject0Data foreignObject0_cache;
      @Node.Child
      private ExportValueNode foreignObject1_exportValue_;

      private ReflectSetNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
         this.arguments3_ = arguments != null && 3 < arguments.length ? arguments[3] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_};
      }

      @ExplodeLoop
      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         Object arguments3Value_ = this.arguments3_.execute(frameValue);
         if (state_0 != 0 && arguments3Value_ instanceof Object[]) {
            Object[] arguments3Value__ = (Object[])arguments3Value_;
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               if (JSGuards.isJSObject(arguments0Value__)) {
                  return this.reflectSet(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value__, this.reflectSet_jsclassProfile_);
               }
            }

            if ((state_0 & 14) != 0) {
               if ((state_0 & 2) != 0) {
                  for (ReflectBuiltinsFactory.ReflectSetNodeGen.ForeignObject0Data s1_ = this.foreignObject0_cache; s1_ != null; s1_ = s1_.next_) {
                     if (s1_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_)) {
                        return this.doForeignObject(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value__, s1_.interop_, s1_.exportValue_);
                     }
                  }
               }

               if ((state_0 & 4) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                  return this.foreignObject1Boundary(state_0, arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value__);
               }

               if ((state_0 & 8) != 0 && !JSGuards.isJSObject(arguments0Value_) && !JSGuards.isForeignObject(arguments0Value_)) {
                  return this.doNonObject(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value__);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
      }

      @CompilerDirectives.TruffleBoundary
      private Object foreignObject1Boundary(int state_0, Object arguments0Value_, Object arguments1Value_, Object arguments2Value_, Object[] arguments3Value__) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         Object var9;
         try {
            InteropLibrary foreignObject1_interop__ = ReflectBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value_);
            var9 = this.doForeignObject(
               arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value__, foreignObject1_interop__, this.foreignObject1_exportValue_
            );
         } finally {
            encapsulating_.set(prev_);
         }

         return var9;
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         if ((state_0 & 14) != 0) {
            return JSTypesGen.expectBoolean(this.execute(frameValue));
         } else {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            Object arguments3Value_ = this.arguments3_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               if (arguments3Value_ instanceof Object[]) {
                  Object[] arguments3Value__ = (Object[])arguments3Value_;
                  if (JSGuards.isJSObject(arguments0Value__)) {
                     return this.reflectSet(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value__, this.reflectSet_jsclassProfile_);
                  }
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectBoolean(this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_));
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         int state_0 = this.state_0_;

         try {
            if ((state_0 & 14) == 0 && state_0 != 0) {
               this.executeBoolean(frameValue);
            } else {
               this.execute(frameValue);
            }
         } catch (UnexpectedResultException var4) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
         }
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value, Object arguments3Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments3Value instanceof Object[]) {
               Object[] arguments3Value_ = (Object[])arguments3Value;
               if (arguments0Value instanceof JSDynamicObject) {
                  JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                  if (JSGuards.isJSObject(arguments0Value_)) {
                     this.reflectSet_jsclassProfile_ = JSClassProfile.create();
                     int var25;
                     this.state_0_ = var25 = state_0 | 1;
                     lock.unlock();
                     hasLock = false;
                     return this.reflectSet(arguments0Value_, arguments1Value, arguments2Value, arguments3Value_, this.reflectSet_jsclassProfile_);
                  }
               }

               if (exclude == 0) {
                  int count1_ = 0;
                  ReflectBuiltinsFactory.ReflectSetNodeGen.ForeignObject0Data s1_ = this.foreignObject0_cache;
                  if ((state_0 & 2) != 0) {
                     while (s1_ != null && (!s1_.interop_.accepts(arguments0Value) || !JSGuards.isForeignObject(arguments0Value))) {
                        s1_ = s1_.next_;
                        count1_++;
                     }
                  }

                  if (s1_ == null && JSGuards.isForeignObject(arguments0Value) && count1_ < 5) {
                     s1_ = super.insert(new ReflectBuiltinsFactory.ReflectSetNodeGen.ForeignObject0Data(this.foreignObject0_cache));
                     s1_.interop_ = s1_.insertAccessor(ReflectBuiltinsFactory.INTEROP_LIBRARY_.create(arguments0Value));
                     s1_.exportValue_ = s1_.insertAccessor(ExportValueNode.create());
                     VarHandle.storeStoreFence();
                     this.foreignObject0_cache = s1_;
                     this.state_0_ = state_0 |= 2;
                  }

                  if (s1_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doForeignObject(arguments0Value, arguments1Value, arguments2Value, arguments3Value_, s1_.interop_, s1_.exportValue_);
                  }
               }

               InteropLibrary foreignObject1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  if (JSGuards.isForeignObject(arguments0Value)) {
                     foreignObject1_interop__ = ReflectBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value);
                     this.foreignObject1_exportValue_ = super.insert(ExportValueNode.create());
                     int var26;
                     this.exclude_ = var26 = exclude | 1;
                     this.foreignObject0_cache = null;
                     state_0 &= -3;
                     int var24;
                     this.state_0_ = var24 = state_0 | 4;
                     lock.unlock();
                     hasLock = false;
                     return this.doForeignObject(
                        arguments0Value, arguments1Value, arguments2Value, arguments3Value_, foreignObject1_interop__, this.foreignObject1_exportValue_
                     );
                  }
               } finally {
                  encapsulating_.set(prev_);
               }

               if (!JSGuards.isJSObject(arguments0Value) && !JSGuards.isForeignObject(arguments0Value)) {
                  int var22;
                  this.state_0_ = var22 = state_0 | 8;
                  lock.unlock();
                  hasLock = false;
                  return this.doNonObject(arguments0Value, arguments1Value, arguments2Value, arguments3Value_);
               }
            }

            throw new UnsupportedSpecializationException(
               this,
               new Node[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_},
               arguments0Value,
               arguments1Value,
               arguments2Value,
               arguments3Value
            );
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
               ReflectBuiltinsFactory.ReflectSetNodeGen.ForeignObject0Data s1_ = this.foreignObject0_cache;
               if (s1_ == null || s1_.next_ == null) {
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
         Object[] s = new Object[]{"reflectSet", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.reflectSet_jsclassProfile_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doForeignObject", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (ReflectBuiltinsFactory.ReflectSetNodeGen.ForeignObject0Data s1_ = this.foreignObject0_cache; s1_ != null; s1_ = s1_.next_) {
               cached.add(Arrays.asList(s1_.interop_, s1_.exportValue_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"doForeignObject", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.foreignObject1_exportValue_));
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

      public static ReflectBuiltins.ReflectSetNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ReflectBuiltinsFactory.ReflectSetNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(ReflectBuiltins.ReflectSetNode.class)
      private static final class ForeignObject0Data extends Node {
         @Node.Child
         ReflectBuiltinsFactory.ReflectSetNodeGen.ForeignObject0Data next_;
         @Node.Child
         InteropLibrary interop_;
         @Node.Child
         ExportValueNode exportValue_;

         ForeignObject0Data(ReflectBuiltinsFactory.ReflectSetNodeGen.ForeignObject0Data next_) {
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

   @GeneratedBy(ReflectBuiltins.ReflectSetPrototypeOfNode.class)
   public static final class ReflectSetPrototypeOfNodeGen extends ReflectBuiltins.ReflectSetPrototypeOfNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private ReflectSetPrototypeOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.reflectSetPrototypeOf(arguments0Value_, arguments1Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.reflectSetPrototypeOf(arguments0Value_, arguments1Value_);
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
         Object[] s = new Object[]{"reflectSetPrototypeOf", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ReflectBuiltins.ReflectSetPrototypeOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ReflectBuiltinsFactory.ReflectSetPrototypeOfNodeGen(context, builtin, arguments);
      }
   }
}
