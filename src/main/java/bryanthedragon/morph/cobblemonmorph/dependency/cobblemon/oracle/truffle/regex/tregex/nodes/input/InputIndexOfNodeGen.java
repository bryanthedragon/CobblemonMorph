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

@GeneratedBy(InputIndexOfNode.class)
public final class InputIndexOfNodeGen extends InputIndexOfNode {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private TruffleString.ByteIndexOfAnyByteNode tStringBytes_indexOfRawValueNode_;
   @Node.Child
   private TruffleString.CharIndexOfAnyCharUTF16Node tStringChars_indexOfRawValueNode_;
   @Node.Child
   private TruffleString.IntIndexOfAnyIntUTF32Node tStringInts_indexOfRawValueNode_;
   @Node.Child
   private InputReadNode truffleObjBytes_charAtNode_;
   @Node.Child
   private InputReadNode truffleObjChars_charAtNode_;

   private InputIndexOfNodeGen() {
   }

   @Override
   public int execute(Object arg0Value, int arg1Value, int arg2Value, Object arg3Value, Encodings.Encoding arg4Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0 && arg0Value instanceof byte[]) {
            byte[] arg0Value_ = (byte[])arg0Value;
            if (arg3Value instanceof byte[]) {
               byte[] arg3Value_ = (byte[])arg3Value;
               return this.doBytes(arg0Value_, arg1Value, arg2Value, arg3Value_, arg4Value);
            }
         }

         if ((state_0 & 2) != 0 && arg0Value instanceof String) {
            String arg0Value_ = (String)arg0Value;
            if (arg3Value instanceof char[]) {
               char[] arg3Value_ = (char[])arg3Value;
               return this.doChars(arg0Value_, arg1Value, arg2Value, arg3Value_, arg4Value);
            }
         }

         if ((state_0 & 28) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            if ((state_0 & 4) != 0 && arg3Value instanceof byte[]) {
               byte[] arg3Value_ = (byte[])arg3Value;
               return this.doTStringBytes(arg0Value_, arg1Value, arg2Value, arg3Value_, arg4Value, this.tStringBytes_indexOfRawValueNode_);
            }

            if ((state_0 & 8) != 0 && arg3Value instanceof char[]) {
               char[] arg3Value_ = (char[])arg3Value;
               return this.doTStringChars(arg0Value_, arg1Value, arg2Value, arg3Value_, arg4Value, this.tStringChars_indexOfRawValueNode_);
            }

            if ((state_0 & 16) != 0 && arg3Value instanceof int[]) {
               int[] arg3Value_ = (int[])arg3Value;
               return this.doTStringInts(arg0Value_, arg1Value, arg2Value, arg3Value_, arg4Value, this.tStringInts_indexOfRawValueNode_);
            }
         }

         if ((state_0 & 96) != 0) {
            if ((state_0 & 32) != 0 && arg3Value instanceof byte[]) {
               byte[] arg3Value_ = (byte[])arg3Value;
               if (TRegexGuards.neitherByteArrayNorString(arg0Value)) {
                  return this.doTruffleObjBytes(arg0Value, arg1Value, arg2Value, arg3Value_, arg4Value, this.truffleObjBytes_charAtNode_);
               }
            }

            if ((state_0 & 64) != 0 && arg3Value instanceof char[]) {
               char[] arg3Value_ = (char[])arg3Value;
               if (TRegexGuards.neitherByteArrayNorString(arg0Value)) {
                  return this.doTruffleObjChars(arg0Value, arg1Value, arg2Value, arg3Value_, arg4Value, this.truffleObjChars_charAtNode_);
               }
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
   }

   private int executeAndSpecialize(Object arg0Value, int arg1Value, int arg2Value, Object arg3Value, Encodings.Encoding arg4Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         if (arg0Value instanceof byte[]) {
            byte[] arg0Value_ = (byte[])arg0Value;
            if (arg3Value instanceof byte[]) {
               byte[] arg3Value_ = (byte[])arg3Value;
               int var21;
               this.state_0_ = var21 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.doBytes(arg0Value_, arg1Value, arg2Value, arg3Value_, arg4Value);
            }
         }

         if (arg0Value instanceof String) {
            String arg0Value_ = (String)arg0Value;
            if (arg3Value instanceof char[]) {
               char[] arg3Value_ = (char[])arg3Value;
               int var20;
               this.state_0_ = var20 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doChars(arg0Value_, arg1Value, arg2Value, arg3Value_, arg4Value);
            }
         }

         if (arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            if (arg3Value instanceof byte[]) {
               byte[] arg3Value_ = (byte[])arg3Value;
               this.tStringBytes_indexOfRawValueNode_ = super.insert(TruffleString.ByteIndexOfAnyByteNode.create());
               int var19;
               this.state_0_ = var19 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.doTStringBytes(arg0Value_, arg1Value, arg2Value, arg3Value_, arg4Value, this.tStringBytes_indexOfRawValueNode_);
            }

            if (arg3Value instanceof char[]) {
               char[] arg3Value_ = (char[])arg3Value;
               this.tStringChars_indexOfRawValueNode_ = super.insert(TruffleString.CharIndexOfAnyCharUTF16Node.create());
               int var18;
               this.state_0_ = var18 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.doTStringChars(arg0Value_, arg1Value, arg2Value, arg3Value_, arg4Value, this.tStringChars_indexOfRawValueNode_);
            }

            if (arg3Value instanceof int[]) {
               int[] arg3Value_ = (int[])arg3Value;
               this.tStringInts_indexOfRawValueNode_ = super.insert(TruffleString.IntIndexOfAnyIntUTF32Node.create());
               int var17;
               this.state_0_ = var17 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.doTStringInts(arg0Value_, arg1Value, arg2Value, arg3Value_, arg4Value, this.tStringInts_indexOfRawValueNode_);
            }
         }

         if (arg3Value instanceof byte[]) {
            byte[] arg3Value_ = (byte[])arg3Value;
            if (TRegexGuards.neitherByteArrayNorString(arg0Value)) {
               this.truffleObjBytes_charAtNode_ = super.insert(InputReadNode.create());
               int var16;
               this.state_0_ = var16 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return this.doTruffleObjBytes(arg0Value, arg1Value, arg2Value, arg3Value_, arg4Value, this.truffleObjBytes_charAtNode_);
            }
         }

         if (arg3Value instanceof char[]) {
            char[] arg3Value_ = (char[])arg3Value;
            if (TRegexGuards.neitherByteArrayNorString(arg0Value)) {
               this.truffleObjChars_charAtNode_ = super.insert(InputReadNode.create());
               int var15;
               this.state_0_ = var15 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               return this.doTruffleObjChars(arg0Value, arg1Value, arg2Value, arg3Value_, arg4Value, this.truffleObjChars_charAtNode_);
            }
         }

         throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
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

   public static InputIndexOfNode create() {
      return new InputIndexOfNodeGen();
   }
}
