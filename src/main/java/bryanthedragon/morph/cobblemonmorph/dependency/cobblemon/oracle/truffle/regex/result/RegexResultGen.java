package com.oracle.truffle.regex.result;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.regex.AbstractConstantKeysObjectGen;
import com.oracle.truffle.regex.runtime.nodes.ToIntNode;
import com.oracle.truffle.regex.runtime.nodes.ToIntNodeGen;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(RegexResult.class)
final class RegexResultGen {
   private RegexResultGen() {
   }

   static {
      LibraryExport.register(RegexResult.class, new RegexResultGen.InteropLibraryExports());
   }

   @GeneratedBy(RegexResult.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, RegexResult.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof RegexResult;

         InteropLibrary uncached = new RegexResultGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof RegexResult;

         return new RegexResultGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(RegexResult.class)
      private static final class Cached extends AbstractConstantKeysObjectGen.InteropLibraryExports.Cached {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @CompilerDirectives.CompilationFinal
         private RegexResultGen.InteropLibraryExports.Cached.IsMemberReadableCacheIdentityData isMemberReadable_cacheIdentity_cache;
         @CompilerDirectives.CompilationFinal
         private RegexResultGen.InteropLibraryExports.Cached.IsMemberReadableCacheEqualsData isMemberReadable_cacheEquals_cache;
         @CompilerDirectives.CompilationFinal
         private RegexResultGen.InteropLibraryExports.Cached.ReadMemberIsMatchIdentityData readMember_isMatchIdentity_cache;
         @CompilerDirectives.CompilationFinal
         private RegexResultGen.InteropLibraryExports.Cached.ReadMemberIsMatchEqualsData readMember_isMatchEquals_cache;
         @CompilerDirectives.CompilationFinal
         private RegexResultGen.InteropLibraryExports.Cached.ReadMemberGetStartIdentityData readMember_getStartIdentity_cache;
         @CompilerDirectives.CompilationFinal
         private RegexResultGen.InteropLibraryExports.Cached.ReadMemberGetStartEqualsData readMember_getStartEquals_cache;
         @CompilerDirectives.CompilationFinal
         private RegexResultGen.InteropLibraryExports.Cached.ReadMemberGetEndIdentityData readMember_getEndIdentity_cache;
         @CompilerDirectives.CompilationFinal
         private RegexResultGen.InteropLibraryExports.Cached.ReadMemberGetEndEqualsData readMember_getEndEquals_cache;
         @CompilerDirectives.CompilationFinal
         private RegexResultGen.InteropLibraryExports.Cached.ReadMemberLastGroupIdentityData readMember_lastGroupIdentity_cache;
         @CompilerDirectives.CompilationFinal
         private RegexResultGen.InteropLibraryExports.Cached.ReadMemberLastGroupEqualsData readMember_lastGroupEquals_cache;
         @CompilerDirectives.CompilationFinal
         private RegexResultGen.InteropLibraryExports.Cached.IsMemberInvocableCacheIdentityData isMemberInvocable_cacheIdentity_cache;
         @CompilerDirectives.CompilationFinal
         private RegexResultGen.InteropLibraryExports.Cached.IsMemberInvocableCacheEqualsData isMemberInvocable_cacheEquals_cache;
         @Node.Child
         private ToIntNode invokeMemberNode__invokeMember_toIntNode_;
         @Node.Child
         private RegexResult.InvokeCacheNode invokeMemberNode__invokeMember_invokeCache_;

         protected Cached(Object receiver) {
            super(receiver);
         }

         @ExplodeLoop
         @Override
         public boolean isMemberReadable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            RegexResult arg0Value = (RegexResult)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 7) != 0) {
               if ((state_0 & 1) != 0) {
                  for (RegexResultGen.InteropLibraryExports.Cached.IsMemberReadableCacheIdentityData s0_ = this.isMemberReadable_cacheIdentity_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (arg1Value == s0_.cachedSymbol_) {
                        assert s0_.result_;

                        return RegexResult.IsMemberReadable.cacheIdentity(arg0Value, arg1Value, s0_.cachedSymbol_, s0_.result_);
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  for (RegexResultGen.InteropLibraryExports.Cached.IsMemberReadableCacheEqualsData s1_ = this.isMemberReadable_cacheEquals_cache;
                     s1_ != null;
                     s1_ = s1_.next_
                  ) {
                     if (arg1Value.equals(s1_.cachedSymbol_)) {
                        assert s1_.result_;

                        return RegexResult.IsMemberReadable.cacheEquals(arg0Value, arg1Value, s1_.cachedSymbol_, s1_.result_);
                     }
                  }
               }

               if ((state_0 & 4) != 0) {
                  return RegexResult.IsMemberReadable.isReadable(arg0Value, arg1Value);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.isMemberReadableAndSpecialize(arg0Value, arg1Value);
         }

         private boolean isMemberReadableAndSpecialize(RegexResult arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               int exclude = this.exclude_;
               if ((exclude & 1) == 0) {
                  int count0_ = 0;
                  RegexResultGen.InteropLibraryExports.Cached.IsMemberReadableCacheIdentityData s0_ = this.isMemberReadable_cacheIdentity_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null) {
                        if (arg1Value == s0_.cachedSymbol_) {
                           assert s0_.result_;
                           break;
                        }

                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null) {
                     boolean result__ = RegexResult.IsMemberReadable.isReadable(arg0Value, arg1Value);
                     if (result__ && count0_ < 4) {
                        s0_ = new RegexResultGen.InteropLibraryExports.Cached.IsMemberReadableCacheIdentityData(this.isMemberReadable_cacheIdentity_cache);
                        s0_.cachedSymbol_ = arg1Value;
                        s0_.result_ = result__;
                        VarHandle.storeStoreFence();
                        this.isMemberReadable_cacheIdentity_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                     }
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return RegexResult.IsMemberReadable.cacheIdentity(arg0Value, arg1Value, s0_.cachedSymbol_, s0_.result_);
                  }
               }

               if ((exclude & 2) == 0) {
                  int count1_ = 0;
                  RegexResultGen.InteropLibraryExports.Cached.IsMemberReadableCacheEqualsData s1_ = this.isMemberReadable_cacheEquals_cache;
                  if ((state_0 & 2) != 0) {
                     while (s1_ != null) {
                        if (arg1Value.equals(s1_.cachedSymbol_)) {
                           assert s1_.result_;
                           break;
                        }

                        s1_ = s1_.next_;
                        count1_++;
                     }
                  }

                  if (s1_ == null) {
                     boolean result__1 = RegexResult.IsMemberReadable.isReadable(arg0Value, arg1Value);
                     if (result__1 && count1_ < 4) {
                        s1_ = new RegexResultGen.InteropLibraryExports.Cached.IsMemberReadableCacheEqualsData(this.isMemberReadable_cacheEquals_cache);
                        s1_.cachedSymbol_ = arg1Value;
                        s1_.result_ = result__1;
                        VarHandle.storeStoreFence();
                        this.isMemberReadable_cacheEquals_cache = s1_;
                        this.exclude_ = exclude |= 1;
                        this.isMemberReadable_cacheIdentity_cache = null;
                        int var14 = state_0 & -2;
                        this.state_0_ = state_0 = var14 | 2;
                     }
                  }

                  if (s1_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return RegexResult.IsMemberReadable.cacheEquals(arg0Value, arg1Value, s1_.cachedSymbol_, s1_.result_);
                  }
               }

               int var17;
               this.exclude_ = var17 = exclude | 3;
               this.isMemberReadable_cacheIdentity_cache = null;
               this.isMemberReadable_cacheEquals_cache = null;
               state_0 &= -4;
               int var16;
               this.state_0_ = var16 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return RegexResult.IsMemberReadable.isReadable(arg0Value, arg1Value);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public NodeCost getCost() {
            int state_0 = this.state_0_;
            if ((state_0 & 7) == 0) {
               return NodeCost.UNINITIALIZED;
            } else {
               if ((state_0 & 7 & (state_0 & 7) - 1) == 0) {
                  RegexResultGen.InteropLibraryExports.Cached.IsMemberReadableCacheIdentityData s0_ = this.isMemberReadable_cacheIdentity_cache;
                  RegexResultGen.InteropLibraryExports.Cached.IsMemberReadableCacheEqualsData s1_ = this.isMemberReadable_cacheEquals_cache;
                  if ((s0_ == null || s0_.next_ == null) && (s1_ == null || s1_.next_ == null)) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         @ExplodeLoop
         @Override
         public Object readMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            RegexResult arg0Value = (RegexResult)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 4088) != 0) {
               if ((state_0 & 8) != 0) {
                  for (RegexResultGen.InteropLibraryExports.Cached.ReadMemberIsMatchIdentityData s0_ = this.readMember_isMatchIdentity_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (arg1Value == s0_.cachedSymbol_) {
                        assert s0_.cachedSymbol_.equals("isMatch");

                        return RegexResult.ReadMember.isMatchIdentity(arg0Value, arg1Value, s0_.cachedSymbol_);
                     }
                  }
               }

               if ((state_0 & 16) != 0) {
                  for (RegexResultGen.InteropLibraryExports.Cached.ReadMemberIsMatchEqualsData s1_ = this.readMember_isMatchEquals_cache;
                     s1_ != null;
                     s1_ = s1_.next_
                  ) {
                     if (arg1Value.equals(s1_.cachedSymbol_)) {
                        assert s1_.cachedSymbol_.equals("isMatch");

                        return RegexResult.ReadMember.isMatchEquals(arg0Value, arg1Value, s1_.cachedSymbol_);
                     }
                  }
               }

               if ((state_0 & 32) != 0) {
                  for (RegexResultGen.InteropLibraryExports.Cached.ReadMemberGetStartIdentityData s2_ = this.readMember_getStartIdentity_cache;
                     s2_ != null;
                     s2_ = s2_.next_
                  ) {
                     if (arg1Value == s2_.cachedSymbol_) {
                        assert s2_.cachedSymbol_.equals("getStart");

                        return RegexResult.ReadMember.getStartIdentity(arg0Value, arg1Value, s2_.cachedSymbol_);
                     }
                  }
               }

               if ((state_0 & 64) != 0) {
                  for (RegexResultGen.InteropLibraryExports.Cached.ReadMemberGetStartEqualsData s3_ = this.readMember_getStartEquals_cache;
                     s3_ != null;
                     s3_ = s3_.next_
                  ) {
                     if (arg1Value.equals(s3_.cachedSymbol_)) {
                        assert s3_.cachedSymbol_.equals("getStart");

                        return RegexResult.ReadMember.getStartEquals(arg0Value, arg1Value, s3_.cachedSymbol_);
                     }
                  }
               }

               if ((state_0 & 128) != 0) {
                  for (RegexResultGen.InteropLibraryExports.Cached.ReadMemberGetEndIdentityData s4_ = this.readMember_getEndIdentity_cache;
                     s4_ != null;
                     s4_ = s4_.next_
                  ) {
                     if (arg1Value == s4_.cachedSymbol_) {
                        assert s4_.cachedSymbol_.equals("getEnd");

                        return RegexResult.ReadMember.getEndIdentity(arg0Value, arg1Value, s4_.cachedSymbol_);
                     }
                  }
               }

               if ((state_0 & 256) != 0) {
                  for (RegexResultGen.InteropLibraryExports.Cached.ReadMemberGetEndEqualsData s5_ = this.readMember_getEndEquals_cache;
                     s5_ != null;
                     s5_ = s5_.next_
                  ) {
                     if (arg1Value.equals(s5_.cachedSymbol_)) {
                        assert s5_.cachedSymbol_.equals("getEnd");

                        return RegexResult.ReadMember.getEndEquals(arg0Value, arg1Value, s5_.cachedSymbol_);
                     }
                  }
               }

               if ((state_0 & 512) != 0) {
                  for (RegexResultGen.InteropLibraryExports.Cached.ReadMemberLastGroupIdentityData s6_ = this.readMember_lastGroupIdentity_cache;
                     s6_ != null;
                     s6_ = s6_.next_
                  ) {
                     if (arg1Value == s6_.cachedSymbol_) {
                        assert s6_.cachedSymbol_.equals("lastGroup");

                        return RegexResult.ReadMember.lastGroupIdentity(arg0Value, arg1Value, s6_.cachedSymbol_);
                     }
                  }
               }

               if ((state_0 & 1024) != 0) {
                  for (RegexResultGen.InteropLibraryExports.Cached.ReadMemberLastGroupEqualsData s7_ = this.readMember_lastGroupEquals_cache;
                     s7_ != null;
                     s7_ = s7_.next_
                  ) {
                     if (arg1Value.equals(s7_.cachedSymbol_)) {
                        assert s7_.cachedSymbol_.equals("lastGroup");

                        return RegexResult.ReadMember.lastGroupEquals(arg0Value, arg1Value, s7_.cachedSymbol_);
                     }
                  }
               }

               if ((state_0 & 2048) != 0) {
                  return RegexResult.ReadMember.readGeneric(arg0Value, arg1Value);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.readMemberAndSpecialize(arg0Value, arg1Value);
         }

         private Object readMemberAndSpecialize(RegexResult arg0Value, String arg1Value) throws UnknownIdentifierException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               int exclude = this.exclude_;
               int oldState_0 = state_0 & 4088;

               try {
                  if ((exclude & 4) == 0) {
                     int count0_ = 0;
                     RegexResultGen.InteropLibraryExports.Cached.ReadMemberIsMatchIdentityData s0_ = this.readMember_isMatchIdentity_cache;
                     if ((state_0 & 8) != 0) {
                        while (s0_ != null) {
                           if (arg1Value == s0_.cachedSymbol_) {
                              assert s0_.cachedSymbol_.equals("isMatch");
                              break;
                           }

                           s0_ = s0_.next_;
                           count0_++;
                        }
                     }

                     if (s0_ == null && arg1Value.equals("isMatch") && count0_ < 2) {
                        s0_ = new RegexResultGen.InteropLibraryExports.Cached.ReadMemberIsMatchIdentityData(this.readMember_isMatchIdentity_cache);
                        s0_.cachedSymbol_ = arg1Value;
                        VarHandle.storeStoreFence();
                        this.readMember_isMatchIdentity_cache = s0_;
                        this.state_0_ = state_0 |= 8;
                     }

                     if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        return RegexResult.ReadMember.isMatchIdentity(arg0Value, arg1Value, s0_.cachedSymbol_);
                     }
                  }

                  if ((exclude & 8) == 0) {
                     int count1_ = 0;
                     RegexResultGen.InteropLibraryExports.Cached.ReadMemberIsMatchEqualsData s1_ = this.readMember_isMatchEquals_cache;
                     if ((state_0 & 16) != 0) {
                        while (s1_ != null) {
                           if (arg1Value.equals(s1_.cachedSymbol_)) {
                              assert s1_.cachedSymbol_.equals("isMatch");
                              break;
                           }

                           s1_ = s1_.next_;
                           count1_++;
                        }
                     }

                     if (s1_ == null && arg1Value.equals("isMatch") && count1_ < 2) {
                        s1_ = new RegexResultGen.InteropLibraryExports.Cached.ReadMemberIsMatchEqualsData(this.readMember_isMatchEquals_cache);
                        s1_.cachedSymbol_ = arg1Value;
                        VarHandle.storeStoreFence();
                        this.readMember_isMatchEquals_cache = s1_;
                        this.exclude_ = exclude |= 4;
                        this.readMember_isMatchIdentity_cache = null;
                        int var19 = state_0 & -9;
                        this.state_0_ = state_0 = var19 | 16;
                     }

                     if (s1_ != null) {
                        lock.unlock();
                        hasLock = false;
                        return RegexResult.ReadMember.isMatchEquals(arg0Value, arg1Value, s1_.cachedSymbol_);
                     }
                  }

                  if ((exclude & 16) == 0) {
                     int count2_ = 0;
                     RegexResultGen.InteropLibraryExports.Cached.ReadMemberGetStartIdentityData s2_ = this.readMember_getStartIdentity_cache;
                     if ((state_0 & 32) != 0) {
                        while (s2_ != null) {
                           if (arg1Value == s2_.cachedSymbol_) {
                              assert s2_.cachedSymbol_.equals("getStart");
                              break;
                           }

                           s2_ = s2_.next_;
                           count2_++;
                        }
                     }

                     if (s2_ == null && arg1Value.equals("getStart") && count2_ < 2) {
                        s2_ = new RegexResultGen.InteropLibraryExports.Cached.ReadMemberGetStartIdentityData(this.readMember_getStartIdentity_cache);
                        s2_.cachedSymbol_ = arg1Value;
                        VarHandle.storeStoreFence();
                        this.readMember_getStartIdentity_cache = s2_;
                        this.state_0_ = state_0 |= 32;
                     }

                     if (s2_ != null) {
                        lock.unlock();
                        hasLock = false;
                        return RegexResult.ReadMember.getStartIdentity(arg0Value, arg1Value, s2_.cachedSymbol_);
                     }
                  }

                  if ((exclude & 32) == 0) {
                     int count3_ = 0;
                     RegexResultGen.InteropLibraryExports.Cached.ReadMemberGetStartEqualsData s3_ = this.readMember_getStartEquals_cache;
                     if ((state_0 & 64) != 0) {
                        while (s3_ != null) {
                           if (arg1Value.equals(s3_.cachedSymbol_)) {
                              assert s3_.cachedSymbol_.equals("getStart");
                              break;
                           }

                           s3_ = s3_.next_;
                           count3_++;
                        }
                     }

                     if (s3_ == null && arg1Value.equals("getStart") && count3_ < 2) {
                        s3_ = new RegexResultGen.InteropLibraryExports.Cached.ReadMemberGetStartEqualsData(this.readMember_getStartEquals_cache);
                        s3_.cachedSymbol_ = arg1Value;
                        VarHandle.storeStoreFence();
                        this.readMember_getStartEquals_cache = s3_;
                        this.exclude_ = exclude |= 16;
                        this.readMember_getStartIdentity_cache = null;
                        int var20 = state_0 & -33;
                        this.state_0_ = state_0 = var20 | 64;
                     }

                     if (s3_ != null) {
                        lock.unlock();
                        hasLock = false;
                        return RegexResult.ReadMember.getStartEquals(arg0Value, arg1Value, s3_.cachedSymbol_);
                     }
                  }

                  if ((exclude & 64) == 0) {
                     int count4_ = 0;
                     RegexResultGen.InteropLibraryExports.Cached.ReadMemberGetEndIdentityData s4_ = this.readMember_getEndIdentity_cache;
                     if ((state_0 & 128) != 0) {
                        while (s4_ != null) {
                           if (arg1Value == s4_.cachedSymbol_) {
                              assert s4_.cachedSymbol_.equals("getEnd");
                              break;
                           }

                           s4_ = s4_.next_;
                           count4_++;
                        }
                     }

                     if (s4_ == null && arg1Value.equals("getEnd") && count4_ < 2) {
                        s4_ = new RegexResultGen.InteropLibraryExports.Cached.ReadMemberGetEndIdentityData(this.readMember_getEndIdentity_cache);
                        s4_.cachedSymbol_ = arg1Value;
                        VarHandle.storeStoreFence();
                        this.readMember_getEndIdentity_cache = s4_;
                        this.state_0_ = state_0 |= 128;
                     }

                     if (s4_ != null) {
                        lock.unlock();
                        hasLock = false;
                        return RegexResult.ReadMember.getEndIdentity(arg0Value, arg1Value, s4_.cachedSymbol_);
                     }
                  }

                  if ((exclude & 128) == 0) {
                     int count5_ = 0;
                     RegexResultGen.InteropLibraryExports.Cached.ReadMemberGetEndEqualsData s5_ = this.readMember_getEndEquals_cache;
                     if ((state_0 & 256) != 0) {
                        while (s5_ != null) {
                           if (arg1Value.equals(s5_.cachedSymbol_)) {
                              assert s5_.cachedSymbol_.equals("getEnd");
                              break;
                           }

                           s5_ = s5_.next_;
                           count5_++;
                        }
                     }

                     if (s5_ == null && arg1Value.equals("getEnd") && count5_ < 2) {
                        s5_ = new RegexResultGen.InteropLibraryExports.Cached.ReadMemberGetEndEqualsData(this.readMember_getEndEquals_cache);
                        s5_.cachedSymbol_ = arg1Value;
                        VarHandle.storeStoreFence();
                        this.readMember_getEndEquals_cache = s5_;
                        this.exclude_ = exclude |= 64;
                        this.readMember_getEndIdentity_cache = null;
                        int var21 = state_0 & -129;
                        this.state_0_ = state_0 = var21 | 256;
                     }

                     if (s5_ != null) {
                        lock.unlock();
                        hasLock = false;
                        return RegexResult.ReadMember.getEndEquals(arg0Value, arg1Value, s5_.cachedSymbol_);
                     }
                  }

                  if ((exclude & 256) == 0) {
                     int count6_ = 0;
                     RegexResultGen.InteropLibraryExports.Cached.ReadMemberLastGroupIdentityData s6_ = this.readMember_lastGroupIdentity_cache;
                     if ((state_0 & 512) != 0) {
                        while (s6_ != null) {
                           if (arg1Value == s6_.cachedSymbol_) {
                              assert s6_.cachedSymbol_.equals("lastGroup");
                              break;
                           }

                           s6_ = s6_.next_;
                           count6_++;
                        }
                     }

                     if (s6_ == null && arg1Value.equals("lastGroup") && count6_ < 2) {
                        s6_ = new RegexResultGen.InteropLibraryExports.Cached.ReadMemberLastGroupIdentityData(this.readMember_lastGroupIdentity_cache);
                        s6_.cachedSymbol_ = arg1Value;
                        VarHandle.storeStoreFence();
                        this.readMember_lastGroupIdentity_cache = s6_;
                        this.state_0_ = state_0 |= 512;
                     }

                     if (s6_ != null) {
                        lock.unlock();
                        hasLock = false;
                        return RegexResult.ReadMember.lastGroupIdentity(arg0Value, arg1Value, s6_.cachedSymbol_);
                     }
                  }

                  int count7_ = 0;
                  RegexResultGen.InteropLibraryExports.Cached.ReadMemberLastGroupEqualsData s7_ = this.readMember_lastGroupEquals_cache;
                  if ((state_0 & 1024) != 0) {
                     while (s7_ != null) {
                        if (arg1Value.equals(s7_.cachedSymbol_)) {
                           assert s7_.cachedSymbol_.equals("lastGroup");
                           break;
                        }

                        s7_ = s7_.next_;
                        count7_++;
                     }
                  }

                  if (s7_ == null && arg1Value.equals("lastGroup") && count7_ < 2) {
                     s7_ = new RegexResultGen.InteropLibraryExports.Cached.ReadMemberLastGroupEqualsData(this.readMember_lastGroupEquals_cache);
                     s7_.cachedSymbol_ = arg1Value;
                     VarHandle.storeStoreFence();
                     this.readMember_lastGroupEquals_cache = s7_;
                     this.exclude_ = exclude |= 256;
                     this.readMember_lastGroupIdentity_cache = null;
                     int var22 = state_0 & -513;
                     this.state_0_ = state_0 = var22 | 1024;
                  }

                  if (s7_ == null) {
                     int var25;
                     this.exclude_ = var25 = exclude | 252;
                     this.readMember_isMatchIdentity_cache = null;
                     this.readMember_isMatchEquals_cache = null;
                     this.readMember_getStartIdentity_cache = null;
                     this.readMember_getStartEquals_cache = null;
                     this.readMember_getEndIdentity_cache = null;
                     this.readMember_getEndEquals_cache = null;
                     state_0 &= -505;
                     int var24;
                     this.state_0_ = var24 = state_0 | 2048;
                     lock.unlock();
                     hasLock = false;
                     return RegexResult.ReadMember.readGeneric(arg0Value, arg1Value);
                  } else {
                     lock.unlock();
                     hasLock = false;
                     return RegexResult.ReadMember.lastGroupEquals(arg0Value, arg1Value, s7_.cachedSymbol_);
                  }
               } finally {
                  if (oldState_0 != 0) {
                     this.readMember_checkForPolymorphicSpecialize(oldState_0);
                  }
               }
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         private void readMember_checkForPolymorphicSpecialize(int oldState_0) {
            if ((oldState_0 & 2048) == 0 && (this.state_0_ & 2048) != 0) {
               this.reportPolymorphicSpecialize();
            }
         }

         @ExplodeLoop
         @Override
         public boolean isMemberInvocable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            RegexResult arg0Value = (RegexResult)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 28672) != 0) {
               if ((state_0 & 4096) != 0) {
                  for (RegexResultGen.InteropLibraryExports.Cached.IsMemberInvocableCacheIdentityData s0_ = this.isMemberInvocable_cacheIdentity_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (arg1Value == s0_.cachedSymbol_) {
                        assert s0_.result_;

                        return RegexResult.IsMemberInvocable.cacheIdentity(arg0Value, arg1Value, s0_.cachedSymbol_, s0_.result_);
                     }
                  }
               }

               if ((state_0 & 8192) != 0) {
                  for (RegexResultGen.InteropLibraryExports.Cached.IsMemberInvocableCacheEqualsData s1_ = this.isMemberInvocable_cacheEquals_cache;
                     s1_ != null;
                     s1_ = s1_.next_
                  ) {
                     if (arg1Value.equals(s1_.cachedSymbol_)) {
                        assert s1_.result_;

                        return RegexResult.IsMemberInvocable.cacheEquals(arg0Value, arg1Value, s1_.cachedSymbol_, s1_.result_);
                     }
                  }
               }

               if ((state_0 & 16384) != 0) {
                  return RegexResult.IsMemberInvocable.isInvocable(arg0Value, arg1Value);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.isMemberInvocableAndSpecialize(arg0Value, arg1Value);
         }

         private boolean isMemberInvocableAndSpecialize(RegexResult arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               int exclude = this.exclude_;
               if ((exclude & 512) == 0) {
                  int count0_ = 0;
                  RegexResultGen.InteropLibraryExports.Cached.IsMemberInvocableCacheIdentityData s0_ = this.isMemberInvocable_cacheIdentity_cache;
                  if ((state_0 & 4096) != 0) {
                     while (s0_ != null) {
                        if (arg1Value == s0_.cachedSymbol_) {
                           assert s0_.result_;
                           break;
                        }

                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null) {
                     boolean result__ = RegexResult.IsMemberInvocable.isInvocable(arg0Value, arg1Value);
                     if (result__ && count0_ < 2) {
                        s0_ = new RegexResultGen.InteropLibraryExports.Cached.IsMemberInvocableCacheIdentityData(this.isMemberInvocable_cacheIdentity_cache);
                        s0_.cachedSymbol_ = arg1Value;
                        s0_.result_ = result__;
                        VarHandle.storeStoreFence();
                        this.isMemberInvocable_cacheIdentity_cache = s0_;
                        this.state_0_ = state_0 |= 4096;
                     }
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return RegexResult.IsMemberInvocable.cacheIdentity(arg0Value, arg1Value, s0_.cachedSymbol_, s0_.result_);
                  }
               }

               if ((exclude & 1024) == 0) {
                  int count1_ = 0;
                  RegexResultGen.InteropLibraryExports.Cached.IsMemberInvocableCacheEqualsData s1_ = this.isMemberInvocable_cacheEquals_cache;
                  if ((state_0 & 8192) != 0) {
                     while (s1_ != null) {
                        if (arg1Value.equals(s1_.cachedSymbol_)) {
                           assert s1_.result_;
                           break;
                        }

                        s1_ = s1_.next_;
                        count1_++;
                     }
                  }

                  if (s1_ == null) {
                     boolean result__1 = RegexResult.IsMemberInvocable.isInvocable(arg0Value, arg1Value);
                     if (result__1 && count1_ < 2) {
                        s1_ = new RegexResultGen.InteropLibraryExports.Cached.IsMemberInvocableCacheEqualsData(this.isMemberInvocable_cacheEquals_cache);
                        s1_.cachedSymbol_ = arg1Value;
                        s1_.result_ = result__1;
                        VarHandle.storeStoreFence();
                        this.isMemberInvocable_cacheEquals_cache = s1_;
                        this.exclude_ = exclude |= 512;
                        this.isMemberInvocable_cacheIdentity_cache = null;
                        int var14 = state_0 & -4097;
                        this.state_0_ = state_0 = var14 | 8192;
                     }
                  }

                  if (s1_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return RegexResult.IsMemberInvocable.cacheEquals(arg0Value, arg1Value, s1_.cachedSymbol_, s1_.result_);
                  }
               }

               int var17;
               this.exclude_ = var17 = exclude | 1536;
               this.isMemberInvocable_cacheIdentity_cache = null;
               this.isMemberInvocable_cacheEquals_cache = null;
               state_0 &= -12289;
               int var16;
               this.state_0_ = var16 = state_0 | 16384;
               lock.unlock();
               hasLock = false;
               return RegexResult.IsMemberInvocable.isInvocable(arg0Value, arg1Value);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((RegexResult)receiver).getMembers(includeInternal);
         }

         @Override
         public Object invokeMember(Object arg0Value_, String arg1Value, Object... arg2Value) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            RegexResult arg0Value = (RegexResult)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 32768) != 0) {
               return arg0Value.invokeMember(
                  arg1Value, arg2Value, this.invokeMemberNode__invokeMember_toIntNode_, this.invokeMemberNode__invokeMember_invokeCache_
               );
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.invokeMemberNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }
         }

         private Object invokeMemberNode_AndSpecialize(RegexResult arg0Value, String arg1Value, Object[] arg2Value) throws UnknownIdentifierException, ArityException, UnsupportedTypeException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var7;
            try {
               int state_0 = this.state_0_;
               this.invokeMemberNode__invokeMember_toIntNode_ = super.insert(ToIntNode.create());
               this.invokeMemberNode__invokeMember_invokeCache_ = super.insert(RegexResultFactory.InvokeCacheNodeGen.create());
               int var11;
               this.state_0_ = var11 = state_0 | 32768;
               lock.unlock();
               hasLock = false;
               var7 = arg0Value.invokeMember(
                  arg1Value, arg2Value, this.invokeMemberNode__invokeMember_toIntNode_, this.invokeMemberNode__invokeMember_invokeCache_
               );
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((RegexResult)receiver).toDisplayString(allowSideEffects);
         }

         @GeneratedBy(RegexResult.class)
         private static final class IsMemberInvocableCacheEqualsData {
            @CompilerDirectives.CompilationFinal
            RegexResultGen.InteropLibraryExports.Cached.IsMemberInvocableCacheEqualsData next_;
            @CompilerDirectives.CompilationFinal
            String cachedSymbol_;
            @CompilerDirectives.CompilationFinal
            boolean result_;

            IsMemberInvocableCacheEqualsData(RegexResultGen.InteropLibraryExports.Cached.IsMemberInvocableCacheEqualsData next_) {
               this.next_ = next_;
            }
         }

         @GeneratedBy(RegexResult.class)
         private static final class IsMemberInvocableCacheIdentityData {
            @CompilerDirectives.CompilationFinal
            RegexResultGen.InteropLibraryExports.Cached.IsMemberInvocableCacheIdentityData next_;
            @CompilerDirectives.CompilationFinal
            String cachedSymbol_;
            @CompilerDirectives.CompilationFinal
            boolean result_;

            IsMemberInvocableCacheIdentityData(RegexResultGen.InteropLibraryExports.Cached.IsMemberInvocableCacheIdentityData next_) {
               this.next_ = next_;
            }
         }

         @GeneratedBy(RegexResult.class)
         private static final class IsMemberReadableCacheEqualsData {
            @CompilerDirectives.CompilationFinal
            RegexResultGen.InteropLibraryExports.Cached.IsMemberReadableCacheEqualsData next_;
            @CompilerDirectives.CompilationFinal
            String cachedSymbol_;
            @CompilerDirectives.CompilationFinal
            boolean result_;

            IsMemberReadableCacheEqualsData(RegexResultGen.InteropLibraryExports.Cached.IsMemberReadableCacheEqualsData next_) {
               this.next_ = next_;
            }
         }

         @GeneratedBy(RegexResult.class)
         private static final class IsMemberReadableCacheIdentityData {
            @CompilerDirectives.CompilationFinal
            RegexResultGen.InteropLibraryExports.Cached.IsMemberReadableCacheIdentityData next_;
            @CompilerDirectives.CompilationFinal
            String cachedSymbol_;
            @CompilerDirectives.CompilationFinal
            boolean result_;

            IsMemberReadableCacheIdentityData(RegexResultGen.InteropLibraryExports.Cached.IsMemberReadableCacheIdentityData next_) {
               this.next_ = next_;
            }
         }

         @GeneratedBy(RegexResult.class)
         private static final class ReadMemberGetEndEqualsData {
            @CompilerDirectives.CompilationFinal
            RegexResultGen.InteropLibraryExports.Cached.ReadMemberGetEndEqualsData next_;
            @CompilerDirectives.CompilationFinal
            String cachedSymbol_;

            ReadMemberGetEndEqualsData(RegexResultGen.InteropLibraryExports.Cached.ReadMemberGetEndEqualsData next_) {
               this.next_ = next_;
            }
         }

         @GeneratedBy(RegexResult.class)
         private static final class ReadMemberGetEndIdentityData {
            @CompilerDirectives.CompilationFinal
            RegexResultGen.InteropLibraryExports.Cached.ReadMemberGetEndIdentityData next_;
            @CompilerDirectives.CompilationFinal
            String cachedSymbol_;

            ReadMemberGetEndIdentityData(RegexResultGen.InteropLibraryExports.Cached.ReadMemberGetEndIdentityData next_) {
               this.next_ = next_;
            }
         }

         @GeneratedBy(RegexResult.class)
         private static final class ReadMemberGetStartEqualsData {
            @CompilerDirectives.CompilationFinal
            RegexResultGen.InteropLibraryExports.Cached.ReadMemberGetStartEqualsData next_;
            @CompilerDirectives.CompilationFinal
            String cachedSymbol_;

            ReadMemberGetStartEqualsData(RegexResultGen.InteropLibraryExports.Cached.ReadMemberGetStartEqualsData next_) {
               this.next_ = next_;
            }
         }

         @GeneratedBy(RegexResult.class)
         private static final class ReadMemberGetStartIdentityData {
            @CompilerDirectives.CompilationFinal
            RegexResultGen.InteropLibraryExports.Cached.ReadMemberGetStartIdentityData next_;
            @CompilerDirectives.CompilationFinal
            String cachedSymbol_;

            ReadMemberGetStartIdentityData(RegexResultGen.InteropLibraryExports.Cached.ReadMemberGetStartIdentityData next_) {
               this.next_ = next_;
            }
         }

         @GeneratedBy(RegexResult.class)
         private static final class ReadMemberIsMatchEqualsData {
            @CompilerDirectives.CompilationFinal
            RegexResultGen.InteropLibraryExports.Cached.ReadMemberIsMatchEqualsData next_;
            @CompilerDirectives.CompilationFinal
            String cachedSymbol_;

            ReadMemberIsMatchEqualsData(RegexResultGen.InteropLibraryExports.Cached.ReadMemberIsMatchEqualsData next_) {
               this.next_ = next_;
            }
         }

         @GeneratedBy(RegexResult.class)
         private static final class ReadMemberIsMatchIdentityData {
            @CompilerDirectives.CompilationFinal
            RegexResultGen.InteropLibraryExports.Cached.ReadMemberIsMatchIdentityData next_;
            @CompilerDirectives.CompilationFinal
            String cachedSymbol_;

            ReadMemberIsMatchIdentityData(RegexResultGen.InteropLibraryExports.Cached.ReadMemberIsMatchIdentityData next_) {
               this.next_ = next_;
            }
         }

         @GeneratedBy(RegexResult.class)
         private static final class ReadMemberLastGroupEqualsData {
            @CompilerDirectives.CompilationFinal
            RegexResultGen.InteropLibraryExports.Cached.ReadMemberLastGroupEqualsData next_;
            @CompilerDirectives.CompilationFinal
            String cachedSymbol_;

            ReadMemberLastGroupEqualsData(RegexResultGen.InteropLibraryExports.Cached.ReadMemberLastGroupEqualsData next_) {
               this.next_ = next_;
            }
         }

         @GeneratedBy(RegexResult.class)
         private static final class ReadMemberLastGroupIdentityData {
            @CompilerDirectives.CompilationFinal
            RegexResultGen.InteropLibraryExports.Cached.ReadMemberLastGroupIdentityData next_;
            @CompilerDirectives.CompilationFinal
            String cachedSymbol_;

            ReadMemberLastGroupIdentityData(RegexResultGen.InteropLibraryExports.Cached.ReadMemberLastGroupIdentityData next_) {
               this.next_ = next_;
            }
         }
      }

