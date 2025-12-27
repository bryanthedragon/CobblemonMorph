package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.Symbol;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSToPropertyKeyNode.class)
public final class JSToPropertyKeyNodeGen extends JSToPropertyKeyNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private JSToPropertyKeyNodeGen.OtherData other_cache;

   private JSToPropertyKeyNodeGen() {
   }

   @Override
   public Object execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof TruffleString) {
         TruffleString arg0Value_ = (TruffleString)arg0Value;
         return this.doTString(arg0Value_);
      } else if ((state_0 & 2) != 0 && arg0Value instanceof Symbol) {
         Symbol arg0Value_ = (Symbol)arg0Value;
         return this.doSymbol(arg0Value_);
      } else {
         if ((state_0 & 4) != 0) {
            JSToPropertyKeyNodeGen.OtherData s2_ = this.other_cache;
            if (s2_ != null && !JSGuards.isSymbol(arg0Value)) {
               return this.doOther(arg0Value, s2_.toPrimitiveNode_, s2_.toStringNode_, s2_.isSymbol_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   private Object executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      Symbol var6;
      try {
         int state_0 = this.state_0_;
         if (arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return this.doTString(arg0Value_);
         }

         if (!(arg0Value instanceof Symbol)) {
            if (JSGuards.isSymbol(arg0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }

            JSToPropertyKeyNodeGen.OtherData s2_ = super.insert(new JSToPropertyKeyNodeGen.OtherData());
            s2_.toPrimitiveNode_ = s2_.insertAccessor(JSToPrimitiveNode.createHintString());
            s2_.toStringNode_ = s2_.insertAccessor(JSToStringNode.create());
            s2_.isSymbol_ = ConditionProfile.createBinaryProfile();
            VarHandle.storeStoreFence();
            this.other_cache = s2_;
            int var11;
            this.state_0_ = var11 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.doOther(arg0Value, s2_.toPrimitiveNode_, s2_.toStringNode_, s2_.isSymbol_);
         }

         Symbol arg0Value_ = (Symbol)arg0Value;
         int var10;
         this.state_0_ = var10 = state_0 | 2;
         lock.unlock();
         hasLock = false;
         var6 = this.doSymbol(arg0Value_);
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
      Object[] data = new Object[4];
      data[0] = 0;
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"doTString", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doSymbol", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doOther", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         JSToPropertyKeyNodeGen.OtherData s2_ = this.other_cache;
         if (s2_ != null) {
            cached.add(Arrays.asList(s2_.toPrimitiveNode_, s2_.toStringNode_, s2_.isSymbol_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      return Introspection.Provider.create(data);
   }

   public static JSToPropertyKeyNode create() {
      return new JSToPropertyKeyNodeGen();
   }

   @GeneratedBy(JSToPropertyKeyNode.JSToPropertyKeyWrapperNode.class)
   public static final class JSToPropertyKeyWrapperNodeGen extends JSToPropertyKeyNode.JSToPropertyKeyWrapperNode implements Introspection.Provider {
      private JSToPropertyKeyWrapperNodeGen(JavaScriptNode operand) {
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

      public static JSToPropertyKeyNode.JSToPropertyKeyWrapperNode create(JavaScriptNode operand) {
         return new JSToPropertyKeyNodeGen.JSToPropertyKeyWrapperNodeGen(operand);
      }
   }

   @GeneratedBy(JSToPropertyKeyNode.class)
   private static final class OtherData extends Node {
      @Node.Child
      JSToPrimitiveNode toPrimitiveNode_;
      @Node.Child
      JSToStringNode toStringNode_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile isSymbol_;

      OtherData() {
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
