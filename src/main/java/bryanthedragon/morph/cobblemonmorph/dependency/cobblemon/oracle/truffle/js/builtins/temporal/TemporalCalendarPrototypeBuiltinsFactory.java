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
import com.oracle.truffle.js.nodes.binary.JSIdenticalNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerOrInfinityNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.temporal.TemporalGetOptionNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDateNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDurationNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(TemporalCalendarPrototypeBuiltins.class)
public final class TemporalCalendarPrototypeBuiltinsFactory {
   @GeneratedBy(TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDateAdd.class)
   public static final class JSTemporalCalendarDateAddNodeGen
      extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDateAdd
      implements Introspection.Provider {
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
      @Node.Child
      private ToTemporalDurationNode toTemporalDurationNode_;
      @Node.Child
      private EnumerableOwnPropertyNamesNode namesNode_;

      private JSTemporalCalendarDateAddNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         Object arguments3Value_ = this.arguments3_.execute(frameValue);
         if (state_0 != 0) {
            return this.dateAdd(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_, this.toTemporalDurationNode_, this.namesNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value, Object arguments3Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         Object var8;
         try {
            int state_0 = this.state_0_;
            this.toTemporalDurationNode_ = super.insert(ToTemporalDurationNode.create(this.getContext()));
            this.namesNode_ = super.insert(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.dateAdd(arguments0Value, arguments1Value, arguments2Value, arguments3Value, this.toTemporalDurationNode_, this.namesNode_);
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
         Object[] s = new Object[]{"dateAdd", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toTemporalDurationNode_, this.namesNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDateAdd create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarDateAddNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDateFromFields.class)
   public static final class JSTemporalCalendarDateFromFieldsNodeGen
      extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDateFromFields
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
      private TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarDateFromFieldsNodeGen.DateFromFieldsData dateFromFields_cache;

      private JSTemporalCalendarDateFromFieldsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarDateFromFieldsNodeGen.DateFromFieldsData s0_ = this.dateFromFields_cache;
            if (s0_ != null) {
               return this.dateFromFields(
                  arguments0Value_, arguments1Value_, arguments2Value_, s0_.identicalNode_, s0_.getOptionNode_, s0_.toIntOrInfinityNode_
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
            TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarDateFromFieldsNodeGen.DateFromFieldsData s0_ = super.insert(
               new TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarDateFromFieldsNodeGen.DateFromFieldsData()
            );
            s0_.identicalNode_ = s0_.insertAccessor(JSIdenticalNode.createSameValue());
            s0_.getOptionNode_ = s0_.insertAccessor(TemporalGetOptionNode.create());
            s0_.toIntOrInfinityNode_ = s0_.insertAccessor(JSToIntegerOrInfinityNode.create());
            VarHandle.storeStoreFence();
            this.dateFromFields_cache = s0_;
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.dateFromFields(arguments0Value, arguments1Value, arguments2Value, s0_.identicalNode_, s0_.getOptionNode_, s0_.toIntOrInfinityNode_);
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
         Object[] s = new Object[]{"dateFromFields", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarDateFromFieldsNodeGen.DateFromFieldsData s0_ = this.dateFromFields_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.identicalNode_, s0_.getOptionNode_, s0_.toIntOrInfinityNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDateFromFields create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarDateFromFieldsNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDateFromFields.class)
      private static final class DateFromFieldsData extends Node {
         @Node.Child
         JSIdenticalNode identicalNode_;
         @Node.Child
         TemporalGetOptionNode getOptionNode_;
         @Node.Child
         JSToIntegerOrInfinityNode toIntOrInfinityNode_;

         DateFromFieldsData() {
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

   @GeneratedBy(TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDateUntil.class)
   public static final class JSTemporalCalendarDateUntilNodeGen
      extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDateUntil
      implements Introspection.Provider {
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
      @Node.Child
      private TruffleString.EqualNode equalNode_;

      private JSTemporalCalendarDateUntilNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         Object arguments3Value_ = this.arguments3_.execute(frameValue);
         if (state_0 != 0) {
            return this.dateUntil(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_, this.equalNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value, Object arguments3Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         Object var8;
         try {
            int state_0 = this.state_0_;
            this.equalNode_ = super.insert(TruffleString.EqualNode.create());
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.dateUntil(arguments0Value, arguments1Value, arguments2Value, arguments3Value, this.equalNode_);
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
         Object[] s = new Object[]{"dateUntil", null, null};
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

      public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDateUntil create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarDateUntilNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDay.class)
   public static final class JSTemporalCalendarDayNodeGen extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDay implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ToTemporalDateNode toTemporalDate_;

      private JSTemporalCalendarDayNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.day(arguments0Value_, arguments1Value_, this.toTemporalDate_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public long executeLong(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            return this.day(arguments0Value_, arguments1Value_, this.toTemporalDate_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeLong(frameValue);
      }

      private long executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         long var6;
         try {
            int state_0 = this.state_0_;
            this.toTemporalDate_ = super.insert(ToTemporalDateNode.create(this.getContext()));
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.day(arguments0Value, arguments1Value, this.toTemporalDate_);
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
         Object[] s = new Object[]{"day", null, null};
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

      public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDay create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarDayNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDayOfWeek.class)
   public static final class JSTemporalCalendarDayOfWeekNodeGen
      extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDayOfWeek
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private JSTemporalCalendarDayOfWeekNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.dayOfWeek(arguments0Value_, arguments1Value_);
      }

      @Override
      public long executeLong(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.dayOfWeek(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeLong(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"dayOfWeek", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDayOfWeek create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarDayOfWeekNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDayOfYear.class)
   public static final class JSTemporalCalendarDayOfYearNodeGen
      extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDayOfYear
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private JSTemporalCalendarDayOfYearNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.dayOfYear(arguments0Value_, arguments1Value_);
      }

      @Override
      public long executeLong(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.dayOfYear(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeLong(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"dayOfYear", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDayOfYear create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarDayOfYearNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDaysInMonth.class)
   public static final class JSTemporalCalendarDaysInMonthNodeGen
      extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDaysInMonth
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private JSTemporalCalendarDaysInMonthNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.daysInMonth(arguments0Value_, arguments1Value_);
      }

      @Override
      public long executeLong(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.daysInMonth(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeLong(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"daysInMonth", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDaysInMonth create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarDaysInMonthNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDaysInWeek.class)
   public static final class JSTemporalCalendarDaysInWeekNodeGen
      extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDaysInWeek
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private JSTemporalCalendarDaysInWeekNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.daysInWeek(arguments0Value_, arguments1Value_);
      }

      @Override
      public long executeLong(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.daysInWeek(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeLong(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"daysInWeek", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDaysInWeek create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarDaysInWeekNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDaysInYear.class)
   public static final class JSTemporalCalendarDaysInYearNodeGen
      extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDaysInYear
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private JSTemporalCalendarDaysInYearNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.daysInYear(arguments0Value_, arguments1Value_);
      }

      @Override
      public int executeInt(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.daysInYear(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeInt(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"daysInYear", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDaysInYear create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarDaysInYearNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalCalendarPrototypeBuiltins.JSTemporalCalendarFields.class)
   public static final class JSTemporalCalendarFieldsNodeGen
      extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarFields
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private JSTemporalCalendarFieldsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.fields(arguments0Value_, arguments1Value_);
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
         Object[] s = new Object[]{"fields", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarFields create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarFieldsNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalCalendarPrototypeBuiltins.JSTemporalCalendarGetterNode.class)
   public static final class JSTemporalCalendarGetterNodeGen
      extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarGetterNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToStringNode durationGetter_toStringNode_;

      private JSTemporalCalendarGetterNodeGen(
         JSContext context, JSBuiltin builtin, TemporalCalendarPrototypeBuiltins.TemporalCalendarPrototype property, JavaScriptNode[] arguments
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
            if ((state_0 & 1) != 0 && JSGuards.isJSTemporalCalendar(arguments0Value_)) {
               return this.durationGetter(arguments0Value_, this.durationGetter_toStringNode_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalCalendar(arguments0Value_)) {
               return TemporalCalendarPrototypeBuiltins.JSTemporalCalendarGetterNode.error(arguments0Value_);
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
            if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalCalendar(arguments0Value_)) {
               return TemporalCalendarPrototypeBuiltins.JSTemporalCalendarGetterNode.error(arguments0Value_);
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
            if (!JSGuards.isJSTemporalCalendar(arguments0Value)) {
               if (JSGuards.isJSTemporalCalendar(arguments0Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
               }

               int var10;
               this.state_0_ = var10 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return TemporalCalendarPrototypeBuiltins.JSTemporalCalendarGetterNode.error(arguments0Value);
            }

            this.durationGetter_toStringNode_ = super.insert(JSToStringNode.create());
            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var5 = this.durationGetter(arguments0Value, this.durationGetter_toStringNode_);
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
         Object[] s = new Object[]{"durationGetter", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.durationGetter_toStringNode_));
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

      public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarGetterNode create(
         JSContext context, JSBuiltin builtin, TemporalCalendarPrototypeBuiltins.TemporalCalendarPrototype property, JavaScriptNode[] arguments
      ) {
         return new TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarGetterNodeGen(context, builtin, property, arguments);
      }
   }

   @GeneratedBy(TemporalCalendarPrototypeBuiltins.JSTemporalCalendarInLeapYear.class)
   public static final class JSTemporalCalendarInLeapYearNodeGen
      extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarInLeapYear
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private JSTemporalCalendarInLeapYearNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.inLeapYear(arguments0Value_, arguments1Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.inLeapYear(arguments0Value_, arguments1Value_);
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
         Object[] s = new Object[]{"inLeapYear", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarInLeapYear create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarInLeapYearNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMergeFields.class)
   public static final class JSTemporalCalendarMergeFieldsNodeGen
      extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMergeFields
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
      private JSToObjectNode toObject_;
      @Node.Child
      private EnumerableOwnPropertyNamesNode namesNode_;

      private JSTemporalCalendarMergeFieldsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.mergeFields(arguments0Value_, arguments1Value_, arguments2Value_, this.toObject_, this.namesNode_);
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
            this.toObject_ = super.insert(JSToObjectNode.createToObject(this.getContext()));
            this.namesNode_ = super.insert(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.mergeFields(arguments0Value, arguments1Value, arguments2Value, this.toObject_, this.namesNode_);
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
         Object[] s = new Object[]{"mergeFields", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toObject_, this.namesNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMergeFields create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarMergeFieldsNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMonthCode.class)
   public static final class JSTemporalCalendarMonthCodeNodeGen
      extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMonthCode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private JSTemporalCalendarMonthCodeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.monthCode(arguments0Value_, arguments1Value_);
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
         Object[] s = new Object[]{"monthCode", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMonthCode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarMonthCodeNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMonthDayFromFields.class)
   public static final class JSTemporalCalendarMonthDayFromFieldsNodeGen
      extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMonthDayFromFields
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
      private TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarMonthDayFromFieldsNodeGen.MonthDayFromFieldsData monthDayFromFields_cache;

      private JSTemporalCalendarMonthDayFromFieldsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarMonthDayFromFieldsNodeGen.MonthDayFromFieldsData s0_ = this.monthDayFromFields_cache;
            if (s0_ != null) {
               return this.monthDayFromFields(
                  arguments0Value_, arguments1Value_, arguments2Value_, s0_.identicalNode_, s0_.getOptionNode_, s0_.toIntOrInfinityNode_
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
            TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarMonthDayFromFieldsNodeGen.MonthDayFromFieldsData s0_ = super.insert(
               new TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarMonthDayFromFieldsNodeGen.MonthDayFromFieldsData()
            );
            s0_.identicalNode_ = s0_.insertAccessor(JSIdenticalNode.createSameValue());
            s0_.getOptionNode_ = s0_.insertAccessor(TemporalGetOptionNode.create());
            s0_.toIntOrInfinityNode_ = s0_.insertAccessor(JSToIntegerOrInfinityNode.create());
            VarHandle.storeStoreFence();
            this.monthDayFromFields_cache = s0_;
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.monthDayFromFields(arguments0Value, arguments1Value, arguments2Value, s0_.identicalNode_, s0_.getOptionNode_, s0_.toIntOrInfinityNode_);
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
         Object[] s = new Object[]{"monthDayFromFields", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarMonthDayFromFieldsNodeGen.MonthDayFromFieldsData s0_ = this.monthDayFromFields_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.identicalNode_, s0_.getOptionNode_, s0_.toIntOrInfinityNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMonthDayFromFields create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarMonthDayFromFieldsNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMonthDayFromFields.class)
      private static final class MonthDayFromFieldsData extends Node {
         @Node.Child
         JSIdenticalNode identicalNode_;
         @Node.Child
         TemporalGetOptionNode getOptionNode_;
         @Node.Child
         JSToIntegerOrInfinityNode toIntOrInfinityNode_;

         MonthDayFromFieldsData() {
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

   @GeneratedBy(TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMonth.class)
   public static final class JSTemporalCalendarMonthNodeGen extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMonth implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private JSTemporalCalendarMonthNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.month(arguments0Value_, arguments1Value_);
      }

      @Override
      public long executeLong(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.month(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeLong(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"month", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMonth create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarMonthNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMonthsInYear.class)
   public static final class JSTemporalCalendarMonthsInYearNodeGen
      extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMonthsInYear
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private JSTemporalCalendarMonthsInYearNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.monthsInYear(arguments0Value_, arguments1Value_);
      }

      @Override
      public long executeLong(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.monthsInYear(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeLong(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"monthsInYear", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMonthsInYear create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarMonthsInYearNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalCalendarPrototypeBuiltins.JSTemporalCalendarToString.class)
   public static final class JSTemporalCalendarToStringNodeGen
      extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarToString
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSTemporalCalendarToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.toString(arguments0Value_);
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
         Object[] s = new Object[]{"toString", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarToString create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarToStringNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalCalendarPrototypeBuiltins.JSTemporalCalendarWeekOfYear.class)
   public static final class JSTemporalCalendarWeekOfYearNodeGen
      extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarWeekOfYear
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private JSTemporalCalendarWeekOfYearNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.weekOfYear(arguments0Value_, arguments1Value_);
      }

      @Override
      public long executeLong(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.weekOfYear(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeLong(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"weekOfYear", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarWeekOfYear create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarWeekOfYearNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalCalendarPrototypeBuiltins.JSTemporalCalendarYearMonthFromFields.class)
   public static final class JSTemporalCalendarYearMonthFromFieldsNodeGen
      extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarYearMonthFromFields
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
      private TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarYearMonthFromFieldsNodeGen.YearMonthFromFieldsData yearMonthFromFields_cache;

      private JSTemporalCalendarYearMonthFromFieldsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarYearMonthFromFieldsNodeGen.YearMonthFromFieldsData s0_ = this.yearMonthFromFields_cache;
            if (s0_ != null) {
               return this.yearMonthFromFields(
                  arguments0Value_, arguments1Value_, arguments2Value_, s0_.identicalNode_, s0_.getOptionNode_, s0_.toIntOrInfinityNode_
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
            TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarYearMonthFromFieldsNodeGen.YearMonthFromFieldsData s0_ = super.insert(
               new TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarYearMonthFromFieldsNodeGen.YearMonthFromFieldsData()
            );
            s0_.identicalNode_ = s0_.insertAccessor(JSIdenticalNode.createSameValue());
            s0_.getOptionNode_ = s0_.insertAccessor(TemporalGetOptionNode.create());
            s0_.toIntOrInfinityNode_ = s0_.insertAccessor(JSToIntegerOrInfinityNode.create());
            VarHandle.storeStoreFence();
            this.yearMonthFromFields_cache = s0_;
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.yearMonthFromFields(arguments0Value, arguments1Value, arguments2Value, s0_.identicalNode_, s0_.getOptionNode_, s0_.toIntOrInfinityNode_);
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
         Object[] s = new Object[]{"yearMonthFromFields", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarYearMonthFromFieldsNodeGen.YearMonthFromFieldsData s0_ = this.yearMonthFromFields_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.identicalNode_, s0_.getOptionNode_, s0_.toIntOrInfinityNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarYearMonthFromFields create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarYearMonthFromFieldsNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(TemporalCalendarPrototypeBuiltins.JSTemporalCalendarYearMonthFromFields.class)
      private static final class YearMonthFromFieldsData extends Node {
         @Node.Child
         JSIdenticalNode identicalNode_;
         @Node.Child
         TemporalGetOptionNode getOptionNode_;
         @Node.Child
         JSToIntegerOrInfinityNode toIntOrInfinityNode_;

         YearMonthFromFieldsData() {
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

   @GeneratedBy(TemporalCalendarPrototypeBuiltins.JSTemporalCalendarYear.class)
   public static final class JSTemporalCalendarYearNodeGen extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarYear implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private JSTemporalCalendarYearNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.year(arguments0Value_, arguments1Value_);
      }

      @Override
      public long executeLong(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.year(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeLong(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"year", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarYear create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarYearNodeGen(context, builtin, arguments);
      }
   }
}
