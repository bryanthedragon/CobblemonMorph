package com.oracle.truffle.js.nodes.array;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.access.WritePropertyNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSSetLengthNode.class)
public final class JSSetLengthNodeGen extends JSSetLengthNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private ArrayLengthNode.ArrayLengthWriteNode setArrayLength_arrayLengthWriteNode_;
   @Node.Child
   private WritePropertyNode setIntLength_setLengthProperty_;
   @Node.Child
   private WritePropertyNode setLength_setLengthProperty_;

   private JSSetLengthNodeGen(JSContext context, boolean isStrict) {
      super(context, isStrict);
   }

   @Override
   public Object execute(Object arg0Value, Object arg1Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 7) != 0 && arg0Value instanceof JSDynamicObject) {
         JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
         if ((state_0 & 3) != 0 && arg1Value instanceof Integer) {
            int arg1Value_ = (Integer)arg1Value;
            if ((state_0 & 1) != 0 && JSSetLengthNode.isArray(arg0Value_)) {
               return JSSetLengthNode.setArrayLength(arg0Value_, arg1Value_, this.setArrayLength_arrayLengthWriteNode_);
            }

            if ((state_0 & 2) != 0) {
               return JSSetLengthNode.setIntLength(arg0Value_, arg1Value_, this.setIntLength_setLengthProperty_);
            }
         }

         if ((state_0 & 4) != 0) {
            return JSSetLengthNode.setLength(arg0Value_, arg1Value, this.setLength_setLengthProperty_);
         }
      }

      if ((state_0 & 8) != 0 && !JSGuards.isJSDynamicObject(arg0Value)) {
         return JSSetLengthNode.setLengthForeign(arg0Value, arg1Value);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }
   }

   private Object executeAndSpecialize(Object arg0Value, Object arg1Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            if (arg1Value instanceof Integer) {
               int arg1Value_ = (Integer)arg1Value;
               if (JSSetLengthNode.isArray(arg0Value_)) {
                  this.setArrayLength_arrayLengthWriteNode_ = super.insert(ArrayLengthNode.ArrayLengthWriteNode.create(this.isStrict));
                  int var16;
                  this.state_0_ = var16 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return JSSetLengthNode.setArrayLength(arg0Value_, arg1Value_, this.setArrayLength_arrayLengthWriteNode_);
               }

               if (exclude == 0) {
                  this.setIntLength_setLengthProperty_ = super.insert(this.createWritePropertyNode());
                  int var15;
                  this.state_0_ = var15 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return JSSetLengthNode.setIntLength(arg0Value_, arg1Value_, this.setIntLength_setLengthProperty_);
               }
            }

            this.setLength_setLengthProperty_ = super.insert(this.createWritePropertyNode());
            int var18;
            this.exclude_ = var18 = exclude | 1;
            state_0 &= -3;
            int var14;
            this.state_0_ = var14 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return JSSetLengthNode.setLength(arg0Value_, arg1Value, this.setLength_setLengthProperty_);
         } else if (JSGuards.isJSDynamicObject(arg0Value)) {
            throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
         } else {
            int var17;
            this.state_0_ = var17 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return JSSetLengthNode.setLengthForeign(arg0Value, arg1Value);
         }
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
      Object[] data = new Object[5];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"setArrayLength", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.setArrayLength_arrayLengthWriteNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"setIntLength", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.setIntLength_setLengthProperty_));
         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"setLength", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.setLength_setLengthProperty_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"setLengthForeign", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      return Introspection.Provider.create(data);
   }

   public static JSSetLengthNode create(JSContext context, boolean isStrict) {
      return new JSSetLengthNodeGen(context, isStrict);
   }
}
