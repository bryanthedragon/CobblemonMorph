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
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.temporal.ToTemporalCalendarWithISODefaultNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDateTimeNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalInstantNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(TemporalTimeZonePrototypeBuiltins.class)
public final class TemporalTimeZonePrototypeBuiltinsFactory {
   @GeneratedBy(TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneGetInstantFor.class)
   public static final class JSTemporalTimeZoneGetInstantForNodeGen
      extends TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneGetInstantFor
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
      private ToTemporalDateTimeNode toTemporalDateTime_;
      @Node.Child
      private TruffleString.EqualNode equalNode_;

      private JSTemporalTimeZoneGetInstantForNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.getInstantFor(arguments0Value_, arguments1Value_, arguments2Value_, this.toTemporalDateTime_, this.equalNode_);
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
            this.toTemporalDateTime_ = super.insert(ToTemporalDateTimeNode.create(this.getContext()));
            this.equalNode_ = super.insert(TruffleString.EqualNode.create());
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.getInstantFor(arguments0Value, arguments1Value, arguments2Value, this.toTemporalDateTime_, this.equalNode_);
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
         Object[] s = new Object[]{"getInstantFor", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toTemporalDateTime_, this.equalNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneGetInstantFor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalTimeZonePrototypeBuiltinsFactory.JSTemporalTimeZoneGetInstantForNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneGetNextOrPreviousTransition.class)
   public static final class JSTemporalTimeZoneGetNextOrPreviousTransitionNodeGen
      extends TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneGetNextOrPreviousTransition
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ToTemporalInstantNode toTemporalInstantNode_;

      private JSTemporalTimeZoneGetNextOrPreviousTransitionNodeGen(JSContext context, JSBuiltin builtin, boolean isNext, JavaScriptNode[] arguments) {
         super(context, builtin, isNext);
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
            return this.getTransition(arguments0Value_, arguments1Value_, this.toTemporalInstantNode_);
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
            this.toTemporalInstantNode_ = super.insert(ToTemporalInstantNode.create(this.getContext()));
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.getTransition(arguments0Value, arguments1Value, this.toTemporalInstantNode_);
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
         Object[] s = new Object[]{"getTransition", null, null};
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

      public static TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneGetNextOrPreviousTransition create(
         JSContext context, JSBuiltin builtin, boolean isNext, JavaScriptNode[] arguments
      ) {
         return new TemporalTimeZonePrototypeBuiltinsFactory.JSTemporalTimeZoneGetNextOrPreviousTransitionNodeGen(context, builtin, isNext, arguments);
      }
   }

   @GeneratedBy(TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneGetOffsetNanosecondsFor.class)
   public static final class JSTemporalTimeZoneGetOffsetNanosecondsForNodeGen
      extends TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneGetOffsetNanosecondsFor
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ToTemporalInstantNode toTemporalInstantNode_;

