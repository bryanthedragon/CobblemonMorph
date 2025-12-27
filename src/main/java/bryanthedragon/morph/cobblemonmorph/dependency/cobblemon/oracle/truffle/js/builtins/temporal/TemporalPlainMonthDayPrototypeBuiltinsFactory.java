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
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarDateFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarGetterNode;
import com.oracle.truffle.js.nodes.temporal.TemporalMonthDayFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalMonthDayNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(TemporalPlainMonthDayPrototypeBuiltins.class)
public final class TemporalPlainMonthDayPrototypeBuiltinsFactory {
   @GeneratedBy(TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayEqualsNode.class)
   public static final class JSTemporalPlainMonthDayEqualsNodeGen
      extends TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayEqualsNode
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
      private ToTemporalMonthDayNode toTemporalMonthDayNode_;

      private JSTemporalPlainMonthDayEqualsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.equals(arguments0Value_, arguments1Value_, this.toStringNode_, this.toTemporalMonthDayNode_);
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
            return this.equals(arguments0Value_, arguments1Value_, this.toStringNode_, this.toTemporalMonthDayNode_);
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
            this.toStringNode_ = super.insert(JSToStringNode.create());
            this.toTemporalMonthDayNode_ = super.insert(ToTemporalMonthDayNode.create(this.getContext()));
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.equals(arguments0Value, arguments1Value, this.toStringNode_, this.toTemporalMonthDayNode_);
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
            cached.add(Arrays.asList(this.toStringNode_, this.toTemporalMonthDayNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayEqualsNode create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalPlainMonthDayPrototypeBuiltinsFactory.JSTemporalPlainMonthDayEqualsNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayGetISOFields.class)
   public static final class JSTemporalPlainMonthDayGetISOFieldsNodeGen
      extends TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayGetISOFields
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSTemporalPlainMonthDayGetISOFieldsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

      public static TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayGetISOFields create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalPlainMonthDayPrototypeBuiltinsFactory.JSTemporalPlainMonthDayGetISOFieldsNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayGetterNode.class)
   public static final class JSTemporalPlainMonthDayGetterNodeGen
      extends TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayGetterNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TemporalCalendarGetterNode monthDayGetter_calendarGetterNode_;

