package com.oracle.truffle.js.runtime.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import java.util.concurrent.locks.Lock;

@GeneratedBy(TopScopeObject.class)
final class TopScopeObjectGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

   private TopScopeObjectGen() {
   }

   static {
      LibraryExport.register(TopScopeObject.class, new TopScopeObjectGen.InteropLibraryExports());
   }

   @GeneratedBy(TopScopeObject.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, TopScopeObject.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof TopScopeObject;

         InteropLibrary uncached = new TopScopeObjectGen.InteropLibraryExports.Uncached();
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof TopScopeObject;

         return new TopScopeObjectGen.InteropLibraryExports.Cached();
      }

      @GeneratedBy(TopScopeObject.class)
      private static final class Cached extends InteropLibrary {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @Node.Child
         private InteropLibrary interop;

         protected Cached() {
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof TopScopeObject) || TopScopeObjectGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof TopScopeObject;
         }

         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((TopScopeObject)receiver).hasLanguage();
         }

         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((TopScopeObject)receiver).getLanguage();
         }

         @Override
         public boolean isScope(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((TopScopeObject)receiver).isScope();
         }

         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((TopScopeObject)receiver).toDisplayString(allowSideEffects);
         }

         @Override
         public boolean hasScopeParent(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((TopScopeObject)receiver).hasScopeParent();
         }

         @Override
         public Object getScopeParent(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((TopScopeObject)receiver).getScopeParent();
         }

         @Override
         public boolean hasMembers(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((TopScopeObject)receiver).hasMembers();
         }

         @Override
         public Object getMembers(Object arg0Value_, boolean arg1Value) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            TopScopeObject arg0Value = (TopScopeObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 1) != 0) {
               return arg0Value.getMembers(arg1Value, this.interop);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.getMembersNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private Object getMembersNode_AndSpecialize(TopScopeObject arg0Value, boolean arg1Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var6;
            try {
               int state_0 = this.state_0_;
               this.interop = super.insert(this.interop == null ? TopScopeObjectGen.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
               int var10;
               this.state_0_ = var10 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.getMembers(arg1Value, this.interop);
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

            TopScopeObject arg0Value = (TopScopeObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 2) != 0) {
               return arg0Value.isMemberReadable(arg1Value, this.interop);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isMemberReadableNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isMemberReadableNode_AndSpecialize(TopScopeObject arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_0 = this.state_0_;
               this.interop = super.insert(this.interop == null ? TopScopeObjectGen.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
               int var10;
               this.state_0_ = var10 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.isMemberReadable(arg1Value, this.interop);
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

            TopScopeObject arg0Value = (TopScopeObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 4) != 0) {
               return arg0Value.readMember(arg1Value, this.interop);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.readMemberNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private Object readMemberNode_AndSpecialize(TopScopeObject arg0Value, String arg1Value) throws UnknownIdentifierException, UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var6;
            try {
               int state_0 = this.state_0_;
               this.interop = super.insert(this.interop == null ? TopScopeObjectGen.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
               int var10;
               this.state_0_ = var10 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.readMember(arg1Value, this.interop);
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

            TopScopeObject arg0Value = (TopScopeObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 8) != 0) {
               return arg0Value.isMemberModifiable(arg1Value, this.interop);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isMemberModifiableNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isMemberModifiableNode_AndSpecialize(TopScopeObject arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_0 = this.state_0_;
               this.interop = super.insert(this.interop == null ? TopScopeObjectGen.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
               int var10;
               this.state_0_ = var10 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.isMemberModifiable(arg1Value, this.interop);
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

            TopScopeObject arg0Value = (TopScopeObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 16) != 0) {
               return arg0Value.isMemberInsertable(arg1Value, this.interop);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isMemberInsertableNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isMemberInsertableNode_AndSpecialize(TopScopeObject arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_0 = this.state_0_;
               this.interop = super.insert(this.interop == null ? TopScopeObjectGen.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
               int var10;
               this.state_0_ = var10 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.isMemberInsertable(arg1Value, this.interop);
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

            TopScopeObject arg0Value = (TopScopeObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 32) != 0) {
               return arg0Value.hasMemberReadSideEffects(arg1Value, this.interop);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.hasMemberReadSideEffectsNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean hasMemberReadSideEffectsNode_AndSpecialize(TopScopeObject arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_0 = this.state_0_;
               this.interop = super.insert(this.interop == null ? TopScopeObjectGen.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
               int var10;
               this.state_0_ = var10 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.hasMemberReadSideEffects(arg1Value, this.interop);
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

            TopScopeObject arg0Value = (TopScopeObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 64) != 0) {
               return arg0Value.hasMemberWriteSideEffects(arg1Value, this.interop);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.hasMemberWriteSideEffectsNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean hasMemberWriteSideEffectsNode_AndSpecialize(TopScopeObject arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_0 = this.state_0_;
               this.interop = super.insert(this.interop == null ? TopScopeObjectGen.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
               int var10;
               this.state_0_ = var10 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.hasMemberWriteSideEffects(arg1Value, this.interop);
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

            TopScopeObject arg0Value = (TopScopeObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 128) != 0) {
               arg0Value.writeMember(arg1Value, arg2Value, this.interop);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.writeMemberNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }
         }

         private void writeMemberNode_AndSpecialize(TopScopeObject arg0Value, String arg1Value, Object arg2Value) throws UnknownIdentifierException, UnsupportedMessageException, UnsupportedTypeException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               this.interop = super.insert(this.interop == null ? TopScopeObjectGen.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
               int var10;
               this.state_0_ = var10 = state_0 | 128;
               lock.unlock();
               hasLock = false;
               arg0Value.writeMember(arg1Value, arg2Value, this.interop);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public boolean isMemberRemovable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            TopScopeObject arg0Value = (TopScopeObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 256) != 0) {
               return arg0Value.isMemberRemovable(arg1Value, this.interop);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isMemberRemovableNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isMemberRemovableNode_AndSpecialize(TopScopeObject arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_0 = this.state_0_;
               this.interop = super.insert(this.interop == null ? TopScopeObjectGen.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
               int var10;
               this.state_0_ = var10 = state_0 | 256;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.isMemberRemovable(arg1Value, this.interop);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public void removeMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            TopScopeObject arg0Value = (TopScopeObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 512) != 0) {
               arg0Value.removeMember(arg1Value, this.interop);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.removeMemberNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private void removeMemberNode_AndSpecialize(TopScopeObject arg0Value, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               this.interop = super.insert(this.interop == null ? TopScopeObjectGen.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
               int var9;
               this.state_0_ = var9 = state_0 | 512;
               lock.unlock();
               hasLock = false;
               arg0Value.removeMember(arg1Value, this.interop);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }
      }

      @GeneratedBy(TopScopeObject.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof TopScopeObject) || TopScopeObjectGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof TopScopeObject;
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
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TopScopeObject)receiver).hasLanguage();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TopScopeObject)receiver).getLanguage();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isScope(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TopScopeObject)receiver).isScope();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TopScopeObject)receiver).toDisplayString(allowSideEffects);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasScopeParent(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TopScopeObject)receiver).hasScopeParent();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getScopeParent(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TopScopeObject)receiver).getScopeParent();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMembers(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TopScopeObject)receiver).hasMembers();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMembers(Object arg0Value_, boolean arg1Value) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            TopScopeObject arg0Value = (TopScopeObject)arg0Value_;
            return arg0Value.getMembers(arg1Value, TopScopeObjectGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberReadable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            TopScopeObject arg0Value = (TopScopeObject)arg0Value_;
            return arg0Value.isMemberReadable(arg1Value, TopScopeObjectGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readMember(Object arg0Value_, String arg1Value) throws UnknownIdentifierException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            TopScopeObject arg0Value = (TopScopeObject)arg0Value_;
            return arg0Value.readMember(arg1Value, TopScopeObjectGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberModifiable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            TopScopeObject arg0Value = (TopScopeObject)arg0Value_;
            return arg0Value.isMemberModifiable(arg1Value, TopScopeObjectGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberInsertable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            TopScopeObject arg0Value = (TopScopeObject)arg0Value_;
            return arg0Value.isMemberInsertable(arg1Value, TopScopeObjectGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMemberReadSideEffects(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            TopScopeObject arg0Value = (TopScopeObject)arg0Value_;
            return arg0Value.hasMemberReadSideEffects(arg1Value, TopScopeObjectGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMemberWriteSideEffects(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            TopScopeObject arg0Value = (TopScopeObject)arg0Value_;
            return arg0Value.hasMemberWriteSideEffects(arg1Value, TopScopeObjectGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeMember(Object arg0Value_, String arg1Value, Object arg2Value) throws UnknownIdentifierException, UnsupportedMessageException, UnsupportedTypeException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            TopScopeObject arg0Value = (TopScopeObject)arg0Value_;
            arg0Value.writeMember(arg1Value, arg2Value, TopScopeObjectGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberRemovable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            TopScopeObject arg0Value = (TopScopeObject)arg0Value_;
            return arg0Value.isMemberRemovable(arg1Value, TopScopeObjectGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void removeMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            TopScopeObject arg0Value = (TopScopeObject)arg0Value_;
            arg0Value.removeMember(arg1Value, TopScopeObjectGen.INTEROP_LIBRARY_.getUncached());
         }
      }
   }
}
