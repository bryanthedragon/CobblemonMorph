package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.SafeInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(CeilNode.class)
public final class CeilNodeGen extends CeilNode implements Introspection.Provider {
   @Node.Child
   private JavaScriptNode arguments0_;
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private ConditionProfile isZero;
   @CompilerDirectives.CompilationFinal
   private ConditionProfile requiresNegativeZero;
   @CompilerDirectives.CompilationFinal
   private ConditionProfile fitsInt;
   @CompilerDirectives.CompilationFinal
   private ConditionProfile fitsSafeLong;

   private CeilNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
      if ((state_0 & 14) == 0 && (state_0 & 15) != 0) {
         return this.execute_int0(state_0, frameValue);
      } else {
         return (state_0 & 11) == 0 && (state_0 & 15) != 0 ? this.execute_double1(state_0, frameValue) : this.execute_generic2(state_0, frameValue);
      }
   }

   private Object execute_int0(int state_0, VirtualFrame frameValue) {
      int arguments0Value_;
      try {
         arguments0Value_ = this.arguments0_.executeInt(frameValue);
      } catch (UnexpectedResultException var5) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(var5.getResult());
      }

      assert (state_0 & 1) != 0;

      return CeilNode.ceilInt(arguments0Value_);
   }

   private Object execute_double1(int state_0, VirtualFrame frameValue) {
      long arguments0Value_long = 0L;
      int arguments0Value_int = 0;

      double arguments0Value_;
      try {
         if ((state_0 & 224) == 0 && (state_0 & 15) != 0) {
            arguments0Value_ = this.arguments0_.executeDouble(frameValue);
         } else if ((state_0 & 208) == 0 && (state_0 & 15) != 0) {
            arguments0Value_int = this.arguments0_.executeInt(frameValue);
            arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
         } else if ((state_0 & 112) == 0 && (state_0 & 15) != 0) {
            arguments0Value_long = this.arguments0_.executeLong(frameValue);
            arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
         } else {
            Object arguments0Value__ = this.arguments0_.execute(frameValue);
            arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 240) >>> 4, arguments0Value__);
         }
      } catch (UnexpectedResultException var9) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(var9.getResult());
      }

      assert (state_0 & 4) != 0;

      return CeilNode.ceilDouble(arguments0Value_, this.isZero, this.requiresNegativeZero, this.fitsInt, this.fitsSafeLong);
   }

   private Object execute_generic2(int state_0, VirtualFrame frameValue) {
      Object arguments0Value_ = this.arguments0_.execute(frameValue);
      if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
         int arguments0Value__ = (Integer)arguments0Value_;
         return CeilNode.ceilInt(arguments0Value__);
      } else if ((state_0 & 2) != 0 && arguments0Value_ instanceof SafeInteger) {
         SafeInteger arguments0Value__ = (SafeInteger)arguments0Value_;
         return CeilNode.ceilSafeInt(arguments0Value__);
      } else if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 240) >>> 4, arguments0Value_)) {
         double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 240) >>> 4, arguments0Value_);
         return CeilNode.ceilDouble(arguments0Value__, this.isZero, this.requiresNegativeZero, this.fitsInt, this.fitsSafeLong);
      } else if ((state_0 & 8) != 0) {
         return this.ceilToDouble(arguments0Value_, this.isZero, this.requiresNegativeZero, this.fitsInt, this.fitsSafeLong);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_);
      }
   }

   @Override
   public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 12) != 0) {
         return JSTypesGen.expectInteger(this.execute(frameValue));
      } else {
         int arguments0Value_;
         try {
            arguments0Value_ = this.arguments0_.executeInt(frameValue);
         } catch (UnexpectedResultException var5) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(var5.getResult()));
         }

         if ((state_0 & 1) != 0) {
            return CeilNode.ceilInt(arguments0Value_);
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
         if ((state_0 & 14) == 0 && (state_0 & 15) != 0) {
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

      Object var9;
      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (arguments0Value instanceof Integer) {
            int arguments0Value_ = (Integer)arguments0Value;
            int var18;
            this.state_0_ = var18 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return CeilNode.ceilInt(arguments0Value_);
         }

         if (arguments0Value instanceof SafeInteger) {
            SafeInteger arguments0Value_ = (SafeInteger)arguments0Value;
            int var17;
            this.state_0_ = var17 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return CeilNode.ceilSafeInt(arguments0Value_);
         }

         int doubleCast0;
         if (exclude != 0 || (doubleCast0 = JSTypesGen.specializeImplicitDouble(arguments0Value)) == 0) {
            this.isZero = this.isZero == null ? ConditionProfile.createBinaryProfile() : this.isZero;
            this.requiresNegativeZero = this.requiresNegativeZero == null ? ConditionProfile.createBinaryProfile() : this.requiresNegativeZero;
            this.fitsInt = this.fitsInt == null ? ConditionProfile.createBinaryProfile() : this.fitsInt;
            this.fitsSafeLong = this.fitsSafeLong == null ? ConditionProfile.createBinaryProfile() : this.fitsSafeLong;
            int var19;
            this.exclude_ = var19 = exclude | 1;
            state_0 &= -5;
            int var16;
            this.state_0_ = var16 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return this.ceilToDouble(arguments0Value, this.isZero, this.requiresNegativeZero, this.fitsInt, this.fitsSafeLong);
         }

         double arguments0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arguments0Value);
         this.isZero = this.isZero == null ? ConditionProfile.createBinaryProfile() : this.isZero;
         this.requiresNegativeZero = this.requiresNegativeZero == null ? ConditionProfile.createBinaryProfile() : this.requiresNegativeZero;
         this.fitsInt = this.fitsInt == null ? ConditionProfile.createBinaryProfile() : this.fitsInt;
         this.fitsSafeLong = this.fitsSafeLong == null ? ConditionProfile.createBinaryProfile() : this.fitsSafeLong;
         state_0 |= doubleCast0 << 4;
         int var14;
         this.state_0_ = var14 = state_0 | 4;
         lock.unlock();
         hasLock = false;
         var9 = CeilNode.ceilDouble(arguments0Value_, this.isZero, this.requiresNegativeZero, this.fitsInt, this.fitsSafeLong);
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return var9;
   }

   @Override
   public NodeCost getCost() {
      int state_0 = this.state_0_;
      if ((state_0 & 15) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & 15 & (state_0 & 15) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[5];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"ceilInt", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"ceilSafeInt", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"ceilDouble", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.isZero, this.requiresNegativeZero, this.fitsInt, this.fitsSafeLong));
         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"ceilToDouble", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.isZero, this.requiresNegativeZero, this.fitsInt, this.fitsSafeLong));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      return Introspection.Provider.create(data);
   }

   public static CeilNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
      return new CeilNodeGen(context, builtin, arguments);
   }
}
