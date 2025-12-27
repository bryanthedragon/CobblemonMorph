package com.oracle.truffle.js.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.temporal.ToLimitedTemporalDurationNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalCalendarNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalInstantNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeZoneNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(TemporalInstantPrototypeBuiltins.class)
public final class TemporalInstantPrototypeBuiltinsFactory {
   @GeneratedBy(TemporalInstantPrototypeBuiltins.JSTemporalInstantAdd.class)
   public static final class JSTemporalInstantAddNodeGen extends TemporalInstantPrototypeBuiltins.JSTemporalInstantAdd implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ToLimitedTemporalDurationNode toLimitedTemporalDurationNode_;

      private JSTemporalInstantAddNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.add(arguments0Value_, arguments1Value_, this.toLimitedTemporalDurationNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         JSDynamicObject var6;
         try {
            int state_0 = this.state_0_;
            this.toLimitedTemporalDurationNode_ = super.insert(ToLimitedTemporalDurationNode.create());
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.add(arguments0Value, arguments1Value, this.toLimitedTemporalDurationNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var6;
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
         Object[] s = new Object[]{"add", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toLimitedTemporalDurationNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalInstantPrototypeBuiltins.JSTemporalInstantAdd create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantAddNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalInstantPrototypeBuiltins.JSTemporalInstantEquals.class)
   public static final class JSTemporalInstantEqualsNodeGen extends TemporalInstantPrototypeBuiltins.JSTemporalInstantEquals implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ToTemporalInstantNode toTemporalInstantNode_;

      private JSTemporalInstantEqualsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.equals(arguments0Value_, arguments1Value_, this.toTemporalInstantNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            return this.equals(arguments0Value_, arguments1Value_, this.toTemporalInstantNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      private boolean executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         boolean var6;
         try {
            int state_0 = this.state_0_;
            this.toTemporalInstantNode_ = super.insert(ToTemporalInstantNode.create(this.getContext()));
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.equals(arguments0Value, arguments1Value, this.toTemporalInstantNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var6;
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
         Object[] s = new Object[]{"equals", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toTemporalInstantNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalInstantPrototypeBuiltins.JSTemporalInstantEquals create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantEqualsNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalInstantPrototypeBuiltins.JSTemporalInstantGetterNode.class)
   public static final class JSTemporalInstantGetterNodeGen
      extends TemporalInstantPrototypeBuiltins.JSTemporalInstantGetterNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSTemporalInstantGetterNodeGen(
         JSContext context, JSBuiltin builtin, TemporalInstantPrototypeBuiltins.TemporalInstantPrototype property, JavaScriptNode[] arguments
      ) {
         super(context, builtin, property);
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
            if ((state_0 & 1) != 0 && JSGuards.isJSTemporalInstant(arguments0Value_)) {
               return this.instantGetter(arguments0Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalInstant(arguments0Value_)) {
               return TemporalInstantPrototypeBuiltins.JSTemporalInstantGetterNode.error(arguments0Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_);
      }

      @Override
      public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         if ((state_0 & 1) != 0) {
            return JSTypesGen.expectInteger(this.execute(frameValue));
         } else {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalInstant(arguments0Value_)) {
               return TemporalInstantPrototypeBuiltins.JSTemporalInstantGetterNode.error(arguments0Value_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_));
            }
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         int state_0 = this.state_0_;

         try {
            if ((state_0 & 1) == 0 && state_0 != 0) {
               this.executeInt(frameValue);
            } else {
               this.execute(frameValue);
            }
         } catch (UnexpectedResultException var4) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
         }
      }

      private Object executeAndSpecialize(Object arguments0Value) {
         int state_0 = this.state_0_;
         if (JSGuards.isJSTemporalInstant(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 1;
            return this.instantGetter(arguments0Value);
         } else if (!JSGuards.isJSTemporalInstant(arguments0Value)) {
            int var3;
            this.state_0_ = var3 = state_0 | 2;
            return TemporalInstantPrototypeBuiltins.JSTemporalInstantGetterNode.error(arguments0Value);
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
         Object[] s = new Object[]{"instantGetter", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"error", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalInstantPrototypeBuiltins.JSTemporalInstantGetterNode create(
         JSContext context, JSBuiltin builtin, TemporalInstantPrototypeBuiltins.TemporalInstantPrototype property, JavaScriptNode[] arguments
      ) {
         return new TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantGetterNodeGen(context, builtin, property, arguments);
      }
   }

   @GeneratedBy(TemporalInstantPrototypeBuiltins.JSTemporalInstantRound.class)
   public static final class JSTemporalInstantRoundNodeGen extends TemporalInstantPrototypeBuiltins.JSTemporalInstantRound implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToNumberNode toNumber_;
      @Node.Child
      private TruffleString.EqualNode equalNode_;

      private JSTemporalInstantRoundNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.round(arguments0Value_, arguments1Value_, this.toNumber_, this.equalNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         JSDynamicObject var6;
         try {
            int state_0 = this.state_0_;
            this.toNumber_ = super.insert(JSToNumberNode.create());
            this.equalNode_ = super.insert(TruffleString.EqualNode.create());
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.round(arguments0Value, arguments1Value, this.toNumber_, this.equalNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var6;
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
         Object[] s = new Object[]{"round", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toNumber_, this.equalNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalInstantPrototypeBuiltins.JSTemporalInstantRound create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantRoundNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalInstantPrototypeBuiltins.JSTemporalInstantSubtract.class)
   public static final class JSTemporalInstantSubtractNodeGen
      extends TemporalInstantPrototypeBuiltins.JSTemporalInstantSubtract
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ToLimitedTemporalDurationNode toLimitedTemporalDurationNode_;

      private JSTemporalInstantSubtractNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.subtract(arguments0Value_, arguments1Value_, this.toLimitedTemporalDurationNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         JSDynamicObject var6;
         try {
            int state_0 = this.state_0_;
            this.toLimitedTemporalDurationNode_ = super.insert(ToLimitedTemporalDurationNode.create());
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.subtract(arguments0Value, arguments1Value, this.toLimitedTemporalDurationNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var6;
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
         Object[] s = new Object[]{"subtract", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toLimitedTemporalDurationNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalInstantPrototypeBuiltins.JSTemporalInstantSubtract create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantSubtractNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalInstantPrototypeBuiltins.JSTemporalInstantToLocaleString.class)
   public static final class JSTemporalInstantToLocaleStringNodeGen
      extends TemporalInstantPrototypeBuiltins.JSTemporalInstantToLocaleString
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSTemporalInstantToLocaleStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.toLocaleString(arguments0Value_);
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
         Object[] s = new Object[]{"toLocaleString", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalInstantPrototypeBuiltins.JSTemporalInstantToLocaleString create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantToLocaleStringNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalInstantPrototypeBuiltins.JSTemporalInstantToString.class)
   public static final class JSTemporalInstantToStringNodeGen
      extends TemporalInstantPrototypeBuiltins.JSTemporalInstantToString
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantToStringNodeGen.ToStringData toString_cache;

      private JSTemporalInstantToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantToStringNodeGen.ToStringData s0_ = this.toString_cache;
            if (s0_ != null) {
               return this.toString(arguments0Value_, arguments1Value_, s0_.toTemporalTimeZone_, s0_.toStringNode_, s0_.equalNode_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private TruffleString executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString var7;
         try {
            int state_0 = this.state_0_;
            TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantToStringNodeGen.ToStringData s0_ = super.insert(
               new TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantToStringNodeGen.ToStringData()
            );
            s0_.toTemporalTimeZone_ = s0_.insertAccessor(ToTemporalTimeZoneNode.create(this.getContext()));
            s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
            s0_.equalNode_ = s0_.insertAccessor(TruffleString.EqualNode.create());
            VarHandle.storeStoreFence();
            this.toString_cache = s0_;
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.toString(arguments0Value, arguments1Value, s0_.toTemporalTimeZone_, s0_.toStringNode_, s0_.equalNode_);
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
         Object[] s = new Object[]{"toString", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantToStringNodeGen.ToStringData s0_ = this.toString_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.toTemporalTimeZone_, s0_.toStringNode_, s0_.equalNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalInstantPrototypeBuiltins.JSTemporalInstantToString create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantToStringNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(TemporalInstantPrototypeBuiltins.JSTemporalInstantToString.class)
      private static final class ToStringData extends Node {
         @Node.Child
         ToTemporalTimeZoneNode toTemporalTimeZone_;
         @Node.Child
         JSToStringNode toStringNode_;
         @Node.Child
         TruffleString.EqualNode equalNode_;

         ToStringData() {
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

   @GeneratedBy(TemporalInstantPrototypeBuiltins.JSTemporalInstantToZonedDateTimeISONode.class)
   public static final class JSTemporalInstantToZonedDateTimeISONodeGen
      extends TemporalInstantPrototypeBuiltins.JSTemporalInstantToZonedDateTimeISONode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ToTemporalTimeZoneNode toTemporalTimeZone_;

      private JSTemporalInstantToZonedDateTimeISONodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.toZonedDateTimeISO(arguments0Value_, arguments1Value_, this.toTemporalTimeZone_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         Object var6;
         try {
            int state_0 = this.state_0_;
            this.toTemporalTimeZone_ = super.insert(ToTemporalTimeZoneNode.create(this.getContext()));
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.toZonedDateTimeISO(arguments0Value, arguments1Value, this.toTemporalTimeZone_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var6;
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
         Object[] s = new Object[]{"toZonedDateTimeISO", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toTemporalTimeZone_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalInstantPrototypeBuiltins.JSTemporalInstantToZonedDateTimeISONode create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantToZonedDateTimeISONodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalInstantPrototypeBuiltins.JSTemporalInstantToZonedDateTimeNode.class)
   public static final class JSTemporalInstantToZonedDateTimeNodeGen
      extends TemporalInstantPrototypeBuiltins.JSTemporalInstantToZonedDateTimeNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ToTemporalCalendarNode toTemporalCalendar_;
      @Node.Child
      private ToTemporalTimeZoneNode toTemporalTimeZone_;

      private JSTemporalInstantToZonedDateTimeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.toZonedDateTime(arguments0Value_, arguments1Value_, this.toTemporalCalendar_, this.toTemporalTimeZone_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         JSDynamicObject var6;
         try {
            int state_0 = this.state_0_;
            this.toTemporalCalendar_ = super.insert(ToTemporalCalendarNode.create(this.getContext()));
            this.toTemporalTimeZone_ = super.insert(ToTemporalTimeZoneNode.create(this.getContext()));
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.toZonedDateTime(arguments0Value, arguments1Value, this.toTemporalCalendar_, this.toTemporalTimeZone_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var6;
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
         Object[] s = new Object[]{"toZonedDateTime", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toTemporalCalendar_, this.toTemporalTimeZone_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalInstantPrototypeBuiltins.JSTemporalInstantToZonedDateTimeNode create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantToZonedDateTimeNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalInstantPrototypeBuiltins.JSTemporalInstantUntilSinceNode.class)
   public static final class JSTemporalInstantUntilSinceNodeGen
      extends TemporalInstantPrototypeBuiltins.JSTemporalInstantUntilSinceNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantUntilSinceNodeGen.UntilOrSinceData untilOrSince_cache;

      private JSTemporalInstantUntilSinceNodeGen(JSContext context, JSBuiltin builtin, boolean isUntil, JavaScriptNode[] arguments) {
         super(context, builtin, isUntil);
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
            TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantUntilSinceNodeGen.UntilOrSinceData s0_ = this.untilOrSince_cache;
            if (s0_ != null) {
               return this.untilOrSince(
                  arguments0Value_, arguments1Value_, arguments2Value_, s0_.toNumber_, s0_.namesNode_, s0_.equalNode_, s0_.toTemporalInstantNode_
               );
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
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         JSDynamicObject var8;
         try {
            int state_0 = this.state_0_;
            TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantUntilSinceNodeGen.UntilOrSinceData s0_ = super.insert(
               new TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantUntilSinceNodeGen.UntilOrSinceData()
            );
            s0_.toNumber_ = s0_.insertAccessor(JSToNumberNode.create());
            s0_.namesNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
            s0_.equalNode_ = s0_.insertAccessor(TruffleString.EqualNode.create());
            s0_.toTemporalInstantNode_ = s0_.insertAccessor(ToTemporalInstantNode.create(this.getContext()));
            VarHandle.storeStoreFence();
            this.untilOrSince_cache = s0_;
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.untilOrSince(
               arguments0Value, arguments1Value, arguments2Value, s0_.toNumber_, s0_.namesNode_, s0_.equalNode_, s0_.toTemporalInstantNode_
            );
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
         Object[] s = new Object[]{"untilOrSince", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantUntilSinceNodeGen.UntilOrSinceData s0_ = this.untilOrSince_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.toNumber_, s0_.namesNode_, s0_.equalNode_, s0_.toTemporalInstantNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalInstantPrototypeBuiltins.JSTemporalInstantUntilSinceNode create(
         JSContext context, JSBuiltin builtin, boolean isUntil, JavaScriptNode[] arguments
      ) {
         return new TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantUntilSinceNodeGen(context, builtin, isUntil, arguments);
      }

      @GeneratedBy(TemporalInstantPrototypeBuiltins.JSTemporalInstantUntilSinceNode.class)
      private static final class UntilOrSinceData extends Node {
         @Node.Child
         JSToNumberNode toNumber_;
         @Node.Child
         EnumerableOwnPropertyNamesNode namesNode_;
         @Node.Child
         TruffleString.EqualNode equalNode_;
         @Node.Child
         ToTemporalInstantNode toTemporalInstantNode_;

         UntilOrSinceData() {
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

   @GeneratedBy(TemporalInstantPrototypeBuiltins.JSTemporalInstantValueOf.class)
   public static final class JSTemporalInstantValueOfNodeGen
      extends TemporalInstantPrototypeBuiltins.JSTemporalInstantValueOf
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSTemporalInstantValueOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.valueOf(arguments0Value_);
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
         Object[] s = new Object[]{"valueOf", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalInstantPrototypeBuiltins.JSTemporalInstantValueOf create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantValueOfNodeGen(context, builtin, arguments);
      }
   }
}
