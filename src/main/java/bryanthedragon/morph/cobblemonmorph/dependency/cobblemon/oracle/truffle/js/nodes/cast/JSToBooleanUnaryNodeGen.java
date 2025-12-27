package com.oracle.truffle.js.nodes.cast;

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
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Symbol;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSToBooleanUnaryNode.class)
public final class JSToBooleanUnaryNodeGen extends JSToBooleanUnaryNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private JSToBooleanNode foreignObject_toBooleanNode_;

   private JSToBooleanUnaryNodeGen(JavaScriptNode operand) {
      super(operand);
   }

   @Override
   public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && operandNodeValue instanceof Boolean) {
         boolean operandNodeValue_ = (Boolean)operandNodeValue;
         return JSToBooleanUnaryNode.doBoolean(operandNodeValue_);
      } else {
         if ((state_0 & 6) != 0) {
            if ((state_0 & 2) != 0 && JSGuards.isJSNull(operandNodeValue)) {
               return JSToBooleanUnaryNode.doNull(operandNodeValue);
            }

            if ((state_0 & 4) != 0 && JSGuards.isUndefined(operandNodeValue)) {
               return JSToBooleanUnaryNode.doUndefined(operandNodeValue);
            }
         }

         if ((state_0 & 8) != 0 && operandNodeValue instanceof Integer) {
            int operandNodeValue_ = (Integer)operandNodeValue;
            return JSToBooleanUnaryNode.doInt(operandNodeValue_);
         } else if ((state_0 & 16) != 0 && operandNodeValue instanceof Long) {
            long operandNodeValue_ = (Long)operandNodeValue;
            return JSToBooleanUnaryNode.doLong(operandNodeValue_);
         } else if ((state_0 & 32) != 0 && JSTypesGen.isImplicitDouble((state_0 & 30720) >>> 11, operandNodeValue)) {
            double operandNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 30720) >>> 11, operandNodeValue);
            return JSToBooleanUnaryNode.doDouble(operandNodeValue_);
         } else if ((state_0 & 64) != 0 && operandNodeValue instanceof BigInt) {
            BigInt operandNodeValue_ = (BigInt)operandNodeValue;
            return JSToBooleanUnaryNode.doBigInt(operandNodeValue_);
         } else if ((state_0 & 128) != 0 && operandNodeValue instanceof TruffleString) {
            TruffleString operandNodeValue_ = (TruffleString)operandNodeValue;
            return JSToBooleanUnaryNode.doString(operandNodeValue_);
         } else if ((state_0 & 256) != 0 && JSGuards.isJSObject(operandNodeValue)) {
            return JSToBooleanUnaryNode.doObject(operandNodeValue);
         } else if ((state_0 & 512) != 0 && operandNodeValue instanceof Symbol) {
            Symbol operandNodeValue_ = (Symbol)operandNodeValue;
            return JSToBooleanUnaryNode.doSymbol(operandNodeValue_);
         } else if ((state_0 & 1024) != 0 && JSGuards.isForeignObject(operandNodeValue)) {
            return JSToBooleanUnaryNode.doForeignObject(operandNodeValue, this.foreignObject_toBooleanNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(operandNodeValue);
         }
      }
   }

   @Override
   public boolean executeBoolean(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 2046) == 0 && (state_0 & 2047) != 0) {
         return this.executeBoolean_boolean0(state_0, frameValue);
      } else if ((state_0 & 2039) == 0 && (state_0 & 2047) != 0) {
         return this.executeBoolean_int1(state_0, frameValue);
      } else if ((state_0 & 2031) == 0 && (state_0 & 2047) != 0) {
         return this.executeBoolean_long2(state_0, frameValue);
      } else {
         return (state_0 & 2015) == 0 && (state_0 & 2047) != 0
            ? this.executeBoolean_double3(state_0, frameValue)
            : this.executeBoolean_generic4(state_0, frameValue);
      }
   }

   private boolean executeBoolean_boolean0(int state_0, VirtualFrame frameValue) {
      boolean operandNodeValue_;
      try {
         operandNodeValue_ = super.operandNode.executeBoolean(frameValue);
      } catch (UnexpectedResultException var5) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(var5.getResult());
      }

      assert (state_0 & 1) != 0;

      return JSToBooleanUnaryNode.doBoolean(operandNodeValue_);
   }

   private boolean executeBoolean_int1(int state_0, VirtualFrame frameValue) {
      int operandNodeValue_;
      try {
         operandNodeValue_ = super.operandNode.executeInt(frameValue);
      } catch (UnexpectedResultException var5) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(var5.getResult());
      }

      assert (state_0 & 8) != 0;

      return JSToBooleanUnaryNode.doInt(operandNodeValue_);
   }

   private boolean executeBoolean_long2(int state_0, VirtualFrame frameValue) {
      long operandNodeValue_;
      try {
         operandNodeValue_ = super.operandNode.executeLong(frameValue);
      } catch (UnexpectedResultException var6) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(var6.getResult());
      }

      assert (state_0 & 16) != 0;

      return JSToBooleanUnaryNode.doLong(operandNodeValue_);
   }

   private boolean executeBoolean_double3(int state_0, VirtualFrame frameValue) {
      long operandNodeValue_long = 0L;
      int operandNodeValue_int = 0;

      double operandNodeValue_;
      try {
         if ((state_0 & 28672) == 0 && (state_0 & 2047) != 0) {
            operandNodeValue_ = super.operandNode.executeDouble(frameValue);
         } else if ((state_0 & 26624) == 0 && (state_0 & 2047) != 0) {
            operandNodeValue_int = super.operandNode.executeInt(frameValue);
            operandNodeValue_ = JSTypes.intToDouble(operandNodeValue_int);
         } else if ((state_0 & 14336) == 0 && (state_0 & 2047) != 0) {
            operandNodeValue_long = super.operandNode.executeLong(frameValue);
            operandNodeValue_ = JSTypes.longToDouble(operandNodeValue_long);
         } else {
            Object operandNodeValue__ = super.operandNode.execute(frameValue);
            operandNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 30720) >>> 11, operandNodeValue__);
         }
      } catch (UnexpectedResultException var9) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(var9.getResult());
      }

      assert (state_0 & 32) != 0;

      return JSToBooleanUnaryNode.doDouble(operandNodeValue_);
   }

   private boolean executeBoolean_generic4(int state_0, VirtualFrame frameValue) {
      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      if ((state_0 & 1) != 0 && operandNodeValue_ instanceof Boolean) {
         boolean operandNodeValue__ = (Boolean)operandNodeValue_;
         return JSToBooleanUnaryNode.doBoolean(operandNodeValue__);
      } else {
         if ((state_0 & 6) != 0) {
            if ((state_0 & 2) != 0 && JSGuards.isJSNull(operandNodeValue_)) {
               return JSToBooleanUnaryNode.doNull(operandNodeValue_);
            }

            if ((state_0 & 4) != 0 && JSGuards.isUndefined(operandNodeValue_)) {
               return JSToBooleanUnaryNode.doUndefined(operandNodeValue_);
            }
         }

         if ((state_0 & 8) != 0 && operandNodeValue_ instanceof Integer) {
            int operandNodeValue__ = (Integer)operandNodeValue_;
            return JSToBooleanUnaryNode.doInt(operandNodeValue__);
         } else if ((state_0 & 16) != 0 && operandNodeValue_ instanceof Long) {
            long operandNodeValue__ = (Long)operandNodeValue_;
            return JSToBooleanUnaryNode.doLong(operandNodeValue__);
         } else if ((state_0 & 32) != 0 && JSTypesGen.isImplicitDouble((state_0 & 30720) >>> 11, operandNodeValue_)) {
            double operandNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 30720) >>> 11, operandNodeValue_);
            return JSToBooleanUnaryNode.doDouble(operandNodeValue__);
         } else if ((state_0 & 64) != 0 && operandNodeValue_ instanceof BigInt) {
            BigInt operandNodeValue__ = (BigInt)operandNodeValue_;
            return JSToBooleanUnaryNode.doBigInt(operandNodeValue__);
         } else if ((state_0 & 128) != 0 && operandNodeValue_ instanceof TruffleString) {
            TruffleString operandNodeValue__ = (TruffleString)operandNodeValue_;
            return JSToBooleanUnaryNode.doString(operandNodeValue__);
         } else if ((state_0 & 256) != 0 && JSGuards.isJSObject(operandNodeValue_)) {
            return JSToBooleanUnaryNode.doObject(operandNodeValue_);
         } else if ((state_0 & 512) != 0 && operandNodeValue_ instanceof Symbol) {
            Symbol operandNodeValue__ = (Symbol)operandNodeValue_;
            return JSToBooleanUnaryNode.doSymbol(operandNodeValue__);
         } else if ((state_0 & 1024) != 0 && JSGuards.isForeignObject(operandNodeValue_)) {
            return JSToBooleanUnaryNode.doForeignObject(operandNodeValue_, this.foreignObject_toBooleanNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(operandNodeValue_);
         }
      }
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      this.executeBoolean(frameValue);
   }

   private boolean executeAndSpecialize(Object operandNodeValue) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      boolean operandNodeValue_;
      try {
         int state_0 = this.state_0_;
         if (operandNodeValue instanceof Boolean) {
            boolean operandNodeValue_x = (Boolean)operandNodeValue;
            int var23;
            this.state_0_ = var23 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return JSToBooleanUnaryNode.doBoolean(operandNodeValue_x);
         }

         if (JSGuards.isJSNull(operandNodeValue)) {
            int var22;
            this.state_0_ = var22 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return JSToBooleanUnaryNode.doNull(operandNodeValue);
         }

         if (JSGuards.isUndefined(operandNodeValue)) {
            int var21;
            this.state_0_ = var21 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return JSToBooleanUnaryNode.doUndefined(operandNodeValue);
         }

         if (operandNodeValue instanceof Integer) {
            int operandNodeValue_x = (Integer)operandNodeValue;
            int var20;
            this.state_0_ = var20 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return JSToBooleanUnaryNode.doInt(operandNodeValue_x);
         }

         if (operandNodeValue instanceof Long) {
            long operandNodeValue_x = (Long)operandNodeValue;
            int var19;
            this.state_0_ = var19 = state_0 | 16;
            lock.unlock();
            hasLock = false;
            return JSToBooleanUnaryNode.doLong(operandNodeValue_x);
         }

         int doubleCast0;
         if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(operandNodeValue)) != 0) {
            double operandNodeValue_x = JSTypesGen.asImplicitDouble(doubleCast0, operandNodeValue);
            state_0 |= doubleCast0 << 11;
            int var18;
            this.state_0_ = var18 = state_0 | 32;
            lock.unlock();
            hasLock = false;
            return JSToBooleanUnaryNode.doDouble(operandNodeValue_x);
         }

         if (operandNodeValue instanceof BigInt) {
            BigInt operandNodeValue_x = (BigInt)operandNodeValue;
            int var16;
            this.state_0_ = var16 = state_0 | 64;
            lock.unlock();
            hasLock = false;
            return JSToBooleanUnaryNode.doBigInt(operandNodeValue_x);
         }

         if (operandNodeValue instanceof TruffleString) {
            TruffleString operandNodeValue_x = (TruffleString)operandNodeValue;
            int var15;
            this.state_0_ = var15 = state_0 | 128;
            lock.unlock();
            hasLock = false;
            return JSToBooleanUnaryNode.doString(operandNodeValue_x);
         }

         if (JSGuards.isJSObject(operandNodeValue)) {
            int var14;
            this.state_0_ = var14 = state_0 | 256;
            lock.unlock();
            hasLock = false;
            return JSToBooleanUnaryNode.doObject(operandNodeValue);
         }

         if (!(operandNodeValue instanceof Symbol)) {
            if (!JSGuards.isForeignObject(operandNodeValue)) {
               throw new UnsupportedSpecializationException(this, new Node[]{super.operandNode}, operandNodeValue);
            }

            this.foreignObject_toBooleanNode_ = super.insert(JSToBooleanNode.create());
            int var13;
            this.state_0_ = var13 = state_0 | 1024;
            lock.unlock();
            hasLock = false;
            return JSToBooleanUnaryNode.doForeignObject(operandNodeValue, this.foreignObject_toBooleanNode_);
         }

         Symbol operandNodeValue_x = (Symbol)operandNodeValue;
         int var12;
         this.state_0_ = var12 = state_0 | 512;
         lock.unlock();
         hasLock = false;
         operandNodeValue_ = JSToBooleanUnaryNode.doSymbol(operandNodeValue_x);
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return operandNodeValue_;
   }

   @Override
   public NodeCost getCost() {
      int state_0 = this.state_0_;
      if ((state_0 & 2047) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & 2047 & (state_0 & 2047) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[12];
      data[0] = 0;
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"doBoolean", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doNull", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doUndefined", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doInt", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doLong", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doDouble", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"doString", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doObject", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doSymbol", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      s = new Object[]{"doForeignObject", null, null};
      if ((state_0 & 1024) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.foreignObject_toBooleanNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[11] = s;
      return Introspection.Provider.create(data);
   }

   public static JSToBooleanUnaryNode create(JavaScriptNode operand) {
      return new JSToBooleanUnaryNodeGen(operand);
   }
}
