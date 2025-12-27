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

@GeneratedBy(InputIndexOfStringNode.class)
public final class InputIndexOfStringNodeGen extends InputIndexOfStringNode {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private TruffleString.ByteIndexOfStringNode tString_indexOfStringNode_;
   @Node.Child
   private TruffleString.ByteIndexOfStringNode tStringMask_indexOfStringNode_;
   @Node.Child
   private InputLengthNode truffleObjBytes_lengthNode_;
   @Node.Child
   private InputRegionMatchesNode truffleObjBytes_regionMatchesNode_;
   @Node.Child
   private InputLengthNode truffleObjString_lengthNode_;
   @Node.Child
   private InputRegionMatchesNode truffleObjString_regionMatchesNode_;

   private InputIndexOfStringNodeGen() {
   }

   @Override
   public int execute(Object arg0Value, int arg1Value, int arg2Value, Object arg3Value, Object arg4Value, Encodings.Encoding arg5Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 3) != 0 && arg0Value instanceof byte[]) {
            byte[] arg0Value_ = (byte[])arg0Value;
            if (arg3Value instanceof byte[]) {
               byte[] arg3Value_ = (byte[])arg3Value;
               if ((state_0 & 1) != 0 && arg4Value == null) {
                  return this.doBytes(arg0Value_, arg1Value, arg2Value, arg3Value_, arg4Value, arg5Value);
               }

               if ((state_0 & 2) != 0 && arg4Value instanceof byte[]) {
                  byte[] arg4Value_ = (byte[])arg4Value;
                  if (arg4Value_ != null) {
                     return this.doBytesMask(arg0Value_, arg1Value, arg2Value, arg3Value_, arg4Value_, arg5Value);
                  }
               }
            }
         }

         if ((state_0 & 12) != 0 && arg0Value instanceof String) {
            String arg0Value_ = (String)arg0Value;
            if (arg3Value instanceof String) {
               String arg3Value_x = (String)arg3Value;
               if ((state_0 & 4) != 0 && arg4Value == null) {
                  return this.doString(arg0Value_, arg1Value, arg2Value, arg3Value_x, arg4Value, arg5Value);
               }

               if ((state_0 & 8) != 0 && arg4Value instanceof String) {
                  String arg4Value_ = (String)arg4Value;
                  if (arg4Value_ != null) {
                     return this.doStringMask(arg0Value_, arg1Value, arg2Value, arg3Value_x, arg4Value_, arg5Value);
                  }
               }
            }
         }

         if ((state_0 & 48) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            if (arg3Value instanceof TruffleString) {
               TruffleString arg3Value_xx = (TruffleString)arg3Value;
               if ((state_0 & 16) != 0 && arg4Value == null) {
                  return this.doTString(arg0Value_, arg1Value, arg2Value, arg3Value_xx, arg4Value, arg5Value, this.tString_indexOfStringNode_);
               }

               if ((state_0 & 32) != 0 && arg4Value instanceof TruffleString.WithMask) {
                  TruffleString.WithMask arg4Value_ = (TruffleString.WithMask)arg4Value;
                  if (arg4Value_ != null) {
                     return this.doTStringMask(arg0Value_, arg1Value, arg2Value, arg3Value_xx, arg4Value_, arg5Value, this.tStringMask_indexOfStringNode_);
                  }
               }
            }
         }

         if ((state_0 & 192) != 0) {
            if ((state_0 & 64) != 0 && arg3Value instanceof byte[]) {
               byte[] arg3Value_xxx = (byte[])arg3Value;
               if (TRegexGuards.neitherByteArrayNorString(arg0Value)) {
                  return this.doTruffleObjBytes(
                     arg0Value,
                     arg1Value,
                     arg2Value,
                     arg3Value_xxx,
                     arg4Value,
                     arg5Value,
                     this.truffleObjBytes_lengthNode_,
                     this.truffleObjBytes_regionMatchesNode_
                  );
               }
            }

            if ((state_0 & 128) != 0 && arg3Value instanceof String) {
               String arg3Value_xxx = (String)arg3Value;
               if (TRegexGuards.neitherByteArrayNorString(arg0Value)) {
                  return this.doTruffleObjString(
                     arg0Value,
                     arg1Value,
                     arg2Value,
                     arg3Value_xxx,
                     arg4Value,
                     arg5Value,
                     this.truffleObjString_lengthNode_,
                     this.truffleObjString_regionMatchesNode_
                  );
               }
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
   }

   private int executeAndSpecialize(Object arg0Value, int arg1Value, int arg2Value, Object arg3Value, Object arg4Value, Encodings.Encoding arg5Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         if (arg0Value instanceof byte[]) {
            byte[] arg0Value_ = (byte[])arg0Value;
            if (arg3Value instanceof byte[]) {
               byte[] arg3Value_ = (byte[])arg3Value;
               if (arg4Value == null) {
                  int var24;
                  this.state_0_ = var24 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.doBytes(arg0Value_, arg1Value, arg2Value, arg3Value_, arg4Value, arg5Value);
               }

               if (arg4Value instanceof byte[]) {
                  byte[] arg4Value_ = (byte[])arg4Value;
                  if (arg4Value_ != null) {
                     int var23;
                     this.state_0_ = var23 = state_0 | 2;
                     lock.unlock();
                     hasLock = false;
                     return this.doBytesMask(arg0Value_, arg1Value, arg2Value, arg3Value_, arg4Value_, arg5Value);
                  }
               }
            }
         }

         if (arg0Value instanceof String) {
            String arg0Value_ = (String)arg0Value;
            if (arg3Value instanceof String) {
               String arg3Value_x = (String)arg3Value;
               if (arg4Value == null) {
                  int var22;
                  this.state_0_ = var22 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return this.doString(arg0Value_, arg1Value, arg2Value, arg3Value_x, arg4Value, arg5Value);
               }

               if (arg4Value instanceof String) {
                  String arg4Value_ = (String)arg4Value;
                  if (arg4Value_ != null) {
                     int var21;
                     this.state_0_ = var21 = state_0 | 8;
                     lock.unlock();
                     hasLock = false;
                     return this.doStringMask(arg0Value_, arg1Value, arg2Value, arg3Value_x, arg4Value_, arg5Value);
                  }
               }
            }
         }

         if (arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            if (arg3Value instanceof TruffleString) {
               TruffleString arg3Value_xx = (TruffleString)arg3Value;
               if (arg4Value == null) {
                  this.tString_indexOfStringNode_ = super.insert(TruffleString.ByteIndexOfStringNode.create());
                  int var20;
                  this.state_0_ = var20 = state_0 | 16;
                  lock.unlock();
                  hasLock = false;
                  return this.doTString(arg0Value_, arg1Value, arg2Value, arg3Value_xx, arg4Value, arg5Value, this.tString_indexOfStringNode_);
               }

               if (arg4Value instanceof TruffleString.WithMask) {
                  TruffleString.WithMask arg4Value_ = (TruffleString.WithMask)arg4Value;
                  if (arg4Value_ != null) {
                     this.tStringMask_indexOfStringNode_ = super.insert(TruffleString.ByteIndexOfStringNode.create());
                     int var19;
                     this.state_0_ = var19 = state_0 | 32;
                     lock.unlock();
                     hasLock = false;
                     return this.doTStringMask(arg0Value_, arg1Value, arg2Value, arg3Value_xx, arg4Value_, arg5Value, this.tStringMask_indexOfStringNode_);
                  }
               }
            }
         }

         if (arg3Value instanceof byte[]) {
            byte[] arg3Value_xxx = (byte[])arg3Value;
            if (TRegexGuards.neitherByteArrayNorString(arg0Value)) {
               this.truffleObjBytes_lengthNode_ = super.insert(InputLengthNode.create());
               this.truffleObjBytes_regionMatchesNode_ = super.insert(InputRegionMatchesNode.create());
               int var18;
               this.state_0_ = var18 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               return this.doTruffleObjBytes(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value_xxx,
                  arg4Value,
                  arg5Value,
                  this.truffleObjBytes_lengthNode_,
                  this.truffleObjBytes_regionMatchesNode_
               );
            }
         }

         if (arg3Value instanceof String) {
            String arg3Value_xxx = (String)arg3Value;
            if (TRegexGuards.neitherByteArrayNorString(arg0Value)) {
               this.truffleObjString_lengthNode_ = super.insert(InputLengthNode.create());
               this.truffleObjString_regionMatchesNode_ = super.insert(InputRegionMatchesNode.create());
               int var17;
               this.state_0_ = var17 = state_0 | 128;
               lock.unlock();
               hasLock = false;
               return this.doTruffleObjString(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value_xxx,
                  arg4Value,
                  arg5Value,
                  this.truffleObjString_lengthNode_,
                  this.truffleObjString_regionMatchesNode_
               );
            }
         }

         throw new UnsupportedSpecializationException(
            this, new Node[]{null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value
         );
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

   public static InputIndexOfStringNode create() {
      return new InputIndexOfStringNodeGen();
   }
}
