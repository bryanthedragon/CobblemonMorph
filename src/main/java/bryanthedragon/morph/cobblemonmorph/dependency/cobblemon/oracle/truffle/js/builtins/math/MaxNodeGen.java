package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(MaxNode.class)
public final class MaxNodeGen extends MaxNode implements Introspection.Provider {
   @Node.Child
   private JavaScriptNode arguments0_;
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private ConditionProfile max2ParamInt_maxProfile_;
   @Node.Child
   private MaxNodeGen.Max2ParamData max2Param_cache;

   private MaxNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
      if (state_0 != 0 && arguments0Value_ instanceof Object[]) {
         Object[] arguments0Value__ = (Object[])arguments0Value_;
         if ((state_0 & 1) != 0 && arguments0Value__.length == 0) {
            return MaxNode.max0Param(arguments0Value__);
         }

         if ((state_0 & 2) != 0 && arguments0Value__.length == 1) {
            return this.max1Param(arguments0Value__);
         }

         if ((state_0 & 4) != 0 && arguments0Value__.length == 2 && MaxNode.caseIntInt(arguments0Value__)) {
            return MaxNode.max2ParamInt(arguments0Value__, this.max2ParamInt_maxProfile_);
         }

         if ((state_0 & 8) != 0) {
            MaxNodeGen.Max2ParamData s3_ = this.max2Param_cache;
            if (s3_ != null && arguments0Value__.length == 2 && !MaxNode.caseIntInt(arguments0Value__)) {
               return this.max2Param(arguments0Value__, s3_.isIntBranch_, s3_.maxProfile_, s3_.toNumber1Node_, s3_.toNumber2Node_);
            }
         }

         if ((state_0 & 16) != 0 && arguments0Value__.length >= 3) {
            return this.max(arguments0Value__);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arguments0Value_);
   }

   @Override
   public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 8) != 0) {
         return JSTypesGen.expectDouble(this.execute(frameValue));
      } else {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 19) != 0 && arguments0Value_ instanceof Object[]) {
            Object[] arguments0Value__ = (Object[])arguments0Value_;
            if ((state_0 & 1) != 0 && arguments0Value__.length == 0) {
               return MaxNode.max0Param(arguments0Value__);
            }

            if ((state_0 & 2) != 0 && arguments0Value__.length == 1) {
               return this.max1Param(arguments0Value__);
            }

            if ((state_0 & 16) != 0 && arguments0Value__.length >= 3) {
               return this.max(arguments0Value__);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectDouble(this.executeAndSpecialize(arguments0Value_));
      }
   }

   @Override
   public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 8) != 0) {
         return JSTypesGen.expectInteger(this.execute(frameValue));
      } else {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 4) != 0 && arguments0Value_ instanceof Object[]) {
            Object[] arguments0Value__ = (Object[])arguments0Value_;
            if (arguments0Value__.length == 2 && MaxNode.caseIntInt(arguments0Value__)) {
               return MaxNode.max2ParamInt(arguments0Value__, this.max2ParamInt_maxProfile_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_));
      }
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      int state_0 = this.state_0_;

      try {
         if ((state_0 & 27) == 0 && state_0 != 0) {
            this.executeInt(frameValue);
         } else if ((state_0 & 12) == 0 && state_0 != 0) {
            this.executeDouble(frameValue);
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

      try {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof Object[]) {
            Object[] arguments0Value_ = (Object[])arguments0Value;
            if (arguments0Value_.length == 0) {
               int var15;
               this.state_0_ = var15 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return MaxNode.max0Param(arguments0Value_);
            }

            if (arguments0Value_.length == 1) {
               int var14;
               this.state_0_ = var14 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.max1Param(arguments0Value_);
            }

            if (arguments0Value_.length == 2 && MaxNode.caseIntInt(arguments0Value_)) {
               this.max2ParamInt_maxProfile_ = ConditionProfile.createBinaryProfile();
               int var13;
               this.state_0_ = var13 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return MaxNode.max2ParamInt(arguments0Value_, this.max2ParamInt_maxProfile_);
            }

            if (arguments0Value_.length == 2 && !MaxNode.caseIntInt(arguments0Value_)) {
               MaxNodeGen.Max2ParamData s3_ = super.insert(new MaxNodeGen.Max2ParamData());
               s3_.isIntBranch_ = ConditionProfile.createBinaryProfile();
               s3_.maxProfile_ = ConditionProfile.createBinaryProfile();
               s3_.toNumber1Node_ = s3_.insertAccessor(JSToNumberNode.create());
               s3_.toNumber2Node_ = s3_.insertAccessor(JSToNumberNode.create());
               VarHandle.storeStoreFence();
               this.max2Param_cache = s3_;
               int var12;
               this.state_0_ = var12 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.max2Param(arguments0Value_, s3_.isIntBranch_, s3_.maxProfile_, s3_.toNumber1Node_, s3_.toNumber2Node_);
            }

            if (arguments0Value_.length >= 3) {
               int var11;
               this.state_0_ = var11 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.max(arguments0Value_);
            }
         }

         throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
      Object[] data = new Object[6];
      data[0] = 0;
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"max0Param", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"max1Param", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"max2ParamInt", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.max2ParamInt_maxProfile_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"max2Param", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         MaxNodeGen.Max2ParamData s3_ = this.max2Param_cache;
         if (s3_ != null) {
            cached.add(Arrays.asList(s3_.isIntBranch_, s3_.maxProfile_, s3_.toNumber1Node_, s3_.toNumber2Node_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"max", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      return Introspection.Provider.create(data);
   }

   public static MaxNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
      return new MaxNodeGen(context, builtin, arguments);
   }

   @GeneratedBy(MaxNode.class)
   private static final class Max2ParamData extends Node {
      @CompilerDirectives.CompilationFinal
      ConditionProfile isIntBranch_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile maxProfile_;
      @Node.Child
      JSToNumberNode toNumber1Node_;
      @Node.Child
      JSToNumberNode toNumber2Node_;

      Max2ParamData() {
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
