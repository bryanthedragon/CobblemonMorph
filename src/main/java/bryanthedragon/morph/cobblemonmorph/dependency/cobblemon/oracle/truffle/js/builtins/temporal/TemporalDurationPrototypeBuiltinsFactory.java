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
import com.oracle.truffle.js.nodes.cast.JSNumberToBigIntNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerWithoutRoundingNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.temporal.TemporalBalanceDurationRelativeNode;
import com.oracle.truffle.js.nodes.temporal.TemporalDurationAddNode;
import com.oracle.truffle.js.nodes.temporal.TemporalRoundDurationNode;
import com.oracle.truffle.js.nodes.temporal.TemporalUnbalanceDurationRelativeNode;
import com.oracle.truffle.js.nodes.temporal.ToLimitedTemporalDurationNode;
import com.oracle.truffle.js.nodes.temporal.ToRelativeTemporalObjectNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(TemporalDurationPrototypeBuiltins.class)
public final class TemporalDurationPrototypeBuiltinsFactory {
   @GeneratedBy(TemporalDurationPrototypeBuiltins.JSTemporalDurationAbs.class)
   public static final class JSTemporalDurationAbsNodeGen extends TemporalDurationPrototypeBuiltins.JSTemporalDurationAbs implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSTemporalDurationAbsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.abs(arguments0Value_);
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
         Object[] s = new Object[]{"abs", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalDurationPrototypeBuiltins.JSTemporalDurationAbs create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationAbsNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalDurationPrototypeBuiltins.JSTemporalDurationAdd.class)
   public static final class JSTemporalDurationAddNodeGen extends TemporalDurationPrototypeBuiltins.JSTemporalDurationAdd implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationAddNodeGen.AddData add_cache;

