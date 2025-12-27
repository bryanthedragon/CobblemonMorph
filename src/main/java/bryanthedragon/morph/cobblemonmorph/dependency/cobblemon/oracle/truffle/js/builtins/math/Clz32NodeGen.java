package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToUInt32Node;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(Clz32Node.class)
public final class Clz32NodeGen extends Clz32Node implements Introspection.Provider {
   @Node.Child
   private JavaScriptNode arguments0_;
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private JSToUInt32Node clz321_toUInt32Node_;

   private Clz32NodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
      return (state_0 & 2) == 0 && state_0 != 0 ? this.execute_int0(state_0, frameValue) : this.execute_generic1(state_0, frameValue);
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

      return Clz32Node.clz32(arguments0Value_);
   }

   private Object execute_generic1(int state_0, VirtualFrame frameValue) {
      Object arguments0Value_ = this.arguments0_.execute(frameValue);
      if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
         int arguments0Value__ = (Integer)arguments0Value_;
         return Clz32Node.clz32(arguments0Value__);
      } else if ((state_0 & 2) != 0) {
         return this.clz32(arguments0Value_, this.clz321_toUInt32Node_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_);
      }
   }

   @Override
   public int executeInt(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      return (state_0 & 2) == 0 && state_0 != 0 ? this.executeInt_int2(state_0, frameValue) : this.executeInt_generic3(state_0, frameValue);
   }

   private int executeInt_int2(int state_0, VirtualFrame frameValue) {
      int arguments0Value_;
      try {
         arguments0Value_ = this.arguments0_.executeInt(frameValue);
      } catch (UnexpectedResultException var5) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(var5.getResult());
      }

      assert (state_0 & 1) != 0;

      return Clz32Node.clz32(arguments0Value_);
   }

   private int executeInt_generic3(int state_0, VirtualFrame frameValue) {
      Object arguments0Value_ = this.arguments0_.execute(frameValue);
      if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
         int arguments0Value__ = (Integer)arguments0Value_;
         return Clz32Node.clz32(arguments0Value__);
      } else if ((state_0 & 2) != 0) {
         return this.clz32(arguments0Value_, this.clz321_toUInt32Node_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_);
      }
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      this.executeInt(frameValue);
   }

   private int executeAndSpecialize(Object arguments0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      int var6;
      try {
         int state_0 = this.state_0_;
         if (!(arguments0Value instanceof Integer)) {
            this.clz321_toUInt32Node_ = super.insert(JSToUInt32Node.create());
            int var11;
            this.state_0_ = var11 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.clz32(arguments0Value, this.clz321_toUInt32Node_);
         }

         int arguments0Value_ = (Integer)arguments0Value;
         int var10;
         this.state_0_ = var10 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var6 = Clz32Node.clz32(arguments0Value_);
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
      Object[] s = new Object[]{"clz32", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"clz32", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.clz321_toUInt32Node_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      return Introspection.Provider.create(data);
   }

   public static Clz32Node create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
      return new Clz32NodeGen(context, builtin, arguments);
   }
}
