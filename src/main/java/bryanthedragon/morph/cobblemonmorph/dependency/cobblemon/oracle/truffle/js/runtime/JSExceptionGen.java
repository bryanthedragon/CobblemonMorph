package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSException.class)
final class JSExceptionGen {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

   private JSExceptionGen() {
   }

   static {
      LibraryExport.register(JSException.class, new JSExceptionGen.InteropLibraryExports());
   }

   @GeneratedBy(JSException.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, JSException.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof JSException;

         InteropLibrary uncached = new JSExceptionGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof JSException;

         return new JSExceptionGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(JSException.class)
      private static final class Cached extends GraalJSExceptionGen.InteropLibraryExports.Cached {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @Node.Child
         private InteropLibrary thisLib;
         @Node.Child
         private InteropLibrary otherLib;
         @Node.Child
         private InteropLibrary delegateLib;

         protected Cached(Object receiver) {
            super(receiver);
         }

         @Override
         public ExceptionType getExceptionType(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSException)receiver).getExceptionType();
         }

         @Override
         public boolean isExceptionIncompleteSource(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSException)receiver).isExceptionIncompleteSource();
         }

         @Override
         public boolean hasMembers(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSException)receiver).hasMembers();
         }

         @Override
         public Object getMembers(Object arg0Value_, boolean arg1Value) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSException arg0Value = (JSException)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 1) != 0) {
               return arg0Value.getMembers(arg1Value, this.delegateLib);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.getMembersNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private Object getMembersNode_AndSpecialize(JSException arg0Value, boolean arg1Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var6;
            try {
               int state_0 = this.state_0_;
               this.delegateLib = super.insert(this.delegateLib == null ? JSExceptionGen.INTEROP_LIBRARY_.createDispatched(5) : this.delegateLib);
               int var10;
               this.state_0_ = var10 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.getMembers(arg1Value, this.delegateLib);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public NodeCost getCost() {
            int state_0 = this.state_0_;
            return (state_0 & 1) == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
         }

         @Override
         public boolean isMemberReadable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSException arg0Value = (JSException)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 2) != 0) {
               return arg0Value.isMemberReadable(arg1Value, this.delegateLib);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isMemberReadableNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isMemberReadableNode_AndSpecialize(JSException arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_0 = this.state_0_;
               this.delegateLib = super.insert(this.delegateLib == null ? JSExceptionGen.INTEROP_LIBRARY_.createDispatched(5) : this.delegateLib);
               int var10;
               this.state_0_ = var10 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.isMemberReadable(arg1Value, this.delegateLib);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public boolean isMemberModifiable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSException arg0Value = (JSException)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 4) != 0) {
               return arg0Value.isMemberModifiable(arg1Value, this.delegateLib);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isMemberModifiableNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isMemberModifiableNode_AndSpecialize(JSException arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_0 = this.state_0_;
               this.delegateLib = super.insert(this.delegateLib == null ? JSExceptionGen.INTEROP_LIBRARY_.createDispatched(5) : this.delegateLib);
               int var10;
               this.state_0_ = var10 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.isMemberModifiable(arg1Value, this.delegateLib);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public boolean isMemberInsertable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSException arg0Value = (JSException)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 8) != 0) {
               return arg0Value.isMemberInsertable(arg1Value, this.delegateLib);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isMemberInsertableNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isMemberInsertableNode_AndSpecialize(JSException arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_0 = this.state_0_;
               this.delegateLib = super.insert(this.delegateLib == null ? JSExceptionGen.INTEROP_LIBRARY_.createDispatched(5) : this.delegateLib);
               int var10;
               this.state_0_ = var10 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.isMemberInsertable(arg1Value, this.delegateLib);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public boolean isMemberRemovable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSException arg0Value = (JSException)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 16) != 0) {
               return arg0Value.isMemberRemovable(arg1Value, this.delegateLib);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isMemberRemovableNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isMemberRemovableNode_AndSpecialize(JSException arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_0 = this.state_0_;
               this.delegateLib = super.insert(this.delegateLib == null ? JSExceptionGen.INTEROP_LIBRARY_.createDispatched(5) : this.delegateLib);
               int var10;
               this.state_0_ = var10 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.isMemberRemovable(arg1Value, this.delegateLib);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public boolean isMemberInvocable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSException arg0Value = (JSException)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 32) != 0) {
               return arg0Value.isMemberInvocable(arg1Value, this.delegateLib);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isMemberInvocableNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isMemberInvocableNode_AndSpecialize(JSException arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_0 = this.state_0_;
               this.delegateLib = super.insert(this.delegateLib == null ? JSExceptionGen.INTEROP_LIBRARY_.createDispatched(5) : this.delegateLib);
               int var10;
               this.state_0_ = var10 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.isMemberInvocable(arg1Value, this.delegateLib);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public boolean hasMemberReadSideEffects(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSException arg0Value = (JSException)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 64) != 0) {
               return arg0Value.hasMemberReadSideEffects(arg1Value, this.delegateLib);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.hasMemberReadSideEffectsNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean hasMemberReadSideEffectsNode_AndSpecialize(JSException arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_0 = this.state_0_;
               this.delegateLib = super.insert(this.delegateLib == null ? JSExceptionGen.INTEROP_LIBRARY_.createDispatched(5) : this.delegateLib);
               int var10;
               this.state_0_ = var10 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.hasMemberReadSideEffects(arg1Value, this.delegateLib);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public boolean hasMemberWriteSideEffects(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSException arg0Value = (JSException)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 128) != 0) {
               return arg0Value.hasMemberWriteSideEffects(arg1Value, this.delegateLib);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.hasMemberWriteSideEffectsNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean hasMemberWriteSideEffectsNode_AndSpecialize(JSException arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_0 = this.state_0_;
               this.delegateLib = super.insert(this.delegateLib == null ? JSExceptionGen.INTEROP_LIBRARY_.createDispatched(5) : this.delegateLib);
               int var10;
               this.state_0_ = var10 = state_0 | 128;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.hasMemberWriteSideEffects(arg1Value, this.delegateLib);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public Object readMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSException arg0Value = (JSException)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 256) != 0) {
               return arg0Value.readMember(arg1Value, this.delegateLib);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.readMemberNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private Object readMemberNode_AndSpecialize(JSException arg0Value, String arg1Value) throws UnknownIdentifierException, UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var6;
            try {
               int state_0 = this.state_0_;
               this.delegateLib = super.insert(this.delegateLib == null ? JSExceptionGen.INTEROP_LIBRARY_.createDispatched(5) : this.delegateLib);
               int var10;
               this.state_0_ = var10 = state_0 | 256;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.readMember(arg1Value, this.delegateLib);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public void writeMember(Object arg0Value_, String arg1Value, Object arg2Value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSException arg0Value = (JSException)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 512) != 0) {
               arg0Value.writeMember(arg1Value, arg2Value, this.delegateLib);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.writeMemberNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }
         }

         private void writeMemberNode_AndSpecialize(JSException arg0Value, String arg1Value, Object arg2Value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               this.delegateLib = super.insert(this.delegateLib == null ? JSExceptionGen.INTEROP_LIBRARY_.createDispatched(5) : this.delegateLib);
               int var10;
               this.state_0_ = var10 = state_0 | 512;
               lock.unlock();
               hasLock = false;
               arg0Value.writeMember(arg1Value, arg2Value, this.delegateLib);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public void removeMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSException arg0Value = (JSException)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 1024) != 0) {
               arg0Value.removeMember(arg1Value, this.delegateLib);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.removeMemberNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private void removeMemberNode_AndSpecialize(JSException arg0Value, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               this.delegateLib = super.insert(this.delegateLib == null ? JSExceptionGen.INTEROP_LIBRARY_.createDispatched(5) : this.delegateLib);
               int var9;
               this.state_0_ = var9 = state_0 | 1024;
               lock.unlock();
               hasLock = false;
               arg0Value.removeMember(arg1Value, this.delegateLib);
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

            JSException arg0Value = (JSException)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 2048) != 0) {
               return arg0Value.invokeMember(arg1Value, arg2Value, this.delegateLib);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.invokeMemberNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }
         }

         private Object invokeMemberNode_AndSpecialize(JSException arg0Value, String arg1Value, Object[] arg2Value) throws UnsupportedMessageException, UnknownIdentifierException, ArityException, UnsupportedTypeException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var7;
            try {
               int state_0 = this.state_0_;
               this.delegateLib = super.insert(this.delegateLib == null ? JSExceptionGen.INTEROP_LIBRARY_.createDispatched(5) : this.delegateLib);
               int var11;
               this.state_0_ = var11 = state_0 | 2048;
               lock.unlock();
               hasLock = false;
               var7 = arg0Value.invokeMember(arg1Value, arg2Value, this.delegateLib);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public boolean hasMetaObject(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSException arg0Value = (JSException)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 4096) != 0) {
               return arg0Value.hasMetaObject(this.delegateLib);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.hasMetaObjectNode_AndSpecialize(arg0Value);
            }
         }

         private boolean hasMetaObjectNode_AndSpecialize(JSException arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var5;
            try {
               int state_0 = this.state_0_;
               this.delegateLib = super.insert(this.delegateLib == null ? JSExceptionGen.INTEROP_LIBRARY_.createDispatched(5) : this.delegateLib);
               int var9;
               this.state_0_ = var9 = state_0 | 4096;
               lock.unlock();
               hasLock = false;
               var5 = arg0Value.hasMetaObject(this.delegateLib);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var5;
         }

         @Override
         public Object getMetaObject(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSException arg0Value = (JSException)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 8192) != 0) {
               return arg0Value.getMetaObject(this.delegateLib);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.getMetaObjectNode_AndSpecialize(arg0Value);
            }
         }

         private Object getMetaObjectNode_AndSpecialize(JSException arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var5;
            try {
               int state_0 = this.state_0_;
               this.delegateLib = super.insert(this.delegateLib == null ? JSExceptionGen.INTEROP_LIBRARY_.createDispatched(5) : this.delegateLib);
               int var9;
               this.state_0_ = var9 = state_0 | 8192;
               lock.unlock();
               hasLock = false;
               var5 = arg0Value.getMetaObject(this.delegateLib);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var5;
         }
      }

      @GeneratedBy(JSException.class)
      @DenyReplace
      private static final class Uncached extends GraalJSExceptionGen.InteropLibraryExports.Uncached {
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
         public ExceptionType getExceptionType(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSException)receiver).getExceptionType();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isExceptionIncompleteSource(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSException)receiver).isExceptionIncompleteSource();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMembers(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSException)receiver).hasMembers();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMembers(Object arg0Value_, boolean arg1Value) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSException arg0Value = (JSException)arg0Value_;
            return arg0Value.getMembers(arg1Value, JSExceptionGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberReadable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSException arg0Value = (JSException)arg0Value_;
            return arg0Value.isMemberReadable(arg1Value, JSExceptionGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberModifiable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSException arg0Value = (JSException)arg0Value_;
            return arg0Value.isMemberModifiable(arg1Value, JSExceptionGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberInsertable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSException arg0Value = (JSException)arg0Value_;
            return arg0Value.isMemberInsertable(arg1Value, JSExceptionGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberRemovable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSException arg0Value = (JSException)arg0Value_;
            return arg0Value.isMemberRemovable(arg1Value, JSExceptionGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberInvocable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSException arg0Value = (JSException)arg0Value_;
            return arg0Value.isMemberInvocable(arg1Value, JSExceptionGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMemberReadSideEffects(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSException arg0Value = (JSException)arg0Value_;
            return arg0Value.hasMemberReadSideEffects(arg1Value, JSExceptionGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMemberWriteSideEffects(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSException arg0Value = (JSException)arg0Value_;
            return arg0Value.hasMemberWriteSideEffects(arg1Value, JSExceptionGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readMember(Object arg0Value_, String arg1Value) throws UnknownIdentifierException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSException arg0Value = (JSException)arg0Value_;
            return arg0Value.readMember(arg1Value, JSExceptionGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeMember(Object arg0Value_, String arg1Value, Object arg2Value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSException arg0Value = (JSException)arg0Value_;
            arg0Value.writeMember(arg1Value, arg2Value, JSExceptionGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void removeMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSException arg0Value = (JSException)arg0Value_;
            arg0Value.removeMember(arg1Value, JSExceptionGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object invokeMember(Object arg0Value_, String arg1Value, Object... arg2Value) throws UnsupportedMessageException, UnknownIdentifierException, ArityException, UnsupportedTypeException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSException arg0Value = (JSException)arg0Value_;
            return arg0Value.invokeMember(arg1Value, arg2Value, JSExceptionGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMetaObject(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSException arg0Value = (JSException)arg0Value_;
            return arg0Value.hasMetaObject(JSExceptionGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMetaObject(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSException arg0Value = (JSException)arg0Value_;
            return arg0Value.getMetaObject(JSExceptionGen.INTEROP_LIBRARY_.getUncached());
         }
      }
   }
}
