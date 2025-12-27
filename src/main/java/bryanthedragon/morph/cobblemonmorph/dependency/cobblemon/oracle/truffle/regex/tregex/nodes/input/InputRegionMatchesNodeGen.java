package com.oracle.truffle.regex.tregex.nodes.input;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.tregex.string.Encodings;
import com.oracle.truffle.regex.util.TRegexGuards;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(InputRegionMatchesNode.class)
public final class InputRegionMatchesNodeGen extends InputRegionMatchesNode {
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
   @Node.Child
   private InputRegionMatchesNodeGen.TruffleObjTruffleObjData truffleObjTruffleObj_cache;

   private InputRegionMatchesNodeGen() {
   }

   @Override
   public boolean execute(Object arg0Value, int arg1Value, Object arg2Value, int arg3Value, int arg4Value, Object arg5Value, Encodings.Encoding arg6Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 3) != 0 && arg0Value instanceof byte[]) {
            byte[] arg0Value_ = (byte[])arg0Value;
            if (arg2Value instanceof byte[]) {
               byte[] arg2Value_ = (byte[])arg2Value;
               if ((state_0 & 1) != 0 && arg5Value == null) {
                  return this.doBytes(arg0Value_, arg1Value, arg2Value_, arg3Value, arg4Value, arg5Value, arg6Value);
               }

               if ((state_0 & 2) != 0 && arg5Value instanceof byte[]) {
                  byte[] arg5Value_ = (byte[])arg5Value;
                  if (arg5Value_ != null) {
                     return this.doBytesMask(arg0Value_, arg1Value, arg2Value_, arg3Value, arg4Value, arg5Value_, arg6Value);
                  }
               }
            }
         }

         if ((state_0 & 12) != 0 && arg0Value instanceof String) {
            String arg0Value_ = (String)arg0Value;
            if (arg2Value instanceof String) {
               String arg2Value_x = (String)arg2Value;
               if ((state_0 & 4) != 0 && arg5Value == null) {
                  return this.doString(arg0Value_, arg1Value, arg2Value_x, arg3Value, arg4Value, arg5Value, arg6Value);
               }

               if ((state_0 & 8) != 0 && arg5Value instanceof String) {
                  String arg5Value_ = (String)arg5Value;
                  if (arg5Value_ != null) {
                     return this.doJavaStringMask(arg0Value_, arg1Value, arg2Value_x, arg3Value, arg4Value, arg5Value_, arg6Value);
                  }
               }
            }
         }

         if ((state_0 & 48) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            if (arg2Value instanceof TruffleString) {
               TruffleString arg2Value_xx = (TruffleString)arg2Value;
               if ((state_0 & 16) != 0 && arg5Value == null) {
                  return this.doTString(arg0Value_, arg1Value, arg2Value_xx, arg3Value, arg4Value, arg5Value, arg6Value, this.tString_regionEqualsNode_);
               }

               if ((state_0 & 32) != 0 && arg5Value instanceof TruffleString.WithMask) {
                  TruffleString.WithMask arg5Value_ = (TruffleString.WithMask)arg5Value;
                  if (arg5Value_ != null) {
                     return this.doTStringMask(
                        arg0Value_, arg1Value, arg2Value_xx, arg3Value, arg4Value, arg5Value_, arg6Value, this.tStringMask_regionEqualsNode_
                     );
                  }
               }
            }
         }

         if ((state_0 & 1984) != 0) {
            if ((state_0 & 192) != 0 && arg2Value instanceof byte[]) {
               byte[] arg2Value_xxx = (byte[])arg2Value;
               if ((state_0 & 64) != 0 && TRegexGuards.neitherByteArrayNorString(arg0Value) && arg5Value == null) {
                  return this.doTruffleObjBytes(
                     arg0Value,
                     arg1Value,
                     arg2Value_xxx,
                     arg3Value,
                     arg4Value,
                     arg5Value,
                     arg6Value,
                     this.truffleObjBytes_lengthNode_,
                     this.truffleObjBytes_charAtNode_
                  );
               }

               if ((state_0 & 128) != 0 && arg5Value instanceof byte[]) {
                  byte[] arg5Value_ = (byte[])arg5Value;
                  if (TRegexGuards.neitherByteArrayNorString(arg0Value) && arg5Value_ != null) {
                     return this.doTruffleObjBytesMask(
                        arg0Value,
                        arg1Value,
                        arg2Value_xxx,
                        arg3Value,
                        arg4Value,
                        arg5Value_,
                        arg6Value,
                        this.truffleObjBytesMask_lengthNode_,
                        this.truffleObjBytesMask_charAtNode_
                     );
                  }
               }
            }

            if ((state_0 & 768) != 0 && arg2Value instanceof String) {
               String arg2Value_xxxx = (String)arg2Value;
               if ((state_0 & 256) != 0 && TRegexGuards.neitherByteArrayNorString(arg0Value) && arg5Value == null) {
                  return this.doTruffleObjString(
                     arg0Value,
                     arg1Value,
                     arg2Value_xxxx,
                     arg3Value,
                     arg4Value,
                     arg5Value,
                     arg6Value,
                     this.truffleObjString_lengthNode_,
                     this.truffleObjString_charAtNode_
                  );
               }

               if ((state_0 & 512) != 0 && arg5Value instanceof String) {
                  String arg5Value_ = (String)arg5Value;
                  if (TRegexGuards.neitherByteArrayNorString(arg0Value) && arg5Value_ != null) {
                     return this.doTruffleObjStringMask(
                        arg0Value,
                        arg1Value,
                        arg2Value_xxxx,
                        arg3Value,
                        arg4Value,
                        arg5Value_,
                        arg6Value,
                        this.truffleObjStringMask_lengthNode_,
                        this.truffleObjStringMask_charAtNode_
                     );
                  }
               }
            }

            if ((state_0 & 1024) != 0) {
               InputRegionMatchesNodeGen.TruffleObjTruffleObjData s10_ = this.truffleObjTruffleObj_cache;
               if (s10_ != null && TRegexGuards.neitherByteArrayNorString(arg0Value) && TRegexGuards.neitherByteArrayNorString(arg2Value) && arg5Value == null) {
                  return this.doTruffleObjTruffleObj(
                     arg0Value,
                     arg1Value,
                     arg2Value,
                     arg3Value,
                     arg4Value,
                     arg5Value,
                     arg6Value,
                     s10_.lengthNode1_,
                     s10_.charAtNode1_,
                     s10_.lengthNode2_,
                     s10_.charAtNode2_
                  );
               }
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
   }

   private boolean executeAndSpecialize(
      Object arg0Value, int arg1Value, Object arg2Value, int arg3Value, int arg4Value, Object arg5Value, Encodings.Encoding arg6Value
   ) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         if (arg0Value instanceof byte[]) {
            byte[] arg0Value_ = (byte[])arg0Value;
            if (arg2Value instanceof byte[]) {
               byte[] arg2Value_ = (byte[])arg2Value;
               if (arg5Value == null) {
                  int var28;
                  this.state_0_ = var28 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.doBytes(arg0Value_, arg1Value, arg2Value_, arg3Value, arg4Value, arg5Value, arg6Value);
               }

               if (arg5Value instanceof byte[]) {
                  byte[] arg5Value_ = (byte[])arg5Value;
                  if (arg5Value_ != null) {
                     int var27;
                     this.state_0_ = var27 = state_0 | 2;
                     lock.unlock();
                     hasLock = false;
                     return this.doBytesMask(arg0Value_, arg1Value, arg2Value_, arg3Value, arg4Value, arg5Value_, arg6Value);
                  }
               }
            }
         }

         if (arg0Value instanceof String) {
            String arg0Value_ = (String)arg0Value;
            if (arg2Value instanceof String) {
               String arg2Value_x = (String)arg2Value;
               if (arg5Value == null) {
                  int var26;
                  this.state_0_ = var26 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return this.doString(arg0Value_, arg1Value, arg2Value_x, arg3Value, arg4Value, arg5Value, arg6Value);
               }

               if (arg5Value instanceof String) {
                  String arg5Value_ = (String)arg5Value;
                  if (arg5Value_ != null) {
                     int var25;
                     this.state_0_ = var25 = state_0 | 8;
                     lock.unlock();
                     hasLock = false;
                     return this.doJavaStringMask(arg0Value_, arg1Value, arg2Value_x, arg3Value, arg4Value, arg5Value_, arg6Value);
                  }
               }
            }
         }

         if (arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            if (arg2Value instanceof TruffleString) {
               TruffleString arg2Value_xx = (TruffleString)arg2Value;
               if (arg5Value == null) {
                  this.tString_regionEqualsNode_ = super.insert(TruffleString.RegionEqualByteIndexNode.create());
                  int var24;
                  this.state_0_ = var24 = state_0 | 16;
                  lock.unlock();
                  hasLock = false;
                  return this.doTString(arg0Value_, arg1Value, arg2Value_xx, arg3Value, arg4Value, arg5Value, arg6Value, this.tString_regionEqualsNode_);
               }

               if (arg5Value instanceof TruffleString.WithMask) {
                  TruffleString.WithMask arg5Value_ = (TruffleString.WithMask)arg5Value;
                  if (arg5Value_ != null) {
                     this.tStringMask_regionEqualsNode_ = super.insert(TruffleString.RegionEqualByteIndexNode.create());
                     int var23;
                     this.state_0_ = var23 = state_0 | 32;
                     lock.unlock();
                     hasLock = false;
                     return this.doTStringMask(
                        arg0Value_, arg1Value, arg2Value_xx, arg3Value, arg4Value, arg5Value_, arg6Value, this.tStringMask_regionEqualsNode_
                     );
                  }
               }
            }
         }

         if (arg2Value instanceof byte[]) {
            byte[] arg2Value_xxx = (byte[])arg2Value;
            if (TRegexGuards.neitherByteArrayNorString(arg0Value) && arg5Value == null) {
               this.truffleObjBytes_lengthNode_ = super.insert(InputLengthNode.create());
               this.truffleObjBytes_charAtNode_ = super.insert(InputReadNode.create());
               int var22;
               this.state_0_ = var22 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               return this.doTruffleObjBytes(
                  arg0Value,
                  arg1Value,
                  arg2Value_xxx,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  this.truffleObjBytes_lengthNode_,
                  this.truffleObjBytes_charAtNode_
               );
            }

            if (arg5Value instanceof byte[]) {
               byte[] arg5Value_ = (byte[])arg5Value;
               if (TRegexGuards.neitherByteArrayNorString(arg0Value) && arg5Value_ != null) {
                  this.truffleObjBytesMask_lengthNode_ = super.insert(InputLengthNode.create());
                  this.truffleObjBytesMask_charAtNode_ = super.insert(InputReadNode.create());
                  int var21;
                  this.state_0_ = var21 = state_0 | 128;
                  lock.unlock();
                  hasLock = false;
                  return this.doTruffleObjBytesMask(
                     arg0Value,
                     arg1Value,
                     arg2Value_xxx,
                     arg3Value,
                     arg4Value,
                     arg5Value_,
                     arg6Value,
                     this.truffleObjBytesMask_lengthNode_,
                     this.truffleObjBytesMask_charAtNode_
                  );
               }
            }
         }

         if (arg2Value instanceof String) {
            String arg2Value_xxxx = (String)arg2Value;
            if (TRegexGuards.neitherByteArrayNorString(arg0Value) && arg5Value == null) {
               this.truffleObjString_lengthNode_ = super.insert(InputLengthNode.create());
               this.truffleObjString_charAtNode_ = super.insert(InputReadNode.create());
               int var20;
               this.state_0_ = var20 = state_0 | 256;
               lock.unlock();
               hasLock = false;
               return this.doTruffleObjString(
                  arg0Value,
                  arg1Value,
                  arg2Value_xxxx,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  this.truffleObjString_lengthNode_,
                  this.truffleObjString_charAtNode_
               );
            }

            if (arg5Value instanceof String) {
               String arg5Value_ = (String)arg5Value;
               if (TRegexGuards.neitherByteArrayNorString(arg0Value) && arg5Value_ != null) {
                  this.truffleObjStringMask_lengthNode_ = super.insert(InputLengthNode.create());
                  this.truffleObjStringMask_charAtNode_ = super.insert(InputReadNode.create());
                  int var19;
                  this.state_0_ = var19 = state_0 | 512;
                  lock.unlock();
                  hasLock = false;
                  return this.doTruffleObjStringMask(
                     arg0Value,
                     arg1Value,
                     arg2Value_xxxx,
                     arg3Value,
                     arg4Value,
                     arg5Value_,
                     arg6Value,
                     this.truffleObjStringMask_lengthNode_,
                     this.truffleObjStringMask_charAtNode_
                  );
               }
            }
         }

         if (!TRegexGuards.neitherByteArrayNorString(arg0Value) || !TRegexGuards.neitherByteArrayNorString(arg2Value) || arg5Value != null) {
            throw new UnsupportedSpecializationException(
               this, new Node[]{null, null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value
            );
         } else {
            InputRegionMatchesNodeGen.TruffleObjTruffleObjData s10_ = super.insert(new InputRegionMatchesNodeGen.TruffleObjTruffleObjData());
            s10_.lengthNode1_ = s10_.insertAccessor(InputLengthNode.create());
            s10_.charAtNode1_ = s10_.insertAccessor(InputReadNode.create());
            s10_.lengthNode2_ = s10_.insertAccessor(InputLengthNode.create());
            s10_.charAtNode2_ = s10_.insertAccessor(InputReadNode.create());
            VarHandle.storeStoreFence();
            this.truffleObjTruffleObj_cache = s10_;
            int var18;
            this.state_0_ = var18 = state_0 | 1024;
            lock.unlock();
            hasLock = false;
            return this.doTruffleObjTruffleObj(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               arg5Value,
               arg6Value,
               s10_.lengthNode1_,
               s10_.charAtNode1_,
               s10_.lengthNode2_,
               s10_.charAtNode2_
            );
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

   public static InputRegionMatchesNode create() {
      return new InputRegionMatchesNodeGen();
   }

   @GeneratedBy(InputRegionMatchesNode.class)
   private static final class TruffleObjTruffleObjData extends Node {
      @Node.Child
      InputLengthNode lengthNode1_;
      @Node.Child
      InputReadNode charAtNode1_;
      @Node.Child
      InputLengthNode lengthNode2_;
      @Node.Child
      InputReadNode charAtNode2_;

      TruffleObjTruffleObjData() {
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
