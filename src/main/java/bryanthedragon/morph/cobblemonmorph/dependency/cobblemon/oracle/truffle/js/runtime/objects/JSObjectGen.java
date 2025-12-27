package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.nodes.interop.JSInteropGetIteratorNode;
import com.oracle.truffle.js.nodes.interop.JSInteropGetIteratorNodeGen;
import com.oracle.truffle.js.nodes.interop.JSInteropInvokeNode;
import com.oracle.truffle.js.nodes.interop.JSInteropInvokeNodeGen;
import com.oracle.truffle.js.nodes.interop.KeyInfoNode;
import com.oracle.truffle.js.nodes.interop.KeyInfoNodeGen;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSObject.class)
public final class JSObjectGen {
   private JSObjectGen() {
   }

   static {
      LibraryExport.register(JSObject.class, new JSObjectGen.InteropLibraryExports());
   }

   @GeneratedBy(JSObject.class)
   public static class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, JSObject.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof JSObject;

         InteropLibrary uncached = new JSObjectGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof JSObject;

         return new JSObjectGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(JSObject.class)
      public static class Cached extends JSDynamicObjectGen.InteropLibraryExports.Cached {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private KeyInfoNode keyInfo;
         @Node.Child
         private JSInteropGetIteratorNode getIterator;
         @CompilerDirectives.CompilationFinal
         private JSObjectGen.InteropLibraryExports.Cached.GetMembersNonArrayCachedData getMembers_nonArrayCached_cache;
         @Node.Child
         private ReadElementNode readMemberNode__readMember_readNode_;
         @CompilerDirectives.CompilationFinal
         private boolean readMemberNode__readMember_bindMemberFunctions_;
         @Node.Child
         private ExportValueNode readMemberNode__readMember_exportNode_;
         @Node.Child
         private ImportValueNode writeMemberNode__writeMember_castValueNode_;
         @Node.Child
         private WriteElementNode writeMemberNode__writeMember_writeNode_;
         @Node.Child
         private JSInteropInvokeNode invokeMemberNode__invokeMember_callNode_;
         @Node.Child
         private ExportValueNode invokeMemberNode__invokeMember_exportNode_;

         protected Cached(Object receiver) {
            super(receiver);
         }

