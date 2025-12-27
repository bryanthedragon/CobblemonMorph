package com.oracle.truffle.regex.tregex.nodes.input;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.tregex.string.Encodings;
import com.oracle.truffle.regex.util.TRegexGuards;
import java.util.concurrent.locks.Lock;

@GeneratedBy(InputEndsWithNode.class)
public final class InputEndsWithNodeGen extends InputEndsWithNode {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private TruffleString.RegionEqualByteIndexNode tString_regionEqualsNode_;
   @Node.Child
   private TruffleString.RegionEqualByteIndexNode tStringMask_regionEqualsNode_;
   @Node.Child
   private InputLengthNode truffleObjBytes_lengthNode_;
   @Node.Child
   private InputReadNode truffleObjBytes_charAtNode_;
   @Node.Child
   private InputLengthNode truffleObjBytesMask_lengthNode_;
   @Node.Child
   private InputReadNode truffleObjBytesMask_charAtNode_;
   @Node.Child
   private InputLengthNode truffleObjString_lengthNode_;
   @Node.Child
   private InputReadNode truffleObjString_charAtNode_;
   @Node.Child
   private InputLengthNode truffleObjStringMask_lengthNode_;
   @Node.Child
   private InputReadNode truffleObjStringMask_charAtNode_;

   private InputEndsWithNodeGen() {
   }

