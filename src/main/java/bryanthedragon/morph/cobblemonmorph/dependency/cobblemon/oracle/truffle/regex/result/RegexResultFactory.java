package com.oracle.truffle.regex.result;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.regex.runtime.nodes.DispatchNode;
import com.oracle.truffle.regex.runtime.nodes.DispatchNodeGen;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(RegexResult.class)
public final class RegexResultFactory {
   @GeneratedBy(RegexResult.InvokeCacheNode.class)
   static final class InvokeCacheNodeGen extends RegexResult.InvokeCacheNode {
      private static final RegexResultFactory.InvokeCacheNodeGen.Uncached UNCACHED = new RegexResultFactory.InvokeCacheNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private RegexResultFactory.InvokeCacheNodeGen.GetStartIdentityData getStartIdentity_cache;
      @Node.Child
      private RegexResultFactory.InvokeCacheNodeGen.GetStartEqualsData getStartEquals_cache;
      @Node.Child
      private RegexResultFactory.InvokeCacheNodeGen.GetEndIdentityData getEndIdentity_cache;
      @Node.Child
      private RegexResultFactory.InvokeCacheNodeGen.GetEndEqualsData getEndEquals_cache;
      @Node.Child
      private RegexResult.RegexResultGetStartNode invokeGeneric_getStartNode_;
      @Node.Child
      private RegexResult.RegexResultGetEndNode invokeGeneric_getEndNode_;

      private InvokeCacheNodeGen() {
      }

