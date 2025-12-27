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
import com.oracle.truffle.js.nodes.cast.JSNumberToBigIntNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarDateFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalDurationAddNode;
import com.oracle.truffle.js.nodes.temporal.TemporalGetOptionNode;
import com.oracle.truffle.js.nodes.temporal.TemporalMonthDayFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalRoundDurationNode;
import com.oracle.truffle.js.nodes.temporal.TemporalYearMonthFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.ToLimitedTemporalDurationNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalCalendarNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDateNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeZoneNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalZonedDateTimeNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(TemporalZonedDateTimePrototypeBuiltins.class)
public final class TemporalZonedDateTimePrototypeBuiltinsFactory {
   @GeneratedBy(TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeAdd.class)
   public static final class JSTemporalZonedDateTimeAddNodeGen
      extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeAdd
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
      private JSNumberToBigIntNode toBigInt_;
      @Node.Child
      private ToLimitedTemporalDurationNode toLimitedTemporalDurationNode_;

      private JSTemporalZonedDateTimeAddNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.add(arguments0Value_, arguments1Value_, arguments2Value_, this.toBigInt_, this.toLimitedTemporalDurationNode_);
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
            this.toBigInt_ = super.insert(JSNumberToBigIntNode.create());
            this.toLimitedTemporalDurationNode_ = super.insert(ToLimitedTemporalDurationNode.create());
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.add(arguments0Value, arguments1Value, arguments2Value, this.toBigInt_, this.toLimitedTemporalDurationNode_);
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
         Object[] s = new Object[]{"add", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toBigInt_, this.toLimitedTemporalDurationNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeAdd create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeAddNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeEquals.class)
   public static final class JSTemporalZonedDateTimeEqualsNodeGen
      extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeEquals
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ToTemporalZonedDateTimeNode toTemporalZonedDateTime_;
      @Node.Child
      private JSToStringNode toStringNode_;

      private JSTemporalZonedDateTimeEqualsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.equals(arguments0Value_, arguments1Value_, this.toTemporalZonedDateTime_, this.toStringNode_);
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
            this.toTemporalZonedDateTime_ = super.insert(ToTemporalZonedDateTimeNode.create(this.getContext()));
            this.toStringNode_ = super.insert(JSToStringNode.create());
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.equals(arguments0Value, arguments1Value, this.toTemporalZonedDateTime_, this.toStringNode_);
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
            cached.add(Arrays.asList(this.toTemporalZonedDateTime_, this.toStringNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeEquals create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeEqualsNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeGetISOFields.class)
   public static final class JSTemporalZonedDateTimeGetISOFieldsNodeGen
      extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeGetISOFields
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSTemporalZonedDateTimeGetISOFieldsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

      public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeGetISOFields create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeGetISOFieldsNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeGetterNode.class)
   public static final class JSTemporalZonedDateTimeGetterNodeGen
      extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeGetterNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSTemporalZonedDateTimeGetterNodeGen(
         JSContext context, JSBuiltin builtin, TemporalZonedDateTimePrototypeBuiltins.TemporalZonedDateTimePrototype property, JavaScriptNode[] arguments
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
            if ((state_0 & 1) != 0 && JSGuards.isJSTemporalZonedDateTime(arguments0Value_)) {
               return this.zonedDateTimeGetter(arguments0Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalZonedDateTime(arguments0Value_)) {
               return TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeGetterNode.error(arguments0Value_);
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
            if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalZonedDateTime(arguments0Value_)) {
               return TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeGetterNode.error(arguments0Value_);
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
         if (JSGuards.isJSTemporalZonedDateTime(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 1;
            return this.zonedDateTimeGetter(arguments0Value);
         } else if (!JSGuards.isJSTemporalZonedDateTime(arguments0Value)) {
            int var3;
            this.state_0_ = var3 = state_0 | 2;
            return TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeGetterNode.error(arguments0Value);
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
         Object[] s = new Object[]{"zonedDateTimeGetter", null, null};
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

      public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeGetterNode create(
         JSContext context, JSBuiltin builtin, TemporalZonedDateTimePrototypeBuiltins.TemporalZonedDateTimePrototype property, JavaScriptNode[] arguments
      ) {
         return new TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeGetterNodeGen(context, builtin, property, arguments);
      }
   }

   @GeneratedBy(TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeRound.class)
   public static final class JSTemporalZonedDateTimeRoundNodeGen
      extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeRound
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

      private JSTemporalZonedDateTimeRoundNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

      public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeRound create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeRoundNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeSince.class)
   public static final class JSTemporalZonedDateTimeSinceNodeGen
      extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeSince
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
      private TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeSinceNodeGen.SinceData since_cache;

      private JSTemporalZonedDateTimeSinceNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeSinceNodeGen.SinceData s0_ = this.since_cache;
            if (s0_ != null) {
               return this.since(
                  arguments0Value_,
                  arguments1Value_,
                  arguments2Value_,
                  s0_.toNumber_,
                  s0_.namesNode_,
                  s0_.toTemporalZonedDateTime_,
                  s0_.toStringNode_,
                  s0_.equalNode_,
                  s0_.durationAddNode_,
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

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         Object var8;
         try {
            int state_0 = this.state_0_;
            TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeSinceNodeGen.SinceData s0_ = super.insert(
               new TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeSinceNodeGen.SinceData()
            );
            s0_.toNumber_ = s0_.insertAccessor(JSToNumberNode.create());
            s0_.namesNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
            s0_.toTemporalZonedDateTime_ = s0_.insertAccessor(ToTemporalZonedDateTimeNode.create(this.getContext()));
            s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
            s0_.equalNode_ = s0_.insertAccessor(TruffleString.EqualNode.create());
            s0_.durationAddNode_ = s0_.insertAccessor(TemporalDurationAddNode.create(this.getContext()));
            s0_.roundDurationNode_ = s0_.insertAccessor(TemporalRoundDurationNode.create(this.getContext()));
            VarHandle.storeStoreFence();
            this.since_cache = s0_;
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.since(
               arguments0Value,
               arguments1Value,
               arguments2Value,
               s0_.toNumber_,
               s0_.namesNode_,
               s0_.toTemporalZonedDateTime_,
               s0_.toStringNode_,
               s0_.equalNode_,
               s0_.durationAddNode_,
               s0_.roundDurationNode_
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
            TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeSinceNodeGen.SinceData s0_ = this.since_cache;
            if (s0_ != null) {
               cached.add(
                  Arrays.asList(
                     s0_.toNumber_,
                     s0_.namesNode_,
                     s0_.toTemporalZonedDateTime_,
                     s0_.toStringNode_,
                     s0_.equalNode_,
                     s0_.durationAddNode_,
                     s0_.roundDurationNode_
                  )
               );
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeSince create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeSinceNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeSince.class)
      private static final class SinceData extends Node {
         @Node.Child
         JSToNumberNode toNumber_;
         @Node.Child
         EnumerableOwnPropertyNamesNode namesNode_;
         @Node.Child
         ToTemporalZonedDateTimeNode toTemporalZonedDateTime_;
         @Node.Child
         JSToStringNode toStringNode_;
         @Node.Child
         TruffleString.EqualNode equalNode_;
         @Node.Child
         TemporalDurationAddNode durationAddNode_;
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

   @GeneratedBy(TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeStartOfDay.class)
   public static final class JSTemporalZonedDateTimeStartOfDayNodeGen
      extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeStartOfDay
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSTemporalZonedDateTimeStartOfDayNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.startOfDay(arguments0Value_);
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
         Object[] s = new Object[]{"startOfDay", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeStartOfDay create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeStartOfDayNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeSubtract.class)
   public static final class JSTemporalZonedDateTimeSubtractNodeGen
      extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeSubtract
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
      private JSNumberToBigIntNode toBigInt_;
      @Node.Child
      private ToLimitedTemporalDurationNode toLimitedTemporalDurationNode_;

      private JSTemporalZonedDateTimeSubtractNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.subtract(arguments0Value_, arguments1Value_, arguments2Value_, this.toBigInt_, this.toLimitedTemporalDurationNode_);
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
            this.toBigInt_ = super.insert(JSNumberToBigIntNode.create());
            this.toLimitedTemporalDurationNode_ = super.insert(ToLimitedTemporalDurationNode.create());
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.subtract(arguments0Value, arguments1Value, arguments2Value, this.toBigInt_, this.toLimitedTemporalDurationNode_);
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
         Object[] s = new Object[]{"subtract", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toBigInt_, this.toLimitedTemporalDurationNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeSubtract create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeSubtractNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToInstant.class)
   public static final class JSTemporalZonedDateTimeToInstantNodeGen
      extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToInstant
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSTemporalZonedDateTimeToInstantNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.toInstant(arguments0Value_);
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
         Object[] s = new Object[]{"toInstant", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToInstant create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeToInstantNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToLocaleString.class)
   public static final class JSTemporalZonedDateTimeToLocaleStringNodeGen
      extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToLocaleString
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSTemporalZonedDateTimeToLocaleStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

      public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToLocaleString create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeToLocaleStringNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainDate.class)
   public static final class JSTemporalZonedDateTimeToPlainDateNodeGen
      extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainDate
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSTemporalZonedDateTimeToPlainDateNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.toPlainDate(arguments0Value_);
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
         Object[] s = new Object[]{"toPlainDate", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainDate create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeToPlainDateNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainDateTime.class)
   public static final class JSTemporalZonedDateTimeToPlainDateTimeNodeGen
      extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainDateTime
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSTemporalZonedDateTimeToPlainDateTimeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.toPlainDateTime(arguments0Value_);
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
         Object[] s = new Object[]{"toPlainDateTime", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainDateTime create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeToPlainDateTimeNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainMonthDay.class)
   public static final class JSTemporalZonedDateTimeToPlainMonthDayNodeGen
      extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainMonthDay
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TemporalMonthDayFromFieldsNode monthDayFromFieldsNode_;
      @Node.Child
      private TemporalCalendarFieldsNode calendarFieldsNode_;

      private JSTemporalZonedDateTimeToPlainMonthDayNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.toPlainMonthDay(arguments0Value_, this.monthDayFromFieldsNode_, this.calendarFieldsNode_);
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

         Object var5;
         try {
            int state_0 = this.state_0_;
            this.monthDayFromFieldsNode_ = super.insert(TemporalMonthDayFromFieldsNode.create(this.getContext()));
            this.calendarFieldsNode_ = super.insert(TemporalCalendarFieldsNode.create(this.getContext()));
            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var5 = this.toPlainMonthDay(arguments0Value, this.monthDayFromFieldsNode_, this.calendarFieldsNode_);
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
         Object[] s = new Object[]{"toPlainMonthDay", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.monthDayFromFieldsNode_, this.calendarFieldsNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainMonthDay create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeToPlainMonthDayNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainTime.class)
   public static final class JSTemporalZonedDateTimeToPlainTimeNodeGen
      extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainTime
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSTemporalZonedDateTimeToPlainTimeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.toPlainTime(arguments0Value_);
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
         Object[] s = new Object[]{"toPlainTime", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainTime create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeToPlainTimeNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainYearMonth.class)
   public static final class JSTemporalZonedDateTimeToPlainYearMonthNodeGen
      extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainYearMonth
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TemporalYearMonthFromFieldsNode yearMonthFromFieldsNode_;
      @Node.Child
      private TemporalCalendarFieldsNode calendarFieldsNode_;

      private JSTemporalZonedDateTimeToPlainYearMonthNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.toPlainYearMonth(arguments0Value_, this.yearMonthFromFieldsNode_, this.calendarFieldsNode_);
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

         Object var5;
         try {
            int state_0 = this.state_0_;
            this.yearMonthFromFieldsNode_ = super.insert(TemporalYearMonthFromFieldsNode.create(this.getContext()));
            this.calendarFieldsNode_ = super.insert(TemporalCalendarFieldsNode.create(this.getContext()));
            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var5 = this.toPlainYearMonth(arguments0Value, this.yearMonthFromFieldsNode_, this.calendarFieldsNode_);
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
         Object[] s = new Object[]{"toPlainYearMonth", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.yearMonthFromFieldsNode_, this.calendarFieldsNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainYearMonth create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeToPlainYearMonthNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToString.class)
   public static final class JSTemporalZonedDateTimeToStringNodeGen
      extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToString
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

      private JSTemporalZonedDateTimeToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

      public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToString create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeToStringNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeUntil.class)
   public static final class JSTemporalZonedDateTimeUntilNodeGen
      extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeUntil
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
      private TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeUntilNodeGen.UntilData until_cache;

      private JSTemporalZonedDateTimeUntilNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeUntilNodeGen.UntilData s0_ = this.until_cache;
            if (s0_ != null) {
               return this.until(
                  arguments0Value_,
                  arguments1Value_,
                  arguments2Value_,
                  s0_.toNumber_,
                  s0_.namesNode_,
                  s0_.toTemporalZonedDateTime_,
                  s0_.toStringNode_,
                  s0_.equalNode_,
                  s0_.durationAddNode_,
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

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         Object var8;
         try {
            int state_0 = this.state_0_;
            TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeUntilNodeGen.UntilData s0_ = super.insert(
               new TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeUntilNodeGen.UntilData()
            );
            s0_.toNumber_ = s0_.insertAccessor(JSToNumberNode.create());
            s0_.namesNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
            s0_.toTemporalZonedDateTime_ = s0_.insertAccessor(ToTemporalZonedDateTimeNode.create(this.getContext()));
            s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
            s0_.equalNode_ = s0_.insertAccessor(TruffleString.EqualNode.create());
            s0_.durationAddNode_ = s0_.insertAccessor(TemporalDurationAddNode.create(this.getContext()));
            s0_.roundDurationNode_ = s0_.insertAccessor(TemporalRoundDurationNode.create(this.getContext()));
            VarHandle.storeStoreFence();
            this.until_cache = s0_;
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.until(
               arguments0Value,
               arguments1Value,
               arguments2Value,
               s0_.toNumber_,
               s0_.namesNode_,
               s0_.toTemporalZonedDateTime_,
               s0_.toStringNode_,
               s0_.equalNode_,
               s0_.durationAddNode_,
               s0_.roundDurationNode_
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
            TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeUntilNodeGen.UntilData s0_ = this.until_cache;
            if (s0_ != null) {
               cached.add(
                  Arrays.asList(
                     s0_.toNumber_,
                     s0_.namesNode_,
                     s0_.toTemporalZonedDateTime_,
                     s0_.toStringNode_,
                     s0_.equalNode_,
                     s0_.durationAddNode_,
                     s0_.roundDurationNode_
                  )
               );
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeUntil create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeUntilNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeUntil.class)
      private static final class UntilData extends Node {
         @Node.Child
         JSToNumberNode toNumber_;
         @Node.Child
         EnumerableOwnPropertyNamesNode namesNode_;
         @Node.Child
         ToTemporalZonedDateTimeNode toTemporalZonedDateTime_;
         @Node.Child
         JSToStringNode toStringNode_;
         @Node.Child
         TruffleString.EqualNode equalNode_;
         @Node.Child
         TemporalDurationAddNode durationAddNode_;
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

   @GeneratedBy(TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeValueOf.class)
   public static final class JSTemporalZonedDateTimeValueOfNodeGen
      extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeValueOf
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSTemporalZonedDateTimeValueOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

      public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeValueOf create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeValueOfNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWithCalendar.class)
   public static final class JSTemporalZonedDateTimeWithCalendarNodeGen
      extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWithCalendar
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ToTemporalCalendarNode toTemporalCalendar_;

      private JSTemporalZonedDateTimeWithCalendarNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.withCalendar(arguments0Value_, arguments1Value_, this.toTemporalCalendar_);
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
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.withCalendar(arguments0Value, arguments1Value, this.toTemporalCalendar_);
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
         Object[] s = new Object[]{"withCalendar", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toTemporalCalendar_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWithCalendar create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeWithCalendarNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWith.class)
   public static final class JSTemporalZonedDateTimeWithNodeGen
      extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWith
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
      private TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeWithNodeGen.WithData with_cache;

      private JSTemporalZonedDateTimeWithNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeWithNodeGen.WithData s0_ = this.with_cache;
            if (s0_ != null) {
               return this.with(
                  arguments0Value_,
                  arguments1Value_,
                  arguments2Value_,
                  s0_.namesNode_,
                  s0_.getOptionNode_,
                  s0_.equalNode_,
                  s0_.calendarFieldsNode_,
                  s0_.dateFromFieldsNode_
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

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         Object var8;
         try {
            int state_0 = this.state_0_;
            TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeWithNodeGen.WithData s0_ = super.insert(
               new TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeWithNodeGen.WithData()
            );
            s0_.namesNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
            s0_.getOptionNode_ = s0_.insertAccessor(TemporalGetOptionNode.create());
            s0_.equalNode_ = s0_.insertAccessor(TruffleString.EqualNode.create());
            s0_.calendarFieldsNode_ = s0_.insertAccessor(TemporalCalendarFieldsNode.create(this.getContext()));
            s0_.dateFromFieldsNode_ = s0_.insertAccessor(TemporalCalendarDateFromFieldsNode.create(this.getContext()));
            VarHandle.storeStoreFence();
            this.with_cache = s0_;
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.with(
               arguments0Value,
               arguments1Value,
               arguments2Value,
               s0_.namesNode_,
               s0_.getOptionNode_,
               s0_.equalNode_,
               s0_.calendarFieldsNode_,
               s0_.dateFromFieldsNode_
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
         Object[] s = new Object[]{"with", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeWithNodeGen.WithData s0_ = this.with_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.namesNode_, s0_.getOptionNode_, s0_.equalNode_, s0_.calendarFieldsNode_, s0_.dateFromFieldsNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWith create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeWithNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWith.class)
      private static final class WithData extends Node {
         @Node.Child
         EnumerableOwnPropertyNamesNode namesNode_;
         @Node.Child
         TemporalGetOptionNode getOptionNode_;
         @Node.Child
         TruffleString.EqualNode equalNode_;
         @Node.Child
         TemporalCalendarFieldsNode calendarFieldsNode_;
         @Node.Child
         TemporalCalendarDateFromFieldsNode dateFromFieldsNode_;

         WithData() {
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

   @GeneratedBy(TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWithPlainDate.class)
   public static final class JSTemporalZonedDateTimeWithPlainDateNodeGen
      extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWithPlainDate
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
      private JSToStringNode toStringNode_;

      private JSTemporalZonedDateTimeWithPlainDateNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.withPlainDate(arguments0Value_, arguments1Value_, this.toTemporalDate_, this.toStringNode_);
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
            this.toStringNode_ = super.insert(JSToStringNode.create());
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.withPlainDate(arguments0Value, arguments1Value, this.toTemporalDate_, this.toStringNode_);
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
         Object[] s = new Object[]{"withPlainDate", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toTemporalDate_, this.toStringNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWithPlainDate create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeWithPlainDateNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWithPlainTime.class)
   public static final class JSTemporalZonedDateTimeWithPlainTimeNodeGen
      extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWithPlainTime
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ToTemporalTimeNode toTemporalTime_;

      private JSTemporalZonedDateTimeWithPlainTimeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.withPlainTime(arguments0Value_, arguments1Value_, this.toTemporalTime_);
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
            this.toTemporalTime_ = super.insert(ToTemporalTimeNode.create(this.getContext()));
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.withPlainTime(arguments0Value, arguments1Value, this.toTemporalTime_);
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
         Object[] s = new Object[]{"withPlainTime", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toTemporalTime_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWithPlainTime create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeWithPlainTimeNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWithTimeZone.class)
   public static final class JSTemporalZonedDateTimeWithTimeZoneNodeGen
      extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWithTimeZone
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ToTemporalTimeZoneNode toTemporalTimeZone_;

      private JSTemporalZonedDateTimeWithTimeZoneNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.withTimeZone(arguments0Value_, arguments1Value_, this.toTemporalTimeZone_);
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
            this.toTemporalTimeZone_ = super.insert(ToTemporalTimeZoneNode.create(this.getContext()));
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.withTimeZone(arguments0Value, arguments1Value, this.toTemporalTimeZone_);
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
         Object[] s = new Object[]{"withTimeZone", null, null};
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

      public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWithTimeZone create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeWithTimeZoneNodeGen(context, builtin, arguments);
      }
   }
}
