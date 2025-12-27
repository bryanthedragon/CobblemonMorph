package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnknownKeyException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.utilities.TriState;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.concurrent.locks.Lock;

@GeneratedBy(HostProxy.class)
final class HostProxyGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

   private HostProxyGen() {
   }

   static {
      LibraryExport.register(HostProxy.class, new HostProxyGen.InteropLibraryExports());
   }

   @GeneratedBy(HostProxy.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, HostProxy.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof HostProxy;

         InteropLibrary uncached = new HostProxyGen.InteropLibraryExports.Uncached();
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof HostProxy;

         return new HostProxyGen.InteropLibraryExports.Cached();
      }

      @GeneratedBy(HostProxy.class)
      private static final class Cached extends InteropLibrary {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int state_1_;
         @CompilerDirectives.CompilationFinal
         private GuestToHostCodeCache cache;
         @Node.Child
         private InteropLibrary executables;

         protected Cached() {
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof HostProxy) || HostProxyGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof HostProxy;
         }

         @Override
         protected TriState isIdenticalOrUndefined(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 3) != 0) {
               if ((state_0 & 1) != 0 && arg1Value instanceof HostProxy) {
                  HostProxy arg1Value_ = (HostProxy)arg1Value;
                  return HostProxy.IsIdenticalOrUndefined.doHostObject(arg0Value, arg1Value_);
               }

               if ((state_0 & 2) != 0 && isIdenticalOrUndefinedFallbackGuard_(state_0, arg0Value, arg1Value)) {
                  return HostProxy.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.isIdenticalOrUndefinedAndSpecialize(arg0Value, arg1Value);
         }

         private TriState isIdenticalOrUndefinedAndSpecialize(HostProxy arg0Value, Object arg1Value) {
            int state_0 = this.state_0_;
            if (arg1Value instanceof HostProxy) {
               HostProxy arg1Value_ = (HostProxy)arg1Value;
               int var6;
               this.state_0_ = var6 = state_0 | 1;
               return HostProxy.IsIdenticalOrUndefined.doHostObject(arg0Value, arg1Value_);
            } else {
               int var5;
               this.state_0_ = var5 = state_0 | 2;
               return HostProxy.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
            }
         }

         @Override
         public NodeCost getCost() {
            int state_0 = this.state_0_;
            if ((state_0 & 3) == 0) {
               return NodeCost.UNINITIALIZED;
            } else {
               return (state_0 & 3 & (state_0 & 3) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
            }
         }

         @Override
         public boolean isInstantiable(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostProxy)receiver).isInstantiable();
         }

         @Override
         public Object instantiate(Object arg0Value_, Object... arg1Value) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 4) != 0) {
               return arg0Value.instantiate(arg1Value, this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.instantiateNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private Object instantiateNode_AndSpecialize(HostProxy arg0Value, Object[] arg1Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var7;
            try {
               int state_0 = this.state_0_;
               InteropLibrary instantiateNode__instantiate_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var11;
               this.state_0_ = var11 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               var7 = arg0Value.instantiate(arg1Value, this, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public boolean isExecutable(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostProxy)receiver).isExecutable();
         }

         @Override
         public Object execute(Object arg0Value_, Object... arg1Value) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 8) != 0) {
               return arg0Value.execute(arg1Value, this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.executeNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private Object executeNode_AndSpecialize(HostProxy arg0Value, Object[] arg1Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var7;
            try {
               int state_0 = this.state_0_;
               InteropLibrary executeNode__execute_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var11;
               this.state_0_ = var11 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               var7 = arg0Value.execute(arg1Value, this, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public boolean isPointer(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostProxy)receiver).isPointer();
         }

         @Override
         public long asPointer(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 16) != 0) {
               return arg0Value.asPointer(this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.asPointerNode_AndSpecialize(arg0Value);
            }
         }

         private long asPointerNode_AndSpecialize(HostProxy arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            long var6;
            try {
               int state_0 = this.state_0_;
               InteropLibrary asPointerNode__asPointer_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var11;
               this.state_0_ = var11 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.asPointer(this, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public boolean hasArrayElements(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostProxy)receiver).hasArrayElements();
         }

         @Override
         public Object readArrayElement(Object arg0Value_, long arg1Value) throws UnsupportedMessageException, InvalidArrayIndexException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 32) != 0) {
               return arg0Value.readArrayElement(arg1Value, this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.readArrayElementNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private Object readArrayElementNode_AndSpecialize(HostProxy arg0Value, long arg1Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var8;
            try {
               int state_0 = this.state_0_;
               InteropLibrary readArrayElementNode__readArrayElement_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var12;
               this.state_0_ = var12 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               var8 = arg0Value.readArrayElement(arg1Value, this, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var8;
         }

         @Override
         public void writeArrayElement(Object arg0Value_, long arg1Value, Object arg2Value) throws UnsupportedMessageException, UnsupportedTypeException, InvalidArrayIndexException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 64) != 0) {
               arg0Value.writeArrayElement(arg1Value, arg2Value, this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.writeArrayElementNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }
         }

         private void writeArrayElementNode_AndSpecialize(HostProxy arg0Value, long arg1Value, Object arg2Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               InteropLibrary writeArrayElementNode__writeArrayElement_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var12;
               this.state_0_ = var12 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               arg0Value.writeArrayElement(arg1Value, arg2Value, this, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public void removeArrayElement(Object arg0Value_, long arg1Value) throws UnsupportedMessageException, InvalidArrayIndexException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 128) != 0) {
               arg0Value.removeArrayElement(arg1Value, this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.removeArrayElementNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private void removeArrayElementNode_AndSpecialize(HostProxy arg0Value, long arg1Value) throws UnsupportedMessageException, InvalidArrayIndexException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               InteropLibrary removeArrayElementNode__removeArrayElement_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var11;
               this.state_0_ = var11 = state_0 | 128;
               lock.unlock();
               hasLock = false;
               arg0Value.removeArrayElement(arg1Value, this, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public long getArraySize(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 256) != 0) {
               return arg0Value.getArraySize(this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.getArraySizeNode_AndSpecialize(arg0Value);
            }
         }

         private long getArraySizeNode_AndSpecialize(HostProxy arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            long var6;
            try {
               int state_0 = this.state_0_;
               InteropLibrary getArraySizeNode__getArraySize_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var11;
               this.state_0_ = var11 = state_0 | 256;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.getArraySize(this, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public boolean isArrayElementReadable(Object arg0Value_, long arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 512) != 0) {
               return arg0Value.isArrayElementExisting(arg1Value, this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isArrayElementExistingNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isArrayElementExistingNode_AndSpecialize(HostProxy arg0Value, long arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var8;
            try {
               int state_0 = this.state_0_;
               InteropLibrary isArrayElementExistingNode__isArrayElementExisting_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var12;
               this.state_0_ = var12 = state_0 | 512;
               lock.unlock();
               hasLock = false;
               var8 = arg0Value.isArrayElementExisting(arg1Value, this, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var8;
         }

         @Override
         public boolean isArrayElementModifiable(Object arg0Value_, long arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 512) != 0) {
               return arg0Value.isArrayElementExisting(arg1Value, this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isArrayElementExistingNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         @Override
         public boolean isArrayElementRemovable(Object arg0Value_, long arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 512) != 0) {
               return arg0Value.isArrayElementExisting(arg1Value, this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isArrayElementExistingNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         @Override
         public boolean isArrayElementInsertable(Object arg0Value_, long arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 1024) != 0) {
               return arg0Value.isArrayElementInsertable(arg1Value, this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isArrayElementInsertableNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isArrayElementInsertableNode_AndSpecialize(HostProxy arg0Value, long arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var8;
            try {
               int state_0 = this.state_0_;
               InteropLibrary isArrayElementInsertableNode__isArrayElementInsertable_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var12;
               this.state_0_ = var12 = state_0 | 1024;
               lock.unlock();
               hasLock = false;
               var8 = arg0Value.isArrayElementInsertable(arg1Value, this, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var8;
         }

         @Override
         public boolean hasMembers(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostProxy)receiver).hasMembers();
         }

         @Override
         public Object getMembers(Object arg0Value_, boolean arg1Value) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 2048) != 0) {
               return arg0Value.getMembers(arg1Value, this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.getMembersNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private Object getMembersNode_AndSpecialize(HostProxy arg0Value, boolean arg1Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var7;
            try {
               int state_0 = this.state_0_;
               InteropLibrary getMembersNode__getMembers_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var11;
               this.state_0_ = var11 = state_0 | 2048;
               lock.unlock();
               hasLock = false;
               var7 = arg0Value.getMembers(arg1Value, this, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public Object readMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 4096) != 0) {
               return arg0Value.readMember(arg1Value, this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.readMemberNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private Object readMemberNode_AndSpecialize(HostProxy arg0Value, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var7;
            try {
               int state_0 = this.state_0_;
               InteropLibrary readMemberNode__readMember_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var11;
               this.state_0_ = var11 = state_0 | 4096;
               lock.unlock();
               hasLock = false;
               var7 = arg0Value.readMember(arg1Value, this, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public void writeMember(Object arg0Value_, String arg1Value, Object arg2Value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 8192) != 0) {
               arg0Value.writeMember(arg1Value, arg2Value, this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.writeMemberNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }
         }

         private void writeMemberNode_AndSpecialize(HostProxy arg0Value, String arg1Value, Object arg2Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               InteropLibrary writeMemberNode__writeMember_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var11;
               this.state_0_ = var11 = state_0 | 8192;
               lock.unlock();
               hasLock = false;
               arg0Value.writeMember(arg1Value, arg2Value, this, this.cache);
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

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 16384) != 0) {
               return arg0Value.invokeMember(arg1Value, arg2Value, this, this.executables, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.invokeMemberNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }
         }

         private Object invokeMemberNode_AndSpecialize(HostProxy arg0Value, String arg1Value, Object[] arg2Value) throws UnsupportedMessageException, UnsupportedTypeException, ArityException, UnknownIdentifierException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var8;
            try {
               int state_0 = this.state_0_;
               InteropLibrary invokeMemberNode__invokeMember_library__ = null;
               this.executables = super.insert(this.executables == null ? HostProxyGen.INTEROP_LIBRARY_.createDispatched(5) : this.executables);
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var12;
               this.state_0_ = var12 = state_0 | 16384;
               lock.unlock();
               hasLock = false;
               var8 = arg0Value.invokeMember(arg1Value, arg2Value, this, this.executables, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var8;
         }

         @Override
         public boolean isMemberInvocable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 32768) != 0) {
               return arg0Value.isMemberInvocable(arg1Value, this, this.executables, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isMemberInvocableNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isMemberInvocableNode_AndSpecialize(HostProxy arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var7;
            try {
               int state_0 = this.state_0_;
               InteropLibrary isMemberInvocableNode__isMemberInvocable_library__ = null;
               this.executables = super.insert(this.executables == null ? HostProxyGen.INTEROP_LIBRARY_.createDispatched(5) : this.executables);
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var11;
               this.state_0_ = var11 = state_0 | 32768;
               lock.unlock();
               hasLock = false;
               var7 = arg0Value.isMemberInvocable(arg1Value, this, this.executables, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public void removeMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 65536) != 0) {
               arg0Value.removeMember(arg1Value, this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.removeMemberNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private void removeMemberNode_AndSpecialize(HostProxy arg0Value, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               InteropLibrary removeMemberNode__removeMember_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var10;
               this.state_0_ = var10 = state_0 | 65536;
               lock.unlock();
               hasLock = false;
               arg0Value.removeMember(arg1Value, this, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public boolean isMemberReadable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 131072) != 0) {
               return arg0Value.isMemberExisting(arg1Value, this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isMemberExistingNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isMemberExistingNode_AndSpecialize(HostProxy arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var7;
            try {
               int state_0 = this.state_0_;
               InteropLibrary isMemberExistingNode__isMemberExisting_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var11;
               this.state_0_ = var11 = state_0 | 131072;
               lock.unlock();
               hasLock = false;
               var7 = arg0Value.isMemberExisting(arg1Value, this, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public boolean isMemberModifiable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 131072) != 0) {
               return arg0Value.isMemberExisting(arg1Value, this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isMemberExistingNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         @Override
         public boolean isMemberRemovable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 131072) != 0) {
               return arg0Value.isMemberExisting(arg1Value, this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isMemberExistingNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         @Override
         public boolean isMemberInsertable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 262144) != 0) {
               return arg0Value.isMemberInsertable(arg1Value, this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isMemberInsertableNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isMemberInsertableNode_AndSpecialize(HostProxy arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var7;
            try {
               int state_0 = this.state_0_;
               InteropLibrary isMemberInsertableNode__isMemberInsertable_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var11;
               this.state_0_ = var11 = state_0 | 262144;
               lock.unlock();
               hasLock = false;
               var7 = arg0Value.isMemberInsertable(arg1Value, this, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public boolean isDate(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostProxy)receiver).isDate();
         }

         @Override
         public boolean isTime(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostProxy)receiver).isTime();
         }

         @Override
         public boolean isTimeZone(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostProxy)receiver).isTimeZone();
         }

         @Override
         public ZoneId asTimeZone(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 524288) != 0) {
               return arg0Value.asTimeZone(this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.asTimeZoneNode_AndSpecialize(arg0Value);
            }
         }

         private ZoneId asTimeZoneNode_AndSpecialize(HostProxy arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            ZoneId var6;
            try {
               int state_0 = this.state_0_;
               InteropLibrary asTimeZoneNode__asTimeZone_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var10;
               this.state_0_ = var10 = state_0 | 524288;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.asTimeZone(this, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public LocalDate asDate(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 1048576) != 0) {
               return arg0Value.asDate(this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.asDateNode_AndSpecialize(arg0Value);
            }
         }

         private LocalDate asDateNode_AndSpecialize(HostProxy arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            LocalDate var6;
            try {
               int state_0 = this.state_0_;
               InteropLibrary asDateNode__asDate_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var10;
               this.state_0_ = var10 = state_0 | 1048576;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.asDate(this, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public LocalTime asTime(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 2097152) != 0) {
               return arg0Value.asTime(this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.asTimeNode_AndSpecialize(arg0Value);
            }
         }

         private LocalTime asTimeNode_AndSpecialize(HostProxy arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            LocalTime var6;
            try {
               int state_0 = this.state_0_;
               InteropLibrary asTimeNode__asTime_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var10;
               this.state_0_ = var10 = state_0 | 2097152;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.asTime(this, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public Instant asInstant(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 4194304) != 0) {
               return arg0Value.asInstant(this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.asInstantNode_AndSpecialize(arg0Value);
            }
         }

         private Instant asInstantNode_AndSpecialize(HostProxy arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Instant var6;
            try {
               int state_0 = this.state_0_;
               InteropLibrary asInstantNode__asInstant_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var10;
               this.state_0_ = var10 = state_0 | 4194304;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.asInstant(this, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public boolean isDuration(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostProxy)receiver).isDuration();
         }

         @Override
         public Duration asDuration(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 8388608) != 0) {
               return arg0Value.asDuration(this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.asDurationNode_AndSpecialize(arg0Value);
            }
         }

         private Duration asDurationNode_AndSpecialize(HostProxy arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Duration var6;
            try {
               int state_0 = this.state_0_;
               InteropLibrary asDurationNode__asDuration_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var10;
               this.state_0_ = var10 = state_0 | 8388608;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.asDuration(this, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostProxy)receiver).hasLanguage();
         }

         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostProxy)receiver).getLanguage();
         }

         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostProxy)receiver).toDisplayString(allowSideEffects);
         }

         @Override
         public boolean hasMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostProxy)receiver).hasMetaObject();
         }

         @Override
         public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostProxy)receiver).getMetaObject();
         }

         @Override
         public boolean hasIterator(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostProxy)receiver).hasIterator();
         }

         @Override
         public Object getIterator(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 16777216) != 0) {
               return arg0Value.getIterator(this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.getIteratorNode_AndSpecialize(arg0Value);
            }
         }

         private Object getIteratorNode_AndSpecialize(HostProxy arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var6;
            try {
               int state_0 = this.state_0_;
               InteropLibrary getIteratorNode__getIterator_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var10;
               this.state_0_ = var10 = state_0 | 16777216;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.getIterator(this, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public boolean isIterator(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostProxy)receiver).isIterator();
         }

         @Override
         public boolean hasIteratorNextElement(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 33554432) != 0) {
               return arg0Value.hasIteratorNextElement(this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.hasIteratorNextElementNode_AndSpecialize(arg0Value);
            }
         }

         private boolean hasIteratorNextElementNode_AndSpecialize(HostProxy arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_0 = this.state_0_;
               InteropLibrary hasIteratorNextElementNode__hasIteratorNextElement_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var10;
               this.state_0_ = var10 = state_0 | 33554432;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.hasIteratorNextElement(this, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public Object getIteratorNextElement(Object arg0Value_) throws UnsupportedMessageException, StopIterationException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 67108864) != 0) {
               return arg0Value.getIteratorNextElement(this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.getIteratorNextElementNode_AndSpecialize(arg0Value);
            }
         }

         private Object getIteratorNextElementNode_AndSpecialize(HostProxy arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var6;
            try {
               int state_0 = this.state_0_;
               InteropLibrary getIteratorNextElementNode__getIteratorNextElement_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var10;
               this.state_0_ = var10 = state_0 | 67108864;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.getIteratorNextElement(this, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public boolean hasHashEntries(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostProxy)receiver).hasHashEntries();
         }

         @Override
         public long getHashSize(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 134217728) != 0) {
               return arg0Value.getHashSize(this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.getHashSizeNode_AndSpecialize(arg0Value);
            }
         }

         private long getHashSizeNode_AndSpecialize(HostProxy arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            long var6;
            try {
               int state_0 = this.state_0_;
               InteropLibrary getHashSizeNode__getHashSize_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var11;
               this.state_0_ = var11 = state_0 | 134217728;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.getHashSize(this, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public boolean isHashEntryReadable(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 268435456) != 0) {
               return arg0Value.isHashValueExisting(arg1Value, this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isHashValueExistingNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isHashValueExistingNode_AndSpecialize(HostProxy arg0Value, Object arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var7;
            try {
               int state_0 = this.state_0_;
               InteropLibrary isHashValueExistingNode__isHashValueExisting_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var11;
               this.state_0_ = var11 = state_0 | 268435456;
               lock.unlock();
               hasLock = false;
               var7 = arg0Value.isHashValueExisting(arg1Value, this, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public boolean isHashEntryModifiable(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 268435456) != 0) {
               return arg0Value.isHashValueExisting(arg1Value, this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isHashValueExistingNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         @Override
         public boolean isHashEntryRemovable(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 268435456) != 0) {
               return arg0Value.isHashValueExisting(arg1Value, this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isHashValueExistingNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         @Override
         public Object readHashValue(Object arg0Value_, Object arg1Value) throws UnsupportedMessageException, UnknownKeyException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 536870912) != 0) {
               return arg0Value.readHashValue(arg1Value, this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.readHashValueNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private Object readHashValueNode_AndSpecialize(HostProxy arg0Value, Object arg1Value) throws UnsupportedMessageException, UnknownKeyException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var7;
            try {
               int state_0 = this.state_0_;
               InteropLibrary readHashValueNode__readHashValue_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var11;
               this.state_0_ = var11 = state_0 | 536870912;
               lock.unlock();
               hasLock = false;
               var7 = arg0Value.readHashValue(arg1Value, this, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public boolean isHashEntryInsertable(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 1073741824) != 0) {
               return arg0Value.isHashEntryInsertable(arg1Value, this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isHashEntryInsertableNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isHashEntryInsertableNode_AndSpecialize(HostProxy arg0Value, Object arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var7;
            try {
               int state_0 = this.state_0_;
               InteropLibrary isHashEntryInsertableNode__isHashEntryInsertable_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var11;
               this.state_0_ = var11 = state_0 | 1073741824;
               lock.unlock();
               hasLock = false;
               var7 = arg0Value.isHashEntryInsertable(arg1Value, this, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public void writeHashEntry(Object arg0Value_, Object arg1Value, Object arg2Value) throws UnsupportedMessageException, UnknownKeyException, UnsupportedTypeException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & -2147483648) != 0) {
               arg0Value.writeHashEntry(arg1Value, arg2Value, this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.writeHashEntryNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }
         }

         private void writeHashEntryNode_AndSpecialize(HostProxy arg0Value, Object arg1Value, Object arg2Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               InteropLibrary writeHashEntryNode__writeHashEntry_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var11;
               this.state_0_ = var11 = state_0 | -2147483648;
               lock.unlock();
               hasLock = false;
               arg0Value.writeHashEntry(arg1Value, arg2Value, this, this.cache);
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

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_1 = this.state_1_;
            if ((state_1 & 1) != 0) {
               arg0Value.removeHashEntry(arg1Value, this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.removeHashEntryNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private void removeHashEntryNode_AndSpecialize(HostProxy arg0Value, Object arg1Value) throws UnsupportedMessageException, UnknownKeyException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_1 = this.state_1_;
               InteropLibrary removeHashEntryNode__removeHashEntry_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var10;
               this.state_1_ = var10 = state_1 | 1;
               lock.unlock();
               hasLock = false;
               arg0Value.removeHashEntry(arg1Value, this, this.cache);
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

            HostProxy arg0Value = (HostProxy)arg0Value_;
            int state_1 = this.state_1_;
            if ((state_1 & 2) != 0) {
               return arg0Value.getHashEntriesIterator(this, this.cache);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.getHashEntriesIteratorNode_AndSpecialize(arg0Value);
            }
         }

         private Object getHashEntriesIteratorNode_AndSpecialize(HostProxy arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var6;
            try {
               int state_1 = this.state_1_;
               InteropLibrary getHashEntriesIteratorNode__getHashEntriesIterator_library__ = null;
               this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
               int var10;
               this.state_1_ = var10 = state_1 | 2;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.getHashEntriesIterator(this, this.cache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public int identityHashCode(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return HostProxy.identityHashCode((HostProxy)receiver);
         }

         private static boolean isIdenticalOrUndefinedFallbackGuard_(int state_0, HostProxy arg0Value, Object arg1Value) {
            return (state_0 & 1) != 0 || !(arg1Value instanceof HostProxy);
         }
      }

      @GeneratedBy(HostProxy.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof HostProxy) || HostProxyGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof HostProxy;
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
         public TriState isIdenticalOrUndefined(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            if (arg1Value instanceof HostProxy) {
               HostProxy arg1Value_ = (HostProxy)arg1Value;
               return HostProxy.IsIdenticalOrUndefined.doHostObject(arg0Value, arg1Value_);
            } else {
               return HostProxy.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
            }
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isInstantiable(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostProxy)receiver).isInstantiable();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object instantiate(Object arg0Value_, Object... arg1Value) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.instantiate(arg1Value, this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isExecutable(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostProxy)receiver).isExecutable();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object execute(Object arg0Value_, Object... arg1Value) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.execute(arg1Value, this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isPointer(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostProxy)receiver).isPointer();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long asPointer(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.asPointer(this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasArrayElements(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostProxy)receiver).hasArrayElements();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readArrayElement(Object arg0Value_, long arg1Value) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.readArrayElement(arg1Value, this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeArrayElement(Object arg0Value_, long arg1Value, Object arg2Value) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            arg0Value.writeArrayElement(arg1Value, arg2Value, this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void removeArrayElement(Object arg0Value_, long arg1Value) throws UnsupportedMessageException, InvalidArrayIndexException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            arg0Value.removeArrayElement(arg1Value, this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long getArraySize(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.getArraySize(this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isArrayElementReadable(Object arg0Value_, long arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.isArrayElementExisting(arg1Value, this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isArrayElementModifiable(Object arg0Value_, long arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.isArrayElementExisting(arg1Value, this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isArrayElementRemovable(Object arg0Value_, long arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.isArrayElementExisting(arg1Value, this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isArrayElementInsertable(Object arg0Value_, long arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.isArrayElementInsertable(arg1Value, this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMembers(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostProxy)receiver).hasMembers();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMembers(Object arg0Value_, boolean arg1Value) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.getMembers(arg1Value, this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.readMember(arg1Value, this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeMember(Object arg0Value_, String arg1Value, Object arg2Value) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            arg0Value.writeMember(arg1Value, arg2Value, this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object invokeMember(Object arg0Value_, String arg1Value, Object... arg2Value) throws UnsupportedMessageException, UnsupportedTypeException, ArityException, UnknownIdentifierException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.invokeMember(arg1Value, arg2Value, this, HostProxyGen.INTEROP_LIBRARY_.getUncached(), arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberInvocable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.isMemberInvocable(arg1Value, this, HostProxyGen.INTEROP_LIBRARY_.getUncached(), arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void removeMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            arg0Value.removeMember(arg1Value, this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberReadable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.isMemberExisting(arg1Value, this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberModifiable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.isMemberExisting(arg1Value, this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberRemovable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.isMemberExisting(arg1Value, this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberInsertable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.isMemberInsertable(arg1Value, this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isDate(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostProxy)receiver).isDate();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isTime(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostProxy)receiver).isTime();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isTimeZone(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostProxy)receiver).isTimeZone();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public ZoneId asTimeZone(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.asTimeZone(this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public LocalDate asDate(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.asDate(this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public LocalTime asTime(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.asTime(this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Instant asInstant(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.asInstant(this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isDuration(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostProxy)receiver).isDuration();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Duration asDuration(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.asDuration(this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostProxy)receiver).hasLanguage();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostProxy)receiver).getLanguage();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostProxy)receiver).toDisplayString(allowSideEffects);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostProxy)receiver).hasMetaObject();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostProxy)receiver).getMetaObject();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasIterator(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostProxy)receiver).hasIterator();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getIterator(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.getIterator(this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isIterator(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostProxy)receiver).isIterator();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasIteratorNextElement(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.hasIteratorNextElement(this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getIteratorNextElement(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.getIteratorNextElement(this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasHashEntries(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostProxy)receiver).hasHashEntries();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long getHashSize(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.getHashSize(this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isHashEntryReadable(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.isHashValueExisting(arg1Value, this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isHashEntryModifiable(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.isHashValueExisting(arg1Value, this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isHashEntryRemovable(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.isHashValueExisting(arg1Value, this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readHashValue(Object arg0Value_, Object arg1Value) throws UnsupportedMessageException, UnknownKeyException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.readHashValue(arg1Value, this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isHashEntryInsertable(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.isHashEntryInsertable(arg1Value, this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeHashEntry(Object arg0Value_, Object arg1Value, Object arg2Value) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            arg0Value.writeHashEntry(arg1Value, arg2Value, this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void removeHashEntry(Object arg0Value_, Object arg1Value) throws UnsupportedMessageException, UnknownKeyException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            arg0Value.removeHashEntry(arg1Value, this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getHashEntriesIterator(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostProxy arg0Value = (HostProxy)arg0Value_;
            return arg0Value.getHashEntriesIterator(this, arg0Value.context.getGuestToHostCache());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public int identityHashCode(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return HostProxy.identityHashCode((HostProxy)receiver);
         }
      }
   }
}