      @GeneratedBy(RegexResult.class)
      @DenyReplace
      private static final class Uncached extends AbstractConstantKeysObjectGen.InteropLibraryExports.Uncached {
         protected Uncached(Object receiver) {
            super(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            return super.accepts(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberReadable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            RegexResult arg0Value = (RegexResult)arg0Value_;
            return RegexResult.IsMemberReadable.isReadable(arg0Value, arg1Value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readMember(Object arg0Value_, String arg1Value) throws UnknownIdentifierException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            RegexResult arg0Value = (RegexResult)arg0Value_;
            return arg1Value.equals("lastGroup")
               ? RegexResult.ReadMember.lastGroupEquals(arg0Value, arg1Value, arg1Value)
               : RegexResult.ReadMember.readGeneric(arg0Value, arg1Value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberInvocable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            RegexResult arg0Value = (RegexResult)arg0Value_;
            return RegexResult.IsMemberInvocable.isInvocable(arg0Value, arg1Value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((RegexResult)receiver).getMembers(includeInternal);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object invokeMember(Object arg0Value_, String arg1Value, Object... arg2Value) throws UnknownIdentifierException, ArityException, UnsupportedTypeException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            RegexResult arg0Value = (RegexResult)arg0Value_;
            return arg0Value.invokeMember(arg1Value, arg2Value, ToIntNodeGen.getUncached(), RegexResultFactory.InvokeCacheNodeGen.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((RegexResult)receiver).toDisplayString(allowSideEffects);
         }
      }
   }
}
