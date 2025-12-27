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
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSToNumberNode.class)
public final class JSToNumberNodeGen extends JSToNumberNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private JSToPrimitiveNode toPrimitiveHintNumberNode;
   @Node.Child
   private JSToNumberNode toNumberNode;
   @Node.Child
   private JSStringToNumberNode string_stringToNumberNode_;

   private JSToNumberNodeGen() {
   }

   @Override
   public Object execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
         int arg0Value_ = (Integer)arg0Value;
         return JSToNumberNode.doInteger(arg0Value_);
      } else if ((state_0 & 2) != 0 && arg0Value instanceof Boolean) {
         boolean arg0Value_ = (Boolean)arg0Value;
         return JSToNumberNode.doBoolean(arg0Value_);
      } else if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 30720) >>> 11, arg0Value)) {
         double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 30720) >>> 11, arg0Value);
         return JSToNumberNode.doDouble(arg0Value_);
      } else {
         if ((state_0 & 24) != 0) {
            if ((state_0 & 8) != 0 && JSGuards.isJSNull(arg0Value)) {
               return JSToNumberNode.doNull(arg0Value);
            }

            if ((state_0 & 16) != 0 && JSGuards.isUndefined(arg0Value)) {
               return JSToNumberNode.doUndefined(arg0Value);
            }
         }

         if ((state_0 & 32) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            return this.doString(arg0Value_, this.string_stringToNumberNode_);
         } else if ((state_0 & 64) != 0 && arg0Value instanceof JSObject) {
            JSObject arg0Value_ = (JSObject)arg0Value;
            return this.doJSObject(arg0Value_, this.toPrimitiveHintNumberNode, this.toNumberNode);
         } else if ((state_0 & 128) != 0 && arg0Value instanceof Symbol) {
            Symbol arg0Value_ = (Symbol)arg0Value;
            return this.doSymbol(arg0Value_);
         } else if ((state_0 & 256) != 0 && arg0Value instanceof BigInt) {
            BigInt arg0Value_ = (BigInt)arg0Value;
            return this.doBigInt(arg0Value_);
         } else {
            if ((state_0 & 1536) != 0) {
               if ((state_0 & 512) != 0 && JSGuards.isForeignObject(arg0Value)) {
                  return this.doForeignObject(arg0Value, this.toPrimitiveHintNumberNode, this.toNumberNode);
               }

               if ((state_0 & 1024) != 0 && JSGuards.isJavaNumber(arg0Value)) {
                  return JSToNumberNode.doJavaObject(arg0Value);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
         }
      }
   }

   private Object executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      Number var24;
      try {
         int state_0 = this.state_0_;
         if (arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            int var23;
            this.state_0_ = var23 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return JSToNumberNode.doInteger(arg0Value_);
         }

         if (arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            int var22;
            this.state_0_ = var22 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return JSToNumberNode.doBoolean(arg0Value_);
         }

         int doubleCast0;
         if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value)) != 0) {
            double arg0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
            state_0 |= doubleCast0 << 11;
            int var21;
            this.state_0_ = var21 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return JSToNumberNode.doDouble(arg0Value_);
         }

         if (JSGuards.isJSNull(arg0Value)) {
            int var19;
            this.state_0_ = var19 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return JSToNumberNode.doNull(arg0Value);
         }

         if (JSGuards.isUndefined(arg0Value)) {
            int var18;
            this.state_0_ = var18 = state_0 | 16;
            lock.unlock();
            hasLock = false;
            return JSToNumberNode.doUndefined(arg0Value);
         }

         if (arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            this.string_stringToNumberNode_ = super.insert(JSStringToNumberNode.create());
            int var17;
            this.state_0_ = var17 = state_0 | 32;
            lock.unlock();
            hasLock = false;
            return this.doString(arg0Value_, this.string_stringToNumberNode_);
         }

         if (arg0Value instanceof JSObject) {
            JSObject arg0Value_ = (JSObject)arg0Value;
            this.toPrimitiveHintNumberNode = super.insert(
               this.toPrimitiveHintNumberNode == null ? JSToPrimitiveNode.createHintNumber() : this.toPrimitiveHintNumberNode
            );
            this.toNumberNode = super.insert(this.toNumberNode == null ? JSToNumberNode.create() : this.toNumberNode);
            int var16;
            this.state_0_ = var16 = state_0 | 64;
            lock.unlock();
            hasLock = false;
            return this.doJSObject(arg0Value_, this.toPrimitiveHintNumberNode, this.toNumberNode);
         }

         if (arg0Value instanceof Symbol) {
            Symbol arg0Value_ = (Symbol)arg0Value;
            int var15;
            this.state_0_ = var15 = state_0 | 128;
            lock.unlock();
            hasLock = false;
            return this.doSymbol(arg0Value_);
         }

         if (arg0Value instanceof BigInt) {
            BigInt arg0Value_ = (BigInt)arg0Value;
            int var14;
            this.state_0_ = var14 = state_0 | 256;
            lock.unlock();
            hasLock = false;
            return this.doBigInt(arg0Value_);
         }

         if (!JSGuards.isForeignObject(arg0Value)) {
            if (!JSGuards.isJavaNumber(arg0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }

            int var13;
            this.state_0_ = var13 = state_0 | 1024;
            lock.unlock();
            hasLock = false;
            return JSToNumberNode.doJavaObject(arg0Value);
         }

         this.toPrimitiveHintNumberNode = super.insert(
            this.toPrimitiveHintNumberNode == null ? JSToPrimitiveNode.createHintNumber() : this.toPrimitiveHintNumberNode
         );
         this.toNumberNode = super.insert(this.toNumberNode == null ? JSToNumberNode.create() : this.toNumberNode);
         int var12;
         this.state_0_ = var12 = state_0 | 512;
         lock.unlock();
         hasLock = false;
         var24 = this.doForeignObject(arg0Value, this.toPrimitiveHintNumberNode, this.toNumberNode);
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return var24;
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
      Object[] s = new Object[]{"doInteger", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doBoolean", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doDouble", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doNull", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doUndefined", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doString", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.string_stringToNumberNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doJSObject", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.toPrimitiveHintNumberNode, this.toNumberNode));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"doSymbol", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doForeignObject", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.toPrimitiveHintNumberNode, this.toNumberNode));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      s = new Object[]{"doJavaObject", null, null};
      if ((state_0 & 1024) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[11] = s;
      return Introspection.Provider.create(data);
   }

   public static JSToNumberNode create() {
      return new JSToNumberNodeGen();
   }

   @GeneratedBy(JSToNumberNode.JSToNumberUnaryNode.class)
   public static final class JSToNumberUnaryNodeGen extends JSToNumberNode.JSToNumberUnaryNode implements Introspection.Provider {
      private JSToNumberUnaryNodeGen(JavaScriptNode operand) {
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

      public static JSToNumberNode.JSToNumberUnaryNode create(JavaScriptNode operand) {
         return new JSToNumberNodeGen.JSToNumberUnaryNodeGen(operand);
      }
   }
}
