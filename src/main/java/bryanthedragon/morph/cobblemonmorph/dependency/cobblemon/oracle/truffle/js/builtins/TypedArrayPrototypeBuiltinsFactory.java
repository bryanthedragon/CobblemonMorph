package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.control.DeletePropertyNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSTypedArrayObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(TypedArrayPrototypeBuiltins.class)
public final class TypedArrayPrototypeBuiltinsFactory {
   @GeneratedBy(TypedArrayPrototypeBuiltins.JSArrayBufferViewFillNode.class)
   public static final class JSArrayBufferViewFillNodeGen extends TypedArrayPrototypeBuiltins.JSArrayBufferViewFillNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @Node.Child
      private JavaScriptNode arguments3_;

      private JSArrayBufferViewFillNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         Object arguments3Value_ = this.arguments3_.execute(frameValue);
         return this.fill(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
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
         Object[] s = new Object[]{"fill", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TypedArrayPrototypeBuiltins.JSArrayBufferViewFillNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TypedArrayPrototypeBuiltinsFactory.JSArrayBufferViewFillNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TypedArrayPrototypeBuiltins.JSArrayBufferViewForEachNode.class)
   public static final class JSArrayBufferViewForEachNodeGen extends TypedArrayPrototypeBuiltins.JSArrayBufferViewForEachNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSArrayBufferViewForEachNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
               if (JSGuards.isJSArrayBufferView(arguments0Value__)) {
                  return this.forEach(arguments0Value__, arguments1Value_, arguments2Value_);
               }
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSArrayBufferView(arguments0Value_)) {
               return this.forEachNonTypedArray(arguments0Value_, arguments1Value_, arguments2Value_);
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
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            if (JSGuards.isJSArrayBufferView(arguments0Value_)) {
               int var7;
               this.state_0_ = var7 = state_0 | 1;
               return this.forEach(arguments0Value_, arguments1Value, arguments2Value);
            }
         }

         if (!JSGuards.isJSArrayBufferView(arguments0Value)) {
            int var6;
            this.state_0_ = var6 = state_0 | 2;
            return this.forEachNonTypedArray(arguments0Value, arguments1Value, arguments2Value);
         } else {
            throw new UnsupportedSpecializationException(
               this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
            );
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
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"forEach", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"forEachNonTypedArray", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static TypedArrayPrototypeBuiltins.JSArrayBufferViewForEachNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TypedArrayPrototypeBuiltinsFactory.JSArrayBufferViewForEachNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TypedArrayPrototypeBuiltins.JSArrayBufferViewIteratorNode.class)
   public static final class JSArrayBufferViewIteratorNodeGen
      extends TypedArrayPrototypeBuiltins.JSArrayBufferViewIteratorNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSArrayBufferViewIteratorNodeGen(JSContext context, JSBuiltin builtin, int iterationKind, JavaScriptNode[] arguments) {
         super(context, builtin, iterationKind);
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if (JSGuards.isJSArrayBufferView(arguments0Value__)) {
               return this.doObject(arguments0Value__);
            }
         }