      private JSTemporalTimeZoneGetOffsetNanosecondsForNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.getOffsetNanosecondsFor(arguments0Value_, arguments1Value_, this.toTemporalInstantNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public double executeDouble(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            return this.getOffsetNanosecondsFor(arguments0Value_, arguments1Value_, this.toTemporalInstantNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeDouble(frameValue);
      }

      private double executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         double var6;
         try {
            int state_0 = this.state_0_;
            this.toTemporalInstantNode_ = super.insert(ToTemporalInstantNode.create(this.getContext()));
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.getOffsetNanosecondsFor(arguments0Value, arguments1Value, this.toTemporalInstantNode_);
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
         Object[] s = new Object[]{"getOffsetNanosecondsFor", null, null};
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

      public static TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneGetOffsetNanosecondsFor create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalTimeZonePrototypeBuiltinsFactory.JSTemporalTimeZoneGetOffsetNanosecondsForNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneGetOffsetStringFor.class)
   public static final class JSTemporalTimeZoneGetOffsetStringForNodeGen
      extends TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneGetOffsetStringFor
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ToTemporalInstantNode toTemporalInstantNode_;

      private JSTemporalTimeZoneGetOffsetStringForNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.getOffsetStringFor(arguments0Value_, arguments1Value_, this.toTemporalInstantNode_);
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
            this.toTemporalInstantNode_ = super.insert(ToTemporalInstantNode.create(this.getContext()));
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.getOffsetStringFor(arguments0Value, arguments1Value, this.toTemporalInstantNode_);
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
         Object[] s = new Object[]{"getOffsetStringFor", null, null};
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

      public static TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneGetOffsetStringFor create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalTimeZonePrototypeBuiltinsFactory.JSTemporalTimeZoneGetOffsetStringForNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneGetPlainDateTimeFor.class)
   public static final class JSTemporalTimeZoneGetPlainDateTimeForNodeGen
      extends TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneGetPlainDateTimeFor
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
      private ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode_;
      @Node.Child
      private ToTemporalInstantNode toTemporalInstantNode_;

      private JSTemporalTimeZoneGetPlainDateTimeForNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.getPlainDateTimeFor(
               arguments0Value_, arguments1Value_, arguments2Value_, this.toTemporalCalendarWithISODefaultNode_, this.toTemporalInstantNode_
            );
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
            this.toTemporalCalendarWithISODefaultNode_ = super.insert(ToTemporalCalendarWithISODefaultNode.create(this.getContext()));
            this.toTemporalInstantNode_ = super.insert(ToTemporalInstantNode.create(this.getContext()));
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.getPlainDateTimeFor(
               arguments0Value, arguments1Value, arguments2Value, this.toTemporalCalendarWithISODefaultNode_, this.toTemporalInstantNode_
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
         Object[] s = new Object[]{"getPlainDateTimeFor", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toTemporalCalendarWithISODefaultNode_, this.toTemporalInstantNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneGetPlainDateTimeFor create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalTimeZonePrototypeBuiltinsFactory.JSTemporalTimeZoneGetPlainDateTimeForNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneGetPossibleInstantsFor.class)
   public static final class JSTemporalTimeZoneGetPossibleInstantsForNodeGen
      extends TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneGetPossibleInstantsFor
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ToTemporalDateTimeNode toTemporalDateTime_;

      private JSTemporalTimeZoneGetPossibleInstantsForNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.getPossibleInstantsFor(arguments0Value_, arguments1Value_, this.toTemporalDateTime_);
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
            this.toTemporalDateTime_ = super.insert(ToTemporalDateTimeNode.create(this.getContext()));
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.getPossibleInstantsFor(arguments0Value, arguments1Value, this.toTemporalDateTime_);
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
         Object[] s = new Object[]{"getPossibleInstantsFor", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toTemporalDateTime_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneGetPossibleInstantsFor create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new TemporalTimeZonePrototypeBuiltinsFactory.JSTemporalTimeZoneGetPossibleInstantsForNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneGetterNode.class)
   public static final class JSTemporalTimeZoneGetterNodeGen
      extends TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneGetterNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToStringNode timeZoneGetter_toStringNode_;

      private JSTemporalTimeZoneGetterNodeGen(
         JSContext context, JSBuiltin builtin, TemporalTimeZonePrototypeBuiltins.TemporalTimeZonePrototype property, JavaScriptNode[] arguments
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
            if ((state_0 & 1) != 0 && JSGuards.isJSTemporalTimeZone(arguments0Value_)) {
               return this.timeZoneGetter(arguments0Value_, this.timeZoneGetter_toStringNode_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalTimeZone(arguments0Value_)) {
               return TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneGetterNode.error(arguments0Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_);
      }

      @Override
      public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalTimeZone(arguments0Value_)) {
            return TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneGetterNode.error(arguments0Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_));
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

         TruffleString var5;
         try {
            int state_0 = this.state_0_;
            if (!JSGuards.isJSTemporalTimeZone(arguments0Value)) {
               if (JSGuards.isJSTemporalTimeZone(arguments0Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
               }

               int var10;
               this.state_0_ = var10 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneGetterNode.error(arguments0Value);
            }

            this.timeZoneGetter_toStringNode_ = super.insert(JSToStringNode.create());
            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var5 = this.timeZoneGetter(arguments0Value, this.timeZoneGetter_toStringNode_);
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
         Object[] s = new Object[]{"timeZoneGetter", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.timeZoneGetter_toStringNode_));
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

      public static TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneGetterNode create(
         JSContext context, JSBuiltin builtin, TemporalTimeZonePrototypeBuiltins.TemporalTimeZonePrototype property, JavaScriptNode[] arguments
      ) {
         return new TemporalTimeZonePrototypeBuiltinsFactory.JSTemporalTimeZoneGetterNodeGen(context, builtin, property, arguments);
      }
   }

   @GeneratedBy(TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneToJSON.class)
   public static final class JSTemporalTimeZoneToJSONNodeGen
      extends TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneToJSON
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToStringNode toString_;

      private JSTemporalTimeZoneToJSONNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.toJSON(arguments0Value_, this.toString_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private TruffleString executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString var5;
         try {
            int state_0 = this.state_0_;
            this.toString_ = super.insert(JSToStringNode.create());
            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var5 = this.toJSON(arguments0Value, this.toString_);
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
         Object[] s = new Object[]{"toJSON", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toString_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneToJSON create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalTimeZonePrototypeBuiltinsFactory.JSTemporalTimeZoneToJSONNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneToString.class)
   public static final class JSTemporalTimeZoneToStringNodeGen
      extends TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneToString
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSTemporalTimeZoneToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

      public static TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneToString create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalTimeZonePrototypeBuiltinsFactory.JSTemporalTimeZoneToStringNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneValueOf.class)
   public static final class JSTemporalTimeZoneValueOfNodeGen
      extends TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneValueOf
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSTemporalTimeZoneValueOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

      public static TemporalTimeZonePrototypeBuiltins.JSTemporalTimeZoneValueOf create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalTimeZonePrototypeBuiltinsFactory.JSTemporalTimeZoneValueOfNodeGen(context, builtin, arguments);
      }
   }
}
