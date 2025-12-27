package com.oracle.truffle.js.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarDateFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarGetterNode;
import com.oracle.truffle.js.nodes.temporal.TemporalMonthDayFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalRoundDurationNode;
import com.oracle.truffle.js.nodes.temporal.TemporalYearMonthFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.ToLimitedTemporalDurationNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalCalendarNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDateNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeZoneNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(TemporalPlainDatePrototypeBuiltins.class)
public final class TemporalPlainDatePrototypeBuiltinsFactory {
   @GeneratedBy(TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateAdd.class)
   public static final class JSTemporalPlainDateAddNodeGen extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateAdd implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private EnumerableOwnPropertyNamesNode namesNode_;
      @Node.Child
      private ToLimitedTemporalDurationNode toLimitedTemporalDurationNode_;

      private JSTemporalPlainDateAddNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.add(arguments0Value_, arguments1Value_, arguments2Value_, this.namesNode_, this.toLimitedTemporalDurationNode_);
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
            this.namesNode_ = super.insert(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
            this.toLimitedTemporalDurationNode_ = super.insert(ToLimitedTemporalDurationNode.create());
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.add(arguments0Value, arguments1Value, arguments2Value, this.namesNode_, this.toLimitedTemporalDurationNode_);
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
            cached.add(Arrays.asList(this.namesNode_, this.toLimitedTemporalDurationNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateAdd create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateAddNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateEquals.class)
   public static final class JSTemporalPlainDateEqualsNodeGen
      extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateEquals
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

      private JSTemporalPlainDateEqualsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.equals(arguments0Value_, arguments1Value_, this.toTemporalDate_, this.toStringNode_);
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
            return this.equals(arguments0Value_, arguments1Value_, this.toTemporalDate_, this.toStringNode_);
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
            this.toTemporalDate_ = super.insert(ToTemporalDateNode.create(this.getContext()));
            this.toStringNode_ = super.insert(JSToStringNode.create());
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.equals(arguments0Value, arguments1Value, this.toTemporalDate_, this.toStringNode_);
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
            cached.add(Arrays.asList(this.toTemporalDate_, this.toStringNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateEquals create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateEqualsNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateGetISOFields.class)
   public static final class JSTemporalPlainDateGetISOFieldsNodeGen
      extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateGetISOFields
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSTemporalPlainDateGetISOFieldsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

      public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateGetISOFields create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateGetISOFieldsNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateGetterNode.class)
   public static final class JSTemporalPlainDateGetterNodeGen
      extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateGetterNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TemporalCalendarGetterNode dateGetter_calendarGetterNode_;

      private JSTemporalPlainDateGetterNodeGen(
         JSContext context, JSBuiltin builtin, TemporalPlainDatePrototypeBuiltins.TemporalPlainDatePrototype property, JavaScriptNode[] arguments
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
            if ((state_0 & 1) != 0 && JSGuards.isJSTemporalDate(arguments0Value_)) {
               return this.dateGetter(arguments0Value_, this.dateGetter_calendarGetterNode_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalDate(arguments0Value_)) {
               return TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateGetterNode.error(arguments0Value_);
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
            if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalDate(arguments0Value_)) {
               return TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateGetterNode.error(arguments0Value_);
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
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         Object var5;
         try {
            int state_0 = this.state_0_;
            if (!JSGuards.isJSTemporalDate(arguments0Value)) {
               if (JSGuards.isJSTemporalDate(arguments0Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
               }

               int var10;
               this.state_0_ = var10 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateGetterNode.error(arguments0Value);
            }

            this.dateGetter_calendarGetterNode_ = super.insert(TemporalCalendarGetterNode.create(this.getContext()));
            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var5 = this.dateGetter(arguments0Value, this.dateGetter_calendarGetterNode_);
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
         Object[] s = new Object[]{"dateGetter", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.dateGetter_calendarGetterNode_));
            s[2] = cached;
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

      public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateGetterNode create(
         JSContext context, JSBuiltin builtin, TemporalPlainDatePrototypeBuiltins.TemporalPlainDatePrototype property, JavaScriptNode[] arguments
      ) {
         return new TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateGetterNodeGen(context, builtin, property, arguments);
      }
   }

   @GeneratedBy(TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateSince.class)
   public static final class JSTemporalPlainDateSinceNodeGen
      extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateSince
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
      private TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateSinceNodeGen.SinceData since_cache;

      private JSTemporalPlainDateSinceNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateSinceNodeGen.SinceData s0_ = this.since_cache;
            if (s0_ != null) {
               return this.since(
                  arguments0Value_,
                  arguments1Value_,
                  arguments2Value_,
                  s0_.toNumber_,
                  s0_.namesNode_,
                  s0_.toTemporalDate_,
                  s0_.toStringNode_,
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
            TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateSinceNodeGen.SinceData s0_ = super.insert(
               new TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateSinceNodeGen.SinceData()
            );
            s0_.toNumber_ = s0_.insertAccessor(JSToNumberNode.create());
            s0_.namesNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
            s0_.toTemporalDate_ = s0_.insertAccessor(ToTemporalDateNode.create(this.getContext()));
            s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
            s0_.equalNode_ = s0_.insertAccessor(TruffleString.EqualNode.create());
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
               s0_.toTemporalDate_,
               s0_.toStringNode_,
               s0_.equalNode_,
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
            TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateSinceNodeGen.SinceData s0_ = this.since_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.toNumber_, s0_.namesNode_, s0_.toTemporalDate_, s0_.toStringNode_, s0_.equalNode_, s0_.roundDurationNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateSince create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateSinceNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateSince.class)
      private static final class SinceData extends Node {
         @Node.Child
         JSToNumberNode toNumber_;
         @Node.Child
         EnumerableOwnPropertyNamesNode namesNode_;
         @Node.Child
         ToTemporalDateNode toTemporalDate_;
         @Node.Child
         JSToStringNode toStringNode_;
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

   @GeneratedBy(TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateSubtract.class)
   public static final class JSTemporalPlainDateSubtractNodeGen
      extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateSubtract
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
      private EnumerableOwnPropertyNamesNode namesNode_;
      @Node.Child
      private ToLimitedTemporalDurationNode toLimitedTemporalDurationNode_;

      private JSTemporalPlainDateSubtractNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.subtract(arguments0Value_, arguments1Value_, arguments2Value_, this.namesNode_, this.toLimitedTemporalDurationNode_);
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
            this.namesNode_ = super.insert(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
            this.toLimitedTemporalDurationNode_ = super.insert(ToLimitedTemporalDurationNode.create());
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.subtract(arguments0Value, arguments1Value, arguments2Value, this.namesNode_, this.toLimitedTemporalDurationNode_);
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
            cached.add(Arrays.asList(this.namesNode_, this.toLimitedTemporalDurationNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateSubtract create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateSubtractNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToLocaleString.class)
   public static final class JSTemporalPlainDateToLocaleStringNodeGen
      extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToLocaleString
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSTemporalPlainDateToLocaleStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

      public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToLocaleString create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateToLocaleStringNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToPlainDateTime.class)
   public static final class JSTemporalPlainDateToPlainDateTimeNodeGen
      extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToPlainDateTime
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ToTemporalTimeNode toTemporalTime_;

      private JSTemporalPlainDateToPlainDateTimeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.toPlainDateTime(arguments0Value_, arguments1Value_, this.toTemporalTime_);
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
            var6 = this.toPlainDateTime(arguments0Value, arguments1Value, this.toTemporalTime_);
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
            cached.add(Arrays.asList(this.toTemporalTime_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToPlainDateTime create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateToPlainDateTimeNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToPlainMonthDay.class)
   public static final class JSTemporalPlainDateToPlainMonthDayNodeGen
      extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToPlainMonthDay
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TemporalMonthDayFromFieldsNode monthDayFromFieldsNode_;
      @Node.Child
      private TemporalCalendarFieldsNode calendarFieldsNode_;

      private JSTemporalPlainDateToPlainMonthDayNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

      private JSDynamicObject executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         JSDynamicObject var5;
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

      public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToPlainMonthDay create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateToPlainMonthDayNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToPlainYearMonth.class)
   public static final class JSTemporalPlainDateToPlainYearMonthNodeGen
      extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToPlainYearMonth
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TemporalYearMonthFromFieldsNode yearMonthFromFieldsNode_;
      @Node.Child
      private TemporalCalendarFieldsNode calendarFieldsNode_;

      private JSTemporalPlainDateToPlainYearMonthNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

      private JSDynamicObject executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         JSDynamicObject var5;
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

      public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToPlainYearMonth create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateToPlainYearMonthNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToString.class)
   public static final class JSTemporalPlainDateToStringNodeGen
      extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToString
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.EqualNode equalNode_;

      private JSTemporalPlainDateToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.toString(arguments0Value_, arguments1Value_, this.equalNode_);
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
            this.equalNode_ = super.insert(TruffleString.EqualNode.create());
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.toString(arguments0Value, arguments1Value, this.equalNode_);
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
            cached.add(Arrays.asList(this.equalNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToString create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateToStringNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToZonedDateTimeNode.class)
   public static final class JSTemporalPlainDateToZonedDateTimeNodeGen
      extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToZonedDateTimeNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateToZonedDateTimeNodeGen.ToZonedDateTimeData toZonedDateTime_cache;

      private JSTemporalPlainDateToZonedDateTimeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateToZonedDateTimeNodeGen.ToZonedDateTimeData s0_ = this.toZonedDateTime_cache;
            if (s0_ != null) {
               return this.toZonedDateTime(
                  arguments0Value_, arguments1Value_, s0_.timeZoneIsUndefined_, s0_.timeIsUndefined_, s0_.toTemporalTime_, s0_.toTemporalTimeZone_
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
            TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateToZonedDateTimeNodeGen.ToZonedDateTimeData s0_ = super.insert(
               new TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateToZonedDateTimeNodeGen.ToZonedDateTimeData()
            );
            s0_.timeZoneIsUndefined_ = ConditionProfile.create();
            s0_.timeIsUndefined_ = ConditionProfile.create();
            s0_.toTemporalTime_ = s0_.insertAccessor(ToTemporalTimeNode.create(this.getContext()));
            s0_.toTemporalTimeZone_ = s0_.insertAccessor(ToTemporalTimeZoneNode.create(this.getContext()));
            VarHandle.storeStoreFence();
            this.toZonedDateTime_cache = s0_;
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.toZonedDateTime(
               arguments0Value, arguments1Value, s0_.timeZoneIsUndefined_, s0_.timeIsUndefined_, s0_.toTemporalTime_, s0_.toTemporalTimeZone_
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
         Object[] s = new Object[]{"toZonedDateTime", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateToZonedDateTimeNodeGen.ToZonedDateTimeData s0_ = this.toZonedDateTime_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.timeZoneIsUndefined_, s0_.timeIsUndefined_, s0_.toTemporalTime_, s0_.toTemporalTimeZone_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToZonedDateTimeNode create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateToZonedDateTimeNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToZonedDateTimeNode.class)
      private static final class ToZonedDateTimeData extends Node {
         @CompilerDirectives.CompilationFinal
         ConditionProfile timeZoneIsUndefined_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile timeIsUndefined_;
         @Node.Child
         ToTemporalTimeNode toTemporalTime_;
         @Node.Child
         ToTemporalTimeZoneNode toTemporalTimeZone_;

         ToZonedDateTimeData() {
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

   @GeneratedBy(TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateUntil.class)
   public static final class JSTemporalPlainDateUntilNodeGen
      extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateUntil
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
      private TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateUntilNodeGen.UntilData until_cache;

      private JSTemporalPlainDateUntilNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateUntilNodeGen.UntilData s0_ = this.until_cache;
            if (s0_ != null) {
               return this.until(
                  arguments0Value_,
                  arguments1Value_,
                  arguments2Value_,
                  s0_.toNumber_,
                  s0_.namesNode_,
                  s0_.toTemporalDate_,
                  s0_.toStringNode_,
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
            TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateUntilNodeGen.UntilData s0_ = super.insert(
               new TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateUntilNodeGen.UntilData()
            );
            s0_.toNumber_ = s0_.insertAccessor(JSToNumberNode.create());
            s0_.namesNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
            s0_.toTemporalDate_ = s0_.insertAccessor(ToTemporalDateNode.create(this.getContext()));
            s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
            s0_.equalNode_ = s0_.insertAccessor(TruffleString.EqualNode.create());
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
               s0_.toTemporalDate_,
               s0_.toStringNode_,
               s0_.equalNode_,
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
            TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateUntilNodeGen.UntilData s0_ = this.until_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.toNumber_, s0_.namesNode_, s0_.toTemporalDate_, s0_.toStringNode_, s0_.equalNode_, s0_.roundDurationNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateUntil create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateUntilNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateUntil.class)
      private static final class UntilData extends Node {
         @Node.Child
         JSToNumberNode toNumber_;
         @Node.Child
         EnumerableOwnPropertyNamesNode namesNode_;
         @Node.Child
         ToTemporalDateNode toTemporalDate_;
         @Node.Child
         JSToStringNode toStringNode_;
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

   @GeneratedBy(TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateValueOf.class)
   public static final class JSTemporalPlainDateValueOfNodeGen
      extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateValueOf
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSTemporalPlainDateValueOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

      public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateValueOf create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateValueOfNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateWithCalendar.class)
   public static final class JSTemporalPlainDateWithCalendarNodeGen
      extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateWithCalendar
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ToTemporalCalendarNode toTemporalCalendar_;

      private JSTemporalPlainDateWithCalendarNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

      public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateWithCalendar create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateWithCalendarNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateWith.class)
   public static final class JSTemporalPlainDateWithNodeGen
      extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateWith
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
      private TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateWithNodeGen.WithData with_cache;

      private JSTemporalPlainDateWithNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateWithNodeGen.WithData s0_ = this.with_cache;
            if (s0_ != null) {
               return this.with(arguments0Value_, arguments1Value_, arguments2Value_, s0_.nameNode_, s0_.calendarFieldsNode_, s0_.dateFromFieldsNode_);
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
            TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateWithNodeGen.WithData s0_ = super.insert(
               new TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateWithNodeGen.WithData()
            );
            s0_.nameNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
            s0_.calendarFieldsNode_ = s0_.insertAccessor(TemporalCalendarFieldsNode.create(this.getContext()));
            s0_.dateFromFieldsNode_ = s0_.insertAccessor(TemporalCalendarDateFromFieldsNode.create(this.getContext()));
            VarHandle.storeStoreFence();
            this.with_cache = s0_;
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.with(arguments0Value, arguments1Value, arguments2Value, s0_.nameNode_, s0_.calendarFieldsNode_, s0_.dateFromFieldsNode_);
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
            TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateWithNodeGen.WithData s0_ = this.with_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.nameNode_, s0_.calendarFieldsNode_, s0_.dateFromFieldsNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateWith create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateWithNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateWith.class)
      private static final class WithData extends Node {
         @Node.Child
         EnumerableOwnPropertyNamesNode nameNode_;
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
}
