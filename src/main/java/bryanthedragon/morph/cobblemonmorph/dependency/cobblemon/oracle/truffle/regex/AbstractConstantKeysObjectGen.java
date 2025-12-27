package com.oracle.truffle.regex;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ValueProfile;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(AbstractConstantKeysObject.class)
public final class AbstractConstantKeysObjectGen {
   private AbstractConstantKeysObjectGen() {
   }

   static {
      LibraryExport.register(AbstractConstantKeysObject.class, new AbstractConstantKeysObjectGen.InteropLibraryExports());
   }

   @GeneratedBy(AbstractConstantKeysObject.class)
   public static class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, AbstractConstantKeysObject.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof AbstractConstantKeysObject;

         InteropLibrary uncached = new AbstractConstantKeysObjectGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof AbstractConstantKeysObject;

         return new AbstractConstantKeysObjectGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(AbstractConstantKeysObject.class)
      public static class Cached extends AbstractRegexObjectGen.InteropLibraryExports.Cached {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @CompilerDirectives.CompilationFinal
         private ValueProfile classProfile;
         @CompilerDirectives.CompilationFinal
         private AbstractConstantKeysObjectGen.InteropLibraryExports.Cached.IsMemberReadableCacheIdentityData isMemberReadable_cacheIdentity_cache;
         @CompilerDirectives.CompilationFinal
         private AbstractConstantKeysObjectGen.InteropLibraryExports.Cached.IsMemberReadableCacheEqualsData isMemberReadable_cacheEquals_cache;
         @CompilerDirectives.CompilationFinal
         private AbstractConstantKeysObjectGen.InteropLibraryExports.Cached.ReadMemberReadIdentityData readMember_readIdentity_cache;
         @CompilerDirectives.CompilationFinal
         private AbstractConstantKeysObjectGen.InteropLibraryExports.Cached.ReadMemberReadEqualsData readMember_readEquals_cache;

         protected Cached(Object receiver) {
            super(receiver);
         }