      @ExplodeLoop
      @Override
      Object execute(RegexResult arg0Value, String arg1Value, int arg2Value) throws UnknownIdentifierException {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (RegexResultFactory.InvokeCacheNodeGen.GetStartIdentityData s0_ = this.getStartIdentity_cache; s0_ != null; s0_ = s0_.next_) {
                  if (arg1Value == s0_.cachedSymbol_) {
                     assert s0_.cachedSymbol_.equals("getStart");

                     return this.getStartIdentity(arg0Value, arg1Value, arg2Value, s0_.cachedSymbol_, s0_.getStartNode_);
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               for (RegexResultFactory.InvokeCacheNodeGen.GetStartEqualsData s1_ = this.getStartEquals_cache; s1_ != null; s1_ = s1_.next_) {
                  if (arg1Value.equals(s1_.cachedSymbol_)) {
                     assert s1_.cachedSymbol_.equals("getStart");

                     return this.getStartEquals(arg0Value, arg1Value, arg2Value, s1_.cachedSymbol_, s1_.getStartNode_);
                  }
               }
            }

            if ((state_0 & 4) != 0) {
               for (RegexResultFactory.InvokeCacheNodeGen.GetEndIdentityData s2_ = this.getEndIdentity_cache; s2_ != null; s2_ = s2_.next_) {
                  if (arg1Value == s2_.cachedSymbol_) {
                     assert s2_.cachedSymbol_.equals("getEnd");

                     return this.getEndIdentity(arg0Value, arg1Value, arg2Value, s2_.cachedSymbol_, s2_.getEndNode_);
                  }
               }
            }

            if ((state_0 & 8) != 0) {
               for (RegexResultFactory.InvokeCacheNodeGen.GetEndEqualsData s3_ = this.getEndEquals_cache; s3_ != null; s3_ = s3_.next_) {
                  if (arg1Value.equals(s3_.cachedSymbol_)) {
                     assert s3_.cachedSymbol_.equals("getEnd");

                     return this.getEndEquals(arg0Value, arg1Value, arg2Value, s3_.cachedSymbol_, s3_.getEndNode_);
                  }
               }
            }

            if ((state_0 & 16) != 0) {
               return RegexResult.InvokeCacheNode.invokeGeneric(
                  arg0Value, arg1Value, arg2Value, this.invokeGeneric_getStartNode_, this.invokeGeneric_getEndNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
      }

      private Object executeAndSpecialize(RegexResult arg0Value, String arg1Value, int arg2Value) throws UnknownIdentifierException {
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
                  RegexResultFactory.InvokeCacheNodeGen.GetStartIdentityData s0_ = this.getStartIdentity_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null) {
                        if (arg1Value == s0_.cachedSymbol_) {
                           assert s0_.cachedSymbol_.equals("getStart");
                           break;
                        }

                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && arg1Value.equals("getStart") && count0_ < 2) {
                     s0_ = super.insert(new RegexResultFactory.InvokeCacheNodeGen.GetStartIdentityData(this.getStartIdentity_cache));
                     s0_.cachedSymbol_ = arg1Value;
                     s0_.getStartNode_ = s0_.insertAccessor(RegexResult.RegexResultGetStartNode.create());
                     VarHandle.storeStoreFence();
                     this.getStartIdentity_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.getStartIdentity(arg0Value, arg1Value, arg2Value, s0_.cachedSymbol_, s0_.getStartNode_);
                  }
               }

               if ((exclude & 2) == 0) {
                  int count1_ = 0;
                  RegexResultFactory.InvokeCacheNodeGen.GetStartEqualsData s1_ = this.getStartEquals_cache;
                  if ((state_0 & 2) != 0) {
                     while (s1_ != null) {
                        if (arg1Value.equals(s1_.cachedSymbol_)) {
                           assert s1_.cachedSymbol_.equals("getStart");
                           break;
                        }

                        s1_ = s1_.next_;
                        count1_++;
                     }
                  }

                  if (s1_ == null && arg1Value.equals("getStart") && count1_ < 2) {
                     s1_ = super.insert(new RegexResultFactory.InvokeCacheNodeGen.GetStartEqualsData(this.getStartEquals_cache));
                     s1_.cachedSymbol_ = arg1Value;
                     s1_.getStartNode_ = s1_.insertAccessor(RegexResult.RegexResultGetStartNode.create());
                     VarHandle.storeStoreFence();
                     this.getStartEquals_cache = s1_;
                     this.exclude_ = exclude |= 1;
                     this.getStartIdentity_cache = null;
                     int var20 = state_0 & -2;
                     this.state_0_ = state_0 = var20 | 2;
                  }

                  if (s1_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.getStartEquals(arg0Value, arg1Value, arg2Value, s1_.cachedSymbol_, s1_.getStartNode_);
                  }
               }

               if ((exclude & 4) == 0) {
                  int count2_ = 0;
                  RegexResultFactory.InvokeCacheNodeGen.GetEndIdentityData s2_ = this.getEndIdentity_cache;
                  if ((state_0 & 4) != 0) {
                     while (s2_ != null) {
                        if (arg1Value == s2_.cachedSymbol_) {
                           assert s2_.cachedSymbol_.equals("getEnd");
                           break;
                        }

                        s2_ = s2_.next_;
                        count2_++;
                     }
                  }

                  if (s2_ == null && arg1Value.equals("getEnd") && count2_ < 2) {
                     s2_ = super.insert(new RegexResultFactory.InvokeCacheNodeGen.GetEndIdentityData(this.getEndIdentity_cache));
                     s2_.cachedSymbol_ = arg1Value;
                     s2_.getEndNode_ = s2_.insertAccessor(RegexResultFactory.RegexResultGetEndNodeGen.create());
                     VarHandle.storeStoreFence();
                     this.getEndIdentity_cache = s2_;
                     this.state_0_ = state_0 |= 4;
                  }

                  if (s2_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.getEndIdentity(arg0Value, arg1Value, arg2Value, s2_.cachedSymbol_, s2_.getEndNode_);
                  }
               }

               if ((exclude & 8) == 0) {
                  int count3_ = 0;
                  RegexResultFactory.InvokeCacheNodeGen.GetEndEqualsData s3_ = this.getEndEquals_cache;
                  if ((state_0 & 8) != 0) {
                     while (s3_ != null) {
                        if (arg1Value.equals(s3_.cachedSymbol_)) {
                           assert s3_.cachedSymbol_.equals("getEnd");
                           break;
                        }

                        s3_ = s3_.next_;
                        count3_++;
                     }
                  }

                  if (s3_ == null && arg1Value.equals("getEnd") && count3_ < 2) {
                     s3_ = super.insert(new RegexResultFactory.InvokeCacheNodeGen.GetEndEqualsData(this.getEndEquals_cache));
                     s3_.cachedSymbol_ = arg1Value;
                     s3_.getEndNode_ = s3_.insertAccessor(RegexResultFactory.RegexResultGetEndNodeGen.create());
                     VarHandle.storeStoreFence();
                     this.getEndEquals_cache = s3_;
                     this.exclude_ = exclude |= 4;
                     this.getEndIdentity_cache = null;
                     int var21 = state_0 & -5;
                     this.state_0_ = state_0 = var21 | 8;
                  }

                  if (s3_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.getEndEquals(arg0Value, arg1Value, arg2Value, s3_.cachedSymbol_, s3_.getEndNode_);
                  }
               }

               this.invokeGeneric_getStartNode_ = super.insert(RegexResult.RegexResultGetStartNode.create());
               this.invokeGeneric_getEndNode_ = super.insert(RegexResultFactory.RegexResultGetEndNodeGen.create());
               int var24;
               this.exclude_ = var24 = exclude | 15;
               this.getStartIdentity_cache = null;
               this.getStartEquals_cache = null;
               this.getEndIdentity_cache = null;
               this.getEndEquals_cache = null;
               state_0 &= -16;
               int var23;
               this.state_0_ = var23 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return RegexResult.InvokeCacheNode.invokeGeneric(
                  arg0Value, arg1Value, arg2Value, this.invokeGeneric_getStartNode_, this.invokeGeneric_getEndNode_
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
         if ((oldState_0 & 16) == 0 && (this.state_0_ & 16) != 0) {
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
               RegexResultFactory.InvokeCacheNodeGen.GetStartIdentityData s0_ = this.getStartIdentity_cache;
               RegexResultFactory.InvokeCacheNodeGen.GetStartEqualsData s1_ = this.getStartEquals_cache;
               RegexResultFactory.InvokeCacheNodeGen.GetEndIdentityData s2_ = this.getEndIdentity_cache;
               RegexResultFactory.InvokeCacheNodeGen.GetEndEqualsData s3_ = this.getEndEquals_cache;
               if ((s0_ == null || s0_.next_ == null)
                  && (s1_ == null || s1_.next_ == null)
                  && (s2_ == null || s2_.next_ == null)
                  && (s3_ == null || s3_.next_ == null)) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static RegexResult.InvokeCacheNode create() {
         return new RegexResultFactory.InvokeCacheNodeGen();
      }

      public static RegexResult.InvokeCacheNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(RegexResult.InvokeCacheNode.class)
      private static final class GetEndEqualsData extends Node {
         @Node.Child
         RegexResultFactory.InvokeCacheNodeGen.GetEndEqualsData next_;
         @CompilerDirectives.CompilationFinal
         String cachedSymbol_;
         @Node.Child
         RegexResult.RegexResultGetEndNode getEndNode_;

         GetEndEqualsData(RegexResultFactory.InvokeCacheNodeGen.GetEndEqualsData next_) {
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

      @GeneratedBy(RegexResult.InvokeCacheNode.class)
      private static final class GetEndIdentityData extends Node {
         @Node.Child
         RegexResultFactory.InvokeCacheNodeGen.GetEndIdentityData next_;
         @CompilerDirectives.CompilationFinal
         String cachedSymbol_;
         @Node.Child
         RegexResult.RegexResultGetEndNode getEndNode_;

         GetEndIdentityData(RegexResultFactory.InvokeCacheNodeGen.GetEndIdentityData next_) {
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

      @GeneratedBy(RegexResult.InvokeCacheNode.class)
      private static final class GetStartEqualsData extends Node {
         @Node.Child
         RegexResultFactory.InvokeCacheNodeGen.GetStartEqualsData next_;
         @CompilerDirectives.CompilationFinal
         String cachedSymbol_;
         @Node.Child
         RegexResult.RegexResultGetStartNode getStartNode_;

         GetStartEqualsData(RegexResultFactory.InvokeCacheNodeGen.GetStartEqualsData next_) {
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

      @GeneratedBy(RegexResult.InvokeCacheNode.class)
      private static final class GetStartIdentityData extends Node {
         @Node.Child
         RegexResultFactory.InvokeCacheNodeGen.GetStartIdentityData next_;
         @CompilerDirectives.CompilationFinal
         String cachedSymbol_;
         @Node.Child
         RegexResult.RegexResultGetStartNode getStartNode_;

         GetStartIdentityData(RegexResultFactory.InvokeCacheNodeGen.GetStartIdentityData next_) {
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

      @GeneratedBy(RegexResult.InvokeCacheNode.class)
      @DenyReplace
      private static final class Uncached extends RegexResult.InvokeCacheNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         Object execute(RegexResult arg0Value, String arg1Value, int arg2Value) throws UnknownIdentifierException {
            return RegexResult.InvokeCacheNode.invokeGeneric(
               arg0Value, arg1Value, arg2Value, RegexResult.RegexResultGetStartNode.getUncached(), RegexResultFactory.RegexResultGetEndNodeGen.getUncached()
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

   @GeneratedBy(RegexResult.RegexResultGetEndNode.class)
   static final class RegexResultGetEndNodeGen extends RegexResult.RegexResultGetEndNode {
      private static final RegexResultFactory.RegexResultGetEndNodeGen.Uncached UNCACHED = new RegexResultFactory.RegexResultGetEndNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private BranchProfile lazyProfile_;
      @Node.Child
      private DispatchNode getIndicesCall_;

      private RegexResultGetEndNodeGen() {
      }

      @Override
      int execute(Object arg0Value, int arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0 && arg0Value instanceof RegexResult) {
            RegexResult arg0Value_ = (RegexResult)arg0Value;
            return RegexResult.RegexResultGetEndNode.doResult(arg0Value_, arg1Value, this.lazyProfile_, this.getIndicesCall_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
         }
      }

      private int executeAndSpecialize(Object arg0Value, int arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var7;
         try {
            int state_0 = this.state_0_;
            if (!(arg0Value instanceof RegexResult)) {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }

            RegexResult arg0Value_ = (RegexResult)arg0Value;
            this.lazyProfile_ = BranchProfile.create();
            this.getIndicesCall_ = super.insert(DispatchNodeGen.create());
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = RegexResult.RegexResultGetEndNode.doResult(arg0Value_, arg1Value, this.lazyProfile_, this.getIndicesCall_);
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
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      public static RegexResult.RegexResultGetEndNode create() {
         return new RegexResultFactory.RegexResultGetEndNodeGen();
      }

      public static RegexResult.RegexResultGetEndNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(RegexResult.RegexResultGetEndNode.class)
      @DenyReplace
      private static final class Uncached extends RegexResult.RegexResultGetEndNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(Object arg0Value, int arg1Value) {
            if (arg0Value instanceof RegexResult) {
               RegexResult arg0Value_ = (RegexResult)arg0Value;
               return RegexResult.RegexResultGetEndNode.doResult(arg0Value_, arg1Value, BranchProfile.getUncached(), DispatchNodeGen.getUncached());
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }
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

   @GeneratedBy(RegexResult.RegexResultGetStartNode.class)
   public static final class RegexResultGetStartNodeGen extends RegexResult.RegexResultGetStartNode {
      private static final RegexResultFactory.RegexResultGetStartNodeGen.Uncached UNCACHED = new RegexResultFactory.RegexResultGetStartNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private BranchProfile lazyProfile_;
      @Node.Child
      private DispatchNode getIndicesCall_;

      private RegexResultGetStartNodeGen() {
      }

      @Override
      public int execute(Object arg0Value, int arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0 && arg0Value instanceof RegexResult) {
            RegexResult arg0Value_ = (RegexResult)arg0Value;
            return RegexResult.RegexResultGetStartNode.doResult(arg0Value_, arg1Value, this.lazyProfile_, this.getIndicesCall_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
         }
      }

      private int executeAndSpecialize(Object arg0Value, int arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var7;
         try {
            int state_0 = this.state_0_;
            if (!(arg0Value instanceof RegexResult)) {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }

            RegexResult arg0Value_ = (RegexResult)arg0Value;
            this.lazyProfile_ = BranchProfile.create();
            this.getIndicesCall_ = super.insert(DispatchNodeGen.create());
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = RegexResult.RegexResultGetStartNode.doResult(arg0Value_, arg1Value, this.lazyProfile_, this.getIndicesCall_);
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
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      public static RegexResult.RegexResultGetStartNode create() {
         return new RegexResultFactory.RegexResultGetStartNodeGen();
      }

      public static RegexResult.RegexResultGetStartNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(RegexResult.RegexResultGetStartNode.class)
      @DenyReplace
      private static final class Uncached extends RegexResult.RegexResultGetStartNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public int execute(Object arg0Value, int arg1Value) {
            if (arg0Value instanceof RegexResult) {
               RegexResult arg0Value_ = (RegexResult)arg0Value;
               return RegexResult.RegexResultGetStartNode.doResult(arg0Value_, arg1Value, BranchProfile.getUncached(), DispatchNodeGen.getUncached());
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }
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
