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
import com.oracle.truffle.js.nodes.cast.JSToIntegerAsIntNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerThrowOnInfinityNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.temporal.TemporalRoundDurationNode;
import com.oracle.truffle.js.nodes.temporal.ToLimitedTemporalDurationNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDateNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeZoneNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(TemporalPlainTimePrototypeBuiltins.class)
public final class TemporalPlainTimePrototypeBuiltinsFactory {
   @GeneratedBy(TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeAdd.class)
   public static final class JSTemporalPlainTimeAddNodeGen extends TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeAdd implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ToLimitedTemporalDurationNode toLimitedTemporalDurationNode_;

      private JSTemporalPlainTimeAddNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

      public static TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeAdd create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeAddNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeEquals.class)
   public static final class JSTemporalPlainTimeEqualsNodeGen
      extends TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeEquals
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ToTemporalTimeNode equalsGeneric_toTemporalTime_;

      private JSTemporalPlainTimeEqualsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments1Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments1Value__ = (JSDynamicObject)arguments1Value_;
               if (JSGuards.isJSTemporalTime(arguments1Value__)) {
                  return this.equalsOtherObj(arguments0Value_, arguments1Value__);
               }
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalTime(arguments1Value_)) {
               return this.equalsGeneric(arguments0Value_, arguments1Value_, this.equalsGeneric_toTemporalTime_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments1Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments1Value__ = (JSDynamicObject)arguments1Value_;
               if (JSGuards.isJSTemporalTime(arguments1Value__)) {
                  return this.equalsOtherObj(arguments0Value_, arguments1Value__);
               }
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalTime(arguments1Value_)) {
               return this.equalsGeneric(arguments0Value_, arguments1Value_, this.equalsGeneric_toTemporalTime_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
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
            if (arguments1Value instanceof JSDynamicObject) {
               JSDynamicObject arguments1Value_ = (JSDynamicObject)arguments1Value;
               if (JSGuards.isJSTemporalTime(arguments1Value_)) {
                  int var12;
                  this.state_0_ = var12 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.equalsOtherObj(arguments0Value, arguments1Value_);
               }
            }

            if (JSGuards.isJSTemporalTime(arguments1Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
            } else {
               this.equalsGeneric_toTemporalTime_ = super.insert(ToTemporalTimeNode.create(this.getContext()));
               int var11;
               this.state_0_ = var11 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.equalsGeneric(arguments0Value, arguments1Value, this.equalsGeneric_toTemporalTime_);
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
         Object[] s = new Object[]{"equalsOtherObj", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"equalsGeneric", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.equalsGeneric_toTemporalTime_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeEquals create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeEqualsNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeGetISOFields.class)
   public static final class JSTemporalPlainTimeGetISOFieldsNodeGen
      extends TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeGetISOFields
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSTemporalPlainTimeGetISOFieldsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.getISOFields(arguments0Value_);
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
         Object[] s = new Object[]{"getISOFields", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeGetISOFields create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeGetISOFieldsNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeGetterNode.class)
   public static final class JSTemporalPlainTimeGetterNodeGen
      extends TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeGetterNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSTemporalPlainTimeGetterNodeGen(
         JSContext context, JSBuiltin builtin, TemporalPlainTimePrototypeBuiltins.TemporalPlainTimePrototype property, JavaScriptNode[] arguments
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
            if ((state_0 & 1) != 0 && JSGuards.isJSTemporalTime(arguments0Value_)) {
               return this.timeGetter(arguments0Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalTime(arguments0Value_)) {
               return TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeGetterNode.error(arguments0Value_);
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
            if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalTime(arguments0Value_)) {
               return TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeGetterNode.error(arguments0Value_);
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
         if (JSGuards.isJSTemporalTime(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 1;
            return this.timeGetter(arguments0Value);
         } else if (!JSGuards.isJSTemporalTime(arguments0Value)) {
            int var3;
            this.state_0_ = var3 = state_0 | 2;
            return TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeGetterNode.error(arguments0Value);
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
         Object[] s = new Object[]{"timeGetter", null, null};
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

      public static TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeGetterNode create(
         JSContext context, JSBuiltin builtin, TemporalPlainTimePrototypeBuiltins.TemporalPlainTimePrototype property, JavaScriptNode[] arguments
      ) {
         return new TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeGetterNodeGen(context, builtin, property, arguments);
      }
   }

   @GeneratedBy(TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeRound.class)
   public static final class JSTemporalPlainTimeRoundNodeGen
      extends TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeRound
      implements Introspection.Provider {
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

      private JSTemporalPlainTimeRoundNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

      public static TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeRound create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeRoundNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeSince.class)
   public static final class JSTemporalPlainTimeSinceNodeGen
      extends TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeSince
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
      private TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeSinceNodeGen.SinceData since_cache;

      private JSTemporalPlainTimeSinceNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeSinceNodeGen.SinceData s0_ = this.since_cache;
            if (s0_ != null) {
               return this.since(
                  arguments0Value_,
                  arguments1Value_,
                  arguments2Value_,
                  s0_.toNumber_,
                  s0_.namesNode_,
                  s0_.toTemporalTime_,
                  s0_.equalNode_,
                  s0_.roundDurationNode_
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
            TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeSinceNodeGen.SinceData s0_ = super.insert(
               new TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeSinceNodeGen.SinceData()
            );
            s0_.toNumber_ = s0_.insertAccessor(JSToNumberNode.create());
            s0_.namesNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
            s0_.toTemporalTime_ = s0_.insertAccessor(ToTemporalTimeNode.create(this.getContext()));
            s0_.equalNode_ = s0_.insertAccessor(TruffleString.EqualNode.create());
            s0_.roundDurationNode_ = s0_.insertAccessor(TemporalRoundDurationNode.create(this.getContext()));
            VarHandle.storeStoreFence();
            this.since_cache = s0_;
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.since(
               arguments0Value, arguments1Value, arguments2Value, s0_.toNumber_, s0_.namesNode_, s0_.toTemporalTime_, s0_.equalNode_, s0_.roundDurationNode_
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
         Object[] s = new Object[]{"since", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeSinceNodeGen.SinceData s0_ = this.since_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.toNumber_, s0_.namesNode_, s0_.toTemporalTime_, s0_.equalNode_, s0_.roundDurationNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeSince create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeSinceNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeSince.class)
      private static final class SinceData extends Node {
         @Node.Child
         JSToNumberNode toNumber_;
         @Node.Child
         EnumerableOwnPropertyNamesNode namesNode_;
         @Node.Child
         ToTemporalTimeNode toTemporalTime_;
         @Node.Child
         TruffleString.EqualNode equalNode_;
         @Node.Child
         TemporalRoundDurationNode roundDurationNode_;

         SinceData() {
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

   @GeneratedBy(TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeSubtract.class)
   public static final class JSTemporalPlainTimeSubtractNodeGen
      extends TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeSubtract
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ToLimitedTemporalDurationNode toLimitedTemporalDurationNode_;

      private JSTemporalPlainTimeSubtractNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

      public static TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeSubtract create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeSubtractNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeToLocaleString.class)
   public static final class JSTemporalPlainTimeToLocaleStringNodeGen
      extends TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeToLocaleString
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSTemporalPlainTimeToLocaleStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

      public static TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeToLocaleString create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeToLocaleStringNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeToPlainDateTime.class)
   public static final class JSTemporalPlainTimeToPlainDateTimeNodeGen
      extends TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeToPlainDateTime
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ToTemporalDateNode toTemporalDate_;

      private JSTemporalPlainTimeToPlainDateTimeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.toPlainDateTime(arguments0Value_, arguments1Value_, this.toTemporalDate_);
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
            this.toTemporalDate_ = super.insert(ToTemporalDateNode.create(this.getContext()));
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.toPlainDateTime(arguments0Value, arguments1Value, this.toTemporalDate_);
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
         Object[] s = new Object[]{"toPlainDateTime", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toTemporalDate_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeToPlainDateTime create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeToPlainDateTimeNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeToString.class)
   public static final class JSTemporalPlainTimeToStringNodeGen
      extends TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeToString
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToStringNode toStringNode_;
      @Node.Child
      private TruffleString.EqualNode equalNode_;

      private JSTemporalPlainTimeToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.toString(arguments0Value_, arguments1Value_, this.toStringNode_, this.equalNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private TruffleString executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString var6;
         try {
            int state_0 = this.state_0_;
            this.toStringNode_ = super.insert(JSToStringNode.create());
            this.equalNode_ = super.insert(TruffleString.EqualNode.create());
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.toString(arguments0Value, arguments1Value, this.toStringNode_, this.equalNode_);
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
         Object[] s = new Object[]{"toString", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toStringNode_, this.equalNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeToString create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeToStringNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeToZonedDateTime.class)
   public static final class JSTemporalPlainTimeToZonedDateTimeNodeGen
      extends TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeToZonedDateTime
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ToTemporalDateNode toTemporalDate_;
      @Node.Child
      private ToTemporalTimeZoneNode toTemporalTimeZone_;

      private JSTemporalPlainTimeToZonedDateTimeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.toZonedDateTime(arguments0Value_, arguments1Value_, this.toTemporalDate_, this.toTemporalTimeZone_);
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
            this.toTemporalDate_ = super.insert(ToTemporalDateNode.create(this.getContext()));
            this.toTemporalTimeZone_ = super.insert(ToTemporalTimeZoneNode.create(this.getContext()));
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.toZonedDateTime(arguments0Value, arguments1Value, this.toTemporalDate_, this.toTemporalTimeZone_);
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
            cached.add(Arrays.asList(this.toTemporalDate_, this.toTemporalTimeZone_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeToZonedDateTime create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeToZonedDateTimeNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeUntil.class)
   public static final class JSTemporalPlainTimeUntilNodeGen
      extends TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeUntil
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
      private TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeUntilNodeGen.UntilData until_cache;

      private JSTemporalPlainTimeUntilNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeUntilNodeGen.UntilData s0_ = this.until_cache;
            if (s0_ != null) {
               return this.until(
                  arguments0Value_,
                  arguments1Value_,
                  arguments2Value_,
                  s0_.toNumber_,
                  s0_.namesNode_,
                  s0_.toTemporalTime_,
                  s0_.equalNode_,
                  s0_.roundDurationNode_
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
            TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeUntilNodeGen.UntilData s0_ = super.insert(
               new TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeUntilNodeGen.UntilData()
            );
            s0_.toNumber_ = s0_.insertAccessor(JSToNumberNode.create());
            s0_.namesNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
            s0_.toTemporalTime_ = s0_.insertAccessor(ToTemporalTimeNode.create(this.getContext()));
            s0_.equalNode_ = s0_.insertAccessor(TruffleString.EqualNode.create());
            s0_.roundDurationNode_ = s0_.insertAccessor(TemporalRoundDurationNode.create(this.getContext()));
            VarHandle.storeStoreFence();
            this.until_cache = s0_;
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.until(
               arguments0Value, arguments1Value, arguments2Value, s0_.toNumber_, s0_.namesNode_, s0_.toTemporalTime_, s0_.equalNode_, s0_.roundDurationNode_
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
         Object[] s = new Object[]{"until", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeUntilNodeGen.UntilData s0_ = this.until_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.toNumber_, s0_.namesNode_, s0_.toTemporalTime_, s0_.equalNode_, s0_.roundDurationNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeUntil create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeUntilNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeUntil.class)
      private static final class UntilData extends Node {
         @Node.Child
         JSToNumberNode toNumber_;
         @Node.Child
         EnumerableOwnPropertyNamesNode namesNode_;
         @Node.Child
         ToTemporalTimeNode toTemporalTime_;
         @Node.Child
         TruffleString.EqualNode equalNode_;
         @Node.Child
         TemporalRoundDurationNode roundDurationNode_;

         UntilData() {
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

   @GeneratedBy(TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeValueOf.class)
   public static final class JSTemporalPlainTimeValueOfNodeGen
      extends TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeValueOf
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSTemporalPlainTimeValueOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

      public static TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeValueOf create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeValueOfNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeWith.class)
   public static final class JSTemporalPlainTimeWithNodeGen
      extends TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeWith
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
      private JSToIntegerThrowOnInfinityNode toIntThrows_;
      @Node.Child
      private JSToIntegerAsIntNode toInt_;

      private JSTemporalPlainTimeWithNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.with(arguments0Value_, arguments1Value_, arguments2Value_, this.toIntThrows_, this.toInt_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         JSDynamicObject var7;
         try {
            int state_0 = this.state_0_;
            this.toIntThrows_ = super.insert(JSToIntegerThrowOnInfinityNode.create());
            this.toInt_ = super.insert(JSToIntegerAsIntNode.create());
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.with(arguments0Value, arguments1Value, arguments2Value, this.toIntThrows_, this.toInt_);
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
         Object[] s = new Object[]{"with", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toIntThrows_, this.toInt_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeWith create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeWithNodeGen(context, builtin, arguments);
      }
   }
}
