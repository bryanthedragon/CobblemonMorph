package com.oracle.truffle.regex;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.regex.runtime.nodes.ExpectByteArrayHostObjectNode;
import com.oracle.truffle.regex.runtime.nodes.ExpectByteArrayHostObjectNodeGen;
import com.oracle.truffle.regex.runtime.nodes.ExpectStringOrTruffleObjectNode;
import com.oracle.truffle.regex.runtime.nodes.ExpectStringOrTruffleObjectNodeGen;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(RegexObject.class)
public final class RegexObjectFactory {
   @GeneratedBy(RegexObject.ExecCompiledRegexNode.class)
   static final class ExecCompiledRegexNodeGen extends RegexObject.ExecCompiledRegexNode {
      private static final RegexObjectFactory.ExecCompiledRegexNodeGen.Uncached UNCACHED = new RegexObjectFactory.ExecCompiledRegexNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private RegexObjectFactory.ExecCompiledRegexNodeGen.ExecuteDirectCallData executeDirectCall_cache;
      @Node.Child
      private IndirectCallNode executeIndirectCall_indirectCallNode_;

      private ExecCompiledRegexNodeGen() {
      }

      @ExplodeLoop
      @Override
      Object execute(CallTarget arg0Value, Object arg1Value, int arg2Value) throws UnsupportedMessageException, ArityException, UnsupportedTypeException {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (RegexObjectFactory.ExecCompiledRegexNodeGen.ExecuteDirectCallData s0_ = this.executeDirectCall_cache; s0_ != null; s0_ = s0_.next_) {
                  if (arg0Value == s0_.cachedCallTarget_) {
                     return RegexObject.ExecCompiledRegexNode.executeDirectCall(arg0Value, arg1Value, arg2Value, s0_.cachedCallTarget_, s0_.directCallNode_);
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               return RegexObject.ExecCompiledRegexNode.executeIndirectCall(arg0Value, arg1Value, arg2Value, this.executeIndirectCall_indirectCallNode_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
      }

      private Object executeAndSpecialize(CallTarget arg0Value, Object arg1Value, int arg2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            int oldState_0 = state_0;

            try {
               if (exclude == 0) {
                  int count0_ = 0;
                  RegexObjectFactory.ExecCompiledRegexNodeGen.ExecuteDirectCallData s0_ = this.executeDirectCall_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && arg0Value != s0_.cachedCallTarget_) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 4) {
                     s0_ = super.insert(new RegexObjectFactory.ExecCompiledRegexNodeGen.ExecuteDirectCallData(this.executeDirectCall_cache));
                     s0_.cachedCallTarget_ = arg0Value;
                     s0_.directCallNode_ = s0_.insertAccessor(DirectCallNode.create(s0_.cachedCallTarget_));
                     VarHandle.storeStoreFence();
                     this.executeDirectCall_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return RegexObject.ExecCompiledRegexNode.executeDirectCall(arg0Value, arg1Value, arg2Value, s0_.cachedCallTarget_, s0_.directCallNode_);
                  }
               }

               this.executeIndirectCall_indirectCallNode_ = super.insert(IndirectCallNode.create());
               int var22;
               this.exclude_ = var22 = exclude | 1;
               this.executeDirectCall_cache = null;
               state_0 &= -2;
               int var21;
               this.state_0_ = var21 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return RegexObject.ExecCompiledRegexNode.executeIndirectCall(arg0Value, arg1Value, arg2Value, this.executeIndirectCall_indirectCallNode_);
            } finally {
               if (oldState_0 != 0) {
                  this.checkForPolymorphicSpecialize(oldState_0);
               }
            }
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      private void checkForPolymorphicSpecialize(int oldState_0) {
         if ((oldState_0 & 2) == 0 && (this.state_0_ & 2) != 0) {
            this.reportPolymorphicSpecialize();
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            if ((state_0 & state_0 - 1) == 0) {
               RegexObjectFactory.ExecCompiledRegexNodeGen.ExecuteDirectCallData s0_ = this.executeDirectCall_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static RegexObject.ExecCompiledRegexNode create() {
         return new RegexObjectFactory.ExecCompiledRegexNodeGen();
      }

      public static RegexObject.ExecCompiledRegexNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(RegexObject.ExecCompiledRegexNode.class)
      private static final class ExecuteDirectCallData extends Node {
         @Node.Child
         RegexObjectFactory.ExecCompiledRegexNodeGen.ExecuteDirectCallData next_;
         @CompilerDirectives.CompilationFinal
         CallTarget cachedCallTarget_;
         @Node.Child
         DirectCallNode directCallNode_;

         ExecuteDirectCallData(RegexObjectFactory.ExecCompiledRegexNodeGen.ExecuteDirectCallData next_) {
            this.next_ = next_;
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(RegexObject.ExecCompiledRegexNode.class)
      @DenyReplace
      private static final class Uncached extends RegexObject.ExecCompiledRegexNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         Object execute(CallTarget arg0Value, Object arg1Value, int arg2Value) throws UnsupportedMessageException, ArityException, UnsupportedTypeException {
            return RegexObject.ExecCompiledRegexNode.executeIndirectCall(arg0Value, arg1Value, arg2Value, IndirectCallNode.getUncached());
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }
      }
   }

   @GeneratedBy(RegexObject.InvokeCacheNode.class)
   static final class InvokeCacheNodeGen extends RegexObject.InvokeCacheNode {
      private static final RegexObjectFactory.InvokeCacheNodeGen.Uncached UNCACHED = new RegexObjectFactory.InvokeCacheNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private RegexObjectFactory.InvokeCacheNodeGen.ExecIdentityData execIdentity_cache;
      @Node.Child
      private RegexObjectFactory.InvokeCacheNodeGen.ExecEqualsData execEquals_cache;
      @Node.Child
      private RegexObjectFactory.InvokeCacheNodeGen.ExecBooleanIdentityData execBooleanIdentity_cache;
      @Node.Child
      private RegexObjectFactory.InvokeCacheNodeGen.ExecBooleanEqualsData execBooleanEquals_cache;
      @Node.Child
      private RegexObjectFactory.InvokeCacheNodeGen.ExecBytesIdentityData execBytesIdentity_cache;
      @Node.Child
      private RegexObjectFactory.InvokeCacheNodeGen.ExecBytesEqualsData execBytesEquals_cache;
      @Node.Child
      private RegexObjectFactory.InvokeCacheNodeGen.InvokeGenericData invokeGeneric_cache;

      private InvokeCacheNodeGen() {
      }

      @ExplodeLoop
      @Override
      Object execute(String arg0Value, RegexObject arg1Value, Object arg2Value, int arg3Value) throws UnsupportedMessageException, ArityException, UnsupportedTypeException, UnknownIdentifierException {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (RegexObjectFactory.InvokeCacheNodeGen.ExecIdentityData s0_ = this.execIdentity_cache; s0_ != null; s0_ = s0_.next_) {
                  if (arg0Value == s0_.cachedSymbol_) {
                     assert s0_.cachedSymbol_.equals("exec");

                     return this.execIdentity(
                        arg0Value, arg1Value, arg2Value, arg3Value, s0_.cachedSymbol_, s0_.expectStringOrTruffleObjectNode_, s0_.execNode_
                     );
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               for (RegexObjectFactory.InvokeCacheNodeGen.ExecEqualsData s1_ = this.execEquals_cache; s1_ != null; s1_ = s1_.next_) {
                  if (arg0Value.equals(s1_.cachedSymbol_)) {
                     assert s1_.cachedSymbol_.equals("exec");

                     return this.execEquals(arg0Value, arg1Value, arg2Value, arg3Value, s1_.cachedSymbol_, s1_.expectStringOrTruffleObjectNode_, s1_.execNode_);
                  }
               }
            }

            if ((state_0 & 4) != 0) {
               for (RegexObjectFactory.InvokeCacheNodeGen.ExecBooleanIdentityData s2_ = this.execBooleanIdentity_cache; s2_ != null; s2_ = s2_.next_) {
                  if (arg0Value == s2_.cachedSymbol_) {
                     assert s2_.cachedSymbol_.equals("execBoolean");

                     return this.execBooleanIdentity(
                        arg0Value, arg1Value, arg2Value, arg3Value, s2_.cachedSymbol_, s2_.expectStringOrTruffleObjectNode_, s2_.execNode_
                     );
                  }
               }
            }

            if ((state_0 & 8) != 0) {
               for (RegexObjectFactory.InvokeCacheNodeGen.ExecBooleanEqualsData s3_ = this.execBooleanEquals_cache; s3_ != null; s3_ = s3_.next_) {
                  if (arg0Value.equals(s3_.cachedSymbol_)) {
                     assert s3_.cachedSymbol_.equals("execBoolean");

                     return this.execBooleanEquals(
                        arg0Value, arg1Value, arg2Value, arg3Value, s3_.cachedSymbol_, s3_.expectStringOrTruffleObjectNode_, s3_.execNode_
                     );
                  }
               }
            }

            if ((state_0 & 16) != 0) {
               for (RegexObjectFactory.InvokeCacheNodeGen.ExecBytesIdentityData s4_ = this.execBytesIdentity_cache; s4_ != null; s4_ = s4_.next_) {
                  if (arg0Value == s4_.cachedSymbol_) {
                     assert s4_.cachedSymbol_.equals("execBytes");

                     return this.execBytesIdentity(
                        arg0Value, arg1Value, arg2Value, arg3Value, s4_.cachedSymbol_, s4_.expectByteArrayHostObjectNode_, s4_.execNode_
                     );
                  }
               }
            }

            if ((state_0 & 32) != 0) {
               for (RegexObjectFactory.InvokeCacheNodeGen.ExecBytesEqualsData s5_ = this.execBytesEquals_cache; s5_ != null; s5_ = s5_.next_) {
                  if (arg0Value.equals(s5_.cachedSymbol_)) {
                     assert s5_.cachedSymbol_.equals("execBytes");

                     return this.execBytesEquals(
                        arg0Value, arg1Value, arg2Value, arg3Value, s5_.cachedSymbol_, s5_.expectByteArrayHostObjectNode_, s5_.execNode_
                     );
                  }
               }
            }

            if ((state_0 & 64) != 0) {
               RegexObjectFactory.InvokeCacheNodeGen.InvokeGenericData s6_ = this.invokeGeneric_cache;
               if (s6_ != null) {
                  return RegexObject.InvokeCacheNode.invokeGeneric(
                     arg0Value, arg1Value, arg2Value, arg3Value, s6_.expectStringOrTruffleObjectNode_, s6_.expectByteArrayHostObjectNode_, s6_.execNode_
                  );
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
      }

      private Object executeAndSpecialize(String arg0Value, RegexObject arg1Value, Object arg2Value, int arg3Value) throws UnsupportedMessageException, ArityException, UnsupportedTypeException, UnknownIdentifierException {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            int oldState_0 = state_0;

            try {
               if ((exclude & 1) == 0) {
                  int count0_ = 0;
                  RegexObjectFactory.InvokeCacheNodeGen.ExecIdentityData s0_ = this.execIdentity_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null) {
                        if (arg0Value == s0_.cachedSymbol_) {
                           assert s0_.cachedSymbol_.equals("exec");
                           break;
                        }

                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && arg0Value.equals("exec") && count0_ < 3) {
                     s0_ = super.insert(new RegexObjectFactory.InvokeCacheNodeGen.ExecIdentityData(this.execIdentity_cache));
                     s0_.cachedSymbol_ = arg0Value;
                     s0_.expectStringOrTruffleObjectNode_ = s0_.insertAccessor(ExpectStringOrTruffleObjectNode.create());
                     s0_.execNode_ = s0_.insertAccessor(RegexObjectFactory.ExecCompiledRegexNodeGen.create());
                     VarHandle.storeStoreFence();
                     this.execIdentity_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.execIdentity(
                        arg0Value, arg1Value, arg2Value, arg3Value, s0_.cachedSymbol_, s0_.expectStringOrTruffleObjectNode_, s0_.execNode_
                     );
                  }
               }

               if ((exclude & 2) == 0) {
                  int count1_ = 0;
                  RegexObjectFactory.InvokeCacheNodeGen.ExecEqualsData s1_ = this.execEquals_cache;
                  if ((state_0 & 2) != 0) {
                     while (s1_ != null) {
                        if (arg0Value.equals(s1_.cachedSymbol_)) {
                           assert s1_.cachedSymbol_.equals("exec");
                           break;
                        }

                        s1_ = s1_.next_;
                        count1_++;
                     }
                  }

                  if (s1_ == null && arg0Value.equals("exec") && count1_ < 3) {
                     s1_ = super.insert(new RegexObjectFactory.InvokeCacheNodeGen.ExecEqualsData(this.execEquals_cache));
                     s1_.cachedSymbol_ = arg0Value;
                     s1_.expectStringOrTruffleObjectNode_ = s1_.insertAccessor(ExpectStringOrTruffleObjectNode.create());
                     s1_.execNode_ = s1_.insertAccessor(RegexObjectFactory.ExecCompiledRegexNodeGen.create());
                     VarHandle.storeStoreFence();
                     this.execEquals_cache = s1_;
                     this.exclude_ = exclude |= 1;
                     this.execIdentity_cache = null;
                     int var21 = state_0 & -2;
                     this.state_0_ = state_0 = var21 | 2;
                  }

                  if (s1_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.execEquals(arg0Value, arg1Value, arg2Value, arg3Value, s1_.cachedSymbol_, s1_.expectStringOrTruffleObjectNode_, s1_.execNode_);
                  }
               }

               if ((exclude & 4) == 0) {
                  int count2_ = 0;
                  RegexObjectFactory.InvokeCacheNodeGen.ExecBooleanIdentityData s2_ = this.execBooleanIdentity_cache;
                  if ((state_0 & 4) != 0) {
                     while (s2_ != null) {
                        if (arg0Value == s2_.cachedSymbol_) {
                           assert s2_.cachedSymbol_.equals("execBoolean");
                           break;
                        }

                        s2_ = s2_.next_;
                        count2_++;
                     }
                  }

                  if (s2_ == null && arg0Value.equals("execBoolean") && count2_ < 3) {
                     s2_ = super.insert(new RegexObjectFactory.InvokeCacheNodeGen.ExecBooleanIdentityData(this.execBooleanIdentity_cache));
                     s2_.cachedSymbol_ = arg0Value;
                     s2_.expectStringOrTruffleObjectNode_ = s2_.insertAccessor(ExpectStringOrTruffleObjectNode.create());
                     s2_.execNode_ = s2_.insertAccessor(RegexObjectFactory.ExecCompiledRegexNodeGen.create());
                     VarHandle.storeStoreFence();
                     this.execBooleanIdentity_cache = s2_;
                     this.state_0_ = state_0 |= 4;
                  }

                  if (s2_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.execBooleanIdentity(
                        arg0Value, arg1Value, arg2Value, arg3Value, s2_.cachedSymbol_, s2_.expectStringOrTruffleObjectNode_, s2_.execNode_
                     );
                  }
               }

               if ((exclude & 8) == 0) {
                  int count3_ = 0;
                  RegexObjectFactory.InvokeCacheNodeGen.ExecBooleanEqualsData s3_ = this.execBooleanEquals_cache;
                  if ((state_0 & 8) != 0) {
                     while (s3_ != null) {
                        if (arg0Value.equals(s3_.cachedSymbol_)) {
                           assert s3_.cachedSymbol_.equals("execBoolean");
                           break;
                        }

                        s3_ = s3_.next_;
                        count3_++;
                     }
                  }

                  if (s3_ == null && arg0Value.equals("execBoolean") && count3_ < 3) {
                     s3_ = super.insert(new RegexObjectFactory.InvokeCacheNodeGen.ExecBooleanEqualsData(this.execBooleanEquals_cache));
                     s3_.cachedSymbol_ = arg0Value;
                     s3_.expectStringOrTruffleObjectNode_ = s3_.insertAccessor(ExpectStringOrTruffleObjectNode.create());
                     s3_.execNode_ = s3_.insertAccessor(RegexObjectFactory.ExecCompiledRegexNodeGen.create());
                     VarHandle.storeStoreFence();
                     this.execBooleanEquals_cache = s3_;
                     this.exclude_ = exclude |= 4;
                     this.execBooleanIdentity_cache = null;
                     int var22 = state_0 & -5;
                     this.state_0_ = state_0 = var22 | 8;
                  }

                  if (s3_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.execBooleanEquals(
                        arg0Value, arg1Value, arg2Value, arg3Value, s3_.cachedSymbol_, s3_.expectStringOrTruffleObjectNode_, s3_.execNode_
                     );
                  }
               }

               if ((exclude & 16) == 0) {
                  int count4_ = 0;
                  RegexObjectFactory.InvokeCacheNodeGen.ExecBytesIdentityData s4_ = this.execBytesIdentity_cache;
                  if ((state_0 & 16) != 0) {
                     while (s4_ != null) {
                        if (arg0Value == s4_.cachedSymbol_) {
                           assert s4_.cachedSymbol_.equals("execBytes");
                           break;
                        }

                        s4_ = s4_.next_;
                        count4_++;
                     }
                  }

                  if (s4_ == null && arg0Value.equals("execBytes") && count4_ < 3) {
                     s4_ = super.insert(new RegexObjectFactory.InvokeCacheNodeGen.ExecBytesIdentityData(this.execBytesIdentity_cache));
                     s4_.cachedSymbol_ = arg0Value;
                     s4_.expectByteArrayHostObjectNode_ = s4_.insertAccessor(ExpectByteArrayHostObjectNodeGen.create());
                     s4_.execNode_ = s4_.insertAccessor(RegexObjectFactory.ExecCompiledRegexNodeGen.create());
                     VarHandle.storeStoreFence();
                     this.execBytesIdentity_cache = s4_;
                     this.state_0_ = state_0 |= 16;
                  }

                  if (s4_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.execBytesIdentity(
                        arg0Value, arg1Value, arg2Value, arg3Value, s4_.cachedSymbol_, s4_.expectByteArrayHostObjectNode_, s4_.execNode_
                     );
                  }
               }

               if ((exclude & 32) == 0) {
                  int count5_ = 0;
                  RegexObjectFactory.InvokeCacheNodeGen.ExecBytesEqualsData s5_ = this.execBytesEquals_cache;
                  if ((state_0 & 32) != 0) {
                     while (s5_ != null) {
                        if (arg0Value.equals(s5_.cachedSymbol_)) {
                           assert s5_.cachedSymbol_.equals("execBytes");
                           break;
                        }

                        s5_ = s5_.next_;
                        count5_++;
                     }
                  }

                  if (s5_ == null && arg0Value.equals("execBytes") && count5_ < 3) {
                     s5_ = super.insert(new RegexObjectFactory.InvokeCacheNodeGen.ExecBytesEqualsData(this.execBytesEquals_cache));
                     s5_.cachedSymbol_ = arg0Value;
                     s5_.expectByteArrayHostObjectNode_ = s5_.insertAccessor(ExpectByteArrayHostObjectNodeGen.create());
                     s5_.execNode_ = s5_.insertAccessor(RegexObjectFactory.ExecCompiledRegexNodeGen.create());
                     VarHandle.storeStoreFence();
                     this.execBytesEquals_cache = s5_;
                     this.exclude_ = exclude |= 16;
                     this.execBytesIdentity_cache = null;
                     int var23 = state_0 & -17;
                     this.state_0_ = state_0 = var23 | 32;
                  }

                  if (s5_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.execBytesEquals(
                        arg0Value, arg1Value, arg2Value, arg3Value, s5_.cachedSymbol_, s5_.expectByteArrayHostObjectNode_, s5_.execNode_
                     );
                  }
               }

               RegexObjectFactory.InvokeCacheNodeGen.InvokeGenericData s6_ = super.insert(new RegexObjectFactory.InvokeCacheNodeGen.InvokeGenericData());
               s6_.expectStringOrTruffleObjectNode_ = s6_.insertAccessor(ExpectStringOrTruffleObjectNode.create());
               s6_.expectByteArrayHostObjectNode_ = s6_.insertAccessor(ExpectByteArrayHostObjectNodeGen.create());
               s6_.execNode_ = s6_.insertAccessor(RegexObjectFactory.ExecCompiledRegexNodeGen.create());
               VarHandle.storeStoreFence();
               this.invokeGeneric_cache = s6_;
               int var26;
               this.exclude_ = var26 = exclude | 63;
               this.execIdentity_cache = null;
               this.execEquals_cache = null;
               this.execBooleanIdentity_cache = null;
               this.execBooleanEquals_cache = null;
               this.execBytesIdentity_cache = null;
               this.execBytesEquals_cache = null;
               state_0 &= -64;
               int var25;
               this.state_0_ = var25 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               return RegexObject.InvokeCacheNode.invokeGeneric(
                  arg0Value, arg1Value, arg2Value, arg3Value, s6_.expectStringOrTruffleObjectNode_, s6_.expectByteArrayHostObjectNode_, s6_.execNode_
               );
            } finally {
               if (oldState_0 != 0) {
                  this.checkForPolymorphicSpecialize(oldState_0);
               }
            }
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      private void checkForPolymorphicSpecialize(int oldState_0) {
         if ((oldState_0 & 64) == 0 && (this.state_0_ & 64) != 0) {
            this.reportPolymorphicSpecialize();
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            if ((state_0 & state_0 - 1) == 0) {
               RegexObjectFactory.InvokeCacheNodeGen.ExecIdentityData s0_ = this.execIdentity_cache;
               RegexObjectFactory.InvokeCacheNodeGen.ExecEqualsData s1_ = this.execEquals_cache;
               RegexObjectFactory.InvokeCacheNodeGen.ExecBooleanIdentityData s2_ = this.execBooleanIdentity_cache;
               RegexObjectFactory.InvokeCacheNodeGen.ExecBooleanEqualsData s3_ = this.execBooleanEquals_cache;
               RegexObjectFactory.InvokeCacheNodeGen.ExecBytesIdentityData s4_ = this.execBytesIdentity_cache;
               RegexObjectFactory.InvokeCacheNodeGen.ExecBytesEqualsData s5_ = this.execBytesEquals_cache;
               if ((s0_ == null || s0_.next_ == null)
                  && (s1_ == null || s1_.next_ == null)
                  && (s2_ == null || s2_.next_ == null)
                  && (s3_ == null || s3_.next_ == null)
                  && (s4_ == null || s4_.next_ == null)
                  && (s5_ == null || s5_.next_ == null)) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static RegexObject.InvokeCacheNode create() {
         return new RegexObjectFactory.InvokeCacheNodeGen();
      }

      public static RegexObject.InvokeCacheNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(RegexObject.InvokeCacheNode.class)
      private static final class ExecBooleanEqualsData extends Node {
         @Node.Child
         RegexObjectFactory.InvokeCacheNodeGen.ExecBooleanEqualsData next_;
         @CompilerDirectives.CompilationFinal
         String cachedSymbol_;
         @Node.Child
         ExpectStringOrTruffleObjectNode expectStringOrTruffleObjectNode_;
         @Node.Child
         RegexObject.ExecCompiledRegexNode execNode_;

         ExecBooleanEqualsData(RegexObjectFactory.InvokeCacheNodeGen.ExecBooleanEqualsData next_) {
            this.next_ = next_;
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(RegexObject.InvokeCacheNode.class)
      private static final class ExecBooleanIdentityData extends Node {
         @Node.Child
         RegexObjectFactory.InvokeCacheNodeGen.ExecBooleanIdentityData next_;
         @CompilerDirectives.CompilationFinal
         String cachedSymbol_;
         @Node.Child
         ExpectStringOrTruffleObjectNode expectStringOrTruffleObjectNode_;
         @Node.Child
         RegexObject.ExecCompiledRegexNode execNode_;

         ExecBooleanIdentityData(RegexObjectFactory.InvokeCacheNodeGen.ExecBooleanIdentityData next_) {
            this.next_ = next_;
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(RegexObject.InvokeCacheNode.class)
      private static final class ExecBytesEqualsData extends Node {
         @Node.Child
         RegexObjectFactory.InvokeCacheNodeGen.ExecBytesEqualsData next_;
         @CompilerDirectives.CompilationFinal
         String cachedSymbol_;
         @Node.Child
         ExpectByteArrayHostObjectNode expectByteArrayHostObjectNode_;
         @Node.Child
         RegexObject.ExecCompiledRegexNode execNode_;

         ExecBytesEqualsData(RegexObjectFactory.InvokeCacheNodeGen.ExecBytesEqualsData next_) {
            this.next_ = next_;
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(RegexObject.InvokeCacheNode.class)
      private static final class ExecBytesIdentityData extends Node {
         @Node.Child
         RegexObjectFactory.InvokeCacheNodeGen.ExecBytesIdentityData next_;
         @CompilerDirectives.CompilationFinal
         String cachedSymbol_;
         @Node.Child
         ExpectByteArrayHostObjectNode expectByteArrayHostObjectNode_;
         @Node.Child
         RegexObject.ExecCompiledRegexNode execNode_;

         ExecBytesIdentityData(RegexObjectFactory.InvokeCacheNodeGen.ExecBytesIdentityData next_) {
            this.next_ = next_;
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(RegexObject.InvokeCacheNode.class)
      private static final class ExecEqualsData extends Node {
         @Node.Child
         RegexObjectFactory.InvokeCacheNodeGen.ExecEqualsData next_;
         @CompilerDirectives.CompilationFinal
         String cachedSymbol_;
         @Node.Child
         ExpectStringOrTruffleObjectNode expectStringOrTruffleObjectNode_;
         @Node.Child
         RegexObject.ExecCompiledRegexNode execNode_;

         ExecEqualsData(RegexObjectFactory.InvokeCacheNodeGen.ExecEqualsData next_) {
            this.next_ = next_;
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(RegexObject.InvokeCacheNode.class)
      private static final class ExecIdentityData extends Node {
         @Node.Child
         RegexObjectFactory.InvokeCacheNodeGen.ExecIdentityData next_;
         @CompilerDirectives.CompilationFinal
         String cachedSymbol_;
         @Node.Child
         ExpectStringOrTruffleObjectNode expectStringOrTruffleObjectNode_;
         @Node.Child
         RegexObject.ExecCompiledRegexNode execNode_;

         ExecIdentityData(RegexObjectFactory.InvokeCacheNodeGen.ExecIdentityData next_) {
            this.next_ = next_;
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(RegexObject.InvokeCacheNode.class)
      private static final class InvokeGenericData extends Node {
         @Node.Child
         ExpectStringOrTruffleObjectNode expectStringOrTruffleObjectNode_;
         @Node.Child
         ExpectByteArrayHostObjectNode expectByteArrayHostObjectNode_;
         @Node.Child
         RegexObject.ExecCompiledRegexNode execNode_;

         InvokeGenericData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(RegexObject.InvokeCacheNode.class)
      @DenyReplace
      private static final class Uncached extends RegexObject.InvokeCacheNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         Object execute(String arg0Value, RegexObject arg1Value, Object arg2Value, int arg3Value) throws UnsupportedMessageException, ArityException, UnsupportedTypeException, UnknownIdentifierException {
            return RegexObject.InvokeCacheNode.invokeGeneric(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               ExpectStringOrTruffleObjectNodeGen.getUncached(),
               ExpectByteArrayHostObjectNodeGen.getUncached(),
               RegexObjectFactory.ExecCompiledRegexNodeGen.getUncached()
            );
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }
      }
   }
}
