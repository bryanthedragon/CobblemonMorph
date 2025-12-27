package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSBooleanObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(BooleanPrototypeBuiltins.class)
public final class BooleanPrototypeBuiltinsFactory {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

   @GeneratedBy(BooleanPrototypeBuiltins.JSBooleanToStringNode.class)
   public static final class JSBooleanToStringNodeGen extends BooleanPrototypeBuiltins.JSBooleanToStringNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private BooleanPrototypeBuiltinsFactory.JSBooleanToStringNodeGen.ToStringForeignObject0Data toStringForeignObject0_cache;

      private JSBooleanToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return (state_0 & 29) == 0 && state_0 != 0 ? this.execute_boolean0(state_0, frameValue) : this.execute_generic1(state_0, frameValue);
      }

      private Object execute_boolean0(int state_0, VirtualFrame frameValue) {
         boolean arguments0Value_;
         try {
            arguments0Value_ = this.arguments0_.executeBoolean(frameValue);
         } catch (UnexpectedResultException var5) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(var5.getResult());
         }

         assert (state_0 & 2) != 0;

         return this.toStringPrimitive(arguments0Value_);
      }

      @CompilerDirectives.TruffleBoundary
      private Object toStringForeignObject1Boundary(int state_0, Object arguments0Value_) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         Object var6;
         try {
            InteropLibrary toStringForeignObject1_interop__ = BooleanPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value_);
            var6 = this.toStringForeignObject(arguments0Value_, toStringForeignObject1_interop__);
         } finally {
            encapsulating_.set(prev_);
         }

         return var6;
      }

      @ExplodeLoop
      private Object execute_generic1(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSBooleanObject) {
            JSBooleanObject arguments0Value__ = (JSBooleanObject)arguments0Value_;
            return this.toString(arguments0Value__);
         } else if ((state_0 & 2) != 0 && arguments0Value_ instanceof Boolean) {
            boolean arguments0Value__ = (Boolean)arguments0Value_;
            return this.toStringPrimitive(arguments0Value__);
         } else {
            if ((state_0 & 28) != 0) {
               if ((state_0 & 4) != 0) {
                  for (BooleanPrototypeBuiltinsFactory.JSBooleanToStringNodeGen.ToStringForeignObject0Data s2_ = this.toStringForeignObject0_cache;
                     s2_ != null;
                     s2_ = s2_.next_
                  ) {
                     if (s2_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_)) {
                        return this.toStringForeignObject(arguments0Value_, s2_.interop_);
                     }
                  }
               }

               if ((state_0 & 8) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                  return this.toStringForeignObject1Boundary(state_0, arguments0Value_);
               }

               if ((state_0 & 16) != 0 && fallbackGuard_(state_0, arguments0Value_)) {
                  return this.toStringOther(arguments0Value_);
               }
            }

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
            int exclude = this.exclude_;
            if (arguments0Value instanceof JSBooleanObject) {
               JSBooleanObject arguments0Value_ = (JSBooleanObject)arguments0Value;
               int var22;
               this.state_0_ = var22 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.toString(arguments0Value_);
            } else if (arguments0Value instanceof Boolean) {
               boolean arguments0Value_ = (Boolean)arguments0Value;
               int var21;
               this.state_0_ = var21 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.toStringPrimitive(arguments0Value_);
            } else {
               if (exclude == 0) {
                  int count2_ = 0;
                  BooleanPrototypeBuiltinsFactory.JSBooleanToStringNodeGen.ToStringForeignObject0Data s2_ = this.toStringForeignObject0_cache;
                  if ((state_0 & 4) != 0) {
                     while (s2_ != null && (!s2_.interop_.accepts(arguments0Value) || !JSGuards.isForeignObject(arguments0Value))) {
                        s2_ = s2_.next_;
                        count2_++;
                     }
                  }

                  if (s2_ == null && JSGuards.isForeignObject(arguments0Value) && count2_ < 5) {
                     s2_ = super.insert(
                        new BooleanPrototypeBuiltinsFactory.JSBooleanToStringNodeGen.ToStringForeignObject0Data(this.toStringForeignObject0_cache)
                     );
                     s2_.interop_ = s2_.insertAccessor(BooleanPrototypeBuiltinsFactory.INTEROP_LIBRARY_.create(arguments0Value));
                     VarHandle.storeStoreFence();
                     this.toStringForeignObject0_cache = s2_;
                     this.state_0_ = state_0 |= 4;
                  }

                  if (s2_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.toStringForeignObject(arguments0Value, s2_.interop_);
                  }
               }

               InteropLibrary toStringForeignObject1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  if (JSGuards.isForeignObject(arguments0Value)) {
                     toStringForeignObject1_interop__ = BooleanPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value);
                     int var23;
                     this.exclude_ = var23 = exclude | 1;
                     this.toStringForeignObject0_cache = null;
                     state_0 &= -5;
                     int var20;
                     this.state_0_ = var20 = state_0 | 8;
                     lock.unlock();
                     hasLock = false;
                     return this.toStringForeignObject(arguments0Value, toStringForeignObject1_interop__);
                  }
               } finally {
                  encapsulating_.set(prev_);
               }

               int var18;
               this.state_0_ = var18 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.toStringOther(arguments0Value);
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
               BooleanPrototypeBuiltinsFactory.JSBooleanToStringNodeGen.ToStringForeignObject0Data s2_ = this.toStringForeignObject0_cache;
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
         Object[] s = new Object[]{"toString", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"toStringPrimitive", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"toStringForeignObject", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (BooleanPrototypeBuiltinsFactory.JSBooleanToStringNodeGen.ToStringForeignObject0Data s2_ = this.toStringForeignObject0_cache;
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
         s = new Object[]{"toStringForeignObject", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"toStringOther", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         return Introspection.Provider.create(data);
      }

      private static boolean fallbackGuard_(int state_0, Object arguments0Value) {
         if ((state_0 & 1) == 0 && arguments0Value instanceof JSBooleanObject) {
            return false;
         } else {
            return (state_0 & 2) == 0 && arguments0Value instanceof Boolean ? false : (state_0 & 8) != 0 || !JSGuards.isForeignObject(arguments0Value);
         }
      }

      public static BooleanPrototypeBuiltins.JSBooleanToStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new BooleanPrototypeBuiltinsFactory.JSBooleanToStringNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(BooleanPrototypeBuiltins.JSBooleanToStringNode.class)
      private static final class ToStringForeignObject0Data extends Node {
         @Node.Child
         BooleanPrototypeBuiltinsFactory.JSBooleanToStringNodeGen.ToStringForeignObject0Data next_;
         @Node.Child
         InteropLibrary interop_;

         ToStringForeignObject0Data(BooleanPrototypeBuiltinsFactory.JSBooleanToStringNodeGen.ToStringForeignObject0Data next_) {
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

   @GeneratedBy(BooleanPrototypeBuiltins.JSBooleanValueOfNode.class)
   public static final class JSBooleanValueOfNodeGen extends BooleanPrototypeBuiltins.JSBooleanValueOfNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private BooleanPrototypeBuiltinsFactory.JSBooleanValueOfNodeGen.ValueOfForeignObject0Data valueOfForeignObject0_cache;

      private JSBooleanValueOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return (state_0 & 29) == 0 && state_0 != 0 ? this.execute_boolean0(state_0, frameValue) : this.execute_generic1(state_0, frameValue);
      }

      private Object execute_boolean0(int state_0, VirtualFrame frameValue) {
         boolean arguments0Value_;
         try {
            arguments0Value_ = this.arguments0_.executeBoolean(frameValue);
         } catch (UnexpectedResultException var5) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(var5.getResult());
         }

         assert (state_0 & 2) != 0;

         return this.valueOfPrimitive(arguments0Value_);
      }

      @CompilerDirectives.TruffleBoundary
      private Object valueOfForeignObject1Boundary(int state_0, Object arguments0Value_) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         Boolean var6;
         try {
            InteropLibrary valueOfForeignObject1_interop__ = BooleanPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value_);
            var6 = this.valueOfForeignObject(arguments0Value_, valueOfForeignObject1_interop__);
         } finally {
            encapsulating_.set(prev_);
         }

         return var6;
      }

      @ExplodeLoop
      private Object execute_generic1(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSBooleanObject) {
            JSBooleanObject arguments0Value__ = (JSBooleanObject)arguments0Value_;
            return this.valueOf(arguments0Value__);
         } else if ((state_0 & 2) != 0 && arguments0Value_ instanceof Boolean) {
            boolean arguments0Value__ = (Boolean)arguments0Value_;
            return this.valueOfPrimitive(arguments0Value__);
         } else {
            if ((state_0 & 28) != 0) {
               if ((state_0 & 4) != 0) {
                  for (BooleanPrototypeBuiltinsFactory.JSBooleanValueOfNodeGen.ValueOfForeignObject0Data s2_ = this.valueOfForeignObject0_cache;
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
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         return (state_0 & 29) == 0 && state_0 != 0 ? this.executeBoolean_boolean2(state_0, frameValue) : this.executeBoolean_generic3(state_0, frameValue);
      }

      private boolean executeBoolean_boolean2(int state_0, VirtualFrame frameValue) {
         boolean arguments0Value_;
         try {
            arguments0Value_ = this.arguments0_.executeBoolean(frameValue);
         } catch (UnexpectedResultException var5) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(var5.getResult());
         }

         assert (state_0 & 2) != 0;

         return this.valueOfPrimitive(arguments0Value_);
      }

      @CompilerDirectives.TruffleBoundary
      private boolean valueOfForeignObject1Boundary0(int state_0, Object arguments0Value_) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         boolean var6;
         try {
            InteropLibrary valueOfForeignObject1_interop__ = BooleanPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value_);
            var6 = this.valueOfForeignObject(arguments0Value_, valueOfForeignObject1_interop__);
         } finally {
            encapsulating_.set(prev_);
         }

         return var6;
      }

      @ExplodeLoop
      private boolean executeBoolean_generic3(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSBooleanObject) {
            JSBooleanObject arguments0Value__ = (JSBooleanObject)arguments0Value_;
            return this.valueOf(arguments0Value__);
         } else if ((state_0 & 2) != 0 && arguments0Value_ instanceof Boolean) {
            boolean arguments0Value__ = (Boolean)arguments0Value_;
            return this.valueOfPrimitive(arguments0Value__);
         } else {
            if ((state_0 & 28) != 0) {
               if ((state_0 & 4) != 0) {
                  for (BooleanPrototypeBuiltinsFactory.JSBooleanValueOfNodeGen.ValueOfForeignObject0Data s2_ = this.valueOfForeignObject0_cache;
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

               if ((state_0 & 16) != 0 && fallbackGuard_(state_0, arguments0Value_)) {
                  return this.valueOfOther(arguments0Value_);
               }
            }

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

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments0Value instanceof JSBooleanObject) {
               JSBooleanObject arguments0Value_ = (JSBooleanObject)arguments0Value;
               int var22;
               this.state_0_ = var22 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.valueOf(arguments0Value_);
            } else if (arguments0Value instanceof Boolean) {
               boolean arguments0Value_ = (Boolean)arguments0Value;
               int var21;
               this.state_0_ = var21 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.valueOfPrimitive(arguments0Value_);
            } else {
               if (exclude == 0) {
                  int count2_ = 0;
                  BooleanPrototypeBuiltinsFactory.JSBooleanValueOfNodeGen.ValueOfForeignObject0Data s2_ = this.valueOfForeignObject0_cache;
                  if ((state_0 & 4) != 0) {
                     while (s2_ != null && (!s2_.interop_.accepts(arguments0Value) || !JSGuards.isForeignObject(arguments0Value))) {
                        s2_ = s2_.next_;
                        count2_++;
                     }
                  }

                  if (s2_ == null && JSGuards.isForeignObject(arguments0Value) && count2_ < 5) {
                     s2_ = super.insert(new BooleanPrototypeBuiltinsFactory.JSBooleanValueOfNodeGen.ValueOfForeignObject0Data(this.valueOfForeignObject0_cache));
                     s2_.interop_ = s2_.insertAccessor(BooleanPrototypeBuiltinsFactory.INTEROP_LIBRARY_.create(arguments0Value));
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
                     valueOfForeignObject1_interop__ = BooleanPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value);
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
               BooleanPrototypeBuiltinsFactory.JSBooleanValueOfNodeGen.ValueOfForeignObject0Data s2_ = this.valueOfForeignObject0_cache;
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

            for (BooleanPrototypeBuiltinsFactory.JSBooleanValueOfNodeGen.ValueOfForeignObject0Data s2_ = this.valueOfForeignObject0_cache;
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
         if ((state_0 & 1) == 0 && arguments0Value instanceof JSBooleanObject) {
            return false;
         } else {
            return (state_0 & 2) == 0 && arguments0Value instanceof Boolean ? false : (state_0 & 8) != 0 || !JSGuards.isForeignObject(arguments0Value);
         }
      }

      public static BooleanPrototypeBuiltins.JSBooleanValueOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new BooleanPrototypeBuiltinsFactory.JSBooleanValueOfNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(BooleanPrototypeBuiltins.JSBooleanValueOfNode.class)
      private static final class ValueOfForeignObject0Data extends Node {
         @Node.Child
         BooleanPrototypeBuiltinsFactory.JSBooleanValueOfNodeGen.ValueOfForeignObject0Data next_;
         @Node.Child
         InteropLibrary interop_;

         ValueOfForeignObject0Data(BooleanPrototypeBuiltinsFactory.JSBooleanValueOfNodeGen.ValueOfForeignObject0Data next_) {
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