         if ((state_0 & 2) != 0 && !JSGuards.isJSArrayBufferView(arguments0Value_)) {
            return this.doNotObject(arguments0Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            if (JSGuards.isJSArrayBufferView(arguments0Value_)) {
               int var5;
               this.state_0_ = var5 = state_0 | 1;
               return this.doObject(arguments0Value_);
            }
         }

         if (!JSGuards.isJSArrayBufferView(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.doNotObject(arguments0Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"doObject", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doNotObject", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static TypedArrayPrototypeBuiltins.JSArrayBufferViewIteratorNode create(
         JSContext context, JSBuiltin builtin, int iterationKind, JavaScriptNode[] arguments
      ) {
         return new TypedArrayPrototypeBuiltinsFactory.JSArrayBufferViewIteratorNodeGen(context, builtin, iterationKind, arguments);
      }
   }

   @GeneratedBy(TypedArrayPrototypeBuiltins.JSArrayBufferViewReverseNode.class)
   public static final class JSArrayBufferViewReverseNodeGen extends TypedArrayPrototypeBuiltins.JSArrayBufferViewReverseNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private DeletePropertyNode reverse0_deletePropertyNode_;

      private JSArrayBufferViewReverseNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if (JSGuards.isJSArrayBufferView(arguments0Value__)) {
               return this.reverse(arguments0Value__, this.reverse0_deletePropertyNode_);
            }
         }

         if ((state_0 & 2) != 0 && !JSGuards.isJSArrayBufferView(arguments0Value_)) {
            return this.reverse(arguments0Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
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
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isJSArrayBufferView(arguments0Value_)) {
                  this.reverse0_deletePropertyNode_ = super.insert(DeletePropertyNode.create(true, this.getContext()));
                  int var11;
                  this.state_0_ = var11 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.reverse(arguments0Value_, this.reverse0_deletePropertyNode_);
               }
            }

            if (JSGuards.isJSArrayBufferView(arguments0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
            } else {
               int var10;
               this.state_0_ = var10 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.reverse(arguments0Value);
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
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"reverse", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.reverse0_deletePropertyNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"reverse", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static TypedArrayPrototypeBuiltins.JSArrayBufferViewReverseNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TypedArrayPrototypeBuiltinsFactory.JSArrayBufferViewReverseNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TypedArrayPrototypeBuiltins.JSArrayBufferViewSetNode.class)
   public static final class JSArrayBufferViewSetNodeGen extends TypedArrayPrototypeBuiltins.JSArrayBufferViewSetNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSArrayBufferViewSetNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
               if (JSGuards.isJSArrayBufferView(arguments0Value__)) {
                  return this.set(arguments0Value__, arguments1Value_, arguments2Value_);
               }
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSArrayBufferView(arguments0Value_)) {
               return this.set(arguments0Value_, arguments1Value_, arguments2Value_);
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
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            if (JSGuards.isJSArrayBufferView(arguments0Value_)) {
               int var7;
               this.state_0_ = var7 = state_0 | 1;
               return this.set(arguments0Value_, arguments1Value, arguments2Value);
            }
         }

         if (!JSGuards.isJSArrayBufferView(arguments0Value)) {
            int var6;
            this.state_0_ = var6 = state_0 | 2;
            return this.set(arguments0Value, arguments1Value, arguments2Value);
         } else {
            throw new UnsupportedSpecializationException(
               this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
            );
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
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"set", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"set", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static TypedArrayPrototypeBuiltins.JSArrayBufferViewSetNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TypedArrayPrototypeBuiltinsFactory.JSArrayBufferViewSetNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TypedArrayPrototypeBuiltins.JSArrayBufferViewSubarrayNode.class)
   public static final class JSArrayBufferViewSubarrayNodeGen
      extends TypedArrayPrototypeBuiltins.JSArrayBufferViewSubarrayNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private ValueProfile subarray0_arrayTypeProfile_;
      @CompilerDirectives.CompilationFinal
      private TypedArrayPrototypeBuiltinsFactory.JSArrayBufferViewSubarrayNodeGen.Subarray1Data subarray1_cache;

      private JSArrayBufferViewSubarrayNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return (state_0 & 6) == 0 && state_0 != 0 ? this.execute_int_int0(state_0, frameValue) : this.execute_generic1(state_0, frameValue);
      }