      private JSTemporalDurationAddNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationAddNodeGen.AddData s0_ = this.add_cache;
            if (s0_ != null) {
               return this.add(
                  arguments0Value_,
                  arguments1Value_,
                  arguments2Value_,
                  s0_.durationAddNode_,
                  s0_.toRelativeTemporalObjectNode_,
                  s0_.toLimitedTemporalDurationNode_
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
            TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationAddNodeGen.AddData s0_ = super.insert(
               new TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationAddNodeGen.AddData()
            );
            s0_.durationAddNode_ = s0_.insertAccessor(TemporalDurationAddNode.create(this.getContext()));
            s0_.toRelativeTemporalObjectNode_ = s0_.insertAccessor(ToRelativeTemporalObjectNode.create(this.getContext()));
            s0_.toLimitedTemporalDurationNode_ = s0_.insertAccessor(ToLimitedTemporalDurationNode.create());
            VarHandle.storeStoreFence();
            this.add_cache = s0_;
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.add(
               arguments0Value, arguments1Value, arguments2Value, s0_.durationAddNode_, s0_.toRelativeTemporalObjectNode_, s0_.toLimitedTemporalDurationNode_
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
         Object[] s = new Object[]{"add", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationAddNodeGen.AddData s0_ = this.add_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.durationAddNode_, s0_.toRelativeTemporalObjectNode_, s0_.toLimitedTemporalDurationNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalDurationPrototypeBuiltins.JSTemporalDurationAdd create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationAddNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(TemporalDurationPrototypeBuiltins.JSTemporalDurationAdd.class)
      private static final class AddData extends Node {
         @Node.Child
         TemporalDurationAddNode durationAddNode_;
         @Node.Child
         ToRelativeTemporalObjectNode toRelativeTemporalObjectNode_;
         @Node.Child
         ToLimitedTemporalDurationNode toLimitedTemporalDurationNode_;

         AddData() {
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

   @GeneratedBy(TemporalDurationPrototypeBuiltins.JSTemporalDurationGetterNode.class)
   public static final class JSTemporalDurationGetterNodeGen
      extends TemporalDurationPrototypeBuiltins.JSTemporalDurationGetterNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSTemporalDurationGetterNodeGen(
         JSContext context, JSBuiltin builtin, TemporalDurationPrototypeBuiltins.TemporalDurationPrototype property, JavaScriptNode[] arguments
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
            if ((state_0 & 1) != 0 && JSGuards.isJSTemporalDuration(arguments0Value_)) {
               return this.durationGetter(arguments0Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalDuration(arguments0Value_)) {
               return TemporalDurationPrototypeBuiltins.JSTemporalDurationGetterNode.error(arguments0Value_);
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
            if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalDuration(arguments0Value_)) {
               return TemporalDurationPrototypeBuiltins.JSTemporalDurationGetterNode.error(arguments0Value_);
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
         if (JSGuards.isJSTemporalDuration(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 1;
            return this.durationGetter(arguments0Value);
         } else if (!JSGuards.isJSTemporalDuration(arguments0Value)) {
            int var3;
            this.state_0_ = var3 = state_0 | 2;
            return TemporalDurationPrototypeBuiltins.JSTemporalDurationGetterNode.error(arguments0Value);
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
         Object[] s = new Object[]{"durationGetter", null, null};
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

      public static TemporalDurationPrototypeBuiltins.JSTemporalDurationGetterNode create(
         JSContext context, JSBuiltin builtin, TemporalDurationPrototypeBuiltins.TemporalDurationPrototype property, JavaScriptNode[] arguments
      ) {
         return new TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationGetterNodeGen(context, builtin, property, arguments);
      }
   }

   @GeneratedBy(TemporalDurationPrototypeBuiltins.JSTemporalDurationNegated.class)
   public static final class JSTemporalDurationNegatedNodeGen
      extends TemporalDurationPrototypeBuiltins.JSTemporalDurationNegated
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSTemporalDurationNegatedNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.negated(arguments0Value_);
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
         Object[] s = new Object[]{"negated", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalDurationPrototypeBuiltins.JSTemporalDurationNegated create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationNegatedNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalDurationPrototypeBuiltins.JSTemporalDurationRound.class)
   public static final class JSTemporalDurationRoundNodeGen extends TemporalDurationPrototypeBuiltins.JSTemporalDurationRound implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationRoundNodeGen.RoundData round_cache;

      private JSTemporalDurationRoundNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationRoundNodeGen.RoundData s0_ = this.round_cache;
            if (s0_ != null) {
               return this.round(
                  arguments0Value_,
                  arguments1Value_,
                  s0_.toNumber_,
                  s0_.namesNode_,
                  s0_.toBigInt_,
                  s0_.equalNode_,
                  s0_.durationAddNode_,
                  s0_.roundToIsTString_,
                  s0_.realtiveToIsZonedDateTime_,
                  s0_.toRelativeTemporalObjectNode_,
                  s0_.roundDurationNode_,
                  s0_.unbalanceDurationRelativeNode_,
                  s0_.balanceDurationRelativeNode_
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
            TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationRoundNodeGen.RoundData s0_ = super.insert(
               new TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationRoundNodeGen.RoundData()
            );
            s0_.toNumber_ = s0_.insertAccessor(JSToNumberNode.create());
            s0_.namesNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
            s0_.toBigInt_ = s0_.insertAccessor(JSNumberToBigIntNode.create());
            s0_.equalNode_ = s0_.insertAccessor(TruffleString.EqualNode.create());
            s0_.durationAddNode_ = s0_.insertAccessor(TemporalDurationAddNode.create(this.getContext()));
            s0_.roundToIsTString_ = ConditionProfile.create();
            s0_.realtiveToIsZonedDateTime_ = ConditionProfile.create();
            s0_.toRelativeTemporalObjectNode_ = s0_.insertAccessor(ToRelativeTemporalObjectNode.create(this.getContext()));
            s0_.roundDurationNode_ = s0_.insertAccessor(TemporalRoundDurationNode.create(this.getContext()));
            s0_.unbalanceDurationRelativeNode_ = s0_.insertAccessor(TemporalUnbalanceDurationRelativeNode.create(this.getContext()));
            s0_.balanceDurationRelativeNode_ = s0_.insertAccessor(TemporalBalanceDurationRelativeNode.create(this.getContext()));
            VarHandle.storeStoreFence();
            this.round_cache = s0_;
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.round(
               arguments0Value,
               arguments1Value,
               s0_.toNumber_,
               s0_.namesNode_,
               s0_.toBigInt_,
               s0_.equalNode_,
               s0_.durationAddNode_,
               s0_.roundToIsTString_,
               s0_.realtiveToIsZonedDateTime_,
               s0_.toRelativeTemporalObjectNode_,
               s0_.roundDurationNode_,
               s0_.unbalanceDurationRelativeNode_,
               s0_.balanceDurationRelativeNode_
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
         Object[] s = new Object[]{"round", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationRoundNodeGen.RoundData s0_ = this.round_cache;
            if (s0_ != null) {
               cached.add(
                  Arrays.asList(
                     s0_.toNumber_,
                     s0_.namesNode_,
                     s0_.toBigInt_,
                     s0_.equalNode_,
                     s0_.durationAddNode_,
                     s0_.roundToIsTString_,
                     s0_.realtiveToIsZonedDateTime_,
                     s0_.toRelativeTemporalObjectNode_,
                     s0_.roundDurationNode_,
                     s0_.unbalanceDurationRelativeNode_,
                     s0_.balanceDurationRelativeNode_
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

      public static TemporalDurationPrototypeBuiltins.JSTemporalDurationRound create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationRoundNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(TemporalDurationPrototypeBuiltins.JSTemporalDurationRound.class)
      private static final class RoundData extends Node {
         @Node.Child
         JSToNumberNode toNumber_;
         @Node.Child
         EnumerableOwnPropertyNamesNode namesNode_;
         @Node.Child
         JSNumberToBigIntNode toBigInt_;
         @Node.Child
         TruffleString.EqualNode equalNode_;
         @Node.Child
         TemporalDurationAddNode durationAddNode_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile roundToIsTString_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile realtiveToIsZonedDateTime_;
         @Node.Child
         ToRelativeTemporalObjectNode toRelativeTemporalObjectNode_;
         @Node.Child
         TemporalRoundDurationNode roundDurationNode_;
         @Node.Child
         TemporalUnbalanceDurationRelativeNode unbalanceDurationRelativeNode_;
         @Node.Child
         TemporalBalanceDurationRelativeNode balanceDurationRelativeNode_;

         RoundData() {
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

   @GeneratedBy(TemporalDurationPrototypeBuiltins.JSTemporalDurationSubtract.class)
   public static final class JSTemporalDurationSubtractNodeGen
      extends TemporalDurationPrototypeBuiltins.JSTemporalDurationSubtract
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
      private TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationSubtractNodeGen.SubtractData subtract_cache;

      private JSTemporalDurationSubtractNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationSubtractNodeGen.SubtractData s0_ = this.subtract_cache;
            if (s0_ != null) {
               return this.subtract(
                  arguments0Value_,
                  arguments1Value_,
                  arguments2Value_,
                  s0_.durationAddNode_,
                  s0_.toRelativeTemporalObjectNode_,
                  s0_.toLimitedTemporalDurationNode_
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
            TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationSubtractNodeGen.SubtractData s0_ = super.insert(
               new TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationSubtractNodeGen.SubtractData()
            );
            s0_.durationAddNode_ = s0_.insertAccessor(TemporalDurationAddNode.create(this.getContext()));
            s0_.toRelativeTemporalObjectNode_ = s0_.insertAccessor(ToRelativeTemporalObjectNode.create(this.getContext()));
            s0_.toLimitedTemporalDurationNode_ = s0_.insertAccessor(ToLimitedTemporalDurationNode.create());
            VarHandle.storeStoreFence();
            this.subtract_cache = s0_;
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.subtract(
               arguments0Value, arguments1Value, arguments2Value, s0_.durationAddNode_, s0_.toRelativeTemporalObjectNode_, s0_.toLimitedTemporalDurationNode_
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
         Object[] s = new Object[]{"subtract", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationSubtractNodeGen.SubtractData s0_ = this.subtract_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.durationAddNode_, s0_.toRelativeTemporalObjectNode_, s0_.toLimitedTemporalDurationNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalDurationPrototypeBuiltins.JSTemporalDurationSubtract create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationSubtractNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(TemporalDurationPrototypeBuiltins.JSTemporalDurationSubtract.class)
      private static final class SubtractData extends Node {
         @Node.Child
         TemporalDurationAddNode durationAddNode_;
         @Node.Child
         ToRelativeTemporalObjectNode toRelativeTemporalObjectNode_;
         @Node.Child
         ToLimitedTemporalDurationNode toLimitedTemporalDurationNode_;

         SubtractData() {
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

   @GeneratedBy(TemporalDurationPrototypeBuiltins.JSTemporalDurationToLocaleString.class)
   public static final class JSTemporalDurationToLocaleStringNodeGen
      extends TemporalDurationPrototypeBuiltins.JSTemporalDurationToLocaleString
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSNumberToBigIntNode toBigIntNode_;

      private JSTemporalDurationToLocaleStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.toString(arguments0Value_, this.toBigIntNode_);
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
            this.toBigIntNode_ = super.insert(JSNumberToBigIntNode.create());
            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var5 = this.toString(arguments0Value, this.toBigIntNode_);
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
         Object[] s = new Object[]{"toString", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toBigIntNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalDurationPrototypeBuiltins.JSTemporalDurationToLocaleString create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationToLocaleStringNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalDurationPrototypeBuiltins.JSTemporalDurationToString.class)
   public static final class JSTemporalDurationToStringNodeGen
      extends TemporalDurationPrototypeBuiltins.JSTemporalDurationToString
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationToStringNodeGen.ToStringData toString_cache;

      private JSTemporalDurationToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationToStringNodeGen.ToStringData s0_ = this.toString_cache;
            if (s0_ != null) {
               return this.toString(arguments0Value_, arguments1Value_, s0_.toBigIntNode_, s0_.toStringNode_, s0_.equalNode_, s0_.roundDurationNode_);
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
            TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationToStringNodeGen.ToStringData s0_ = super.insert(
               new TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationToStringNodeGen.ToStringData()
            );
            s0_.toBigIntNode_ = s0_.insertAccessor(JSNumberToBigIntNode.create());
            s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
            s0_.equalNode_ = s0_.insertAccessor(TruffleString.EqualNode.create());
            s0_.roundDurationNode_ = s0_.insertAccessor(TemporalRoundDurationNode.create(this.getContext()));
            VarHandle.storeStoreFence();
            this.toString_cache = s0_;
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.toString(arguments0Value, arguments1Value, s0_.toBigIntNode_, s0_.toStringNode_, s0_.equalNode_, s0_.roundDurationNode_);
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
            TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationToStringNodeGen.ToStringData s0_ = this.toString_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.toBigIntNode_, s0_.toStringNode_, s0_.equalNode_, s0_.roundDurationNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalDurationPrototypeBuiltins.JSTemporalDurationToString create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationToStringNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(TemporalDurationPrototypeBuiltins.JSTemporalDurationToString.class)
      private static final class ToStringData extends Node {
         @Node.Child
         JSNumberToBigIntNode toBigIntNode_;
         @Node.Child
         JSToStringNode toStringNode_;
         @Node.Child
         TruffleString.EqualNode equalNode_;
         @Node.Child
         TemporalRoundDurationNode roundDurationNode_;

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

   @GeneratedBy(TemporalDurationPrototypeBuiltins.JSTemporalDurationTotal.class)
   public static final class JSTemporalDurationTotalNodeGen extends TemporalDurationPrototypeBuiltins.JSTemporalDurationTotal implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationTotalNodeGen.TotalData total_cache;

      private JSTemporalDurationTotalNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationTotalNodeGen.TotalData s0_ = this.total_cache;
            if (s0_ != null) {
               return this.total(
                  arguments0Value_,
                  arguments1Value_,
                  s0_.namesNode_,
                  s0_.toBigIntNode_,
                  s0_.equalNode_,
                  s0_.toRelativeTemporalObjectNode_,
                  s0_.roundDurationNode_,
                  s0_.unbalanceDurationRelativeNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public double executeDouble(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationTotalNodeGen.TotalData s0_ = this.total_cache;
            if (s0_ != null) {
               return this.total(
                  arguments0Value_,
                  arguments1Value_,
                  s0_.namesNode_,
                  s0_.toBigIntNode_,
                  s0_.equalNode_,
                  s0_.toRelativeTemporalObjectNode_,
                  s0_.roundDurationNode_,
                  s0_.unbalanceDurationRelativeNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeDouble(frameValue);
      }

      private double executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         double var7;
         try {
            int state_0 = this.state_0_;
            TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationTotalNodeGen.TotalData s0_ = super.insert(
               new TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationTotalNodeGen.TotalData()
            );
            s0_.namesNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
            s0_.toBigIntNode_ = s0_.insertAccessor(JSNumberToBigIntNode.create());
            s0_.equalNode_ = s0_.insertAccessor(TruffleString.EqualNode.create());
            s0_.toRelativeTemporalObjectNode_ = s0_.insertAccessor(ToRelativeTemporalObjectNode.create(this.getContext()));
            s0_.roundDurationNode_ = s0_.insertAccessor(TemporalRoundDurationNode.create(this.getContext()));
            s0_.unbalanceDurationRelativeNode_ = s0_.insertAccessor(TemporalUnbalanceDurationRelativeNode.create(this.getContext()));
            VarHandle.storeStoreFence();
            this.total_cache = s0_;
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.total(
               arguments0Value,
               arguments1Value,
               s0_.namesNode_,
               s0_.toBigIntNode_,
               s0_.equalNode_,
               s0_.toRelativeTemporalObjectNode_,
               s0_.roundDurationNode_,
               s0_.unbalanceDurationRelativeNode_
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
         Object[] s = new Object[]{"total", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationTotalNodeGen.TotalData s0_ = this.total_cache;
            if (s0_ != null) {
               cached.add(
                  Arrays.asList(
                     s0_.namesNode_,
                     s0_.toBigIntNode_,
                     s0_.equalNode_,
                     s0_.toRelativeTemporalObjectNode_,
                     s0_.roundDurationNode_,
                     s0_.unbalanceDurationRelativeNode_
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

      public static TemporalDurationPrototypeBuiltins.JSTemporalDurationTotal create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationTotalNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(TemporalDurationPrototypeBuiltins.JSTemporalDurationTotal.class)
      private static final class TotalData extends Node {
         @Node.Child
         EnumerableOwnPropertyNamesNode namesNode_;
         @Node.Child
         JSNumberToBigIntNode toBigIntNode_;
         @Node.Child
         TruffleString.EqualNode equalNode_;
         @Node.Child
         ToRelativeTemporalObjectNode toRelativeTemporalObjectNode_;
         @Node.Child
         TemporalRoundDurationNode roundDurationNode_;
         @Node.Child
         TemporalUnbalanceDurationRelativeNode unbalanceDurationRelativeNode_;

         TotalData() {
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

   @GeneratedBy(TemporalDurationPrototypeBuiltins.JSTemporalDurationValueOf.class)
   public static final class JSTemporalDurationValueOfNodeGen
      extends TemporalDurationPrototypeBuiltins.JSTemporalDurationValueOf
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSTemporalDurationValueOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

      public static TemporalDurationPrototypeBuiltins.JSTemporalDurationValueOf create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationValueOfNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(TemporalDurationPrototypeBuiltins.JSTemporalDurationWith.class)
   public static final class JSTemporalDurationWithNodeGen extends TemporalDurationPrototypeBuiltins.JSTemporalDurationWith implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToIntegerWithoutRoundingNode toInt_;

      private JSTemporalDurationWithNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.with(arguments0Value_, arguments1Value_, this.toInt_);
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
            this.toInt_ = super.insert(JSToIntegerWithoutRoundingNode.create());
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.with(arguments0Value, arguments1Value, this.toInt_);
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
         Object[] s = new Object[]{"with", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toInt_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static TemporalDurationPrototypeBuiltins.JSTemporalDurationWith create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationWithNodeGen(context, builtin, arguments);
      }
   }
}
