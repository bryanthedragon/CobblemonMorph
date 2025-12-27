package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.InvalidBufferOffsetException;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnknownKeyException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.api.utilities.TriState;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.concurrent.locks.Lock;

@GeneratedBy(HostObject.class)
final class HostObjectGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

   private HostObjectGen() {
   }

   static {
      LibraryExport.register(HostObject.class, new HostObjectGen.InteropLibraryExports());
   }

   @GeneratedBy(HostObject.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, HostObject.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof HostObject;

         InteropLibrary uncached = new HostObjectGen.InteropLibraryExports.Uncached();
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof HostObject;

         return new HostObjectGen.InteropLibraryExports.Cached();
      }

      @GeneratedBy(HostObject.class)
      private static final class Cached extends InteropLibrary {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int state_1_;
         @CompilerDirectives.CompilationFinal
         private volatile int state_2_;
         @CompilerDirectives.CompilationFinal
         private volatile int state_3_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private HostObject.IsArrayNode isArray;
         @Node.Child
         private HostObject.IsListNode isList;
         @CompilerDirectives.CompilationFinal
         private BranchProfile error;
         @Node.Child
         private HostObject.IsMapEntryNode isMapEntry;
         @Node.Child
         private HostToTypeNode toHost;
         @Node.Child
         private HostContext.ToGuestValueNode toGuest;
         @Node.Child
         private HostObject.LookupConstructorNode lookupConstructor;
         @Node.Child
         private HostExecuteNode hostExecute;
         @Node.Child
         private HostObject.IsIterableNode isIterable;
         @Node.Child
         private HostObject.IsIteratorNode isIterator;
         @Node.Child
         private HostObject.IsMapNode isMap;
         @Node.Child
         private HostObject.LookupFieldNode lookupField;
         @Node.Child
         private HostObject.ReadFieldNode readField;
         @Node.Child
         private HostObject.LookupMethodNode lookupMethod;
         @Node.Child
         private HostObject.IsBufferNode isBuffer;
         @CompilerDirectives.CompilationFinal
         private ValueProfile classProfile;
         @Node.Child
         private HostObject.LookupFunctionalMethodNode lookupFunctionalMethod;
         @Node.Child
         private InteropLibrary numbers;
         @Node.Child
         private HostObject.ContainsKeyNode containsKey;
         @CompilerDirectives.CompilationFinal
         private HostObjectGen.InteropLibraryExports.Cached.IsMemberReadableCachedData isMemberReadable_cached_cache;
         @CompilerDirectives.CompilationFinal
         private HostObjectGen.InteropLibraryExports.Cached.IsMemberModifiableCachedData isMemberModifiable_cached_cache;
         @CompilerDirectives.CompilationFinal
         private HostObjectGen.InteropLibraryExports.Cached.IsMemberInternalCachedData isMemberInternal_cached_cache;
         @CompilerDirectives.CompilationFinal
         private HostObjectGen.InteropLibraryExports.Cached.IsMemberInvocableCachedData isMemberInvocable_cached_cache;
         @Node.Child
         private HostObject.ArraySet writeArrayElement_array_arraySet_;
         @Node.Child
         private HostObject.ArrayGet readArrayElement_array_arrayGet_;
         @Node.Child
         private InteropLibrary instantiate_arrayCached_indexes_;
         @CompilerDirectives.CompilationFinal
         private BranchProfile getIteratorNextElement_iterator_stopIteration_;
         @Node.Child
         private HostObject.LookupInnerClassNode readMemberNode__readMember_lookupInnerClass_;
         @Node.Child
         private HostObject.WriteFieldNode writeMemberNode__writeMember_writeField_;
         @Node.Child
         private InteropLibrary invokeMemberNode__invokeMember_fieldValues_;

         protected Cached() {
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof HostObject) || HostObjectGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof HostObject;
         }

         @ExplodeLoop
         @Override
         public boolean isMemberReadable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 3) != 0) {
               if ((state_0 & 1) != 0 && arg0Value.isStaticClass()) {
                  for (HostObjectGen.InteropLibraryExports.Cached.IsMemberReadableCachedData s0_ = this.isMemberReadable_cached_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (arg0Value.isStaticClass() == s0_.cachedStatic_ && arg0Value.getLookupClass() == s0_.cachedClazz_ && s0_.cachedName_.equals(arg1Value)) {
                        return HostObject.IsMemberReadable.doCached(
                           arg0Value, arg1Value, s0_.cachedStatic_, s0_.cachedClazz_, s0_.cachedName_, s0_.cachedReadable_
                        );
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  return HostObject.IsMemberReadable.doUncached(arg0Value, arg1Value);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.isMemberReadableAndSpecialize(arg0Value, arg1Value);
         }

         private boolean isMemberReadableAndSpecialize(HostObject arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               int exclude = this.exclude_;
               if ((exclude & 1) == 0 && arg0Value.isStaticClass()) {
                  int count0_ = 0;
                  HostObjectGen.InteropLibraryExports.Cached.IsMemberReadableCachedData s0_ = this.isMemberReadable_cached_cache;
                  if ((state_0 & 1) != 0) {
                     while (
                        s0_ != null
                           && (
                              arg0Value.isStaticClass() != s0_.cachedStatic_
                                 || arg0Value.getLookupClass() != s0_.cachedClazz_
                                 || !s0_.cachedName_.equals(arg1Value)
                           )
                     ) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null) {
                     boolean cachedStatic__ = arg0Value.isStaticClass();
                     if (arg0Value.isStaticClass() == cachedStatic__) {
                        Class<?> cachedClazz__ = arg0Value.getLookupClass();
                        if (arg0Value.getLookupClass() == cachedClazz__ && count0_ < 5) {
                           s0_ = new HostObjectGen.InteropLibraryExports.Cached.IsMemberReadableCachedData(this.isMemberReadable_cached_cache);
                           s0_.cachedStatic_ = cachedStatic__;
                           s0_.cachedClazz_ = cachedClazz__;
                           s0_.cachedName_ = arg1Value;
                           s0_.cachedReadable_ = HostObject.IsMemberReadable.doUncached(arg0Value, arg1Value);
                           VarHandle.storeStoreFence();
                           this.isMemberReadable_cached_cache = s0_;
                           this.state_0_ = state_0 |= 1;
                        }
                     }
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return HostObject.IsMemberReadable.doCached(
                        arg0Value, arg1Value, s0_.cachedStatic_, s0_.cachedClazz_, s0_.cachedName_, s0_.cachedReadable_
                     );
                  }
               }

               int var16;
               this.exclude_ = var16 = exclude | 1;
               this.isMemberReadable_cached_cache = null;
               state_0 &= -2;
               int var15;
               this.state_0_ = var15 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return HostObject.IsMemberReadable.doUncached(arg0Value, arg1Value);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public NodeCost getCost() {
            int state_0 = this.state_0_;
            if ((state_0 & 3) == 0) {
               return NodeCost.UNINITIALIZED;
            } else {
               if ((state_0 & 3 & (state_0 & 3) - 1) == 0) {
                  HostObjectGen.InteropLibraryExports.Cached.IsMemberReadableCachedData s0_ = this.isMemberReadable_cached_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         @ExplodeLoop
         @Override
         public boolean isMemberModifiable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 12) != 0) {
               if ((state_0 & 4) != 0 && arg0Value.isStaticClass()) {
                  for (HostObjectGen.InteropLibraryExports.Cached.IsMemberModifiableCachedData s0_ = this.isMemberModifiable_cached_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (arg0Value.isStaticClass() == s0_.cachedStatic_ && arg0Value.getLookupClass() == s0_.cachedClazz_ && s0_.cachedName_.equals(arg1Value)) {
                        return HostObject.IsMemberModifiable.doCached(
                           arg0Value, arg1Value, s0_.cachedStatic_, s0_.cachedClazz_, s0_.cachedName_, s0_.cachedModifiable_
                        );
                     }
                  }
               }

               if ((state_0 & 8) != 0) {
                  return HostObject.IsMemberModifiable.doUncached(arg0Value, arg1Value);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.isMemberModifiableAndSpecialize(arg0Value, arg1Value);
         }

         private boolean isMemberModifiableAndSpecialize(HostObject arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               int exclude = this.exclude_;
               if ((exclude & 2) == 0 && arg0Value.isStaticClass()) {
                  int count0_ = 0;
                  HostObjectGen.InteropLibraryExports.Cached.IsMemberModifiableCachedData s0_ = this.isMemberModifiable_cached_cache;
                  if ((state_0 & 4) != 0) {
                     while (
                        s0_ != null
                           && (
                              arg0Value.isStaticClass() != s0_.cachedStatic_
                                 || arg0Value.getLookupClass() != s0_.cachedClazz_
                                 || !s0_.cachedName_.equals(arg1Value)
                           )
                     ) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null) {
                     boolean cachedStatic__ = arg0Value.isStaticClass();
                     if (arg0Value.isStaticClass() == cachedStatic__) {
                        Class<?> cachedClazz__ = arg0Value.getLookupClass();
                        if (arg0Value.getLookupClass() == cachedClazz__ && count0_ < 5) {
                           s0_ = new HostObjectGen.InteropLibraryExports.Cached.IsMemberModifiableCachedData(this.isMemberModifiable_cached_cache);
                           s0_.cachedStatic_ = cachedStatic__;
                           s0_.cachedClazz_ = cachedClazz__;
                           s0_.cachedName_ = arg1Value;
                           s0_.cachedModifiable_ = HostObject.IsMemberModifiable.doUncached(arg0Value, arg1Value);
                           VarHandle.storeStoreFence();
                           this.isMemberModifiable_cached_cache = s0_;
                           this.state_0_ = state_0 |= 4;
                        }
                     }
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return HostObject.IsMemberModifiable.doCached(
                        arg0Value, arg1Value, s0_.cachedStatic_, s0_.cachedClazz_, s0_.cachedName_, s0_.cachedModifiable_
                     );
                  }
               }

               int var16;
               this.exclude_ = var16 = exclude | 2;
               this.isMemberModifiable_cached_cache = null;
               state_0 &= -5;
               int var15;
               this.state_0_ = var15 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return HostObject.IsMemberModifiable.doUncached(arg0Value, arg1Value);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @ExplodeLoop
         @Override
         public boolean isMemberInternal(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 48) != 0) {
               if ((state_0 & 16) != 0 && arg0Value.isStaticClass()) {
                  for (HostObjectGen.InteropLibraryExports.Cached.IsMemberInternalCachedData s0_ = this.isMemberInternal_cached_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (arg0Value.isStaticClass() == s0_.cachedStatic_ && arg0Value.getLookupClass() == s0_.cachedClazz_ && s0_.cachedName_.equals(arg1Value)) {
                        return HostObject.IsMemberInternal.doCached(
                           arg0Value, arg1Value, s0_.cachedStatic_, s0_.cachedClazz_, s0_.cachedName_, s0_.cachedInternal_
                        );
                     }
                  }
               }

               if ((state_0 & 32) != 0) {
                  return HostObject.IsMemberInternal.doUncached(arg0Value, arg1Value);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.isMemberInternalAndSpecialize(arg0Value, arg1Value);
         }

         private boolean isMemberInternalAndSpecialize(HostObject arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               int exclude = this.exclude_;
               if ((exclude & 4) == 0 && arg0Value.isStaticClass()) {
                  int count0_ = 0;
                  HostObjectGen.InteropLibraryExports.Cached.IsMemberInternalCachedData s0_ = this.isMemberInternal_cached_cache;
                  if ((state_0 & 16) != 0) {
                     while (
                        s0_ != null
                           && (
                              arg0Value.isStaticClass() != s0_.cachedStatic_
                                 || arg0Value.getLookupClass() != s0_.cachedClazz_
                                 || !s0_.cachedName_.equals(arg1Value)
                           )
                     ) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null) {
                     boolean cachedStatic__ = arg0Value.isStaticClass();
                     if (arg0Value.isStaticClass() == cachedStatic__) {
                        Class<?> cachedClazz__ = arg0Value.getLookupClass();
                        if (arg0Value.getLookupClass() == cachedClazz__ && count0_ < 5) {
                           s0_ = new HostObjectGen.InteropLibraryExports.Cached.IsMemberInternalCachedData(this.isMemberInternal_cached_cache);
                           s0_.cachedStatic_ = cachedStatic__;
                           s0_.cachedClazz_ = cachedClazz__;
                           s0_.cachedName_ = arg1Value;
                           s0_.cachedInternal_ = HostObject.IsMemberInternal.doUncached(arg0Value, arg1Value);
                           VarHandle.storeStoreFence();
                           this.isMemberInternal_cached_cache = s0_;
                           this.state_0_ = state_0 |= 16;
                        }
                     }
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return HostObject.IsMemberInternal.doCached(
                        arg0Value, arg1Value, s0_.cachedStatic_, s0_.cachedClazz_, s0_.cachedName_, s0_.cachedInternal_
                     );
                  }
               }

               int var16;
               this.exclude_ = var16 = exclude | 4;
               this.isMemberInternal_cached_cache = null;
               state_0 &= -17;
               int var15;
               this.state_0_ = var15 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return HostObject.IsMemberInternal.doUncached(arg0Value, arg1Value);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @ExplodeLoop
         @Override
         public boolean isMemberInvocable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 192) != 0) {
               if ((state_0 & 64) != 0 && arg0Value.isStaticClass()) {
                  for (HostObjectGen.InteropLibraryExports.Cached.IsMemberInvocableCachedData s0_ = this.isMemberInvocable_cached_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (arg0Value.isStaticClass() == s0_.cachedStatic_ && arg0Value.getLookupClass() == s0_.cachedClazz_ && s0_.cachedName_.equals(arg1Value)) {
                        return HostObject.IsMemberInvocable.doCached(
                           arg0Value, arg1Value, s0_.cachedStatic_, s0_.cachedClazz_, s0_.cachedName_, s0_.cachedInvokable_
                        );
                     }
                  }
               }

               if ((state_0 & 128) != 0) {
                  return HostObject.IsMemberInvocable.doUncached(arg0Value, arg1Value);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.isMemberInvocableAndSpecialize(arg0Value, arg1Value);
         }

         private boolean isMemberInvocableAndSpecialize(HostObject arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               int exclude = this.exclude_;
               if ((exclude & 8) == 0 && arg0Value.isStaticClass()) {
                  int count0_ = 0;
                  HostObjectGen.InteropLibraryExports.Cached.IsMemberInvocableCachedData s0_ = this.isMemberInvocable_cached_cache;
                  if ((state_0 & 64) != 0) {
                     while (
                        s0_ != null
                           && (
                              arg0Value.isStaticClass() != s0_.cachedStatic_
                                 || arg0Value.getLookupClass() != s0_.cachedClazz_
                                 || !s0_.cachedName_.equals(arg1Value)
                           )
                     ) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null) {
                     boolean cachedStatic__ = arg0Value.isStaticClass();
                     if (arg0Value.isStaticClass() == cachedStatic__) {
                        Class<?> cachedClazz__ = arg0Value.getLookupClass();
                        if (arg0Value.getLookupClass() == cachedClazz__ && count0_ < 5) {
                           s0_ = new HostObjectGen.InteropLibraryExports.Cached.IsMemberInvocableCachedData(this.isMemberInvocable_cached_cache);
                           s0_.cachedStatic_ = cachedStatic__;
                           s0_.cachedClazz_ = cachedClazz__;
                           s0_.cachedName_ = arg1Value;
                           s0_.cachedInvokable_ = HostObject.IsMemberInvocable.doUncached(arg0Value, arg1Value);
                           VarHandle.storeStoreFence();
                           this.isMemberInvocable_cached_cache = s0_;
                           this.state_0_ = state_0 |= 64;
                        }
                     }
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return HostObject.IsMemberInvocable.doCached(
                        arg0Value, arg1Value, s0_.cachedStatic_, s0_.cachedClazz_, s0_.cachedName_, s0_.cachedInvokable_
                     );
                  }
               }

               int var16;
               this.exclude_ = var16 = exclude | 8;
               this.isMemberInvocable_cached_cache = null;
               state_0 &= -65;
               int var15;
               this.state_0_ = var15 = state_0 | 128;
               lock.unlock();
               hasLock = false;
               return HostObject.IsMemberInvocable.doUncached(arg0Value, arg1Value);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public boolean isArrayElementReadable(Object arg0Value_, long arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 3840) != 0) {
               if ((state_0 & 256) != 0 && this.isArray.execute(arg0Value)) {
                  return HostObject.IsArrayElementReadable.doArray(arg0Value, arg1Value, this.isArray);
               }

               if ((state_0 & 512) != 0 && this.isList.execute(arg0Value)) {
                  return HostObject.IsArrayElementReadable.doList(arg0Value, arg1Value, this.isList, this.error);
               }

               if ((state_0 & 1024) != 0 && this.isMapEntry.execute(arg0Value)) {
                  return HostObject.IsArrayElementReadable.doMapEntry(arg0Value, arg1Value, this.isMapEntry);
               }

               if ((state_0 & 2048) != 0 && !this.isList.execute(arg0Value) && !this.isArray.execute(arg0Value) && !this.isMapEntry.execute(arg0Value)) {
                  return HostObject.IsArrayElementReadable.doNotArrayOrList(arg0Value, arg1Value, this.isList, this.isArray, this.isMapEntry);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.isArrayElementReadableAndSpecialize(arg0Value, arg1Value);
         }

         private boolean isArrayElementReadableAndSpecialize(HostObject arg0Value, long arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean NotArrayOrList_duplicateFound_;
            try {
               int state_0 = this.state_0_;
               boolean Array_duplicateFound_ = false;
               if ((state_0 & 256) != 0 && this.isArray.execute(arg0Value)) {
                  Array_duplicateFound_ = true;
               }

               if (!Array_duplicateFound_) {
                  HostObject.IsArrayNode isArrayElementReadable_array_isArray__ = super.insert(
                     this.isArray == null ? HostObjectFactory.IsArrayNodeGen.create() : this.isArray
                  );
                  if (isArrayElementReadable_array_isArray__.execute(arg0Value) && (state_0 & 256) == 0) {
                     if (this.isArray == null) {
                        HostObject.IsArrayNode isArrayElementReadable_array_isArray___check = super.insert(isArrayElementReadable_array_isArray__);
                        if (isArrayElementReadable_array_isArray___check == null) {
                           throw new AssertionError(
                              "Specialization 'doArray(HostObject, long, IsArrayNode)' contains a shared cache with name 'isArray' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isArray = isArrayElementReadable_array_isArray___check;
                     }

                     this.state_0_ = state_0 |= 256;
                     Array_duplicateFound_ = true;
                  }
               }

               if (Array_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return HostObject.IsArrayElementReadable.doArray(arg0Value, arg1Value, this.isArray);
               }

               boolean List_duplicateFound_ = false;
               if ((state_0 & 512) != 0 && this.isList.execute(arg0Value)) {
                  List_duplicateFound_ = true;
               }

               if (!List_duplicateFound_) {
                  HostObject.IsListNode isArrayElementReadable_list_isList__ = super.insert(
                     this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList
                  );
                  if (isArrayElementReadable_list_isList__.execute(arg0Value) && (state_0 & 512) == 0) {
                     if (this.isList == null) {
                        HostObject.IsListNode isArrayElementReadable_list_isList___check = super.insert(isArrayElementReadable_list_isList__);
                        if (isArrayElementReadable_list_isList___check == null) {
                           throw new AssertionError(
                              "Specialization 'doList(HostObject, long, IsListNode, BranchProfile)' contains a shared cache with name 'isList' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isList = isArrayElementReadable_list_isList___check;
                     }

                     this.error = this.error == null ? BranchProfile.create() : this.error;
                     this.state_0_ = state_0 |= 512;
                     List_duplicateFound_ = true;
                  }
               }

               if (List_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return HostObject.IsArrayElementReadable.doList(arg0Value, arg1Value, this.isList, this.error);
               }

               boolean MapEntry_duplicateFound_ = false;
               if ((state_0 & 1024) != 0 && this.isMapEntry.execute(arg0Value)) {
                  MapEntry_duplicateFound_ = true;
               }

               if (!MapEntry_duplicateFound_) {
                  HostObject.IsMapEntryNode isArrayElementReadable_mapEntry_isMapEntry__ = super.insert(
                     this.isMapEntry == null ? HostObjectFactory.IsMapEntryNodeGen.create() : this.isMapEntry
                  );
                  if (isArrayElementReadable_mapEntry_isMapEntry__.execute(arg0Value) && (state_0 & 1024) == 0) {
                     if (this.isMapEntry == null) {
                        HostObject.IsMapEntryNode isArrayElementReadable_mapEntry_isMapEntry___check = super.insert(
                           isArrayElementReadable_mapEntry_isMapEntry__
                        );
                        if (isArrayElementReadable_mapEntry_isMapEntry___check == null) {
                           throw new AssertionError(
                              "Specialization 'doMapEntry(HostObject, long, IsMapEntryNode)' contains a shared cache with name 'isMapEntry' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isMapEntry = isArrayElementReadable_mapEntry_isMapEntry___check;
                     }

                     this.state_0_ = state_0 |= 1024;
                     MapEntry_duplicateFound_ = true;
                  }
               }

               if (!MapEntry_duplicateFound_) {
                  NotArrayOrList_duplicateFound_ = false;
                  if ((state_0 & 2048) != 0 && !this.isList.execute(arg0Value) && !this.isArray.execute(arg0Value) && !this.isMapEntry.execute(arg0Value)) {
                     NotArrayOrList_duplicateFound_ = true;
                  }

                  if (!NotArrayOrList_duplicateFound_) {
                     HostObject.IsListNode isArrayElementReadable_notArrayOrList_isList__ = super.insert(
                        this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList
                     );
                     if (!isArrayElementReadable_notArrayOrList_isList__.execute(arg0Value)) {
                        HostObject.IsArrayNode isArrayElementReadable_notArrayOrList_isArray__ = super.insert(
                           this.isArray == null ? HostObjectFactory.IsArrayNodeGen.create() : this.isArray
                        );
                        if (!isArrayElementReadable_notArrayOrList_isArray__.execute(arg0Value)) {
                           HostObject.IsMapEntryNode isArrayElementReadable_notArrayOrList_isMapEntry__ = super.insert(
                              this.isMapEntry == null ? HostObjectFactory.IsMapEntryNodeGen.create() : this.isMapEntry
                           );
                           if (!isArrayElementReadable_notArrayOrList_isMapEntry__.execute(arg0Value) && (state_0 & 2048) == 0) {
                              if (this.isList == null) {
                                 HostObject.IsListNode isArrayElementReadable_notArrayOrList_isList___check = super.insert(
                                    isArrayElementReadable_notArrayOrList_isList__
                                 );
                                 if (isArrayElementReadable_notArrayOrList_isList___check == null) {
                                    throw new AssertionError(
                                       "Specialization 'doNotArrayOrList(HostObject, long, IsListNode, IsArrayNode, IsMapEntryNode)' contains a shared cache with name 'isList' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                                    );
                                 }

                                 this.isList = isArrayElementReadable_notArrayOrList_isList___check;
                              }

                              if (this.isArray == null) {
                                 HostObject.IsArrayNode isArrayElementReadable_notArrayOrList_isArray___check = super.insert(
                                    isArrayElementReadable_notArrayOrList_isArray__
                                 );
                                 if (isArrayElementReadable_notArrayOrList_isArray___check == null) {
                                    throw new AssertionError(
                                       "Specialization 'doNotArrayOrList(HostObject, long, IsListNode, IsArrayNode, IsMapEntryNode)' contains a shared cache with name 'isArray' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                                    );
                                 }

                                 this.isArray = isArrayElementReadable_notArrayOrList_isArray___check;
                              }

                              if (this.isMapEntry == null) {
                                 HostObject.IsMapEntryNode isArrayElementReadable_notArrayOrList_isMapEntry___check = super.insert(
                                    isArrayElementReadable_notArrayOrList_isMapEntry__
                                 );
                                 if (isArrayElementReadable_notArrayOrList_isMapEntry___check == null) {
                                    throw new AssertionError(
                                       "Specialization 'doNotArrayOrList(HostObject, long, IsListNode, IsArrayNode, IsMapEntryNode)' contains a shared cache with name 'isMapEntry' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                                    );
                                 }

                                 this.isMapEntry = isArrayElementReadable_notArrayOrList_isMapEntry___check;
                              }

                              int var18;
                              this.state_0_ = var18 = state_0 | 2048;
                              NotArrayOrList_duplicateFound_ = true;
                           }
                        }
                     }
                  }

                  if (!NotArrayOrList_duplicateFound_) {
                     throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
                  }

                  lock.unlock();
                  hasLock = false;
                  return HostObject.IsArrayElementReadable.doNotArrayOrList(arg0Value, arg1Value, this.isList, this.isArray, this.isMapEntry);
               }

               lock.unlock();
               hasLock = false;
               NotArrayOrList_duplicateFound_ = HostObject.IsArrayElementReadable.doMapEntry(arg0Value, arg1Value, this.isMapEntry);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return NotArrayOrList_duplicateFound_;
         }

         @Override
         public boolean isArrayElementModifiable(Object arg0Value_, long arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 61440) != 0) {
               if ((state_0 & 4096) != 0 && this.isArray.execute(arg0Value)) {
                  return HostObject.IsArrayElementModifiable.doArray(arg0Value, arg1Value, this.isArray);
               }

               if ((state_0 & 8192) != 0 && this.isList.execute(arg0Value)) {
                  return HostObject.IsArrayElementModifiable.doList(arg0Value, arg1Value, this.isList, this.error);
               }

               if ((state_0 & 16384) != 0 && this.isMapEntry.execute(arg0Value)) {
                  return HostObject.IsArrayElementModifiable.doMapEntry(arg0Value, arg1Value, this.isMapEntry);
               }

               if ((state_0 & 32768) != 0 && !this.isList.execute(arg0Value) && !this.isArray.execute(arg0Value) && !this.isMapEntry.execute(arg0Value)) {
                  return HostObject.IsArrayElementModifiable.doNotArrayOrList(arg0Value, arg1Value, this.isList, this.isArray, this.isMapEntry);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.isArrayElementModifiableAndSpecialize(arg0Value, arg1Value);
         }

         private boolean isArrayElementModifiableAndSpecialize(HostObject arg0Value, long arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean NotArrayOrList_duplicateFound_;
            try {
               int state_0 = this.state_0_;
               boolean Array_duplicateFound_ = false;
               if ((state_0 & 4096) != 0 && this.isArray.execute(arg0Value)) {
                  Array_duplicateFound_ = true;
               }

               if (!Array_duplicateFound_) {
                  HostObject.IsArrayNode isArrayElementModifiable_array_isArray__ = super.insert(
                     this.isArray == null ? HostObjectFactory.IsArrayNodeGen.create() : this.isArray
                  );
                  if (isArrayElementModifiable_array_isArray__.execute(arg0Value) && (state_0 & 4096) == 0) {
                     if (this.isArray == null) {
                        HostObject.IsArrayNode isArrayElementModifiable_array_isArray___check = super.insert(isArrayElementModifiable_array_isArray__);
                        if (isArrayElementModifiable_array_isArray___check == null) {
                           throw new AssertionError(
                              "Specialization 'doArray(HostObject, long, IsArrayNode)' contains a shared cache with name 'isArray' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isArray = isArrayElementModifiable_array_isArray___check;
                     }

                     this.state_0_ = state_0 |= 4096;
                     Array_duplicateFound_ = true;
                  }
               }

               if (Array_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return HostObject.IsArrayElementModifiable.doArray(arg0Value, arg1Value, this.isArray);
               }

               boolean List_duplicateFound_ = false;
               if ((state_0 & 8192) != 0 && this.isList.execute(arg0Value)) {
                  List_duplicateFound_ = true;
               }

               if (!List_duplicateFound_) {
                  HostObject.IsListNode isArrayElementModifiable_list_isList__ = super.insert(
                     this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList
                  );
                  if (isArrayElementModifiable_list_isList__.execute(arg0Value) && (state_0 & 8192) == 0) {
                     if (this.isList == null) {
                        HostObject.IsListNode isArrayElementModifiable_list_isList___check = super.insert(isArrayElementModifiable_list_isList__);
                        if (isArrayElementModifiable_list_isList___check == null) {
                           throw new AssertionError(
                              "Specialization 'doList(HostObject, long, IsListNode, BranchProfile)' contains a shared cache with name 'isList' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isList = isArrayElementModifiable_list_isList___check;
                     }

                     this.error = this.error == null ? BranchProfile.create() : this.error;
                     this.state_0_ = state_0 |= 8192;
                     List_duplicateFound_ = true;
                  }
               }

               if (List_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return HostObject.IsArrayElementModifiable.doList(arg0Value, arg1Value, this.isList, this.error);
               }

               boolean MapEntry_duplicateFound_ = false;
               if ((state_0 & 16384) != 0 && this.isMapEntry.execute(arg0Value)) {
                  MapEntry_duplicateFound_ = true;
               }

               if (!MapEntry_duplicateFound_) {
                  HostObject.IsMapEntryNode isArrayElementModifiable_mapEntry_isMapEntry__ = super.insert(
                     this.isMapEntry == null ? HostObjectFactory.IsMapEntryNodeGen.create() : this.isMapEntry
                  );
                  if (isArrayElementModifiable_mapEntry_isMapEntry__.execute(arg0Value) && (state_0 & 16384) == 0) {
                     if (this.isMapEntry == null) {
                        HostObject.IsMapEntryNode isArrayElementModifiable_mapEntry_isMapEntry___check = super.insert(
                           isArrayElementModifiable_mapEntry_isMapEntry__
                        );
                        if (isArrayElementModifiable_mapEntry_isMapEntry___check == null) {
                           throw new AssertionError(
                              "Specialization 'doMapEntry(HostObject, long, IsMapEntryNode)' contains a shared cache with name 'isMapEntry' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isMapEntry = isArrayElementModifiable_mapEntry_isMapEntry___check;
                     }

                     this.state_0_ = state_0 |= 16384;
                     MapEntry_duplicateFound_ = true;
                  }
               }

               if (!MapEntry_duplicateFound_) {
                  NotArrayOrList_duplicateFound_ = false;
                  if ((state_0 & 32768) != 0 && !this.isList.execute(arg0Value) && !this.isArray.execute(arg0Value) && !this.isMapEntry.execute(arg0Value)) {
                     NotArrayOrList_duplicateFound_ = true;
                  }

                  if (!NotArrayOrList_duplicateFound_) {
                     HostObject.IsListNode isArrayElementModifiable_notArrayOrList_isList__ = super.insert(
                        this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList
                     );
                     if (!isArrayElementModifiable_notArrayOrList_isList__.execute(arg0Value)) {
                        HostObject.IsArrayNode isArrayElementModifiable_notArrayOrList_isArray__ = super.insert(
                           this.isArray == null ? HostObjectFactory.IsArrayNodeGen.create() : this.isArray
                        );
                        if (!isArrayElementModifiable_notArrayOrList_isArray__.execute(arg0Value)) {
                           HostObject.IsMapEntryNode isArrayElementModifiable_notArrayOrList_isMapEntry__ = super.insert(
                              this.isMapEntry == null ? HostObjectFactory.IsMapEntryNodeGen.create() : this.isMapEntry
                           );
                           if (!isArrayElementModifiable_notArrayOrList_isMapEntry__.execute(arg0Value) && (state_0 & 32768) == 0) {
                              if (this.isList == null) {
                                 HostObject.IsListNode isArrayElementModifiable_notArrayOrList_isList___check = super.insert(
                                    isArrayElementModifiable_notArrayOrList_isList__
                                 );
                                 if (isArrayElementModifiable_notArrayOrList_isList___check == null) {
                                    throw new AssertionError(
                                       "Specialization 'doNotArrayOrList(HostObject, long, IsListNode, IsArrayNode, IsMapEntryNode)' contains a shared cache with name 'isList' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                                    );
                                 }

                                 this.isList = isArrayElementModifiable_notArrayOrList_isList___check;
                              }

                              if (this.isArray == null) {
                                 HostObject.IsArrayNode isArrayElementModifiable_notArrayOrList_isArray___check = super.insert(
                                    isArrayElementModifiable_notArrayOrList_isArray__
                                 );
                                 if (isArrayElementModifiable_notArrayOrList_isArray___check == null) {
                                    throw new AssertionError(
                                       "Specialization 'doNotArrayOrList(HostObject, long, IsListNode, IsArrayNode, IsMapEntryNode)' contains a shared cache with name 'isArray' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                                    );
                                 }

                                 this.isArray = isArrayElementModifiable_notArrayOrList_isArray___check;
                              }

                              if (this.isMapEntry == null) {
                                 HostObject.IsMapEntryNode isArrayElementModifiable_notArrayOrList_isMapEntry___check = super.insert(
                                    isArrayElementModifiable_notArrayOrList_isMapEntry__
                                 );
                                 if (isArrayElementModifiable_notArrayOrList_isMapEntry___check == null) {
                                    throw new AssertionError(
                                       "Specialization 'doNotArrayOrList(HostObject, long, IsListNode, IsArrayNode, IsMapEntryNode)' contains a shared cache with name 'isMapEntry' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                                    );
                                 }

                                 this.isMapEntry = isArrayElementModifiable_notArrayOrList_isMapEntry___check;
                              }

                              int var18;
                              this.state_0_ = var18 = state_0 | 32768;
                              NotArrayOrList_duplicateFound_ = true;
                           }
                        }
                     }
                  }

                  if (!NotArrayOrList_duplicateFound_) {
                     throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
                  }

                  lock.unlock();
                  hasLock = false;
                  return HostObject.IsArrayElementModifiable.doNotArrayOrList(arg0Value, arg1Value, this.isList, this.isArray, this.isMapEntry);
               }

               lock.unlock();
               hasLock = false;
               NotArrayOrList_duplicateFound_ = HostObject.IsArrayElementModifiable.doMapEntry(arg0Value, arg1Value, this.isMapEntry);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return NotArrayOrList_duplicateFound_;
         }

         @Override
         public void writeArrayElement(Object arg0Value_, long arg1Value, Object arg2Value) throws UnsupportedMessageException, UnsupportedTypeException, InvalidArrayIndexException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 983040) != 0) {
               if ((state_0 & 65536) != 0 && this.isArray.execute(arg0Value)) {
                  HostObject.WriteArrayElement.doArray(
                     arg0Value, arg1Value, arg2Value, this.toHost, this.isArray, this.writeArrayElement_array_arraySet_, this.error
                  );
                  return;
               }

               if ((state_0 & 131072) != 0 && this.isList.execute(arg0Value)) {
                  HostObject.WriteArrayElement.doList(arg0Value, arg1Value, arg2Value, this.isList, this.toHost, this.error);
                  return;
               }

               if ((state_0 & 262144) != 0 && this.isMapEntry.execute(arg0Value)) {
                  HostObject.WriteArrayElement.doMapEntry(arg0Value, arg1Value, arg2Value, this.isMapEntry, this.toHost, this.error);
                  return;
               }

               if ((state_0 & 524288) != 0 && !this.isList.execute(arg0Value) && !this.isArray.execute(arg0Value) && !this.isMapEntry.execute(arg0Value)) {
                  HostObject.WriteArrayElement.doNotArrayOrList(arg0Value, arg1Value, arg2Value, this.isList, this.isArray, this.isMapEntry);
                  return;
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.writeArrayElementAndSpecialize(arg0Value, arg1Value, arg2Value);
         }

         private void writeArrayElementAndSpecialize(HostObject arg0Value, long arg1Value, Object arg2Value) throws InvalidArrayIndexException, UnsupportedTypeException, UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               boolean Array_duplicateFound_ = false;
               if ((state_0 & 65536) != 0 && this.isArray.execute(arg0Value)) {
                  Array_duplicateFound_ = true;
               }

               if (!Array_duplicateFound_) {
                  HostObject.IsArrayNode writeArrayElement_array_isArray__ = super.insert(
                     this.isArray == null ? HostObjectFactory.IsArrayNodeGen.create() : this.isArray
                  );
                  if (writeArrayElement_array_isArray__.execute(arg0Value) && (state_0 & 65536) == 0) {
                     this.toHost = super.insert(this.toHost == null ? HostToTypeNodeGen.create() : this.toHost);
                     if (this.isArray == null) {
                        HostObject.IsArrayNode writeArrayElement_array_isArray___check = super.insert(writeArrayElement_array_isArray__);
                        if (writeArrayElement_array_isArray___check == null) {
                           throw new AssertionError(
                              "Specialization 'doArray(HostObject, long, Object, HostToTypeNode, IsArrayNode, ArraySet, BranchProfile)' contains a shared cache with name 'isArray' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isArray = writeArrayElement_array_isArray___check;
                     }

                     this.writeArrayElement_array_arraySet_ = super.insert(HostObjectFactory.ArraySetNodeGen.create());
                     this.error = this.error == null ? BranchProfile.create() : this.error;
                     this.state_0_ = state_0 |= 65536;
                     Array_duplicateFound_ = true;
                  }
               }

               if (Array_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  HostObject.WriteArrayElement.doArray(
                     arg0Value, arg1Value, arg2Value, this.toHost, this.isArray, this.writeArrayElement_array_arraySet_, this.error
                  );
                  return;
               }

               boolean List_duplicateFound_ = false;
               if ((state_0 & 131072) != 0 && this.isList.execute(arg0Value)) {
                  List_duplicateFound_ = true;
               }

               if (!List_duplicateFound_) {
                  HostObject.IsListNode writeArrayElement_list_isList__ = super.insert(
                     this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList
                  );
                  if (writeArrayElement_list_isList__.execute(arg0Value) && (state_0 & 131072) == 0) {
                     if (this.isList == null) {
                        HostObject.IsListNode writeArrayElement_list_isList___check = super.insert(writeArrayElement_list_isList__);
                        if (writeArrayElement_list_isList___check == null) {
                           throw new AssertionError(
                              "Specialization 'doList(HostObject, long, Object, IsListNode, HostToTypeNode, BranchProfile)' contains a shared cache with name 'isList' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isList = writeArrayElement_list_isList___check;
                     }

                     this.toHost = super.insert(this.toHost == null ? HostToTypeNodeGen.create() : this.toHost);
                     this.error = this.error == null ? BranchProfile.create() : this.error;
                     this.state_0_ = state_0 |= 131072;
                     List_duplicateFound_ = true;
                  }
               }

               if (List_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  HostObject.WriteArrayElement.doList(arg0Value, arg1Value, arg2Value, this.isList, this.toHost, this.error);
                  return;
               }

               boolean MapEntry_duplicateFound_ = false;
               if ((state_0 & 262144) != 0 && this.isMapEntry.execute(arg0Value)) {
                  MapEntry_duplicateFound_ = true;
               }

               if (!MapEntry_duplicateFound_) {
                  HostObject.IsMapEntryNode writeArrayElement_mapEntry_isMapEntry__ = super.insert(
                     this.isMapEntry == null ? HostObjectFactory.IsMapEntryNodeGen.create() : this.isMapEntry
                  );
                  if (writeArrayElement_mapEntry_isMapEntry__.execute(arg0Value) && (state_0 & 262144) == 0) {
                     if (this.isMapEntry == null) {
                        HostObject.IsMapEntryNode writeArrayElement_mapEntry_isMapEntry___check = super.insert(writeArrayElement_mapEntry_isMapEntry__);
                        if (writeArrayElement_mapEntry_isMapEntry___check == null) {
                           throw new AssertionError(
                              "Specialization 'doMapEntry(HostObject, long, Object, IsMapEntryNode, HostToTypeNode, BranchProfile)' contains a shared cache with name 'isMapEntry' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isMapEntry = writeArrayElement_mapEntry_isMapEntry___check;
                     }

                     this.toHost = super.insert(this.toHost == null ? HostToTypeNodeGen.create() : this.toHost);
                     this.error = this.error == null ? BranchProfile.create() : this.error;
                     this.state_0_ = state_0 |= 262144;
                     MapEntry_duplicateFound_ = true;
                  }
               }

               if (!MapEntry_duplicateFound_) {
                  boolean NotArrayOrList_duplicateFound_ = false;
                  if ((state_0 & 524288) != 0 && !this.isList.execute(arg0Value) && !this.isArray.execute(arg0Value) && !this.isMapEntry.execute(arg0Value)) {
                     NotArrayOrList_duplicateFound_ = true;
                  }

                  if (!NotArrayOrList_duplicateFound_) {
                     HostObject.IsListNode writeArrayElement_notArrayOrList_isList__ = super.insert(
                        this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList
                     );
                     if (!writeArrayElement_notArrayOrList_isList__.execute(arg0Value)) {
                        HostObject.IsArrayNode writeArrayElement_notArrayOrList_isArray__ = super.insert(
                           this.isArray == null ? HostObjectFactory.IsArrayNodeGen.create() : this.isArray
                        );
                        if (!writeArrayElement_notArrayOrList_isArray__.execute(arg0Value)) {
                           HostObject.IsMapEntryNode writeArrayElement_notArrayOrList_isMapEntry__ = super.insert(
                              this.isMapEntry == null ? HostObjectFactory.IsMapEntryNodeGen.create() : this.isMapEntry
                           );
                           if (!writeArrayElement_notArrayOrList_isMapEntry__.execute(arg0Value) && (state_0 & 524288) == 0) {
                              if (this.isList == null) {
                                 HostObject.IsListNode writeArrayElement_notArrayOrList_isList___check = super.insert(writeArrayElement_notArrayOrList_isList__);
                                 if (writeArrayElement_notArrayOrList_isList___check == null) {
                                    throw new AssertionError(
                                       "Specialization 'doNotArrayOrList(HostObject, long, Object, IsListNode, IsArrayNode, IsMapEntryNode)' contains a shared cache with name 'isList' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                                    );
                                 }

                                 this.isList = writeArrayElement_notArrayOrList_isList___check;
                              }

                              if (this.isArray == null) {
                                 HostObject.IsArrayNode writeArrayElement_notArrayOrList_isArray___check = super.insert(
                                    writeArrayElement_notArrayOrList_isArray__
                                 );
                                 if (writeArrayElement_notArrayOrList_isArray___check == null) {
                                    throw new AssertionError(
                                       "Specialization 'doNotArrayOrList(HostObject, long, Object, IsListNode, IsArrayNode, IsMapEntryNode)' contains a shared cache with name 'isArray' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                                    );
                                 }

                                 this.isArray = writeArrayElement_notArrayOrList_isArray___check;
                              }

                              if (this.isMapEntry == null) {
                                 HostObject.IsMapEntryNode writeArrayElement_notArrayOrList_isMapEntry___check = super.insert(
                                    writeArrayElement_notArrayOrList_isMapEntry__
                                 );
                                 if (writeArrayElement_notArrayOrList_isMapEntry___check == null) {
                                    throw new AssertionError(
                                       "Specialization 'doNotArrayOrList(HostObject, long, Object, IsListNode, IsArrayNode, IsMapEntryNode)' contains a shared cache with name 'isMapEntry' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                                    );
                                 }

                                 this.isMapEntry = writeArrayElement_notArrayOrList_isMapEntry___check;
                              }

                              int var19;
                              this.state_0_ = var19 = state_0 | 524288;
                              NotArrayOrList_duplicateFound_ = true;
                           }
                        }
                     }
                  }

                  if (!NotArrayOrList_duplicateFound_) {
                     throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
                  }

                  lock.unlock();
                  hasLock = false;
                  HostObject.WriteArrayElement.doNotArrayOrList(arg0Value, arg1Value, arg2Value, this.isList, this.isArray, this.isMapEntry);
                  return;
               }

               lock.unlock();
               hasLock = false;
               HostObject.WriteArrayElement.doMapEntry(arg0Value, arg1Value, arg2Value, this.isMapEntry, this.toHost, this.error);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public boolean isArrayElementRemovable(Object arg0Value_, long arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 3145728) != 0) {
               if ((state_0 & 1048576) != 0 && this.isList.execute(arg0Value)) {
                  return HostObject.IsArrayElementRemovable.doList(arg0Value, arg1Value, this.isList, this.error);
               }

               if ((state_0 & 2097152) != 0 && !this.isList.execute(arg0Value)) {
                  return HostObject.IsArrayElementRemovable.doOther(arg0Value, arg1Value, this.isList);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.isArrayElementRemovableAndSpecialize(arg0Value, arg1Value);
         }

         private boolean isArrayElementRemovableAndSpecialize(HostObject arg0Value, long arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean Other_duplicateFound_;
            try {
               int state_0 = this.state_0_;
               boolean List_duplicateFound_ = false;
               if ((state_0 & 1048576) != 0 && this.isList.execute(arg0Value)) {
                  List_duplicateFound_ = true;
               }

               if (!List_duplicateFound_) {
                  HostObject.IsListNode isArrayElementRemovable_list_isList__ = super.insert(
                     this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList
                  );
                  if (isArrayElementRemovable_list_isList__.execute(arg0Value) && (state_0 & 1048576) == 0) {
                     if (this.isList == null) {
                        HostObject.IsListNode isArrayElementRemovable_list_isList___check = super.insert(isArrayElementRemovable_list_isList__);
                        if (isArrayElementRemovable_list_isList___check == null) {
                           throw new AssertionError(
                              "Specialization 'doList(HostObject, long, IsListNode, BranchProfile)' contains a shared cache with name 'isList' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isList = isArrayElementRemovable_list_isList___check;
                     }

                     this.error = this.error == null ? BranchProfile.create() : this.error;
                     this.state_0_ = state_0 |= 1048576;
                     List_duplicateFound_ = true;
                  }
               }

               if (!List_duplicateFound_) {
                  Other_duplicateFound_ = false;
                  if ((state_0 & 2097152) != 0 && !this.isList.execute(arg0Value)) {
                     Other_duplicateFound_ = true;
                  }

                  if (!Other_duplicateFound_) {
                     HostObject.IsListNode isArrayElementRemovable_other_isList__ = super.insert(
                        this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList
                     );
                     if (!isArrayElementRemovable_other_isList__.execute(arg0Value) && (state_0 & 2097152) == 0) {
                        if (this.isList == null) {
                           HostObject.IsListNode isArrayElementRemovable_other_isList___check = super.insert(isArrayElementRemovable_other_isList__);
                           if (isArrayElementRemovable_other_isList___check == null) {
                              throw new AssertionError(
                                 "Specialization 'doOther(HostObject, long, IsListNode)' contains a shared cache with name 'isList' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                              );
                           }

                           this.isList = isArrayElementRemovable_other_isList___check;
                        }

                        int var14;
                        this.state_0_ = var14 = state_0 | 2097152;
                        Other_duplicateFound_ = true;
                     }
                  }

                  if (!Other_duplicateFound_) {
                     throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
                  }

                  lock.unlock();
                  hasLock = false;
                  return HostObject.IsArrayElementRemovable.doOther(arg0Value, arg1Value, this.isList);
               }

               lock.unlock();
               hasLock = false;
               Other_duplicateFound_ = HostObject.IsArrayElementRemovable.doList(arg0Value, arg1Value, this.isList, this.error);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return Other_duplicateFound_;
         }

         @Override
         public void removeArrayElement(Object arg0Value_, long arg1Value) throws UnsupportedMessageException, InvalidArrayIndexException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 12582912) != 0) {
               if ((state_0 & 4194304) != 0 && this.isList.execute(arg0Value)) {
                  HostObject.RemoveArrayElement.doList(arg0Value, arg1Value, this.isList, this.error);
                  return;
               }

               if ((state_0 & 8388608) != 0 && !this.isList.execute(arg0Value)) {
                  HostObject.RemoveArrayElement.doOther(arg0Value, arg1Value, this.isList);
                  return;
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.removeArrayElementAndSpecialize(arg0Value, arg1Value);
         }

         private void removeArrayElementAndSpecialize(HostObject arg0Value, long arg1Value) throws InvalidArrayIndexException, UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               boolean List_duplicateFound_ = false;
               if ((state_0 & 4194304) != 0 && this.isList.execute(arg0Value)) {
                  List_duplicateFound_ = true;
               }

               if (!List_duplicateFound_) {
                  HostObject.IsListNode removeArrayElement_list_isList__ = super.insert(
                     this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList
                  );
                  if (removeArrayElement_list_isList__.execute(arg0Value) && (state_0 & 4194304) == 0) {
                     if (this.isList == null) {
                        HostObject.IsListNode removeArrayElement_list_isList___check = super.insert(removeArrayElement_list_isList__);
                        if (removeArrayElement_list_isList___check == null) {
                           throw new AssertionError(
                              "Specialization 'doList(HostObject, long, IsListNode, BranchProfile)' contains a shared cache with name 'isList' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isList = removeArrayElement_list_isList___check;
                     }

                     this.error = this.error == null ? BranchProfile.create() : this.error;
                     this.state_0_ = state_0 |= 4194304;
                     List_duplicateFound_ = true;
                  }
               }

               if (!List_duplicateFound_) {
                  boolean Other_duplicateFound_ = false;
                  if ((state_0 & 8388608) != 0 && !this.isList.execute(arg0Value)) {
                     Other_duplicateFound_ = true;
                  }

                  if (!Other_duplicateFound_) {
                     HostObject.IsListNode removeArrayElement_other_isList__ = super.insert(
                        this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList
                     );
                     if (!removeArrayElement_other_isList__.execute(arg0Value) && (state_0 & 8388608) == 0) {
                        if (this.isList == null) {
                           HostObject.IsListNode removeArrayElement_other_isList___check = super.insert(removeArrayElement_other_isList__);
                           if (removeArrayElement_other_isList___check == null) {
                              throw new AssertionError(
                                 "Specialization 'doOther(HostObject, long, IsListNode)' contains a shared cache with name 'isList' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                              );
                           }

                           this.isList = removeArrayElement_other_isList___check;
                        }

                        int var14;
                        this.state_0_ = var14 = state_0 | 8388608;
                        Other_duplicateFound_ = true;
                     }
                  }

                  if (!Other_duplicateFound_) {
                     throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
                  }

                  lock.unlock();
                  hasLock = false;
                  HostObject.RemoveArrayElement.doOther(arg0Value, arg1Value, this.isList);
                  return;
               }

               lock.unlock();
               hasLock = false;
               HostObject.RemoveArrayElement.doList(arg0Value, arg1Value, this.isList, this.error);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public Object readArrayElement(Object arg0Value_, long arg1Value) throws UnsupportedMessageException, InvalidArrayIndexException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 251658240) != 0) {
               if ((state_0 & 16777216) != 0 && this.isArray.execute(arg0Value)) {
                  return HostObject.ReadArrayElement.doArray(
                     arg0Value, arg1Value, this.readArrayElement_array_arrayGet_, this.isArray, this.toGuest, this.error
                  );
               }

               if ((state_0 & 33554432) != 0 && this.isList.execute(arg0Value)) {
                  return HostObject.ReadArrayElement.doList(arg0Value, arg1Value, this.isList, this.toGuest, this.error);
               }

               if ((state_0 & 67108864) != 0 && this.isMapEntry.execute(arg0Value)) {
                  return HostObject.ReadArrayElement.doMapEntry(arg0Value, arg1Value, this.isMapEntry, this.toGuest, this.error);
               }

               if ((state_0 & 134217728) != 0 && !this.isArray.execute(arg0Value) && !this.isList.execute(arg0Value) && !this.isMapEntry.execute(arg0Value)) {
                  return HostObject.ReadArrayElement.doNotArrayOrList(arg0Value, arg1Value, this.isArray, this.isList, this.isMapEntry);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.readArrayElementAndSpecialize(arg0Value, arg1Value);
         }

         private Object readArrayElementAndSpecialize(HostObject arg0Value, long arg1Value) throws InvalidArrayIndexException, UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            HostObject.IsListNode readArrayElement_list_isList___check;
            try {
               int state_0 = this.state_0_;
               boolean Array_duplicateFound_ = false;
               if ((state_0 & 16777216) != 0 && this.isArray.execute(arg0Value)) {
                  Array_duplicateFound_ = true;
               }

               if (!Array_duplicateFound_) {
                  HostObject.IsArrayNode readArrayElement_array_isArray__ = super.insert(
                     this.isArray == null ? HostObjectFactory.IsArrayNodeGen.create() : this.isArray
                  );
                  if (readArrayElement_array_isArray__.execute(arg0Value) && (state_0 & 16777216) == 0) {
                     this.readArrayElement_array_arrayGet_ = super.insert(HostObjectFactory.ArrayGetNodeGen.create());
                     if (this.isArray == null) {
                        HostObject.IsArrayNode readArrayElement_array_isArray___check = super.insert(readArrayElement_array_isArray__);
                        if (readArrayElement_array_isArray___check == null) {
                           throw new AssertionError(
                              "Specialization 'doArray(HostObject, long, ArrayGet, IsArrayNode, ToGuestValueNode, BranchProfile)' contains a shared cache with name 'isArray' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isArray = readArrayElement_array_isArray___check;
                     }

                     this.toGuest = super.insert(this.toGuest == null ? HostContextFactory.ToGuestValueNodeGen.create() : this.toGuest);
                     this.error = this.error == null ? BranchProfile.create() : this.error;
                     this.state_0_ = state_0 |= 16777216;
                     Array_duplicateFound_ = true;
                  }
               }

               if (Array_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return HostObject.ReadArrayElement.doArray(
                     arg0Value, arg1Value, this.readArrayElement_array_arrayGet_, this.isArray, this.toGuest, this.error
                  );
               }

               boolean List_duplicateFound_ = false;
               if ((state_0 & 33554432) != 0 && this.isList.execute(arg0Value)) {
                  List_duplicateFound_ = true;
               }

               if (!List_duplicateFound_) {
                  HostObject.IsListNode readArrayElement_list_isList__ = super.insert(
                     this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList
                  );
                  if (readArrayElement_list_isList__.execute(arg0Value) && (state_0 & 33554432) == 0) {
                     if (this.isList == null) {
                        readArrayElement_list_isList___check = super.insert(readArrayElement_list_isList__);
                        if (readArrayElement_list_isList___check == null) {
                           throw new AssertionError(
                              "Specialization 'doList(HostObject, long, IsListNode, ToGuestValueNode, BranchProfile)' contains a shared cache with name 'isList' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isList = readArrayElement_list_isList___check;
                     }

                     this.toGuest = super.insert(this.toGuest == null ? HostContextFactory.ToGuestValueNodeGen.create() : this.toGuest);
                     this.error = this.error == null ? BranchProfile.create() : this.error;
                     this.state_0_ = state_0 |= 33554432;
                     List_duplicateFound_ = true;
                  }
               }

               if (List_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return HostObject.ReadArrayElement.doList(arg0Value, arg1Value, this.isList, this.toGuest, this.error);
               }

               boolean MapEntry_duplicateFound_ = false;
               if ((state_0 & 67108864) != 0 && this.isMapEntry.execute(arg0Value)) {
                  MapEntry_duplicateFound_ = true;
               }

               if (!MapEntry_duplicateFound_) {
                  readArrayElement_list_isList___check = (HostObject.IsMapEntryNode)super.insert(
                     this.isMapEntry == null ? HostObjectFactory.IsMapEntryNodeGen.create() : this.isMapEntry
                  );
                  if (readArrayElement_list_isList___check.execute(arg0Value) && (state_0 & 67108864) == 0) {
                     if (this.isMapEntry == null) {
                        HostObject.IsMapEntryNode readArrayElement_mapEntry_isMapEntry___check = super.insert(readArrayElement_list_isList___check);
                        if (readArrayElement_mapEntry_isMapEntry___check == null) {
                           throw new AssertionError(
                              "Specialization 'doMapEntry(HostObject, long, IsMapEntryNode, ToGuestValueNode, BranchProfile)' contains a shared cache with name 'isMapEntry' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isMapEntry = readArrayElement_mapEntry_isMapEntry___check;
                     }

                     this.toGuest = super.insert(this.toGuest == null ? HostContextFactory.ToGuestValueNodeGen.create() : this.toGuest);
                     this.error = this.error == null ? BranchProfile.create() : this.error;
                     this.state_0_ = state_0 |= 67108864;
                     MapEntry_duplicateFound_ = true;
                  }
               }

               if (!MapEntry_duplicateFound_) {
                  boolean NotArrayOrList_duplicateFound_ = false;
                  if ((state_0 & 134217728) != 0 && !this.isArray.execute(arg0Value) && !this.isList.execute(arg0Value) && !this.isMapEntry.execute(arg0Value)) {
                     NotArrayOrList_duplicateFound_ = true;
                  }

                  if (!NotArrayOrList_duplicateFound_) {
                     HostObject.IsArrayNode readArrayElement_notArrayOrList_isArray__ = super.insert(
                        this.isArray == null ? HostObjectFactory.IsArrayNodeGen.create() : this.isArray
                     );
                     if (!readArrayElement_notArrayOrList_isArray__.execute(arg0Value)) {
                        HostObject.IsListNode readArrayElement_notArrayOrList_isList__ = super.insert(
                           this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList
                        );
                        if (!readArrayElement_notArrayOrList_isList__.execute(arg0Value)) {
                           HostObject.IsMapEntryNode readArrayElement_notArrayOrList_isMapEntry__ = super.insert(
                              this.isMapEntry == null ? HostObjectFactory.IsMapEntryNodeGen.create() : this.isMapEntry
                           );
                           if (!readArrayElement_notArrayOrList_isMapEntry__.execute(arg0Value) && (state_0 & 134217728) == 0) {
                              if (this.isArray == null) {
                                 HostObject.IsArrayNode readArrayElement_notArrayOrList_isArray___check = super.insert(
                                    readArrayElement_notArrayOrList_isArray__
                                 );
                                 if (readArrayElement_notArrayOrList_isArray___check == null) {
                                    throw new AssertionError(
                                       "Specialization 'doNotArrayOrList(HostObject, long, IsArrayNode, IsListNode, IsMapEntryNode)' contains a shared cache with name 'isArray' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                                    );
                                 }

                                 this.isArray = readArrayElement_notArrayOrList_isArray___check;
                              }

                              if (this.isList == null) {
                                 HostObject.IsListNode readArrayElement_notArrayOrList_isList___check = super.insert(readArrayElement_notArrayOrList_isList__);
                                 if (readArrayElement_notArrayOrList_isList___check == null) {
                                    throw new AssertionError(
                                       "Specialization 'doNotArrayOrList(HostObject, long, IsArrayNode, IsListNode, IsMapEntryNode)' contains a shared cache with name 'isList' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                                    );
                                 }

                                 this.isList = readArrayElement_notArrayOrList_isList___check;
                              }

                              if (this.isMapEntry == null) {
                                 HostObject.IsMapEntryNode readArrayElement_notArrayOrList_isMapEntry___check = super.insert(
                                    readArrayElement_notArrayOrList_isMapEntry__
                                 );
                                 if (readArrayElement_notArrayOrList_isMapEntry___check == null) {
                                    throw new AssertionError(
                                       "Specialization 'doNotArrayOrList(HostObject, long, IsArrayNode, IsListNode, IsMapEntryNode)' contains a shared cache with name 'isMapEntry' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                                    );
                                 }

                                 this.isMapEntry = readArrayElement_notArrayOrList_isMapEntry___check;
                              }

                              int var18;
                              this.state_0_ = var18 = state_0 | 134217728;
                              NotArrayOrList_duplicateFound_ = true;
                           }
                        }
                     }
                  }

                  if (!NotArrayOrList_duplicateFound_) {
                     throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
                  }

                  lock.unlock();
                  hasLock = false;
                  return HostObject.ReadArrayElement.doNotArrayOrList(arg0Value, arg1Value, this.isArray, this.isList, this.isMapEntry);
               }

               lock.unlock();
               hasLock = false;
               readArrayElement_list_isList___check = (HostObject.IsListNode)HostObject.ReadArrayElement.doMapEntry(
                  arg0Value, arg1Value, this.isMapEntry, this.toGuest, this.error
               );
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return readArrayElement_list_isList___check;
         }

         @Override
         public long getArraySize(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & -268435456) != 0) {
               if ((state_0 & 268435456) != 0 && this.isArray.execute(arg0Value)) {
                  return HostObject.GetArraySize.doArray(arg0Value, this.isArray);
               }

               if ((state_0 & 536870912) != 0 && this.isList.execute(arg0Value)) {
                  return HostObject.GetArraySize.doList(arg0Value, this.isList, this.error);
               }

               if ((state_0 & 1073741824) != 0 && this.isMapEntry.execute(arg0Value)) {
                  return HostObject.GetArraySize.doMapEntry(arg0Value, this.isMapEntry);
               }

               if ((state_0 & -2147483648) != 0 && !this.isArray.execute(arg0Value) && !this.isList.execute(arg0Value) && !this.isMapEntry.execute(arg0Value)) {
                  return HostObject.GetArraySize.doNotArrayOrList(arg0Value, this.isArray, this.isList, this.isMapEntry);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.getArraySizeAndSpecialize(arg0Value);
         }

         private long getArraySizeAndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            long var23;
            try {
               int state_0 = this.state_0_;
               boolean Array_duplicateFound_ = false;
               if ((state_0 & 268435456) != 0 && this.isArray.execute(arg0Value)) {
                  Array_duplicateFound_ = true;
               }

               if (!Array_duplicateFound_) {
                  HostObject.IsArrayNode getArraySize_array_isArray__ = super.insert(
                     this.isArray == null ? HostObjectFactory.IsArrayNodeGen.create() : this.isArray
                  );
                  if (getArraySize_array_isArray__.execute(arg0Value) && (state_0 & 268435456) == 0) {
                     if (this.isArray == null) {
                        HostObject.IsArrayNode getArraySize_array_isArray___check = super.insert(getArraySize_array_isArray__);
                        if (getArraySize_array_isArray___check == null) {
                           throw new AssertionError(
                              "Specialization 'doArray(HostObject, IsArrayNode)' contains a shared cache with name 'isArray' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isArray = getArraySize_array_isArray___check;
                     }

                     this.state_0_ = state_0 |= 268435456;
                     Array_duplicateFound_ = true;
                  }
               }

               if (Array_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return HostObject.GetArraySize.doArray(arg0Value, this.isArray);
               }

               boolean List_duplicateFound_ = false;
               if ((state_0 & 536870912) != 0 && this.isList.execute(arg0Value)) {
                  List_duplicateFound_ = true;
               }

               if (!List_duplicateFound_) {
                  HostObject.IsListNode getArraySize_list_isList__ = super.insert(this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList);
                  if (getArraySize_list_isList__.execute(arg0Value) && (state_0 & 536870912) == 0) {
                     if (this.isList == null) {
                        HostObject.IsListNode getArraySize_list_isList___check = super.insert(getArraySize_list_isList__);
                        if (getArraySize_list_isList___check == null) {
                           throw new AssertionError(
                              "Specialization 'doList(HostObject, IsListNode, BranchProfile)' contains a shared cache with name 'isList' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isList = getArraySize_list_isList___check;
                     }

                     this.error = this.error == null ? BranchProfile.create() : this.error;
                     this.state_0_ = state_0 |= 536870912;
                     List_duplicateFound_ = true;
                  }
               }

               if (List_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return HostObject.GetArraySize.doList(arg0Value, this.isList, this.error);
               }

               boolean MapEntry_duplicateFound_ = false;
               if ((state_0 & 1073741824) != 0 && this.isMapEntry.execute(arg0Value)) {
                  MapEntry_duplicateFound_ = true;
               }

               if (!MapEntry_duplicateFound_) {
                  HostObject.IsMapEntryNode getArraySize_mapEntry_isMapEntry__ = super.insert(
                     this.isMapEntry == null ? HostObjectFactory.IsMapEntryNodeGen.create() : this.isMapEntry
                  );
                  if (getArraySize_mapEntry_isMapEntry__.execute(arg0Value) && (state_0 & 1073741824) == 0) {
                     if (this.isMapEntry == null) {
                        HostObject.IsMapEntryNode getArraySize_mapEntry_isMapEntry___check = super.insert(getArraySize_mapEntry_isMapEntry__);
                        if (getArraySize_mapEntry_isMapEntry___check == null) {
                           throw new AssertionError(
                              "Specialization 'doMapEntry(HostObject, IsMapEntryNode)' contains a shared cache with name 'isMapEntry' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isMapEntry = getArraySize_mapEntry_isMapEntry___check;
                     }

                     this.state_0_ = state_0 |= 1073741824;
                     MapEntry_duplicateFound_ = true;
                  }
               }

               if (!MapEntry_duplicateFound_) {
                  boolean NotArrayOrList_duplicateFound_ = false;
                  if ((state_0 & -2147483648) != 0
                     && !this.isArray.execute(arg0Value)
                     && !this.isList.execute(arg0Value)
                     && !this.isMapEntry.execute(arg0Value)) {
                     NotArrayOrList_duplicateFound_ = true;
                  }

                  if (!NotArrayOrList_duplicateFound_) {
                     HostObject.IsArrayNode getArraySize_notArrayOrList_isArray__ = super.insert(
                        this.isArray == null ? HostObjectFactory.IsArrayNodeGen.create() : this.isArray
                     );
                     if (!getArraySize_notArrayOrList_isArray__.execute(arg0Value)) {
                        HostObject.IsListNode getArraySize_notArrayOrList_isList__ = super.insert(
                           this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList
                        );
                        if (!getArraySize_notArrayOrList_isList__.execute(arg0Value)) {
                           HostObject.IsMapEntryNode getArraySize_notArrayOrList_isMapEntry__ = super.insert(
                              this.isMapEntry == null ? HostObjectFactory.IsMapEntryNodeGen.create() : this.isMapEntry
                           );
                           if (!getArraySize_notArrayOrList_isMapEntry__.execute(arg0Value) && (state_0 & -2147483648) == 0) {
                              if (this.isArray == null) {
                                 HostObject.IsArrayNode getArraySize_notArrayOrList_isArray___check = super.insert(getArraySize_notArrayOrList_isArray__);
                                 if (getArraySize_notArrayOrList_isArray___check == null) {
                                    throw new AssertionError(
                                       "Specialization 'doNotArrayOrList(HostObject, IsArrayNode, IsListNode, IsMapEntryNode)' contains a shared cache with name 'isArray' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                                    );
                                 }

                                 this.isArray = getArraySize_notArrayOrList_isArray___check;
                              }

                              if (this.isList == null) {
                                 HostObject.IsListNode getArraySize_notArrayOrList_isList___check = super.insert(getArraySize_notArrayOrList_isList__);
                                 if (getArraySize_notArrayOrList_isList___check == null) {
                                    throw new AssertionError(
                                       "Specialization 'doNotArrayOrList(HostObject, IsArrayNode, IsListNode, IsMapEntryNode)' contains a shared cache with name 'isList' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                                    );
                                 }

                                 this.isList = getArraySize_notArrayOrList_isList___check;
                              }

                              if (this.isMapEntry == null) {
                                 HostObject.IsMapEntryNode getArraySize_notArrayOrList_isMapEntry___check = super.insert(
                                    getArraySize_notArrayOrList_isMapEntry__
                                 );
                                 if (getArraySize_notArrayOrList_isMapEntry___check == null) {
                                    throw new AssertionError(
                                       "Specialization 'doNotArrayOrList(HostObject, IsArrayNode, IsListNode, IsMapEntryNode)' contains a shared cache with name 'isMapEntry' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                                    );
                                 }

                                 this.isMapEntry = getArraySize_notArrayOrList_isMapEntry___check;
                              }

                              int var16;
                              this.state_0_ = var16 = state_0 | -2147483648;
                              NotArrayOrList_duplicateFound_ = true;
                           }
                        }
                     }
                  }

                  if (!NotArrayOrList_duplicateFound_) {
                     throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
                  }

                  lock.unlock();
                  hasLock = false;
                  return HostObject.GetArraySize.doNotArrayOrList(arg0Value, this.isArray, this.isList, this.isMapEntry);
               }

               lock.unlock();
               hasLock = false;
               var23 = HostObject.GetArraySize.doMapEntry(arg0Value, this.isMapEntry);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var23;
         }

         @Override
         public boolean isInstantiable(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_1 = this.state_1_;
            if ((state_1 & 7) != 0) {
               if ((state_1 & 1) != 0 && !arg0Value.isClass()) {
                  return HostObject.IsInstantiable.doUnsupported(arg0Value);
               }

               if ((state_1 & 2) != 0 && arg0Value.isArrayClass()) {
                  return HostObject.IsInstantiable.doArrayCached(arg0Value);
               }

               if ((state_1 & 4) != 0 && arg0Value.isDefaultClass()) {
                  return HostObject.IsInstantiable.doObjectCached(arg0Value, this.lookupConstructor);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.isInstantiableAndSpecialize(arg0Value);
         }

         private boolean isInstantiableAndSpecialize(HostObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var5;
            try {
               int state_1 = this.state_1_;
               if (!arg0Value.isClass()) {
                  int var11;
                  this.state_1_ = var11 = state_1 | 1;
                  lock.unlock();
                  hasLock = false;
                  return HostObject.IsInstantiable.doUnsupported(arg0Value);
               }

               if (arg0Value.isArrayClass()) {
                  int var10;
                  this.state_1_ = var10 = state_1 | 2;
                  lock.unlock();
                  hasLock = false;
                  return HostObject.IsInstantiable.doArrayCached(arg0Value);
               }

               if (!arg0Value.isDefaultClass()) {
                  throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
               }

               this.lookupConstructor = super.insert(
                  this.lookupConstructor == null ? HostObjectFactory.LookupConstructorNodeGen.create() : this.lookupConstructor
               );
               int var9;
               this.state_1_ = var9 = state_1 | 4;
               lock.unlock();
               hasLock = false;
               var5 = HostObject.IsInstantiable.doObjectCached(arg0Value, this.lookupConstructor);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var5;
         }

         @Override
         public Object instantiate(Object arg0Value_, Object... arg1Value) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_1 = this.state_1_;
            if ((state_1 & 56) != 0) {
               if ((state_1 & 8) != 0 && !arg0Value.isClass()) {
                  return HostObject.Instantiate.doUnsupported(arg0Value, arg1Value);
               }

               if ((state_1 & 16) != 0 && arg0Value.isArrayClass()) {
                  return HostObject.Instantiate.doArrayCached(arg0Value, arg1Value, this.instantiate_arrayCached_indexes_, this.error);
               }

               if ((state_1 & 32) != 0 && arg0Value.isDefaultClass()) {
                  return HostObject.Instantiate.doObjectCached(arg0Value, arg1Value, this.lookupConstructor, this.hostExecute, this.error);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.instantiateAndSpecialize(arg0Value, arg1Value);
         }

         private Object instantiateAndSpecialize(HostObject arg0Value, Object[] arg1Value) throws UnsupportedMessageException, UnsupportedTypeException, ArityException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var6;
            try {
               int state_1 = this.state_1_;
               if (!arg0Value.isClass()) {
                  int var12;
                  this.state_1_ = var12 = state_1 | 8;
                  lock.unlock();
                  hasLock = false;
                  return HostObject.Instantiate.doUnsupported(arg0Value, arg1Value);
               }

               if (arg0Value.isArrayClass()) {
                  this.instantiate_arrayCached_indexes_ = super.insert(HostObjectGen.INTEROP_LIBRARY_.createDispatched(1));
                  this.error = this.error == null ? BranchProfile.create() : this.error;
                  int var11;
                  this.state_1_ = var11 = state_1 | 16;
                  lock.unlock();
                  hasLock = false;
                  return HostObject.Instantiate.doArrayCached(arg0Value, arg1Value, this.instantiate_arrayCached_indexes_, this.error);
               }

               if (!arg0Value.isDefaultClass()) {
                  throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
               }

               this.lookupConstructor = super.insert(
                  this.lookupConstructor == null ? HostObjectFactory.LookupConstructorNodeGen.create() : this.lookupConstructor
               );
               this.hostExecute = super.insert(this.hostExecute == null ? HostExecuteNode.create() : this.hostExecute);
               this.error = this.error == null ? BranchProfile.create() : this.error;
               int var10;
               this.state_1_ = var10 = state_1 | 32;
               lock.unlock();
               hasLock = false;
               var6 = HostObject.Instantiate.doObjectCached(arg0Value, arg1Value, this.lookupConstructor, this.hostExecute, this.error);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public Object getIterator(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_1 = this.state_1_;
            if ((state_1 & 448) != 0) {
               if ((state_1 & 64) != 0 && this.isArray.execute(arg0Value)) {
                  return HostObject.GetIterator.doArray(arg0Value, this.isArray, this.toGuest);
               }

               if ((state_1 & 128) != 0 && this.isIterable.execute(arg0Value)) {
                  return HostObject.GetIterator.doIterable(arg0Value, this.isIterable, this.toGuest, this.error);
               }

               if ((state_1 & 256) != 0 && !this.isArray.execute(arg0Value) && !this.isIterable.execute(arg0Value)) {
                  return HostObject.GetIterator.doNotArrayOrIterable(arg0Value, this.isArray, this.isIterable);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.getIteratorAndSpecialize(arg0Value);
         }

         private Object getIteratorAndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            HostObject.IsArrayNode getIterator_array_isArray___check;
            try {
               int state_1 = this.state_1_;
               boolean Array_duplicateFound_ = false;
               if ((state_1 & 64) != 0 && this.isArray.execute(arg0Value)) {
                  Array_duplicateFound_ = true;
               }

               if (!Array_duplicateFound_) {
                  HostObject.IsArrayNode getIterator_array_isArray__ = super.insert(
                     this.isArray == null ? HostObjectFactory.IsArrayNodeGen.create() : this.isArray
                  );
                  if (getIterator_array_isArray__.execute(arg0Value) && (state_1 & 64) == 0) {
                     if (this.isArray == null) {
                        getIterator_array_isArray___check = super.insert(getIterator_array_isArray__);
                        if (getIterator_array_isArray___check == null) {
                           throw new AssertionError(
                              "Specialization 'doArray(HostObject, IsArrayNode, ToGuestValueNode)' contains a shared cache with name 'isArray' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isArray = getIterator_array_isArray___check;
                     }

                     this.toGuest = super.insert(this.toGuest == null ? HostContextFactory.ToGuestValueNodeGen.create() : this.toGuest);
                     this.state_1_ = state_1 |= 64;
                     Array_duplicateFound_ = true;
                  }
               }

               if (Array_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return HostObject.GetIterator.doArray(arg0Value, this.isArray, this.toGuest);
               }

               boolean Iterable_duplicateFound_ = false;
               if ((state_1 & 128) != 0 && this.isIterable.execute(arg0Value)) {
                  Iterable_duplicateFound_ = true;
               }

               if (!Iterable_duplicateFound_) {
                  getIterator_array_isArray___check = (HostObject.IsIterableNode)super.insert(
                     this.isIterable == null ? HostObjectFactory.IsIterableNodeGen.create() : this.isIterable
                  );
                  if (getIterator_array_isArray___check.execute(arg0Value) && (state_1 & 128) == 0) {
                     if (this.isIterable == null) {
                        HostObject.IsIterableNode getIterator_iterable_isIterable___check = super.insert(getIterator_array_isArray___check);
                        if (getIterator_iterable_isIterable___check == null) {
                           throw new AssertionError(
                              "Specialization 'doIterable(HostObject, IsIterableNode, ToGuestValueNode, BranchProfile)' contains a shared cache with name 'isIterable' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isIterable = getIterator_iterable_isIterable___check;
                     }

                     this.toGuest = super.insert(this.toGuest == null ? HostContextFactory.ToGuestValueNodeGen.create() : this.toGuest);
                     this.error = this.error == null ? BranchProfile.create() : this.error;
                     this.state_1_ = state_1 |= 128;
                     Iterable_duplicateFound_ = true;
                  }
               }

               if (!Iterable_duplicateFound_) {
                  boolean NotArrayOrIterable_duplicateFound_ = false;
                  if ((state_1 & 256) != 0 && !this.isArray.execute(arg0Value) && !this.isIterable.execute(arg0Value)) {
                     NotArrayOrIterable_duplicateFound_ = true;
                  }

                  if (!NotArrayOrIterable_duplicateFound_) {
                     HostObject.IsArrayNode getIterator_notArrayOrIterable_isArray__ = super.insert(
                        this.isArray == null ? HostObjectFactory.IsArrayNodeGen.create() : this.isArray
                     );
                     if (!getIterator_notArrayOrIterable_isArray__.execute(arg0Value)) {
                        HostObject.IsIterableNode getIterator_notArrayOrIterable_isIterable__ = super.insert(
                           this.isIterable == null ? HostObjectFactory.IsIterableNodeGen.create() : this.isIterable
                        );
                        if (!getIterator_notArrayOrIterable_isIterable__.execute(arg0Value) && (state_1 & 256) == 0) {
                           if (this.isArray == null) {
                              HostObject.IsArrayNode getIterator_notArrayOrIterable_isArray___check = super.insert(getIterator_notArrayOrIterable_isArray__);
                              if (getIterator_notArrayOrIterable_isArray___check == null) {
                                 throw new AssertionError(
                                    "Specialization 'doNotArrayOrIterable(HostObject, IsArrayNode, IsIterableNode)' contains a shared cache with name 'isArray' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                                 );
                              }

                              this.isArray = getIterator_notArrayOrIterable_isArray___check;
                           }

                           if (this.isIterable == null) {
                              HostObject.IsIterableNode getIterator_notArrayOrIterable_isIterable___check = super.insert(
                                 getIterator_notArrayOrIterable_isIterable__
                              );
                              if (getIterator_notArrayOrIterable_isIterable___check == null) {
                                 throw new AssertionError(
                                    "Specialization 'doNotArrayOrIterable(HostObject, IsArrayNode, IsIterableNode)' contains a shared cache with name 'isIterable' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                                 );
                              }

                              this.isIterable = getIterator_notArrayOrIterable_isIterable___check;
                           }

                           int var14;
                           this.state_1_ = var14 = state_1 | 256;
                           NotArrayOrIterable_duplicateFound_ = true;
                        }
                     }
                  }

                  if (!NotArrayOrIterable_duplicateFound_) {
                     throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
                  }

                  lock.unlock();
                  hasLock = false;
                  return HostObject.GetIterator.doNotArrayOrIterable(arg0Value, this.isArray, this.isIterable);
               }

               lock.unlock();
               hasLock = false;
               getIterator_array_isArray___check = (HostObject.IsArrayNode)HostObject.GetIterator.doIterable(
                  arg0Value, this.isIterable, this.toGuest, this.error
               );
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return getIterator_array_isArray___check;
         }

         @Override
         public boolean hasIteratorNextElement(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_1 = this.state_1_;
            if ((state_1 & 1536) != 0) {
               if ((state_1 & 512) != 0 && this.isIterator.execute(arg0Value)) {
                  return HostObject.HasIteratorNextElement.doIterator(arg0Value, this.isIterator, this.error);
               }

               if ((state_1 & 1024) != 0 && !this.isIterator.execute(arg0Value)) {
                  return HostObject.HasIteratorNextElement.doNotIterator(arg0Value, this.isIterator);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.hasIteratorNextElementAndSpecialize(arg0Value);
         }

         private boolean hasIteratorNextElementAndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean NotIterator_duplicateFound_;
            try {
               int state_1 = this.state_1_;
               boolean Iterator_duplicateFound_ = false;
               if ((state_1 & 512) != 0 && this.isIterator.execute(arg0Value)) {
                  Iterator_duplicateFound_ = true;
               }

               if (!Iterator_duplicateFound_) {
                  HostObject.IsIteratorNode hasIteratorNextElement_iterator_isIterator__ = super.insert(
                     this.isIterator == null ? HostObjectFactory.IsIteratorNodeGen.create() : this.isIterator
                  );
                  if (hasIteratorNextElement_iterator_isIterator__.execute(arg0Value) && (state_1 & 512) == 0) {
                     if (this.isIterator == null) {
                        HostObject.IsIteratorNode hasIteratorNextElement_iterator_isIterator___check = super.insert(
                           hasIteratorNextElement_iterator_isIterator__
                        );
                        if (hasIteratorNextElement_iterator_isIterator___check == null) {
                           throw new AssertionError(
                              "Specialization 'doIterator(HostObject, IsIteratorNode, BranchProfile)' contains a shared cache with name 'isIterator' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isIterator = hasIteratorNextElement_iterator_isIterator___check;
                     }

                     this.error = this.error == null ? BranchProfile.create() : this.error;
                     this.state_1_ = state_1 |= 512;
                     Iterator_duplicateFound_ = true;
                  }
               }

               if (!Iterator_duplicateFound_) {
                  NotIterator_duplicateFound_ = false;
                  if ((state_1 & 1024) != 0 && !this.isIterator.execute(arg0Value)) {
                     NotIterator_duplicateFound_ = true;
                  }

                  if (!NotIterator_duplicateFound_) {
                     HostObject.IsIteratorNode hasIteratorNextElement_notIterator_isIterator__ = super.insert(
                        this.isIterator == null ? HostObjectFactory.IsIteratorNodeGen.create() : this.isIterator
                     );
                     if (!hasIteratorNextElement_notIterator_isIterator__.execute(arg0Value) && (state_1 & 1024) == 0) {
                        if (this.isIterator == null) {
                           HostObject.IsIteratorNode hasIteratorNextElement_notIterator_isIterator___check = super.insert(
                              hasIteratorNextElement_notIterator_isIterator__
                           );
                           if (hasIteratorNextElement_notIterator_isIterator___check == null) {
                              throw new AssertionError(
                                 "Specialization 'doNotIterator(HostObject, IsIteratorNode)' contains a shared cache with name 'isIterator' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                              );
                           }

                           this.isIterator = hasIteratorNextElement_notIterator_isIterator___check;
                        }

                        int var12;
                        this.state_1_ = var12 = state_1 | 1024;
                        NotIterator_duplicateFound_ = true;
                     }
                  }

                  if (!NotIterator_duplicateFound_) {
                     throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
                  }

                  lock.unlock();
                  hasLock = false;
                  return HostObject.HasIteratorNextElement.doNotIterator(arg0Value, this.isIterator);
               }

               lock.unlock();
               hasLock = false;
               NotIterator_duplicateFound_ = HostObject.HasIteratorNextElement.doIterator(arg0Value, this.isIterator, this.error);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return NotIterator_duplicateFound_;
         }

         @Override
         public Object getIteratorNextElement(Object arg0Value_) throws UnsupportedMessageException, StopIterationException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_1 = this.state_1_;
            if ((state_1 & 6144) != 0) {
               if ((state_1 & 2048) != 0 && this.isIterator.execute(arg0Value)) {
                  return HostObject.GetIteratorNextElement.doIterator(
                     arg0Value, this.isIterator, this.toGuest, this.error, this.getIteratorNextElement_iterator_stopIteration_
                  );
               }

               if ((state_1 & 4096) != 0 && !this.isIterator.execute(arg0Value)) {
                  return HostObject.GetIteratorNextElement.doNotIterator(arg0Value, this.isIterator);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.getIteratorNextElementAndSpecialize(arg0Value);
         }

         private Object getIteratorNextElementAndSpecialize(HostObject arg0Value) throws StopIterationException, UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            HostObject.IsIteratorNode getIteratorNextElement_iterator_isIterator__;
            try {
               int state_1 = this.state_1_;
               boolean Iterator_duplicateFound_ = false;
               if ((state_1 & 2048) != 0 && this.isIterator.execute(arg0Value)) {
                  Iterator_duplicateFound_ = true;
               }

               if (!Iterator_duplicateFound_) {
                  getIteratorNextElement_iterator_isIterator__ = super.insert(
                     this.isIterator == null ? HostObjectFactory.IsIteratorNodeGen.create() : this.isIterator
                  );
                  if (getIteratorNextElement_iterator_isIterator__.execute(arg0Value) && (state_1 & 2048) == 0) {
                     if (this.isIterator == null) {
                        HostObject.IsIteratorNode getIteratorNextElement_iterator_isIterator___check = super.insert(
                           getIteratorNextElement_iterator_isIterator__
                        );
                        if (getIteratorNextElement_iterator_isIterator___check == null) {
                           throw new AssertionError(
                              "Specialization 'doIterator(HostObject, IsIteratorNode, ToGuestValueNode, BranchProfile, BranchProfile)' contains a shared cache with name 'isIterator' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isIterator = getIteratorNextElement_iterator_isIterator___check;
                     }

                     this.toGuest = super.insert(this.toGuest == null ? HostContextFactory.ToGuestValueNodeGen.create() : this.toGuest);
                     this.error = this.error == null ? BranchProfile.create() : this.error;
                     this.getIteratorNextElement_iterator_stopIteration_ = BranchProfile.create();
                     this.state_1_ = state_1 |= 2048;
                     Iterator_duplicateFound_ = true;
                  }
               }

               if (!Iterator_duplicateFound_) {
                  boolean NotIterator_duplicateFound_ = false;
                  if ((state_1 & 4096) != 0 && !this.isIterator.execute(arg0Value)) {
                     NotIterator_duplicateFound_ = true;
                  }

                  if (!NotIterator_duplicateFound_) {
                     HostObject.IsIteratorNode getIteratorNextElement_notIterator_isIterator__ = super.insert(
                        this.isIterator == null ? HostObjectFactory.IsIteratorNodeGen.create() : this.isIterator
                     );
                     if (!getIteratorNextElement_notIterator_isIterator__.execute(arg0Value) && (state_1 & 4096) == 0) {
                        if (this.isIterator == null) {
                           HostObject.IsIteratorNode getIteratorNextElement_notIterator_isIterator___check = super.insert(
                              getIteratorNextElement_notIterator_isIterator__
                           );
                           if (getIteratorNextElement_notIterator_isIterator___check == null) {
                              throw new AssertionError(
                                 "Specialization 'doNotIterator(HostObject, IsIteratorNode)' contains a shared cache with name 'isIterator' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                              );
                           }

                           this.isIterator = getIteratorNextElement_notIterator_isIterator___check;
                        }

                        int var12;
                        this.state_1_ = var12 = state_1 | 4096;
                        NotIterator_duplicateFound_ = true;
                     }
                  }

                  if (!NotIterator_duplicateFound_) {
                     throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
                  }

                  lock.unlock();
                  hasLock = false;
                  return HostObject.GetIteratorNextElement.doNotIterator(arg0Value, this.isIterator);
               }

               lock.unlock();
               hasLock = false;
               getIteratorNextElement_iterator_isIterator__ = (HostObject.IsIteratorNode)HostObject.GetIteratorNextElement.doIterator(
                  arg0Value, this.isIterator, this.toGuest, this.error, this.getIteratorNextElement_iterator_stopIteration_
               );
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return getIteratorNextElement_iterator_isIterator__;
         }

         @Override
         public long getHashSize(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_1 = this.state_1_;
            if ((state_1 & 24576) != 0) {
               if ((state_1 & 8192) != 0 && this.isMap.execute(arg0Value)) {
                  return HostObject.GetHashSize.doMap(arg0Value, this.isMap, this.error);
               }

               if ((state_1 & 16384) != 0 && !this.isMap.execute(arg0Value)) {
                  return HostObject.GetHashSize.doNotMap(arg0Value, this.isMap);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.getHashSizeAndSpecialize(arg0Value);
         }

         private long getHashSizeAndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            long var13;
            try {
               int state_1 = this.state_1_;
               boolean Map_duplicateFound_ = false;
               if ((state_1 & 8192) != 0 && this.isMap.execute(arg0Value)) {
                  Map_duplicateFound_ = true;
               }

               if (!Map_duplicateFound_) {
                  HostObject.IsMapNode getHashSize_map_isMap__ = super.insert(this.isMap == null ? HostObjectFactory.IsMapNodeGen.create() : this.isMap);
                  if (getHashSize_map_isMap__.execute(arg0Value) && (state_1 & 8192) == 0) {
                     if (this.isMap == null) {
                        HostObject.IsMapNode getHashSize_map_isMap___check = super.insert(getHashSize_map_isMap__);
                        if (getHashSize_map_isMap___check == null) {
                           throw new AssertionError(
                              "Specialization 'doMap(HostObject, IsMapNode, BranchProfile)' contains a shared cache with name 'isMap' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isMap = getHashSize_map_isMap___check;
                     }

                     this.error = this.error == null ? BranchProfile.create() : this.error;
                     this.state_1_ = state_1 |= 8192;
                     Map_duplicateFound_ = true;
                  }
               }

               if (!Map_duplicateFound_) {
                  boolean NotMap_duplicateFound_ = false;
                  if ((state_1 & 16384) != 0 && !this.isMap.execute(arg0Value)) {
                     NotMap_duplicateFound_ = true;
                  }

                  if (!NotMap_duplicateFound_) {
                     HostObject.IsMapNode getHashSize_notMap_isMap__ = super.insert(this.isMap == null ? HostObjectFactory.IsMapNodeGen.create() : this.isMap);
                     if (!getHashSize_notMap_isMap__.execute(arg0Value) && (state_1 & 16384) == 0) {
                        if (this.isMap == null) {
                           HostObject.IsMapNode getHashSize_notMap_isMap___check = super.insert(getHashSize_notMap_isMap__);
                           if (getHashSize_notMap_isMap___check == null) {
                              throw new AssertionError(
                                 "Specialization 'doNotMap(HostObject, IsMapNode)' contains a shared cache with name 'isMap' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                              );
                           }

                           this.isMap = getHashSize_notMap_isMap___check;
                        }

                        int var12;
                        this.state_1_ = var12 = state_1 | 16384;
                        NotMap_duplicateFound_ = true;
                     }
                  }

                  if (!NotMap_duplicateFound_) {
                     throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
                  }

                  lock.unlock();
                  hasLock = false;
                  return HostObject.GetHashSize.doNotMap(arg0Value, this.isMap);
               }

               lock.unlock();
               hasLock = false;
               var13 = HostObject.GetHashSize.doMap(arg0Value, this.isMap, this.error);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var13;
         }

         @Override
         public Object readHashValue(Object arg0Value_, Object arg1Value) throws UnsupportedMessageException, UnknownKeyException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_1 = this.state_1_;
            if ((state_1 & 98304) != 0) {
               if ((state_1 & 32768) != 0 && this.isMap.execute(arg0Value)) {
                  return HostObject.ReadHashValue.doMap(arg0Value, arg1Value, this.isMap, this.toHost, this.toGuest, this.error);
               }

               if ((state_1 & 65536) != 0 && !this.isMap.execute(arg0Value)) {
                  return HostObject.ReadHashValue.doNotMap(arg0Value, arg1Value, this.isMap);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.readHashValueAndSpecialize(arg0Value, arg1Value);
         }

         private Object readHashValueAndSpecialize(HostObject arg0Value, Object arg1Value) throws UnknownKeyException, UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            HostObject.IsMapNode readHashValue_map_isMap__;
            try {
               int state_1 = this.state_1_;
               boolean Map_duplicateFound_ = false;
               if ((state_1 & 32768) != 0 && this.isMap.execute(arg0Value)) {
                  Map_duplicateFound_ = true;
               }

               if (!Map_duplicateFound_) {
                  readHashValue_map_isMap__ = super.insert(this.isMap == null ? HostObjectFactory.IsMapNodeGen.create() : this.isMap);
                  if (readHashValue_map_isMap__.execute(arg0Value) && (state_1 & 32768) == 0) {
                     if (this.isMap == null) {
                        HostObject.IsMapNode readHashValue_map_isMap___check = super.insert(readHashValue_map_isMap__);
                        if (readHashValue_map_isMap___check == null) {
                           throw new AssertionError(
                              "Specialization 'doMap(HostObject, Object, IsMapNode, HostToTypeNode, ToGuestValueNode, BranchProfile)' contains a shared cache with name 'isMap' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isMap = readHashValue_map_isMap___check;
                     }

                     this.toHost = super.insert(this.toHost == null ? HostToTypeNodeGen.create() : this.toHost);
                     this.toGuest = super.insert(this.toGuest == null ? HostContextFactory.ToGuestValueNodeGen.create() : this.toGuest);
                     this.error = this.error == null ? BranchProfile.create() : this.error;
                     this.state_1_ = state_1 |= 32768;
                     Map_duplicateFound_ = true;
                  }
               }

               if (!Map_duplicateFound_) {
                  boolean NotMap_duplicateFound_ = false;
                  if ((state_1 & 65536) != 0 && !this.isMap.execute(arg0Value)) {
                     NotMap_duplicateFound_ = true;
                  }

                  if (!NotMap_duplicateFound_) {
                     HostObject.IsMapNode readHashValue_notMap_isMap__ = super.insert(this.isMap == null ? HostObjectFactory.IsMapNodeGen.create() : this.isMap);
                     if (!readHashValue_notMap_isMap__.execute(arg0Value) && (state_1 & 65536) == 0) {
                        if (this.isMap == null) {
                           HostObject.IsMapNode readHashValue_notMap_isMap___check = super.insert(readHashValue_notMap_isMap__);
                           if (readHashValue_notMap_isMap___check == null) {
                              throw new AssertionError(
                                 "Specialization 'doNotMap(HostObject, Object, IsMapNode)' contains a shared cache with name 'isMap' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                              );
                           }

                           this.isMap = readHashValue_notMap_isMap___check;
                        }

                        int var13;
                        this.state_1_ = var13 = state_1 | 65536;
                        NotMap_duplicateFound_ = true;
                     }
                  }

                  if (!NotMap_duplicateFound_) {
                     throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
                  }

                  lock.unlock();
                  hasLock = false;
                  return HostObject.ReadHashValue.doNotMap(arg0Value, arg1Value, this.isMap);
               }

               lock.unlock();
               hasLock = false;
               readHashValue_map_isMap__ = (HostObject.IsMapNode)HostObject.ReadHashValue.doMap(
                  arg0Value, arg1Value, this.isMap, this.toHost, this.toGuest, this.error
               );
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return readHashValue_map_isMap__;
         }

         @Override
         public void writeHashEntry(Object arg0Value_, Object arg1Value, Object arg2Value) throws UnsupportedMessageException, UnknownKeyException, UnsupportedTypeException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_1 = this.state_1_;
            if ((state_1 & 393216) != 0) {
               if ((state_1 & 131072) != 0 && this.isMap.execute(arg0Value)) {
                  HostObject.WriteHashEntry.doMap(arg0Value, arg1Value, arg2Value, this.isMap, this.toHost, this.error);
                  return;
               }

               if ((state_1 & 262144) != 0 && !this.isMap.execute(arg0Value)) {
                  HostObject.WriteHashEntry.doNotMap(arg0Value, arg1Value, arg2Value, this.isMap);
                  return;
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.writeHashEntryAndSpecialize(arg0Value, arg1Value, arg2Value);
         }

         private void writeHashEntryAndSpecialize(HostObject arg0Value, Object arg1Value, Object arg2Value) throws UnsupportedTypeException, UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_1 = this.state_1_;
               boolean Map_duplicateFound_ = false;
               if ((state_1 & 131072) != 0 && this.isMap.execute(arg0Value)) {
                  Map_duplicateFound_ = true;
               }

               if (!Map_duplicateFound_) {
                  HostObject.IsMapNode writeHashEntry_map_isMap__ = super.insert(this.isMap == null ? HostObjectFactory.IsMapNodeGen.create() : this.isMap);
                  if (writeHashEntry_map_isMap__.execute(arg0Value) && (state_1 & 131072) == 0) {
                     if (this.isMap == null) {
                        HostObject.IsMapNode writeHashEntry_map_isMap___check = super.insert(writeHashEntry_map_isMap__);
                        if (writeHashEntry_map_isMap___check == null) {
                           throw new AssertionError(
                              "Specialization 'doMap(HostObject, Object, Object, IsMapNode, HostToTypeNode, BranchProfile)' contains a shared cache with name 'isMap' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isMap = writeHashEntry_map_isMap___check;
                     }

                     this.toHost = super.insert(this.toHost == null ? HostToTypeNodeGen.create() : this.toHost);
                     this.error = this.error == null ? BranchProfile.create() : this.error;
                     this.state_1_ = state_1 |= 131072;
                     Map_duplicateFound_ = true;
                  }
               }

               if (!Map_duplicateFound_) {
                  boolean NotMap_duplicateFound_ = false;
                  if ((state_1 & 262144) != 0 && !this.isMap.execute(arg0Value)) {
                     NotMap_duplicateFound_ = true;
                  }

                  if (!NotMap_duplicateFound_) {
                     HostObject.IsMapNode writeHashEntry_notMap_isMap__ = super.insert(
                        this.isMap == null ? HostObjectFactory.IsMapNodeGen.create() : this.isMap
                     );
                     if (!writeHashEntry_notMap_isMap__.execute(arg0Value) && (state_1 & 262144) == 0) {
                        if (this.isMap == null) {
                           HostObject.IsMapNode writeHashEntry_notMap_isMap___check = super.insert(writeHashEntry_notMap_isMap__);
                           if (writeHashEntry_notMap_isMap___check == null) {
                              throw new AssertionError(
                                 "Specialization 'doNotMap(HostObject, Object, Object, IsMapNode)' contains a shared cache with name 'isMap' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                              );
                           }

                           this.isMap = writeHashEntry_notMap_isMap___check;
                        }

                        int var14;
                        this.state_1_ = var14 = state_1 | 262144;
                        NotMap_duplicateFound_ = true;
                     }
                  }

                  if (!NotMap_duplicateFound_) {
                     throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
                  }

                  lock.unlock();
                  hasLock = false;
                  HostObject.WriteHashEntry.doNotMap(arg0Value, arg1Value, arg2Value, this.isMap);
                  return;
               }

               lock.unlock();
               hasLock = false;
               HostObject.WriteHashEntry.doMap(arg0Value, arg1Value, arg2Value, this.isMap, this.toHost, this.error);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public void removeHashEntry(Object arg0Value_, Object arg1Value) throws UnsupportedMessageException, UnknownKeyException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_1 = this.state_1_;
            if ((state_1 & 1572864) != 0) {
               if ((state_1 & 524288) != 0 && this.isMap.execute(arg0Value)) {
                  HostObject.RemoveHashEntry.doMap(arg0Value, arg1Value, this.isMap, this.toHost, this.error);
                  return;
               }

               if ((state_1 & 1048576) != 0 && !this.isMap.execute(arg0Value)) {
                  HostObject.RemoveHashEntry.doNotMap(arg0Value, arg1Value, this.isMap);
                  return;
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.removeHashEntryAndSpecialize(arg0Value, arg1Value);
         }

         private void removeHashEntryAndSpecialize(HostObject arg0Value, Object arg1Value) throws UnknownKeyException, UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_1 = this.state_1_;
               boolean Map_duplicateFound_ = false;
               if ((state_1 & 524288) != 0 && this.isMap.execute(arg0Value)) {
                  Map_duplicateFound_ = true;
               }

               if (!Map_duplicateFound_) {
                  HostObject.IsMapNode removeHashEntry_map_isMap__ = super.insert(this.isMap == null ? HostObjectFactory.IsMapNodeGen.create() : this.isMap);
                  if (removeHashEntry_map_isMap__.execute(arg0Value) && (state_1 & 524288) == 0) {
                     if (this.isMap == null) {
                        HostObject.IsMapNode removeHashEntry_map_isMap___check = super.insert(removeHashEntry_map_isMap__);
                        if (removeHashEntry_map_isMap___check == null) {
                           throw new AssertionError(
                              "Specialization 'doMap(HostObject, Object, IsMapNode, HostToTypeNode, BranchProfile)' contains a shared cache with name 'isMap' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isMap = removeHashEntry_map_isMap___check;
                     }

                     this.toHost = super.insert(this.toHost == null ? HostToTypeNodeGen.create() : this.toHost);
                     this.error = this.error == null ? BranchProfile.create() : this.error;
                     this.state_1_ = state_1 |= 524288;
                     Map_duplicateFound_ = true;
                  }
               }

               if (!Map_duplicateFound_) {
                  boolean NotMap_duplicateFound_ = false;
                  if ((state_1 & 1048576) != 0 && !this.isMap.execute(arg0Value)) {
                     NotMap_duplicateFound_ = true;
                  }

                  if (!NotMap_duplicateFound_) {
                     HostObject.IsMapNode removeHashEntry_notMap_isMap__ = super.insert(
                        this.isMap == null ? HostObjectFactory.IsMapNodeGen.create() : this.isMap
                     );
                     if (!removeHashEntry_notMap_isMap__.execute(arg0Value) && (state_1 & 1048576) == 0) {
                        if (this.isMap == null) {
                           HostObject.IsMapNode removeHashEntry_notMap_isMap___check = super.insert(removeHashEntry_notMap_isMap__);
                           if (removeHashEntry_notMap_isMap___check == null) {
                              throw new AssertionError(
                                 "Specialization 'doNotMap(HostObject, Object, IsMapNode)' contains a shared cache with name 'isMap' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                              );
                           }

                           this.isMap = removeHashEntry_notMap_isMap___check;
                        }

                        int var13;
                        this.state_1_ = var13 = state_1 | 1048576;
                        NotMap_duplicateFound_ = true;
                     }
                  }

                  if (!NotMap_duplicateFound_) {
                     throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
                  }

                  lock.unlock();
                  hasLock = false;
                  HostObject.RemoveHashEntry.doNotMap(arg0Value, arg1Value, this.isMap);
                  return;
               }

               lock.unlock();
               hasLock = false;
               HostObject.RemoveHashEntry.doMap(arg0Value, arg1Value, this.isMap, this.toHost, this.error);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public Object getHashEntriesIterator(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_1 = this.state_1_;
            if ((state_1 & 6291456) != 0) {
               if ((state_1 & 2097152) != 0 && this.isMap.execute(arg0Value)) {
                  return HostObject.GetHashEntriesIterator.doMap(arg0Value, this.isMap, this.toGuest, this.error);
               }

               if ((state_1 & 4194304) != 0 && !this.isMap.execute(arg0Value)) {
                  return HostObject.GetHashEntriesIterator.doNotMap(arg0Value, this.isMap);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.getHashEntriesIteratorAndSpecialize(arg0Value);
         }

         private Object getHashEntriesIteratorAndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            HostObject.IsMapNode getHashEntriesIterator_map_isMap__;
            try {
               int state_1 = this.state_1_;
               boolean Map_duplicateFound_ = false;
               if ((state_1 & 2097152) != 0 && this.isMap.execute(arg0Value)) {
                  Map_duplicateFound_ = true;
               }

               if (!Map_duplicateFound_) {
                  getHashEntriesIterator_map_isMap__ = super.insert(this.isMap == null ? HostObjectFactory.IsMapNodeGen.create() : this.isMap);
                  if (getHashEntriesIterator_map_isMap__.execute(arg0Value) && (state_1 & 2097152) == 0) {
                     if (this.isMap == null) {
                        HostObject.IsMapNode getHashEntriesIterator_map_isMap___check = super.insert(getHashEntriesIterator_map_isMap__);
                        if (getHashEntriesIterator_map_isMap___check == null) {
                           throw new AssertionError(
                              "Specialization 'doMap(HostObject, IsMapNode, ToGuestValueNode, BranchProfile)' contains a shared cache with name 'isMap' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isMap = getHashEntriesIterator_map_isMap___check;
                     }

                     this.toGuest = super.insert(this.toGuest == null ? HostContextFactory.ToGuestValueNodeGen.create() : this.toGuest);
                     this.error = this.error == null ? BranchProfile.create() : this.error;
                     this.state_1_ = state_1 |= 2097152;
                     Map_duplicateFound_ = true;
                  }
               }

               if (!Map_duplicateFound_) {
                  boolean NotMap_duplicateFound_ = false;
                  if ((state_1 & 4194304) != 0 && !this.isMap.execute(arg0Value)) {
                     NotMap_duplicateFound_ = true;
                  }

                  if (!NotMap_duplicateFound_) {
                     HostObject.IsMapNode getHashEntriesIterator_notMap_isMap__ = super.insert(
                        this.isMap == null ? HostObjectFactory.IsMapNodeGen.create() : this.isMap
                     );
                     if (!getHashEntriesIterator_notMap_isMap__.execute(arg0Value) && (state_1 & 4194304) == 0) {
                        if (this.isMap == null) {
                           HostObject.IsMapNode getHashEntriesIterator_notMap_isMap___check = super.insert(getHashEntriesIterator_notMap_isMap__);
                           if (getHashEntriesIterator_notMap_isMap___check == null) {
                              throw new AssertionError(
                                 "Specialization 'doNotMap(HostObject, IsMapNode)' contains a shared cache with name 'isMap' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                              );
                           }

                           this.isMap = getHashEntriesIterator_notMap_isMap___check;
                        }

                        int var12;
                        this.state_1_ = var12 = state_1 | 4194304;
                        NotMap_duplicateFound_ = true;
                     }
                  }

                  if (!NotMap_duplicateFound_) {
                     throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
                  }

                  lock.unlock();
                  hasLock = false;
                  return HostObject.GetHashEntriesIterator.doNotMap(arg0Value, this.isMap);
               }

               lock.unlock();
               hasLock = false;
               getHashEntriesIterator_map_isMap__ = (HostObject.IsMapNode)HostObject.GetHashEntriesIterator.doMap(
                  arg0Value, this.isMap, this.toGuest, this.error
               );
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return getHashEntriesIterator_map_isMap__;
         }

         @Override
         protected TriState isIdenticalOrUndefined(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_1 = this.state_1_;
            if ((state_1 & 25165824) != 0) {
               if ((state_1 & 8388608) != 0 && arg1Value instanceof HostObject) {
                  HostObject arg1Value_ = (HostObject)arg1Value;
                  return HostObject.IsIdenticalOrUndefined.doHostObject(arg0Value, arg1Value_);
               }

               if ((state_1 & 16777216) != 0 && isIdenticalOrUndefinedFallbackGuard_(state_1, arg0Value, arg1Value)) {
                  return HostObject.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.isIdenticalOrUndefinedAndSpecialize(arg0Value, arg1Value);
         }

         private TriState isIdenticalOrUndefinedAndSpecialize(HostObject arg0Value, Object arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            TriState var7;
            try {
               int state_1 = this.state_1_;
               if (!(arg1Value instanceof HostObject)) {
                  int var12;
                  this.state_1_ = var12 = state_1 | 16777216;
                  lock.unlock();
                  hasLock = false;
                  return HostObject.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
               }

               HostObject arg1Value_ = (HostObject)arg1Value;
               int var11;
               this.state_1_ = var11 = state_1 | 8388608;
               lock.unlock();
               hasLock = false;
               var7 = HostObject.IsIdenticalOrUndefined.doHostObject(arg0Value, arg1Value_);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public boolean hasMembers(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).hasMembers();
         }

         @Override
         public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).getMembers(includeInternal);
         }

         @Override
         public Object readMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_1 = this.state_1_;
            if ((state_1 & 33554432) != 0) {
               return arg0Value.readMember(
                  arg1Value, this.lookupField, this.readField, this.lookupMethod, this.readMemberNode__readMember_lookupInnerClass_, this.error
               );
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.readMemberNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private Object readMemberNode_AndSpecialize(HostObject arg0Value, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var6;
            try {
               int state_1 = this.state_1_;
               this.lookupField = super.insert(this.lookupField == null ? HostObjectFactory.LookupFieldNodeGen.create() : this.lookupField);
               this.readField = super.insert(this.readField == null ? HostObjectFactory.ReadFieldNodeGen.create() : this.readField);
               this.lookupMethod = super.insert(this.lookupMethod == null ? HostObjectFactory.LookupMethodNodeGen.create() : this.lookupMethod);
               this.readMemberNode__readMember_lookupInnerClass_ = super.insert(HostObjectFactory.LookupInnerClassNodeGen.create());
               this.error = this.error == null ? BranchProfile.create() : this.error;
               int var10;
               this.state_1_ = var10 = state_1 | 33554432;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.readMember(
                  arg1Value, this.lookupField, this.readField, this.lookupMethod, this.readMemberNode__readMember_lookupInnerClass_, this.error
               );
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public boolean isMemberInsertable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).isMemberInsertable(member);
         }

         @Override
         public void writeMember(Object arg0Value_, String arg1Value, Object arg2Value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_1 = this.state_1_;
            if ((state_1 & 67108864) != 0) {
               arg0Value.writeMember(arg1Value, arg2Value, this.lookupField, this.writeMemberNode__writeMember_writeField_, this.error);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.writeMemberNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }
         }

         private void writeMemberNode_AndSpecialize(HostObject arg0Value, String arg1Value, Object arg2Value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_1 = this.state_1_;
               this.lookupField = super.insert(this.lookupField == null ? HostObjectFactory.LookupFieldNodeGen.create() : this.lookupField);
               this.writeMemberNode__writeMember_writeField_ = super.insert(HostObjectFactory.WriteFieldNodeGen.create());
               this.error = this.error == null ? BranchProfile.create() : this.error;
               int var10;
               this.state_1_ = var10 = state_1 | 67108864;
               lock.unlock();
               hasLock = false;
               arg0Value.writeMember(arg1Value, arg2Value, this.lookupField, this.writeMemberNode__writeMember_writeField_, this.error);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public Object invokeMember(Object arg0Value_, String arg1Value, Object... arg2Value) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_1 = this.state_1_;
            if ((state_1 & 134217728) != 0) {
               return arg0Value.invokeMember(
                  arg1Value,
                  arg2Value,
                  this.lookupMethod,
                  this.hostExecute,
                  this.lookupField,
                  this.readField,
                  this.invokeMemberNode__invokeMember_fieldValues_,
                  this.error
               );
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.invokeMemberNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }
         }

         private Object invokeMemberNode_AndSpecialize(HostObject arg0Value, String arg1Value, Object[] arg2Value) throws UnsupportedTypeException, ArityException, UnsupportedMessageException, UnknownIdentifierException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var7;
            try {
               int state_1 = this.state_1_;
               this.lookupMethod = super.insert(this.lookupMethod == null ? HostObjectFactory.LookupMethodNodeGen.create() : this.lookupMethod);
               this.hostExecute = super.insert(this.hostExecute == null ? HostExecuteNode.create() : this.hostExecute);
               this.lookupField = super.insert(this.lookupField == null ? HostObjectFactory.LookupFieldNodeGen.create() : this.lookupField);
               this.readField = super.insert(this.readField == null ? HostObjectFactory.ReadFieldNodeGen.create() : this.readField);
               this.invokeMemberNode__invokeMember_fieldValues_ = super.insert(HostObjectGen.INTEROP_LIBRARY_.createDispatched(5));
               this.error = this.error == null ? BranchProfile.create() : this.error;
               int var11;
               this.state_1_ = var11 = state_1 | 134217728;
               lock.unlock();
               hasLock = false;
               var7 = arg0Value.invokeMember(
                  arg1Value,
                  arg2Value,
                  this.lookupMethod,
                  this.hostExecute,
                  this.lookupField,
                  this.readField,
                  this.invokeMemberNode__invokeMember_fieldValues_,
                  this.error
               );
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public boolean isArrayElementInsertable(Object arg0Value_, long arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_1 = this.state_1_;
            if ((state_1 & 268435456) != 0) {
               return arg0Value.isArrayElementInsertable(arg1Value, this.isList, this.error);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isArrayElementInsertableNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isArrayElementInsertableNode_AndSpecialize(HostObject arg0Value, long arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var7;
            try {
               int state_1 = this.state_1_;
               this.isList = super.insert(this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList);
               this.error = this.error == null ? BranchProfile.create() : this.error;
               int var11;
               this.state_1_ = var11 = state_1 | 268435456;
               lock.unlock();
               hasLock = false;
               var7 = arg0Value.isArrayElementInsertable(arg1Value, this.isList, this.error);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public boolean hasArrayElements(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_1 = this.state_1_;
            if ((state_1 & 536870912) != 0) {
               return arg0Value.hasArrayElements(this.isList, this.isArray, this.isMapEntry);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.hasArrayElementsNode_AndSpecialize(arg0Value);
            }
         }

         private boolean hasArrayElementsNode_AndSpecialize(HostObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var5;
            try {
               int state_1 = this.state_1_;
               this.isList = super.insert(this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList);
               this.isArray = super.insert(this.isArray == null ? HostObjectFactory.IsArrayNodeGen.create() : this.isArray);
               this.isMapEntry = super.insert(this.isMapEntry == null ? HostObjectFactory.IsMapEntryNodeGen.create() : this.isMapEntry);
               int var9;
               this.state_1_ = var9 = state_1 | 536870912;
               lock.unlock();
               hasLock = false;
               var5 = arg0Value.hasArrayElements(this.isList, this.isArray, this.isMapEntry);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var5;
         }

         @Override
         public boolean hasBufferElements(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_1 = this.state_1_;
            if ((state_1 & 1073741824) != 0) {
               return arg0Value.hasBufferElements(this.isBuffer);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.hasBufferElementsNode_AndSpecialize(arg0Value);
            }
         }

         private boolean hasBufferElementsNode_AndSpecialize(HostObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var5;
            try {
               int state_1 = this.state_1_;
               this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
               int var9;
               this.state_1_ = var9 = state_1 | 1073741824;
               lock.unlock();
               hasLock = false;
               var5 = arg0Value.hasBufferElements(this.isBuffer);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var5;
         }

         @Override
         public boolean isBufferWritable(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_1 = this.state_1_;
            if ((state_1 & -2147483648) != 0) {
               return arg0Value.isBufferWritable(this.isBuffer, this.error);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isBufferWritableNode_AndSpecialize(arg0Value);
            }
         }

         private boolean isBufferWritableNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var5;
            try {
               int state_1 = this.state_1_;
               this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
               this.error = this.error == null ? BranchProfile.create() : this.error;
               int var9;
               this.state_1_ = var9 = state_1 | -2147483648;
               lock.unlock();
               hasLock = false;
               var5 = arg0Value.isBufferWritable(this.isBuffer, this.error);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var5;
         }

         @Override
         public long getBufferSize(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 1) != 0) {
               return arg0Value.getBufferSize(this.isBuffer, this.error);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.getBufferSizeNode_AndSpecialize(arg0Value);
            }
         }

         private long getBufferSizeNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            long var5;
            try {
               int state_2 = this.state_2_;
               this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
               this.error = this.error == null ? BranchProfile.create() : this.error;
               int var10;
               this.state_2_ = var10 = state_2 | 1;
               lock.unlock();
               hasLock = false;
               var5 = arg0Value.getBufferSize(this.isBuffer, this.error);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var5;
         }

         @Override
         public byte readBufferByte(Object arg0Value_, long arg1Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 2) != 0) {
               return arg0Value.readBufferByte(arg1Value, this.isBuffer, this.error, this.classProfile);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.readBufferByteNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private byte readBufferByteNode_AndSpecialize(HostObject arg0Value, long arg1Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            byte var7;
            try {
               int state_2 = this.state_2_;
               this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
               this.error = this.error == null ? BranchProfile.create() : this.error;
               this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
               int var11;
               this.state_2_ = var11 = state_2 | 2;
               lock.unlock();
               hasLock = false;
               var7 = arg0Value.readBufferByte(arg1Value, this.isBuffer, this.error, this.classProfile);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public void writeBufferByte(Object arg0Value_, long arg1Value, byte arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 4) != 0) {
               arg0Value.writeBufferByte(arg1Value, arg2Value, this.isBuffer, this.error, this.classProfile);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.writeBufferByteNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }
         }

         private void writeBufferByteNode_AndSpecialize(HostObject arg0Value, long arg1Value, byte arg2Value) throws InvalidBufferOffsetException, UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_2 = this.state_2_;
               this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
               this.error = this.error == null ? BranchProfile.create() : this.error;
               this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
               int var11;
               this.state_2_ = var11 = state_2 | 4;
               lock.unlock();
               hasLock = false;
               arg0Value.writeBufferByte(arg1Value, arg2Value, this.isBuffer, this.error, this.classProfile);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public short readBufferShort(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 8) != 0) {
               return arg0Value.readBufferShort(arg1Value, arg2Value, this.isBuffer, this.error, this.classProfile);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.readBufferShortNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }
         }

         private short readBufferShortNode_AndSpecialize(HostObject arg0Value, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            short var8;
            try {
               int state_2 = this.state_2_;
               this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
               this.error = this.error == null ? BranchProfile.create() : this.error;
               this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
               int var12;
               this.state_2_ = var12 = state_2 | 8;
               lock.unlock();
               hasLock = false;
               var8 = arg0Value.readBufferShort(arg1Value, arg2Value, this.isBuffer, this.error, this.classProfile);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var8;
         }

         @Override
         public void writeBufferShort(Object arg0Value_, ByteOrder arg1Value, long arg2Value, short arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 16) != 0) {
               arg0Value.writeBufferShort(arg1Value, arg2Value, arg3Value, this.isBuffer, this.error, this.classProfile);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.writeBufferShortNode_AndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
            }
         }

         private void writeBufferShortNode_AndSpecialize(HostObject arg0Value, ByteOrder arg1Value, long arg2Value, short arg3Value) throws InvalidBufferOffsetException, UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_2 = this.state_2_;
               this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
               this.error = this.error == null ? BranchProfile.create() : this.error;
               this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
               int var12;
               this.state_2_ = var12 = state_2 | 16;
               lock.unlock();
               hasLock = false;
               arg0Value.writeBufferShort(arg1Value, arg2Value, arg3Value, this.isBuffer, this.error, this.classProfile);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public int readBufferInt(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 32) != 0) {
               return arg0Value.readBufferInt(arg1Value, arg2Value, this.isBuffer, this.error, this.classProfile);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.readBufferIntNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }
         }

         private int readBufferIntNode_AndSpecialize(HostObject arg0Value, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            int var8;
            try {
               int state_2 = this.state_2_;
               this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
               this.error = this.error == null ? BranchProfile.create() : this.error;
               this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
               int var12;
               this.state_2_ = var12 = state_2 | 32;
               lock.unlock();
               hasLock = false;
               var8 = arg0Value.readBufferInt(arg1Value, arg2Value, this.isBuffer, this.error, this.classProfile);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var8;
         }

         @Override
         public void writeBufferInt(Object arg0Value_, ByteOrder arg1Value, long arg2Value, int arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 64) != 0) {
               arg0Value.writeBufferInt(arg1Value, arg2Value, arg3Value, this.isBuffer, this.error, this.classProfile);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.writeBufferIntNode_AndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
            }
         }

         private void writeBufferIntNode_AndSpecialize(HostObject arg0Value, ByteOrder arg1Value, long arg2Value, int arg3Value) throws InvalidBufferOffsetException, UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_2 = this.state_2_;
               this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
               this.error = this.error == null ? BranchProfile.create() : this.error;
               this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
               int var12;
               this.state_2_ = var12 = state_2 | 64;
               lock.unlock();
               hasLock = false;
               arg0Value.writeBufferInt(arg1Value, arg2Value, arg3Value, this.isBuffer, this.error, this.classProfile);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public long readBufferLong(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 128) != 0) {
               return arg0Value.readBufferLong(arg1Value, arg2Value, this.isBuffer, this.error, this.classProfile);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.readBufferLongNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }
         }

         private long readBufferLongNode_AndSpecialize(HostObject arg0Value, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            long var8;
            try {
               int state_2 = this.state_2_;
               this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
               this.error = this.error == null ? BranchProfile.create() : this.error;
               this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
               int var13;
               this.state_2_ = var13 = state_2 | 128;
               lock.unlock();
               hasLock = false;
               var8 = arg0Value.readBufferLong(arg1Value, arg2Value, this.isBuffer, this.error, this.classProfile);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var8;
         }

         @Override
         public void writeBufferLong(Object arg0Value_, ByteOrder arg1Value, long arg2Value, long arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 256) != 0) {
               arg0Value.writeBufferLong(arg1Value, arg2Value, arg3Value, this.isBuffer, this.error, this.classProfile);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.writeBufferLongNode_AndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
            }
         }

         private void writeBufferLongNode_AndSpecialize(HostObject arg0Value, ByteOrder arg1Value, long arg2Value, long arg3Value) throws InvalidBufferOffsetException, UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_2 = this.state_2_;
               this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
               this.error = this.error == null ? BranchProfile.create() : this.error;
               this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
               int var13;
               this.state_2_ = var13 = state_2 | 256;
               lock.unlock();
               hasLock = false;
               arg0Value.writeBufferLong(arg1Value, arg2Value, arg3Value, this.isBuffer, this.error, this.classProfile);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public float readBufferFloat(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 512) != 0) {
               return arg0Value.readBufferFloat(arg1Value, arg2Value, this.isBuffer, this.error, this.classProfile);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.readBufferFloatNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }
         }

         private float readBufferFloatNode_AndSpecialize(HostObject arg0Value, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            float var8;
            try {
               int state_2 = this.state_2_;
               this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
               this.error = this.error == null ? BranchProfile.create() : this.error;
               this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
               int var12;
               this.state_2_ = var12 = state_2 | 512;
               lock.unlock();
               hasLock = false;
               var8 = arg0Value.readBufferFloat(arg1Value, arg2Value, this.isBuffer, this.error, this.classProfile);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var8;
         }

         @Override
         public void writeBufferFloat(Object arg0Value_, ByteOrder arg1Value, long arg2Value, float arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 1024) != 0) {
               arg0Value.writeBufferFloat(arg1Value, arg2Value, arg3Value, this.isBuffer, this.error, this.classProfile);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.writeBufferFloatNode_AndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
            }
         }

         private void writeBufferFloatNode_AndSpecialize(HostObject arg0Value, ByteOrder arg1Value, long arg2Value, float arg3Value) throws InvalidBufferOffsetException, UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_2 = this.state_2_;
               this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
               this.error = this.error == null ? BranchProfile.create() : this.error;
               this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
               int var12;
               this.state_2_ = var12 = state_2 | 1024;
               lock.unlock();
               hasLock = false;
               arg0Value.writeBufferFloat(arg1Value, arg2Value, arg3Value, this.isBuffer, this.error, this.classProfile);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public double readBufferDouble(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 2048) != 0) {
               return arg0Value.readBufferDouble(arg1Value, arg2Value, this.isBuffer, this.error, this.classProfile);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.readBufferDoubleNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }
         }

         private double readBufferDoubleNode_AndSpecialize(HostObject arg0Value, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            double var8;
            try {
               int state_2 = this.state_2_;
               this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
               this.error = this.error == null ? BranchProfile.create() : this.error;
               this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
               int var13;
               this.state_2_ = var13 = state_2 | 2048;
               lock.unlock();
               hasLock = false;
               var8 = arg0Value.readBufferDouble(arg1Value, arg2Value, this.isBuffer, this.error, this.classProfile);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var8;
         }

         @Override
         public void writeBufferDouble(Object arg0Value_, ByteOrder arg1Value, long arg2Value, double arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 4096) != 0) {
               arg0Value.writeBufferDouble(arg1Value, arg2Value, arg3Value, this.isBuffer, this.error, this.classProfile);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.writeBufferDoubleNode_AndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
            }
         }

         private void writeBufferDoubleNode_AndSpecialize(HostObject arg0Value, ByteOrder arg1Value, long arg2Value, double arg3Value) throws InvalidBufferOffsetException, UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_2 = this.state_2_;
               this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
               this.error = this.error == null ? BranchProfile.create() : this.error;
               this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
               int var13;
               this.state_2_ = var13 = state_2 | 4096;
               lock.unlock();
               hasLock = false;
               arg0Value.writeBufferDouble(arg1Value, arg2Value, arg3Value, this.isBuffer, this.error, this.classProfile);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public boolean isNull(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).isNull();
         }

         @Override
         public boolean isExecutable(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 8192) != 0) {
               return arg0Value.isExecutable(this.lookupFunctionalMethod);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isExecutableNode_AndSpecialize(arg0Value);
            }
         }

         private boolean isExecutableNode_AndSpecialize(HostObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var5;
            try {
               int state_2 = this.state_2_;
               this.lookupFunctionalMethod = super.insert(
                  this.lookupFunctionalMethod == null ? HostObjectFactory.LookupFunctionalMethodNodeGen.create() : this.lookupFunctionalMethod
               );
               int var9;
               this.state_2_ = var9 = state_2 | 8192;
               lock.unlock();
               hasLock = false;
               var5 = arg0Value.isExecutable(this.lookupFunctionalMethod);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var5;
         }

         @Override
         public Object execute(Object arg0Value_, Object... arg1Value) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 16384) != 0) {
               return arg0Value.execute(arg1Value, this.hostExecute, this.lookupFunctionalMethod, this.error);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.executeNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private Object executeNode_AndSpecialize(HostObject arg0Value, Object[] arg1Value) throws UnsupportedMessageException, UnsupportedTypeException, ArityException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var6;
            try {
               int state_2 = this.state_2_;
               this.hostExecute = super.insert(this.hostExecute == null ? HostExecuteNode.create() : this.hostExecute);
               this.lookupFunctionalMethod = super.insert(
                  this.lookupFunctionalMethod == null ? HostObjectFactory.LookupFunctionalMethodNodeGen.create() : this.lookupFunctionalMethod
               );
               this.error = this.error == null ? BranchProfile.create() : this.error;
               int var10;
               this.state_2_ = var10 = state_2 | 16384;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.execute(arg1Value, this.hostExecute, this.lookupFunctionalMethod, this.error);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public boolean isNumber(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 32768) != 0) {
               return arg0Value.isNumber(this.classProfile);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isNumberNode_AndSpecialize(arg0Value);
            }
         }

         private boolean isNumberNode_AndSpecialize(HostObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var5;
            try {
               int state_2 = this.state_2_;
               this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
               int var9;
               this.state_2_ = var9 = state_2 | 32768;
               lock.unlock();
               hasLock = false;
               var5 = arg0Value.isNumber(this.classProfile);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var5;
         }

         @Override
         public boolean fitsInByte(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 65536) != 0) {
               return arg0Value.fitsInByte(this, this.numbers);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.fitsInByteNode_AndSpecialize(arg0Value);
            }
         }

         private boolean fitsInByteNode_AndSpecialize(HostObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_2 = this.state_2_;
               InteropLibrary fitsInByteNode__fitsInByte_thisLibrary__ = null;
               this.numbers = super.insert(this.numbers == null ? HostObjectGen.INTEROP_LIBRARY_.createDispatched(5) : this.numbers);
               int var10;
               this.state_2_ = var10 = state_2 | 65536;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.fitsInByte(this, this.numbers);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public boolean fitsInShort(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 131072) != 0) {
               return arg0Value.fitsInShort(this, this.numbers);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.fitsInShortNode_AndSpecialize(arg0Value);
            }
         }

         private boolean fitsInShortNode_AndSpecialize(HostObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_2 = this.state_2_;
               InteropLibrary fitsInShortNode__fitsInShort_thisLibrary__ = null;
               this.numbers = super.insert(this.numbers == null ? HostObjectGen.INTEROP_LIBRARY_.createDispatched(5) : this.numbers);
               int var10;
               this.state_2_ = var10 = state_2 | 131072;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.fitsInShort(this, this.numbers);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public boolean fitsInInt(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 262144) != 0) {
               return arg0Value.fitsInInt(this, this.numbers);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.fitsInIntNode_AndSpecialize(arg0Value);
            }
         }

         private boolean fitsInIntNode_AndSpecialize(HostObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_2 = this.state_2_;
               InteropLibrary fitsInIntNode__fitsInInt_thisLibrary__ = null;
               this.numbers = super.insert(this.numbers == null ? HostObjectGen.INTEROP_LIBRARY_.createDispatched(5) : this.numbers);
               int var10;
               this.state_2_ = var10 = state_2 | 262144;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.fitsInInt(this, this.numbers);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public boolean fitsInLong(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 524288) != 0) {
               return arg0Value.fitsInLong(this, this.numbers);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.fitsInLongNode_AndSpecialize(arg0Value);
            }
         }

         private boolean fitsInLongNode_AndSpecialize(HostObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_2 = this.state_2_;
               InteropLibrary fitsInLongNode__fitsInLong_thisLibrary__ = null;
               this.numbers = super.insert(this.numbers == null ? HostObjectGen.INTEROP_LIBRARY_.createDispatched(5) : this.numbers);
               int var10;
               this.state_2_ = var10 = state_2 | 524288;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.fitsInLong(this, this.numbers);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public boolean fitsInFloat(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 1048576) != 0) {
               return arg0Value.fitsInFloat(this, this.numbers);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.fitsInFloatNode_AndSpecialize(arg0Value);
            }
         }

         private boolean fitsInFloatNode_AndSpecialize(HostObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_2 = this.state_2_;
               InteropLibrary fitsInFloatNode__fitsInFloat_thisLibrary__ = null;
               this.numbers = super.insert(this.numbers == null ? HostObjectGen.INTEROP_LIBRARY_.createDispatched(5) : this.numbers);
               int var10;
               this.state_2_ = var10 = state_2 | 1048576;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.fitsInFloat(this, this.numbers);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public boolean fitsInDouble(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 2097152) != 0) {
               return arg0Value.fitsInDouble(this, this.numbers);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.fitsInDoubleNode_AndSpecialize(arg0Value);
            }
         }

         private boolean fitsInDoubleNode_AndSpecialize(HostObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_2 = this.state_2_;
               InteropLibrary fitsInDoubleNode__fitsInDouble_thisLibrary__ = null;
               this.numbers = super.insert(this.numbers == null ? HostObjectGen.INTEROP_LIBRARY_.createDispatched(5) : this.numbers);
               int var10;
               this.state_2_ = var10 = state_2 | 2097152;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.fitsInDouble(this, this.numbers);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public byte asByte(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 4194304) != 0) {
               return arg0Value.asByte(this, this.numbers, this.error);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.asByteNode_AndSpecialize(arg0Value);
            }
         }

         private byte asByteNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            byte var6;
            try {
               int state_2 = this.state_2_;
               InteropLibrary asByteNode__asByte_thisLibrary__ = null;
               this.numbers = super.insert(this.numbers == null ? HostObjectGen.INTEROP_LIBRARY_.createDispatched(5) : this.numbers);
               this.error = this.error == null ? BranchProfile.create() : this.error;
               int var10;
               this.state_2_ = var10 = state_2 | 4194304;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.asByte(this, this.numbers, this.error);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public short asShort(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 8388608) != 0) {
               return arg0Value.asShort(this, this.numbers, this.error);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.asShortNode_AndSpecialize(arg0Value);
            }
         }

         private short asShortNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            short var6;
            try {
               int state_2 = this.state_2_;
               InteropLibrary asShortNode__asShort_thisLibrary__ = null;
               this.numbers = super.insert(this.numbers == null ? HostObjectGen.INTEROP_LIBRARY_.createDispatched(5) : this.numbers);
               this.error = this.error == null ? BranchProfile.create() : this.error;
               int var10;
               this.state_2_ = var10 = state_2 | 8388608;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.asShort(this, this.numbers, this.error);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public int asInt(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 16777216) != 0) {
               return arg0Value.asInt(this, this.numbers, this.error);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.asIntNode_AndSpecialize(arg0Value);
            }
         }

         private int asIntNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            int var6;
            try {
               int state_2 = this.state_2_;
               InteropLibrary asIntNode__asInt_thisLibrary__ = null;
               this.numbers = super.insert(this.numbers == null ? HostObjectGen.INTEROP_LIBRARY_.createDispatched(5) : this.numbers);
               this.error = this.error == null ? BranchProfile.create() : this.error;
               int var10;
               this.state_2_ = var10 = state_2 | 16777216;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.asInt(this, this.numbers, this.error);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public long asLong(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 33554432) != 0) {
               return arg0Value.asLong(this, this.numbers, this.error);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.asLongNode_AndSpecialize(arg0Value);
            }
         }

         private long asLongNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            long var6;
            try {
               int state_2 = this.state_2_;
               InteropLibrary asLongNode__asLong_thisLibrary__ = null;
               this.numbers = super.insert(this.numbers == null ? HostObjectGen.INTEROP_LIBRARY_.createDispatched(5) : this.numbers);
               this.error = this.error == null ? BranchProfile.create() : this.error;
               int var11;
               this.state_2_ = var11 = state_2 | 33554432;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.asLong(this, this.numbers, this.error);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public float asFloat(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 67108864) != 0) {
               return arg0Value.asFloat(this, this.numbers, this.error);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.asFloatNode_AndSpecialize(arg0Value);
            }
         }

         private float asFloatNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            float var6;
            try {
               int state_2 = this.state_2_;
               InteropLibrary asFloatNode__asFloat_thisLibrary__ = null;
               this.numbers = super.insert(this.numbers == null ? HostObjectGen.INTEROP_LIBRARY_.createDispatched(5) : this.numbers);
               this.error = this.error == null ? BranchProfile.create() : this.error;
               int var10;
               this.state_2_ = var10 = state_2 | 67108864;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.asFloat(this, this.numbers, this.error);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public double asDouble(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 134217728) != 0) {
               return arg0Value.asDouble(this, this.numbers, this.error);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.asDoubleNode_AndSpecialize(arg0Value);
            }
         }

         private double asDoubleNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            double var6;
            try {
               int state_2 = this.state_2_;
               InteropLibrary asDoubleNode__asDouble_thisLibrary__ = null;
               this.numbers = super.insert(this.numbers == null ? HostObjectGen.INTEROP_LIBRARY_.createDispatched(5) : this.numbers);
               this.error = this.error == null ? BranchProfile.create() : this.error;
               int var11;
               this.state_2_ = var11 = state_2 | 134217728;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.asDouble(this, this.numbers, this.error);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public boolean isString(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 268435456) != 0) {
               return arg0Value.isString(this.classProfile);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isStringNode_AndSpecialize(arg0Value);
            }
         }

         private boolean isStringNode_AndSpecialize(HostObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var5;
            try {
               int state_2 = this.state_2_;
               this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
               int var9;
               this.state_2_ = var9 = state_2 | 268435456;
               lock.unlock();
               hasLock = false;
               var5 = arg0Value.isString(this.classProfile);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var5;
         }

         @Override
         public String asString(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 536870912) != 0) {
               return arg0Value.asString(this, this.numbers, this.error);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.asStringNode_AndSpecialize(arg0Value);
            }
         }

         private String asStringNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            String var6;
            try {
               int state_2 = this.state_2_;
               InteropLibrary asStringNode__asString_thisLibrary__ = null;
               this.numbers = super.insert(this.numbers == null ? HostObjectGen.INTEROP_LIBRARY_.createDispatched(5) : this.numbers);
               this.error = this.error == null ? BranchProfile.create() : this.error;
               int var10;
               this.state_2_ = var10 = state_2 | 536870912;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.asString(this, this.numbers, this.error);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public boolean isBoolean(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).isBoolean();
         }

         @Override
         public boolean asBoolean(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & 1073741824) != 0) {
               return arg0Value.asBoolean(this.error);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.asBooleanNode_AndSpecialize(arg0Value);
            }
         }

         private boolean asBooleanNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var5;
            try {
               int state_2 = this.state_2_;
               this.error = this.error == null ? BranchProfile.create() : this.error;
               int var9;
               this.state_2_ = var9 = state_2 | 1073741824;
               lock.unlock();
               hasLock = false;
               var5 = arg0Value.asBoolean(this.error);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var5;
         }

         @Override
         public boolean isDate(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).isDate();
         }

         @Override
         public LocalDate asDate(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).asDate();
         }

         @Override
         public boolean isTime(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).isTime();
         }

         @Override
         public LocalTime asTime(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).asTime();
         }

         @Override
         public boolean isTimeZone(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).isTimeZone();
         }

         @Override
         public ZoneId asTimeZone(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).asTimeZone();
         }

         @Override
         public Instant asInstant(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).asInstant();
         }

         @Override
         public boolean isDuration(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).isDuration();
         }

         @Override
         public Duration asDuration(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).asDuration();
         }

         @Override
         public boolean isException(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).isException();
         }

         @Override
         public ExceptionType getExceptionType(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_2 = this.state_2_;
            if ((state_2 & -2147483648) != 0) {
               return arg0Value.getExceptionType(this.error);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.getExceptionTypeNode_AndSpecialize(arg0Value);
            }
         }

         private ExceptionType getExceptionTypeNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            ExceptionType var5;
            try {
               int state_2 = this.state_2_;
               this.error = this.error == null ? BranchProfile.create() : this.error;
               int var9;
               this.state_2_ = var9 = state_2 | -2147483648;
               lock.unlock();
               hasLock = false;
               var5 = arg0Value.getExceptionType(this.error);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var5;
         }

         @Override
         public boolean isExceptionIncompleteSource(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_3 = this.state_3_;
            if ((state_3 & 1) != 0) {
               return arg0Value.isExceptionIncompleteSource(this.error);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isExceptionIncompleteSourceNode_AndSpecialize(arg0Value);
            }
         }

         private boolean isExceptionIncompleteSourceNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var5;
            try {
               int state_3 = this.state_3_;
               this.error = this.error == null ? BranchProfile.create() : this.error;
               int var9;
               this.state_3_ = var9 = state_3 | 1;
               lock.unlock();
               hasLock = false;
               var5 = arg0Value.isExceptionIncompleteSource(this.error);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var5;
         }

         @Override
         public int getExceptionExitStatus(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_3 = this.state_3_;
            if ((state_3 & 2) != 0) {
               return arg0Value.getExceptionExitStatus(this.error);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.getExceptionExitStatusNode_AndSpecialize(arg0Value);
            }
         }

         private int getExceptionExitStatusNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            int var5;
            try {
               int state_3 = this.state_3_;
               this.error = this.error == null ? BranchProfile.create() : this.error;
               int var9;
               this.state_3_ = var9 = state_3 | 2;
               lock.unlock();
               hasLock = false;
               var5 = arg0Value.getExceptionExitStatus(this.error);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var5;
         }

         @Override
         public boolean hasExceptionMessage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).hasExceptionMessage();
         }

         @Override
         public Object getExceptionMessage(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_3 = this.state_3_;
            if ((state_3 & 4) != 0) {
               return arg0Value.getExceptionMessage(this.error);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.getExceptionMessageNode_AndSpecialize(arg0Value);
            }
         }

         private Object getExceptionMessageNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var5;
            try {
               int state_3 = this.state_3_;
               this.error = this.error == null ? BranchProfile.create() : this.error;
               int var9;
               this.state_3_ = var9 = state_3 | 4;
               lock.unlock();
               hasLock = false;
               var5 = arg0Value.getExceptionMessage(this.error);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var5;
         }

         @Override
         public boolean hasExceptionCause(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).hasExceptionCause();
         }

         @Override
         public Object getExceptionCause(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).getExceptionCause();
         }

         @Override
         public boolean hasExceptionStackTrace(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).hasExceptionStackTrace();
         }

         @Override
         public Object getExceptionStackTrace(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).getExceptionStackTrace();
         }

         @Override
         public RuntimeException throwException(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_3 = this.state_3_;
            if ((state_3 & 8) != 0) {
               return arg0Value.throwException(this.error);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.throwExceptionNode_AndSpecialize(arg0Value);
            }
         }

         private RuntimeException throwExceptionNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            RuntimeException var5;
            try {
               int state_3 = this.state_3_;
               this.error = this.error == null ? BranchProfile.create() : this.error;
               int var9;
               this.state_3_ = var9 = state_3 | 8;
               lock.unlock();
               hasLock = false;
               var5 = arg0Value.throwException(this.error);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var5;
         }

         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).hasLanguage();
         }

         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).getLanguage();
         }

         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).toDisplayString(allowSideEffects);
         }

         @Override
         public boolean hasIterator(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_3 = this.state_3_;
            if ((state_3 & 16) != 0) {
               return arg0Value.hasIterator(this.isIterable, this.isArray);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.hasIteratorNode_AndSpecialize(arg0Value);
            }
         }

         private boolean hasIteratorNode_AndSpecialize(HostObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var5;
            try {
               int state_3 = this.state_3_;
               this.isIterable = super.insert(this.isIterable == null ? HostObjectFactory.IsIterableNodeGen.create() : this.isIterable);
               this.isArray = super.insert(this.isArray == null ? HostObjectFactory.IsArrayNodeGen.create() : this.isArray);
               int var9;
               this.state_3_ = var9 = state_3 | 16;
               lock.unlock();
               hasLock = false;
               var5 = arg0Value.hasIterator(this.isIterable, this.isArray);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var5;
         }

         @Override
         public boolean isIterator(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_3 = this.state_3_;
            if ((state_3 & 32) != 0) {
               return arg0Value.isIterator(this.isIterator);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isIteratorNode_AndSpecialize(arg0Value);
            }
         }

         private boolean isIteratorNode_AndSpecialize(HostObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var5;
            try {
               int state_3 = this.state_3_;
               this.isIterator = super.insert(this.isIterator == null ? HostObjectFactory.IsIteratorNodeGen.create() : this.isIterator);
               int var9;
               this.state_3_ = var9 = state_3 | 32;
               lock.unlock();
               hasLock = false;
               var5 = arg0Value.isIterator(this.isIterator);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var5;
         }

         @Override
         public boolean hasHashEntries(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_3 = this.state_3_;
            if ((state_3 & 64) != 0) {
               return arg0Value.hasHashEntries(this.isMap);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.hasHashEntriesNode_AndSpecialize(arg0Value);
            }
         }

         private boolean hasHashEntriesNode_AndSpecialize(HostObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var5;
            try {
               int state_3 = this.state_3_;
               this.isMap = super.insert(this.isMap == null ? HostObjectFactory.IsMapNodeGen.create() : this.isMap);
               int var9;
               this.state_3_ = var9 = state_3 | 64;
               lock.unlock();
               hasLock = false;
               var5 = arg0Value.hasHashEntries(this.isMap);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var5;
         }

         @Override
         public boolean isHashEntryReadable(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_3 = this.state_3_;
            if ((state_3 & 128) != 0) {
               return arg0Value.isHashEntryReadable(arg1Value, this.isMap, this.containsKey);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isHashEntryReadableNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isHashEntryReadableNode_AndSpecialize(HostObject arg0Value, Object arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_3 = this.state_3_;
               this.isMap = super.insert(this.isMap == null ? HostObjectFactory.IsMapNodeGen.create() : this.isMap);
               this.containsKey = super.insert(this.containsKey == null ? HostObjectFactory.ContainsKeyNodeGen.create() : this.containsKey);
               int var10;
               this.state_3_ = var10 = state_3 | 128;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.isHashEntryReadable(arg1Value, this.isMap, this.containsKey);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public boolean isHashEntryModifiable(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_3 = this.state_3_;
            if ((state_3 & 128) != 0) {
               return arg0Value.isHashEntryReadable(arg1Value, this.isMap, this.containsKey);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isHashEntryReadableNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         @Override
         public boolean isHashEntryRemovable(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_3 = this.state_3_;
            if ((state_3 & 128) != 0) {
               return arg0Value.isHashEntryReadable(arg1Value, this.isMap, this.containsKey);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isHashEntryReadableNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         @Override
         public boolean isHashEntryInsertable(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_3 = this.state_3_;
            if ((state_3 & 256) != 0) {
               return arg0Value.isHashEntryInsertable(arg1Value, this.isMap, this.containsKey);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isHashEntryInsertableNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isHashEntryInsertableNode_AndSpecialize(HostObject arg0Value, Object arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_3 = this.state_3_;
               this.isMap = super.insert(this.isMap == null ? HostObjectFactory.IsMapNodeGen.create() : this.isMap);
               this.containsKey = super.insert(this.containsKey == null ? HostObjectFactory.ContainsKeyNodeGen.create() : this.containsKey);
               int var10;
               this.state_3_ = var10 = state_3 | 256;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.isHashEntryInsertable(arg1Value, this.isMap, this.containsKey);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public boolean hasMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).hasMetaObject();
         }

         @Override
         public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).getMetaObject();
         }

         @Override
         public boolean isMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).isMetaObject();
         }

         @Override
         public Object getMetaQualifiedName(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).getMetaQualifiedName();
         }

         @Override
         public Object getMetaSimpleName(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).getMetaSimpleName();
         }

         @Override
         public boolean isMetaInstance(Object arg0Value_, Object arg1Value) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject arg0Value = (HostObject)arg0Value_;
            int state_3 = this.state_3_;
            if ((state_3 & 512) != 0) {
               return arg0Value.isMetaInstance(arg1Value, this, this.error);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isMetaInstanceNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isMetaInstanceNode_AndSpecialize(HostObject arg0Value, Object arg1Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var7;
            try {
               int state_3 = this.state_3_;
               InteropLibrary isMetaInstanceNode__isMetaInstance_library__ = null;
               this.error = this.error == null ? BranchProfile.create() : this.error;
               int var11;
               this.state_3_ = var11 = state_3 | 512;
               lock.unlock();
               hasLock = false;
               var7 = arg0Value.isMetaInstance(arg1Value, this, this.error);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public boolean hasMetaParents(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).hasMetaParents();
         }

         @Override
         public Object getMetaParents(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject)receiver).getMetaParents();
         }

         @Override
         public int identityHashCode(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return HostObject.identityHashCode((HostObject)receiver);
         }

         private static boolean isIdenticalOrUndefinedFallbackGuard_(int state_1, HostObject arg0Value, Object arg1Value) {
            return (state_1 & 8388608) != 0 || !(arg1Value instanceof HostObject);
         }

         @GeneratedBy(HostObject.class)
         private static final class IsMemberInternalCachedData {
            @CompilerDirectives.CompilationFinal
            HostObjectGen.InteropLibraryExports.Cached.IsMemberInternalCachedData next_;
            @CompilerDirectives.CompilationFinal
            boolean cachedStatic_;
            @CompilerDirectives.CompilationFinal
            Class<?> cachedClazz_;
            @CompilerDirectives.CompilationFinal
            String cachedName_;
            @CompilerDirectives.CompilationFinal
            boolean cachedInternal_;

            IsMemberInternalCachedData(HostObjectGen.InteropLibraryExports.Cached.IsMemberInternalCachedData next_) {
               this.next_ = next_;
            }
         }

         @GeneratedBy(HostObject.class)
         private static final class IsMemberInvocableCachedData {
            @CompilerDirectives.CompilationFinal
            HostObjectGen.InteropLibraryExports.Cached.IsMemberInvocableCachedData next_;
            @CompilerDirectives.CompilationFinal
            boolean cachedStatic_;
            @CompilerDirectives.CompilationFinal
            Class<?> cachedClazz_;
            @CompilerDirectives.CompilationFinal
            String cachedName_;
            @CompilerDirectives.CompilationFinal
            boolean cachedInvokable_;

            IsMemberInvocableCachedData(HostObjectGen.InteropLibraryExports.Cached.IsMemberInvocableCachedData next_) {
               this.next_ = next_;
            }
         }

         @GeneratedBy(HostObject.class)
         private static final class IsMemberModifiableCachedData {
            @CompilerDirectives.CompilationFinal
            HostObjectGen.InteropLibraryExports.Cached.IsMemberModifiableCachedData next_;
            @CompilerDirectives.CompilationFinal
            boolean cachedStatic_;
            @CompilerDirectives.CompilationFinal
            Class<?> cachedClazz_;
            @CompilerDirectives.CompilationFinal
            String cachedName_;
            @CompilerDirectives.CompilationFinal
            boolean cachedModifiable_;

            IsMemberModifiableCachedData(HostObjectGen.InteropLibraryExports.Cached.IsMemberModifiableCachedData next_) {
               this.next_ = next_;
            }
         }

         @GeneratedBy(HostObject.class)
         private static final class IsMemberReadableCachedData {
            @CompilerDirectives.CompilationFinal
            HostObjectGen.InteropLibraryExports.Cached.IsMemberReadableCachedData next_;
            @CompilerDirectives.CompilationFinal
            boolean cachedStatic_;
            @CompilerDirectives.CompilationFinal
            Class<?> cachedClazz_;
            @CompilerDirectives.CompilationFinal
            String cachedName_;
            @CompilerDirectives.CompilationFinal
            boolean cachedReadable_;

            IsMemberReadableCachedData(HostObjectGen.InteropLibraryExports.Cached.IsMemberReadableCachedData next_) {
               this.next_ = next_;
            }
         }
      }

      @GeneratedBy(HostObject.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof HostObject) || HostObjectGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof HostObject;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberReadable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return HostObject.IsMemberReadable.doUncached(arg0Value, arg1Value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberModifiable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return HostObject.IsMemberModifiable.doUncached(arg0Value, arg1Value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberInternal(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return HostObject.IsMemberInternal.doUncached(arg0Value, arg1Value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberInvocable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return HostObject.IsMemberInvocable.doUncached(arg0Value, arg1Value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isArrayElementReadable(Object arg0Value_, long arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            if (HostObjectFactory.IsArrayNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.IsArrayElementReadable.doArray(arg0Value, arg1Value, HostObjectFactory.IsArrayNodeGen.getUncached());
            } else if (HostObjectFactory.IsListNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.IsArrayElementReadable.doList(arg0Value, arg1Value, HostObjectFactory.IsListNodeGen.getUncached(), BranchProfile.getUncached());
            } else if (HostObjectFactory.IsMapEntryNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.IsArrayElementReadable.doMapEntry(arg0Value, arg1Value, HostObjectFactory.IsMapEntryNodeGen.getUncached());
            } else if (!HostObjectFactory.IsListNodeGen.getUncached().execute(arg0Value)
               && !HostObjectFactory.IsArrayNodeGen.getUncached().execute(arg0Value)
               && !HostObjectFactory.IsMapEntryNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.IsArrayElementReadable.doNotArrayOrList(
                  arg0Value,
                  arg1Value,
                  HostObjectFactory.IsListNodeGen.getUncached(),
                  HostObjectFactory.IsArrayNodeGen.getUncached(),
                  HostObjectFactory.IsMapEntryNodeGen.getUncached()
               );
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isArrayElementModifiable(Object arg0Value_, long arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            if (HostObjectFactory.IsArrayNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.IsArrayElementModifiable.doArray(arg0Value, arg1Value, HostObjectFactory.IsArrayNodeGen.getUncached());
            } else if (HostObjectFactory.IsListNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.IsArrayElementModifiable.doList(
                  arg0Value, arg1Value, HostObjectFactory.IsListNodeGen.getUncached(), BranchProfile.getUncached()
               );
            } else if (HostObjectFactory.IsMapEntryNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.IsArrayElementModifiable.doMapEntry(arg0Value, arg1Value, HostObjectFactory.IsMapEntryNodeGen.getUncached());
            } else if (!HostObjectFactory.IsListNodeGen.getUncached().execute(arg0Value)
               && !HostObjectFactory.IsArrayNodeGen.getUncached().execute(arg0Value)
               && !HostObjectFactory.IsMapEntryNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.IsArrayElementModifiable.doNotArrayOrList(
                  arg0Value,
                  arg1Value,
                  HostObjectFactory.IsListNodeGen.getUncached(),
                  HostObjectFactory.IsArrayNodeGen.getUncached(),
                  HostObjectFactory.IsMapEntryNodeGen.getUncached()
               );
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeArrayElement(Object arg0Value_, long arg1Value, Object arg2Value) throws InvalidArrayIndexException, UnsupportedTypeException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            if (HostObjectFactory.IsArrayNodeGen.getUncached().execute(arg0Value)) {
               HostObject.WriteArrayElement.doArray(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  HostToTypeNodeGen.getUncached(),
                  HostObjectFactory.IsArrayNodeGen.getUncached(),
                  HostObjectFactory.ArraySetNodeGen.getUncached(),
                  BranchProfile.getUncached()
               );
            } else if (HostObjectFactory.IsListNodeGen.getUncached().execute(arg0Value)) {
               HostObject.WriteArrayElement.doList(
                  arg0Value, arg1Value, arg2Value, HostObjectFactory.IsListNodeGen.getUncached(), HostToTypeNodeGen.getUncached(), BranchProfile.getUncached()
               );
            } else if (HostObjectFactory.IsMapEntryNodeGen.getUncached().execute(arg0Value)) {
               HostObject.WriteArrayElement.doMapEntry(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  HostObjectFactory.IsMapEntryNodeGen.getUncached(),
                  HostToTypeNodeGen.getUncached(),
                  BranchProfile.getUncached()
               );
            } else if (!HostObjectFactory.IsListNodeGen.getUncached().execute(arg0Value)
               && !HostObjectFactory.IsArrayNodeGen.getUncached().execute(arg0Value)
               && !HostObjectFactory.IsMapEntryNodeGen.getUncached().execute(arg0Value)) {
               HostObject.WriteArrayElement.doNotArrayOrList(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  HostObjectFactory.IsListNodeGen.getUncached(),
                  HostObjectFactory.IsArrayNodeGen.getUncached(),
                  HostObjectFactory.IsMapEntryNodeGen.getUncached()
               );
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
            }
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isArrayElementRemovable(Object arg0Value_, long arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            if (HostObjectFactory.IsListNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.IsArrayElementRemovable.doList(
                  arg0Value, arg1Value, HostObjectFactory.IsListNodeGen.getUncached(), BranchProfile.getUncached()
               );
            } else if (!HostObjectFactory.IsListNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.IsArrayElementRemovable.doOther(arg0Value, arg1Value, HostObjectFactory.IsListNodeGen.getUncached());
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void removeArrayElement(Object arg0Value_, long arg1Value) throws InvalidArrayIndexException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            if (HostObjectFactory.IsListNodeGen.getUncached().execute(arg0Value)) {
               HostObject.RemoveArrayElement.doList(arg0Value, arg1Value, HostObjectFactory.IsListNodeGen.getUncached(), BranchProfile.getUncached());
            } else if (!HostObjectFactory.IsListNodeGen.getUncached().execute(arg0Value)) {
               HostObject.RemoveArrayElement.doOther(arg0Value, arg1Value, HostObjectFactory.IsListNodeGen.getUncached());
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readArrayElement(Object arg0Value_, long arg1Value) throws InvalidArrayIndexException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            if (HostObjectFactory.IsArrayNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.ReadArrayElement.doArray(
                  arg0Value,
                  arg1Value,
                  HostObjectFactory.ArrayGetNodeGen.getUncached(),
                  HostObjectFactory.IsArrayNodeGen.getUncached(),
                  HostContextFactory.ToGuestValueNodeGen.getUncached(),
                  BranchProfile.getUncached()
               );
            } else if (HostObjectFactory.IsListNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.ReadArrayElement.doList(
                  arg0Value,
                  arg1Value,
                  HostObjectFactory.IsListNodeGen.getUncached(),
                  HostContextFactory.ToGuestValueNodeGen.getUncached(),
                  BranchProfile.getUncached()
               );
            } else if (HostObjectFactory.IsMapEntryNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.ReadArrayElement.doMapEntry(
                  arg0Value,
                  arg1Value,
                  HostObjectFactory.IsMapEntryNodeGen.getUncached(),
                  HostContextFactory.ToGuestValueNodeGen.getUncached(),
                  BranchProfile.getUncached()
               );
            } else if (!HostObjectFactory.IsArrayNodeGen.getUncached().execute(arg0Value)
               && !HostObjectFactory.IsListNodeGen.getUncached().execute(arg0Value)
               && !HostObjectFactory.IsMapEntryNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.ReadArrayElement.doNotArrayOrList(
                  arg0Value,
                  arg1Value,
                  HostObjectFactory.IsArrayNodeGen.getUncached(),
                  HostObjectFactory.IsListNodeGen.getUncached(),
                  HostObjectFactory.IsMapEntryNodeGen.getUncached()
               );
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long getArraySize(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            if (HostObjectFactory.IsArrayNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.GetArraySize.doArray(arg0Value, HostObjectFactory.IsArrayNodeGen.getUncached());
            } else if (HostObjectFactory.IsListNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.GetArraySize.doList(arg0Value, HostObjectFactory.IsListNodeGen.getUncached(), BranchProfile.getUncached());
            } else if (HostObjectFactory.IsMapEntryNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.GetArraySize.doMapEntry(arg0Value, HostObjectFactory.IsMapEntryNodeGen.getUncached());
            } else if (!HostObjectFactory.IsArrayNodeGen.getUncached().execute(arg0Value)
               && !HostObjectFactory.IsListNodeGen.getUncached().execute(arg0Value)
               && !HostObjectFactory.IsMapEntryNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.GetArraySize.doNotArrayOrList(
                  arg0Value,
                  HostObjectFactory.IsArrayNodeGen.getUncached(),
                  HostObjectFactory.IsListNodeGen.getUncached(),
                  HostObjectFactory.IsMapEntryNodeGen.getUncached()
               );
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isInstantiable(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            if (!arg0Value.isClass()) {
               return HostObject.IsInstantiable.doUnsupported(arg0Value);
            } else if (arg0Value.isArrayClass()) {
               return HostObject.IsInstantiable.doArrayCached(arg0Value);
            } else if (arg0Value.isDefaultClass()) {
               return HostObject.IsInstantiable.doObjectCached(arg0Value, HostObjectFactory.LookupConstructorNodeGen.getUncached());
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object instantiate(Object arg0Value_, Object... arg1Value) throws UnsupportedMessageException, UnsupportedTypeException, ArityException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            if (!arg0Value.isClass()) {
               return HostObject.Instantiate.doUnsupported(arg0Value, arg1Value);
            } else if (arg0Value.isArrayClass()) {
               return HostObject.Instantiate.doArrayCached(arg0Value, arg1Value, HostObjectGen.INTEROP_LIBRARY_.getUncached(), BranchProfile.getUncached());
            } else if (arg0Value.isDefaultClass()) {
               return HostObject.Instantiate.doObjectCached(
                  arg0Value, arg1Value, HostObjectFactory.LookupConstructorNodeGen.getUncached(), HostExecuteNodeGen.getUncached(), BranchProfile.getUncached()
               );
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getIterator(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            if (HostObjectFactory.IsArrayNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.GetIterator.doArray(
                  arg0Value, HostObjectFactory.IsArrayNodeGen.getUncached(), HostContextFactory.ToGuestValueNodeGen.getUncached()
               );
            } else if (HostObjectFactory.IsIterableNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.GetIterator.doIterable(
                  arg0Value,
                  HostObjectFactory.IsIterableNodeGen.getUncached(),
                  HostContextFactory.ToGuestValueNodeGen.getUncached(),
                  BranchProfile.getUncached()
               );
            } else if (!HostObjectFactory.IsArrayNodeGen.getUncached().execute(arg0Value)
               && !HostObjectFactory.IsIterableNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.GetIterator.doNotArrayOrIterable(
                  arg0Value, HostObjectFactory.IsArrayNodeGen.getUncached(), HostObjectFactory.IsIterableNodeGen.getUncached()
               );
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasIteratorNextElement(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            if (HostObjectFactory.IsIteratorNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.HasIteratorNextElement.doIterator(arg0Value, HostObjectFactory.IsIteratorNodeGen.getUncached(), BranchProfile.getUncached());
            } else if (!HostObjectFactory.IsIteratorNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.HasIteratorNextElement.doNotIterator(arg0Value, HostObjectFactory.IsIteratorNodeGen.getUncached());
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getIteratorNextElement(Object arg0Value_) throws StopIterationException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            if (HostObjectFactory.IsIteratorNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.GetIteratorNextElement.doIterator(
                  arg0Value,
                  HostObjectFactory.IsIteratorNodeGen.getUncached(),
                  HostContextFactory.ToGuestValueNodeGen.getUncached(),
                  BranchProfile.getUncached(),
                  BranchProfile.getUncached()
               );
            } else if (!HostObjectFactory.IsIteratorNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.GetIteratorNextElement.doNotIterator(arg0Value, HostObjectFactory.IsIteratorNodeGen.getUncached());
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long getHashSize(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            if (HostObjectFactory.IsMapNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.GetHashSize.doMap(arg0Value, HostObjectFactory.IsMapNodeGen.getUncached(), BranchProfile.getUncached());
            } else if (!HostObjectFactory.IsMapNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.GetHashSize.doNotMap(arg0Value, HostObjectFactory.IsMapNodeGen.getUncached());
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readHashValue(Object arg0Value_, Object arg1Value) throws UnknownKeyException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            if (HostObjectFactory.IsMapNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.ReadHashValue.doMap(
                  arg0Value,
                  arg1Value,
                  HostObjectFactory.IsMapNodeGen.getUncached(),
                  HostToTypeNodeGen.getUncached(),
                  HostContextFactory.ToGuestValueNodeGen.getUncached(),
                  BranchProfile.getUncached()
               );
            } else if (!HostObjectFactory.IsMapNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.ReadHashValue.doNotMap(arg0Value, arg1Value, HostObjectFactory.IsMapNodeGen.getUncached());
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeHashEntry(Object arg0Value_, Object arg1Value, Object arg2Value) throws UnsupportedTypeException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            if (HostObjectFactory.IsMapNodeGen.getUncached().execute(arg0Value)) {
               HostObject.WriteHashEntry.doMap(
                  arg0Value, arg1Value, arg2Value, HostObjectFactory.IsMapNodeGen.getUncached(), HostToTypeNodeGen.getUncached(), BranchProfile.getUncached()
               );
            } else if (!HostObjectFactory.IsMapNodeGen.getUncached().execute(arg0Value)) {
               HostObject.WriteHashEntry.doNotMap(arg0Value, arg1Value, arg2Value, HostObjectFactory.IsMapNodeGen.getUncached());
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
            }
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void removeHashEntry(Object arg0Value_, Object arg1Value) throws UnknownKeyException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            if (HostObjectFactory.IsMapNodeGen.getUncached().execute(arg0Value)) {
               HostObject.RemoveHashEntry.doMap(
                  arg0Value, arg1Value, HostObjectFactory.IsMapNodeGen.getUncached(), HostToTypeNodeGen.getUncached(), BranchProfile.getUncached()
               );
            } else if (!HostObjectFactory.IsMapNodeGen.getUncached().execute(arg0Value)) {
               HostObject.RemoveHashEntry.doNotMap(arg0Value, arg1Value, HostObjectFactory.IsMapNodeGen.getUncached());
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getHashEntriesIterator(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            if (HostObjectFactory.IsMapNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.GetHashEntriesIterator.doMap(
                  arg0Value, HostObjectFactory.IsMapNodeGen.getUncached(), HostContextFactory.ToGuestValueNodeGen.getUncached(), BranchProfile.getUncached()
               );
            } else if (!HostObjectFactory.IsMapNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.GetHashEntriesIterator.doNotMap(arg0Value, HostObjectFactory.IsMapNodeGen.getUncached());
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public TriState isIdenticalOrUndefined(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            if (arg1Value instanceof HostObject) {
               HostObject arg1Value_ = (HostObject)arg1Value;
               return HostObject.IsIdenticalOrUndefined.doHostObject(arg0Value, arg1Value_);
            } else {
               return HostObject.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
            }
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMembers(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).hasMembers();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).getMembers(includeInternal);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.readMember(
               arg1Value,
               HostObjectFactory.LookupFieldNodeGen.getUncached(),
               HostObjectFactory.ReadFieldNodeGen.getUncached(),
               HostObjectFactory.LookupMethodNodeGen.getUncached(),
               HostObjectFactory.LookupInnerClassNodeGen.getUncached(),
               BranchProfile.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberInsertable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).isMemberInsertable(member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeMember(Object arg0Value_, String arg1Value, Object arg2Value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            arg0Value.writeMember(
               arg1Value,
               arg2Value,
               HostObjectFactory.LookupFieldNodeGen.getUncached(),
               HostObjectFactory.WriteFieldNodeGen.getUncached(),
               BranchProfile.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object invokeMember(Object arg0Value_, String arg1Value, Object... arg2Value) throws UnsupportedTypeException, ArityException, UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.invokeMember(
               arg1Value,
               arg2Value,
               HostObjectFactory.LookupMethodNodeGen.getUncached(),
               HostExecuteNodeGen.getUncached(),
               HostObjectFactory.LookupFieldNodeGen.getUncached(),
               HostObjectFactory.ReadFieldNodeGen.getUncached(),
               HostObjectGen.INTEROP_LIBRARY_.getUncached(),
               BranchProfile.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isArrayElementInsertable(Object arg0Value_, long arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.isArrayElementInsertable(arg1Value, HostObjectFactory.IsListNodeGen.getUncached(), BranchProfile.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasArrayElements(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.hasArrayElements(
               HostObjectFactory.IsListNodeGen.getUncached(), HostObjectFactory.IsArrayNodeGen.getUncached(), HostObjectFactory.IsMapEntryNodeGen.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasBufferElements(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.hasBufferElements(HostObjectFactory.IsBufferNodeGen.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isBufferWritable(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.isBufferWritable(HostObjectFactory.IsBufferNodeGen.getUncached(), BranchProfile.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long getBufferSize(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.getBufferSize(HostObjectFactory.IsBufferNodeGen.getUncached(), BranchProfile.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public byte readBufferByte(Object arg0Value_, long arg1Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.readBufferByte(arg1Value, HostObjectFactory.IsBufferNodeGen.getUncached(), BranchProfile.getUncached(), ValueProfile.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeBufferByte(Object arg0Value_, long arg1Value, byte arg2Value) throws InvalidBufferOffsetException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            arg0Value.writeBufferByte(
               arg1Value, arg2Value, HostObjectFactory.IsBufferNodeGen.getUncached(), BranchProfile.getUncached(), ValueProfile.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public short readBufferShort(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.readBufferShort(
               arg1Value, arg2Value, HostObjectFactory.IsBufferNodeGen.getUncached(), BranchProfile.getUncached(), ValueProfile.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeBufferShort(Object arg0Value_, ByteOrder arg1Value, long arg2Value, short arg3Value) throws InvalidBufferOffsetException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            arg0Value.writeBufferShort(
               arg1Value, arg2Value, arg3Value, HostObjectFactory.IsBufferNodeGen.getUncached(), BranchProfile.getUncached(), ValueProfile.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public int readBufferInt(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.readBufferInt(
               arg1Value, arg2Value, HostObjectFactory.IsBufferNodeGen.getUncached(), BranchProfile.getUncached(), ValueProfile.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeBufferInt(Object arg0Value_, ByteOrder arg1Value, long arg2Value, int arg3Value) throws InvalidBufferOffsetException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            arg0Value.writeBufferInt(
               arg1Value, arg2Value, arg3Value, HostObjectFactory.IsBufferNodeGen.getUncached(), BranchProfile.getUncached(), ValueProfile.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long readBufferLong(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.readBufferLong(
               arg1Value, arg2Value, HostObjectFactory.IsBufferNodeGen.getUncached(), BranchProfile.getUncached(), ValueProfile.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeBufferLong(Object arg0Value_, ByteOrder arg1Value, long arg2Value, long arg3Value) throws InvalidBufferOffsetException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            arg0Value.writeBufferLong(
               arg1Value, arg2Value, arg3Value, HostObjectFactory.IsBufferNodeGen.getUncached(), BranchProfile.getUncached(), ValueProfile.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public float readBufferFloat(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.readBufferFloat(
               arg1Value, arg2Value, HostObjectFactory.IsBufferNodeGen.getUncached(), BranchProfile.getUncached(), ValueProfile.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeBufferFloat(Object arg0Value_, ByteOrder arg1Value, long arg2Value, float arg3Value) throws InvalidBufferOffsetException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            arg0Value.writeBufferFloat(
               arg1Value, arg2Value, arg3Value, HostObjectFactory.IsBufferNodeGen.getUncached(), BranchProfile.getUncached(), ValueProfile.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public double readBufferDouble(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.readBufferDouble(
               arg1Value, arg2Value, HostObjectFactory.IsBufferNodeGen.getUncached(), BranchProfile.getUncached(), ValueProfile.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeBufferDouble(Object arg0Value_, ByteOrder arg1Value, long arg2Value, double arg3Value) throws InvalidBufferOffsetException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            arg0Value.writeBufferDouble(
               arg1Value, arg2Value, arg3Value, HostObjectFactory.IsBufferNodeGen.getUncached(), BranchProfile.getUncached(), ValueProfile.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isNull(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).isNull();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isExecutable(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.isExecutable(HostObjectFactory.LookupFunctionalMethodNodeGen.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object execute(Object arg0Value_, Object... arg1Value) throws UnsupportedMessageException, UnsupportedTypeException, ArityException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.execute(
               arg1Value, HostExecuteNodeGen.getUncached(), HostObjectFactory.LookupFunctionalMethodNodeGen.getUncached(), BranchProfile.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isNumber(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.isNumber(ValueProfile.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInByte(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.fitsInByte(this, HostObjectGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInShort(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.fitsInShort(this, HostObjectGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInInt(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.fitsInInt(this, HostObjectGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInLong(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.fitsInLong(this, HostObjectGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInFloat(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.fitsInFloat(this, HostObjectGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInDouble(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.fitsInDouble(this, HostObjectGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public byte asByte(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.asByte(this, HostObjectGen.INTEROP_LIBRARY_.getUncached(), BranchProfile.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public short asShort(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.asShort(this, HostObjectGen.INTEROP_LIBRARY_.getUncached(), BranchProfile.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public int asInt(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.asInt(this, HostObjectGen.INTEROP_LIBRARY_.getUncached(), BranchProfile.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long asLong(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.asLong(this, HostObjectGen.INTEROP_LIBRARY_.getUncached(), BranchProfile.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public float asFloat(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.asFloat(this, HostObjectGen.INTEROP_LIBRARY_.getUncached(), BranchProfile.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public double asDouble(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.asDouble(this, HostObjectGen.INTEROP_LIBRARY_.getUncached(), BranchProfile.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isString(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.isString(ValueProfile.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public String asString(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.asString(this, HostObjectGen.INTEROP_LIBRARY_.getUncached(), BranchProfile.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isBoolean(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).isBoolean();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean asBoolean(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.asBoolean(BranchProfile.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isDate(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).isDate();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public LocalDate asDate(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).asDate();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isTime(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).isTime();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public LocalTime asTime(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).asTime();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isTimeZone(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).isTimeZone();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public ZoneId asTimeZone(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).asTimeZone();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Instant asInstant(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).asInstant();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isDuration(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).isDuration();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Duration asDuration(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).asDuration();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isException(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).isException();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public ExceptionType getExceptionType(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.getExceptionType(BranchProfile.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isExceptionIncompleteSource(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.isExceptionIncompleteSource(BranchProfile.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public int getExceptionExitStatus(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.getExceptionExitStatus(BranchProfile.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasExceptionMessage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).hasExceptionMessage();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getExceptionMessage(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.getExceptionMessage(BranchProfile.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasExceptionCause(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).hasExceptionCause();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getExceptionCause(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).getExceptionCause();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasExceptionStackTrace(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).hasExceptionStackTrace();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getExceptionStackTrace(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).getExceptionStackTrace();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public RuntimeException throwException(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.throwException(BranchProfile.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).hasLanguage();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).getLanguage();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).toDisplayString(allowSideEffects);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasIterator(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.hasIterator(HostObjectFactory.IsIterableNodeGen.getUncached(), HostObjectFactory.IsArrayNodeGen.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isIterator(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.isIterator(HostObjectFactory.IsIteratorNodeGen.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasHashEntries(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.hasHashEntries(HostObjectFactory.IsMapNodeGen.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isHashEntryReadable(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.isHashEntryReadable(arg1Value, HostObjectFactory.IsMapNodeGen.getUncached(), HostObjectFactory.ContainsKeyNodeGen.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isHashEntryModifiable(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.isHashEntryReadable(arg1Value, HostObjectFactory.IsMapNodeGen.getUncached(), HostObjectFactory.ContainsKeyNodeGen.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isHashEntryRemovable(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.isHashEntryReadable(arg1Value, HostObjectFactory.IsMapNodeGen.getUncached(), HostObjectFactory.ContainsKeyNodeGen.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isHashEntryInsertable(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.isHashEntryInsertable(arg1Value, HostObjectFactory.IsMapNodeGen.getUncached(), HostObjectFactory.ContainsKeyNodeGen.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).hasMetaObject();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).getMetaObject();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).isMetaObject();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMetaQualifiedName(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).getMetaQualifiedName();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMetaSimpleName(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).getMetaSimpleName();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMetaInstance(Object arg0Value_, Object arg1Value) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject arg0Value = (HostObject)arg0Value_;
            return arg0Value.isMetaInstance(arg1Value, this, BranchProfile.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMetaParents(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).hasMetaParents();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMetaParents(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject)receiver).getMetaParents();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public int identityHashCode(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return HostObject.identityHashCode((HostObject)receiver);
         }
      }
   }
}
