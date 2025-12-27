package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.SlowPathException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSStringToNumberNode.class)
public final class JSStringToNumberNodeGen extends JSStringToNumberNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private ConditionProfile infinity_endsWithInfinity_;
   @Node.Child
   private TruffleString.RegionEqualByteIndexNode infinity_regionEqualsNode_;

   private JSStringToNumberNodeGen() {
   }

   @Override
   protected double executeNoTrim(TruffleString arg0Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0 && JSGuards.stringLength(arg0Value) == 0) {
            return this.doLengthIsZero(arg0Value);
         }

         if ((state_0 & 2) != 0 && this.startsWithI(arg0Value)) {
            return this.doInfinity(arg0Value, this.infinity_endsWithInfinity_, this.infinity_regionEqualsNode_);
         }

         if ((state_0 & 4) != 0
            && JSGuards.stringLength(arg0Value) > 0
            && !this.startsWithI(arg0Value)
            && !this.startsWithValidDouble(arg0Value)
            && !this.isHex(arg0Value)
            && !this.isOctal(arg0Value)
            && !this.isBinary(arg0Value)) {
            return this.doNaN(arg0Value);
         }

         if ((state_0 & 8) != 0 && this.isHex(arg0Value) && JSGuards.stringLength(arg0Value) <= 15) {
            return this.doHexSafe(arg0Value);
         }

         if ((state_0 & 16) != 0 && this.isHex(arg0Value) && JSGuards.stringLength(arg0Value) > 15) {
            return this.doHex(arg0Value);
         }

         if ((state_0 & 32) != 0 && this.isOctal(arg0Value) && JSGuards.stringLength(arg0Value) <= 19) {
            return this.doOctalSafe(arg0Value);
         }

         if ((state_0 & 64) != 0 && this.isOctal(arg0Value) && JSGuards.stringLength(arg0Value) > 19) {
            return this.doOctal(arg0Value);
         }

         if ((state_0 & 128) != 0 && this.isBinary(arg0Value) && JSGuards.stringLength(arg0Value) <= 55) {
            return this.doBinarySafe(arg0Value);
         }

         if ((state_0 & 256) != 0 && this.isBinary(arg0Value) && JSGuards.stringLength(arg0Value) > 55) {
            return this.doBinary(arg0Value);
         }

         if ((state_0 & 512) != 0 && JSGuards.stringLength(arg0Value) > 0 && JSGuards.stringLength(arg0Value) <= 9 && this.allDigits(arg0Value, 9)) {
            return this.doSmallPosInt(arg0Value);
         }

         if ((state_0 & 1024) != 0 && JSGuards.stringLength(arg0Value) > 0 && JSGuards.stringLength(arg0Value) <= 17 && this.startsWithValidInt(arg0Value)) {
            try {
               return this.doInteger(arg0Value);
            } catch (SlowPathException var9) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Lock lock = this.getLock();
               lock.lock();

               try {
                  this.exclude_ |= 2;
                  this.state_0_ &= -1025;
               } finally {
                  lock.unlock();
               }

               return this.executeAndSpecialize(arg0Value);
            }
         }

         if ((state_0 & 2048) != 0 && JSGuards.stringLength(arg0Value) > 0 && this.startsWithValidDouble(arg0Value)) {
            return this.doDouble(arg0Value);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value);
   }

   private double executeAndSpecialize(TruffleString arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      double var7;
      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (JSGuards.stringLength(arg0Value) == 0) {
            int var32;
            this.state_0_ = var32 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return this.doLengthIsZero(arg0Value);
         }

         if (this.startsWithI(arg0Value)) {
            this.infinity_endsWithInfinity_ = ConditionProfile.create();
            this.infinity_regionEqualsNode_ = super.insert(TruffleString.RegionEqualByteIndexNode.create());
            int var31;
            this.state_0_ = var31 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doInfinity(arg0Value, this.infinity_endsWithInfinity_, this.infinity_regionEqualsNode_);
         }

         if (JSGuards.stringLength(arg0Value) > 0
            && !this.startsWithI(arg0Value)
            && !this.startsWithValidDouble(arg0Value)
            && !this.isHex(arg0Value)
            && !this.isOctal(arg0Value)
            && !this.isBinary(arg0Value)) {
            int var30;
            this.state_0_ = var30 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.doNaN(arg0Value);
         }

         if (this.isHex(arg0Value) && JSGuards.stringLength(arg0Value) <= 15) {
            int var29;
            this.state_0_ = var29 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return this.doHexSafe(arg0Value);
         }

         if (this.isHex(arg0Value) && JSGuards.stringLength(arg0Value) > 15) {
            int var28;
            this.state_0_ = var28 = state_0 | 16;
            lock.unlock();
            hasLock = false;
            return this.doHex(arg0Value);
         }

         if (this.isOctal(arg0Value) && JSGuards.stringLength(arg0Value) <= 19) {
            int var27;
            this.state_0_ = var27 = state_0 | 32;
            lock.unlock();
            hasLock = false;
            return this.doOctalSafe(arg0Value);
         }

         if (this.isOctal(arg0Value) && JSGuards.stringLength(arg0Value) > 19) {
            int var26;
            this.state_0_ = var26 = state_0 | 64;
            lock.unlock();
            hasLock = false;
            return this.doOctal(arg0Value);
         }

         if (this.isBinary(arg0Value) && JSGuards.stringLength(arg0Value) <= 55) {
            int var25;
            this.state_0_ = var25 = state_0 | 128;
            lock.unlock();
            hasLock = false;
            return this.doBinarySafe(arg0Value);
         }

         if (this.isBinary(arg0Value) && JSGuards.stringLength(arg0Value) > 55) {
            int var24;
            this.state_0_ = var24 = state_0 | 256;
            lock.unlock();
            hasLock = false;
            return this.doBinary(arg0Value);
         }

         if ((exclude & 1) == 0 && JSGuards.stringLength(arg0Value) > 0 && JSGuards.stringLength(arg0Value) <= 9 && this.allDigits(arg0Value, 9)) {
            int var23;
            this.state_0_ = var23 = state_0 | 512;
            lock.unlock();
            hasLock = false;
            return this.doSmallPosInt(arg0Value);
         }

         if ((exclude & 2) != 0 || JSGuards.stringLength(arg0Value) <= 0 || JSGuards.stringLength(arg0Value) > 17 || !this.startsWithValidInt(arg0Value)) {
            if (JSGuards.stringLength(arg0Value) <= 0 || !this.startsWithValidDouble(arg0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }

            int var34;
            this.exclude_ = var34 = exclude | 3;
            state_0 &= -1537;
            int var22;
            this.state_0_ = var22 = state_0 | 2048;
            lock.unlock();
            hasLock = false;
            return this.doDouble(arg0Value);
         }

         int var33;
         this.exclude_ = var33 = exclude | 1;
         state_0 &= -513;
         int var20;
         this.state_0_ = var20 = state_0 | 1024;

         try {
            lock.unlock();
            hasLock = false;
            return this.doInteger(arg0Value);
         } catch (SlowPathException var17) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            lock.lock();

            try {
               this.exclude_ |= 2;
               this.state_0_ &= -1025;
            } finally {
               lock.unlock();
            }

            var7 = this.executeAndSpecialize(arg0Value);
         }
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
      if (state_0 == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[13];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doLengthIsZero", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doInfinity", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.infinity_endsWithInfinity_, this.infinity_regionEqualsNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doNaN", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doHexSafe", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doHex", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doOctalSafe", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doOctal", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"doBinarySafe", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doBinary", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doSmallPosInt", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      s = new Object[]{"doInteger", null, null};
      if ((state_0 & 1024) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[11] = s;
      s = new Object[]{"doDouble", null, null};
      if ((state_0 & 2048) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[12] = s;
      return Introspection.Provider.create(data);
   }

   public static JSStringToNumberNode create() {
      return new JSStringToNumberNodeGen();
   }
}
