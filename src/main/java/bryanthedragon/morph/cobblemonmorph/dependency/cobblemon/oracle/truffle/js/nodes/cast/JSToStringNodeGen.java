package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSToStringNode.class)
public final class JSToStringNodeGen extends JSToStringNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private JSToPrimitiveNode toPrimitiveHintStringNode;
   @Node.Child
   private JSToStringNode toStringNode;
   @Node.Child
   private JSDoubleToStringNode double_doubleToStringNode_;

   private JSToStringNodeGen() {
   }

   private JSToStringNodeGen(boolean undefinedToEmpty, boolean symbolToString) {
      super(undefinedToEmpty, symbolToString);
   }

   @Override
   public TruffleString executeString(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof TruffleString) {
         TruffleString arg0Value_ = (TruffleString)arg0Value;
         return this.doString(arg0Value_);
      } else {
         if ((state_0 & 6) != 0) {
            if ((state_0 & 2) != 0 && JSGuards.isJSNull(arg0Value)) {
               return this.doNull(arg0Value);
            }

            if ((state_0 & 4) != 0 && JSGuards.isUndefined(arg0Value)) {
               return this.doUndefined(arg0Value);
            }
         }

         if ((state_0 & 8) != 0 && arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            return this.doBoolean(arg0Value_);
         } else if ((state_0 & 16) != 0 && arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            return this.doInteger(arg0Value_);
         } else if ((state_0 & 32) != 0 && arg0Value instanceof BigInt) {
            BigInt arg0Value_ = (BigInt)arg0Value;
            return this.doBigInt(arg0Value_);
         } else if ((state_0 & 64) != 0 && arg0Value instanceof Long) {
            long arg0Value_ = (Long)arg0Value;
            return this.doLong(arg0Value_);
         } else if ((state_0 & 128) != 0 && JSTypesGen.isImplicitDouble((state_0 & 30720) >>> 11, arg0Value)) {
            double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 30720) >>> 11, arg0Value);
            return this.doDouble(arg0Value_, this.double_doubleToStringNode_);
         } else if ((state_0 & 256) != 0 && arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            return this.doJSObject(arg0Value_, this.toPrimitiveHintStringNode, this.toStringNode);
         } else if ((state_0 & 512) != 0 && arg0Value instanceof Symbol) {
            Symbol arg0Value_ = (Symbol)arg0Value;
            return this.doSymbol(arg0Value_);
         } else if ((state_0 & 1024) != 0 && JSGuards.isForeignObject(arg0Value)) {
            return this.doTruffleObject(arg0Value, this.toPrimitiveHintStringNode, this.toStringNode);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
         }
      }
   }

   private TruffleString executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      TruffleString arg0Value_;
      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (arg0Value instanceof TruffleString) {
            arg0Value_ = (TruffleString)arg0Value;
            int var25;
            this.state_0_ = var25 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return this.doString(arg0Value_);
         }

         if (JSGuards.isJSNull(arg0Value)) {
            int var24;
            this.state_0_ = var24 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doNull(arg0Value);
         }

         if (exclude == 0 && JSGuards.isUndefined(arg0Value)) {
            int var23;
            this.state_0_ = var23 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.doUndefined(arg0Value);
         }

         if (arg0Value instanceof Boolean) {
            boolean arg0Value_x = (Boolean)arg0Value;
            int var22;
            this.state_0_ = var22 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return this.doBoolean(arg0Value_x);
         }

         if (arg0Value instanceof Integer) {
            int arg0Value_x = (Integer)arg0Value;
            int var21;
            this.state_0_ = var21 = state_0 | 16;
            lock.unlock();
            hasLock = false;
            return this.doInteger(arg0Value_x);
         }

         if (arg0Value instanceof BigInt) {
            BigInt arg0Value_x = (BigInt)arg0Value;
            int var20;
            this.state_0_ = var20 = state_0 | 32;
            lock.unlock();
            hasLock = false;
            return this.doBigInt(arg0Value_x);
         }

         if (arg0Value instanceof Long) {
            long arg0Value_x = (Long)arg0Value;
            int var19;
            this.state_0_ = var19 = state_0 | 64;
            lock.unlock();
            hasLock = false;
            return this.doLong(arg0Value_x);
         }

         int doubleCast0;
         if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value)) != 0) {
            double arg0Value_x = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
            this.double_doubleToStringNode_ = super.insert(JSDoubleToStringNode.create());
            state_0 |= doubleCast0 << 11;
            int var18;
            this.state_0_ = var18 = state_0 | 128;
            lock.unlock();
            hasLock = false;
            return this.doDouble(arg0Value_x, this.double_doubleToStringNode_);
         }

         if (arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_x = (JSDynamicObject)arg0Value;
            this.toPrimitiveHintStringNode = super.insert(
               this.toPrimitiveHintStringNode == null ? JSToPrimitiveNode.createHintString() : this.toPrimitiveHintStringNode
            );
            this.toStringNode = super.insert(this.toStringNode == null ? JSToStringNode.create() : this.toStringNode);
            int var26;
            this.exclude_ = var26 = exclude | 1;
            state_0 &= -5;
            int var16;
            this.state_0_ = var16 = state_0 | 256;
            lock.unlock();
            hasLock = false;
            return this.doJSObject(arg0Value_x, this.toPrimitiveHintStringNode, this.toStringNode);
         }

         if (arg0Value instanceof Symbol) {
            Symbol arg0Value_x = (Symbol)arg0Value;
            int var14;
            this.state_0_ = var14 = state_0 | 512;
            lock.unlock();
            hasLock = false;
            return this.doSymbol(arg0Value_x);
         }

         if (!JSGuards.isForeignObject(arg0Value)) {
            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
         }

         this.toPrimitiveHintStringNode = super.insert(
            this.toPrimitiveHintStringNode == null ? JSToPrimitiveNode.createHintString() : this.toPrimitiveHintStringNode
         );
         this.toStringNode = super.insert(this.toStringNode == null ? JSToStringNode.create() : this.toStringNode);
         int var13;
         this.state_0_ = var13 = state_0 | 1024;
         lock.unlock();
         hasLock = false;
         arg0Value_ = this.doTruffleObject(arg0Value, this.toPrimitiveHintStringNode, this.toStringNode);
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return arg0Value_;
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
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doString", null, null};
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
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doBoolean", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doInteger", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doLong", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"doDouble", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.double_doubleToStringNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doJSObject", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.toPrimitiveHintStringNode, this.toStringNode));
         s[2] = cached;
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
      s = new Object[]{"doTruffleObject", null, null};
      if ((state_0 & 1024) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.toPrimitiveHintStringNode, this.toStringNode));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[11] = s;
      return Introspection.Provider.create(data);
   }

   public static JSToStringNode create() {
      return new JSToStringNodeGen();
   }

   public static JSToStringNode create(boolean undefinedToEmpty, boolean symbolToString) {
      return new JSToStringNodeGen(undefinedToEmpty, symbolToString);
   }

   @GeneratedBy(JSToStringNode.JSToStringWrapperNode.class)
   public static final class JSToStringWrapperNodeGen extends JSToStringNode.JSToStringWrapperNode implements Introspection.Provider {
      private JSToStringWrapperNodeGen(JavaScriptNode operand) {
         super(operand);
      }

      @Override
      public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
         return this.doDefault(operandNodeValue);
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object operandNodeValue_ = super.operandNode.execute(frameValue);
         return this.doDefault(operandNodeValue_);
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
         Object[] s = new Object[]{"doDefault", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static JSToStringNode.JSToStringWrapperNode create(JavaScriptNode operand) {
         return new JSToStringNodeGen.JSToStringWrapperNodeGen(operand);
      }
   }
}