   @Override
   public boolean execute(Object arg0Value, Object arg1Value, Object arg2Value, Encodings.Encoding arg3Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 3) != 0 && arg0Value instanceof byte[]) {
            byte[] arg0Value_ = (byte[])arg0Value;
            if (arg1Value instanceof byte[]) {
               byte[] arg1Value_ = (byte[])arg1Value;
               if ((state_0 & 1) != 0 && arg2Value == null) {
                  return this.doBytes(arg0Value_, arg1Value_, arg2Value, arg3Value);
               }

               if ((state_0 & 2) != 0 && arg2Value instanceof byte[]) {
                  byte[] arg2Value_ = (byte[])arg2Value;
                  if (arg2Value_ != null) {
                     return this.doBytesMask(arg0Value_, arg1Value_, arg2Value_, arg3Value);
                  }
               }
            }
         }

         if ((state_0 & 12) != 0 && arg0Value instanceof String) {
            String arg0Value_ = (String)arg0Value;
            if (arg1Value instanceof String) {
               String arg1Value_x = (String)arg1Value;
               if ((state_0 & 4) != 0 && arg2Value == null) {
                  return this.doString(arg0Value_, arg1Value_x, arg2Value, arg3Value);
               }

               if ((state_0 & 8) != 0 && arg2Value instanceof String) {
                  String arg2Value_ = (String)arg2Value;
                  if (arg2Value_ != null) {
                     return this.doStringMask(arg0Value_, arg1Value_x, arg2Value_, arg3Value);
                  }
               }
            }
         }

         if ((state_0 & 48) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            if (arg1Value instanceof TruffleString) {
               TruffleString arg1Value_xx = (TruffleString)arg1Value;
               if ((state_0 & 16) != 0 && arg2Value == null) {
                  return this.doTString(arg0Value_, arg1Value_xx, arg2Value, arg3Value, this.tString_regionEqualsNode_);
               }

               if ((state_0 & 32) != 0 && arg2Value instanceof TruffleString.WithMask) {
                  TruffleString.WithMask arg2Value_ = (TruffleString.WithMask)arg2Value;
                  if (arg2Value_ != null) {
                     return this.doTStringMask(arg0Value_, arg1Value_xx, arg2Value_, arg3Value, this.tStringMask_regionEqualsNode_);
                  }
               }
            }
         }

         if ((state_0 & 960) != 0) {
            if ((state_0 & 192) != 0 && arg1Value instanceof byte[]) {
               byte[] arg1Value_xxx = (byte[])arg1Value;
               if ((state_0 & 64) != 0 && TRegexGuards.neitherByteArrayNorString(arg0Value) && arg2Value == null) {
                  return this.doTruffleObjBytes(
                     arg0Value, arg1Value_xxx, arg2Value, arg3Value, this.truffleObjBytes_lengthNode_, this.truffleObjBytes_charAtNode_
                  );
               }

               if ((state_0 & 128) != 0 && arg2Value instanceof byte[]) {
                  byte[] arg2Value_ = (byte[])arg2Value;
                  if (TRegexGuards.neitherByteArrayNorString(arg0Value) && arg2Value_ != null) {
                     return this.doTruffleObjBytesMask(
                        arg0Value, arg1Value_xxx, arg2Value_, arg3Value, this.truffleObjBytesMask_lengthNode_, this.truffleObjBytesMask_charAtNode_
                     );
                  }
               }
            }

            if ((state_0 & 768) != 0 && arg1Value instanceof String) {
               String arg1Value_xxxx = (String)arg1Value;
               if ((state_0 & 256) != 0 && TRegexGuards.neitherByteArrayNorString(arg0Value) && arg2Value == null) {
                  return this.doTruffleObjString(
                     arg0Value, arg1Value_xxxx, arg2Value, arg3Value, this.truffleObjString_lengthNode_, this.truffleObjString_charAtNode_
                  );
               }

               if ((state_0 & 512) != 0 && arg2Value instanceof String) {
                  String arg2Value_ = (String)arg2Value;
                  if (TRegexGuards.neitherByteArrayNorString(arg0Value) && arg2Value_ != null) {
                     return this.doTruffleObjStringMask(
                        arg0Value, arg1Value_xxxx, arg2Value_, arg3Value, this.truffleObjStringMask_lengthNode_, this.truffleObjStringMask_charAtNode_
                     );
                  }
               }
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
   }

   private boolean executeAndSpecialize(Object arg0Value, Object arg1Value, Object arg2Value, Encodings.Encoding arg3Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         if (arg0Value instanceof byte[]) {
            byte[] arg0Value_ = (byte[])arg0Value;
            if (arg1Value instanceof byte[]) {
               byte[] arg1Value_ = (byte[])arg1Value;
               if (arg2Value == null) {
                  int var24;
                  this.state_0_ = var24 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.doBytes(arg0Value_, arg1Value_, arg2Value, arg3Value);
               }

               if (arg2Value instanceof byte[]) {
                  byte[] arg2Value_ = (byte[])arg2Value;
                  if (arg2Value_ != null) {
                     int var23;
                     this.state_0_ = var23 = state_0 | 2;
                     lock.unlock();
                     hasLock = false;
                     return this.doBytesMask(arg0Value_, arg1Value_, arg2Value_, arg3Value);
                  }
               }
            }
         }

         if (arg0Value instanceof String) {
            String arg0Value_ = (String)arg0Value;
            if (arg1Value instanceof String) {
               String arg1Value_x = (String)arg1Value;
               if (arg2Value == null) {
                  int var22;
                  this.state_0_ = var22 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return this.doString(arg0Value_, arg1Value_x, arg2Value, arg3Value);
               }

               if (arg2Value instanceof String) {
                  String arg2Value_ = (String)arg2Value;
                  if (arg2Value_ != null) {
                     int var21;
                     this.state_0_ = var21 = state_0 | 8;
                     lock.unlock();
                     hasLock = false;
                     return this.doStringMask(arg0Value_, arg1Value_x, arg2Value_, arg3Value);
                  }
               }
            }
         }

         if (arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            if (arg1Value instanceof TruffleString) {
               TruffleString arg1Value_xx = (TruffleString)arg1Value;
               if (arg2Value == null) {
                  this.tString_regionEqualsNode_ = super.insert(TruffleString.RegionEqualByteIndexNode.create());
                  int var20;
                  this.state_0_ = var20 = state_0 | 16;
                  lock.unlock();
                  hasLock = false;
                  return this.doTString(arg0Value_, arg1Value_xx, arg2Value, arg3Value, this.tString_regionEqualsNode_);
               }

               if (arg2Value instanceof TruffleString.WithMask) {
                  TruffleString.WithMask arg2Value_ = (TruffleString.WithMask)arg2Value;
                  if (arg2Value_ != null) {
                     this.tStringMask_regionEqualsNode_ = super.insert(TruffleString.RegionEqualByteIndexNode.create());
                     int var19;
                     this.state_0_ = var19 = state_0 | 32;
                     lock.unlock();
                     hasLock = false;
                     return this.doTStringMask(arg0Value_, arg1Value_xx, arg2Value_, arg3Value, this.tStringMask_regionEqualsNode_);
                  }
               }
            }
         }

         if (arg1Value instanceof byte[]) {
            byte[] arg1Value_xxx = (byte[])arg1Value;
            if (TRegexGuards.neitherByteArrayNorString(arg0Value) && arg2Value == null) {
               this.truffleObjBytes_lengthNode_ = super.insert(InputLengthNode.create());
               this.truffleObjBytes_charAtNode_ = super.insert(InputReadNode.create());
               int var18;
               this.state_0_ = var18 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               return this.doTruffleObjBytes(arg0Value, arg1Value_xxx, arg2Value, arg3Value, this.truffleObjBytes_lengthNode_, this.truffleObjBytes_charAtNode_);
            }

            if (arg2Value instanceof byte[]) {
               byte[] arg2Value_ = (byte[])arg2Value;
               if (TRegexGuards.neitherByteArrayNorString(arg0Value) && arg2Value_ != null) {
                  this.truffleObjBytesMask_lengthNode_ = super.insert(InputLengthNode.create());
                  this.truffleObjBytesMask_charAtNode_ = super.insert(InputReadNode.create());
                  int var17;
                  this.state_0_ = var17 = state_0 | 128;
                  lock.unlock();
                  hasLock = false;
                  return this.doTruffleObjBytesMask(
                     arg0Value, arg1Value_xxx, arg2Value_, arg3Value, this.truffleObjBytesMask_lengthNode_, this.truffleObjBytesMask_charAtNode_
                  );
               }
            }
         }

         if (arg1Value instanceof String) {
            String arg1Value_xxxx = (String)arg1Value;
            if (TRegexGuards.neitherByteArrayNorString(arg0Value) && arg2Value == null) {
               this.truffleObjString_lengthNode_ = super.insert(InputLengthNode.create());
               this.truffleObjString_charAtNode_ = super.insert(InputReadNode.create());
               int var16;
               this.state_0_ = var16 = state_0 | 256;
               lock.unlock();
               hasLock = false;
               return this.doTruffleObjString(
                  arg0Value, arg1Value_xxxx, arg2Value, arg3Value, this.truffleObjString_lengthNode_, this.truffleObjString_charAtNode_
               );
            }

            if (arg2Value instanceof String) {
               String arg2Value_ = (String)arg2Value;
               if (TRegexGuards.neitherByteArrayNorString(arg0Value) && arg2Value_ != null) {
                  this.truffleObjStringMask_lengthNode_ = super.insert(InputLengthNode.create());
                  this.truffleObjStringMask_charAtNode_ = super.insert(InputReadNode.create());
                  int var15;
                  this.state_0_ = var15 = state_0 | 512;
                  lock.unlock();
                  hasLock = false;
                  return this.doTruffleObjStringMask(
                     arg0Value, arg1Value_xxxx, arg2Value_, arg3Value, this.truffleObjStringMask_lengthNode_, this.truffleObjStringMask_charAtNode_
                  );
               }
            }
         }

         throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value);
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

   public static InputEndsWithNode create() {
      return new InputEndsWithNodeGen();
   }
}