         @ExplodeLoop
         @Override
         public Object getMembers(Object arg0Value_, boolean arg1Value) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSObject arg0Value = (JSObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 3) != 0) {
               if ((state_0 & 1) != 0) {
                  for (JSObjectGen.InteropLibraryExports.Cached.GetMembersNonArrayCachedData s0_ = this.getMembers_nonArrayCached_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     assert s0_.cachedJSClass_ != null;

                     if (JSObject.getJSClass(arg0Value) == s0_.cachedJSClass_) {
                        return JSObject.GetMembers.nonArrayCached(arg0Value, arg1Value, s0_.cachedJSClass_);
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  return JSObject.GetMembers.nonArrayUncached(arg0Value, arg1Value);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.getMembersAndSpecialize(arg0Value, arg1Value);
         }

         private Object getMembersAndSpecialize(JSObject arg0Value, boolean arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               int exclude = this.exclude_;
               if (exclude == 0) {
                  int count0_ = 0;
                  JSObjectGen.InteropLibraryExports.Cached.GetMembersNonArrayCachedData s0_ = this.getMembers_nonArrayCached_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null) {
                        assert s0_.cachedJSClass_ != null;

                        if (JSObject.getJSClass(arg0Value) == s0_.cachedJSClass_) {
                           break;
                        }

                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null) {
                     JSClass cachedJSClass__ = JSObject.getJSClass(arg0Value);
                     if (cachedJSClass__ != null && JSObject.getJSClass(arg0Value) == cachedJSClass__ && count0_ < 3) {
                        s0_ = new JSObjectGen.InteropLibraryExports.Cached.GetMembersNonArrayCachedData(this.getMembers_nonArrayCached_cache);
                        s0_.cachedJSClass_ = cachedJSClass__;
                        VarHandle.storeStoreFence();
                        this.getMembers_nonArrayCached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                     }
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return JSObject.GetMembers.nonArrayCached(arg0Value, arg1Value, s0_.cachedJSClass_);
                  }
               }

               int var15;
               this.exclude_ = var15 = exclude | 1;
               this.getMembers_nonArrayCached_cache = null;
               state_0 &= -2;
               int var14;
               this.state_0_ = var14 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return JSObject.GetMembers.nonArrayUncached(arg0Value, arg1Value);
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
                  JSObjectGen.InteropLibraryExports.Cached.GetMembersNonArrayCachedData s0_ = this.getMembers_nonArrayCached_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         @Override
         public boolean hasMembers(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSObject)receiver).hasMembers();
         }

         @Override
         public Object readMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSObject arg0Value = (JSObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 4) != 0) {
               return arg0Value.readMember(
                  arg1Value,
                  this,
                  this.readMemberNode__readMember_readNode_,
                  this.readMemberNode__readMember_bindMemberFunctions_,
                  this.readMemberNode__readMember_exportNode_
               );
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.readMemberNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private Object readMemberNode_AndSpecialize(JSObject arg0Value, String arg1Value) throws UnknownIdentifierException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var7;
            try {
               int state_0 = this.state_0_;
               InteropLibrary readMemberNode__readMember_self__ = null;
               this.readMemberNode__readMember_readNode_ = super.insert(ReadElementNode.create(JSObject.language(this).getJSContext()));
               this.readMemberNode__readMember_bindMemberFunctions_ = JSObject.language(this).bindMemberFunctions();
               this.readMemberNode__readMember_exportNode_ = super.insert(ExportValueNode.create());
               int var11;
               this.state_0_ = var11 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               var7 = arg0Value.readMember(
                  arg1Value,
                  this,
                  this.readMemberNode__readMember_readNode_,
                  this.readMemberNode__readMember_bindMemberFunctions_,
                  this.readMemberNode__readMember_exportNode_
               );
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public boolean isMemberReadable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSObject arg0Value = (JSObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 8) != 0) {
               return arg0Value.isMemberReadable(arg1Value, this.keyInfo);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isMemberReadableNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isMemberReadableNode_AndSpecialize(JSObject arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_0 = this.state_0_;
               this.keyInfo = super.insert(this.keyInfo == null ? KeyInfoNodeGen.create() : this.keyInfo);
               int var10;
               this.state_0_ = var10 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.isMemberReadable(arg1Value, this.keyInfo);
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

            JSObject arg0Value = (JSObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 16) != 0) {
               arg0Value.writeMember(
                  arg1Value, arg2Value, this.keyInfo, this.writeMemberNode__writeMember_castValueNode_, this.writeMemberNode__writeMember_writeNode_
               );
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.writeMemberNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }
         }

         private void writeMemberNode_AndSpecialize(JSObject arg0Value, String arg1Value, Object arg2Value) throws UnknownIdentifierException, UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               this.keyInfo = super.insert(this.keyInfo == null ? KeyInfoNodeGen.create() : this.keyInfo);
               this.writeMemberNode__writeMember_castValueNode_ = super.insert(ImportValueNode.create());
               this.writeMemberNode__writeMember_writeNode_ = super.insert(WriteElementNode.createCachedInterop());
               int var10;
               this.state_0_ = var10 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               arg0Value.writeMember(
                  arg1Value, arg2Value, this.keyInfo, this.writeMemberNode__writeMember_castValueNode_, this.writeMemberNode__writeMember_writeNode_
               );
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public boolean isMemberModifiable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSObject arg0Value = (JSObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 32) != 0) {
               return arg0Value.isMemberModifiable(arg1Value, this.keyInfo);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isMemberModifiableNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isMemberModifiableNode_AndSpecialize(JSObject arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_0 = this.state_0_;
               this.keyInfo = super.insert(this.keyInfo == null ? KeyInfoNodeGen.create() : this.keyInfo);
               int var10;
               this.state_0_ = var10 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.isMemberModifiable(arg1Value, this.keyInfo);
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

            JSObject arg0Value = (JSObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 64) != 0) {
               return arg0Value.isMemberInsertable(arg1Value, this.keyInfo);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isMemberInsertableNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isMemberInsertableNode_AndSpecialize(JSObject arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_0 = this.state_0_;
               this.keyInfo = super.insert(this.keyInfo == null ? KeyInfoNodeGen.create() : this.keyInfo);
               int var10;
               this.state_0_ = var10 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.isMemberInsertable(arg1Value, this.keyInfo);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public void removeMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            ((JSObject)receiver).removeMember(member);
         }

         @Override
         public boolean isMemberRemovable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSObject arg0Value = (JSObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 128) != 0) {
               return arg0Value.isMemberRemovable(arg1Value, this.keyInfo);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isMemberRemovableNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isMemberRemovableNode_AndSpecialize(JSObject arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_0 = this.state_0_;
               this.keyInfo = super.insert(this.keyInfo == null ? KeyInfoNodeGen.create() : this.keyInfo);
               int var10;
               this.state_0_ = var10 = state_0 | 128;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.isMemberRemovable(arg1Value, this.keyInfo);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public Object invokeMember(Object arg0Value_, String arg1Value, Object... arg2Value) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSObject arg0Value = (JSObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 256) != 0) {
               return arg0Value.invokeMember(
                  arg1Value, arg2Value, this, this.invokeMemberNode__invokeMember_callNode_, this.invokeMemberNode__invokeMember_exportNode_
               );
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.invokeMemberNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }
         }

         private Object invokeMemberNode_AndSpecialize(JSObject arg0Value, String arg1Value, Object[] arg2Value) throws UnsupportedMessageException, UnknownIdentifierException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var8;
            try {
               int state_0 = this.state_0_;
               InteropLibrary invokeMemberNode__invokeMember_self__ = null;
               this.invokeMemberNode__invokeMember_callNode_ = super.insert(JSInteropInvokeNode.create());
               this.invokeMemberNode__invokeMember_exportNode_ = super.insert(ExportValueNode.create());
               int var12;
               this.state_0_ = var12 = state_0 | 256;
               lock.unlock();
               hasLock = false;
               var8 = arg0Value.invokeMember(
                  arg1Value, arg2Value, this, this.invokeMemberNode__invokeMember_callNode_, this.invokeMemberNode__invokeMember_exportNode_
               );
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

            JSObject arg0Value = (JSObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 512) != 0) {
               return arg0Value.isMemberInvocable(arg1Value, this.keyInfo);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isMemberInvocableNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isMemberInvocableNode_AndSpecialize(JSObject arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_0 = this.state_0_;
               this.keyInfo = super.insert(this.keyInfo == null ? KeyInfoNodeGen.create() : this.keyInfo);
               int var10;
               this.state_0_ = var10 = state_0 | 512;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.isMemberInvocable(arg1Value, this.keyInfo);
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

            JSObject arg0Value = (JSObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 1024) != 0) {
               return arg0Value.hasMemberReadSideEffects(arg1Value, this.keyInfo);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.hasMemberReadSideEffectsNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean hasMemberReadSideEffectsNode_AndSpecialize(JSObject arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_0 = this.state_0_;
               this.keyInfo = super.insert(this.keyInfo == null ? KeyInfoNodeGen.create() : this.keyInfo);
               int var10;
               this.state_0_ = var10 = state_0 | 1024;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.hasMemberReadSideEffects(arg1Value, this.keyInfo);
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

            JSObject arg0Value = (JSObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 2048) != 0) {
               return arg0Value.hasMemberWriteSideEffects(arg1Value, this.keyInfo);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.hasMemberWriteSideEffectsNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean hasMemberWriteSideEffectsNode_AndSpecialize(JSObject arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_0 = this.state_0_;
               this.keyInfo = super.insert(this.keyInfo == null ? KeyInfoNodeGen.create() : this.keyInfo);
               int var10;
               this.state_0_ = var10 = state_0 | 2048;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.hasMemberWriteSideEffects(arg1Value, this.keyInfo);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public boolean hasIterator(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSObject arg0Value = (JSObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 4096) != 0) {
               return arg0Value.hasIterator(this, this.getIterator);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.hasIteratorNode_AndSpecialize(arg0Value);
            }
         }

         private boolean hasIteratorNode_AndSpecialize(JSObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_0 = this.state_0_;
               InteropLibrary hasIteratorNode__hasIterator_self__ = null;
               this.getIterator = super.insert(this.getIterator == null ? JSInteropGetIteratorNode.create() : this.getIterator);
               int var10;
               this.state_0_ = var10 = state_0 | 4096;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.hasIterator(this, this.getIterator);
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

            JSObject arg0Value = (JSObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 8192) != 0) {
               return arg0Value.getIterator(this, this.getIterator);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.getIteratorNode_AndSpecialize(arg0Value);
            }
         }

         private Object getIteratorNode_AndSpecialize(JSObject arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var6;
            try {
               int state_0 = this.state_0_;
               InteropLibrary getIteratorNode__getIterator_self__ = null;
               this.getIterator = super.insert(this.getIterator == null ? JSInteropGetIteratorNode.create() : this.getIterator);
               int var10;
               this.state_0_ = var10 = state_0 | 8192;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.getIterator(this, this.getIterator);
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

            return ((JSObject)receiver).hasLanguage();
         }

         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSObject)receiver).getLanguage();
         }

         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSObject)receiver).toDisplayString(allowSideEffects);
         }

         @GeneratedBy(JSObject.class)
         private static final class GetMembersNonArrayCachedData {
            @CompilerDirectives.CompilationFinal
            JSObjectGen.InteropLibraryExports.Cached.GetMembersNonArrayCachedData next_;
            @CompilerDirectives.CompilationFinal
            JSClass cachedJSClass_;

            GetMembersNonArrayCachedData(JSObjectGen.InteropLibraryExports.Cached.GetMembersNonArrayCachedData next_) {
               this.next_ = next_;
            }
         }
      }

      @GeneratedBy(JSObject.class)
      public static class Uncached extends JSDynamicObjectGen.InteropLibraryExports.Uncached {
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
         public Object getMembers(Object arg0Value_, boolean arg1Value) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSObject arg0Value = (JSObject)arg0Value_;
            return JSObject.GetMembers.nonArrayUncached(arg0Value, arg1Value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMembers(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSObject)receiver).hasMembers();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readMember(Object arg0Value_, String arg1Value) throws UnknownIdentifierException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSObject arg0Value = (JSObject)arg0Value_;
            return arg0Value.readMember(
               arg1Value, this, JSObject.getUncachedRead(), JSObject.language(this).bindMemberFunctions(), ExportValueNode.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberReadable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSObject arg0Value = (JSObject)arg0Value_;
            return arg0Value.isMemberReadable(arg1Value, KeyInfoNodeGen.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeMember(Object arg0Value_, String arg1Value, Object arg2Value) throws UnknownIdentifierException, UnsupportedMessageException, UnsupportedTypeException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSObject arg0Value = (JSObject)arg0Value_;
            arg0Value.writeMember(arg1Value, arg2Value, KeyInfoNodeGen.getUncached(), ImportValueNode.getUncached(), JSObject.getUncachedWrite());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberModifiable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSObject arg0Value = (JSObject)arg0Value_;
            return arg0Value.isMemberModifiable(arg1Value, KeyInfoNodeGen.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberInsertable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSObject arg0Value = (JSObject)arg0Value_;
            return arg0Value.isMemberInsertable(arg1Value, KeyInfoNodeGen.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void removeMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            ((JSObject)receiver).removeMember(member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberRemovable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSObject arg0Value = (JSObject)arg0Value_;
            return arg0Value.isMemberRemovable(arg1Value, KeyInfoNodeGen.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object invokeMember(Object arg0Value_, String arg1Value, Object... arg2Value) throws UnsupportedMessageException, UnknownIdentifierException, ArityException, UnsupportedTypeException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSObject arg0Value = (JSObject)arg0Value_;
            return arg0Value.invokeMember(arg1Value, arg2Value, this, JSInteropInvokeNodeGen.getUncached(), ExportValueNode.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberInvocable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSObject arg0Value = (JSObject)arg0Value_;
            return arg0Value.isMemberInvocable(arg1Value, KeyInfoNodeGen.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMemberReadSideEffects(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSObject arg0Value = (JSObject)arg0Value_;
            return arg0Value.hasMemberReadSideEffects(arg1Value, KeyInfoNodeGen.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMemberWriteSideEffects(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSObject arg0Value = (JSObject)arg0Value_;
            return arg0Value.hasMemberWriteSideEffects(arg1Value, KeyInfoNodeGen.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasIterator(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSObject arg0Value = (JSObject)arg0Value_;
            return arg0Value.hasIterator(this, JSInteropGetIteratorNodeGen.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getIterator(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSObject arg0Value = (JSObject)arg0Value_;
            return arg0Value.getIterator(this, JSInteropGetIteratorNodeGen.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSObject)receiver).hasLanguage();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSObject)receiver).getLanguage();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSObject)receiver).toDisplayString(allowSideEffects);
         }
      }
   }
}
