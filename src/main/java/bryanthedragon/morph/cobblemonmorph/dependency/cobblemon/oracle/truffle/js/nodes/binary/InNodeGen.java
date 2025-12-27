package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.access.JSProxyHasPropertyNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(InNode.class)
public final class InNodeGen extends InNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private JSProxyHasPropertyNode proxy_proxyHasPropertyNode_;
   @Node.Child
   private IsObjectNode foreign_isObjectNode_;
   @CompilerDirectives.CompilationFinal
   private BranchProfile foreign_errorBranch_;

   private InNodeGen(JSContext context, JavaScriptNode left, JavaScriptNode right) {
      super(context, left, right);
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      Object leftNodeValue_ = super.leftNode.execute(frameValue);
      Object rightNodeValue_ = super.rightNode.execute(frameValue);
      if (state_0 != 0) {
         if ((state_0 & 3) != 0 && rightNodeValue_ instanceof JSDynamicObject) {
            JSDynamicObject rightNodeValue__ = (JSDynamicObject)rightNodeValue_;
            if ((state_0 & 1) != 0 && JSGuards.isJSObject(rightNodeValue__) && !JSGuards.isJSProxy(rightNodeValue__)) {
               return this.doObject(leftNodeValue_, rightNodeValue__);
            }

            if ((state_0 & 2) != 0 && JSGuards.isJSProxy(rightNodeValue__)) {
               return this.doProxy(leftNodeValue_, rightNodeValue__, this.proxy_proxyHasPropertyNode_);
            }
         }

         if ((state_0 & 12) != 0) {
            if ((state_0 & 4) != 0 && JSGuards.isForeignObject(rightNodeValue_)) {
               return this.doForeign(leftNodeValue_, rightNodeValue_, this.foreign_isObjectNode_, this.foreign_errorBranch_);
            }

            if ((state_0 & 8) != 0 && JSGuards.isNullOrUndefined(rightNodeValue_)) {
               return InNode.doNullOrUndefined(leftNodeValue_, rightNodeValue_);
            }
         }

         if ((state_0 & 16) != 0 && rightNodeValue_ instanceof Symbol) {
            Symbol rightNodeValue__x = (Symbol)rightNodeValue_;
            return InNode.doSymbol(leftNodeValue_, rightNodeValue__x);
         }

         if ((state_0 & 32) != 0 && rightNodeValue_ instanceof TruffleString) {
            TruffleString rightNodeValue__x = (TruffleString)rightNodeValue_;
            return InNode.doTString(leftNodeValue_, rightNodeValue__x);
         }

         if ((state_0 & 64) != 0 && rightNodeValue_ instanceof SafeInteger) {
            SafeInteger rightNodeValue__x = (SafeInteger)rightNodeValue_;
            return InNode.doSafeInteger(leftNodeValue_, rightNodeValue__x);
         }

         if ((state_0 & 128) != 0 && rightNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__x = (BigInt)rightNodeValue_;
            return InNode.doBigInt(leftNodeValue_, rightNodeValue__x);
         }

         if ((state_0 & 256) != 0 && !JSGuards.isTruffleObject(rightNodeValue_)) {
            return InNode.doNotTruffleObject(leftNodeValue_, rightNodeValue_);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
   }

   @Override
   public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 504) != 0) {
         return JSTypesGen.expectBoolean(this.execute(frameValue));
      } else {
         Object leftNodeValue_ = super.leftNode.execute(frameValue);
         Object rightNodeValue_ = super.rightNode.execute(frameValue);
         if ((state_0 & 7) != 0) {
            if ((state_0 & 3) != 0 && rightNodeValue_ instanceof JSDynamicObject) {
               JSDynamicObject rightNodeValue__ = (JSDynamicObject)rightNodeValue_;
               if ((state_0 & 1) != 0 && JSGuards.isJSObject(rightNodeValue__) && !JSGuards.isJSProxy(rightNodeValue__)) {
                  return this.doObject(leftNodeValue_, rightNodeValue__);
               }

               if ((state_0 & 2) != 0 && JSGuards.isJSProxy(rightNodeValue__)) {
                  return this.doProxy(leftNodeValue_, rightNodeValue__, this.proxy_proxyHasPropertyNode_);
               }
            }

            if ((state_0 & 4) != 0 && JSGuards.isForeignObject(rightNodeValue_)) {
               return this.doForeign(leftNodeValue_, rightNodeValue_, this.foreign_isObjectNode_, this.foreign_errorBranch_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectBoolean(this.executeAndSpecialize(leftNodeValue_, rightNodeValue_));
      }
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      int state_0 = this.state_0_;

      try {
         if ((state_0 & 504) == 0 && state_0 != 0) {
            this.executeBoolean(frameValue);
         } else {
            this.execute(frameValue);
         }
      } catch (UnexpectedResultException var4) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
      }
   }

   private Object executeAndSpecialize(Object leftNodeValue, Object rightNodeValue) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         if (rightNodeValue instanceof JSDynamicObject) {
            JSDynamicObject rightNodeValue_ = (JSDynamicObject)rightNodeValue;
            if (JSGuards.isJSObject(rightNodeValue_) && !JSGuards.isJSProxy(rightNodeValue_)) {
               int var19;
               this.state_0_ = var19 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.doObject(leftNodeValue, rightNodeValue_);
            }

            if (JSGuards.isJSProxy(rightNodeValue_)) {
               this.proxy_proxyHasPropertyNode_ = super.insert(JSProxyHasPropertyNode.create(this.context));
               int var18;
               this.state_0_ = var18 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doProxy(leftNodeValue, rightNodeValue_, this.proxy_proxyHasPropertyNode_);
            }
         }

         if (JSGuards.isForeignObject(rightNodeValue)) {
            this.foreign_isObjectNode_ = super.insert(IsObjectNode.create());
            this.foreign_errorBranch_ = BranchProfile.create();
            int var17;
            this.state_0_ = var17 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.doForeign(leftNodeValue, rightNodeValue, this.foreign_isObjectNode_, this.foreign_errorBranch_);
         } else if (JSGuards.isNullOrUndefined(rightNodeValue)) {
            int var16;
            this.state_0_ = var16 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return InNode.doNullOrUndefined(leftNodeValue, rightNodeValue);
         } else if (rightNodeValue instanceof Symbol) {
            Symbol rightNodeValue_x = (Symbol)rightNodeValue;
            int var15;
            this.state_0_ = var15 = state_0 | 16;
            lock.unlock();
            hasLock = false;
            return InNode.doSymbol(leftNodeValue, rightNodeValue_x);
         } else if (rightNodeValue instanceof TruffleString) {
            TruffleString rightNodeValue_x = (TruffleString)rightNodeValue;
            int var14;
            this.state_0_ = var14 = state_0 | 32;
            lock.unlock();
            hasLock = false;
            return InNode.doTString(leftNodeValue, rightNodeValue_x);
         } else if (rightNodeValue instanceof SafeInteger) {
            SafeInteger rightNodeValue_x = (SafeInteger)rightNodeValue;
            int var13;
            this.state_0_ = var13 = state_0 | 64;
            lock.unlock();
            hasLock = false;
            return InNode.doSafeInteger(leftNodeValue, rightNodeValue_x);
         } else if (rightNodeValue instanceof BigInt) {
            BigInt rightNodeValue_x = (BigInt)rightNodeValue;
            int var11;
            this.state_0_ = var11 = state_0 | 128;
            lock.unlock();
            hasLock = false;
            return InNode.doBigInt(leftNodeValue, rightNodeValue_x);
         } else if (JSGuards.isTruffleObject(rightNodeValue)) {
            throw new UnsupportedSpecializationException(this, new Node[]{super.leftNode, super.rightNode}, leftNodeValue, rightNodeValue);
         } else {
            int var12;
            this.state_0_ = var12 = state_0 | 256;
            lock.unlock();
            hasLock = false;
            return InNode.doNotTruffleObject(leftNodeValue, rightNodeValue);
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
      Object[] data = new Object[10];
      data[0] = 0;
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"doObject", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doProxy", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.proxy_proxyHasPropertyNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doForeign", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.foreign_isObjectNode_, this.foreign_errorBranch_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doNullOrUndefined", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doSymbol", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doTString", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doSafeInteger", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doNotTruffleObject", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      return Introspection.Provider.create(data);
   }

   public static InNode create(JSContext context, JavaScriptNode left, JavaScriptNode right) {
      return new InNodeGen(context, left, right);
   }
}
