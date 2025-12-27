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
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.WriteNode;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ScopeVariables.class)
final class ScopeVariablesGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private ScopeVariablesGen() {
   }

   static {
      LibraryExport.register(ScopeVariables.class, new ScopeVariablesGen.InteropLibraryExports());
   }

   @GeneratedBy(ScopeVariables.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, ScopeVariables.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof ScopeVariables;

         InteropLibrary uncached = new ScopeVariablesGen.InteropLibraryExports.Uncached();
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof ScopeVariables;

         return new ScopeVariablesGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(ScopeVariables.class)
      private static final class Cached extends InteropLibrary {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @CompilerDirectives.CompilationFinal
         private ScopeVariablesGen.InteropLibraryExports.Cached.IsMemberReadableCachedData isMemberReadable_cached_cache;
         @CompilerDirectives.CompilationFinal
         private ScopeVariablesGen.InteropLibraryExports.Cached.IsMemberModifiableCachedData isMemberModifiable_cached_cache;
         @Node.Child
         private ScopeVariablesGen.InteropLibraryExports.Cached.ReadMemberCachedData readMember_cached_cache;
         @Node.Child
         private ScopeVariablesGen.InteropLibraryExports.Cached.WriteMemberCachedData writeMember_cached_cache;
         @CompilerDirectives.CompilationFinal
         private Node acceptsNode__accepts_cachedNode_;
         @CompilerDirectives.CompilationFinal
         private boolean acceptsNode__accepts_cachedNodeEnter_;

         protected Cached(Object receiver) {
            ScopeVariables castReceiver = (ScopeVariables)receiver;
            this.acceptsNode__accepts_cachedNode_ = castReceiver.blockOrRoot;
            this.acceptsNode__accepts_cachedNodeEnter_ = castReceiver.nodeEnter;
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof ScopeVariables) || ScopeVariablesGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof ScopeVariables && this.accepts_(receiver);
         }

         @ExplodeLoop
         @Override
         public boolean isMemberReadable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            ScopeVariables arg0Value = (ScopeVariables)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 3) != 0) {
               if ((state_0 & 1) != 0) {
                  for (ScopeVariablesGen.InteropLibraryExports.Cached.IsMemberReadableCachedData s0_ = this.isMemberReadable_cached_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.cachedMember_.equals(arg1Value)) {
                        return ScopeVariables.IsMemberReadable.doCached(arg0Value, arg1Value, s0_.cachedMember_, s0_.cachedResult_);
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  return ScopeVariables.IsMemberReadable.doGeneric(arg0Value, arg1Value);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.isMemberReadableAndSpecialize(arg0Value, arg1Value);
         }

         private boolean isMemberReadableAndSpecialize(ScopeVariables arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               int exclude = this.exclude_;
               if ((exclude & 1) == 0) {
                  int count0_ = 0;
                  ScopeVariablesGen.InteropLibraryExports.Cached.IsMemberReadableCachedData s0_ = this.isMemberReadable_cached_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.cachedMember_.equals(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 4) {
                     s0_ = new ScopeVariablesGen.InteropLibraryExports.Cached.IsMemberReadableCachedData(this.isMemberReadable_cached_cache);
                     s0_.cachedMember_ = arg1Value;
                     s0_.cachedResult_ = ScopeVariables.IsMemberReadable.doGeneric(arg0Value, arg1Value);
                     VarHandle.storeStoreFence();
                     this.isMemberReadable_cached_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return ScopeVariables.IsMemberReadable.doCached(arg0Value, arg1Value, s0_.cachedMember_, s0_.cachedResult_);
                  }
               }

               int var15;
               this.exclude_ = var15 = exclude | 1;
               this.isMemberReadable_cached_cache = null;
               state_0 &= -2;
               int var14;
               this.state_0_ = var14 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return ScopeVariables.IsMemberReadable.doGeneric(arg0Value, arg1Value);
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
                  ScopeVariablesGen.InteropLibraryExports.Cached.IsMemberReadableCachedData s0_ = this.isMemberReadable_cached_cache;
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

            ScopeVariables arg0Value = (ScopeVariables)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 12) != 0) {
               if ((state_0 & 4) != 0) {
                  for (ScopeVariablesGen.InteropLibraryExports.Cached.IsMemberModifiableCachedData s0_ = this.isMemberModifiable_cached_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.cachedMember_.equals(arg1Value)) {
                        return ScopeVariables.IsMemberModifiable.doCached(arg0Value, arg1Value, s0_.cachedMember_, s0_.cachedResult_);
                     }
                  }
               }

               if ((state_0 & 8) != 0) {
                  return ScopeVariables.IsMemberModifiable.doGeneric(arg0Value, arg1Value);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.isMemberModifiableAndSpecialize(arg0Value, arg1Value);
         }

         private boolean isMemberModifiableAndSpecialize(ScopeVariables arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               int exclude = this.exclude_;
               if ((exclude & 2) == 0) {
                  int count0_ = 0;
                  ScopeVariablesGen.InteropLibraryExports.Cached.IsMemberModifiableCachedData s0_ = this.isMemberModifiable_cached_cache;
                  if ((state_0 & 4) != 0) {
                     while (s0_ != null && !s0_.cachedMember_.equals(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 4) {
                     s0_ = new ScopeVariablesGen.InteropLibraryExports.Cached.IsMemberModifiableCachedData(this.isMemberModifiable_cached_cache);
                     s0_.cachedMember_ = arg1Value;
                     s0_.cachedResult_ = ScopeVariables.IsMemberModifiable.doGeneric(arg0Value, arg1Value);
                     VarHandle.storeStoreFence();
                     this.isMemberModifiable_cached_cache = s0_;
                     this.state_0_ = state_0 |= 4;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return ScopeVariables.IsMemberModifiable.doCached(arg0Value, arg1Value, s0_.cachedMember_, s0_.cachedResult_);
                  }
               }

               int var15;
               this.exclude_ = var15 = exclude | 2;
               this.isMemberModifiable_cached_cache = null;
               state_0 &= -5;
               int var14;
               this.state_0_ = var14 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return ScopeVariables.IsMemberModifiable.doGeneric(arg0Value, arg1Value);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @ExplodeLoop
         @Override
         public Object readMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            ScopeVariables arg0Value = (ScopeVariables)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 48) != 0) {
               if ((state_0 & 16) != 0) {
                  for (ScopeVariablesGen.InteropLibraryExports.Cached.ReadMemberCachedData s0_ = this.readMember_cached_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.cachedMember_.equals(arg1Value)) {
                        return ScopeVariables.ReadMember.doCached(arg0Value, arg1Value, s0_.cachedMember_, s0_.resolvedSlot_, s0_.readNode_);
                     }
                  }
               }

               if ((state_0 & 32) != 0) {
                  return ScopeVariables.ReadMember.doGeneric(arg0Value, arg1Value);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.readMemberAndSpecialize(arg0Value, arg1Value);
         }

         private Object readMemberAndSpecialize(ScopeVariables arg0Value, String arg1Value) throws UnknownIdentifierException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               int exclude = this.exclude_;
               if ((exclude & 4) == 0) {
                  int count0_ = 0;
                  ScopeVariablesGen.InteropLibraryExports.Cached.ReadMemberCachedData s0_ = this.readMember_cached_cache;
                  if ((state_0 & 16) != 0) {
                     while (s0_ != null && !s0_.cachedMember_.equals(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 4) {
                     s0_ = super.insert(new ScopeVariablesGen.InteropLibraryExports.Cached.ReadMemberCachedData(this.readMember_cached_cache));
                     s0_.cachedMember_ = arg1Value;
                     s0_.resolvedSlot_ = ScopeVariables.findSlot(arg1Value, arg0Value);
                     s0_.readNode_ = s0_.insertAccessor(ScopeVariables.findReadNode(s0_.resolvedSlot_));
                     VarHandle.storeStoreFence();
                     this.readMember_cached_cache = s0_;
                     this.state_0_ = state_0 |= 16;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return ScopeVariables.ReadMember.doCached(arg0Value, arg1Value, s0_.cachedMember_, s0_.resolvedSlot_, s0_.readNode_);
                  }
               }

               int var15;
               this.exclude_ = var15 = exclude | 4;
               this.readMember_cached_cache = null;
               state_0 &= -17;
               int var14;
               this.state_0_ = var14 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return ScopeVariables.ReadMember.doGeneric(arg0Value, arg1Value);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @ExplodeLoop
         @Override
         public void writeMember(Object arg0Value_, String arg1Value, Object arg2Value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            ScopeVariables arg0Value = (ScopeVariables)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 192) != 0) {
               if ((state_0 & 64) != 0) {
                  for (ScopeVariablesGen.InteropLibraryExports.Cached.WriteMemberCachedData s0_ = this.writeMember_cached_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.cachedMember_.equals(arg1Value)) {
                        ScopeVariables.WriteMember.doCached(arg0Value, arg1Value, arg2Value, s0_.cachedMember_, s0_.resolvedSlot_, s0_.writeNode_);
                        return;
                     }
                  }
               }

               if ((state_0 & 128) != 0) {
                  ScopeVariables.WriteMember.doGeneric(arg0Value, arg1Value, arg2Value);
                  return;
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.writeMemberAndSpecialize(arg0Value, arg1Value, arg2Value);
         }

         private void writeMemberAndSpecialize(ScopeVariables arg0Value, String arg1Value, Object arg2Value) throws UnknownIdentifierException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               int exclude = this.exclude_;
               if ((exclude & 8) == 0) {
                  int count0_ = 0;
                  ScopeVariablesGen.InteropLibraryExports.Cached.WriteMemberCachedData s0_ = this.writeMember_cached_cache;
                  if ((state_0 & 64) != 0) {
                     while (s0_ != null && !s0_.cachedMember_.equals(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 4) {
                     s0_ = super.insert(new ScopeVariablesGen.InteropLibraryExports.Cached.WriteMemberCachedData(this.writeMember_cached_cache));
                     s0_.cachedMember_ = arg1Value;
                     s0_.resolvedSlot_ = ScopeVariables.findSlot(arg1Value, arg0Value);
                     WriteNode writeNode___ = ScopeVariables.findWriteNode(s0_.resolvedSlot_);
                     if (writeNode___ instanceof Node) {
                        s0_.insertAccessor((Node)writeNode___);
                     }

                     s0_.writeNode_ = writeNode___;
                     VarHandle.storeStoreFence();
                     this.writeMember_cached_cache = s0_;
                     this.state_0_ = state_0 |= 64;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     ScopeVariables.WriteMember.doCached(arg0Value, arg1Value, arg2Value, s0_.cachedMember_, s0_.resolvedSlot_, s0_.writeNode_);
                     return;
                  }
               }

               int var16;
               this.exclude_ = var16 = exclude | 8;
               this.writeMember_cached_cache = null;
               state_0 &= -65;
               int var15;
               this.state_0_ = var15 = state_0 | 128;
               lock.unlock();
               hasLock = false;
               ScopeVariables.WriteMember.doGeneric(arg0Value, arg1Value, arg2Value);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         private boolean accepts_(Object arg0Value_) {
            ScopeVariables arg0Value = (ScopeVariables)arg0Value_;
            return arg0Value.accepts(this.acceptsNode__accepts_cachedNode_, this.acceptsNode__accepts_cachedNodeEnter_);
         }

         @Override
         public boolean isScope(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((ScopeVariables)receiver).isScope();
         }

         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((ScopeVariables)receiver).hasLanguage();
         }

         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((ScopeVariables)receiver).getLanguage();
         }

         @Override
         public boolean hasScopeParent(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((ScopeVariables)receiver).hasScopeParent();
         }

         @Override
         public Object getScopeParent(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((ScopeVariables)receiver).getScopeParent();
         }

         @Override
         public boolean hasMembers(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((ScopeVariables)receiver).hasMembers();
         }

         @Override
         public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((ScopeVariables)receiver).getMembers(includeInternal);
         }

         @Override
         public boolean isMemberInsertable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((ScopeVariables)receiver).isMemberInsertable(member);
         }

         @Override
         public boolean hasSourceLocation(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((ScopeVariables)receiver).hasSourceLocation();
         }

         @Override
         public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((ScopeVariables)receiver).getSourceLocation();
         }

         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((ScopeVariables)receiver).toDisplayString(allowSideEffects);
         }

         @GeneratedBy(ScopeVariables.class)
         private static final class IsMemberModifiableCachedData {
            @CompilerDirectives.CompilationFinal
            ScopeVariablesGen.InteropLibraryExports.Cached.IsMemberModifiableCachedData next_;
            @CompilerDirectives.CompilationFinal
            String cachedMember_;
            @CompilerDirectives.CompilationFinal
            boolean cachedResult_;

            IsMemberModifiableCachedData(ScopeVariablesGen.InteropLibraryExports.Cached.IsMemberModifiableCachedData next_) {
               this.next_ = next_;
            }
         }

         @GeneratedBy(ScopeVariables.class)
         private static final class IsMemberReadableCachedData {
            @CompilerDirectives.CompilationFinal
            ScopeVariablesGen.InteropLibraryExports.Cached.IsMemberReadableCachedData next_;
            @CompilerDirectives.CompilationFinal
            String cachedMember_;
            @CompilerDirectives.CompilationFinal
            boolean cachedResult_;

            IsMemberReadableCachedData(ScopeVariablesGen.InteropLibraryExports.Cached.IsMemberReadableCachedData next_) {
               this.next_ = next_;
            }
         }

         @GeneratedBy(ScopeVariables.class)
         private static final class ReadMemberCachedData extends Node {
            @Node.Child
            ScopeVariablesGen.InteropLibraryExports.Cached.ReadMemberCachedData next_;
            @CompilerDirectives.CompilationFinal
            String cachedMember_;
            @CompilerDirectives.CompilationFinal
            ScopeVariables.ResolvedSlot resolvedSlot_;
            @Node.Child
            JavaScriptNode readNode_;

            ReadMemberCachedData(ScopeVariablesGen.InteropLibraryExports.Cached.ReadMemberCachedData next_) {
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

         @GeneratedBy(ScopeVariables.class)
         private static final class WriteMemberCachedData extends Node {
            @Node.Child
            ScopeVariablesGen.InteropLibraryExports.Cached.WriteMemberCachedData next_;
            @CompilerDirectives.CompilationFinal
            String cachedMember_;
            @CompilerDirectives.CompilationFinal
            ScopeVariables.ResolvedSlot resolvedSlot_;
            @Node.Child
            WriteNode writeNode_;

            WriteMemberCachedData(ScopeVariablesGen.InteropLibraryExports.Cached.WriteMemberCachedData next_) {
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
      }

      @GeneratedBy(ScopeVariables.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof ScopeVariables) || ScopeVariablesGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof ScopeVariables && accepts_(receiver);
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

            ScopeVariables arg0Value = (ScopeVariables)arg0Value_;
            return ScopeVariables.IsMemberReadable.doGeneric(arg0Value, arg1Value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberModifiable(Object arg0Value_, String arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            ScopeVariables arg0Value = (ScopeVariables)arg0Value_;
            return ScopeVariables.IsMemberModifiable.doGeneric(arg0Value, arg1Value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readMember(Object arg0Value_, String arg1Value) throws UnknownIdentifierException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            ScopeVariables arg0Value = (ScopeVariables)arg0Value_;
            return ScopeVariables.ReadMember.doGeneric(arg0Value, arg1Value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeMember(Object arg0Value_, String arg1Value, Object arg2Value) throws UnknownIdentifierException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            ScopeVariables arg0Value = (ScopeVariables)arg0Value_;
            ScopeVariables.WriteMember.doGeneric(arg0Value, arg1Value, arg2Value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isScope(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((ScopeVariables)receiver).isScope();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((ScopeVariables)receiver).hasLanguage();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((ScopeVariables)receiver).getLanguage();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasScopeParent(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((ScopeVariables)receiver).hasScopeParent();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getScopeParent(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((ScopeVariables)receiver).getScopeParent();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMembers(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((ScopeVariables)receiver).hasMembers();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((ScopeVariables)receiver).getMembers(includeInternal);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberInsertable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((ScopeVariables)receiver).isMemberInsertable(member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasSourceLocation(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((ScopeVariables)receiver).hasSourceLocation();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((ScopeVariables)receiver).getSourceLocation();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((ScopeVariables)receiver).toDisplayString(allowSideEffects);
         }

         @CompilerDirectives.TruffleBoundary
         private static boolean accepts_(Object arg0Value_) {
            ScopeVariables arg0Value = (ScopeVariables)arg0Value_;
            return arg0Value.accepts(arg0Value.blockOrRoot, arg0Value.nodeEnter);
         }
      }
   }
}
