package com.oracle.truffle.js.runtime.interop;

import com.oracle.truffle.api.CompilerDirectives;
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
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import java.util.concurrent.locks.Lock;

@GeneratedBy(DynamicScopeWrapper.class)
final class DynamicScopeWrapperGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);
   private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);

   private DynamicScopeWrapperGen() {
   }

   static {
      LibraryExport.register(DynamicScopeWrapper.class, new DynamicScopeWrapperGen.InteropLibraryExports());
   }

   @GeneratedBy(DynamicScopeWrapper.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, DynamicScopeWrapper.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof DynamicScopeWrapper;

         InteropLibrary uncached = new DynamicScopeWrapperGen.InteropLibraryExports.Uncached();
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof DynamicScopeWrapper;

         return new DynamicScopeWrapperGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(DynamicScopeWrapper.class)
      private static final class Cached extends InteropLibrary {
         @Node.Child
         private DynamicObjectLibrary receiverScopeDynamicObjectLibrary_;
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @Node.Child
         private TruffleString.FromJavaStringNode fromJavaStringNode;
         @Node.Child
         private ExportValueNode readMemberNode__readMember_exportValueNode_;

         protected Cached(Object receiver) {
            DynamicScopeWrapper castReceiver = (DynamicScopeWrapper)receiver;
            this.receiverScopeDynamicObjectLibrary_ = super.insert(DynamicScopeWrapperGen.DYNAMIC_OBJECT_LIBRARY_.create(castReceiver.scope));
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof DynamicScopeWrapper) || DynamicScopeWrapperGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return !(receiver instanceof DynamicScopeWrapper) ? false : this.receiverScopeDynamicObjectLibrary_.accepts(((DynamicScopeWrapper)receiver).scope);
         }

         @Override
         public boolean hasMembers(Object receiver) {
            assert receiver instanceof DynamicScopeWrapper : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((DynamicScopeWrapper)receiver).hasMembers();
         }

         @Override
         public Object getMembers(Object arg0Value_, boolean arg1Value) throws UnsupportedMessageException {
            assert arg0Value_ instanceof DynamicScopeWrapper : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            DynamicScopeWrapper arg0Value = (DynamicScopeWrapper)arg0Value_;
            DynamicObjectLibrary getMembersNode__getMembers_access__ = this.receiverScopeDynamicObjectLibrary_;
            return arg0Value.getMembers(arg1Value, getMembersNode__getMembers_access__);
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
         }

         @Override
         public boolean isMemberReadable(Object arg0Value_, String arg1Value) {
            assert arg0Value_ instanceof DynamicScopeWrapper : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            DynamicScopeWrapper arg0Value = (DynamicScopeWrapper)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 1) != 0) {
               DynamicObjectLibrary isMemberReadableNode__isMemberReadable_access__ = this.receiverScopeDynamicObjectLibrary_;
               return arg0Value.isMemberReadable(arg1Value, this.fromJavaStringNode, isMemberReadableNode__isMemberReadable_access__);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isMemberReadableNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isMemberReadableNode_AndSpecialize(DynamicScopeWrapper arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var7;
            try {
               int state_0 = this.state_0_;
               DynamicObjectLibrary isMemberReadableNode__isMemberReadable_access__ = null;
               this.fromJavaStringNode = super.insert(this.fromJavaStringNode == null ? TruffleString.FromJavaStringNode.create() : this.fromJavaStringNode);
               isMemberReadableNode__isMemberReadable_access__ = this.receiverScopeDynamicObjectLibrary_;
               int var11;
               this.state_0_ = var11 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               var7 = arg0Value.isMemberReadable(arg1Value, this.fromJavaStringNode, isMemberReadableNode__isMemberReadable_access__);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public boolean isMemberModifiable(Object arg0Value_, String arg1Value) {
            assert arg0Value_ instanceof DynamicScopeWrapper : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            DynamicScopeWrapper arg0Value = (DynamicScopeWrapper)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 2) != 0) {
               DynamicObjectLibrary isMemberModifiableNode__isMemberModifiable_access__ = this.receiverScopeDynamicObjectLibrary_;
               return arg0Value.isMemberModifiable(arg1Value, this.fromJavaStringNode, isMemberModifiableNode__isMemberModifiable_access__);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isMemberModifiableNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isMemberModifiableNode_AndSpecialize(DynamicScopeWrapper arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var7;
            try {
               int state_0 = this.state_0_;
               DynamicObjectLibrary isMemberModifiableNode__isMemberModifiable_access__ = null;
               this.fromJavaStringNode = super.insert(this.fromJavaStringNode == null ? TruffleString.FromJavaStringNode.create() : this.fromJavaStringNode);
               isMemberModifiableNode__isMemberModifiable_access__ = this.receiverScopeDynamicObjectLibrary_;
               int var11;
               this.state_0_ = var11 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               var7 = arg0Value.isMemberModifiable(arg1Value, this.fromJavaStringNode, isMemberModifiableNode__isMemberModifiable_access__);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public boolean isMemberInsertable(Object receiver, String member) {
            assert receiver instanceof DynamicScopeWrapper : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((DynamicScopeWrapper)receiver).isMemberInsertable(member);
         }

         @Override
         public Object readMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
            assert arg0Value_ instanceof DynamicScopeWrapper : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            DynamicScopeWrapper arg0Value = (DynamicScopeWrapper)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 4) != 0) {
               DynamicObjectLibrary readMemberNode__readMember_access__ = this.receiverScopeDynamicObjectLibrary_;
               return arg0Value.readMember(
                  arg1Value, this.fromJavaStringNode, readMemberNode__readMember_access__, this.readMemberNode__readMember_exportValueNode_
               );
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.readMemberNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private Object readMemberNode_AndSpecialize(DynamicScopeWrapper arg0Value, String arg1Value) throws UnknownIdentifierException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var7;
            try {
               int state_0 = this.state_0_;
               DynamicObjectLibrary readMemberNode__readMember_access__ = null;
               this.fromJavaStringNode = super.insert(this.fromJavaStringNode == null ? TruffleString.FromJavaStringNode.create() : this.fromJavaStringNode);
               readMemberNode__readMember_access__ = this.receiverScopeDynamicObjectLibrary_;
               this.readMemberNode__readMember_exportValueNode_ = super.insert(ExportValueNode.create());
               int var11;
               this.state_0_ = var11 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               var7 = arg0Value.readMember(
                  arg1Value, this.fromJavaStringNode, readMemberNode__readMember_access__, this.readMemberNode__readMember_exportValueNode_
               );
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public void writeMember(Object arg0Value_, String arg1Value, Object arg2Value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
            assert arg0Value_ instanceof DynamicScopeWrapper : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            DynamicScopeWrapper arg0Value = (DynamicScopeWrapper)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 8) != 0) {
               DynamicObjectLibrary writeMemberNode__writeMember_access__ = this.receiverScopeDynamicObjectLibrary_;
               arg0Value.writeMember(arg1Value, arg2Value, this.fromJavaStringNode, writeMemberNode__writeMember_access__);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.writeMemberNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }
         }

         private void writeMemberNode_AndSpecialize(DynamicScopeWrapper arg0Value, String arg1Value, Object arg2Value) throws UnsupportedMessageException, UnknownIdentifierException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               DynamicObjectLibrary writeMemberNode__writeMember_access__ = null;
               this.fromJavaStringNode = super.insert(this.fromJavaStringNode == null ? TruffleString.FromJavaStringNode.create() : this.fromJavaStringNode);
               writeMemberNode__writeMember_access__ = this.receiverScopeDynamicObjectLibrary_;
               int var11;
               this.state_0_ = var11 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               arg0Value.writeMember(arg1Value, arg2Value, this.fromJavaStringNode, writeMemberNode__writeMember_access__);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }
      }

      @GeneratedBy(DynamicScopeWrapper.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof DynamicScopeWrapper) || DynamicScopeWrapperGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof DynamicScopeWrapper;
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
         public boolean hasMembers(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DynamicScopeWrapper)receiver).hasMembers();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMembers(Object arg0Value_, boolean arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            DynamicScopeWrapper arg0Value = (DynamicScopeWrapper)arg0Value_;
            return arg0Value.getMembers(arg1Value, DynamicScopeWrapperGen.DYNAMIC_OBJECT_LIBRARY_.getUncached(arg0Value.scope));
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberReadable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            DynamicScopeWrapper arg0Value = (DynamicScopeWrapper)arg0Value_;
            return arg0Value.isMemberReadable(
               arg1Value, TruffleString.FromJavaStringNode.getUncached(), DynamicScopeWrapperGen.DYNAMIC_OBJECT_LIBRARY_.getUncached(arg0Value.scope)
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberModifiable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            DynamicScopeWrapper arg0Value = (DynamicScopeWrapper)arg0Value_;
            return arg0Value.isMemberModifiable(
               arg1Value, TruffleString.FromJavaStringNode.getUncached(), DynamicScopeWrapperGen.DYNAMIC_OBJECT_LIBRARY_.getUncached(arg0Value.scope)
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberInsertable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DynamicScopeWrapper)receiver).isMemberInsertable(member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readMember(Object arg0Value_, String arg1Value) throws UnknownIdentifierException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            DynamicScopeWrapper arg0Value = (DynamicScopeWrapper)arg0Value_;
            return arg0Value.readMember(
               arg1Value,
               TruffleString.FromJavaStringNode.getUncached(),
               DynamicScopeWrapperGen.DYNAMIC_OBJECT_LIBRARY_.getUncached(arg0Value.scope),
               ExportValueNode.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeMember(Object arg0Value_, String arg1Value, Object arg2Value) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            DynamicScopeWrapper arg0Value = (DynamicScopeWrapper)arg0Value_;
            arg0Value.writeMember(
               arg1Value,
               arg2Value,
               TruffleString.FromJavaStringNode.getUncached(),
               DynamicScopeWrapperGen.DYNAMIC_OBJECT_LIBRARY_.getUncached(arg0Value.scope)
            );
         }
      }
   }
}