      private JSTemporalPlainMonthDayGetterNodeGen(
         JSContext context, JSBuiltin builtin, TemporalPlainMonthDayPrototypeBuiltins.TemporalPlainMonthDayPrototype property, JavaScriptNode[] arguments
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
            if ((state_0 & 1) != 0 && JSGuards.isJSTemporalMonthDay(arguments0Value_)) {
               return this.monthDayGetter(arguments0Value_, this.monthDayGetter_calendarGetterNode_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalMonthDay(arguments0Value_)) {
               return TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayGetterNode.error(arguments0Value_);
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
            if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalMonthDay(arguments0Value_)) {
               return TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayGetterNode.error(arguments0Value_);
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
            if (!JSGuards.isJSTemporalMonthDay(arguments0Value)) {
               if (JSGuards.isJSTemporalMonthDay(arguments0Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
               }

               int var10;
               this.state_0_ = var10 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayGetterNode.error(arguments0Value);
            }

            this.monthDayGetter_calendarGetterNode_ = super.insert(TemporalCalendarGetterNode.create(this.getContext()));
            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var5 = this.monthDayGetter(arguments0Value, this.monthDayGetter_calendarGetterNode_);
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
         Object[] s = new Object[]{"monthDayGetter", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.monthDayGetter_calendarGetterNode_));
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

      public static TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayGetterNode create(
         JSContext context, JSBuiltin builtin, TemporalPlainMonthDayPrototypeBuiltins.TemporalPlainMonthDayPrototype property, JavaScriptNode[] arguments
      ) {
         return new TemporalPlainMonthDayPrototypeBuiltinsFactory.JSTemporalPlainMonthDayGetterNodeGen(context, builtin, property, arguments);
      }
   }

   @GeneratedBy(TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayToLocaleString.class)
   public static final class JSTemporalPlainMonthDayToLocaleStringNodeGen
      extends TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayToLocaleString
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSTemporalPlainMonthDayToLocaleStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

      public static TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayToLocaleString create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalPlainMonthDayPrototypeBuiltinsFactory.JSTemporalPlainMonthDayToLocaleStringNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayToPlainDateNode.class)
   public static final class JSTemporalPlainMonthDayToPlainDateNodeGen
      extends TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayToPlainDateNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TemporalPlainMonthDayPrototypeBuiltinsFactory.JSTemporalPlainMonthDayToPlainDateNodeGen.ToPlainDateData toPlainDate_cache;

      private JSTemporalPlainMonthDayToPlainDateNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            TemporalPlainMonthDayPrototypeBuiltinsFactory.JSTemporalPlainMonthDayToPlainDateNodeGen.ToPlainDateData s0_ = this.toPlainDate_cache;
            if (s0_ != null) {
               return this.toPlainDate(arguments0Value_, arguments1Value_, s0_.namesNode_, s0_.calendarFieldsNode_, s0_.dateFromFieldsNode_);
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

         Object var7;
         try {
            int state_0 = this.state_0_;
            TemporalPlainMonthDayPrototypeBuiltinsFactory.JSTemporalPlainMonthDayToPlainDateNodeGen.ToPlainDateData s0_ = super.insert(
               new TemporalPlainMonthDayPrototypeBuiltinsFactory.JSTemporalPlainMonthDayToPlainDateNodeGen.ToPlainDateData()
            );
            s0_.namesNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
            s0_.calendarFieldsNode_ = s0_.insertAccessor(TemporalCalendarFieldsNode.create(this.getContext()));
            s0_.dateFromFieldsNode_ = s0_.insertAccessor(TemporalCalendarDateFromFieldsNode.create(this.getContext()));
            VarHandle.storeStoreFence();
            this.toPlainDate_cache = s0_;
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.toPlainDate(arguments0Value, arguments1Value, s0_.namesNode_, s0_.calendarFieldsNode_, s0_.dateFromFieldsNode_);
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
         Object[] s = new Object[]{"toPlainDate", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            TemporalPlainMonthDayPrototypeBuiltinsFactory.JSTemporalPlainMonthDayToPlainDateNodeGen.ToPlainDateData s0_ = this.toPlainDate_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.namesNode_, s0_.calendarFieldsNode_, s0_.dateFromFieldsNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayToPlainDateNode create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalPlainMonthDayPrototypeBuiltinsFactory.JSTemporalPlainMonthDayToPlainDateNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayToPlainDateNode.class)
      private static final class ToPlainDateData extends Node {
         @Node.Child
         EnumerableOwnPropertyNamesNode namesNode_;
         @Node.Child
         TemporalCalendarFieldsNode calendarFieldsNode_;
         @Node.Child
         TemporalCalendarDateFromFieldsNode dateFromFieldsNode_;

         ToPlainDateData() {
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

   @GeneratedBy(TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayToString.class)
   public static final class JSTemporalPlainMonthDayToStringNodeGen
      extends TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayToString
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.EqualNode equalNode_;

      private JSTemporalPlainMonthDayToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

      public static TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayToString create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalPlainMonthDayPrototypeBuiltinsFactory.JSTemporalPlainMonthDayToStringNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayValueOf.class)
   public static final class JSTemporalPlainMonthDayValueOfNodeGen
      extends TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayValueOf
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSTemporalPlainMonthDayValueOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

      public static TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayValueOf create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalPlainMonthDayPrototypeBuiltinsFactory.JSTemporalPlainMonthDayValueOfNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayWithNode.class)
   public static final class JSTemporalPlainMonthDayWithNodeGen
      extends TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayWithNode
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
      private TemporalPlainMonthDayPrototypeBuiltinsFactory.JSTemporalPlainMonthDayWithNodeGen.WithData with_cache;

      private JSTemporalPlainMonthDayWithNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            TemporalPlainMonthDayPrototypeBuiltinsFactory.JSTemporalPlainMonthDayWithNodeGen.WithData s0_ = this.with_cache;
            if (s0_ != null) {
               return this.with(arguments0Value_, arguments1Value_, arguments2Value_, s0_.namesNode_, s0_.monthDayFromFieldsNode_, s0_.calendarFieldsNode_);
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
            TemporalPlainMonthDayPrototypeBuiltinsFactory.JSTemporalPlainMonthDayWithNodeGen.WithData s0_ = super.insert(
               new TemporalPlainMonthDayPrototypeBuiltinsFactory.JSTemporalPlainMonthDayWithNodeGen.WithData()
            );
            s0_.namesNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
            s0_.monthDayFromFieldsNode_ = s0_.insertAccessor(TemporalMonthDayFromFieldsNode.create(this.getContext()));
            s0_.calendarFieldsNode_ = s0_.insertAccessor(TemporalCalendarFieldsNode.create(this.getContext()));
            VarHandle.storeStoreFence();
            this.with_cache = s0_;
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.with(arguments0Value, arguments1Value, arguments2Value, s0_.namesNode_, s0_.monthDayFromFieldsNode_, s0_.calendarFieldsNode_);
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
            TemporalPlainMonthDayPrototypeBuiltinsFactory.JSTemporalPlainMonthDayWithNodeGen.WithData s0_ = this.with_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.namesNode_, s0_.monthDayFromFieldsNode_, s0_.calendarFieldsNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayWithNode create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalPlainMonthDayPrototypeBuiltinsFactory.JSTemporalPlainMonthDayWithNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(TemporalPlainMonthDayPrototypeBuiltins.JSTemporalPlainMonthDayWithNode.class)
      private static final class WithData extends Node {
         @Node.Child
         EnumerableOwnPropertyNamesNode namesNode_;
         @Node.Child
         TemporalMonthDayFromFieldsNode monthDayFromFieldsNode_;
         @Node.Child
         TemporalCalendarFieldsNode calendarFieldsNode_;

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