         @ExplodeLoop
         @Override
         public boolean isMemberReadable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            AbstractConstantKeysObject arg0Value = (AbstractConstantKeysObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 7) != 0) {
               if ((state_0 & 1) != 0) {
                  for (AbstractConstantKeysObjectGen.InteropLibraryExports.Cached.IsMemberReadableCacheIdentityData s0_ = this.isMemberReadable_cacheIdentity_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (arg1Value == s0_.cachedSymbol_ && CompilerDirectives.isExact(arg0Value, s0_.cachedClass_)) {
                        assert s0_.result_;

                        return AbstractConstantKeysObject.IsMemberReadable.cacheIdentity(arg0Value, arg1Value, s0_.cachedSymbol_, s0_.cachedClass_, s0_.result_);
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  for (AbstractConstantKeysObjectGen.InteropLibraryExports.Cached.IsMemberReadableCacheEqualsData s1_ = this.isMemberReadable_cacheEquals_cache;
                     s1_ != null;
                     s1_ = s1_.next_
                  ) {
                     if (arg1Value.equals(s1_.cachedSymbol_) && CompilerDirectives.isExact(arg0Value, s1_.cachedClass_)) {
                        assert s1_.result_;

                        return AbstractConstantKeysObject.IsMemberReadable.cacheEquals(arg0Value, arg1Value, s1_.cachedSymbol_, s1_.cachedClass_, s1_.result_);
                     }
                  }
               }

               if ((state_0 & 4) != 0) {
                  return AbstractConstantKeysObject.IsMemberReadable.isReadable(arg0Value, arg1Value, this.classProfile);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.isMemberReadableAndSpecialize(arg0Value, arg1Value);
         }

         private boolean isMemberReadableAndSpecialize(AbstractConstantKeysObject arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               int exclude = this.exclude_;
               if ((exclude & 1) == 0) {
                  int count0_ = 0;
                  AbstractConstantKeysObjectGen.InteropLibraryExports.Cached.IsMemberReadableCacheIdentityData s0_ = this.isMemberReadable_cacheIdentity_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null) {
                        if (arg1Value == s0_.cachedSymbol_ && CompilerDirectives.isExact(arg0Value, s0_.cachedClass_)) {
                           assert s0_.result_;
                           break;
                        }

                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null) {
                     Class<?> cachedClass__ = arg0Value.getClass();
                     if (CompilerDirectives.isExact(arg0Value, cachedClass__)) {
                        boolean result__ = arg0Value.isMemberReadableImpl(arg1Value);
                        if (result__ && count0_ < 8) {
                           s0_ = new AbstractConstantKeysObjectGen.InteropLibraryExports.Cached.IsMemberReadableCacheIdentityData(
                              this.isMemberReadable_cacheIdentity_cache
                           );
                           s0_.cachedSymbol_ = arg1Value;
                           s0_.cachedClass_ = cachedClass__;
                           s0_.result_ = result__;
                           VarHandle.storeStoreFence();
                           this.isMemberReadable_cacheIdentity_cache = s0_;
                           this.state_0_ = state_0 |= 1;
                        }
                     }
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return AbstractConstantKeysObject.IsMemberReadable.cacheIdentity(arg0Value, arg1Value, s0_.cachedSymbol_, s0_.cachedClass_, s0_.result_);
                  }
               }

               if ((exclude & 2) == 0) {
                  int count1_ = 0;
                  AbstractConstantKeysObjectGen.InteropLibraryExports.Cached.IsMemberReadableCacheEqualsData s1_ = this.isMemberReadable_cacheEquals_cache;
                  if ((state_0 & 2) != 0) {
                     while (s1_ != null) {
                        if (arg1Value.equals(s1_.cachedSymbol_) && CompilerDirectives.isExact(arg0Value, s1_.cachedClass_)) {
                           assert s1_.result_;
                           break;
                        }

                        s1_ = s1_.next_;
                        count1_++;
                     }
                  }

                  if (s1_ == null) {
                     Class<?> cachedClass__1 = arg0Value.getClass();
                     if (CompilerDirectives.isExact(arg0Value, cachedClass__1)) {
                        boolean result__1 = arg0Value.isMemberReadableImpl(arg1Value);
                        if (result__1 && count1_ < 8) {
                           s1_ = new AbstractConstantKeysObjectGen.InteropLibraryExports.Cached.IsMemberReadableCacheEqualsData(
                              this.isMemberReadable_cacheEquals_cache
                           );
                           s1_.cachedSymbol_ = arg1Value;
                           s1_.cachedClass_ = cachedClass__1;
                           s1_.result_ = result__1;
                           VarHandle.storeStoreFence();
                           this.isMemberReadable_cacheEquals_cache = s1_;
                           this.exclude_ = exclude |= 1;
                           this.isMemberReadable_cacheIdentity_cache = null;
                           int var15 = state_0 & -2;
                           this.state_0_ = state_0 = var15 | 2;
                        }
                     }
                  }

                  if (s1_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return AbstractConstantKeysObject.IsMemberReadable.cacheEquals(arg0Value, arg1Value, s1_.cachedSymbol_, s1_.cachedClass_, s1_.result_);
                  }
               }

               this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
               int var18;
               this.exclude_ = var18 = exclude | 3;
               this.isMemberReadable_cacheIdentity_cache = null;
               this.isMemberReadable_cacheEquals_cache = null;
               state_0 &= -4;
               int var17;
               this.state_0_ = var17 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return AbstractConstantKeysObject.IsMemberReadable.isReadable(arg0Value, arg1Value, this.classProfile);
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
                  AbstractConstantKeysObjectGen.InteropLibraryExports.Cached.IsMemberReadableCacheIdentityData s0_ = this.isMemberReadable_cacheIdentity_cache;
                  AbstractConstantKeysObjectGen.InteropLibraryExports.Cached.IsMemberReadableCacheEqualsData s1_ = this.isMemberReadable_cacheEquals_cache;
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

            AbstractConstantKeysObject arg0Value = (AbstractConstantKeysObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 56) != 0) {
               if ((state_0 & 8) != 0) {
                  for (AbstractConstantKeysObjectGen.InteropLibraryExports.Cached.ReadMemberReadIdentityData s0_ = this.readMember_readIdentity_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (arg1Value == s0_.cachedSymbol_) {
                        return AbstractConstantKeysObject.ReadMember.readIdentity(arg0Value, arg1Value, s0_.cachedSymbol_, s0_.classProfile_);
                     }
                  }
               }

               if ((state_0 & 16) != 0) {
                  for (AbstractConstantKeysObjectGen.InteropLibraryExports.Cached.ReadMemberReadEqualsData s1_ = this.readMember_readEquals_cache;
                     s1_ != null;
                     s1_ = s1_.next_
                  ) {
                     if (arg1Value.equals(s1_.cachedSymbol_)) {
                        return AbstractConstantKeysObject.ReadMember.readEquals(arg0Value, arg1Value, s1_.cachedSymbol_, s1_.classProfile_);
                     }
                  }
               }

               if ((state_0 & 32) != 0) {
                  return AbstractConstantKeysObject.ReadMember.read(arg0Value, arg1Value, this.classProfile);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.readMemberAndSpecialize(arg0Value, arg1Value);
         }

         private Object readMemberAndSpecialize(AbstractConstantKeysObject arg0Value, String arg1Value) throws UnknownIdentifierException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               int exclude = this.exclude_;
               if ((exclude & 4) == 0) {
                  int count0_ = 0;
                  AbstractConstantKeysObjectGen.InteropLibraryExports.Cached.ReadMemberReadIdentityData s0_ = this.readMember_readIdentity_cache;
                  if ((state_0 & 8) != 0) {
                     while (s0_ != null && arg1Value != s0_.cachedSymbol_) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 8) {
                     s0_ = new AbstractConstantKeysObjectGen.InteropLibraryExports.Cached.ReadMemberReadIdentityData(this.readMember_readIdentity_cache);
                     s0_.cachedSymbol_ = arg1Value;
                     s0_.classProfile_ = ValueProfile.createClassProfile();
                     VarHandle.storeStoreFence();
                     this.readMember_readIdentity_cache = s0_;
                     this.state_0_ = state_0 |= 8;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return AbstractConstantKeysObject.ReadMember.readIdentity(arg0Value, arg1Value, s0_.cachedSymbol_, s0_.classProfile_);
                  }
               }

               if ((exclude & 8) == 0) {
                  int count1_ = 0;
                  AbstractConstantKeysObjectGen.InteropLibraryExports.Cached.ReadMemberReadEqualsData s1_ = this.readMember_readEquals_cache;
                  if ((state_0 & 16) != 0) {
                     while (s1_ != null && !arg1Value.equals(s1_.cachedSymbol_)) {
                        s1_ = s1_.next_;
                        count1_++;
                     }
                  }

                  if (s1_ == null && count1_ < 8) {
                     s1_ = new AbstractConstantKeysObjectGen.InteropLibraryExports.Cached.ReadMemberReadEqualsData(this.readMember_readEquals_cache);
                     s1_.cachedSymbol_ = arg1Value;
                     s1_.classProfile_ = ValueProfile.createClassProfile();
                     VarHandle.storeStoreFence();
                     this.readMember_readEquals_cache = s1_;
                     this.exclude_ = exclude |= 4;
                     this.readMember_readIdentity_cache = null;
                     int var13 = state_0 & -9;
                     this.state_0_ = state_0 = var13 | 16;
                  }

                  if (s1_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return AbstractConstantKeysObject.ReadMember.readEquals(arg0Value, arg1Value, s1_.cachedSymbol_, s1_.classProfile_);
                  }
               }

               this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
               int var16;
               this.exclude_ = var16 = exclude | 12;
               this.readMember_readIdentity_cache = null;
               this.readMember_readEquals_cache = null;
               state_0 &= -25;
               int var15;
               this.state_0_ = var15 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return AbstractConstantKeysObject.ReadMember.read(arg0Value, arg1Value, this.classProfile);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public boolean hasMembers(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((AbstractConstantKeysObject)receiver).hasMembers();
         }

         @Override
         public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((AbstractConstantKeysObject)receiver).getMembers(includeInternal);
         }

         @GeneratedBy(AbstractConstantKeysObject.class)
         private static final class IsMemberReadableCacheEqualsData {
            @CompilerDirectives.CompilationFinal
            AbstractConstantKeysObjectGen.InteropLibraryExports.Cached.IsMemberReadableCacheEqualsData next_;
            @CompilerDirectives.CompilationFinal
            String cachedSymbol_;
            @CompilerDirectives.CompilationFinal
            Class<?> cachedClass_;
            @CompilerDirectives.CompilationFinal
            boolean result_;

            IsMemberReadableCacheEqualsData(AbstractConstantKeysObjectGen.InteropLibraryExports.Cached.IsMemberReadableCacheEqualsData next_) {
               this.next_ = next_;
            }
         }

         @GeneratedBy(AbstractConstantKeysObject.class)
         private static final class IsMemberReadableCacheIdentityData {
            @CompilerDirectives.CompilationFinal
            AbstractConstantKeysObjectGen.InteropLibraryExports.Cached.IsMemberReadableCacheIdentityData next_;
            @CompilerDirectives.CompilationFinal
            String cachedSymbol_;
            @CompilerDirectives.CompilationFinal
            Class<?> cachedClass_;
            @CompilerDirectives.CompilationFinal
            boolean result_;

            IsMemberReadableCacheIdentityData(AbstractConstantKeysObjectGen.InteropLibraryExports.Cached.IsMemberReadableCacheIdentityData next_) {
               this.next_ = next_;
            }
         }

         @GeneratedBy(AbstractConstantKeysObject.class)
         private static final class ReadMemberReadEqualsData {
            @CompilerDirectives.CompilationFinal
            AbstractConstantKeysObjectGen.InteropLibraryExports.Cached.ReadMemberReadEqualsData next_;
            @CompilerDirectives.CompilationFinal
            String cachedSymbol_;
            @CompilerDirectives.CompilationFinal
            ValueProfile classProfile_;

            ReadMemberReadEqualsData(AbstractConstantKeysObjectGen.InteropLibraryExports.Cached.ReadMemberReadEqualsData next_) {
               this.next_ = next_;
            }
         }

         @GeneratedBy(AbstractConstantKeysObject.class)
         private static final class ReadMemberReadIdentityData {
            @CompilerDirectives.CompilationFinal
            AbstractConstantKeysObjectGen.InteropLibraryExports.Cached.ReadMemberReadIdentityData next_;
            @CompilerDirectives.CompilationFinal
            String cachedSymbol_;
            @CompilerDirectives.CompilationFinal
            ValueProfile classProfile_;

            ReadMemberReadIdentityData(AbstractConstantKeysObjectGen.InteropLibraryExports.Cached.ReadMemberReadIdentityData next_) {
               this.next_ = next_;
            }
         }
      }

      @GeneratedBy(AbstractConstantKeysObject.class)
      public static class Uncached extends AbstractRegexObjectGen.InteropLibraryExports.Uncached {
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

            AbstractConstantKeysObject arg0Value = (AbstractConstantKeysObject)arg0Value_;
            return AbstractConstantKeysObject.IsMemberReadable.isReadable(arg0Value, arg1Value, ValueProfile.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readMember(Object arg0Value_, String arg1Value) throws UnknownIdentifierException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            AbstractConstantKeysObject arg0Value = (AbstractConstantKeysObject)arg0Value_;
            return AbstractConstantKeysObject.ReadMember.read(arg0Value, arg1Value, ValueProfile.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMembers(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((AbstractConstantKeysObject)receiver).hasMembers();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((AbstractConstantKeysObject)receiver).getMembers(includeInternal);
         }
      }
   }
}