      private Object execute_int_int0(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);

         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var8) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments2Value = this.arguments2_.execute(frameValue);
            return this.executeAndSpecialize(arguments0Value_, var8.getResult(), arguments2Value);
         }

         int arguments2Value_;
         try {
            arguments2Value_ = this.arguments2_.executeInt(frameValue);
         } catch (UnexpectedResultException var7) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, var7.getResult());
         }

         assert (state_0 & 1) != 0;

         if (arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if (JSGuards.isJSArrayBufferView(arguments0Value__)) {
               return this.subarray(arguments0Value__, arguments1Value_, arguments2Value_, this.subarray0_arrayTypeProfile_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
      }

      private Object execute_generic1(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         if ((state_0 & 3) != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if ((state_0 & 1) != 0 && arguments1Value_ instanceof Integer) {
               int arguments1Value__ = (Integer)arguments1Value_;
               if (arguments2Value_ instanceof Integer) {
                  int arguments2Value__ = (Integer)arguments2Value_;
                  if (JSGuards.isJSArrayBufferView(arguments0Value__)) {
                     return this.subarray(arguments0Value__, arguments1Value__, arguments2Value__, this.subarray0_arrayTypeProfile_);
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               TypedArrayPrototypeBuiltinsFactory.JSArrayBufferViewSubarrayNodeGen.Subarray1Data s1_ = this.subarray1_cache;
               if (s1_ != null && JSGuards.isJSArrayBufferView(arguments0Value__)) {
                  return this.subarray(
                     arguments0Value__, arguments1Value_, arguments2Value_, s1_.arrayTypeProfile_, s1_.negativeBegin_, s1_.negativeEnd_, s1_.smallerEnd_
                  );
               }
            }
         }

         if ((state_0 & 4) != 0 && !JSGuards.isJSArrayBufferView(arguments0Value_)) {
            return this.subarrayGeneric(arguments0Value_, arguments1Value_, arguments2Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSTypedArrayObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (arguments1Value instanceof Integer) {
                  int arguments1Value_ = (Integer)arguments1Value;
                  if (arguments2Value instanceof Integer) {
                     int arguments2Value_ = (Integer)arguments2Value;
                     if (JSGuards.isJSArrayBufferView(arguments0Value_)) {
                        this.subarray0_arrayTypeProfile_ = ValueProfile.createIdentityProfile();
                        int var16;
                        this.state_0_ = var16 = state_0 | 1;
                        lock.unlock();
                        hasLock = false;
                        return this.subarray(arguments0Value_, arguments1Value_, arguments2Value_, this.subarray0_arrayTypeProfile_);
                     }
                  }
               }

               if (JSGuards.isJSArrayBufferView(arguments0Value_)) {
                  TypedArrayPrototypeBuiltinsFactory.JSArrayBufferViewSubarrayNodeGen.Subarray1Data s1_ = new TypedArrayPrototypeBuiltinsFactory.JSArrayBufferViewSubarrayNodeGen.Subarray1Data();
                  s1_.arrayTypeProfile_ = ValueProfile.createIdentityProfile();
                  s1_.negativeBegin_ = ConditionProfile.createBinaryProfile();
                  s1_.negativeEnd_ = ConditionProfile.createBinaryProfile();
                  s1_.smallerEnd_ = ConditionProfile.createBinaryProfile();
                  VarHandle.storeStoreFence();
                  this.subarray1_cache = s1_;
                  int var15;
                  this.state_0_ = var15 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.subarray(
                     arguments0Value_, arguments1Value, arguments2Value, s1_.arrayTypeProfile_, s1_.negativeBegin_, s1_.negativeEnd_, s1_.smallerEnd_
                  );
               }
            }

            if (JSGuards.isJSArrayBufferView(arguments0Value)) {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
               );
            } else {
               int var14;
               this.state_0_ = var14 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.subarrayGeneric(arguments0Value, arguments1Value, arguments2Value);
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
         Object[] s = new Object[]{"subarray", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.subarray0_arrayTypeProfile_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"subarray", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            TypedArrayPrototypeBuiltinsFactory.JSArrayBufferViewSubarrayNodeGen.Subarray1Data s1_ = this.subarray1_cache;
            if (s1_ != null) {
               cached.add(Arrays.asList(s1_.arrayTypeProfile_, s1_.negativeBegin_, s1_.negativeEnd_, s1_.smallerEnd_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"subarrayGeneric", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static TypedArrayPrototypeBuiltins.JSArrayBufferViewSubarrayNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TypedArrayPrototypeBuiltinsFactory.JSArrayBufferViewSubarrayNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(TypedArrayPrototypeBuiltins.JSArrayBufferViewSubarrayNode.class)
      private static final class Subarray1Data {
         @CompilerDirectives.CompilationFinal
         ValueProfile arrayTypeProfile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile negativeBegin_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile negativeEnd_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile smallerEnd_;

         Subarray1Data() {
         }
      }
   }
}
