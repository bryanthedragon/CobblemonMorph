package com.oracle.truffle.regex;

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
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.regex.runtime.nodes.ToLongNode;
import com.oracle.truffle.regex.runtime.nodes.ToLongNodeGen;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(RegexObject.class)
final class RegexObjectGen {
   private RegexObjectGen() {
   }

   static {
      LibraryExport.register(RegexObject.class, new RegexObjectGen.InteropLibraryExports());
   }

   @GeneratedBy(RegexObject.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, RegexObject.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof RegexObject;

         InteropLibrary uncached = new RegexObjectGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof RegexObject;

         return new RegexObjectGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(RegexObject.class)
      private static final class Cached extends AbstractConstantKeysObjectGen.InteropLibraryExports.Cached {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @CompilerDirectives.CompilationFinal
         private ValueProfile classProfile;
         @CompilerDirectives.CompilationFinal
         private RegexObjectGen.InteropLibraryExports.Cached.IsMemberInvocableCacheIdentityData isMemberInvocable_cacheIdentity_cache;
         @CompilerDirectives.CompilationFinal
         private RegexObjectGen.InteropLibraryExports.Cached.IsMemberInvocableCacheEqualsData isMemberInvocable_cacheEquals_cache;
         @Node.Child
         private ToLongNode invokeMemberNode__invokeMember_toLongNode_;
         @Node.Child
         private RegexObject.InvokeCacheNode invokeMemberNode__invokeMember_invokeCache_;

         protected Cached(Object receiver) {
            super(receiver);
         }

         @ExplodeLoop
         @Override
         public boolean isMemberInvocable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            RegexObject arg0Value = (RegexObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 7) != 0) {
               if ((state_0 & 1) != 0) {
                  for (RegexObjectGen.InteropLibraryExports.Cached.IsMemberInvocableCacheIdentityData s0_ = this.isMemberInvocable_cacheIdentity_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (arg1Value == s0_.cachedSymbol_) {
                        assert s0_.result_;

                        return RegexObject.IsMemberInvocable.cacheIdentity(arg0Value, arg1Value, s0_.cachedSymbol_, s0_.result_);
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  for (RegexObjectGen.InteropLibraryExports.Cached.IsMemberInvocableCacheEqualsData s1_ = this.isMemberInvocable_cacheEquals_cache;
                     s1_ != null;
                     s1_ = s1_.next_
                  ) {
                     if (arg1Value.equals(s1_.cachedSymbol_)) {
                        assert s1_.result_;

                        return RegexObject.IsMemberInvocable.cacheEquals(arg0Value, arg1Value, s1_.cachedSymbol_, s1_.result_);
                     }
                  }
               }

               if ((state_0 & 4) != 0) {
                  return RegexObject.IsMemberInvocable.isInvocable(arg0Value, arg1Value);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.isMemberInvocableAndSpecialize(arg0Value, arg1Value);
         }

         private boolean isMemberInvocableAndSpecialize(RegexObject arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               int exclude = this.exclude_;
               if ((exclude & 1) == 0) {
                  int count0_ = 0;
                  RegexObjectGen.InteropLibraryExports.Cached.IsMemberInvocableCacheIdentityData s0_ = this.isMemberInvocable_cacheIdentity_cache;
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
                     boolean result__ = RegexObject.IsMemberInvocable.isInvocable(arg0Value, arg1Value);
                     if (result__ && count0_ < 3) {
                        s0_ = new RegexObjectGen.InteropLibraryExports.Cached.IsMemberInvocableCacheIdentityData(this.isMemberInvocable_cacheIdentity_cache);
                        s0_.cachedSymbol_ = arg1Value;
                        s0_.result_ = result__;
                        VarHandle.storeStoreFence();
                        this.isMemberInvocable_cacheIdentity_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                     }
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return RegexObject.IsMemberInvocable.cacheIdentity(arg0Value, arg1Value, s0_.cachedSymbol_, s0_.result_);
                  }
               }

               if ((exclude & 2) == 0) {
                  int count1_ = 0;
                  RegexObjectGen.InteropLibraryExports.Cached.IsMemberInvocableCacheEqualsData s1_ = this.isMemberInvocable_cacheEquals_cache;
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
                     boolean result__1 = RegexObject.IsMemberInvocable.isInvocable(arg0Value, arg1Value);
                     if (result__1 && count1_ < 3) {
                        s1_ = new RegexObjectGen.InteropLibraryExports.Cached.IsMemberInvocableCacheEqualsData(this.isMemberInvocable_cacheEquals_cache);
                        s1_.cachedSymbol_ = arg1Value;
                        s1_.result_ = result__1;
                        VarHandle.storeStoreFence();
                        this.isMemberInvocable_cacheEquals_cache = s1_;
                        this.exclude_ = exclude |= 1;
                        this.isMemberInvocable_cacheIdentity_cache = null;
                        int var14 = state_0 & -2;
                        this.state_0_ = state_0 = var14 | 2;
                     }
                  }

                  if (s1_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return RegexObject.IsMemberInvocable.cacheEquals(arg0Value, arg1Value, s1_.cachedSymbol_, s1_.result_);
                  }
               }

               int var17;
               this.exclude_ = var17 = exclude | 3;
               this.isMemberInvocable_cacheIdentity_cache = null;
               this.isMemberInvocable_cacheEquals_cache = null;
               state_0 &= -4;
               int var16;
               this.state_0_ = var16 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return RegexObject.IsMemberInvocable.isInvocable(arg0Value, arg1Value);
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
                  RegexObjectGen.InteropLibraryExports.Cached.IsMemberInvocableCacheIdentityData s0_ = this.isMemberInvocable_cacheIdentity_cache;
                  RegexObjectGen.InteropLibraryExports.Cached.IsMemberInvocableCacheEqualsData s1_ = this.isMemberInvocable_cacheEquals_cache;
                  if ((s0_ == null || s0_.next_ == null) && (s1_ == null || s1_.next_ == null)) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         @Override
         public Object invokeMember(Object arg0Value_, String arg1Value, Object... arg2Value) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            RegexObject arg0Value = (RegexObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 8) != 0) {
               return arg0Value.invokeMember(
                  arg1Value, arg2Value, this.invokeMemberNode__invokeMember_toLongNode_, this.invokeMemberNode__invokeMember_invokeCache_
               );
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.invokeMemberNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }
         }

         private Object invokeMemberNode_AndSpecialize(RegexObject arg0Value, String arg1Value, Object[] arg2Value) throws UnknownIdentifierException, ArityException, UnsupportedTypeException, UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var7;
            try {
               int state_0 = this.state_0_;
               this.invokeMemberNode__invokeMember_toLongNode_ = super.insert(ToLongNode.create());
               this.invokeMemberNode__invokeMember_invokeCache_ = super.insert(RegexObjectFactory.InvokeCacheNodeGen.create());
               int var11;
               this.state_0_ = var11 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               var7 = arg0Value.invokeMember(
                  arg1Value, arg2Value, this.invokeMemberNode__invokeMember_toLongNode_, this.invokeMemberNode__invokeMember_invokeCache_
               );
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @GeneratedBy(RegexObject.class)
         private static final class IsMemberInvocableCacheEqualsData {
            @CompilerDirectives.CompilationFinal
            RegexObjectGen.InteropLibraryExports.Cached.IsMemberInvocableCacheEqualsData next_;
            @CompilerDirectives.CompilationFinal
            String cachedSymbol_;
            @CompilerDirectives.CompilationFinal
            boolean result_;

            IsMemberInvocableCacheEqualsData(RegexObjectGen.InteropLibraryExports.Cached.IsMemberInvocableCacheEqualsData next_) {
               this.next_ = next_;
            }
         }

         @GeneratedBy(RegexObject.class)
         private static final class IsMemberInvocableCacheIdentityData {
            @CompilerDirectives.CompilationFinal
            RegexObjectGen.InteropLibraryExports.Cached.IsMemberInvocableCacheIdentityData next_;
            @CompilerDirectives.CompilationFinal
            String cachedSymbol_;
            @CompilerDirectives.CompilationFinal
            boolean result_;

            IsMemberInvocableCacheIdentityData(RegexObjectGen.InteropLibraryExports.Cached.IsMemberInvocableCacheIdentityData next_) {
               this.next_ = next_;
            }
         }
      }

      @GeneratedBy(RegexObject.class)
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
         public boolean isMemberInvocable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            RegexObject arg0Value = (RegexObject)arg0Value_;
            return RegexObject.IsMemberInvocable.isInvocable(arg0Value, arg1Value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object invokeMember(Object arg0Value_, String arg1Value, Object... arg2Value) throws UnknownIdentifierException, ArityException, UnsupportedTypeException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            RegexObject arg0Value = (RegexObject)arg0Value_;
            return arg0Value.invokeMember(arg1Value, arg2Value, ToLongNodeGen.getUncached(), RegexObjectFactory.InvokeCacheNodeGen.getUncached());
         }
      }
   }
}
